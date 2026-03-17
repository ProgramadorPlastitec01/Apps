package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.RolJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import Controladores.TipoInstrumentoJpaController;
import Controladores.AreaJpaController;
import Controladores.PlantillaJpaController;

public class Tag_tipo_Instrumento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        
        HttpSession sesion = pageContext.getSession();
        String UserName = "";
        String UserRol = "";
        UserName = pageContext.getSession().getAttribute("Nombre").toString();
        UserRol = pageContext.getSession().getAttribute("Rol").toString();
        boolean Auth = true;
        if (UserRol.equals("ADMINISTRADOR") || UserRol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }
        
        RolJpaController RolJpa = new RolJpaController();
        AreaJpaController AreaJpa = new AreaJpaController();
        PlantillaJpaController PlantillaJpa = new PlantillaJpaController();
        TipoInstrumentoJpaController TipoIntrsumentoJpa = new TipoInstrumentoJpaController();
        List lst_tipoInstrumento = null;
        List lst_area = null;
        List lst_plantilla = null;
        String filtro = "";
        int idTipo = Integer.parseInt(pageContext.getRequest().getAttribute("idTI").toString());
        try {
            if (idTipo > 0) {
                //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR  TIPO DE INSTRUMENTO">
                lst_tipoInstrumento = TipoIntrsumentoJpa.consultaTipoInstrumentoId(idTipo);
                if (lst_tipoInstrumento != null) {
                    Object[] obj_tipo = (Object[]) lst_tipoInstrumento.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Actualizar tipo instrumento</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Tipo_instrumento?opc=3&idTI=" + obj_tipo[0] + "' method='post'>");
                    out.print("<div class='mt-3 mb-2' style='text-align: center;'>");
                    int estTipo = Integer.parseInt(obj_tipo[9].toString());
                    out.print("<b>Tipo frecuencia</b>");
                    out.print("</div>");
                    out.print("<div class='col-lg-12' style='display: flex; justify-content: center; align-items: center;'>");
                    out.print("<div class='mr-5'>");
                    out.print("<label class='custom-switch'>");
                    out.print("<input type='radio' name='opt_tipo' value='1' class='custom-switch-input' " + ((estTipo == 1) ? "checked" : "") + ">");
                    out.print("<span class='custom-switch-description mr-2'> Inspección - Verificación</span>");
                    out.print("<span class='custom-switch-indicator'></span>");
                    out.print("</label><br>");
                    out.print("</div>");
                    out.print("<div class=''>");
                    out.print("<label class='custom-switch'>");
                    out.print("<input type='radio' name='opt_tipo' value='2' class='custom-switch-input  " + ((estTipo == 2) ? "checked" : "") + "'>");
                    out.print("<span class='custom-switch-description mr-2'> Verificación - Calibración</span>");
                    out.print("<span class='custom-switch-indicator'></span>");
                    out.print("</label>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-12' style='display: flex;'>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' class='form-control' style='margin-left:0;' name='txt_tipo' id='txt_tipo' value='" + obj_tipo[2] + "' placeholder='Tipo Instrumento' data-toggle='tooltip' data-placement='top' title='Tipo Instrumento' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-6' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Area'>");
                    out.print("<select class='form-control select2' name='Cbx_area'>");
                    out.print("<option value='" + obj_tipo[1] + "'>" + obj_tipo[7] + "</option>");
                    lst_area = AreaJpa.consultaAreas();
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if ((Integer) obj_area[4] == 1) {
                            if ((Integer) obj_tipo[1] == (Integer) obj_area[0]) {
                            } else {
                                out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                            }
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-12' style='display: flex;'>");
                    out.print("<input type='number' class='form-control' name='nmb_freInt' id='nmb_freInt' value='" + obj_tipo[3] + "' placeholder='Frecuencia Interna' data-toggle='tooltip' data-placement='top' title='Frecuencia Interna' required>");
                    out.print("<input type='number' class='form-control' name='nmb_tlInt' id='nmb_tlInt' value='" + obj_tipo[4] + "' placeholder='Tolerancia Interna' data-toggle='tooltip' data-placement='top' title='Tolerancia Interna' required>");
                    out.print("</div>");

                    out.print("<div class='col-lg-12' style='display: flex;'>");
                    out.print("<input type='number' class='form-control' name='nmb_freExt' id='nmb_freExt' value='" + obj_tipo[5] + "' placeholder='Frecuencia Externa' data-toggle='tooltip' data-placement='top' title='Frecuencia Externa' required>");
                    out.print("<input type='number' class='form-control' name='nmb_tlExt' id='nmb_tlExt' value='" + obj_tipo[6] + "' placeholder='Tolerancia Externa' data-toggle='tooltip' data-placement='top' title='Tolerancia Externa' required>");
                    out.print("</div>");

                    out.print("<div class='col-lg-12' style='display: flex;'>");
                    out.print("<div class='col-lg-6' style='display: flex;align-items: center;'>");
                    out.print("<b class='mr-2'>Aplica grafica</b>");
                    out.print("<div class='mr-2'>");
                    int estgraf = Integer.parseInt(obj_tipo[10].toString());
                    out.print("<label class='custom-switch'>");
                    out.print("<input type='radio' name='opt_grafica' value='1' class='custom-switch-input' " + ((estgraf == 0) ? "checked" : "") + ">");
                    out.print("<span class='custom-switch-description mr-2'>Si</span>");
                    out.print("<span class='custom-switch-indicator'></span>");
                    out.print("</label><br>");
                    out.print("</div>");
                    out.print("<div class=''>");
                    out.print("<label class='custom-switch'>");
                    out.print("<input type='radio' name='opt_grafica' value='2' class='custom-switch-input' " + ((estgraf == 1) ? "checked" : "") + ">");
                    out.print("<span class='custom-switch-description mr-2'>No</span>");
                    out.print("<span class='custom-switch-indicator'></span>");
                    out.print("</label>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-6'>");
                    out.print("<div class='' style='margin-top: 12px;margin-bottom: 12px;' data-toggle='tooltip' data-placement='top' title='Plantilla'>");
                    out.print("<select class='form-control select2' name='Cbx_plantilla'>");
                    out.print("<option value='" + obj_tipo[11] + "'>" + obj_tipo[12] + "</option>");
                    lst_plantilla = PlantillaJpa.consultaPlantillas();
                    if (lst_plantilla != null) {
                        for (int i = 0; i < lst_plantilla.size(); i++) {
                            Object[] obj_plantilla = (Object[]) lst_plantilla.get(i);
                            if ((Integer) obj_plantilla[8] == 1) {
                                if (obj_tipo[11] != obj_plantilla[0]) {
                                    out.print("<option value='" + obj_plantilla[0] + "'>" + obj_plantilla[1] + "</option>");
                                }
                            }
                        }
                    } else {
                        out.print("<option>Ha ocurrido un error, favor consultar con TI.</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Actualizar tipo instrumento</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<h3>Ha ocurrido un problema al consultar la informacion, favor comucarse con TI.</h3>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR TIPO INSTRUMENTO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar tipo instrumento</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Tipo_instrumento?opc=2' method='post'>");
            out.print("<div class='mt-3 mb-2' style='text-align: center;'>");
            out.print("<b>Tipo frecuencia</b>");
            out.print("</div>");
            out.print("<div class='col-lg-12' style='display: flex; justify-content: center; align-items: center;'>");
            out.print("<div class='mr-5'>");
            out.print("<label class='custom-switch'>");
            out.print("<input type='radio' name='opt_tipo' value='1' class='custom-switch-input' checked>");
            out.print("<span class='custom-switch-description mr-2'> Inspección - Verificación</span>");
            out.print("<span class='custom-switch-indicator'></span>");
            out.print("</label><br>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<label class='custom-switch'>");
            out.print("<input type='radio' name='opt_tipo' value='2' class='custom-switch-input'>");
            out.print("<span class='custom-switch-description mr-2'> Verificación - Calibración</span>");
            out.print("<span class='custom-switch-indicator'></span>");
            out.print("</label>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' style='margin-left:0;' name='txt_tipo' id='txt_tipo' placeholder='Tipo Instrumento' data-toggle='tooltip' data-placement='top' title='Tipo Instrumento' required>");
            out.print("</div>");
            out.print("<div class='col-lg-6' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Area'>");
            out.print("<select class='form-control select2' name='Cbx_area' id='Cbx_area'>");
            out.print("<option value='0'>Seleccionar area</option>");
            lst_area = AreaJpa.consultaAreas();
            for (int i = 0; i < lst_area.size(); i++) {
                Object[] obj_area = (Object[]) lst_area.get(i);
                if ((Integer) obj_area[4] == 1) {
                    out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                }
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='number' class='form-control' name='nmb_freInt' id='nmb_freInt' placeholder='Frecuencia Interna' data-toggle='tooltip' data-placement='top' title='Frecuencia Interna' required>");
            out.print("<input type='number' class='form-control' name='nmb_tlInt' id='nmb_tlInt' placeholder='Tolerancia Interna' data-toggle='tooltip' data-placement='top' title='Tolerancia Interna' required>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='number' class='form-control' name='nmb_freExt' id='nmb_freExt' placeholder='Frecuencia Externa' data-toggle='tooltip' data-placement='top' title='Frecuencia Externa' required>");
            out.print("<input type='number' class='form-control' name='nmb_tlExt' id='nmb_tlExt' placeholder='Tolerancia Externa' data-toggle='tooltip' data-placement='top' title='Tolerancia Externa' required>");
            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<div class='col-lg-6' style='display: flex;align-items: center;'>");
            out.print("<b class='mr-2'>Aplica grafica</b>");
            out.print("<div class='mr-2'>");
            out.print("<label class='custom-switch'>");
            out.print("<input type='radio' name='opt_grafica' value='1' class='custom-switch-input' checked>");
            out.print("<span class='custom-switch-description mr-2'>Si</span>");
            out.print("<span class='custom-switch-indicator'></span>");
            out.print("</label><br>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<label class='custom-switch'>");
            out.print("<input type='radio' name='opt_grafica' value='2' class='custom-switch-input'>");
            out.print("<span class='custom-switch-description mr-2'>No</span>");
            out.print("<span class='custom-switch-indicator'></span>");
            out.print("</label>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-6'>");
            out.print("<div class='' style='margin-top: 12px;margin-bottom: 12px;' data-toggle='tooltip' data-placement='top' title='Plantilla'>");
            out.print("<select class='form-control select2' name='Cbx_plantilla' id='Cbx_plantilla'>");
            out.print("<option value='0'>Seleccionar plantilla</option>");
            lst_plantilla = PlantillaJpa.consultaPlantillas();
            if (lst_plantilla != null) {

                for (int i = 0; i < lst_plantilla.size(); i++) {
                    Object[] obj_plantilla = (Object[]) lst_plantilla.get(i);
                    if ((Integer) obj_plantilla[8] == 1) {
                        out.print("<option value='" + obj_plantilla[0] + "'>" + obj_plantilla[1] + "</option>");
                    }
                }
            } else {
                out.print("<option>Ha ocurrido un error, favor consultar con TI.</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONSULTA PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Tipo Instrumento</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de tipos de instrumento</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (Auth) {
                out.print("<button class='btn btn-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered table-striped' id='table-1'>");
            out.print("<thead>");
            out.print("<tr class='centrar_fila'>");
            out.print("<th>Tipo</th>");
            out.print("<th>Area</th>");
            out.print("<th colspan='2'>Frecuencias</th>");
            out.print("<th style='min-width: 90px;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_tipoInstrumento = TipoIntrsumentoJpa.consultaTipoInstrumentos();
            if (lst_tipoInstrumento != null) {
                for (int i = 0; i < lst_tipoInstrumento.size(); i++) {
                    Object[] obj_tipo = (Object[]) lst_tipoInstrumento.get(i);
                    out.print("<tr class='centrar_fila'>");
                    out.print("<td>" + obj_tipo[2] + "</td>");
                    out.print("<td>" + obj_tipo[7] + "</td>");
                    if ((Integer) obj_tipo[9] == 0) {
                        out.print("<td align='center'>Inspección cada<br />" + obj_tipo[3] + " <b>dia(s)</b> + " + obj_tipo[4] + " <b>dia(s)</b></td>");
                        out.print("<td align='center'>Verificación cada<br />" + obj_tipo[5] + " <b>dia(s)</b> + " + obj_tipo[6] + " <b>dia(s)</b></td>");
                    } else if ((Integer) obj_tipo[9] == 1) {
                        out.print("<td align='center'>Verificación cada<br />" + obj_tipo[3] + " <b>dia(s)</b> + " + obj_tipo[4] + " <b>dia(s)</b></td>");
                        out.print("<td align='center'>Calibración cada<br />" + obj_tipo[5] + " <b>dia(s)</b> + " + obj_tipo[6] + " <b>dia(s)</b></td>");
                    }
                    int est = Integer.parseInt(obj_tipo[8].toString());

                    if (Auth) {
                        out.print("<td align='center'>"
                                + "<a href='#' id='btn_add' class='btn btn-secondary' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                                + "<a href='#' class='btn btn-secondary btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> </td>");
                    } else {
                        out.print("<td>");
                        out.print("<a href='Tipo_instrumento?opc=4&idTI=" + obj_tipo[0] + "&est=" + ((est == 1) ? 0 : 1) + "' id='btn_add' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                                + "<a href='Tipo_instrumento?opc=1&idTI=" + obj_tipo[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> ");

                        out.print("</td>");
                    }
                    out.print("</tr>");
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

//</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_tipo_Instrumento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
