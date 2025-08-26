package Tag;

import Controller.RoleControllerJpa;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.KnowledgeJpaController;

public class Tag_knowledgeTable extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        KnowledgeJpaController KnowledgeJpa = new KnowledgeJpaController();
        int idUser = 0, idRol = 0;
        String txtPermissions = "";
        List lst_role = null, lst_knowledge = null, lst_knowledgeId = null;
        int IdKnowledge = 0;
        try {
            idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }
        try {
            IdKnowledge = Integer.parseInt(pageContext.getRequest().getAttribute("IdKnowledge").toString());
        } catch (Exception e) {
            IdKnowledge = 0;
        }
        try {
            lst_knowledge = KnowledgeJpa.ConsultKnowledgeBase();
            if (IdKnowledge > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar manual </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_knowledgeId = KnowledgeJpa.ConsultKnowledgeBaseId(IdKnowledge);
                if (lst_knowledgeId != null) {
                    Object[] ObjKnowledge = (Object[]) lst_knowledgeId.get(0);
                    out.print("<form action='Attach_1.jsp' method='post' enctype='multipart/form-data'>");

                    out.print("<input type='hidden' name='IdKnowledge' value='" + IdKnowledge + "'>");

                    out.print("<div class='d-flex'>");

                    out.print("<div class='col-lg-6'>");
                    out.print("<input class='form-control' list='datalistOptions' name='Category' placeholder='Seleccione o escriba actividad' autocomplete='off' value='" + ObjKnowledge[1] + "'>");
                    out.print("<datalist id='datalistOptions'>");
                    if (lst_knowledge != null) {
                        for (int i = 0; i < lst_knowledge.size(); i++) {
                            Object[] ObjCategory = (Object[]) lst_knowledge.get(i);
                            out.print("<option value='" + ObjCategory[1] + "'></option>");
                        }
                    }
                    out.print("</datalist>");
                    out.print("</div>");

                    out.print("<div class='col-lg-6'>"
                            + "<input type='text' class='form-control' name='Title' id='Title' placeholder='Tilte' data-toggle='tooltip' data-placement='top' title='Titulo' value='" + ObjKnowledge[2] + "'>"
                            + "</div>");

                    out.print("</div>");

                    out.print("<input type='hidden' class='form-control' name='OldAttach' id='OldAttach' value='" + ObjKnowledge[3] + "'>");

                    out.print("<div class='d-flex'>");

                    out.print("<div class='d-flex col-lg-6' style='align-items: center;'>");
                    out.print("<input type='file' class='form-control' name='Attach' id='txtFile' data-toggle='tooltip' data-placement='top' title='Adjunto'>");
                    out.print("<div id='DownloadFile'></div>");
                    out.print("</div>");

                    out.print("<script>");
                    out.print("document.getElementById('txtFile').addEventListener('change', function(){ "
                            + "var input = this; "
                            + "var NameFile = input.files[0].name; "
                            + "var DownloadFile = document.getElementById('DownloadFile'); "
                            + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\"><i class=\"fas fa-download\"></i></a>'; "
                            + "});");
                    out.print("</script>");

                    out.print("<div class='col-lg-6'>");
                    out.print("<input type='text' class='form-control' name='Description' id='' placeholder='Descripción' data-toggle='tooltip' data-placement='top' title='Descripción' value='" + ObjKnowledge[4] + "'>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='text-center mt-2'>");
                    out.print("<button class='btn btn-green'>Confirmar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<h4>Se ha presentado un error al consultar la base por Id</h4>");
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
            //<editor-fold defaultstate="collapsed" desc="REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registar manual </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Attach_1.jsp' method='post' enctype='multipart/form-data'>");

            out.print("<input type='hidden' name='IdKnowledge' value='" + 0 + "'>");

            out.print("<div class='d-flex'>");

            out.print("<div class='col-lg-6'>");
            out.print("<input class='form-control' list='datalistOptions' name='Category' placeholder='Seleccione o escriba actividad' autocomplete='off'>");
            out.print("<datalist id='datalistOptions'>");
            if (lst_knowledge != null) {
                for (int i = 0; i < lst_knowledge.size(); i++) {
                    Object[] ObjCategory = (Object[]) lst_knowledge.get(i);
                    out.print("<option value='" + ObjCategory[1] + "'></option>");
                }
            }
            out.print("</datalist>");
            out.print("</div>");

            out.print("<div class='col-lg-6'>"
                    + "<input type='text' class='form-control' name='Title' id='Title' placeholder='Tilte' data-toggle='tooltip' data-placement='top' title='Titulo' >"
                    + "</div>");

            out.print("</div>");

            out.print("<input type='hidden' class='form-control' name='OldAttach' id='OldAttach' >");

            out.print("<div class='d-flex'>");

            out.print("<div class='d-flex col-lg-6' style='align-items: center;'>");
            out.print("<input type='file' class='form-control' name='Attach' id='txtFile' data-toggle='tooltip' data-placement='top' title='Adjunto'>");
            out.print("<div id='DownloadFile'></div>");
            out.print("</div>");

            out.print("<script>");
            out.print("document.getElementById('txtFile').addEventListener('change', function(){ "
                    + "var input = this; "
                    + "var NameFile = input.files[0].name; "
                    + "var DownloadFile = document.getElementById('DownloadFile'); "
                    + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\"><i class=\"fas fa-download\"></i></a>'; "
                    + "});");
            out.print("</script>");

            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='Description' id='' placeholder='Descripción' data-toggle='tooltip' data-placement='top' title='Descripción'>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='text-center mt-2'>");
            out.print("<button class='btn btn-green'>Confirmar</button>");
            out.print("</div>");
            out.print("</form>");
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
                    + "<h4>Base de conocimiento</h4>"
                    + "</div>");
            if (txtPermissions.contains("[68]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.7;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>Categoria</th>");
            out.print("<th>Titulo</th>");
            out.print("<th>Adjunto</th>");
            out.print("<th>Descripción</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (lst_knowledge != null) {
                for (int i = 0; i < lst_knowledge.size(); i++) {
                    Object[] ObjKnow = (Object[]) lst_knowledge.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjKnow[0] + "</td>");
                    out.print("<td>" + ObjKnow[1] + "</td>");
                    out.print("<td>" + ObjKnow[2] + "</td>");
                    out.print("<td><a class=\"btn btn-info\" onclick='window.location.href=\"DownloadKnowledge?File_name=" + ObjKnow[3] + "\"'><i class=\"fas fa-download\" style='color:white'></i></a></div></td>");
                    out.print("<td>" + ObjKnow[4] + "</td>");
                    int state = Integer.parseInt(ObjKnow[5].toString());
                    out.print("<td><div class='badge badge-" + ((state == 1) ? "success'>Activo" : "danger'>Inactivo") + "</div></td>");
                    out.print("<td class='text-center'>");
                    out.print("<div class='d-flex justify-content-around'>");
                    if (txtPermissions.contains("[69]")) {
                        out.print("<button class='btn btn-warning btn-sm'  onclick='window.location.href=\"Knowledge?opt=1&IdKnowledge=" + ObjKnow[0] + "\"'><i class='fas fa-user-edit' ></i></button>");
                    }
                    if (state == 1) {
                        if (txtPermissions.contains("[71]")) {
                            out.print("<a class='btn btn-success btn-sm' href='Knowledge?opt=3&IdKnowledge=" + ObjKnow[0] + "&State=0'><i class='fas fa-check'></i></a>");
                        } else {
                            out.print("<button class='btn btn-success btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-check'></i></button>");
                        }
                    } else {
                        if (txtPermissions.contains("[71]")) {
                            out.print("<a class='btn btn-danger btn-sm' href='Knowledge?opt=3&IdKnowledge=" + ObjKnow[0] + "&State=1'><i class='fas fa-times'></i></a>");
                        } else {
                            out.print("<button class='btn btn-danger' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-times'></i></button>");
                        }
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_knowledgeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
