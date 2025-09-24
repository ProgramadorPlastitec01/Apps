<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Start.tld" prefix="Start" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio | ST</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fullcalendar/fullcalendar.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
    </head>
    <style>
        h2{
            text-transform: capitalize;
        }
        a{
            text-transform: capitalize;
        }
    </style>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Start:Start/>
                </div>
                <script type="text/javascript">
                    function ChangeDiv(number) {
                        if (number === 1) {
                            document.getElementById("div_start_access").style.display = "none";
                            document.getElementById("div_start_calendar").style.display = "block";
                        } else {
                            document.getElementById("div_start_calendar").style.display = "none";
                            document.getElementById("div_start_access").style.display = "block";
                        }
                    }
                </script>
            </div>
        </div>
        <Alerts:Alert/>
        <script src="Interfaz/Contenido/assets/modules/fullcalendar/fullcalendar.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/fullcalendar/locale-all.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-calendar.js"></script>
        <script src="Interfaz/Contenido/assets/js/fullcalendar/index.global.js"></script>
    </body>
</html>
