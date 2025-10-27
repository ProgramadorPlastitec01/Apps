package Servlets;

import Controladoras.EquipoJpaController;
import Controladoras.DetalleEquipoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Detalle_equipo", urlPatterns = {"/Detalle_equipo"})
public class Detalle_equipo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            EquipoJpaController jpa_equipo = new EquipoJpaController();
            DetalleEquipoJpaController jpaDetalleE = new DetalleEquipoJpaController();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String mac = "", nombre_equipo = "", login_Plas = "", win_version = "", office_version = "", antivirus = "", tipo_estado = "", activos_soporte = "";
            String filtro = "", tipo_equipo = "", ip = "", garantia = "", internet = "", win_instalado = "", office_instalado = "";
            String vlan = "", vpn = "", skye = "", gmail = "", correo_interno = "", correo_externo = "", factura = "", fecha_factura = "";
            String licencia = "", fecha_garantia ="", proveedor = "",  tipo_software = "", red = "";
            String campo = "", busq = "", query = "";

            int id_equipo = 0, id_detalle = 0, id_detalleEC = 0, id_detalleEM = 0;

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="SEVELET PRINCIPAL">
                    try {
                        id_detalle = Integer.parseInt(request.getParameter("idDT"));
                    } catch (Exception e) {
                        id_detalle = 0;
                    }
                    try {
                        id_detalleEC = Integer.parseInt(request.getParameter("idDTEC"));
                    } catch (Exception e) {
                        id_detalleEC = 0;
                    }
                    try {
                        id_detalleEM = Integer.parseInt(request.getParameter("idDTEM"));
                    } catch (Exception e) {
                        id_detalleEM = 0;
                    }
                    try {
                        query = request.getParameter("query").toString();
                    } catch (Exception e) {
                        query = "";
                    }
                    try {
                        filtro = request.getParameter("txt_bus");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    request.setAttribute("query", query);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_detalle", id_detalle);
                    request.setAttribute("id_detalleEC", id_detalleEC);
                    request.setAttribute("id_detalleEM", id_detalleEM);
                    request.getRequestDispatcher("Detalle_equipo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DETALLE">
                    id_equipo = Integer.parseInt(request.getParameter("slc_equipo"));
                    nombre_equipo = request.getParameter("txt_nombre_eq");
                    login_Plas = request.getParameter("txt_loginp");
                    mac = request.getParameter("txt_mac");
                    antivirus = request.getParameter("txt_antivirus");
                    win_version = request.getParameter("txt_wversion");
                    office_version = request.getParameter("txt_officeV");
                    tipo_estado = request.getParameter("txt_testado");
                    activos_soporte = request.getParameter("txt_activos_soporte");
                    resultado = jpaDetalleE.registroDetalleEquipo(id_equipo, nombre_equipo, login_Plas, mac, antivirus, win_version, office_version, tipo_estado, activos_soporte, nombre);
                    request.setAttribute("RegistroDetalle", resultado);
                    request.getRequestDispatcher("Detalle_equipo?opc=1&txt_bus=&idDT=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DETALLE">
                    id_detalle = Integer.parseInt(request.getParameter("idDT"));
                    nombre_equipo = request.getParameter("Txt_nombre_equipo");
                    tipo_estado = request.getParameter("Txt_tipo_estado");
                    login_Plas = request.getParameter("Txt_login_plastitec");
                    ip = request.getParameter("Txt_ip");
                    garantia = request.getParameter("Txt_garantia");
                    mac = request.getParameter("Txt_mac");
                    antivirus = request.getParameter("Txt_antivirus");
                    internet = request.getParameter("Txt_internet");
                    win_instalado = request.getParameter("Txt_win_instalado");
                    office_instalado = request.getParameter("Txt_office_instalado");
                    vlan = request.getParameter("Txt_vlan");
                    vpn = request.getParameter("Txt_vpn");
                    skye = request.getParameter("Txt_skye");
                    gmail = request.getParameter("Txt_gmail");
                    correo_interno = request.getParameter("Txt_correo_interno");
                    correo_externo = request.getParameter("Txt_correo_externo");
                    factura = request.getParameter("Txt_factura");
                    fecha_factura = request.getParameter("Txt_fecha_factura");
                    licencia = request.getParameter("Txt_lincecia");
                    fecha_garantia = request.getParameter("Txt_fecha_garantia");
                    proveedor = request.getParameter("Txt_proveedor");
                    activos_soporte = request.getParameter("Txt_activos_soporte");
                    tipo_software = request.getParameter("Txt_tipo_sofware");
                    red = request.getParameter("Txt_red");
                    resultado = jpaDetalleE.ModificarDetalleEquipo(id_detalle, nombre_equipo, login_Plas, ip, mac, red, vlan, win_instalado, office_instalado,antivirus,internet,vpn,skye,gmail,correo_interno,correo_externo,factura,fecha_factura,licencia,fecha_garantia,proveedor,garantia,activos_soporte,tipo_software,tipo_estado);
                    request.setAttribute("RegistroDetalle", resultado);
                    request.getRequestDispatcher("Detalle_equipo?opc=1&txt_bus=&idDT=0").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO ESPECIFICO">
                    try {
                        id_detalle = Integer.parseInt(request.getParameter("idDT"));
                    } catch (Exception e) {
                        id_detalle = 0;
                    }
                    try {
                        filtro = request.getParameter("txt_bus");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    tipo_estado = request.getParameter("txt_testado");
                    busq = request.getParameter("fto");
                    campo = request.getParameter("Txt_filtro_campos");
                    query = jpaDetalleE.Filtro_dinamico(tipo_estado, busq, campo);
                    request.setAttribute("query", query);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_detalle", id_detalle);
                    request.getRequestDispatcher("Detalle_equipo.jsp").forward(request, response);
                    break;
                //</editor-fold>
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Detalle_equipo.jsp").forward(request, response);
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
