package Servlets;

import Controladores.FichaTecnicaJpaController;
import Factory.Productos;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Ficha_tecnica extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=<-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            Productos prod = new Productos();
            FichaTecnicaJpaController jpa_fichaT = new FichaTecnicaJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
            int id_fichaT = 0, version = 0, estado = 0;
            String filtro = "", codigo = "", codigo_ficha = "", tipo = "", codigo_producto = "", nombre_producto = "";
            double atr = 0, atrMx = 0, atrMn = 0;
            double y1 = 0, desvMx_y1 = 0, desvMn_y1 = 0;
            double x1 = 0, desvMx_x1 = 0, desvMn_x1 = 0;
            double y2 = 0, desvMx_y2 = 0, desvMn_y2 = 0;
            double x2 = 0, desvMx_x2 = 0, desvMn_x2 = 0;
            double x3 = 0, desvMx_x3 = 0, desvMn_x3 = 0;
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_fichaT", id_fichaT);
                    request.getRequestDispatcher("Ficha_tecnica.jsp").forward(request, response);
                    break;
                case 2:
                    filtro = request.getParameter("txt_bus");
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    codigo = request.getParameter("txt_codigo").trim();
                    request.setAttribute("productos", prod.Productos(codigo));
                    request.setAttribute("codigo", codigo);
                    if (id_fichaT != 0) {
                        request.getRequestDispatcher("Ficha_tecnica?opc=1&idF=" + id_fichaT + "&txt_bus=" + filtro + "").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    }
                    break;
                case 3:
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    String[] Producto = request.getParameter("slt_producto").split(" / ");
                    codigo_producto = Producto[0];
                    nombre_producto = Producto[1];
                    tipo = request.getParameter("slt_registro");
                    codigo_ficha = request.getParameter("txt_codigo_ficha");
                    version = Integer.parseInt(request.getParameter("txt_version"));
                    y1 = Double.parseDouble(request.getParameter("txt_y1"));
                    desvMx_y1 = Double.parseDouble(request.getParameter("txt_desvMx_y1"));
                    desvMn_y1 = Double.parseDouble(request.getParameter("txt_desvMn_y1"));
                    x1 = Double.parseDouble(request.getParameter("txt_x1"));
                    desvMx_x1 = Double.parseDouble(request.getParameter("txt_desvMx_x1"));
                    desvMn_x1 = Double.parseDouble(request.getParameter("txt_desvMn_x1"));
                    y2 = Double.parseDouble(request.getParameter("txt_y2"));
                    desvMx_y2 = Double.parseDouble(request.getParameter("txt_desvMx_y2"));
                    desvMn_y2 = Double.parseDouble(request.getParameter("txt_desvMn_y2"));
                    x2 = Double.parseDouble(request.getParameter("txt_x2"));
                    desvMx_x2 = Double.parseDouble(request.getParameter("txt_desvMx_x2"));
                    desvMn_x2 = Double.parseDouble(request.getParameter("txt_desvMn_x2"));
                    x3 = Double.parseDouble(request.getParameter("txt_x3"));
                    desvMx_x3 = Double.parseDouble(request.getParameter("txt_desvMx_x3"));
                    desvMn_x3 = Double.parseDouble(request.getParameter("txt_desvMn_x3"));
                    atr = Double.parseDouble(request.getParameter("txt_atr"));
                    atrMx = Double.parseDouble(request.getParameter("txt_atrMx"));
                    atrMn = Double.parseDouble(request.getParameter("txt_atrMn"));
                    resultado = jpa_fichaT.registroFichaTecnica(codigo_ficha, version, codigo_producto, nombre_producto, y1, x1, y2, x2, x3, desvMx_y1, desvMn_y1, desvMx_x1, desvMn_x1, desvMx_y2, desvMn_y2, desvMx_x2, desvMn_x2, desvMx_x3, desvMn_x3, id_usuario, atr, atrMx, atrMn, tipo);
                    if (id_fichaT != 0) {
                        jpa_fichaT.estadoFichaTecnica(id_fichaT, 0);
                    }
                    request.setAttribute("Registro_ficha", resultado);
                    request.getRequestDispatcher("Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 4:
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    filtro = request.getParameter("txt_bus");
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_fichaT.estadoFichaTecnica(id_fichaT, estado);
                    request.setAttribute("estado_ficha", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
