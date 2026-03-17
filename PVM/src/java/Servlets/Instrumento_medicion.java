package Servlets;

import Controladores.InstrumentoMedicionJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Instrumento_medicion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            InstrumentoMedicionJpaController jpa_intrumento = new InstrumentoMedicionJpaController();
            Date fecha = new Date();
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            boolean resultadoA = false;
            boolean resultadoV = false;
            boolean resultadoFechas = false;
            List PlantillaI = null;
            String codigo = "", instrumento = "", fabricante = "", modelo = "", numero_serial = "", ubicacion = "",
                    rango_medida = "", division_escala = "", exactitud = "", clasificion = "", observaciones = "", filtro = "", plantilla = "",
                    justificacion = "", ultima_verificacion_int = "", ultima_verificacion_ext = "", fechaVrf = "", datosInf = "", anio = "";
            int id_instrumento = 0, id_tipoI = 0, id_tipoIF = 0, id_tipo = 0, estado = 0, finalizar = 0, id_tipoP = 0, id_plantilla = 0, tipoV = 0, 
                    id_plantillaV = 0, id_verificacion = 0, id_instrBack = 0,
                    eliminarV = 0, dias = 0;
            String evento = "", justify = "";
            switch (opc) {
                case 1:
                    try {
                        evento = request.getParameter("event");
                    } catch (Exception e) {
                        evento = "Principal";
                    }
                    try {
                        id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    } catch (Exception e) {
                        id_tipoIF = 0;
                    }
                    try {
                        id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    } catch (Exception e) {
                        id_instrumento = 0;
                    }
                    try {
                        id_instrBack = Integer.parseInt(request.getParameter("idInstBack"));
                    } catch (Exception e) {
                        id_instrBack = 0;
                    }
                    try {
                        filtro = request.getParameter("txt_bus");
                        if (filtro == null) {
                            filtro = "";
                        }
                    } catch (Exception e) {
                        filtro = "";
                    }
                    try {
                        dias = Integer.parseInt(request.getParameter("txt_dias"));
                    } catch (Exception e) {
                        dias = 5;
                    }
                    if (id_instrumento != 0) {
                        request.setAttribute("Consulta_instrumento", jpa_intrumento.consultaInstrumentoId(id_instrumento));
                    }
                    if (id_tipoIF != 0 || id_tipoIF == 0) {
                        if (id_instrBack > 0) {
                            request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentoId(id_instrBack));
                        }else if (!filtro.equals("") && id_tipoIF == 0) {
                            request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentosFiltro(filtro, dias));
                        } else if (!filtro.equals("") && id_tipoIF > 0) {
                            request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentosFiltroTipoInstrumento(filtro, id_tipoIF, dias));
                        } else if (id_tipoIF > 0) {
                            request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentosTipoInstrumento(id_tipoIF, dias));
                        } else {
                            request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentos(dias));
                        }
                    } else if (!filtro.equals("")) {
                        request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentosFiltro(filtro, dias));
                    } else {
                        request.setAttribute("Consulta_instrumentos", jpa_intrumento.consultaInstrumentos(dias));
                    }
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("txt_bus", filtro);
                    request.setAttribute("idTipoF", id_tipoIF);
                    request.setAttribute("Dias", dias);
                    request.setAttribute("idI", id_instrumento);
                    request.setAttribute("idInstBack", id_instrBack);
                    request.getRequestDispatcher("Instrumento_medicion.jsp").forward(request, response);
                    break;
                case 2:
                    evento = request.getParameter("event");
                    codigo = request.getParameter("txt_codigo");
                    id_tipo = Integer.parseInt(request.getParameter("lstTipo"));
                    instrumento = request.getParameter("txt_instrumento");
                    ubicacion = request.getParameter("txt_ubicacion");
                    fabricante = request.getParameter("txt_fabricante");
                    modelo = request.getParameter("txt_modelo");
                    id_plantilla = Integer.parseInt(request.getParameter("lstVrf"));
                    numero_serial = request.getParameter("txt_numSerial");
                    rango_medida = request.getParameter("txt_ranMedida");
                    division_escala = request.getParameter("txt_divEscala");
                    exactitud = request.getParameter("txt_exactitud");
                    id_tipoI = Integer.parseInt(request.getParameter("lstTipoI"));
                    clasificion = request.getParameter("lstClas");
                    observaciones = request.getParameter("txt_observaciones");
                    ultima_verificacion_int = request.getParameter("txt_verificacionInt");
                    ultima_verificacion_ext = request.getParameter("txt_verificacionExt");
                    instrumento = instrumento + "//" + ubicacion;
                    resultado = jpa_intrumento.registrarIntrumento(id_tipoI, id_tipo, id_plantilla, codigo, instrumento, fabricante, modelo, numero_serial, rango_medida, division_escala, exactitud, clasificion, observaciones, ultima_verificacion_int, ultima_verificacion_ext, nombre_Usuario);
                    if (resultado == true) {
                        jpa_intrumento.ActualizarPlantilla(id_tipoI, id_plantilla);
                    }
                    request.setAttribute("Registro_instrumento", resultado);
                    request.getRequestDispatcher("Instrumento_medicion?opc=1&lstTipoIF=" + 0 + "&txt_dias=" + 5 + "&idI=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    if (id_tipoP == 1) {
                        evento = "verificacionInstrumento";
                    } else if (id_tipoP == 2) {
                        evento = "FichasTecnicas";
                    }
                    try {
                        id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    } catch (Exception e) {
                        id_verificacion = 0;
                    }
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    filtro = request.getParameter("txt_bus");
                    try {
                        id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    } catch (Exception e) {
                        id_tipoIF = 0;
                    }
                    try {
                        dias = Integer.parseInt(request.getParameter("txt_dias"));
                    } catch (Exception e) {
                        dias = 5;
                    }
                    if (id_verificacion != 0) {
                        request.setAttribute("Finalizar_instrumento", jpa_intrumento.consultaVerificacionId(id_verificacion));
                        request.setAttribute("verificacion_instrumento", true);
                        request.setAttribute("Id_instrumento", id_instrumento);
                        request.setAttribute("Id_Tipo_plantilla", id_tipoP);
                        request.setAttribute("txt_bus", filtro);
                        request.setAttribute("lstTipoIF", id_tipoIF);
                        request.setAttribute("txt_dias", dias);
                        request.setAttribute("event", evento);
                        request.getRequestDispatcher("Instrumento_medicion.jsp").forward(request, response);
                    } else if (id_tipoP == 1) {
                        try {
                            eliminarV = Integer.parseInt(request.getParameter("EvE"));
                        } catch (Exception e) {
                            eliminarV = 0;
                        }
                        anio = request.getParameter("slc_anio");
                        if (anio == null) {
                            anio = "";
                        }
                        if (eliminarV != 0) {
                            resultadoA = Boolean.parseBoolean(request.getParameter("resultA"));
                            resultadoV = Boolean.parseBoolean(request.getParameter("resultV"));
                            if (resultadoA == true && resultadoV == true) {
                                request.setAttribute("Eliminar_verificacion_archivo_Ext", resultado);
                            } else if (resultadoV) {
                                request.setAttribute("Eliminar_verificacion_Ext", resultado);
                            } else if (resultadoA) {
                                request.setAttribute("Eliminar_Archivo_Ext", resultado);
                            }
                        }
                        request.setAttribute("verificacion_instrumento", true);
                        request.setAttribute("Id_instrumento", id_instrumento);
                        request.setAttribute("Id_Tipo_plantilla", id_tipoP);
                        request.setAttribute("txt_bus", filtro);
                        request.setAttribute("lstTipoIF", id_tipoIF);
                        request.setAttribute("txt_dias", dias);
                        request.setAttribute("slc_anio", anio);
                        request.setAttribute("event", evento);
                        request.getRequestDispatcher("Instrumento_medicion.jsp").forward(request, response);
                    } else if (id_tipoP == 2) {
                        PlantillaI = jpa_intrumento.consultaPlantillasInstrumento(id_instrumento, id_tipoP);
                        if (PlantillaI != null) {
                            request.setAttribute("Fichas_Tecnicas", PlantillaI);
                        } else {
                            id_tipoI = Integer.parseInt(request.getParameter("idTi"));
                            request.setAttribute("Fichas_Tecnicas", jpa_intrumento.traerPlantillaFichaTecnica(id_tipoI));
                        }
                        request.setAttribute("Id_Tipo_plantilla", id_tipoP);
                        request.setAttribute("Id_instrumento", id_instrumento);
                        request.setAttribute("txt_bus", filtro);
                        request.setAttribute("lstTipoIF", id_tipoIF);
                        request.setAttribute("txt_dias", dias);
                        request.setAttribute("event", evento);
                        request.getRequestDispatcher("Instrumento_medicion.jsp").forward(request, response);
                    }
                    break;
                case 4:
                    id_plantilla = Integer.parseInt(request.getParameter("idPi"));
                    plantilla = request.getParameter("txt_plantilla");
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    filtro = request.getParameter("txt_bus");
                    id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    dias = Integer.parseInt(request.getParameter("txt_dias"));
                    if (id_plantilla != 0) {
                        resultado = jpa_intrumento.modificarPlantillaInstrumento(id_plantilla, plantilla);
                        request.setAttribute("Guardar_plantilla_instrumento", resultado);
                        if (id_tipoP == 1) {
                            id_verificacion = Integer.parseInt(request.getParameter("idV"));
                            List verificacion = jpa_intrumento.consultaVerificacionId(id_verificacion);
                            Object[] obj_verificacion = (Object[]) verificacion.get(0);
                            request.getRequestDispatcher("Instrumento_medicion?opc=7&idI=" + id_instrumento + "&idTp=" + id_tipoP + "&idV=" + id_verificacion + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + id_tipoP + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                        }
                    } else {
                        resultado = jpa_intrumento.registrarPlantillaInstrumento(id_instrumento, id_tipoP, plantilla);
                        request.setAttribute("Registro_plantilla_instrumento", resultado);
                        if (id_tipoP == 1) {
                            id_verificacion = Integer.parseInt(request.getParameter("idV"));
                            PlantillaI = jpa_intrumento.consultaUltimaPlantillaVerificacionInstrumento(id_instrumento, id_tipoP);
                            Object[] obj_plantilla = (Object[]) PlantillaI.get(0);
                            jpa_intrumento.modificarVerificacionIntrumento((Integer) obj_plantilla[0], id_verificacion);
                            List verificacion = jpa_intrumento.consultaVerificacionId(id_verificacion);
                            Object[] obj_verificacion = (Object[]) verificacion.get(0);
                            request.getRequestDispatcher("Instrumento_medicion?opc=7&idI=" + id_instrumento + "&idTp=" + id_tipoP + "&idV=" + id_verificacion + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + id_tipoP + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                        }
                    }
                    break;
                case 5:
                    id_plantilla = Integer.parseInt(request.getParameter("idPi"));
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    if (estado == 0) {
                        finalizar = Integer.parseInt(request.getParameter("est"));
                        resultado = jpa_intrumento.modificarEstadoPlantillaInstrumento(id_plantilla, finalizar);
                        request.setAttribute("Estado_plantilla_instrumento", resultado);
                    } else {
                        resultado = jpa_intrumento.modificarEstadoPlantillaInstrumento(id_plantilla, estado);
                        request.setAttribute("Estado_Verificacion", resultado);
                    }
                    filtro = request.getParameter("txt_bus");
                    try {
                        id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    } catch (Exception e) {
                        id_tipoIF = 0;
                    }
                    try {
                        dias = Integer.parseInt(request.getParameter("txt_dias"));
                    } catch (Exception e) {
                        dias = 5;
                    }
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + id_tipoP + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                    break;
                case 6:
                    String fechaE_actual = request.getParameter("fecha");
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    resultado = Boolean.parseBoolean(request.getParameter("result"));
                    filtro = request.getParameter("txt_bus");
                    id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    dias = Integer.parseInt(request.getParameter("txt_dias"));
                    estado = 2;
                    if (!fechaE_actual.equals("")) {
                        resultadoFechas = jpa_intrumento.modificarFechasVerificacionExterna(id_instrumento, fechaE_actual);
                        if (resultado) {
                            id_verificacion = Integer.parseInt(request.getParameter("idV"));
                            List verificacion = jpa_intrumento.consultaVerificacionId(id_verificacion);
                            Object[] obj_verificacion = (Object[]) verificacion.get(0);
                            if (obj_verificacion[4] != null) {
                                id_plantilla = (Integer) obj_verificacion[4];
                                resultado = jpa_intrumento.modificarEstadoPlantillaInstrumento(id_plantilla, estado);
                            }
                        }
                        request.setAttribute("Estado_plantilla_instrumento", resultado);
                    } else if (resultado) {
                        request.setAttribute("Resultado_Verificacion", resultado);
                    }
                    request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + id_tipoP + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                    break;
                case 7:
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    id_plantilla = Integer.parseInt(request.getParameter("idP"));
                    tipoV = Integer.parseInt(request.getParameter("idTv"));
                    filtro = request.getParameter("txt_bus");
                    id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    dias = Integer.parseInt(request.getParameter("txt_dias"));
                    request.setAttribute("Id_instrumento", id_instrumento);
                    request.setAttribute("Id_Tipo_plantilla", id_tipoP);
                    request.setAttribute("Id_verificacion", id_verificacion);
                    request.setAttribute("Id_Tipo_verificacion", tipoV);
                    request.setAttribute("txt_bus", filtro);
                    request.setAttribute("lstTipoIF", id_tipoIF);
                    request.setAttribute("txt_dias", dias);
                    if (id_plantilla != 0) {
                        request.setAttribute("Plantilla_verificacion", jpa_intrumento.consultaPlantillaVerificacionInstrumento(id_plantilla));
                    } else {
                        id_plantillaV = Integer.parseInt(request.getParameter("idPV"));
                        request.setAttribute("Plantilla_verificacion", jpa_intrumento.consultaPlantillaVerificacion(id_plantillaV));
                    }
                    evento = "Plantilla_verificacion";
                    request.setAttribute("event", evento);
                    request.getRequestDispatcher("Instrumento_medicion.jsp").forward(request, response);
                    break;
                case 8:
                    estado = Integer.parseInt(request.getParameter("est"));
                    tipoV = Integer.parseInt(request.getParameter("idTv"));
                    String fecha_actual = request.getParameter("fecha");
                    id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    filtro = request.getParameter("txt_bus");
                    id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    dias = Integer.parseInt(request.getParameter("txt_dias"));
                    datosInf = request.getParameter("datosE");
                    List lst_intrumento = jpa_intrumento.consultaInstrumentoId(id_instrumento);
                    Object[] obj_instrumento = (Object[]) lst_intrumento.get(0);
                    if (tipoV == 2) {
                        id_plantilla = Integer.parseInt(request.getParameter("idPi"));
                        resultado = jpa_intrumento.modificarEstadoPlantillaInstrumento(id_plantilla, estado);
                        request.setAttribute("Estado_plantilla_instrumento", resultado);
                        request.setAttribute("estado", estado);
                        if (Integer.parseInt(obj_instrumento[39].toString()) == 1) {
                            List lst_datos = jpa_intrumento.consultaInformeIdVerIdPlan(id_verificacion, id_plantilla);
                            if (lst_datos != null) {
                                Object[] obj_datos = (Object[]) lst_datos.get(0);
                                jpa_intrumento.modificarDatosInforme(Integer.parseInt(obj_datos[0].toString()), datosInf);
                            } else {
                                jpa_intrumento.registrarDatosEstadisticos(id_verificacion, id_plantilla, datosInf, nombre_Usuario);
                            }
                        }
                        resultadoFechas = jpa_intrumento.modificarFechasVerificacionInterna(id_instrumento, fecha_actual);
                        List verificacion = jpa_intrumento.consultaVerificacionId(id_verificacion);
                        Object[] obj_verificacion = (Object[]) verificacion.get(0);
                        request.getRequestDispatcher("Instrumento_medicion?opc=7&idI=" + id_instrumento + "&idTp=" + id_tipoP + "&idV=" + id_verificacion + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                    } else if (tipoV == 3) {
                        id_plantilla = Integer.parseInt(request.getParameter("idPi"));
                        if (id_plantilla != 0) {
                            resultado = jpa_intrumento.modificarEstadoPlantillaInstrumento(id_plantilla, estado);
                            request.setAttribute("Estado_plantilla_instrumento", resultado);
                            request.setAttribute("estado", estado);
                            List verificacion = jpa_intrumento.consultaVerificacionId(id_verificacion);
                            Object[] obj_verificacion = (Object[]) verificacion.get(0);
                            request.getRequestDispatcher("Instrumento_medicion?opc=7&idI=" + id_instrumento + "&idTp=" + id_tipoP + "&idV=" + id_verificacion + "&idP=" + obj_verificacion[4] + "&idTv=" + obj_verificacion[2] + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                        } else {
                            id_tipoI = Integer.parseInt(request.getParameter("idTi"));
                            resultado = jpa_intrumento.modificarEstadoVerificacion(id_verificacion, estado);
                            request.setAttribute("Estado_plantilla_instrumento", resultado);
                            request.setAttribute("estado", estado);
                            request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + 1 + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                        }
                    }
                    break;
                case 9:
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_intrumento.modificarEstadoInstrumento(id_instrumento, estado);
                    request.setAttribute("Resultado_Instrumento_estado", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Instrumento_medicion?opc=1&idI=0").forward(request, response);
                    break;
                case 10:
                    try {
                        id_tipoIF = Integer.parseInt(request.getParameter("id_tipoIF"));
                    } catch (Exception e) {
                        id_tipoIF = 0;
                    }
                    try {
                        dias = Integer.parseInt(request.getParameter("txt_dias"));
                    } catch (Exception e) {
                        dias = 5;
                    }
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    codigo = request.getParameter("txt_codigo");
                    id_tipo = Integer.parseInt(request.getParameter("lstTipo"));
                    id_plantilla = Integer.parseInt(request.getParameter("lstVrf"));
                    instrumento = request.getParameter("txt_instrumento");
                    ubicacion = request.getParameter("txt_ubicacion");
                    fabricante = request.getParameter("txt_fabricante");
                    modelo = request.getParameter("txt_modelo");
                    numero_serial = request.getParameter("txt_numSerial");
                    rango_medida = request.getParameter("txt_ranMedida");
                    division_escala = request.getParameter("txt_divEscala");
                    exactitud = request.getParameter("txt_exactitud");
                    id_tipoI = Integer.parseInt(request.getParameter("lstTipoI"));
                    clasificion = request.getParameter("lstClas");
                    observaciones = request.getParameter("txt_observaciones");
                    ultima_verificacion_int = request.getParameter("txt_verificacionInt");
                    ultima_verificacion_ext = request.getParameter("txt_verificacionExt");
                    instrumento = instrumento + "//" + ubicacion;
                    resultado = jpa_intrumento.modificarIntrumento(id_instrumento, id_tipoI, id_tipo, id_plantilla, codigo, instrumento, fabricante, modelo, numero_serial, rango_medida, division_escala, exactitud, clasificion, observaciones, ultima_verificacion_int, ultima_verificacion_ext);
                    request.setAttribute("Modificar_instrumento", resultado);
                    request.getRequestDispatcher("Instrumento_medicion?opc=1&idI=" + 0 + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                    break;
                case 11:
                    id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    filtro = request.getParameter("txt_bus");
                    id_tipoIF = Integer.parseInt(request.getParameter("lstTipoIF"));
                    dias = Integer.parseInt(request.getParameter("txt_dias"));
                    fechaVrf = request.getParameter("txt_fecha");
                    resultado = jpa_intrumento.modificarFechaVerificacion(id_verificacion, fechaVrf);
                    request.setAttribute("Modificar_FechaVrf", resultado);
                    request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + id_tipoP + "&EvE=" + 0 + "&idV=" + 0 + "&txt_bus=" + filtro + "&lstTipoIF=" + id_tipoIF + "&txt_dias=" + dias + "").forward(request, response);
                    break;
                case 12:
                    id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    id_instrumento = Integer.parseInt(request.getParameter("idI"));
                    id_tipoI = Integer.parseInt(request.getParameter("idTi"));
                    id_tipoP = Integer.parseInt(request.getParameter("idTp"));
                    justify = request.getParameter("Just");
                    boolean result = jpa_intrumento.AnularVerificaciones(id_verificacion);
                    resultado = jpa_intrumento.RegistrarEvento(id_verificacion, justify, nombre_Usuario);
                    id_verificacion = 0;
//                    request.setAttribute("AnularVerificaciones", result);
                    request.setAttribute("RegistroEventos", resultado);
                    request.getRequestDispatcher("Instrumento_medicion?opc=3&idI=" + id_instrumento + "&idTi=" + id_tipoI + "&idTp=" + id_tipoP + "&EvE=0&idV=0").forward(request, response);
                    break;
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Instrumento_medicion.jsp").forward(request, response);
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
