package Tags;

import Controladores.AreaJpaController;
import Controladores.InstrumentoMedicionJpaController;
import Controladores.NoConformidadJpaController;
import Controladores.TipoInstrumentoJpaController;
import Controladores.TipoVerificacionJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_rnoconforme extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String nombre_Usuario = sesion.getAttribute("Nombre").toString();
        String rol = sesion.getAttribute("Rol").toString();
        boolean Auth = true;
        if (rol.equals("ADMINISTRADOR") || rol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }
        TipoInstrumentoJpaController jpa_tipoI = new TipoInstrumentoJpaController();
        TipoVerificacionJpaController jpa_tipoV = new TipoVerificacionJpaController();
        InstrumentoMedicionJpaController jpa_instrumento = new InstrumentoMedicionJpaController();
        TipoInstrumentoJpaController jpa_TipoInst = new TipoInstrumentoJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        NoConformidadJpaController jpa_noconforme = new NoConformidadJpaController();
        Date fecha = new Date();
        double hora_minuto = Double.parseDouble(fecha.getHours() + "." + fecha.getMinutes());
        String anio = (fecha.getYear() + 1900) + "";
        String mes = fecha.getMonth() + 1 + "";
        String dia = (fecha.getDate() < 10 ? "0" : "") + "" + fecha.getDate() + "";
        String fecha_actual = anio + "-" + mes + "-" + dia;
        boolean validSerial = false;
        if (pageContext.getRequest().getAttribute("Consulta_serial") != null) {
            validSerial = true;
        }
        try {
            List lst_noconforme = jpa_noconforme.consultasRgtNoConformes();
            int cns = 0;
            if (pageContext.getRequest().getAttribute("Visor_plantilla") == null) {

                //<editor-fold defaultstate="collapsed" desc="REGISTRAR NO CONFORME">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + ((validSerial) ? "block" : "none") + "'>");
                out.print("<div class='cont_reg' style='width:35%; margin-left: 41%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar </h2>");
                if (validSerial) {
                    out.print("<a href='Noconforme?opc=1' class='btn btn-outline-secondary' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></a>");
                } else {
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                }
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Noconforme?opc=2' method='post'>");
                out.print("<div class='col-lg-12'>");
                out.print("<input type='text' class='form-control' name='txtserial' id='txtserial' placeholder='Buscar serial...' data-toggle='tooltip' data-placement='top' title='Buscar serial' style='width: 97%;'>");
                out.print("</div>");
                out.print("</form>");

                if (validSerial) {
                    List lst_Serial = (List) pageContext.getRequest().getAttribute("Consulta_serial");
                    List lst_consecutivos = jpa_noconforme.consultaConsecutivos();
                    out.print("<div class='col-lg-12'>");
                    out.print("<b class='subTitle mb-2'>Instrumento</b>");
                    out.print("<form action='Noconforme?opc=3' method='post'>");
                    out.print("<div class='mt-2' data-toggle='tooltip' data-placement='top' title='Instrumento'>");
                    out.print("<select class='form-control' id='idlst_serial' name='lst_serial' style='width: 97%;'>");
                    if (validSerial == false) {
                        out.print("<option value='0'>Seleccionar instrumento</option>");
                    }
                    for (int i = 0; i < lst_Serial.size(); i++) {
                        Object[] obj_Serial = (Object[]) lst_Serial.get(i);
                        out.print("<option value='" + obj_Serial[0] + "'>" + obj_Serial[1] + " - " + obj_Serial[2] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Fecha'>");
                    out.print("<input type='date' class='form-control' name='txtfecha' value='" + fecha_actual + "' style='width: 97%;'>");
                    out.print("</div>");
                    out.print("<div class=''>");
                    if (lst_consecutivos == null) {
                        cns = 1;
                    } else {
                        Object[] obj_Consecutivo = (Object[]) lst_consecutivos.get(0);
                        cns = (Integer) obj_Consecutivo[1];
                        cns = cns + 1;
                    }
                    out.print("<input type='text' class='form-control' name='txtconsecutivo' value='" + cns + "' style='width: 97%;' data-toggle='tooltip' data-placement='top' title='Consecutivo'>");
                    out.print("</div>");
                    out.print("<div class='' style='text-align: center;'>");
                    out.print("<button class='btn btn-green'>Registrar</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                } else if (pageContext.getRequest().getAttribute("Consulta_serialvacio") != null) {
                    out.print("<div class='col-lg-12'>");
                    out.print("<b class='text-danger'>Serial no encontrado</b><br>");
                    out.print("<span><i class='fas fa-exclamation-triangle'></i></span>");

                    out.print("</div>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="CONSULTA PRINCIPAL">
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo registro no conforme</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Listado de registros</h4>");
                if (Auth) {
                    out.print("<button class='btn btn-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
                } else {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'  style='align-items: center;'>");
                out.print("<thead>");
                out.print("<tr style='text-align: center;'>");
                out.print("<th>Serial</th>");
                out.print("<th>Nombre</th>");
                out.print("<th>Fecha</th>");
                out.print("<th>Consecutivo</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Registro</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_noconforme != null) {
                    for (int i = 0; i < lst_noconforme.size(); i++) {
                        Object[] obj_noconforme = (Object[]) lst_noconforme.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_noconforme[1] + "</td>");
                        out.print("<td>" + obj_noconforme[2] + "</td>");
                        out.print("<td>" + obj_noconforme[3] + "</td>");
                        out.print("<td>" + obj_noconforme[4] + "</td>");
                        int estado = Integer.parseInt(obj_noconforme[6].toString());
                        if (estado == 0) {
                            out.print("<td align='center'><span class='text-warning' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Elaboracion'><i class='fas fa-envelope' style='font-size: 20px;'></i></span></td>");
                        } else if (estado == 1) {
                            out.print("<td align='center'><span class='text-info' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Finalizado'><i class='fas fa-envelope-open' style='font-size: 20px;'></i></span></td>");
                        } else if (estado == 2) {
                            out.print("<td align='center'><span class='text-dark' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Correo Enviado'><i class='fas fa-envelope' style='font-size: 20px;'></i></span></td>");
                        } else if (estado == 3) {
                            out.print("<td align='center'><span class='text-warning' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='En Revision'><i class='fas fa-envelope-open' style='font-size: 20px;'></i></span></td>");
                        } else if (estado == 4) {
                            out.print("<td align='center'><span class='text-success' style='cursor: pointer;' data-toggle='tooltip' data-placement='top' title='Revisado'><i class='fas fa-envelope-open' style='font-size: 20px;'></i></span></td>");
                        }
                        out.print("<td align='center'><a class='btn btn-white' href='Noconforme?opc=4&id=" + obj_noconforme[5] + "'><i class='fas fa-eye'></i></a><br /></td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='6'>No se ha encontrado información.</td>");
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
            } else {
                //<editor-fold defaultstate="collapsed" desc="DETALLE DE REGISTRO">
                List lst_visor = (List) pageContext.getRequest().getAttribute("Visor_plantilla");
                List lst_plantilla = (List) pageContext.getRequest().getAttribute("plantilla");
                String plantilla = "";
                Object[] obj_visor = (Object[]) lst_visor.get(0);
                Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                String plantilla1 = obj_plantilla[3].toString();

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='width: 35%;margin-left: 42%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Seleccionar usuarios</h3>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-green btn-sm mr-2' id='btn_enviar' onclick='SendMail();EnvioCorreoCarga();' style='display: none;' data-toggle='tooltip' data-placement='top' title='Enviar'><i class='fas fa-share'></i></button>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_form_user' style='max-height: 530px;overflow-y: scroll;'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered table-sm' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Usuario</th>");
                out.print("<td>Revisa</td>");
                out.print("<td>Notificar</td>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                List mail = jpa_noconforme.mailNoConforme();
                if (mail != null) {
                    for (int i = 0; i < mail.size(); i++) {
                        Object[] obj_mail = (Object[]) mail.get(i);
                        out.print("<tr>");
                        out.print("<td ><b style='color:black' >" + obj_mail[0] + " " + obj_mail[1] + "</b></td>");
                        out.print("<td align='center'><input type='radio' id='Rdb_destino' name='Rdb_destino' onchange='habilitar(this.value);' onclick='SeleccionDestino(this,chk_destino" + i + ");' value='[" + obj_mail[3] + "]' ></td>");
                        out.print("<td align='center'><input type='checkbox' id='chk_destino" + i + "' name='chk_destino' onclick='SeleccionDestinatarios(this);' value='[" + obj_mail[3] + "]' ></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");

                out.print("<form action='Noconforme?opc=7&idi=" + obj_visor[0] + "&idrgt=" + obj_visor[4] + "' method='post' id='FormMail' >");
                out.print("<input id='txt_destinatarios' name='txt_destinatarios' type='hidden' >");
                out.print("<input id='txt_destino' name='txt_destino' type='hidden' >");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<link type='text/css' rel='stylesheet' href='Interfaz/HTML_Editor/jquery-te-1.4.0.css'>");
                out.print("<script type='text/javascript' src='Interfaz/HTML_Editor/jquery-te-1.4.0.min.js' charset='utf-8'></script>");
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo registro no conforme</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Listado de registros</h4>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                if (!Auth) {
                    int estado = (Integer) obj_visor[5];
                    out.print("<div class='mb-3' style='float: right;'>");
                    if (estado == 1) {
                        plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-green' onclick='mostrarConvencion(1)'><i class='fas fa-envelope'></i></button>");
                        out.print("</div>");
                    } else if (estado == 2) {
                        plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<b style='color:black;'> CORREO ENVIADO </b>");
                    } else if (estado == 0) {
                        if (obj_visor[3] == null) {
                            out.print("<a href='#' onclick='platilla()' class='btn btn-green' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></a>");
                            plantilla = plantilla1;
                        } else {
                            plantilla = obj_visor[3].toString();
                            out.print("<a href='Noconforme?opc=6&id=" + obj_visor[4] + "&idins=" + obj_visor[0] + "' class='btn btn-green mr-2' data-toggle='tooltip' data-placement='top' title='Finalizar'><i class='fas fa-check'></i></a>");
                            out.print("<a href='#' onclick='platilla()' class='btn btn-green' data-toggle='tooltip' data-placement='top' title='Guardar'><i class='fas fa-save'></i></a>");
                        }
                    } else if (estado == 3) {
                        plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<b style='color:#FACC2E;'> EN REVISION </b>");
                    } else if (estado == 4) {
                        plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<b style='color:#68BB18;'> REVISADO </b>");
                    }
                    out.print("</div>");
                } else {
                    out.print("<div class='float-right'>");
                    int estado = (Integer) obj_visor[5];
                    if (obj_visor[3] != null) {
                        plantilla = obj_visor[3].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                    }
                    if (estado == 1) {
                        out.print("<b style='color:#819FF7;'> FINALIZADO </b>");
                    } else if (estado == 2) {
                        out.print("<b style='color:black;'> CORREO ENVIADO </b>");
                    } else if (estado == 0) {
                        out.print("<b style='color:#FE9A2E;'> EN ELABORACION </b>");
                    } else if (estado == 3) {
                        out.print("<b style='color:#FACC2E;'> EN REVISION </b>");
                    } else if (estado == 4) {
                        out.print("<b style='color:#68BB18;'> REVISADO </b>");
                    }
                    out.print("</div>");
                }

                out.print("<div class='' style='display: flex;'>");
                out.print("<a href='Noconforme?opc=1' class='btn btn-green mr-3' data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></a>");
                out.print("<div><b style='color: black;'><span>" + obj_visor[1] + "</span></b>&nbsp;&nbsp;&nbsp;<b class='subTitle2'>Serial:&nbsp;</b> " + obj_visor[2] + "</div>");
                out.print("</div>");

                out.print("<div class=''>");
                out.print("<textarea name='textarea' id='htmleditor-id' class='jqte-test'>" + plantilla + "</textarea>");
                out.print("</div>");

                out.print("<script>");
                out.print("$('.jqte-test').jqte();");
                out.print(" var jqteStatus = true;");
                out.print("$('.status').click(function()");
                out.print("{");
                out.print("jqteStatus = jqteStatus ? false : true;");
                out.print("$('.jqte-test').jqte({'status' : jqteStatus})");
                out.print(" });");
                out.print("</script>");

                out.print("<form action='Noconforme?opc=5' method='post' id='formP' name='formP'>");
                out.print("<input type='hidden' name='txt_plantilla' id='plantilla-id' >");
                out.print("<input type='hidden' name='idrgt' value=" + obj_visor[4] + " >");
                out.print("<input type='hidden' name='idins' value=" + obj_visor[0] + " >");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
//                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_rnoconforme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
