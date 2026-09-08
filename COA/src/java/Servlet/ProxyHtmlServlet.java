package Servlet;

import Method.RemoteHtmlFetcher;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProxyHtmlServlet")
public class ProxyHtmlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processProxyRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processProxyRequest(request, response);
    }

    private void processProxyRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String targetUrl = request.getParameter("proxyUrl");
        if (targetUrl == null || targetUrl.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("<h3>Error: URL de destino no especificada.</h3>");
            return;
        }

        Map<String, String> postParams = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String param = paramNames.nextElement();
            if (!"proxyUrl".equals(param)) {
                postParams.put(param, request.getParameter(param));
            }
        }

        String appBaseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";

        try {
            String html = RemoteHtmlFetcher.fetch(targetUrl.trim(), postParams.isEmpty() ? null : postParams, appBaseUrl);
            response.getWriter().print(html);
        } catch (Exception ex) {
            getServletContext().log("Error en ProxyHtmlServlet al conectar con " + targetUrl, ex);
            response.getWriter().print("<h3>Error al cargar el documento remoto: " + ex.getMessage() + "</h3>");
        }
    }
}
