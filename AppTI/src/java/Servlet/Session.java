package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Encript.ControlEncryption;
import Controller.UserControllerJpa;
import Mail.Mail_Session;
import java.util.List;
import java.time.LocalDate;

import SQL.ConnectionsBd;

public class Session extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            ControlEncryption md5 = new ControlEncryption();
            UserControllerJpa UserJpa = new UserControllerJpa();
            Mail_Session mailSend = new Mail_Session();
            ConnectionsBd OtherBd = new ConnectionsBd();
            int opt = Integer.parseInt(request.getParameter("opt"));
            List lst_user = null, lst_userRest = null;
            int temp = 0, IdUser = 0, document = 0;
            String user = "", password = "", passwordEncrypt = "", doc = "", cod = "", mail = "";
            String StartDate = "", EndDate = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="LOGIN">
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    if (temp == 1) {
                        IdUser = Integer.parseInt(request.getParameter("IdUser"));
                        request.setAttribute("IdUser", IdUser);
                        request.setAttribute("Change_Password", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        user = request.getParameter("Txt_user");
                        password = request.getParameter("Txt_password");
                        if (password.length() >= 8) {
                            passwordEncrypt = md5.md5(password);
                            lst_user = UserJpa.ConsultUsersUserPassword(user, passwordEncrypt);
                            if (lst_user == null) {
                                lst_user = UserJpa.ConsultUsersUserPassword(user, password);
                            }
                        } else {
                            lst_user = UserJpa.ConsultUsersUserPassword(user, password);
                        }
                        if (lst_user == null) {
                            result = true;
                            request.setAttribute("Non_existent_user", result);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            Object[] obj_session = (Object[]) lst_user.get(0);
                            if ((Integer) obj_session[11] == 0) {
                                result = true;
                                request.setAttribute("Deactivaded_user", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else if (obj_session[8].toString().equals("YES")) {
                                request.setAttribute("IdUser", obj_session[0]);
                                request.setAttribute("Change_Password", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            } else {
                                session.setAttribute("idUsuario", obj_session[0]);
                                session.setAttribute("Nombres", obj_session[1]);
                                session.setAttribute("Rol/Nombres", obj_session[7] + "/" + obj_session[1]);
                                session.setAttribute("Documento", obj_session[2]);
                                session.setAttribute("Usuario", obj_session[4]);
                                session.setAttribute("idRol", obj_session[6]);
                                session.setAttribute("NombreRol", obj_session[7]);
                                session.setAttribute("Nombre", obj_session[9]);
                                session.setAttribute("Apellido", obj_session[10]);
                                session.setAttribute("Permisos", obj_session[14]);
                                session.setAttribute("Estado", obj_session[11]);
                                request.setAttribute("welcome", true);
                                request.getRequestDispatcher("Start?opc=1").forward(request, response);
                            }
                        }
                    }
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="RESTORE PASSWORD">
                    IdUser = Integer.parseInt(request.getParameter("IdUser"));
                    password = request.getParameter("Txt_password");
                    passwordEncrypt = md5.md5(password);
                    result = UserJpa.RestorePassUserSession(IdUser, passwordEncrypt);
                    request.setAttribute("Update_password", result);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="START AND END DATE">
                    StartDate = request.getParameter("startDate");
                    EndDate = request.getParameter("endDate");
                    session.setAttribute("StartDate", StartDate);
                    session.setAttribute("EndDate", EndDate);
                    request.getRequestDispatcher("Start.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="LOGIN OF CUSTOMER">
//                    int docx = Integer.parseInt(request.getParameter("txtDocument").toString());
                    int docx = 1001345764;
//                    int codx = Integer.parseInt(request.getParameter("txtCode").toString());
                    int codx = 4698;
                    lst_user = UserJpa.ConsultUserReporter(docx, codx);

                    if (lst_user == null) {
                        lst_user = OtherBd.Consultar_SIRH(docx, codx);
                        if (lst_user != null && lst_user.size() > 0 && !lst_user.isEmpty()) {
                            String[] Objuser = lst_user.toString().replace("[", "").replace("]", "").split("///");
                            String names = Objuser[0].toString();
                            int docux = Integer.parseInt(Objuser[1].toString());
                            int codex = Integer.parseInt(Objuser[2].toString());
                            String area = Objuser[3].toString();
                            request.setAttribute("Names", names);
                            request.setAttribute("Documento", docux);
                            request.setAttribute("CodeUser", codex);
                            request.setAttribute("AreaName", area);
                            request.setAttribute("NewUserReporter", true);
                            request.getRequestDispatcher("Customer.jsp").forward(request, response);
                        } else {
                            request.setAttribute("Non_existent_user", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    } else {
                        Object[] obj_session = (Object[]) lst_user.get(0);
                        session.setAttribute("idUsuario", obj_session[0]);
                        session.setAttribute("Nombres", obj_session[2]);
                        session.setAttribute("Documento", obj_session[3]);
                        session.setAttribute("Codigo", obj_session[4]);
                        request.setAttribute("welcome", true);
                        request.getRequestDispatcher("Customer?opt=1").forward(request, response);
                    }
//            }
//</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="RESET PASSWORD">
                    document = Integer.parseInt(request.getParameter("Txt_document"));
                    user = request.getParameter("Txt_user");
                    mail = request.getParameter("Txt_mail");
                    lst_userRest = UserJpa.ConsultUsersRestarPassword(document, user);
                    if (lst_userRest != null) {
                        Object[] obj_user = (Object[]) lst_userRest.get(0);
                        int currentYear = LocalDate.now().getYear();
                        String name = obj_user[1].toString() + " " + obj_user[2].toString();
                        IdUser = Integer.parseInt(obj_user[0].toString());
                        result = UserJpa.RestorePassUser(IdUser);
                        Mail_Session mailer = new Mail_Session();
                        mailer.RememeberPassword(name, currentYear, mail, getServletContext());
                        request.setAttribute("Mail_Reset_Pass", result);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Unidentified_User", true);
                        request.getRequestDispatcher("Start?opc=1").forward(request, response);
                    }
                    //</editor-fold>
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("Leave.jsp").forward(request, response);
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
