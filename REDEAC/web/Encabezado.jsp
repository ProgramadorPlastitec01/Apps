<!--Principal-->
<link rel="icon" type="image/png" href="Interfaz/Contenido/Images/Logo.png"/>
<link type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet">
<link type="text/css" href="Interfaz/Contenido/Css/bootstrap.css" rel="stylesheet">

<!--Menu-->
<link type="text/css" href="Interfaz/Contenido/Css/Menu.css" rel="stylesheet">
<script type="text/javascript" src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="Interfaz/Contenido/Scripts/Menu.js"></script>
<script src="Interfaz/Contenido/Scripts/bootstrap.js"></script>
<!--style input select-->
<link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
<script type="text/javascript" src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
<!--Lib Icons-->
<link type="text/css" href="Interfaz/FontAwesome/css/all.css" rel="stylesheet">
<!--Validacion-->
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<link type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css" rel="stylesheet">
<!--Alertas-->
<script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<link type="text/css" href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet">
<!--Paginacion-->

<!--Calendarios-->
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<link type="text/css" href="Interfaz/Calendarios/pikaday.css" rel="stylesheet">
<!--Calendar_Boostrap-->
<script type="text/javascript" src="Interfaz/CalendarB/bootstrap-year-calendar.js"></script>
<script type="text/javascript" src="Interfaz/CalendarB/bootstrap-year-calendar.es.js"></script>
<link type="text/css" href="Interfaz/CalendarB/bootstrap-year-calendar.css" rel="stylesheet">
<!--Calendar_Boostrap-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>

<!-- Editores -->
<script type="text/javascript" src="Interfaz/Editor/samples/css/samples.css"></script>
<script type="text/javascript" src="Interfaz/Editor/samples/toolbarconfigurator/lib/codemirror/neo.css"></script>

<script src="Interfaz/Editor/samples/js/sample.js"></script>
<script src="Interfaz/Editor/ckeditor.js"></script>
<script src="Interfaz/Editor/Configuracion.js"></script>

<script type="text/javascript">
    String.prototype.preg_quote = function () {
        p = /([:.\+*?[^\]$(){}=!<>|:)])/g;
        return this.replace(p, "\\$1");
    }
    function buscar(cadena) {
        if (!cadena.length)
            return;
        var info3;
        cadena = cadena.preg_quote();
        var patron = new RegExp(cadena + '(?!\}\})', 'gim');
        var espacio = /^\s$/;
        var el = document.getElementById('resultados').getElementsByTagName('*');
        for (var ii = 0; ii < el.length; ii++) {
            if (el[ii].hasChildNodes && el[ii].nodeName.toLowerCase() != 'title' && el[ii].nodeName.toLowerCase() != 'script' && el[ii].nodeName.toLowerCase() != 'meta' && el[ii].nodeName.toLowerCase() != 'link' && el[ii].nodeName.toLowerCase() != 'style') {
                var tt = el[ii].childNodes;
                for (var jj in tt) {
                    if (tt[jj].nodeType == 3 && !espacio.test(tt[jj].nodeValue)) {
                        patron.lastIndex = 0;
                        if (info3 = patron.exec(tt[jj].nodeValue)) {
                            tt[jj].nodeValue = tt[jj].nodeValue.replace(patron, '{{' + tt[jj].nodeValue.substr(info3['index'], cadena.length) + '}}');

                        }
                    }

                }
            }
        }
        document.getElementById('resultados').innerHTML = document.getElementById('resultados').innerHTML.split('}}').join('</span>').split('{{').join('<span style="background-color: yellow;">');
    }
    window.onload = function () {
        if (document.getElementById('resultados') != null) {
            original = document.getElementById('resultados').innerHTML;
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
        frameDoc.document.write('<link type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet">');
        frameDoc.document.write('<script type="text/javascript" src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js"><\/script>');
        frameDoc.document.write('<link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('<script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"><"\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"><"\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/bezier.js"><\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/jquery.signaturepad.js"><\/"script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/json2.min.js"><\/script>');
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 300);
        return false;
    }
    ;
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
        frameDoc.document.write('<link type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet">');
        frameDoc.document.write('<script type="text/javascript" src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js"><\/script>');
        frameDoc.document.write('<link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('<script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"><"\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"><"\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/bezier.js"><\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/jquery.signaturepad.js"><\/"script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/json2.min.js"><\/script>');
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 300);
        return false;
    }
    ;
    function Imprimir(id) {
        var contenedor = document.getElementById("Imprimir" + id).innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<html><head><title>Imprimir</title>');
        frameDoc.document.write('<link type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet">');
        frameDoc.document.write('<script type="text/javascript" src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js"><\/script>');
        frameDoc.document.write('<link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('<script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"><"\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"><"\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/bezier.js"><\/script>');
        frameDoc.document.write('<script src="Interfaz/Firma/jquery.signaturepad.js"><\/"script>');
        frameDoc.document.write('<script src="Interfaz/Firma/assets/json2.min.js"><\/script>');
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
//        document.getElementById("R005").style.display = "block";
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 300);
        return false;
    }
    ;
    function HabilitarModal() {
        document.getElementById("R005").style.display = "block";
    }
    function usuario(campo) {
        if (campo.checked) {
            document.getElementById("usuariosF-id").value += "[" + campo.value + "]";
        } else {
            document.getElementById("usuariosF-id").value = document.getElementById("usuariosF-id").value.replace("[" + campo.value + "]", "");
        }
    }
    ;
    function VentanaOp(val) {
        window.open(val);
    }
</script>
<script type="text/javascript">
    var tableToExcel = (function () {
        var uri = 'data:application/vnd.ms-excel;base64,'
                , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head>[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]</head><body><table>{table}</table></body></html>'
                , base64 = function (s) {
                    return window.btoa(unescape(encodeURIComponent(s)))
                }
        , format = function (s, c) {
            return s.replace(/{(\w+)}/g, function (m, p) {
                return c[p];
            })
        }
        return function (table, name) {
            if (!table.nodeType)
                table = document.getElementById(table)
            var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
            window.location.href = uri + base64(format(template, ctx))
        }
    })()
</script>

<script type="text/javascript">
    function tableToExcel2(table, name, filename) {
        let uri = 'data:application/vnd.ms-excel;base64,', 
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><title></title><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>', 
        base64 = function(s) { return window.btoa(decodeURIComponent(encodeURIComponent(s))) },         format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; })}
        
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}

        var link = document.createElement('a');
        link.download = filename;
        link.href = uri + base64(format(template, ctx));
        link.click();
}
</script>

<script type="text/javascript" src="https://unpkg.com/xlsx@0.15.1/dist/xlsx.full.min.js"></script>
<script>
    function ExportToExcel(type, fn, dl) {
       var hoy = new Date();
       var fecha = hoy.getFullYear() + '_' + ( hoy.getMonth() + 1 ) + '_' + hoy.getDate();
       var hora = hoy.getHours() + hoy.getMinutes() + hoy.getSeconds();
       var fechaYHora = fecha + '_' + hora;
       var elt = document.getElementById('table_exp');
       var wb = XLSX.utils.table_to_book(elt, { sheet: "sheet1" });
       return dl ?
         XLSX.write(wb, { bookType: type, bookSST: true, type: 'base64' }):
         XLSX.writeFile(wb, fn || ('Reporte_'+ fechaYHora +'.' + (type || 'xlsx')));
    }
</script>


<script type="text/javascript" language="javascript">
    function mostrarConvencion(id) {
        if (document.getElementById("Ventana" + id).style.display === "none") {
            document.getElementById("Ventana" + id).style.display = "block";
        } else if (document.getElementById("Ventana" + id).style.display === "block") {
            document.getElementById("Ventana" + id).style.display = "none";
        }
    }
</script>
<script type="text/javascript" language="javascript">
    function AgregarFiltro() {
        var val = document.getElementById("filtro-id").value;
        var filtro = document.getElementById("filtroF").value;
        if (filtro === "") {
            document.getElementById("filtroF").value = val;
        } else {
            document.getElementById("filtroF").value = filtro + "+" + val;
        }
        var div = document.getElementById("filtroVista");
        var contdiv = document.getElementById("filtroVista").innerHTML;
        div.innerHTML = contdiv + "<b id='P" + val + "'>" + val + "</b>&nbsp;<a href='#' id='I" + val + "' onclick=\"quitarFiltro('" + val + "');\"><i class='fa fa-minus fa-sm' style='color:#292929'></i><br></a>";
        document.getElementById("filtro-id").value = "";
    }
    ;
    function quitarFiltro(val) {
        var valor = document.getElementById("filtroF").value;
        if (valor.includes(val + "+")) {
            document.getElementById("filtroF").value = valor.replace(val + "+", "");
        } else {
            document.getElementById("filtroF").value = valor.replace(val, "");
        }
        var node = document.getElementById("P" + val + "");
        node.parentNode.removeChild(node);
        var node = document.getElementById("I" + val + "");
        node.parentNode.removeChild(node);
    }
</script>



