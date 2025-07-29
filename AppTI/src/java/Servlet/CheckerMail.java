package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Method.CheckMail;

public class CheckerMail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        CheckMail mailCheck = new CheckMail();
        HttpSession sesion = request.getSession();

        int opt = Integer.parseInt(request.getParameter("opt"));
        String Host = "", Port = "", Mail = "", Passw = "", Receptor = "", resultMail = "";
        boolean result = false;

        try {
            switch (opt) {
                case 1:
                    try {
                        Host = request.getParameter("txtHost").toString().trim();
                        Port = request.getParameter("txtPort").toString().trim();
                        Mail = request.getParameter("txtMail").toString().trim();
                        Passw = request.getParameter("txtfakePsw").toString().trim();
                        Receptor = request.getParameter("txtReceptor").toString().trim();
                        
                        resultMail = mailCheck.MailChecker(Host, Port, Mail, Passw, Receptor);
                        
                        request.setAttribute("Host", Host);
                        request.setAttribute("Port", Port);
                        request.setAttribute("Mail", Mail);
                        request.setAttribute("Passw", Passw);
                        request.setAttribute("Receptor", Receptor);
                        request.setAttribute("resultMail", resultMail);
                    } catch (Exception e) {
                        request.getRequestDispatcher("CheckMail.jsp").forward(request, response);
                    }
                    request.getRequestDispatcher("CheckMail.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("CheckMail.jsp").forward(request, response);
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
