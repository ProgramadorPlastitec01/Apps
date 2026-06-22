package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            if (sesion.getAttribute("Documento") != null || sesion.getAttribute("Nombres") != null || sesion.getAttribute("NombreRol") != null) {
                int idUsuario = Integer.parseInt(pageContext.getSession().getAttribute("idUsuario").toString());
                String nombre = (String) sesion.getAttribute("Nombres");
                String rol = (String) sesion.getAttribute("NombreRol");
                out.print("<div class='container'>");
                out.print("<div class='menu' tabindex='1'>");
                out.print("<div id='templatemo_header'>");
                out.print("<br><br><b style='font-size:350%;'>ACTIVOS</b>"
                        + "<h1><b>" + rol.toUpperCase() + "/</b><b class='negro'>" + nombre.toUpperCase() + "</b>"
                        + "<div style='float:right; width:25px; margin-top:-21px; margin-right:25px; color: black;'><span onclick='CerrarSesion();' class='fas fa-running fa-size_small' title='Salir de la Activos'></span></div></h1>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style=\"float:right; margin-top:14px; margin-right:10px;\"><a  style='font-size: 12.2px;color: #fff;text-decoration: none;Font-weight: 700;outline: none;text-align: center;' href='#' onclick=\"ReestablecerPass('" + idUsuario + "');\">Restablecer contraseña</a></div>");
                out.print("<ul>");
                out.print("<li><a href='Inicio.jsp'>Inicio</a></li>");
                //<editor-fold defaultstate="collapsed" desc="COMPLEMENTARIOS">
                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    if (rol.equals("ADMINISTRADOR")) {
                        out.print("<li><a href='Usuario?opc=1&idUsuario=0'>Usuarios</a></li>");
                        out.print("<li><a href='Area?opc=1&idArea=0'>Áreas</a></li>");
                        out.print("<li><a href='Clasificacion?opc=1&idClasificacion=0'>Clasificación</a></li>");
                    }
                    out.print("<li><a href='Ubicacion?opc=1&idUbicacion=0'>Ubicaciones</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                }
//                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REQUISICION MATERIAL">
                out.print("<li><a href='#'>Requisicion Material</a>");
                out.print("<ul>");
                out.print("<li><a href='Requisicion?opc=10'>General</a></li>");
                out.print("<li><a href='Requisicion?opc=1&idRequisicion=0'>Solicitud</a></li>");
                out.print("<li><a href='Requisicion?opc=36'>Listado Maestro</a></li>");
                out.print("<li><a href='Requisicion?opc=39'>REQ Entregada</a></li>");
                out.print("<li><a href='Requisicion?opc=17'>REQ. Declinadas</a></li>");
                out.print("<li><a href='Requisicion?opc=21'>REQ. Devolución</a></li>");
                out.print("</ul>");
                out.print("</li>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVOS">
//                if (!rol.equals("CONSULTA")) {
                if (!rol.equals("AUXILIAR ALMACEN")) {
                    if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("SOLICITANTE") || rol.equals("CONSULTA")) {
                        out.print("<li><a href='#'>Procesos y Activos</a>");
                        out.print("<ul>");
                        out.print("<li><a href='Proceso?opc=1&idProceso=0'>Activos en Proceso</a></li>");
                        out.print("<li><a href='Proceso?opc=6&idProceso=0'>Procesos por Definir </a></li>");
                        out.print("<li><a href='Activo?opc=1&idActivo=0&query='>Inventario Maquinaria</a></li>");
                        out.print("</ul>");
                    }
                    out.print("</li>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REPORTES">
                out.print("<li><a href='#'>Reportes</a>");
                out.print("<ul>"
                        + "<li><a href='Requisicion?opc=33&query=&txt_arg_requisicion=&dias_vencidos=0'>Reporte de requisición </a></li>"
                        + "</ul>");
                out.print("</li>");
                //</editor-fold>
                out.print("<br style='clear: left' />");
                out.print("</div>");
                out.print("</div>");
            }
        } catch (Exception e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }

        return super.doStartTag();

    }
}
