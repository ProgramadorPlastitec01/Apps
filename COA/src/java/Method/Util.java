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
}
