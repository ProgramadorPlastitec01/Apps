package Tags;

import Controladores.PlanoJpaController;
import Controladores.VerificarEtdJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_plano extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        PlanoJpaController jpa_plano = new PlanoJpaController();
        VerificarEtdJpaController jpa_itemVer = new VerificarEtdJpaController();
        String rol = sesion.getAttribute("Rol").toString();
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);
        List lst_planos = null;
        List lst_plano = null;
        List lst_items = null;
        List lst_itemsPln = null;
        List lst_itmVerPln = null;
        int id_plano = 0;
        try {
            try {
                id_plano = Integer.parseInt(pageContext.getRequest().getAttribute("id_plano").toString());
            } catch (Exception e) {
                id_plano = 0;
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Plano</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Plano</h4>");
            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)'  data-toggle=\"tooltip\" data-placement='top' title='' data-original-title='Registar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            if (id_plano != 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PLANO">
                lst_plano = jpa_plano.consultaPlanoId(id_plano);
                Object[] obj_plano = (Object[]) lst_plano.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_plano'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar plano</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<form action='Plano?opc=3' onsubmit='modificarP();' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='idP' value='" + id_plano + "'>");

                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-12'>");
                out.print("<input type='date' class='form-control' name='txt_fecha' id='fecha-id' placeholder='Fecha' value='" + obj_plano[3] + "' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Nombre'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title='Tipo'>");
                out.print("<select class='form-control' name='slc_tipo' style='margin-top: 12px;margin-bottom: 12px;'>");
                out.print("<option value='" + obj_plano[2] + "'>" + obj_plano[2] + "</option>");
                out.print("<option value='Electrodo'>Electrodo</option>");
                out.print("<option value='Platinas'>Platinas</option>");
                out.print("<option value='Moldes'>Moldes</option>");
                out.print("<option value='Grafadoras'>Grafadoras</option>");
                out.print("<option value='Otros'>Otros</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                if (obj_plano[1].toString().contains("-")) {
                    String[] arg_piezas = obj_plano[1].toString().split("-");
                    out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' class='form-control' name='txt_letraP' id='txt_letraP' value='" + arg_piezas[0] + "' placeholder='Letra' required data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Letra del plano'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' class='form-control' name='txt_numeroP' id='txt_numeroP' placeholder='Numero' value='" + arg_piezas[1] + "' required='' data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Numero del plano'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' class='form-control' name='txt_letraP' id='txt_letraP' value='" + obj_plano[1] + "' placeholder='Letra' required data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Letra del plano'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' class='form-control' name='txt_numeroP' id='txt_numeroP' placeholder='Numero' value='' required='' data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Numero del plano'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                }

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR PLANO">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_plano'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar plano</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Plano?opc=2' onsubmit='registroP();' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
            out.print("<div class='col-12'>");
            out.print("<input type='date' class='form-control' name='txt_fecha' id='fecha-id' placeholder='Fecha' value='" + fechaFormateada + "' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Nombre'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
            out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title='Tipo'>");
            out.print("<select class='form-control' name='slc_tipo' required style='margin-top: 12px;margin-bottom: 12px;'>");
            out.print("<option selected disabled value=''>Seleccione tipo</option>");
            out.print("<option value='Electrodo'>Electrodo</option>");
            out.print("<option value='Platinas'>Platinas</option>");
            out.print("<option value='Moldes'>Moldes</option>");
            out.print("<option value='Grafadoras'>Grafadoras</option>");
            out.print("<option value='Otros'>Otros</option>");
            out.print("</select>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-12 col-md-12' style='display: flex;'>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='txt_letraP' id='txt_letraP' placeholder='Letra' required data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Letra del plano'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='txt_numeroP' id='txt_numeroP' placeholder='Numero' required='' data-toggle='tooltip' autocomplete='off' data-placemente='top' title='Numero del plano'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Fecha</th>");
            out.print("<th style='width:60%'>Nombre plano</th>");
            out.print("<th>Tipo</th>");
            out.print("<th>Modificar</th>");
            out.print("<th>Verificar</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_planos = jpa_plano.consultaPlanos();
            if (lst_planos != null) {
                //<editor-fold defaultstate="collapsed" desc="TABLA PLANO">
                for (int i = 0; i < lst_planos.size(); i++) {
                    Object[] obj_plano = (Object[]) lst_planos.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_plano[3] + "</td>");
                    out.print("<td>" + obj_plano[1] + "</td>");
                    out.print("<td>" + obj_plano[2] + "</td>");
                    out.print("<td align='center' ><a href='Plano?opc=1&idP=" + obj_plano[0] + "' class=\"btn btn-red\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Editar\"><i class=\"fas fa-pencil-alt\"></i></a></td>");
                    if (rol.equals("MTF") || rol.equals("ADMIN")) {
                        lst_itemsPln = jpa_plano.consultaItemsIdplano((Integer) obj_plano[0]);
                        if (lst_itemsPln == null) {
                            if (obj_plano[2].equals("Electrodo")) {
                                out.print("<td align='center'><a style='color:white' onclick='mostrarPlano(" + i + ")' class=\"btn btn-warning\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Registrar\"><i class=\"fas fa-plus\"></i></a></td>");
                            } else {
                                out.print("<td style='background-color:#ccc'></td>");
                            }
                        } else {
                            if (obj_plano[2].equals("Electrodo")) {
                                out.print("<td align='center'><a style='color:white' onclick='mostrarPlano(" + i + ")' class=\"btn btn-info\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Verificar\"><i class=\"fas fa-check-circle\"></i></a></td>");
                            } else {
                                out.print("<td style='background-color:#ccc'></td>");
                            }
                        }
                    } else {
                        out.print("<td align='center' ><button class=\"btn btn-white\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Sin permisos de verificar\"><i class=\"fas fa-exclamation\"></i></button></td>");
                    }
                    out.print("</tr>");
                }
                //</editor-fold>
            }
            out.print("</tbody>");
            out.print("</table>");
            for (int i = 0; i < lst_planos.size(); i++) {
                Object[] obj_plano = (Object[]) lst_planos.get(i);
                //<editor-fold defaultstate="collapsed" desc="Mostrar Plano - Calificacion Plano">
                out.print("<div class='sweet-local' tabindex='-1' id='Plano" + i + "' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_plano_VC'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Plano " + obj_plano[1] + "</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarPlano(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (obj_plano[2].equals("Electrodo")) {
                    lst_items = jpa_itemVer.consultaItemsVerificacion();
                    lst_itemsPln = jpa_plano.consultaItemsIdplano((Integer) obj_plano[0]);
                    out.print("<table class='table table-striped table-hover' style='width:100%'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='position: sticky;top: 0;background:#e2e2e2;' scope=\"col\">Descripción</th>");
                    out.print("<th style='position: sticky;top: 0;background:#e2e2e2;' scope=\"col\">Medida / Standard </th>");
                    out.print("<th style='position: sticky;top: 0;background:#e2e2e2;' scope=\"col\">Cumple</th>");
                    out.print("<th  style='position: sticky;top: 0;background:#e2e2e2;'scope=\"col\">Aplica</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    if (lst_itemsPln != null) {
                        lst_itmVerPln = jpa_plano.consultaItemsVerificadosIdPlano((Integer) obj_plano[0]);
                        Object[] obj_itmVerPln = (Object[]) lst_itmVerPln.get(0);
                        if (Integer.parseInt(obj_itmVerPln[0].toString()) == Integer.parseInt(obj_itmVerPln[1].toString())) {
                            for (int j = 0; j < lst_itemsPln.size(); j++) {
                                Object[] obj_itmPln = (Object[]) lst_itemsPln.get(j);
                                out.print("<tr scope=\"row\">");
                                out.print("<td>" + obj_itmPln[3] + "</td>");
                                out.print("<td>" + obj_itmPln[4] + "</td>");
                                out.print("<td>" + obj_itmPln[5] + "</td>");
                                out.print("<td>" + obj_itmPln[6] + "</td>");
                                out.print("</tr>");
                            }
                        } else {
                            int cont = 0;
                            out.print("<form action='Plano?opc=4&idP=" + obj_plano[0] + "&nomP=" + obj_plano[1] + "' method='post' id='form1_" + i + "' name='form1_" + i + "'>");
                            out.print("<input type='hidden' name='numI' value='" + (cont + 1) + "'>");
                            for (int j = 0; j < lst_items.size(); j++) {
                                Object[] obj_items = (Object[]) lst_items.get(j);
                                if (cont <= (Integer.parseInt(obj_itmVerPln[1].toString()) - 1)) {
                                    Object[] obj_itmPln = (Object[]) lst_itemsPln.get(cont);
                                    out.print("<tr scope=\"row\">");
                                    out.print("<td>" + obj_itmPln[3] + "</td>");
                                    out.print("<td>" + obj_itmPln[4] + "</td>");
                                    out.print("<td>" + obj_itmPln[5] + "</td>");
                                    out.print("<td>" + obj_itmPln[6] + "</td>");
                                    out.print("</tr>");
                                    cont++;
                                } else {
                                    out.print("<tr scope=\"row\">");
                                    out.print("<td>" + obj_items[1] + "</td>");
                                    out.print("<td>" + obj_items[2] + "</td>");
                                    out.print("<td><select class='form-control' name='slc_cumple_" + j + "' id='selectC-id' style='width:70px;'><br />");
                                    out.print("<option>SI</opction>");
                                    out.print("<option>NO</opction>");
                                    out.print("</select></td>");
                                    out.print("<td><select class='form-control' name='slc_aplica_" + j + "' id='selectA-id' style='width:70px;'><br />");
                                    out.print("<option>SI</opction>");
                                    out.print("<option>NO</opction>");
                                    out.print("</select></td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("<tr scope=\"row\">");
                            out.print("<td align='center' colspan='4'><input type='submit' value='Guardar' id='guardar' ></td>");
                            out.print("</tr>");
                            out.print("</form>");
                        }
                    } else {
                        out.print("<form action='Plano?opc=4&idP=" + obj_plano[0] + "&nomP=" + obj_plano[1] + "' method='post' id='form1_" + i + "' name='form1_" + i + "'>");
                        out.print("<input type='hidden' name='numI' value='0'>");
                        for (int j = 0; j < lst_items.size(); j++) {
                            Object[] obj_items = (Object[]) lst_items.get(j);
                            out.print("<tr scope=\"row\">");
                            out.print("<td>" + obj_items[1] + "</td>");
                            out.print("<td>" + obj_items[2] + "</td>");
                            out.print("<td><select name='slc_cumple_" + j + "' class='form-control' id='selectC-id' style='width:70px;'><br />");
                            out.print("<option>SI</opction>");
                            out.print("<option>NO</opction>");
                            out.print("</select></td>");
                            out.print("<td><select name='slc_aplica_" + j + "' class='form-control' id='selectA-id' style='width:70px;'><br />");
                            out.print("<option>SI</opction>");
                            out.print("<option>NO</opction>");
                            out.print("</select></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr scope=\"row\">");
                        out.print("<td align='center' colspan='4'><input class=\"btn btn-red\" style=\"border-radius: 4px;\" type='submit' value='Guardar' id='guardar' ></td>");
                        out.print("</tr>");
                        out.print("</form>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_plano.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
