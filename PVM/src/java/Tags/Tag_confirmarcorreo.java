package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.UsuarioJpaController;
import Controladores.RolJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import Controladores.NoConformidadJpaController;

public class Tag_confirmarcorreo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        NoConformidadJpaController jpa_noconforme = new NoConformidadJpaController();
        try {

            int est = (Integer) pageContext.getRequest().getAttribute("est");
            List lst_confirmar = (List) pageContext.getRequest().getAttribute("Correo_plantilla");
            String plantilla = "";
            Object[] obj_visor = (Object[]) lst_confirmar.get(0);
            plantilla = obj_visor[3].toString();
            int estado = (Integer) obj_visor[5];
            plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");

            out.print("<link type='text/css' rel='stylesheet' href='Interfaz/HTML_Editor/jquery-te-1.4.0.css'>");
            out.print("<script type='text/javascript' src='Interfaz/HTML_Editor/jquery-te-1.4.0.min.js' charset='utf-8'></script>");

            //<editor-fold defaultstate="collapsed" desc="LISTADO PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header' style='justify-content: space-around;'>");
            out.print("<h1>Revision reporte no conformidad</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
//            out.print("<div class='card-header' style='justify-content: space-between;'>");
//            out.print("<h3>Confirmacion de correo</h3>");
////            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
//            out.print("<a href='#' class='btn btn-green' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Revisar'><i class='fas fa-envelope'></i></a>");
//            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            
            out.print("<div class='mb-3' style='display: flex; float: right;'>");            
            if (est == 0) {
                String usu = pageContext.getRequest().getAttribute("usu").toString();
                if (estado == 2 || estado == 3) {
                    jpa_noconforme.modificarEstadoRegistroNoConformidad(Integer.parseInt(obj_visor[4].toString()), 3);
                    out.print("<script>");
                    out.print("function editable(){");
                    out.print("document.getElementById(\"obs\").contentEditable = \"true\"; ");
                    out.print("document.getElementById(\"firma\").innerHTML = \"" + usu + "\" ");
                    out.print("}");
                    out.print("</script>");

                    out.print("<div class='' id='finalizar'>");
                    out.print("<a href='ConfirmarMail?opc=2&id=" + obj_visor[4] + "&est=0&usu=" + usu + "' class='btn btn-green mr-2' data-toggle='tooltip' data-placement='top' title='Finalizar'><i class='fas fa-check'></i></a>");
                    out.print("</div>");
                    out.print("<button class='btn btn-green' onclick='platilla()' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");

                } else if (estado == 4) {
                    plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                    out.print("<b> REVISADO </b>");
                }
                out.print("<form action='ConfirmarMail?opc=3' method='post' id='formP' name='formP'>");
                out.print("<input type='hidden' name='txt_plantilla' id='plantilla-id' >");
                out.print("<input type='hidden' name='idrgt' value=" + obj_visor[4] + " >");
                out.print("<input type='hidden' name='idins' value=" + obj_visor[0] + " >");
                out.print("<input type='hidden' name='usu' value='" + usu + "'>");
                out.print("<input type='hidden' name='est' value='" + 0 + "'>");
                out.print("</form>");
                out.print("<script>");
                out.print("function editable2(){}");
                out.print("</script>");
            } else if (estado == 2) {
                String usu = pageContext.getRequest().getAttribute("usu").toString();
                if (estado == 2 || estado == 3 || estado == 4) {
                    out.print("<div class=''>");
                    out.print("<script>");
                    out.print("function editable(){");
                    out.print("document.getElementById('obsdrto').contentEditable = true;");
                    out.print("document.getElementById(\"firmadrto\").innerHTML = '" + usu + "';");
                    out.print("}");
                    out.print("</script>");
                    out.print("<button class='btn btn-green' onclick='platilla()' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></button>");
                    out.print("</div>");
                }
                out.print("<form action='ConfirmarMail?opc=3' method='post' id='formP' name='formP'>");
                out.print("<input type='hidden' name='txt_plantilla' id='plantilla-id'>");
                out.print("<input type='hidden' name='idrgt' value='" + obj_visor[4] + "'>");
                out.print("<input type='hidden' name='idins' value='" + obj_visor[0] + "'>");
                out.print("<input type='hidden' name='usu' value='" + usu + "'>");
                out.print("<input type='hidden' name='est' value='" + 2 + "'>");
                out.print("</form>");
                out.print("<script>");
                out.print("function editable2(){");
                out.print(" var x = document.getElementById(\"obsdrto\").innerHTML;");
                out.print("if (x != \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\") {");
                out.print("document.getElementById(\"obsdrto\").contentEditable = \"false\";");
                out.print("document.getElementById(\"guardar\").style.display='none';");
                out.print("}else{");
                out.print("document.getElementById(\"guardar\").style.display='initial';");
                out.print("}");
                out.print("}");
                out.print("</script>");
            }
            out.print("</div>");

            out.print("<h5>Registro no conforme <b style='color: black;'>" + obj_visor[1] + "</b><b class='subTitle2'> Serial: </b> " + obj_visor[2] + "</b></h5>");
            out.print("<div class=''>");
            out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>"+ plantilla +"</textarea>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

            out.print("<script>");
                out.print("$('.jqte-test').jqte();");
                out.print(" var jqteStatus = true;");
                out.print("$('.status').click(function()");
                out.print("{");
                out.print("jqteStatus = jqteStatus ? false : true;");
                out.print("$('.jqte-test').jqte({'status' : jqteStatus})");
                out.print(" });");
                out.print("</script>");

            out.print("<script>");
            out.print(" var y = document.getElementById(\"obs\").innerHTML; ");
            out.print(" if (y === \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\") { ");
            out.print(" document.getElementById(\"finalizar\").style.display='none'; ");
            out.print(" }else{ ");
            out.print(" document.getElementById(\"finalizar\").style.display='initial'; ");
            out.print(" } ");
            out.print("</script>");

//</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_confirmarcorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
