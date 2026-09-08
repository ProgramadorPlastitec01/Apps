<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/user" prefix="User" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@ page import="Method.OfficePlatformService" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/codemirror/lib/codemirror.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/codemirror/theme/duotone-dark.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <script type="text/javascript">
            history.pushState(null, null, 'EditorOffice.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'EditorOffice.jsp');
            });
        </script>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <%--
UBICACIÓN EN NETBEANS: Web Pages -> EditorOffice.jsp (o en tu JSP de vista)
                    --%>

                    <%
                        // Si configuraste las variables en web.xml, se cargan de application (ServletContext):
                        String ctxUrl = application.getInitParameter("OFFICE_PLATFORM_URL");
                        String ctxKey = application.getInitParameter("OFFICE_PLATFORM_API_KEY");
                        if (ctxUrl != null && ctxKey != null) {
                            OfficePlatformService.init(ctxUrl, ctxKey);
                        }

                        String token = (String) request.getAttribute("widgetToken");
                        String server = (String) request.getAttribute("serverUrl");
                        if (token == null || token.isEmpty()) {
                            String cedula = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : "12345678";
                            String nombre = session.getAttribute("Usuario") != null ? session.getAttribute("Usuario").toString() : "Usuario Sistema";
                            token = OfficePlatformService.obtenerToken(cedula, nombre);
                            server = OfficePlatformService.SERVER_URL;
                        }
                    %>

                    <!-- Contenedor del Editor y Gestor Documental (Estilos responsivos listos) -->
                    <div id="office-platform" style="width: 100%; height: 85vh; border: 1px solid #cbd5e1; border-radius: 8px; overflow: hidden; background: #ffffff;"></div>

                    <!-- Script de integración de Office Platform -->
                    <script
                        src="<%= server%>/office-platform-widget.js"
                        data-container="office-platform"
                        data-server="<%= server%>"
                        data-token="<%= token%>">
                    </script>
                </div>
            </div>
            <Alert:Alert/>
        </div>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>

        <script src="Interface/Content/Assets/modules/summernote/summernote-bs4.js"></script>
        <script src="Interface/Content/Assets/modules/codemirror/lib/codemirror.js"></script>
        <script src="Interface/Content/Assets/modules/codemirror/mode/javascript/javascript.js"></script>
    </body>
</html>