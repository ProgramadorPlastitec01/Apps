<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Inspeccion_manga_new.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Inspección Manga</title>
        <!-- CSS Principal -->
        <link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
        <!--Validaciones-->
        <script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
        <!--Alertas-->
        <link rel="stylesheet" href="Interfaz/Alertas/dist/sweetalert.css">
        <script src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <!-- CONTROL ENVIO DE PETICIONES -->
        <script type = "text/javascript" >
            history.pushState(null, null, 'index.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'index.jsp');
            });
        </script>
        <style>
            /* all */
            ::-webkit-input-placeholder { color:#f5f5f6; }
            ::-moz-placeholder { color:#f5f5f6; } /* firefox 19+ */
            :-ms-input-placeholder { color:#f5f5f6; } /* ie */
            input:-moz-placeholder { color:#f5f5f6; }
            .placeholder-white::placeholder { color: grey;}         
        </style>
    </head>
    <body id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%, #28794B 50%);">
        <Alertas:Alertas />
    <center>
        <br /><br />
        <div style="width: 600px;margin-top: 50px;" align='center'>
            <div style="font-size: 1.2em;height: 70px;width:600px;background: linear-gradient(to left,#f5f5f6 50%,#28794B 20%);color: #f5f5f6;" align='center'>
                <!--<a href="#" style="text-decoration: none;font-size: 2em;color: #f5f5f6;">Registros<span style="color: #f5f5f6" > LAB</span></a>-->
                <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
                <div style='float:left;width:50%;margin-top: 10px'>CONTROL DE PROCESO EXTRUSIÓN<br/> DE MANGA</div>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #28794B;">
                <br /><br /><br />
                <form action="Sesion?opc=1" method="post">
                    <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#28794B;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                    <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#28794B;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                    <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #f5f5f6;color:#28794B'/><br/><br/>
                    <!--<b style='color: #f5f5f6'>Va 07.26.05</b>-->
                    <!--<b style='color: #f5f5f6'>Va 10.28.06</b>-->
                    <!--<b style='color: #f5f5f6'>Va 10.28.06</b>-->
                    <b style='color: #f5f5f6'>Va 11.28.07</b>
                </form>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                <br /><br />
                <img src="Interfaz/Contenido/images/Inspeccion_manga_new.png" alt="Logo" width="160" height="160" />
            </div>
            <div style="float: left;width: 600px;height: 180px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                    <p align="justify"><b>Inspección Manga </b>Este sistema de información es el encargado de facilitar el manejo de datos de los registros <b>R-PI-011</b> , <b>R-GC-078 / 122 / 159 </b> y <b> R-GC-097</b>.<br />El sistema como ayuda virtual permite al usuario acceder a la información de manera
                        <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
                </div>
            </div>
        </div>
    </center>
</body>
</html>