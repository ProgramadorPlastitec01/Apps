<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_caso.tld" prefix="caso" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
        <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <title>Encuesta</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        </head>
        <body>
            <div id="content">
            <caso:MuestraCaso/>
        </div>
        <resultado:MuestraResultado/>
    </body>
</html>
