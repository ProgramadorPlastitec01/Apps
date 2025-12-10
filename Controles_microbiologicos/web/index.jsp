<%-- 
    Document   : index
    Created on : 30/05/2012, 12:09:09 PM
    Author     : a.sistemas2
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_microbiologico.png" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Control Microbiologicos</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'index.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'index.jsp');
            });
        </script>
        <script type="text/javascript" src="Interfaz/Alertas/lib/sweetalert.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert-dev.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>

        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <!-- CSS Principal -->
        <link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
        <link href="Interfaz/Validacion/StyleSheetLiveValidation.css" rel="stylesheet" type="text/css"/>
    </head>
</head>
<body style="background: linear-gradient(to left,#f5f5f6 50%, #A146BF 50% );">
<center>
    <!--<div style="width: 600px">
        <div style="float: left;width: 800px;margin-top: 100px;">
            <div style="float: left;width: 300px;height: 300px;">
                <br /><br /><br />
                <img src="Interfaz/Contenido/images/Control_microbiologico.png" alt="Logo" width="170" height="170" />
                <br />
                <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
    <!-- <h2>Registros LAB<br />Vp. 00.00.00</h2>-->
    <!--</div>
    <div style="float: left;width: 300px;height: 300px;">
        <br /><br /><br />
        <fieldset>
            <legend>Iniciar Sesión</legend>
            <form action="Sesion?opc=1" method="post">
                <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" onchange='javascript:this.value=this.value.toUpperCase();'/><br />
                <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" onchange='javascript:this.value=this.value.toUpperCase();'/><br />
                <input type="submit" value="Iniciar" /><br/><br/>
                <b>Va 01.01.04</b>
            </form>
        </fieldset>
    </div>
    <div style="float: left;width: 600px;height: 170px;background-color: #292929;color: #fff">
        <div style="width: 510px;margin-top: 10px" align="center">
            <p align="justify"><b>Controles Microbiologicos </b>Este sistema de información es el encargado de facilitar el manejo de datos de los analisis, permitiendole al usuario interpretar los controles realizados en las diferentes áreas muestradas de forma estadística en un reporte <b>(Grafico)</b>.<br />El sistema como ayuda virtual permite al usuario acceder a la información de manera
                <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
        </div>
    </div>
</div>
</div>-->
    <div style="width: 600px;margin-top: 100px;" align='center'>

        <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
            <br><br><br><br />
            <img src="Interfaz/Contenido/images/Control_microbiologico2.png" alt="Logo" width="170" height="170" />
        </div>
        <div style="float: left;width: 300px;height: 300px;color: #A146BF;">
            <img src="Interfaz/Contenido/images/templatemo_logo2A.png" alt="Logo" />
            <br /><br /><br /><br /><form action="Sesion?opc=1" method="post">
                <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#f5f5f6;border-bottom:1px solid #A146BF;border-right: none;border-left: none;border-top: none;"  /><br />
                <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#f5f5f6;border-bottom:1px solid #A146BF;border-right: none;border-left: none;border-top: none;"/>
                <input type="submit" value="Iniciar" /><br/><br/>
                <!--<b style='color: #B4045F'>Va 01.01.04</b>-->
                <b style='color: #A146BF'>Va 04.06.07</b>
            </form>
        </div>
        <div style="float: left;width: 600px;height: 150px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
            <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                Este sistema de información es el encargado de facilitar el manejo de datos de los analisis, permitiendole al usuario interpretar los controles realizados en las diferentes áreas muestradas de forma estadística en un reporte (Grafico).El sistema como ayuda virtual permite al usuario acceder a la información de manera
                segura, rapida y confiable para poder realizar en cada uno de los procesos del registro una adecuada manipulación.
            </div>
        </div>
    </div>
</center>
<Alertas:Alertas />
</body>
</html>
