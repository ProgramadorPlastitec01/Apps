package Method;

import javax.servlet.http.Part;

/**
 * Utilidades para javax.servlet.http.Part compatibles con Servlet 3.0.
 *
 * Producción corre sobre Tomcat 7 (Servlet 3.0), que NO tiene
 * Part.getSubmittedFileName() (agregado en Servlet 3.1). Llamarlo compila si
 * el classpath trae la API 3.1, pero revienta en ejecución con
 * NoSuchMethodError. Por eso el nombre se lee del header Content-Disposition.
 */
public final class PartUtil {

    private PartUtil() {
    }

    /**
     * Nombre del archivo enviado (solo el nombre, sin ruta del cliente; IE
     * viejo manda la ruta completa). Devuelve "" si el part no es un archivo.
     */
    public static String getFileName(Part part) {
        String header = part.getHeader("content-disposition");
        if (header == null) {
            return "";
        }
        for (String token : header.split(";")) {
            String t = token.trim();
            if (t.toLowerCase().startsWith("filename=")) {
                String name = t.substring("filename=".length()).trim();
                if (name.length() >= 2 && name.startsWith("\"") && name.endsWith("\"")) {
                    name = name.substring(1, name.length() - 1);
                }
                int slash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
                return slash >= 0 ? name.substring(slash + 1) : name;
            }
        }
        return "";
    }
}
