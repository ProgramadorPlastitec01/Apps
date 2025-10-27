package Tags;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladoras.CasoJpaController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tag_calificacion_caso extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        CasoJpaController jpa_caso = new CasoJpaController();
        try {
            int id_caso = 0;
            try {
                id_caso = Integer.parseInt(pageContext.getRequest().getAttribute("id_caso").toString());
            } catch (Exception e) {
            }
            List lst_calificacion = jpa_caso.consultaCasoSolucionCorreo(id_caso);
            if (lst_calificacion != null) {
                Object[] obj_califacion = (Object[]) lst_calificacion.get(0);
                out.print("<div class=\"modal is-visible\" id=\"modal1\">");
                out.print("<div class=\"modal-dialog\">");
                out.print("<div class=\"login-box\">");
                if (Integer.parseInt(obj_califacion[11].toString()) == 0) {
                    out.print("<h2>¡Cuéntanos tu experiencia!</h2>");
                    out.print("<form action='Calificar_caso?opc=2' method='post' style='display:flex;' >");
                    out.print("<input type='hidden' value='" + obj_califacion[0] + "' name='id_caso'>");
                    //<editor-fold defaultstate="collapsed" desc="PARTE IZQUIERDA">
                    out.print("<div style='width:50%'>");
                    out.print("<div style='width:200px; margin-left:16%; margin-top:-3%;'>");
                    out.print("<p style='font-size:13px; font-family:sans-serif'><i><b>" + obj_califacion[1] + "</b></i></p>");
                    out.print("</div>");
                    out.print("<div style='border:1px solid #582b77; width:182px; margin-left:10%; margin-top:-3%;'>");
                    out.print("<img src=\"Interfaz/Fotos/" + obj_califacion[10] + ".jpg\" width=\"182px\" height=\"184px\" >");
                    out.print("</div>");

                    out.print("<div class='stars'>\n");
                    out.print("<input class='star star-5' id='star-5' value='5' type='radio' name='star' required/>\n");
                    out.print("<label class='star star-5' for='star-5' title='Excelente'></label>\n");
                    out.print("<input class='star star-4' id='star-4' value='4' type='radio' name='star' required/>\n");
                    out.print("<label class='star star-4' for='star-4' title='Bien'></label>\n");
                    out.print("<input class='star star-3' id='star-3' value='3' type='radio' name='star' required/>\n");
                    out.print("<label class='star star-3' for='star-3' title='Regular'></label>\n");
                    out.print("<input class='star star-2' id='star-2' value='2' type='radio' name='star' required/>\n");
                    out.print("<label class='star star-2' for='star-2' title='Malo'></label>\n");
                    out.print("<input class='star star-1' id='star-1' value='1' type='radio' name='star' required/>\n");
                    out.print("<label class='star star-1' for='star-1' title='Pésimo'></label>\n");
                    out.print("</div>\n");

                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="PARTE DERECHA">
                    out.print("<div style='width:50%'>");
                    out.print("<div style='display:flex; margin-top:-13%'>");
                    out.print("<div class='user-box'>");
                    out.print("<input type='number' name='Txt_documento' min='0'  onkeyup=\"this.value=Numeros(this.value)\" required>");
                    out.print("<label>Documento</label><br>");
                    out.print("</div>");
                    out.print("<div class='user-box'>");
                    out.print("<input type='number' name='Txt_codigo' min='0' onkeyup=\"this.value=Numeros(this.value)\" required>");
                    out.print("<label>Código</label><br>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='font-size:19px; margin-top:13%;margin-left:37%'><p><b>Parada</b></p></div>");
                    out.print("<div style='display:flex; margin-top:-20%'>");
                    out.print("<div class='user-box'>");
                    out.print("<input type='number' name='Txt_equipo' min='0'  onkeyup=\"this.value=Numeros(this.value)\" required>");
                    out.print("<label>Equipo(Min)</label><br>");
                    out.print("</div>");
                    out.print("<div class='user-box'>");
                    out.print("<input type='number' name='Txt_maquina' min='0' onkeyup=\"this.value=Numeros(this.value)\" required>");
                    out.print("<label>Producción (Min)</label><br>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='user-textarea'>");
                    out.print("<textarea name='Txt_opinion'  style='max-width: 280px;min-width: 280px;max-height: 57px;' placeholder='Qué te pareció el soporte ¡Opina!' required></textarea>");
                    out.print("</div>");
                    out.print("<a onclick=\"javascript:document.getElementById('Btn_accionO').click();\">");
                    out.print("<span></span>");
                    out.print("<span></span>");
                    out.print("<span></span>");
                    out.print("<span></span>");
                    out.print("Enviar");
                    out.print("</a>");
                    out.print("<div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionO' />");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else {
                    out.print("<h2> REDEAC </h2><center><img src='Interfaz/Contenido/Images/Logo.png' width='158' height='158'></center>");
                    out.print("<h2 style='margin-top: 5%'>¡Muchas gracias por calificarnos!</h2>");
                }
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_calificacion_caso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
