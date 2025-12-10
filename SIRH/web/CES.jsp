<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>CES</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'CES.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'CES.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
    </head>
    <body>
    </body>
</html>
