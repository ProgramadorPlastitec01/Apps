package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.RecipienteJpaController;
import Controladores.ParametrosJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_recipiente extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        RecipienteJpaController RecipienteJpa = new RecipienteJpaController();
        ParametrosJpaController ParametrosJpa = new ParametrosJpaController();
        List lst_recipiente = null;
        List lst_parametros = null;
        //<editor-fold defaultstate="collapsed" desc="DECLARACION DE VARIABLES">
        int id_rec = 0;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CAPTURAR DATOS">
        try {
            id_rec = Integer.parseInt(pageContext.getRequest().getAttribute("id_recipiente").toString());
        } catch (Exception e) {
            id_rec = 0;
        }
        //</editor-fold>
        try {
            if (id_rec > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR RECIPIENTE">
                lst_recipiente = RecipienteJpa.ConsultarRecipientesId(id_rec);
                Object[] obj_recipiente = (Object[]) lst_recipiente.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='height: 46%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Recipiente</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_rec'>");
                out.print("<form action='Recipiente?opc=2' method='post'>");

                out.print("<div class='cont_form_sty'>");
                out.print("<div style='width: 40%'>");
                out.print("<input type='hidden' name='id_rec' value='" + obj_recipiente[0] + "'>");
                out.print("<b>Recipiente</b>");
                out.print("<input type='text' class='form-control' name='Txt_rec' id='Txt_rec' placeholder='Ingresar nombre de recipiente' value='" + obj_recipiente[1] + "'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_rec');val1.add(Validate.Presence);</script>");
                out.print("</div>");

                out.print("<div style='width: 40%;display: flex; justify-content: space-around;'>");
                out.print("<div style='width: 50%; margin-right:6px;'>");
                out.print("<b>Peso de recipiente</b>");
                out.print("<input type='number' class='form-control' name='peso_rec' id='peso_rec' placeholder='Ingresar peso de recipiente' value='" + obj_recipiente[2] + "'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('peso_rec');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("<div style='width:50%;'>");
                out.print("<b>Unidad Medida</b>");
                lst_parametros = ParametrosJpa.Consultar_categorias("Und_medida");
                out.print("<select style='margin:10px;' class='form-select' id='Cbx_medida_recipiente' name='medida_recipiente'>");
                out.print("<option value='" + obj_recipiente[9] + "'>" + obj_recipiente[10] + "</option>");
                if (lst_parametros != null) {
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_medida_recipiente');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='cont_form_sty'>");
                out.print("<div style='width: 40%'>");
                out.print("<b>Bolsa</b>");
                out.print("<input type='text' class='form-control' name='Txt_bolsa' id='Txt_bolsa' placeholder='Ingresar nombre de bolsa' value='" + obj_recipiente[3] + "'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_bolsa');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                
                out.print("<div style='width: 40%;display: flex; justify-content: space-around;'>");
                out.print("<div style='width: 50%; margin-right:6px;'>");
                out.print("<b>Peso de bolsa</b>");
                out.print("<input type='number' class='form-control' name='peso_bolsa'id='peso_bolsa' placeholder='Ingresar peso de bolsa' value='" + obj_recipiente[4] + "'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('peso_bolsa');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("<div style='width:50%;'>");
                out.print("<b>Unidad Medida</b>");
                lst_parametros = ParametrosJpa.Consultar_categorias("Und_medida");
                out.print("<select style='margin:10px;' class='form-select' id='Cbx_medida_bolsa' name='medida_bolsa'>");
                out.print("<option value='" + obj_recipiente[11] + "'>" + obj_recipiente[12] + "</option>");
                if (lst_parametros != null) {
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_medida_bolsa');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<button class='btn btn-primary' type='submit'> Editar </button>");
                out.print("</form>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="AGREGAR RECIPIENTE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='height:46%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Recipiente</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(5)'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_rec'>");
            out.print("<form action='Recipiente?opc=2' method='post'>");

            out.print("<div class='cont_form_sty'>");
            out.print("<div style='width: 40%'>");
            out.print("<b>Recipiente</b>");
            out.print("<input type='text' class='form-control' name='Txt_rec' id='Txt_rec' placeholder='Ingresar nombre de recipiente' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_rec');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width: 40%;display: flex;justify-content: space-around;'>");
            out.print("<div style='width: 50%;margin-right:6px;'>");
            out.print("<b>Peso de recipiente</b>");
            out.print("<input type='number' class='form-control' name='peso_rec' id='peso_rec' placeholder='Ingresar peso' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('peso_rec');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width: 50%'>");
            out.print("<b>Unidad Medida</b>");
            lst_parametros = ParametrosJpa.Consultar_categorias("Und_medida");
            out.print("<select style='margin:10px;' class='form-select' id='Cbx_medida_recipiente' name='medida_recipiente'>");
            out.print("<option value='0'>Seleccionar</option>");
            if (lst_parametros != null) {
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                }
            }
            out.print("</select>"
                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_medida_recipiente');"
                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");

            out.print("<div class='cont_form_sty'>");
            out.print("<div style='width: 40%'>");
            out.print("<b>Bolsa</b>");
            out.print("<input type='text' class='form-control' name='Txt_bolsa' id='Txt_bolsa' placeholder='Ingresar nombre de bolsa' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_bolsa');val1.add(Validate.Presence);</script>");
            out.print("</div>");

            out.print("<div style='width: 40%;display: flex;justify-content: space-around;'>");
            out.print("<div style='width: 50%;margin-right:6px;'>");
            out.print("<b>Peso de bolsa</b>");
            out.print("<input type='number' class='form-control' name='peso_bolsa'id='peso_bolsa' placeholder='Ingresar peso' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('peso_bolsa');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width: 50%'>");
            out.print("<b>Unidad Medida</b>");
            lst_parametros = ParametrosJpa.Consultar_categorias("Und_medida");
            out.print("<select style='margin:10px;' class='form-select' id='Cbx_medida_bolsa' name='medida_bolsa'>");
            out.print("<option value='0'>Seleccionar</option>");
            if (lst_parametros != null) {
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                }
            }
            out.print("</select>"
                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_medida_bolsa');"
                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
            out.print("</div>");

            out.print("</div>");

            out.print("</div>");

            out.print("<button class='btn btn-primary' type='submit'> Registrar </button>");
            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL - CONSULTA">
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE TABLA - BUSCADOR - AGREGAR">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'>Recipiente</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='margin-top: -7px;'>");
            out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'"
                    + "type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("</div>");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</li>");
            out.print("</ol>");
            out.print("<a onclick='mostrarConvencion(5)' "
                    + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white'>Agregar <i class='fas fa-plus'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            out.print("<div class='white-box'>");
            out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
            out.print("<h3 class='box-title'>Tabla Recipiente</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap' id='resultados'>");
            out.print("<thead>");
            out.print("<tr align='center'>");
            out.print("<th class='border-top-0'>Recipiente</th>");
            out.print("<th class='border-top-0'>Peso</th>");
            out.print("<th class='border-top-0'>Bolsa</th>");
            out.print("<th class='border-top-0'>Peso</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("<th class='border-top-0'>Usuario Registro</th>");
            out.print("<th class='border-top-0' colspan='2'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_recipiente = RecipienteJpa.ConsultarRecipientes();
            if (lst_recipiente != null) {
                for (int i = 0; i < lst_recipiente.size(); i++) {
                    Object[] obj_recipiente = (Object[]) lst_recipiente.get(i);
                    out.print("<tr align='center'>");
                    out.print("<td>" + obj_recipiente[1] + "</td>");
                    out.print("<td>" + obj_recipiente[2] + " " + obj_recipiente[10] + "</td>");
                    out.print("<td>" + obj_recipiente[3] + "</td>");
                    out.print("<td>" + obj_recipiente[4] + " " + obj_recipiente[12] + "</td>");
                    if (obj_recipiente[6].toString().equals("ACTIVO")) {
                        out.print("<td><b style='color:#7ace4c;'>" + obj_recipiente[6] + "</b></td>");
                    } else {
                        out.print("<td><b style='color:#f33155;'>" + obj_recipiente[6] + "</b></td>");
                    }
                    out.print("<td>" + obj_recipiente[7] + "</td>");
                    out.print("<td><a href='Recipiente?opc=1&id_rec=" + obj_recipiente[0] + "' class='btn btn-warning' title='Editar Recipiente'><i class='fas fa-edit'></i></a></td>");
                    out.print("<td><a href='Recipiente?opc=3&id_rec=" + obj_recipiente[0] + "&est=" + obj_recipiente[5] + "' class='btn btn-primary' title='Cambiar Estado' style='width: 44%;' ><i class='" + (((Integer) obj_recipiente[5] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='9' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
                out.print("</tr>");
            }

            out.print("</tbody>");
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 5);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- Fin -->");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_recipiente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
