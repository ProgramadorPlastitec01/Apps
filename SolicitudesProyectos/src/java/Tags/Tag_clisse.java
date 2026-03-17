package Tags;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.ClisseJpaController;
import javax.servlet.http.HttpSession;

public class Tag_clisse extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            String nombre = sesion.getAttribute("Nombre").toString();
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            ClisseJpaController jpa_clisse = new ClisseJpaController();
            List lst_clisse = null, lst_clisseId = null, lst_detalle = null, lst_parametro = null, lst_detalleId = null, lst_minmax_letra = null,
                    lst_diff = null;
            int estado_actual = 0, control = 0, id_clisse = 0, count = 0, id_detalle = 0, estado = 0, idInicial = 0, idFinal = 0, temp2 = 0;
            String ControlG = "", letra = "";
            try {
                id_clisse = Integer.parseInt(pageContext.getRequest().getAttribute("id_clisse").toString());
            } catch (NumberFormatException e) {
                id_clisse = 0;
            }
            if (pageContext.getRequest().getAttribute("Clisse").toString().equals("ModuloConsulta")) {
                //<editor-fold defaultstate="collapsed" desc="CONSULTA CISSE">
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Módulo Control R-MTF-059</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Listado R-MTF-059</h4>");
                out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                if (id_clisse != 0) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR CLISSE">
                    lst_clisseId = jpa_clisse.Consulta_Clisse_Id(id_clisse);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contClisse'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Modificar R-MTF-059</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    if (lst_clisseId != null) {
                        Object[] obj_clisseId = (Object[]) lst_clisseId.get(0);
                        out.print("<form action='Clisse?opc=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='id_clisse' value='" + id_clisse + "'>");
                        out.print("<div class='col-12 mt-2'>");
                        out.print("<input type='date' class='form-control' name='txt_fecha' id='txt_fecha' placeholder='Fecha' required='' value='" + obj_clisseId[1] + "' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                        out.print("</div>");
                        out.print("<div class='col-12 mt-2'>");
                        out.print("<input type='text' class='form-control' name='txt_codigo' id='txt_codigo' placeholder='Codigo/Arte' required='' value='" + obj_clisseId[2] + "' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Codigo/Arte'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un codigo!</div>");
                        out.print("</div>");
                        out.print("<div class='col-12 mt-2'>");
                        out.print("<input type='text' class='form-control' name='txt_producto' id='txt_producto' placeholder='Producto' required='' value='" + obj_clisseId[3] + "' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Producto'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un producto!</div>");
                        out.print("</div>");

                        out.print("<div class='col-12 mt-2'>");
                        out.print("<textarea class='summernote-simple' name='txt_observacion' required='' style'resize: none; !important' placeholder='Observación!'>" + ((obj_clisseId[11] == null) ? "" : obj_clisseId[11]) + "</textarea>");
                        out.print("</div>");

                        out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                        out.print("</div>");
                        out.print("</form>");
                    } else {
                        out.print("<div class='cont_form_user'>");
                        out.print("<div class='col-lg-12 col-md-6' style='text-align:center;margin-top: 20px;margin-bottom: 20px;'>");
                        out.print("<h6>Se ha generado un error en la consulta, favor cominucarse con el area de T.I.</h6><br>");
                        out.print("<i class=\"fas fa-exclamation-triangle\" style='font-size: 25px;color: #fc544b;'></i>");
                        out.print("</div>");
                        out.print("</div>");
                    }

                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTRO CLISSE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contClisse'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Registrar R-MTF-059</h3>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<form action='Clisse?opc=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-12 mt-2'>");
                out.print("<input type='date' class='form-control' name='txt_fecha' id='txt_fecha' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                out.print("</div>");
                out.print("<div class='col-12 mt-2'>");
                out.print("<input type='text' class='form-control' name='txt_codigo' id='txt_codigo' placeholder='Codigo/Arte' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Codigo/Arte'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un codigo!</div>");
                out.print("</div>");

                out.print("<div class='col-12 mt-2'>");
                out.print("<input type='text' class='form-control' name='txt_producto' id='txt_producto' placeholder='Producto' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Producto'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un producto!</div>");
                out.print("</div>");

                out.print("<div class='col-12 mt-2'>");
                out.print("<textarea class='summernote-simple' name='txt_observacion' required='' style'resize: none; !important' placeholder='Observación!'></textarea>");
                out.print("</div>");

                out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                //<editor-fold defaultstate="collapsed" desc="LISTA DE REGISTROS CLISSE">
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Estado</th>");
                out.print("<th>Fecha</th>");
                out.print("<th>Codigo</th>");
                out.print("<th>Producto</th>");
                out.print("<th>Ejecutor</th>");
                out.print("<th>Verificador</th>");
                out.print("<th>Control</th>");
                out.print("<th style='text-align: center;'>Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_clisse = jpa_clisse.Consulta_Clisse();
                if (lst_clisse != null) {
                    for (int i = 0; i < lst_clisse.size(); i++) {
                        Object[] obj_clisse = (Object[]) lst_clisse.get(i);
                        control = Integer.parseInt(obj_clisse[9].toString());
                        estado_actual = Integer.parseInt(obj_clisse[8].toString());
                        out.print("<tr>");
                        out.print("<td align='center'><a style='color:white;cursor:auto' class='btn btn-" + ((estado_actual == 1) ? "success" : (estado_actual == 3) ? "secondary" : "danger") + " btn-sm'><b" + ((estado_actual == 1) ? "style='color:green'> ACTIVO</b>" : (estado_actual == 3) ? "style='color:gray'> HISTORICO</b>" : "style='color:black'>INACTIVO</b>") + "</a>" + "</td>");
                        out.print("<td>" + obj_clisse[1] + "</td>");
                        out.print("<td>" + obj_clisse[2] + "</td>");
                        out.print("<td>" + obj_clisse[3] + "</td>");
                        out.print("<td>" + ((obj_clisse[4] == null) ? "SIN FIRMA" : obj_clisse[4]) + "</td>");
                        out.print("<td>" + ((obj_clisse[6] == null) ? "SIN FIRMA" : obj_clisse[6]) + "</td>");
                        out.print("<td><b class='" + ((control == 1) ? "BcolorV" : (control == 3) ? "BcolorC" : (control == 4) ? "BcolorP" : "BcolorR") + "'  > " + ((control == 1) ? "CUMPLE" : (control == 3) ? "CUARENTENA" : (control == 4) ? "SIN CONTROL" : "NO CUMPLE") + "" + "</td>");

                        out.print("<td align='center' ><div style='display:flex;'>");
                        out.print("<div class='mr-2'><a href='Clisse?opc=1&id_clisse=" + obj_clisse[0] + "&temp=1' class='btn btn-white btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Ver'><i class='fas fa-eye'></i></a></div>");
                        if ((Integer) obj_clisse[10] == 0) {
                            out.print("<div class='mr-2'  data-toggle='tooltip' data-placement='top' title='Registro cerrado'><a class='btn btn-secondary btn-icon btn-sm disabled'><i class='fas fa-pencil-alt'></i></a></div>");
                            out.print("<div><a style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Estado Finalizado'><i class='fas fa-lock '></i></a></div>");
                        } else {
                            out.print("<div class='mr-2'><a href='Clisse?opc=1&id_clisse=" + obj_clisse[0] + "'  class='btn btn-black btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-pencil-alt'></i></a></div>");
                            out.print("<div><a style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Estado Pendiente'><i class='fas fa-lock-open'></i></a></div>");
                        }
                        out.print("</div></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Clisse").toString().equals("ModuloDetalle")) {
                //<editor-fold defaultstate="collapsed" desc="DETALLE CLISSE">
                try {
                    id_detalle = Integer.parseInt(pageContext.getRequest().getAttribute("id_detalle").toString());
                } catch (NumberFormatException e) {
                    id_detalle = 0;
                }
                try {
                    temp2 = Integer.parseInt(pageContext.getRequest().getAttribute("temp2").toString());
                } catch (NumberFormatException e) {
                    temp2 = 0;
                }
                try {
                    letra = pageContext.getRequest().getAttribute("letra").toString();
                } catch (Exception e) {
                    letra = "";
                }
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Detalle Clisse</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                lst_clisseId = jpa_clisse.Consulta_Clisse_Id(id_clisse);
                lst_detalle = jpa_clisse.Detalle_Clisse(id_clisse);
                if (lst_clisseId != null) {
                    Object[] obj_clisseId = (Object[]) lst_clisseId.get(0);
                    estado_actual = Integer.parseInt(obj_clisseId[8].toString());
                    control = Integer.parseInt(obj_clisseId[9].toString());
                    estado = Integer.parseInt(obj_clisseId[10].toString());
                    idInicial = Integer.parseInt(obj_clisseId[14].toString());
                    idFinal = Integer.parseInt(obj_clisseId[15].toString());
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CONTROL">
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROL METROLOGIA A">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
                    out.print("<input type='hidden' id='idInicial' value='" + idInicial + "'>");
                    out.print("<input type='hidden' id='idFinal' value='" + idFinal + "'>");
                    out.print("<div class='contClisse' id='contextoA'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Registrar Control <b style='color:#f70f03'>A</b></h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<form action='Clisse?opc=3' id='FormClisse' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='id_clisse' value='" + id_clisse + "'>");
                    out.print("<input type='hidden' name='estadoV' id='estadoV'  value='1'>");
                    out.print("<input type='hidden' name='contadorInicial' id='contadorIncial'  value=''>");
                    out.print("<input type='hidden' name='contadorFinal' id='contadorFinal'  value=''>");
                    out.print("<input type='hidden' name='txt_letra' id='txt_letra'  value=''>");

                    out.print("<div class='col-12 mt-2'>");
                    out.print("<b class='clssB'>EJECUTOR:</b><br/><input type='text' class='form-control btnEstric' readonly='false' name='txt_ejecutor' id='txt_ejecutor' value='" + nombre + "' placeholder='Ejecutor' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Ejecutor'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un ejecutor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-12 mt-2 text-center'>");
                    out.print("<button type='button' onclick='CampoAddClisset()' class='btn btn-red btn-sm'><i class='fas fa-plus'></i></button>");
                    out.print("</div>");

                    out.print("<div id='container' class='mt-3 containerOrder'>");
                    out.print("</div>");

                    out.print("<div class='col-12 mt-2'>");
                    out.print("<b class='clssB'>Observación:</b><br/>"
                            + "<textarea class='form-control' name='txt_observacion' id='txt_observacion' placeholder='Observación' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Observación'></textarea>");
                    out.print("</div>");

                    out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                    out.print("<button  onclick='Validar()' class='btn btn-red btn-lg'>Registrar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    if (temp2 > 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROL METROLOGIA B - C - D">
                        lst_minmax_letra = jpa_clisse.Consulta_MinMax_Letra(id_clisse, letra);
                        if (lst_minmax_letra != null) {
                            Object[] obj_detalleMM = (Object[]) lst_minmax_letra.get(0);
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='contClisse' id='contextoB'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h3>Registrar Control <b style='color:#f70f03;text-transform: uppercase;'>" + letra.substring(0, 1) + "</b></h3>");
                            out.print("<button class='btn btn-outline-secondary' onclick=\"javascript:location.href='Clisse?opc=1&temp=1&id_clisse=" + id_clisse + "'\" style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            if (idFinal != Integer.parseInt(obj_detalleMM[2].toString())) {
                                out.print("<form action='Clisse?opc=3' id='FormClisse' method='post' class='needs-validation' novalidate=''>");
                                out.print("<input type='hidden' name='id_clisse' value='" + id_clisse + "'>");
                                out.print("<input type='hidden' name='estadoV' id='estadoV'  value='1'>");
                                out.print("<input type='hidden' name='idInicialBCD' id='idInicialBCD'  value='" + obj_detalleMM[2] + "'>");
                                out.print("<input type='hidden' name='idFinalBCD' id='idFinalBCD'  value='" + ((idFinal == 0) ? 1 : idFinal) + "'>");
                                out.print("<input type='hidden' name='contadorInicial' id=''  value='" + (Integer.parseInt(obj_detalleMM[2].toString()) + 1) + "'>");
                                out.print("<input type='hidden' name='contadorFinal' id='contadorFinalB'  value=''>");
                                out.print("<input type='hidden' name='txt_letra' id='txt_letraB'  value='" + letra + "'>");

                                out.print("<div class='col-12 mt-2'>");
                                out.print("<b class='clssB'>EJECUTOR:</b><br/><input type='text' class='form-control btnEstric' readonly='false' name='txt_ejecutor' id='txt_ejecutor' value='" + nombre + "' placeholder='Ejecutor' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Ejecutor'>");
                                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un ejecutor!</div>");
                                out.print("</div>");

                                out.print("<div class='col-12 mt-2 text-center'>");
                                out.print("<button type='button' onclick='CamposClissetB()' class='btn btn-red btn-sm'><i class='fas fa-plus'></i></button>");
                                out.print("</div>");

                                out.print("<div id='containerB' class='mt-3 containerOrder'>");
                                out.print("</div>");

                                out.print("<div class='col-12 mt-2'>");
                                out.print("<b class='clssB'>Observación:</b><br/>"
                                        + "<textarea class='form-control' name='txt_observacion' id='txt_observacion' placeholder='Observación' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Observación'></textarea>");
                                out.print("</div>");

                                out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                                out.print("<button  onclick='Validar()' class='btn btn-red btn-lg'>Registrar</button>");
                                out.print("</div>");

                                out.print("<script>");
                                out.print("document.addEventListener('keydown', function (event) {\n"
                                        + "    if (event.key === '+') {\n"
                                        + "        CampoAddClissetB();  // Asegúrate de que la función se llama correctamente\n"
                                        + "    }\n"
                                        + "});");
                                out.print("</script>");
                                out.print("</form>");
                            } else {
                                out.print("<div class='cont_form_user'>");
                                out.print("<div class='col-lg-12 col-md-6' style='text-align:center;margin-top: 20px;margin-bottom: 20px;'>");
                                out.print("<h5 style='color: #fc544b;'>¡Controles completos!</h5>");
                                out.print("<h6>Si desea registrar mas controles, registre nuevos I.D en el clise <b style='color:orange'>A</b></h6>");
                                out.print("<i class=\"fas fa-exclamation-triangle\" style='font-size: 25px;color: #fc544b;'></i>");
                                out.print("</div>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                    }
                    //</editor-fold>
                    if (id_detalle > 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROL METROLOGIA DETALLE">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contClisse'>");
                        lst_detalleId = jpa_clisse.Consultar_detalleId(id_detalle);
                        if (lst_detalleId != null) {
                            Object[] obj_detalleId = (Object[]) lst_detalleId.get(0);
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h4>Registrar Control Cuarentena</h4>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<form action='Clisse?opc=4' id='FormClisseCuarentena' method='post' class='needs-validation' novalidate=''>");
                            lst_parametro = jpa_clisse.Consultar_parametro("ControlMetrologico");
                            if (lst_parametro != null) {
                                Object[] obj_parametro = (Object[]) lst_parametro.get(0);
                                ControlG = obj_parametro[2].toString();
                                out.print("<input type='hidden' id='toleracia' value='" + ControlG + "'>");
                            }
                            out.print("<input type='hidden' name='id_clisse' value='" + id_clisse + "'>");
                            out.print("<input type='hidden' name='id_detalle' value='" + id_detalle + "'>");
                            out.print("<input type='hidden'  name='estadoV' id='estadoVC'  value=''>");
                            out.print("<div class='FormControl'>");
                            out.print("<div class='col-6 mt-2'>");
                            out.print("<b class='clssB'>I.D</b><br/><input type='text' class='form-control btnEstric' readonly='false' name='id' id='idC' value='" + obj_detalleId[2] + "'  placeholder='I.D' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='I.D'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un I.D!</div>");
                            out.print("</div>");

                            out.print("<div class='col-6 mt-2'>");
                            out.print("<b class='clssB'>EJECUTOR:</b><br/><input type='text' class='form-control btnEstric' readonly='false' name='txt_ejecutor' id='txt_ejecutor' value='" + nombre + "' placeholder='Ejecutor' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Ejecutor'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un ejecutor!</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='FormControl'>");

                            out.print("<div class='col-6 mt-2'>");
                            out.print("<b class='clssB'>A:</b><br/>"
                                    + "<input type='text' class='form-control' name='txt_a' id='txt_a_c' placeholder='Control (A)' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Control (A)'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");

                            out.print("<div class='col-6 mt-2'>");
                            out.print("<b class='clssB'>B:</b><br/><input type='text' class='form-control' name='txt_b' id='txt_b_c' placeholder='Control (B)' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Control (B)'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class='FormControl'>");

                            out.print("<div class='col-6 mt-2'>");
                            out.print("<b class='clssB'>C:</b><br/><input type='text' class='form-control' name='txt_c' id='txt_c_c' placeholder='Control (C)' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Control (C)'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");

                            out.print("<div class='col-6 mt-2'>");
                            out.print("<b class='clssB'>D:</b><br/><input type='text' class='form-control' name='txt_d' id='txt_d_c' placeholder='Control (D)' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Control (D)'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class='col-12 mt-2'>");
                            out.print("<b class='clssB'>Observación:</b><br/>"
                                    + "<textarea class='form-control' name='txt_observacion' id='txt_observacion' placeholder='Observación' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Observación'></textarea>");
                            out.print("</div>");

                            out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                            out.print("<button  onclick='ValidarCuarentena()' class='btn btn-red btn-lg'>Registrar</button>");
                            out.print("</div>");
                            out.print("</form>");
                        }
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    out.print("<div style='width:100%; margin:auto' class='text-center'>");
                    out.print("<table class='table table-sm table-hover'>");
                    out.print("<thead><tr>");
                    out.print("<th class='thDiffValores'>CLISSE</th>");
                    out.print("<th class='thDiffValores'>MINIMO</th>");
                    out.print("<th class='thDiffValores'>MAXIMO</th>");
                    out.print("<th class='thDiffValores'>DIFERENCIA</th>");
                    out.print("</tr></thead>");
                    out.print("<tbody>");
                    lst_diff = jpa_clisse.Consultar_Diff_valores(id_clisse);
                    if (lst_diff != null) {
                        for (int i = 0; i < lst_diff.size(); i++) {
                            Object[] obj_diff = (Object[]) lst_diff.get(i);
                            out.print("<tr>");
                            out.print("<th class='thDiffValores'> " + ((obj_diff[1] == null) ? "Sin datos" : obj_diff[1]) + "</th>");
                            out.print("<td>" + ((obj_diff[2] == null) ? "Sin datos" : obj_diff[2]) + "</td>");
                            out.print("<td>" + ((obj_diff[3] == null) ? "Sin datos" : obj_diff[3]) + "</td>");
                            if (obj_diff[5] == null) {
                                out.print("<td>" + ((obj_diff[4] == null) ? "Sin datos" : obj_diff[4]) + "</td>");
                            } else if (obj_diff[5].equals("F")) {
                                out.print("<td><b style='color:red'>" + ((obj_diff[4] == null) ? "Sin datos" : obj_diff[4]) + "</b></td>");
                            } else {
                                out.print("<td><b style='color:green'>" + ((obj_diff[4] == null) ? "Sin datos" : obj_diff[4]) + "</b></td>");
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</<tbody>");
                    out.print("</table>");
                    out.print("</div>");

                    if (estado == 1) {
                        if (control == 1 || control == 3 || control == 4) {
                            if (rol.equals("ADMIN") || rol.equals("MTF")) {
                                out.print("<input type='hidden' id='etdC' value='" + control + "'>");
                                out.print("<form action='Clisse?opc=5' id='FormVerificacion' method='post'>");
                                out.print("<input type='hidden' name='id_clisse' value='" + id_clisse + "'>");
                                out.print("<input type='hidden' name='txt_verificador' value='" + nombre + "'>");
                                out.print("<input type='hidden' name='estado' value='0'>");
                                out.print("</form>");
                                out.print("<div class='DivButtonPending'>");
                                out.print("<button class='btn btn-green' style='border-radius: 4px;'  onclick='ValidarEstadoFirma()'><i class='fas fa-check-double'></i></button>");
                                out.print("</div>");
                            }
                        }
                    } else {
                        out.print("<div class='DivButtonPending'>");
                        out.print("<button class='btn btn-info imprimir' onclick='Imprimir();' data-toggle='tooltip' data-placement='top' title='Imprimir / PDF'><i class='fas fa-print'></i></button>");
                        out.print("</div>");
                    }

                    out.print("<div id='Imprimir'>");
                    out.print("<table style='width:100%'>");
                    out.print("<thead>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO R-MTF-059">
                    out.print("<tr>");
                    out.print("<tr><td colspan='12' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:30%' align='center' rowspan='2'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                    out.print("<td style='width:40%;'><h6 style='text-align: center;'>REGISTRO</h6></td>");
                    out.print("<td style='width:30%'  align='center'><b>CODIGO</b><b style='color:black'> R-MTF-059</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:40%;' ><b  class='mt-2' style='text-align: center;'>CONTROL METROLOGIA CLISE ESTAMPADO</b></td>");
                    out.print("<td style='width:30%' align='center' ><b>VERSIÓN</b><b style='color:black'> 01</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:100%'class='PdgTd'  colspan='4'><b>CODIGO CLISE - PRODUCTO:</b> " + obj_clisseId[2] + " - " + obj_clisseId[3] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='PdgTd'><b>EJECUTO: </b>" + ((obj_clisseId[4] == null) ? "SIN FIRMA" : obj_clisseId[4]) + "</td>");
                    out.print("<td class='PdgTd'><b>VERIFICADO POR: </b>" + ((obj_clisseId[6] == null) ? "SIN FIRMA" : obj_clisseId[6]) + "</td>");
                    out.print("<td class='PdgTd' ><b>ESTADO ACTUAL: " + ((estado_actual == 1) ? "<b style='color: green'>ACTIVO</b>" : (estado_actual == 3) ? "<b style='color: orange'>HISTORICO</b>" : "<b style='color: red'>INACTIVO</b>") + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='PdgTd' colspan='2'><b>FECHA:</b> " + obj_clisseId[1] + "</td>");
                    out.print("<td class='PdgTd'><b>ESTADO CONTROL: " + ((control == 1) ? "<b style='color: green'>CUMPLE</b>" : (control == 3) ? "<b style='color: orange'>CUARENTENA</b>" : (control == 4) ? "<b class='BcolorP'>SIN CONTROL</b>" : "<b style='color: red'>NO CUMPLE</b>") + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='PdgTd' colspan='4'><b style='colo:black;'>NOTA IMPORTANTE: </b>EL ESTADO ACTUAL DEL CLISSE SE DETERMINA DE ACUERDO AL REGISTRO R-PR-015. <b style='colo:black;'> LA DIFERENCIA EN ALTURAS NO DEBE SER MAYOR A +/-0.3MM </b> SI  CUMPLE EL CLISSE ES APTO PARA PRODUCCION, SI <b style='colo:black;'> NO CUMPLE </b> EL CLISSE DEBE SER VERIFICADO MEDIANTE MONTAJE, AJUSTES Y PRUEBAS EN EL EQUIPO HOT STAMPING QUE DETERMINE EL AREA DE PRODUCCION.</td>");
                    out.print("</tr>");
                    //</editor-fold>
                    out.print("</thead>");
                    out.print("</table>");
                    out.print("<table  style='width:100%'>");
                    out.print("<tbody>");
                    out.print("<tr class='StyleTr'>");
                    out.print("<td style='width:6%'>I.D</td>");
                    if (estado == 0) {

                    } else if (idInicial > 0 && idFinal > 0) {
                        out.print("<td class='TmnTable'><button onclick='mostrarConvencion(4);EnviarLetra(\"a\");' class='btn btn-red btn-sm'>A</button></td>");
                        out.print("<td class='TmnTable'><button onclick=\"javascript:location.href='Clisse?opc=1&temp=1&id_clisse=" + id_clisse + "&temp2=1&letra=b1'\" class='btn btn-red btn-sm'>B</button></td>");
                        out.print("<td class='TmnTable'><button onclick=\"javascript:location.href='Clisse?opc=1&temp=1&id_clisse=" + id_clisse + "&temp2=1&letra=c1'\" class='btn btn-red btn-sm'>C</button></td>");
                        out.print("<td class='TmnTable'><button onclick=\"javascript:location.href='Clisse?opc=1&temp=1&id_clisse=" + id_clisse + "&temp2=1&letra=d1'\" class='btn btn-red btn-sm'>D</button></td>");
                    } else {
                        out.print("<td class='TmnTable'><button onclick='mostrarConvencion(4);EnviarLetra(\"a\");' class='btn btn-red btn-sm'>A</button></td>");
                        out.print("<td class='TmnTable'><button class='btn btn-secondary btn-sm not-allowed'>B</button></td>");
                        out.print("<td class='TmnTable'><button class='btn btn-secondary btn-sm not-allowed'>C</button></td>");
                        out.print("<td class='TmnTable'><button class='btn btn-secondary btn-sm not-allowed'>D</button></td>");
                    }
                    out.print("<td class='TmnTable'>A</td>");
                    out.print("<td class='TmnTable'>B</td>");
                    out.print("<td class='TmnTable'>C</td>");
                    out.print("<td class='TmnTable'>D</td>");
                    out.print("<td class='TmnTable'>A</td>");
                    out.print("<td class='TmnTable'>B</td>");
                    out.print("<td class='TmnTable'>C</td>");
                    out.print("<td class='TmnTable'>D</td>");
                    out.print("</tr>");
                    if (lst_detalle != null) {
                        for (int i = 0; i < lst_detalle.size(); i++) {
                            Object[] obj_detalle = (Object[]) lst_detalle.get(i);
                            out.print("<tr " + ((Integer.parseInt(obj_detalle[15].toString()) == 3) ? "class='trCuarentena'" : "") + " >");

                            if (Integer.parseInt(obj_detalle[15].toString()) == 3) {
                                out.print("<td class='StyleTd'><a href='Clisse?opc=1&id_clisse=" + id_clisse + "&id_detalle=" + obj_detalle[0] + "&temp=1' class='btn btn-warning btn-sm'>" + obj_detalle[2] + "</a></td>");
                            } else {
                                out.print("<td class='StyleTr'>" + obj_detalle[2] + "</td>");
                            }
                            out.print("<td class='tdCenter'>" + ((obj_detalle[3] == null) ? "-" : obj_detalle[3]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[4] == null) ? "-" : obj_detalle[4]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[5] == null) ? "-" : obj_detalle[5]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[6] == null) ? "-" : obj_detalle[6]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[7] == null) ? "-" : obj_detalle[7]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[8] == null) ? "-" : obj_detalle[8]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[9] == null) ? "-" : obj_detalle[9]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[10] == null) ? "-" : obj_detalle[10]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[11] == null) ? "-" : obj_detalle[11]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[12] == null) ? "-" : obj_detalle[12]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[13] == null) ? "-" : obj_detalle[13]) + "</td>");
                            out.print("<td class='tdCenter'>" + ((obj_detalle[14] == null) ? "-" : obj_detalle[14]) + "</td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td class='TextNaraja' colspan='13' style='width:6%'>SIN CONTROL DE CLISSE REGISTRADOS</td>");
                        out.print("</tr>");
                        out.print("<tr><td colspan='13'><span style='    font-size: 12px;\n"
                            + "    font-style: italic;\n"
                            + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                    }
                    out.print("</tbody>");
                    out.print("<tr><td class='PdgTd' colspan='13'><b>OBSERVACIONES GENERALES: </b>" + ((obj_clisseId[11] == null) ? "N/A" : obj_clisseId[11]) + "</td></tr>");
                    out.print("<tr><td colspan='13'><span style='    font-size: 12px;\n"
                            + "    font-style: italic;\n"
                            + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                    out.print("</table>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                out.print("</div>");
                //</editor-fold>
            }

        } catch (Exception ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
