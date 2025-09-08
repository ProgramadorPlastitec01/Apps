package Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.DeviceJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controller.ItemJpaController;
import Controller.AreaControllerJpa;

public class Tag_device extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        DeviceJpaController DeviceJpa = new DeviceJpaController();
        ItemJpaController ItemJpa = new ItemJpaController();
        AreaControllerJpa AreaJpa = new AreaControllerJpa();
        List lst_device = null;
        List lst_item = null;
        List lst_area = null;
        int action = 0, idTypeDv = 0, steDv = 0;
        try {
            action = Integer.parseInt(pageContext.getRequest().getAttribute("act").toString());
        } catch (Exception e) {
            action = 0;
        }
        try {
            idTypeDv = Integer.parseInt(pageContext.getRequest().getAttribute("idTypeDv").toString());
        } catch (Exception e) {
            idTypeDv = 0;
        }
        try {
            steDv = Integer.parseInt(pageContext.getRequest().getAttribute("steDv").toString());
        } catch (Exception e) {
            steDv = 0;
        }

        //</editor-fold>
        try {
            if (action == 3) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE DOCUMENT">

                //</editor-fold>
            } else if (action == 2) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE DETAIL">

                //</editor-fold>
            } else if (action == 1) {
                //<editor-fold defaultstate="collapsed" desc="LIST DEVICE">

                //<editor-fold defaultstate="collapsed" desc="DEVICE REGISTER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 50%; right: 18%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar Dispositivos </h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                out.print("<div class=''>");

                out.print("<span class=''>Item</span>");
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='' style='margin: 12px -12px 12px 12px;'>");
                out.print("<select id='slctDta2' class='form-control select2' name='cbx_Item' >");
                out.print("<option selected disabled>Seleccionar</option>");
                lst_item = ItemJpa.ConsultItemAvaibleDetail();
                if (lst_item != null) {
                    for (int i = 0; i < lst_item.size(); i++) {
                        Object[] Objitm = (Object[]) lst_item.get(i);
                        out.print("<option value='" + Objitm[0] + "'> " + Objitm[1].toString() + " - " + Objitm[2].toString() + "</option>");
                    }
                } else {
                    out.print("<option value='0'></option>");
                }
                out.print("</select>");
                out.print("</div>");

                out.print("<span class=''>Cargo</span>");
                out.print("<input type='text' class='form-control' name='txt_chargue' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");

                out.print("<span class=''>Nombre</span>");
                out.print("<input type='text' class='form-control' name='txt_name' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");

                out.print("<span class=''>Serial</span>");
                out.print("<input type='text' class='form-control' name='txt_serial' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");
                out.print("</div>");

                out.print("<div class=''>");

                out.print("<span class=''>Consecutivo</span>");
                lst_item = ItemJpa.ConsultLastItem();
                int consec = 0;
                if (lst_item != null) {
                    Object[] Objit = (Object[]) lst_item.get(0);
                    consec = Integer.parseInt(Objit[1].toString()) + 1;
                }
                out.print("<input type='number' class='form-control' name='nmb_consec' id='' data-toggle='tooltip' data-placement='top' title='' value='" + consec + "'>");

                out.print("<span class=''>Responsable</span>");
                out.print("<input type='text' class='form-control' name='txt_respo' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");

                out.print("<span class=''>Area</span>");
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='' style='margin: 12px -12px 12px 12px;'>");
                out.print("<select id='slctDta' class='form-control select2' name='cbx_area'>");
                out.print("<option selected disabled>Seleccionar area</option>");
                lst_area = AreaJpa.ConsultAreaActive();
                if (lst_area != null) {
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] ObjArea = (Object[]) lst_area.get(i);
                        out.print("<option value='" + ObjArea[0] + "' data-toggle='tooltip' data-placement='top' title='prueba'>" + ObjArea[1].toString() + "</option>");
                    }
                } else {
                    out.print("<option value='0'>Error</option>");

                }
                out.print("</select>");
                out.print("</div>");

                out.print("<span class=''>Ubicación</span>");
                out.print("<input type='text' class='form-control' name='txt_location' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Registrar</button>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DEVICE LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=1\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Listado de dispositivos</h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");

                out.print("<div class='mt-4 w-100 d-flex' style='justify-content: space-between;'>");
                //<editor-fold defaultstate="collapsed" desc="FILTER AND BUTTONS">
                int est1 = 0, est2 = 0, est3 = 0;
                lst_device = DeviceJpa.ConsultDeviceCouter();
                if (lst_device != null) {
                    try {
                        Object[] ObDv = (Object[]) lst_device.get(0);
                        est1 = Integer.parseInt(ObDv[0].toString());
                        est2 = Integer.parseInt(ObDv[1].toString());
                        est3 = Integer.parseInt(ObDv[2].toString());
                    } catch (Exception e) {
                    }
                }

                out.print("<div class='col-lg-4'>");
                out.print("<input type='text' class='form-control' name='' id='myInput' placeholder='Buscar...' style='border: 1px solid #afafaf;' >");
                out.print("</div>");
                out.print("<div class='col-lg-4 d-flex justify-content-end'>");
                if (steDv > 0) {
                    out.print("<button class='btn btn-green mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "\"'><i class=\"fas fa-times\"></i> </button>");
                }
                out.print("<button class='btn btn-success mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "&steDv=1\"'><i class=\"fas fa-clipboard-check\"></i> Bueno (" + est1 + ")</button>");
                out.print("<button class='btn btn-warning mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "&steDv=2\"'><i class=\"fas fa-folder-open\"></i> Revisión (" + est2 + ")</button>");
                out.print("<button class='btn btn-danger mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "&steDv=3\"'><i class=\"fas fa-folder-minus\"></i> De baja (" + est3 + ")</button>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='card-body mt-3'>");
                //<editor-fold defaultstate="collapsed" desc="LSIT DATA">
                if (steDv > 0) {
                    lst_device = DeviceJpa.ConsultDevice_type_ste(idTypeDv, steDv);
                } else {
                    lst_device = DeviceJpa.ConsultDevice_type_All(idTypeDv);
                }
                out.print("<div class='row justify-content-around' id='container'>");
                if (lst_device != null) {
                    for (int i = 0; i < lst_device.size(); i++) {
                        //<editor-fold defaultstate="collapsed" desc="SQUARE LIST">
                        Object[] ObjDev = (Object[]) lst_device.get(i);
                        int stedv = Integer.parseInt(ObjDev[5].toString());
                        out.print("<div class='col-lg-3 mb-4 mr-2 dvList single-item' style='border-bottom: 1px solid #" + ((stedv == 1) ? "63ed7a" : (stedv == 2) ? "ffa426" : "fc544b") + ";'>");
                        out.print("<div class='conscDv'>");
                        out.print("<span>" + ObjDev[3] + "</span>");
                        out.print("</div>");

                        out.print("<div class=''>");
                        out.print("<h5>" + ObjDev[4] + "</h5>");
                        out.print("</div>");

                        out.print("<div class='textdv'>");
                        out.print("<span>Cargo: " + ObjDev[7] + "</span><br>");
                        out.print("<span>Ubicacion: " + ObjDev[9] + "</span><br>");
                        out.print("<span><span class='bullet text-" + ((stedv == 1) ? "success" : (stedv == 2) ? "warning" : "danger") + "'></span> " + ((stedv == 1) ? "Bueno" : (stedv == 2) ? "Revisión" : "De baja") + "</span><br>");
                        out.print("</div>");

                        out.print("<div class='gnDiv'>");
                        out.print("<div class='d-flex dvListBtn'>");
                        out.print("<a href='#'>Ver detalle</a>");
                        out.print("<button class='btn btn-green btn-sm'><i class=\"fas fa-link\"></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                } else {
                    out.print("<div class='col-lg-5 mb-4 mr-2 dvList'>");
                    out.print("<h5>No se ha encontrado información relacionada!</h5>");
                    out.print("</div>");
                }
                //</editor-fold>
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>

                //</editor-fold>
            } else if (action == 0) {
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<span class=''></span>");
                out.print("<h2>DISPOSITIVOS</h2>");
                out.print("<span class=''></span>");
                out.print("</div>");
                out.print("<div class='card-body'>");

                lst_device = DeviceJpa.ConsultAllTypeDeviceCount();

                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-7 mr-2 contDataList'>");
                //<editor-fold defaultstate="collapsed" desc="LIST LEFT">
                if (lst_device != null) {
                    out.print("<div class='d-flex justify-content-between devfx'>");
                    out.print("<h5>Tipo de dispositivo</h5>");
                    out.print("<h6>Cantidad</h6>");
                    out.print("</div>");
                    for (int i = 0; i < lst_device.size(); i++) {
                        Object[] ObjDev = (Object[]) lst_device.get(i);
                        out.print("<div class='d-flex justify-content-between devDet' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + ObjDev[0] + "\"'>");
                        out.print("<span>" + ObjDev[1] + "</span>");
                        out.print("<span class=''>" + ObjDev[2] + "</span>");
                        out.print("</div>");
                    }
                }
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='col-lg-5'>");
                //<editor-fold defaultstate="collapsed" desc="LIST RIGHT">
//                out.print("<div class='col-lg-12 mb-4 divButton'>");
//                out.print("<h5 class='mb-2'>Consultar todos los dispositivos</h5>");
//                out.print("<div class='text-center'>");
//                out.print("<button class='btn btn-green' onclick='window.location.href=\"Device?opt=1&act=1\"'><i class='fas fa-search' style='font-size: 16px;'></i></button>");
//                out.print("</div>");
//                out.print("</div>");

                //<editor-fold defaultstate="collapsed" desc="COUNTER">
                out.print("<div class='divCount'>");
                out.print("<div class=''>");
                out.print("<h5>Estado de dispositivos</h5>");
                out.print("</div>");
                lst_device = DeviceJpa.ConsultAllStateDeviceCount();
                out.print("<div class='row DivCounter'>");
                for (int i = 0; i < lst_device.size(); i++) {
                    Object[] ObjDevice = (Object[]) lst_device.get(i);
                    out.print("<div class='col-lg-3 mr-2 boxCount " + ((ObjDevice[0].toString().equals("Bueno")) ? "bgGreen" : (ObjDevice[0].toString().equals("Revision")) ? "bgOrange" : "bgRed") + "'>");
                    out.print("<h2>" + ObjDevice[2] + "</h2>");
                    out.print("<p>" + ObjDevice[0] + "</p>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="LIST DOC">
                out.print("<div class='mt-4 divList'>");

                out.print("<div class=''>");
                out.print("<h5>Documentos en proceso</h5>");
                out.print("</div>");

                out.print("<table class='table table-sm' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Dispositivo</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Ver</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                out.print("<tr>");
                out.print("<td>Tablet 1</td>");
                out.print("<td>Mantenimiento</td>");
                out.print("<td><button class='btn btn-info btn-sm'><i class='fas fa-eye'></i></button></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Impresora 1</td>");
                out.print("<td>Mantenimiento</td>");
                out.print("<td><button class='btn btn-info btn-sm'><i class='fas fa-eye'></i></button></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>Scanner 1</td>");
                out.print("<td>Mantenimiento</td>");
                out.print("<td><button class='btn btn-info btn-sm'><i class='fas fa-eye'></i></button></td>");
                out.print("</tr>");
                out.print("</tbody>");
                out.print("</table>");

                out.print("</div>");
                //</editor-fold>

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_device.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
