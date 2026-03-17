<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Alerta.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/tlds/Inicio.tld" prefix="InicioPvm" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PVM | Inicio</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                <div class="main-content" style="min-height: 694px;">
                <InicioPvm:inicio/>
                </div>
            </div>
        </div>
        <Alertas:LanzarAlertas/>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        
        <!--<script src="Interfaz/Contenido/Graficas/js/jquery-1.9.1.js"></script>-->
<!--        <script src="Interfaz/Contenido/Graficas/js/JS_1GRAFICS.js"></script>
        <script src="Interfaz/Contenido/Graficas/js/JS_2GRAFICS.js"></script>
        <script src="Interfaz/Contenido/Graficas/js/JS_3GRAFICS.js"></script>
        <script src="Interfaz/Contenido/Graficas/js/JS_4GRAFICS.js"></script>-->
        <!--<script src="Interfaz/Contenido/Graficas/js/highcharts-regression.js"></script>-->

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        
        <!--<script src="Interfaz/Contenido/assets/js/page/index.js"></script>-->
        <!-- Template JS File -->
        <!--<script src="Interfaz/Contenido/assets/js/scripts.js"></script>-->
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>
        
    </body>
</html>
