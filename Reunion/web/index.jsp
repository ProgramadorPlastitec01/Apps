<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Reunion.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Reunion</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'index.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'index.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <style>
                /* all */
                ::-webkit-input-placeholder { color:#f5f5f6; }
                ::-moz-placeholder { color:#f5f5f6; } /* firefox 19+ */
                :-ms-input-placeholder { color:#f5f5f6; } /* ie */
                input:-moz-placeholder { color:#f5f5f6; }
                .placeholder-white::placeholder { color: grey;}         
            </style>
        </head>
        <body id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%, #AD1457 50%);">
        <Alertas:Alertas/>
    <center>
        <div style="width: 600px;margin-top: 50px;" align='center'>
            <div style="font-size: 1.2em;height: 70px;width:600px;background: linear-gradient(to left,#f5f5f6 50%,#AD1457 20%);color: #f5f5f6;" align='center'>
                <!--<a href="#" style="text-decoration: none;font-size: 2em;color: #f5f5f6;">Registros<span style="color: #f5f5f6" > LAB</span></a>-->
                <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
                <div style='float:left;width:50%;margin-top: 10px'></div>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #28794B;">
                <br /><br /><br />
                <form action="Sesion?opc=1" method="post">
                    <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#AD1457;color: #f5f5f6;border-bottom:2px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                    <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#AD1457;color: #f5f5f6;border-bottom:2px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                    <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #f5f5f6;color:#AD1457'/><br/><br/>
                    <b style='color: #f5f5f6'>Va 01.04.03</b>
                </form>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                <br /><br />
                <img src="Interfaz/Contenido/images/Reunion.png" alt="Logo" width="160" height="160" />
            </div>
            <div style="float: left;width: 600px;height: 150px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                    <p style="color:grey" align="justify"><b>Reuniones</b> Este sistema es el encargado de almacenar y facilitar el manejo de información de las reuniones realizadas por la diferentes areas.
                        <br />El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>segura, rapida </b>y<b> confiable</b>.</p>
                </div>
            </div>
        </div>
    </center>
</body>
</html>
