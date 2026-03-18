package Servlet;

import Metodos.Server_redeac;
import Metodos.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Soporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            //Sesion
            Email MailMethod = new Email();
            Server_redeac ConsulRedeac = new Server_redeac();
            PrintWriter out = response.getWriter();
            HttpSession sesion = request.getSession();
            
            List lst_mail = null;
            
            String names = "", mail = "", txtTecnicos = "", txtPriori = "", txt_case = "";
            String UserName = sesion.getAttribute("Nombre").toString();
            int NroDoc = Integer.parseInt(sesion.getAttribute("Documento").toString());
            int idUsuario = Integer.parseInt(sesion.getAttribute("id").toString());
            int opc = Integer.parseInt(request.getParameter("opc"));
            int document = 0, code = 0, idArea = 0,idSupport = 0;
            boolean result = false;

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE MAIN">
                    try {
                        idSupport = Integer.parseInt(request.getParameter("idSupport"));
                    } catch (Exception e) {
                        idSupport = 0;
                    }
                    request.setAttribute("idUsuario", idUsuario);
                    request.setAttribute("Documento", NroDoc);
                    request.setAttribute("NombreUser", UserName);
                    request.setAttribute("idSupport", idSupport);
                    request.getRequestDispatcher("Soporte.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER REPORTANT">
                    names = request.getParameter("Txt_names");
                    mail = request.getParameter("Txt_mail");
                    document = Integer.parseInt(request.getParameter("Txt_document"));
                    code = Integer.parseInt(request.getParameter("Txt_code"));
                    idArea = Integer.parseInt(request.getParameter("cbx_area"));

                    result = ConsulRedeac.RegisterReportant(names, mail, idArea, document, code);
                    request.setAttribute("RegisterRepotant", result);
                    request.getRequestDispatcher("Soporte?opc=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER CASE">
                    
                    idArea = Integer.parseInt(request.getParameter("idArea"));
                    txtTecnicos = request.getParameter("txtTecnicos");
                    idUsuario = Integer.parseInt(request.getParameter("idUser"));
                    txt_case = request.getParameter("txt_case");
                    txtPriori = request.getParameter("cbx_priori");
                    try {
                        
                    } catch (Exception e) {
                    }
                    result = ConsulRedeac.RegisterCase(idArea, txtTecnicos, idUsuario, txt_case, txtPriori);
                    if (result) {
                        lst_mail = ConsulRedeac.consul_caseMail(idArea, idUsuario);
                        for (int i = 0; i < lst_mail.size(); i++) {
                            String[] arr_case = lst_mail.toString().replace("[", "").replace("]", "").split("////");
                            for (int j = 0; j < arr_case.length; j++) {
                                Object[] obj_data = arr_case[i].toString().split("---");
                                MailMethod.SolicitudSoporte(obj_data[1].toString(), obj_data[3].toString(),  "APP Solicitudes Proyectos ->"+obj_data[4].toString(), obj_data[5].toString(), obj_data[6].toString(), obj_data[7].toString(), "SOLICITUD SOPORTE", Integer.parseInt(obj_data[0].toString()));
                                j = arr_case.length;
                            }
                        }
                    }
                    request.setAttribute("RegisterCase", result);
                    request.getRequestDispatcher("Soporte?opc=1").forward(request, response);
//</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Soporte.jsp").forward(request, response);
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
