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

public class Visual extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        FormatJpaController FormatJpa = new FormatJpaController();
        ConnectionSQLServer FactoryJpa = new ConnectionSQLServer();
        ConnectionRegistrosLAB RegistrosLabJpa = new ConnectionRegistrosLAB();
        SettingJpaController SettingJpa = new SettingJpaController();
        ConnectionGeneracionLotes GeneracionLotesJpa = new ConnectionGeneracionLotes();
        String Type = "", Product = "", ProductFact = "", Batch = "", Register = "", Html = "", BatchLay = "", BatchCentralTube = "", BatchInk = "";
        int Order = 0, IdFormat = 0, Count = 1;
        List lst_content = null;
        List lst_headFact = null;
        List lst_RLab = null;
        List lst_Glotes1 = null;
        List lst_Glotes2 = null;
        List lst_GlotesRep = null;
        List lst_parameter = null;
        List lst_data = null;
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
                Order = Integer.parseInt(pageContext.getRequest().getParameter("order"));
            } catch (Exception e) {
                Order = 0;
            }
            try {
                Product = pageContext.getRequest().getParameter("product");
                ProductFact = Product.replace("2C", "3C");
            } catch (Exception e) {
                Product = "";
                ProductFact = "";
            }
            try {
                Batch = pageContext.getRequest().getParameter("batch");
            } catch (Exception e) {
                Batch = "";
            }
            out.print("<section class='section'>");
            lst_content = FormatJpa.ConsultFormatId(IdFormat);
            if (lst_content != null) {
                Object[] Obj_Format = (Object[]) lst_content.get(0);
                Html = Obj_Format[4].toString();
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='d-flex'>"
                        + "<div class='mr-2'>"
                        + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp';cargarDatos()\" >"
                        + "<i class=\"fas fa-arrow-left\"></i>"
                        + "</button>"
                        + "</div>"
                        + "<h4>Generación Certificado</h4>"
                        + "</div>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1);'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='p-3'>");
                lst_headFact = FactoryJpa.Products(Order, ProductFact, Batch);
                if (lst_headFact != null && !lst_headFact.isEmpty() && lst_headFact.size() > 0) {
                    String[] ArgHead = lst_headFact.get(1).toString().replace("]", "").replace("[", "").split("///");
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
                    Html = Html.replace("<h3 class=\"mb-0\">XConsX</h3>", "<h3 class=\"mb-0 editable\" contenteditable='true'>CC*****</h3>");
                    Html = Html.replace("XYFX", "<span class='editable' contenteditable='true'>****</span>");
                    Html = Html.replace("XMFX", "<span class='editable' contenteditable='true'>**</span>");
                    Html = Html.replace("XDFX", "<span class='editable' contenteditable='true'>**</span>");
                    Html = Html.replace("XYEX", "<span class='editable' contenteditable='true'>****</span>");
                    Html = Html.replace("XMEX", "<span class='editable' contenteditable='true'>**</span>");
                    Html = Html.replace("XDEX", "<span class='editable' contenteditable='true'>**</span>");
                    Html = Html.replace("XCodeX", "<span class='editable' contenteditable='true'>-----</span>");
                    Html = Html.replace("XClient_OrderX", "<span class='editable' contenteditable='true'>-------</span>");
                    Html = Html.replace("XSampleX", "<span class='editable' contenteditable='true'>----</span>");
                }
                lst_RLab = RegistrosLabJpa.ConsultMaterials(Order, Product, Batch);
                if (lst_RLab != null && !lst_RLab.isEmpty()) {
                    String[] ArgMaterials = lst_RLab.get(0).toString().replace("]", "").replace("[", "").split("///");
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
                }
                lst_GlotesRep = GeneracionLotesJpa.ConsultarCC_RepcecionMaterial(BatchInk);
                if (lst_GlotesRep != null && !lst_GlotesRep.isEmpty() && lst_GlotesRep.size() > 0) {
                    String[] ArgInk = lst_GlotesRep.get(0).toString().replace("]", "").replace("[", "").split("///");
                    Html = Html.replace("COS3", ArgInk[0]);
                }
                lst_parameter = SettingJpa.ConsultSettingCategorie("DimensionalesR-GC-046");
                if (lst_parameter != null) {
                    Object[] ObjParameter = (Object[]) lst_parameter.get(0);
                    if (ObjParameter[2] != null) {
                        String[] ArgParameter = ObjParameter[2].toString().replace("[", "").replace("]", "").split(",");
                        for (int i = 0; i < ArgParameter.length; i++) {
                            String Parameter = ArgParameter[i];
                            lst_data = RegistrosLabJpa.DimensionalQuery(Order, Product, Batch, Parameter);
                            if (lst_data != null) {
                                String[] ArgData = lst_data.get(0).toString().replace("]", "").replace("[", "").split("///");
                                Html = Html.replace("MIN" + Count + "", ArgData[0]);
                                Html = Html.replace("MAX" + Count + "", ArgData[1]);
                                Html = Html.replace("MEAN" + Count + "", ArgData[2]);
                            }
                            Count++;
                        }
                    }
                }

                out.print(Html);

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
