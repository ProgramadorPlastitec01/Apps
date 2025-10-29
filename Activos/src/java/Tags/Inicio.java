package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.List;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.RequisicionJpaController;
import Controladores.AreaJpaController;
import Factory.ReferenciasMANT;
import java.util.Arrays;

public class Inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            String rol = (String) sesion.getAttribute("NombreRol");
            String nombre = (String) sesion.getAttribute("Nombres");
            AreaJpaController jpa_area = new AreaJpaController();
            ReferenciasMANT FactoryJpa = new ReferenciasMANT();
            RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
            if (nombre == null) {
                out.print("<a href='Sesion?opc=1'></a>");
            } else {
                out.print("<h3>¡ Bienvenido <b>" + nombre + "</b>! </h3>");
            }
            //<editor-fold defaultstate="collapsed" desc="INICIÓ">
            out.print("<div style='display:flex;justify-content:space-between;'>");
            out.print("<div style='width:55%;overflow:auto;max-height: 500px;'>");
            out.print("<h2><b>Stock VS Minimo</b></h2>");
            out.print("<table class='table2' style='width:100%;'>");
            out.print("<tr>");
            out.print("<th class='sticky'>ESTADO</th>");
            out.print("<th class='sticky'>CODIGO</th>");
            out.print("<th class='sticky'>REFERENCIA</th>");
            out.print("<th class='sticky'>MINIMO</th>");
            out.print("<th class='sticky'>EXISTENCIA</th>");
            out.print("</tr>");
            List lst_factory = FactoryJpa.StockMinimo();
            if (lst_factory != null) {
                for (int i = 0; i < lst_factory.size(); i++) {
                    out.print("<tr>");
                    out.print("" + lst_factory.get(i).toString()
                            .replace("<td>Stock</td>", "<td style='text-align:center;border-left:1px solid #b5b5b5;'><i style='font-size:20px;color:#169a2c;' class=\"fas fa-flag\"></i></td>")
                            .replace("<td>Alerta</td>", "<td style='text-align:center;border-left:1px solid #b5b5b5;'><i style='font-size:20px;color:#f17e18;' class=\"fas fa-flag\"></i></td>"));
                    out.print("</tr>");
                }
            }
            out.print("</table>");
            out.print("</div>");

            out.print("<div style='width:55%;align='center'>");
            out.print("<h2><b>Requisiciones</b></h2>");
            out.print("<table class='table2' style='width:100%;'>");
            out.print("<tr>");
            out.print("<th>ESTADO</th>");
            List lst_area = jpa_area.consultarAreas();
            for (int i = 0; i < lst_area.size(); i++) {
                Object[] obj_area = (Object[]) lst_area.get(i);
                out.print("<th>" + obj_area[2].toString() + "</th>");
//                out.print("<th>" + obj_area[1].toString().replace("MTTO", "") + "</th>");
            }
            out.print("<th>TOTAL</th>");
            out.print("</tr>");
            List lst_requisiciones = jpa_requisicion.consultaContadorEstadoAC();
            for (int j = 0; j < lst_requisiciones.size(); j++) {
                Object[] obj_requisicion = (Object[]) lst_requisiciones.get(j);
                out.print("<tr>");
                out.print("<td style='border-left:1px solid #b5b5b5;'><b>" + obj_requisicion[0] + "</b></td>");
                out.print("<td>" + obj_requisicion[1] + "</td>");
                out.print("<td>" + obj_requisicion[2] + "</td>");
                out.print("<td>" + obj_requisicion[3] + "</td>");
                out.print("<td>" + obj_requisicion[4] + "</td>");
                out.print("<td>" + obj_requisicion[5] + "</td>");
                out.print("<td><b>" + obj_requisicion[6] + "</b></td>");
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("</div>");
            //</editor-fold>
        } catch (Exception e) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
