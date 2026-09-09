package Servlet;

import Method.ChromeHeadlessPdf;
import Method.RemoteHtmlFetcher;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Server-side replacement for the old "abrirVisorImpresionVectorial"
 * (browser window.print()) flow. Fetches one remote document, renders it
 * with Chrome headless print-to-pdf (real print engine, respects @page and
 * page-break CSS) and streams the resulting PDF back directly.
 *
 * Same request contract as ProxyHtmlServlet ("proxyUrl" + optional extra
 * params posted as the remote form body) so it's a drop-in replacement from
 * the JSP's point of view.
 */
@WebServlet("/HtmlToPdfServlet")
public class HtmlToPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String targetUrl = request.getParameter("proxyUrl");
        if (targetUrl == null || targetUrl.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL de destino no especificada.");
            return;
        }

        Map<String, String> postParams = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String param = paramNames.nextElement();
            if (!"proxyUrl".equals(param)) {
                postParams.put(param, request.getParameter(param));
            }
        }

        String appBaseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";

        File workDir = new File(System.getProperty("java.io.tmpdir"), "coa-pdf-" + System.nanoTime());
        try {
            String html = RemoteHtmlFetcher.fetch(targetUrl.trim(), postParams.isEmpty() ? null : postParams, appBaseUrl, false, request.getHeader("Cookie"));
            html = RemoteHtmlFetcher.injectPrintStyle(html);

            File pdfFile = ChromeHeadlessPdf.renderHtmlToPdf(html, workDir);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"registro.pdf\"");
            response.setContentLengthLong(pdfFile.length());
            try (OutputStream out = response.getOutputStream()) {
                Files.copy(pdfFile.toPath(), out);
            }
        } catch (Exception ex) {
            getServletContext().log("Error generando PDF individual", ex);
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo generar el PDF del registro.");
            }
        } finally {
            deleteRecursively(workDir);
        }
    }

    private void deleteRecursively(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteRecursively(f);
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
    }
}
