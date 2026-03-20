<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Empleado.tld" prefix="Empleado"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Personal</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Empleado.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Empleado.jsp');
                });
            </script>
            <link rel="stylesheet" href="Interfaz/Template/Css/flotante.css" media="screen">
            <link rel="stylesheet" href="Interfaz/fontawesome/css/all.min.css">
            <script type="text/javascript">
                function PostBackUbicacion() {
                    var Ubicacion = document.getElementById("idA");
                    document.forms['formUbicacion'].submit();
                }
            </script>
            <script type="text/javascript">
                function mostrar(i) {
                    document.getElementById('prueba' + i + '').style.display = 'block';
                }
            </script>
            <script type="text/javascript">
                function cerrar(i) {
                    document.getElementById('prueba' + i + '').style.display = 'none';
                }
            </script>
        </head>
        <body>
            <div id="subpage">
                <div id="templatemo_wrapper">
                <Menu:Menu/>
                <Empleado:Empleado/>
            </div>
                <Alertas:Alertas />
        </div>
        <script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
    </body>
</html>
