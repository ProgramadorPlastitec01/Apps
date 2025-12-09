package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Profile extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print("<div class='main-content'>");
            out.print("  <section class='section'>");

            out.print("    <div class='section-header'>");
            out.print("      <h1>Profile</h1>");
            out.print("      <div class='section-header-breadcrumb'>");
            out.print("        <div class='breadcrumb-item active'><a href='#'>Dashboard</a></div>");
            out.print("        <div class='breadcrumb-item'>Profile</div>");
            out.print("      </div>");
            out.print("    </div>");

            out.print("    <div class='section-body'>");
            out.print("      <h2 class='section-title'>Hi, Ujang!</h2>");
            out.print("      <p class='section-lead'>Change information about yourself on this page.</p>");

            out.print("      <div class='row mt-sm-4'>");

// ================= COLUMNA IZQUIERDA ====================
            out.print("        <div class='col-12 col-md-12 col-lg-5'>");
            out.print("          <div class='card profile-widget'>");

            out.print("            <div class='profile-widget-header'>");
            out.print("              <img alt='image' src='assets/img/avatar/avatar-1.png' class='rounded-circle profile-widget-picture'>");

            out.print("              <div class='profile-widget-items'>");

            out.print("                <div class='profile-widget-item'>");
            out.print("                  <div class='profile-widget-item-label'>Posts</div>");
            out.print("                  <div class='profile-widget-item-value'>187</div>");
            out.print("                </div>");

            out.print("                <div class='profile-widget-item'>");
            out.print("                  <div class='profile-widget-item-label'>Followers</div>");
            out.print("                  <div class='profile-widget-item-value'>6,8K</div>");
            out.print("                </div>");

            out.print("                <div class='profile-widget-item'>");
            out.print("                  <div class='profile-widget-item-label'>Following</div>");
            out.print("                  <div class='profile-widget-item-value'>2,1K</div>");
            out.print("                </div>");

            out.print("              </div>"); // profile-widget-items
            out.print("            </div>"); // profile-widget-header

            out.print("            <div class='profile-widget-description'>");
            out.print("              <div class='profile-widget-name'>Ujang Maman <div class='text-muted d-inline font-weight-normal'><div class='slash'></div> Web Developer</div></div>");
            out.print("              Ujang maman is a superhero name in <b>Indonesia</b>, especially in my family. He is not a fictional character but an original hero in my family, a hero for his children and for his wife. So, I use the name as a user in this template. Not a tribute, I'm just bored with <b>'John Doe'</b>.");
            out.print("            </div>");

            out.print("            <div class='card-footer text-center'>");
            out.print("              <div class='font-weight-bold mb-2'>Follow Ujang On</div>");

            out.print("              <a href='#' class='btn btn-social-icon btn-facebook mr-1'><i class='fab fa-facebook-f'></i></a>");
            out.print("              <a href='#' class='btn btn-social-icon btn-twitter mr-1'><i class='fab fa-twitter'></i></a>");
            out.print("              <a href='#' class='btn btn-social-icon btn-github mr-1'><i class='fab fa-github'></i></a>");
            out.print("              <a href='#' class='btn btn-social-icon btn-instagram'><i class='fab fa-instagram'></i></a>");

            out.print("            </div>"); // card-footer
            out.print("          </div>"); // card profile-widget
            out.print("        </div>"); // col izquierda

// ================= COLUMNA DERECHA ====================
            out.print("        <div class='col-12 col-md-12 col-lg-7'>");
            out.print("          <div class='card'>");

            out.print("            <form method='post' class='needs-validation' novalidate>");

            out.print("              <div class='card-header'><h4>Edit Profile</h4></div>");

            out.print("              <div class='card-body'>");

            out.print("                <div class='row'>");
            out.print("                  <div class='form-group col-md-6 col-12'>");
            out.print("                    <label>First Name</label>");
            out.print("                    <input type='text' class='form-control' value='Ujang' required>");
            out.print("                    <div class='invalid-feedback'>Please fill in the first name</div>");
            out.print("                  </div>");

            out.print("                  <div class='form-group col-md-6 col-12'>");
            out.print("                    <label>Last Name</label>");
            out.print("                    <input type='text' class='form-control' value='Maman' required>");
            out.print("                    <div class='invalid-feedback'>Please fill in the last name</div>");
            out.print("                  </div>");
            out.print("                </div>");

            out.print("                <div class='row'>");
            out.print("                  <div class='form-group col-md-7 col-12'>");
            out.print("                    <label>Email</label>");
            out.print("                    <input type='email' class='form-control' value='ujang@maman.com' required>");
            out.print("                    <div class='invalid-feedback'>Please fill in the email</div>");
            out.print("                  </div>");

            out.print("                  <div class='form-group col-md-5 col-12'>");
            out.print("                    <label>Phone</label>");
            out.print("                    <input type='tel' class='form-control' value=''>");
            out.print("                  </div>");
            out.print("                </div>");

            out.print("                <div class='row'>");
            out.print("                  <div class='form-group col-12'>");
            out.print("                    <label>Bio</label>");
            out.print("                    <textarea class='form-control summernote-simple'>Ujang maman is a superhero name in <b>Indonesia</b>...</textarea>");
            out.print("                  </div>");
            out.print("                </div>");

            out.print("                <div class='row'>");
            out.print("                  <div class='form-group mb-0 col-12'>");
            out.print("                    <div class='custom-control custom-checkbox'>");
            out.print("                      <input type='checkbox' name='remember' class='custom-control-input' id='newsletter'>");
            out.print("                      <label class='custom-control-label' for='newsletter'>Subscribe to newsletter</label>");
            out.print("                      <div class='text-muted form-text'>You will get new information about products, offers and promotions</div>");
            out.print("                    </div>");
            out.print("                  </div>");
            out.print("                </div>");

            out.print("              </div>"); // card-body

            out.print("              <div class='card-footer text-right'>");
            out.print("                <button class='btn btn-primary'>Save Changes</button>");
            out.print("              </div>");

            out.print("            </form>");

            out.print("          </div>");
            out.print("        </div>"); // col derecha

            out.print("      </div>"); // row mt-sm-4
            out.print("    </div>"); // section-body

            out.print("  </section>");
            out.print("</div>");

        } catch (Exception ex) {
            Logger.getLogger(Profile.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
