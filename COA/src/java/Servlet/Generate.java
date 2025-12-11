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
import Controller.CertificatesJpaController;
import Controller.EventsJpaController;

public class Generate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
            EventsJpaController EventConn = new EventsJpaController();
            String RolName = session.getAttribute("Rol/Nombres").toString();
            int IdRol = Integer.parseInt(session.getAttribute("idRol").toString());
            int opt = Integer.parseInt(request.getParameter("opt"));
            String Signature = session.getAttribute("Firma").toString();
            int Order = 0, IdCertificates = 0, TempDelete = 0, TempM = 0, Temp = 0, StateCerti = 0;
            String Type = "", Product = "", Batch = "", Html = "", Code = "", Consecutive = "", Customer = "", IdCertiMasive = "", Category = "",
                    Justification = "", Record = "", FormatName = "", Message = "", Amount = "", DateDispatch = "";
            boolean result = false;
            List lst_id = null;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="GENERAL">
                    try {
                        Type = request.getParameter("Type");
                    } catch (Exception e) {
                        Type = "";
                    }
                    request.setAttribute("Type", Type);
                    request.setAttribute("TempDelete", TempDelete);
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
                    if (IdCertificates > 0) {
                        //<editor-fold defaultstate="collapsed" desc="UPDATE">
                        result = CertificatesJpa.CertificatesUpdate(IdCertificates, Consecutive, Amount, DateDispatch, Html);
                        if (result) {
                            request.setAttribute("UpdateCertificate", result);
                        }
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
                        result = CertificatesJpa.CertificatesRegister(Type, Code, Order, Product, Batch, Consecutive, Customer, Amount, DateDispatch, RolName, Html);
                        if (result) {
                            lst_id = CertificatesJpa.ConsultCeritcateTypeId(Type);
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
                        request.getRequestDispatcher("Generate?opt=2&Type=" + Type + "&Order=" + Order + "&Product=" + Product + "&Batch=" + Batch + "&FormatName=" + FormatName + "&IdCertificates=" + IdCertificates + "&StateCerti=1")
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
                    if ((IdRol == 1) || (IdRol == 2)) {
                        if (Signature != null) {
                            if (Temp == 0) {
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE MASIVE">
                                String[] IdsCerti = IdCertiMasive.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                for (int i = 0; i < IdsCerti.length; i++) {
                                    IdCertificates = Integer.parseInt(IdsCerti[i]);
                                    result = CertificatesJpa.CertificatesUpdateSignature(IdCertificates, Signature);
                                }
                                if (result) {
                                    request.setAttribute("UpdateCertificate", result);
                                }
                                request.getRequestDispatcher("Generate?opt=8").forward(request, response);
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE UNIQUE">
                                IdCertificates = Integer.parseInt(IdCertiMasive);
                                result = CertificatesJpa.CertificatesUpdateSignature(IdCertificates, Signature);
                                if (result) {
                                    request.setAttribute("UpdateCertificate", result);
                                }
                                request.getRequestDispatcher("Generate?opt=2&Type=" + Type + "&IdCertificates=" + IdCertificates + "&TempDelete=0").forward(request, response);
                                //</editor-fold>
                            }
                        }
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
                    result = CertificatesJpa.RegisterNovelty(IdCertificates, Category, Justification, RolName);
                    if (result) {
                        if (Category.equals("Delete")) {
                            request.setAttribute("DeleteCertificates", result);
                        } else {
                            request.setAttribute("DeleteCertificates", result);
                        }
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
                    EventConn.RegisterEvents(IdCertificates, Message);
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
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    result = CertificatesJpa.UpdateCertificateFinish(IdCertificates);
                    if (result) {
                        request.setAttribute("FinishCertificate", result);
                    }
                    request.getRequestDispatcher("Generate?opt=1&Type=" + Type).forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT SIGNATURE REPORT">
                    request.getRequestDispatcher("SignatureReport.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
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
