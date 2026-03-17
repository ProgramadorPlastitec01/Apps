package Tags;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.InicioJpaController;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String nombre = pageContext.getSession().getAttribute("Nombre").toString();
        InicioJpaController InicioJpa = new InicioJpaController();
        List lst_anio = null, lst_plano = null, lst_prioridad = null, lst_rol = null;
        try {
            out.print("<section class='section'>");
            out.print("<div style='justify-content:center;' class='section-header'>");
            out.print("<h1>Bienvenido a <span style='color:#b72e27'>Solicitudes</span> Proyectos</h1> ");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");

            out.print("<div class='card-body'>");
            //<editor-fold defaultstate="collapsed" desc="SECCION DE CONTADORES - MEDIDOR">
            out.print("<div ><h4>Seguimiento de solicitudes en proceso</h4></div>");
            out.print("<div class='DivControl mt-4'>");
            out.print("<div class='contContador'>");
            out.print("<div class='LevDivCont'>Año</div>");
            out.print("<table class='table table-sm table-hover'>");
            out.print("<thead><th class='thCont thContT1'># Sol.</th><th class='thCont thContT2'>Año</th></thead>");
            out.print("<tbody>");
            lst_anio = InicioJpa.ContadorAnio();
            if (lst_anio != null) {
                for (int i = 0; i < lst_anio.size(); i++) {
                    Object[] obj_anio = (Object[]) lst_anio.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_anio[0] + "</td>");
                    out.print("<td>" + obj_anio[1] + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='2' style='text-align:center;'>No existe información</td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");

            out.print("<div class='contContador'>");
            out.print("<div class='LevDivCont'>Plano</div>");
            out.print("<table class='table table-sm table-hover'>");
            out.print("<thead><th class='thCont thContT1'># Sol.</th><th class='thCont thContT2'>Plano</th></thead>");
            out.print("<tbody>");
            lst_plano = InicioJpa.ContadorPlano();
            if (lst_anio != null) {
                for (int i = 0; i < lst_plano.size(); i++) {
                    Object[] obj_plano = (Object[]) lst_plano.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_plano[0] + "</td>");
                    out.print("<td>" + obj_plano[1] + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='2' style='text-align:center;'>No existe información</td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");

            out.print("<div class='contContador'>");
            out.print("<div class='LevDivCont'>Prioridad</div>");
            out.print("<table class='table table-sm table-hover'>");
            out.print("<thead><th class='thCont thContT1'># Sol.</th><th class='thCont thContT2'>Prioridad</th></thead>");
            out.print("<tbody>");
            lst_prioridad = InicioJpa.ContadorPrioridad();
            if (lst_prioridad != null) {
                for (int i = 0; i < lst_prioridad.size(); i++) {
                    Object[] obj_prioridad = (Object[]) lst_prioridad.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_prioridad[0] + "</td>");
                    out.print("<td>" + obj_prioridad[1] + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='2' style='text-align:center;'>No existe información</td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");

            out.print("<div class='contContador'>");
            out.print("<div class='LevDivCont'>Rol</div>");
            out.print("<table class='table table-sm table-hover'>");
            out.print("<thead><th class='thCont thContT1'># Sol.</th><th class='thCont thContT2'>Rol</th></thead>");
            out.print("<tbody>");
            lst_rol = InicioJpa.ContadorRol();
            if (lst_rol != null) {
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_rol = (Object[]) lst_rol.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_rol[0] + "</td>");
                    out.print("<td>" + obj_rol[1] + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='2' style='text-align:center;'>No existe información</td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");

            out.print("</div>");
            //</editor-fold>
            out.print("</div>");

            out.print("<div class='DivIconos'>");
            out.print("<table class='table' style='width:100%;'>");
            out.print("<th class='thCont thContT1'>Icono</th>");
            out.print("<th class='thCont'>Función</th>");
            out.print("<th class='thCont'>Icono</th>");
            out.print("<th class='thCont'>Función</th>");
            out.print("<th class='thCont'>Icono</th>");
            out.print("<th class='thCont thContT3'>Función</th>");
            out.print("<tr><td align='center'><span class='fas fa-plus fontIcono'></span></td>"
                    + "<td>Habilitá formulario para registrar.</td>"
                    + "<td align='center'><span class='fas fa-edit fontIcono'></span></td>"
                    + "<td>Habilitá formulario para modificar.</td>"
                    + "<td align='center'><span class='fas fa-times fontIcono'></span></td>"
                    + "<td>Permite cerrar ventana emergente o devolver el seguimiento.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-check fontIcono'></span></td>"
                    + "<td>Indicar que un registro esta verificado o activo.</td>"
                    + "<td align='center'><span class='fas fa-people-carry fontIcono'></span></td>"
                    + "<td>Indica acceder o consultar el módulo de solicitudes.</td>"
                    + "<td align='center'><span class='fas fa-vote-yea fontIcono'></span></td>"
                    + "<td>Indica acceder o consultar el módulo de solicitudes desde herramental (Molde).</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-file-alt fontIcono'></span></td>"
                    + "<td>Indica acceder o consultar el módulo de solicited desde herramental (Ficha tecnica).</td>"
                    + "<td align='center'><span class='fas fa-lock-open fontIcono'></span></td>"
                    + "<td>Indica que el registro se encuentra abierto.</td>"
                    + "<td align='center'><span class='fas fa-lock fontIcono'></span></td>"
                    + "<td>Indica que el registro se encuentra cerrado.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-eye fontIcono'></span></td>"
                    + "<td>Permite acceder al consultar el detalle del registro.</td>"
                    + "<td align='center'><span class='fas fa-folder-plus fontIcono'></span></td>"
                    + "<td>Indica registrar solicitud por herramental (Molde - Ficha Tecnica).</td>"
                    + "<td align='center'><span class='fas fa-wrench fontIcono'></span></td>"
                    + "<td>Permite acceder al módulo de soporte para generar caso.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-sign-out-alt fontIcono'></span></td>"
                    + "<td>Indica opción de salir del sistema.</td>"
                    + "<td align='center'><span class='fas fa-home fontIcono'></span></td>"
                    + "<td>Permite acceder al módulo de inicio.</td>"
                    + "<td align='center'><span class='fas fa-search fontIcono'></span></td>"
                    + "<td>Habilita formulario para filtro de busqueda.</td></tr>");
            out.print("<tr><td align='center'><span class='fas fa-print fontIcono'></span></td>"
                    + "<td>Permite imprimir o guadar en PDF archivo generado.</td>"
                    + "<td align='center'><span class='fas fa-file-excel  fontIcono'></td>"
                    + "<td>Descargar archivo excel con información del archivo generado.</td>"
                    + "<td align='center'><span class='fas fa-arrow-left fontIcono'></span></td>"
                    + "<td>Permite devolver al anterior módulo.</tdp></tr>");
            out.print("</table>");
            out.print("</div>");
            
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException e) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
