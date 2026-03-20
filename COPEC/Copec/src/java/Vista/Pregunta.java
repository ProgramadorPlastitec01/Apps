package Vista;

import Controlador.PruebaJpaController;
import Controlador.TipoProcesoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Pregunta extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        TipoProcesoJpaController jpa_tipoP = new TipoProcesoJpaController();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        String filtro = pageContext.getRequest().getAttribute("filtro").toString();
        int id_prueba = Integer.parseInt(pageContext.getRequest().getAttribute("id_prueba").toString());
        List lst_preguntas = (List) pageContext.getRequest().getAttribute("lst_preguntas");
        List lst_prueba = jpa_prueba.ConsultapruebaId(id_prueba);
        try {
            out.print("<div id='content_sin'>");
            out.print("<a href='Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 1 + "&txt_bus='><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
            Object[] obj_prueba = (Object[]) lst_prueba.get(0);
            if (!lst_preguntas.isEmpty()) {
                if (obj_prueba[5] != null) {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th>Pregunta</th>");
                    out.print("<th>Respuesta</th>");
                    out.print("<th>Opciones</th>");
                    out.print("<th>Proceso</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_preguntas.size(); i++) {
                        Object[] obj_preguntas = (Object[]) lst_preguntas.get(i);
                        String[] opciones = obj_preguntas[5].toString().split("//");
                        if (obj_prueba[5].toString().contains("[" + obj_preguntas[0] + "]")) {
                            out.print("<tr>");
                            out.print("<td>" + obj_preguntas[3] + "</td>");
                            out.print("<td>" + obj_preguntas[4] + "</td>");
                            out.print("<td valign='top'>");
                            out.print("<ul style='margin:0px;'>");
                            for (int j = 0; j < opciones.length; j++) {
                                out.print("<li>" + opciones[j] + "</li>");
                            }
                            out.print("<ul>");
                            out.print("</td>");
                            out.print("<td align='center'>" + obj_preguntas[2] + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }else{
                    out.print("<h2>No se han agregado preguntas</h2>");
                }
            } else {
                out.print("<h3>No se encontraron resultados</h3>");
            }
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
