package Tags;

import Controladores_BD.AreaJpaController;
import Controladores_BD.CargoJpaController;
import Controladores_BD.CategoriaJpaController;
import Controladores_BD.MenuJpaController;
import Controladores_BD.TipoCategoriaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_plastitec extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            MenuJpaController jpacmnu = new MenuJpaController();
            AreaJpaController jpacars = new AreaJpaController();
            CargoJpaController jpaccgs = new CargoJpaController();
            TipoCategoriaJpaController jpactct = new TipoCategoriaJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            //VARIABLES GLOBALES
            List lst_areas = null;
            List lst_formatos_sst = null;
            List lst_cargos = null;
            List lst_tipo_categorias = null;
            List lst_categorias = null;
            List lst_numero_trabajador = null;
            List lst_numero_trabajadores = null;
            int id_numero_trabajadores = 0;
            int grafica = 0;
            List lst_opciones_permisos = null;
            String permisos = "";
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int id_opcion_menu = 0;
            String arg_meses[] = {"1 ENERO", "2 FEBRERO", "3 MARZO", "4 ABRIL", "5 MAYO", "6 JUNIO", "7 JULIO", "8 AGOSTO", "9 SEPTIEMBRE", "10 OCTUBRE", "11 NOVIEMBRE", "12 DICIEMBRE"};
            if (pageContext.getRequest().getAttribute("Plastitec") != null) {
                //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                id_opcion_menu = Integer.parseInt(pageContext.getRequest().getAttribute("Permisos").toString());
                lst_opciones_permisos = jpacmnu.Opciones_usuario_id(id_opcion_menu, menu);
                if (lst_opciones_permisos != null) {
                    Object[] obj_permisos = (Object[]) lst_opciones_permisos.get(0);
                    permisos = obj_permisos[3].toString();
                } else {
                    permisos = "";
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="AREAS">
                if (pageContext.getRequest().getAttribute("Plastitec").toString().equals("Areas")) {
                    grafica = Integer.parseInt(pageContext.getRequest().getAttribute("Grafica").toString());
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR AREAS">
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar área</h3>");
                        out.print("<form action='Plastitec?opc=2' method='post'>");
                        out.print("Área :");
                        out.print("<input type='text' name='Txt_area' id='Txt_area' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_area');val1.add(Validate.Presence);</script>");
                        out.print("Siglatura :");
                        out.print("<input type='text' name='Txt_sigla' id='Txt_sigla' placeholder='Sigla' maxlength='3' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sigla');val1.add(Validate.Presence);</script>");
                        out.print("Jefe :");
                        out.print("<input type='text' name='Txt_jefe' id='Txt_jefe' placeholder='Nombre del responsable' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_jefe');val1.add(Validate.Presence);</script>");
                        out.print("Correo :");
                        out.print("<textarea name='Txt_correo' id='Txt_correo' placeholder='Correos' onchange='javascript:this.value=this.value.toUpperCase();' /></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("</div>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    lst_areas = jpacars.Consultar_areas();
                    out.print("<div id='content" + ((permisos.contains("I") || rol.equals("ADMINISTRADOR")) ? "" : "_sin") + "'>");
                    out.print("<h3><a href='Plastitec?opc=1&mnu=8&gfc=1'><span class='fa fa-chart-pie fa-size_super_small'></span></a>Áreas<div style='float:right'>"
                            + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
                    if (grafica == 1) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:15%;'>");
                        out.print("<div style='float:right;'><a href='Plastitec?opc=1&mnu=8'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<script type=\"text/javascript\" src=\"Interfaz/Graficas/js/highcharts_principal.js\"></script>");
                        out.print("<script src=\"Interfaz/Graficas/js/highcharts.js\"></script>");
                        out.print("<script src=\"Interfaz/Graficas/js/modules/exporting.js\"></script>");
                        out.print("<script type=\"text/javascript\">");
                        out.print("$(function () {");
                        out.print("$('#Grafica_areas').highcharts({");
                        out.print("chart: {");
                        out.print("type: 'pie',");
                        out.print("options3d: {");
                        out.print("enabled: true,");
                        out.print("alpha: 45");
                        out.print("}");
                        out.print("},");
                        out.print("title: {");
                        out.print("text: 'PLASTITEC S.A'");
                        out.print("},");
                        out.print("subtitle: {");
                        out.print("text: 'Grafica de personal por áreas'");
                        out.print("},");
                        out.print("plotOptions: {");
                        out.print("pie: {");
                        out.print("innerSize: 100,");
                        out.print("depth: 45");
                        out.print("}");
                        out.print("},");
                        out.print("series: [{");
                        out.print("name: 'Porcentaje',");
                        out.print("data: [");
                        for (int i = 0; i < lst_areas.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_areas.get(i);
                            if (i == 0) {
                                out.print("['" + obj_areas[1] + " # " + obj_areas[6] + "', " + obj_areas[7] + "]");
                            } else {
                                out.print(",['" + obj_areas[1] + " # " + obj_areas[6] + "', " + obj_areas[7] + "]");
                            }
                        }
                        out.print("]");
                        out.print("}]");
                        out.print("});");
                        out.print("});");
                        out.print("</script>");
                        out.print("<div id='Grafica_areas' style='min-width: 310px; margin: 0 auto;'></div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
                    // </editor-fold>
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Área</th>");
                    out.print("<th>Siglas</th>");
                    out.print("<th>Cant.Personal</th>");
                    out.print("<th>Jefe</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_areas.size(); i++) {
                        Object[] obj_areas = (Object[]) lst_areas.get(i);
                        out.print("<tr " + ((Integer.parseInt(obj_areas[5].toString()) == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_areas[1] + "</td>");
                        out.print("<td align='center'>" + obj_areas[2] + "</td>");
                        out.print("<td align='center'>" + obj_areas[6] + "</td>");
                        out.print("<td>" + obj_areas[3] + "</td>");
                        out.print("<td align='center'>");
                        if (Integer.parseInt(obj_areas[5].toString()) == 1) {
                            if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='DesactivarArea(" + obj_areas[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                            }
                        } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                            out.print("<span onclick='ActivarArea(" + obj_areas[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
//</editor-fold>
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CARGOS">
                else if (pageContext.getRequest().getAttribute("Plastitec").toString().equals("Cargos")) {
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Cargo</h3>");
                        out.print("<form action='Plastitec?opc=5' method='post'>");
                        out.print("Cargo :");
                        out.print("<input type='text' name='Txt_cargo' id='Txt_cargo' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cargo');val1.add(Validate.Presence);</script>");
                        lst_areas = jpacars.Consultar_areas();
                        out.print("Areas :");
                        out.print("<select name='Cbx_area' id='Cbx_area'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_areas.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_areas.get(i);
                            out.print("<option value='" + obj_areas[0] + "'>" + obj_areas[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br />Especialidad :<br />");
                        out.print("<input type='radio' name='Rdb_especialidad' id='Rdb_especialidad' value='1'  />SI ");
                        out.print("<input type='radio' name='Rdb_especialidad' id='Rdb_especialidad' value='0' checked />NO ");
                        lst_formatos_sst = jpaccgs.Formatos_sst_cargo();
                        out.print("<br /><br />Formato SST :");
                        out.print("<select name='Cbx_formato_sst' id='Cbx_formato_sst'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        out.print("<option value='N/A'>N/A</option>");
                        for (int i = 0; i < lst_formatos_sst.size(); i++) {
                            Object[] obj_formatos_sst = (Object[]) lst_formatos_sst.get(i);
                            out.print("<option value='" + obj_formatos_sst[0] + "'>" + obj_formatos_sst[0] + " / " + obj_formatos_sst[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_formato_sst');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("</div>");
                    }
                    out.print("<div id='content" + ((permisos.contains("I") || rol.equals("ADMINISTRADOR")) ? "" : "_sin") + "'>");
                    lst_cargos = jpaccgs.Consultar_cargos();
                    out.print("<h3>Cargos<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Cargo</th>");
                    out.print("<th>Area</th>");
                    out.print("<th>Especialidad</th>");
                    out.print("<th>Formato SST</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_cargos.size(); i++) {
                        Object[] obj_cargos = (Object[]) lst_cargos.get(i);
                        out.print("<tr " + ((Integer.parseInt(obj_cargos[5].toString()) == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_cargos[1] + "</td>");
                        out.print("<td>" + obj_cargos[3] + "</td>");
                        out.print("<td align='center'>" + (((Integer) obj_cargos[6] == 1) ? "SI" : "NO") + "</td>");
                        out.print("<td>" + obj_cargos[7] + "</td>");
                        out.print("<td align='center'>");
                        if (Integer.parseInt(obj_cargos[5].toString()) == 1) {
                            if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='DesactivarCargo(" + obj_cargos[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                            }
                        } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                            out.print("<span onclick='ActivarCargo(" + obj_cargos[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CATEGORIAS">
                else if (pageContext.getRequest().getAttribute("Plastitec").toString().equals("Categoria")) {
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Categoria</h3>");
                        out.print("<form action='Plastitec?opc=8' method='post'>");
                        out.print("Categoria :");
                        out.print("<input type='text' name='Txt_categoria' id='Txt_categoria' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_categoria');val1.add(Validate.Presence);</script>");
                        out.print("Tipos :");
                        lst_tipo_categorias = jpactct.Consultar_tipo_categorias();
                        out.print("<select name='Cbx_tipo_categoria' id='Cbx_tipo_categoria'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_tipo_categorias.size(); i++) {
                            Object[] obj_tipos_categorias = (Object[]) lst_tipo_categorias.get(i);
                            out.print("<option value='" + obj_tipos_categorias[0] + "'>" + obj_tipos_categorias[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_categoria');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br />Clasificar como materno :<br />");
                        out.print("<input type='radio' name='Rdb_maternidad' id='Rdb_maternidad' value='1'  />SI ");
                        out.print("<input type='radio' name='Rdb_maternidad' id='Rdb_maternidad' value='0' checked />NO ");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("</div>");
                    }
                    out.print("<div id='content" + ((permisos.contains("I") || rol.equals("ADMINISTRADOR")) ? "" : "_sin") + "'>");
                    lst_categorias = jpacctg.Consultar_categorias();
                    out.print("<h3>Categorias<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Categoria</th>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Maternidad</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_categorias.size(); i++) {
                        Object[] obj_categorias = (Object[]) lst_categorias.get(i);
                        out.print("<tr " + ((Integer.parseInt(obj_categorias[4].toString()) == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_categorias[1] + "</td>");
                        out.print("<td>" + obj_categorias[3] + "</td>");
                        out.print("<td align='center'>" + (((Integer) obj_categorias[5] == 1) ? "SI" : "NO") + "</td>");
                        out.print("<td align='center'>");
                        if (Integer.parseInt(obj_categorias[4].toString()) == 1) {
                            if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='DesactivarCategoria(" + obj_categorias[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                            }
                        } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                            out.print("<span onclick='ActivarCategoria(" + obj_categorias[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="# TRABAJADORES">
                else if (pageContext.getRequest().getAttribute("Plastitec").toString().equals("Numero_trabajadores")) {
                    id_numero_trabajadores = Integer.parseInt(pageContext.getRequest().getAttribute("Id_numero_trabajadores").toString());
                    if (id_numero_trabajadores > 0) {
                        lst_numero_trabajador = jpacmnu.Numero_trabajadores_id(id_numero_trabajadores);
                        Object[] obj_numero_trabajadores = (Object[]) lst_numero_trabajador.get(0);
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Modificar # Trabajadores</h3>");
                        out.print("<form action='Plastitec?opc=11&intb=" + obj_numero_trabajadores[0] + "' method='post'>");
                        out.print("Año :");
                        out.print("<input type='number' name='Txt_anio' id='Txt_anio' placeholder='Año' value='" + obj_numero_trabajadores[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_anio');val1.add(Validate.Presence);</script>");
                        out.print("Mes :");
                        out.print("<select name='Cbx_mes' id='Cbx_mes'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < arg_meses.length; i++) {
                            if ((Integer) obj_numero_trabajadores[2] == (i + 1)) {
                                out.print("<option value='" + arg_meses[i].split(" ")[0] + "' selected>" + arg_meses[i].split(" ")[1] + "</option>");
                            } else {
                                out.print("<option value='" + arg_meses[i].split(" ")[0] + "'>" + arg_meses[i].split(" ")[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mes');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br /># Trabajadores :");
                        out.print("<input type='number' name='Txt_num_trabajadores' id='Txt_num_trabajadores' value='" + obj_numero_trabajadores[3] + "'  placeholder='# Trabajadores' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_num_trabajadores');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Modificar' />");
                        out.print("</form>");
                        out.print("</div>");
                    } else {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar # Trabajadores</h3>");
                        out.print("<form action='Plastitec?opc=11&intb=0' method='post'>");
                        out.print("Año :");
                        out.print("<input type='number' name='Txt_anio' id='Txt_anio' placeholder='Año' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_anio');val1.add(Validate.Presence);</script>");
                        out.print("Mes :");
                        out.print("<select name='Cbx_mes' id='Cbx_mes'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < arg_meses.length; i++) {
                            out.print("<option value='" + arg_meses[i].split(" ")[0] + "'>" + arg_meses[i].split(" ")[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mes');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br /># Trabajadores :");
                        out.print("<input type='number' name='Txt_num_trabajadores' id='Txt_num_trabajadores' placeholder='# Trabajadores' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_num_trabajadores');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("</div>");
                    }
                    out.print("<div id='content'>");
                    lst_numero_trabajadores = jpacmnu.Numero_trabajadores();
                    out.print("<h3>Numero de trabajadores<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Año</th>");
                    out.print("<th>Mes</th>");
                    out.print("<th># Trabajadores</th>");
                    out.print("<th>Responsable</th>");
                    out.print("<th>Opc</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_numero_trabajadores.size(); i++) {
                        Object[] obj_numero_trabajadores = (Object[]) lst_numero_trabajadores.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_numero_trabajadores[1] + "</td>");
                        out.print("<td align='center'>" + obj_numero_trabajadores[2] + "</td>");
                        out.print("<td align='center'>" + obj_numero_trabajadores[3] + "</td>");
                        out.print("<td align='center'><b>" + obj_numero_trabajadores[5] + "</b> | " + obj_numero_trabajadores[4] + "</td>");
                        out.print("<td align='center'><span onclick=\"location.href='Plastitec?opc=10&mnu=36&intb=" + obj_numero_trabajadores[0] + "'\" class='fa fa-pen fa-size_small'></span></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_plastitec.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
