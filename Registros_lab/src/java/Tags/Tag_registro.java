package Tags;

import Controladores.CategoriaJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.ParadaMaquinaJpaController;
import Controladores.ParametroJpaController;
import Controladores.PncJpaController;
import Controladores.RegistroEntradaMaterialJpaController;
import Controladores.RegistroEspesorBocaJpaController;
import Controladores.RegistroEspesorColaJpaController;
import Controladores.RegistroFrecuenciaHoraJpaController;
import Controladores.RegistroImplementoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroLoteCodigoJpaController;
import Controladores.RegistroObservacionJpaController;
import Controladores.RegistroPruebaCalidadJpaController;
import Controladores.RegistroHoraInsumosController;
import Controladores.ParamJpaController;
import Metodos.Connection_metrologia;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.util.Date;

public class Tag_registro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String usuario_rol = pageContext.getSession().getAttribute("Rol/Nombres").toString();
            //<editor-fold defaultstate="collapsed" desc="CONEXIONES">
            RegistroJpaController jpacrgt = new RegistroJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            ParadaMaquinaJpaController jpacpmq = new ParadaMaquinaJpaController();
            RegistroLoteCodigoJpaController jpacrlc = new RegistroLoteCodigoJpaController();
            RegistroPruebaCalidadJpaController jpacrpc = new RegistroPruebaCalidadJpaController();
            RegistroImplementoJpaController jpacrip = new RegistroImplementoJpaController();
            RegistroEntradaMaterialJpaController jpacrem = new RegistroEntradaMaterialJpaController();
            RegistroObservacionJpaController jpacros = new RegistroObservacionJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            RegistroEspesorBocaJpaController jpacreb = new RegistroEspesorBocaJpaController();
            RegistroEspesorColaJpaController jpacrec = new RegistroEspesorColaJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            PncJpaController jpacpnc = new PncJpaController();
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            Connection_metrologia jpacsra = new Connection_metrologia();
            RegistroHoraInsumosController RegistroHraJpa = new RegistroHoraInsumosController();
            ParamJpaController ParametroJpa = new ParamJpaController();
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="VARIBALES">
            String[] arg_parametros = {"Pared doble_3_4_5_0",
                "Pared sencilla_6_7_8_0",
                "Soldadura boca_9_10_11_0",
                "Soldadura cola_12_13_14_0",
                "Longitud total_15_16_17_0",
                "Ducto derecho_18_19_20_0",
                "Ducto central_50_51_52_0",
                "Ducto izquierdo_21_22_23_0",
                "Dia. Int. ducto derecho_24_25_26_1",
                "Dia. Int. ducto central_53_54_55_1",
                "Dia. Int. ducto izquierdo_30_31_32_1",
                "Dia. Ext. ducto derecho_27_28_29_1",
                "Dia. Ext. ducto central_56_57_58_1",
                "Dia. Ext. ducto izquierdo_33_34_35_1",
                "Ancho de manga_36_37_38_0",
                "Ancho de ventana_59_60_61_0",
                "Espesor ducto bicapa Ext_62_63_64_0",
                "Espesor ducto bicapa Int_65_66_67_0",
                "Pared sencilla estriada_69_70_71_0",
                "Distancia X4_72_73_74_0",
                "Distancia X5_75_76_77_0"};
            int id_registro = 0;
            int id_entrada_material = 0;
            int total = 0;
            int opcion = 0;
            int suma_total = 0;
            int contador = 0;
            int contador_numero = 0;
            int contador_estado = 0;
            int id_observacion = 0;
            int contador_caracter = 0;
            int contador_na = 0;
            double sumatoria = 0;
            double promedio = 0;
            int id_pnc = 0;
            String promedio_frecuencia_hora = "";
            String datos_pnc = "";
            String filtro = "";
            List lst_parametros = null;
            List lst_resgistro = null;
            List lst_promedios = null;
            List lst_responsables = null;
            List lst_seriales = null;
            List lst_produccion = null;
            List lst_produccion_consulta = null;
            List lst_mantenimiento = null;
            List lst_mantenimiento_consulta = null;
            List lst_implementos = null;
            List lst_observacion = null;
            List lst_registro_despeje = null;
            List lst_espesores_bocas = null;
            List lst_espesores_colas = null;
            List lst_entradas_material = null;
            List lst_entrada_material = null;
            List lst_ficha = null;
            List lst_categoria = null;
            List lst_pnc = null;
            List lst_pnc_registro = null;
            List lst_prdas = null;
            List lst_hora_insumo = null;
            int id_prda = 0;
            String idValid = "";
            lst_parametros = ParametroJpa.ConsultarParametrosxCategoria("idPermitidos");
            if (lst_parametros != null) {
                Object[] obj_par = (Object[]) lst_parametros.get(0);
                idValid = obj_par[2].toString();
            }
            //</editor-fold>
            if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_parametros_frecuencia")) {
                // <editor-fold desc="PARAMETROS DE FRECUENCIA POR HORA">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                opcion = Integer.parseInt(pageContext.getRequest().getAttribute("Modifica").toString());
                lst_parametros = jpacrfh.Parametros_registro_frecuencia_hora(id_registro);
                lst_ficha = jpacftn.Traer_ficha_registro(id_registro);
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                String[] control_estaciones = null;
                if (obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals("")) {
                } else {
                    control_estaciones = obj_registro[77].toString().split("-");
                }
                int cont_estaciones = 0;
                // <editor-fold defaultstate="collapsed" desc="CONTROL EMERGENTES">
                out.print("<div id='content_sin'>");
                out.print("<span id='Menu_registro' class='far fa-caret-square-down fa-size_small style='margin-top:10px' title='Desplegar Menu'></span> Menu<br /><br />");
                //                // <editor-fold defaultstate="collapsed" desc="MENU FLOTANTE">
//                //out.print("<img id=\"Menu_registro\" src='Interfaz/Contenido/Iconos/Menu.png' width='20px' height='20px' alt='edit' title='Desplegar Menu' />");
                out.print("<script>");
                out.print("$(Menu_registro).click(function() {");
                out.print("$(\"#toggle\").toggle(\"slide\");");
                out.print("});");
                out.print("</script>");
                out.print("<div style='display:none;border: 2px solid #15aabf;margin-top:-20px;border-radius:0px 25px 25px 25px' id=\"toggle\">");
                out.print("<div id='sidebar' style='border-right:none'>");
                out.print("<h3>Opciones <br />Consulta Del Registro</h3>");
                out.print("<ul class='sidebar_menu'>");
                out.print("<li><a href='Registro?opc=23&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Detalle del registro</a></li>");
                lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                if (lst_registro_despeje == null) {
                    if ((Integer) obj_registro[16] == 1) {
                        out.print("<li><a href='#' onclick='RegistroDespeje(" + id_registro + ");Enviar_evento();'>Registro despeje de linea</a></li>");
//                    } else {
//                        out.print("<li><a href='#' title='Si el registro esta cerrado no se puede crear registro de despeje'>Registro despeje de linea</a></li>");
                    }
                } else {
                    out.print("<li><a href='Registro?opc=41&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Registro despeje de linea</a></li>");
                }
                if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                    out.print("<li><a href='Registro?opc=49&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                } else if (obj_registro[65].toString().equals("R-PRF-056")) {
                    out.print("<li><a href='Registro?opc=53&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
//                    out.print("<li><a href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                } else {
                    out.print("<li><a href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                }
                out.print("<li><a href='Orden?opc=6&ipd=" + obj_registro[1] + "&odn=" + obj_registro[18] + "&pdt=" + obj_registro[21] + "/" + obj_registro[22] + "&irg=" + id_registro + "&tcs=0&fto='>Salir del registro</a></li>");
                out.print("</ul>");
                out.print("<h3>Opciones <br />Funcionales Del Registro</h3>");
                out.print("<ul class='sidebar_menu'>");

                if (!obj_registro[65].toString().equals("R-PRF-056")) {
                    if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                        out.print("<li><a href='Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1/2 h</a></li>");
                        out.print("<li><a href='Registro?opc=10&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Centros</a></li>");
                    } else {
                        out.print("<li><a href='Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1h</a></li>");
                        out.print("<li><a href='Registro?opc=10&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Bocas</a></li>");
                    }
                    out.print("<li><a href='Registro?opc=11&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Colas</a></li>");
                } else {
                    out.print("<li><a href='Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1h</a></li>");
                }
                if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                    out.print("<li><a href='Registro?opc=47&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Verificación de lote y codigo</a></li>");
                    out.print("<li><a href='Registro?opc=48&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Pruebas calidad</a></li>");
                } else if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("<li><a href='Registro?opc=3&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Verificación de lote y codigo</a></li>");
                    out.print("<li><a href='Registro?opc=51&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Pruebas calidad</a></li>");
                } else {
                    out.print("<li><a href='Registro?opc=3&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Verificación de lote y codigo</a></li>");
                    out.print("<li><a href='Registro?opc=4&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Pruebas calidad</a></li>");
                }
                out.print("<li><a href='Registro?opc=5&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Electrodos/Implementos y Seriales</a></li>");
                out.print("<li><a href='Registro?opc=15&Id_registro=" + id_registro + "&Datos_pnc=0' onclick='Enviar_evento();checkSubmit();'>Producto no conforme</a></li>");
                out.print("<li><a href='Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=0' onclick='Enviar_evento();checkSubmit();'>Control entrada de materiales</a></li>");
                if (obj_registro[65].toString().equals("R-PRF-056")) {
                    out.print("<li><a href='Registro?opc=6&Id_registro=" + id_registro + "&temp=1' onclick='Enviar_evento();checkSubmit();'>Paradas de máquina</a></li>");
                } else {
                    out.print("<li><a href='Registro?opc=6&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Paradas de máquina</a></li>");
                }
                if (obj_registro[65].toString().equals("R-PRF-056")) {
                    out.print("<li><a href='Registro?opc=54&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Hora montaje insumos</a></li>");
                }
                out.print("<li><a href='Registro?opc=24&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Observaciones</a></li>");
                out.print("</ul>");
                out.print("</div>");
                out.print("</div>");
//                // </editor-fold>
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_registro[16] == 0) {
                        out.print("<span class='far fa-plus-square fa-size_small color_span' title='Registrar control'></span> Registrar control<br />");
                    } else if (rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Coordinadora-Produccion")) {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar control'></span> Registrar control<br />");
                        out.print("<span class='fa fa-eraser fa-size_small' onclick='Form_limpiar_cabecera()' title='Limpiar estaciones horarias'></span> Limpiar estaciones horarias<br />");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar control'></span> Registrar control<br />");
                    }
                }
                if (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador") || rol.equals("Documental")) {
                    if (rol.equals("Documental")) {
                        out.print("<span onclick='Form_calidad_cabecera()' class='far fa-list-alt fa-size_small' title='Completar datos calidad'></span> Completar datos calidad");
                    } else if ((Integer) obj_registro[16] == 0) {
//                        out.print("<div style='float:right'><h3>Completar datos calidad</h3></div>");
                        out.print("<span class='far fa-list-alt fa-size_small color_span' title='Completar datos calidad'></span> Completar datos calidad");
                    } else {
//                        out.print("<div style='float:right'><h3>Completar datos calidad<img onclick='Form_calidad_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Completar datos calidad' /></h3></div>");
                        out.print("<span onclick='Form_calidad_cabecera()' class='far fa-list-alt fa-size_small' title='Completar datos calidad'></span> Completar datos calidad");
                    }
                }
                out.print("<h3>Parámetros de frecuencia</h3>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACION">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_limpiar_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Limpiar Estación</h3>");
                out.print("<form action='Registro?opc=30' method='post' name='FormLimpiar' id='FormLimpiar' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                out.print("<br />Seleccionar estación horaria para la limpiar información.<br /><br />");
                out.print("<select name='Cbx_frecuencia_limpiar' id='Cbx_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                for (int i = 1; i <= 10; i++) {
                    if (!(obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals(""))) {
                        for (int j = 0; j < control_estaciones.length; j++) {
                            if (i == Integer.parseInt(control_estaciones[j])) {
                                cont_estaciones++;
                                break;
                            } else {
                                cont_estaciones = 0;
                            }
                        }
                    }
                    if (cont_estaciones > 0) {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 5) {
                                    out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 6) {
                                out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                            } else {
                                out.print("<option value='0' style='color:red' >Hora " + i + " bloqueada</option>");
                            }
                        }
                        cont_estaciones = 0;
                    } else {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 5) {
                                    out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 6) {
                                out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                            } else {
                                out.print("<option value='" + i + "' >Hora " + i + "</option>");
                            }
                        }
                        cont_estaciones = 0;
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='JAVASCRIPT:FormLimpiar.submit()'><img src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Registro'></a><br />");
                out.print("<br /><input type='submit' value='Limpiar' /><br />");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="MODIFICAR PARAMETROS">
                if (opcion > 0) {
                    out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: block;'>");
                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                    out.print("<div align='right'><span class='fa fa-times fa-size_small' onclick=\"location.href ='Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0'\" title='Cancelar modificación' ></span></div>");
                    out.print("<h3>Modificación de parámetro</h3>");
                    out.print("<form action='Registro?opc=26' method='post' name='FormParametros' id='FormParametros' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                    out.print("<input type='hidden' name='Id_parametro' value='" + opcion + "' />");
                    lst_parametros = jpacprm.Traer_parametro(opcion);
                    Object[] obj_parametro = (Object[]) lst_parametros.get(0);
                    out.print("<br />El parámetro a modificar es <b>" + obj_parametro[1].toString().toUpperCase() + "</b> seleccionar estación horaria para la corrección ");
                    out.print(" e ingresar el nuevo valor del parámetro ");
                    out.print("<br /><br /><b>Hora :</b><br /><select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                    out.print("<option value='0' >Seleccionar toma</option>");
                    for (int i = 1; i <= 10; i++) {
                        if (!(obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals(""))) {
                            for (int j = 0; j < control_estaciones.length; j++) {
                                if (i == Integer.parseInt(control_estaciones[j])) {
                                    cont_estaciones++;
                                    break;
                                } else {
                                    cont_estaciones = 0;
                                }
                            }
                        }
                        if (cont_estaciones > 0) {
                            if (i == 5 || i == 10) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    if (i == 5) {
                                        out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                    } else {
                                        out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                    }
                                }
                            } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                                if (i >= 6) {
                                    out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Hora " + i + " bloqueada</option>");
                                }
                            }
                            cont_estaciones = 0;
                        } else {
                            if (i == 5 || i == 10) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    if (i == 5) {
                                        out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                    } else {
                                        out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                    }
                                }
                            } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                                if (i >= 6) {
                                    out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Hora " + i + "</option>");
                                }
                            }
                            cont_estaciones = 0;
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<br /><b>Valor :</b><br />");
                    if (obj_parametro[5].equals("Estado")) {
                        out.print("<input type='radio' name='Vlr_parametro_" + obj_parametro[0] + "' value='Cumple'/>Cumple&nbsp<input type='radio' name='Vlr_parametro_" + obj_parametro[0] + "' value='No cumple' />No cumple"
                                + "&nbsp<input type='radio' name='Vlr_parametro_" + obj_parametro[0] + "' value='N/A' checked='checked'/>N/A");
                    } else if (obj_parametro[5].equals("Numero")) {
                        for (int j = 0; j < lst_ficha.size(); j++) {
                            Object[] obj_ficha = (Object[]) lst_ficha.get(j);
                            double mayor = 0;
                            double menor = 0;
                            for (int k = 0; k < arg_parametros.length; k++) {
                                String[] arg_temp_parametro = arg_parametros[k].split("_");
                                if (obj_parametro[9].equals(arg_temp_parametro[0])) {
                                    int parametro = Integer.parseInt(arg_temp_parametro[1]);
                                    int parametro_max = Integer.parseInt(arg_temp_parametro[2]);
                                    int parametro_min = Integer.parseInt(arg_temp_parametro[3]);
                                    int val_responsable = Integer.parseInt(arg_temp_parametro[4]);
                                    mayor = Double.parseDouble(obj_ficha[parametro].toString()) + Double.parseDouble(obj_ficha[parametro_max].toString());
                                    menor = Double.parseDouble(obj_ficha[parametro].toString()) - Double.parseDouble(obj_ficha[parametro_min].toString());
                                    out.print("<input type='hidden' name='Txt_minimo_" + obj_parametro[0] + "' id='Txt_minimo_" + obj_parametro[0] + "' value='" + menor + "' />");
                                    out.print("<input type='hidden' name='Txt_maximo_" + obj_parametro[0] + "' id='Txt_maximo_" + obj_parametro[0] + "' value='" + mayor + "' />");
                                    if (obj_registro[65].equals("R-PRF-056") && obj_parametro[1].toString().contains("LONGITUD DE DUCTO")) {
                                        out.print("<input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametro[0] + "'id='Vlr_parametro_" + obj_parametro[0] + "' />&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametro[0] + "');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimosPlumatt, { match: 'Txt_minimo_" + obj_parametro[0] + "'} );"
                                                + "val1.add(Validate.Parametros_maximosPlumatt, { match: 'Txt_maximo_" + obj_parametro[0] + "'} );"
                                                + "</script>"
                                                + "<script type='text/javascript'>function AutoFocus_" + obj_parametro[2] + "(prox){document.getElementById('Vlr_parametro_" + obj_parametro[0] + "').focus()}</script>");
                                    } else {
                                        out.print("<input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametro[0] + "'id='Vlr_parametro_" + obj_parametro[0] + "' />&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametro[0] + "');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + obj_parametro[0] + "'} );"
                                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + obj_parametro[0] + "'} );"
                                                + "</script>");
                                    }
                                    break;
                                }
                            }
                        }
                    } else if (Integer.parseInt(obj_parametro[0].toString()) == 222 || Integer.parseInt(obj_parametro[0].toString()) == 223 || Integer.parseInt(obj_parametro[0].toString()) == 224 || Integer.parseInt(obj_parametro[0].toString()) == 225 || Integer.parseInt(obj_parametro[0].toString()) == 226 || Integer.parseInt(obj_parametro[0].toString()) == 227) {
                        if (obj_parametro[1].equals("RESPONSABLE")) {
                            out.print("<input type='text' name='Vlr_parametro_" + obj_parametro[0] + "' id='Vlr_parametro_" + obj_parametro[0] + "' placeholder='Maximo 10 Carac' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.toUpperCase();javascript:this.value=this.value.trim();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametro[0] + "');val1.add(Validate.Presence);val1.add(Validate.ValorTexto);</script>");
                        } else {
                            out.print("<input type='text' name='Vlr_parametro_" + obj_parametro[0] + "' id='Vlr_parametro_" + obj_parametro[0] + "' placeholder='00:00' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.toUpperCase();javascript:this.value=this.value.trim();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametro[0] + "');val1.add(Validate.Presence);val1.add(Validate.ValorHora);</script>");
                        }
                    } else if (obj_parametro[5].equals("Caracter")) {
                        out.print("<input type='text' name='Vlr_parametro_" + obj_parametro[0] + "' id='Vlr_parametro_" + obj_parametro[0] + "' placeholder='Valor' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.toUpperCase();javascript:this.value=this.value.trim();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametro[0] + "');val1.add(Validate.Presence);val1.add(Validate.ValorNA);</script>");
                    }
                    out.print("<br /><br /><input type='submit' value='Modificar valor'>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="REGISTRAR PARAMETROS">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:700px;position: absolute;top: 2%;left:25%;'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<div style='overflow-y:scroll;height:650px;'>");
                out.print("<h3>Registro parámetros de frecuencia por hora</h3>");
                out.print("<form action='Registro?opc=18' method='post' name='FormParametros' id='FormParametros' onsubmit='checkSubmit();'>");
                out.print("<table class='table2' style='width:100%'>");
                out.print("<tr>");
                out.print("<td><b class='negro'>Seleccionar inicio de toma de los datos</b></td>");
                out.print("<td>");
                out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                for (int i = 1; i <= 10; i++) {
                    if (!(obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals(""))) {
                        for (int j = 0; j < control_estaciones.length; j++) {
                            if (i == Integer.parseInt(control_estaciones[j])) {
                                cont_estaciones++;
                                break;
                            } else {
                                cont_estaciones = 0;
                            }
                        }
                    }
                    if (cont_estaciones > 0) {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 5) {
                                    out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 6) {
                                out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                            } else {
                                out.print("<option value='0' style='color:red' >Hora " + i + " bloqueada</option>");
                            }
                        }
                        cont_estaciones = 0;
                    } else {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 5) {
                                    out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 6) {
                                out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                            } else {
                                out.print("<option value='" + i + "' >Hora " + i + "</option>");
                            }
                        }
                        cont_estaciones = 0;
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    out.print("<tr>");
                    if (obj_parametros[7].equals("Estado")) {
                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                        out.print("<td><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='Cumple'/>Cumple<br /><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='No cumple' />No cumple"
                                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='N/A' checked='checked'/>N/A</td>");
                    } else if (obj_parametros[7].equals("Numero")) {
                        for (int j = 0; j < lst_ficha.size(); j++) {
                            Object[] obj_ficha = (Object[]) lst_ficha.get(j);
                            double mayor = 0;
                            double menor = 0;
                            for (int k = 0; k < arg_parametros.length; k++) {
                                String[] arg_temp_parametro = arg_parametros[k].split("_");
                                if (obj_parametros[11].equals(arg_temp_parametro[0])) {
                                    int parametro = Integer.parseInt(arg_temp_parametro[1]);
                                    int parametro_max = Integer.parseInt(arg_temp_parametro[2]);
                                    int parametro_min = Integer.parseInt(arg_temp_parametro[3]);
                                    int val_responsable = Integer.parseInt(arg_temp_parametro[4]);

                                    if (rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Produccion")) {
                                        if (val_responsable == 0) {
                                            mayor = Double.parseDouble(obj_ficha[parametro].toString()) + Double.parseDouble(obj_ficha[parametro_max].toString());
                                            menor = Double.parseDouble(obj_ficha[parametro].toString()) - Double.parseDouble(obj_ficha[parametro_min].toString());
                                            out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                                            out.print("<input type='hidden' name='Txt_minimo_" + obj_parametros[2] + "' id='Txt_minimo_" + i + "' value='" + menor + "' />");
                                            out.print("<input type='hidden' name='Txt_maximo_" + obj_parametros[2] + "' id='Txt_maximo_" + i + "' value='" + mayor + "' />");
                                            if (obj_registro[65].equals("R-PRF-056") && obj_parametros[3].toString().contains("LONGITUD DE DUCTO")) {
                                                out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                        + "<script type='text/javascript'>"
                                                        + "var val1 = new LiveValidation('Vlr_parametro_" + i + "');"
                                                        + "val1.add(Validate.Presence);"
                                                        + "val1.add(Validate.Decimal);"
                                                        + "val1.add(Validate.Parametros_minimosPlumatt, { match: 'Txt_minimo_" + i + "'});"
                                                        + "val1.add(Validate.Parametros_maximosPlumatt, { match: 'Txt_maximo_" + i + "'});"
                                                        + "</script>"
                                                        + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                                            } else {
                                                out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');"
                                                        + "val1.add(Validate.Presence);"
                                                        + "val1.add(Validate.Decimal);"
                                                        + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + i + "'} );"
                                                        + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + i + "'} );"
                                                        + "</script>"
                                                        + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                                            }
                                            break;
                                        }
                                    } else if (rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad")) {
                                        mayor = Double.parseDouble(obj_ficha[parametro].toString()) + Double.parseDouble(obj_ficha[parametro_max].toString());
                                        menor = Double.parseDouble(obj_ficha[parametro].toString()) - Double.parseDouble(obj_ficha[parametro_min].toString());
                                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                                        out.print("<input type='hidden' name='Txt_minimo_" + obj_parametros[2] + "' id='Txt_minimo_" + i + "' value='" + menor + "' />");
                                        out.print("<input type='hidden' name='Txt_maximo_" + obj_parametros[2] + "' id='Txt_maximo_" + i + "' value='" + mayor + "' />");
                                        if (obj_registro[65].equals("R-PRF-056") && obj_parametros[3].toString().contains("LONGITUD DE DUCTO")) {
                                            out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                    + "<script type='text/javascript'>"
                                                    + "var val1 = new LiveValidation('Vlr_parametro_" + i + "');"
                                                    + "val1.add(Validate.Presence);"
                                                    + "val1.add(Validate.Decimal);"
                                                    + "val1.add(Validate.Parametros_minimosPlumatt, { match: 'Txt_minimo_" + i + "'});"
                                                    + "val1.add(Validate.Parametros_maximosPlumatt, { match: 'Txt_maximo_" + i + "'});"
                                                    + "</script>"
                                                    + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");

                                        } else {
                                            out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');"
                                                    + "val1.add(Validate.Presence);"
                                                    + "val1.add(Validate.Decimal);"
                                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + i + "'} );"
                                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + i + "'} );"
                                                    + "</script>"
                                                    + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                        if (obj_parametros[3].equals("RESPONSABLE")) {
                            out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' placeholder='Maximo 10 Caracteres' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');val1.add(Validate.Presence);val1.add(Validate.ValorTexto);</script>"
                                    + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                        } else {
                            out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' placeholder='00:00' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');val1.add(Validate.Presence);val1.add(Validate.ValorHora);</script>"
                                    + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                        }
                    } else if (obj_parametros[7].equals("Caracter")) {
                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                        out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' placeholder='Valor' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');val1.add(Validate.Presence);val1.add(Validate.ValorNA);</script>"
                                + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div style='float:left;'><input type='submit' value='Registrar'/></div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</fieldset></div>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="DIAMETROS CALIDAD">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_calidad' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_calidad_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Completar datos calidad</h3>");
                out.print("<form action='Registro?opc=20' method='post' name='FormParametrosCalidad' id='FormParametrosCalidad' onsubmit='checkSubmit();'>");
                out.print("<table class='table2'>");
                out.print("<tr>");
                out.print("<td><b class='negro'>Seleccionar inicio de toma de los datos</b></td>");
                out.print("<td>");
                out.print("<select name='Cbx_frecuencia_calidad' id='Cbx_frecuencia_calidad' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                for (int i = 1; i <= 10; i++) {
                    if (!(obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals(""))) {
                        for (int j = 0; j < control_estaciones.length; j++) {
                            if (i == Integer.parseInt(control_estaciones[j])) {
                                cont_estaciones++;
                                break;
                            } else {
                                cont_estaciones = 0;
                            }
                        }
                    }
                    if (rol.equals("Documental")) {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador") || rol.equals("Inspectora-Calidad") || rol.equals("Documental")) {
                                if (i == 5) {
                                    out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador") || rol.equals("Documental")) {
                            if (i >= 6) {
                                out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                            } else {
                                out.print("<option value='" + i + "' >Hora " + i + "</option>");
                            }
                        }
                        cont_estaciones = 0;
                    } else if (cont_estaciones > 0) {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador") || rol.equals("Inspectora-Calidad") || rol.equals("Documental")) {
                                if (i == 5) {
                                    out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Documental")) {
                            if (i >= 6) {
                                out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                            } else {
                                out.print("<option value='0' style='color:red' >Hora " + i + " bloqueada</option>");
                            }
                        }
                        cont_estaciones = 0;
                    } else {
                        if (i == 5 || i == 10) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador") || rol.equals("Inspectora-Calidad") || rol.equals("Documental")) {
                                if (i == 5) {
                                    out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador") || rol.equals("Documental")) {
                            if (i >= 6) {
                                out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                            } else {
                                out.print("<option value='" + i + "' >Hora " + i + "</option>");
                            }
                        }
                        cont_estaciones = 0;
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_calidad');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
//                out.print("<tr>");
//                out.print("<td colspan='2'><hr /></td>");
//                out.print("</tr>");
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    if (obj_parametros[12].toString().equals("Calidad")) {
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                        if (obj_parametros[7].equals("Numero")) {
                            for (int j = 0; j < lst_ficha.size(); j++) {
                                Object[] obj_ficha = (Object[]) lst_ficha.get(j);
                                double mayor = 0;
                                double menor = 0;
                                for (int k = 0; k < arg_parametros.length; k++) {
                                    String[] arg_temp_parametro = arg_parametros[k].split("_");
                                    if (obj_parametros[11].equals(arg_temp_parametro[0])) {
                                        int parametro = Integer.parseInt(arg_temp_parametro[1]);
                                        int parametro_max = Integer.parseInt(arg_temp_parametro[2]);
                                        int parametro_min = Integer.parseInt(arg_temp_parametro[3]);
                                        int val_responsable = Integer.parseInt(arg_temp_parametro[4]);
                                        if (val_responsable == 1) {
                                            mayor = Double.parseDouble(obj_ficha[parametro].toString()) + Double.parseDouble(obj_ficha[parametro_max].toString());
                                            menor = Double.parseDouble(obj_ficha[parametro].toString()) - Double.parseDouble(obj_ficha[parametro_min].toString());
                                            out.print("<input type='hidden' name='Txt_minimo_" + obj_parametros[2] + "' id='Txt_minimo_" + i + "' value='" + menor + "' />");
                                            out.print("<input type='hidden' name='Txt_maximo_" + obj_parametros[2] + "' id='Txt_maximo_" + i + "' value='" + mayor + "' />");
                                            out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_calidad_" + obj_parametros[2] + "'id='Vlr_parametro_calidad_" + i + "' onchange='javascript:this.value=this.value.trim();AutoFocusCalidad_" + obj_parametros[2] + "();' />&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_calidad_" + i + "');"
                                                    + "val1.add(Validate.Presence);"
                                                    + "val1.add(Validate.Decimal);"
                                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + i + "'} );"
                                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + i + "'} );"
                                                    + "</script>"
                                                    + "<script type='text/javascript'>function AutoFocusCalidad_" + obj_parametros[2] + "(){document.getElementById('Vlr_parametro_calidad_" + (i + 1) + "').focus()}</script></td>");
                                            break;
                                        }
                                    }
                                }
                            }
                        } else if (obj_parametros[7].equals("Caracter")) {
                            out.print("<td><input type='text' name='Vlr_parametro_calidad_" + obj_parametros[2] + "' id='Vlr_parametro_calidad_" + i + "' placeholder='Valor' onchange='javascript:this.value=this.value.trim();AutoFocusCalidad_" + obj_parametros[2] + "();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_calidad_" + i + "');val1.add(Validate.Presence);val1.add(Validate.ValorNA);</script>"
                                    + "<script type='text/javascript'>function AutoFocusCalidad_" + obj_parametros[2] + "(){document.getElementById('Vlr_parametro_calidad_" + (i + 1) + "').focus()}</script></td>");
                        }
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div style='float:left;'><input type='submit' value='Registrar'/></div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_parametros = jpacrfh.Parametros_tomas_registro_frecuencia_hora(id_registro);
                if (lst_parametros == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos de parámetros de frecuencia por hora</b>");
                    out.print("</center>");
                } else {
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th style='width:33%'>Parámetro</th>");
                    for (int i = 1; i <= 10; i++) {
                        if (!(obj_registro[77].toString() == null ? "" == null : obj_registro[77].toString().equals(""))) {
                            for (int j = 0; j < control_estaciones.length; j++) {
                                if (i == Integer.parseInt(control_estaciones[j])) {
                                    cont_estaciones++;
                                    break;
                                } else {
                                    cont_estaciones = 0;
                                }
                            }
                        }
                        if (cont_estaciones > 0) {
                            if (i == 5 || i == 10) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    out.print("<th><a href='#' onclick='DesbloquearEstacion(" + i + "," + id_registro + ")' title='Estación Bloqueada' style='color:#fff;'>COORD.</a></th>");

                                } else {
                                    out.print("<th>COORD.</th>");
                                }
                            } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad")) {
                                if (i >= 6) {
                                    out.print("<th><a href='#' onclick='DesbloquearEstacion(" + i + "," + id_registro + ")' title='Estación Bloqueada' style='color:#fff;'>" + (i - 1) + "</a></th>");
                                } else {
                                    out.print("<th><a href='#' onclick='DesbloquearEstacion(" + i + "," + id_registro + ")' title='Estación Bloqueada' style='color:#fff;'>" + i + "</a></th>");
                                }
                            } else if (i >= 6) {
                                out.print("<th>" + (i - 1) + "</th>");
                            } else {
                                out.print("<th>" + i + "</th>");
                            }
                            cont_estaciones = 0;
                        } else {
                            if (i == 5 || i == 10) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    out.print("<th><a href='#' onclick='BloquearEstacion(" + i + "," + id_registro + ")' title='Estación Desbloqueada' style='color:#fff;'>COORD.</a></th>");
                                } else {
                                    out.print("<th>COORD.</th>");
                                }
                            } else if (i >= 6) {
                                out.print("<th><a href='#' onclick='BloquearEstacion(" + i + "," + id_registro + ")' title='Estación Desbloqueada' style='color:#fff;'>" + (i - 1) + "</a></th>");
                            } else {
                                out.print("<th><a href='#' onclick='BloquearEstacion(" + i + "," + id_registro + ")' title='Estación Desbloqueada' style='color:#fff;'>" + i + "</a></th>");
                            }
                            cont_estaciones = 0;
                        }
                    }
                    out.print("<th>PROM.</th>");
                    if (!rol.equals("Consulta")) {
                        if ((Integer) obj_registro[16] == 1) {
                            out.print("<th>Modificar</th>");
                        }
                    }
                    out.print("<tr>");
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3] + "</td>");
                        if (obj_parametros[5] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[5].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[15].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[5] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[5] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                            } else if (arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[5].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[5].equals("Cumple") || obj_parametros[5].equals("CUMPLE") || obj_parametros[5].equals("N/A")) {
                                    if (obj_parametros[5].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[5].toString().trim().equals("N/A") || obj_parametros[5].toString().trim().equals("n/a") || obj_parametros[5].toString().trim().equals("N/a") || obj_parametros[5].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[5].toString());
                                }
                            }
                        }
                        if (obj_parametros[6] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[6].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[16].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[6] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[6] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[6].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[6].equals("Cumple") || obj_parametros[6].equals("CUMPLE") || obj_parametros[6].equals("N/A")) {
                                    if (obj_parametros[6].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[6].toString().trim().equals("N/A") || obj_parametros[6].toString().trim().equals("n/a") || obj_parametros[6].toString().trim().equals("N/a") || obj_parametros[6].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[6].toString());
                                }
                            }
                        }
                        if (obj_parametros[7] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[7].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[17].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[7] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[7] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[7].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[7].equals("Cumple") || obj_parametros[7].equals("CUMPLE") || obj_parametros[7].equals("N/A")) {
                                    if (obj_parametros[7].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[7].toString().trim().equals("N/A") || obj_parametros[7].toString().trim().equals("n/a") || obj_parametros[7].toString().trim().equals("N/a") || obj_parametros[7].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[7].toString());
                                }
                            }
                        }
                        if (obj_parametros[8] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[8].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[18].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[8] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[8] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[8] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[8].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[8].equals("Cumple") || obj_parametros[8].equals("CUMPLE") || obj_parametros[8].equals("N/A")) {
                                    if (obj_parametros[8].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[8].toString().trim().equals("N/A") || obj_parametros[8].toString().trim().equals("n/a") || obj_parametros[8].toString().trim().equals("N/a") || obj_parametros[8].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[8].toString());
                                }
                            }
                        }
                        if (obj_parametros[9] == null) {
                            out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                        } else if (obj_parametros[9].toString().equals("null")) {
                            out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[19].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b>" + obj_parametros[9] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'>" + obj_parametros[9] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + obj_parametros[9] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='calidad'>" + obj_parametros[9] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[9].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[9].equals("Cumple") || obj_parametros[9].equals("CUMPLE") || obj_parametros[9].equals("N/A")) {
                                    if (obj_parametros[9].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[9].toString().trim().equals("N/A") || obj_parametros[9].toString().trim().equals("n/a") || obj_parametros[9].toString().trim().equals("N/a") || obj_parametros[9].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[9].toString());
                                }
                            }
                        }
                        if (obj_parametros[10] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[10].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[20].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[10] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[10] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[10] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[10] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[10].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[10].equals("Cumple") || obj_parametros[10].equals("CUMPLE") || obj_parametros[10].equals("N/A")) {
                                    if (obj_parametros[10].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[10].toString().trim().equals("N/A") || obj_parametros[10].toString().trim().equals("n/a") || obj_parametros[10].toString().trim().equals("N/a") || obj_parametros[10].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[10].toString());
                                }
                            }
                        }
                        if (obj_parametros[11] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[11].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[21].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[11] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[11] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[11] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[11] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[11].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[11].equals("Cumple") || obj_parametros[11].equals("CUMPLE") || obj_parametros[11].equals("N/A")) {
                                    if (obj_parametros[11].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[11].toString().trim().equals("N/A") || obj_parametros[11].toString().trim().equals("n/a") || obj_parametros[11].toString().trim().equals("N/a") || obj_parametros[11].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[11].toString());
                                }
                            }
                        }
                        if (obj_parametros[12] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[12].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[22].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[12] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[12] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[12] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[12].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[12].equals("Cumple") || obj_parametros[12].equals("CUMPLE") || obj_parametros[12].equals("N/A")) {
                                    if (obj_parametros[12].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[12].toString().trim().equals("N/A") || obj_parametros[12].toString().trim().equals("n/a") || obj_parametros[12].toString().trim().equals("N/a") || obj_parametros[12].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[12].toString());
                                }
                            }
                        }
                        if (obj_parametros[13] == null) {
                            out.print("<td align='center'></td>");
                        } else if (obj_parametros[13].toString().equals("null")) {
                            out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[23].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[13] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[13] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[13] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[13] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[13].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[13].equals("Cumple") || obj_parametros[13].equals("CUMPLE") || obj_parametros[13].equals("N/A")) {
                                    if (obj_parametros[13].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[13].toString().trim().equals("N/A") || obj_parametros[13].toString().trim().equals("n/a") || obj_parametros[13].toString().trim().equals("N/a") || obj_parametros[13].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[13].toString());
                                }
                            }
                        }
                        if (obj_parametros[14] == null) {
                            out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                        } else if (obj_parametros[14].toString().equals("null")) {
                            out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[24].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b>" + obj_parametros[14] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'>" + obj_parametros[14] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + obj_parametros[14] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='calidad'>" + obj_parametros[14] + "</b></td>");
                            }
                            if (obj_parametros[25].toString().equals("Numero")) {
                                sumatoria = sumatoria + Double.parseDouble(obj_parametros[14].toString());
                                contador_numero++;
                                contador_estado--;
                            } else if (obj_parametros[25].toString().equals("Estado")) {
                                if (obj_parametros[14].equals("Cumple") || obj_parametros[14].equals("CUMPLE") || obj_parametros[14].equals("N/A")) {
                                    if (obj_parametros[14].equals("N/A")) {
                                        contador_na++;
                                    }
                                } else {
                                    contador_estado++;
                                }
                                contador_numero--;
                            } else if (obj_parametros[25].toString().equals("Caracter")) {
                                if (obj_parametros[14].toString().trim().equals("N/A") || obj_parametros[14].toString().trim().equals("n/a") || obj_parametros[14].toString().trim().equals("N/a") || obj_parametros[14].toString().trim().equals("n/A")) {
                                    contador_na++;
                                } else if (Integer.parseInt(obj_parametros[2].toString()) == 222 || Integer.parseInt(obj_parametros[2].toString()) == 223 || Integer.parseInt(obj_parametros[2].toString()) == 224 || Integer.parseInt(obj_parametros[2].toString()) == 225 || Integer.parseInt(obj_parametros[2].toString()) == 226 || Integer.parseInt(obj_parametros[2].toString()) == 227) {
                                    contador_na++;
                                } else {
                                    contador_caracter++;
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[14].toString());
                                }
                            }
                        }
                        if (contador_estado < 0) {
                            if (contador_numero != 0) {
                                promedio = sumatoria / contador_numero;
                                long mult = (long) Math.pow(10, 2);
                                promedio = (Math.round(promedio * mult)) / (double) mult;
                                promedio_frecuencia_hora = "<b>" + promedio + "</b>";
                            } else {
                                promedio_frecuencia_hora = "<b>0</b>";
                            }
                        }
                        if (contador_numero < 0) {
                            if (contador_estado == 0) {
                                if (contador_na > 0) {
                                    promedio_frecuencia_hora = "<b class='naranja'>N/A</b>";
                                } else {
                                    promedio_frecuencia_hora = "<b class='verde'>Cumple</b>";
                                }
                            } else {
                                promedio_frecuencia_hora = "<b class='rojo'>No cumple</b>";
                            }
                        }
                        if (contador_caracter > 0) {
                            if (contador_na > 0 && contador_caracter == 0) {
                                promedio_frecuencia_hora = "<b>N/A</b>";
                            } else {
                                promedio = sumatoria / contador_caracter;
                                long mult = (long) Math.pow(10, 2);
                                promedio = (Math.round(promedio * mult)) / (double) mult;
                                promedio_frecuencia_hora = "<b>" + promedio + "</b>";
                            }
                        }
                        if (promedio_frecuencia_hora == null ? "" == null : promedio_frecuencia_hora.equals("")) {
                            out.print("<td align='center' ><b>N/A</b></td>");
                        } else {
                            out.print("<td align='center' >" + promedio_frecuencia_hora + "</td>");
                        }
                        contador_numero = 0;
                        contador_estado = 0;
                        contador_na = 0;
                        contador_caracter = 0;
                        sumatoria = 0;
                        promedio = 0;
                        promedio_frecuencia_hora = "";
                        if (!rol.equals("Consulta")) {
                            if ((Integer) obj_registro[16] == 1) {
                                if (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                                    if (obj_parametros[26].toString().equals("Todos") || obj_parametros[26].toString().equals("Calidad")) {
                                        out.print("<td align='center'>"
                                                + "<form action='Registro?opc=2' method='post' onsubmit='checkSubmit();' name='FormModificarParametro" + i + "' id='FormModificarParametro" + i + "'>"
                                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                + "<input type='hidden' name='Modifica' value='" + obj_parametros[2] + "' />"
                                                + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificarParametro" + i + ".submit()' title='Actualizar Registro'></span>"
                                                + "</form>"
                                                + "</td>");
                                    }
                                } else if (obj_parametros[26].toString().equals("Todos")) {
                                    out.print("<td align='center'>"
                                            + "<form action='Registro?opc=2' method='post' onsubmit='checkSubmit();' name='FormModificarParametro" + i + "' id='FormModificarParametro" + i + "'>"
                                            + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                            + "<input type='hidden' name='Modifica' value='" + obj_parametros[2] + "' />"
                                            + "<span class='fa fa-pen fa-size_small' onclick='JAVASCRIPT:FormModificarParametro" + i + ".submit()' title='Actualizar Registro'></span>"
                                            + "</form>"
                                            + "</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "<span class='fa fa-pen fa-size_small color_span' title='Sin permisos'></span>"
                                            + "</td>");
                                }
                            }
                        }
                    }
                    lst_responsables = jpacrfh.Responsables_tomas_registro_frecuencia_hora(id_registro);
                    Object[] obj_responsables = (Object[]) lst_responsables.get(0);
                    out.print("<tr>");
                    out.print("<td align='center'><b>Hora de toma de datos</b></td>");
                    for (int i = 0; i < 10; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + arg_responsables[0] + "</b></td>");
                            } else if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + arg_responsables[0] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + arg_responsables[0] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + arg_responsables[0] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental") ) {
                                out.print("<td align='center'><b class='calidad'>" + arg_responsables[0] + "</b></td>");
                            }
                        }
                    }
                    out.print("<th rowspan=2 colspan=2></th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>Responsables</b></td>");
                    for (int i = 0; i < 10; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + arg_responsables[2] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental") ) {
                                out.print("<td align='center'><b class='calidad'>" + arg_responsables[2] + "</b></td>");
                            }
                        }
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
//</editor-fold>
                out.print("<br />");
                out.print("<br />");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_verificacion")) {
                // <editor-fold desc="VERIFICACIÓN DE LOTE Y CODIGO">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_parametros = jpacrlc.Parametros_registro_lote_codigo(id_registro);
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'><br />");
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_registro[16] == 0) {
                    } else if (rol.equals("Administrador")) {
//                        out.print("<h3><a id='mostrar' href='javascript:mostrar();' ><img onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registro de verificación lote y código' /></a>"
//                                + "<a id='mostrar' href='javascript:mostrar3();' ><img onclick='Form_limpiar_cabecera()' src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Estación' /></a>  Verificación de lote y código</h1>");
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar control'></span> Registrar verificación control<br />");
                        out.print("<span class='fa fa-eraser fa-size_small' onclick='Form_limpiar_cabecera()' title='Limpiar estaciones horarias'></span> Limpiar estaciones horarias<br />");
                    } else {
//                        out.print("<h3><a id='mostrar' href='javascript:mostrar();' ><img id='cambiar' onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registro de verificación lote y código' /></a>  Verificación de lote y código</h1>");
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar control'></span> Registrar verificación de control<br />");
                    }
                }
                out.print("<h3>Verificación de lote y código</h3>");
                //<editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACIONES">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_limpiar_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Limpiar Estación</h3>");
                out.print("<form action='Registro?opc=33' method='post' name='FormLimpiar' id='FormLimpiar' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                out.print("<br />Seleccionar estación horaria para la limpiar información.<br /><br />");
                out.print("<select name='Cbx_frecuencia_limpiar' id='Cbx_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                out.print("<option value='1' >hora 1</option>");
                out.print("<option value='2' >hora 2</option>");
                out.print("<option value='3' >hora 3</option>");
                out.print("<option value='4' >hora 4</option>");
                out.print("<option value='5' >hora 5</option>");
                out.print("<option value='6' >hora 6</option>");
                out.print("<option value='7' >hora 7</option>");
                out.print("<option value='8' >hora 8</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='JAVASCRIPT:FormLimpiar.submit()'><img src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Registro'></a><br />");
                out.print("<br /><input type='submit' value='Limpiar' /><br />");
                out.print("</form>");
                out.print("</fieldset></div>");
                //FIN LIMPIAR
                //</editor-fold>    
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Registro verificación de lote y codigo</h3>");
                out.print("<form action='Registro?opc=8' method='post' onsubmit='checkSubmit();'>");
                out.print("<table>");
                out.print("<tr>");
                out.print("<td>Selccionar inicio de toma de los datos</td>");
                out.print("<td>");
                out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                out.print("<option value='1' >hora 1</option>");
                out.print("<option value='2' >hora 2</option>");
                out.print("<option value='3' >hora 3</option>");
                out.print("<option value='4' >hora 4</option>");
                out.print("<option value='5' >hora 5</option>");
                out.print("<option value='6' >hora 6</option>");
                out.print("<option value='7' >hora 7</option>");
                out.print("<option value='8' >hora 8</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'><hr /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'><b class='rojo'>El lote del producto a ingresar debe ser igual al de la cabecera.</b></td>");
                out.print("</tr>");
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                    if (obj_parametros[7].equals("Estado")) {
                        out.print("<td><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='Cumple'/>Cumple<input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='No cumple' checked='checked'/>No cumple</td>");
                    } else if (obj_parametros[7].equals("Caracter")) {
                        if (obj_registro[65].toString().equals("R-PRF-019")) {
                            out.print("<input type='hidden' id='Lote_producto' name='Lote_producto' value='" + obj_registro[103].toString().toUpperCase() + "' />");
                        } else {
                            out.print("<input type='hidden' id='Lote_producto' name='Lote_producto' value='" + obj_registro[3].toString().toUpperCase() + "' />");
                        }
                        out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + obj_parametros[2] + "' placeholder='Lote' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametros[2] + "');val1.add(Validate.Presence);"
                                + "val1.add(Validate.LoteC);val1.add(Validate.Confirmation, { match: 'Lote_producto'} );</script></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div style='float:right;'><input type='submit' value='Registrar'/></div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_parametros = jpacrlc.Parametros_tomas_registro_lote_codigo(id_registro);
                if (lst_parametros == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos de verificación de lote y codigo</b>");
                    out.print("</center>");
                } else {
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    for (int i = 0; i < 8; i++) {
                        out.print("<th>" + (i + 1) + "</th>");
                    }
                    out.print("<tr>");
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3] + "</td>");
                        if (obj_parametros[5] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[5].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[13].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[5] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[5] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                            }
                        }
                        if (obj_parametros[6] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[6].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[14].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[6] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[6] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                            }
                        }
                        if (obj_parametros[7] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[7].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[15].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[7] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[7] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                            }
                        }
                        if (obj_parametros[8] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[8].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[16].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[8] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[8] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[8] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                            }
                        }
                        if (obj_parametros[9] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[9].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[9] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[17].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[9] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[9] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[9] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[9] + "</b></td>");
                            }
                        }
                        if (obj_parametros[10] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[10].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[10] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[18].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[10] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[10] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[10] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[10] + "</b></td>");
                            }
                        }
                        if (obj_parametros[11] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[11].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[11] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[19].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[11] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[11] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[11] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[11] + "</b></td>");
                            }
                        }
                        if (obj_parametros[12] == null) {
                            out.print("<td><b></b></td>");
                        } else if (obj_parametros[12].equals("No cumple")) {
                            out.print("<td align='center'><b class='rojo'>" + obj_parametros[12] + "</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[20].toString().split("/");
                            if (arg_responsables[0].equals("Administrador")) {
                                out.print("<td align='center'><b>" + obj_parametros[12] + "</b></td>");
                            } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + obj_parametros[12] + "</td>");
                            } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                            } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + obj_parametros[12] + "</b></td>");
                            }
                        }
                    }
                    out.print("</table>");
                }
                //</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_pruebas_calidad")) {
                // <editor-fold desc="PRUEBAS DE CALIDAD ">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_parametros = jpacrpc.Parametros_registro_prueba_calidad(id_registro);
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'><br />");
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_registro[16] == 0) {
                    } else if (rol.equals("Administrador")) {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar prueba de calidad'></span> Registrar prueba de calidad<br />");
                        out.print("<span class='fa fa-eraser fa-size_small' onclick='Form_limpiar_cabecera()' title='Limpiar estaciones horarias'></span> Limpiar estaciones horarias<br />");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar prueba de calidad'></span> Registrar prueba de calidad<br />");
                    }
                }
                out.print("<h3>Pruebas de calidad</h3>");
                //<editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACIÓN">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:600px;position: absolute;top: 5%;left:15%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_limpiar_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Limpiar Estación</h3>");
                out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                out.print("<br />Seleccionar parámetro y estación horaria para la limpiar información.<br /><br />");
                out.print("<table>");
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    out.print("<tr>");
                    out.print("<form action='Registro?opc=36' method='post' name='Form_" + obj_parametros[0] + "' onsubmit='checkSubmit();'>");
                    //out.print("<form action='Registro?opc=14' method='post' name='Form_" + obj_parametros[0] + "'>");
                    out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                    String Valid = "[" + obj_parametros[2].toString() + "]";
                    if (idValid.toString().contains(Valid)) {
                        out.print("<td><b> Cada(1)hora y media</b></td>");
                    } else {
                        out.print("<td><b> Cada(" + obj_parametros[6] + ")horas</b></td>");
                    }
                    if (idValid.toString().contains(Valid)) {
                        out.print("<td>");
                        out.print("<select style='width:100px' name='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' id='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' >");
                        out.print("<option value='0' >toma</option>");
                        out.print("<option value='1' >hora 1</option>");
                        out.print("<option value='2' >hora 2</option>");
                        out.print("<option value='3' >hora 3</option>");
                        out.print("<option value='4' >hora 4</option>");
                        out.print("<option value='5' >hora 5</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar_" + obj_parametros[2] + "');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");
                    } else if ((Integer) obj_parametros[6] == 1) {
                        out.print("<td>");
                        out.print("<select style='width:100px' name='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' id='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' >");
                        out.print("<option value='0' >toma</option>");
                        out.print("<option value='1' >hora 1</option>");
                        out.print("<option value='2' >hora 2</option>");
                        out.print("<option value='3' >hora 3</option>");
                        out.print("<option value='4' >hora 4</option>");
                        out.print("<option value='5' >hora 5</option>");
                        out.print("<option value='6' >hora 6</option>");
                        out.print("<option value='7' >hora 7</option>");
                        out.print("<option value='8' >hora 8</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar_" + obj_parametros[2] + "');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");
                    } else if ((Integer) obj_parametros[6] == 2) {
                        out.print("<td>");
                        out.print("<select style='width:100px' name='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' id='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' >");
                        out.print("<option value='0' >toma</option>");
                        out.print("<option value='1' >hora 1 y 2</option>");
                        out.print("<option value='2' >hora 3 y 4</option>");
                        out.print("<option value='3' >hora 5 y 6</option>");
                        out.print("<option value='4' >hora 7 y 8</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar_" + obj_parametros[2] + "');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");
                    } else if ((Integer) obj_parametros[6] == 3) {
                        out.print("<td>");
                        out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                        out.print("<option value='0' >toma</option>");
                        out.print("<option value='1' >hora 1, 2 y 3 </option>");
                        out.print("<option value='2' >hora 4, 5 y 6 </option>");
                        out.print("<option value='4' >hora 7 y 8 </option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");

                    } else if ((Integer) obj_parametros[6] == 4) {
                        out.print("<td>");
                        out.print("<select style='width:100px' name='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' id='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' >");
                        out.print("<option value='0' >toma</option>");
                        out.print("<option value='1' >hora 1-2-3-4</option>");
                        out.print("<option value='2' >hora 5-6-7-8</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar_" + obj_parametros[2] + "');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");
                    } else if ((Integer) obj_parametros[6] == 8) {
                        out.print("<td>");
                        out.print("<select style='width:100px' name='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' id='Cbx_frecuencia_limpiar_" + obj_parametros[2] + "' >");
                        out.print("<option value='0' >toma</option>");
                        out.print("<option value='1' >hora 8</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar_" + obj_parametros[2] + "');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</td>");
                    }
                    out.print("<td>");
                    out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                    out.print("<input type='hidden' name='Id_parametro' id='Id_parametro' value='" + obj_parametros[2] + "' />");
                    out.print("<input type='submit' style='width:100px' value='Limpiar'/>");
                    out.print("</td>");
                    out.print("</form>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><hr /></td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                //out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='JAVASCRIPT:FormLimpiar.submit()'><img src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Registro'></a><br />");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:800px;position: absolute;top: 5%;left:15%;'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Registro pruebas calidad</h3>");
                out.print("<table>");
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                    if (rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || rol.equals("Administrador") || rol.equals("Documental")) {
                        out.print("<tr>");
                        out.print("<form action='Registro?opc=14' method='post' name='Form_" + obj_parametros[0] + "' onsubmit='checkSubmit();'>");
                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                        String Valid = "[" + obj_parametros[1].toString() + "]";
                        if (idValid.toString().contains(Valid)) {
                            out.print("<td><b>Cada(1)hora y media</b></td>");
                        } else {
                            out.print("<td><b>Cada(" + obj_parametros[6] + ")horas</b></td>");
                        }
                        if (idValid.toString().contains(Valid)) {
                            out.print("<td>");
                            out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                            out.print("<option value='0' >toma</option>");
                            out.print("<option value='1' >hora 1</option>");
                            out.print("<option value='2' >hora 2</option>");
                            out.print("<option value='3' >hora 3</option>");
                            out.print("<option value='4' >hora 4</option>");
                            out.print("<option value='5' >hora 5</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                        } else if ((Integer) obj_parametros[6] == 1) {
                            out.print("<td>");
                            out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                            out.print("<option value='0' >toma</option>");
                            out.print("<option value='1' >hora 1</option>");
                            out.print("<option value='2' >hora 2</option>");
                            out.print("<option value='3' >hora 3</option>");
                            out.print("<option value='4' >hora 4</option>");
                            out.print("<option value='5' >hora 5</option>");
                            out.print("<option value='6' >hora 6</option>");
                            out.print("<option value='7' >hora 7</option>");
                            out.print("<option value='8' >" + idValid + "</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                        } else if ((Integer) obj_parametros[6] == 2) {
                            out.print("<td>");
                            out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                            out.print("<option value='0'>toma</option>");
                            out.print("<option value='1'>hora 1 y 2</option>");
                            out.print("<option value='2'>hora 3 y 4</option>");
                            out.print("<option value='3'>hora 5 y 6</option>");
                            out.print("<option value='4'>hora 7 y 8</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                        } else if ((Integer) obj_parametros[6] == 3) {
                            out.print("<td>");
                            out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                            out.print("<option value='0' >toma</option>");
                            out.print("<option value='1' >hora 1, 2 y 3 </option>");
                            out.print("<option value='2' >hora 4, 5 y 6 </option>");
                            out.print("<option value='3' >hora 7 y 8 </option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                        } else if ((Integer) obj_parametros[6] == 4) {
                            out.print("<td>");
                            out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                            out.print("<option value='0' >toma</option>");
                            out.print("<option value='1' >hora 1-2-3-4</option>");
                            out.print("<option value='2' >hora 5-6-7-8</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                        } else if ((Integer) obj_parametros[6] == 8) {
                            out.print("<td>");
                            out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                            out.print("<option value='0' >toma</option>");
                            out.print("<option value='1' >hora 8</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</td>");
                        }
                        if (obj_parametros[7].equals("Estado")) {
                            out.print("<td><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='Cumple'/>Cumple<b>/</b><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='No cumple' />No cumple<b>/</b><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='N/A' checked='checked'/>N/A</td>");
                        } else if (obj_parametros[7].equals("Caracter")) {
                            out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + obj_parametros[2] + "' placeholder='Lote' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametros[2] + "');val1.add(Validate.Presence);</script></td>");
                        }
                        out.print("<td>");
                        out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                        out.print("<input type='hidden' name='Id_parametro' id='Id_parametro' value='" + obj_parametros[2] + "' />");
                        out.print("<input type='submit' style='width:100px' value='Registrar'/>");
                        out.print("</td>");
                        out.print("</form>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr>");
                        out.print("<form action='Registro?opc=14' method='post' name='Form_" + obj_parametros[0] + "' onsubmit='checkSubmit();'>");
                        if (obj_parametros[12].equals("Todos")) {
                            out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                            out.print("<td><b>Cada(" + obj_parametros[6] + ")horas</b></td>");
                            String Valid = "[" + obj_parametros[1].toString() + "]";
                            if (idValid.toString().contains(Valid)) {
                                out.print("<td>");
                                out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                                out.print("<option value='0' >toma</option>");
                                out.print("<option value='1' >hora 1</option>");
                                out.print("<option value='2' >hora 2</option>");
                                out.print("<option value='3' >hora 3</option>");
                                out.print("<option value='4' >hora 4</option>");
                                out.print("<option value='5' >hora 5</option>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</td>");
                            } else if ((Integer) obj_parametros[6] == 1) {
                                out.print("<td>");
                                out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                                out.print("<option value='0' >toma</option>");
                                out.print("<option value='1' >hora 1</option>");
                                out.print("<option value='2' >hora 2</option>");
                                out.print("<option value='3' >hora 3</option>");
                                out.print("<option value='4' >hora 4</option>");
                                out.print("<option value='5' >hora 5</option>");
                                out.print("<option value='6' >hora 6</option>");
                                out.print("<option value='7' >hora 7</option>");
                                out.print("<option value='8' >hora 8</option>");
                                out.print("<option value='8' >" + idValid + "</option>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</td>");
                            } else if ((Integer) obj_parametros[6] == 2) {
                                out.print("<td>");
                                out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                                out.print("<option value='0' >toma</option>");
                                out.print("<option value='1' >hora 1 y 2</option>");
                                out.print("<option value='2' >hora 3 y 4</option>");
                                out.print("<option value='3' >hora 5 y 6</option>");
                                out.print("<option value='4' >hora 7 y 8</option>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</td>");
                            } else if ((Integer) obj_parametros[6] == 3) {
                                out.print("<td>");
                                out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                                out.print("<option value='0' >toma</option>");
                                out.print("<option value='1' >hora 1, 2 y 3 </option>");
                                out.print("<option value='2' >hora 4, 5 y 6 </option>");
                                out.print("<option value='4' >hora 7 y 8 </option>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</td>");
                            } else if ((Integer) obj_parametros[6] == 4) {
                                out.print("<td>");
                                out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                                out.print("<option value='0' >toma</option>");
                                out.print("<option value='1' >hora 1-2-3-4</option>");
                                out.print("<option value='2' >hora 5-6-7-8</option>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</td>");
                            } else if ((Integer) obj_parametros[6] == 8) {
                                out.print("<td>");
                                out.print("<select style='width:100px' name='Cbx_frecuencia_" + obj_parametros[2] + "' id='Cbx_frecuencia_" + obj_parametros[2] + "' >");
                                out.print("<option value='0' >toma</option>");
                                out.print("<option value='1' >hora 8</option>");
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_" + obj_parametros[2] + "');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("</td>");
                            }
                            if (obj_parametros[7].equals("Estado")) {
                                out.print("<td><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='Cumple'/>Cumple<b>/</b><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='No cumple' />No cumple<b>/</b><input type='radio' name='Vlr_parametro_" + obj_parametros[2] + "' value='N/A' checked='checked'/>N/A</td>");
                            } else if (obj_parametros[7].equals("Caracter")) {
                                out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + obj_parametros[2] + "' placeholder='Lote'/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametros[2] + "');val1.add(Validate.Presence);;</script></td>");
                            }
                            out.print("<td>");
                            out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                            out.print("<input type='hidden' name='Id_parametro' id='Id_parametro' value='" + obj_parametros[2] + "' />");
                            out.print("<input type='submit' style='width:100px' value='Registrar'/>");
                            out.print("</td>");
                            out.print("</form>");
                            out.print("</tr>");
                        }
                    }
                    out.print("<tr><td colspan='4'><hr /></td></tr>");
                }
                out.print("</table>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_parametros = jpacrpc.Parametros_tomas_registro_prueba_calidad(id_registro);
                if (lst_parametros == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos de pruebas de calidad</b>");
                    out.print("</center>");
                } else {
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    out.print("<th colspan='2'>1</th>");
                    out.print("<th colspan='2'>2</th>");
                    out.print("<th colspan='2'>3</th>");
                    out.print("<th colspan='2'>4</th>");
                    out.print("<th colspan='2'>5</th>");
                    out.print("<th colspan='2'>6</th>");
                    out.print("<th colspan='2'>7</th>");
                    out.print("<th colspan='2'>8</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3] + "</td>");
                        if ((Integer) obj_parametros[4] == 1) {
                            String Valid = "[" + obj_parametros[2].toString() + "]";
                            int cols = 2;
                            if (!idValid.toString().contains(Valid)) {
                                cols = 2;
                            } else {
                                cols = 3;
                            }
                            if (obj_parametros[5] == null) {
                                out.print("<td align='center' colspan='" + cols + "'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td align='center' colspan='" + cols + "'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'  colspan='" + cols + "'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center' colspan='" + cols + "'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td align='center' colspan='" + cols + "'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td align='center' colspan='" + cols + "'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center' colspan='" + cols + "'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td align='center' colspan='" + cols + "'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td align='center' colspan='" + cols + "'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center' colspan='" + cols + "'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                            if (obj_parametros[8] == null) {
                                out.print("<td align='center' colspan='" + cols + "'><b></b></td>");
                            } else if (obj_parametros[8].equals("No cumple")) {
                                out.print("<td align='center' colspan='" + cols + "'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[16].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center' colspan='" + cols + "'>" + obj_parametros[8] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='coordinadora'>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                                }
                            }
                            if (obj_parametros[9] == null) {
                                out.print("<td align='center' colspan='" + cols + "'><b></b></td>");
                            } else if (obj_parametros[9].equals("No cumple")) {
                                out.print("<td align='center' colspan='" + cols + "'><b class='rojo'>" + obj_parametros[9] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[17].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b>" + obj_parametros[9] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center' colspan='" + cols + "'>" + obj_parametros[9] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='coordinadora'>" + obj_parametros[9] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td align='center' colspan='" + cols + "'><b class='calidad'>" + obj_parametros[9] + "</b></td>");
                                }
                            }
                            Valid = "[" + obj_parametros[2].toString() + "]";
                            if (!idValid.toString().contains(Valid)) {
                                if (obj_parametros[10] == null) {
                                    out.print("<td align='center' colspan='2'><b></b></td>");
                                } else if (obj_parametros[10].equals("No cumple")) {
                                    out.print("<td align='center' colspan='2'><b class='rojo'>" + obj_parametros[10] + "</b></td>");
                                } else {
                                    String[] arg_responsables = obj_parametros[18].toString().split("/");
                                    if (arg_responsables[0].equals("Administrador")) {
                                        out.print("<td align='center' colspan='2'><b>" + obj_parametros[10] + "</b></td>");
                                    } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                        out.print("<td align='center' colspan='2'>" + obj_parametros[10] + "</td>");
                                    } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                        out.print("<td align='center' colspan='2'><b class='coordinadora'>" + obj_parametros[10] + "</b></td>");
                                    } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                        out.print("<td align='center' colspan='2'><b class='calidad'>" + obj_parametros[10] + "</b></td>");
                                    }
                                }
                                if (obj_parametros[11] == null) {
                                    out.print("<td align='center' colspan='2'><b></b></td>");
                                } else if (obj_parametros[11].equals("No cumple")) {
                                    out.print("<td align='center' colspan='2'><b class='rojo'>" + obj_parametros[11] + "</b></td>");
                                } else {
                                    String[] arg_responsables = obj_parametros[19].toString().split("/");
                                    if (arg_responsables[0].equals("Administrador")) {
                                        out.print("<td align='center' colspan='2'><b>" + obj_parametros[11] + "</b></td>");
                                    } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                        out.print("<td align='center' colspan='2'>" + obj_parametros[11] + "</td>");
                                    } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                        out.print("<td align='center' colspan='2'><b class='coordinadora'>" + obj_parametros[11] + "</b></td>");
                                    } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                        out.print("<td align='center' colspan='2'><b class='calidad'>" + obj_parametros[11] + "</b></td>");
                                    }
                                }
                                if (obj_parametros[12] == null) {
                                    out.print("<td align='center' colspan='2'><b></b></td>");
                                } else if (obj_parametros[12].equals("No cumple")) {
                                    out.print("<td align='center' colspan='2'><b class='rojo'>" + obj_parametros[12] + "</b></td>");
                                } else {
                                    String[] arg_responsables = obj_parametros[20].toString().split("/");
                                    if (arg_responsables[0].equals("Administrador")) {
                                        out.print("<td align='center' colspan='2'><b>" + obj_parametros[12] + "</b></td>");
                                    } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                        out.print("<td align='center' colspan='2'>" + obj_parametros[12] + "</td>");
                                    } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                        out.print("<td align='center' colspan='2'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                    } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                        out.print("<td align='center' colspan='2'><b class='calidad'>" + obj_parametros[12] + "</b></td>");
                                    }
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 2) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='4' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='4' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='4' align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='4' align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='4' align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='4' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td colspan='4' align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td colspan='4' align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='4' align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='4' align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='4' align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='4' ='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td colspan='4' align='center'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td colspan='4' align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='4' align='center'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='4' align='center'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='4' align='center'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='4' align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                            if (obj_parametros[8] == null) {
                                out.print("<td colspan='4' align='center'><b></b></td>");
                            } else if (obj_parametros[8].equals("No cumple")) {
                                out.print("<td colspan='4' align='center'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[16].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='4' align='center'><b>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='4' align='center'>" + obj_parametros[8] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='4' align='center'><b class='coordinadora'>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='4' align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                                }
                            }

                        } else if ((Integer) obj_parametros[4] == 3) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='6' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='6' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='6' align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='6' align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='6' align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='6' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td colspan='6' align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td colspan='6' align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='6' align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='6' align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='6' align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='6' align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td colspan='4' align='center'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td colspan='4' align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='4' align='center'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='4' align='center'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='4' align='center'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='4' align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 4) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='8' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='8' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='8' align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='8' align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='8' align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='8' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td colspan='8' align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td colspan='8' align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='8' align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='8' align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='8' align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='8' align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 8) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='16' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='16' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='16'  align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='16'  align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='16'  align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad") || arg_responsables[0].equals("Documental")) {
                                    out.print("<td colspan='16' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_implementos")) {
                // <editor-fold desc="IMPLEMENTOS">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                String fecha[] = obj_registro[2].toString().split("-");
                String fecha_version = fecha[0] + "." + fecha[1] + fecha[2];
                double fecha_version_decimal = Double.parseDouble(fecha_version);
                lst_implementos = jpacrip.Implementos_registro(id_registro);
                Object[] obj_implementos = (Object[]) lst_implementos.get(0);
                String seriales = null;
                try {
                    seriales = obj_implementos[2].toString() + "-" + obj_implementos[3].toString() + "-" + obj_implementos[4].toString() + "-" + obj_implementos[11].toString() + "-" + obj_implementos[16].toString();
                } catch (Exception ex) {
                }
                String[] arg_seriales = null;
                if (seriales == null) {
                    arg_seriales = null;
                } else {
                    arg_seriales = seriales.toString().split("-");
                }
                out.print("<div id='content'>");
                //<editor-fold defaultstate="collapsed" desc="ELECTRODOS">
                out.print("<h3>Electrodos / Implementos y Seriales</h3>");
                if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                    if ((Integer) obj_registro[16] == 1) {
                        out.print("<form action='Registro?opc=9' method='post' onsubmit='checkSubmit();'>");
                    }
                }
                out.print("<fieldset>");
                out.print("<legend>Electrodos</legend>");
                if (!obj_registro[65].toString().equals("R-PRF-010")) {
                    try {
                        if (rol.equals("Consulta") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                            out.print("<b>Bocas : </b><input type='text' disabled='true' name='Txt_electrodos_bocas' id='Txt_electrodos_bocas' style='width:250px' placeholder='Electrodos en Bocas' value='" + ((obj_implementos[5] == null) ? "" : obj_implementos[5]) + "' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        } else {
                            out.print("<b>Bocas : </b><input type='text' name='Txt_electrodos_bocas' id='Txt_electrodos_bocas' style='width:250px' placeholder='Electrodos en Bocas' value='" + ((obj_implementos[5] == null) ? "" : obj_implementos[5]) + "' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        }
                    } catch (Exception ex) {
                        if (rol.equals("Consulta") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                            out.print("<b>Bocas : </b><input type='text' disabled='true' name='Txt_electrodos_bocas' id='Txt_electrodos_bocas' style='width:250px' placeholder='Electrodos en Bocas' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        } else {
                            out.print("<b>Bocas : </b><input type='text' name='Txt_electrodos_bocas' id='Txt_electrodos_bocas' style='width:250px' placeholder='Electrodos en Bocas' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        }
                    }
                    out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                } else {
                    out.print("<input type='hidden' name='Txt_electrodos_bocas' id='Txt_electrodos_bocas' value='N/A' />");
                }
                if (!obj_registro[65].toString().equals("R-PRF-056")) {
                    try {
                        if (rol.equals("Consulta") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                            out.print("<b>Colas : </b><input type='text' disabled='true' name='Txt_electrodos_colas' id='Txt_electrodos_colas' style='width:250px' placeholder='Electrodos en Colas' value='" + ((obj_implementos[6] == null) ? "" : obj_implementos[6]) + "' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        } else {
                            out.print("<b>Colas : </b><input type='text' name='Txt_electrodos_colas' id='Txt_electrodos_colas' style='width:250px' placeholder='Electrodos en Colas' value='" + ((obj_implementos[6] == null) ? "" : obj_implementos[6]) + "' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        }
                    } catch (Exception ex) {
                        if (rol.equals("Consulta") || rol.equals("Inspectora-Calidad") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                            out.print("<b>Colas : </b><input type='text' disabled='true' name='Txt_electrodos_colas' id='Txt_electrodos_colas' style='width:250px' placeholder='Electrodos en Colas' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        } else {
                            out.print("<b>Colas : </b><input type='text' name='Txt_electrodos_colas' id='Txt_electrodos_colas' style='width:250px' placeholder='Electrodos en Colas' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        }
                    }
                } else {
                    out.print("<b>Colas : </b><input type='text' name='Txt_electrodos_colas' id='Txt_electrodos_colas' style='width:250px;pointer-events:none;background: #cacaca;' placeholder='Electrodos en Colas' onchange='javascript:this.value=this.value.toUpperCase();' value='N/A'/>");

                }
                out.print("</fieldset>");
                out.print("<br /><br />");
                out.print("<fieldset>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="IMPLEMENTOS">
                out.print("<legend>Implementos</legend>");
                out.print("<table>");
                out.print("<tr>");
                out.print("<td><b>Tijeras</b></td>");
                try {
                    if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                        out.print("<td><input type='radio' disabled='true' name='Rdb_tijeras' value='1' checked='checked' />SI<br /><input type='radio' disabled='true' name='Rdb_tijeras' value='0' />NO</td>");
                    } else {
                        out.print("<td><input type='radio' name='Rdb_tijeras' value='1' " + (((Integer) obj_implementos[7] == 1) ? "checked='checked'" : "") + " />SI<br /><input type='radio' name='Rdb_tijeras' value='0' " + (((Integer) obj_implementos[7] == 0) ? "checked='checked'" : "") + "/>NO</td>");
                    }
                } catch (Exception ex) {
                    if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                        out.print("<td><input type='radio' disabled='true' name='Rdb_tijeras' value='1' />SI<br /><input type='radio' disabled='true' name='Rdb_tijeras' value='0' checked='checked'/>NO</td>");
                    } else {
                        out.print("<td><input type='radio' name='Rdb_tijeras' value='1' />SI<br /><input type='radio' name='Rdb_tijeras' value='0' checked='checked'/>NO</td>");
                    }
                }
                out.print("<td></td>");
                out.print("<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>");
                if (obj_registro[65].toString().equals("R-PRF-011")) {
                    if (fecha_version_decimal >= 2015.0520) {
                        out.print("<td><b>Dispositivo apertura bolsa</b></td>");
                    } else {
                        out.print("<td><b>Espatula</b></td>");
                    }
                } else {
                    out.print("<td><b>Espatula</b></td>");
                }
                try {
                    if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                        out.print("<td><input type='radio' disabled='true' name='Rdb_espatula' value='1' " + (((Integer) obj_implementos[8] == 1) ? "checked='checked'" : "") + "/>SI<br /><input type='radio' disabled='true' name='Rdb_espatula' value='0' " + (((Integer) obj_implementos[8] == 0) ? "checked='checked'" : "") + "/>NO</td>");
                    } else {
//                        out.print("<td><input type='radio' name='Rdb_espatula' value='1' checked='checked'/>SI<br /><input type='radio' name='Rdb_espatula' value='0' />NO</td>");
                        out.print("<td><input type='radio' name='Rdb_espatula' value='1' " + (((Integer) obj_implementos[8] == 1) ? "checked='checked'" : "") + "/>SI<br /><input type='radio' name='Rdb_espatula' value='0' " + (((Integer) obj_implementos[8] == 0) ? "checked='checked'" : "") + "/>NO</td>");
                    }
                } catch (Exception ex) {
                    if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                        out.print("<td><input type='radio' disabled='true' name='Rdb_espatula' value='1' />SI<br /><input type='radio' disabled='true' name='Rdb_espatula' value='0' checked='checked'/>NO</td>");
                    } else {
                        out.print("<td><input type='radio' name='Rdb_espatula' value='1' />SI<br /><input type='radio' name='Rdb_espatula' value='0' checked='checked'/>NO</td>");
                    }
                }
                out.print("<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>");
                out.print("<td><b>LLaves</b></td>");
                try {
                    if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                        out.print("<td><input type='radio' disabled='true' name='Rdb_llaves' value='1' checked='checked' />SI<br /><input type='radio' disabled='true' name='Rdb_llaves' value='0' />NO</td>");
                    } else {
                        out.print("<td><input type='radio' name='Rdb_llaves' value='1' " + (((Integer) obj_implementos[9] == 1) ? "checked='checked'" : "") + " />SI<br /><input type='radio' name='Rdb_llaves' value='0' " + (((Integer) obj_implementos[9] == 0) ? "checked='checked'" : "") + "/>NO</td>");
                    }
                } catch (Exception ex) {
                    if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                        out.print("<td><input type='radio' disabled='true' name='Rdb_llaves' value='1' />SI<br /><input type='radio' disabled='true' name='Rdb_llaves' value='0' checked='checked'/>NO</td>");
                    } else {
                        out.print("<td><input type='radio' name='Rdb_llaves' value='1' />SI<br /><input type='radio' name='Rdb_llaves' value='0' checked='checked'/>NO</td>");
                    }
                }
                out.print("<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>");
                if (!obj_registro[65].toString().equals("R-PRF-010")) {
                    out.print("<td><b>Pinzas</b></td>");
                    try {
                        if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                            out.print("<td><input type='radio' disabled='true' name='Rdb_pinzas' value='1' checked='checked' />SI<br /><input type='radio' disabled='true' name='Rdb_pinzas' value='0' />NO</td>");
                        } else {
                            out.print("<td><input type='radio' name='Rdb_pinzas' value='1' " + (((Integer) obj_implementos[10] == 1) ? "checked='checked'" : "") + " />SI<br /><input type='radio' name='Rdb_pinzas' value='0' " + (((Integer) obj_implementos[10] == 0) ? "checked='checked'" : "") + "/>NO</td>");
                        }
                    } catch (Exception ex) {
                        if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || (Integer) obj_registro[16] == 0) {
                            out.print("<td><input type='radio' disabled='true' name='Rdb_pinzas' value='1' />SI<br /><input type='radio' disabled='true' name='Rdb_pinzas' value='0' checked='checked'/>NO</td>");
                        } else {
                            out.print("<td><input type='radio' name='Rdb_pinzas' value='1' />SI<br /><input type='radio' name='Rdb_pinzas' value='0' checked='checked'/>NO</td>");
                        }
                    }
                } else {
                    out.print("<input type='hidden' name='Rdb_pinzas' value='0' />");
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("</fieldset>");
                out.print("<br /><br />");
                out.print("<fieldset>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SERIALES">
                out.print("<legend>Seriales</legend>");
                String serial = "";
                lst_seriales = jpacsra.Seriales_metrologia();
                String selecion_seriales = "";
                if (lst_seriales == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No se generaron datos de seriales.</b>");
                    out.print("</center>");
                } else {
                    out.print("<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar serial'/>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<div style='display:block'>");
                    out.print("<div style='float:left'>");
                    out.print("<table id='resultados' style='width:700px' class='table'>");
                    out.print("<tr>");
                    out.print("<th></th>");
                    out.print("<th>Serial</th>");
                    out.print("<th>Tipo serial</th>");
                    out.print("<th colspan='2'>Fecha Inspección/Verificación</th>");
                    out.print("<th colspan='2'>Fecha Veficación/Calibración</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_seriales.size(); i++) {
                        String[] Arg_seriales = lst_seriales.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                        for (int l = 0; l < Arg_seriales.length; l++) {
                            String[] obj_seriales = Arg_seriales[l].toString().split("---");
                            int control_metrologia = 0;
                            if (Integer.parseInt(obj_seriales[11]) == 0) {
                                out.print("<tr class='rojo'>");
                                control_metrologia++;
                            } else if (Integer.parseInt(obj_seriales[11]) == 1) {
                                out.print("<tr class='naranja'>");
                            } else if (Integer.parseInt(obj_seriales[11]) == 2) {
                                out.print("<tr>");
                            }
                            out.print("<td>");
                            if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                                try {
                                    if (arg_seriales != null) {
                                        for (int j = 0; j < arg_seriales.length; j++) {
                                            if (arg_seriales[j].equals(obj_seriales[3])) {
                                                serial = arg_seriales[j];
                                            }
                                        }
                                        if (obj_seriales[3].equals(serial) && (Integer) obj_registro[16] != 0) {
                                            out.print("<input type='checkbox' checked " + ((control_metrologia > 0) ? "disabled='true'" : "") + " id='Ckb_serial_" + i + "' name='Ckb_serial_" + i + "' value='[" + obj_seriales[3].toString() + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]' onclick=\"SeleccionImplementos(this);\" />");
                                            selecion_seriales = selecion_seriales + "[" + obj_seriales[3] + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]";
                                        } else {
                                            out.print("<input type='checkbox' " + ((control_metrologia > 0) ? "disabled='true'" : "") + " id='Ckb_serial_" + i + "' name='Ckb_serial_" + i + "' value='[" + obj_seriales[3].toString() + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]' onclick=\"SeleccionImplementos(this);\" />");
                                        }
                                    } else {
                                        out.print("<input type='checkbox' " + ((control_metrologia > 0) ? "disabled='true'" : "") + " id='Ckb_serial_" + i + "' name='Ckb_serial_" + i + "' value='[" + obj_seriales[3].toString() + "/" + obj_seriales[1] + "/" + obj_seriales[14] + "/" + obj_seriales[15] + "]' onclick=\"SeleccionImplementos(this);\" />");
                                    }
                                } catch (Exception ex) {
                                }
                            }
                            out.print("</td>");
//                            out.print("<td>");
//                            out.print("<input type='checkbox' id='Ckb_serial_" + i + "' name='Ckb_serial_" + i + "' value='[" + obj_seriales[2].toString() + "/" + obj_seriales[0] + "/" + obj_seriales[4] + "/" + obj_seriales[5] + "]' onclick=\"SeleccionImplementos(this);\"/>");
//                            out.print("</td>");
                            out.print("<td>" + obj_seriales[3] + "</td>");
                            out.print("<td>" + obj_seriales[1] + "</td>");
                            if (obj_seriales[14].equals("N-A")) {
                                out.print("<td colspan='2' align='center' style='background-color:#eee;'>N/A</td>");
                            } else {
                                out.print("<td align='center'>Ult." + obj_seriales[13].split("-")[0] + "<br />" + obj_seriales[4] + "</td>");
                                out.print("<td align='center'>Prox." + obj_seriales[13].split("-")[0] + "<br />" + obj_seriales[6] + "</td>");
                            }
                            if (obj_seriales[15].equals("N-A")) {
                                out.print("<td colspan='2' align='center' style='background-color:#eee;'>N/A</td>");
                            } else {
                                out.print("<td align='center'>Ult." + obj_seriales[13].split("-")[1] + "<br />" + obj_seriales[7] + "</td>");
                                out.print("<td align='center'>Prox." + obj_seriales[13].split("-")[1] + "<br />" + obj_seriales[9] + "</td>");
                            }
                            i = lst_seriales.size();
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                out.print("</div>");
                out.print("<div style='float:left;width:220px'>");
                out.print("<div style='float:left;'>");
//                out.print("<br />");
                out.print("<fieldset style='float:right;width:200px'>");
                out.print("<legend>Selección</legend>");
                if (obj_implementos[2].toString() == null ? "" == null : obj_implementos[2].toString().equals("")) {
                    out.print("<b>Calibrador(es) :</b> Sin asignar<br /><hr />");
                } else {
                    out.print("<b>Calibrador(es) :</b><br /> " + obj_implementos[2].toString().replace("-", "<br />") + "<hr />");
                }
                if (fecha_version_decimal >= 2017.0415) {
                    if (obj_implementos[3].toString() == null ? "" == null : obj_implementos[3].toString().equals("")) {
                        out.print("<b>Regla(s) :</b> Sin asignar<br /><hr />");
                    } else {
                        out.print("<b>Regla(s) :</b><br />" + obj_implementos[3].toString().replace("-", "<br />") + "<hr />");
                    }
                } else {
                    if (obj_implementos[3].toString() == null ? "" == null : obj_implementos[3].toString().equals("")) {
                        out.print("<b>Regla(s) larga :</b> Sin asignar<br /><hr />");
                    } else {
                        out.print("<b>Regla(s) larga :</b><br />" + obj_implementos[3].toString().replace("-", "<br />") + "<hr />");
                    }
                    if (obj_implementos[4].toString() == null ? "" == null : obj_implementos[4].toString().equals("")) {
                        out.print("<b>Regla(s) corta :</b> Sin asignar<br /><hr />");
                    } else {
                        out.print("<b>Regla(s) corta :</b><br />" + obj_implementos[4].toString().replace("-", "<br />") + "<hr />");
                    }
                }
                if (obj_registro[65].toString().equals("R-PRF-013") || obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-019")) {
                    if (fecha_version_decimal >= 2018.0521) {
                        if (obj_implementos[16].toString() == null ? "" == null : obj_implementos[16].toString().equals("")) {
                            out.print("<b>Lainas :</b> Sin asignar<br /><hr />");
                        } else {
                            out.print("<b>Lainas :</b><br />" + obj_implementos[16].toString().replace("-", "<br />") + "<hr />");
                        }
                    }
                    if (fecha_version_decimal >= 2015.0520) {
                        if (obj_implementos[11].toString() == null ? "" == null : obj_implementos[11].toString().equals("")) {
                            out.print("<b>Indicador digital :</b> Sin asignar<br /><hr />");
                        } else {
                            out.print("<b>Indicador digital :</b><br />" + obj_implementos[11].toString().replace("-", "<br />") + "<br />");
                        }
                    }
                }
                out.print("</fieldset>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cleaner'></div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("</fieldset>");
                out.print("<br />");
                out.print("<input type='hidden' name='Txt_seleccion_seriales' id='Txt_seleccion_seriales' value='" + selecion_seriales + "'/>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
//                out.print("<input type='hidden' name='Cantidad_seriales' id='Cantidad_seriales' value='" + lst_seriales.size() + "' />");
                if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                    if ((Integer) obj_registro[16] == 1) {
                        out.print("<input type='submit' value='Registrar'>");
                        out.print("</form>");
                        out.print("<br /><br />");
                    }
                }
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_parada_maquina")) {
                // <editor-fold desc="PARADAS DE MAQUINA">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'>");
                out.print("<h3>Paradas de máquina</h3>");
                out.print("<table class='table4' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>PRODUCCION</th>");
                out.print("<th>MAQUINARIA</th>");
                out.print("</tr>");
                out.print("<tr>");
                //<editor-fold defaultstate="collapsed" desc="PM PRODUCCION">
                out.print("<td valign='top' style='border-right: 1px solid #15aabf;width:50%'><dir>");
                lst_produccion = jpacpmq.Parada_maquinas_categoria(1);
                if (lst_produccion == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay categorías en paradas por <br />producción registradas</b>");
                    out.print("</center>");
                } else {
                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                        if ((Integer) obj_registro[16] != 0) {
                            out.print("<form action='Registro?opc=7' method='post' onsubmit='checkSubmit();'>");
                            out.print("<select name='Cbx_parada_produccion' id='Cbx_parada_produccion' >");
                            out.print("<option value='0' >Seleccionar parada producción</option>");
                            for (int i = 0; i < lst_produccion.size(); i++) {
                                Object[] obj_produccion = (Object[]) lst_produccion.get(i);
                                if (obj_registro[65].toString().equals("R-PRF-010")) {
                                    if (obj_produccion[3] == obj_registro[64]) {
                                        out.print("<option value='" + obj_produccion[0] + "'>" + obj_produccion[1] + "</option>");
                                    }
                                } else if (obj_produccion[3] == obj_registro[64] || (Integer) obj_produccion[3] == 1) {
                                    out.print("<option value='" + obj_produccion[0] + "'>" + obj_produccion[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_parada_produccion');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                            out.print("<input type='text' style='width:50px' id='Txt_cantidad_produccion' name='Txt_cantidad_produccion' placeholder='Minutos' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_produccion');val1.add(Validate.Presence);val1.add(Validate.Total_numero);</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp");
                            out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                            out.print("<input type='hidden' name='Id_parada_maquina' id='Id_parada_maquina' value='2' />");
                            out.print("<input type='submit' value='Registrar' style='width:80px'/>");
                            out.print("</form>");
                            out.print("<br />");
                        }
                    }
                    lst_produccion_consulta = jpacpmq.Parada_maquinas_categoria_registradas(1, id_registro);
                    if (lst_produccion_consulta == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay paradas por producción</b>");
                        out.print("</center>");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        out.print("<th>Minutos</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                            if ((Integer) obj_registro[16] == 1) {
                                out.print("<th>Quitar</th>");
                            }
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_produccion_consulta.size(); i++) {
                            Object[] obj_paradas_produccion = (Object[]) lst_produccion_consulta.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_paradas_produccion[3] + "</td>");
                            out.print("<td align='center'>" + obj_paradas_produccion[4] + "</td>");
                            total = total + (Integer) obj_paradas_produccion[4];
                            if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                                if ((Integer) obj_registro[16] == 0) {
                                } else {
                                    out.print("<td align='center'><span class='fa fa-times fa-size_small' onclick='EliminarParadaMaquina(" + obj_paradas_produccion[0] + "," + id_registro + ")' title='Quitar PNC'></span></td>");
                                    //out.print("<td align='center'><a href='#' onclick='EliminarParadaMaquina(" + obj_paradas_produccion[0] + "," + id_registro + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Quitar PNC' /></a></td>");
                                }
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b>" + total + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                }
                out.print("</td>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PM MAQUINARIA">
                out.print("<td valign='top' style='width:50%'><dir>");
                lst_mantenimiento = jpacpmq.Parada_maquinas_categoria(2);
                if (lst_mantenimiento == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay categorías en paradas por <br />mantenimiento registradas</b>");
                    out.print("</center>");
                } else {
                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                        if ((Integer) obj_registro[16] != 0) {
                            out.print("<form action='Registro?opc=7' method='post' onsubmit='checkSubmit();'>");
                            out.print("<select name='Cbx_parada_mantenimiento' id='Cbx_parada_mantenimiento' >");
                            out.print("<option value='0' >Seleccionar parada mantenimiento</option>");
                            for (int i = 0; i < lst_mantenimiento.size(); i++) {
                                Object[] obj_mantenimiento = (Object[]) lst_mantenimiento.get(i);
                                if (obj_registro[65].toString().equals("R-PRF-010")) {
                                    if (obj_mantenimiento[3] == obj_registro[64]) {
                                        out.print("<option value='" + obj_mantenimiento[0] + "'>" + obj_mantenimiento[1] + "</option>");
                                    }
                                } else if (obj_mantenimiento[3] == obj_registro[64] || (Integer) obj_mantenimiento[3] == 1) {
                                    out.print("<option value='" + obj_mantenimiento[0] + "'>" + obj_mantenimiento[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_parada_mantenimiento');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                            out.print("<input type='text' style='width:50px' id='Txt_cantidad_mantenimiento' name='Txt_cantidad_mantenimiento' placeholder='Minutos' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_mantenimiento');val1.add(Validate.Presence);val1.add(Validate.Total_numero);</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp");
                            out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                            out.print("<input type='hidden' name='Id_parada_maquina' id='Id_parada_maquina' value='1' />");
                            out.print("<input type='submit' value='Registrar' style='width:80px'/>");
                            out.print("</form>");
                            out.print("<br />");
                        }
                    }
                    lst_mantenimiento_consulta = jpacpmq.Parada_maquinas_categoria_registradas(2, id_registro);
                    if (lst_mantenimiento_consulta == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay paradas por mantenimiento</b>");
                        out.print("</center>");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        out.print("<th>Minutos</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                            if ((Integer) obj_registro[16] == 0) {
                            } else {
                                out.print("<th >Quitar</th>");
                            }
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_mantenimiento_consulta.size(); i++) {
                            Object[] obj_paradas_mantenimiento = (Object[]) lst_mantenimiento_consulta.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_paradas_mantenimiento[3] + "</td>");
                            out.print("<td align='center'>" + obj_paradas_mantenimiento[4] + "</td>");
                            total = total + (Integer) obj_paradas_mantenimiento[4];
                            if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                                if ((Integer) obj_registro[16] == 0) {
                                } else {
                                    out.print("<td align='center'><span class='fa fa-times fa-size_small' onclick='EliminarParadaMaquina(" + obj_paradas_mantenimiento[0] + "," + id_registro + ")' title='Quitar PNC'></span></td>");
                                }
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b>" + total + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                }
                out.print("</td>");
                //</editor-fold>
                out.print("</tr>");
                out.print("</table>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_parada_maquina_pmtt")) {
                //<editor-fold defaultstate="collapsed" desc="PARADAS DE MAQUINA PLUMATT">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                id_prda = Integer.parseInt(pageContext.getRequest().getAttribute("id_prda").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                if (id_prda > 0) {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_del' style='width:55% !important'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Eliminar tomas</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    lst_prdas = jpacpmq.Consultar_idpard_pmtt_idcate(id_prda);
                    if (lst_prdas != null) {
                        Object[] obj_prd = (Object[]) lst_prdas.get(0);
                        out.print("<h3>" + obj_prd[1] + "</h3>");
                    } else {
                        out.print("<h3>Error</h3>");
                    }
                    out.print("<form action='Registro?opc=29&Id_registro=" + id_registro + "&temp=1' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='' style='display: flex;'>");

                    out.print("<table class='table' align='center'>");
                    out.print("<tr>");
                    out.print("<th>HORA</th>");
                    out.print("<th>MIN</th>");
                    out.print("<th><i class='fas fa-trash'></i></th>");
                    out.print("</tr>");
                    lst_prdas = jpacpmq.Consultar_idpard_pmtt(id_registro, id_prda);
                    if (lst_prdas != null) {
                        for (int i = 0; i < lst_prdas.size(); i++) {
                            Object[] Obj_prds = (Object[]) lst_prdas.get(i);
                            out.print("<tr align='center'>");
                            out.print("<th style='background: #15aabf52; color: black; font-weight: 500;border-radius: 0;'>" + Obj_prds[5] + "</th>");
                            out.print("<td>" + Obj_prds[4] + "</td>");
                            out.print("<td>");
                            out.print("<input type='checkbox' class='form-control' onclick='pasarDatos([" + Obj_prds[0] + "])'>");
                            out.print("</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<input type='hidden' id='Txt_ids' name='Txt_ids'>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center; margin-top: 10px;'>");
                    out.print("<input type='submit' value='Eliminar' style='width: 20%;' >");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                }
                out.print("<div id='content'>");
                out.print("<h3>Paradas de máquina</h3>");
                out.print("<table class='table4' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>PRODUCCION</th>");
                out.print("</tr>");
                out.print("<tr>");
                //<editor-fold defaultstate="collapsed" desc="PM PRODUCCION">
                out.print("<td valign='top' style='border-right: 1px solid #15aabf;width:50%'><dir>");
                lst_produccion = jpacpmq.Parada_maquinas_categoria(1);
                if (lst_produccion == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay categorías en paradas por <br />producción registradas</b>");
                    out.print("</center>");
                } else {
                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                        if ((Integer) obj_registro[16] != 0) {
                            out.print("<form action='Registro?opc=7&temp=1' method='post' onsubmit='checkSubmit();'>");
                            out.print("<select name='Cbx_parada_produccion' id='Cbx_parada_produccion' >");
                            out.print("<option value='0' >Seleccionar parada producción</option>");
                            for (int i = 0; i < lst_produccion.size(); i++) {
                                Object[] obj_produccion = (Object[]) lst_produccion.get(i);
                                if (obj_registro[65].toString().equals("R-PRF-010")) {
                                    if (obj_produccion[3] == obj_registro[64]) {
                                        out.print("<option value='" + obj_produccion[0] + "'>" + obj_produccion[1] + "</option>");
                                    }
                                } else if (obj_produccion[3] == obj_registro[64] || (Integer) obj_produccion[3] == 1) {
                                    out.print("<option value='" + obj_produccion[0] + "'>" + obj_produccion[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_parada_produccion');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                            out.print("<input type='text' style='width:50px' id='Txt_cantidad_produccion' name='Txt_cantidad_produccion' placeholder='Minutos' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_produccion');val1.add(Validate.Presence);val1.add(Validate.Total_numero);</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp");
                            out.print("<select id='Cbx_hora_produccion' name='Cbx_hora_produccion' style='width: 45px; text-align: center;  margin-right: 15px;'>");
                            out.print("<option value='0' >Hora</option>");
                            for (int i = 1; i < 9; i++) {
                                out.print("<option value='" + i + "'>" + i + "</option>");
                            }
                            out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_hora_produccion');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                            out.print("<input type='hidden' name='Id_parada_maquina' id='Id_parada_maquina' value='2' />");
                            out.print("<input type='submit' value='Registrar' style='width:80px'/>");
                            out.print("</form>");
                            out.print("<br />");
                        }
                    }
//                    lst_produccion_consulta = jpacpmq.Parada_maquinas_categoria_registradas(1, id_registro);
                    lst_produccion_consulta = jpacpmq.Parada_maquinas_categoria_registradas_id(1, id_registro);
                    if (lst_produccion_consulta == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay paradas por producción</b>");
                        out.print("</center>");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        for (int i = 1; i <= 8; i++) {
                            out.print("<th>" + i + "</th>");
                        }
                        out.print("<th>Total</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                            if ((Integer) obj_registro[16] == 1) {
                                out.print("<th style='max-width:15px;'>Quitar</th>");
                            }
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_produccion_consulta.size(); i++) {
                            Object[] obj_ids = (Object[]) lst_produccion_consulta.get(i);
                            Object[] obj_paradas_produccion = (Object[]) lst_produccion_consulta.get(i);
                            List lst_idParadas = jpacpmq.Parada_maquinas_categoria_registradas_idParada(1, id_registro, Integer.parseInt(obj_ids[2].toString()));
                            if (lst_idParadas != null) {
                                for (int j = 0; j < lst_idParadas.size(); j++) {
                                    Object[] obj_parad = (Object[]) lst_idParadas.get(j);
                                    out.print("<tr>");
                                    out.print("<td>" + obj_parad[2] + "</td>");
                                    String[] data = obj_parad[3].toString().replace("],[", "///").replace("]", "").replace("[", "").split("///");
                                    int itera = 0;
                                    for (int k = 1; k <= 8; k++) {
                                        String[] Arr_data = data[itera].split("/");
                                        int data_min = Integer.parseInt(Arr_data[0].toString());
                                        int data_hour = Integer.parseInt(Arr_data[1].toString());
                                        if (data_hour == k) {
                                            out.print("<td align='center'>" + data_min + "</td>");
                                            if (itera < data.length - 1) {
                                                itera++;
                                            }
                                        } else {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("<td align='center'>" + obj_parad[4] + "</td>");
                                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                                        if ((Integer) obj_registro[16] == 0) {
                                        } else {
                                            out.print("<td align='center'><a href='Registro?opc=6&Id_registro=" + id_registro + "&id_prda=" + obj_parad[1] + "&temp=1' style='color: #34495e; font-size: 16px;'><i class='fas fa-trash'></i></a></td>");
                                        }
                                    }
                                }
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        List lst_results = jpacpmq.Parada_maquinas_sumas(id_registro, 1);
                        if (lst_results != null) {
                            for (int i = 0; i < lst_results.size(); i++) {
                                Object[] obj_result = (Object[]) lst_results.get(i);
                                out.print("<td align='center'><b>" + obj_result[0] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[1] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[2] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[3] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[4] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[5] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[6] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[7] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[8] + "</b></td>");
                                out.print("<td align='center'></td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("</table>");
                    }
                }
                out.print("</td>");
                out.print("</tr>");

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PM MANTENIMIENTO">
                out.print("<tr>");
                out.print("<th>MANTENIMIENTO</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td valign='top' style='width:50%'><dir>");
                lst_mantenimiento = jpacpmq.Parada_maquinas_categoria(2);
                if (lst_mantenimiento == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay categorías en paradas por <br />mantenimiento registradas</b>");
                    out.print("</center>");
                } else {
                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                        if ((Integer) obj_registro[16] != 0) {
                            out.print("<form action='Registro?opc=7&temp=1' method='post' onsubmit='checkSubmit();'>");
                            out.print("<select name='Cbx_parada_mantenimiento' id='Cbx_parada_mantenimiento' >");
                            out.print("<option value='0' >Seleccionar parada mantenimiento</option>");
                            for (int i = 0; i < lst_mantenimiento.size(); i++) {
                                Object[] obj_mantenimiento = (Object[]) lst_mantenimiento.get(i);
                                if (obj_registro[65].toString().equals("R-PRF-010")) {
                                    if (obj_mantenimiento[3] == obj_registro[64]) {
                                        out.print("<option value='" + obj_mantenimiento[0] + "'>" + obj_mantenimiento[1] + "</option>");
                                    }
                                } else if (obj_mantenimiento[3] == obj_registro[64] || (Integer) obj_mantenimiento[3] == 1) {
                                    out.print("<option value='" + obj_mantenimiento[0] + "'>" + obj_mantenimiento[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_parada_mantenimiento');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
                            out.print("<input type='text' style='width:50px' id='Txt_cantidad_mantenimiento' name='Txt_cantidad_mantenimiento' placeholder='Minutos' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad_mantenimiento');val1.add(Validate.Presence);val1.add(Validate.Total_numero);</script>");
                            out.print("&nbsp&nbsp&nbsp&nbsp");
                            out.print("<select id='Cbx_hora_maquina' name='Cbx_hora_maquina' style='width: 45px; text-align: center;  margin-right: 15px;'>");
                            out.print("<option value='0' >Hora</option>");
                            for (int i = 1; i < 9; i++) {
                                out.print("<option value='" + i + "'>" + i + "</option>");
                            }
                            out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_hora_maquina');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                            out.print("<input type='hidden' name='Id_parada_maquina' id='Id_parada_maquina' value='1' />");
                            out.print("<input type='submit' value='Registrar' style='width:80px'/>");
                            out.print("</form>");
                            out.print("<br />");
                        }
                    }
                    lst_mantenimiento_consulta = jpacpmq.Parada_maquinas_categoria_registradas_id(2, id_registro);
                    if (lst_mantenimiento_consulta == null) {
                        out.print("<center>");
                        out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                        out.print("<br /><b class='naranja'>No hay paradas por mantenimiento</b>");
                        out.print("</center>");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        for (int i = 1; i <= 8; i++) {
                            out.print("<th>" + i + "</th>");
                        }
                        out.print("<th>Total</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                            if ((Integer) obj_registro[16] == 0) {
                            } else {
                                out.print("<th style='max-width:15px;'>Quitar</th>");
                            }
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_mantenimiento_consulta.size(); i++) {
                            Object[] obj_ids = (Object[]) lst_mantenimiento_consulta.get(i);
                            Object[] obj_paradas_mantenimiento = (Object[]) lst_mantenimiento_consulta.get(i);
                            List lst_idParadas = jpacpmq.Parada_maquinas_categoria_registradas_idParada(2, id_registro, Integer.parseInt(obj_ids[2].toString()));
                            if (lst_idParadas != null) {
                                for (int j = 0; j < lst_idParadas.size(); j++) {
                                    Object[] obj_parad = (Object[]) lst_idParadas.get(j);
                                    out.print("<tr>");
                                    out.print("<td>" + obj_parad[2] + "</td>");
                                    String[] data = {};
                                    if (obj_parad[3].toString().contains("],[")) {
                                        data = obj_parad[3].toString().replace("],[", "///").replace("]", "").replace("[", "").split("///");
                                    } else {
                                        data = obj_parad[3].toString().replace("]", "///").replace("[", "").split("///");
                                    }
                                    int itera = 0;
                                    for (int k = 1; k <= 8; k++) {
                                        String[] Arr_data = data[itera].split("/");
                                        int data_min = Integer.parseInt(Arr_data[0].toString());
                                        int data_hour = Integer.parseInt(Arr_data[1].toString());
                                        if (data_hour == k) {
                                            out.print("<td align='center'>" + data_min + "</td>");
                                            if (itera < data.length - 1) {
                                                itera++;
                                            }
                                        } else {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("<td align='center'>" + obj_parad[4] + "</td>");
                                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                                        if ((Integer) obj_registro[16] == 0) {
                                        } else {
                                            out.print("<td align='center'><a href='Registro?opc=6&Id_registro=" + id_registro + "&id_prda=" + obj_parad[1] + "&temp=1' style='color: #34495e; font-size: 16px;'><i class='fas fa-trash'></i></a></td>");
                                        }
                                    }
                                }
                            }

                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        List lst_results = jpacpmq.Parada_maquinas_sumas(id_registro, 2);
                        if (lst_results != null) {
                            for (int i = 0; i < lst_results.size(); i++) {
                                Object[] obj_result = (Object[]) lst_results.get(i);
                                out.print("<td align='center'><b>" + obj_result[0] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[1] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[2] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[3] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[4] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[5] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[6] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[7] + "</b></td>");
                                out.print("<td align='center'><b>" + obj_result[8] + "</b></td>");
                                out.print("<td align='center'></td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("</table>");
                    }
                }
                out.print("</td>");
                out.print("</tr>");
                //</editor-fold>
                out.print("</table>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_soldadura_bocas")) {
                // <editor-fold desc="SOLDADURA BOCAS R-PRF-010 ">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'><br />");
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_registro[16] == 0) {
                    } else if (rol.equals("Administrador") || rol.equals("Coordinadora-Produccion")) {
//                        out.print("<h3><img onclick='Form_registro_cabecera()'  src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registrar soldadura en " + ((obj_registro[65].toString().equals("R-PRF-010")) ? "Centros" : "Bocas") + "' />"
//                                + "<img onclick='Form_limpiar_cabecera()' src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Estación' />  Espesor soldadura " + ((obj_registro[65].toString().equals("R-PRF-010")) ? "Centros" : "Bocas") + "</h3>");
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar soldadura en " + ((obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) ? "Centros" : "Bocas") + "'></span> Registrar soldadura en " + ((obj_registro[65].toString().equals("R-PRF-010")) ? "Centros" : "Bocas") + "<br />");
                        out.print("<span class='fa fa-eraser fa-size_small' onclick='Form_limpiar_cabecera()' title='Limpiar estaciones horarias'></span> Limpiar estaciones horarias<br />");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar soldadura en " + ((obj_registro[65].toString().equals("R-PRF-010")) ? "Centros" : "Bocas") + "'></span> Registrar soldadura en " + ((obj_registro[65].toString().equals("R-PRF-010")) ? "Centros" : "Bocas") + "<br />");
                    }
                }
                out.print("<h3>Espesor soldadura " + ((obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) ? "Centros" : "Bocas") + "</h3>");
                //<editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACION">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_limpiar_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Limpiar Estación</h3>");
                out.print("<form action='Registro?opc=34' method='post' name='FormLimpiar' id='FormLimpiar' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                out.print("<br />Seleccionar estación horaria para la limpiar información.<br /><br />");
                out.print("<select name='Cbx_frecuencia_limpiar' id='Cbx_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                out.print("<option value='1' >hora 1</option>");
                out.print("<option value='2' >hora 2</option>");
                out.print("<option value='3' >hora 3</option>");
                out.print("<option value='4' >hora 4</option>");
                out.print("<option value='5' >hora 5</option>");
                out.print("<option value='6' >hora 6</option>");
                out.print("<option value='7' >hora 7</option>");
                out.print("<option value='8' >hora 8</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                out.print("<select name='Cbx_sub_frecuencia_limpiar' id='Cbx_sub_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar sub-toma</option>");
                out.print("<option value='1' >1</option>");
                out.print("<option value='2' >2</option>");
                out.print("<option value='3' >3</option>");
                out.print("<option value='4' >4</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_sub_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='JAVASCRIPT:FormLimpiar.submit()'><img src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Registro'></a><br />");
                out.print("<br /><input type='submit' value='Limpiar' /><br />");
                out.print("</form>");
                out.print("</fieldset></div>");
                //FIN LIMPIAR
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Registro espesor soldadura " + ((obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) ? "Centros" : "Bocas") + "</h3>");
                out.print("<form action='Registro?opc=12' method='post' onsubmit='checkSubmit();'>");
                out.print("<table>");
                out.print("<tr>");
                out.print("<td>Seleccionar inicio de toma de los datos</td>");
                out.print("<td>");
                out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                out.print("<option value='1' >hora 1</option>");
                out.print("<option value='2' >hora 2</option>");
                out.print("<option value='3' >hora 3</option>");
                out.print("<option value='4' >hora 4</option>");
                out.print("<option value='5' >hora 5</option>");
                out.print("<option value='6' >hora 6</option>");
                out.print("<option value='7' >hora 7</option>");
                out.print("<option value='8' >hora 8</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Seleccionar inicio de sub-toma</td>");
                out.print("<td>");
                out.print("<select name='Cbx_sub_frecuencia' id='Cbx_sub_frecuencia' >");
                out.print("<option value='0' >Seleccionar sub-toma</option>");
                out.print("<option value='1' >1</option>");
                out.print("<option value='2' >2</option>");
                out.print("<option value='3' >3</option>");
                out.print("<option value='4' >4</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_sub_frecuencia');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'><hr /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'>");
                lst_ficha = jpacftn.Traer_ficha_registro(id_registro);
                Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                double inicio_boca = 0;
                double estandar_boca = 0;
                double fin_boca = 0;
                if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                    if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[47].toString()) > 0) {
                        estandar_boca = Double.parseDouble(obj_ficha[47].toString());
                        inicio_boca = Double.parseDouble(obj_ficha[47].toString()) - Double.parseDouble(obj_ficha[49].toString());
                        fin_boca = Double.parseDouble(obj_ficha[47].toString()) + Double.parseDouble(obj_ficha[48].toString());
                    } else {
                        estandar_boca = Double.parseDouble(obj_ficha[12].toString());
                        inicio_boca = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
                        fin_boca = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                    }
                } else if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[44].toString()) > 0) {
                    estandar_boca = Double.parseDouble(obj_ficha[44].toString());
                    inicio_boca = Double.parseDouble(obj_ficha[44].toString()) - Double.parseDouble(obj_ficha[46].toString());
                    fin_boca = Double.parseDouble(obj_ficha[44].toString()) + Double.parseDouble(obj_ficha[45].toString());
                } else {
                    estandar_boca = Double.parseDouble(obj_ficha[9].toString());
                    inicio_boca = Double.parseDouble(obj_ficha[9].toString()) - Double.parseDouble(obj_ficha[11].toString());
                    fin_boca = Double.parseDouble(obj_ficha[9].toString()) + Double.parseDouble(obj_ficha[10].toString());
                }
                contador = 0;
                out.print("<div style='overflow: scroll;width:700px;'>");
                out.print("<table class='table'>");
                out.print("<tr>");
                for (double i = inicio_boca; i < fin_boca + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    out.print("<th>" + i + "</th>");
                    contador++;
                }
                out.print("</tr>");
                out.print("<tr>");
                for (double i = inicio_boca; i < fin_boca + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    if (i == estandar_boca) {
                        out.print("<td align='center'><input type='radio' name='Txt_toma1' value='" + i + "' checked/></td>");
                    } else {
                        out.print("<td align='center'><input type='radio' name='Txt_toma1' value='" + i + "' /></td>");
                    }
                }
                out.print("</tr>");
                out.print("<tr>");
                for (double i = inicio_boca; i < fin_boca + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    if (i == estandar_boca) {
                        out.print("<td align='center'><input type='radio' name='Txt_toma2' value='" + i + "' checked/></td>");
                    } else {
                        out.print("<td align='center'><input type='radio' name='Txt_toma2' value='" + i + "' /></td>");
                    }
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div style='float:right;'><input type='submit' value='Registrar'/></div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_espesores_bocas = jpacreb.Consultar_registro_espesores_bocas(id_registro);
                out.print("<table class='table'>");
                out.print("<tr>");
                out.print("<th rowspan='2'>#</th>");
                for (int i = 0; i < 8; i++) {
                    out.print("<td style='background-color:#dcdcdc' rowspan='" + (contador + 2) + "'=></td>");
                    out.print("<th colspan='4'>" + (i + 1) + "</th>");
                    if ((i + 1) == 8) {
                        out.print("<th rowspan='" + (contador + 2) + "'></th>");
                    }
                }
                out.print("</tr>");
                out.print("<tr>");
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        out.print("<td><b>" + (j + 1) + "</td>");
                    }
                }
                out.print("</tr>");
                for (double i = inicio_boca; i < fin_boca + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    out.print("<tr>");
                    out.print("<td align='center'><b>" + i + "</b></td>");
                    for (int j = 0; j < 8; j++) {
                        for (int l = 0; l < 4; l++) {
                            out.print("<td>");
                            if (lst_espesores_bocas == null) {
                                out.print("");
                            } else {
                                for (int k = 0; k < lst_espesores_bocas.size(); k++) {
                                    Object[] obj_espesores_boca = (Object[]) lst_espesores_bocas.get(k);
                                    if ((Integer) obj_espesores_boca[2] == (j + 1)) {
                                        if ((Integer) obj_espesores_boca[3] == (l + 1)) {
                                            if ((Double) obj_espesores_boca[4] == i || (Double) obj_espesores_boca[5] == i) {
                                                String[] responsable = obj_espesores_boca[6].toString().split("/");
                                                if (responsable[0].equals("Coordinadora-Calidad") || responsable[0].equals("Inspectora-Calidad")) {
                                                    if ((Double) obj_espesores_boca[4] == (Double) obj_espesores_boca[5]) {
                                                        out.print("<b class='calidad'>X</b>");
                                                    } else {
                                                        out.print("<b class='calidad'>X</b>");
                                                    }
                                                } else if (responsable[0].equals("Coordinadora-Produccion")) {
                                                    if ((Double) obj_espesores_boca[4] == (Double) obj_espesores_boca[5]) {
                                                        out.print("<b class='coordinadora'>X</b>");
                                                    } else {
                                                        out.print("<b class='coordinadora'>X</b>");
                                                    }
                                                } else if ((Double) obj_espesores_boca[4] == (Double) obj_espesores_boca[5]) {
                                                    out.print("X");
                                                } else {
                                                    out.print("X");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            out.print("</td>");
                        }
                    }
                    out.print("</tr>");
                }
                lst_promedios = jpacreb.Promedio_soldadura_espesores_bocas(id_registro);
                out.print("<tr>");
                if (lst_promedios != null) {
                    Object[] obj_promedio_soldadura = (Object[]) lst_promedios.get(0);
                    if (obj_promedio_soldadura[2] == null) {
                        out.print("<th colspan='41'>No se han registrado espesores en " + ((obj_registro[65].toString().equals("R-PRF-010")) ? "Centros" : "Bocas") + "<th>");
                    } else {
                        String result_cps = jpacreb.Calcular_CP_CPK_espesores_id_registro(Integer.parseInt(obj_registro[1].toString()), lst_espesores_bocas, Integer.parseInt(obj_registro[74].toString()));
                        String[] arg_result_cps = result_cps.split("-");
                        out.print("<th colspan='41'>Promedio de sellado : " + obj_promedio_soldadura[2] + "&nbsp;&nbsp;&nbsp;&nbsp;Desviación estandar : " + arg_result_cps[5] + "&nbsp;&nbsp;&nbsp;&nbsp;CP : " + arg_result_cps[0] + "&nbsp;&nbsp;&nbsp;&nbsp;CPK : " + arg_result_cps[1] + "<th>");
                    }
                } else {
                    out.print("<th colspan='41'>No se han registrado espesores en " + ((obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) ? "Centros" : "Bocas") + "<th>");
                }
                out.print("</tr>");
                out.print("</table>");
                //</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_soldadura_colas")) {
                // <editor-fold desc="SOLDADURA COLAS">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'><br />");
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_registro[16] == 0) {
//                        out.print("<h3>Espesor soldadura colas</h3>");
                    } else if (rol.equals("Administrador") || rol.equals("Coordinadora-Produccion")) {
//                        out.print("<h3><img onclick='Form_registro_cabecera()'src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registrar soldadura en colas' />"
//                                + "<img onclick='Form_limpiar_cabecera()' src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Estación' />  Espesor soldadura colas</h3>");
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar soldadura en colas'></span> Registrar soldadura en colas<br />");
                        out.print("<span class='fa fa-eraser fa-size_small' onclick='Form_limpiar_cabecera()' title='Limpiar estaciones horarias'></span> Limpiar estaciones horarias<br />");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar soldadura en colas'></span> Registrar soldadura en colas<br />");
//                        out.print("<h3><img onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registrar soldadura en colas' />  Espesor soldadura colas</h3>");
                    }
                }
                out.print("<h3>Espesor soldadura colas</h3>");
                //<editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACION">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_limpiar_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Limpiar Estación</h3>");
                out.print("<form action='Registro?opc=35' method='post' name='FormLimpiar' id='FormLimpiar' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                out.print("<br />Seleccionar estación horaria para la limpiar información.<br /><br />");
                out.print("<select name='Cbx_frecuencia_limpiar' id='Cbx_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                out.print("<option value='1' >hora 1</option>");
                out.print("<option value='2' >hora 2</option>");
                out.print("<option value='3' >hora 3</option>");
                out.print("<option value='4' >hora 4</option>");
                out.print("<option value='5' >hora 5</option>");
                out.print("<option value='6' >hora 6</option>");
                out.print("<option value='7' >hora 7</option>");
                out.print("<option value='8' >hora 8</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                out.print("<select name='Cbx_sub_frecuencia_limpiar' id='Cbx_sub_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar sub-toma</option>");
                out.print("<option value='1' >1</option>");
                out.print("<option value='2' >2</option>");
                out.print("<option value='3' >3</option>");
                out.print("<option value='4' >4</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_sub_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='JAVASCRIPT:FormLimpiar.submit()'><img src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Registro'></a><br />");
                out.print("<br /><input type='submit' value='Limpiar' /><br />");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Registro espesor soldadura colas</h3>");
                out.print("<form action='Registro?opc=13' method='post' onsubmit='checkSubmit();'>");
                out.print("<table>");
                out.print("<tr>");
                out.print("<td>Seleccionar inicio de toma de los datos</td>");
                out.print("<td>");
                out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                out.print("<option value='1' >hora 1</option>");
                out.print("<option value='2' >hora 2</option>");
                out.print("<option value='3' >hora 3</option>");
                out.print("<option value='4' >hora 4</option>");
                out.print("<option value='5' >hora 5</option>");
                out.print("<option value='6' >hora 6</option>");
                out.print("<option value='7' >hora 7</option>");
                out.print("<option value='8' >hora 8</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Seleccionar inicio de sub-toma</td>");
                out.print("<td>");
                out.print("<select name='Cbx_sub_frecuencia' id='Cbx_sub_frecuencia' >");
                out.print("<option value='0' >Seleccionar sub-toma</option>");
                out.print("<option value='1' >1</option>");
                out.print("<option value='2' >2</option>");
                out.print("<option value='3' >3</option>");
                out.print("<option value='4' >4</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_sub_frecuencia');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'><hr /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'>");
                lst_ficha = jpacftn.Traer_ficha_registro(id_registro);
                Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                double inicio_cola = 0;
                double estandar_cola = 0;
                double fin_cola = 0;
                if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[47].toString()) > 0) {
                    estandar_cola = Double.parseDouble(obj_ficha[47].toString());
                    inicio_cola = Double.parseDouble(obj_ficha[47].toString()) - Double.parseDouble(obj_ficha[49].toString());
                    fin_cola = Double.parseDouble(obj_ficha[47].toString()) + Double.parseDouble(obj_ficha[48].toString());
                } else {
                    estandar_cola = Double.parseDouble(obj_ficha[12].toString());
                    inicio_cola = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
                    fin_cola = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                }
                contador = 0;
                out.print("<div style='overflow: scroll;width:700px;'>");
                out.print("<table class='table'>");
                out.print("<tr>");
                for (double i = inicio_cola; i < fin_cola + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    out.print("<th>" + i + "</th>");
                    contador++;
                }
                out.print("</tr>");
                out.print("<tr>");
                for (double i = inicio_cola; i < fin_cola + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    if (i == estandar_cola) {
                        out.print("<td align='center'><input type='radio' name='Txt_toma1' value='" + i + "' checked/></td>");
                    } else {
                        out.print("<td align='center'><input type='radio' name='Txt_toma1' value='" + i + "' /></td>");
                    }
                }
                out.print("</tr>");
                out.print("<tr>");
                for (double i = inicio_cola; i < fin_cola + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    if (i == estandar_cola) {
                        out.print("<td align='center'><input type='radio' name='Txt_toma2' value='" + i + "' checked/></td>");
                    } else {
                        out.print("<td align='center'><input type='radio' name='Txt_toma2' value='" + i + "' /></td>");
                    }
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div style='float:right;'><input type='submit' value='Registrar'/></div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_espesores_colas = jpacrec.Consultar_registro_espesores_colas(id_registro);
                out.print("<table class='table'>");
                out.print("<tr>");
                out.print("<th rowspan='2'>#</th>");
                for (int i = 0; i < 8; i++) {
                    out.print("<td style='background-color:#dcdcdc' rowspan='" + (contador + 2) + "'=></td>");
                    out.print("<th colspan='4'>" + (i + 1) + "</th>");
                    if ((i + 1) == 8) {
                        out.print("<th rowspan='" + (contador + 2) + "'></th>");
                    }
                }
                out.print("</tr>");
                out.print("<tr>");
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        out.print("<td><b>" + (j + 1) + "</td>");
                    }
                }
                out.print("</tr>");
                for (double i = inicio_cola; i < fin_cola + 0.01; i += 0.01) {
                    long mult = (long) Math.pow(10, 2);
                    i = (Math.round(i * mult)) / (double) mult;
                    out.print("<tr>");
                    out.print("<td align='center'><b>" + i + "</b></td>");
                    for (int j = 0; j < 8; j++) {
                        for (int l = 0; l < 4; l++) {
                            out.print("<td>");
                            if (lst_espesores_colas == null) {
                                out.print("");
                            } else {
                                for (int k = 0; k < lst_espesores_colas.size(); k++) {
                                    Object[] obj_espesores_cola = (Object[]) lst_espesores_colas.get(k);
                                    if ((Integer) obj_espesores_cola[2] == (j + 1)) {
                                        if ((Integer) obj_espesores_cola[3] == (l + 1)) {
                                            if ((Double) obj_espesores_cola[4] == i || (Double) obj_espesores_cola[5] == i) {
                                                String[] responsable = obj_espesores_cola[6].toString().split("/");
                                                if (responsable[0].equals("Coordinadora-Calidad") || responsable[0].equals("Inspectora-Calidad")) {
                                                    if ((Double) obj_espesores_cola[4] == (Double) obj_espesores_cola[5]) {
                                                        out.print("<b class='calidad'>X</b>");
                                                    } else {
                                                        out.print("<b class='calidad'>X</b>");
                                                    }
                                                } else if (responsable[0].equals("Coordinadora-Produccion")) {
                                                    if ((Double) obj_espesores_cola[4] == (Double) obj_espesores_cola[5]) {
                                                        out.print("<b class='coordinadora'>X</b>");
                                                    } else {
                                                        out.print("<b class='coordinadora'>X</b>");
                                                    }
                                                } else if ((Double) obj_espesores_cola[4] == (Double) obj_espesores_cola[5]) {
                                                    out.print("X");
                                                } else {
                                                    out.print("X");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            out.print("</td>");
                        }
                    }
                    out.print("</tr>");
                }
                lst_promedios = jpacrec.Promedio_soldadura_espesores_colas(id_registro);
                out.print("<tr>");
                if (lst_promedios != null) {
                    Object[] obj_promedio_soldadura = (Object[]) lst_promedios.get(0);
                    if (obj_promedio_soldadura[2] == null) {
                        out.print("<th colspan='41'>No se han registrado espesores en colas<th>");
                    } else {
                        String result_cps = jpacrec.Calcular_CP_CPK_espesores_id_registro(Integer.parseInt(obj_registro[1].toString()), lst_espesores_colas, Integer.parseInt(obj_registro[74].toString()));
                        String[] arg_result_cps = result_cps.split("-");
                        out.print("<th colspan='41'>Promedio de sellado : " + obj_promedio_soldadura[2] + "&nbsp;&nbsp;&nbsp;&nbsp;Desviación estandar : " + arg_result_cps[5] + "&nbsp;&nbsp;&nbsp;&nbsp;CP : " + arg_result_cps[0] + "&nbsp;&nbsp;&nbsp;&nbsp;CPK : " + arg_result_cps[1] + "<th>");
                    }
                } else {
                    out.print("<th colspan='41'>No se han registrado espesores en colas<th>");
                }
                out.print("</tr>");
                out.print("</table>");
                //</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_pnc")) {
                // <editor-fold desc="PNC">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                datos_pnc = pageContext.getRequest().getAttribute("Id_pnc").toString();
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'><br />");
                if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                    if ((Integer) obj_registro[16] == 0) {
//                        out.print("<h3>Descripción de PNC</h3>");
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar descripción de PNC'></span> Registrar descripción de PNC<br />");
//                        out.print("<h3><img onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registrar descripción de PNC' />  Descripción de PNC</h1>");
                    }
                }
                out.print("<h3>Descripción de PNC</h3>");
                //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Descripción de PNC</h3>");
                out.print("<form action='Registro?opc=16' method='post' onsubmit='checkSubmit();' onsubmit='checkSubmit();'>");
                lst_categoria = jpacctg.Categorias();
                out.print("<select name='Cbx_pnc' style='width:400px' id='Cbx_pnc'>");
                out.print("<optgroup label='Seleccionar'>");
                out.print("<option value='0' >Seleccionar Tipo de categoria / Descripción PNC</option>");
                out.print("</optgroup>");
                for (int i = 0; i < lst_categoria.size(); i++) {
                    Object[] obj_categorias = (Object[]) lst_categoria.get(i);
                    lst_pnc = jpacpnc.PNC_categoria_registro((Integer) obj_categorias[0], id_registro);
                    if (lst_pnc == null || lst_pnc.isEmpty()) {
//                        out.print("<optgroup label='" + obj_categorias[1] + " sin datos'></optgroup>");
                    } else {
                        out.print("<optgroup style='background-color:" + obj_categorias[2] + "' label='" + obj_categorias[1] + "'>");
                        for (int j = 0; j < lst_pnc.size(); j++) {
                            Object[] obj_pnc = (Object[]) lst_pnc.get(j);
                            if (Integer.parseInt(obj_pnc[4].toString()) != 1) {
                                out.print("<option style='background-color:" + obj_categorias[2] + "' value='" + obj_pnc[0] + "'>" + obj_pnc[1] + "</option>");
                            }
                        }
                        out.print("</optgroup>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_pnc');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div style='float:right'><input type='submit' value='Registrar' /></div>");
                out.print("</form>");
                out.print("</fieldset></div>");
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                for (int j = 0; j < lst_categoria.size(); j++) {
                    Object[] obj_categorias = (Object[]) lst_categoria.get(j);
                    lst_pnc_registro = jpacpnc.Pnc_registro(id_registro, (Integer) obj_categorias[0]);
                    if (lst_pnc_registro == null) {
                        contador++;
                    }
                }
                if (contador == lst_categoria.size()) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos en descripción de PNC</b>");
                    out.print("</center>");
                } else {
                    out.print("<table class='table' style='100%'>");
                    out.print("<tr>");
                    out.print("<th>Descripción</th>");
                    for (int i = 0; i < 8; i++) {
                        out.print("<th>" + (i + 1) + "</th>");
                    }
                    out.print("<th>Tolal</th>");
                    if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                        if ((Integer) obj_registro[16] == 0) {
                        } else {
                            out.print("<th>Quitar</th>");
                        }
                    }
                    out.print("</tr>");
                    for (int j = 0; j < lst_categoria.size(); j++) {
                        Object[] obj_categorias = (Object[]) lst_categoria.get(j);
                        lst_pnc_registro = jpacpnc.Pnc_registro(id_registro, (Integer) obj_categorias[0]);
                        if (lst_pnc_registro == null) {
                        } else {
                            out.print("<tr>");
                            if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                                if ((Integer) obj_registro[16] == 0) {
                                    out.print("<th style='background-color:#bbb' colspan='10'>" + obj_categorias[1] + "</th>");
                                } else {
                                    out.print("<th style='background-color:#bbb' colspan='11'>" + obj_categorias[1] + "</th>");
                                }
                            } else {
                                out.print("<th colspan='10'>" + obj_categorias[1] + "</th>");
                            }
                            out.print("</tr>");
                            for (int i = 0; i < lst_pnc_registro.size(); i++) {
                                Object[] obj_pnc_registro = (Object[]) lst_pnc_registro.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_pnc_registro[2] + "</td>");
                                for (int k = 5; k <= 12; k++) {
                                    if (obj_pnc_registro[k] == null) {
                                        out.print("<td align='center'>0</td>");
                                    } else if (rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad")) {
                                        out.print("<td align='center'>"
                                                + "" + obj_pnc_registro[k] + ""
                                                + "</td>");
                                    } else if ((Integer) obj_registro[16] == 0) {
                                        out.print("<td align='center'>"
                                                + "" + obj_pnc_registro[k] + ""
                                                + "</td>");
                                    } else {
                                        out.print("<td align='center'>"
                                                + "<a style='color:gray;font-size:12px;' href='Registro?opc=15&Id_registro=" + id_registro + "&Datos_pnc=" + obj_pnc_registro[0] + "/" + obj_pnc_registro[2] + "/" + obj_pnc_registro[k] + "/" + (k - 4) + "' >" + obj_pnc_registro[k] + "</a>"
                                                + "</td>");
                                    }
                                }
                                suma_total = suma_total + ((Integer) obj_pnc_registro[5] + (Integer) obj_pnc_registro[6] + (Integer) obj_pnc_registro[7] + (Integer) obj_pnc_registro[8] + (Integer) obj_pnc_registro[9] + (Integer) obj_pnc_registro[10] + (Integer) obj_pnc_registro[11] + (Integer) obj_pnc_registro[12]);
                                out.print("<td align='center'><b>" + ((Integer) obj_pnc_registro[5] + (Integer) obj_pnc_registro[6] + (Integer) obj_pnc_registro[7] + (Integer) obj_pnc_registro[8] + (Integer) obj_pnc_registro[9] + (Integer) obj_pnc_registro[10] + (Integer) obj_pnc_registro[11] + (Integer) obj_pnc_registro[12]) + "</b></td>");
                                if (!(rol.equals("Consulta") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad"))) {
                                    if ((Integer) obj_registro[16] == 0) {
                                    } else {
                                        out.print("<td align='center'><span class='fa fa-times fa-size_small' onclick='EliminarPNC(" + obj_pnc_registro[0] + "," + id_registro + ")' title='Quitar PNC' ></span></td>");
                                        //out.print("<td align='center'><a href='#' onclick='EliminarPNC(" + obj_pnc_registro[0] + "," + id_registro + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Quitar PNC' /></a></td>");
                                    }
                                }
                                out.print("</tr>");
                            }
                        }
                    }
                    out.print("<tr>");
                    out.print("<th colspan='9'>Total descripción PNC</th>");
                    out.print("<td align='center'><b>" + suma_total + "</b></td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO HORA">
                if (!datos_pnc.equals("0")) {
                    String[] descripcion_pnc = datos_pnc.toString().split("/");
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03; display: block;'>");
                    out.print("<fieldset class='popup_local' style='width:300px;position: absolute;top: 15%;left:60%'>");
                    out.print("<div style='float:right'><span class='fa fa-times fa-size_small' onclick=\"location.href='Registro?opc=15&Id_registro=" + id_registro + "&Datos_pnc=0'\" title='Cancelar registro de toma'></span></div>");
                    out.print("<h3>Toma :" + descripcion_pnc[3] + " </h3>");
                    out.print("<form action='Registro?opc=17' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>" + descripcion_pnc[1].toString().toUpperCase() + "</b><br />");
                    out.print("<input type='text' style='width:50px;text-align: center;' name='Txt_valor' id='Txt_valor' value='" + descripcion_pnc[2] + "' autofocus />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_valor');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                    out.print("&nbsp&nbsp&nbsp&nbsp");
                    out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                    out.print("<input type='hidden' name='Id_registro_pnc' id='Id_registro_pnc' value='" + descripcion_pnc[0] + "' />");
                    out.print("<input type='hidden' name='Txt_toma' id='Txt_toma' value='" + descripcion_pnc[3] + "' />");
                    out.print("<input  style='width:80px;' type='submit' value='Registrar'  />");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                }
//</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_entrada_materiales")) {
                // <editor-fold desc="ENTRADA DE MATERIALES">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                id_entrada_material = Integer.parseInt(pageContext.getRequest().getAttribute("Id_entrada_material").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                out.print("<div id='content'><br />");
                if (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta")) {
//                    out.print("<h3>Control entrada de materiales</h3>");
                } else if ((Integer) obj_registro[16] == 0) {
//                    out.print("<h3>Control entrada de materiales</h3>");
                } else {
                    out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar entrada de materiales'></span> Registrar entrada de materiales<br />");
//                    out.print("<h3><img onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Registrar entrada de materiales' /> Control entrada de materiales</h3>");
                }
                out.print("<h3>Control entrada de materiales</h3>");
                //<editor-fold defaultstate="collapsed" desc="REGISTRO PROD">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:350px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Control entrada de materiales</h3>");
                out.print("<form action='Registro?opc=21' method='post' onsubmit='checkSubmit();' id='FormEntrMate'>");
                out.print("Ingresar el nombre del producto , el lote o los lotes que estan en proceso para realizarla entrada de material a la línea."
                        + "<br /><br /><b>Materiales : </b><br />" + obj_registro[62] + "<br />");
                out.print("<b>Producto en proceso : </b><br />");
                out.print("<input type='text' name='Txt_producto_proceso' id='Txt_producto_proceso' placeholder='Producto en proceso' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_producto_proceso');val1.add(Validate.Presence);</script></td>");
                out.print("<br />");
                String[] lst_materiales = obj_registro[62].toString().split("-");
                out.print("<b>Lote en C proceso : </b><br />");
                out.print("<input type='text' name='Txt_lote_proceso_1' id='Txt_lote_proceso_1' placeholder='Lote C' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>"
                        + "var val1 = new LiveValidation('Txt_lote_proceso_1');val1.add(Validate.Presence);val1.add(Validate.Lotec);val1.add(Validate.LoteC);"
                        + "val1.add( Validate.Inclusion, { within: [ ");
                for (int i = 0; i < lst_materiales.length; i++) {
                    if (i == (lst_materiales.length - 1)) {
                        out.print("'" + lst_materiales[i] + "','N/A'");
                    } else {
                        out.print("'" + lst_materiales[i] + "',");
                    }
                }
                out.print("], partialMatch: true } );"
                        + "</script>");
                out.print("<br />");
                out.print("<b>Lote en P proceso : </b><br />");
                out.print("<input type='text' name='Txt_lote_proceso_2' id='Txt_lote_proceso_2' placeholder='Lote P' onkeyup='javascript:this.value=this.value.toUpperCase();' value='N/A'/>"
                        + "<script type='text/javascript'>"
                        + "var val1 = new LiveValidation('Txt_lote_proceso_2');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                        + "val1.add( Validate.Inclusion, { within: [ ");
                for (int i = 0; i < lst_materiales.length; i++) {
                    if (i == (lst_materiales.length - 1)) {
                        out.print("'" + lst_materiales[i] + "','N/A'");
                    } else {
                        out.print("'" + lst_materiales[i] + "',");
                    }
                }
                out.print("], partialMatch: true } );"
                        + "</script>");
                out.print("<br />");
                out.print("<b>Otro lote proceso : </b><br />");
                out.print("<input type='text' name='Txt_lote_proceso_3' id='Txt_lote_proceso_3' placeholder='Otro lote' onkeyup='javascript:this.value=this.value.toUpperCase();' value='N/A'/>"
                        + "<script type='text/javascript'>"
                        + "var val1 = new LiveValidation('Txt_lote_proceso_3');val1.add(Validate.Presence);"
                        + "val1.add( Validate.Inclusion, { within: [ ");
                for (int i = 0; i < lst_materiales.length; i++) {
                    if (i == (lst_materiales.length - 1)) {
                        out.print("'" + lst_materiales[i] + "','N/A'");
                    } else {
                        out.print("'" + lst_materiales[i] + "',");
                    }
                }
                out.print("], partialMatch: true } );"
                        + "</script>");
                out.print("<br />");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                if (obj_registro[65].toString().equals("R-PRF-056")) {
                    out.print("<input type='submit' value='Registrar' onclick='ejecutarFormEnt()'/>");
                } else {
                    out.print("<input type='submit' value='Registrar' />");
                }
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_entradas_material = jpacrem.Entradas_materiales_registro(id_registro);
                if (lst_entradas_material == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos de control de entradad de materiales</b>");
                    out.print("</center>");
                } else {
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Producto en proceso</th>");
                    out.print("<th colspan='3'>Lotes en proceso</th>");
                    out.print("<th rowspan='2'>Responsable<br />proceso</th>");
                    out.print("<th rowspan='2'>Producto <br />entrante</th>");
                    out.print("<th colspan='3'>Lote entrante</th>");
                    out.print("<th rowspan='2'>Cantidad</th>");
                    out.print("<th rowspan='2'>Responsable<br />entrada</th>");
                    if (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                        if ((Integer) obj_registro[16] != 0) {
                            out.print("<th rowspan='2'>Entrada</th>");
                            out.print("<th rowspan='2'>Quitar</th>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<td align='center'><b>C</b></td>");
                    out.print("<td align='center'><b>P</b></td>");
                    out.print("<td align='center'><b>Otro</td>");
                    out.print("<td align='center'><b>C</b></td>");
                    out.print("<td align='center'><b>P</b></td>");
                    out.print("<td align='center'><b>Otro</b></td>");
                    out.print("<tr>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_entradas_material.size(); i++) {
                        Object[] obj_entradas_material = (Object[]) lst_entradas_material.get(i);
                        String[] arg_responsables_proceso = obj_entradas_material[5].toString().split("/");
                        out.print("<tr>");
                        out.print("<td>" + obj_entradas_material[2] + "</td>");
                        out.print("<td>" + obj_entradas_material[3] + "</td>");
                        out.print("<td>" + obj_entradas_material[4] + "</td>");
                        out.print("<td>" + obj_entradas_material[14] + "</td>");
                        out.print("<td>" + arg_responsables_proceso[1] + "<br />(" + obj_entradas_material[6] + ")</td>");
                        if (obj_entradas_material[7] == null) {
                            out.print("<td colspan='6' align='center'><b class='rojo' >Pendiente datos del producto entrante a la línea.</b></td>");
                        } else {
                            String[] arg_responsables_entrante = obj_entradas_material[12].toString().split("/");
                            out.print("<td><b class='calidad'>" + obj_entradas_material[7] + "</b></td>");
                            out.print("<td><b class='calidad'>" + obj_entradas_material[8] + "</b></td>");
                            out.print("<td><b class='calidad'>" + obj_entradas_material[9] + "</b></td>");
                            out.print("<td><b class='calidad'>" + obj_entradas_material[15] + "</b></td>");
                            out.print("<td><b class='calidad'>" + obj_entradas_material[10] + " " + obj_entradas_material[11] + "</b></td>");
                            out.print("<td><b class='calidad'>" + arg_responsables_entrante[1] + "<br />(" + obj_entradas_material[13] + ")</b></td>");
                        }
                        if (rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if ((Integer) obj_registro[16] != 0) {
                                if (obj_entradas_material[7] == null) {
                                    out.print("<td align='center'><span class='fa fa-pen fa-size_small' onclick=\"location.href='Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=" + obj_entradas_material[0] + "'\" title='Datos entrantes' ></span></td>");
                                } else {
                                    out.print("<td align='center'><span class='fa fa-pen fa-size_small color_span' onclick=\"location.href='Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=" + obj_entradas_material[0] + "'\" title='Ya han sido registrados los datos entrantes de la línea' ></span></td>");
                                }
                                out.print("<td align='center'><span class='fa fa-times fa-size_small' onclick='EliminarEntradaMaterial(" + obj_entradas_material[0] + "," + id_registro + ")' title='Quitar Entrada de material'></span></td>");
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO CALIDAD">
                if (id_entrada_material != 0) {
                    lst_entrada_material = jpacrem.Traer_entradas_materiales_id(id_entrada_material);
                    Object[] obj_entrada_material = (Object[]) lst_entrada_material.get(0);
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03; display: block;'>");
                    out.print("<fieldset class='popup_local' style='width:600px;position:absolute;top:15%;left:25%'>");
                    out.print("<div style='float:right'><span class='fa fa-times fa-size_small' onclick=\"location.href='Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=0'\" title='Cancelar registro de material entrante' ></span></div>");
                    out.print("<form action='Registro?opc=22' method='post' onsubmit='checkSubmit();' id='formValidEnt'>");

                    out.print("<div style='width:40%;float:left'>");
                    out.print("<h3>Material entrante</h3>");
                    out.print("Validar la información del material en proceso y completar la entrada con la generación de lotes , cantidad y unidad de medida.<br /><br />");
                    out.print("<b>Producto en proceso : </b><b class='negro'>" + obj_entrada_material[2].toString().toUpperCase() + "</b><br />");
                    out.print("<b>Lote en proceso C : </b><b class='negro'>" + obj_entrada_material[3].toString().toUpperCase() + "</b><br />");
                    out.print("<b>Lote en proceso P : </b><b class='negro'>" + obj_entrada_material[4].toString().toUpperCase() + "</b><br />");
                    out.print("<b>Otro lote en proceso : </b><b class='negro'>" + obj_entrada_material[14].toString().toUpperCase() + "</b><br />");
                    out.print("<br /><b>Materiales : </b>" + obj_registro[62] + "<br/>");

                    if (obj_registro[65].toString().equals("R-PRF-056")) {
                        out.print("<input type='submit' value='Registrar' onclick='ejecutarFormEntVal()'/>");
                    } else {
                        out.print("<input type='submit' value='Registrar' />");
                    }

                    out.print("</div>");

                    out.print("<div style='width:55%;float:left'>");
                    out.print("<input type='hidden' name='Producto_proceso' id='Producto_proceso' value='" + obj_entrada_material[2] + "' />");
                    out.print("<b>Producto entrante : </b><br /><input type='text' name='Txt_producto_entrante' id='Txt_producto_entrante' value='" + obj_entrada_material[2] + "' placeholder='Producto entrante' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_producto_entrante');val1.add(Validate.Presence);val1.add(Validate.Confirmation,{ match: 'Producto_proceso'});</script><br />");
                    out.print("<b>Lote entrante C : </b><br /><input type='text' name='Txt_lote_entrante_1' id='Txt_lote_entrante_1' value='" + obj_entrada_material[3] + "' placeholder='Lote entrante C' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_entrante_1');val1.add(Validate.Presence);val1.add(Validate.LoteC);"
                            + "val1.add( Validate.Inclusion, { within: [ ");
                    for (int i = 0; i < lst_materiales.length; i++) {
                        if (i == (lst_materiales.length - 1)) {
                            out.print("'" + lst_materiales[i] + "','N/A'");
                        } else {
                            out.print("'" + lst_materiales[i] + "',");
                        }
                    }
                    out.print("], partialMatch: true } );"
                            + "</script><br />");
                    out.print("<b>Lote entrante P : </b><br /><input type='text' name='Txt_lote_entrante_2' id='Txt_lote_entrante_2' value='" + obj_entrada_material[4] + "' placeholder='Lote entrante P' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_entrante_2');val1.add(Validate.Presence);val1.add(Validate.LoteP);"
                            + "val1.add( Validate.Inclusion, { within: [ ");
                    for (int i = 0; i < lst_materiales.length; i++) {
                        if (i == (lst_materiales.length - 1)) {
                            out.print("'" + lst_materiales[i] + "','N/A'");
                        } else {
                            out.print("'" + lst_materiales[i] + "',");
                        }
                    }
                    out.print("], partialMatch: true } );"
                            + "</script><br />");
                    out.print("<b>Otro lote entrante : </b><br />");
                    out.print("<input type='text' name='Txt_lote_entrante_3' id='Txt_lote_entrante_3' placeholder='Otro lote entrante' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_entrada_material[14] + "' />"
                            + "<script type='text/javascript'>"
                            + "var val1 = new LiveValidation('Txt_lote_entrante_3');val1.add(Validate.Presence);"
                            + "val1.add( Validate.Inclusion, { within: [ ");
                    for (int i = 0; i < lst_materiales.length; i++) {
                        if (i == (lst_materiales.length - 1)) {
                            out.print("'" + lst_materiales[i] + "','N/A'");
                        } else {
                            out.print("'" + lst_materiales[i] + "',");
                        }
                    }
                    out.print("], partialMatch: true } );"
                            + "</script><br />");
                    out.print("<b>Cantidad entrante : </b><br /><input type='text' name='Txt_cantidad' id='Txt_cantidad' placeholder='Cantidad'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad');val1.add(Validate.Presence);val1.add(Validate.Decimal_total);</script><br />");
                    out.print("<b>Unidad de medida : </b><br />");
                    out.print("<select name='Cbx_unidad' id='Cbx_unidad' >");
                    out.print("<option value='0' >Unidad de medida</option>");
                    out.print("<option value='Und(s)' >Und(s)</option>");
                    out.print("<option value='Paquete(s)' >Paquete(s)</option>");
                    out.print("<option value='Rollo(s)' >Rollo(s)</option>");
                    out.print("<option value='Kg(s)' >Kg(s)</option>");
                    out.print("<option value='g' >g</option>");
                    out.print("<option value='m(s)' >m(s)</option>");
                    out.print("<option value='Caja(s)' >Caja(s)</option>");
                    out.print("<option value='Bolsa(s)' >Bolsa(s)</option>");
                    out.print("<option value='Frasco(s)' >Frasco(s)</option>");
                    out.print("<option value='Impresion(es)' >Impresion(es)</option>");
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_unidad');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                    out.print("<input type='hidden' name='Id_entrada' id='Id_entrada' value='" + obj_entrada_material[0] + "' />");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                }
//</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                // </editor-fold>
            } else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_hora_montaje")) {
                //<editor-fold defaultstate="collapsed" desc="HORA MONTAJE INSUMOS">
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                int hora = 0, temp1 = 0;
                try {
                    hora = Integer.parseInt(pageContext.getRequest().getAttribute("HoraSeleccionada").toString());
                } catch (Exception e) {
                    hora = 0;
                }
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                Date horaAct = new Date();
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
                String horaVal = formatoHora.format(horaAct);
                lst_hora_insumo = RegistroHraJpa.Registro_hora_insumos(id_registro);
                if (lst_hora_insumo != null) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DUCTOS">
                    Object[] obj_insu = (Object[]) lst_hora_insumo.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_del'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar Hora Montaje Insumos</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times' style='font-size: 23px;'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Registro?opc=55&Id_registro=" + id_registro + "' method='post'>");
                    out.print("<input type='hidden' name='temp1' value='1'>");

                    out.print("<div class='' style='display: flex; justify-content: space-around;'>");
                    out.print("<div class=''>");
                    out.print("<span><b>Ducto Izquierdo</b></span><br>");
                    out.print("<b>C:</b><input type='text' id='dto_izqc' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_izqc' placeholder='Ducto C' value='" + obj_insu[4] + "' required> <br>");
                    out.print("<b>P:</b><input type='text' id='dto_izqp' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_izqp' placeholder='Ducto P' value='" + obj_insu[5] + "' required>");
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<span><b>Ducto Derecho</b></span><br>");
                    out.print("<b>C:</b><input type='text' id='dto_drcc' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_drcc' placeholder='Ducto C' value='" + obj_insu[6] + "' required><br>");
                    out.print("<b>P:</b><input type='text' id='dto_drcp' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_drcp' placeholder='Ducto P' value='" + obj_insu[7] + "' required>");
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<span><b>Ducto Central</b></span><br>");
                    out.print("<b>C:</b><input type='text' id='dto_ctrl' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_ctlc' placeholder='Ducto C' value='" + obj_insu[8] + "' required><br>");
                    out.print("<b>P:</b><input type='text' id='dto_ctrl' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_ctlp' placeholder='Ducto P' value='" + obj_insu[9] + "' required>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div style='text-align: center; margin-top: 15px;'>");
                    out.print("<input type='submit' value='Modificar' style='cursor:pointer;'>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                if (lst_hora_insumo == null) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DUCTOS">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_del'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Hora Montaje Insumos</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times' style='font-size: 23px;'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Registro?opc=55&Id_registro=" + id_registro + "' method='post'>");

                    out.print("<div class='' style='display: flex; justify-content: space-around;'>");
                    out.print("<div class=''>");
                    out.print("<span><b>Ducto Izquierdo</b></span><br>");
                    out.print("<b>C:</b><input type='text' id='dto_izqc' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_izqc' placeholder='Ducto C' required> <br>");
                    out.print("<b>P:</b><input type='text' id='dto_izqp' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_izqp' placeholder='Ducto P' required>");
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<span><b>Ducto Derecho</b></span><br>");
                    out.print("<b>C:</b><input type='text' id='dto_drcc' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_drcc' placeholder='Ducto C' required><br>");
                    out.print("<b>P:</b><input type='text' id='dto_drcp' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_drcp' placeholder='Ducto P' required>");
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<span><b>Ducto Central</b></span><br>");
                    out.print("<b>C:</b><input type='text' id='dto_drcc' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_ctlc' placeholder='Ducto C' required><br>");
                    out.print("<b>P:</b><input type='text' id='dto_drcp' style='margin-top: 10px;margin-bottom: 10px;margin-left: 10px;' name='dto_ctlp' placeholder='Ducto P' required>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div style='text-align: center; margin-top: 15px;'>");
                    out.print("<input type='submit' value='Registrar' style='cursor:pointer;'>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    if (hora > 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO HORA INSUMOS">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + ((hora > 0) ? "block" : "none") + ";'>");
                        out.print("<div class='cont_del'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Datos Hora: " + hora + "</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Registro?opc=56&Id_registro=" + id_registro + "&cbx_hora=" + hora + "' method='post'>");
                        Object[] obj_hm = (Object[]) lst_hora_insumo.get(0);
                        int hm = 9 + hora;
                        String hm_iz = "";
                        String hm_dc = "";
                        String hm_ct = "";
                        if (obj_hm[hm] != null) {
                            if (obj_hm[hm].toString().contains("///")) {
                                String[] arr_hm = obj_hm[hm].toString().split("///");
                                for (int i = 0; i < arr_hm.length; i++) {
                                    if (arr_hm[i].toString().contains("[IZQ]")) {
                                        hm_iz = arr_hm[i].toString();
                                    } else if (arr_hm[i].toString().contains("[DRC]")) {
                                        hm_dc = arr_hm[i].toString();
                                    } else if (arr_hm[i].toString().contains("[CTL]")) {
                                        hm_ct = arr_hm[i].toString();
                                    }
                                }
                            } else {
                                if (obj_hm[hm].toString().contains("[IZQ]")) {
                                    hm_iz = obj_hm[hm].toString();
                                } else if (obj_hm[hm].toString().contains("[DRC]")) {
                                    hm_dc = obj_hm[hm].toString();
                                } else if (obj_hm[hm].toString().contains("[CTL]")) {
                                    hm_ct = obj_hm[hm].toString();
                                }
                            }
                        } else {
                            hm_iz = "";
                            hm_dc = "";
                            hm_ct = "";
                        }
                        //<editor-fold defaultstate="collapsed" desc="DUCTO IZQUIERDO">
                        out.print("<p style='margin-bottom: 0px;'><b>Ducto Izquierdo</b></p>");
                        out.print("<div class='cont_hm' style='display: flex;justify-content: space-around;'>");
                        out.print("<div class=''>");
                        String[] values_iz = {};
                        if (!hm_iz.equals("")) {
                            values_iz = hm_iz.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        }
                        out.print("<p>Consecutivo</p>");
                        out.print("<input type='text' class='form-control' id='txt_conse_iz' name='txt_conse_iz' style='text-align: center;' value='" + ((hm_iz.equals("")) ? "" : values_iz[1]) + "' placeholder='consecutivo'>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<p>Seleccionar lado:</p>");
                        out.print("<select style='text-align:center;' id='cbx_lados_iz' name='cbx_lados_iz'>");
                        lst_parametros = RegistroHraJpa.ConsultarParametrosxCategoria("LadosMontaje");
                        if (lst_parametros != null) {
                            if (!hm_iz.equals("")) {
                                out.print("<option value='" + values_iz[2] + "'>" + values_iz[2] + "</option>");
                            } else {
                                out.print("<option value='0'>seleccionar lado</option>");
                            }
                            Object[] obj_param = (Object[]) lst_parametros.get(0);
                            String[] lados = obj_param[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < lados.length; i++) {
                                out.print("<option value='" + lados[i] + "'>" + lados[i] + "</option>");
                            }
                        } else {
                            out.print("<option value='0'>Error de parametros</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<p>Ingresar hora:</p>");
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String horaFormateada = "";
                        if (!hm_iz.equals("")) {
                            if (!values_iz[3].toString().equals("")) {
                                String temp = values_iz[3].toString();
                                Date horax = sdf.parse(temp);
                                horaFormateada = sdf.format(horax);
                            }
                        }
                        out.print("<input type='time' style='text-align: center;' class='form-control' id='txt_hora_iz' name='txt_hora_iz' value='" + ((hm_iz.equals("")) ? horaVal : horaFormateada) + "' placeholder='Hora' required>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="DUCTO DERECHO">
                        out.print("<p style='margin-bottom: 0px;'><b>Ducto Derecho</b></p>");
                        out.print("<div class='cont_hm' style='display: flex;justify-content: space-around;'>");
                        out.print("<div class=''>");
                        String[] values_dc = {};
                        if (!hm_dc.equals("")) {
                            values_dc = hm_dc.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        }
                        out.print("<p>Consecutivo</p>");
                        out.print("<input type='text' class='form-control' style='text-align: center;' id='txt_conse_dc' name='txt_conse_dc' value='" + ((hm_dc.equals("")) ? "" : values_dc[1]) + "' placeholder='consecutivo'>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<p>Seleccionar lado:</p>");
                        out.print("<select style='text-align:center;' id='cbx_lados_dc' name='cbx_lados_dc'>");
                        lst_parametros = RegistroHraJpa.ConsultarParametrosxCategoria("LadosMontaje");
                        if (lst_parametros != null) {
                            if (!hm_dc.equals("")) {
                                out.print("<option value='" + values_dc[2] + "'>" + values_dc[2] + "</option>");
                            } else {
                                out.print("<option value='0'>seleccionar lado</option>");
                            }
                            Object[] obj_param = (Object[]) lst_parametros.get(0);
                            String[] lados = obj_param[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < lados.length; i++) {
                                out.print("<option value='" + lados[i] + "'>" + lados[i] + "</option>");
                            }
                        } else {
                            out.print("<option value='0'>Error de parametros</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        horaFormateada = "";
                        if (!hm_dc.equals("")) {
                            if (!values_dc[3].toString().equals("")) {
                                String temp = values_dc[3].toString();
                                Date horax = sdf.parse(temp);
                                horaFormateada = sdf.format(horax);
                            }
                        }
                        out.print("<p>Ingresar hora:</p>");
                        out.print("<input type='time' style='text-align: center;' class='form-control' id='txt_hora_dc' name='txt_hora_dc' value='" + ((hm_dc.equals("")) ? horaVal : horaFormateada) + "' placeholder='Hora' required>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="DUCTO CENTRAL">
                        out.print("<p style='margin-bottom: 0px;'><b>Ducto Central</b></p>");
                        out.print("<div class='cont_hm' style='display: flex;justify-content: space-around;'>");
                        out.print("<div class=''>");
                        String[] values_ct = {};
                        if (!hm_ct.equals("")) {
                            values_ct = hm_ct.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        }
                        out.print("<p>Consecutivo</p>");
                        out.print("<input type='text' class='form-control' style='text-align: center;' id='txt_conse_ct' name='txt_conse_ct' value='" + ((hm_ct.equals("")) ? "" : values_ct[1]) + "' placeholder='consecutivo'>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<p>Seleccionar lado:</p>");
                        out.print("<select style='text-align:center;' id='cbx_lados_ct' name='cbx_lados_ct'>");
                        lst_parametros = RegistroHraJpa.ConsultarParametrosxCategoria("LadosMontaje");
                        if (lst_parametros != null) {
                            if (!hm_ct.equals("")) {
                                out.print("<option value='" + values_ct[2] + "'>" + values_ct[2] + "</option>");
                            } else {
                                out.print("<option value='0'>seleccionar lado</option>");
                            }
                            Object[] obj_param = (Object[]) lst_parametros.get(0);
                            String[] lados = obj_param[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < lados.length; i++) {
                                out.print("<option value='" + lados[i] + "'>" + lados[i] + "</option>");
                            }
                        } else {
                            out.print("<option value='0'>Error de parametros</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        horaFormateada = "";
                        if (!hm_ct.equals("")) {
                            if (!values_ct[3].toString().equals("")) {
                                String temp = values_ct[3].toString();
                                Date horax = sdf.parse(temp);
                                horaFormateada = sdf.format(horax);
                            }
                        }
                        out.print("<p>Ingresar hora:</p>");
                        out.print("<input type='time' style='text-align: center;' class='form-control' id='txt_hora_ct' name='txt_hora_ct' value='" + ((hm_ct.equals("")) ? horaVal : horaFormateada) + "' placeholder='Hora' required>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>

                        out.print("<div class='' style='text-align: center; margin-top:20px;'>");
                        out.print("<input type='submit' class='form-control' value='Registrar'>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR RESPONSABLE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_del' style='width: 25%; margin-left: 40%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2> Ingresar responsable </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user' style='text-align:center;'>");
                out.print("<form action='Registro?opc=57&Id_registro=" + id_registro + "' method='post'>");
                out.print("<input type='hidden' id='txt_tempo' name='txt_tempo'>");
                out.print("<input type='text' id='txt_resonsable' placeholder='Nombre apellido / Codigo' style='margin-top:20px; margin-bottom: 20px;' name='txt_resonsable'>");
                out.print("<input type='submit' value='Registrar'>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div id='content'><br />");
                out.print("<div class='' style='display: flex;align-items: center;'>");
                if (lst_hora_insumo == null) {
                    out.print("<button class='btn' style='height: 25px;cursor: pointer; font-size: 24px;background: transparent;border: none;margin-right: 10px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                } else {
                    out.print("<button class='btn' style='height: 25px;cursor: pointer; font-size: 24px;background: transparent;border: none;margin-right: 10px;' onclick='mostrarConvencion(3)'><i class='fas fa-pen'></i></button>");
                }
                out.print("<h3>Hora montaje insumos</h3>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                out.print("<div class=''>");
                if (lst_hora_insumo != null) {
                    Object[] obj_insu = (Object[]) lst_hora_insumo.get(0);
                    int itera = 10;
                    String hora_data = "";
                    String hora_cons = "";
                    String hora_izq = "";
                    String hora_drc = "";
                    String hora_ctl = "";
                    for (int i = 1; i <= 8; i++) {
                        if (obj_insu[itera] != null) {
                            if (obj_insu[itera].toString().contains("///")) {
                                String[] arr_lif = obj_insu[itera].toString().split("///");
                                hora_data += "[" + i + arr_lif[0] + "]--";
                                hora_data += "[" + i + arr_lif[1] + "]--";
                                hora_data += "[" + i + arr_lif[2] + "]--";
                            } else {
                                hora_data += "[" + i + obj_insu[itera] + "]--";
                            }
                        }
                        itera++;
                    }
                    if (!hora_data.equals("")) {
                        String[] arr_globe = hora_data.split("--");
                        for (int j = 0; j < arr_globe.length; j++) {
                            if (arr_globe[j].toString().contains("[CONS]")) {
                                hora_cons += arr_globe[j].toString() + "///";
                            } else if (arr_globe[j].toString().contains("[IZQ]")) {
                                hora_izq += arr_globe[j].toString() + "///";
                            } else if (arr_globe[j].toString().contains("[DRC]")) {
                                hora_drc += arr_globe[j].toString() + "///";
                            } else if (arr_globe[j].toString().contains("[CTL]")) {
                                hora_ctl += arr_globe[j].toString() + "///";
                            }
                        }
                    }
                    String respons_iz = "";
                    String respons_dc = "";
                    String respons_ct = "";
                    if (obj_insu[18] != null) {
                        if (obj_insu[18].toString().contains("///")) {
                            String[] respoData = obj_insu[18].toString().split("///");
                            for (int i = 0; i < respoData.length; i++) {
                                if (respoData[i].toString().contains("[IZQ]")) {
                                    respons_iz = respoData[i].toString();
                                } else if (respoData[i].toString().contains("[DRC]")) {
                                    respons_dc = respoData[i].toString();
                                } else if (respoData[i].toString().contains("[CTL]")) {
                                    respons_ct = respoData[i].toString();
                                }
                            }
                        } else {
                            if (obj_insu[18].toString().contains("[IZQ]")) {
                                respons_iz = obj_insu[18].toString();
                            } else if (obj_insu[18].toString().contains("[DRC]")) {
                                respons_dc = obj_insu[18].toString();
                            } else if (obj_insu[18].toString().contains("[CTL]")) {
                                respons_ct = obj_insu[18].toString();
                            }
                        }
                        respons_iz = respons_iz.replace("[IZQ]", "");
                        respons_dc = respons_dc.replace("[DRC]", "");
                        respons_ct = respons_ct.replace("[CTL]", "");
                    } else {

                    }

                    out.print("<form action='Registro?opc=54&Id_registro=" + id_registro + "' method='post' id='form_hora' method='post'>");
                    out.print("<input type='hidden' class='form-control' id='cbx_hora_hm' name='cbx_hora'>");
                    out.print("</form>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>INSUMO</th>");
                    for (int i = 1; i < 9; i++) {
                        out.print("<th><button class='btn_hora' onclick='ejectForm(" + i + ")'>" + i + "</button></th>");
                    }
                    out.print("<th>RESPONSABLE</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    int irr = 0;
                    for (int i = 1; i < 9; i++) {
                        if (!hora_cons.equals("")) {
                            String[] arr_cons = hora_cons.toString().split("///");
                            if (arr_cons[irr].contains("[" + i + "[")) {
                                String data = arr_cons[irr].toString().replace("[" + i + "[", "[").replace("]]", "]");
                                String[] date = data.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<td rowspan='2' align='center'>" + date[1] + " <br> " + date[2] + " <br> " + date[3] + "</td>");
                                if (irr < arr_cons.length - 1) {
                                    irr++;
                                }
                            } else {
                                out.print("<td rowspan='2'> &nbsp; </td>");
                            }
                        } else {
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="DUCTO IZQUIERDO">
                    out.print("<td><b>DUCTO IZQUIERDO</b></td>");
                    irr = 0;
                    for (int i = 1; i < 9; i++) {
                        if (!hora_izq.equals("")) {
                            String[] arr_izq = hora_izq.toString().split("///");
                            if (arr_izq[irr].contains("[" + i + "[")) {
                                String data = arr_izq[irr].toString().replace("[" + i + "[", "[").replace("]]", "]");
                                String[] date = data.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<td rowspan='3' align='center'>" + date[1] + " <br> " + date[2] + " <br> " + date[3] + "</td>");
                                if (irr < arr_izq.length - 1) {
                                    irr++;
                                }
                            } else {
                                out.print("<td rowspan='3'> &nbsp; </td>");
                            }
                        } else {
                            out.print("<td rowspan='3'> &nbsp; </td>");
                        }
                    }
                    if (!respons_iz.equals("")) {
                        out.print("<td rowspan='3' align='center' style='width: 20%;font-size: 10px; cursor:pointer;'>" + respons_iz + " </td>");
                    } else {
                        out.print("<td rowspan='3' align='center' style='width: 20%;font-size: 20px; cursor:pointer;'> <i class=\"fas fa-user-plus\" onclick='mostrarConvencion(2);sendData(1);'></i> </td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>C: " + obj_insu[4] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>P: " + obj_insu[5] + "</td>");
                    //</editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="DUCTO DERECHO">
                    out.print("<td><b>DUCTO DERECHO</b></td>");
                    irr = 0;
                    for (int i = 1; i < 9; i++) {
                        if (!hora_drc.equals("")) {
                            String[] arr_drc = hora_drc.toString().split("///");
                            if (arr_drc[irr].contains("[" + i + "[")) {
                                String data = arr_drc[irr].toString().replace("[" + i + "[", "[").replace("]]", "]");
                                String[] date = data.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<td rowspan='3' align='center'>" + date[1] + " <br> " + date[2] + " <br> " + date[3] + "</td>");
                                if (irr < arr_drc.length - 1) {
                                    irr++;
                                }
                            } else {
                                out.print("<td rowspan='3'> &nbsp; </td>");
                            }
                        } else {
                            out.print("<td rowspan='3'> &nbsp; </td>");
                        }
                    }
                    if (!respons_dc.equals("")) {
                        out.print("<td rowspan='3' align='center' style='width: 20%;font-size: 10px; cursor:pointer;'>" + respons_dc + " </td>");
                    } else {
                        out.print("<td rowspan='3' align='center' style='width: 20%;font-size: 20px; cursor:pointer;'> <i class=\"fas fa-user-plus\" onclick='mostrarConvencion(2);sendData(2);'></i> </td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>C: " + obj_insu[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>P: " + obj_insu[7] + "</td>");
                    //</editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="DUCTO CENTRAL">
                    out.print("<td><b>DUCTO CENTRAL</b></td>");
                    irr = 0;
                    for (int i = 1; i < 9; i++) {
                        if (!hora_ctl.equals("")) {
                            String[] arr_ctl = hora_ctl.toString().split("///");
                            if (arr_ctl[irr].contains("[" + i + "[")) {
                                String data = arr_ctl[irr].toString().replace("[" + i + "[", "[").replace("]]", "]");
                                String[] date = data.replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<td rowspan='3' align='center'>" + date[1] + " <br> " + date[2] + " <br> " + date[3] + "</td>");
                                if (irr < arr_ctl.length - 1) {
                                    irr++;
                                }
                            } else {
                                out.print("<td rowspan='3'> &nbsp; </td>");
                            }
                        } else {
                            out.print("<td rowspan='3'> &nbsp; </td>");
                        }
                    }
                    if (!respons_ct.equals("")) {
                        out.print("<td rowspan='3' align='center' style='width: 20%;font-size: 10px; cursor:pointer;'>" + respons_ct + " </td>");
                    } else {
                        out.print("<td rowspan='3' align='center' style='width: 20%;font-size: 20px; cursor:pointer;'> <i class=\"fas fa-user-plus\" onclick='mostrarConvencion(2);sendData(3);'></i> </td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>C: " + obj_insu[8] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>P: " + obj_insu[9] + "</td>");
                    //</editor-fold>
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<div style='text-align: center;'>");
                    out.print("<i style='font-size: 190px;' class=\"fas fa-exclamation-triangle\"></i>");
                    out.print("<h3>No se han registrado ductos!</h3>");
                    out.print("</div>");
                }
                out.print("</div>");
                //</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
