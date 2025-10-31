package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controladores.RecipienteJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Recipiente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String Rol_usuario = "ADMINISTRADOR";
//            String Rol_usuario = sesion.getAttribute("Rol").toString();
//            String nombre_usuario = sesion.getAttribute("Nombre").toString();
            boolean result = false;
            RecipienteJpaController RecipienteJpa = new RecipienteJpaController();
            List lst_recipiente = null;
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int id_rec = 0, est = 0;
            String Txt_rec = "", Txt_bolsa = "", peso_rec = "", peso_bolsa = "", medida_recipiente = "", medidad_bolsa = "";
            //</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE RECIPIENTE">
                    try {
                        id_rec = Integer.parseInt(request.getParameter("id_rec"));
                    } catch (Exception e) {
                        id_rec = 0;
                    }
                    request.setAttribute("id_recipiente", id_rec);
                    request.getRequestDispatcher("recipiente.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR RECIPIENTE">
                    try {
                        id_rec = Integer.parseInt(request.getParameter("id_rec"));
                    } catch (Exception e) {
                        id_rec = 0;
                    }
                    try {
                        Txt_rec = request.getParameter("Txt_rec");
                    } catch (Exception e) {
                        Txt_rec = "";
                    }
                    try {
                        peso_rec = request.getParameter("peso_rec");
                    } catch (Exception e) {
                        peso_rec = "";
                    }
                    try {
                        Txt_bolsa = request.getParameter("Txt_bolsa");
                    } catch (Exception e) {
                        Txt_bolsa = "";
                    }
                    try {
                        peso_bolsa = request.getParameter("peso_bolsa");
                    } catch (Exception e) {
                        peso_bolsa = "";
                    }
                    try {
                        medida_recipiente = request.getParameter("medida_recipiente");
                    } catch (Exception e) {
                        medida_recipiente = "";
                    }
                    try {
                        medidad_bolsa = request.getParameter("medida_bolsa");
                    } catch (Exception e) {
                        medidad_bolsa = "";
                    }
                    if (id_rec == 0) {
                        result = RecipienteJpa.RegistrarRecipiente(Txt_rec, peso_rec, Txt_bolsa, peso_bolsa, 1, Rol_usuario, medida_recipiente, medidad_bolsa);
                        request.setAttribute("Registro_recipiente", result);
                    } else {
                        result = RecipienteJpa.ModificarRecipiente(id_rec, Txt_rec, peso_rec, Txt_bolsa, peso_bolsa, medida_recipiente, medidad_bolsa);
                        id_rec = 0;
                        request.setAttribute("Modificar_recipiente", result);
                    }

                    request.getRequestDispatcher("Recipiente?opc=1&id_rec=" + id_rec + "").forward(request, response);

                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO DE RECIPIENTE">
                    try {
                        id_rec = Integer.parseInt(request.getParameter("id_rec"));
                    } catch (Exception e) {
                        id_rec = 0;
                    }
                    try {
                        est = Integer.parseInt(request.getParameter("est"));
                    } catch (Exception e) {
                        est = 0;
                    }

                    if (est == 1) {
                        result = RecipienteJpa.ModificarEstadoRecipienteId(id_rec, 2);
                    } else {
                        result = RecipienteJpa.ModificarEstadoRecipienteId(id_rec, 1);
                    }
                    request.setAttribute("ModificarEst_recipiente", result);
                    request.getRequestDispatcher("Recipiente?opc=1&id_rec=0").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("recipiente.jsp").forward(request, response);
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
