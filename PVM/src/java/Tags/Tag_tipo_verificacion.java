package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import Controladores.TipoVerificacionJpaController;

public class Tag_tipo_verificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        HttpSession sesion = pageContext.getSession();
        String UserName = "";
        String UserRol = "";
        UserName = pageContext.getSession().getAttribute("Nombre").toString();
        UserRol = pageContext.getSession().getAttribute("Rol").toString();
        boolean Auth = true;
        if (UserRol.equals("ADMINISTRADOR") || UserRol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }

        JspWriter out = pageContext.getOut();
        String rol = sesion.getAttribute("Rol").toString();
        TipoVerificacionJpaController TipoVeriJpa = new TipoVerificacionJpaController();
        List lst_tipoVerifi = null;
        int id_tipoV = Integer.parseInt(pageContext.getRequest().getAttribute("idTV").toString());
        try {
            if (id_tipoV > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR TIPO DE VERIFICACION">
                lst_tipoVerifi = TipoVeriJpa.consultaTipoVerificacionId(id_tipoV);
                if (lst_tipoVerifi != null) {
                    Object[] obj_tipo = (Object[]) lst_tipoVerifi.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='width: 30%;text-align: center;margin-left: 41%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar tipo verificación</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Tipo_verificacion?opc=3&idTV=" + obj_tipo[0] + "' method='post'>");
                    out.print("<div class='col-lg-12'>");
                    out.print("<input type='text' name='txt_tipo' class='form-control' style='width: 95%;' placeholder='Tipo verificación' data-toggle='tooltip' data-placement='top' title='Tipo verificación' value='" + obj_tipo[1] + "' required>");
                    out.print("</div>");
                    out.print("<div class=''>");
                    out.print("<button class='btn btn-green'>Modificar</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 30%;text-align: center;margin-left: 41%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar tipo verificación</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<h3>Ha ocurrido un problema con la consulta de datos, favor comunicarse a TI.</h3>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR TIPO DE VERIFICACION">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 30%;text-align: center;margin-left: 41%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar tipo verificación</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Tipo_verificacion?opc=2' method='post'>");
            out.print("<div class='col-lg-12'>");
            out.print("<input type='text' class='form-control' style='width: 95%;' name='txt_tipo' placeholder='Tipo verificación' data-toggle='tooltip' data-placement='top' title='Tipo verificación' required>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<button class='btn btn-green'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONSULTA PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo tipo verificación</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de tipos de verificaciones</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (Auth) {
                out.print("<button class='btn btn-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered table-striped' id='table-1'>");
            out.print("<thead>");
            out.print("<tr class='centrar_fila'>");
            out.print("<th>Tipo</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Modificar</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_tipoVerifi = TipoVeriJpa.consultaTipoVerificacion();
            if (lst_tipoVerifi != null) {
                for (int i = 0; i < lst_tipoVerifi.size(); i++) {
                    Object[] obj_verf = (Object[]) lst_tipoVerifi.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_verf[1] + "</td>");
                    int est = Integer.parseInt(obj_verf[2].toString());

                    if (Auth) {
                        out.print("<td align='center'>"
                                + "<a href='#' id='btn_add' class='btn btn-secondary' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a></td>"
                                + "<td align='center'><a href='#' class='btn btn-secondary btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> ");
                    } else {
                        out.print("<td align='center'><a href='Tipo_verificacion?opc=4&idTV=" + obj_verf[0] + "&est=" + ((est == 1) ? 0 : 1) + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a></td>");
                        out.print("<td align='center'><a href='Tipo_verificacion?opc=1&idTV=" + obj_verf[0] + "' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a></td>");
                    }

                    out.print("</tr>");
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
        } catch (Exception ex) {
            Logger.getLogger(Tag_tipo_verificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
