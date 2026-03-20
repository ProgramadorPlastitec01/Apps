<link rel="icon" type="image/png" href="Interfaz/Template/images/Ico.ico" />
<link rel="stylesheet" href="Interfaz/Template/Css/CSS_Principal.css" media="screen">
<!--link rel="stylesheet" href="Interfaz/Template/Css/CSS_Principal_1.css" media="screen"-->
<link rel="stylesheet" href="Interfaz/Template/Css/CSS_Menu.css" media="screen">
<!-- reloj-->
<script type="text/javascript" src="Interfaz/TimeCircle/inc/jquery.min.js"></script>
<script type="text/javascript" src="Interfaz/TimeCircle/inc/TimeCircles.js"></script>
<link rel="stylesheet" href="Interfaz/TimeCircle/inc/TimeCircles.css" media="screen">
<script type="text/javascript" src="Interfaz/Template/Scripts/JS_Menu_Min.js"></script>
<!-- Acordeon -->
<link rel="stylesheet" href="Interfaz/Acordeon/Css_accordeon.css">
<!-- JavaScript calendarios -->
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<link href="Interfaz/Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<script type="text/javascript" src="Interfaz/Template/Scripts/JS_Menu.js"></script>
<script type="text/javascript">
    ddsmoothmenu.init({
        mainmenuid: "templatemo_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    })
</script>
<link rel="stylesheet" href="Interfaz/LiveValidation/StyleSheetLiveValidation.css" media="screen">
<script type="text/javascript" src="Interfaz/LiveValidation/LiveValidation.js"></script>
<style type="text/css" media="screen">@import "Interfaz/Tabs/tabs.css";</style>
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
<script type="text/javascript" language="javascript">
    function Imprimir() {
        var contenedor = document.getElementById("Imprimir").innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<html><head><title>Imprimir</title>');
        frameDoc.document.write('<link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('<link href="Interfaz/Template/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />');
        //frameDoc.document.write('<link href="Interfaz/Template/Css/CSS_Principal_1.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('</head><body>');
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
</script>
<script type="text/javascript">     var tableToExcel = (function() {
            var uri = 'data:application/vnd.ms-excel;base64,'
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
<script  type="text/javascript">
            window.history.go(1);</script> 



<!-- Dependencies alertas 
<script src="Interfaz/alertas/jquery.js" type="text/javascript"></script>
                <script src="Interfaz/alertas/jquery.ui.draggable.js" type="text/javascript"></script>-->

<!-- Core files alertas 
<script src="Interfaz/alertas/jquery.alerts.js" type="text/javascript"></script>
<link href="Interfaz/alertas/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" /> -->


<script type="text/javascript" src="Interfaz/alertasweet/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/alertasweet/sweetalert.css">
<script type="text/javascript" src="Interfaz/alertasweet/alertas_copec.js"></script>
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<SCRIPT type="text/javascript">

            $(document).ready(function () {
    setTimeout(function () {
    $(".mensajes").fadeOut(800).fadeIn(800).fadeOut(500).fadeIn(500).fadeOut(300);
    }, 3000);
    });
</SCRIPT>



