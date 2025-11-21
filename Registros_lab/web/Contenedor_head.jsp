<!-- CONTROL ENVIO DE PETICIONES  -->
<script language="javascript">
    function checkKeyCode(evt)
    {
        var evt = (evt) ? evt : ((event) ? event : null);
        var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        if (event.keyCode === 116)
        {
            evt.keyCode = 0;
            return false;
        }
    }
    document.onkeydown = checkKeyCode;
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
<!--Fonts-->
<link href="Interfaz/Contenido/Css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/FontAwesome/css/all.css">
<script src="Interfaz/Contenido/Scripts/prefix-free.js"></script>
<!--Validaciones-->
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
<!-- CSS Principal -->
<link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />
<!-- CSS Menu -->
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Menu.css" />
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
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
<link href="Interfaz/Tabs/tabs.css" rel="stylesheet" type="text/css" />
<!--Alertas-->
<link rel="stylesheet" href="Interfaz/Alertas/dist/sweetalert2018.css">
<script src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<!--Calendarios-->
<link rel="stylesheet" type="text/css" href="Interfaz/Calendarios/pikaday.css">
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<!-- Menu flotante -->
<script src="Interfaz/Contenido/Scripts/jquery-1.10.2.js"></script>
<style>
    #toggle {
        float: left;
        width: 240px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
    }
</style>
<!--Filtrar-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<!-- JavaScript paginacion-->
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<!--Acoordeon-->
<!--<link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css"/>-->
<link rel="stylesheet" type="text/css" href="Interfaz/Acordeon/Acordeon_principal.css">
<link rel="stylesheet" href="Interfaz/Acordeon/Css_accordeon.css">
<!--Inprimir-->
<script language="javascript">
    //document.getElementById('copies-settings-box').style.display = 'hidden';
    function Imprimir() {
        var objeto = document.getElementById('Imprimir');  //obtenemos el objeto a imprimir
        var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
        ventana.document.write(objeto.innerHTML); //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.close();  //cerramos el documento
        ventana.print(); //imprimimos la ventana
//        ventana.close();  //cerramos la ventana
        //location.href='Materia_prima?opc=1&itk=0';
    }
</script>
<script language="javascript">
    //document.getElementById('copies-settings-box').style.display = 'hidden';
    function Imprimir_mp() {
        var objeto = document.getElementById('Imprimir_mp');  //obtenemos el objeto a imprimir
        var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
        ventana.document.write(objeto.innerHTML); //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.close();  //cerramos el documento
        ventana.print(); //imprimimos la ventana
        ventana.close();  //cerramos la ventana
        //location.href='Materia_prima?opc=1&itk=0';
    }
</script>
<!--Excel-->
<script language="javascript">
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
