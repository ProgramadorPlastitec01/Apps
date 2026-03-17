package Tags;

import Controladores.InstrumentoMedicionJpaController;
import Controladores.TipoInstrumentoJpaController;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        InstrumentoMedicionJpaController jpa_intrumento = new InstrumentoMedicionJpaController();
        TipoInstrumentoJpaController jpa_TipoInst = new TipoInstrumentoJpaController();

        HttpSession sesion = pageContext.getSession();
        String UserName = "";
        String Userrol = "";

        List lst_instrumentos = null;
        try {
            UserName = pageContext.getSession().getAttribute("Nombre").toString();
            Userrol = pageContext.getSession().getAttribute("Rol").toString();
        } catch (Exception e) {
            UserName = "Temp";
            Userrol = "Temp";
        }
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Programa de verificación metrologica </h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-around;'>");
            out.print("<h4><b class='subTitle2'>Bienvenido, </b> " + UserName + " </h4>");
            out.print("</div>");
            
            out.print("<div class='card-body'>");
            out.print("<div class='' style='display: flex; justify-content: space-between;'>");
            out.print("<h5 class='mb-3'>Instrumentos proximos a verificar</h5>");
            out.print("<div class='initialButton'>");
            out.print("<button class='btn btn-success mr-2' onclick=\"tableToExcel('table-1')\"  data-toggle='tooltip' data-placement='top' title='Exportar Excel' ><i class=\"fas fa-file-excel\"></i></button>");
            out.print("<button class='btn btn-danger' onclick='Imprimir();' data-toggle='tooltip' data-placement='top' title='Imprimir pdf'><i class='fas fa-file-pdf'></i></button>");
            out.print("</div>");
            out.print("</div>");
            
            out.print("<div class='table-responsive' id='Imprimir'>");
            lst_instrumentos = jpa_intrumento.consultaInstrumentos(5);
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr style='display: none;'>");
            out.print("<th>.</th>");
            out.print("<th>.</th>");
            out.print("<th>.</th>");
            out.print("<th>.</th>");
            out.print("<th>.</th>");
            out.print("<th>.</th>");
//            out.print("<th>.</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (lst_instrumentos != null) {
                int temp = 0;
                for (int i = 0; i < lst_instrumentos.size(); i++) {
                    Object[] obj_instrumentos = (Object[]) lst_instrumentos.get(i);
                    String[] Inst_Ubic = obj_instrumentos[6].toString().split("//");
                    List lst_VrfExterna = jpa_intrumento.consultaVerificacionExternaInstrumento((Integer) obj_instrumentos[0]);
                    List lst_TipoInst = jpa_TipoInst.consultaTipoInstrumentoId((Integer) obj_instrumentos[1]);
                    Object[] obj_TipoInst = (Object[]) lst_TipoInst.get(0);
                    out.print("<tr style='border: 1px solid #f6f6f6;'>");
                    if (lst_VrfExterna != null) {
                        Object[] obj_VrfExt = (Object[]) lst_VrfExterna.get(0);
                         if (obj_instrumentos[37].equals("1")) {
                            temp = 1;
                            //<editor-fold defaultstate="collapsed" desc="ESTADO AMARILLO">
                            out.print("<th style='background: #F5D942;'>");
                            out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                            if (obj_instrumentos[38].equals("Interna")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Inspeccion </b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Verificacion </b>");
                                }
                            } else if (obj_instrumentos[38].equals("Externa")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Verificacion </b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Calibracion </b>");
                                }
                            } else if (obj_instrumentos[38].equals("Todos")) {
                                if ((Integer) obj_TipoInst[9] == 0) {
                                    out.print("<b style='color:#FFF'>Inspeccion Verificacion </b>");
                                } else {
                                    out.print("<b style='color:#FFF'>Verificacion Calibracion </b>");
                                }
                            }
                            out.print("</div>");
                            out.print("</th>");
                            out.print("<th class='filaLimited'>");
                            out.print("<div class='' style='align-items: center;height: 100px;'>");
                            out.print("<a href='#' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                            out.print("</div>");
                            out.print("</th>");
                            //</editor-fold>
                        } 
                    }  else if (obj_instrumentos[37].equals("1")) {
                        temp = 1;
                        //<editor-fold defaultstate="collapsed" desc="ESTADO AMARILLO">   
                        out.print("<th style='background: #F5D942;'>");
                        out.print("<div class='' style='height: 11%;writing-mode: vertical-rl;transform: rotate(180deg);'>");
                        if (obj_instrumentos[38].equals("Interna")) {
                            if ((Integer) obj_TipoInst[9] == 0) {
                                out.print("<b style='color:#FFF'>Inspeccion</b>");
                            } else {
                                out.print("<b style='color:#FFF'>Verificacion</b>");
                            }
                        } else if (obj_instrumentos[38].equals("Externa")) {
                            if ((Integer) obj_TipoInst[9] == 0) {
                                out.print("<b style='color:#FFF'>Verificacion</b>");
                            } else {
                                out.print("<b style='color:#FFF'>Calibracion</b>");
                            }
                        } else if (obj_instrumentos[38].equals("Todos")) {
                            if ((Integer) obj_TipoInst[9] == 0) {
                                out.print("<b style='color:#FFF'>Inspeccion Verificacion</b>");
                            } else {
                                out.print("<b style='color:#FFF'>Verificacion Calibracion</b>");
                            }
                        }
                        out.print("</div>");
                        out.print("</th>");
                        out.print("<th class='filaLimited'>");
                        out.print("<div class='' style='align-items: center;height: 100px;'>");
                        out.print("<a href='#' style='color:#fff;text-decoration:none;'><b style='color:#000;'>" + obj_instrumentos[9] + "<br> " + Inst_Ubic[1] + "</b></a><br />");
                        out.print("</div>");
                        out.print("</th>");
                        //</editor-fold>
                    } 
                    if (temp == 1) {
                        out.print("<td>"
                                + "<b class='subTitle'>Tipo: </b>" + obj_instrumentos[4] + "<br>"
                                + "<b class='subTitle'>Instrumento: </b>" + Inst_Ubic[0] + "<br>"
                                + "<b class='subTitle'>Codigo: </b>" + obj_instrumentos[5] + ""
                                + "</td>");
                        if ((Integer) obj_TipoInst[9] == 0) {
                            if (Integer.parseInt(obj_instrumentos[32].toString()) > 0 && Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima Inspección: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima Inspección: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else if (Integer.parseInt(obj_instrumentos[32].toString()) > 0) {
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima Inspección: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima Inspección: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            } else if (Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            }
                        } else if ((Integer) obj_TipoInst[9] == 1) {
                            if (Integer.parseInt(obj_instrumentos[32].toString()) > 0 && Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima Calibración: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima Calibración: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else if (Integer.parseInt(obj_instrumentos[32].toString()) > 0) {
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima verificación: </b><br /> " + obj_instrumentos[18] + "<hr /><b class='subTitle'>Proxima verificación: </b><br /> " + obj_instrumentos[19] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[20] + "'>+/-(" + obj_instrumentos[23] + ")</b></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            } else if (Integer.parseInt(obj_instrumentos[33].toString()) > 0) {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td align='center' style='width:15%;'><b class='subTitle'>Ultima Calibración: </b><br /> " + obj_instrumentos[24] + "<hr /><b class='subTitle'>Proxima Calibración: </b><br /> " + obj_instrumentos[25] + "<b style='color:#000;' title='Fecha tolerancia: " + obj_instrumentos[26] + "'>+/-(" + obj_instrumentos[29] + ")</b></td>");
                            } else {
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                                out.print("<td valing='top' class='puntos' style='width:15%;'></td>");
                            }
                        }
                        out.print("<td><b class='subTitle'>Area: </b>" + obj_instrumentos[15] + "<br />"
                                + "<b class='subTitle'>Rango medida: </b> " + obj_instrumentos[10] + "<br />"
                                + "<b class='subTitle'>Observaciones: </b>" + obj_instrumentos[17] + ""
                                + "</td>");

//                        out.print("<td>");
//                        if ((Integer) obj_instrumentos[31] != 1) {
//                            out.print("<button class='btn btn-danger mb-1' onclick='Activarinstrumento(" + obj_instrumentos[0] + ")' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='fas fa-check-circle'></i></button><br>");
//                        } else {
//                            out.print("<button class='btn btn-success mb-1' onclick='Inactivarinstrumento(" + obj_instrumentos[0] + ")' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class=\"fas fa-check-circle\"></i></i></button><br>");
//                        }
//                        out.print("<a href='Instrumento_medicion?opc=3&idI=" + obj_instrumentos[0] + "&idTi=" + obj_instrumentos[1] + "&idTp=" + 1 + "&EvE=" + 0 + "&idV=" + 0 + "' class='btn btn-info mb-1' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Verificacion Instrumento'><img src='Interfaz/Contenido/assets/img/Doc2.fw.png' width='16px'></a><br>");
//                        out.print("<a href='Instrumento_medicion?opc=3&idI=" + obj_instrumentos[0] + "&idTi=" + obj_instrumentos[1] + "&idTp=" + 2 + "&idV=" + 0 + "' class='btn btn-warning' style='width: 41px;' data-toggle='tooltip' data-placement='top' title='Visualizar Ficha Tecnica'><i class='fas fa-eye'></i></a>");
//                        out.print("</td>");
                    }
                    temp = 0;
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

        } catch (Exception ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
