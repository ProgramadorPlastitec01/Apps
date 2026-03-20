package Tags;

import Controladores.CalibradorJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Metodos.Consultas_metrologia;

public class Tag_calibrador extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        CalibradorJpaController jpa_calibrador = new CalibradorJpaController();
        Consultas_metrologia JpaConsultaMetro = new Consultas_metrologia();
        List lst_calibradores = null;
        List lst_consultaMetro = null;
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        try {
            out.print("<div id='content_sin'>");
            if (!filtro.equals("")) {
                out.print("<a href='Calibrador?opc=1&idC=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' /></a>");
//                lst_calibradores = jpa_calibrador.consultaCalibradoresFiltro(filtro);
                lst_calibradores = JpaConsultaMetro.calibradores_filtro(filtro);
            } else {
//                lst_calibradores = jpa_calibrador.consultaCalibradores();
                lst_calibradores = JpaConsultaMetro.calibradores();
            }
            out.print("<div style='float:right;'>");
//            out.print("<form method='post' action='Calibrador?opc=1&idC=" + 0 + "'>");
            out.print("<input name='txt_bus' id='txt_bus' type='text' placeholder='Buscar' onkeyup='Filtrar()'><br/>");
//            out.print("</form>");
            out.print("</div>");
            if (lst_calibradores.isEmpty()) {
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                out.print("<h3>Calibrador</h3>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%'>");
                out.print("<tr>");
                out.print("<th rowspan='2'>Serial</th>");
                out.print("<th rowspan='2'>codigo</th>");
                out.print("<th rowspan='2'>Tipo</th>");
                out.print("<th COLSPAN='2'>Verificacion Interna</th>");
                out.print("</tr>");
                out.print("</tr>");
                out.print("<td align='center'><b>Ultima</b></td>");
                out.print("<td align='center'><b>Proxima</b></td>");
                out.print("</tr>");

                for (int p = 0; p < lst_calibradores.size(); p++) {
                    String[] Arg_seriales = lst_calibradores.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                    for (int i = 0; i < Arg_seriales.length; i++) {
                        String[] obj_calibrador = Arg_seriales[i].toString().split("---");
                        out.print("<tr>");
                        if (obj_calibrador[11].toString().equals("0")) {
                            out.print("<td><b style='color:red'>" + obj_calibrador[2] + "</b></td>");
                            out.print("<td align='center'><b style='color:red'>" + obj_calibrador[0] + "</b></td>");
                            out.print("<td><b style='color:red'>" + obj_calibrador[1] + "</b></td>");
                            out.print("<td align='center'>" + obj_calibrador[4] + "-<b style='color:red'>" + obj_calibrador[7] + "</b></td>");
                            out.print("<td align='center'>" + obj_calibrador[5] + "</td>");
                        } else {
                            out.print("<td>" + obj_calibrador[2] + "</td>");
                            out.print("<td align='center'>" + obj_calibrador[0] + "</td>");
                            out.print("<td>" + obj_calibrador[1] + "</td>");
                            out.print("<td align='center'>" + obj_calibrador[4] + "</td>");
                            out.print("<td align='center'>" + obj_calibrador[5] + "</td>");
                        }
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager3('resultados',24);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_calibrador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_calibrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
