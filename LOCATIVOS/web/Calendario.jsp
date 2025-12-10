<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Inicio.tld" prefix="Inicio"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
       <title>Locativos MT</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Programacion.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Programacion.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Inicio:Inicio/>
        </div>
        <Alertas:Alertas />
    </body>
</html>
