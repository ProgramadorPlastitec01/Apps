package Servlets;

import Controladores.AreaJpaController;
//import Controladores.GrupoJpaController;
//import Controladores.TipoCalificacionJpaController;
//import Controladores.TipoInformeJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complemento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            AreaJpaController jpacara = new AreaJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            int tipo_estado = 0;
            int id_area = 0;
            boolean proceso = true;
            String area = "";
            String sigla = "";
            String correo = "";
            switch (opc) {
                case 1:
                    tipo = "Modulo_area";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 2:
                    id_area = Integer.parseInt(request.getParameter("Id_area").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacara.Activar_area(id_area);
                    } else {
                        jpacara.Desactivar_area(id_area);
                    }
                    request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    break;
                case 3:
                    area = request.getParameter("Txt_nombre");
                    sigla = request.getParameter("Txt_sigla");
                    correo = request.getParameter("Txt_correo");
                    proceso = jpacara.Registrado_area(area, sigla, correo, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_area");
                    } else {
                        request.setAttribute("Alerta", "Error_area");
                    }
                    request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
