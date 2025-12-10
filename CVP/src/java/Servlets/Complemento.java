package Servlets;

import Controladores.AreaJpaController;
import Controladores.GrupoJpaController;
import Controladores.TipoCalificacionJpaController;
import Controladores.TipoInformeJpaController;
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
            TipoCalificacionJpaController jpactcl = new TipoCalificacionJpaController();
            GrupoJpaController jpacgpo = new GrupoJpaController();
            TipoInformeJpaController jpactif = new TipoInformeJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            String filtro = "";
            int tipo_estado = 0;
            int id_area = 0;
            int id_tipo_informe = 0;
            int vigencia = 0;
            int id_grupo = 0;
            int id_tipo_calificacion = 0;
            boolean proceso = true;
            String area = "";
            String sigla = "";
            String correo = "";
            String tipo_informe = "";
            String color = "";
            String tipo_clasificacion = "";
            String descripcion = "";
            String grupo = "";
            String sub_grupo = "";
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
                case 4:
                    tipo = "Modulo_tipo_calificacion";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 5:
                    id_tipo_calificacion = Integer.parseInt(request.getParameter("Id_tipo_calificacion").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpactcl.Activar_tipo_calificacion(id_tipo_calificacion);
                    } else {
                        jpactcl.Desactivar_tipo_calificacion(id_tipo_calificacion);
                    }
                    request.getRequestDispatcher("Complemento?opc=4").forward(request, response);
                    break;
                case 6:
                    tipo_clasificacion = request.getParameter("Txt_nombre");
                    descripcion = request.getParameter("Txt_descripcion");
                    proceso = jpactcl.Registrar_tipo_calificacion(tipo_clasificacion, descripcion, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_tipo_calificacion");
                    } else {
                        request.setAttribute("Alerta", "Error_tipo_calificacion");
                    }
                    request.getRequestDispatcher("Complemento?opc=4").forward(request, response);
                    break;
                case 7:
                    tipo = "Modulo_grupos";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 8:
                    id_grupo = Integer.parseInt(request.getParameter("Id_grupo").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacgpo.Activar_grupo(id_grupo);
                    } else {
                        jpacgpo.Desactivar_grupo(id_grupo);
                    }
                    request.getRequestDispatcher("Complemento?opc=7").forward(request, response);
                    break;
                case 9:
                    grupo = request.getParameter("Txt_nombre");
                    sub_grupo = request.getParameter("Cbx_grupo");
                    color = request.getParameter("Txt_color");
                    proceso = jpacgpo.Registrar_grupos(grupo, sub_grupo, color, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_grupo");
                    } else {
                        request.setAttribute("Alerta", "Error_grupo");
                    }
                    request.getRequestDispatcher("Complemento?opc=7").forward(request, response);
                    break;
                case 10:
                    tipo = "Modulo_tipo_informe";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 11:
                    id_tipo_informe = Integer.parseInt(request.getParameter("Id_tipo_informe").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpactif.Activar_tipo_informe(id_tipo_informe);
                        if (proceso) {
                            request.setAttribute("Alerta", "activar_tipo_informe");
                        } else {
                            request.setAttribute("Alerta", "tipo_informe_fail");
                        }
                    } else {
                        proceso = jpactif.Desactivar_tipo_informe(id_tipo_informe);
                        if (proceso) {
                            request.setAttribute("Alerta", "desactivar_tipo_informe");
                        } else {
                            request.setAttribute("Alerta", "tipo_informe_fail");
                        }
                    }
                    request.getRequestDispatcher("Complemento?opc=10").forward(request, response);
                    break;
                case 12:
                    tipo_informe = request.getParameter("Txt_nombre");
                    color = request.getParameter("Txt_color");
                    vigencia = Integer.parseInt(request.getParameter("Rdb_vigencia"));
                    proceso = jpactif.Registrar_tipo_informe(tipo_informe, vigencia, color, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_tipo_informe");

                    } else {
                        request.setAttribute("Alerta", "Error_tipo_informe");
                    }
                    request.getRequestDispatcher("Complemento?opc=10").forward(request, response);
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
