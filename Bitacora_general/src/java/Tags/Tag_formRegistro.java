package Tags;

import Controladoras.RegistroJpaController;
import Utilidades.PmpConexion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_formRegistro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        HttpSession sesion = pageContext.getSession();
        int idUsuario = Integer.parseInt(sesion.getAttribute("Identificacion").toString());
        String rol = sesion.getAttribute("Rol").toString();

        RegistroJpaController jpaRegistro = new RegistroJpaController();
        PmpConexion conn = new PmpConexion();
        List lstRegistro = null;
        List lstLinea = null;
        List lstEquipos = null;
        List lstEquiposId = null;
        List lstEjecuto = null;
        int id, count = 0, idDescPp, idDescColpitt = 0;
        int idRegistro, idLinea, idDescFalla = 0;
        int Temp = 0, Temp2 = 0;
        int idDescLinSell, idDescEquiBocas = 0;
        String idZona = "";
        Object[] campo, equipos = null;
        List lstLineaSellado = null;
        String[] equiSellado, descripcion, desc1 = null;
        String[] equiEnsamble = null;
        Object[] objVerifica = null;
        String idequipo = "";

        LocalDate fechaActual = LocalDate.now();

        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        try {
            try {
                idLinea = Integer.parseInt(pageContext.getRequest().getAttribute("idLinea").toString());
            } catch (Exception e) {
                idLinea = 0;
            }
            try {
                idRegistro = Integer.parseInt(pageContext.getRequest().getAttribute("idRegistro").toString());
            } catch (Exception e) {
                idRegistro = 0;
            }
            try {
                idDescFalla = Integer.parseInt(pageContext.getRequest().getAttribute("idDescFalla").toString());
            } catch (Exception e) {
                idDescFalla = 0;
            }
            try {
                idDescLinSell = Integer.parseInt(pageContext.getRequest().getAttribute("idDescLinSell").toString());
            } catch (Exception e) {
                idDescLinSell = 0;
            }
            try {
                idDescEquiBocas = Integer.parseInt(pageContext.getRequest().getAttribute("idDescEquiBocas").toString());
            } catch (Exception ex) {
                idDescEquiBocas = 0;
            }
            try {
                idDescPp = Integer.parseInt(pageContext.getRequest().getAttribute("idDescPp").toString());
            } catch (Exception ex) {
                idDescPp = 0;
            }
            try {
                idDescColpitt = Integer.parseInt(pageContext.getRequest().getAttribute("idDescColpitt").toString());
            } catch (Exception ex) {
                idDescColpitt = 0;
            }
            try {
                Temp = Integer.parseInt(pageContext.getRequest().getAttribute("Temp1").toString());
            } catch (Exception ex) {
                Temp = 0;
            }
            try {
                Temp2 = Integer.parseInt(pageContext.getRequest().getAttribute("Temp2").toString());
            } catch (Exception ex) {
                Temp2 = 0;
            }

            // Obtiene la informacion del registro segun su ID
            lstRegistro = jpaRegistro.consultarRegistrosPorID(idRegistro);
            if (lstRegistro != null) {
                Object[] objRegistro = (Object[]) lstRegistro.get(0);
                String dates = objRegistro[1].toString().replace("-", "");
                int date = Integer.parseInt(dates.toString());
                out.println("<div class='buscar'>");
                out.println("<span class='tooltip-container'>");
                out.println("<a href='Registro?op=1&Temp1=" + Temp + "'><i class=\"fas fa-reply fa-lg\"></i></a>");
                out.println("<div class=\"tooltip medida\">VOLVER</div>");
                out.println("</span>");
                if (objRegistro[12].equals(0)) {
                    out.println("<span class='tooltip-container' onclick=\"printSection('div-a-imprimir')\">");
                    out.println("<i class=\"fas fa-print fa-lg\" style='cursor:pointer'></i>");
                    out.println("<div class=\"tooltip medida6\">IMPRIMIR</div>");
                    out.println("</span>");
                } else {
                    out.println("<span class='tooltip-container'>");
                    out.println("<i onclick='confirmarCerrarRegistro(" + objRegistro[0] + ", " + Temp + ")' class=\"fas fa-unlock fa-lg\" style='cursor:pointer'></i>");
                    out.println("<div class=\"tooltip medida6\">CERRAR</div>");
                    out.println("</span>");
                }
                out.println("</div>");
                try {
                    idZona = objRegistro[3].toString().replace("][", ",").replace("[", "").replace("]", "");
                } catch (Exception e) {
                    idZona = "";
                }
                out.println("<div id=\"div-a-imprimir\">");
                //<editor-fold defaultstate="collapsed" desc="CABECERA">
                out.println("<div class='cabecera-registro espacio'>");
                out.println("<div class='cabecera-titulo color-fondo fuente-negrita'>COPIA NO CONTROLADA</div>");
                out.println("<div class='cabecera-titulo'><img src='Interfaz/Images/Logo.png' alt='Logo' width='200' title='logo-plastitec'></div>");
                out.println("<div class='cabecera-titulo'><span>REGISTRO</span></div>");
                out.println("<div class='cabecera-titulo'><span>CODIGO: R-MTF-011</span></div>");
                out.println("<div class='cabecera-titulo'><span style='text-align: center;' >REPORTE DE TRABAJO DE MANTENIMIENTO</span></div>");
                if (date >= 20260521) {
                    out.println("<div class='cabecera-titulo'><span>VERSION: 018</span></div>");
                } else if (date >= 20250415) {
                    out.println("<div class='cabecera-titulo'><span>VERSION: 017</span></div>");
                } else if (date >= 20241204) {
                    out.println("<div class='cabecera-titulo'><span>VERSION: 016</span></div>");
                } else {
                    out.println("<div class='cabecera-titulo'><span>VERSION: 015</span></div>");
                }

                out.println("<div class='cabecera-titulo'><span>FECHA:</span> " + objRegistro[1] + "</div>");
                out.println("<div class='cabecera-titulo'><span>TURNO:</span> " + objRegistro[2] + "</div>");
                String zonas = conn.consultaZonasId(idZona).toString().replace("[", "").replace("]", "");
                out.println("<div class='cabecera-titulo'><span>ZONA:</span> " + zonas + "</div>");
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LINEA Y DESCRIPCION FALLA">
                lstLinea = conn.consultaLineas(idZona); // CONSULTA LAS LINEAS DE PMP SEGUN EL ID DE LA ZONA
                String[] lineas = lstLinea.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                //<editor-fold defaultstate="collapsed" desc="MODAL FORMULARIO INGRESAR">
                out.println("<div id=\"modal\" class=\"modal\">");
                out.println("<div class=\"modal-content\" style='width: 30%;'>");
                out.println("<span class=\"close\" onclick=\"cerrarModal()\">&times;</span>");
                out.println("<form action='Registro?op=3' method='post'>");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='idDescFalla' id='contador' value=''>");
                out.println("<h2 class='centrar'>AGREGAR DESCRIPCION</h2>");
                out.println("<div>");
                out.println("<label class='titulo-label'>LINEA</label>");
                out.println("<div>");
                out.println("<select name='idLinea' required>");
                out.println("<option value=''>-- Seleccionar --</option>");
                for (int l = 0; l < lineas.length; l++) {
                    String[] linea = lineas[l].toString().split("---");
                    out.println("<option value='" + linea[0].trim() + "'>" + linea[1] + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("</div>");

                out.println("<div>");
                out.println("<label for='descripcion' class='espacio titulo-label'>DESCRIPCION</label>");
                out.println("<div><textarea id='descripcion' name='descFallaNueva' rows='5' cols='20' oninput='controlTexto()'></textarea></div>");
                out.println("</div>");
                out.println("<input type='submit' value='Enviar' class='espacio' id='botonAgregar' style='cursor:pointer'>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                if (idDescFalla > 0) {
                    //<editor-fold defaultstate="collapsed" desc="MODAL FOMULARIO EDITAR">
                    descripcion = objRegistro[4].toString().replace("][", "---").replace("[", "").replace("]", "").split("---");
                    out.println("<div style='display: block' class='modal'>");
                    out.println("<div class=\"modal-content\" style='width: 30%;'>");
                    out.println("<span><a class=\"close\" href='Registro?op=4&idRegistro=" + idRegistro + "'>&times;</a></span>");
                    out.println("<h2 class='centrar'>Editar Registro</h2>");
                    out.println("<form action='Registro?op=4' method='post'>");
                    out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] desc = descripcion[i].split("///");
                        if (desc[0].equals("" + idDescFalla)) {
                            out.println("<input type='hidden' name='idDescFalla' value='" + idDescFalla + "' >");
                            out.println("<div>");
                            out.println("<div><label class='titulo-label'>LINEA</label></div>");
                            out.println("<select name='idLineaNueva' required>");
                            out.println("<option value=''>-- Seleccionar --</option>");
                            for (int j = 0; j < lineas.length; j++) {
                                String[] lin = lineas[j].split("---");
                                if (desc[1].equals(lin[0].trim())) {
                                    out.println("<option value='" + lin[0].trim() + "' selected>" + lin[1] + "</option>");
                                } else {
                                    out.println("<option value='" + lin[0].trim() + "'>" + lin[1] + "</option>");
                                }
                            }
                            out.println("</select>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label for='desc' class='titulo-label'>DESCRIPCION</label>");
                            out.println("<div>");
                            out.println(!desc[2].equals(":") ? "<textarea id='desc' name='descFallaNueva' rows='5' cols='20' oninput='validarTexto()'>" + desc[2] + "</textarea>" : "<textarea id='desc' name='descFallaNueva' rows='5' cols='20' oninput='validarTexto()'></textarea>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<td class='centrar'><input type='hidden' name='descFalla' value='" + desc[2] + "'></td>");
                            out.println("<td class='centrar'><input type='hidden' name='idLinea' value='" + desc[1] + "'></td>");
                            out.println("<input type='submit' value='Editar' id='botonEditar' style='cursor:pointer'>");
                            out.println("</tr>");
                            break;
                        }
                    }
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</div>");
                    //</editor-fold>
                }
                out.println("<div " + ((objRegistro[12].equals(1)) ? "class='desc espacio sombreado'" : "class='desc-2 espacio sombreado'") + ">");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='idZona' value='" + idZona + "'>");
                out.println("<div class='campo fuente-negrita color-fondo'> LINEA </div>");
                out.println("<div class='campo fuente-negrita color-fondo'> DESCRIPCION DE LA FALLA Y SOLUCION ESPECIFICA TECNICA </div>");
                if (objRegistro[12].equals(1)) {
                    out.println("<div class='campo fuente-negrita color-fondo'> EDITAR </div>");
                    out.println("<div class='campo fuente-negrita color-fondo'> ELIMINAR </div>");
                }
                lstEquipos = conn.consultaZonaEquipo(idZona);
                if (lstEquipos != null) {
                    if (objRegistro[4] == null || objRegistro[4].equals("")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR SECCION 1 VACIA">
                        if (objRegistro[12].equals(1)) {
                            out.println("<div class='campo'>");
                            out.println("<i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"abrirModal(1)\"></i>");
                            out.println("</div>");
                            out.println("<div class='campo'></div><div class='campo'></div><div class='campo'></div>");
                        } else {
                            out.println("<div class='campo fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="TRAER SECCION 1 CON INFORMACION">
                        // CICLO FOR PARA RECORER LA DESCRIPCION Y OBTENER EL ID DE LA LINEA
                        descripcion = objRegistro[4].toString().replace("][", "---").replace("[", "").replace("]", "").split("---");
                        for (int i = 0; i < descripcion.length; i++) {
                            String[] desc = descripcion[i].split("///");
                            out.println("<div class='campo fuente-negrita'>");
                            lstEquiposId = conn.consultaLineasId(desc[1].trim());
                            if (lstEquiposId != null) {
                                String[] nombreEquipo = lstEquiposId.toString().replace("[", "").replace("]", "").replace("///", "").split("---");
                                out.println("<span>" + nombreEquipo[1] + "</span>");
                            }
                            out.println("</div>");
                            count = Integer.parseInt(desc[0].trim());
                            out.println(desc[2].equals(":") ? "<div class='campo relleno'> - </div>" : "<div class='campo relleno'>" + desc[2] + "</div>");
                            if (objRegistro[12].equals(1)) {
                                out.println("<div class='campo'><a class='boton-edit' href='Registro?op=1&idDescFalla=" + desc[0].trim() + "&idRegistro=" + idRegistro + "'><i class=\"fas fa-edit\"></i></a></div>");
                                out.println("<div class='campo'><span class='boton-delete' style='cursor:pointer' onclick=\"eliminarDescripcion('" + idRegistro + "', '" + desc[1].trim() + "', '" + desc[2] + "','" + desc[0].trim() + "');\"><i class=\"fas fa-minus\"></i></span></div>");
                            }
                            count++;
                        }
                        if (objRegistro[12].equals(1)) {
                            out.println("<div class='campo'>");
                            out.println("<i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"abrirModal(" + count + ")\"></i>");
                            out.println("</div>");
                            out.println("<div class='campo'></div><div class='campo'></div><div class='campo'></div>");
                        }
                        //</editor-fold>
                    }
                }
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FOMULARIO EDITAR DUCTO BOCAS">
                if (idDescEquiBocas > 0) {
                    out.println("<div style='display: block' class='modal'>");
                    out.println("<div class='modal-content' style='width: 30%;'>");
                    out.println("<span><a class=\"close\" href='Registro?op=11&idRegistro=" + idRegistro + "'>&times;</a></span>");
                    out.println("<h2>Editar Detección Ducto Bocas</h2>");
                    out.println("<form action='Registro?op=11' method='post'>");
                    out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                    descripcion = objRegistro[7].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                    lstEquipos = conn.consultaZonaEquipo(idZona);
                    String[] equiLinSell = lstEquipos.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] descBocas = descripcion[i].split("///");
                        String idDescBocas = descBocas[0].replace("-", "");
                        if (idDescBocas.equals("" + idDescEquiBocas)) {
                            out.println("<input type='hidden' name='idDescEquiBocas' value='" + idDescEquiBocas + "'>");
                            out.println("<div>");
                            out.println("<label for='hora' class='titulo-label'>HORA</label>");
                            out.println("<div><input type='time' id='hora' name='hora' value='" + descBocas[2] + "' required></div>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label class='titulo-label'>EQUIPO BOCAS</label>");
                            out.println("<div>");
                            out.println("<select name='idEquipo' required>");
                            out.println("<option value=''> -- Seleccionar -- </option>");
                            for (int j = 0; j < equiLinSell.length; j++) {
                                String[] equiDucBocas = equiLinSell[j].split("---");
                                if (descBocas[1].equals(equiDucBocas[0].trim())) {
                                    out.println("<option value='" + equiDucBocas[0].trim() + "' selected>" + equiDucBocas[1] + "</option>");
                                } else {
                                    out.println("<option value='" + equiDucBocas[0].trim() + "'>" + equiDucBocas[1] + "</option>");
                                }
                            }
                            out.println("</select>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label class='titulo-label'>ESTADO</label>");
                            out.println("<div class='radios'>");
                            out.println("<input type='radio' id='estado1' name='estado' value='Cumple' " + ((descBocas[3].equals("Cumple")) ? "checked" : "") + " ><label for='estado1' > Cumple </label>");
                            out.println("<input type='radio' id='estado2' name='estado' value='No-Cumple' " + ((descBocas[3].equals("No-Cumple")) ? "checked" : "") + "><label for='estado2' > No Cumple </label>");
                            out.println("<input type='radio' id='estado3' name='estado' value='N/A' " + ((descBocas[3].equals("N/A")) ? "checked" : "") + " ><label for='estado3' > N/A </label>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<input type='hidden' name='formato' value='" + descripcion[i] + "' >");
                        }
                    }
                    out.println("<input type='submit' value='Enviar' style='cursor:pointer'>");
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FOMULARIO EDITAR SELLADORA PP">
                if (idDescPp > 0) {
                    out.println("<div style='display: block' class='modal'>");
                    out.println("<div class='modal-content' style='width: 30%;'>");
                    out.println("<span><a class=\"close\" href='Registro?op=11&idRegistro=" + idRegistro + "'>&times;</a></span>");
                    out.println("<h2>EDITAR DESAFIO SISTEMA DETECCION PELICULA/DUCTO MAQUINAS SELLADORAS PP</h2>");
                    out.println("<form action='Registro?op=11' method='post'>");
                    out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                    descripcion = objRegistro[8].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                    lstEquipos = conn.consultaZonaEquipo(idZona);
                    String[] equiLinSell = lstEquipos.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] descPp = descripcion[i].split("///");
                        String idDescSellPp = descPp[0].replace("-", "");
                        if (idDescSellPp.equals("" + idDescPp)) {
                            out.println("<input type='hidden' name='idDescPp' value='" + idDescPp + "'>");
                            out.println("<div>");
                            out.println("<label for='hora' class='titulo-label'>HORA</label>");
                            out.println("<div><input type='time' id='hora' name='hora' value='" + descPp[2] + "' required></div>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label class='titulo-label'>EQUIPO SELLADORA PP</label>");
                            out.println("<div>");
                            out.println("<select name='idEquipo' required>");
                            out.println("<option value=''> -- Seleccionar -- </option>");
                            for (int j = 0; j < equiLinSell.length; j++) {
                                String[] equiSellPp = equiLinSell[j].split("---");
                                if (descPp[1].equals(equiSellPp[0].trim())) {
                                    out.println("<option value='" + equiSellPp[0].trim() + "' selected>" + equiSellPp[1] + "</option>");
                                } else {
                                    out.println("<option value='" + equiSellPp[0].trim() + "'>" + equiSellPp[1] + "</option>");
                                }
                            }
                            out.println("</select>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label class='titulo-label'>ESTADO</label>");
                            out.println("<div class='radios'>");
                            out.println("<input type='radio' id='estadoPp1' name='estado' value='Cumple' " + ((descPp[3].equals("Cumple")) ? "checked" : "") + " ><label for='estadoPp1' > Cumple </label>");
                            out.println("<input type='radio' id='estadoPp2' name='estado' value='No-Cumple' " + ((descPp[3].equals("No-Cumple")) ? "checked" : "") + "><label for='estadoPp2' > No Cumple </label>");
                            out.println("<input type='radio' id='estadoPp3' name='estado' value='N/A' " + ((descPp[3].equals("N/A")) ? "checked" : "") + " ><label for='estadoPp3' > N/A </label>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<input type='hidden' name='formato' value='" + descripcion[i] + "' >");
                        }
                    }
                    out.println("<input type='submit' value='Enviar' style='cursor:pointer'>");
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FOMULARIO EDITAR SELLADORA COLPITT">
                if (idDescColpitt > 0) {
                    out.println("<div style='display: block' class='modal'>");
                    out.println("<div class='modal-content' style='width: 30%;'>");
                    out.println("<span><a class=\"close\" href='Registro?op=11&idRegistro=" + idRegistro + "'>&times;</a></span>");
                    out.println("<h2>DESAFIO SISTEMA DETECCION DUCTO POR FUERA MAQUINAS SELLADORAS COLPITT</h2>");
                    out.println("<form action='Registro?op=11' method='post'>");
                    out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                    descripcion = objRegistro[9].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                    lstEquipos = conn.consultaZonaEquipo(idZona);
                    String[] equiColpitt = lstEquipos.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] descColpitt = descripcion[i].split("///");
                        String idDescSellColpitt = descColpitt[0].replace("-", "");
                        if (idDescSellColpitt.equals("" + idDescColpitt)) {
                            out.println("<input type='hidden' name='idDescColpitt' value='" + idDescColpitt + "'>");
                            out.println("<div>");
                            out.println("<label for='hora' class='titulo-label'>HORA</label>");
                            out.println("<div><input type='time' id='hora' name='hora' value='" + descColpitt[2] + "' required></div>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label class='titulo-label'>EQUIPO SELLADORA COLPITT</label>");
                            out.println("<div>");
                            out.println("<select name='idEquipo' required>");
                            out.println("<option value=''> -- Seleccionar -- </option>");
                            for (int j = 0; j < equiColpitt.length; j++) {
                                String[] equiSellPp = equiColpitt[j].split("---");
                                if (descColpitt[1].equals(equiSellPp[0].trim())) {
                                    out.println("<option value='" + equiSellPp[0].trim() + "' selected>" + equiSellPp[1] + "</option>");
                                } else {
                                    out.println("<option value='" + equiSellPp[0].trim() + "'>" + equiSellPp[1] + "</option>");
                                }
                            }
                            out.println("</select>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div>");
                            out.println("<label class='titulo-label'>ESTADO</label>");
                            out.println("<div class='radios'>");
                            out.println("<input type='radio' id='estadoC1' name='estado' value='Cumple' " + ((descColpitt[3].equals("Cumple")) ? "checked" : "") + " ><label for='estadoC1' > Cumple </label>");
                            out.println("<input type='radio' id='estadoC2' name='estado' value='No-Cumple' " + ((descColpitt[3].equals("No-Cumple")) ? "checked" : "") + "><label for='estadoC2' > No Cumple </label>");
                            out.println("<input type='radio' id='estadoC3' name='estado' value='N/A' " + ((descColpitt[3].equals("N/A")) ? "checked" : "") + " ><label for='estadoC3' > N/A </label>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<input type='hidden' name='formato' value='" + descripcion[i] + "' >");
                        }
                    }
                    out.println("<input type='submit' value='Enviar' style='cursor:pointer'>");
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO DUCTO BOCAS">
                out.println("<div id='modalEquBocas' class='modal'>");
                out.println("<div class='modal-content' style='width: 30%;'>");
                out.println("<span class='close' onclick='equipoBocasCerrar()'>&times;</span>");
                out.println("<form action='Registro?op=9' method='post'>");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='idDescBocas' id='countBocas' value='' >");
                out.println("<h2 class='centrar'> DESAFIO SISTEMA DETECCION DUCTO BOCAS	</h2>");
                out.println("<div>");
                out.println("<label>Hora</label>");
                out.println("<input type='time' name='hora' required>");
                out.println("</div>");
                out.println("<div>");
                out.println("<div><label class='titulo-label'>EQUIPO BOCAS</label></div>");
                out.println("<select name='idEquipo' required>");
                out.println("<option value=''>-- Seleccionar --</option>");
                lstEquipos = conn.consultaZonaEquipo(idZona);
                equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < equipos.length; i++) {
                    Object[] idEquiBoc = equipos[i].toString().replace(", ", "").split("---");
                    out.println("<option value='" + idEquiBoc[0] + "'> " + idEquiBoc[1] + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("<div>");
                out.println("<label class='titulo-label'>Estado</label>");
                out.println("<div class='radios'>");
                out.println("<input type='radio' id='estado1' name='estado' value='Cumple'><label for='estado1' > Cumple </label>");
                out.println("<input type='radio' id='estado2' name='estado' value='No-Cumple' ><label for='estado2' > No Cumple </label>");
                out.println("<input type='radio' id='estado3' name='estado' value='N/A' checked ><label for='estado3' > N/A </label>");
                out.println("</div>");
                out.println("</div>");
                out.println("<input type='submit' name='Enviar' style='cursor:pointer'>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO SELLADORA PP">
                out.println("<div id='modalEquPp' class='modal'>");
                out.println("<div class='modal-content' style='width: 30%;'>");
                out.println("<span class='close' onclick='equipoPpCerrar()'>&times;</span>");
                out.println("<form action='Registro?op=9' method='post'>");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='idDescPp' id='countPp' value='' >");
                out.println("<h2 class='centrar'>DESAFIO SISTEMA DETECCION PELICULA/DUCTO MAQUINAS SELLADORAS PP</h2>");
                out.println("<div>");
                out.println("<label>Hora</label>");
                out.println("<input type='time' name='hora' required>");
                out.println("</div>");
                out.println("<div>");
                out.println("<div><label class='titulo-label'>EQUIPO SELLADORAS PP</label></div>");
                out.println("<select name='idEquipo' required>");
                out.println("<option value=''>-- Seleccionar --</option>");
                lstEquipos = conn.consultaZonaEquipo(idZona);
                equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < equipos.length; i++) {
                    Object[] idEquipo = equipos[i].toString().replace(", ", "").split("---");
                    out.println("<option value='" + idEquipo[0] + "'> " + idEquipo[1] + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("<div>");
                out.println("<label class='titulo-label'>Estado</label>");
                out.println("<div class='radios'>");
                out.println("<input type='radio' id='estadoPp1' name='estado' value='Cumple'><label for='estadoPp1' > Cumple </label>");
                out.println("<input type='radio' id='estadoPp2' name='estado' value='No-Cumple' ><label for='estadoPp2' > No Cumple </label>");
                out.println("<input type='radio' id='estadoPp3' name='estado' value='N/A' checked ><label for='estadoPp3' > N/A </label>");
                out.println("</div>");
                out.println("</div>");
                out.println("<input type='submit' name='Enviar' style='cursor:pointer'>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO SELLADORA COLPITT">
                out.println("<div id='modalEquColpitt' class='modal'>");
                out.println("<div class='modal-content' style='width: 30%;'>");
                out.println("<span class='close' onclick='equipoColpittCerrar()'>&times;</span>");
                out.println("<form action='Registro?op=9' method='post'>");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='idDescColpitt' id='countColpitt' value='' >");
                out.println("<h2 class='centrar'>DESAFIO SISTEMA DETECCION DUCTO POR FUERA MAQUINAS SELLADORAS COLPITT</h2>");
                out.println("<div>");
                out.println("<label>Hora</label>");
                out.println("<input type='time' name='hora' required>");
                out.println("</div>");
                out.println("<div>");
                out.println("<div><label class='titulo-label'>EQUIPO SELLADORAS PP</label></div>");
                out.println("<select name='idEquipo' required>");
                out.println("<option value=''>-- Seleccionar --</option>");
                lstEquipos = conn.consultaZonaEquipo(idZona);
                equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < equipos.length; i++) {
                    Object[] idEquipo = equipos[i].toString().replace(", ", "").split("---");
                    out.println("<option value='" + idEquipo[0] + "'> " + idEquipo[1] + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("<div>");
                out.println("<label class='titulo-label'>Estado</label>");
                out.println("<div class='radios'>");
                out.println("<input type='radio' id='estadoC1' name='estado' value='Cumple'><label for='estadoC1' > Cumple </label>");
                out.println("<input type='radio' id='estadoC2' name='estado' value='No-Cumple' ><label for='estadoC2' > No Cumple </label>");
                out.println("<input type='radio' id='estadoC3' name='estado' value='N/A' checked ><label for='estadoC3' > N/A </label>");
                out.println("</div>");
                out.println("</div>");
                out.println("<input type='submit' name='Enviar' style='cursor:pointer'>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO FIRMA">
                out.println("<div id='modalFirma2' class='modal'>");
                out.println("<div class='modal-content' style='width: 20%;'>");
                out.println("<span class='close' onclick='firmaCerrar2()'>&times;</span>");
                out.println("<form action='Registro?op=13' method='post' id='firma3'>");
                out.println("<input type='text' name='idRegistro' value='" + idRegistro + "' hidden>");
                out.println("<input type='text' name='formatoAnti' id='formatoAnti' value='' hidden>");
                out.println("<input type='text' name='id' id='id2' value='' hidden>");
                out.println("<input type='text' name='camp1' id='camp1' value='' hidden>");
                out.println("<input type='text' name='camp2' id='camp2' value='' hidden>");
                out.println("<input type='text' name='tipoFirma' id='tipoFirma' value='' hidden>");
                out.println("<input type='text' name='camp3' id='camp3' value='' hidden>");
                out.println("<h2 class='centrar'> Firmar </h2>");
                out.println("<div>");
                out.println("<label for='numDoc' class='titulo-label'>Ingresar N° Documento</label>");
                out.println("<div><input type='number' name='numDoc' id='numDoc'></div>");
                out.println("</div>");
                out.println("<div>");
                out.println("<div><label for='codigo' class='titulo-label'>Ingresar Codigo</label></div>");
                out.println("<div><input type='number' name='codigo' id='codigo' ></div>");
                out.println("</div>");
                out.println("</form>");
                out.println("<input class='espacio' type='submit' value='Firmar' style='cursor:pointer' onclick='ConfirmarFirma2()'>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INSPECCION RUTINARIA LINEAS">
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO-INSPECCION-RUTINARIA-LINEAS">
                out.println("<div id=\"modalLineaSellado\" class=\"modal\">");
                out.println("<div class=\"modal-content\" style='width: 50%;'>");
                out.println("<span class=\"close\" onclick=\"lineaSelladoCerrar()\">&times;</span>");
                out.println("<form action='Registro?op=6' method='post'>");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='idLiSell' id='countLiSe' value=''>");
                out.println("<input type='hidden' name='fechaV' id='' value='" + date + "'>");
                out.println("<h2 class='centrar'>Lineas de Sellado</h2>");
                out.println("<div class='campos-modal'>");
                out.println("<div class='campo-modal'>");
                out.println("<label for='linea' class='titulo-label'>LINEA</label>");
                out.println("<select name='campo1' id='linea' class='lista' required>");
                out.println("<option value=''>-- Seleccionar --</option>");
                lstEquipos = conn.consultaZonaEquipo(idZona);
                equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < equipos.length; i++) {
                    Object[] idLiSellado = equipos[i].toString().replace(", ", "").split("---");
                    out.println("<option value='" + idLiSellado[0] + "'>" + idLiSellado[1] + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                if (date >= 20260521) {
                    //<editor-fold defaultstate="collapsed" desc="VERSION 2026-05-20">
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo2' class='titulo-label'>TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG</label>");
                    out.println("<div class='entrada'><input type='text' name='campo2' id='campo2' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo3' class='titulo-label'>SISTEMA CORTE DUCTO (CUCHILLAS)</label>");
                    out.println("<div class='entrada'><input type='text' name='campo3' id='campo3' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo4' class='titulo-label'>PRESION REFRIGERACION - GENERACION PSI</label>");
                    out.println("<div class='entrada'><input type='text' name='campo4' id='campo4' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo5' class='titulo-label'>PRESION NEUM./HCA. PRENSA BAR/PSI</label>");
                    out.println("<div class='entrada'><input type='text' name='campo5' id='campo5' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo6' class='titulo-label'>T° HORNO U.V °C <br/> / LED </label>");
                    out.println("<div class='entrada'><input type='text' name='campo6' id='campo6' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo7' class='titulo-label'>T° ELECTRODOS °C</label>");
                    out.println("<div class='entrada'><input type='text' name='campo7' id='campo7' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo8' class='titulo-label'>ESTADO MANDRILES</label>");
                    out.println("<div class='entrada'><input type='text' name='campo8' id='campo8' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo9' class='titulo-label'>VOLTAJE GRILLA</label>");
                    out.println("<div class='entrada'><input type='text' name='campo9' id='campo9' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo10' class='titulo-label'>SISTEMA CORTE DUCTO</label>");
                    out.println("<div class='entrada'><input type='text' name='campo10' id='campo10' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo11' class='titulo-label'>ALINEACION Y CENTRADO DE MANDRILES</label>");
                    out.println("<div class='entrada'><input type='text' name='campo11' id='campo11' oninput='controltextosellado260521()'></div>");
                    out.println("</div>");
                    //</editor-fold>
                } else if (date >= 20250415) {
                    //<editor-fold defaultstate="collapsed" desc="2025-04-15">
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo2' class='titulo-label'>TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG</label>");
                    out.println("<div class='entrada'><input type='text' name='campo2' id='campo2' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo3' class='titulo-label'>SISTEMA CORTE DUCTO (CUCHILLAS)</label>");
                    out.println("<div class='entrada'><input type='text' name='campo3' id='campo3' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo4' class='titulo-label'>PRESION REFRIGERACION - GENERACION PSI</label>");
                    out.println("<div class='entrada'><input type='text' name='campo4' id='campo4' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo5' class='titulo-label'>PRESION NEUM./HCA. PRENSA BAR/PSI</label>");
                    out.println("<div class='entrada'><input type='text' name='campo5' id='campo5' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo6' class='titulo-label'>T° HORNO U.V °C <br/> / LED </label>");
                    out.println("<div class='entrada'><input type='text' name='campo6' id='campo6' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo7' class='titulo-label'>T° ELECTRODOS °C</label>");
                    out.println("<div class='entrada'><input type='text' name='campo7' id='campo7' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo8' class='titulo-label'>ESTADO MANDRILES</label>");
                    out.println("<div class='entrada'><input type='text' name='campo8' id='campo8' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo9' class='titulo-label'>VOLTAJE GRILLA</label>");
                    out.println("<div class='entrada'><input type='text' name='campo9' id='campo9' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo10' class='titulo-label'>SISTEMA CORTE DUCTO</label>");
                    out.println("<div class='entrada'><input type='text' name='campo10' id='campo10' oninput='controltextosellado250414()'></div>");
                    out.println("</div>");

                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="ANTES">

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo2' class='titulo-label'>TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG</label>");
                    out.println("<div class='entrada'><input type='text' name='campo2' id='campo2' oninput='controltextosellado()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo3' class='titulo-label'>SISTEMA CORTE DUCTO (CUCHILLAS)</label>");
                    out.println("<div class='entrada'><input type='text' name='campo3' id='campo3' oninput='controltextosellado()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo4' class='titulo-label'>PRESION REFRIGERACION - GENERACION PSI</label>");
                    out.println("<div class='entrada'><input type='text' name='campo4' id='campo4' oninput='controltextosellado()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo5' class='titulo-label'>PRESION NEUM./HCA. PRENSA BAR/PSI</label>");
                    out.println("<div class='entrada'><input type='text' name='campo5' id='campo5' oninput='controltextosellado()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo6' class='titulo-label'>T° HORNO U.V °C <br/> / LED </label>");
                    out.println("<div class='entrada'><input type='text' name='campo6' id='campo6' oninput='controltextosellado()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo7' class='titulo-label'>T° ELECTRODOS °C</label>");
                    out.println("<div class='entrada'><input type='text' name='campo7' id='campo7' oninput='controltextosellado()'></div>");
                    out.println("</div>");
                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo8' class='titulo-label'>ESTADO MANDRILES</label>");
                    out.println("<div class='entrada'><input type='text' name='campo8' id='campo8' oninput='controltextosellado()'></div>");
                    out.println("</div>");

                    out.println("<div class='campo-modal'>");
                    out.println("<label for='campo9' class='titulo-label'>VOLTAJE GRILLA</label>");
                    out.println("<div class='entrada'><input type='text' name='campo9' id='campo9' oninput='controltextosellado()'></div>");
                    out.println("</div>");
                    //</editor-fold>
                }

                out.println("</div>");
                out.println("<input type='submit' id='subir' value='Enviar' style='cursor:pointer'>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                if (idDescLinSell > 0) {
                    //<editor-fold defaultstate="collapsed" desc="FORMULARIO EDITAR LINEAS DE SELLADO">

                    out.println("<div style='display: block' class='modal'>");
                    out.println("<div class=\"modal-content\" style='width: 50%;'>");
                    out.println("<span><a class=\"close\" href='Registro?op=7&idRegistro=" + idRegistro + "'>&times;</a></span>");
                    out.println("<h2 class='centrar'>Editar Linea de Sellado</h2>");
                    out.println("<form action='Registro?op=7' method='post'>");
                    out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                    out.println("<input type='hidden' name='fechaV' id='' value='" + date + "'>");
                    out.println("<div class='campos-modal'>");
                    lstEquipos = conn.consultaZonaEquipo(idZona);
                    String[] equiLinSell = lstEquipos.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                    descripcion = objRegistro[5].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");

                    for (int i = 0; i < descripcion.length; i++) {
                        String[] descLinSell = descripcion[i].split("///");
                        if (descLinSell[0].equals("" + idDescLinSell)) {
                            out.println("<input type='hidden' name='idDescNuevoLiSellado' value='" + idDescLinSell + "'>");
                            out.println("<div>");
                            out.println("<label for='linea' class='titulo-label'>LINEA</label>");
                            out.println("<select name='idEquLiSe' id='linea' class='lista' required>");
                            out.println("<option value=''> -- Seleccionar -- </option>");
                            for (int j = 0; j < equiLinSell.length; j++) {
                                String[] descLiSe = equiLinSell[j].split("---");
                                if (descLinSell[1].equals(descLiSe[0].trim())) {
                                    out.println("<option value='" + descLiSe[0].trim() + "' selected>" + descLiSe[1] + "</option>");
                                } else {
                                    out.println("<option value='" + descLiSe[0].trim() + "'>" + descLiSe[1] + "</option>");
                                }
                            }
                            out.println("</select>");
                            out.println("</div>");
                            if (date >= 20260521) {
                                //<editor-fold defaultstate="collapsed" desc="VERSION 2026-05-20">
                                out.println("<div>");
                                out.println("<label for='campo2' class='titulo-label'>TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG</label>");
                                out.println(!descLinSell[2].equals(":") ? "<input type='text' name='campo2' id='campoM2' oninput='verificacionTextoSellado260520()' value='" + descLinSell[2] + "'>" : "<input type='text' name='campo2' id='campoM2' oninput='verificacionTextoSellado260520()' value=''>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo3' class='titulo-label'>SISTEMA CORTE DUCTO (CUCHILLAS)</label>");
                                out.println(!descLinSell[3].equals(":") ? "<div class='entrada'><input type='text' name='campo3' id='campoM3' oninput='verificacionTextoSellado260520()' value='" + descLinSell[3] + "'></div>" : "<div class='entrada'><input type='text' name='campo3' id='campoM3' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo4' class='titulo-label'>PRESION REFRIGERACION - GENERACION PSI</label>");
                                out.println(!descLinSell[4].equals(":") ? "<div class='entrada'><input type='text' name='campo4' id='campoM4' oninput='verificacionTextoSellado260520()' value='" + descLinSell[4] + "'></div>" : "<div class='entrada'><input type='text' name='campo4' id='campoM4' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo5' class='titulo-label'>PRESION NEUM./HCA. PRENSA BAR/PSI</label>");
                                out.println(!descLinSell[5].equals(":") ? "<div class='entrada'><input type='text' name='campo5' id='campoM5' oninput='verificacionTextoSellado260520()' value='" + descLinSell[5] + "'></div>" : "<div class='entrada'><input type='text' name='campo5' id='campoM5' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo6' class='titulo-label'>T° HORNO U.V °C <br/> / LED </label>");
                                out.println(!descLinSell[6].equals(":") ? "<div class='entrada'><input type='text' name='campo6' id='campoM6' oninput='verificacionTextoSellado260520()' value='" + descLinSell[6] + "'></div>" : "<div class='entrada'><input type='text' name='campo6' id='campoM6' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo7' class='titulo-label'>T° ELECTRODOS °C</label>");
                                out.println(!descLinSell[7].equals(":") ? "<div class='entrada'><input type='text' name='campo7' id='campoM7' oninput='verificacionTextoSellado260520()' value='" + descLinSell[7] + "'></div>" : "<div class='entrada'><input type='text' name='campo7' id='campoM7' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo8' class='titulo-label'>ESTADO MANDRILES</label>");
                                out.println(!descLinSell[8].equals(":") ? "<div class='entrada'><input type='text' name='campo8' id='campoM8' oninput='verificacionTextoSellado260520()' value='" + descLinSell[8] + "'></div>" : "<div class='entrada'><input type='text' name='campo8' id='campoM8' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo9' class='titulo-label'>VOLTAJE GRILLA</label>");
                                out.println(!descLinSell[9].equals(":") ? "<div class='entrada'><input type='text' name='campo9' id='campoM9' oninput='verificacionTextoSellado260520()' value='" + descLinSell[9] + "'></div>" : "<div class='entrada'><input type='text' name='campo9' id='campoM9' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo10' class='titulo-label'>SISTEMA CORTE DUCTO</label>");
                                out.println(!descLinSell[10].equals(":") ? "<div class='entrada'><input type='text' name='campo10' id='campoM10' oninput='verificacionTextoSellado260520()' value='" + descLinSell[10] + "'></div>" : "<div class='entrada'><input type='text' name='campo10' id='campoM10' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo11' class='titulo-label'>ALINEACION Y CENTRADO DE MANDRILES</label>");
                                out.println(!descLinSell[11].equals(":") ? "<div class='entrada'><input type='text' name='campo11' id='campoM11' oninput='verificacionTextoSellado260520()' value='" + descLinSell[11] + "'></div>" : "<div class='entrada'><input type='text' name='campo11' id='campoM11' oninput='verificacionTextoSellado260520()' value=''></div>");
                                out.println("</div>");
                                //</editor-fold>
                            } else if (date >= 20250415) {
                                //<editor-fold defaultstate="collapsed" desc="NUEVA VERSION">
                                out.println("<div>");
                                out.println("<label for='campo2' class='titulo-label'>TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG</label>");
                                out.println(!descLinSell[2].equals(":") ? "<input type='text' name='campo2' id='campoM2' oninput='verificacionTextoSellado250414()' value='" + descLinSell[2] + "'>" : "<input type='text' name='campo2' id='campoM2' oninput='verificacionTextoSellado250414()' value=''>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo3' class='titulo-label'>SISTEMA CORTE DUCTO (CUCHILLAS)</label>");
                                out.println(!descLinSell[3].equals(":") ? "<div class='entrada'><input type='text' name='campo3' id='campoM3' oninput='verificacionTextoSellado250414()' value='" + descLinSell[3] + "'></div>" : "<div class='entrada'><input type='text' name='campo3' id='campoM3' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo4' class='titulo-label'>PRESION REFRIGERACION - GENERACION PSI</label>");
                                out.println(!descLinSell[4].equals(":") ? "<div class='entrada'><input type='text' name='campo4' id='campoM4' oninput='verificacionTextoSellado250414()' value='" + descLinSell[4] + "'></div>" : "<div class='entrada'><input type='text' name='campo4' id='campoM4' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo5' class='titulo-label'>PRESION NEUM./HCA. PRENSA BAR/PSI</label>");
                                out.println(!descLinSell[5].equals(":") ? "<div class='entrada'><input type='text' name='campo5' id='campoM5' oninput='verificacionTextoSellado250414()' value='" + descLinSell[5] + "'></div>" : "<div class='entrada'><input type='text' name='campo5' id='campoM5' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo6' class='titulo-label'>T° HORNO U.V °C <br/> / LED </label>");
                                out.println(!descLinSell[6].equals(":") ? "<div class='entrada'><input type='text' name='campo6' id='campoM6' oninput='verificacionTextoSellado250414()' value='" + descLinSell[6] + "'></div>" : "<div class='entrada'><input type='text' name='campo6' id='campoM6' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo7' class='titulo-label'>T° ELECTRODOS °C</label>");
                                out.println(!descLinSell[7].equals(":") ? "<div class='entrada'><input type='text' name='campo7' id='campoM7' oninput='verificacionTextoSellado250414()' value='" + descLinSell[7] + "'></div>" : "<div class='entrada'><input type='text' name='campo7' id='campoM7' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo8' class='titulo-label'>ESTADO MANDRILES</label>");
                                out.println(!descLinSell[8].equals(":") ? "<div class='entrada'><input type='text' name='campo8' id='campoM8' oninput='verificacionTextoSellado250414()' value='" + descLinSell[8] + "'></div>" : "<div class='entrada'><input type='text' name='campo8' id='campoM8' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo9' class='titulo-label'>VOLTAJE GRILLA</label>");
                                out.println(!descLinSell[9].equals(":") ? "<div class='entrada'><input type='text' name='campo9' id='campoM9' oninput='verificacionTextoSellado250414()' value='" + descLinSell[9] + "'></div>" : "<div class='entrada'><input type='text' name='campo9' id='campoM9' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo10' class='titulo-label'>SISTEMA CORTE DUCTO</label>");
                                out.println(!descLinSell[10].equals(":") ? "<div class='entrada'><input type='text' name='campo10' id='campoM10' oninput='verificacionTextoSellado250414()' value='" + descLinSell[10] + "'></div>" : "<div class='entrada'><input type='text' name='campo10' id='campoM10' oninput='verificacionTextoSellado250414()' value=''></div>");
                                out.println("</div>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="ANTES">
                                out.println("<div>");
                                out.println("<label for='campo2' class='titulo-label'>TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG</label>");
                                out.println(!descLinSell[2].equals(":") ? "<input type='text' name='campo2' id='campoM2' oninput='verificacionTextoSellado()' value='" + descLinSell[2] + "'>" : "<input type='text' name='campo2' id='campoM2' oninput='verificacionTextoSellado()' value=''>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo3' class='titulo-label'>SISTEMA CORTE DUCTO (CUCHILLAS)</label>");
                                out.println(!descLinSell[3].equals(":") ? "<div class='entrada'><input type='text' name='campo3' id='campoM3' oninput='verificacionTextoSellado()' value='" + descLinSell[3] + "'></div>" : "<div class='entrada'><input type='text' name='campo3' id='campoM3' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo4' class='titulo-label'>PRESION REFRIGERACION - GENERACION PSI</label>");
                                out.println(!descLinSell[4].equals(":") ? "<div class='entrada'><input type='text' name='campo4' id='campoM4' oninput='verificacionTextoSellado()' value='" + descLinSell[4] + "'></div>" : "<div class='entrada'><input type='text' name='campo4' id='campoM4' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo5' class='titulo-label'>PRESION NEUM./HCA. PRENSA BAR/PSI</label>");
                                out.println(!descLinSell[5].equals(":") ? "<div class='entrada'><input type='text' name='campo5' id='campoM5' oninput='verificacionTextoSellado()' value='" + descLinSell[5] + "'></div>" : "<div class='entrada'><input type='text' name='campo5' id='campoM5' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo6' class='titulo-label'>T° HORNO U.V °C <br/> / LED </label>");
                                out.println(!descLinSell[6].equals(":") ? "<div class='entrada'><input type='text' name='campo6' id='campoM6' oninput='verificacionTextoSellado()' value='" + descLinSell[6] + "'></div>" : "<div class='entrada'><input type='text' name='campo6' id='campoM6' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo7' class='titulo-label'>T° ELECTRODOS °C</label>");
                                out.println(!descLinSell[7].equals(":") ? "<div class='entrada'><input type='text' name='campo7' id='campoM7' oninput='verificacionTextoSellado()' value='" + descLinSell[7] + "'></div>" : "<div class='entrada'><input type='text' name='campo7' id='campoM7' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo8' class='titulo-label'>ESTADO MANDRILES</label>");
                                out.println(!descLinSell[8].equals(":") ? "<div class='entrada'><input type='text' name='campo8' id='campoM8' oninput='verificacionTextoSellado()' value='" + descLinSell[8] + "'></div>" : "<div class='entrada'><input type='text' name='campo8' id='campoM8' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                out.println("<div>");
                                out.println("<label for='campo9' class='titulo-label'>VOLTAJE GRILLA</label>");
                                out.println(!descLinSell[9].equals(":") ? "<div class='entrada'><input type='text' name='campo9' id='campoM9' oninput='verificacionTextoSellado()' value='" + descLinSell[9] + "'></div>" : "<div class='entrada'><input type='text' name='campo9' id='campoM9' oninput='verificacionTextoSellado()' value=''></div>");
                                out.println("</div>");
                                //</editor-fold>
                            }
                            out.println("</div>");
                            String desc = "";
                            if (date >= 20260521) {
                                desc = descLinSell[2] + "///" + descLinSell[3] + "///" + descLinSell[4] + "///" + descLinSell[5] + "///" + descLinSell[6]
                                        + "///" + descLinSell[7] + "///" + descLinSell[8] + "///" + descLinSell[9] + "///" + descLinSell[10] + "///" + descLinSell[11];
                            } else if (date >= 20250415) {
                                desc = descLinSell[2] + "///" + descLinSell[3] + "///" + descLinSell[4] + "///" + descLinSell[5] + "///" + descLinSell[6]
                                        + "///" + descLinSell[7] + "///" + descLinSell[8] + "///" + descLinSell[9] + "///" + descLinSell[10];
                            } else {
                                desc = descLinSell[2] + "///" + descLinSell[3] + "///" + descLinSell[4] + "///" + descLinSell[5] + "///" + descLinSell[6]
                                        + "///" + descLinSell[7] + "///" + descLinSell[8] + "///" + descLinSell[9];
                            }
                            out.println("<input type='hidden' name='desc' value='" + desc + "'>");
                            out.println("<input type='hidden' name='idEquiSell' value='" + descLinSell[1] + "'>");
                        }
                    }
                    out.println("<input type='submit' id='cambiar' value='Enviar' style='cursor:pointer'>");
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</div>");
                    //</editor-fold>
                }
                if (date >= 20260521) {
                    out.println("<div " + ((objRegistro[12].equals(1)) ? "class='sellado26520 espacio sombreado'" : "class='sellado-2 espacio sombreado'") + ">");
                } else if (date >= 20250415) {
                    out.println("<div " + ((objRegistro[12].equals(1)) ? "class='sellado25417 espacio sombreado'" : "class='sellado-2 espacio sombreado'") + ">");
                } else {
                    out.println("<div " + ((objRegistro[12].equals(1)) ? "class='sellado espacio sombreado'" : "class='sellado-2 espacio sombreado'") + ">");
                }
                out.println("<div class='titulo fuente-negrita color-fondo'>INSPECCION RUTINARIA DE LINEAS DE SELLADO</div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> EQUIPO </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> TIEMPOS SELLADO (ANILLOS - MANGA - FLAMEADO) SEG </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> SISTEMA CORTE DUCTO (CUCHILLAS) </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> PRESION REFRIGERACION - GENERACION PSI </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> PRESION NEUM./HCA. PRENSA BAR/PSI </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> T° HORNO U.V °C <br/> / LED  </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> T° ELECTRODOS °C </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> ESTADO MANDRILES </div>");
                out.println("<div class='titulo fuente-negrita color-fondo'> VOLTAJE GRILLA </div>");
                if (date >= 20250415) {
                    out.println("<div class='titulo fuente-negrita color-fondo'> SISTEMA CORTE DUCTO </div>");
                }
                if (date >= 20260521) {
                    out.println("<div class='titulo fuente-negrita color-fondo'> ALINEACION Y CENTRADO DE MANDRILES </div>");
                }
                if (objRegistro[12].equals(1)) {
                    out.println("<div class='titulo fuente-negrita color-fondo'> EDITAR </div>");
                    out.println("<div class='titulo fuente-negrita color-fondo'> ELIMINAR </div>");
                }
                lstLineaSellado = jpaRegistro.consultarInspeccionSelladoPorID(idRegistro);
                if (lstLineaSellado != null || !lstLineaSellado.isEmpty()) {
                    equiSellado = lstLineaSellado.toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                    if (!equiSellado[0].equals("")) {

                        for (int i = 0; i < equiSellado.length; i++) {
                            campo = equiSellado[i].split("///");
                            lstEquipos = conn.consultaEquipoId(campo[1].toString());
                            String[] lineasSellado = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                            for (int j = 0; j < lineasSellado.length; j++) {
                                Object[] EquSellado = lineasSellado[j].toString().replace(", ", "").split("---");
                                if (EquSellado[0].equals(campo[1])) {
                                    out.println("<div class='titulo fuente-negrita'>" + EquSellado[1] + "</div>");
                                }
                            }
                            out.println(campo[2].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[2] + "</div>");
                            out.println(campo[3].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[3] + "</div>");
                            out.println(campo[4].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[4] + "</div>");
                            out.println(campo[5].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[5] + "</div>");
                            out.println(campo[6].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[6] + "</div>");
                            out.println(campo[7].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[7] + "</div>");
                            out.println(campo[8].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[8] + "</div>");
                            out.println(campo[9].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[9] + "</div>");
                            if (date >= 20250415) {
                                out.println(campo[10].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[10] + "</div>");
                            }
                            if (date >= 20260521) {
                                out.println(campo[11].equals(":") ? "<div class='titulo'> - </div>" : "<div class='titulo'>" + campo[11] + "</div>");
                            }
                            String frase = "";
                            if (date >= 20260521) {
                                frase = campo[2].toString() + "///" + campo[3] + "///" + campo[4] + "///" + campo[5] + "///" + campo[6] + "///" + campo[7] + "///" + campo[8] + "///" + campo[9] + "///" + campo[10] + "///" + campo[11];
                            } else if (date >= 20250415) {
                                frase = campo[2].toString() + "///" + campo[3] + "///" + campo[4] + "///" + campo[5] + "///" + campo[6] + "///" + campo[7] + "///" + campo[8] + "///" + campo[9] + "///" + campo[10];
                            } else {
                                frase = campo[2].toString() + "///" + campo[3] + "///" + campo[4] + "///" + campo[5] + "///" + campo[6] + "///" + campo[7] + "///" + campo[8] + "///" + campo[9];
                            }
                            count = Integer.parseInt(campo[0].toString());
                            if (objRegistro[12].equals(1)) {
                                out.println("<div class='titulo'> <a class='boton-edit' href='Registro?op=1&idDescLinSell=" + campo[0].toString() + "&idRegistro=" + idRegistro + "&idZona=" + idZona + "'> <i class=\"fas fa-edit\"></i></a></div>");
                                out.println("<div class='titulo'> <span class='boton-delete' style='cursor:pointer' onclick=\"eliminarLineaSellado(" + idRegistro + "," + campo[1] + "," + campo[0] + ",'" + frase + "');\"> <i class=\"fas fa-minus\"></i> </span> </div>");
                            }
                            count++;
                        }

                        if (objRegistro[12].equals(1)) {
                            out.println("<div class='titulo fuente-negrita'>");
                            out.println("<i class=\"fas fa-plus\" style='cursor:pointer'onclick=\"lineaSellado(" + count + ")\"></i>");
                            out.println("</div>");
                            out.println("<div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div><div class='titulo'></div>");
                        }

                    } else {
                        if (objRegistro[12].equals(1)) {
                            out.println("<div class='titulo fuente-negrita'>");
                            out.println("<i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"lineaSellado(1)\"></i>");
                            out.println("</div>");
                            out.println(""
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>"
                                    + "<div class='titulo'></div>");
                        } else {
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        }
                    }
                }
                out.println("</div>");
                out.println("<div class='observacion'>");
                out.println("<strong>-VERIFICAR LA VELOCIDAD CIERRE PRENSA</strong>, EL CILINDRO INFERIOR DEBE LLEGAR PRIMERO QUE EL SUPERIOR AL MOMENTO DEL CIERRE.<br>");
                out.println("<strong> - EL TIEMPO DE FLAMEADO</strong> SE TOMARA EN LA LINEA SELLADO BOCAS EVA. <br>");
                out.println("-DATOS COMO PRESION Y TEMPERATURA SE TOMARAN EN LOS EQUIPOS QUE CUENTEN CON LOS INSTRUMENTOS PARA LA MEDICION. <br>");
                out.println("<strong>- VERIFICAR CUCHILLAS SISTEMA CORTE DUCTO</strong> INICIANDO EN MAQUINAS COLPITT, DESMONTAR Y REALIZAR PULIDO Y LIMPIEZA DE SER NECESARIO. GARANTIZAR CORRECTO FUNCIONAMIENTO DE CORTE DE DUCTO. <br>");
                out.println("<strong>- VERIFICAR ESTADO DE MANDRILES</strong> EN LAS LINEAS COLPITT ASEGURANDO QUE NO SE ENCUENTREN CON LA SUPERFICIE BRILLANTE.");
                if (date >= 20250415) {
                    out.println("<br><strong>- VERIFICAR SISTEMA CORTE DUCTO </strong> GARANTIZAR CORRECTO FUNCIONAMIENTO DEL SISTEMA CORTE DUCTO, REALIZAR LIMPIEZA AL SISTEMA INCLUYENDO LA RANURA DE CORTE DUCTO CON TRAPO EN CERRUTINA Y ALCOHOL DE SER NECESARIO.");
                }
                if (date >= 20260521) {
                    out.println("<br><strong>- VERIFICAR CENTRADO DE MANDRILES VS DADOS DE SELLADO ANILLOS EN LAS LINEAS PP, PARA GARANTIZAR SU CORRECTA POSICION DURANTE EL PROCESO DE FABRICACION DE BOLSAS.</strong>");
                }
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INSPECCION RUTINARIA EQUIPOS ENSAMBLE">
                //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO FIRMA EQUIPOS DE ENSAMBLE">
                out.println("<div id='modalFirma' class='modal' " + ((Temp2 == 1) ? "style='display:block;'" : "display:none;") + ">");
                out.println("<div class='modal-content' style='width: 20%;'>");
                out.println("<span class='close' onclick='firmaCerrar()'>&times;</span>");
                out.println("<form action='Registro?op=22' method='post' id='firma'>");
                out.println("<input type='text' name='idRegistro' value='" + idRegistro + "' hidden>");
                out.println("<input type='text' name='id' id='id' value='' hidden>");
                out.println("<h2 class='centrar'> Firmar </h2>");
                out.println("<div>");
                out.println("<label for='numDoc' class='titulo-label'>Ingresar N° Documento</label>");
                out.println("<div><input type='number' name='numDoc' id='numDoc'></div>");
                out.println("</div>");
                out.println("<div>");
                out.println("<div><label for='codigo' class='titulo-label'>Ingresar Codigo</label></div>");
                out.println("<div><input type='number' name='codigo' id='codigo' ></div>");
                out.println("</div>");
                out.println("</form>");
                out.println("<input class='espacio' type='submit' value='Firmar' style='cursor:pointer' onclick='ConfirmarFirma()'>");
                out.println("</div>");
                out.println("</div>");
                //</editor-fold>
                out.println("<form action='Registro?op=12' method='post' id='firma2'>");
                out.println("<div class='ensamble espacio sombreado'>");
                out.println("<input type='hidden' name='idRegistro' value='" + idRegistro + "'>");
                out.println("<input type='hidden' name='equEnsaAnt' value='" + objRegistro[6] + "'>");
                descripcion = objRegistro[6].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                String[] formato = objRegistro[6].toString().replace("][", "]_[").split("_");
                if (!objRegistro[6].equals("") || !objRegistro[6].toString().isEmpty()) {
                    out.println("<div class='campo-titulo color-fondo fuente-negrita relleno'>INSPECCION RUTINARIA EQUIPOS ENSAMBLE</div>");
                    out.println("<div class='campo-ensamble fuente-negrita color-fondo relleno'>EQUIPO</div>");
                    out.println("<div class='campo-ensamble fuente-negrita color-fondo relleno'>CELULOSAS</div>");
                    out.println("<div class='campo-ensamble fuente-negrita color-fondo relleno'>LIMPIEZA CICLON</div>");
                    out.println("<div class='campo-ensamble fuente-negrita color-fondo relleno'>FIRMA CALIDAD</div>");
                    //<editor-fold defaultstate="collapsed" desc="EQ-MA-01">
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-01</div>");
                    out.println("<input type='hidden' name='EQ-MA-01-3' value='" + descripcion[0] + "'>");
                    out.println("<input type='hidden' name='EQ-MA-01-4' value='" + descripcion[3] + "'>");
                    out.println("<input type='text' name='Temp2' id='Temp2' value='0' hidden>");

                    if (objRegistro[12].equals(1) && descripcion[3].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-01-1' name='EQ-MA-01-1' value='Cumple' " + ((descripcion[1].equals("Cumple")) ? "checked" : "") + " ><label for='EQ-MA-01-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-01-2' name='EQ-MA-01-1' value='No-Cumple' " + ((descripcion[1].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-01-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-01-3' name='EQ-MA-01-1' value='N/A' " + ((descripcion[1].equals("N/A")) ? "checked" : "") + " ><label for='EQ-MA-01-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-01-4' name='EQ-MA-01-2' value='Cumple' " + ((descripcion[2].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-01-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-01-5' name='EQ-MA-01-2' value='No-Cumple' " + ((descripcion[2].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-01-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-01-6' name='EQ-MA-01-2' value='N/A' " + ((descripcion[2].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-01-6'>N/A</label>");
                        out.println("</div>");
                        out.print("<div class='campo-ensamble fuente-negrita color-fondo'>");
                        out.print("<span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[0] + "'); Guardarinfo()\">" + descripcion[3] + "</span>");
                        out.print("</div>");

                    } else {
                        out.println("<input type='hidden' name='EQ-MA-01-1' value='" + descripcion[1] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-01-2' value='" + descripcion[2] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[1] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[2] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[3].equals("sin firma") ? "N/A" : "" + descripcion[3] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[0] + "', '" + descripcion[3] + "', 1)\">" + descripcion[3] + "</a>") + "</div>");
                    }

                    //</editor-fold>
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-02</div>");
                    out.println("<input type='hidden' name='EQ-MA-02-4' value='" + descripcion[7] + "'>");
                    out.println("<input type='hidden' name='EQ-MA-02-3' value='" + descripcion[4] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[7].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-02-1' name='EQ-MA-02-1' value='Cumple' " + ((descripcion[5].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-02-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-02-2' name='EQ-MA-02-1' value='No-Cumple' " + ((descripcion[5].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-02-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-02-3' name='EQ-MA-02-1' value='N/A' " + ((descripcion[5].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-02-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-02-4' name='EQ-MA-02-2' value='Cumple' " + ((descripcion[6].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-02-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-02-5' name='EQ-MA-02-2' value='No-Cumple' " + ((descripcion[6].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-02-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-02-6' name='EQ-MA-02-2' value='N/A' " + ((descripcion[6].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-02-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span onclick=\"guardarFirma('" + descripcion[4] + "'); Guardarinfo()\" style='cursor:pointer'>" + descripcion[7] + "</span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-MA-02-1' value='" + descripcion[5] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-02-2' value='" + descripcion[6] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[5] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[6] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[7].equals("sin firma") ? "N/A" : "" + descripcion[7] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[1] + "', '" + descripcion[7] + "', 1)\">" + descripcion[7] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-03</div>");
                    out.println("<input type='hidden' name='EQ-MA-03-3' value='" + descripcion[8] + "'>");
                    out.println("<input type='hidden' name='EQ-MA-03-4' value='" + descripcion[11] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[11].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-03-1' name='EQ-MA-03-1' value='Cumple' " + ((descripcion[9].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-03-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-03-2' name='EQ-MA-03-1' value='No-Cumple' " + ((descripcion[9].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-03-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-03-3' name='EQ-MA-03-1' value='N/A' " + ((descripcion[9].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-03-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-03-4' name='EQ-MA-03-2' value='Cumple' " + ((descripcion[10].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-03-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-03-5' name='EQ-MA-03-2' value='No-Cumple' " + ((descripcion[10].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-03-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-03-6' name='EQ-MA-03-2' value='N/A' " + ((descripcion[10].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-03-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[8] + "'); Guardarinfo()\"> " + descripcion[11] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-MA-03-1' value='" + descripcion[9] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-03-2' value='" + descripcion[10] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[9] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[10] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[11].equals("sin firma") ? "N/A" : "" + descripcion[11] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[2] + "', '" + descripcion[11] + "', 1)\">" + descripcion[11] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-04</div>");
                    out.println("<input type='hidden' name='EQ-MA-04-3' value='" + descripcion[12] + "'>");
                    out.println("<input type='hidden' name='EQ-MA-04-4' value='" + descripcion[15] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[15].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-04-1' name='EQ-MA-04-1' value='Cumple' " + ((descripcion[13].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-04-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-04-2' name='EQ-MA-04-1' value='No-Cumple' " + ((descripcion[13].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-04-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-04-3' name='EQ-MA-04-1' value='N/A' " + ((descripcion[13].equals("N/A")) ? "checked" : "") + " ><label for='EQ-MA-04-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-04-4' name='EQ-MA-04-2' value='Cumple' " + ((descripcion[14].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-04-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-04-5' name='EQ-MA-04-2' value='No-Cumple' " + ((descripcion[14].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-04-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-04-6' name='EQ-MA-04-2' value='N/A' " + ((descripcion[14].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-04-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[12] + "'); Guardarinfo()\"> " + descripcion[15] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-MA-04-1' value='" + descripcion[13] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-04-2' value='" + descripcion[14] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[13] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[14] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[15].equals("sin firma") ? "N/A" : "" + descripcion[15] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[3] + "', '" + descripcion[15] + "', 1)\">" + descripcion[15] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-05</div>");
                    out.println("<input type='hidden' name='EQ-MA-05-3' value='" + descripcion[16] + "'>");
                    out.println("<input type='hidden' name='EQ-MA-05-4' value='" + descripcion[19] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[19].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-05-1' name='EQ-MA-05-1' value='Cumple' " + ((descripcion[17].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-05-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-05-2' name='EQ-MA-05-1' value='No-Cumple' " + ((descripcion[17].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-05-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-05-3' name='EQ-MA-05-1' value='N/A' " + ((descripcion[17].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-05-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-05-4' name='EQ-MA-05-2' value='Cumple' " + ((descripcion[18].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-05-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-05-5' name='EQ-MA-05-2' value='No-Cumple' " + ((descripcion[18].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-05-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-05-6' name='EQ-MA-05-2' value='N/A' " + ((descripcion[18].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-05-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[16] + "'); Guardarinfo()\"> " + descripcion[19] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-MA-05-1' value='" + descripcion[17] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-05-2' value='" + descripcion[18] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[17] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[18] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[19].equals("sin firma") ? "N/A" : "" + descripcion[19] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[4] + "', '" + descripcion[19] + "', 1)\">" + descripcion[19] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-06</div>");
                    out.println("<input type='hidden' name='EQ-MA-06-3' value='" + descripcion[20] + "'>");
                    out.println("<input type='hidden' name='EQ-MA-06-4' value='" + descripcion[23] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[23].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-06-1' name='EQ-MA-06-1' value='Cumple' " + ((descripcion[21].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-06-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-06-2' name='EQ-MA-06-1' value='No-Cumple' " + ((descripcion[21].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-06-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-06-3' name='EQ-MA-06-1' value='N/A' " + ((descripcion[21].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-06-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-MA-06-4' name='EQ-MA-06-2' value='Cumple' " + ((descripcion[22].equals("Cumple")) ? "checked" : "") + " ><label for='EQ-MA-06-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-06-5' name='EQ-MA-06-2' value='No-Cumple' " + ((descripcion[22].equals("No-Cumple")) ? "checked" : "") + " ><label for='EQ-MA-06-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-MA-06-6' name='EQ-MA-06-2' value='N/A' " + ((descripcion[22].equals("N/A")) ? "checked" : "") + " ><label for='EQ-MA-06-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[20] + "'); Guardarinfo()\"> " + descripcion[23] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-MA-06-1' value='" + descripcion[21] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-06-2' value='" + descripcion[22] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[21] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[22] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[23].equals("sin firma") ? "N/A" : "" + descripcion[23] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[5] + "', '" + descripcion[23] + "', 1)\">" + descripcion[23] + "</a>") + "</div>");
                    }
                    if (date >= 20241204) {
                        out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-MA-07</div>");
                        out.println("<input type='hidden' name='EQ-MA-07-3' value='" + descripcion[40] + "'>");
                        out.println("<input type='hidden' name='EQ-MA-07-4' value='" + descripcion[43] + "'>");
                        if (objRegistro[12].equals(1) && descripcion[43].equals("sin firma")) {
                            out.println("<div class='campo-ensamble'>");
                            out.println("<input type='radio' id='EQ-MA-07-1' name='EQ-MA-07-1' value='Cumple' " + ((descripcion[41].equals("Cumple")) ? "checked" : "") + "><label for='EQ-MA-07-1'>Cumple</label>");
                            out.println("<input type='radio' id='EQ-MA-07-2' name='EQ-MA-07-1' value='No-Cumple' " + ((descripcion[41].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-MA-07-2'>No Cumple</label>");
                            out.println("<input type='radio' id='EQ-MA-07-3' name='EQ-MA-07-1' value='N/A' " + ((descripcion[41].equals("N/A")) ? "checked" : "") + "><label for='EQ-MA-07-3'>N/A</label>");
                            out.println("</div>");
                            out.println("<div class='campo-ensamble'>");
                            out.println("<input type='radio' id='EQ-MA-07-4' name='EQ-MA-07-2' value='Cumple' " + ((descripcion[42].equals("Cumple")) ? "checked" : "") + " ><label for='EQ-MA-07-4'>Cumple</label>");
                            out.println("<input type='radio' id='EQ-MA-07-5' name='EQ-MA-07-2' value='No-Cumple' " + ((descripcion[42].equals("No-Cumple")) ? "checked" : "") + " ><label for='EQ-MA-07-5'>No Cumple</label>");
                            out.println("<input type='radio' id='EQ-MA-07-6' name='EQ-MA-07-2' value='N/A' " + ((descripcion[42].equals("N/A")) ? "checked" : "") + " ><label for='EQ-MA-07-6'>N/A</label>");
                            out.println("</div>");
                            out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[40] + "'); Guardarinfo()\"> " + descripcion[43] + " </span></div>");
                        } else {
                            out.println("<input type='hidden' name='EQ-MA-07-1' value='" + descripcion[41] + "'>");
                            out.println("<input type='hidden' name='EQ-MA-07-2' value='" + descripcion[42] + "'>");
                            out.println("<div class='campo-ensamble'>" + descripcion[41] + "</div>");
                            out.println("<div class='campo-ensamble'>" + descripcion[42] + "</div>");
                            out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[43].equals("sin firma") ? "N/A" : "" + descripcion[43] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[10] + "', '" + descripcion[43] + "', 1)\">" + descripcion[43] + "</a>") + "</div>");
                        }
                    } else {

                    }

                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-GR-03</div>");
                    out.println("<input type='hidden' name='EQ-GR-03-3' value='" + descripcion[24] + "'>");
                    out.println("<input type='hidden' name='EQ-GR-03-4' value='" + descripcion[27] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[27].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-03-1' name='EQ-GR-03-1' value='Cumple' " + ((descripcion[25].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-03-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-03-2' name='EQ-GR-03-1' value='No-Cumple' " + ((descripcion[25].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-03-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-03-3' name='EQ-GR-03-1' value='N/A' " + ((descripcion[25].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-03-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-03-4' name='EQ-GR-03-2' value='Cumple' " + ((descripcion[26].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-03-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-03-5' name='EQ-GR-03-2' value='No-Cumple' " + ((descripcion[26].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-03-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-03-6' name='EQ-GR-03-2' value='N/A' " + ((descripcion[26].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-03-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[24] + "'); Guardarinfo()\"> " + descripcion[27] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-GR-03-1' value='" + descripcion[25] + "'>");
                        out.println("<input type='hidden' name='EQ-GR-03-2' value='" + descripcion[26] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[25] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[26] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[27].equals("sin firma") ? "N/A" : "" + descripcion[27] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[6] + "', '" + descripcion[27] + "', 1)\">" + descripcion[27] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-GR-04</div>");
                    out.println("<input type='hidden' name='EQ-GR-04-3' value='" + descripcion[28] + "'>");
                    out.println("<input type='hidden' name='EQ-GR-04-4' value='" + descripcion[31] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[31].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-04-1' name='EQ-GR-04-1' value='Cumple' " + ((descripcion[29].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-04-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-04-2' name='EQ-GR-04-1' value='No-Cumple' " + ((descripcion[29].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-04-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-04-3' name='EQ-GR-04-1' value='N/A' " + ((descripcion[29].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-04-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-04-4' name='EQ-GR-04-2' value='Cumple' " + ((descripcion[30].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-04-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-04-5' name='EQ-GR-04-2' value='No-Cumple' " + ((descripcion[30].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-04-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-04-6' name='EQ-GR-04-2' value='N/A' " + ((descripcion[30].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-04-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[28] + "'); Guardarinfo()\"> " + descripcion[31] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-GR-04-1' value='" + descripcion[29] + "'>");
                        out.println("<input type='hidden' name='EQ-GR-04-2' value='" + descripcion[30] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[29] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[30] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[31].equals("sin firma") ? "N/A" : "" + descripcion[31] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[7] + "', '" + descripcion[31] + "', 1)\">" + descripcion[31] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-GR-05</div>");
                    out.println("<input type='hidden' name='EQ-GR-05-3' value='" + descripcion[32] + "' >");
                    out.println("<input type='hidden' name='EQ-GR-05-4' value='" + descripcion[35] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[35].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-05-1' name='EQ-GR-05-1' value='Cumple' " + ((descripcion[33].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-05-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-05-2' name='EQ-GR-05-1' value='No-Cumple' " + ((descripcion[33].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-05-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-05-3' name='EQ-GR-05-1' value='N/A' " + ((descripcion[33].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-05-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-05-4' name='EQ-GR-05-2' value='Cumple' " + ((descripcion[34].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-05-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-05-5' name='EQ-GR-05-2' value='No-Cumple' " + ((descripcion[34].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-05-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-05-6' name='EQ-GR-05-2' value='N/A' " + ((descripcion[34].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-05-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[32] + "'); Guardarinfo()\"> " + descripcion[35] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-GR-05-1' value='" + descripcion[33] + "'>");
                        out.println("<input type='hidden' name='EQ-GR-05-2' value='" + descripcion[34] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[33] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[34] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[35].equals("sin firma") ? "N/A" : "" + descripcion[35] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[8] + "', '" + descripcion[35] + "', 1)\">" + descripcion[35] + "</a>") + "</div>");
                    }
                    out.println("<div class='campo-ensamble fuente-negrita relleno'>EQ-GR-06</div>");
                    out.println("<input type='hidden' name='EQ-GR-06-3' value='" + descripcion[36] + "'>");
                    out.println("<input type='hidden' name='EQ-GR-06-4' value='" + descripcion[39] + "'>");
                    if (objRegistro[12].equals(1) && descripcion[39].equals("sin firma")) {
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-06-1' name='EQ-GR-06-1' value='Cumple' " + ((descripcion[37].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-06-1'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-06-2' name='EQ-GR-06-1' value='No-Cumple' " + ((descripcion[37].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-06-2'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-06-3' name='EQ-GR-06-1' value='N/A' " + ((descripcion[37].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-06-3'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble'>");
                        out.println("<input type='radio' id='EQ-GR-06-4' name='EQ-GR-06-2' value='Cumple' " + ((descripcion[38].equals("Cumple")) ? "checked" : "") + "><label for='EQ-GR-06-4'>Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-06-5' name='EQ-GR-06-2' value='No-Cumple' " + ((descripcion[38].equals("No-Cumple")) ? "checked" : "") + "><label for='EQ-GR-06-5'>No Cumple</label>");
                        out.println("<input type='radio' id='EQ-GR-06-6' name='EQ-GR-06-2' value='N/A' " + ((descripcion[38].equals("N/A")) ? "checked" : "") + "><label for='EQ-GR-06-6'>N/A</label>");
                        out.println("</div>");
                        out.println("<div class='campo-ensamble fuente-negrita color-fondo'><span style='cursor:pointer' onclick=\"guardarFirma('" + descripcion[36] + "'); Guardarinfo()\"> " + descripcion[39] + " </span></div>");
                    } else {
                        out.println("<input type='hidden' name='EQ-GR-06-1' value='" + descripcion[37] + "'>");
                        out.println("<input type='hidden' name='EQ-GR-06-2' value='" + descripcion[38] + "'>");
                        out.println("<div class='campo-ensamble'>" + descripcion[37] + "</div>");
                        out.println("<div class='campo-ensamble'>" + descripcion[38] + "</div>");
                        out.println("<div class='campo-ensamble color-fondo'> " + ((objRegistro[12].equals(0)) ? "<p class='firma'> " + (descripcion[39].equals("sin firma") ? "N/A" : "" + descripcion[39] + "") + "</p>" : "<a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ", '" + formato[9] + "', '" + descripcion[39] + "', 1)\">" + descripcion[39] + "</a>") + "</div>");
                    }
                    out.println("</div>");
                    out.println("<div class='observacion'><strong>NOTA:</strong> PARA LOS EQUIPOS DE ENSAMBLE DE MARIPOSAS :1 ( trabajando con cùpula) 2,3 SE DEBE REALIZAR EL CAMBIO DE CELULOSAS IMPREGNACION ALCOHOL CADA 2 DÍAS DESDE EL INICIO DE PRODUCCIÓN (Comenzando desde el domingo o lunes festivo ) <strong>TENER SIEMPRE PRESENTES LAS NORMAS DE SEGURIDAD.</strong></div>");
                } else {
                    out.println("<div><strong>ERROR NO EXISTE EL FORMATO DE INSPECCION RUTINARIA EQUIPOS ENSAMBLE</strong></div>");
                    out.println("<div><a href='Registro?op=15&idRegistro=" + idRegistro + "'>REESTABLECER FORMATO</a></div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TABLA DETECCION DUCTO BOCAS">
                descripcion = objRegistro[7].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                out.println("<div class='campo-bocas-titulo color-fondo fuente-negrita espacio'>DESAFIO SISTEMA DETECCION DUCTO MAQUINAS BOCAS</div>");
                out.println("<div " + ((objRegistro[12].equals(1)) ? "class='bocas sombreado'" : "class='bocas-2 sombreado'") + " >");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>HORA</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>EQUIPO</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>ESTADO</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>FIRMA CALIDAD</div>");
                if (objRegistro[12].equals(1)) {
                    out.println("<div class='campo-bocas color-fondo fuente-negrita'>EDITAR</div>");
                    out.println("<div class='campo-bocas color-fondo fuente-negrita'>ELIMINAR</div>");
                }
                if (objRegistro[7] != "" || !objRegistro[7].toString().isEmpty()) {
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] desc = descripcion[i].split("///");
                        out.println("<div class='campo-bocas'>" + desc[2] + "</div>");
                        lstEquipos = conn.consultaEquipoId(desc[1]);
                        equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                        for (int j = 0; j < equipos.length; j++) {
                            String[] idDucBoc = equipos[j].toString().replace(", ", "").split("---");
                            if (idDucBoc[0].equals(desc[1].trim())) {
                                out.println("<div class='campo-bocas'> " + idDucBoc[1] + " </div>");
                            }
                        }
                        count = Integer.parseInt(desc[0]);
                        out.println("<div class='campo-bocas'>" + desc[3] + "</div>");
                        if (objRegistro[12].equals(1) && desc[4].equals("sin firma")) {
                            out.println("<div class='campo-bocas fuente-negrita'><strong style='cursor:pointer' onclick=\"guardarFirma2('" + descripcion[i] + "', '" + desc[0] + "', '" + desc[1] + "', '" + desc[2] + "',4,'" + desc[3] + "')\">" + desc[4] + "</strong> </div>");
                            out.println("<div class='campo-bocas'> <a class='boton-edit' href='Registro?op=1&idDescEquiBocas=" + desc[0] + "&idRegistro=" + idRegistro + "&idZona=" + idZona + "'> <i class=\"fas fa-edit\"></i></a></div>");
                            out.println("<div class='campo-bocas'> <span class='boton-delete' style='cursor:pointer' onclick=\"eliminarItem(" + idRegistro + ",'" + descripcion[i] + "',1);\"><i class=\"fas fa-minus\"></i></span> </div>");
                        } else {
                            if (objRegistro[12].equals(1)) {
                                out.println("<div class='campo-bocas'><a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ",'" + descripcion[i] + "','" + desc[4] + "', 2)\">" + desc[4] + "</a></div>");
                                out.println("<div class='campo-bocas'><i class=\"fas fa-ban fa-lg\"></i></div>");
                                out.println("<div class='campo-bocas'><i class=\"fas fa-ban fa-lg\"></i></div>");
                            } else {
                                out.println("<div class='campo-bocas color-fondo fuente-negrita firma'>" + desc[4] + "</div>");
                            }
                        }
                        count++;
                    }
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='campo-bocas'><i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"equipoBocasGuardar(" + count + ")\"></i></div>");
                        out.println("<div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div>");
                    }
                } else {
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='campo-bocas'><i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"equipoBocasGuardar(1)\"></i></div>");
                        out.println("<div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div>");
                    } else {
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                    }
                }
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MAQUINA SELLADORA PP">
                descripcion = objRegistro[8].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                out.println("<div class='campo-bocas-titulo color-fondo fuente-negrita espacio'>DESAFIO SISTEMA DETECCION PELICULA/DUCTO MAQUINAS SELLADORAS PP</div>");
                out.println("<div " + ((objRegistro[12].equals(1)) ? "class='bocas sombreado'" : "class='bocas-2 sombreado'") + " >");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>HORA</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>EQUIPO</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>ESTADO</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>FIRMA CALIDAD</div>");
                if (objRegistro[12].equals(1)) {
                    out.println("<div class='campo-bocas color-fondo fuente-negrita'>EDITAR</div>");
                    out.println("<div class='campo-bocas color-fondo fuente-negrita'>ELIMINAR</div>");
                }
                if (objRegistro[8] != "" || !objRegistro[8].toString().isEmpty()) {
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] desc = descripcion[i].split("///");
                        out.println("<div class='campo-bocas'>" + desc[2] + "</div>");
                        lstEquipos = conn.consultaEquipoId(desc[1]);
                        equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                        for (int j = 0; j < equipos.length; j++) {
                            String[] idDucBoc = equipos[j].toString().replace(", ", "").split("---");
                            if (idDucBoc[0].equals(desc[1].trim())) {
                                out.println("<div class='campo-bocas'> " + idDucBoc[1] + " </div>");
                            }
                        }
                        out.println("<div class='campo-bocas'>" + desc[3] + "</div>");
                        count = Integer.parseInt(desc[0]);
                        if (objRegistro[12].equals(1) && desc[4].equals("sin firma")) {
                            out.println("<div class='campo-bocas fuente-negrita'><strong style='cursor:pointer' onclick=\"guardarFirma2('" + descripcion[i] + "', '" + desc[0] + "', '" + desc[1] + "', '" + desc[2] + "',5,'" + desc[3] + "')\">" + desc[4] + "</strong> </div>");
                            out.println("<div class='campo-bocas'><a class='boton-edit' href='Registro?op=1&idDescPp=" + desc[0] + "&idRegistro=" + idRegistro + "&idZona=" + idZona + "'> <i class=\"fas fa-edit\"></i></a></div>");
                            out.println("<div class='campo-bocas'><span class='boton-delete' style='cursor:pointer' onclick=\"eliminarItem(" + idRegistro + ",'" + descripcion[i] + "', 2);\"><i class=\"fas fa-minus\"></i></span> </div>");
                        } else {
                            if (objRegistro[12].equals(1)) {
                                out.println("<div class='campo-bocas fuente-negrita'><a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ",'" + descripcion[i] + "','" + desc[4] + "', 3)\">" + desc[4] + "</a></div>");
                                out.println("<div class='campo-bocas'><i class=\"fas fa-ban fa-lg\"></i></div>");
                                out.println("<div class='campo-bocas'><i class=\"fas fa-ban fa-lg\"></i></div>");
                            } else {
                                out.println("<div class='campo-bocas color-fondo fuente-negrita firma'>" + desc[4] + "</div>");
                            }
                        }
                        count++;
                    }
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='campo-bocas'><i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"equipoPpGuardar(" + count + ")\"></i></div>");
                        out.println("<div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div>");
                    }
                } else {
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='campo-bocas'><i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"equipoPpGuardar(1)\"></i></div>");
                        out.println("<div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div>");
                    } else {
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                    }
                }
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INSPECCION RUTINARIO COLPITT">
                descripcion = objRegistro[9].toString().replace("][", "_").replace("[", "").replace("]", "").split("_");
                out.println("<div class='campo-bocas-titulo color-fondo fuente-negrita espacio'>DESAFIO SISTEMA DETECCION DUCTO POR FUERA MAQUINAS SELLADORAS COLPITT</div>");
                out.println("<div " + ((objRegistro[12].equals(1)) ? "class='bocas sombreado'" : "class='bocas-2 sombreado'") + " >");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>HORA</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>EQUIPO</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>ESTADO</div>");
                out.println("<div class='campo-bocas color-fondo fuente-negrita'>FIRMA CALIDAD</div>");
                if (objRegistro[12].equals(1)) {
                    out.println("<div class='campo-bocas color-fondo fuente-negrita'>EDITAR</div>");
                    out.println("<div class='campo-bocas color-fondo fuente-negrita'>ELIMINAR</div>");
                }
                if (objRegistro[9] != "" || !objRegistro[9].toString().isEmpty()) {
                    for (int i = 0; i < descripcion.length; i++) {
                        String[] desc = descripcion[i].split("///");
                        out.println("<div class='campo-bocas'>" + desc[2] + "</div>");
                        lstEquipos = conn.consultaEquipoId(desc[1]);
                        equipos = lstEquipos.toString().replace("[", "").replace("]", "").split("///");
                        for (int j = 0; j < equipos.length; j++) {
                            String[] idDucBoc = equipos[j].toString().replace(", ", "").split("---");
                            if (idDucBoc[0].equals(desc[1].trim())) {
                                out.println("<div class='campo-bocas'> " + idDucBoc[1] + " </div>");
                            }
                        }
                        count = Integer.parseInt(desc[0]);
                        out.println("<div class='campo-bocas'>" + desc[3] + "</div>");
                        if (objRegistro[12].equals(1) && desc[4].equals("sin firma")) {
                            out.println("<div class='campo-bocas fuente-negrita'><strong style='cursor:pointer' onclick=\"guardarFirma2('" + descripcion[i] + "', '" + desc[0] + "', '" + desc[1] + "', '" + desc[2] + "',6,'" + desc[3] + "')\">" + desc[4] + "</strong> </div>");
                            out.println("<div class='campo-bocas'><a class='boton-edit' href='Registro?op=1&idDescColpitt=" + desc[0] + "&idRegistro=" + idRegistro + "&idZona=" + idZona + "'> <i class=\"fas fa-edit\"></i></a></div>");
                            out.println("<div class='campo-bocas'><span class='boton-delete' style='cursor:pointer' onclick=\"eliminarItem(" + idRegistro + ",'" + descripcion[i] + "', 3);\"><i class=\"fas fa-minus\"></i></span> </div>");
                        } else {
                            if (objRegistro[12].equals(1)) {
                                out.println("<div class='campo-bocas'><a class='firma' style='cursor:pointer' onclick=\"eliminarFirmaItem(" + idRegistro + ",'" + descripcion[i] + "','" + desc[4] + "',4)\">" + desc[4] + "</a></div>");
                                out.println("<div class='campo-bocas'><i class=\"fas fa-ban fa-lg\"></i></div>");
                                out.println("<div class='campo-bocas'><i class=\"fas fa-ban fa-lg\"></i></div>");
                            } else {
                                out.println("<div class='campo-bocas color-fondo fuente-negrita firma'>" + desc[4] + "</div>");
                            }
                        }
                        count++;
                    }
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='campo-bocas'><i class=\"fas fa-plus\"  style='cursor:pointer' onclick=\"equipoColpittGuardar(" + count + ")\"></i></div>");
                        out.println("<div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div>");
                    }
                } else {
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='campo-bocas'><i class=\"fas fa-plus\" style='cursor:pointer' onclick=\"equipoColpittGuardar(1)\"></i></div>");
                        out.println("<div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div> <div class='campo-bocas'></div>");
                    } else {
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                    }
                }
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="OBSEVACIONES DE VERIFICACION">
                out.println("<div class='observacion-pp espacio'>");
                out.println("<div class='pp centrar'>OBSERVACIONES DE LA VERIFICACION</div>");
                out.println("<div class='pp'><strong>NOTA: REALIZAR EL DESAFIO A SISTEMAS DETECCION PELICULA Y DUCTOS PARA GARANTIZAR SU CORRECTO FUNCIONAMIENTO. EN CASO DE NO CUMPLIMIENTO RELIZAR LOS AJUSTES NECESARIOS</strong>.</div>");
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES GENERAL">
                out.println("<div class='observacion-pp espacio'>");
                out.println("<div class='pp centrar'>OBSERVACIONES GENERALES</div>");
                //<editor-fold defaultstate="collapsed" desc="PERMISOS ROLES">
                if (rol.equals("TECNICO MANTENIMIENTO FARMACEUTICO")) {
                    if (objRegistro[12].equals(0)) {
                        if (objRegistro[13].equals("")) {
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        } else {
                            out.println("<div class='observacion-pp pp relleno'>" + objRegistro[13] + "</div>");
                        }
                    } else {
                        out.println("<div class='observacion-pp pp relleno' contenteditable='true' id=\"div-editable\">" + objRegistro[13] + "</div>");
                        out.println("<input type='text' id=\"input-text\" name='observacion' value='" + objRegistro[13] + "' hidden>");
                    }
                } else if (rol.equals("COORDINADOR MANTENIMIENTO FARMACEUTICO")) {
                    if (objRegistro[10] != null) {
                        if (objRegistro[13].equals("")) {
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        } else {
                            out.println("<div class='observacion-pp pp relleno'>" + objRegistro[13] + "</div>");
                        }
                    } else if (Integer.parseInt(objRegistro[15].toString()) == 29 || Integer.parseInt(objRegistro[15].toString()) == 27) {
                        if (idUsuario == Integer.parseInt(objRegistro[11].toString()) || Integer.parseInt(objRegistro[15].toString()) == 29) {
                            out.println("<div class='observacion-pp pp relleno' contenteditable='true' id=\"div-editable\" oninput='updateTextInput()'>" + objRegistro[13] + "</div>");
                            out.println("<input type='text' id=\"input-text\" name='observacion' value='" + objRegistro[13] + "' hidden>");
                        } else {
                            if (objRegistro[13].equals("")) {
                                out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                            } else {
                                out.println("<div class='observacion-pp pp relleno'>" + objRegistro[13] + "</div>");
                            }
                        }
                    }
                } else if (rol.equals("JEFE MANTENIMIENTO FARMACEUTICO") || rol.equals("ADMINISTRADOR")) {
                    if (objRegistro[10] != null) {
                        if (objRegistro[13].equals("")) {
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        } else {
                            out.println("<div class='observacion-pp pp relleno'>" + objRegistro[13] + "</div>");
                        }
                    } else if (Integer.parseInt(objRegistro[15].toString()) == 29 || Integer.parseInt(objRegistro[15].toString()) == 27) {
                        out.println("<div class='observacion-pp pp relleno' contenteditable='true' id=\"div-editable\" oninput='updateTextInput()'>" + objRegistro[13] + "</div>");
                        out.println("<input type='text' id=\"input-text\" name='observacion' value='" + objRegistro[13] + "' hidden>");
                    }
                } else {
                    if (objRegistro[10] != null) {
                        if (objRegistro[13].equals("")) {
                            out.println("<div class='campo-bocas fuente-negrita linea'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>");
                        } else {
                            out.println("<div class='observacion-pp pp relleno'>" + objRegistro[13] + "</div>");
                        }
                    } else {
                        out.println("<div class='observacion-pp pp relleno' contenteditable='false' id=\"div-editable\" oninput='updateTextInput()'>" + objRegistro[13] + "</div>");
                        out.println("<input type='text' id=\"input-text\" name='observacion' value='" + objRegistro[13] + "' hidden readonly>");
                    }
                }
                //</editor-fold>
                out.println("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FIRMAS">
                out.println("<div class='espacio'>");
                lstEjecuto = jpaRegistro.responsableRegistro(idRegistro);
                objVerifica = (Object[]) lstEjecuto.get(0);
                out.println("<div class='firma-campo'><strong>EJECUTO:</strong> <span>" + objVerifica[0] + " " + objVerifica[1] + " - " + objVerifica[3] + "</span></div>");
                if (objRegistro[10] != null) {
                    lstEjecuto = jpaRegistro.responsableVerifica(idRegistro);
                    objVerifica = (Object[]) lstEjecuto.get(0);
                    if (objRegistro[12].equals(1)) {
                        out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span style='cursor:pointer' onclick=\"confirmarFirmaJefe(" + idUsuario + " ," + idRegistro + ");\">" + (objVerifica[0] + " " + objVerifica[1] + " - " + objVerifica[3] + " - " + objRegistro[14]) + "</span> </div>");
                    } else {
                        out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span>" + (objVerifica[0] + " " + objVerifica[1] + " - " + objVerifica[3] + " - " + objRegistro[14].toString().substring(0, 7)) + "</span> </div>");
                        //<editor-fold defaultstate="collapsed" desc="CAMBIO SOLICITADO POR MTF QUITAR DIA Y HORAS - ANTES">
//                        out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span>" + (objVerifica[0] + " " + objVerifica[1] + " - " + objVerifica[3] + " - " + objRegistro[14]) + "</span> </div>");
//</editor-fold>

                    }
                } else {
                    if (objRegistro[12].equals(0)) {
                        if (rol.equals("JEFE MANTENIMIENTO FARMACEUTICO")) {
                            out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span style='cursor:pointer' onclick=\"confirmarFirmaJefe(" + idUsuario + " ," + idRegistro + ");\"> SIN FIRMA </span></div>");
                        } else if (rol.equals("COORDINADOR MANTENIMIENTO FARMACEUTICO")) {
                            if (objVerifica[4].equals("TECNICO MANTENIMIENTO FARMACEUTICO")) {
                                out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span style='cursor:pointer' onclick=\"confirmarFirmaJefe(" + idUsuario + " ," + idRegistro + ");\"> SIN FIRMA </span></div>");
                            } else {
                                out.println("<div class='firma-campo'><strong>VERIFICO: </strong> SIN FIRMA </div>");
                            }
                        } else {
                            out.println("<div class='firma-campo'><strong>VERIFICO: </strong> SIN FIRMA </span></div>");
                        }
                    } else {
                        if (rol.equals("JEFE MANTENIMIENTO FARMACEUTICO")) {
                            out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span style='cursor:pointer' onclick=\"confirmarFirmaJefe(" + idUsuario + " ," + idRegistro + ");\">SIN FIRMA</span> </div>");
                        } else if (rol.equals("COORDINADOR MANTENIMIENTO FARMACEUTICO")) {
                            if (objVerifica[4].equals("TECNICO MANTENIMIENTO FARMACEUTICO")) {
                                out.println("<div class='firma-campo'><strong>VERIFICO: </strong> <span style='cursor:pointer' onclick=\"confirmarFirmaJefe(" + idUsuario + " ," + idRegistro + ");\">SIN FIRMA</span> </div>");
                            } else {
                                out.println("<div class='firma-campo'><strong>VERIFICO: </strong> SIN FIRMA </div>");
                            }
                        } else {
                            out.println("<div class='firma-campo'><strong>VERIFICO: </strong> SIN FIRMA </div>");
                        }
                    }
                }
                out.println("</div>");
                if (objRegistro[12].equals(1)) {
                    out.println("<span class='boton-flotante'> <button onclick=\"pasarTexto(document.getElementById('div-editable'), document.getElementById('input-text'))\" id='Guardar'>Guardar</button> </span>");
                }
                //</editor-fold>
                out.println("</form>");
                if (date >= 20250415) {
                    out.println("<div class='protectdata'>");
                    out.println("<div class='pp centrar'>La información personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales</div>");
                    out.println("</div>");
                }
                out.println("</div>");
                out.println("</div>");
                // Script para recargar en la misma posicion
                out.println("<script>");
                out.println("window.onload=function(){");
                out.println("var pos=window.name || 0;");
                out.println("window.scrollTo(0,pos);");
                out.println("}");
                out.println("window.onunload=function(){");
                out.println("window.name=self.pageYOffset || (document.documentElement.scrollTop+document.body.scrollTop);");
                out.println("}");
                out.println("</script>");
            } else {
                out.println("Lista vacia");
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_formRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
