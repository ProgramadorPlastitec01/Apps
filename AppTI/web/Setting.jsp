<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_setting.tld" prefix="Sett" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fullcalendar/fullcalendar.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
    </head>
    <body>
        
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Sett:Setting/>
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
        
        <script src="Interface/Content/Assets/modules/fullcalendar/fullcalendar.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/fullcalendar/locale-all.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-calendar.js"></script>
        <script src="Interface/Content/Assets/js/fullcalendar/index.global.js"></script>
    </body>
</html>
