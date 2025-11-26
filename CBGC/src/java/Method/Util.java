package Method;

import java.util.Map;

public class Util {
    // Convierte "[a///b///c]" en {"a","b","c"}
    public static String[] parseResult(Object obj) {
        if (obj == null) return new String[0];
        return obj.toString().replace("[", "").replace("]", "").split("///");
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
