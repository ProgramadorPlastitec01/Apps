package Tags;

import Controladores.FichaTecnicaJpaController;
import Controladores.ParametroJpaController;
import Controladores.ProductoJpaController;
import Controladores.RegistroFrecuenciaHoraJpaController;
import Controladores.RegistroFrecuenciaMediaHoraJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroObservacionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu_registro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroObservacionJpaController jpacros = new RegistroObservacionJpaController();
            RegistroFrecuenciaMediaHoraJpaController jpacrfm = new RegistroFrecuenciaMediaHoraJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            ProductoJpaController jpacpdt = new ProductoJpaController();
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            //PERMISOS POR ROL
            String[] arg_parametros = {"Pared doble_3_4_5_0",
                "Pared sencilla_6_7_8_0",
                "Soldadura boca_9_10_11_0",
                "Soldadura cola_12_13_14_0",
                "Longitud total_15_16_17_0",
                "Ducto derecho_18_19_20_0",
                "Ducto central_50_51_52_0",
                "Ducto izquierdo_21_22_23_0",
                "Dia.Int.ducto derecho_24_25_26_1",
                "Dia.Int.ducto central_53_54_55_1",
                "Dia.Int.ducto izquierdo_30_31_32_1",
                "Dia.Ext.ducto derecho_27_28_29_1",
                "Dia.Ext.ducto central_56_57_58_1",
                "Dia.Ext.ducto izquierdo_33_34_35_1",
                "Ancho de manga_36_37_38_0",
                "Ancho de ventana_59_60_61_0",
                "Espesor ducto bicapa Ext_62_63_64_0",
                "Espesor ducto bicapa Int_65_66_67_0",
                "Pared sencilla estriada_69_70_71_0",
                "Distancia X4_72_73_74_0",
                "Distancia X5_75_76_77_0"};
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String usuario_rol = pageContext.getSession().getAttribute("Rol/Nombres").toString();
            int id_registro = 0;
            int id_observacion = 0;
            List lst_resgistro = null;
            List lst_plantilla = null;
            List lst_observacion = null;
            List lst_registro_despeje = null;
            //VARIABLES
            int opcion = 0;
            int modulo = 0;
            int contador_numero = 0;
            int contador_estado = 0;
            int contador_caracter = 0;
            int contador_na = 0;
            double sumatoria = 0;
            double promedio = 0;
            String promedio_frecuencia_hora = "";
            List lst_parametros = null;
            List lst_responsables = null;
            List lst_ficha = null;
            String liberado = "";
            // <editor-fold defaultstate="collapsed" desc="MENU DEL REGISTRO">
            if (pageContext.getRequest().getAttribute("Menu_registro") != null) {
                String menu_registro = pageContext.getRequest().getAttribute("Menu_registro").toString();
                id_registro = Integer.parseInt(menu_registro.split("/")[0].toString());
                modulo = Integer.parseInt(menu_registro.split("/")[1].toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                if (lst_registro_despeje == null) {
                    liberado = "N/A";
                } else {
                    Object[] obj_registro_despeje = (Object[]) lst_registro_despeje.get(0);
                    liberado = ((Integer.parseInt(obj_registro_despeje[3].toString()) == 1) ? "Liberado" : "No_liberado").toString();
                }
                out.println("<div align='center' id='Carga' style='display: none;'><br /><br /><br /><img src='Interfaz/Contenido/images/loading.gif' /><br /><br /><b>Enviando....favor esperar</b></div>");
                if (modulo == 0) {
                    out.print("<div style='display:none' id='sidebar'>");
                } else {
                    out.print("<div id='sidebar'>");
                }
                if (liberado.equals("N/A") || liberado.equals("Liberado")) {
                    out.print("<h3>Opciones <br />Consulta Del Registro</h3>");
                    out.print("<ul class='sidebar_menu'>");
                    out.print("<li><a href='Registro?opc=23&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Detalle del registro</a></li>");
                    out.print("<li><a href=\"javascript:window.open('Orden?opc=14&ipd=" + obj_registro[1] + "&iln=" + obj_registro[5] + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0\" >Registro despeje de linea</a></li>");
                    if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                        out.print("<li><a href='Registro?opc=49&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                    } else if (obj_registro[65].toString().equals("R-PRF-056")) {
                        out.print("<li><a href='Registro?opc=53&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                    } else {
                        out.print("<li><a href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                    }
                    out.print("<li>"
                            + "<form action='Orden?opc=6' method='post' name='FormSalir" + id_registro + "' id='FormSalir" + id_registro + "' onsubmit='Enviar_evento();checkSubmit();'>"
                            + "<input type='hidden' name='ipd' value='" + obj_registro[1] + "' />"
                            + "<input type='hidden' name='odn' value='" + obj_registro[18] + "' />"
                            + "<input type='hidden' name='pdt' value='" + obj_registro[21] + "/" + obj_registro[22] + "' />"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='tcs' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormSalir" + id_registro + ".submit()'>Salir del registro</a>"
                            + "</form>"
                            + "</li>");
                    out.print("</ul>");
                    out.print("<h3>Opciones <br />Funcionales Del Registro</h3>");
                    out.print("<ul class='sidebar_menu'>");
                    if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                        out.print("<li><a href='Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1/2 h</a></li>");
                        out.print("<li><a href='Registro?opc=10&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Centros</a></li>");
                    } else if (obj_registro[65].toString().equals("R-PRF-056")) {
                        out.print("<li><a href='Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1h</a></li>");
                    } else {
                        out.print("<li><a href='Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1h</a></li>");
                        out.print("<li><a href='Registro?opc=10&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Bocas</a></li>");
                    }
                    if (!obj_registro[65].toString().equals("R-PRF-056")) {
                        out.print("<li><a href='Registro?opc=11&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Colas</a></li>");
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
                } else {
                    out.print("<h3>Opciones <br />Consulta Del Registro</h3>");
                    out.print("<ul class='sidebar_menu'>");
                    out.print("<li><a href='Registro?opc=23&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Detalle del registro</a></li>");
                    out.print("<li>"
                            + "<form action='Orden?opc=6' method='post' name='FormSalir" + id_registro + "' id='FormSalir" + id_registro + "' onsubmit='Enviar_evento();checkSubmit();'>"
                            + "<input type='hidden' name='ipd' value='" + obj_registro[1] + "' />"
                            + "<input type='hidden' name='odn' value='" + obj_registro[18] + "' />"
                            + "<input type='hidden' name='pdt' value='" + obj_registro[21] + "/" + obj_registro[22] + "' />"
                            + "<input type='hidden' name='irg' value='" + id_registro + "' />"
                            + "<input type='hidden' name='tcs' value='0' />"
                            + "<input type='hidden' name='fto' value='' />"
                            + "<a href='JAVASCRIPT:FormSalir" + id_registro + ".submit()'>Salir del registro</a>"
                            + "</form>"
                            + "</li>");
                    out.print("</ul>");
                    out.print("<h3>Opciones <br />Funcionales Del Registro</h3>");
                    out.print("<ul class='sidebar_menu'>");
                    out.print("<li><a href='Registro?opc=19&Id_registro=" + id_registro + "&Id_entrada=0' onclick='Enviar_evento();checkSubmit();'>Control entrada de materiales</a></li>");
                    out.print("</ul>");
                }
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of sidebar -->");
            }
// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="DETALLE DEL REGISTRO">
            if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_detalle")) {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                String fecha[] = obj_registro[2].toString().split("-");
                String fecha_version = fecha[0] + "." + fecha[1] + fecha[2];
                double fecha_version_decimal = Double.parseDouble(fecha_version);
                out.print("<div id='content'>");
                out.print("<br />");
                out.print("<div id='tab-container' style='border-color:#ddd'>");
                //<editor-fold defaultstate="collapsed" desc="DATOS DEL REGISTRO">
                out.print("<div class='tab-content' style='display: none;'>"
                        + "<b class='tab' title='Datos del registro'>DATOS DEL REGISTRO</b>");
                out.print("<table style='width:100%'>");
                out.print("<tr><td style=';border-right: 1px solid #15aabf;width:50%'>");
                out.print("<dir /><h2>Orden</h2>");
                out.print("<p>Numero Orden:&nbsp&nbsp<b class='negro'>" + obj_registro[18].toString().toUpperCase() + "</b><br />");
                out.print("Cliente :&nbsp&nbsp<b class='negro'>" + obj_registro[19].toString().toUpperCase() + "</b><br />");
                out.print("Observaciones :&nbsp&nbsp<b class='negro'>" + obj_registro[20].toString().toUpperCase() + "</b></p>");
                out.print("<h2>Producto</h2>");
                out.print("<p>Producto :&nbsp&nbsp</b><b class='negro'>" + obj_registro[22].toString().toUpperCase() + "</b><br />");
                out.print("Código :&nbsp&nbsp</b><b class='negro'>" + obj_registro[21].toString().toUpperCase() + "</b><br />");
                out.print("Volumen :&nbsp&nbsp</b><b class='negro'>" + obj_registro[23] + "</b><br />");
                if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("Producto Terminado:&nbsp&nbsp</b><br /><b class='negro'>" + obj_registro[99].toString().split(" ___ ")[0].split(" / ")[1].toUpperCase() + " / " + obj_registro[99].toString().split(" ___ ")[0].split(" / ")[2].toUpperCase() + "</b></p>");
                }
                out.print("<h2>Registro</h2>");
                out.print("<p>Turno :&nbsp&nbsp<b class='negro'>" + obj_registro[4].toString().toUpperCase() + "</b><br />"
                        + "Fecha :&nbsp&nbsp<b class='negro'>" + obj_registro[2].toString().toUpperCase() + "</b><br />");
                out.print("Línea :&nbsp&nbsp<b class='negro'>" + obj_registro[6].toString().toUpperCase() + "</b><br />"
                        + "Lote producto :&nbsp&nbsp</b><b class='negro'>" + obj_registro[3].toString().toUpperCase() + "</b><br />");
                if (obj_registro[65].toString().equals("R-PRF-011") || obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("Lote cola :&nbsp&nbsp</b><b class='negro'>" + ((obj_registro[66] != null) ? obj_registro[66] : "N/A") + "</b><br />");
                    if (obj_registro[65].toString().equals("R-PRF-019")) {
                        out.print("Lote boca :&nbsp&nbsp<b class='negro'>" + ((obj_registro[103] != null) ? obj_registro[103] : "N/A") + "</b><br />");
                    }
                }
                out.print("</p>");
                out.print("</td>");
                out.print("<td style='width:50%' valign='top'>");
                //<editor-fold defaultstate="collapsed" desc="RESPONSABLES">
                out.print("<dir /><h2>Responsables</h2>");
                String[] arg_responsables = obj_registro[17].toString().split(",");
                for (int i = 0; i < arg_responsables.length; i++) {
                    if (rol.equals("Encargada-operaria") || rol.equals("Administrador")) {
                        out.print("<form action='Registro?opc=39' method='post' name='Form_responsables" + i + "' id='Form_responsables" + i + "' onsubmit='checkSubmit();'>");
                        out.print("<input type='hidden' name='Txt_responsables' value='" + obj_registro[17].toString() + "' />"
                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                + "<input type='hidden' name='Posicion' value='" + i + "' />");
                    }
                    String[] arg_responsables_rol = arg_responsables[i].toString().split("/");
                    for (int j = 0; j < arg_responsables_rol.length; j++) {
                        if (rol.equals("Encargada-operaria") || rol.equals("Administrador")) {
                            out.print("<input type='hidden' name='Txt_responsable" + i + "' value='" + arg_responsables_rol[0].toString() + "/" + arg_responsables_rol[1].toString() + "/" + arg_responsables_rol[2].toString() + "' />"
                                    + "<input type='hidden' name='Estado' value='" + arg_responsables_rol[2].toString() + "' />");
                            if (arg_responsables_rol[0].equals("Administrador")) {
                                if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                    out.print("Administrador :&nbsp&nbsp<b>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                            + "<input type='checkbox' onclick='Form_responsables" + i + ".submit();' /><br />");
                                } else {
                                    out.print("Administrador :&nbsp&nbsp<b><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                            + "<input type='checkbox'  onclick='Form_responsables" + i + ".submit();' checked/><br />");
                                }
                                break;
                            } else if (arg_responsables_rol[0].equals("Inspectora-Calidad") || arg_responsables_rol[0].equals("Coordinadora-Calidad")) {
                                if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                    out.print("Calidad :&nbsp&nbsp<b class='calidad'>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                            + "<input type='checkbox'  onclick='Form_responsables" + i + ".submit();' /><br />");
                                } else {
                                    out.print("Calidad :&nbsp&nbsp<b class='calidad'><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                            + "<input type='checkbox'  onclick='Form_responsables" + i + ".submit();' checked/><br />");
                                }
                                break;
                            } else if (arg_responsables_rol[0].equals("Coordinadora-Produccion")) {
                                if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                    out.print("Coordinadora :&nbsp&nbsp<b class='coordinadora'>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                            + "<input type='checkbox'  onclick='Form_responsables" + i + ".submit();' /><br />");
                                } else {
                                    out.print("Coordinadora :&nbsp&nbsp<b class='coordinadora'><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                            + "<input type='checkbox'  onclick='Form_responsables" + i + ".submit();' checked/><br />");
                                }
                                break;
                            } else if (arg_responsables_rol[0].equals("Encargada-operaria")) {
                                if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                    out.print("Encargada-operaria :&nbsp&nbsp<b class='negro'>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                            + "<input type='checkbox'  onclick='Form_responsables" + i + ".submit();' /><br />");
                                } else {
                                    out.print("Encargada-operaria :&nbsp&nbsp<b class='negro'><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                            + "<input type='checkbox' onclick='Form_responsables" + i + ".submit();' checked/><br />");
                                }
                                break;
                            }
                        } else if (arg_responsables_rol[0].equals("Administrador")) {
                            if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                out.print("Administrador :&nbsp&nbsp<b>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                        + "<input type='checkbox' disabled='true' /><br />");
                            } else {
                                out.print("Administrador :&nbsp&nbsp<b><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                        + "<input type='checkbox' disabled='true' checked/><br />");
                            }
                            break;
                        } else if (arg_responsables_rol[0].equals("Inspectora-Calidad") || arg_responsables_rol[0].equals("Coordinadora-Calidad")) {
                            if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                out.print("Calidad :&nbsp&nbsp<b class='calidad'>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                        + "<input type='checkbox' disabled='true'/><br />");
                            } else {
                                out.print("Calidad :&nbsp&nbsp<b class='calidad'><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                        + "<input type='checkbox' disabled='true' checked/><br />");
                            }
                            break;
                        } else if (arg_responsables_rol[0].equals("Coordinadora-Produccion")) {
                            if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                out.print("Coordinadora :&nbsp&nbsp<b class='coordinadora'>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                        + "<input type='checkbox' disabled='true'/><br />");
                            } else {
                                out.print("Coordinadora :&nbsp&nbsp<b class='coordinadora'><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                        + "<input type='checkbox' disabled='true' checked/><br />");
                            }
                            break;
                        } else if (arg_responsables_rol[0].equals("Encargada-operaria")) {
                            if (Integer.parseInt(arg_responsables_rol[2].toString()) == 0) {
                                out.print("Encargada-operaria :&nbsp&nbsp<b class='negro'>" + arg_responsables_rol[1].toString().toUpperCase() + "</b>"
                                        + "<input type='checkbox' disabled='true'/><br />");
                            } else {
                                out.print("Encargada-operaria :&nbsp&nbsp<b class='negro'><u>" + arg_responsables_rol[1].toString().toUpperCase() + "</u></b>"
                                        + "<input type='checkbox' disabled='true' checked/><br />");
                            }
                            break;
                        }
                    }
                    if (rol.equals("Encargada-operaria") || rol.equals("Administrador")) {
                        out.print("</form>");
                    }
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="GENERACIÓN DE LOTES">
                out.print("<h2>Generación de lotes</h2><p>");
                if (obj_registro[65].toString().equals("R-PRF-011") || obj_registro[65].toString().equals("R-PRF-012") || obj_registro[65].toString().equals("R-PRF-019")) {
                    if (fecha_version_decimal >= 2016.0401) {
                        out.print("Manga&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[7].toString().toUpperCase() + "</b>"
                                + "&nbsp&nbsp&nbsp&nbsp<b>C alt:&nbsp&nbsp</b><b class='negro'>" + obj_registro[78].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[8].toString().toUpperCase() + "</b><br />");
                    } else {
                        out.print("Manga&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[7].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[8].toString().toUpperCase() + "</b><br />");
                    }
                } else {
                    out.print("Manga&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[7].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[8].toString().toUpperCase() + "</b><br />");
                }
                if (obj_registro[65].toString().equals("R-PRF-011") || obj_registro[65].toString().equals("R-PRF-013")) {
                    out.print("Ducto derecho&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[9].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[10].toString().toUpperCase() + "</b><br />");
                    if (obj_registro[65].toString().equals("R-PRF-011")) {
                        if (fecha_version_decimal >= 2016.0401) {
                            out.print("Ducto central&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[79].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[80].toString().toUpperCase() + "</b><br />");
                        }
                    }
                    out.print("Ducto izquierdo&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[11].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[12].toString().toUpperCase() + "</b><br />");
                }
                if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("Ducto &nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[9].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp");
                    out.print("<b>C Alt :&nbsp&nbsp</b><b class='negro'>" + ((obj_registro[104] == null) ? obj_registro[104].toString().toUpperCase() : "N/A") + "</b>&nbsp&nbsp&nbsp&nbsp");
                    out.print("<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[10].toString().toUpperCase() + "</b><br />");
                    out.print("Ciclo de esterilización : <b class='negro'>" + obj_registro[106].toString().toUpperCase() + "</b><br />");
                    out.print("Lote tubo de refuerzo : <b class='negro'>" + obj_registro[105].toString().toUpperCase() + "</b><br />");
                }
                if (!(obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012"))) {
                    out.print("Ensamble :&nbsp&nbsp<b class='negro'>" + obj_registro[13].toString().toUpperCase() + "</b><br />"
                            + "Lote ensamble :&nbsp&nbsp<b class='negro'>" + obj_registro[14].toString().toUpperCase() + "</b><br />");
                    if (obj_registro[75] != null && obj_registro[76] != null) {
                        out.print("Ensamble 2:&nbsp&nbsp<b class='negro'>" + obj_registro[75].toString().toUpperCase() + "</b><br />"
                                + "Lote ensamble 2:&nbsp&nbsp<b class='negro'>" + obj_registro[76].toString().toUpperCase() + "</b><br />");
                    }
                    if (obj_registro[65].toString().equals("R-PRF-019")) {
                        if (obj_registro[107] != null && obj_registro[108] != null) {
                            out.print("Ensamble 3:&nbsp&nbsp<b class='negro'>" + obj_registro[107].toString().toUpperCase() + "</b><br />"
                                    + "Lote ensamble 3:&nbsp&nbsp<b class='negro'>" + obj_registro[108].toString().toUpperCase() + "</b><br />");
                        }
                        if (obj_registro[109] != null && obj_registro[110] != null) {
                            out.print("Ensamble 4:&nbsp&nbsp<b class='negro'>" + obj_registro[109].toString().toUpperCase() + "</b><br />"
                                    + "Lote ensamble 4:&nbsp&nbsp<b class='negro'>" + obj_registro[110].toString().toUpperCase() + "</b><br />");
                        }
                    }
                }
                if (obj_registro[65].toString().equals("R-PRF-012")) {
                    out.print("SubLote C&nbsp&nbsp&nbsp&nbsp<b>C :&nbsp&nbsp</b><b class='negro'>" + obj_registro[118].toString().toUpperCase() + "</b>"
                            + "&nbsp&nbsp&nbsp&nbsp<b>C alt:&nbsp&nbsp</b><b class='negro'>" + obj_registro[119].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbsp<b>P :&nbsp&nbsp</b><b class='negro'>" + obj_registro[120].toString().toUpperCase() + "</b><br />");
                }
                if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("Foil :&nbsp&nbsp<b class='negro'>" + obj_registro[67] + " / " + obj_registro[15].toString().toUpperCase() + "</b><br />");
                } else if (!obj_registro[65].toString().equals("R-PRF-012")) {
                    if (obj_registro[65].toString().equals("R-PRF-010") && fecha_version_decimal >= 2020.0601) {
                        out.print("Tinta :&nbsp&nbsp<b class='negro'>" + obj_registro[67] + "</b> M : &nbsp&nbsp<b class='negro'>" + obj_registro[121] + "</b> Color :&nbsp&nbsp  " + obj_registro[15].toString().toUpperCase() + "</b><br />");
                    } else {
                        out.print("Tinta :&nbsp&nbsp<b class='negro'>" + obj_registro[67] + " / " + obj_registro[15].toString().toUpperCase() + "</b><br />");
                    }
                }
                if (obj_registro[65].toString().equals("R-PRF-010") && fecha_version_decimal >= 2020.0601) {
                    out.print("Horno UV &nbsp&nbsp&nbsp&nbsp<b class='negro'>" + obj_registro[122].toString().toUpperCase() + "</b>"
                            + "&nbsp&nbsp&nbsp&nbsp Luz led &nbsp&nbsp<b class='negro'>" + obj_registro[123].toString().toUpperCase() + "</b><br />");
                }
                //</editor-fold>
                out.print("</p></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DATOS DE CONTROL">
                out.print("<div class='tab-content' style='display: none;'>"
                        + "<b class='tab' style='width:200em' title='Datos de control'>DATOS DE CONTROL</b>");
                out.print("<table style='width:100%'>");
                out.print("<tr><td style=';border-right: 1px solid #15aabf;width:50%'>");
                out.print("<dir /><h2>Datos de control</h2><p>");
                out.print("Código :&nbsp&nbsp<b class='negro'>" + obj_registro[24].toString().toUpperCase() + "</b>&nbsp&nbsp&nbsp&nbspVersión :&nbsp&nbsp<b class='negro'>" + obj_registro[25].toString().toUpperCase() + "</b><br />");
                if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("Código EVA:&nbsp&nbsp<b class='negro'>" + obj_registro[99].toString().split(" ___ ")[0].split(" / ")[0].replace(" V", "</b> Versión :<b class='negro'>") + "</b><br />");
//                    out.print("Código EVA:&nbsp&nbsp<b class='negro'>" + obj_registro[99].toString().toUpperCase().replace("V", "</b>&nbsp&nbsp&nbsp&nbspVersión :&nbsp&nbsp<b class='negro'>") + "</b><br />");
                }
                out.print("<h2>Especificaciones</h2><p>");
                out.print("Pared doble :&nbsp&nbsp<b class='negro'>" + obj_registro[26].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[27].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[28].toString().toUpperCase() + "</b><br />");
                out.print("Pared sencilla :&nbsp&nbsp<b class='negro'>" + obj_registro[29].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[30].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[31].toString().toUpperCase() + "</b><br />");
                out.print("Sellado bocas :&nbsp&nbsp<b class='negro'>" + obj_registro[32].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[33].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[34].toString().toUpperCase() + "</b>"
                        + "<br />&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                        + "Sellado alternativo : <b class='negro'>" + obj_registro[68].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[69].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[70].toString().toUpperCase() + "</b><br />");
                out.print("Sellado colas :&nbsp&nbsp<b class='negro'>" + obj_registro[35].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[36].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[37].toString().toUpperCase() + "</b>"
                        + "<br />&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                        + "Sellado alternativo : <b class='negro'>" + obj_registro[71].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[72].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[73].toString().toUpperCase() + "</b><br />");
                out.print("Longitud total :&nbsp&nbsp<b class='negro'>" + obj_registro[38].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[39].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[40].toString().toUpperCase() + "</b><br />");
                out.print("Ducto derecho :&nbsp&nbsp<b class='negro'>" + obj_registro[41].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[42].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[43].toString().toUpperCase() + "</b><br />");
                out.print("Ducto central :&nbsp&nbsp<b class='negro'>" + obj_registro[81].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[82].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[83].toString().toUpperCase() + "</b><br />");
                out.print("Ducto izquierdo :&nbsp&nbsp<b class='negro'>" + obj_registro[44].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[45].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[46].toString().toUpperCase() + "</b><br />");
                out.print("Ancho de manga :&nbsp&nbsp<b class='negro'>" + obj_registro[59].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[60].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[61].toString().toUpperCase() + "</b><br />");
                out.print("Ancho de ventana :&nbsp&nbsp<b class='negro'>" + obj_registro[90].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[91].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[92].toString().toUpperCase() + "</b><br />");
                if (rol.equals("Administrador") || rol.equals("Coordinadora-Produccion") || rol.equals("Coordinadora-Calidad")) {
                    out.print("<b class='naranja'>Opcion temporal para los coordinadores del proceso, se permitira cambiar los datos de la FT en los parametros de X4 y X5</b><br />");
                    out.print("<form action='Registro?opc=52' method='post'>");
                    out.print("<input type='hidden' name='irg' value='" + id_registro + "' />");
                    out.print("<input type='hidden' name='cft' value='" + obj_registro[24] + "' />");
                    out.print("<input type='hidden' name='vft' value='" + obj_registro[25] + "' />");
                    //<editor-fold defaultstate="collapsed" desc="DISTANCIA X4">
                    out.print("<b>Distancia al borde X4 :</b><br />");
                    out.print("<input style='width:150px' type='text' name='Txt_distancia_x4' id='Txt_distancia_x4' value='" + obj_registro[112].toString() + "' placeholder='Distancia a borde X4' title='Distancia a borde X4' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    out.print("<b>+</b>");
                    out.print("<input style='width:50px' type='text' name='Txt_distancia_x4_max' id='Txt_distancia_x4_max' value='" + obj_registro[113].toString() + "'  placeholder='Desv +' title='Desviación Distancia a borde X4' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    out.print("<b>-</b>");
                    out.print("<input style='width:50px' type='text' name='Txt_distancia_x4_min' id='Txt_distancia_x4_min' value='" + obj_registro[114].toString() + "'  placeholder='Desv -' title='Desviación Distancia a borde X4' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x4_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DISTANCIA X5">
                    out.print("<br /><br /><b>Distancia al borde X5 :</b><br />");
                    out.print("<input style='width:150px' type='text' name='Txt_distancia_x5' id='Txt_distancia_x5' value='" + obj_registro[115].toString() + "' placeholder='Distancia a borde X5' title='Distancia a borde X5' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    out.print("<b>+</b>");
                    out.print("<input style='width:50px' type='text' name='Txt_distancia_x5_max' id='Txt_distancia_x5_max' value='" + obj_registro[116].toString() + "' placeholder='Desv +' title='Desviación Distancia a borde X5' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                    out.print("<b>-</b>");
                    out.print("<input style='width:50px' type='text' name='Txt_distancia_x5_min' id='Txt_distancia_x5_min' value='" + obj_registro[117].toString() + "' placeholder='Desv -' title='Desviación Distancia a borde X5' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_distancia_x5_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
//</editor-fold>
                    out.print("<input type='submit' value='Actualizar FT' />");
                    out.print("</form>");
                } else {
                    out.print("Distancia X4 :&nbsp&nbsp<b class='negro'>" + obj_registro[112].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[113].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[114].toString().toUpperCase() + "</b><br />");
                    out.print("Distancia X5 :&nbsp&nbsp<b class='negro'>" + obj_registro[115].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[116].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[117].toString().toUpperCase() + "</b><br />");
                }
                out.print("</p></td>");
                out.print("<td style='width:50%'>");
                out.print("<dir /><h2>Eva</h2><p>");
                out.print("Ducto bicapa espesor interno :&nbsp&nbsp<b class='negro'>" + obj_registro[93].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[94].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[95].toString().toUpperCase() + "</b><br />");
                out.print("Ducto bicapa espesor externo :&nbsp&nbsp<b class='negro'>" + obj_registro[96].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[97].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[98].toString().toUpperCase() + "</b><br />");
                out.print("Pared sencilla estriada :&nbsp&nbsp<b class='negro'>" + obj_registro[100].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[101].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[102].toString().toUpperCase() + "</b><br />");
                out.print("<h2>Diámetros</h2><p>");
                out.print("Diámetro interior ducto derecho :&nbsp&nbsp<b class='negro'>" + obj_registro[47].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[48].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[49].toString().toUpperCase() + "</b><br />");
                out.print("Diámetro exterior ducto derecho :&nbsp&nbsp<b class='negro'>" + obj_registro[50].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[51].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[52].toString().toUpperCase() + "</b><br />");
                out.print("Diámetro interior ducto central :&nbsp&nbsp<b class='negro'>" + obj_registro[84].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[85].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[86].toString().toUpperCase() + "</b><br />");
                out.print("Diámetro exterior ducto central :&nbsp&nbsp<b class='negro'>" + obj_registro[87].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[88].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[89].toString().toUpperCase() + "</b><br />");
                out.print("Diámetro interior ducto izquierdo :&nbsp&nbsp<b class='negro'>" + obj_registro[53].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[54].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[55].toString().toUpperCase() + "</b><br />");
                out.print("Diámetro exterior ducto izquierdo :&nbsp&nbsp<b class='negro'>" + obj_registro[56].toString().toUpperCase() + "</b><b> + </b><b class='negro'>" + obj_registro[57].toString().toUpperCase() + "</b><b> - </b><b class='negro'>" + obj_registro[58].toString().toUpperCase() + "</b><br />");
                out.print("<h2>Materiales</h2><p>");
                out.print("Códigos de materiales :&nbsp&nbsp<b class='negro'><br />" + obj_registro[62].toString().toUpperCase() + "</b><br />");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LIMPIAR MODULOS">
                if (rol.equals("Administrador")) {
                    out.print("<div class='tab-content' style='display: none;'>"
                            + "<b class='tab' style='width:200em' title='Datos de control'>LIMPIAR MÓDULOS</b>");
                    out.print("<dir /><h2>Vaciar módulos automaticos</h2>");
                    if ((Integer) obj_registro[16] == 1) {
                        if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                            out.print("<a onclick='LimpiarModulos(" + id_registro + ",4);' href='#'>Limpiar módulo parametros de frecuencia cada media hora</a><br /><br />");
                        } else {
                            out.print("<a onclick='LimpiarModulos(" + id_registro + ",1);' href='#'>Limpiar módulo parametros de frecuencia por hora</a><br /><br />");
                        }
                        out.print("<a onclick='LimpiarModulos(" + id_registro + ",2);' href='#'>Limpiar módulo verificacion de lote y codigo</a><br /><br />");
                        out.print("<a onclick='LimpiarModulos(" + id_registro + ",3);' href='#'>Limpiar módulo pruebas de calidad</a><br /><br />");
                        out.print("<a onclick='LimpiarModulos(" + id_registro + ",6);' href='#'>Eliminar registro vacio.</a><br /><br />");
                        lst_registro_despeje = jpacrgt.Registro_despeje(id_registro);
                        if (lst_registro_despeje != null) {
                            out.print("<a onclick='LimpiarModulos(" + id_registro + ",5);' href='#'>Limpiar módulo registro despeje de línea</a>");
                        }
                    } else {
                        out.print("<b class='rojo'>El turno se encuentra cerrado para limpiar los módulos.</b>");
                    }
                    out.print("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DATOS ESTADISTICOS">
                if (!obj_registro[65].toString().equals("R-PRF-010") || !obj_registro[65].toString().equals("R-PRF-012")) {
                    String ids_registros = "";
                    out.print("<div class='tab-content' style='display: none;'>"
                            + "<b class='tab' style='width:200em' title='Datos Estadisticos'>DATOS ESTADISTICOS</b>");
                    out.print("<dir /><h2>Datos Estadisticos</h2>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Parametro</th>");
                    out.print("<th>Min</th>");
                    out.print("<th>Max</th>");
                    out.print("<th>Media</th>");
                    out.print("<th>Desviación Estandar</th>");
                    out.print("<th>CP</th>");
                    out.print("<th>CPI</th>");
                    out.print("<th>CPS</th>");
                    out.print("<th>CPK</th>");
                    out.print("</tr>");
                    ids_registros = "r.id_registro = " + id_registro + "";
                    List lst_comparadores = jpacprm.Comparadores();
                    for (int i = 0; i < lst_comparadores.size(); i++) {
                        Object[] obj_comparador = (Object[]) lst_comparadores.get(i);
                        List lst_datos_estadisticos = jpacrfh.Datos_estadisticos_frecuencia_hora(obj_comparador[0].toString(), ids_registros);
                        String datos_estadisticos = jpacrfh.Calcular_CP_CPK_estadisticos(Integer.parseInt(obj_registro[1].toString()), lst_datos_estadisticos, obj_comparador[0].toString());
                        if (datos_estadisticos.contains("-")) {
                            String[] arg_datos_estadisticos = datos_estadisticos.split("-");
                            out.print("<tr>");
                            out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                            for (int j = 1; j < arg_datos_estadisticos.length; j++) {
                                out.print("<td align='center'>" + arg_datos_estadisticos[j] + "</td>");
                            }
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<td><b class='negro'>" + obj_comparador[0] + "</b></td>");
                            out.print("<td colspan='8' align='center'><b class='naranja'>No se pudo realizar calculos la desvisión estandar es cero (0).</b></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");
                }
//</editor-fold>
                out.print("</div><script src='Interfaz/Tabs/tabs.js'></script>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
            } // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="OBSERVACIONES">
            else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_observaciones")) {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                id_observacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_observacion").toString());
                out.print("<div id='content'><br />");
                if (!rol.equals("Consulta")) {
                    if ((Integer) obj_registro[16] == 0) {
                    } else {
                        out.print("<span class='far fa-plus-square fa-size_small' onclick='Form_registro_cabecera()' title='Registrar observaciones'></span> Registrar observaciones");
                    }
                }
                out.print("<h3>Observaciones</h3>");
                //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></div>");
                out.print("<h3>Observaciones " + rol + "</h3><dir>");
                out.print("<form action='Registro?opc=25' method='post' onsubmit='checkSubmit();'>");
                out.print("<b>Asunto : </b><br />");
                out.print("<input type='text' style='width:400px;' name='Txt_asunto' id='Txt_asunto' placeholder='Asunto'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_asunto');val1.add(Validate.Presence);</script><br /><br />");
                out.print("<b>Descripción : </b><br />");
                out.print("<textarea name='Txt_descripcion' style='width:400px;height:200px' id='Txt_descripcion' placeholder='Descricpción de las observaciones del registro.' ></textarea>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");
                out.print("<input type='hidden' name='Id_registro' id='Id_registro' value='" + id_registro + "' />");
                out.print("<div align='right' style='float:right'><input type='submit' value='Registrar' /></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                lst_observacion = jpacros.Observaciones_registro(id_registro);
                if (lst_observacion == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos en observaciones</b>");
                    out.print("</center>");
                } else {
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Fecha<br />(Hora)</th>");
                    out.print("<th style='width:25%'>Asunto</th>");
                    out.print("<th style='width:60%'>Descripción</th>");
                    out.print("<th>Responsable</th>");
                    if ((Integer) obj_registro[16] != 0) {
                        out.print("<th>Modificar</th>");
                        out.print("<th>Quitar</th>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_observacion.size(); i++) {
                        Object[] obj_observacion = (Object[]) lst_observacion.get(i);
                        String[] arg_responsable = obj_observacion[5].toString().split("/");
                        if (obj_observacion[4].equals("Administrador")) {
                            out.print("<tr class='administrador'>");
                        } else if (obj_observacion[4].equals("Inspectora-Calidad") || obj_observacion[4].equals("Coordinadora-Calidad")) {
                            out.print("<tr class='calidad'>");
                        } else if (obj_observacion[4].equals("Coordinadora-Produccion")) {
                            out.print("<tr class='coordinadora'>");
                        } else if (obj_observacion[4].equals("Encargada-operaria")) {
                            out.print("<tr>");
                        }
                        out.print("<td align='center'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</td>");
                        if ((Integer) obj_registro[16] != 0) {
                            if (obj_observacion[5].toString().equals(usuario_rol) || rol.equals("Administrador")) {
                                if (id_observacion == Integer.parseInt(obj_observacion[0].toString())) {
                                    out.print("<td colspan='2'>");
                                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03; display:block;'>");
                                    out.print("<fieldset class='popup_local' style='width:500px;position: absolute;top: 15%;left:25%'>");
                                    out.print("<div style='float:right'><span class='fa fa-times fa-size_small' onclick=\"location.href='Registro?opc=24&Id_registro=" + id_registro + "'\" title='Cancelar modificación observación' ></span></div>");
                                    out.print("<h3>Modificar Observación</h3><dir>");
                                    out.print("<form action='Registro?opc=40&Id_registro=" + id_registro + "' method='post' onsubmit='checkSubmit();' id='Form_modificar_observacion' name='Form_modificar_observacion'>"
                                            + "<input type='hidden' name='Id_observacion' id='Id_observacion' value='" + obj_observacion[0] + "'/>"
                                            + "<script type='text/javascript'>"
                                            + "function checkearTecla_asunto" + obj_observacion[0] + "(e){"
                                            + "if(e.keyCode == 13){"
                                            + "document.getElementById('Form_modificar_observacion').submit();"
                                            + "return true;"
                                            + "}}</script>"
                                            + "<b>Asunto : </b><br /><input type='text' style='width:400px;' name='Txt_asunto_" + obj_observacion[0] + "' id='Txt_asunto_" + obj_observacion[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla_" + obj_observacion[0] + "(event)' value='" + obj_observacion[2] + "' /><br />");
                                    out.print("<script type='text/javascript'>"
                                            + "function checkearTecla_" + obj_observacion[0] + "(e){"
                                            + "if(e.keyCode == 13){"
                                            + "document.getElementById('Form_modificar_observacion').submit();"
                                            + "return true;"
                                            + "}}</script>"
                                            + "<b>Descripcion : </b><br /><textarea style='width:400px;height:200px' name='Txt_descripcion_" + obj_observacion[0] + "' id='Txt_descripcion_" + obj_observacion[0] + "' onkeyup='javascript:this.value=this.value.toUpperCase();' onkeypress='return checkearTecla_" + obj_observacion[0] + "(event)'>" + obj_observacion[3] + "</textarea>"
                                            + "<input style='float:right;' type='submit' value='Modificar' />"
                                            + "</form></fieldset></div></td>");
                                } else {
                                    out.print("<td>" + obj_observacion[2] + "</td>");
                                    out.print("<td>" + obj_observacion[3] + "</td>");
                                }
                                out.print("<td>" + arg_responsable[1] + "</td>");
                                out.print("<td align='center' style='color:#34495e;'><span class='fa fa-pen fa-size_small' onclick=\"location.href='Registro?opc=24&Id_registro=" + id_registro + "&ios=" + obj_observacion[0] + "'\" title='Modificar Observación'></span></td>");
                                out.print("<td align='center' style='color:#34495e;'><span class='fa fa-times fa-size_small' onclick='EliminarObservacion(" + obj_observacion[0] + "," + id_registro + ")' title='Quitar Observación' ></span></td>");
                            } else {
                                out.print("<td>" + obj_observacion[2] + "</td>");
                                out.print("<td>" + obj_observacion[3] + "</td>");
                                out.print("<td>" + arg_responsable[1] + "</td>");
//                                if (obj_observacion[4].equals("Coordinadora-Produccion")) {
//                                    out.print("<td align='center' style='background-color:#fff'><span class='fa fa-pen fa-size_small color_span' title='Sin permisos de modificar observación'></span></td>");
//                                    out.print("<td align='center' style='background-color:#fff'><span class='fa fa-times fa-size_small color_span' title='Sin permisos de quitar observación'></span></td>");
//                                } else {
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small color_span' title='Sin permisos de modificar observación'></span></td>");
                                out.print("<td align='center'><span class='fa fa-times fa-size_small color_span' title='Sin permisos de quitar observación'></span></td>");
//                                }
                            }
                        } else {
                            out.print("<td>" + obj_observacion[2] + "</td>");
                            out.print("<td>" + obj_observacion[3] + "</td>");
                            out.print("<td>" + arg_responsable[1] + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //</editor-fold>
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
            } // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="PARAMETROS DE FRECUENCIA POR CADA MEDIAHORA">
            else if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_parametros_frecuencia_media")) {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                opcion = Integer.parseInt(pageContext.getRequest().getAttribute("Modifica").toString());
                lst_parametros = jpacrfm.Parametros_registro_frecuencia_media_hora(id_registro);
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
                    } else {
                        out.print("<li><a href='#' title='Si el registro esta cerrado no se puede crear registro de despeje'>Registro despeje de linea</a></li>");
                    }
                } else {
                    out.print("<li><a href='Registro?opc=41&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Registro despeje de linea</a></li>");
                }
                if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                    out.print("<li><a href='Registro?opc=49&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                } else {
                    out.print("<li><a href='Registro?opc=27&Id_registro=" + id_registro + "' target='_blank'>Visor de registro</a></li>");
                }
                out.print("<li><a href='Orden?opc=6&ipd=" + obj_registro[1] + "&odn=" + obj_registro[18] + "&pdt=" + obj_registro[21] + "/" + obj_registro[22] + "&irg=" + id_registro + "&tcs=0&fto='>Salir del registro</a></li>");
                out.print("</ul>");
                out.print("<h3>Opciones <br />Funcionales Del Registro</h3>");
                out.print("<ul class='sidebar_menu'>");
                if (obj_registro[65].toString().equals("R-PRF-010") || obj_registro[65].toString().equals("R-PRF-012")) {
                    out.print("<li><a href='Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1/2 h</a></li>");
                    out.print("<li><a href='Registro?opc=10&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Centros</a></li>");
                } else {
                    out.print("<li><a href='Registro?opc=2&Id_registro=" + id_registro + "&Modifica=0' onclick='Enviar_evento();checkSubmit();'>Parametros frecuencia por 1h</a></li>");
                    out.print("<li><a href='Registro?opc=10&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Bocas</a></li>");
                }
                out.print("<li><a href='Registro?opc=11&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Espesor soldadura Colas</a></li>");
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
                out.print("<li><a href='Registro?opc=6&Id_registro=" + id_registro + "' onclick='Enviar_evento();checkSubmit();'>Paradas de máquina</a></li>");
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
                out.print("<h3>Parámetros de frecuencia por cada media hora</h3>");
//                out.print("<div style='display:block'>");
//                if (!rol.equals("Consulta")) {
//                    if ((Integer) obj_registro[16] == 0) {
//                        out.print("<div style='float:left'><h3>Parámetros de frecuencia por cada media hora</h3></div>");
//                    } else if (rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Coordinadora-Produccion")) {
//                        out.print("<div style='float:left'><h3><img onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Parámetros de frecuencia por cada media hora' />"
//                                + "<img onclick='Form_limpiar_cabecera()' src='Interfaz/Contenido/Iconos/Clean.png' width='20px' height='20px' alt='edit' title='Limpiar Estación' /> Parámetros de frecuencia por cada media hora</h3></div>");
//                    } else {
//                        out.print("<div style='float:left'><h3><img onclick='Form_registro_cabecera()' src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px' alt='edit' title='Parámetros de frecuencia por cada media hora' /> Parámetros de frecuencia por hora</h3></div>");
//                    }
//                } else {
//                    out.print("<div style='float:left'><h3>Parámetros de frecuencia por cada media hora</h3></div>");
//                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="LIMPIAR ESTACION">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_limpiar_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<h3>Limpiar Estación</h3>");
                out.print("<form action='Registro?opc=44' method='post' name='FormLimpiar' id='FormLimpiar' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                out.print("<br />Seleccionar estación horaria para la limpiar información.<br /><br />");
                out.print("<select name='Cbx_frecuencia_limpiar' id='Cbx_frecuencia_limpiar' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                for (int i = 1; i <= 18; i++) {
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
                        if (i == 8 || i == 17) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 8) {
                                    out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 9 && i <= 16) {
                                out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                            } else {
                                out.print("<option value='0' style='color:red' >Hora " + ((i == 18) ? (i - 2) : i) + " bloqueada</option>");
                            }
                        }
                        cont_estaciones = 0;
                    } else {
                        if (i == 8 || i == 17) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 8) {
                                    out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 9 && i <= 16) {
                                out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                            } else {
                                out.print("<option value='" + i + "' >Hora " + ((i == 18) ? (i - 2) : i) + "</option>");
                            }
                        }
                        cont_estaciones = 0;
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia_limpiar');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                //out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='JAVASCRIPT:FormLimpiar.submit()'><img src='Interfaz/Contenido/Iconos/Clean.png' width='26px' height='26px' alt='edit' title='Limpiar Registro'></a><br />");
                out.print("<br /><br /><input type='submit' value='Limpiar' /><br />");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="MODIFICAR PARAMETROS">
                if (opcion > 0) {
                    out.print("<div class='sweet-local' tabindex='-1' id='Form_limpiar' style='opacity: 1.03; display: block;'>");
                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 15%;left:25%'>");
                    out.print("<div align='right'><span class='fa fa-times fa-size_small' onclick=\"location.href='Registro?opc=43&Id_registro=" + id_registro + "&Modifica=0'\" title='Cancelar modificación'></span></div>");
                    out.print("<h3>Modificación de parámetro</h3>");
                    out.print("<form action='Registro?opc=46' method='post' name='FormParametros' id='FormParametros' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                    out.print("<input type='hidden' name='Id_parametro' value='" + opcion + "' />");
                    lst_parametros = jpacprm.Traer_parametro(opcion);
                    Object[] obj_parametro = (Object[]) lst_parametros.get(0);
                    out.print("<br />El parámetro a modificar es <b>" + obj_parametro[1].toString().toUpperCase() + "</b> seleccionar estación horaria para la corrección ");
                    out.print(" e ingresar el nuevo valor para la corrección del parámetro ");
                    out.print("<br /><b>Hora</b><br />");
                    out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                    out.print("<option value='0' >Seleccionar toma</option>");
                    for (int i = 1; i <= 18; i++) {
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
                            if (i == 8 || i == 17) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    if (i == 8) {
                                        out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                    } else {
                                        out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                    }
                                }
                            } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                                if (i >= 9 && i <= 16) {
                                    out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Hora " + ((i == 18) ? (i - 2) : i) + " bloqueada</option>");
                                }
                            }
                            cont_estaciones = 0;
                        } else {
                            if (i == 8 || i == 17) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    if (i == 8) {
                                        out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                    } else {
                                        out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                    }
                                }
                            } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                                if (i >= 9 && i <= 16) {
                                    out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Hora " + ((i == 18) ? (i - 2) : i) + "</option>");
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
                                    out.print("<input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametro[0] + "'id='Vlr_parametro_" + obj_parametro[0] + "' />&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + obj_parametro[0] + "');"
                                            + "val1.add(Validate.Presence);"
                                            + "val1.add(Validate.Decimal);"
                                            + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + obj_parametro[0] + "'} );"
                                            + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + obj_parametro[0] + "'} );"
                                            + "</script>");
                                    break;
                                }
                            }
                        }
                    } else if (obj_parametro[5].equals("Caracter")) {
                        out.print("<input type='text' name='Vlr_parametro_" + obj_parametro[0] + "' id='Vlr_parametro_" + obj_parametro[0] + "' placeholder='Valor' onkeyup='Replace(this)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
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
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:700px;position:absolute;top: 2%;left:25%;'>");
                out.print("<div style='float:right;'><span class='fa fa-times fa-size_small' onclick='Form_registro_cabecera_cerrar()' title='Cancelar'></span></div>");
                out.print("<div style='overflow-y:scroll;height:650px;'>");
                out.print("<h3>Registro parámetros de frecuencia por cada media hora</h3>");
                out.print("<form action='Registro?opc=45' method='post' name='FormParametros' id='FormParametros' onsubmit='checkSubmit();'>");
                out.print("<table class='table2'>");
                out.print("<tr>");
                out.print("<td><b class='negro'>Seleccionar inicio de toma de los datos</b></td>");
                out.print("<td>");
                out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' >");
                out.print("<option value='0' >Seleccionar toma</option>");
                for (int i = 1; i <= 18; i++) {
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
                        if (i == 8 || i == 17) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 8) {
                                    out.print("<option value='0' style='color:red' >Coordinadora 1° Toma bloqueada</option>");
                                } else {
                                    out.print("<option value='0' style='color:red' >Coordinadora 2° Toma bloqueada</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 9 && i <= 16) {
                                out.print("<option value='0' style='color:red' >Hora " + (i - 1) + " bloqueada</option>");
                            } else {
                                out.print("<option value='0' style='color:red' >Hora " + ((i == 18) ? (i - 2) : i) + " bloqueada</option>");
                            }
                        }
                        cont_estaciones = 0;
                    } else {
                        if (i == 8 || i == 17) {
                            if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                if (i == 8) {
                                    out.print("<option value='" + i + "' >Coordinadora 1° Toma</option>");
                                } else {
                                    out.print("<option value='" + i + "' >Coordinadora 2° Toma</option>");
                                }
                            }
                        } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Encargada-operaria") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad") || rol.equals("Administrador")) {
                            if (i >= 9 && i <= 16) {
                                out.print("<option value='" + i + "' >Hora " + (i - 1) + "</option>");
                            } else {
                                out.print("<option value='" + i + "' >Hora " + ((i == 18) ? (i - 2) : i) + "</option>");
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
//                out.print("<tr>");
//                out.print("<td colspan='2'><hr /></td>");
//                out.print("</tr>");
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
                                            out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametros[2] + "'id='Vlr_parametro_" + i + "'  onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');"
                                                    + "val1.add(Validate.Presence);"
                                                    + "val1.add(Validate.Decimal);"
                                                    + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + i + "'} );"
                                                    + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + i + "'} );"
                                                    + "</script>"
                                                    + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                                            break;
                                        }
                                    } else if (rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad")) {
                                        mayor = Double.parseDouble(obj_ficha[parametro].toString()) + Double.parseDouble(obj_ficha[parametro_max].toString());
                                        menor = Double.parseDouble(obj_ficha[parametro].toString()) - Double.parseDouble(obj_ficha[parametro_min].toString());
                                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                                        out.print("<input type='hidden' name='Txt_minimo_" + obj_parametros[2] + "' id='Txt_minimo_" + i + "' value='" + menor + "' />");
                                        out.print("<input type='hidden' name='Txt_maximo_" + obj_parametros[2] + "' id='Txt_maximo_" + i + "' value='" + mayor + "' />");
                                        out.print("<td><input type='text' style='width:50px' name='Vlr_parametro_" + obj_parametros[2] + "'id='Vlr_parametro_" + i + "'  onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>&nbsp&nbsp&nbsp&nbsp<b>" + obj_ficha[parametro] + " + " + obj_ficha[parametro_max] + " - " + obj_ficha[parametro_min] + "</b>"
                                                + "<script type='text/javascript'>var val1 = new LiveValidation('Vlr_parametro_" + i + "');"
                                                + "val1.add(Validate.Presence);"
                                                + "val1.add(Validate.Decimal);"
                                                + "val1.add(Validate.Parametros_minimos, { match: 'Txt_minimo_" + i + "'} );"
                                                + "val1.add(Validate.Parametros_maximos, { match: 'Txt_maximo_" + i + "'} );"
                                                + "</script>"
                                                + "<script type='text/javascript'>function AutoFocus_" + obj_parametros[2] + "(prox){document.getElementById('Vlr_parametro_" + (i + 1) + "').focus()}</script></td>");
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (obj_parametros[7].equals("Caracter")) {
                        out.print("<td>" + obj_parametros[3].toString().toUpperCase() + "</td>");
                        out.print("<td><input type='text' name='Vlr_parametro_" + obj_parametros[2] + "' id='Vlr_parametro_" + i + "' placeholder='Valor' onkeyup='Replace(this)'  onchange='javascript:this.value=this.value.trim();AutoFocus_" + obj_parametros[2] + "();'/>"
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
                // <editor-fold defaultstate="collapsed" desc="CONSULTA PARAMETROS">
                lst_parametros = jpacrfm.Parametros_tomas_registro_frecuencia_hora(id_registro);
                if (lst_parametros == null) {
                    out.print("<center>");
                    out.print("<br /><span class='fas fa-exclamation-circle fa-size_big color_span_naranja' title='No hay datos en la consulta'></span><br />");
                    out.print("<br /><b class='naranja'>No hay datos de parámetros de frecuencia por hora</b>");
                    out.print("</center>");
                } else {
                    out.print("<div style='width:100%;overflow-x:scroll;'>");
                    out.print("<table class='table' style='width:100%;'>");
                    // <editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    for (int i = 1; i <= 18; i++) {
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
                            if (i == 8 || i == 17) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    out.print("<th><a href='#' onclick='DesbloquearEstacionMedia(" + i + "," + id_registro + ")' title='Estación Bloqueada' style='color:#fff;'>COORD.</a></th>");
                                } else {
                                    out.print("<th>COORD.</th>");
                                }
                            } else if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador") || rol.equals("Coordinadora-Calidad") || rol.equals("Inspectora-Calidad")) {
                                if (i >= 9 && i <= 16) {
                                    out.print("<th><a href='#' onclick='DesbloquearEstacionMedia(" + i + "," + id_registro + ")' title='Estación Bloqueada' style='color:#fff;'>" + (i - 1) + "</a></th>");
                                } else {
                                    out.print("<th><a href='#' onclick='DesbloquearEstacionMedia(" + i + "," + id_registro + ")' title='Estación Bloqueada' style='color:#fff;'>" + ((i == 18) ? (i - 2) : i) + "</a></th>");
                                }
                            } else if (i >= 9 && i <= 16) {
                                out.print("<th>" + (i - 1) + "</th>");
                            } else {
                                out.print("<th>" + ((i == 18) ? (i - 2) : i) + "</th>");
                            }
                            cont_estaciones = 0;
                        } else {
                            if (i == 8 || i == 17) {
                                if (rol.equals("Coordinadora-Produccion") || rol.equals("Administrador")) {
                                    out.print("<th><a href='#' onclick='BloquearEstacionMedia(" + i + "," + id_registro + ")' title='Estación Desbloqueada' style='color:#fff;'>COORD.</a></th>");
                                } else {
                                    out.print("<th>COORD.</th>");
                                }
                            } else if (i >= 9 && i <= 16) {
                                out.print("<th><a href='#' onclick='BloquearEstacionMedia(" + i + "," + id_registro + ")' title='Estación Desbloqueada' style='color:#fff;'>" + (i - 1) + "</a></th>");
                            } else {
                                out.print("<th><a href='#' onclick='BloquearEstacionMedia(" + i + "," + id_registro + ")' title='Estación Desbloqueada' style='color:#fff;'>" + ((i == 18) ? (i - 2) : i) + "</a></th>");
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
                    // </editor-fold>
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3] + "</td>");
                        //INICIO CICLO MASIVO
                        int var_inicial = 5;
                        for (int j = 0; j < 18; j++) {
                            // <editor-fold defaultstate="collapsed" desc="CUERPO">
                            if (j == 7 || j == 16) {
                                if (obj_parametros[(var_inicial + j)] == null) {
                                    out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                                } else if (obj_parametros[(var_inicial + j)].toString().equals("null")) {
                                    out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='rojo'>Pendiente</b></td>");
                                } else {
                                    String[] arg_responsables = obj_parametros[(var_inicial + j) + 18].toString().split("/");
                                    if (arg_responsables[1].equals("Administrador")) {
                                        out.print("<td align='center' style='background-color:#ced6e0;border:none'><b>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                    } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                        out.print("<td align='center' style='background-color:#ced6e0;border:none'>" + obj_parametros[(var_inicial + j)] + "</td>");
                                    } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                        out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                    } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad")) {
                                        out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='calidad'>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                    }
                                    if (obj_parametros[41].toString().equals("Numero")) {
                                        sumatoria = sumatoria + Double.parseDouble(obj_parametros[(var_inicial + j)].toString());
                                        contador_numero++;
                                        contador_estado--;
                                    } else if (obj_parametros[41].toString().equals("Estado")) {
                                        if (obj_parametros[(var_inicial + j)].equals("Cumple") || obj_parametros[(var_inicial + j)].equals("CUMPLE") || obj_parametros[(var_inicial + j)].equals("N/A")) {
                                            if (obj_parametros[(var_inicial + j)].equals("N/A")) {
                                                contador_na++;
                                            }
                                        } else {
                                            contador_estado++;
                                        }
                                        contador_numero--;
                                    } else if (obj_parametros[41].toString().equals("Caracter")) {
                                        if (obj_parametros[(var_inicial + j)].toString().trim().equals("N/A") || obj_parametros[(var_inicial + j)].toString().trim().equals("n/a") || obj_parametros[(var_inicial + j)].toString().trim().equals("N/a") || obj_parametros[(var_inicial + j)].toString().trim().equals("n/A")) {
                                            contador_na++;
                                        } else {
                                            contador_caracter++;
                                            sumatoria = sumatoria + Double.parseDouble(obj_parametros[(var_inicial + j)].toString());
                                        }
                                    }
                                }
                            } else if (obj_parametros[(var_inicial + j)] == null) {
                                out.print("<td align='center'></td>");
                            } else if (obj_parametros[(var_inicial + j)].toString().equals("null")) {
                                out.print("<td align='center'><b class='rojo'>Pendiente</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[(var_inicial + j) + 18].toString().split("/");
                                if (arg_responsables[1].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[(var_inicial + j)] + "</td>");
                                } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                } else if (arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                }
                                if (obj_parametros[41].toString().equals("Numero")) {
                                    sumatoria = sumatoria + Double.parseDouble(obj_parametros[(var_inicial + j)].toString());
                                    contador_numero++;
                                    contador_estado--;
                                } else if (obj_parametros[41].toString().equals("Estado")) {
                                    if (obj_parametros[(var_inicial + j)].equals("Cumple") || obj_parametros[(var_inicial + j)].equals("CUMPLE") || obj_parametros[(var_inicial + j)].equals("N/A")) {
                                        if (obj_parametros[(var_inicial + j)].equals("N/A")) {
                                            contador_na++;
                                        }
                                    } else {
                                        contador_estado++;
                                    }
                                    contador_numero--;
                                } else if (obj_parametros[41].toString().equals("Caracter")) {
                                    if (obj_parametros[(var_inicial + j)].toString().trim().equals("N/A") || obj_parametros[(var_inicial + j)].toString().trim().equals("n/a") || obj_parametros[(var_inicial + j)].toString().trim().equals("N/a") || obj_parametros[(var_inicial + j)].toString().trim().equals("n/A")) {
                                        contador_na++;
                                    } else {
                                        contador_caracter++;
                                        sumatoria = sumatoria + Double.parseDouble(obj_parametros[(var_inicial + j)].toString());
                                    }
                                }
                            }
                        }
                        //FIN INICIO CICLO MASIVO
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="ESTADISTICA">
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
                                    if (obj_parametros[42].toString().equals("Todos") || obj_parametros[42].toString().equals("Calidad")) {
                                        out.print("<td align='center'>"
                                                + "<form action='Registro?opc=43' method='post' onsubmit='checkSubmit();' name='FormModificarParametro" + i + "' id='FormModificarParametro" + i + "'>"
                                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                                + "<input type='hidden' name='Modifica' value='" + obj_parametros[2] + "' />"
                                                //+ "<a href='JAVASCRIPT:FormModificarParametro" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                                + "<span class='fa fa-pen fa-size_small' onclick='FormModificarParametro" + i + ".submit()' title='Actualizar Registro'></span>"
                                                + "</form>"
                                                + "</td>");
                                    }
                                } else if (obj_parametros[42].toString().equals("Todos")) {
                                    out.print("<td align='center'>"
                                            + "<form action='Registro?opc=43' method='post' onsubmit='checkSubmit();' name='FormModificarParametro" + i + "' id='FormModificarParametro" + i + "'>"
                                            + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                            + "<input type='hidden' name='Modifica' value='" + obj_parametros[2] + "' />"
                                            //+ "<a href='JAVASCRIPT:FormModificarParametro" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Update.png' width='20px' height='20px' alt='edit' title='Actualizar Registro' /></a>"
                                            + "<span class='fa fa-pen fa-size_small' onclick='FormModificarParametro" + i + ".submit()' title='Actualizar Registro'></span>"
                                            + "</form>"
                                            + "</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            //+ "<img src='Interfaz/Contenido/Iconos/Warning.png' width='20px' height='20px' alt='edit' title='Sin permisos' />"
                                            + "<span class='fa fa-pen fa-size_small color_span' title='Sin permisos'></span>"
                                            + "</td>");
                                }
                            }
                        }
                    }
                    lst_responsables = jpacrfm.Responsables_tomas_registro_frecuencia_hora(id_registro);
                    Object[] obj_responsables = (Object[]) lst_responsables.get(0);
                    out.print("<tr>");
                    out.print("<td align='center'><b>Hora de toma de datos</b></th>");
                    for (int i = 0; i < 18; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + arg_responsables[0] + "</b></td>");
                            } else if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + arg_responsables[0] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + arg_responsables[0] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + arg_responsables[0] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + arg_responsables[0] + "</b></td>");
                            }
                        }
                    }
                    out.print("<th rowspan=2 colspan=2></th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>Responsables</b></td>");
                    for (int i = 0; i < 18; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color:#ced6e0;border:none'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + arg_responsables[2] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad")) {
                                out.print("<td align='center'><b class='calidad'>" + arg_responsables[2] + "</b></td>");
                            }
                        }
                    }
                    out.print("</tr>");
                    // </editor-fold>
                    out.print("</table>");
                    out.print("</div>");
                }
                // </editor-fold>
                out.print("<br />");
                out.print("<br />");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
            } // </editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu_registro.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
