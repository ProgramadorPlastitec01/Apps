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
import Controller.ItemJpaController;
import java.util.List;

public class TrackingItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        RemoveWord rmov = new RemoveWord();
        MoveItemJpaController MoveItemJpa = new MoveItemJpaController();
        ItemJpaController ItemJpa = new ItemJpaController();

        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        int opt = Integer.parseInt(request.getParameter("opt"));
        boolean result = false;
        String ref = "", dateMove = "", keyWord = "", model = "", serial = "", Locati = "", Observ = "", idTemToSig = "", NameSigna = "";
        int numItem = 0, nummov = 0, action = 0, idItem = 0, nmItem = 0, idMov = 0, docx = 0, codx = 0, idSignat = 0;

        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        String validation = "";
                        try {
                            numItem = Integer.parseInt(request.getParameter("txt_numItem"));
                            validation += " i.item_num = '" + numItem + "' AND";
                        } catch (Exception e) {
                            numItem = 0;
                        }
                        try {
                            action = Integer.parseInt(request.getParameter("action"));
                        } catch (Exception e) {
                            action = 0;
                        }
                        try {
                            idItem = Integer.parseInt(request.getParameter("idItem"));
                        } catch (Exception e) {
                            idItem = 0;
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
                            idTemToSig = request.getParameter("idItmeToSig");
                        } catch (Exception e) {
                            idTemToSig = "";
                        }
                        try {
                            keyWord = request.getParameter("txt_keyword");
                            if (!keyWord.equals("")) {
                                validation += " ( i.item_num like '%" + keyWord + "%' AND f.cod_reference like '%" + keyWord + "%' OR f.ref_name like '%" + keyWord + "%' OR m.mov_num like '%" + keyWord + "%' OR m.mov_location like '%" + keyWord + "%' OR m.mov_obs like '%" + keyWord + "%' )";
                            }
                        } catch (Exception e) {
                            keyWord = "";
                        }
                        try {
                            docx = Integer.parseInt(request.getParameter("docx"));
                        } catch (Exception e) {
                            docx = 0;
                        }
                        try {
                            codx = Integer.parseInt(request.getParameter("codx"));
                        } catch (Exception e) {
                            codx = 0;
                        }

                        String q = rmov.RemoveLastWord(validation, "AND");
                        List lst_result = null;
                        lst_result = MoveItemJpa.ConsultSetting(q);
                        if (lst_result == null) {
                            lst_result = MoveItemJpa.ConsultMoveItems();
                        }
                        request.setAttribute("action", action);
                        request.setAttribute("idItem", idItem);
                        request.setAttribute("ResultDataSearch", lst_result);
                        request.setAttribute("idTemToSig", idTemToSig);
                        request.setAttribute("docx", docx);
                        request.setAttribute("codx", codx);
                        request.getRequestDispatcher("TrackingItems.jsp").forward(request, response);
                    } catch (Exception e) {
                        request.getRequestDispatcher("TrackingItems.jsp").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="EDIT ITEM DATA">
                    try {
                        idItem = Integer.parseInt(request.getParameter("idItem"));
                    } catch (Exception e) {
                        idItem = 0;
                    }
                    try {
                        idMov = Integer.parseInt(request.getParameter("txtidMov"));
                    } catch (Exception e) {
                        idMov = 0;
                    }
                    nmItem = Integer.parseInt(request.getParameter("nmbItem"));
                    model = request.getParameter("txtModel");
                    serial = request.getParameter("txtSerial");
                    Locati = request.getParameter("TxtLocation");
                    Observ = request.getParameter("txtObs");
                    result = ItemJpa.ItemUpdateData(idItem, nmItem, model, serial, Observ);
                    if (result) {
                        result = MoveItemJpa.UpdateMoveItemData(idMov, Locati);
                    }
                    request.setAttribute("EditMoveItem", result);
                    request.getRequestDispatcher("TrackingItem?opt=1&action=1&idItem=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE ITEMS">
                    try {
                        idTemToSig = request.getParameter("idItmeToSig");
                    } catch (Exception e) {
                        idTemToSig = "";
                    }
                    try {
                        NameSigna = request.getParameter("NameSigna");
                    } catch (Exception e) {
                        NameSigna = "";
                    }
                    try {
                        codx = Integer.parseInt(request.getParameter("codx"));
                    } catch (Exception e) {
                        codx = 0;
                    }

                    idTemToSig = idTemToSig.replace("][", ",").replace("[", "").replace("]", "").trim();
                    idSignat = Integer.parseInt(request.getParameter("idSig"));

                    String sginature = idSignat + "/" + NameSigna + "/" + codx;

                    result = MoveItemJpa.MoveItemSignature(sginature, idTemToSig, idUser);

                    request.setAttribute("SignatureMove", result);
                    request.getRequestDispatcher("TrackingItem?opt=1&action=1&codx=0&idItmeToSig=").forward(request, response);
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
