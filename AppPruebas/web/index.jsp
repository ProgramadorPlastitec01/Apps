<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/login.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
        <title>Login | ST</title>
    </head>
    <body>
        <jsp:include page="libraries.jsp"></jsp:include>
        <div class="sweet-local" tabindex="-1" id="Ventana1" style="opacity: 1.03; display:none;">
            <div class="cont_reg">
                <div style="display: flex; justify-content: space-between">
                    <h2>Sistema de tubo</h2>
                    <button class="btn btn-outline-secondary" onclick="mostrarConvencion(1)" style="height: 30px;padding: 3px;width: 30px;"><i class="fas fa-times"></i></button>
                </div>
                <div class="cont_form_user">
                    <p>Este sistema de información, es el encargado de facilitar el manejo de datos de los registros <b style="color:#00281b">R-GC-040</b> y <b style="color:#00281b">R-GC-209</b>.</br> El sistema como ayuda virtual, permite al usuario acceder a la información de manera <b style="color:#00281b">segura, rapida</b> y <b style="color:#00281b">confiable</b> para poder realizar en cada uno de los procesos del registros una adecuada manipulación.</p>
                </div>
            </div>
        </div>
        
        <div id="main">
            <div class="" id="D" style="color: #6ea7a5;">
                <h1>SOPORTE<br>APLICATIVOS</h1>
            </div>
            <div>
                <div class="cont_icon" onclick="mostrarConvencion(1)">
                    <i class="fas fa-question"></i>
                </div>
                <!--<h1>SISTEMA DE TUBO</h1>-->
                <form action="Login?opt=1" method="post" autocomplete="off">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text" style="background: #00cbc9; color: black; border: 1px solid #0a2a5c;">
                                    <i class="fas fa-user"></i>
                                </div>
                            </div>
                            <input type="text" class="form-control" name="Txt_user" id="Txt_user" placeholder="Usuario" autocomplete="off" style="background: #0000005c !important; color:white; border: 1px solid #0a2a5c;">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text" style="background: #00cbc9; color: black; border: 1px solid #0a2a5c;">
                                    <i class="fas fa-key"></i>
                                </div>
                            </div>
                            <input type="password" class="form-control" name="Txt_password" id="txtPassword" placeholder="Contraseña" autocomplete="off"  style="background: #0000005c !important; color:white; border: 1px solid #0a2a5c;">
                            <div class="input-group-text" onclick="mostrarPass()" id="show_password" style="cursor: pointer;background: #00cbc9; color: black; border: 1px solid #0a2a5c;"><i id="icon" class="fas fa-eye"></i></div>
                        </div>
                    </div>
                    <button class="btn" style="margin-top: 25px; box-shadow: 1px 2px 5px 0px #00cbc9; background: #68e6f7;"><i class="fas fa-sign-in-alt"></i></button>

                </form>
                <div style="float: right; bottom: 0;"><b style="font-size:15px; color:#00cbc9; ">VP</b><b style="font-size: 15px; color:#00cbc9"> 00.00.00</b></div>
            </div>
        </div>
        <Alert:AlertData/>
        <script type="text/javascript">
            function mostrarPass() {
                var password = document.getElementById("txtPassword");
                var eye = document.getElementById("icon");
                if (password.type == "password") {
                    password.type = "text";
                    eye.className = "fas fa-eye-slash";
                } else {
                    password.type = "password";
                    eye.className = "fas fa-eye";
                }
            }
        </script>
        <script type="text/javascript" language="javascript">

            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
        </script>
    </body>
</html>
