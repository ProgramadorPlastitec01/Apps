package Tags;

import Controladores.ControlEspesorJpaController;
import Controladores.ControlEspesorPPJpaController;
import Controladores.EntradaMaterialJpaController;
import Controladores.EventoJpaController;
import Controladores.FactorMedidaJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RolloEstriaVentanaJpaController;
import Controladores.RolloJpaController;
import Controladores.SerialJpaController;
import Metodos.Estadisticos;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_rollo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
//            JPAS
            RolloJpaController jpacrlo = new RolloJpaController();
            RolloEstriaVentanaJpaController jpacrev = new RolloEstriaVentanaJpaController();
            ProductoJpaController jpacpdt = new ProductoJpaController();
            ControlEspesorJpaController jpaccep = new ControlEspesorJpaController();
            ControlEspesorPPJpaController jpaccepp = new ControlEspesorPPJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            Estadisticos mtdetd = new Estadisticos();
            EntradaMaterialJpaController jpacemt = new EntradaMaterialJpaController();
            FactorMedidaJpaController jpacfmd = new FactorMedidaJpaController();
            EventoJpaController jpacevt = new EventoJpaController();
            SerialJpaController jpacsrl = new SerialJpaController();
            //VARIABLES GLOBALES
            String filtro = "";
            String orden = "";
            double resultado = 0;
            int contador = 0;
            double factor_medida_actual = 0;
            String resultados = "";
            String seriales = "";
            int id_producto = 0;
            int id_registro = 0;
            int materiales = 0;
            int equipos = 0;
            int id_entrada_material = 0;
            int estria_ventana = 0;
            int id_rollo = 0;
            int toma = 0;
            List lst_rollos = null;
            List lst_producto = null;
            List lst_entradas_material = null;
            List lst_seriales_seleccion = null;
            List lst_eventos = null;
            List lst_rollo = null;
            List lst_rollo_siguiente = null;
            List lst_registro = null;
            List lst_factor_medida = null;
            List lst_controles_espesor = null;
            if (pageContext.getRequest().getAttribute("Rollo") != null) {
                //                // <editor-fold defaultstate="collapsed" desc="ROLLO R-PI-011">
                if (pageContext.getRequest().getAttribute("Rollo").toString().equals("Registro_rollo")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    id_rollo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_rollo").toString());
                    materiales = Integer.parseInt(pageContext.getRequest().getAttribute("Materiales").toString());
                    id_entrada_material = Integer.parseInt(pageContext.getRequest().getAttribute("Id_entrada_material").toString());
                    Date fecha = new Date();
                    String fecha_actual = (fecha.getYear() + 1900) + "" + (fecha.getMonth() <= 9 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() <= 9 ? "-0" : "-") + "" + fecha.getDate();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    lst_rollo_siguiente = jpacrlo.Traer_ultimo_rollo(id_producto, id_registro);
                    Object[] obj_rollo = null;
                    if (id_rollo != 0) {
                        lst_rollo = jpacrlo.Traer_rollo_id_producto(id_producto, id_rollo);
                        obj_rollo = (Object[]) lst_rollo.get(0);
                    }
                    int cont_bajar_rollo = 0;
                    int rollo_siguiente = 0;
                    int nextRoll = 0;
                    int minRoll = 0;
                    int maxRoll = 0;
                    boolean allowed = true;

                    if (lst_rollo_siguiente != null) {
                        Object[] obj_rollo_siguiente = (Object[]) lst_rollo_siguiente.get(0);
                        nextRoll = Integer.parseInt(obj_rollo_siguiente[0].toString());
                        if (obj_rollo_siguiente[3] != null) {

                            //<editor-fold defaultstate="collapsed" desc="VALIDA ROLLOS">
//                            VALIDA QUE EL SIGUIEN ROLLO SIGA DENTRO DEL RANGO
                            String[] range = obj_rollo_siguiente[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                            int posit = Integer.parseInt(obj_rollo_siguiente[4].toString());
                            if (posit >= range.length) {
                                allowed = false;
                            } else {
                                try {
                                    String[] detailRag = range[posit].split("-");
                                    minRoll = Integer.parseInt(detailRag[0].toString());
                                    maxRoll = Integer.parseInt(detailRag[1].toString());
                                    if (nextRoll == maxRoll) {
                                        allowed = false;
                                    }
                                } catch (Exception e) {
                                }
                            }
                            //</editor-fold>
                        }
                        if (obj_rollo_siguiente[1].equals("P")) {
                            cont_bajar_rollo++;
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="VALIDAR ROLLO">
//                            VALIDA SIGUIENTE ROLLO QUE ESTE DENTRO DEL RANGO
                            if (obj_rollo_siguiente[3] != null) {
                                rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                                if (rollo_siguiente >= minRoll && rollo_siguiente <= maxRoll) {
                                    rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                                } else {
                                    rollo_siguiente = minRoll;
                                }
                            } else {
                                rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
                            }

//</editor-fold>
                        }
                    } else {
                        rollo_siguiente = 1;
                    }
                    String fecha_registro = obj_registro[2].toString().replace("-", "");
                    int fecha_convert = Integer.parseInt(fecha_registro);
                    // <editor-fold defaultstate="collapsed" desc="REGISTRO ROLLO">
                    if ((Integer) obj_registro[14] == 1) {
                        out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                        out.print("<fieldset class='popup_local' style='width:700px;position: absolute;top: 50px;left: 5%;'>");
                        //<editor-fold defaultstate="collapsed" desc="# DE ROLLO">
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES ">
                        out.print("<div align='right'>"
                                + "<form action='Rollo?opc=1' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                + "<input type='hidden' name='rlo' value='0' />"
                                + "<input type='hidden' name='fto' value='' />"
                                + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:22px;height:22px' alt='edit'  /></a>"
                                + "</form>"
                                + "</div>");
                        if (id_rollo != 0) {
                            out.print("<h3>Modificar Rollo " + obj_rollo[2] + "</h3>");
                        } else if (lst_rollo_siguiente != null) {
                            Object[] obj_rollo_siguiente = (Object[]) lst_rollo_siguiente.get(0);
                            if ((Integer) obj_rollo_siguiente[0] == 0) {
                                allowed = false;
                            }
                            if (obj_rollo_siguiente[1].equals("P")) {

                                out.print("<h3>Registrar Rollo " + obj_rollo_siguiente[0] + "</h3>");
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="VALIDACION ROLLO 1">
//                                EN ESTE MODULO SE VALIDA QUE EL ROLLO ESTE DENTRO DE UN RANGO PERMITIDO
//                                if (obj_rollo_siguiente[3] != null) {
//                                    nextRoll = Integer.parseInt(obj_rollo_siguiente[0].toString());
//                                    String[] range = obj_rollo_siguiente[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                                    int posit = Integer.parseInt(obj_rollo_siguiente[4].toString());
//                                    String[] detailRag = range[posit].split("-");
//                                    minRoll = Integer.parseInt(detailRag[0].toString());
//                                    maxRoll = Integer.parseInt(detailRag[1].toString());
//                                    if (nextRoll == maxRoll) {
//                                        allowed = false;
//                                    }
//                                }
                                //</editor-fold>
                                if (obj_rollo_siguiente[1].equals("P")) {
                                    cont_bajar_rollo++;
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="VALIDACION ROLLO 2 - SIGUIENTE ROLLO">
//                                    EN ESTE CAMPO DE VALIDAICON SE CONTROLA EL SIGUIENTE ROLLO EN CASO DE SER NUEVO
//                                    if (obj_rollo_siguiente[3] != null) {
//                                        if (rollo_siguiente >= minRoll && rollo_siguiente <= maxRoll) {
//                                            if (rollo_siguiente == minRoll) {
//                                                rollo_siguiente = minRoll;
//                                            } else {
//                                                rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
//                                            }
//                                        } else {
//                                            rollo_siguiente = minRoll;
//                                        }
//                                    } else {
//                                        rollo_siguiente = ((Integer) obj_rollo_siguiente[0] + 1);
//                                    }
                                    //</editor-fold>
                                }
                                if (allowed) {
                                    out.print("<h3>Registrar Rollo " + rollo_siguiente + "</h3>");
                                } else {
                                    out.print("<h3>Sin rollos asignados</h3>");
                                }
                            }
                        } else {
                            lst_registro = jpacrlo.proximo_rollo_idregistro(id_registro);
                            if (lst_registro != null && allowed) {
                                Object[] ObReg = (Object[]) lst_registro.get(0);
                                String[] ragRll = ObReg[1].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                int posit = Integer.parseInt(ObReg[3].toString());
                                String[] detailRang = ragRll[posit].toString().split("-");
                                try {
                                    int SigRoll = Integer.parseInt(detailRang[0].toString());
                                    if (SigRoll == 0) {
                                        allowed = false;
                                        out.print("<h3>Sin rollo programado</h3>");
                                    } else {
                                        out.print("<h3>Registrar Rollo " + SigRoll + "</h3>");
                                    }
                                } catch (Exception e) {
                                    int SigRoll = 1;
                                    out.print("<h3>Registrar Rollo " + SigRoll + "</h3>");
                                }
                            } else {
                                out.print("<h3>Sin rollo programado</h3>");
                            }
                        }
                        out.print("<input type='hidden' id='Txt_pared_doble_min' value='" + ((Double) obj_registro[28] - (Double) obj_registro[30]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_doble_max' value='" + ((Double) obj_registro[28] + (Double) obj_registro[29]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_sencilla_min' value='" + ((Double) obj_registro[31] - (Double) obj_registro[33]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_sencilla_max' value='" + ((Double) obj_registro[31] + (Double) obj_registro[32]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_manga_min' value='" + ((Double) obj_registro[34] - (Double) obj_registro[36]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_manga_max' value='" + ((Double) obj_registro[34] + (Double) obj_registro[35]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_bobina_min' value='" + ((Double) obj_registro[37] - (Double) obj_registro[39]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_bobina_max' value='" + ((Double) obj_registro[37] + (Double) obj_registro[38]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_bobina_min' value='" + ((Double) obj_registro[37] - (Double) obj_registro[39]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_bobina_max' value='" + ((Double) obj_registro[37] + (Double) obj_registro[38]) + "' />");
                        out.print("<input type='hidden' id='Txt_peso_min' value='" + ((Double) obj_registro[46] - (Double) obj_registro[48]) + "' />");
                        out.print("<input type='hidden' id='Txt_peso_max' value='" + ((Double) obj_registro[46] + (Double) obj_registro[47]) + "' />");
                        out.print("<form action='Rollo?opc=" + ((id_rollo == 0) ? "4" : "12") + "' method='post' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />");
                        if (id_rollo != 0) {
                            out.print("<input type='hidden' name='Estado_calidad' value='" + obj_rollo[3] + "' />"
                                    + "<input type='hidden' name='Id_rollo' value='" + obj_rollo[0] + "' />"
                                    + "<input type='hidden' name='Rollo' value='" + obj_rollo[2] + "' />");
                        }
                        if (obj_registro[56].toString().equals("1")) {
                            if (id_rollo == 0) {
                                out.print("<input type='radio' name='Rdb_rollo_refilado' id='Rdb_rollo_refilado' value='1' onclick='Rollo_refilado(this)' checked />Rollo padre ");
                                out.print("<input type='radio' name='Rdb_rollo_refilado' id='Rdb_rollo_refilado' value='0' onclick='Rollo_refilado(this)' />Rollo hijo<br />");
                                out.print("<b class='negro'>Numero de rollo refilado :</b>");
                                out.print("<input type='text' name='Txt_numero_refilado' id='Txt_numero_refilado' placeholder='Numero de rollo refilado' readonly='true' title='Numero de rollo refilado' value='0' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_numero_refilado');val1.add(Validate.Presence);</script>");
                            } else {
                                out.print("<input type='radio' name='Rdb_rollo_refilado' id='Rdb_rollo_refilado' value='1' onclick='Rollo_refilado(this)' " + ((obj_rollo[28].toString().equals("0")) ? "checked" : "") + " />Rollo padre ");
                                out.print("<input type='radio' name='Rdb_rollo_refilado' id='Rdb_rollo_refilado' value='0' onclick='Rollo_refilado(this)' " + ((obj_rollo[28].toString().equals("0")) ? "" : "checked") + "/>Rollo hijo<br />");
                                out.print("<b class='negro'>Numero de rollo refilado :</b>");
                                out.print("<input type='text' name='Txt_numero_refilado' id='Txt_numero_refilado' placeholder='Numero de rollo refilado' title='Numero de rollo refilado' "
                                        + "value='" + obj_rollo[28] + "' " + ((obj_rollo[28].toString().equals("0")) ? "readonly='true'" : "") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_numero_refilado');val1.add(Validate.Presence);</script>");
                            }
                        } else {
                            out.print("<input type='hidden' name='Txt_numero_refilado' id='Txt_numero_refilado' value='0' />");
                        }
//</editor-fold>
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'><h3>Pared Doble</h3>");
                        //<editor-fold defaultstate="collapsed" desc="PARED DOBLE">
                        out.print("<b>Primer extremo :</b>");
                        out.print("<input type='text' name='Txt_primer_extremo' id='Txt_primer_extremo' placeholder='Primer extremo' title='Primer extremo' onkeyup='javascript:this.value=this.value.toUpperCase();' "
                                + "value='" + ((id_rollo == 0 || obj_rollo[4] == null) ? "" : obj_rollo[4]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_primer_extremo');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                + "</script>");
                        out.print("<b>Centro :</b>");
                        out.print("<input type='text' name='Txt_centro' id='Txt_centro' placeholder='Centro' title='Centro' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                + "value='" + ((id_rollo == 0 || obj_rollo[5] == null) ? "" : obj_rollo[5]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centro');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                + "</script>");
                        out.print("<b>Segundo extremo :</b>");
                        out.print("<input type='text' name='Txt_segundo_extremo' id='Txt_segundo_extremo' placeholder='Segundo extremo' title='Segundo extremo' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                + "value='" + ((id_rollo == 0 || obj_rollo[6] == null) ? "" : obj_rollo[6]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_segundo_extremo');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                + "</script>");
//</editor-fold>
                        out.print("</td>");
                        out.print("<td valign='top'><h3>Pared Sencilla</h3>");
                        //<editor-fold defaultstate="collapsed" desc="PARED SENCILLA">
                        out.print("<b>Minimo pared sencilla :</b>");
                        out.print("<input type='text' name='Txt_min_pared_sencilla' id='Txt_min_pared_sencilla' placeholder='Min. pared sencilla' title='Min. pared sencilla' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                + "value='" + ((id_rollo == 0 || obj_rollo[7] == null) ? "" : obj_rollo[7]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_min_pared_sencilla');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                + "</script>");
                        out.print("<b>Maximo pared sencilla :</b>");
                        out.print("<input type='text' name='Txt_max_pared_sencilla' id='Txt_max_pared_sencilla' placeholder='Max. pared sencilla' title='Max. pared sencilla' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                + "value='" + ((id_rollo == 0 || obj_rollo[8] == null) ? "" : obj_rollo[8]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_max_pared_sencilla');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                + "</script>");
//</editor-fold>
                        out.print("</td>");
                        out.print("<td valign='top'><h3>Ancho</h3>");
                        //<editor-fold defaultstate="collapsed" desc="ANCHO">
                        out.print("<b>Ancho de manga :</b>");
                        out.print("<input type='text' name='Txt_ancho_manga' id='Txt_ancho_manga' placeholder='Ancho de manga' title='Ancho de manga' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                + "value='" + ((id_rollo == 0 || obj_rollo[9] == null) ? "" : obj_rollo[9]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_manga_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_manga_max'} );"
                                + "</script>");
                        out.print("<b>Ancho de bobina :</b>");
                        out.print("<input type='text' name='Txt_ancho_bobina' id='Txt_ancho_bobina' placeholder='Ancho de bobina' title='Ancho de bobina' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                + "value='" + ((id_rollo == 0 || obj_rollo[10] == null) ? "" : obj_rollo[10]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_bobina_min'} );"
                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_bobina_max'} );"
                                + "</script>");
//</editor-fold>
                        out.print("</td>");
                        out.print("<td valign='top'><h3>Peso</h3>");
                        //<editor-fold defaultstate="collapsed" desc="PESO">
                        if (obj_registro[56].toString().equals("1")) {
                            out.print("<b>Peso bruto PP:</b>");
                            //out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' onkeyup='javascript:this.value=this.value.toUpperCase();' onchange='Peso(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ")'/>"
                            out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' onchange='Peso_pp(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ")'"
                                    + "value='" + ((id_rollo == 0 || obj_rollo[11] == null) ? "" : obj_rollo[11]) + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_bruto');val1.add(Validate.Presence);</script>");
                            out.print("<b>Peso Neto PP:</b><br />");
                            out.print("<input type='text' name='Txt_peso_neto' id='Txt_peso_neto' placeholder='Peso Neto' title='Peso Neto' "
                                    + "" + ((id_rollo == 0 || obj_rollo[12] == null) ? "onkeyup='javascript:this.value=this.value.toUpperCase();' readonly='true' value='0'" : " value='" + obj_rollo[12] + "' readonly='true'") + " />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_neto');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_peso_min'} );"
                                    //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_peso_max'} );"
                                    + "</script>");
                        } else {
                            out.print("<b>Peso bruto:</b>");
                            out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' onchange='Peso(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ")'"
                                    + "value='" + ((id_rollo == 0 || obj_rollo[11] == null) ? "" : obj_rollo[11]) + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_bruto');val1.add(Validate.Presence);</script>");
                            out.print("<b>Peso Neto:</b><br />");
                            out.print("<input type='text' name='Txt_peso_neto' id='Txt_peso_neto' placeholder='Peso Neto' title='Peso Neto' "
                                    + "" + ((id_rollo == 0 || obj_rollo[12] == null) ? "onkeyup='javascript:this.value=this.value.toUpperCase();' readonly='true' value='0'" : " value='" + obj_rollo[12] + "' readonly='true'") + " />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_neto');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_peso_min'} );"
                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_peso_max'} );"
                                    + "</script>");
                        }
//</editor-fold>
                        out.print("</td>");
                        out.print("<td valign='top'><h3>Particulas y Perimetros</h3>");
                        //<editor-fold defaultstate="collapsed" desc="PARTICULAS Y PERIMETROS">
                        out.print("<b>Particulas:</b><br />");
                        out.print("<select name='Cbx_particula' id='Cbx_particula' title='Particulas'>");
                        out.print("<option value='0' >Seleccionar</option>");
                        if (id_rollo == 0 || obj_rollo[13] == null) {
                            out.print("<option value='NO' >NO</option>");
                            out.print("<option value='SI' >SI</option>");
                        } else if (obj_rollo[13].equals("NO")) {
                            out.print("<option value='NO' selected>NO</option>");
                            out.print("<option value='SI' >SI</option>");
                        } else {
                            out.print("<option value='NO' >NO</option>");
                            out.print("<option value='SI' selected>SI</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_particula');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                        if (fecha_convert >= 20160317) {
                            out.print("<b>Perimetro Derecho° :</b>");
                            out.print("<input type='text' name='Txt_perimetro_1' id='Txt_perimetro_1' placeholder='Perimetro Derecho' title='Perimetro Derecho' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                    + "value='" + ((id_rollo == 0 || obj_rollo[26] == null) ? "" : obj_rollo[26]) + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_1');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    + "</script>");
                            out.print("<b>Perimetro Izquierdo :</b>");
                            out.print("<input type='text' name='Txt_perimetro_2' id='Txt_perimetro_2' placeholder='Perimetro Izquierdo' title='Perimetro Izquierdo' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                    + "value='" + ((id_rollo == 0 || obj_rollo[27] == null) ? "" : obj_rollo[27]) + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_2');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    + "</script>");
                        } else {
                            out.print("<input type='hidden' name='Txt_perimetro_1' value='0' />");
                            out.print("<input type='hidden' name='Txt_perimetro_2' value='0' />");
                        }
//</editor-fold>
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        if (allowed) {
                            if (id_rollo != 0) {
                                out.print("<input type='submit' value='Modificar' />");
                            } else {
                                out.print("<input type='submit' value='Registrar' />");
                            }
                        } else {
                            out.print("<p>Se ha alcanzado el maximo rollo asignado (" + maxRoll + "). </p>");
                        }
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="CONSULTA ROLLOS">
                    out.print("<div id='content_sin'>");
                    out.print("<form action='Orden?opc=6' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='tcs' value='0' />"
                            + "<input type='hidden' name='irg' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='edit' title='Volver a registros' /></a>"
                            + "</form>");
                    out.print("<div align='right'>"
                            + "<form action='Rollo?opc=1' method='post' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                    } else {
                        out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                    }
                    out.print("</form></div>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_rollos = jpacrlo.Traer_rollos_id_registro(id_registro);
                    } else {
                        lst_rollos = jpacrlo.Traer_rollos_id_registro_filtro(id_registro, filtro);
                        if (lst_rollos == null) {
                            lst_rollos = jpacrlo.Traer_rollos_id_registro(id_registro);
                        }
                    }
                    out.print("<div style='display:block'>");
                    out.print("<div style='float:left;width:400px'>");
//                    if ((rol.equals("Coordinadora_calidad") || rol.equals("Inspectora_calidad") || rol.equals("Administrador")) && cont_bajar_rollo == 0 && (Integer) obj_registro[15] == 1) {
//                        out.print("<a href='Rollo?opc=8&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "'><img src='Interfaz/Contenido/Iconos/Download.png' height='22px' width='22px' alt='edit' title='Bajar rollo " + rollo_siguiente + "' /></a>");
//                    }
                    out.print("<form action='Rollo?opc=1' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />"
                            + "<input type='hidden' name='fto' value='' />");
                    if ((rol.equals("Coordinadora_calidad") || rol.equals("Inspectora_calidad") || rol.equals("Administrador")) && cont_bajar_rollo == 0 && (Integer) obj_registro[15] == 1) {
                        if (allowed) {
                            out.print("<a href='Rollo?opc=8&irg=" + id_registro + "&odn=" + orden + "&ipd=" + id_producto + "'><img src='Interfaz/Contenido/Iconos/Download.png' height='22px' width='22px' alt='edit' title='Bajar rollo " + rollo_siguiente + "' /></a> Bajar rollo ");
                        } else {
                            out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Download.png' height='22px' width='22px' alt='edit' title='Sin rollos asignados' /></a> Bajar rollo ");
                        }
                    }
                    if (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Administrador")) {
                        out.print(((cont_bajar_rollo == 0) ? "<img onclick='JAVASCRIPT:document.getElementById(\"Registro_rollo_" + obj_registro[0] + "\").style.display=\"block\";' src='Interfaz/Contenido/Iconos/Plus.png' alt='edit' title='Registro de rollos' /> Registrar rollo" : ""));
                    }
                    out.print("<a href='JAVASCRIPT:FormActualizar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit' title='Volver a registros' /></a>Actualizar rollos"
                            + "</form></div>");
                    out.print("<div style='float:right;'>");
                    if (obj_registro[56].toString().equals("1")) {
                        out.print("<div style='float:left;width:165px'>");
                        out.print("<form action='Rollo?opc=1' method='post' name='FormMaterial' id='FormMaterial' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                + "<input type='hidden' name='rlo' value='0' />"
                                + "<input type='hidden' name='emt' value='1' />"
                                + "<input type='hidden' name='fto' value='' />"
                                + "<a href='JAVASCRIPT:FormMaterial.submit()'><img src='Interfaz/Contenido/Iconos/Entrada_material.png'  alt='edit' title='R-PI-034' /></a> Entradas de material "
                                + "</form></div>");
                    }
                    out.print("<a onclick=\"tableToExcel('Excel', 'R-PI-011')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF");
                    out.print("</div>");
                    out.print("</div>");
                    if (lst_rollos == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay rollos registrados </b>");
                        out.print("</center>");
                    } else {
                        String reglas = "";
                        String balanza = "";
                        String calibrador = "";
                        try {
                            lst_seriales_seleccion = jpacsrl.Traer_equipos_medicion_registro(id_registro);
                            Object[] obj_equipos_seleccion = (Object[]) lst_seriales_seleccion.get(0);
                            reglas = obj_equipos_seleccion[2] + "";
                            balanza = obj_equipos_seleccion[4] + "";
                            calibrador = obj_equipos_seleccion[6] + "";
                        } catch (Exception e) {
                            reglas = "<b class='naranja'>---</b>";
                            balanza = "<b class='naranja'>---</b>";
                            calibrador = "<b class='naranja'>---</b>";
                        }

                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR CURVATURA">
                        out.print("<div class='sweet-local' id='Ventana1' style='opacity: 1.03; display:none;'>");
                        out.print("<fieldset class='popup_local' style='width:700px;position: absolute;top: 50px;left: 5%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Registrar curvatura</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");

                        out.print("</div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>

                        out.print("<table class='table' style='width:100%'>");
                        if (fecha_convert >= 20160101) {
                            out.print("<tr>");
                            out.print("<td colspan='15' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td align='center' colspan='3' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        if (fecha_convert >= 20160101) {
                            out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='9'" : "colspan='7'") + " align='center'><b class='negro'>REGISTRO</b></td>");
                        } else {
                            out.print("<td colspan='7' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                        }
                        out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-PI-011 V</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='9'" : "colspan='7'") + " align='center'><b class='negro'>INSPECCION CALIDAD<br />EXTRUSION MANGA</b></td>");
                        if (fecha_convert >= 20160317) {
                            out.print("<td colspan='3' align='center'><b class='negro'>VERSION 6</b></td>");
                        } else {
                            out.print("<td colspan='3' align='center'><b class='negro'>VERSION 5</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Orden</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center'>" + obj_registro[21] + "</td>");
                        out.print("<td align='center'><b>Producto</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='8'" : "colspan='7'") + ">" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                        out.print("<td align='center'><b>Máquina</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[9] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Fecha y turno</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[2] + " " + obj_registro[3] + "</td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center'><b>Ficha Técnica</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='3'" : "colspan='2'") + " align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                        out.print("<td align='center'><b>Regla</b></td>");
                        out.print("<td align='center'>" + reglas + "</td>");
                        out.print("<td align='center'><b>Balanza</b></td>");
                        out.print("<td align='center'>" + balanza + "</td>");
                        out.print("<td align='center'><b>Calibrador</b></td>");
                        out.print("<td colspan='2' align='center'>" + calibrador + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Pared Doble</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center' >" + obj_registro[28] + " <b>+</b> " + obj_registro[29] + " <b>-</b> " + obj_registro[30] + "</td>");
                        out.print("<td align='center'><b>Pared Sencilla</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center'>" + obj_registro[31] + " <b>+</b> " + obj_registro[32] + " <b>-</b> " + obj_registro[33] + "</td>");
                        out.print("<td align='center'><b>Ancho manga</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[34] + " <b>+</b> " + obj_registro[35] + " <b>-</b> " + obj_registro[36] + "</td>");
                        out.print("<td align='center'><b>Ancho bobina</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[37] + " <b>+</b> " + obj_registro[38] + " <b>-</b> " + obj_registro[39] + "</td>");
                        out.print("<td align='center'><b>Peso</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[46] + " <b>+</b> " + obj_registro[47] + " <b>-</b> " + obj_registro[48] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th rowspan='2'>Rollo</th>");
                        out.print("<th colspan='3'>Pared Doble</th>");
                        out.print("<th colspan='2'>Pared Sencilla</th>");
                        out.print("<th colspan='2'>Ancho</th>");
                        out.print("<th colspan='2'>Peso</th>");
                        if (fecha_convert >= 20160317) {
                            out.print("<th colspan='2'>Perimetro</th>");
                        }
                        out.print("<th rowspan='2'>Particulas</th>");
//                        out.print("<th rowspan='2'>Curvatura</th>");
                        out.print("<th rowspan='2' colspan='2'>Registros Calidad</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                        out.print("<td align='center'><b>Centro</b></td>");
                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                        out.print("<td align='center'><b>Minimo</b></td>");
                        out.print("<td align='center'><b>Maximo</b></td>");
                        out.print("<td align='center'><b>Manga</b></td>");
                        out.print("<td align='center'><b>Bobina</b></td>");
                        out.print("<td align='center'><b>Bruto</b></td>");
                        out.print("<td align='center'><b>Neto</b></td>");
                        if (fecha_convert >= 20160317) {
                            out.print("<td align='center' colspan='2'><b>Diferencia</b></td>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_rollos.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                            if ((Integer) obj_rollos[2] == 1 || (Integer) obj_rollos[2] % (Integer) obj_registro[52] == 0) {
                                out.print("<td align='center'>");
                                out.print("<form action='Rollo?opc=1' method='post' name='FormActualizarRollo" + i + "' id='FormActualizarRollo" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                        + "<input type='hidden' name='fto' value=''>"
                                        + "<a href='JAVASCRIPT:FormActualizarRollo" + i + ".submit()'><b>" + ((obj_rollos[28].toString().equals("0")) ? obj_rollos[2] : obj_rollos[28]) + "</b></a>");
                                out.print("</form>");
                                out.print("<form action='Rollo?opc=10' method='post' name='FormEvento" + i + "' id='FormEvento" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                        + "<input type='hidden' name='fto' value='NORMAL'>"
                                        + "<input type='hidden' name='etvt' value='0'>");
                                if (obj_rollos[3].toString().equals("A")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_aprobado.png' style='width:15px;height:15px;' alt='edit' title='Rollo aprobado' /></a></a>");
                                } else if (obj_rollos[3].toString().equals("C")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' style='width:15px;height:15px;' alt='edit' title='Rollo en cuarentena' /></a>");
                                } else if (obj_rollos[3].toString().equals("R")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' style='width:15px;height:15px;' alt='edit' title='Rollo rechazo' /></a>");
                                }
                                out.print("</form></td>");
                            } else if (obj_rollos[3].equals("P")) {
                                out.print("<td align='center'><b>" + ((obj_rollos[28].toString().equals("0")) ? obj_rollos[2] : obj_rollos[28]) + "</b></td>");
                            } else {
                                out.print("<td align='center'>");
                                out.print("<form action='Rollo?opc=1' method='post' name='FormActualizarRollo" + i + "' id='FormActualizarRollo" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                        + "<input type='hidden' name='fto' value=''>"
                                        + "<a href='JAVASCRIPT:FormActualizarRollo" + i + ".submit()'><b>" + obj_rollos[2] + "</b></a>");
                                out.print("</form>");
                                out.print("<form action='Rollo?opc=10' method='post' name='FormEvento" + i + "' id='FormEvento" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                        + "<input type='hidden' name='fto' value='NORMAL'>"
                                        + "<input type='hidden' name='etvt' value='0'>");
                                if (obj_rollos[3].toString().equals("A")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_aprobado.png' style='width:15px;height:15px;' alt='edit' title='Rollo aprobado' /></a></a>");
                                } else if (obj_rollos[3].toString().equals("C")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' style='width:15px;height:15px;' alt='edit' title='Rollo en cuarentena' /></a>");
                                } else if (obj_rollos[3].toString().equals("R")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' style='width:15px;height:15px;' alt='edit' title='Rollo rechazo' /></a>");
                                } else if (obj_rollos[3].toString().equals("S")) {
                                    out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_sin_datos.png' style='width:15px;height:15px;' alt='edit' title='Rollo sin confirmar' /></a>");
                                } else if (obj_rollos[3].toString().equals("P")) {
                                }
                                out.print("</form></td>");
                            }
                            if (obj_rollos[3].equals("P")) {
                                out.print("<td align='center' " + ((fecha_convert >= 20160317) ? "colspan='12'" : "colspan='11'") + "><b class='naranja'>Pendiente datos del rollo</b></td>");
                            } else if (obj_rollos[4] == null) {
                                out.print("<td align='center' " + ((fecha_convert >= 20160317) ? "colspan='12'" : "colspan='11'") + "><b class='naranja'>Pendiente datos del rollo</b></td>");
                            } else {
                                out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[7] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[8] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[9] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[11] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[12] + "</td>");
                                if (fecha_convert >= 20160317) {
                                    out.print("<td colspan='2' align='center'>");
                                    if (obj_rollos[26] == null) {
                                        out.print("<b class='naranja'>N/A</b>");
                                    } else {
                                        out.print("" + obj_rollos[26] + " - " + obj_rollos[27] + "");
                                        try {
                                            resultado = mtdetd.Direfencia_perimetros((Double) obj_rollos[26], (Double) obj_rollos[27]);
                                            if (resultado <= (Double) obj_registro[45]) {
                                                out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                            } else {
                                                out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                            }
                                        } catch (Exception e) {
                                            out.print("<b class='naranja'> = ---</b>");
                                        }
                                        out.print("</td>");
                                    }
                                    out.print("</td>");
                                }
                                out.print("<td align='center'>" + obj_rollos[13] + "</td>");
                            }
                            // CURVATURA
//                            int curva = 0;
//                            double dtax = 0;
//                            try {
//                                dtax = Float.parseFloat(obj_registro[71].toString());
//                                curva = (int) Math.round(dtax);
//                            } catch (Exception e) {
//                                curva = 0;
//                            }
//                            String conten = "";
//                            boolean ShowData = obj_rollos[29] == null;
//
//                            if (obj_rollos[29] == null) {
//                                conten = "-";
//                            } else {
//                                conten = obj_rollos[29].toString();
//                            }
//                            if (ShowData) {
//                                out.print("<td align='center'><span onclick='mostrarConvencion(1)' style='cursor: pointer;'>" + conten + "</span></td>");
//                            } else {
//                                out.print("<td align='center'> - </td>");
//                            }

                            out.print("<td align='center' style='display: flex;justify-content: space-evenly;'>");
                            if ((Integer) obj_registro[15] == 1) {
                                if (obj_registro[56].toString().equals("1")) {
                                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_id_rollo(Integer.parseInt(obj_rollos[0].toString()));
                                } else {
                                    lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo(Integer.parseInt(obj_rollos[0].toString()));
                                }
                                if ((Integer) obj_rollos[2] == 1 || (Integer) obj_rollos[2] % (Integer) obj_registro[52] == 0 || lst_controles_espesor != null) {
                                    out.print(""
                                            + "<form action='Rollo?opc=" + ((obj_registro[56].toString().equals("1")) ? "13" : "2") + "' method='post' name='FormVer" + i + "' id='FormVer" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                            + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                            + "<input type='hidden' name='tma' value='0'>"
                                            + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='edit' title='R-GC-078' /></a>"
                                            + "</form>");
                                    if ((Integer) obj_registro[55] == 0) {
                                        out.print(""
                                                + "<form action='Rollo?opc=6' method='post' name='Form97" + i + "' id='Form97" + i + "' onsubmit='checkSubmit();'>"
                                                + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                                + "<input type='hidden' name='odn' value='" + orden + "'>"
                                                + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                                + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                                + "<a href='JAVASCRIPT:Form97" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit_97.png' alt='edit' title='R-GC-097' /></a>"
                                                + "</form>");
                                    }
                                } else {
                                    out.print("<b class='naranja'>---</b>");
                                }
                            } else {
                                if (obj_registro[56].toString().equals("1")) {
                                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_id_rollo(Integer.parseInt(obj_rollos[0].toString()));
                                } else {
                                    lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo(Integer.parseInt(obj_rollos[0].toString()));
                                }
                                if ((Integer) obj_rollos[2] == 1 || (Integer) obj_rollos[2] % (Integer) obj_registro[52] == 0 || lst_controles_espesor != null) {
                                    if (lst_controles_espesor != null) {
                                        out.print(""
                                                + "<form action='Rollo?opc=" + ((obj_registro[56].toString().equals("1")) ? "13" : "2") + "' method='post' name='FormVer" + i + "' id='FormVer" + i + "' onsubmit='checkSubmit();'>"
                                                + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                                + "<input type='hidden' name='odn' value='" + orden + "'>"
                                                + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                                + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                                + "<input type='hidden' name='tma' value='0'>"
                                                + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='edit' title='R-GC-078' /></a>"
                                                + "</form>");
                                        if ((Integer) obj_registro[55] == 0) {
                                            out.print(""
                                                    + "<form action='Rollo?opc=6' method='post' name='Form97" + i + "' id='Form97" + i + "' onsubmit='checkSubmit();'>"
                                                    + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                                    + "<input type='hidden' name='odn' value='" + orden + "'>"
                                                    + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                                    + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                                    + "<a href='JAVASCRIPT:Form97" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit_97.png' alt='edit' title='R-GC-097' /></a>"
                                                    + "</form>");
                                        }
                                    } else {
                                        out.print("<b class='naranja'>---</b>");
                                    }
                                } else {
                                    out.print("<b class='naranja'>---</b>");
                                }
                            }
//                            if (i == 0) {
////                                out.print("<a href='JAVASCRIPT:'><img src='Interfaz/Contenido/Iconos/curvas2.png'></a>");
//                                out.print("<input type='radio' name='' id='' value='" + obj_rollos[0] + "'>");
//                            }
//                            if (curva != 0 && (i + 1) % curva == 0) {
//                                out.print("<input type='radio' name='' id='' value='" + obj_rollos[0] + "'>");
////                                out.print("<a href='JAVASCRIPT:'><img src='Interfaz/Contenido/Iconos/curvas2.png'>"+ curva +"</a>");
//                            }

                            out.print("</td>");

                        }
                        out.print("</table>");
                        if (obj_registro[56].toString().equals("1")) {
                            out.print("<div style='float:right'><b class='naranja'>NOTA: PARA LAS MANGA DE PP SE TOMARA EL DIAMETRO Y NO LOS PERIMETROS.</b></div>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="TABLA OCULTA">
                        out.print("<div style='display:none'>");
                        out.print("<div id='Imprimir'>");
                        out.print("<table class='table' id='Excel' style='width:100%'>");
                        if (fecha_convert >= 20160101) {
                            out.print("<tr>");
                            out.print("<td colspan='15' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td align='center' colspan='3' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        if (fecha_convert >= 20160101) {
                            out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='9'" : "colspan='7'") + " align='center'><b class='negro'>REGISTRO</b></td>");
                        } else {
                            out.print("<td colspan='7' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                        }
                        out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-PI-011 V</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='9'" : "colspan='7'") + " align='center'><b class='negro'>INSPECCION CALIDAD<br />EXTRUSION MANGA</b></td>");
                        if (fecha_convert >= 20160317) {
                            out.print("<td colspan='3' align='center'><b class='negro'>VERSION 6</b></td>");
                        } else {
                            out.print("<td colspan='3' align='center'><b class='negro'>VERSION 5</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Orden</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center'>" + obj_registro[21] + "</td>");
                        out.print("<td align='center'><b>Producto</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='8'" : "colspan='7'") + ">" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                        out.print("<td align='center'><b>Máquina</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[9] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Fecha y turno</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[2] + " " + obj_registro[3] + "</td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center'><b>Ficha Técnica</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='3'" : "colspan='2'") + " align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                        out.print("<td align='center'><b>Regla</b></td>");
                        out.print("<td align='center'>" + reglas + "</td>");
                        out.print("<td align='center'><b>Balanza</b></td>");
                        out.print("<td align='center'>" + balanza + "</td>");
                        out.print("<td align='center'><b>Calibrador</b></td>");
                        out.print("<td colspan='2' align='center'>" + calibrador + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Pared Doble</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center' >" + obj_registro[28] + " <b>+</b> " + obj_registro[29] + " <b>-</b> " + obj_registro[30] + "</td>");
                        out.print("<td align='center'><b>Pared Sencilla</b></td>");
                        out.print("<td " + ((fecha_convert >= 20160317) ? "colspan='2'" : "") + " align='center'>" + obj_registro[31] + " <b>+</b> " + obj_registro[32] + " <b>-</b> " + obj_registro[33] + "</td>");
                        out.print("<td align='center'><b>Ancho manga</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[34] + " <b>+</b> " + obj_registro[35] + " <b>-</b> " + obj_registro[36] + "</td>");
                        out.print("<td align='center'><b>Ancho bobina</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[37] + " <b>+</b> " + obj_registro[38] + " <b>-</b> " + obj_registro[39] + "</td>");
                        out.print("<td align='center'><b>Peso</b></td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[46] + " <b>+</b> " + obj_registro[47] + " <b>-</b> " + obj_registro[48] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th rowspan='2'>ROLLO</th>");
                        out.print("<th colspan='3'>PARED DOBLE</th>");
                        out.print("<th colspan='2'>PARED SENCILLA</th>");
                        out.print("<th colspan='2'>ANCHO</th>");
                        out.print("<th colspan='2'>PESO</th>");
                        out.print("<th colspan='2'>PERIMETROS</th>");
                        out.print("<th rowspan='2'>PARTICULAS</th>");
                        out.print("<th rowspan='2'>ESTADO CALIDAD</th>");
                        out.print("<th rowspan='2'>OPERARIO</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>PRIMER EXTREMO</b></td>");
                        out.print("<td align='center'><b>CENTRO</b></td>");
                        out.print("<td align='center'><b>SEGUNDO EXTREMO</b></td>");
                        out.print("<td align='center'><b>MINIMO</b></td>");
                        out.print("<td align='center'><b>MAXIMO</b></td>");
                        out.print("<td align='center'><b>MANGA</b></td>");
                        out.print("<td align='center'><b>BOBINA</b></td>");
                        out.print("<td align='center'><b>BRUTO</b></td>");
                        out.print("<td align='center'><b>NETO</b></td>");
                        out.print("<td align='center' colspan='2'>DIFERENCIA</td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_rollos.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                            out.print("<td align='center'><b>" + ((obj_rollos[28].toString().equals("0")) ? obj_rollos[2] : obj_rollos[28]) + "</b></td>");
                            if (obj_rollos[4] == null) {
                                out.print("<td align='center' " + ((fecha_convert >= 20160317) ? "colspan='12'" : "colspan='11'") + "><b class='naranja'>PENDIENTE DATOS DEL ROLLO</b></td>");
                            } else {
                                out.print("<td align='center'>" + ((obj_rollos[4] == null) ? "<b class='negro'>?</b>" : obj_rollos[4]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[5] == null) ? "<b class='negro'>?</b>" : obj_rollos[5]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[6] == null) ? "<b class='negro'>?</b>" : obj_rollos[6]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[7] == null) ? "<b class='negro'>?</b>" : obj_rollos[7]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[8] == null) ? "<b class='negro'>?</b>" : obj_rollos[8]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[9] == null) ? "<b class='negro'>?</b>" : obj_rollos[9]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[10] == null) ? "<b class='negro'>?</b>" : obj_rollos[10]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[11] == null) ? "<b class='negro'>?</b>" : obj_rollos[11]) + "</td>");
                                out.print("<td align='center'>" + ((obj_rollos[12] == null) ? "<b class='negro'>?</b>" : obj_rollos[12]) + "</td>");
                                if (fecha_convert >= 20160317) {
                                    out.print("<td colspan='2' align='center'>");
                                    if (obj_rollos[26] == null) {
                                        out.print("<b class='naranja'>N/A</b>");
                                    } else {
                                        out.print("" + obj_rollos[26] + " - " + obj_rollos[27] + "");
                                        try {
                                            resultado = mtdetd.Direfencia_perimetros((Double) obj_rollos[26], (Double) obj_rollos[27]);
                                            if (resultado <= (Double) obj_registro[45]) {
                                                out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                            } else {
                                                out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                            }
                                        } catch (Exception e) {
                                            out.print("<b class='naranja'> = ---</b>");
                                        }
                                    }
                                    out.print("</td>");
                                } else {
                                    out.print("<td colspan='2' align='center'><b class='naranja'>N/A</b></td>");
                                }
                                out.print("<td align='center'>" + ((obj_rollos[13] == null) ? "<b class='negro'>?</b>" : obj_rollos[13]) + "</td>");
                            }
                            if (obj_rollos[3].equals("A")) {
                                out.print("<td align='center'><b class='verde'>APROBADO</b></td>");
                            } else if (obj_rollos[3].equals("C")) {
                                out.print("<td align='center'><b  class='Amarillo'>CUARENTENA</b></td>");
                            } else if (obj_rollos[3].equals("R")) {
                                out.print("<td align='center'><b class='rojo'>RECHAZADO</b></td>");
                            } else {
                                out.print("<td align='center'>SIN ESTADO</td>");
                            }
                            if (obj_rollos[4] == null) {
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else {
                                out.print("<td align='center'>" + obj_rollos[17].toString().split("/")[1] + "</td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    // </editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="R-PI-034">
                    if (materiales > 0) {
                        lst_producto = jpacpdt.Productos_id_producto(id_producto);
                        Object[] obj_producto = (Object[]) lst_producto.get(0);
                        out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' style='width:90%;height:600px;top:1%;left:0%;overflow:scroll;'>");
                        out.print("<div style='float:right'>");
                        out.print("<form action='Rollo?opc=1' method='post' name='FormVolverMateriales' id='FormVolverMateriales' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                + "<input type='hidden' name='rlo' value='0' />"
                                + "<input type='hidden' name='fto' value='' />"
                                + "<a href='JAVASCRIPT:FormVolverMateriales.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Volver' /></a>"
                                + "</form></div>");
                        out.print("<a onclick=\"tableToExcel('Excel_mp', 'R-PI-011')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                                + "<a onclick='Imprimir_mp();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF");
                        out.print("<div id='Imprimir_mp'>");
                        out.print("<script language='javascript'>");
                        out.print("function NextFocus(sig) {");
                        out.print("var siguiente = document.getElementById(sig);");
                        out.print("siguiente.focus();");
                        out.print("}");
                        out.print("function NextFocusM(sig) {");
                        out.print("var siguiente = document.getElementById(sig);");
                        out.print("siguiente.focus();");
                        out.print("}");
                        out.print("</script>");
                        out.print("<table class='table4' id='Excel_mp' style='width:100%' >");
                        //<editor-fold defaultstate="collapsed" desc="CABECERA">
                        out.print("<tr>");
                        out.print("<td colspan='14' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='7' align='center'><b class='negro'>REGISTRO</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-PI-034 V</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='7' align='center'><b class='negro'>CONTROL ENTRADA DE MATERIAS PRIMAS</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 0</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2'><b>ORDEN</b></td>");
                        out.print("<td colspan='2' >" + obj_producto[35] + "</td>");
                        out.print("<td colspan='2' ><b>PRODUCTO</b></td>");
                        out.print("<td colspan='4' >" + obj_producto[2] + " / " + obj_producto[3] + "</td>");
                        out.print("<td ><b>FICHA</b></td>");
                        out.print("<td colspan='3' >" + obj_producto[6] + " <b>VERSIÓN</b> " + obj_producto[7] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th rowspan='2' >OPC</th>");
                        out.print("<th colspan='2'>FECHA</th>");
                        out.print("<th rowspan='2' >LOTE PROD.</th>");
                        out.print("<th colspan='3'>MATERIA PRIMA A INGRESAR</th>");
                        out.print("<th colspan='3'>MATERIA PRIMA EN PROCESO</th>");
                        out.print("<th rowspan='2'>COLOR DE LA MATERIA<br />PRIMA SEGUN TABLA</th>");
                        out.print("<th colspan='2'>RESPONSABLES</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Fecha y hora</b></td>");
                        out.print("<td align='center'><b>Turno</b></td>");
                        out.print("<td align='center'><b>M</b></td>");
                        out.print("<td align='center'><b>Lote</b></td>");
                        out.print("<td align='center'><b>Cantidad Kg</b></td>");
                        out.print("<td align='center'><b>M</b></td>");
                        out.print("<td align='center'><b>Lote</b></td>");
                        out.print("<td align='center'><b>Cantidad Kg</b></td>");
                        out.print("<td align='center'><b>Operario Encargado</b></td>");
                        out.print("<td align='center'><b>Inspectora de calidad</b></td>");
                        out.print("</tr>");
                        //</editor-fold>
                        if (Integer.parseInt(obj_registro[14].toString()) == 1 || Integer.parseInt(obj_registro[15].toString()) == 1) {
                            if (!(rol.equals("Consulta") || rol.equals("Administrador"))) {
                                out.print("<tr>");
                                //<editor-fold defaultstate="collapsed" desc="REGISTRA MP">
                                out.print("<form action='Rollo?opc=15' method='post' id='FormEntradaMateria'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />");
                                out.print("<td></td>");
                                out.print("<td colspan='3'><b class='naranja'>" + fecha_actual + "</b></td>");
                                if (Integer.parseInt(obj_registro[14].toString()) == 1 && (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion"))) {
                                    out.print("<td><input type='text' name='Txt_m_entrante' id='Valor_1' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;;margin:0\" onchange=\"NextFocus('Valor_2')\" required value='M' autofocus/></td>");
                                    out.print("<td><input type='text' name='Txt_lote_entrante' id='Valor_2' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;;margin:0\" onchange=\"NextFocus('Valor_3')\" required /></td>");
                                    out.print("<td><input type='text' name='Txt_cantidad_entrante' id='Valor_3' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"NextFocus('Valor_7')\" required /></td>");
                                } else {
                                    out.print("<input type='hidden' name='Txt_m_entrante' value='N/A' />"
                                            + "<input type='hidden' name='Txt_lote_entrante' value='N/A' />"
                                            + "<input type='hidden' name='Txt_cantidad_entrante' value='0' />");
                                    out.print("<td colspan='3'><b class='naranja'>N/A</b></td>");
                                }
                                if (Integer.parseInt(obj_registro[15].toString()) == 1 && (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad"))) {
                                    out.print("<td><input type='text' name='Txt_m_proceso' id='Valor_4' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;;margin:0\" onchange=\"NextFocus('Valor_5')\" required value='M' autofocus/></td>");
                                    out.print("<td><input type='text' name='Txt_lote_proceso' id='Valor_5' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;;margin:0\" onchange=\"NextFocus('Valor_6')\" required /></td>");
                                    out.print("<td><input type='text' name='Txt_cantidad_proceso' id='Valor_6' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"NextFocus('Valor_7')\" required /></td>");
                                } else {
                                    out.print("<input type='hidden' name='Txt_m_proceso' value='N/A' />"
                                            + "<input type='hidden' name='Txt_lote_proceso' value='N/A' />"
                                            + "<input type='hidden' name='Txt_cantidad_proceso' value='0' />");
                                    out.print("<td colspan='3'><b class='naranja'>N/A</b></td>");
                                }
                                if (Integer.parseInt(obj_registro[14].toString()) == 1 && (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion"))) {
                                    out.print("<td><input type='text' name='Txt_color_entrante' id='Valor_7' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"JAVASCRIPT:FormEntradaMateria.submit()\" required /></td>");
                                } else if (Integer.parseInt(obj_registro[15].toString()) == 1 && (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad"))) {
                                    out.print("<td><input type='text' name='Txt_color_entrante' id='Valor_7' style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"JAVASCRIPT:FormEntradaMateria.submit()\" required /></td>");
                                } else {
                                    out.print("<td ><b class='naranja'>N/A</b></td>");
                                }
                                out.print("<td colspan='3'><b class='naranja'>PENDIENTE</b></td>");
                                out.print("</form>");
                                out.print("</tr>");
                                //</editor-fold>
                            }
                        }
                        lst_entradas_material = jpacemt.Traer_entradas_material_id_producto(id_producto);
                        if (lst_entradas_material != null) {
                            for (int i = 0; i < lst_entradas_material.size(); i++) {
                                Object[] obj_entradas_material = (Object[]) lst_entradas_material.get(i);
                                double turno = Double.parseDouble(obj_entradas_material[7].toString());
                                out.print("<tr>");
                                if (id_entrada_material > 0 && Integer.parseInt(obj_entradas_material[0].toString()) == id_entrada_material) {
                                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR O FIRMAR">
                                    out.print("<form action='Rollo?opc=1' method='post' name='FormCancelarModEntrada" + obj_entradas_material[0] + "' id='FormCancelarModEntrada" + obj_entradas_material[0] + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                            + "<input type='hidden' name='rlo' value='0' />"
                                            + "<input type='hidden' name='emt' value='" + id_registro + "' />"
                                            + "<input type='hidden' name='fto' value='' />"
                                            + "</form>");
                                    out.print("<td>");
                                    out.print("<a href='JAVASCRIPT:FormCancelarModEntrada" + obj_entradas_material[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' style='width:15px;height:15px;' alt='edit' title='Editar' /></a>"
                                            + " | <a href='JAVASCRIPT:FormModEntradaMateria" + obj_entradas_material[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Save.png' style='width:15px;height:15px;' alt='edit' title='Editar' /></a>");
                                    out.print("</td>");
                                    out.print("<form action='Rollo?opc=16' method='post' id='FormModEntradaMateria" + obj_entradas_material[0] + "'>"
                                            + "<input type='hidden' name='iem' value='" + obj_entradas_material[0] + "' />"
                                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />");
                                    out.print("<td>" + obj_entradas_material[6] + " | " + obj_entradas_material[7].toString().replace(".", ":") + "</td>");
                                    out.print("<td>" + ((turno >= 6 && turno < 14) ? " UNO " : ((turno >= 14 && turno < 22) ? " DOS " : " TRES ")) + "</td>");
                                    out.print("<td >" + obj_entradas_material[18] + "</td>");
                                    if (!rol.equals("Consulta")) {
                                        if (rol.equals("Administrador") || rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion")) {
                                            out.print("<td><input type='text' name='Txt_m_entrante' id='Valor_m1' value='" + obj_entradas_material[12] + "'"
                                                    + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"NextFocusM('Valor_m2')\"/></td>");
                                            out.print("<td><input type='text' name='Txt_lote_entrante' id='Valor_m2' value='" + obj_entradas_material[13] + "'"
                                                    + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"NextFocusM('Valor_m3')\"/></td>");
                                            out.print("<td><input type='text' name='Txt_cantidad_entrante' id='Valor_m3' value='" + obj_entradas_material[14] + "'"
                                                    + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" " + ((rol.equals("Administrador")) ? " onchange=\"NextFocus('Valor_m4')\" " : " onchange=\"NextFocus('Valor_m7')\" ") + " /></td>");
                                        } else {
                                            out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[12] + "</td>");
                                            out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[13] + "</td>");
                                            out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[14] + "</td>");
                                            out.print("<input type='hidden' name='Txt_m_entrante' value='" + obj_entradas_material[12] + "' />"
                                                    + "<input type='hidden' name='Txt_lote_entrante' value='" + obj_entradas_material[13] + "' />"
                                                    + "<input type='hidden' name='Txt_cantidad_entrante' value='" + obj_entradas_material[14] + "' />");
                                        }
                                        if (rol.equals("Administrador") || rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                                            out.print("<td><input type='text' name='Txt_m_proceso' id='Valor_m4' value='" + obj_entradas_material[8] + "'"
                                                    + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"NextFocusM('Valor_m5')\"/></td>");
                                            out.print("<td><input type='text' name='Txt_lote_proceso' id='Valor_m5' value='" + obj_entradas_material[9] + "'"
                                                    + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"NextFocusM('Valor_m6')\"/></td>");
                                            out.print("<td><input type='text' name='Txt_cantidad_proceso' id='Valor_m6' value='" + obj_entradas_material[10] + "'"
                                                    + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" " + ((rol.equals("Administrador")) ? " onchange=\"NextFocus('Valor_m7')\" " : " onchange=\"JAVASCRIPT:FormModEntradaMateria" + obj_entradas_material[0] + ".submit()\" ") + "/></td>");
                                        } else {
                                            out.print("<td style='background-color:#D7EBF5'>" + obj_entradas_material[8] + "</td>");
                                            out.print("<td style='background-color:#D7EBF5'>" + obj_entradas_material[9] + "</td>");
                                            out.print("<td style='background-color:#D7EBF5'>" + obj_entradas_material[10] + "</td>");
                                            out.print("<input type='hidden' name='Txt_m_proceso' value='" + obj_entradas_material[8] + "' />"
                                                    + "<input type='hidden' name='Txt_lote_proceso' value='" + obj_entradas_material[9] + "' />"
                                                    + "<input type='hidden' name='Txt_cantidad_proceso' value='" + obj_entradas_material[10] + "' />");
                                        }
                                        out.print("<td><input type='text' name='Txt_color_entrante' id='Valor_m7' value='" + obj_entradas_material[16] + "'"
                                                + "style=\"border-top:#AD103C;border-right:none;border-left:none;width:60px;font-size: 11px;color:#292929;margin:0\" onchange=\"JAVASCRIPT:FormModEntradaMateria" + obj_entradas_material[0] + ".submit()\"/></td>");
                                        out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[11].toString().split("/")[1] + "</td>");
                                        out.print("<td style='background-color:#D7EBF5'>" + ((!obj_entradas_material[15].toString().equals("N/A")) ? obj_entradas_material[15].toString().split("/")[1] : "<b class='rojo'>PENDIENTE</b>") + "</td>");
                                    }
                                    out.print("</form>");
                                    out.print("</tr>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                                    out.print("<td>");
                                    if (rol.equals("Administrador")) {
                                        out.print("<form action='Rollo?opc=1' method='post' name='FormEditarEntrada" + obj_entradas_material[0] + "' id='FormEditarEntrada" + obj_entradas_material[0] + "' onsubmit='checkSubmit();'>"
                                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                + "<input type='hidden' name='rlo' value='0' />"
                                                + "<input type='hidden' name='emt' value='" + id_registro + "' />"
                                                + "<input type='hidden' name='iem' value='" + obj_entradas_material[0] + "' />"
                                                + "<input type='hidden' name='fto' value='' />"
                                                + "<a href='JAVASCRIPT:FormEditarEntrada" + obj_entradas_material[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' style='width:15px;height:15px;' alt='edit' title='Editar' /></a>"
                                                + "</form>");
                                    }
                                    if (Integer.parseInt(obj_entradas_material[17].toString()) > 0) {
                                        if (Integer.parseInt(obj_entradas_material[19].toString()) == 0 && (rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion"))) {
                                            out.print("<form action='Rollo?opc=1' method='post' name='FormEditarEntrada" + obj_entradas_material[0] + "' id='FormEditarEntrada" + obj_entradas_material[0] + "' onsubmit='checkSubmit();'>"
                                                    + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                    + "<input type='hidden' name='rlo' value='0' />"
                                                    + "<input type='hidden' name='emt' value='" + id_registro + "' />"
                                                    + "<input type='hidden' name='iem' value='" + obj_entradas_material[0] + "' />"
                                                    + "<input type='hidden' name='fto' value='' />"
                                                    + "<a href='JAVASCRIPT:FormEditarEntrada" + obj_entradas_material[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' style='width:15px;height:15px;' alt='edit' title='Editar' /></a>"
                                                    + "</form>");
                                        } else if (Integer.parseInt(obj_entradas_material[19].toString()) == 1 && (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad"))) {
                                            out.print("<form action='Rollo?opc=1' method='post' name='FormEditarEntrada" + obj_entradas_material[0] + "' id='FormEditarEntrada" + obj_entradas_material[0] + "' onsubmit='checkSubmit();'>"
                                                    + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                    + "<input type='hidden' name='rlo' value='0' />"
                                                    + "<input type='hidden' name='emt' value='" + id_registro + "' />"
                                                    + "<input type='hidden' name='iem' value='" + obj_entradas_material[0] + "' />"
                                                    + "<input type='hidden' name='fto' value='' />"
                                                    + "<a href='JAVASCRIPT:FormEditarEntrada" + obj_entradas_material[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' style='width:15px;height:15px;' alt='edit' title='Editar' /></a>"
                                                    + "</form>");
                                        } else {
                                            if (!(rol.equals("Consulta") || rol.equals("Administrador"))) {
                                                out.print("<form action='Rollo?opc=17' method='post' name='FormFirmarEntrada" + obj_entradas_material[0] + "' id='FormFirmarEntrada" + obj_entradas_material[0] + "' onsubmit='checkSubmit();'>"
                                                        + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                                        + "<input type='hidden' name='odn' value='" + orden + "' />"
                                                        + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                                        + "<input type='hidden' name='iem' value='" + obj_entradas_material[0] + "' />"
                                                        + "<a href='JAVASCRIPT:FormFirmarEntrada" + obj_entradas_material[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Edit.png' style='width:15px;height:15px;' alt='edit' title='Firmar' /></a>"
                                                        + "</form>");
                                            }
                                            out.print("");
                                        }
                                    }
                                    out.print("</td>");
                                    out.print("<td>" + obj_entradas_material[6] + " | " + obj_entradas_material[7].toString().replace(".", ":") + "</td>");
                                    out.print("<td>" + ((turno >= 6 && turno < 14) ? " UNO " : ((turno >= 14 && turno < 22) ? " DOS " : " TRES ")) + "</td>");
                                    out.print("<td >" + obj_entradas_material[18] + "</td>");
                                    if (Integer.parseInt(obj_entradas_material[19].toString()) == 0) {
                                        out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[12] + "</td>");
                                        out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[13] + "</td>");
                                        out.print("<td style='background-color:#DEF3C2'>" + obj_entradas_material[14] + "</td>");
                                        out.print("<td colspan='3' style='background-color:#eee'><b class='negro'>N/A</b></td>");
                                    } else {
                                        out.print("<td colspan='3' style='background-color:#eee'><b class='negro'>N/A</b></td>");
                                        out.print("<td style='background-color:#D7EBF5'>" + obj_entradas_material[8] + "</td>");
                                        out.print("<td style='background-color:#D7EBF5'>" + obj_entradas_material[9] + "</td>");
                                        out.print("<td style='background-color:#D7EBF5'>" + obj_entradas_material[10] + "</td>");
                                    }
                                    out.print("<td>" + obj_entradas_material[16] + "</td>");
                                    out.print("<td style='background-color:#DEF3C2'>" + ((!obj_entradas_material[11].toString().equals("PENDIENTE/PENDIENTE")) ? obj_entradas_material[11].toString().split("/")[1] : "<b class='rojo'>PENDIENTE</b>") + "</td>");
                                    out.print("<td style='background-color:#D7EBF5'>" + ((!obj_entradas_material[15].toString().equals("PENDIENTE/PENDIENTE")) ? obj_entradas_material[15].toString().split("/")[1] : "<b class='rojo'>PENDIENTE</b>") + "</td>");
                                    //</editor-fold>
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="CONTROLES DE ESPESOR R-GC-078 / 159">
                else if (pageContext.getRequest().getAttribute("Rollo").toString().equals("Control_espesor")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    id_rollo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_rollo").toString());
                    orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    toma = Integer.parseInt(pageContext.getRequest().getAttribute("Toma").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    int cantidad_tomas = Integer.parseInt(obj_registro[53].toString());
                    int cantidad_evaluar = Integer.parseInt(obj_registro[54].toString());
                    double variacion_espesor = Double.parseDouble(obj_registro[43].toString());
                    double ancho_manga_min = Double.parseDouble(obj_registro[34].toString()) - Double.parseDouble(obj_registro[36].toString());
                    double ancho_manga_max = Double.parseDouble(obj_registro[34].toString()) + Double.parseDouble(obj_registro[35].toString());
                    double pared_doble_min = Double.parseDouble(obj_registro[28].toString()) - Double.parseDouble(obj_registro[30].toString());
                    double pared_doble_max = Double.parseDouble(obj_registro[28].toString()) + Double.parseDouble(obj_registro[29].toString());
                    double pared_sencilla_min = Double.parseDouble(obj_registro[31].toString()) - Double.parseDouble(obj_registro[33].toString());
                    double pared_sencilla_max = Double.parseDouble(obj_registro[31].toString()) + Double.parseDouble(obj_registro[32].toString());
                    out.print("<div id='content_sin'>");
                    out.print("<form action='Rollo?opc=1' method='post' name='FormVolver' id='FormVolver' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a registros' /></a>"
                            + "</form>");
                    out.print("<div align='right'>"
                            + "<form action='Rollo?opc=1' method='post' onsubmit='checkSubmit();'>"
                            + "<a onclick=\"tableToExcel('Excel', '" + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "R-GC-159" : "R-GC-078") + "')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\"  alt=\"\" title='Imprimir' /></a> Imprimir o PDF "
                            + "</form></div>");
                    lst_rollo = jpacrlo.Traer_rollo_id_registro(id_registro, id_rollo);
                    Object[] obj_rollo = (Object[]) lst_rollo.get(0);
                    String fecha_registro = obj_registro[2].toString().replace("-", "");
                    int fecha_convert = Integer.parseInt(fecha_registro);
                    out.print("<table class='table' style='width:100%'>");
                    if (fecha_convert >= 20160101) {
                        out.print("<tr>");
                        out.print("<td colspan='16' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td align='center' colspan='3' rowspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    if (fecha_convert >= 20160101) {
                        out.print("<td colspan='8' align='center'><b class='negro'>REGISTRO</b></td>");
                    } else {
                        out.print("<td colspan='8' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                    }
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td colspan='5' align='center'><b class='negro'>CODIGO<br />R-GC-159</b></td>");
                    } else {
                        out.print("<td colspan='5' align='center'><b class='negro'>CODIGO<br />R-GC-078</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='8' align='center'><b class='negro'>CONTROL ESPESOR MANGA</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        if (fecha_convert >= 20161221) {
                            out.print("<td colspan='4' align='center'><b class='negro'>VERSION 1</b></td>");
                        } else {
                            out.print("<td colspan='4' align='center'><b class='negro'>VERSION 0</b></td>");
                        }
                    } else {
                        out.print("<td colspan='5' align='center'><b class='negro'>VERSION 3</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>Orden</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[21] + "</td>");
                    out.print("<td align='center'><b>Producto</b></td>");
                    out.print("<td align='center' colspan='6'>" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                    out.print("<td align='center'><b>Lote Producto</b></td>");
                    out.print("<td colspan='5' align='center'>" + obj_registro[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>Fecha y turno</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[2] + " " + obj_registro[11] + "</td>");
                    out.print("<td align='center'><b>Ficha Técnica</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                    out.print("<td align='center'><b>Lote C</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[6] + "</td>");
                    out.print("<td align='center'><b>Lote P</b></td>");
                    out.print("<td  align='center'>" + obj_registro[7] + "</td>");
                    out.print("<td align='center'><b>Máquina</b></td>");
                    out.print("<td colspan='4' align='center'>" + obj_registro[9] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='15'>Datos extrusión</th>");
                    out.print("<th colspan='1' rowspan='2'>Curvatura <br> ( Calidad )</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Rollo " + obj_rollo[2] + "</th>");
                    out.print("<td align='center'><b>Primer Extremo</b></td>");
                    out.print("<td align='center'><b>Centro</b></td>");
                    out.print("<td align='center'><b>Segundo Extremo</b></td>");
                    out.print("<td align='center'><b>Mínimo</b></td>");
                    out.print("<td align='center'><b>Máximo</b></td>");
                    out.print("<td align='center'><b>Manga</b></td>");
                    out.print("<td align='center'><b>Bobina</b></td>");
                    out.print("<td align='center'><b>Bruto</b></td>");
                    out.print("<td align='center'><b>Neto</b></td>");
                    out.print("<td align='center'><b>Particulas</b></td>");
                    out.print("<td align='center' colspan='3'><b>Diferencia de perimetro</b></td>");
                    out.print("<td align='center'><b>Algorítmo</b></td>");

//                    out.print("<td align='center'><b>Curvatura</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if (obj_rollo[4] == null) {
                        out.print("<td align='center' colspan='11'><b class='naranja'>Pendiente datos del rollo</b></td>");
                    } else {
                        if (obj_rollo[3].equals("A")) {
                            out.print("<td align='center'><b class='verde'>Aprobado</b></td>");
                        } else if (obj_rollo[3].equals("C")) {
                            out.print("<td align='center'><b  class='naranja'>Curentena</b></td>");
                        } else if (obj_rollo[3].equals("R")) {
                            out.print("<td align='center'><b class='rojo'>Rechazado</b></td>");
                        } else {
                            out.print("<td align='center'>Sin estado</td>");
                        }
                        out.print("<td align='center'>" + obj_rollo[4] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[5] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[6] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[7] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[8] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[9] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[10] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[11] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[12] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[13] + "</td>");
                    }
                    out.print("<form action='Rollo?opc=5' method='post' onsubmit='checkSubmit();'>"
                            + "<td align='center' colspan='" + (((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? "2" : "3") + "'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                            + "<input type='hidden' name='odn' value='" + orden + "'>"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                            + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                            + "<input type='hidden' name='irl' value='" + obj_rollo[0] + "'>"
                            + "<input type='hidden' name='tma' value='0'>"
                            + "" + (((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? ""
                            + "<input style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;' type='text' placeholder='1°' name='Txt_perimetro_1' id='Txt_perimetro_1'  value='" + ((obj_rollo[14] == null) ? "" : obj_rollo[14]) + "'/>" : obj_rollo[14]) + ""
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_1');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    out.print(" - " + (((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? ""
                            + "<input style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;' type='text' placeholder='2°' name='Txt_perimetro_2' id='Txt_perimetro_2' value='" + ((obj_rollo[15] == null) ? "" : obj_rollo[15]) + "' />" : obj_rollo[15])
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_2');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    try {
                        resultado = mtdetd.Direfencia_perimetros((Double) obj_rollo[14], (Double) obj_rollo[15]);
                        if (resultado <= (Double) obj_registro[45]) {
                            out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                        } else {
                            out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                        }
                    } catch (Exception e) {
                        out.print(" = <b class='rojo'>---</b>");
                    }
                    out.print("</td>");

                    out.print((((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? "<td><div class='myButton'><input type='submit' value=''></div></td></form>" : ""));

                    out.print("<td align='center'>" + obj_rollo[16] + "</td>");

                    //CURVATURA
                    out.print("<td>");

                    out.print("<div class='divCurv'>");
                    out.print("<div class='divCurvIn' style='text-align: center;'>");
                    out.print("<form action='Rollo?opc=21' method='post' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='irg' value='" + id_registro + "'>"
                            + "<input type='hidden' name='odn' value='" + orden + "'>"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                            + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                            + "<input type='hidden' name='irl' value='" + obj_rollo[0] + "'>"
                            + "<input type='hidden' name='tma' value='0'>");
                    out.print("<input type='hidden' id='minCurv' value='0'>");
                    out.print("<input type='hidden' id='MaxCurv' value='5'>");
                    if ((Integer) obj_registro[15] == 1) {
                        out.print("<input style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;' type='text' placeholder='Curva..' name='Txt_curva_xt' id='Txt_curva_xt' value='" + ((obj_rollo[29] == null) ? "" : obj_rollo[29]) + "' "
                                + " onChange=\"Validacion('Txt_curva_xt','minCurv', 'MaxCurv', 'Txt_curva_xt')\">");
                        out.print("<script type='text/javascript'>var val2 = new LiveValidation('Txt_curva_xt');val2.add(Validate.Presence);val2.add(Validate.Decimal);</script>");
                        out.print("</div>");
                        out.print("<div class='divCurvIn'>");
                        out.print("<div class='myButton' style='margin-left: 10px;width: 15px; margin-top: 3px;'><input type='submit' value=''></div>");
                    } else {
                        out.print("<span>" + ((obj_rollo[29] == null) ? "-" : obj_rollo[29]) + "</span>");
                    }
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");

                    out.print("</td>");

                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='16'>Controles de espesor</th>");
                    out.print("</tr>");
                    out.print("</table>");
                    lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo((Integer) obj_rollo[0]);
                    if (lst_controles_espesor == null) {
                        for (int i = 0; i < cantidad_tomas; i++) {
                            jpaccep.Controles_vacios((Integer) obj_rollo[0], (i + 1));
                        }
                        lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo((Integer) obj_rollo[0]);
                    }
                    // <editor-fold defaultstate="collapsed" desc="REGISTO DE TOMAS">
                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                        if (toma > 0) {
                            if ((Integer) obj_control_espesor[2] == toma) {
                                out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                                out.print("<fieldset class='popup_local' style='width:90%;height:auto;top:30%;left:0%;overflow:scroll;'>");
//                                out.print("<fieldset class='resalta_field' id='Registro_tomas' style='width:800px;position: absolute;top: 300px;left: 25%;'>");
                                out.print("<form action='Rollo?opc=3' method='post' style='margin:0' id='Form_" + i + "' name='Form_" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                                        + "<input type='hidden' name='tma' value='" + obj_control_espesor[2] + "'>"
                                        + "<input type='hidden' name='cev' value='" + obj_registro[54] + "'>"
                                        + "<input type='hidden' name='ice' value='" + obj_control_espesor[0] + "'>");
                                int ps_1 = 3;
                                int ps_2 = 11;
                                int pd = 22;
                                out.print("<table class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td align='center' style='width:20%'><b>TOMA #" + toma + "</b></td>");
                                for (int j = 0; j < 8; j++) {
                                    out.print("<td align='center' style='width:70px'><b>" + (j + 1) + "</b></td>");
                                }
                                out.print("</tr>");
                                if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                                    out.print("<tr>");
                                    out.print("<td colspan='9'></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>Pared Doble</b></td>");
                                    for (int j = 0; j < 8; j++) {
                                        if (j <= (cantidad_evaluar - 1)) {
                                            if (j == (cantidad_evaluar - 1)) {
                                                out.print("<td align='center'>"
                                                        + "<input type='hidden' name='min_pd' id='min_pd' value='" + pared_doble_min + "' />"
                                                        + "<input type='hidden' name='max_pd' id='max_pd' value='" + pared_doble_max + "' />"
                                                        + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                        + "name='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "' id='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "'"
                                                        //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                        + "placeholder='" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "'"
                                                        + "onChange=\"Validacion('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "','min_pd', 'max_pd','Txt_parametro_ps" + obj_control_espesor[2] + "_" + ps_1 + "')\"/></td>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                            } else {
                                                out.print("<td align='center'>"
                                                        + "<input type='hidden' name='min_pd' id='min_pd' value='" + pared_doble_min + "' />"
                                                        + "<input type='hidden' name='max_pd' id='max_pd' value='" + pared_doble_max + "' />"
                                                        + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                        + "name='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "' id='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "'"
                                                        //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                        + "placeholder='" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "'"
                                                        + "onChange=\"Validacion('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "','min_pd', 'max_pd','Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + (j + 1)) + "')\"/></td>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                            }
                                        } else if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                            out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                        } else {
                                            out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                                out.print("<tr>");
                                out.print("<td colspan='9'></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td align='center'><b>Pared Sencilla 1</b></td>");
                                for (int j = 0; j < 8; j++) {
                                    if (j <= (cantidad_evaluar - 1)) {
                                        if (j == (cantidad_evaluar - 1)) {
                                            out.print("<td align='center'>"
                                                    + "<input type='hidden' name='min' id='min' value='" + pared_sencilla_min + "' />"
                                                    + "<input type='hidden' name='max' id='max' value='" + pared_sencilla_max + "' />"
                                                    + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                    + "name='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "' id='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "'"
                                                    //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "placeholder='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "onChange=\"Validacion('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "','min', 'max','Txt_parametro_ps" + obj_control_espesor[2] + "_" + ps_2 + "')\"/></td>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                        } else {
                                            out.print("<td align='center'>"
                                                    + "<input type='hidden' name='min' id='min' value='" + pared_sencilla_min + "' />"
                                                    + "<input type='hidden' name='max' id='max' value='" + pared_sencilla_max + "' />"
                                                    + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                    + "name='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "' id='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "'"
                                                    //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "placeholder='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "onChange=\"Validacion('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "','min', 'max','Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + (j + 1)) + "')\"/></td>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                        }
                                    } else if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                        out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                                    } else {
                                        out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                                    }
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='9' ></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td align='center'><b>Pared sencilla 2</b></td>");
                                for (int j = 0; j < 8; j++) {
                                    if (j <= (cantidad_evaluar - 1)) {
                                        out.print("<td align='center'>"
                                                + "<input type='hidden' name='min' id='min' value='" + pared_sencilla_min + "' />"
                                                + "<input type='hidden' name='max' id='max' value='" + pared_sencilla_max + "' />"
                                                + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                + "name='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "' id='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "'"
                                                //+ "value='" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "'"
                                                + "placeholder='" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "'"
                                                + "onChange=\"Validacion('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "','min', 'max','Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + (j + 1)) + "')\"/></td>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                    } else if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                        out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                                    } else {
                                        out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                                    }
                                }
                                out.print("</tr>");
                                lst_seriales_seleccion = jpacsrl.Traer_equipos_medicion_registro((Integer) obj_registro[0]);
                                Object[] obj_equipos_medicion = (Object[]) lst_seriales_seleccion.get(0);
                                try {
                                    seriales = obj_equipos_medicion[8].toString();
                                    //seriales = obj_equipos_medicion[2].toString() + "-" + obj_equipos_medicion[4].toString() + "-" + obj_equipos_medicion[6].toString() + "-" + obj_equipos_medicion[8].toString();
                                } catch (Exception ex) {
                                }
                                String[] arg_seriales = null;
                                if (seriales == null) {
                                    arg_seriales = null;
                                } else {
                                    arg_seriales = seriales.toString().split("-");
                                }
                                out.print("<tr>");
                                out.print("<td colspan='2' align='center'><b>Indicador Digital</b></td>");
                                if (arg_seriales != null) {
                                    out.print("<td align='center' colspan='3'>"
                                            + "<select name='Cbx_equipo_medicion_" + toma + "' id='Cbx_equipo_medicion_" + toma + "' style='font-size:11px'  />");
                                    out.print("<option value='0' style='margin:0;font-size: 11px;'>Asignar</option>");
                                    for (int j = 0; j < arg_seriales.length; j++) {
                                        out.print("<option value='" + arg_seriales[j] + "' >" + arg_seriales[j] + "</option>");
                                    }
                                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_equipo_medicion_" + toma + "');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td>");
                                } else {
                                    out.print("<td align='center' colspan='4'><b class='naranja'>Sin equipo</b></td>");
                                }
                                out.print("<td align='center' colspan='2' " + ((Integer.parseInt(obj_registro[55].toString()) > 0 && fecha_convert >= 20161221) ? "rowspan='2'" : "") + "><div class='myButton'><input type='submit' value=''></div><b class='verde'>Registrar</b></td>");
                                out.print("</form>");
                                out.print("<td align='center' colspan='2' " + ((Integer.parseInt(obj_registro[55].toString()) > 0 && fecha_convert >= 20161221) ? "rowspan='2'" : "") + ">"
                                        + "<form action='Rollo?opc=2' method='post' name='FormCancelar" + toma + "' id='FormCancelar" + toma + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                                        + "<input type='hidden' name='tma' value='0'>"
                                        + "<a href='JAVASCRIPT:FormCancelar" + toma + ".submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:18px;height:18px' title='Cancelar Registro' /></a>"
                                        + "<br /><b class='rojo'>CANCELAR</b></form></td>");
                                out.print("</tr>");
                                //ANCHO DE MANGA
                                if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                                    if (fecha_convert >= 20161221) {
                                        out.print("<tr>");
                                        out.print("<td colspan='2' align='center'><b>Ancho de manga</b></td>");
                                        out.print("<td colspan='3' align='center'>"
                                                + "<input type='hidden' name='min_am' id='min_am' value='" + ancho_manga_min + "' />"
                                                + "<input type='hidden' name='max_am' id='max_am' value='" + ancho_manga_max + "' />"
                                                + "<input type='text' style='text-align:center;margin:0;border-color:none;font-size:11px;'"
                                                + "name='Txt_ancho_manga_" + toma + "' id='Txt_ancho_manga_" + toma + "'"
                                                + "placeholder='Ancho de manga calidad' "
                                                + "onChange=\"Validacion('Txt_ancho_manga_" + toma + "','min_am', 'max_am','Txt_ancho_manga_" + toma + "')\" /></td>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_" + toma + "');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_manga_min'} );"
                                                //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_manga_max'} );"
                                                + "</script>");
                                        out.print("</tr>");
                                    }
                                    out.print("<input type='hidden' name='Txt_ancho_manga_" + toma + "' id='Txt_ancho_manga_" + toma + "' value='0' />");
                                } else {
                                    out.print("<input type='hidden' name='Txt_ancho_manga_" + toma + "' id='Txt_ancho_manga_" + toma + "' value='0' />");
                                }
                                out.print("</table>");
                                out.print("</fieldset>");
                                out.print("</div>");
                            }
                        }
                    }
                    // </editor-fold>
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='2'><b>Toma</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td align='center' colspan='8'><b>Pared Doble </b></td>");
                        out.print("<td align='center' rowspan='" + (lst_controles_espesor.size() + 2) + "' style='width:0.5px;background-color:#ddd;'></td>");
                    }
                    out.print("<td align='center' colspan='8'><b>Pared Sencilla 1</b></td>");
                    out.print("<td align='center' rowspan='" + (lst_controles_espesor.size() + 2) + "' style='width:0.5px;background-color:#ddd;'></td>");
                    out.print("<td align='center' colspan='8'><b>Pared Sencilla 2</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td align='center' rowspan='2' ><b>Comparador</b></td>");
                        if (fecha_convert >= 20161221) {
                            out.print("<td align='center' rowspan='2' ><b>Ancho<br />Pelicula</b></td>");
                        }
                        out.print("<td align='center' rowspan='2' ><b>Edge to Edge</b></td>");
                    } else {
                        out.print("<td align='center' rowspan='2' colspan='2'><b>Comparador</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td align='center'><b>1</b></td>");
                        out.print("<td align='center'><b>2</b></td>");
                        out.print("<td align='center'><b>3</b></td>");
                        out.print("<td align='center'><b>4</b></td>");
                        out.print("<td align='center'><b>5</b></td>");
                        out.print("<td align='center'><b>6</b></td>");
                        out.print("<td align='center'><b>7</b></td>");
                        out.print("<td align='center'><b>8</b></td>");
                    }
                    out.print("<td align='center'><b>1</b></td>");
                    out.print("<td align='center'><b>2</b></td>");
                    out.print("<td align='center'><b>3</b></td>");
                    out.print("<td align='center'><b>4</b></td>");
                    out.print("<td align='center'><b>5</b></td>");
                    out.print("<td align='center'><b>6</b></td>");
                    out.print("<td align='center'><b>7</b></td>");
                    out.print("<td align='center'><b>8</b></td>");
                    out.print("<td align='center'><b>1</b></td>");
                    out.print("<td align='center'><b>2</b></td>");
                    out.print("<td align='center'><b>3</b></td>");
                    out.print("<td align='center'><b>4</b></td>");
                    out.print("<td align='center'><b>5</b></td>");
                    out.print("<td align='center'><b>6</b></td>");
                    out.print("<td align='center'><b>7</b></td>");
                    out.print("<td align='center'><b>8</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                        int ps_1 = 3;
                        int ps_2 = 11;
                        int pd = 22;
                        double variacion = 0;
                        out.print("<tr>");
                        if ((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) {
                            out.print("<td align='center'>"
                                    + "<form action='Rollo?opc=2' method='post' name='FormToma" + i + "' id='FormToma" + i + "' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                    + "<input type='hidden' name='odn' value='" + orden + "'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                    + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                                    + "<input type='hidden' name='tma' value='" + obj_control_espesor[2] + "'>"
                                    + "<a href='JAVASCRIPT:FormToma" + i + ".submit()'><b>" + obj_control_espesor[2] + "</b></a>"
                                    + "</form></td>");
                        } else {
                            out.print("<td align='center'><b>" + obj_control_espesor[2] + "</b></td>");
                        }
                        // <editor-fold defaultstate="collapsed" desc="CONSULTA DE TOMAS CONTROLES DE ESPESOR">
                        if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                            for (int j = 0; j < 8; j++) {
                                if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                } else {
                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                }

                            }
                        }
                        for (int j = 0; j < 8; j++) {
                            if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                            }
                        }
                        for (int j = 0; j < 8; j++) {
                            if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                            }
                        }
                        if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                            out.print("<td align='center'>" + obj_control_espesor[19] + "</td>");
                            if (fecha_convert >= 20161221) {
                                if ((Double) obj_control_espesor[30] >= ancho_manga_min && (Double) obj_control_espesor[30] <= ancho_manga_max) {
                                    out.print("<td align='center' >" + obj_control_espesor[30] + "</td>");
                                } else {
                                    out.print("<td align='center' ><b class='rojo'>" + obj_control_espesor[30] + "</b></td>");
                                }
                            }
                            try {
                                if (cantidad_evaluar == 8) {
                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[22].toString()), Double.parseDouble(obj_control_espesor[29].toString()));
                                } else if (cantidad_evaluar == 6) {
                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[22].toString()), Double.parseDouble(obj_control_espesor[27].toString()));
                                } else {
                                    variacion = mtdetd.Variacion_espesor_pared_doble(Double.parseDouble(obj_control_espesor[22].toString()), Double.parseDouble(obj_control_espesor[25].toString()));
                                }
                                out.print("<td align='center'>" + ((variacion <= variacion_espesor) ? " <b class='verde' " : " <b class='rojo' ") + " style='text-transform: lowercase;'>" + variacion + " mm</b></td>");
                            } catch (Exception ex) {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            out.print("<td align='center'>" + obj_control_espesor[19] + "</td>");
                        }
                        out.print("</tr>");
                        // </editor-fold>
                    }
                    out.print("</table>");
                    // <editor-fold defaultstate="collapsed" desc="TABLA IMPRESION">
                    out.print("<div style='display:none'>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' id='Excel' style='width:100%'>");
                    if (fecha_convert >= 20160101) {
                        out.print("<tr>");
                        out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='28'" : "colspan='19'") + " style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' rowspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    if (fecha_convert >= 20160101) {
                        out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='17'" : "colspan='11'") + " align='center'><b class='negro'>REGISTRO</b></td>");
                    } else {
                        out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='17'" : "colspan='11'") + " align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                    }
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='6'" : "colspan='3'") + " align='center'><b class='negro'>CODIGO<br />R-GC-159</b></td>");
                    } else {
                        out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='9'" : "colspan='3'") + " align='center'><b class='negro'>CODIGO<br />R-GC-078</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='17'" : "colspan='11'") + " align='center'><b class='negro'>CONTROL ESPESOR MANGA</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        if (fecha_convert >= 20161221) {
                            out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='6'" : "colspan='3'") + " align='center'><b class='negro'>VERSION 1</b></td>");
                        } else {
                            out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='6'" : "colspan='3'") + " align='center'><b class='negro'>VERSION 0</b></td>");
                        }
                    } else {
                        out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='9'" : "colspan='3'") + " align='center'><b class='negro'>VERSION 3</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b>ORDEN</b></td>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='5'" : "colspan='2'") + " align='center'>" + obj_registro[21] + "</td>");
                    out.print("<td align='center' colspan='2'><b>PRODUCTO</b></td>");
                    out.print("<td align='center' " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='11'" : "colspan='8'") + ">" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                    out.print("<td align='center'><b>ROLLOS</b></td>");
                    out.print("<td align='center' " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='5'" : "colspan='2'") + " >" + obj_rollo[2] + " - " + obj_rollo[2] + ".</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b>FECHA Y TURNO</b></td>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='7'" : "colspan='4'") + " align='center'>" + obj_registro[2] + " " + obj_registro[11] + "</td>");
                    out.print("<td colspan='4' align='center'><b>FICHA TÉCNICA</b></td>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='7'" : "colspan='4'") + " align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                    out.print("<td align='center'><b>MÁQUINA</b></td>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='5'" : "colspan='2'") + " align='center'>" + obj_registro[9] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b>LOTE PRODUCTO</b></td>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='7'" : "colspan='4'") + " align='center'>" + obj_registro[5] + "</td>");
                    out.print("<td colspan='4' align='center'><b>LOTE C</b></td>");
                    out.print("<td " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='7'" : "colspan='4'") + " align='center'>" + obj_registro[6] + "</td>");
                    out.print("<td align='center'><b>LOTE P</b></td>");
                    out.print("<td align='center' " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='5'" : "colspan='2'") + ">" + obj_registro[7] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b># Rollo</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_rollo[2] + "</td>");
                    if (obj_rollo[3].equals("A")) {
                        out.print("<td align='center' colspan='5'><b class='verde'>APROBADO</b></td>");
                    } else if (obj_rollo[3].equals("C")) {
                        out.print("<td align='center' colspan='5'><b class='Amarillo'>CUARENTENA</b></td>");
                    } else if (obj_rollo[3].equals("R")) {
                        out.print("<td align='center' colspan='5'><b class='rojo'>RECHAZADO</b></td>");
                    } else {
                        out.print("<td align='center' colspan='5'>Sin estado</td>");
                    }
                    if (fecha_convert >= 20170401) {
                        out.print("<td align='center' colspan='4'><b>DIFERENCIA DE DIAMETRO</b></td>");
                        out.print("<td align='center' colspan='7'>");
                        out.print(obj_rollo[14] + " - " + obj_rollo[15]);
                        try {
                            resultado = mtdetd.Direfencia_perimetros((Double) obj_rollo[14], (Double) obj_rollo[15]);
                            if (resultado <= (Double) obj_registro[45]) {
                                out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                            } else {
                                out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                            }
                        } catch (Exception e) {
                            out.print(" = <b class='rojo'>---</b>");
                        }
                        out.print("</td>");
                    }
                    Object[] obj_responsable = (Object[]) lst_controles_espesor.get(0);
                    out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "1" : "4") + "'><b>RESPONSABLE</b></td>");
                    try {
                        out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "5" : "10") + "'>" + obj_responsable[20].toString().split("/")[1] + "</td>");
                    } catch (Exception e) {
                        out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "5" : "10") + "'>Sin asignar</td>");
                    }
                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<th colspan='2'>ROLLO " + obj_rollo[2] + "</th>");
//                    if (obj_rollo[3].equals("A")) {
//                        out.print("<td align='center' colspan='2'><b class='verde'>APROBADO</b></td>");
//                    } else if (obj_rollo[3].equals("C")) {
//                        out.print("<td align='center' colspan='2'><b  class='Amarillo'>CUARENTENA</b></td>");
//                    } else if (obj_rollo[3].equals("R")) {
//                        out.print("<td align='center' colspan='2'><b class='rojo'>RECHAZADO</b></td>");
//                    } else {
//                        out.print("<td align='center'>Sin estado</td>");
//                    }
//                    lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo((Integer) obj_rollo[0]);
//                    Object[] obj_responsable = (Object[]) lst_controles_espesor.get(0);
//                    out.print("<th " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='18'" : "colspan='9'") + ">CONTROLES DE ESPESOR</th>");
//                    out.print("<td align='center' colspan='3'><b>RESPONSABLE</b></td>");
//                    out.print("<td align='center' colspan='3'>" + obj_responsable[20] + "</td>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th " + ((Integer.parseInt(obj_registro[55].toString()) > 0) ? "colspan='28'" : "colspan='19'") + ">CONTROLES DE ESPESOR</th>");
                    out.print("</tr>");
                    if (lst_controles_espesor == null) {
                        for (int i = 0; i < cantidad_tomas; i++) {
                            jpaccep.Controles_vacios((Integer) obj_rollo[0], (i + 1));
                        }
                        lst_controles_espesor = jpaccep.Traer_controles_espesor_id_rollo((Integer) obj_rollo[0]);
                    }
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='2'><b>TOMA</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td align='center' colspan='8'><b>Pared Doble </b></td>");
                        out.print("<td align='center' rowspan='" + (lst_controles_espesor.size() + 2) + "' style='width:0.5px;'></td>");
                    }
                    out.print("<td align='center' colspan='8'><b>PARED SENCILLA 1</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) == 0) {
                        out.print("<td align='center' rowspan='" + (lst_controles_espesor.size() + 2) + "' style='width:0.5px;'></td>");
                    }
                    out.print("<td align='center' colspan='8'><b>PARED SENCILLA 2</b></td>");
                    out.print("<td align='center' rowspan='2'><b>COMPARADOR</b></td>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        if (fecha_convert >= 20161221) {
                            out.print("<td align='center' rowspan='2'><b>ANCHO<br />PELICULA</b></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                        out.print("<td align='center'><b>1</b></td>");
                        out.print("<td align='center'><b>2</b></td>");
                        out.print("<td align='center'><b>3</b></td>");
                        out.print("<td align='center'><b>4</b></td>");
                        out.print("<td align='center'><b>5</b></td>");
                        out.print("<td align='center'><b>6</b></td>");
                        out.print("<td align='center'><b>7</b></td>");
                        out.print("<td align='center'><b>8</b></td>");
                    }
                    out.print("<td align='center'><b>1</b></td>");
                    out.print("<td align='center'><b>2</b></td>");
                    out.print("<td align='center'><b>3</b></td>");
                    out.print("<td align='center'><b>4</b></td>");
                    out.print("<td align='center'><b>5</b></td>");
                    out.print("<td align='center'><b>6</b></td>");
                    out.print("<td align='center'><b>7</b></td>");
                    out.print("<td align='center'><b>8</b></td>");
                    out.print("<td align='center'><b>1</b></td>");
                    out.print("<td align='center'><b>2</b></td>");
                    out.print("<td align='center'><b>3</b></td>");
                    out.print("<td align='center'><b>4</b></td>");
                    out.print("<td align='center'><b>5</b></td>");
                    out.print("<td align='center'><b>6</b></td>");
                    out.print("<td align='center'><b>7</b></td>");
                    out.print("<td align='center'><b>8</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'><b>" + obj_control_espesor[2] + "</b></td>");
                        int ps_1 = 3;
                        int ps_2 = 11;
                        int pd = 22;
                        if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                            for (int j = 0; j < 8; j++) {
                                if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                    out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                } else {
                                    out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                }
                            }
                        }
                        for (int j = 0; j < 8; j++) {
                            if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                            }
                        }
                        for (int j = 0; j < 8; j++) {
                            if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                            }
                        }
                        out.print("<td align='center'>" + obj_control_espesor[19] + "</td>");
                        if (Integer.parseInt(obj_registro[55].toString()) > 0) {
                            if (fecha_convert >= 20161221) {
                                if ((Double) obj_control_espesor[30] >= ancho_manga_min && (Double) obj_control_espesor[30] <= ancho_manga_max) {
                                    out.print("<td align='center'>" + obj_control_espesor[30] + "</td>");
                                } else {
                                    out.print("<td align='center'><b class='rojo'>" + obj_control_espesor[30] + "</b></td>");
                                }
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    // </editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="CONTROLES DE ESPESOR R-GC-122 PP">
                else if (pageContext.getRequest().getAttribute("Rollo").toString().equals("Control_espesor_pp")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    id_rollo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_rollo").toString());
                    orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    toma = Integer.parseInt(pageContext.getRequest().getAttribute("Toma").toString());
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    int cantidad_tomas = Integer.parseInt(obj_registro[53].toString());
                    int cantidad_evaluar = Integer.parseInt(obj_registro[54].toString());
                    double variacion_espesor = Double.parseDouble(obj_registro[43].toString());
                    double ancho_manga_min = Double.parseDouble(obj_registro[34].toString()) - Double.parseDouble(obj_registro[36].toString());
                    double ancho_manga_max = Double.parseDouble(obj_registro[34].toString()) + Double.parseDouble(obj_registro[35].toString());
                    double pared_doble_min = Double.parseDouble(obj_registro[28].toString()) - Double.parseDouble(obj_registro[30].toString());
                    double pared_doble_max = Double.parseDouble(obj_registro[28].toString()) + Double.parseDouble(obj_registro[29].toString());
                    double pared_sencilla_min = Double.parseDouble(obj_registro[31].toString()) - Double.parseDouble(obj_registro[33].toString());
                    double pared_sencilla_max = Double.parseDouble(obj_registro[31].toString()) + Double.parseDouble(obj_registro[32].toString());
                    out.print("<div id='content_sin'>");
                    out.print("<form action='Rollo?opc=1' method='post' name='FormVolver' id='FormVolver' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a registros' /></a>"
                            + "</form>");
                    out.print("<div align='right'>"
                            + "<form action='Rollo?opc=1' method='post' onsubmit='checkSubmit();'>"
                            + "<a onclick=\"tableToExcel('Excel', 'R-GC-122')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF "
                            + "</form></div>");
                    lst_rollo = jpacrlo.Traer_rollo_id_registro(id_registro, id_rollo);
                    Object[] obj_rollo = (Object[]) lst_rollo.get(0);
                    String fecha_registro = obj_registro[2].toString().replace("-", "");
                    int fecha_convert = Integer.parseInt(fecha_registro);
                    out.print("<table class='table' style='width:100%'>");
                    // <editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<td colspan='" + ((fecha_convert >= 20170401) ? "17" : "15") + "' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "4" : "3") + "' rowspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='" + ((fecha_convert >= 20170401) ? "9" : "8") + "' align='center'><b class='negro'>REGISTRO</b></td>");
                    out.print("<td colspan='4' align='center'><b class='negro'>CODIGO<br />R-GC-122</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='" + ((fecha_convert >= 20170401) ? "9" : "8") + "' align='center'><b class='negro'>CONTROL ESPESOR MANGA PP</b></td>");
                    if (fecha_convert >= 20170401) {
                        out.print("<td colspan='4' align='center'><b class='negro'>VERSION 5</b></td>");
                    } else {
                        out.print("<td colspan='4' align='center'><b class='negro'>VERSION 4</b></td>");
                    }
                    out.print("</tr>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="DATOS GLOBALES">
                    out.print("<tr>");
                    out.print("<td align='center'><b>Orden</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[21] + "</td>");
                    out.print("<td align='center'><b>Producto</b></td>");
                    out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "7" : "6") + "'>" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                    out.print("<td align='center'><b>Lote Producto</b></td>");
                    out.print("<td colspan='" + ((fecha_convert >= 20170401) ? "5" : "4") + "' align='center'>" + obj_registro[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>Fecha y turno</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[2] + " " + obj_registro[11] + "</td>");
                    out.print("<td align='center'><b>Ficha Técnica</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                    out.print("<td align='center'><b>Lote C</b></td>");
                    out.print("<td colspan='" + ((fecha_convert >= 20170401) ? "3" : "2") + "' align='center'>" + obj_registro[6] + "</td>");
                    out.print("<td align='center'><b>Lote P</b></td>");
                    out.print("<td colspan='" + ((fecha_convert >= 20170401) ? "3" : "2") + "' align='center'>" + obj_registro[7] + "</td>");
                    out.print("<td align='center'><b>Máquina</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[9] + "</td>");
                    out.print("</tr>");
// </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="DATOS EXTRUSION">
                    out.print("<tr>");
                    out.print("<th colspan='" + ((fecha_convert >= 20170401) ? "17" : "15") + "'>Datos extrusión</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Rollo " + ((obj_rollo[28].toString().equals("0")) ? obj_rollo[2] : obj_rollo[28]) + "</th>");
                    out.print("<td align='center'><b>Primer Extremo</b></td>");
                    out.print("<td align='center'><b>Centro</b></td>");
                    out.print("<td align='center'><b>Segundo Extremo</b></td>");
                    out.print("<td align='center'><b>Mínimo</b></td>");
                    out.print("<td align='center'><b>Máximo</b></td>");
                    out.print("<td align='center'><b>Manga</b></td>");
                    out.print("<td align='center'><b>Bobina</b></td>");
                    out.print("<td align='center' colspan='2'><b>Bruto</b></td>");
                    out.print("<td align='center' colspan='2'><b>Neto</b></td>");
                    out.print("<td align='center' colspan='2'><b>Particulas</b></td>");
                    out.print("<td align='center' colspan='3'><b>Diferencia de diametros</b></td>");
//                    out.print("<td align='center'><b>Algorítmo</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if (obj_rollo[4] == null) {
                        out.print("<td align='center' colspan='11'><b class='naranja'>Pendiente datos del rollo</b></td>");
                    } else {
                        if (obj_rollo[3].equals("A")) {
                            out.print("<td align='center'><b class='verde'>Aprobado</b></td>");
                        } else if (obj_rollo[3].equals("C")) {
                            out.print("<td align='center'><b  class='naranja'>Curentena</b></td>");
                        } else if (obj_rollo[3].equals("R")) {
                            out.print("<td align='center'><b class='rojo'>Rechazado</b></td>");
                        } else {
                            out.print("<td align='center'>Sin estado</td>");
                        }
                        out.print("<td align='center'>" + obj_rollo[4] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[5] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[6] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[7] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[8] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[9] + "</td>");
                        out.print("<td align='center'>" + obj_rollo[10] + "</td>");
                        out.print("<td align='center' colspan='2'>" + obj_rollo[11] + "</td>");
                        out.print("<td align='center' colspan='2'>" + obj_rollo[12] + "</td>");
                        out.print("<td align='center' colspan='2'>" + obj_rollo[13] + "</td>");
                    }
                    out.print("<form action='Rollo?opc=18' method='post' onsubmit='checkSubmit();'>"
                            + "<td align='center' colspan='" + (((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? "2" : "3") + "'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                            + "<input type='hidden' name='odn' value='" + orden + "'>"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                            + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                            + "<input type='hidden' name='irl' value='" + obj_rollo[0] + "'>"
                            + "<input type='hidden' name='tma' value='0'>"
                            + "" + (((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? ""
                            + "<input style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;' type='text' placeholder='1°' name='Txt_perimetro_1' id='Txt_perimetro_1'  value='" + ((obj_rollo[14] == null) ? "" : obj_rollo[14]) + "'/>" : obj_rollo[14]) + ""
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_1');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    out.print(" - " + (((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? ""
                            + "<input style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;' type='text' placeholder='2°' name='Txt_perimetro_2' id='Txt_perimetro_2' value='" + ((obj_rollo[15] == null) ? "" : obj_rollo[15]) + "' />" : obj_rollo[15])
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_2');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    try {
                        resultado = mtdetd.Direfencia_perimetros((Double) obj_rollo[14], (Double) obj_rollo[15]);
                        if (resultado <= (Double) obj_registro[45]) {
                            out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                        } else {
                            out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                        }
                    } catch (Exception e) {
                        out.print(" = <b class='rojo'>---</b>");
                    }
                    out.print("</td>");
                    out.print((((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) ? "<td><div class='myButton'><input type='submit' value=''></div></td></form>" : ""));
//                    out.print("<td align='center'>" + obj_rollo[16] + "</td>");
                    out.print("</tr>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="DATOS CALIDAD CONTROLES DE ESPESOR">
                    out.print("<tr>");
                    out.print("<th colspan='" + ((fecha_convert >= 20170401) ? "17" : "15") + "'>Controles de espesor</th>");
                    out.print("</tr>");
                    out.print("</table>");
                    lst_controles_espesor = jpaccepp.Traer_controles_espesor_id_rollo((Integer) obj_rollo[0]);
                    if (lst_controles_espesor == null) {
                        for (int i = 0; i < cantidad_tomas; i++) {
                            jpaccepp.Controles_vacios((Integer) obj_rollo[0], (i + 1));
                        }
                        lst_controles_espesor = jpaccepp.Traer_controles_espesor_id_rollo((Integer) obj_rollo[0]);
                    }
//                    // <editor-fold defaultstate="collapsed" desc="REGISTO DE TOMAS">
                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                        if (toma > 0) {
                            if ((Integer) obj_control_espesor[2] == toma) {
                                out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                                out.print("<fieldset class='popup_local' style='width:90%;height:auto;top:35%;left:0%;overflow:scroll;'>");
//                                out.print("<fieldset class='resalta_field' id='Registro_tomas' style='width:1100px;position: absolute;top: 300px;left: 10%;'>");
//                                out.print("<legend>Controles de espesor </legend>");
                                out.print("<form action='Rollo?opc=14' method='post' style='margin:0' id='Form_" + i + "' name='Form_" + i + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                                        + "<input type='hidden' name='tma' value='" + obj_control_espesor[2] + "'>"
                                        + "<input type='hidden' name='cev' value='" + obj_registro[54] + "'>"
                                        + "<input type='hidden' name='ice' value='" + obj_control_espesor[0] + "'>");
                                int ps_1 = 3;
                                int ps_2 = 23;
                                int pd = 43;
                                out.print("<table class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td align='center'><b>TOMA #" + toma + "</b></td>");
                                for (int j = 0; j < 20; j++) {
                                    out.print("<td align='center' style='width:70px'><b>" + (j + 1) + "</b></td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='9'></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td align='center'><b>Pared Doble</b></td>");
                                for (int j = 0; j < 20; j++) {
                                    if (j <= (cantidad_evaluar - 1)) {
                                        if (j == (cantidad_evaluar - 1)) {
                                            out.print("<td align='center'>"
                                                    + "<input type='hidden' name='min_pd' id='min_pd' value='" + pared_doble_min + "' />"
                                                    + "<input type='hidden' name='max_pd' id='max_pd' value='" + pared_doble_max + "' />"
                                                    + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                    + "name='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "' id='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "'"
                                                    //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "placeholder='" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "'"
                                                    + "onChange=\"Validacion('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "','min_pd', 'max_pd','Txt_parametro_ps" + obj_control_espesor[2] + "_" + ps_1 + "')\"/></td>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                        } else {
                                            out.print("<td align='center'>"
                                                    + "<input type='hidden' name='min_pd' id='min_pd' value='" + pared_doble_min + "' />"
                                                    + "<input type='hidden' name='max_pd' id='max_pd' value='" + pared_doble_max + "' />"
                                                    + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                    + "name='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "' id='Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "'"
                                                    //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "placeholder='" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "'"
                                                    + "onChange=\"Validacion('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "','min_pd', 'max_pd','Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + (j + 1)) + "')\"/></td>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_pd" + obj_control_espesor[2] + "_" + (pd + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                        }
                                    } else if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                        out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                                    } else {
                                        out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                                    }
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='9'></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td align='center'><b>Pared Sencilla 1</b></td>");
                                for (int j = 0; j < 20; j++) {
                                    if (j <= (cantidad_evaluar - 1)) {
                                        if (j == (cantidad_evaluar - 1)) {
                                            out.print("<td align='center'>"
                                                    + "<input type='hidden' name='min' id='min' value='" + pared_sencilla_min + "' />"
                                                    + "<input type='hidden' name='max' id='max' value='" + pared_sencilla_max + "' />"
                                                    + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                    + "name='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "' id='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "'"
                                                    //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "placeholder='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "onChange=\"Validacion('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "','min', 'max','Txt_parametro_ps" + obj_control_espesor[2] + "_" + ps_2 + "')\"/></td>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                        } else {
                                            out.print("<td align='center'>"
                                                    + "<input type='hidden' name='min' id='min' value='" + pared_sencilla_min + "' />"
                                                    + "<input type='hidden' name='max' id='max' value='" + pared_sencilla_max + "' />"
                                                    + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                    + "name='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "' id='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "'"
                                                    //+ "value='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "placeholder='" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "'"
                                                    + "onChange=\"Validacion('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "','min', 'max','Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + (j + 1)) + "')\"/></td>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_1 + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                        }
                                    } else if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                        out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                                    } else {
                                        out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                                    }
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='9' ></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td align='center'><b>Pared sencilla 2</b></td>");
                                for (int j = 0; j < 20; j++) {
                                    if (j <= (cantidad_evaluar - 1)) {
                                        out.print("<td align='center'>"
                                                + "<input type='hidden' name='min' id='min' value='" + pared_sencilla_min + "' />"
                                                + "<input type='hidden' name='max' id='max' value='" + pared_sencilla_max + "' />"
                                                + "<input type='text' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;'"
                                                + "name='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "' id='Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "'"
                                                //+ "value='" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "'"
                                                + "placeholder='" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "'"
                                                + "onChange=\"Validacion('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "','min', 'max','Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + (j + 1)) + "')\"/></td>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro_ps" + obj_control_espesor[2] + "_" + (ps_2 + j) + "');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                                    } else if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                        out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                                    } else {
                                        out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                                    }
                                }
                                out.print("</tr>");
                                lst_seriales_seleccion = jpacsrl.Traer_equipos_medicion_registro((Integer) obj_registro[0]);
                                Object[] obj_equipos_medicion = (Object[]) lst_seriales_seleccion.get(0);
                                try {
                                    seriales = obj_equipos_medicion[8].toString();
                                    //seriales = obj_equipos_medicion[2].toString() + "-" + obj_equipos_medicion[4].toString() + "-" + obj_equipos_medicion[6].toString() + "-" + obj_equipos_medicion[8].toString();
                                } catch (Exception ex) {
                                }
                                String[] arg_seriales = null;
                                if (seriales == null) {
                                    arg_seriales = null;
                                } else {
                                    arg_seriales = seriales.toString().split("-");
                                }
                                out.print("<tr>");
                                //ANCHO DE MANGA
                                out.print("<td colspan='2' align='center'><b>Ancho de manga</b></td>");
                                out.print("<td colspan='4' align='center'>"
                                        + "<input type='hidden' name='min_am' id='min_am' value='" + ancho_manga_min + "' />"
                                        + "<input type='hidden' name='max_am' id='max_am' value='" + ancho_manga_max + "' />"
                                        + "<input type='text' style='text-align:center;margin:0;border-color:none;font-size:11px;'"
                                        + "name='Txt_ancho_manga_" + toma + "' id='Txt_ancho_manga_" + toma + "'"
                                        + "placeholder='Ancho de manga calidad' "
                                        + "onChange=\"Validacion('Txt_ancho_manga_" + toma + "','min_am', 'max_am','Txt_ancho_manga_" + toma + "')\" /></td>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga_" + toma + "');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_manga_min'} );"
                                        //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_manga_max'} );"
                                        + "</script>");
                                //INDICADOR DIGITAL
                                out.print("<td colspan='3' align='center'><b>Indicador Digital</b></td>");
                                if (arg_seriales != null) {
                                    out.print("<td align='center' colspan='4'>"
                                            + "<select name='Cbx_equipo_medicion_" + toma + "' id='Cbx_equipo_medicion_" + toma + "'  />");
                                    out.print("<option value='0' style='margin:0;font-size: 11px;'>Asignar</option>");
                                    for (int j = 0; j < arg_seriales.length; j++) {
                                        out.print("<option value='" + arg_seriales[j] + "' >" + arg_seriales[j] + "</option>");
                                    }
                                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_equipo_medicion_" + toma + "');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td>");
                                } else {
                                    out.print("<td align='center' colspan='4'><b class='naranja'>Sin equipo</b></td>");
                                }
                                out.print("<td align='center' colspan='4'><div class='myButton'><input type='submit' value=''></div><b class='verde'>Registrar</b></td>");
                                out.print("</form>");
                                out.print("<td align='center' colspan='4'>"
                                        + "<form action='Rollo?opc=13' method='post' name='FormCancelar" + toma + "' id='FormCancelar" + toma + "' onsubmit='checkSubmit();'>"
                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                        + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                                        + "<input type='hidden' name='tma' value='0'>"
                                        + "<a href='JAVASCRIPT:FormCancelar" + toma + ".submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:18px;height:18px' title='Cancelar Registro' /></a>"
                                        + "<br /><b class='rojo'>CANCELAR</b></form></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</fieldset>");
                                out.print("</div>");
                            }
                        }
                    }
//                    // </editor-fold>
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'><b>Toma</b></td>");
                    for (int i = 0; i < 20; i++) {
                        out.print("<td align='center' ><b>" + (i + 1) + "</b></td>");
                    }
                    out.print("<td align='center'><b>Indicador<br />Digital</b></td>");
                    out.print("<td align='center' ><b>Ancho<br />Pelicula</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                        int ps_1 = 3;
                        int ps_2 = 23;
                        int pd = 43;
                        double variacion = 0;
                        out.print("<tr>");
                        if ((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) {
                            out.print("<td align='center' rowspan='3'>"
                                    + "<form action='Rollo?opc=13' method='post' name='FormToma" + i + "' id='FormToma" + i + "' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                    + "<input type='hidden' name='odn' value='" + orden + "'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                    + "<input type='hidden' name='rlo' value='" + id_rollo + "'>"
                                    + "<input type='hidden' name='tma' value='" + obj_control_espesor[2] + "'>"
                                    + "<a href='JAVASCRIPT:FormToma" + i + ".submit()'><b>" + obj_control_espesor[2] + "</b></a>"
                                    + "</form></td>");
                        } else {
                            out.print("<td align='center' rowspan='3'><b>" + obj_control_espesor[2] + "</b></td>");
                        }
                        // <editor-fold defaultstate="collapsed" desc="CONSULTA DE TOMAS CONTROLES DE ESPESOR">
                        out.print("<td align='center'><b>PD</b></td>");
                        for (int j = 0; j < 20; j++) {
                            if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                            }

                        }
                        out.print("<td align='center' rowspan='3'>" + obj_control_espesor[63] + "</td>");
                        if ((Double) obj_control_espesor[64] >= ancho_manga_min && (Double) obj_control_espesor[64] <= ancho_manga_max) {
                            out.print("<td align='center' rowspan='3'>" + obj_control_espesor[64] + "</td>");
                        } else {
                            out.print("<td align='center' rowspan='3'><b class='rojo'>" + obj_control_espesor[64] + "</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>PS1</b></td>");
                        for (int j = 0; j < 20; j++) {
                            if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>PS2</b></td>");
                        for (int j = 0; j < 20; j++) {
                            if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                            }
                        }
                        out.print("</tr>");
//                        // </editor-fold>
                    }
                    out.print("</table>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="TABLA IMPRESION">
                    out.print("<div style='display:none'>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' id='Excel' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<td colspan='28' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' rowspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='17' align='center'><b class='negro'>REGISTRO</b></td>");
                    out.print("<td colspan='6' align='center'><b class='negro'>CODIGO<br />R-GC-122</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='17' align='center'><b class='negro'>CONTROL ESPESOR MANGA PP</b></td>");
                    if (fecha_convert >= 20170401) {
                        out.print("<td colspan='6' align='center'><b class='negro'>VERSION 5</b></td>");
                    } else {
                        out.print("<td colspan='6' align='center'><b class='negro'>VERSION 4</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b>ORDEN</b></td>");
                    out.print("<td colspan='7' align='center'>" + obj_registro[21] + "</td>");
                    out.print("<td align='center' colspan='4'><b>PRODUCTO</b></td>");
                    out.print("<td align='center' colspan='13'>" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b>FECHA Y TURNO</b></td>");
                    out.print("<td colspan='7' align='center'>" + obj_registro[2] + " " + obj_registro[11] + "</td>");
                    out.print("<td colspan='4' align='center'><b>FICHA TÉCNICA</b></td>");
                    out.print("<td colspan='7' align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                    out.print("<td align='center'><b>MÁQUINA</b></td>");
                    out.print("<td colspan='5' align='center'>" + obj_registro[9] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b>LOTE PRODUCTO</b></td>");
                    out.print("<td colspan='7' align='center'>" + obj_registro[5] + "</td>");
                    out.print("<td colspan='4' align='center'><b>LOTE C</b></td>");
                    out.print("<td colspan='7' align='center'>" + obj_registro[6] + "</td>");
                    out.print("<td align='center'><b>LOTE P</b></td>");
                    out.print("<td align='center' colspan='5'>" + obj_registro[7] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'><b># Rollo</b></td>");
                    out.print("<td colspan='2' align='center'>" + ((obj_rollo[28].toString().equals("0")) ? obj_rollo[2] : obj_rollo[28]) + "</td>");
                    if (obj_rollo[3].equals("A")) {
                        out.print("<td align='center' colspan='5'><b class='verde'>APROBADO</b></td>");
                    } else if (obj_rollo[3].equals("C")) {
                        out.print("<td align='center' colspan='5'><b class='Amarillo'>CUARENTENA</b></td>");
                    } else if (obj_rollo[3].equals("R")) {
                        out.print("<td align='center' colspan='5'><b class='rojo'>RECHAZADO</b></td>");
                    } else {
                        out.print("<td align='center' colspan='5'>Sin estado</td>");
                    }
                    if (fecha_convert >= 20170401) {
                        out.print("<td align='center' colspan='4'><b>DIFERENCIA DE DIAMETRO</b></td>");
                        out.print("<td align='center' colspan='7'>");
                        out.print(obj_rollo[14] + " - " + obj_rollo[15]);
                        try {
                            resultado = mtdetd.Direfencia_perimetros((Double) obj_rollo[14], (Double) obj_rollo[15]);
                            if (resultado <= (Double) obj_registro[45]) {
                                out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                            } else {
                                out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                            }
                        } catch (Exception e) {
                            out.print(" = <b class='rojo'>---</b>");
                        }
                        out.print("</td>");
                    }
                    Object[] obj_responsable = (Object[]) lst_controles_espesor.get(0);
                    out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "1" : "4") + "'><b>RESPONSABLE</b></td>");
                    try {
                        out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "5" : "10") + "'>" + obj_responsable[65].toString().split("/")[1] + "</td>");
                    } catch (Exception e) {
                        out.print("<td align='center' colspan='" + ((fecha_convert >= 20170401) ? "5" : "10") + "'>Sin asignar</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='28'>CONTROLES DE ESPESOR</th>");
                    out.print("</tr>");
                    //CONTROLES DE ESPESOR TABLA DE EXPORTACIÓN
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'><b>Toma</b></td>");
                    for (int i = 0; i < 20; i++) {
                        out.print("<td align='center' ><b>" + (i + 1) + "</b></td>");
                    }
                    out.print("<td align='center' colspan='2'><b>INDICADOR<br />DIGITAL</b></td>");
                    out.print("<td align='center' colspan='4'><b>ANCHO<br />PELICULA</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_controles_espesor.size(); i++) {
                        Object[] obj_control_espesor = (Object[]) lst_controles_espesor.get(i);
                        int ps_1 = 3;
                        int ps_2 = 23;
                        int pd = 43;
                        double variacion = 0;
                        out.print("<tr>");
                        if ((Integer) obj_registro[15] == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) {
                            out.print("<td align='center' rowspan='3'><b>" + obj_control_espesor[2] + "</b></td>");
                        } else {
                            out.print("<td align='center' rowspan='3'><b>" + obj_control_espesor[2] + "</b></td>");
                        }
                        // <editor-fold defaultstate="collapsed" desc="CONSULTA DE TOMAS CONTROLES DE ESPESOR EXPORTAR">
                        out.print("<td align='center'><b>PD</b></td>");
                        for (int j = 0; j < 20; j++) {
                            if ((Double) obj_control_espesor[(pd + j)] >= pared_doble_min && (Double) obj_control_espesor[(pd + j)] <= pared_doble_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(pd + j)] == 0) ? "" : obj_control_espesor[(pd + j)]) + "</b></td>");
                            }
                        }
                        out.print("<td align='center' rowspan='3' colspan='2'>" + obj_control_espesor[63] + "</td>");
                        if ((Double) obj_control_espesor[64] >= ancho_manga_min && (Double) obj_control_espesor[64] <= ancho_manga_max) {
                            out.print("<td align='center' rowspan='3' colspan='4'>" + obj_control_espesor[64] + "</td>");
                        } else {
                            out.print("<td align='center' rowspan='3' colspan='4'><b class='rojo'>" + obj_control_espesor[64] + "</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>PS1</b></td>");
                        for (int j = 0; j < 20; j++) {
                            if ((Double) obj_control_espesor[(ps_1 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_1 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_1 + j)] == 0) ? "" : obj_control_espesor[(ps_1 + j)]) + "</b></td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>PS2</b></td>");
                        for (int j = 0; j < 20; j++) {
                            if ((Double) obj_control_espesor[(ps_2 + j)] >= pared_sencilla_min && (Double) obj_control_espesor[(ps_2 + j)] <= pared_sencilla_max) {
                                out.print("<td align='center'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</td>");
                            } else {
                                out.print("<td align='center'><b class='rojo'>" + (((Double) obj_control_espesor[(ps_2 + j)] == 0) ? "" : obj_control_espesor[(ps_2 + j)]) + "</b></td>");
                            }
                        }
                        out.print("</tr>");
//                        // </editor-fold>
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    // </editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="AMERICIO R-GC-097">
                else if (pageContext.getRequest().getAttribute("Rollo").toString().equals("Americio")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    id_rollo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_rollo").toString());
                    lst_rollo = jpacrlo.Traer_rollo_id_producto(id_producto, id_rollo);
                    Object[] obj_rollo = (Object[]) lst_rollo.get(0);
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registro Americio Rollo " + obj_rollo[2] + "</h3>");
                    if (Integer.parseInt(obj_registro[15].toString()) == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) {
                        out.print("<form action='Rollo?opc=7' method='post' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                + "<input type='hidden' name='odn' value='" + orden + "'>"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                + "<input type='hidden' name='rlo' value='" + id_rollo + "'>");
                        out.print("<b>Micrometro digital :</b>");
                        out.print("<input type='text' name='Txt_micrometro_digital' id='Txt_micrometro_digital' placeholder='Micrometro digital' title='Micrometro digital' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + ((obj_rollo[20] == null) ? "" : obj_rollo[20]) + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_micrometro_digital');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "</script>");
                        out.print("<b>Sensor espesor :</b>");
                        out.print("<input type='text' name='Txt_sensor_espesor' id='Txt_sensor_espesor' placeholder='Sensor espesor' title='Sensor espesor' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + ((obj_rollo[21] == null) ? "" : obj_rollo[21]) + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sensor_espesor');"
                                + "val1.add(Validate.Presence);"
                                + "val1.add(Validate.Decimal);"
                                + "</script>");
                        out.print("<b>Tensión :</b>");
                        out.print("<input type='text' name='Txt_tension' id='Txt_tension' placeholder='Tensión' title='Tensión' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + ((obj_rollo[22] == null) ? "" : obj_rollo[22].toString().toUpperCase()) + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tension');"
                                + "val1.add(Validate.Presence);"
                                + "</script>");
                        out.print("<b>Observaciones :</b>");
                        out.print("<textarea style='height:100px' type='text' name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones al iniciar la orden de producción' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' >"
                                + "" + ((obj_rollo[23] == null) ? "ROLLO EN PANTALLA " + ((Integer) obj_rollo[2] + 1) + "" : obj_rollo[23]) + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    } else {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    //flotante factor de medida
                    out.print("<script language='Javascript'>");
                    out.print("function mostrar() {");
                    out.print("var panel, mostrar;");
                    out.print("panel = document.getElementById('Factor_medida');");
                    out.print("if(panel.style.visibility == 'hidden') {");
                    out.print("panel.style.visibility = 'visible';");
                    out.print("mostrar = document.getElementById('mostrar').childNodes[0];");
                    out.print("document.getElementById('cambiar').src = 'Interfaz/Contenido/Iconos/Min.png';");
                    out.print("document.getElementById('cambiar').title = 'Cancelar';");
                    out.print("}else {");
                    out.print("panel.style.visibility = 'hidden';");
                    out.print("mostrar = document.getElementById('mostrar').childNodes[0];");
                    out.print("document.getElementById('cambiar').src = 'Interfaz/Contenido/Iconos/Plus.png';");
                    out.print("}");
                    out.print("}");
                    out.print("</script>");
                    //FIN flotante factor de medida
                    out.print("<div style='float:left'><form action='Rollo?opc=1' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a registros' /></a>"
                            + "</form></div>");
                    out.print("<div style='float:right'>"
                            + "<h3>Factor de medida<img onclick=\"javascript:document.getElementById('Registro_factor').style.display='block'\" src='Interfaz/Contenido/Iconos/Plus.png' width='25px' height='25px' alt='edit' title='Factor de medida' /></h3></div><br />"
                            + "<br /><br /><br /><div style='float:right'><a onclick=\"tableToExcel('Excel', 'R-PI-011')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF</div>");
                    lst_factor_medida = jpacfmd.Factores_medida(id_producto);
                    String fecha_registro = obj_registro[2].toString().replace("-", "");
                    int fecha_convert = Integer.parseInt(fecha_registro);
                    if (lst_factor_medida != null) {
                        out.print("<div class='sweet-local' id='Registro_factor' style='opacity: 1.03; display: none;'>");
                        out.print("<fieldset class='popup_local' style='width:700px;position: absolute;top: 50px;left: 25%;'>");
                        out.print("<div style='float:right'><img onclick=\"javascript:document.getElementById('Registro_factor').style.display='none'\" src='Interfaz/Contenido/Iconos/Delete.png' width='22px' height='22px' alt='edit' title='Factor de medida' /></div>");
                        out.print("<h3>Factor de medida</h3>");
                        out.print("<form action='Registro?opc=30' method='post' name='FormFactor' id='FormFactor' onsubmit='checkSubmit();'>");
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Factor de medida</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Fecha</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_factor_medida.size(); i++) {
                            Object[] obj_factor_medida = (Object[]) lst_factor_medida.get(i);
                            if (i == 0) {
                                out.print("<tr class='verde'>");
                                factor_medida_actual = Double.parseDouble(obj_factor_medida[2].toString());
                            } else {
                                out.print("<tr>");
                            }
                            out.print("<td>" + obj_factor_medida[2] + "</td>");
                            out.print("<td>" + obj_factor_medida[3] + "</td>");
                            out.print("<td>" + obj_factor_medida[8] + "</td>");
                            out.print("<tr>");
                            out.print("</tr>");

                        }
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
                    lst_rollos = jpacrlo.Traer_rollos_id_producto(id_producto);
                    if (lst_rollos == null) {
                    } else {

                        out.print("<table class='table' style='width:100%'>");
                        if (fecha_convert >= 20160101) {
                            out.print("<tr>");
                            out.print("<td colspan='10' style='background-color:#979595;;border-radius:10px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("</tr>");
                        }
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        if (fecha_convert >= 20160101) {
                            out.print("<td colspan='5' align='center'><b class='negro'>REGISTRO</b></td>");
                        } else {
                            out.print("<td colspan='5' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                        }
                        out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-GC-097</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='5' align='center'><b class='negro'>VERIFICACION DE LECTURA <br />MEDIDOR RADIOACTIVO</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 5</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Orden</b></td>");
                        out.print("<td align='center'>" + obj_registro[21] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Producto</b></td>");
                        out.print("<td colspan='4'>" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                        out.print("<td align='center'><b>Máquina</b></td>");
                        out.print("<td align='center'>" + obj_registro[9] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>#Rollo</th>");
                        out.print("<th>Micrometro digital</th>");
                        out.print("<th>Sensor Espesor</th>");
                        out.print("<th>Diferencia</th>");
                        out.print("<th>Tensión</th>");
                        out.print("<th colspan='3'>Observaciones</th>");
                        out.print("<th colspan='2'>Modificar</th>");
                        out.print("</tr>");
                        contador = 0;
                        for (int i = 0; i < lst_rollos.size(); i++) {
                            Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                            if ((Integer) obj_rollos[2] == 1 || (Integer) obj_rollos[2] % (Integer) obj_registro[52] == 0) {
                                out.print("<tr>");
                                if (obj_rollos[20] == null) {
                                    out.print("<td align='center'><b>" + obj_rollos[2] + "</b></td>");
                                    out.print("<td colspan='7' align='center'><b class='naranja'>Sin datos</b></td>");
                                    contador++;
                                } else {
                                    out.print("<td align='center'><b>" + obj_rollos[2] + "</b></td>");
                                    out.print("<td align='center'>" + obj_rollos[20] + "</b></td>");
                                    out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                    try {
                                        resultado = mtdetd.Diferencia_americio((Double) obj_rollos[20], (Double) obj_rollos[21]);
                                        if (resultado <= 6) {
                                            out.print("<td align='center'><b class='verde'>" + resultado + "</b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='rojo'>" + resultado + "</b></td>");
                                        }
                                    } catch (Exception e) {
                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
                                    }
                                    out.print("<td align='center'>" + obj_rollos[22] + "</td>");
                                    out.print("<td colspan='3' align='center'>" + obj_rollos[23] + "</td>");
                                    contador++;
                                }
                                if (Integer.parseInt(obj_registro[15].toString()) == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) {
                                    out.print("<td align='center' colspan='2'>"
                                            + "<form action='Rollo?opc=6' method='post' name='Form97" + i + "' id='Form97" + i + "' onsubmit='checkSubmit();'>"
                                            + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                            + "<input type='hidden' name='odn' value='" + orden + "'>"
                                            + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                            + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                            + "<a href='JAVASCRIPT:Form97" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='25px' height='25px' alt='edit' title='Actualizar americio' /></a>"
                                            + "</form></td>");
                                } else {
                                    out.print("<td colspan='2' align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='25px' height='25px' alt='edit' title='Sin permisos' /></a></td>");
                                }
                                out.print("</tr>");
                                if (contador == 4) {
                                    try {
                                        resultados = mtdetd.Resumen_factor_medida(id_producto);
                                        out.print("<tr >");
                                        out.print("<th>Promedios</th>");
                                        out.print("<td style='background-color:#ddd;' align='center'><b class='negro'>" + resultados.split("/")[0] + "</b></td>");
                                        out.print("<td style='background-color:#ddd;' align='center'><b class='negro'>" + resultados.split("/")[1] + "</b></td>");
                                        resultado = mtdetd.Nuevo_factor_medida(Double.parseDouble(resultados.split("/")[0]), Double.parseDouble(resultados.split("/")[1]), factor_medida_actual);
                                        if (Double.parseDouble(resultados.split("/")[2]) > 6) {
                                            if (Integer.parseInt(obj_registro[14].toString()) == 1) {
                                                out.print("<td style='background-color:#ddd;' align='center'><b class='rojo'>" + resultados.split("/")[2] + "</b></td>");
                                                out.print("<form action='Rollo?opc=9' method='post' onsubmit='checkSubmit();'>"
                                                        + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                                        + "<input type='hidden' name='odn' value='" + orden + "'>"
                                                        + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                                        + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                                        + "<input type='hidden' name='mcm' value='" + resultados.split("/")[0] + "'>"
                                                        + "<input type='hidden' name='ssr' value='" + resultados.split("/")[1] + "'>"
                                                        + "<input type='hidden' name='dfr' value='" + resultados.split("/")[2] + "'>"
                                                        + "<td align='center' colspan='5' style='background-color:#ddd;'>"
                                                        + "<b class='rojo'> ( " + resultados.split("/")[0] + " / " + resultados.split("/")[1] + " ) X " + factor_medida_actual + " = "
                                                        + "<input type='text' id='Txt_factor_medida' name='Txt_factor_medida' style='text-align:center;width:30px;margin:0;border-color:none;font-size:11px;' value='" + resultado + "' placeholder='" + resultado + "'/>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_factor_medida');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script><br />"
                                                        + "<textarea type='text' id='Txt_observacion' name='Txt_observacion' onchange='javascript:this.value=this.value.toUpperCase();' style='width:500px;height:20px;margin:0;font-size:11px;'>"
                                                        + "CAMBIO DE FACTOR DE MEDIDA POR DIFERENCIA ENTRE SENSOR Y MICRÓMETRO EN LOS ROLLOS " + resultados.split("/")[3] + " SUPERA LAS 6 &micro;m.</textarea>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observacion');val1.add(Validate.Presence);</script>"
                                                        + "</b></td>");
                                                out.print("<td align='center'><div class='myButton'><input type='submit' value=''></div></td></form>");
                                            } else {
                                                out.print("<td style='background-color:#ddd;' align='center'><b class='rojo'>" + resultados.split("/")[2] + "</b></td>");
                                                out.print("<td align='center' colspan='6' style='background-color:#ddd;'><b class='rojo'> ( " + resultados.split("/")[0] + " / " + resultados.split("/")[1] + " ) X " + factor_medida_actual + " = " + resultado + "<br />"
                                                        + "CAMBIO DE FACTOR DE MEDIDA POR DIFERENCIA ENTRE SENSOR Y MICRÓMETRO EN LOS ROLLOS " + resultados.split("/")[3] + " SUPERA LAS 6 &micro;m.</b></td>");
                                            }
                                        } else {
                                            out.print("<td style='background-color:#ddd;' align='center'><b class='verde'>" + resultados.split("/")[2] + "</b></td>");
                                            out.print("<td align='center' colspan='6' style='background-color:#ddd;'><b class='verde'> ( " + resultados.split("/")[0] + " / " + resultados.split("/")[1] + " ) X " + factor_medida_actual + " = " + resultado + "</b></td>");
                                        }
                                        out.print("</tr>");
                                    } catch (Exception e) {
                                        out.print("<tr>");
                                        out.print("<th>Promedios</th>");
                                        out.print("<td colspan='9' style='background-color:#ddd;' align='center'><b class='negro'>Error en resultados.</b></td>");
                                        out.print("</tr>");
                                    }
                                }
                            }
                        }
                        out.print("</table>");
                        //TABLA OCULTA
                        out.print("<div style='display:none;background-color:#fff'>");
                        out.print("<div id='Imprimir'>");
                        out.print("<table class='table' id='Excel' style='width:100%'>");
                        if (fecha_convert >= 20160101) {
                            out.print("<tr>");
                            out.print("<td colspan='10' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='5' align='center'><b class='negro'>MANUAL DE REGISTROS</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>CODIGO<br />R-GC-097</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='5' align='center'><b class='negro'>VERIFICACION DE LECTURA <br />MEDIDOR RADIOACTIVO</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>VERSION 5</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>ORDEN</b></td>");
                        out.print("<td align='center'>" + obj_registro[21] + "</td>");
                        out.print("<td align='center'><b>PRODUCTO</b></td>");
                        out.print("<td colspan='4'>" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                        out.print("<td align='center'><b>MÁQUINA</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[9] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>#ROLLO</th>");
                        out.print("<th>MICROMETRO DIGITAL</th>");
                        out.print("<th>SENSOR ESPESOR</th>");
                        out.print("<th>DIFERENCIA</th>");
                        out.print("<th>TENSIÓN</th>");
                        out.print("<th colspan='3'>OBSERVACIONES</th>");
                        out.print("<th colspan='2'>RESPONSABLE</th>");
                        out.print("</tr>");
                        contador = 0;
                        for (int i = 0; i < lst_rollos.size(); i++) {
                            Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                            if ((Integer) obj_rollos[2] == 1 || (Integer) obj_rollos[2] % (Integer) obj_registro[52] == 0) {
                                out.print("<tr>");
                                if (obj_rollos[20] == null) {
                                    out.print("<td align='center'><b>" + obj_rollos[2] + "</b></td>");
                                    out.print("<td colspan='9' align='center'><b class='naranja'>Sin datos</b></td>");
                                    contador++;
                                } else {
                                    out.print("<td align='center'><b>" + obj_rollos[2] + "</b></td>");
                                    out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                    out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                    try {
                                        resultado = mtdetd.Diferencia_americio((Double) obj_rollos[20], (Double) obj_rollos[21]);
                                        if (resultado <= 6) {
                                            out.print("<td align='center'><b class='verde'>" + resultado + "</b></td>");
                                        } else {
                                            out.print("<td align='center'><b class='rojo'>" + resultado + "</b></td>");
                                        }
                                    } catch (Exception e) {
                                        out.print("<td align='center'><b class='naranja'>---</b></td>");
                                    }
                                    out.print("<td align='center'>" + obj_rollos[22] + "</td>");
                                    out.print("<td colspan='3'>" + obj_rollos[23] + "</td>");
                                    out.print("<td colspan='2' align='center'>" + obj_rollos[24].toString().split("/")[1] + "</td>");
                                    contador++;
                                }
                                out.print("</tr>");
                                if (contador == 4) {
                                    try {
                                        resultados = mtdetd.Resumen_factor_medida(id_producto);
                                        out.print("<tr >");
                                        out.print("<th>Promedios</th>");
                                        out.print("<td style='background-color:#ddd;' align='center'><b class='negro'>" + resultados.split("/")[0] + "</b></td>");
                                        out.print("<td style='background-color:#ddd;' align='center'><b class='negro'>" + resultados.split("/")[1] + "</b></td>");
                                        resultado = mtdetd.Nuevo_factor_medida(Double.parseDouble(resultados.split("/")[0]), Double.parseDouble(resultados.split("/")[1]), factor_medida_actual);
                                        if (Double.parseDouble(resultados.split("/")[2]) > 6) {
                                            if (Integer.parseInt(obj_registro[14].toString()) == 1) {
                                                out.print("<td style='background-color:#ddd;' align='center'><b class='rojo'>" + resultados.split("/")[2] + "</b></td>");
                                                out.print("<td align='center' colspan='6' style='background-color:#ddd;'><b class='rojo'> ( " + resultados.split("/")[0] + " / " + resultados.split("/")[1] + " ) X " + factor_medida_actual + " = " + resultado + "<br />"
                                                        + "CAMBIO DE FACTOR DE MEDIDA POR DIFERENCIA ENTRE SENSOR Y MICRÓMETRO EN LOS ROLLOS " + resultados.split("/")[3] + " SUPERA LAS 6 &micro;m.</b></td>");
                                            } else {
                                                out.print("<td style='background-color:#ddd;' align='center'><b class='rojo'>" + resultados.split("/")[2] + "</b></td>");
                                                out.print("<td align='center' colspan='6' style='background-color:#ddd;'><b class='rojo'> ( " + resultados.split("/")[0] + " / " + resultados.split("/")[1] + " ) X " + factor_medida_actual + " = " + resultado + "<br />"
                                                        + "CAMBIO DE FACTOR DE MEDIDA POR DIFERENCIA ENTRE SENSOR Y MICRÓMETRO EN LOS ROLLOS " + resultados.split("/")[3] + " SUPERA LAS 6 &micro;m.</b></td>");
                                            }
                                        } else {
                                            out.print("<td style='background-color:#ddd;' align='center'><b class='verde'>" + resultados.split("/")[2] + "</b></td>");
                                            out.print("<td align='center' colspan='6' style='background-color:#ddd;'><b class='verde'> ( " + resultados.split("/")[0] + " / " + resultados.split("/")[1] + " ) X " + factor_medida_actual + " = " + resultado + "</b></td>");
                                        }
                                        out.print("</tr>");
                                    } catch (Exception e) {
                                        out.print("<tr>");
                                        out.print("<th>Promedios</th>");
                                        out.print("<td colspan='9' style='background-color:#ddd;' align='center'><b class='negro'>Error en resultados.</b></td>");
                                        out.print("</tr>");
                                    }
                                }
                            }
                        }
                        out.print("</table>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="EVENTOS ROLLO">
                else if (pageContext.getRequest().getAttribute("Rollo").toString().equals("Eventos")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    id_rollo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_rollo").toString());
                    filtro = pageContext.getRequest().getAttribute("Tipo").toString();
                    estria_ventana = Integer.parseInt(pageContext.getRequest().getAttribute("Estria_ventana").toString());
                    if (filtro.equals("NORMAL")) {
                        lst_rollo = jpacrlo.Traer_rollo_id_registro(id_registro, id_rollo);
                    } else {
                        lst_rollo = jpacrev.Traer_rollos_id_rollo(id_rollo);
                    }
                    Object[] obj_rollo = (Object[]) lst_rollo.get(0);
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    out.print("<div id='sidebar'>");
                    if (filtro.equals("NORMAL")) {
                        out.print("<h3>Registrar evento rollo " + ((obj_rollo[28].toString().equals("0")) ? obj_rollo[2] : obj_rollo[28]) + "</h3>");
                    } else {
                        out.print("<h3>Registrar evento rollo " + obj_rollo[2] + "</h3>");
                    }
                    if (Integer.parseInt(obj_registro[15].toString()) == 1 && !(rol.equals("Operario_extrusion") || rol.equals("Coordinador_extrusion") || rol.equals("Consulta"))) {
                        out.print("<form action='Rollo?opc=11' method='post' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                + "<input type='hidden' name='rlo' value='" + id_rollo + "' />");
                        out.print("<input type='hidden' name='fto' value='" + ((filtro.equals("NORMAL")) ? filtro : "ESPECIAL") + "' />"
                                + "<input type='hidden' name='etvt' value='" + estria_ventana + "' />");
                        out.print("<b>Estado de calidad actual :</b><br />");
                        if (obj_rollo[3].equals("A")) {
                            out.print("<b class='negro'>APROBADO</b><br /><br />");
                        } else if (obj_rollo[3].equals("C")) {
                            out.print("<b class='negro'>CUARENTENA</b><br /><br />");
                        } else if (obj_rollo[3].equals("R")) {
                            out.print("<b class='negro'>RECHAZADO</b><br /><br />");
                        } else if (obj_rollo[3].equals("S")) {
                            out.print("<b class='negro'>SIN ASIGNAR</b><br /><br />");
                        }
                        out.print("<b>Estado de calidad :</b>");
                        out.print("<select name='Cbx_estado_calidad' id='Cbx_estado_calidad' title='Estado de calidad'>");
                        out.print("<option value='0' >Seleccionar estado de calidad</option>");
                        if (obj_rollo[3].equals("A")) {
                            out.print("<option value='A' selected>Aprobado</option>");
                            out.print("<option value='C' >Cuarentena</option>");
                            out.print("<option value='R' >Rechazado</option>");
                        } else if (obj_rollo[3].equals("C")) {
                            out.print("<option value='A' >Aprobado</option>");
                            out.print("<option value='C' selected>Cuarentena</option>");
                            out.print("<option value='R' >Rechazado</option>");
                        } else if (obj_rollo[3].equals("R")) {
                            out.print("<option value='A' >Aprobado</option>");
                            out.print("<option value='C' >Cuarentena</option>");
                            out.print("<option value='R' selected>Rechazado</option>");
                        } else {
                            out.print("<option value='A' >Aprobado</option>");
                            out.print("<option value='C' >Cuarentena</option>");
                            out.print("<option value='R' >Rechazado</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_estado_calidad');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Justificación :</b>");
                        out.print("<textarea style='height:100px' name='Txt_justificacion' id='Txt_justificacion' placeholder='Justificación' title='Justificación' onkeyup='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_justificacion');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    } else {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    out.print("<form action='Rollo?opc=" + ((filtro.equals("NORMAL")) ? "1" : "19") + "' method='post' name='FormVolver' id='FormVolver' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<input type='hidden' name='etvt' value='" + estria_ventana + "' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png'  alt='edit' title='Volver a registros' /></a>"
                            + "</form>");
                    out.print("<h3>Eventos estado de calidad rollo " + obj_rollo[2] + "</h3>");
                    if (filtro.equals("NORMAL")) {
                        lst_eventos = jpacevt.Eventos_estado_calidad_id_rollo(id_rollo, filtro);
                    } else {
                        lst_eventos = jpacevt.Eventos_estado_calidad_id_rollo_estria_ventana(id_rollo, filtro);
                    }
                    if (lst_eventos == null) {
                    } else {
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Fecha</th>");
                        out.print("<th>Responsable</th>");
                        out.print("<th>Estado de calidad</th>");
                        out.print("<th>Justificación</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_eventos.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_eventos = (Object[]) lst_eventos.get(i);
                            out.print("<td align='center'>" + obj_eventos[6] + "</td>");
                            out.print("<td>" + obj_eventos[5] + "</td>");
                            if (obj_eventos[3].equals("A")) {
                                out.print("<td align='center'><b class='verde'>Aprobado</b></td>");
                            } else if (obj_eventos[3].equals("C")) {
                                out.print("<td align='center'><b class='amarillo'>Cuarentena</b></td>");
                            } else if (obj_eventos[3].equals("R")) {
                                out.print("<td align='center'><b class='rojo'>Rechazado</b></td>");
                            }
                            out.print("<td>" + obj_eventos[4] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ROLLO R-PI-023 Y R-GC-052">
                else if (pageContext.getRequest().getAttribute("Rollo").toString().equals("Registro_rollo_estria_ventana")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    orden = pageContext.getRequest().getAttribute("Orden_produccion").toString();
                    id_producto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_producto").toString());
                    id_rollo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_rollo").toString());
                    estria_ventana = Integer.parseInt(pageContext.getRequest().getAttribute("Estria_ventana").toString());
                    Date fecha = new Date();
                    String fecha_actual = (fecha.getYear() + 1900) + "" + (fecha.getMonth() <= 9 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() <= 9 ? "-0" : "-") + "" + fecha.getDate();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    lst_registro = jpacrgt.Traer_registro_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    lst_rollo_siguiente = jpacrev.Traer_ultimo_rollo(id_producto);
                    Object[] obj_rollo = null;
                    if (id_rollo != 0) {
                        lst_rollo = jpacrev.Traer_rollos_id_rollo(id_rollo);
                        obj_rollo = (Object[]) lst_rollo.get(0);
                    }
                    String fecha_registro = obj_registro[2].toString().replace("-", "");
                    int fecha_convert = Integer.parseInt(fecha_registro);
                    // <editor-fold defaultstate="collapsed" desc="CONSULTA ROLLOS">
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="VOLVER">
                    out.print("<form action='Orden?opc=6' method='post' name='FormVolver' id='FormVer' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='tcs' value='0' />"
                            + "<input type='hidden' name='irg' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='edit' title='Volver a registros' /></a> Volver"
                            + "</form>");
//</editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="REGISTRO ROLLO">
//                    out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
//                    out.print("<fieldset class='popup_local' style='width:" + ((estria_ventana == 2) ? "1000px" : "600px") + ";position: absolute;" + ((estria_ventana == 2) ? "top: 50px;left: 1%" : "top: 25px;left: 20%") + ";'>");
                    int registro_calidad = 0;
                    if ((Integer) obj_registro[14] == 1 && !rol.equals("Consulta")) {
                        if (id_rollo != 0) {
                            if (!(rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad") || rol.equals("Administrador")) && ((Integer) obj_rollo[2] == 1 || (Integer) obj_rollo[2] % (Integer) obj_registro[52] == 0)) {
                                registro_calidad++;
                                out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                                out.print("<fieldset class='popup_local' style='width:400px;position: absolute;top: 25px;left: 20%;'>");
                            } else {
                                registro_calidad = 0;
                                out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                                out.print("<fieldset class='popup_local' style='width:" + ((estria_ventana == 2) ? "1000px" : "600px") + ";position: absolute;" + ((estria_ventana == 2) ? "top: 50px;left: 1%" : "top: 25px;left: 20%") + ";'>");
                            }
                            out.print("<div align='right'>"
                                    + "<form action='Rollo?opc=19" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' method='post' name='FormCancelar' id='FormCancelar' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                                    + "<input type='hidden' name='odn' value='" + orden + "' />"
                                    + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                    + "<input type='hidden' name='rlo' value='0' />"
                                    + "<input type='hidden' name='fto' value='' />"
                                    + "<a href='JAVASCRIPT:FormCancelar.submit()'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Limpiar registro turno' /></a>"
                                    + "</form>"
                                    + "</div>");
                            out.print("<h3>Modificar Rollo " + (((Integer) obj_rollo[2] == 1 || (Integer) obj_rollo[2] % (Integer) obj_registro[52] == 0) ? "Calidad" : "") + " #" + obj_rollo[2] + "</h3>");
                        } else if (lst_rollo_siguiente != null) {
                            Object[] obj_rollo_siguiente = (Object[]) lst_rollo_siguiente.get(0);
                            if ((((Integer) obj_rollo_siguiente[0] + 1) == 1 || ((Integer) obj_rollo_siguiente[0] + 1) % (Integer) obj_registro[52] == 0)) {
                                registro_calidad++;
                                out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                                out.print("<fieldset class='popup_local' style='width:400px;position: absolute;top: 25px;left: 20%;'>");
                            } else {
                                registro_calidad = 0;
                                out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                                out.print("<fieldset class='popup_local' style='width:" + ((estria_ventana == 2) ? "1000px" : "600px") + ";position: absolute;" + ((estria_ventana == 2) ? "top: 50px;left: 1%" : "top: 25px;left: 20%") + ";'>");
                            }
                            out.print("<div align='right'><img onclick='JAVASCRIPT:document.getElementById(\"Registro_rollo_" + obj_registro[0] + "\").style.display=\"none\";' src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Cerrar' /></div>");
                            out.print("<h3>Registrar Rollo " + ((((Integer) obj_rollo_siguiente[0] + 1) == 1 || ((Integer) obj_rollo_siguiente[0] + 1) % (Integer) obj_registro[52] == 0) ? "Calidad" : "") + " #" + ((Integer) obj_rollo_siguiente[0] + 1) + "</h3>");
                        } else {
                            if (!(rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad") || rol.equals("Administrador"))) {
                                registro_calidad++;
                                out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                                out.print("<fieldset class='popup_local' style='width:400px;position: absolute;top: 25px;left: 20%;'>");
                            } else {
                                registro_calidad = 0;
                                out.print("<div class='sweet-local' id='Registro_rollo_" + obj_registro[0] + "' style='opacity: 1.03; display: " + ((id_rollo != 0) ? "block" : "none") + ";'>");
                                out.print("<fieldset class='popup_local' style='width:" + ((estria_ventana == 2) ? "1000px" : "600px") + ";position: absolute;" + ((estria_ventana == 2) ? "top: 50px;left: 1%" : "top: 25px;left: 20%") + ";'>");
                            }
                            out.print("<div align='right'><img onclick='JAVASCRIPT:document.getElementById(\"Registro_rollo_" + obj_registro[0] + "\").style.display=\"none\";' src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Cerrar' /></div>");
                            out.print("<h3>Registrar Rollo Calidad #1</h3>");
                        }
//                        out.print("CC" + registro_calidad);
                        out.print("<input type='hidden' id='Txt_pared_doble_min' value='" + ((Double) obj_registro[28] - (Double) obj_registro[30]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_doble_max' value='" + ((Double) obj_registro[28] + (Double) obj_registro[29]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_doble_estria_min' value='" + ((Double) obj_registro[58] - (Double) obj_registro[60]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_doble_estria_max' value='" + ((Double) obj_registro[58] + (Double) obj_registro[59]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_sencilla_min' value='" + ((Double) obj_registro[31] - (Double) obj_registro[33]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_sencilla_max' value='" + ((Double) obj_registro[31] + (Double) obj_registro[32]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_sencilla_estria_min' value='" + ((Double) obj_registro[61] - (Double) obj_registro[63]) + "' />");
                        out.print("<input type='hidden' id='Txt_pared_sencilla_estria_max' value='" + ((Double) obj_registro[61] + (Double) obj_registro[62]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_ventana_min' value='" + ((Double) obj_registro[64] - (Double) obj_registro[66]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_ventana_max' value='" + ((Double) obj_registro[64] + (Double) obj_registro[65]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_manga_min' value='" + ((Double) obj_registro[34] - (Double) obj_registro[36]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_manga_max' value='" + ((Double) obj_registro[34] + (Double) obj_registro[35]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_bobina_min' value='" + ((Double) obj_registro[37] - (Double) obj_registro[39]) + "' />");
                        out.print("<input type='hidden' id='Txt_ancho_bobina_max' value='" + ((Double) obj_registro[37] + (Double) obj_registro[38]) + "' />");
                        out.print("<input type='hidden' id='Txt_centrado_ventana_min' value='" + obj_registro[68] + "' />");
                        out.print("<input type='hidden' id='Txt_peso_min' value='" + ((Double) obj_registro[46] - (Double) obj_registro[48]) + "' />");
                        out.print("<input type='hidden' id='Txt_peso_max' value='" + ((Double) obj_registro[46] + (Double) obj_registro[47]) + "' />");
                        out.print("<input type='hidden' id='Txt_peso_bruto_max' value='" + ((Double) obj_registro[46] + (Double) obj_registro[47] + (Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + "' />");
                        out.print("<input type='hidden' id='Txt_max_tolerancia' value='1000' />");
                        out.print("<input type='hidden' id='Txt_max_tolerancia' value='1000' />");
                        //out.print("<input type='hidden' id='Action_form_rollo' value='Rollo?opc=20" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' />");
                        out.print("<form action='Rollo?opc=20" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' method='post' id='Form_" + ((id_rollo != 0) ? "modificar" : "registro") + "_edicion_rollos' onsubmit='checkSubmit();'>"
                                + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                                + "<input type='hidden' name='odn' value='" + orden + "' />"
                                + "<input type='hidden' name='ipd' value='" + id_producto + "' />");
                        if (id_rollo != 0) {
                            out.print("<input type='hidden' name='Id_rollo' value='" + obj_rollo[0] + "' />"
                                    + "<input type='hidden' name='Rollo' value='" + obj_rollo[2] + "' />");
                            out.print("<div style='display:none'><input type='text' id='Control_envio' required value='' /></div>");
                        } else {
                            out.print("<input type='hidden' name='Id_rollo' value='0' />");
                        }
                        out.print("<input type='hidden' id='Detalle_defecto' name='Detalle_defecto' value='' />");
                        out.print("<table>");
                        out.print("<tr>");
                        long mult_2 = (long) Math.pow(10, 2);
                        double resultado_peso = 0;
                        if (id_rollo > 0) {
                            resultado_peso = (Double) obj_rollo[21] + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]);
                            resultado_peso = (Math.round(resultado_peso * mult_2)) / (double) mult_2;
                        }
                        if (registro_calidad > 0) {
                            //<editor-fold defaultstate="collapsed" desc="PESO">
                            out.print("<td valign='top'><h3>Ancho</h3>");
                            out.print("<b>Ancho de bobina :</b>");
                            out.print("<input type='text' name='Txt_ancho_bobina' id='Txt_ancho_bobina' placeholder='Ancho de bobina' title='Ancho de bobina' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                    + "value='" + ((id_rollo == 0) ? "" : obj_rollo[20]) + "' "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_ancho_bobina','Txt_ancho_bobina_min','Txt_ancho_bobina_max','Txt_peso_bruto');\" />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_bobina_min'} );"
                                    //                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_bobina_max'} );"
                                    + "</script>");
                            out.print("<br /><b>Particulas:</b><br />");
                            out.print("<select name='Cbx_particula' id='Cbx_particula' title='Particulas'>");
                            out.print("<option value='0' >Seleccionar</option>");
                            if (id_rollo == 0) {
                                out.print("<option value='NO' >NO</option>");
                                out.print("<option value='SI' >SI</option>");
                            } else {
                                out.print("<option value='NO' " + ((obj_rollo[22].equals("NO")) ? "selected" : "") + ">NO</option>");
                                out.print("<option value='SI' " + ((obj_rollo[22].equals("SI")) ? "selected" : "") + ">SI</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_particula');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                            out.print("<td valign='top'><h3>Peso</h3>");
                            out.print("<b>Peso bruto :</b><br />");
                            //out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' onkeyup='javascript:this.value=this.value.toUpperCase();' onchange='Peso(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ")'/>"
                            out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' "
                                    + "value='" + ((id_rollo == 0) ? "" : resultado_peso) + "' "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Peso_pp(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ");Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_peso_bruto','Txt_peso_min','Txt_peso_bruto_max'" + ((id_rollo == 0) ? ",'Txt_perimetro_1'" : "") + ");\" "
                                    + "" + ((id_rollo == 0) ? "" : "onchange") + "=\"Peso_pp(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ");\" />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_bruto');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_peso_min'} );"
                                    //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_peso_bruto_max'} );"
                                    + "</script>");
                            out.print("<br /><b>Peso Neto :</b><br />");
                            out.print("<input type='text' name='Txt_peso_neto' id='Txt_peso_neto' placeholder='Peso Neto' title='Peso Neto' "
                                    + "" + ((id_rollo == 0) ? "onkeyup='javascript:this.value=this.value.toUpperCase();' readonly='true' value='0'" : " value='" + obj_rollo[21] + "' readonly='true'") + " "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_peso_neto','Txt_peso_min','Txt_peso_max'" + ((id_rollo == 0) ? ",'Txt_perimetro_1'" : "") + ");\" />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_neto');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_peso_min'} );"
                                    //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_peso_max'} );"
                                    + "</script>");
                            out.print("</td>");
                            out.print("<input type='hidden' name='Txt_primer_extremo' value='" + ((id_rollo == 0) ? "0" : obj_rollo[4]) + "' />");
                            out.print("<input type='hidden' name='Txt_centro' value='" + ((id_rollo == 0) ? "0" : obj_rollo[5]) + "' />");
                            out.print("<input type='hidden' name='Txt_segundo_extremo' value='" + ((id_rollo == 0) ? "0" : obj_rollo[6]) + "' />");
                            out.print("<input type='hidden' name='Txt_primer_extremo_estria' value='" + ((id_rollo == 0) ? "0" : obj_rollo[7]) + "' />");
                            out.print("<input type='hidden' name='Txt_centro_estria' value='" + ((id_rollo == 0) ? "0" : obj_rollo[8]) + "' />");
                            out.print("<input type='hidden' name='Txt_segundo_extremo_estria' value='" + ((id_rollo == 0) ? "0" : obj_rollo[9]) + "' />");
                            out.print("<input type='hidden' name='Txt_min_pared_sencilla' value='" + ((id_rollo == 0) ? "0" : obj_rollo[10]) + "' />");
                            out.print("<input type='hidden' name='Txt_max_pared_sencilla' value='" + ((id_rollo == 0) ? "0" : obj_rollo[11]) + "' />");
                            out.print("<input type='hidden' name='Txt_min_pared_sencilla_estria' value='" + ((id_rollo == 0) ? "0" : obj_rollo[12]) + "' />");
                            out.print("<input type='hidden' name='Txt_max_pared_sencilla_estria' value='" + ((id_rollo == 0) ? "0" : obj_rollo[13]) + "' />");
                            out.print("<input type='hidden' name='Txt_min_pared_sencilla_frosted' value='" + ((id_rollo == 0) ? "0" : obj_rollo[14]) + "' />");
                            out.print("<input type='hidden' name='Txt_max_pared_sencilla_frosted' value='" + ((id_rollo == 0) ? "0" : obj_rollo[15]) + "' />");
                            out.print("<input type='hidden' name='Txt_ancho_ventana' value='" + ((id_rollo == 0) ? "0" : obj_rollo[18]) + "' />");
                            out.print("<input type='hidden' name='Txt_ancho_manga' value='" + ((id_rollo == 0) ? "0" : obj_rollo[19]) + "' />");
//                            out.print("<input type='hidden' name='Txt_ancho_bobina' value='" + ((id_rollo == 0) ? "0" : obj_rollo[20]) + "' />");
//                            out.print("<input type='hidden' name='Cbx_particula' value='" + ((id_rollo == 0) ? "0" : obj_rollo[22]) + "' />");
                            out.print("<input type='hidden' name='Txt_perimetro_1' value='" + ((id_rollo == 0) ? "0" : obj_rollo[23]) + "' />");
                            out.print("<input type='hidden' name='Txt_perimetro_2' value='" + ((id_rollo == 0) ? "0" : obj_rollo[24]) + "' />");
                            out.print("<input type='hidden' name='Txt_cv_extremo_1' value='" + ((id_rollo == 0) ? "0" : obj_rollo[16]) + "' />");
                            out.print("<input type='hidden' name='Txt_cv_extremo_2' value='" + ((id_rollo == 0) ? "0" : obj_rollo[17]) + "' />");
                            out.print("<input type='hidden' name='Estado_calidad' " + ((id_rollo == 0) ? "value='S' " : "value='" + obj_rollo[3] + "' id='Estado_calidad'") + " />");
                            //</editor-fold>
                        } else {
                            out.print("<input type='hidden' name='Estado_calidad' id='Estado_calidad' value=''/>");
                            if (estria_ventana == 1) {
                                //<editor-fold defaultstate="collapsed" desc="PARED DOBLE ESTRIA">
                                out.print("<td valign='top'><h3>Pared Doble Con Estrias</h3>");
                                out.print("<b>Primer extremo :</b><br />");
                                out.print("<input type='text' name='Txt_primer_extremo_estria' id='Txt_primer_extremo_estria' placeholder='Primer extremo con estria' title='PD 1ª extremo' onkeyup='javascript:this.value=this.value.toUpperCase();' "
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[7]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_primer_extremo_estria','Txt_pared_doble_estria_min','Txt_pared_doble_estria_max'" + ((id_rollo == 0) ? ",'Txt_centro_estria'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_centro_estria);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_primer_extremo_estria');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_estria_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_estria_max'} );"
                                        + "</script>");
                                out.print("<b>Centro :</b><br />");
                                out.print("<input type='text' name='Txt_centro_estria' id='Txt_centro_estria' placeholder='Centro' title='PD Centro con estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[8]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_centro_estria','Txt_pared_doble_estria_min','Txt_pared_doble_estria_max'" + ((id_rollo == 0) ? ",'Txt_segundo_extremo_estria'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_segundo_extremo_estria);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centro_estria');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_estria_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_estria_max'} );"
                                        + "</script>");
                                out.print("<b>Segundo extremo :</b><br />");
                                out.print("<input type='text' name='Txt_segundo_extremo_estria' id='Txt_segundo_extremo_estria' placeholder='Segundo extremo' title='PD 2ª extremo con estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[9]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_segundo_extremo_estria','Txt_pared_doble_estria_min','Txt_pared_doble_estria_max'" + ((id_rollo == 0) ? ",'Txt_min_pared_sencilla_estria'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_min_pared_sencilla_estria);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_segundo_extremo_estria');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_estria_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_estria_max'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="PARED DOBLE VENTANA">
                                out.print("<td valign='top'><h3>Pared Doble</h3>");
                                out.print("<b>Primer extremo :</b><br />");
                                out.print("<input type='text' name='Txt_primer_extremo' id='Txt_primer_extremo' placeholder='Primer extremo' title='PD 1ª extremo' onkeyup='javascript:this.value=this.value.toUpperCase();' "
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[4]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_primer_extremo','Txt_pared_doble_min','Txt_pared_doble_max'" + ((id_rollo == 0) ? ",'Txt_centro'" : "") + ");\"  " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_centro);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_primer_extremo');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                        + "</script>");
                                out.print("<b>Centro :</b><br />");
                                out.print("<input type='text' name='Txt_centro' id='Txt_centro' placeholder='Centro' title='PD centro' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[5]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_centro','Txt_pared_doble_min','Txt_pared_doble_max'" + ((id_rollo == 0) ? ",'Txt_segundo_extremo'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_segundo_extremo);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centro');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                        + "</script>");
                                out.print("<b>Segundo extremo :</b><br />");
                                out.print("<input type='text' name='Txt_segundo_extremo' id='Txt_segundo_extremo' placeholder='Segundo extremo' title='PD 2ª extremo' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[6]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_segundo_extremo','Txt_pared_doble_min','Txt_pared_doble_max'" + ((id_rollo == 0) ? ",'Txt_min_pared_sencilla'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_min_pared_sencilla);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_segundo_extremo');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                            }
                            if (estria_ventana == 1) {
                                //<editor-fold defaultstate="collapsed" desc="PARED SENCILLA ESTRIA">
                                out.print("<td valign='top'><h3>Pared Sencilla Con Estria</h3>");
                                out.print("<b>Min pared sencilla :</b><br />");
                                out.print("<input type='text' name='Txt_min_pared_sencilla_estria' id='Txt_min_pared_sencilla_estria' placeholder='Min. pared sencilla' title='Min. PS con estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[12]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_min_pared_sencilla_estria','Txt_pared_sencilla_estria_min','Txt_pared_sencilla_estria_max'" + ((id_rollo == 0) ? ",'Txt_max_pared_sencilla_estria'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_max_pared_sencilla_estria);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_min_pared_sencilla_estria');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_estria_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_estria_max'} );"
                                        + "</script>");
                                out.print("<b>Max pared sencilla :</b><br />");
                                out.print("<input type='text' name='Txt_max_pared_sencilla_estria' id='Txt_max_pared_sencilla_estria' placeholder='Max. pared sencilla' title='Max. PS con estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[13]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_max_pared_sencilla_estria','Txt_pared_sencilla_estria_min','Txt_pared_sencilla_estria_max'" + ((id_rollo == 0) ? ",'Txt_primer_extremo'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_primer_extremo);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_max_pared_sencilla_estria');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_estria_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_estria_max'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="PARED SENCILLA VENTANA">
                                out.print("<td valign='top'><h3>Pared Sencilla</h3>");
                                out.print("<b>Min transparente:</b><br />");
                                out.print("<input type='text' name='Txt_min_pared_sencilla' id='Txt_min_pared_sencilla' placeholder='Min. ps transparente' title='Min. PS transparente' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[10]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_min_pared_sencilla','Txt_pared_sencilla_min','Txt_pared_sencilla_max'" + ((id_rollo == 0) ? ",'Txt_max_pared_sencilla'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_max_pared_sencilla);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_min_pared_sencilla');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                        + "</script>");
                                out.print("<b>Max transparente :</b><br />");
                                out.print("<input type='text' name='Txt_max_pared_sencilla' id='Txt_max_pared_sencilla' placeholder='Max. ps transparente' title='Max. PS transparente' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[11]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_max_pared_sencilla','Txt_pared_sencilla_min','Txt_pared_sencilla_max'" + ((id_rollo == 0) ? ",'Txt_min_pared_sencilla_frosted'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_min_pared_sencilla_frosted);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_max_pared_sencilla');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                        + "</script>");
                                out.print("<b>Min frosted:</b><br />");
                                out.print("<input type='text' name='Txt_min_pared_sencilla_frosted' id='Txt_min_pared_sencilla_frosted' placeholder='Min. ps frosted' title='Min. PS frosted' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[14]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_min_pared_sencilla_frosted','Txt_pared_sencilla_min','Txt_pared_sencilla_max'" + ((id_rollo == 0) ? ",'Txt_max_pared_sencilla_frosted'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_max_pared_sencilla_frosted);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_min_pared_sencilla_frosted');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                        + "</script>");
                                out.print("<b>Max frosted :</b><br />");
                                out.print("<input type='text' name='Txt_max_pared_sencilla_frosted' id='Txt_max_pared_sencilla_frosted' placeholder='Max. ps frosted' title='Max. PS frosted' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[15]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_max_pared_sencilla_frosted','Txt_pared_sencilla_min','Txt_pared_sencilla_max'" + ((id_rollo == 0) ? ",'Txt_cv_extremo_1'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_cv_extremo_1);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_max_pared_sencilla_frosted');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                            }
                            if (estria_ventana == 1) {
                                //<editor-fold defaultstate="collapsed" desc="PARED DOBLE SIN ESTRIA">
                                out.print("<td valign='top'><h3>Pared Doble Sin Estria</h3>");
                                out.print("<b>Primer extremo :</b><br />");
                                out.print("<input type='text' name='Txt_primer_extremo' id='Txt_primer_extremo' placeholder='Primer extremo' title='PD 1ª extremo sin estria' onkeyup='javascript:this.value=this.value.toUpperCase();' "
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[4]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_primer_extremo','Txt_pared_doble_min','Txt_pared_doble_max'" + ((id_rollo == 0) ? ",'Txt_centro'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_centro);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_primer_extremo');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                        + "</script>");
                                out.print("<b>Centro :</b><br />");
                                out.print("<input type='text' name='Txt_centro' id='Txt_centro' placeholder='Centro' title='PD centro sin estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[5]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_centro','Txt_pared_doble_min','Txt_pared_doble_max'" + ((id_rollo == 0) ? ",'Txt_segundo_extremo'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_segundo_extremo);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centro');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                        + "</script>");
                                out.print("<b>Segundo extremo :</b><br />");
                                out.print("<input type='text' name='Txt_segundo_extremo' id='Txt_segundo_extremo' placeholder='Segundo extremo' title='PD 2ª extremo sin estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[6]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_segundo_extremo','Txt_pared_doble_min','Txt_pared_doble_max'" + ((id_rollo == 0) ? ",'Txt_min_pared_sencilla'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_min_pared_sencilla);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_segundo_extremo');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_doble_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_doble_max'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="PARED SENCILLA SIN ESTRIA">
                                out.print("<td valign='top'><h3>Pared Sencilla Sin Estria</h3>");
                                out.print("<b>Min pared sencilla :</b><br />");
                                out.print("<input type='text' name='Txt_min_pared_sencilla' id='Txt_min_pared_sencilla' placeholder='Min. pared sencilla' title='Min. PS sin estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[10]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_min_pared_sencilla','Txt_pared_sencilla_min','Txt_pared_sencilla_max'" + ((id_rollo == 0) ? ",'Txt_max_pared_sencilla'" : "") + ");\"  " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_max_pared_sencilla);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_min_pared_sencilla');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                        + "</script>");
                                out.print("<b>Max pared sencilla :</b><br />");
                                out.print("<input type='text' name='Txt_max_pared_sencilla' id='Txt_max_pared_sencilla' placeholder='Max. pared sencilla' title='Max. PS sin estria' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[11]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_max_pared_sencilla','Txt_pared_sencilla_min','Txt_pared_sencilla_max'" + ((id_rollo == 0) ? ",'Txt_ancho_manga'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_ancho_manga);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_max_pared_sencilla');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_pared_sencilla_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_pared_sencilla_max'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                            }
                            if (estria_ventana == 1) {
                                out.print("</tr>");
                                out.print("<tr>");
                            }
                            if (estria_ventana == 2) {
                                //<editor-fold defaultstate="collapsed" desc="CENTRADO DE VENTANA">
                                out.print("<td valign='top'><h3>Centrado Ventana</h3>");
                                out.print("<b>Extremo 1 :</b><br />");
                                out.print("<input type='text' name='Txt_cv_extremo_1' id='Txt_cv_extremo_1' placeholder='Extermo 1' title='Centrado de ventana Ext 1' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[16]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_cv_extremo_1','Txt_centrado_ventana_min','Txt_max_tolerancia'" + ((id_rollo == 0) ? ",'Txt_cv_extremo_2'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_cv_extremo_2);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cv_extremo_1');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_centrado_ventana_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_max_tolerancia'} );"
                                        + "</script>");
                                out.print("<b>Extremo 2 :</b><br />");
                                out.print("<input type='text' name='Txt_cv_extremo_2' id='Txt_cv_extremo_2' placeholder='Extermo 2' title='Centrado de ventana Ext 2' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[17]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_cv_extremo_2','Txt_centrado_ventana_min','Txt_max_tolerancia'" + ((id_rollo == 0) ? ",'Txt_ancho_ventana'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_ancho_ventana);\"") + "/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cv_extremo_2');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_centrado_ventana_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_max_tolerancia'} );"
                                        + "</script>");
                                out.print("</td>");
                                //</editor-fold>
                            }
                            //<editor-fold defaultstate="collapsed" desc="ANCHO">
                            out.print("<td valign='top'><h3>Ancho</h3>");
                            if (estria_ventana == 2) {
                                out.print("<b>Ancho de ventana :</b>");
                                out.print("<input type='text' name='Txt_ancho_ventana' id='Txt_ancho_ventana' placeholder='Ancho de manga' title='Ancho de ventana' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[18]) + "' "
                                        + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_ancho_ventana','Txt_ancho_ventana_min','Txt_ancho_ventana_max'" + ((id_rollo == 0) ? ",'Txt_ancho_manga'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_ancho_manga);\"") + " />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_ventana');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        //                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_ventana_min'} );"
                                        //                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_ventana_max'} );"
                                        + "</script>");
                            }
                            out.print("<b>Ancho de manga :</b>");
                            out.print("<input type='text' name='Txt_ancho_manga' id='Txt_ancho_manga' placeholder='Ancho de manga' title='Ancho de manga' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                    + "value='" + ((id_rollo == 0) ? "" : obj_rollo[19]) + "' "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_ancho_manga','Txt_ancho_manga_min','Txt_ancho_manga_max'" + ((id_rollo == 0) ? ",'Txt_ancho_bobina'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_ancho_bobina);\"") + " />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_manga');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_manga_min'} );"
                                    //                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_manga_max'} );"
                                    + "</script>");
                            out.print("<b>Ancho de bobina :</b>");
                            out.print("<input type='text' name='Txt_ancho_bobina' id='Txt_ancho_bobina' placeholder='Ancho de bobina' title='Ancho de bobina' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                    + "value='" + ((id_rollo == 0) ? "" : obj_rollo[20]) + "' "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_ancho_bobina','Txt_ancho_bobina_min','Txt_ancho_bobina_max'" + ((id_rollo == 0) ? ",'Txt_peso_bruto'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_peso_bruto);\"") + " />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho_bobina');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_ancho_bobina_min'} );"
                                    //                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_ancho_bobina_max'} );"
                                    + "</script>");
                            out.print("</td>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="PESO">
                            out.print("<td valign='top'><h3>Peso</h3>");
                            out.print("<b>Peso bruto :</b>");
                            //out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' onkeyup='javascript:this.value=this.value.toUpperCase();' onchange='Peso(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ")'/>"
                            out.print("<input type='text' name='Txt_peso_bruto' id='Txt_peso_bruto' placeholder='Peso Bruto' title='Peso Bruto' "
                                    + "value='" + ((id_rollo == 0) ? "" : resultado_peso) + "' "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Peso_pp(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ");Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_peso_bruto','Txt_peso_min','Txt_peso_bruto_max'" + ((id_rollo == 0) ? ",'Txt_perimetro_1'" : "") + ");\" " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_perimetro_1);\"") + " "
                                    + "" + ((id_rollo == 0) ? "" : "onchange") + "=\"Peso_pp(this," + ((Double) obj_registro[49] + (Double) obj_registro[50] + (Double) obj_registro[51]) + ");\" />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_bruto');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_peso_min'} );"
                                    //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_peso_bruto_max'} );"
                                    + "</script>");
                            out.print("<b>Peso Neto :</b><br />");
                            out.print("<input type='text' name='Txt_peso_neto' id='Txt_peso_neto' placeholder='Peso Neto' title='Peso Neto' "
                                    + "" + ((id_rollo == 0) ? "onkeyup='javascript:this.value=this.value.toUpperCase();' readonly='true' value='0'" : " value='" + obj_rollo[21] + "' readonly='true'") + " "
                                    + "" + ((id_rollo == 0) ? "onchange" : "onfocus") + "=\"Rollo_" + ((id_rollo == 0) ? "" : "mod_") + "estria_ventana('Txt_peso_neto','Txt_peso_min','Txt_peso_max'" + ((id_rollo == 0) ? ",'Txt_perimetro_1'" : "'") + ");\" />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_neto');"
                                    + "val1.add(Validate.Presence);"
                                    + "val1.add(Validate.Decimal);"
                                    //                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_peso_min'} );"
                                    //                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_peso_max'} );"
                                    + "</script>");
                            out.print("</td>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="PRUEBAS">
                            out.print("<td valign='top'><h3>Pruebas y perimetros</h3>");
                            out.print("<b>Particulas:</b><br />");
                            out.print("<select name='Cbx_particula' id='Cbx_particula' title='Particulas'>");
                            out.print("<option value='0' >Seleccionar</option>");
                            if (id_rollo == 0) {
                                out.print("<option value='NO' >NO</option>");
                                out.print("<option value='SI' >SI</option>");
                            } else {
                                out.print("<option value='NO' " + ((obj_rollo[22].equals("NO")) ? "selected" : "") + ">NO</option>");
                                out.print("<option value='SI' " + ((obj_rollo[22].equals("SI")) ? "selected" : "") + ">SI</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_particula');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            if (estria_ventana == 1) {
                                out.print("<b>Perimetro Derecho° :</b>");
                                out.print("<input type='text' name='Txt_perimetro_1' id='Txt_perimetro_1' placeholder='Perimetro Derecho' title='Perimetro Derecho' onkeyup='javascript:this.value=this.value.toUpperCase();' " + ((id_rollo == 0) ? "" : "onchange=\"Pasar_valor(this.value, Txt_perimetro_2);\"") + " "
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[23]) + "' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_1');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        + "</script>");
                                out.print("<b>Perimetro Izquierdo :</b>");
                                out.print("<input type='text' name='Txt_perimetro_2' id='Txt_perimetro_2' placeholder='Perimetro Izquierdo' title='Perimetro Izquierdo' onkeyup='javascript:this.value=this.value.toUpperCase();'"
                                        + "value='" + ((id_rollo == 0) ? "" : obj_rollo[24]) + "' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_perimetro_2');"
                                        + "val1.add(Validate.Presence);"
                                        + "val1.add(Validate.Decimal);"
                                        + "</script>");
                            }
                            out.print("</td>");
//</editor-fold>
                        }
                        out.print("</tr>");
                        out.print("</table>");
                        if (id_rollo != 0) {
                            out.print("<a onclick='Revalidar_parameros()' href='#'>Validar parametros</a><hr /><center><input type='submit' id='Btn_modificar_rollo' style='display:none;' value='Modificar' /></center>");
                        } else {
                            out.print("<hr /><input type='submit' value='Registrar' />");
                        }
                        //<editor-fold defaultstate="collapsed" desc="CONTROL DE VARIABLES VACIAS">
                        if (registro_calidad == 0) {
                            if (estria_ventana == 1) {
                                out.print("<input type='hidden' name='Txt_ancho_ventana' id='Txt_ancho_ventana' value='" + 0 + "' />");
                                out.print("<input type='hidden' name='Txt_cv_extremo_1' id='Txt_cv_extremo_1' value='" + 0 + "' />");
                                out.print("<input type='hidden' name='Txt_cv_extremo_2' id='Txt_cv_extremo_2' value='" + 0 + "' />");
                                out.print("<input type='hidden' name='Txt_min_pared_sencilla_frosted' id='Txt_min_pared_sencilla_frosted' value='" + 0 + "' />");
                                out.print("<input type='hidden' name='Txt_max_pared_sencilla_frosted' id='Txt_max_pared_sencilla_frosted' value='" + 0 + "' />");
                                out.print("<input type='hidden' name='Txt_perimetro_1' id='Txt_perimetro_1' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_perimetro_2' id='Txt_perimetro_2' value='" + 0 + "'/>");
                            } else {
                                //VARIABLE VACIAS VENTANA
                                out.print("<input type='hidden' name='Txt_primer_extremo_estria' id='Txt_primer_extremo_estria' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_centro_estria' id='Txt_centro_estria' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_segundo_extremo_estria' id='Txt_segundo_extremo_estria' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_min_pared_sencilla_estria' id='Txt_min_pared_sencilla_estria' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_max_pared_sencilla_estria' id='Txt_max_pared_sencilla_estria' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_perimetro_1' id='Txt_perimetro_1' value='" + 0 + "'/>");
                                out.print("<input type='hidden' name='Txt_perimetro_2' id='Txt_perimetro_2' value='" + 0 + "'/>");
                            }
                        }
//</editor-fold>
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//                    // </editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="BUSCAR Y EXPORTAR">
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_rollos = jpacrev.Traer_rollos_id_registro(id_registro);
                    } else if (lst_rollos == null) {
                        lst_rollos = jpacrev.Traer_rollos_id_registro(id_registro);
//                        lst_rollos = jpacrlo.Traer_rollos_id_registro_filtro(id_registro, filtro);
//                            lst_rollos = jpacrlo.Traer_rollos_id_registro(id_registro);
                    }
                    out.print("<div align='right'>"
                            + "<form action='Rollo?opc=19" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' method='post' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                    } else {
                        out.print("<input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/>");
                    }
                    out.print("</form></div>");
                    out.print("<div style='display:block'>");
                    out.print("<div style='float:left;width:300px'>");
                    out.print("<form action='Rollo?opc=19" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' method='post' name='FormActualizar' id='FormActualizar' onsubmit='checkSubmit();'>"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='odn' value='" + orden + "' />"
                            + "<input type='hidden' name='ipd' value='" + id_producto + "' />"
                            + "<input type='hidden' name='rlo' value='0' />"
                            + "<input type='hidden' name='fto' value='' />");
                    lst_seriales_seleccion = jpacsrl.Traer_equipos_medicion_registro(id_registro);
                    String regla = "<b class='naranja'>Pendiente selección</b>", balanza = "<b class='naranja'>Pendiente selección</b>", calibrador = "<b class='naranja'>Pendiente selección</b>";
                    if (lst_seriales_seleccion != null) {
                        Object[] obj_equipos_seleccion = (Object[]) lst_seriales_seleccion.get(0);
                        regla = obj_equipos_seleccion[2].toString();
                        balanza = obj_equipos_seleccion[4].toString();
                        calibrador = obj_equipos_seleccion[6].toString();
                    }
                    if (!(rol.equals("Consulta") || rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad"))) {
                        out.print("<img onclick='JAVASCRIPT:document.getElementById(\"Registro_rollo_" + obj_registro[0] + "\").style.display=\"block\";' src='Interfaz/Contenido/Iconos/Plus.png' alt='edit' title='Registro de rollos' /> Registrar rollo");
                    }
                    out.print("<a href='JAVASCRIPT:FormActualizar.submit()'><img src='Interfaz/Contenido/Iconos/Update.png' alt='edit' title='Volver a registros' /></a>Actualizar rollos"
                            + "</form></div>");
                    out.print("<div style='float:right;'>");
                    out.print("<a onclick=\"tableToExcel('Excel', 'R-PI-011')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\"  alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel  "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' id='Excel' style='width:100%'>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<td colspan='20' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' rowspan='2' style='width:25%'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='10' align='center' style='width:50%'><b class='negro'>REGISTRO</b></td>");
                    out.print("<td colspan='5' align='center' style='width:25%'><b class='negro'>CODIGO<br />R-" + ((estria_ventana == 2) ? "GC-052" : "PI-023") + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='10' align='center'><b class='negro'>" + ((estria_ventana == 2) ? "CONTROL EXTRUSIÓN PELICULA TUBULAR CON VENTANA" : "INSPECCIÓN CALIDAD EXTRUSIÓN DE MANGA CON ESTRIAS") + "</b></td>");
                    out.print("<td colspan='5' align='center'><b class='negro'>VERSION " + ((estria_ventana == 2) ? "6" : "3") + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS PRODUCCIÓN">
                    out.print("<td colspan='2' align='center'><b>Orden</b></td>");
                    out.print("<td colspan='3' align='center'>" + obj_registro[21] + "</td>");
                    out.print("<td colspan='2' align='center'><b>Producto</b></td>");
                    out.print("<td colspan='6' align='center'>" + obj_registro[23] + " / " + obj_registro[24] + "</td>");
                    if (estria_ventana > 0) {
                        out.print("<td colspan='2' align='center'><b>Máquina</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[9] + "</td>");
                        out.print("<td align='center'><b>CC</b></td>");
                        out.print("<td colspan='2' align='center'>" + obj_registro[10] + "</td>");
                    } else {
                        out.print("<td colspan='2' align='center'><b>Máquina</b></td>");
                        out.print("<td colspan='5' align='center'>" + obj_registro[9] + "</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'><b>Fecha y turno</b></td>");
                    out.print("<td colspan='3' align='center'>" + obj_registro[2] + " " + obj_registro[3] + "</td>");
                    out.print("<td colspan='2' align='center'><b>Ficha Técnica</b></td>");
                    out.print("<td colspan='3' align='center'>" + obj_registro[26] + " versión " + obj_registro[27] + "</td>");
                    out.print("<td colspan='2' align='center'><b>Lote C</b></td>");
                    out.print("<td colspan='3' align='center'>" + obj_registro[5] + " / " + obj_registro[6] + "</td>");
                    out.print("<td colspan='2' align='center'><b>Lote P</b></td>");
                    out.print("<td colspan='3' align='center'>" + obj_registro[7] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center'><b>Regla</b></td>");
                    out.print("<td colspan='3' align='center'>" + regla + "</td>");
                    out.print("<td colspan='2' align='center'><b>Balanza</b></td>");
                    out.print("<td colspan='3' align='center'>" + balanza + "</td>");
                    out.print("<td colspan='2' align='center'><b>Calibrador</b></td>");
                    out.print("<td colspan='8' align='center'>" + calibrador + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center'><b>Pared Doble</b></td>");
                    out.print("<td colspan='2' align='center' >" + obj_registro[28] + " <b>+</b> " + obj_registro[29] + " <b>-</b> " + obj_registro[30] + "</td>");
                    out.print("<td colspan='2' align='center'><b>Pared Sencilla</b></td>");
                    out.print("<td colspan='2' align='center'>" + obj_registro[31] + " <b>+</b> " + obj_registro[32] + " <b>-</b> " + obj_registro[33] + "</td>");
                    if (estria_ventana > 0) {
                        out.print("<td colspan='2' align='center'><b>Ancho manga</b></td>");
                        out.print("<td colspan='2' align='center' colspan='2'>" + obj_registro[34] + " <b>+</b> " + obj_registro[35] + " <b>-</b> " + obj_registro[36] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Ancho bobina</b></td>");
                        out.print("<td colspan='2' align='center' colspan='2'>" + obj_registro[37] + " <b>+</b> " + obj_registro[38] + " <b>-</b> " + obj_registro[39] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Peso</b></td>");
                        out.print("<td colspan='2' align='center' colspan='2'>" + obj_registro[46] + " <b>+</b> " + obj_registro[47] + " <b>-</b> " + obj_registro[48] + "</td>");
                    } else {
                        out.print("<td colspan='2' align='center'><b>Pared Doble Estria</b></td>");
                        out.print("<td colspan='2' align='center' colspan='2'>" + obj_registro[34] + " <b>+</b> " + obj_registro[35] + " <b>-</b> " + obj_registro[36] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Pared Sencilla Esteria</b></td>");
                        out.print("<td colspan='2' align='center' colspan='2'>" + obj_registro[37] + " <b>+</b> " + obj_registro[38] + " <b>-</b> " + obj_registro[39] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Peso</b></td>");
                        out.print("<td colspan='2' align='center' colspan='2'>" + obj_registro[46] + " <b>+</b> " + obj_registro[47] + " <b>-</b> " + obj_registro[48] + "</td>");
                    }
                    out.print("</tr>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="TITULOS">
                    if (estria_ventana == 2) {
                        out.print("<tr>");
                        out.print("<th rowspan='3'>Rollo</th>");
                        out.print("<th colspan='3' rowspan='2'>Pared Doble</th>");
                        out.print("<th colspan='6'>Pared Sencilla</b></th>");
                        out.print("<th colspan='4'rowspan='2'>Centrado de ventana</th>");
                        out.print("<th rowspan='3'>Ancho ventana mm</th>");
                        out.print("<th rowspan='3'>Ancho manga mm</th>");
                        out.print("<th rowspan='3'>Ancho bobina mm</th>");
                        out.print("<th rowspan='3'>Peso rollo Kg</th>");
                        out.print("<th rowspan='3'>Particulas</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='3'><b>Trasparente</b></td>");
                        out.print("<td align='center' colspan='3'><b>Frosted</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                        out.print("<td align='center'><b>Centro</b></td>");
                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                        out.print("<td align='center' colspan='2'><b>Max</b></td>");
                        out.print("<td align='center'><b>Min</b></td>");
                        out.print("<td align='center' colspan='2'><b>Max</b></td>");
                        out.print("<td align='center'><b>Min</b></td>");
                        out.print("<td align='center' colspan='2'><b>Extremo 1</b></td>");
                        out.print("<td align='center' colspan='2'><b>Extremo 2</b></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr>");
                        out.print("<th rowspan='2'>Rollo</th>");
                        out.print("<th colspan='3'>Pared Doble con estrias mm</th>");
                        out.print("<th colspan='2'>Pared Sencilla con estrias mm</th>");
                        out.print("<th colspan='3'>Pared Doble sin estria mm</th>");
                        out.print("<th colspan='2'>Pared Sencilla sin estria mm</th>");
                        out.print("<th rowspan='2'>Ancho manga mm</th>");
                        out.print("<th rowspan='2'>Ancho bobina mm</th>");
                        out.print("<th colspan='2'>Perimetro mm</th>");
                        out.print("<th rowspan='2'>Peso rollo Kg</th>");
                        out.print("<th rowspan='2' colspan='3'>Particulas</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                        out.print("<td align='center'><b>Centro</b></td>");
                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                        out.print("<td align='center'><b>Max</b></td>");
                        out.print("<td align='center'><b>Min</b></td>");
                        out.print("<td align='center'><b>Primer Extremo</b></td>");
                        out.print("<td align='center'><b>Centro</b></td>");
                        out.print("<td align='center'><b>Segundo Extremo</b></td>");
                        out.print("<td align='center'><b>Max</b></td>");
                        out.print("<td align='center'><b>Min</b></td>");
                        out.print("<td align='center'><b>Derecho</b></td>");
                        out.print("<td align='center'><b>Izquierdo</b></td>");
                        out.print("</tr>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE ROLLOS">
                    if (lst_rollos == null) {
                        out.print("<tr><td colspan='20' align='center'>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay rollos registrados </b>");
                        out.print("</td></tr>");
                    } else {
                        for (int i = 0; i < lst_rollos.size(); i++) {
                            Object[] obj_rollos = (Object[]) lst_rollos.get(i);
                            out.print("<tr " + ((obj_rollos[26].toString().contains("_calidad")) ? "class='calidad' " : "") + ">");
                            //<editor-fold defaultstate="collapsed" desc="ESTADO DEL ROLLO">
                            out.print("<td align='center' " + (((Integer) obj_rollos[2] == 1 || (Integer) obj_rollos[2] % (Integer) obj_registro[52] == 0) ? " style='BACKGROUND-COLOR:#caf2ff' " : "") + ">");
                            out.print("<form action='Rollo?opc=19" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' method='post' name='FormActualizarRollo" + i + "' id='FormActualizarRollo" + i + "' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                    + "<input type='hidden' name='odn' value='" + orden + "'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                    + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                    + "<input type='hidden' name='fto' value=''>"
                                    + "<a href='JAVASCRIPT:FormActualizarRollo" + i + ".submit()'><b>" + obj_rollos[2] + "</b></a>");
                            out.print("</form>");
                            out.print("<form action='Rollo?opc=10" + ((estria_ventana == 2) ? "&etvt=2" : "&etvt=1") + "' method='post' name='FormEvento" + i + "' id='FormEvento" + i + "' onsubmit='checkSubmit();'>"
                                    + "<input type='hidden' name='irg' value='" + id_registro + "'>"
                                    + "<input type='hidden' name='odn' value='" + orden + "'>"
                                    + "<input type='hidden' name='ipd' value='" + id_producto + "'>"
                                    + "<input type='hidden' name='rlo' value='" + obj_rollos[0] + "'>"
                                    + "<input type='hidden' name='fto' value='ESPECIAL'>");
                            if (obj_rollos[3].toString().equals("A")) {
                                //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_aprobado.png' style='width:15px;height:15px;' alt='edit' title='Rollo aprobado' /></a>");
                                out.print("<img onclick='JAVASCRIPT:FormEvento" + i + ".submit()' src='Interfaz/Contenido/Iconos/Flag_aprobado.png' style='width:15px;height:15px;' alt='edit' title='Rollo aprobado' />");
                            } else if (obj_rollos[3].toString().equals("C")) {
                                //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' style='width:15px;height:15px;' alt='edit' title='Rollo en cuarentena' /></a>");
                                out.print("<img onclick='JAVASCRIPT:FormEvento" + i + ".submit()' src='Interfaz/Contenido/Iconos/Flag_cuarentena.png' style='width:15px;height:15px;' alt='edit' title='Rollo en cuarentena' />");
                            } else if (obj_rollos[3].toString().equals("R")) {
                                //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_rechazado.png' style='width:15px;height:15px;' alt='edit' title='Rollo rechazo' /></a>");
                                out.print("<img onclick='JAVASCRIPT:FormEvento" + i + ".submit()' src='Interfaz/Contenido/Iconos/Flag_rechazado.png' style='width:15px;height:15px;' alt='edit' title='Rollo rechazo' />");
                            } else if (obj_rollos[3].toString().equals("S")) {
                                //out.print("<a href='JAVASCRIPT:FormEvento" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Flag_sin_datos.png' style='width:15px;height:15px;' alt='edit' title='Rollo sin confirmar' /></a>");
                                out.print("<img onclick='JAVASCRIPT:FormEvento" + i + ".submit()' src='Interfaz/Contenido/Iconos/Flag_sin_datos.png' style='width:15px;height:15px;' alt='edit' title='Rollo sin confirmar' />");
                            }
//                            out.print("</td>");
                            out.print("</form></td>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                            if (obj_rollos[3].toString().equals("S")) {
                                if (estria_ventana == 2) {
                                    out.print("<td align='center' colspan='15'><b class='naranja'>PENDIENTE COMPLETAR CALIDAD</b></td>");
                                    out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                    out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                    out.print("<td align='center'>" + obj_rollos[22] + "</td>");
                                } else {
                                    out.print("<td align='center' colspan='11'><b class='naranja'>PENDIENTE COMPLETAR CALIDAD</b></td>");
                                    out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                    out.print("<td align='center' colspan='2'><b class='naranja'>---</b></td>");
                                    out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                    out.print("<td align='center' colspan='3'>" + obj_rollos[22] + "</td>");
                                }
                            } else if (estria_ventana == 2) {
                                out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                out.print("<td align='center' colspan='2'>" + obj_rollos[11] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                out.print("<td align='center' colspan='2'>" + obj_rollos[15] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[14] + "</td>");
                                out.print("<td align='center' colspan='2'>" + obj_rollos[16] + "</td>");
                                out.print("<td align='center' colspan='2'>" + obj_rollos[17] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[18] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[19] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[22] + "</td>");
                            } else {
                                out.print("<td align='center'>" + obj_rollos[7] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[8] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[9] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[13] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[12] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[4] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[5] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[6] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[11] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[10] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[19] + "</td>");
                                out.print("<td align='center'>" + obj_rollos[20] + "</td>");
                                out.print("<td colspan='2' align='center'>");
                                out.print("" + obj_rollos[23] + " - " + obj_rollos[24] + "");
                                try {
                                    resultado = mtdetd.Direfencia_perimetros((Double) obj_rollos[23], (Double) obj_rollos[24]);
                                    if (resultado <= (Double) obj_registro[45]) {
                                        out.print(" = <b class='verde' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                    } else {
                                        out.print(" = <b class='rojo' style='text-transform: lowercase;'>" + resultado + " mm</b>");
                                    }
                                } catch (Exception e) {
                                    out.print("<b class='naranja'> = ---</b>");
                                }
                                out.print("</td>");
                                out.print("<td align='center'>" + obj_rollos[21] + "</td>");
                                out.print("<td align='center' colspan='3'>" + obj_rollos[22] + "</td>");
                            }
                            out.print("</tr>");
//</editor-fold>
                        }
                    }
                    //</editor-fold>
                    if (estria_ventana == 2) {
                        out.print("<tr><td colspan='20'><div style='float:right'><b class='naranja'>*DISTANCIA DEL BORDE DE LA BOLSA AL INICIO DE LA VENTANA NO MENOR A 72 mm</b></div></td></tr>");
                    }
                    out.print("<tr>");
                    out.print("<th colspan='4'>Responsables</th>");
                    out.print("<td colspan='4' align='center'><b>Calidad</b></td>");
                    out.print("<td colspan='4'>" + obj_registro[12] + " <b class='calidad'>" + obj_registro[11] + "</b></td>");
                    out.print("<td colspan='4' align='center'><b>Prod. Insumos</b></td>");
                    out.print("<td colspan='4'>" + obj_registro[4] + " <b class='extrusion'>" + obj_registro[3] + "</b></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    // </editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_rollo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
