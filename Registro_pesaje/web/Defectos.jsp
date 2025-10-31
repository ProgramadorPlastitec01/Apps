<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Defectos.tld" prefix="Defectos" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Defecto | Registro Pesaje</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Defectos.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Defectos.jsp');
            });
        </script>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div id="cont_total" class="cont_total2">
                <div style="width: 100%; margin-top: 10px;">
                <Defectos:Defectos/>
                <Resultado:ResultadosAlertas/>
            </div>
        </div>
    </body>
</html>
