package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.DocumentControllerJpa;
import Controller.TemplateControllerJpa;
import Controller.UserControllerJpa;
import Controller.SegmentationControllerJpa;
import Mail.SendMail;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Document extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        DocumentControllerJpa DocumentJpa = new DocumentControllerJpa();
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        UserControllerJpa UserJpa = new UserControllerJpa();
        SegmentationControllerJpa SegmentationJpa = new SegmentationControllerJpa();
        Random MixedJpa = new Random();
        SendMail MailJpa = new SendMail();
        StringBuilder nombreUsuarioBuilder = new StringBuilder(10);
        List lst_document = null;
        List lst_template = null;
        List lst_segmentation = null;
        List lst_DocumentJpa = null;
        int opt = 0, IdDoc = 0, State = 0, module = 0, TypeSig = 0, IdSig = 0, NroIdenti = 0, IdUser = 0, IdAgree = 0, UserId = 0, typeSeg = 0,
                idClient = 0, ste = 0, RenewUser = 0, IdDocx = 0, idSeg = 0;
        boolean result = false;
        String BusinessName = "", Mail = "", BaseTemplate = "", Template = "", Files = "", Event = "", Format = "", Signature = "", TypeLtter = "",
                Forms = "", FinalForm = "", Names = "", Position = "", Consult = "", Date = "", Info = "", Obs = "", formClient = "", Due = "", Confirmation = "";
        //</editor-fold>
        opt = Integer.parseInt(request.getParameter("opt").toString());
        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        String[] DtaFormat = {};
        String idSegx = "", namex = "", formatx = "", tempx = "", justify = "";
        try {
            try {
                Format = request.getParameter("TxtFormat");
                DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
            } catch (Exception e) {
                try {
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    lst_DocumentJpa = DocumentJpa.ConsultDocumentsId(IdDoc);
                    if (lst_DocumentJpa != null) {
                        Object[] ObjDoc = (Object[]) lst_DocumentJpa.get(0);
                        Format = ObjDoc[3].toString();
                        DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                    }
                } catch (Exception ex) {
                    IdDoc = 0;
                }
            }
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    } catch (Exception e) {
                        IdDoc = 0;
                    }
                    try {
                        IdDocx = Integer.parseInt(request.getParameter("IdDocx"));
                    } catch (Exception e) {
                        IdDocx = 0;
                    }
                    try {
                        Event = request.getParameter("Event");
                    } catch (Exception e) {
                        Event = "Main";
                    }
                    try {
                        ste = Integer.parseInt(request.getParameter("ste"));
                    } catch (Exception e) {
                        ste = 0;
                    }
                    try {
                        idSegx = request.getParameter("idSegx");
                        namex = request.getParameter("name");
                        formatx = request.getParameter("format");
                        tempx = request.getParameter("tempo");
                    } catch (Exception e) {
                        idSegx = "";
                        namex = "";
                        formatx = "";
                        tempx = "";
                    }
                    request.setAttribute("idSegx", idSegx);
                    request.setAttribute("namex", namex);
                    request.setAttribute("formatx", formatx);
                    request.setAttribute("tempx", tempx);
                    request.setAttribute("IdDoc", IdDoc);
                    request.setAttribute("EventDoc", Event);
                    request.setAttribute("ste", ste);
                    request.setAttribute("IdDocx", IdDocx);
                    request.getRequestDispatcher("Document.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER">
                    LocalDate fechaActual = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String fechaFormateada = fechaActual.format(formatter);

                    String nombreUsuario = "";
                    String TypeDoc = "";
                    String TypeContra = "";
                    try {
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    } catch (Exception e) {
                        IdDoc = 0;
                    }
                    try {
                        IdDocx = Integer.parseInt(request.getParameter("IdDocx"));
                    } catch (Exception e) {
                        IdDocx = 0;
                    }
                    try {
                        RenewUser = Integer.parseInt(request.getParameter("RenewUser"));
                    } catch (Exception e) {
                        RenewUser = 0;
                    }
                    try {
                        TypeDoc = request.getParameter("radType");
                    } catch (Exception e) {
                        TypeDoc = "";
                    }
                    try {
                        TypeContra = request.getParameter("ContraType");
                    } catch (Exception e) {
                        TypeContra = "";
                    }

                    BusinessName = request.getParameter("TxtBusinessName");
                    Mail = request.getParameter("TxtMail");
                    BaseTemplate = request.getParameter("TxtTemplate");
                    Files = request.getParameter("TxtFiles");
                    IdAgree = Integer.parseInt(request.getParameter("TxtAgree"));
                    String[] baseTempl = BaseTemplate.toString().replace("]", "").replace("[", "").split("/");
                    lst_template = TemplateJpa.ConsultTemplateId(Integer.parseInt(baseTempl[0].toString()));
                    if (lst_template != null) {
                        Object[] ObjTemplate = (Object[]) lst_template.get(0);
                        Template = ObjTemplate[1].toString();
                        formClient = ObjTemplate[7].toString();
                        String tyDoc = "", tyCon = "";
                        if (TypeDoc.equals("vin")) {
                            tyDoc = "Vinculacion";
                        } else if (TypeDoc.equals("act")) {
                            tyDoc = "Actualizacion";
                        }
                        if (TypeContra.equals("prv")) {
                            tyCon = "Proveedor";
                        } else if (TypeContra.equals("cli")) {
                            tyCon = "Cliente";
                        }
                        formClient = formClient.replace("[[0][N/A][N/A][N/A]", "[[0][" + tyDoc + "][N/A][" + tyCon + "]");
                    }
                    if (RenewUser == 1) {
                        //<editor-fold defaultstate="collapsed" desc="RE NEW DOCUMENT">
                        result = DocumentJpa.DocumentRegister(BusinessName, Mail, baseTempl[1].toString(), Template, formClient, Files, IdAgree);
                        lst_document = DocumentJpa.ConsultDocumentsClient(Mail);
                        if (lst_document != null) {
                            Object[] Obj_doc = (Object[]) lst_document.get(0);
                            IdDoc = Integer.parseInt(Obj_doc[0].toString());
                            UserJpa.UserRenewDocument(IdDoc, IdDocx);
                            if (result) {
                                lst_document = DocumentJpa.ConsultLastdocument(Mail);
                                if (lst_document != null) {
                                    Object[] ObjDoc = (Object[]) lst_document.get(0);
                                    nombreUsuario = ObjDoc[4].toString();
                                    MailJpa.ResendMailClient(nombreUsuario, ObjDoc[1].toString(), Mail);
                                    MailJpa.SendingNotifyAdd(BusinessName, nombreUsuario);
                                    try {
                                        idSeg = Integer.parseInt(request.getParameter("idSegx"));
                                    } catch (Exception e) {
                                        idSeg = 0;
                                    }
                                    if (idSeg > 0) {
                                        SegmentationJpa.UpdateSegmentationNewDoc(idSeg, fechaFormateada);
                                    }
                                }
                            }
                            request.setAttribute("DocRenew", result);
                        }
                        //</editor-fold>
                    } else {
                        if (IdDoc == 0) {
                            //<editor-fold defaultstate="collapsed" desc="REGISTER DOCUMENT">
                            result = DocumentJpa.DocumentRegister(BusinessName, Mail, baseTempl[1].toString(), Template, formClient, Files, IdAgree);
                            if (result) {
                                String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                                for (int i = 0; i < 10; i++) {
                                    int indice = MixedJpa.nextInt(caracteres.length());
                                    char caracter = caracteres.charAt(indice);
                                    nombreUsuarioBuilder.append(caracter);
                                }
                                nombreUsuario = nombreUsuarioBuilder.toString();
                                int numeroAleatorio = 0;
                                for (int i = 0; i < 10; i++) {
                                    numeroAleatorio = MixedJpa.nextInt(Integer.MAX_VALUE);
                                }
                                lst_document = DocumentJpa.ConsultDocumentsClient(Mail);
                                if (lst_document != null) {
                                    Object[] Obj_doc = (Object[]) lst_document.get(0);
                                    IdDoc = Integer.parseInt(Obj_doc[0].toString());
                                    UserJpa.RegisterClient(BusinessName, numeroAleatorio, nombreUsuario, Mail, "Administrador", IdDoc);
                                } else {
                                }
                                IdDoc = 0;
                            }

                            if (result) {
                                lst_document = DocumentJpa.ConsultLastdocument(Mail);
                                if (lst_document != null) {
                                    Object[] objDoc = (Object[]) lst_document.get(0);
                                    MailJpa.SendingClientMail(Mail, nombreUsuario);
                                    MailJpa.SendingNotifyAdd(BusinessName, nombreUsuario);
                                }
                            }

                            request.setAttribute("DocRegister", result);
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="UPDATE DOCUMENT">
                            if (result) {

                            } else {
                                result = DocumentJpa.UpdateRegister(IdDoc, BusinessName, Mail, baseTempl[1].toString(), Template, Files, IdAgree);
                            }
                            if (result) {
                                lst_document = DocumentJpa.ConsultDocumentByIdDoc(IdDoc);
                                if (lst_document != null) {
                                    Object[] ObjUser = (Object[]) lst_document.get(0);
                                    MailJpa.SendingClientMailModify(Mail, ObjUser[5].toString());
                                    MailJpa.SendingNotifyModify(BusinessName);
                                }
                            }
                            request.setAttribute("DocUpdate", result);
                            //</editor-fold>
                        }
                    }
                    request.getRequestDispatcher("Document?opt=1&IdDoc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE BOSS">
                    module = 16;
                    String dataRecap = "";
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    TypeSig = Integer.parseInt(request.getParameter("TypeSig"));
                    UserId = Integer.parseInt(request.getParameter("IdUser"));

                    try {
                        if (TypeSig == 1) {
                            Signature = request.getParameter("TxtSignatureDrawtw");
                        } else if (TypeSig == 2) {
                            Signature = request.getParameter("TxtSignatureWrite");
                            TypeLtter = request.getParameter("TxtSigLetter");
                            Signature = Signature + "/" + TypeLtter;
                        } else if (TypeSig == 3) {
                            Signature = request.getParameter("TxtSignatureImg");
                        }
                        if (!Signature.equals("")) {
                            result = DocumentJpa.DocumentSignatureRegisterBoss(IdDoc, TypeSig, UserId, Signature);
                            result = DocumentJpa.UpdateDocumentState(IdDoc, 6);
                            request.setAttribute("StateDocument", result);
                        }
                    } catch (Exception e) {
                        Signature = "";
                    }

                    Forms = "[[" + module + "][" + UserId + "]]";
                    for (int i = 0; i < DtaFormat.length; i++) {
                        if (i == DtaFormat.length - 1) {
                            if (i != module) {
                                FinalForm += "[" + DtaFormat[i] + "]";
                            } else {
                                FinalForm += Forms;
                            }
                        } else {
                            if (i != module) {
                                if (i == 0) {
                                    String[] Recopilation = DtaFormat[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    dataRecap += "[" + Recopilation[3] + "]";
                                } else if (i == 1) {
                                    String[] Recopilation = DtaFormat[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    dataRecap += "[" + Recopilation[1] + "]";
                                    dataRecap += "[" + Recopilation[2] + "]";
                                    dataRecap += "[" + Recopilation[5] + "]";
                                    dataRecap += "[" + Recopilation[11] + "]";
                                    dataRecap += "[" + Recopilation[12] + "]";
                                } else if (i == 5) {
                                    String[] Recopilation = DtaFormat[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    if (!Recopilation[1].toString().equals("N/A")) {
                                        dataRecap += "[" + Recopilation[1] + " " + Recopilation[2] + "]";
                                    }
                                } else if (i == 7) {
                                    String[] Recopilation = DtaFormat[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    if (!Recopilation[1].toString().equals("N/A")) {
                                        dataRecap += "[Si]";
                                    } else {
                                        dataRecap += "[No]";
                                    }
                                } else if (i == 9) {
                                    String[] Recopilation = DtaFormat[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    dataRecap += "[" + Recopilation[1] + "]";
                                }
                                FinalForm += "[" + DtaFormat[i] + "]/";
                            } else {
                                FinalForm += Forms + "/";
                            }
                        }
                    }

                    lst_document = DocumentJpa.ConsultDocumentsId(IdDoc);
                    if (lst_document != null) {
                        Object[] ObjDoc = (Object[]) lst_document.get(0);
                        if (result) {
                            String[] DataFinal = dataRecap.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            String Typebuss = DataFinal[0].toUpperCase();;
                            String NameBuss = DataFinal[1];
                            String TaxId = DataFinal[2];
                            String benfF = DataFinal[7];
                            int CiuuOne = Integer.parseInt(DataFinal[4].toString().split("/")[0]);
                            int CiuuTwo = Integer.parseInt(DataFinal[5].toString().split("/")[0]);
                            String City = DataFinal[3];
                            String LegalR = DataFinal[6];
                            String IsPep = DataFinal[8];
                            String NatInt = "";
                            int Tipe = Integer.parseInt(ObjDoc[14].toString());
                            if (Tipe == 1) {
                                NatInt = "NATIONAL";
                            } else if (Tipe == 2) {
                                NatInt = "INTERNATIONAL";
                            } else {
                                NatInt = "N/A";
                            }
                            result = SegmentationJpa.RegisterSegmentationByDocument(IdDoc, NameBuss, TaxId, Typebuss, benfF, CiuuOne, CiuuTwo, City, LegalR, IsPep, NatInt, UserName);
                            request.setAttribute("RegisterSegmentation", result);
                            if (result) {
                                result = SegmentationJpa.UpdatePreviousSegmentation(DataFinal[0], DataFinal[1]);
                                UserJpa.UserUpdateStateSwap(IdDoc);
                            }
                        }
                    }
//                    request.setAttribute("StateDocument", result);
                    Event = "Checking";
                    request.getRequestDispatcher("Document?opt=1&IdDoc=" + IdDoc + "&Event=" + Event + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="APROVE DOCUMENT">
                    module = 15;
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    try {
                        Event = request.getParameter("Event");
                    } catch (Exception e) {
                        Event = "Main";
                    }
                    lst_DocumentJpa = DocumentJpa.ConsultDocumentsId(IdDoc);
                    if (lst_DocumentJpa != null) {
                        Object[] ObjDoc = (Object[]) lst_DocumentJpa.get(0);
                        Format = ObjDoc[3].toString();
                    }
                    DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");

                    Date = request.getParameter("TxtDate");
                    Names = request.getParameter("TxtNameFun");
                    NroIdenti = Integer.parseInt(request.getParameter("NmbIdentiFun"));
                    Position = request.getParameter("TxtPosition");
                    Consult = request.getParameter("TxtConsult");
                    Due = request.getParameter("TxtDue");
                    typeSeg = Integer.parseInt(request.getParameter("tipeSeg"));
                    Confirmation = request.getParameter("TxtSiNo");
                    String nameClient = "";
                    lst_document = DocumentJpa.ConsultDocumentByIdDoc(IdDoc);
                    if (lst_document != null) {
                        Object[] objclient = (Object[]) lst_document.get(0);
                        nameClient = objclient[2].toString();
                    } else {
                        nameClient = "N/A";
                    }

                    Forms = "[[" + module + "][" + Date + "][" + Names + "][" + NroIdenti + "][" + Position + "][" + Consult + "][" + Due + "][" + Confirmation + "]]";
                    for (int i = 0; i < DtaFormat.length; i++) {
                        if (i == DtaFormat.length - 1) {
                            if (i != module) {
                                FinalForm += "[" + DtaFormat[i] + "]";
                            } else {
                                FinalForm += Forms;
                            }
                        } else {
                            if (i != module) {
                                FinalForm += "[" + DtaFormat[i] + "]/";
                            } else {
                                FinalForm += Forms + "/";
                            }
                        }
                    }
                    module++;
                    result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                    if (result) {
                        result = DocumentJpa.UpdateDocumentState(IdDoc, 4);
                        DocumentJpa.UpdateDocumentNationality(IdDoc, typeSeg);
                        MailJpa.SendingBossNotify(nameClient);
                    }
                    request.setAttribute("DocumentAprove", result);
                    request.getRequestDispatcher("Document?opt=1&IdDoc=" + IdDoc + "&Event=" + Event + "").forward(request, response);
//</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="RETURN DOCUMENT">
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    State = Integer.parseInt(request.getParameter("CbxState"));
                    Obs = request.getParameter("TxtNote");
                    result = DocumentJpa.DocumentRegisterObservations(IdDoc, State, Obs, UserName);
                    if (result) {
                        lst_document = DocumentJpa.ConsultDocumentByIdDoc(IdDoc);
                        if (lst_document != null) {
                            Object[] obUSer = (Object[]) lst_document.get(0);
                            MailJpa.SendingClientMailReturn(obUSer[8].toString(), obUSer[5].toString());
                            MailJpa.SendingNotifyReturn(obUSer[2].toString());
                        }
                    }
                    request.setAttribute("ReturnDocument", result);
                    request.getRequestDispatcher("Document?opt=1&IdDoc=" + IdDoc + "&Event=Checking").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="RE SEND MAIL CLIENT">
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    idClient = Integer.parseInt(request.getParameter("idClient"));
                    result = UserJpa.UserUpdatePass(idClient);
                    if (result) {
                        lst_document = DocumentJpa.ConsultDocumentByIdDoc(IdDoc);
                        if (lst_document != null) {
                            Object[] obUSer = (Object[]) lst_document.get(0);
                            MailJpa.ResendMailClient(obUSer[5].toString(), obUSer[2].toString(), obUSer[8].toString());
                        }
                        result = true;
                    }
                    request.setAttribute("ResendClientCredencials", result);
                    request.getRequestDispatcher("Document?opt=1&IdDoc=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="CONCLUDE DOCUMENT">
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    result = DocumentJpa.UpdateDocumentStateFinal(IdDoc, 15, 3);
                    justify = request.getParameter("txtJustify");
                    if (result) {
                        DocumentJpa.DocumentRegisterConclude(IdDoc, justify, UserName);
                    }
                    request.setAttribute("ConcludeDocument", result);
                    request.getRequestDispatcher("Document?opt=1&IdDoc=" + IdDoc + "&Event=Checking").forward(request, response);
//</editor-fold>
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("Document.jsp").forward(request, response);
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
