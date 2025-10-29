package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.ActivoJpaController;
import Metodos.Email;
import Metodos.Filtro_dinamico;

public class Activo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();

        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            HttpSession sesion = request.getSession();
            String nombreArea = (String) sesion.getAttribute("Area");
            int opc = Integer.parseInt(request.getParameter("opc"));
            String nombre = (String) sesion.getAttribute("Nombres");
            String rol = (String) sesion.getAttribute("NombreRol");
            ActivoJpaController jpa_activo = new ActivoJpaController();
            Filtro_dinamico class_filtro = new Filtro_dinamico();
            int idActivo, idAdicion, estado, area, asegurado, consultaEstado = 0;
            String codigo, ubicacion, nombre_equipo, marca, modelo, serie, ano_fabricacion, fabricante, orden_compra;
            String fecha_compra, costo, num_factura, descripcion, fecha_ingreso, tipo_activo, justificacion;
            boolean accion = true;
            Email correo = new Email();
            String fecha_i;
            String fecha_f;
            String busq;
            String fechas;
            String campo;
            String planta;
            String bodega;
            String piso;
            String proceso;
            String query = "";
            String fecha, valor, compra;
//</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    try {
                        query = request.getParameter("query").toString();
                    } catch (Exception e) {
                        query = "";
                    }
                    try {
                        idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    } catch (Exception e) {
                        idActivo = 0;
                    }
                    try {
                        consultaEstado = Integer.parseInt(request.getParameter("consultaEstado"));
                    } catch (Exception e) {
                        consultaEstado = 1;
                    }
                    request.setAttribute("idActivo", idActivo);
                    request.setAttribute("consultaEstado", consultaEstado);
                    if (idActivo != 0) {
                        request.setAttribute("Activo", "Modificar_activo");
                    } else {
                        request.setAttribute("Activo", "Registar_activo");
                    }
                    request.setAttribute("query", query);
                    request.getRequestDispatcher("Activo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                    codigo = request.getParameter("Txt_codigo");
                    planta = request.getParameter("Cbx_planta");
                    bodega = request.getParameter("Cbx_bodega");
                    piso = request.getParameter("Cbx_piso");
                    proceso = request.getParameter("Cbx_Proceso");
                    area = Integer.parseInt(request.getParameter("Cbx_area"));
                    nombre_equipo = request.getParameter("Txt_nombre_equipo");
                    marca = request.getParameter("Txt_marca");
                    modelo = request.getParameter("Txt_modelo");
                    serie = request.getParameter("Txt_serie");
                    ano_fabricacion = request.getParameter("Txt_ano_fabricacion");
                    fabricante = request.getParameter("Txt_fabricante");
                    orden_compra = request.getParameter("Txt_orden_compra");
                    fecha_compra = request.getParameter("Txt_fecha_compra");
                    costo = request.getParameter("Txt_costo");
                    num_factura = request.getParameter("Txt_num_factura");
                    descripcion = request.getParameter("Txt_descripcion");
                    fecha_ingreso = request.getParameter("Txt_fecha_ingreso");
                    tipo_activo = request.getParameter("Cbx_tipo_activo");
                    accion = jpa_activo.registrarActivo(codigo, planta, bodega, piso, proceso, area, nombre_equipo, marca, modelo, serie, ano_fabricacion, fabricante, orden_compra, fecha_compra, costo, num_factura, descripcion, fecha_ingreso, nombreArea, tipo_activo);
                    if (accion) {
                        ubicacion = planta + bodega + piso;
                        correo.notificarRegistroActivo(codigo, ubicacion, area, nombre_equipo, marca, modelo, serie, ano_fabricacion, fabricante, orden_compra, fecha_compra, costo, num_factura, descripcion, fecha_ingreso, nombreArea, tipo_activo);
                        request.setAttribute("Alerta", "Registro_activo");
                        request.setAttribute("var1", nombre_equipo);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Activo?opc=1&idActivo=0").forward(request, response);

                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    codigo = request.getParameter("Txt_codigoM");
                    planta = request.getParameter("Cbx_plantaM");
                    bodega = request.getParameter("Cbx_bodegaM");
                    piso = request.getParameter("Cbx_pisoM");
                    proceso = request.getParameter("Cbx_ProcesoM");
                    area = Integer.parseInt(request.getParameter("Cbx_areaM"));
                    nombre_equipo = request.getParameter("Txt_nombre_equipoM");
                    marca = request.getParameter("Txt_marcaM");
                    modelo = request.getParameter("Txt_modeloM");
                    serie = request.getParameter("Txt_serieM");
                    ano_fabricacion = request.getParameter("Txt_ano_fabricacionM");
                    fabricante = request.getParameter("Txt_fabricanteM");
                    orden_compra = request.getParameter("Txt_orden_compraM");
                    fecha_compra = request.getParameter("Txt_fecha_compraM");
                    costo = request.getParameter("Txt_costoM");
                    num_factura = request.getParameter("Txt_num_facturaM");
                    descripcion = request.getParameter("Txt_descripcionM");
                    fecha_ingreso = request.getParameter("Txt_fecha_ingresoM");
                    tipo_activo = request.getParameter("Cbx_tipo_activoM");
                    jpa_activo.registrarLogActivo(idActivo);
                    accion = jpa_activo.modificarActivo(idActivo, codigo, planta, bodega, piso, proceso, area, nombre_equipo, marca, modelo, serie, ano_fabricacion, fabricante, orden_compra, fecha_compra, costo, num_factura, descripcion, fecha_ingreso, tipo_activo);
                    if (accion == true) {
                        request.setAttribute("Alerta", "Modificar_activo");
                        request.setAttribute("var1", nombre_equipo);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Activo?opc=1&idActivo=0").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVAR">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    accion = jpa_activo.desactivaActivo(idActivo);
                    if (accion) {
                        request.setAttribute("Alerta", "cambio_estado");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Activo?opc=1&idActivo=0").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVAR">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    justificacion = request.getParameter("Txt_justificacion");
                    estado = Integer.parseInt(request.getParameter("estado"));
                    if (justificacion.equals("")) {
                        request.setAttribute("Alerta", "ErrorAtvDeclinado");
                    } else {
                        accion = jpa_activo.activarActivo(idActivo, justificacion);
                        if (accion) {
                            request.setAttribute("Alerta", "cambio_estado");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar");
                        }
                    }
                    if (estado == 2) {
                        request.getRequestDispatcher("Activo?opc=1&idActivo=0&consultaEstado=2").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Activo?opc=1&idActivo=0&consultaEstado=0").forward(request, response);
                    }
                    break;
//</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="DAR D-E BAJA">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    justificacion = request.getParameter("Txt_justificacion");
                    jpa_activo.registrarLogActivo(idActivo);
                    if (justificacion.equals("")) {
                        request.setAttribute("Alerta", "ErrorAtvDeclinado");
                    } else {
                        accion = jpa_activo.DarBajaActivo(idActivo, justificacion);
                        if (accion) {
                            request.setAttribute("Alerta", "cambio_estado");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar");
                        }
                    }
                    request.getRequestDispatcher("Activo?opc=1&idActivo=0").forward(request, response);

                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="ADICION">
                    try {
                        idAdicion = Integer.parseInt(request.getParameter("idAdicion"));
                    } catch (Exception e) {
                        idAdicion = 0;
                    }
                    try {
                        consultaEstado = Integer.parseInt(request.getParameter("consultaEstado"));
                    } catch (Exception e) {
                        consultaEstado = 1;
                    }
                    try {
                        query = request.getParameter("query").toString();
                    } catch (Exception e) {
                        query = "";
                    }
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    request.setAttribute("idActivo", idActivo);
                    request.setAttribute("Activo", "RegistrarAdicion");
                    request.setAttribute("idAdicion", idAdicion);
                    request.setAttribute("query", query);
                    request.setAttribute("consultaEstado", consultaEstado);
                    request.getRequestDispatcher("Activo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="DAR DE BAJA NOTIFICACIÓN">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    justificacion = request.getParameter("Txt_justificacion");
                    accion = jpa_activo.DarBajaActivo(idActivo, justificacion);
                    if (accion) {
                        request.setAttribute("Alerta", "cambio_estado");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Activo?opc=1&idActivo=0").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ADICION">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    fecha = request.getParameter("fecha");
                    valor = request.getParameter("txt_valor");
                    compra = request.getParameter("txt_orden");
                    descripcion = request.getParameter("txt_descricpion");
                    accion = jpa_activo.registrarAdicion(idActivo, fecha, valor, compra, descripcion, nombre);
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_adicion");
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Activo?opc=1&idActivo=0").forward(request, response);
                    break;
                //</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR AD">
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    idAdicion = Integer.parseInt(request.getParameter("idAdicion"));
                    fecha = request.getParameter("fecha");
                    valor = request.getParameter("txt_valor");
                    compra = request.getParameter("txt_orden");
                    descripcion = request.getParameter("txt_descricpion");
                    accion = jpa_activo.modificarAdicion(idAdicion, fecha, valor, compra, descripcion);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_adicion");
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Activo?opc=7&idActivo=" + idActivo + "&idAdicion=0").forward(request, response);
                    break;
                //</editor-fold>
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE ACTIVO">
                    try {
                        consultaEstado = Integer.parseInt(request.getParameter("consultaEstado"));
                    } catch (Exception e) {
                        consultaEstado = 1;
                    }
                    try {
                        query = request.getParameter("query").toString();
                    } catch (Exception e) {
                        query = "";
                    }
                    idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    request.setAttribute("idActivo", idActivo);
                    request.setAttribute("consultaEstado", consultaEstado);
                    request.setAttribute("query", query);
                    request.setAttribute("Activo", "HistorialActivos");
                    request.getRequestDispatcher("Activo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA">
                    try {
                        idActivo = Integer.parseInt(request.getParameter("idActivo"));
                    } catch (Exception e) {
                        idActivo = 0;
                    }
                    try {
                        consultaEstado = Integer.parseInt(request.getParameter("consultaEstado"));
                    } catch (Exception e) {
                        consultaEstado = 1;
                    }
                    if (idActivo != 0) {
                        request.setAttribute("Activo", "Modificar_activo");
                    } else {
                        request.setAttribute("Activo", "Registar_activo");
                    }
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    busq = request.getParameter("fto");
                    fechas = request.getParameter("Txt_filtro_fecha");
                    campo = request.getParameter("Txt_filtro_campos");
                    request.setAttribute("idActivo", idActivo);
                    request.setAttribute("consultaEstado", consultaEstado);
                    query = class_filtro.Filtro_dinamico(fecha_i, fecha_f, busq, fechas, campo);
                    request.setAttribute("query", query);
                    request.getRequestDispatcher("Activo.jsp").forward(request, response);
                    break;
                //</editor-fold>
            }
        } catch (Exception e) {
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
