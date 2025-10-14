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
import Controller.SettingJpaController;
import java.util.List;
import Method.Util;
import Controller.CertificatesJpaController;

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
        String Type = "", Product = "", ProductFact = "", Batch = "", DataTechnicalSheet = "", Html = "", BatchLay = "", BatchCentralTube = "", BatchInk = "";
        int Order = 0, IdFormat = 0, Count = 1, Count2 = 1, IdCertificates = 0;
        List lst_content = null;
        List lst_headFact = null;
        List lst_RLab = null;
        List lst_Glotes1 = null;
        List lst_Glotes2 = null;
        List lst_GlotesRep = null;
        List lst_parameter = null;
        List lst_data = null;
        List lst_welds = null;
        List lst_sheet = null;
        List lst_prm = null;
        try {
            try {
                Type = pageContext.getRequest().getParameter("Type");
            } catch (Exception e) {
                Type = "";
            }
            try {
                IdFormat = Integer.parseInt(pageContext.getRequest().getParameter("IdFormat"));
            } catch (Exception e) {
                IdFormat = 0;
            }
            try {
                IdCertificates = Integer.parseInt(pageContext.getRequest().getParameter("IdCertificates"));
            } catch (Exception e) {
                IdCertificates = 0;
            }
            try {
                Order = Integer.parseInt(pageContext.getRequest().getParameter("Order"));
            } catch (Exception e) {
                Order = 0;
            }
            try {
                Product = pageContext.getRequest().getParameter("Product");
                ProductFact = Product.replace("2C", "3C");
            } catch (Exception e) {
                Product = "";
                ProductFact = "";
            }
            try {
                Batch = pageContext.getRequest().getParameter("Batch");
            } catch (Exception e) {
                Batch = "";
            }
            out.print("<section class='section'>");

            if (IdCertificates > 0) {
                lst_content = CertificatesJpa.ConsultCertificatesIdHtml(IdCertificates);
            } else {
                lst_content = FormatJpa.ConsultFormatId(IdFormat);
            }
            if (lst_content != null) {
                Object[] Obj_Format = (Object[]) lst_content.get(0);
                if (IdCertificates > 0) {
                    Html = Obj_Format[3].toString();
                } else {
                    Html = Obj_Format[4].toString();
                }
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='d-flex'>"
                        + "<div class='mr-2'>"
                        + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Generate?opt=1&Type=" + Type + "';cargarDatos()\" >"
                        + "<i class=\"fas fa-arrow-left\"></i>"
                        + "</button>"
                        + "</div>"
                        + "<h4>Generación Certificado</h4>"
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
                    }
                    lst_RLab = RegistrosLabJpa.ConsultMaterials(Order, Product, Batch);
                    if (lst_RLab != null && !lst_RLab.isEmpty() && lst_RLab.size() > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MATERIAL REGISTROS LAB">
                        String[] ArgMaterials = Util.parseResult(lst_RLab.get(0));
                        Html = Html.replace("REF1", ArgMaterials[0]);
                        Html = Html.replace("LBT1", ArgMaterials[1]);
                        Html = Html.replace("REF2", ArgMaterials[9]);
                        Html = Html.replace("LBT2", ArgMaterials[10]);
                        Html = Html.replace("LBT3", ArgMaterials[8]);
                        BatchLay = ArgMaterials[1];
                        lst_Glotes1 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(BatchLay);
                        if (lst_Glotes1 != null && !lst_Glotes1.isEmpty() && lst_Glotes1.size() > 0) {
                            String[] ArgLay = lst_Glotes1.get(0).toString().replace("]", "").replace("[", "").split("///");
                            Html = Html.replace("COS1", "CC" + ArgLay[0]);
                        }
                        BatchCentralTube = ArgMaterials[10];
                        lst_Glotes2 = GeneracionLotesJpa.ConsultarCC_GeneracionLote(BatchCentralTube);
                        if (lst_Glotes2 != null && !lst_Glotes2.isEmpty() && lst_Glotes2.size() > 0) {
                            String[] ArgCentralTube = lst_Glotes2.get(0).toString().replace("]", "").replace("[", "").split("///");
                            Html = Html.replace("COS2", "CC" + ArgCentralTube[0]);
                        }
                        BatchInk = ArgMaterials[8];
                        //</editor-fold>
                    }
                    lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(BatchInk);
                    if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                        //<editor-fold defaultstate="collapsed" desc="CC REPECTION">
                        String[] ArgInk = Util.parseResult(lst_GlotesRep.get(0));
                        Html = Html.replace("COS3", ArgInk[0]);
                        //</editor-fold>
                    }
                    lst_sheet = SettingJpa.ConsultSettingCategorie("ENEMA - Ficha");
                    if (lst_sheet != null) {
                        //<editor-fold defaultstate="collapsed" desc="TECNHNICAL SHEET">
                        Object[] ObjSheet = (Object[]) lst_sheet.get(0);
                        if (!ObjSheet[2].equals("") || ObjSheet[2] != null) {
                            lst_prm = RegistrosLabJpa.QueryTechnicalSheet(Order, Product, ObjSheet[2].toString());
                            if (lst_prm != null) {
                                String[] ArgPrm = Util.parseResult(lst_prm.get(0));
                                DataTechnicalSheet = ArgPrm[10];
                                int ForCant = Integer.parseInt(ArgPrm[0].trim());
                                for (int i = 1; i < ForCant; i++) {
                                    Html = Html.replace("PRM" + Count2 + "", ArgPrm[i]);
                                    Count2++;
                                }
                            }
                        }
                        //</editor-fold>
                    }
                    lst_parameter = SettingJpa.ConsultSettingCategorie("ENEMA");
                    if (lst_parameter != null) {
                        //<editor-fold defaultstate="collapsed" desc="DATA PARAMETER">
                        Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                        if (ObjParameter[2] != null) {
                            lst_data = RegistrosLabJpa.DimensionalQuery(Order, Product, Batch, ObjParameter[2].toString(), ObjParameter[3].toString());
                            if (lst_data != null) {
                                for (int i = 0; i < lst_data.size(); i++) {
                                    String[] ArgData = Util.parseResult(lst_data.get(i));
                                    Html = Html.replace("MIN" + Count + "", ArgData[1]);
                                    Html = Html.replace("MAX" + Count + "", ArgData[2]);
                                    Html = Html.replace("MEAN" + Count + "", ArgData[3]);
                                    Count++;
                                }
                            }
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
                        }
                        Count++;
                        //</editor-fold>
                    }
                    if (!DataTechnicalSheet.equals("")) {
                        Html = Html.replace("XFichaTecnicaX", DataTechnicalSheet);
                    }
                    out.print("<div id='HtmlContent'>");
                    out.print(Html);
                    out.print("</div>");
                    out.print("<form id='FormGenerate' action='Generate?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
                    out.print("<input type='hidden' name='Type' value='" + Type + "'>");
                    out.print("<input type='hidden' name='Order' value='" + Order + "'>");
                    out.print("<input type='hidden' name='Product' value='" + Product + "'>");
                    out.print("<input type='hidden' name='Batch' value='" + Batch + "'>");
                    out.print("<input type='hidden' name='IdFormat' value='" + IdFormat + "'>");
                    out.print("</form>");
                    out.print("<div class='DivButtonPending'>");
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='saveHtml()'><i class='fas fa-save'></i></button>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    out.print("<div id='HtmlContent'>");
                    out.print(Html);
                    out.print("</div>");
                    
                    out.print("<form id='FormGenerate' action='Generate?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return FormGenerate(this)'>");
                    out.print("<input type='hidden' name='Type' value='" + Type + "'>");
                    out.print("<input type='hidden' name='Order' value='" + Order + "'>");
                    out.print("<input type='hidden' name='Product' value='" + Product + "'>");
                    out.print("<input type='hidden' name='Batch' value='" + Batch + "'>");
                    out.print("<input type='hidden' name='IdFormat' value='" + IdFormat + "'>");
                    out.print("<input type='hidden' name='IdCertificates' value='" + IdCertificates + "'>");
                    out.print("</form>");
                    out.print("<div class='DivButtonPending'>");
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='saveHtml()'><i class='fas fa-save'></i></button>");
                    out.print("</div>");
                }

                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
