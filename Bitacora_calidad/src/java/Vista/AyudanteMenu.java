package Vista;

import Controlador.NotasJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class AyudanteMenu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            List var = null;
            List info = null;
            if (sesion.getAttribute("identificacion") != null || sesion.getAttribute("nombre") != null || sesion.getAttribute("rol") != null) {
                int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("identificacion").toString());
                //      int  = (String) sesion.getAttribute("identificacion");
                String nombre = (String) sesion.getAttribute("nombre");
                String rol = (String) sesion.getAttribute("rol");
                // <editor-fold defaultstate="collapsed"  desc="1. CERRAR SESSION.">
                out.print("<div id='templatemo_header'>");
                out.print("<div style='float:right'><img src='Interfaz/Contenido/images/Bitacora.png' alt='logo' width='85px'/></div>");
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();' ><b>" + rol.toUpperCase() + "/</b><b class='negro'>" + nombre.toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
// </editor-fold>
                // <editor-fold defaultstate="collapsed"  desc="2. MENU">
                out.print("<div style='float:right; margin-top:14px; margin-right:10px;'>");
                out.print("<form action='Usuarios?l=6&idUsuario=" + id_usuario + "' method='post' name='formRC'>");
                out.print("<input type='hidden' name='txt_passM' id='pass-id' value=''>");
                out.print("<center><a href='#' onclick='contrasena()'><b style='color:#fff'>Restablecer contraseña</b></a></center>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<script language='Javascript'>"
                        + "function mostrar() {"
                        + "var panel, mostrarr ;var pagina =''; panel = document.getElementById('Notificaciones');"
                        + "if(panel.style.visibility == 'hidden') {"
                        + "panel.style.visibility = 'visible';"
                        + "mostrarr = document.getElementById('mostrar').childNodes[i];"
                        + "document.getElementById('cambiar').src='Interfaz/Contenido/Iconos/Min.png';"
                        + "document.getElementById('cambiar').title = 'Cancelar';"
                        + "}else {"
                        + "panel.style.visibility = 'hidden';"
                        + "mostrarr = document.getElementById('mostrar').childNodes[0];"
                        + "document.getElementById('cambiar').src = 'Interfaz/Contenido/Iconos/Plus.png';location.href = pagina;}}</script>");
                out.print("<ul>");
                NotasJpaController objnotas = new NotasJpaController();
                var = objnotas.consultaCountNotasCalidad();
                info = objnotas.consultaCountInfoNotasCalidad();
                Object[] cantidad = (Object[]) var.get(0);
                out.print("<li><a href='R_GC_079?opc=1&Id_Actividad=0&txt_bus='>R-GC-079</a></li>");
                out.print("<li><a href='Notas?op=1'>Notas</a></li>");
                out.print("<li><a href='Novedades?opc=4&txt_ubicacion=0&Accion=Consulta'>Novedad de maquina</a></li>");
                if (!rol.equals("Inspector_calidad")) {
                    out.print("<li><a href='#'>Complementos</a>");
                }
                out.print("<ul>");
                if (!rol.equals("Inspector_calidad")) {
                    if (rol.equals("Administrador")) {
                        out.print("<li><a href='Usuarios?l=1'>Usuarios</a></li>");
                        out.print("<li><a href='Ubicacion?lc=5'>Ubicaciónes</a></li>");
                    }
                    if (rol.equals("Coordinador_calidad") || rol.equals("Administrador") || rol.equals("Coordinador_sgc") || rol.equals("Directora_calidad")) {
                        out.print("<li><a href='Maquinaria?op=1'>Maquinas</a></li>");
                    }
                }
                out.print("</ul>");
                out.print("</li>");
                out.print("<div class='contenedor'>");
                out.print("<li><a href='javascript:mostrar();'>Notificacion<span class='burbuja'>" + cantidad[0] + "</span></a></li>");
                if (Integer.parseInt(cantidad[0].toString()) != 0) {
                    out.print("<fieldset class='resalta_field' id='Notificaciones' style='width: 400px; visibility: hidden; position: absolute; top: 158px; left: 35%;'>");
                    out.print("<table class='table' style='width:100%;'>");
                    for (int i = 0; i < info.size(); i++) {
                        Object[] obj_info = (Object[]) info.get(i);
                        out.print("<tr>");
                        out.print("<td rowspan='3' align='center'><b>Fecha : </b> " + obj_info[1] + "<br /><a href='Notas?op=4&Id_nota=" + obj_info[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='Logo' width='20' height='20.5' title='Ver' /></a></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>Responsable : </b> " + obj_info[4] + " " + obj_info[5] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>Asunto : </b> " + obj_info[2] + "</td>");
                        out.print("</tr>");
                        out.print("<tr><td colspan='3'></td></tr>");
                    }
                    out.print("</table>");
                    out.print("</fieldset>");
                } else {
                    out.print("<fieldset class='resalta_field' id='Notificaciones' style='width: 400px; visibility: hidden; position: absolute; top: 158px; left: 35%;'>");
                    out.print("<table class='table' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<td style='border-radius:20px;'><b>No se han encontrado Notificaciones</b></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</fieldset>");
                }
                out.print("</div>");
                out.print("</div>");
                // </editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(AyudanteMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
