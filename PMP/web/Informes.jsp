<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Informes.tld" prefix="Informes"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/PMP_MI.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Informes</title>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Informes.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Informes.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!--Post backs-->
                <script type="text/javascript">
                    function PostBackAnio() {
                        var anio = document.getElementById("Cbx_anio");
                        document.forms['FormAnio'].submit();
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Informes:Informes />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>