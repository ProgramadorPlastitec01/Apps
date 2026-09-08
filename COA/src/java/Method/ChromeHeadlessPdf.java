package Method;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Renders an HTML document to a PDF file using Chrome/Edge's headless
 * "print to PDF" mode, invoked as an external process. This reuses the same
 * real print engine a user would get from Ctrl+P > Guardar como PDF in the
 * browser, so it respects @page/page-break CSS and paginates tables
 * correctly instead of the pixel-slicing approach used by html2canvas.
 */
public class ChromeHeadlessPdf {

    private static final String[] BROWSER_CANDIDATE_PATHS = {
        "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
        "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
        "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe",
        "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe"
    };

    private static volatile String browserPathCache;

    public static String findBrowserExecutable() {
        if (browserPathCache != null) {
            return browserPathCache;
        }
        for (String path : BROWSER_CANDIDATE_PATHS) {
            if (new File(path).exists()) {
                browserPathCache = path;
                return browserPathCache;
            }
        }
        throw new IllegalStateException("No se encontró Chrome ni Edge instalado en el servidor para generar el PDF.");
    }

    public static File renderHtmlToPdf(String htmlContent, File workDir) throws IOException, InterruptedException {
        if (!workDir.exists()) {
            workDir.mkdirs();
        }

        String uid = UUID.randomUUID().toString();
        File htmlFile = new File(workDir, uid + ".html");
        File pdfFile = new File(workDir, uid + ".pdf");
        File profileDir = new File(workDir, uid + "-profile");
        profileDir.mkdirs();

        Files.write(htmlFile.toPath(), htmlContent.getBytes(StandardCharsets.UTF_8));

        String browserPath = findBrowserExecutable();

        // --user-data-dir is critical here: without a dedicated profile per
        // invocation, Chrome's single-instance behavior can hand off each new
        // "chrome.exe --headless ..." call to any Chrome window already open
        // on this machine (e.g. the developer's own browser, or a previous
        // call in the same batch) instead of actually running headless, which
        // silently produces an empty PDF with exit code 0.
        ProcessBuilder pb = new ProcessBuilder(
                browserPath,
                "--headless",
                "--disable-gpu",
                "--no-sandbox",
                "--no-first-run",
                "--disable-extensions",
                "--user-data-dir=" + profileDir.getAbsolutePath(),
                "--print-to-pdf=" + pdfFile.getAbsolutePath(),
                "--run-all-compositor-stages-before-draw",
                "--virtual-time-budget=15000",
                htmlFile.toURI().toString()
        );
        pb.redirectErrorStream(true);

        Process process = pb.start();
        try (InputStream is = process.getInputStream()) {
            byte[] buf = new byte[4096];
            while (is.read(buf) != -1) {
                // Drain stdout/stderr so the process never blocks on a full pipe buffer.
            }
        }

        boolean finished = process.waitFor(60, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            htmlFile.delete();
            throw new IOException("Tiempo de espera agotado generando el PDF con el navegador headless.");
        }

        htmlFile.delete();

        if (!pdfFile.exists() || pdfFile.length() == 0) {
            throw new IOException("El navegador headless no generó el archivo PDF esperado (código de salida " + process.exitValue() + ").");
        }

        return pdfFile;
    }
}
