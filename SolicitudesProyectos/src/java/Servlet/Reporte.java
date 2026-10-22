package Servlet;

import Controladores.ReporteJpaController;
import Metodos.ExportadorExcel;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Informe de Gestion del Taller de Proyectos.
 *
 * Solo lee y normaliza parametros y hace forward: el calculo vive en los
 * procedimientos almacenados y el render en Tag_reporte.
 */
public class Reporte extends HttpServlet {

    // SimpleDateFormat NO es thread-safe. Como el Servlet es un singleton
    // multihilo, se usa un ThreadLocal: una instancia por hilo, sin estado
    // compartido entre peticiones concurrentes al Informe.
    private static final ThreadLocal<SimpleDateFormat> FORMATO_DIA =
            ThreadLocal.withInitial(new java.util.function.Supplier<SimpleDateFormat>() {
                public SimpleDateFormat get() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            });
    private static final ThreadLocal<SimpleDateFormat> FORMATO_MES =
            ThreadLocal.withInitial(new java.util.function.Supplier<SimpleDateFormat>() {
                public SimpleDateFormat get() {
                    return new SimpleDateFormat("yyyy-MM");
                }
            });

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession sesion = request.getSession();
            String rolU = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String periodo, area, periodoLibre;
            int comparativo, modo;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="PANTALLA DEL INFORME">
                    // El periodo llega como yyyy-MM desde un input type='month'.
                    // Sin periodo se asume el mes en curso, que es lo que el
                    // usuario quiere ver al entrar por el menu.
                    periodo = request.getParameter("txt_periodo");
                    if (periodo == null || !periodo.matches("\\d{4}-\\d{2}")) {
                        periodo = FORMATO_MES.get().format(Calendar.getInstance().getTime());
                    }
                    // Nunca un mes futuro: el calendario del filtro lo limita,
                    // pero un parametro manipulado a mano se acota al mes en curso.
                    if (periodo.compareTo(mesActual()) > 0) {
                        periodo = mesActual();
                    }
                    area = request.getParameter("slc_area");
                    if (area == null) {
                        area = "";
                    }
                    try {
                        comparativo = Integer.parseInt(request.getParameter("slc_comparativo"));
                    } catch (NumberFormatException e) {
                        comparativo = 1;
                    }
                    // 1 = mes anterior, 2 = mismo mes del anio anterior,
                    // 3 = un mes cualquiera elegido por el usuario,
                    // 4 = sin comparacion (solo se ofrece en modo rango).
                    if (comparativo != 2 && comparativo != 3 && comparativo != 4) {
                        comparativo = 1;
                    }
                    periodoLibre = request.getParameter("txt_periodo_comp");
                    if (periodoLibre == null || !periodoLibre.matches("\\d{4}-\\d{2}")) {
                        periodoLibre = "";
                        if (comparativo == 3) {
                            // Sin mes valido no se puede comparar contra "otro":
                            // se vuelve al mes anterior en vez de romper.
                            comparativo = 1;
                        }
                    }

                    // modo 1 = mes (lo de siempre), 2 = rango de fechas.
                    try {
                        modo = Integer.parseInt(request.getParameter("slc_modo"));
                    } catch (NumberFormatException e) {
                        modo = 1;
                    }
                    String desde = request.getParameter("txt_desde");
                    String hasta = request.getParameter("txt_hasta");
                    // En rango: ninguna punta futura y, si vienen invertidas, se
                    // ordenan en vez de descartar lo que pidio el usuario.
                    if (modo == 2) {
                        desde = acotarHoy(desde);
                        hasta = acotarHoy(hasta);
                        if (rangoValido(hasta, desde) && !hasta.equals(desde)) {
                            String tmp = desde;
                            desde = hasta;
                            hasta = tmp;
                        }
                    }
                    if (modo != 2 || !rangoValido(desde, hasta)) {
                        // Cualquier rango incompleto o al reves vuelve al modo
                        // mes en vez de romper la pantalla.
                        modo = 1;
                        desde = primerDia(periodo);
                        // Mes en curso: el fin es hoy, no un dia futuro del calendario.
                        hasta = finPeriodoMes(periodo);
                    }
                    request.setAttribute("modo", modo);
                    request.setAttribute("desde", desde);
                    request.setAttribute("hasta", hasta);
                    request.setAttribute("periodo_libre", periodoLibre);
                    request.setAttribute("periodo", periodo);
                    request.setAttribute("area", area);
                    request.setAttribute("comparativo", comparativo);
                    request.setAttribute("fecha_inicio", desde);
                    request.setAttribute("fecha_fin", hasta);
                    // El periodo de comparacion se resuelve aqui y no en el Tag:
                    // es normalizacion de parametros, no renderizado.
                    if (modo == 2) {
                        // En rango la serie es el propio rango, agrupado segun su
                        // largo: por mes, dos semanas darian una sola barra.
                        request.setAttribute("fecha_inicio_serie", desde);
                        request.setAttribute("agrupacion", agrupacionSerie(desde, hasta));
                        // Comparativo: el rango anterior de IGUAL cantidad de dias.
                        if (comparativo == 4) {
                            request.setAttribute("periodo_comp", "");
                            request.setAttribute("fecha_inicio_comp", "");
                            request.setAttribute("fecha_fin_comp", "");
                        } else if (comparativo == 2) {
                            // Mismo rango, desplazado un anio hacia atras.
                            String cIni = restarAnio(desde);
                            String cFin = restarAnio(hasta);
                            request.setAttribute("periodo_comp", Metodos.Render.rangoLegible(cIni, cFin));
                            request.setAttribute("fecha_inicio_comp", cIni);
                            request.setAttribute("fecha_fin_comp", cFin);
                        } else {
                            String cIni = sumarDias(desde, -diasRango(desde, hasta));
                            String cFin = sumarDias(desde, -1);
                            request.setAttribute("periodo_comp", Metodos.Render.rangoLegible(cIni, cFin));
                            request.setAttribute("fecha_inicio_comp", cIni);
                            request.setAttribute("fecha_fin_comp", cFin);
                        }
                    } else {
                        // El grafico de columnas necesita una ventana de 12 meses:
                        // una sola barra no es una serie y no deja ver tendencia.
                        request.setAttribute("fecha_inicio_serie", primerDia(periodoMenosMeses(periodo, 11)));
                        request.setAttribute("agrupacion", "mes");
                        if (comparativo == 4) {
                            request.setAttribute("periodo_comp", "");
                            request.setAttribute("fecha_inicio_comp", "");
                            request.setAttribute("fecha_fin_comp", "");
                        } else {
                        String comp = periodoComparacion(periodo, comparativo, periodoLibre);
                        request.setAttribute("periodo_comp", comp);
                        request.setAttribute("fecha_inicio_comp", primerDia(comp));
                        // MTD: si el periodo es el mes en curso la comparacion se
                        // corta al mismo dia; si es mes cerrado, mes completo.
                        request.setAttribute("fecha_fin_comp", finComparacionMes(periodo, comp));
                        }
                    }
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="DESCARGA EXCEL">
                    // Mismos parametros que la pantalla: el Excel tiene que
                    // salir con el periodo y el area que el usuario esta viendo.
                    periodo = request.getParameter("txt_periodo");
                    if (periodo == null || !periodo.matches("\\d{4}-\\d{2}")) {
                        periodo = FORMATO_MES.get().format(Calendar.getInstance().getTime());
                    }
                    // Nunca un mes futuro: el calendario del filtro lo limita,
                    // pero un parametro manipulado a mano se acota al mes en curso.
                    if (periodo.compareTo(mesActual()) > 0) {
                        periodo = mesActual();
                    }
                    area = request.getParameter("slc_area");
                    if (area == null) {
                        area = "";
                    }
                    try {
                        comparativo = Integer.parseInt(request.getParameter("slc_comparativo"));
                    } catch (NumberFormatException e) {
                        comparativo = 1;
                    }
                    if (comparativo != 2 && comparativo != 3 && comparativo != 4) {
                        comparativo = 1;
                    }
                    periodoLibre = request.getParameter("txt_periodo_comp");
                    if (periodoLibre == null || !periodoLibre.matches("\\d{4}-\\d{2}")) {
                        periodoLibre = "";
                    }
                    try {
                        modo = Integer.parseInt(request.getParameter("slc_modo"));
                    } catch (NumberFormatException e) {
                        modo = 1;
                    }
                    String d = request.getParameter("txt_desde");
                    String h = request.getParameter("txt_hasta");
                    if (modo == 2) {
                        d = acotarHoy(d);
                        h = acotarHoy(h);
                        if (rangoValido(h, d) && !h.equals(d)) {
                            String tmp = d;
                            d = h;
                            h = tmp;
                        }
                    }
                    if (modo != 2 || !rangoValido(d, h)) {
                        modo = 1;
                        d = primerDia(periodo);
                        h = finPeriodoMes(periodo);
                    }
                    generarExcel(request, response, periodo, area, comparativo, periodoLibre, modo, d, h);
                    //</editor-fold>
                    break;
                default:
                    response.sendRedirect("Inicio.jsp");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, "Error inesperado en Servlet Reporte", e);
            response.sendRedirect("Inicio.jsp");
        }
    }

    /**
     * Arma el libro del informe y lo escribe en la respuesta como descarga.
     *
     * Las consultas se hacen aqui y se pasan resueltas al exportador: el
     * exportador solo da formato, no conoce la base. Son los MISMOS
     * procedimientos que alimentan la pantalla, para que el Excel no pueda dar
     * numeros distintos a los que el usuario acaba de ver.
     *
     * @param request peticion en curso
     * @param response respuesta donde se escribe el archivo
     * @param periodo periodo en formato yyyy-MM
     * @param area rol del area filtrada; vacio para todas
     * @param comparativo 1 = mes anterior, 2 = mismo mes del anio anterior
     */
    private void generarExcel(HttpServletRequest request, HttpServletResponse response,
            String periodo, String area, int comparativo, String periodoLibre,
            int modo, String desde, String hasta) throws IOException {
        ReporteJpaController jpa = new ReporteJpaController();
        Controladores.SolicitudJpaController jpaSlt = new Controladores.SolicitudJpaController();

        String fechaI = desde;
        String fechaF = hasta;
        String fechaIComp, fechaFComp, periodoComp, etiquetaPeriodo, agrupacion;
        if (modo == 2) {
            // Mismo criterio que la pantalla: el rango anterior de igual largo.
            int dias = diasRango(desde, hasta);
            if (comparativo == 4) {
                fechaIComp = "";
                fechaFComp = "";
            } else if (comparativo == 2) {
                fechaIComp = restarAnio(desde);
                fechaFComp = restarAnio(hasta);
            } else {
                fechaIComp = sumarDias(desde, -dias);
                fechaFComp = sumarDias(desde, -1);
            }
            periodoComp = fechaIComp.isEmpty() ? ""
                    : Metodos.Render.rangoLegible(fechaIComp, fechaFComp);
            etiquetaPeriodo = Metodos.Render.rangoLegible(desde, hasta);
            agrupacion = agrupacionSerie(desde, hasta);
        } else {
            if (comparativo == 4) {
                periodoComp = "";
                fechaIComp = "";
                fechaFComp = "";
            } else {
                periodoComp = periodoComparacion(periodo, comparativo, periodoLibre);
                fechaIComp = primerDia(periodoComp);
                // MTD: coherente con la pantalla (ver finComparacionMes).
                fechaFComp = finComparacionMes(periodo, periodoComp);
            }
            etiquetaPeriodo = periodo;
            agrupacion = "mes";
        }

        XSSFWorkbook libro = new ExportadorExcel().generar(
                etiquetaPeriodo, periodoComp, area,
                jpa.consultaKpis(fechaI, fechaF, area),
                fechaIComp.isEmpty() ? null : jpa.consultaKpis(fechaIComp, fechaFComp, area),
                jpa.consultaHorasArea(fechaI, fechaF),
                // Modo mes: ventana de 12 meses para ver tendencia.
                // Modo rango: el propio rango, con la agrupacion que corresponda.
                (modo == 2)
                        ? jpa.consultaHorasMes(desde, hasta, agrupacion)
                        : jpa.consultaHorasMes(primerDia(periodoMenosMeses(periodo, 11)), fechaF, "mes"),
                jpa.consultaRespuestaPrioridad(fechaI, fechaF, area),
                jpa.consultaXlsSolicitudes(fechaI, fechaF, area),
                jpa.consultaXlsRegistros(fechaI, fechaF, area),
                jpa.consultaXlsPlanosPiezas(fechaI, fechaF, area),
                jpa.consultaTecnicos(fechaI, fechaF));

        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // El nombre refleja el periodo real: un mes o el rango completo.
        String nombre = (modo == 2)
                ? "Informe_Gestion_Taller_" + desde + "_" + hasta
                : "Informe_Gestion_Taller_" + periodo.replace("-", "");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + nombre + ".xlsx\"");
        OutputStream salida = null;
        try {
            salida = response.getOutputStream();
            libro.write(salida);
            salida.flush();
        } finally {
            // El libro y el flujo se cierran siempre, tambien si el usuario
            // cancela la descarga a mitad de camino.
            try {
                libro.close();
            } catch (IOException ignorada) {
                // El libro ya quedo escrito; no hay nada que informar al usuario.
            }
            if (salida != null) {
                try {
                    salida.close();
                } catch (IOException ignorada) {
                    // Cliente desconectado: la descarga se corto del otro lado.
                }
            }
        }
    }

    /**
     * Comprueba que el rango este completo y en orden.
     *
     * @param desde fecha inicial en formato yyyy-MM-dd
     * @param hasta fecha final en formato yyyy-MM-dd
     * @return true si ambas existen y desde no es posterior a hasta
     */
    private boolean rangoValido(String desde, String hasta) {
        if (desde == null || hasta == null
                || !desde.matches("\\d{4}-\\d{2}-\\d{2}") || !hasta.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        // Comparar como texto alcanza: el formato ISO ordena cronologicamente.
        return desde.compareTo(hasta) <= 0;
    }

    /**
     * Cantidad de dias del rango, ambos extremos incluidos.
     *
     * @param desde fecha inicial en formato yyyy-MM-dd
     * @param hasta fecha final en formato yyyy-MM-dd
     * @return dias del rango
     */
    private int diasRango(String desde, String hasta) {
        long ms = aCalendario(hasta).getTimeInMillis() - aCalendario(desde).getTimeInMillis();
        return (int) (ms / (24L * 60L * 60L * 1000L)) + 1;
    }

    /**
     * Desplaza una fecha una cantidad de dias.
     *
     * @param fecha fecha base en formato yyyy-MM-dd
     * @param dias dias a sumar; negativo para retroceder
     * @return la fecha resultante en formato yyyy-MM-dd
     */
    /**
     * Desplaza una fecha un anio hacia atras, para el comparativo "mismo
     * periodo del anio anterior" en modo rango.
     *
     * @param fecha fecha en formato yyyy-MM-dd
     * @return la fecha un anio antes
     */
    private String restarAnio(String fecha) {
        Calendar c = aCalendario(fecha);
        c.add(Calendar.YEAR, -1);
        return FORMATO_DIA.get().format(c.getTime());
    }

    private String sumarDias(String fecha, int dias) {
        Calendar c = aCalendario(fecha);
        c.add(Calendar.DAY_OF_MONTH, dias);
        return FORMATO_DIA.get().format(c.getTime());
    }

    /**
     * Calendario a partir de una fecha ISO, con la hora en cero para que las
     * restas den dias exactos y no se corran por el horario de verano.
     *
     * @param fecha fecha en formato yyyy-MM-dd
     * @return el calendario correspondiente
     */
    private Calendar aCalendario(String fecha) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Integer.parseInt(fecha.substring(0, 4)),
                Integer.parseInt(fecha.substring(5, 7)) - 1,
                Integer.parseInt(fecha.substring(8, 10)));
        return c;
    }

    /**
     * Agrupacion de la serie de tendencia segun el largo del rango.
     *
     * Un rango de dos semanas agrupado por mes es una sola barra: no es una
     * serie. Los cortes son de legibilidad, no de negocio.
     *
     * @param desde fecha inicial en formato yyyy-MM-dd
     * @param hasta fecha final en formato yyyy-MM-dd
     * @return 'dia', 'semana' o 'mes'
     */
    private String agrupacionSerie(String desde, String hasta) {
        int dias = diasRango(desde, hasta);
        if (dias <= 31) {
            return "dia";
        }
        if (dias <= 120) {
            return "semana";
        }
        return "mes";
    }

    /**
     * Primer dia del mes de un periodo.
     *
     * @param periodo periodo en formato yyyy-MM
     * @return fecha yyyy-MM-01
     */
    private String primerDia(String periodo) {
        return periodo + "-01";
    }

    /**
     * Ultimo dia del mes de un periodo, calculado con el calendario para no
     * equivocarse en febrero ni en los anios bisiestos.
     *
     * @param periodo periodo en formato yyyy-MM
     * @return fecha yyyy-MM-dd del ultimo dia del mes
     */
    private String ultimoDia(String periodo) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(periodo.substring(0, 4)),
                Integer.parseInt(periodo.substring(5, 7)) - 1, 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return FORMATO_DIA.get().format(cal.getTime());
    }

    /**
     * Mes en curso del servidor en formato yyyy-MM.
     *
     * @return el mes actual
     */
    private String mesActual() {
        return FORMATO_MES.get().format(Calendar.getInstance().getTime());
    }

    /**
     * Fecha de hoy en formato yyyy-MM-dd.
     *
     * @return la fecha de hoy
     */
    private String hoy() {
        return FORMATO_DIA.get().format(Calendar.getInstance().getTime());
    }

    /**
     * Indica si el periodo es el mes que esta corriendo.
     *
     * @param periodo periodo en formato yyyy-MM
     * @return true si es el mes en curso
     */
    private boolean esMesEnCurso(String periodo) {
        return mesActual().equals(periodo);
    }

    /**
     * Fin real de un periodo mensual. El mes en curso todavia no termino, asi
     * que su fin es HOY y no el ultimo dia del calendario: cerrar en un dia
     * futuro sumaria dias sin datos y ademas compararia un mes incompleto
     * contra uno completo.
     *
     * @param periodo periodo en formato yyyy-MM
     * @return fecha yyyy-MM-dd del fin del periodo
     */
    private String finPeriodoMes(String periodo) {
        return esMesEnCurso(periodo) ? hoy() : ultimoDia(periodo);
    }

    /**
     * Fin del periodo de comparacion mensual, honrando Month-to-Date.
     *
     * Si el periodo base es el mes en curso, la comparacion se corta al MISMO
     * dia de hoy dentro del mes de comparacion: comparar 10 dias corridos
     * contra un mes completo daria un delta enganoso. El dia se acota al ultimo
     * dia real del mes comparado (ej. hoy 31 -> 28 en febrero). Si el periodo
     * base es un mes cerrado, se compara el mes completo en ambos lados.
     *
     * @param periodoBase periodo analizado en formato yyyy-MM
     * @param periodoComp periodo de comparacion en formato yyyy-MM
     * @return fecha yyyy-MM-dd del fin de la comparacion
     */
    private String finComparacionMes(String periodoBase, String periodoComp) {
        if (!esMesEnCurso(periodoBase)) {
            return ultimoDia(periodoComp);
        }
        int diaHoy = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(periodoComp.substring(0, 4)),
                Integer.parseInt(periodoComp.substring(5, 7)) - 1, 1);
        cal.set(Calendar.DAY_OF_MONTH,
                Math.min(diaHoy, cal.getActualMaximum(Calendar.DAY_OF_MONTH)));
        return FORMATO_DIA.get().format(cal.getTime());
    }

    /**
     * Acota una fecha ISO a hoy. Una fecha invalida se devuelve sin tocar para
     * que la valide quien corresponda.
     *
     * @param fecha fecha en formato yyyy-MM-dd
     * @return la fecha, nunca posterior a hoy
     */
    private String acotarHoy(String fecha) {
        if (fecha == null || !fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return fecha;
        }
        String h = hoy();
        return (fecha.compareTo(h) > 0) ? h : fecha;
    }

    /**
     * Periodo desplazado hacia atras una cantidad de meses.
     *
     * @param periodo periodo base en formato yyyy-MM
     * @param meses meses a restar
     * @return periodo resultante en formato yyyy-MM
     */
    private String periodoMenosMeses(String periodo, int meses) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(periodo.substring(0, 4)),
                Integer.parseInt(periodo.substring(5, 7)) - 1, 1);
        cal.add(Calendar.MONTH, -meses);
        return FORMATO_MES.get().format(cal.getTime());
    }

    /**
     * Periodo contra el que se compara el informe.
     *
     * @param periodo periodo base en formato yyyy-MM
     * @param comparativo 1 = mes anterior, 2 = mismo mes del anio anterior,
     *                    3 = el mes que eligio el usuario
     * @param periodoLibre mes elegido en formato yyyy-MM; solo se usa con 3
     * @return periodo de comparacion en formato yyyy-MM
     */
    private String periodoComparacion(String periodo, int comparativo, String periodoLibre) {
        if (comparativo == 3 && periodoLibre != null && !periodoLibre.isEmpty()) {
            return periodoLibre;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(periodo.substring(0, 4)),
                Integer.parseInt(periodo.substring(5, 7)) - 1, 1);
        cal.add((comparativo == 2) ? Calendar.YEAR : Calendar.MONTH, -1);
        return FORMATO_MES.get().format(cal.getTime());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
