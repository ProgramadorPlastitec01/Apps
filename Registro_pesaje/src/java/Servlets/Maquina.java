package Servlets;

import Controladores.MaquinaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Maquina extends HttpServlet {

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
            MaquinaJpaController MaquinaJpa = new MaquinaJpaController();
            List lst_maquina = null;
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            String nmb_maquina = "", nm_molde = "", nmb_peso = "", tara = "", cod_prod = "", prod = "", cod = "", Txt_code = "", unidad_medidad = "";
            int id_maq = 0, est = 0;

            //</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE MAQUINAS">

                    try {
                        id_maq = Integer.parseInt(request.getParameter("id_maq"));
                    } catch (Exception e) {
                        id_maq = 0;
                    }
                    request.setAttribute("id_maquina", id_maq);
                    request.getRequestDispatcher("maquina.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR MAQUINAS">
                    try {
                        id_maq = Integer.parseInt(request.getParameter("id_maq"));
                    } catch (Exception e) {
                        id_maq = 0;
                    }
                    try {
                        nmb_maquina = request.getParameter("nmb_maq");
                    } catch (Exception e) {
                        nmb_maquina = "";
                    }
                    try {
                        cod_prod = request.getParameter("Cbx_codigo");
                        String[] Arg_cod_prod = cod_prod.toString().split("///");
                        cod = Arg_cod_prod[0].toString().trim();
                        prod = Arg_cod_prod[1].toString().trim();
                    } catch (Exception e) {
                        cod_prod = "";
                    }

                    try {
                        nm_molde = request.getParameter("nmb_mold");
                    } catch (Exception e) {
                        nm_molde = "";
                    }
                    try {
                        tara = request.getParameter("tara");
                    } catch (Exception e) {
                        tara = "";
                    }
                    try {
                        unidad_medidad = request.getParameter("unidad_medidad");
                    } catch (Exception e) {
                        unidad_medidad = "";
                    }

                    if (id_maq == 0) {
                        result = MaquinaJpa.RegistrarMaquina(nmb_maquina, cod, prod, nm_molde, tara, 1, Rol_usuario);
                        request.setAttribute("Registro_maquina", result);
                    } else {
                        result = MaquinaJpa.EditarMaquina(id_maq, nmb_maquina, cod, prod, nm_molde, tara);
                        id_maq = 0;
                        request.setAttribute("EditarMaquina", result);
                    }

                    request.getRequestDispatcher("Maquina?opc=1&id_maq=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO MAQUINAS">
                    try {
                        id_maq = Integer.parseInt(request.getParameter("id_maq"));
                    } catch (Exception e) {
                        id_maq = 0;
                    }
                    try {
                        est = Integer.parseInt(request.getParameter("est"));
                    } catch (Exception e) {
                        est = 0;
                    }
                    if (est == 1) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                    result = MaquinaJpa.CambiarEstadoMaquina(id_maq, est);
                    request.setAttribute("CambiarEstadoMaquina", result);
                    request.getRequestDispatcher("Maquina?opc=1&id_maq=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA PRODUCTOS FACTORY">
                    try {
                        Txt_code = request.getParameter("Txt_code");
                    } catch (Exception e) {
                        Txt_code = "";
                    }
                    request.setAttribute("CodigoFact", Txt_code);
                    request.getRequestDispatcher("Maquina?opc=1&id_maq=0").forward(request, response);
                    //</editor-fold>
                    break;
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("maquina.jsp").forward(request, response);
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
