package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.EntradaMaterialJpaController;
import Controladores.ParametrosJpaController;
import SQL.Conexion_Factory;
import java.util.List;

public class Tag_EntradaMaterial extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String NombreRol = sesion.getAttribute("NombreRol").toString();
        EntradaMaterialJpaController EntradaJpa = new EntradaMaterialJpaController();
        ParametrosJpaController ParametrosJpa = new ParametrosJpaController();
        Conexion_Factory FactoryJpa = new Conexion_Factory();
        List lst_entrada = null;
        List lst_turnos = null;
        List lst_linea = null;
        List lst_factory = null;
        List lst_entrada_id = null;
        String codigo = "";
        int id_entrada = 0, temp = 0;
        try {
            try {
                temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
            } catch (NumberFormatException e) {
                temp = 0;
            }
            if (temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="TABLA ENTRADA DETALLE">
                try {
                    id_entrada = Integer.parseInt(pageContext.getRequest().getAttribute("id_entrada").toString());
                } catch (NumberFormatException e) {
                    id_entrada = 0;
                }
                try {
                    codigo = pageContext.getRequest().getAttribute("codigo").toString();
                } catch (Exception e) {
                    codigo = "";
                }
                //<editor-fold defaultstate="collapsed" desc="CONSULTA CODIGO - PRODUCTO FACTORY">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_entrada_Material' style='height: auto; width:31%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Consulta código para ingresar material</h3>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class=\"fas fa-times\"></i></button>");
                out.print("</div>");
                out.print("<form action='EntradaMaterial?opc=1' method='post'>");
                out.print("<b>Código del producto</b>");
                out.print("<input type='text' class='form-control' name='codigo' id='Código' placeholder='Consulta código' autocomplete='off' value=''>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Código');val1.add(Validate.Presence);</script>");
                out.print("<div style='margin-left:70%;margin-top:2%'>"
                        + "<button class=\"btn btn-primary\" type='submit' name='' id='' placeholder='' value='Consultar'>Consultar</button>"
                        + "</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                if (!codigo.equals("")) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA MATERIAL">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_orden'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Registrar Entrada</h4>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(2)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='EntradaMaterial?opc=2' method='post'>");

                    out.print("<div class='mb-2' style='display:flex;'>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Fecha</b>");
                    out.print("<input type='date' class='form-control' name='fecha' id='fecha' placeholder='Fecha'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('fecha');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Turno</b>");
                    lst_turnos = ParametrosJpa.Consultar_categorias("Turnos");
                    if (lst_turnos != null) {
                        out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' placeholder='Seleccionar Turno'>");
                        out.print("<option value='0'>Selecccione Turno</option>");
                        for (int i = 0; i < lst_turnos.size(); i++) {
                            Object[] obj_turno = (Object[]) lst_turnos.get(i);
                            out.print("<option value='" + obj_turno[2] + "'>" + obj_turno[2] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"¡Alerta!\","
                                + "text:\"No se encontraron turnos, comunicarse con T.I.\","
                                + "type: \"warning\","
                                + "});");
                        out.print("</script>");
                    }
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Maquina</b>");
                    lst_linea = ParametrosJpa.Consultar_categorias("Maquina");
                    if (lst_linea != null) {
                        out.print("<select class='form-control' name='Cbx_linea' id='Cbx_linea' placeholder='Seleccionar Maquina'>");
                        out.print("<option value='0'>Selecccione Maquina</option>");
                        for (int i = 0; i < lst_linea.size(); i++) {
                            Object[] obj_maquina = (Object[]) lst_linea.get(i);
                            out.print("<option value='" + obj_maquina[2] + "'>" + obj_maquina[2] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"¡Alerta!\","
                                + "text:\"No se encontraron turnos, comunicarse con T.I.\","
                                + "type: \"warning\","
                                + "});");
                        out.print("</script>");
                    }
                    out.print("</div>");

                    out.print("</div>");
                    out.print("<div class='mb-2' style='display:flex; '>");
                    out.print("<div style='width:70%;margin-right: 3%;'>");
                    lst_factory = FactoryJpa.ConsultaCodigosProducto(codigo);
                    if (lst_factory != null && lst_factory.size() > 0) {

                        out.print("<b>Producto</b>");
                        out.print("<select class='form-select' style='' id='producto' name='producto'>");
                        out.print("<option value='0'>Seleccionar codigo</option>");
                        for (int i = 0; i < lst_factory.size(); i++) {
                            String[] Arg_product = lst_factory.get(i).toString().replace("]", " ").replace("[", " ").split("/");
                            out.print("<option>" + Arg_product[0] + " - " + Arg_product[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('producto');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"¡Alerta!\","
                                + "text:\"No se encontraron referencias con el código digitado. Ingrese el código manualmente\","
                                + "timer: \"3000\","
                                + "showConfirmButton: \"false\","
                                + "type: \"warning\","
                                + "});");
                        out.print("</script>");
                        out.print("<b>Producto</b>");
                        out.print("<input type='text' class='form-control' name='producto' value='' id='producto' placeholder='Ingrese el código manual'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('producto');val1.add(Validate.Presence);</script>");
                    }
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Lote Producto</b>");
                    out.print("<input type='text' class='form-control' name='loteprod' value='' id='loteprod' placeholder='Lote Producto'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('loteprod');val1.add(Validate.Presence);</script>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='mb-2' style='display:flex;'>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Lote C</b>");
                    out.print("<input type='text' class='form-control' name='lotec' value='' id='lotec' placeholder='Lote C'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('lotec');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Lote P</b>");
                    out.print("<input type='text' class='form-control' name='lotep' value='' id='lotep' placeholder='Lote P'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('lotep');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:33%;margin-right: 3%;'>");
                    out.print("<b>Cantidad</b>");
                    out.print("<input type='number' class='form-control' name='cantidad' value='' id='cantidad' placeholder='Cantidad'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('cantidad');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='margin-right: 3%;'>");
                    out.print("<b>Observación</b>");
                    out.print("<textarea class='form-control' name='observacion' id='observacion' placeholder='Observación'></textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('observacion');val1.add(Validate.Presence);</script>");
                    out.print("</div>");

                    out.print("<div style='margin: 1%;margin-left: 85%;'><button class=\"btn btn-primary\" type='submit' name='' id='' placeholder='' value='Registrar'>Registrar</button></div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                if (id_entrada > 0) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR ENTRADA MATERIAL">
                    lst_entrada_id = EntradaJpa.ConsultaEntradaMaterial_Id(id_entrada);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_orden'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Modificar Entrada</h4>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(3)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    if (lst_entrada_id != null) {
                        Object[] obj_entrada = (Object[]) lst_entrada_id.get(0);
                        out.print("<form action='EntradaMaterial?opc=2' method='post'>");
                        out.print("<input type='hidden' name='id_entrada' value='" + id_entrada + "'>");
                        out.print("<div class='mb-2' style='display:flex;'>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Fecha</b>");
                        out.print("<input type='date' class='form-control' name='fecha' id='fecha' placeholder='Fecha' value='" + obj_entrada[1] + "'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('fecha');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Turno</b>");
                        lst_turnos = ParametrosJpa.Consultar_categorias("Turnos");
                        if (lst_turnos != null) {
                            out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' placeholder='Seleccionar Turno'>");
                            out.print("<option value='" + obj_entrada[2] + "'>" + obj_entrada[2] + "</option>");
                            for (int i = 0; i < lst_turnos.size(); i++) {
                                Object[] obj_turno = (Object[]) lst_turnos.get(i);
                                if (!obj_entrada[2].equals(obj_turno[2])) {
                                    out.print("<option value='" + obj_turno[2] + "'>" + obj_turno[2] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<script type='text/javascript'>");
                            out.print("swal({"
                                    + "title:\"¡Alerta!\","
                                    + "text:\"No se encontraron turnos, comunicarse con T.I.\","
                                    + "type: \"warning\","
                                    + "});");
                            out.print("</script>");
                        }
                        out.print("</div>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Maquina</b>");
                        lst_linea = ParametrosJpa.Consultar_categorias("Maquina");
                        if (lst_linea != null) {
                            out.print("<select class='form-control' name='Cbx_linea' id='Cbx_linea' placeholder='Seleccionar Maquina'>");
                            out.print("<option value='" + obj_entrada[3] + "'>" + obj_entrada[3] + "</option>");
                            for (int i = 0; i < lst_linea.size(); i++) {
                                Object[] obj_maquina = (Object[]) lst_linea.get(i);
                                if (!obj_entrada[3].equals(obj_maquina[2])) {
                                    out.print("<option value='" + obj_maquina[2] + "'>" + obj_maquina[2] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<script type='text/javascript'>");
                            out.print("swal({"
                                    + "title:\"¡Alerta!\","
                                    + "text:\"No se encontraron turnos, comunicarse con T.I.\","
                                    + "type: \"warning\","
                                    + "});");
                            out.print("</script>");
                        }
                        out.print("</div>");

                        out.print("</div>");
                        out.print("<div class='mb-2' style='display:flex; '>");
                        out.print("<div style='width:70%;margin-right: 3%;'>");
                        out.print("<b>Producto</b>");
                        out.print("<input type='text' class='form-control' name='producto'  id='producto' value='" + obj_entrada[4] + "' placeholder='Ingrese el código manual' readonly='false'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('producto');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Lote Producto</b>");
                        out.print("<input type='text' class='form-control' name='loteprod' value='" + obj_entrada[5] + "' id='loteprod' placeholder='Lote Producto'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('loteprod');val1.add(Validate.Presence);</script>");
                        out.print("</div>");

                        out.print("</div>");

                        out.print("<div class='mb-2' style='display:flex;'>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Lote C</b>");
                        out.print("<input type='text' class='form-control' name='lotec' value='" + obj_entrada[6] + "' id='lotec' placeholder='Lote C'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('lotec');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Lote P</b>");
                        out.print("<input type='text' class='form-control' name='lotep' value='" + obj_entrada[7] + "' id='lotep' placeholder='Lote P'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('lotep');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div style='width:33%;margin-right: 3%;'>");
                        out.print("<b>Cantidad</b>");
                        out.print("<input type='number' class='form-control' name='cantidad' value='" + obj_entrada[8] + "' id='cantidad' placeholder='Cantidad'>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('cantidad');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div style='margin-right: 3%;'>");
                        out.print("<b>Observación</b>");
                        out.print("<textarea class='form-control' name='observacion' id='observacion' placeholder='Observación'>" + obj_entrada[12] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('observacion');val1.add(Validate.Presence);</script>");
                        out.print("</div>");

                        out.print("<div style='margin: 1%;margin-left: 85%;'><button class=\"btn btn-primary\" type='submit' name='' id='' placeholder='' value=''>Modificar</button></div>");
                        out.print("</form>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="CABECERA">
                out.print("<div class='page-wrapper'>");
                out.print("<div class='page-breadcrumb bg-white'>");
                out.print("<div class='row align-items-center'>");
                out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
                out.print("<h4 class='page-title' style='width: 290px;'> R-PRF-015</h4>");
                out.print("</div>");
                out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
                out.print("<div class='d-md-flex' style='height: 33px;'>");
                out.print("<ol class='breadcrumb ms-auto'>");
                out.print("<li>");
                out.print("<div class='input-group'>");
                out.print("<div class='form-outline' style='margin-top: -7px;'>");
                out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();"
                        + " type='search' id='form1' class='form-control' placeholder='Buscar..' />");
                out.print("</div>");
                out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
                out.print("<i class='fas fa-search'></i>");
                out.print("</button>");
                out.print("</div>");
                out.print("</li>");
                out.print("</ol>");
                if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                    out.print("<a onclick='mostrarConvencion(1)'"
                            + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Agregar Entrada'>Agregar <i class='fas fa-plus'></i></a>");
                }
                out.print("<a href='EntradaMaterial?opc=1&temp=1'"
                        + "class='btn btn-primary  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='R-PRF-015'>Reporte R-PRF-015 <i class='fas fa-file-alt'></i></a>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL - CONSULTA">
                out.print("<div class='container-fluid'>");
                out.print("<div class='row'>");
                out.print("<div class='col-sm-12'>");
                out.print("<div class='white-box'>");
                out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
                out.print("<h3 class='box-title'>Tabla de Entrada de Material</h3>");
                out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
                out.print("</div>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-hover text-nowrap table-sm' id='resultados'>");
                out.print("<thead>");
                out.print("<tr>");
//            out.print("<th class='border-top-0'>Estado</th>");
                out.print("<th class='border-top-0'>Fecha / Turno</th>");
                out.print("<th class='border-top-0'>Linea</th>");
                out.print("<th class='border-top-0'>Nombre Producto</th>");
                out.print("<th class='border-top-0'>Lote</th>");
                out.print("<th class='border-top-0'>Cantidad</th>");
                out.print("<th class='border-top-0'>Responsable</th>");
                out.print("<th class='border-top-0'>Observación</th>");
                out.print("<th class='border-top-0' style='text-align:center;' colspan='2'>Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_entrada = EntradaJpa.ConsultaEntradaMaterial();
                if (lst_entrada != null) {
                    for (int i = 0; i < lst_entrada.size(); i++) {
                        Object[] obj_entrada = (Object[]) lst_entrada.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_entrada[1] + "<br>" + obj_entrada[2] + "</td>");
                        out.print("<td>" + obj_entrada[3] + "</td>");
                        if (obj_entrada[4].toString().contains("///")) {
                            out.print("<td>" + obj_entrada[4].toString().split("///")[0] + "<br/>");
                            out.print("" + obj_entrada[4].toString().split("///")[1] + "</td>");
                        } else {
                            out.print("<td>" + obj_entrada[4] + "</td>");
                        }
                        out.print("<td><b>Prod: </b>" + obj_entrada[5] + "</br>");
                        out.print("<b>C: </b>" + obj_entrada[6] + "</br>");
                        out.print("<b>P: </b>" + obj_entrada[7] + "</td>");
                        out.print("<td>" + obj_entrada[8] + "</td>");
                        out.print("<td><b style='color:#6f6f6f'>" + ((obj_entrada[9] != null) ? obj_entrada[9] : "Sin firma Encagarda") + "</b>");
                        out.print("<br/><b style='color:black'>" + ((obj_entrada[10] != null) ? obj_entrada[10] : "Sin firma Coordinadora") + "</b>");
                        out.print("<br/><b style='color:#045FB4'>" + ((obj_entrada[11] != null) ? obj_entrada[11] : "Sin firma Ins. calidad") + "</b></td>");
                        out.print("<td>" + obj_entrada[12] + "</td>");
                        if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada") || NombreRol.equals("Inspectora Calidad")) {
                            if (obj_entrada[9] != null && obj_entrada[10] != null && obj_entrada[11] != null) {
                                out.print("<td><a href='' class='btn btn-secondary disabled'><i style='color:#fff;' class=\"fas fa-ban\" title='Cerrado por firmas'></i></a></td>");
                            } else {
                                out.print("<td><a href='EntradaMaterial?opc=1&id_entrada=" + obj_entrada[0] + "' class='btn btn-warning'><i style='color:#fff;' class=\"fas fa-edit\" title='Modificar Entrada'></i></a></td>");
                            }
                            out.print("<td><a href='EntradaMaterial?opc=3&id_entrada=" + obj_entrada[0] + "' class='btn btn-info'><i style='color:#fff;' class=\"fas fa-signature\" title='Firmar entrada de material'></i></a></td>");
                        } else {
                            out.print("<td><a href='' class='btn btn-secondary disabled'><i style='color:#fff;' class=\"fas fa-ban\" title='Cerrado por firmas'></i></a></td>");
                            out.print("<td><a href='' class='btn btn-info disabled'><i style='color:#fff;' class=\"fas fa-signature\" title='Firmar entrada de material'></i></a></td>");

                        }
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr><td colspan='8'>No existen datos registrados</td></tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager0 = new Pager0('resultados', 5);");
                out.print("pager0.init();");
                out.print("pager0.showPageNav('pager0','NavPosicion0');");
                out.print("pager0.showPage(1);");
                out.print("</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="R-PRF-015 DIGITAL">
                //<editor-fold defaultstate="collapsed" desc="CABECERA">
                out.print("<div class='page-wrapper'>");
                out.print("<div class='page-breadcrumb bg-white'>");
                out.print("<div class='row align-items-center'>");
                out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
                out.print("<div style='display: flex;align-items: baseline;'>");
                out.print("<a style='background: white; border: 1px solid white;margin-right:15px;' href='EntradaMaterial?opc=1&id_entrada=&temp=0' class='btn btn-secondary' title='Volver a Ordenes de Produccion'><img src='Interfaz/Contenido/Imagenes/reply.png' width='15'></a>");
                out.print("<h4 class='page-title'> Reporte R-PRF-015</h4>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
                out.print("<div class='d-md-flex' style='height: 33px;'>");
                out.print("<ol class='breadcrumb ms-auto'>");
                out.print("<li>");
                out.print("<form action='' method='post'>");
                out.print("<div class='input-group'>");
                out.print("<div class='form-outline' style='margin-top: -7px;'>");
                out.print("<input style='height: 33px;' id='search-focus' onkeyup='FiltrarTBody()' onchange='javascript:this.value=this.value.toUpperCase();'"
                        + "type='search' id='form1' class='form-control' placeholder='Buscar..' />");
                out.print("</div>");
                out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
                out.print("<i class='fas fa-search'></i>");
                out.print("</button>");
                out.print("<a onclick=\"exportToExcel()\"  style='margin-top:-7px;'"
                        + "class='btn btn-success  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Exportar a Excel'>Exportar a Excel <i class='fas fa-file-excel'></i></a>");
                out.print("</div>");
                out.print("</form>");

                out.print("</li>");
                out.print("</ol>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                out.print("<div class='container-fluid'>");
                out.print("<div class='row'>");
                out.print("<div class='col-sm-12'>");
                out.print("<div class='white-box'>");
                out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
                out.print("<h3 class='box-title'>Tabla Entrada de Material</h3>");
                out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
                out.print("</div>");
                out.print("<div class='table-responsive' >");
                out.print("<table class='table-bordered tableMain' id='TableStyle' >");
                out.print("<tr>");
                out.print("<td colspan='10' style='background-color:#CCC;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td rowspan='2' colspan='3' align='center' style='width:26%'>");
                out.print("<img src='Interfaz/Contenido/Imagenes/Plast.png' alt='Logo' style='width:211px;height:77px' /></td>");
                out.print("<td align='center' colspan='4' style='width:48%' ><b>REGISTRO</td>");
                out.print("<td align='center' colspan='3' style='width:26%'><b>CODIGO : R-PRF-015</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' colspan='4'><b>CONTROL DE ENTRADA DE MATERIAL</td>");
                out.print("<td align='center' colspan='3'><b>VERSIÓN 3</td>");
                out.print("</tr>");
                out.print("<tr align='center' style='font-size: 12PX;font-weight: bold;'>");
                out.print("<td>FECHA</td>");
                out.print("<td>TURNO</td>");
                out.print("<td>LINEA</td>");
                out.print("<td>NOMBRE DEL PRODUCTO</td>");
                out.print("<td>INSUMO</td>");
                out.print("<td>LOTE PRODUCTO</td>");
                out.print("<td>CANTIDAD</td>");
                out.print("<td>Vo. ENCARGADA</td>");
                out.print("<td>Vo. COORDINADORA</td>");
                out.print("<td>INS. CALIDAD</td>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody id='resultados'>");
                lst_entrada = EntradaJpa.ConsultaEntradaMaterial();
                if (lst_entrada != null) {
                    for (int i = 0; i < lst_entrada.size(); i++) {
                        Object[] obj_reporte = (Object[]) lst_entrada.get(i);
                        out.print("<tr  style='font-size: 13px;'>");
                        out.print("<td>" + obj_reporte[1] + "</td>");
                        out.print("<td>" + obj_reporte[2] + "</td>");
                        out.print("<td>" + obj_reporte[3] + "</td>");
                        if (obj_reporte[4].toString().contains("///")) {
//                            out.print("<td>" + obj_reporte[4].toString().split("///")[0] + "<br>" + obj_reporte[4].toString().split("///")[1] + "</td>");
                            out.print("<td>" + obj_reporte[4].toString().replace("///", "-") + "</td>");
                        } else {
                            out.print("<td>" + obj_reporte[4] + "</td>");
                        }
                        out.print("<td style='width:14%;'><b>C:</b>" + obj_reporte[6] + " <hr style='margin-top:0px;margin-bottom:0px;' /><b>P:</b>" + obj_reporte[7] + "</td>");
//                        out.print("<td style='width:14%;'><b>C:</b>" + obj_reporte[6] + " <hr style='margin-top:0px;margin-bottom:0px;' /><b>P:</b>" + obj_reporte[7] + "</td>");
                        out.print("<td>" + obj_reporte[5] + "</td>");
                        out.print("<td>" + obj_reporte[8] + "</td>");
                        out.print("<td><b style='color:#6f6f6f'>" + ((obj_reporte[9] != null) ? obj_reporte[9] : "Sin firma Encagarda") + "</b></td>");
                        out.print("<td><b style='color:black'>" + ((obj_reporte[10] != null) ? obj_reporte[10] : "Sin firma Coordinadora") + "</b></td>");
                        out.print("<td><b style='color:#045FB4'>" + ((obj_reporte[11] != null) ? obj_reporte[11] : "Sin firma Ins. calidad") + "</b></td>");
                        out.print("</tr>");

                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager0 = new Pager0('resultados', 10);");
                out.print("pager0.init();");
                out.print("pager0.showPageNav('pager0','NavPosicion0');");
                out.print("pager0.showPage(1);");
                out.print("</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_EntradaMaterial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
