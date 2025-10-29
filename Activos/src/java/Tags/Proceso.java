package Tags;

import Controladores.ActivoJpaController;
import Controladores.ProcesoJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Proceso extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            HttpSession sesion = pageContext.getSession();
            String nombreArea = (String) sesion.getAttribute("Area");
            String rol = (String) sesion.getAttribute("NombreRol");
            ProcesoJpaController jpa_procesos = new ProcesoJpaController();
            ActivoJpaController jpa_activos = new ActivoJpaController();
            List lst_procesos, lst_activos, lst_proceso = null;
            String cadena = "";
            //</editor-fold>
            if (pageContext.getRequest().getAttribute("Proceso").equals("ModuloActivosProceso")) {
                int idProceso = Integer.parseInt(pageContext.getRequest().getAttribute("idProceso").toString());
                lst_activos = jpa_activos.consultarActivoEstado(2);
                lst_procesos = jpa_procesos.consultarProcesos();
                if (idProceso == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FORMULARIO PROCESO">
                    out.print("<div class='sweet-local' tabindex='-1' id='emergente1' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style=' width:65%; height:60%; position: absolute;top:20%; left:15%;text-align:left '>");
                    out.print("<div style='float:right;'><a href='Proceso?opc=1&idProceso=0' style='color:black;'><span class='fas fa-times fa-size_super_small' title='Volver al inicio'/></span></a></div>");
                    out.print("<legend>Registrar Proceso</legend>");
                    out.print("<div style='overflow:scroll; width:100%; height:88%;'>");
                    out.print("<div style='width:200px; float:left; padding:5px 15px 0px 5px'>");
                    out.print("<form action='Proceso?opc=2' method='post'>");
                    out.print("<b>Código :</b>");
                    out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script>");
                    out.print("<b>Fecha Inicio</b>");
                    out.print("<input type='text' name='Txt_fecha_incio' id='start' placeholder='Fecha' autocomplete='off' title='Fecha de Inicio' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                    out.print("<b>Nombre :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de Área' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                    out.print("<input type='submit' value='Registrar' />");
                    out.print("</div>");
                    out.print("<div style='width:auto; float:right;'>");
                    out.print("<b>Descripción :</b>");
                    out.print("<textarea name='Txt_descripcion' id='small_descripcion-id' style='width:450px; height:200px;'><div contenteditable='true'><p>*</p></div></textarea>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset></div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                    lst_proceso = jpa_procesos.consultarProceso(idProceso);
                    Object[] obj_proceso = (Object[]) lst_proceso.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='emergente' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style=' width:65%; height:50%; position: absolute;top:20%; left:15%;text-align:left '>");
                    out.print("<div style='float:right;'><a href='Proceso?opc=1&idProceso=0' style='color:black;'><span class='fas fa-times fa-size_super_small' title='Volver al inicio'/></span></a></div>");
                    out.print("<legend>Modificar Proceso</legend>");
                    out.print("<div style='overflow:scroll; width:100%; height:88%;'>");
                    out.print("<div style='width:200px; float:left ; padding:5px 15px 0px 5px'>");
                    out.print("<form action='Proceso?opc=3' method='post'>");
                    out.print("<b>Código:</b>");
                    out.print("<input type='text' name='Txt_codigoM' id='Txt_codigoM' value='" + obj_proceso[1] + "'  placeholder='Código' title='Código' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigoM');val1.add(Validate.Presence);</script>");
                    out.print("<b>Fecha Inicio:</b>");
                    out.print("<input type='text' name='Txt_fecha_incioM' id='start' value='" + obj_proceso[2] + "'  placeholder='Fecha' autocomplete='off' title='Fecha de Inicio' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                    out.print("<b>Nombre:</b>");
                    out.print("<input type='text' name='Txt_nombreM' id='Txt_nombreM' value='" + obj_proceso[4] + "'  placeholder='Nombre' title='Nombre de Área' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombreM');val1.add(Validate.Presence);</script>");
                    out.print("<input type='hidden' name='idProceso' value='" + obj_proceso[0] + "' />");
                    out.print("<input type='submit' value='Modificar' />");
                    out.print("</div>");
                    out.print("<div style='width:auto; float:right;'>");
                    out.print("<b>Descripción :</b>");
                    out.print("<textarea name='Txt_descripcionM' id='small_descripcion-id' style='width:500px; height:200px;'>" + (((obj_proceso[5]) == null) ? "<div contenteditable='true'><p style='margin:0px;'>*<p></div>" : obj_proceso[5].toString().replace("<div>", "<div contenteditable='true'>")) + "</textarea>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset></div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="USAR ACTIVOS DADOS DE BAJA">
                if (lst_activos != null) {
                    out.print("<div class='sweet-local' tabindex='-1' id='emergente2' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='emergente' style='width:35%; height:35%; position: absolute;top:20%; left:30%; padding:11px; overflow:scroll'>");
                    out.print("<a href='Proceso?opc=1&idProceso=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio' style='float:right;'/></a>");
                    out.print("<form action='Proceso?opc=5' method='post' name='form1'>");
                    out.print("<br><input type='submit' value='Agregar' /></center>");
                    out.print("<div align='left' id='NavPosicion2'></div>");
                    out.print("<center><table id='resultados_2' class='table' style='width:80%;'>");
                    out.print("<tr><th class='th2' colspan='3'>Activos dados de Baja :</th><tr>");
                    int contador = 0;
                    for (int i = 0; i < lst_activos.size(); i++) {
                        Object[] obj_activos = (Object[]) lst_activos.get(i);
                        if (contador == 0) {
                            out.print("<tr>");
                        }
                        out.print("<td style='width:15%;'><input type='checkbox' name='Cbx_activo" + i + "' id='Cbx_activo" + i + "'value='" + obj_activos[0] + "/" + obj_activos[1] + "/" + obj_activos[5] + "' onclick='recibeActivo(this)'><b>" + obj_activos[1] + "</b><br><b>Nombre:</b>" + obj_activos[8] + "<br><b>Proceso:</b>" + obj_activos[5] + "<br></td>");
                        contador++;

                        if (contador == 3) {
                            out.print("</tr>");
                            contador = 0;
                        }
                    }
                    out.print("</table>");
                    out.print("<input type='hidden' name='idProcesoM' id='idProcesoM' value='" + idProceso + "' />");
                    out.print("<input type='hidden' name='activoUsado' id='activoUsado'  />");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager3 = new Pager3('resultados_2',5);");
                    out.print("pager3.init();");
                    out.print("pager3.showPageNav3('pager3','NavPosicion2');");
                    out.print("pager3.showPage(1);");
                    out.print("</script>");
                    out.print("</form>");
                    out.print("</fieldset></div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVOS EN PROCESO">
                out.print("<div id='container'>");
                out.print("<div style='float: right;'><input id='Txt_filtro' type='text' onkeyup='Filtrartodo()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                if (!rol.equals("CONSULTA")) {
                    out.print("<h3><a  href='#' onclick=\"mostrar(\'1\')\" style='color:black;'><span class='fas fa-plus fa-size_super_small' title='Registro de Proyectos' /></span></a>&nbsp;&nbsp;Proyecto</h3>");
                } else {
                    out.print("<h3>Procesos</h3><br>");
                }
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados0' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th style='width:1%'>#</th>");
                out.print("<th style='width:5%'>Código</th>");
                out.print("<th style='width:10%'>Fecha Inicio</th>");
                out.print("<th style='width:10%'>Nombre</th>");
                out.print("<th style='width:55%'>Descripción</th>");
                out.print("<th style='width:10%'>Estado</th>");
                if (!rol.equals("CONSULTA")) {
                    out.print("<th style='width:5%'>Modificar</th>");
                    out.print("<th style='width:9%'>AT baja</th>");
                }
                out.print("</tr>");
                if (lst_procesos == null) {
                    out.print("<tr><td align='center' colspan='7'>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<b>No se encontraron Procesos</b>");
                    out.print("</td></tr>");
                } else {
                    for (int i = 0; i < lst_procesos.size(); i++) {
                        Object[] obj_procesos = (Object[]) lst_procesos.get(i);
                        if (obj_procesos[8].equals(nombreArea) || rol.equals("CONSULTA") || nombreArea.equals("MANTENIMIENTO") || rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                            out.print("<tr>");
                            int ara = Integer.parseInt(obj_procesos[7].toString());
                            out.print("<td align='center'><b>N°:</b>" + obj_procesos[0] + "</td>");
                            if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO")) {
                                out.print("<td align='center'><b>" + obj_procesos[1] + "</b>"
                                        + "<br><hr><b>Area: </b><br>" + obj_procesos[8] + "</td>");
                            } else {
                                out.print("<td align='center'><b>" + obj_procesos[1] + "</b></td>");
                            }
                            out.print("<td align='center'>" + obj_procesos[2] + "</td>");
                            out.print("<td align='center'>" + obj_procesos[4] + "</td>");
                            out.print("<td valign='top'>");
                            if (obj_procesos[5] == null || obj_procesos[5] == "") {
                                out.print("NINGUNA");
                            } else if (obj_procesos[5].toString().contains("<img")) {
                                String[] arg_img = obj_procesos[5].toString().split("<img");
                                for (int k = 0; k < arg_img.length; k++) {
                                    if (k == 0) {
                                        cadena = arg_img[k];
                                    } else {
                                        cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_" + obj_procesos[0] + "_" + k + "' onclick=\"Abrir_img_pro('Img_" + obj_procesos[0] + "_" + k + "');\" " + arg_img[k];
                                    }
                                }
                                out.print(cadena);
                            } else {
                                out.print(obj_procesos[5]);
                            }
                            if ((!obj_procesos[10].toString().equals("N/A") && !(obj_procesos[10] == null)) || (!obj_procesos[9].toString().equals("N/A") && !(obj_procesos[9] == null))) {
                                out.print("<hr />");
                                if (!obj_procesos[10].toString().equals("N/A") && !(obj_procesos[10] == null)) {
                                    out.print("<b>Justificación no Finalizado: </b>" + obj_procesos[10] + "<br>");
                                }
                                if (!obj_procesos[9].toString().equals("N/A") && !(obj_procesos[9] == null)) {
                                    out.print("<b>Activos Tomados: </b>" + obj_procesos[9].toString().replace("][", "<br>").replace("]", "").replace("[", "") + "");
                                }
                            }
                            out.print("</td>");
                            //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                            if (Integer.parseInt(obj_procesos[6].toString()) == 2) {
                                if (rol.equals("ADMINISTRADOR") || rol.equals("MANTENIMIENTO") || rol.equals("SOLICITANTE")) {
                                    out.print("<td align='center'><b class='naranja'>Finalizar activo en proceso</b><br />");
                                    out.print("<a href='#' onclick='Estado(" + obj_procesos[0] + ",3)'><b class='verde'>SI</b></a> | ");
                                    out.print("<a href='#' onclick='noFinalizado(" + obj_procesos[0] + ",1)'><b class='rojo'>NO</b></a>");
                                    out.print("</td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Activo en proceso</b><br />");
                                }
                            } else {
                                out.print("<td align='center'><b class='verde'>" + ((Integer.parseInt(obj_procesos[6].toString()) == 3) ? "Finalizado" : ((Integer.parseInt(obj_procesos[6].toString()) == 1) ? "<b class='rojo'>No Finalizado </b>" : " ")) + "<br>" + ((obj_procesos[3] != null) ? "<b>" + obj_procesos[3] + "</b> </br>" : "") + ((Integer.parseInt(obj_procesos[6].toString()) == 4) ? "<b class='verde'>Finalizado Verificado </b>" : ((Integer.parseInt(obj_procesos[6].toString()) == 0) ? "<b class='rojo'> No Finalizado Verificado </b> " : " ")) + "<br>");
                            }
                            if (!rol.equals("CONSULTA")) {
                                if (Integer.parseInt(obj_procesos[6].toString()) == 3 || (Integer.parseInt(obj_procesos[6].toString()) == 4) || (rol.equals("CONSULTA"))) {
                                    out.print("<td align='center' style='color:#bfbfbf' ><span class='fas fa-pencil-alt fa-size_small' title='Modificar Proceso' /></span></td>");
                                    out.print("<td align='center' style='color:#bfbfbf' ><span class='far fa-minus-square fa-size_small' title='Activos dados de baja' /></span></td>");
                                } else if (Integer.parseInt(obj_procesos[6].toString()) == 1 || (Integer.parseInt(obj_procesos[6].toString()) == 0) || (rol.equals("CONSULTA"))) {
                                    out.print("<td align='center' style='color:#bfbfbf'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Proceso' /></span></td>");
                                    out.print("<td align='center' style='color:#bfbfbf'><span class='far fa-minus-square fa-size_small' title='Activos dados de baja' /></span></td>");
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="ACTIVOS TOMADOS DE BAJA">
                                    out.print("<td align='center'><a href='Proceso?opc=1&idProceso=" + obj_procesos[0] + "' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Proceso' /></span></a></td>");
                                    if (lst_activos != null) {
                                        out.print("<td align='center'><a href='#' onclick=\"mostrar(\'2\',\'" + obj_procesos[0] + "\',\'" + obj_procesos[9] + "\')\" style='color:black;'><span class='far fa-minus-square fa-size_small' title='Activos dados de baja' /></span></a></td>");
                                    }else {
                                        out.print("<td align='center' ><a href='#' style='color: #b7b7b7;' ><span class='far fa-minus-square fa-size_small' title='No existen activos de baja' /></span></a></td>");
                                    }
                                    //</editor-fold>
                                }
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
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
//</editor-fold>
//</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Proceso").equals("ModuloProcesoDefinir")) {
                //<editor-fold defaultstate="collapsed" desc="PROCESO POR DEFINIR">
                out.print("<div id='container'>");
                out.print("<div style='float: right; '><input id='Txt_filtro' type='text' onkeyup='Filtrartodo()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                lst_procesos = jpa_procesos.consultarProcesosVerificar();
                out.print("<h3>Verificar Procesos</h3>");
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados0' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th style='width:1%'>#</th>");
                out.print("<th style='width:10%'>Código</th>");
                out.print("<th style='width:10%'>Fecha Inicio</th>");
                out.print("<th style='width:10%'>Nombre</th>");
                out.print("<th style='width:20%'>Descripción</th>");
                out.print("<th style='width:20%'>Justificación</th>");
                out.print("<th style='width:10%'>Fecha Fin</th>");
                out.print("<th style='width:10%'>Estado</th>");
                out.print("<th style='width:10%'>Liberar</th>");
                out.print("<th style='width:10%'>Devolver</th>");
                out.print("</tr>");
                if (lst_procesos == null) {
                    out.print("<tr><td align='center' colspan='10'>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                    out.print("<b>No se encontraron Procesos pendientes de verificar</b>");
                    out.print("</td></tr>");
                } else {
                    for (int i = 0; i < lst_procesos.size(); i++) {
                        Object[] obj_procesov = (Object[]) lst_procesos.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'><b>N°:</b>" + obj_procesov[0] + "</td>");
                        out.print("<td align='center'><b>" + obj_procesov[1] + "</b></td>");
                        out.print("<td align='center'>" + obj_procesov[2] + "</td>");
                        out.print("<td align='center'>" + obj_procesov[4] + "</td>");
                        out.print("<td align='center'>");
                        if (obj_procesov[5] == null || obj_procesov[5] == "") {
                            out.print("NINGUNA");
                        } else if (obj_procesov[5].toString().contains("<img")) {
                            String[] arg_img = obj_procesov[5].toString().split("<img");
                            for (int k = 0; k < arg_img.length; k++) {
                                if (k == 0) {
                                    cadena = arg_img[k];
                                } else {
                                    cadena = cadena + "<img style='width:20px; height:20px;' class='content_sin' id='Img_" + obj_procesov[0] + "_" + k + "' onclick=\"Abrir_img_pro('Img_" + obj_procesov[0] + "_" + k + "');\" " + arg_img[k];
                                }
                            }
                            out.print(cadena);
                        } else {
                            out.print(obj_procesov[5]);
                        }
                        out.print("</td>");
                        out.print("<td align='center'>" + obj_procesov[11] + "</td>");
                        out.print("<td align='center'>" + obj_procesov[3] + "</td>");
                        if (Integer.parseInt(obj_procesov[6].toString()) == 2 && obj_procesov[8].equals(nombreArea)) {
                        } else {
                            out.print("<td align='center'><b class='verde'>" + ((Integer.parseInt(obj_procesov[6].toString()) == 3) ? "Finalizado por Definir" : "<b class='rojo'>No Finalizado por Definir</b>") + "<br>" + ("") + "</td>");
                        }
                        out.print("<td align='center'><a href='#' onclick='Liberar(" + obj_procesov[0] + "," + ((Integer.parseInt(obj_procesov[6].toString()) == 3) ? "4" : "0") + ")' style='color:black;'><span class='fas fa-check fa-size_small' title='Verificar' /></span></a></td>");
                        out.print("<td align='center'><a href='#' onclick='Devolver(" + obj_procesov[0] + ")' style='color:black;'><span class='far fa-arrow-alt-circle-left fa-size_small' title='Devolver' /></span></a></td>");
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
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            }

        } catch (Exception e) {
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
