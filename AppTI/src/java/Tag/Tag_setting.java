package Tag;

import Controller.RoleControllerJpa;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_setting extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_role = null;

        HttpSession sesion = pageContext.getSession();

        int idRol = 0;
        String txtPermissions = "";
        try {
            idRol = Integer.parseInt(sesion.getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            
            out.print("<h1>Configuración del sistema</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-body'>");
            out.print("<div class='row'>");

            if (txtPermissions.contains("[22]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class='fas fa-file-code'></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Formatos</h4>");
                out.print("<p>Modulo encargado de crear, gestionar y modificar los formatos de registros que se manejan en el área.</p>");
                out.print("<a href='Format?opt=1' class='card-cta'>Configuración<i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }

            if (txtPermissions.contains("[6]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class='fas fa-user-shield'></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Roles y permisos</h4>");
                out.print("<p>Módulo encargado de crear, gestionar los roles y pemisos del sistema.</p>");
                out.print("<a href='Role?opt=1' class='card-cta'>Configuración <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }

            if (txtPermissions.contains("[17]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class='fas fa-user-clock'></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Programacion de turnos</h4>");
                out.print("<p>Modulo encargado de la programación semanal de turnos para el personal del área.</p>");
                out.print("<a href='Shift?opt=1' class='card-cta'>Configurar <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }

            if (txtPermissions.contains("21")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class='fas fa-clipboard'></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Mi plantilla</h4>");
                out.print("<p>Modulo encargado de administrar la plantilla que aparece al registrar su bitacora.</p>");
                out.print("<a href='Template?opt=1' class='card-cta'>Configurar <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }

            if (txtPermissions.contains("[20]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class='fas fa-cogs'></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Configuracion Avanzada</h4>");
                out.print("<p>Modulo encargado de gestionar parametros de configuracion del sistema.</p>");
                out.print("<a href='advConfig?opt=1' class='card-cta'>Configurar <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }

            if (txtPermissions.contains("[1]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class='fas fa-users'></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Usuarios</h4>");
                out.print("<p>Modulo encargado de crear y gestionar los usuarios del sistema.</p>");
                out.print("<a href='User?opt=1' class='card-cta text-primary'>Configurar <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            
            if (txtPermissions.contains("[1]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class=\"fas fa-tablet-alt\"></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Aplicativos</h4>");
                out.print("<p>Modulo encargado de crear y gestionar los aplicativos de Tecnología de Información.</p>");
                out.print("<a href='Application?opt=1' class='card-cta text-primary'>Configurar <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            
            if (txtPermissions.contains("[1]")) {
                out.print("<div class='col-lg-6'>");
                out.print("<div class='card card-large-icons'>");
                out.print("<div class='card-icon bg-primary text-white'>");
                out.print("<i class=\"fas fa-envelope-open-text\"></i>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<h4>Verificador Correo</h4>");
                out.print("<p>Modulo encargado de crear y gestionar los aplicativos de Tecnología de Información.</p>");
                out.print("<a href='CheckerMail?opt=1' class='card-cta text-primary'>Configurar <i class='fas fa-chevron-right'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
