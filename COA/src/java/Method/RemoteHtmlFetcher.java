package Method;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fetches an HTML document from a remote (or internal, relative) URL and
 * normalizes it (adds a base href so relative assets resolve, strips
 * scripts). Shared by ProxyHtmlServlet (browser-side viewer) and the
 * server-side PDF generation servlets, so both proxy paths behave the same.
 */
public class RemoteHtmlFetcher {

    /** Kept for backwards compatibility (e.g. ProxyHtmlServlet's live browser-tab viewer): strips scripts. */
    public static String fetch(String targetUrl, Map<String, String> postParams, String appBaseUrl) throws IOException {
        return fetch(targetUrl, postParams, appBaseUrl, true);
    }

    /**
     * @param stripScripts Whether to strip &lt;script&gt; tags from the fetched document.
     *      Stripping is meant for the old "open in a live, visible browser tab" viewer,
     *      where a rogue script could pop up dialogs, redirect, or otherwise hijack the
     *      user's tab. Chrome headless (used to render PDFs) has none of those risks — and
     *      some source documents (e.g. R-PRF-007) actually need their own script to run in
     *      order to populate their printable content (a contenteditable/textarea-backed
     *      template), so PDF generation must pass stripScripts=false or it prints blank.
     */
    public static String fetch(String targetUrl, Map<String, String> postParams, String appBaseUrl, boolean stripScripts) throws IOException {
        String resolvedUrl = resolveUrl(targetUrl, appBaseUrl);

        HttpURLConnection conn = null;
        try {
            URL url = new URL(resolvedUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) COA-BatchRecord/1.0");

            if (postParams != null && !postParams.isEmpty()) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, String> entry : postParams.entrySet()) {
                    if (postData.length() > 0) {
                        postData.append("&");
                    }
                    postData.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue() != null ? entry.getValue() : "", "UTF-8"));
                }
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.toString().getBytes("UTF-8"));
                    os.flush();
                }
            } else {
                conn.setRequestMethod("GET");
            }

            int status = conn.getResponseCode();
            InputStream is = (status >= 400) ? conn.getErrorStream() : conn.getInputStream();
            if (is == null) {
                return "<h3>Error: No se recibió contenido del servidor remoto.</h3>";
            }

            byte[] rawBytes;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try {
                byte[] chunk = new byte[8192];
                int n;
                while ((n = is.read(chunk)) != -1) {
                    buffer.write(chunk, 0, n);
                }
            } finally {
                is.close();
            }
            rawBytes = buffer.toByteArray();

            String html = new String(rawBytes, detectCharset(conn.getContentType(), rawBytes));
            String baseUrl = resolvedUrl.substring(0, resolvedUrl.lastIndexOf('/') + 1);

            // Ignore whatever charset the source page itself declares: we already
            // decoded its bytes above and always write the temp file as UTF-8
            // (see ChromeHeadlessPdf), so a stale <meta charset=ISO-8859-1> left
            // in place would make Chrome misread our correctly-decoded content
            // right back into mojibake. Strip any declaration and force UTF-8.
            html = html.replaceAll("(?i)<meta[^>]*charset\\s*=[^>]*>", "");
            String headInjection = "<meta charset=\"UTF-8\"><base href=\"" + baseUrl + "\">";

            if (html.contains("<head>")) {
                html = html.replace("<head>", "<head>" + headInjection);
            } else if (html.contains("<HEAD>")) {
                html = html.replace("<HEAD>", "<HEAD>" + headInjection);
            } else {
                html = headInjection + html;
            }

            return stripScripts ? html.replaceAll("(?i)<script[\\s\\S]*?</script>", "") : html;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Some documents (e.g. "DownloadGL?File_name=...") come back as a
     * relative URL pointing at another servlet inside this same app instead
     * of an absolute http(s) URL. Resolve those against the app's own base
     * URL so they can be fetched like any other document.
     */
    private static String resolveUrl(String targetUrl, String appBaseUrl) {
        targetUrl = targetUrl.trim();
        if (targetUrl.matches("(?i)^https?://.*")) {
            return targetUrl;
        }
        String base = appBaseUrl.endsWith("/") ? appBaseUrl : appBaseUrl + "/";
        String relative = targetUrl.startsWith("/") ? targetUrl.substring(1) : targetUrl;
        return base + relative;
    }

    private static final Pattern CONTENT_TYPE_CHARSET = Pattern.compile("charset=([^;\\s]+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern META_CHARSET = Pattern.compile("<meta[^>]+charset=[\"']?([^\"';>\\s]+)", Pattern.CASE_INSENSITIVE);

    /**
     * The remote documents proxied here are legacy JSP pages that declare
     * (and genuinely are encoded as) ISO-8859-1/Windows-1252 — confirmed by
     * inspecting the raw bytes directly (e.g. the byte for an accented "á"
     * is the single byte 0xE1, not the 2-byte UTF-8 sequence 0xC3 0xA1).
     * Honor the real charset: Content-Type header first, then a &lt;meta
     * charset&gt;/&lt;meta http-equiv&gt; sniff of the raw bytes, falling back to
     * Windows-1252 by default. Windows-1252 is used instead of strict
     * ISO-8859-1 (including when ISO-8859-1 is what's declared) because
     * these Windows-authored pages use bytes in the 0x80-0x9F range for
     * punctuation like bullets/curly quotes, which ISO-8859-1 maps to
     * unprintable control characters but Windows-1252 maps correctly — the
     * same "labeled ISO-8859-1 but treat as Windows-1252" rule browsers use.
     */
    private static String detectCharset(String contentType, byte[] rawBytes) {
        if (contentType != null) {
            Matcher m = CONTENT_TYPE_CHARSET.matcher(contentType);
            if (m.find()) {
                String cs = validCharsetOrNull(m.group(1));
                if (cs != null) {
                    return cs;
                }
            }
        }
        String sniff = new String(rawBytes, 0, Math.min(rawBytes.length, 2048), StandardCharsets.ISO_8859_1);
        Matcher m = META_CHARSET.matcher(sniff);
        if (m.find()) {
            String cs = validCharsetOrNull(m.group(1));
            if (cs != null) {
                return cs;
            }
        }
        return "windows-1252";
    }

    private static String validCharsetOrNull(String name) {
        try {
            String cs = name.trim().replace("\"", "").replace("'", "");
            if (cs.equalsIgnoreCase("ISO-8859-1") || cs.equalsIgnoreCase("latin1")
                    || cs.equalsIgnoreCase("iso-8859-15")) {
                cs = "windows-1252";
            }
            return Charset.isSupported(cs) ? cs : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Injects a top-level (not nested in @media) @page rule plus a few
     * print-friendly defaults, so Chrome's real print engine (used by
     * ChromeHeadlessPdf) renders the document as landscape A4 without
     * relying on any print CSS the source document may or may not have.
     */
    public static String injectPrintStyle(String html) {
        String style = "<style>"
                + "@page { size: A4 landscape; margin: 8mm; }"
                + "html, body { margin:0; padding:0; background:#ffffff; }"
                + "table { page-break-inside: auto; }"
                + "tr { page-break-inside: avoid; page-break-after: auto; }"
                // Chrome's --print-to-pdf omits background colors/images by default
                // (same as the browser's "Background graphics" print option being
                // unchecked); force them on so header/coordinate cell colors survive.
                + "* { -webkit-print-color-adjust: exact !important; print-color-adjust: exact !important; }"
                // Documents built on the jQuery-TE rich text editor (e.g. R-PRF-057)
                // wrap their printable content in a fixed-height, scrollable
                // .jqte_editor box; expand it and drop the toolbar so print shows
                // the full content instead of just the visible scroll viewport.
                + ".jqte, .jqte_editor { height:auto !important; max-height:none !important; overflow:visible !important; }"
                + ".jqte_toolbar { display:none !important; }"
                + "</style>";

        if (html.toLowerCase().contains("</head>")) {
            html = html.replaceFirst("(?i)</head>", style + "</head>");
        } else {
            html = style + html;
        }

        // Belt-and-suspenders for the jQuery-TE case above: some versions set
        // the editor box's height as an inline style at init time (higher
        // precedence than a plain stylesheet rule in some edge cases), and
        // that init can run after our <style> tag is parsed. Force it via JS,
        // with a short delay so it runs after jQuery-TE's own setup, and set
        // it with setProperty(..., "important") so it can't be re-overridden.
        String script = "<script>setTimeout(function(){"
                + "document.querySelectorAll('.jqte, .jqte_editor').forEach(function(el){"
                + "el.style.setProperty('height','auto','important');"
                + "el.style.setProperty('max-height','none','important');"
                + "el.style.setProperty('overflow','visible','important');"
                + "});"
                + "document.querySelectorAll('.jqte_toolbar').forEach(function(el){"
                + "el.style.setProperty('display','none','important');"
                + "});"
                + "}, 500);</script>";

        if (html.toLowerCase().contains("</body>")) {
            return html.replaceFirst("(?i)</body>", script + "</body>");
        }
        return html + script;
    }
}
