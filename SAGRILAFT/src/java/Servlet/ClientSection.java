package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.DocumentControllerJpa;
import java.util.Calendar;
import java.util.List;
import java.io.File;
import javax.servlet.http.HttpSession;

import Mail.SendMail;

public class ClientSection extends HttpServlet {

    private String parseOrDefault(String value) {
        return (value == null || value.trim().isEmpty()) ? "N/A" : value.trim();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        String NameSession = "";
        HttpSession sesion = request.getSession();
        try {
            NameSession = sesion.getAttribute("Nombre").toString();
        } catch (Exception e) {
            NameSession = "Not Found!";
        }

        DocumentControllerJpa DocumentJpa = new DocumentControllerJpa();

        SendMail MailData = new SendMail();

        int opt = 0, IdDoc = 0, module = 0, NroIdenti = 0, counter = 0, TypeSig = 0, IdSig = 0, State = 0;
        String Format = "", Forms = "", TypeProc = "", DateInit = "", TypeThird = "", FinalForm = "", ValidAction = "", AlterText = "",
                BusinessName = "", NroDv = "", Country = "", City = "", Address = "", Phones = "", Mail = "", WebPage = "", PostalCode = "",
                CodeCiiu_1 = "", CodeCiiu_2 = "", NroComercial = "", TypeCompany = "", ClasiCompany = "", Certification = "", IdOther = "",
                Iva = "", Resolution = "", SelfRetaining = "", ReteSource = "", ValueReteSource = "", DataRetaining = "", Ica = "",
                TributaryCity = "", UserZone = "", ValueAprov = "", TimeDays = "", Names = "", Role = "", NroCel = "", MailFact = "", LastName = "",
                DateDocum = "", TypeDoc = "", Place = "", NroDoc = "", IsPep = "", Participaction = "", Entity = "", AccountType = "",
                AccountNumb = "", ResourceOrigin = "", CoinType = "", Assets = "", Passives = "", Heritage = "", Income = "", Expenses = "",
                OtherIncome = "", ConceptIncome = "", AnioReport = "", UndReport = "", Quest = "", Obs = "", MoneyOne = "", MoneyTwo = "", DetailOne = "",
                DetailTwo = "", ReadDoc = "", FilesDocs = "", IdFiles = "", FileToAttch = "", FilesChange = "", Signature = "", TypeLtter = "", NroIdentix = "",
                TypeDocGeneral = "", DateComerc = "", ActivityComercial = "", ParcialForm = "", Phone = "", Contact = "", Nit = "", TypeFormat = "", DescSource = "";
        boolean result = false;
        List lst_DocumentJpa = null;
        String[] DtaFormat = {};
        Calendar cal = Calendar.getInstance();
        String anio = cal.get(Calendar.YEAR) + "";
        String mes = "";
        if ((cal.get(Calendar.MONTH) + 1) < 10) {
            mes = "0" + (cal.get(Calendar.MONTH) + 1);
        } else {
            mes = (cal.get(Calendar.MONTH) + 1) + "";
        }
        String dia = "";
        if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
            dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
        } else {
            dia = cal.get(Calendar.DAY_OF_MONTH) + "";
        }

        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            try {
                IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                lst_DocumentJpa = DocumentJpa.ConsultDocumentsId(IdDoc);
                if (lst_DocumentJpa != null) {
                    Object[] ObjDoc = (Object[]) lst_DocumentJpa.get(0);
                    Format = ObjDoc[3].toString();
                    TypeFormat = ObjDoc[4].toString();
                    DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                }
            } catch (Exception e) {
                Format = request.getParameter("TxtFormat");
                DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
            }
            if (TypeFormat.contains("Circular 170")) {
                //<editor-fold defaultstate="collapsed" desc="DATA CIRCULAR 170">
                switch (opt) {
                    case 1:
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        request.setAttribute("IdDoc", IdDoc);
                        request.getRequestDispatcher("ClientSection.jsp").forward(request, response);
                        break;
                    case 2:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 0 - START STATE">
                        module = 0;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        TypeProc = request.getParameter("TxtTypeProc");
                        DateInit = request.getParameter("DateInit");
                        TypeThird = request.getParameter("TxtTypeThird");
                        if (TypeThird.equals("Otro")) {
                            AlterText = request.getParameter("TxtOther");
                            TypeThird = "Otro/" + AlterText;
                        }
                        ValidAction = request.getParameter("TxtValidAction");

                        Forms = "[[" + module + "][" + TypeProc + "][" + DateInit + "][" + TypeThird + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 3:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 1 - GENERAL INFORMATION">
                        module = 1;
                        IdDoc = Integer.parseInt(parseOrDefault(request.getParameter("IdDoc")));
                        BusinessName = parseOrDefault(request.getParameter("TxtNameBusi"));
                        TypeDocGeneral = parseOrDefault(request.getParameter("CbxTypeDocGeneral"));
                        NroIdentix = parseOrDefault(request.getParameter("NmbIndeti"));
                        NroDv = parseOrDefault(request.getParameter("TxtDv"));
                        City = parseOrDefault(request.getParameter("TxtCity"));
                        Address = parseOrDefault(request.getParameter("TxtAddress"));
                        Phones = parseOrDefault(request.getParameter("TxtPhones"));
                        Mail = parseOrDefault(request.getParameter("TxtMail"));
                        WebPage = parseOrDefault(request.getParameter("TxtWebPage"));
                        CodeCiiu_1 = parseOrDefault(request.getParameter("CbxCiiu1"));
                        NroComercial = parseOrDefault(request.getParameter("TxtNroComercial"));
                        DateComerc = parseOrDefault(request.getParameter("TxtDateComer"));
                        TypeCompany = parseOrDefault(request.getParameter("TypeCompany"));
                        ActivityComercial = parseOrDefault(request.getParameter("comercialActiv"));
                        ValidAction = parseOrDefault(request.getParameter("TxtValidAction"));

                        Forms = "[[" + module + "][" + BusinessName + "][" + TypeDocGeneral + "][" + NroIdentix + "][" + NroDv + "][" + City + "][" + Address + "][" + Phones + "][" + Mail + "]"
                                + "[" + WebPage + "][" + CodeCiiu_1 + "][" + NroComercial + "][" + DateComerc + "][" + TypeCompany + "][" + ActivityComercial + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 4:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 2 - CERTIFICATIONS">
                        module = 2;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        try {
                            IdOther = request.getParameter("TxtIdOther");
                            if (IdOther == null) {
                                AlterText = "";
                            } else {
                                AlterText = request.getParameter("TxtOther");
                                if (AlterText.contains("N/A")) {
                                    AlterText = "NA";
                                }
                            }
                        } catch (Exception e) {
                            AlterText = "";
                        }

                        Certification = request.getParameter("TxtCertifications");
                        Certification = Certification.replace("][", "/").replace("[", "").replace("]", "");
                        if (!AlterText.equals("")) {
                            Certification += "--Otro|||" + AlterText + "";
                        } else {
                            Certification = Certification.replace("--", "");
                        }
                        Forms = "[[" + module + "][" + Certification + "]]";

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
                        if (ValidAction.equals("2")) {
                            module++;
                        } else if (ValidAction.equals("3")) {
                            module = module + 2;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 5:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 3 - PEP">
                        module = 3;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        String parcialForm = "";
                        IsPep = request.getParameter("IsPep");
                        if (IsPep.equals("Si")) {
                            for (int i = 1; i <= 6; i++) {
                                Quest = request.getParameter("Txt_Quest" + i + "");

                                Obs = request.getParameter("Txt_Obs" + i + "");
                                if (Obs.equals("")) {
                                    Obs = "NA";
                                }
                                parcialForm += "[" + Quest + "/" + Obs + "]";
                            }
                            Forms = "[[" + module + "][Si]" + parcialForm + "]";
                        } else {
                            Forms = "[[" + module + "][No][1][2][3][4][5][6]]";
                        }

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
                        if (ValidAction.equals("2")) {
                            module++;
                        } else if (ValidAction.equals("3")) {
                            module = module + 2;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 6:
                        //<editor-fold defaultstate="collapsed" desc="LEGAL REPRESENTATIVE">
                        module = 4;
                        ParcialForm = "";
                        ValidAction = request.getParameter("TxtValidAction");
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        Names = request.getParameter("TxtName");
                        TypeDoc = request.getParameter("CbxTypeDoc");
                        NroDoc = request.getParameter("NmbNroDoc");
                        Mail = request.getParameter("TxtMail");
                        Phone = request.getParameter("TxtPhone");
                        IsPep = request.getParameter("is_pep");
                        try {
                            counter = Integer.parseInt(request.getParameter("TxtCounterPerson"));
                        } catch (Exception e) {
                            counter = 0;
                        }
                        if (counter != 0) {
                            ParcialForm = "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + Mail + "/" + Phone + "/" + IsPep + "/0]";
                            for (int i = 2; i <= counter; i++) {
                                try {
                                    Names = request.getParameter("TxtName" + i + "");
                                    if (Names == null) {
                                        ParcialForm += "";
                                    } else {
                                        TypeDoc = request.getParameter("CbxTypeDoc" + i + "");
                                        NroDoc = request.getParameter("NmbNroDoc" + i + "");
                                        Mail = request.getParameter("TxtMail" + i + "");
                                        Phone = request.getParameter("TxtPhone" + i + "");
                                        IsPep = request.getParameter("is_pep" + i + "");

                                        ParcialForm += "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + Mail + "/" + Phone + "/" + IsPep + "/" + i + "]";
                                    }
                                } catch (Exception e) {
                                    ParcialForm += "";
                                }
                            }
                            Forms = "[[" + module + "]" + ParcialForm + "]";
                        } else {
                            Forms = "[[" + module + "][" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + Mail + "/" + Phone + "/" + IsPep + "/0]]";
                        }

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 7:
                        //<editor-fold defaultstate="collapsed" desc="SHAREHOLDING STRUCTURE">
                        module = 5;
                        ParcialForm = "";

                        //<editor-fold defaultstate="collapsed" desc="ACCIONARIES">
                        ValidAction = request.getParameter("TxtValidAction");
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        Names = request.getParameter("TxtNameAcc");
                        TypeDoc = request.getParameter("CbxTypeDocAcc");
                        NroDoc = request.getParameter("NmbNroDocAcc");
                        IsPep = request.getParameter("is_pepAcc");
                        Participaction = request.getParameter("TxtPartAcc");

                        try {
                            counter = Integer.parseInt(request.getParameter("TxtCounterPersonAcc"));
                        } catch (Exception e) {
                            counter = 0;
                        }
                        if (counter != 0) {
                            ParcialForm = "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + "" + Participaction + "/0]";
                            for (int i = 2; i <= counter; i++) {
                                try {
                                    Names = request.getParameter("TxtName" + i + "Acc");
                                    if (Names == null) {
                                        ParcialForm += "";
                                    } else {
                                        TypeDoc = request.getParameter("CbxTypeDoc" + i + "Acc");
                                        NroDoc = request.getParameter("NmbNroDoc" + i + "Acc");
                                        IsPep = request.getParameter("is_pep" + i + "Acc");
                                        Participaction = request.getParameter("TxtPart" + i + "Acc");
                                        ParcialForm += "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + "" + Participaction + "/" + i + "]";
                                    }
                                } catch (Exception e) {
                                    ParcialForm += "";
                                }
                            }
                            Forms = "[[" + module + "]" + ParcialForm + "";
                        } else {
                            Forms = "[[" + module + "][" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + "" + Participaction + "/0]";
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="BENEFICIARIES">
                        ValidAction = request.getParameter("TxtValidAction");
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        Names = request.getParameter("TxtName");
                        TypeDoc = request.getParameter("CbxTypeDoc");
                        NroDoc = request.getParameter("NmbNroDoc");
                        IsPep = request.getParameter("is_pep");

                        try {
                            counter = Integer.parseInt(request.getParameter("TxtCounterPersonBenf"));
                        } catch (Exception e) {
                            counter = 0;
                        }
                        Forms += "---";
                        if (counter != 0) {
                            ParcialForm = "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/0]";
                            for (int i = 2; i <= counter; i++) {
                                try {
                                    Names = request.getParameter("TxtName" + i + "");
                                    if (Names == null) {
                                        ParcialForm += "";
                                    } else {
                                        TypeDoc = request.getParameter("CbxTypeDoc" + i + "");
                                        NroDoc = request.getParameter("NmbNroDoc" + i + "");
                                        IsPep = request.getParameter("is_pep" + i + "");

                                        ParcialForm += "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + i + "]";
                                    }
                                } catch (Exception e) {
                                    ParcialForm += "";
                                }
                            }
                            Forms += ParcialForm + "]";
                        } else {
                            Forms += "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/0]]";
                        }

                        //</editor-fold>
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
//</editor-fold>
                        break;
                    case 8:
                        //<editor-fold defaultstate="collapsed" desc="PAYMENT CONDITIONS">
                        module = 6;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");

                        ValueAprov = request.getParameter("TxtValueAprov");
                        TimeDays = request.getParameter("TxtDays");
                        if (TimeDays.equals("Otro")) {
                            AlterText = request.getParameter("TxtOther");
                            TimeDays = "Otro/" + AlterText;
                        }
                        Names = request.getParameter("TxtNames");
                        Role = request.getParameter("TxtRole");
                        NroCel = request.getParameter("NmbCel");
                        MailFact = request.getParameter("TxtMailFact");

                        Forms = "[[" + module + "][" + ValueAprov + "][" + TimeDays + "][" + Names + "][" + Role + "][" + NroCel + "][" + MailFact + "]]";

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 9:
                        //<editor-fold defaultstate="collapsed" desc="TRIBUTARY INFORMATION">
                        module = 7;

                        String RespoIva = "",
                         AtRten = "",
                         GrnCont = "",
                         RegSim = "",
                         Renta = "",
                         ResOne = "",
                         ResTwo = "";

                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");

                        RespoIva = request.getParameter("RespIva");
                        AtRten = request.getParameter("AtRten");
                        GrnCont = request.getParameter("GrnCont");
                        RegSim = request.getParameter("RegSim");
                        Renta = request.getParameter("Renta");
                        Iva = request.getParameter("Iva");
                        Ica = request.getParameter("Ica");

                        if (AtRten.equals("Si")) {
                            ResOne = request.getParameter("NroResolOne");
                            AtRten += "/" + ResOne;
                        }
                        if (GrnCont.equals("Si")) {
                            ResTwo = request.getParameter("NroResolTwo");
                            GrnCont += "/" + ResTwo;
                        }

                        Forms = "[[" + module + "][" + RespoIva + "][" + AtRten + "][" + GrnCont + "][" + RegSim + "][" + Renta + "][" + Iva + "][" + Ica + "]]";

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 10:
                        //<editor-fold defaultstate="collapsed" desc="COMERCIAL INFORMATION">                                       
                        module = 8;
                        //<editor-fold defaultstate="collapsed" desc="COMERCIAL REFERENCES">
                        ParcialForm = "";
                        ValidAction = request.getParameter("TxtValidAction");
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));

                        Names = request.getParameter("TxtComercialRef");
                        Nit = request.getParameter("TxtNit");
                        Contact = request.getParameter("TxtConctac");
                        Mail = request.getParameter("TxtEmail");
                        Phone = request.getParameter("TxtPhone");

                        try {
                            counter = Integer.parseInt(request.getParameter("TxtCounterPerson"));
                        } catch (Exception e) {
                            counter = 0;
                        }
                        if (counter != 0) {
                            ParcialForm = "[" + Names + "/" + "" + Nit + "/" + "" + Contact + "/" + Mail + "/" + Phone + "/0]";
                            for (int i = 2; i <= counter; i++) {
                                try {
                                    Names = request.getParameter("TxtComercialRef" + i + "");
                                    if (Names == null) {
                                        ParcialForm += "";
                                    } else {
                                        Nit = request.getParameter("TxtNit" + i + "");
                                        Contact = request.getParameter("TxtConctac" + i + "");
                                        Mail = request.getParameter("TxtEmail" + i + "");
                                        Phone = request.getParameter("TxtPhone" + i + "");

                                        ParcialForm += "[" + Names + "/" + "" + Nit + "/" + "" + Contact + "/" + Mail + "/" + Phone + "/" + i + "]";
                                    }
                                } catch (Exception e) {
                                    ParcialForm += "";
                                }
                            }
                            Forms = "[[" + module + "]" + ParcialForm + "";
                        } else {
                            Forms = "[[" + module + "][" + Names + "/" + "" + Nit + "/" + "" + Contact + "/" + Mail + "/" + Phone + "/0]";
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="BANK REFERENCES">
                        Forms += "---";
                        ParcialForm = "";
                        ValidAction = request.getParameter("TxtValidAction");
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));

                        Names = request.getParameter("TxtBankRef");
                        TypeDoc = request.getParameter("CbxTypeDoc");
                        NroDoc = request.getParameter("NmbNroDoc");
                        Contact = request.getParameter("TxtContBank");
                        Phone = request.getParameter("TxtPhoneBank");
                        try {
                            counter = Integer.parseInt(request.getParameter("TxtCounterPersonBank"));
                        } catch (Exception e) {
                            counter = 0;
                        }
                        if (counter != 0) {
                            ParcialForm = "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + Contact + "/" + Phone + "/0]";
                            for (int i = 2; i <= counter; i++) {
                                try {
                                    Names = request.getParameter("TxtBankRef" + i + "");
                                    if (Names == null) {
                                        ParcialForm += "";
                                    } else {
                                        TypeDoc = request.getParameter("CbxTypeDoc" + i + "");
                                        NroDoc = request.getParameter("NmbNroDoc" + i + "");
                                        Contact = request.getParameter("TxtContBank" + i + "");
                                        Phone = request.getParameter("TxtPhoneBank" + i + "");
                                        ParcialForm += "[" + Names + "/" + TypeDoc + "/" + NroDoc + "/" + Contact + "/" + Phone + "/" + i + "]";
                                    }
                                } catch (Exception e) {
                                    ParcialForm += "";
                                }
                            }
                            Forms += ParcialForm + "]";
                        } else {
                            Forms += "[" + Names + "/" + TypeDoc + "/" + "" + NroDoc + "/" + Contact + "/" + Phone + "/0]]";
                        }
                        //</editor-fold>

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 11:
                        //<editor-fold defaultstate="collapsed" desc="FINANCIAL INFORMATION">
                        module = 9;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
//                    Entity = request.getParameter("TxtEntity");
//                    AccountType = request.getParameter("TxtAccountType");
//                    AccountNumb = request.getParameter("TxtAccountNumb");
                        ResourceOrigin = request.getParameter("TxtResourceOrigin");
                        CoinType = request.getParameter("TxtCoinType");
                        Assets = request.getParameter("TxtAssets");
                        Passives = request.getParameter("TxtPassives");
                        Heritage = request.getParameter("TxtHeritage");
                        Income = request.getParameter("TxtIncome");
                        Expenses = request.getParameter("TxtExpenses");
                        OtherIncome = request.getParameter("TxtOtherIncome");
                        ConceptIncome = request.getParameter("TxtConceptIncome");
//                    AnioReport = request.getParameter("TxtAnioReport");
//                    UndReport = request.getParameter("TxtUndReport");
                        Forms = "[[" + module + "][" + ResourceOrigin + "][" + CoinType + "][" + Assets + "][" + Passives + "][" + Heritage + "][" + Income + "][" + Expenses + "][" + OtherIncome + "][" + ConceptIncome + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
//
//                    //</editor-fold>
                        break;
                    case 12:
                        //<editor-fold defaultstate="collapsed" desc="INTERNATIONAL OPERATIONS">
                        module = 10;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");

                        MoneyOne = request.getParameter("Txt_money1");
                        MoneyTwo = request.getParameter("Txt_money2");
                        DetailOne = request.getParameter("Txt_detail1");

                        if (DetailOne.equals("")) {
                            DetailOne = "NA";
                        }

                        DetailTwo = request.getParameter("Txt_detail2");
                        if (DetailTwo.equals("")) {
                            DetailTwo = "NA";
                        }

                        Forms = "[[" + module + "][" + MoneyOne + "/" + DetailOne + "][" + MoneyTwo + "/" + DetailTwo + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 13:
                        //<editor-fold defaultstate="collapsed" desc="SUPPLY CHAIN ​​SECURITY AGREEMENT">
                        module = 11;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        ReadDoc = request.getParameter("Txt_ReadDoc");
                        try {
                            TypeSig = Integer.parseInt(request.getParameter("TypeSig"));
                            NroIdenti = Integer.parseInt(request.getParameter("NmbDocx").toString());
                            try {
                                IdSig = Integer.parseInt(request.getParameter("NbmIdSigna"));
                            } catch (Exception e) {
                                IdSig = 0;
                            }
                            try {
                                if (TypeSig == 1) {
                                    Signature = request.getParameter("TxtSignatureDraw");
                                } else if (TypeSig == 2) {
                                    Signature = request.getParameter("TxtSignatureWrite");
                                    TypeLtter = request.getParameter("TxtSigLetter");
                                    Signature = Signature + "/" + TypeLtter;
                                } else if (TypeSig == 3) {
                                    Signature = request.getParameter("TxtSignatureImg");
                                }
                                if (!Signature.equals("")) {
                                    if (IdSig <= 0) {
                                        result = DocumentJpa.DocumentSignatureRegisterAgree(IdDoc, TypeSig, Signature);
                                    } else {
                                        result = DocumentJpa.DocumentSignatureUpdate(IdSig, TypeSig, Signature);
                                    }
                                }
                            } catch (Exception e) {
                                Signature = "";
                            }
                            Forms = "[[" + module + "][" + ReadDoc + "][" + NroIdenti + "][" + dia + "][" + mes + "][" + anio + "]]";
                        } catch (Exception e) {
                            Forms = "[[" + module + "][" + ReadDoc + "]]";
                        }

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1&IdDoc=" + IdDoc + "").forward(request, response);
//</editor-fold>
                        break;
                    case 14:
                        //<editor-fold defaultstate="collapsed" desc="STATEMENTS">
                        module = 12;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        ReadDoc = request.getParameter("Txt_ReadDoc");
                        if (!ReadDoc.equals("")) {
                            ReadDoc = "1";
                        }
                        Forms = "[[" + module + "][" + ReadDoc + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
//</editor-fold>
                        break;
                    case 15:
                        //<editor-fold defaultstate="collapsed" desc="ATTACH">
                        module = 13;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        IdFiles = request.getParameter("txtIdFiles");
                        FilesDocs = request.getParameter("Txt_FilesDoc");
                        String[] NewFiles = {};
                        try {
                            FilesChange = request.getParameter("Txt_ValidNew");
                            if (FilesChange == "") {
                                FilesChange = "NA";
                            } else {
                                NewFiles = FilesChange.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            }
                        } catch (Exception e) {
                            FilesChange = "NA";
                        }
                        if (!FilesDocs.toString().equals("-ChangeState-")) {
                            if (FilesChange.equals("") || FilesChange.equals("NA")) {
                                //<editor-fold defaultstate="collapsed" desc="NEW FILES">

                                String[] DocData = FilesDocs.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                String[] FileData = IdFiles.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                for (int i = 0; i < FileData.length; i++) {
                                    FileToAttch += "[" + FileData[i] + "/" + DocData[i] + "]";
                                }
//</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="REPLACE FILES">

                                lst_DocumentJpa = DocumentJpa.ConsultDocumentFiles(IdDoc);
                                if (lst_DocumentJpa != null) {
                                    Object[] objDoc = (Object[]) lst_DocumentJpa.get(0);
                                    String[] docs = objDoc[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                                    String[] DataChange = FilesChange.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    String[] DocData = FilesDocs.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    int iterator = 0;
                                    for (int i = 0; i < docs.length; i++) {
                                        int idDocChan = Integer.parseInt(docs[i].split("/")[0]);
                                        try {
                                            int idDocx = Integer.parseInt(DataChange[iterator].split("/")[0]);
                                            if (idDocChan == idDocx) {
                                                for (int j = 0; j < NewFiles.length; j++) {
                                                    String[] NameNew = NewFiles[j].split("/");
                                                    String validNew = NameNew[1].toString();
                                                    validNew = validNew.replace("C:\\fakepath\\", "").replace(".", "///");
                                                    validNew = validNew.split("///")[0];
                                                    if (DocData[iterator].toString().contains(validNew)) {
                                                        FileToAttch += "[" + idDocx + "/" + DocData[iterator] + "]";
                                                        iterator++;
                                                    } else {
                                                        FileToAttch += "[" + docs[i] + "]";
                                                    }
                                                }
                                            } else {
                                                FileToAttch += "[" + docs[i] + "]";
                                            }
                                        } catch (Exception e) {
                                            FileToAttch += "[" + docs[i] + "]";
                                        }
                                    }
                                }
//</editor-fold>
                            }

                            Forms = "[[" + module + "][" + FileToAttch + "]]";
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
                            if (ValidAction.equals("2")) {
                                module++;
                            }
                            result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                            if (result) {
                                DocumentJpa.UpdateDocumentFiles(IdDoc, FileToAttch);
                            }
                        } else {
                            FinalForm = Format;
                            if (ValidAction.equals("2")) {
                                module++;
                            }
                            result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        }
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
//</editor-fold>
                        break;
                    case 16:
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE CLIENT">
                        String Title = "";
                        module = 14;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        TypeSig = Integer.parseInt(request.getParameter("TypeSig"));
                        Names = request.getParameter("TxtName");
                        Title = request.getParameter("TxtTitle");
                        NroIdenti = Integer.parseInt(request.getParameter("NmbDocx").toString());
                        try {
                            IdSig = Integer.parseInt(request.getParameter("NbmIdSigna"));
                        } catch (Exception e) {
                            IdSig = 0;
                        }

                        try {
                            if (TypeSig == 1) {
                                Signature = request.getParameter("TxtSignatureDraw");
                            } else if (TypeSig == 2) {
                                Signature = request.getParameter("TxtSignatureWrite");
                                TypeLtter = request.getParameter("TxtSigLetter");
                                Signature = Signature + "/" + TypeLtter;
                            } else if (TypeSig == 3) {
                                Signature = request.getParameter("TxtSignatureImg");
                            }
                            if (!Signature.equals("")) {
                                if (IdSig <= 0) {
                                    result = DocumentJpa.DocumentSignatureRegister(IdDoc, TypeSig, Signature);
                                } else {
                                    result = DocumentJpa.DocumentSignatureUpdate(IdSig, TypeSig, Signature);
                                }
                            }
                        } catch (Exception e) {
                            Signature = "";
                        }

                        Forms = "[[" + module + "][" + Names + "][" + NroIdenti + "]["+ Title +"]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 17:
                        //<editor-fold defaultstate="collapsed" desc="MODULE RETURN">
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        State = Integer.parseInt(request.getParameter("Sttate"));
                        result = DocumentJpa.UpdateDocumentStateTemplate(IdDoc, State);
                        request.setAttribute("DocumentStateBack", result);
                        request.setAttribute("IdDoc", IdDoc);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 18:
                        //<editor-fold defaultstate="collapsed" desc="CLOSE DOCUMENT">
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        result = DocumentJpa.UpdateDocumentStateFinal(IdDoc, 15, 3);
                        if (result) {
                            MailData.NotifyPlastitecDocumentEnd(NameSession);
                        }
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                }
                //</editor-fold>
            } else if (TypeFormat.contains("Due diligence")) {
                //<editor-fold defaultstate="collapsed" desc="DATA DUE DILIGENCE">
                String PostCode = "", ApliCode = "", Economy = "", oneQuest = "", twoQuest = "", threeQuest = "",
                        fourQuest = "", fiveQuest = "", sixQuest = "", Countries = "", Currency = "", Service = "", Subsidiaries = "",
                        CitiesSub = "", manager = "", EmailManager = "", Comercial = "", EmailComercial = "", TxtContac = "", EmailContac = "",
                        Payment = "", EmailPayment = "", Financial = "", TypeAccount = "", TxtPayment = "", Title = "";
                int IdManager = 0, IdComercial = 0, IdContac = 0, IdPayment = 0, Account = 0, Limit = 0;
                switch (opt) {
                    case 1:
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        request.setAttribute("IdDoc", IdDoc);
                        request.getRequestDispatcher("ClientSection.jsp").forward(request, response);
                        break;
                    case 2:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 0 - START STATE">
                        module = 0;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        TypeProc = request.getParameter("TxtTypeProc");
                        DateInit = request.getParameter("DateInit");
                        TypeThird = request.getParameter("TxtTypeThird");
                        if (TypeThird.equals("Otro")) {
                            AlterText = request.getParameter("TxtOther");
                            TypeThird = "Otro/" + AlterText;
                        }
                        ValidAction = request.getParameter("TxtValidAction");

                        Forms = "[[" + module + "][" + TypeProc + "][" + DateInit + "][" + TypeThird + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 3:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 1 - COMPANY INFORMATION">
                        module = 1;
                        IdDoc = Integer.parseInt(parseOrDefault(request.getParameter("IdDoc")));
                        BusinessName = parseOrDefault(request.getParameter("TxtNameBusi"));
                        NroIdentix = parseOrDefault(request.getParameter("NmbIndeti"));
                        Address = parseOrDefault(request.getParameter("TxtAddress"));
                        PostCode = parseOrDefault(request.getParameter("TxtPostCode"));
                        Country = parseOrDefault(request.getParameter("TxtCountry"));
                        City = parseOrDefault(request.getParameter("TxtCity"));
                        Phones = parseOrDefault(request.getParameter("TxtPhones"));
                        WebPage = parseOrDefault(request.getParameter("TxtWebPage"));
                        ApliCode = parseOrDefault(request.getParameter("TxtApplicableCode"));
                        Economy = parseOrDefault(request.getParameter("TxtEconomy"));
                        ValidAction = parseOrDefault(request.getParameter("TxtValidAction"));

                        Forms = "[[" + module + "][" + BusinessName + "][" + NroIdentix + "][" + Address + "][" + PostCode + "][" + Country + "][" + City + "][" + Phones + "][" + WebPage + "]"
                                + "[" + ApliCode + "][" + Economy + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 4:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 2 - SUPLEMENTARY INFORMATION">
                        module = 2;

                        oneQuest = request.getParameter("oneQuest");
                        twoQuest = request.getParameter("twoQuest");
                        Countries = getParamOrNA(request, "TxtCountries");
                        Currency = request.getParameter("TxtCurrency");
                        Service = request.getParameter("TxtService");
                        Subsidiaries = request.getParameter("TxtSubsidiaries");
                        CitiesSub = request.getParameter("TxtCitiesSub");
                        threeQuest = request.getParameter("threeQuest");
                        fourQuest = request.getParameter("fourQuest");
                        fiveQuest = request.getParameter("fiveQuest");
                        sixQuest = request.getParameter("sixQuest");
                        ValidAction = parseOrDefault(request.getParameter("TxtValidAction"));

                        Forms = "[[" + module + "][" + oneQuest + "][" + twoQuest + "][" + Countries + "][" + Currency + "][" + Service + "][" + Subsidiaries + "][" + CitiesSub + "][" + threeQuest + "]"
                                + "[" + fourQuest + "][" + fiveQuest + "][" + sixQuest + "]]";

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 5:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 3 - CONTACT">
                        module = 3;
                        manager = request.getParameter("TxtManager");
                        IdManager = Integer.parseInt(request.getParameter("NmbIdManager"));
                        EmailManager = request.getParameter("TxtEmailManager");
                        Comercial = request.getParameter("TxtComercial");
                        IdComercial = Integer.parseInt(request.getParameter("NmbIdComercial"));
                        EmailComercial = request.getParameter("TxtEmailComercial");
                        TxtContac = request.getParameter("TxtContac");
                        IdContac = Integer.parseInt(request.getParameter("NmbIdContac"));
                        EmailContac = request.getParameter("TxtEmailContac");
                        Payment = request.getParameter("TxtPayment");
                        IdPayment = Integer.parseInt(request.getParameter("NmbIdPayment"));
                        EmailPayment = request.getParameter("TxtEmailPayment");

                        ValidAction = parseOrDefault(request.getParameter("TxtValidAction"));
                        Forms = "[[" + module + "][" + manager + "][" + IdManager + "][" + EmailManager + "][" + Comercial + "][" + IdComercial + "][" + EmailComercial + "]"
                                + "[" + TxtContac + "][" + IdContac + "][" + EmailContac + "][" + Payment + "][" + IdPayment + "][" + EmailPayment + "]]";

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 6:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 4 - FINANCIAL INFORMATION">
                        module = 4;

                        Financial = request.getParameter("TxtFinancial");
                        TypeAccount = request.getParameter("TxtTypeAccount");
                        if (TypeAccount.equals("Other")) {
                            TypeAccount = request.getParameter("txtOtherOne");
                        }
                        Account = Integer.parseInt(request.getParameter("NmbAccount"));
                        Limit = Integer.parseInt(request.getParameter("NmbLimit"));
                        TxtPayment = request.getParameter("TxtPayment");

                        ValidAction = parseOrDefault(request.getParameter("TxtValidAction"));
                        Forms = "[[" + module + "][" + Financial + "][" + TypeAccount + "][" + Account + "][" + Limit + "][" + TxtPayment + "]]";

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 13:
                        //<editor-fold defaultstate="collapsed" desc="SUPPLY CHAIN ​​SECURITY AGREEMENT">
                        module = 5;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        ReadDoc = request.getParameter("Txt_ReadDoc");
                        try {
                            TypeSig = Integer.parseInt(request.getParameter("TypeSig"));
                            NroIdenti = Integer.parseInt(request.getParameter("NmbDocx").toString());
                            try {
                                IdSig = Integer.parseInt(request.getParameter("NbmIdSigna"));
                            } catch (Exception e) {
                                IdSig = 0;
                            }
                            try {
                                if (TypeSig == 1) {
                                    Signature = request.getParameter("TxtSignatureDraw");
                                } else if (TypeSig == 2) {
                                    Signature = request.getParameter("TxtSignatureWrite");
                                    TypeLtter = request.getParameter("TxtSigLetter");
                                    Signature = Signature + "/" + TypeLtter;
                                } else if (TypeSig == 3) {
                                    Signature = request.getParameter("TxtSignatureImg");
                                }
                                if (!Signature.equals("")) {
                                    if (IdSig <= 0) {
                                        result = DocumentJpa.DocumentSignatureRegisterAgree(IdDoc, TypeSig, Signature);
                                    } else {
                                        result = DocumentJpa.DocumentSignatureUpdate(IdSig, TypeSig, Signature);
                                    }
                                }
                            } catch (Exception e) {
                                Signature = "";
                            }
                            Forms = "[[" + module + "][" + ReadDoc + "][" + NroIdenti + "][" + dia + "][" + mes + "][" + anio + "]]";
                        } catch (Exception e) {
                            Forms = "[[" + module + "][" + ReadDoc + "]]";
                        }

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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1&IdDoc=" + IdDoc + "").forward(request, response);
//</editor-fold>
                        break; 
                    case 8:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 6 - STATEMENTS">
                        module = 6;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ReadDoc = request.getParameter("Txt_ReadDoc");
                        DescSource = request.getParameter("txtDescSource");
                        ValidAction = request.getParameter("TxtValidAction");
                        if (!ReadDoc.equals("")) {
                            ReadDoc = "1";
                        }
                        Forms = "[[" + module + "][" + ReadDoc + "][" + DescSource + "]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
//</editor-fold>
                        break;
                    case 15:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 7 - ATTACH">
                        module = 7;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        IdFiles = request.getParameter("txtIdFiles");
                        FilesDocs = request.getParameter("Txt_FilesDoc");
                        String[] NewFiles = {};
                        try {
                            FilesChange = request.getParameter("Txt_ValidNew");
                            if (FilesChange == "") {
                                FilesChange = "NA";
                            } else {
                                NewFiles = FilesChange.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            }
                        } catch (Exception e) {
                            FilesChange = "NA";
                        }
                        if (!FilesDocs.toString().equals("-ChangeState-")) {
                            if (FilesChange.equals("") || FilesChange.equals("NA")) {
                                //<editor-fold defaultstate="collapsed" desc="NEW FILES">

                                String[] DocData = FilesDocs.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                String[] FileData = IdFiles.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                for (int i = 0; i < FileData.length; i++) {
                                    FileToAttch += "[" + FileData[i] + "/" + DocData[i] + "]";
                                }
//</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="REPLACE FILES">

                                lst_DocumentJpa = DocumentJpa.ConsultDocumentFiles(IdDoc);
                                if (lst_DocumentJpa != null) {
                                    Object[] objDoc = (Object[]) lst_DocumentJpa.get(0);
                                    String[] docs = objDoc[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                                    String[] DataChange = FilesChange.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    String[] DocData = FilesDocs.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    int iterator = 0;
                                    for (int i = 0; i < docs.length; i++) {
                                        int idDocChan = Integer.parseInt(docs[i].split("/")[0]);
                                        try {
                                            int idDocx = Integer.parseInt(DataChange[iterator].split("/")[0]);
                                            if (idDocChan == idDocx) {
                                                for (int j = 0; j < NewFiles.length; j++) {
                                                    String[] NameNew = NewFiles[j].split("/");
                                                    String validNew = NameNew[1].toString();
                                                    validNew = validNew.replace("C:\\fakepath\\", "").replace(".", "///");
                                                    validNew = validNew.split("///")[0];
                                                    if (DocData[iterator].toString().contains(validNew)) {
                                                        FileToAttch += "[" + idDocx + "/" + DocData[iterator] + "]";
                                                        iterator++;
                                                    } else {
                                                        FileToAttch += "[" + docs[i] + "]";
                                                    }
                                                }
                                            } else {
                                                FileToAttch += "[" + docs[i] + "]";
                                            }
                                        } catch (Exception e) {
                                            FileToAttch += "[" + docs[i] + "]";
                                        }
                                    }
                                }
//</editor-fold>
                            }

                            Forms = "[[" + module + "][" + FileToAttch + "]]";
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
                            if (ValidAction.equals("2")) {
                                module++;
                            }
                            result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                            if (result) {
                                DocumentJpa.UpdateDocumentFiles(IdDoc, FileToAttch);
                            }
                        } else {
                            FinalForm = Format;
                            if (ValidAction.equals("2")) {
                                module++;
                            }
                            result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        }
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
//</editor-fold>
                        break;
                    case 16:
                        //<editor-fold defaultstate="collapsed" desc="MODULE 8 - SIGNATURE CLIENT">
                        module = 8;
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        ValidAction = request.getParameter("TxtValidAction");
                        TypeSig = Integer.parseInt(request.getParameter("TypeSig"));
                        Names = request.getParameter("TxtName");
                        Title = request.getParameter("TxtTitle");
                        NroIdenti = Integer.parseInt(request.getParameter("NmbDocx").toString());
                        try {
                            IdSig = Integer.parseInt(request.getParameter("NbmIdSigna"));
                        } catch (Exception e) {
                            IdSig = 0;
                        }

                        try {
                            if (TypeSig == 1) {
                                Signature = request.getParameter("TxtSignatureDraw");
                            } else if (TypeSig == 2) {
                                Signature = request.getParameter("TxtSignatureWrite");
                                TypeLtter = request.getParameter("TxtSigLetter");
                                Signature = Signature + "/" + TypeLtter;
                            } else if (TypeSig == 3) {
                                Signature = request.getParameter("TxtSignatureImg");
                            }
                            if (!Signature.equals("")) {
                                if (IdSig <= 0) {
                                    result = DocumentJpa.DocumentSignatureRegister(IdDoc, TypeSig, Signature);
                                } else {
                                    result = DocumentJpa.DocumentSignatureUpdate(IdSig, TypeSig, Signature);
                                }
                            }
                        } catch (Exception e) {
                            Signature = "";
                        }

                        Forms = "[[" + module + "][" + Names + "][" + NroIdenti + "]["+ Title +"]]";
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
                        if (ValidAction.equals("2")) {
                            module++;
                        }
                        result = DocumentJpa.UpdateDocumentFormClient(IdDoc, FinalForm, module);
                        request.setAttribute("UpdateFormClient", result);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 17:
                        //<editor-fold defaultstate="collapsed" desc="MODULE RETURN">
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        State = Integer.parseInt(request.getParameter("Sttate"));
                        result = DocumentJpa.UpdateDocumentStateTemplate(IdDoc, State);
                        request.setAttribute("DocumentStateBack", result);
                        request.setAttribute("IdDoc", IdDoc);
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                    case 18:
                        //<editor-fold defaultstate="collapsed" desc="CLOSE DOCUMENT">
                        IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                        result = DocumentJpa.UpdateDocumentStateFinal(IdDoc, 9, 3);
                        if (result) {
                            MailData.NotifyPlastitecDocumentEnd(NameSession);
                        }
                        request.getRequestDispatcher("ClientSection?opt=1").forward(request, response);
                        //</editor-fold>
                        break;
                }
                //</editor-fold>
            }

        } catch (Exception e) {
            request.getRequestDispatcher("ClientSection.jsp").forward(request, response);
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

    private String getParamOrNA(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return (value == null || value.trim().isEmpty()) ? "N/a" : value;
    }
}
