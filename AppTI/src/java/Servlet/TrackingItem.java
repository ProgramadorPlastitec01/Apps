package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Method.RemoveWord;
import Controller.MoveItemJpaController;
import java.util.List;

public class TrackingItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        RemoveWord rmov = new RemoveWord();
        MoveItemJpaController MoveItemJpa = new MoveItemJpaController();

        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        int opt = Integer.parseInt(request.getParameter("opt"));
        boolean result = false;
        String ref = "", dateMove = "", keyWord = "";
        int numItem = 0, nummov = 0;

        try {
            switch (opt) {
                case 1:
                    try {
                        String validation = "";
                        try {
                            numItem = Integer.parseInt(request.getParameter("txt_numItem"));
                            validation += " i.item_num = '" + numItem + "' AND";
                        } catch (Exception e) {
                            numItem = 0;
                        }
                        try {
                            ref = request.getParameter("txt_Ref");
                            if (!ref.equals("")) {
                                validation += " f.cod_reference = '" + ref + "' AND";
                            }
                        } catch (Exception e) {
                            ref = "";
                        }
                        try {
                            dateMove = request.getParameter("txt_dateMove");
                            if (!dateMove.equals("")) {
                                validation += " m.mov_date = '" + dateMove + "' AND";
                            }
                        } catch (Exception e) {
                            dateMove = "";
                        }
                        try {
                            nummov = Integer.parseInt(request.getParameter("txt_numMov"));
                            validation += " m.mov_num = '" + nummov + "' AND";
                        } catch (Exception e) {
                            nummov = 0;
                        }
                        try {
                            keyWord = request.getParameter("txt_keyword");
                            if (!keyWord.equals("")) {
                                validation += " ( i.item_num like '%" + keyWord + "%' AND f.cod_reference like '%" + keyWord + "%' OR f.ref_name like '%" + keyWord + "%' OR m.mov_num like '%" + keyWord + "%' OR m.mov_location like '%" + keyWord + "%' OR m.mov_obs like '%" + keyWord + "%' )";
                            }
                        } catch (Exception e) {
                            keyWord = "";
                        }

                        String q = rmov.RemoveLastWord(validation, "AND");

                        List lst_result = MoveItemJpa.ConsultSetting(q);
                        request.setAttribute("ResultDataSearch", lst_result);
                        request.getRequestDispatcher("TrackingItems.jsp").forward(request, response);
                    } catch (Exception e) {
                        request.getRequestDispatcher("TrackingItems.jsp").forward(request, response);
                    }
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
