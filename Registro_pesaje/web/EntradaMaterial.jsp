
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/WEB-INF/tlds/Resultado.tld" prefix="Resuldato" %>
<%@taglib uri="/WEB-INF/tlds/EntradaMaterial.tld" prefix="EntradaMaterial" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Entrada Material | Registro Pesaje</title>
        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'EntradaMaterial.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'EntradaMaterial.jsp');
                    });
                </script>-->
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <Resuldato:ResultadosAlertas/> 
                <EntradaMaterial:EntradaMaterial/>
            </div>
                    </div>
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
    background-color: #04B4CC; \
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

    var ctx = { worksheet: 'R-PRF-015', table: tableCopy.innerHTML };
    var link = document.createElement("a");
    link.download = "R-PRF-015.xls";
    link.href = uri + base64(format(template, ctx));
    link.click();
}


        </script>
    </body>
</html>
