package Tags;

import Controladores.ActividadJpaController;
import Controladores.ActividadesOrdenJpaController;
import Controladores.EquipoJpaController;
import Controladores.NovedadOrdenJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.ParametroJpaController;
import Controladores.ParametroOrdenJpaController;
import Controladores.RepuestoOrdenJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.UsuarioJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_orden_trabajo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            //JPAS
            EquipoJpaController jpaceqp = new EquipoJpaController();
            TipoEquipoJpaController jpacieq = new TipoEquipoJpaController();
            ActividadJpaController jpacatv = new ActividadJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            ActividadesOrdenJpaController jpacaot = new ActividadesOrdenJpaController();
            RepuestoOrdenJpaController jpacrod = new RepuestoOrdenJpaController();
            ParametroOrdenJpaController jpacpod = new ParametroOrdenJpaController();
            NovedadOrdenJpaController jpacnod = new NovedadOrdenJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //FIN JPAS
            //VARIABLES GLOBALES
            String filtro = "";
            String posicion = "", user = "", contenido = "";
            int filtro_vacio = 0;
            int id_equipo = 0;
            int id_orden_trabajo = 0;
            int id_actividad_orden = 0;
            int instrucciones = 0;
            int programar_ot = 0;
            List lst_equipo = null;
            List lst_plantilla = null;
            List lst_ordenes_trabajo = null;
            List lst_orden_trabajo = null;
            List lst_actividades_orden = null;
            List lst_actividades = null;
            List lst_parametros_orden = null;
            List lst_novedades_orden = null;
            List lst_parametros = null;
            List lst_repuestos = null;
            List lst_usuarios = null;
            double total_tiempo = 0;
            double parametro = 0;
            if (pageContext.getRequest().getAttribute("Orden_trabajo") != null) {
                if (!rol.equals("Administrador")) {
                    out.print("<link rel='stylesheet' href='Interfaz/froala/CSS/validation_delete.css'>");
                } else {
                    out.print("");
                }
                // <editor-fold defaultstate="collapsed" desc="HISTORIAL ORDEN DE TRABAJO X EQUIPO">
                if (pageContext.getRequest().getAttribute("Orden_trabajo").toString().equals("Historial_orden")) {
                    id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo").toString());
                    programar_ot = Integer.parseInt(pageContext.getRequest().getAttribute("Programar").toString());
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_ordenes_trabajo = jpacotb.Traer_orden_trabajo_id_equipo(id_equipo);
                    } else {
                        lst_ordenes_trabajo = jpacotb.Traer_orden_trabajo_numero(Integer.parseInt(filtro));
                        if (lst_ordenes_trabajo == null) {
                            lst_ordenes_trabajo = jpacotb.Traer_orden_trabajo_id_equipo(id_equipo);
                            filtro_vacio++;
                        }
                    }

                    out.print("<div id='content_sin'>");
                    lst_equipo = jpaceqp.Traer_equipo(id_equipo);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    out.print("<h3><form action='Equipo?opc=1&ieq=0&ot=0&fto=" + obj_equipo[1] + "' method='post' name='FormVolver' id='FormVolver'>"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a equipos' /></a>Historial O.T " + obj_equipo[1] + "</h3>"
                            + "</form>");
                    if (lst_ordenes_trabajo == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de OT registrados</b>");
                        out.print("</center>");
                    } else {
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div align='right'><form action='Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        } else if (filtro_vacio > 0) {
                            out.print("<div align='right'><form action='Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0' method='post'><b class='rojo'>El valor filtrado no obtubo resultados  </b><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        } else {
                            out.print("<div align='right'><form action='Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                        }
                        out.print("<div id='NavPosicion'></div>");
                        //<editor-fold defaultstate="collapsed" desc="OT PROCESO IMAGEN">
                        out.print("<table class='table' id='resultados' style='width:100%;'>");
                        out.print("<tr>");
                        //out.print("<th colspan='10'>Order de trabajo</th>");
                        out.print("<th colspan='11'>Ordenes de trabajo</th>");//
                        out.print("</tr>");
                        for (int i = 0; i < lst_ordenes_trabajo.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes_trabajo.get(i);
                            out.print("<tr>");
                            //out.print("<td align='center' colspan='10'>"
                            lst_actividades_orden = jpacaot.Traer_actividades_id_orden((Integer) obj_ordenes[0]);
                            lst_parametros_orden = jpacpod.Traer_parametros_orden((Integer) obj_ordenes[0]);
                            int estado = Integer.parseInt(obj_ordenes[14].toString());
                            int programado = Integer.parseInt(obj_ordenes[15].toString());
                            lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
                            try {
                                out.print("<td colspan='11' " + ((Integer.parseInt(obj_ordenes[19].toString()) >= 3 && estado <= 3) ? " style='BACKGROUND-COLOR:#FFEAD9'" : " ") + ">");
                            } catch (Exception e) {
                                out.print("<td colspan='11' >");
                            }
                            out.print("" + ((obj_ordenes[8].equals(usuario) || obj_ordenes[10].equals(usuario)) ? "<div style='float:right'><img src='Interfaz/Contenido/Iconos/Clavo.png' alt='logo'/></div>" : "") + ""
                                    + "<div style='width:100px;float:left' align='left'><h2 style='margin:0px 0px 0px 0px'><b>O.T </b><b class='negro'> " + obj_ordenes[1] + "</b></h2><b class='negro'>" + obj_ordenes[17] + "</b></div>"
                                    + "<div style='float:left;width:300px;'><a " + ((estado <= 3) ? "onclick='CambiarResponsables(" + obj_ordenes[0] + "," + obj_ordenes[2] + ");' ><u><b>Programador : </b>" + obj_ordenes[6] + "</u></a>" : " ><b>Programador : </b>" + obj_ordenes[6] + "</a>") + "<br />"
                                    + "<b>Técnico Ejecutor : </b>" + obj_ordenes[8] + "<br />"
                                    + "<b>Técnico Revisor : </b>" + obj_ordenes[10] + "<br />"
                                    + "<b>Horometro</b><b class='negro'> " + obj_ordenes[4] + "</b><br />"
                                    + "<b>Equipo</b><b class='negro'> " + obj_ordenes[3] + "</b></div>"
                                    + "<a href='Orden_trabajo?opc=3&iot=" + obj_ordenes[0] + "&isg=1'>");
                            if (estado == 1 || estado == 2 || estado == 3) {
                                if (programado == 1) {
                                    if (lst_parametros_orden != null && lst_actividades_orden != null) {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3.png' alt='logo' />");
                                    } else if (lst_parametros_orden == null && lst_actividades_orden != null) {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2.png' alt='logo' />");
                                    } else if (lst_parametros_orden != null && lst_actividades_orden == null) {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23.png' alt='logo' />");
                                    } else {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1.png' alt='logo' />");
                                    }
                                } else if (lst_parametros_orden != null && lst_actividades_orden != null) {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3C.png' alt='logo' />");
                                } else if (lst_parametros_orden == null && lst_actividades_orden != null) {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2C.png' alt='logo' />");
                                } else if (lst_parametros_orden != null && lst_actividades_orden == null) {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23C.png' alt='logo' />");
                                } else {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1C.png' alt='logo' />");
                                }
                            } else if (estado == 4) {
                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_4" + ((lst_novedades_orden != null) ? "_nov" : "") + ".png' alt='logo' />");
                            } else if (estado == 5) {
                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_5" + ((lst_novedades_orden != null) ? "_nov" : "") + ".png' alt='logo' />");
                            } else if (estado == 6) {
                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_6" + ((lst_novedades_orden != null) ? "_nov" : "") + ".png' alt='logo' />");
                            }
                            //<editor-fold defaultstate="collapsed" desc="OLD PROGRESS BAR">
//                            out.print("" + ((obj_ordenes[8].equals(usuario) || obj_ordenes[10].equals(usuario)) ? "<div style='float:right'><img src='Interfaz/Contenido/Iconos/Clavo.png' alt='logo'/></div>" : "") + ""
//                                    + "<div style='width:100px;float:left' align='left'><h2 style='margin:0px 0px 0px 0px'><b>O.T </b><b class='negro'> " + obj_ordenes[1] + "</b></h2><b class='negro'>" + obj_ordenes[17] + "</b></div>"
//                                    + "<div style='float:left;width:300px;'><a " + ((obj_ordenes[9].equals("0000-00-00 00:00")) ? "onclick='CambiarResponsables(" + obj_ordenes[0] + "," + obj_ordenes[2] + ");' ><u><b>Programador : </b>" + obj_ordenes[6] + "</u></a>" : " ><b>Programador : </b>" + obj_ordenes[6] + "</a>") + "<br />"
//                                    + "<b>Técnico Ejecutor : </b>" + obj_ordenes[8] + "<br />"
//                                    + "<b>Técnico Revisor : </b>" + obj_ordenes[10] + "<br />"
//                                    + "<b>Horometro</b><b class='negro'> " + obj_ordenes[4] + "</b><br />"
//                                    + "<b>Equipo</b><b class='negro'> " + obj_ordenes[3] + "</b></div>"
//                                    + "<a href='Orden_trabajo?opc=3&iot=" + obj_ordenes[0] + "&isg=1'>");
////                            if (lst_actividades_orden != null && lst_parametros_orden == null) {
//                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
//                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2.png' alt='logo' />");
//                                } else {
//                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2.png' alt='logo' />");
//                                }
//                            } else if (lst_parametros_orden != null && lst_actividades_orden == null) {
//                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
//                                    if ((Integer) obj_ordenes[15] == 1) {
//                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23.png' alt='logo' />");
//                                    } else {
//                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23C.png' alt='logo' />");
//                                    }
//                                } else if ((Integer) obj_ordenes[15] == 1) {
//                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23.png' alt='logo' />");
//                                } else {
//                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23C.png' alt='logo' />");
//                                }
//                            } else if (lst_parametros_orden != null && lst_actividades_orden != null) {
//                                if (!obj_ordenes[9].equals("0000-00-00 00:00")) {
//                                    if (obj_ordenes[11].equals("0000-00-00 00:00")) {
//                                        lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
//                                        if (lst_novedades_orden == null) {
//                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_4.png' alt='logo' />");
//                                        } else {
//                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_4_nov.png' alt='logo' />");
//                                        }
//                                    } else if ((Integer) obj_ordenes[14] == 1) {
//                                        lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
//                                        if (lst_novedades_orden == null) {
//                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_5.png' alt='logo' />");
//                                        } else {
//                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_5_nov.png' alt='logo' />");
//                                        }
//                                    } else {
//                                        lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
//                                        if (lst_novedades_orden == null) {
//                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_6.png' alt='logo'  />");
//                                        } else {
//                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_6_nov.png' alt='logo'  />");
//                                        }
//                                    }
//                                } else if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
//                                    if ((Integer) obj_ordenes[15] == 1) {
//                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3.png' alt='logo' />");
//                                    } else {
//                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3C.png' alt='logo' />");
//                                    }
//                                } else if ((Integer) obj_ordenes[15] == 1) {
//                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3.png' alt='logo' />");
//                                } else {
//                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3C.png' alt='logo' />");
//                                }
//                            } else if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
//                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1.png' alt='logo'/>");
//                            } else {
//                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1.png' alt='logo'/>");
//                            }
//</editor-fold>
                            out.print("</a>");
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        //</editor-fold>
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        if (programar_ot != 0) {
                            lst_orden_trabajo = jpacotb.Traer_orden_trabajo_id_orden(programar_ot);
                            Object[] obj_orden_trabajo = (Object[]) lst_orden_trabajo.get(0);
                            out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:280px;visibility: visible;position: fixed;top: 190px;left: 35%;'>");
                            out.print("<div style='float:right'><a href='Orden_trabajo?opc=1&ieq=" + id_equipo + "&ot=0&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar' /></a></div>");
                            out.print("<form align='left' action='Orden_trabajo?opc=21' method='post' name='Form_prog_" + obj_equipo[0] + "' id='Form_prog_" + obj_equipo[0] + "'>");
                            out.print("<h3>O.T " + obj_orden_trabajo[1] + "</h3>");
                            out.print("<b>Equipo : </b><b class='negro'>" + obj_equipo[1] + "</b><br />");
                            out.print("<b>Horometro programado : </b><b class='negro'>" + obj_orden_trabajo[4] + "</b><br />");
                            out.print("<b>Quien Programa: </b>" + usuario + "<br /><br />");
                            out.print("<b>Tiempo estimado : </b><br />");
                            out.print("Cant. <input type='text' style='width:35px' name='Txt_tiempo_estimado' value='" + obj_orden_trabajo[5].toString().split(" ")[0] + "' id='Txt_tiempo_estimado' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tiempo_estimado');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            String tiempo_mod = "Tiempo <select name='Cbx_tiempo_estimado' id='Cbx_tiempo_estimado' style='width:100px'><option value='0'>Tiempo</option><option value='Minuto(s)'>Minuto(s)</option><option value='Hora(s)'>Hora(s)</option><option value='Dia(s)'>Dia(s)</option></select>";
                            if (tiempo_mod.contains("value='" + obj_orden_trabajo[5].toString().split(" ")[1] + "'")) {
                                tiempo_mod = tiempo_mod.replace("value='" + obj_orden_trabajo[5].toString().split(" ")[1] + "'", "value='" + obj_orden_trabajo[5].toString().split(" ")[1] + "' selected");
                            }
                            out.print("" + tiempo_mod
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tiempo_estimado');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                            lst_usuarios = jpacusa.Usuarios();
                            out.print("<b>Quien Ejecuta : </b><br />");
                            out.print("<select style='width:230px' name='Cbx_tecnico_ejecutor' id='Cbx_tecnico_ejecutor' title='Tecnico ejecutor'>");
                            out.print("<option value='0' >Seleccionar ejecutor</option>");
                            for (int i = 0; i < lst_usuarios.size(); i++) {
                                Object[] obj_tecnicos_ejecutores = (Object[]) lst_usuarios.get(i);
                                if (obj_tecnicos_ejecutores[8].equals("Tecnico") || obj_tecnicos_ejecutores[8].equals("Tecnico_Encargado")) {
                                    out.print("<option value='" + obj_tecnicos_ejecutores[1] + "'>" + obj_tecnicos_ejecutores[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tecnico_ejecutor');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                            out.print("<b>Quien Revisa : </b><br />");
                            out.print("<select style='width:230px' name='Cbx_tecnico_revisor' id='Cbx_tecnico_revisor' title='Tecnico revisor'>");
                            out.print("<option value='0' >Seleccionar revisor</option>");
                            for (int i = 0; i < lst_usuarios.size(); i++) {
                                Object[] obj_tecnicos_revisores = (Object[]) lst_usuarios.get(i);
                                if (obj_tecnicos_revisores[8].equals("Tecnico_Encargado")) {
                                    out.print("<option value='" + obj_tecnicos_revisores[1] + "'>" + obj_tecnicos_revisores[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tecnico_revisor');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                            out.print("<input type='hidden' name='ieq' value='" + obj_equipo[0] + "' />");
                            out.print("<input type='hidden' name='iot' value='" + programar_ot + "' />");
                            out.print("<input type='submit' value='Modificar' />");
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
                        }
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="R-MTI-103 ORDENES TRABAJO DETALLE">
                else if (pageContext.getRequest().getAttribute("Orden_trabajo").toString().equals("Historial_orden_detalle")) {
                    id_orden_trabajo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden_trabajo").toString());
                    id_actividad_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_actividad_orden").toString());
                    instrucciones = Integer.parseInt(pageContext.getRequest().getAttribute("Instrucciones").toString());
                    posicion = pageContext.getRequest().getAttribute("Posicion").toString();
                    lst_orden_trabajo = jpacotb.Traer_orden_trabajo_id_orden(id_orden_trabajo);
                    Object[] obj_orden_trabajo = (Object[]) lst_orden_trabajo.get(0);
                    lst_equipo = jpaceqp.Traer_equipo((Integer) obj_orden_trabajo[2]);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    lst_actividades_orden = jpacaot.Traer_actividades_id_orden(id_orden_trabajo);
                    int estado = Integer.parseInt(obj_orden_trabajo[14].toString());
                    int programacion = Integer.parseInt(obj_orden_trabajo[15].toString());
//                    out.print("<div id='content_sin'>Estado =" + estado + " Programado = " + programacion);
                    out.print("<div id='content_sin'>");
                    out.println("<input type='hidden' id='Txt_pos' value='" + posicion + "' />");
                    //<editor-fold defaultstate="collapsed" desc="REGISTROS CONTROLADO ACTIVIDADES">
                    if (id_actividad_orden > 0) {
                        out.print("<div class='sweet-local' id='Registro_actividad_orden' style='opacity: 1.03; display: " + ((id_actividad_orden > 0) ? "block" : "none") + ";'>");
                        out.print("<fieldset class='popup_local' style='width:60%;visibility: visible;position: absolute;top: 10px;left: 15%;height:60%;overflow:scroll;font-size:14px;'>");
                        out.print("<div style='float:right'><a onclick='document.getElementById(\"Registro_actividad_orden\").style.display = \"none\"'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar' /></a></div>");
                        if (lst_actividades_orden != null) {
                            out.print("<h3>Asignación de actividades a la OT</h3>");
                            out.print("<form action='Orden_trabajo?opc=11&iot=" + id_orden_trabajo + "' method='post' id='Form_actividades'>");
                            out.print("<div class='myButton' align='left'><input type='submit' value=''> Guardar actividades</div>");
                            int psc_ini = 0;
                            int orden_ini = 0;
                            int centro = 0;
                            int orden_fin = 0;
                            for (int i = 0; i < lst_actividades_orden.size(); i++) {
                                Object[] obj_actividades_orden = (Object[]) lst_actividades_orden.get(i);
                                if (Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) {
                                    orden_ini = i - 5;
                                    if (orden_ini < 0) {
                                        orden_ini = i;
                                    }
                                    centro = i;
                                    orden_fin = i + 6;
                                    if (orden_fin > lst_actividades_orden.size()) {
                                        orden_fin = lst_actividades_orden.size();
                                    }
                                }
                            }
                            out.print("<input type='hidden' name='psc' value='Modulo_act_" + id_actividad_orden + "' />");
                            out.print("<input type='hidden' name='Orden_inicio' value='" + orden_ini + "' />");
                            out.print("<input type='hidden' name='Orden_fin' value='" + orden_fin + "' />");
                            out.print("<table class='table' id='Excel' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center' rowspan='2' ><b>ITEM</b></td>");
                            out.print("<td align='center' rowspan='2' colspan='4'><b>ACTIVIDAD</b></td>");
                            if ((Integer) obj_orden_trabajo[1] > 10050) {
                                out.print("<td align='center' colspan='4'><b>EJECUCIÓN</b></td>");
                            } else {
                                out.print("<td align='center' colspan='3'><b>EJECUCIÓN</b></td>");
                            }
                            out.print("<td align='center' rowspan='2' colspan='3' ><b>OBSERVACIONES</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center'><b>TIEMPO</b></td>");
                            out.print("<td align='center'><b>SI</b></td>");
                            out.print("<td align='center'><b>NO</b></td>");
                            if ((Integer) obj_orden_trabajo[1] > 10050) {
                                out.print("<td align='center'><b>N/A</b></td>");
                            }
                            out.print("</tr>");
                            for (int i = orden_ini; i < orden_fin; i++) {
                                Object[] obj_actividades_orden = (Object[]) lst_actividades_orden.get(i);
                                out.print("<tr>");
                                out.print("<td align='center' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff'" : "") + "><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='4' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff;width:50%'" : "style='width:50%'") + ">" + obj_actividades_orden[4] + "</td>");
                                out.print("<td align='center' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff'" : "") + ">"
                                        + "<input type='text' name='Txt_valor_1" + obj_actividades_orden[0] + "' id='Txt_valor_1" + obj_actividades_orden[0] + "' value='" + ((obj_actividades_orden[5] == null || (Double) obj_actividades_orden[5] == 0.0) ? "0" : obj_actividades_orden[5]) + "' "
                                        + "style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor_1" + obj_actividades_orden[0] + "');val1.add(Validate.DecimalNA);</script>"
                                        + "</td>");
                                out.print("<td align='center' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff'" : "") + ">"
                                        + "<input name='Txt_valor_2" + obj_actividades_orden[0] + "' type='radio' value='1' " + (((Integer) obj_actividades_orden[6] == 1) ? "checked" : "") + " />"
                                        + "</td>");
                                out.print("<td align='center' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff'" : "") + ">"
                                        + "<input name='Txt_valor_2" + obj_actividades_orden[0] + "' type='radio' value='0' " + (((Integer) obj_actividades_orden[6] == 0) ? "checked" : "") + " />"
                                        + "</td>");
                                if ((Integer) obj_orden_trabajo[1] > 10050) {
                                    out.print("<td align='center' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff'" : "") + ">"
                                            + "<input name='Txt_valor_2" + obj_actividades_orden[0] + "' type='radio' value='2' " + (((Integer) obj_actividades_orden[6] == 2) ? "checked" : "") + " />"
                                            + "</td>");
                                }
                                out.print("<td align='center' colspan='3' " + ((Integer.parseInt(obj_actividades_orden[0].toString()) == id_actividad_orden) ? "style='background-color:#caf2ff'" : "") + ">"
                                        + "<textarea name='Txt_valor_3" + obj_actividades_orden[0] + "' style='border-width:0;width:95%;height:22px;font-size: 11px;color:#292929;margin:0;' onkeyup='javascript:this.value=this.value.toUpperCase();' >" + ((obj_actividades_orden[7] == null) ? "" : obj_actividades_orden[7]) + "</textarea>"
                                        + "</td>");
                                out.print("<tr>");
                            }
                            out.print("</table>");
                            out.print("</form>");
                        }
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    lst_plantilla = jpacotb.Traer_plantilla_ot_seguridad();
                    Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                    out.print("<div class='sweet-local' id='Instrucciones_seguridad' style='opacity: 1.03; display: " + ((instrucciones > 0) ? "block" : "none") + ";'>");
                    out.print("<fieldset class='popup_local' style='width:60%;visibility: visible;position: absolute;top: 10px;left: 15%;height:60%;overflow:scroll;font-size:14px;'>");
                    out.print("<div style='float:right'><a onclick='document.getElementById(\"Instrucciones_seguridad\").style.display = \"none\"'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar' /></a></div>");
                    out.print("<hr />" + obj_plantilla[2].toString());
                    out.print("</fieldset>");
                    out.print("</div>");
                    out.print("<div style='display:block'>");
                    out.print("<div style='float:left;width:300px'><form action='Orden_trabajo?opc=1&ieq=" + obj_orden_trabajo[2] + "&ot=0&fto=" + obj_orden_trabajo[1] + "' method='post' name='FormVolver' id='FormVolver'>"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a equipos' /></a>Volver</h2>"
                            + "</form></div>");
                    if (programacion == 0) {
                        if ((rol.equals("Jefe_MTI") || rol.equals("Administrador") || rol.equals("Asistente_MTI"))) {
                            out.print("<div style='float:right;width:400px' align='right'>"
                                    + "<a onclick='EliminarOT(" + id_orden_trabajo + "," + obj_orden_trabajo[1] + "," + obj_orden_trabajo[2] + "," + obj_orden_trabajo[4] + ")' ><img src=\"Interfaz/Contenido/Iconos/Trash.png\" alt=\"\" title='Eliminar OT' /></a> Eliminar<br />"
                                    + "</div>");
                        }
                    } else if (estado >= 5) {
                        out.print("<div style='float:right;width:59%' align='right'>");
                        if (rol.equals("Jefe_MTI") || rol.equals("Administrador") || rol.equals("Asistente_MTI")) {
                            if (estado == 5) {
                                out.print("<a onclick='VolverEjecucion(" + obj_orden_trabajo[0] + ")'><img src=\"Interfaz/Contenido/Iconos/arrow-alt-circle-left-solid.png\"  alt=\"\" title='Devolver OT' /></a> Devolver OT a ejecucion &nbsp;");
                                out.print("<a onclick=\"CerrarOT(" + id_orden_trabajo + "," + obj_orden_trabajo[2] + ");\"><img src=\"Interfaz/Contenido/Iconos/Open.png\"  alt=\"\" title='Cerrar OT' /></a>  Cerrar OT &nbsp;");
                            }
                        }
                        out.print("<a onclick=\"tableToExcel('Excel', 'OT " + obj_orden_trabajo[1] + "')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                                + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF <br />"
                                + "</div>");
                    }
                    out.print("</div>");

                    out.print("<div class=''>");

                    out.print("</div>");

                    out.print("<div id='Recarga'>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' id='Excel' style='width:100%'>");
                    // <editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<td colspan='12' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b><span id='Modulo_equipo'></span></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' />"
                            + "</td>");
                    out.print("<td align='center' colspan='6'><h3>REGISTRO <hr />PROGRAMA DE MANTENIMINTO PREVENTIVO LISTADO DE TAREAS DE ORDEN DE TRABAJO<br />INSUMOS FARMACÉUTICOS</h3></td>");
                    if ((Integer) obj_orden_trabajo[1] > 10050) {
                        out.print("<td align='center' colspan='3'><h3>CODIGO  R-MTI-103<br />VERSION </b><b> 4</h3></td>");
                    } else if ((Integer) obj_orden_trabajo[1] > 59) {
                        out.print("<td align='center' colspan='3'><h3>CODIGO  R-MTI-103<br />VERSION </b><b> 3</h3></td>");
                    } else {
                        out.print("<td align='center' colspan='3'><h3>CODIGO  R-MTI-103<br />VERSION </b><b> 2</h3></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='11'>EQUIPO</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>EQUIPO</b></td>");
                    out.print("<td align='center'><b class='negro'>" + obj_orden_trabajo[3] + "</b></td>");
                    out.print("<td align='center'><b>TIPO</b></td>");
                    out.print("<td align='center'><b class='negro'>" + obj_equipo[7] + "</b></td>");
                    out.print("<td align='center'><b>MARCA</b></td>");
                    out.print("<td align='center'>" + obj_equipo[2] + "</td>");
                    out.print("<td align='center'><b>MODELO</b></td>");
                    out.print("<td align='center'>" + obj_equipo[3] + "</td>");
                    out.print("<td align='center'><b>SERIAL</b></td>");
                    out.print("<td align='center'>" + obj_equipo[4] + "</td>");
                    out.print("<th rowspan='2' >O.T<br />" + obj_orden_trabajo[1] + "</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>DESCRIPCION</b></td>");
                    out.print("<td align='center'>" + obj_equipo[5] + "</td>");
                    out.print("<td align='center'><b>UBICACION</b></td>");
                    out.print("<td align='center'><b class='negro'>" + obj_orden_trabajo[18] + "</b></td>");
                    out.print("<td align='center'><b>VOLTAJE</b></td>");
                    out.print("<td align='center'>" + obj_equipo[10] + "</td>");
                    out.print("<td align='center'><b>CAPACIDAD</b></td>");
                    out.print("<td align='center'>" + obj_equipo[11] + "</td>");
                    out.print("<td align='center'><b>TIEMPO ESTIMADO</b></td>");
                    out.print("<td align='center'><b class='negro'>" + obj_orden_trabajo[5] + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>NOTA IMPORTANTE</b></td>");
                    out.print("<td colspan='5'>AL REALIZAR LAS TAREAS ENCOMENDADAS EN LA PRESENTE ORDEN DE TRABAJO DEBERA TENER EN CUENTA LAS CONDICIONES DE SEGURIDAD QUE CORRESPONDAN AL CASO</td>");
                    //out.print("<td align='center'><a href='Interfaz/Contenido/Progress_bar/Instrucciones_seguridad.pdf' target='_blank'><img src='Interfaz/Contenido/Iconos/Seguridad_industrial.png'></a></td>");
                    out.print("<td align='center'><a onclick='document.getElementById(\"Instrucciones_seguridad\").style.display = \"block\"' ><img src='Interfaz/Contenido/Iconos/Seguridad_industrial.png'></a></td>");
                    out.print("<td align='center' colspan='2'><b>HOROMETRO PROGRAMADO</b></td>");
                    out.print("<td align='center' colspan='2'><b class='negro'>" + obj_orden_trabajo[4] + "</b></td>");
                    out.print("</tr>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                    out.print("<tr>");
                    if (programacion == 0) {
                        if ((rol.equals("Jefe_MTI") || rol.equals("Administrador") || rol.equals("Asistente_MTI"))) {
                            out.print("<td align='center'><a onclick='Form_registro_actividades()' href='#' ><img id='cambiar' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registro de actividades' /></a></td>");
                            out.print("<th colspan='10'>ACTIVIDADES</th>");
                        } else {
                            out.print("<th colspan='11'>ACTIVIDADES</th>");
                        }
                    } else {
                        out.print("<th colspan='11'>ACTIVIDADES</th>");
                    }
                    out.print("</tr>");
                    if (lst_actividades_orden != null) {
                        out.print("<tr>");
                        out.print("<td align='center' rowspan='2' ><b>ITEM</b></td>");
                        out.print("<td align='center' rowspan='2' colspan='4'><b>ACTIVIDAD</b></td>");
                        if ((Integer) obj_orden_trabajo[1] > 10050) {
                            out.print("<td align='center' colspan='4'><b>EJECUCIÓN</b></td>");
                        } else {
                            out.print("<td align='center' colspan='3'><b>EJECUCIÓN</b></td>");
                        }
                        out.print("<td align='center' rowspan='2' colspan='3' ><b>OBSERVACIONES</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>TIEMPO</b></td>");
                        out.print("<td align='center'><b>SI</b></td>");
                        out.print("<td align='center'><b>NO</b></td>");
                        if ((Integer) obj_orden_trabajo[1] > 10050) {
                            out.print("<td align='center'><b>N/A</b></td>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_actividades_orden.size(); i++) {
                            Object[] obj_actividades_orden = (Object[]) lst_actividades_orden.get(i);
                            out.print("<tr>");
                            //<editor-fold defaultstate="collapsed" desc="ORDEN DE ACTIVIDADES">
                            out.print("<td align='center'>");
                            if (programacion == 0 && (rol.equals("Jefe_MTI") || rol.equals("Administrador") || rol.equals("Asistente_MTI"))) {
                                out.print("<b><form action='Orden_trabajo?opc=22&iot=" + id_orden_trabajo + "' method='post' id='Form_orden_" + i + "'>"
                                        + "<input type='hidden' name='iaot' id='iaot' value='" + obj_actividades_orden[0] + "' />"
                                        + "<input type='text' name='Txt_orden_" + obj_actividades_orden[0] + "' id='Txt_orden_" + obj_actividades_orden[0] + "' value='" + ((Integer.parseInt(obj_actividades_orden[10].toString()) >= 0) ? obj_actividades_orden[10] : "0") + "' "
                                        + "style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#016279;margin:0;font-weight: bold;' />"
                                        + "</form></b>");
                            } else if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                                if (estado <= 3 && programacion == 1) {
//                                    if (programacion == 1 && ((i + 1) > 5 && (i + 1) < (lst_actividades_orden.size() - 4))) {
                                    out.print("<b><a href='Orden_trabajo?opc=3&iot=" + id_orden_trabajo + "&isg=0&iao=" + obj_actividades_orden[0] + "'>" + (i + 1) + "</a></b>");
//                                    } else {
//                                        out.print("<b>" + (i + 1) + "</b>");
//                                    }
                                } else {
                                    out.print("<b>" + (i + 1) + "</b>");
                                }
                            } else {
                                out.print("<b>" + (i + 1) + "</b>");
                            }
                            out.print("<span id='Modulo_act_" + obj_actividades_orden[0] + "'></span>");
                            out.print("</td>");
                            //</editor-fold>
                            out.print("<td colspan='4' style='width:50%'>" + obj_actividades_orden[4] + "</td>");
                            if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                                if (estado == 4) {
                                    if (programacion == 1) {
                                        out.print("<td align='center' ><b class='negro'>" + ((obj_actividades_orden[5] == null || (Double) obj_actividades_orden[5] == 0.0) ? "" : obj_actividades_orden[5]) + "</b></td>");
                                        if ((Integer) obj_orden_trabajo[1] > 10050) {
                                            if ((Integer) obj_actividades_orden[6] == 0) {
                                                out.print("<td></td><td align='center'><b class='negro'>X</b></td><td></td>");
                                            } else if ((Integer) obj_actividades_orden[6] == 2) {
                                                out.print("<td></td><td></td><td align='center'><b class='negro'>X</b></td>");
                                            } else {
                                                out.print("<td align='center'><b class='negro'>X</b></td><td></td><td></td>");
                                            }
                                        } else {
                                            if ((Integer) obj_actividades_orden[6] == 0) {
                                                out.print("<td></td><td align='center'><b class='negro'>X</b></td>");
                                            } else {
                                                out.print("<td align='center'><b class='negro'>X</b></td><td></td>");
                                            }
                                        }
                                        out.print("<td colspan='3'>" + ((obj_actividades_orden[7] == null) ? "" : obj_actividades_orden[7]) + "</td>");
                                        //<editor-fold defaultstate="collapsed" desc="OLD_ACTIVIDADES">
//                                        out.print("<td align='center'>"
//                                                + "<input type='text' name='Txt_valor_1" + obj_actividades_orden[0] + "' id='Txt_valor_1" + obj_actividades_orden[0] + "' value='" + ((obj_actividades_orden[5] == null || (Double) obj_actividades_orden[5] == 0.0) ? "0" : obj_actividades_orden[5]) + "' "
//                                                + "style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' />"
//                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor_1" + obj_actividades_orden[0] + "');val1.add(Validate.DecimalNA);</script>"
//                                                + "</td>");
//                                        out.print("<td align='center'>"
//                                                + "<input name='Txt_valor_2" + obj_actividades_orden[0] + "' type='radio' value='1' " + (((Integer) obj_actividades_orden[6] == 1) ? "checked" : "") + " />"
//                                                + "</td>");
//                                        out.print("<td align='center'>"
//                                                + "<input name='Txt_valor_2" + obj_actividades_orden[0] + "' type='radio' value='0' " + (((Integer) obj_actividades_orden[6] == 0) ? "checked" : "") + " />"
//                                                + "</td>");
//                                        out.print("<td align='center' colspan='3'>"
//                                                + "<script type='text/javascript'>"
//                                                + "function checkearTecla_" + obj_actividades_orden[0] + "(e){"
//                                                + "if(e.keyCode == 13){"
//                                                + "document.getElementById('Form_actividades').submit();"
//                                                + "return true;"
//                                                + "}}</script>"
//                                                + "<textarea name='Txt_valor_3" + obj_actividades_orden[0] + "' style='border-width:0;width:95%;height:22px;font-size: 11px;color:#292929;margin:0;' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla_" + obj_actividades_orden[0] + "(event)'>" + ((obj_actividades_orden[7] == null) ? "" : obj_actividades_orden[7]) + "</textarea>"
//                                                + "</td>");
//</editor-fold>
                                    } else {
                                        out.print("<td align='center' colspan='6'><b class='rojo'>Actividad sin ejecutar</b></td>");
                                    }
                                } else {
                                    out.print("<td align='center' ><b class='negro'>" + ((obj_actividades_orden[5] == null || (Double) obj_actividades_orden[5] == 0.0) ? "" : obj_actividades_orden[5]) + "</b></td>");
                                    if ((Integer) obj_orden_trabajo[1] > 10050) {
                                        if ((Integer) obj_actividades_orden[6] == 0) {
                                            out.print("<td></td><td align='center'><b class='negro'>X</b></td><td></td>");
                                        } else if ((Integer) obj_actividades_orden[6] == 2) {
                                            out.print("<td></td><td></td><td align='center'><b class='negro'>X</b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'>X</b></td><td></td><td></td>");
                                        }
                                    } else {
                                        if ((Integer) obj_actividades_orden[6] == 0) {
                                            out.print("<td></td><td align='center'><b class='negro'>X</b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'>X</b></td><td></td>");
                                        }
                                    }
                                    out.print("<td colspan='3'>" + obj_actividades_orden[7] + "</td>");
                                }
                            } else {
                                out.print("<td align='center'><b class='negro'>" + ((obj_actividades_orden[5] == null || (Double) obj_actividades_orden[5] == 0.0) ? "" : obj_actividades_orden[5]) + "</b></td>");
                                if ((Integer) obj_orden_trabajo[1] > 10050) {
                                    if ((Integer) obj_actividades_orden[6] == 0) {
                                        out.print("<td></td><td align='center'><b class='negro'>X</b></td><td></td>");
                                    } else if ((Integer) obj_actividades_orden[6] == 2) {
                                        out.print("<td></td><td></td><td align='center'><b class='negro'>X</b></td>");
                                    } else {
                                        out.print("<td align='center'><b class='negro'>X</b></td><td></td><td></td>");
                                    }
                                } else {
                                    if ((Integer) obj_actividades_orden[6] == 0) {
                                        out.print("<td></td><td align='center'><b class='negro'>X</b></td>");
                                    } else {
                                        out.print("<td align='center'><b class='negro'>X</b></td><td></td>");
                                    }
                                }
                                out.print("<td colspan='3'>" + obj_actividades_orden[7] + "</td>");
                            }
                            out.print("</tr>");
                            if (obj_actividades_orden[5] != null) {
                                total_tiempo = total_tiempo + (Double) obj_actividades_orden[5];
                                if (lst_actividades_orden.size() - 1 == i) {
                                    out.print("<tr>");
                                    out.print("<td align='right' colspan='5'><b>TIEMPO TOTAL ACTIVIDAD</b></td>");
                                    out.print("<td align='center'><b>" + total_tiempo + "</b></td>");
                                    out.print("<td colspan='5'></b></td>");
                                    out.print("</tr>");
                                }
                            } else if (lst_actividades_orden.size() - 1 == i) {
                                out.print("<tr>");
                                out.print("<td align='right' colspan='5'><b>TIEMPO TOTAL ACTIVIDAD</b></td>");
                                out.print("<td align='center'><b>N/A</b></td>");
                                out.print("<td colspan='5'></b></td>");
                                out.print("</tr>");
                            }
                        }
                    }
//                    out.print("</form>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="REPUESTOS">
                    out.print("<tr>");
                    if (programacion == 0) {
                        out.print("<th colspan='11'>REPUESTOS<span id='Modulo_repuestos'></span></th>");
                    } else if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                        out.print("<td align='center'><a onclick='CrearRepuestos(" + id_orden_trabajo + ");'><img src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registro de repuestos' /></a></td>");
                        out.print("<th colspan='9'>REPUESTOS<span id='Modulo_repuestos'></span></th>");
                        out.print("<form action='Orden_trabajo?opc=16&iot=" + id_orden_trabajo + "' method='post' id='Form_repuestos'>");
                        out.print("<td align='center'><div class='myButton'><input type='submit' value=''></div></td>");
                    } else {
                        out.print("<th colspan='11'>REPUESTOS<span id='Modulo_repuestos'></span></th>");
                    }
                    out.print("</tr>");
                    lst_repuestos = jpacrod.Traer_repuestos_orden(id_orden_trabajo);
                    if (lst_repuestos == null) {
                        out.print("<td align='center' colspan='11' ><b class='negro'>No se utilizarón repuestos en esta OT.</b></td>");
                    } else {
                        out.print("<tr>");
                        out.print("<td align='center' colspan='1' rowspan='2'><b>ITEM</b></td>");
                        out.print("<td align='center' colspan='2' rowspan='2'><b>REPUESTO</b></td>");
                        out.print("<td align='center' colspan='2' rowspan='2'><b>CONDICIÓN</b></td>");
                        out.print("<td align='center' colspan='3'><b>DATOS</b></td>");
                        out.print("<td align='center' colspan='3' rowspan='2'><b>OBSERVACIONES</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>CANTIDAD</b></td>");
                        out.print("<td align='center'><b>REQUERIDOS</b></td>");
                        out.print("<td align='center'><b>UTILIZADOS</b></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_repuestos.size(); i++) {
                            Object[] obj_repuestos_orden = (Object[]) lst_repuestos.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                                if (estado <= 3 && programacion == 1) {
                                    out.print("<td align='center' colspan='2'>");
                                    out.print("<input type='text' name='Txt_valor_1_" + obj_repuestos_orden[0] + "' value='" + ((obj_repuestos_orden[3] == null) ? "" : obj_repuestos_orden[3]) + "' style='border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "</td>");
                                    out.print("<td align='center' colspan='2'>"
                                            + "<div style='display:block'><div style='float:left;width:80%'><b class='naranja'>El repuesto nuevo cumple con las especificaciones tecnicas del original.</b></div>"
                                            + "<div style='float:right'>"
                                            + "SI <input name='Txt_valor_2_" + obj_repuestos_orden[0] + "' type='radio' value='1' "
                                            + "" + (((Integer) obj_repuestos_orden[4] == 0) ? "" : "checked") + " />"
                                            + "NO <input name='Txt_valor_2_" + obj_repuestos_orden[0] + "' type='radio' value='0' "
                                            + "" + (((Integer) obj_repuestos_orden[4] == 0) ? "checked" : "") + " />"
                                            + "</div></div></td>");
                                    out.print("<td align='center'>");
                                    out.print("<input type='text' name='Txt_valor_3_" + obj_repuestos_orden[0] + "' value='" + ((obj_repuestos_orden[5] == null || (Double) obj_repuestos_orden[5] == 0.0) ? "" : obj_repuestos_orden[5]) + "' style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' />"
                                            + "</td>");
                                    out.print("<td align='center'>");
                                    out.print("<input type='text' name='Txt_valor_4_" + obj_repuestos_orden[0] + "' value='" + ((obj_repuestos_orden[6] == null || (Double) obj_repuestos_orden[6] == 0.0) ? "" : obj_repuestos_orden[6]) + "' style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' />"
                                            + "</td>");
                                    out.print("<td align='center'>");
                                    out.print("<input type='text' name='Txt_valor_5_" + obj_repuestos_orden[0] + "' value='" + ((obj_repuestos_orden[7] == null || (Double) obj_repuestos_orden[7] == 0.0) ? "" : obj_repuestos_orden[7]) + "' style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' />"
                                            + "</td>");
                                    if ((Integer) obj_repuestos_orden[4] == 0) {
                                        out.print("<td align='center' colspan='3'>"
                                                + "<script type='text/javascript'>"
                                                + "function checkearTecla2_" + obj_repuestos_orden[0] + "(e){"
                                                + "if(e.keyCode == 13){"
                                                + "document.getElementById('Form_repuestos').submit();"
                                                + "return true;"
                                                + "}}</script>"
                                                + "<textarea name='Txt_valor_6_" + obj_repuestos_orden[0] + "' style='border-width:0;width:95%;height:22px;font-size: 11px;color:#292929;margin:0;' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla2_" + obj_repuestos_orden[0] + "(event)'>" + ((obj_repuestos_orden[8] == null) ? "" : obj_repuestos_orden[8]) + "</textarea>"
                                                + "</td>");
                                    } else {
                                        out.print("<td align='center' colspan='3'>"
                                                + "<textarea name='Txt_valor_6_" + obj_repuestos_orden[0] + "' style='border-width:0;width:95%;height:22px;font-size: 11px;color:#292929;margin:0;' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla2_" + obj_repuestos_orden[0] + "(event)' readonly='true'>N/A</textarea>"
                                                + "</td>");
                                    }
                                } else {
                                    out.print("<td colspan='2'><b class='negro'>" + obj_repuestos_orden[3] + "</b></td>");
                                    if ((Integer) obj_repuestos_orden[4] == 0) {
                                        out.print("<td colspan='2'><b class='rojo'>El repuesto no cumple con las especificaciones tecnicas del original.</b></td>");
                                    } else {
                                        out.print("<td colspan='2'><b class='verde'>El repuesto cumple con las especificaciones tecnicas del original.</b></td>");
                                    }
                                    out.print("<td align='center'>" + ((obj_repuestos_orden[5] == null || (Double) obj_repuestos_orden[5] == 0.0) ? "" : obj_repuestos_orden[5]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_repuestos_orden[6] == null || (Double) obj_repuestos_orden[6] == 0.0) ? "" : obj_repuestos_orden[6]) + "</td>");
                                    out.print("<td align='center'>" + ((obj_repuestos_orden[7] == null || (Double) obj_repuestos_orden[7] == 0.0) ? "" : obj_repuestos_orden[7]) + "</td>");
                                    out.print("<td colspan='3'>" + ((obj_repuestos_orden[8].toString() == null ? "" == null : obj_repuestos_orden[8].toString().equals("")) ? "N/A" : obj_repuestos_orden[8]) + "</td>");
                                }
                            } else {
                                out.print("<td colspan='2'><b class='negro'>" + obj_repuestos_orden[3] + "</b></td>");
                                if ((Integer) obj_repuestos_orden[4] == 0) {
                                    out.print("<td colspan='2'><b class='rojo'>El repuesto no cumple con las especificaciones tecnicas del original.</b></td>");
                                } else {
                                    out.print("<td colspan='2'><b class='verde'>El repuesto cumple con las especificaciones tecnicas del original.</b></td>");
                                }
                                out.print("<td align='center'>" + ((obj_repuestos_orden[5] == null || (Double) obj_repuestos_orden[5] == 0.0) ? "" : obj_repuestos_orden[5]) + "</td>");
                                out.print("<td align='center'>" + ((obj_repuestos_orden[6] == null || (Double) obj_repuestos_orden[6] == 0.0) ? "" : obj_repuestos_orden[6]) + "</td>");
                                out.print("<td align='center'>" + ((obj_repuestos_orden[7] == null || (Double) obj_repuestos_orden[7] == 0.0) ? "" : obj_repuestos_orden[7]) + "</td>");
                                out.print("<td colspan='3'>" + ((obj_repuestos_orden[8].toString() == null ? "" == null : obj_repuestos_orden[8].toString().equals("")) ? "N/A" : obj_repuestos_orden[8]) + "</td>");
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</form>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="PARAMETROS">
                    out.print("<tr>");
                    if (programacion == 0) {
                        if ((rol.equals("Jefe_MTI") || rol.equals("Administrador") || rol.equals("Asistente_MTI"))) {
                            out.print("<td align='center'><a onclick='Form_registro_parametros()' href='#' ><img id='cambiar_2' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registro de parámetros' /></a></td>");
                            out.print("<th colspan='10'>PARAMETROS<span id='Modulo_parametros'></span></th>");
                        } else {
                            out.print("<th colspan='11'>PARAMETROS<span id='Modulo_parametros'></span></th>");
                        }
                    } else if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                        out.print("<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "' method='post' id='Form_parametros'>");
                        out.print("<th colspan='10'>PARAMETROS<span id='Modulo_parametros'></span></th>");
                        out.print("<td align='center'><div class='myButton'><input type='submit' value=''></div></td>");
                    } else {
                        out.print("<th colspan='11'>PARAMETROS<span id='Modulo_parametros'></span></th>");
                    }
                    out.print("</tr>");
                    lst_parametros_orden = jpacpod.Traer_parametros_orden(id_orden_trabajo);
                    if (lst_parametros_orden == null) {
                    } else {
                        out.print("<tr>");
                        out.print("<td align='center' rowspan='2' ><b>ITEM</b></td>");
                        out.print("<td align='center' rowspan='2' colspan='2'><b>PARAMETRO</b></td>");
                        out.print("<td align='center' rowspan='2'><b>INSTRUMENTO</b></td>");
                        out.print("<td align='center' rowspan='2' colspan='2'><b>ESTANDAR</b></td>");
                        out.print("<td align='center' rowspan='2' colspan='3'><b>VALORES TOMADOS</b></td>");
                        out.print("<td align='center' colspan='2'><b>CUMPLE</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>SI</b></td>");
                        out.print("<td align='center'><b>NO</b></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_parametros_orden.size(); i++) {
                            Object[] obj_parametros_orden = (Object[]) lst_parametros_orden.get(i);
                            out.print("<tr>");
                            //<editor-fold defaultstate="collapsed" desc="ORDEN DE ACTIVIDADES">
                            out.print("<td align='center'>");
                            if (programacion == 0 && (rol.equals("Jefe_MTI") || rol.equals("Administrador") || rol.equals("Asistente_MTI"))) {
                                out.print("<b><form action='Orden_trabajo?opc=23&iot=" + id_orden_trabajo + "' method='post' id='Form_orden_parametro" + i + "'>"
                                        + "<input type='hidden' name='ipo' id='ipo' value='" + obj_parametros_orden[0] + "' />"
                                        + "<input type='text' name='Txt_orden_parametro_" + obj_parametros_orden[0] + "' id='Txt_orden_" + obj_parametros_orden[0] + "' value='" + ((Integer.parseInt(obj_parametros_orden[14].toString()) >= 0) ? obj_parametros_orden[14] : "0") + "' "
                                        + "style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#016279;margin:0;font-weight: bold;' />"
                                        + "</form></b>"
                                );
                            } else {
                                out.print("<b>" + (i + 1) + "</b>");
                            }
                            out.print("</td>");
//                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            //</editor-fold>
                            out.print("<td colspan='2'>" + obj_parametros_orden[2] + "</td>");
                            out.print("<td>" + obj_parametros_orden[3] + "</td>");
                            String validar = obj_parametros_orden[13].toString();
                            if (validar.equals("Numero")) {
                                out.print("<td align='center' colspan='2'><b>MIN (</b>" + ((Double) obj_parametros_orden[7] - (Double) obj_parametros_orden[9]) + "<b> - </b>" + ((Double) obj_parametros_orden[7] + (Double) obj_parametros_orden[8]) + "<b>) MAX</b></td>");
                            } else if (validar.equals("Estado")) {
                                out.print("<td align='center' colspan='2'><b>N/A</b></td>");
                            } else {
                                out.print("<td align='center' colspan='2'><b>N/A</b></td>");
                            }
                            if ((Integer) obj_parametros_orden[6] == 1) {
                                if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                                    if (estado <= 3 || programacion == 1) {
                                        if (obj_parametros_orden[13].equals("Numero") || obj_parametros_orden[13].equals("Caracter")) {
                                            out.print("<td align='center' colspan='3'>"
                                                    //                                                + "<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "&atb=toma1&ipo=" + obj_parametros_orden[0] + "' method='post'>"
                                                    + "<input type='text' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='" + ((obj_parametros_orden[10] == null) ? "" : obj_parametros_orden[10]) + "' style='text-align:center;border-width:0;width:150px;font-size: 11px;color:#292929;margin:0;' />"
                                                    + "<b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b></td>"
                                                    + "<input type='hidden' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='0' />"
                                                    + "<input type='hidden' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='0' />");
                                            //if (((Double) obj_parametros_orden[7] + (Double) obj_parametros_orden[8]) >= (Double) obj_parametros_orden[10] && ((Double) obj_parametros_orden[7] - (Double) obj_parametros_orden[9]) <= (Double) obj_parametros_orden[10]) {
                                        } else if (obj_parametros_orden[13].equals("Estado")) {
                                            out.print("<td align='center' colspan='3'><b><input type='radio' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='Cumple' " + ((obj_parametros_orden[10] == null) ? "" : ((obj_parametros_orden[10].equals("Cumple")) ? "checked" : "")) + "/> Cumple<br />"
                                                    + "<input type='radio' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='No cumple' " + ((obj_parametros_orden[10] == null) ? "" : ((obj_parametros_orden[10].equals("No cumple")) ? "checked" : "")) + "/> No cumple</b></td>"
                                                    + "<input type='hidden' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='0' />"
                                                    + "<input type='hidden' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='0' />");
                                        }
                                    } else {
                                        out.print("<td align='center' colspan='3'><b class='rojo'>Parametro sin ejecutar</b></td>");
                                    }
                                } else {
                                    out.print("<td>" + ((obj_parametros_orden[10] == null) ? "" : obj_parametros_orden[10] + " <b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b>") + "</td>");
                                    out.print("<td></td>");
                                    out.print("<td></td>");
                                }
                                try {
                                    if (validar.equals("Numero")) {
                                        double max = (Double) obj_parametros_orden[7] + (Double) obj_parametros_orden[8];
                                        double min = (Double) obj_parametros_orden[7] - (Double) obj_parametros_orden[9];
                                        double valor1 = Double.parseDouble(obj_parametros_orden[10].toString());
                                        if (valor1 <= max && valor1 >= min) {
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                        }
                                    } else if (obj_parametros_orden[13].equals("Estado")) {
                                        if (obj_parametros_orden[10].equals("Cumple")) {
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                        }
                                    } else {
                                        out.print("<td align='center'><b class='negro'>X</b></td>");
                                        out.print("<td align='center'><b class='negro'></b></td>");
                                    }
                                } catch (Exception e) {
                                    out.print("<td align='center' colspan='2'><b class='negro'>No se puede definir</b></td>");
                                }
                            } else if ((Integer) obj_parametros_orden[6] == 2) {
                                if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                                    if (estado <= 3 || programacion == 1) {
                                        if (obj_parametros_orden[13].equals("Numero") || obj_parametros_orden[13].equals("Caracter")) {
                                            out.print("<td align='center' colspan='2'>"
                                                    //                                                + "<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "&atb=toma1&ipo=" + obj_parametros_orden[0] + "' method='post'>"
                                                    + "<input type='text' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='" + ((obj_parametros_orden[10] == null) ? "" : obj_parametros_orden[10]) + "' style='text-align:center;border-width:0;width:80px;font-size: 11px;color:#292929;margin:0;' />"
                                                    + "<b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b></td>");
                                            out.print("<td align='center'>"
                                                    //                                                + "<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "&atb=toma2&ipo=" + obj_parametros_orden[0] + "' method='post'>"
                                                    + "<input type='text' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='" + ((obj_parametros_orden[11] == null) ? "" : obj_parametros_orden[11]) + "' style='text-align:center;border-width:0;width:50px;font-size: 11px;color:#292929;margin:0;' />"
                                                    + "<b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b></td>"
                                                    + "<input type='hidden' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='0' />");
                                        } else if (obj_parametros_orden[13].equals("Estado")) {
                                            out.print("<td align='center' colspan='2'><b><input type='radio' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='Cumple' " + ((obj_parametros_orden[10] == null) ? "" : ((obj_parametros_orden[10].equals("Cumple")) ? "checked" : "")) + "/> Cumple<br />"
                                                    + "<input type='radio' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='No cumple' " + ((obj_parametros_orden[10] == null) ? "" : ((obj_parametros_orden[10].equals("No cumple")) ? "checked" : "")) + "/> No cumple</b></td>");
                                            out.print("<td align='center'><b><input type='radio' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='Cumple' " + ((obj_parametros_orden[11] == null) ? "" : ((obj_parametros_orden[11].equals("Cumple")) ? "checked" : "")) + "/> Cumple<br />"
                                                    + "<input type='radio' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='No cumple' " + ((obj_parametros_orden[11] == null) ? "" : ((obj_parametros_orden[11].equals("No cumple")) ? "checked" : "")) + "/> No cumple</b></td>"
                                                    + "<input type='hidden' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='0' />");
                                        }
                                    } else {
                                        out.print("<td align='center' colspan='3'><b class='rojo'>Parametro sin ejecutar</b></td>");
                                    }
                                } else {
                                    out.print("<td>" + ((obj_parametros_orden[10] == null) ? "" : obj_parametros_orden[10] + " <b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b>") + "</td>");
                                    out.print("<td>" + ((obj_parametros_orden[11] == null) ? "" : obj_parametros_orden[11] + " <b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b>") + "</td>");
                                    out.print("<td></td>");
                                }
                                try {
                                    if (obj_parametros_orden[13].equals("Numero")) {
                                        double max = (Double) obj_parametros_orden[7] + (Double) obj_parametros_orden[8];
                                        double min = (Double) obj_parametros_orden[7] - (Double) obj_parametros_orden[9];
                                        double valor1 = Double.parseDouble(obj_parametros_orden[10].toString());
                                        double valor2 = Double.parseDouble(obj_parametros_orden[11].toString());
                                        if ((valor1 <= max && valor1 >= min) && (valor2 <= max && valor2 >= min)) {
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                        }
                                    } else if (obj_parametros_orden[13].equals("Estado")) {
                                        if (obj_parametros_orden[10].equals("Cumple") && obj_parametros_orden[11].equals("Cumple")) {
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                        }
                                    } else {
                                        out.print("<td align='center'><b class='negro'>X</b></td>");
                                        out.print("<td align='center'><b class='negro'></b></td>");
                                    }
                                } catch (Exception e) {
                                    out.print("<td align='center' colspan='2'><b class='negro'>No se puede definir</b></td>");
                                }
                            } else {
                                if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                                    if (estado <= 3 || programacion == 1) {
                                        if (obj_parametros_orden[13].equals("Numero") || obj_parametros_orden[13].equals("Caracter")) {
                                            out.print("<td align='center'>"
                                                    //                                                + "<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "&atb=toma1&ipo=" + obj_parametros_orden[0] + "' method='post' style='border-width:0;width:70px;margin:0;'>"
                                                    + "<input type='text' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='" + ((obj_parametros_orden[10] == null) ? "" : obj_parametros_orden[10]) + "' style='text-align:center;border-width:0;width:60%;font-size: 11px;color:#292929;margin:0;' />"
                                                    + "<b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b></td>");
                                            out.print("<td align='center'>"
                                                    //                                                + "<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "&atb=toma2&ipo=" + obj_parametros_orden[0] + "' method='post' style='border-width:0;width:70px;margin:0;'>"
                                                    + "<input type='text' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='" + ((obj_parametros_orden[11] == null) ? "" : obj_parametros_orden[11]) + "' style='text-align:center;border-width:0;width:60%;font-size: 11px;color:#292929;margin:0;' />"
                                                    + "<b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b></td>");
                                            out.print("<td align='center'>"
                                                    //                                                + "<form action='Orden_trabajo?opc=10&iot=" + id_orden_trabajo + "&atb=toma3&ipo=" + obj_parametros_orden[0] + "' method='post' style='border-width:0;width:70px;margin:0;'>"
                                                    + "<input type='text' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='" + ((obj_parametros_orden[12] == null) ? "" : obj_parametros_orden[12]) + "' style='text-align:center;border-width:0;width:60%;font-size: 11px;color:#292929;margin:0;' />"
                                                    + "<b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b></td>");
                                        } else if (obj_parametros_orden[13].equals("Estado")) {
                                            out.print("<td align='center'><b><input type='radio' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='Cumple' " + ((obj_parametros_orden[10] == null) ? "" : ((obj_parametros_orden[10].equals("Cumple")) ? "checked" : "")) + "/> Cumple<br />"
                                                    + "<input type='radio' name='Txt_valor_1" + obj_parametros_orden[0] + "' value='No cumple' " + ((obj_parametros_orden[10] == null) ? "" : ((obj_parametros_orden[10].equals("No cumple")) ? "checked" : "")) + "/> No cumple</b></td>");
                                            out.print("<td align='center'><b><input type='radio' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='Cumple' " + ((obj_parametros_orden[11] == null) ? "" : ((obj_parametros_orden[11].equals("Cumple")) ? "checked" : "")) + "/> Cumple<br />"
                                                    + "<input type='radio' name='Txt_valor_2" + obj_parametros_orden[0] + "' value='No cumple' " + ((obj_parametros_orden[11] == null) ? "" : ((obj_parametros_orden[11].equals("No cumple")) ? "checked" : "")) + "/> No cumple</b></td>");
                                            out.print("<td align='center'><b><input type='radio' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='Cumple' " + ((obj_parametros_orden[12] == null) ? "" : ((obj_parametros_orden[12].equals("Cumple")) ? "checked" : "")) + "/> Cumple<br />"
                                                    + "<input type='radio' name='Txt_valor_3" + obj_parametros_orden[0] + "' value='No cumple' " + ((obj_parametros_orden[12] == null) ? "" : ((obj_parametros_orden[12].equals("No cumple")) ? "checked" : "")) + "/> No cumple</b></td>");
                                        }
                                    } else {
                                        out.print("<td align='center' colspan='3'><b class='rojo'>Parametro sin ejecutar</b></td>");
                                    }
                                } else {
                                    out.print("<td>" + ((obj_parametros_orden[10] == null) ? "" : obj_parametros_orden[10] + " <b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b>") + "</td>");
                                    out.print("<td>" + ((obj_parametros_orden[11] == null) ? "" : obj_parametros_orden[11] + " <b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b>") + "</td>");
                                    out.print("<td>" + ((obj_parametros_orden[12] == null) ? "" : obj_parametros_orden[12] + " <b title='" + obj_parametros_orden[4] + "'>" + obj_parametros_orden[5] + "</b>") + "</td>");
                                }
                                try {
                                    if (obj_parametros_orden[13].equals("Numero")) {
                                        double max = (Double) obj_parametros_orden[7] + (Double) obj_parametros_orden[8];
                                        double min = (Double) obj_parametros_orden[7] - (Double) obj_parametros_orden[9];
                                        double valor1 = Double.parseDouble(obj_parametros_orden[10].toString());
                                        double valor2 = Double.parseDouble(obj_parametros_orden[11].toString());
                                        double valor3 = Double.parseDouble(obj_parametros_orden[12].toString());
                                        if ((valor1 <= max && valor1 >= min) && (valor2 <= max && valor2 >= min) && (valor3 <= max && valor3 >= min)) {
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                        }
                                    } else if (obj_parametros_orden[13].equals("Estado")) {
                                        if (obj_parametros_orden[10].equals("Cumple") && obj_parametros_orden[11].equals("Cumple") && obj_parametros_orden[12].equals("Cumple")) {
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='negro'></b></td>");
                                            out.print("<td align='center'><b class='negro'>X</b></td>");
                                        }
                                    } else {
                                        out.print("<td align='center'><b class='negro'>X</b></td>");
                                        out.print("<td align='center'><b class='negro'></b></td>");
                                    }
                                } catch (Exception e) {
                                    out.print("<td align='center' colspan='2'><b class='negro'>No se puede definir</b></td>");
                                }
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</form>");
                    // </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="NOVEDADES">
                    out.print("<tr>");
                    if (!rol.equals("Consulta") && programacion == 1 && estado != 6) {
                        out.print("<td align='center'><a onclick='CrearNovedades(" + id_orden_trabajo + ");'><img src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registro de repuestos' /></a></td>");
//                        out.print("<form action='Orden_trabajo?opc=18&iot=" + id_orden_trabajo + "' method='post' id='Form_novedades'>");
//                        out.print("<form action='subirArchivos.jsp' enctype='multipart/form-data' method='post' id='Form_novedades' >");
//                        out.print("<input type='text' name='txtIdUser' id='txtIdUser' value='" + id_orden_trabajo + "'>");
                        out.print("<th colspan='10'>NOVEDADES<span id='Modulo_novedades'></span></th>");
//                        out.print("<td align='center'><div class='myButton'><input type='submit' value='' onclick='uploadFiles()'></div></td>");
                    } else {
                        out.print("<th colspan='12'>NOVEDADES<span id='Modulo_novedades'></span></th>");
                    }
                    out.print("</tr>");
                    lst_novedades_orden = jpacnod.Traer_novedades_orden(id_orden_trabajo);
                    if (lst_novedades_orden == null || lst_novedades_orden.isEmpty()) {
                        out.print("<tr>");
                        out.print("<td colspan='11' align='center'><b class='negro'>Sin novedades</b></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr>");
                        out.print("<td align='center'><b>Item</b></td>");
                        out.print("<td align='center' colspan='2'><b>Asunto</b></td>");
                        out.print("<td align='center' colspan='6'><b>Observación</b></td>");
                        out.print("<td align='center' colspan='1'><b>Evidencia</b></td>");
                        out.print("<td align='center' colspan='1'><b>Guardar</b></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_novedades_orden.size(); i++) {
                            Object[] obj_novedades = (Object[]) lst_novedades_orden.get(i);
                            out.print("<tr>");
                            out.print("<form action='subirArchivos.jsp' enctype='multipart/form-data' method='post' id='Form_novedades" + i + "' >");
                            out.print("<input type='hidden' name='idOt' id='txtIdUser' value='" + id_orden_trabajo + "'>");
                            out.print("<input type='hidden' name='idNov' id='txtIdUser' value='" + obj_novedades[0] + "'>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            if (!rol.equals("Consulta") && programacion == 1 && estado != 6) {
                                user = "" + rol + "/" + usuario + "";
                                out.print("<td align='center' colspan='2'>");
                                out.print("<input type='text' name='Txt_valor_1" + obj_novedades[0] + "' placeholder='Asunto...' value='" + ((obj_novedades[2] == null) ? "" : obj_novedades[2]) + "' onkeyup='javascript:this.value=this.value.toUpperCase();' style='text-align:center;border-width:0;width:95%;font-size: 11px;color:#292929;margin:0;' />"
                                        + "</td>");
                                String[] ObsxImg = {};
                                String imgNov = "", obs = "";
                                if (!obj_novedades[3].equals("")) {
                                    ObsxImg = obj_novedades[3].toString().split("////");
                                    try {
                                        obs = ObsxImg[0].toString();
                                    } catch (Exception e) {
                                        obs = "";
                                    }
                                    try {
                                        imgNov = ObsxImg[1].toString();
                                    } catch (Exception e) {
                                        imgNov = "--";
                                    }
                                } else {
                                    obs = "";
                                    imgNov = "--";
                                }
                                out.print("<td align='center' colspan='6' class='editor_nes'>"
                                        + "<textarea id='' name='Txt_valor_2" + obj_novedades[0] + "' placeholder='Observacion...' style='border-width:0;width:95%;height:22px;font-size: 11px;color:#292929;margin:0;height:30%;' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla_" + obj_novedades[0] + "(event)'>" + obs + "</textarea>"
                                        + "</td>");
                                out.print("<td style='text-align: center;'>");
                                if (imgNov.toString().equals("--")) {
                                    out.print("<div class='file-upload-container'>");
                                    out.print("<label class='file-upload-label' for='file-upload" + obj_novedades[0] + "'><img src='Interfaz/Contenido/Iconos/camera.png' style='cursor: pointer;'></label>");
                                    out.print("<input type='file' id='file-upload" + obj_novedades[0] + "' name='file-upload" + obj_novedades[0] + "' class='file-upload-input' multiple>");
                                    out.print("</div>");
                                } else {
                                    out.print("<div class='gallery'>");
                                    String[] multImg = imgNov.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    String delet = "";
                                    for (int j = 0; j < multImg.length; j++) {
                                        String nameFile = multImg[j].toString();
                                        out.print("<a href=\"photos/" + nameFile + "\" class=\"chocolat-image\">");
                                        out.print("<img src=\"photos/" + nameFile + "\" alt=\"Imagen " + j + "\" style=\"width: 70px; height: auto;\">");
                                        out.print("</a>");
                                        delet += "[" + nameFile + "]";
                                    }
                                    out.print("</div>");
                                    out.print("<input type='hidden' class='form-control' name='txtDelete' value='" + delet + "'>");
                                    out.print("<div class='file-upload-container'>");
                                    out.print("<label id='fielAPp' class='file-upload-label' for='file-upload" + obj_novedades[0] + "'><img src='Interfaz/Contenido/Iconos/camera.png' style='cursor: pointer;'></label>");
                                    out.print("<input type='file' id='file-upload" + obj_novedades[0] + "' name='file-upload" + obj_novedades[0] + "' class='file-upload-input' multiple>");
                                    out.print("</div>");
                                }

                                out.print("<td align='center'><div class='myButton'><input type='submit' value='' onclick='Form_novedades" + i + ".submit()'></div></td>");
                                out.print("</td>");

                            } else {
                                out.print("<td align='center' colspan='2'>" + obj_novedades[2] + "</td>");
//                                contenido = obj_novedades[3].toString().replace("<p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>", " ").replace("><img", "><div class='gallery gallery-fw'><img").replace("class=\"fr-fic fr-dib\"", "data-title=\"Image " + (i + 1) + "\" class=\"gallery-item regis fr-fic fr-dib\"").replace("src", "data-image").replace("style=\"width:", "style=\"height:300px;width:").replace("b\"></p>", "b\"></div></p>");
                                String[] ObsxImg = {};
                                String imgNov = "", obs = "";
                                if (!obj_novedades[3].equals("")) {
                                    ObsxImg = obj_novedades[3].toString().split("////");
                                    try {
                                        obs = ObsxImg[0].toString();
                                    } catch (Exception e) {
                                        obs = "";
                                    }
                                    try {
                                        imgNov = ObsxImg[1].toString();
                                    } catch (Exception e) {
                                        imgNov = "--";
                                    }
                                } else {
                                    obs = "";
                                    imgNov = "--";
                                }
                                out.print("<td align='center' colspan='6' class='editor_nes'>"
                                        + "<textarea id='' name='Txt_valor_2" + obj_novedades[0] + "' placeholder='Observacion...' style='border-width:0;width:95%;height:22px;font-size: 11px;color:#292929;margin:0;height:30%;' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla_" + obj_novedades[0] + "(event)'>" + obs + "</textarea>"
                                        + "</td>");
                                out.print("<td style='text-align: center;'>");
                                if (imgNov.toString().equals("--")) {
                                    out.print("<div class='file-upload-container'>");
                                    out.print("<label class='file-upload-label' for='file-upload" + obj_novedades[0] + "'><img src='Interfaz/Contenido/Iconos/camera.png' style='cursor: pointer;'></label>");
                                    out.print("<input type='file' id='file-upload" + obj_novedades[0] + "' name='file-upload" + obj_novedades[0] + "' class='file-upload-input' multiple>");
                                    out.print("</div>");
                                } else {
                                    out.print("<div class='gallery'>");
                                    String[] multImg = imgNov.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    String delet = "";
                                    for (int j = 0; j < multImg.length; j++) {
                                        String nameFile = multImg[j].toString();
                                        out.print("<a href=\"photos/" + nameFile + "\" class=\"chocolat-image\">");
                                        out.print("<img src=\"photos/" + nameFile + "\" alt=\"Imagen " + j + "\" style=\"width: 70px; height: auto;\">");
                                        out.print("</a>");
                                        delet += "[" + nameFile + "]";
                                    }
                                    out.print("</div>");
//                                    out.print("<input type='hidden' class='form-control' name='txtDelete' value='" + delet + "'>");
//                                    out.print("<div class='file-upload-container'>");
//                                    out.print("<label id='fielAPp' class='file-upload-label' for='file-upload" + obj_novedades[0] + "'><img src='Interfaz/Contenido/Iconos/camera.png' style='cursor: pointer;'></label>");
//                                    out.print("<input type='file' id='file-upload" + obj_novedades[0] + "' name='file-upload" + obj_novedades[0] + "' class='file-upload-input' multiple>");
//                                    out.print("</div>");
                                }
                                out.print("</td>");
                                out.print("<td colspan='8'> </td>");

                            }
                            out.print("</form>");
                            out.print("</tr>");
                        }
                    }
                    out.print("<script> "
                            + "            $(document).ready(function () { "
                            + "                $('.gallery').Chocolat({ "
                            + "                    imageSize: 'contain', "
                            + "                    loop: true, "
                            + "                    fullscreen: false, "
                            + "                    enableZoom: true, "
                            + "                    enableClose: true "
                            + "                }); "
                            + "            }); "
                            + "        </script>");

//
//                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="RESPONSABLES">
                    out.print("<tr>");
                    out.print("<th colspan='11'>RESPONSABLES</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2' ><b>PROGRAMADOR</b></td>");
                    if (programacion == 1) {
                        out.print("<td align='center'>" + obj_orden_trabajo[6] + "<br /><b class='negro'>Programación realizada</b></td>");
                    } else {
                        out.print("<td align='center'>" + obj_orden_trabajo[6] + "<br /><a href='#' class='verde' onclick=\"CerrarProgramacion(" + id_orden_trabajo + "," + obj_orden_trabajo[2] + ")\">ENVIAR A EJECUCIÓN</a></td>");
                    }
                    out.print("<td align='center'><b>EJECUTOR</b></td>");
                    if (estado <= 3 && programacion == 1) {
                        if (rol.equals("Tecnico") || rol.equals("Administrador")) {
                            out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[8] + "<br /><a class='verde' href='#' onclick='CerrarEjecucion(" + obj_orden_trabajo[0] + "," + id_equipo + ")'>Guardar y enviar a revisión</a>");
                            if (rol.equals("Administrador")) {
                                out.print("<hr /><a class='rojo' href='#' onclick='VolverProgramacion(" + obj_orden_trabajo[0] + ")'>Volver a programación</a>");
                            }
                        } else {
                            out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[8] + "<br /><b class='negro'>PENDIENTE EJECUTAR</b>");
                            if (rol.equals("Jefe_MTI") || rol.equals("Asistente_MTI")) {
                                out.print("<hr /><a class='rojo' href='#' onclick='VolverProgramacion(" + obj_orden_trabajo[0] + ")'>Volver a programación</a>");
                            }
                        }
                    } else {
                        out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[8] + "<br /><b class='negro'>PENDIENTE EJECUTAR</b>");
                    }
                    out.print("</td>");
                    out.print("<td align='center' ><b>REVISOR</b></td>");
                    if (estado <= 3) {
                        out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[10] + "<br /><b class='negro'>PENDIENTE</b></td>");
                    } else if (estado == 4) {
                        if (rol.equals("Tecnico_Encargado") || rol.equals("Administrador") || rol.equals("Jefe_MTI") || rol.equals("Asistente_MTI")) {
                            out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[10] + "<br /><a class='verde' href='#' onclick='CerrarRevision(" + obj_orden_trabajo[0] + "," + id_equipo + ")'>Guardar revisión</a>");
                            out.print("<hr /><a class='rojo' href='#' onclick='VolverEjecucion(" + obj_orden_trabajo[0] + ")'>Volver a ejecución</a></td>");
                        } else {
                            out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[10] + "<br /><b class='negro'>PENDIENTE</b></td>");
                        }
                    } else {
                        out.print("<td align='center' colspan='3' >" + obj_orden_trabajo[10] + "<br /><b class='negro'>Revisado</b>");
                        if ((rol.equals("Administrador") || rol.equals("Jefe_MTI") || rol.equals("Asistente_MTI")) && Integer.parseInt(obj_orden_trabajo[14].toString()) == 1) {
                            out.print("<hr /><a class='rojo' href='#' onclick='VolverEjecucion(" + obj_orden_trabajo[0] + ")'>Volver a ejecución</a>");
                        }
                        out.print("</td>");
                    }
                    out.print("</tr>");
                    // </editor-fold>
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    // <editor-fold defaultstate="collapsed" desc="REGISTRO ACTIVIDADES">
                    int id_actividad = 0;
                    int cont_actividad = 0;
                    lst_actividades = jpacatv.Traer_actividades_tipo_equipo((Integer) obj_equipo[6]);
                    lst_actividades_orden = jpacaot.Traer_actividades_id_orden(id_orden_trabajo);
                    out.print("<div class='sweet-local' id='Form_registro_actividades' style='opacity: 1.03; display: none;'>");
                    out.print("<fieldset class='popup_local' style='width:80%;height:600px;position;top: 2%;left:5%;overflow:scroll;'>");
                    out.print("<form action='Orden_trabajo?opc=5' method='post' name='f1' id='f1' >");
                    out.print("<div align='right'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' onclick='Form_registro_actividades_cerrar()' title='Cerrar actividad' /></div>");
                    out.print("<h3>Asignación de actividades a la OT</h3>");
                    out.print("<br /><div align='right'>");
                    out.print("<a href='javascript:seleccionar_todo()'>Marcar todos</a> | <a href='javascript:deseleccionar_todo()'>ninguno</a> ");
                    out.print("<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar actividad' onchange='javascript:this.value=this.value.toUpperCase();' />");
                    out.print("<input type='submit' value='Asignar'/></div><br />");
                    if (lst_actividades == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>Sin actividades para asignar</b>");
                        out.print("</center>");
                    } else {
                        out.print("<input type='hidden' id='Id_orden_trabajo' name='Id_orden_trabajo' value='" + id_orden_trabajo + "' />");
                        out.print("<input type='hidden' id='Cantidad_actividades' name='Cantidad_actividades' value='" + lst_actividades.size() + "' />");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table id='resultados' style='width:100%' class='table'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th colspan='2'>Actividad</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_actividades.size(); i++) {
                            Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                            if ((Integer) obj_actividades[4] == 1) {
                                out.print("<tr>");
                                out.print("<td align='center'>");
                                if (lst_actividades_orden != null) {
                                    for (int j = 0; j < lst_actividades_orden.size(); j++) {
                                        Object[] obj_actividades_orden = (Object[]) lst_actividades_orden.get(j);
                                        if (Integer.parseInt(obj_actividades[0].toString()) == Integer.parseInt(obj_actividades_orden[3].toString())) {
                                            id_actividad = Integer.parseInt(obj_actividades_orden[3].toString());
                                        }
                                    }
                                    if ((Integer) obj_actividades[0] == id_actividad) {
                                        //out.print("<input type='checkbox' disabled='true' checked name='Ckb_serial[" + i + "]' value='" + obj_actividades[0] + "' />");
                                        out.print("<img src='Interfaz/Contenido/Iconos/Selected.png' width='12px' height='12px' alt='edit' title='Activar actividad' />");
                                        cont_actividad++;
                                    } else {
                                        out.print("<input type='checkbox' name='Ckb_actividad[" + i + "]' value='" + obj_actividades[0] + "' />");
                                    }
                                } else {
                                    out.print("<input type='checkbox' name='Ckb_actividad[" + i + "]' value='" + obj_actividades[0] + "' />");
                                }
                                out.print("</td>");
                                if (cont_actividad > 0) {
                                    out.print("<td>" + obj_actividades[1] + "</td>");
                                    //out.print("<td align='center'><a href='#'  onclick='ActivarActividad(" + obj_actividades[0] + "," + id_tipo_equipo + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Activar actividad' /></a></td>");
                                    out.print("<td align='center'><a onclick='QuitarActividad(" + obj_actividades[0] + "," + obj_orden_trabajo[0] + ")' ><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Quitar actividad' /></a></td>");
                                    cont_actividad = 0;
                                } else {
                                    out.print("<td colspan='2'>" + obj_actividades[1] + "</td>");
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="REGISTRO PARAMETROS">
                    //REGISTRO PARAMETROS
                    int id_parametro = 0;
                    int cont_parametro = 0;
                    lst_parametros = jpacprm.Traer_parametros_tipo_equipo((Integer) obj_equipo[6]);
                    lst_parametros_orden = jpacpod.Traer_parametros_orden((Integer) obj_orden_trabajo[0]);
                    out.print("<div class='sweet-local' id='Form_registro_parametros' style='opacity: 1.03; display: none;'>");
                    out.print("<fieldset class='popup_local' style='width:80%;height:600px;position;top: 2%;left:5%;overflow:scroll;'>");
                    out.print("<div align='right'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' onclick='Form_registro_parametros_cerrar()' title='Cerrar parametros' /></div>");
                    out.print("<h3>Asignación de parámetros a la OT</h3>");
                    if (lst_parametros == null) {
                        out.print("<center>");
                        out.print("<br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>Sin parámetros para asignar</b>");
                        out.print("</center>");
                    } else {
                        out.print("<br /><input id='Txt_filtro_2' type='text' onkeyup='Filtrar_2()' placeholder='Buscar parametro' onchange='javascript:this.value=this.value.toUpperCase();'/>  ");
                        out.print("<div align='left' id='NavPosicion_2'></div>");
                        out.print("<table id='resultados_2' style='width:100%' class='table'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>parámetros</th>");
                        out.print("<th>Instrumento</th>");
                        out.print("<th>Unidad medida</th>");
                        out.print("<th colspan='2'>Validación</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            out.print("<tr>");
                            out.print("<form action='Orden_trabajo?opc=6' method='post' id='Form_" + i + "' name='Form_" + i + "'>");
                            out.print("<td align='center'>");
                            out.print("<input type='hidden' id='Id_orden_trabajo' name='Id_orden_trabajo' value='" + id_orden_trabajo + "'/>");
                            out.print("<input type='hidden' id='Id_parametro' name='Id_parametro' value='" + obj_parametros[0] + "'/>");
                            if (obj_parametros[12].toString().equals("Numero")) {
                                out.print("<input type='hidden' id='Txt_parametro_max' name='Txt_parametro_max' value='" + obj_parametros[10] + "'/>");
                                out.print("<input type='hidden' id='Txt_parametro_min' name='Txt_parametro_min' value='" + obj_parametros[11] + "'/>");
                            } else if (obj_parametros[12].toString().equals("Estado") || obj_parametros[12].toString().equals("Caracter")) {
                                out.print("<input type='hidden' id='Txt_parametro' name='Txt_parametro' value='0'/>");
                                out.print("<input type='hidden' id='Txt_parametro_max' name='Txt_parametro_max' value='0'/>");
                                out.print("<input type='hidden' id='Txt_parametro_min' name='Txt_parametro_min' value='0'/>");
                            }
                            if ((Integer) obj_parametros[14] == 1) {
                                if (lst_parametros_orden != null) {
                                    for (int j = 0; j < lst_parametros_orden.size(); j++) {
                                        Object[] obj_parametros_orden = (Object[]) lst_parametros_orden.get(j);
                                        if (Integer.parseInt(obj_parametros[0].toString()) == Integer.parseInt(obj_parametros_orden[1].toString())) {
                                            id_parametro = Integer.parseInt(obj_parametros_orden[1].toString());
                                        }
                                    }
                                    if ((Integer) obj_parametros[0] == id_parametro) {
                                        out.print("<img src='Interfaz/Contenido/Iconos/Selected.png' width='12px' height='12px' alt='edit' title='Activar actividad' />");
                                        cont_parametro++;
                                    } else {
                                        out.print("<a href='javascript:document.Form_" + i + ".submit()'>Asignar <b>" + (i + 1) + "</b></a>");
                                    }
                                } else {
                                    out.print("<a href='javascript:document.Form_" + i + ".submit()'>Asignar <b>" + (i + 1) + "</b></a>");
                                }
                                out.print("</td>");
                                if (cont_parametro > 0) {
                                    out.print("<td >" + obj_parametros[1] + "</td>");
                                    out.print("<td>" + obj_parametros[5] + "</td>");
                                    out.print("<td>" + obj_parametros[7] + " / " + obj_parametros[8] + "</td>");
                                    if (obj_parametros[12].toString().equals("Numero")) {
                                        if ((Double) obj_parametros[9] == 0) {
                                            for (int j = 0; j < lst_parametros_orden.size(); j++) {
                                                Object[] obj_parametros_orden = (Object[]) lst_parametros_orden.get(j);
                                                if (Integer.parseInt(obj_parametros[0].toString()) == Integer.parseInt(obj_parametros_orden[1].toString())) {
                                                    parametro = Double.parseDouble(obj_parametros_orden[7].toString());
                                                }
                                            }
                                            out.print("<td><b>MIN (</b>" + (Double) obj_parametros[11] + "<b>)-(</b><b class='negro'><input style='text-align:center;border-width:0;width:23px;font-size: 11px;color:#292929;' type='text' id='Txt_parametro' name='Txt_parametro' value='" + parametro + "' /> </b><b>)-(</b>" + (Double) obj_parametros[10] + "<b>) MAX</b></td>");
                                        } else {
                                            out.print("<td><b>MIN (</b>" + ((Double) obj_parametros[9] - (Double) obj_parametros[11]) + "<b>)-(</b><b class='negro'>" + obj_parametros[9] + "</b><b>)-(</b>" + ((Double) obj_parametros[9] + (Double) obj_parametros[10]) + "<b>) MAX</b></td>");
                                            out.print("<input type='hidden' id='Txt_parametro' name='Txt_parametro' value='" + obj_parametros[9] + "'/>");
                                        }
                                    } else if (obj_parametros[12].toString().equals("Estado")) {
                                        out.print("<td align='center'><b class='negro'> Cumple </b></td>");
                                    } else if (obj_parametros[12].toString().equals("Caracter")) {
                                        out.print("<td align='center'><b class='negro'> Campo </b></td>");
                                    }
                                    out.print("<td align='center'><a onclick='QuitarParametro(" + obj_parametros[0] + "," + obj_orden_trabajo[0] + ")' ><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Quitar parámetro' /></a></td>");
                                    cont_parametro = 0;
                                } else {
                                    out.print("<td >" + obj_parametros[1] + "</td>");
                                    out.print("<td>" + obj_parametros[5] + "</td>");
                                    out.print("<td>" + obj_parametros[7] + " / " + obj_parametros[8] + "</td>");
                                    if (obj_parametros[12].toString().equals("Numero")) {
                                        if ((Double) obj_parametros[9] == 0) {
                                            out.print("<td colspan='2'><b>MIN (</b>" + (Double) obj_parametros[11] + "<b>)-(</b><b class='negro'><input style='text-align:center;border-width:0;width:23px;font-size: 11px;color:#292929;margin:0;' type='text' id='Txt_parametro' name='Txt_parametro' value='O.T' /></b><b>)-(</b>" + (Double) obj_parametros[10] + "<b>) MAX</b></td>");
                                        } else {
                                            out.print("<td colspan='2'><b>MIN (</b>" + ((Double) obj_parametros[9] - (Double) obj_parametros[11]) + "<b>)-(</b><b class='negro'>" + obj_parametros[9] + "</b><b>)-(</b>" + ((Double) obj_parametros[9] + (Double) obj_parametros[10]) + "<b>) MAX</b></td>");
                                            out.print("<input type='hidden' id='Txt_parametro' name='Txt_parametro' value='" + obj_parametros[9] + "'/>");
                                        }
                                    } else if (obj_parametros[12].toString().equals("Estado")) {
                                        out.print("<td align='center' colspan='2'><b class='negro'> Cumple </b></td>");
                                    } else if (obj_parametros[12].toString().equals("Caracter")) {
                                        out.print("<td align='center' colspan='2'><b class='negro'> Campo </b></td>");
                                    }
                                }
                            }
                            out.print("</form>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager_2 = new Pager_2('resultados_2', 10);");
                    out.print("pager_2.init();");
                    out.print("pager_2.showPageNav('pager_2','NavPosicion_2');");
                    out.print("pager_2.showPage(1);");
                    out.print("</script>");
                    out.print("</fieldset>");
                    // </editor-fold>
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="HISTORIAL ORDEN DE TRABAJO GENERAL">
                else if (pageContext.getRequest().getAttribute("Orden_trabajo").toString().equals("Historial_orden_general")) {
                    id_orden_trabajo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden_trabajo").toString());
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        filtro = "0";
                    }
                    lst_ordenes_trabajo = jpacotb.Traer_ordenes_trabajo(filtro);
                    out.print("<div id='content_sin'>");
                    if (lst_ordenes_trabajo == null) {
                        out.print("<h3>Ordenes de trabajo en proceso</h3>");
                        out.print("<div style='float:left'>");
                        out.print("<form action='Orden_trabajo?opc=19&iot=0' method='post' id='form1'>");
                        out.print("<input type='radio' name='fto' value='1' onclick='form1.submit();' " + ((filtro.equals("1")) ? "checked" : "") + "/> OT En programación ");
                        out.print("<input type='radio' name='fto' value='3' onclick='form1.submit();' " + ((filtro.equals("3")) ? "checked" : "") + "/> OT Sin ejecutar ");
                        out.print("<input type='radio' name='fto' value='4' onclick='form1.submit();' " + ((filtro.equals("4")) ? "checked" : "") + "/> OT Sin revisar ");
                        out.print("<input type='radio' name='fto' value='5' onclick='form1.submit();' " + ((filtro.equals("5")) ? "checked" : "") + "/> OT Sin cerrar ");
                        out.print("<input type='radio' name='fto' value='6' onclick='form1.submit();' " + ((filtro.equals("6")) ? "checked" : "") + "/> OT Cerradas ");
                        out.print("<input type='radio' name='fto' value='0' onclick='form1.submit();' " + ((filtro.equals("0")) ? "checked" : "") + "/> Todas las OT ");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de OT registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Ordenes de trabajo en proceso");
                        out.print("<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        out.print("<div style='float:left'>");
                        out.print("<form action='Orden_trabajo?opc=19&iot=0' method='post' id='form1'>");
                        out.print("<input type='radio' name='fto' value='1' onclick='form1.submit();' " + ((filtro.equals("1")) ? "checked" : "") + "/> OT En programación ");
                        out.print("<input type='radio' name='fto' value='3' onclick='form1.submit();' " + ((filtro.equals("3")) ? "checked" : "") + "/> OT Sin ejecutar ");
                        out.print("<input type='radio' name='fto' value='4' onclick='form1.submit();' " + ((filtro.equals("4")) ? "checked" : "") + "/> OT Sin revisar ");
                        out.print("<input type='radio' name='fto' value='5' onclick='form1.submit();' " + ((filtro.equals("5")) ? "checked" : "") + "/> OT Sin cerrar ");
                        out.print("<input type='radio' name='fto' value='6' onclick='form1.submit();' " + ((filtro.equals("6")) ? "checked" : "") + "/> OT Cerradas ");
                        out.print("<input type='radio' name='fto' value='0' onclick='form1.submit();' " + ((filtro.equals("0")) ? "checked" : "") + "/> Todas las OT ");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<br /><br /><div id='NavPosicion'></div>");
                        //<editor-fold defaultstate="collapsed" desc="OT PROCESO IMAGEN">
                        out.print("<table class='table' id='resultados' style='width:100%;'>");
                        out.print("<tr>");
                        //out.print("<th colspan='10'>Order de trabajo</th>");
                        out.print("<th colspan='11'>Ordenes de trabajo</th>");//
                        out.print("</tr>");
                        for (int i = 0; i < lst_ordenes_trabajo.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes_trabajo.get(i);
                            out.print("<tr>");
                            //out.print("<td align='center' colspan='10'>"
                            lst_actividades_orden = jpacaot.Traer_actividades_id_orden((Integer) obj_ordenes[0]);
                            lst_parametros_orden = jpacpod.Traer_parametros_orden((Integer) obj_ordenes[0]);
                            int estado = Integer.parseInt(obj_ordenes[14].toString());
                            int programado = Integer.parseInt(obj_ordenes[15].toString());
                            lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
                            try {
                                //out.print("<td colspan='11' " + ((Integer.parseInt(obj_ordenes[19].toString()) >= 3 && obj_ordenes[9].equals("0000-00-00 00:00")) ? " style='BACKGROUND-COLOR:"+((filtro.equals("3") && (Integer.parseInt(obj_ordenes[20].toString()) > 0 ))?"#c8ffee":"#FFEAD9")+"'" : " ") + ">");
                                out.print("<td colspan='11' " + ((Integer.parseInt(obj_ordenes[19].toString()) >= 3 && estado <= 3 && programado == 1) ? " style='BACKGROUND-COLOR:" + (((filtro.equals("3") || filtro.equals("0")) && (Integer.parseInt(obj_ordenes[20].toString()) > 0)) ? "#caf2ff" : "#FFEAD9") + "'" : " ") + ">");
                            } catch (Exception e) {
                                out.print("<td colspan='11' >");
                            }
                            out.print("" + ((obj_ordenes[8].equals(usuario) || obj_ordenes[10].equals(usuario)) ? "<div style='float:right'><img src='Interfaz/Contenido/Iconos/Clavo.png' alt='logo'/></div>" : "") + ""
                                    + "<div style='width:100px;float:left' align='left'><h2 style='margin:0px 0px 0px 0px'><b>O.T </b><b class='negro'> " + obj_ordenes[1] + "</b></h2><b class='negro'>" + obj_ordenes[17] + "</b></div>"
                                    + "<div style='float:left;width:300px;'><a " + ((estado <= 3) ? "onclick='CambiarResponsables(" + obj_ordenes[0] + "," + obj_ordenes[2] + ");' ><u><b>Programador : </b>" + obj_ordenes[6] + "</u></a>" : " ><b>Programador : </b>" + obj_ordenes[6] + "</a>") + "<br />"
                                    + "<b>Técnico Ejecutor : </b>" + obj_ordenes[8] + "<br />"
                                    + "<b>Técnico Revisor : </b>" + obj_ordenes[10] + "<br />"
                                    + "<b>Horometro</b><b class='negro'> " + obj_ordenes[4] + "</b><br />"
                                    + "<b>Equipo</b><b class='negro'> " + obj_ordenes[3] + "</b></div>"
                                    + "<a href='Orden_trabajo?opc=3&iot=" + obj_ordenes[0] + "&isg=1'>");
//                                    jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 2);
                            if (estado == 1 || estado == 2 || estado == 3) {
                                if (programado == 1) {
                                    if (lst_parametros_orden != null && lst_actividades_orden != null) {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3.png' alt='logo' />");
                                    } else if (lst_parametros_orden == null && lst_actividades_orden != null) {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2.png' alt='logo' />");
                                    } else if (lst_parametros_orden != null && lst_actividades_orden == null) {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23.png' alt='logo' />");
                                    } else {
                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1.png' alt='logo' />");
                                    }
                                } else if (lst_parametros_orden != null && lst_actividades_orden != null) {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3C.png' alt='logo' />");
                                } else if (lst_parametros_orden == null && lst_actividades_orden != null) {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2C.png' alt='logo' />");
                                } else if (lst_parametros_orden != null && lst_actividades_orden == null) {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23C.png' alt='logo' />");
                                } else {
                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1C.png' alt='logo' />");
                                }
                            } else if (estado == 4) {
                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_4" + ((lst_novedades_orden != null) ? "_nov" : "") + ".png' alt='logo' />");
                            } else if (estado == 5) {
                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_5" + ((lst_novedades_orden != null) ? "_nov" : "") + ".png' alt='logo' />");
                            } else if (estado == 6) {
                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_6" + ((lst_novedades_orden != null) ? "_nov" : "") + ".png' alt='logo' />");
                            }
////                            <editor-fold defaultstate="collapsed" desc="COMENTARIO OLD PROGRESS BAR">
////                            if (lst_actividades_orden != null && lst_parametros_orden == null) {
////                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
////                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2.png' alt='logo' />");
////                                   jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 2);
////                                } else {
////                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_2.png' alt='logo' />");
////                                   jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 2);
////                                }
////                            } else if (lst_parametros_orden != null && lst_actividades_orden == null) {
////                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
////                                    if ((Integer) obj_ordenes[15] == 1) {
////                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23.png' alt='logo' />");
////                                    } else {
////                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23C.png' alt='logo' />");
////                                    }
////                                } else if ((Integer) obj_ordenes[15] == 1) {
////                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23.png' alt='logo' />");
////                                } else {
////                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_23C.png' alt='logo' />");
////                                }
////                            } else if (lst_parametros_orden != null && lst_actividades_orden != null) {
////                                if (!obj_ordenes[9].equals("0000-00-00 00:00")) {
////                                    if (obj_ordenes[11].equals("0000-00-00 00:00")) {
////                                        lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
////                                        if (lst_novedades_orden == null) {
////                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_4.png' alt='logo' />");
////                                           jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 4);
////                                        } else {
////                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_4_nov.png' alt='logo' />");
////                                           jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 4);
////                                        }
////                                    } else if ((Integer) obj_ordenes[14] != 6) {
////                                        lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
////                                        if (lst_novedades_orden == null) {
////                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_5.png' alt='logo' />");
////                                           jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 5);
////                                        } else {
////                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_5_nov.png' alt='logo' />");
////                                           jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 5);
////                                        }
////                                    } else {
////                                        lst_novedades_orden = jpacnod.Traer_novedades_orden((Integer) obj_ordenes[0]);
////                                        if (lst_novedades_orden == null) {
////                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_6.png' alt='logo' />");
////                                           jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 6);
////                                        } else {
////                                            out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_6_nov.png' alt='logo' />");
////                                           jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 6);
////                                        }
////                                    }
////                                } else if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
////                                    if ((Integer) obj_ordenes[15] == 1) {
////                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3.png' alt='logo' />");
////                                       jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 3);
////                                    } else {
////                                        out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3C.png' alt='logo' />");
////                                       jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 3);
////                                    }
////                                } else if ((Integer) obj_ordenes[15] == 1) {
////                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3.png' alt='logo' />");
////                                       jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 3);
////                                } else {
////                                    out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_3C.png' alt='logo' />");
////                                       jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 3);
////                                }
////                            } else if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
////                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1.png' alt='logo'/>");
////                                       jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 1);
////                            } else {
////                                out.print("<img style='height:95px;width:763px;' src='Interfaz/Contenido/Progress_bar/Progres_1.png' alt='logo'/>");
////                                       jpacotb.Nuevo_cambio_estado(Integer.parseInt(obj_ordenes[0].toString()), 1);
////                            }
////</editor-fold>
                            out.print("</a>");
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        //</editor-fold>
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
                // </editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_orden_trabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();

    }
}
