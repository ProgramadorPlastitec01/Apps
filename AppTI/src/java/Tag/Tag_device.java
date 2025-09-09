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
import Controller.DeviceHeaderJpaController;
import Controller.TypeSecuenceJpaController;
import Controller.DeviceDetailJpaController;

public class Tag_device extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        DeviceJpaController DeviceJpa = new DeviceJpaController();
        ItemJpaController ItemJpa = new ItemJpaController();
        AreaControllerJpa AreaJpa = new AreaControllerJpa();
        DeviceHeaderJpaController DeviceHead = new DeviceHeaderJpaController();
        DeviceDetailJpaController DeviceDetailJpa = new DeviceDetailJpaController();
        TypeSecuenceJpaController SecuenceJpa = new TypeSecuenceJpaController();

        List lst_device = null;
        List lst_deviceHead = null;
        List lst_typeDevice = null;
        List lst_item = null;
        List lst_area = null;
        List lst_secuence = null;
        List lst_DeviceDetail = null;

        int action = 0, idTypeDv = 0, steDv = 0, idDevice = 0, state = 0;
        String nameDevice = "", typeDvName = "";
        String[] structure = {};
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

        try {
            idDevice = Integer.parseInt(pageContext.getRequest().getAttribute("idDevice").toString());
        } catch (Exception e) {
            idDevice = 0;
        }
        lst_device = DeviceJpa.ConsultDevicexId(idDevice);
        if (lst_device != null) {
            Object[] ObjDvGen = (Object[]) lst_device.get(0);
            nameDevice = ObjDvGen[3].toString();
            typeDvName = ObjDvGen[14].toString();
        }

        //</editor-fold>
        try {
            if (action == 4) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE DOCUMENT">

//</editor-fold>
            } else if (action == 3) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE DETAIL">
                int idDeviceHead = 0;
                try {
                    idDeviceHead = Integer.parseInt(pageContext.getRequest().getAttribute("idDeviceHead").toString());
                } catch (Exception e) {
                    idDeviceHead = 0;
                }

                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("<div><h4>Documentacion</h4><h2>" + nameDevice + "</h2></div>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                lst_deviceHead = DeviceHead.ConsultDeviceHeaderIdHead(idDeviceHead);
                if (lst_deviceHead != null) {
                    Object[] ObjDvHe = (Object[]) lst_deviceHead.get(0);
                    structure = ObjDvHe[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                    out.print("<div class='card'>");
                    out.print("<div class=\"row mt-4\" style='width: 100%; justify-content: center;'>");
                    out.print("<div class=\"col-12\">");
                    out.print("<div class=\"wizard-steps\" style='display: flex; flex-wrap: wrap; justify-content: center;'>");

                    state = Integer.parseInt(ObjDvHe[4].toString());
                    for (int i = 1; i < structure.length; i++) {
                        String[] idxnamexico = structure[i].toString().split("/");
                        int id = 0;
                        String name = idxnamexico[1].toString();
                        String ico = idxnamexico[2].toString();
                        if (!idxnamexico[0].toString().equals("A")) {
                            id = Integer.parseInt(idxnamexico[0].toString());
                        }

                        if (i == state) {
                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + "&idDoc=" + id + "&idpcHead=" + "&type=" + structure[i] + "&step=" + i + "\"' style='background: #33bf98; color:#0b0025; cursor: pointer;' data-toggle='tooltip' data-placement='top' title='En proceso'>");
                            out.print("<div class=\"wizard-step-icon\">");
                            out.print("<i class=\"" + ico + "\"></i>");
                            out.print("</div>");
                            out.print("<div class=\"wizard-step-label\">");
                            out.print(name);
                            out.print("<div style='position: absolute;bottom: 2px;left: 47%;'>");
                            out.print("<p style='margin: 0;'><i class=\"fas fa-spinner fa-spin\"></i></p>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        } else if (i > state) {
                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" style='opacity: 0.5;background: #0b002599; cursor: no-drop;' data-toggle='tooltip' data-placement='top' title='Aún no disponible'>");
                            out.print("<div class=\"wizard-step-icon\">");
                            out.print("<i class=\"" + ico + "\"></i>");
                            out.print("</div>");
                            out.print("<div class=\"wizard-step-label\">");
                            out.print(name);
                            out.print("<div style='position: absolute;bottom: 2px;left: 24%;'>");
                            out.print("<p style='margin: 0;'>-&nbsp;Pendiente&nbsp;-</p>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            lst_DeviceDetail = DeviceDetailJpa.ConsultDeviceDetailxDvxType(idDeviceHead, name);
                            if (lst_DeviceDetail != null) {
                                Object[] ObSt = (Object[]) lst_DeviceDetail.get(0);
                                if (ObSt[5].toString().contains("XX")) {
                                    out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + "&idDoc=" + id + "&idpcHead=" + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                    out.print("<div class=\"wizard-step-icon\">");
                                    out.print("<i class=\"" + ico + "\"></i>");
                                    out.print("</div>");
                                    out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                    out.print(name);
                                    out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                    out.print("<p style='margin: 0; width: 170px; background: #ffa426;border-radius: 3px;'><b><b class='text-black'>Pendiente Firma</b></b> &nbsp; <i class='fas fa-signature'></i></p>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                } else {
                                    int steDet = Integer.parseInt(ObSt[6].toString());
                                    if (steDet == 0) {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + "&idDoc=" + id + "&idpcHead=" + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class=\"" + ico + "\"></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print(name);
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #ffa426;border-radius: 3px;'><b><b class='text-warning'>Pendiente Firma</b></b> &nbsp; <i class='fas fa-signature'></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    } else if (steDet == 2) {
//                                    out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + "&idDoc=" + id + "&idpcHead=" + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class=\"" + ico + "\"></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print(name);
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #33bf98;border-radius: 3px;'><b>Realizado</b> &nbsp; <i class=\"fas fa-check\"></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    } else {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class='<i class=\"fas fa-exclamation-triangle\"></i>'></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print("Error");
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #33bf98;border-radius: 3px;'><b>Error</b> &nbsp; <i class=\"fas fa-check\"></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    }
                                }
                            }
                        }

                    }

                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");

                //</editor-fold>
            } else if (action == 2) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE LIST DETAIL">

                //<editor-fold defaultstate="collapsed" desc="REGISTER EVENT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nuevo evento</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Device?opt=3' method='post' class='needs-validation' novalidate=''>");

                out.print("<input type='hidden' name='idTypeDv' value='" + idTypeDv + "'>");
                out.print("<input type='hidden' name='idDevice' value='" + idDevice + "'>");

                out.print("<div class='text-center' style='justify-content: center;'>");
                out.print("<span class='mb-2 mt-2'><b>Fecha</b></span>");
                out.print("<input type='date' style='margin: auto;' class='form-control col-lg-8 mb-2' name='txtDte' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");

                out.print("<div class='text-center mt-4'>");
                out.print("<span class=''><b>Seleccionar tipo de proceso</b></span>");
                out.print("<div class='mt-2'>");
                lst_secuence = SecuenceJpa.ConsultSecuenceByType("Device");
                out.print("<select class='form-control col-lg-8' name='CbxDvType' style='margin: auto;' required>");
                out.print("<option value='' disabled selected >Seleccionar un tipo</option>");
                if (lst_secuence != null) {
                    for (int i = 0; i < lst_secuence.size(); i++) {
                        Object[] ObjAp = (Object[]) lst_secuence.get(i);
                        String struc = ObjAp[2].toString();
                        out.print("<option value='" + struc + "'>" + struc.replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</option>");
                    }

                } else {
                    out.print("<option value='' disabled>Ha ocurrido un error.</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='text-center mt-4'>");
                out.print("<button class='btn btn-green'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="LIST EVENTS">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2> " + nameDevice + " </h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Fecha</th>");
                out.print("<th>Tipo</th>");
                out.print("<th>Usuario Registro</th>");
                out.print("<th>Fecha Registro</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");

                lst_deviceHead = DeviceHead.ConsultDeviceHeaderId(idDevice);
                if (lst_deviceHead != null) {
                    for (int i = 0; i < lst_deviceHead.size(); i++) {
                        Object[] Objdevice = (Object[]) lst_deviceHead.get(i);
                        out.print("<tr>");
                        int sta = Integer.parseInt(Objdevice[4].toString());
                        out.print("<td>" + Objdevice[2] + "</td>");
                        String struc = Objdevice[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0];
                        out.print("<td><b>" + struc + "</b></td>");
                        out.print("<td>" + Objdevice[7] + "</td>");
                        out.print("<td>" + Objdevice[8] + "</td>");
                        String[] states = Objdevice[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        String[] stat = {};
                        if (sta >= states.length) {
                            if (Objdevice[9] != null) {
                                String[] dataDetail = Objdevice[9].toString().split("--");
                                out.print("<td>Pendiente documento por firmar<br> &nbsp; <i class=\"fas fa-signature\"></i> <b class='text-warning'>" + dataDetail[0] + "</b></td>");
                            } else {
                                out.print("<td>Documento Finalizado</td>");
                            }

                        } else {
                            stat = states[sta].split("/");
                            out.print("<td>" + stat[1] + "</td>");
                        }
                        out.print("<td class='text-center'>");
                        out.print("<button class='btn btn-yellow' onclick='window.location.href=\"Device?opt=1&act=3&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDeviceHead=" + Objdevice + "\"'><i class='fas fa-folder-open'></i></button>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td class='text-center' colspan='6'><span style='font-size: 20px; font-weight: bold;'>No se han encontrado eventos, puedes registrar el primero haciendo clic sobre el boton <i class='fas fa-plus'></i></span></td>");
                    out.print("</tr>");
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

                //</editor-fold>
            } else if (action == 1) {
                //<editor-fold defaultstate="collapsed" desc="LIST DEVICE">
                String nameDv = "";
                lst_typeDevice = DeviceJpa.ConsultTypeDeviceId(idTypeDv);
                if (lst_typeDevice != null) {
                    Object[] Objtpe = (Object[]) lst_typeDevice.get(0);
                    nameDv = Objtpe[1].toString();
                } else {
                    nameDv = "";
                }

                //<editor-fold defaultstate="collapsed" desc="DEVICE REGISTER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 50%; right: 18%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar Dispositivos </h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Device?opt=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='idTypeDv' id='' value='" + idTypeDv + "'>");
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
                        out.print("<option value='" + Objitm[1] + "'> " + Objitm[1].toString() + " - " + Objitm[2].toString() + "</option>");
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
                out.print("</form>");

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
                out.print("<div class='text-center'><h2>Listado de dispositivos</h2><h6>" + nameDv + "</h6></div>");
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

                        out.print("<div id='dvDetFront" + i + "' class='textdv' style='display: block;'>");
                        out.print("<span>Area: " + ObjDev[11] + "</span><br>");
                        out.print("<span>Ubicacion: " + ObjDev[9] + "</span><br>");
                        out.print("<span>Cargo: " + ObjDev[7] + "</span><br>");
                        out.print("<span><span class='bullet text-" + ((stedv == 1) ? "success" : (stedv == 2) ? "warning" : "danger") + "'></span> " + ((stedv == 1) ? "Bueno" : (stedv == 2) ? "Revisión" : "De baja") + "</span><br>");
                        out.print("</div>");

                        out.print("<div id='dvDetBack" + i + "' class='textdv dvBack' style='display: none;'>");
                        out.print("<span>Serial: " + ObjDev[6] + "</span><br>");
                        out.print("<span>Usuario registro: " + ObjDev[12] + "</span><br>");
                        out.print("<span>Fecha registro: " + ObjDev[13] + "</span><br>");
                        out.print("</div>");

                        out.print("<div class='gnDiv'>");
                        out.print("<div class='d-flex dvListBtn'>");
                        out.print("<a href='#' onclick='showDetail(" + i + ")'><i id='arrow" + i + "' class='fas fa-chevron-down'></i> Ver detalle</a>");
                        out.print("<button class='btn btn-green btn-sm' onclick='window.location.href=\"Device?opt=1&act=2&idTypeDv=" + idTypeDv + "&idDevice=" + ObjDev[0] + "\"'><i class=\"fas fa-link\"></i></button>");
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
