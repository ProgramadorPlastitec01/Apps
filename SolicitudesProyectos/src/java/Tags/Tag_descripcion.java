package Tags;

import Controladores.DescripcionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_descripcion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DescripcionJpaController jpa_descripcion = new DescripcionJpaController();
        List lst_descripcion = null;
        List lst_descripciones = null;
        int id_descripcion = 0;
        String filtro = "";
        try {
            try {
                id_descripcion = Integer.parseInt(pageContext.getRequest().getAttribute("id_descripcion").toString());
            } catch (Exception e) {
                id_descripcion = 0;
            }
            try {
                filtro = pageContext.getRequest().getAttribute("filtro").toString();
            } catch (Exception e) {
                filtro = "";
            }
            if (id_descripcion > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR DESCRPICION">
                lst_descripcion = jpa_descripcion.consultaDescripcionId(id_descripcion);
                Object[] obj_descripcion = (Object[]) lst_descripcion.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_defecto'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Descripción</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Descripcion?opc=3&idD=" + id_descripcion + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_desc' id='txt_desc' value='" + obj_descripcion[2] + "' placeholder='Descripción' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR DESCRIPCION">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_defecto'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Descripción</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Descripcion?opc=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='text' class='form-control' name='txt_desc' id='txt_desc' placeholder='Descripción' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descipción'>");
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

            out.print("<form action='Descripcion?opc=1' method='post' class='needs-validation' novalidate=''>");
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
            out.print("<h1>Modulo Descripción</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de descripción</h4>");

            out.print("<div style='display:flex;'>");

            if (!filtro.equals("")) {
                out.print("<div class='mr-3'>");
                out.print("<button class='btn btn-danger' style='border-radius: 4px;' onclick=\"javascript:location.href='Descripcion?opc=1'\" data-toggle='tooltip' data-placement='top' title='Quitar filtro'><i class='fas fa-times'></i></button>");
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
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            //<editor-fold defaultstate="collapsed" desc="TABLA DESCRIPCION">
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ID</th>");
            out.print("<th>Nombre</th>");
            out.print("<th style='text-align: center;'>Opc</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (!filtro.equals("")) {
                lst_descripciones = jpa_descripcion.consultaDescripcionFiltro(filtro);
            } else {
                lst_descripciones = jpa_descripcion.consultaDescripcion();
            }
            if (lst_descripciones != null) {
                for (int i = 0; i < lst_descripciones.size(); i++) {
                    Object[] obj_descripcion = (Object[]) lst_descripciones.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_descripcion[0] + "</td>");
                    out.print("<td>" + obj_descripcion[2] + "</td>");
                    int est = (Integer) obj_descripcion[3];
                    out.print("<td align='center'><a href='Descripcion?opc=1&idD=" + obj_descripcion[0] + "' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar'><i class='fas fa-pencil-alt'></i></a></td>");
                    out.print("<td align='center'><a href='Descripcion?opc=4&idD=" + obj_descripcion[0] + "&est=" + ((est == 1) ? "0" : "1") + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>" + "</td>");
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_descripcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
