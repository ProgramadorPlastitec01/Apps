package Servlet;

import Controller.DeviceDetailJpaController;
import Controller.UserControllerJpa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.DeviceJpaController;
import Controller.DeviceHeaderJpaController;
import Controller.SettingControllerJpa;
import java.util.List;

public class Device extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        DeviceJpaController DeviceJpa = new DeviceJpaController();
        DeviceHeaderJpaController DeviceHeaderJpa = new DeviceHeaderJpaController();
        DeviceDetailJpaController DeviceDetailJpa = new DeviceDetailJpaController();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();

        List lst_setting = null;

        HttpSession sesion = request.getSession();
        int id_user = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String name_user = sesion.getAttribute("Nombres").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int idTypeDv = 0, steDv = 0, act = 0, itm = 0, consect = 0, idArea = 0, idDevice = 0, idDeviceHead = 0, idDeviceDetail = 0, temp = 0, idItem = 0,
                idSign = 0, docx = 0, codx = 0, idDoc = 0;
        String chargue = "", respon = "", nameDv = "", serial = "", location = "", date = "", Structure = "";
        String NumberPC = "", Mail = "", Description = "", typeDoc = "", dte_doc = "", type = "", DocFiles = "", SigMode = "", htmlTabla = "", DocCode = "", xtemp = "";
        boolean result = false;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        act = Integer.parseInt(request.getParameter("act"));
                    } catch (Exception e) {
                        act = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        steDv = Integer.parseInt(request.getParameter("steDv"));
                    } catch (Exception e) {
                        steDv = 0;
                    }
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (Exception e) {
                        idDevice = 0;
                    }
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (Exception e) {
                        idDeviceHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idItem = Integer.parseInt(request.getParameter("cbxItem"));
                    } catch (Exception e) {
                        idItem = 0;
                    }
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
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
                    request.setAttribute("act", act);
                    request.setAttribute("idTypeDv", idTypeDv);
                    request.setAttribute("steDv", steDv);
                    request.setAttribute("idDevice", idDevice);
                    request.setAttribute("idDeviceHead", idDeviceHead);
                    request.setAttribute("type", type);
                    request.setAttribute("idItem", idItem);
                    request.setAttribute("idDoc", idDoc);
                    request.setAttribute("NmbDoc", docx);
                    request.setAttribute("NmbCod", codx);
                    request.setAttribute("SigMode", SigMode);
                    request.getRequestDispatcher("Device.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER DEVICE">
                    try {
                        act = Integer.parseInt(request.getParameter("act"));
                    } catch (Exception e) {
                        act = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        consect = Integer.parseInt(request.getParameter("nmb_consec"));
                    } catch (Exception e) {
                        consect = 0;
                    }

                    try {
                        nameDv = request.getParameter("txt_name");
                    } catch (Exception e) {
                        nameDv = "";
                    }
                    try {
                        chargue = request.getParameter("txt_chargue");
                    } catch (Exception e) {
                        chargue = "";
                    }
                    try {
                        itm = Integer.parseInt(request.getParameter("cbx_Item"));
                    } catch (Exception e) {
                        itm = 0;
                    }
                    try {
                        respon = request.getParameter("txt_respo");
                    } catch (Exception e) {
                        respon = "";
                    }
                    try {
                        idArea = Integer.parseInt(request.getParameter("cbx_area"));
                    } catch (Exception e) {
                        idArea = 0;
                    }
                    try {
                        serial = request.getParameter("txt_serial");
                    } catch (Exception e) {
                        serial = "";
                    }
                    try {
                        location = request.getParameter("txt_location");
                    } catch (Exception e) {
                        location = "";
                    }

                    result = DeviceJpa.RegisterDevice(idTypeDv, consect, nameDv, itm, serial, chargue, respon, location, idArea, id_user);
                    request.setAttribute("RegisterDevice", result);
                    request.getRequestDispatcher("Device?opt=1&act=" + act + "&idTypeDv=" + idTypeDv + "").forward(request, response);
//</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER HEADER">
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (Exception e) {
                        idDevice = 0;
                    }

                    try {
                        date = request.getParameter("txtDte").toString();
                        Structure = request.getParameter("CbxDvType").toString();
                    } catch (Exception e) {
                        date = "";
                        Structure = "";
                    }

                    result = DeviceHeaderJpa.RegisterDeviceHeader(idDevice, date, Structure, name_user);
                    request.setAttribute("DeviceHeaderReg", result);

                    request.getRequestDispatcher("Device?opt=1&act=2&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="UPLOAD FILES">                    
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (NumberFormatException e) {
                        idDeviceHead = 0;
                    }
                    try {
                        idDeviceDetail = Integer.parseInt(request.getParameter("idDeviceDetail"));
                    } catch (NumberFormatException e) {
                        idDeviceDetail = 0;
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
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (Exception e) {
                        idDevice = 0;
                    }
                    if (temp == 1) {
                        result = DeviceDetailJpa.UpdateDeviceDetailContent(idDeviceDetail, DocFiles, id_user);
                        request.setAttribute("UploadFile_update", result);
                    } else {
                        result = DeviceDetailJpa.RegisterDeviceDetail(idDeviceHead, type, DocFiles, "N/A", id_user, 0);
                        request.setAttribute("UploadFile_new", result);
                    }
                    request.getRequestDispatcher("Device?opt=1&act=4&IdDevice=" + idDevice + "&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATE">
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (Exception e) {
                        idDevice = 0;
                    }
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (NumberFormatException e) {
                        idDeviceHead = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idDeviceDetail = Integer.parseInt(request.getParameter("idDeviceDetail"));
                    } catch (NumberFormatException e) {
                        idDeviceDetail = 0;
                    }
//                    result = CompHeader.ChangueStateComputerHeader(idDeviceHead);
                    result = DeviceHeaderJpa.ChangueStateDeviceHeader(idDeviceHead);
                    if (result) {
                        try {
                            xtemp = request.getParameter("xtemp");
                        } catch (Exception e) {
                            xtemp = "";
                        }
                        if (xtemp.equals("1")) {
                            result = DeviceDetailJpa.ChangeStateDeviceDetail(idDeviceDetail);
                        }
                    }
                    request.setAttribute("ComputerState", result);
                    request.getRequestDispatcher("Device?opt=1&idDevice=" + idDevice + "&idTypeDv=" + idTypeDv + "&act=3").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="ASIGN ITEM">
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (NumberFormatException e) {
                        idDeviceHead = 0;
                    }
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (Exception e) {
                        idDevice = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }

                    idItem = Integer.parseInt(request.getParameter("cbxItem"));

                    result = DeviceJpa.UpdateDeviceItem(idDevice, idItem);
                    if (result) {
                        result = DeviceDetailJpa.RegisterDeviceDetail(idDeviceHead, type, idItem + "", "N/A", id_user, 1);
                    }

                    request.setAttribute("ComputerItem", result);
                    request.getRequestDispatcher("Device?opt=1&IdDevice=" + idDevice + "&idTypeDv=" + idTypeDv + "&act=4&IdDeviceHead=" + idDeviceHead + "&type=" + type + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER ASIG">
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (NumberFormatException e) {
                        idDevice = 0;
                    }
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (NumberFormatException e) {
                        idDeviceHead = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
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
                    if (!textcal.equals("Si")) {
//                        CompHeader.DleeCalificationComputerHeader(idDeviceHead);
                        DeviceHeaderJpa.DeleteCalificationDeviceHead(idDeviceHead);
                    }
//                    result = CompDetailJpa.registerPcDetail(idDeviceHead, type, structure, respo, 1, 1);
                    result = DeviceDetailJpa.RegisterDeviceDetail(idDeviceHead, type, structure, respo, id_user, 1);
                    request.setAttribute("Register003", result);
                    request.getRequestDispatcher("Device?opt=1&idDevice=" + idDevice + "&act=4&idDeviceHead=" + idDeviceHead + "&type=" + type + "&idTypeDv=" + idTypeDv + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE DOCUMENT">
                    try {
                        idDeviceDetail = Integer.parseInt(request.getParameter("idDeviceDetail"));
                    } catch (NumberFormatException e) {
                        idDeviceDetail = 0;
                    }
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (NumberFormatException e) {
                        idDeviceHead = 0;
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
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }

                    String Signature = "";
                    List lst_detail = null;
//                    List lst_detail = DeviceDetailJpa.ConsultComputerDetailxid(idDetail);
                    lst_detail = DeviceDetailJpa.ConsultDeviceDetail_id(idDeviceDetail);
                    if (lst_detail != null) {
                        Object[] ObjDet = (Object[]) lst_detail.get(0);
                        Signature = ObjDet[5].toString().replace(SigMode + "/XX", SigMode + "/" + idSign);
                    }
                    result = DeviceDetailJpa.DeviceSignature(idDeviceDetail, Signature);

                    request.setAttribute("SignatureRegs", result);
                    request.getRequestDispatcher("Device?opt=1&idTypeDv=" + idTypeDv + "&IdDevice=" + idDevice + "&act=4&IdDeviceHead=" + idDeviceHead + "&type=" + type + "&NmbDoc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER PREVENTIVE MAINTENANCE">
                    try {
                        idDevice = Integer.parseInt(request.getParameter("idDevice"));
                    } catch (Exception e) {
                        idDevice = 0;
                    }
                    try {
                        idDeviceHead = Integer.parseInt(request.getParameter("idDeviceHead"));
                    } catch (NumberFormatException e) {
                        idDeviceHead = 0;
                    }
                    try {
                        idDeviceDetail = Integer.parseInt(request.getParameter("idDeviceDetail"));
                    } catch (NumberFormatException e) {
                        idDeviceDetail = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        textcal = request.getParameter("textcal");
                    } catch (Exception e) {
                        textcal = "";
                    }
                    try {
                        htmlTabla = request.getParameter("htmlTabla");
                        if (!htmlTabla.contains("<div id='idtabla'>")) {
                            htmlTabla = "<div id=\"idtabla\">" + htmlTabla + "</div>";
                        }
                    } catch (Exception e) {
                        htmlTabla = "<div id=\"idtabla\">" + htmlTabla + "</div>";
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }

                    if (idDeviceDetail > 0) {
//                        result = CompDetailJpa.UpdatePcDetailContent(idDetail, htmlTabla, 0);
                        result = DeviceDetailJpa.UpdateDeviceDetailContent(idDeviceDetail, htmlTabla, id_user);
                    } else {
                        try {
                            DocCode = request.getParameter("DocCode");
                        } catch (Exception e) {
                            DocCode = "";
                        }
                        lst_setting = SettingJpa.ConsultSettingCategorie("DocSig" + DocCode + "");
                        respo = "";
                        if (lst_setting != null) {
                            if (lst_setting != null) {
                                Object[] ObjSett = (Object[]) lst_setting.get(0);
                                respo = ObjSett[2].toString();
                            } else {
                                respo = "";
                            }
                        }
//                        result = CompDetailJpa.registerPcDetail(idDeviceHead, type, htmlTabla, respo, 1, 1);
                        result = DeviceDetailJpa.RegisterDeviceDetail(idDeviceHead, type, htmlTabla, respo, id_user, 1);
                    }
                    request.setAttribute("Register004_029", result);
                    request.getRequestDispatcher("Device?opt=1&IdDevice=" + idDevice + "&act=4&idTypeDv="+ idTypeDv +"&IdDeviceHead=" + idDeviceHead + "&type=" + type + "").forward(request, response);
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
