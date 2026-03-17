package Tags;

import Controladores.ElectrodoJpaController;
import Controladores.PlanoJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_electrodo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        PlanoJpaController jpa_plano = new PlanoJpaController();
        ElectrodoJpaController jpa_electrodo = new ElectrodoJpaController();
        List lst_plano = jpa_plano.consultaPlanos();
        List lst_tipoP = jpa_plano.consultaTipoPlano();
        List lst_electrodos = null;
        try {
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR ELECTRODO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Electrodo</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Electrodo?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-lg-6 col-md-6 DivCentral'>");
            out.print("<div class='col-12' style='  margin-left:4.2% !important;' data-toggle='tooltip' data-placemente='top' title='Plano al que pertenece'>");
            out.print("<select class='form-control select2'  required name='slc_plano'>");
            out.print("<option selected disabled value=''>Seleccione Plano</option>");
            for (int i = 0; i < lst_tipoP.size(); i++) {
                Object[] obj_tipo = (Object[]) lst_tipoP.get(i);
                out.print("<optgroup label='" + obj_tipo[0] + "'>");
                for (int j = 0; j < lst_plano.size(); j++) {
                    Object[] obj_plano = (Object[]) lst_plano.get(j);
                    if (obj_tipo[0].equals(obj_plano[2])) {
                        out.print("<option value='" + obj_plano[0] + "'>" + obj_plano[1] + "</option>");
                    }
                }
                out.print("</optgroup>");
            }
            out.print("</optgroup>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-12' style='  margin-left:-4.2% !important;'>");
            out.print("<input type='text' class='form-control' name='txt_numero' id='txt_numero' placeholder='Numero Electrodo' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Numero Electrodo'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-6 col-md-6 DivCentral'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_linea' id='txt_linea' placeholder='Maquina o Referencia' required data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Maquina o Referencia'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-lg-12 mt-3' data-toggle='tooltip' data-placemente='top' title='Estado'>");
            out.print("<select class='form-control' required name='slc_estado'>");
            out.print("<option selected disabled value=''>Seleccione Estado</option>");
            out.print("<option>Activo</opction>");
            out.print("<option>Inactivo</opction>");
            out.print("<option>Obsoleto</opction>");
            out.print("<option>Prueba</opction>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Electrodo</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Electrodos</h4>");
            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar' ><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE LA TABLA">
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Numero Electrodo</th>");
            out.print("<th>Linea o Maquina</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Plano</th>");
            out.print("</tr>");
            out.print("<tbody>");
            lst_electrodos = jpa_electrodo.consultaElectrodos();
            if (lst_electrodos != null) {
                for (int i = 0; i < lst_electrodos.size(); i++) {
                    Object[] obj_electrodos = (Object[]) lst_electrodos.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_electrodos[2] + "</td>");
                    if (obj_electrodos[2].equals("N/A") || obj_electrodos[3].equals("N/A")) {
                        out.print("<form action='Electrodo?opc=3&idE=" + obj_electrodos[0] + "' method='post' id='form_" + i + "'>");
                        out.print("<td><input class='form-control' type='text' name='txt_linea' id='linea-id' placeholder='Maquina o Referencia' onchange='javascript:this.value=this.value.toUpperCase();' /></td>");
                        out.print("<td><select class='form-control' name='slc_estado' id='estado-id'><br />");
                        out.print("<option>Activo</opction>");
                        out.print("<option>Inactivo</opction>");
                        out.print("<option>Obsoleto</opction>");
                        out.print("<option>Prueba</opction>");
                        out.print("</select></td>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('estado-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("</form>");
                        out.print("<td>" + obj_electrodos[5] + "</td>");
                    } else {
                        out.print("<td>" + obj_electrodos[3] + "</td>");
                        out.print("<td>" + obj_electrodos[4] + "</td>");
                        out.print("<td>" + obj_electrodos[5] + "</td>");
                    }
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
