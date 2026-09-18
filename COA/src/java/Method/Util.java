package Method;

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
}
