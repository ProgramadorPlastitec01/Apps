package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.CustomerControllerJpa;

public class Customer extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession sesion = request.getSession();

        CustomerControllerJpa CustomerJpa = new CustomerControllerJpa();
        int docUser = 0;
        String requestCus = "", pcDetail = "", ipPc = "", ext = "", opinion = "", names = "", mail = "", reason = "";
        int idArea = 0, idCustom = 0, priority = 0, typeTicket = 0, idTicket = 0, temp = 0, pcStop = 0, prodStop = 0, score = 0, idArx = 0, doc = 0, codx = 0;
        boolean result = false;
        try {
            docUser = Integer.parseInt(sesion.getAttribute("Documento").toString());
        } catch (Exception e) {
            docUser = 0;
        }
        int opt = Integer.parseInt(request.getParameter("opt"));
        int doctemp = 0;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE INITIAL">
                    if (docUser == 0) {
                        try {
                            doctemp = Integer.parseInt(request.getParameter("idDoct"));
                        } catch (Exception e) {
                            doctemp = 0;
                        }
                        docUser = doctemp;
                    }
                    try {
                        idTicket = Integer.parseInt(request.getParameter("idTicket"));
                    } catch (Exception e) {
                        idTicket = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    request.setAttribute("idTicket", idTicket);
                    request.setAttribute("temp", temp);
                    request.setAttribute("DocUser", docUser);
                    request.getRequestDispatcher("Customer.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER TICKET">
                    try {
                        idTicket = Integer.parseInt(request.getParameter("idTicket"));
                    } catch (Exception e) {
                        idTicket = 0;
                    }
                    priority = Integer.parseInt(request.getParameter("txtPriority").toString());
                    typeTicket = Integer.parseInt(request.getParameter("txtTypeSpt").toString());
                    ipPc = request.getParameter("txtPcCustomer").toString();
                    if (typeTicket == 1) {
                        if (ipPc.contains("172.16")) {
                            pcDetail = "0";
                        } else {
                            pcDetail = ipPc;
                        }
                    } else if (typeTicket == 2) {
                        pcDetail = request.getParameter("selectApp").toString();
                    } else if (typeTicket == 3) {
                        pcDetail = request.getParameter("selectPc").toString();
                    }
                    requestCus = request.getParameter("txtRequest").toString();
                    if (idTicket > 0) {
                        result = CustomerJpa.ModifyTicket(idTicket, requestCus, priority, typeTicket, pcDetail);
                        request.setAttribute("EditTicket", result);
                    } else {
                        idArea = Integer.parseInt(request.getParameter("NmbIdArea").toString());
                        idCustom = Integer.parseInt(request.getParameter("NmbIdUser").toString());
                        ext = request.getParameter("nmbExt").toString();
                        ext = "<i><b>Numero o Ext: </b>" + ext + "</i><br>";
                        requestCus = ext + requestCus;
                        result = CustomerJpa.NewTicketComplete(idArea, idCustom, requestCus, priority, typeTicket, pcDetail, ipPc);
                        request.setAttribute("NewTicket", result);
                    }
                    request.getRequestDispatcher("Customer?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="STARTING TICKET">
                    try {
                        idTicket = Integer.parseInt(request.getParameter("idTicket"));
                    } catch (Exception e) {
                        idTicket = 0;
                    }
                    pcStop = Integer.parseInt(request.getParameter("NmbPc"));
                    prodStop = Integer.parseInt(request.getParameter("NmbProd"));
                    score = Integer.parseInt(request.getParameter("rating"));
                    opinion = request.getParameter("txtOpinion");
                    result = CustomerJpa.StartingTicket(idTicket, pcStop, prodStop, score, opinion);
                    request.setAttribute("RatingTicket", result);
                    request.getRequestDispatcher("Customer?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="NEW REPORTER">
                    try {
                        idArx = Integer.parseInt(request.getParameter("cbxArea"));
                    } catch (Exception e) {
                        idArx = 0;
                    }
                    try {
                        names = request.getParameter("txtNames");
                    } catch (Exception e) {
                        names = "";
                    }
                    try {
                        doc = Integer.parseInt(request.getParameter("nmbDoc"));
                    } catch (Exception e) {
                        doc = 0;
                    }
                    try {
                        codx = Integer.parseInt(request.getParameter("nmbCod"));
                    } catch (Exception e) {
                        codx = 0;
                    }
                    try {
                        mail = request.getParameter("txtMail");
                    } catch (Exception e) {
                        mail = "";
                    }
                    docUser = doc;
                    result = CustomerJpa.NewCustomer(idArx, names, doc, codx, mail);
                    request.setAttribute("NewReporter", result);
                    request.getRequestDispatcher("Customer?opt=1&idDoct=" + doc + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="RE OPEN TICKET">
                    try {
                        idTicket = Integer.parseInt(request.getParameter("idTicket"));
                    } catch (Exception e) {
                        idTicket = 0;
                    }
                    reason = request.getParameter("txt_reason");
                    result = CustomerJpa.ReOpenTicket(idTicket, reason);
                    
                    request.setAttribute("reOpening", result);
                    request.getRequestDispatcher("Customer?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
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
