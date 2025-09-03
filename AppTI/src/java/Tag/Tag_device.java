package Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.DeviceJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tag_device extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        DeviceJpaController DeviceJpa = new DeviceJpaController();
        List lst_device = null;
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

                out.print("<div class='mt-2 w-100'>");
                out.print("<input type='text' class='form-control col-lg-8' style='margin: auto;text-align:center;' name='' id='' data-toggle='tooltip' data-placement='top' placeholder='Buscar...' title='Buscar...' value=''>");
                out.print("</div>");

                out.print("<div class='card-body'>");
                if (steDv > 1) {
                    lst_device = DeviceJpa.ConsultDevice_type_ste(idTypeDv, steDv);
                } else {
                    lst_device = DeviceJpa.ConsultDevice_type_All(idTypeDv);
                }
                out.print("<div class='row justify-content-around' id='container'>");
                if (lst_device != null) {

                    for (int i = 0; i < lst_device.size(); i++) {
                        Object[] ObjDev = (Object[]) lst_device.get(i);
                        int stedv = Integer.parseInt(ObjDev[5].toString());
                        out.print("<div class='col-lg-3 mb-4 mr-2 dvList' style='border-bottom: 1px solid #"+ ((stedv == 1) ? "63ed7a" : (stedv == 2) ? "ffa426" : "fc544b") +";'>");

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
                    }
                } else {
                    out.print("<div class='col-lg-5 mb-4 mr-2 dvList'>");
                    out.print("<h5>No se ha encontrado información relacionada!</h5>");
                    out.print("</div>");
                }
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
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
