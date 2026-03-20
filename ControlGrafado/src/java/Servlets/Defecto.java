package Servlets;

import Controladores.DefectoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Defecto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
            boolean resultado = false;
            DefectoJpaController jpa_defecto = new DefectoJpaController();
            String filtro = "", defecto = "";
            int id_defecto = 0, estado = 0;
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    id_defecto = Integer.parseInt(request.getParameter("idD"));
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_defecto", id_defecto);
                    request.getRequestDispatcher("Defecto.jsp").forward(request, response);
                    break;
                case 2:
                    defecto = request.getParameter("txt_defecto");
                    resultado = jpa_defecto.registroDefecto(defecto, id_usuario);
                    request.setAttribute("registro_defecto", resultado);
                    request.getRequestDispatcher("Defecto?opc=1&idD=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_defecto = Integer.parseInt(request.getParameter("idD"));
                    defecto = request.getParameter("txt_defecto");
                    resultado = jpa_defecto.modificarDefecto(id_defecto, defecto);
                    request.setAttribute("modificar_defecto", resultado);
                    request.getRequestDispatcher("Defecto?opc=1&idD=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_defecto = Integer.parseInt(request.getParameter("idD"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_defecto.estadoDefecto(id_defecto, estado);
                    request.setAttribute("estado_defecto", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Defecto?opc=1&idD=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
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
