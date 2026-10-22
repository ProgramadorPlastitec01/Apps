package Tags;

import Controladores.ReporteJpaController;
import Controladores.SolicitudJpaController;
import Metodos.Render;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Informe de Gestion del Taller de Proyectos.
 *
 * SOLO renderiza. Todo el calculo (horas, medianas, porcentajes, agrupaciones,
 * cumplimiento contra meta) llega resuelto desde los procedimientos
 * almacenados; el rango del periodo y el de comparacion llegan resueltos desde
 * Servlet.Reporte. La unica aritmetica que queda aqui es la resta entre el
 * valor del periodo y el de su comparacion para pintar la flecha del delta,
 * que es formato de presentacion y no una regla de negocio.
 */
public class Tag_reporte extends TagSupport {

    // Gris de las agrupaciones y de los datos que no existen todavia.
    private static final String GRIS = "#ADB5BD";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ReporteJpaController jpa_reporte = new ReporteJpaController();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();

        String periodo = atributo("periodo");
        String periodoComp = atributo("periodo_comp");
        String periodoLibre = atributo("periodo_libre");
        String agrupacion = atributo("agrupacion");
        int modo = 1;
        try {
            modo = Integer.parseInt(atributo("modo"));
        } catch (NumberFormatException e) {
            modo = 1;
        }
        String area = atributo("area");
        String fechaI = atributo("fecha_inicio");
        String fechaF = atributo("fecha_fin");
        // Topes del calendario tomados del reloj del servidor: el usuario no
        // puede pedir meses ni dias futuros. El backend igual los reacota, esto
        // solo evita que el datepicker los ofrezca.
        java.util.Date ahora = new java.util.Date();
        String maxMes = new java.text.SimpleDateFormat("yyyy-MM").format(ahora);
        String maxDia = new java.text.SimpleDateFormat("yyyy-MM-dd").format(ahora);
        // Etiqueta del periodo: el mes, o el rango completo en modo rango.
        String etiquetaPeriodo = (modo == 2) ? Render.rangoLegible(fechaI, fechaF) : periodo;
        String fechaISerie = atributo("fecha_inicio_serie");
        String fechaIComp = atributo("fecha_inicio_comp");
        String fechaFComp = atributo("fecha_fin_comp");
        int comparativo = 1;
        try {
            comparativo = Integer.parseInt(atributo("comparativo"));
        } catch (NumberFormatException e) {
            comparativo = 1;
        }

        List lst_kpis = jpa_reporte.consultaKpis(fechaI, fechaF, area);
        // Sin comparacion las fechas llegan vacias y no se consulta nada.
        List lst_kpis_comp = (fechaIComp.isEmpty() || fechaFComp.isEmpty())
                ? null : jpa_reporte.consultaKpis(fechaIComp, fechaFComp, area);
        List lst_horas_mes = jpa_reporte.consultaHorasMes(fechaISerie, fechaF,
                agrupacion.isEmpty() ? "mes" : agrupacion);
        List lst_horas_area = jpa_reporte.consultaHorasArea(fechaI, fechaF);
        List lst_top = jpa_solicitud.consultaTopPlanos(fechaI, fechaF, 3, "", area);
        List lst_tecnicos = jpa_reporte.consultaTecnicos(fechaI, fechaF);
        List lst_respuesta = jpa_reporte.consultaRespuestaPrioridad(fechaI, fechaF, area);

        Object[] k = (lst_kpis != null) ? (Object[]) lst_kpis.get(0) : null;
        Object[] kc = (lst_kpis_comp != null) ? (Object[]) lst_kpis_comp.get(0) : null;

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'><h1>Informe de Gestion &mdash; Taller de Proyectos</h1></div>");
            out.print("<div class='section-body'>");

            //<editor-fold defaultstate="collapsed" desc="1. FILTRO">
            out.print("<div class='card noImprimir filtroSticky'>");
            out.print("<div class='card-body'>");
            out.print("<form action='Reporte' method='get' class='row'>");
            out.print("<input type='hidden' name='opc' value='1'>");

            // Selector de modo. El bloque de mes y el de rango se emiten los
            // dos y el JS muestra el que corresponde: asi el formulario funciona
            // aunque el navegador no ejecute el script.
            out.print("<div class='col-md-2 pb-2'>");
            out.print("<label>Ver por</label>");
            out.print("<select class='form-control' name='slc_modo' id='slcModo' onchange='mostrarModo();'>");
            out.print("<option value='1' " + ((modo == 1) ? "selected" : "") + ">Mes</option>");
            out.print("<option value='2' " + ((modo == 2) ? "selected" : "") + ">Rango de fechas</option>");
            out.print("</select>");
            out.print("</div>");

            out.print("<div class='col-md-3 pb-2 bloqueModoMes' " + ((modo == 2) ? "style='display:none;'" : "") + ">");
            out.print("<label>Periodo</label>");
            out.print("<input type='month' class='form-control' name='txt_periodo' max='" + maxMes + "' value='" + Render.html(periodo) + "'>");
            out.print("</div>");

            out.print("<div class='col-md-2 pb-2 bloqueModoRango' " + ((modo == 2) ? "" : "style='display:none;'") + ">");
            out.print("<label>Desde</label>");
            out.print("<input type='date' class='form-control' name='txt_desde' max='" + maxDia + "' value='" + Render.html(fechaI) + "'>");
            out.print("</div>");
            out.print("<div class='col-md-2 pb-2 bloqueModoRango' " + ((modo == 2) ? "" : "style='display:none;'") + ">");
            out.print("<label>Hasta</label>");
            out.print("<input type='date' class='form-control' name='txt_hasta' max='" + maxDia + "' value='" + Render.html(fechaF) + "'>");
            out.print("</div>");

            out.print("<div class='col-md-3 pb-2'>");
            out.print("<label>Comparar contra</label>");
            out.print("<select class='form-control' name='slc_comparativo' id='slcComparativo' onchange='mostrarMesLibre();'>");
            // Opciones coherentes con el modo. El valor 4 es "sin comparativo"
            // (disponible en ambos modos); 1 el periodo/mes inmediato; 2 el mismo
            // periodo/mes del anio anterior; 3 "otro mes" solo tiene sentido en mes.
            out.print("<option value='4' " + ((comparativo == 4) ? "selected" : "") + ">Sin comparativo"
                    + ((modo == 2) ? " (solo este periodo)" : " (solo este mes)") + "</option>");
            out.print("<option value='1' " + ((comparativo == 1) ? "selected" : "") + ">"
                    + ((modo == 2) ? "Periodo inmediatamente anterior (mismo numero de dias)" : "Mes inmediatamente anterior") + "</option>");
            out.print("<option value='2' " + ((comparativo == 2) ? "selected" : "") + ">"
                    + ((modo == 2) ? "Mismo periodo del anio anterior" : "Mismo mes del anio anterior") + "</option>");
            if (modo == 1) {
                out.print("<option value='3' " + ((comparativo == 3) ? "selected" : "") + ">Otro mes especifico&hellip;</option>");
            }
            out.print("</select>");
            // El selector de mes solo aparece con la tercera opcion; se emite
            // siempre para que el formulario no dependa de JS para existir.
            out.print("<input type='month' class='form-control mt-2' name='txt_periodo_comp' id='txtPeriodoComp' max='" + maxMes + "' value='"
                    + Render.html(periodoLibre.isEmpty() ? periodoComp : periodoLibre) + "' "
                    + ((comparativo == 3) ? "" : "style='display:none;'") + ">");
            out.print("</div>");

            // El combo de areas se llena con las que tienen horas en el
            // periodo. No hace falta un procedimiento nuevo: es la misma lista
            // que alimenta la dona, y asi no ofrece filtros que darian vacio.
            out.print("<div class='col-md-3 pb-2'>");
            out.print("<label>Area solicitante</label>");
            out.print("<select class='form-control' name='slc_area'>");
            out.print("<option value=''>Todas las areas</option>");
            if (lst_horas_area != null) {
                for (int i = 0; i < lst_horas_area.size(); i++) {
                    Object[] a = (Object[]) lst_horas_area.get(i);
                    String rol = (a[0] == null) ? "" : a[0].toString();
                    out.print("<option value='" + Render.html(rol) + "' "
                            + (rol.equals(area) ? "selected" : "") + ">" + Render.html(a[1]) + "</option>");
                }
            }
            out.print("</select>");
            out.print("</div>");

            out.print("<div class='col-md-3 pb-2' style='display:flex;align-items:flex-end;'>");
            out.print("<button class='btn btn-red btn-lg' style='width:100%;'>Consultar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div></div>");
            //</editor-fold>

            // Todo lo que sigue entra al PDF. El id lo usa Imprimir() en el JSP.
            out.print("<div id='Imprimir'>");

            //<editor-fold defaultstate="collapsed" desc="CABECERA DEL REGISTRO">
            out.print("<div class='soloImprimir'>");
            out.print("<h3 style='margin:0;'>Informe de Gestion &mdash; Taller de Proyectos</h3>");
            out.print("<p style='margin:0;'>" + ((modo == 2) ? "Del " : "Periodo ")
                    + Render.html(etiquetaPeriodo)
                    + (periodoComp.isEmpty() ? "" : " &middot; comparado contra " + Render.html(periodoComp))
                    + " &middot; area: " + (area.isEmpty() ? "todas" : Render.html(area)) + "</p>");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="2. TARJETAS DE INDICADORES">
            out.print("<div class='kpiGrid'>");
            tarjeta(out, "Total Trabajos Realizados", (k == null) ? null : String.valueOf(k[2]),
                    delta(k, kc, 2, "", periodoComp), "fas fa-check-double");
            tarjeta(out, "En Proceso", (k == null) ? null : String.valueOf(k[3]),
                    delta(k, kc, 3, "", periodoComp), "fas fa-layer-group");
            tarjeta(out, "Volumen Atendido", (k == null) ? null : String.valueOf(k[1]),
                    delta(k, kc, 1, "", periodoComp), "fas fa-inbox");
            tarjeta(out, "% Productividad Real", (k == null) ? null : Render.horas(k[6]) + "%",
                    delta(k, kc, 6, "%", periodoComp), "fas fa-chart-line");
            tarjeta(out, "Tiempo Real Invertido", (k == null) ? null : Render.horas(k[0]) + " h",
                    delta(k, kc, 0, "h", periodoComp), "fas fa-clock");
            tarjeta(out, "Ciclo Real de Trabajo", (k == null) ? null : Render.horas(k[4]) + " d",
                    delta(k, kc, 4, "d", periodoComp), "fas fa-flag-checkered");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="3. HORAS POR MES Y POR AREA">
            StringBuilder jsMes = new StringBuilder("[");
            if (lst_horas_mes != null) {
                for (int i = 0; i < lst_horas_mes.size(); i++) {
                    Object[] m = (Object[]) lst_horas_mes.get(i);
                    jsMes.append(i > 0 ? "," : "")
                            .append("{\"etiqueta\":\"").append(Render.json(m[1]))
                            .append("\",\"valor\":").append(m[2])
                            .append(",\"color\":\"#B72E27\"}");
                }
            }
            jsMes.append("]");

            StringBuilder jsArea = new StringBuilder("[");
            if (lst_horas_area != null) {
                for (int i = 0; i < lst_horas_area.size(); i++) {
                    Object[] a = (Object[]) lst_horas_area.get(i);
                    jsArea.append(i > 0 ? "," : "")
                            .append("{\"etiqueta\":\"").append(Render.json(a[1]))
                            .append("\",\"valor\":").append(a[2])
                            .append(",\"color\":\"").append(rampa(i)).append("\"}");
                }
            }
            jsArea.append("]");

            // Los tres graficos en el MISMO grid: el navegador acomoda 3, 2 o 1
            // por fila segun el ancho. Antes el Top de planos iba aparte y
            // obligaba a bajar media pantalla para verlo.
            out.print("<div class='graficoGrid'>");

            out.print("<div class='graficoCard'>");
            out.print("<h4>Horas entregadas por mes</h4>");
            out.print("<div class='graficoCanvasWrap'><canvas id='chartMes'></canvas></div>");
            out.print("<p class='notaBloque'>" + ((modo == 2)
                    ? "Agrupado por " + Render.html(agrupacion.isEmpty() ? "mes" : agrupacion)
                      + " sobre el rango seleccionado."
                    : "Ventana de 12 meses hasta " + Render.html(periodo) + ".") + "</p>");
            out.print("</div>");

            out.print("<div class='graficoCard'>");
            out.print("<h4>Horas por area</h4>");
            out.print("<div class='graficoCanvasWrap'><canvas id='chartArea'></canvas></div>");
            out.print("<div class='leyendaLista'>");
            if (lst_horas_area != null) {
                for (int i = 0; i < lst_horas_area.size(); i++) {
                    Object[] a = (Object[]) lst_horas_area.get(i);
                    out.print("<div class='leyendaFila'>"
                            + "<span><span class='leyendaColor' style='background:" + rampa(i) + ";'></span>"
                            + Render.html(a[1]) + "</span>"
                            + "<span>" + Render.horas(a[2]) + " h (" + a[4] + "%)</span></div>");
                }
            } else {
                out.print("<p style='text-align:center;'>No existen datos registrados</p>");
            }
            out.print("</div></div>");

            out.print("<div class='graficoCard'>");
            out.print("<h4>Planos con mas horas</h4>");
            out.print("<div class='graficoCanvasWrap'><canvas id='chartPlanos'></canvas></div>");
            out.print("</div>");

            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="4. TOP 10 DE PLANOS">
            StringBuilder jsTop = new StringBuilder("[");
            if (lst_top != null) {
                for (int i = 0; i < lst_top.size(); i++) {
                    Object[] t = (Object[]) lst_top.get(i);
                    int idPln = Integer.parseInt(t[0].toString());
                    jsTop.append(i > 0 ? "," : "")
                            .append("{\"etiqueta\":\"").append(Render.json(t[1]))
                            .append("\",\"valor\":").append(t[7])
                            // Las agrupaciones ('Varios' id 0, 'Sin plano
                            // asignado' id -1) van en gris, como en el pastel
                            // del indicador: es la convencion ya aprobada.
                            .append(",\"color\":\"").append((idPln > 0) ? rampa(i) : GRIS).append("\"}");
                }
            }
            jsTop.append("]");

            out.print("<div class='bloqueCard'>");
            out.print("<h4>Planos con mas horas en el periodo</h4>");
            out.print("<div class='tablaScroll'>");
            out.print("<table class='table-hover'>");
            out.print("<thead><tr>");
            out.print("<th class='th1Verificacion'>Plano</th>");
            out.print("<th class='th1Verificacion'>No. de parte</th>");
            out.print("<th class='th1Verificacion'>Tipo</th>");
            out.print("<th class='th1Verificacion'>Solicitudes</th>");
            out.print("<th class='th1Verificacion'>Piezas</th>");
            out.print("<th class='th1Verificacion'>Horas</th>");
            out.print("<th class='th1Verificacion'>%</th>");
            out.print("</tr></thead><tbody>");
            if (lst_top != null) {
                for (int i = 0; i < lst_top.size(); i++) {
                    Object[] t = (Object[]) lst_top.get(i);
                    out.print("<tr>");
                    out.print("<td class='PdgTd'>" + Render.html(t[1]) + "</td>");
                    out.print("<td class='PdgTd'>" + Render.oRaya(t[2]) + "</td>");
                    out.print("<td class='PdgTd'>" + Render.html(t[3]) + "</td>");
                    out.print("<td class='PdgTd' align='center'>" + t[5] + "</td>");
                    out.print("<td class='PdgTd' align='center'>" + t[6] + "</td>");
                    out.print("<td class='PdgTd' align='right'>" + Render.horas(t[7]) + "</td>");
                    out.print("<td class='PdgTd' align='right'>" + t[8] + "%</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='7' style='text-align:center;'>No existen datos registrados</td></tr>");
            }
            out.print("</tbody></table>");
            out.print("</div></div>");
            //</editor-fold>

            out.print("<div class='saltoPagina'></div>");

            //<editor-fold defaultstate="collapsed" desc="5. CAPACIDAD Y RESPUESTA POR PRIORIDAD">
            out.print("<div class='bloqueGrid'>");

            out.print("<div class='bloqueCard'>");
            out.print("<h4>Capacidad del equipo</h4>");
            out.print("<div class='tablaScroll'>");
            out.print("<table class='table-hover'>");
            out.print("<thead><tr>");
            out.print("<th class='th1Verificacion'>Tecnico</th>");
            out.print("<th class='th1Verificacion'>Horas</th>");
            out.print("<th class='th1Verificacion'>Solicitudes</th>");
            out.print("<th class='th1Verificacion'>Utilizacion</th>");
            out.print("</tr></thead><tbody>");
            if (lst_tecnicos != null) {
                for (int i = 0; i < lst_tecnicos.size(); i++) {
                    Object[] t = (Object[]) lst_tecnicos.get(i);
                    out.print("<tr>");
                    out.print("<td class='PdgTd'>" + Render.html(t[0]) + "</td>");
                    out.print("<td class='PdgTd' align='right'>" + Render.horas(t[1]) + "</td>");
                    out.print("<td class='PdgTd' align='center'>" + t[2] + "</td>");
                    out.print("<td class='PdgTd' align='right'>" + t[4] + "%</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='4' style='text-align:center;'>No existen datos registrados</td></tr>");
            }
            out.print("</tbody></table></div>");
            out.print("<p class='notaBloque'>Utilizacion sobre las horas disponibles "
                    + "por tecnico al mes que define la tabla de parametros. Puede superar el 100%: es tiempo extra.</p>");
            out.print("</div>");

            out.print("<div class='bloqueCard'>");
            out.print("<h4>Volumen y Rendimiento por Clasificación</h4>");
            out.print("<div class='tablaScroll'>");
            out.print("<table class='table-hover'>");
            out.print("<thead><tr>");
            out.print("<th class='th1Verificacion'>Clasificación / Prioridad</th>");
            out.print("<th class='th1Verificacion'>Total Trabajos Realizados</th>");
            out.print("<th class='th1Verificacion'>Tiempo 1a Atención</th>");
            out.print("<th class='th1Verificacion'>Tiempo Ciclo Real</th>");
            out.print("<th class='th1Verificacion'>Estatus Técnico</th>");
            out.print("</tr></thead><tbody>");
            if (lst_respuesta != null) {
                for (int i = 0; i < lst_respuesta.size(); i++) {
                    Object[] r = (Object[]) lst_respuesta.get(i);
                    int cerradas = (r[3] == null) ? 0 : Integer.parseInt(r[3].toString());
                    out.print("<tr>");
                    out.print("<td class='PdgTd'>" + Render.html(r[1]) + "</td>");
                    out.print("<td class='PdgTd' align='center'><b>" + cerradas + "</b></td>");
                    out.print("<td class='PdgTd' align='center'>"
                            + ((r[5] == null) ? Render.oRaya(null) : r[5] + " d") + "</td>");
                    out.print("<td class='PdgTd' align='center'>"
                            + ((r[6] == null) ? Render.oRaya(null) : r[6] + " d") + "</td>");
                    out.print("<td class='PdgTd' align='center'>"
                            + (cerradas > 0
                                    ? "<span class='pastilla' style='background:#47c363;'>Atendido</span>"
                                    : "<span style='color:#a9b1bd;'>&mdash;</span>")
                            + "</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td colspan='5' style='text-align:center;'>No existen datos registrados</td></tr>");
            }
            out.print("</tbody></table></div>");
            out.print("<p class='notaBloque'>Medición técnica del volumen real ejecutado por Proyectos. "
                    + "Se descartan penalizaciones arbitrarias por prioridades externas para reflejar el trabajo efectivo del taller.</p>");
            out.print("</div>");

            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="6. COBERTURA DEL DATO Y NOTA METODOLOGICA">
            out.print("<div class='bloqueCard'>");
            out.print("<h4>Confiabilidad del dato</h4>");
            if (k != null) {
                String cobertura = String.valueOf(k[8]);
                out.print("<p style='margin-bottom:4px;'><b>Cobertura del dato: " + cobertura + "%</b> "
                        + "&mdash; porcentaje de solicitudes cerradas en el periodo que tienen al menos un "
                        + "registro de tiempo.</p>");
                out.print("<div class='barraCobertura'>");
                out.print("<div style='background:" + colorCobertura(k[8]) + ";width:" + cobertura + "%;'></div>");
                out.print("</div>");
                out.print("<p style='font-size:12px;margin-top:10px;'>"
                        + "En el periodo se descartaron <b>" + k[9] + "</b> registros de tiempo y se corrigieron "
                        + "<b>" + k[10] + "</b> por cruce de medianoche.</p>");
            }
            out.print("<div style='font-size:11px;color:#34395e;border-top:1px solid #eee;padding-top:8px;'>");
            out.print("<b>Nota metodologica.</b> Las horas se anclan a la fecha del registro de tiempo, es decir "
                    + "miden lo que el taller ENTREGO en el periodo, no lo que se solicito. ");
            out.print("Un registro se descarta cuando sus fechas quedan a mas de 24 h de distancia: el aplicativo "
                    + "calcula la duracion con la hora del dia e ignora el cruce de fechas, de modo que un trabajo "
                    + "real de varios dias se guarda incompleto. <b>Por eso las horas de los trabajos largos estan "
                    + "sub-reportadas en este informe.</b> ");
            out.print("La tasa de registros corregidos por cruce de medianoche fue de cerca del 22% anual entre "
                    + "2019 y 2024 y bajo a 1,9% en 2026: <b>comparar periodos a ambos lados de esa frontera "
                    + "compara dos calidades de dato distintas.</b>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            out.print("<p style='font-size:12px;font-style:italic;margin-left:8px;'>La informacion personal en "
                    + "este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion "
                    + "de datos personales.</p>");
            out.print("</div>");   // cierra #Imprimir

            //<editor-fold defaultstate="collapsed" desc="7. BOTONES">
            out.print("<div class='card noImprimir'><div class='card-body' style='text-align:center;'>");
            // Impresion nativa del navegador: html2canvas rasterizaba el DOM y
            // sacaba 147 paginas casi vacias con este layout.
            out.print("<button id='btnPdf' class='btn btn-info' onclick='DescargarPDF();'>"
                    + "<i class='fas fa-file-pdf'></i>&nbsp;Descargar PDF</button>&nbsp;&nbsp;");
            // Se arma con los mismos parametros del filtro para que el archivo
            // salga con el periodo y el area que el usuario esta viendo.
            out.print("<a class='btn btn-red' href='Reporte?opc=2&txt_periodo=" + Render.html(periodo)
                    + "&slc_area=" + Render.html(area) + "&slc_comparativo=" + comparativo
                    + "&txt_periodo_comp=" + Render.html(periodoLibre)
                    + "&slc_modo=" + modo + "&txt_desde=" + Render.html(fechaI)
                    + "&txt_hasta=" + Render.html(fechaF) + "'>"
                    + "<i class='fas fa-file-excel'></i>&nbsp;Descargar Excel</a>");
            out.print("</div></div>");
            //</editor-fold>

            out.print("</div>");
            out.print("</section>");

            // Los tres arreglos los consume crearGrafico() en Reporte.jsp.
            out.print("<script>");
            out.print("var DATOS_MES = " + jsMes.toString() + ";");
            out.print("var DATOS_AREA = " + jsArea.toString() + ";");
            out.print("var DATOS_TOP = " + jsTop.toString() + ";");
            out.print("</script>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

    //<editor-fold defaultstate="collapsed" desc="AYUDAS DE RENDERIZADO">
    /**
     * Atributo de request como cadena, vacia si no viaja.
     *
     * @param nombre nombre del atributo puesto por Servlet.Reporte
     * @return el valor, o cadena vacia
     */
    private String atributo(String nombre) {
        Object v = pageContext.getRequest().getAttribute(nombre);
        return (v == null) ? "" : v.toString();
    }

    /**
     * Una tarjeta de indicador con su valor y su variacion contra el periodo de
     * comparacion.
     *
     * @param out salida de la pagina
     * @param titulo nombre del indicador
     * @param valor valor ya formateado, o null si no hay datos
     * @param variacion fragmento HTML devuelto por delta()
     * @param icono clase del icono de Font Awesome
     */
    private void tarjeta(JspWriter out, String titulo, String valor, String variacion, String icono)
            throws IOException {
        // Una sola celda del grid: el ancho lo decide .kpiGrid, no la tarjeta.
        out.print("<div class='kpiCard'>");
        out.print("<div class='kpiValor'>" + ((valor == null) ? Render.oRaya(null) : valor) + "</div>");
        out.print("<div class='kpiEtiqueta'><i class='" + icono + "'></i>&nbsp;" + titulo + "</div>");
        out.print("<div class='kpiDelta'>" + variacion + "</div>");
        out.print("</div>");
    }

    /**
     * Variacion de un indicador contra su periodo de comparacion.
     *
     * El texto y el sentido de negocio los resuelve Metodos.Render, el mismo
     * que usa el exportador de Excel: asi un backlog que sube sale rojo en los
     * dos lados y no hay dos tablas de "que es bueno" que se desincronicen.
     *
     * @param k fila de KPI del periodo; admite null
     * @param kc fila de KPI del periodo de comparacion; admite null
     * @param col indice de la columna en sp_rpt_c_kpis
     * @param unidad unidad a mostrar junto al valor; puede ir vacia
     * @param periodoComp periodo comparado en formato yyyy-MM
     * @return fragmento HTML con la flecha, el porcentaje y el periodo
     */
    private String delta(Object[] k, Object[] kc, int col, String unidad, String periodoComp) {
        Object actual = (k != null && col < k.length) ? k[col] : null;
        Object previo = (kc != null && col < kc.length) ? kc[col] : null;
        String texto = Render.html(Render.textoDelta(actual, previo, unidad, periodoComp));
        Boolean mejoro = Render.mejoro(actual, previo, col);
        String clase = (mejoro == null) ? "kpiPlano"
                : (mejoro.booleanValue() ? "kpiSube" : "kpiBaja");
        return "<span class='" + clase + "'>" + texto + "</span>";
    }

    /**
     * Semaforo del cumplimiento de una franja.
     *
     * Los cortes 80 y 50 son de presentacion, no salen de la tabla parametros:
     * el dato que si sale de parametros es la meta en dias contra la que el
     * procedimiento ya calculo este porcentaje. Se fijaron para reproducir el
     * criterio de lectura pedido por Proyectos: 36% rojo, 62% y 65% ambar.
     *
     * @param pct porcentaje en meta; null cuando la franja no tiene meta o no
     *            hay solicitudes con dato en el periodo
     * @return fragmento HTML con la pastilla de color
     */
    private String semaforo(Object pct) {
        if (pct == null) {
            return "<span style='color:#a9b1bd;'>&mdash;</span>";
        }
        double v;
        try {
            v = Double.parseDouble(pct.toString());
        } catch (NumberFormatException e) {
            return Render.oRaya(pct);
        }
        String color = (v >= 80) ? "#47c363" : (v >= 50) ? "#ffa426" : "#B72E27";
        return "<span class='pastilla' style='background:" + color + ";'>" + pct + "%</span>";
    }

    /**
     * Color de una serie en la rampa del rojo corporativo, de mayor a menor.
     *
     * @param indice posicion de la serie, ya ordenada de mayor a menor
     * @return color hexadecimal
     */
    private static String rampa(int indice) {
        String[] rampa = {"#B72E27", "#C2453E", "#CD5B55", "#D8726C", "#E08983",
            "#E79F9A", "#EDB2AE", "#F1C3C0", "#F5D2D0", "#F8DFDD"};
        return rampa[indice % rampa.length];
    }

    /**
     * Color de la barra de cobertura del dato.
     *
     * @param pct porcentaje de cobertura
     * @return color hexadecimal
     */
    private static String colorCobertura(Object pct) {
        try {
            double v = Double.parseDouble(pct.toString());
            return (v >= 90) ? "#47c363" : (v >= 70) ? "#ffa426" : "#B72E27";
        } catch (Exception e) {
            return GRIS;
        }
    }
    //</editor-fold>
}
