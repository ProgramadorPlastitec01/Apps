package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import Controller.FormatControllerJpa;
import Controller.AppDetailControllerJpa;
import Controller.AppHeaderControllerJpa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controller.SettingControllerJpa;
import Controller.AppControllerJpa;

import SQL.ConnectionsBd;
import javax.servlet.http.HttpSession;

public class AppDetail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        AppDetailControllerJpa AppDetail = new AppDetailControllerJpa();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        AppHeaderControllerJpa AppHeader = new AppHeaderControllerJpa();
        ConnectionsBd SignatureJpa = new ConnectionsBd();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        AppControllerJpa AppJpa = new AppControllerJpa();

        String UserRol = sesion.getAttribute("idRol").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int module = 0, idApp = 0, idDoc = 0, swpt = 0, idHead = 0, idDet = 0, inCount = 0, docx = 0, codx = 0, idSignatue = 0, step = 0;
        String date = "", affair = "", personal = "", content = "", format = "", type = "", typeVers = "",
                areasUx = "", rols = "", requer = "", moveX = "", moveY = "", moveJ = "", ara = "", dteSol = "", dteEjec = "",
                devlop = "", dteCap = "", Obs = "", finalvers = "", newRols = "", newAreas = "", cobianBD = "", cobiaFls = "",
                signature = "", sustance = "";
        boolean result = false;

        List lst_format = null;
        List lst_signature = null;
        List lst_setting = null;
        List lst_app = null;

        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        module = Integer.parseInt(request.getParameter("mod"));
                    } catch (Exception e) {
                        module = 0;
                    }
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }
                    try {
                        swpt = Integer.parseInt(request.getParameter("swpt"));
                    } catch (Exception e) {
                        swpt = 0;
                    }
                    try {
                        idDet = Integer.parseInt(request.getParameter("idDet"));
                    } catch (Exception e) {
                        idDet = 0;
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
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        step = Integer.parseInt(request.getParameter("step"));
                    } catch (Exception e) {
                        step = 0;
                    }
                    request.setAttribute("mod", module);
                    request.setAttribute("idApp", idApp);
                    request.setAttribute("idDoc", idDoc);
                    request.setAttribute("idHead", idHead);
                    request.setAttribute("swpt", swpt);
                    request.setAttribute("idDet", idDet);
                    request.setAttribute("docx", docx);
                    request.setAttribute("codx", codx);
                    request.setAttribute("type", type);
                    request.setAttribute("step", step);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("appDetail.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER DATA 14">
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idDet = Integer.parseInt(request.getParameter("idDet"));
                    } catch (Exception e) {
                        idDet = 0;
                    }
                    try {
                        step = Integer.parseInt(request.getParameter("step"));
                    } catch (Exception e) {
                        step = 0;
                    }
                    date = request.getParameter("txtDate");
                    affair = request.getParameter("txtAffair");
                    personal = request.getParameter("txtPersonal");
                    content = request.getParameter("txtContent");

                    format = "[" + affair + "][" + content + "]";
                    if (idDet == 0) {
                        result = AppDetail.NewDocumentByHead(idHead, date, type, format, personal, "ADMIN");
                        request.setAttribute("RegisterDocument14", result);
                    } else {
                        result = AppDetail.UpdateDocument(idDet, date, format, personal, 1, "ADMIN");
                        request.setAttribute("UpdateDocument14", result);
                    }

                    request.getRequestDispatcher("AppDetail?opt=1&mod=3&swpt=0&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGSITER HEADER">
                    try {
                        module = Integer.parseInt(request.getParameter("mod"));
                    } catch (Exception e) {
                        module = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    typeVers = request.getParameter("CbxApp");
                    result = AppHeader.registerAppHeader(idApp, typeVers, "ADMIN");
                    request.setAttribute("registerAppHeader", result);
                    request.getRequestDispatcher("AppDetail?opt=1&mod=" + module + "&idApp=" + idApp + "&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATE">
                    try {
                        module = Integer.parseInt(request.getParameter("mod"));
                    } catch (Exception e) {
                        module = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }
                    try {
                        step = Integer.parseInt(request.getParameter("step"));
                    } catch (Exception e) {
                        step = 0;
                    }
                    result = AppHeader.UpdateStateHead(idHead, step);
                    request.setAttribute("UpdateStateHead", result);
                    request.getRequestDispatcher("AppDetail?opt=1&mod=" + module + "&idApp=" + idApp + "&idHead=" + idHead + "").forward(request, response);

                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER ANNEXES">
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idDet = Integer.parseInt(request.getParameter("idDet"));
                    } catch (Exception e) {
                        idDet = 0;
                    }

                    content = request.getParameter("txtProtoData");
                    if (idDet > 0) {
                        result = AppDetail.updateDetailAnnexes(idDet, content, "ADMIN");
                        request.setAttribute("UpdateDetailAnnexes", result);
                    } else {
                        LocalDate fechaActual = LocalDate.now();
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String fechaFormateada = fechaActual.format(formato);
                        result = AppDetail.registerDetailAnnexes(idHead, fechaFormateada, type, content, 1, "ADMIN");
                        request.setAttribute("RegisterDetailAnnexes", result);
                    }

                    request.getRequestDispatcher("AppDetail?opt=1&mod=3&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER DATA 33">
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idDet = Integer.parseInt(request.getParameter("idDet"));
                    } catch (Exception e) {
                        idDet = 0;
                    }

                    LocalDate fechaActual = LocalDate.now();
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateAct = fechaActual.format(formato);

                    areasUx = request.getParameter("txtAreasUx");
                    rols = request.getParameter("txtRols");
                    inCount = Integer.parseInt(request.getParameter("inCouter"));

                    String requeri = "";
                    for (int i = 0; i <= inCount; i++) {
                        requer = request.getParameter("txtRequi_" + i + "");
                        moveX = request.getParameter("txtMoveX_" + i + "");
                        moveY = request.getParameter("txtMoveY_" + i + "");
                        moveJ = request.getParameter("txtMoveJ_" + i + "");
                        ara = request.getParameter("txtArea_" + i + "");
                        dteSol = request.getParameter("txtDateSol_" + i + "");
                        dteEjec = request.getParameter("txtDateEjec_" + i + "");
                        if (i == inCount) {
                            requeri += "" + requer + "/" + moveX + "/" + moveY + "/" + moveJ + "/" + ara + "/" + dteSol + "/" + dteEjec;
                        } else {
                            requeri += "" + requer + "/" + moveX + "/" + moveY + "/" + moveJ + "/" + ara + "/" + dteSol + "/" + dteEjec + "---";
                        }
                    }

                    devlop = request.getParameter("CbxDev");
                    personal = request.getParameter("txtPersonal");

                    //<editor-fold defaultstate="collapsed" desc="DEVELOPER / DIRECTOR TI / RESPONSE APP">
                    String prev_dev = "";
                    lst_app = AppDetail.ConsultAppDetailId(idDet);
                    try {
                        if (lst_app != null) {
                            Object[] ObjDev = (Object[]) lst_app.get(0);
                            String[] data_file = ObjDev[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            prev_dev = data_file[3].split(" / ")[2].trim();
                        }
                    } catch (Exception e) {
                        prev_dev = "";
                    }

                    String dir_doc = "";
                    String dir_data = "";
                    lst_setting = SettingJpa.ConsultSettingCategorie("AproveDirectory");
                    if (lst_setting != null) {
                        Object[] ObjDir = (Object[]) lst_setting.get(0);
                        dir_doc = ObjDir[2].toString().split(" / ")[2].trim();
                        dir_data = ObjDir[2].toString().replace("[", "").replace("]", "");
                    }
                    String resp_doc = "";
                    String resp_data = "";
                    lst_app = AppJpa.ConsultAppId(idApp);
                    if (lst_app != null) {
                        Object[] ObjApp = (Object[]) lst_app.get(0);
                        resp_doc = ObjApp[2].toString().split(" / ")[2].trim();
                        resp_data = ObjApp[2].toString().replace("[", "").replace("]", "");
                    }
                    //</editor-fold>

                    String[] recapPerson = personal.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    String personRecap = "";
                    for (int i = 0; i < recapPerson.length; i++) {
                        String[] detail = recapPerson[i].toString().split(" / ");
                        String docTemp = detail[2].toString().trim();
                        if (!docTemp.equals(prev_dev) && !docTemp.equals(dir_doc) && !docTemp.equals(resp_doc)) {
                            personRecap += "[" + recapPerson[i] + "]";
                        }
                    }
                    personRecap += "[" + devlop + "][" + dir_data + "][" + resp_data + "]";
                    dteCap = request.getParameter("TxtDateCapac");
                    Obs = request.getParameter("TxtObservations");
                    finalvers = request.getParameter("txtFinalVers");
                    if (!finalvers.equals("")) {
                        AppHeader.UpdateVersionHead(idHead, finalvers);
                    }
                    newRols = request.getParameter("TxtNewRole");
                    newAreas = request.getParameter("TxtNewArea");
                    cobianBD = request.getParameter("TxtCobianBD");
                    cobiaFls = request.getParameter("TxtCobianFile");
                    content = "[" + areasUx + "][" + rols + "][" + requeri + "][" + devlop + "][" + dteCap + "][" + Obs + "][" + finalvers + "][" + newRols + "][" + newAreas + "][" + cobianBD + "][" + cobiaFls + "]";
                    if (idDet > 0) {
                        result = AppDetail.UpdateDocument33(idDet, content, personRecap, "ADMIN");
                        request.setAttribute("UpdateDocument33", result);
                    } else {
                        result = AppDetail.registerDocument33(idHead, dateAct, type, content, personRecap, "ADMIN");
                        request.setAttribute("RegisterDocument33", result);
                    }
                    request.getRequestDispatcher("AppDetail?opt=1&mod=3&swpt=0&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="SGINATURE MODULE">
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }
                    try {
                        type = request.getParameter("type");
                    } catch (Exception e) {
                        type = "";
                    }
                    try {
                        idDet = Integer.parseInt(request.getParameter("idDet"));
                    } catch (Exception e) {
                        idDet = 0;
                    }
                    try {
                        idSignatue = Integer.parseInt(request.getParameter("idSignature"));
                    } catch (Exception e) {
                        idSignatue = 0;
                    }
                    String[] pers = {};
                    List lst_appDetail = AppDetail.ConsultAppDetailId(idDet);
                    if (lst_appDetail != null) {
                        Object[] ObjPers = (Object[]) lst_appDetail.get(0);
                        pers = ObjPers[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    } else {
                        result = false;
                    }
                    String asis = "";
                    docx = Integer.parseInt(request.getParameter("docx"));
                    codx = Integer.parseInt(request.getParameter("codx"));
                    if (idSignatue == 0) {
                        signature = request.getParameter("txtSignature").replace("[[", "[").replace("]]", "]");
                        result = SignatureJpa.New_signature(docx, codx, signature);
                        lst_signature = SignatureJpa.Consultar_firmasDoc(docx, codx);
                        if (lst_signature.size() > 0) {
                            String[] DataSign = lst_signature.toString().replace("[", "").replace("]", "").split("///");
                            idSignatue = Integer.parseInt(DataSign[0].toString());
                        }
                        request.setAttribute("SignatureNew", result);
                    }
                    int contSig = 0;
                    for (int i = 0; i < pers.length; i++) {
                        String[] dat = pers[i].toString().split(" / ");
                        int tempdoc = Integer.parseInt(dat[2].toString().trim());
                        if (tempdoc == docx) {
                            asis += "[" + pers[i].replace("XX", idSignatue + "") + "]";
                        } else {
                            asis += "[" + pers[i] + "]";
                            if (pers[i].toString().contains("XX")) {
                                contSig++;
                            }
                        }
                    }
                    result = AppDetail.UpdateDetailPersonal(idDet, asis);
                    if (result && contSig == 0) {
                        AppDetail.UpdateDetailState(idDet);
                    }
                    request.setAttribute("SignatureOk", result);

                    request.getRequestDispatcher("AppDetail?opt=1&mod=3&swpt=2").forward(request, response);
//</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="CHECK ALL DOCUMENTS">
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    try {
                        idHead = Integer.parseInt(request.getParameter("idHead"));
                    } catch (Exception e) {
                        idHead = 0;
                    }

                    lst_app = AppDetail.ConsultAppDetailIdhead(idHead);
                    String message = "";
                    boolean validData = false;
                    if (lst_app != null) {
                        for (int i = 0; i < lst_app.size(); i++) {
                            Object[] ObjPerson = (Object[]) lst_app.get(i);
                            try {
                                String[] personx = ObjPerson[3].toString().replace("][", "").replace("[", "").replace("]", "").split(" / ");
                                String validSignat = personx[4].toString().trim();
                                if (validSignat.equals("XX")) {
                                    validData = true;
                                    message = ObjPerson[2].toString().split("/")[1];
                                    break;
                                }
                            } catch (Exception e) {
                            }
                        }
                    } else {
                        message = "No se ha encontrado información";
                    }
                    if (validData) {
                        request.setAttribute("Message", message);
                    } else {
                        result = AppJpa.UpdateHeadStateFinal(idHead);
                        request.setAttribute("UpdateFinal", result);
                    }
                    request.getRequestDispatcher("AppDetail?opt=1&mod=2&idApp=" + idApp + "&idHead=" + idHead + "&idDet=0").forward(request, response);
                    //</editor-fold>
                    break;

            }
        } catch (Exception e) {
            request.getRequestDispatcher("appDetail.jsp").forward(request, response);
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
