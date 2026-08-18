package Connection;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SearchDatesServlet")
public class SearchDatesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> json = new HashMap<>();

        try {

            String orden = request.getParameter("orden");
            String producto = request.getParameter("producto");
            String lote = request.getParameter("lote");

            String[] fechas = ProductDAO.buscarFechas(
                    orden,
                    producto,
                    lote
            );

            if (fechas != null) {

                json.put("success", true);
                json.put("fechaInicio", fechas[0]);
                json.put("fechaFin", fechas[1]);

            } else {

                json.put("success", false);
                json.put("message", "No se encontraron fechas.");

            }

        } catch (Exception ex) {

            ex.printStackTrace();

            json.put("success", false);
            json.put("message", ex.getMessage());

        }

        response.getWriter().write(new Gson().toJson(json));

    }

}