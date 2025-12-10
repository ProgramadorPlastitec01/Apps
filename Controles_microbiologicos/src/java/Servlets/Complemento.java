package Servlets;

import controladoras.AreaMuestradaJpaController;
import controladoras.DesinfectanteJpaController;
import controladoras.TipoAreaJpaController;
import controladoras.TipoNivelJpaController;
import controladoras.UnidadesJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complemento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            DesinfectanteJpaController jpacdsf = new DesinfectanteJpaController();
            AreaMuestradaJpaController jpacame = new AreaMuestradaJpaController();
            TipoAreaJpaController jpactar = new TipoAreaJpaController();
            UnidadesJpaController jpacumd = new UnidadesJpaController();
            TipoNivelJpaController jpa_tipoN = new TipoNivelJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String desinfectante = "", tipo_area = "", area_muestrada = "", unidad_medida = "", tipo_nivel = "";
            int dato = 0, cumple = 0, alerta = 0, accion = 0, incumplimiento = 0, id_tipoNivel = 0, estado = 0;
            int id_desinfectante = 0, id_area_muestrada = 0, id_tipo_area = 0, id_unidad_medida = 0;
            String tipo = "";
            switch (opc) {
                case 1:
                    try {
                        id_desinfectante = Integer.parseInt(request.getParameter("idD"));
                    } catch (Exception e) {
                        id_desinfectante = 0;
                    }
                    if (id_desinfectante != 0) {
                        estado = Integer.parseInt(request.getParameter("est"));
                        jpacdsf.EstadoDesinfectante(id_desinfectante, estado);
                    }
                    tipo = "Desinfectantes";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 2:
                    desinfectante = request.getParameter("Txt_desinfectante").toString();
                    proceso = jpacdsf.Registrar_desinfectante(desinfectante);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_desinfectante");
                    } else {
                        request.setAttribute("Alerta", "Error_desinfectante");
                    }
                    request.setAttribute("var1", desinfectante);
                    request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    break;
                case 3:
                    try {
                        id_area_muestrada = Integer.parseInt(request.getParameter("idA"));
                    } catch (Exception e) {
                        id_area_muestrada = 0;
                    }
                    if (id_area_muestrada != 0) {
                        estado = Integer.parseInt(request.getParameter("est"));
                        jpacame.EstadoAreaMuestrada(id_area_muestrada, estado);
                    }
                    tipo = "Areas_muestradas";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 4:
                    area_muestrada = request.getParameter("Txt_area_muestrada").toString();
                    proceso = jpacame.Registrar_area_muestrada(area_muestrada);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_area_muestrada");
                    } else {
                        request.setAttribute("Alerta", "Error_area_muestrada");
                    }
                    request.setAttribute("var1", area_muestrada);
                    request.getRequestDispatcher("Complemento?opc=3").forward(request, response);
                    break;
                case 5:
                    try {
                        id_tipo_area = Integer.parseInt(request.getParameter("idTA"));
                    } catch (Exception e) {
                        id_tipo_area = 0;
                    }
                    if (id_tipo_area != 0) {
                        estado = Integer.parseInt(request.getParameter("est"));
                        jpactar.EstadoTipoArea(id_tipo_area, estado);
                    }
                    tipo = "Tipos_areas";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 6:
                    tipo_area = request.getParameter("Txt_tipo_area");
                    id_tipoNivel = Integer.parseInt(request.getParameter("slc_tipoN"));
                    proceso = jpactar.Registrar_tipo_area(tipo_area, id_tipoNivel);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_tipo_area");
                    } else {
                        request.setAttribute("Alerta", "Error_tipo_area");
                    }
                    request.setAttribute("var1", tipo_area);
                    request.getRequestDispatcher("Complemento?opc=5").forward(request, response);
                    break;
                case 7:
                    try {
                        id_unidad_medida = Integer.parseInt(request.getParameter("idU"));
                    } catch (Exception e) {
                        id_unidad_medida = 0;
                    }
                    if (id_unidad_medida != 0) {
                        estado = Integer.parseInt(request.getParameter("est"));
                        jpacumd.EstadoUnidadMedida(id_unidad_medida, estado);
                    }
                    tipo = "Unidades_medida";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 8:
                    unidad_medida = request.getParameter("Txt_unidad_medida").toString();
                    proceso = jpacumd.Registrar_unidad_medida(unidad_medida);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_unidad_medida");
                    } else {
                        request.setAttribute("Alerta", "Error_unidad_medida");
                    }
                    request.setAttribute("var1", unidad_medida);
                    request.getRequestDispatcher("Complemento?opc=7").forward(request, response);
                    break;
                case 9:
                    try {
                        id_tipoNivel = Integer.parseInt(request.getParameter("idTN"));
                    } catch (Exception e) {
                        id_tipoNivel = 0;
                    }
                    tipo = "Tipo_nivel";
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("id_tipoNivel", id_tipoNivel);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 10:
                    try {
                        id_tipoNivel = Integer.parseInt(request.getParameter("idTN"));
                    } catch (Exception e) {
                        id_tipoNivel = 0;
                    }
                    tipo_nivel = request.getParameter("Txt_tipo");
                    dato = Integer.parseInt(request.getParameter("Txt_dato"));
                    cumple = Integer.parseInt(request.getParameter("Txt_cumple"));
                    alerta = Integer.parseInt(request.getParameter("Txt_alerta"));
                    accion = Integer.parseInt(request.getParameter("Txt_accion"));
                    incumplimiento = Integer.parseInt(request.getParameter("Txt_incumplimiento"));
                    if (id_tipoNivel != 0) {
                        jpa_tipoN.EstadoTipoNivel(id_tipoNivel);
                    }
                    proceso = jpa_tipoN.RegistrarTipoNivel(id_tipoNivel, tipo_nivel, dato, cumple, alerta, accion, incumplimiento);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_tipo_nivel");
                    } else {
                        request.setAttribute("Alerta", "Error_tipo_nivel");
                    }
                    request.setAttribute("var1", tipo_nivel);
                    request.getRequestDispatcher("Complemento?opc=9").forward(request, response);
                    break;
                case 11:

//                  tipo_nivel = request.getParameter("Txt_tipo");
//                    dato = Integer.parseInt(request.getParameter("Txt_dato"));
//                    cumple = Integer.parseInt(request.getParameter("Txt_cumple"));
//                    alerta = Integer.parseInt(request.getParameter("Txt_alerta"));
//                    accion = Integer.parseInt(request.getParameter("Txt_accion"));
//                    incumplimiento = Integer.parseInt(request.getParameter("Txt_incumplimiento"));
//                    proceso = jpacusa.Modificar_complemento(tipo_nivel, dato, cumple, alerta, accion, incumplimiento);
//                    if (proceso) {
//                        request.setAttribute("Alerta", "Modificar_usuario");
//                       // request.setAttribute("var1", nombre + " " + apellido);
//                        request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
//                    } else {
//                        request.setAttribute("Alerta", "Error_usuario_modificar");
//                        //request.setAttribute("var1", nombre + " " + apellido);
//                        request.getRequestDispatcher("Usuario?opc=1&fto=").forward(request, response);
//                    }
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
