package Servlets;

import Metodos.ProductoStock;
import Factory.ReferenciasMANT;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockMinimo")
public class StockMinimoServlet extends HttpServlet {

    ReferenciasMANT FactoryJpa = new ReferenciasMANT();

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        int pagina = 1;
        String busqueda = request.getParameter("buscar");

        try {
            String pagParam = request.getParameter("pagina");
            if (pagParam != null) {
                pagina = Integer.parseInt(pagParam);
                if (pagina < 1) pagina = 1;
            }
        } catch (Exception e) {
            pagina = 1;
        }

        if (busqueda == null) busqueda = "";

        int registrosPorPagina = 100;
        int offset = (pagina - 1) * registrosPorPagina;

        try {

            List<ProductoStock> lista =
                FactoryJpa.StockMinimo(offset,
                                       registrosPorPagina,
                                       busqueda);

            for (ProductoStock p : lista) {

                out.print("<tr>");

                if ("Stock".equals(p.getEstado())) {
                    out.print("<td style='text-align:center;'>"
                            + "<i style='font-size:20px;color:#169a2c;' "
                            + "class='fas fa-flag'></i></td>");
                } else {
                    out.print("<td style='text-align:center;'>"
                            + "<i style='font-size:20px;color:#f17e18;' "
                            + "class='fas fa-flag'></i></td>");
                }

                out.print("<td><b>" + p.getCod() + "</b></td>");
                out.print("<td>" + p.getNombre() + "</td>");
                out.print("<td>" + p.getMinimo() + "</td>");
                out.print("<td>" + p.getExist() + "</td>");
                out.print("</tr>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("<tr><td colspan='5' style='color:red;'>");
            out.print(e.toString());
            out.print("</td></tr>");
        }

        out.close();
    }
}
