package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controladores.UsuarioJpaController;
import java.util.List;

public class Perfil extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        UsuarioJpaController usuarioJpa = new UsuarioJpaController();
        List lst_usuario = null;

        try {
            int opc = Integer.parseInt(request.getParameter("opc"));

            String txt_img = "", txt_user = "", txt_apellido = "";
            int id_user = 0, actu = 0;
            boolean result;

            switch (opc) {
                case 1:
                    request.getRequestDispatcher("Perfil.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        actu = Integer.parseInt(request.getParameter("actu"));
                    } catch (Exception e) {
                        actu = 0;
                    }
                    if (actu == 1) {
                        id_user = Integer.parseInt(request.getParameter("id_user"));
                        txt_user = request.getParameter("Txt_user");
                        txt_apellido = request.getParameter("Txt_lastname");
                        result = usuarioJpa.CAmbiarNombreApellido(id_user, txt_user, txt_apellido);
                        request.setAttribute("CambiarNombreApellido", result);
                    } else {
                        txt_img = request.getParameter("txt_img");
                        id_user = Integer.parseInt(request.getParameter("id_user"));
                        result = usuarioJpa.cambiarFotoPerfil(id_user, txt_img);
                        request.setAttribute("CambioFoto", result);
                    }
                    request.getRequestDispatcher("Perfil?opc=1").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
