<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_inicio.tld" prefix="inicio" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <title>Inicio</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Inicio.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Inicio.jsp');
                });
            </script>
        </head>
        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <inicio:MuestraInicio/>
        </div>
        <script>
            var slideIndex = 1;
            showDivs(slideIndex);
            function plusDivs(n) {
                showDivs(slideIndex += n);
            }
            function showDivs(n) {
                var i;
                var x = document.getElementsByClassName("slidesBtc");
                if (n > x.length) {
                    slideIndex = 1
                }
                if (n < 1) {
                    slideIndex = x.length
                }
                for (i = 0; i < x.length; i++) {
                    x[i].style.display = "none";
                }
                x[slideIndex - 1].style.display = "block";
            }
        </script>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>
