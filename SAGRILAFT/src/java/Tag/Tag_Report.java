package Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controller.SegmentationControllerJpa;
import Controller.CIIUControllerJpa;
import Controller.ConfigurationControllerJpa;
import javax.servlet.http.HttpSession;

public class Tag_Report extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        SegmentationControllerJpa SegmentatioJpa = new SegmentationControllerJpa();
        CIIUControllerJpa CIUUJpa = new CIIUControllerJpa();
        ConfigurationControllerJpa ConfigurationJpa = new ConfigurationControllerJpa();
        List lst_segmentation = null, lst_CIIU = null, lst_Conf = null, lst_Configuration = null, lst_Company = null, lst_Conf7 = null;
        int Qualification = 0, Experience = 0, Relationship = 0, LevelCode2 = 0, Report = 0, vcv = 0, Temp = 0, idUserCli = 0;
        double CalcQ = 0.0, CalcEx = 0.0, CalcRel = 0.0, sumT = 0.0, LevelCode1 = 0.0, LevelRisk = 0;
        String Format = "", BusinessAssociate = "", VlrFormatt = "", ConsultQuery = "";
        try {
            try {
                Format = pageContext.getRequest().getAttribute("Format").toString();
            } catch (Exception e) {
                Format = "NATIONAL";
            }
            try {
                Report = Integer.parseInt(pageContext.getRequest().getAttribute("Report").toString());
            } catch (NumberFormatException e) {
                Report = 0;
            }
            if (Report == 1) {
                //<editor-fold defaultstate="collapsed" desc="HISTORICAL COUNTERPART">
                try {
                    ConsultQuery = pageContext.getRequest().getAttribute("ConsultQuery").toString();
                } catch (Exception e) {
                    ConsultQuery = "";
                }
                try {
                    BusinessAssociate = pageContext.getRequest().getAttribute("BusinessAssociate").toString();
                } catch (Exception e) {
                    BusinessAssociate = "";
                }
                try {
                    Temp = Integer.parseInt(pageContext.getRequest().getAttribute("Temp").toString());
                } catch (NumberFormatException e) {
                    Temp = 0;
                }
                try {
                    idUserCli = Integer.parseInt(pageContext.getRequest().getAttribute("idUserCli").toString());
                } catch (Exception e) {
                    idUserCli = 0;
                }
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Historial Contraparte</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                if (!BusinessAssociate.equals("")) {
                    //<editor-fold defaultstate="collapsed" desc="HISTORICAL DETAIL">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contReport scrollbarReport'>");

                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Historial <b style='color:#387a8d'>" + BusinessAssociate + "</b></h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    lst_Company = SegmentatioJpa.ConsultSegmentationCompany(BusinessAssociate);
                    if (lst_Company != null) {
                        out.print("<div class='mb-2'><b style='color:#002237'>Cant. registro:</b> " + lst_Company.size() + "</div>");
                        for (int i = 0; i < lst_Company.size(); i++) {
                            Object[] obj_company = (Object[]) lst_Company.get(i);
                            out.print("<div id='list'><span>");
                            out.print("<div class=\"single-item\">");
                            out.print("<div id=\"accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="HEAD">
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-" + i + "\" aria-expanded=\"true\">");
                            out.print("<div  class='styledata single-item'  style='display:flex; justify-content:space-between;width:100%; text-align:center; border-right: 3px solid green border-left: 3px solid #fd1e08;'>");

                            out.print("<div style='width:22%'>");
                            out.print("<b>" + (obj_company[5] == null ? "Sin tipo" : obj_company[5].equals("") ? "Sin tipo" : obj_company[5]) + "</b>");
                            out.print("</div>");

                            out.print("<div style='width:22%'>");
                            out.print("<b>" + (obj_company[19] == null ? "Sin fecha definida" : obj_company[19].equals("") ? "Sin fecha definida" : obj_company[19]) + "</b>");
                            out.print("</div>");

                            out.print("<div style='width:15%'>");
                            out.print("<b>" + (obj_company[17] == null ? "Sin valor anual" : obj_company[17].equals("") ? "Sin valor anual" : obj_company[17]) + "</b>");
                            out.print("</div>");

                            out.print("<div style='width:22%'>");
                            out.print((Integer.parseInt(obj_company[33].toString()) == 1 ? "<b style='color:green'>ACTIVO" : "<b style='color:A09F9F'>OBSOLETO") + "</b>");
                            out.print("</div>");
                            
                            out.print("<div width='width: 18%;'>");
                            out.print("<button class='btn btn-info btn-sm' data-toggle='tooltip' data-pacement='top' title='Ver documento' onclick='window.location.href=\"Document?opt=1&IdDoc="+ obj_company[1] +"&Event=Checking\"; cargarDatos()'><i class='fas fa-file'></i></button>");
                            out.print("</div>");

                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="CONTAINER">
                            out.print("<div class='DivAJRP mt-3 mb-3'>");
                            out.print("<div  class=\"accordion-body collapse\" id=\"panel-body-" + i + "\" data-parent=\"#accordion\">");
//                            out.print("<button class='btn btn-'>tesst</button>");
                            out.print("<ul class=\"nav nav-tabs\" style='justify-content:center;' id=\"myTab2\" role=\"tablist\">\n"
                                    + "                      <li class=\"nav-item\">\n"
                                    + "                        <a class=\"nav-link active\" id=\"basic-tab2\" data-toggle=\"tab\" href=\"#basic\" role=\"tab\" aria-controls=\"BasicInformation\" aria-selected=\"true\">Información Basica</a>\n"
                                    + "                      </li>\n"
                                    + "                      <li class=\"nav-item\">\n"
                                    + "                        <a class=\"nav-link\" id=\"risk-tab2\" data-toggle=\"tab\" href=\"#risk\" role=\"tab\" aria-controls=\"Análisis de riesgo\" aria-selected=\"false\">Análisis de riesgo</a>\n"
                                    + "                      </li>\n"
                                    + "                      <li class=\"nav-item\">\n"
                                    + "                        <a class=\"nav-link\" id=\"oea-tab2\" data-toggle=\"tab\" href=\"#oea\" role=\"tab\" aria-controls=\"Controles OEA\" aria-selected=\"false\">Controles OEA</a>\n"
                                    + "                      </li>\n"
                                    + "                      <li class=\"nav-item\">\n"
                                    + "                        <a class=\"nav-link\" id=\"sagrilaf-tab2\" data-toggle=\"tab\" href=\"#sagrilaf\" role=\"tab\" aria-controls=\"Controles SAGRILAFT y PTEE\" aria-selected=\"false\">Controles SAGRILAFT y PTEE</a>\n"
                                    + "                      </li>\n"
                                    + "                    </ul>");

                            out.print("<div class=\"tab-content tab-bordered\" id=\"myTab3Content\">");

                            out.print("<div class=\"tab-pane fade show active\" id=\"basic\" role=\"tabpanel\" aria-labelledby=\"basic-tab2\">");
                            //<editor-fold defaultstate="collapsed" desc="BASIC - INFORMATION">

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Código Plastitec</b></div><div class='DivDetailDescription'>" + ((obj_company[2] == null) ? "" : obj_company[2]) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Fecha Registro</b></div><div class='DivDetailDescription'>" + ((obj_company[3] == null) ? "" : obj_company[3]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Área responsable</b></div><div class='DivDetailDescription'>" + ((obj_company[4] == null) ? "" : obj_company[4]) + "</div>");
                            if (obj_company[32].equals("NATIONAL")) {
                                out.print("<div class='DivDetail b_color'><b>NIT / ID: </b></div><div class='DivDetailDescription'>" + ((obj_company[6] == null) ? "" : obj_company[6]) + "</div>");
                            } else {
                                out.print("<div class='DivDetail b_color'><b>TAX / ID: </b></div><div class='DivDetailDescription'>" + ((obj_company[6] == null) ? "" : obj_company[6]) + "</div>");
                            }
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Asociado de negocio</b></div><div class='DivDetailDescription'>" + ((obj_company[7] == null) ? "" : obj_company[7]) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Tipo</b></div><div class='DivDetailDescription'>" + ((obj_company[5] == null) ? "" : obj_company[5]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            if (obj_company[32].equals("NATIONAL")) {
                                out.print("<div class='DivDetail b_color'><b>Ciudad</b></div><div class='DivDetailDescription'>" + ((obj_company[12] == null) ? "" : obj_company[12]) + "</div>");
                            } else {
                                out.print("<div class='DivDetail b_color'><b>País</b></div><div class='DivDetailDescription'>" + ((obj_company[12] == null) ? "" : obj_company[12]) + "</div>");
                            }
                            out.print("<div class='DivDetail b_color'><b>PEP</b></div><div class='DivDetailDescription'>" + ((obj_company[21] == null) ? "" : obj_company[21]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Beneficiario Final</b></div><div class='DivDetailDescription'>" + ((obj_company[9] == null) ? "" : obj_company[9]) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Observacion</b></div><div class='DivDetailDescription'>" + ((obj_company[31] == null) ? "" : obj_company[31]) + "</div>");
                            out.print("</div>");

                            if (obj_company[32].equals("NATIONAL")) {
                                out.print("<div style='display:flex;justify-content:space-evenly'>");
                                out.print("<div class='DivDetail b_color'><b>Código CIIU principal</b></div><div class='DivDetailDescription'>");
                                if (obj_company[10] != null) {
                                    lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_company[10].toString()));
                                    if (lst_CIIU != null) {
                                        Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                        out.print(obj_CIIU[1]);
                                        lst_CIIU = null;
                                    }
                                } else {
                                    out.print("Sin dato registrado");
                                }
                                out.print("</div>");
                                out.print("<div class='DivDetail b_color'><b>ACT. económica primaria</b></div><div class='DivDetailDescription'>");
                                if (obj_company[10] != null) {
                                    lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_company[10].toString()));
                                    if (lst_CIIU != null) {
                                        Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                        lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                        Object[] obj_Setting = (Object[]) lst_Conf7.get(0);
                                        if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                            out.print("<b style='color:red;'>Riesgo Alto</b>");
                                        } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                            out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                        } else {
                                            out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                        }
                                        LevelCode1 = Integer.parseInt(obj_Setting[2].toString());
                                        lst_CIIU = null;
                                    } else {
                                        out.print("Sin codigo CIIU asociado");
                                    }
                                } else {
                                    out.print("Sin dato registrado");
                                }
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div style='display:flex;justify-content:space-evenly'>");
                                out.print("<div class='DivDetail b_color'><b>Código CIIU secundario</b></div><div class='DivDetailDescription'>");
                                if (obj_company[11] != null) {
                                    lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_company[11].toString()));
                                    if (lst_CIIU != null) {
                                        Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                        out.print(obj_CIIU[1]);
                                        lst_CIIU = null;
                                    }
                                    out.print("</div>");
                                    out.print("<div class='DivDetail b_color'><b>ACT económica secundario</b></div><div class='DivDetailDescription'>");
                                    lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_company[11].toString()));
                                    if (lst_CIIU != null) {
                                        Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                        lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                        Object[] obj_Setting = (Object[]) lst_Conf7.get(0);
                                        if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                            out.print("<b style='color:red;'>Riesgo Alto</b>");
                                        } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                            out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                        } else {
                                            out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                        }
                                        LevelCode2 = Integer.parseInt(obj_Setting[2].toString());
                                    } else {
                                        out.print("Sin codigo CIIU asociado");
                                    }
                                } else {
                                    out.print("Sin dato registrado");
                                }
                                out.print("</div>");
                                out.print("</div>");
                            }

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Tipo de persona</b></div><div class='DivDetailDescription'>" + ((obj_company[8] == null) ? "" : obj_company[8]) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Representante Legal</b></div><div class='DivDetailDescription'>" + ((obj_company[13] == null) ? "" : obj_company[13]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Cargo de Desempeña</b></div><div class='DivDetailDescription'>" + ((obj_company[15] == null) ? "" : obj_company[15]) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Operación anuales</b></div><div class='DivDetailDescription'>" + ((obj_company[16] == null) ? "" : obj_company[16]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            NumberFormat format = NumberFormat.getInstance();
                            if (obj_company[17] != null) {
                                vcv = Integer.parseInt(obj_company[17].toString());
                                VlrFormatt = format.format(vcv);
                            } else {
                                vcv = 0;
                            }
                            out.print("<div class='DivDetail b_color'><b>Valor compras y ventas anuales</b></div><div class='DivDetailDescription'>" + ((vcv == 0) ? "" : VlrFormatt) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Antiguedad (AÑOS)</b></div><div class='DivDetailDescription'>" + ((obj_company[18] == null) ? "" : obj_company[18]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Tipo de servicio</b></div><div class='DivDetailDescription'>" + ((obj_company[20] == null) ? "" : obj_company[20]) + "</div>");
                            out.print("<div class='DivDetail b_color'><b>Cadena de suministro</b></div><div class='DivDetailDescription'>" + ((obj_company[22] == null) ? "" : obj_company[22]) + "</div>");
                            out.print("</div>");

                            //</editor-fold>
                            out.print("</div>");

                            out.print("<div class=\"tab-pane fade\" id=\"risk\" role=\"tabpanel\" aria-labelledby=\"risk-tab2\">");
                            //<editor-fold defaultstate="collapsed" desc="ANALITYS RISK LEVEL">
                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetailControl b_color'><b>Calificación OEA. CT - PAT. o avalada por aduana del país</b></div><div class='DivDetailDescription'>" + ((obj_company[23] == null) ? "" : obj_company[23]) + "</div>");
                            out.print("<div class='DivDetailControl b_color'><b>Experencia en el mercardo</b></div><div class='DivDetailDescription'>" + ((obj_company[24] == null) ? "" : obj_company[24]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetailControl b_color'><b>Tiempo de relación cormencial</b></div><div class='DivDetailDescription'>" + ((obj_company[25] == null) ? "" : obj_company[25]) + "</div>");
                            out.print("<div class='DivDetailControl b_color'><b>Información del carga</b></div><div class='DivDetailDescription'>" + ((obj_company[26] == null) ? "N/A" : obj_company[26]) + "</div>");
                            out.print("</div>");
                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetailControl b_color'><b>Puntaje total</b></div><div class='DivDetailDescription'>");
                            lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                            if (lst_Configuration != null) {
                                Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                                if (obj_company[23] != null && obj_company[24] != null && obj_company[25] != null) {
                                    Qualification = Integer.parseInt(obj_company[23].toString());
                                    Experience = Integer.parseInt(obj_company[24].toString());
                                    Relationship = Integer.parseInt(obj_company[25].toString());
                                    String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                    CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                    CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                    CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                    sumT = CalcQ + CalcEx + CalcRel;
                                    out.print(sumT);
                                } else {
                                    out.print("Sin datos");
                                }
                            } else {
                                out.print("Fallo en el calculo");
                            }
                            out.print("</div>");
                            out.print("<div class='DivDetailControl b_color'><b>Nivel de riesgo del negocio</b></div><div class='DivDetailDescription'>");
                            lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                            if (lst_Configuration != null) {
                                Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                                if (obj_company[23] != null && obj_company[24] != null && obj_company[25] != null) {
                                    Qualification = Integer.parseInt(obj_company[23].toString());
                                    Experience = Integer.parseInt(obj_company[24].toString());
                                    Relationship = Integer.parseInt(obj_company[25].toString());
                                    String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                    CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                    CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                    CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                    sumT = CalcQ + CalcEx + CalcRel;
                                    if (sumT >= 6) {
                                        out.print("<b style='color:red;'>Riesgo Alto</b>");
                                    } else if (sumT <= 4) {
                                        out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                    } else {
                                        out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                    }
                                    LevelRisk = sumT;
                                } else {
                                    out.print("Sin datos");
                                }
                            } else {
                                out.print("Fallo en el calculo");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");

                            out.print("<div class=\"tab-pane fade\" id=\"oea\" role=\"tabpanel\" aria-labelledby=\"oea-tab2\">");
                            //<editor-fold defaultstate="collapsed" desc="CONTROL OEA">
                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Aplica acuerdo</b></div><div style='width:18;'>");
                            if (obj_company[23] != null) {
                                Qualification = Integer.parseInt(obj_company[23].toString());
                                if (Qualification == 0) {
                                    out.print("<span style='text-decoration: underline;font-style: italic;'>No aplica acuerdo</span>");
                                } else {
                                    out.print("<span style='text-decoration: underline;font-style: italic;'>Aplica Acuerdo de Seguridad</span>");
                                }
                            } else {
                                out.print("Sin datos");
                            }
                            out.print("</div>");
                            out.print("<div class='DivDetail b_color'><b>Aplica visita</b></div><div class='DivDetailDescription'>");
                            lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                            if (lst_Configuration != null) {
                                Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                                if (obj_company[23] != null && obj_company[24] != null && obj_company[25] != null) {
                                    Qualification = Integer.parseInt(obj_company[23].toString());
                                    Experience = Integer.parseInt(obj_company[24].toString());
                                    Relationship = Integer.parseInt(obj_company[25].toString());
                                    String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                    CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                    CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                    CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                    sumT = CalcQ + CalcEx + CalcRel;
                                    if (sumT >= 6) {
                                        out.print("<b style='color:red;'>VISITAR</b>");
                                    } else {
                                        out.print("<b style='color:gray;'>N/A</b>");
                                    }
                                } else {
                                    out.print("<b style='color:gray;'>SIN DATOS</b>");
                                }
                            } else {
                                out.print("Fallo en el calculo");
                            }
                            out.print("</div>");
                            out.print("</div>");

                            //</editor-fold>
                            out.print("</div>");

                            out.print("<div class=\"tab-pane fade\" id=\"sagrilaf\" role=\"tabpanel\" aria-labelledby=\"sagrilaf-tab2\">");
                            //<editor-fold defaultstate="collapsed" desc="CONTROL SAGRILAFT AND PTEE">
                            out.print("<div style='display:flex;justify-content:space-evenly'>");
                            out.print("<div class='DivDetail b_color'><b>Segmentación</b></div><div class='DivDetailDescription'>");
                            if (LevelCode1 >= 6 && LevelCode2 >= 6 && LevelRisk >= 6 && obj_company[21].equals("SI")) {
                                out.print("<b style='color:red'>Riesgo Alto</b>");
                            } else {
                                out.print("<b style='color:green;'>Riesgo Bajo</b>");
                            }
                            out.print("</div>");
                            out.print("<div class='DivDetail b_color'><b>Días</b></div><div class='DivDetailDescription'>" + ((obj_company[34] == null) ? "" : obj_company[34]) + "</div>");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content:space-evenly'>");

                            out.print("<div class='DivDetail b_color'><b>Debida diligencia</b></div><div class='DivDetailDescription'>");
                            if (obj_company[21] != null || LevelRisk > 0) {
                                if (obj_company[21].equals("SI") || LevelRisk >= 6) {
                                    out.print("<b style='color:#e15f00'>Intensificada</b>");
                                } else {
                                    out.print("<b>Normal</b>");
                                }
                            }
                            out.print("</div>");

                            out.print("<div class='DivDetail b_color'><b>Estado vinculantes</b></div><div class='DivDetailDescription'>");
                            if (obj_company[34] != null) {
                                int days = Integer.parseInt(obj_company[34].toString());
                                if (days < 300) {
                                    out.print("<b style='color:green;'>Vigente</b>");
                                } else if (days < 365) {
                                    out.print("<b style='color:orange;'>Proximo a vencer</b>");
                                } else {
                                    out.print("<b style='color:red;'>Vencido</b>");
                                }
                            } else {
                                out.print("<b style='color:gray;'>SIN DATOS</b>");
                            }
                            out.print("</div>");
                            out.print("</div>");

                            //</editor-fold>
                            out.print("</div>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</span></div>");
                        }
                    } else {
                        out.print("<div><b>Cant registro:</b> 0</div>");
                        out.print("<h6>Sin historial</h6>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="SEARCH FILTER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contFilterSegmentation'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Filtro de busqueda</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<form action='Segmentation?opt=7' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='Temp' value='1'>");
                out.print("<input type='hidden' name='Report' value='1'>");
                out.print("<div style='display:flex; justify-content: space-between'>");

                out.print("<div class='DivFilter'>");
                out.print("<h5 class='mt-2'>Estado:</h5>");
                out.print("<div class='selectgroup m-2' >");
                out.print("<label class=\"selectgroup-item\" >");
                out.print("<input type=\"radio\" name=\"State\" value=\"2\" class=\"selectgroup-input\" checked>");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Todas</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\">");
                out.print("<input type=\"radio\" name=\"State\" value=\"1\" class=\"selectgroup-input\" >");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Abiertas</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\">");
                out.print("<input type=\"radio\" name=\"State\" value=\"0\" class=\"selectgroup-input\">");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Cerradas</span>");
                out.print("</label>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='DivFilter'>");
                out.print("<h5 class='mt-2'>Vigencia:</h5>");
                out.print("<div class='selectgroup m-2' >");
                out.print("<label class=\"selectgroup-item\" >");
                out.print("<input type=\"radio\" name=\"Validity\" value=\"1\" class=\"selectgroup-input\" >");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Vigente</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\">");
                out.print("<input type=\"radio\" name=\"Validity\" value=\"2\" class=\"selectgroup-input\" >");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Proxima a vencer</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\">");
                out.print("<input type=\"radio\" name=\"Validity\" value=\"3\" class=\"selectgroup-input\">");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Vencido</span>");
                out.print("</label>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div class='mt-2' style='display:flex; justify-content: space-between'>");

                out.print("<div class='DivFilter'>");
                out.print("<h5 class='mt-2 mb-2'>Texto:</h5>");
                out.print("<div style='width:93%;' class='ml-2'><input type='text' name='TextFilter' class='form-control' placeholder='Texto a filtrar'  data-toggle='tooltip' data-placement='top' title='Texto a filtrar'></div>");
                out.print("</div>");
                out.print("<div class='DivFilter'>");
                out.print("<h5 class='mt-2'>Fecha Monitoreo:</h5>");
                out.print("<div class='mb-2' style='display:flex;justify-content:space-around'>"
                        + "<div style='width:44%'>"
                        + "<input class='form-control' type=\"text\" name='InitialDate' placeholder='Fecha Inicio' id=\"InitialDate\" autocomplete='off' data-toggle='tooltip' data-placement='top' title='Fecha Inicio'></div>"
                        + "<div style='width:44%'> "
                        + "<input class='form-control' type=\"text\" name='EndDate' placeholder='Fecha Fin' id=\"EndDate\"  autocomplete='off' data-toggle='tooltip' data-placement='top' title='Fecha Fin'></div></div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='DivFilter mt-2 ml-6' style='margin-left: 158px;'>");
                out.print("<h5 class='mt-2'>Registro:</h5>");
                out.print("<div class='selectgroup m-2' >");
                out.print("<label class=\"selectgroup-item\" >");
                out.print("<input type=\"radio\" name=\"Format\" value=\"TODAS\" class=\"selectgroup-input\" checked>");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Todas</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\" >");
                out.print("<input type=\"radio\" name=\"Format\" value=\"NATIONAL\" class=\"selectgroup-input\">");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Nacional</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\">");
                out.print("<input type=\"radio\" name=\"Format\" value=\"INTERNATIONAL\" class=\"selectgroup-input\" >");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Internacional</span>");
                out.print("</label>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-blue btn-lg'>Consultar</button>");
                out.print("</div>");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                if (ConsultQuery.equals("")) {
                    lst_segmentation = SegmentatioJpa.ConsultSegmentationReport();
                } else {
                    lst_segmentation = SegmentatioJpa.ConsultQueryMysql(ConsultQuery);
                }
                out.print("<div class='card-header' style='display:flex;justify-content:space-between'>");
                out.print("<div><h6>Contrapartes</h6></div>");
                if (!ConsultQuery.equals("")) {
                    out.print("<div style='display: flex;' >");
                    out.print("<div class='mr-2'><button style='color:white;border-radius:5px;'  onclick=\"window.location.href='Segmentation?opt=6&Report=1'\"  class=\"btn btn-danger btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Cancelar \"><i class=\"fas fa-times\"></i></button></div>");
                    out.print("<div><button style='color:white;border-radius:5px;'  onclick='mostrarConvencion(2)'  class=\"btn btn-blue btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Filtrar \"><i class=\"fas fa-search\"></i></button></div>");
                    out.print("</div>");
                } else {
                    out.print("<div><button style='color:white;border-radius:5px;'  onclick='mostrarConvencion(2)'  class=\"btn btn-blue btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Filtrar \"><i class=\"fas fa-search\"></i></button></div>");
                }
                out.print("</div>");
                if (lst_segmentation != null) {
                    for (int i = 0; i < lst_segmentation.size(); i++) {
                        Object[] obj_segmetantion = (Object[]) lst_segmentation.get(i);
                        Random random = new Random();
                        int r = random.nextInt(256);
                        int g = random.nextInt(256);
                        int b = random.nextInt(256);
                        if (i % 4 == 0) {
                            out.print("<div class='DivFlex'>");
                        }
                        out.print("<div class='DivArticle' style='border:1px solid rgb(" + r + ", " + g + ", " + b + ");'>");
                        out.print("<article style='margin-bottom:0px !important;height:100%; background:transparent;' class='article article-style-b'>");
                        out.print("<div class='BtnPS'>");
                        if (obj_segmetantion[34] != null) {
                            int days = Integer.parseInt(obj_segmetantion[34].toString());
                            if (days < 300) {
                                out.print("<buttom type='buttom' class='btn btn-success btn-sm' >Vigente</buttom>");
                            } else if (days < 365) {
                                out.print("<buttom type='buttom' class='btn btn-warning btn-sm'>Proximo a vencer</buttom>");
                            } else {
                                out.print("<buttom type='buttom' class='btn btn-danger btn-sm'>Vencido</buttom>");
                            }
                        } else {
                            out.print("<buttom class='btn btn-info btn-sm'>Sin definir</buttom>");
                        }
                        out.print("</div>");
                        out.print("<div class='article-details' style='background:transparent;'>");
                        out.print("<div class='article-title'>");
                        out.print("<h6 style='color:#002237'>" + ((obj_segmetantion[7] == null) ? "Sin nombre" : obj_segmetantion[7]) + "</h6>");
                        out.print("</div><hr style='margin-top:7px;'>");
                        out.print("<div class='DivHG'>");
                        out.print("<i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title='" + (obj_segmetantion[32].equals("NATIONAL") ? "NIT/ID" : "TAX/ID") + "'>" + ((obj_segmetantion[6] == null) ? "Sin NIT" : (obj_segmetantion[6].equals("") ? "Sin NIT" : obj_segmetantion[6])) + "</b></br>");
                        out.print("<i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Representante Legal \">" + ((obj_segmetantion[13] == null) ? "Sin Representante" : obj_segmetantion[13]) + "</b><br>");
                        out.print("<i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Tipo Negocio \">" + ((obj_segmetantion[5] == null) ? "Sin tipo negocio" : obj_segmetantion[5]) + "</b><br>");
                        out.print("<i class=\"fas fa-check\" style='color:#002237;'></i> <b data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Fecha Monitoreo \">" + (obj_segmetantion[19] == null ? "Sin fecha definida" : obj_segmetantion[19].equals("") ? "Sin fecha definida" : obj_segmetantion[19]) + "</b>");
                        out.print("</div>");
                        out.print("<div class='article-cta BottomDown'>");
                        out.print("<a href='Segmentation?opt=6&Report=1&BusinessAssociate=" + obj_segmetantion[7] + "&idUserCli="+ obj_segmetantion[38] +"' class='btn btn-blue'>Consultar <i class='fas fa-chevron-right'></i></a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</article>");
                        out.print("</div>");

                        if ((i + 1) % 4 == 0 || i == lst_segmentation.size() - 1) {
                            out.print("</div>");
                        }

                    }
                } else {
                    out.print("<div style='text-align:center;' class='mb-5 mt-4'><h2>No existen datos</h2><br/><i style='font-size:75px;' class=\"fas fa-sad-tear\"></i></div>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="R-SEG-SGLT-003">
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>R-SEG-SGLT-003 - " + ((Format.equals("NATIONAL")) ? "NACIONAL" : "INTERNACIONAL") + "</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");

                out.print("<div class='card-header' style='display:flex;justify-content:space-between'>");
                out.print("<div style='display:flex;justify-content:space-evenly'>"
                        + "<div class='mr-2'><button class='btn btn-" + ((Format.equals("NATIONAL")) ? "blue" : "outline-blue") + "'  style='border-radius:4px;' onclick=\"window.location.href='Segmentation?opt=6&Format=NATIONAL'\" data-toggle='tooltip' data-placement='top' title='Consultar Formato Nacional'><i class='fas fa-map-marker-alt'></i></button></div>");
                out.print("<div><button class='btn btn-" + ((Format.equals("INTERNATIONAL")) ? "blue" : "outline-blue") + "' style='border-radius:4px;' onclick=\"window.location.href='Segmentation?opt=6&Format=INTERNATIONAL'\" data-toggle='tooltip' data-placement='top' title='Consultar Formato Internacional'><i class='fas fa-globe-americas'></i></button></div>"
                        + "</div>");

                out.print("<div style='display:flex;justify-content:space-evenly'>");
                out.print("<div><button class='btn btn-success' style='color:black;border-radius:4px;' onclick=\"exportToExcel()\" data-toggle='tooltip' data-placement='top' title='Exportar a Excel'><i class='fas fa-file-excel'></i></button></div>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class='card-body scrollbar' style='overflow-y: scroll;height: 450px;'>");
                out.print("<table class='tableSGLT table-hover' id='TableStyle'>");
                out.print("<thead>");
                //<editor-fold defaultstate="collapsed" desc="HEAD REGISTER">
                out.print("<tr>");
                out.print("<tr><td colspan='36' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td class='Borderhead' align='center' colspan='12' rowspan='2'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                out.print("<td class='Borderhead' rowspan='2' colspan='12'><h6 style='text-align: center;'>MODELO DE PERFILAMIENTO Y SEGMENTACION</h6></td>");
                out.print("<td class='Borderhead' align='center' colspan='12'><h6 style='text-align: center;'>CODIGO <b style='black;'>R-SEG-SGLT-003</b></h6></td>");
                out.print("<tr>");
                out.print("<td class='Borderhead' colspan='12' align='center'><h6 style='text-align: center;'>VERSION <b style='black;'>000</b> </td>");
                out.print("</tr>");
                out.print("<tr><td></td></tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="HEAD TABLE">
                out.print("<tr class='Std'>");
                out.print("<td rowspan='4' class='StickyVTH'>CÓDIGO PLASTITEC</td>");
                out.print("<td rowspan='4' class='StickyVTH1'>FECHA DE REGISTRO</td>");
                out.print("<td rowspan='4' class='" + ((Format.equals("NATIONAL")) ? "StickyVTH2" : "StickyVTH2INT") + "'>" + ((Format.equals("NATIONAL")) ? "ÁREA USUARIA RESPONSABLE DEL NEGOCIO" : "AREA USUARIA RESPONSABLE DEL ASOCIADO DE NEGOCIO") + "</td>");
                out.print("<td rowspan='2' class='Sticky10' colspan='" + ((Format.equals("NATIONAL")) ? "21" : "17") + "'>INFORMACIÓN BASICA</td>");
                out.print("<td colspan='" + ((Format.equals("NATIONAL")) ? "6" : "9") + "' class='Sticky11'>" + ((Format.equals("NATIONAL")) ? "ANÁLISIS DE NIVEL DE RIESGO" : "NIVEL ANÁLISIS DE NIVEL DE RIESGO") + "</td>");
                out.print("<td rowspan='2' class='Sticky7' colspan='2'>CONTROLES OEA</td>");
                out.print("<td rowspan='2' class='Sticky7' colspan='4'>CONTROLES SAGRILAFT Y PTEE</td>");
                out.print("</tr>");

                out.print("<tr class='Std'>");
                out.print("<td " + ((Format.equals("NATIONAL")) ? "" : "colspan='3'") + " class='Sticky4'>ANÁLISIS DE NIVEL DE RIESGO PAÍS</td>");
                if (Format.equals("INTERNATIONAL")) {
                    out.print("<td class='Sticky4'>CONTROLES DE SEGURIDAD</td>");
                }
                out.print("<td colspan='2'  class='Sticky4'>TRAYECTORIA DEL CLIENTE/PROVEEDOR</td>");
                out.print("<td rowspan='2'  class='Sticky4'>CONTACTO CON LA CARGA O INFORMACIÓN DE LA CARGA</td>");
                out.print("<td rowspan='2'  class='Sticky4'>PUNTAJE TOTAL</td>");
                out.print("<td rowspan='3'  class='Sticky4'>NIVEL DE RIESGO DEL ASOCIADO DE NEGOCIO</td>");
                out.print("</tr>");

                out.print("<tr class='Std'>");
                out.print("<td rowspan='2' class='" + ((Format.equals("NATIONAL")) ? "StickyVTH3" : "StickyVTH3INT") + "'>TIPO</td>");
                out.print("<td rowspan='2' class='" + ((Format.equals("NATIONAL")) ? "StickyVTH4" : "StickyVTH4INT") + "'>NOMBRE DEL CLIENTE / PROVEEDOR</td>");
                out.print("<td rowspan='2' class='" + ((Format.equals("NATIONAL")) ? "StickyVTH5" : "StickyVTH5INT") + "'>" + ((Format.equals("NATIONAL")) ? "NIT / ID" : "TAX ID") + "</td>");
                out.print("<td rowspan='2' class='Sticky3'>TIPO DE PERSONA</td>");

                out.print("<td rowspan='2' class='Sticky3'>CONOCIMIENTO DEL BENEFICIARIO FINAL</td>");
                if (Format.equals("NATIONAL")) {
                    out.print("<td rowspan='2' class='Sticky3'>CODIGO CIIU PRINCIPAL</td>");
                    out.print("<td rowspan='2' class='Sticky3'>CODIGO CIIU SECUNDARIO</td>");
                    out.print("<td rowspan='2' class='Sticky3'>ACTIVIDAD ECONÓMICA PRIMARIA DE ALTO RIESGO</td>");
                    out.print("<td rowspan='2' class='Sticky3'>ACTIVIDAD ECONÓMICA  SECUNDARIA DE ALTO RIESGO</td>");
                }
                out.print("<td rowspan='2' class='Sticky3'>DOMICILIO DEL BENEFICIARIO</td>");
                out.print("<td rowspan='2' class='Sticky3'>NOMBRE DEL REPRESENTANTE LEGAL (SI SE OBTIENE LA INFORMACIÓN)</td>");
                out.print("<td rowspan='2' class='Sticky3'>NOMBRE DE LA PERSONA DEL CONTACTO</td>");
                out.print("<td rowspan='2' class='Sticky3'>CARGO QUE DESEMPEÑA</td>");
                out.print("<td rowspan='2' class='Sticky3'>FRECUENCIA DE OPERACIONES ANUALES</td>");
                out.print("<td rowspan='2' class='Sticky3'>VALOR COMPRAS/ VENTAS ANUALES APROX (MILLONES)</td>");
                out.print("<td rowspan='2' class='Sticky3'>ANTIGÜEDAD (AÑOS)</td>");
                out.print("<td rowspan='2' class='Sticky3'>FECHA DE ÚLTIMO MONITOREO</td>");
                out.print("<td rowspan='2' class='Sticky3'>TIPO DE SERVICIO O PRODUCTO COMPRADO U OFRECIDO</td>");
                out.print("<td rowspan='2' class='Sticky3'>SE REPORTA PERSONA EXPUESTA POLÍTICAMENTE (PEP)</td>");
                out.print("<td rowspan='2' class='Sticky3'>ASOCIADO DE NEGOCIO PARA LA CADENA DE SUMINISTRO</td>");
                out.print("<td rowspan='2' class='Sticky3'>TIPO DE DOCUMENTACIÓN SOLICITADA </td>");
                if (Format.equals("INTERNATIONAL")) {
                    out.print("<td class='Sticky5'>ÍNDICE AML DE BASILEA (LAFT)</td>");
                    out.print("<td class='Sticky5'>ÍNDICE DE PERCEPCIÓN DE LA CORRUPCIÓN</td>");
                    out.print("<td class='Sticky5'>ÍNDICE GLOBAL DE SOBORNO</td>");
                }
                out.print("<td class='Sticky5'>CALIFICACIÓN COMO OEA, CT- PAT, O AVALADA POR ADUANA DEL PAÍS</td>");
                out.print("<td class='Sticky5'>EXPERIENCIA EN EL MERCADO</td>");
                out.print("<td class='Sticky5'>TIEMPO DE RELACIÓN COMERCIAL</td>");
                out.print("<td rowspan='2' class='Sticky9'>APLICA ACUERDO</td>");
                out.print("<td rowspan='2' class='Sticky9'>APLICA VISITA</td>");
                out.print("<td rowspan='2' class='Sticky9'>SEGMENTACIÓN</td>");
                out.print("<td rowspan='2' class='Sticky9'>DEBIDA DILIGENCIA</td>");
                out.print("<td rowspan='2' class='Sticky9'>DÍAS</td>");
                out.print("<td rowspan='2' class='Sticky9'>ESTADO DE VERIFICACIÓN EN LISTAS VINCULANTES</td>");
                out.print("</tr>");

                out.print("<tr class='Std'>");
                if (Format.equals("INTERNATIONAL")) {
                    out.print("<td class='Sticky6'>10%</td>");
                    out.print("<td class='Sticky6'>10%</td>");
                    out.print("<td class='Sticky6'>10%</td>");
                }
                out.print("<td class='Sticky6'>30%</td>");
                out.print("<td class='Sticky6'>20%</td>");
                out.print("<td class='Sticky6'>20%</td>");
                out.print("<td class='Sticky8'>30%</td>");
                out.print("<td class='Sticky8'>100%</td>");
                out.print("</tr>");
                //</editor-fold>
                out.print("</thead>");
                out.print("<tbody>");
                lst_segmentation = SegmentatioJpa.ConsultSegmentation(Format);
                if (lst_segmentation != null) {
                    for (int i = 0; i < lst_segmentation.size(); i++) {
                        //<editor-fold defaultstate="collapsed" desc="CONTENT">
                        Object[] obj_segmentation = (Object[]) lst_segmentation.get(i);
                        out.print("<tr class='hoverable'>");
                        out.print("<td class='StickyVTB SHover'>" + ((obj_segmentation[2] == null) ? "" : obj_segmentation[2]) + "</td>");
                        out.print("<td class='" + ((Format.equals("NATIONAL")) ? "StickyVTB2" : "StickyVTB2INT") + " SHover'>" + ((obj_segmentation[3] == null) ? "" : obj_segmentation[3]) + "</td>");
                        out.print("<td class='" + ((Format.equals("NATIONAL")) ? "StickyVTB3" : "StickyVTB3INT") + " SHover'>" + ((obj_segmentation[4] == null) ? "" : obj_segmentation[4]) + "</td>");
                        out.print("<td class='" + ((Format.equals("NATIONAL")) ? "StickyVTB4" : "StickyVTB4INT") + " SHover'>" + ((obj_segmentation[5] == null) ? "" : obj_segmentation[5]) + "</td>");
                        out.print("<td class='" + ((Format.equals("NATIONAL")) ? "StickyVTB5" : "StickyVTB5INT") + " SHover'>" + ((obj_segmentation[7] == null) ? "" : obj_segmentation[7]) + "</td>");
                        out.print("<td class='" + ((Format.equals("NATIONAL")) ? "StickyVTB6" : "StickyVTB6INT") + " SHover'>" + ((obj_segmentation[6] == null) ? "" : obj_segmentation[6]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[8] == null) ? "" : obj_segmentation[8]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[9] == null) ? "" : obj_segmentation[9]) + "</td>");
                        if (Format.equals("NATIONAL")) {
                            out.print("<td class='TdBorder'>" + ((obj_segmentation[35] == null) ? "" : obj_segmentation[35]) + "</td>");
                            out.print("<td class='TdBorder'>" + ((obj_segmentation[36] == null) ? "" : obj_segmentation[36]) + "</td>");
                            out.print("<td class='TdBorder'>");
                            //<editor-fold defaultstate="collapsed" desc="ACT PRIMARY">
                            if (obj_segmentation[10] != null) {
                                lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_segmentation[10].toString()));
                                if (lst_CIIU != null) {
                                    Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                    lst_Conf = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                    Object[] obj_Setting = (Object[]) lst_Conf.get(0);
                                    if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                        out.print("<b style='color:red;'>Riesgo Alto</b>");
                                    } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                        out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                    } else {
                                        out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                    }
                                    LevelCode1 = Integer.parseInt(obj_Setting[2].toString());
                                    lst_CIIU = null;
                                } else {
                                    out.print("Sin codigo CIIU asociado");
                                }
                            } else {
                                out.print("Sin dato registrado");
                            }
                            //</editor-fold>
                            out.print("</td>");
                            out.print("<td class='TdBorder'>");
                            //<editor-fold defaultstate="collapsed" desc="ACT SECUNDARY">
                            if (obj_segmentation[11] != null) {
                                lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_segmentation[11].toString()));
                                if (lst_CIIU != null) {
                                    Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                    lst_Conf = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                    Object[] obj_Setting = (Object[]) lst_Conf.get(0);
                                    if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                        out.print("<b style='color:red;'>Riesgo Alto</b>");
                                    } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                        out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                    } else {
                                        out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                    }
                                    LevelCode2 = Integer.parseInt(obj_Setting[2].toString());
                                } else {
                                    out.print("Sin codigo CIIU asociado");
                                }
                            } else {
                                out.print("Sin dato registrado");
                            }
                            //</editor-fold>
                            out.print("</td>");
                        }
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[12] == null) ? "" : obj_segmentation[12]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[13] == null) ? "" : obj_segmentation[13]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[14] == null) ? "" : obj_segmentation[14]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[15] == null) ? "" : obj_segmentation[15]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[16] == null) ? "" : obj_segmentation[16]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[17] == null) ? "" : obj_segmentation[17]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[18] == null) ? "" : obj_segmentation[18]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[19] == null) ? "" : obj_segmentation[19]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[20] == null) ? "" : obj_segmentation[20]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[21] == null) ? "" : obj_segmentation[21]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[22] == null) ? "" : obj_segmentation[22]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[17] == null) ? "BASICO" : (Integer.parseInt(obj_segmentation[17].toString()) >= 63800000) ? "DETALLADO" : "BASICO") + "</td>");
                        if (Format.equals("INTERNATIONAL")) {
                            out.print("<td class='TdBorder'>" + ((obj_segmentation[27] == null) ? "" : obj_segmentation[27]) + "</td>");
                            out.print("<td class='TdBorder'>" + ((obj_segmentation[28] == null) ? "" : obj_segmentation[28]) + "</td>");
                            out.print("<td class='TdBorder'>" + ((obj_segmentation[29] == null) ? "" : obj_segmentation[29]) + "</td>");
                        }
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[23] == null) ? "" : obj_segmentation[23]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[24] == null) ? "" : obj_segmentation[24]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[25] == null) ? "" : obj_segmentation[25]) + "</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[26] == null) ? "" : obj_segmentation[26]) + "</td>");
                        //<editor-fold defaultstate="collapsed" desc="TOTAL POINT">
                        lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                        if (lst_Configuration != null) {
                            Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                            if (obj_segmentation[23] != null && obj_segmentation[24] != null && obj_segmentation[25] != null) {
                                Qualification = Integer.parseInt(obj_segmentation[23].toString());
                                Experience = Integer.parseInt(obj_segmentation[24].toString());
                                Relationship = Integer.parseInt(obj_segmentation[25].toString());
                                String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<td class='TdBorder' style='text-align:center;' >");
                                CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                sumT = CalcQ + CalcEx + CalcRel;
                                sumT = Math.round(sumT * 1000.0) / 1000.0;
                                out.print(sumT);
                                out.print("</td>");
                            } else {
                                out.print("<td class='TdBorder' style='text-align:center;'>Fallo en el calculo</td>");
                            }
                        } else {
                            out.print("<td class='TdBorder' style='text-align:center;'>Fallo en el calculo</td>");
                        }
                        //</editor-fold>
                        out.print("<td class='TdBorder'>");
                        //<editor-fold defaultstate="collapsed" desc="LEVE RISK TOTAL">
                        lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                        if (lst_Configuration != null) {
                            Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                            if (obj_segmentation[23] != null && obj_segmentation[24] != null && obj_segmentation[25] != null) {
                                Qualification = Integer.parseInt(obj_segmentation[23].toString());
                                Experience = Integer.parseInt(obj_segmentation[24].toString());
                                Relationship = Integer.parseInt(obj_segmentation[25].toString());
                                String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                sumT = CalcQ + CalcEx + CalcRel;
                                if (sumT >= 6) {
                                    out.print("<b style='color:red;'>Riesgo Alto</b>");
                                } else if (sumT <= 4) {
                                    out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                }
                                LevelRisk = sumT;
                            } else {
                                out.print("Sin datos");
                            }
                        } else {
                            out.print("Fallo en el calculo");
                        }
                        //</editor-fold>
                        out.print("</td>");
                        out.print("<td class='TdBorder'>");
                        //<editor-fold defaultstate="collapsed" desc="AGREEMENT">
                        if (obj_segmentation[23] != null) {
                            Qualification = Integer.parseInt(obj_segmentation[23].toString());
                            if (Qualification == 0) {
                                out.print("<span style='text-decoration: underline;font-style: italic;'>No aplica acuerdo</span>");
                            } else {
                                out.print("<span style='text-decoration: underline;font-style: italic;'>Aplica Acuerdo de Seguridad</span>");
                            }
                        } else {
                            out.print("Sin datos");
                        }
                        //</editor-fold>
                        out.print("</td>");
                        out.print("<td class='TdBorder'>");
                        //<editor-fold defaultstate="collapsed" desc="VISIT">
                        lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                        if (lst_Configuration != null) {
                            Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                            if (obj_segmentation[23] != null && obj_segmentation[24] != null && obj_segmentation[25] != null) {
                                Qualification = Integer.parseInt(obj_segmentation[23].toString());
                                Experience = Integer.parseInt(obj_segmentation[24].toString());
                                Relationship = Integer.parseInt(obj_segmentation[25].toString());
                                String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                sumT = CalcQ + CalcEx + CalcRel;
                                if (sumT >= 6) {
                                    out.print("<b style='color:red;'>VISITAR</b>");
                                } else {
                                    out.print("<b style='color:gray;'>N/A</b>");
                                }
                            } else {
                                out.print("<b style='color:gray;'>SIN DATOS</b>");
                            }
                        } else {
                            out.print("Fallo en el calculo");
                        }
                        //</editor-fold>
                        out.print("</td>");
                        out.print("<td class='TdBorder'>");
                        if (LevelCode1 >= 6 && LevelCode2 >= 6 && LevelRisk >= 6 && obj_segmentation[21].equals("SI")) {
                            out.print("<b style='color:red'>Riesgo Alto</b>");
                        } else {
                            out.print("<b style='color:green;'>Riesgo Bajo</b>");
                        }
                        out.print("</td>");
                        out.print("<td class='TdBorder'>");
                        if (obj_segmentation[21] != null || LevelRisk > 0) {
                            if (obj_segmentation[21].equals("SI") || LevelRisk >= 6) {
                                out.print("<b style='color:#e15f00'>Intensificada</b>");
                            } else {
                                out.print("<b>Normal</b>");
                            }
                        }
                        out.print("</td>");
                        out.print("<td class='TdBorder'>" + ((obj_segmentation[34] == null) ? "" : obj_segmentation[34]) + "</td>");
                        out.print("<td class='TdBorder'>");
                        if (obj_segmentation[34] != null) {
                            int days = Integer.parseInt(obj_segmentation[34].toString());
                            if (days < 300) {
                                out.print("<b style='color:green;'>Vigente</b>");
                            } else if (days < 365) {
                                out.print("<b style='color:orange;'>Proximo a vencer</b>");
                            } else {
                                out.print("<b style='color:red;'>Vencido</b>");
                            }
                        } else {
                            out.print("<b style='color:gray;'>SIN DATOS</b>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                        //</editor-fold>
                    }
                }
                out.print("</tbody>");
                out.print("</table>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }

        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Tag_Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
