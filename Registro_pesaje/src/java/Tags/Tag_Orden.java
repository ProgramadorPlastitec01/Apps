package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import SQL.Conexion_Factory;
import Controladores.OrdenJpaController;
import Controladores.MaquinaJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

import Controladores.ParametrosJpaController;

public class Tag_Orden extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String NombreRol = sesion.getAttribute("NombreRol").toString();
        MaquinaJpaController jpamqn = new MaquinaJpaController();
        OrdenJpaController OrdenProduccionJpa = new OrdenJpaController();
        ParametrosJpaController ParametrosJpa = new ParametrosJpaController();
        Conexion_Factory sqlcft = new Conexion_Factory();
        List lst_orden = null;
        List lst_orden_id = null;
        List lst_orden_factory = null;
        List lst_maquinas = null;
        List lst_parametros = null;
        List lst_clientes = null;
        int id_orden = 0, pesoxgramos = 0, numero_orden = 0;
        try {
            try {
                id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
            } catch (Exception e) {
                id_orden = 0;
            }
            try {
                numero_orden = Integer.parseInt(pageContext.getRequest().getAttribute("numero_orden").toString());
            } catch (Exception e) {
                numero_orden = 0;
            }
            // <editor-fold defaultstate="collapsed" desc="CONSULTA OP FACTORY">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_orden' style='height: auto; width: 42%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registro de ordenes</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(2)'><i class=\"fas fa-times\"></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_orden' style='margin-left:1%; display: flex;'>");
            out.print("<button class='btn btn-primary' onclick='ChangeMode(1,2)'>Registro Orden</button>");
            out.print("<button class='btn btn-primary' onclick='ChangeMode(2,1)'>Consulta Factory</button>");
            out.print("</div>");

            out.print("<div class='' id='contOrder1' style='display: block; margin-top: 15px;'>");
            out.print("<form action='Orden?opc=5' method='post'>");

            out.print("<div class='' style='display: flex; justify-content: space-around'>");
            out.print("<div class=''>");
            out.print("<b>Numero de orden</b>");
            out.print("<input type='number' class='form-control' name='TxtOrden' style='width: 230px;' id='TxtOrden' placeholder='' data-toggle='tooltip' data-placement='top' title='' >");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('TxtOrden');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<b>Cantidad programada</b>");
            out.print("<input type='number' class='form-control' name='txtCantidad' style='width: 230px;' id='txtCantidad' placeholder=''   data-toggle='tooltip' data-placement='top' title='' >");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('txtCantidad');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='' style='display: flex; justify-content: space-around'>");
            out.print("<div class=''>");
            out.print("<b>Unidades x bolsa</b>");
            out.print("<input type='number' class='form-control' name='unidadesxempaque' style='width: 230px;' id='unidadesxempaque' placeholder='' data-toggle='tooltip' data-placement='top' title='' >");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('unidadesxempaque');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<b>Nombre cliente</b>");
            out.print("<input type='text' class='form-control'  name='Cbx_cliente' id='Txt_filtro_avanzado2' style='width: 230px;' list='Personal2'>");
            out.print("<div id='Buscar_valores2'></div>");
            out.print("<input type='hidden' name='fto' id='Txt_valores_filtro2' oninput='javascript:this.value+=document.getElementById('Buscar_valores2').innerHTML'/>");
            out.print("<datalist id='Personal2' name='Cbx_cliente'><label>");
            lst_clientes = sqlcft.ConsultaClientes();
            if (lst_clientes != null) {
                for (int i = 0; i < lst_clientes.size(); i++) {
                    String[] DataClient = lst_clientes.toString().replace("[", "").replace("]", "").split(",");
                    try {
                        out.print("<option value='" + DataClient[i].split("/")[1] + "'></option>");
                    } catch (Exception e) {
                        out.print("<option value='" + DataClient[i] + "'></option>");
                    }
                }
            } else {
                out.print("<option>Error</option>");
            }
            out.print("</label></datalist>");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('Txt_filtro_avanzado2');val1.add(Validate.Presence);</script>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div style='width: 512px;margin-left: 26px;'>");
            out.print("<b>Maquina</b>");
//            out.print("<input type='text' class='form-control' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' placeholder='Seleccionar maquina' list='Personal'>");
//            out.print("<div id='Buscar_valores'></div>");
//            out.print("<input type='hidden' name='fto' id='Txt_valores_filtro' oninput='javascript:this.value+=document.getElementById('Buscar_valores').innerHTML'/>");
            out.print("<select class='form-control' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado'  placeholder='Seleccionar maquina'>");
            lst_maquinas = jpamqn.ConsultarMaquinasActivas();
            out.print("<option value='0'>Selecccione Maquina</option>");
            if (lst_maquinas != null) {
                for (int i = 0; i < lst_maquinas.size(); i++) {
                    Object[] obj_maquina = (Object[]) lst_maquinas.get(i);
                    out.print("<option value='" + obj_maquina[1] + "/" + obj_maquina[2] + "/" + obj_maquina[0] + "/" + obj_maquina[5] + "'>" + obj_maquina[1] + " - " + obj_maquina[2] + " - " + obj_maquina[4] + " - " + obj_maquina[5] + "</option>");
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_filtro_avanzado');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
            } else {
                out.print("<option value='0'>No existen Maquinas registradas con este producto</option>");
                out.print("</select>");
            }
            out.print("</div>");

            out.print("<div style='text-align: center;'>");
            out.print("<button class='btn btn-primary' style='margin-top: 15px;'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");

            out.print("<div class='' id='contOrder2' style='display: none;'>");
            out.print("<form action='Orden?opc=1' method='post'>");
            out.print("<b>Orden Factory</b>");
            out.print("<input type='text' class='form-control' name='numero_orden' id='numero_orden' placeholder='Consulta orden Factory' autocomplete='off' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('numero_orden');val1.add(Validate.Presence);</script>");
            out.print("<div style='margin-left:70%;margin-top:2%'><button class=\"btn btn-primary\" type='submit' name='' id='' placeholder='' value='Registrar'>Consultar</button></div>");
            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            if (numero_orden > 0) {
                lst_orden_factory = sqlcft.ConsultaVersionFT(numero_orden);
                if (lst_orden_factory != null && lst_orden_factory.size() > 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_orden'>");
                    String[] arg_orden = lst_orden_factory.toString().replace("[", "").replace("]", "").split(" / ");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>" + arg_orden[2].trim() + "</h4>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(3)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Orden?opc=2' method='post'>");
                    out.print("<input type='hidden' class='form-control' name='ft_version' value='" + arg_orden[13] + "/" + arg_orden[14] + "/" + arg_orden[15] + "' >");
                    out.print("<div style='margin-left:1%' class='cont_form_orden'>");
                    out.print("<div style='justify-content:space-evenly;display: flex;margin-left:6%;'>");
                    out.print("<input type='hidden' name='producto' value='" + arg_orden[2].trim() + "'>");
                    out.print("<div class='div_margin'>");
                    out.print("<div>");
                    out.print("<b># Orden</b>");
                    out.print("<br><p>" + arg_orden[0].trim() + "</p>");
                    out.print("<input type='hidden' name='orden' value='" + arg_orden[0].trim() + "'>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Cantidad programada</b>");
                    out.print("<br><p>" + arg_orden[8].trim() + "-" + arg_orden[10].trim() + "</p>");
                    out.print("<input type='hidden' name='cantidad' value='" + arg_orden[8].trim() + "'>");
                    out.print("<input type='hidden' name='unidad' value='" + arg_orden[10].trim() + "'>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Lote</b>");
                    out.print("<br><p>" + (arg_orden[4].trim().equals("") ? "N/A" : arg_orden[4].trim()) + "</p>");
                    out.print("<input type='hidden' name='lote' value='" + (arg_orden[4].trim().equals("") ? "N/A" : arg_orden[4].trim()) + "'>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='div_margin'>");
                    out.print("<div>");
                    out.print("<b>Codigo</b>");
                    out.print("<br><p>" + arg_orden[1].trim() + "</p>");
                    out.print("<input type='hidden' name='codigo' value='" + arg_orden[1].trim() + "'>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Cant. Entregada</b>");
                    out.print("<br><p> 0 -" + arg_orden[10].trim() + "</p>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Fecha Inicio</b>");
                    out.print("<br><p>" + arg_orden[11].trim() + "</p>");
                    out.print("<input type='hidden' name='fechai' value='" + arg_orden[11].trim() + "'>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='div_margin'>");
                    out.print("<div>");
                    out.print("<b>Plan</b>");
                    out.print("<br><p>" + arg_orden[3].trim() + "</p>");
                    out.print("<input type='hidden' name='plan' value='" + arg_orden[3].trim() + "'>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Centro Costo</b>");
                    out.print("<br><p>" + arg_orden[5].trim() + "-" + arg_orden[7].trim() + "</p>");
                    String centro_costo = arg_orden[5].trim() + "-" + arg_orden[7].trim();
                    out.print("<input type='hidden' name='centro' value='" + centro_costo + "'>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Fecha Cierre</b>");
                    out.print("<br><p>" + arg_orden[12].trim() + "</p>");
                    out.print("<input type='hidden' name='fechaf' value='" + arg_orden[12].trim() + "'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='justify-content: space-evenly;display: flex;align-items: end;' >");
                    out.print("<div style='width:35%;'>");
                    out.print("<b>Maquina</b>");
                    lst_maquinas = jpamqn.ConsultarMaquinasXProd(arg_orden[1].toString().trim());
                    out.print("<select class='form-control' name='valor_maquina' id='valor_maquina' placeholder='Seleccionar Maquina'>");
                    if (lst_maquinas != null) {
                        out.print("<option value='0'>Selecccione Maquina</option>");
                        for (int i = 0; i < lst_maquinas.size(); i++) {
                            Object[] obj_maquina = (Object[]) lst_maquinas.get(i);
                            out.print("<option value='" + obj_maquina[0] + "-" + obj_maquina[3] + "'>" + obj_maquina[1] + " - " + obj_maquina[2] + " - " + obj_maquina[3] + "g</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                    } else {
                        out.print("<option value='0'>No existen Maquinas registradas con este producto</option>");
                        out.print("</select>");
                    }
                    out.print("<div>");
                    out.print("</div>");
                    out.print("<div style='width:35%'>");
                    out.print("<b>Unidades x Empaque:</b>");
                    lst_parametros = ParametrosJpa.Consultar_categorias("Cliente");
                    if (lst_parametros != null) {
                        out.print("<select class='form-control' name='unidadesxempaque' id='unidadesxempaque' placeholder='Seleccione la unidades por empaque'>");
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_para = (Object[]) lst_parametros.get(i);
                            out.print("<option value='" + obj_para[3] + "'>" + obj_para[3] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    } else {
                        out.print("<select>");
                        out.print("<option></option>");
                        out.print("</select>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='margin: 2%;margin-left: 85%;'><button class=\"btn btn-primary\" type='submit' name='' id='' placeholder='' value='Registrar'>Registrar</button></div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"¡Alerta!\","
                            + "text:\"No se encontraron Ordenes de producción, con el valor ingresado.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (id_orden > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_orden' style='height:192px'>");
                lst_orden_id = OrdenProduccionJpa.ConsultarOrdenId(id_orden);
                if (lst_orden_id != null) {
                    Object[] obj_orden_modificar = (Object[]) lst_orden_id.get(0);
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Modificar Orden <b>" + obj_orden_modificar[1] + "</b></h4>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(4)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Orden?opc=4' method='post'>");
                    out.print("<input type='hidden' name='id_orden' value='" + id_orden + "'>");
                    out.print("<input type='hidden' name='cantidad' value='" + obj_orden_modificar[6] + "'>");
                    out.print("<div style='justify-content: space-evenly;display: flex;align-items: end;' >");
                    out.print("<div style='width:36%;'>");
                    out.print("<b>Maquina</b>");
                    lst_maquinas = jpamqn.ConsultarMaquinasXProd(obj_orden_modificar[2].toString().trim());
                    out.print("<select class='form-control' name='valor_maquina' id='valor_maquina' placeholder='Seleccionar Maquina'>");
                    if (lst_maquinas != null) {
                        out.print("<option value='" + obj_orden_modificar[16] + "-" + obj_orden_modificar[19] + "'>" + obj_orden_modificar[17] + " - " + obj_orden_modificar[18] + " - " + obj_orden_modificar[19] + "g</option>");
                        for (int i = 0; i < lst_maquinas.size(); i++) {
                            Object[] obj_maquina = (Object[]) lst_maquinas.get(i);
                            out.print("<option value='" + obj_maquina[0] + "-" + obj_maquina[3] + "'>" + obj_maquina[1] + " - " + obj_maquina[2] + " - " + obj_maquina[3] + "g</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                    } else {
                        out.print("<option value='0'>No existen Maquinas registradas con este producto</option>");
                    }
                    out.print("<div>");
                    out.print("</div>");
                    out.print("<div style='width:36%'>");
                    out.print("<b>Unidades x Empaque:</b>");
//                    lst_parametros = ParametrosJpa.Consultar_categorias("Cliente");
//                    out.print("<select class='form-control' name='unidadesxempaque' id='unidadesxempaque' placeholder='Seleccione la unidades por empaque'>");
//                    if (lst_parametros != null) {
//                        out.print("<option value='" + obj_orden_modificar[23] + "'> " + obj_orden_modificar[23] + " </option>");
//                        for (int i = 0; i < lst_parametros.size(); i++) {
//                            Object[] obj_para = (Object[]) lst_parametros.get(i);
//                            out.print("<option value='" + obj_para[3] + "'>" + obj_para[3] + "</option>");
//                        }
//                        out.print("</select>"
//                                + "<script type='text/javascript'>var mySelect = new LiveValidation('unidadesxempaque');"
//                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                    } else {
//                        out.print("<select>");
//                        out.print("<option></option>");
//                        out.print("</select>");
//                    }
                    out.print("<input type='text' class='form-control' name='unidadesxempaque' value='" + obj_orden_modificar[23] + "'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='margin: 2%;margin-left: 85%;'><button class=\"btn btn-primary\" type='submit' name='' id='' placeholder='' value='Modificar'>Modificar</button></div>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"¡Alerta!\","
                            + "text:\"No se encontro orden, consultar con el area de T.I\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
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
            out.print("<h4 class='page-title' style='width: 290px;'> Orden de Produccion</h4>");
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
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                out.print("<a onclick='mostrarConvencion(2)'"
                        + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Agregar una Orden'>Agregar <i class='fas fa-plus'></i></a>");
            }
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
            out.print("<h3 class='box-title'>Tabla Orden</h3>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap table-sm' id='resultados'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class='border-top-0'>Ver</th>");
            out.print("<th class='border-top-0'># OP </th>");
            out.print("<th class='border-top-0'>Maquina</th>");
            out.print("<th class='border-top-0'>Codigo</th>");
            out.print("<th class='border-top-0'>Cant. <br>Programada</th>");
            out.print("<th class='border-top-0'>Cant. <br>Producida</th>");
            out.print("<th class='border-top-0'>Peso Meta OP</th>");
            out.print("<th class='border-top-0'>Unidades <br> x Empaque</th>");
            out.print("<th colspan='2' class='border-top-0'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody >");
            lst_orden = OrdenProduccionJpa.ConsultarOrden();
            if (lst_orden != null) {
                for (int i = 0; i < lst_orden.size(); i++) {
                    Object[] obj_orden = (Object[]) lst_orden.get(i);
                    out.print("<tr>");
                    out.print("<td><a href='Registro?opc=1&id_orden=" + obj_orden[0] + "' class='btn btn-success'><i style='color:#fff;' class=\"fas fa-eye\"></i></i></a></td>");
                    out.print("<td>" + obj_orden[1] + "</td>");
                    out.print("<td>" + obj_orden[3] + "</td>");
                    out.print("<td class='tooltip3'>" + obj_orden[6] + "");
                    out.print("<span class='tooltiptext'>" + obj_orden[7] + "</span>");
                    out.print("</td>");
                    out.print("<td>" + obj_orden[10] + " un</td>");
                    out.print("<td>" + ((obj_orden[11] == null) ? "0" : obj_orden[11]) + " un</td>");
                    out.print("<td><b>" + obj_orden[13] + " g</b></td>");
                    out.print("<td><b>" + obj_orden[20] + " un</b></td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                        out.print("<td><a href='Orden?opc=1&id_orden=" + obj_orden[0] + "' class='btn btn-warning'><i style='color:#fff;' class=\"fas fa-edit\" title='Registro Abierto'></i></i></a></td>");
                    } else {
                        out.print("<td><button type='button' class='btn btn-warning' disabled><i style='color:#fff;' class=\"fas fa-edit\" title='Sin permisos'></i></i></button></td>");
                    }
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                        out.print("<td>" + ((Integer.parseInt(obj_orden[17].toString()) == 1)
                                ? "<a href='Orden?opc=3&id_orden=" + obj_orden[0] + "&estado=" + obj_orden[17] + "' class='btn btn-info'><i style='color:#fff;' class=\"fas fa-lock-open\" title='Registro Abierto'></i></i></a>"
                                : "<a href='Orden?opc=3&id_orden=" + obj_orden[0] + "&estado=" + obj_orden[17] + "' class='btn btn-info'><i style='color:#fff;' class=\"fas fa-lock\" title='Registro Cerrado'></i></a>") + "</td>");
                    } else {
                        out.print("<td>" + ((Integer.parseInt(obj_orden[17].toString()) == 1)
                                ? "<button class='btn btn-info' disabled><i style='color:#fff;' class=\"fas fa-lock-open\" title='Abierto' ></i></i></button>"
                                : "<button class='btn btn-info' disabled><i style='color:#fff;' class=\"fas fa-lock\" title='Cerrado' ></i></button>") + "</td>");
                    }
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='8' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
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
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_Orden.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
