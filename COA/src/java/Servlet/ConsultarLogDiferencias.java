package Servlet;

import Connection.ConnectionRegistrosLAB;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ConsultarLogDiferencias")
public class ConsultarLogDiferencias extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String idProductoParam = request.getParameter("idProducto");
            String lote = request.getParameter("lote");
            String idLineaParam = request.getParameter("idLinea");
            String ciclo = request.getParameter("ciclo");

            if (idProductoParam == null || lote == null || idLineaParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(new Gson().toJson(
                        Collections.singletonMap("mensaje", "Faltan parámetros de consulta.")));
                return;
            }

            int idProducto = Integer.parseInt(idProductoParam);
            int idLinea = Integer.parseInt(idLineaParam);

            ConnectionRegistrosLAB conexion = new ConnectionRegistrosLAB();
            List<Object[]> logs = conexion.ConsultarLogDiferencias(idProducto, lote, idLinea, ciclo);

            response.getWriter().print(new Gson().toJson(logs));
        } catch (Exception ex) {
            getServletContext().log("Error consultando logs de diferencias", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(new Gson().toJson(
                    Collections.singletonMap("mensaje", "No fue posible consultar el historial de cambios.")));
        }
    }
}
