<link rel="icon" type="image/png" href="Interfaz/Contenido/images/Ico.ico"/>
<link href="Interfaz/Contenido/Css/CSS_Menu.css" rel="stylesheet" type="text/css" />
<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
<link href="Interfaz/Validacion/StyleSheetLiveValidation.css" rel="stylesheet" type="text/css" />
<!--Fonts-->
<link href="Interfaz/Contenido/Css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/FontAwesome/css/all.css">
<script src="Interfaz/Contenido/Scripts/prefix-free.js"></script>
<!-- filtro -->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery alertas -->
<script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<!-- Menu flotante -->
<script src="Interfaz/Contenido/Scripts/jquery-1.10.2.js"></script>
<!-- JavaScript calendarios -->
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<link href="Interfaz/Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<!-- JavaScript Tooltip -->
<link href="Interfaz/Tooltip/css/tooltipster.bundle.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-borderless.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-light.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-noir.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-punk.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-shadow.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="Interfaz/Tooltip/js/tooltipster.bundle.min.js"></script>
<!-- JavaScript html editor -->
<style type="text/css" media="screen">@import "Interfaz/Tabs/tabs.css";</style>
<!-- JavaScript desplega menu -->
<script type="text/javascript">
    ddsmoothmenu.init({
        mainmenuid: "templatemo_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    })
</script>
<script type="text/javascript">
    function mostrarEmergente(id) {
        if (document.getElementById("Convecion" + id).style.display === "none") {
            document.getElementById("Convecion" + id).style.display = "block";
        } else if (document.getElementById("Convecion" + id).style.display === "block") {
            document.getElementById("Convecion" + id).style.display = "none";
        }
    }
    function mostrarVentana(ids) {
        if (document.getElementById("Ventana" + ids).style.display === "none") {
        document.getElementById("Ventana" + ids).style.display = "block";
        } else if (document.getElementById("Ventana" + ids).style.display === "block") {
        document.getElementById("Ventana" + ids).style.display = "none";
        }
    }
</script>
<!--Excel-->
            <script>
    var tableToExcel = (function() {             var uri = 'data:application/vnd.ms-excel;base64,'
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
                        frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css"/>');
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
<script type="text/javascript">
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
<script>
            function time() {
                setTimeout(function () {
                    swal("Atencion!", "Se cerrara la session por inactividad", "warning");
                }, 1770000);
            }
            function agregarUM(check, id) {
                id = "[" + id + "]";
                if (check.checked) {
                    var campo = document.getElementById("usuario-id").value;
                    campo = campo + id;
                    document.getElementById("usuario-id").value = campo;
                } else {
                    var campo = document.getElementById("usuario-id").value;
                    campo = campo.replace(id, "");
                    document.getElementById("usuario-id").value = campo;
                }
                document.getElementById("usuario-id").value = campo;
            }
            function agregarUP(check, id) {
                id = "[" + id + "]";
                if (check.checked) {
                    var campo = document.getElementById("usuario-idP").value;
                    campo = campo + id;
                    document.getElementById("usuario-idP").value = campo;
                } else {
                    var campo = document.getElementById("usuario-idP").value;
                    campo = campo.replace(id, "");
                    document.getElementById("usuario-idP").value = campo;
                }
                document.getElementById("usuario-idP").value = campo;
            }
            function idP(id, est) {
                document.getElementById("idP").value = id;
                document.getElementById("estP").value = est;
            }
            function idMV(id) {
                document.getElementById("idMV").value = id;
            }
</script>
<style type="text/css">
    html, body
    {
        font-family: verdana,sans-serif;
        margin: 0;
        padding: 5px;
    }
</style>
<!-- Menu flotante -->
<style>
    #toggle {
        float: left;
        width: 240px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;

    }
    #toggle2 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;   

    }
    #toggle3 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
    #toggle4 {
        position: absolute;
        float: right;
        width: 300px;
        font-size: 14px;
        right: 215px;
        background-color: #fff;
    }
    #toggle5 {
        float: left;
        width: 242px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
</style>
