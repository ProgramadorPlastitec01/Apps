<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_visor.tld" prefix="Visor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>visor</title>
        <!--Fonts-->
        <link href="Interfaz/Contenido/Css/fonts.css" rel="stylesheet" type="text/css" media="all" />
        <script src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/FontAwesome/css/all.css">
        <script src="Interfaz/Contenido/Scripts/prefix-free.js"></script>
        <link rel="icon" type="image/png" href="Interfaz/Contenido/images/Ico.ico"/>
        <link href="Interfaz/HTML_Editor/demo/demo.css" rel="stylesheet" type="text/css" />
        <link type="text/css" rel="stylesheet" href="Interfaz/HTML_Editor/jquery-te-1.4.0.css">
        <script type="text/javascript" src="Interfaz/HTML_Editor/jquery.min.js" charset="utf-8"></script>
        <script type="text/javascript" src="Interfaz/HTML_Editor/jquery-te-1.4.0.min.js" charset="utf-8"></script>
        <script>
            function time() {
                setTimeout(function () {
                    swal("Atencion!", "Se cerrara la session por inactividad", "warning");
                }, 1770000);
            }
        </script>
        <script type="text/javascript">
            function color(campo, color) {
                if (color === 1) {
                    campo.style.color = "blue";
                } else {
                    campo.style.color = "black";
                }
            }
        </script>
        <script type="text/javascript">
            function firmar() {
                var plantilla = document.getElementById("textarea").value;
                document.getElementById("textareaD").value = plantilla;
                document.formFirm.submit();
            }
        </script>
        <!-- Acordeon -->
        <link rel="stylesheet" href="Interfaz/Acordeon/Css_accordeon.css">
    </head>
    <body onbeforeunload="Salir()">
        <Visor:Visor />
        <script>
            $('.jqte-test').jqte();
            // settings of status
            var jqteStatus = true;
            $(".status").click(function ()
            {
                jqteStatus = jqteStatus ? false : true;
                $('.jqte-test').jqte({"status": jqteStatus})
            });
        </script>
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
    </body>
</html>
