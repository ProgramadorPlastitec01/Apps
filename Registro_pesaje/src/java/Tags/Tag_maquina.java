package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.MaquinaJpaController;
import Controladores.ParametrosJpaController;
import SQL.Conexion_Factory;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_maquina extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String NombreRol = sesion.getAttribute("NombreRol").toString();
        MaquinaJpaController MaquinaJpa = new MaquinaJpaController();
        ParametrosJpaController ParametrosJpa = new ParametrosJpaController();
        Conexion_Factory ProductoJpa = new Conexion_Factory();
        List lst_maquina = null;
        List lst_producto = null;
        List lst_parametros = null;
        //<editor-fold defaultstate="collapsed" desc="DESCLARAR VARIABLES">
        int id_maq = 0;
        String Txt_code = "";
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CAPTURAR VARIABLES">
        try {
            id_maq = Integer.parseInt(pageContext.getRequest().getAttribute("id_maquina").toString());
        } catch (Exception e) {
            id_maq = 0;
        }

        try {
            Txt_code = pageContext.getRequest().getAttribute("CodigoFact").toString();
        } catch (Exception e) {
            Txt_code = "";
        }

        //</editor-fold>
        try {
            if (id_maq > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR MAQUINA">

                lst_maquina = MaquinaJpa.ConsultarMaquinas_id(id_maq);
                Object[] obj_maquina = (Object[]) lst_maquina.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 40%; height: 50%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Maquina</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(3)'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_maq' style='width: 100%;'>");
                out.print("<form action='Maquina?opc=2' method='post'>");
                out.print("<input type='hidden' name='id_maq' value='" + obj_maquina[0] + "'>");
                out.print("<div style='display: flex; justify-content: space-around; margin-bottom: 15px;'>");
                out.print("<div style='width: 45%;'>");
                out.print("<b>Codigo de producto</b>");
                lst_producto = ProductoJpa.ConsultaCodigosProducto(Txt_code);
                if (lst_producto != null) {
                    out.print("<select class='form-select select2' style='' id='selectMaquina' name='Cbx_codigo'>");
                    out.print("<option value='" + obj_maquina[2] + "///" + obj_maquina[3] + " '>" + obj_maquina[2] + " - " + obj_maquina[3] + "</option>");
                    for (int i = 0; i < lst_producto.size(); i++) {
                        String[] Arg_product = lst_producto.get(i).toString().replace("]", " ").replace("[", " ").split("/");
                        if (Arg_product[0].trim().equals(obj_maquina[2].toString().trim())) {
                        } else {
                            out.print("<option value='" + Arg_product[0] + "///" + Arg_product[1] + "'>" + Arg_product[0] + " - " + Arg_product[1] + "</option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('selectMaquina');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                } else {
                    out.print("<b>No se ha encontrado el producto</b>");
                }
                out.print("</div>");
                out.print("<div style='width: 45%;'>");
                out.print("<b>Maquina</b>");
                lst_parametros = ParametrosJpa.Consultar_categorias("Maquina");
                out.print("<select class='form-select' id='Cbx_maquina' name='nmb_maq'>");
                out.print("<option value='" + obj_maquina[10] + "'>" + obj_maquina[1] + "</option>");
                if (lst_parametros != null) {
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div style='display: flex; justify-content: space-around;'>");

                out.print("<div style='width:45%;'>");
                out.print("<b>Unidades</b>");
                out.print("<input type='number' class='form-control' name='tara' id='tara' placeholder='Ingresar unidades' value='" + obj_maquina[5] + "' required autocomplete='off'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('tara');val1.add(Validate.Presence);</script>");
                out.print("</div>");

                out.print("<div style='width:45%;'>");
                out.print("<b>Molde</b>");
                out.print("<input type='text' class='form-control' name='nmb_mold' id='nmb_mold' placeholder='Ingresar molde' value='" + obj_maquina[4] + "' required autocomplete='off'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('nmb_mold');val1.add(Validate.Presence);</script>");
                out.print("</div>");
//                out.print("<div style='width:50%;'>");
//                out.print("<b>Unidad Medida</b>");
//                lst_parametros = ParametrosJpa.Consultar_categorias("Und_medida");
//                out.print("<select class='form-select' id='Cbx_unidad_medidad' name='unidad_medidad'>");
//                out.print("<option value='" + obj_maquina[11] + "'>" + obj_maquina[9] + "</option>");
//                if (lst_parametros != null) {
//                    for (int i = 0; i < lst_parametros.size(); i++) {
//                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
//                        out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
//                    }
//                }
//                out.print("</select>"
//                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_unidad_medidad');"
//                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("<div style='text-align: center; width: 100%; margin-top: 10px;'>");
                out.print("<button type='submit' class='btn btn-primary'> Editar </button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="CONSULTAR PRODUCTOS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 40%; height: 25%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Consultar Producto</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(5)'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form' style='text-align: center;'>");
            out.print("<form action='Maquina?opc=4' method='post'>");
            out.print("<input type='text' class='form-control' name='Txt_code' id='' placeholder='Ingresar codigo de producto' value='' autocomplete='off'>");
            out.print("<button class='btn btn-primary' style='margin-top: 10px;'>Consultar</button>");
            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            if (!Txt_code.equals("")) {
                //<editor-fold defaultstate="collapsed" desc="AGREGAR MAQUINA">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 40%; height: 50%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar Maquina</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(9)'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_maq' style='width: 100%;'>");
                out.print("<form action='Maquina?opc=2' method='post'>");

                out.print("<div style='display: flex; justify-content: space-around; margin-bottom: 15px;'>");
                out.print("<div style='width: 45%;'>");
                out.print("<b>Codigo de producto</b>");
                lst_producto = ProductoJpa.ConsultaCodigosProducto(Txt_code);
                if (lst_producto != null) {
                    out.print("<select class='form-select' style='' id='Cbx_codigo' name='Cbx_codigo'>");
                    out.print("<option value='0'>Seleccionar codigo</option>");
                    for (int i = 0; i < lst_producto.size(); i++) {
                        String[] Arg_product = lst_producto.get(i).toString().replace("]", " ").replace("[", " ").split("/");
                        out.print("<option value='" + Arg_product[0] + "///" + Arg_product[1] + "'>" + Arg_product[0] + " - " + Arg_product[1] + "</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_codigo');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                } else {
                    out.print("<b>No se ha encontrado el producto</b>");
                }
                out.print("</div>");

                out.print("<div style='width: 45%;'>");
                out.print("<b>Maquina</b>");
                lst_parametros = ParametrosJpa.Consultar_categorias("Maquina");
                out.print("<select class='form-select' id='Cbx_maquina' name='nmb_maq'>");
                out.print("<option value='0'>Seleccionar Maquina</option>");
                if (lst_parametros != null) {
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div style='display: flex; justify-content: space-around;'>");

//                out.print("<div style='width:45%;'>");
//                out.print("<b>Peso</b>");
//                out.print("<input type='text' class='form-control' name='nmb_peso' id='nmb_peso' placeholder='Ingresar peso' value='' required autocomplete='off'>"
//                        + "<script type='text/javascript'>var val1 = new LiveValidation('nmb_peso');val1.add(Validate.Presence);</script>");
//                out.print("</div>");
                out.print("<div style='width:45%;'>");
                out.print("<b>Tara</b>");
                out.print("<input type='number' class='form-control' name='tara' id='tara' placeholder='Ingresar cantidad de unidades' value='' required autocomplete='off'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('tara');val1.add(Validate.Presence);</script>");
                out.print("</div>");

                out.print("<div style='width:45%;'>");
                out.print("<b>Molde</b>");
                out.print("<input type='text' class='form-control' name='nmb_mold' id='nmb_mold' placeholder='Ingresar molde' value='' required autocomplete='off'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('nmb_mold');val1.add(Validate.Presence);</script>");
                out.print("</div>");

//                out.print("<div style='width:45%;'>");
//                out.print("<b>Unidad Medida</b>");
//                lst_parametros = ParametrosJpa.Consultar_categorias("Und_medida");
//                out.print("<select class='form-select' id='Cbx_unidad_medidad' name='unidad_medidad'>");
//                out.print("<option value='0'>Seleccionar Unidad Medidad</option>");
//                if (lst_parametros != null) {
//                    for (int i = 0; i < lst_parametros.size(); i++) {
//                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
//                        out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
//                    }
//                }
//                out.print("</select>"
//                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_unidad_medidad');"
//                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("<div style='text-align: center; width: 100%; margin-top: 10px;'>");
                out.print("<button type='submit' class='btn btn-primary'> Registrar </button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL - CONSULTA">
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE TABLA - BUSCADOR - AGREGAR">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'> Gestion de Maquinas</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<form action='' method='post'>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='margin-top: -7px;'>");
            out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'"
                    + "type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("</div>");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</form>");

            out.print("</li>");
            out.print("</ol>");
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                out.print("<a onclick='mostrarConvencion(5)'"
                        + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white'>Agregar <i class='fas fa-plus'></i></a>");
            }
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
            out.print("<h3 class='box-title'>Tabla Maquinas</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap' id='resultados'>");
            out.print("<thead>");
            out.print("<tr align='center'>");
            out.print("<th class='border-top-0'>Maquina</th>");
            out.print("<th class='border-top-0'>Codigo Producto</th>");
            out.print("<th class='border-top-0'>Molde</th>");
            out.print("<th class='border-top-0'>Unidades x Kilo (Tara)</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("<th class='border-top-0' colspan='2'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            lst_maquina = MaquinaJpa.ConsultarMaquinas();
            out.print("<tbody>");
            if (lst_maquina != null) {
                for (int i = 0; i < lst_maquina.size(); i++) {
                    Object[] obj_maquina = (Object[]) lst_maquina.get(i);
                    out.print("<tr align='center'>");
                    out.print("<td>" + obj_maquina[1] + "</td>");

                    out.print("<td class='tooltip3'><span>" + obj_maquina[2] + "</span>");
                    out.print("<span class='tooltiptext'>" + ((obj_maquina[3] == null) ? "Producto no encontrado" : obj_maquina[3]) + "</span></td>");

                    out.print("<td>" + obj_maquina[4] + "</td>");
                    out.print("<td>" + obj_maquina[5] + "</td>");
//                    String[] tara = obj_maquina[5].toString().split("///");
//                    out.print("<td>" + tara[0] + " x " + tara[1] + " </td>");
//                    out.print("<td>" + obj_maquina[10] + "</td>");
                    if (obj_maquina[6].toString().equals("ACTIVO")) {
                        out.print("<td><b style='color:#7ace4c;'>" + obj_maquina[6] + "</b></td>");
                    } else {
                        out.print("<td><b style='color:#f33155;'>" + obj_maquina[6] + "</b></td>");
                    }
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                        out.print("<td><a class='btn btn-warning' href='Maquina?opc=1&id_maq=" + obj_maquina[0] + "' title='Editar Maquina'><i class='fas fa-edit'></i></a></td>");
                        out.print("<td><a class='btn btn-primary' href='Maquina?opc=3&id_maq=" + obj_maquina[0] + "&est=" + obj_maquina[9] + "' style='' title='Cambiar Estado'><i class='" + (((Integer) obj_maquina[9] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    } else {
                        out.print("<td><a class='btn btn-warning disabled' href='' title='Editar Maquina'><i class='fas fa-edit'></i></a></td>");
                        out.print("<td><a class='btn btn-primary disabled' href='' style='' title='Cambiar Estado'><i class='" + (((Integer) obj_maquina[9] == 1) ? "fas fa-check" : "fas fa-times") + "'></i></a></td>");
                    }
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
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
            Logger.getLogger(Tag_maquina.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
