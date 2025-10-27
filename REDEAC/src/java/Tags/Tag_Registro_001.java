package Tags;

import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladoras.RegistroJpaController;
import Controladoras.Registro_001JpaController;
import Controladoras.EquipoJpaController;
import Controladoras.ListasVerificacionJpaController;
import Controladoras.CasoJpaController;
import Controladoras.PermisosJpaController;
import SQL.Connection_mysql_sirh;
import java.time.LocalDateTime;
import SQL.Consultas_117;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class Tag_Registro_001 extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String nombre_apellidos = pageContext.getSession().getAttribute("Nombre_apellido").toString();
        int id_usuarioS = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        RegistroJpaController jpa_registro = new RegistroJpaController();
        Registro_001JpaController jpa_reg_001 = new Registro_001JpaController();
        Connection_mysql_sirh jpa_usuarios = new Connection_mysql_sirh();
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        ListasVerificacionJpaController jpa_listaEquipo = new ListasVerificacionJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        PermisosJpaController PermisosJpa = new PermisosJpaController();
        Consultas_117 jpa_consultas = new Consultas_117();
        List lst_usuario = null;
        List lst_registro = null;
        List lst_reg001 = null;
        List lst_consultaUsers = null;
        List lst_equipos = null;
        List lst_listaEquipos = null;
        List lst_firma = null;
        List lst_permisos = null;
        LocalDateTime DateTime = LocalDateTime.now();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date eate = new Date();
        int mesActual = DateTime.getMonthValue();
        int anioActual = DateTime.getYear();
        int diaActual = DateTime.getDayOfMonth();
        int minActual = DateTime.getMinute();
        int horaActual = DateTime.getHour();
        int anio = 0, mes = 0, fto = 0, id_reg = 0, documento = 0, codigo = 0;
        String[] meses = {"N/A", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        try {
            //<editor-fold defaultstate="collapsed" desc="CAPTURAR DATOS">
            try {
                anio = Integer.parseInt(pageContext.getRequest().getAttribute("anio").toString());
                mes = Integer.parseInt(pageContext.getRequest().getAttribute("mes").toString());
            } catch (Exception e) {
                anio = 0;
                mes = 0;
            }
            try {
                fto = Integer.parseInt(pageContext.getRequest().getAttribute("funcionamiento").toString());
            } catch (Exception e) {
                fto = 0;
            }
            try {
                id_reg = Integer.parseInt(pageContext.getRequest().getAttribute("id_regActividad").toString());
            } catch (Exception e) {
                id_reg = 0;
            }
            //</editor-fold>
            if (anio > 0 && mes == 0) {
                //<editor-fold defaultstate="collapsed" desc="VISTA DE CALENDARIO DE R-TI-001">          
                out.print("<div class='c_head'>");
                out.print("<div>");
                out.print("<h3>R-TI-001</h3>");
                out.print("</div>");
                out.print("<div>");
                out.print("<form action='Registro_001?opc=1' method='post' id='form_anio'>");
                lst_reg001 = jpa_reg_001.Consultar_Anios();
                out.print("<select name='anio' onchange=\"this.form.submit()\">");
                out.print("<option value='" + anio + "'>" + anio + "</option>");
                for (int i = 0; i < lst_reg001.size(); i++) {
                    Object[] obj_anios = (Object[]) lst_reg001.get(i);
                    out.print("<option onclick='EnviarForm()'>" + obj_anios[1] + "</option>");
                }
                out.print("</select>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_month'>");
                out.print("<div class='cont_meses' id='cont_mes1'>");
                for (int i = 1; i < 5; i++) {
                    lst_registro = jpa_registro.Consulta_contadores_mes(anio, i);
                    Object[] obj_cont = (Object[]) lst_registro.get(0);
                    List lst_cont = jpa_registro.Contador_CasosRegistrados(anio, i);
                    Object[] obj_cont2 = (Object[]) lst_cont.get(0);
                    out.print("<div class='cont_month1'>");
                    out.print("<div class='cont_h'>");
                    out.print("<h2 style='color: black;'>" + meses[i] + "</h2>");
                    out.print("</div>");
                    out.print("<div class='cont_b'>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/atencion_cliente_r.fw.png' width='50%'></b><span class='tooltiptext'>Casos Registrados</span><br>" + obj_cont2[0] + "</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/atencion_cliente.png' width='50%'></b><span class='tooltiptext'>Casos Solucionados</span><br>" + obj_cont[0] + "</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/temporizadorE.png' width='50%'></b><span class='tooltiptext'>Parada Equipo: </span><br>" + ((obj_cont[1] == null) ? "0" : obj_cont[1]) + " min</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/temporizadorP.png' width='50%'></b><span class='tooltiptext'>Parada Produccion: </span><br>" + ((obj_cont[2] == null) ? "0" : obj_cont[2]) + " min</div>");
                    out.print("</div>");
//                    out.print("<button class='button-64' style='width: 64%;margin-left: 18%;'><spqan><a href='Registro_001?opc=1&anio=" + anio + "&mes&=" + i + "'>Consultar</a></button>");
//                    out.print("<button class=\"button-64\" role=\"button\" style='width: 64%;margin-left: 12%;font-size: 15px;'><span class='text'><a class='link_a' href='Registro_001?opc=1&anio=" + anio + "&mes=" + i + "'>Consultar</a></span></button>");
                    out.print("<a class='link_a' href='Registro_001?opc=1&anio=" + anio + "&mes=" + i + "'><button class=\"button-64\" id=\"bnt_64\" role=\"button\"><span class='text'>Consultar</span></button></a>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("<div class='cont_meses' id='cont_mes2'>");
                for (int i = 5; i < 9; i++) {
                    lst_registro = jpa_registro.Consulta_contadores_mes(anio, i);
                    Object[] obj_cont = (Object[]) lst_registro.get(0);
                    List lst_cont = jpa_registro.Contador_CasosRegistrados(anio, i);
                    Object[] obj_cont2 = (Object[]) lst_cont.get(0);
                    out.print("<div class='cont_month1'>");
                    out.print("<div class='cont_h'>");
                    out.print("<h2 style='color: black;'>" + meses[i] + "</h2>");
                    out.print("</div>");
                    out.print("<div class='cont_b'>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/atencion_cliente_r.fw.png' width='50%'></b><span class='tooltiptext'>Casos Registrados</span><br>" + obj_cont2[0] + "</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/atencion_cliente.png' width='50%'></b><span class='tooltiptext'>Casos Solucionados</span><br>" + obj_cont[0] + "</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/temporizadorE.png' width='50%'></b><span class='tooltiptext'>Parada equipo: </span><br>" + ((obj_cont[1] == null) ? "0" : obj_cont[1]) + " min</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/temporizadorP.png' width='50%'></b><span class='tooltiptext'>Parada Produccion: </span><br>" + ((obj_cont[2] == null) ? "0" : obj_cont[2]) + " min</div>");
                    out.print("</div>");
//                    out.print("<button class='button-70' style='width: 64%;margin-left: 18%;'><a href='Registro_001?opc=1&anio=" + anio + "&mes&=" + i + "'>Consultar</a></button>");
//                    out.print("<button class=\"button-64\" role=\"button\" style='width: 64%;margin-left: 12%;font-size: 15px;'><span class='text'><a class='link_a' href='Registro_001?opc=1&anio=" + anio + "&mes=" + i + "'>Consultar</a></span></button>");
                    out.print("<a class='link_a' href='Registro_001?opc=1&anio=" + anio + "&mes=" + i + "'><button class=\"button-64\" id=\"bnt_64\" role=\"button\" ><span class='text'>Consultar</span></button></a>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("<div class='cont_meses'>");
                for (int i = 9; i < 13; i++) {
                    lst_registro = jpa_registro.Consulta_contadores_mes(anio, i);
                    Object[] obj_cont = (Object[]) lst_registro.get(0);
                    List lst_cont = jpa_registro.Contador_CasosRegistrados(anio, i);
                    Object[] obj_cont2 = (Object[]) lst_cont.get(0);
                    out.print("<div class='cont_month1'>");
                    out.print("<div class='cont_h'>");
                    out.print("<h2 style='color: black;'>" + meses[i] + "</h2>");
                    out.print("</div>");
                    out.print("<div class='cont_b'>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/atencion_cliente_r.fw.png' width='50%'></b><span class='tooltiptext'>Casos Registrados</span><br>" + obj_cont2[0] + "</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/atencion_cliente.png' width='50%'></b><span class='tooltiptext'>Casos Solucionados</span><br>" + obj_cont[0] + "</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/temporizadorE.png' width='50%'></b><span class='tooltiptext'>Parada equipo: </span><br>" + ((obj_cont[1] == null) ? "0" : obj_cont[1]) + " min</div>");
                    out.print("<div class='tooltip_2' style='border-bottom: none;text-align: center;'><b><img src='Interfaz/Contenido/Images/temporizadorP.png' width='50%'></b><span class='tooltiptext'>Parada Produccion: </span><br>" + ((obj_cont[2] == null) ? "0" : obj_cont[2]) + " min</div>");
                    out.print("</div>");
//                    out.print("<button class='button-70' style='width: 64%;margin-left: 18%;'><a href='Registro_001?opc=1&anio=" + anio + "&mes&=" + i + "'>Consultar</a></button>");
//                    out.print("<button class=\"button-64\" role=\"button\" style='width: 64%;margin-left: 12%;font-size: 15px;'><span class='text'><a class='link_a' href='Registro_001?opc=1&anio=" + anio + "&mes=" + i + "'>Consultar</a></span></button>");
                    out.print("<a class='link_a' href='Registro_001?opc=1&anio=" + anio + "&mes=" + i + "'><button class=\"button-64\" id=\"bnt_64\" role=\"button\" ><span class='text'>Consultar</span></button></a>");
                    out.print("</div>");
                }
//                
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (anio > 0 && mes > 0) {
                //<editor-fold defaultstate="collapsed" desc="INGRESAR A CONSULTAS POR MES">
                if (fto == 1) {
                    //<editor-fold defaultstate="collapsed" desc="EDITAR ACTIVIDAD">

                    lst_reg001 = jpa_reg_001.Consultar_registroActividad_id(id_reg);
                    Object[] obj_edit = (Object[]) lst_reg001.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_actividad'>");
                    out.print("<div style='width: 100%; display: flex; justify-content: space-between;'>");
                    out.print("<h2>Modificar Actividad</h2>");
                    out.print("<button onclick='mostrarConvencion(2)' style='height: 1%;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<form action='Registro_001?opc=3' method='post'>");
                    out.print("<input type='hidden' name='txt_mes' value='" + mes + "'>");
                    out.print("<input type='hidden' name='txt_anio' value='" + anio + "'>");
                    out.print("<input type='hidden' name='id_regAct' value='" + obj_edit[0] + "'>");
                    out.print("<input type='hidden' name='fto' value='1'>");
                    out.print("<div>");
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR 1">                
                    out.print("<div class='container_1'>");
                    out.print("<div style=''>");
                    out.print("<b>Fecha/hora Solicitud</b><br>");
                    out.print("<input type='datetime-local' class='form-control' name='fch_solicitud' id='datepicker' placeholder='' value='" + obj_edit[1] + "' style='width: 196px;' required>"
                            + "");
                    out.print("</div>");

                    lst_consultaUsers = jpa_usuarios.Empleado_sirh_nombre_area();
                    out.print("<div class='selectt' style=''>");
                    out.print("<b>Funcionario y Area</b><br>");
                    out.print("<select class='form-control' data-live-search='true' name='txt_funcArea'>");
                    out.print("<option>" + obj_edit[2] + "</option>");
                    for (int i = 0; i < lst_consultaUsers.size(); i++) {
                        String[] arg_personal = lst_consultaUsers.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                        out.print("<option>" + arg_personal[i] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div style=''>");
                    lst_equipos = jpa_equipo.consultaEquipos();
                    lst_listaEquipos = jpa_listaEquipo.consultaListaDetalleVerificacionGeneral();
                    int temp = 0;
//                    int e_verifi = Integer.parseInt(obj_edit[5].toString());
                    if ((obj_edit[5].toString().equals("N/A")) || (obj_edit[5].toString().equals("NA"))) {
                        out.print("<b>Equipo: </b><input type='radio' name='tipo' id='txt_equipo' value='1' onclick='mostrarCampos(1)'>&nbsp;&nbsp;&nbsp;");
                        out.print("<b>Otro: </b><input type='radio' name='tipo' id='txt_otro' value='2' checked onclick='mostrarCampos(2)'><br>");
                        temp = 1;
                    } else {
                        out.print("<b>Equipo: </b><input type='radio' name='tipo' id='txt_equipo' value='1' checked onclick='mostrarCampos(1)'>&nbsp;&nbsp;&nbsp;");
                        out.print("<b>Otro: </b><input type='radio' name='tipo' id='txt_otro' value='2' onclick='mostrarCampos(2)'><br>");
                        temp = 2;
                    }
                    out.print("<div id='modal1' style=' display: " + ((temp == 1) ? "none" : "block") + "'>");
                    out.print("<select class='form-control' name='txt_pc' placeholder='pc' value='' style='width: 100%;' data-live-search='true'>");
                    out.print("<option value='" + obj_edit[4] + "' style='display:none'>" + obj_edit[5] + "</option>");
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                        out.println("<option value='" + obj_equipos[0] + "'>" + obj_equipos[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");

                    out.print("<div id='modal2' style='display: " + ((temp == 2) ? "none" : "block") + "''>");
                    out.print("<select type='' class='form-control' name='txt_otro' placeholder='Otro' value='' style='width: 100%;' data-live-search='true'>");
                    out.print("<option value='" + obj_edit[6] + "' style='display:none'>" + obj_edit[7] + "</option>");
                    for (int k = 0; k < lst_listaEquipos.size(); k++) {
                        Object[] obj_Listequipos = (Object[]) lst_listaEquipos.get(k);
                        out.println("<option value='" + obj_Listequipos[0] + "'>" + obj_Listequipos[3] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div>");
                    int id_tp = Integer.parseInt(obj_edit[15].toString());
                    lst_listaEquipos = jpa_reg_001.Consultar_soportes_id(id_tp);
                    out.print("<b>Tipo Soporte</b><br>");
                    out.print("<select class='form-control' placeholder='Tipo Soporte' data-live-search='true' name='txt_tipoSop'>");
                    lst_reg001 = jpa_reg_001.Consultar_soportes();
                    if (lst_listaEquipos != null) {
                        Object[] obj_idTipoS = (Object[]) lst_listaEquipos.get(0);
                        out.print("<option value=" + obj_idTipoS[0] + "> " + obj_idTipoS[1] + " </option>");
                    } else {
                        out.print("<option value='0'>No espeficado</option>");
                    }
                    for (int i = 0; i < lst_reg001.size(); i++) {
                        Object[] obj_sopo = (Object[]) lst_reg001.get(i);
                        out.print("<option value=" + obj_sopo[0] + "> " + obj_sopo[1] + " </option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR 2">                
                    out.print("<div style='display: flex; justify-content: space-evenly;'>");
                    out.print("<div>");
                    out.print("<b>Fecha/hora Ejecución</b><br>");
                    out.print("<input type='datetime-local' class='form-control' name='fch_ejecucion' id='fch_ejecucion' placeholder='' value='" + obj_edit[11] + "' style='width: 196px;' required>"
                            + "");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Fecha/hora Solución </b><br>");
                    out.print("<input type='datetime-local' class='form-control' name='fch_solucion' id='fch_solucion' placeholder='' value='" + obj_edit[3] + "' style='width: 196px;' required>"
                            + "");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Ejecuto</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_ejecutor' id='txt_ejecutor' value='" + obj_edit[10] + "' disabled style='width: 100%;'>"
                            + "");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR 3">      
                    out.print("<div class='cont_regDia'>");
                    out.print("<textarea id='editor' style='background: white;' name='txt_act_sol' required>");
                    out.print("<div contenteditable=\"true\">");
                    out.print("" + obj_edit[8] + ""
                            + "<hr>"
                            + "" + obj_edit[9] + "");
                    out.print("</div>");
                    out.print("</textarea>");
                    out.print("<script>");
                    out.print("$('#summernote').summernote({");
                    out.print("placeholder: 'Por favor recargar la pagina!!',");
                    out.print("tabsize: 2,");
                    out.print("height: 100");
                    out.print("});");
                    out.print("</script>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR BOTON">            
                    out.print("<div class='cl_boton'>");
                    out.print("<input type='reset' class='btn_act' value='Limpiar'>&nbsp;");
                    out.print("<button type='submit' class='btn_act'>Finalizar</button>");
                    out.print("</div>");
                    //</editor-fold>        
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (fto == 2) {
                    //<editor-fold defaultstate="collapsed" desc="FIRMAR ACTIVIDAD">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_actividad' style='width:480px;'>");
                    out.print("<div style='width: 100%; display: flex; justify-content: space-between;'>");
                    out.print("<h2>Firmar Actividad</h2>");
                    out.print("<button onclick='mostrarConvencion(3)' style='height: 1%;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    try {
                        codigo = Integer.parseInt(pageContext.getRequest().getAttribute("codigo_usuario").toString());
                    } catch (Exception e) {
                        codigo = 0;
                    }
                    if (codigo != 0) {
                        lst_firma = jpa_caso.Traer_firmas_codigo(codigo);
                        //<editor-fold defaultstate="collapsed" desc="MOSTRAR FIRMAS">
                        if (lst_firma != null) {
                            Object[] obj_firma = (Object[]) lst_firma.get(0);
                            if (obj_firma[3] != null) {
                                out.print("<div>");
                                out.print("<form action='Registro_001?opc=4&fto=2' name='' id='' method='post' style='margin:0px;'>");
                                out.print("<center>");
                                out.print("<div style='display: flex;justify-content: space-evenly;'>");
                                out.print("<input type='hidden' name='txt_mes' value='" + mes + "'>");
                                out.print("<input type='hidden' name='txt_anio' value='" + anio + "'>");
                                out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigo != 0) ? codigo : "") + "' placeholder='Codigo' style='width:80px;margin: 0px;height: 30px;width: 75%;border-radius: 6px;margin-left: 35px;' required>&nbsp;&nbsp;&nbsp;");
                                out.print("</div>");
                                out.print("</center>");
                                out.print("</div>");
                                out.print("<div style='text-align: center;'>");
                                out.print("<h3 style='margin-top: 10px; margin-bottom: 0px;'>Califica la atención prestada!</h3>");
                                out.print("<div class=\"stars\">");
                                out.print("<input class=\"star star-5\" id=\"star-5\" value='5' type=\"radio\" name=\"star\"/ checked required>");
                                out.print("<label class=\"star star-5\" for=\"star-5\" data-title='Excelente' style='cursor:pointer;'></label>");
                                out.print("<input class=\"star star-4\" id=\"star-4\" value='4' type=\"radio\" name=\"star\"/  required>");
                                out.print("<label class=\"star star-4\" for=\"star-4\" data-title='Bien' style='cursor:pointer;'></label>");
                                out.print("<input class=\"star star-3\" id=\"star-3\" value='3' type=\"radio\" name=\"star\"/  required>");
                                out.print("<label class=\"star star-3\" for=\"star-3\" data-title='Regular' style='cursor:pointer;'></label>");
                                out.print("<input class=\"star star-2\" id=\"star-2\" value='2' type=\"radio\" name=\"star\"/ required>");
                                out.print("<label class=\"star star-2\" for=\"star-2\" data-title='Malo' style='cursor:pointer;' ></label>");
                                out.print("<input class=\"star star-1\" id=\"star-1\" value='1' type=\"radio\" name=\"star\"/  required>");
                                out.print("<label class=\"star star-1\" for=\"star-1\" data-title='Pésimo' style='cursor:pointer;'></label>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div>");
                                out.print("<textarea class='form-control' name='opinion' placeholder='Ingresa tu opinion!' style='width: 450px;height: 54px;resize:none;'>Buen servicio.</textarea>");
                                out.print("</div>");
                                out.print("<div style='display: flex; width: 100%; margin-top: 5px; justify-content: space-between; margin-bottom: 5px;'>");
                                out.print("<input type='text' style='width: 49%;' class='form-control' name='txt_paradaE' id='' placeholder='Parada Equipo' required>");
                                out.print("<input type='text' style='width: 49%;' class='form-control' name='txt_paradaP' id='' placeholder='Parada Produccion' required>");
                                out.print("</div>");
                                out.print("<div class='sigPad signed' style='width:100%;height:247px;border: 1px solid black;margin-bottom: 10px;margin-top: 11px;border-radius: 5px;'>");
                                out.print("<div class='sigWrapper'>");
                                out.print("<div class='codigo' style='display:block'>" + obj_firma[2] + "</div>");
                                out.print("<canvas class='pad' width='440px' height='250px'></canvas>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<script>");
                                out.print("$(document).ready(function () {");
                                out.print("$('.sigPad').signaturePad(");
                                out.print("{");
                                out.print("displayOnly:true,");
                                out.print("penColour : '#292929',");
                                out.print("scale : [1,1]");
                                out.print("}");
                                out.print(").regenerate(" + obj_firma[3] + ");");
                                out.print("});");
                                out.print("</script>");
                                if (obj_firma[3] != null) {
                                    out.print("<input type='hidden' name='id_firma' value='" + obj_firma[0] + "'>");
                                    out.print("<input type='hidden' name='txt_documento' value='" + obj_firma[1] + "'>");
                                    out.print("<input type='hidden' name='id_reg' value='" + id_reg + "'>");
                                    out.print("<div style='height: 30px;'>");
                                    out.print("<button class='btn_regAc' style='float: right;height: 30px;'>Firmar</button>");
                                    out.print("</div>");
                                }
                                out.print("</form>");
                            } else {
                                out.print("<center><h4><b style='color:orange;'>El usuario No tiene firma registrada</b></h4><center>");
                            }
                        } else {
//                            out.print("<center><h4><b style='color:red;'>El usuario no se encuentra registrado</b></h4><center>");
                            //<editor-fold defaultstate="collapsed" desc="generar firma">
//                            lst_firma = jpa_caso.Traer_firmas(documento, codigo);
//                            Object[] obj_firma = (Object[]) lst_firma.get(0);
                            out.print("<div>");
                            out.print("<form action='Registro_001?opc=4&fto=2' name='' id='' method='post' style='margin:0px;'>");
                            out.print("<center>");
                            out.print("<div style='display: flex;justify-content: space-evenly;'>");
                            out.print("<input type='hidden' name='mes' value='" + mes + "'>");
                            out.print("<input type='hidden' name='anio' value='" + anio + "'>");
                            out.print("<input type='hidden' name='id_reg' value='" + id_reg + "'>");
                            out.print("<input type='text' class='form-control' name='txt_documento' id='codigo-id' placeholder='Documento' style='width:80px;margin: 0px;height: 30px;width: 49%;border-radius: 6px;' required>&nbsp;&nbsp;&nbsp;");
                            out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigo != 0) ? codigo : "") + "' placeholder='Codigo' style='width:80px;margin: 0px;height: 30px;width: 49%;border-radius: 6px;' required>&nbsp;&nbsp;&nbsp;");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</center>");
                            out.print("<input type='hidden' name='txt_mes' value='" + mes + "'>");
                            out.print("<input type='hidden' name='txt_anio' value='" + anio + "'>");
                            out.print("<div class='sigPad' id='smoothed' style='width:100%;'>");
                            out.print("<ul class='sigNav' style='display: block;'>");
                            out.print("<li class='clearButton' style='display: list-item;'><a href='#clear'>Borrar</a></li>");
                            out.print("</ul>");
                            out.print("<div class='sig sigWrapper current' style='height: auto; display: block;'>");
                            out.print("<div class='codigo' style='display: block;'>0</div>");
                            out.print("<canvas class='pad' width=440' height='250'></canvas>");
                            out.print("<input type='hidden' name='txt_firma' class='output' value='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<script>");
                            out.print("$(document).ready(function () {");
                            out.print("$('#smoothed').signaturePad(");
                            out.print("{");
                            out.print("drawOnly: true,");
                            out.print("drawBezierCurves:true,");
                            out.print("lineTop: 200,");
                            out.print("bgColour : 'transparent',");
                            out.print("penColour : '#292929'");
                            out.print("}");
                            out.print(")});");
                            out.print("</script>");
                            out.print("<div style='height: 30px; margin-top:10px'>");
                            out.print("<button class='btn_regAc' style='float: right;height: 30px;'>Firmar</button>");
                            out.print("</div>");
                            out.print("</form>");
                        }
//</editor-fold>
                    } else {
                        out.print("<div>");
                        out.print("<form action='Registro_001?opc=1&fto=2' name='' id='' method='post' style='margin:0px;'>");
                        out.print("<center>");
                        out.print("<div style='display: flex;justify-content: space-evenly;'>");
                        out.print("<input type='hidden' name='mes' value='" + mes + "'>");
                        out.print("<input type='hidden' name='anio' value='" + anio + "'>");
                        out.print("<input type='hidden' name='id_regActividad' value='" + id_reg + "'>");
                        out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigo != 0) ? codigo : "") + "' placeholder='Codigo' style='width:80px;margin: 0px;height: 30px;width: 75%;border-radius: 6px;'  required>&nbsp;&nbsp;&nbsp;");
                        out.print("<button type='submit' class='btn_regAc' style='width:100px;height: 32px;'>Buscar <i class=\"fas fa-search\"></i></button>");
                        out.print("</div>");
                        out.print("</center>");
                        out.print("</form>");
                        out.print("</div>");
                    }

                    out.print("</div>");
                    out.print("</div>");

                    //</editor-fold>
                    //</editor-fold>
                } else if (fto == 3) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ACTIVIDAD">   
                    out.print("<script src='Interfaz/EditorHtml/htmlpopper.min.js' type='text/javascript'></script>");
                    out.print("<link href='Interfaz/EditorHtml/htmlbootstrap.min.css' rel='stylesheet' type='text/css'/>");
                    out.print("<script src='Interfaz/EditorHtml/htmlbootstrap.min.js' type='text/javascript'></script>");
                    out.print("<link href='Interfaz/EditorHtml/htmlsummernote-bs4.min.css' rel='stylesheet' type='text/css'/>");
                    out.print("<script src='Interfaz/EditorHtml/htmlsummernote-bs4.min.js' type='text/javascript'></script>");
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_actividad'>");
                    out.print("<div style='width: 100%; display: flex; justify-content: space-between;'>");
                    out.print("<h2>Registrar Actividad</h2>");
                    out.print("<button onclick='mostrarConvencion(1)' style='height: 1%;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<form action='Registro_001?opc=3' method='post'>");
                    out.print("<input type='hidden' name='txt_mes' value='" + mes + "'>");
                    out.print("<input type='hidden' name='txt_anio' value='" + anio + "'>");
                    out.print("<div>");
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR 1">                
                    out.print("<div class='container_1'>");
                    out.print("<div style=''>");
                    out.print("<b>Fecha/hora Solicitud</b><br>");
                    out.print("<input type='datetime-local' class='form-control' name='fch_solicitud' onclick='fechas(1)' id='fechas_1' placeholder='' value='' style='width: 196px;' required>");
                    out.print("</div>");
                    lst_consultaUsers = jpa_usuarios.Empleado_sirh_nombre_area_R();
                    out.print("<div class='selectt' style=''>");
                    out.print("<b>Funcionario y Area</b><br>");
                    out.print("<select class='form-control' data-live-search='true' name='txt_funcArea' style='width: 196px;'>");
                    out.print("<option>Seleccione Usuario...</option>");
                    for (int i = 0; i < lst_consultaUsers.size(); i++) {
                        String[] arg_personal = lst_consultaUsers.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                        out.print("<option>" + arg_personal[i].toString()
                                .replace("í", "i").replace("Í", "I")
                                .replace("Ó", "O").replace("ó", "o")
                                .replace("Á", "A").replace("á", "a")
                                .replace("É", "E").replace("é", "e")
                                .replace("Ú", "U").replace("ú", "u")
                                + "</option>");
//                        out.print("<option>" + arg_personal[i].toString() + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div style=''>");
                    lst_equipos = jpa_equipo.consultaEquipos();
                    lst_listaEquipos = jpa_listaEquipo.consultaListaDetalleVerificacionGeneral();
                    out.print("<b>Equipo: </b><input type='radio' name='tipo' id='txt_equipo' value='1' checked onclick='mostrarCampos(1)'>&nbsp;&nbsp;&nbsp;");
                    out.print("<b>Otro: </b><input type='radio' name='tipo' id='txt_otro' value='2' onclick='mostrarCampos(2)'><br>");
                    out.print("<div id='modal1' style=' display: block;'>");
                    out.print("<select class='form-control' name='txt_pc' placeholder='pc' value='' style='width: 196px;' data-live-search='true'>");
                    out.print("<option value='1'>N/A</option>");
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                        out.println("<option value='" + obj_equipos[0] + "'>" + obj_equipos[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div id='modal2' style='display: none;'>");
                    out.print("<select type='' class='form-control' name='txt_otro' placeholder='Otro' value='' style='width: 100%;' data-live-search='true'>");
                    out.print("<option value='' style='display:none'>Seleccione Equipo</option>");
                    for (int k = 0; k < lst_listaEquipos.size(); k++) {
                        Object[] obj_Listequipos = (Object[]) lst_listaEquipos.get(k);
                        out.println("<option value='" + obj_Listequipos[0] + "'>" + obj_Listequipos[3] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Tipo Soporte</b><br>");
                    lst_reg001 = jpa_reg_001.Consultar_soportes();
                    out.print("<select class='form-control' placeholder='Tipo Soporte' data-live-search='true' name='txt_tipoSop'>");
                    out.print("<option value='' style='display:none'>Seleccione tipo...</option>");
                    for (int i = 0; i < lst_reg001.size(); i++) {
                        Object[] obj_soport = (Object[]) lst_reg001.get(i);
                        out.print("<option value=" + obj_soport[0] + "> " + obj_soport[1] + " </option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR 2">                
                    out.print("<div style='display: flex; justify-content: space-evenly;'>");
                    out.print("<div>");
                    out.print("<b>Fecha/hora Ejecución</b><br>");
                    out.print("<input type='datetime-local' class='form-control' name='fch_ejecucion' onclick='fechas(2)' id='fechas_2' placeholder='' value='' style='width: 196px;' required>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Fecha/hora Solución </b><br>");
                    out.print("<input type='datetime-local' class='form-control' name='fch_solucion' onclick='fechas(3)' id='fechas_3' placeholder='' value='' style='width: 196px;' required>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Ejecuto</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_ejecutor' id='txt_ejecutor' value='" + nombre_apellidos + "' disabled style='width: 100%;'>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR 3">      
                    out.print("<div class='cont_regDia'>");
                    out.print("<textarea id='editor' style='background: white;' name='txt_act_sol' required>");
                    out.print("<div contenteditable='true'>");
                    out.print("<div contenteditable='false'>Actividad:</div>"
                            + "<div contenteditable='true'><p id=\"demo\">*</p></div>"
                            + "<hr>"
                            + "<div contenteditable='false'>Solucion:</div>"
                            + "<div contenteditable='true'><p>*</p></div>");
                    out.print("</div>");
                    out.print("</textarea>");
                    out.print("<script>");
                    out.print("$('#summernote2').summernote({");
                    out.print("placeholder: 'Por favor recargar la pagina!!',");
                    out.print("tabsize: 2,");
                    out.print("height: 100");
                    out.print("});");
                    out.print("</script>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="CONTENEDOR BOTON">            
                    out.print("<div class='cl_boton'>");
                    out.print("<input type='reset' class='btn_act' value='Limpiar'>&nbsp;");
                    out.print("<button type='submit' class='btn_act'>Crear</button>");
                    out.print("</div>");
                    //</editor-fold>        
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>              
                }
                //<editor-fold defaultstate="collapsed" desc="TABLA ESCONDIDAD EXPORTAR EXCEL Y PDF">
                out.print("<div id='table_exp' class='table_exp' style='display: none; background: white;'>");
                out.print("<div id='Imprimir8'>");
                out.print("<table class='tab_tab'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Fecha Reportante</th>");
                out.print("<th>Usuario Reportante</th>");
                out.print("<th>Fecha Ejecucion</th>");
                out.print("<th>Equipo PC</th>");
                out.print("<th>Equipo Otro</th>");
                out.print("<th>Actividad</th>");
                out.print("<th>Solucion</th>");
                out.print("<th>Ejecutor</th>");
                out.print("<th>Fecha Solucion</th>");
                out.print("<th>Parada PC</th>");
                out.print("<th>Parada Produccion</th>");
                out.print("<th>Tipo Soporte</th>");
                out.print("<th>Puntuacion</th>");
                out.print("<th>Opinion</th>");
                out.print("<th>Tipo Registro</th>");
                out.print("<th style='width: 10%;'>Firma de usuario</th>");
                out.print("<th>Area</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_reg001 = jpa_reg_001.Consultar_registro_meses(anio, mes);
                if (lst_reg001 != null) {
                    for (int i = 0; i < lst_reg001.size(); i++) {
                        out.print("<tr>");
                        Object[] obj_detalle = (Object[]) lst_reg001.get(i);
                        out.print("<td>" + obj_detalle[1] + "</td>");
                        out.print("<td>" + obj_detalle[2] + "</td>");
                        out.print("<td>" + obj_detalle[3] + "</td>");
                        out.print("<td>" + obj_detalle[5] + "</td>");
                        out.print("<td>" + obj_detalle[7] + "</td>");
                        out.print("<td>" + obj_detalle[8] + "</td>");
                        out.print("<td>" + obj_detalle[9] + "</td>");
                        out.print("<td>" + obj_detalle[10] + "</td>");
                        out.print("<td>" + obj_detalle[11] + "</td>");
                        out.print("<td>" + obj_detalle[12] + "</td>");
                        out.print("<td>" + obj_detalle[13] + "</td>");
                        int tipo = Integer.parseInt(obj_detalle[16].toString());
                        lst_equipos = jpa_reg_001.Consultar_soportes_id(tipo);
                        if (lst_equipos != null) {
                            Object[] obj_tp = (Object[]) lst_equipos.get(0);
                            out.print("<td>" + obj_tp[1] + "</td>");
                        } else {
                            out.print("<td>No aplica</td>");
                        }
                        out.print("<td align='center'>");
                        String strellas = "";
                        try {
                            if (obj_detalle[17] != null) {

                                String puntuacion = obj_detalle[17].toString();
                                if (puntuacion.equals("AR")) {
                                    out.print("N/A");
                                } else {
                                    int punt = Integer.parseInt(puntuacion);
                                    if (punt > 0) {
                                        for (int j = 0; j < punt; j++) {
                                            strellas += "☆";
                                        }
                                        out.print(strellas);
                                    } else {
                                        out.print("No<br> calificado");
                                    }
                                }
                            } else {
                                out.print("No<br> calificado");
                            }
                        } catch (Exception e) {
                            out.print("error");

                        }
                        out.print("</td>");
                        out.print("<td align='center'>" + ((obj_detalle[18] == null) ? "No <br> calificado" : obj_detalle[18].toString()) + "</td>");
                        int tp_valid = 0;
                        try {
                            tp_valid = Integer.parseInt(obj_detalle[15].toString());
                        } catch (Exception e) {
                            tp_valid = 0;
                        }

                        if (tp_valid == 1) {
                            out.print("<td align='center'><p class='tooltip_4'><span class='tooltiptext'>Actividad Reportada</span><span>Actividad <br> Reportada</span></p></td>");
                        } else if (tp_valid == 2) {
                            out.print("<td align='center'><p class='tooltip_4'><span class='tooltiptext'>Registro 001</span><span>Registro 001</span></p></td>");
                        } else if (tp_valid == 3) {
                            out.print("<td align='center'><p class='tooltip_4'><span class='tooltiptext'>Casos</span><span>Casos</span></p></td>");
                        }

                        out.print("<td>");
                        int cod_user = 0;
                        try {
                            cod_user = Integer.parseInt(obj_detalle[14].toString());
                        } catch (Exception e) {
                            cod_user = 0;
                        }
                        lst_firma = jpa_reg_001.Consultar_FirmasPor_Codigo(cod_user);
                        if (lst_firma != null) {
//                            //<editor-fold defaultstate="collapsed" desc="PAD FIRMAS">
//                            Object[] obj_firm = (Object[]) lst_firma.get(0);
//                            String pad_firmas = "<div style='display: block;width: 120%;'>"
//                                    + "<div class='sigPads" + i + " signed' style='width:100%;height: 40px;display: block;position: relative; margin-top: -10px; margin-bottom: -4px;'>"
//                                    + "<div class='sigWrapper'>"
//                                    + "<canvas class='pad' width='95px' height='60px'></canvas>"
//                                    + "</div>"
//                                    + "<div class='codigo' style='display:block; margin: 9px 0px 0px 90px; font-size: 18px;'>" + obj_firm[2] + "</div>"
//                                    + "</div>"
//                                    + "</div>"
//                                    + "<script>"
//                                    + "$(document).ready(function () {"
//                                    + "$('.sigPads" + i + "').signaturePad("
//                                    + "{"
//                                    + "displayOnly:true,"
//                                    + "penColour : '#000',"
//                                    + "scale : [0.25,0.25]"
//                                    + "}"
//                                    + ").regenerate(" + obj_firm[3].toString() + ");"
//                                    + "});"
//                                    + "</script>";
//                            //</editor-fold>
//                            out.print(pad_firmas);
                            out.print("" + cod_user + "");
                        } else {
                            out.print("" + cod_user + "");
                        }
                        out.print("</td>");
                        try {
                            int cod_u = Integer.parseInt(obj_detalle[20].toString());
                            lst_usuario = jpa_usuarios.consultar_Areas_Xcodido(cod_u);
                            if (lst_usuario != null && lst_usuario.size() > 0) {
                                String areaas_us = lst_usuario.toString();
                                areaas_us = areaas_us.replace("]", "").replace("[", "")
                                        .replace("Á", "A").replace("á", "a")
                                        .replace("É", "E").replace("é", "e")
                                        .replace("Í", "I").replace("í", "i")
                                        .replace("Ó", "O").replace("ó", "o")
                                        .replace("Ú", "U").replace("ú", "u");
                                out.print("<td>" + areaas_us + "</td>");
                            } else {
                                String[] area_us = obj_detalle[2].toString().split("-");
                                out.print("<td>" + area_us[2]
                                        .replace("Á", "A").replace("á", "a")
                                        .replace("É", "E").replace("é", "e")
                                        .replace("Í", "I").replace("í", "i")
                                        .replace("Ó", "O").replace("ó", "o")
                                        .replace("Ú", "U").replace("ú", "u") + "</td>");
                            }
                        } catch (Exception exception) {
                            try {
                                String[] area_us = obj_detalle[2].toString().split("-");
                                out.print("<td>" + area_us[2]
                                        .replace("Á", "A").replace("á", "a")
                                        .replace("É", "E").replace("é", "e")
                                        .replace("Í", "I").replace("í", "i")
                                        .replace("Ó", "O").replace("ó", "o")
                                        .replace("Ú", "U").replace("ú", "u") + "</td>");
                            } catch (Exception exception1) {
                                out.print("<td>" + cod_user + "</td>");
                            }
                        }
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='14'>No se han encontrado datos</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CABECERA">
                out.print("<div style='width: 100%; display: flex;justify-content: space-between; margin-bottom: 10px; vertical-align: middle;'>");
                out.print("<div style=''>");
                out.print("<a class='link_back' href='Registro_001?opc=1&anio=" + anio + "'><i class=\"fa fa-arrow-left fa-lg\"></i></a>");
                out.print("<h2 style='margin: 0;'>R-TI-001 | " + meses[mes] + "</h2><br>");
                // CUANDO VUELVA LA RESTRICCION EL REGISTRO 001 POR MES SE DEBE VOLVER A HABILITAR LAS CONDICIONES DE LA LINEA (759)
                // DESPUES SE DEBE CAMBIAR HABILITANDO LA CONDICION DE LA LINEA (772)
                // LUEGO SE DEBE IR A LA LINEA (954) DONDE SE ENCUENTRA LA OTRA VALDIDACION POR MES
                lst_permisos = PermisosJpa.Consultar_Permisos(1);
                int permiso = 0;
                if (lst_permisos != null) {
                    Object[] obj_permis = (Object[]) lst_permisos.get(0);
                    permiso = Integer.parseInt(obj_permis[3].toString());
                }
                if (permiso == 0 || mesActual == mes && anioActual == anio) {
                    out.print("<a href='Registro_001?opc=1&fto=3&anio=" + anio + "&mes=" + mes + "'><i class='fas fa-plus' style='color:#292929; margin-top: 10px;font-size: 26px; cursor: pointer;'></i></a>&nbsp;&nbsp;&nbsp;&nbsp;");
                }
//                out.print("<a href='#' onclick=\"tableToExcel('table_exp', 'W3C Example Table')\" title='Exportar a Excel / PDF' value=\"Export to Excel\"><i class='far fa-file-excel fa-lg' style='color:#292929; margin-top: 10px;'></i></a>&nbsp;&nbsp;</b>");
                out.print("<a href='#' onclick=\"ExportToExcel('xlsx')\" title='Exportar a Excel / PDF' value=\"Export to Excel\"><i class='far fa-file-excel fa-lg' style='color:#292929; margin-top: 10px;'></i></a>&nbsp;&nbsp;</b>");
                out.print("<a href='#' onclick='Imprimir(8);' title='PDF'><i class='fas fa-print fa-lg' style='color:#292929; margin-top: 10px;'></i></a>&nbsp;&nbsp;</b>");
                out.print("</div>");
                out.print("<div>");
                out.print("<div style='display:flex;'>");
                out.print("<div><i class='fas fa-search' style='font-size: 20px; margin:6px;'></i></div>"
                        + "<div><input type='search' class='buscador form-control' id='search-focus' placeholder='Buscar..' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("</div>");
                out.print("<div style='margin-top: 5%;margin-right: 12px;' class='botones2' id='cont_botones'>");
                if (permiso == 0 || mesActual == mes && anioActual == anio) {
                    out.print("<form action='Registro_001?opc=3&fto=1' method='post'>");
                    out.print("<input type='hidden' name='txt_mes' value='" + mes + "'>");
                    out.print("<input type='hidden' name='txt_anio' value='" + anio + "'>");
                    out.print("<input type='hidden' id='Txt_ids' name='id_regActividad'>");
                    out.print("<button class='btn_act1' id='btn_edit' style='margin-left: 10px;'>Editar <i class='fas fa-pen'></i></button>");
                    out.print("</form>");

                    out.print("<form action='Registro_001?opc=1&fto=2' method='post'>");
                    out.print("<input type='hidden' name='mes' value='" + mes + "'>");
                    out.print("<input type='hidden' name='anio' value='" + anio + "'>");
                    out.print("<input type='hidden' id='Txt_ids2' name='id_regActividad'>");
                    out.print("<button class='btn_act1' id='btn_firma'>Firmar <i class='fas fa-signature'></i></button>");
                    out.print("</form>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                lst_registro = jpa_registro.Consultar_registros_id(21);
                Object[] obj_registro = (Object[]) lst_registro.get(0);
                out.print("<div id='NavPosicion'></div>");
                out.print("<div class='table_reg01' id='Imprimir2'>");

                if (lst_reg001 != null) {
                    Object[] obj_reg = (Object[]) lst_reg001.get(0);
                    String datoFecha = obj_reg[19].toString().replace("-", "");
                    int fechaValor = Integer.parseInt(datoFecha);
                    if (fechaValor <= 20221101) {
                        out.print("" + obj_registro[5].toString().replace("VERSIÓN: 1", "VERSIÓN: 0") + " ");
                    } else {
                        out.print("" + obj_registro[5] + "");
                    }
                } else {
                    out.print("" + obj_registro[5] + "");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL">  
                out.print("<table class='table' id='tb_reg01'  style='margin-top: -1px;'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th colspan='2'>SOLICITUD</th>");
                out.print("<th colspan='1'>EJECUCION</th>");
                out.print("<th colspan='1'>ID</th>");
                out.print("<th colspan='1'>DESCRIPCION</th>");
                out.print("<th colspan='8'>SOLUCION</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<th rowspan='2' style='width: 78px;'>FECHA Y HORA</th>");
                out.print("<th rowspan='2'>FUNCIONARIO <br> Y AREA</th>");
                out.print("<th rowspan='2' style='width: 78px;'>FECHA Y HORA</th>");
                out.print("<th rowspan='2'>NO</th>");
                out.print("<th rowspan='2'>SE DETERMINA EL INCONVENIENTE O FALLA DEL EQUIPO</th>");
                out.print("<th rowspan='2'>SE DETERMINA LA SOLUCION O ACCION TOMADA</th>");
                out.print("<th rowspan='2'>EJECUTÓ</th>");
                out.print("<th rowspan='2' style='width: 78px;'>FECHA Y HORA</th>");
                if (lst_reg001 != null) {
                    Object[] obj_reg = (Object[]) lst_reg001.get(0);
                    String datoFecha = obj_reg[19].toString().replace("-", "");
                    int fechaValor = Integer.parseInt(datoFecha);
                    if (fechaValor <= 20221101) {
                        out.print("<th colspan='2'>PARADA PC?</th>");
                        out.print("<th rowspan='2'>TIEMPO PARADA EN min</th>");
                    } else {
                        out.print("<th colspan='2'>PARADA?</th>");
                    }
                } else {
                    out.print("<th colspan='2'>PARADA?</th>");
                }
                out.print("<th rowspan='2'>TIPO <br> SOPORTE</th>");
                out.print("<th rowspan='2'>TIPO <br> REGISTRO </th>");
                out.print("<th rowspan='2'>FIRMA RECIBIDO</th>");
                out.print("</tr>");
                out.print("<tr>");
                if (lst_reg001 != null) {
                    Object[] obj_reg = (Object[]) lst_reg001.get(0);
                    String datoFecha = obj_reg[19].toString().replace("-", "");
                    int fechaValor = Integer.parseInt(datoFecha);
                    if (fechaValor <= 20221101) {
                        out.print("<th>SI</th>");
                        out.print("<th>NO</th>");
                    } else {
                        out.print("<th><p class='tooltip_2'><span>EQ</span><span class='tooltiptext'>EQUIPOS</span></p></th>");
                        out.print("<th><p class='tooltip_2'><span>PR</span><span class='tooltiptext'>PRODUCCION</span></p></th>");
                    }
                } else {
                    out.print("<th><p class='tooltip_2'><span>EQ</span><span class='tooltiptext'>EQUIPOS</span></p></th>");
                    out.print("<th><p class='tooltip_2'><span>PR</span><span class='tooltiptext'>PRODUCCION</span></p></th>");
                }
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");

                if (lst_reg001 != null) {
                    for (int i = 0; i < lst_reg001.size(); i++) {
                        Object[] obj_reg = (Object[]) lst_reg001.get(i);
                        int valid = Integer.parseInt(obj_reg[15].toString());
                        out.print("<tr class='naranja' style='background-color: "+ ((valid == 1 ) ?  "blue;" : "blue;") +"'>");
                        if (obj_reg[1].toString().contains(String.valueOf(anio)) && obj_reg[1].toString().contains(String.valueOf(mes))) {
                            out.print("<tr class=''>");
                        } else {
                            out.print("<tr class='cont_filas' title='El registro no fue creado este mes'>");
                        }
//                    out.print("<td>"+ obj_reg[0] +"</td>");
                        out.print("<td align='center'>" + obj_reg[1] + "</td>");
                        out.print("<td align='center'>" + obj_reg[2] + "</td>");
                        out.print("<td align='center'>" + obj_reg[3] + "</td>");
                        if (obj_reg[5].toString().equals("N/A") || obj_reg[5].toString().equals("NA")) {
                            out.print("<td align='center'>" + obj_reg[7] + "</td>");
                        } else {
                            out.print("<td align='center'>" + obj_reg[5] + "</td>");
                        }
                        out.print("<td align='center'>" + obj_reg[8] + "</td>");
                        out.print("<td align='center'>" + obj_reg[9] + "</td>");
                        int punt = 0;
                        try {
                            punt = Integer.parseInt(obj_reg[17].toString());
                        } catch (Exception e) {
                            punt = 0;
                        }
                        String estrellas = "";
                        for (int j = 1; j <= punt; j++) {
                            estrellas += "<i class='fas fa-star' style='color: yellow;'></i>";
                        }
                        if (punt == 0 && obj_reg[18] == null || obj_reg[18] == null) {
                            out.print("<td align='center'><p class='tooltip_3'><span>" + obj_reg[10] + "</span><span class='tooltiptext'>Calificacion:<br>-Sin calificar-<br>Opinion:<br>-Sin opinion-</span></p></td>");
                        } else if (obj_reg[18].toString().equals("AR")) {
                            out.print("<td align='center'><p class='tooltip_3'><span>" + obj_reg[10] + "</span><span class='tooltiptext'>Actividad Reportada</span></p></td>");
                        } else {
                            out.print("<td align='center'><p class='tooltip_3'><span><i class='fas fa-star' title='Usuario calificado' style='color: #f1c00b;'></i><br>" + obj_reg[10] + "</span><span class='tooltiptext'>Calificacion:<br>" + estrellas + "<br>Opinion:<br>" + obj_reg[18] + "</span></p></td>");
                        }

                        out.print("<td align='center'>" + obj_reg[11] + "</td>");

                        if (lst_reg001 != null) {
                            String datoFecha = obj_reg[19].toString().replace("-", "");
                            int fechaValor = Integer.parseInt(datoFecha);
                            if (fechaValor <= 20221101) {
                                int cantidad = Integer.parseInt(obj_reg[12].toString()) + Integer.parseInt(obj_reg[13].toString());
                                if (cantidad > 0) {
                                    out.print("<td align='center'>X</td>");
                                    out.print("<td align='center'></td>");
                                } else {
                                    out.print("<td align='center'></td>");
                                    out.print("<td align='center'>X</td>");
                                }
                                out.print("<td align='center'> " + cantidad + " </td>");
                            } else {
                                if (obj_reg[12] != null) {
                                    out.print("<td align='center'>" + obj_reg[12] + " " + (((Integer) obj_reg[12] == 0) ? "" : "<br>MIN") + "</td>");
                                } else {
                                    out.print("<td align='center'> 0 </td>");
                                }
                                if (obj_reg[13] != null) {
                                    out.print("<td align='center'>" + obj_reg[13] + " " + (((Integer) obj_reg[13] == 0) ? "" : "<br>MIN") + " </td>");
                                } else {
                                    out.print("<td align='center'> 0 </td>");
                                }
                            }
                            int id_tp = Integer.parseInt(obj_reg[16].toString());
                            lst_listaEquipos = jpa_reg_001.Consultar_soportes_id(id_tp);
                            if (lst_listaEquipos != null) {
                                Object[] obj_tipo = (Object[]) lst_listaEquipos.get(0);
                                out.print("<td align='center'>" + obj_tipo[1] + "</td>");
                            } else {
                                out.print("<td align='center'>NO ESPECIFICADO</td>");
                            }
                        }
                        int tp_valid = 0;
                        try {
                            tp_valid = Integer.parseInt(obj_reg[15].toString());
                        } catch (Exception e) {
                            tp_valid = 0;
                        }

                        if (tp_valid == 1) {
                            out.print("<td align='center'><p class='tooltip_4'><span class='tooltiptext'>Actividad Reportada</span><span>Reportada</span></p></td>");
                        } else if (tp_valid == 2) {
                            out.print("<td align='center'><p class='tooltip_4'><span class='tooltiptext'>Registro 001</span><span>Registro 001</span></p></td>");
                        } else if (tp_valid == 3) {
                            out.print("<td align='center'><p class='tooltip_4'><span class='tooltiptext'>Casos</span><span>Casos</span></p></td>");
                        }
                        //<editor-fold defaultstate="collapsed" desc="VALIDACION POR MES">
                        lst_permisos = PermisosJpa.Consultar_Permisos(1);
                        int permisox = 0;
                        if (lst_permisos != null) {
                            Object[] obj_permis = (Object[]) lst_permisos.get(0);
                            permisox = Integer.parseInt(obj_permis[3].toString());
                        }

                        if (mesActual != mes || anioActual != anio && permisox == 0) {
//                            //Esta validacion de fecha se realiza el 15 de septiembre, de aca hacia atras todo tiene la firma en fisico y en adelante la posibilidad de agregar una firma digital
                            int id_firma = 0;
                            try {
                                id_firma = Integer.parseInt(obj_reg[14].toString());
                            } catch (Exception e) {
                                id_firma = 99999;
                            }
                            lst_firma = jpa_reg_001.Consultar_FirmasPor_Codigo(id_firma);
//                            lst_firma = jpa_consultas.Consultar_firmas(Integer.parseInt(obj_reg[14].toString()));
                            if (lst_firma == null) {
                                if (id_firma == 0) {
                                    out.print("<td align='center'><b style='color: green;'>Firmado en fisico</b></td>");
                                } else if (id_firma == 99999) {
                                    out.print("<td align='center'><b style='color: red;'>No firmado</b></td>");
                                } else if (Integer.parseInt(obj_reg[15].toString()) == 3) {
                                    out.print("<td align='center'><b style='color: orange;'>" + obj_reg[14].toString() + "</b></td>");
                                } else {
                                    out.print("<td align='center'><b style='color: orange;'>" + obj_reg[14].toString() + "</b></td>");
                                }
                            } else {
                                Object[] obj_firm = (Object[]) lst_firma.get(0);
                                out.print("<td align='center' style='width: 100px;'>");
                                out.print("<div style='display: block;'>");
                                out.print("<div class='sigPad" + i + " signed' style='width:100%;height: 60px;display: block;position: relative;'>");
                                out.print("<div class='sigWrapper'>");
                                out.print("<canvas class='pad' width='95px' height='60px'></canvas>");
                                out.print("</div>");
                                out.print("<div class='codigo' style='display:block;margin: 21px 0px 0px 49px;font-size: 18px;'>" + obj_firm[2] + "</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<script>");
                                out.print("$(document).ready(function () {");
                                out.print("$('.sigPad" + i + "').signaturePad(");
                                out.print("{");
                                out.print("displayOnly:true,");
                                out.print("penColour : '#292929',");
                                out.print("scale : [0.25,0.25]");
                                out.print("}");
                                out.print(").regenerate(" + obj_firm[3] + ");");
                                out.print("});");
                                out.print("</script>");
                                out.print("</td>");
                            }
                        } else {
//</editor-fold>
                            if (valid == 2) {
                                if (obj_reg[14].toString().equals("") || obj_reg[14] == null) {
                                    out.print("<td align='center'><input type='radio' name='id_registro' value='" + obj_reg[0] + "' onclick='EnviarDatos(this.value)'></td>");
                                } else {
                                    int id_firmaX = Integer.parseInt(obj_reg[14].toString());
                                    lst_firma = jpa_reg_001.Consultar_FirmasPor_Codigo(id_firmaX);
                                    if (lst_firma != null) {
                                        Object[] obj_firm = (Object[]) lst_firma.get(0);
                                        out.print("<td align='center' style='width: 100px;'>");
                                        out.print("<div style='display: block;'>");
                                        out.print("<div class='sigPad" + i + " signed' style='width:100%;height: 60px;display: block;position: relative;'>");
                                        out.print("<div class='sigWrapper'>");
                                        out.print("<canvas class='pad' width='95px' height='60px'></canvas>");
                                        out.print("</div>");
                                        out.print("<div class='codigo' style='display:block;margin: 21px 0px 0px 49px;font-size: 18px;'>" + obj_firm[2] + "</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("<script>");
                                        out.print("$(document).ready(function () {");
                                        out.print("$('.sigPad" + i + "').signaturePad(");
                                        out.print("{");
                                        out.print("displayOnly:true,");
                                        out.print("penColour : '#292929',");
                                        out.print("scale : [0.25,0.25]");
                                        out.print("}");
                                        out.print(").regenerate(" + obj_firm[3] + ");");
                                        out.print("});");
                                        out.print("</script>");
                                        out.print("</td>");
                                    } else {
                                        if (id_firmaX > 0) {
                                            out.print("<td align='center'><b style='color: orange;'>" + id_firmaX + "</b></td>");
                                        } else {
                                            out.print("<td align='center'><b>Campo sin firma</b></td>");
                                        }
                                    }
                                }
                            } else {
                                int id_firmaX = Integer.parseInt(obj_reg[14].toString());
                                lst_firma = jpa_reg_001.Consultar_FirmasPor_Codigo(id_firmaX);
                                if (lst_firma == null) {
                                    out.print("<td align='center'><b style='color: orange;'>" + obj_reg[14].toString() + "</b></td>");
                                } else {
                                    Object[] obj_firm = (Object[]) lst_firma.get(0);
                                    out.print("<td align='center' style='width: 100px;'>");
                                    out.print("<div style='display: block;'>");
                                    out.print("<div class='sigPad" + i + " signed' style='width:100%;height: 60px;display: block;position: relative;'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='95px' height='60px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigo' style='display:block;margin: 21px 0px 0px 49px;font-size: 18px;'>" + obj_firm[2] + "</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('.sigPad" + i + "').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firm[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                    out.print("</td>");
                                }
                            }
                        }
                    }
                    out.print("</tr>");
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='12' align='center'>No se han encontrado datos <i class=\"fas fa-exclamation-circle\"></i></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('tb_reg01',7);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_Registro_001.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
