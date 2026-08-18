package Tags;

import Controladoras.RegistroJpaController;
import Utilidades.PmpConexion;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_registro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        int codigo = Integer.parseInt(sesion.getAttribute("codigo").toString());
        int idUsuario = Integer.parseInt(sesion.getAttribute("Identificacion").toString());
        String nombreResp = sesion.getAttribute("Nombre").toString();
        RegistroJpaController jpaRegistro = new RegistroJpaController();
        PmpConexion conn = new PmpConexion();
        List<String> lstZonas = null;
        List lstRegistros = null;
        List lstResponsable, lstVerifica = null;
        Date fecha = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String fechaStr = df.format(fecha);
        Object[] objResponsableVerifica, objResponsableVerifica2 = null;
        String fechaInicio = "", fechaFin = "", idTurno = "", datoGlobal = "", fechaInicioStr = "", fechaFinStr = "";
        int Temp1 = 0, idResponsable = 0, idCargo = 0;
        List lst_modi_cabecera = null;
        int idReg = 0, cargo = 0;
        try {
            Temp1 = Integer.parseInt(pageContext.getRequest().getAttribute("Temp1").toString());
        } catch (Exception e) {
            Temp1 = 0;
        }

        try {
            idReg = Integer.parseInt(pageContext.getRequest().getAttribute("idReg").toString());
        } catch (Exception e) {
            idReg = 0;
        }

        try {
            cargo = Integer.parseInt(pageContext.getRequest().getAttribute("cargo").toString());
        } catch (Exception e) {
            cargo = 0;
        }
        try {
            fechaInicioStr = pageContext.getRequest().getAttribute("fechaInicioStr").toString();
            fechaFinStr = pageContext.getRequest().getAttribute("fechaFinStr").toString();
            idResponsable = Integer.parseInt(pageContext.getRequest().getAttribute("idResponsable").toString());
            idTurno = pageContext.getRequest().getAttribute("idTurno").toString();
            idCargo = Integer.parseInt(pageContext.getRequest().getAttribute("idCargo").toString());
            datoGlobal = pageContext.getRequest().getAttribute("datoGlobal").toString();
            Temp1 = Integer.parseInt(pageContext.getRequest().getAttribute("Temp1").toString());
        } catch (Exception e) {
            fechaInicioStr = "";
            fechaFinStr = "";
            idResponsable = 0;
            idTurno = "";
            idCargo = 0;
            datoGlobal = "";
        }
        try {
            out.println("<div class='buscar'>");
            if (rol.equals("COORDINADOR MANTENIMIENTO FARMACEUTICO") || rol.equals("TECNICO MANTENIMIENTO FARMACEUTICO") || rol.equals("ADMINISTRADOR")) {
                out.println("<div class='tooltip-container'>");
                out.println("<i class=\"fas fa-bars fa-lg\" id='Menu_registroB' style='cursor:pointer'></i>");
                out.println("<div class=\"tooltip medida\">CREAR REGISTRO</div>");
                out.println("</div>");
            } else {
                out.println("<div class='tooltip-container'>");
                out.println("<i class=\"fas fa-bars fa-lg\" style='cursor: no-drop;'></i>");
                out.println("<div class=\"tooltip medida\">CREAR REGISTRO</div>");
                out.println("</div>");
            }
            out.println("<div style='width: 260px; display: flex; justify-content: space-between; align-items: center;'>");
            out.println("<i id='filtroBuscar' class=\"fas fa-search fa-lg\" style='cursor:pointer'></i>");
            if (Temp1 == 1) {
                out.println("<a href='Registro?op=20'><i class=\"fas fa-trash-alt fa-lg\"></i></a>");
            }
            out.println("<input id='filtro_bitacora' onkeyup='FiltroRegistro();' style='margin-bottom: 0;' type='text' placeholder='Buscar'>");
            out.println("</div>");
            out.println("</div>");
            //<editor-fold defaultstate="collapsed" desc="MODAL REGISTRAR">
            out.println("<div class='forms'>");
            out.println("<div>");
            out.println("<div style='display:none;' id='toggleR'>");
            out.println("<div>");
            out.println("<form action='Registro?op=2' method='post' onsubmit='checkSubmit();'>");
            out.println("<fieldset>");
            out.println("<legend>Nuevo Registro</legend>");
            out.println("<div>");
            out.println("<label>Responable:</label>");
            out.println("<input type='hidden' value='" + idUsuario + "' name='nombreResp'>");
            out.println("<div><input type='text' value='" + nombreResp + "' readonly></div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Fecha:</label>");
            out.println("<div><input type='text' value='" + fechaStr + "' name='fecha' readonly></div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Turno: </label>");
            out.println("</div>");
            out.println("<div>");
            out.println("<select name='turno' required>");
            out.println("<option style='display:none;' value=''> --Seleccione turno-- </option>");
            lstRegistros = jpaRegistro.consultarturno();
            for (int t = 0; t < lstRegistros.size(); t++) {
                Object[] obj_turno = (Object[]) lstRegistros.get(t);
                out.println("<option value='" + obj_turno[2] + "'>" + obj_turno[3] + "</option>");
            }
            out.println("</select>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Zona: </label>");
            out.println("<div>");
            lstZonas = conn.consultaZonas(); // SE CONSULTAN ZONAS DE APLICATIVO PMP
            if (lstZonas != null) {
                String[] zonas = lstZonas.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                for (int i = 0; i < zonas.length; i++) {
                    String[] objZona = zonas[i].toString().replace(" ", "").split("---");
                    out.print("<div><input type='checkbox' onclick='MassiveId(" + objZona[0] + ")'>" + objZona[1] + "</div>");
                }
            } else {
                out.print("<b>No existen zonas</b>");
            }
            out.println("</div>");
            out.println("<input type='text' name='idZona' id='idZ' value='' hidden>");
            //Alerta de resultado se encuentra en Tag_ resultados
            out.println("<input type='submit' value='Registrar' style='cursor:pointer'>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="FILTRO">
            out.println("<div>");
            out.println("<div style='display:none;' id='toggleS'>");
            out.println("<div>");
            out.println("<form action='Registro?op=19' method='post' onsubmit='checkSubmit();'>");
            out.println("<fieldset>");
            out.println("<legend>Buscar</legend>");
            out.println("<div>");
            out.println("<label>Fecha Inicio: </label>");
            out.println("<div><input type='text' id='start' name='fechaInicio' onchange='fechas()' value='" + fechaInicio + "' autocomplete='off' placeholder='AAAA-MM-DD'></div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Fecha Fin: </label>");
            out.println("<div><input type='text' id='end' name='fechaFin' value='" + fechaFin + "' autocomplete='off' placeholder='AAAA-MM-DD'></div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Responsable: </label>");
            out.println("<div>");
            out.println("<select name='idResponsable'>");
            out.println("<option value=''>-- Seleccionar --</option>");
            lstResponsable = jpaRegistro.responsableRegistro2();
            if (lstResponsable != null) {
                for (int i = 0; i < lstResponsable.size(); i++) {
                    objResponsableVerifica = (Object[]) lstResponsable.get(i);
                    if (objResponsableVerifica[5].equals(idResponsable)) {
                        out.println("<option value='" + objResponsableVerifica[5] + "' selected>" + objResponsableVerifica[0] + " " + objResponsableVerifica[1] + "</option>");
                    } else {
                        out.println("<option value='" + objResponsableVerifica[5] + "'>" + objResponsableVerifica[0] + " " + objResponsableVerifica[1] + "</option>");
                    }
                }
            } else {
                out.println("<option value=''>Sin Datos</option>");
            }
            out.println("</select>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Turno: </label>");
            out.println("<div>");
            out.println("<select name='idTurno'>");
            out.println("<option value=''>-- Seleccionar --</option>");
            lstRegistros = jpaRegistro.consultarturno();
            for (int t = 0; t < lstRegistros.size(); t++) {
                Object[] obj_turno = (Object[]) lstRegistros.get(t);
                out.println(idTurno.equals(obj_turno[2]) ? "<option value='" + obj_turno[2] + "' selected>" + obj_turno[3] + "</option>" : "<option value='" + obj_turno[2] + "'>" + obj_turno[3] + "</option>");
            }
            out.println("</select>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Cargo: </label>");
            out.println("<div>");
            out.println("<select name='idCargo'>");
            out.println("<option value=''>-- Seleccionar --</option>");
            lstResponsable = jpaRegistro.cargoRegistroBitacora();
            if (lstResponsable != null) {
                for (int i = 0; i < lstResponsable.size(); i++) {
                    objResponsableVerifica = (Object[]) lstResponsable.get(i);
                    if (objResponsableVerifica[6].equals(idCargo)) {
                        out.println("<option value='" + objResponsableVerifica[6] + "' selected>" + objResponsableVerifica[4] + "</option>");
                    } else {
                        out.println("<option value='" + objResponsableVerifica[6] + "'>" + objResponsableVerifica[4] + "</option>");
                    }
                }
            } else {
                out.println("<option value=''>Sin responsable</option>");
            }

            out.println("</select>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Palabra Clave: </label>");
            out.println("<div><input type='text' name='datoGlobal' value='" + datoGlobal + "'></div>");
            out.println("</div>");
            out.println("<input type='submit' value='Buscar' style='cursor:pointer'>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            if (!fechaInicioStr.equals("") || !fechaFinStr.equals("") || idResponsable != 0 || !idTurno.equals("") || idCargo != 0 || !datoGlobal.equals("") && Temp1 == 1) {
                lstRegistros = jpaRegistro.filtrar(fechaInicioStr, fechaFinStr, idResponsable, idTurno, idCargo, datoGlobal);
                if (lstRegistros.isEmpty()) {
                    out.println("<br>");
                    out.println("<h1>No se encontraron registros.</h1>");
                    out.println("<div id='NavPosicion' style='display:none;'></div>");
                }
            } else {
                lstRegistros = jpaRegistro.consultarRegistros();
            }

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE LA TABLA">
            out.println("<div id='NavPosicion'></div>");
            out.println("<br>");
            out.println("<table id='resultadosT1'>");
            out.println("<td></td>");
            if (lstRegistros != null) {
                for (int i = 0; i < lstRegistros.size(); i++) {
                    Object[] objRegistro = (Object[]) lstRegistros.get(i);
                    lstResponsable = jpaRegistro.responsableRegistro(Integer.parseInt(objRegistro[0].toString()));
                    if (lstResponsable != null) {
                        int estado = Integer.parseInt(objRegistro[12].toString());
                        String idLi = objRegistro[3].toString().replace("][", ",").replace("[", "").replace("]", "");
                        out.println("<tr class='registro'>");
                        out.println("<td rowspan='2'><span class='sub-titulo relleno'>" + objRegistro[0] + "</span></td>");
                        List lst_zona = conn.consultaZonasId(idLi);
                        out.println("<td style='text-align:center;' rowspan='2'><div><div class='sub-titulo-flex'><span class='sub-titulo'>ZONA</span> </div>");
                        out.println("<div>");
                        for (int j = 0; j < lst_zona.size(); j++) {
                            if (j == lstZonas.size() - 1) {
                                out.print(lst_zona.get(j));
                            } else {

                                out.print(lst_zona.get(j) + "<br/>");
                            }
                        }
                        out.print("</div></div></td>");

                        out.println("<td rowspan='2'><div class='sub-titulo-flex'><span class='sub-titulo'>FECHA: </span><span>" + objRegistro[1] + "</span>");
                        out.println("<span class='sub-titulo'>TURNO: </span>" + objRegistro[2] + "</div></td>");
                        objResponsableVerifica = (Object[]) lstResponsable.get(0);
                        out.println("<td><span class='sub-titulo'>RESPONSABLE: </span>" + objResponsableVerifica[0] + " " + objResponsableVerifica[1] + "</span></td>");
                        if (objRegistro[10] != null) {
                            lstVerifica = jpaRegistro.responsableVerifica(Integer.parseInt(objRegistro[0].toString()));
                            objResponsableVerifica2 = (Object[]) lstVerifica.get(0);
                            out.println("<td><span class='sub-titulo'>VERIFICA: </span>" + (objResponsableVerifica2[0] + " " + objResponsableVerifica2[1]) + "</td>");
                        } else {
                            out.println("<td><span class='sub-titulo' style='color: red;'>VERIFICA: </span> FALTA POR FIRMAR </td>");
                        }
                        //<editor-fold defaultstate="collapsed" desc="PERMISOS_CARGOS">
                        if (estado == 1) {
                            if (rol.equals("JEFE MANTENIMIENTO FARMACEUTICO") || rol.equals("ADMINISTRADOR")) {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idReg=" + objRegistro[0] + "' id='Menu_registroM'> <i class='fas fa-user-edit fa-lg'></i> </a>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'> <i class='fas fa-file-alt fa-lg'></i> </a>");
                                out.println("<div class='tooltip medida2'>R-MTF-011</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i onclick='confirmarCerrarRegistro(" + objRegistro[0] + ", " + Temp1 + ")' class='fas fa-lock-open fa-lg' style='cursor:pointer'></i>");
                                out.println("<div class='tooltip medida3'>CERRAR</div>");
                                out.println("</td>");
                            } else if (rol.equals("COORDINADOR MANTENIMIENTO FARMACEUTICO") && idUsuario == Integer.parseInt(objRegistro[11].toString())) {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idReg=" + objRegistro[0] + "' id='Menu_registroM'><i class='fas fa-user-edit fa-lg'></i></a>");
                                out.println("<div class='tooltip medida1'  >EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'> <i class='fas fa-file-alt fa-lg'></i> </a>");
                                out.println("<div class='tooltip medida2'>R-MTF-011</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i onclick='confirmarCerrarRegistro(" + objRegistro[0] + ", " + Temp1 + " )' class=\"fas fa-lock-open fa-lg\" style='cursor:pointer'></i>");
                                out.println("<div class='tooltip medida3'>CERRAR</div>");
                                out.println("</td>");
                            } else if (rol.equals("TECNICO MANTENIMIENTO FARMACEUTICO") && idUsuario == Integer.parseInt(objRegistro[11].toString())) {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idReg=" + objRegistro[0] + "' id='Menu_registroM'><i class='fas fa-user-edit fa-lg'></i></a>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'> <i class='fas fa-file-alt fa-lg'></i> </a>");
                                out.println("<div class=\"tooltip medida2\">R-MTF-011</div>");
                                out.println("</td>");
                                if (objResponsableVerifica[3].equals(codigo)) {
                                    out.println("<td class='tooltip-container' rowspan='2'>");
                                    out.println("<i onclick='confirmarCerrarRegistro(" + objRegistro[0] + ", " + Temp1 + ")' class=\"fas fa-lock-open fa-lg\" style='cursor:pointer'></i>");
                                    out.println("<div class=\"tooltip medida3\">CERRAR</div>");
                                    out.println("</td>");
                                } else {
                                    out.println("<td class='tooltip-container' rowspan='2'>");
                                    out.println("<i class=\"fas fa-lock-open fa-lg\" style='cursor: no-drop;'></i>");
                                    out.println("<div class=\"tooltip medida3\">CERRAR</div>");
                                    out.println("</td>");
                                }
                            } else {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i class='fas fa-user-edit fa-lg' style='cursor: no-drop;color:#aeaeae'></i>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i class='fas fa-file-alt fa-lg' style='cursor: no-drop;color:#aeaeae'></i>");
                                out.println("<div class='tooltip medida2'>R-MTF-011</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i class='fas fa-lock-open fa-lg' style='cursor: no-drop;color:#aeaeae'></i>");
                                out.println("<div class='tooltip medida3'>CERRAR</div>");
                                out.println("</td>");

                            }
                        } else {
                            if (rol.equals("JEFE MANTENIMIENTO FARMACEUTICO") || rol.equals("ADMINISTRADOR")) {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='#'><i class='fas fa-user-edit fa-lg' style='cursor: no-drop;color:#aeaeae'></i></a>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'><i class=\"fas fa-eye fa-lg\"></i></a>");
                                out.println("<div class=\"tooltip medida4\">VER</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i onclick='confirmarAbrirRegistro(" + objRegistro[0] + ", " + Temp1 + ")' class=\"fas fa-lock fa-lg\" style='cursor:pointer'></i>");
                                out.println("<div class=\"tooltip medida5\">ABRIR</div>");
                                out.println("</td>");

                            } else if (rol.equals("COORDINADOR MANTENIMIENTO FARMACEUTICO")) {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='#'><i class='fas fa-user-edit fa-lg' style='cursor: no-drop;color:#aeaeae'></i></a>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'><i class=\"fas fa-eye fa-lg\"></i></a>");
                                out.println("<div class=\"tooltip medida4\">VER</div>");
                                out.println("</td>");
                                if (objResponsableVerifica[4].equals("TECNICO MANTENIMIENTO FARMACEUTICO")) {
                                    out.println("<td class='tooltip-container' rowspan='2'>");
                                    out.println("<i onclick='confirmarAbrirRegistro(" + objRegistro[0] + ", " + Temp1 + ")' class='fas fa-lock fa-lg' style='cursor:pointer'></i>");
                                    out.println("<div class=\"tooltip medida5\">ABRIR</div>");
                                    out.println("</td>");
                                } else {
                                    out.println("<td class='tooltip-container' rowspan='2'>");
                                    out.println("<i class=\"fas fa-lock fa-lg\" style='cursor: no-drop;'></i>");
                                    out.println("<div class=\"tooltip medida5\">ABRIR</div>");
                                    out.println("</td>");
                                }
                            } else if (rol.equals("TECNICO MANTENIMIENTO FARMACEUTICO")) {

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='#'><i class='fas fa-user-edit fa-lg' style='cursor: no-drop;color:#aeaeae'></i></a>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'><i class=\"fas fa-eye fa-lg\"></i></a>");
                                out.println("<div class=\"tooltip medida4\">VER</div>");
                                out.println("</td>");

                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<i class=\"fas fa-lock fa-lg\" style='cursor: no-drop color:#aeaeae;'></i>");
                                out.println("<div class=\"tooltip medida5\">ABRIR</div>");
                                out.println("</td>");
                            } else {
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='#'><i class='fas fa-user-edit fa-lg' style='cursor: no-drop;color:#aeaeae'></i></a>");
                                out.println("<div class='tooltip medida1'>EDITAR</div>");
                                out.println("</td>");
                                out.println("<td class='tooltip-container' rowspan='2'>");
                                out.println("<a href='Registro?op=1&idRegistro=" + objRegistro[0] + "&Temp1=" + Temp1 + "'><i class=\"fas fa-eye fa-lg\"></i></a>");
                                out.println("<div class=\"tooltip medida4\">VER</div>");
                                out.println("</td>");
                                out.println("<td class='tooltip-container' class=\"tooltip-container\">");
                                out.println("<i class=\"fas fa-lock fa-lg\" style='cursor: no-drop; color:#aeaeae'></i>");
                                out.println("<div class=\"tooltip medida5\">ABRIR</div>");
                                out.println("</td>");
                            }
                        }
                        //</editor-fold>
                        out.println("<td><span class='sub-titulo'>CARGO: </span>" + objResponsableVerifica[4] + "</td>");
                        if (objRegistro[10] != null) {
                            lstVerifica = jpaRegistro.responsableVerifica(Integer.parseInt(objRegistro[0].toString()));
                            objResponsableVerifica2 = (Object[]) lstVerifica.get(0);
                            out.println("<td><span class='sub-titulo'>CARGO: </span>" + objResponsableVerifica2[4] + "</td>");
                        } else {
                            out.println("<td><span class='sub-titulo' style='color: red;'>CARGO: </span> N/A </td>");
                        }
                    }
                }
            } else {
                out.println("<tr class='registro'>");
                out.println("<td style='width:1245px' ><strong><br>SIN DATOS</strong></td>");
                out.println("</tr>");
            }

            out.println("</tr>");
            out.println("</table>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MODAL EDITAR">
            if (idReg > 0) {

                out.println("<div class='forms'>");
                out.println("<div>");
                out.println("<div style='display:block;' id='toggleM'>");
                lst_modi_cabecera = jpaRegistro.traerregistroid(idReg);
                Object[] cabecera = (Object[]) lst_modi_cabecera.get(0);
                out.println("<form action='Registro?op=21' id='formmodi' method='post' onsubmit='return ZonaVacia();'>");
                out.println("<fieldset>");
                out.println("<legend>Modificar Registro</legend>");
                out.println("<i class='fas fa-times fa-lg' style='margin: 0% 0% 0% 95%;cursor:pointer;' onclick='cerrarmodicabecera()'></i>");
                out.println("<div>");
                out.println("<label>Responable:</label>");
                out.println("<input type='text' value='" + cabecera[0] + "' name='nombreResp' hidden>");
                out.println("<div><input type='text' value='" + cabecera[1] + "' readonly></div>");
                out.println("</div>");
                out.println("<div>");
                out.println("<label>Fecha:</label>");
                out.println("<div><input type='text' value='" + cabecera[4] + "' name='fecha' readonly></div>");
                out.println("</div>");
                out.println("<input type='text' value='" + cabecera[3] + "' name='idregistro' hidden>");
                out.println("<div>");
                out.println("<label>Turno: </label>");
                out.println("</div>");
                out.println("<div>");
                out.println("<select name='turno' required>");
                out.println("<option style='display:none;' value=''> --Seleccione turno-- </option>");
                lstRegistros = jpaRegistro.consultarturno();
                for (int t = 0; t < lstRegistros.size(); t++) {
                    Object[] obj_turno = (Object[]) lstRegistros.get(t);
                    out.println(cabecera[5].equals(obj_turno[2]) ? "<option value='" + obj_turno[2] + "' selected>" + obj_turno[3] + "</option>" : "<option value='" + obj_turno[2] + "'>" + obj_turno[3] + "</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("<div>");
                out.println("<label>Zona: </label>");
                out.println("<div>");
                lstZonas = conn.consultaZonas(); // SE CONSULTAN ZONAS DE APLICATIVO PMP
                if (lstZonas != null) {
                    String[] zonas = lstZonas.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                    for (int i = 0; i < zonas.length; i++) {
                        String[] objZona = zonas[i].toString().replace(" ", "").split("---");
                        String lineas = cabecera[6].toString();
                        if (lineas.contains(objZona[0])) {
                            out.print("<div><input type='checkbox' onclick='MassiveIdModi(" + objZona[0] + ")' checked>" + objZona[1] + "</div>");
                        } else {
                            out.print("<div><input type='checkbox' onclick='MassiveIdModi(" + objZona[0] + ")'>" + objZona[1] + "</div>");
                        }
                    }
                } else {
                    out.print("<b>No existen zonas</b>");
                }
                out.println("</div>");
                out.print("<input type='text' name='idZonaM' id='idZmodi' value='" + cabecera[6] + "' hidden>");
                //Alerta de resultado se encuentra en Tag_ resultados
                out.println("<input type='submit' value='Modificar' style='cursor:pointer'>");
                out.println("</fieldset>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="SCRIPT DE LA PAGINACION">
            out.println("<script type='text/javascript'>");
            out.println("var pager = new Pager('resultadosT1', 10);");
            out.println("pager.init();");
            out.println("pager.showPageNav('pager','NavPosicion');");
            out.println("pager.showPage(1);");
            out.println("</script>");
            //</editor-fold>
            out.println("<script>");
            out.println("$(Menu_registroB).click(function() {");
            out.println("$(\"#toggleR\").toggle(\"slide\");");
            out.println("$(\"#toggleM\").hide();");
            out.println("});");
            out.println("</script>");

            out.println("<script>");
            out.println("$(filtroBuscar).click(function() {");
            out.println("$(\"#toggleS\").toggle(\"slide\");");
            out.println("});");
            out.println("</script>");

            out.println("<script>");
            out.println("$(Menu_registroM).click(function() {");
            out.println("$(\"#toggleM\").toggle(\"slide\");");
            out.println("$(\"#toggleR\").hide();");
            out.println("});");
            out.println("</script>");

        } catch (Exception ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
