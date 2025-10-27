<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu_consulta.tld" prefix="menu" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>REDEAC</title>
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        
            <script type = "text/javascript" >
                history.pushState(null, null, 'Menu_consulta.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Menu_consulta.jsp');
                });
            </script>
        </head>
        <body>
        <menu:MuestraMenuConsulta/>
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

