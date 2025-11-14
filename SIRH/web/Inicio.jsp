<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/tlds/Inicio.tld" prefix="Inicio" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Inicio.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container">
            <div id="page">
                <Inicio:Inicio />
                <Alertas:Alertas />
                <script src="Interfaz/Calendarios/Js_range.js"></script>
                <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
                <script src="Interfaz/Calendarios/Js_normal.js"></script>
                <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
            </div>
        </div>
    </body>
</html>
