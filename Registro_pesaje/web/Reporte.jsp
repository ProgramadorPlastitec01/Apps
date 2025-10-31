<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Reporte.tld" prefix="Reporte" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte | Registro Pesaje</title>
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
<!--        <script type = "text/javascript" >
            history.pushState(null, null, 'Reporte.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Reporte.jsp');
            });
        </script>-->
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <Resultado:ResultadosAlertas/>
                <Reporte:Reporte/>
            </div>
        </div>
    </body>
</html>
