<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/inicio.tld" prefix="incio" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Inicio | Reporte Pesaje</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'inicio.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'inicio.jsp');
            });
        </script>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <incio:Inicio />
            </div>
        </div>
        <script>
                
        </script>
    </body>
</html>
