package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import Controladores.OrdenProduccionJpaController;
import Controladores.RolJpaController;

import Factory.Connection_Inv;

public class Tag_production_order extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        OrdenProduccionJpaController OrderJpa = new OrdenProduccionJpaController();
        Connection_Inv FactJpa = new Connection_Inv();
        RolJpaController RoleJpa = new RolJpaController();

        List lst_order = null;
        List lst_client = null;
        List lst_roll = null;

        int id_orden = 0, UserRol = 0;
        int temp = 0;
        String txtPermisos = "";
        try {
            id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_order").toString());
        } catch (Exception e) {
            id_orden = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
            lst_roll = RoleJpa.Consult_role_id(UserRol);
            Object[] obj_permi = (Object[]) lst_roll.get(0);
            txtPermisos = obj_permi[2].toString();
        } catch (Exception e) {
            UserRol = 0;
            txtPermisos = "";
        }
        try {
            if (id_orden > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDIT ORDER">
                lst_order = OrderJpa.Consult_OrderId(id_orden);
                Object[] Obj_editOrder = (Object[]) lst_order.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Orden de producción</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_order' style='display: block; margin-top:12px;'>");
                out.print("<form action='Production_order?opc=2&id_order=" + Obj_editOrder[0] + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class=''>");
                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='Text_orden_a' id='Txt_orden_edit' value='" + Obj_editOrder[3] + "' placeholder='Numero Orden' required='' style='margin-bottom: 12px;' data-toggle='tooltip' data-placement='top' title='# Orden'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-6' style='margin-bottom: 12px;' data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'>");
                out.print("<select class='form-control select2' name='Cbx_Data'>");
                out.print("<option value='" + Obj_editOrder[1] + "'>" + Obj_editOrder[2] + "</option>");
                lst_order = OrderJpa.Consult_dataSheet();
                if (lst_order != null) {
                    for (int i = 0; i < lst_order.size(); i++) {
                        Object[] obj_Data = (Object[]) lst_order.get(i);
                        out.print("<option value='" + obj_Data[0] + "'>" + obj_Data[3].toString() + "</option>");
                    }
                } else {
                    out.print("<option>Error al consultar Fichas Tecnicas</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-lg-12' style='display: flex;'>");

                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title='Cliente'>");
                out.print("<select class='form-control select2' name='Cbx_client'>");
                out.print("<option value='" + Obj_editOrder[4] + "'>" + Obj_editOrder[4] + "</option>");
                lst_client = FactJpa.Clientes();
                if (lst_client != null) {
                    for (int i = 0; i < lst_client.size(); i++) {
                        String[] clientes = lst_client.get(i).toString().split("////");
                        out.print("<option value='" + clientes[1] + "'>" + clientes[1].toString() + "</option>");
                    }
                } else {
                    out.print("<option>Error al consultar clientes</option>");
                }

                out.print("</select>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<textarea class='form-control' placeholder='Observaciones' name='Txt_Obs' required>" + Obj_editOrder[5] + "</textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;margin-top:12px;'>");
                out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER ORDER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Orden de producción</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div style='text-align: center;'>");
            out.print("<div class='selectgroup w-50'>");
            out.print("<label class='selectgroup-item' onclick='changeForm(1)'>");
            out.print("<input type='radio' name='transportation' value='1' class='selectgroup-input' checked=''>");
            out.print("<span class='selectgroup-button selectgroup-button-icon'>Cliente</span>");
            out.print("</label>");
            out.print("<label class='selectgroup-item' onclick='changeForm(2)'>");
            out.print("<input type='radio' name='transportation' value='2' class='selectgroup-input'>");
            out.print("<span class='selectgroup-button selectgroup-button-icon'>Interna</span>");
            out.print("</label>");
            out.print("</div>");
            out.print("</div>");
            out.print("<form action='Production_order?opc=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div id='cont_form_int' style='display: none; margin-top: 12px'>");
            out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='formul' id='formul' onkeyup='makeOrder()' placeholder='Codigo' style='margin-bottom: 12px; width: 100%;' data-toggle='tooltip' data-placement='top' title='Codigo' value='0' required>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control datepicker' name='datepick' onchange='makeOrder()' id='datepick' placeholder='Fecha' style='margin-bottom: 12px; width: 100%;' data-toggle='tooltip' data-placement='top' title='Fecha'>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
            out.print("<div class='col-lg-12'>");
            out.print("<div class=''>");
            out.print("<input type='text' class='form-control' name='Txt_orden1' id='Txt_orden1' placeholder='Numero Orden' required='' style='margin-bottom: 12px;' data-toggle='tooltip' data-placement='top' title='# Orden'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='' data-toggle='tooltip' data-placement='top' title='# Orden'>");
            out.print("<input type='hidden' class='form-control' name='Txt_orden' id='Txt_orden' placeholder='Numero Orden' required='' style='margin-bottom: 12px; width: 100%;' data-toggle='tooltip' data-placement='top' title='# Orden'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("<input type='hidden' name='Text_orden_a' id='test'>");
            out.print("<input type='hidden' name='temps' id='teemp' value='1'>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col-lg-12' style='display: flex; justify-content: space-between;'>");
            out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title='Ficha Tecnica'>");
            out.print("<select class='select2' name='Cbx_Data'>");
            out.print("<option value='0'>Seleccionar Ficha Tecnica...</option>");
            lst_order = OrderJpa.Consult_dataSheet();
            if (lst_order != null) {
                for (int i = 0; i < lst_order.size(); i++) {
                    Object[] obj_Data = (Object[]) lst_order.get(i);
                    out.print("<option value='" + obj_Data[0] + "'>" + obj_Data[3].toString() + "</option>");
                }
            } else {
                out.print("<option>Error al consultar Fichas Tecnicas</option>");
            }
            out.print("</select>");
            out.print("</div>");

            out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title='Cliente'>");
            out.print("<select class='select2' name='Cbx_client'>");
            out.print("<option value='0'>Seleccionar Cliente...</option>");
            lst_client = FactJpa.Clientes();
            if (lst_client != null || lst_client.size() != 0) {
                for (int i = 0; i < lst_client.size(); i++) {
                    String[] clientes = lst_client.get(i).toString().split("////");
                    out.print("<option value='" + clientes[1] + "'>" + clientes[1].toString() + "</option>");
                }
            } else {
                out.print("<option>Error al consultar clientes</option>");
            }

            out.print("</select>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col-lg-12 mt-3' style='display: flex;'>");
            out.print("<textarea class='form-control' placeholder='Observaciones' name='Txt_Obs' required data-toggle='tooltip' data-placement='top' title='Observaciones'></textarea>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-12 text-center' style='margin-top: 12px;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo de Orden de Producción</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Orden de Producción</h4>");
            out.print("<div class=''>");
            if (temp > 0) {
                out.print("<a href='Production_order?opc=1&temp=0' class='btn btn-danger btn-sm' style='border-radius: 4px; margin-left:12px; color: white;' data-toggle='tooltip' data-placement='top' title='Quitar Filtro'><i class=\"fas fa-times\"></i></a>");
            }

            out.print("<a href='Production_order?opc=1&temp=1' class='btn btn-success btn-sm' style='border-radius: 4px; margin-left:12px; color: white;' data-toggle='tooltip' data-placement='top' title='Ordenes Abiertas'><i class=\"fas fa-lock-open\"></i></a>");
            out.print("<a href='Production_order?opc=1&temp=2' class='btn btn-secondary btn-sm' style='border-radius: 4px; margin-left:12px; color: white;' data-toggle='tooltip' data-placement='top' title='Ordenes Cerrada'><i class=\"fas fa-lock\"></i></a>");
            if (txtPermisos.contains("[28]")) {
                out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green btn-sm' style='border-radius: 4px; margin-left:12px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos' ><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");

            out.print("<tr>");
            out.print("<th style='text-align: center;'>Registro</th>");
            out.print("<th>Ficha Tecnica</th>");
            out.print("<th>No. Orden</th>");
            out.print("<th>Cliente</th>");
            out.print("<th>Observaciones</th>");
            out.print("<th>Lotes</th>");
            out.print("<th style='text-align: center; min-width: 120px;'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");

            if (temp == 1) {
                lst_order = OrderJpa.Order_Open();
            } else if (temp == 2) {
                lst_order = OrderJpa.OrderClose();
            } else {
                lst_order = OrderJpa.Consult_Order();
            }

            if (lst_order != null) {
                for (int i = 0; i < lst_order.size(); i++) {
                    Object[] Obj_order = (Object[]) lst_order.get(i);
                    out.print("<tr>");
                    out.print("<td align='center'> <a href='Record?opc=1&id_order=" + Obj_order[0] + "' class='btn btn-white'><i class='fas fa-eye'></i></a></td>");
                    out.print("<td>" + Obj_order[1].toString() + "</td>");
                    out.print("<td><b>" + Obj_order[2].toString() + "</b></td>");
                    out.print("<td>" + Obj_order[3].toString() + "</td>");
                    out.print("<td>" + Obj_order[4].toString() + "</td>");

                    if (Obj_order[8] == null) {
                        out.print("<td class='text-center'><i class='fas fa-comment-alt' style='font-size: 20px;' data-toggle='tooltip' data-placement='top' title='Sin lotes Registrados'></i></td>");
                    } else {
                        out.print("<td>");
                        out.print("<div style='max-height: 60px; max-width: 100px; overflow-y: auto; white-space: normal;'>");
                        out.print(Obj_order[8]);
                        out.print("</div>");
                        out.print("</td>");
//                        out.print("<td class='text-center'><i class='fas fa-comment-alt text-info' style='font-size: 20px;' data-toggle='tooltip' data-placement='top' title='"+ Obj_order[8].toString().replace(",", "\n\n") +"'></i></td>");
                    }

                    int est = Integer.parseInt(Obj_order[5].toString());
                    out.print("<td align='center'>");
                    if (txtPermisos.contains("[29]")) {
                        out.print("<a href='Production_order?opc=1&id_order=" + Obj_order[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' disabled class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[30]")) {
                        out.print("<a href='Production_order?opc=3&id_order=" + Obj_order[0] + "&est=" + est + "' class='btn btn-" + ((est != 0) ? "success" : "secondary") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((est != 0) ? "fas fa-lock-open" : "fas fa-lock") + "'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' class='btn btn-" + ((est != 0) ? "success" : "secondary") + "' style='opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est != 0) ? "fas fa-lock-open" : "fas fa-lock") + "'></i></a> &nbsp;&nbsp;");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7' align='center'>");
                out.print("<i class=\"fas fa-exclamation-triangle\" style='font-size: 50px;'></i><h2>No se ha registrado ninguna orden de produccion!</h2>");
                out.print("</td>");
                out.print("</tr>");
            }

            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

//</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_production_order.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
