package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.TemplateControllerJpa;
import java.util.List;

public class Tag_template extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        List lst_tempalte = null;
        int idUser = 0, idTempl = 0;
        try {
            idUser = Integer.parseInt(pageContext.getRequest().getAttribute("IdUser").toString());
        } catch (Exception e) {
            idUser = 0;
        }
        try {
            idTempl = Integer.parseInt(pageContext.getRequest().getAttribute("idTempl").toString());
        } catch (Exception e) {
            idTempl = 0;
        }
        try {
            if (idTempl > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDIT TEMPLATE">
                lst_tempalte = TemplateJpa.ConsultTemplateId(idTempl);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Plantilla </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_tempalte != null) {
                    Object[] ObTempl = (Object[]) lst_tempalte.get(0);
                    out.print("<form action='Template?opt=2&idTempl=" + ObTempl[0] + "' method='post' class='needs-validation' novalidate=''>");
                    out.print("<span class='text-dark'>Nombre de la plantilla</span>");
                    out.print("<input type='text' name='txtTitle' class='form-control col-lg-10' value='" + ObTempl[2] + "' required>");
                    out.print("<span class='text-dark'>Contenido de la plantilla</span>");
                    out.print("<textarea id='editorCK' name='txtTemplate' placeholder=''>");
                    out.print(ObTempl[3]);
                    out.print("</textarea>");
                    out.print("<div class=''>");
                    out.print("<button class='btn btn-green' onclick='cargarDatos()'>Editar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<h2>Ha ocurrido un error al consultar los datos.</h2>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="NEW TEMPLATE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral' style='width: 44%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Plantilla</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Template?opt=3' method='post' class='needs-validation' novalidate=''>");
            out.print("<span class='text-dark'>Nombre de la plantilla</span>");
            out.print("<input type='text' class='form-control col-lg-10' name='txtTitle' id='' required>");
            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green' onclick='cargarDatos()'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN">
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Setting.jsp\";cargarDatos()'><i class='fas fa-arrow-left'></i></button>");
            out.print("<h2>Plantillas</h2>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            lst_tempalte = TemplateJpa.ConsultTemplatexIdUser(idUser);

            out.print("<div class='row' style='justify-content: center;'>");
            if (lst_tempalte != null) {
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                for (int i = 0; i < lst_tempalte.size(); i++) {
                    Object[] ObjTem = (Object[]) lst_tempalte.get(i);
                    int ste = Integer.parseInt(ObjTem[4].toString());
                    out.print("<div class='col-lg-3 SquareTemplate mr-4 mt-4'>");
                    out.print("<div class='d-flex' style='align-items: center;'>");
                    out.print("<div class='col-lg-8'>");
                    out.print("<h6 style='margin: 0px;'>" + ObjTem[2] + "</h6>");
                    out.print("<span class=''>" + ((ste == 1) ? "Activo" : "Inactivo") + " <span class='bullet text-" + ((ste == 1) ? "success" : "danger") + "'></span> </span>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<div class='d-flex' style='justify-content: flex-end;'>");
                    out.print("<button class='btn btn-green btn-sm mr-2' onclick='window.location.href=\"Template?opt=4&idTempl=" + ObjTem[0] + "\";cargarDatos()'><i class='fas fa-" + ((ste == 1) ? "times" : "check") + "'></i></button>");
                    out.print("<button class='btn btn-warning btn-sm' onclick='window.location.href=\"Template?opt=1&idTempl=" + ObjTem[0] + "\";cargarDatos()'><i class='fas fa-pen'></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }else{
                out.print("<div class=''>");
                out.print("<h3> No se ha encontrado ninguna plantilla! </h3>");
                out.print("</div>");
            }
            out.print("</div>");
            //<editor-fold defaultstate="collapsed" desc="old">
//            if (lst_tempalte != null) {
//                Object[] ObjTempl = (Object[]) lst_tempalte.get(0);
//                out.print("<form action='Template?opt=2&idUser=" + idUser + "' method='post'>");
//                out.print("<div class=''>");
//                out.print("<label>Ingresar contenido: </label>");
//                out.print("<textarea id='editorCK' placeholder='Datos de la plantilla..' name='txtTemplate' data-toggle='tooltip' data-placement='top' title='Plantilla'>" + ((ObjTempl[2] == null) ? "" : ObjTempl[2].toString()) + "</textarea>");
//                out.print("</div>");
//                out.print("<div class='text-center'>");
//                out.print("<button class='btn btn-green'>Actualizar</button>");
//                out.print("</div>");
//                out.print("</form>");
//            } else {
//                out.print("<form action='Template?opt=3&idUser=" + idUser + "' method='post'>");
//                out.print("<div class=''>");
//                out.print("<label>Ingresar contenido: </label>");
//                out.print("<textarea id='editorCK' placeholder='Datos de la plantilla..' name='txtTemplate' data-toggle='tooltip' data-placement='top' title='Plantilla'></textarea>");
//                out.print("</div>");
//                out.print("<div class='text-center'>");
//                out.print("<button class='btn btn-green'>Actualizar</button>");
//                out.print("</div>");
//                out.print("</form>");
//            }
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_template.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
