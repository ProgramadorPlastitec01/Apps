package Tags;

import Controladores_BD.MenuJpaController;
import Controladores_BD.PersonalJpaController;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
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
            MenuJpaController jpacmnu = new MenuJpaController();
            PersonalJpaController jpacpsn = new PersonalJpaController();
            String consulta = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            int id_opcion_menu = cal.get(Calendar.YEAR);
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            //<editor-fold defaultstate="collapsed" desc="MENU">
            if (pageContext.getSession().getAttribute("Menu") != null) {
                int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
                if (pageContext.getRequest().getAttribute("Permisos") != null) {
                    id_opcion_menu = Integer.parseInt(pageContext.getRequest().getAttribute("Permisos").toString());
                } else {
                    id_opcion_menu = 0;
                }
                String rol = pageContext.getSession().getAttribute("Rol").toString();
                List lst_menu = null;
                List lst_persona = null;
                List lst_opciones = null;
                out.print("<div id=\"logo\" class=\"container\">");
                out.print("<table style='width:100%'>"
                        + "<tr>"
                        + "<td rowspan='2'><h1><span class=\"fab fa-hornbill fa-size_normal\"></span></h1></td>"
                        + "<td><h1>SIRH</h1></td>"
                        + "<td>Sistema de información Recursos Humanos<br />"
                        + "<b>" + pageContext.getSession().getAttribute("Rol") + "</b> / "
                        + "<b class='negro'>" + pageContext.getSession().getAttribute("Nombre_apellido") + "</b></td>"
                        + "<td valign='top' align='right' style='width:50%;'>");
                try {
                    consulta = pageContext.getSession().getAttribute("Consulta").toString();
                    if ("".equals(consulta)) {
                    } else {
                        String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                        out.print("<div style='height:120px;overflow:scroll;'><table class='table' style='width:70%'>");
                        for (int i = 0; i < arg_consulta.length; i++) {
                            lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                            Object[] obj_persona = (Object[]) lst_persona.get(0);
                            out.print("<tr>"
                                    + "<td align='center'><b>" + (i + 1) + "</b></td>"
                                    + "<td align='center'>" + obj_persona[0] + "</td>"
                                    + "<td>" + obj_persona[2] + " " + obj_persona[1] + "</td>"
                                    + "<td align='center'><a style='text-decoration:none;' href='Personal?opc=6&Txt_documento=" + obj_persona[0] + "'><span class='fa fa-times fa-size_super_small'></span></a></td>"
                                    + "</tr>");
                        }
                        out.print("</table></div></td>");
                    }
                } catch (Exception e) {
                    out.print("</td>");
                }
                out.print("</tr></table>");
                out.print("</div>");
                out.print("<div class='wrap'><nav style='border-radius:20px;" + ((id_opcion_menu == 5) ? "z-index:12000" : "") + "'>");
                out.print("<ul class='menu'>");
                if (rol.equals("ADMINISTRADOR")) {
                    lst_menu = jpacmnu.Menu_todo();
                    for (int i = 0; i < lst_menu.size(); i++) {
                        Object[] obj_menu = (Object[]) lst_menu.get(i);
                        lst_opciones = jpacmnu.Opciones_todas(Integer.parseInt(obj_menu[0].toString()));
                        if (lst_opciones == null) {
                            out.print("<li>");
                            out.print("" + obj_menu[2].toString());
                            out.print("</li>");
                        } else {
                            out.print("<li>");
                            out.print("" + obj_menu[2].toString());
                            out.print("<ul>");
                            for (int j = 0; j < lst_opciones.size(); j++) {
                                Object[] obj_opciones = (Object[]) lst_opciones.get(j);
                                out.print("" + obj_opciones[2].toString());
                            }
                            out.print("</ul>");
                            out.print("</li>");
                        }
                    }
                } else {
                    lst_menu = jpacmnu.Menu_usuario(menu);
                    if (lst_menu != null) {
                        for (int i = 0; i < lst_menu.size(); i++) {
                            Object[] obj_menu = (Object[]) lst_menu.get(i);
                            lst_opciones = jpacmnu.Opciones_usuario(Integer.parseInt(obj_menu[0].toString()), menu);
                            if (lst_opciones == null) {
                                out.print("<li>");
                                out.print("" + obj_menu[2].toString());
                                out.print("</li>");
                            } else {
                                out.print("<li>");
                                out.print("" + obj_menu[2].toString());
                                out.print("<ul>");
                                for (int j = 0; j < lst_opciones.size(); j++) {
                                    Object[] obj_opciones = (Object[]) lst_opciones.get(j);
                                    out.print("" + obj_opciones[2].toString());
                                }
                                out.print("</ul>");
                                out.print("</li>");
                            }
                        }
                    }
                }
                out.print("</ul>");
                out.print("<div class='clearfix'></div>");
                out.print("</nav>");
                out.print("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="AJUSTES">
            if (pageContext.getRequest().getAttribute("Ajustes") != null) {
                if (pageContext.getRequest().getAttribute("Ajustes").toString().equals("Fecha_proceso")) {
                    //<editor-fold defaultstate="collapsed" desc="FECHA PROCESO">
                    fecha_inicio = pageContext.getRequest().getAttribute("FechaP_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("FechaP_fin").toString();
//                    out.print("<div id='content_sin'>");
                    out.print("<div class='sweet-local' tabindex='-1' id='Popup_fecha_proceso' style='opacity: 1.03; display: block;margin-left:10px'>");
                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:35%;position: absolute;top: 25%;left:25%;'>");
                    out.print("<div style='float:right;'><a href='Inicio?opc=2&mnu=5'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                    out.print("<h3>Fecha de proceso</h3>");
                    out.print("Ingresar rango de fechas para ajustar las consultas de <b>SIRH</b>.<br /><br />");
                    out.print("<form action='Sesion?opc=5' method='post' name='Foto'>");
                    out.print("<div style='width:50%;float:left'>");
                    out.print("<b>Fecha Inicio :</b><br /><input type='text' name='fpi' id='start' autocomplete='off' value='" + fecha_inicio + "'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                    out.print("<br /><b>Fecha Fin :</b><br /><input type='text' name='fpf' id='end' autocomplete='off' value='" + fecha_fin + "' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:49%;float:left'><a href='Sesion?opc=5&fpi=" + (anio + "-" + mes + "-01") + "&fpf=" + (anio + "-" + mes + "-" + dia) + "'><i><b>Ajustar a mes actual  </b></i><span class='fa fa-calendar-day fa-size_super_small'></span></a><br /><input type='submit' value='Ajustar' /></div>");
                    out.print("Actualmente esta ajustado a " + fecha_inicio + " hasta " + fecha_fin + ".");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
//                    out.print("</div>");
//</editor-fold>
                }
            }
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
