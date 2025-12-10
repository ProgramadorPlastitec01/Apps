<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Solicitud.tld" prefix="solicitud"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<%@taglib uri="/WEB-INF/Tlds/Programacion.tld" prefix="Programacion"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
       <title>Locativos MT</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Ventanas_emergentes.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Ventanas_emergentes.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
    </head>
    <body id="subpage" style="background:#FFF url(Interfaz/Contenido/images/pattern.png) repeat top left;">
        <div id="templatemo_wrapper">
            <solicitud:Solicitud/>
            <Programacion:Programacion/>
            <Alertas:Alertas />
        </div>
    </body>
</html>
