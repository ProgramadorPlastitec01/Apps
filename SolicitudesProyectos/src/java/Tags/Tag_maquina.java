package Tags;

import Controladores.MaquinaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_maquina extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        MaquinaJpaController jpa_maquina = new MaquinaJpaController();
        List lst_maquinas = null;
        List lst_maquina = null;
        int id_maquina = 0;
        try {
            try {
                id_maquina = Integer.parseInt(pageContext.getRequest().getAttribute("id_maquina").toString());
            } catch (Exception e) {
                id_maquina = 0;
            }
            if (id_maquina > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR MAQUINA">
                lst_maquina = jpa_maquina.consultaMaquinaId(id_maquina);
                Object[] obj_maquina = (Object[]) lst_maquina.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_defecto'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Defecto</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Maquina?opc=3&idM=" + id_maquina + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_maquina' id='txt_maquina' value='" + obj_maquina[1] + "' placeholder='Nombre Maquina' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
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
            } else {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR MAQUINA">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_defecto'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar Maquina</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Maquina?opc=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='text' class='form-control' name='txt_maquina' id='txt_maquina' placeholder='Nombre Maquina' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Nombre Maquina'>");
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
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Maquina</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de maquina(s)</h4>");
            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            //<editor-fold defaultstate="collapsed" desc="TABLA MAQUINA">
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
            lst_maquinas = jpa_maquina.consultaMaquinas();
            if (lst_maquinas != null) {
                for (int i = 0; i < lst_maquinas.size(); i++) {
                    Object[] obj_maquinas = (Object[]) lst_maquinas.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_maquinas[0] + "</td>");
                    out.print("<td>" + obj_maquinas[1] + "</td>");
                    int est = (Integer) obj_maquinas[2];
                    out.print("<td align='center'><a href='Maquina?opc=1&idM=" + obj_maquinas[0] + "' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar'><i class='fas fa-pencil-alt'></i></a></td>");
                    out.print("<td align='center'><a href='Maquina?opc=4&idM=" + obj_maquinas[0] + "&est=" + ((est == 1) ? "0" : "1") + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>" + "</td>");
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
            Logger.getLogger(Tag_maquina.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
