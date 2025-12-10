package Servlets;

import Controladores.PermisosJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Permisos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        PermisosJpaController jpa_permisos = new PermisosJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
        int opc = Integer.parseInt(request.getParameter("opc"));
        String usu_registro = "", modulo = "", opcion = "", desc = "", cargo = "";
        int id_permisos = 0, id_p = 0, estado = 0;
        boolean proceso = false;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES SESION">
        HttpSession sesion = request.getSession();
        usu_registro = sesion.getAttribute("Usuario").toString();
        cargo = sesion.getAttribute("Cargo").toString();
        //</editor-fold>

        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO GENERAL">
                    try {
                        id_permisos = Integer.parseInt(request.getParameter("idPerm").toString());
                    } catch (Exception e) {
                        id_permisos = 0;
                    }

                    request.setAttribute("idPerm", id_permisos);
                    request.getRequestDispatcher("Permisos.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR PERMISO">
                    try {
                        modulo = request.getParameter("Mod_permisos");
                        opcion = request.getParameter("Opc_permisos");
                        desc = request.getParameter("Desc_permisos");

                        if (modulo == null || opcion == null || desc == null) {
                            request.getRequestDispatcher("Permisos?opc=1").forward(request, response);
                        } else {
                            proceso = jpa_permisos.PermissionRegister(modulo, opcion, desc, cargo);

                            if (proceso) {
                                request.setAttribute("Alerta", "Registro_permiso");
                            } else {
                                request.setAttribute("Alerta", "error_Registro_permiso");
                            }

                            request.getRequestDispatcher("Permisos?opc=1").forward(request, response);
                        }

                    } catch (Exception e) {
                        request.getRequestDispatcher("Permisos?opc=1").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PERMISO">
                    try {
                        id_p = Integer.parseInt(request.getParameter("id").toString());
                        estado = Integer.parseInt(request.getParameter("estado").toString());
                        modulo = request.getParameter("Mod_permisos");
                        opcion = request.getParameter("Opc_permisos");
                        desc = request.getParameter("Desc_permisos");

                        proceso = jpa_permisos.PermissionUpdate(id_p, modulo, opcion, desc, estado);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_permiso");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_permiso");
                        }

                        request.getRequestDispatcher("Permisos?opc=1").forward(request, response);

                    } catch (Exception e) {
                        request.getRequestDispatcher("Permisos?opc=1").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO PERMISO">
                    id_p = Integer.parseInt(request.getParameter("Id").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());

                    proceso = jpa_permisos.PermissionEstate(id_p, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "permiso_estado");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Permisos?opc=1").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Permisos.jsp").forward(request, response);
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
