package Servlet;

import Method.BatchRecordManifest;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sirve el HTML de los Registros de Cabecera de Inspección Manga asociados a
 * un orden/lote, para que FileManager.jsp pueda mostrarlo ("Ver original") y
 * convertirlo a PDF ("Ver PDF", vía HtmlToPdfServlet + proxyUrl) igual que
 * los demás registros del listado, antes de generar el Batch Record
 * unificado completo.
 */
@WebServlet("/MangaCabeceraViewServlet")
public class MangaCabeceraViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");

        if (orden == null || lote == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros orden o lote.");
            return;
        }

        try {
            BatchRecordManifest.MangaResult resultado = BatchRecordManifest.consultarRegistrosCabeceraManga(orden, lote);
            Map<String, Object> doc = resultado.documento;
            if (doc == null || doc.get("html") == null) {
                String motivo = resultado.diagnostico != null ? resultado.diagnostico
                        : "No hay Registros de Cabecera de Inspección Manga para este lote.";
                response.sendError(HttpServletResponse.SC_NOT_FOUND, motivo);
                return;
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print((String) doc.get("html"));
        } catch (Exception ex) {
            getServletContext().log("Error consultando Registros de Cabecera de Inspección Manga", ex);
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al consultar Inspección Manga.");
            }
        }
    }
}
