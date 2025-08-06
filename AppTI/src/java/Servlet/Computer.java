package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.ComputerControllerJpa;
import Controller.ComputerDetailJpaController;
import Controller.ComputerHeaderControllerJpa;

import Controller.SettingControllerJpa;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Computer extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ComputerControllerJpa ComputerJpa = new ComputerControllerJpa();
        ComputerDetailJpaController CompDetailJpa = new ComputerDetailJpaController();
        ComputerHeaderControllerJpa CompHeader = new ComputerHeaderControllerJpa();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        HttpSession sesion = request.getSession();
        String UserRol = sesion.getAttribute("idRol").toString();

        List lst_setting = null;

        int opt = Integer.parseInt(request.getParameter("opt"));
        int IdComputer = 0, module = 0, StateCmp = 0, idPcHead = 0, step = 0, idDoc = 0, temp = 0, idDetail = 0, idItem = 0,
                docx = 0, codx = 0, idSign = 0, udpate004 = 0;
        String NumberPC = "", Mail = "", Description = "", typeDoc = "", dte_doc = "", type = "", DocFiles = "", SigMode = "", htmlTabla = "";
        boolean Result = false;

        try {
            IdComputer = Integer.parseInt(request.getParameter("IdComputer"));
        } catch (NumberFormatException e) {
            IdComputer = 0;
        }

        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE PC">
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        module = Integer.parseInt(request.getParameter("mod"));
                    } catch (NumberFormatException e) {
                        module = 0;
                    }
                    try {
                        StateCmp = Integer.parseInt(request.getParameter("StateCmp"));
                    } catch (NumberFormatException e) {
                        StateCmp = 99;
                    }
                    try {
                        step = Integer.parseInt(request.getParameter("step"));
                    } catch (Exception e) {
                        step = 0;
                    }
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idItem = Integer.parseInt(request.getParameter("cbxItem"));
                    } catch (Exception e) {
                        idItem = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        docx = Integer.parseInt(request.getParameter("NmbDoc"));
                    } catch (Exception e) {
                        docx = 0;
                    }
                    try {
                        codx = Integer.parseInt(request.getParameter("NmbCod"));
                    } catch (Exception e) {
                        codx = 0;
                    }
                    try {
                        SigMode = request.getParameter("txtSigMode");
                    } catch (Exception e) {
                        SigMode = "";
                    }
                    request.setAttribute("mod", module);
                    request.setAttribute("IdComputer", IdComputer);
                    request.setAttribute("idPcHead", idPcHead);
                    request.setAttribute("StateCmp", StateCmp);
                    request.setAttribute("step", step);
                    request.setAttribute("idDoc", idDoc);
                    request.setAttribute("type", type);
                    request.setAttribute("idItem", idItem);
                    request.setAttribute("NmbDoc", docx);
                    request.setAttribute("NmbCod", codx);
                    request.setAttribute("SigMode", SigMode);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("Computer.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE PC">
                    try {
                        StateCmp = Integer.parseInt(request.getParameter("StateCmp"));
                    } catch (NumberFormatException e) {
                        StateCmp = 99;
                    }
                    NumberPC = request.getParameter("txtPC");
                    Mail = request.getParameter("txtMail");
                    Description = request.getParameter("txtDescription");
                    if (IdComputer > 0) {
                        Result = ComputerJpa.UpdateComputer(IdComputer, NumberPC, Mail, Description);
                        request.setAttribute("ComputerUpdate", Result);
                    } else {
                        Result = ComputerJpa.RegisterComputer(NumberPC, Mail, Description);
                        request.setAttribute("ComputerRegister", Result);
                    }
                    request.getRequestDispatcher("Computer?opt=1&IdComputer=0&StateCmp=" + StateCmp + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER HEADER">
                    try {
                        module = Integer.parseInt(request.getParameter("mod"));
                    } catch (NumberFormatException e) {
                        module = 0;
                    }
                    typeDoc = request.getParameter("CbxPcType");
                    dte_doc = request.getParameter("txtDte");

                    Result = CompDetailJpa.registerPcHeader(IdComputer, dte_doc, typeDoc, "Admin");
                    request.setAttribute("registerPcHeader", Result);
                    request.getRequestDispatcher("Computer?opt=1&mod=" + module + "&IdComputer=" + IdComputer + "&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="UPLOAD FILES">                    
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        idDetail = Integer.parseInt(request.getParameter("idDetail"));
                    } catch (NumberFormatException e) {
                        idDetail = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        DocFiles = request.getParameter("fileDocs");
                    } catch (Exception e) {
                        DocFiles = "";
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("xtemp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    if (temp == 1) {
                        Result = CompDetailJpa.UpdatePcDetailContent(idDetail, DocFiles, 1);
                        request.setAttribute("UploadFile_update", Result);
                    } else {
                        Result = CompDetailJpa.registerPcDetail(idPcHead, type, DocFiles, "N/A", 1, 0);
//                        Result = CompHeader.ChangueStateComputerHeader(idPcHead);
                        request.setAttribute("UploadFile_new", Result);
                    }
                    request.getRequestDispatcher("Computer?opt=1&mod=3&IdComputer=" + IdComputer + "&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATE">
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        module = Integer.parseInt(request.getParameter("mod"));
                    } catch (NumberFormatException e) {
                        module = 0;
                    }
                    try {
                        idDetail = Integer.parseInt(request.getParameter("idDetail"));
                    } catch (NumberFormatException e) {
                        idDetail = 0;
                    }
                    Result = CompHeader.ChangueStateComputerHeader(idPcHead);
                    if (Result) {
                        try {
                            temp = Integer.parseInt(request.getParameter("xtemp"));
                        } catch (Exception e) {
                            temp = 0;
                        }
                        if (temp == 1) {
                            Result = CompDetailJpa.ComputerDetailFinishState(idDetail);
                        }
                    }
                    request.setAttribute("ComputerState", Result);
                    request.getRequestDispatcher("Computer?opt=1&IdComputer=" + IdComputer + "&mod=" + module + "&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="ASIGN ITEM">
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }

                    idItem = Integer.parseInt(request.getParameter("cbxItem"));

                    Result = ComputerJpa.UpdateComputerItem(IdComputer, idItem);

                    if (Result) {
                        Result = CompDetailJpa.registerPcDetail(idPcHead, type, idItem + "", "N/A", 1, 1);
//                        Result = CompHeader.ChangueStateComputerHeader(idPcHead);
                    }

                    request.setAttribute("ComputerItem", Result);
                    request.getRequestDispatcher("Computer?opt=1&IdComputer=" + IdComputer + "&mod=3&idpcHead=" + idPcHead + "&type=" + type + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER ASIG">
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    String structure = "";
                    //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
                    String txt_post = "",
                     txt_area = "",
                     txt_location = "",
                     txt_bossname = "",
                     txt_name = "",
                     txt_indentity = "",
                     txt_place = "",
                     txt_user = "",
                     txt_day = "",
                     txt_month = "",
                     txt_anio = "",
                     txt_comm1 = "",
                     txt_comm2 = "",
                     txt_comm3 = "",
                     txt_comm4 = "",
                     txt_comm5 = "",
                     textcal = "",
                     textFll = "";

                    String txt_otherItem = "",
                     txt_soft = "";

                    int CounterSoftware = 0;

                    txt_post = "[" + request.getParameter("txt_post") + "]";
                    txt_area = "[" + request.getParameter("txt_area") + "]";
                    txt_location = "[" + request.getParameter("txt_location") + "]";
                    txt_bossname = "[" + request.getParameter("txt_bossname") + "]";
                    txt_name = "[" + request.getParameter("txt_name") + "]";
                    txt_indentity = "[" + request.getParameter("txt_indentity") + "]";
                    txt_place = "[" + request.getParameter("txt_place") + "]";
                    txt_user = "[" + request.getParameter("txt_user") + "]";
                    txt_day = "[" + request.getParameter("txt_day") + "]";
                    txt_month = "[" + request.getParameter("txt_month") + "]";
                    txt_anio = "[" + request.getParameter("txt_anio") + "]";
                    txt_comm1 = "[" + request.getParameter("txt_comm1") + "]";
                    txt_comm2 = "[" + request.getParameter("txt_comm2") + "]";
                    txt_comm3 = "[" + request.getParameter("txt_comm3") + "]";
                    txt_comm4 = "[" + request.getParameter("txt_comm4") + "]";
                    txt_comm5 = "[" + request.getParameter("txt_comm5") + "]";
                    textcal = "[" + request.getParameter("textcal") + "]";
                    textFll = "[" + request.getParameter("textFll") + "]";
                    //</editor-fold>

                    try {
                        txt_otherItem = request.getParameter("txt_otherItem").replace("] [", ",");
                    } catch (Exception e) {
                        txt_otherItem = "[NoN]";
                    }
                    try {
                        txt_soft = request.getParameter("txt_soft").replace("] [", "---");
                    } catch (Exception e) {
                        txt_soft = "[NoN]";
                    }

                    structure = txt_otherItem + txt_soft + txt_post + txt_area + txt_location + txt_bossname + txt_name + txt_indentity + txt_place + txt_user
                            + txt_day + txt_month + txt_anio + txt_comm1 + txt_comm2 + txt_comm3 + txt_comm4 + txt_comm5 + textcal + textFll;

                    lst_setting = SettingJpa.ConsultSettingCategorie("DocSig003");
                    String respo = "";
                    if (lst_setting != null) {
                        if (lst_setting != null) {
                            Object[] ObjSett = (Object[]) lst_setting.get(0);
                            respo = ObjSett[2].toString();
                        } else {
                            respo = "[NoN]";
                        }
                    }
                    Result = CompDetailJpa.registerPcDetail(idPcHead, type, structure, respo, 1, 0);
                    request.setAttribute("Register003", Result);
                    request.getRequestDispatcher("Computer?opt=1&IdComputer=" + IdComputer + "&mod=3&idpcHead=" + idPcHead + "&type=" + type + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE DOCUMENT">
                    try {
                        idDetail = Integer.parseInt(request.getParameter("idDetail"));
                    } catch (NumberFormatException e) {
                        idDetail = 0;
                    }
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idSign = Integer.parseInt(request.getParameter("idSignature"));
                    } catch (Exception e) {
                        idSign = 0;
                    }
                    try {
                        docx = Integer.parseInt(request.getParameter("NmbDoc"));
                    } catch (Exception e) {
                        docx = 0;
                    }
                    try {
                        codx = Integer.parseInt(request.getParameter("NmbCod"));
                    } catch (Exception e) {
                        codx = 0;
                    }
                    try {
                        SigMode = request.getParameter("SigMode");
                    } catch (Exception e) {
                        SigMode = "";
                    }

                    String Signature = "";

                    List lst_detail = CompDetailJpa.ConsultComputerDetailxid(idDetail);
                    if (lst_detail != null) {
                        Object[] ObjDet = (Object[]) lst_detail.get(0);
                        Signature = ObjDet[5].toString().replace(SigMode + "/XX", SigMode + "/" + idSign);
                    }
                    if (!Signature.contains("/XX")) {
                        Result = CompDetailJpa.ComputerSignaturxe(idDetail, Signature, 1);
                    } else {
                        Result = CompDetailJpa.ComputerSignaturxe(idDetail, Signature, 0);
                    }

                    request.setAttribute("SignatureRegs", Result);
                    request.getRequestDispatcher("Computer?opt=1&IdComputer=" + IdComputer + "&mod=3&idpcHead=" + idPcHead + "&type=" + type + "&NmbDoc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER PREVENTIVE MAINTENANCE">
                    try {
                        idPcHead = Integer.parseInt(request.getParameter("idpcHead"));
                    } catch (NumberFormatException e) {
                        idPcHead = 0;
                    }
                    try {
                        idDetail = Integer.parseInt(request.getParameter("idDetail"));
                    } catch (NumberFormatException e) {
                        idDetail = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        htmlTabla = request.getParameter("htmlTabla");
                        if (!htmlTabla.contains("<div id='idtabla'>")) {
                            htmlTabla = "<div id=\"idtabla\">" + htmlTabla + "</div>";
                        }
                    } catch (Exception e) {
                        htmlTabla = "<div id=\"idtabla\">" + htmlTabla + "</div>";
                    }
                    
                    lst_setting = SettingJpa.ConsultSettingCategorie("DocSig004");
                    respo = "";
                    if (lst_setting != null) {
                        if (lst_setting != null) {
                            Object[] ObjSett = (Object[]) lst_setting.get(0);
                            respo = ObjSett[2].toString();
                        } else {
                            respo = "";
                        }
                    }

                    if (idDetail > 0) {
                        Result = CompDetailJpa.UpdatePcDetailContent(idDetail, htmlTabla, 0);
                    } else {
                        Result = CompDetailJpa.registerPcDetail(idPcHead, type, htmlTabla, respo, 1, 0);
                    }
                    request.setAttribute("PreventiveMain", opt);
                    request.getRequestDispatcher("Computer?opt=1&IdComputer=" + IdComputer + "&mod=3&idpcHead=" + idPcHead + "&type=" + type + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Computer.jsp").forward(request, response);
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
