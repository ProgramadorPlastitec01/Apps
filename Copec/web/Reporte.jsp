<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Reporte.tld" prefix="Reporte"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Reporte</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Reporte.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Reporte.jsp');
                });
            </script>
            <script src="Interfaz/Graficas/js/jquery.min.js"></script>
            <script src="Interfaz/Graficas/js/highcharts.js"></script>
            <script src="Interfaz/Graficas/js/modules/exporting.js"></script>
            <script src="Interfaz/Graficas/js/modules/offline-exporting.js"></script>
        </head>
        <body>
            <div id="subpage">
                <div id="templatemo_wrapper">
                <Menu:Menu/>
                <Reporte:Reporte/>
            </div>
            <Alertas:Alertas />
        </div>
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
        <script type="text/javascript" src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>