<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
        <title>Locativos MT</title>
        <!-- CONTROL ENVIO DE PETICIONES-->
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
        <!-- CSS Principal -->
        <link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
        <!--Validaciones-->
        <script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
        <!--Alertas-->
        <link rel="stylesheet" href="Interfaz/Alertas/dist/sweetalert.css">
        <script src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
    </head>
    <body id="subpage" id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%, #b33939 50%);">
        <!--<div style='background-color:#c10937;color:#FFF;' align='center'><MARQUEE>............VERSION DE PRUEBA  CAMILO YO VERE ...........</MARQUEE></div>-->
        <Alertas:Alertas />
    <center>
        <div style="width: 600px;margin-top: 100px;" align='center'>
            <div style="font-size: 1.2em;height: 70px;width:600px;background: linear-gradient(to left,#f5f5f6 50%,#b33939 20%);color: #f5f5f6;" align='center'>
                <!--<a href="#" style="text-decoration: none;font-size: 2em;color: #f5f5f6;">Registros<span style="color: #f5f5f6" > LAB</span></a>-->
                <img src="Interfaz/Contenido/images/titulo_loc.png" alt="Logo" style='width: 30%;height:30%' />
                <div style='float:left;width:50%;margin-top: 10px'>GESTIÓN DE ARREGLOS LOCATIVOS</div>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #b33939;">
                <br /><br /><br /><form action="Sesion?opc=1" method="post">
                    <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#b33939;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                    <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#b33939;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                    <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #f5f5f6;color:#b33939'/><br/><br/>
                    <b style='color: #f5f5f6'>Va 04.13.09</b>
                </form>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                <br />
                <img src="Interfaz/Contenido/images/locativos.png" alt="Logo" style='width: 65%;height:65%'/>
            </div>
            <div style="float: left;width: 600px;height: 130px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                    <p style="color:grey" align="justify"><b>Locativos MT </b>Este sistema de información es el encargado de facilitar el canal de comunicacion de las áreas con <b>Mtto General</b> en la solicitud de arreglos locativos.<br />El sistema como ayuda virtual permite al usuario acceder a la información de manera
                        <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos de los locativos un adecuado manejo.</p>
                </div>
            </div>
        </div>
    </center>
</body>
</html>
