package Tags;

import Controladores.CargoJpaController;
import Controladores.PermisosJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_permisos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        PermisosJpaController jpa_permisos = new PermisosJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
        int id_permiso = 0;
        String txt_permisos = "";
        List lst_permisos = null, lst_cargos = null;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES DE SESION">
        HttpSession sesion = pageContext.getSession();
        int id_cargo = Integer.parseInt(sesion.getAttribute("id_position").toString());
        //</editor-fold>

        try {

            try {
                id_permiso = Integer.parseInt(pageContext.getRequest().getAttribute("idPerm").toString());
            } catch (Exception e) {
                id_permiso = 0;
            }
            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_cargo = 0;
                txt_permisos = "";
            }

            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>");
            out.print("Permisos");
            out.print("</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>");
            out.print("Listado de permisos");
            out.print("</h4>");
            if (txt_permisos.contains("[49]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class:'card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<div class='cuerpo mx-5 mt-3'>");

            //<editor-fold defaultstate="collapsed" desc="TABLA DE PERMISOS">
            out.print("<table class='table table-bordered table-hover' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>");
            out.print("M&oacute;dulo");
            out.print("</th>");
            out.print("<th>");
            out.print("Opci&oacute;n");
            out.print("</th>");
            out.print("<th>");
            out.print("Descripci&oacute;n");
            out.print("</th>");
            out.print("<th>");
            out.print("Modificar");
            out.print("</th>");
            out.print("<th>");
            out.print("Estado");
            out.print("</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_permisos = jpa_permisos.Consult_Allpermissions();
            if (lst_permisos != null || lst_permisos.size() > 0 || lst_permisos.isEmpty()) {
                for (int i = 0; i < lst_permisos.size(); i++) {
                    Object[] obj_lst_permisos = (Object[]) lst_permisos.get(i);
                    out.print("<tr>");
                    out.print("<td " + ((Integer.parseInt(obj_lst_permisos[4].toString()) == 1) ? "" : "class='text-danger'") + ">");
                    out.print(obj_lst_permisos[1]);
                    out.print("</td>");
                    out.print("<td " + ((Integer.parseInt(obj_lst_permisos[4].toString()) == 1) ? "" : "class='text-danger'") + ">");
                    out.print(obj_lst_permisos[2]);
                    out.print("</td>");
                    out.print("<td " + ((Integer.parseInt(obj_lst_permisos[4].toString()) == 1) ? "" : "class='text-danger'") + ">");
                    out.print(obj_lst_permisos[3]);
                    out.print("</td>");
                    out.print("<td class='text-center'>");
                    if (txt_permisos.contains("[50]")) {
                        if (Integer.parseInt(obj_lst_permisos[4].toString()) == 1) {
                            out.print("<a href='Permisos?opc=1&idPerm=" + obj_lst_permisos[0] + "' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar registro'><i class='fas fa-pen fa-lg'></i></a>");
                        } else if (Integer.parseInt(obj_lst_permisos[4].toString()) == 0) {
                            out.print("<i class='fas fa-ban fa-lg' style='color:#FF0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                        }
                    } else {
                        out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos para realizar esta acción'></i>");
                    }
                    out.print("</td>");
                    out.print("<td class='text-center'>");
                    if (txt_permisos.contains("[51]")) {
                        if (Integer.parseInt(obj_lst_permisos[4].toString()) == 1) {
                            out.print("<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Inactivar registro' onclick='InactivarPermisos(" + obj_lst_permisos[0] + ",0)'><i class='fas fa-check fa-lg'></i></button>");
                        } else if (Integer.parseInt(obj_lst_permisos[4].toString()) == 0) {
                            out.print("<button type='button' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar registro' onclick='ActivarUsuario(" + obj_lst_permisos[0] + ",1)'><i class='fas fa-times fa-lg'></i></button>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    } else {
                        out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos para realizar esta acción'></i>");
                    }
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            //</editor-fold>

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("</div>");
            out.print("</div>");

            out.print("</section>");

            //<editor-fold defaultstate="collapsed" desc="REGISTRAR PERMISO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<h4 style='color:black;'>Registrar</h4>");
            out.print("<hr>");
            out.print("<form action='Permisos?opc=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='form-group'>");
//            out.print("<div class='row'>");
//            out.print("<div class='col'>");
//            out.print("<label for='Mod_permisos'><span style='color:black;'>M&oacute;dulo</span></label>");
//            out.print("</div>");
//            out.print("<div class='col'>");
//            out.print("<label for='Opc_permisos'><span style='color:black;'>Opci&oacute;n</span></label>");
//            out.print("</div>");
//            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<input class='form-control' list='permisos' name='Mod_permisos' id='Mod_permisos' placeholder='Módulo' data-toggle='tooltip' data-placemente='top' title='Módulo' required>");
            out.print("<datalist id='permisos'>");
            lst_permisos = null;
            lst_permisos = jpa_permisos.traer_modulos();
            if (lst_permisos != null || lst_permisos.size() > 0 || !lst_permisos.isEmpty()) {
                for (int mp = 0; mp < lst_permisos.size(); mp++) {
                    Object[] obj_lst_perm_modulo = (Object[]) lst_permisos.get(mp);
                    out.print("<option value='" + obj_lst_perm_modulo[0] + "'>");
                }
            }
            out.print("</datalist>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col'>");
            out.print("<input class='form-control' name='Opc_permisos' id='Opc_permisos' placeholder='Opción' data-toggle='tooltip' data-placemente='top' title='Opción' required>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<br>");
//            out.print("<div class='row'>");
//            out.print("<div class='col'>");
//            out.print("<label for='Desc_permisos'><span style='color:black;'>Descripci&oacute;n</span></label>");
//            out.print("</div>");
//            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<textarea class='form-control' name='Desc_permisos' id='Desc_permisos' style='margin-left: 0%;' placeholder='Descripión' data-toggle='tooltip' data-placemente='top' title='Descripción' required></textarea>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='row'>");
            out.print("<div class='col text-center'>");
            out.print("<button type='submit' class='btn btn-success'>Enviar</button>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            if (id_permiso > 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PERMISO">
                lst_permisos = null;
                lst_permisos = jpa_permisos.Consult_permissions_id(id_permiso);
                if (lst_permisos != null) {
                    Object[] obj_lst_id_permisos = (Object[]) lst_permisos.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<h4 style='color:black;'>Modificar permiso</h4>");
                    out.print("<hr>");
                    out.print("<form action='Permisos?opc=3' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='text' name='id' value='" + id_permiso + "' required hidden/>");
                    out.print("<input type='text' name='estado' value='" + obj_lst_id_permisos[4] + "' required hidden/>");
                    out.print("<div class='form-group'>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col'>");
//                    out.print("<label for='Mod_permisos'><span style='color:black;'>M&oacute;dulo</span></label>");
//                    out.print("</div>");
//                    out.print("<div class='col'>");
//                    out.print("<label for='Opc_permisos'><span style='color:black;'>Opci&oacute;n</span></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' list='permisos' name='Mod_permisos' id='Mod_permisos' value='" + obj_lst_id_permisos[1] + "' placeholder='Módulo' data-toggle='tooltip' data-placemente='top' title='Módulo' required>");
                    out.print("<datalist id='permisos'>");
                    lst_permisos = null;
                    lst_permisos = jpa_permisos.traer_modulos();
                    if (lst_permisos != null || lst_permisos.size() > 0 || !lst_permisos.isEmpty()) {
                        for (int mp = 0; mp < lst_permisos.size(); mp++) {
                            Object[] obj_lst_perm_modulo = (Object[]) lst_permisos.get(mp);
                            out.print("<option value='" + obj_lst_perm_modulo[0] + "'>");
                        }
                    }
                    out.print("</datalist>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' name='Opc_permisos' id='Opc_permisos' value='" + obj_lst_id_permisos[2] + "' placeholder='Opción' data-toggle='tooltip' data-placemente='top' title='Opción' required>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col'>");
//                    out.print("<label for='Desc_permisos'><span style='color:black;'>Descripci&oacute;n</span></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<textarea class='form-control' name='Desc_permisos' id='Desc_permisos' style='margin-left: 0%;' placeholder='Descripión' data-toggle='tooltip' data-placemente='top' title='Descripción' required>" + obj_lst_id_permisos[3] + "</textarea>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col text-center'>");
                    out.print("<button type='submit' class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }

        } catch (Exception ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
