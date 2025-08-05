package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.ComputerInformationControllerJpa;
import java.util.List;
import Controller.SettingControllerJpa;
import Controller.RoleControllerJpa;

public class Tag_information extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        ComputerInformationControllerJpa InformationJpa = new ComputerInformationControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_information = null, lst_Id_information = null;
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        int State = 0, IdInformation = 0, StateInf = 0, Counter = 0, idRol = 0;
        String TypeAct = "", CantAct = "";
        String txtPermissions = "";
        List lst_setting = null, lst_role = null, view_antivirus = null, view_red = null, view_state = null, view_warranty = null, view_process = null, view_type = null;
        try {
            try {
                idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                lst_role = RoleJpa.ConsultRoleId(idRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                txtPermissions = obj_permi[2].toString();
            } catch (Exception e) {
                idRol = 0;
                txtPermissions = "";
            }
            try {
                IdInformation = Integer.parseInt(pageContext.getRequest().getAttribute("IdInformation").toString());
            } catch (NumberFormatException e) {
                IdInformation = 0;
            }
            try {
                State = Integer.parseInt(pageContext.getRequest().getAttribute("State").toString());
            } catch (NumberFormatException e) {
                State = 0;
            }
            try {
                Counter = Integer.parseInt(pageContext.getRequest().getAttribute("Counter").toString());
            } catch (NumberFormatException e) {
                Counter = 0;
            }
            if (IdInformation > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE DETAIL">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Actualizar Especificaciones</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<form action='Information?opt=2' method='post' name='FormInfo' id='FormInfo' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='IdInformation' value='" + IdInformation + "'>");
                lst_Id_information = InformationJpa.ConsultInformationId(IdInformation);
                if (lst_Id_information != null) {
                    Object[] obj_informationId = (Object[]) lst_Id_information.get(0);
                    //<editor-fold defaultstate="collapsed" desc="SECTION 1">
                    out.print("<div class='mb-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

                    out.print("<div class='col-6'>");
                    out.print("<b>Correo externo</b><br/>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_mail' id='Txt_mail' title='Correo Externo' required value='" + obj_informationId[19] + "' autocomplete='off' >");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                    out.print("</div>");

                    out.print("<div class='col-6'>");
                    out.print("<b>Factura</b><br/>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_bill' id='Txt_bill' title='Factura' value='" + obj_informationId[20] + "' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="SECTION 2">
                    out.print("<div class='mt-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

                    out.print("<div class='col-6'>");
                    out.print("<b>Fecha Factura</b><br/>");
                    out.print("<input type='date' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_informationId[21] + "' name='Txt_date_bill' id='Txt_date_bill' title='Fecha Factura' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-6' id='DivCat1'>");
                    out.print("<b>Fecha Licencia</b><br/>");
                    out.print("<input type='date' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_informationId[23] + "' name='Txt_date_licence' id='Txt_date_licence' title='Fecha Licencia' required autocomplete='off' >");
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un cargo.</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="SECTION 3">
                    out.print("<div class='mt-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

                    out.print("<div class='col-6'>");
                    out.print("<b>Garantia</b><br/>");
                    out.print("<select class='form-control' name='Txt_warranty' id='' required>");
                    out.print("<option selected value='" + obj_informationId[25] + "'>" + obj_informationId[25] + "</option>");
                    String[] ArgWarranty = {"SI", "NO"};
                    for (int i = 0; i < ArgWarranty.length; i++) {
                        if (!obj_informationId[25].equals(ArgWarranty[i])) {
                            out.print("<option value='" + ArgWarranty[i] + "'>" + ArgWarranty[i] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-6' id='DivCat1'>");
                    out.print("<b>Fecha Garantia</b><br/>");
                    out.print("<input type='date' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_informationId[26] + "' name='Txt_date_warranty' id='Txt_date_warranty' title='Fecha Garantia' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un cargo.</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="SECTION 4">
                    out.print("<div class='mt-2' style='display: flex;justify-content: space-around;  align-items: baseline;'>");
//
                    out.print("<div class='col-6'>");
                    out.print("<b>SKYPE</b><br/>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_informationId[16] + "' name='Txt_skype' id='Txt_skype' title='Skype' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-6' id='DivCat1' data-toggle='tooltip' data-placemente='top' title='VLAN'>");
                    out.print("<b>VLAN</b><br/>");
                    out.print("<select class='form-control' name='Txt_vlan' id='Select1' required>");
                    out.print("<option selected value='" + obj_informationId[10] + "'>" + obj_informationId[10] + "</option>");
                    String[] ArgVlan = {"ADMINISTRATIVO", "PROCESO", "SERVIDOR"};
                    for (int i = 0; i < ArgVlan.length; i++) {
                        out.print("<option value='" + ArgVlan[i] + "'>" + ArgVlan[i] + "</option>");
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un cargo.</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="SECTION 5">
                    out.print("<div class='mt-2 mb-4' style='display: flex;justify-content: space-around;  align-items: baseline;'>");
                    out.print("<div class='col-6'>");
                    out.print("<b>Punto de Red</b><br/>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_informationId[29] + "' name='Txt_network_point' id='Txt_network_point' title='Punto de Red' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-6' id='DivCat1'>");
                    out.print("<b>Especificaciones</b><br/>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_informationId[30] + "' name='Txt_desc' id='Txt_desc' title='Especificaciones' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un cargo.</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg' >Actualizar</button>");
                    out.print("</div>");
                }

                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (Counter > 0) {
                //<editor-fold defaultstate="collapsed" desc="ACCOUNTANTS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width:80%;     padding: 20px 47px 18px 16px;'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Estadisticas</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='col-lg-12 d-flex'>");
                out.print("<div class='col-lg-4 mr-3 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                view_antivirus = InformationJpa.ViewAntivirus();
                if (view_antivirus != null) {
                    //<editor-fold defaultstate="collapsed" desc="ANTIVIRUS">
                    out.print("<h4 class='mb-2 mt-4 text-center'>Antivirus</h4>");
                    out.print("<canvas id=\"myChart2\" width=\"400\" height=\"230\"></canvas>");
                    String labels = "";
                    String dataSi = "", dataNo = "";
                    for (int i = 0; i < view_antivirus.size(); i++) {
                        Object[] ObjAct = (Object[]) view_antivirus.get(i);
                        String Type = ObjAct[0].toString();
                        String Cant = ObjAct[1].toString();

                        labels += "'" + Type + "', ";

                        if (Type.equals("SI")) {
                            dataSi = Cant;
                        } else if (Type.equals("NO")) {
                            dataNo = Cant;
                        }
                    }

                    if (!labels.isEmpty()) {
                        labels = labels.substring(0, labels.length() - 2);
                    }
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'bar', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labels + "], ");  // Etiquetas "SI", "NO", "N/A"
                    out.print("    datasets: [ ");
                    out.print("      { ");
                    out.print("        label: 'SI', ");
                    out.print("        data: [" + dataSi + ", 0], "); // Valores correctos
                    out.print("        backgroundColor: '#443104', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("      { ");
                    out.print("        label: 'NO', ");
                    out.print("        data: [0, " + dataNo + "], ");
                    out.print("        backgroundColor: '#74590f', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("    ] ");
                    out.print("  }, ");
                    out.print("  options: { ");
                    out.print("    legend: { display: true, position: 'bottom', labels: { fontColor: '#333', fontSize: 12 } }, ");
                    out.print("    scales: { ");
                    out.print("      yAxes: [{ gridLines: { drawBorder: false, color: '#f2f2f2' }, ticks: { beginAtZero: true, stepSize: 50 } }], ");
                    out.print("      xAxes: [{ ticks: { autoSkip: false }, gridLines: { display: false } }] ");
                    out.print("    } ");
                    out.print("  } ");
                    out.print("});");
                    out.print("</script>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("<div class='col-lg-4 mr-3 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                view_red = InformationJpa.ViewRed();
                if (view_red != null) {
                    //<editor-fold defaultstate="collapsed" desc="RED">
                    out.print("<h4 class='mb-2 mt-4 text-center'>RED</h4>");
                    out.print("<canvas id=\"myChart3\" width=\"400\" height=\"230\"></canvas>");
                    String labels = "";
                    String dataSi = "", dataNo = "", dataNa = "";
                    for (int i = 0; i < view_red.size(); i++) {
                        Object[] ObjAct = (Object[]) view_red.get(i);
                        String Type = ObjAct[0].toString();
                        String Cant = ObjAct[1].toString();

                        labels += "'" + Type + "', ";

                        if (Type.equals("SI")) {
                            dataSi = Cant;
                        } else if (Type.equals("NO")) {
                            dataNo = Cant;
                        } else if (Type.equals("N/A")) {
                            dataNa = Cant;
                        }
                    }
                    if (!labels.isEmpty()) {
                        labels = labels.substring(0, labels.length() - 2);
                    }
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart3\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'bar', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labels + "], ");  // Etiquetas "SI", "NO", "N/A"
                    out.print("    datasets: [ ");
                    out.print("      { ");
                    out.print("        label: 'SI', ");
                    out.print("        data: [" + dataSi + ", 0, 0], "); // Valores correctos
                    out.print("        backgroundColor: '#04344d', ");
//                    out.print("        borderColor: '#fdcb6e', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("      { ");
                    out.print("        label: 'NO', ");
                    out.print("        data: [0, " + dataNo + ",0], ");
                    out.print("        backgroundColor: '#065374', ");
//                    out.print("        borderColor: '#038a87', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("      { ");
                    out.print("        label: 'N/A', ");
                    out.print("        data: [0, 0," + dataNa + "], ");
                    out.print("        backgroundColor: '#00638d', ");
//                    out.print("        borderColor: '#0984e3', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("    ] ");
                    out.print("  }, ");
                    out.print("  options: { ");
                    out.print("    legend: { display: true, position: 'bottom', labels: { fontColor: '#333', fontSize: 12 } }, ");
                    out.print("    scales: { ");
                    out.print("      yAxes: [{ gridLines: { drawBorder: false, color: '#f2f2f2' }, ticks: { beginAtZero: true, stepSize: 50 } }], ");
                    out.print("      xAxes: [{ ticks: { autoSkip: false }, gridLines: { display: false } }] ");
                    out.print("    } ");
                    out.print("  } ");
                    out.print("});");
                    out.print("</script>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("<div class='col-lg-4 mr-3 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                view_process = InformationJpa.ViewProcess();
                if (view_process != null) {
                    //<editor-fold defaultstate="collapsed" desc="PROCESS">
                    out.print("<h4 class='mb-2 mt-4 text-center'>Proceso</h4>");
                    out.print("<canvas id=\"myChart6\" width=\"400\" height=\"230\"></canvas>");
                    String labelData = "";
                    String valueData = "";
                    for (int i = 0; i < view_process.size(); i++) {
                        Object[] ObjProcess = (Object[]) view_process.get(i);
                        labelData += "'" + ObjProcess[0].toString() + "', ";
                        valueData += ObjProcess[1].toString() + ", ";
                    }
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart6\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'doughnut', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labelData + "], ");
                    out.print("    datasets: [{ ");
                    out.print("      label: 'Proceso', ");
                    out.print("      data: [" + valueData + "], ");
                    out.print("      backgroundColor: [");

                    String[] colors = {"'#0d2259'", "'#153b9a'", "'#0d3dc6'", "'#0943f1'", "'#1653ff'", "'#5da4ff'", "'#a8ddb5'", "'#ccebc5'"};
                    for (int i = 0; i < view_process.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < view_process.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderColor: [");
                    for (int i = 0; i < view_process.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < view_process.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderWidth: 1.7 }], ");
                    out.print("}, options: { "
                            + "responsive: true,"
                            + "    cutout: '60%', "
                            + "    legend: { "
                            + "      position: 'bottom', "
                            + "    }, ");
                    out.print("}});");
                    out.print("</script>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12 d-flex'>");
                out.print("<div class='col-lg-4 mr-3 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                view_state = InformationJpa.ViewState();
                if (view_state != null) {
                    //<editor-fold defaultstate="collapsed" desc="STATE">
                    out.print("<h4 class='mb-2 mt-4 text-center'>Estado</h4>");
                    out.print("<canvas id=\"myChart4\" width=\"400\" height=\"230\"></canvas>");
                    String labelData = "";
                    String valueData = "";
                    for (int i = 0; i < view_state.size(); i++) {
                        Object[] ObjState = (Object[]) view_state.get(i);
                        labelData += "'" + ObjState[0].toString() + "', ";
                        valueData += ObjState[1].toString() + ", ";
                    }
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart4\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'pie', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labelData + "], ");
                    out.print("    datasets: [{ ");
                    out.print("      label: 'Proceso', ");
                    out.print("      data: [" + valueData + "], ");
                    out.print("      backgroundColor: [");

                    String[] colors = {"'#490076'", "'#6a079c'", "'#8607ca'"};
                    for (int i = 0; i < view_state.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < view_state.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderColor: [");
                    for (int i = 0; i < view_state.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < view_state.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderWidth: 1.7 }], ");
                    out.print("}, options: { "
                            + "responsive: true,"
                            + "    cutout: '60%', "
                            + "    legend: { "
                            + "      position: 'bottom', "
                            + "    }, ");
                    out.print("}});");
                    out.print("</script>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("<div class='col-lg-4 mr-3 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                view_warranty = InformationJpa.ViewWarranty();
                if (view_warranty != null) {
                    //<editor-fold defaultstate="collapsed" desc="WARRANTY">
                    out.print("<h4 class='mb-2 mt-4 text-center'>Garantia</h4>");
                    out.print("<canvas id=\"myChart5\" width=\"400\" height=\"230\"></canvas>");
                    String labels = "";
                    String dataSI = "", dataNO = "", dataNA = "";
                    for (int i = 0; i < view_warranty.size(); i++) {
                        Object[] ObjAct = (Object[]) view_warranty.get(i);
                        String Type = ObjAct[0].toString();
                        String Cant = ObjAct[1].toString();

                        labels += "'" + Type + "', ";

                        if (Type.equals("SI")) {
                            dataSI = Cant;
                        } else if (Type.equals("NO")) {
                            dataNO = Cant;
                        } else if (Type.equals("N/A")) {
                            dataNA = Cant;
                        }
                    }
                    if (!labels.isEmpty()) {
                        labels = labels.substring(0, labels.length() - 2);
                    }
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart5\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'bar', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labels + "], ");  // Etiquetas "SI", "NO", "N/A"
                    out.print("    datasets: [ ");
                    out.print("      { ");
                    out.print("        label: 'SI', ");
                    out.print("        data: [" + dataSI + ", 0, 0], "); // Valores correctos
                    out.print("        backgroundColor: '#5f0038', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("      { ");
                    out.print("        label: 'NO', ");
                    out.print("        data: [0, " + dataNO + ",0], ");
                    out.print("        backgroundColor: '#980362', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("      { ");
                    out.print("        label: 'N/A', ");
                    out.print("        data: [0, 0," + dataNA + "], ");
                    out.print("        backgroundColor: '#b80074', ");
                    out.print("        borderWidth: 1.7 ");
                    out.print("      }, ");
                    out.print("    ] ");
                    out.print("  }, ");
                    out.print("  options: { ");
                    out.print("    legend: { display: true, position: 'bottom', labels: { fontColor: '#333', fontSize: 12 } }, ");
                    out.print("    scales: { ");
                    out.print("      yAxes: [{ gridLines: { drawBorder: false, color: '#f2f2f2' }, ticks: { beginAtZero: true, stepSize: 50 } }], ");
                    out.print("      xAxes: [{ ticks: { autoSkip: false }, gridLines: { display: false } }] ");
                    out.print("    } ");
                    out.print("  } ");
                    out.print("});");
                    out.print("</script>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("<div class='col-lg-4 mr-2 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                view_type = InformationJpa.ViewType();
                if (view_type != null) {
                    //<editor-fold defaultstate="collapsed" desc="TYPE">
                    out.print("<h4 class='mb-2 mt-4 text-center'>Tipo</h4>");
                    out.print("<canvas id=\"myChart7\" width=\"400\" height=\"230\"></canvas>");
                    String labelData = "";
                    String valueData = "";
                    for (int i = 0; i < view_type.size(); i++) {
                        Object[] ObjType = (Object[]) view_type.get(i);
                        labelData += "'" + ObjType[0].toString() + "', ";
                        valueData += ObjType[1].toString() + ", ";
                    }
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart7\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'horizontalBar', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labelData + "], ");
                    out.print("    datasets: [{ ");
                    out.print("      label: 'Proceso', ");
                    out.print("      data: [" + valueData + "], ");
                    out.print("      backgroundColor: [");

                    String[] colors = {"'#042f05'", "'#135413'", "'#156714'", "'#158312'", "'#18a913'", "'#24c91e'"};
                    for (int i = 0; i < view_type.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < view_type.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderColor: [");
                    for (int i = 0; i < view_type.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < view_type.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderWidth: 1.7 }], ");
                    out.print("}, options: { "
                            + "responsive: true,"
                            + "    cutout: '60%', "
                            + "    legend: { "
                            + "      position: 'bottom', "
                            + "    }, ");
                    out.print("}});");
                    out.print("</script>");
                    //</editor-fold>
                }
                out.print("</div>");

                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>   
            }

            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header justify-content-between'>");
            out.print("<div><h4>PC Información</h4></div>");
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'><button class=\"btn btn-green btn-sm\" style=\"border-radius: 4px;\"  onclick='window.location.href=\"Information?opt=1&State=" + State + "&Counter=1\"' ><i class=\"fas fa-chart-bar\"></i></button></div>");
            if (txtPermissions.contains("[48]")) {
                out.print("<div><button class=\"btn btn-green btn-sm\" style=\"border-radius: 4px;\"  onclick=\"exportarExcel()\" ><i class=\"fas fa-file-excel\"></i></button></div>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='card'>");
            out.print("<div class='d-flex  m-2 justify-content-between'>");
            //<editor-fold defaultstate="collapsed" desc="FILTER">
            out.print("<div class=\"form-group text-center mb-0\">\n"
                    + "                      <div class=\"selectgroup selectgroup-pills\">\n"
                    + "                  <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" onclick=\"javascript:location.href='Information?opt=1&State=3'\" name=\"icon-input\" value=\"3\" class=\"selectgroup-input\" " + ((State == 3) ? "checked" : "") + " >\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-info\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='General'><i style='font-size:17px' class=\"fas fa-star-of-life\"></i></span>\n"
                    + "                        </label>\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" onclick=\"javascript:location.href='Information?opt=1&State=1'\" name=\"icon-input\" value=\"1\" class=\"selectgroup-input\" " + ((State == 1) ? "checked" : "") + ">\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-success\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='Bueno'><i style='font-size:17px' class=\"fas fa-thumbs-up\"></i></span>\n"
                    + "                        </label>\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" onclick=\"javascript:location.href='Information?opt=1&State=2'\" name=\"icon-input\" value=\"2\" class=\"selectgroup-input\" " + ((State == 2) ? "checked" : "") + ">\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-warning\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='En Proceso'><i style='font-size:17px' class=\"fas fa-fist-raised\"></i></span>\n"
                    + "                        </label>\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" onclick=\"javascript:location.href='Information?opt=1&State=0'\" name=\"icon-input\" value=\"3\" class=\"selectgroup-input\" " + ((State == 0) ? "checked" : "") + ">\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-danger\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='De Baja'><i style='font-size:17px' class=\"fas fa-thumbs-down\"></i></span>\n"
                    + "                        </label>\n");
            out.print("</div>"
                    + "</div>");
            out.print("<div><input style='width:91%' type=\"text\" class='form-control' id=\"myInput\" placeholder=\"Buscar...\"></div>"
                    //</editor-fold>
                    + "</div>");
            out.print("<div id=\"accordion\">");
            out.print("<div class=\"accordion\">");

            if (State == 3) {
                lst_information = InformationJpa.ConsultInformation();
            } else {
                lst_information = InformationJpa.ConsultInformation(State);
            }
            if (lst_information != null) {
                out.print("<div id='container' class=\"container\">");
                for (int i = 0; i < lst_information.size(); i++) {
                    Object[] obj_info = (Object[]) lst_information.get(i);
                    StateInf = Integer.parseInt(obj_info[3].toString());
                    out.print("<div id='list'><span>");
                    out.print("<div class=\"single-item\">");
                    //<editor-fold defaultstate="collapsed" desc="HEAD">
                    out.print("<div class=\"accordion-header accc_div_dataSheet\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-" + i + "\" aria-expanded=\"true\">");
                    out.print("<div  class='styledata' style='display:flex; justify-content:space-between;width:100%; text-align:center; border-right: 3px solid " + ((StateInf == 1) ? "green" : (StateInf == 2) ? "#ffa426" : "#fd1e08") + "; border-left: 3px solid " + ((StateInf == 1) ? "green" : (StateInf == 2) ? "#ffa426" : "#fd1e08") + ";'>");

                    out.print("<div style='width:20%'>");
                    out.print("<b>" + obj_info[2] + "</b>");
                    out.print("</div>");

                    out.print("<div style='width:30%'>");
                    out.print("" + obj_info[4] + "");
                    out.print("</div>");

                    out.print("<div style='width:20%'>");
                    out.print("" + obj_info[7] + "");
                    out.print("</div>");

                    out.print("<div style='width:20%'>");
                    out.print("" + obj_info[8] + "");
                    out.print("</div>");

                    out.print("<div style='width:10%'>");
                    out.print("" + obj_info[5] + "");
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONTENT">
                    out.print("<div class=\"accordion-body collapse\" id=\"panel-body-" + i + "\" style='background-color: rgb(251 251 251);max-width: 99%;' data-parent=\"#accordion\">");
                    out.print("<div style='display:flex; padding-top:16px; justify-content: space-evenly;'>");
                    out.print("<div style='width:85%;text-align:center;'><h5>Especificaciones</h5></div>");
                    if (txtPermissions.contains("[47]")) {
                        out.print("<div>"
                                + ((StateInf == 1) ? "<a href='Information?opt=1&IdInformation=" + obj_info[0] + "'><i style='font-size:22px; color:black;' class=\"fas fa-pencil-alt\"></i></a>" : "")
                                + "</div>");
                    }
                    out.print("</div>");

                    out.print("<hr class='hr_sheet'>");

                    out.print("<div style='display:flex;justify-content:space-around;width:100%;'>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Antivirus: </b>" + obj_info[13] + "</div>");
                    out.print("<div><b class='b_text'>Correo Interno: </b>" + obj_info[18] + "</div>");
                    out.print("<div><b class='b_text'>Correo Externo: </b>" + obj_info[19] + "</div>");
                    out.print("<div><b class='b_text'>Descripción: </b>" + obj_info[29] + "</div>");
                    out.print("<div><b class='b_text'>Factura: </b>" + obj_info[20] + "</div>");
                    out.print("<div><b class='b_text'>Fecha Factura: </b>" + obj_info[21] + "</div>");
                    out.print("<div><b class='b_text'>Gmail: </b>" + obj_info[17] + "</div>");
                    out.print("<div><b class='b_text'>Garantia: </b>" + obj_info[25] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>Garantia Fecha: </b>" + obj_info[6] + "</div>");
                    out.print("<div><b class='b_text'>Internet: </b>" + obj_info[14] + "</div>");
                    out.print("<div><b class='b_text'>Login Plastitec: </b>" + obj_info[6] + "</div>");
                    out.print("<div><b class='b_text'>Licencia: </b>" + obj_info[22] + "</div>");
                    out.print("<div><b class='b_text'>Lincecia Fecha Fin: </b>" + obj_info[23] + "</div>");
                    out.print("<div><b class='b_text'>MAC: </b>" + obj_info[8] + "</div>");
                    out.print("<div><b class='b_text'>Proveedor: </b>" + obj_info[24] + "</div>");
                    out.print("<div><b class='b_text'>Punto de red: </b>" + obj_info[28] + "</div>");
                    out.print("</div>");

                    out.print("<div class='DivGrip'>");
                    out.print("<div><b class='b_text'>RED: </b>" + obj_info[9] + "</div>");
                    out.print("<div><b class='b_text'>SKYPE: </b>" + obj_info[16] + "</div>");
                    out.print("<div><b class='b_text'>Tipo Estado: </b>" + obj_info[27] + "</div>");
                    out.print("<div><b class='b_text'>Tipo Software: </b>" + obj_info[26] + "</div>");
                    out.print("<div><b class='b_text'>VLAN: </b>" + obj_info[10] + "</div>");
                    out.print("<div><b class='b_text'>VPN: </b>" + obj_info[15] + "</div>");
                    out.print("<div><b class='b_text'>Versión Office: </b>" + obj_info[12] + "</div>");
                    out.print("<div><b class='b_text'>Versión WIN: </b>" + obj_info[11] + "</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='text-center mb-2'><b class='b_text'>Caracteristicas: </b><br>" + obj_info[30] + "</div>");

                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</span></div>");
                }
                out.print("</div>");
            } else {

            }
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

        } catch (Exception e) {
            Logger.getLogger(Tag_information.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }

}
