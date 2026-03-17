<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Programa de verificacion metrologica</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Menu.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Menu.jsp');
            });
        </script>
    </head>
    <body id="subpage" onload='AlertPendiete()'>
        <div id="templatemo_wrapper">
            <menu:MuestraMenu />
        </div>
    <resultados:MuestraResultados/>
</body>
<script src="Interfaz/Acordeon/Js_accordeon.js"></script>
</html>
