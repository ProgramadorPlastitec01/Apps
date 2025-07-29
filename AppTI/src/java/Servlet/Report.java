package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Method.FilterInfoReport;

public class Report extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession sesion = request.getSession();
            FilterInfoReport FilterMethod = new FilterInfoReport();
            int opt = Integer.parseInt(request.getParameter("opt"));
            //<editor-fold defaultstate="collapsed" desc="VARIABLE">
            String Affair = "", Charge = "", Person = "", Description = "", Solution = "", DateR = "", DateInitialR = "", DateFinalR = "",
                    DateF = "", Antivirus = "", Internal_Mail = "", External_Mail = "",
                    Bill = "", Bill_Date = "", Gmail = "", Warranty = "", Warranty_Date = "", Internet = "", Login_Plastitec = "",
                    Licence = "", Licence_date = "", MAC = "", IP = "", Supplier = "", Network_Point = "", RED = "", Skype = "", Type_State = "",
                    Type_PC = "", VLAN = "", VPN = "", Office_Version = "", WIN_Version = "", Responsible = "", Area = "";
            String Qrt = "";
            int IdPending = 0, Priority = 0, ProgressInitial = 0, ProgressFinal = 0, Filter = 0, IdPC = 0;
            //</editor-fold>
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE PENDING">
                    try {
                        Filter = Integer.parseInt(request.getParameter("Filter"));
                    } catch (NumberFormatException e) {
                        Filter = 0;
                    }
                    try {
                        IdPending = Integer.parseInt(request.getParameter("IdPending"));
                    } catch (NumberFormatException e) {
                        IdPending = 0;
                    }
                    try {
                        Affair = request.getParameter("Txt_affair");
                    } catch (Exception e) {
                        Affair = "";
                    }
                    try {
                        Priority = Integer.parseInt(request.getParameter("Txt_priority"));
                    } catch (NumberFormatException e) {
                        Priority = 0;
                    }
                    try {
                        Charge = request.getParameter("Txt_charge");
                    } catch (Exception e) {
                        Charge = "";
                    }
                    try {
                        Person = request.getParameter("Txt_person");
                    } catch (Exception e) {
                        Person = "";
                    }
                    try {
                        DateR = request.getParameter("DateRegister");
                    } catch (Exception e) {
                        DateR = "";
                    }
                    try {
                        DateF = request.getParameter("DateSolution");
                    } catch (Exception e) {
                        DateF = "";
                    }
                    try {
                        ProgressInitial = Integer.parseInt(request.getParameter("Txt_progressInitial"));
                    } catch (NumberFormatException e) {
                    }
                    try {
                        ProgressFinal = Integer.parseInt(request.getParameter("Txt_progressFinal"));
                    } catch (NumberFormatException e) {
                        ProgressFinal = 0;
                    }
                    try {
                        Description = request.getParameter("Txt_description");
                    } catch (Exception e) {
                        Description = "";
                    }
                    try {
                        Solution = request.getParameter("Txt_solution");
                    } catch (Exception e) {
                        Solution = "";
                    }
                    request.setAttribute("Filter", Filter);
                    request.setAttribute("IdPending", IdPending);
                    request.setAttribute("Txt_affair", Affair);
                    request.setAttribute("Txt_priority", Priority);
                    request.setAttribute("Txt_charge", Charge);
                    request.setAttribute("Txt_person", Person);
                    request.setAttribute("DateRegister", DateR);
                    request.setAttribute("DateSolution", DateF);
                    request.setAttribute("Txt_progressInitial", ProgressInitial);
                    request.setAttribute("Txt_progressFinal", ProgressFinal);
                    request.setAttribute("Txt_description", Description);
                    request.setAttribute("Txt_solution", Solution);
                    request.setAttribute("Report", "Pending");
                    request.getRequestDispatcher("Report.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="MODULE INFORMATION PC">
                    try {
                        Filter = Integer.parseInt(request.getParameter("Filter"));
                    } catch (Exception e) {
                        Filter = 0;
                    }
                    if (Filter > 0) {
                        //<editor-fold defaultstate="collapsed" desc="FILTER VARIABLE">
                        try {
                            IdPC = Integer.parseInt(request.getParameter("IdPC"));
                        } catch (Exception e) {
                            IdPC = 0;
                        }
                        try {
                            Antivirus = request.getParameter("Antivirus");
                        } catch (Exception e) {
                            Antivirus = "";
                        }
                        try {
                            Internal_Mail = request.getParameter("Internal_Mail");
                        } catch (Exception e) {
                            Internal_Mail = "";
                        }
                        try {
                            External_Mail = request.getParameter("External_Mail");
                        } catch (Exception e) {
                            External_Mail = "";
                        }
                        try {
                            Description = request.getParameter("Description");
                        } catch (Exception e) {
                            Description = "";
                        }
                        try {
                            Bill = request.getParameter("Bill");
                        } catch (Exception e) {
                            Bill = "";
                        }
                        try {
                            Bill_Date = request.getParameter("Bill_Date");
                        } catch (Exception e) {
                            Bill_Date = "";
                        }
                        try {
                            Gmail = request.getParameter("Gmail");
                        } catch (Exception e) {
                            Gmail = "";
                        }
                        try {
                            Warranty = request.getParameter("Warranty");
                        } catch (Exception e) {
                            Warranty = "";
                        }
                        try {
                            Warranty_Date = request.getParameter("Warranty_Date");
                        } catch (Exception e) {
                            Warranty_Date = "";
                        }
                        try {
                            Internet = request.getParameter("Internet");
                        } catch (Exception e) {
                            Internet = "";
                        }
                        try {
                            Login_Plastitec = request.getParameter("Login_Plastitec");
                        } catch (Exception e) {
                            Login_Plastitec = "";
                        }
                        try {
                            Licence = request.getParameter("Licence");
                        } catch (Exception e) {
                            Licence = "";
                        }
                        try {
                            Licence_date = request.getParameter("Licence_date");
                        } catch (Exception e) {
                            Licence_date = "";
                        }
                        try {
                            MAC = request.getParameter("MAC");
                        } catch (Exception e) {
                            MAC = "";
                        }
                        try {
                            IP = request.getParameter("IP");
                        } catch (Exception e) {
                            IP = "";
                        }
                        try {
                            Supplier = request.getParameter("Supplier");
                        } catch (Exception e) {
                            Supplier = "";
                        }
                        try {
                            Network_Point = request.getParameter("Network_Point");
                        } catch (Exception e) {
                            Network_Point = "";
                        }
                        try {
                            RED = request.getParameter("RED");
                        } catch (Exception e) {
                            RED = "";
                        }
                        try {
                            Skype = request.getParameter("Skype");
                        } catch (Exception e) {
                            Skype = "";
                        }
                        try {
                            Type_State = request.getParameter("Type_State");
                        } catch (Exception e) {
                            Type_State = "";
                        }
                        try {
                            VLAN = request.getParameter("VLAN");
                        } catch (Exception e) {
                            VLAN = "";
                        }
                        try {
                            VPN = request.getParameter("VPN");
                        } catch (Exception e) {
                            VPN = "";
                        }
                        try {
                            Office_Version = request.getParameter("Office_Version");
                        } catch (Exception e) {
                            Office_Version = "";
                        }
                        try {
                            WIN_Version = request.getParameter("WIN_Version");
                        } catch (Exception e) {
                            WIN_Version = "";
                        }
                        try {
                            Responsible = request.getParameter("Responsible");
                        } catch (Exception e) {
                            Responsible = "";
                        }
                        try {
                            Area = request.getParameter("Area");
                        } catch (Exception e) {
                            Area = "";
                        }
                        try {
                            Type_PC = request.getParameter("Type_PC");
                        } catch (Exception e) {
                            Type_PC = "";
                        }
                        Qrt = FilterMethod.FilterInfoReport(IdPC, Antivirus, Internal_Mail, External_Mail, Bill, Bill_Date, Gmail, Warranty, Warranty_Date, Internet, Login_Plastitec, Licence, Licence_date, MAC, IP, Supplier, Network_Point, RED, Skype, Type_State, VLAN, VPN, Office_Version, WIN_Version, Responsible, Area, Type_PC);
                        request.setAttribute("Qrt", Qrt);
                        //</editor-fold>
                    }
                    request.setAttribute("Report", "Information");
                    request.setAttribute("Filter", Filter);
                    request.getRequestDispatcher("Report.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException ex) {
            request.getRequestDispatcher("Report.jsp").forward(request, response);
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
