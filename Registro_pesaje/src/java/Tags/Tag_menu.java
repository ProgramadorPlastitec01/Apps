package Tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.UsuarioJpaController;
import java.util.List;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        UsuarioJpaController UsuarioJpa = new UsuarioJpaController();
        List lst_usuario = null;

        try {
            HttpSession sesion = pageContext.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String NombreRol = sesion.getAttribute("NombreRol").toString();
            String nombre = (String) sesion.getAttribute("Nombres");
            int id_userSesion = (Integer) sesion.getAttribute("idUsuario");
            //<editor-fold defaultstate="collapsed" desc="PRE-CARGADOR DE PAGINAS SPINNER">
            out.print("<div class='preloader'>");
            out.print("<div class='lds-ripple'>");
            out.print("<div class='lds-pos'></div>");
            out.print("<div class='lds-pos'></div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="HEADER PAGE">
            out.print("<div id='main-wrapper' data-layout='vertical' data-navbarbg='skin5' data-sidebartype='full' data-sidebar-position='absolute' data-header-position='absolute' data-boxed-layout='full' style='box-shadow: 0px 0px 6px #cacaca;'>");
            out.print("<header class='topbar' data-navbarbg='skin5'>");

            out.print("<nav class='navbar top-navbar navbar-expand-md navbar-dark'>");
            out.print("<div class='navbar-header' data-logobg='skin6' style=''>");

            out.print("<a class='navbar-brand' href='inicio.jsp'>");
            out.print("<b style='margin-right: -126px;'><img src='Interfaz/Contenido/Imagenes/Logo_SP.png' alt='homepage' style='width: 30%; margin-top:-11%; margin-left:5%; '/></b>");
//            out.print("<b style='margin-right: -126px;'><img src='Interfaz/Contenido/Imagenes/filter.png' alt='homepage' style='width: 30%;'/></b>");
            out.print("<span class='logo-text'>");
            out.print("<img src='Interfaz/Contenido/Imagenes/Title_reg_pesaje_v2.png' alt='homepage' style='margin-left: 7%; width: 80%;' />");
            out.print("</span>");
            out.print("</a>");

            out.print("<a class='nav-toggler waves-effect waves-light text-dark d-block d-md-none' href='javascript:void(0)'>");
            out.print("<i class='ti-menu ti-close'></i>");
            out.print("</a>");
            out.print("</div>");
            lst_usuario = UsuarioJpa.ConsultarLogoUsuario(id_userSesion);
            Object[] obj_user = (Object[]) lst_usuario.get(0);
            out.print("<div class='navbar-collapse collapse' id='navbarSupportedContent' data-navbarbg='skin5'>");
            out.print("<ul class='navbar-nav ms-auto d-flex align-items-center'>");
            out.print("<li>");
            out.print("<a class='profile-pic' href='#' onclick='mostrar_opc()' style='padding: 18px;'>");
            out.print("<img src='Interfaz/Contenido/Imagenes/" + obj_user[1] + "' alt='user-img' width='36' class='img-circle' />");
            out.print("<span>" + nombre + "</span>");
            out.print("</a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</nav>");
            out.print("</header>");

            out.print("<div class='cont_user' id='cont_user' style='display: none;'>");
            out.print("<button class='button-30'><a href='Perfil?opc=1'><i class=\"fas fa-user\"></i> Perfil</a></button><br>");
            out.print("<button class='button-30'><a href='Salir.jsp'><i class=\"fas fa-sign-out-alt\"></i> Salir</a></button>");
            out.print("</div>");

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="SIDEBAR MENU">
            out.print("<style>");
            out.print("li{margin-bottom: 10px;}");
            out.print("</style>");

            out.print("<div style='display:flex;'>");
            out.print("<aside class='left-sidebar2' data-sidebarbg='skin6' style='box-shadow: 0px 0px 5px #cacaca;' id='cont_aside'>");
            out.print("<div class='scroll-sidebar'>");
            out.print("<nav class='sidebar-nav'>");

            out.print("<ul id='sidebarnav'>");
            out.print("<li class='text-center p-20 upgrade-btn' style='margin-top: 20px;' title='Ir a inicio'>");
            out.print("<a class='btn d-grid btn-danger text-white' href='inicio.jsp' aria-expanded='false' style='width: 80%;margin: auto; display: flex;'> <i class=\"fas fa-home\"></i> </a>");
            out.print("</li>");
            if (NombreRol.equals("Administrador")) {
                out.print("<li class='sidebar-item' id='opc_user' onclick='mostrar_opc2()' title='Configuración'>");
                out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='#' aria-expanded='false' >");
                out.print("<i class='fas fa-cogs' aria-hidden='true'></i>");
                out.print("<span class='hide-menu'> Configuración </span>");
                out.print("</a>");

                out.print("<ul class='opc_section' id='opc_section' style='background: #e3e3e3; display: none;'>");
                out.print("<li class='sidebar-item' title='Usuario'>");
                out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Usuario?opc=1' aria-expanded='false'>");
                out.print("<i class='fas fa-user' aria-hidden='true'></i>");
                out.print("<span class='hide-menu'> Usuario </span>");
                out.print("</a>");
                out.print("</li>");
                out.print("<li class='sidebar-item' title='Rol'>");
                out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Rol?opc=1' aria-expanded='false'>");
                out.print("<i class='fas fa-user-tag' aria-hidden='true'></i>");
                out.print("<span class='hide-menu'> Rol </span>");
                out.print("</a>");
                out.print("</li>");
                out.print("</ul>");
            }
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Consulta")) {
                out.print("<li class='sidebar-item' id='opc_complemento' onclick='mostrar_opc3()' title='Complementos'>");
                out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='#' aria-expanded='false' >");
                out.print("<i class='fas fa-bars' aria-hidden='true'></i>");
                out.print("<span class='hide-menu'> Complementos </span>");
                out.print("</a>");
                out.print("<ul class='opc_section' id='opc_complement' style='background: #e3e3e3; display: none;'>");
                if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Consulta")) {
                    out.print("<li class='sidebar-item pt-2' title='Defectos'>");
                    out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Defecto?opc=1' aria-expanded='false'>");
                    out.print("<i class='fas fa-flag'></i>");
                    out.print("<span class='hide-menu'> Defectos </span>");
                    out.print("</a>");
                    out.print("</li>");
                }
                if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Consulta")) {
                    out.print("<li class='sidebar-item pt-2' title='Maquina'>");
                    out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Maquina?opc=1' aria-expanded='false'>");
                    out.print("<i class='fas fa-truck-loading'></i>");
                    out.print("<span class='hide-menu'> Maquina </span>");
                    out.print("</a>");
                    out.print("</li>");

                    out.print("<li class='sidebar-item' title='Tiempo descontable'>");
                    out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Tiempo_descontable?opc=1' aria-expanded='false'>");
                    out.print("<i class='fas fa-user-clock' aria-hidden='true'></i>");
                    out.print("<span class='hide-menu'> Tiempo Descontable </span>");
                    out.print("</a>");
                    out.print("</li>");
                    if (NombreRol.equals("Administrador")) {
                        out.print("<li class='sidebar-item' title='Recipiente'>");
                        out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Recipiente?opc=1' aria-expanded='false'>");
                        out.print("<i class='fas fa-inbox' aria-hidden='true'></i>");
                        out.print("<span class='hide-menu'> Recipiente </span>");
                        out.print("</a>");
                        out.print("</li>");
                    }
                }
                out.print("</ul>");
            }
            out.print("<li class='sidebar-item' title='Orden de produccion'>");
            out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Orden?opc=1' aria-expanded='false'>");
            out.print("<i class='fas fa-file-powerpoint' aria-hidden='true'></i>");
            out.print("<span class='hide-menu'> Orden de produccion </span>");
            out.print("</a>");
            out.print("</li>");

            out.print("<li class='sidebar-item' title=' Reporte Turno'>");
            out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='Reporte?opc=1&var=1' aria-expanded='false'>");
            out.print("<i class='fas fa-paste' aria-hidden='true'></i>");
            out.print("<span class='hide-menu'> Reporte Turno </span>");
            out.print("</a>");
            out.print("</li>");

            out.print("<li class='sidebar-item' title='R-PRF-015'>");
            out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='EntradaMaterial?opc=1&temp=0' aria-expanded='false'>");
            out.print("<i class='fas fa-box' aria-hidden='true'></i>");
            out.print("<span class='hide-menu'> R-PRF-015 </span>");
            out.print("</a>");
            out.print("</li>");

            out.print("<li class='sidebar-item' title='R-PRF-024'>");
            out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='ReporteDefectos?opc=1&temp=0' aria-expanded='false'>");
            out.print("<i class=\"fas fa-file-excel\"></i>");
            out.print("<span class='hide-menu'> R-PRF-024 </span>");
            out.print("</a>");
            out.print("</li>");

            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora GSC")) {
                out.print("<li class='sidebar-item' title='Reporte Defectos'>");
                out.print("<a class='sidebar-link waves-effect waves-dark sidebar-link' href='ReporteDefectos?opc=1&temp=1' aria-expanded='false'>");
                out.print("<i class='fas fa-file-invoice' aria-hidden='true'></i>");
                out.print("<span class='hide-menu'> Reporte Defectos </span>");
                out.print("</a>");
                out.print("</li>");
            }

            out.print("<li class='hide_menu'>");
            out.print("<a>");
            out.print("<i></i>");
            out.print("<span onclick='Esconder_menu()'><i id='icon_menu' title='Desplegar menu' class=\"fas fa-chevron-right\"></i></span>");
            out.print("</a>");
            out.print("</li>");
            out.print("</ul>");
            out.print("</nav>");
            out.print("</div>");
            out.print("</aside>");

            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
        } catch (Exception e) {
        }

        return super.doStartTag();
    }
}
