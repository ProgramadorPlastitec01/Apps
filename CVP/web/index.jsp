<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/CVP.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>CVP</title>
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
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%,#007C2A 50%);overflow-y: hidden;">
        <Alertas:Alertas />
    <center>
        <div style="width: 600px;margin-top: 120px; color: #f5f5f6;" align='center'>
            <div style="float: left;width: 300px;height: 300px;">
                <br />
                <br />
                <h3 style='color:#f5f5f6'>CALIFICACION Y VALIDACION DE <br>PROCESOS</h3>
                <br />
                <form action="Sesion?opc=1" method="post">
                    <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" onchange='javascript:this.value = this.value.toUpperCase();' style="background-color:#007c2a;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/><br />
                    <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#007c2a;color: #f5f5f6;border-bottom:1px solid; color:#f5f5f6;border-right: none;border-left: none;border-top: none;"/><br />
                    <br/><input type="submit" value="Iniciar" style='background-color: #f5f5f6;color:#15aabf;color:#007C2A' /><br/>
                    <br /><b style='color:#fff'>Va 02.07.02</b>
                </form>
            </div>
            <div style="float: left;width: 300px;height: 300px;">
                <br /><br /><br />
                <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
                <img src="Interfaz/Contenido/images/CVP.png" alt="Logo" /><br>
                <br />
                <!-- <h2>Registros LAB<br />Vp. 00.00.00</h2>-->
            </div>
            <div style="float: left;width: 600px;height: 180px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;border-radius:20px;">
                <div style="width: 500px;margin-top: 20px;text-align: justify; color: grey; align:justify;" align="left">
                    <b> CVP </b>  (Calificación y validación de procesos) Este sistema de información es el encargado de facilitar la evidencia documentada que proporciona un alto grado de seguridad de los procesos de manera consistente, produciendo productos que cumplen las especificaciones y características de calidad predeterminados. 
                    <br /><br />El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos de la calificacón y validación una adecuada manipulación.
                </div>
            </div>
        </div>
    </center>
</body>
</html>
