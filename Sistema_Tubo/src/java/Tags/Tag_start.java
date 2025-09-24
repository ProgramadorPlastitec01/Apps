package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.OrdenProduccionJpaController;
import java.util.List;

import Controladores.RolJpaController;
import java.time.LocalDate;

public class Tag_start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        OrdenProduccionJpaController JpaOrder = new OrdenProduccionJpaController();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_order = null;
        List lst_roll = null;
        LocalDate today = LocalDate.now();
        String date = today.toString();
        int UserRol = 0;
        String txtPermisos = "";
        try {
            UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
            lst_roll = RoleJpa.Consult_role_id(UserRol);
            Object[] obj_permi = (Object[]) lst_roll.get(0);
            txtPermisos = obj_permi[2].toString();
        } catch (Exception e) {
            UserRol = 0;
            txtPermisos = "";
        }

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header' style='justify-content: center;'>");
            out.print("<h1>Bienvenido a Sistema de Tubo</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-body'>");
            lst_order = JpaOrder.OrderRecordOpen();
            if (lst_order != null) {
                out.print("<h5>Registros abiertos</h5>");
                out.print("<div style='text-align: center;'>");
                out.print("<div class='selectgroup w-50'>");
                out.print("<label class='selectgroup-item' onclick='ChangeDiv(1)'>");
                out.print("<input type='radio' name='transportation' value='1' class='selectgroup-input' checked=''>");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Calendario</span>");
                out.print("</label>");
                out.print("<label class='selectgroup-item' onclick='ChangeDiv(2)'>");
                out.print("<input type='radio' name='transportation' value='2' class='selectgroup-input'>");
                out.print("<span class='selectgroup-button selectgroup-button-icon'>Acceso Directo</span>");
                out.print("</label>");
                out.print("</div>");
                out.print("</div>");
                out.print("<hr class='hr_sheet'>");
                out.print("<div id='div_start_access' style='display:none;'>");
                //<editor-fold defaultstate="collapsed" desc="QUICK ACCESS">
                for (int i = 0; i < lst_order.size(); i++) {
                    Object[] obj_order = (Object[]) lst_order.get(i);
                    out.print("<div class='div_acces_priv'>");

                    out.print("<div class='div_acces'>");
                    out.print("<div class='dsn_acces'><b>Orden: </b>" + obj_order[1] + "</div>");
                    out.print("<div class='dsn_acces'><b>Codigo: </b>" + obj_order[5] + "</div>");
                    out.print("<div class='dsn_acces'><b>Producto: </b>" + obj_order[6] + "</div>");
                    out.print("</div>");

                    out.print("<div  class='div_acces'>");
                    out.print("<div class='dsn_acces'><b>Ficha Tecnica: </b>" + obj_order[3] + " V(" + obj_order[4] + ")</div>");
                    out.print("<div class='dsn_acces'><b>Línea: </b>" + obj_order[7] + "</div>");
                    out.print("<div class='dsn_acces'><b>Fecha: </b>" + obj_order[10] + "</div>");
                    out.print("</div>");

                    out.print("<div  class='div_acces'>");
                    out.print("<div class='dsn_acces'><b>Lote Producto: </b>" + obj_order[11] + "</div>");
                    out.print("<div class='dsn_acces'><b>Lote C: </b>" + obj_order[12] + "</div>");
                    out.print("<div class='dsn_acces'><b>Rollos: </b>" + obj_order[15] + " - " + obj_order[16] + "</div>");
                    out.print("</div>");

                    out.print("<div class='div_acces'>");
                    out.print("<div class='dsn_acces'><b>Estado Orden: </b>" + ((Integer.parseInt(obj_order[9].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "<b style='color:black;'>Cerrado</b>") + "</div>");
                    out.print("<div class='dsn_acces'><b>Estado Producción: </b>" + ((Integer.parseInt(obj_order[13].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "<b style='color:black;'>Cerrado</b>") + "</div>");
                    out.print("<div class='dsn_acces'><b>Estado Calidad: </b>" + ((Integer.parseInt(obj_order[14].toString()) == 1) ? "<b style='color:green;'>Abierto</b>" : "<b style='color:black;'>Cerrado</b>") + "</div>");
                    out.print("</div>");

                    out.print("<div class='div_acces_bottom'>");
                    if (txtPermisos.contains("[59]")) {
                        out.print("<a style='line-height: 18px;' onclick=\"javascript:location.href='Record?opc=1&id_order=" + obj_order[0] + "&temp_4=" + obj_order[8] + "'\" class='btn btn-white'><i class='fas fa-eye'></i> | Ingreso Registro</a>");
                    } else {
                        out.print("<a style='line-height: 18px;' onclick='#' class='btn btn-white'><i class='fas fa-ban'></i> | No tiene permisos</a>");
                    }
                    out.print("</div>");

                    out.print("</div>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("<div id='div_start_calendar' style='display:block;'>");
                //<editor-fold defaultstate="collapsed" desc="CALENDAR">
                out.print("<script>");
                out.print("document.addEventListener('DOMContentLoaded', function() {");
                out.print("var calendarEl = document.getElementById('calendar');");
                out.print("var calendar = new FullCalendar.Calendar(calendarEl, {");
                out.print("headerToolbar: {");
                out.print("left: 'prev,next,today',");
                out.print("center: 'title',");
                out.print("right: 'multiMonthYear,dayGridMonth,timeGridWeek,timeGridDay'");
                out.print("},");
                out.print("locales: 'es',");
                out.print("initialView: 'dayGridMonth',");
                out.print("initialDate: '" + date + "',");
                out.print("editable: true,");
                out.print("selectable: false,");
                out.print("dayMaxEvents: true, // allow \"more\" link when too many events");
                out.print("// multiMonthMaxColumns: 1, // guarantee single column");
                out.print("// showNonCurrentDates: true,");
                out.print("// fixedWeekCount: false,\n");
                out.print("// businessHours: true,\n");
                out.print("// weekends: false,\n");
                out.print("events: [\n");
                for (int i = 0; i < lst_order.size(); i++) {
                    Object[] obj_calendar = (Object[]) lst_order.get(i);
                    out.print("");
                    out.print(" {\n");
                    out.print("title: '" + obj_calendar[11] + " - " + obj_calendar[17] + "',\n");
                    out.print("start: '" + obj_calendar[18] + "',\n");
                    out.print("url: 'Record?opc=1&id_order=" + obj_calendar[0] + "&temp_4=" + obj_calendar[8] + "'\n");
                    if (i == lst_order.size()) {
                        out.print("}");
                    } else {
                        out.print("},");
                    }
                    out.print("");
                }
                out.print("]\n");
                out.print("});\n");
                out.print("\n");
                out.print("calendar.render();\n");
                out.print("});\n");
                out.print("\n");
                out.print("</script>");
                out.print("<div id='calendar'></div>");
                //</editor-fold>
                out.print("</div>");
            } else {
                out.print("<div style='text-align:center;'><i style='font-size:80px;' class='fas fa-laugh-beam'></i><br/><h1>No se encuentra registros abiertos</h1></div>");
            }

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
