<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/NovedadesTLD.tld" prefix="novedades"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page="master_head.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Novedades.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Novedades.jsp');
                });
            </script>
            <script type="text/javascript">
                function registroN() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function PostBackUbicacion() {
                    var ubicacion = document.getElementById("txt_ubicacion");
                    document.forms['formubicacion'].submit();
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu></menu:menu>
            <novedades:Novedades></novedades:Novedades>
            </div>
        <resultados:Resultados></resultados:Resultados>
        <script src="Calendarios/Js_range.js" type="text/javascript"></script>
        <script src="Calendarios/Js_normal.js" type="text/javascript"></script>
    </body>
</html>
