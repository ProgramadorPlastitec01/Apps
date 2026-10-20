package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controller.CertificatesJpaController;
import Controller.CertificateFileJpaController;
import Controller.CertificateDuplicationJpaController;
import Controller.EventsJpaController;
import Controller.UserControllerJpa;
import Method.Mail;

public class Generate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
            EventsJpaController EventConn = new EventsJpaController();
            Mail MailConn = new Mail();
            String RolName = session.getAttribute("Rol/Nombres").toString();
            String UserName = session.getAttribute("Nombres").toString();
            int IdRol = Integer.parseInt(session.getAttribute("idRol").toString());
            int opt = Integer.parseInt(request.getParameter("opt"));
            String Signature = "";
            try {
                Signature = session.getAttribute("Firma").toString();
            } catch (Exception e) {
                Signature = "";
            }
            int Order = 0, IdCertificates = 0, TempDelete = 0, TempM = 0, Temp = 0, StateCerti = 0, State = 0, StateM = 0, IdCertificate = 0, Anio = 0, CountReg = 0, AmoutReg = 0;
            String Type = "", Product = "", Batch = "", Html = "", Code = "", Consecutive = "", Customer = "", IdCertiMasive = "", Category = "",
                    Justification = "", Record = "", FormatName = "", Message = "", Amount = "", DateDispatch = "", MaterialBatches = "", Client = "",
                    NumberCertificate = "", DateI = "", DateF = "";
            boolean result = false;
            List lst_id = null;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="GENERAL">
                    try {
                        StateM = Integer.parseInt(request.getParameter("StateM"));
                    } catch (Exception e) {
                        StateM = 0;
                    }
                    try {
                        IdCertificate = Integer.parseInt(request.getParameter("IdCertificate"));
                    } catch (Exception e) {
                        IdCertificate = 0;
                    }
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    request.setAttribute("Type", Type);
                    request.setAttribute("StateM", StateM);
                    request.setAttribute("TempDelete", TempDelete);
                    request.setAttribute("IdCertificate", IdCertificate);
                    request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT FORMAT">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        Order = Integer.parseInt(request.getParameter("Order"));
                    } catch (Exception e) {
                        Order = 0;
                    }
                    try {
                        Product = request.getParameter("Product");
                    } catch (Exception e) {
                        Product = "";
                    }
                    try {
                        Batch = request.getParameter("Batch");
                    } catch (Exception e) {
                        Batch = "";
                    }
                    try {
                        FormatName = request.getParameter("FormatName");
                    } catch (Exception e) {
                        FormatName = "";
                    }
                    try {
                        Record = request.getParameter("Record");
                    } catch (Exception e) {
                        Record = "";
                    }
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    try {
                        TempDelete = Integer.parseInt(request.getParameter("TempDelete"));
                    } catch (Exception e) {
                        TempDelete = 0;
                    }
                    try {
                        StateCerti = Integer.parseInt(request.getParameter("StateCerti"));
                    } catch (Exception e) {
                        StateCerti = 0;
                    }
                    try {
                        TempM = Integer.parseInt(request.getParameter("TempM"));
                    } catch (Exception e) {
                        TempM = 0;
                    }
                    try {
                        Client = request.getParameter("Client");
                    } catch (Exception e) {
                        Client = "";
                    }
                    try {
                        DateI = request.getParameter("DateI");
                    } catch (Exception e) {
                        DateI = "";
                    }
                    try {
                        DateF = request.getParameter("DateF");
                    } catch (Exception e) {
                        DateF = "";
                    }
                    try {
                        CountReg = Integer.parseInt(request.getParameter("CountReg"));
                    } catch (Exception e) {
                        CountReg = 0;
                    }
                    try {
                        AmoutReg = Integer.parseInt(request.getParameter("AmoutReg"));
                    } catch (Exception e) {
                        AmoutReg = 0;
                    }
                    request.setAttribute("Type", Type);
                    request.setAttribute("Order", Order);
                    request.setAttribute("Product", Product);
                    request.setAttribute("Batch", Batch);
                    request.setAttribute("Record", Record);
                    request.setAttribute("FormatName", FormatName);
                    request.setAttribute("IdCertificates", IdCertificates);
                    request.setAttribute("TempDelete", TempDelete);
                    request.setAttribute("StateCerti", StateCerti);
                    request.setAttribute("TempM", TempM);
                    request.setAttribute("Client", Client);
                    request.setAttribute("DateI", DateI);
                    request.setAttribute("DateF", DateF);
                    request.setAttribute("CountReg", CountReg);
                    request.setAttribute("AmoutReg", AmoutReg);
                    request.getRequestDispatcher("Visual.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="SAVE FORMAT GENERATE">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    try {
                        Html = URLDecoder.decode(request.getParameter("Html"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Html = "";
                    }
                    // Diagnóstico temporal: confirmar si el Html ya llega incompleto
                    // desde el navegador (vs. truncarse después, al guardar/leer en BD).
                    {
                        String rawParam = request.getParameter("Html");
                        Logger.getLogger(Generate.class.getName()).log(Level.INFO,
                                "SAVE Html: rawParamLength={0}, decodedLength={1}, decodedTail=[{2}]",
                                new Object[]{
                                    rawParam == null ? -1 : rawParam.length(),
                                    Html.length(),
                                    Html.length() > 300 ? Html.substring(Html.length() - 300) : Html
                                });
                    }
                    try {
                        Consecutive = request.getParameter("ConsValue");
                    } catch (Exception e) {
                        Consecutive = "CC****";
                    }
                    try {
                        Amount = request.getParameter("AmountValue");
                    } catch (Exception e) {
                        Amount = "*****";
                    }
                    try {
                        DateDispatch = request.getParameter("DateDispatch");
                    } catch (Exception e) {
                        DateDispatch = "*****";
                    }
                    try {
                        MaterialBatches = request.getParameter("MaterialBatches");
                    } catch (Exception e) {
                        MaterialBatches = "";
                    }
                    if (IdCertificates > 0) {
                        //<editor-fold defaultstate="collapsed" desc="UPDATE">
                        result = CertificatesJpa.CertificatesUpdate(IdCertificates, Consecutive, Amount, DateDispatch, MaterialBatches, Html);
                        request.setAttribute("UpdateCertificate", result);
                        request.getRequestDispatcher("Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates)
                                .forward(request, response);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTER">
                        try {
                            Order = Integer.parseInt(request.getParameter("Order"));
                        } catch (Exception e) {
                            Order = 0;
                        }
                        try {
                            Product = request.getParameter("Product");
                        } catch (Exception e) {
                            Product = "";
                        }
                        try {
                            Batch = request.getParameter("Batch");
                        } catch (Exception e) {
                            Batch = "";
                        }
                        try {
                            Customer = request.getParameter("clientValue");
                        } catch (Exception e) {
                            Customer = "*****";
                        }
                        try {
                            Code = request.getParameter("codeValue");
                        } catch (Exception e) {
                            Code = "*****";
                        }
                        try {
                            FormatName = request.getParameter("FormatName");
                        } catch (Exception e) {
                            FormatName = "";
                        }
                        try {
                            AmoutReg = Integer.parseInt(request.getParameter("AmoutReg"));
                        } catch (Exception e) {
                            AmoutReg = 0;
                        }
                        result = CertificatesJpa.CertificatesRegister(Type, Code, Order, Product, Batch, Consecutive, Customer, Amount, DateDispatch, RolName, MaterialBatches, AmoutReg, Html);
                        if (result) {
                            lst_id = CertificatesJpa.ConsultCertificatesIdType(Type);
                            if (lst_id != null) {
                                try {
                                    Object[] ObjId = (Object[]) lst_id.get(0);
                                    IdCertificates = Integer.parseInt(ObjId[0].toString());
                                } catch (Exception e) {
                                    IdCertificates = 0;
                                }
                            }
                            request.setAttribute("RegisterCertificates", result);
                        }
                        request.getRequestDispatcher("Generate?opt=2&Type=" + Type + "&Order=" + Order + "&Product=" + Product + "&Batch=" + Batch + "&FormatName=" + FormatName + "&IdCertificates=" + IdCertificates + "&StateCerti=1&AmoutReg=" + AmoutReg)
                                .forward(request, response);
                        //</editor-fold>
                    }
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        IdCertiMasive = request.getParameter("IdCertiMasive");
                    } catch (Exception e) {
                        IdCertiMasive = "";
                    }
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (Exception e) {
                        Temp = 0;
                    }
                    // La firma se toma de user.signature (BD), no solo de la sesión:
                    // la sesión se carga al iniciar sesión, así que una firma
                    // registrada después en el Perfil no llegaba al certificado
                    // (quedaba authorization_signature = '').
                    try {
                        List lstUser = new UserControllerJpa().ConsultUsersid(Integer.parseInt(session.getAttribute("idUsuario").toString()));
                        if (lstUser != null) {
                            Object[] ObjUser = (Object[]) lstUser.get(0);
                            Signature = (ObjUser[8] == null) ? "" : ObjUser[8].toString().trim();
                            session.setAttribute("Firma", Signature);
                        }
                    } catch (Exception e) {
                        // Se conserva la firma de la sesión.
                    }
                    int Signed = 0, Failed = 0;
                    boolean CanSign = ((IdRol == 1) || (IdRol == 2)) && Signature != null && !Signature.trim().isEmpty();
                    if (Signature == null || Signature.trim().isEmpty()) {
                        request.setAttribute("SigMissing", true);
                    }
                    if (CanSign) {
                        // Única: IdCertiMasive = "5". Masiva: IdCertiMasive = "[5][8][9]".
                        String[] IdsCerti = IdCertiMasive == null ? new String[0]
                                : IdCertiMasive.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int i = 0; i < IdsCerti.length; i++) {
                            try {
                                IdCertificates = Integer.parseInt(IdsCerti[i].trim());
                            } catch (NumberFormatException e) {
                                Failed++;
                                continue;
                            }
                            if (signCertificate(CertificatesJpa, IdCertificates, Signature)) {
                                Signed++;
                            } else {
                                Failed++;
                            }
                        }
                    }
                    if (Temp == 0) {
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE MASIVE">
                        request.setAttribute("SigMasive", Signed > 0 && Failed == 0);
                        if (Failed > 0) {
                            request.setAttribute("SigMasiveResume", "Firmados: " + Signed + ", no firmados: " + Failed + ".");
                        }
                        request.getRequestDispatcher("Generate?opt=8").forward(request, response);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE UNIQUE">
                        request.setAttribute("SigUnique", Signed == 1);
                        request.getRequestDispatcher("Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates + "&TempDelete=0&StateCerti=" + (Signed == 1 ? 3 : 2)).forward(request, response);
                        //</editor-fold>
                    }
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="DELETE/RETURN CERTIFICATE">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    try {
                        Category = request.getParameter("Category");
                    } catch (Exception e) {
                        Category = "";
                    }
                    try {
                        Justification = request.getParameter("Justification");
                    } catch (Exception e) {
                        Justification = "";
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("State"));
                    } catch (Exception e) {
                        State = 0;
                    }
                    result = CertificatesJpa.RegisterNovelty(IdCertificates, Category, Justification, RolName, State);
                    if (result) {
                        if (Category.equals("Delete")) {
                            request.setAttribute("DeleteCertificates", result);
                        } else {
                            request.setAttribute("ReturnCertificates", result);
                        }
                        try {
                            NumberCertificate = request.getParameter("NumberCertificate");
                        } catch (Exception e) {
                            NumberCertificate = "";
                        }
                        MailConn.CertificateReturn(NumberCertificate, UserName, Justification, Category, getServletContext());
                    }
                    request.getRequestDispatcher("Generate?opt=1&Type=" + Type).forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER EVENTS">
                    System.out.println(">>> EVENTO AUTO RECIBIDO <<<");
                    System.out.println("Type: " + request.getParameter("Type"));
                    System.out.println("IdCertificates: " + request.getParameter("IdCertificates"));
                    System.out.println("Message: " + request.getParameter("Message"));

                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    try {
                        Message = request.getParameter("Message");
                    } catch (Exception e) {
                        Message = "";
                    }
                    result = EventConn.RegisterEvents(IdCertificates, Message);
                    if (result) {
                        request.setAttribute("RegisterNovetly", result);
                    }
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="CONFIRM CERTIFICATE">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        IdCertiMasive = request.getParameter("IdCertiMasive");
                    } catch (Exception e) {
                        IdCertiMasive = "";
                    }
                    if (IdCertiMasive == null || IdCertiMasive.trim().isEmpty()) {
                        // Finalización única (Visual.jsp): se normaliza al mismo
                        // formato "[id]" que usa la selección masiva.
                        try {
                            IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                        } catch (Exception e) {
                            IdCertificates = 0;
                        }
                        IdCertiMasive = (IdCertificates > 0) ? "[" + IdCertificates + "]" : "";
                    }
                    String[] IdsFinish = IdCertiMasive.trim().isEmpty() ? new String[0]
                            : IdCertiMasive.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    java.util.List<String> Finished = new java.util.ArrayList<String>();
                    int NotFinished = 0;
                    for (int i = 0; i < IdsFinish.length; i++) {
                        if (IdsFinish[i].trim().isEmpty()) {
                            continue;
                        }
                        try {
                            IdCertificates = Integer.parseInt(IdsFinish[i].trim());
                        } catch (NumberFormatException e) {
                            NotFinished++;
                            continue;
                        }
                        NumberCertificate = finishCertificate(CertificatesJpa, IdCertificates);
                        if (NumberCertificate == null) {
                            NotFinished++;
                        } else {
                            Finished.add(NumberCertificate);
                        }
                    }
                    if (!Finished.isEmpty()) {
                        // Un único correo con todos los certificados finalizados.
                        MailConn.CertificateSend(Finished, UserName, getServletContext());
                    }
                    request.setAttribute("FinishCertificate", !Finished.isEmpty() && NotFinished == 0);
                    if (NotFinished > 0) {
                        request.setAttribute("FinishCertificateResume",
                                "Finalizados: " + Finished.size() + ", no finalizados: " + NotFinished + ".");
                    }
                    request.getRequestDispatcher("Generate?opt=1&Type=" + Type).forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT SIGNATURE REPORT">
                    try {
                        StateM = Integer.parseInt(request.getParameter("StateM"));
                    } catch (Exception e) {
                        StateM = 0;
                    }
                    request.setAttribute("StateM", StateM);
                    request.getRequestDispatcher("SignatureReport.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="VIEW CERTIFICATE">
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    request.setAttribute("IdCertificates", IdCertificates);
                    request.getRequestDispatcher("CertifiedView.jsp").forward(request, response);

                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="DUPLICATE REG">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    Integer NewCertificateId = CertificatesJpa.DuplicateRegister(IdCertificates, UserName);
                    result = NewCertificateId != null;
                    if (result) {
                        request.setAttribute("DuplicateCertificate", result);
                        String userId = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;
                        new CertificateDuplicationJpaController().registerDuplication(
                                IdCertificates, NewCertificateId, userId, UserName);
                    }
                    request.setAttribute("Type", Type);
                    request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(Generate.class.getName()).log(Level.SEVERE,
                    "Error procesando Generate?opt=" + request.getParameter("opt"), ex);
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
        }
    }

    /**
     * Finaliza un certificado (state = 2) solo si está en estado 1 (En
     * gestión) y devuelve su número de certificado (consecutive_quality)
     * para la notificación. Devuelve null si no se pudo finalizar. El
     * número se lee de BD porque la finalización masiva solo envía ids.
     */
    private String finishCertificate(CertificatesJpaController CertificatesJpa, int IdCertificates) {
        List lstCerti = CertificatesJpa.ConsultCertificatesId("", IdCertificates);
        if (lstCerti == null) {
            return null;
        }
        Object[] ObjCerti = (Object[]) lstCerti.get(0);
        try {
            if (Integer.parseInt(ObjCerti[8].toString()) != 1) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        if (!CertificatesJpa.UpdateCertificateFinish(IdCertificates)) {
            return null;
        }
        return (ObjCerti[3] == null) ? String.valueOf(IdCertificates) : ObjCerti[3].toString().trim();
    }

    /**
     * Firma un certificado (authorization_signature = firma, state = 3) solo
     * si está en estado 2 (Finalizado), y registra su lote en
     * certificate_lotes con los datos del propio certificado en BD. Antes se
     * usaban Customer/Anio/Order/Batch del request, que la firma masiva no
     * envía (quedaba un lote '' / 0 / 0 / '') y que en masivo pueden ser
     * lotes distintos por certificado.
     */
    private boolean signCertificate(CertificatesJpaController CertificatesJpa, int IdCertificates, String Signature) {
        List lstCerti = CertificatesJpa.ConsultCertificatesId("", IdCertificates);
        if (lstCerti == null) {
            return false;
        }
        Object[] ObjCerti = (Object[]) lstCerti.get(0);
        try {
            if (Integer.parseInt(ObjCerti[8].toString()) != 2) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        if (!CertificatesJpa.CertificatesUpdateSignature(IdCertificates, Signature)) {
            return false;
        }
        // El lote queda visible en la navegación de FileManager.jsp (Gestor de
        // Archivos) desde que se firma, tenga o no archivos subidos todavía —
        // sin crear nada en el filesystem local (certificate_lotes cumple el
        // rol de marcador en BD).
        new CertificateFileJpaController().registerLote(
                (ObjCerti[4] == null) ? "" : ObjCerti[4].toString(),
                (ObjCerti[12] == null) ? "" : ObjCerti[12].toString(),
                (ObjCerti[5] == null) ? "" : ObjCerti[5].toString(),
                (ObjCerti[7] == null) ? "" : ObjCerti[7].toString());
        return true;
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
