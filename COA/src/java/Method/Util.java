package Method;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    // Convierte "NULL" (o vacío) en null para que el merge de "Actualizar
    // desde Factory" sepa que ese campo todavía no tiene dato real.
    public static String valorOrNull(String[] arr, int idx) {
        if (arr == null || idx >= arr.length || arr[idx] == null) {
            return null;
        }
        String valor = arr[idx].trim();
        return (valor.isEmpty() || valor.equalsIgnoreCase("NULL")) ? null : valor;
    }

    /**
     * Tags trae el nombre del producto con el volumen embebido (ej. "BOLSA
     * NUTRIBAG BB 2000mL 3V BCT-EE"), a diferencia de Factory, que ya lo
     * separa en SQL (columnas Producto y Volumen). Devuelve
     * {nombreSinVolumen, volumen}, o null si el nombre no trae un patrón
     * "NNNmL" que separar.
     */
    public static String[] separarVolumenDelNombre(String nombreProducto) {
        if (nombreProducto == null) {
            return null;
        }
        Matcher m = Pattern.compile("\\d+\\s*mL", Pattern.CASE_INSENSITIVE).matcher(nombreProducto);
        if (!m.find()) {
            return null;
        }
        String volumen = m.group().trim();
        String nombreSinVolumen = (nombreProducto.substring(0, m.start()) + nombreProducto.substring(m.end()))
                .replaceAll("\\s+", " ").trim();
        return new String[]{nombreSinVolumen, volumen};
    }

    public static class ResultadoActualizacionPendientes {
        public final String html;
        public final int camposActualizados;

        public ResultadoActualizacionPendientes(String html, int camposActualizados) {
            this.html = html;
            this.camposActualizados = camposActualizados;
        }
    }

    /**
     * Certificado en Borrador: algunos campos de cabecera quedaron en
     * "pendiente" (class 'pending') porque Factory no tenía dato al momento
     * de generar (ej. Factura/Remisión aún no facturadas). Esto permite
     * reconsultar Factory más tarde y completar SOLO esos campos —
     * identificados por el atributo data-field puesto al generar la
     * cabecera— sin tocar nada que el usuario ya haya editado a mano.
     */
    public static ResultadoActualizacionPendientes actualizarPendientesDesdeFactory(
            String html, Map<String, String> valoresFrescos) {
        if (html == null || valoresFrescos == null) {
            return new ResultadoActualizacionPendientes(html, 0);
        }
        int actualizados = 0;
        for (Map.Entry<String, String> entrada : valoresFrescos.entrySet()) {
            String campo = entrada.getKey();
            String valor = entrada.getValue();
            if (valor == null || valor.trim().isEmpty()) {
                continue;
            }
            Pattern p = Pattern.compile(
                    "<span(\\s+class='([^']*)')?\\s+data-field='" + Pattern.quote(campo) + "'([^>]*)>([^<]*)</span>");
            Matcher m = p.matcher(html);
            if (!m.find()) {
                continue;
            }
            String claseActual = m.group(2) == null ? "" : m.group(2);
            if (!claseActual.matches(".*\\bpending\\b.*")) {
                continue;
            }
            String claseLimpia = claseActual.replaceAll("\\s*\\bpending\\b\\s*", " ").trim();
            if (claseLimpia.isEmpty()) {
                claseLimpia = "editable";
            }
            String nuevoSpan = "<span class='" + claseLimpia + "' data-field='" + campo + "'" + m.group(3) + ">" + valor + "</span>";
            html = html.substring(0, m.start()) + nuevoSpan + html.substring(m.end());
            actualizados++;
        }
        return new ResultadoActualizacionPendientes(html, actualizados);
    }
    // Convierte "[a /// b /// c]" en {"a","b","c"} (los campos vienen unidos
    // con " /// ", así que hay que recortar el espacio que queda pegado a
    // cada lado tras el split; sin este trim una URL en el último campo
    // queda con un espacio inicial y deja de matchear como URL absoluta).
    public static String[] parseResult(Object obj) {
        if (obj == null) return new String[0];
        String[] parts = obj.toString().replace("[", "").replace("]", "").split("///");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    // Reemplaza todas las ocurrencias de las claves del mapa en el HTML
    public static String applyReplacements(String html, Map<String, String> replacements) {
        if (html == null || replacements == null) return html;
        for (Map.Entry<String, String> e : replacements.entrySet()) {
            html = html.replace(e.getKey(), e.getValue());
        }
        return html;
    }

    // Evita NullPointer cuando un valor puede venir nulo
    public static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    // Escapa un valor para insertarlo dentro de un atributo HTML de doble
    // comilla que a su vez contiene un string JS de comilla simple (patrón
    // onclick="fn(this, '<%= valor %>')" usado en FileManager.jsp). Sin esto,
    // un valor de un sistema externo (Registros LAB, Generación de Lotes,
    // Inspección Manga...) que traiga comillas o una etiqueta HTML rompe el
    // atributo y corrompe visualmente toda la fila.
    public static String jsAttr(Object o) {
        return jsAttr(o == null ? null : o.toString());
    }

    public static String jsAttr(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    /**
     * Escapa un valor para insertarlo en un literal JS de comilla simple
     * dentro de un bloque &lt;script&gt; (no un atributo HTML): a diferencia
     * de {@link #jsAttr}, NO hay que convertir "&" a "&amp;" aquí, porque el
     * contenido de &lt;script&gt; nunca pasa por el decodificador de
     * entidades HTML del navegador — si se usa jsAttr ahí, el "&amp;" queda
     * como texto literal dentro del string JS (visto en vivo: una URL de
     * redirect con "&amp;Type=..." en vez de "&Type=...", porque el valor
     * quedaba mal armado desde el JS antes de llegar siquiera al servidor).
     */
    public static String jsString(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    public static String jsString(Object o) {
        return jsString(o == null ? null : o.toString());
    }

    // Algunos registros heredados de Generación de Lotes traen el nombre del
    // archivo como una etiqueta <a href="...">texto</a> completa en vez de un
    // nombre plano. Si trae ese formato, se usa el href real; si no, el valor
    // tal cual (ya sin etiquetas, por seguridad).
    public static String extractFileName(String s) {
        if (s == null) return "";
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("href=\"([^\"]+)\"").matcher(s);
        if (m.find()) {
            return m.group(1);
        }
        return s.replaceAll("<[^>]*>", "").trim();
    }

    // Congela los <select> del certificado dejando únicamente el texto de la
    // opción elegida (ej: registro INVIMA en R-GC-074). Se usa al pasar el
    // certificado a un estado superior a "En gestión", donde ya no debe
    // permitirse cambiar la selección.
    public static String collapseSelects(String html) {
        if (html == null) return html;
        java.util.regex.Matcher selectMatcher = java.util.regex.Pattern
                .compile("<select[^>]*>(.*?)</select>", java.util.regex.Pattern.DOTALL)
                .matcher(html);
        StringBuffer sb = new StringBuffer();
        while (selectMatcher.find()) {
            selectMatcher.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(selectedOptionText(selectMatcher.group(1))));
        }
        selectMatcher.appendTail(sb);
        return sb.toString();
    }

    // Congela los <input type="date"> del certificado (ej: fecha de despacho)
    // dejando únicamente el valor guardado como texto plano. El type="date"
    // puede venir con las clases/atributos en cualquier orden según cómo haya
    // quedado guardado el HTML, así que no se busca un literal exacto: se
    // localiza la etiqueta completa por su type y se extrae su value.
    public static String collapseDateInputs(String html) {
        if (html == null) return html;
        java.util.regex.Matcher inputMatcher = java.util.regex.Pattern
                .compile("<input[^>]*type=\"date\"[^>]*>")
                .matcher(html);
        StringBuffer sb = new StringBuffer();
        while (inputMatcher.find()) {
            java.util.regex.Matcher valueMatcher = java.util.regex.Pattern
                    .compile("value=\"([^\"]*)\"")
                    .matcher(inputMatcher.group());
            String value = valueMatcher.find() ? valueMatcher.group(1) : "";
            inputMatcher.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(value));
        }
        inputMatcher.appendTail(sb);
        return sb.toString();
    }

    // Un lote de Recepción Material puede matchear (LIKE) más de un
    // consecutivo/codigo distinto en Generación de Lotes. Si todos los
    // resultados son el mismo par, se usa directamente (respetando el
    // envoltorio que ya traía el COS en ese punto del formato); si difieren
    // entre sí, se deja un <select> para que el usuario elija cuál aplica; el
    // COS asociado se actualiza con el onchange inline del propio select.
    public static String replaceLoteReception(String html, List lst_GlotesRep, int cnt2, boolean cosEditable) {
        // Clave de deduplicación: ref+cos no basta (dos recepciones distintas
        // del mismo lote pueden compartir "consecutivo", ver código real
        // producción), así que se incluye el codigo crudo (r.codigo) también.
        // Ese codigo crudo es la única columna que permite, más adelante,
        // filtrar los anexos de Generación de Lotes por la recepción exacta
        // que el usuario seleccionó aquí (no solo por lote): queda grabado en
        // el atributo data-ref del span COSn, que sí persiste al guardar el
        // certificado (a diferencia del estado "selected" de un <select>).
        LinkedHashMap<String, String[]> distinct = new LinkedHashMap<String, String[]>();
        for (Object o : lst_GlotesRep) {
            String[] arg = parseResult(o);
            String codigoRaw = arg[1];
            String ref = codigoRaw.replace("M", "");
            String cos = "CC" + arg[0];
            distinct.put(ref + "|" + cos + "|" + codigoRaw, new String[]{ref, cos, codigoRaw});
        }
        if (distinct.size() == 1) {
            String[] only = distinct.values().iterator().next();
            html = html.replace("REF" + cnt2, only[0]);
            String cosSpan = "<span id='COS" + cnt2 + "' data-ref='" + htmlAttr(only[2]) + "'>" + only[1] + "</span>";
            html = html.replace("COS" + cnt2, cosEditable
                    ? "<span  class='editable pending' contenteditable='true'>" + cosSpan + "</span>"
                    : cosSpan);
            return html;
        }
        StringBuilder options = new StringBuilder();
        String firstCos = null;
        String firstRef = null;
        for (String[] vals : distinct.values()) {
            if (firstCos == null) {
                firstCos = vals[1];
                firstRef = vals[2];
            }
            options.append("<option data-cos='").append(vals[1]).append("' data-ref='").append(htmlAttr(vals[2]))
                    .append("'>").append(vals[0]).append("</option>");
        }
        // El COS se reemplaza primero: el <select> que se inserta abajo para
        // REF menciona "COS" + cnt2 dentro de su onchange, y si el reemplazo
        // de COS se hiciera después volvería a matchear (y corromper) ese
        // texto recién insertado en vez de solo el placeholder original.
        html = html.replace("COS" + cnt2, "<span id='COS" + cnt2 + "' data-ref='" + htmlAttr(firstRef) + "'>" + firstCos + "</span>");
        html = html.replace("REF" + cnt2,
                "<select class='editable-select' onchange=\"var o=this.options[this.selectedIndex];var s=document.getElementById('COS"
                + cnt2 + "');s.textContent=o.dataset.cos;s.setAttribute('data-ref', o.dataset.ref);\">" + options + "</select>");
        return html;
    }

    /**
     * Salvedad para cuando Tag.Visual no encuentra el consecutivo de un
     * material buscando por lote + familia de proceso (Connection.
     * ConnectionGeneracionLotes.ConsultarCC_GeneracionLote): el nombre de
     * proceso puede variar más de lo que el código conoce, y el material de
     * Registros LAB puede estar nombrado distinto al de Generación de Lotes.
     * En vez de mostrar "----" directamente, el llamador vuelve a buscar solo
     * por lote (ConsultarCC_GeneracionLoteDetalle) y pasa el resultado aquí:
     * si hay un solo consecutivo, se usa directo; si hay varios (lote
     * compartido entre procesos), se deja elegir — nunca se pierde el dato
     * solo porque el proceso no calzó.
     *
     * @param detalle filas "consecutivo///proceso///fecha" (puede ser null/vacío).
     */
    public static String resolverConsecutivoAmbiguo(String html, int cnt, List<String> detalle) {
        LinkedHashMap<String, String> opciones = new LinkedHashMap<String, String>();
        if (detalle != null) {
            for (String fila : detalle) {
                String[] arg = parseResult(fila);
                if (arg.length == 0 || arg[0].isEmpty() || opciones.containsKey(arg[0])) {
                    continue;
                }
                String proceso = arg.length > 1 ? arg[1] : "";
                String fecha = arg.length > 2 ? arg[2] : "";
                String etiqueta = "CC" + arg[0]
                        + (proceso.isEmpty() ? "" : " - " + proceso)
                        + (fecha.isEmpty() ? "" : " (" + fecha + ")");
                opciones.put(arg[0], etiqueta);
            }
        }

        if (opciones.isEmpty()) {
            return html.replace("COS" + cnt, "<span  class='editable pending' contenteditable='true'>----</span>");
        }
        if (opciones.size() == 1) {
            return html.replace("COS" + cnt, "CC" + opciones.keySet().iterator().next());
        }

        StringBuilder select = new StringBuilder("<select class='editable-select'>");
        for (String etiqueta : opciones.values()) {
            select.append("<option>").append(htmlAttr(etiqueta)).append("</option>");
        }
        select.append("</select>");
        return html.replace("COS" + cnt, select.toString());
    }

    /** Escapa un valor para insertarlo dentro de un atributo HTML simple-comillado (data-ref='...'). */
    private static String htmlAttr(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("'", "&#39;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    /**
     * Extrae los codigos de recepción de material (r.codigo, Generación de
     * Lotes) que quedaron seleccionados en un certificado ya guardado, leyendo
     * los spans id='COSn' data-ref='...' que deja {@link #replaceLoteReception}.
     * Se usa en FileManager.jsp para que el tab "Generación de Lotes" muestre
     * solo los anexos de la recepción puntual elegida, no todas las que
     * comparten el mismo lote.
     */
    public static java.util.List<String> extractSelectedReceptionCodes(String html) {
        java.util.List<String> codigos = new java.util.ArrayList<String>();
        if (html == null) {
            return codigos;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("id=['\"]COS\\d+['\"]\\s+data-ref=['\"]([^'\"]*)['\"]")
                .matcher(html);
        while (m.find()) {
            String codigo = m.group(1)
                    .replace("&#39;", "'")
                    .replace("&quot;", "\"")
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                    .replace("&amp;", "&");
            if (!codigo.isEmpty()) {
                codigos.add(codigo);
            }
        }
        return codigos;
    }

    private static String selectedOptionText(String optionsHtml) {
        java.util.regex.Matcher optionMatcher = java.util.regex.Pattern
                .compile("<option([^>]*)>(.*?)</option>", java.util.regex.Pattern.DOTALL)
                .matcher(optionsHtml);
        String firstText = "";
        while (optionMatcher.find()) {
            String text = optionMatcher.group(2).trim();
            if (firstText.isEmpty()) {
                firstText = text;
            }
            if (optionMatcher.group(1).contains("selected")) {
                return text;
            }
        }
        return firstText;
    }
}
