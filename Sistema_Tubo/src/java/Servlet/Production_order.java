package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.OrdenProduccionJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controladores.ParametrosJpaController;

public class Production_order extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String UserRol = sesion.getAttribute("idRol").toString();
        OrdenProduccionJpaController OrderProdJpa = new OrdenProduccionJpaController();
        ParametrosJpaController ParameterJpa = new ParametrosJpaController();

        int opc = Integer.parseInt(request.getParameter("opc"));
        int id_order = 0, est = 0, dataSheet = 0, temp = 0, temps = 0;
        String NoOrder = "", client = "", obs = "", client_v = "", ChangeLte = "";
        List lst_order = null;
        List lst_parameter = null;

        boolean result = false;
        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN ORDER">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    request.setAttribute("Id_order", id_order);
                    request.setAttribute("temp", temp);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("ProductionOrder.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT ORDER">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    dataSheet = Integer.parseInt(request.getParameter("Cbx_Data"));
                    try {
                        temps = Integer.parseInt(request.getParameter("temps"));
                    } catch (Exception e) {
                        temps = 0;
                    }
                    if (temps == 1) {
                        NoOrder = request.getParameter("Txt_orden1");
                    } else {
                        NoOrder = request.getParameter("Text_orden_a");
                    }
//                    NoOrder = request.getParameter("Txt_orden");
                    client = request.getParameter("Cbx_client");
                    obs = request.getParameter("Txt_Obs");
                    lst_parameter = ParameterJpa.ConsultParametersCategory_last("CambioLote");
                    if (lst_parameter != null) {
                        Object[] obj_param = (Object[]) lst_parameter.get(0);
                        String[] arr_param = obj_param[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int i = 0; i < arr_param.length; i++) {
                            String[] arr_clien = arr_param[i].split("/");
                            client_v = arr_clien[0].toString();
                            if (client.contains(client_v)) {
                                ChangeLte = "-/" + arr_clien[1].toString() + "";
                                i = arr_param.length;
                            } else {
                                ChangeLte = "N/A";
                            }
                        }
                    } else {
                        request.setAttribute("TableParamData_no", true);
                    }
                    if (!ChangeLte.equals("N/A")) {
                        if (id_order <= 0) {
                            result = OrderProdJpa.Register_orderProduction_lte(dataSheet, NoOrder, client, obs, ChangeLte, rol_usuario);
                            request.setAttribute("registerOrder", result);
                        } else {
                            result = OrderProdJpa.Update_orderProduction_lte(id_order, dataSheet, NoOrder, client, obs, ChangeLte);
                            request.setAttribute("UdateOrder", result);
                        }
                    } else {
                        if (id_order <= 0) {
                            result = OrderProdJpa.Register_orderProduction(dataSheet, NoOrder, client, obs, rol_usuario);
                            request.setAttribute("registerOrder", result);
                        } else {
                            result = OrderProdJpa.Update_orderProduction(id_order, dataSheet, NoOrder, client, obs);
                            request.setAttribute("UdateOrder", result);
                        }
                    }
                    request.getRequestDispatcher("Production_order?opc=1&id_order=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATUS">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (Exception e) {
                        id_order = 0;
                    }
                    try {
                        est = Integer.parseInt(request.getParameter("est"));
                    } catch (Exception e) {
                        est = 0;
                    }
                    lst_order = OrderProdJpa.ConsultStateActiveOrderRegister(id_order);
                    if (lst_order != null) {
                        Object[] obj_est = (Object[]) lst_order.get(0);
                        if (obj_est[4] == null || obj_est[4].toString().equals("CERRADO")) {
                            if (obj_est[5] == null || obj_est[5].toString().equals("CERRADO")) {
                                if (est == 1) {
                                    est = 0;
                                    result = OrderProdJpa.OrderChangeStatus(id_order, est);
                                    request.setAttribute("Order_StatuChange", result);
                                } else {
                                    est = 1;
                                    result = OrderProdJpa.OrderChangeStatus(id_order, est);
                                    request.setAttribute("Order_StatuChange", result);
                                }
                            } else {
                                request.setAttribute("OrderValidationState", true);
                            }
                        } else {
                            request.setAttribute("OrderValidationState", true);
                        }
                    }

                    request.getRequestDispatcher("Production_order?opc=1&id_order=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("ProductionOrder.jsp").forward(request, response);
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
