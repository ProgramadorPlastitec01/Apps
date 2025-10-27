package Servlets;

import Controladoras.ListasVerificacionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Lst_verificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombreUsuario = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            ListasVerificacionJpaController jpa_lstVer = new ListasVerificacionJpaController();
            String nombre = "", descripcion = "", nombre_old = "", item = "", serial = "", ubicacion = "", nombreT = "", fecha = "", responsable = "", registro = "";
            int id_lstVer = 0, mod_equipo = 0, consecutivo = 0, id_tipo_verificacion = 0, id_verificacion = 0, id_Hverificacion = 0, idAdjunto = 0, id_area = 0, idMovimiento;
            String modulo = "", estado = "", filtro = "", equipo = "", cargo = "", fechaM = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="SEVLERT GENERAL">
                    modulo = request.getParameter("mod");
                    filtro = request.getParameter("txt_bus");
                    try {
                        id_lstVer = Integer.parseInt(request.getParameter("idLV"));
                    } catch (Exception e) {
                        id_lstVer = 0;
                    }
                    try {
                        id_verificacion = Integer.parseInt(request.getParameter("idVR"));
                    } catch (Exception e) {
                        id_verificacion = 0;
                    }
                    try {
                        id_Hverificacion = Integer.parseInt(request.getParameter("idHV"));
                    } catch (Exception e) {
                        id_Hverificacion = 0;
                    }
                    try {
                        idAdjunto = Integer.parseInt(request.getParameter("idAD"));
                    } catch (Exception e) {
                        idAdjunto = 0;
                    }
                    try {
                        idMovimiento = Integer.parseInt(request.getParameter("idM"));
                    } catch (Exception e) {
                        idMovimiento = 0;
                    }
                    if (modulo == null) {
                        modulo = "";
                    }
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("idMovimiento", idMovimiento);
                    request.setAttribute("idAdjunto", idAdjunto);
                    request.setAttribute("id_Hverificacion", id_Hverificacion);
                    request.setAttribute("id_lst_verificacion", id_lstVer);
                    request.setAttribute("id_verificacion", id_verificacion);
                    request.getRequestDispatcher("Lst_verificacion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO EQUIPO VERIFICACION">
                    nombreT = request.getParameter("txt_tipoE");
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_lstVer.registrarEquipoVerificacion(nombreT, descripcion, nombreUsuario);
                    request.setAttribute("Registro_lista", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=0&idLVR=0&mod=LV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR EQUIPO VERIFICACION">
                    id_tipo_verificacion = Integer.parseInt(request.getParameter("idTipoVE"));
                    nombreT = request.getParameter("txt_MtipoE");
                    descripcion = request.getParameter("txt_Mdescripcion");
                    resultado = jpa_lstVer.modificarListaVerificacion(id_tipo_verificacion, nombreT, descripcion);
                    request.setAttribute("Modificar_lista", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=0&idLVR=0&mod=LV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR DETALLE EQUIPO VERIFICACIÃ“N">
                    id_tipo_verificacion = Integer.parseInt(request.getParameter("idLV"));
                    consecutivo = Integer.parseInt(request.getParameter("consecutivo"));
                    nombre = request.getParameter("txt_nombre");
                    item = request.getParameter("txt_item");
                    serial = request.getParameter("txt_serial");
                    estado = request.getParameter("rd_estado");
                    responsable = request.getParameter("txt_responsable");
                    ubicacion = request.getParameter("txt_ubicacion");
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    fechaM = request.getParameter("txt_fechaM");
                    cargo = request.getParameter("txt_cargo");
                    resultado = jpa_lstVer.registrarEquipoDetalle(consecutivo, id_tipo_verificacion, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo, nombreUsuario);
                    id_lstVer = Integer.parseInt(request.getParameter("idLV"));
                    request.setAttribute("Registro_equipo", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=0&mod=LDV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DETALLE EQUIPO VERIFICACION">
                    id_lstVer = Integer.parseInt(request.getParameter("idLV"));
                    id_verificacion = Integer.parseInt(request.getParameter("idVRF"));
                    consecutivo = Integer.parseInt(request.getParameter("consecutivoM"));
                    nombre = request.getParameter("txt_nombreM");
                    item = request.getParameter("txt_itemM");
                    serial = request.getParameter("txt_serialM");
                    estado = request.getParameter("rd_estadoM");
                    responsable = request.getParameter("txt_responsableM");
                    ubicacion = request.getParameter("txt_ubicacionM");
                    id_area = Integer.parseInt(request.getParameter("slc_areaM"));
                    fechaM = request.getParameter("txt_fechaMM");
                    cargo = request.getParameter("txt_cargoM");
                    resultado = jpa_lstVer.modificarDetalleListaVerificacion(id_verificacion, consecutivo, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo);
                    jpa_lstVer.registrarLogEquipo(id_verificacion, id_lstVer, consecutivo, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo, nombreUsuario);
                    request.setAttribute("Modificar_equipo", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=0&mod=LDV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ANEXO HOJA DE VIDA">
                    id_verificacion = Integer.parseInt(request.getParameter("idVR"));
                    id_verificacion = Integer.parseInt(request.getParameter("idVR"));
                    registro = request.getParameter("slc_registro");
                    String[] campos = registro.split("//");
                    fecha = request.getParameter("txt_fechaA");
                    descripcion = request.getParameter("txt_descripcion");
                    resultado = jpa_lstVer.registrarAdjuntoHoja(id_verificacion,  registro, fecha, descripcion, nombreUsuario);
                    id_lstVer = Integer.parseInt(request.getParameter("idLV"));
                    request.setAttribute("Registrar_anexo", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idLVR=" + id_verificacion + "&idHV=0&mod=HVV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //</editor-fold//<editor-fold defaultstate="collapsed" desc="MODIFICAR ANEXO HOJA DE VIDA">
                    idAdjunto = Integer.parseInt(request.getParameter("idH"));
                    registro = request.getParameter("slc_registro");
                    fecha = request.getParameter("txt_fechaAM");
                    descripcion = request.getParameter("txt_descripcionM");
                    resultado = jpa_lstVer.modificarAjuntoHoja(idAdjunto, registro , fecha, descripcion);
                    id_lstVer = Integer.parseInt(request.getParameter("idLV"));
                    request.setAttribute("Modificar_anexo", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idLVR=" + id_verificacion + "&idHV=0&mod=HVV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR ADJUNTO">
                    idAdjunto = Integer.parseInt(request.getParameter("idAD"));
                    resultado = jpa_lstVer.EliminarAdjunto(idAdjunto);
                    request.setAttribute("Eliminar_anexo", resultado);
                    id_verificacion = Integer.parseInt(request.getParameter("idVR"));
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=&idLVR=" + id_verificacion + "&idHV=0&mod=HVV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR MOVIMIENTO">
                    id_verificacion = Integer.parseInt(request.getParameter("idVR"));
                    id_tipo_verificacion = Integer.parseInt(request.getParameter("idLV"));
                    consecutivo = Integer.parseInt(request.getParameter("consecutivo"));
                    nombre = request.getParameter("txt_nombre");
                    item = request.getParameter("txt_item");
                    serial = request.getParameter("txt_serial");
                    estado = request.getParameter("rd_estado");
                    responsable = request.getParameter("txt_responsable");
                    ubicacion = request.getParameter("txt_ubicacion");
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    fechaM = request.getParameter("txt_fechaM");
                    cargo = request.getParameter("txt_cargo");
                    resultado = jpa_lstVer.registrarEquipoMovimiento(id_verificacion, consecutivo, id_tipo_verificacion, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo, nombreUsuario);
                    if (resultado == true) {
                        jpa_lstVer.modificarDetalleListaVerificacion(id_verificacion, consecutivo, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo);
                    }
                    request.setAttribute("Registrar_movimiento", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=" + id_tipo_verificacion + "&idVR=" + id_verificacion + "&mod=MLV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR MOVIMIENTO">
                    idMovimiento = Integer.parseInt(request.getParameter("idM"));
                    id_verificacion = Integer.parseInt(request.getParameter("idVR"));
                    consecutivo = Integer.parseInt(request.getParameter("consecutivoM"));
                    nombre = request.getParameter("txt_nombreM");
                    item = request.getParameter("txt_itemM");
                    serial = request.getParameter("txt_serialM");
                    estado = request.getParameter("rd_estadoM");
                    responsable = request.getParameter("txt_responsableM");
                    ubicacion = request.getParameter("txt_ubicacionM");
                    id_area = Integer.parseInt(request.getParameter("slc_areaM"));
                    fechaM = request.getParameter("txt_fechaMM");
                    cargo = request.getParameter("txt_cargoM");
                    resultado = jpa_lstVer.modificarEquipoMovimiento(idMovimiento, id_verificacion, consecutivo, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo);
//                    jpa_lstVer.registrarLogEquipo(id_verificacion, id_lstVer, consecutivo, nombre, item, serial, estado, responsable, ubicacion, id_area, fechaM, cargo, nombreUsuario);
                    id_lstVer = Integer.parseInt(request.getParameter("idLV"));
                    request.setAttribute("Modificar_movimiento", resultado);
                    request.getRequestDispatcher("Lst_verificacion?opc=1&idLV=" + id_lstVer + "&idVR=" + id_verificacion + "&idM=0&mod=MLV&txt_bus=").forward(request, response);
                    break;
                //</editor-fold>
            }
        } catch (Exception ex) {
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
