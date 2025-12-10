<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menus" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_microbiologico.png" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Inicio</title>
            <jsp:include page="Encabezado.jsp"></jsp:include>
        </head>
        <body id="subpage">
        <Menus:Menu/>
            <div id="templatemo_wrapper">
            <div style="float: right;width: 530px;height: 150px;background-color: #292929;color: #fff">
                <center>
                    <div style="width: 510px;margin-top: 10px" align="center">
                        <p align="justify"><b>Controles Microbiologicos </b>Este sistema de información es el encargado de facilitar el manejo de datos de los analisis, permitiendole al usuario interpretar los controles realizados en las diferentes áreas muestradas de forma estadística en un reporte <b>(Grafico)</b>.<br />El sistema como ayuda virtual permite al usuario acceder a la información de manera
                            <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
                    </div>
                </center>
            </div>
            <Inicio:Inicio />
        </div>
    </body>
</html>