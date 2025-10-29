<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Inicio.tld" prefix="inicio" %>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
        </head>
        <body>
            <div id="templatemo_wrapper">
            <menu:Menu/>
            <inicio:Inicio/>
            <alertas:Alertas/>
        </div>
    </body>
</html>
