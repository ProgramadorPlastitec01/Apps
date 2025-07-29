package Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_trackingItem extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        List lst_result = null;
        try {
            lst_result = (List<String>) pageContext.getRequest().getAttribute("ResultDataSearch");
        } catch (Exception e) {
            lst_result = null;
        }

        try {
            try {

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Firmar </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h2>Seguimiento a Items</h2>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                //<editor-fold defaultstate="collapsed" desc="SEARCH FORM">
                out.print("<div id='accordion'>");
                out.print("<div class='accordion'>");
                out.print("<div id='accFunc' class='accordion-header text-center " + ((lst_result != null) ? "collapsed" : "") + "' role='button' data-toggle='collapse' data-target='#panel-body-1' aria-expanded='" + ((lst_result == null) ? "true" : "false") + "'>");
                out.print("<h4>Filtro de busqueda <i class='fas fa-search'></i></h4>");
                out.print("</div>");
                out.print("<div class='accordion-body collapse " + ((lst_result == null) ? "show" : "") + "' id='panel-body-1' data-parent='#accordion'>");
                out.print("<div class='text-center mt-3'>");
                out.print("<h4>Buscar movimientos realizados:</h4>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<form action='TrackingItem?opt=1' method='post' class='needs-validation' novalidate='' id='FormMoveSearch'>");
                out.print("<div class='row'>");
                out.print("<div class='col-lg-4 mt-3'>");
                out.print("<input type='number' class='form-control' name='txt_numItem' id='id_numItem' data-toggle='tooltip' data-placement='top' title='Num. Item' placeholder='Num. Item' value=''>");
                out.print("</div>");
                out.print("<div class='col-lg-4 mt-3'>");
                out.print("<input type='text' class='form-control' name='txt_Ref' id='id_ref' data-toggle='tooltip' data-placement='top' title='Referencia' placeholder='Referencia' value=''>");
                out.print("</div>");
                out.print("<div class='col-lg-4 mt-3'>");
                out.print("<input type='date' class='form-control' name='txt_dateMove' id='id_dateMove' data-toggle='tooltip' data-placement='top' title='Fecha Movimiento' placeholder='Fecha Movimiento' value=''>");
                out.print("</div>");
                out.print("<div class='col-lg-4 mt-3'>");
                out.print("<input type='number' class='form-control' name='txt_numMov' id='id_numMov' data-toggle='tooltip' data-placement='top' title='Num. Movimiento' placeholder='Num. Movimiento' value=''>");
                out.print("</div>");
                out.print("<div class='col-lg-4 mt-3'>");
                out.print("<input type='text' class='form-control' name='txt_keyword' id='id_keyword' data-toggle='tooltip' data-placement='top' title='Palabra clave' placeholder='Palabra clave' value=''>");
                out.print("</div>");
                out.print("<div class='col-lg-4 mt-3'>");
                out.print("<button class='btn btn-green' type='button' style='width: 100%;' onclick='validformSearch()'>Buscar.. <i class='fas fa-search'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                if (lst_result != null) {
                    out.print("<div class='card'>");
                    out.print("<div class='card-body'>");
                    out.print("<form action='' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='' id='idItmeToSig' value=''>");
                    out.print("<div class='text-right' style='margin: 10px;'>");
                    out.print("<button class='btn btn-green' id='btnUserSign' style='display: none;'>Firmar</button>");
                    out.print("</form>");
                    out.print("</div>");

                    out.print("<table class='table table-bordered' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr class='text-center'>");
                    out.print("<th>Movimiento</th>");
                    out.print("<th>Fecha Mov.</th>");
                    out.print("<th>Item</th>");
                    out.print("<th>Referencia</th>");
                    out.print("<th>Ubicacion</th>");
                    out.print("<th>Modelo</th>");
                    out.print("<th>Serial</th>");
                    out.print("<th>Observacion</th>");
                    out.print("<th>Responsable</th>");
                    out.print("<th>Usuario</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>"); 
                    for (int i = 0; i < lst_result.size(); i++) {
                        Object[] ObjRs = (Object[]) lst_result.get(i);
                        out.print("<tr>");
                        out.print("<td class='text-center'><span class='badge badge-" + ((ObjRs[6].toString().contains("ENT")) ? "success" : "info") + " text-dark' style='font-weight: 700;'>" + ObjRs[6] + ObjRs[7] + "</span></td>");
                        out.print("<td>" + ObjRs[1] + "</td>");
                        out.print("<td>" + ObjRs[3] + "</td>");
                        out.print("<td data-toggle='tooltip' data-placement='top' title='" + ObjRs[5] + "'><span>" + ObjRs[4] + "</span></td>");
                        out.print("<td>" + ObjRs[8] + "</td>");
                        out.print("<td>" + ObjRs[9] + "</td>");
                        out.print("<td>" + ObjRs[10] + "</td>");
                        out.print("<td>" + ObjRs[11] + "</td>");
                        out.print("<td>" + ObjRs[13] + "</td>");
                        if (ObjRs[14] != null) {
                            out.print("<td>" + ObjRs[14] + "</td>");
                        } else {
                            out.print("<td class='text-center'>");
                            out.print("<button id='btnSelec" + i + "' class='btn btn-green btn-sm' onclick='SelectedSigna(" + i + ", " + ObjRs[0] + ")'><i class='fas fa-signature'></i></button>");
                            out.print("</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                } else {
                    out.print("<div class='card'>");
                    out.print("<div class='card-body text-center'>");
                    out.print("<h3>No se han encontrado resultados de la busqueda! </h3>");
                    out.print("<img src='Interface/Imagen/vacio.png' alt=''>");
                    out.print("</div>");
                    out.print("</div>");
                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_trackingItem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
