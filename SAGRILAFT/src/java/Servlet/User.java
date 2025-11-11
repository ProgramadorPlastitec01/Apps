package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.UserControllerJpa;
import javax.servlet.http.HttpSession;

public class User extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        UserControllerJpa UserJpa = new UserControllerJpa();
        String Name = "", LstName = "", User = "", Mail = "", Position = "";
        int Role = 0, Document = 0, opt = 0, IdUser = 0, State = 0;
        
        HttpSession sesion = request.getSession();
        String permiss = sesion.getAttribute("Permisos").toString();
        
        boolean Result = false;
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE USER">
                    try {
                        IdUser = Integer.parseInt(request.getParameter("IdUser"));
                    } catch (Exception e) {
                        IdUser = 0;
                    }
                    request.setAttribute("IdUser", IdUser);
                    request.setAttribute("permiss", permiss);
                    request.getRequestDispatcher("User.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER USER">
                    Name = request.getParameter("TxtName");
                    LstName = request.getParameter("TxtLst");
                    Document = Integer.parseInt(request.getParameter("TxtDoc").toString());
                    User = request.getParameter("TxtUser");
                    Role = Integer.parseInt(request.getParameter("CbxRole").toString());
                    Mail = request.getParameter("TxtMail");
                    Position = request.getParameter("CbxPosit");
                    Result = UserJpa.UserRegister(Name, LstName, Document, User, Role, Mail, Position, "Administrador");
                    request.setAttribute("UserRegister", Result);
                    request.getRequestDispatcher("User?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE USER">
                    IdUser = Integer.parseInt(request.getParameter("IdUser").toString());
                    Name = request.getParameter("TxtName");
                    LstName = request.getParameter("TxtLst");
                    Document = Integer.parseInt(request.getParameter("TxtDoc").toString());
                    User = request.getParameter("TxtUser");
                    Role = Integer.parseInt(request.getParameter("CbxRole").toString());
                    Mail = request.getParameter("TxtMail");
                    Position = request.getParameter("CbxPosit");
                    Result = UserJpa.UserUpdate(IdUser, Name, LstName, Document, User, Role, Mail, Position);
                    request.setAttribute("UserUpdate", Result);
                    request.getRequestDispatcher("User?opt=1&IdUser=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    IdUser = Integer.parseInt(request.getParameter("IdUser").toString());
                    State = Integer.parseInt(request.getParameter("State").toString());
                    Result = UserJpa.UserUpdateState(IdUser, State);
                    request.setAttribute("UserState", Result);
                    request.getRequestDispatcher("User?opt=1&IdUser=0").forward(request, response);
                    break;
                case 5:
                    IdUser = Integer.parseInt(request.getParameter("IdUser").toString());
                    Result = UserJpa.UserUpdatePass(IdUser);
                    request.setAttribute("UserPass", Result);
                    request.getRequestDispatcher("User?opt=1&IdUser=0").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("User.jsp").forward(request, response);
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
