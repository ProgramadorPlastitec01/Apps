package Vista;

import Controlador.AreaJpaController;
import Controlador.ManualJpaController;
import Email.Connection_mysql_daruma;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            Connection_mysql_daruma cnx_daruma = new Connection_mysql_daruma();
            ManualJpaController jpa_manual = new ManualJpaController();
            List lst_documentosD = null;
            List lst_documentosC = null;
            String filtro = "";
            int id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            String sigla = sesion.getAttribute("l_sgt").toString();
            int daruma = 0;
            out.print("<div id='templatemo_header'>");
            out.print("<div style='float:right'><img src='Interfaz/Template/images/Copec.png' alt='Logo' width='140.9' height='99.9'></div>");
            if (Integer.parseInt(sesion.getAttribute("l_id_capacitador").toString()) == 9) {
                //if (Integer.parseInt(sesion.getAttribute("l_id_capacitador").toString()) == 9) {
                AreaJpaController obj_area = new AreaJpaController();
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();'><b>Consultor(a) " + pageContext.getSession().getAttribute("l_sgt").toString().toUpperCase() + "/</b><b class='negro'>" + pageContext.getSession().getAttribute("l_capacitador").toString().toUpperCase() + "</b></a></h1></div>");
                out.print("<form method='post' action='Login?opc=2' name='enviarCon' id='enviarCon'>");
                out.print("<select style='float: right;' name='slt_area' id='area-id' onchange='document.enviarCon.submit()'>");
                out.print("<option value='0'>SELECCIONE CONSULTA</option>");
                for (int i = 0; i < obj_area.ara_c_a_area().size(); i++) {
                    Object[] obj_ara = (Object[]) obj_area.ara_c_a_area().get(i);
                    if ((Integer) obj_ara[3] == 1) {
                        if (Integer.parseInt(sesion.getAttribute("l_id_area").toString()) == Integer.parseInt(obj_ara[0].toString())) {
                            out.print("<option value='" + obj_ara[0] + "' selected>" + obj_ara[2] + " / " + obj_ara[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_ara[0] + "'>" + obj_ara[2] + " / " + obj_ara[1] + "</option>");
                        }
                    }
                }
                out.print("</select><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('area-id');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                out.print("</script>");
                out.print("</form>");
            } else {
                out.print("<div id='site_title'><h1><a href='#' onclick='CerrarSesion();'><b>Capacitador(a) " + pageContext.getSession().getAttribute("l_sgt").toString().toUpperCase() + "/</b><b class='negro'>" + pageContext.getSession().getAttribute("l_capacitador").toString().toUpperCase() + "</b></a></h1></div>");
            }
            out.print("</div>");
            out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
            out.print("<ul>");
            out.print("<li><a href='Login?opc=3'>Inicio</a></li>");
            out.print("<li><a href='Capacitacion?opc=1&txt_bus=" + filtro + "'>Programar</a></li>");
            out.print("<li><a href='Empleado?opc=1'>Personal</a></li>");
            out.print("<li><a href='Manual?opc=1'>Documento</a></li>");
            out.print("<li><a href='Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 0 + "&txt_bus='>Calificación</a></li>");
            out.print("<li><a href='#'>Reportes</a>");
            out.print("<ul>");
            out.print("<li><a href='Reporte?opc=1'>Reporte_prueba</a></li>");
            out.print("</ul>");
            out.print("</li>");
            if (Integer.parseInt(sesion.getAttribute("l_id_area").toString()) == 12 || Integer.parseInt(sesion.getAttribute("l_id_area").toString()) == 17) {
                out.print("<li><a href='#'>Complementos</a>");
                out.print("<ul>");
                out.print("<li><a href='Capacitador?opc=1&txt_bus=" + filtro + "'>Capacitador</a></li>");
                out.print("<li><a href='Area?opc=1&txt_bus=" + filtro + "'>Área</a></li>");
                out.print("<li><a href='Cargo?opc=1&txt_bus=" + filtro + "'>Cargo</a></li>");
                out.print("<li><a href='Documento?opc=1&txt_bus=" + filtro + "'>Tipo Documento</a></li>");
                out.print("<li><a href='Empleado?opc=9&esta=1&txt_bus=" + filtro + "'>Traslado Personal</a></li>");
                out.print("<li><a href='TipoProceso?opc=1&idP=" + 0 + "&txt_bus='>Tipo Proceso</a></li>");
                out.print("</ul>");
            } else if (Integer.parseInt(sesion.getAttribute("l_id_area").toString()) == 5) {
                out.print("<li><a href='#'>Complementos</a>");
                out.print("<ul>");
                out.print("<li><a href='Cargo?opc=6&txt_bus=" + filtro + "'>Cargo</a></li>");
                out.print("<li><a href='Empleado?opc=9&esta=1&txt_bus=" + filtro + "'>Traslado Personal</a></li>");
                out.print("<li><a href='TipoProceso?opc=1&idP=" + 0 + "&txt_bus='>Tipo Proceso</a></li>");
                out.print("</ul>");
            } else if (Integer.parseInt(sesion.getAttribute("l_id_area").toString()) != 12) {
                if (Integer.parseInt(sesion.getAttribute("l_id_area").toString()) != 5) {
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Cargo?opc=6&txt_bus=" + filtro + "'>Cargo</a></li>");
                    out.print("<li><a href='TipoProceso?opc=1&idP=" + 0 + "&txt_bus='>Tipo Proceso</a></li>");
                    out.print("</ul>");
                }
            }
            out.print("<li class='restablecer'>");
            out.print("<a href='Login?opc=4'>Restablecer contraseña</a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("<br style='clear: left' />");
            out.print("</div>");
            try {
                daruma = Integer.parseInt(pageContext.getRequest().getAttribute("Daruma").toString());
            } catch (Exception e) {
                daruma = 0;
            }
            if (daruma != 0) {
                if (sigla.contains("LO")) {
                    lst_documentosC = jpa_manual.mnl_c_manual_v2("LO", "DC");
                    lst_documentosD = cnx_daruma.ConsultaDocumentosAreaLO();
                }else if (sigla.contains("GC")) {
                    lst_documentosC = jpa_manual.mnl_c_manual_v2("GC", "DT");
                    lst_documentosD = cnx_daruma.ConsultaDocumentosArea(2);
                } else {
                    lst_documentosC = jpa_manual.mnl_c_manual(sigla);
                    lst_documentosD = cnx_daruma.ConsultaDocumentosArea((id_area == 1) ? 8 : ((id_area == 2) ? 14 : ((id_area == 3) ? 2 : ((id_area == 4 || id_area == 25) ? 13 : ((id_area == 5) ? 7 : ((id_area == 6 || id_area == 13 || id_area == 14) ? 10 : ((id_area == 7) ? 17 : ((id_area == 8) ? 16 : ((id_area == 9) ? 15 : ((id_area == 10) ? 9 : ((id_area == 11) ? 5 : ((id_area == 17) ? 18 : 1))))))))))));
                }

                if (lst_documentosC != null && lst_documentosD != null) {
                    out.print("<div id='content_sin'>");
                    out.print("<h2>Se encontraron diferencias entre la version vigente de daruma y copec</h2>");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Version</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_documentosC.size(); i++) {
                        Object[] obj_documentosC = (Object[]) lst_documentosC.get(i);
                        if (Integer.parseInt(obj_documentosC[6].toString()) == 1) {
                            if (lst_documentosD.get(0).toString().contains(obj_documentosC[1].toString())) {
                                String[] documentos = lst_documentosD.get(0).toString().split(",");
                                for (int j = 0; j < documentos.length; j++) {
                                    if (documentos[j].contains("[" + obj_documentosC[1].toString() + "//")) {
                                        String[] doc_ver = documentos[j].replace("[", "").replace("]", "").split("//");
                                        if (!doc_ver[1].equals(obj_documentosC[2].toString())) {
                                            if (Integer.parseInt(doc_ver[1]) < Integer.parseInt(obj_documentosC[2].toString())) {
                                                out.print("<tr>");
                                                out.print("<td>" + obj_documentosC[3] + "</td>");
                                                out.print("<td align='center'>" + obj_documentosC[1] + "</td>");
                                                out.print("<td align='center'><b class='rojo'>Copec v" + obj_documentosC[2] + " - Daruma v" + doc_ver[1] + "</b></td>");
                                                out.print("</tr>");
                                            } else {
                                                out.print("<tr>");
                                                out.print("<td>" + obj_documentosC[3] + "</td>");
                                                out.print("<td align='center'>" + obj_documentosC[1] + "</td>");
                                                out.print("<td align='center'><b class='naranja'>Copec v" + obj_documentosC[2] + " - Daruma v" + doc_ver[1] + "</b></td>");
                                                out.print("</tr>");
                                            }
                                        }
                                    }
                                }
                                out.print("</tr>");
                            }
                        }
                    }
                } else {
                    out.print("No existe conexion");
                }
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
