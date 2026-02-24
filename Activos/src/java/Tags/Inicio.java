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

            out.print("<div style='display:flex'>");
            out.print("<div style='width:60%;'>");  
            out.print("<div style='overflow:auto;max-height:500px;'>");

            out.print("<div style='display:flex;align-items:center;justify-content: space-between;'>");
            out.print("<div><h2><b>Stock VS Minimo</b></h2></div>");
            out.print("<div>");
            out.print("<input type='text' id='buscador' "
                    + "placeholder='Buscar producto...' "
                    + "style='    width: 210px;\n"
                    + "    padding: 6px;\n"
                    + "    float: right;' "
                    + "onkeyup='buscarProducto()'>");
            out.print("</div>");
            out.print("</div>");

            out.print("<table class='table2' style='width:100%;'>");

            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ESTADO</th>");
            out.print("<th>CODIGO</th>");
            out.print("<th>REFERENCIA</th>");
            out.print("<th>MINIMO</th>");
            out.print("<th>EXISTENCIA</th>");
            out.print("</tr>");
            out.print("</thead>");

            out.print("<tbody id='tablaStock'>");
            out.print("</tbody>");

            out.print("</table>");
            out.print("</div>");
            out.print("<div style='margin-top:10px;text-align:center;'>");
            out.print("<input type='submit' style='width:11%' value='Anterior' onclick='cambiarPagina(-1)'>");
            out.print("<span id='paginaActual' style='margin:0 15px;font-weight:bold;'>Pag. 1</span>");
            out.print("<input type='submit' style='width:11%' value='Siguiente' onclick='cambiarPagina(1)'>");
            out.print("</div>");
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="SCRIPTS">
            out.print("<script>");

            out.print("let pagina = 1;");
            out.print("let textoBusqueda = '';");

            out.print("function cargarPagina(p){");
            out.print("fetch('stockMinimo?pagina=' + p + '&buscar=' + encodeURIComponent(textoBusqueda))");
            out.print(".then(r => r.text())");
            out.print(".then(data => {");
            out.print("document.getElementById('tablaStock').innerHTML = data;");
            out.print("document.getElementById('paginaActual').innerText = p;");
            out.print("pagina = p;");
            out.print("});");
            out.print("}");

            out.print("function cambiarPagina(v){");
            out.print("if(pagina + v < 1) return;");
            out.print("cargarPagina(pagina + v);");
            out.print("}");

            out.print("function buscarProducto(){");
            out.print("textoBusqueda = document.getElementById('buscador').value;");
            out.print("cargarPagina(1);");
            out.print("}");

            out.print("cargarPagina(1);");

            out.print("</script>");
            //</editor-fold>

            out.print("<div style='width:40%;align='center'>");
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
            out.print("</div>");
            //</editor-fold>
        } catch (Exception e) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
