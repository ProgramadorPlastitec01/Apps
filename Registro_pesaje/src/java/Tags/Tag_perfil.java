package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.UsuarioJpaController;
import java.util.List;

public class Tag_perfil extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String Nombres = sesion.getAttribute("Nombre").toString();
        String Nombrese = sesion.getAttribute("Nombres").toString();
        String Apellido = sesion.getAttribute("Apellido").toString();
        String Rol = sesion.getAttribute("NombreRol").toString();
        String usuario = sesion.getAttribute("Usuario").toString();
        int id_userSesion = (Integer) sesion.getAttribute("idUsuario");
        int est = (Integer) sesion.getAttribute("Estado");
        int id_user = 0;

        UsuarioJpaController UsuarioJpa = new UsuarioJpaController();
        List lst_usuario = null;

        try {
            id_user = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
        } catch (Exception e) {
            id_user = 1;
        }
        try {
            //<editor-fold defaultstate="collapsed" desc="SELECCIONAR IMGS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 48%; height: 390px;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Seleccionar Icono</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='sel_imgs'>");
            out.print("<h4 style='text-align: center; margin-bottom: 4%;'><b>Cambiar icono</b></h4>");
            out.print("<center>");

            lst_usuario = UsuarioJpa.ConsultarLogoUsuario(id_userSesion);
            String photo = "";
            if (lst_usuario != null && lst_usuario.size() != 0) {
                Object[] obj_user = (Object[]) lst_usuario.get(0);
                out.print("<form action='Perfil?opc=2' method='post'>");
                out.print("<div id=\"myDIV\">");
                
                out.print("<div style='display: flex;'>");
                out.print("<a onclick='MasivoPhoto(\"angel.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("angel.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/angel.png' ></a>");
                out.print("<a onclick='MasivoPhoto(\"tapabocas.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("tapabocas.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/tapabocas.png' ></a>");
                out.print("<a onclick='MasivoPhoto(\"neutral.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("neutral.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/neutral.png' ></a>");
                out.print("<a onclick='MasivoPhoto(\"crying.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("crying.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/crying.png' ></a>");
                out.print("</div>");
                
                out.print("<div style='display: flex;'>");
                out.print("<a onclick='MasivoPhoto(\"strar.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("strar.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/strar.png' ></a>");
                out.print("<a onclick='MasivoPhoto(\"perv.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("perv.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/perv.png' ></a>");
                out.print("<a onclick='MasivoPhoto(\"alien.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("alien.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/alien.png' ></a>");
                out.print("<a onclick='MasivoPhoto(\"asleep.png\")'><img class=\"btn_bt " + ((obj_user[1].toString().equals("asleep.png")) ? "active2" : "") + "\" src='Interfaz/Contenido/Imagenes/asleep.png' ></a>");
                out.print("</div>");
                
                out.print("</div>");
                out.print("<div style='margin-top: 3%;'>");
                out.print("<input type='hidden' name='id_user' id='id_user' value='" + obj_user[0] + "'>");
                out.print("<input type='hidden' name='txt_img' id='txt_img' value='" + obj_user[1] + "'>");
                out.print("<button class='btn btn-primary'>Guardar <i class=\"fas fa-save\"></i></button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</center>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("</div>");
                out.print("</form>");
                photo = ""+ obj_user[1] +"";
            } else {
                out.print("<div>");
                out.print("<center>");
                out.print("<i style='font-size: 110px;' class=\"fas fa-exclamation-circle\"></i>");
                out.print("<p style='margin-top: 15px;'>¡Error! Debe salir y volver a iniciar sesion.</p>");
                out.print("<p>Si el problema persiste debe comunicarse al TI</p>");
                out.print("</center>");
                out.print("</div>");
            }

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CABECERA">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h3 class='page-title'> Perfil de usuario <i class='fas fa-pen'></i></h3>");
            out.print("</div>");
//            out.print("<div>");
//            out.print("<h3>Editar Perfil <i class=\"fas fa-pen\"></i></h3>");
//            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<a onclick='Confirmacion(" + id_userSesion + ")' class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white'>"
                    + "Reestablecer Contraseña <i class=\"fas fa-key\"></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DEL MODULO">
            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            
            out.print("<div class='white-box' style='display: flex; align-items: center; justify-content: space-evenly;'>");
            
            out.print("<div class='img_perfil' style='width: 20%;'>");
            out.print("<img src='Interfaz/Contenido/Imagenes/" + photo + "' style='width: 85%; border-radius: 50%;' onclick='mostrarConvencion(1)'>");
            out.print("</div>");
            
            out.print("<div style='width: 60%; text-align: left;'>");
            
            out.print("<div>");
            out.print("<h2> " + Nombrese + " </h2>");
            out.print("</div>");
            
            out.print("<div style='display: flex; justify-content: space-between; margin-top: 40px;'>");
            out.print("<div style='text-align: left;'>");
            out.print("<h4>Usuario: </h4>");
            out.print("<h2><b> " + usuario + "</b></h2>");
            out.print("</div>");
            out.print("<div style='text-align: left;'>");
            out.print("<h4>Rol: </h4>");
            out.print("<h2><b> " + Rol + "</b></h2>");
            out.print("</div>");
            out.print("<div style='text-align: left;'>");
            if (est == 1) {
                out.print("<h4>Estado:</h4><h2> <b style='color: green;'>Activo</b></h2>");
            } else {
                out.print("<h4>Estado:</h4><h2> <b style='color: red;'>In-actvo</b></h2>");
            }
            out.print("</div>");
            
            out.print("</div>");
            out.print("</div>");            
            
            out.print("</div>");            
            out.print("<div style='margin-top: 60px;'>");
            out.print("<form action='Perfil?opc=2&actu=1' method='post' style='padding: 20px; box-shadow: 0px 0px 5px 4px #e1e1e1;'>");
            out.print("<h4 style='text-align: center;'><b>Actualizar Datos</b></h4>");
            out.print("<div style='display: flex;width: 100%; justify-content: space-evenly;'>");

            out.print("<div style='width: 35%;'>");
            out.print("<b>Nombres</b>");
            out.print("<input type='hidden' name='id_user' id='id_user' value='" + id_userSesion + "'><br>");
            out.print("<input type='text' name='Txt_user' id='Txt_user' class='form-control' placeholder='Nombre' value='" + Nombres + "'><br>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_user');val1.add(Validate.Presence);</script>");
            out.print("</div>");

            out.print("<div style='width: 35%; margin-left: 5%;'>");
            out.print("<b>Apellidos</b>");
            out.print("<input type='text' name='Txt_lastname' id='Txt_lastname' class='form-control' placeholder='Apellido' value='" + Apellido + "'>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lastname');val1.add(Validate.Presence);</script>");
            out.print("</div>");

            out.print("</div>");
            out.print("<div style='width: 100%; text-align: center;'>");
            out.print("<button class='btn btn-primary' style='width: 20%;'>Actualizar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

        } catch (Exception ex) {
            Logger.getLogger(Tag_perfil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
