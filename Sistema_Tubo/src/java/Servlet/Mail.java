package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.CorreoJpaController;

public class Mail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            CorreoJpaController MailJpa = new CorreoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_mail = 0, port = 0, est = 0;
            String functtion = "", emisor = "", pass = "", host = "", receptor = "";
            boolean result = false;

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT TEMPLATES">
                    try {
                        id_mail = Integer.parseInt(request.getParameter("id_mail"));
                    } catch (Exception e) {
                        id_mail = 0;
                    }
                    request.setAttribute("id_mail", id_mail);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("Mail.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT MAIL">
                    try {
                        id_mail = Integer.parseInt(request.getParameter("id_mail"));
                    } catch (Exception e) {
                        id_mail = 0;
                    }
                    functtion = request.getParameter("Txt_funct");
                    emisor = request.getParameter("Txt_emisor");
                    pass = request.getParameter("Txt_password");
                    host = request.getParameter("Txt_host");
                    port = Integer.parseInt(request.getParameter("Nmb_port"));
                    receptor = request.getParameter("Txt_recept");

                    if (id_mail <= 0) {
                        result = MailJpa.MailRegister(functtion, emisor, pass, host, port, receptor, rol_usuario);
                        request.setAttribute("MailRegister", result);
                    } else {
                        est = Integer.parseInt(request.getParameter("Nmb_est"));
                        result = MailJpa.MailUpdate(id_mail, functtion, emisor, pass, host, port, receptor, est);
                        request.setAttribute("MailUpdate", result);
                    }
                    request.getRequestDispatcher("Mail?opc=1%id_mail=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MAIL STATUS">
                    try {
                        id_mail = Integer.parseInt(request.getParameter("id_mail"));
                    } catch (Exception e) {
                        id_mail = 0;
                    }
                    est = Integer.parseInt(request.getParameter("Nmb_est"));
                    if (est == 1) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                    result = MailJpa.MailChangeEstate(id_mail, est);
                    request.setAttribute("MailChangeState", result);
                    request.getRequestDispatcher("Mail?opc=1&id_mail=0").forward(request, response);
//</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Mail.jsp").forward(request, response);
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
