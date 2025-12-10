/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Controladores.AreaJpaController;
import Controladores.ParametrosJpaController;
import Controladores.UsuarioJpaController;
import Controladores.CargoJpaController;
import Controladores.CategoriaJpaController;
import Controladores.EtapaJpaController;
import Controladores.FaseJpaController;
import Controladores.PermisosJpaController;
import Controladores.PruebaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Tag_complemento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        //<editor-fold defaultstate="collapsed" desc="SESION">
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        String usuario = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
        String cargo = sesion.getAttribute("Cargo").toString().toUpperCase();
        int id_cargo = Integer.parseInt(sesion.getAttribute("id_position").toString());
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="CONTORLADORES">
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        ParametrosJpaController jpa_parametros = new ParametrosJpaController();
        CargoJpaController jpa_cargos = new CargoJpaController();
        EtapaJpaController jpa_etapa = new EtapaJpaController();
        FaseJpaController jpa_fase = new FaseJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        CategoriaJpaController jpa_categoria = new CategoriaJpaController();
        PermisosJpaController jpa_permisos = new PermisosJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
        String consulta = "", txt_permisos = "";

        int id_g = 0, Temp = 0, id_position = 0, id_permisos = 0;

        List lst_usuario = null, lst_parametros = null, lst_cargos = null, lst_etapa = null, lst_fase = null, lst_area = null, lst_cargo = null, lst_prueba = null, lst_tipo_prueba = null, lst_categoria = null;
        //</editor-fold>

        try {
            try {
                consulta = pageContext.getRequest().getAttribute("complemento").toString();
            } catch (Exception e) {
                consulta = "";
            }
            try {
                id_g = Integer.parseInt(pageContext.getRequest().getAttribute("Id").toString());
            } catch (Exception e) {
                id_g = 0;
            }
            try {
                Temp = Integer.parseInt(pageContext.getRequest().getAttribute("Temp").toString());
            } catch (Exception e) {
                Temp = 0;
            }
            try {
                lst_cargos = jpa_cargo.Consult_position_id(id_cargo);
                Object[] obj_lst_perm_cargo = (Object[]) lst_cargos.get(0);
                txt_permisos = obj_lst_perm_cargo[2].toString();
            } catch (Exception e) {
                id_cargo = 0;
                txt_permisos = "";
            }
            try {
                id_permisos = Integer.parseInt(pageContext.getRequest().getAttribute("id_perm").toString());
            } catch (Exception e) {
                id_permisos = 0;
            }

            //<editor-fold defaultstate="collapsed" desc="VALIDACION URL">
            if (consulta.equals("Pruebas_B")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Pruebas.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Pruebas.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            } else if (consulta.equals("Categoria")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Categorías.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Categorías.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            } else if (consulta.equals("Etapa")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Etapas.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Etapas.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            } else if (consulta.equals("Fase")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Fases.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Fases.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            } else if (consulta.equals("Usuario")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Usuarios.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Usuarios.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            } else if (consulta.equals("Area")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Áreas.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Áreas.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            } else if (consulta.equals("Cargo")) {
                out.print("        <script type = \"text/javascript\" >\n"
                        + "            history.pushState(null, null, 'Cargos.jsp');\n"
                        + "            window.addEventListener('popstate', function (event) {\n"
                        + "                history.pushState(null, null, 'Cargos.jsp');\n"
                        + "            });\n"
                        + "        </script>");
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;width: 35%;margin-left: 40%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            if (consulta.equals("Usuario")) {
                //<editor-fold defaultstate="collapsed" desc="USUARIO">
                out.print("<h4 style='color:black;'>Registrar usuario</h4>");
                out.print("<hr>");
                out.print("<form action='Usuario?opc=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Responsable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Nombre'><b>Nombre</b></label>");
//                out.print("</div>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Apellido'><b>Apellido</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Nombre' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<input class='form-control' type='text' name='Apellido'  placeholder='Apellido' data-toggle='tooltip' data-placement='top' title='Apellido' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Identificacion'><b>Identificacion</b></label>");
//                out.print("</div>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Cargo'><b>Cargo</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='number' name='Identificacion' placeholder='Identificacion' data-toggle='tooltip' data-placement='top' title='Identificacion' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Cargo'>");
                out.print("<select class='select2 form-control' name='Cargo' required>");
                out.print("<option value='' selected disabled hidden>Seleccione cargo</option>");
                lst_cargos = jpa_cargos.Consultar_cargos();
                for (int cu = 0; cu < lst_cargos.size(); cu++) {
                    Object[] obj_lst_cargos = (Object[]) lst_cargos.get(cu);
                    out.print("<option value='" + obj_lst_cargos[0] + "'>" + obj_lst_cargos[3] + " - " + obj_lst_cargos[7] + "</option>");
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='usuario'><b>Usuario</b></label>");
//                out.print("</div>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='contra'><b>Contrase&ntilde;a</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='usuario' placeholder='Usuario' data-toggle='tooltip' data-placement='top' title='Usuario' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' id='contra_year' name='contra'placeholder='Contraseña' data-toggle='tooltip' data-placement='top' title='Contraseña' required readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Correo'><b>Correo electronico</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='email' name='Correo' placeholder='Correo electronico' data-toggle='tooltip' data-placement='top' title='Correo electronico' required />");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<label class='form-label'><b>Ver en la lista de distribuci&oacute;n</b></label>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                lst_parametros = jpa_parametros.Ver_lista_distribucion();
                for (int vld = 0; vld < lst_parametros.size(); vld++) {
                    Object[] obj_lst_parametros = (Object[]) lst_parametros.get(vld);
                    out.print("<div class='col'>");
                    out.print("<div class='form-check form-check-inline' style='margin-left:50%;'>");
                    out.print("<div class='selectgroup selectgroup-pills'>");
                    out.print("<label class='selectgroup-item'>");
                    out.print("<input type='radio' name='icon-input' value='" + obj_lst_parametros[2] + "' class='selectgroup-input' " + ((obj_lst_parametros[2].equals("O") ? "checked" : "")) + ">");
                    out.print("<span class='selectgroup-button selectgroup-button-icon'>" + obj_lst_parametros[3] + "</span>");
                    out.print("</label>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>
            } else if (consulta.equals("Etapa")) {
                //<editor-fold defaultstate="collapsed" desc="ETAPA">
                out.print("<h4 style='color:black;'>Registrar etapas</h4>");
                out.print("<hr>");
                out.print("<form action='Complemento?opc=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Responsable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Numero'><b>Numero</b></label>");
//                out.print("</div>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Numero'><b>Norma</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Numero' placeholder='Numero' data-toggle='tooltip' data-placement='top' title='Numero' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<select name='norma' class='form-control' data-toggle='tooltip' data-placement='top' title='Norma' required>");
                out.print("<option value='' selected disabled hidden>Seleccione norma</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.Ver_norma();
                if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                    for (int prnrm = 0; prnrm < lst_parametros.size(); prnrm++) {
                        Object[] obj_lst_para_norm = (Object[]) lst_parametros.get(prnrm);
                        out.print("<option value='" + obj_lst_para_norm[2] + "'>" + obj_lst_para_norm[3] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='exampleFormControlTextarea1'><b>Guia de la norma</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<textarea class='form-control' name='guia' id='Guia_norma' rows='3' style='margin-left:0%;' placeholder='Guia de la norma' data-toggle='tooltip' data-placement='top' title='Guia de la norma' required></textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='exampleFormControlTextarea1'><b>Etapa</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<textarea class='form-control' name='etapa' id='exampleFormControlTextarea1' rows='3' style='margin-left:0%;' placeholder='Etapa' data-toggle='tooltip' data-placement='top' title='Etapa' required></textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>
            } else if (consulta.equals("Fase")) {
                //<editor-fold defaultstate="collapsed" desc="FASE">
                out.print("<h4 style='color:black;'>Registrar fases</h4>");
                out.print("<hr>");
                out.print("<form action='Complemento?opc=5' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Responsable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Etapa'><b>Etapa</b></label>");
//                out.print("</div>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='norma'><b>Norma</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Etapa'>");
                out.print("<select id='etapa' class='select2 form-control' name='etapa_f' required>");
                out.print("<option value='' selected disabled hidden>Seleccione etapa</option>");
                lst_etapa = null;
                lst_etapa = jpa_etapa.Consultar_etapa();
                if (lst_etapa != null || lst_etapa.size() > 0 || !lst_etapa.isEmpty()) {
                    for (int ef = 0; ef < lst_etapa.size(); ef++) {
                        Object[] obj_lst_etapa_fase = (Object[]) lst_etapa.get(ef);
                        if (Integer.parseInt(obj_lst_etapa_fase[5].toString()) == 1) {
                            out.print("<option  value='" + obj_lst_etapa_fase[0] + "'>" + obj_lst_etapa_fase[3] + " " + obj_lst_etapa_fase[4] + "</option>");
                        }
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
//                out.print("<div class='col'>");
//                out.print("<input class='form-control' type='text' id='norma_E' name='norma' readonly/>");
//                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='letra'><b>Letra</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Letra' data-toggle='tooltip' data-placement='top' title='Letra' placeholder='Letra'/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='exampleFormControlTextarea1'><b>Fase</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<textarea class='form-control' name='fase' id='exampleFormControlTextarea1' rows='3' style='margin-left:0%;' data-toggle='tooltip' data-placement='top' title='Fase' placeholder='Fase' required></textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
//                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>
            } else if (consulta.equals("Area")) {
                //<editor-fold defaultstate="collapsed" desc="AREA">
                out.print("<h4 style='color:black;'>Registrar área</h4>");
                out.print("<hr>");
                out.print("<form action='Complemento?opc=8' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Responsable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='area'><b>&Aacute;rea</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='area' placeholder='Área' data-toggle='tooltip' data-placement='top' title='Área' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='siglatura'><b>Siglatura</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='siglatura' placeholder='Siglatura' data-toggle='tooltip' data-placement='top' title='Siglatura' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>
            } else if (consulta.equals("Cargo")) {
                //<editor-fold defaultstate="collapsed" desc="CARGO">
                out.print("<h4 style='color:black;'>Registrar cargo</h4>");
                out.print("<hr>");
                out.print("<form action='Complemento?opc=11' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='Responsable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "'placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='cargoP'><b>Cargo</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='cargoP' placeholder='Cargo' data-toggle='tooltip' data-placement='top' title='Cargo' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='areaC'><b>Área</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Área'>");
                out.print("<select name='areaC' class='select2 form-control' required>");
                out.print("<option value='' selected hidden disabled>Seleccioner área</option>");
                lst_area = null;
                lst_area = jpa_area.Consultar_areas();
                if (lst_area != null || lst_area.size() > 0 || !lst_area.isEmpty()) {
                    for (int ac = 0; ac < lst_area.size(); ac++) {
                        Object[] obj_lst_are_car = (Object[]) lst_area.get(ac);
                        out.print("<option value='" + obj_lst_are_car[0] + "'>" + obj_lst_are_car[3] + " - " + obj_lst_are_car[4] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col' style='text-align:center;'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>
            } else if (consulta.contains("Pruebas_B")) {
                //<editor-fold defaultstate="collapsed" desc="PRUEBA">
                out.print("<h4 style='color:black;'>Registrar prueba</h4>");
                out.print("<hr>");
                out.print("<div>");
                out.print("<form action='Complemento?opc=14' method='post' class='needs-validation' novalidate=''>");
//                out.print("<div class='row'>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='responable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                out.print("<input class='form-control' type='text' name='responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' readonly required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='prueba'><b>Prueba</b></label>");
//                out.print("</div>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='tipo_prueba'><b>Tipo prueba</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='prueba' placeholder='Prueba' data-toggle='tooltip' data-placement='top' title='Prueba' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<div data-toggle='tooltip' data-placement='top' title='Tipo prueba'>");
                out.print("<select class='form-control select2' name='tipo_prueba' required>");
                out.print("<option value='' selected disabled hidden>Seleccione el tipo de prueba</option>");
                lst_tipo_prueba = jpa_prueba.Tipos_prueba();
                if (lst_tipo_prueba != null || lst_tipo_prueba.size() > 0 || !lst_tipo_prueba.isEmpty()) {
                    for (int tp = 0; tp < lst_tipo_prueba.size(); tp++) {
                        Object[] Obj_lst_t_prueba = (Object[]) lst_tipo_prueba.get(tp);
                        out.print("<option value='" + Obj_lst_t_prueba[1] + "'>" + Obj_lst_t_prueba[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='catego'><b>Categoria</b></label>");
//                out.print("</div>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='Codigo'><b>Código</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<select class='form-control' name='cetego' data-toggle='tooltip' data-placement='top' title='Categoria' required>");
                out.print("<option value='' selected disabled hidden>Seleccione una categoria</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.categoria_prueba();
                if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                    for (int p = 0; p < lst_parametros.size(); p++) {
                        Object[] Obj_lst_paramentros = (Object[]) lst_parametros.get(p);
                        out.print("<option value='" + Obj_lst_paramentros[2] + "'>" + Obj_lst_paramentros[3] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' name='Codigo' placeholder='Código' data-toggle='tooltip' data-placement='top' title='Código' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col text-center'>");
//                out.print("<label for='exampleFormControlTextarea1'><b>Criterio de aceptacion</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<textarea class='form-control' style='margin-left: 0%;' name='aceptacion' id='exampleFormControlTextarea1' rows='3' placeholder='Criterios de aceptacion' data-toggle='tooltip' data-placement='top' title='Criterios de aceptacion' required></textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='row' style='margin-top:-5%;'>");
                out.print("<div class='col text-center'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                //</editor-fold>
            } else if (consulta.contains("Categoria")) {
                //<editor-fold defaultstate="collapsed" desc="CATEGORIA">
                out.print("<h4 style='color:black;'>Registrar categor&iacute;a</h4>");
                out.print("<hr>");
                out.print("<div>");
                out.print("<form action='Complemento?opc=17' method='post' class='needs-validation' novalidate=''>");
//                out.print("<div class='row'>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='responable'><b>Responsable</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                out.print("<input class='form-control' type='text' name='responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' readonly required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
//                out.print("<div class='row'>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='tipo_catego'><b>Tipo</b></label>");
//                out.print("</div>");
//                out.print("<div class='col text-center'>");
//                out.print("<label class='form-check-label' for='catego'><b>Categoría</b></label>");
//                out.print("</div>");
//                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col'>");
                out.print("<select class='form-control' name='tipo_catego' data-toggle='tooltip' data-placement='top' title='Tipo categoría' required>");
                out.print("<option value='' disabled selected hidden>Seleccione tipo categoría</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.Tipo_categoria();
                if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                    for (int pca = 0; pca < lst_parametros.size(); pca++) {
                        Object[] obj_lst_p_tcategoria = (Object[]) lst_parametros.get(pca);
                        out.print("<option value='" + obj_lst_p_tcategoria[2] + "'>" + obj_lst_p_tcategoria[3] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col'>");
                out.print("<input class='form-control' type='text' placeholder='Categoría' data-toggle='tooltip' data-placement='top' title='Categoría' name='catego' required/>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col text-center'>");
                out.print("<span class='text-warning font-weight-bold'>Formato: N/A</span><br>");
                out.print("<label class='form-check-label' for='tipo_campo'><b>Tipo campo</b></label>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='row'>");
                out.print("<div class='col' id='select-container'>");
                out.print("<select class='form-control full-width' name='tipo_camp_catego' id='tipo_camp_catego' required>");
                out.print("<option value='' disabled selected hidden>Seleccione una categoría</option>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.Tipo_campo_categoria();
                if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                    for (int tcc = 0; tcc < lst_parametros.size(); tcc++) {
                        Object[] Obj_lst_paramentros = (Object[]) lst_parametros.get(tcc);
                        String[] formato = Obj_lst_paramentros[3].toString().split("=");
                        out.print("<option value='" + Obj_lst_paramentros[2] + "'>" + formato[0] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col hidden' id='input-container'>");
                out.print("<input class='form-control' type='text' name='formato_catego' id='formato_catego' />");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");

                //<editor-fold defaultstate="collapsed" desc="VALIDACION FORMATO POR TIPO">
                if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                    for (int tcc = 0; tcc < lst_parametros.size(); tcc++) {
                        Object[] Obj_lst_paramentros = (Object[]) lst_parametros.get(tcc);
                        String[] formato = Obj_lst_paramentros[3].toString().split("=");

                        out.print("    <script>\n"
                                + "        document.getElementById('tipo_camp_catego').addEventListener('change', function() {\n"
                                + "            var selectedValue = this.value;\n"
                                + "            var inputField = document.getElementById('formato_catego');\n"
                                + "            var inputContainer = document.getElementById('input-container');\n"
                                + "            var selectContainer = document.getElementById('select-container');\n"
                                + "\n"
                                + "            if (selectedValue) {\n"
                                + "                // Mostrar el campo de entrada y establecer su valor basado en la selección\n"
                                + "                inputContainer.classList.remove('hidden');\n"
                                + "                selectContainer.classList.remove('col');\n"
                                + "                selectContainer.classList.add('col-md-6');\n"
                                + "                \n"
                                + "                if (selectedValue === '" + Obj_lst_paramentros[2] + "') {\n"
                                + "                    inputField.value = '" + formato[1] + "';\n"
                                + "                }"
                                + "            } else {\n"
                                + "                // Ocultar el campo de entrada y ajustar el espacio del select\n"
                                + "                inputContainer.classList.add('hidden');\n"
                                + "                selectContainer.classList.remove('col-md-6');\n"
                                + "                selectContainer.classList.add('col');\n"
                                + "                inputField.value = ''; // Limpia el campo cuando se oculta\n"
                                + "            }\n"
                                + "        });\n"
                                + "    </script>");
                    }
                }
                //</editor-fold>

                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col text-center'>");
                out.print("<label for='exampleFormControlTextarea1'><b>Archivos adjuntos</b></label>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='row'>");
//                out.print("<div class='col'>");
                lst_parametros = null;
                lst_parametros = jpa_parametros.adjunto_catego();
                for (int ac = 0; ac < lst_parametros.size(); ac++) {
                    Object[] obj_lst_parametros = (Object[]) lst_parametros.get(ac);
                    out.print("<div class='col'>");
                    out.print("<div class='form-check form-check-inline' style='margin-left:50%;'>");
                    out.print("<div class='selectgroup selectgroup-pills'>");
                    out.print("<label class='selectgroup-item'>");
                    out.print("<input type='radio' name='icon-input' value='" + obj_lst_parametros[2] + "' class='selectgroup-input' " + ((obj_lst_parametros[2].equals("0") ? "checked" : "")) + " >");
                    out.print("<span class='selectgroup-button selectgroup-button-icon'>" + obj_lst_parametros[3] + "</span>");
                    out.print("</label>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//                out.print("</div>");
                out.print("</div>");
                out.print("<br>");
                out.print("<div class='row'>");
                out.print("<div class='col text-center'>");
                out.print("<button class='btn btn-success'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            if (id_g > 0 && Temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='border: 2px solid #0052a4;border-radius: 12px;margin-top: 4%;width: 35%;margin-left: 40%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;margin-left: 95%;' data-toggle='tooltip' data-placement='left' title='Cerrar'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (consulta.equals("Usuario")) {
                    //<editor-fold defaultstate="collapsed" desc="USUARIO">
                    lst_usuario = null;
                    lst_usuario = jpa_usuario.Traer_usuario(id_g);
                    Object[] obj_lst_usu_id = (Object[]) lst_usuario.get(0);
                    if (lst_usuario != null || lst_usuario.size() > 0) {
                        out.print("<h4 style='color:black;'>Modificar usuario</h4>");
                        out.print("<hr>");
                        out.print("<form action='Usuario?opc=3' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                        out.print("<input type='text' name='Id_usu' value='" + id_g + "' hidden/>");
//                        out.print("<div class='row'>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='Responsable'><b>Responsable</b></label>");
//                        out.print("</div>");
//                        out.print("</div>");
                        out.print("<br>");
                        out.print("<div class='row'>");
                        out.print("<div class='col'>");
                        out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable'  required readonly/>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<br>");
//                        out.print("<div class='row'>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='Nombre'><b>Nombre</b></label>");
//                        out.print("</div>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='Apellido'><b>Apellido</b></label>");
//                        out.print("</div>");
//                        out.print("</div>");
                        out.print("<div class='row'>");
                        out.print("<div class='col'>");
                        out.print("<input class='form-control' type='text' name='Nombre' value='" + obj_lst_usu_id[3] + "' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' required/>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col' style='text-align:center;'>");
                        out.print("<input class='form-control' type='text' name='Apellido' value='" + obj_lst_usu_id[4] + "' placeholder='Apellido' data-toggle='tooltip' data-placement='top' title='Apellido' required/>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<br>");
//                        out.print("<div class='row'>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='Identificacion'><b>Identificacion</b></label>");
//                        out.print("</div>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='Cargo'><b>Cargo</b></label>");
//                        out.print("</div>");
//                        out.print("</div>");
                        out.print("<div class='row'>");
                        out.print("<div class='col'>");
                        out.print("<input class='form-control' type='number' name='Identificacion' value='" + obj_lst_usu_id[5] + "' placeholder='Identificacion' data-toggle='tooltip' data-placement='top' title='Identificacion' required/>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col'>");
                        out.print("<div data-toggle='tooltip' data-placement='top' title='Cargo'>");
                        out.print("<select class='select2 form-control' style='width:100% !important;' name='Cargo' required>");
                        lst_cargos = jpa_cargos.Consultar_cargos();
                        for (int cu = 0; cu < lst_cargos.size(); cu++) {
                            Object[] obj_lst_cargos = (Object[]) lst_cargos.get(cu);
                            out.print("<option value='" + obj_lst_cargos[0] + "' " + ((obj_lst_usu_id[10].equals(obj_lst_cargos[0])) ? "selected" : "") + ">" + obj_lst_cargos[3] + " - " + obj_lst_cargos[7] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<br>");
//                        out.print("<div class='row'>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='usuario'><b>Usuario</b></label>");
//                        out.print("</div>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='contra'><b>Contrase&ntilde;a</b></label>");
//                        out.print("</div>");
//                        out.print("</div>");
                        out.print("<div class='row'>");
                        out.print("<div class='col'>");
                        out.print("<input class='form-control' type='text' name='usuario' value='" + obj_lst_usu_id[6] + "' placeholder='Usuario' data-toggle='tooltip' data-placement='top' title='Usuario' required/>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col'>");
                        out.print("<input class='form-control' type='password' value='" + obj_lst_usu_id[7] + "' name='contra' placeholder='Contraseña' data-toggle='tooltip' data-placement='top' title='Contraseña' required readonly/>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<br>");
//                        out.print("<div class='row'>");
//                        out.print("<div class='col' style='text-align:center;'>");
//                        out.print("<label for='Correo'><b>Correo electronico</b></label>");
//                        out.print("</div>");
//                        out.print("</div>");
                        out.print("<div class='row'>");
                        out.print("<div class='col'>");
                        out.print("<input class='form-control' type='email' name='Correo' value='" + obj_lst_usu_id[8] + "' placeholder='Correo electronico' data-toggle='tooltip' data-placement='top' title='Correo electronico' required />");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<br>");
                        out.print("<div class='row'>");
                        out.print("<div class='col' style='text-align:center;'>");
                        out.print("<label class='form-label'><b>Ver en la lista de distribuci&oacute;n</b></label>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='row'>");
                        lst_parametros = jpa_parametros.Ver_lista_distribucion();
                        for (int vld = 0; vld < lst_parametros.size(); vld++) {
                            Object[] obj_lst_parametros = (Object[]) lst_parametros.get(vld);
                            out.print("<div class='col'>");
                            out.print("<div class='form-check form-check-inline' style='margin-left:50%;'>");
                            out.print("<div class='selectgroup selectgroup-pills'>");
                            out.print("<label class='selectgroup-item'>");
                            out.print("<input type='radio' name='icon-input' value='" + obj_lst_parametros[2] + "' class='selectgroup-input' " + ((obj_lst_parametros[2].equals(obj_lst_usu_id[11]) ? "checked" : "")) + ">");
                            out.print("<span class='selectgroup-button selectgroup-button-icon'>" + obj_lst_parametros[3] + "</span>");
                            out.print("</label>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("<br>");
                        out.print("<div class='row'>");
                        out.print("<div class='col' style='text-align:center;'>");
                        out.print("<button class='btn btn-success'>Modificar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                    } else {
                        out.print("");
                    }

                    //</editor-fold>
                } else if (consulta.equals("Etapa")) {
                    //<editor-fold defaultstate="collapsed" desc="ETAPA">
                    out.print("<h4 style='color:black;'>Modificar etapa</h4>");
                    out.print("<hr>");
                    lst_etapa = jpa_etapa.Traer_etapa(id_g);
                    Object[] obj_lst_id_etapa = (Object[]) lst_etapa.get(0);
                    out.print("<form action='Complemento?opc=3' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                    out.print("<input type='text' name='Id_E' value='" + id_g + "' hidden/>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Responsable'><b>Responsable</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Numero'><b>Numero</b></label>");
//                    out.print("</div>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Numero'><b>Norma</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Numero' value='" + obj_lst_id_etapa[3] + "' placeholder='Numero' data-toggle='tooltip' data-placement='top' title='Numero' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col'>");
                    out.print("<select name='norma' class='form-control' data-toggle='tooltip' data-placement='top' title='Norma' required>");
                    out.print("<option value='' selected disabled hidden>Seleccione norma</option>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.Ver_norma();
                    if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                        for (int prnrm = 0; prnrm < lst_parametros.size(); prnrm++) {
                            Object[] obj_lst_para_norm = (Object[]) lst_parametros.get(prnrm);
                            out.print("<option value='" + obj_lst_para_norm[2] + "' " + ((obj_lst_para_norm[2].equals(obj_lst_id_etapa[6]) ? "selected" : "")) + ">" + obj_lst_para_norm[3] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='exampleFormControlTextarea1'><b>Guia de la norma</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<textarea class='form-control' name='guia' id='Guia_norma_M' rows='3' style='margin-left:0%;' placeholder='Guia de la norma' data-toggle='tooltip' data-placement='top' title='Guia de la norma' required>" + obj_lst_id_etapa[7] + "</textarea>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='exampleFormControlTextarea1'><b>Etapa</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<textarea class='form-control' name='etapa' id='exampleFormControlTextarea1' rows='3' style='margin-left:0%;' placeholder='Etapa' data-toggle='tooltip' data-placement='top' title='Etapa' required>" + obj_lst_id_etapa[4] + "</textarea>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col' style='text-align:center;'>");
                    out.print("<button type='submit' class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else if (consulta.equals("Fase")) {
                    //<editor-fold defaultstate="collapsed" desc="FASE">
                    out.print("<h4 style='color:black;'>Modificar fase</h4>");
                    out.print("<hr>");
                    lst_fase = jpa_fase.Traer_fase(id_g);
                    Object[] obj_lst_id_fase = (Object[]) lst_fase.get(0);
                    out.print("<form action='Complemento?opc=6' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                    out.print("<input type='text' name='id_fas' value='" + id_g + "' hidden/>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Responsable'><b>Responsable</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responsable' required readonly/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Etapa'><b>Etapa</b></label>");
//                    out.print("</div>");
//                out.print("<div class='col' style='text-align:center;'>");
//                out.print("<label for='norma'><b>Norma</b></label>");
//                out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Etapa' value='" + obj_lst_id_fase[7] + " " + obj_lst_id_fase[8] + "' placeholder='Etapa' data-toggle='tooltip' data-placement='top' title='Etapa' required readonly/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
//                out.print("<div class='col'>");
//                out.print("<input class='form-control' type='text' id='norma_E' name='norma' readonly/>");
//                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//                out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='letra'><b>Letra</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Letra' value='" + obj_lst_id_fase[3] + "' placeholder='Letra' data-toggle='tooltip' data-placement='top' title='Letra'/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='exampleFormControlTextarea1'><b>Fase</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<textarea class='form-control' name='fase' id='exampleFormControlTextarea1' rows='3' style='margin-left:0%;' placeholder='Fase' data-toggle='tooltip' data-placement='top' title='Fase' required>" + obj_lst_id_fase[4] + "</textarea>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col' style='text-align:center;'>");
                    out.print("<button class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else if (consulta.equals("Area")) {
                    //<editor-fold defaultstate="collapsed" desc="AREA">
                    out.print("<h4 style='color:black;'>Modificar área</h4>");
                    out.print("<hr>");
                    lst_area = jpa_area.Traer_area(id_g);
                    Object[] obj_lst_id_area = (Object[]) lst_area.get(0);
                    out.print("<form action='Complemento?opc=9' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                    out.print("<input type='text' name='id_a' value='" + id_g + "' hidden/>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Responsable'><b>Responsable</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responable' required readonly/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='area'><b>&Aacute;rea</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='area' value='" + obj_lst_id_area[3] + "' placeholder='área' data-toggle='tooltip' data-placement='top' title='área' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='siglatura'><b>Siglatura</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='siglatura' value='" + obj_lst_id_area[4] + "' placeholder='Siglatura' data-toggle='tooltip' data-placement='top' title='Siglatura' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col' style='text-align:center;'>");
                    out.print("<button class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else if (consulta.equals("Cargo")) {
                    //<editor-fold defaultstate="collapsed" desc="CARGO">
                    out.print("<h4 style='color:black;'>Modificar cargo</h4>");
                    out.print("<hr>");
                    lst_cargo = null;
                    lst_cargo = jpa_cargo.Traer_crago(id_g);
                    Object[] obj_lst_id_cargo = (Object[]) lst_cargo.get(0);
                    out.print("<form action='Complemento?opc=12' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                    out.print("<input type='text' name='Id_C' value='" + id_g + "' hidden/>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='Responsable'><b>Responsable</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responable' required readonly/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='cargoP'><b>Cargo</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='cargoP' value='" + obj_lst_id_cargo[3] + "' placeholder='Cargo' data-toggle='tooltip' data-placement='top' title='Cargo' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col' style='text-align:center;'>");
//                    out.print("<label for='areaC'><b>Área</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<div data-toggle='tooltip' data-placement='top' title='Área'>");
                    out.print("<select name='areaC' class='select2 form-control' required>");
                    lst_area = null;
                    lst_area = jpa_area.Consultar_areas();
                    if (lst_area != null || lst_area.size() > 0 || !lst_area.isEmpty()) {
                        for (int ac = 0; ac < lst_area.size(); ac++) {
                            Object[] obj_lst_are_car = (Object[]) lst_area.get(ac);
                            out.print("<option value='" + obj_lst_are_car[0] + "' " + ((obj_lst_are_car[0].equals(obj_lst_id_cargo[5]) ? "selected" : "")) + ">" + obj_lst_are_car[3] + " - " + obj_lst_are_car[4] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col' style='text-align:center;'>");
                    out.print("<button class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else if (consulta.equals("Pruebas_B")) {
                    //<editor-fold defaultstate="collapsed" desc="PRUEBA">
                    out.print("<h4 style='color:black;'>Modificar prueba</h4>");
                    out.print("<hr>");
                    lst_prueba = jpa_prueba.Traer_prueba(id_g);
                    Object[] obj_lst_id_prueba = (Object[]) lst_prueba.get(0);
                    out.print("<div>");
                    out.print("<form action='Complemento?opc=15' method='post' class='needs-validation' novalidate=''>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='responable'><b>Responsable</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                    out.print("<input type='text' name='Id_P' value='" + id_g + "' hidden/>");
                    out.print("<input class='form-control' type='text' name='responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responable' readonly required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='prueba'><b>Prueba</b></label>");
//                    out.print("</div>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='tipo_prueba'><b>Tipo prueba</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='prueba' value='" + obj_lst_id_prueba[3] + "' placeholder='Prueba' data-toggle='tooltip' data-placement='top' title='Prueba' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col'>");
                    out.print("<div data-toggle='tooltip' data-placement='top' title='Tipo prueba'>");
                    out.print("<select class='form-control select2' style='width: 265.707px !important;' name='tipo_prueba' required>");
                    out.print("<option selected disabled hidden>Seleccione el tipo de prueba</option>");
                    lst_tipo_prueba = jpa_prueba.Tipos_prueba();
                    if (lst_tipo_prueba != null || lst_tipo_prueba.size() > 0 || !lst_tipo_prueba.isEmpty()) {
                        for (int tp = 0; tp < lst_tipo_prueba.size(); tp++) {
                            Object[] Obj_lst_t_prueba = (Object[]) lst_tipo_prueba.get(tp);
                            out.print("<option value='" + Obj_lst_t_prueba[1] + "' " + ((Obj_lst_t_prueba[1].equals(obj_lst_id_prueba[4]) ? "selected" : "")) + ">" + Obj_lst_t_prueba[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='catego'><b>Categoria</b></label>");
//                    out.print("</div>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='Codigo'><b>Código</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<select class='form-control' name='cetego' data-toggle='tooltip' data-placement='top' title='Categoria' required>");
                    out.print("<option selected disabled hidden>Seleccione una categoria</option>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.categoria_prueba();
                    if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                        for (int p = 0; p < lst_parametros.size(); p++) {
                            Object[] Obj_lst_paramentros = (Object[]) lst_parametros.get(p);
                            out.print("<option value='" + Obj_lst_paramentros[2] + "'  " + ((Obj_lst_paramentros[2].equals(obj_lst_id_prueba[5])) ? "selected" : "") + ">" + Obj_lst_paramentros[3] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='Codigo' value='" + obj_lst_id_prueba[8] + "' placeholder='Código' data-toggle='tooltip' data-placement='top' title='Código' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label for='exampleFormControlTextarea1'><b>Criterio de aceptacion</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<textarea class='form-control' style='margin-left: 0%;' name='aceptacion' id='exampleFormControlTextarea1' rows='3' placeholder='Criterio de aceptacion' data-toggle='tooltip' data-placement='top' title='Criterio de aceptacion' required>" + obj_lst_id_prueba[7] + "</textarea>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='row' style='margin-top:-5%;'>");
                    out.print("<div class='col text-center'>");
                    out.print("<button class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (consulta.equals("Categoria")) {
                    //<editor-fold defaultstate="collapsed" desc="CATEGORIA">
                    out.print("<h4 style='color:black;'>Modificar categoria</h4>");
                    out.print("<hr>");
                    lst_categoria = null;
                    lst_categoria = jpa_categoria.Traer_categoria(id_g);
                    Object[] obj_lst_id_catego = (Object[]) lst_categoria.get(0);
                    out.print("<div>");
                    out.print("<form action='Complemento?opc=18' method='post' class='needs-validation' novalidate=''>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='responable'><b>Responsable</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<input type='text' name='complemento' value='" + consulta + "' hidden/>");
                    out.print("<input type='text' name='Id_Catego' value='" + id_g + "' hidden/>");
                    out.print("<input class='form-control' type='text' name='responsable' value='" + usuario + "' placeholder='Responsable' data-toggle='tooltip' data-placement='top' title='Responable' readonly required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
//                    out.print("<div class='row'>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='tipo_catego'><b>Tipo</b></label>");
//                    out.print("</div>");
//                    out.print("<div class='col text-center'>");
//                    out.print("<label class='form-check-label' for='catego'><b>Categoría</b></label>");
//                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<select class='form-control' name='tipo_catego' placeholder='Tipo Categoria' data-toggle='tooltip' data-placement='top' title='Tipo categoría' required>");
                    out.print("<option value='' disabled selected hidden>Seleccione tipo categoría</option>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.Tipo_categoria();
                    if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                        for (int pca = 0; pca < lst_parametros.size(); pca++) {
                            Object[] obj_lst_p_tcategoria = (Object[]) lst_parametros.get(pca);
                            out.print("<option value='" + obj_lst_p_tcategoria[2] + "' " + ((obj_lst_p_tcategoria[2].equals(obj_lst_id_catego[3])) ? "selected" : "") + ">" + obj_lst_p_tcategoria[3] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='catego' value='" + obj_lst_id_catego[4] + "' placeholder='Categoría' data-toggle='tooltip' data-placement='top' title='Categoría' required/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col text-center'>");
                    out.print("<span class='text-warning font-weight-bold'>Formato: N/A</span><br>");
                    out.print("<label class='form-check-label' for='tipo_campo'><b>Tipo campo</b></label>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='row'>");
                    out.print("<div class='col'>");
                    out.print("<select class='form-control full-width' name='tipo_camp_catego' required>");
                    out.print("<option value='' disabled selected hidden>Seleccione una categoria</option>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.Tipo_campo_categoria();
                    if (lst_parametros != null || lst_parametros.size() > 0 || !lst_parametros.isEmpty()) {
                        for (int tcc = 0; tcc < lst_parametros.size(); tcc++) {
                            Object[] Obj_lst_paramentros = (Object[]) lst_parametros.get(tcc);
                            String[] formato = Obj_lst_paramentros[3].toString().split("=");
                            out.print("<option value='" + Obj_lst_paramentros[2] + "' " + ((Obj_lst_paramentros[2].equals(obj_lst_id_catego[5])) ? "selected" : "") + ">" + formato[0] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col'>");
                    out.print("<input class='form-control' type='text' name='formato_catego' value='" + obj_lst_id_catego[6] + "'/>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col text-center'>");
                    out.print("<label for='exampleFormControlTextarea1'><b>Archivos adjuntos</b></label>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='row'>");
//                out.print("<div class='col'>");
                    lst_parametros = null;
                    lst_parametros = jpa_parametros.adjunto_catego();
                    for (int ac = 0; ac < lst_parametros.size(); ac++) {
                        Object[] obj_lst_parametros = (Object[]) lst_parametros.get(ac);
                        out.print("<div class='col'>");
                        out.print("<div class='form-check form-check-inline' style='margin-left:50%;'>");
                        out.print("<div class='selectgroup selectgroup-pills'>");
                        out.print("<label class='selectgroup-item'>");
                        out.print("<input type='radio' name='icon-input' value='" + obj_lst_parametros[2] + "' class='selectgroup-input' " + ((obj_lst_parametros[2].toString().equals(obj_lst_id_catego[8].toString())) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon'>" + obj_lst_parametros[3] + "</span>");
                        out.print("</label>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
//                out.print("</div>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div class='row'>");
                    out.print("<div class='col text-center'>");
                    out.print("<button class='btn btn-success'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            if (id_permisos > 0) {
                //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                lst_cargo = jpa_cargo.Consult_position_id(id_permisos);
                Object[] obj_cargo_permission = (Object[]) lst_cargo.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_role_permission'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Permisos</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Complemento?opc=20' method='post'>");
                out.print("<input type='hidden' name='Cbx_permission' id='Cbx_permission' value='" + obj_cargo_permission[2] + "'>");
                out.print("<input type='hidden' value='" + id_permisos + "' name='id_rol' id='id_rol'>");
                out.print("<input type='hidden' value='" + consulta + "' name='complemento' id='complemento'>");
                out.print("<div class=\"card-body\">");
                out.print("<div class=\"row\">");
                out.print("<div class='col-12 col-sm-12 col-md-4' style='max-height:318px;'>");
                out.print("<div class='scrollbar'>");
                out.print("<ul class=\"nav nav-pills flex-column\" id=\"myTab4\" role=\"tablist\">");
                //<editor-fold defaultstate="collapsed" desc="MODULES">
                lst_cargos = jpa_cargos.Consult_Modules();
                String modules = "", cons_modules = "";
                if (lst_cargos != null) {
                    for (int i = 0; i < lst_cargos.size(); i++) {
                        Object[] Obj_module = (Object[]) lst_cargos.get(i);
                        String module = Obj_module[1].toString().replace(" ", "_").replace("-", "_");
                        out.print("<li class=\"nav-item\">");
                        out.print("<a class=\"nav-link " + ((i == 0) ? "active" : "") + " \" id=\"" + module + "-tab\" data-toggle=\"tab\" href=\"#" + module + "\" role=\"tab\" aria-controls=\"" + module + "\" aria-selected=\"true\">" + Obj_module[1] + "</a>");
                        out.print("</li>");
                        modules += "[" + module + "]";
                        cons_modules += "[" + Obj_module[1] + "]";
                    }
                } else {
                    out.print("<li class=\"nav-item\">");
                    out.print("<a class=\"nav-link active\" id=\"-tab\" data-toggle=\"tab\" href=\"#\" role=\"tab\" aria-controls=\"\" aria-selected=\"true\">Ha ocurrido un error, favor comunicarse a T.I</a>");
                    out.print("</li>");
                }
//                out.print(modules);
//                out.print(cons_modules);
                out.print("</ul>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="PERMISSION LIST">
                out.print("<div class=\"col-12 col-sm-12 col-md-8\">");
                out.print("<div class=\"tab-content no-padding\" id=\"myTab2Content\">");
                try {
                    String[] Arr_modules = modules.replace("][", "//").replace("[", "").replace("]", "").split("//");
                    String[] Arr_modules_cons = cons_modules.replace("][", "//").replace("[", "").replace("]", "").split("//");
                    for (int i = 0; i < Arr_modules.length; i++) {
                        out.print("<div class='tab-pane fade " + ((i == 0) ? "show active" : "") + "' id='" + Arr_modules[i] + "' role='tabpanel' aria-labelledby='" + Arr_modules[i] + "-tab'>");
                        List lst_ficha = jpa_permisos.Consult_permissions_only(Arr_modules_cons[i]);
                        out.print("<h4>Permisos " + Arr_modules_cons[i] + "</h4>");
                        if (lst_ficha != null) {
                            out.print("<div class='module_permss'>");
                            for (int j = 0; j < lst_ficha.size(); j++) {
                                Object[] Obj_module = (Object[]) lst_ficha.get(j);
                                if (obj_cargo_permission[2].toString().contains("[" + Obj_module[0] + "]")) {
                                    out.print("<input type='checkbox' name='#' id='' value='" + Obj_module[0] + "' onclick='Masivo(this.value);' checked><span data-toggle='tooltip' data-placement='right' data-html='true' title='<p>"+Obj_module[3]+"</p>'>" + Obj_module[2] + "</span><br>");
                                } else {
                                    out.print("<input type='checkbox' name='#' id='' value='" + Obj_module[0] + "' onclick='Masivo(this.value);'><span data-toggle='tooltip' data-placement='right' data-html='true' title='<p>"+Obj_module[3]+"</p>'>" + Obj_module[2] + "</span><br>");
                                }
                            }
                            out.print("</div>");
                        } else {
                            out.print("<div class='' style='text-align: center;'>");
                            out.print("<h4 style='margin-top: 5%;'>Se ha producido un error al cargar los permisos, favor comunicarse con T.I</h4>");
                            out.print("<i class=\"fas fa-sad-tear\" style='font-size: 80px;'></i>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                    }
                } catch (Exception e) {
                    out.print("<div class='' style='text-align: center;'>");
                    out.print("<h4 style='margin-top: 5%;'>Se ha producido un error al cargar los permisos, favor comunicarse con T.I</h4>");
                    out.print("<i class=\"fas fa-sad-tear\" style='font-size: 80px;'></i>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
//</editor-fold>

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>");
            //<editor-fold defaultstate="collapsed" desc="NOMBRE MODULO">
            if (consulta.contains("Usuario")) {
                out.print("Usuarios");
            } else if (consulta.contains("Etapa")) {
                out.print("Etapas");
            } else if (consulta.contains("Fase")) {
                out.print("Fases");
            } else if (consulta.contains("Area")) {
                out.print("&Aacute;rea");
            } else if (consulta.contains("Cargo")) {
                out.print("Cargos");
            } else if (consulta.contains("Pruebas_B")) {
                out.print("Pruebas");
            } else if (consulta.contains("Categoria")) {
                out.print("Categorias");
            }
            //</editor-fold>
            out.print("</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>");
            //<editor-fold defaultstate="collapsed" desc="LISTA DE ...">
            if (consulta.contains("Usuario")) {
                out.print("Listado de usuarios");
            } else if (consulta.contains("Etapa")) {
                out.print("Listado de etapas");
            } else if (consulta.contains("Fase")) {
                out.print("Listado de Fases");
            } else if (consulta.contains("Area")) {
                out.print("Listado de &Aacute;reas");
            } else if (consulta.contains("Cargo")) {
                out.print("Listado de cargos");
            } else if (consulta.contains("Pruebas_B")) {
                out.print("Listado de pruebas");
            } else if (consulta.contains("Categoria")) {
                out.print("Listado de categorias");
            }
            //</editor-fold>
            out.print("</h4>");
            //<editor-fold defaultstate="collapsed" desc="CONVENCION USARIOS">
            if (consulta.contains("Usuario")) {
                out.print("<div>");
                out.print("<div class='dropdown d-inline btn-group dropleft'>");
                out.print("<button class='btn btn-info dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>");
                out.print("Convenciones");
                out.print("</button>");
                out.print("<div class='dropdown-menu' style='width: 595px !important;'>");
                out.print("<span class='dropdown-item has-icon disabled'><figure class='avatar mr-2 avatar-xs'><img src='Interfaz/Contenido/Img/avatar-3.png' alt='...'></figure> Este icono, representa que el usuario se encuentra en la lista de distribuci&oacute;n.</span>");
                out.print("<span class='dropdown-item has-icon disabled'><figure class='avatar mr-2 avatar-xs'><img src='Interfaz/Contenido/Img/avatar-4.png' alt='...'></figure> Este icono, representa que el usuario no esta en la lista de distribuci&oacute;n.</span>");
                out.print("<span class='dropdown-item has-icon disabled'><figure class='avatar mr-2 avatar-xs'><img src='Interfaz/Contenido/Img/avatar-5.png' alt='...'></figure> Este icono, representa que el usuario esta inactivo en el aplicativo.</span>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="BOTON REGISTRAR">
            if (consulta.equals("Usuario")) {
                if (txt_permisos.contains("[2]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            } else if (consulta.equals("Etapa")) {
                if (txt_permisos.contains("[7]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            } else if (consulta.equals("Fase")) {
                if (txt_permisos.contains("[11]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            } else if (consulta.equals("Area")) {
                if (txt_permisos.contains("[15]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            } else if (consulta.equals("Cargo")) {
                if (txt_permisos.contains("[19]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            } else if (consulta.equals("Pruebas_B")) {
                if (txt_permisos.contains("[24]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            } else if (consulta.equals("Categoria")) {
                if (txt_permisos.contains("[28]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick = 'mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                }
            }
            //</editor-fold>
            out.print("</div>");

            out.print("<div class:'card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<div class='cuerpo mx-5 mt-3'>");

            //<editor-fold defaultstate="collapsed" desc="TABLA CONSULTA">
            out.print("<table class='table table-bordered table-hover' id='table-1'>");
            if (consulta.contains("Usuario")) {
                //<editor-fold defaultstate="collapsed" desc="USUARIOS">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("Listado");
                out.print("</th>");
                out.print("<th>");
                out.print("Nombre");
                out.print("</th>");
                out.print("<th>");
                out.print("Identificacion");
                out.print("</th>");
                out.print("<th>");
                out.print("Usuario");
                out.print("</th>");
                out.print("<th>");
                out.print("Cargo");
                out.print("</th>");
                if (txt_permisos.contains("[3]") || txt_permisos.contains("[5]") || txt_permisos.contains("[4]")) {
                    out.print("<th>");
                    out.print("Acciones");
                    out.print("</th>");
                }
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_usuario = jpa_usuario.Consultar_usuarios();
                if (lst_usuario != null || !lst_usuario.isEmpty() || lst_usuario.size() > 0) {
                    for (int u = 0; u < lst_usuario.size(); u++) {
                        Object[] obj_lst_usuarios = (Object[]) lst_usuario.get(u);
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'>");
                        out.print("<figure class='avatar mr-2 avatar-sm'>");
                        if (obj_lst_usuarios[12].equals("V") && obj_lst_usuarios[9].equals(1)) {
                            out.print("<img src='Interfaz/Contenido/Img/avatar-3.png' alt='...'>");
                        } else if (obj_lst_usuarios[12].equals("O") && obj_lst_usuarios[9].equals(1)) {
                            out.print("<img src='Interfaz/Contenido/Img/avatar-4.png' alt='...'>");
                        } else if (obj_lst_usuarios[9].equals(0)) {
                            out.print("<img src='Interfaz/Contenido/Img/avatar-5.png' alt='...'>");
                        }
                        out.print("</figure>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<span " + ((obj_lst_usuarios[9].equals(1) ? "" : "class='text-danger'")) + ">" + obj_lst_usuarios[3] + " " + obj_lst_usuarios[4] + "</span>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<span " + ((obj_lst_usuarios[9].equals(1) ? "" : "class='text-danger'")) + ">" + obj_lst_usuarios[5] + "</span>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<span " + ((obj_lst_usuarios[9].equals(1) ? "" : "class='text-danger'")) + ">" + obj_lst_usuarios[6] + "</span>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<span " + ((obj_lst_usuarios[9].equals(1)) ? "" : "class='text-danger'") + ">" + obj_lst_usuarios[11] + "</span>");
                        out.print("</td>");
                        if (txt_permisos.contains("[3]") || txt_permisos.contains("[5]") || txt_permisos.contains("[4]")) {
                            out.print("<td>");
                            out.print("<div style='display: inline-flex;'>");
                            if (obj_lst_usuarios[9].equals(1) && txt_permisos.contains("[3]")) {
                                out.print("<a href='Usuario?opc=1&complemento=" + consulta + "&Id=" + obj_lst_usuarios[0] + "&Temp=0' class='btn btn-warning' onclick = 'mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Editar registro'>");
                                out.print("<i class='fas fa-pen fa-lg'></i>");
                                out.print("</a>");
                            } else {
                                out.print("");
                            }

                            if (obj_lst_usuarios[9].equals(1) && txt_permisos.contains("[5]")) {
//                                out.print("<a href='Usuario?opc=5&complemento=" + consulta + "&Id_usu=" + obj_lst_usuarios[0] + "' class='btn btn-info' style='margin-left: 6%;' data-toggle='tooltip' data-placement='top' title='Restablecer contraseña'>");
//                                out.print("<i class='fas fa-key fa-rotate-180 fa-lg'></i>");
//                                out.print("</a>");
                                out.print("<button type='button' onclick='Restablecer_password(\""+consulta+"\", "+obj_lst_usuarios[0]+")' class='btn btn-info' style='margin-left: 6%;' data-toggle='tooltip' data-placement='top' title='Restablecer contraseña'>");
                                out.print("<i class='fas fa-key fa-rotate-180 fa-lg'></i>");
                                out.print("</button>");
                            } else {
                                out.print("");
                            }

                            if (obj_lst_usuarios[9].equals(1) && txt_permisos.contains("[4]")) {
                                out.print("<button type='button' class='btn btn-success' style='margin-left: 6%;' data-toggle='tooltip' data-placement='top' title='Inactivar registro' onclick='InactivarUsuario(\"" + consulta + "\"," + obj_lst_usuarios[0] + ",0)'>");
                                out.print("<i class='fas fa-check fa-lg'></i>");
                                out.print("</button>");
                            } else if (obj_lst_usuarios[9].equals(0) && txt_permisos.contains("[4]")) {
                                out.print("<a href='#' class='btn btn-danger' style='margin-left: 6%;' data-toggle='tooltip' data-placement='top' title='Activar registro' onclick='ActivarUsuario(\"" + consulta + "\"," + obj_lst_usuarios[0] + ",1)'>");
                                out.print("<i class='fas fa-times fa-lg'></i>");
                                out.print("</a>");
                            }
                            out.print("</div>");
                            out.print("</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                }
                //</editor-fold>
            } else if (consulta.contains("Etapa")) {
                //<editor-fold defaultstate="collapsed" desc="ETAPA">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("Numero");
                out.print("</th>");
                out.print("<th>");
                out.print("Etapa");
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
                lst_etapa = jpa_etapa.Consultar_etapa();
                if (lst_etapa != null || lst_etapa.size() > 0 || !lst_etapa.isEmpty()) {
                    for (int e = 0; e < lst_etapa.size(); e++) {
                        Object[] obj_lst_etapa = (Object[]) lst_etapa.get(e);
                        out.print("<tr>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_etapa[5].toString()) == 1) ? "" : "class='text-danger'") + " >" + obj_lst_etapa[3] + "</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_etapa[5].toString()) == 1) ? "" : "class='text-danger'") + " >" + obj_lst_etapa[4] + "</td>");
                        out.print("<td style='text-align:center;'>");
                        if (txt_permisos.contains("[8]")) {
                            if (Integer.parseInt(obj_lst_etapa[5].toString()) == 1) {
                                out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&Id=" + obj_lst_etapa[0] + "&Temp=0' onclick = 'mostrarConvencion(2)' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar etapa'><i class='fas fa-pen fa-lg'></i></a>");
                            } else {
                                out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td style='text-align:center;'>");
                        if (txt_permisos.contains("[9]")) {
                            out.print(Integer.parseInt(obj_lst_etapa[5].toString()) == 1 ? "<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Inactivar etapa' onclick='InactivarEtapa(\"" + consulta + "\"," + obj_lst_etapa[0] + ", 0)'><i class='fas fa-check fa-lg'></i></button>" : "<button type='button' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar etapa' onclick='ActivarEtapa(\"" + consulta + "\"," + obj_lst_etapa[0] + ", 1)'><i class='fas fa-times fa-lg'></i></button>");
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                //</editor-fold>
            } else if (consulta.contains("Fase")) {
                //<editor-fold defaultstate="collapsed" desc="FASE">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("Etapa");
                out.print("</th>");
                out.print("<th>");
                out.print("Letra");
                out.print("</th>");
                out.print("<th>");
                out.print("Fase");
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
                lst_fase = jpa_fase.Consultar_fases();
                if (lst_fase == null || lst_fase.size() > 0 || lst_fase.isEmpty()) {
                    for (int f = 0; f < lst_fase.size(); f++) {
                        Object[] obj_lst_fases = (Object[]) lst_fase.get(f);
                        out.print("<tr>");
                        out.print("<td>");
                        out.print(obj_lst_fases[7]);
                        out.print("</td>");
                        out.print("<td>");
                        out.print(obj_lst_fases[3]);
                        out.print("</td>");
                        out.print("<td>");
                        out.print(obj_lst_fases[4]);
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[12]")) {
                            out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&Id=" + obj_lst_fases[0] + "&Temp=0' onclick = 'mostrarConvencion(2)' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar registro'><i class='fas fa-pen fa-lg'></i></a>");
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td>");
                        if (txt_permisos.contains("[13]")) {
                            out.print("<button type='button' class='btn btn-success' onclick = 'InactivarFase(\"" + consulta + "\"," + obj_lst_fases[0] + ", 0)' data-toggle='tooltip' data-placement='top' title='Inactivar registro'><i class='fas fa-check fa-lg'></i></button>");
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                //</editor-fold>
            } else if (consulta.contains("Area")) {
                //<editor-fold defaultstate="collapsed" desc="AREA">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("&Aacute;rea");
                out.print("</th>");
                out.print("<th>");
                out.print("Siglatura");
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
                lst_area = jpa_area.Consultar_areas();
                if (lst_area != null || lst_area.size() > 0 || lst_area.isEmpty()) {
                    for (int a = 0; a < lst_area.size(); a++) {
                        Object[] obj_lst_area = (Object[]) lst_area.get(a);
                        out.print("<tr>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_area[5].toString()) == 1) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_area[3]);
                        out.print("</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_area[5].toString()) == 1) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_area[4]);
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[16]")) {
                            if (Integer.parseInt(obj_lst_area[5].toString()) == 1) {
                                out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&Id=" + obj_lst_area[0] + "&Temp=0' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar registro'><i class='fas fa-pen fa-lg'></i></a>");
                            } else if (Integer.parseInt(obj_lst_area[5].toString()) == 0) {
                                out.print("<i class='fas fa-ban fa-lg' style='color: #FF0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[17]")) {
                            if (Integer.parseInt(obj_lst_area[5].toString()) == 1) {
                                out.print("<button type='button' class='btn btn-success' onclick='InactivarArea(\"" + consulta + "\"," + obj_lst_area[0] + ", 0)' data-toggle='tooltip' data-placement='top' title='Inactivar registro' ><i class='fas fa-check fa-lg'></i></button>");
                            } else if (Integer.parseInt(obj_lst_area[5].toString()) == 0) {
                                out.print("<button type='button' class='btn btn-danger' onclick='ActivarArea(\"" + consulta + "\"," + obj_lst_area[0] + ", 1)' data-toggle='tooltip' data-placement='top' title='Activar registro'><i class='fas fa-times fa-lg'></i></button>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                //</editor-fold>
            } else if (consulta.contains("Cargo")) {
                //<editor-fold defaultstate="collapsed" desc="CARGO">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("Cargo");
                out.print("</th>");
                out.print("<th>");
                out.print("Área");
                out.print("</th>");
                out.print("<th>");
                out.print("Sigla");
                out.print("</th>");
                out.print("<th>");
                out.print("Modificar");
                out.print("</th>");
                out.print("<th>");
                out.print("Permisos");
                out.print("</th>");
                out.print("<th>");
                out.print("Estado");
                out.print("</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_cargo = jpa_cargo.Consultar_cargos();
                if (lst_cargo == null || lst_cargo.size() > 0 || lst_cargo.isEmpty()) {
                    for (int c = 0; c < lst_cargo.size(); c++) {
                        Object[] obj_lst_cargo = (Object[]) lst_cargo.get(c);
                        out.print("<tr>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_cargo[4].toString()) == 0) ? "class='text-danger'" : "") + ">");
                        out.print(obj_lst_cargo[3]);
                        out.print("</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_cargo[4].toString()) == 0) ? "class='text-danger'" : "") + ">");
                        out.print(obj_lst_cargo[6]);
                        out.print("</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_cargo[4].toString()) == 0) ? "class='text-danger'" : "") + ">");
                        out.print(obj_lst_cargo[7]);
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("20")) {
                            if (Integer.parseInt(obj_lst_cargo[4].toString()) == 1) {
                                out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&Id=" + obj_lst_cargo[0] + "&Temp=0' onclick='mostrarConvencion(2)' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar registro'><i class='fas fa-pen fa-lg'></i></a>");
                            } else if (Integer.parseInt(obj_lst_cargo[4].toString()) == 0) {
                                out.print("<i class='fas fa-ban fa-lg' style='color:#FF0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[21]")) {
                            if (Integer.parseInt(obj_lst_cargo[4].toString()) == 1) {
                                out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&id_perm=" + obj_lst_cargo[0] + "' onclick='mostrarConvencion(3)' class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Asignar permisos'><i class='fas fa-shield-alt fa-lg'></i></a>");
                            } else if (Integer.parseInt(obj_lst_cargo[4].toString()) == 0) {
                                out.print("<i class='fas fa-ban fa-lg' style='color:#FF0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[22]")) {
                            if (Integer.parseInt(obj_lst_cargo[4].toString()) == 1) {
                                out.print("<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Inactivar registro' onclick ='InactivarCargo(\"" + consulta + "\", " + obj_lst_cargo[0] + ", 0)'><i class='fas fa-check fa-lg'></i></button>");
                            } else if (Integer.parseInt(obj_lst_cargo[4].toString()) == 0) {
                                out.print("<button type='button' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar registro' onclick ='ActivarCargo(\"" + consulta + "\", " + obj_lst_cargo[0] + ", 1)'><i class='fas fa-times fa-lg'></i></button>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No Tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                //</editor-fold>
            } else if (consulta.contains("Pruebas_B")) {
                //<editor-fold defaultstate="collapsed" desc="PRUEBA">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("Prueba");
                out.print("</th>");
                out.print("<th>");
                out.print("Tipo prueba");
                out.print("</th>");
                out.print("<th>");
                out.print("Categoria");
                out.print("</th>");
                out.print("<th>");
                out.print("Documento");
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
                lst_prueba = jpa_prueba.Consultar_pruebas();
                if (lst_prueba != null || lst_prueba.size() > 0 || !lst_prueba.isEmpty()) {
                    for (int pr = 0; pr < lst_prueba.size(); pr++) {
                        Object[] obj_lst_pruebas = (Object[]) lst_prueba.get(pr);
                        out.print("<tr>");
                        out.print("<td " + ((obj_lst_pruebas[6].equals(1)) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_pruebas[3]);
                        out.print("</td>");
                        out.print("<td " + ((obj_lst_pruebas[6].equals(1)) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_pruebas[4]);
                        out.print("</td>");
                        out.print("<td " + ((obj_lst_pruebas[6].equals(1)) ? "" : "class='text-danger'") + ">");
                        out.print("<span class='font-weight-bold'>" + obj_lst_pruebas[5] + "</span>");
                        out.print("</td>");
                        out.print("<td " + ((obj_lst_pruebas[6].equals(1)) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_pruebas[8]);
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[25]")) {
                            if (obj_lst_pruebas[6].equals(1)) {
                                out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&Id=" + obj_lst_pruebas[0] + "&Temp=0' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar registro'><i class='fas fa-pen fa-lg'></i></a>");
                            } else if (obj_lst_pruebas[6].equals(0)) {
                                out.print("<i class='fas fa-ban fa-lg' style='color:#FF0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[26]")) {
                            if (obj_lst_pruebas[6].equals(1)) {
                                out.print("<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Inactivar registro' onclick='InactivarPrueba(\"" + consulta + "\"," + obj_lst_pruebas[0] + ",0)'><i class='fas fa-check fa-lg'></i></button>");
                            } else if (obj_lst_pruebas[6].equals(0)) {
                                out.print("<button type='button' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar registro' onclick='ActivarPrueba(\"" + consulta + "\"," + obj_lst_pruebas[0] + ",1)'><i class='fas fa-times fa-lg'></i></button>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                //</editor-fold>
            } else if (consulta.contains("Categoria")) {
                //<editor-fold defaultstate="collapsed" desc="CATEGORIA">
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>");
                out.print("Tipo categor&iacute;a");
                out.print("</th>");
                out.print("<th>");
                out.print("Categor&iacute;a");
                out.print("</th>");
                out.print("<th>");
                out.print("Tipo campo");
                out.print("</th>");
                out.print("<th>");
                out.print("Archivo");
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
                lst_categoria = jpa_categoria.Consultar_categorias();
                if (lst_categoria != null || lst_categoria.size() > 0 || !lst_categoria.isEmpty()) {
                    for (int catego = 0; catego < lst_categoria.size(); catego++) {
                        Object[] obj_lst_categoria = (Object[]) lst_categoria.get(catego);
                        out.print("<tr>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_categoria[7].toString()) == 1) ? "" : "class='text-danger'") + ">");
                        out.print("<span class='font-weight-bold'>" + obj_lst_categoria[3] + "</span>");
                        out.print("</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_categoria[7].toString()) == 1) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_categoria[4]);
                        out.print("</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_categoria[7].toString()) == 1) ? "" : "class='text-danger'") + ">");
                        out.print(obj_lst_categoria[5]);
                        out.print("</td>");
                        out.print("<td " + ((Integer.parseInt(obj_lst_categoria[7].toString()) == 1) ? "" : "class='text-danger'") + ">");
                        out.print(Integer.parseInt(obj_lst_categoria[8].toString()) == 1 ? "SI" : "NO");
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[29]")) {
                            if (Integer.parseInt(obj_lst_categoria[7].toString()) == 1) {
                                out.print("<a href='Complemento?opc=1&complemento=" + consulta + "&Id=" + obj_lst_categoria[0] + "&Temp=0' class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='Modificar registro'><i class='fas fa-pen fa-lg'></i></a>");
                            } else if (Integer.parseInt(obj_lst_categoria[7].toString()) == 0) {
                                out.print("<i class='fas fa-ban fa-lg' style='color:#FF0000;font-size:30px;' data-toggle='tooltip' data-placement='top' title='No se puede realizar esta acción'></i>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("<td class='text-center'>");
                        if (txt_permisos.contains("[30]")) {
                            if (Integer.parseInt(obj_lst_categoria[7].toString()) == 1) {
                                out.print("<button type='button' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Inactivar categoria' onclick='InactivarCatego(\"" + consulta + "\"," + obj_lst_categoria[0] + ",0)'><i class='fas fa-check fa-lg'></i></button>");
                            } else if (Integer.parseInt(obj_lst_categoria[7].toString()) == 0) {
                                out.print("<button type='button' class='btn btn-danger' data-toggle='tooltip' data-placement='top' title='Activar categoria' onclick='ActivarCatego(\"" + consulta + "\"," + obj_lst_categoria[0] + ",1)'><i class='fas fa-times fa-lg'></i></button>");
                            }
                        } else {
                            out.print("<i class='fas fa-ban fa-lg' style='color: #ff0000;font-size: 30px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos para realizar esta acción'></i>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }

                }
                out.print("</tbody>");
                //</editor-fold>
            }
            out.print("</table>");
            //</editor-fold>

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("</div>");
            out.print("</div>");

            out.print("</section>");

        } catch (IOException ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
