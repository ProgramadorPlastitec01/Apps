<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_Start.tld" prefix="Starting" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio | SGLT</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Start.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Start?opt=1');
            });
        </script>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fullcalendar/fullcalendar.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png" />
    </head>
    <body>
        
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Base.jsp"></jsp:include>
                <div class="main-content" style="min-height: 694px;">
                    <Starting:Initial/>
                </div>
            </div>
        </div>
        <script src="Interfaz/Contenido/assets/modules/fullcalendar/fullcalendar.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/fullcalendar/locale-all.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-calendar.js"></script>
        <script src="Interfaz/Contenido/assets/js/fullcalendar/index.global.js"></script>
    </body>
</html>
