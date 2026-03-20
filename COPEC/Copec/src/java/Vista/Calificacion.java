package Vista;

import Controlador.CalificacionJpaController;
import Controlador.PreguntaJpaController;
import Controlador.PruebaJpaController;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import SQL.Conexion_herramental;

public class Calificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        PreguntaJpaController jpa_pregunta = new PreguntaJpaController();
        CalificacionJpaController jpa_calificacion = new CalificacionJpaController();
        Conexion_herramental jpa_herramental = new Conexion_herramental();
//        List lst_defecto = jpa_calificacion.ConsultaherramentalDefecto();
        List lst_defecto = null;
        try {
            lst_defecto = jpa_herramental.Consulta_herramental_defecto();
        } catch (Exception ex) {
            Logger.getLogger(Calificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date fecha = new Date();
        DateFormat FechaYHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("Confirmar_usuario")) {
                //<editor-fold defaultstate="collapsed" desc="confirmar usuario">
                out.print("<div style='text-align:center;'>");
                out.print("<center>");
                out.print("<fieldset style='width:20%;margin-top: 15%;'>");
                out.print("<legend>Confirmar Usuario</legend>");
                out.print("<form method='post' action='Calificacion?opc=2&mod=0'>");
                out.print("<b>Documento</b><br/>");
                out.print("<input type='text' name='txt_documento' id='documento-id' placeholder='Documento'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('documento-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Codigo</b><br/>");
                out.print("<input type='text' name='txt_codigo' id='codigo-id' placeholder='Codigo'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('codigo-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Confirmar'><br/>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</center>");
                out.print("<div class='cleaner'></div></div>");
                //</editor-fold>
            }
            if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("Pruebas_usuario")) {
                //<editor-fold defaultstate="collapsed" desc="mostrar pruebas pendientes o realizadas">
                List lst_programaciones = (List) pageContext.getRequest().getAttribute("programaciones");
                List lst_empleado = (List) pageContext.getRequest().getAttribute("empleados");
                List lst_pruebas = null;
                int resultado = 0;
                int resultado2 = 0;
                if (lst_programaciones == null || lst_programaciones.isEmpty()) {
                    out.print("<div id='content_sin'>");
                    out.print("<a href='Calificacion?opc=1'><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                    out.print("<b>No se encontraron resultados</b>");
                    out.print("<div class='cleaner'></div></div>");
                } else {
                    Object[] obj_empleado = (Object[]) lst_empleado.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<a href='Calificacion?opc=1'><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                    out.print("<h3>Pruebas programadas</h3>");
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>id</th>");
                    out.print("<th>Prueba</th>");
                    out.print("<th>Area</th>");
                    out.print("<th>Tiempo</th>");
                    out.print("<th>Promedio</th>");
                    out.print("<th>Preguntas</th>");
                    out.print("<th>Iniciar</th>");
                    out.print("<th>Resultado</th>");
                    out.print("</tr>");
                    for (int l = 0; l < lst_programaciones.size(); l++) {
                        Object[] obj_programacion = (Object[]) lst_programaciones.get(l);
                        String[] id_pruebas = obj_programacion[7].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                        for (int i = 0; i < id_pruebas.length; i++) {
                            resultado = 0;
                            resultado2 = 0;
                            lst_pruebas = jpa_prueba.ConsultapruebaLogId(Integer.parseInt(id_pruebas[i].toString()), Integer.parseInt(obj_programacion[0].toString()));
                            Object[] obj_prueba = (Object[]) lst_pruebas.get(0);
                            String[] contP = obj_prueba[5].toString().replace("][", "-").split("-");
                            List lst_pruReal = jpa_calificacion.ConsultaPruebaRealizada(Integer.parseInt(obj_empleado[0].toString()), Integer.parseInt(obj_prueba[0].toString()), Integer.parseInt(obj_programacion[0].toString()));
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_prueba[0] + "</td>");
                            out.print("<td>" + obj_prueba[1] + "</td>");
                            out.print("<td>" + obj_prueba[6] + "</td>");
                            out.print("<td align='center'>" + obj_prueba[3] + "<b> minuto(s)</b></td>");
                            out.print("<td align='center'>" + obj_prueba[4] + "</td>");
                            out.print("<td align='center'>" + contP.length + "</td>");
                            if (lst_pruReal == null) {
                                out.print("<td align='center'><a href='#' onclick=\"SPrueba(\'" + obj_prueba[3] + "\',\'" + obj_empleado[1] + "" + obj_empleado[2] + "\',\'" + obj_empleado[0] + "\',\'" + id_pruebas[i] + "\',\'" + obj_empleado[3] + "\',\'" + obj_empleado[4] + "\',\'" + obj_programacion[0] + "\');\"><img src='Interfaz/Template/images/Ver.png' alt='Logo' width='22' height='22.5' /></a></td>");
                                out.print("<td align='center'>N/A</td>");
                            } else {
                                if (lst_pruReal.size() > contP.length) {
                                    for (int j = 0; j < lst_pruReal.size(); j++) {
                                        Object[] obj_resultados = (Object[]) lst_pruReal.get(j);
                                        if (Integer.parseInt(obj_resultados[9].toString()) == 1) {
                                            resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                                        } else {
                                            resultado2 = (resultado2 + Integer.parseInt(obj_resultados[6].toString()));
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < lst_pruReal.size(); j++) {
                                        Object[] obj_resultados = (Object[]) lst_pruReal.get(j);
                                        resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                                    }

                                }
                                resultado = (resultado * 100) / contP.length;
                                resultado2 = (resultado2 * 100) / contP.length;
                                if (resultado < (Integer) obj_prueba[4]) {
                                    if (resultado2 == 0) {
                                        out.print("<td align='center'><b style='color:#F6921E'>Prueba ya realizada </b><hr /><a href='#' onclick=\"SPrueba(\'" + obj_prueba[3] + "\',\'" + obj_empleado[1] + "" + obj_empleado[2] + "\',\'" + obj_empleado[0] + "\',\'" + id_pruebas[i] + "\',\'" + obj_empleado[3] + "\',\'" + obj_empleado[4] + "\',\'" + obj_programacion[0] + "\');\"><b style='color:red'>Repetir Prueba</b></a></td>"
                                                + "<td align='center'><b style='color:#292929'>Estado: </b><b style='color:red'>No aprobado " + resultado + " %</b></td>");
                                    } else if (resultado2 < Integer.parseInt(obj_prueba[4].toString())) {
                                        out.print("<td align='center'><b style='color:#F6921E'>1° : Prueba ya realizada </b><hr /><b style='color:#F6921E'>2° : Prueba ya realizada </b></td>"
                                                + "<td align='center'><b style='color:#292929'>Estado: </b><b style='color:red'>No aprobado " + resultado + " %</b><hr /><b style='color:#292929'>Estado: </b><b style='color:red'>No aprobado " + resultado2 + " %</b></td>");
                                    } else {
                                        out.print("<td align='center'><b style='color:#F6921E'>1° : Prueba ya realizada </b><hr /><b style='color:#F6921E'>2° : Prueba ya realizada </b></td>"
                                                + "<td align='center'><b style='color:#292929'>Estado: </b><b style='color:red'>No aprobado " + resultado + " %</b><hr /><b style='color:#292929'>Estado: </b><b style='color:green'>aprobado " + resultado2 + " %</b></td>");
                                    }
                                } else {
                                    out.print("<td align='center'><b style='color:#F6921E'>Prueba ya realizada </b></td>"
                                            + "<td align='center'><b style='color:#292929'>Estado: </b><b style='color:green'>aprobado " + resultado + " %</b></td>");
                                }
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<div class='cleaner'></div></div>");
                }
                //</editor-fold>
            }
            if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("Prueba")) {
                //<editor-fold defaultstate="collapsed" desc="prueba a realizar">
                List lst_empleado = (List) pageContext.getRequest().getAttribute("empleado");
                List lst_pruebas = (List) pageContext.getRequest().getAttribute("prueba");
                int id_prueba = Integer.parseInt(pageContext.getRequest().getAttribute("id_prueba").toString());
                int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
                int id_programacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_programacion").toString());
                Object[] obj_empleado = (Object[]) lst_empleado.get(0);
                Object[] obj_prueba = (Object[]) lst_pruebas.get(0);
                String[] contP = obj_prueba[5].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                int cont = 1;
                String query = "";
                out.print("<script language=\"JavaScript\" type=\"text/javascript\">\n"
                        + "            var bPreguntar = true;\n"
                        + "            window.onbeforeunload = preguntarAntesDeSalir;\n"
                        + "            function preguntarAntesDeSalir()\n"
                        + "            {\n"
                        + "                if (bPreguntar)\n"
                        + "                    return \"¿Seguro que quieres salir?\";\n"
                        + "            }\n"
                        + "        </script>");
                out.print("<div style='position:fixed;margin-left:70%;margin-top:43%;'>");
                out.print("<div id='CountDownTimer' data-timer='" + ((Integer) obj_prueba[3] * 60) + "' style='width: 300px; height: 100px;'></div>");
//                out.print("<div id='CountDownTimer' data-timer='5' style='width: 300px; height: 100px;'></div>");
                out.print("</div>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td colspan='4' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td rowspan='2' align='center' style='width:25%'><img src=' Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px' ></td>");
                out.print("<td align='center' colspan='2'>Registro</td>");
                out.print("<td align='center'><b>Codigo: </b>R-GC-178</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' colspan='2'>Calificación de personal</td>");
                out.print("<td align='center'><b>Version: </b>0</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' rowspan='2'><b>Usuario: </b>" + obj_empleado[1] + " " + obj_empleado[2] + "</td>");
                out.print("<td align='center' colspan='2'><b>Cargo: </b>" + obj_empleado[10] + "</td>");
                out.print("<td align='center'><b>Area: </b>" + obj_empleado[7] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>Prueba: </b>" + obj_prueba[1] + "</td>");
                out.print("<td align='center'><b>Num preguntas: </b>" + contP.length + "</td>");
                out.print("<td align='center'><b>Promedio minimo: </b>" + obj_prueba[4] + "</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<form method='post' id='formP-id' name='formP' action='Calificacion?opc=4'>");
                out.print("<table class='table2' style='width:100%;'>");
                for (int i = 0; i < contP.length; i++) {
                    out.print("<tr>");
                    out.print("<td style='width:5%;font-size: 16px;' align='center'><b style='color:#292929;'>" + cont + "</b></td>");
                    out.print("<td valign='top' style='padding-left: 15px;'>");
                    List lst_pregunta = jpa_pregunta.ConsultaPreguntaId(Integer.parseInt(contP[i]));
                    Object[] obj_pregunta = (Object[]) lst_pregunta.get(0);
                    out.print("<input type='hidden' value='" + obj_pregunta[0] + "' name='idPr" + obj_pregunta[0] + "'>");
                    out.print("<div style='float:left;width:70%'>");
                    out.print("<b style='color:#292929;'>" + obj_pregunta[3] + "</b><br />");
                    String[] opciones = (obj_pregunta[4] + "//" + obj_pregunta[5].toString()).split("//");
                    String num = "";
                    for (int j = 0; j < opciones.length; j++) {
                        int rand = (int) (Math.random() * opciones.length);
                        if (num.contains("[" + rand + "]")) {
                            j = (j - 1);
                        } else {
                            num = num + "[" + rand + "]";
                            out.print("&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='opc" + obj_pregunta[0] + "' id='id-opc" + obj_pregunta[0] + "" + j + "' value='" + opciones[rand] + "' onclick='Calificar(" + obj_pregunta[0] + ");boton()'>");
                            out.print("" + opciones[rand] + "<br />");
                        }
                    }
                    out.print("</div>");
                    cont = (cont + 1);
                    out.print("<div style='float:left;width:30%'>");
                    String[] consulta = lst_defecto.toString().replace("[", "").replace("]", "").split("-------");
                    for (int p = 0; p < consulta.length; p++) {
                        String[] obj_defecto = consulta[p].toString().split("///////");
                        String[] preg = obj_pregunta[3].toString().split(" ");
                        if (preg[preg.length - 1].equals(obj_defecto[6]) || (preg[preg.length - 2] + " " + preg[preg.length - 1]).equals(obj_defecto[6])) {
                            String[] arg_imgenD = obj_defecto[7].toString().split("</p>")[0].split("<p>");
                            String[] arg_link = arg_imgenD[1].split("\"");
                            String image = "";
                            for (int k = 0; k < arg_link.length; k++) {
                                if (arg_link[k].contains("UserFiles")) {
                                    image = "http://172.16.2.117:8084/Herramental/" + arg_link[k] + "";
                                    out.print("<a href='#' onclick=\"Image('" + image + "');return false;\">");
                                    out.print("<img class='prueba' src=\"" + image + "\" alt=\" \" width=\"300\" height=\"212\">");
                                    out.print("</a>");
                                }
                            }
                        }
                    }
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                    if (i == 0) {
                        query = "(" + id_usuario + "," + id_prueba + "," + obj_pregunta[0] + "," + id_programacion + ",selccionU,(select if(p.respuesta =selccionU  ,1,0) from pregunta p where p.id_pregunta =" + obj_pregunta[0] + "),fechI)";
                    } else {
                        query = query + ",(" + id_usuario + "," + id_prueba + "," + obj_pregunta[0] + "," + id_programacion + ",selccionU,(select if(p.respuesta =selccionU  ,1,0) from pregunta p where p.id_pregunta =" + obj_pregunta[0] + "),fechI)";
                    }
                }
                out.print("</table>");
                out.print("<center>");
                out.print("<input type='submit' style='display:none' id='botonF' value='Finalizar' onclick='bPreguntar = false;'>");
                out.print("</center>");
                out.print("<input type='hidden' value='" + contP.length + "' name='Topreg' id='Topreg-id'>");
                out.print("<input type='hidden' value='" + query + "' name='query' id='query-id'>");
                out.print("<input type='hidden' value='" + id_usuario + "' name='idU' id='idU'>");
                out.print("<input type='hidden' value='" + id_prueba + "' name='idP' id='idP'>");
                out.print("<input type='hidden' value='" + id_programacion + "' name='idProg' id='idProg'>");
                out.print("</form>");
                //</editor-fold>
            }
            if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("resultado")) {
                //<editor-fold defaultstate="collapsed" desc="resultado de la prueba">
                int id_prueba = Integer.parseInt(pageContext.getRequest().getAttribute("id_prueba").toString());
                int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
                int id_programacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_programacion").toString());
                List lst_pruebas = jpa_prueba.ConsultapruebaLogId(id_prueba, id_programacion);
                Object[] obj_prueba = (Object[]) lst_pruebas.get(0);
                String[] contP = obj_prueba[5].toString().replace("][", "-").split("-");
                int resultado = 0;
                List lst_pruReal = jpa_calificacion.ConsultaPruebaRealizada(id_usuario, id_prueba, id_programacion);
                for (int j = 0; j < lst_pruReal.size(); j++) {
                    Object[] obj_resultados = (Object[]) lst_pruReal.get(j);
                    if (Integer.parseInt(obj_resultados[9].toString()) == 2) {
                        resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                    }
                }
                resultado = (resultado * 100) / contP.length;
                out.print("<script type='text/javascript'>");
                out.print("function ResultadoAlert() {");
                out.print("swal({");
                out.print("title:\"Resultado\",");
                out.print("text:\"");
                if (resultado < (Integer) obj_prueba[4]) {
                    out.print("<b style='color:#292929'>Se ha finalizado la prueba<br /> promedio minimo: " + obj_prueba[4] + "%<br />Su promedio total es: " + resultado + " %</b><br />");
                    out.print("<b style='color:red'>No aprobado</b>");
                    out.print("");
                    out.print("\",");
                    out.print("type:\"error\",");
                } else {
                    out.print("<b style='color:#292929'>Se ha finalizado la prueba<br /> promedio minimo: " + obj_prueba[4] + "%<br />Su promedio total es: " + resultado + " %</b><br />");
                    out.print("<b style='color:green'>aprobado</b>");
                    out.print("");
                    out.print("\",");
                    out.print("type:\"success\",");
                }
                out.print("showCancelButton:\"true\",");
                out.print("confirmButtonColor:\"#048B86\",");
                out.print("confirmButtonText:\"Aceptar\",");
                out.print("cancelButtonText:\"Cancelar\",");
                out.print("closeOnConfirm:\"false\",");
                out.print("closeOnCancel:\"false\",");
                out.print("html:\"true\"");
                out.print("},");
                out.print("function(isConfirm){");
                out.print("if (isConfirm) {");
                out.print("window.close();");
                out.print("}else{");
                out.print("window.close();");
                out.print("}");
                out.print("});");
                out.print("}");
                out.print("</script>");
                //</editor-fold>
            }
            if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("Pruebas_usuarioR")) {
                //<editor-fold defaultstate="collapsed" desc="mostrar pruebas realizadas">
                List lst_programaciones = (List) pageContext.getRequest().getAttribute("programaciones");
                List lst_empleado = (List) pageContext.getRequest().getAttribute("empleados");
                List lst_pruebas = null;
                int resultado = 0;
                int resultado2 = 0;
                if (lst_programaciones == null || lst_programaciones.isEmpty()) {
                    out.print("<div id='content_sin'>");
                    out.print("<a href='Empleado?opc=1'><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                    out.print("<b>No se encontraron resultados</b>");
                    out.print("<div class='cleaner'></div></div>");
                } else {
                    Object[] obj_empleado = (Object[]) lst_empleado.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<a href='Empleado?opc=1'><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                    out.print("<h3>Pruebas programadas</h3>");
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>id</th>");
                    out.print("<th>Prueba</th>");
                    out.print("<th>Area</th>");
                    out.print("<th>Tiempo</th>");
                    out.print("<th>Promedio</th>");
                    out.print("<th>Preguntas</th>");
                    out.print("<th>Resultado</th>");
                    out.print("<th>Ver</th>");
                    out.print("</tr>");
                    for (int l = 0; l < lst_programaciones.size(); l++) {
                        Object[] obj_programacion = (Object[]) lst_programaciones.get(l);
                        String[] id_pruebas = obj_programacion[7].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                        for (int i = 0; i < id_pruebas.length; i++) {
                            resultado = 0;
                            resultado2 = 0;
                            lst_pruebas = jpa_prueba.ConsultapruebaLogId(Integer.parseInt(id_pruebas[i].toString()), Integer.parseInt(obj_programacion[0].toString()));
                            Object[] obj_prueba = (Object[]) lst_pruebas.get(0);
                            String[] contP = obj_prueba[5].toString().replace("][", "-").split("-");
                            List lst_pruReal = jpa_calificacion.ConsultaPruebaRealizada(Integer.parseInt(obj_empleado[0].toString()), Integer.parseInt(obj_prueba[0].toString()), Integer.parseInt(obj_programacion[0].toString()));
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_prueba[0] + "</td>");
                            out.print("<td>" + obj_prueba[1] + "</td>");
                            out.print("<td>" + obj_prueba[6] + "</td>");
                            out.print("<td align='center'>" + obj_prueba[3] + "<b> minuto(s)</b></td>");
                            out.print("<td align='center'>" + obj_prueba[4] + "</td>");
                            out.print("<td align='center'>" + contP.length + "</td>");
                            if (lst_pruReal == null) {
                                out.print("<td align='center'><b style='color:#F6921E'>No se ha realizado la prueba</b></td>");
                                out.print("<td align='center'>N/A</td>");
                            } else {
                                if (lst_pruReal.size() > contP.length) {
                                    for (int j = 0; j < lst_pruReal.size(); j++) {
                                        Object[] obj_resultados = (Object[]) lst_pruReal.get(j);
                                        if (Integer.parseInt(obj_resultados[9].toString()) == 1) {
                                            resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                                        } else {
                                            resultado2 = (resultado2 + Integer.parseInt(obj_resultados[6].toString()));
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < lst_pruReal.size(); j++) {
                                        Object[] obj_resultados = (Object[]) lst_pruReal.get(j);
                                        resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                                    }
                                }
                                resultado = (resultado * 100) / contP.length;
                                resultado2 = (resultado2 * 100) / contP.length;
                                if (resultado < (Integer) obj_prueba[4]) {
                                    if (resultado2 == 0) {
                                        out.print("<td align='center'><b style='color:#292929'>Estado: </b><b style='color:red'>1° : No aprobado " + resultado + " %</b><hr/><b style='color:#292929'>Estado: </b><b style='color:orange'>2° : Intento no realizado</b></td>");
                                        out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + id_pruebas[i] + "&idU=" + obj_empleado[0] + "&idProg=" + obj_programacion[0] + "&int=1'><img src='Interfaz/Template/images/Ver.png' width='22' height='22' title='Pruebas'></a><hr/><a href='#'><img src='Interfaz/Template/images/Warning.png' width='22' height='22' title='Pruebas'></a></td>");
                                    } else if (resultado2 < Integer.parseInt(obj_prueba[4].toString())) {
                                        out.print("<td align='center'><b style='color:#292929'>Estado: </b><b style='color:red'>1° : No aprobado " + resultado + " %</b><hr/><b style='color:#292929'>Estado: </b><b style='color:red'>2° : No aprobado " + resultado2 + " %</b></td>");
                                        out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + id_pruebas[i] + "&idU=" + obj_empleado[0] + "&idProg=" + obj_programacion[0] + "&int=1'><img src='Interfaz/Template/images/Ver.png' width='22' height='22' title='Pruebas'></a><hr/><a href='Calificacion?opc=5&idP=" + id_pruebas[i] + "&idU=" + obj_empleado[0] + "&idProg=" + obj_programacion[0] + "&int=2'><img src='Interfaz/Template/images/Ver.png' width='22' height='22' title='Pruebas'></a></td>");
                                    } else {
                                        out.print("<td align='center'><b style='color:#292929'>Estado: </b><b style='color:red'>1° : No aprobado " + resultado + " %</b><hr/><b style='color:#292929'>Estado: </b><b style='color:green'>2° : aprobado " + resultado2 + " %</b></td>");
                                        out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + id_pruebas[i] + "&idU=" + obj_empleado[0] + "&idProg=" + obj_programacion[0] + "&int=1'><img src='Interfaz/Template/images/Ver.png' width='22' height='22' title='Pruebas'></a><hr/><a href='Calificacion?opc=5&idP=" + id_pruebas[i] + "&idU=" + obj_empleado[0] + "&idProg=" + obj_programacion[0] + "&int=2'><img src='Interfaz/Template/images/Ver.png' width='22' height='22' title='Pruebas'></a></td>");
                                    }
                                } else {
                                    out.print("<td align='center'><b style='color:#292929'>Estado: </b><b style='color:green'>aprobado " + resultado + " %</b></td>");
                                    out.print("<td align='center'><a href='Calificacion?opc=5&idP=" + id_pruebas[i] + "&idU=" + obj_empleado[0] + "&idProg=" + obj_programacion[0] + "&int=2'><img src='Interfaz/Template/images/Ver.png' width='22' height='22' title='Pruebas'></td>");
                                }
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<div class='cleaner'></div></div>");
                }
                //</editor-fold>
            }
            if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("prueba_realizada")) {
                //<editor-fold defaultstate="collapsed" desc="Vista pruebas realizadas">
                int resultado = 0;
                List lst_empleado = (List) pageContext.getRequest().getAttribute("empleado");
                Object[] obj_empleado = (Object[]) lst_empleado.get(0);
                List lst_pruebas = (List) pageContext.getRequest().getAttribute("prueba");
                List lst_pruebaRealizada = (List) pageContext.getRequest().getAttribute("pruebaRealizada");
                Object[] obj_prueba = (Object[]) lst_pruebas.get(0);
                String[] contP = obj_prueba[5].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                int cont = 1;
                out.print("<a href='Calificacion?opc=2&txt_documento=" + obj_empleado[3] + "&txt_codigo=" + obj_empleado[4] + "&mod=" + 1 + "'><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                out.print("<div style='float:right;'><a onclick='Imprimir();' ><img src=\"Interfaz/Template/images/Printer.png\" style=\"width: 30px;height: 30px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF</div>");
                out.print("<div id='Imprimir'>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td colspan='5' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td rowspan='2' align='center' style='width:25%'><img src=' Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px' ></td>");
                out.print("<td align='center' colspan='3'>Registro</td>");
                out.print("<td align='center'><b>Codigo: </b>R-GC-178</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' colspan='3'>Calificación de personal</td>");
                out.print("<td align='center'><b>Version: </b>0</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' rowspan='2'><b>Usuario: </b>" + obj_empleado[1] + " " + obj_empleado[2] + "</td>");
                out.print("<td align='center' colspan='2'><b>Cargo: </b>" + obj_empleado[10] + "</td>");
                out.print("<td align='center' colspan='2'><b>Area: </b>" + obj_empleado[7] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>Prueba: </b>" + obj_prueba[1] + "</td>");
                out.print("<td align='center'><b>Num preguntas: </b>" + contP.length + "</td>");
                out.print("<td align='center'><b>Calificación minima: </b>" + obj_prueba[4] + " %</td>");
                for (int j = 0; j < lst_pruebaRealizada.size(); j++) {
                    Object[] obj_resultados = (Object[]) lst_pruebaRealizada.get(j);
                    resultado = (resultado + Integer.parseInt(obj_resultados[6].toString()));
                }
                resultado = (resultado * 100) / contP.length;
                if (resultado < (Integer) obj_prueba[4]) {
                    out.print("<td align='center'><b>Resultado: </b><b style='color:red'>No aprobado " + resultado + " %</b></td>");
                } else {
                    out.print("<td align='center'><b>Resultado: </b><b style='color:green'>Aprobado " + resultado + " %</b></td>");
                }
                out.print("</tr>");
                out.print("</table>");
                out.print("<table class='table3' style='width:100%;'>");
                for (int i = 0; i < lst_pruebaRealizada.size(); i++) {
                    Object[] obj_pruebaR = (Object[]) lst_pruebaRealizada.get(i);
                    if ((Integer) obj_pruebaR[3] == Integer.parseInt(contP[i].toString())) {
                        if ((Integer) obj_pruebaR[6] == 1) {
//                            out.print("<tr style='background-color:#d8ffc8'>");
//                            out.print("<td style='width:5%;font-size: 16px;border: 5px #3ed600 solid;' align='center'><b style='color:#292929;'>" + cont + "</b></td>");
                            out.print("<tr>");
                            out.print("<td style='width:5%;font-size: 16px;' align='center'><b style='color:#32ad00;'>" + cont + "</b></td>");
                        } else {
//                            out.print("<tr style='background-color:#fdd4d4'>");
//                            out.print("<td style='width:5%;font-size: 16px;border: 5px #dc0000 solid;' align='center'><b style='color:#292929;'>" + cont + "</b></td>");
                            out.print("<tr>");
                            out.print("<td style='width:5%;font-size: 16px;' align='center'><b style='color:#dc0000;'>" + cont + "</b></td>");
                        }
                        out.print("<td valign='top' style='padding-left: 15px;'>");
                        List lst_pregunta = jpa_pregunta.ConsultaPreguntaId((Integer) obj_pruebaR[3]);
                        Object[] obj_pregunta = (Object[]) lst_pregunta.get(0);
                        out.print("<b style='color:#292929;'>" + obj_pregunta[3] + "</b><br />");
                        String[] opciones = (obj_pregunta[4] + "//" + obj_pregunta[5].toString()).split("//");
                        String num = "";
                        for (int j = 0; j < opciones.length; j++) {
                            int rand = (int) (Math.random() * opciones.length);
                            if (num.contains("[" + rand + "]")) {
                                j = (j - 1);
                            } else {
                                num = num + "[" + rand + "]";
                                if (obj_pruebaR[5].toString().equals(opciones[rand])) {
                                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='opc" + obj_pregunta[0] + "' id='id-opc" + obj_pregunta[0] + "" + j + "' value='" + opciones[rand] + "' onclick='Calificar(" + obj_pregunta[0] + ");boton()' disabled='true' checked>");
                                } else {
                                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='opc" + obj_pregunta[0] + "' id='id-opc" + obj_pregunta[0] + "" + j + "' value='" + opciones[rand] + "' onclick='Calificar(" + obj_pregunta[0] + ");boton()' disabled='true'>");
                                }
                                out.print("" + opciones[rand] + "<br />");
                            }
                        }
                        cont = (cont + 1);
                        out.print("</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("</div>");
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Calificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
