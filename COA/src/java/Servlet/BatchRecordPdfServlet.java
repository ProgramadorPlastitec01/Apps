package Servlet;

import Method.BatchRecordManifest;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BatchRecordPdfServlet")
public class BatchRecordPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String orden = request.getParameter("orden");
            String lote = request.getParameter("lote");
            String cliente = request.getParameter("cliente");
            String anio = request.getParameter("anio");

            if (orden == null || lote == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(new Gson().toJson(
                        Collections.singletonMap("mensaje", "Faltan parámetros orden o lote.")));
                return;
            }

            Map<String, Object> resultado = BatchRecordManifest.build(orden, lote, cliente, anio);
            response.getWriter().print(new Gson().toJson(resultado));
        } catch (Exception ex) {
            getServletContext().log("Error generando manifest de Batch Record PDF", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(new Gson().toJson(
                    Collections.singletonMap("mensaje", "Error al procesar los documentos del Batch Record.")));
        }
    }
}
