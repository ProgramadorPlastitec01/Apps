package Tags;

import Controladoras.CargoJpaController;
import Controladoras.NotasJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_nota extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        Date fecha = new Date();
        NotasJpaController jpa_nota = new NotasJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            List permisos = null;
            String rol = sesion.getAttribute("Rol").toString();
            String nombre = sesion.getAttribute("Nombre").toString();
            int IdUsuario = Integer.parseInt(sesion.getAttribute("Identificacion").toString());
            int CargoUsa = Integer.parseInt(sesion.getAttribute("Cargo").toString());
            String filtro = (String) pageContext.getRequest().getAttribute("filtro");
            int SeIdarea = Integer.parseInt(sesion.getAttribute("Area").toString());
            permisos = jpa_cargo.ConsultaCargosPorId(CargoUsa);
            //</editor-fold>
            Object[] obj_permisos = (Object[]) permisos.get(0);
            
            if (obj_permisos[13].equals(1)) {
                out.print("<div id='sidebar'>");
                if (pageContext.getRequest().getAttribute("ConsultanotaRM") != null) {
                    List ConNotasM = (List) pageContext.getRequest().getAttribute("ConsultanotaRM");
                    Object[] obj_notaM = (Object[]) ConNotasM.get(0);
                    out.print("<h3>Modificar nota  |<a href='Nota?op=1&idN=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a></h3>");
                    out.print("<form action='Nota?op=3&idN=" + obj_notaM[0] + "&txt_bus=" + filtro + "' method='post' onsubmit='ModificarN();' name='form1' onsubmit='checkSubmit();'>");
                    out.print("<b>Asunto: </b><br />");
                    out.print("<input type='text' name='txt_asuntoM' id='asunto-id' placeholder='Asunto' value='" + obj_notaM[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('asunto-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Descripción: </b><br />");
                    out.print("<textarea id='descripcion_id' name='text_descripcionM' class='input_full' rows='5'  placeholder='Descripción'  onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_notaM[5] + "</textarea><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('descripcion_id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<input type='submit' id='btsubmit' value='Modificar'><br />");
                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "        </div>");
                    out.print("</form>");
                } else {
                    // if (obj_permisos[13].equals(1)) {
                    out.print("<h3>Nueva nota</h3>");
                    out.print("<form action='Nota?op=2&SeIdarea=" + SeIdarea + "'  method='post' name='form1' onsubmit='RegistroN();' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<b>Fecha: </b><br />");
                    out.print("<input type='text' name='txt_fecha' id='fecha-id' placeholder='Fecha' class='required input_field'  value='" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() < 9 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() <= 9 ? "-0" : "-") + "" + fecha.getDate() + "' readonly='true'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('fecha-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Asunto: </b><br />");
                    out.print("<input type='text' name='txt_asunto' id='asunto-id' placeholder='Asunto' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('asunto-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Descripción: </b><br />");
                    out.print("<textarea id='descripcion_id' name='text_descripcion' class='input_full' rows='5'  placeholder='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('descripcion_id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<input type='submit' id='btsubmit' value='Registrar'><br/>");
                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "        </div>");
                    out.print("</form>");
//                    } else {
//                        out.print("<h3>Nueva nota</h3>");
//                        out.print("<center>");
//                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos'><br />");
//                        out.print("<b>Sin permiso de registro</b>");
//                        out.print("</center>");
//                    }
                }
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
            } else {
                out.print("<div id='content_sin'>");
            }
            if (pageContext.getRequest().getAttribute("Consultanota") != null) {
                List ConNotas = (List) pageContext.getRequest().getAttribute("Consultanota");
                out.print("<div style='float: right;'>");
                out.print("<form action='Nota?op=1&idN=" + 0 + "' method='post' >");
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</form>");
                out.print("</div>");
                out.print("<h3>Notas registradas</h3>");
                if (ConNotas == null || ConNotas.isEmpty()) {
                    out.print("<h3>No se encuentran notas registradas<h3>");
                } else {
                    out.print("<div id='NavPosicion'></div>");

                    out.print("<table class='table' id='resultados' style='width: 100%;'>");
                    for (int i = 0; i < ConNotas.size(); i++) {
                        //<editor-fold defaultstate="collapsed" desc="tabla-notas">
                        Object[] obj_nota = (Object[]) ConNotas.get(i);
                        String personalP = "";
                        String personalI = "";
                        out.print("<tr>");
                        out.print("<td colspan='7'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th rowspan='3' style='width:100px;' align='center'>" + obj_nota[3] + "</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>Asunto: </b>" + obj_nota[4] + "</th>");
                        out.print("<td><b>Responsable: </b>" + obj_nota[2] + "</th>");
                        if (obj_nota[2].equals(nombre + "/" + rol)) {
                            out.print("<td rowspan='2' style='width:5%;' align='center'><a href='Nota?op=1&idN=" + obj_nota[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            if (obj_nota[7].equals(0)) {
                                out.print("<td rowspan='2' style='width:5%;' align='center'><a href='#' onclick=\"EnviarNota(" + SeIdarea + "," + obj_nota[0] + ")\"><img src='Interfaz/Contenido/Iconos/Mail.png' alt='Logo' width='30' title='Enviar nota por correo' /></a></td>");
                            } else {
                                out.print("<td rowspan='2' style='width:5%;' align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' title='Se ha enviado la nota por correo' /></a></td>");
                            }
                        } else {
                            out.print("<td rowspan='2' align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' alt='Logo' width='25' height='25.5' title='Requiere Permisos para modificar'/></a></td>");
                            out.print("<td rowspan='2' align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' alt='Logo' width='25' height='25.5' title='Requiere Permisos para envio de correo'/></a></td>");
                        }
                        if (obj_nota[6] == null) {
                            out.print("<td rowspan='2'><b>No se ha revisado la nota</b></td>");
                        } else {
                            String[] arg_nameId = obj_nota[6].toString().split("-");
                            for (int j = 0; j < arg_nameId.length; j++) {
                                int cont = Integer.parseInt(arg_nameId[j]);
                                List usa = jpa_nota.ConsultaUsuariosRevisado(cont);
                                Object[] obj_usa = (Object[]) usa.get(0);
                                if ((j % 2) == 0) {
                                    personalP = personalP + "<b>" + obj_usa[1] + " " + obj_usa[2] + "</b><br />";
                                } else {
                                    personalI = personalI + "<b>" + obj_usa[1] + " " + obj_usa[2] + "</b><br />";
                                }
                            }
                            if (!obj_permisos[13].equals(1)) {
                                out.print("<td rowspan='2' style='width:18%'>");
                                out.print("" + personalP + "");
                                out.print("</td>");
                                out.print("<td rowspan='2' style='width:18%'>");
                                out.print("" + personalI + "");
                                out.print("</td>");
                            } else {
                                out.print("<td rowspan='2' style='width:15%'>");
                                out.print("" + personalP + "");
                                out.print("</td>");
                                out.print("<td rowspan='2' style='width:15%'>");
                                out.print("" + personalI + "");
                                out.print("</td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2'><b>Descripción: </b>" + obj_nota[5] + "</td>");
                        out.print("</tr>");
                        //</editor-fold>
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 20);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
