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
import Controladores.RegistroFrecuenciaMediaHoraJpaController;
import Controladores.RegistroImplementoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroLoteCodigoJpaController;
import Controladores.RegistroObservacionJpaController;
import Controladores.RegistroPruebaCalidadJpaController;
import Controladores.SerialJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_visor_registro extends TagSupport {
    
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            try {
                
            } catch (Exception e) {
            }
//            String[] rol_usuario = pageContext.getAttribute("Rol/Nombres").toString().split("/");
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            //JPAS
            RegistroJpaController jpacrgt = new RegistroJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            ParadaMaquinaJpaController jpacpmq = new ParadaMaquinaJpaController();
            RegistroLoteCodigoJpaController jpacrlc = new RegistroLoteCodigoJpaController();
            RegistroPruebaCalidadJpaController jpacrpc = new RegistroPruebaCalidadJpaController();
            RegistroImplementoJpaController jpacrip = new RegistroImplementoJpaController();
            RegistroEntradaMaterialJpaController jpacrem = new RegistroEntradaMaterialJpaController();
            SerialJpaController jpacsra = new SerialJpaController();
            RegistroObservacionJpaController jpacros = new RegistroObservacionJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            RegistroEspesorBocaJpaController jpacreb = new RegistroEspesorBocaJpaController();
            RegistroEspesorColaJpaController jpacrec = new RegistroEspesorColaJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            PncJpaController jpacpnc = new PncJpaController();
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            RegistroFrecuenciaMediaHoraJpaController jpacrfm = new RegistroFrecuenciaMediaHoraJpaController();
            //FIN JPAS
            //VARIABLES
            int id_registro = 0;
            int id_entrada_material = 0;
            int total = 0;
            int opcion = 0;
            int suma_total = 0;
            int contador = 0;
            int contador_numero = 0;
            int contador_estado = 0;
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
            List lst_espesores_bocas = null;
            List lst_espesores_colas = null;
            List lst_entradas_material = null;
            List lst_entrada_material = null;
            List lst_ficha = null;
            List lst_categoria = null;
            List lst_pnc = null;
            List lst_pnc_registro = null;
            int visual = 0;
            //FIN VARIABLES
            if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_visor")) {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_ficha = jpacftn.Traer_ficha_registro(id_registro);
                Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                String fecha[] = obj_registro[2].toString().split("-");
                String fecha_version = fecha[0] + "." + fecha[1] + fecha[2];
                double fecha_version_decimal = Double.parseDouble(fecha_version);
                // <editor-fold defaultstate="collapsed" desc="CABECERA">
                // <editor-fold defaultstate="collapsed" desc="TITULO">
                out.print("<table class='table' style='width:1530px'>");
                if (fecha_version_decimal >= 2016.0101) {
                    out.print("<tr>");
                    out.print("<td colspan='20' style='background-color:#CCC;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                }
                out.print("<tr>");
                out.print("<td align='center' colspan='2'>"
                        + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                        + "</td>");
                if (fecha_version_decimal >= 2016.0101) {
                    out.print("<td align='center' colspan='2'><h2 class='negro'>REGISTRO <hr />CONTROL PROCESO</h2></td>");
                } else {
                    out.print("<td align='center' colspan='2'><h2 class='negro'>MANUAL DE REGISTROS <hr />CONTROL PROCESO</h2></td>");
                }
// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="VERSIONES">
                if (obj_registro[65].toString().equals("R-PRF-013")) {
                    if (fecha_version_decimal >= 2026.0106) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>15</b></h2></td>");
                    }else if (fecha_version_decimal >= 2024.0925) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>14</b></h2></td>");
                    } else if (fecha_version_decimal >= 2022.0908) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>13</b></h2></td>");
                    } else if (fecha_version_decimal >= 2020.0623 && fecha_version_decimal <= 2022.0907) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>12</b></h2></td>");
                    } else if (fecha_version_decimal >= 2018.0521 && fecha_version_decimal <= 2020.0622) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>11</b></h2></td>");
                    } else if (fecha_version_decimal >= 2015.0526 && fecha_version_decimal <= 2018.0228) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>10</b></h2></td>");
                    } else if (fecha_version_decimal >= 2015.0226 && fecha_version_decimal <= 2015.0525) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>9</b></h2></td>");
                    } else if (fecha_version_decimal >= 2014.1018 && fecha_version_decimal <= 2015.0225) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>8</b></h2></td>");
                    } else if (fecha_version_decimal >= 2014.0830 && fecha_version_decimal <= 2014.1017) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>7</b></h2></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>6</b></h2></td>");
                    }
                } else if (obj_registro[65].toString().equals("R-PRF-011")) {
                    if (fecha_version_decimal >= 2024.0925) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>14</b></h2></td>");
                    } else if (fecha_version_decimal >= 2022.0908) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>13</b></h2></td>");
                    } else if (fecha_version_decimal >= 2020.0623 && fecha_version_decimal <= 2022.0907) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>12</b></h2></td>");
                    } else if (fecha_version_decimal >= 2018.0521 && fecha_version_decimal <= 2020.0622) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>11</b></h2></td>");
                    } else if (fecha_version_decimal >= 2016.0401 && fecha_version_decimal <= 2018.0228) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>10</b></h2></td>");
                    } else if (fecha_version_decimal >= 2015.0526 && fecha_version_decimal <= 2016.0331) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>9</b></h2></td>");
                    } else if (fecha_version_decimal >= 2015.0226 && fecha_version_decimal <= 2015.0525) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>8</b></h2></td>");
                    } else if (fecha_version_decimal >= 2014.1018 && fecha_version_decimal <= 2015.0225) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>7</b></h2></td>");
                    } else if (fecha_version_decimal >= 2014.0830 && fecha_version_decimal <= 2014.1017) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>6</b></h2></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>5</b></h2></td>");
                    }
                } else if (obj_registro[65].toString().equals("R-PRF-019")) {
                    if (fecha_version_decimal >= 2022.0908) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>13</b></h2></td>");
                    } else if (fecha_version_decimal >= 2020.0623 && fecha_version_decimal <= 2022.0907) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>12</b></h2></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>11</b></h2></td>");
                    }
                }
// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="LOTES">
                out.print("</tr>");
                out.print("<tr>");
                //<editor-fold defaultstate="collapsed" desc="ORDEN, CLIENTE FICHA PRODUCTO">
                out.print("<td align='center'><b>ORDEN DE PRODUCCION : </b>" + obj_registro[18] + "</td>");
                out.print("<td align='center'><b>CLIENTE : </b>" + obj_registro[19] + "</td>");
                if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("<td><b>FICHA TECNICA INI: </b>" + obj_registro[24] + " <b> VERSION : </b>" + obj_registro[25] + "<br />");
                    out.print("<b class='negro'>FICHA TECNICA EVA : </b>" + obj_registro[99].toString().split(" / ")[0].toUpperCase().replace("V", " <b> VERSION : </b>") + "");
                    if (!obj_registro[111].toString().equals("N/A")) {
                        out.print("<br /><b class='naranja'>FICHA TECNICA COMP: </b>");
                        if (obj_registro[111].toString().contains("][")) {
                            String var_temp = obj_registro[111].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                            String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                            for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                out.print("<br />" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[0].toUpperCase().replace("V", " <b> VERSION </b> ") + "");
                            }
                        } else {
                            out.print(obj_registro[111].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[0].toUpperCase().replace("V", " <b> VERSION </b> ") + "");
                        }
                    }
                    out.print("</td>");
                } else {
                    out.print("<td align='center'><b>FICHA TECNICA : </b>" + obj_registro[24] + " <b> VERSION : </b>" + obj_registro[25] + "</td>");
                }
                out.print("<td align='center'><b>LINEA : </b>" + obj_registro[6] + "</td>");
                if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("<td >");
                    out.print("<b>PRODUCTO INICIAL: </b>" + obj_registro[21] + " / " + obj_registro[22] + "<br />");
                    out.print("<b class='negro'>PRODUCTO TERMINADO : </b>" + obj_registro[99].toString().split(" ___ ")[0].split(" / ")[1].toUpperCase() + " / " + obj_registro[99].toString().split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                    if (!obj_registro[111].toString().equals("N/A")) {
                        out.print("<br /><b class='naranja'>ENSAMBLE(S) : </b>");
                        if (obj_registro[111].toString().contains("][")) {
                            String var_temp = obj_registro[111].toString().replace("][", " SEPARADOR ").replace("]", "").replace("[", "").replace("|", "");
                            String[] arg_prod_complementarios = var_temp.split(" SEPARADOR ");
                            for (int i = 0; i < arg_prod_complementarios.length; i++) {
                                out.print("<br />" + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[1].toUpperCase() + " / " + arg_prod_complementarios[i].split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                            }
                        } else {
                            out.print(obj_registro[111].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[1].toUpperCase() + " / " + obj_registro[111].toString().replace("[", "").replace("]", "").split(" ___ ")[0].split(" / ")[2].toUpperCase() + "");
                        }
                    }
                    out.print("</td>");
                } else {
                    out.print("<td align='center'><b>PRODUCTO : </b>" + obj_registro[21] + "<b>/</b>" + obj_registro[22] + "</td>");
                }
                out.print("<td align='center'><b>LOTE PRODUCTO:</b>" + obj_registro[3] + " <b> VOLUMEN : </b>" + obj_registro[23] + "</td>");
                out.print("</tr>");
//</editor-fold>
                out.print("<tr>");
                //<editor-fold defaultstate="collapsed" desc="FECHAS Y OTROS LOTES">
                if (obj_registro[65].toString().equals("R-PRF-013")) {
                    out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + "</td>");
                    out.print("<td align='center'><b>TURNO : </b>" + obj_registro[4] + "</td>");
                } else if (obj_registro[65].toString().equals("R-PRF-011")) {
                    if (fecha_version_decimal >= 2014.0830) {
                        out.print("<td align='center'><b>LOTE COLA : </b>" + obj_registro[66] + "</td>");
                        out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + "&nbsp&nbsp&nbsp&nbsp<b>TURNO : </b>" + obj_registro[4] + "</td>");
                    } else {
                        out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + "</td>");
                        out.print("<td align='center'><b>TURNO : </b>" + obj_registro[4] + "</td>");
                    }
                } else if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("<td align='center'><b>LOTE BOCA : </b>" + obj_registro[103] + "<br /><b>LOTE COLA : </b>" + obj_registro[66] + "</td>");
                    out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + "<br /><b>TURNO : </b>" + obj_registro[4] + "</td>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ENSAMBLES 1 Y 2">
                if (fecha_version_decimal >= 2015.0526) {
                    out.print("<td align='center'><b>ENSAMBLE : </b>" + obj_registro[13] + "<br /><b>ENSAMBLE 2°: </b>" + obj_registro[75] + "</td>");
                    out.print("<td align='center'><b>LOTE ENSAMBLE: </b>" + obj_registro[14] + "<br /><b>LOTE ENSAMBLE 2°: </b>" + obj_registro[76] + "</td>");
                } else {
                    out.print("<td align='center'><b>ENSAMBLE : </b>" + obj_registro[13] + "</td>");
                    out.print("<td align='center'><b>LOTE ENSAMBLE: </b>" + obj_registro[14] + "</td>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TINTA">
                if (obj_registro[65].toString().equals("R-PRF-013") || obj_registro[65].toString().equals("R-PRF-011")) {
                    if (fecha_version_decimal >= 2014.0830) {
                        out.print("<td align='center'><b>COLOR / LOTE DE TINTA : </b>" + obj_registro[67] + "/" + obj_registro[15] + "</td>");
                    } else {
                        out.print("<td align='center'><b>LOTE DE TINTA : </b>" + obj_registro[15] + "</td>");
                    }
                } else if (obj_registro[65].toString().equals("R-PRF-019")) {
                    if (fecha_version_decimal >= 2020.0218) {
                        out.print("<td align='center'><b>COLOR / LOTE DE FOIL : </b>" + obj_registro[67] + "/" + obj_registro[15] + "</td>");
                    } else {
                        out.print("<td align='center'><b>COLOR / LOTE DE TINTA : </b>" + obj_registro[67] + "/" + obj_registro[15] + "</td>");
                    }
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="RESPONSABLES">
                out.print("<td align='center'rowspan='2' ><b>RESPONSABLES : </b><br />");
                String[] reportantes = null;
                reportantes = obj_registro[17].toString().split(",");
                for (int j = 0; j < reportantes.length; j++) {
                    String[] reportantes_rol = null;
                    reportantes_rol = reportantes[j].split("/");
                    for (int k = 0; k < 1; k++) {
                        if (reportantes_rol[2].toString().equals("1")) {
                            if (reportantes_rol[0].equals("Administrador")) {
                                out.print("<b>" + reportantes_rol[1] + "</b><br />");
                            } else if (reportantes_rol[0].equals("Encargada-operaria")) {
                                out.print("" + reportantes_rol[1] + "<br />");
                            } else if (reportantes_rol[0].equals("Coordinadora-Produccion")) {
                                out.print("<b class='coordinadora'>" + reportantes_rol[1] + "</b><br />");
                            } else if (reportantes_rol[0].equals("Coordinadora-Calidad") || reportantes_rol[0].equals("Inspectora-Calidad")) {
                                out.print("<b class='calidad'>" + reportantes_rol[1] + "</b><br />");
                            } 
                        }
                    }
                }
                out.print("</td>");
//</editor-fold>
                out.print("</tr>");
                out.print("<tr>");
                //<editor-fold defaultstate="collapsed" desc="MANGAS Y DUCTOS">
                if (obj_registro[65].toString().equals("R-PRF-011")) {
                    if (fecha_version_decimal >= 2016.0401) {
                        out.print("<td align='center' colspan='2'><b>MANGA C : </b>" + obj_registro[7] + "<b class='negro'> C :</b>" + obj_registro[78] + "<b> P :</b>" + obj_registro[8] + "</td>");
                        out.print("<td align='center'><b>DUCTO IZQUIERDO C : </b>" + obj_registro[11] + "<b> P :</b>" + obj_registro[12] + "</td>");
                        out.print("<td align='center'><b>DUCTO CENTRAL C : </b>" + obj_registro[79] + "<b> P :</b>" + obj_registro[80] + "</td>");
                        out.print("<td align='center'><b>DUCTO DERECHO C : </b>" + obj_registro[9] + "<b> P :</b>" + obj_registro[10] + "</td>");
                    } else {
                        out.print("<td align='center' colspan='2'><b>MANGA C : </b>" + obj_registro[7] + "<b> P :</b>" + obj_registro[8] + "</td>");
                        out.print("<td align='center'><b>DUCTO IZQUIERDO C : </b>" + obj_registro[11] + "<b> P :</b>" + obj_registro[12] + "</td>");
                        out.print("<td align='center' colspan='2'><b>DUCTO DERECHO C : </b>" + obj_registro[9] + "<b> P :</b>" + obj_registro[10] + "</td>");
                    }
                } else if (obj_registro[65].toString().equals("R-PRF-013")) {
                    out.print("<td align='center' colspan='2'><b>MANGA C : </b>" + obj_registro[7] + "<b> P :</b>" + obj_registro[8] + "</td>");
                    out.print("<td align='center'><b>DUCTO IZQUIERDO C : </b>" + obj_registro[11] + "<b> P :</b>" + obj_registro[12] + "</td>");
                    out.print("<td align='center' colspan='2'><b>DUCTO DERECHO C : </b>" + obj_registro[9] + "<b> P :</b>" + obj_registro[10] + "</td>");
                } else if (obj_registro[65].toString().equals("R-PRF-019")) {
                    out.print("<td align='center' colspan='2'><b>MANGA C : </b>" + obj_registro[7] + "<b class='negro'> C :</b>" + obj_registro[78] + "<b> P :</b>" + obj_registro[8] + "");
                    out.print("<br /><b>DUCTO C : </b>" + obj_registro[9] + "<b class='negro'> C :</b>" + obj_registro[104] + "<b> P :</b>" + obj_registro[10] + "</td>");
                    out.print("<td align='center'><b>ENSAMBLE 3°: </b>" + obj_registro[107] + "<br /><b>ENSAMBLE 4°: </b>" + obj_registro[109] + "</td>");
                    out.print("<td align='center'><b>LOTE ENSAMBLE 3°: </b>" + obj_registro[108] + "<br /><b>LOTE ENSAMBLE 4°: </b>" + obj_registro[110] + "</td>");
                    out.print("<td align='center'><b>TUBO DE REFUERZO: </b>" + obj_registro[105] + "<br /><b>CICLO DE ESTERILIZACION :</b>" + obj_registro[106] + "</td>");
                }
//</editor-fold>
                out.print("</tr>");
                out.print("</table>");
                // </editor-fold>
                // </editor-fold>
                out.print("<div style='width:1550px;'>");
                // <editor-fold defaultstate="collapsed" desc="PARAMETROS DE FRECIENCIA POR HORA">
                //PARAMETROS DE FRECIENCIA POR HORA
                out.print("<div style='float:left'>");
                lst_parametros = jpacrfh.Parametros_tomas_registro_frecuencia_hora(id_registro);
                if (lst_parametros == null) {
                    out.print("<table class='table' style='width:850px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Parámetros de frecuencia por hora</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:330px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:850px;height:330px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<table class='table' style='width:850px'>");
                    out.print("<tr>");
                    out.print("<td colspan='12'><b>Parámetros de frecuencia por hora</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    out.print("<th>1</th>");
                    out.print("<th>2</th>");
                    out.print("<th>3</th>");
                    out.print("<th>4</th>");
                    out.print("<th>COORD.</th>");
                    out.print("<th>5</th>");
                    out.print("<th>6</th>");
                    out.print("<th>7</th>");
                    out.print("<th>8</th>");
                    out.print("<th>COORD.</th>");
                    out.print("<th>PROM.</th>");
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
                            } else if (arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Documental") ){
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
                            out.print("<td align='center' style='background-color : #dcdcdc;'></td>");
                        } else if (obj_parametros[9].toString().equals("null")) {
                            out.print("<td align='center' style='background-color : #dcdcdc;'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[19].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b>" + obj_parametros[9] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'>" + obj_parametros[9] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b class='coordinadora'>" + obj_parametros[9] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b class='calidad'>" + obj_parametros[9] + "</b></td>");
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
                            out.print("<td align='center' style='background-color : #dcdcdc;'></td>");
                        } else if (obj_parametros[14].toString().equals("null")) {
                            out.print("<td align='center' style='background-color : #dcdcdc;'><b class='rojo'>Pendiente</b></td>");
                        } else {
                            String[] arg_responsables = obj_parametros[24].toString().split("/");
                            if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b>" + obj_parametros[14] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'>" + obj_parametros[14] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b class='coordinadora'>" + obj_parametros[14] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b class='calidad'>" + obj_parametros[14] + "</b></td>");
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
                                    promedio_frecuencia_hora = "<b>Cumple</b>";
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
                    }
                    lst_responsables = jpacrfh.Responsables_tomas_registro_frecuencia_hora(id_registro);
                    Object[] obj_responsables = (Object[]) lst_responsables.get(0);
                    out.print("<tr>");
                    out.print("<th>Hora de toma de datos</th>");
                    for (int i = 0; i < 10; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b class='coordinadora'>" + arg_responsables[0] + "</b></td>");
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
                    out.print("<th>Responsables</th>");
                    for (int i = 0; i < 10; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 5 || (i + 1) == 10) {
                                out.print("<td align='center' style='background-color : #dcdcdc;'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Administrador")) {
                                out.print("<td align='center'><b>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                out.print("<td align='center'>" + arg_responsables[2] + "</td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                out.print("<td align='center'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
                            } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                out.print("<td align='center'><b class='calidad'>" + arg_responsables[2] + "</b></td>");
                            }
                        }
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="VERIFICACIÓN DE LOTE Y CODIGO">

                //VERIFICACIÓN DE LOTE Y CODIGO
                lst_parametros = jpacrlc.Parametros_tomas_registro_lote_codigo(id_registro);
                if (lst_parametros == null) {
                    out.print("<table class='table' style='width:850px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Verificación de lote y código</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:100px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:850px;height:100px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<table class='table' style='width:850px'>");
                    out.print("<tr>");
                    out.print("<td colspan='42'><b>Verificación de lote y código</b></td>");
                    out.print("</tr>");
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
                //FIN VERIFICACIÓN DE LOTE Y CODIGO
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="PRUEBAS CALIDAD">

                //PRUEBAS CALIDAD
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                lst_parametros = jpacrpc.Parametros_tomas_registro_prueba_calidad(id_registro);
                if (lst_parametros == null) {
                    out.print("<table class='table' style='width:850px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Pruebas calidad</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:850px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de parámetros de pruebas de calidad</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:850px'>");
                    out.print("<tr>");
                    out.print("<td colspan='9'><b>Pruebas calidad</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    out.print("<th>1</th>");
                    out.print("<th>2</th>");
                    out.print("<th>3</th>");
                    out.print("<th>4</th>");
                    out.print("<th>5</th>");
                    out.print("<th>6</th>");
                    out.print("<th>7</th>");
                    out.print("<th>8</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3] + "</td>");
                        if ((Integer) obj_parametros[4] == 1) {
                            if (obj_parametros[5] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                            if (obj_parametros[8] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[8].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[16].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[8] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                                }
                            }
                            if (obj_parametros[9] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[9].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[9] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[17].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[9] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[9] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[9] + "</b></td>");
                                }
                            }
                            if (obj_parametros[10] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[10].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[10] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[18].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[10] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[10] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[10] + "</b></td>");
                                }
                            }
                            if (obj_parametros[11] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[11].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[11] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[19].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[11] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[11] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[11] + "</b></td>");
                                }
                            }
                            if (obj_parametros[12] == null) {
                                out.print("<td align='center'><b></b></td>");
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
                        } else if ((Integer) obj_parametros[4] == 2) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' ='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                            if (obj_parametros[8] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[8].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[16].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[8] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 4) {
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
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
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
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='4' align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 8) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='8' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='8' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='8'  align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='8'  align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='8'  align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='8' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //FIN PRUEBAS CALIDAD
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="DESCRIPCION DE PNC">
                //DESCRIPCION DE PNC
                lst_categoria = jpacctg.Categorias();
                for (int j = 0; j < lst_categoria.size(); j++) {
                    Object[] obj_categorias = (Object[]) lst_categoria.get(j);
                    lst_pnc_registro = jpacpnc.Pnc_registro(id_registro, (Integer) obj_categorias[0]);
                    if (lst_pnc_registro == null) {
                        contador++;
                    }
                }
                if (contador == lst_categoria.size()) {
                    out.print("<table class='table' style='width:850px'>");
                    out.print("<tr>");
                    out.print("<td ><b>Descripcion de producto no conforme (PNC</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:320px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:843px;height:320px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de descripción de producto no conforme</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:850px'>");
                    out.print("<tr>");
                    out.print("<td colspan='10'><b>Descripcion de producto no conforme (PNC)</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Descripción</th>");
                    for (int i = 0; i < 8; i++) {
                        out.print("<th>" + (i + 1) + "</th>");
                    }
                    out.print("<th>Tolal</th>");
                    out.print("</tr>");
                    for (int j = 0; j < lst_categoria.size(); j++) {
                        Object[] obj_categorias = (Object[]) lst_categoria.get(j);
                        lst_pnc_registro = jpacpnc.Pnc_registro(id_registro, (Integer) obj_categorias[0]);
                        if (lst_pnc_registro == null) {
                        } else {
                            out.print("<tr>");
                            out.print("<th colspan='10'>" + obj_categorias[1] + "</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_pnc_registro.size(); i++) {
                                Object[] obj_pnc_registro = (Object[]) lst_pnc_registro.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_pnc_registro[2] + "</td>");
                                if (obj_pnc_registro[5] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[5] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[6] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[6] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[7] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[7] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[8] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[8] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[9] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[9] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[10] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[10] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[11] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[11] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[12] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[12] + ""
                                            + "</td>");
                                }
                                suma_total = suma_total + ((Integer) obj_pnc_registro[5] + (Integer) obj_pnc_registro[6] + (Integer) obj_pnc_registro[7] + (Integer) obj_pnc_registro[8] + (Integer) obj_pnc_registro[9] + (Integer) obj_pnc_registro[10] + (Integer) obj_pnc_registro[11] + (Integer) obj_pnc_registro[12]);
                                out.print("<th align='center'>" + ((Integer) obj_pnc_registro[5] + (Integer) obj_pnc_registro[6] + (Integer) obj_pnc_registro[7] + (Integer) obj_pnc_registro[8] + (Integer) obj_pnc_registro[9] + (Integer) obj_pnc_registro[10] + (Integer) obj_pnc_registro[11] + (Integer) obj_pnc_registro[12]) + "</th>");
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
                //FIN DESCRIPCION DE PNC
                out.print("</div>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="SOLDADURA EN BOCA">
                //SOLDADURA EN BOCA
                out.print("<div style='float:left; width:680px'>");
//                double inicio_boca = Double.parseDouble(obj_ficha[9].toString()) - Double.parseDouble(obj_ficha[11].toString());
//                double fin_boca = Double.parseDouble(obj_ficha[9].toString()) + Double.parseDouble(obj_ficha[10].toString());
                lst_espesores_bocas = jpacreb.Consultar_registro_espesores_bocas(id_registro);
                if (lst_espesores_bocas == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Espesor soldadura en bocas</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de espesor soldadura en bocas</b><br /><br />");
                } else {
                    double inicio_boca = 0;
                    double fin_boca = 0;
                    if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[44].toString()) > 0) {
                        inicio_boca = Double.parseDouble(obj_ficha[44].toString()) - Double.parseDouble(obj_ficha[46].toString());
                        fin_boca = Double.parseDouble(obj_ficha[44].toString()) + Double.parseDouble(obj_ficha[45].toString());
                    } else {
                        inicio_boca = Double.parseDouble(obj_ficha[9].toString()) - Double.parseDouble(obj_ficha[11].toString());
                        fin_boca = Double.parseDouble(obj_ficha[9].toString()) + Double.parseDouble(obj_ficha[10].toString());
                    }
                    contador = 0;
                    for (double i = inicio_boca; i < fin_boca + 0.01; i += 0.01) {
                        long mult = (long) Math.pow(10, 2);
                        i = (Math.round(i * mult)) / (double) mult;
                        contador++;
                    }
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='42'><b>Espesor soldadura en bocas</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'></th>");
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
                        out.print("<th>" + i + "</th>");
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
                    out.print("<tr>");
                    lst_promedios = jpacreb.Promedio_soldadura_espesores_bocas(id_registro);
                    if (lst_promedios != null) {
                        Object[] obj_promedio_soldadura = (Object[]) lst_promedios.get(0);
                        if (obj_promedio_soldadura[2] == null) {
                            out.print("<th colspan='41'>No se han registrado espesores en bocas</th>");
                        } else {
                            String result_cps = jpacreb.Calcular_CP_CPK_espesores_id_registro(Integer.parseInt(obj_registro[1].toString()), lst_espesores_bocas, Integer.parseInt(obj_registro[74].toString()));
                            String[] arg_result_cps = result_cps.split("-");
                            out.print("<th colspan='41'>Promedio de sellado : " + obj_promedio_soldadura[2] + "&nbsp;&nbsp;&nbsp;&nbsp;Desviación estandar : " + arg_result_cps[5] + "&nbsp;&nbsp;&nbsp;&nbsp;CP : " + arg_result_cps[0] + "&nbsp;&nbsp;&nbsp;&nbsp;CPK : " + arg_result_cps[1] + "</th>");
                        }
                    } else {
                        out.print("<th colspan='41'>No se han registrado espesores en bocas</th>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
                //FIN SOLDADURA EN BOCA
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="SOLDADURA EN COLA">
                //SOLDADURA EN COLA
//                double inicio_cola = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
//                double fin_cola = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                lst_espesores_colas = jpacrec.Consultar_registro_espesores_colas(id_registro);
                if (lst_espesores_colas == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Espesor soldadura en colas</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de espesor soldadura en colas</b><br /><br />");
                } else {
                    double inicio_cola = 0;
                    double fin_cola = 0;
                    if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[47].toString()) > 0) {
                        inicio_cola = Double.parseDouble(obj_ficha[47].toString()) - Double.parseDouble(obj_ficha[49].toString());
                        fin_cola = Double.parseDouble(obj_ficha[47].toString()) + Double.parseDouble(obj_ficha[48].toString());
                    } else {
                        inicio_cola = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
                        fin_cola = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                    }
                    contador = 0;
                    for (double i = inicio_cola; i < fin_cola + 0.01; i += 0.01) {
                        long mult = (long) Math.pow(10, 2);
                        i = (Math.round(i * mult)) / (double) mult;
                        contador++;
                    }
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='42'><b>Espesor soldadura en colas</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'></th>");
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
                        out.print("<th>" + i + "</th>");
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
                            out.print("<th colspan='41'>No se han registrado espesores en colas</th>");
                        } else {
                            String result_cps = jpacrec.Calcular_CP_CPK_espesores_id_registro(Integer.parseInt(obj_registro[1].toString()), lst_espesores_colas, Integer.parseInt(obj_registro[74].toString()));
                            String[] arg_result_cps = result_cps.split("-");
                            out.print("<th colspan='41'>Promedio de sellado : " + obj_promedio_soldadura[2] + "&nbsp;&nbsp;&nbsp;&nbsp;Desviación estandar : " + arg_result_cps[5] + "&nbsp;&nbsp;&nbsp;&nbsp;CP : " + arg_result_cps[0] + "&nbsp;&nbsp;&nbsp;&nbsp;CPK : " + arg_result_cps[1] + "</th>");
                        }
                    } else {
                        out.print("<th colspan='41'>No se han registrado espesores en colas</th>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
                //FIN SOLDADURA EN COLA
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="IMPLEMENTOS">
                // IMPLEMENTOS
                lst_implementos = jpacrip.Implementos_registro(id_registro);
                if (lst_implementos == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Electrodos, Implementos y Seriales</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:100px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:100px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de Electrodos / Implementos y Seriales</b><br /><br />");
                } else {
                    Object[] obj_implementos = (Object[]) lst_implementos.get(0);
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr><td colspan='5'><b>Electrodos, Implementos y Seriales</b></td></tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Electrodos</th>");
                    out.print("<th colspan='2'>Bocas</th>");
                    out.print("<th colspan='2'>Colas</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'>" + obj_implementos[5] + "</td>");
                    out.print("<td align='center' colspan='2'>" + obj_implementos[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Implementos</th>");
                    out.print("<th >Tijeras</th>");
                    if (obj_registro[65].toString().equals("R-PRF-011")) {
                        if (fecha_version_decimal >= 2015.0526) {
                            out.print("<th >Dispositivo apertura bolsa</th>");
                        } else {
                            out.print("<th >Espatula</th>");
                        }
                    } else {
                        out.print("<th >Espatula</th>");
                    }
                    out.print("<th >Llaves</th>");
                    out.print("<th >Pinzas</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if ((Integer) obj_implementos[7] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    if ((Integer) obj_implementos[8] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    if ((Integer) obj_implementos[9] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    if ((Integer) obj_implementos[10] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Seriales</th>");
                    if (obj_registro[65].toString().equals("R-PRF-013")) {
                        if (fecha_version_decimal >= 2015.0526) {
                            out.print("<th>Calibrador</th>");
                            out.print("<th>Indicador Digital</th>");
                        } else {
                            out.print("<th colspan='2'>Calibrador</th>");
                        }
                    } else {
                        out.print("<th colspan='2'>Calibrador</th>");
                    }
                    if (fecha_version_decimal >= 2015.0526) {
                        if (fecha_version_decimal >= 2018.0521) {
                            out.print("<th>Reglas</th>");
                            out.print("<th>Lainas</th>");
                        } else {
                            out.print("<th colspan='2'>Reglas</th>");
                        }
                    } else {
                        out.print("<th >Regla larga</th>");
                        out.print("<th >Regla corta</th>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    //CALIBRADORES E INDICADORES
                    if (obj_registro[65].toString().equals("R-PRF-013")) {
                        if (fecha_version_decimal >= 2015.0526) {
                            out.print("<td align='center' >" + obj_implementos[2] + "</td>");
                            out.print("<td align='center' >" + obj_implementos[11] + "</td>");
                        } else {
                            out.print("<td align='center' colspan='2'>" + obj_implementos[2] + "</td>");
                        }
                    } else {
                        out.print("<td align='center' colspan='2'>" + obj_implementos[2] + "</td>");
                    }
                    //REGLAS LAINAS
                    if (fecha_version_decimal >= 2015.0526) {
                        if (fecha_version_decimal >= 2018.0521) {
                            out.print("<td align='center'>" + obj_implementos[3] + "</td>");
                            out.print("<td align='center'>" + obj_implementos[16] + "</td>");
                        } else {
                            out.print("<td colspan='2' align='center'>" + obj_implementos[3] + "</td>");
                        }
                    } else {
                        out.print("<td align='center'>" + obj_implementos[3] + "</td>");
                        out.print("<td align='center'>" + obj_implementos[4] + "</td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
                // FIN IMPLEMENTOS
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="CONTROL DE ENTRADA DE MATERIALES">
                // CONTROL DE ENTRADA DE MATERIALES
                lst_entradas_material = jpacrem.Entradas_materiales_registro(id_registro);
                if (lst_entradas_material == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Control de entrada de materiales</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos en el control de entrada de materiales</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='11'><b>Control de entrada de materiales</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Producto en proceso</th>");
                    out.print("<th colspan='3'>Lotes en proceso</th>");
                    out.print("<th rowspan='2'>Responsable<br />proceso</th>");
                    out.print("<th rowspan='2'>Producto <br />entrante</th>");
                    out.print("<th colspan='3'>Lote entrante</th>");
                    out.print("<th rowspan='2'>Cantidad</th>");
                    out.print("<th rowspan='2'>Responsable<br />entrada</th>");
                    out.print("</tr>");
                    out.print("<th>C</th>");
                    out.print("<th>P</th>");
                    out.print("<th>Otro</th>");
                    out.print("<th>C</th>");
                    out.print("<th>P</th>");
                    out.print("<th>Otro</th>");
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
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //FIN CONTROL DE ENTRADA DE MATERIALES
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="PARADAS DE MAQUINA">
                //PARADAS DE MAQUINA
                lst_produccion_consulta = jpacpmq.Parada_maquinas_categoria_registradas(1, id_registro);
                lst_mantenimiento_consulta = jpacpmq.Parada_maquinas_categoria_registradas(2, id_registro);
                if (lst_produccion_consulta == null && lst_mantenimiento_consulta == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Paradas de maquina</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:200px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:200px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos en paradas de maquina.</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><b>Paradas de maquina</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th >Producción</td>");
                    out.print("<th >Mantenimiento</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign='top'>");
                    if (lst_produccion_consulta == null) {
                        out.print("<b class='naranja'>No hay paradas de maquina por producción</b><br /><br />");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        out.print("<th>Minutos</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_produccion_consulta.size(); i++) {
                            Object[] obj_paradas_produccion = (Object[]) lst_produccion_consulta.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_paradas_produccion[3] + "</td>");
                            out.print("<td align='center'>" + obj_paradas_produccion[4] + "</td>");
                            total = total + (Integer) obj_paradas_produccion[4];
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b>" + total + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                    out.print("</td>");
                    out.print("<td valign='top'>");
                    if (lst_mantenimiento_consulta == null) {
                        out.print("<b class='naranja'>No hay paradas de maquina por mantenimiento</b><br /><br />");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        out.print("<th>Minutos</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_mantenimiento_consulta.size(); i++) {
                            Object[] obj_paradas_mantenimiento = (Object[]) lst_mantenimiento_consulta.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_paradas_mantenimiento[3] + "</td>");
                            out.print("<td align='center'>" + obj_paradas_mantenimiento[4] + "</td>");
                            total = total + (Integer) obj_paradas_mantenimiento[4];
                            
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b>" + total + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                //FIN PARADAS DE MAQUINA
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="OBSERVACIONES">
                //OBSERVACIONES
                lst_observacion = jpacros.Observaciones_registro(id_registro);
                if (lst_observacion == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>OBSERVACIONES</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:100px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:100px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>OBSERVACIONES</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Fecha<br />(Hora)</th>");
                    out.print("<th>Asunto</th>");
                    out.print("<th>Descripción</th>");
                    out.print("<th>Responsable</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_observacion.size(); i++) {
                        Object[] obj_observacion = (Object[]) lst_observacion.get(i);
                        String[] arg_responsable = obj_observacion[5].toString().split("/");
                        out.print("<tr>");
                        if (obj_observacion[4].equals("Administrador")) {
                            out.print("<td align='center'><b class='administrador'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</b></td>");
                            out.print("<td><b class='administrador'>" + obj_observacion[2] + "</b></td>");
                            out.print("<td><b class='administrador'>" + obj_observacion[3] + "</b></td>");
                            out.print("<td><b class='administrador'>" + arg_responsable[1] + "</b></td>");
                        } else if (obj_observacion[4].equals("Inspectora-Calidad") || obj_observacion[4].equals("Coordinadora-Calidad")) {
                            out.print("<td align='center'><b class='calidad'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</b></td>");
                            out.print("<td ><b class='calidad'>" + obj_observacion[2] + "</b></b></td>");
                            out.print("<td><b class='calidad'>" + obj_observacion[3] + "</b></td>");
                            out.print("<td><b class='calidad'>" + arg_responsable[1] + "</b></td>");
                        } else if (obj_observacion[4].equals("Coordinadora-Produccion")) {
                            out.print("<td align='center'><b class='coordinadora'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</b></td>");
                            out.print("<td><b class='coordinadora'>" + obj_observacion[2] + "</b></td>");
                            out.print("<td><b class='coordinadora'>" + obj_observacion[3] + "</b></td>");
                            out.print("<td><b class='coordinadora'>" + arg_responsable[1] + "</b></td>");
                        } else if (obj_observacion[4].equals("Encargada-operaria")) {
                            out.print("<td align='center'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</td>");
                            out.print("<td>" + obj_observacion[2] + "</td>");
                            out.print("<td>" + obj_observacion[3] + "</td>");
                            out.print("<td>" + arg_responsable[1] + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //FIN OBSERVACIONES
                // </editor-fold>
                out.print("</div>");
            }
            if (pageContext.getRequest().getAttribute("Registro").toString().equals("Registro_visor_screen")) {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_ficha = jpacftn.Traer_ficha_registro(id_registro);
                Object[] obj_ficha = (Object[]) lst_ficha.get(0);
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                Object[] obj_registro = (Object[]) lst_resgistro.get(0);
                String fecha[] = obj_registro[2].toString().split("-");
                String fecha_version = fecha[0] + "." + fecha[1] + fecha[2];
                double fecha_version_decimal = Double.parseDouble(fecha_version);
                // <editor-fold defaultstate="collapsed" desc="CABECERA">
                // <editor-fold defaultstate="collapsed" desc="TITULO">
                out.print("<table class='table' style='width:1830px'>");
                out.print("<tr>");
                out.print("<td colspan='20' style='background-color:#CCC;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' colspan='2'>"
                        + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                        + "</td>");
                if (obj_registro[65].toString().equals("R-PRF-012")) {
                    out.print("<td align='center' colspan='2'><h2 class='negro'>REGISTRO <hr />CONTROL PROCESO LINEA SELLADO DRENAJE Y DRENAJE APD</h2></td>");
                } else {
                    out.print("<td align='center' colspan='2'><h2 class='negro'>REGISTRO <hr />CONTROL PROCESO</h2></td>");
                }
// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="VERSIONES">
                if (obj_registro[65].toString().equals("R-PRF-012")) {
                    if (fecha_version_decimal >= 2024.0521) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>10</b></h2></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>9</b></h2></td>");
                    }
                } else if (obj_registro[65].toString().equals("R-PRF-010")) {
                    if (fecha_version_decimal >= 2022.1228) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>12</b></h2></td>");
                    } else if (fecha_version_decimal >= 2020.0623) {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>11</b></h2></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><h2 class='negro'>CODIGO <b>" + obj_registro[65] + "</b> VERSION <b>10</b></h2></td>");
                    }
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="LOTES">
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>ORDEN DE PRODUCCION : </b>" + obj_registro[18] + "</td>");
                out.print("<td align='center'><b>CLIENTE : </b>" + obj_registro[19] + "</td>");
                out.print("<td align='center'><b>FICHA TECNICA : </b>" + obj_registro[24] + " <b> VERSION : </b>" + obj_registro[25] + "</td>");
                out.print("<td align='center'><b>LINEA : </b>" + obj_registro[6] + "</td>");
                out.print("<td align='center'><b>PRODUCTO : </b>" + obj_registro[21] + "<b>/</b>" + obj_registro[22] + "</td>");
                out.print("<td align='center'rowspan='2' ><b>RESPONSABLES : </b><br />");
                String[] reportantes = null;
                reportantes = obj_registro[17].toString().split(",");
                for (int j = 0; j < reportantes.length; j++) {
                    String[] reportantes_rol = null;
                    reportantes_rol = reportantes[j].split("/");
                    for (int k = 0; k < 1; k++) {
                        if (reportantes_rol[2].toString().equals("1")) {
                            if (reportantes_rol[0].equals("Administrador")) {
                                out.print("<b>" + reportantes_rol[1] + "</b><br />");
                            } else if (reportantes_rol[0].equals("Encargada-operaria")) {
                                out.print("" + reportantes_rol[1] + "<br />");
                            } else if (reportantes_rol[0].equals("Coordinadora-Produccion")) {
                                out.print("<b class='coordinadora'>" + reportantes_rol[1] + "</b><br />");
                            } else if (reportantes_rol[0].equals("Coordinadora-Calidad") || reportantes_rol[0].equals("Inspectora-Calidad")) {
                                out.print("<b class='calidad'>" + reportantes_rol[1] + "</b><br />");
                            }
                        }
                    }
                }
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>LOTE PRODUCTO:</b>" + obj_registro[3] + " <b> VOLUMEN : </b>" + obj_registro[23] + "</td>");
                if (obj_registro[65].toString().equals("R-PRF-010")) {
                    if (fecha_version_decimal < 2020.0623) {
                        out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + "</td>");
                        out.print("<td align='center'><b>TURNO : </b>" + obj_registro[4] + "</td>");
                    } else {
                        out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + " <b>TURNO : </b>" + obj_registro[4] + "</td>");
                    }
                    out.print("<td align='center'><b>MANGA C : </b>" + obj_registro[7] + " <b class='negro'> C alt : </b>" + obj_registro[78] + "<b> P :</b>" + obj_registro[8] + "</td>");
                    out.print("<td align='center'><b>COLOR / LOTE DE TINTA|FOIL : </b>" + obj_registro[67] + "/" + obj_registro[15] + " <b> M : </b>" + obj_registro[121] + "</td>");
                    if (fecha_version_decimal >= 2020.0623) {
                        out.print("<td align='center'><b>HORNO UV : </b> " + obj_registro[122] + " <b>LUZ LED : </b>" + obj_registro[123] + "</td>");
                    }
                } else {
                    out.print("<td align='center'><b>FECHA : </b>" + obj_registro[2] + "</td>");
                    out.print("<td align='center'><b>TURNO : </b>" + obj_registro[4] + "</td>");
                    out.print("<td align='center'><b>MANGA C : </b>" + obj_registro[7] + " <b class='negro'> C alt : </b>" + obj_registro[78] + "<b> P :</b>" + obj_registro[8] + "</td>");
                    out.print("<td align='center'><b>SUBLOTES C : </b>" + obj_registro[118] + " <b class='negro'> C alt : </b>" + obj_registro[119] + "<b> P :</b>" + obj_registro[120] + "</td>");
                }
                out.print("</tr>");
                out.print("</table>");
                // </editor-fold>
                // </editor-fold>
                out.print("<div style='width:1850px;'>");
                // <editor-fold defaultstate="collapsed" desc="PARAMETROS DE FRECIENCIA POR HORA">
                //PARAMETROS DE FRECIENCIA POR HORA
                out.print("<div style='float:left'>");
                lst_parametros = jpacrfm.Parametros_tomas_registro_frecuencia_hora(id_registro);
                if (lst_parametros == null) {
                    out.print("<table class='table' style='width:1150px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Parámetros de frecuencia 30 min</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:330px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:1150px;height:330px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<table class='table' style='width:1150px'>");
                    out.print("<tr>");
                    out.print("<td colspan='12'><b>Parámetros de frecuencia 30 min</b></td>");
                    out.print("</tr>");
                    // <editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    for (int i = 1; i <= 18; i++) {
                        if (i >= 9 && i <= 16) {
                            out.print("<th>" + (i - 1) + "</th>");
                        } else {
                            out.print("<th>" + ((i == 18) ? (i - 2) : i) + "</th>");
                        }
                    }
                    out.print("<th>PROM.</th>");
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
                                    out.print("<td align='center' style='background-color : #dcdcdc;border:none'></td>");
                                } else if (obj_parametros[(var_inicial + j)].toString().equals("null")) {
                                    out.print("<td align='center' style='background-color : #dcdcdc;border:none'><b class='rojo'>Pendiente</b></td>");
                                } else {
                                    String[] arg_responsables = obj_parametros[(var_inicial + j) + 18].toString().split("/");
                                    if (arg_responsables[1].equals("Administrador")) {
                                        out.print("<td align='center' style='background-color : #dcdcdc;border:none'><b>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                    } else if (arg_responsables[1].equals("Encargada-operaria")) {
                                        out.print("<td align='center' style='background-color : #dcdcdc;border:none'>" + obj_parametros[(var_inicial + j)] + "</td>");
                                    } else if (arg_responsables[1].equals("Coordinadora-Produccion")) {
                                        out.print("<td align='center' style='background-color : #dcdcdc;border:none'><b class='coordinadora'>" + obj_parametros[(var_inicial + j)] + "</b></td>");
                                    } else if (arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Documental")) {
                                        out.print("<td align='center' style='background-color : #dcdcdc;border:none'><b class='calidad'>" + obj_parametros[(var_inicial + j)] + "</b></td>");
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
                                } else if (arg_responsables[1].equals("Inspectora-Calidad") || arg_responsables[1].equals("Coordinadora-Calidad") || arg_responsables[1].equals("Documental")) {
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
                                    promedio_frecuencia_hora = "<b>Cumple</b>";
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
                    }
                    lst_responsables = jpacrfm.Responsables_tomas_registro_frecuencia_hora(id_registro);
                    Object[] obj_responsables = (Object[]) lst_responsables.get(0);
                    out.print("<tr>");
                    out.print("<th>Hora de toma de datos</th>");
                    for (int i = 0; i < 18; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color : #dcdcdc;border:none'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color : #dcdcdc;border:none'><b class='coordinadora'>" + arg_responsables[0] + "</b></td>");
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
                    out.print("<th>Responsables</th>");
                    for (int i = 0; i < 18; i++) {
                        if (obj_responsables[i] == null) {
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color : #dcdcdc;border:none'></td>");
                            } else {
                                out.print("<td align='center'></td>");
                            }
                        } else {
                            String[] arg_responsables = obj_responsables[i].toString().split("/");
                            if ((i + 1) == 8 || (i + 1) == 17) {
                                out.print("<td align='center' style='background-color : #dcdcdc;border:none'><b class='coordinadora'>" + arg_responsables[2] + "</b></td>");
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
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="VERIFICACIÓN DE LOTE Y CODIGO">

                //VERIFICACIÓN DE LOTE Y CODIGO
                lst_parametros = jpacrlc.Parametros_tomas_registro_lote_codigo(id_registro);
                if (lst_parametros == null) {
                    out.print("<table class='table' style='width:1150px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Verificación de lote y código</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:100px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:1150px;height:100px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<table class='table' style='width:1150px'>");
                    out.print("<tr>");
                    out.print("<td colspan='42'><b>Verificación de lote y código</b></td>");
                    out.print("</tr>");
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
                //FIN VERIFICACIÓN DE LOTE Y CODIGO
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="PRUEBAS CALIDAD">

                //PRUEBAS CALIDAD
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                lst_resgistro = jpacrgt.Traer_registro_id_registro(id_registro);
                lst_parametros = jpacrpc.Parametros_tomas_registro_prueba_calidad(id_registro);
                if (lst_parametros == null) {
                    out.print("<table class='table' style='width:1150px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Pruebas calidad</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:1150px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de parámetros de pruebas de calidad</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:1150px'>");
                    out.print("<tr>");
                    out.print("<td colspan='9'><b>Pruebas calidad</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Parámetro</th>");
                    out.print("<th>1</th>");
                    out.print("<th>2</th>");
                    out.print("<th>3</th>");
                    out.print("<th>4</th>");
                    out.print("<th>5</th>");
                    out.print("<th>6</th>");
                    out.print("<th>7</th>");
                    out.print("<th>8</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_parametros.size(); i++) {
                        Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_parametros[3] + "</td>");
                        if ((Integer) obj_parametros[4] == 1) {
                            if (obj_parametros[5] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                            if (obj_parametros[8] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[8].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[16].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[8] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                                }
                            }
                            if (obj_parametros[9] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[9].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[9] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[17].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[9] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[9] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[9] + "</b></td>");
                                }
                            }
                            if (obj_parametros[10] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[10].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[10] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[18].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[10] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[10] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[10] + "</b></td>");
                                }
                            }
                            if (obj_parametros[11] == null) {
                                out.print("<td align='center'><b></b></td>");
                            } else if (obj_parametros[11].equals("No cumple")) {
                                out.print("<td align='center'><b class='rojo'>" + obj_parametros[11] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[19].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td align='center'><b>" + obj_parametros[11] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td align='center'>" + obj_parametros[11] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td align='center'><b class='coordinadora'>" + obj_parametros[12] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td align='center'><b class='calidad'>" + obj_parametros[11] + "</b></td>");
                                }
                            }
                            if (obj_parametros[12] == null) {
                                out.print("<td align='center'><b></b></td>");
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
                        } else if ((Integer) obj_parametros[4] == 2) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                            if (obj_parametros[6] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[6].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[6] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[14].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[6] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[6] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' ='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                            if (obj_parametros[7] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[7].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[7] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[15].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[7] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[7] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' align='center'><b class='calidad'>" + obj_parametros[7] + "</b></td>");
                                }
                            }
                            if (obj_parametros[8] == null) {
                                out.print("<td colspan='2' align='center'><b></b></td>");
                            } else if (obj_parametros[8].equals("No cumple")) {
                                out.print("<td colspan='2' align='center'><b class='rojo'>" + obj_parametros[8] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[16].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='2' align='center'><b>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='2' align='center'>" + obj_parametros[8] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='2' align='center'><b class='coordinadora'>" + obj_parametros[8] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='2' align='center'><b class='calidad'>" + obj_parametros[8] + "</b></td>");
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 4) {
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
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
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
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='4' align='center'><b class='calidad'>" + obj_parametros[6] + "</b></td>");
                                }
                            }
                        } else if ((Integer) obj_parametros[4] == 8) {
                            if (obj_parametros[5] == null) {
                                out.print("<td colspan='8' align='center'><b></b></td>");
                            } else if (obj_parametros[5].equals("No cumple")) {
                                out.print("<td colspan='8' align='center'><b class='rojo'>" + obj_parametros[5] + "</b></td>");
                            } else {
                                String[] arg_responsables = obj_parametros[13].toString().split("/");
                                if (arg_responsables[0].equals("Administrador")) {
                                    out.print("<td colspan='8'  align='center'><b>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Encargada-operaria")) {
                                    out.print("<td colspan='8'  align='center'>" + obj_parametros[5] + "</td>");
                                } else if (arg_responsables[0].equals("Coordinadora-Produccion")) {
                                    out.print("<td colspan='8'  align='center'><b class='coordinadora'>" + obj_parametros[5] + "</b></td>");
                                } else if (arg_responsables[0].equals("Inspectora-Calidad") || arg_responsables[0].equals("Coordinadora-Calidad")) {
                                    out.print("<td colspan='8' align='center'><b class='calidad'>" + obj_parametros[5] + "</b></td>");
                                }
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //FIN PRUEBAS CALIDAD
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="DESCRIPCION DE PNC">
                //DESCRIPCION DE PNC
                lst_categoria = jpacctg.Categorias();
                for (int j = 0; j < lst_categoria.size(); j++) {
                    Object[] obj_categorias = (Object[]) lst_categoria.get(j);
                    lst_pnc_registro = jpacpnc.Pnc_registro(id_registro, (Integer) obj_categorias[0]);
                    if (lst_pnc_registro == null) {
                        contador++;
                    }
                }
                if (contador == lst_categoria.size()) {
                    out.print("<table class='table' style='width:1150px'>");
                    out.print("<tr>");
                    out.print("<td ><b>Descripcion de producto no conforme (PNC</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:320px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:1150px;height:320px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de descripción de producto no conforme</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:1150px'>");
                    out.print("<tr>");
                    out.print("<td colspan='10'><b>Descripcion de producto no conforme (PNC)</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Descripción</th>");
                    for (int i = 0; i < 8; i++) {
                        out.print("<th>" + (i + 1) + "</th>");
                    }
                    out.print("<th>Tolal</th>");
                    out.print("</tr>");
                    for (int j = 0; j < lst_categoria.size(); j++) {
                        Object[] obj_categorias = (Object[]) lst_categoria.get(j);
                        lst_pnc_registro = jpacpnc.Pnc_registro(id_registro, (Integer) obj_categorias[0]);
                        if (lst_pnc_registro == null) {
                        } else {
                            out.print("<tr>");
                            out.print("<th colspan='10'>" + obj_categorias[1] + "</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_pnc_registro.size(); i++) {
                                Object[] obj_pnc_registro = (Object[]) lst_pnc_registro.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_pnc_registro[2] + "</td>");
                                if (obj_pnc_registro[5] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[5] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[6] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[6] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[7] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[7] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[8] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[8] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[9] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[9] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[10] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[10] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[11] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[11] + ""
                                            + "</td>");
                                }
                                if (obj_pnc_registro[12] == null) {
                                    out.print("<td align='center'>0</td>");
                                } else {
                                    out.print("<td align='center'>"
                                            + "" + obj_pnc_registro[12] + ""
                                            + "</td>");
                                }
                                suma_total = suma_total + ((Integer) obj_pnc_registro[5] + (Integer) obj_pnc_registro[6] + (Integer) obj_pnc_registro[7] + (Integer) obj_pnc_registro[8] + (Integer) obj_pnc_registro[9] + (Integer) obj_pnc_registro[10] + (Integer) obj_pnc_registro[11] + (Integer) obj_pnc_registro[12]);
                                out.print("<th align='center'>" + ((Integer) obj_pnc_registro[5] + (Integer) obj_pnc_registro[6] + (Integer) obj_pnc_registro[7] + (Integer) obj_pnc_registro[8] + (Integer) obj_pnc_registro[9] + (Integer) obj_pnc_registro[10] + (Integer) obj_pnc_registro[11] + (Integer) obj_pnc_registro[12]) + "</th>");
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
                //FIN DESCRIPCION DE PNC
                out.print("</div>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="SOLDADURA EN BOCA">
                //SOLDADURA EN BOCA
                out.print("<div style='float:left; width:680px'>");
//                double inicio_boca = Double.parseDouble(obj_ficha[9].toString()) - Double.parseDouble(obj_ficha[11].toString());
//                double fin_boca = Double.parseDouble(obj_ficha[9].toString()) + Double.parseDouble(obj_ficha[10].toString());
                lst_espesores_bocas = jpacreb.Consultar_registro_espesores_bocas(id_registro);
                if (lst_espesores_bocas == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Espesor soldadura en Centros</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de espesor soldadura en bocas</b><br /><br />");
                } else {
                    double inicio_boca = 0;
                    double fin_boca = 0;
                    if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[47].toString()) > 0) {
                        inicio_boca = Double.parseDouble(obj_ficha[47].toString()) - Double.parseDouble(obj_ficha[49].toString());
                        fin_boca = Double.parseDouble(obj_ficha[47].toString()) + Double.parseDouble(obj_ficha[48].toString());
                    } else {
                        inicio_boca = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
                        fin_boca = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                    }
                    contador = 0;
                    for (double i = inicio_boca; i < fin_boca + 0.01; i += 0.01) {
                        long mult = (long) Math.pow(10, 2);
                        i = (Math.round(i * mult)) / (double) mult;
                        contador++;
                    }
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='42'><b>Espesor soldadura en Centros</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'></th>");
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
                        out.print("<th>" + i + "</th>");
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
                    out.print("<tr>");
                    lst_promedios = jpacreb.Promedio_soldadura_espesores_bocas(id_registro);
                    if (lst_promedios != null) {
                        Object[] obj_promedio_soldadura = (Object[]) lst_promedios.get(0);
                        if (obj_promedio_soldadura[2] == null) {
                            out.print("<th colspan='41'>No se han registrado espesores en bocas</th>");
                        } else {
                            String result_cps = jpacreb.Calcular_CP_CPK_espesores_id_registro(Integer.parseInt(obj_registro[1].toString()), lst_espesores_bocas, Integer.parseInt(obj_registro[74].toString()));
                            String[] arg_result_cps = result_cps.split("-");
                            out.print("<th colspan='41'>Promedio de sellado : " + obj_promedio_soldadura[2] + "&nbsp;&nbsp;&nbsp;&nbsp;Desviación estandar : " + arg_result_cps[5] + "&nbsp;&nbsp;&nbsp;&nbsp;CP : " + arg_result_cps[0] + "&nbsp;&nbsp;&nbsp;&nbsp;CPK : " + arg_result_cps[1] + "</th>");
                        }
                    } else {
                        out.print("<th colspan='41'>No se han registrado espesores en bocas</th>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
                //FIN SOLDADURA EN BOCA
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="SOLDADURA EN COLA">
                //SOLDADURA EN COLA
//                double inicio_cola = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
//                double fin_cola = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                lst_espesores_colas = jpacrec.Consultar_registro_espesores_colas(id_registro);
                if (lst_espesores_colas == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Espesor soldadura en colas</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de espesor soldadura en colas</b><br /><br />");
                } else {
                    double inicio_cola = 0;
                    double fin_cola = 0;
                    if ((Integer) obj_registro[74] == 1 && Double.parseDouble(obj_ficha[47].toString()) > 0) {
                        inicio_cola = Double.parseDouble(obj_ficha[47].toString()) - Double.parseDouble(obj_ficha[49].toString());
                        fin_cola = Double.parseDouble(obj_ficha[47].toString()) + Double.parseDouble(obj_ficha[48].toString());
                    } else {
                        inicio_cola = Double.parseDouble(obj_ficha[12].toString()) - Double.parseDouble(obj_ficha[14].toString());
                        fin_cola = Double.parseDouble(obj_ficha[12].toString()) + Double.parseDouble(obj_ficha[13].toString());
                    }
                    contador = 0;
                    for (double i = inicio_cola; i < fin_cola + 0.01; i += 0.01) {
                        long mult = (long) Math.pow(10, 2);
                        i = (Math.round(i * mult)) / (double) mult;
                        contador++;
                    }
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='42'><b>Espesor soldadura en colas</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'></th>");
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
                        out.print("<th>" + i + "</th>");
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
                            out.print("<th colspan='41'>No se han registrado espesores en colas</th>");
                        } else {
                            String result_cps = jpacrec.Calcular_CP_CPK_espesores_id_registro(Integer.parseInt(obj_registro[1].toString()), lst_espesores_colas, Integer.parseInt(obj_registro[74].toString()));
                            String[] arg_result_cps = result_cps.split("-");
                            out.print("<th colspan='41'>Promedio de sellado : " + obj_promedio_soldadura[2] + "&nbsp;&nbsp;&nbsp;&nbsp;Desviación estandar : " + arg_result_cps[5] + "&nbsp;&nbsp;&nbsp;&nbsp;CP : " + arg_result_cps[0] + "&nbsp;&nbsp;&nbsp;&nbsp;CPK : " + arg_result_cps[1] + "</th>");
                        }
                    } else {
                        out.print("<th colspan='41'>No se han registrado espesores en colas</th>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                }
                //FIN SOLDADURA EN COLA
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="IMPLEMENTOS">
                // IMPLEMENTOS
                lst_implementos = jpacrip.Implementos_registro(id_registro);
                if (lst_implementos == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Electrodos, Implementos y Seriales</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:100px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:100px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos de Electrodos / Implementos y Seriales</b><br /><br />");
                } else {
                    Object[] obj_implementos = (Object[]) lst_implementos.get(0);
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr><td colspan='5'><b>Electrodos, Implementos y Seriales</b></td></tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Electrodos</th>");
                    out.print("<th colspan='2'>Bocas</th>");
                    out.print("<th colspan='2'>Colas</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'>" + obj_implementos[5] + "</td>");
                    out.print("<td align='center' colspan='2'>" + obj_implementos[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Implementos</th>");
                    out.print("<th >Tijeras</th>");
                    out.print("<th >Espatula</th>");
                    out.print("<th colspan='2'>Llaves</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if ((Integer) obj_implementos[7] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    if ((Integer) obj_implementos[8] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    if ((Integer) obj_implementos[9] == 1) {
                        out.print("<td align='center'>Si</td>");
                    } else {
                        out.print("<td align='center'>No</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Seriales</th>");
                    out.print("<th>Calibrador</th>");
                    out.print("<th>Indicador Digital</th>");
                    out.print("<th >Regla larga</th>");
                    out.print("<th >Regla corta</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' >" + obj_implementos[2] + "</td>");
                    out.print("<td align='center' >" + obj_implementos[11] + "</td>");
                    out.print("<td align='center'>" + obj_implementos[3] + "</td>");
                    out.print("<td align='center'>" + obj_implementos[4] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                // FIN IMPLEMENTOS
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="CONTROL DE ENTRADA DE MATERIALES">
                // CONTROL DE ENTRADA DE MATERIALES
                lst_entradas_material = jpacrem.Entradas_materiales_registro(id_registro);
                if (lst_entradas_material == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Control de entrada de materiales</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:150px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:150px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos en el control de entrada de materiales</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='11'><b>Control de entrada de materiales</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>Producto en proceso</th>");
                    out.print("<th colspan='3'>Lotes en proceso</th>");
                    out.print("<th rowspan='2'>Responsable<br />proceso</th>");
                    out.print("<th rowspan='2'>Producto <br />entrante</th>");
                    out.print("<th colspan='3'>Lote entrante</th>");
                    out.print("<th rowspan='2'>Cantidad</th>");
                    out.print("<th rowspan='2'>Responsable<br />entrada</th>");
                    out.print("</tr>");
                    out.print("<th>C</th>");
                    out.print("<th>P</th>");
                    out.print("<th>Otro</th>");
                    out.print("<th>C</th>");
                    out.print("<th>P</th>");
                    out.print("<th>Otro</th>");
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
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //FIN CONTROL DE ENTRADA DE MATERIALES
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="PARADAS DE MAQUINA">
                //PARADAS DE MAQUINA
                lst_produccion_consulta = jpacpmq.Parada_maquinas_categoria_registradas(1, id_registro);
                lst_mantenimiento_consulta = jpacpmq.Parada_maquinas_categoria_registradas(2, id_registro);
                if (lst_produccion_consulta == null && lst_mantenimiento_consulta == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>Paradas de maquina</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:200px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:200px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
//                    out.print("<b class='naranja'>No hay datos en paradas de maquina.</b><br /><br />");
                } else {
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><b>Paradas de maquina</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th >Producción</td>");
                    out.print("<th >Mantenimiento</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign='top'>");
                    if (lst_produccion_consulta == null) {
                        out.print("<b class='naranja'>No hay paradas de maquina por producción</b><br /><br />");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        out.print("<th>Minutos</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_produccion_consulta.size(); i++) {
                            Object[] obj_paradas_produccion = (Object[]) lst_produccion_consulta.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_paradas_produccion[3] + "</td>");
                            out.print("<td align='center'>" + obj_paradas_produccion[4] + "</td>");
                            total = total + (Integer) obj_paradas_produccion[4];
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b>" + total + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                    out.print("</td>");
                    out.print("<td valign='top'>");
                    if (lst_mantenimiento_consulta == null) {
                        out.print("<b class='naranja'>No hay paradas de maquina por mantenimiento</b><br /><br />");
                    } else {
                        total = 0;
                        out.print("<table class='table' align='center'>");
                        out.print("<tr>");
                        out.print("<th>Parada</th>");
                        out.print("<th>Minutos</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_mantenimiento_consulta.size(); i++) {
                            Object[] obj_paradas_mantenimiento = (Object[]) lst_mantenimiento_consulta.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_paradas_mantenimiento[3] + "</td>");
                            out.print("<td align='center'>" + obj_paradas_mantenimiento[4] + "</td>");
                            total = total + (Integer) obj_paradas_mantenimiento[4];
                            
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b>" + total + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                }
                //FIN PARADAS DE MAQUINA
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="OBSERVACIONES">
                //OBSERVACIONES
                lst_observacion = jpacros.Observaciones_registro(id_registro);
                if (lst_observacion == null) {
                    out.print("<table class='table' style='width:680px;'>");
                    out.print("<tr>");
                    out.print("<td ><b>OBSERVACIONES</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='background-color:#eee;height:100px'>"
                            + "<img src='Interfaz/Contenido/images/Linea.png' alt='Logo' style='width:680px;height:100px' />");
////                            + "<b class='naranja'>No hay datos de parámetros de frecuencia por hora</b><br /><br /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                } else {
                    out.print("<table class='table' style='width:680px'>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>OBSERVACIONES</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Fecha<br />(Hora)</th>");
                    out.print("<th>Asunto</th>");
                    out.print("<th>Descripción</th>");
                    out.print("<th>Responsable</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_observacion.size(); i++) {
                        Object[] obj_observacion = (Object[]) lst_observacion.get(i);
                        String[] arg_responsable = obj_observacion[5].toString().split("/");
                        out.print("<tr>");
                        if (obj_observacion[4].equals("Administrador")) {
                            out.print("<td align='center'><b class='administrador'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</b></td>");
                            out.print("<td><b class='administrador'>" + obj_observacion[2] + "</b></td>");
                            out.print("<td><b class='administrador'>" + obj_observacion[3] + "</b></td>");
                            out.print("<td><b class='administrador'>" + arg_responsable[1] + "</b></td>");
                        } else if (obj_observacion[4].equals("Inspectora-Calidad") || obj_observacion[4].equals("Coordinadora-Calidad")) {
                            out.print("<td align='center'><b class='calidad'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</b></td>");
                            out.print("<td ><b class='calidad'>" + obj_observacion[2] + "</b></b></td>");
                            out.print("<td><b class='calidad'>" + obj_observacion[3] + "</b></td>");
                            out.print("<td><b class='calidad'>" + arg_responsable[1] + "</b></td>");
                        } else if (obj_observacion[4].equals("Coordinadora-Produccion")) {
                            out.print("<td align='center'><b class='coordinadora'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</b></td>");
                            out.print("<td><b class='coordinadora'>" + obj_observacion[2] + "</b></td>");
                            out.print("<td><b class='coordinadora'>" + obj_observacion[3] + "</b></td>");
                            out.print("<td><b class='coordinadora'>" + arg_responsable[1] + "</b></td>");
                        } else if (obj_observacion[4].equals("Encargada-operaria")) {
                            out.print("<td align='center'>" + obj_observacion[6] + "<br />(" + obj_observacion[7] + ")</td>");
                            out.print("<td>" + obj_observacion[2] + "</td>");
                            out.print("<td>" + obj_observacion[3] + "</td>");
                            out.print("<td>" + arg_responsable[1] + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                //FIN OBSERVACIONES
                out.print("</div>");
                // </editor-fold>
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Tag_visor_registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return super.doStartTag();
    }
}
