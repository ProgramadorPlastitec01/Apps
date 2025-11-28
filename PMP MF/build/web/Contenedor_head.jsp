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
<script type="text/javascript">
    function VolverModulo() {
        try {
            var enlace = document.getElementById("Txt_enlace_volver").value;
            location.href = enlace;
        } catch (error) {
            alert("En este módulo no se puede volver.");
        }
    }
</script>
<link href="Interfaz/Progressbar/Css_progressbar.css" rel="stylesheet" type="text/css"/>
<!--Fonts-->
<link rel="stylesheet" type="text/css" href="Interfaz/StylePage/FontAwesome/css/all.css">
<script src="Interfaz/StylePage/js/jquery-3.0.0.min.js" type="text/javascript"></script>
<script src="Interfaz/StylePage/js/prefix-free.js"></script>
<!--Css-->
<link href="Interfaz/StylePage/css/sb-admin-2.min.css" rel="stylesheet">
<link href="Interfaz/StylePage/css/My_css.css" rel="stylesheet">
<link href="Interfaz/StylePage/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<!--Validaciones-->
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
<!--Alertas-->
<link rel="stylesheet" href="Interfaz/Alertas/dist/sweetalert.css">
<script src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<!--Paginacion-->
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<!--Filtrar-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<!--Calendarios-->
<link rel="stylesheet" type="text/css" href="Interfaz/Calendarios/pikaday.css">
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<!--Scripts-->
<script type="text/javascript" src="Interfaz/Scripts/JS_Firma.js"></script>
<!--Imprimir-->
<script language="javascript">
    function Imprimir() {
        var objeto = document.getElementById('Div_export');  //obtenemos el objeto a imprimir
        var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
        ventana.document.write('<link href="Interfaz/StylePage/css/Table_export.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.write(objeto.innerHTML); //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.write('<link href="Interfaz/StylePage/css/Table_export.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
        ventana.document.close();  //cerramos el documento
        ventana.print(); //imprimimos la ventana
//        ventana.close();  //cerramos la ventana
    }
</script>
<!--Excel-->
<script>
    function exportTableToExcel(tableID, filename = ''){
        var downloadLink;
        var dataType = 'application/vnd.ms-excel';
        var tableSelect = document.getElementById(tableID);
        var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
// Specify file name
        filename = filename ? filename + '.xls' : 'excel_data.xls';
// Create download link element
        downloadLink = document.createElement("a");
        document.body.appendChild(downloadLink);
        if (navigator.msSaveOrOpenBlob) {
            var blob = new Blob(['ufeff', tableHTML], {
                type: dataType
            });
            navigator.msSaveOrOpenBlob(blob, filename);
        } else {
// Create a link to the file
            downloadLink.href = 'data:' + dataType + ', ' + tableHTML;

// Setting the file name
            downloadLink.download = filename;
//triggering the function
            downloadLink.click();
        }
    }
</script>