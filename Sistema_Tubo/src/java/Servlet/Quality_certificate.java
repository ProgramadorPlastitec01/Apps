package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.CertificadoCalidadJpaController;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class Quality_certificate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            HttpSession sesion = request.getSession();
            CertificadoCalidadJpaController CertificateJpa = new CertificadoCalidadJpaController();
            String UserName = sesion.getAttribute("Nombres").toString();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            PrintWriter out = response.getWriter();
            int opc = Integer.parseInt(request.getParameter("opc"));
            List lst_summary = null;
            int idOrder = 0, temp = 0, minRoll = 0, maxRoll = 0, tempC = 0, ac_year = 0, id_summary = 0, idCerti = 0;
            String txtLotes = "", txtFecha = "", idSummaries = "", event = "";
            String fch_despa = "", NroCerti = "", txtclient = "", txtProd = "", rangeRlls = "", txtLines = "", idRlls = "", idRegs = "";
            boolean result = false;
            Calendar cal = Calendar.getInstance();
            int CurrYear = cal.get(Calendar.YEAR);

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN">
                    try {
                        id_summary = Integer.parseInt(request.getParameter("id_summary"));
                    } catch (Exception e) {
                        id_summary = 0;
                    }
                    try {
                        idCerti = Integer.parseInt(request.getParameter("idCerti"));
                    } catch (Exception e) {
                        idCerti = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp1"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        event = request.getParameter("event");
                    } catch (Exception e) {
                        event = "";
                    }
                    try {
                        tempC = Integer.parseInt(request.getParameter("tempC"));
                    } catch (Exception e) {
                        tempC = 0;
                    }
                    try {
                        idOrder = Integer.parseInt(request.getParameter("idOrder"));
                    } catch (Exception e) {
                        idOrder = 0;
                    }
                    if (temp == 2) {
                        try {
                            txtLotes = request.getParameter("txtLote");
                        } catch (Exception e) {
                            txtLotes = "";
                        }
                        try {
                            minRoll = Integer.parseInt(request.getParameter("minRoll"));
                            maxRoll = Integer.parseInt(request.getParameter("maxRoll"));
                            tempC = 1;
                        } catch (Exception e) {
                            minRoll = 0;
                            maxRoll = 0;
                        }
                    }
                    try {
                        txtFecha = request.getParameter("txtFecha");
                    } catch (Exception e) {
                        txtFecha = "";
                    }
                    try {
                        ac_year = Integer.parseInt(request.getParameter("ac_year"));
                    } catch (Exception e) {
                        ac_year = CurrYear;
                    }
                    request.setAttribute("id_rol", UserRol);
                    request.setAttribute("idOrder", idOrder);
                    request.setAttribute("txtLote", txtLotes);
                    request.setAttribute("minRoll", minRoll);
                    request.setAttribute("maxRoll", maxRoll);
                    request.setAttribute("tempC", tempC);
                    request.setAttribute("txtFecha", txtFecha);
                    request.setAttribute("ac_year", ac_year);
                    request.setAttribute("id_summary", id_summary);
                    request.setAttribute("event", event);
                    request.setAttribute("idCerti", idCerti);
                    request.getRequestDispatcher("QualityCertificate.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="SUMMARY REGISTER">
                    try {
                        idOrder = Integer.parseInt(request.getParameter("idOrder"));
                    } catch (Exception e) {
                        idOrder = 0;
                    }
                    try {
                        idCerti = Integer.parseInt(request.getParameter("idCerti"));
                    } catch (Exception e) {
                        idCerti = 0;
                    }
                    try {
                        tempC = Integer.parseInt(request.getParameter("tempC"));
                    } catch (Exception e) {
                        tempC = 0;
                    }
                    try {
                        NroCerti = request.getParameter("nmb_certi");
                        if (NroCerti.isEmpty()) {
                            fch_despa = "";
                            NroCerti = "";
                        } else {
                            fch_despa = request.getParameter("fch_despa");
                        }
                    } catch (Exception e) {
                        fch_despa = "";
                        NroCerti = "";
                    }
                    try {
                        txtclient = request.getParameter("txtclient");
                        txtProd = request.getParameter("txtProd");
                        txtLotes = request.getParameter("txtLote");
                        rangeRlls = request.getParameter("RangeRlls");
                        txtLines = request.getParameter("txtLines");
                        idRlls = request.getParameter("idRolls");
                        idRegs = request.getParameter("idRegs");
                        String currentRange = "", newRange = "";
                        if (idCerti > 0) {
                            lst_summary = CertificateJpa.ConsultRangoRllsxSummary(idCerti);
                            if (lst_summary != null) {
                                Object[] ObjSummary = (Object[]) lst_summary.get(0);
                                currentRange = ObjSummary[3].toString();
                                newRange = "[" + currentRange.replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + "]";
                                newRange += "[" + rangeRlls.replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "]";
                            } else {
                                request.setAttribute("SummaryRegister", false);
                            }
                            result = CertificateJpa.updateRegisterAddRolls(idCerti, newRange, txtLines, idRlls);
                        } else {
                            result = CertificateJpa.SummaryRegister(idOrder, fch_despa, NroCerti, txtclient, txtProd, txtLotes, rangeRlls, txtLines, idRlls, rol_usuario);
                        }

                        if (idCerti > 0) {
                            id_summary = idCerti;
                        } else {
                            lst_summary = CertificateJpa.ConsultLastSummary(idOrder, txtLotes, rangeRlls);
                            try {
                                Object[] obj_sum = (Object[]) lst_summary.get(0);
                                id_summary = Integer.parseInt(obj_sum[0].toString());
                            } catch (Exception e) {
                                id_summary = 0;
                            }
                        }

                        if (id_summary > 0) {
                            request.setAttribute("SummaryRegister", result);
                            if (result == true) {
                                lst_summary = CertificateJpa.ConsultidResumxRegister(idRegs);
                                if (lst_summary != null) {
                                    Object[] obj_reg = (Object[]) lst_summary.get(0);
                                    if (obj_reg[2] == null) {
                                        idSummaries = "[" + id_summary + "]";
                                    } else {
                                        if (obj_reg[2].toString().contains("[" + id_summary + "]")) {
                                            idSummaries = "[" + id_summary + "]";
                                        } else {
                                            idSummaries = obj_reg[2].toString() + "[" + id_summary + "]";
                                        }
                                    }
                                    CertificateJpa.updateRegisterAddIdSummary(idSummaries, idRegs);
                                } else {
                                    idSummaries = "[" + id_summary + "]";
                                    CertificateJpa.updateRegisterAddIdSummary(idSummaries, idRegs);
                                }
                                idOrder = 0;
                            } else {
                                request.setAttribute("SummaryRegister", false);
                            }
                        } else {
                            request.setAttribute("SummaryRegister", false);
                        }
                    } catch (Exception e) {
                        request.setAttribute("SummaryRegister", false);
                    }
                    id_summary = 0;
                    request.getRequestDispatcher("Quality_certificate?opc=1&idOrder=0").forward(request, response);
//</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE SUMMARY">
                    try {
                        id_summary = Integer.parseInt(request.getParameter("id_summary"));
                    } catch (Exception e) {
                        id_summary = 0;
                    }
                    NroCerti = request.getParameter("nmb_certi");
                    fch_despa = request.getParameter("fch_despa");
                    result = CertificateJpa.UpdateSummary(id_summary, fch_despa, NroCerti);
                    id_summary = 0;
                    request.setAttribute("UpdateSummary", result);
                    request.getRequestDispatcher("Quality_certificate?opc=1").forward(request, response);
//</editor-fold>
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("QualityCertificate.jsp").forward(request, response);
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
