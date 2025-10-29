package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.appControllerJpa;

public class App extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            appControllerJpa AppJpa = new appControllerJpa();
            String FullName = request.getSession().getAttribute("FullName").toString();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idApp = 0, idSetting = 0;
            String name = "", logo = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    request.setAttribute("idApp", idApp);
                    request.getRequestDispatcher("app.jsp").forward(request, response);
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE APP">
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    name = request.getParameter("txtApp");
                    logo = request.getParameter("txtLogo");
                    idSetting = Integer.parseInt(request.getParameter("cbxSetting"));
                    if (idApp > 0) {
                        result = AppJpa.UpdateApp(idApp, name, logo, idSetting);
                        request.setAttribute("AppUpdate", result);
                    } else {
                        result = AppJpa.RegisterApp(name, logo, idSetting, FullName);
                        request.setAttribute("AppRegister", result);
                    }
                    request.getRequestDispatcher("App?opt=1&idApp=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="STATE APP">
                    try {
                        idApp = Integer.parseInt(request.getParameter("idApp"));
                    } catch (Exception e) {
                        idApp = 0;
                    }
                    result = AppJpa.StatApp(idApp);
                    request.setAttribute("AppUpdateStatus", result);
                    request.getRequestDispatcher("App?opt=1&idApp=0").forward(request, response);
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
