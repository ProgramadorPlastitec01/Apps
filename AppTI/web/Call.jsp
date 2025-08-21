<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld"  prefix="Alert"%><%--
--%><%@taglib uri="/WEB-INF/tlds/Tld_call.tld" prefix="Call" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="shortcut icon" href="Interface/Content/Imagen/Icon1.png" />
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css" >
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <title>R-TI-001</title>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Call:Call/>
                </div>
            </div>
        </div>
        <script>
            function redirigir(select) {
            var year = select.value;
            if (year) {
            window.location.href = 'Call?opt=1&Module=General&Yar=' + year;
            }
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
                            background-color: #33bf98; \
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
                    var table = document.getElementById('TableStyle');
                    // Hacer una copia de la tabla para no afectar al HTML original
                    var tableCopy = table.cloneNode(true);
                    // Reemplazar los <hr> por <br> y luego convertirlos en saltos de línea (\n) en la copia de la tabla
                    var rows = tableCopy.getElementsByTagName('tr');
                    for (var i = 0; i < rows.length; i++) {
                    var cells = rows[i].getElementsByTagName('td');
                    for (var j = 0; j < cells.length; j++) {
            var cellContent = cells[j].innerHTML;
            // Reemplazar <hr> por <br>
            cellContent = cellContent.replace(/<hr\s*\/?>/g, '<br>');
            // Convertir <br> en saltos de línea (\n)
            cellContent = cellContent.replace(/<br\s*\/?>/g, '\n');
            cells[j].innerHTML = cellContent;
        }
    }

    var ctx = { worksheet: 'R-TI-001', table: tableCopy.innerHTML };
    var link = document.createElement("a");
    link.download = "R-TI-001.xls";
    link.href = uri + base64(format(template, ctx));
    link.click();
}
        </script>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>
    </body>
</html>
