package Tags;

import Controladores.ClienteJpaController;
import Controladores.ControlDmsCJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_visor extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String Usuario = sesion.getAttribute("Nombre").toString();
        String Rol = sesion.getAttribute("Rol").toString();
        int id_rol = Integer.parseInt(sesion.getAttribute("id_rol").toString());
        ControlDmsCJpaController jpa_turno = new ControlDmsCJpaController();
        ClienteJpaController jpa_usuario = new ClienteJpaController();
        int id_despeje = Integer.parseInt(pageContext.getRequest().getAttribute("id_despeje").toString());
        List lst_despeje = jpa_turno.consultaDespejeTurno(id_despeje);
        Object[] obj_despeje = (Object[]) lst_despeje.get(0);
        List lst_turno = jpa_turno.consultaTurnoId((Integer) obj_despeje[1]);
        Object[] obj_turno = (Object[]) lst_turno.get(0);
        int id_usuarioF = 0;
        List lst_usuarioF = null;
        String usuarioC = "";
        String rolC = "";
        int idRolC = 0;
        try {
            id_usuarioF = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuarioF").toString());
        } catch (Exception e) {
            id_usuarioF = 0;
        }
        try {
            String plantilla = obj_despeje[2].toString();
            if ((Integer) obj_turno[19] == 0 || id_rol == 2) {
//            if (!obj_turno[18].toString().equals("cerrado") && (Integer) obj_turno[19] == 0) {
                //<editor-fold defaultstate="collapsed" desc="CAMBIO DE SESION">
                if (id_usuarioF != 0) {
                    lst_usuarioF = jpa_usuario.consultausuarioId(id_usuarioF);
                    Object[] obj_usa = (Object[]) lst_usuarioF.get(0);
                    usuarioC = obj_usa[1] + " " + obj_usa[2];
                    rolC = obj_usa[7].toString();
                    idRolC = Integer.parseInt(obj_usa[6].toString());
                }
                out.print("<button class='accordion'>Cambiar Usuario</button>");
                out.print("<div class='panel'>");
                out.print("<div style='width:30%;float:left;'>");
                out.print("<form action='Turno?opc=18' method='post'>");
                out.print("<br /><input type='text' name='Txt_user' id='Txt_user' placeholder='Usuario' onchange='javascript:this.value = this.value.toUpperCase();'/><br />");
                out.print("<input type='password' name='Txt_password' id='Txt_password' placeholder='Contraseña'/><br />");
                out.print("<input type='submit' value='Iniciar'/><br/><br/>");
                out.print("<input type='hidden' name='idD' value='" + id_despeje + "'/>");
                out.print("<input type='hidden' name='idUF' value='" + id_usuarioF + "'/>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='width:69%;float:left;'>");
                out.print("<br /><b>Usuario Actual :</b>" + ((usuarioC.equals("")) ? Usuario : usuarioC) + "<br />");
                out.print("<b>Rol: </b>" + ((rolC.equals("")) ? Rol : rolC) + "<br /><br />");
                out.print("<b class='naranja'>Para cambiar de usuario temporalmente debe diligenciar completamente el registro de despeje, favor introducir a la izquierda los datos de sesión.<br />Luego de Iniciar verifique si es su sesión.</b>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br /><br />");
//</editor-fold> 
                if ((Integer) obj_despeje[4] == 0) {
                    plantilla = plantilla.replaceAll("contenteditable=\"true\"", "contenteditable=\"false\"");
                    if ((Integer) obj_despeje[3] == 1) {
                        plantilla = plantilla.replaceAll("id=\"obsevaciones\" contenteditable=\"false\"", "id=\"obsevaciones\" contenteditable=\"true\"");
                    } else {
                        plantilla = plantilla.replaceAll("id=\"obsevaciones\" contenteditable=\"true\"", "id=\"obsevaciones\" contenteditable=\"false\"");
                    }
                } else {
                    plantilla = plantilla.replace("XXXGRAFADORAXXX", obj_turno[22].toString().split(" ")[1]);
                    plantilla = plantilla.replace("XXXFECHAXXX", obj_turno[2].toString());
                    plantilla = plantilla.replace("XXXTURNOXXX", obj_turno[3].toString());
                    plantilla = plantilla.replace("XXXFICHATXXX", obj_turno[41].toString().split("-")[2]);
                    plantilla = plantilla.replace("XXXVERFICHXXX", obj_turno[42].toString());
                }
                if (idRolC != 0) {
                    id_rol = idRolC;
                }
                if (id_rol == 2 || id_rol == 3 || id_rol == 4) {
                    plantilla = plantilla.replaceAll("CODCOLOR", "onclick=\"color(this,1);\"");
                    plantilla = plantilla.replace("color(this,2)", "color(this,1)");
                } else if (id_rol == 5 || id_rol == 6 || id_rol == 1) {
                    plantilla = plantilla.replaceAll("CODCOLOR", "onclick=\"color(this,2);\"");
                    plantilla = plantilla.replace("color(this,1)", "color(this,2)");
                }
                if ((Integer) obj_despeje[4] != 0 || id_rol == 1 || id_rol == 2 || id_rol == 3 || id_rol == 5 || id_rol == 6) {
                    out.print("<div style='float:left'>");
                    out.print("<form method='post' action='Turno?opc=17' name='formFirm'>");
//                    out.print("<span class='fas fa-signature fa-size_small' onclick='firmar();'></span>Firmar Registro&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<a href='#' onclick='firmar();' style='color:black;'><span class='fas fa-signature fa-size_small'></span></a>&nbsp;&nbsp;Firmar Registro&nbsp;&nbsp;&nbsp;&nbsp;");
                    out.print("<select name='slc_firma' id='firma-id'>");
                    out.print("<option style='display:none'>Seleccionar Sección</option>");
                    out.print("<option value='1'>1. RETIRO MATERIAL DE LA LINEA</option>");
                    out.print("<option value='3'>3. INSPECCION DE MATERIALES</option>");
                    out.print("</select>");
                    out.print("<input type='hidden' name='textareaD' id='textareaD' value=''/>");
                    out.print("<input type='hidden' name='idD' value='" + id_despeje + "'/>");
                    out.print("<input type='hidden' name='idUF' value='" + id_usuarioF + "'/>");
                    out.print("<input type='hidden' name='txt_usuario' value='" + ((usuarioC.equals("")) ? Usuario : usuarioC) + "'/>");
                    out.print("<input type='hidden' name='txt_rol' value='" + ((idRolC == 0) ? id_rol : idRolC) + "'/>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<div style='float:right'>");
                    if ((Integer) obj_despeje[4] == 1) {
                        out.print("<b>Liberar </b><a href='#' onclick='javascript:document.getElementById(\"estD\").value = \"0\";document.formDesp.submit();' style='color:black;'><span class='fas fa-clipboard-check fa-size_small'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                    if ((Integer) obj_despeje[3] != 0) {
                        out.print("<b>Guardar </b><a href='#' onclick='javascript:document.formDesp.submit();' style='color:black;'><span class='far fa-save fa-size_small'></span></a>");
                    }
                    out.print("</div><br/><br/>");
                } else if (id_rol == 2 || id_rol == 1) {
                    if ((Integer) obj_despeje[3] != 0) {
                        out.print("<div style='float:left'>");
                        out.print("<b>Bloquear observaciones </b><a href='#' onclick='javascript:document.getElementById(\"estO\").value = \"0\";document.formDesp.submit();' style='color:black;'><span class='fas fa-check fa-size_small'></span></a>");
                        out.print("</div>");
                        out.print("<div style='float:right'>");
                        out.print("<b>Guardar </b><a href='#' onclick='javascript:document.formDesp.submit();' style='color:black;'><span class='far fa-save fa-size_small'></span></a>");
                        out.print("</div><br/><br/>");
                    } else {
                        out.print("<div style='float:left'>");
                        out.print("<b>Habilitar observaciones </b><a href='#' onclick='javascript:document.formDesp.submit();' style='color:black;' ><span class='fas fa-check fa-size_small'></span></a>");
                        out.print("</div><br/><br/>");
                    }
                }
            }
            out.print("<form method='post' action='Turno?opc=16' name='formDesp'>");
            out.print("<input type='hidden' name='idD' value='" + id_despeje + "'/>");
            out.print("<input type='hidden' name='idUF' value='" + id_usuarioF + "'/>");
            out.print("<input type='hidden' name='estD' id='estD' value='1'/>");
            out.print("<input type='hidden' name='estO' id='estO' value='1'/>");
            out.print("<textarea name='textarea' id='textarea' class='jqte-test'>");
            out.print("" + plantilla + "");
            out.print("</textarea>");
            out.print("</form>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
