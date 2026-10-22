package Metodos;

/**
 * Ayudas de renderizado compartidas por los Tags que pintan datos venidos de la
 * base. Viven aqui y no dentro de un Tag porque las usan Tag_solicitud (pastel
 * del indicador) y Tag_reporte (informe de gestion): duplicarlas garantizaria
 * que una de las dos copias se quede sin corregir.
 *
 * No hay logica de negocio: solo escapado y formato de presentacion.
 */
public class Render {

    /**
     * Escapa un valor antes de imprimirlo como HTML.
     * solicitud.Descripcion guarda HTML crudo del editor (&lt;strong&gt;,
     * saltos): imprimirlo tal cual rompe la tabla y la leyenda del grafico.
     *
     * @param valor valor crudo devuelto por el procedimiento; admite null
     * @return el texto listo para imprimir, o cadena vacia si venia null
     */
    public static String html(Object valor) {
        if (valor == null) {
            return "";
        }
        return valor.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    /**
     * Escapa un valor antes de incrustarlo en un arreglo JSON dentro de un
     * &lt;script&gt;. Las descripciones traen comillas y barras invertidas
     * literales que dejarian la etiqueta sin cerrar.
     *
     * @param valor valor crudo devuelto por el procedimiento; admite null
     * @return el texto listo para ir entre comillas dentro del JSON
     */
    public static String json(Object valor) {
        if (valor == null) {
            return "";
        }
        return valor.toString()
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", " ")
                .replace("\n", " ")
                // Neutraliza "</script>" dentro de una descripcion.
                .replace("<", "\\u003c");
    }

    /**
     * Celda de un dato que puede no existir todavia. Se usa para
     * plano.numero_parte mientras se cargan los numeros del R-PM-001 y para las
     * medianas de las franjas sin solicitudes en el periodo.
     *
     * @param valor valor a mostrar; admite null o cadena vacia
     * @return el valor escapado, o una raya gris cuando no hay dato
     */
    public static String oRaya(Object valor) {
        String texto = html(valor).trim();
        if (texto.isEmpty()) {
            return "<span style='color:#a9b1bd;'>&mdash;</span>";
        }
        return texto;
    }

    /**
     * Numero con punto de miles y coma decimal, para las horas del informe.
     * El locale va explicito: con el locale por defecto del servidor el mismo
     * informe saldria "24.337,9" o "24,337.9" segun donde este desplegado.
     *
     * @param valor numero devuelto por el procedimiento; admite null
     * @return el numero formateado, o "0,0" si venia null o no es un numero
     */
    public static String horas(Object valor) {
        if (valor == null) {
            return "0,0";
        }
        try {
            return String.format(java.util.Locale.GERMAN, "%,.1f",
                    Double.parseDouble(valor.toString()));
        } catch (NumberFormatException e) {
            return html(valor);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="COMPARATIVO DE INDICADORES">
    /**
     * Nombres cortos de mes. "ago-2025" se lee de un vistazo; "2025-08" hay
     * que traducirlo mentalmente.
     */
    private static final String[] MESES = {"ene", "feb", "mar", "abr", "may", "jun",
        "jul", "ago", "sep", "oct", "nov", "dic"};

    /**
     * Periodo en formato legible.
     *
     * @param periodo periodo en formato yyyy-MM
     * @return el periodo como "ago-2025", o el valor original si no encaja
     */
    public static String mesCorto(String periodo) {
        if (periodo == null || periodo.length() < 7 || periodo.charAt(4) != '-') {
            return (periodo == null) ? "" : periodo;
        }
        try {
            int m = Integer.parseInt(periodo.substring(5, 7));
            if (m < 1 || m > 12) {
                return periodo;
            }
            return MESES[m - 1] + "-" + periodo.substring(0, 4);
        } catch (NumberFormatException e) {
            return periodo;
        }
    }

    /**
     * Indica si BAJAR es lo bueno para un indicador de sp_rpt_c_kpis.
     *
     * Esta tabla es la UNICA fuente del sentido de negocio: la usan la pantalla
     * y el Excel. Sin ella cada consumidor decidiria por su cuenta y un backlog
     * que sube podria salir verde en un lado y rojo en el otro.
     *
     * Indices de sp_rpt_c_kpis:
     *   0 horas entregadas       subir es bueno (tiempo real invertido)
     *   1 solicitudes rango      subir es bueno (volumen atendido/recibido)
     *   2 trabajos realizados    subir es bueno (solicitudes cerradas)
     *   3 en proceso (backlog)   BAJAR es bueno
     *   4 ciclo real de trabajo  BAJAR es bueno
     *   5 primera atencion       BAJAR es bueno
     *   6 % productividad real   subir es bueno (avance real: cerradas/recibidas * 100)
     *   7 utilizacion            subir es bueno
     *   8 cobertura del dato     subir es bueno
     *   9 registros descartados  BAJAR es bueno
     *  10 registros corregidos   BAJAR es bueno
     *
     * @param columnaKpi indice de la columna en sp_rpt_c_kpis
     * @return true cuando una baja del indicador es una mejora
     */
    public static boolean bajarEsMejor(int columnaKpi) {
        return columnaKpi == 3 || columnaKpi == 4 || columnaKpi == 5
                || columnaKpi == 9 || columnaKpi == 10;
    }

    /**
     * Convierte a numero lo que el driver devuelve como BigDecimal o String.
     *
     * @param valor valor crudo; admite null
     * @return el numero, o null si no lo es
     */
    public static Double numero(Object valor) {
        if (valor == null) {
            return null;
        }
        try {
            return Double.valueOf(valor.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Texto del comparativo de un indicador contra el periodo anterior.
     *
     * Lleva flecha, valor absoluto, porcentaje y periodo legible porque el
     * formato anterior ("-707,7 vs 2025-08") no decia ni la magnitud relativa
     * ni de que mes hablaba.
     *
     * @param actual valor del periodo
     * @param anterior valor del periodo de comparacion
     * @param unidad unidad a mostrar junto al valor absoluto; puede ir vacia
     * @param periodoComp periodo comparado en formato yyyy-MM
     * @return por ejemplo "▼ 707,7 h menos (-19,3 %) que ago-2025"
     */
    public static String textoDelta(Object actual, Object anterior, String unidad, String periodoComp) {
        Double a = numero(actual);
        Double b = numero(anterior);
        if (a == null || b == null) {
            return "sin comparacion";
        }
        double dif = a.doubleValue() - b.doubleValue();
        String mes = mesCorto(periodoComp);
        if (dif == 0) {
            return "sin cambio frente a " + mes;
        }
        boolean subio = dif > 0;
        String flecha = subio ? "\u25B2" : "\u25BC";
        String sufijo = (unidad == null || unidad.isEmpty()) ? "" : " " + unidad;
        // Sin base no hay porcentaje posible: dividir por cero daria infinito.
        String pct = (b.doubleValue() == 0) ? "sin base de comparacion"
                : (subio ? "+" : "-") + horas(Double.valueOf(Math.abs(dif * 100 / b.doubleValue()))) + " %";
        return flecha + " " + horas(Double.valueOf(Math.abs(dif))) + sufijo
                + (subio ? " mas" : " menos") + " (" + pct + ") que " + mes;
    }

    /**
     * Indica si la variacion de un indicador es una mejora para el negocio.
     * Se usa para el color: verde si mejoro, rojo si empeoro, sin importar el
     * signo. Un backlog que sube es rojo aunque el numero suba.
     *
     * @param actual valor del periodo
     * @param anterior valor del periodo de comparacion
     * @param columnaKpi indice de la columna en sp_rpt_c_kpis
     * @return true si mejoro, false si empeoro, null si no hay comparacion
     */
    public static Boolean mejoro(Object actual, Object anterior, int columnaKpi) {
        Double a = numero(actual);
        Double b = numero(anterior);
        if (a == null || b == null || a.doubleValue() == b.doubleValue()) {
            return null;
        }
        boolean subio = a.doubleValue() > b.doubleValue();
        return Boolean.valueOf(bajarEsMejor(columnaKpi) ? !subio : subio);
    }
    //</editor-fold>

    /**
     * Texto legible de un campo que puede traer HTML guardado en la base.
     *
     * Varias descripciones de solicitud vienen del editor con etiquetas
     * incrustadas ("&lt;strong&gt;Cavidades:&lt;/strong&gt;"). Se QUITAN las
     * etiquetas y se escapa el resto: renderizar ese HTML seria abrir una via
     * de inyeccion, y mostrarlo crudo se lee como basura.
     *
     * No toca el dato en la base, solo la presentacion.
     *
     * @param valor valor crudo devuelto por el procedimiento; admite null
     * @return el texto sin etiquetas, escapado y con los espacios colapsados
     */
    public static String textoPlano(Object valor) {
        if (valor == null) {
            return "";
        }
        String texto = valor.toString()
                // Todo lo que va entre < y > se elimina, incluidas las
                // etiquetas mal cerradas que el editor deja a veces.
                .replaceAll("<[^>]*>", " ")
                // Entidades que ya venian escritas en la base.
                .replace("&nbsp;", " ")
                .replaceAll("\\s+", " ")
                .trim();
        return html(texto);
    }

    /**
     * Rango de fechas en formato legible para cabeceras y etiquetas.
     *
     * "01-oct-2025 a 31-dic-2025" se lee de un vistazo; "2025-10-01 / 2025-12-31"
     * obliga a traducir.
     *
     * @param desde fecha inicial en formato yyyy-MM-dd
     * @param hasta fecha final en formato yyyy-MM-dd
     * @return el rango legible, o cadena vacia si falta alguna fecha
     */
    public static String rangoLegible(String desde, String hasta) {
        if (desde == null || hasta == null || desde.isEmpty() || hasta.isEmpty()) {
            return "";
        }
        return diaLegible(desde) + " a " + diaLegible(hasta);
    }

    /**
     * Una fecha en formato legible.
     *
     * @param fecha fecha en formato yyyy-MM-dd
     * @return por ejemplo "01-oct-2025", o el valor original si no encaja
     */
    public static String diaLegible(String fecha) {
        if (fecha == null || fecha.length() < 10) {
            return (fecha == null) ? "" : fecha;
        }
        try {
            int m = Integer.parseInt(fecha.substring(5, 7));
            if (m < 1 || m > 12) {
                return fecha;
            }
            return fecha.substring(8, 10) + "-" + MESES[m - 1] + "-" + fecha.substring(0, 4);
        } catch (NumberFormatException e) {
            return fecha;
        }
    }
}
