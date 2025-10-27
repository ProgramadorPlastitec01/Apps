<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_usuario.tld" prefix="usuario" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
        <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <title>Usuario</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        </head>
        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <usuario:MuestraUsuario/>
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
