<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <title>REDEAC</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Menu.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Menu.jsp');
                });
            </script>
        </head>
        <body>
        <menu:MuestraMenu/>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
        <script>
            function mostrar_opc2(ide) {
                var id = ide;
                if (document.getElementById("opc_section"+ id +"").style.display === "none") {
                    document.getElementById("opc_section"+ id +"").style.display = "block";
                } else if (document.getElementById("opc_section"+ id +"").style.display === "block") {
                    document.getElementById("opc_section"+ id +"").style.display = "none";
                }
        </script>

        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>
