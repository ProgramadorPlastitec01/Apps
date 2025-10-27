<link href="Interfaz/Contenido/Css/CSS_Menu.css" rel="stylesheet" type="text/css" />
<!--<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
<link href="Interfaz/Contenido/Css/Css_General.css" rel="stylesheet" type="text/css" />-->
<link href="Interfaz/Contenido/Css/CSS_Principal_2.css" rel="stylesheet" type="text/css" />
<link href="Interfaz/Validacion/StyleSheetLiveValidation.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/model.css"/>
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/tooltip.css"/>
<!--<link href="Interfaz/Progressbar/Css_progressbar.css" rel="stylesheet" type="text/css"/>-->
<!--Fonts-->
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/FontAwesome/css/all.css">
<script src="Interfaz/StylePage/js/jquery-3.0.0.min.js" type="text/javascript"></script>
<script src="Interfaz/StylePage/js/prefix-free.js"></script>
<script src="Interfaz/Contenido/Scripts/bootstrap.min.js"></script>
<!--Css-->
<!--Boostrap-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/i18n/defaults-zh_CN.min.js"></script>

<script src="Interfaz/StylePage/js/jquery-3.0.0.min.js" type="text/javascript"></script>
<script src="Interfaz/StylePage/js/prefix-free.js"></script>
<!--<link type="text/css" href="Interfaz/Contenido/Css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">-->
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
<!-- JQuery Calendarios -->
<script src="Calendarios/moment.js" type="text/javascript"></script>
<link href="Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<script src="Calendarios/pikaday.js" type="text/javascript"></script>
<!-- JQuery alertas -->
<script src="Interfaz/Alertas/dist/sweetalert.min.js" type="text/javascript"></script>
<link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
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

<!--<script type="text/javascript">
    $(document).ready(function () {
        $('#summernote').summernote();
    })
</script>-->
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
<!--validacion acciones navegador-->
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
    document.onkeydown = checkKeyCode;
</script>
<script>
    function FiltroAvanzado(e) {
        tecla = (document.all) ? e.keyCode : e.which;
        if (tecla === 43) {
            var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
            if (filtro !== "") {
                document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                document.getElementById('Buscar_valores').innerHTML += "<a href='#' onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\" style='text-decoration:none'>" + filtro + "</a><br />";
            }
            document.getElementById('Txt_filtro_avanzado').value = "";
        }
    }
    function FiltroAvanzadoQuitar(e) {
        var valor = document.getElementById('Txt_valores_filtro').value;
        document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
        var vista = document.getElementById('Buscar_valores').innerHTML;
        var elim = "<a href=\"#\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\" style=\"text-decoration:none\">" + e + "</a><br>";
        document.getElementById('Buscar_valores').innerHTML = "";
        document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
    }
</script>
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
        var el = document.getElementById('resultadosT1').getElementsByTagName('*');
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
        document.getElementById('resultadosT1').innerHTML = document.getElementById('resultadosT1').innerHTML.split('}}').join('</span>').split('{{').join('<span style="background-color: yellow;">');
    }
    window.onload = function () {
        if (document.getElementById('resultadosT1') != null) {
            original = document.getElementById('resultados').innerHTML;
        }
    }
</script>
<!--Imprimir-->
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
        frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal_2.css" rel="stylesheet" type="text/css" />');
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
    function contrasena() {
        document.formRC.submit()
    }
    function contrasenaM() {
        document.formMRC.submit()
    }
</script>