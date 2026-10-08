<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <link type="image/png" href="Interfaz/Contenido/images/Control_pruebas.ico" rel="icon" >
        <script type = "text/javascript" >
            history.pushState(null, null, 'index.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'index.jsp');
            });
        </script>
        <title>Control Pruebas</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
    </head>
    <body class="container-login">
    <Alertas:Alertas />
    <div align="center" style="width:40%;background-color: #fff;border-radius: 20px;padding: 20px;">
        <h1 style="font-size: 4.2em;color: #10ac84;">
            <span class="fas fa-clipboard-check fa-size_normal"></span>
        </h1>
        <b style="font-size:30px;color:#10ac84">Control</b><b style="font-size:30px;color:#34495e;"> Pruebas</b><br />
        <p style="color: grey;font-size: 16px;">Sistema de información para el control de pruebas del proceso farmaceutico.</p>
        <b style="font-size: 16px;color:grey;">Iniciar Sesión</b><br /><br />
        <form action="Sesion?opc=1" method="post">
            <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="font-size: 16px;width: 90%;background-color:#f5f5f6;border-bottom:1px solid #15aabf;border-right: none;border-left: none;border-top: none;" autocomplete="off"/><br />
            <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="font-size: 16px;width: 90%;background-color:#f5f5f6;border-bottom:1px solid #15aabf;border-right: none;border-left: none;border-top: none;" autocomplete="off"/>
            <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" /><br/><br/>
            <b>Va 02.07.03</b>
        </form>
        <p style="color: grey;font-size: 14px">&copy; PLASTITEC - 2022</p>
    </div>
    </body>
</html>
