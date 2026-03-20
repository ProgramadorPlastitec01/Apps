package Servlets;

import Controladores.ComentarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Nota extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
            String Usuario = sesion.getAttribute("Nombre").toString();
            boolean resultado = false;
            ComentarioJpaController jpa_nota = new ComentarioJpaController();
            String filtro = "", fecha = "", asunto = "", descripcion = "";
            int id_nota = 0, estado = 0;
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    id_nota = Integer.parseInt(request.getParameter("idN"));
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_nota", id_nota);
                    request.getRequestDispatcher("Nota.jsp").forward(request, response);
                    break;
                case 2:
                    fecha = request.getParameter("txt_fecha");
                    asunto = request.getParameter("txt_asunto");
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_nota.registroNota(fecha, asunto, descripcion, id_usuario);
                    request.setAttribute("registro_nota", resultado);
                    request.getRequestDispatcher("Nota?opc=1&idN=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_nota = Integer.parseInt(request.getParameter("idN"));
                    fecha = request.getParameter("txt_fecha");
                    asunto = request.getParameter("txt_asunto");
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_nota.modificarNota(id_nota, fecha, asunto, descripcion);
                    request.setAttribute("modificar_nota", resultado);
                    request.getRequestDispatcher("Nota?opc=1&idN=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_nota = Integer.parseInt(request.getParameter("idN"));
                    resultado = jpa_nota.estadoNota(id_nota, Usuario);
                    request.setAttribute("estado_nota", resultado);
                    request.getRequestDispatcher("Nota?opc=1&idN=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
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
