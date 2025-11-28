package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (pageContext.getSession().getAttribute("Menu") != null) {
                int menu = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
                String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                String Codigo = pageContext.getSession().getAttribute("Codigo").toString();
                out.print("<ul class=\"navbar-nav bg-gradient-menu sidebar sidebar-dark accordion\" id=\"accordionSidebar\">");
                //<editor-fold defaultstate="collapsed" desc="Logo">
                out.print("<a class=\"sidebar-brand d-flex align-items-center justify-content-center\" >");
                out.print("<div class=\"sidebar-brand-icon rotate-n-15\">");
                out.print("<i class=\"fas fa-tools\"></i>");
                out.print("</div>");
                out.print("<div class=\"sidebar-brand-text mx-3\">PMP <sup>MTF</sup></div>");
                out.print("</a>");
                out.print("<hr class=\"sidebar-divider my-0\">");
//</editor-fold>
                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Asistente") || nombre_rol.equals("Coordinador")) {
                    //<editor-fold defaultstate="collapsed" desc="Dashboard">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapseFive\" aria-expanded=\"true\" aria-controls=\"collapseTree\">");
                    out.print("<i class=\"far fa-fw fa-calendar-alt\"></i>");
                    out.print("<span>Cronograma</span></a>");
                    out.print("</a>");
                    out.print("<div id=\"collapseFive\" class=\"collapse\" aria-labelledby=\"headingTwo\" data-parent=\"#accordionSidebar\">");
                    out.print("<div class=\"bg-white py-2 collapse-inner rounded\">");
                    out.print("<a class=\"collapse-item\" href=\"Reportes?opc=1\">Maestro</a>");
                    out.print("<a class=\"collapse-item\" href=\"Reportes?opc=7\">Actividad general</a>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</li>");
//</editor-fold>
                    out.print("<hr class=\"sidebar-divider\">");
                    out.print("<div class=\"sidebar-heading\">Parametrización</div>");
                    //<editor-fold defaultstate="collapsed" desc="Menu Complementos">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapseTwo\" aria-expanded=\"true\" aria-controls=\"collapseTwo\">");
                    out.print("<i class=\"fas fa-fw fa-cog\"></i>");
                    out.print("<span>Complementos</span>");
                    out.print("</a>");
                    out.print("<div id=\"collapseTwo\" class=\"collapse\" aria-labelledby=\"headingTwo\" data-parent=\"#accordionSidebar\">");
                    out.print("<div class=\"bg-white py-2 collapse-inner rounded\">");
                    if (nombre_rol.equals("Administrador")) {
                        out.print("<a class=\"collapse-item\" href=\"Complementos?opc=1\">Correo</a>");
                    }
                    out.print("<a class=\"collapse-item\" href=\"Complementos?opc=13\">Zonas</a>");
                    out.print("<a class=\"collapse-item\" href=\"Complementos?opc=19\">Actividades Generales</a>");
                    out.print("<a class=\"collapse-item\" href=\"Complementos?opc=10\">Lineas</a>");
                    out.print("<a class=\"collapse-item\" href=\"Complementos?opc=4\">Tipo de equipos</a>");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        out.print("<a class=\"collapse-item\" href=\"Complementos?opc=25\">Instrucciones de Seguridad</a>");
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        out.print("<a class=\"collapse-item\" href=\"Complementos?opc=27\">Registros</a>");
                    }
                    if (Codigo.equals("385") || Codigo.equals("121") || Codigo.equals("13") || Codigo.equals("4039")) {
                        out.print("<a class=\"collapse-item\" href=\"Complementos?opc=30\">Cambiar rol</a>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</li>");
                    //</editor-fold>
                }
                if (nombre_rol.equals("Administrador")) {
                    //<editor-fold defaultstate="collapsed" desc="Menu Usuarios">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link\" href=\"Usuario?opc=1\">");
                    out.print("<i class=\"fa fa-fw fa-user\"></i>");
                    out.print("<span>Usuarios</span></a>");
                    out.print("</li>");
//</editor-fold>
                }
                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Asistente") || nombre_rol.equals("Coordinador")) {
                    out.print("<hr class=\"sidebar-divider\">");
                    out.print("<div class=\"sidebar-heading\">Menu</div>");
                    //<editor-fold defaultstate="collapsed" desc="Menu Equipos">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link\" href=\"Equipo?opc=1\">");
                    out.print("<i class=\"fas fa-cogs\"></i>");
//                out.print("<i class=\"fas fa-chalkboard-teacher\"></i>");
                    out.print("<span>Equipos PMP OT</span></a>");
                    out.print("</li>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Menu Orden">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapseTree\" aria-expanded=\"true\" aria-controls=\"collapseTree\">");
                    out.print("<i class=\"fas fa-file-signature\"></i>");
                    out.print("<span>Ordenes de trabajo</span>");
                    out.print("</a>");
                    out.print("<div id=\"collapseTree\" class=\"collapse\" aria-labelledby=\"headingTwo\" data-parent=\"#accordionSidebar\">");
                    out.print("<div class=\"bg-white py-2 collapse-inner rounded\">");
                    out.print("<a class=\"collapse-item\" href=\"Orden?opc=8&Id_tipo_mtto=3&Id_origen=0\">OT Zonas</a>");
                    out.print("<a class=\"collapse-item\" href=\"Orden?opc=8&Id_tipo_mtto=4&Id_origen=0\">OT General</a>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</li>");
//</editor-fold>
                }
                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Asistente") || nombre_rol.equals("Coordinador") || nombre_rol.equals("Inspectora_Calidad") || nombre_rol.equals("Coordinadora_Calidad") ) {
                    //<editor-fold defaultstate="collapsed" desc="Menu Reto">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link\" href=\"Reto?opc=1\">");
                    out.print("<i class=\"fas fa-file-alt\"></i>");
//                out.print("<i class=\"fas fa-chalkboard-teacher\"></i>");
                    out.print("<span>Reto Inspección</span></a>");
                    out.print("</li>");
                    //</editor-fold>
                }
                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Asistente") || nombre_rol.equals("Coordinador")) {
                    out.print("<hr class=\"sidebar-divider\">");
                    out.print("<div class=\"sidebar-heading\">Reporte</div>");
                    //<editor-fold defaultstate="collapsed" desc="Menu Reportes">
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapseFour\" aria-expanded=\"true\" aria-controls=\"collapseTree\">");
                    out.print("<i class=\"fas fa-chart-bar\"></i>");
                    out.print("<span>Reportes</span>");
                    out.print("</a>");
                    out.print("<div id=\"collapseFour\" class=\"collapse\" aria-labelledby=\"headingTwo\" data-parent=\"#accordionSidebar\">");
                    out.print("<div class=\"bg-white py-2 collapse-inner rounded\">");
//                out.print("<a class=\"collapse-item\" href=\"buttons.html\">Listado Maestro</a>");
                    out.print("<a class=\"collapse-item\" href='Reportes?opc=2&vlr=&fin=&ffn='>Indicador</a>");
                    out.print("<a class=\"collapse-item\" href='Reportes?opc=6&fcha='>Reporte Proximas OT</a>");
                    out.print("<a class=\"collapse-item\" href='Devolucion?opc=1'>Devolucion</a>");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        out.print("<a class=\"collapse-item\" href='Reportes?opc=3&uss=" + nombre_usuario + "'>Correo Semanal</a>");
                    }
                    out.print("<a class=\"collapse-item\" href='Reportes?opc=9'>Notas</a>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</li>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Replegar menu">
                    out.print("<hr class=\"sidebar-divider d-none d-md-block\">");
                    out.print("<div class=\"text-center d-none d-md-inline\">");
                    out.print("<button class=\"rounded-circle border-0\" id=\"sidebarToggle\"></button>");
                    out.print("</div>");
//</editor-fold>
                }
                out.print("</ul>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
