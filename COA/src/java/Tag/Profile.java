package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.UserControllerJpa;
import java.util.List;

public class Profile extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        JspWriter out = pageContext.getOut();
        String NameUser = session.getAttribute("Nombres").toString();
        String Name = session.getAttribute("Nombre").toString();
        String NameRol = session.getAttribute("NombreRol").toString();
        int IdUser = Integer.parseInt(session.getAttribute("idUsuario").toString());
        UserControllerJpa UserJpa = new UserControllerJpa();
        List lst_user = null;
        try {

            out.print("<section class='section'>");

            out.print("<div class='section-header'>");
            out.print("<h1>Perfil</h1>");
            out.print("</div>");

            out.print("<div class='section-body'>");
            out.print("<h2 class='section-title'>Hola, " + Name + "!</h2>");
            out.print("<p class='section-lead'>Aqui puede modificar su información.</p>");

            out.print("<div class='row mt-sm-4'>");

            out.print("<div class='col-12 col-md-12 col-lg-5'>");
            out.print("<div class='card profile-widget'>");

            out.print("<div class='profile-widget-header'>");
            out.print("<img alt='image' src='Interface/Content/Assets/img/avatar/avatar-7.png' class='rounded-circle profile-widget-picture'>");

            out.print("<div class='profile-widget-items'>");

            out.print("</div>"); // profile-widget-items
            out.print("</div>"); // profile-widget-header

            out.print("<div class='profile-widget-description'>");
            out.print("<div class='profile-widget-name'><div class='CardHeaderSig'>" + NameUser + " <div class='text-muted d-inline font-weight-normal'><div class='slash'></div> " + NameRol + "</div></div></div>");
            lst_user = UserJpa.ConsultUsersid(IdUser);
            if (lst_user != null) {
                Object[] ObjUser = (Object[]) lst_user.get(0);
                out.print("<form method='post'action=\"UpdateSignatureUser\"enctype='multipart/form-data' class='needs-validation' novalidate>");
                out.print("<input type='hidden' name='idUser' value='" + IdUser + "'>");
                out.print("<div class='row'>");
                out.print("<div class='form-group col-md-6 col-12'>");
                out.print("<label>Firma (Imagen)</label>");
                out.print("<input type='file' class='form-control' name='File' id='IdFile' required onchange='validarNombreArchivo(this)'>");
                out.print("</div>");
                String firma = ObjUser[8] != null ? ObjUser[8].toString() : "";
                if (!firma.isEmpty()) {
                    out.print("<div class='form-group col-md-6 col-12'>");
                    out.print("<label>Firma actual</label><br>");
                    out.print("<img src='Interface/Uploads/Signature/" + firma + "' alt='Firma' style='height:100px; border:1px solid #ccc; padding:5px;'>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("<div class='card-footer text-right' style='padding: 0px 25px;'>");
                out.print("<button class='btn btn-primary'>Actualizar Firma</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
            }

            out.print("</div>"); // card profile-widget
            out.print("</div>"); // col izquierda

            out.print("<div class='col-12 col-md-12 col-lg-7'>");
            out.print("<div class='card'>");

            if (lst_user != null) {
                Object[] ObjUser = (Object[]) lst_user.get(0);
                out.print("<form method='post' action='Profile?opt=2' class='needs-validation' novalidate>");
                out.print("<input type='hidden' name='idUser' value='" + IdUser + "'>");
                out.print("<div class='card-header'><h4>Editar Perfil</h4></div>");
                out.print("<div class='card-body'>");
                out.print("<div class='row'>");
                out.print("<div class='form-group col-md-6 col-12'>");
                out.print("<label>Nombre</label>");
                out.print("<input type='text' class='form-control' name='name' value='" + ObjUser[1] + "' required>");
                out.print("<div class='invalid-feedback'>Please fill in the first name</div>");
                out.print("</div>");

                out.print("<div class='form-group col-md-6 col-12'>");
                out.print("<label>Apellido</label>");
                out.print("<input type='text' class='form-control' name='lastname' value='" + ObjUser[2] + "' required>");
                out.print("<div class='invalid-feedback'>Please fill in the last name</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='row'>");
                out.print("<div class='form-group col-md-6 col-12'>");
                out.print("<label>Documento</label>");
                out.print("<input type='text' class='form-control' name='document' value='" + ObjUser[3] + "' required>");
                out.print("<div class='invalid-feedback'>Please fill in the first name</div>");
                out.print("</div>");

                out.print("<div class='form-group col-md-6 col-12'>");
                out.print("<label>Codigo</label>");
                out.print("<input type='int' class='form-control' name='code' value='" + ObjUser[4] + "' required>");
                out.print("<div class='invalid-feedback'>Please fill in the last name</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='row'>");
                out.print("<div class='form-group col-md-6 col-12'>");
                out.print("<label>Usuario</label>");
                out.print("<input type='int' class='form-control' name='user' value='" + ObjUser[5] + "' required>");
                out.print("<div class='invalid-feedback'>Please fill in the last name</div>");
                out.print("</div>");

                out.print("<input type='hidden' name='pass2' value='" + ObjUser[6] + "'>");

                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Contraseña</label>");
                out.print("<div class='d-flex align-items-baseline'>"
                        + "<div><input type=\"password\" style='width:248px' class=\"form-control\" id='pass-input' autocomplete=\"new-password\"name='pass' value=''></div>");
                out.print("<div><button type='button' class='btn btn-info btn-sm' onclick='mostrarAlertaPass()' style='margin-left:10px;'><i class=\"fas fa-question\"></i></button></div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>"); // card-body

                out.print("<div class='card-footer text-right'>");
                out.print("<button class='btn btn-primary'>Guardar</button>");
                out.print("</div>");

                out.print("</form>");
            }

            out.print("</div>");
            out.print("</div>"); // col derecha

            out.print("</div>"); // row mt-sm-4
            out.print("</div>"); // section-body

            out.print("</section>");

        } catch (Exception ex) {
            Logger.getLogger(Profile.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
