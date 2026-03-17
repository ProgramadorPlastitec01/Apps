package Tags;

import Controladores.VerificarEtdJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_item_verificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            VerificarEtdJpaController jpa_itemver = new VerificarEtdJpaController();
            List lst_itemVer = null;
            List lst_id_itemVer = null;
            int id_item = 0;
            String filtro = "";
            try {
                id_item = Integer.parseInt(pageContext.getRequest().getAttribute("id_item").toString());
            } catch (Exception e) {
                id_item = 0;
            }
            try {
                filtro = pageContext.getRequest().getAttribute("filtro").toString();
            } catch (Exception e) {
                filtro = "";
            }
            if (id_item > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR ITEM VERIFICACION">
                lst_id_itemVer = jpa_itemver.consultaItemsVerificacionId(id_item);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Ítem</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_id_itemVer != null) {
                    Object[] obj_itemVer = (Object[]) lst_id_itemVer.get(0);
                    out.print("<form action='Item_verificacion?opc=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='hidden' name='id_item' value='" + obj_itemVer[0] + "'>");
                    out.print("<input type='text' class='form-control' name='txt_descripcion' id='txt_descripcion' value='" + obj_itemVer[1] + "' placeholder='Descripción' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-12'>");
                    out.print("<input type='text' class='form-control' name='txt_medida' id='txt_medida' value='" + obj_itemVer[2] + "' placeholder='Medida/Standrard' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Medida/Standrard'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                    out.print("</div>");

                    out.print("</form>");
                } else {
                    out.print("<h2>Fallo en información, favor comunicarse con T.I</h2>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR ITEM VERIFICACIÓN">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Ítem</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Item_verificacion?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_descripcion' id='txt_descripcion' placeholder='Descripción' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_medida' id='txt_medida' placeholder='Medida/Standrard' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Medida/Standrard'>");
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
            //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_filtro'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Filtro</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Item_verificacion?opc=1' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='col-12 mb-2'>");
            out.print("<input type='text' class='form-control' name='filtro' id='filtro' placeholder='Texto a buscar..' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Texto a buscar'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-red btn-lg'>Consultar</button>");
            out.print("</div>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Módulo Ítem Verificación</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Item Verificación</h4>");

            out.print("<div style='display:flex;'>");

            if (!filtro.equals("")) {
                out.print("<div class='mr-3'>");
                out.print("<button class='btn btn-danger' style='border-radius: 4px;' onclick=\"javascript:location.href='Item_verificacion?opc=1'\" data-toggle='tooltip' data-placement='top' title='Quitar filtro'><i class='fas fa-times'></i></button>");
                out.print("</div>");
            }
            out.print("<div class='mr-3'>");
            out.print("<button class='btn btn-info' style='border-radius: 4px;' onclick='mostrarConvencion(4)' data-toggle='tooltip' data-placement='top' title='Filtro de busqueda'><i class='fas fa-search'></i></button>");
            out.print("</div>");

            out.print("<div>");
            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("<div></div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            //<editor-fold defaultstate="collapsed" desc="TABLA LISTA DE VERIFICACION">
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th>Medida/Standard</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (filtro.equals("")) {
                lst_itemVer = jpa_itemver.consultaItemsVerificacion();
            } else {
                lst_itemVer = jpa_itemver.consultaFitlroPalabra(filtro);
            }
            if (lst_itemVer != null) {
                for (int i = 0; i < lst_itemVer.size(); i++) {
                    Object[] obj_itemVer = (Object[]) lst_itemVer.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_itemVer[0] + "</td>");
                    out.print("<td>" + obj_itemVer[1] + "</td>");
                    out.print("<td>" + obj_itemVer[2] + "</td>");
                    out.print("<td><a href='Item_verificacion?opc=1&id_item=" + obj_itemVer[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a></td>");
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
        } catch (Exception ex) {
            Logger.getLogger(Tag_item_verificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
