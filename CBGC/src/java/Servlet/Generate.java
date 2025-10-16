package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.CertificatesJpaController;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import Connection.ConnectionSignature;
import Method.Util;

public class Generate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
            ConnectionSignature SignatureConn = new ConnectionSignature();
            String RolName = session.getAttribute("Rol/Usuario").toString();
            int IdRol = Integer.parseInt(session.getAttribute("idRol").toString());
            int Document = Integer.parseInt(session.getAttribute("Documento").toString());
            int CodeSig = Integer.parseInt(session.getAttribute("Codigo").toString());
            int opt = Integer.parseInt(request.getParameter("opt"));
            int Order = 0, IdFormat = 0, IdCertificates = 0;
            String Type = "", Product = "", Batch = "", Html = "", Code = "", Consecutive = "", Customer = "";
            boolean result = false;
            List lst_sign = null;
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
                        IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                    } catch (Exception e) {
                        IdFormat = 0;
                    }
                    try {
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    request.setAttribute("Type", Type);
                    request.setAttribute("order", Order);
                    request.setAttribute("product", Product);
                    request.setAttribute("batch", Batch);
                    request.setAttribute("IdFormat", IdFormat);
                    request.setAttribute("IdCertificates", IdCertificates);
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
                    if (IdCertificates > 0) {
                        //<editor-fold defaultstate="collapsed" desc="UPDATE">
                        result = CertificatesJpa.CertificatesUpdate(IdCertificates, Consecutive, Html);
                        if (result) {

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
                            IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                        } catch (Exception e) {
                            IdFormat = 0;
                        }
                        result = CertificatesJpa.CertificatesRegister(Type, Code, Order, Product, Batch, Consecutive, Customer, RolName, Html);
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
                        }
                        request.getRequestDispatcher("Generate?opt=2&Type=" + Type + "&Order=" + Order + "&Product=" + Product + "&Batch=" + Batch + "&IdFormat=" + IdFormat + "&IdCertificates=" + IdCertificates)
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
                        IdCertificates = Integer.parseInt(request.getParameter("IdCertificates"));
                    } catch (Exception e) {
                        IdCertificates = 0;
                    }
                    if ((IdRol == 1) || (IdRol == 2)) {
                        lst_sign = SignatureConn.ConsultSignature(Document, CodeSig);
                        if (lst_sign != null) {
                            String[] ArgSign = Util.parseResult(lst_sign.get(0));
                            result = CertificatesJpa.CertificatesUpdateSignature(IdCertificates, ArgSign[0]);
                            if (result) {
                            }
                        }
                    } else {
                        request.setAttribute("UnauthorizedSignature", true);
                    }
                    request.getRequestDispatcher("Generate?opt=1&Type=" + Type).forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
//            request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
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
