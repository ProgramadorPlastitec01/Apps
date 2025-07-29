package Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_checkMail extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        String resultMail = "";
        try {
            resultMail = pageContext.getRequest().getAttribute("resultMail").toString();
        } catch (Exception e) {
            resultMail = "";
        }

        String Host = "", Port = "", Mail = "", Passw = "", Receptor = "";

        //<editor-fold defaultstate="collapsed" desc="VARIABLE">
        try {
            Host = pageContext.getRequest().getAttribute("Host").toString();
        } catch (Exception e) {
            Host = "Sin datos...";
        }
        try {
            Port = pageContext.getRequest().getAttribute("Port").toString();
        } catch (Exception e) {
            Port = "Sin datos...";
        }
        try {
            Mail = pageContext.getRequest().getAttribute("Mail").toString();
        } catch (Exception e) {
            Mail = "Sin datos...";
        }
        try {
            Passw = pageContext.getRequest().getAttribute("Passw").toString();
        } catch (Exception e) {
            Passw = "Sin datos...";
        }
        try {
            Receptor = pageContext.getRequest().getAttribute("Receptor").toString();
        } catch (Exception e) {
            Receptor = "Sin datos...";
        }
//</editor-fold>

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-around;'>");
            out.print("<h2> VERIFICADOR DE CORREO</h2>");
//            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
//            out.print("<div class='card-body'>");


            out.print("<div class='d-flex' style='justify-content: center; margin-top: 3%;'>");
            out.print("<div class='card-body col-lg-5' style='box-shadow: 0px 0px 8px 0px #adadad;margin: 10px;' id='ContFormData'>");
            //<editor-fold defaultstate="collapsed" desc="LEFT FORM">
            out.print("<div class='text-center'>");
            out.print("<h4>Datos de conexión</h4>");
            out.print("</div>");

            out.print("<form action='CheckerMail?opt=1' method='post' class='needs-validation' novalidate='' id='formCheckMail'>");
            out.print("<div class='mb-2'>");
            out.print("<span class=''>Host:</span>");
            out.print("<input type='text' class='form-control' name='txtHost' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
            out.print("</div>");

            out.print("<div class='mb-2'>");
            out.print("<span class=''>Puerto:</span>");
            out.print("<input type='text' class='form-control' name='txtPort' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
            out.print("</div>");

            out.print("<div class='mb-2'>");
            out.print("<span class=''>Correo:</span>");
            out.print("<input type='mail' class='form-control' name='txtMail' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
            out.print("</div>");

            out.print("<div class='mb-2'>");
            out.print("<span class=''>Contraseña:</span>");
            out.print("<div class='d-flex'>");
            out.print("<input type=\"password\" name=\"fake-pass\" style=\"display:none\">");
            out.print("<input type='password' class='form-control' name='txtfakePsw' id='txtPassw' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='' required autocomplete='new-password'>");
            out.print("<div class='StlViewPass' onclick='mostrarPass()' id='show_password' style='cursor: pointer;'>");
            out.print("<i id='icon' class='fas fa-eye iconEye'></i>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='mb-2'>");
            out.print("<span class=''>Correo receptor:</span>");
            out.print("<input type='mail' class='form-control' name='txtReceptor' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
            out.print("</div>");

            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green' type='button' onclick='ValidMail()'>Comprobar</button>");
            out.print("</div>");

            out.print("</form>");
            //</editor-fold>
            out.print("</div>");

            out.print("<div class='col-lg-2' style='display: none' id='contLoader'>");
            //<editor-fold defaultstate="collapsed" desc="CENTER">
            out.print("<div style='margin-top: 120%; text-align: center;' >");
            out.print("<div class='d-flex' style='justify-content: space-between; align-items: center;'>");
            out.print("<i class=\"fas fa-desktop\" style='font-size: 25px;color: #0b0025;'></i>");
            out.print("<i class=\"fas fa-spinner fa-pulse\" style='font-size: 40px; color: #33bf98;'></i>");
            out.print("<i class=\"fas fa-paper-plane\" style='font-size: 25px;color: #0b0025;'></i>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");

            out.print("<div class='card-body col-lg-5' style='box-shadow: 0px 0px 8px 0px #adadad;margin: 10px;'>");
            //<editor-fold defaultstate="collapsed" desc="RIGHT SIDE">
            out.print("<div class='text-center'>");
            out.print("<h4>Prueba de conexión</h4>");
            out.print("</div>");

            out.print("<div class=''>");
            out.print("<h6 class='mt-2'><b>Host:</b></h6> <span class='ml-4'>" + Host + "</span><br>");
            out.print("<h6 class='mt-2'><b>Puerto:</b></h6> <span class='ml-4'>" + Port + "</span><br>");
            out.print("<h6 class='mt-2'><b>Correo:</b></h6> <span class='ml-4'>" + Mail + "</span><br>");
            if (Passw.contains("Sin datos")) {
                out.print("<h6 class='mt-2'><b>Contraseña:</b></h6> <span class='ml-4'>" + Passw + "</span><br>");
            } else {
                out.print("<h6 class='mt-2'><b>Contraseña:</b></h6> <span class='ml-4 password-hidden' id='spanPw'>" + Passw + "</span> &nbsp; <i id='iconz' onclick='mostrarPass2()' class='fas fa-eye eyeIcom'></i><br>");
            }
            out.print("<h6 class='mt-2'><b>Correo receptor:</b></h6> <span class='ml-4'>" + Receptor + "</span><br>");
            out.print("</div>");

            out.print("<div class='mt-4'>");
            out.print("<h6>Resultado:</h6> ");
            if (!resultMail.equals("")) {
                out.print("<form action='CheckerMail?opt=1' method='post' name='formRetry'>");
                out.print("<input type='hidden' name='txtHost' value='" + Host + "'>");
                out.print("<input type='hidden' name='txtPort' value='" + Port + "'>");
                out.print("<input type='hidden' name='txtMail' value='" + Mail + "'>");
                out.print("<input type='hidden' name='txtfakePsw' value='" + Passw + "'>");
                out.print("<input type='hidden' name='txtReceptor' value='" + Receptor + "'>");

                out.print("<div class='text-center'>");
                if (resultMail.contains("exitosamente")) {
                    out.print("<div class='text-center'> <span class='ml-4 text-success' style='font-size: 20px;'><b>" + resultMail + "</b></span> <i class=\"fas fa-check-circle text-success\" style='font-size: 20px;'></i></div>");
                    out.print("<button class='btn btn-success mt-3' onclick='formRetry.submit()'><i class=\"fas fa-undo-alt\"></i> Reintentar </button>");
                } else {
                    out.print("<div class='text-center'> <span class='ml-4 text-danger' style='font-size: 20px;'><b>" + resultMail + "</b></span> <i class=\"fas fa-exclamation-triangle text-danger\" style='font-size: 20px;'></i></div>");
                    out.print("<button class='btn btn-warning mt-3' onclick='formRetry.submit()'><i class=\"fas fa-undo-alt\"></i> Reintentar </button>");
                }
                out.print("</div>");

                out.print("</form>");
            }
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");

//            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception e) {
        }

        return super.doStartTag();
    }
}
