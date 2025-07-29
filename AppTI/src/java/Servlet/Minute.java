package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.MinuteJpaController;
import Controller.FormatControllerJpa;
import java.util.List;

import SQL.ConnectionsBd;

public class Minute extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        MinuteJpaController MinuteJpa = new MinuteJpaController();
        FormatControllerJpa FormaJpa = new FormatControllerJpa();
        ConnectionsBd SignatureJpa = new ConnectionsBd();
        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        int opt = Integer.parseInt(request.getParameter("opt"));
        int idMinu = 0, state = 0, event = 0, idUserReg = 0, idState = 0, flt = 0, temp = 0, docx = 0, codx = 0, idSig = 0;
        String matter = "", staff = "", cont = "", date = "", content = "", idDoc = "", signature = "";
        boolean result = false;
        List dataResult = null;
        List lst_signature = null;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                    try {
                        idMinu = Integer.parseInt(request.getParameter("idMinu"));
                    } catch (Exception e) {
                        idMinu = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        flt = Integer.parseInt(request.getParameter("flt"));
                    } catch (Exception e) {
                        flt = 0;
                    }
                    try {
                        event = Integer.parseInt(request.getParameter("event"));
                    } catch (Exception e) {
                        event = 0;
                    }
                    try {
                        docx = Integer.parseInt(request.getParameter("docx"));
                    } catch (Exception e) {
                        docx = 0;
                    }
                    try {
                        codx = Integer.parseInt(request.getParameter("codx"));
                    } catch (Exception e) {
                        codx = 0;
                    }

                    date = request.getParameter("txtDate");
                    matter = request.getParameter("txtMatter");
                    staff = request.getParameter("txtStaff");
                    content = request.getParameter("txtcontent");
                    try {
                        idUserReg = Integer.parseInt(request.getParameter("cbxUser"));
                    } catch (Exception e) {
                        idUserReg = 0;
                    }
                    try {
                        idState = Integer.parseInt(request.getParameter("cbxState"));
                    } catch (Exception e) {
                        idState = 0;
                    }

                    request.setAttribute("idMinu", idMinu);
                    request.setAttribute("dataResult", dataResult);
                    request.setAttribute("event", event);

                    request.setAttribute("flt", flt);

                    request.setAttribute("f_date", date);
                    request.setAttribute("f_matter", matter);
                    request.setAttribute("f_staff", staff);
                    request.setAttribute("f_content", content);
                    request.setAttribute("f_idUserReg", idUserReg);
                    request.setAttribute("f_idState", idState);

                    request.setAttribute("temp", temp);
                    request.setAttribute("docx", docx);
                    request.setAttribute("codx", codx);

                    request.getRequestDispatcher("minute.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT">
                    try {
                        idMinu = Integer.parseInt(request.getParameter("idMinu"));
                    } catch (Exception e) {
                        idMinu = 0;
                    }
                    date = request.getParameter("txtDate");
                    matter = request.getParameter("txtMatter");
                    staff = request.getParameter("txtStaff");

                    if (idMinu > 0) {
                        result = MinuteJpa.UpdateMinute(idMinu, matter, date, staff, idUser);
                        request.setAttribute("updateMinute", result);
                    } else {
                        List lst_format = FormaJpa.ConsultFormatName("R-TI-014");
                        String idDocx = "";
                        if (lst_format != null) {
                            Object[] ObjForm = (Object[]) lst_format.get(0);
                            idDocx = "[" + ObjForm[0].toString() + "]";
                        } else {
                            idDocx = "[5]";
                        }
                        result = MinuteJpa.MinuteRegister(matter, date, staff, idDocx, idUser);
                        request.setAttribute("registerMinute", result);
                    }
                    request.getRequestDispatcher("Minute?opt=1&idMinu=" + idMinu + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                    try {
                        idMinu = Integer.parseInt(request.getParameter("idMinu"));
                    } catch (Exception e) {
                        idMinu = 0;
                    }
                    try {
                        idSig = Integer.parseInt(request.getParameter("idSig"));
                    } catch (Exception e) {
                        idSig = 0;
                    }
                    String[] People = {};
                    List lst_minute = MinuteJpa.ConsultMinuteId(idMinu);
                    if (lst_minute != null) {
                        Object[] ObjMin = (Object[]) lst_minute.get(0);
                        People = ObjMin[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    } else {
                        result = false;
                    }
                    String staff_rw = "";
                    docx = Integer.parseInt(request.getParameter("docx"));
                    codx = Integer.parseInt(request.getParameter("codx"));

                    if (idSig == 0) {
                        signature = request.getParameter("txtSignature").replace("[[", "[").replace("]]", "]");
                        result = SignatureJpa.New_signature(docx, codx, signature);
                        lst_signature = SignatureJpa.Consultar_firmasDoc(docx, codx);
                        if (lst_signature.size() > 0) {
                            String[] DataSign = lst_signature.toString().replace("[", "").replace("]", "").split("///");
                            idSig = Integer.parseInt(DataSign[0].toString());
                        }
                        request.setAttribute("SignatureNew", result);
                    }
                    
                    int contSig = 0;
                    for (int i = 0; i < People.length; i++) {
                        String[] dat = People[i].toString().split(" / ");
                        int tempdoc = Integer.parseInt(dat[2].toString().trim());
                        if (tempdoc == docx) {
                            staff_rw += "[" + People[i].replace("XX", idSig + "") + "]";
                        } else {
                            staff_rw += "[" + People[i] + "]";
                            if (People[i].toString().contains("XX")) {
                                contSig++;
                            }
                        }
                    }
                    result = MinuteJpa.UpdateMinuteStaff(idMinu, staff_rw);
                    if (result && contSig == 0) {
                        result = MinuteJpa.UpdateMinuteState(idMinu, 3);
                        request.setAttribute("ChangueStateMinute", result);
                    }
                    request.setAttribute("SignatureOk", result);
                    request.getRequestDispatcher("Minute?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE CONTENT DATA">
                    try {
                        idMinu = Integer.parseInt(request.getParameter("idMinu"));
                    } catch (Exception e) {
                        idMinu = 0;
                    }
                    cont = request.getParameter("txtCont");
                    idDoc = request.getParameter("txtIdDoc");
                    cont = idDoc + "[" + cont + "]";
                    result = MinuteJpa.UpdateMinuteContent(idMinu, 2, cont, idUser);
                    request.setAttribute("UpdateContentDoc", result);
                    request.getRequestDispatcher("Minute?opt=1").forward(request, response);
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
