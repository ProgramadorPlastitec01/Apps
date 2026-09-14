package Servlet;

import Method.BatchRecordManifest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sirve el HTML de uno de los Registros de Despeje de Línea de Inspección
 * Manga asociados a un orden/lote, para que FileManager.jsp pueda mostrarlo
 * ("Ver original") y convertirlo a PDF ("Ver PDF", vía HtmlToPdfServlet +
 * proxyUrl) igual que los demás registros del listado, antes de generar el
 * Batch Record unificado completo. Como puede haber varios despejes por
 * lote, se identifican por posición (índice) dentro de la misma lista que
 * BatchRecordManifest.consultarRegistrosDespejeManga ya trae.
 */
@WebServlet("/MangaDespejeViewServlet")
public class MangaDespejeViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        String indiceParam = request.getParameter("indice");

        if (orden == null || lote == null || indiceParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros orden, lote o indice.");
            return;
        }

        int indice;
        try {
            indice = Integer.parseInt(indiceParam);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro indice no es un número válido.");
            return;
        }

        try {
            BatchRecordManifest.MangaListResult resultado = BatchRecordManifest.consultarRegistrosDespejeManga(orden, lote);
            List<Map<String, Object>> documentos = resultado.documentos;
            if (documentos == null || indice < 0 || indice >= documentos.size()) {
                String motivo = resultado.diagnostico != null ? resultado.diagnostico
                        : "No hay Registro de Despeje de Inspección Manga en esa posición para este lote.";
                response.sendError(HttpServletResponse.SC_NOT_FOUND, motivo);
                return;
            }
            String html = (String) documentos.get(indice).get("html");
            if (html == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El Registro de Despeje no tiene contenido HTML.");
                return;
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(html);
        } catch (Exception ex) {
            getServletContext().log("Error consultando Registro de Despeje de Inspección Manga", ex);
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al consultar Inspección Manga.");
            }
        }
    }
}
