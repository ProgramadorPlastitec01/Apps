package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.FormatControllerJpa;
import java.util.List;

public class Tag_format extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        List lst_format = null;
        int idfmt = 0, temp = 0;
        try {
            idfmt = Integer.parseInt(pageContext.getRequest().getAttribute("idfmt").toString());
        } catch (Exception e) {
            idfmt = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            if (idfmt > 0 && temp == 1) {
                //<editor-fold defaultstate="collapsed" desc="FORMAT DATA">
                lst_format = FormatJpa.ConsultFormatId(idfmt);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 80%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Modificar formato</h3>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                if (lst_format != null) {
                    Object[] ObjFomt = (Object[]) lst_format.get(0);
                    out.print("<div class='card'>");
                    out.print("<div class='card-header'>");
                    out.print("<h4>" + ObjFomt[1] + " - " + ObjFomt[2] + "</h4>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");
                    out.print("<ul class='nav nav-pills' id='myTab3' role='tablist'>");
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link active' id='home-tab3' data-toggle='tab' href='#home3' role='tab' aria-controls='home' aria-selected='true'>Editor de código</a>");
                    out.print("</li>");
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link' id='profile-tab3' data-toggle='tab' href='#profile3' role='tab' aria-controls='profile' aria-selected='false'>Vista previa</a>");
                    out.print("</li>");
                    out.print("</ul>");
                    out.print("<div class='tab-content' id='myTabContent2' style='overflow-y: auto;max-height: 430px;'>");
                    out.print("<div class='tab-pane fade show active' id='home3' role='tabpanel' aria-labelledby='home-tab3'>");
                    out.print("<form action='Format?opt=4&idfmt=" + ObjFomt[0] + "' method='post'>");
                    out.print("<textarea class='codeeditor' name='txtFormat'>" + ((ObjFomt[3] == null) ? "" : ObjFomt[3]) + "</textarea>");
                    out.print("<div style='position: absolute;margin-top: 9px;right: 50%;'>");
                    out.print("<button class='btn btn-green'>Actualizar</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<div class='tab-pane fade' id='profile3' role='tabpanel' aria-labelledby='profile-tab3'>");
                    out.print("<div class=''>");
                    out.print(((ObjFomt[3] == null) ? "Sin codigo registrado" : ObjFomt[3]));
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<h4>Se ha presentado un error al consultar la información</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>");
                out.print("const body = document.body;\n"
                        + "body.classList.toggle('modal-open');");
                out.print("</script>");
                //</editor-fold>
            } else if (idfmt > 0 && temp == 2) {
                //<editor-fold defaultstate="collapsed" desc="FORMAT UPDATER">
                lst_format = FormatJpa.ConsultFormatId(idfmt);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nueva version del formato </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_format != null) {
                    Object[] Objform = (Object[]) lst_format.get(0);
                    out.print("<form action='Format?opt=2&idfmt=" + Objform[0] + "&temp=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='d-flex justify-content-center'>");
                    out.print("<input type='text' class='form-control' name='txtFormat' value='" + Objform[3] + "' >");
                    out.print("<input type='text' class='form-control col-lg-3' name='txtCode' id='' value='" + Objform[1] + "' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' >");
                    out.print("<input type='text' class='form-control col-lg-4' name='txtName' id='' value='" + Objform[2] + "' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' >");
                    int val = Integer.parseInt(Objform[4].toString()) + 1;
                    out.print("<input type='number' class='form-control col-lg-3' name='nmbVersion' id='' value='" + val + "' placeholder='Version' data-toggle='tooltip' data-placement='top' title='Version' >");
                    out.print("</div>");
                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-green'>Registrar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<h4>Se ha presentado un error al consultar los datos</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (idfmt > 0 && temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="FORMAT EDITER">
                lst_format = FormatJpa.ConsultFormatId(idfmt);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar formato </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_format != null) {
                    Object[] Objform = (Object[]) lst_format.get(0);
                    out.print("<form action='Format?opt=2&idfmt=" + Objform[0] + "' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='d-flex justify-content-center'>");
                    out.print("<input type='text' class='form-control col-lg-3' name='txtCode' id='' value='" + Objform[1] + "' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' >");
                    out.print("<input type='text' class='form-control col-lg-4' name='txtName' id='' value='" + Objform[2] + "' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' >");
                    out.print("<input type='number' class='form-control col-lg-3' name='nmbVersion' id='' value='" + Objform[4] + "' placeholder='Version' data-toggle='tooltip' data-placement='top' title='Version' >");
                    out.print("</div>");
                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-green'>Confirmar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<h4>Se ha presentado un error al consultar los datos</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="FORMAT REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Nuevo formato </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Format?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='d-flex justify-content-center'>");
            out.print("<input type='text' class='form-control col-lg-3' name='txtCode' id='' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='Codigo' >");
            out.print("<input type='text' class='form-control col-lg-4' name='txtName' id='' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' >");
            out.print("<input type='number' class='form-control col-lg-3' name='nmbVersion' id='' placeholder='Version' data-toggle='tooltip' data-placement='top' title='Version' >");
            out.print("</div>");
            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
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
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'>"
                    + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" >"
                    + "<i class=\"far fa-hand-point-left\"></i>"
                    + "</button>"
                    + "</div>"
                    + "<h4>Listado de registros</h4>"
                    + "</div>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Codigo</th>");
            out.print("<th>Nombre</th>");
            out.print("<th>Version</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_format = FormatJpa.ConsultFormats();
            if (lst_format != null) {
                for (int i = 0; i < lst_format.size(); i++) {
                    Object[] ObjFormat = (Object[]) lst_format.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjFormat[1] + "</td>");
                    out.print("<td>" + ObjFormat[2] + "</td>");
                    out.print("<td>" + ObjFormat[4] + "</td>");
                    int state = Integer.parseInt(ObjFormat[5].toString());
                    out.print("<td><div class='badge badge-" + ((state == 1) ? "success'>Activo" : "danger'>Inactivo") + "</div></td>");
                    out.print("<td class='text-center'>");
                    out.print("<div class='d-flex justify-content-around'>");
                    if (state == 1) {
                        out.print("<button class='btn btn-success btn-sm' onclick='window.location.href=\"Format?opt=3&idfmt=" + ObjFormat[0] + "&state=0\"' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='fas fa-check'></i></button>");
                    } else {
                        out.print("<button class='btn btn-danger btn-sm' onclick='window.location.href=\"Format?opt=3&idfmt=" + ObjFormat[0] + "&state=1\"' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='fas fa-times'></i></button>");
                    }
                    out.print("<button class='btn btn-info btn-sm' onclick='window.location.href=\"Format?opt=1&idfmt=" + ObjFormat[0] + "&temp=1\"' data-toggle='tooltip' data-placement='top' title='Formato'><i class='fas fa-file'></i></button>");
                    out.print("<button class='btn btn-green btn-sm' onclick='window.location.href=\"Format?opt=1&idfmt=" + ObjFormat[0] + "&temp=2\"' data-toggle='tooltip' data-placement='top' title='Formato'><i class=\"fas fa-arrow-alt-circle-up\"></i></button>");
                    out.print("<button class='btn btn-warning btn-sm' onclick='window.location.href=\"Format?opt=1&idfmt=" + ObjFormat[0] + "\"' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></button>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='5' class='text-center'>Se ha presentado un error al consultar la información.</td>");
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
            Logger.getLogger(Tag_format.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
