package Connection;

import Connection.ProductDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import Tag.Product;

@WebServlet("/SearchProductsServlet")
public class SearchProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orden = request.getParameter("orden");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Product> productos = ProductDAO.buscarPorOrden(orden);

            // Convertir lista a JSON
            String json = new Gson().toJson(productos);
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("[]");
        }
    }
}

