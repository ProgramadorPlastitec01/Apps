package Tags;

import Controladores.RequisicionJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Reporte_requisicion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
            List lst_reporte = null;
            //<editor-fold defaultstate="collapsed" desc="REPORTE">
            lst_reporte = jpa_requisicion.ReporteRequisicion();
            out.print("<h3>Reporte de requisiciones</h3>");
            out.print("<div style='float: right;'>"
                    + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                    + "</div></br>");
            out.print("<br><div style='float:right;'><a onclick=\"tableToExcel('testTable', 'Reporte de requisiciones')\" value=\"Exportar a Excel\">"
                    + "<i class='far fa-file-excel fa-lg' style='color:#292929'></i></a><b>Exportar a excel</b></div><br>");
            out.print("<div align='' id='NavPosicion0'></div>");
            out.print("<div id='testTable'>");
            out.print("<div style='height:100%' style='overflow-y:scroll' >");
            out.print("<table id='resultados' style='width:100%' class='table-cebra'>");
            out.print("<tr>");
            out.print("<th class='sticky'>Área</th>");
            out.print("<th class='sticky'>#</th>");
            out.print("<th class='sticky'>Material</th>");
            out.print("<th class='sticky'>Estado</th>");
            out.print("<th class='sticky'>Prioridad</th>");
            out.print("<th class='sticky'>Cant. Solicitada</th>");
            out.print("<th class='sticky'>Fecha Solicitud</th>");
            out.print("<th class='sticky'>Cant. Recibida</th>");
            out.print("<th class='sticky'>Fecha Estimada</th>");
            out.print("<th class='sticky'>Fecha Proveedor</th>");
            out.print("<th class='sticky'>Imp</th>");
            out.print("<th class='sticky'>OC</th>");
            out.print("<th class='sticky'>Dias vencidos</th>");
            out.print("<th class='sticky'>Observación</th>");
            out.print("</tr>");
            if (lst_reporte != null) {
                for (int i = 0; i < lst_reporte.size(); i++) {
                    Object[] obj_reporte = (Object[]) lst_reporte.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_reporte[0] + "</td>");
                    out.print("<td>" + obj_reporte[1] + "</td>");
                    out.print("<td>" + (obj_reporte[2] == null ? "N/A" : obj_reporte[2]) + "</td>");
                    out.print("<td>" + (obj_reporte[3] == null ? "N/A" : obj_reporte[3]) + "</td>");
                    out.print("<td>" + (obj_reporte[4] == null ? "N/A" : obj_reporte[4]) + "</td>");
                    out.print("<td>" + (obj_reporte[5] == null ? "N/A" : obj_reporte[5]) + "</td>");
                    out.print("<td>" + (obj_reporte[6] == null ? "N/A" : obj_reporte[6]) + "</td>");
                    out.print("<td>" + (obj_reporte[7] == null ? "N/A" : obj_reporte[7]) + "</td>");
                    out.print("<td>" + (obj_reporte[8] == null ? "N/A" : obj_reporte[8]) + "</td>");
                    out.print("<td>" + (obj_reporte[9] == null ? "N/A" : obj_reporte[9]) + "</td>");
                     out.print("<td>" + (obj_reporte[13] == null ? "N/A" : (Integer.parseInt(obj_reporte[13].toString()) == 1) ? "Nacional" : "Exterior") + "</td>");
                    out.print("<td>" + (obj_reporte[10] == null ? "N/A" : obj_reporte[10]) + "</td>");
                    out.print("<td>" + (obj_reporte[11] == null ? "N/A" : obj_reporte[11]) + "</td>");
                    out.print("<td>" + (obj_reporte[12] == null ? "" : obj_reporte[12]) + "</td>");
                    out.print("</tr>");
                }
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 100);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
        } catch (Exception e) {
            Logger.getLogger(Reporte_requisicion.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
