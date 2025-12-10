<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Inicio.tld" prefix="Inicio"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/CVP.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Inicio</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Inicio.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <!--<div style="float: right;width: 530px;height: 210px;background-color: #292929;color: #fff">
                <center>
                    <div style="width: 500px;margin-top: 20px" align="center">
                        <p style="color:#ffffff" align="justify"><b>CVP </b> (Calificación y validación de procesos) Este sistema de información es el encargado de facilitar el manejo del control dimensional de las bolsas, diligenciados en los registros
                            <br />El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
                    </div>
                </center>
            </div>-->
            <Inicio:Inicio />
        </div>
    </body>
</html>