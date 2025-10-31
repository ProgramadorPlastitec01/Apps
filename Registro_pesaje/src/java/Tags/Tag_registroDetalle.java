package Tags;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.TiempoDescontableJpaController;
import Controladores.DefectoJpaController;
import Controladores.RegistroJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.OrdenJpaController;
import Controladores.ParametrosJpaController;
import SQL.Conexion_Factory;
import SQL.Connection_mysql_sirh;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpSession;

import Controladores.MaquinaJpaController;

public class Tag_registroDetalle extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        int IdUserAct = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String NombreRol = sesion.getAttribute("NombreRol").toString();
        RegistroDetalleJpaController RegistroDetalleJpa = new RegistroDetalleJpaController();
        RegistroJpaController RegistroJpa = new RegistroJpaController();
        OrdenJpaController OrdenJpa = new OrdenJpaController();
        TiempoDescontableJpaController TiempoJpa = new TiempoDescontableJpaController();
        ParametrosJpaController ParametroJpa = new ParametrosJpaController();
        DefectoJpaController DefectoJpa = new DefectoJpaController();
        Connection_mysql_sirh ConsultSirh = new Connection_mysql_sirh();
        Conexion_Factory sqlcft = new Conexion_Factory();
        MaquinaJpaController MaquinaJpa = new MaquinaJpaController();
        List lst_RegistroDell = null;
        List lst_Registro = null;
        List lst_consultarSirh = null;
        List lst_orden = null;
        List lst_tiempo = null;
        List lst_parametros = null;
        List lst_maquina = null;
        List lst_control = null;
        List lst_cuarentenas = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(cal.getTime());
        String fecha = format1.format(cal.getTime());
        String DataClass = "";
        String ClassLink = "";
        String ClassTab = "";
        //<editor-fold defaultstate="collapsed" desc="VARIABLES">
        String Txt_orden = "123456", tiempo = "", defecto = "", obs = "", NameChange = "", RolChange = "", RolValid = "";
        int id_reg = 0, id_dell = 0, id_temp = 0, id_orden = 0, id_hora = 0, id_cuarentenaID = 0, id_userChange = 0, idRolChange = 0, idUserValid = 0, tempH = 0,
                limpieza = 0;
        double peso = 0;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CAPTURAR DATOS">
        try {
            id_reg = Integer.parseInt(pageContext.getRequest().getAttribute("id_registro").toString());
        } catch (Exception e) {
            id_reg = 0;
        }
        try {
            id_dell = Integer.parseInt(pageContext.getRequest().getAttribute("id_regDetalle").toString());
        } catch (Exception e) {
            id_dell = 0;
        }
        try {
            id_temp = Integer.parseInt(pageContext.getRequest().getAttribute("id_temp").toString());
        } catch (Exception e) {
            id_temp = 0;
        }
        try {
            id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
        } catch (Exception e) {
            id_orden = 0;
        }
        try {
            id_hora = Integer.parseInt(pageContext.getRequest().getAttribute("id_hora").toString());
        } catch (Exception e) {
            id_hora = 0;
        }
        try {
            tiempo = pageContext.getRequest().getAttribute("tiempo").toString();
        } catch (Exception e) {
            tiempo = "0";
        }
        try {
            defecto = pageContext.getRequest().getAttribute("defecto").toString();
        } catch (Exception e) {
            defecto = "0";
        }
        try {
            obs = pageContext.getRequest().getAttribute("observacion").toString();
        } catch (Exception e) {
            obs = "0";
        }
        try {
            id_cuarentenaID = Integer.parseInt(pageContext.getRequest().getAttribute("id_cuarentena").toString());
        } catch (Exception e) {
            id_cuarentenaID = 0;
        }
        try {
            tempH = Integer.parseInt(pageContext.getRequest().getAttribute("tempH").toString());
        } catch (Exception e) {
            tempH = 0;
        }
        try {
            limpieza = Integer.parseInt(pageContext.getRequest().getAttribute("limpieza").toString());
        } catch (NumberFormatException e) {
            limpieza = 0;
        }
        try {
            id_userChange = Integer.parseInt(pageContext.getRequest().getAttribute("idUsuarioChng").toString());
            NameChange = pageContext.getRequest().getAttribute("NombresChng").toString();
            RolChange = pageContext.getRequest().getAttribute("NombreRolChng").toString();
            idRolChange = Integer.parseInt(pageContext.getRequest().getAttribute("idRolChng").toString());
        } catch (Exception e) {
            id_userChange = 0;
            NameChange = "";
            RolChange = "";
            idRolChange = 0;
        }

        //</editor-fold>
        try {
            if (id_hora > 0) {
                //<editor-fold defaultstate="collapsed" desc="VISUAL POR HORA">
                lst_RegistroDell = RegistroDetalleJpa.ConsultarDetalle_id(id_dell);
                if (lst_RegistroDell != null) {
                    Object[] obj_regDetll = (Object[]) lst_RegistroDell.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana10' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_hour' id='cont_hour'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3 style='text-align: center;'><b>Orden: </b> " + obj_regDetll[39] + "</h3>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(10)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");

                    out.print("<div style=' margin-top: 15px;'><h5 style='margin-left: 4px;'><b>Operaria: </b>" + obj_regDetll[4] + "</h5></div>");
                    out.print("<div style='display: flex; width: 100%; justify-content: space-around;'>");
                    out.print("<div style='width: 66%;'>");
                    lst_maquina = MaquinaJpa.ConsultarMaquinas_id((Integer) obj_regDetll[41]);
                    if (lst_maquina != null) {
                        Object[] obj_maq = (Object[]) lst_maquina.get(0);
                        out.print("<h5><b>Maquina: </b>" + obj_maq[1] + " - " + obj_maq[4] + " - " + obj_maq[5] + "</h5>");
                    } else {
                        out.print("<h5>Maquina No Encontrada</h5>");
                    }
                    out.print("<h5><b>Bascula de turno: </b> " + obj_regDetll[44].toString().replace("_", " ").replace(".txt", "") + "</h5>");
                    out.print("</div>");
                    out.print("<div style='width: 30%;'>");
                    out.print("<h5><b>Lote: </b> " + obj_regDetll[40] + "</h5>");
                    String vrTara = obj_regDetll[35].toString();
//                    out.print("<h5><b>Grupo: </b> " + obj_regDetll[3] + "</h5>");
                    out.print("<h5><b>Hora: </b> " + id_hora + "</h5>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("</div>");
                    out.print("<form action='Registro_detalle?opc=7&id_reg=" + id_reg + "&id_orden=" + id_orden + "&id_dell=" + id_dell + "' method='post' id='form_hora1'>");
                    out.print("<input type='hidden' name='id_hora' value='" + id_hora + "'>");
                    out.print("<input type='hidden' name='peso_meta' value='" + Double.valueOf(obj_regDetll[34].toString()) + "'>");
                    out.print("<input type='hidden' name='peso_a' value='" + Double.valueOf(obj_regDetll[36].toString()) + "'>");
                    out.print("<input type='hidden' name='tara' value='" + vrTara + "'>");
                    out.print("<input type='hidden' name='peso' id='peso_force' placeholder='' value=''>");
                    out.print("</form>");
                    out.print("<form action='Registro_detalle?opc=3&id_reg=" + id_reg + "&id_orden=" + id_orden + "&id_dell=" + id_dell + "' method='post' id='form_hora'>");
                    out.print("<input type='hidden' name='id_hora' value='" + id_hora + "'>");
                    //<editor-fold defaultstate="collapsed" desc="VARIABLES">

                    double Peso_maquina = 0, Peso_meta = 0, Peso_nominal = 0, Peso_undEmpa = 0, pesoDescontable = 0;
                    int undEmpa = 0;
                    try {
                        Peso_maquina = Double.valueOf(obj_regDetll[36].toString());
                    } catch (Exception e) {
                        Peso_maquina = 0;
                    }
                    try {
                        Peso_meta = Double.valueOf(obj_regDetll[34].toString());
                    } catch (Exception e) {
                        Peso_meta = 0;
                    }
                    try {
                        Peso_undEmpa = Double.valueOf(obj_regDetll[37].toString());
                    } catch (Exception e) {
                        Peso_undEmpa = 0;
                    }
                    try {
                        undEmpa = Integer.parseInt(obj_regDetll[38].toString());
                    } catch (Exception e) {
                        undEmpa = 0;
                    }
                    try {
                        peso = Double.parseDouble(pageContext.getRequest().getAttribute("id_registro").toString());
                    } catch (Exception e) {
                        peso = 0;
                    }
                    //</editor-fold>
                    int prueba = 0;
                    out.print("<div>");
                    out.print("<div style='display: flex; justify-content: space-evenly; margin-top:20px;'>");
                    out.print("<h5><b><p>Cant. por bolsa: </b><span style='color: #fa5e0f;'>" + undEmpa + " Und.</span></p></h5>");
                    out.print("<h5><b><p>Peso por bolsa: </b><span style='color: #fa5e0f;'>" + Peso_undEmpa + "g</span></p></h5>");
                    out.print("</div>");
                    out.print("<div style='width:100%;'>");

//                    ALERTA BOLSA COMPLETA
                    out.print("<div id='bolsa_alert' style='text-align:center; font-weight:bold; font-size:35px; color:red; display: none;'>");
                    out.print("<span>¡¡Se ha completado una bolsa!!</span>");
                    out.print("</div>");

//                    PESO DE LA BASCULA
                    out.print("<div style='display: flex;justify-content: center;'><input type='text' name='peso' id='fileContent' value='' style='border: none;font-size: 80px;width: 62%;color: #00a500;text-align: center;'> <span style='font-size: 80px; color: #00a500;'>g</span></div>");

//                    CAMPOS ESCONDIDOS
                    out.print("<input type='hidden' class='form-control' name='' id='selectBascula' value='" + obj_regDetll[44].toString().replace(".txt", "") + "' onchange='ReadData()'>");
                    out.print("<input type='hidden' class='form-control' name='' id='Peso_undEmpa' value='" + Peso_undEmpa + "'>");
                    out.print("<input type='hidden' class='form-control' name='' id='Peso_nominal' value='" + vrTara + "'>");
                    out.print("<input type='hidden' class='form-control' name='' id='undEmpa' value='" + undEmpa + "'>");
                    out.print("<input type='hidden' class='form-control' name='' id='PesoMaq' value='" + Peso_maquina + "'>");
                    out.print("<input type='hidden' class='form-control' name='' id='PesoMeta' value='" + Peso_meta + "'>");
//                    out.print("<input type='hidden' class='form-control' name='' id='unds' value='" + vrTara[1].toString() + "'>");

                    out.print("<div style='margin-left: 1%;margin-top:3%; justify-content: space-between; display: flex;padding: 0px 80px 0px 80px'>");
                    out.print("<b><p class='mostrador'>Bolsas completas:<br> <input type='text' id='calculo'></p></b>");
                    out.print("<b><p class='mostrador2'>Unidades aprox:<br> <input type='text' id='calculo2'>&nbsp;Und.</p></b>");
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='text-align: center; heigth: 50px; margin-top: 20px;'>");
                    out.print("<div id=\"container1\"></div>");
                    out.print("<div style='justify-content: space-evenly;display: flex; '>");
                    out.print("<button type=\"submit\" class=\"btn btn-" + ((tempH > 0) ? "info" : "warning") + "\" style='width: 135px; color: white;'>" + ((tempH > 0) ? "Modificar" : "Finalizar") + "  </button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            } else if (tiempo.equals("1")) {
                //<editor-fold defaultstate="collapsed" desc="VISUALIZAR CONTADOR DE TIEMPO">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana11' style='opacity: 1.03; display:block;'>");
//                out.print("<div class='cont_reg' style='width: 18%; height: 59%;margin-top: 12%; margin-left:78%;'>");
                out.print("<div class='cont_reg' style='width: 25%; height: auto;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Descontar Tiempo</h4>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(11)'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                lst_tiempo = TiempoJpa.Consultar_tiempoDescontable_id(id_dell);
                Object[] obj_desc = (Object[]) lst_tiempo.get(0);
                if (obj_desc[1] == null) {
                    //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRAR TIEMPOS">
                    out.print("<form action='Registro_detalle?opc=4' method='post' >");
                    out.print("<input type='hidden' name='id_dell' value='" + id_dell + "'>");
                    out.print("<input type='hidden' name='id_reg' value='" + id_reg + "'>");
                    out.print("<input type='hidden' name='id_orden' value='" + id_orden + "'>");
                    out.print("<div class='cont_reg_tiempo'>");
                    lst_tiempo = TiempoJpa.ConsultarTiempoDescontable();
                    String arr = "";
                    String arr2 = "";
                    if (lst_tiempo != null) {
                        out.print("<div class='cont_time'>");
                        for (int i = 0; i < lst_tiempo.size(); i++) {
                            Object[] obj_time = (Object[]) lst_tiempo.get(i);
                            out.print("<div class='cont_times'>");
                            out.print("<b>" + obj_time[1] + "</b>");
                            out.print("<input type='hidden' name='' id='id_time_" + i + "' value='" + obj_time[1] + "'><br>");
                            out.print("<div style='display: flex;' class='time_input'>");
                            out.print("<input type='number' class='form-control' name='time_" + i + "' id='time_" + i + "' placeholder='Cant.' value='" + obj_time[2] + "' onblur='pasarDatos_tiempo(" + i + ")'>");
                            out.print("<button type='button' title='Cantidad en minutos'>Min.</button></div>");
                            out.print("</div>");
                            arr += "[" + obj_time[2] + "/" + obj_time[1] + "]";
                            arr2 += "[" + obj_time[2] + "/" + obj_time[1] + "]";
                        }
                        out.print("</div>");
                    } else {

                    }
                    out.print("<input type='hidden' name='txt_tiempo' id='arm' value='" + arr2 + "'>");
                    out.print("<input type='hidden' name='txt_tiempot' id='arm2' value='" + arr + "'>");
                    out.print("<div style='text-align: center; margin-top: 5%;'>");
                    out.print("<div id=\"container1\"></div>");
                    out.print("<button type=\"submit\"  class=\"btn btn-primary\"> Registrar Tiempo </button>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="FORMULARIO EDITAR TIEMPOS ">
                    out.print("<form action='Registro_detalle?opc=4&time=1' method='post' >");
                    out.print("<input type='hidden' name='id_dell' value='" + id_dell + "'>");
                    out.print("<input type='hidden' name='id_reg' value='" + id_reg + "'>");
                    out.print("<input type='hidden' name='id_orden' value='" + id_orden + "'>");
                    out.print("<div class='cont_reg_tiempo'>");
                    lst_tiempo = TiempoJpa.ConsultarTiempoDescontable();
                    out.print("<div class='cont_time'>");
                    String arr = "";
                    String[] tiempos = obj_desc[1].toString().replace("][", "]-[").split("-");
                    for (int i = 0; i < lst_tiempo.size(); i++) {
                        try {
                            String[] cant = tiempos[i].toString().split("/");
                            Object[] obj_time = (Object[]) lst_tiempo.get(i);
                            out.print("<div class='cont_times'>");
                            out.print("<b>" + obj_time[1] + "</b>");
                            out.print("<input type='hidden' name='' id='id_time_" + i + "' value='" + obj_time[1] + "'><br>");
                            out.print("<div style='display: flex;' class='time_input'><input type='number' class='form-control' name='time_" + i + "' id='time_" + i + "' placeholder='Cant.' value='" + cant[0].toString().replace("[", "") + "' onblur='pasarDatos_tiempo(" + i + ")'>");
                            out.print("<button type='button' title='Cantidad en minutos'>Min.</button></div>");
                            out.print("</div>");
                            arr += "[" + cant[0].toString().replace("[", "") + "/" + obj_time[1] + "]";
                        } catch (Exception e) {
                            Object[] obj_time = (Object[]) lst_tiempo.get(i);
                            out.print("<div class='cont_times'>");
                            out.print("<b>" + obj_time[1] + "</b>");
                            out.print("<input type='text' name='' id='id_time_" + i + "' value='" + obj_time[1] + "'><br>");
                            out.print("<div style='display: flex;' class='time_input'><input type='number' class='form-control' name='time_" + i + "' id='time_" + i + "' placeholder='Cant.' value='0' onblur='pasarDatos_tiempo(" + i + ")'>");
                            out.print("<button type='button' title='Cantidad en minutos'>Min.</button></div>");
                            out.print("</div>");
                            arr += "[" + "0" + "/" + obj_time[1] + "]";
                        }
                    }
                    out.print("</div>");
                    out.print("<input type='hidden' name='txt_tiempo' id='arm' value=''>");
                    out.print("<input type='hidden' name='txt_tiempot' id='arm2' value='" + arr + "'>");
                    out.print("<div style='text-align: center; margin-top: 5%;'>");
                    out.print("<div id=\"container1\"></div>");
                    out.print("<button type=\"submit\"  class=\"btn btn-primary\"> Editar Tiempo </button>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (defecto.equals("1")) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRO Y CONTROL DE CUARENTENAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana13' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='margin-top: 2%;width: 76%; height: 93%;\n"
                        + "    overflow-y: scroll;'>");
                lst_control = RegistroDetalleJpa.ConsultarControlesCuarentena(id_reg);
                if (lst_control == null || lst_control.size() == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE CONTROL">
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Nuevo control de cuarentena</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(13)'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_defc'>");
                    out.print("<form action='Registro_detalle?opc=8&id_orden=" + id_orden + "&id_reg=" + id_reg + "' method='post'>");
                    out.print("<div class=''>");
                    out.print("<div class='col-lg-4' style=' margin:auto; margin-bottom: 20px;'>");
                    out.print("<b class=''>Turno</b>");
                    out.print("<select class='form-control' name='txtTurno'>");
                    out.print("<option value=''>Seleccione turno</option>");
                    out.print("<option value='1'>Turno 1</option>");
                    out.print("<option value='2'>Turno 2</option>");
                    out.print("<option value='3'>Turno 3</option>");
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div class='d-flex' style='justify-content: center; margin-auto; margin-left: 11%;'>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<b class=''>Base</b>");
                    out.print("<div class='col-lg-10'>");
                    out.print("<input type='text' class='form-control' name='txtBaseC' id='' style='margin-right: 20px;' placeholder='C:' required><br>");
                    out.print("<input type='text' class='form-control' name='txtBaseP' id='' style='margin-right: 20px;' placeholder='P:' required>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<b class=''>Piston</b>");
                    out.print("<div class='col-lg-10'>");
                    out.print("<input type='text' class='form-control' name='txtPistonC' id='' style='margin-right: 20px;' placeholder='C:' required><br>");
                    out.print("<input type='text' class='form-control' name='txtPistonP' id='' placeholder='P:' required>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='' style='margin-top: 20px; text-align: center;'>");
                    out.print("<button class='btn btn-primary'>Registrar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="GESTION DE CUARENTENAS">
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Controles por turno</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(13)'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_defc'>");
                    out.print("<div class='cuaCont'>");
                    for (int i = 0; i < lst_control.size(); i++) {
                        Object[] objTab = (Object[]) lst_control.get(i);
                        out.print("<button class='cuaConLink " + ((i == lst_control.size() - 1) ? "active" : "") + "' onclick=\"openCont(event, 'cuaCont" + i + "')\">Turno " + objTab[2] + "</button>");
                    }
                    if (lst_control.size() < 3) {
                        out.print("<button class='cuaConLink' onclick=\"openCont(event, 'cuaCont4')\"><i class='fas fa-plus'></i></button>");
                    }
                    out.print("</div>");
                    for (int i = 0; i < lst_control.size(); i++) {
                        //<editor-fold defaultstate="collapsed" desc="CONTROLES">
                        Object[] objTab = (Object[]) lst_control.get(i);
                        out.print("<div id='cuaCont" + i + "' class='cuanContContent' " + ((i == lst_control.size() - 1) ? "style='display: block;'" : "style='display: none;'") + " >");
                        String BaseC = objTab[3].toString().replace("[", "").replace("]", "").split("/")[0];
                        String BaseP = objTab[3].toString().replace("[", "").replace("]", "").split("/")[1];
                        String PistonC = objTab[4].toString().replace("[", "").replace("]", "").split("/")[0];
                        String PistonP = objTab[4].toString().replace("[", "").replace("]", "").split("/")[1];
                        out.print("<div class='col-lg-12' style='text-align: center;display: flex;justify-content: center;margin-top: 2%;margin-bottom: 2%;'>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<b>Base: C </b><span class='Totales'>" + BaseC + "</span><b> P </b><span class='Totales'>" + BaseP + "</span>");
                        out.print("</div>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<b>Piston: C </b><span class='Totales'>" + PistonC + "</span><b> P </b><span class='Totales'>" + PistonP + "</span>");
                        out.print("</div>");
                        out.print("</div>");
                        int id_contr = Integer.parseInt(objTab[0].toString());
                        lst_parametros = ParametroJpa.Consultar_categorias("ClasesTurnos");
                        if (lst_parametros != null) {
                            Object[] objClass = (Object[]) lst_parametros.get(0);
                            DataClass = objClass[2].toString();
                        }
                        String[] PartClass = DataClass.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        lst_cuarentenas = RegistroDetalleJpa.ConsultarCuarentenasXcontrol(id_contr);
                        if (lst_cuarentenas != null && lst_cuarentenas.size() > 0) {
                            //<editor-fold defaultstate="collapsed" desc="CUARENTENAS">
                            out.print("<div class='tab'>");
                            out.print("<ul class='tabmenu'>");
                            out.print("<li class=\"" + PartClass[i].toString().split("/")[0] + "\" onclick=\"" + PartClass[i].toString().split("/")[2] + "(event, '" + PartClass[i].toString().split("/")[3] + "99')\"><i class='fas fa-plus'></i></li>");
                            for (int j = 0; j < lst_cuarentenas.size(); j++) {
                                Object[] objCua = (Object[]) lst_cuarentenas.get(j);
                                ClassLink = PartClass[i].toString().split("/")[0];
                                int CuaEstado = Integer.parseInt(objCua[7].toString());
                                if (CuaEstado == 0) {
                                    out.print("<div class='d-flex'><div style='margin-right:7%'><i id='icoCand' class='fas fa-lock'></i></div><div><li class=\"" + ClassLink + " " + ((j == 0) ? "active" : "") + "\" onclick=\"" + PartClass[i].toString().split("/")[2] + "(event, '" + PartClass[i].toString().split("/")[3] + "" + j + "')\"> " + objCua[2] + "</li></div></div>");
                                } else {
                                    out.print("</i><li class=\"" + ClassLink + " " + ((j == 0) ? "active" : "") + "\" onclick=\"" + PartClass[i].toString().split("/")[2] + "(event, '" + PartClass[i].toString().split("/")[3] + "" + j + "')\"> " + objCua[2] + "</li>");
                                }
                            }
                            out.print("</ul>");
                            for (int j = 0; j < lst_cuarentenas.size(); j++) {
                                Object[] objCua = (Object[]) lst_cuarentenas.get(j);
                                ClassTab = PartClass[i].toString().split("/")[1];
                                int CuaEstado = Integer.parseInt(objCua[7].toString());
                                out.print("<div class=\"" + ClassTab + "\" id='" + PartClass[i].toString().split("/")[3] + "" + j + "' " + ((j == 0) ? "style='display: block;'" : "style='display: none;'") + "  >");
                                out.print("<form action='Registro_detalle?opc=5&edit=1&id_cuarent=" + objCua[0] + "' method='post'>");
                                out.print("<input type='hidden' name='id_dell' value='" + id_dell + "'>");
                                out.print("<input type='hidden' name='id_reg' value='" + id_reg + "'>");
                                out.print("<input type='hidden' name='id_orden' value='" + id_orden + "'>");
                                if (CuaEstado == 0) {
                                    out.print("<span class='Totales' style='margin-left: 9%;'>Cuarentena Finalizada <i class='fas fa-lock'></i></span>");
                                }
                                out.print("<div style='display: flex; width: 100%; justify-content: space-around; margin-top: 10px; margin-left: 8%;'>");
                                //<editor-fold defaultstate="collapsed" desc="MENSAJE EN CASO DE ERROR">
//                                El funcionamiento de esta seccion del modulo es la siguiente
//                                        1. se recorre la cantidad de turnos que hay
//                                        2. por cada turno se recorren las cuarentenas        
//                                        3. por cada cuarentena se despliega el modulo de defectos (registro o edicion)        
//                                        4. se debio construir un javascript para cada una de las seccion de las cuarentenas es decir que cada cuarentena tiene un javascriot unico
//                                                los javascript se nombraron en base al ID de la cuarentena (obj_defc[0]) y el iterador en este caso la letra m
//                                        5. cada uno realiza validacion enviado los datos modiifcados a un input escondido llamado "arm" que tambien lleva acompañado con el ID de la cuarentena (obj_defc[0])
//                                                eje. "arm2"
//                                        6. posteriormente se compararan los dos inputs escondidos que se envian al servlet
//                                        PD. Ojala no hayan errores :c
                                //</editor-fold>
                                lst_RegistroDell = RegistroDetalleJpa.Consultar_defectos_activos();
                                //<editor-fold defaultstate="collapsed" desc="LISTA DEFECTOS IZQUIERDA">
                                out.print("<div style='width: 48%;'>");
                                String arr = "";
                                String[] defectos = objCua[5].toString().replace("][", "]-[").split("-");
                                for (int m = 0; m < 10; m++) {
                                    try {
                                        String[] cant = defectos[m].toString().split("/");
                                        Object[] obj_defc = (Object[]) lst_RegistroDell.get(m);
                                        out.print("<div class='defecto' name=''>");
                                        out.print("<b>" + obj_defc[2] + "</b>");
                                        out.print("<input type='hidden' name='' id='id_def" + objCua[0] + "_" + m + "' placeholder='' value='" + obj_defc[2] + "'><br>");
                                        out.print("<input type='number' style='width: 40%;' class='form-control' min='0' oninput='validarInput(this)' name='cant_" + m + "' id='cant" + objCua[0] + "_" + m + "' placeholder='Cant.' value='" + cant[0].toString().replace("[", "") + "' onblur='pasarDatos" + objCua[0] + "(" + m + ")' " + ((CuaEstado == 0) ? "disabled" : "") + ">");
                                        out.print("</div>");
                                        arr += "[" + cant[0].toString().replace("[", "") + "/" + obj_defc[2] + "]";
                                    } catch (Exception e) {
                                        Object[] obj_defc = (Object[]) lst_RegistroDell.get(m);
                                        out.print("<div class='defecto' name=''>");
                                        out.print("<b>" + obj_defc[2] + "</b>");
                                        out.print("<input type='hidden' name='' id='id_def" + objCua[0] + "_" + m + "' placeholder='' value='" + obj_defc[2] + "'><br>");
                                        out.print("<input type='number' style='width: 40%;' class='form-control' name='cant_" + m + "' id='cant" + objCua[0] + "_" + m + "' placeholder='Cant.' value='" + 0 + "' onblur='pasarDatos" + objCua[0] + "(" + m + ")' " + ((CuaEstado == 0) ? "disabled" : "") + ">");
                                        out.print("</div>");
                                        arr += "[" + 0 + "/" + obj_defc[2] + "]";
                                    }
                                }
                                out.print("</div>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="LISTA DEFECTOS DERECHA">
                                out.print("<div style='width: 48%;'>");
                                int m = 0;
                                for (m = 10; m < lst_RegistroDell.size(); m++) {
                                    try {
                                        String[] cant = defectos[m].toString().split("/");
                                        Object[] obj_defc = (Object[]) lst_RegistroDell.get(m);
                                        out.print("<div class='defecto' name=''>");
                                        out.print("<b>" + obj_defc[2] + "</b>");
                                        out.print("<input type='hidden' name='' id='id_def" + objCua[0] + "_" + m + "' placeholder='' value='" + obj_defc[2] + "'><br>");
                                        out.print("<input type='number' style='width: 40%;' class='form-control' name='cant_" + m + "' id='cant" + objCua[0] + "_" + m + "' placeholder='Cant.' value='" + cant[0].toString().replace("[", "") + "' onblur='pasarDatos" + objCua[0] + "(" + m + ")' " + ((CuaEstado == 0) ? "disabled" : "") + ">");
                                        out.print("</div>");
                                        arr += "[" + cant[0].toString().replace("[", "") + "/" + obj_defc[2] + "]";
                                    } catch (Exception e) {
                                        Object[] obj_defc = (Object[]) lst_RegistroDell.get(i);
                                        out.print("<div class='defecto' name=''>");
                                        out.print("<b>" + obj_defc[2] + "</b>");
                                        out.print("<input type='hidden' name='' id='id_def" + objCua[0] + "_" + m + "' placeholder='' value='" + obj_defc[2] + "'><br>");
                                        out.print("<input type='number' style='width: 40%;' class='form-control' name='cant_" + m + "' id='cant" + objCua[0] + "_" + m + "' placeholder='Cant.' value='" + 0 + "' onblur='pasarDatos" + objCua[0] + "(" + m + ")' " + ((CuaEstado == 0) ? "disabled" : "") + ">");
                                        out.print("</div>");
                                        arr += "[" + 0 + "/" + obj_defc[2] + "]";
                                    }
                                }
                                out.print("</div>");
                                //</editor-fold>
                                out.print("</div>");
                                String DefectoN = "";
                                int DefectoMayor = 0;
                                int TotalDefect = 0;
                                String[] DataConteo = objCua[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                for (int k = 0; k < DataConteo.length; k++) {
                                    String[] DateDefecto = DataConteo[k].split("/");
                                    int DefectoC = Integer.parseInt(DateDefecto[0].toString());
                                    TotalDefect = TotalDefect + DefectoC;
                                    if (DefectoC > DefectoMayor) {
                                        DefectoMayor = DefectoC;
                                        DefectoN = DateDefecto[1].toString();
                                    }
                                }
                                if (DefectoMayor == 0) {
                                    DefectoN = "Sin defectos registrados";
                                }
                                out.print("<div class='col-lg-12' style='margin-left: 8%;margin-top: 2%;margin-bottom: 3%'>");
                                out.print("<div class='ContToal'>");
                                out.print("<div class='col-lg-4'>");
                                out.print("<b> Total defectos: </b><span class='Totales'>" + TotalDefect + "</span>");
                                out.print("</div>");
                                out.print("<div class='col-lg-8'>");
                                out.print("<b> Defecto con mayor cantidad: </b><span class='Totales'>" + DefectoN + " - " + DefectoMayor + "</span>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='hidden' name='txt_defecto' id='arm" + objCua[0] + "' placeholder='' value=''>");
                                out.print("<input type='hidden' name='txt_defectot' id='armt' placeholder='' value='" + arr + "'>");
                                out.print("<div style='text-align: center;width: 100%;margin: 10px;margin-left: 8%;'>");
                                if (CuaEstado == 1) {
                                    out.print("<button class='btn btn-primary' >Editar Defectos</button>");
                                    out.print("<button type='button' class='btn btn-info' onclick='CloseForm(" + objCua[0] + ")' style='float: right;'>Cerrar cuarentena &nbsp;<i class='fas fa-lock'></i></button>");
                                } else {
                                    out.print("<button class='btn btn-primary' disabled>Editar Defectos</button>");
                                    out.print("<button type='button' class='btn btn-info' onclick='CloseForm(" + objCua[0] + ")' style='float: right;'>Ver detalles &nbsp; <i class='fas fa-info-circle'></i></button>");
                                }
                                out.print("</div>");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("<form action='Registro_detalle?opc=1' method='post' id='FormCierre" + objCua[0] + "'>");
                                out.print("<input type='hidden' class='form-control' name='id_orden' value='" + id_orden + "' >");
                                out.print("<input type='hidden' class='form-control' name='id_registro' value='" + id_reg + "' >");
                                out.print("<input type='hidden' class='form-control' name='id_contrl' value='" + id_contr + "' >");
                                out.print("<input type='hidden' class='form-control' name='id_cuarentena' value='" + objCua[0] + "' >");
                                out.print("</form>");

                                out.print("<script>");
                                out.print("function pasarDatos" + objCua[0] + "(id) {"
                                        + "  var ids = document.getElementById('cant" + objCua[0] + "_' + id).value;"
                                        + "  var decf = document.getElementById('id_def" + objCua[0] + "_' + id ).value;"
                                        + "  document.getElementById('arm" + objCua[0] + "').value += \"[\"+ ids +\"/\"+ decf +\"]\";"
                                        + "  document.getElementById('cant" + objCua[0] + "_' + id ).className = \"borderdefc\";"
                                        + "}");
                                out.print("</script>");
                            }
                            String arr2 = "";
                            lst_RegistroDell = RegistroDetalleJpa.Consultar_defectos_activos();
                            if (lst_RegistroDell != null) {
                                for (int j = 0; j < lst_RegistroDell.size(); j++) {
                                    Object[] obj_defc = (Object[]) lst_RegistroDell.get(j);
                                    arr2 += "[" + 0 + "/" + obj_defc[2] + "]";
                                }
                            }
                            out.print("<div class='" + ClassTab + "' id='" + PartClass[i].toString().split("/")[3] + "99' style='display: none;width: 100%;margin-left: 8%;'>");
                            out.print("<form action='Registro_detalle?opc=9' method='post'>");
                            out.print("<input type='hidden' class='form-control' name='id_orden' value='" + id_orden + "'>");
                            out.print("<input type='hidden' class='form-control' name='id_reg' value='" + id_reg + "'>");
                            out.print("<input type='hidden' class='form-control' name='id_contrl' value='" + objTab[0] + "'>");
                            out.print("<div style='display: flex;justify-content:space-evenly;align-items: center;'>");
                            out.print("<div class=''>");
                            out.print("<b class=''>Numero de cuarentena</b>");
                            out.print("<input type='text' class='form-control' name='NumCuarent' placeholder='Numero de cuarentena' title='Numero de cuarentena' style='width: 300px;' required>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<b class=''>Unidades en cuarentena</b>");
                            out.print("<input type='text' class='form-control' name='UndCuarent' placeholder='Unidades en cuarentena' title='Unidades en cuarentena' required>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<button class='btn btn-primary'>Registrar</button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='txtDefectos' value='" + arr2 + "' >");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="REGISTRO CUARENTENAS - LISTA VACIA">
                            String arr2 = "";
                            lst_RegistroDell = RegistroDetalleJpa.Consultar_defectos_activos();
                            if (lst_RegistroDell != null) {
                                for (int j = 0; j < lst_RegistroDell.size(); j++) {
                                    Object[] obj_defc = (Object[]) lst_RegistroDell.get(j);
                                    arr2 += "[" + 0 + "/" + obj_defc[2] + "]";
                                }
                            }
                            out.print("<div class='tab'>");
                            out.print("<ul class='tabmenu'>");
                            out.print("<li class='Newlinks active' onclick='openNew(event, 'NewCua')'><i class='fas fa-plus'></i></li>");
                            out.print("</ul>");
                            out.print("<div class='Newcontent id='NewCua' style='width: 100%;margin-left: 8%;'>");
                            out.print("<form action='Registro_detalle?opc=9' method='post'>");
                            out.print("<input type='hidden' class='form-control' name='id_orden' value='" + id_orden + "'>");
                            out.print("<input type='hidden' class='form-control' name='id_reg' value='" + id_reg + "'>");
                            out.print("<input type='hidden' class='form-control' name='id_contrl' value='" + objTab[0] + "'>");
                            out.print("<div style='display: flex;justify-content:space-evenly; align-items: center;'>");
                            out.print("<div class=''>");
                            out.print("<b class=''>Numero de cuarentena</b>");
                            out.print("<input type='text' class='form-control' name='NumCuarent' id='myInput' placeholder='Numero de cuarentena' title='Numero de cuarentena' required>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<b class=''>Unidades en cuarentena</b>");
                            out.print("<input type='text' class='form-control' name='UndCuarent' placeholder='Unidades en cuarentena' title='Unidades en cuarentena' required>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<button class='btn btn-primary'>Registrar</button>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<input type='hidden' class='form-control' name='txtDefectos' value='" + arr2 + "' >");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        out.print("</div>");
                        //</editor-fold>
                    }
                    if (lst_control.size() < 3) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROLES">
                        out.print("<div id='cuaCont4' class='cuanContContent' style='display: none;'>");
                        out.print("<h3>Nuevo control de cuarentena</h3>");
                        out.print("<form action='Registro_detalle?opc=8&id_orden=" + id_orden + "&id_reg=" + id_reg + "' method='post'>");
                        out.print("<div class=''>");
                        out.print("<div class='col-lg-4' style=' margin:auto; margin-bottom: 20px;'>");
                        out.print("<b class=''>Turno</b>");
                        out.print("<select class='form-control' name='txtTurno'>");
                        out.print("<option value=''>Seleccione turno</option>");
                        out.print("<option value='1'>Turno 1</option>");
                        out.print("<option value='2'>Turno 2</option>");
                        out.print("<option value='3'>Turno 3</option>");
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class='d-flex' style='justify-content: center; margin: auto; margin-left:11%;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<b class=''>Base</b>");
                        out.print("<div class='col-lg-10'>");
                        out.print("<input type='text' class='form-control' name='txtBaseC' id='' style='margin-right: 20px;' placeholder='C:' required><br>");
                        out.print("<input type='text' class='form-control' name='txtBaseP' id='' style='margin-right: 20px;' placeholder='P:' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<b class=''>Piston</b>");
                        out.print("<div class='col-lg-10'>");
                        out.print("<input type='text' class='form-control' name='txtPistonC' id='' style='margin-right: 20px;' placeholder='C:' required><br>");
                        out.print("<input type='text' class='form-control' name='txtPistonP' id='' placeholder='P:' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='' style='margin-top: 20px; text-align: center;'>");
                        out.print("<button class='btn btn-primary'>Registrar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (id_cuarentenaID > 0) {
                //<editor-fold defaultstate="collapsed" desc="CIERRE DE CUARENTENAS">
                int idControl = Integer.parseInt(pageContext.getRequest().getAttribute("id_control").toString());
                lst_RegistroDell = RegistroDetalleJpa.ConsultarControlxCuarentena(idControl, id_cuarentenaID);
                Object[] obj_cua = (Object[]) lst_RegistroDell.get(0);
                int contadorFirmas = 0;
                int stateCua = Integer.parseInt(obj_cua[7].toString());
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana15' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 60%; height: auto; margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between;height: 35px;margin-bottom: 16px;'>");
                out.print("<button class='btn_clsRg' style='width: 29px;' onclick='window.location.href=\"Registro_detalle?opc=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "&defecto=1\"'><i class='fas fa-reply'></i></button>");
                out.print("<h2>Finalizar Cuarentena</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(15)'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_obs' style=''>");
                //<editor-fold defaultstate="collapsed" desc="DATOS DE CONTROLES / CUARENTENAS">

                out.print("<div class='d-flex col-lg-12'>");
                out.print("<div class='col-lg-5'>");
                out.print("<div class='contControl col-lg-10'>");
                out.print("<div class='Conttitle'>");
                out.print("<h4 style=''>Datos de control</h4>");
                out.print("</div>");

                out.print("<div class='d-flex' style='margin-top: 10px;'>");
                out.print("<div class='col-lg-4'>");
                out.print("<b>Turno</b><br>");
                out.print("<b>Base C</b><br>");
                out.print("<b>Base P</b><br>");
                out.print("<b>Piston C</b><br>");
                out.print("<b>Piston P</b><br>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<b class='Textbold'>" + obj_cua[9] + "</b><br>");
                out.print("<b class='Textbold'>" + obj_cua[10].toString().replace("[", "").replace("]", "").split("/")[0] + "</b><br>");
                out.print("<b class='Textbold'>" + obj_cua[10].toString().replace("[", "").replace("]", "").split("/")[1] + "</b><br>");
                out.print("<b class='Textbold'>" + obj_cua[11].toString().replace("[", "").replace("]", "").split("/")[0] + "</b><br>");
                out.print("<b class='Textbold'>" + obj_cua[11].toString().replace("[", "").replace("]", "").split("/")[1] + "</b>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-7'>");
//                out.print("<h4 style='margin-top: 20px;'>Datos de cuarentena</h4>");
                String DefectoN = "";
                int DefectoMayor = 0;
                int TotalDefect = 0;
                String[] DataConteo = obj_cua[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                for (int k = 0; k < DataConteo.length; k++) {
                    String[] DateDefecto = DataConteo[k].split("/");
                    int DefectoC = Integer.parseInt(DateDefecto[0].toString());
                    TotalDefect = TotalDefect + DefectoC;
                    if (DefectoC > DefectoMayor) {
                        DefectoMayor = DefectoC;
                        DefectoN = DateDefecto[1].toString();
                    }
                }

                out.print("<div class='contControl col-lg-10'>");

                out.print("<div class='Conttitle'>");
                out.print("<h4>Datos de cuarentena</h4>");
                out.print("</div>");

                out.print("<div class='d-flex' style='margin-top: 10px;'>");

                out.print("<div class='col-lg-6'>");
                out.print("<b>Numero de cuarentena</b><br>");
                out.print("<b>Unidades de cuarentena</b><br>");
                out.print("<b>Defecto por cuarentena</b><br>");
                out.print("<b>Total defectos</b><br>");
                out.print("<b>Total aprobados</b><br>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<b class='Textbold'>" + obj_cua[2] + "</b><br>");
                out.print("<b class='Textbold'>" + obj_cua[3] + "</b><br>");
                out.print("<b class='Textbold'>" + DefectoN + " - " + DefectoMayor + "</b><br>");
                out.print("<b class='Textbold'>" + TotalDefect + "</b><br>");
                int totalAprob = (Integer) obj_cua[3] - TotalDefect;
                out.print("<b class='Textbold'>" + totalAprob + "</b><br>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="CAMBIO DE USUARIOS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_signature' style='margin-left: 33%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Cambio de usuario</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (id_userChange > 0) {
                    out.print("<div style='margin-bottom:15px;'><b>Usuario actual:</b> " + NameChange + "</div>");
                } else {
                    out.print("<div style='margin-bottom:15px;'><b>Usuario actual:</b> " + rol_usuario + "</div>");
                }
                out.print("<form action='Registro_detalle?opc=10' method='post'>");
                out.print("<input type='hidden' name='id_orden' value='" + id_orden + "' />");
                out.print("<input type='hidden' name='id_registro' value='" + id_reg + "' />");
                out.print("<input type='hidden' name='id_contrl' value='" + idControl + "' />");
                out.print("<input type='hidden' name='id_cuarent' value='" + id_cuarentenaID + "' />");
//                out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                out.print("<div class='form-group'>");
                out.print("<div class='input-group'>");
                out.print("<div class='input-group-prepend'>");
                out.print("<div class='input-group-text'  style='height: 100%;'>");
                out.print("<i class='fas fa-user'></i>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='text' class='form-control' name='Txt_user' id='Txt_user' placeholder='Usuario' autocomplete='off'>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='form-group'>");
                out.print("<div class='input-group'>");
                out.print("<div class='input-group-prepend'>");
                out.print("<div class='input-group-text' style='height: 100%;'>");
                out.print("<i class='fas fa-key'></i>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='password' class='form-control' name='Txt_password' id='txtPassword' placeholder='Contraseña' autocomplete='off'>");
                out.print("<div class='input-group-text' onclick='mostrarPass()' id='show_password' style='cursor: pointer;'><i id='icon' class='fas fa-eye'></i></div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<button class='btn' style='box-shadow: 1px 2px 5px 0px #959595;margin-left:180px;'><i class='fas fa-arrow-right'></i></button>         ");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="FIRMAS">
                out.print("<div class='d-flex' style='justify-content: space-between; height: 40px;margin-top: 30px;align-items: center;'>");
                out.print("<div class='textFirmas'>");
                out.print("<h4>Firmas</h4>");
                out.print("</div>");
                out.print("<div class='d-flex' style='align-items: center;'>");
                out.print("<div class='' style='margin-right: 27px;'>");
                if (id_userChange > 0) {
                    out.print("<b>Rol/Usuario actual: </b><span class='Textbold' style='color: #f33155;'>&nbsp; " + RolChange + "/" + NameChange + "</span>");
                    RolValid = RolChange;
                    idUserValid = id_userChange;
                } else {
                    out.print("<b>Rol/Usuario actual: </b><span class='Textbold' style='color: #f33155;'>&nbsp; " + rol_usuario + "</span>");
                    RolValid = rol_usuario;
                    idUserValid = IdUserAct;
                }
                out.print("</div>");
                if (stateCua == 1) {
                    out.print("<button class='btn btn-white' onclick='mostrarConvencion(5)'><i class='fas fa-users'></i></button>");
                } else {
                    out.print("<button class='btn btn-white' disabled><i class='fas fa-users'></i></button>");
                }
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='d-flex col-lg-12' style='justify-content: space-evenly;'>");
                lst_RegistroDell = RegistroDetalleJpa.ConsultarFirmasxRevision(id_cuarentenaID);
                if (lst_RegistroDell != null) {
                    Object[] Objfirma = (Object[]) lst_RegistroDell.get(0);
                    out.print("<div class='col-lg-3' style='text-align: center;'>");
                    if (Objfirma[2] == null) {
                        if (RolValid.contains("Encargada") || RolValid.contains("Administrador")) {
                            out.print("<b>Firma</b><br>");
                            out.print("<div class='SingatureSquareAct' onclick='EjecuteFirma(" + id_orden + "," + id_reg + "," + idControl + ", " + id_cuarentenaID + ", " + idUserValid + ",1)'>");
                            out.print("<p class='SingatureText'>Firmar</p>");
                            out.print("</div>");
                        } else {
                            out.print("<b>Firma</b><br>");
                            out.print("<div class='SingatureSquare'>");
                            out.print("<p class='SingatureText'>Firmar</p>");
                            out.print("</div>");
                        }
                    } else {
                        out.print("<b></b><br>");
                        out.print("<div class='SingatureSquareFin'>");
                        out.print("<p class='SingatureTextFin'>" + Objfirma[2].toString().split("/")[0] + "<br>" + Objfirma[2].toString().split("/")[1] + " <br><span style='color: green;'>Firmado &nbsp; <i style='color: green;' class=\"fas fa-check\"></i></span></p>");
                        out.print("</div>");
                        contadorFirmas++;
                    }
                    out.print("<b class='Textbold'>Responsable Revision</b>");
                    out.print("</div>");
                }

                lst_RegistroDell = RegistroDetalleJpa.ConsultarFirmasxInspectora(id_cuarentenaID);
                if (lst_RegistroDell != null) {
                    Object[] Objfirma = (Object[]) lst_RegistroDell.get(0);
                    out.print("<div class='col-lg-3' style='text-align: center;'>");
                    if (Objfirma[2] == null) {
                        if (RolValid.contains("Inspectora Calidad") || RolValid.contains("Administrador")) {
                            out.print("<b>Firma</b><br>");
                            out.print("<div class='SingatureSquareActCal' onclick='EjecuteFirma(" + id_orden + "," + id_reg + "," + idControl + ", " + id_cuarentenaID + ", " + idUserValid + ",2)'>");
                            out.print("<p class='SingatureText'>Firmar</p>");
                            out.print("</div>");
                        } else {
                            out.print("<b>Firma</b><br>");
                            out.print("<div class='SingatureSquare'>");
                            out.print("<p class='SingatureText'>Firmar</p>");
                            out.print("</div>");
                        }
                    } else {
                        out.print("<b></b><br>");
                        out.print("<div class='SingatureSquareFin'>");
                        out.print("<p class='SingatureTextFin'><span style='color: #4094ff;'>" + Objfirma[2].toString().split("/")[0] + "<br>" + Objfirma[2].toString().split("/")[1] + "</span> <br><span style='color: green;'>Firmado &nbsp; <i style='color: green;' class=\"fas fa-check\"></i></span></p>");
                        out.print("</div>");
                        contadorFirmas++;
                    }
                    out.print("<b class='Textbold'>Inspectora Calidad</b>");
                    out.print("</div>");
                }

                lst_RegistroDell = RegistroDetalleJpa.ConsultarFirmasxCoordinadora(id_cuarentenaID);
                if (lst_RegistroDell != null) {
                    Object[] Objfirma = (Object[]) lst_RegistroDell.get(0);
                    out.print("<div class='col-lg-3' style='text-align: center;'>");
                    if (Objfirma[2] == null) {
                        if (RolValid.contains("Coordinadora") || RolValid.contains("Administrador")) {
                            out.print("<b>Firma</b><br>");
                            out.print("<div class='SingatureSquareAct' onclick='EjecuteFirma(" + id_orden + "," + id_reg + "," + idControl + ", " + id_cuarentenaID + ", " + idUserValid + ",3)'>");
                            out.print("<p class='SingatureText'>Firmar</p>");
                            out.print("</div>");
                        } else {
                            out.print("<b>Firma</b><br>");
                            out.print("<div class='SingatureSquare'>");
                            out.print("<p class='SingatureText'>Firmar</p>");
                            out.print("</div>");
                        }
                    } else {
                        out.print("<b></b><br>");
                        out.print("<div class='SingatureSquareFin'>");
                        out.print("<p class='SingatureTextFin'>" + Objfirma[2].toString().split("/")[0] + "<br>" + Objfirma[2].toString().split("/")[1] + " <br><span style='color: green;'>Firmado &nbsp; <i style='color: green;' class=\"fas fa-check\"></i></span></p>");
                        out.print("</div>");
                        contadorFirmas++;
                    }
                    out.print("<b class='Textbold'>Coordinadora Produccion</b>");
                    out.print("</div>");
                }

                out.print("</div>");
                if (stateCua == 1) {
                    if (contadorFirmas == 3) {
                        out.print("<div class='btnFirma'>");
                        out.print("<form action='Registro_detalle?opc=12' method='post'>");
                        out.print("<input type='hidden' class='form-control' name='id_orden' value='" + id_orden + "'>");
                        out.print("<input type='hidden' class='form-control' name='id_registro' value='" + id_reg + "'>");
                        out.print("<input type='hidden' class='form-control' name='id_contrl' value='" + idControl + "'>");
                        out.print("<input type='hidden' class='form-control' name='id_cuarent' value='" + id_cuarentenaID + "'>");
                        out.print("<input type='hidden' class='form-control' name='totalAprob' value='" + totalAprob + "'>");
                        out.print("<input type='hidden' class='form-control' name='defectCuaren' value='" + DefectoN + " - " + DefectoMayor + "'>");

                        out.print("<button class='btn btn-primary'>Finalizar</button>");
                        out.print("</form>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='btnFirma'>");
                        out.print("<button type='button' class='btn btn-primary' disabled>Firmas (" + contadorFirmas + "/3)</button>");
                        out.print("</div>");
                    }
                } else {
                    out.print("<div class='' style='margin-top: 20px;text-align: center;color: #f34155;'>");
                    out.print("<h4>Cuarentena finalizada &nbsp; <i class='fas fa-lock'></i></h4>");
                    out.print("</div>");
                }

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            } else if (obs.equals("1")) {
                //<editor-fold defaultstate="collapsed" desc="VISUALIZAR OBSERVACIONES">                
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana14' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 70%; height: auto; margin-top: 4%;'>");
                out.print("<div style='display: flex; justify-content: space-between;'>");
                out.print("<h2>Observaciones</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(14)'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_obs' style='display: flex; justify-content: space-around;'>");
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO OBSERVACIONES">
                lst_RegistroDell = RegistroDetalleJpa.Consultar_ObservacionesId(id_dell);
                Object[] obj_obs = (Object[]) lst_RegistroDell.get(0);
                out.print("<div style='width: 48%;'>");
                out.print("<form action='Registro_detalle?opc=6' method='post'>");
                out.print("<input type='hidden' name='id_dell' value='" + id_dell + "'>");
                out.print("<input type='hidden' name='id_reg' value='" + id_reg + "'>");
                out.print("<input type='hidden' name='id_orden' value='" + id_orden + "'>");
                out.print("<div class='obs_form'>");
                out.print("<div class='obs_form_selec'>");
                out.print("<select class='form-control' name='cbx_hora' id='cbx_hora'>");
                out.print("<option value='0'>Seleccione hora</option>");
                for (int i = 1; i < 9; i++) {
                    out.print("<option>" + i + "</option>");
                }
                out.print("</select><br>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('cbx_hora');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");
                out.print("<div class='obs_form_input'>");
                out.print("<input type='text' class='form-control' name='txt_motivo' id='txt_motivo' placeholder='Motivo' value=''><br>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('txt_motivo');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("<div>");
                out.print("<button type='reset' class='btn btn-outline-secondary' onclick='activar_div(this.value)' id='btn_gro'><i class='fas fa-eraser'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='obs_form_texa'>");
                out.print("<textarea class='form-control' name='txt_justifi' id='txt_justifi' placeholder='Ingresar justificación'></textarea><br>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('txt_justifi');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("<div style='width: 100%; text-align: center;'>");
                out.print("<input type='hidden' name='txt_comple' id='txt_comple' value='" + obj_obs[1] + "'>");
                out.print("<button class='btn btn-primary'>Confirmar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='width: 48%;'>");
                if (obj_obs[1] != null) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTAR OBSERVACIONES">
                    String[] observ = obj_obs[1].toString().split("--");
                    out.print("<h4>Observaciones Agregadas</h4>");
                    out.print("<div class='obs_group' id='obs_group'>");
                    for (int i = 0; i < observ.length; i++) {
                        String[] observaciones = observ[i].replace("][", "--").replace("[", "").replace("]", "").split("--");
                        out.print("<div class='obs_group2' id='obs_group_" + i + "'"
                                + "onclick='pasarObs(" + observaciones[0] + ", \"" + observaciones[1] + "\", \"" + observaciones[2] + "\", \"" + observ[i] + "\", " + i + ")'>");
                        out.print("<div class='obs_hora'>");
                        out.print("<p><b>Hora: </b>" + observaciones[0] + "</p>");
                        out.print("</div>");
                        out.print("<div style='display: flex;width: 16%;'>");
                        out.print("<p><b>Motivo: </b>" + observaciones[1] + "</p>");
                        out.print("</div>");
                        out.print("<div style='display: flex;width: 65%;'>");
                        out.print("<p><b>Justificación: </b>" + observaciones[2] + "</p>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    out.print("<h4>Observaciones Agregadas</h4>");
                    out.print("<p style='text-align: center; margin-top: 15%;'>No se ha registrado ninguna observación</p>");
                }
                out.print("</div>");

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (limpieza > 0) {
                //<editor-fold defaultstate="collapsed" desc="LIMPIEZA DE PESO">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana15' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_limpieza' id='cont_limpieza'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3 style='text-align: center;'><b>Limpieza Peso </b></h3>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(15)'><i class=\"fas fa-times\"></i></button>");
                out.print("</div>");

                lst_RegistroDell = RegistroDetalleJpa.ConsultarDetalle_id(id_dell);
                if (lst_RegistroDell != null) {
                    Object[] obj_regDetll = (Object[]) lst_RegistroDell.get(0);
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                        out.print("<div style=' margin-top: 15px;'><h5 style='margin-left: 4px;'><b>Operaria: </b>" + obj_regDetll[4] + "</h5></div>");
                        out.print("<div class=\"wrapper\">\n"
                                + " <input type=\"radio\" name=\"select\" id=\"option-1\" onclick='HabilitarDivLimpieza(1)' checked>\n"
                                + " <input type=\"radio\" name=\"select\" id=\"option-2\" onclick='HabilitarDivLimpieza(2)'>\n"
                                + "   <label for=\"option-1\" class=\"option option-1\">\n"
                                + "     <div class=\"dot\"></div>\n"
                                + "      <span>Hora</span>\n"
                                + "      </label>\n"
                                + "   <label for=\"option-2\" class=\"option option-2\">\n"
                                + "     <div class=\"dot\"></div>\n"
                                + "      <span>General</span>\n"
                                + "   </label>\n"
                                + "</div>");
                    }

                    out.print("<div id='LimHora' style='display:block;justify-content: center;'>");
//                    
                    out.print("<form action='Registro_detalle?opc=13&id_reg=" + id_reg + "&id_orden=" + id_orden + "&id_dell=" + id_dell + "' method='post' id='form_limpieza'>");
                    out.print("<input type='hidden' name='tipoLim' value='1'>");
                    out.print("<select class='form-control' name='Cbx_limpieza' id='Cbx_limpieza' placeholder='Seleccionar Hora'>");
                    out.print("<option value='0'>Selecccione hora</option>");
                    out.print("<option value='1'>Hora 1</option>");
                    out.print("<option value='2'>Hora 2</option>");
                    out.print("<option value='3'>Hora 3</option>");
                    out.print("<option value='4'>Hora 4</option>");
                    out.print("<option value='5'>Hora 5</option>");
                    out.print("<option value='6'>Hora 6</option>");
                    out.print("<option value='7'>Hora 7</option>");
                    out.print("<option value='8'>Hora 8</option>");
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_limpieza');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//
                    out.print("<div style='justify-content: space-evenly;display: flex; margin-top:20px; '>");
                    out.print("<button type=\"submit\" class='btn btn-warning' style='width: 135px; color: white;'>Limpiar</button>");
                    out.print("</div>");
//
                    out.print("</form>");
                    out.print("</div>");

//
                    out.print("<div id='LimGeneral' style='display:none'>");
//                    
                    out.print("<form action='Registro_detalle?opc=13&id_reg=" + id_reg + "&id_orden=" + id_orden + "&id_dell=" + id_dell + "' method='post' id='form_limpieza'>");
                    out.print("<input type='hidden' name='tipoLim' value='2'>");
                    out.print("<input type='hidden' name='Cbx_limpieza' value='1'>");

                    out.print("<div style='display: flex;justify-content: center;'>"
                            + "<b style='color:red'>¿Esta seguro de eliminar los pesos registrados de esta persona?</b>"
                            + "</div>");
//                    
                    out.print("<div style='display:flex;justify-content: space-evenly;align-items: baseline;'>"
                            + "<b>SI </b><input type='radio' name='validacion' value='1'>");
                    out.print("<b>NO </b><input type='radio' name='validacion' onclick='mostrarConvencion(15);' value='0'>"
                            + "</div>");
//                    
                    out.print("<div style='justify-content: space-evenly;display: flex; margin-top:20px; '>");
                    out.print("<button type=\"submit\" class='btn btn-warning' style='width: 135px; color: white;'>Limpiar</button>");
                    out.print("</div>");
//                    
                    out.print("</form>");
                    out.print("</div>");

                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="VISUALIZAR REGISTRO DETALLE">
                if (id_dell > 0 && id_reg > 0) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR REGISTRO DETALLE">
                    lst_RegistroDell = RegistroDetalleJpa.ConsultarDetalle_id(id_dell);
                    Object[] obj_editDetalle = (Object[]) lst_RegistroDell.get(0);
                    lst_Registro = RegistroJpa.ConsultarRegistroId(Integer.parseInt(obj_editDetalle[1].toString()));
                    Object[] obj_editReg = (Object[]) lst_Registro.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana16' style='opacity: 1.03; display: block;'>");
                    out.print("<div class='cont_reg' style='width: 44%; height: auto;margin-top: 4%;overflow-x: auto;max-height: 81%;''>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar Registro Detalle</h2>");
                    out.print("<a class='btn_clsRg' href='Registro_detalle?opc=1&id_registro=" + obj_editDetalle[1] + "&id_orden=" + id_orden + "'><i class='fas fa-times'></i></a>");
                    out.print("</div>");
                    out.print("<div class='cont_form_detll'>");
                    out.print("<form action='Registro_detalle?opc=2' method='post'>");
                    out.print("<h5>Numero de Orden: <b>" + obj_editReg[2] + "</b></h5>");
                    out.print("<div class='cont_dell'>");
                    out.print("<div class='cont_dell_2'>");
                    out.print("<input type='hidden' name='id_dell' id='id_dell' value='" + obj_editDetalle[0] + "'>");
                    out.print("<input type='hidden' name='id_reg' id='id_reg' value='" + obj_editDetalle[1] + "'>");
                    out.print("<input type='hidden' name='id_orden' id='id_orden' value='" + id_orden + "'>");
                    out.print("<b>Turno</b>");
                    out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' placeholder='Seleccionar turno'>");
                    lst_parametros = ParametroJpa.Consultar_categorias("Turnos");
                    out.print("<option>" + obj_editDetalle[2] + "</option>");
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                        }
                    }
                    out.print("</select> "
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
                    out.print("</div>");
                    out.print("<div class='cont_dell_2'>");
                    out.print("<div style='display: flex; height: 21px;'>");
                    out.print("<b>Bascula </b>");
                    out.print("<p class='tooltip5'><span style='margin-left: 5px;'><i class=\"fas fa-question-circle\"></i></span><span class='tooltiptext'>Se debe seleccionar el numero de la bascula donde se encuentre.</span></p>");
                    out.print("</div>");
                    lst_parametros = ParametroJpa.Consultar_categorias("Bascula");
                    out.print("<select class='form-control' name='Cbx_bascula' id='Cbx_bascula' placeholder='Seleccione Bascula'>");
                    if (obj_editDetalle[43] != null) {
                        out.print("<option value='" + obj_editDetalle[43] + "'>" + obj_editDetalle[44].toString().replace("_", " ").replace(".txt", "") + "</option>");
                    } else {
                        out.print("<option selected disabled>Seleccione una bascula</option>");
                    }
                    lst_parametros = ParametroJpa.Consultar_Basculas_Disponibles();
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_bas = (Object[]) lst_parametros.get(i);
                            if (obj_editDetalle[43] != null) {
                                if (obj_editDetalle[43] != obj_bas[0]) {
                                    out.print("<option value='" + obj_bas[0] + "'>" + obj_bas[2].toString().replace("_", " ").replace(".txt", "") + "</option>");
                                }
                            } else {
                                out.print("<option value='" + obj_bas[0] + "'>" + obj_bas[2].toString().replace("_", " ").replace(".txt", "") + "</option>");
                            }
                        }
                    } else {
                        out.print("<option>No se han encontrado basculas</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_bascula');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='cont_groups'>");
                    out.print("<div>");
                    out.print("<b>Encargada</b>");
                    out.print("<div style='display: flex;'>");
                    out.print("<div style='width: 100%;'>");
//                    out.print("<input type='text' class='form-control' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' placeholder='Ingresar codigo de encargada' list='Personal' value='" + obj_editDetalle[4].toString().replace("[", "").replace("]", "") + "'>"
//                            + "");
                    out.print("<select name='Txt_filtro_avanzado' id='selectPersonal2' class='form-control select2' required>");
                    lst_consultarSirh = ConsultSirh.Empleado_sirh();
                    if (lst_consultarSirh != null && lst_consultarSirh.size() > 0 && !lst_consultarSirh.isEmpty()) {
                        for (int i = 0; i < lst_consultarSirh.size(); i++) {
                            String[] Arg_personal = lst_consultarSirh.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                            if (obj_editDetalle[4].toString().equals(Arg_personal[i])) {
                                out.print("<option  selected value='[" + Arg_personal[i] + "]'>" + Arg_personal[i] + "</option>");
                            } else {
                                out.print("<option value='[" + Arg_personal[i] + "]'>" + Arg_personal[i] + "</option>");
                            }
                        }
                    } else {
                        out.print("<option value='Error'></option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('selectPersonal2');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div id='newRow' style='margin-bottom: 10px;'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='height: 40px; width: 97%;'>");
                    out.print("<button type='submit' id='btn_reg' class='btn btn-primary' style='float: right; margin-right: -8px;'> Modificar </button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                if (id_temp == 1) {
                    //<editor-fold defaultstate="collapsed" desc="AGREGAR REGISTRO DETALLE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana9' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='width: 45%; height: auto;margin-top: 4%; overflow-x: auto;max-height: 81%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Registro Detalle</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(9)'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_detll'>");
                    out.print("<form action='Registro_detalle?opc=2' method='post'>");
                    lst_orden = OrdenJpa.ConsultarNumeroOrden(id_orden);
                    if (lst_orden != null) {
                        Object[] obj_ord = (Object[]) lst_orden.get(0);
                        out.print("<h5>Numero de Orden:<b> " + obj_ord[1] + "</b></h5>");
                    } else {
                        out.print("<h5>Numero de Orden:<b> " + id_orden + "</b></h5>");
                    }
                    out.print("<div class='cont_dell'>");
                    out.print("<div class='cont_dell_2' style='width: 27%;'>");
                    out.print("<input type='hidden' name='id_reg' id='id_reg' value='" + id_reg + "'>");
                    out.print("<input type='hidden' name='id_orden' id='id_orden' value='" + id_orden + "'>");
                    out.print("<b>Turno</b>");
                    out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' placeholder='Seleccionar turno'>");
                    lst_parametros = ParametroJpa.Consultar_categorias("Turnos");
                    out.print("<option value='0'>Seleccionar Turno</option>");
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            out.print("<option value='" + obj_parametros[0] + "'>" + obj_parametros[2] + "</option>");
                        }
                    }
                    out.print("</select> "
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
                    out.print("</div>");
                    out.print("<div class='cont_dell_2' style='width:33%;'>");
                    out.print("<div style='display: flex; height: 21px;'>");
                    out.print("<b>Bascula </b>");
                    out.print("<p class='tooltip5'><span style='margin-left: 5px;'><i class=\"fas fa-question-circle\"></i></span><span class='tooltiptext'>Se debe seleccionar el numero de la bascula donde se encuentre.</span></p>");
                    out.print("</div>");
                    out.print("<select class='form-control' name='Cbx_bascula' id='Cbx_bascula' placeholder='Seleccione Bascula'>");
                    out.print("<option value='0'>Seleccione Bascula</option>");
                    lst_parametros = ParametroJpa.Consultar_Basculas_Disponibles();
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_bas = (Object[]) lst_parametros.get(i);
                            out.print("<option value='" + obj_bas[0] + "' >" + obj_bas[2].toString().replace("_", " ").replace(".txt", "") + "</option>");
                        }
                    } else {
                        out.print("<option>No se han encontrado basculas</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_bascula');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='cont_groups mt-2'>");
                    out.print("<div>");
                    out.print("<b>Operaria</b>");
                    out.print("<div style='display: flex;'>");
//                    out.print("<div style='width: 100%;'>");
//                    out.print("<input type='text' class='form-control' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' placeholder='Ingresar codigo de emplead@' list='Personal'>");
//                    out.print("</div>");
                    out.print("</div>");
                    out.print("<select  id='selectPersonal' class='form-control select2'  multiple='' required name='Txt_filtro_avanzado'>");
                    lst_consultarSirh = ConsultSirh.Empleado_sirh();
                    if (lst_consultarSirh != null && lst_consultarSirh.size() > 0 && !lst_consultarSirh.isEmpty()) {
                        for (int i = 0; i < lst_consultarSirh.size(); i++) {
                            String[] Arg_personal = lst_consultarSirh.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                            out.print("<option value='[" + Arg_personal[i] + "]'>" + Arg_personal[i] + "</option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('selectPersonal');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
                    //<editor-fold defaultstate="collapsed" desc="CODIGO ANTIGUO">

//                    out.print("<div id='Buscar_valores'></div>");
//                    out.print("<input type='hidden' name='fto' id='Txt_valores_filtro' oninput='javascript:this.value+=document.getElementById('Buscar_valores').innerHTML'/>");
//                    out.print("<datalist id='Personal'><label>"
//                            + "<select name='Personal'>");
//                    lst_consultarSirh = ConsultSirh.Empleado_sirh();
//                    if (lst_consultarSirh != null && lst_consultarSirh.size() > 0 && !lst_consultarSirh.isEmpty()) {
//                        for (int i = 0; i < lst_consultarSirh.size(); i++) {
//                            String[] Arg_personal = lst_consultarSirh.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
//                            out.print("<option value='" + Arg_personal[i] + "'></option>");
//                        }
//                    } else {
//                        out.print("<option value='Error'></option>");
//                    }
//                    out.print("</select></label></datalist>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div id='newRow' style='margin-bottom: 10px;'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='height: 40px; width: 97%;'>");
                    out.print("<button type='submit' id='btn_reg' class='btn btn-primary'> Registrar </button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="VISUALIZAR DATOS ORDEN">
            if (id_orden > 0) {
                lst_orden = OrdenJpa.ConsultarOrdenId(id_orden);
                if (lst_orden != null) {
                    //<editor-fold defaultstate="collapsed" desc="ORDEN">
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_orden' style='height: auto;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>" + obj_orden[3] + "</h4>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(12)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_orden'>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b># OP</b>");
                    out.print("<br><p>" + obj_orden[1] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Codigo</b>");
                    out.print("<br><p>" + obj_orden[2].toString() + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Fecha Inicio</b>");
                    out.print("<br><p>" + obj_orden[10] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Plan</b>");
                    out.print("<br><p>" + obj_orden[4] + "</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Centro Costo</b>");
                    out.print("<br><p>" + obj_orden[9] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Maquina</b>");
                    out.print("<br><p>" + obj_orden[17] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Molde</b>");
                    out.print("<br><p>" + obj_orden[18] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Peso x 1000 uds.</b>");
                    out.print("<br><p>" + obj_orden[19] + "g</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Cantidad Programada</b>");
                    out.print("<br><p>" + obj_orden[6] + "-" + obj_orden[8] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Peso Programado</b>");
                    out.print("<br><p>" + obj_orden[20] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Unidades de Empaque</b>");
                    out.print("<br><p>" + obj_orden[21] + " <b>un.</b></p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Peso x und. de Empaque</b>");
                    out.print("<br><p>" + obj_orden[22] + " <b>g.</b></p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Cant. Revisada</b>");
                    out.print("<br><p> " + obj_orden[7] + " -" + obj_orden[8] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Fecha Inicio Revision</b>");
                    out.print("<br><p>" + obj_orden[14] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Fecha Final Revision</b>");
                    out.print("<br><p>--</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Ficha Técnica</b>");
                    out.print("<br><p>" + (obj_orden[24].toString().contains("/") ? obj_orden[24].toString().split("/")[1] + " V" + obj_orden[24].toString().split("/")[2] : "Sin asociación") + "</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='margin-left:70%; height: 30px;'><button style='float: right;' type='button' class='btn btn-primary' onclick='mostrarConvencion(12)'> Cerrar <i class='fas fa-times'></i></button></div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 32%; height: 26%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Informacion de registro</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(8)'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_rdt'>");
                    out.print("<div>");
                    out.print("<p> No se ha podido cargar datos del registro<p>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<button style='float: right;' type='submit' class='btn btn-primary' onclick='mostrarConvencion(8)'> Cerrar <i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="VISUALIZAR DATOS DE REGISTRO">
            lst_Registro = RegistroJpa.ConsultarRegistroId(id_reg);
            if (lst_Registro.size() > 0) {
                Object[] obj_registro = (Object[]) lst_Registro.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='width: 48%; height: auto;'>");
                out.print("<div style='display: flex; justify-content: space-between;'>");
                out.print("<h2>Informacion de registro</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(8)'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_rdt'>");
                out.print("<div class='cont_regdell'>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Numero de orden</b>");
                out.print("<p>" + obj_registro[2] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Recipiente</b>");
                out.print("<p>" + obj_registro[4] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Peso recipiente</b>");
                out.print("<p>" + obj_registro[10] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Fecha dia</b>");
                out.print("<p>" + obj_registro[5] + "</p>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_regdell'>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Estiba</b>");
                out.print("<p>" + obj_registro[7] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Lote</b>");
                out.print("<p>" + obj_registro[6] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Bolsa</b>");
                out.print("<p>" + obj_registro[11] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in'>");
                out.print("<b>Peso bolsa</b>");
                out.print("<p>" + obj_registro[12] + "</p>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_regdell_in_2' style='text-align: center;'>");
                out.print("<b>Observaciones</b>");
                out.print("<p style='margin-top: 10px;overflow-y: auto; height: 80px; width: 100%; text-align: justify;'>" + obj_registro[8] + "</p>");
                out.print("</div>");
                out.print("<div class='cont_regdell'>");
                out.print("<div class='cont_regdell_in' style='width: 100%;'>");
                out.print("<button style='float: right;' type='submit' class='btn btn-primary' onclick='mostrarConvencion(8)'> Cerrar <i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            } else {
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='width: 32%; height: 26%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Informacion de registro</h2>");
                out.print("<button class='btn_clsRg' onclick='mostrarConvencion(8)'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_rdt'>");
                out.print("<div>");
                out.print("<p> No se ha podido cargar datos del registro<p>");
                out.print("</div>");
                out.print("<div>");
                out.print("<button style='float: right;' type='submit' class='btn btn-primary' onclick='mostrarConvencion(8)'> Cerrar <i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="SELECCIONAR BASCULA AL ABRIR DETALLE ">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana10' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 32%; height: 26%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Seleccionar Bascula!</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(10)'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div>");
            out.print("<form action='Registro_detalle?opc=1&est=1&temp=1' method='post'>");
            out.print("<input type='hidden' name='id_dell' id='id_dell_bas'>");
            out.print("<input type='hidden' name='id_orden' id='id_orden' value='" + id_orden + "'>");
            out.print("<input type='hidden' name='id_registro' id='id_registro' value='" + id_reg + "'>");
            out.print("<div class='cont_dell_2' style='width:100%;'>");
            out.print("<div style='display: flex; height: 21px;'>");
            out.print("<b>Bascula </b>");
            out.print("<p class='tooltip5'><span style='margin-left: 5px;'><i class=\"fas fa-question-circle\"></i></span><span class='tooltiptext'>Se debe seleccionar el numero de la bascula donde se encuentre.</span></p>");
            out.print("</div>");
            out.print("<select class='form-control' name='Cbx_bascula' id='Cbx_bascula' placeholder='Seleccione Bascula'>");
            out.print("<option value='0'>Seleccione Bascula</option>");
            lst_parametros = ParametroJpa.Consultar_Basculas_Disponibles();
            if (lst_parametros != null) {
                for (int i = 0; i < lst_parametros.size(); i++) {
                    Object[] obj_bas = (Object[]) lst_parametros.get(i);
                    out.print("<option value='" + obj_bas[0] + "' >" + obj_bas[2].toString().replace("_", " ").replace(".txt", "") + "</option>");
                }
            } else {
                out.print("<option>No se han encontrado basculas</option>");
            }
            out.print("</select>"
                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_bascula');"
                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: ''});</script>");
            out.print("</div>");
            out.print("<div style='text-align: center; margin-top: 10px;'>");
            out.print("<button type='submit' class='btn btn-primary'> Confirmar <i class=\"fas fa-check\"></i></button>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE TABLA - BUSCADOR - AGREGAR">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center' style='justify-content: space-between;'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'> Registro Detalle </h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px; float: right;'>");
            out.print("<div class='content_botones'>");
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                out.print("<a href='Registro_detalle?opc=1&rdll=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "'"
                        + "class='btn btn-danger d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Agregar un Registro'>Agregar <i class='fas fa-plus'></i></a>");
            }
            out.print("<button class='btn btn-dark' onclick='mostrarConvencion(12)' style='color: #fff; margin-right: 5px; margin-left: 5px; height:33px;' title='Ver datos de Orden '>Orden <i class='fas fa-book'></i></button>");
            out.print("<button class='btn btn-success' onclick='mostrarConvencion(8)' style='color: #fff; height:33px;background: #005c00; border: 1px solid #005c00;' title='Ver Datos de Registro'>Registro <i class='fas fa-file-alt'></i></button>");

            lst_Registro = RegistroJpa.ConsultarRegistroId(id_reg);
            if (lst_Registro.size() > 0) {
                Object[] obj_registro = (Object[]) lst_Registro.get(0);
                out.print("<form action='Reporte?opc=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "&turno=" + obj_registro[16] + "' method='post'>");
                out.print("<button class='btn btn-dark' onclick='' id='' style='color: #fff;background: #207aff; margin-left: 5px; height:33px; border: 1px solid #207aff;' title='Ingresar al Reporte'>Ver Reporte <i class=\"fas fa-paste\"></i></button>");
                out.print("</form>");
            }

            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada") || NombreRol.equals("Inspectora Calidad")) {
                out.print("<form action='Registro_detalle?opc=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "&defecto=1' method='post'>");
                out.print("<input type='hidden' name='id_dell' value='1'>");
                out.print("<button class='btn btn-dark' id='' style='color: #fff;background: #16a085; margin-right: 5px; margin-left: 5px; height:33px; border: 1px solid #16a085;' title='Gestionar Defectos'>Defectos <i class='fas fa-flag'></i></button>");
                out.print("</form>");
            }

            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                out.print("<form action='Registro_detalle?opc=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "&tiempo=1' method='post'>");
                out.print("<input type='hidden' name='id_dell' id='txt_dll'>");
                out.print("<button class='btn btn-dark' onclick='' id='btn_tiem' disabled='true' style='margin-right: 5px; color: #fff;background: #9b59b6; height:33px; border: 1px solid #9b59b6;' title='Gestionar Tiempo'>Tiempo <i class=\"fas fa-clock\"></i></button>");
                out.print("</form>");
            }
            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada") || NombreRol.equals("Inspectora Calidad")) {
                out.print("<form action='Registro_detalle?opc=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "&observacion=1' method='post'>");
                out.print("<input type='hidden' name='id_dell' id='txt_dll3'>");
                out.print("<button class='btn btn-dark' onclick='' id='btn_obs' disabled='true' style='color: #fff;background: #fa5e0f; margin-right: 5px; height:33px; border: 1px solid #fa5e0f;' title='Gestionar Observaciones'>Observación <i class=\"fas fa-exclamation-circle\"></i></button>");
                out.print("</form>");
            }

            out.print("</div>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='display: flex;'>");
            out.print("<input style='height: 33px; margin-top: -7px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'"
                    + "type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</div>");

            out.print("</li>");
            out.print("</ol>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
            lst_RegistroDell = RegistroDetalleJpa.ConsultarRegistroDetalle_id(id_reg);
            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            out.print("<div class='white-box'>");
            out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
            out.print("<h3 class='box-title'><a style='background: white; border: 1px solid white;margin-right:15px;' href='Registro?opc=1&id_orden=" + id_orden + "' class='btn btn-secondary' title='Volver a Registros'><img src='Interfaz/Contenido/Imagenes/reply.png' width='15'></a>Tabla Registros Detalle</h3>");
            out.print("<div>");

            out.print("<div align='right' id='NavPosicion0'></div>");
            if (lst_RegistroDell != null) {
                out.print("<div><b class=''>Cant. Personal: </b>" + lst_RegistroDell.size() + "</div>");
            }

            out.print("</div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table' id='resultados' style='width: 100%;'>");
            out.print("<thead>");
            out.print("<tr align='center'>");
            out.print("<th class='border-top-0' rowspan='2'><i class=\"fas fa-cog\"></i></th>");
            out.print("<th class='border-top-0' rowspan='2' style='max-width: 40px;'>Turno</th>");
            out.print("<th class='border-top-0' rowspan='2' style='min-width: 130px;'>Operaria</th>");
            out.print("<th class='border-top-0' colspan='8'>Hora / Unidades</th>");
            out.print("</tr>");
            out.print("<tr align='center'>");
            out.print("<th class='border-top-0'>1</th>");
            out.print("<th class='border-top-0'>2</th>");
            out.print("<th class='border-top-0'>3</th>");
            out.print("<th class='border-top-0'>4</th>");
            out.print("<th class='border-top-0'>5</th>");
            out.print("<th class='border-top-0'>6</th>");
            out.print("<th class='border-top-0'>7</th>");
            out.print("<th class='border-top-0'>8</th>");
            out.print("<th class='border-top-0'>Total<br>Peso</th>");
            out.print("<th class='border-top-0'>Tiempo</th>");
            out.print("<th class='border-top-0'>Obs</th>");
            out.print("<th class='border-top-0'>Estado</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
//            List lst_temp = RegistroDetalleJpa.Consultar_defectos_activos();
//            Object[] obj_defectos = (Object[]) lst_temp.get(0);
            if (lst_RegistroDell != null && lst_RegistroDell.size() != 0) {
                for (int i = 0; i < lst_RegistroDell.size(); i++) {
                    Object[] obj_regDetll = (Object[]) lst_RegistroDell.get(i);
                    out.print("<tr align='center' id='fila_" + i + "'>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        out.print("<td style='vertical-align: middle;'> <input class='form-check-input' type='radio' name='id_redll' id='radio_id' onclick='pasarDetalles(this.value)' value='" + obj_regDetll[0] + "' style=''></td>");
                    } else if (NombreRol.equals("Inspectora Calidad")) {
                        out.print("<td style='vertical-align: middle;'> <input class='form-check-input' type='radio' name='id_redll' id='radio_idGC' onclick='pasarDetallesGC(this.value)' value='" + obj_regDetll[0] + "' style=''></td>");
                    } else {
                        out.print("<td style='vertical-align: middle;'> <input class='form-check-input' type='radio' disabled></td>");
                    }
//                    out.print("<td style='vertical-align: middle;'> <a id='square_"+i+"' class='far fa-square' onclick='pasarDetalles("+i+")' style='color: #3f5568;font-size: 18px;cursor: pointer;'></a> <input type='radio' name='id_redll' id='radio_id' value='" + obj_regDetll[0] + "' style='display: none;'></td>");
                    out.print("<td title='" + ((obj_regDetll[58] == null) ? "No existe bascula" : obj_regDetll[58]) + "'>" + obj_regDetll[2] + " </td>");
                    //<editor-fold defaultstate="collapsed" desc="CODIGO ANTERIOR ">
                    // if ((Integer) obj_regDetll[5] == 1) {
                    //     String usuarios = obj_regDetll[4].toString().replace("][", "<br><b>-</b>").replace("[", "-").replace("]", "");
                    ////     out.print("<td><a class='btn btn-outline-dark' style=' text-align: center;' title='Editar Usuarios'           href='Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "'><p class='tooltip7' style='border-bottom: 1px dotted black; margin-bottom: 5px;' title='Registro Terminado'><span>" + obj_regDetll[3] + "<i class=\"fas fa-pen\"></i></span><span class='tooltiptext'>" + usuarios + "</span></p></a></td>");
                    //     out.print("<td><a class='btn btn-outline-dark' style='width: 100%; text-align: center;' title='Editar Usuarios' href='Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "'><p class='tooltip7' style='border-bottom: 1px dotted black; margin-bottom: 5px;' title='Registro Terminado'><span>" + obj_regDetll[3] + "<i class=\"fas fa-pen\"></i></span><span class='tooltiptext'><b>Usuarios: </b><br>" + usuarios + "<br> <b>Bascula de turno:</b> <br> " + ((obj_regDetll[59] != null) ? obj_regDetll[59].toString().replace("_", " ").replace(".txt", "") : "Sin Bascula") + "</span></p></a></td>");
                    // } else {
                    //     if (obj_regDetll[4] != null) {
                    //         String usuarios = obj_regDetll[4].toString().replace("][", "<br><b>-</b>").replace("[", "-").replace("]", "");
                    //         out.print("<td><a><p class='tooltip7' style='border-bottom: 1px dotted black; margin-bottom: 5px;' title='Registro Terminado'><span>" + obj_regDetll[3] + "<i class=\"fas fa-pen\"></i></span><span class='tooltiptext'><b>Usuarios: </b><br>" + usuarios + "<br> <b>Bascula de turno:</b> <br> " + ((obj_regDetll[59] != null) ? obj_regDetll[59].toString().replace("_", " ").replace(".txt", "") : "Sin Bascula") + "</span></p></a></td>");
                    ////         out.print("<td><p class='tooltip7' style='border-bottom: 1px dotted black;' title='Registro Terminado'><span>" + obj_regDetll[3] + "</span><span class='tooltiptext'>" + usuarios + "</span></p></td>");
                    //     } else {
                    //         out.print("<td><p class='tooltip7' style='border-bottom: 1px dotted black;' title='Registro Terminado'><span>" + obj_regDetll[3] + "</span><span class='tooltiptext'>No hay usuarios registrados<br> en caso de ver este mensaje comuniquese a T.I <br> <i class=\"fas fa-exclamation-triangle\"></i></span></p></td>");
                    //     }
                    // }
                    //</editor-fold>
                    try {
                        if (obj_regDetll[3] != null) {
                            String[] DataUser = obj_regDetll[3].toString().replace("[", "").replace("]", "").split("-");
                            out.print("<td style='max-width: 180px;'><p class='tooltip3'><span><a style='color: black;padding: 5px;' href='Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "'>" + DataUser[0] + "</a></span><span class='tooltiptext'><b>Codigo: </b>" + DataUser[1] + "</span></p></td>");
                        } else {
                            out.print("<td>No hay usuarios asociados</td>");
                        }
                    } catch (Exception e) {
                        out.print("<td>No hay usuarios asociados</td>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="HORA 1">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[6] == null && obj_regDetll[7] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 1 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[6] != null && obj_regDetll[7] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=1&validhor=0&tempH=1\"' >" + obj_regDetll[5].toString() + "</a></span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[45].toString() + " un</span></p>");
                            } else if (obj_regDetll[6] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=1&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[6] != null && obj_regDetll[7] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[5].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[45].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                            }
                        }
                    } else {
                        if (obj_regDetll[6] != null && obj_regDetll[7] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[5].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[45].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 2">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[9] == null && obj_regDetll[10] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 2 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[9] != null && obj_regDetll[10] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=2&validhor=0&tempH=1\"' >" + obj_regDetll[8].toString() + "</a> </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[46].toString() + " un</span></p>");
                            } else if (obj_regDetll[9] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=2&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[9] != null && obj_regDetll[10] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[8].toString() + "</span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[46].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[9] != null && obj_regDetll[10] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[8].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[46].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 3">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[12] == null && obj_regDetll[13] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 3 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[12] != null && obj_regDetll[13] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=3&validhor=0&tempH=1\"' >" + obj_regDetll[11].toString() + "</a> </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[47].toString() + " un</span></p>");
                            } else if (obj_regDetll[12] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=3&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[12] != null && obj_regDetll[13] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[11].toString() + "</span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[47].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[12] != null && obj_regDetll[13] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[11].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[47].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 4">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[15] == null && obj_regDetll[16] == null) {
//                            out.print("<a style='color: blue;' onclick='AlertaConfirmacion(" + obj_regDetll[0] + ", " + obj_regDetll[1] + ", " + id_orden + ", " + 4 + ")'>"
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 4 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[15] != null && obj_regDetll[16] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=4&validhor=0&tempH=1\"' >" + obj_regDetll[14].toString() + "</a></span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[48].toString() + " un</span></p>");
                            } else if (obj_regDetll[15] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=4&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[15] != null && obj_regDetll[16] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[14].toString() + "</span><span class='tooltiptext'>   <b>Unidades:</b><br> " + obj_regDetll[48].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[15] != null && obj_regDetll[16] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[14].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[48].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 5">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[18] == null && obj_regDetll[19] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 5 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[18] != null && obj_regDetll[19] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=5&validhor=0&tempH=1\"' >" + obj_regDetll[17].toString() + "</a></span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[49].toString() + " un</span></p>");
                            } else if (obj_regDetll[18] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=5&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[18] != null && obj_regDetll[19] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[17].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[49].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[18] != null && obj_regDetll[19] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[17].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[49].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 6">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[21] == null && obj_regDetll[22] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 6 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[21] != null && obj_regDetll[22] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=6&validhor=0&tempH=1\"' >" + obj_regDetll[20].toString() + "</a> </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[50].toString() + " un</span></p>");
                            } else if (obj_regDetll[21] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=6&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[21] != null && obj_regDetll[22] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[20].toString() + "</span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[50].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[21] != null && obj_regDetll[22] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[20].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[50].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 7">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[24] == null && obj_regDetll[25] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 7 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[24] != null && obj_regDetll[25] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=7&validhor=0&tempH=1\"' >" + obj_regDetll[23].toString() + "</a> </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[51].toString() + " un</span></p>");
                            } else if (obj_regDetll[24] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=7&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[24] != null && obj_regDetll[25] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[23].toString() + "</span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[51].toString() + " un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[24] != null && obj_regDetll[25] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[23].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[51].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="HORA 8">
                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            if (obj_regDetll[27] == null && obj_regDetll[28] == null) {
                                out.print("<a style='color: blue;' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=" + 8 + "&validhor=1\"'>"
                                        + "<button class='btn btn-outline-secondary'><i class='fas fa-play'></i></button></a>");
                            } else if (obj_regDetll[27] != null && obj_regDetll[28] != null) {
                                out.print("<p class='tooltip5'><span><a onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=8&validhor=0&tempH=1\"' >" + obj_regDetll[26].toString() + "</a> </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[52] + "  un</span></p>");
                            } else if (obj_regDetll[27] != null) {
                                out.print("<button class='btn btn-outline-warning' onclick='window.location.href=\"Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&id_hora=8&validhor=0\"'><i class=\"fas fa-stop\"></i></button>");
                            }
                        } else {
                            if (obj_regDetll[27] != null && obj_regDetll[28] != null) {
                                out.print("<p class='tooltip5'><span>" + obj_regDetll[26].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[52] + "  un</span></p>");
                            } else {
                                out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");
                            }
                        }
                    } else {
                        if (obj_regDetll[27] != null && obj_regDetll[28] != null) {
                            out.print("<p class='tooltip5'><span>" + obj_regDetll[26].toString() + " </span><span class='tooltiptext'>  <b>Unidades:</b><br> " + obj_regDetll[52].toString() + " un</span></p>");
                        } else {
                            out.print("<button style='cursor: no-drop; border: transparent; background: white;' disabled title='No se puede ingresar peso'><i class=\"fa-solid fa-ban\"></i></button>");

                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    out.print("<td><p class='tooltip5'><span>" + obj_regDetll[32].toString() + " </span><span class='tooltiptext'> <b>Usuario registro:</b><br>" + obj_regDetll[55].toString() + "<br> <b>Unidades: </b><br>" + obj_regDetll[53].toString() + " un</span></td>");
                    if (obj_regDetll[34] != null) {
                        String[] tiempos = obj_regDetll[34].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                        out.print("<td>");
                        out.print("<p class='tooltip4'><span><i class='fas fa-clock' style='color: #9a5cb3;'></i></span>");
                        out.print("<span class='tooltiptext'>");
                        out.print("<b>TIEMPO - MINUTOS </b><br>");
                        for (int j = 0; j < tiempos.length; j++) {
                            String[] des = tiempos[j].toString().split("/");
                            out.print("- <b style='color:#9b59b6;'>" + des[1] + "</b>: " + des[0] + " Min<br>");
                        }
                        out.print("</span></p></td>");
                    } else {
                        out.print("<td>");
                        out.print("<p class='tooltip4'><span><i class='fas fa-clock'></i></span>");
                        out.print("<span class='tooltiptext'> No se ha descontado tiempo</span></p></td>");
                    }

                    if (obj_regDetll[56] != null) {
                        String[] observaciones;
                        String[] obsP;
                        try {
                            observaciones = obj_regDetll[56].toString().replace("]//", "]/").replace("]/", "--").split("--");
                            out.print("<td>");
                            out.print("<p class='tooltip8'><span><i class='fas fa-exclamation-circle' style='color: #fa5e0f;'></i></span>");
                            out.print("<span class='tooltiptext'>");
                            out.print("<b style='margin-left: 38%; color: #fa5e0f;'>Observaciones</b><br>");
                            for (String observacion : observaciones) {
                                obsP = observacion.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("<b>Hora: </b>" + obsP[0] + "<br><b>Asunto:</b> " + obsP[1] + "<br><b>Justificacion: </b>" + obsP[2] + "<br><br>");
                            }
                            out.print("</span></p>");
                        } catch (IOException ex) {
                            obsP = obj_regDetll[56].toString().replace("][", "-").replace("]/", "").replace("[", "").replace("]", "").split("-");
                            out.print("<td>");
                            out.print("<p class='tooltip8'><span><i class='fas fa-exclamation-circle'></i></span>");
                            out.print("<span class='tooltiptext'>");
                            out.print("<b style='margin-left: 38%; color: #fa5e0f'>Observaciones</b> <br>");
                            for (String obss : obsP) {
                                obsP = obss.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("<b>Hora: </b>" + obsP[0] + "<br><b>Asunto:</b> " + obsP[1] + "<br><b>Justificacion: </b>" + obsP[2] + "<br><br>");
                            }
                            out.print("</span></p></td>");
                            out.print("</td>");
                        }
                    } else {
                        out.print("<td>");
                        out.print("<p class='tooltip4'><span><i class='fas fa-exclamation-circle'></i></span>");
                        out.print("<span class='tooltiptext'> No se han registrado observaciones</span></p></td>");
                        out.print("</td>");
                    }

                    out.print("<td>");
                    if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            out.print("<a href='Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&est=0&temp=1' class='btn btn-info btn-sm' title='Registro Abierto'><i style='color:#fff;' class='fas fa-lock-open'></i></a>");
                            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                                out.print("<a style='margin-left:10px;' href='Registro_detalle?opc=1&id_dell=" + obj_regDetll[0] + "&id_registro=" + obj_regDetll[1] + "&id_orden=" + id_orden + "&est=0&limpieza=1' class='btn btn-danger btn-sm' title='Borrar Control'><i style='color:#fff;' class='fas fa-eraser'></i></a>");
                            }
                        } else {
                            if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora")) {
                                out.print("<a onclick='mostrarConvencion(10);passarBas(" + obj_regDetll[0] + ")' class='btn btn-info' title='Registro Cerrado'><i style='color:#fff;' class='fas fa-lock'></i></a>");
                            } else {
                                out.print("<button class='btn btn-info' disabled title='Registro Cerrado'><i style='color:#fff;' class='fas fa-lock'></i></button>");
                            }
                        }
                    } else {
                        if (Integer.parseInt(obj_regDetll[4].toString()) == 1) {
                            out.print("<button class='btn btn-info' disabled title='Registro Abierto'><i style='color:#fff;' class='fas fa-lock-open'></i></button>");
                        } else {
                            out.print("<button class='btn btn-info' disabled title='Registro Cerrado'><i style='color:#fff;' class='fas fa-lock'></i></button>");
                        }
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='15' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
                out.print("</tr>");
            }

            out.print("</tbody>");
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 20);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- Fin -->");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_registroDetalle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
