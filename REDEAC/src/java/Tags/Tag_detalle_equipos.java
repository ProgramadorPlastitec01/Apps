package Tags;

import Controladoras.AreaJpaController;
import Controladoras.DetalleEquipoJpaController;
import Controladoras.EquipoJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_detalle_equipos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        DetalleEquipoJpaController jpa_detalle = new DetalleEquipoJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(cal.getTime());
        String fecha = format1.format(cal.getTime());
        List lst_detalleE = null;
        List lst_detalleEM = null;
        List lst_equipo = null;
        List lst_contred = null;
        List lst_contTipo = null;
        List lst_contEst = null;
        List lst_contAnt = null;
        List lst_contGar = null;
        List lst_contEstDet = null;
        int id_detalle = 0, id_detalleEC = 0, id_detalleEM = 0;
        String filtro = "", query = "";
        try {
            filtro = pageContext.getRequest().getAttribute("filtro").toString();
        } catch (Exception e) {
            filtro = "";
        }
        try {
            query = pageContext.getRequest().getAttribute("query").toString();
        } catch (Exception e) {
            query = "";
        }
        try {
            id_detalle = Integer.parseInt(pageContext.getRequest().getAttribute("id_detalle").toString());
        } catch (Exception e) {
            id_detalle = 0;
        }
        try {
            id_detalleEC = Integer.parseInt(pageContext.getRequest().getAttribute("id_detalle").toString());
        } catch (Exception e) {
            id_detalleEC = 0;
        }
        try {
            id_detalleEM = Integer.parseInt(pageContext.getRequest().getAttribute("id_detalle").toString());
        } catch (Exception e) {
            id_detalleEM = 0;
        }
        lst_equipo = jpa_equipo.consultaEquipos();
        try {
            if (!query.equals("")) {
                lst_detalleE = jpa_detalle.ConsultaQuery(query);
            } else if (filtro.equals("")) {
                lst_detalleE = jpa_detalle.consultaDetalleEquipo();
            } else {
                lst_detalleE = jpa_detalle.consultaDetalleEquipoFiltro(filtro);
            }
            out.print("<div style='float:right;'>");
            out.print("<input type='text' name='txt_bus' id='Txt_filtro' class='form-control'  onkeyup='FiltrarDetalleEquipo()'  placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'>");
            out.print("</div>");
            out.print("<h3>Detalle Equipo</h3><br>");
//            out.print("<a href='#' data-toggle=\"modal\" data-target=\"#Registrar\"><i class='fa fa-plus fa-lg' style='color:#292929'></i></a>");
            out.print("<a href='#' ><i class='fa fa-plus fa-lg' style='color:#c4c4c4 !important; cursor: no-drop;'></i></a>");
            //            //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA ESPECIFICO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana16' style='opacity: 1.03; display:none;'>");
            out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:535px; height:600px; overflow:scroll; position: absolute;top:0%; left:34%;text-align:left '>");
            out.print("<a href='Detalle_equipo?opc=1&txt_bus=' class='close'>&times;</a>");
            out.print("<b class='title'>Filtro de busqueda</b><hr>");
            out.print("<form action='Detalle_equipo?opc=4&txt_bus=&idDT=0' name='formFiltro' method='post'>");
            out.print("<input type='hidden' name='Txt_filtro_campos' id='Txt_filtro_campos' value='[nombre]'><br/>");
            out.print("<table style='width:95%;font-size:12px'>");
            out.print("<tr>");
            out.print("<td style='width: 50%;'>");
            out.print("<center><select name='txt_testado' id='testado-id' required>");
            out.print("<option value='' style='display:none'>Tipo estado</option>");
            out.print("<option value='ALL'>TODOS</option>");
            out.print("<option value='SERVIDORES'>SERVIDORES</option>");
            out.print("<option value='EQUIPOS DE MESA'>EQUIPOS DE MESA</option>");
            out.print("<option value='EQUIPOS PLANTA 1'>EQUIPOS PLANTA 1</option>");
            out.print("<option value='EQUIPOS EN STOCK'>EQUIPOS EN STOCK</option>");
            out.print("<option value='ETIQUETAS'>ETIQUETAS</option>");
            out.print("<option value='PORTERIAS'>PORTERIAS</option>");
            out.print("</select>");
            out.print("</td>");
            out.print("<td rowspan='5' style='width: 50%;'>");
            out.print("<input type='checkbox' id='Ckb_campo1' onclick='SeleccionCampos(Ckb_campo1)' value='[nombre]' checked='true' > PC<br>");
            out.print("<input type='checkbox' id='Ckb_campo2' onclick='SeleccionCampos(Ckb_campo2)' value='[red]'> RED<br>");
            out.print("<input type='checkbox' id='Ckb_campo3' onclick='SeleccionCampos(Ckb_campo3)' value='[mac]'> MAC<br>");
            out.print("<input type='checkbox' id='Ckb_campo4' onclick='SeleccionCampos(Ckb_campo4)' value='[punto_red]'> PUNTO RED<br>");
            out.print("<input type='checkbox' id='Ckb_campo5' onclick='SeleccionCampos(Ckb_campo5)' value='[nombre_equipo]'> NOMBRE USUARIO<br>");
            out.print("<input type='checkbox' id='Ckb_campo6' onclick='SeleccionCampos(Ckb_campo6)' value='[login_plastitec]'> LOGIN PLASTITEC<br>");
            out.print("<input type='checkbox' id='Ckb_campo7' onclick='SeleccionCampos(Ckb_campo7)' value='[login_novell]'> LOGIN NOVELL<br>");
            out.print("<input type='checkbox' id='Ckb_campo8' onclick='SeleccionCampos(Ckb_campo8)' value='[login_solin]'> LOGIN SOLIN<br>");
            out.print("<input type='checkbox' id='Ckb_campo9' onclick='SeleccionCampos(Ckb_campo9)' value='[pwd_solin]'> PWD SOLIN<br>");
            out.print("<input type='checkbox' id='Ckb_campo10' onclick='SeleccionCampos(Ckb_campo10)' value='[login_plastitecsa]'> LOGIN PLASTITEC SA<br>");
            out.print("<input type='checkbox' id='Ckb_campo11' onclick='SeleccionCampos(Ckb_campo11)' value='[dominio]'> DOMINIO<br>");
            out.print("<input type='checkbox' id='Ckb_campo12' onclick='SeleccionCampos(Ckb_campo12)' value='[ip_anterior]'> IP ANTERIOR<br>");
            out.print("<input type='checkbox' id='Ckb_campo13' onclick='SeleccionCampos(Ckb_campo13)' value='[ip_nueva]'> IP NUEVA<br>");
            out.print("<input type='checkbox' id='Ckb_campo14' onclick='SeleccionCampos(Ckb_campo14)' value='[mascara]'> MASCARA<br>");
            out.print("<input type='checkbox' id='Ckb_campo15' onclick='SeleccionCampos(Ckb_campo15)' value='[puerta_enlance]'> PUERTA ENLACE<br>");
            out.print("<input type='checkbox' id='Ckb_campo16' onclick='SeleccionCampos(Ckb_campo16)' value='[vlan]'> VLAN<br>");
            out.print("<input type='checkbox' id='Ckb_campo17' onclick='SeleccionCampos(Ckb_campo17)' value='[win_version]'> WIN INSTALADO VERSIÓN<br>");
            out.print("<input type='checkbox' id='Ckb_campo18' onclick='SeleccionCampos(Ckb_campo18)' value='[win_tipo]'> WIN INSTALADO TIPO<br>");
            out.print("<input type='checkbox' id='Ckb_campo19' onclick='SeleccionCampos(Ckb_campo19)' value='[win_factura_version]'> WIN FACTURA VERSION<br>");
            out.print("<input type='checkbox' id='Ckb_campo20' onclick='SeleccionCampos(Ckb_campo20)' value='[win_factura_tipo]'> WIN FACTURA TIPO<br>");
            out.print("<input type='checkbox' id='Ckb_campo21' onclick='SeleccionCampos(Ckb_campo21)' value='[office_version]'> OFFICE INSTALADO VERSION<br>");
            out.print("<input type='checkbox' id='Ckb_campo22' onclick='SeleccionCampos(Ckb_campo22)' value='[office_tipo]'> OFFICE INSTALADO TIPO<br>");
            out.print("<input type='checkbox' id='Ckb_campo23' onclick='SeleccionCampos(Ckb_campo23)' value='[office_factura_version]'> OFFICE FACTURA VERSION<br>");
            out.print("<input type='checkbox' id='Ckb_campo24' onclick='SeleccionCampos(Ckb_campo24)' value='[office_factura_tipo]'> OFFICE FACTURA TIPO<br>");
            out.print("<input type='checkbox' id='Ckb_campo25' onclick='SeleccionCampos(Ckb_campo25)' value='[factura]'> FACTURA <br>");
            out.print("<input type='checkbox' id='Ckb_campo26' onclick='SeleccionCampos(Ckb_campo26)' value='[fecha_factura]'> FECHA FACTURA<br>");
            out.print("<input type='checkbox' id='Ckb_campo27' onclick='SeleccionCampos(Ckb_campo27)' value='[licencia]'> LICENCIAS<br>");
            out.print("<input type='checkbox' id='Ckb_campo28' onclick='SeleccionCampos(Ckb_campo28)' value='[fecha_garan]'> F. FIN GARAN<br>");
            out.print("<input type='checkbox' id='Ckb_campo29' onclick='SeleccionCampos(Ckb_campo29)' value='[garantia]'> GARANTIA<br>");
            out.print("<input type='checkbox' id='Ckb_campo30' onclick='SeleccionCampos(Ckb_campo30)' value='[proveedor]'> PROVEEDOR<br>");
            out.print("<input type='checkbox' id='Ckb_campo31' onclick='SeleccionCampos(Ckb_campo31)' value='[estado]'> ACTIVOS-SOPORTE-DAÑADO<br>");
            out.print("<input type='checkbox' id='Ckb_campo32' onclick='SeleccionCampos(Ckb_campo32)' value='[antivirus]'> ANTIVIRUS<br>");
            out.print("<input type='checkbox' id='Ckb_campo33' onclick='SeleccionCampos(Ckb_campo33)' value='[internet]'> INTERNET<br>");
            out.print("<input type='checkbox' id='Ckb_campo34' onclick='SeleccionCampos(Ckb_campo34)' value='[descripcion]'> DESCRIPCION<br>");
            out.print("<input type='checkbox' id='Ckb_campo35' onclick='SeleccionCampos(Ckb_campo35)' value='[stiker_win]'> STIKER WIN<br>");
            out.print("<input type='checkbox' id='Ckb_campo36' onclick='SeleccionCampos(Ckb_campo36)' value='[stiker_office]'> STIKER OFFICE<br>");
            out.print("<input type='checkbox' id='Ckb_campo37' onclick='SeleccionCampos(Ckb_campo37)' value='[serial_windows]'> SERIAL WINDOWS<br>");
            out.print("<input type='checkbox' id='Ckb_campo38' onclick='SeleccionCampos(Ckb_campo38)' value='[serial_office]'> SERIAL OFFICE<br>");
            out.print("<input type='checkbox' id='Ckb_campo39' onclick='SeleccionCampos(Ckb_campo39)' value='[software_antivirus]'> SOFTWARE ANTIVIRUS<br>");
            out.print("<input type='checkbox' id='Ckb_campo40' onclick='SeleccionCampos(Ckb_campo40)' value='[software_internet]'> SOFTWARE INTERNET<br>");
            out.print("<input type='checkbox' id='Ckb_campo41' onclick='SeleccionCampos(Ckb_campo41)' value='[software_adobe]'> SOFTWARE ADOBE ACROBAT<br>");
            out.print("<input type='checkbox' id='Ckb_campo42' onclick='SeleccionCampos(Ckb_campo42)' value='[software_pausas]'> SOFTWARE PAUSAS ACTIVAS<br>");
            out.print("<input type='checkbox' id='Ckb_campo43' onclick='SeleccionCampos(Ckb_campo43)' value='[software_flash]'> SOFTWARE ADOBE FLASH<br>");
            out.print("<input type='checkbox' id='Ckb_campo44' onclick='SeleccionCampos(Ckb_campo44)' value='[software_suit]'> SOFTWARE ADOBE SUITE SOLIN<br>");
            out.print("<input type='checkbox' id='Ckb_campo45' onclick='SeleccionCampos(Ckb_campo45)' value='[tipo_software]'> SOFTWARE NUMERO 9<br>");
            out.print("<input type='checkbox' id='Ckb_campo46' onclick='SeleccionCampos(Ckb_campo46)' value='[correo]'> CORREO <br>");
            out.print("<input type='checkbox' id='Ckb_campo47' onclick='SeleccionCampos(Ckb_campo47)' value='[responsable]'> RESPONSABLE <br>");
            out.print("</td>");
            out.print("</center>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<center><td>"
                    + "Despues de escribir una palabra se debe agregar el (<b class='rojo'>+</b>).<br> y para quitar la palabra se da click encima encima de la palabra."
                    + "<br><input type='text' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado'  class=\"form-group\" autocomplete='off' onkeypress='FiltroAvanzado(event);' placeholder='Buscar'/>"
                    + "<br /><b>Valores a filtrar</b><div id='Buscar_valores'></div>"
                    + "<input type='text' name='fto'  id='Txt_valores_filtro' oninput=\"javascript:this.value+=document.getElementById('Buscar_valores').innerHTML\"/>");
//            if (filtro.length() > 0) {
//                out.print("<br /><b>Anteriores Filtrado</b><br />" + filtro.toUpperCase().replace("+", "<br />") + "");
//            }
            out.print("</center></td>");
            out.print("</tr>");
            out.print("</div>");
            out.print("</div>");
            out.print("</table><br><br>");
            out.print("<div style='float:right;'>");
            out.print("<input type='submit' value='Filtrar'>");
            out.print("</div>");
            out.print("</form>");
            out.print("</fieldset>");
            out.print("</div>");
//            //</editor-fold>
            if (id_detalle == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRO DETALLE EQUIPO">
                out.print("<div class='modal fade' id='Registrar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog modal-lg-r'>");
                out.print("<div class='modal-content'>");
                out.print("<form action='Detalle_equipo?opc=2' name='formA' method='post'>");
                out.print("<div class='modal-header'>");
                out.print("<a href='Detalle_equipo?opc=1&txt_bus=&query=' class='close'>&times;</a>");
                out.print("<h4 class='modal-title'>Registrar</h4>");
                out.print("</div>");
                out.print("<div class='modal-bodyDER' align='center'>");
                out.print("<table style='font-size:12px;width:100%'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div class='form-group'>");
                out.print("<b>Equipo: </b><br>");
                out.print("<select data-live-search=\"true\" class=\"form-control\" name='slc_equipo'  required>");
                for (int i = 0; i < lst_equipo.size(); i++) {
                    Object[] obj_equipo = (Object[]) lst_equipo.get(i);
                    out.println("<option value=" + obj_equipo[0] + ">" + obj_equipo[1] + "</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td><b>Nombre equipo: </b><br>");
                out.print("<input type='text' name='txt_nombre_eq' class='form-control' id='nombre-id' placeholder='Nombre de equipo' onchange='javascript:this.value=this.value.toUpperCase();' required autocomplete='off'><br/>");
                out.print("</td>");
                out.print("<td><b>Login Plastitec: </b><br>");
                out.print("<input type='text' name='txt_loginp' class='form-control' id='loginp-id' placeholder='Login Plastitec' onchange='javascript:this.value=this.value.toUpperCase();' required autocomplete='off'><br/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td><b>Mac: </b><br>");
                out.print("<input type='text' name='txt_mac' class='form-control' id='mac-id' placeholder='Mac' onchange='javascript:this.value=this.value.toUpperCase();' required autocomplete='off'><br/>");
                out.print("</td>");
                out.print("<td><div class='form-group'>");
                out.print("<b>Antivirus: </b><br>");
                out.print("<select class=\"form-control\" name='txt_antivirus' id='antivirus-id' required>");
                out.print("<option value='' style='display:none'>Aplica antivirus</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td><b>Win version: </b><br>");
                out.print("<input type='text' name='txt_wversion' class='form-control' id='wversion-id' placeholder='Win version' onchange='javascript:this.value=this.value.toUpperCase();' required autocomplete='off'><br/>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<td><b>Office versión: </b><br>");
                out.print("<input type='text' name='txt_officeV' class='form-control' id='officeV-id' placeholder='Office versión' onchange='javascript:this.value=this.value.toUpperCase();' required autocomplete='off'><br/>");
                out.print("</td>");
                out.print("<td><div class='form-group'>");
                out.print("<b>Tipo Estado: </b><br>");
                out.print("<select data-live-search=\"true\" class=\"form-control\" name='txt_testado' id='' required>");
                out.print("<option value='' style='display:none'>Tipo estado</option>");
                out.print("<option value='DE BAJA'>DE BAJA</option>");
                out.print("<option value='SERVIDORES'>SERVIDORES</option>");
                out.print("<option value='PORTATIL'>PORTATIL</option>");
                out.print("<option value='EQUIPOS DE MESA'>EQUIPOS DE MESA</option>");
                out.print("<option value='EQUIPOS PLANTA 1'>EQUIPOS PLANTA 1</option>");
                out.print("<option value='EQUIPOS EN STOCK'>EQUIPOS EN STOCK</option>");
                out.print("<option value='ETIQUETAS'>ETIQUETAS</option>");
                out.print("<option value='PORTERIAS'>PORTERIAS</option>");
                out.print("</select>");
                out.print("</div></td>");
                out.print("<td><div class='form-group'><b>Activos Soporte: </b><br>");
                out.print("<select class=\"form-control\" name='txt_activos_soporte' id='activos_soporte_id' required>");
                out.print("<option value='' style='display:none'>Activos Soporte</option>");
                out.print("<option value='ACTIVO'>ACTIVO</option>");
                out.print("<option value='SOPORTE'>SOPORTE</option>");
                out.print("<option value='DAÑADO'>DAÑADO</option>");
                out.print("</select>");
                out.print("</div></td>");
                out.print("</tr>");

                out.print("</table>");
                out.print("</div>");
                out.print("<div class='modal-footer'>");
                out.print("<input type='submit' value='Registrar'>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                ////</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR DETALLE EQUIPO">
                lst_detalleEM = jpa_detalle.consultaModificarDetalleEquipo(id_detalle);
                Object[] obj_detalleEM = (Object[]) lst_detalleEM.get(0);
                out.print("<div class='modal fade' id='Modificar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog modal-lg-r'>");
                out.print("<div class='modal-content' style='overflow-y: scroll;\n"
                        + "    max-height: 100%;\n"
                        + "    height: auto%;\n"
                        + "'>");
                out.print("<form action='Detalle_equipo?opc=3' name='formA' method='post'>");
                out.print("<div class='modal-header'>");
                out.print("<a href='Detalle_equipo?opc=1&txt_bus=&query=' class='close'>&times;</a>");
                out.print("<h4 class='modal-title'>Modificar  - " + obj_detalleEM[2] + "</h4>");
                out.print("</div>");
                out.print("<input type='hidden' name='idDT' value='" + obj_detalleEM[0] + "'>");
                out.print("<div class='modal-body'  align='center'>");
//                out.print("<div class='modal-body' style='overflow:auto; max-height:100% ' align='center'>");
                out.print("<div style='display:flex; text-align:left; padding: 9px 1px 18px' >");
                //<editor-fold defaultstate="collapsed" desc="OPCIONES CHECKBOX">
                out.print("<div style='width:25%' >");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(1);' > Nombre Equipo</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(2);' > Tipo de equipo</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(3);' > Login Plastitec</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(4);' > Ip</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(5);' > Mac</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(6);' > Garantia</div>");
                out.print("</div>");
                out.print("<div style='width:25%' >");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(7);' > Antivirus</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(8);' > Internet</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(9);' > Win. Instalado  </div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(10);' > Office. Instalado</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(11);' > Vlan</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(12);' > VPN </div>");
                out.print("</div>");
                out.print("<div style='width:25%' >");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(13);' > Skye </div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(14);' > Gmail</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(15);' > Correo Interno </div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(16);' > Correo Externo </div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(17);' > Factura</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(18);' > Fecha Factura</div>");
                out.print("</div>");
                out.print("<div style='width:25%' >");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(19);' > Licencia  </div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(20);' > Fecha Garantia</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(21);' > Proveedor</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(22);' > Activos Soporte</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(23);' > Tipo Software</div>");
                out.print("<div><input type='checkbox' name='' id='' onclick='MostrarCampo(24);' > Red</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div id='modal'  style=' margin-left:4%;display:none;'>");
                //<editor-fold defaultstate="collapsed" desc="SECCION 1">
                out.print("<div style='display:flex; ' >");
                out.print("<div id='Txt_nombre_equipo' style='display:none;' class='div_mod' ><b style='text-align:left;'>Nombre Equipo </b><br/><input type='text' class='form-control' class='form-control' name='Txt_nombre_equipo' value='" + obj_detalleEM[28] + "'></div>");
                out.print("<div class=\"form-group\" id='Txt_tipo_equipo' style='display:none;' class='div_mod'><b style='text-align:left;'>Tipo Equipo </b><br/>");
                out.print("<select  class='form-control_select'  id='exampleFormControlSelect1' name='Txt_tipo_estado'  required>");
                out.print("<option value='" + obj_detalleEM[25] + "'>" + obj_detalleEM[25] + "</option>");
                out.print("<option value='DE BAJA'>DE BAJA</option>");
                out.print("<option value='SERVIDORES'>SERVIDORES</option>");
                out.print("<option value='PORTATIL'>PORTATIL</option>");
                out.print("<option value='EQUIPOS DE MESA'>EQUIPOS DE MESA</option>");
                out.print("<option value='EQUIPOS PLANTA 1'>EQUIPOS PLANTA 1</option>");
                out.print("<option value='EQUIPOS EN STOCK'>EQUIPOS EN STOCK</option>");
                out.print("<option value='ETIQUETAS'>ETIQUETAS</option>");
                out.print("<option value='PORTERIAS'>PORTERIAS</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("<div id='Txt_login_plastitec' style='display:none;' class='div_mod'><b style='text-align:left;'>Login Plastitec </b><br/><input type='text' class='form-control' name='Txt_login_plastitec'  value='" + obj_detalleEM[3] + "' ></div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 2">
                out.print("<div style='display:flex; ' >");
                out.print("<div id='Txt_ip' style='display:none; padding: 0px;' class='div_mod' ><b style='text-align:left;'>Ip</b><br/><input type='text' class='form-control' name='Txt_ip'  value='" + obj_detalleEM[4] + "' ></div>");
                out.print("<div id='Txt_mac' style='display:none;' class='div_mod'><b style='text-align:left;'>Mac</b><br/><input type='text' class='form-control' name='Txt_mac'  value='" + obj_detalleEM[5] + "' ></div>");
                out.print("<div class=\"form-group\" id='Txt_garantia' style='display:none;' class='div_mod'><b style='text-align:left;'>Garantia</b><br/>");
                out.print("<select class='form-control'  id='exampleFormControlSelect2' name='Txt_garantia' required>");
                out.print("<option value='" + obj_detalleEM[22] + "'>" + obj_detalleEM[22] + "</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 3">
                out.print("<div style='display:flex; ' >");
                out.print("<div class=\"form-group\" id='Txt_antivirus' style='display:none; padding: 0px 11px 0px 5px;' class='div_mod'><b style='text-align:left;'>Antivirus</b><br/>");
                out.print("<select class='form-control'   id='exampleFormControlSelect2' name='Txt_antivirus' required>");
                out.print("<option value='" + obj_detalleEM[10] + "'>" + obj_detalleEM[10] + "</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("<div class=\"form-group\" id='Txt_internet' style='display:none;' class='div_mod'><b style='text-align:left;'>Internet</b><br/>");
                out.print("<select class='form-control_select'  id='exampleFormControlSelect2' name='Txt_internet' required>");
                out.print("<option value='" + obj_detalleEM[11] + "'>" + obj_detalleEM[11] + "</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("<div id='Txt_win_instalado' style='display:none;' class='div_mod'><b style='text-align:left;'>Win Instalado</b><br/><input type='text' class='form-control' name='Txt_win_instalado'  value='" + obj_detalleEM[8] + "' ></div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 4">
                out.print("<div style='display:flex; ' >");
                out.print("<div id='Txt_office_instalado' style='display:none;' class='div_mod'><b style='text-align:left;'>Office Instalado</b><br/><input type='text' class='form-control' name='Txt_office_instalado'  value='" + obj_detalleEM[9] + "' ></div>");
                out.print("<div id='Txt_vlan' style='display:none;' class='div_mod'><b style='text-align:left;'>Vlan</b><br/><input type='text' class='form-control' name='Txt_vlan' value='" + obj_detalleEM[7] + "' ></div>");
                out.print("<div id='Txt_vpn' style='display:none;' class='div_mod'><b style='text-align:left;'>Vpn</b><br/><input type='text' class='form-control' name='Txt_vpn'  value='" + obj_detalleEM[12] + "' ></div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 5">
                out.print("<div style='display:flex;'>");
                out.print("<div id='Txt_skye' style='display:none;' class='div_mod'><b style='text-align:left;'>Skye</b><br/><input type='text' class='form-control' name='Txt_skye'  value='" + obj_detalleEM[13] + "' ></div>");
                out.print("<div id='Txt_gmail' style='display:none;' class='div_mod'><b style='text-align:left;'>Gmail</b><br/><input type='text' class='form-control' name='Txt_gmail'  value='" + obj_detalleEM[14] + "' ></div>");
                out.print("<div id='Txt_correo_interno' style='display:none;' class='div_mod'><b style='text-align:left;'>Correo Interno</b><br/><input type='text' class='form-control' name='Txt_correo_interno'  value='" + obj_detalleEM[15] + "' ></div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 6">
                out.print("<div style='display:flex; ' >");
                out.print("<div id='Txt_correo_externo' style='display:none;' class='div_mod'><b style='text-align:left;'>Correo Externo</b><br/><input type='text' class='form-control' name='Txt_correo_externo'  value='" + obj_detalleEM[16] + "' ></div>");
                out.print("<div id='Txt_factura' style='display:none;' class='div_mod'><b style='text-align:left;'>Factura</b><br/><input type='text' class='form-control' name='Txt_factura'  value='" + obj_detalleEM[17] + "' ></div>");
                out.print("<div id='Txt_fecha_factura' style='display:none;' class='div_mod'><b style='text-align:left;'>Fecha Factura</b><br/><input type='text' class='form-control' id='datepicker' name='Txt_fecha_factura'  value='" + ((obj_detalleEM[18] == null) ? fecha : obj_detalleEM[18].toString()) + "' required></div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 7">
                out.print("<div style='display:flex; ' >");
                out.print("<div class=\"form-group\" id='Txt_lincecia' style='display:none; padding: 0px 3px 0px 5px' class='div_mod'><b style='text-align:left;'>Lincecia</b><br/>");
                out.print("<select class='form-control_select'  id='exampleFormControlSelect2' name='Txt_lincecia' required>");
                out.print("<option value='" + obj_detalleEM[19] + "'>" + obj_detalleEM[19] + "</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("<div id='Txt_fecha_garantia' style='display:none;' class='div_mod'><b style='text-align:left;'>Fecha Garantia</b><br/><input type='text' class='form-control' id='datepicker2' name='Txt_fecha_garantia'  value='" + ((obj_detalleEM[20] == null) ? fecha : obj_detalleEM[20].toString()) + "' ></div>");
                out.print("<div id='Txt_proveedor' style='display:none;' class='div_mod'><b style='text-align:left;'>Proveedor</b><br/><input type='text' class='form-control' name='Txt_proveedor'  value='" + obj_detalleEM[21] + "' ></div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SECCION 8">
                out.print("<div style='display:flex; ' >");
                out.print("<div class=\"form-group\" id='Txt_activos_soporte' style='display:none; padding: 0px 11px 0px 5px;' class='div_mod'><b style='text-align:left;'>Activos Soporte</b><br/>");
                out.print("<select class='form-control_select'  id='exampleFormControlSelect2' name='Txt_activos_soporte' required>");
                out.print("<option value='" + obj_detalleEM[23] + "'>" + obj_detalleEM[23] + "</option>");
                out.print("<option value='ACTIVOS'>ACTIVO</option>");
                out.print("<option value='SOPORTE'>SOPORTE</option>");
                out.print("<option value='DAÑADO'>DAÑADO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("<div class=\"form-group\" id='Txt_tipo_sofware' style='display:none; padding: 0px 11px 0px 5px;' class='div_mod'><b style='text-align:left;'>Tipo Software</b><br/>");
                out.print("<select class='form-control_select'  id='exampleFormControlSelect2' name='Txt_tipo_sofware' required>");
                out.print("<option value='" + obj_detalleEM[24] + "'>" + obj_detalleEM[24] + "</option>");
                out.print("<option value='PC'>PC</option>");
                out.print("<option value='PORTATIL'>PORTATIL</option>");
                out.print("<option value='SERVIDOR'>SERVIDOR</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("<div class=\"form-group\" id='Txt_red' style='display:none;' class='div_mod'><b style='text-align:left;'>Red</b><br/>");
                out.print("<select class='form-control_select'  id='exampleFormControlSelect2' name='Txt_red' id='red_id' required>");
                out.print("<option value='" + obj_detalleEM[6] + "'>" + obj_detalleEM[6] + "</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div class='modal-footer'>");
                out.print("<input type='submit' value='Modificar'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>");
                out.print("$(\"#Modificar\").modal(\"show\");");
                out.print("</script>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="TABLA OCULTA - EXEL ">
            out.print("<div id='testTable' style='display:none;'>");
            out.print("<table class='table-deq'>");
            out.print("<tr>");
            out.print("<th>PC</th>");
            out.print("<th>NOMBRE EQUIPO</th>");
            out.print("<th>TIPO ESTADO</th>");
            out.print("<th>LOGIN PLASTITEC</th>");
            out.print("<th>IP</th>");
            out.print("<th>MAC</th>");
            out.print("<th>GARANTIA</th>");
            out.print("<th>ANTIVIRUS</th>");
            out.print("<th>INTERNET</th>");
            out.print("<th>WIN INSTALADO</th>");
            out.print("<th>OFFICE INSTALADO</th>");
            out.print("<th>VLAN</th>");
            out.print("<th>VPN</th>");
            out.print("<th>SKYE</th>");
            out.print("<th>GMAIL</th>");
            out.print("<th>CORREO INTERNO</th>");
            out.print("<th>CORREO EXTERNO</th>");
            out.print("<th>FACTURA</th>");
            out.print("<th>FECHA FACTURA</th>");
            out.print("<th>LICENCIA</th>");
            out.print("<th>FECHA GARANTIA</th>");
            out.print("<th>PROVEEDOR</th>");
            out.print("<th>ACTIVOS SOPORTE</th>");
            out.print("<th>TIPO SOFTWARE</th>");
            out.print("</tr>");
            //<editor-fold defaultstate="collapsed" desc="LISTADO EQUIPOS">
            for (int i = 0; i < lst_detalleE.size(); i++) {
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                Object[] obj_detalle = (Object[]) lst_detalleE.get(i);
                out.print("<tbody>");
                out.print("<tr align='center'>");
                out.print("<td><a href='Detalle_equipo?opc=1&txt_bus=&idDT=" + obj_detalle[0] + "'><b class='title'>" + obj_detalle[2] + "</b></a></td>");
                out.print("<td>" + obj_detalle[28] + "</td>");
                out.print("<td>" + obj_detalle[25] + "</td>");
                out.print("<td>" + obj_detalle[3] + "</td>");
                out.print("<td>" + obj_detalle[4] + "</td>");
                out.print("<td>" + obj_detalle[5] + "</td>");
                out.print("<td>" + obj_detalle[22] + "</td>");
                out.print("<td>" + obj_detalle[10] + "</td>");
                out.print("<td>" + obj_detalle[11] + "</td>");
                out.print("<td>" + obj_detalle[8] + "</td>");
                out.print("<td>" + obj_detalle[9] + "</td>");
                out.print("<td>" + obj_detalle[7] + "</td>");
                out.print("<td>" + obj_detalle[12] + "</td>");
                out.print("<td>" + obj_detalle[13] + "</td>");
                out.print("<td>" + obj_detalle[14] + "</td>");
                out.print("<td>" + obj_detalle[15] + "</td>");
                out.print("<td>" + obj_detalle[16] + "</td>");
                out.print("<td>" + obj_detalle[17] + "</td>");
                out.print("<td>" + obj_detalle[18] + "</td>");
                out.print("<td>" + obj_detalle[19] + "</td>");
                out.print("<td>" + obj_detalle[20] + "</td>");
                out.print("<td>" + obj_detalle[21] + "</td>");
                out.print("<td>" + obj_detalle[23] + "</td>");
                out.print("<td>" + obj_detalle[24] + "</td>");
                out.print("</tbody>");
                out.print("</tr>");
                //</editor-fold>
            }
//                //</editor-fold>
            out.print("</table>");
            out.print("</div>");
            //</editor-fold>
            //                //<editor-fold defaultstate="collapsed" desc="CONVECION DE CONTADORES">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana15' style='opacity: 1.03; display:none;'>");
            out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='overflow-x: auto;max-height:79%;width:908px; height:auto; position: absolute;top:6%; left:21%;text-align:left '>");
            out.print("<a href='Detalle_equipo?opc=1&txt_bus=' class='close'>&times;</a>");
            out.print("<b>CONTADORES</b><hr>");
            //                //<editor-fold defaultstate="collapsed" desc="ESTADO EQUIPOS">
            out.print("<div style='float:left'>");
            lst_contEst = jpa_detalle.ContadorEstadoEquipo();
            out.print("<b class='title'>Estado</b><br>");
            out.print("<table class='table' style='width:285px'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>ESTADO</th>");
            out.print("<th>EQUIPO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_contEst.size(); i++) {
                Object[] obj_contE = (Object[]) lst_contEst.get(i);
                out.print("<tr>");
                out.print("<td>" + obj_contE[0] + "</td>");
                out.print("<td>");
                if (obj_contE[1].equals("B")) {
                    out.print("<b class='verde'>BUENO</b>");
                } else if (obj_contE[1].equals("R")) {
                    out.print("<b class='naranja'>REVISADO</b>");
                } else {
                    out.print("<b class='rojo'>DAÑADO</b>");
                }
                obj_contE[2] = obj_contE[2].toString().replace("[", "").replace("]", "");
                out.print("<td>"
                        + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + obj_contE[2] + "</span></b>"
                        + "</td>");
                out.print("</tr>");
            }
            out.print("</table></div>");
//                //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="TIPO EQUIPO">
            out.print("<div style='float:left;'>");
            lst_contTipo = jpa_detalle.ContadorTipoEquipo();
            out.print("<b class='title'>Tipo Equipo</b>");
            out.print("<table class='table' style='width:285px'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>&nbsp;&nbsp;TIPO&nbsp;EQUIPO&nbsp;&nbsp;</th>");
            out.print("<th>EQUIPO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_contTipo.size(); i++) {
                Object[] obj_contR = (Object[]) lst_contTipo.get(i);
                out.print("<tr>");
                out.print("<td>" + obj_contR[0] + "</td>");
                out.print("<td>" + (obj_contR[1] == null ? "N/A" : obj_contR[1]) + "</td>");

                if (obj_contR[2] != null) {
                    obj_contR[2] = obj_contR[2].toString().replace("[", "").replace("]", "");
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + obj_contR[2] + "</span></b>"
                            + "</td>");
                } else {
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>N/A</span></b>"
                            + "</td>");
                }
                out.print("</tr>");
            }
            out.print("</table></div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="RED">
            out.print("<div style='float:left'>");
            lst_contred = jpa_detalle.ContadorRed();
            out.print("<b class='title'>Red</b><br>");
            out.print("<table class='table' style='width:285px'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>RED</th>");
            out.print("<th>EQUIPO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_contred.size(); i++) {
                Object[] obj_contR = (Object[]) lst_contred.get(i);
                out.print("<tr>");
                out.print("<td>" + obj_contR[0] + "</td>");
                out.print("<td>" + obj_contR[1] + "</td>");
                obj_contR[2] = obj_contR[2].toString().replace("[", "").replace("]", "");
                out.print("<td>"
                        + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + obj_contR[2] + "</span></b>"
                        + "</td>");
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("</div>");
//                //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ANTIVIRUS">
            out.print("<div style='float:right; position: relative; top:3%;'>");
            lst_contAnt = jpa_detalle.ContadorAntivirus();
            out.print("<br><br><b class='title'>Antivirus</b><br>");
            out.print("<table class='table' style='width:285px'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>ANTIVIRUS</th>");
            out.print("<th>EQUIPO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_contAnt.size(); i++) {
                Object[] obj_contR = (Object[]) lst_contAnt.get(i);
                out.print("<tr>");
                out.print("<td>" + obj_contR[0] + "</td>");
                out.print("<td>" + ((obj_contR[1] == null) ? "N/A" : obj_contR[1]) + "</td>");
                if (obj_contR[2] != null) {
                    obj_contR[2] = obj_contR[2].toString().replace("[", "").replace("]", "");
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + obj_contR[2] + "</span></b>"
                            + "</td>");
                } else {
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>N/A</span></b>"
                            + "</td>");
                }
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("</div>");
//                //</editor-fold>
            //                //<editor-fold defaultstate="collapsed" desc="GARANTIA">
            out.print("<div style='float:right;'>");
            out.print("<br><b class='title'>Garantia</b><br>");
            lst_contGar = jpa_detalle.ContadorGarantia();
            out.print("<table class='table' style='width:285px'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>GARANTIA</th>");
            out.print("<th>EQUIPO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_contGar.size(); i++) {
                Object[] obj_contR = (Object[]) lst_contGar.get(i);
                out.print("<tr>");
                out.print("<td>" + obj_contR[0] + "</td>");
                out.print("<td>" + ((obj_contR[1] == null) ? "N/A" : obj_contR[1]) + "</td>");
                if (obj_contR[2] != null) {
                    obj_contR[2] = obj_contR[2].toString().replace("[", "").replace("]", "");
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + obj_contR[2] + "</span></b>"
                            + "</td>");
                } else {
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + ((obj_contR[2] == null) ? "N/A" : obj_contR[2]) + "</span></b>"
                            + "</td>");
                }
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("</div>");

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ESTADO DETALLE">
            out.print("<div style='float:right;'>");
            out.print("<br><b class='title'>Tipo Estado</b><br>");
            lst_contEstDet = jpa_detalle.ContadorEstadoDetalle();
            out.print("<table class='table' style='width:285px'>");
            out.print("<tr>");
            out.print("<th>#</th>");
            out.print("<th>ESTADO</th>");
            out.print("<th>EQUIPO</th>");
            out.print("</tr>");
            for (int i = 0; i < lst_contEstDet.size(); i++) {
                Object[] obj_contR = (Object[]) lst_contEstDet.get(i);
                out.print("<tr>");
                out.print("<td>" + obj_contR[0] + "</td>");
                out.print("<td>" + ((obj_contR[1] == null) ? "N/A" : obj_contR[1]) + "</td>");
                if (obj_contR[2] != null) {
                    obj_contR[2] = obj_contR[2].toString().replace("[", "").replace("]", "");
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>" + obj_contR[2] + "</span></b>"
                            + "</td>");
                } else {
                    out.print("<td>"
                            + "<b class='tooltip_css'>Equipos<span class='tooltiptext_css' valign='top'>N/A</span></b>"
                            + "</td>");
                }
                out.print("</tr>");
            }
            out.print("</table>");
            out.print("</div>");
//                //</editor-fold>
            out.print("</fieldset>");
            out.print("</div>");
//                //</editor-fold>
            out.print("<div style='display:flex; width:100%; justify-content:space-between;'>");
            out.print("<div style='margin-top: 0.5%;' id='NavPosicion'></div>");
            out.print("<div>");
            out.print("<a onclick=\"tableToExcel('testTable', 'W3C Example Table')\" value=\"Export to Excel\"><i class='far fa-file-excel fa-lg' style='color:#292929'></i></a><b>Exportar a excel</b>"
                    + "&nbsp;&nbsp;<a href='#' onclick='mostrarConvencion(15)'><i class='far fa-chart-bar fa-lg' style='color:#292929'></i></a><b>Contadores</b>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div >");
            out.print("<div class='table-container'>");
            out.print("<div class='page-container'>");
            out.print("<table class='table-deq' id='resultados'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class='sticky3' style='font-size: 12px;'>PC</th>");
            out.print("<th class='sticky2'>NOMBRE EQUIPO</th>");
            out.print("<th class='sticky2'>TIPO ESTADO</th>");
            out.print("<th class='sticky2'>LOGIN PLASTITEC</th>");
            out.print("<th class='sticky2'>IP</th>");
            out.print("<th class='sticky2'>MAC</th>");
            out.print("<th class='sticky2'>GARANTIA</th>");
            out.print("<th class='sticky2'>ANTIVIRUS</th>");
            out.print("<th class='sticky2'>INTERNET</th>");
            out.print("<th class='sticky2'>WIN INSTALADO</th>");
            out.print("<th class='sticky2'>OFFICE INSTALADO</th>");
            out.print("<th class='sticky2'>VLAN</th>");
            out.print("<th class='sticky2'>VPN</th>");
            out.print("<th class='sticky2'>SKYE</th>");
            out.print("<th class='sticky2'>GMAIL</th>");
            out.print("<th class='sticky2'>CORREO INTERNO</th>");
            out.print("<th class='sticky2'>CORREO EXTERNO</th>");
            out.print("<th class='sticky2'>FACTURA</th>");
            out.print("<th class='sticky2'>FECHA FACTURA</th>");
            out.print("<th class='sticky2'>LICENCIA</th>");
            out.print("<th class='sticky2'>FECHA GARANTIA</th>");
            out.print("<th class='sticky2'>PROVEEDOR</th>");
            out.print("<th class='sticky2'>ACTIVOS SOPORTE</th>");
            out.print("<th class='sticky2'>TIPO SOFTWARE</th>");
            out.print("</thead>");
            out.print("</tr>");
            if (lst_detalleE != null) {
                for (int i = 0; i < lst_detalleE.size(); i++) {
                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                    Object[] obj_detalle = (Object[]) lst_detalleE.get(i);
                    out.print("<tbody>");
                    out.print("<tr align='center'>");
//                    out.print("<td class='sticky'><a href='Detalle_equipo?opc=1&txt_bus=&idDT=" + obj_detalle[0] + "'><b  style='font-size: 12px;;color:" + (obj_detalle[29].equals("B") ? "green" : (obj_detalle[29].equals("R") ? "orange" : "red")) + "' >" + obj_detalle[2] + "</b></a></td>");
                    out.print("<td class='sticky'><a href='#'  style='color:#c4c4c4 !important; cursor: no-drop;'><b  style='font-size: 12px;;color:" + (obj_detalle[29].equals("B") ? "green" : (obj_detalle[29].equals("R") ? "orange" : "red")) + "' >" + obj_detalle[2] + "</b></a></td>");
                    out.print("<td>" + obj_detalle[28] + "</td>");
                    out.print("<td>" + obj_detalle[25] + "</td>");
                    out.print("<td>" + obj_detalle[3] + "</td>");
                    out.print("<td>" + obj_detalle[4] + "</td>");
                    out.print("<td>" + obj_detalle[5] + "</td>");
                    out.print("<td>" + obj_detalle[22] + "</td>");
                    out.print("<td>" + obj_detalle[10] + "</td>");
                    out.print("<td>" + obj_detalle[11] + "</td>");
                    out.print("<td>" + obj_detalle[8] + "</td>");
                    out.print("<td>" + obj_detalle[9] + "</td>");
                    out.print("<td>" + obj_detalle[7] + "</td>");
                    out.print("<td>" + obj_detalle[12] + "</td>");
                    out.print("<td>" + obj_detalle[13] + "</td>");
                    out.print("<td>" + obj_detalle[14] + "</td>");
                    out.print("<td>" + obj_detalle[15] + "</td>");
                    out.print("<td>" + obj_detalle[16] + "</td>");
                    out.print("<td>" + obj_detalle[17] + "</td>");
                    out.print("<td>" + obj_detalle[18] + "</td>");
                    out.print("<td>" + obj_detalle[19] + "</td>");
                    out.print("<td>" + obj_detalle[20] + "</td>");
                    out.print("<td>" + obj_detalle[21] + "</td>");
                    out.print("<td>" + obj_detalle[23] + "</td>");
                    out.print("<td>" + obj_detalle[24] + "</td>");
                    out.print("</tbody>");
                    out.print("</tr>");
                    //</editor-fold>
                }
            } else {
                out.print("<td colspan='23' align='center'>NO EXISTE REGISTROS  </td>");
            }
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager = new Pager('resultados',25);");
            out.print("pager.init();");
            out.print("pager.showPageNav('pager','NavPosicion');");
            out.print("pager.showPage(1);");
            out.print("</script>");
            out.print("</div>");
            out.print("</div>");

        } catch (IOException ex) {
            Logger.getLogger(Tag_detalle_equipos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
