package Servlets;

import Controladores.CorreoJpaController;
import Controladores.GeneralJpaController;
import Controladores.InstruccionJpaController;
import Controladores.LineaJpaController;
import Controladores.ParametroGeneralJpaController;
import Controladores.ParametroJpaController;
import Controladores.ParametroZonaJpaController;
import Controladores.RegistroJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.ZonaJpaController;
import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complementos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            //JPAS
            CorreoJpaController jpaccro = new CorreoJpaController();
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            ZonaJpaController jpaczna = new ZonaJpaController();
            GeneralJpaController jpacgnr = new GeneralJpaController();
            ParametroZonaJpaController jpacpzn = new ParametroZonaJpaController();
            ParametroGeneralJpaController jpacpgn = new ParametroGeneralJpaController();
            InstruccionJpaController jpacitc = new InstruccionJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            UsuarioJpaController jpaujpc = new UsuarioJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String tipo = "";
            int estado = 0;
            int id_correo = 0;
            String funcion = "", emisor = "", password = "", receptor = "", host = "";
            int id_tipo_equipo = 0, id_tipo_mtto = 0, frecuencia = 0, frecuencia_alerta = 0, estado_mtto = 0, version = 0;
            String tipo_equipo = "", codigo_mm = "", codigo_insp = "", codigo_lub = "";
            int id_parametro = 0;
            String grupo = "", actividad = "";
            int id_linea = 0;
            int id_registro = 0;
            int id_instrucccion = 0;
            int id_zona = 0;
            int port = 0;
            int rol = 0;
            int Id_usuario = 0;
            int id_general = 0;
            String codigo = "", linea = "";
            String titulo = "", contenido = "";
            String observaciones = "", nota_importante = "";
            String fecha = "";
            String zona = "";
            String general = "";
            String descripcion = "";
            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="CORREOS">
                case 1:
                    tipo = "Correos";
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_correo = Integer.parseInt(request.getParameter("Id_correo").toString());
                    } catch (Exception e) {
                        id_correo = 0;
                    }
                    request.setAttribute("Id_correo", id_correo);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 2:
                    id_correo = Integer.parseInt(request.getParameter("Id_correo").toString());
                    funcion = request.getParameter("Txt_funcion");
                    emisor = request.getParameter("Txt_emisor");
                    password = request.getParameter("Txt_password");
                    receptor = request.getParameter("Txt_receptor");
                    host = request.getParameter("Txt_host");
                    port = Integer.parseInt(request.getParameter("Txt_port"));
                    if (id_correo > 0) {
                        proceso = jpaccro.Modificar_correo(id_correo, funcion, emisor, password, receptor, host, port, rol_usuario);
                    } else {
                        proceso = jpaccro.Registrar_correo(funcion, emisor, password, receptor, host, port, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_correo == 0) ? "Registro" : "Modificar") + "_correo");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_correo == 0) ? "registro" : "modificar") + "_correo");
                    }
                    request.setAttribute("var1", funcion);
                    request.getRequestDispatcher("Complementos?opc=1&Id_correo=0").forward(request, response);
                    break;
                case 3:
                    id_correo = Integer.parseInt(request.getParameter("Id_correo").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpaccro.Activar_correo(id_correo);
                    } else {
                        jpaccro.Desactivar_correo(id_correo);
                    }
                    request.getRequestDispatcher("Complementos?opc=1&Id_correo=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TIPOS DE EQUIPO">
                case 4:
                    tipo = "Tipo_equipos";
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    } catch (Exception e) {
                        id_tipo_equipo = 0;
                    }
                    request.setAttribute("Id_tipo_equipo", id_tipo_equipo);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 5:
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    tipo_equipo = request.getParameter("Txt_tipo_equipo");
                    frecuencia = Integer.parseInt(request.getParameter("Txt_frecuencia").toString());
                    frecuencia_alerta = Integer.parseInt(request.getParameter("Txt_frecuencia_alerta").toString());
                    estado_mtto = Integer.parseInt(request.getParameter("Rdb_tipo_registro").toString());
                    if (estado_mtto == 1) {
                        codigo_mm = request.getParameter("Txt_codigo_mm");
                        codigo_insp = request.getParameter("Txt_codigo_insp");
                        codigo_lub = "N/A";
                    } else {
                        codigo_mm = "N/A";
                        codigo_insp = "N/A";
                        codigo_lub = request.getParameter("Txt_codigo_lub");
                    }
                    if (id_tipo_equipo > 0) {
                        proceso = jpacteq.Modificar_tipo_equipo(id_tipo_equipo, tipo_equipo, frecuencia, frecuencia_alerta, codigo_mm, codigo_insp, codigo_lub, estado_mtto, rol_usuario);
                    } else {
                        proceso = jpacteq.Registrar_tipo_equipo(tipo_equipo, frecuencia, frecuencia_alerta, codigo_mm, codigo_insp, codigo_lub, estado_mtto, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_tipo_equipo == 0) ? "Registro" : "Modificar") + "_tipo_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_tipo_equipo == 0) ? "registro" : "modificar") + "_tipo_equipo");
                    }
                    request.setAttribute("var1", tipo_equipo);
                    request.getRequestDispatcher("Complementos?opc=4&Id_tipo_equipo=0").forward(request, response);
                    break;
                case 6:
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpacteq.Activar_tipo_equipo(id_tipo_equipo);
                    } else {
                        jpacteq.Desactivar_tipo_equipo(id_tipo_equipo);
                    }
                    request.getRequestDispatcher("Complementos?opc=4&Id_tipo_equipo=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                case 7:
                    tipo = "Parametros";
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto").toString());
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    } catch (Exception e) {
                        id_parametro = 0;
                    }
                    request.setAttribute("Id_tipo_equipo", id_tipo_equipo);
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.setAttribute("Id_parametro", id_parametro);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 8:
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    grupo = request.getParameter("Txt_grupo");
                    actividad = request.getParameter("Txt_actividad");
                    if (id_parametro > 0) {
                        proceso = jpacprm.Modificar_parametro(id_parametro, grupo, actividad, id_tipo_mtto, id_tipo_equipo, rol_usuario);
                    } else {
                        proceso = jpacprm.Registro_parametro(grupo, actividad, id_tipo_mtto, id_tipo_equipo, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_parametro == 0) ? "Registro" : "Modificar") + "_parametro");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_parametro == 0) ? "registro" : "modificar") + "_parametro");
                    }
                    request.getRequestDispatcher("Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=" + id_tipo_mtto + "&Id_parametro=0").forward(request, response);
                    break;
                case 9:
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Id_tipo_equipo").toString());
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpacprm.Activar_parametro(id_parametro);
                    } else {
                        jpacprm.Desactivar_parametro(id_parametro);
                    }
                    request.getRequestDispatcher("Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=" + id_tipo_mtto + "&Id_parametro=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LINEAS">
                case 10:
                    tipo = "Lineas";
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_linea = Integer.parseInt(request.getParameter("Id_linea").toString());
                    } catch (Exception e) {
                        id_linea = 0;
                    }
                    request.setAttribute("Id_linea", id_linea);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 11:
                    id_linea = Integer.parseInt(request.getParameter("Id_linea").toString());
                    codigo = request.getParameter("Txt_codigo");
                    linea = request.getParameter("Txt_linea");
                    id_zona = Integer.parseInt(request.getParameter("Cbx_zona").toString());
                    if (id_linea > 0) {
                        proceso = jpaclna.Modificar_linea(id_linea, linea, codigo, id_zona, rol_usuario);
                    } else {
                        proceso = jpaclna.Registrar_linea(linea, codigo, id_zona, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_linea == 0) ? "Registro" : "Modificar") + "_linea");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_linea == 0) ? "registro" : "modificar") + "_linea");
                    }
                    request.setAttribute("var1", linea);
                    request.getRequestDispatcher("Complementos?opc=10&Id_linea=0").forward(request, response);
                    break;
                case 12:
                    id_linea = Integer.parseInt(request.getParameter("Id_linea").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpaclna.Activar_linea(id_linea);
                    } else {
                        jpaclna.Desactivar_linea(id_linea);
                    }
                    request.getRequestDispatcher("Complementos?opc=10&Id_linea=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ZONAS">
                case 13:
                    tipo = "Zonas";
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_zona = Integer.parseInt(request.getParameter("Id_zona").toString());
                    } catch (Exception e) {
                        id_zona = 0;
                    }
                    request.setAttribute("Id_zona", id_zona);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 14:
                    id_zona = Integer.parseInt(request.getParameter("Id_zona").toString());
                    zona = request.getParameter("Txt_zona");
                    frecuencia = Integer.parseInt(request.getParameter("Txt_frecuencia").toString());
                    frecuencia_alerta = Integer.parseInt(request.getParameter("Txt_frecuencia_alerta").toString());
                    codigo = request.getParameter("Cbx_registro");
                    if (id_zona > 0) {
                        proceso = jpaczna.Modificar_zona(id_zona, zona, frecuencia, frecuencia_alerta, codigo, rol_usuario);
                    } else {
                        proceso = jpaczna.Registrar_zona(zona, frecuencia, frecuencia_alerta, codigo, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_zona == 0) ? "Registro" : "Modificar") + "_zona");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_zona == 0) ? "registro" : "modificar") + "_zona");
                    }
                    request.setAttribute("var1", zona);
                    request.getRequestDispatcher("Complementos?opc=13&Id_zona=0").forward(request, response);
                    break;
                case 15:
                    //REGISTRAR LINEAS A LAS ZONAS
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PARAMETROS ZONA">
                case 16:
                    tipo = "Parametros_zona";
                    id_zona = Integer.parseInt(request.getParameter("Id_zona").toString());
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    } catch (Exception e) {
                        id_parametro = 0;
                    }
                    request.setAttribute("Id_zona", id_zona);
                    request.setAttribute("Id_parametro", id_parametro);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 17:
                    id_zona = Integer.parseInt(request.getParameter("Id_zona").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    grupo = request.getParameter("Txt_grupo");
                    actividad = request.getParameter("Txt_actividad");
                    if (id_parametro > 0) {
                        proceso = jpacpzn.Modificar_parametro(id_parametro, grupo, actividad, rol_usuario);
                    } else {
                        proceso = jpacpzn.Registro_parametro(id_zona, grupo, actividad, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_parametro == 0) ? "Registro" : "Modificar") + "_parametro");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_parametro == 0) ? "registro" : "modificar") + "_parametro");
                    }
                    request.getRequestDispatcher("Complementos?opc=16&Id_zona=" + id_zona + "&Id_parametro=0").forward(request, response);
                    break;
                case 18:
                    id_zona = Integer.parseInt(request.getParameter("Id_zona").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpacpzn.Activar_parametro(id_parametro);
                    } else {
                        jpacpzn.Desactivar_parametro(id_parametro);
                    }
                    request.getRequestDispatcher("Complementos?opc=16&Id_zona=" + id_zona + "&Id_parametro=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="GENERALES">
                case 19:
                    tipo = "General";
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_general = Integer.parseInt(request.getParameter("Id_general").toString());
                    } catch (Exception e) {
                        id_general = 0;
                    }
                    request.setAttribute("Id_general", id_general);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 20:
                    id_general = Integer.parseInt(request.getParameter("Id_general").toString());
                    general = request.getParameter("Txt_general");
                    descripcion = request.getParameter("Txt_descripcion");
                    frecuencia = Integer.parseInt(request.getParameter("Txt_frecuencia").toString());
                    frecuencia_alerta = Integer.parseInt(request.getParameter("Txt_frecuencia_alerta").toString());
                    codigo = request.getParameter("Cbx_registro");
                    if (id_general > 0) {
                        proceso = jpacgnr.Modificar_actividad_general(id_general, general, descripcion, frecuencia, frecuencia_alerta, codigo, rol_usuario);
                    } else {
                        proceso = jpacgnr.Registrar_actividad_general(general, descripcion, frecuencia, frecuencia_alerta, codigo, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_general == 0) ? "Registro" : "Modificar") + "_general");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_general == 0) ? "registro" : "modificar") + "_general");
                    }
                    request.setAttribute("var1", general);
                    request.getRequestDispatcher("Complementos?opc=19&Id_general=0").forward(request, response);
                    break;
                case 21:
                    id_general = Integer.parseInt(request.getParameter("Id_general").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpacgnr.Activar_actividad_general(id_general);
                    } else {
                        jpacgnr.Desactivar_actividad_general(id_general);
                    }
                    request.getRequestDispatcher("Complementos?opc=19&Id_general=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PARAMETROS GENERALES">
                case 22:
                    tipo = "Parametros_general";
                    id_general = Integer.parseInt(request.getParameter("Id_general").toString());
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    } catch (Exception e) {
                        id_parametro = 0;
                    }
                    request.setAttribute("Id_general", id_general);
                    request.setAttribute("Id_parametro", id_parametro);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 23:
                    id_general = Integer.parseInt(request.getParameter("Id_general").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    grupo = request.getParameter("Txt_grupo");
                    actividad = request.getParameter("Txt_actividad");
                    if (id_parametro > 0) {
                        proceso = jpacpgn.Modificar_parametro(id_parametro, grupo, actividad, rol_usuario);
                    } else {
                        proceso = jpacpgn.Registro_parametro(id_general, grupo, actividad, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_parametro == 0) ? "Registro" : "Modificar") + "_parametro");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_parametro == 0) ? "registro" : "modificar") + "_parametro");
                    }
                    request.getRequestDispatcher("Complementos?opc=22&Id_general=" + id_general + "&Id_parametro=0").forward(request, response);
                    break;
                case 24:
                    id_general = Integer.parseInt(request.getParameter("Id_general").toString());
                    id_parametro = Integer.parseInt(request.getParameter("Id_parametro").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpacpgn.Activar_parametro(id_parametro);
                    } else {
                        jpacpgn.Desactivar_parametro(id_parametro);
                    }
                    request.getRequestDispatcher("Complementos?opc=22&Id_general=" + id_general + "&Id_parametro=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES DE SEGURIDADA">
                case 25:
                    tipo = "Instrucciones_seguridad";
                    request.setAttribute("Complementos", tipo);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 26:
                    id_instrucccion = Integer.parseInt(request.getParameter("Id_instruccion").toString());
                    titulo = request.getParameter("Txt_titulo");
                    contenido = request.getParameter("Txt_instruccion_seguridad");
                    proceso = jpacitc.Modificar_instruccion(id_instrucccion, titulo, contenido, rol_usuario);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizacion_seguridad");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizacion_seguridad");
                    }
                    request.getRequestDispatcher("Complementos?opc=25").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTROS">
                case 27:
                    tipo = "Registros";
                    request.setAttribute("Complementos", tipo);
                    try {
                        id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    request.setAttribute("Id_registro", id_registro);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 28:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    codigo = request.getParameter("Txt_codigo");
                    version = Integer.parseInt(request.getParameter("Txt_version").toString());
                    titulo = request.getParameter("Txt_titulo");
                    nota_importante = request.getParameter("Txt_nota");
                    observaciones = request.getParameter("Txt_observacion");
                    fecha = request.getParameter("Txt_fecha");
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Cbx_tipo_mtto").toString());
                    if (id_registro > 0) {
                        proceso = jpacrgt.Modificar_registro(id_registro, codigo, titulo, version, nota_importante, observaciones, fecha, id_tipo_mtto, rol_usuario);
                    } else {
                        proceso = jpacrgt.Registrar_registro(codigo, titulo, version, nota_importante, observaciones, fecha, id_tipo_mtto, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_registro == 0) ? "Registro" : "Modificar") + "_registro");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_registro == 0) ? "registro" : "modificar") + "_registro");
                    }
                    request.setAttribute("var1", codigo + " V " + version);
                    request.getRequestDispatcher("Complementos?opc=27&Id_registro=0").forward(request, response);
                    break;
                case 29:
                    id_registro = Integer.parseInt(request.getParameter("Id_registro").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpacrgt.Activar_registro(id_registro);
                    } else {
                        jpacrgt.Desactivar_registro(id_registro);
                    }
                    request.getRequestDispatcher("Complementos?opc=27&Id_registro=0").forward(request, response);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR ROL">
                case 30:
                    tipo = "Cambiar_rol";
                    request.setAttribute("Complementos", tipo);
                    request.getRequestDispatcher("Complementos.jsp").forward(request, response);
                    break;
                case 31:
                    Id_usuario = Integer.parseInt(request.getParameter("Id_usuario").toString());
                    rol = Integer.parseInt(request.getParameter("Cbx_rol"));
                    proceso = jpaujpc.Cambiar_rol(Id_usuario, rol);
                    if (proceso) {
                        request.setAttribute("Alerta", "Cambiar_rol");
                        request.getRequestDispatcher("Complementos?opc=30").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Complementos?opc=30").forward(request, response);
                    }
                    break;
                //</editor-fold>
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
