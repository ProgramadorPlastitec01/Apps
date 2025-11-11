
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="tld_alert" %>
<%@taglib uri="/WEB-INF/tlds/tld_Report.tld" prefix="Report" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>R-SEG-SGLT-003 | Historial Contraparte</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png" />
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/tablesglt.css">
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.16.9/xlsx.full.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js"></script>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/datepickerJQ.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Base.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Report:Report/>
                </div>
            </div>
            <tld_alert:AlertModule/>
        </div>

        <script type="text/javascript">
            function exportToExcel() {
                    var uri = 'data:application/vnd.ms-excel;base64,';
                    var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><style>\
            .tableSGLT { \
            width: 100%; \
            table-layout: auto; \
            } \
            .tableSGLT tr { \
            font-size: 10px; \
            text-align: center; \
            border: 1px solid #c1c1c1; \
            } \
            .tableSGLT td { \
            padding: 0px 10px 0px 10px; \
            border: 1px solid #c1c1c1; \
            } \
            th { \
            background-color: #04B4CC; \
            } \
            .Std td { \
            background: #5ecbeb;\
            color: black;\
            font-weight: bold; \
            } \
            </style></head><body> \
            <table class="tableSGLT" id="TableStyle">{table}</table> \
            <img src="Interfaz/Contenido/Imagen/Logo.png" style="width: 211px; height: 72px;" alt="Logo"></body></html>';
                                var base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) };
                                var format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) };
                                var table = document.getElementById('TableStyle');
                                var ctx = { worksheet: 'R-SEG-SGLT-003', table: table.innerHTML }; var link = document.createElement("a");               link.download = "R-SEG-SGLT-003.xls";
            link.href = uri + base64(format(template, ctx));
            link.click();
            }

            // Aplicar estilos Sticky a las columnas verticales
            var stickyElements = document.querySelectorAll('.StickyVTH');
            stickyElements.forEach(function(element) {
            element.classList.add('Sticky');
            });

            // Aplicar estilos Sticky a las columnas verticales en el cuerpo de la tabla
            var stickyVTBElements = document.querySelectorAll('.StickyVTB');
            stickyVTBElements.forEach(function(element) {
            element.classList.add('Sticky');
            });
        </script>
         <script type="text/javascript" src="Interfaz/Contenido/assets/js/paging.js"></script>
        <script src="Interfaz/Contenido/Scripts/jquery-3.5.1.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/jquery-ui.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/datepicker-es.js"></script>
        <script>
                                var jQuery = $.noConflict();
                                jQuery(function () {
                                jQuery("#InitialDate").datepicker({
                                onSelect: function (selectedDate) {
                                var fechaInicio = jQuery(this).datepicker('getDate');
                                fechaInicio.setDate(fechaInicio.getDate()); // Sumar un día
                                jQuery("#EndDate").datepicker('option', 'minDate', fechaInicio); // Bloquear fechas anteriores
                                jQuery("#EndDate").datepicker('setDate', fechaInicio); // Establecer fecha mínima y predeterminada
                                }
                                });
                                jQuery("#EndDate").datepicker({
                                minDate: 1, // Bloquear fechas anteriores a mañana
                                });
                                // Configurar el idioma del calendario a español
                                jQuery.datepicker.setDefaults(jQuery.datepicker.regional['es']);
                                });
        </script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
    </body>
</html>
