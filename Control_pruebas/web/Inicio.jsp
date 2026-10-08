<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Inicio.tld" prefix="Inicio"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Inicio.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>
        <link type="image/png" href="Interfaz/Contenido/images/Control_pruebas.ico" rel="icon" >
            <link rel="stylesheet" href="Interfaz/Contenido/Css/CSS_Inicio.css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Inicio</title>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Inicio:Inicio />
        </div>
    </body>
</html>