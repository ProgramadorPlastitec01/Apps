<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/recipiente.tld" prefix="recipiente" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Recipiente | Registro Pesaje</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'recipiente.jsp');
            window.addEventListener('popstate', function (event) {
            history.pushState(null, null, 'recipiente.jsp');
            });
        </script>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
        <div class="cont_total2" id="cont_total">
            <div style="width: 100%; margin-top: 10px;">
            <recipiente:Recipiente />
            </div>
        </div>
            <Resultado:ResultadosAlertas />
    </body>
</html>
