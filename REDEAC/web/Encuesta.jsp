<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_encuesta.tld" prefix="Encuesta" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Encuesta</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <!--HTML editor-->
            <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
            <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
            <script type="text/javascript">
                function mostrarC(d) {
                    var cod = document.getElementById("Cbox" + d);
                    if (cod.checked) {
                        document.getElementById("txt_cop" + d).style.display = "block";
                        document.getElementById("txt_cop" + d).value = 1;
                    } else {
                        document.getElementById("txt_cop" + d).style.display = "none";
                        document.getElementById("txt_cop" + d).value = "";
                    }
                }
                function seleccionar_todo() {
                    for (i = 0; i < document.form1.elements.length; i++) {
                        if (document.form1.elements[i].type === "checkbox") {
                            document.form1.elements[i].checked = 1;
                        }
                        if (document.form1.elements[i].type === "number") {
                            document.form1.elements[i].style.display = "block";
                            document.form1.elements[i].value = 1;
                        }
                    }
                }
                function deseleccionar_todo() {
                    for (i = 0; i < document.form1.elements.length; i++) {
                        if (document.form1.elements[i].type === "checkbox") {
                            document.form1.elements[i].checked = 0
                        }
                        if (document.form1.elements[i].type === "number") {
                            document.form1.elements[i].style.display = "none";
                            document.form1.elements[i].value = 0;
                        }
                    }
                }
                function Enviar_caso() {
                    window.onload = document.getElementById("Formulario").style.display = "none";
                    window.onload = document.getElementById("Carga").style.display = "block";
                }
            </script>
        </head>
        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <Encuesta:MuestraEncuesta/>
        </div>
        <resultado:MuestraResultado/>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>
