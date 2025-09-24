package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.FichaTecnicaJpaController;
import Controladores.RolJpaController;
import Factory.Connection_Inv;
import java.util.List;

public class Tag_data_sheet extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        FichaTecnicaJpaController JpaFicha = new FichaTecnicaJpaController();
        Connection_Inv JpaFactory = new Connection_Inv();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_data_sheet = null;
        List lst_products = null;
        List lst_data_sheet_id = null;
        List lst_permission = null;
        JspWriter out = pageContext.getOut();
        int state = 0, I_visual = 0, id_data_sheet = 0, temp_1 = 0, state_data = 0, UserRol = 0;
        String code = "", txtPermisos = "";
        try {
            try {
                code = pageContext.getRequest().getAttribute("code").toString();
            } catch (Exception e) {
                code = "";
            }
            try {
                id_data_sheet = Integer.parseInt(pageContext.getRequest().getAttribute("id_data_sheet").toString());
            } catch (NumberFormatException e) {
                id_data_sheet = 0;
            }
            try {
                temp_1 = Integer.parseInt(pageContext.getRequest().getAttribute("temp_1").toString());
            } catch (NumberFormatException e) {
                temp_1 = 0;
            }
            try {
                state_data = Integer.parseInt(pageContext.getRequest().getAttribute("state_data").toString());
            } catch (NumberFormatException e) {
                state_data = 0;
            }
            try {
                UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
                lst_permission = RoleJpa.Consult_role_id(UserRol);
                Object[] obj_permi = (Object[]) lst_permission.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                UserRol = 0;
                txtPermisos = "";
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Ficha Tecnica</h1>");
            out.print("</div>");
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-12\">");
            out.print("<div class=\"card\">");
            out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
            out.print("<h4>Listado Ficha Tecnica</h4>");
            if (txtPermisos.contains("[1]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class=\"card-body\">");
            out.print("<div style='display:flex;justify-content:space-between;'>");
            out.print("<div style='text-align: center;'>");
            out.print("<div class='selectgroup w-100'>");
            out.print("<label class='selectgroup-item' onclick=\"javascript:location.href='Data_sheet?opc=1&state_data=2'\">");
            out.print("<input type='radio' name='state' value='1' class='selectgroup-input' " + ((state_data == 2) ? "checked=''" : "") + ">");
            out.print("<span class='selectgroup-button selectgroup-button-icon'>Todos</span>");
            out.print("</label>");
            out.print("<label class='selectgroup-item' onclick=\"javascript:location.href='Data_sheet?opc=1&state_data=1'\">");
            out.print("<input type='radio' name='state' value='1' class='selectgroup-input' " + ((state_data == 1) ? "checked=''" : "") + ">");
            out.print("<span class='selectgroup-button selectgroup-button-icon'>Activo</span>");
            out.print("</label>");
            out.print("<label class='selectgroup-item' onclick=\"javascript:location.href='Data_sheet?opc=1&state_data=0'\">");
            out.print("<input type='radio' name='state' value='2' class='selectgroup-input' " + ((state_data == 0) ? "checked=''" : "") + ">");
            out.print("<span class='selectgroup-button selectgroup-button-icon'>Inactivo</span>");
            out.print("</label>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div style='margin-bottom: 2%;'><input class='form-control' type=\"text\" id=\"myInput\" placeholder=\"Buscar\" ></div>");
            out.print("</div>");
            if (id_data_sheet > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE">
                lst_data_sheet_id = JpaFicha.Consult_Data_sheet_id(id_data_sheet);
                if (lst_data_sheet_id != null || lst_data_sheet_id.isEmpty()) {
                    Object[] obj_data_id = (Object[]) lst_data_sheet_id.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_data_sheet'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>" + ((temp_1 == 1) ? "Actualizar " : "Modificar ") + "Ficha Tecnica</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class=''>");

                    out.print("<form class='needs-validation' novalidate='' action='Data_sheet?opc=2' method='post'>");
                    out.print("<input type='hidden' name='id_data_sheet' value='" + id_data_sheet + "'>");
                    out.print("<input type='hidden' name='temp_1' value='" + temp_1 + "'>");

                    out.print("<div class='' style='display: flex;justify-content: space-around;'>");
                    out.print("<div style='width:47%' data-toggle='tooltip' data-placement='right' title='Codigo'><input type='text'  style='background-color:  #ebebeb;' class='form-control input_none' name='Txt_code' id='Txt_code' placeholder='Codigo' value='" + obj_data_id[1] + "'></div>");
                    out.print("<div style='width:47%' data-toggle='tooltip' data-placement='right' title='Producto'><input type='text'  style='background-color:  #ebebeb;' class='form-control input_none' name='Txt_product' id='Txt_product' placeholder='Producto' value='" + obj_data_id[2] + "'></div>");
                    out.print("</div>");

                    out.print("<div class=''  style='display: flex;justify-content: space-around; margin-top:10px;'>");

                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' style='" + ((temp_1 == 1) ? "background-color:  #ebebeb;" : "") + "' class='form-control " + ((temp_1 == 1) ? "input_none" : "") + "' name='Txt_name_sheet' id='Txt_name_sheet' placeholder='Nombre Ficha Tecnica' value='" + obj_data_id[3] + "' data-toggle='tooltip' data-placement='right' title='Nombre Ficha Tecnica' required=''>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-6'>");
                    int version = Integer.parseInt(obj_data_id[4].toString());
                    out.print("<input type='number'  class='form-control' name='version' id='Txt_version' placeholder='Version' value='" + ((temp_1 == 1) ? version + 1 : version) + "' data-toggle='tooltip' data-placement='right' title='Version' required=''>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='divCbData'>");
                    out.print("<div style='width:24%'>");
                    //<editor-fold defaultstate="collapsed" desc="INTERNO SIN PRESURIZAR">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_intSinPre' id='Txt_intSinPre' placeholder='Interno sin presurizar' value='" + obj_data_id[5] + "' data-toggle='tooltip' data-placement='right' title='Interno sin presurizar' required=''>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");

                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:25%' class='form-control' name='Txt_intSinPre_max' id='Txt_diameter_ex_max' placeholder='' required='' value='" + obj_data_id[7] + "'>"
                            + "</div>");

                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:25%' class='form-control' name='Txt_intSinPre_min' id='Txt_diameter_ex_min' placeholder='' required='' value='" + obj_data_id[6] + "'>"
                            + "</div>");

                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div style='width:24%'>");
                    //<editor-fold defaultstate="collapsed" desc="INTERNO PRESURIZADO">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_intPre' id='Txt_intPre' placeholder='Interno presurizado' value='" + obj_data_id[8] + "' data-toggle='tooltip' data-placement='right' title='Interno presurizado' required=''>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");

                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:25%' class='form-control' name='Txt_intPre_max' id='Txt_intPre_max' placeholder='' required='' value='" + obj_data_id[10] + "'>"
                            + "</div>");

                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:25%' class='form-control' name='Txt_intPre_min' id='Txt_intPre_min' placeholder='' required='' value='" + obj_data_id[9] + "'>"
                            + "</div>");

                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div style='width:24%;'>");
                    //<editor-fold defaultstate="collapsed" desc="EXTERNO_SIN_PRESURIZAR">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_extSinPre' id='Txt_extSinPre' placeholder='Externo sin presurizar' required='' value='" + obj_data_id[11] + "' data-toggle='tooltip' data-placement='right' title='Externo sin presurizar' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");
                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_extSinPre_max' id='Txt_extSinPre_max' placeholder='' required='' value='" + obj_data_id[13] + "'>"
                            + "</div>");

                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_extSinPre_min' id='Txt_extSinPre_min' placeholder='' required='' value='" + obj_data_id[12] + "'>"
                            + "</div>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div style='width:24%;'>");
                    //<editor-fold defaultstate="collapsed" desc="EXTERNO_PRESURIZADO">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_extPre' id='Txt_extPre' placeholder='Externo presurizado' required='' value='" + obj_data_id[14] + "' data-toggle='tooltip' data-placement='right' title='Externo presurizado' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");
                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_extPre_max' id='Txt_extPre_max' placeholder='' required='' value='" + obj_data_id[16] + "'>"
                            + "</div>");

                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_extPre_min' id='Txt_extPre_min' placeholder='' required='' value='" + obj_data_id[15] + "'>"
                            + "</div>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='divCbData'>");
                    out.print("<div style='width:24%;'>");
                    //<editor-fold defaultstate="collapsed" desc="ESPESOR DE PARED">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_wall_thickness' id='Txt_wall_thickness' placeholder='Espesor pared' required='' value='" + obj_data_id[17] + "' data-toggle='tooltip' data-placement='right' title='Espesor de Pared' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");
                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_wall_thickness_max' id='Txt_wall_thickness_max' placeholder='' required='' value='" + obj_data_id[19] + "'>"
                            + "</div>");

                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_wall_thickness_min' id='Txt_wall_thickness_min' placeholder='' required='' value='" + obj_data_id[18] + "'>"
                            + "</div>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div style='width:24%'>");
                    //<editor-fold defaultstate="collapsed" desc="DIAMETRO EXTERIOR BOBINA">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_diameter_coil_ex' id='Txt_diameter_coil_ex' placeholder='Diametro Exterior Bobina' required='' value='" + obj_data_id[20] + "' data-toggle='tooltip' data-placement='right' title='Diametro Exterior Bobina'>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");

                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_diameter_coil_ex_max' id='Txt_diameter_coil_max' placeholder='' required='' value='" + obj_data_id[22] + "'>"
                            + "</div>");

                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_diameter_coil_ex_min' id='Txt_diameter_coil_min' placeholder='' required='' value='" + obj_data_id[21] + "'>"
                            + "</div>");

                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div style='width:24%'>");
                    //<editor-fold defaultstate="collapsed" desc="DIAMETRO INTERIOR BOBINA">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_diameter_coil_in' id='Txt_diameter_coil_in' placeholder='Diametro Exterior Bobina' required='' value='" + obj_data_id[23] + "' data-toggle='tooltip' data-placement='right' title='Diametro Exterior Bobina'>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");
                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_diameter_coil_in_max' id='Txt_diameter_coil_max' placeholder='' required='' value='" + obj_data_id[25] + "'>"
                            + "</div>");
                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_diameter_coil_in_min' id='Txt_diameter_coil_min' placeholder='' required='' value='" + obj_data_id[24] + "'>"
                            + "</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div style='width:24%;'>");
                    //<editor-fold defaultstate="collapsed" desc="PESO">
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' class='form-control' name='Txt_roll_weight' id='Txt_roll_weight' placeholder='Peso rollo' required='' value='" + obj_data_id[26] + "' data-toggle='tooltip' data-placement='right' title='Peso del rollo'>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='divTlData'>");
                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_roll_weight_max' id='Txt_roll_weight_max' placeholder='' required='' value='" + obj_data_id[28] + "'>"
                            + "</div>");
                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='text' style='width:20%' class='form-control' name='Txt_roll_weight_min' id='Txt_roll_weight_min' placeholder='' required='' value='" + obj_data_id[27] + "'>"
                            + "</div>");
                    out.print("</div>");
                    //</editor-fold>

                    out.print("</div>");
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="RUGOSIDAD Y OBSERVACIONES">
                    
                    out.print("<div class='ml-2 col-lg-2'>");
                        out.print("<span>Rugosidad</span>");
                        out.print("</div>");
                    out.print("<div class='col-lg-12'  style='display: flex;justify-content: space-around; margin-top:10px;'>");

                    out.print("<div style='margin-right:5px;' class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                            + "<input type='number' style='width:20%' class='form-control' name='Txt_min_rugosity' id='Txt_min_rugosity' data-toggle='tooltip' data-placement='top' title='Min Rugosidad' placeholder='' value='" + obj_data_id[29] + "' required='' autocomplete='off'>"
                            + "</div>");
                    out.print("<div class=\"input-group\">"
                            + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                            + "<input type='number' style='width:20%' class='form-control' name='Txt_max_rugosity' id='Txt_max_rugosity' data-toggle='tooltip' data-placement='top' title='Max Rugosidad' placeholder='' value='" + obj_data_id[30] + "' required='' autocomplete='off'>"
                            + "</div>");

                    out.print("<div class='col-lg-8'>");
                    out.print("<textarea class='form-control' name='Txt_observation' id='Txt_observation' placeholder='Observaciones' data-toggle='tooltip' data-placement='right' title='Observaciones' required=''>" + obj_data_id[31] + "</textarea>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    out.print("</div>");
                    out.print("<div class='' style='margin-top:14px;width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>" + ((temp_1 == 1) ? "Actualizar" : "Modificar") + "</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="REGISTER">
                if (code.length() == 4) {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    lst_products = JpaFactory.Productos(code);
                    if (lst_products == null || lst_products.size() == 0 || lst_products.isEmpty()) {
                        //<editor-fold defaultstate="collapsed" desc="VALIDATION CONSULT PRODUCTS">
                        out.print("<div class='cont_products'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4>Registrar Ficha Tecnica</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div style='text-align:center;'><b>El producto filtrado no existe producto, favor consulte nuevamente</b></div>");
                        out.print("<form class='needs-validation' novalidate='' action='Data_sheet?opc=1' method='post'>");
                        out.print("<input style='width:100%' type='number' class='form-control'  name='code' id='code' placeholder='Codigo' autocomplete='off' required='' min='1111' max='9999' minlength='4' maxlength='4' oninput='maxLengthCheck(this);'>");
                        out.print("<div class=\"invalid-feedback\"> Debe ingresar minimo 4 digitos</div>");
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<br/><button class='btn btn-green btn-lg'>Consultar</button>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTER DATA SHEET">
                        out.print("<div class='cont_data_sheet'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4>Registrar Ficha Tecnica</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        String[] Arr_products = lst_products.toString().replace("[", "").replace("]", "").split("////");
                        out.print("<div class=''>");
                        out.print("<form class='needs-validation' novalidate='' action='Data_sheet?opc=2' method='post'>");
                        out.print("<div class='' style='display: flex;justify-content: space-around;'>");
                        out.print("<div style='width:47%' data-toggle='tooltip' data-placement='right' title='Codigo'><input type='text'  style='background-color:  #ebebeb;' class='form-control input_none' name='Txt_code' id='Txt_code' placeholder='Codigo' value='" + Arr_products[0] + "' ></div>");
                        out.print("<div style='width:47%' data-toggle='tooltip' data-placement='right' title='Producto'><input type='text' style='background-color:  #ebebeb;' class='form-control input_none' name='Txt_product' id='Txt_product' placeholder='Producto' value='" + Arr_products[1] + "'></div>");
                        out.print("</div>");

                        out.print("<div class='' style='display: flex;justify-content: space-around; margin-top:10px;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<input type='text' class='form-control' name='Txt_name_sheet' id='Txt_name_sheet' placeholder='Nombre Ficha Tecnica'  data-toggle='tooltip' data-placement='right' title='Nombre Ficha Tecnica' required='' autocomplete='off' >");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<input type='number' class='form-control' name='version' id='Txt_version' placeholder='Version' data-toggle='tooltip' data-placement='right' title='Version' required='' autocomplete='off' >");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='divCbData'>");

                        out.print("<div style='width:24%'>");
                        //<editor-fold defaultstate="collapsed" desc="INTERNO SIN PRESURIZAR">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_intSinPre' id='Txt_intSinPre' placeholder='Interno sin presurizar' required='' data-toggle='tooltip' data-placement='right' title='Interno sin presurizar' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");
                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='text' style='width:25%' class='form-control' name='Txt_intSinPre_max' id='Txt_diameter_ex_max' placeholder='' required='' autocomplete='off' >"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='text' style='width:25%' class='form-control' name='Txt_intSinPre_min' id='Txt_diameter_ex_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div style='width:24%'>");
                        //<editor-fold defaultstate="collapsed" desc="INTERNO PRESURIZADO">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_intPre' id='Txt_intPre' placeholder='Interno presurizado' required='' data-toggle='tooltip' data-placement='right' title='Interno presurizado' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");

                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='text' style='width:25%' class='form-control' name='Txt_intPre_max' id='Txt_diameter_ex_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='text' style='width:25%' class='form-control' name='Txt_intPre_min' id='Txt_diameter_ex_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div style='width:24%;'>");
                        //<editor-fold defaultstate="collapsed" desc="EXTERNO_SIN_PRESURIZAR">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_extSinPre' id='Txt_wall_thickness' placeholder='Externo sin presurizar' required='' data-toggle='tooltip' data-placement='right' title='Externo sin presurizar' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");
                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_extSinPre_max' id='Txt_wall_thickness_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_extSinPre_min' id='Txt_wall_thickness_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");
                        out.print("</div>");

                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div style='width:24%;'>");
                        //<editor-fold defaultstate="collapsed" desc="EXTERNO_PRESURIZADO">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_extPre' id='Txt_wall_thickness' placeholder='Externo presurizado' required='' data-toggle='tooltip' data-placement='right' title='Externo presurizado' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");
                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_extPre_max' id='Txt_wall_thickness_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_extPre_min' id='Txt_wall_thickness_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");
                        out.print("</div>");

                        //</editor-fold>
                        out.print("</div>");

                        out.print("</div>");

                        out.print("<div class='divCbData'>");

                        out.print("<div style='width:24%;'>");
                        //<editor-fold defaultstate="collapsed" desc="ESPESOR DE PARED">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_wall_thickness' id='Txt_wall_thickness' placeholder='Espesor pared' required='' data-toggle='tooltip' data-placement='right' title='Espesor de Pared' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");
                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_wall_thickness_max' id='Txt_wall_thickness_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_wall_thickness_min' id='Txt_wall_thickness_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");
                        out.print("</div>");

                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div style='width:24%'>");
                        //<editor-fold defaultstate="collapsed" desc="DIAMETRO EXTERIOR BOBINA">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_diameter_coil_ex' id='Txt_diameter_coil_ex' placeholder='Diametro Exterior Bobina' required='' data-toggle='tooltip' data-placement='right' title='Diametro Exterior Bobina' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");

                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_diameter_coil_ex_max' id='Txt_diameter_coil_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='text' style='width:20%' class='form-control' name='Txt_diameter_coil_ex_min' id='Txt_diameter_coil_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div style='width:24%'>");
                        //<editor-fold defaultstate="collapsed" desc="DIAMETRO INTERIOR BOBINA">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='number' class='form-control' name='Txt_diameter_coil_in' id='Txt_diameter_coil_in' placeholder='Diametro Interior Bobina' required='' data-toggle='tooltip' data-placement='right' title='Diametro Interior Bobina' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");

                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='number' style='width:20%' class='form-control' name='Txt_diameter_coil_in_max' id='Txt_diameter_coil_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='number' style='width:20%' class='form-control' name='Txt_diameter_coil_in_min' id='Txt_diameter_coil_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div style='width:24%'>");
                        //<editor-fold defaultstate="collapsed" desc="PESO DEL ROLLO">
                        out.print("<div class='col-lg-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_roll_weight' id='Txt_roll_weight' placeholder='Peso del rollo' required='' data-toggle='tooltip' data-placement='right' title='Peso del rollo' autocomplete='off'>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='divTlData'>");

                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='number' style='width:20%' class='form-control' name='Txt_roll_weight_max' id='Txt_roll_weight_max' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='number' style='width:20%' class='form-control' name='Txt_roll_weight_min' id='Txt_roll_weight_min' placeholder='' required='' autocomplete='off'>"
                                + "</div>");

                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("</div>");

                        out.print("<div class='ml-2 col-lg-2'>");
                        out.print("<span>Rugosidad</span>");
                        out.print("</div>");

                        out.print("<div class='' style='display: flex;justify-content: space-around;'>");
                        out.print("<div class='divTlData ml-4'>");
                        out.print("<div style='margin-right:5px;' class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-plus\"></i></div>"
                                + "<input type='number' style='width:20%' class='form-control' name='Txt_min_rugosity' id='Txt_min_rugosity' data-toggle='tooltip' data-placement='top' title='Min Rugosidad' placeholder='' required='' autocomplete='off'>"
                                + "</div>");
                        out.print("<div class=\"input-group\">"
                                + "<div style='padding: 10px 8px;' class=\"input-group-text\"><i class=\"fas fa-minus\"></i></div>"
                                + "<input type='number' style='width:20%' class='form-control' name='Txt_max_rugosity' id='Txt_max_rugosity' data-toggle='tooltip' data-placement='top' title='Max Rugosidad' placeholder='' required='' autocomplete='off'>"
                                + "</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-8'>");
                        out.print("<textarea class='form-control' name='Txt_observation' id='Txt_code' placeholder='Observaciones' data-toggle='tooltip' data-placement='right' title='Observaciones' required='' autocomplete='off' ></textarea>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("</div>");

                        out.print("<div class='' style='margin-top:14px;width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                        out.print("</div>");

                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="CONSULT CODE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_products'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Consultar Producto</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<div class='' style='display: flex;'>");
            out.print("<div class='col-lg-12 col-md-12'>");
            out.print("<form class=\"needs-validation\" novalidate='' action='Data_sheet?opc=1' method='post'>");
            out.print("<input  type='number' class='form-control'  name='code' id='code' placeholder='Codigo' autocomplete='off' required='' min='1111' max='9999' minlength='4' maxlength='4' oninput='maxLengthCheck(this);'>");
            out.print("<div class=\"invalid-feedback\"> Debe ingresar minimo 4 digitos</div>");
            out.print("<div class='' style='text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Consultar</button>");
            out.print("</div>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<div id=\"accordion\">");
            out.print("<div class=\"accordion\">");

            if (state_data == 2) {
                lst_data_sheet = JpaFicha.Consult_Data_sheet();
            } else {
                lst_data_sheet = JpaFicha.Consult_Data_sheet_State(state_data);
            }
            if (lst_data_sheet != null) {
                out.print("<div id='container' class=\"container\">");
                for (int i = 0; i < lst_data_sheet.size(); i++) {
                    Object[] obj_data = (Object[]) lst_data_sheet.get(i);
                    state = Integer.parseInt(obj_data[32].toString());
                    out.print("<div id='list'><span>");
                    out.print("<div class=\"single-item\">");
                    //<editor-fold defaultstate="collapsed" desc="Cabecera">
                    out.print("<div class=\"accordion-header accc_div_dataSheet\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-" + i + "\" aria-expanded=\"true\">");
                    out.print("<div  class='styledata single-item'  style='display:flex; justify-content:space-between;width:100%; text-align:center; border-right: 3px solid " + ((state == 1) ? "green" : "#fd1e08") + "; border-left: 3px solid " + ((state == 1) ? "green" : "#fd1e08") + ";'>");

                    out.print("<div style='width:33%'>");
                    out.print("<b>" + obj_data[3] + "(" + obj_data[4] + ")</b>");
                    out.print("</div>");

                    out.print("<div style='width:33%'>");
                    out.print("<b>" + obj_data[1] + "</b>");
                    out.print("</div>");

                    out.print("<div style='width:33%'>"
                            + "");
                    if (txtPermisos.contains("[74]")) {
                        out.print("" + ((state == 1) ? "<a href='Data_sheet?opc=3&id_data_sheet=" + obj_data[0] + "&state=" + state + "'><i style='font-size:22px; color:green '  class=\"fas fa-check-circle\"></i></a>"
                                : "<a href='Data_sheet?opc=3&id_data_sheet=" + obj_data[0] + "&state=" + state + "'><i style='font-size:23px; color:#fd1e08 ' class=\"fas fa-times-circle\"></i></a>") + "");
                    } else {
                        out.print("" + ((state == 1) ? "<i style='font-size:22px; color:green '  class=\"fas fa-check-circle\"></i>"
                                : "<i style='font-size:23px; color:#fd1e08 ' class=\"fas fa-times-circle\"></i>") + "");
                    }
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Contenido">
                    out.print("<div class=\"accordion-body collapse\" id=\"panel-body-" + i + "\" style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                    out.print("<div style='display:flex; padding-top:16px; justify-content: space-evenly;'>");
                    if (txtPermisos.contains("[3]")) {
                        out.print("<div>"
                                + ((state == 1) ? "<a href='Data_sheet?opc=1&id_data_sheet=" + obj_data[0] + "&temp_1=1'><i style='font-size:25px; color:black;' class=\"far fa-hand-point-up\"></i></a>" : "")
                                + "</div>");
                    }
                    out.print("<div style='width:85%;text-align:center;'><b class='b_text' >" + obj_data[2] + "</b></div>");
                    if (txtPermisos.contains("[2]")) {
                        out.print("<div>"
                                + ((state == 1) ? "<a href='Data_sheet?opc=1&id_data_sheet=" + obj_data[0] + "'><i style='font-size:22px; color:black;' class=\"fas fa-pencil-alt\"></i></a>" : "")
                                + "</div>");
                    }
                    out.print("</div>");

                    out.print("<hr class='hr_sheet'>");
                    out.print("<div>");

                    out.print("<div style='display:flex;justify-content:space-around;width:100%;'>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Interno sin presurizar: </b>" + obj_data[5] + "</div>");
                    out.print("<div><b class='b_text'>Interno sin presurizar Min: </b>" + obj_data[6] + "</div>");
                    out.print("<div><b class='b_text'>Interno sin presurizar Max: </b>" + obj_data[7] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Interno presurizado: </b>" + obj_data[8] + "</div>");
                    out.print("<div><b class='b_text'>Interno presurizado Min: </b>" + obj_data[9] + "</div>");
                    out.print("<div><b class='b_text'>Interno presurizado Max: </b>" + obj_data[10] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Externo sin presurizar: </b>" + obj_data[11] + "</div>");
                    out.print("<div><b class='b_text'>Externo sin presurizar Min: </b>" + obj_data[12] + "</div>");
                    out.print("<div><b class='b_text'>Externo sin presurizar Max: </b>" + obj_data[13] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Externo presurizado: </b>" + obj_data[14] + "</div>");
                    out.print("<div><b class='b_text'>Externo presurizado Min: </b>" + obj_data[15] + "</div>");
                    out.print("<div><b class='b_text'>Externo presurizado Max: </b>" + obj_data[16] + "</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-around;width:100%;'>");
                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Espesor de pared: </b>" + obj_data[17] + "</div>");
                    out.print("<div><b class='b_text'>Espesor de pared Min: </b>" + obj_data[18] + "</div>");
                    out.print("<div><b class='b_text'>Espesor de pared Max: </b>" + obj_data[19] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Diametro exterior bobina: </b>" + obj_data[20] + "</div>");
                    out.print("<div><b class='b_text'>Diametro exterior bobina Min: </b>" + obj_data[21] + "</div>");
                    out.print("<div><b class='b_text'>Diametro exterior bobina Max: </b>" + obj_data[22] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Diametro interior bobina: </b>" + obj_data[23] + "</div>");
                    out.print("<div><b class='b_text'>Diametro interior bobina Min: </b>" + obj_data[24] + "</div>");
                    out.print("<div><b class='b_text'>Diametro interior bobina Max: </b>" + obj_data[25] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Peso de rollo: </b>" + obj_data[26] + "</div>");
                    out.print("<div><b class='b_text'>Peso de rollo Min: </b>" + obj_data[27] + "</div>");
                    out.print("<div><b class='b_text'>Peso de rollo Max: </b>" + obj_data[28] + "</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='DivGrip mb-4' style='height: 50px;text-align: center;width: 100%;'>");
                    out.print("<div><b class='b_text'>Min. rugosidad: </b>" + obj_data[29] + "</div>");
                    out.print("<div><b class='b_text'>Max. rugosidad: </b>" + obj_data[30] + "</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<hr class='hr_sheet'>");
                    out.print("<div class='DivObservation'>");
                    out.print("<div><b class='b_text'>Observaciones:</b><br>" + obj_data[31] + "</div>");

                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</span></div>");
                }
                out.print("</div>");
            } else {
                out.print("<div style=\"text-align:center;\"><h4>No existe fichas tecnicas registradas</h4></div>");
            }
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_data_sheet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
