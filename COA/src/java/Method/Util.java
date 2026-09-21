package Method;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Util {
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

    // Un lote de Recepción Material puede matchear (LIKE) más de un
    // consecutivo/codigo distinto en Generación de Lotes. Si todos los
    // resultados son el mismo par, se usa directamente (respetando el
    // envoltorio que ya traía el COS en ese punto del formato); si difieren
    // entre sí, se deja un <select> para que el usuario elija cuál aplica; el
    // COS asociado se actualiza con el onchange inline del propio select.
    public static String replaceLoteReception(String html, List lst_GlotesRep, int cnt2, boolean cosEditable) {
        LinkedHashMap<String, String> distinct = new LinkedHashMap<String, String>();
        for (Object o : lst_GlotesRep) {
            String[] arg = parseResult(o);
            String ref = arg[1].replace("M", "");
            String cos = "CC" + arg[0];
            distinct.put(ref + "|" + cos, ref + "|" + cos);
        }
        if (distinct.size() == 1) {
            String[] only = distinct.keySet().iterator().next().split("\\|");
            html = html.replace("REF" + cnt2, only[0]);
            html = html.replace("COS" + cnt2, cosEditable
                    ? "<span  class='editable pending' contenteditable='true'>" + only[1] + "</span>"
                    : only[1]);
            return html;
        }
        StringBuilder options = new StringBuilder();
        String firstCos = null;
        for (String key : distinct.keySet()) {
            String[] parts = key.split("\\|");
            if (firstCos == null) {
                firstCos = parts[1];
            }
            options.append("<option data-cos='").append(parts[1]).append("'>").append(parts[0]).append("</option>");
        }
        // El COS se reemplaza primero: el <select> que se inserta abajo para
        // REF menciona "COS" + cnt2 dentro de su onchange, y si el reemplazo
        // de COS se hiciera después volvería a matchear (y corromper) ese
        // texto recién insertado en vez de solo el placeholder original.
        html = html.replace("COS" + cnt2, "<span id='COS" + cnt2 + "'>" + firstCos + "</span>");
        html = html.replace("REF" + cnt2,
                "<select class='editable-select' onchange=\"document.getElementById('COS" + cnt2 + "').textContent=this.options[this.selectedIndex].dataset.cos;\">" + options + "</select>");
        return html;
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
