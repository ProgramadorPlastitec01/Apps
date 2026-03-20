package Tags;

import Controladores.FichaTecnicaJpaController;
import Controladores.OrdenJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_orden extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        OrdenJpaController jpa_orden = new OrdenJpaController();
        FichaTecnicaJpaController jpa_ficha = new FichaTecnicaJpaController();
        HttpSession sesion = pageContext.getSession();
        int id_rol = Integer.parseInt(sesion.getAttribute("id_rol").toString());
        int id_usu = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
        List lst_orden = null;
        List lst_l_orden = null;
        List lst_ordenes = null;
        List lst_fichaT = null;
        List lst_fichaTD = null;
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        String fichaT = (String) pageContext.getRequest().getAttribute("fichaT");
        int id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
        try {
            if (id_rol == 2 || id_rol == 1) {
                //<editor-fold defaultstate="collapsed" desc="registro orden">
                if (!fichaT.equals("")) {
                    lst_fichaT = jpa_ficha.consultaFichaTecnica(fichaT);
                }
                if (lst_fichaT != null) {
                    Object[] obj_ficha = (Object[]) lst_fichaT.get(0);
                    out.print("<div class='overlay' tabindex='-1' id='Ventana1' style='z-index: 100;opacity: 1.06; display: block;'>");
                    out.print("<fieldset class='resalta' id='registro_turno'  style=' overflow:hidden; width:18%; height:57%; top:59%;left:34%'>");
                    out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' onclick='VolverD()'></span></div>");
                    out.print("<h3>Nueva orden</h3>");
                    out.print("<form method='post' action='Orden?opc=1&idO=" + 0 + "&txt_bus=' style='margin:0%;'>");
                    out.print("<b>Ficha Técnica:</b><br/>");
                    out.print("<input type='text' name='txt_ficha' id='ficha-id' value='" + obj_ficha[2] + "' placeholder='Ficha técnica' onchange='javascript:this.value=this.value.toUpperCase(); autocomplete='" + lst_fichaTD + "'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('ficha-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("</form>");
                    out.print("<form method='post' action='Orden?opc=2' onsubmit='registroO();'>");
                    out.print("<b>Ficha técnica:</b><br/>");
                    out.print("<select name='slc_ficha' id='Sficha-id'>");
                    out.print("<option value='' style='display:none;'>SELECCIONE FICHA TÉCNICA</option>");
                    for (int i = 0; i < lst_fichaT.size(); i++) {
                        Object[] obj_Sficha = (Object[]) lst_fichaT.get(i);
                        if ((Integer) obj_Sficha[25] == 1) {
                            out.print("<option value='" + obj_Sficha[0] + "'>" + obj_Sficha[2].toString().toUpperCase() + " - " + obj_Sficha[3] + " / " + obj_Sficha[4].toString().toUpperCase() + " " + obj_Sficha[5].toString().toUpperCase() + " / " + obj_Sficha[24] + "</option>");
                        }
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('Sficha-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Orden de Producción:</b><br/>");
                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='Orden de producción' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
//                    out.print("<input type='text' name='txt_orden' id='orden-id' placeholder='Orden de producción' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('orden-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("validation.add( Validate.Enteros );");
                    out.print("</script>");
                    out.print("<b>Descripción:</b><br/>");
                    out.print("<textarea rows='6' id='descripcion-id' name='txt_descripcion' placeholder='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('descripcion-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "          <div></div>\n"
                            + "        </div>");
                    out.print("</fieldset></div>");
                } else {
                    out.print("<div class='overlay' tabindex='-1' id='Ventana1' style='z-index: 100;opacity: 1.06; display: none;'>");
                    out.print("<fieldset class='resalta' id='registro_turno'  style=' overflow:hidden; width:18%; height:57%; top:59%;left:34%'>");
                    out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' onclick='VolverD()'></span></div>");
                    out.print("<h3>Nueva orden</h3>");
                    out.print("<form method='post' action='Orden?opc=1&idO=" + 0 + "&txt_bus='>");
                    out.print("<b>Ficha Técnica:</b><br/>");
                    out.print("<input type='text' name='txt_ficha' id='ficha-id' placeholder='FT-E-????' onchange='javascript:this.value=this.value.toUpperCase();'>");
                    out.print("<input type='submit' value='Enviar'>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('ficha-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("</form>");
                    out.print("</fieldset></div>");
                }
                //</editor-fold>
            }
            out.print("<div class='cleaner'></div>");
            out.print("<div id='sin-content'>");
            if (id_orden != 0) {
                //<editor-fold defaultstate="collapsed" desc="modificar dimensionales">
                lst_l_orden = jpa_orden.consultaLoteEnsamble(id_orden);
                lst_orden = jpa_orden.consultaOrdenId(id_orden);
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                lst_fichaT = jpa_ficha.consultaFichaTecnicaId(Integer.parseInt(obj_orden[5].toString()));
                Object[] obj_ficha = (Object[]) lst_fichaT.get(0);
                double minY2 = (Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString()));
                double maxY2 = (Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString()));
                double minx1 = (Double.parseDouble(obj_ficha[9].toString()) - Double.parseDouble(obj_ficha[11].toString()));
                double maxx1 = (Double.parseDouble(obj_ficha[9].toString()) + Double.parseDouble(obj_ficha[10].toString()));
                double miny1 = (Double.parseDouble(obj_ficha[6].toString()) - Double.parseDouble(obj_ficha[8].toString()));
                double maxy1 = (Double.parseDouble(obj_ficha[6].toString()) + Double.parseDouble(obj_ficha[7].toString()));
                double minx2 = (Double.parseDouble(obj_ficha[15].toString()) - Double.parseDouble(obj_ficha[17].toString()));
                double maxx2 = (Double.parseDouble(obj_ficha[15].toString()) + Double.parseDouble(obj_ficha[16].toString()));
                double minx3 = (Double.parseDouble(obj_ficha[18].toString()) - Double.parseDouble(obj_ficha[20].toString()));
                double maxx3 = (Double.parseDouble(obj_ficha[18].toString()) + Double.parseDouble(obj_ficha[19].toString()));
                out.print("<div class='overlay' tabindex='-1' id='bloq' style='opacity: 1.06; display: block;'>");
                out.print("<fieldset class='resalta' id='Mod_parametros' style='visibility: visible;'>");
                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' onclick='VolverD()'></span></div>");
                out.print("<h3>Modificar controles dimensionales <br /><b>Orden: </b><b class='negro'>" + obj_orden[2] + "</b></h3>");
                if (lst_l_orden != null) {
                    out.print("<form action='Orden?opc=4' method='post' name='formModDimensional'>");
                    out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<b>Lote:</b><br />");
                    out.print("<select name='slt_lotes' id='lotes-id'>");
                    out.print("<option value='' style='display:none'>Seleccione los lotes</option>");
                    for (int i = 0; i < lst_l_orden.size(); i++) {
                        Object[] obj_lotes = (Object[]) lst_l_orden.get(i);
                        out.print("<option>" + obj_lotes[1] + "</option>");
                    }
                    out.print("</select><br /><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('lotes-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Parametro:</b><br />");
                    out.print("<select name='slt_parametro' id='parametro-id' onchange='MostrarPrmt(this.value)'>");
                    out.print("<option value='' style='display:none'>Seleccione los lotes</option>");
                    out.print("<option value='y2'>ALTURA PISTÓN</option>");
                    out.print("<option value='x1'>DIÁMETRO PISTÓN</option>");
                    out.print("<option value='y1'>Ø INTERNO CONFORMADO</option>");
                    out.print("<option value='x3'>Ø CONEXIÓN</option>");
                    out.print("</select><br /><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('parametro-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<div id='y2' style='display:none'>");
                    //<editor-fold defaultstate="collapsed" desc="y2">
                    out.print("<b>Y2:</b><br />");
                    out.print("<select name='slt_parametroy2' id='y2parametro-id' style='width:90px;'>");
                    out.print("<option value='' style='display:none'>Seleccione</option>");
                    out.print("<option value='>'>Mayor que</option>");
                    out.print("<option value='<'>menor que</option>");
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='number' name='txt_y2' id='id-y2' placeholder='y2' min=" + (Math.round(minY2 * 100) / 100d) + " max=" + (Math.round(maxY2 * 100) / 100d) + " step='0.01' style='width:80px;' onchange='validarDim(this.value)' onkeypress='validarDim(this.value)' onblur='validarDim(this.value)'>");
                    out.print("<input type='hidden' id='Val-y2' value='" + Double.parseDouble(obj_ficha[12].toString()) + "'>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div id='x1' style='display:none'>");
                    //<editor-fold defaultstate="collapsed" desc="x1">
                    out.print("<b>x1:</b><br />");
                    out.print("<select name='slt_parametrox1' id='x1parametro-id' style='width:90px;'>");
                    out.print("<option value='' style='display:none'>Seleccione</option>");
                    out.print("<option value='>'>Mayor que</option>");
                    out.print("<option value='<'>menor que</option>");
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='number' name='txt_x1' id='id-x1' placeholder='x1' min=" + (Math.round(minx1 * 100) / 100d) + " max=" + (Math.round(maxx1 * 100) / 100d) + " step='0.01' style='width:80px;' onchange='validarDim(this.value)' onkeypress='validarDim(this.value)' onblur='validarDim(this.value)'>");
                    out.print("<input type='hidden' id='Val-x1' value='" + Double.parseDouble(obj_ficha[9].toString()) + "'>");
//</editor-fold>
                    out.print("</div>");
                    out.print("<div id='y1' style='display:none'>");
                    //<editor-fold defaultstate="collapsed" desc="y1">
                    out.print("<b>y1:</b><br />");
                    out.print("<select name='slt_parametroy1' id='y1parametro-id' style='width:90px;'>");
                    out.print("<option value='' style='display:none'>Seleccione</option>");
                    out.print("<option value='>'>Mayor que</option>");
                    out.print("<option value='<'>menor que</option>");
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='number' name='txt_y1' id='id-y1' placeholder='y1' min=" + (Math.round(miny1 * 100) / 100d) + " max=" + (Math.round(maxy1 * 100) / 100d) + " step='0.01' style='width:80px;' onchange='validarDim(this.value)' onkeypress='validarDim(this.value)' onblur='validarDim(this.value)'>");
                    out.print("<input type='hidden' id='Val-y1' value='" + Double.parseDouble(obj_ficha[6].toString()) + "'>");
//</editor-fold>
                    out.print("</div>");
                    out.print("<div id='x2' style='display:none'>");
                    //<editor-fold defaultstate="collapsed" desc="x2">
                    out.print("<b>x2:</b><br />");
                    out.print("<select name='slt_parametrox2' id='x2parametro-id' style='width:90px;'>");
                    out.print("<option value='' style='display:none'>Seleccione</option>");
                    out.print("<option value='>'>Mayor que</option>");
                    out.print("<option value='<'>menor que</option>");
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='number' name='txt_x2' id='id-x2' placeholder='x2' min=" + (Math.round(minx2 * 100) / 100d) + " max=" + (Math.round(maxx2 * 100) / 100d) + " step='0.01' style='width:80px;' onchange='validarDim(this.value)' onkeypress='validarDim(this.value)' onblur='validarDim(this.value)'>");
                    out.print("<input type='hidden' id='Val-x2' value='" + Double.parseDouble(obj_ficha[15].toString()) + "'>");
//</editor-fold>
                    out.print("</div>");
                    out.print("<div id='x3' style='display:none'>");
                    //<editor-fold defaultstate="collapsed" desc="x3">
                    out.print("<b>x3:</b><br />");
                    out.print("<select name='slt_parametrox3' id='x3parametro-id' style='width:90px;'>");
                    out.print("<option value='' style='display:none'>Seleccione</option>");
                    out.print("<option value='>'>Mayor que</option>");
                    out.print("<option value='<'>menor que</option>");
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='number' name='txt_x3' id='id-x3' placeholder='x3' min=" + (Math.round(minx3 * 100) / 100d) + " max=" + (Math.round(maxx3 * 100) / 100d) + " step='0.01' style='width:80px;' onchange='validarDim(this.value)' onkeypress='validarDim(this.value)' onblur='validarDim(this.value)'>");
                    out.print("<input type='hidden' id='Val-x3' value='" + Double.parseDouble(obj_ficha[18].toString()) + "'>");
//</editor-fold>
                    out.print("</div>");
                    out.print("<b>Justificacion:</b><br/>");
                    out.print("<textarea rows='6' id='justificacion-id' name='txt_justificacion' placeholder='Justificacion' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('justificacion-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("</form>");
                    out.print("<input type='submit' onclick='Alertdimensional()' value='Modificar'>");
                    out.print("<br /><br /><b class='rojo'>Favor verificar la informacion, una vez modificados los datos no se podran restaurar.</b>");
                } else {
                    out.print("<h3>No existe lotes de ensamble</h3>");

                }
                out.print("</fieldset>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="Consulta ordenes">
            if (!filtro.equals("")) {
                out.print("<a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' title='Volver' /></a>");
                lst_ordenes = jpa_orden.consultaOrdenesFiltro(filtro);
            } else {
                lst_ordenes = jpa_orden.consultaOrdenes();
            }
            out.print("<br/><br/><div style='float: right;'>");
            out.print("<form method='post' action='Orden?opc=1&idO=" + 0 + "&txt_ficha='>");
            out.print("<input type='text' name='txt_bus' placeholder='Buscar'><br/>");
            out.print("</form>");
            out.print("</div>");
            out.print("<h3>Orden  de producción</h3>");
            if (lst_ordenes == null) {
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                if (id_rol == 6 || id_rol == 5 || id_rol == 3) {
                } else {
                    out.print("<div style='float:left;'><span class='fas fa-plus fa-size_small' onclick='mostrarVentana(1)'></span></div>");
                }
                out.print("</br></br><div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Orden</th>");
                out.print("<th>Ficha Tecnica</th>");
                out.print("<th>Contenido</th>");
                out.print("<th>Lote de Ensamble</th>");
                out.print("<th colspan='4'>Opc</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_ordenes.size(); i++) {
                    Object[] obj_orden = (Object[]) lst_ordenes.get(i);
                    out.print("<tr>");
                    out.print("<td align='center' style='width:12%;'><b>" + obj_orden[2] + "</b></br></br>");
                    out.print("<span class='fas fa-eye fa-size_small' onclick='envioVer(" + obj_orden[0] + ")' title='Ver'></span>  </td>");
                    out.print("<td style='width:30%;'><b>Ficha Tecnica: </b>" + obj_orden[6] + "<b title='Version'> V: </b>" + obj_orden[9] + ""
                            + "<hr/><b>Producto: </b>" + obj_orden[7] + "</td>");
                    out.print("<td  valign='top' style='width:25%;'><b>Descripcion: </b>" + obj_orden[3] + "</td>");
                    lst_l_orden = jpa_orden.consultaLoteEnsamble(Integer.parseInt(obj_orden[0].toString()));
                    if (lst_l_orden != null) {
                        out.print("<td  align='center' style='width:25%;'>");
                        for (int j = 0; j < lst_l_orden.size(); j++) {
                            Object[] obj_l_op = (Object[]) lst_l_orden.get(j);
                            out.print("<div style='overflow='scroll'>");
                            out.print("<b style='color:#000;'>" + obj_l_op[1] + "</b><b title='total Registros'>(" + obj_l_op[0] + ")</b>");
                            List lst_estado_lote_op = jpa_orden.consultaLoteEnsambleEstados((Integer) obj_orden[0], obj_l_op[1].toString());
                            int ap = 0, cu = 0, se = 0, re = 0;
                            for (int k = 0; k < lst_estado_lote_op.size(); k++) {
                                Object[] obj_estado_registros = (Object[]) lst_estado_lote_op.get(k);
                                if (obj_estado_registros[1].toString().equals("aprobado")) {
                                    ap = Integer.parseInt(obj_estado_registros[2].toString());
                                }
                                if (obj_estado_registros[1].toString().equals("cuarentena")) {
                                    cu = Integer.parseInt(obj_estado_registros[2].toString());
                                }
                                if (obj_estado_registros[1].toString().equals("seguimiento")) {
                                    se = Integer.parseInt(obj_estado_registros[2].toString());
                                }
                                if (obj_estado_registros[1].toString().equals("rechazado")) {
                                    re = Integer.parseInt(obj_estado_registros[2].toString());
                                }
                            }
                            out.print("<b class='verde' title='Registros Aprobados'>(" + ap + ")</b>");
                            out.print("<b class='naranja' title='Registros Cuarentena'>(" + cu + ")</b>");
                            out.print("<b class='azul'>(" + se + ")</b>");
                            out.print("<b class='rojo' title='Registros Rechazado'>(" + re + ")</b>");
                            out.print("<br />");
                        }
                        out.print("</div>");
                        out.print("</td>");
                    } else {
                        out.print("<td align='center' style='width:13%;'>Sin Lote</td>");
                    }
                    if (id_rol == 1 || id_usu == 12 || id_usu == 13 || id_usu == 35) {
                        out.print("<td align='center'><span class='fas fa-exclamation-triangle fa-size_small' onclick=envioCuarentena(" + obj_orden[0] + ") title='Cuarentena'></span></td>");
                        out.print("<td align='center'><span class='fas fa-search fa-size_small' onclick='VerSeguimiento(" + obj_orden[0] + ")' title='Seguimiento'></span></td>");
                        out.print("<td align='center'><span class='fas fa-edit fa-size_small' onclick='habilitarEditar(" + obj_orden[0] + ")'title='Modificar dimencionales'></span></td>");
                    } else if (id_rol == 6 || id_rol == 5) {
                    } else {
                        out.print("<td align='center'><a href='Turno?opc=1&idO=" + obj_orden[0] + "&idT=" + 0 + "&ver=" + 0 + "&registro=" + 5 + "&txt_bus=' style='color:black;'><span class='fas fa-exclamation-triangle fa-size_small' title='Cuarentena'></span></td>");
                        out.print("<td align='center'><span class='fas fa-search fa-size_small' onclick='VerSeguimiento(" + obj_orden[0] + ")' title='Seguimiento'></span></td>");
                    }
                    if (obj_orden[4].equals("abierto")) {
                        if (id_rol == 2 || id_rol == 1 || id_rol == 4) {
                            out.print("<td align='center'><span href='#' class='fas fa-lock-open fa-size_small' onclick='estado(\"cerrado\",\"" + obj_orden[0] + "\",\"" + filtro + "\")' title='Orden abierta'></span>");
                        } else if (id_rol == 6 || id_rol == 5 || id_rol == 3) {
                            out.print("<td align='center' ><span style='color:#bdbdbd;' href='#' class='fas fa-lock-open fa-size_small' title='Orden abierta'></span>");
                        } else {
                            out.print("<td align='center' ><span href='#' class='fas fa-lock-open fa-size_small' title='Orden abierta'></span>");
                        }
                    } else if (id_rol == 2 || id_rol == 1) {
                        out.print("<td align='center'><span href='#' class='fas fa-lock fa-size_small' onclick='estado(\"abierto\",\"" + obj_orden[0] + "\",\"" + filtro + "\")' title='Orden cerrada'></span>");
                    } else if (id_rol == 6 || id_rol == 5 || id_rol == 3) {
                        out.print("<td align='center'><span style='color:#bdbdbd;' class='fas fa-lock fa-size_small' title='Orden cerrada'></span>");
                    } else {
                        out.print("<td align='center'><span class='lass='fas fa-lock fa-size_small' title='Orden cerrada'></span>");
                    }
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
            //</editor-fold>
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_orden.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
