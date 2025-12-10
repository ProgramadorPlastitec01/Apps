<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css" />
<link rel="icon" type="image/png" href="Interfaz/Contenido/images/Bitacora.ico" />
<link href="Interfaz/Contenido/Css/CSS_Menu.css" rel="stylesheet" type="text/css" />
<script src="Interfaz/Alertas/dist/sweetalert.min.js" type="text/javascript"></script>
<link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Contenido/Css/Css_Principal_New.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
<!--Filtrar-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<!-- CSS acordion -->
<link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css"/>
<script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
<script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
<script src="Calendarios/moment.js" type="text/javascript"></script>
<link href="Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<script src="Calendarios/pikaday.js" type="text/javascript"></script>
<script type="text/javascript">
    function contrasena() {
        document.getElementById("pass-id").value = '2018';  // id del campo contraseña 
        document.formRC.submit();  /// dar name al formulario 
    }
</script>
<script type="text/javascript">
    function contrasenaM() {
        document.getElementById("passM-id").value = '1';  // id del campo contraseña 
        document.form1.submit();  /// dar name al formulario 
    }
</script>
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
    ddsmoothmenu.init({
        mainmenuid: "templatemo_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    })
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
        frameDoc.document.write('<link href="Interfaz/Contenido/Css/Css_Principal_New.css" rel="stylesheet" type="text/css" />');
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