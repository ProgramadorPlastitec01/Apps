package Connection;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchCountServlet")
public class SearchCountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonObject json = new JsonObject();

        try {

            String orden = request.getParameter("orden");
            String producto = request.getParameter("producto");
            String lote = request.getParameter("lote");
            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin = request.getParameter("fechaFin");

            int total = ProductDAO.contarRegistros(
                    orden,
                    producto,
                    lote,
                    fechaInicio,
                    fechaFin
            );

            json.addProperty("success", true);
            json.addProperty("totalRegistros", total);

        } catch (Exception ex) {

            ex.printStackTrace();

            json.addProperty("success", false);
            json.addProperty("totalRegistros", 0);
            json.addProperty("message", ex.getMessage());

        }

        response.getWriter().write(json.toString());

    }

}