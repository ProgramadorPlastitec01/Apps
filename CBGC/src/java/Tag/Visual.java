package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.FormatJpaController;
import Connection.ConnectionSQLServer;
import Connection.ConnectionRegistrosLAB;
import Connection.ConnectionGeneracionLotes;
import Connection.ConnectionSignature;
import Controller.SettingJpaController;
import Controller.CertificatesJpaController;
import java.util.List;
import Method.Util;
import java.io.IOException;
import java.util.Optional;

public class Visual extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        FormatJpaController FormatJpa = new FormatJpaController();
        ConnectionSQLServer FactoryJpa = new ConnectionSQLServer();
        ConnectionRegistrosLAB RegistrosLabJpa = new ConnectionRegistrosLAB();
        SettingJpaController SettingJpa = new SettingJpaController();
        CertificatesJpaController CertificatesJpa = new CertificatesJpaController();
        ConnectionGeneracionLotes GeneracionLotesJpa = new ConnectionGeneracionLotes();
        ConnectionSignature SignatureConn = new ConnectionSignature();
        String Type = "", Product = "", ProductFact = "", Batch = "", DataTechnicalSheet = "", Html = "", BatchLay = "", Record = "", FormatName = "";
        int Order = 0, IdForm = 0, Count = 1, Count2 = 1, IdCertificates = 0, State = 0, IdSig = 0, TempDelete = 0;
        List lst_content = null;
        List lst_headFact = null;
        List lst_RLab = null;
        List lst_Glotes1 = null;
        List lst_GlotesRep = null;
        List lst_parameter = null;
        List lst_data = null;
        List lst_welds = null;
        List lst_sheet = null;
        List lst_prm = null;
        List lst_sign = null;
        try {
            //<editor-fold defaultstate="collapsed" desc="ENVIRONMENT VARIABLES">
            // Variables recibidas
            Type = Optional.ofNullable(pageContext.getRequest().getParameter("Type")).orElse("");
            FormatName = Optional.ofNullable(pageContext.getRequest().getParameter("FormatName")).orElse("");
            Product = Optional.ofNullable(pageContext.getRequest().getParameter("Product")).orElse("");
            Batch = Optional.ofNullable(pageContext.getRequest().getParameter("Batch")).orElse("");
            // Variables derivadas
            // Procesar FormatName
            try {
                if (!FormatName.isEmpty() && FormatName.contains("/")) {
                    String[] parts = FormatName.split("/");
                    IdForm = Integer.parseInt(parts[0]);
                    Record = parts.length > 1 ? parts[1] : "";
                }
            } catch (Exception e) {
                IdForm = 0;
                Record = "";
            }
            // Procesar IdCertificates
            try {
                IdCertificates = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("IdCertificates")).orElse("0"));
            } catch (NumberFormatException e) {
                IdCertificates = 0;
            }
            // Procesar Order
            try {
                Order = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("Order")).orElse("0"));
            } catch (NumberFormatException e) {
                Order = 0;
            }
            // Procesar ProductFact
            try {
                ProductFact = Product.replace("2C", "3C");
            } catch (Exception e) {
                ProductFact = "";
            }
            // Procesar TempDelete
            try {
                TempDelete = Integer.parseInt(Optional.ofNullable(pageContext.getRequest().getParameter("TempDelete")).orElse("0"));
            } catch (NumberFormatException e) {
                TempDelete = 0;
            }
            //</editor-fold>
            out.print("<section class='section'>");

            if (IdCertificates > 0) {
                //<editor-fold defaultstate="collapsed" desc="CONSULT DATA REGISTER">
                lst_content = CertificatesJpa.ConsultCertificatesIdHtml(IdCertificates);
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULT DATA NEW">
                lst_content = FormatJpa.ConsultFormatId(IdForm);
                //</editor-fold>
            }
            if (lst_content != null) {
                Object[] Obj_Format = (Object[]) lst_content.get(0);
                if (IdCertificates > 0) {
                    //<editor-fold defaultstate="collapsed" desc="VALIDATION BY ID">
                    Html = Obj_Format[3].toString();
                    State = Integer.parseInt(Obj_Format[4].toString());
                    if (Obj_Format[5] != null) {
                        IdSig = Integer.parseInt(Obj_Format[5].toString());
                    }
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="VALIDATION NEW">
                    Html = Obj_Format[4].toString();
                    State = 99;
                    //</editor-fold>
                }
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='d-flex justify-content-between' style='width:100%' >"
                        + "<div class='mr-2 d-flex align-items-baseline'>"
                        + "<button class='btn btn-outline-primary btn-sm mr-2' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Generate?opt=1&Type=" + Type + "&TempDelete=" + TempDelete + "';cargarDatos()\" >"
                        + "<i class=\"fas fa-arrow-left\"></i>"
                        + "</button>"
                        + "<h4>Generación Certificado</h4></div>"
                        + ((State == 0) ? "<div><button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;' onclick='PrintHtml()'><i class='fas fa-print'></i></button></div>" : "") + ""
                        + "</div>");
                out.print("</div>");

                out.print("<div class='p-3'>");

                if (IdCertificates == 0) {
                    //<editor-fold defaultstate="collapsed" desc="GENERATION CERO">
                    lst_headFact = FactoryJpa.Products(Order, ProductFact, Batch);
                    if (lst_headFact != null && !lst_headFact.isEmpty() && lst_headFact.size() > 0) {
                        //<editor-fold defaultstate="collapsed" desc="HEAD FACTORY">
                        String[] ArgHead = Util.parseResult(lst_headFact.get(1));
                        Html = Html.replace("XOrderX", ArgHead[0]);
                        Html = Html.replace("XClientX", ArgHead[1]);
                        Html = Html.replace("XAddressX", ArgHead[2]);
                        Html = Html.replace("XPhoneX", ArgHead[3]);
                        Html = Html.replace("XCityX", ArgHead[4]);
                        Html = Html.replace("XCountryX", ArgHead[5]);
                        Html = Html.replace("XBillX", ArgHead[6]);
                        Html = Html.replace("XReissue_listX", ArgHead[7]);
                        Html = Html.replace("XProductX", ArgHead[9]);
                        Html = Html.replace("XAbilityX", ArgHead[10]);
                        Html = Html.replace("XBatchX", Batch);
                        Html = Html.replace("XQuatityGenX", ArgHead[11] + " UNIDADES");
                        Html = Html.replace("XClient_OrderX", ArgHead[12]);
                        Html = Html.replace("<h3 id=\"consValue\" class=\"mb-0\">XConsX</h3>", "<h3 id=\"consValue\" class=\"mb-0 editable\" contenteditable='true'>CC*****</h3>");

                        Html = Html.replace("XCodeX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XSampleX", "<span class='editable' contenteditable='true'>----</span>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                        Html = Html.replace("XOrderX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XClientX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XAddressX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XPhoneX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XCityX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XCountryX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XBillX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XReissue_listX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XProductX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XAbilityX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XBatchX", Batch);
                        Html = Html.replace("XQuatityGenX", "<span class='editable' contenteditable='true'>-----</span>" + " UNIDADES");
                        Html = Html.replace("XClient_OrderX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("<h3 id=\"consValue\" class=\"mb-0\">XConsX</h3>", "<h3 id=\"consValue\" class=\"mb-0 editable\" contenteditable='true'>CC*****</h3>");
                        Html = Html.replace("XCodeX", "<span class='editable' contenteditable='true'>-----</span>");
                        Html = Html.replace("XSampleX", "<span class='editable' contenteditable='true'>----</span>");
                        //</editor-fold>
                    }
                    if (IdForm == 1) {
                        //<editor-fold defaultstate="collapsed" desc="R-GC-046">
                        lst_RLab = RegistrosLabJpa.ConsultMaterials(Order, Product, Batch);
                        if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                            //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB">
                            String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                            String[] etiquetas = {
                                "REF1", "LBT1", "REF2", "LBT2", "REF3", "LBT3",
                                "REF4", "LBT4", "REF5", "LBT5", "REF6", "LBT6",
                                "REF7", "LBT7", "LBT8", "LBT9"
                            };

                            for (int i = 0; i < etiquetas.length; i++) {
                                String valor = ArgMaterials[i].trim();
                                String etiqueta = etiquetas[i];

                                if (valor.equals("N/A")) {
                                    Html = Html.replace(etiqueta, "<span class='editable' contenteditable='true'>-----</span>");
                                } else {
                                    Html = Html.replace(etiqueta, valor);
                                }
                            }

                            BatchLay = ArgMaterials[1];
                            String[] ArgImP = {ArgMaterials[1], ArgMaterials[3], ArgMaterials[5], ArgMaterials[7], ArgMaterials[9], ArgMaterials[11], ArgMaterials[13]};
                            int Cnt = 1;
                            for (int i = 0; i < ArgImP.length; i++) {
                                lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(ArgImP[i]);
                                if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                                    String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                                    Html = Html.replace("COS" + Cnt + "", "CC" + ArgLay[0]);
                                } else {
                                    Html = Html.replace("COS" + Cnt, "<span class='editable' contenteditable='true'>-----</span>");
                                }
                                Cnt++;
                            }

                            String[] ArgTnt = {ArgMaterials[14], ArgMaterials[15]};
                            int Cnt2 = 8;
                            for (int o = 0; o < ArgTnt.length; o++) {
                                lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(ArgTnt[o]);
                                if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                                    //<editor-fold defaultstate="collapsed" desc="CC RECECTION">
                                    String[] ArgInk = Util.parseResult(lst_GlotesRep.get(0));
                                    Html = Html.replace("REF" + Cnt2 + "", ArgInk[1].replace("M", ""));
                                    Html = Html.replace("COS" + Cnt2 + "", "CC" + ArgInk[0]);
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                    Html = Html.replace("REF" + Cnt2, "<span class='editable' contenteditable='true'>-----</span>");
                                    Html = Html.replace("COS" + Cnt2, "<span class='editable' contenteditable='true'>-----</span>");
                                    //</editor-fold>
                                }
                                Cnt2++;
                            }
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                            for (int i = 1; i < 10; i++) {
                                Html = Html.replace("REF" + i + "", "<span class='editable' contenteditable='true'>----</span>");
                                Html = Html.replace("LBT" + i + "", "<span class='editable' contenteditable='true'>----</span>");
                                Html = Html.replace("COS" + i + "", "<span class='editable' contenteditable='true'>----</span>");
                            }
                            //</editor-fold>
                        }
                        lst_sheet = SettingJpa.ConsultSettingCategorie(Record + " - Ficha");
                        if (lst_sheet != null) {
                            //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                            Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                            if (ObjSheet[2] != null && !ObjSheet[2].equals("")) {
                                lst_prm = RegistrosLabJpa.QueryTechnicalSheet(Order, Product, ObjSheet[2].toString());
                                if (lst_prm != null) {
                                    String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                    DataTechnicalSheet = ArgPrm[16];
                                    int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                    for (int i = 1; i < ForCant; i++) {
                                        if (ArgPrm[i].contains("0 +/- 0")) {
                                            Html = Html.replaceFirst("PRM" + Count2 + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("PRM" + Count2 + "", ArgPrm[i]);
                                        }
                                        Count2++;
                                    }
                                }
                            }
                            //</editor-fold>
                        }
                        lst_parameter = SettingJpa.ConsultSettingCategorie(Record);
                        if (lst_parameter != null) {
                            //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                            Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                            if (ObjParameter[2] != null) {
                                lst_data = RegistrosLabJpa.DimensionalQuery(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                                if (lst_data != null) {
                                    for (int i = 0; i < lst_data.size(); i++) {
                                        String[] ArgData = Util.parseResult(lst_data.get(i));
                                        if (ArgData[1].trim().equals("0")) {
                                            Html = Html.replaceFirst("MIN" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("MIN" + Count + "", ArgData[1]);
                                        }
                                        if (ArgData[2].trim().equals("0")) {
                                            Html = Html.replaceFirst("MAX" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("MAX" + Count + "", ArgData[2]);
                                        }
                                        if (ArgData[3].trim().equals("0.00")) {
                                            Html = Html.replaceFirst("MEAN" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("MEAN" + Count + "", ArgData[3]);
                                        }
                                        Count++;
                                    }
                                }
                            }
                            //</editor-fold>
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="R-GC-074">
                        lst_RLab = RegistrosLabJpa.ConsultMaterialsRGC74(Order, Product, Batch);
                        if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                            //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB">
                            String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                            String[] etiquetas = {
                                "REF1", "LBT1", "REF2", "LBT2", "REF3", "LBT3",
                                "REF4", "LBT4", "REF5", "LBT5", "REF6", "LBT6",
                                "REF7", "LBT7", "REF8", "LBT8", "LBT9"
                            };

                            for (int i = 0; i < etiquetas.length; i++) {
                                String valor = ArgMaterials[i].trim();
                                String etiqueta = etiquetas[i];

                                if (valor.equals("N/A")) {
                                    Html = Html.replace(etiqueta, "<span class='editable' contenteditable='true'>-----</span>");
                                } else {
                                    Html = Html.replace(etiqueta, valor);
                                }
                            }

                            BatchLay = ArgMaterials[1];
                            String[] ArgImP = {ArgMaterials[1], ArgMaterials[3], ArgMaterials[5], ArgMaterials[7], ArgMaterials[9], ArgMaterials[11], ArgMaterials[13], ArgMaterials[15]};
                            int Cnt = 1;
                            for (int i = 0; i < ArgImP.length; i++) {
                                lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(ArgImP[i]);
                                if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                                    String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                                    Html = Html.replace("COS" + Cnt + "", "CC" + ArgLay[0]);
                                } else {
                                    Html = Html.replace("COS" + Cnt, "<span class='editable' contenteditable='true'>-----</span>");
                                }
                                Cnt++;
                            }

                            String[] ArgTnt = {ArgMaterials[16]};
                            int Cnt2 = 9;
                            for (int o = 0; o < ArgTnt.length; o++) {
                                lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(ArgTnt[o]);
                                if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                                    //<editor-fold defaultstate="collapsed" desc="CC RECECTION">
                                    String[] ArgInk = Util.parseResult(lst_GlotesRep.get(0));
                                    Html = Html.replace("REF" + Cnt2 + "", ArgInk[1].replace("M", ""));
                                    Html = Html.replace("COS" + Cnt2 + "", "<span class='editable' contenteditable='true'>CC" + ArgInk[0] + "</span>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="VALIDATION">
                                    Html = Html.replace("REF" + Cnt2, "<span class='editable' contenteditable='true'>-----</span>");
                                    Html = Html.replace("COS" + Cnt2, "<span class='editable' contenteditable='true'>-----</span>");
                                    //</editor-fold>
                                }
                                Cnt2++;
                            }
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="VALIDATION NO DATA">
                            for (int i = 1; i < 10; i++) {
                                Html = Html.replace("REF" + i + "", "<span class='editable' contenteditable='true'>----</span>");
                                Html = Html.replace("LBT" + i + "", "<span class='editable' contenteditable='true'>----</span>");
                                Html = Html.replace("COS" + i + "", "<span class='editable' contenteditable='true'>----</span>");
                            }
                            //</editor-fold>
                        }
                        lst_sheet = SettingJpa.ConsultSettingCategorie(Record + " - Ficha");
                        if (lst_sheet != null) {
                            //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                            Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                            if (!ObjSheet[2].equals("") || ObjSheet[2] != null) {
                                lst_prm = RegistrosLabJpa.QueryTechnicalSheetRGC74(Order, Product, ObjSheet[2].toString());
                                if (lst_prm != null) {
                                    String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                    DataTechnicalSheet = ArgPrm[11];
                                    int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                    for (int i = 1; i < ForCant; i++) {
                                        if (ArgPrm[i].contains("0 +/- 0")) {
                                            Html = Html.replaceFirst("PRM" + Count2 + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("PRM" + Count2 + "", ArgPrm[i]);
                                        }
                                        Count2++;
                                    }
                                }
                            }
                            //</editor-fold>
                        }
                        lst_parameter = SettingJpa.ConsultSettingCategorie(Record);
                        if (lst_parameter != null) {
                            //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                            Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                            if (ObjParameter[2] != null) {
                                lst_data = RegistrosLabJpa.DimensionalQueryRGC74(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                                if (lst_data != null) {
                                    for (int i = 0; i < lst_data.size(); i++) {
                                        String[] ArgData = Util.parseResult(lst_data.get(i));
                                        if (ArgData[1].trim().equals("0")) {
                                            Html = Html.replaceFirst("MIN" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("MIN" + Count + "", ArgData[1]);
                                        }
                                        if (ArgData[2].trim().equals("0")) {
                                            Html = Html.replaceFirst("MAX" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("MAX" + Count + "", ArgData[2]);
                                        }
                                        if (ArgData[3].trim().equals("0.00")) {
                                            Html = Html.replaceFirst("MEAN" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                                        } else {
                                            Html = Html.replaceFirst("MEAN" + Count + "", ArgData[3]);
                                        }
                                        Count++;
                                    }
                                }
                            }
                            //</editor-fold>
                        }
                        //</editor-fold>
                    }
                    String[] ArgCategory = {"boca", "cola"};
                    for (int i = 0; i < ArgCategory.length; i++) {
                        //<editor-fold defaultstate="collapsed" desc="DATA WELDS">
                        lst_welds = RegistrosLabJpa.QueryWelds(Order, Product, Batch, ArgCategory[i]);
                        if (lst_welds != null && !lst_welds.isEmpty() && lst_welds.size() > 0) {
                            String[] ArgWelds = Util.parseResult(lst_welds.get(0));
                            Html = Html.replace("MIN" + Count + "", ArgWelds[0]);
                            Html = Html.replace("MAX" + Count + "", ArgWelds[1]);
                            Html = Html.replace("MEAN" + Count + "", ArgWelds[2]);
                        } else {
                            Html = Html.replace("MIN" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                            Html = Html.replace("MAX" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                            Html = Html.replace("MEAN" + Count + "", "<span class='editable' contenteditable='true'>-----</span>");
                        }
                        Count++;
                        //</editor-fold>
                    }
                    if (!DataTechnicalSheet.equals("")) {
                        //<editor-fold defaultstate="collapsed" desc="CHANGE DATASHEET OBSERVATION">
                        Html = Html.replace("XFichaTecnicaX", DataTechnicalSheet);
                        //</editor-fold>
                    }
                    out.print("<div id='HtmlContent'>");
                    out.print(Html);
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="FORM SAVE">
                    out.print("<form id='FormGenerate' action='Generate?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
                    out.print("<input type='hidden' name='Type' value='" + Type + "'>");
                    out.print("<input type='hidden' name='Order' value='" + Order + "'>");
                    out.print("<input type='hidden' name='Product' value='" + Product + "'>");
                    out.print("<input type='hidden' name='Batch' value='" + Batch + "'>");
                    out.print("<input type='hidden' name='FormatName' value='" + FormatName + "'>");
                    out.print("</form>");
                    out.print("<div class='DivButtonPending'>");
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='saveHtml()'><i class='fas fa-save'></i></button>");
                    out.print("</div>");
                    out.print(" <script src=\"Interface/Content/Assets/js/DeleteRow.js\"></script>");
                    //</editor-fold>
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="VIEW CERTIFICATE UPDATE - CLOSE">
                    if (State == 0) {
                        //<editor-fold defaultstate="collapsed" desc="CLOSE">
                        out.print("<div id='Imprimir'><div id='HtmlContent'>");
                        Html = Html.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        Html = Html.replaceAll("<input type=\"checkbox\"", "<input type=\"checkbox\" class=\"disabled\" ");
                        Html = Html.replaceAll("<input name=\"result\" type=\"radio\"", "<input name=\"result\" type=\"radio\" class=\"disabled\"");
                        lst_sign = SignatureConn.ConsultSignatureId(IdSig);
                        if (lst_sign != null) {
                            String[] ArgSign = Util.parseResult(lst_sign.get(0));
                            String valorCoord = ArgSign[3].replace("\"", "&quot;").replace("'", "&#39;");
                            Html = Html.replaceAll(
                                    "<input type=\"hidden\" id=\"coordenadas-hidden\" value=\"\">",
                                    "<input type='hidden' id=\"coordenadas-hidden\" value=\"" + valorCoord + "\">"
                            );
                            Html = Html.replaceAll(
                                    "<button class=\"btn btn-sm btn-danger me-2 mr-2\" title=\"Eliminar fila o grupo\"><i class=\"fas fa-times\"></i></button>",
                                    ""
                            );

                        }
                        out.print(Html);
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="UPDATE">
                        out.print("<div id='HtmlContent'>");
                        out.print(Html);
                        out.print("</div>");
                        out.print("<form id='FormGenerate' action='Generate?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
                        out.print("<input type='hidden' name='Type' value='" + Type + "'>");
                        out.print("<input type='hidden' name='Order' value='" + Order + "'>");
                        out.print("<input type='hidden' name='Product' value='" + Product + "'>");
                        out.print("<input type='hidden' name='Batch' value='" + Batch + "'>");
                        out.print("<input type='hidden' name='FormatName' value='" + FormatName + "'>");
                        out.print("<input type='hidden' name='IdCertificates' value='" + IdCertificates + "'>");
                        out.print("</form>");
                        out.print("<div class='DivButtonPending'>");
                        out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='saveHtml()'><i class='fas fa-save'></i></button>");
                        out.print("</div>");
                        out.print(" <script src=\"Interface/Content/Assets/js/DeleteRow.js\"></script>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
            try {
                out.print("<div class='alert alert-danger'>Error al cargar los datos del certificado.</div>");
            } catch (IOException ex1) {
                Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return super.doStartTag();
    }
}
