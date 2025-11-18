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
                DetailTwo = "", ReadDoc = "", FilesDocs = "", IdFiles = "", FileToAttch = "", FilesChange = "", Signature = "", TypeLtter = "", NroIdentix = "";
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
//        String dir_nameAtt = getServletContext().getRealPath("\\Interfaz\\Contenido\\SagrilaftDocs\\Attach\\");
//        String dir_nameSig = getServletContext().getRealPath("\\Interfaz\\Contenido\\SagrilaftDocs\\Signature\\");
//        String dir_nameAtt = getServletContext().getRealPath("/Interfaz/Contenido/SagrilaftDocs/Attach/");
//        String dir_nameSig = getServletContext().getRealPath("/Interfaz/Contenido/SagrilaftDocs/Signature/");
//        File dir_nameAtt = new File(getServletContext().getRealPath("/Interfaz/Contenido/SagrilaftDocs/Attach/"));
//        File dir_nameSig = new File(getServletContext().getRealPath("/Interfaz/Contenido/SagrilaftDocs/Signature/"));
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            try {
                Format = request.getParameter("TxtFormat");
                DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
            } catch (Exception e) {
                IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                lst_DocumentJpa = DocumentJpa.ConsultDocumentsId(IdDoc);
                if (lst_DocumentJpa != null) {
                    Object[] ObjDoc = (Object[]) lst_DocumentJpa.get(0);
                    Format = ObjDoc[3].toString();
                    DtaFormat = Format.toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                }
            }
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
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    BusinessName = request.getParameter("TxtNameBusi");
                    NroIdentix = request.getParameter("NmbIndeti");
                    NroDv = request.getParameter("TxtDv");
                    Country = request.getParameter("TxtCountry");
                    City = request.getParameter("TxtCity");
                    Address = request.getParameter("TxtAddress");
                    Phones = request.getParameter("TxtPhones");
                    Mail = request.getParameter("TxtMail");
                    WebPage = request.getParameter("TxtWebPage");
                    PostalCode = request.getParameter("TxtPostalCode");
                    CodeCiiu_1 = request.getParameter("CbxCiiu1");
                    CodeCiiu_2 = request.getParameter("CbxCiiu2");
                    NroComercial = request.getParameter("TxtNroComercial");
                    TypeCompany = request.getParameter("TypeCompany");
                    ClasiCompany = request.getParameter("TxtClasiCompany");
                    ValidAction = request.getParameter("TxtValidAction");

                    Forms = "[[" + module + "][" + BusinessName + "][" + NroIdentix + "][" + NroDv + "][" + Country + "][" + City + "][" + Address + "][" + Phones + "][" + Mail + "]"
                            + "[" + WebPage + "][" + PostalCode + "][" + CodeCiiu_1 + "][" + CodeCiiu_2 + "][" + NroComercial + "][" + TypeCompany + "][" + ClasiCompany + "]]";
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
                    //<editor-fold defaultstate="collapsed" desc="TRIBUTARY INFORMATION">
                    module = 3;
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    ValidAction = request.getParameter("TxtValidAction");
                    Iva = request.getParameter("TxtIva");
                    if (Iva.equals("Otro")) {
                        AlterText = request.getParameter("TxtOther");
                        Iva = "Otro/" + AlterText;
                    }
                    try {
                        Resolution = request.getParameter("TxtResolution");

                    } catch (Exception e) {
                        Resolution = "NA";
                    }

                    SelfRetaining = request.getParameter("TxtRetaining");
                    if (SelfRetaining.contains("Otro")) {
                        AlterText = request.getParameter("TxtOther2");
                        SelfRetaining = "Otro/" + AlterText;
                    } else if (SelfRetaining.contains("Si")) {
                        ReteSource = request.getParameter("TxtReteSource");
                        if (ReteSource.equals("Si")) {
                            ValueReteSource = request.getParameter("TxtOther3");
                        }
                        SelfRetaining = SelfRetaining + "/" + ReteSource + "/" + ValueReteSource;
                    }
                    DataRetaining = request.getParameter("TxtDataRetain");
                    if (DataRetaining.equals("Otro")) {
                        AlterText = request.getParameter("TxtOther4");
                        DataRetaining = "Otro/" + AlterText;
                    }
                    Ica = request.getParameter("TxtIca");
                    TributaryCity = request.getParameter("TxtCityTri");
                    UserZone = request.getParameter("TxtUserZone");

                    Forms = "[[" + module + "][" + Iva + "][" + Resolution + "][" + SelfRetaining + "][" + DataRetaining + "][" + Ica + "][" + TributaryCity + "][" + UserZone + "]]";

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
                    //<editor-fold defaultstate="collapsed" desc="PAYMENT CONDITIONS">
                    module = 4;
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
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="LEGAL REPRESENTATIVE">
                    module = 5;
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    ValidAction = request.getParameter("TxtValidAction");
                    Names = request.getParameter("TxtNames");
                    LastName = request.getParameter("TxtLastName");
                    TypeDoc = request.getParameter("CbxTypeDoc");
                    NroDoc = request.getParameter("NmbNroDoc");
                    DateDocum = request.getParameter("TxtDate");
                    Place = request.getParameter("TxtPlace");
                    Phones = request.getParameter("TxtPhones");
                    Mail = request.getParameter("TxtMail");
                    Forms = "[[" + module + "][" + Names + "][" + LastName + "][" + TypeDoc + "/" + NroDoc + "][" + DateDocum + "/" + Place + "][" + Phones + "][" + Mail + "]]";
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
                    //<editor-fold defaultstate="collapsed" desc="SHAREHOLDING STRUCTURE">
                    module = 6;
                    String ParcialForm = "";
                    ValidAction = request.getParameter("TxtValidAction");
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    Names = request.getParameter("TxtName");
                    TypeDoc = request.getParameter("CbxTypeDoc");
                    NroDoc = request.getParameter("NmbNroDoc");
                    IsPep = request.getParameter("is_pep");
                    Participaction = request.getParameter("TxtPart");
                    try {
                        counter = Integer.parseInt(request.getParameter("TxtCounterPerson"));
                    } catch (Exception e) {
                        counter = 0;
                    }
                    if (counter != 0) {
                        ParcialForm = "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + "" + Participaction + "/0]";
                        for (int i = 2; i <= counter; i++) {
                            try {
                                Names = request.getParameter("TxtName" + i + "");
                                if (Names == null) {
                                    ParcialForm += "";
                                } else {
                                    TypeDoc = request.getParameter("CbxTypeDoc" + i + "");
                                    NroDoc = request.getParameter("NmbNroDoc" + i + "");
                                    IsPep = request.getParameter("is_pep" + i + "");
                                    Participaction = request.getParameter("TxtPart" + i + "");
                                    ParcialForm += "[" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + "" + Participaction + "/" + i + "]";
                                }
                            } catch (Exception e) {
                                ParcialForm += "";
                            }
                        }
                        Forms = "[[" + module + "]" + ParcialForm + "]";
                    } else {
                        Forms = "[[" + module + "][" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/" + "" + Participaction + "/0]]";
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
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="FINAL BENEFICIARIES">
                    module = 7;
                    ParcialForm = "";
                    ValidAction = request.getParameter("TxtValidAction");
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    Names = request.getParameter("TxtName");
                    TypeDoc = request.getParameter("CbxTypeDoc");
                    NroDoc = request.getParameter("NmbNroDoc");
                    IsPep = request.getParameter("is_pep");
                    try {
                        counter = Integer.parseInt(request.getParameter("TxtCounterPerson"));
                    } catch (Exception e) {
                        counter = 0;
                    }
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
                        Forms = "[[" + module + "]" + ParcialForm + "]";
                    } else {
                        Forms = "[[" + module + "][" + Names + "/" + "" + TypeDoc + "/" + "" + NroDoc + "/" + "" + IsPep + "/0]]";
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
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="FINANCIAL INFORMATION">
                    module = 8;
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    ValidAction = request.getParameter("TxtValidAction");
                    Entity = request.getParameter("TxtEntity");
                    AccountType = request.getParameter("TxtAccountType");
                    AccountNumb = request.getParameter("TxtAccountNumb");
                    ResourceOrigin = request.getParameter("TxtResourceOrigin");
                    CoinType = request.getParameter("TxtCoinType");
                    Assets = request.getParameter("TxtAssets");
                    Passives = request.getParameter("TxtPassives");
                    Heritage = request.getParameter("TxtHeritage");
                    Income = request.getParameter("TxtIncome");
                    Expenses = request.getParameter("TxtExpenses");
                    OtherIncome = request.getParameter("TxtOtherIncome");
                    ConceptIncome = request.getParameter("TxtConceptIncome");
                    AnioReport = request.getParameter("TxtAnioReport");
                    UndReport = request.getParameter("TxtUndReport");
                    Forms = "[[" + module + "][" + Entity + "][" + AccountType + "][" + AccountNumb + "][" + ResourceOrigin + "][" + CoinType + "][" + Assets + "][" + Passives + "][" + Heritage + "][" + Income + "][" + Expenses + "][" + OtherIncome + "][" + ConceptIncome + "][" + AnioReport + "][" + UndReport + "]]";
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
                    //<editor-fold defaultstate="collapsed" desc="POLITICALLY EXPOSED PERSON">

                    module = 9;
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
                    module = 14;
                    IdDoc = Integer.parseInt(request.getParameter("IdDoc"));
                    ValidAction = request.getParameter("TxtValidAction");
                    TypeSig = Integer.parseInt(request.getParameter("TypeSig"));
                    Names = request.getParameter("TxtName");
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

                    Forms = "[[" + module + "][" + Names + "][" + NroIdenti + "]]";
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

}
