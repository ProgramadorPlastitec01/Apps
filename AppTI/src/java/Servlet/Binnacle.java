package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.BinnacleControllerJpa;
import Controller.ActivitySystemControllerJpa;

public class Binnacle extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        try {
            HttpSession sesion = request.getSession();
            BinnacleControllerJpa binnacleJpa = new BinnacleControllerJpa();
            ActivitySystemControllerJpa activitySystem = new ActivitySystemControllerJpa();
            String UserRol = sesion.getAttribute("idRol").toString();
            int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
            String userSession = sesion.getAttribute("Nombres").toString();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idBinn = 0, temp = 0, ste = 0;
            String datIn = "", dateFin = "", hourIn = "", hourFin = "", shift = "", binnacle = "", idAct = "", idRec = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN">

                    try {
                        idBinn = Integer.parseInt(request.getParameter("idBinn"));
                    } catch (Exception e) {
                        idBinn = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    request.setAttribute("idRol", UserRol);
                    request.setAttribute("idUser", idUser);
                    request.setAttribute("idbinn", idBinn);
                    request.setAttribute("temp", temp);
                    request.getRequestDispatcher("Binnacle.jsp").forward(request, response);
//</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE">
                    try {
                        idBinn = Integer.parseInt(request.getParameter("idBinn"));
                    } catch (Exception e) {
                        idBinn = 0;
                    }
                    datIn = request.getParameter("txtDateIni");
                    hourIn = request.getParameter("txtHourIni");
                    datIn = datIn + " " + hourIn;
                    dateFin = request.getParameter("txtDateFin");
                    hourFin = request.getParameter("txtHourfin");
                    dateFin = dateFin + " " + hourFin;
                    shift = request.getParameter("txtshift");
                    if (idBinn > 0) {
                        result = binnacleJpa.BinnacleUpdate(idBinn, datIn, dateFin, shift);
                        request.setAttribute("UpdateBinnacle", result);
                    } else {
                        result = binnacleJpa.BinnacleRegister(datIn, dateFin, shift, idUser);
                        request.setAttribute("RegisterBinnacle", result);
                    }
                    request.getRequestDispatcher("Binnacle?opt=1&idBinn=0").forward(request, response);
//</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE BINNACLE DATA">
                    try {
                        idBinn = Integer.parseInt(request.getParameter("idBinn"));
                    } catch (Exception e) {
                        idBinn = 0;
                    }
                    binnacle = request.getParameter("txtBinnacle");

                    result = binnacleJpa.BinnacleUpdateData(idBinn, binnacle);
                    request.setAttribute("UpdateDatabinnacle", result);
                    if (result) {
                        activitySystem.ActivityRegister(idUser, 2, "Bitacora", "Se registra bitacora del día", 1, userSession);
                    }
                    request.getRequestDispatcher("Binnacle?opt=1&idBinn=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="SEND BINNACLE">
                    try {
                        idBinn = Integer.parseInt(request.getParameter("idBinn"));
                    } catch (Exception e) {
                        idBinn = 0;
                    }
                    idAct = request.getParameter("txtiIdAct");

                    result = binnacleJpa.BinnacleUpdateStateFinal(idBinn, idAct, 1);

//                    ------------------------ PENDIENTE CREAR METODOS DE CORREO -------------------
                    request.setAttribute("SendBinnacle", result);
                    request.getRequestDispatcher("Binnacle?opt=1&idBinn=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="STATE BINNACLE">
                    try {
                        idBinn = Integer.parseInt(request.getParameter("idBinn"));
                    } catch (Exception e) {
                        idBinn = 0;
                    }
                    ste = Integer.parseInt(request.getParameter("state"));
                    result = binnacleJpa.BinnacleUpdateState(idBinn, ste);
                    if (ste == 0) {
                        request.setAttribute("GetBackBinnacle", result);
                    } else if (ste == 2) {
                        request.setAttribute("FinishBinnacle", result);
                    }
                    request.getRequestDispatcher("Binnacle?opt=1&idBinn=0").forward(request, response);
//</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="CHECK BINNACLES">
                    idRec = request.getParameter("txtIdRecolected");
                    String ids = idRec.replace("][", ",").replace("[", "").replace("]", "");
                    result = binnacleJpa.CheckBinnacleBoss(ids);
                    request.setAttribute("CheckBinnacle", result);
                    request.getRequestDispatcher("Binnacle?opt=1&idBinn=0").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("Binnacle.jsp").forward(request, response);
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
