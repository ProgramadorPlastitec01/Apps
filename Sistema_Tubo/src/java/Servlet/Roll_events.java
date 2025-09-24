package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.RolloJpaController;
import Controladores.OrdenProduccionJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Roll_events extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        String UserRol = sesion.getAttribute("idRol").toString();
        RolloJpaController JpaRoll = new RolloJpaController();
        OrdenProduccionJpaController JpaOrden = new OrdenProduccionJpaController();
        int opc = Integer.parseInt(request.getParameter("opc"));
        int id_order = 0, state = 0, temp = 0, idRoll = 0, temp1 = 0, id_count = 0, orden_filter = 0,
                id_registrer = 0, temp3 = 0, id_last_roll = 0, temp4 = 0, temp2 = 0, temp5 = 0;
        String batch = "", justify = "", Roll = "", batch_filter = "", RollId = "", RollNumber = "", line_filter = "", StructureRoll = "";
        boolean result = false;
        List lst_roll = null;
        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT_ROLL_EVENTS">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (NumberFormatException e) {
                        id_order = 0;
                    }
                    try {
                        temp1 = Integer.parseInt(request.getParameter("temp1"));
                    } catch (NumberFormatException e) {
                        temp1 = 0;
                    }
                    if (temp1 > 0) {
                        try {
                            batch = request.getParameter("batch");
                        } catch (Exception e) {
                            batch = "";
                        }
                        try {
                            state = Integer.parseInt(request.getParameter("state"));
                        } catch (NumberFormatException e) {
                            state = 0;
                        }
                        try {
                            temp = Integer.parseInt(request.getParameter("temp"));
                        } catch (NumberFormatException e) {
                            temp = 0;
                        }
                        try {
                            temp2 = Integer.parseInt(request.getParameter("temp2"));
                        } catch (NumberFormatException e) {
                            temp2 = 0;
                        }
                        try {
                            temp3 = Integer.parseInt(request.getParameter("temp3"));
                        } catch (NumberFormatException e) {
                            temp3 = 0;
                        }
                        try {
                            temp5 = Integer.parseInt(request.getParameter("temp5"));
                        } catch (NumberFormatException e) {
                            temp5 = 0;
                        }
                        try {
                            RollId = request.getParameter("RollId");
                        } catch (Exception e) {
                            RollId = "";
                        }
                        try {
                            RollNumber = request.getParameter("RollNumber");
                        } catch (Exception e) {
                            RollNumber = "";
                        }
                        try {
                            orden_filter = Integer.parseInt(request.getParameter("orden_filter"));
                        } catch (NumberFormatException e) {
                            orden_filter = 0;
                        }
                        try {
                            temp4 = Integer.parseInt(request.getParameter("temp4"));
                        } catch (NumberFormatException e) {
                            temp4 = 0;
                        }
                        if (temp4 > 0) {
                            try {
                                batch_filter = request.getParameter("batch_filter");
                            } catch (Exception e) {
                                batch_filter = "";
                            }
                            if (temp4 == 2) {
                                try {
                                    line_filter = request.getParameter("line_filter");
                                } catch (NumberFormatException e) {
                                    line_filter = "";
                                }
                                try {
                                    id_registrer = Integer.parseInt(request.getParameter("id_registrer"));
                                } catch (NumberFormatException e) {
                                    id_registrer = 0;
                                }
                            }
                        }
                    }
                    request.setAttribute("id_order", id_order);
                    request.setAttribute("batch", batch);
                    request.setAttribute("state", state);
                    request.setAttribute("temp", temp);
                    request.setAttribute("temp2", temp2);
                    request.setAttribute("temp3", temp3);
                    request.setAttribute("temp5", temp5);
                    request.setAttribute("RollId", RollId);
                    request.setAttribute("RollNumber", RollNumber);
                    request.setAttribute("orden_filter", orden_filter);
                    request.setAttribute("batch_filter", batch_filter);
                    request.setAttribute("line_filter", line_filter);
                    request.setAttribute("id_registrer", id_registrer);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("RollEvents.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="APROVED AND REFUSED ROLL">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (NumberFormatException e) {
                        id_order = 0;
                    }
                    try {
                        batch = request.getParameter("batch");
                    } catch (Exception e) {
                        batch = "";
                    }
                    try {
                        state = Integer.parseInt(request.getParameter("state"));
                    } catch (NumberFormatException e) {
                        state = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    Roll = request.getParameter("idRoll");
                    justify = request.getParameter("Txt_justify");
                    String[] Arg_idR = Roll.replace("][", "-").replace("[", "").replace("]", "").split("-");
                    for (int i = 0; i < Arg_idR.length; i++) {
                        idRoll = Integer.parseInt(Arg_idR[i]);
                        result = JpaRoll.RolloH_register(idRoll, state, justify, UserName);
                        JpaRoll.Rollo_StatusUpdate(idRoll, state);
                        if (state == 1) {
                            id_count = Integer.parseInt(request.getParameter("NumRoll" + i));
                            JpaRoll.UpdateRollNumber(idRoll, id_count);
                            StructureRoll = "[" + request.getParameter("NumRoll" + i) + "]";
                            JpaOrden.OrderUpdateRollRegister(id_order, StructureRoll);
                        }
                    }

                    if (state == 1) {
                        request.setAttribute("AprovedRollEvents", result);
                    } else {
                        request.setAttribute("RefusedRollEvents", result);
                    }
                    request.getRequestDispatcher("Roll_events?opc=1&id_order=" + id_order + "&batch=" + batch + "&state=" + state + "&temp=" + temp + "&temp1=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="TRASFER ROLL">
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (NumberFormatException e) {
                        id_order = 0;
                    }
                    try {
                        batch = request.getParameter("batch");
                    } catch (Exception e) {
                        batch = "";
                    }
                    try {
                        state = Integer.parseInt(request.getParameter("state"));
                    } catch (NumberFormatException e) {
                        state = 0;
                    }
                    try {
                        orden_filter = Integer.parseInt(request.getParameter("orden_filter"));
                    } catch (NumberFormatException e) {
                        orden_filter = 0;
                    }
                    try {
                        batch_filter = request.getParameter("batch_filter");
                    } catch (Exception e) {
                        batch_filter = "";
                    }
                    try {
                        id_registrer = Integer.parseInt(request.getParameter("id_registrer"));
                    } catch (NumberFormatException e) {
                        id_registrer = 0;
                    }
                    Roll = request.getParameter("RollId");
                    justify = request.getParameter("Txt_justify");
                    String[] Arg_id = Roll.replace("][", "-").replace("[", "").replace("]", "").split("-");
                    for (int i = 0; i < Arg_id.length; i++) {
                        idRoll = Integer.parseInt(Arg_id[i]);
                        JpaRoll.RolloH_register(idRoll, state, justify, UserName);
                        id_count = Integer.parseInt(request.getParameter("NumRoll" + i));
                        result = JpaRoll.UpdateIdRegisterNumberRollStateAproved(idRoll, id_registrer, id_count);
                        StructureRoll = "[" + request.getParameter("NumRoll" + i) + "]";
                        JpaOrden.OrderUpdateRollRegister(orden_filter, StructureRoll);
                    }
                    request.setAttribute("AprovedRollEvents", result);
                    request.getRequestDispatcher("Roll_events?opc=1&id_order=" + id_order + "&temp3=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("RollEvents.jsp").forward(request, response);
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
