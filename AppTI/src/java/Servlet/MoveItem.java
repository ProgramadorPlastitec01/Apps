package Servlet;

import Controller.ActivitySystemControllerJpa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.ItemJpaController;
import Controller.MoveItemJpaController;
import java.util.List;

public class MoveItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String userSession = sesion.getAttribute("Nombres").toString();
        int opt = Integer.parseInt(request.getParameter("opt"));

        ItemJpaController ItemJpa = new ItemJpaController();
        MoveItemJpaController MoveItemJpa = new MoveItemJpaController();
        ActivitySystemControllerJpa activitySystem = new ActivitySystemControllerJpa();
        List lst_item = null;

        boolean result = false;
        int id_ref = 0, item = 0, idItem = 0, count = 0;
        String ref = "", dtIn = "", dtFn = "", numEnt = "", dateMov = "", locationMov = "",
                obsMov = "", typeMov = "", dateMove = "", moveValid = "", itm = "", loc = "",
                obs = "", model = "", serial = "";
        String[] dataMove = {};

        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        ref = request.getParameter("txt_ref");
                    } catch (Exception e) {
                        ref = "";
                    }
                    try {
                        numEnt = request.getParameter("numEnt");
                    } catch (Exception e) {
                        numEnt = "";
                    }
                    try {
                        dtIn = request.getParameter("txtDateIni");
                        dtFn = request.getParameter("txtDateFin");
                    } catch (Exception e) {
                        dtIn = "";
                        dtFn = "";
                    }

                    try {
                        dateMove = request.getParameter("dateMove");
                    } catch (Exception e) {
                        dateMove = "";
                    }
                    try {
                        moveValid = request.getParameter("txt_fieldMove");
                    } catch (Exception e) {
                        moveValid = "";
                    }
                    request.setAttribute("ref", ref);
                    request.setAttribute("DateIni", dtIn);
                    request.setAttribute("DateFin", dtFn);
                    request.setAttribute("numEnt", numEnt);
                    request.setAttribute("dateMove", dateMove);
                    request.setAttribute("moveValid", moveValid);
                    request.getRequestDispatcher("moveItem.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER ITEM">
                    try {
                        ref = request.getParameter("txt_ref");
                    } catch (Exception e) {
                        ref = "";
                    }
                    try {
                        numEnt = request.getParameter("numMov");
                    } catch (Exception e) {
                        numEnt = "";
                    }
                    try {
                        dtIn = request.getParameter("txtDateIni");
                        dtFn = request.getParameter("txtDateFin");
                    } catch (Exception e) {
                        dtIn = "";
                        dtFn = "";
                    }

                    id_ref = Integer.parseInt(request.getParameter("id_ref"));
                    item = Integer.parseInt(request.getParameter("txt_numItem"));
                    typeMov = request.getParameter("TypeMov");
                    dateMov = request.getParameter("txt_date");
                    locationMov = request.getParameter("txt_location");
                    model = request.getParameter("txt_model");
                    serial = request.getParameter("txt_serial");
                    obsMov = request.getParameter("txt_Obs");

                    result = ItemJpa.ItemRegister(id_ref, item, model, serial, obsMov, idUser);
                    if (result) {
                        lst_item = ItemJpa.ConsultLastItemReg(id_ref);
                        if (lst_item != null) {
                            Object[] obItm = (Object[]) lst_item.get(0);
                            idItem = Integer.parseInt(obItm[0].toString());
                            result = MoveItemJpa.RegisterItemMoveNew(idItem, typeMov, numEnt, dateMov, locationMov, obsMov, idUser, idUser);
                            if (result) {
                                activitySystem.ActivityRegister(idUser, 2, "Movimiento Items", "Se registro un movimiento de items " + item + "", 1, userSession);
                            }
                            request.setAttribute("RegisterItem", item);
                            request.getRequestDispatcher("MoveItem?opt=1").forward(request, response);
                        } else {
                            idItem = 0;
                        }
                    } else {
                        request.setAttribute("RegisterItem", 0);
                        request.getRequestDispatcher("MoveItem?opt=1").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MOVE ITEM">
                    String q = "";
                    String typeMo = "";
                    String nummov = "";
                    try {
                        numEnt = request.getParameter("numEnt");
                        typeMo = numEnt.replace("ENT", "ENT/").replace("SAL", "SAL/").split("/")[0];
                        nummov = numEnt.replace("ENT", "ENT/").replace("SAL", "SAL/").split("/")[1];
                    } catch (Exception e) {
                        numEnt = "";
                    }
                    try {
                        dateMove = request.getParameter("dateMove");
                    } catch (Exception e) {
                        dateMove = "";
                    }
                    count = Integer.parseInt(request.getParameter("txtCountData"));
                    for (int i = 1; i <= count; i++) {
                        itm = request.getParameter("txtIdItem" + i + "");
                        loc = request.getParameter("txtLocation" + i + "");
                        obs = request.getParameter("txtObs" + i + "");
                        q += "(" + itm + ", '" + typeMo + "', " + nummov + ", '" + dateMove + "', '" + loc + "', '" + obs + "', " + idUser + ", " + idUser + ")";
                        if (i != count) {
                            q += ",";
                        }
                    }
                    result = MoveItemJpa.RegisterItemMove(q);
                    if (result) {
                        activitySystem.ActivityRegister(idUser, 2, "Movimiento Items", "Se realizo un movimiento de item", 1, userSession);
                    }
                    request.setAttribute("RegisterMove", result);
                    request.getRequestDispatcher("MoveItem?opt=1&numEnt=").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
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
