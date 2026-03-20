package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        try {
            if (sesion.getAttribute("Nombre") != null || sesion.getAttribute("Rol") != null) {
                String nombre = sesion.getAttribute("Nombre").toString();
                int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
                String rol = sesion.getAttribute("Rol").toString();
                int id_rol = Integer.parseInt(sesion.getAttribute("id_rol").toString());
                // <editor-fold defaultstate="collapsed"  desc="cerrar session.">
                out.print("<div id='templatemo_header'>");
                out.print("<br><br><b style='font-size:250%; color:black;'>CONTROL</b>&nbsp;&nbsp;<b style='font-size:250%; color:#00838f;'>GRAFADO</b><h1><a href='#'  >"
                        + "<b style='font-size: small;'>" + rol + "/</b><b class='negro' style='font-size: small;'>" + nombre.toUpperCase() + "</b></a></h1>");
                out.print("<div style='float:right; width:25px; margin-top:-40px; margin-right:25px;'><span class='fas fa-running fa-size_normal' onclick='CerrarSesion();'></span></div>");
                out.print("</div>");
                // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Menù">
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style='float:right;margin-top: 14px;margin-right: 10px;'>");
                out.print("<center><a href='Usuario?opc=5&idU=" + id_usuario + "'><b style='color:#fff'>Restablecer contraseña</b></a></center>");
                out.print("</div>");
                if (id_rol == 1) {
                    out.print("<ul>");
                    out.print("<li><a href='Inicio.jsp'>Inicio</a>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Usuario?opc=1&idU=" + 0 + "&txt_bus='>Usuario</a></li>");
                    out.print("<li><a href='Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus='>Datos de control</a>");
                    out.print("<li><a href='Defecto?opc=1&idD=" + 0 + "&txt_bus='>Defecto</a></li>");
                    out.print("<li><a href='Maquina?opc=1&idM=" + 0 + "&txt_bus='>Máquina</a></li>");
                    out.print("<li><a href='Nota?opc=1&idN=" + 0 + "&txt_bus='>Nota</a></li>");
                    out.print("</ul>");
                    out.print("<li><a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='>Orden de producción</a>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Resumen?opc=1&idR=0&txt_orden='>R-GC-132</a></li>");
                    out.print("<li><a href='Resumen?opc=4&idR=0'>Resumidos</a></li>");
                    out.print("<li><a href='Resumen?opc=6&txt_orden=&slt_lote='>Formulación</a></li>");
                    out.print("<li><a href='Resumen?opc=7&txt_orden=&idO=0'>Frecuencia</a></li>");
                    out.print("<li><a href='Resumen?opc=8&idF=0'>Premuestras</a></li>");
                    out.print("</ul>");
                    out.print("</ul>");
                } else if (id_rol == 2) {
                    out.print("<ul>");
                    out.print("<li><a href='Inicio.jsp'>Inicio</a>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus='>Datos de control</a>");
                    out.print("<li><a href='Defecto?opc=1&idD=" + 0 + "&txt_bus='>Defecto</a></li>");
                    out.print("<li><a href='Nota?opc=1&idN=" + 0 + "&txt_bus='>Nota</a></li>");
                    out.print("</ul>");
                    out.print("<li><a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='>Orden de producción</a>");
                    out.print("</ul>");
                } else if (id_rol == 3) {
                    out.print("<ul>");
                    out.print("<li><a href='Inicio.jsp'>Inicio</a>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Nota?opc=1&idN=" + 0 + "&txt_bus='>Nota</a></li>");
                    out.print("</ul>");
                    out.print("<li><a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='>Orden de producción</a>");
                    out.print("</ul>");
                } else if (id_rol == 4) {
                    out.print("<ul>");
                    out.print("<li><a href='Inicio.jsp'>Inicio</a>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Ficha_tecnica?opc=1&idF=" + 0 + "&txt_bus='>Datos de control</a>");
                    out.print("</ul>");
                    out.print("<li><a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='>Orden de producción</a>");
                    out.print("<li><a href='#'>Reportes</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Resumen?opc=1&idR=0&txt_orden='>R-GC-132</a></li>");
                    out.print("<li><a href='Resumen?opc=4&idR=0'>Resumidos</a></li>");
                    out.print("<li><a href='Resumen?opc=6&txt_orden=&slt_lote='>Formulación</a></li>");
                    out.print("<li><a href='Resumen?opc=7&txt_orden=&idO=0'>Frecuencia</a></li>");
                    out.print("<li><a href='Resumen?opc=8&idF=0'>Premuestras</a></li>");
                    out.print("</ul>");
                    out.print("</ul>");
                } else if (id_rol == 5  || id_rol == 6) {
                    out.print("<ul>");
                    out.print("<li><a href='Inicio.jsp'>Inicio</a>");
                    out.print("<li><a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='>Orden de producción</a>");
                    out.print("</ul>");
                }
                //</editor-fold>
                out.print("<div class='cleaner'></div></div>");
            } else {
                try {
                    pageContext.getRequest().getRequestDispatcher("index.jsp").forward(pageContext.getRequest(), pageContext.getResponse());
                } catch (ServletException ex) {
                    Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
