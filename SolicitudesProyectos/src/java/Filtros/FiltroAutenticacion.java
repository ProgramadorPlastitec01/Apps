package Filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtro de autenticacion central del aplicativo.
 *
 * POR QUE EXISTE
 * Los Servlets no validaban sesion: usaban request.getSession() (que CREA una
 * sesion si no hay) y no verificaban al usuario antes de procesar el opc. La
 * seguridad vivia solo en la visibilidad del menu, que se saltea con un POST
 * directo. Este filtro pone la validacion en un unico punto, antes de que
 * cualquier Servlet o JSP protegido se ejecute.
 *
 * Deja pasar sin sesion solo lo publico: la pantalla de login, el propio
 * Servlet Login y los recursos estaticos (css/js/imagenes/fuentes bajo
 * Interfaz). Todo lo demas exige una sesion con el atributo "id" presente; si
 * no, redirige a index.jsp (para navegacion) o responde 401 (para peticiones
 * AJAX/no-HTML), sin llegar nunca al Servlet.
 */
public class FiltroAutenticacion implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Sin estado: el filtro no guarda nada por peticion.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String contexto = request.getContextPath();
        String uri = request.getRequestURI();
        String ruta = uri.substring(contexto.length());

        if (esPublico(ruta)) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession sesion = request.getSession(false);
        boolean autenticado = (sesion != null && sesion.getAttribute("id") != null);
        if (autenticado) {
            chain.doFilter(req, resp);
            return;
        }

        // No autenticado: nunca se llega al Servlet.
        if (esPeticionAjax(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.sendRedirect(contexto + "/index.jsp");
        }
    }

    /**
     * Rutas que se sirven sin sesion: login, la pantalla inicial, la raiz y los
     * recursos estaticos. La comparacion es sobre la ruta sin el context path.
     *
     * @param ruta ruta de la peticion sin el context path (ej. "/Plano")
     * @return true si es publica
     */
    private boolean esPublico(String ruta) {
        if (ruta == null || ruta.isEmpty() || ruta.equals("/")) {
            return true;
        }
        if (ruta.equals("/index.jsp") || ruta.equals("/Login")) {
            return true;
        }
        // Recursos estaticos: toda la carpeta de interfaz y las extensiones tipicas.
        if (ruta.startsWith("/Interfaz/")) {
            return true;
        }
        String r = ruta.toLowerCase();
        return r.endsWith(".css") || r.endsWith(".js") || r.endsWith(".png")
                || r.endsWith(".jpg") || r.endsWith(".jpeg") || r.endsWith(".gif")
                || r.endsWith(".ico") || r.endsWith(".svg") || r.endsWith(".woff")
                || r.endsWith(".woff2") || r.endsWith(".ttf") || r.endsWith(".map");
    }

    /**
     * Distingue peticiones AJAX (para responder 401 en vez de redirigir a una
     * pantalla que el cliente JS no espera).
     *
     * @param request la peticion
     * @return true si parece una peticion asincrona
     */
    private boolean esPeticionAjax(HttpServletRequest request) {
        String conXhr = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(conXhr);
    }

    @Override
    public void destroy() {
        // Sin recursos que liberar.
    }
}
