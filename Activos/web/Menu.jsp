<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
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
            <div id="popUp2" class="modal2" onclick="javascript:document.getElementById('popUp2').style.display = 'none'">
                <img class="modal2-content" id="imgReq" style="width: auto; height: auto;"/>
            </div>
        </div>
    </body>
</html>