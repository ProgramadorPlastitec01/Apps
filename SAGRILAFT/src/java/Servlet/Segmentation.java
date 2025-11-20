package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.SegmentationControllerJpa;
import Controller.EventControllerJpa;
import Controller.ConfigurationControllerJpa;
import java.util.List;

public class Segmentation extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        SegmentationControllerJpa SegmentationJpa = new SegmentationControllerJpa();
        EventControllerJpa EventJpa = new EventControllerJpa();
        ConfigurationControllerJpa ConfigJpa = new ConfigurationControllerJpa();
        int opt = 0, IdSegmentation = 0, Temp = 0, Qualification = 0, Experience = 0, Relationship = 0, Burden = 0,
                BaselIndex = 0, CorruptionIndex = 0, BiberyIndex = 0, State = 0, IdVisit = 0, Code = 0, Validity = 0,
                AnnualFrequecy = 0, Antiquity = 0, Report = 0, idDoc = 0;
        String Format = "", Observation = "", Date = "", PEP = "", Affair = "", Description = "", FileDocs = "", Area = "",
                TextFilter = "", InitialDate = "", EndDate = "", ConsultMysql = "", ContacPerson = "", PerformsPost = "",
                SupplyChain = "", BeneficiaryFinal = "", TypeServiceOffered = "", BusinessAssociate = "", TypePerson = "", nameCompany = "";
        boolean Result = false;
        long ValueSalesPurchases = 0;
        List lst_filter = null, lst_query = null;
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE SETTING">
                    try {
                        IdSegmentation = Integer.parseInt(request.getParameter("IdSegmentation"));
                    } catch (NumberFormatException e) {
                        IdSegmentation = 0;
                    }
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (NumberFormatException e) {
                        Temp = 0;
                    }
                    try {
                        Format = request.getParameter("Format");
                    } catch (Exception e) {
                        Format = "";
                    }
                    try {
                        IdVisit = Integer.parseInt(request.getParameter("IdVisit"));
                    } catch (NumberFormatException e) {
                        IdVisit = 0;
                    }
                    request.setAttribute("IdSegmentation", IdSegmentation);
                    request.setAttribute("Temp", Temp);
                    request.setAttribute("Format", Format);
                    request.setAttribute("IdVisit", IdVisit);
                    request.getRequestDispatcher("Segmentation.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE CONTROL SEGMENTATION">
                    try {
                        IdSegmentation = Integer.parseInt(request.getParameter("IdSegmentation"));
                    } catch (Exception e) {
                        IdSegmentation = 0;
                    }
                    try {
                        idDoc = Integer.parseInt(request.getParameter("idDoc"));
                    } catch (Exception e) {
                        idDoc = 0;
                    }
                    try {
                        Format = request.getParameter("Format");
                    } catch (Exception e) {
                        Format = "";
                    }
                    nameCompany = request.getParameter("txtName");
                    Code = Integer.parseInt(request.getParameter("Code"));
                    Area = request.getParameter("Txt_Area");
                    Date = request.getParameter("Txt_Date");
                    PEP = request.getParameter("Txt_Pep");
                    Qualification = Integer.parseInt(request.getParameter("Qualification"));
                    Experience = Integer.parseInt(request.getParameter("Experience"));
                    Relationship = Integer.parseInt(request.getParameter("Relationship"));
                    Burden = Integer.parseInt(request.getParameter("Burden"));
                    ContacPerson = request.getParameter("Txt_ContacPerson");
                    PerformsPost = request.getParameter("Txt_PerformsPost");
                    AnnualFrequecy = Integer.parseInt(request.getParameter("AnnualFrequecy"));
                    ValueSalesPurchases = Long.parseLong(request.getParameter("ValueSalesPurchases"));
                    Antiquity = Integer.parseInt(request.getParameter("Antiquity"));
                    SupplyChain = request.getParameter("Txt_SupplyChain");
                    TypePerson = request.getParameter("Txt_TypePerson");
                    BeneficiaryFinal = request.getParameter("Txt_BeneficiaryFinal");
                    TypeServiceOffered = request.getParameter("Txt_TypeServiceOffered");
                    Observation = request.getParameter("Txt_Observation");
                    if (Format.equals("INTERNATIONAL")) {
                        BaselIndex = Integer.parseInt(request.getParameter("BaselIndex"));
                        CorruptionIndex = Integer.parseInt(request.getParameter("CorruptionIndex"));
                        BiberyIndex = Integer.parseInt(request.getParameter("BiberyIndex"));
                        Result = SegmentationJpa.UpdateControlSegmentationInternational(IdSegmentation, Code, Area, TypePerson, Date, PEP, Qualification, Experience, Relationship, Burden, BaselIndex,
                                CorruptionIndex, BiberyIndex, ContacPerson, PerformsPost, AnnualFrequecy, ValueSalesPurchases, Antiquity, SupplyChain, BeneficiaryFinal, TypeServiceOffered, Observation, idDoc, nameCompany);
                        request.setAttribute("SegmentationRegisterINT", Result);
                    } else {
                        Result = SegmentationJpa.UpdateControlSegmentation(IdSegmentation, Code, Area, TypePerson, Date, PEP, Qualification, Experience, Relationship, Burden, ContacPerson, PerformsPost, 
                                AnnualFrequecy, ValueSalesPurchases, Antiquity, SupplyChain, BeneficiaryFinal, TypeServiceOffered, Observation, idDoc, nameCompany);
                        request.setAttribute("SegmentationRegister", Result);
                    }
                    request.getRequestDispatcher("Segmentation?opt=1&IdSegmentation=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATESTATE">
                    try {
                        IdSegmentation = Integer.parseInt(request.getParameter("IdSegmentation"));
                    } catch (Exception e) {
                        IdSegmentation = 0;
                    }
                    State = Integer.parseInt(request.getParameter("State"));
                    Result = SegmentationJpa.UpdateStateSegmentation(IdSegmentation, State);
                    request.setAttribute("SegmentationUpdateState", Result);
                    request.getRequestDispatcher("Segmentation?opt=1&IdSegmentation=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER - UPDATE VISIT - ATTACH">
                    try {
                        IdSegmentation = Integer.parseInt(request.getParameter("IdSegmentation"));
                    } catch (NumberFormatException e) {
                        IdSegmentation = 0;
                    }
                    try {
                        Format = request.getParameter("Format");
                    } catch (Exception e) {
                        Format = "";
                    }
                    try {
                        IdVisit = Integer.parseInt(request.getParameter("IdVisit"));
                    } catch (NumberFormatException e) {
                        IdVisit = 0;
                    }
                    Date = request.getParameter("Txt_Date");
                    Affair = request.getParameter("Txt_Affair");
                    Description = request.getParameter("Txt_Description");
                    FileDocs = request.getParameter("Txt_FilesDoc");
                    if (IdVisit > 0) {
                        Result = EventJpa.UpdateEvent(IdVisit, Date, Affair, Description, FileDocs);
                        request.setAttribute("VisitUpdate", Result);
                        request.getRequestDispatcher("Segmentation?opt=1&IdSegmentation=" + IdSegmentation + "&Format=" + Format + "&Temp=1&IdVisit=0").forward(request, response);
                    } else {
                        Result = EventJpa.RegisterEvent(IdSegmentation, Date, Affair, Description, FileDocs, "ADMINISTRADOR");
                        request.setAttribute("VisitRegistrer", Result);
                        request.getRequestDispatcher("Segmentation?opt=1&IdSegmentation=" + IdSegmentation + "&Format=" + Format + "&Temp=1").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="FILTER SEGMENTATION">
                    try {
                        Format = request.getParameter("Format");
                    } catch (Exception e) {
                        Format = "";
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("State"));
                    } catch (NumberFormatException e) {
                        State = 2;
                    }
                    try {
                        Validity = Integer.parseInt(request.getParameter("Validity"));
                    } catch (NumberFormatException e) {
                        Validity = 0;
                    }
                    try {
                        InitialDate = request.getParameter("InitialDate");
                    } catch (Exception e) {
                        InitialDate = "";
                    }
                    try {
                        EndDate = request.getParameter("EndDate");
                    } catch (Exception e) {
                        EndDate = "";
                    }
                    try {
                        TextFilter = request.getParameter("TextFilter");
                    } catch (Exception e) {
                        TextFilter = "";
                    }
                    try {
                        IdSegmentation = Integer.parseInt(request.getParameter("IdSegmentation"));
                    } catch (NumberFormatException e) {
                        IdSegmentation = 0;
                    }
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (NumberFormatException e) {
                        Temp = 0;
                    }
                    try {
                        IdVisit = Integer.parseInt(request.getParameter("IdVisit"));
                    } catch (NumberFormatException e) {
                        IdVisit = 0;
                    }
                    lst_filter = ConfigJpa.ConsultSettingsByCategorie("ConsultFilterMysql");
                    if (lst_filter != null) {
                        Object[] Obj_mysql = (Object[]) lst_filter.get(0);
                        ConsultMysql = Obj_mysql[3].toString();
                    }
                    if (!ConsultMysql.equals("")) {
                        if (!Format.equals("")) {
                            ConsultMysql = ConsultMysql + "s.Format = '" + Format + "'";
                        }
                        if (State < 3) {
                            ConsultMysql = ConsultMysql + " AND s.State = " + State;
                        }
                        if (Validity > 0) {
                            switch (Validity) {
                                case 1:
                                    ConsultMysql = ConsultMysql + " AND (DATEDIFF(DATE(NOW()),s.DateAssessment) < 300) ";
                                    break;
                                case 2:
                                    ConsultMysql = ConsultMysql + " AND (DATEDIFF(DATE(NOW()),s.DateAssessment) > 300 AND DATEDIFF(DATE(NOW()),s.DateAssessment) < 365) ";
                                    break;
                                case 3:
                                    ConsultMysql = ConsultMysql + " AND (DATEDIFF(DATE(NOW()),s.DateAssessment) > 365)";
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (!InitialDate.equals("") && !EndDate.equals("")) {
                            ConsultMysql = ConsultMysql + " AND s.DateAssessment BETWEEN '" + InitialDate.replace("/", "-") + "' AND '" + EndDate.replace("/", "-") + "'";
                        }
                        if (!TextFilter.equals("")) {
                            ConsultMysql = ConsultMysql + " AND s.PlastitecCode LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.Area LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.NitTax LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.TypeBusiness LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.BusinessAssociate LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.PersonType LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.PEP LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.CountryCity LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.LegalRepresentative LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.ContactPerson LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.TypeServiceOffered LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.DateAssessment LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.PerformsPost LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.BeneficiaryFinal LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.Observation LIKE CONCAT('%','" + TextFilter + "','%') ";
                        }
                        ConsultMysql = ConsultMysql + " ORDER BY Days DESC";
                    }
                    request.setAttribute("IdSegmentation", IdSegmentation);
                    request.setAttribute("Temp", Temp);
                    request.setAttribute("Format", Format);
                    request.setAttribute("Query", ConsultMysql);
                    request.setAttribute("IdVisit", IdVisit);
                    request.getRequestDispatcher("Segmentation.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="MODULE REPORT - R-SEG-SGLT-003">
                    try {
                        Format = request.getParameter("Format");
                    } catch (Exception e) {
                        Format = "";
                    }
                    try {
                        Report = Integer.parseInt(request.getParameter("Report"));
                    } catch (NumberFormatException e) {
                        Report = 0;
                    }
                    try {
                        BusinessAssociate = request.getParameter("BusinessAssociate");
                    } catch (Exception e) {
                        BusinessAssociate = "";
                    }
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (NumberFormatException e) {
                        Temp = 0;
                    }
                    request.setAttribute("Temp", Temp);
                    request.setAttribute("Format", Format);
                    request.setAttribute("Report", Report);
                    request.setAttribute("BusinessAssociate", BusinessAssociate);
                    request.getRequestDispatcher("Report.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="FILTER SEGMENTATION REPORT">
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (NumberFormatException e) {
                        Temp = 0;
                    }
                    try {
                        Report = Integer.parseInt(request.getParameter("Report"));
                    } catch (NumberFormatException e) {
                        Report = 0;
                    }
                    try {
                        Format = request.getParameter("Format");
                    } catch (Exception e) {
                        Format = "";
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("State"));
                    } catch (NumberFormatException e) {
                        State = 2;
                    }
                    try {
                        Validity = Integer.parseInt(request.getParameter("Validity"));
                    } catch (NumberFormatException e) {
                        Validity = 0;
                    }
                    try {
                        InitialDate = request.getParameter("InitialDate");
                    } catch (Exception e) {
                        InitialDate = "";
                    }
                    try {
                        EndDate = request.getParameter("EndDate");
                    } catch (Exception e) {
                        EndDate = "";
                    }
                    try {
                        TextFilter = request.getParameter("TextFilter");
                    } catch (Exception e) {
                        TextFilter = "";
                    }
                    int Count = 0;
                    lst_filter = ConfigJpa.ConsultSettingsByCategorie("ConsultFilterMysql");
                    if (lst_filter != null) {
                        Object[] Obj_mysql = (Object[]) lst_filter.get(0);
                        ConsultMysql = Obj_mysql[3].toString();
                    }
                    if (!ConsultMysql.equals("")) {
                        if (Format.equals("TODAS")) {
                        } else {
                            if (!Format.equals("")) {
                                ConsultMysql = ConsultMysql + "s.Format = '" + Format + "'";
                                Count++;
                            }
                        }
                        if (State < 2) {
                            if (Count == 0) {
                                ConsultMysql = ConsultMysql + " s.State = " + State;
                            } else {
                                ConsultMysql = ConsultMysql + " AND s.State = " + State;
                            }
                        } else {
                            if (State == 0) {
                                if (Count > 0) {
                                    ConsultMysql = ConsultMysql + " AND s.State = 0";
                                } else {
                                    ConsultMysql = ConsultMysql + " s.State = 0";
                                }
                            } else {
                                if (Count > 0) {
                                    ConsultMysql = ConsultMysql + " AND s.State = 1";
                                } else {
                                    ConsultMysql = ConsultMysql + " s.State = 1";
                                }
                            }
                        }
                        if (Validity > 0) {
                            switch (Validity) {
                                case 1:
                                    ConsultMysql = ConsultMysql + " AND (DATEDIFF(DATE(NOW()),s.DateAssessment) < 300) ";
                                    break;
                                case 2:
                                    ConsultMysql = ConsultMysql + " AND (DATEDIFF(DATE(NOW()),s.DateAssessment) > 300 AND DATEDIFF(DATE(NOW()),s.DateAssessment) < 365) ";
                                    break;
                                case 3:
                                    ConsultMysql = ConsultMysql + " AND (DATEDIFF(DATE(NOW()),s.DateAssessment) > 365)";
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (!InitialDate.equals("") && !EndDate.equals("")) {
                            ConsultMysql = ConsultMysql + " AND s.DateAssessment BETWEEN '" + InitialDate.replace("/", "-") + "' AND '" + EndDate.replace("/", "-") + "'";
                        }
                        if (!TextFilter.equals("")) {
                            ConsultMysql = ConsultMysql + " AND (s.PlastitecCode LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.Area LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.NitTax LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.TypeBusiness LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.BusinessAssociate LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.PersonType LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.PEP LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.CountryCity LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.LegalRepresentative LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.ContactPerson LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.TypeServiceOffered LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.DateAssessment LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.PerformsPost LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.BeneficiaryFinal LIKE CONCAT('%','" + TextFilter + "','%') OR "
                                    + "		s.Observation LIKE CONCAT('%','" + TextFilter + "','%')) ";
                        }
                        ConsultMysql = ConsultMysql + " ORDER BY BusinessAssociate ASC";
                    }
                    request.setAttribute("ConsultQuery", ConsultMysql);
                    request.setAttribute("Temp", Temp);
                    request.setAttribute("Report", Report);
                    request.getRequestDispatcher("Report.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException | NumberFormatException | ServletException ex) {
            request.getRequestDispatcher("Segmentation.jsp").forward(request, response);
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
