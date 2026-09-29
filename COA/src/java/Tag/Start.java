package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.StartJpaController;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Dashboard de inicio.
 *
 * El tablero se organiza como colas de trabajo ("que tengo que hacer hoy")
 * y no como contadores sueltos: KPIs accionables, produccion por mes,
 * composicion por modulo, actividad reciente y la cola de certificados sin
 * cerrar ordenada por antiguedad.
 *
 * La antiguedad se mide desde registration_date porque
 * certificates.modification_date no lo escribe ningun procedimiento; por eso
 * los rotulos dicen "dias desde la generacion" y no tiempo de ciclo.
 */
public class Start extends TagSupport {

    /** Dias desde la generacion a partir de los cuales se marca atraso. */
    private static final int DIAS_ATRASO = 3;
    /** Dias a partir de los cuales el atraso se considera critico. */
    private static final int DIAS_CRITICO = 7;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        StartJpaController StartJpa = new StartJpaController();
        JspWriter out = pageContext.getOut();
        String NameUser = "";
        try {
            NameUser = session.getAttribute("Nombres").toString();
        } catch (Exception e) {
            NameUser = "";
        }
        try {
            out.print("<section class='section'>");

            //<editor-fold defaultstate="collapsed" desc="CABECERA">
            out.print("<div class='section-header dash-header'>");
            out.print("<div>");
            out.print("<h1 class='mb-0'>Dashboard</h1>");
            if (!NameUser.equals("")) {
                out.print("<div class='dash-header-sub'>Hola, " + esc(NameUser) + " &middot; " + hoy() + "</div>");
            }
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            imprimirKpis(out, StartJpa);
            imprimirProduccion(out, StartJpa);
            imprimirActividad(out, StartJpa);
            imprimirPendientes(out, StartJpa);

            out.print("</section>");

        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

    //<editor-fold defaultstate="collapsed" desc="FILA 1 - KPIS ACCIONABLES">
    /**
     * Cuatro colas de trabajo con su contexto: lo pendiente de finalizar, lo
     * pendiente de firmar, el volumen del mes contra el anterior y los
     * reprocesos (devoluciones y eliminaciones) de los ultimos 30 dias.
     */
    private void imprimirKpis(JspWriter out, StartJpaController StartJpa) throws IOException {

        List lst = StartJpa.ConsultKpis(DIAS_ATRASO);
        if (lst == null || lst.isEmpty()) {
            return;
        }
        Object[] K = (Object[]) lst.get(0);

        int EnGestion = toInt(K[0]), EnGestionTarde = toInt(K[1]);
        int PorFirmar = toInt(K[2]), PorFirmarTarde = toInt(K[3]);
        int Mes = toInt(K[4]), MesAnterior = toInt(K[5]);
        int Devoluciones = toInt(K[6]), Eliminados = toInt(K[7]);

        out.print("<div class='row'>");

        //<editor-fold defaultstate="collapsed" desc="EN GESTION">
        tarjetaKpi(out, "info", "fas fa-pen-nib", "En gestión", EnGestion,
                (EnGestionTarde > 0)
                        ? "<span class='dash-pill dash-pill-warn'><i class='fas fa-clock'></i> " + EnGestionTarde + " con más de " + DIAS_ATRASO + " días</span>"
                        : "<span class='dash-foot-ok'>Pendientes de finalizar</span>",
                "Generate?opt=1&Type=&StateM=1");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="POR FIRMAR">
        tarjetaKpi(out, "warning", "fas fa-signature", "Por firmar", PorFirmar,
                (PorFirmarTarde > 0)
                        ? "<span class='dash-pill dash-pill-warn'><i class='fas fa-clock'></i> " + PorFirmarTarde + " con más de " + DIAS_ATRASO + " días</span>"
                        : "<span class='dash-foot-ok'>Esperando autorización</span>",
                "Generate?opt=8&StateM=2");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="GENERADOS ESTE MES">
        String Tendencia;
        if (MesAnterior == 0) {
            Tendencia = (Mes == 0)
                    ? "<span class='dash-foot-ok'>Sin movimiento</span>"
                    : "<span class='dash-foot-ok'>Sin mes anterior para comparar</span>";
        } else {
            int Var = (int) Math.round(((Mes - MesAnterior) * 100.0) / MesAnterior);
            String Clase = (Var >= 0) ? "dash-pill-up" : "dash-pill-down";
            String Flecha = (Var >= 0) ? "fa-arrow-up" : "fa-arrow-down";
            Tendencia = "<span class='dash-pill " + Clase + "'><i class='fas " + Flecha + "'></i> "
                    + Math.abs(Var) + "%</span> <span class='dash-foot-ok'>vs " + MesAnterior + " del mes anterior</span>";
        }
        tarjetaKpi(out, "success", "fas fa-file-medical", "Generados este mes", Mes, Tendencia,
                "Generate?opt=1&Type=");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="NOVEDADES">
        int Novedades = Devoluciones + Eliminados;
        tarjetaKpi(out, "danger", "fas fa-rotate-left", "Novedades (30 días)", Novedades,
                (Novedades > 0)
                        ? "<span class='dash-foot-ok'>" + Devoluciones + " devolución" + ((Devoluciones == 1) ? "" : "es")
                          + " &middot; " + Eliminados + " eliminado" + ((Eliminados == 1) ? "" : "s") + "</span>"
                        : "<span class='dash-foot-ok'>Sin reprocesos</span>",
                "Novelty?opt=1");
        //</editor-fold>

        out.print("</div>");
    }

    private void tarjetaKpi(JspWriter out, String Tono, String Icono, String Rotulo,
            int Valor, String Pie, String Link) throws IOException {
        out.print("<div class='col-xl-3 col-lg-6 col-md-6 col-12'>");
        out.print("<div class='dash-card dash-kpi' onclick='window.location.href=\"" + Link + "\";cargarDatos()'>");
        out.print("<div class='dash-kpi-icon dash-ico-" + Tono + "'><i class='" + Icono + "'></i></div>");
        out.print("<div class='dash-kpi-body'>");
        out.print("<div class='dash-kpi-label'>" + Rotulo + "</div>");
        out.print("<div class='dash-kpi-value'>" + numero(Valor) + "</div>");
        out.print("<div class='dash-kpi-foot'>" + Pie + "</div>");
        out.print("</div>");
        out.print("<i class='fas fa-chevron-right dash-kpi-go'></i>");
        out.print("</div>");
        out.print("</div>");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FILA 2 - PRODUCCION Y COMPOSICION">
    private void imprimirProduccion(JspWriter out, StartJpaController StartJpa) throws IOException {

        out.print("<div class='row'>");

        //<editor-fold defaultstate="collapsed" desc="GRAFICA POR MES">
        out.print("<div class='col-lg-8 col-12'>");
        out.print("<div class='dash-card h-100'>");
        out.print("<div class='dash-card-head'>");
        out.print("<h4>Certificados generados por mes</h4>");
        out.print("<span class='dash-card-note'>Últimos 6 meses &middot; por estado actual</span>");
        out.print("</div>");

        //<editor-fold defaultstate="collapsed" desc="MICRO METRICAS">
        List lstAcc = StartJpa.ConsultAccountants();
        if (lstAcc != null && !lstAcc.isEmpty()) {
            Object[] A = (Object[]) lstAcc.get(0);
            out.print("<div class='dash-microrow'>");
            microMetrica(out, "Hoy", toInt(A[0]));
            microMetrica(out, "Esta semana", toInt(A[1]));
            microMetrica(out, "Este mes", toInt(A[2]));
            microMetrica(out, "Este año", toInt(A[3]));
            out.print("</div>");
        }
        //</editor-fold>

        out.print("<div class='dash-card-body'>");

        List lstProd = StartJpa.ConsultProductionByState();
        if (lstProd == null || lstProd.isEmpty()) {
            vacio(out, "fas fa-chart-column", "Todavía no hay certificados para graficar");
        } else {
            StringBuilder Lbl = new StringBuilder(), Ges = new StringBuilder(),
                    Fin = new StringBuilder(), Fir = new StringBuilder();
            for (int i = 0; i < lstProd.size(); i++) {
                Object[] P = (Object[]) lstProd.get(i);
                if (i > 0) {
                    Lbl.append(",");
                    Ges.append(",");
                    Fin.append(",");
                    Fir.append(",");
                }
                Lbl.append("'").append(capitalizar(str(P[1]))).append("'");
                Ges.append(toInt(P[2]));
                Fin.append(toInt(P[3]));
                Fir.append(toInt(P[4]));
            }
            out.print("<div class='dash-chart' style='height:260px'><canvas id='chartProduccion'></canvas></div>");
            out.print("<script>");
            out.print("document.addEventListener('DOMContentLoaded', function () {");
            out.print("new Chart(document.getElementById('chartProduccion').getContext('2d'), {");
            out.print("type: 'bar',");
            out.print("data: { labels: [" + Lbl + "], datasets: [");
            out.print("{ label: 'En gestión', data: [" + Ges + "], backgroundColor: '#3abaf4' },");
            out.print("{ label: 'Finalizado', data: [" + Fin + "], backgroundColor: '#ffa426' },");
            out.print("{ label: 'Firmado', data: [" + Fir + "], backgroundColor: '#47c363' }");
            out.print("] },");
            out.print("options: {");
            out.print("maintainAspectRatio: false,");
            out.print("legend: { position: 'bottom', labels: { boxWidth: 10, padding: 18, fontColor: '#8a94a6' } },");
            out.print("tooltips: { mode: 'index', intersect: false },");
            out.print("scales: {");
            out.print("xAxes: [{ stacked: true, gridLines: { display: false, drawBorder: false }, ticks: { fontColor: '#8a94a6' } }],");
            // Chart.js 2.7 no tiene ticks.precision: sin el callback el eje
            // muestra decimales cuando los valores son pequeños.
            out.print("yAxes: [{ stacked: true, gridLines: { color: '#f1f3f8', drawBorder: false },");
            out.print("ticks: { beginAtZero: true, fontColor: '#8a94a6', callback: function (v) { return (v % 1 === 0) ? v : ''; } } }]");
            out.print("}}");
            out.print("});");
            out.print("});");
            out.print("</script>");
        }

        out.print("</div>");
        out.print("</div>");
        out.print("</div>");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="DISTRIBUCION POR MODULO">
        out.print("<div class='col-lg-4 col-12'>");
        out.print("<div class='dash-card h-100'>");
        out.print("<div class='dash-card-head'>");
        out.print("<h4>Distribución por módulo</h4>");
        out.print("</div>");
        out.print("<div class='dash-card-body'>");

        List lstType = StartJpa.ConsultByType();
        if (lstType == null || lstType.isEmpty()) {
            vacio(out, "fas fa-layer-group", "Sin certificados registrados");
        } else {
            String[] Colores = {"#6777ef", "#3abaf4", "#47c363", "#ffa426", "#fc544b"};
            StringBuilder Lbl = new StringBuilder(), Dat = new StringBuilder(), Col = new StringBuilder();
            int Total = 0;
            for (int i = 0; i < lstType.size(); i++) {
                Object[] T = (Object[]) lstType.get(i);
                Total += toInt(T[1]);
                if (i > 0) {
                    Lbl.append(",");
                    Dat.append(",");
                    Col.append(",");
                }
                Lbl.append("'").append(moduloNombre(str(T[0]))).append("'");
                Dat.append(toInt(T[1]));
                Col.append("'").append(Colores[i % Colores.length]).append("'");
            }

            out.print("<div class='dash-donut'>");
            out.print("<div class='dash-chart' style='height:170px'><canvas id='chartModulos'></canvas></div>");
            out.print("<div class='dash-donut-center'>");
            out.print("<div class='dash-donut-value'>" + numero(Total) + "</div>");
            out.print("<div class='dash-donut-label'>Total</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<ul class='dash-legend'>");
            for (int i = 0; i < lstType.size(); i++) {
                Object[] T = (Object[]) lstType.get(i);
                int Cant = toInt(T[1]);
                int Pct = (Total == 0) ? 0 : (int) Math.round((Cant * 100.0) / Total);
                out.print("<li>");
                out.print("<span class='dash-dot' style='background:" + Colores[i % Colores.length] + "'></span>");
                out.print("<span class='dash-legend-name'>" + moduloNombre(str(T[0])) + "</span>");
                out.print("<span class='dash-legend-val'>" + numero(Cant) + " <small>(" + Pct + "%)</small></span>");
                out.print("</li>");
            }
            out.print("</ul>");

            out.print("<script>");
            out.print("document.addEventListener('DOMContentLoaded', function () {");
            out.print("new Chart(document.getElementById('chartModulos').getContext('2d'), {");
            out.print("type: 'doughnut',");
            out.print("data: { labels: [" + Lbl + "], datasets: [{ data: [" + Dat + "], backgroundColor: [" + Col + "], borderWidth: 0 }] },");
            out.print("options: { maintainAspectRatio: false, cutoutPercentage: 72, legend: { display: false } }");
            out.print("});");
            out.print("});");
            out.print("</script>");
        }

        out.print("</div>");
        out.print("</div>");
        out.print("</div>");
        //</editor-fold>

        out.print("</div>");
    }

    private void microMetrica(JspWriter out, String Rotulo, int Valor) throws IOException {
        out.print("<div class='dash-micro'>");
        out.print("<div class='dash-micro-value'>" + numero(Valor) + "</div>");
        out.print("<div class='dash-micro-label'>" + Rotulo + "</div>");
        out.print("</div>");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FILA 3 - ACTIVIDAD">
    private void imprimirActividad(JspWriter out, StartJpaController StartJpa) throws IOException {

        out.print("<div class='row'>");

        //<editor-fold defaultstate="collapsed" desc="TOP CLIENTES">
        out.print("<div class='col-lg-8 col-12'>");
        out.print("<div class='dash-card h-100'>");
        out.print("<div class='dash-card-head'>");
        out.print("<h4>Clientes con más certificados</h4>");
        out.print("<a href='Client?opt=1&Client=' class='dash-link'>Ver todos <i class='fas fa-chevron-right'></i></a>");
        out.print("</div>");
        out.print("<div class='dash-card-body p-0'>");

        List lstRanking = StartJpa.ConsultRankingCustomer();
        if (lstRanking == null || lstRanking.isEmpty()) {
            vacio(out, "fas fa-users", "Sin clientes con certificados");
        } else {
            // El ancho de la barra es relativo al cliente lider, para comparar
            // volumenes de un vistazo sin leer los numeros.
            int Maximo = 0;
            for (int i = 0; i < lstRanking.size(); i++) {
                Maximo = Math.max(Maximo, toInt(((Object[]) lstRanking.get(i))[1]));
            }

            out.print("<div class='table-responsive'>");
            out.print("<table class='table dash-table mb-0'>");
            out.print("<thead><tr>");
            out.print("<th>Cliente</th>");
            out.print("<th style='width:22%'>Certificados</th>");
            out.print("<th>Último generado</th>");
            out.print("<th>Último CC</th>");
            out.print("<th class='text-right'>Acción</th>");
            out.print("</tr></thead>");
            out.print("<tbody>");
            for (int i = 0; i < lstRanking.size(); i++) {
                Object[] R = (Object[]) lstRanking.get(i);
                int Cant = toInt(R[1]);
                int Ancho = (Maximo == 0) ? 0 : (int) Math.round((Cant * 100.0) / Maximo);
                out.print("<tr>");
                out.print("<td><span class='dash-customer'>" + esc(str(R[0])) + "</span></td>");
                out.print("<td>");
                out.print("<div class='dash-bar-wrap'>");
                out.print("<div class='dash-bar'><span style='width:" + Ancho + "%'></span></div>");
                out.print("<b class='dash-bar-val'>" + numero(Cant) + "</b>");
                out.print("</div>");
                out.print("</td>");
                out.print("<td class='text-muted'>" + fecha(R[2]) + "</td>");
                out.print("<td><span class='dash-code'>" + esc(str(R[3])) + "</span></td>");
                out.print("<td class='text-right'>");
                out.print("<a href='Client?opt=1&Client=" + url(str(R[0])) + "' class='btn btn-sm btn-outline-primary dash-btn' "
                        + "data-toggle='tooltip' data-placement='top' title='Ver detalle'><i class='fas fa-arrow-right'></i></a>");
                out.print("</td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");
            out.print("</div>");
        }

        out.print("</div>");
        out.print("</div>");
        out.print("</div>");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="ULTIMOS MOVIMIENTOS">
        out.print("<div class='col-lg-4 col-12'>");
        out.print("<div class='dash-card h-100'>");
        out.print("<div class='dash-card-head'>");
        out.print("<h4>Últimos movimientos</h4>");
        out.print("</div>");
        out.print("<div class='dash-card-body'>");

        List lstUlt = StartJpa.ConsultUltimateCertificate();
        if (lstUlt == null || lstUlt.isEmpty()) {
            vacio(out, "fas fa-clock-rotate-left", "Sin actividad reciente");
        } else {
            out.print("<ul class='dash-feed'>");
            for (int i = 0; i < lstUlt.size(); i++) {
                Object[] U = (Object[]) lstUlt.get(i);
                int Estado = toInt(U[5]);
                out.print("<li onclick='window.location.href=\"" + str(U[3]) + "\";cargarDatos()'>");
                out.print("<span class='dash-feed-ico'>"
                        + "<img src='Interface/Imagen/" + moduloImg(str(U[4])) + ".png' alt='' "
                        + "data-toggle='tooltip' data-placement='top' title='" + moduloNombre(str(U[4])) + "'/></span>");
                out.print("<div class='dash-feed-body'>");
                out.print("<div class='dash-feed-title'>" + esc(str(U[0]))
                        + "<span class='dash-state dash-state-" + Estado + "'>" + estadoNombre(Estado) + "</span></div>");
                out.print("<div class='dash-feed-sub'>" + esc(str(U[1])) + "</div>");
                out.print("<div class='dash-feed-time'>" + relativo(toInt(U[6])) + "</div>");
                out.print("</div>");
                out.print("</li>");
            }
            out.print("</ul>");
        }

        out.print("</div>");
        out.print("</div>");
        out.print("</div>");
        //</editor-fold>

        out.print("</div>");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FILA 4 - REQUIEREN ATENCION">
    /**
     * Cola de certificados sin cerrar, del mas antiguo al mas reciente. Es la
     * seccion que convierte el tablero en operativo: dice sobre que actuar.
     */
    private void imprimirPendientes(JspWriter out, StartJpaController StartJpa) throws IOException {

        List lst = StartJpa.ConsultPending(10);

        out.print("<div class='row'>");
        out.print("<div class='col-12'>");
        out.print("<div class='dash-card'>");
        out.print("<div class='dash-card-head'>");
        out.print("<h4>Requieren atención</h4>");
        out.print("<span class='dash-card-note'>Sin cerrar, del más antiguo al más reciente</span>");
        out.print("</div>");
        out.print("<div class='dash-card-body p-0'>");

        if (lst == null || lst.isEmpty()) {
            vacio(out, "fas fa-circle-check", "Nada pendiente: todos los certificados están firmados");
        } else {
            out.print("<div class='table-responsive'>");
            out.print("<table class='table dash-table mb-0'>");
            out.print("<thead><tr>");
            out.print("<th>Certificado</th>");
            out.print("<th>Cliente</th>");
            out.print("<th>Orden / Lote</th>");
            out.print("<th>Responsable</th>");
            out.print("<th class='text-center'>Antigüedad</th>");
            out.print("<th class='text-center'>Estado</th>");
            out.print("<th class='text-right'>Acción</th>");
            out.print("</tr></thead>");
            out.print("<tbody>");
            for (int i = 0; i < lst.size(); i++) {
                Object[] P = (Object[]) lst.get(i);
                int Estado = toInt(P[6]);
                int Dias = toInt(P[8]);
                String Tono = (Dias >= DIAS_CRITICO) ? "dash-age-crit"
                        : (Dias >= DIAS_ATRASO) ? "dash-age-warn" : "dash-age-ok";

                out.print("<tr>");
                out.print("<td><span class='dash-code'>" + esc(str(P[1])) + "</span></td>");
                out.print("<td><span class='dash-customer'>" + esc(str(P[2])) + "</span></td>");
                out.print("<td class='text-muted'>" + esc(str(P[3])) + " <span class='dash-sep'>/</span> " + esc(str(P[4])) + "</td>");
                out.print("<td class='text-muted'>" + esc(str(P[5])) + "</td>");
                out.print("<td class='text-center'><span class='dash-age " + Tono + "'>"
                        + ((Dias == 0) ? "Hoy" : Dias + ((Dias == 1) ? " día" : " días")) + "</span></td>");
                out.print("<td class='text-center'><span class='dash-state dash-state-" + Estado + "'>" + estadoNombre(Estado) + "</span></td>");
                out.print("<td class='text-right'>");
                out.print("<a href='" + str(P[9]) + "' class='btn btn-sm btn-outline-primary dash-btn' "
                        + "data-toggle='tooltip' data-placement='top' title='Abrir'><i class='fas fa-eye'></i></a>");
                out.print("</td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");
            out.print("</div>");
        }

        out.print("</div>");
        out.print("</div>");
        out.print("</div>");
        out.print("</div>");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AUXILIARES">
    private void vacio(JspWriter out, String Icono, String Texto) throws IOException {
        out.print("<div class='dash-empty'>");
        out.print("<i class='" + Icono + "'></i>");
        out.print("<span>" + Texto + "</span>");
        out.print("</div>");
    }

    private static int toInt(Object o) {
        try {
            return Integer.parseInt(o.toString().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static String str(Object o) {
        return (o == null) ? "" : o.toString().trim();
    }

    private static String numero(int Valor) {
        return NumberFormat.getIntegerInstance(new Locale("es", "CO")).format(Valor);
    }

    /** Evita que un nombre de cliente con &, < o comillas rompa el marcado. */
    private static String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    private static String url(String s) {
        try {
            return URLEncoder.encode(str(s), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str(s);
        }
    }

    /** El timestamp de la BD se imprimia crudo, con el sufijo ".0" de Java. */
    private static String fecha(Object o) {
        if (o == null) {
            return "";
        }
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .format(new java.util.Date(java.sql.Timestamp.valueOf(
                            o.toString().trim().replace("T", " ")).getTime()));
        } catch (Exception e) {
            return str(o);
        }
    }

    private static String hoy() {
        return capitalizar(new SimpleDateFormat("EEEE d 'de' MMMM, yyyy", new Locale("es", "CO"))
                .format(new java.util.Date()));
    }

    private static String relativo(int Minutos) {
        if (Minutos < 1) {
            return "hace instantes";
        }
        if (Minutos < 60) {
            return "hace " + Minutos + " min";
        }
        if (Minutos < 1440) {
            int h = Minutos / 60;
            return "hace " + h + ((h == 1) ? " hora" : " horas");
        }
        if (Minutos < 10080) {
            int d = Minutos / 1440;
            return "hace " + d + ((d == 1) ? " día" : " días");
        }
        int s = Minutos / 10080;
        return "hace " + s + ((s == 1) ? " semana" : " semanas");
    }

    private static String capitalizar(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    private static String moduloImg(String Type) {
        if ("RegistrosLab".equals(Type)) {
            return "Registros_lab";
        }
        if ("InspeccionManga".equals(Type)) {
            return "Inspeccion_manga";
        }
        if ("ControlGrafado".equals(Type)) {
            return "Control_grafado";
        }
        return "ST_Desc_2";
    }

    private static String moduloNombre(String Type) {
        if ("RegistrosLab".equals(Type)) {
            return "Registros Lab";
        }
        if ("InspeccionManga".equals(Type)) {
            return "Inspección Manga";
        }
        if ("ControlGrafado".equals(Type)) {
            return "Control Grafado";
        }
        if ("SistemaTubo".equals(Type)) {
            return "Sistema de Tubo";
        }
        return esc(Type);
    }

    private static String estadoNombre(int Estado) {
        switch (Estado) {
            case 1:
                return "En gestión";
            case 2:
                return "Finalizado";
            case 3:
                return "Firmado";
            default:
                return "Eliminado";
        }
    }
    //</editor-fold>
}
