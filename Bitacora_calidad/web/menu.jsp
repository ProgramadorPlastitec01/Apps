<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'menu.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'menu.jsp');
                });
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu></menu:menu>
            </div>
        <resultados:Resultados />
    </body>
</html>
