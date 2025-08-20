package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.ActivityJpaController;
import Controller.ActivityDetailJpaController;
import Controller.ActivitySystemControllerJpa;
import Controller.ComputerControllerJpa;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Activity extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String UserRol = sesion.getAttribute("idRol").toString();
        String userSession = sesion.getAttribute("Nombres").toString();
        ActivityJpaController ActivityJpa = new ActivityJpaController();
        ActivityDetailJpaController ActivityDetail = new ActivityDetailJpaController();
        ActivitySystemControllerJpa activitySystem = new ActivitySystemControllerJpa();
        ComputerControllerJpa ComputerJpa = new ComputerControllerJpa();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int idAct = 0, event = 0, typeSelect = 0, action = 0, temp = 0;
        String activity = "", weekx = "", typePc = "", typeDv = "", idEjec = "", idVerf = "", txtComent = "", txtDate = "";
        List lst_activity = null;
        List lst_pc_dev = null;
        boolean result = false;
        String[] Multi_Pc = {};
        String[] Multi_Dv = {};

        try {
            switch (opt) {
                case 1:
                    try {
                        idAct = Integer.parseInt(request.getParameter("idAct"));
                    } catch (Exception e) {
                        idAct = 0;
                    }
                    try {
                        event = Integer.parseInt(request.getParameter("event"));
                    } catch (Exception e) {
                        event = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    request.setAttribute("idAct", idAct);
                    request.setAttribute("event", event);
                    request.setAttribute("temp", temp);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("Activity.jsp").forward(request, response);
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="EDIT ACTIVITY">
                    try {
                        idAct = Integer.parseInt(request.getParameter("idAct"));
                    } catch (Exception e) {
                        idAct = 0;
                    }
                    activity = request.getParameter("txtAct");
                    weekx = request.getParameter("cbxWeek");

                    result = ActivityJpa.UpdateAcivity(idAct, activity, weekx, idUser);
                    if (result) {
                        activitySystem.ActivityRegister(idUser, 2, "R-TI-005", "Se modifico actividad " + activity + " #" + idAct + "", 1, userSession);
                    }
                    request.setAttribute("EditAcivity", result);
                    request.getRequestDispatcher("Activity?opt=1&idAct=0").forward(request, response);
                    //</editor-fold>
                    break;

                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER DETAIL">
                    String insr_pc = "";
                    String insr_dv = "";
                    String datapc = "";
                    String data_vd = "";
                    try {
                        typeSelect = Integer.parseInt(request.getParameter("typeSelect"));
                    } catch (Exception e) {
                        typeSelect = 0;
                    }
                    activity = request.getParameter("txtAct");
                    weekx = request.getParameter("cbxWeek");

                    result = ActivityJpa.RegisterAcivity(activity, weekx, idUser);
                    if (result) {
                        lst_activity = ActivityJpa.ConsultActivityLast(idUser);
                        if (lst_activity != null) {
                            Object[] ObjAct = (Object[]) lst_activity.get(0);
                            idAct = Integer.parseInt(ObjAct[0].toString());
                            if (typeSelect == 1) {
                                //<editor-fold defaultstate="collapsed" desc="SELECTION ONE X ONE ">
                                try {
                                    insr_pc = "INSERT INTO activity_detail (id_activity, comp_device) VALUES ";
                                    Multi_Pc = request.getParameterValues("cbxPc");
                                    int legthData = Multi_Pc.length - 1;
                                    for (int i = 0; i < legthData; i++) {
                                        if (i != legthData - 1) {
                                            datapc += "(" + idAct + ", '" + Multi_Pc[i] + "'),";
                                        } else {
                                            datapc += "(" + idAct + ", '" + Multi_Pc[i] + "');";
                                        }
                                    }

                                    insr_pc += datapc;
                                    result = ActivityDetail.RegisterActivityDetail(insr_pc);
                                } catch (Exception e) {
                                    insr_pc = "";

                                }
                                try {
                                    insr_dv = "INSERT INTO activity_detail (id_activity, comp_device) VALUES ";
                                    Multi_Dv = request.getParameterValues("cbxDv");
                                    int legthData = Multi_Dv.length - 1;
                                    for (int i = 0; i < legthData; i++) {
                                        if (i != legthData - 1) {
                                            data_vd += "(" + idAct + ", '" + Multi_Dv[i] + "'),";
                                        } else {
                                            data_vd += "(" + idAct + ", '" + Multi_Dv[i] + "');";
                                        }
                                    }
                                    insr_dv += data_vd;
                                    result = ActivityDetail.RegisterActivityDetail(insr_dv);
                                } catch (Exception e) {
                                    insr_dv = "";
                                }

                                //</editor-fold>
                            } else if (typeSelect == 2) {
                                //<editor-fold defaultstate="collapsed" desc="SELECTION GROUP ">
                                typePc = request.getParameter("cbxPc");
                                typeDv = request.getParameter("cbxDv");
                                String[] computer = typePc.toString().replace("[", "").replace("]", "").split("/");
                                String[] Device = typeDv.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                                insr_pc = "INSERT INTO activity_detail (id_activity, comp_device) VALUES ";
                                try {
                                    //<editor-fold defaultstate="collapsed" desc="GOOD AND REVISION // CRITICAL">
                                    int typePcCons = Integer.parseInt(computer[1].toString().trim());
                                    if (typePcCons == 1) {
                                        lst_pc_dev = ComputerJpa.ConsulteComputerB_R();
                                    } else if (typePcCons == 2) {
                                        lst_pc_dev = ComputerJpa.ConsulteComputerCritical();
                                    }
                                    if (lst_pc_dev != null) {
                                        for (int i = 0; i < lst_pc_dev.size(); i++) {
                                            Object[] ObjComp = (Object[]) lst_pc_dev.get(i);
                                            if (i != lst_pc_dev.size() - 1) {
                                                datapc += "(" + idAct + ", '[" + ObjComp[0] + "/" + ObjComp[1] + "/" + ObjComp[2] + "]' ),";
                                            } else {
                                                datapc += "(" + idAct + ", '[" + ObjComp[0] + "/" + ObjComp[1] + "/" + ObjComp[2] + "]' );";
                                            }
                                        }
                                        insr_pc += datapc;
                                        result = ActivityDetail.RegisterActivityDetail(insr_pc);
                                    }
                                } catch (Exception e) {
                                    insr_pc = "";
                                    //</editor-fold>
                                }
                                try {
                                    //<editor-fold defaultstate="collapsed" desc="DEVICES">

                                    insr_dv = "INSERT INTO activity_detail (id_activity, comp_device) VALUES ";
                                    for (int i = 0; i < Device.length; i++) {
                                        String[] dvce = Device[i].toString().split("/");
                                        int tmDv = Integer.parseInt(dvce[1].toString());
                                        lst_pc_dev = ComputerJpa.ConsulteDevicerB_R_idType(tmDv);
                                        if (lst_pc_dev != null) {
                                            for (int j = 0; j < lst_pc_dev.size(); j++) {
                                                Object[] Obdv = (Object[]) lst_pc_dev.get(j);
                                                if (j != lst_pc_dev.size() - 1) {
                                                    data_vd += "(" + idAct + ", '[" + Obdv[0] + "/" + Obdv[3] + "/" + Obdv[4] + "]'),";
                                                } else {
                                                    data_vd += "(" + idAct + ", '[" + Obdv[0] + "/" + Obdv[3] + "/" + Obdv[4] + "]');";
                                                }
                                            }
                                            insr_dv += data_vd;
                                            result = ActivityDetail.RegisterActivityDetail(insr_dv);
                                        } else {
                                            insr_dv = "";
                                        }
                                    }
                                } catch (Exception e) {
                                    insr_dv = "";
                                    //</editor-fold>
                                }
                                //</editor-fold>
                            }
                        }
                        activitySystem.ActivityRegister(idUser, 2, "R-TI-005", "Se registrar nueva actividad programada", 1, userSession);
                    } else {

                    }
                    request.getRequestDispatcher("Activity?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    try {
                        idAct = Integer.parseInt(request.getParameter("idAct"));
                    } catch (Exception e) {
                        idAct = 0;
                    }
                    try {
                        event = Integer.parseInt(request.getParameter("event"));
                    } catch (Exception e) {
                        event = 0;
                    }
                    action = Integer.parseInt(request.getParameter("action"));
                    txtDate = request.getParameter("txtDate");
                    txtComent = request.getParameter("txtComent");
                    if (action == 1) {
                        idEjec = request.getParameter("idEject").replace("][", ",").replace("[", "").replace("]", "");
                        result = ActivityDetail.UpdateActivityDetail_Act(idEjec, txtDate, txtComent, idUser);
                        if (result) {
                            activitySystem.ActivityRegister(idUser, 2, "R-TI-005", "Se ejecuta actividad #" + idAct + "", 1, userSession);
                        }
                        request.setAttribute("EjectAct", result);
                    } else if (action == 2) {
                        idVerf = request.getParameter("idVerf").replace("][", ",").replace("[", "").replace("]", "");
                        result = ActivityDetail.UpdateActivityDetail_Ver(idVerf, txtDate, txtComent, idUser);
                        if (result) {
                            activitySystem.ActivityRegister(idUser, 2, "R-TI-005", "Se revisa actividad #" + idAct + "", 1, userSession);
                        }
                        request.setAttribute("VerfAct", result);
                    }
                    request.getRequestDispatcher("Activity?opt=1").forward(request, response);
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Activity.jsp").forward(request, response);
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
