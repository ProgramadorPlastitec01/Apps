package Vista;

import Controlador.ActividadJpaController;
import Controlador.MaquinasJpaController;
import Controlador.NovedadesJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class VistaNovedades extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            HttpSession sesion = pageContext.getSession();
            MaquinasJpaController MaqJpa = new MaquinasJpaController();
            ActividadJpaController ActJpa = new ActividadJpaController();
            NovedadesJpaController NvdJpa = new NovedadesJpaController();
            int Id_Actividad = 0;
            int Id_Novedad = 0;
            String rol = sesion.getAttribute("rol").toString();
            String Usuario = sesion.getAttribute("nombre").toString();
            int Id_Usuario = Integer.parseInt(sesion.getAttribute("identificacion").toString());
            String Accion = "";
            List list_maquinas = null;
            List list_actividad = null;
            List list_novedad = null;
            List list_ubicaciones = null;
            List list_novedades = null;
//</editor-fold>
            Accion = pageContext.getRequest().getAttribute("Accion").toString();
            out.print("<div id='content_sin'>");
            //<editor-fold defaultstate="collapsed" desc="NOVEDADES R-GC-079">
            if (Accion.equals("Novedades-R-GC-079")) {
                Id_Actividad = Integer.parseInt(pageContext.getRequest().getAttribute("Id_Actividad").toString());
                Id_Novedad = Integer.parseInt(pageContext.getRequest().getAttribute("Id_Novedad").toString());
                list_novedades = NvdJpa.consultarNovendad(Id_Actividad);
                list_actividad = ActJpa.actividadId(Id_Actividad);
                Object[] Obj_Actividad = (Object[]) list_actividad.get(0);
                if (Id_Usuario == Integer.parseInt(Obj_Actividad[12].toString()) || rol.equals("Administrador")) {
                    out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Plus.png' width='25' height='25'  onclick='javascript:document.getElementById(\"Form_registro_Novedad\").style.display=\"block\"'></a>");
                    if (Id_Novedad == 0) {
                        //<editor-fold defaultstate="collapsed" desc="1. REGISTRAR">
                        out.print("<div class='sweet-local' tabindex='-1' id='Form_registro_Novedad' style='opacity: 1.03; display: none;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_registrar' style='width:510px; height:550px; position: absolute;top: 2%;left:25%; overflow:scroll;'>");
                        out.print("<div style='float:right;'><a href='#'><img src='Interfaz/Contenido/Iconos/Delete.png' onclick='javascript:document.getElementById(\"Form_registro_Novedad\").style.display=\"none\"' width='22' height='22' title='Cancelar'></a></div>");
                        out.print("<h3>Registar Novedad</h3>");
                        out.print("<form method='post' onsubmit='registroN()' action='Novedades?opc=2' name='form1'>");
                        out.print("<div style='width:200px; float:left;'>");
                        out.print("<input type='hidden' name='Id_Actividad' value='" + Id_Actividad + "' class='input_full' readonly='true'/>");
                        out.print("<b>Máquina :</b>");
                        out.print("<select name='Maquina' id='Maquina' onchange='javascript:this.value=this.value.toUpperCase();'>");
                        out.print("<option value=''>SELECCIONE MÁQUINA</option>");
                        list_maquinas = MaqJpa.consultaMaquinaria();
                        for (int i = 0; i < list_maquinas.size(); i++) {
                            Object[] Obj_maquina = (Object[]) list_maquinas.get(i);
                            if (Integer.parseInt(Obj_maquina[4].toString()) == 1) {
                                out.print("<option value='" + Obj_maquina[0] + "'>" + Obj_maquina[2] + "</option>");
                            }
                        }
                        out.print("</select><br />");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('Maquina');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<div style='width:500px; float:left;'>");
                        out.print("<textarea  id='descripcion-id' name='txt_descripcion-id' style='width:500px; height:650px;'><b style='color:#880e4f;'>Producto Trabajado:</b><div contenteditable='true'><p></p></div><hr/>"
                                + "<b style='color:#880e4f;'>Novedad:</b><div contenteditable='true'><p></p></div>");
                        out.print("</textarea>");
                        out.print("</div>");
                        out.print("<input type='submit' id='btsubmit'  value='Registrar'>");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left:72px; display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</fieldset></div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="2. MODIFICAR">
                        Id_Actividad = Integer.parseInt(pageContext.getRequest().getAttribute("Id_Actividad").toString());
                        list_novedad = NvdJpa.consultarNovendadId(Id_Novedad);
                        Object[] Obj_Nove = (Object[]) list_novedad.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Form_modificar' style='opacity: 1.03; display:block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:510px; height:550px; position: absolute;top: 2%;left:25%; overflow:scroll;'>");
                        out.print("<div style='float:right;'><a href='Novedades?opc=1&Id_Actividad=" + Id_Actividad + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                        out.print("<h3>Modificar Novedad</h3>");
                        out.print("<form method='post' onsubmit='registroN()' action='Novedades?opc=3' name='form1'>");
                        out.print("<div style='width:200px; float:left;'>");
                        out.print("<input type='hidden' name='Id_Novedad' value='" + Obj_Nove[0] + "' class='input_full' readonly='true'/>");
                        out.print("<input type='hidden' name='Id_Actividad' value='" + Id_Actividad + "' class='input_full' readonly='true'/>");
                        out.print("<input type='hidden' name='Accion' value='Modificar' class='input_full' readonly='true'/>");
                        out.print("<b>Máquina :</b>");
                        out.print("<select name='Maquina' id='Maquina' onchange='javascript:this.value=this.value.toUpperCase();'>");
                        out.print("<option value=''>SELECCIONE MÁQUINA</option>");
                        list_maquinas = MaqJpa.consultaMaquinaria();
                        for (int i = 0; i < list_maquinas.size(); i++) {
                            Object[] Obj_maquina = (Object[]) list_maquinas.get(i);
                            if (Obj_maquina[0].equals(Obj_Nove[2])) {
                                out.print("<option value='" + Obj_maquina[0] + "' selected>" + Obj_maquina[2] + "</option>");
                            }
                            out.print("<option value='" + Obj_maquina[0] + "'>" + Obj_maquina[2] + "</option>");
                        }
                        out.print("</select><br />");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('Maquina');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<div style='width:500px; float:left;'>");
                        out.print("<textarea  id='descripcion-id' name='txt_descripcion-id' style='width:500px; height:400px;'>" + Obj_Nove[4].toString().replace("<div>", "<div contenteditable='true'>") + "<hr/>"
                                + "" + Obj_Nove[5].toString().replace("<div>", "<div contenteditable='true'>") + "");
                        out.print("</textarea>");
                        out.print("</div>");
                        out.print("<input type='submit' id='btsubmit'  value='Modificar'>");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                        out.print("</fieldset></div>");
//</editor-fold>
                    }
                }
                out.print("<h3>Novedades   <a href='R_GC_079?opc=1&Id_Actividad=0&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' /></a></h3>");
                if (list_novedades == null) {
                    out.print("<center>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                    out.print("<b>No Encontro Ninguna Novedad</b>");
                    out.print("</center>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    for (int i = 0; i < list_novedades.size(); i++) {
                        Object[] Obj_Novedad = (Object[]) list_novedades.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='6'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th rowspan='2' style='width:10%;'>" + Obj_Novedad[8] + "</th>");
                        out.print("<td  style='width:44%;'><b>Consecutivo: </b>" + Obj_Novedad[11] + "</td>");
                        out.print("<td  style='width:44%;'><b>Fecha novedad: </b>" + Obj_Novedad[1] + "</td>");
                        if (Obj_Actividad[9].equals("a")) {
                            if ((Usuario.equals(Obj_Novedad[10])) || (rol.equals("Administrador"))) {
                                out.print("<td rowspan='3'  style='width:2%;' align='center'><a href='Novedades?opc=1&Id_Actividad=" + Obj_Novedad[3] + "&idN=" + Obj_Novedad[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' title='Modificar'</a></td>");
                            } else {
                                out.print("<td rowspan='3'><b>Responsable: </b>" + Obj_Novedad[10] + "</td>");
                            }
                        } else {
                            out.print("<td rowspan='2'><b>Responsable: </b>" + Obj_Novedad[10] + "</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td ><b>Novedad: </b>" + Obj_Novedad[4].toString().replace("Novedad:", "") + "</td>");
                        out.print("<td><b>Producto Trabajado: </b>" + Obj_Novedad[5].toString().replace("Producto Trabajado:", "") + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 30);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="5. Vista R-GC-079">
            if (Accion.equals("Imprimir")) {
                list_actividad = (List) this.pageContext.getRequest().getAttribute("list_actividad");
                Object[] Obj_Actividad = (Object[]) list_actividad.get(0);
                out.print("<a href='R_GC_079?opc=1&Id_Actividad=0&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Atras' /></a>");
                out.print("<div style='display:inline; float: right;'>");
                out.print("<a onclick='Imprimir();'><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 30px; height: 30.5px\" alt=\"\" title='Imprimir' />");
                out.print("</a> Imprimir o PDF");
                out.print("</div>");
                out.print("<div id='Imprimir'>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                String datoFecha = Obj_Actividad[2].toString().replace("-", "");
                int FechaInt = Integer.parseInt(datoFecha);
                if (FechaInt >= 20160101) {
                    out.print("<tr>");
                    out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                }
                out.print("<tr>");
                out.print("<td align='center' rowspan='3' style='width:25%;'><img src=\"Interfaz/Contenido/images/Logo.png\" style=\"width: 160px; height: 70px\" alt=\"\"/></td>");
                out.print("<td align='center' colspan='3'><h3 style='color: black;'>REGISTRO BITACORA GESTION CALIDAD<h3></td>");
                out.print("<td align='center'>CODIGO<br />R-GC-079</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' style='width:25%;'><b>Fecha: </b>" + Obj_Actividad[2] + "</td>");
                out.print("<td align='center' style='width:25%;'><b>Consecutivo: </b>" + Obj_Actividad[8] + "</td>");
                out.print("<td align='center'><b>Turno: </b><br />" + Obj_Actividad[4] + "</td>");
                out.print("<td align='center'>VERSION<br />2</td>");
                out.print("</tr>");
                out.print("<tr>");
                if ((Integer) Obj_Actividad[0] < 8480) {
                    out.print("<td align='center' colspan='2'><b>Ubicación : </b>N/A</td>");
                } else {
                    out.print("<td align='center' colspan='2'><b>Ubicación : </b>" + Obj_Actividad[14] + "</td>");
                }
                out.print("<td align='center' colspan='2'><b>Usuario: </b>" + Obj_Actividad[10].toString() + " " + Obj_Actividad[11].toString() + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<th colspan='5'>ACTIVIDADES CALIDAD</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='5'><b>Actividades urgentes: </b><br / >" + Obj_Actividad[5].toString().replace("Actividades Urgentes:", "") + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='width:50%;' colspan='2'><b>Novedades personal: </b><br / >" + Obj_Actividad[6].toString().replace("Personal:", "") + "</td>");
                out.print("<td colspan='3'><b>Observaciones: </b><br / >" + Obj_Actividad[7].toString().replace("Notas:", "") + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<th colspan='5' align='center'>NOVEDADES DE MAQUINA</th>");
                out.print("</tr>");
                list_novedades = (List) this.pageContext.getRequest().getAttribute("list_novedades");
                if (list_novedades != null) {
                    for (int i = 0; i < list_novedades.size(); i++) {
                        Object[] obj_novedad = (Object[]) list_novedades.get(i);
                        if (i == 0) {
                            out.print("<tr>");
                            out.print("<td><b>Máquina</b</td>");
                            out.print("<td style='width:35%;'><b>Producto trabajado</b></td>");
                            out.print("<td colspan='3'><b>Novedad</b></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td>" + obj_novedad[8] + "</td>");
                        out.print("<td style='width:35%;'>" + obj_novedad[5].toString().replace("Producto Trabajado:", "") + "</td>");
                        out.print("<td colspan='3'>" + obj_novedad[4].toString().replace("Novedad:", "") + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<center>");
                    out.print("<b>NO HAY NINGUNA NOVEDAD</b>");
                    out.print("</center>");
                }
                out.print("</table>");
                out.print("</div>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Módulo Novedades Máquina">
            //<editor-fold defaultstate="collapsed" desc="3. FORMULARIOS NOVEDADES MAQUINA">
            out.print("<div class='cleaner'></div></div>");
            if (Accion.equals("Consulta")) {
                list_ubicaciones = (List) this.pageContext.getRequest().getAttribute("list_ubicaciones");
                list_maquinas = (List) this.pageContext.getRequest().getAttribute("list_maquinas");
                out.print("<div id='sidebar'>");
                out.print("<h3>Consultar novedades</h3>");
                out.print("<form method='post' action='Novedades?opc=4' name='formubicacion'>");
                out.print("<input type='hidden' name='Accion' value='Consulta' class='input_full' readonly='true'/>");
                out.print("<b>Ubicación :</b>");
                out.print("<select name='txt_ubicacion' id='validateUB' onchange='PostBackUbicacion()' >");
                out.print("<option value=''>SELECCIONE SU UBICACIÓN</option>");
                for (int i = 0; i < list_ubicaciones.size(); i++) {
                    Object[] obj_UbicacionS = (Object[]) list_ubicaciones.get(i);
                    if ((obj_UbicacionS[2].equals(Integer.valueOf(1)))
                            && (!obj_UbicacionS[0].equals(Integer.valueOf(6)))) {
                        out.print("<option value='" + obj_UbicacionS[0] + "'>" + obj_UbicacionS[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateUB');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</form>");
                if (list_maquinas != null) {
                    out.print("<form method='post' action='Novedades?opc=4' name='form1'>");
                    out.print("<input type='hidden' name='Accion' value='Filter' class='input_full' readonly='true'/>");
                    out.print("<b>Máquina:</b>");
                    out.print("<select name='Id_Maquina' id='Id_Maquina' class='input_full'>");
                    out.print("<option value='0'>Seleccione la Máquina</option>");
                    for (int i = 0; i < list_maquinas.size(); i++) {
                        Object[] obj_maquina = (Object[]) list_maquinas.get(i);
                        if (((Integer) obj_maquina[4]).intValue() == 1) {
                            out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[2] + "</option>");
                        }
                    }
                    out.print("</select><br /><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Id_Maquina');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("<b>Fecha inicial</b>");
                    out.print("<input id='start' type='text' name='fechaI' class='required input_field'  placeholder='Seleccionar fecha' >");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('start');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Hora inicial</b>");
                    out.print("<input type='time' name='horaI' value='00:00:00'  placeholder='Hora' >");
                    out.print("<br />");
                    out.print("<b>Fecha final</b>");
                    out.print("<input id='end' type='text' name='fechaF' class='required input_field' placeholder='Seleccionar fecha' >");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('end');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Hora final</b>");
                    out.print("<input type='time' name='horaF' value='23:59:00'  placeholder='Hora' >");
                    out.print("<input type='submit' value='Consultar'/>");
                    out.print("</form>");
                }
                out.print("<script type='text/javascript'>");
                out.print("$(function() { $( '#datepicker' ).datepicker({ altField: '#alternate', altFormat: 'DD, d MM, yy' }); });");
                out.print("var validation = new LiveValidation('alternate');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<div class='cleaner'></div></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="4. CONSULTAR">
                out.print("<div id='content'>");
                list_novedades = (List) this.pageContext.getRequest().getAttribute("list_novedades");
                if (list_novedades == null) {
                    out.print("<center>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                    out.print("<b>No Encontro Ninguna Novedad</b>");
                    out.print("</center>");
                } else {
                    Object[] Obj_Novedad = (Object[]) list_novedades.get(0);
                    out.print("<h3>Novedades " + Obj_Novedad[8] + "</h3>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    for (int i = 0; i < list_novedades.size(); i++) {
                        Object[] Obj_Novedades = (Object[]) list_novedades.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='6'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th rowspan='2' style='width:10%;'>" + Obj_Novedades[8] + "</th>");
                        out.print("<td><b>Consecutivo: </b>" + Obj_Novedades[11] + "</td>");
                        out.print("<td><b>Fecha novedad: </b>" + Obj_Novedades[1] + "</td>");
                        out.print("<td rowspan='2'><b>Responsable: </b>" + Obj_Novedades[10] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='1'><b>Novedad: </b>" + Obj_Novedades[4] + "</td>");
                        out.print("<td><b>Producto trabajado: </b>" + Obj_Novedades[5] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 30);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            out.print("<div class='cleaner'></div></div>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(VistaNovedades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
