<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_nota.tld" prefix="nota" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Nota</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function registroN() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <nota:MuestraNotas />
        </div>
        <resultados:MuestraResultados />
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>
