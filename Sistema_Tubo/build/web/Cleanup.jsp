<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Cleanup.tld" prefix="Cleanup" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paradas | ST</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />

        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Cleanup:Cleanup/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>
        <script>
            function ConsultRegister(temp) {
                if (temp === 1) {
                    document.getElementById("temp1").value = 1;
                    document.getElementById("formRegisterQ").submit();
                }
                if (temp === 2) {
                    document.getElementById("temp1").value = 2;
                    document.getElementById("formRegisterQ").submit();
                }
            }
        </script>
        <script>
            function formChart() {
                document.getElementById("from_chart").submit();
            }
        </script>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/> 

        <!--THIS FILE-->
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <!-------->

        <!-- JS Libraies -->
        <script src="Interfaz/Contenido/assets/modules/chart.min.js"></script>

        <!-- Page Specific JS File -->
        <!--<script src="Interfaz/Contenido/assets/js/page/modules-chartjs.js"></script>-->
        <!--<script src="Interfaz/Contenido/assets/js/page/modules-chartjs_4.js"></script>-->
    </body>
</body>
</html>
