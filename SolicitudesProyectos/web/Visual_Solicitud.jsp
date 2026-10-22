<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/pendiente.tld" prefix="Pendiente" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SP | Visual</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <Pendiente:Pendiente/>
            </div>
        </div>
        <script>
            function abrirVentana() {
                // Abre una nueva ventana con el contexto dinamico de la aplicacion
                window.open("<%= request.getContextPath() %>/", "_blank");
            }
        </script>
        <%-- jQuery debe ir antes de Bootstrap y modules-toastr.js: ambos
             requieren $ en el nivel superior del archivo. Sin este orden se
             lanza '$ is not defined' y ningun listener posterior se registra. --%>
        <script src="Interfaz/Contenido/assets/modules/jquery.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
    </body>
</html>
