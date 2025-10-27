package Tags;

import Controladoras.CargoJpaController;
import Controladoras.NotasJpaController;
import java.io.IOException;
import java.util.List;
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
        List var = null;
        String nombre = sesion.getAttribute("Nombre").toString();
        String rol = sesion.getAttribute("Rol").toString();
        int idUsuario = Integer.parseInt(sesion.getAttribute("Identificacion").toString());
        int idArea = Integer.parseInt(sesion.getAttribute("Area").toString());
        int idCargo = Integer.parseInt(sesion.getAttribute("Cargo").toString());
        NotasJpaController jpa_notas = new NotasJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        var = jpa_notas.ContadorNotasPorIdArea(idArea);
        Object[] cantidad = (Object[]) var.get(0);
        List info = null;
        info = jpa_notas.ConsultaNotasPorIdArea(idArea);
        int cont = 0;
        List permisos = null;
        permisos = jpa_cargo.ConsultaCargosPorId(idCargo);
        Object[] obj_permisos = (Object[]) permisos.get(0);
        try {
            if (sesion.getAttribute("Nombre") != null || sesion.getAttribute("Rol") != null) {
                // <editor-fold defaultstate="collapsed"  desc="cerrar session.">
                out.print("<div id='templatemo_header'>");
                //out.print("<div id='site_title'><b style='font-size:500%; color:#601c03;'>Bitacora</b><b style='font-size:500%; color:black;'>General</b></b><h1><a href='#' onclick='CerrarSesion();' ><b>" + rol + "/</b><b class='negro'>" + nombre.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("<div id='site_title'><b style='font-size:500%; color:#601c03;'>Bitacora</b><b style='font-size:500%; color:black;'>General</b>"
                        + "<div style='display: flex; justify-content: space-between'><div><h1><b>" + rol + "/</b><b class='negro'>" + nombre.toString().toUpperCase() + "</b></h1></div><div><span style='font-size: 26px; cursor:pointer;' onclick='CerrarSesion();' class='fas fa-running' title='Salir de Bitacora'></span></div></div>"
                        + "</div>");
                out.print("</div>");
                // </editor-fold>
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<div style='float:right;margin-top: 14px;margin-right: 10px;'>");
                out.print("<form action='Usuario?op=5&idU=" + idUsuario + "&validacion=0' method='post' name='formRC' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_passM'  id='pass-id' value=''>");
                out.print("<span onclick='contrasena()'><b style='color:#fff;cursor:pointer;'>Restablecer contraseña</b></span>");
                out.print("</form>");
                out.print("</div>");
                out.print("<script language='Javascript'>"
                        + "function mostrarN() {"
                        + "var panel, mostrarMK ;var pagina =''; panel = document.getElementById('Notificaciones');"
                        + "if(panel.style.visibility == 'hidden') {"
                        + "panel.style.visibility = 'visible';"
                        + "mostrarMK = document.getElementById('mostrarrr').childNodes[i];"
                        + "}else {"
                        + "panel.style.visibility = 'hidden';"
                        + "mostrarMK = document.getElementById('mostrarrr').childNodes[0];"
                        + "location.href = pagina;}}</script>");
                out.print("<ul>");
                if (obj_permisos[12].equals(1)) {
                    if (obj_permisos[1].equals(8) || obj_permisos[1].equals(1)) {
                        out.print("<li><a href='#'>Registro</a>");
                        out.print("<ul>");
                        out.print("<li><a href='Actividad?op=1&idC=" + 0 + "&idA=" + 0 + "&txt_bus='>Actividad</a></li>");
                        out.print("<li><a href='Registro?op=1'>Registro 011</a></li>");
                        out.print("</ul>");
                        out.print("</li>");
                    } else {
                        out.print("<li><a href='Actividad?op=1&idC=" + 0 + "&idA=" + 0 + "&txt_bus='>Actividad</a></li>");
                    }

                } else {
                    if (obj_permisos[1].equals(8) || obj_permisos[1].equals(1)) {
                        out.print("<li><a href='#'>Registro</a>");
                        out.print("<ul>");
                        out.print("<li><a href='Actividad?op=1&idC=" + idCargo + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus='>Actividad</a></li>");
                        if (obj_permisos[0].equals(29) || obj_permisos[0].equals(28) || obj_permisos[0].equals(27) || obj_permisos[0].equals(25)) {
                            out.print("<li><a href='Registro?op=1'>Registro 011</a></li>");
                        }
                        out.print("</ul>");
                        out.print("</li>");
                    } else {
                        out.print("<li><a href='Actividad?op=1&idC=" + idCargo + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus='>Actividad</a></li>");
                    }
                }
                out.print("<li><a href='novedad_Maquina.jsp'>Novedad de Maquina</a></li>");
                if (rol.equals("ADMINISTRADOR")) {
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Area?op=1&idAra=" + 0 + "&txt_bus='>Area</a></li>");
                    out.print("<li><a href='Cargo?op=3&idC=" + 0 + "&txt_bus='>Cargo</a></li>");
                    out.print("<li><a href='Maquinas?op=1&idM=" + 0 + "&txt_bus='>Maquinas</a></li>");
                    out.print("<li><a href='Usuario?op=1&idU=" + 0 + "&txt_bus=&idC=" + 0 + "'>Usuario</a></li>");
                    out.print("<li><a href='Ubicacion?op=1&idU=" + 0 + "'>Ubicación</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                }
                out.print("<li><a href='Nota?op=1&idN=" + 0 + "&txt_bus='>Notas</a></li>");
                // <editor-fold defaultstate="collapsed"  desc="contador burbuja notificacion">
                cont = Integer.parseInt(cantidad[0].toString());
                String idu = String.valueOf(idUsuario);
                for (int a = 0; a < info.size(); a++) {
                    Object[] obj_infor = (Object[]) info.get(a);
                    if (obj_infor[6] == null) {
                    } else {
                        String[] arg_datos = obj_infor[6].toString().split("-");
                        for (int j = 0; j < arg_datos.length; j++) {
                            String y = arg_datos[j];
                            if (y.equals(idu)) {
                                cont = cont - 1;
                            }
                        }

                    }
                }
// </editor-fold>
                out.print("<div class='contenedor'>");
                out.print("<li><a href='javascript:mostrarN();'>Notificacion<span class='burbuja' id='not'>" + cont + "</span></a></li>");
                // <editor-fold defaultstate="collapsed"  desc="notificaciones">
                if (Integer.parseInt(cantidad[0].toString()) != 0) {
                    out.print("<fieldset class='resalta_field' id='Notificaciones' style='width: 340px; visibility: hidden; position: absolute; top: 158px; left: 32%; height: 381px; overflow: overlay;'>");
                    String idU = String.valueOf(idUsuario);
                    for (int i = 0; i < info.size(); i++) {
                        Object[] obj_info = (Object[]) info.get(i);
                        if (obj_info[6] == null) {
                            out.print("<a href='Nota?op=4&idN=" + obj_info[0] + "&idU=" + idUsuario + "'>");
                            out.print("<table class='tablaN' style='width:100%;'>");
                            out.print("<tr>");
                            out.print("<td  align='center'>" + obj_info[3] + "<br /></td>");
                            out.print("<td><b>Asunto : </b> " + obj_info[4] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2'><b>Responsable : </b> " + obj_info[2] + "</td>");
                            out.print("</tr>");
                            out.print("<tr bgcolor='#666666'><td colspan='3' style='padding:2px;'></td></tr>");
                            out.print("</table></a>");
                        } else {
                            String[] arg_datos = obj_info[6].toString().split("-");
                            for (int j = 0; j < arg_datos.length; j++) {
                                String x = arg_datos[j];
                                if (x.equals(idU)) {
                                    out.print("<a href='Nota?op=5&idN=" + obj_info[0] + "'>");
                                    out.print("<table class='tablaN' style='width:100%;'>");
                                    out.print("<tr>");
                                    out.print("<td  align='center'>" + obj_info[3] + "<br /></td>");
                                    out.print("<td><b>Asunto : </b> " + obj_info[4] + "</td>");
                                    out.print("<td rowspan='2' align='center'><img src='Interfaz/Contenido/Iconos/chulo.png' alt='Logo' width='25' height='15' title='Revisado' /></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td colspan='2'><b>Responsable : </b> " + obj_info[2] + "</td>");
                                    out.print("</tr>");
                                    out.print("<tr bgcolor='#666666'><td colspan='3' style='padding:2px;'></td></tr>");
                                    out.print("</table></a>");
                                    j = arg_datos.length;
                                } else {
                                    int p = arg_datos.length - 1;
                                    if (j == p) {
                                        out.print("<a href='Nota?op=4&idN=" + obj_info[0] + "&idU=" + idUsuario + "'>");
                                        out.print("<table class='tablaN' style='width:100%;'>");
                                        out.print("<tr>");
                                        out.print("<td  align='center'>" + obj_info[3] + "<br /></td>");
                                        out.print("<td><b>Asunto : </b> " + obj_info[4] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>Responsable : </b> " + obj_info[2] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr bgcolor='#666666'><td colspan='3' style='padding:2px;'></td></tr>");
                                        out.print("</table></a>");
                                    } else {
                                    }
                                }
                            }
                        }
                    }
                    out.print("</fieldset>");
                } else {
                    out.print("<fieldset class='resalta_field' id='Notificaciones' style='width: 400px; visibility: hidden; position: absolute; top: 158px; left: 35%;'>");
                    out.print("<table class='table' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<td><b>No se han encontrado Notificaciones</b></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</fieldset>");
                }
                // </editor-fold>
                // out.print("<li><a href='Registro?op=1'>Bitacoras</a></li>");
                out.print("</ul>");
                out.print("<br style='clear: left'/>");
                out.print("</div>");
            } else {
                try {
                    pageContext.getRequest().getRequestDispatcher("index.jsp").forward(pageContext.getRequest(), pageContext.getResponse());
                } catch (ServletException ex) {
                    Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
            try {
                pageContext.getRequest().getRequestDispatcher("index.jsp").forward(pageContext.getRequest(), pageContext.getResponse());
            } catch (ServletException ex1) {
                Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return super.doStartTag();
    }
}
