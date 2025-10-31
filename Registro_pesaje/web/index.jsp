<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Result" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio sesion | Registro Pesaje</title>
        <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/Inicio_sesion.css">
        <!--Alertas-->
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <!--Font Awesome icons-->
        <link rel="stylesheet" href="Interfaz/Contenido/fontawesome/css/all.css">
        <!--Validacion-->
        <script type="text/javascript" src="Interfaz/Contenido/Validacion/LiveValidation.js"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Validacion/StyleSheetLiveValidation.css">
    </head>
    <body class="container-login">
        <Result:ResultadosAlertas />
    <center>
        <div>
            <div align='center'>
                <div class="container" id="container">
                    <div class="form-container sign-in-container">
                        <form action="Login?opc=1" method="post">
                            <div ><img src="Interfaz/Contenido/Imagenes/Logo.png" alt="homepage" style="width: 43%;"></div>
                            <h1>Iniciar Sesión</h1><br>
                            <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" autocomplete="off" />
                            <div style="display: flex; width: 100%;">
                                <input name="Txt_password" ID="txtPassword" placeholder="Contraseña" type="Password" Class="form-control" autocomplete="off" />
                                <button id="show_password" class="button-75" type="button" onclick="mostrarPassword()">
                                    <i class="fas fa-eye"></i> 
                                </button>
                            </div>
                            <button class="bn39" href="inicio.jsp"><span class="bn39span">LOGIN</span></button><br>
                            <!--<b style='color: #000;size: 13px;'>VP. 00.00.00</b><br>-->
                            <b style='color: #000;size: 13px;'>VA. 00.00.00</b><br>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </center>

    <script type="text/javascript">
        function mostrarPassword() {
            var cambio = document.getElementById("txtPassword");
            if (cambio.type == "password") {
                cambio.type = "text";
                $('.icon').removeClass('far fa-eye-slash').addClass('far fa-eye');
            } else {
                cambio.type = "password";
                $('.icon').removeClass('far fa-eye').addClass('far fa-eye-slash');
            }
        }

        $(document).ready(function () {
            //CheckBox mostrar contraseña
            $('#ShowPassword').click(function () {
                $('#Password').attr('type', $(this).is(':checked') ? 'text' : 'password');
            });
        });
    </script>

</body>
</html>
