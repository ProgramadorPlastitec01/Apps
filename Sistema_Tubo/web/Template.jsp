<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Template.tld" prefix="Template" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<meta http-equiv=”Content-Type” content=”text/html; charset=ISO-8859-1″ />-->
        <title>Plantillas | ST</title>

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/codemirror/lib/codemirror.css">
        <!--<link rel="stylesheet" href="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.css">-->

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/codemirror/theme/duotone-dark.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Template:Template/>
                </div>
            </div>
        </div>
        <script>
            function SwitchValue() {
                if (document.getElementById('Nmb_est').value == 0) {
                    document.getElementById("Nmb_est").value = 1;
                } else {
                    document.getElementById("Nmb_est").value = 0;
                }
            }
        </script>
        <Alerts:Alert/>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/codemirror/mode/javascript/javascript.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script src="Interfaz/Contenido/assets/modules/codemirror/lib/codemirror.js"></script>
    </body>
</html>
