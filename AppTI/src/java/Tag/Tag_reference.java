package Tag;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.ReferenceControllerJpa;
import SQL.ConnectionFactory;

public class Tag_reference extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        ReferenceControllerJpa ReferenceJpa = new ReferenceControllerJpa();
        ConnectionFactory FactoryJpa = new ConnectionFactory();
        List lst_reference = null;
        List lst_factory = null;
        String ref = "";

        try {
            ref = pageContext.getRequest().getAttribute("ref").toString();
        } catch (Exception e) {
            ref = "";
        }

        try {

            //<editor-fold defaultstate="collapsed" desc="REGISTER REFERENCE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + ((ref.equals("")) ? "none" : "block") + ";'>");
            out.print("<div class='contGeneral' style='width: 64%; right: 10%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Referencia</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");

            out.print("<form action='Reference?opt=1' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='text-center'>");
            out.print("<label>Ingresa referencia a consultar de factory:</label>");
            out.print("<input type='text' class='form-control text-center col-lg-6' name='txt_Ref' style='margin: auto; margin-bottom: 10px;' data-toggle='tooltip' data-placement='top' title='Referencia' placeholder='Referencia' value='' autocomplete='off' >");
            out.print("</div>");
            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green'>Consultar <i class='fas fa-search'></i></button>");
            out.print("</div>");
            out.print("</form>");

            if (!ref.equals("")) {

                lst_reference = ReferenceJpa.ConsultReferencesFact(ref);
                if (lst_reference != null) {
                    out.print("<div class=''>");
                    out.print("<div class='text-center mt-4'>");
                    out.print("<h4 class='text-info'>¡REFERENCIA YA ESTA REGISTRADA EN EL SISTEMA!</h4>");
                    out.print("<h6>Favor escribir otra referencia</h6>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    lst_factory = FactoryJpa.ConsulNewReference(ref);
                    if (lst_factory.size() > 0) {
                        String[] lstFact = lst_factory.toString().replace("[", "").replace("]", "").split(" / ");
                        out.print("<form action='Reference?opt=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<div class='text-center mt-4 text-success'>");
                        out.print("<h4>¡REFERENCIA ENCONTRADA!</h4>");
                        out.print("</div>");
                        out.print("<div class='row' style='justify-content: space-evenly; margin-top: 15px;'>");
                        out.print("<div class='col-lg-2'>");
                        out.print("<label class='' style='margin-bottom: 0;'>Referencia</label>");
                        out.print("<input type='text' class='form-control disabled' name='txt_Ref' id='' data-toggle='tooltip' data-placement='top' title='' value='" + lstFact[0] + "' >");
                        out.print("</div>");
                        out.print("<div class='col-lg-5'>");
                        out.print("<label class='' style='margin-bottom: 0;'>Nombre</label>");
                        out.print("<input type='text' class='form-control disabled' name='txt_RefName' id='' data-toggle='tooltip' data-placement='top' title='' value='" + lstFact[1] + "' >");
                        out.print("</div>");
                        out.print("<div class='col-lg-5'>");
                        out.print("<label class='' style='margin-bottom: 0;'>Proveedor</label>");
                        out.print("<input type='text' class='form-control disabled' name='txt_supplier' id='' data-toggle='tooltip' data-placement='top' title='' value='" + lstFact[3] + "' >");
                        out.print("</div>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<label class='' style='margin-bottom: 0;'>Marca</label>");
                        out.print("<input type='text' class='form-control' name='txt_brand' id='' data-toggle='tooltip' data-placement='top' title='' value='' autocomplete='off'>");
                        out.print("</div>");                        
                        out.print("<div class='col-lg-4'>");
                        out.print("<label class='' style='margin-bottom: 0;'>Ubicación</label>");
                        out.print("<input type='text' class='form-control' name='txt_location' id='' data-toggle='tooltip' data-placement='top' title='' value='' autocomplete='off'>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='text-center mt2'>");
                        out.print("<button class='btn btn-yellow'>Registrar Referencia</button>");
                        out.print("</div>");
                        out.print("</form>");
                    } else {
                        out.print("<div class=''>");
                        out.print("<div class='text-center mt-4'>");
                        out.print("<h4 class='text-warning'>¡REFERENCIA NO ENCONTRADA!</h4>");
                        out.print("<h6>Favor escribir otra referencia</h6>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                }
            }

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4></h4>");
            out.print("<h2>Referencias</h2>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Referencia</th>");
            out.print("<th>Nombre</th>");
            out.print("<th>Proveedor</th>");
            out.print("<th>Marca</th>");
            out.print("<th>Ubicacion</th>");
            out.print("<th>Ultima edicion</th>");
            out.print("<th>Registro</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_reference = ReferenceJpa.ConsultReferences();
            if (lst_reference != null) {
                for (int i = 0; i < lst_reference.size(); i++) {
                    Object[] ObjRef = (Object[]) lst_reference.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjRef[1] + "</td>");
                    out.print("<td>" + ObjRef[2] + "</td>");
                    out.print("<td>" + ObjRef[3] + "</td>");
                    out.print("<td>" + ObjRef[4] + "</td>");
                    out.print("<td>" + ObjRef[5] + "</td>");
                    out.print("<td>" + ObjRef[7] + "</td>");
                    out.print("<td>" + ObjRef[10] + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7' class='text-center'>No se ha encontrado información, registra la primera seleccionando la opcion <i class='fas fa-plus'></i></td>");
                out.print("</tr>");
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_reference.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_reference.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
