<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Error 500</title>
        <script type = "text/javascript" >
            history.pushState(null, null, '500.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, '500.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
    </head>
    <body>
        <div class="piel">
            <center>
                <br /><br /><br /><br /><br /><br />
                <div style="width: 600px;margin-top: 100px;" align='center'>
                    <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                        <b style="font-size:42px;color:f5f5f6">SIRH</b>
                        <h1 style="font-size: 3.2em;color: #f5f5f6;">
                            <span class="fab fa-hornbill fa-size_normal" style="color: #f5f5f6"></span>
                        </h1>
                    </div>
                    <div style="float: left;width: 300px;height: 300px;">
                        <b class="negro" style="font-size:120px">500</b><br />
                        <b style="font-size:18px">Tiempo en sesión finalizado</b><br />
                        <b class="negro" style="font-size:18px"><a href="index.jsp">Volver</a></b>
                    </div>
                </div>
            </center>
        </div>
    </body>
</html>
