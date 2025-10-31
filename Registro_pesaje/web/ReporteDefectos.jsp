<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/ReporteDefectos.tld" prefix="Defectos"%>
<%@taglib  uri="/WEB-INF/tlds/Resultado.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Defectos | Registro Pesaje</title>
        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'ReporteDefectos.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'ReporteDefectos.jsp');
                    });
                </script>-->
        <style>
            .tab {
                overflow: hidden;
                border: 1px solid #ccc;
                background-color: #f1f1f1;
                justify-content: center;
            }

            /* Estilos para los botones de los tabs */
            .tab button {
                background-color: inherit;
                float: left;
                border: none;
                outline: none;
                cursor: pointer;
                padding: 14px 16px;
                transition: 0.3s;
            }

            /* Cambia el color de fondo del botón cuando está activo */
            .tab button.active {
                background-color: #2f323e;
                color: white;
                font-weight: bold;
            }

            /* Estilos para el contenido de los tabs */
            .tabcontent2 {
                display: none;
                padding: 6px 12px;
                border: 1px solid #ccc;
                border-top: none;
                margin-left: 0;

            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <Defectos:ReporteDefecto/>
                <Alertas:ResultadosAlertas/>
            </div>
        </div>
        <script>
            function openTab(evt, tabName) {
            var i, tabcontent, tablinks;
// Oculta todos los elementos con la clase "tabcontent"
            tabcontent = document.getElementsByClassName("tabcontent2");
            for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
            }

// Elimina la clase "active" de todos los botones de los tabs
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
            }

// Muestra el contenido del tab seleccionado y marca el botón como activo
            document.getElementById(tabName).style.display = "block";
            evt.currentTarget.className += " active";
            }
        </script>
        <script>
            function printSection(el) {
            var getFullContent = document.body.innerHTML;
            var printsection = document.getElementById(el).innerHTML;
            document.body.innerHTML = printsection;
            window.print();
            document.body.innerHTML = getFullContent;
            }
        </script>
                    <script>
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
                        } \
                        .tableSGLT td { \
                        padding: 0px 10px 0px 10px; \
                        border: 1px solid #c1c1c1; \
                        } \
                        th { \
                        background-color: #f6f6f6; \
                        } \
                        .thColor { \
                         font-weight: bold; \
                         background-color: #f33155; \
                         color: #f6f6f6; \
                        } \
                        .Std td { \
                        background: #5ecbeb;\
                        color: black;\
                        font-weight: bold; \
                        } \
                        </style></head><body> \
                        <table class="tableSGLT" id="TableStyle">{table}</table> \
                        </body></html>';
                    var base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) };
                    var format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) };
                    // Obtener el div que contiene la información
                    var table = document.getElementById('TableStyleDiv');
                    // Crear una nueva tabla a partir del contenido del div
                    var tableHtml = '<table><tbody>';
                    var rows = table.getElementsByClassName('table-container')[0].children;
                    tableHtml += '<tr>';
                    for (var i = 0; i < rows.length; i++) {
                    if (rows[i].classList.contains('table-header-colspan')) {
                    tableHtml += '<th colspan="18" class="thColor">' + rows[i].innerHTML + '</th>';
                    }
                    }
                    tableHtml += '</tr>';
                    // Crear la fila de encabezados
                    tableHtml += '<tr>';
                    for (var i = 0; i < rows.length; i++) {
                    if (rows[i].classList.contains('table-header')) {
                    tableHtml += '<th>' + rows[i].innerHTML + '</th>';
                    }
                    }
                    tableHtml += '</tr>';
                    // Crear filas de celdas
                    var cellCount = 0;
                    var cellRow = '';
                    for (var i = 0; i < rows.length; i++) {
                    if (rows[i].classList.contains('table-cell')) {
                    if (cellCount % 18 === 0 && cellCount !== 0) {
                    tableHtml += '<tr>' + cellRow + '</tr>';
                    cellRow = '';
                    }
                    cellRow += '<td>' + rows[i].innerHTML + '</td>';
                    cellCount++;
                    }
                    }
                    if (cellRow !== '') {
                    tableHtml += '<tr>' + cellRow + '</tr>';
                    }

                    tableHtml += '<tr>';
                    for (var i = 0; i < rows.length; i++) {
                    if (rows[i].classList.contains('table-headerUltTotales')) {
                    tableHtml += '<th colspan="18"  class="thColor">' + rows[i].innerHTML + '</th>';
                    }
                    }
                    tableHtml += '</tr>';
                    // Añadir la fila de totales
                    tableHtml += '<tr>';
                    for (var i = 0; i < rows.length; i++) {
                    if (rows[i].classList.contains('table-headerUlt')) {
                    tableHtml += '<th>' + rows[i].innerHTML + '</th>';
                        }
                    }
                    tableHtml += '</tr>';
                    tableHtml += '</tbody></table>';

                    // Configurar el contexto para el template
                    var ctx = { worksheet: 'Reporte Defectos', table: tableHtml };

                    // Crear el enlace para la descarga
                    var link = document.createElement("a");
                    link.download = "Reporte Defectos.xls";
                    link.href = uri + base64(format(template, ctx));
                    link.click();
                }
        </script>
    </body>
</html>
