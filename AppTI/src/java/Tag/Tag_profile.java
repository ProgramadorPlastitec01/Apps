package Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.UserControllerJpa;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_profile extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        UserControllerJpa UserJpa = new UserControllerJpa();
        HttpSession sesion = pageContext.getSession();
        int IdUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        JspWriter out = pageContext.getOut();
        List lst_user = null, lst_profile = null, lst_calification = null, lst_stadisticProfile = null;
        try {
            lst_user = UserJpa.ConsultUsersid(IdUser);
            lst_profile = UserJpa.ConsultDataProfile(IdUser);
            if (lst_user != null || lst_profile != null) {
                Object[] ObjUsr = (Object[]) lst_user.get(0);
                Object[] ObjProfile = (Object[]) lst_profile.get(0);
                out.print("<input type=\"hidden\" id=\"initialImage\" value='" + ((ObjUsr[12] == null) ? "E1.png" : ObjUsr[12]) + "'>");
                out.print("<section class='section'>");
                out.print("<div class=\"section-header\" style='margin-bottom:0px'>\n"
                        + "            <h1>Perfil</h1>\n"
                        + "          </div>");
                out.print("<div class='card-body'>");
                out.print("<h2 class='text-center'>Icono</h2>");
                out.print("<div class=\"card\">");
                out.print("<div class=\"carousel-wrapper\">\n"
                        + "  <div class=\"carousel-track\" id=\"carouselTrack\"></div>\n"
                        + "  <button class=\"nav-button left\" onclick=\"prevSlide()\">&#10094;</button>\n"
                        + "  <button class=\"nav-button right\" onclick=\"nextSlide()\">&#10095;</button>\n"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"section-body\">");
                out.print("<h2 class=\"section-title\">Hola, " + ObjUsr[1] + "!</h2>");

                out.print("<div class=\"row mt-sm-4\">");
                //<editor-fold defaultstate="collapsed" desc="DIV LEFT">
                out.print("<div class=\"col-12 col-md-12 col-lg-6\">");
                //<editor-fold defaultstate="collapsed" desc="DIV PROFILE">
                out.print("<div class=\"card profile-widget\">");
                out.print("<div class=\"profile-widget-header\">");
                out.print("<img alt=\"image\" src=\"Interface/Photos/" + ObjUsr[3] + ".jpg\" class=\"rounded-circle profile-widget-picture\">");

                out.print("<div class=\"profile-widget-items\">");
                out.print("<div class=\"profile-widget-item\">");
                out.print("<div class=\"profile-widget-item-label\">Actividades</div>");
                out.print("<div class=\"profile-widget-item-value\">" + ObjProfile[1] + "</div>");
                out.print("</div>");
                out.print("<div class=\"profile-widget-item\">");
                out.print("<div class=\"profile-widget-item-label\">Bitacoras</div>");
                out.print("<div class=\"profile-widget-item-value\">" + ObjProfile[2] + "</div>");
                out.print("</div>");
                out.print("<div class=\"profile-widget-item\">");
                out.print("<div class=\"profile-widget-item-label\">Casos</div>");
                out.print("<div class=\"profile-widget-item-value\">" + ObjProfile[0] + "</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("<form action='Profile?opt=2' method=\"post\" class=\"needs-validation\" novalidate=\"\">");
                out.print("<div class=\"card-header\">");
                out.print("<h4>Actualiza tu Perfil</h4>");
                out.print("</div>");
                out.print("<div class=\"card-body\">");
                out.print("<input type='hidden' name='idUser' value='" + ObjUsr[0] + "'>");
                out.print("<div class=\"row\">");
                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Documento</label>");
                out.print("<input type=\"number\" class=\"form-control\"  readonly='true' value='" + ObjUsr[3] + "' required=\"\">");
                out.print("</div>");

                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Codigo</label>");
                out.print("<input type=\"text\" class=\"form-control\" name='code' value='" + ObjUsr[4] + "' required=\"\">");
                out.print("<div class=\"invalid-feedback\">");
                out.print("¡Debe ingresa el código!");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"row\">");
                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Nombre</label>");
                out.print("<input type=\"text\" class=\"form-control\" name='name' value='" + ObjUsr[1] + "' required=\"\">");
                out.print("<div class=\"invalid-feedback\">");
                out.print("¡Debe ingresa su nombre!");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Apeliido</label>");
                out.print("<input type=\"text\" class=\"form-control\" name='lastname' value='" + ObjUsr[2] + "' >");
                out.print("<div class=\"invalid-feedback\">");
                out.print("¡Debe ingresa sus apellidos!");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"row\">");
                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Usuario</label>");
                out.print("<input type=\"text\" class=\"form-control\"  name='user' value='" + ObjUsr[5] + "' required=\"\">");
                out.print("<div class=\"invalid-feedback\">");
                out.print("¡Debe ingresa el usuario!");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"form-group col-md-6 col-12\">");
                out.print("<label>Contraseña</label>");
                out.print("<div class='d-flex align-items-baseline'>"
                        + "<div class='w-90'><input type=\"password\" class=\"form-control\" id='pass-input' autocomplete=\"new-password\"  name='pass' value=''></div>");
                out.print("<div><button type='button' class='btn btn-info btn-sm' onclick='mostrarAlertaPass()' style='margin-left:10px;'><i class=\"fas fa-question\"></i></button></div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");

                out.print("<input type='hidden' name='pass2' value='" + ObjUsr[6] + "'>");

                out.print("<input type=\"hidden\" id=\"selectedImage\" name='icon' value=''>");

                out.print("</div>");
                out.print("<div class=\"card-footer text-right\">");
                out.print("<button class=\"btn btn-green\">Guarda cambios</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");

                //</editor-fold>
                out.print("</div>");

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DIV RIGHT">
                out.print("<div class=\"col-12 col-md-12 col-lg-5\">");
                //<editor-fold defaultstate="collapsed" desc="ULT ACT MONTH">
                out.print("<div class=\"card profile-widget\">");
                out.print("<h5 class='text-center mt-2'>Actividad ultimos tres meses</h5>");
                lst_stadisticProfile = UserJpa.ConsultStadisticProfile(IdUser);
                if (lst_stadisticProfile != null) {
                    String MthP = "";
                    String SppP = "";
                    String BncP = "";
                    String AtyP = "";
                    int totalSupport = 0;
                    int totalBinnacle = 0;
                    int totalActivity = 0;

                    for (int i = 0; i < lst_stadisticProfile.size(); i++) {
                        Object[] ObjStd = (Object[]) lst_stadisticProfile.get(i);

                        String mes = "'" + ObjStd[0].toString() + "'";
                        String soporte = ObjStd[1].toString();
                        String bitacora = ObjStd[2].toString();
                        String actividad = ObjStd[3].toString();

                        totalSupport += Integer.parseInt(ObjStd[1].toString());
                        totalBinnacle += Integer.parseInt(ObjStd[2].toString());
                        totalActivity += Integer.parseInt(ObjStd[3].toString());

                        if (i == lst_stadisticProfile.size() - 1) {
                            MthP += mes;
                            SppP += soporte;
                            BncP += bitacora;
                            AtyP += actividad;
                        } else {
                            MthP += mes + ", ";
                            SppP += soporte + ", ";
                            BncP += bitacora + ", ";
                            AtyP += actividad + ", ";
                        }
                    }
                    out.print("<div class=\"profile-widget-items\">");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\">Actividades</div>");
                    out.print("<div class=\"profile-widget-item-value\">" + totalActivity + "</div>");
                    out.print("</div>");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\">Bitacoras</div>");
                    out.print("<div class=\"profile-widget-item-value\">" + totalBinnacle + "</div>");
                    out.print("</div>");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\">Ticket</div>");
                    out.print("<div class=\"profile-widget-item-value\">" + totalSupport + "</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<canvas id=\"myChart2\" width=\"400\" height=\"230\"></canvas>");
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d');");
                    out.print("var myChart = new Chart(ctx, {");
                    out.print("type: 'line',");
                    out.print("data: {");
                    out.print("labels: [" + MthP + "],");
                    out.print("datasets: [");

                    // Actividades
                    out.print("{");
                    out.print("label: 'Actividades',");
                    out.print("data: [" + AtyP + "],");
                    out.print("borderColor: 'rgba(75, 192, 192, 1)',");
                    out.print("borderWidth: 2, fill: false, tension: 0.4");
                    out.print("},");

                    // Bitácoras
                    out.print("{");
                    out.print("label: 'Bitácoras',");
                    out.print("data: [" + BncP + "],");
                    out.print("borderColor: 'rgba(255, 99, 132, 1)',");
                    out.print("borderWidth: 2, fill: false, tension: 0.4");
                    out.print("},");

                    // Soportes
                    out.print("{");
                    out.print("label: 'Tickets',");
                    out.print("data: [" + SppP + "],");
                    out.print("borderColor: '#f5f526',");
                    out.print("borderWidth: 2, fill: false, tension: 0.4");
                    out.print("}");

                    out.print("]");
                    out.print("},"); // cierre de data

                    // Opciones del gráfico
                    out.print("options: {");
                    out.print("responsive: true,");
                    out.print("plugins: { legend: { display: true, position: 'top' } },");
                    out.print("scales: { y: { beginAtZero: true } }");
                    out.print("}");

                    out.print("});");
                    out.print("</script>");
                }
                out.print("</div>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="DIV CALIFICATION">
                lst_calification = UserJpa.ConsultSupportCalification(IdUser);
                if (lst_calification != null) {
                    Object[] ObjCalification = (Object[]) lst_calification.get(0);
                    out.print("<div class=\"card profile-widget mt-2\">");
                    out.print("<div class=\"profile-widget-header\">");
                    out.print("<h5 class='text-center mt-2'>Tickets</h5>");
                    out.print("<div class=\"profile-widget-items\">");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\">"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "<i class=\"fas fa-star StarColor\"></i><br/>"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "</div>");
                    out.print("<div class=\"profile-widget-item-value\">" + ObjCalification[0] + "</div>");
                    out.print("</div>");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\">"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "<i class=\"fas fa-star StarColor\"></i><br/>"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "<i class=\"fas fa-star StarColor\"></i>"
                            + "</div>");
                    out.print("<div class=\"profile-widget-item-value\">" + ObjCalification[1] + "</div>");
                    out.print("</div>");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\"><i class=\"fas fa-star StarColor\"></i><i class=\"fas fa-star StarColor\"></i><i class=\"fas fa-star StarColor\"></i></div>");
                    out.print("<div class=\"profile-widget-item-value\">" + ObjCalification[2] + "</div>");
                    out.print("</div>");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\"><i class=\"fas fa-star StarColor\"></i><i class=\"fas fa-star StarColor\"></i></div>");
                    out.print("<div class=\"profile-widget-item-value\">" + ObjCalification[3] + "</div>");
                    out.print("</div>");
                    out.print("<div class=\"profile-widget-item\">");
                    out.print("<div class=\"profile-widget-item-label\"><i class=\"fas fa-star StarColor\"></i></div>");
                    out.print("<div class=\"profile-widget-item-value\">" + ObjCalification[4] + "</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");

                    String ArgCnt = ObjCalification[0] + ","
                            + ObjCalification[1] + ","
                            + ObjCalification[3] + ","
                            + ObjCalification[4];

                    out.print("<div class=\"profile-widget-description\">");
                    out.print("<canvas id=\"myChart3\" width=\"400\" height=\"230\"></canvas>");
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart3\").getContext('2d');");
                    out.print("var myChart = new Chart(ctx, {");
                    out.print("  type: 'pie',");
                    out.print("  data: {");
                    out.print("    labels: ['5', '4', '3', '2', '1'],");
                    out.print("    datasets: [{");
                    out.print("      label: 'Proceso',");
                    out.print("      data: [" + ArgCnt + "],");
                    out.print("      backgroundColor: [");
                    out.print("        'rgba(255, 99, 132, 0.6)',");
                    out.print("        'rgba(54, 162, 235, 0.6)',");
                    out.print("        'rgba(255, 206, 86, 0.6)',");
                    out.print("        'rgba(75, 192, 192, 0.6)',");
                    out.print("        'rgba(153, 102, 255, 0.6)'");
                    out.print("      ],");
                    out.print("      borderColor: [");
                    out.print("        'rgba(255, 99, 132, 1)',");
                    out.print("        'rgba(54, 162, 235, 1)',");
                    out.print("        'rgba(255, 206, 86, 1)',");
                    out.print("        'rgba(75, 192, 192, 1)',");
                    out.print("        'rgba(153, 102, 255, 1)'");
                    out.print("      ],");
                    out.print("      borderWidth: 1.7");
                    out.print("    }]");
                    out.print("  },");
                    out.print("  options: {");
                    out.print("    responsive: true,");
                    out.print("    plugins: {");
                    out.print("      legend: {");
                    out.print("        position: 'bottom'");
                    out.print("      }");
                    out.print("    }");
                    out.print("  }");
                    out.print("});");
                    out.print("</script>");

                    out.print("</div>");

                    out.print("</div>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("</div>");
            }

            out.print("</div>");
            out.print("</section>");

        } catch (Exception e) {
             e.printStackTrace();
        }
        return super.doStartTag();
    }

}
