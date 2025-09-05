package Tag;

import Controller.RoleControllerJpa;
import Controller.IdeaJpaController;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_idea extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            RoleControllerJpa RoleJpa = new RoleControllerJpa();
            IdeaJpaController IdeaJpa = new IdeaJpaController();
            int idUser = 0, idRol = 0, state = 0, idIdea = 0;
            String txtPermissions = "";
            List lst_role = null, lst_idea = null, lst_ideaId = null;
            try {
                idIdea = Integer.parseInt(pageContext.getRequest().getAttribute("idIdea").toString());
            } catch (Exception e) {
                idIdea = 0;
            }
            try {
                idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                lst_role = RoleJpa.ConsultRoleId(idRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                txtPermissions = obj_permi[2].toString();
            } catch (Exception e) {
                idRol = 0;
            }
            if (idIdea > 0) {
                //<editor-fold defaultstate="collapsed" desc="SOLUTION IDEA">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Concluir Idea</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_ideaId = IdeaJpa.ConsultIdeaId(idIdea);
                if (lst_ideaId != null) {
                    Object[] ObjIdeaId = (Object[]) lst_ideaId.get(0);
                    out.print("<form action='Idea?opt=2' method='post' id='FormIdea'>");
                    out.print("<input type='hidden' name='idIdea' value='" + ObjIdeaId[0] + "'>");
                    out.print("<input type='hidden' id='validation' name='state' value=''>");
                    out.print("<div class='col-12 mt-4' style='max-height: 540px;overflow: auto;'>"
                            + "<textarea class='form-control' id='editorCK' name='Txt_description' required></textarea>"
                            + "<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe indicar una descripción.</div>"
                            + "</div>");

                    out.print("</form>");

                    out.print("<div class='mt-2 d-flex justify-content-around'>");
                    out.print("<button class='btn btn-info' onclick='ValidationSave(1)'>En gestión</button>");
                    out.print("<button class='btn btn-green'onclick='ValidationSave(0)'>Finalizar</button>");
                    out.print("</div>");

                } else {
                    out.print("<h4>Se ha presentado un error al consultar el usuario</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>"
                        + " document.addEventListener('DOMContentLoaded', function() {"
                        + "    function toggleClass() {"
                        + "        const body = document.body;"
                        + "        body.classList.add('modal-open');"
                        + "    }"
                        + "    toggleClass();"
                        + " });"
                        + "</script>");
                //</editor-fold>
            }
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'>"
                    + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" >"
                    + "<i class=\"far fa-hand-point-left\"></i>"
                    + "</button>"
                    + "</div>"
                    + "<h4>Ideas</h4>"
                    + "</div>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");

            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>Tipo</th>");
            out.print("<th>Descripción</th>");
            out.print("<th>Responsable</th>");
            out.print("<th>Nota</th>");
            out.print("<th>Fecha registro</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Concluir</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");

            lst_idea = IdeaJpa.ConsultIdea();
            if (lst_idea != null) {
                for (int i = 0; i < lst_idea.size(); i++) {
                    Object[] ObjIdea = (Object[]) lst_idea.get(i);
                    out.print("<tr>");
                    out.print("<td class=''>" + ObjIdea[0] + "</td>");
                    out.print("<td>" + ObjIdea[2] + "</td>");
                    out.print("<td>" + ObjIdea[3] + "</td>");
                    out.print("<td>" + ObjIdea[1] + "</td>");
                    out.print("<td>" + ((ObjIdea[5] == null) ? "" : ObjIdea[5]) + "</td>");
                    out.print("<td>" + ((ObjIdea[6] == null) ? "" : ObjIdea[6]) + "</td>");
                    state = Integer.parseInt(ObjIdea[4].toString());
                    out.print("<td><div class='badge badge-" + ((state == 1) ? "success'>Vigente" : "dark'>Cerrado") + "</div></td>");
                    out.print("<td class='text-center'>");
                    if (txtPermissions.contains("[4]")) {
                        if (state == 0) {
                            out.print("<button class='btn btn-green' ><i class=\"fas fa-check\"></i></button>");
                        } else {
                            out.print("<a class='btn btn-green' href='Idea?opt=1&idIdea=" + ObjIdea[0] + "'><i class=\"fas fa-thumbs-up\"></i></a>");
                        }
                    } else {
                        out.print("<button class='btn btn-success btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-check'></i></button>");
                    }
                    out.print("</td>");
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
        } catch (Exception e) {
        }
        return super.doStartTag();
    }

}
