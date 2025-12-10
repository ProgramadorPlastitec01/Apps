<%@page import="java.util.Random"%>
<!-- CONTROL ENVIO DE PETICIONES  -->
<script language="javascript">
    function checkKeyCode(evt)
    {
        var evt = (evt) ? evt : ((event) ? event : null);
        var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        if (event.keyCode == 116)
        {
            evt.keyCode = 0;
            return false
        }
    }
    document.onkeydown = checkKeyCode;</script>
<script type="text/javascript">
    var statsend = false;
    function checkSubmit() {
        if (!statsend) {
            statsend = true;
            return true;
        } else {
            alert(" Un momento por favor el formulario se esta enviando...");
            return false;
        }
    }
</script>
<!--<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800|Open+Sans+Condensed:300,700" rel="stylesheet" />-->
<%
    try {
        String css = request.getSession().getAttribute("Color").toString();
        out.print("<link   href = \"Interfaz/MasterPage/" + css + "\" rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
    } catch (Exception e) {
        Random rn = new Random();
        int answer = rn.nextInt(18) + 1;
        switch (answer) {
            case 1:
                out.print("<link href = \"Interfaz/MasterPage/DarkBlue.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 2:
                out.print("<link href = \"Interfaz/MasterPage/Blue.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 3:
                out.print("<link href = \"Interfaz/MasterPage/Teal.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 4:
                out.print("<link href = \"Interfaz/MasterPage/Yellow.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 5:
                out.print("<link href = \"Interfaz/MasterPage/Red.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 6:
                out.print("<link href = \"Interfaz/MasterPage/DarkRed.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 7:
                out.print("<link href = \"Interfaz/MasterPage/Green.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 8:
                out.print("<link href = \"Interfaz/MasterPage/DarkGreen.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 9:
                out.print("<link href = \"Interfaz/MasterPage/LightGreen.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 10:
                out.print("<link href = \"Interfaz/MasterPage/Orange.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 11:
                out.print("<link href = \"Interfaz/MasterPage/DarkOrange.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 12:
                out.print("<link href = \"Interfaz/MasterPage/LightRed.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 13:
                out.print("<link href = \"Interfaz/MasterPage/default.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 14:
                out.print("<link href = \"Interfaz/MasterPage/LightPurple.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 15:
                out.print("<link href = \"Interfaz/MasterPage/Purple.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 16:
                out.print("<link href = \"Interfaz/MasterPage/DarkPurple.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 17:
                out.print("<link href = \"Interfaz/MasterPage/Brown.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
            case 18:
                out.print("<link href = \"Interfaz/MasterPage/Aqua.css\"  rel = \"stylesheet\" type = \"text/css\" media = \"all\" />");
                break;
        }
    }
%>
<!--Fonts-->
<link href="Interfaz/MasterPage/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="Interfaz/MasterPage/jquery-3.0.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/MasterPage/FontAwesome/css/all.css">
<script src="Interfaz/MasterPage/prefix-free.js"></script>
<!--Paginación-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<link href="Interfaz/Paginas/Emerge.css" rel="stylesheet"  type="text/css" media="all" />
<!--Validaciones-->
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
<!--Alertas-->
<link rel="stylesheet" href="Interfaz/Alertas/dist/sweetalert.css">
<script src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<!--Calendarios-->
<link rel="stylesheet" type="text/css" href="Interfaz/Calendarios/pikaday.css">
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<!--HTML EDITOR-->
<script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
<script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
<script type="text/javascript">
    function Informe() {
        var htmleditor = document.getElementsByName("HTML_Editor").innerHTML;
        document.getElementsByName("Txt_descripcion").value = htmleditor;
        document.Form_informe.submit();
    }
</script>
<!--Tabs
<link href="Interfaz/Tabs/tabs.css" rel="stylesheet" type="text/css"/>-->
<!--Acoordeon-->
<!--<link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css"/>-->
<link rel="stylesheet" type="text/css" href="Interfaz/Acordeon/Acordeon_principal.css">
<link rel="stylesheet" href="Interfaz/Acordeon/Css_accordeon.css">
<link href="Interfaz/Graficas/Progress_bar.css" rel="stylesheet" type="text/css"/>
<!--Range
    <link href="Interfaz/Range/Range.css" rel="stylesheet" type="text/css" />
<script src="Interfaz/Range/Range1.js"></script>
    <script src="Interfaz/Range/Range2.js"></script>-->
<!--Firmas-->
<link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
<!--Imprimir-->
<script type="text/javascript">
    function Imprimir() {
        var contenedor = document.getElementById("Imprimir").innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<link href="Interfaz/Parallax/styles/style.css" rel="stylesheet" type="text/css" />');
//frameDoc.document.write('<link href="Interfaz/MasterPage/default.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 50);
        return false;
    }
    function Imprimir_basico() {
        var contenedor = document.getElementById("Imprimir_basico").innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
                frameDoc.document.open();
//frameDoc.document.write('<link href="Interfaz/Parallax/styles/style.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('<link href="Interfaz/MasterPage/default.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 50);
        return false;
    }
    function Imprimir_informe() {
        var contenedor = document.getElementById("Imprimir_informe").innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<html><head><title>Imprimir Informe</title>');
        frameDoc.document.write('<link href="Interfaz/MasterPage/default.css" rel="stylesheet" type="text/css"/>');
        frameDoc.document.write('<link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css"/>');
        frameDoc.document.write('<link href="Interfaz/Graficas/Progress_bar.css" rel="stylesheet" type="text/css"/>');
        frameDoc.document.write('</head><body style="background-color:#fff">');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 300);
        return false;
    }
    function CambiarColor(color) {
        location.href = 'Sesion?opc=4&color=' + color + '';
    }
</script>
<!--Excel-->
<script >
            var tableToExcel = (function() {     var uri = 'data:application/vnd.ms-excel;base64,'
//, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
                    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
, base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
, format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
return function(table, name) {
if (!table.nodeType) table = document.getElementById(table)
var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
window.location.href = uri + base64(format(template, ctx))
}
})()
</script>