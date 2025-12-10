<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/PMP_MI.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>PMP</title>
        <!-- CSS Principal -->
        <link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />
        <style>
            /* all */
            ::-webkit-input-placeholder { color:#f5f5f6; }
            ::-moz-placeholder { color:#f5f5f6; } /* firefox 19+ */
            :-ms-input-placeholder { color:#f5f5f6; } /* ie */
            input:-moz-placeholder { color:#f5f5f6; }
            .placeholder-white::placeholder { color: grey;}  
        </style>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
    </head>
    <body id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%, #016279 50%);">
        <!--<div style='background-color:#c10937;color:#FFF;' align='center'><MARQUEE>............VERSION DE PRUEBA  CAMILO YO VERE ...........</MARQUEE></div>-->
    <Alertas:Alertas />
<center>
    <div style="width: 600px;margin-top: 100px;" align='center'>
        <div style="font-size: 1.2em;height: 70px;width:600px;background: linear-gradient(to left,#f5f5f6 50%,#016279 20%);color: #f5f5f6;" align='center'>
            <!--<a href="#" style="text-decoration: none;font-size: 2em;color: #f5f5f6;">Registros<span style="color: #f5f5f6" > LAB</span></a>-->
            <img src="Interfaz/Contenido/images/templatemo_logo.png"  />
            <div style='float:left;width:50%;margin-top: 10px'>PROGRAMA DE MANTENIMIENTO PREVENTIVO MTTO INSUMOS</div>
        </div>
        <div style="float: left;width: 300px;height: 300px;color: #016279;">
            <br /><br /><br /><form action="Sesion?opc=1" method="post">
                <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#016279;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#016279;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #f5f5f6;color:#016279'/><br/><br/>
                <b style='color: #f5f5f6'>VA 05.22.10</b>
                <!--<b style='color: #f5f5f6'>Va 03.20.07</b>-->
            </form>
        </div>
        <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
            <br /><br />
            <img src="Interfaz/Contenido/images/PMP_MI.png" alt="Logo" width="200" height="190"/>
        </div>
        <div style="float: left;width: 600px;height: 180px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
            <div style="width: 500px;margin-top: 20px;text-align: justify;background-color:#f5f5f6 " align="left">
                <p align="justify"><b>PMP MI </b>Este sistema de información es el encargado de alertar y controlar la información de las diferentes actividades programadas en las ordenes de trabajo para el cada equipo<b>(maquina)</b> de la oraganización.<br />El sistema como ayuda virtual permite al usuario acceder a la información de manera
                    <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
            </div>
        </div>
    </div>
</center>
</body>
<!--<body>
<center>
    <div style="width: 600px">
        <div style="float: left;width: 800px;margin-top: 100px;">
            <div style="float: left;width: 300px;height: 300px;">
                <br /><br /><br />
                <img src="Interfaz/Contenido/images/PMP_MI.png" alt="Logo" width="200" height="190" />
                <br />
                <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
<!-- <h2>Registros LAB<br />Vp. 00.00.00</h2>
</div>
<div style="float: left;width: 300px;height: 300px;">
<br /><br /><br />
<fieldset class="login_user">
    <legend>Iniciar Sesión</legend>
    <form action="Sesion?opc=1" method="post">
        <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" onchange='javascript:this.value = this.value.toUpperCase();'/><br />
        <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" onchange='javascript:this.value = this.value.toUpperCase();'/><br />
        <input type="submit" value="Iniciar" /><br/><br/>
        <b>Va 01.09.02</b>
    </form>
</fieldset>
</div>
<div style="float: left;width: 600px;height: 170px;background-color: #292929;color: #fff">
<div style="width: 450px;margin-top: 20px" align="center">
    <p align="justify"><b>PMP MI </b>Este sistema de información es el encargado de alertar y controlar la información de las diferentes actividades programadas en las ordenes de trabajo para el cada equipo<b>(maquina)</b> de la oraganización.<br />El sistema como ayuda virtual permite al usuario acceder a la información de manera
        <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
</div>
</div>
</div>
</div>
</center>
</body>-->
</html>
