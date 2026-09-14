<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Inicio.tld" prefix="Inicio"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_formulas_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <!--Controlar url-->
            <script type = "text/javascript" >
                history.pushState(null, null, 'Inicio.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Inicio.jsp');
                });
            </script>
            <title>Inicio</title>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!-- CSS Principal -->
                <link href="Interfaz/Contenido/Css/Css_Principal_New.css" rel="stylesheet" type="text/css" />
                <!-- CSS Menu -->
                <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Menu.css" />
                <!-- JQuery desplega menu -->
                <script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
                <!-- JQuery desplega menu -->
                <script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
                <!-- JavaScript desplega menu -->
                <script type="text/javascript">
                ddsmoothmenu.init({
                    mainmenuid: "templatemo_menu", //menu DIV id
                    orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
                    classname: 'ddsmoothmenu', //class added to menu's outer DIV
                    //customtheme: ["#1c5a80", "#18374a"],
                    contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
                })
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <div style="float: right;width: 530px;height: 170px;background-color: #292929;color: #fff">
                <center>
                    <div style="width: 450px;margin-top: 20px">
                        <p align="justify"><b>Control Formulas </b>Este sistema de información es el encargado de facilitar la asignación de materias primas a las diferentes formulas y generar el manual de registro
                            <b>Control uso de lotes o sublotes de materias primas en formulas R-PI-004</b>. El sistema como ayuda virtual permite al usuario acceder a la información de manera<b> segura, rapida </b>y<b>
                                confiable</b> para poder realizar en cada uno de los procesos de las formulas una adecuada manipulación.</p>
                    </div>
                </center>
            </div>
            <Inicio:Inicio />
        </div>
    </body>
</html>