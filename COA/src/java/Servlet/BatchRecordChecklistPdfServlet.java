package Servlet;

import Method.BatchRecordChecklist;
import Method.BatchRecordManifest;
import Method.ChromeHeadlessPdf;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * "Exportar PDF" de la lista de verificación R-GC-094 de un lote
 * (FileManager.jsp): solo el encabezado del registro y la tabla de
 * verificación, con el mismo formato que la portada del Batch Record
 * (BatchRecordPdfGenerateServlet.buildCoverHtml). Es una copia de consulta:
 * no lleva bloque de firma, no se archiva en Office Platform y no reemplaza
 * al Batch Record aprobado.
 */
@WebServlet("/BatchRecordChecklistPdfServlet")
public class BatchRecordChecklistPdfServlet extends HttpServlet {

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        if (orden == null || lote == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros orden o lote.");
            return;
        }

        File workDir = new File(System.getProperty("java.io.tmpdir"), "coa-checklist-" + System.nanoTime());
        try {
            Map<String, Object> manifest = BatchRecordManifest.build(orden, lote, cliente, anio, "", false);
            List<Map<String, Object>> documentos = (List<Map<String, Object>>) manifest.get("documentos");
            BatchRecordChecklist.Resultado checklist = BatchRecordChecklist.evaluar(cliente, anio, orden, lote, documentos);

            String html = BatchRecordPdfGenerateServlet.buildCoverHtml(manifest, documentos, checklist,
                    "COPIA DE CONSULTA (sin firma de aprobación)");
            File pdf = ChromeHeadlessPdf.renderHtmlToPdf(html, workDir);

            String fileName = "R-GC-094_" + safe(orden) + "_" + safe(lote) + "_"
                    + new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date()) + ".pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            response.setContentLengthLong(pdf.length());
            try (OutputStream out = response.getOutputStream()) {
                Files.copy(pdf.toPath(), out);
            }
        } catch (Exception ex) {
            getServletContext().log("Error exportando la lista de verificación R-GC-094 del lote " + lote, ex);
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo exportar la lista de verificación.");
            }
        } finally {
            File[] archivos = workDir.listFiles();
            if (archivos != null) {
                for (File f : archivos) {
                    f.delete();
                }
            }
            workDir.delete();
        }
    }

    private static String safe(String value) {
        return value == null || value.trim().isEmpty() ? "SIN_DATO" : value.trim().replaceAll("[^a-zA-Z0-9_\\-]", "_");
    }
}
