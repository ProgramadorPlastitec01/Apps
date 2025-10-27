package Servlets;

import Controladoras.CronogramaJpaController;
import Controladoras.SeguimientoActividadJpaController;
import Controladoras.RegistroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import SQL.Connection_mysql_sirh;

public class Registro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            SeguimientoActividadJpaController jpa_seguimiento = new SeguimientoActividadJpaController();
            CronogramaJpaController jpa_cronograma = new CronogramaJpaController();
            RegistroJpaController jpa_registro = new RegistroJpaController();
            Connection_mysql_sirh jpa_personal = new Connection_mysql_sirh();
            List lst_personal = null;
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            int id_rol = Integer.parseInt(sesion.getAttribute("Id_rol").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String modulo = "", fechaI = "", fechaF = "", actividad = "", semana = "", equipos = "", observaciones = "", meses = "";
            String fechaD = "", carpeta = "", num_cap = "", ruta = "", fechaE = "", filtro = "", FechaI = "", FechaF = "", fechaCr = "";
            String responsable = "";
            String fecha = "", asunto = "", personal = "", contenido = "", usr_registro = "", fecha_registro = "", firma_usuario = "";
            int id_rp = 0, id_acta = 0, estado = 0, r_acta = 0, id_fto = 0, documentou = 0, codigou = 0, id_doc = 0;
            String contenidoA = "", txt_personal = "", txt_Cont_acta = "", txt_elaborado = "", reg = "", personal_ex = "";
            List lst_consulta = null;
            String nombre_ex = "", cargo_ex = "", documento_ex = "", codigo_ex = "", firmas_ex = "";
            int id_area = 0, id_programacion = 0, id_equipoP, tipo = 0, app = 0, id_cronograma = 0, anio = 0, id_digitalizacion = 0, id_seguimiento = 0, idRP2 = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="SERVLET PRINCIPAL">
                    try {
                        id_programacion = Integer.parseInt(request.getParameter("idP"));
                    } catch (Exception e) {
                        id_programacion = 0;
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("txt_bus"));
                    } catch (Exception e) {
                        anio = 0;
                    }
                    try {
                        id_digitalizacion = Integer.parseInt(request.getParameter("idD"));
                    } catch (Exception e) {
                        id_digitalizacion = 0;
                    }
                    try {
                        id_acta = Integer.parseInt(request.getParameter("idRA"));
                    } catch (Exception e) {
                        id_acta = 0;
                    }
                    try {
                        id_rp = Integer.parseInt(request.getParameter("idRP"));
                    } catch (Exception e) {
                        id_rp = 0;
                    }
                    try {
                        idRP2 = Integer.parseInt(request.getParameter("idRP2"));
                    } catch (Exception e) {
                        idRP2 = 0;
                    }
                    try {
                        id_seguimiento = Integer.parseInt(request.getParameter("idS"));
                    } catch (Exception e) {
                        id_seguimiento = 0;
                    }
                    try {
                        filtro = request.getParameter("txt_filtro");
                    } catch (Exception e) {
                        filtro = "";
                    }
                    try {
                        fechaI = request.getParameter("txt_fechaI");
                    } catch (Exception e) {
                        fechaI = "";
                    }
                    try {
                        fechaF = request.getParameter("txt_fechaF");
                    } catch (Exception e) {
                        fechaF = "";
                    }
                    
                    modulo = request.getParameter("mod");
                    request.setAttribute("fechaI", fechaI);
                    request.setAttribute("fechaF", fechaF);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("idRA", id_acta);
                    request.setAttribute("idRP", id_rp);
                    request.setAttribute("idRP2", idRP2);
                    request.setAttribute("Registro", modulo);
                    request.setAttribute("anioC", anio);
                    request.setAttribute("id_seguimiento", id_seguimiento);
                    request.setAttribute("id_digitalizacion", id_digitalizacion);
                    request.setAttribute("id_programacion", id_programacion);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="OPCION 2">
                    fechaI = request.getParameter("txt_fechaI");
                    fechaF = request.getParameter("txt_fechaF");
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    request.setAttribute("fecha_inicial", fechaI);
                    request.setAttribute("fecha_fin", fechaF);
                    request.setAttribute("id_area", id_area);
                    request.setAttribute("idRA", id_acta);
                    request.getRequestDispatcher("Registro?opc=1&mod=R001").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO PROGRAMACION DE ACTIVIDAD">
                    actividad = request.getParameter("txt_actividad");
                    semana = request.getParameter("slc_semana");
                    equipos = request.getParameter("txt_equipos");
                    resultado = jpa_seguimiento.registroProgramacionActividad(actividad, semana, nombre);
                    if (resultado) {
                        String[] arg_equipos = equipos.replace("][", "---").replace("]", "").replace("[", "").split("---");
                        for (int i = 0; i < arg_equipos.length; i++) {
                            jpa_seguimiento.registroEquiposProgramacion(arg_equipos[i]);
                        }
                    }
                    request.setAttribute("Registro_Programacion_Actividad", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R005").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR EQUIPO PROGRAMACION">
                    id_programacion = Integer.parseInt(request.getParameter("idP"));
                    id_equipoP = Integer.parseInt(request.getParameter("idEP"));
                    resultado = jpa_seguimiento.eliminarEquipoProgramacion(id_equipoP);
                    request.setAttribute("Eliminar_Equipo_Programacion", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R005&idP=" + id_programacion + "").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="EJECUTAR EQUIPO PROGRAMACION">
                    id_programacion = Integer.parseInt(request.getParameter("idP"));
                    id_equipoP = Integer.parseInt(request.getParameter("idEP"));
                    observaciones = request.getParameter("txt_observaciones");
                    resultado = jpa_seguimiento.ejecutarEquipoProgramacion(id_equipoP, observaciones, nombre);
                    request.setAttribute("Ejecutar_Equipo_Programacion", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R005&idP=" + id_programacion + "").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="VERIFICAR EQUIPO PROGRAMACION">
                    id_programacion = Integer.parseInt(request.getParameter("idP"));
                    id_equipoP = Integer.parseInt(request.getParameter("idEP"));
                    observaciones = request.getParameter("txt_observaciones");
                    resultado = jpa_seguimiento.verificarEquipoProgramacion(id_equipoP, observaciones, nombre);
                    request.setAttribute("Verificar_Equipo_Programacion", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R005&idP=" + id_programacion + "").forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CRONOGRAMA">
                    tipo = Integer.parseInt(request.getParameter("slc_tipo"));
                    meses = request.getParameter("slc_meses");
                    if (tipo == 1) {
                        actividad = request.getParameter("txt_actividad");
                    } else {
                        app = Integer.parseInt(request.getParameter("slc_aplicativo"));
                        actividad = "VERIFICACION DE CONTROL";
                    }
                    String[] mes = meses.replace("][", "-").replace("[", "").replace("]", "").split("-");
                    for (int i = 0; i < mes.length; i++) {
                        resultado = jpa_cronograma.registroCronograma(tipo, app, actividad, Integer.parseInt(mes[i]), nombre);
                    }
                    request.setAttribute("tipo", tipo);
                    request.setAttribute("Registro_R026", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R026").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="VERIFICAR ACTIVIDAD">
                    id_cronograma = Integer.parseInt(request.getParameter("idC"));
                    tipo = Integer.parseInt(request.getParameter("tipo"));
                    resultado = jpa_cronograma.verificarActividad(id_cronograma, id_usuario);
                    request.getRequestDispatcher("Registro?opc=1&mod=R026").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="VALIDAR ACTIVIDAD">
                    id_cronograma = Integer.parseInt(request.getParameter("idC"));
                    resultado = jpa_cronograma.validarActividad(id_cronograma, id_usuario);
                    request.getRequestDispatcher("Registro?opc=1&mod=R026").forward(request, response);
                    break;
                //</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR FECHA REGISTRO">
                    int id_fecha = Integer.parseInt(request.getParameter("id_fecha"));
                    String td_val = request.getParameter("td_val");
                    resultado = jpa_cronograma.modificarFechaR_005(id_fecha, td_val);
                    request.getRequestDispatcher("Registro?opc=1&mod=R005").forward(request, response);
                    break;
                //</editor-fold>
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DIGITALIZACION">
                    fechaD = request.getParameter("txt_fechaD");
                    carpeta = request.getParameter("txt_nombre");
                    num_cap = request.getParameter("txt_num_cap");
                    ruta = request.getParameter("txt_descripcion");
                    fechaE = request.getParameter("txt_fechaD");
                    resultado = jpa_registro.registroDigitalizacion(fechaD, carpeta, num_cap, ruta, fechaE, nombre);
                    request.setAttribute("Registrar_digitalizacion", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=").forward(request, response);
                    break;
                //</editor-fold>
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DIGITALIZACION">
                    id_digitalizacion = Integer.parseInt(request.getParameter("idD"));
                    fechaD = request.getParameter("txt_fechaDM");
                    carpeta = request.getParameter("txt_nombreM");
                    num_cap = request.getParameter("txt_num_capM");
                    ruta = request.getParameter("txt_descripcionM");
                    fechaE = request.getParameter("txt_fechaDM");
                    resultado = jpa_registro.ModificarDigitalizacion(id_digitalizacion, fechaD, carpeta, num_cap, ruta, fechaE);
                    request.setAttribute("Modificar_digitalizacion", resultado);
                    request.getRequestDispatcher("Registro?opc=1&idD=0&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=").forward(request, response);
                    break;
//</editor-fold>
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR ESTADO DIGITALIZACION">
                    id_digitalizacion = Integer.parseInt(request.getParameter("idD"));
                    resultado = jpa_registro.ModificarEstadoDigitalizacion(id_digitalizacion);
                    request.setAttribute("Inabilitar_digitalizacion", resultado);
                    request.getRequestDispatcher("Registro?opc=1&idD=0&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=").forward(request, response);
                    break;
                //</editor-fold>
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="FIRMAR DIGITALIZACION">
                    id_digitalizacion = Integer.parseInt(request.getParameter("idD"));
                    resultado = jpa_registro.firmarDigitalizacion(id_digitalizacion, id_usuario);
                    request.getRequestDispatcher("Registro?opc=1&idD=0&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=").forward(request, response);
                    break;
//</editor-fold>
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR FECHA VERIFICACION">
                    id_cronograma = Integer.parseInt(request.getParameter("idC"));
                    fechaCr = request.getParameter("txt_fechaC");
                    resultado = jpa_cronograma.modificarFechaVerfica(id_cronograma, fechaCr);
                    break;
                //</editor-fold>
                case 16:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR FECHA VALIDACION">
                    id_cronograma = Integer.parseInt(request.getParameter("idC"));
                    fechaCr = request.getParameter("txt_fechaC");
                    resultado = jpa_cronograma.modificarFechaValida(id_cronograma, fechaCr);
                    break;
                //</editor-fold>
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR RESPONSABLE R-SI-005">
                    id_seguimiento = Integer.parseInt(request.getParameter("idR"));
                    responsable = request.getParameter("txt_responsable");
                    resultado = jpa_seguimiento.ModificarResponsbleR005(id_seguimiento, responsable);
                    break;
                //</editor-fold>
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PROGRMACION ACTIVIDAD">
                    id_seguimiento = Integer.parseInt(request.getParameter("idS"));
                    actividad = request.getParameter("txt_actividad");
                    semana = request.getParameter("slc_semana");
                    equipos = request.getParameter("txt_equipos");
                    resultado = jpa_seguimiento.ModificarProgramacionActividad(id_seguimiento, actividad, semana);
                    if (resultado) {
                        String[] arg_equipos = equipos.replace("][", "---").replace("]", "").replace("[", "").split("---");
                        for (int i = 0; i < arg_equipos.length; i++) {
                            jpa_seguimiento.ModificarEquiposProg(id_seguimiento, arg_equipos[i]);
                        }
                    }
                    request.setAttribute("Modificar_Programacion_Actividad", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=R005&idS=0").forward(request, response);
                    break;
                //</editor-fold>
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR RESPONSABLE VERIFICADO R-005">
                    id_seguimiento = Integer.parseInt(request.getParameter("idR2"));
                    responsable = request.getParameter("txt_responsableV");
                    resultado = jpa_seguimiento.ModificarResponsbleVerificadoR005(id_seguimiento, responsable);
                    break;
//</editor-fold>
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR FECHA VERIFICADO R-005">
                    id_seguimiento = Integer.parseInt(request.getParameter("idF"));
                    String fechaV = request.getParameter("txt_fechaV");
                    resultado = jpa_seguimiento.ModificarFechaVerificadoR005(id_seguimiento, fechaV);
                    break;
//</editor-fold>
                case 21:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR CONTENIDO ACTA">
                    id_acta = Integer.parseInt(request.getParameter("idRA"));
                    contenidoA = request.getParameter("txt_contenidoA");
                    resultado = jpa_registro.modificarContenidoActa(id_acta, contenido);
                    break;
//</editor-fold>
                case 22:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO ACTA">
                    id_acta = Integer.parseInt(request.getParameter("idRA"));
                    fecha = request.getParameter("txt_fecha");
                    asunto = request.getParameter("txt_asunto");
                    personal = request.getParameter("txt_personal");
                    List lst_registro = null;
                    lst_registro = jpa_registro.Consultar_registros_id(id_acta);
                    if (lst_registro != null) {
                        Object[] obj_reg = (Object[]) lst_registro.get(0);
                        if (obj_reg[5] != null) {
                            contenido = obj_reg[5].toString();
                            contenido = contenido.replace("XXASUNTOXX", "" + asunto + "");
                            contenido = contenido.replace("XXFECHAXX", fecha);
                        } else {
                            contenido = "";
                        }
                    } else {
                        contenido = "8";
                    }
                    resultado = jpa_registro.registrarActa(id_acta, fecha, asunto, personal, nombre);
                    request.setAttribute("Registrar_ACTA", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&idRA=0&txt_filtro=").forward(request, response);
                    break;
                //</editor-fold>
                case 23:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR ACTA">
                    id_acta = Integer.parseInt(request.getParameter("idRA"));
                    fecha = request.getParameter("txt_fecha");
                    asunto = request.getParameter("txt_asunto");
                    resultado = jpa_registro.modificarActa(id_acta, fecha, asunto);
                    request.setAttribute("Modificar_ACTA", resultado);
                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRA=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 24:
                    //<editor-fold defaultstate="collapsed" desc="PREVISUALIZAR REGISTROS, REGISTRAR Y EDITAR">
                    try {
                        id_fto = Integer.parseInt(request.getParameter("fto"));
                    } catch (Exception e) {
                        id_fto = 0;
                    }
                    id_acta = Integer.parseInt(request.getParameter("idRA"));
                    fecha = request.getParameter("txt_fecha");
                    asunto = request.getParameter("txt_asunto");
                    contenidoA = request.getParameter("contenidoA");

                    try {
                        txt_personal = request.getParameter("Txt_valores_filtro");
                        if (txt_personal.equals("")) {
                            txt_personal = null;
                        }
                    } catch (Exception e) {
                        txt_personal = null;
                    }

                    if (id_fto == 1) {
                        resultado = jpa_registro.Registrar_acta(id_acta, fecha, asunto, contenidoA, nombre, id_rol);
                        id_acta = 0;
                        request.setAttribute("RegistroActa", resultado);

                    } else if (id_fto == 2) {
                        if (txt_personal != null) {
                            resultado = jpa_registro.Modificar_personal_acta(id_acta, txt_personal);
                            request.setAttribute("RegistroUsuariosActa", resultado);
                        } else {
                            request.setAttribute("RegistroUsuariosActaVacio", true);
                        }
                        request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);
                        break;
                    } else if (id_fto == 3) {
                        resultado = jpa_registro.Modificar_personal_acta(id_acta, txt_personal);
                        request.setAttribute("ModificarUsuariosActa", resultado);
                        request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);
                        break;
                    } else if (id_fto == 4) {
                        documento_ex = request.getParameter("documento_ex");
                        nombre_ex = request.getParameter("nombre_ex");
                        cargo_ex = request.getParameter("cargo_ex");
                        codigo_ex = request.getParameter("codigo_ex");
                        firmas_ex = request.getParameter("firmas_ex");
                        personal_ex = "[ " + documento_ex + " - " + nombre_ex + " - " + cargo_ex + " - " + codigo_ex + " - " + firmas_ex + "]";
                        String personal_acta = "";
                        lst_registro = jpa_registro.Consultar_Personal_Acta(id_acta);
                        Object[] obj_pers = (Object[]) lst_registro.get(0);
                        if (obj_pers[1] == null) {
                            personal_acta = "";
                        } else {
                            personal_acta = obj_pers[1].toString();
                        }
                        personal_ex = personal_ex + personal_acta;
                        resultado = jpa_registro.Modificar_personal_acta(id_acta, personal_ex);
                        request.setAttribute("Registro_usuariosExternos", resultado);
                        request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);
                        break;
                    }

                    request.setAttribute("id_actaA", id_acta);
                    request.setAttribute("txt_fechaA", fecha);
                    request.setAttribute("txt_asuntoA", asunto);

                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&idRA=0&txt_filtro=").forward(request, response);
//                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&idRA=0&txt_filtro=&idRP=" + id_acta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 25:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CONTENIDO DEL ACTA ">
                    try {
                        id_fto = Integer.parseInt(request.getParameter("fto"));
                    } catch (Exception e) {
                        id_fto = 0;
                    }
                    try {
                        id_acta = Integer.parseInt(request.getParameter("idRA"));
                    } catch (Exception e) {
                        id_acta = 0;
                    }
                    try {
                        txt_Cont_acta = request.getParameter("txt_contenidoActa");
                    } catch (Exception e) {
                        txt_Cont_acta = "";
                    }
                    try {
                        txt_elaborado = request.getParameter("txt_responsable");
                    } catch (Exception e) {
                        txt_elaborado = "";
                    }

                    if (id_acta > 0) {
                        lst_registro = jpa_registro.consultarActaId(id_acta);
                        Object[] obj_acta = (Object[]) lst_registro.get(0);
                        lst_consulta = jpa_registro.Consultar_registros_id(Integer.parseInt(obj_acta[1].toString()));
                        Object[] obj_reg = (Object[]) lst_consulta.get(0);
                        reg = obj_reg[5].toString();
                        reg = reg.replace("</textarea>", "" + txt_Cont_acta + "</textarea>")
                                .replace("<input disabled", "<input value=\"" + txt_elaborado + "\"");

                        reg = reg.replace("'", "&quot;");
                        resultado = jpa_registro.Modificar_Contenido_acta(id_acta, reg);
                        request.setAttribute("ModificarContenidoActa", resultado);
                    }
                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);

                    //</editor-fold>
                    break;
                case 26:
                    //<editor-fold defaultstate="collapsed" desc="CONSULTAR REGISTRSAR FIRMAS">
                    try {
                        id_fto = Integer.parseInt(request.getParameter("fto"));
                    } catch (Exception e) {
                        id_fto = 0;
                    }
                    try {
                        id_acta = Integer.parseInt(request.getParameter("idRA"));
                    } catch (Exception e) {
                        id_acta = 0;
                    }
                    try {
                        firma_usuario = request.getParameter("txt_firma");
                    } catch (Exception e) {
                        firma_usuario = "";
                    }
                    try {
                        documentou = Integer.parseInt(request.getParameter("id_documento"));
                    } catch (Exception e) {
                        documentou = 0;
                    }
                    try {
                        codigou = Integer.parseInt(request.getParameter("txt_codigo"));
                    } catch (Exception e) {
                        codigou = 0;
                    }
                    try {
                        id_doc = Integer.parseInt(request.getParameter("id_doc"));
                    } catch (Exception e) {
                        id_doc = 0;
                    }
                    try {
                        id_doc = Integer.parseInt(request.getParameter("id_doc"));
                    } catch (Exception e) {
                        id_doc = 0;
                    }
                    if (id_fto == 4) {
                        if (firma_usuario != null) {
                            resultado = jpa_registro.Registrar_NuevaFirma_usuario(documentou, codigou, firma_usuario);
                            request.setAttribute("RegistrarNuevaFirma", resultado);
                        } else {
//                            lst_personal = jpa_personal.Empleado_sirh_comparacion(documentou);
//                            if (lst_personal.size() == 0) {
//                                resultado = true;
//                                request.setAttribute("NoExisteUsuario", resultado);
//                                request.setAttribute("NoExisteUsuario_documento", documentou);
//                                request.setAttribute("firma_usuarios", firma_usuario);
//                                request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);
//                                break;
//                            } else {
//
//                            }
                            request.setAttribute("id_doc_usuario", id_doc);
                            request.setAttribute("documento_usuario", documentou);
                            request.setAttribute("codigo_usuario", codigou);
                        }
                        request.setAttribute("id_doc_usuario", id_doc);
                        request.setAttribute("documento_usuario", documentou);
                        request.setAttribute("codigo_usuario", codigou);
                    } else if (id_fto == 5) {
                        request.setAttribute("id_fto", id_fto);
                        request.setAttribute("documento_usuario", documentou);
                        request.setAttribute("codigo_usuario", codigou);
                    }
                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);

                    //</editor-fold>
                    break;
                case 27:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE FIRMAS">
                    try {
                        id_acta = Integer.parseInt(request.getParameter("idRA"));
                    } catch (Exception e) {
                        id_acta = 0;
                    }
                    try {
                        firma_usuario = request.getParameter("txt_firma").replace("]", "").replace("[", "");
                    } catch (Exception e) {
                        firma_usuario = "";
                    }
                    try {
                        documentou = Integer.parseInt(request.getParameter("id_documento"));
                    } catch (Exception e) {
                        documentou = 0;
                    }

                    String firmas_pers = "";
                    lst_registro = jpa_registro.consultarActaId(id_acta);
                    Object[] obj_actas = (Object[]) lst_registro.get(0);
                    String[] Personal = obj_actas[4].toString().replace("][", "//").replace("]", "").replace("[", "").split("//");
//                    String[] Personal = obj_actas[4].toString().replace("][", "//").split("//");
                    for (int i = 0; i < Personal.length; i++) {
                        if (Personal[i].toString().contains("" + documentou + "")) {
                            Personal[i] = Personal[i].toString().replace("XXFIRMASXX", firma_usuario + "][");
                            resultado = jpa_registro.Modificar_personal_acta(id_acta, Personal[i]);
                        }
                        firmas_pers += "[" + Personal[i] + "]";
                        firmas_pers = firmas_pers.replace("[]", "");
                    }
                    if (firmas_pers != "") {
                        resultado = jpa_registro.Modificar_personal_acta(id_acta, firmas_pers);
                        request.setAttribute("RegistroFirmasActa", resultado);
                    } else {
//                        resultado = jpa_registro.Modificar_personal_acta(id_acta, obj_actas[4].toString());
//                        request.setAttribute("RegistroFirmas", resultado);

                    }
                    request.getRequestDispatcher("Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + id_acta + "").forward(request, response);
                    //</editor-fold>
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
