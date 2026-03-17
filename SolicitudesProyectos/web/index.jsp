<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alerta" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/login.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
        <title>Login | SP</title>
    </head>
    <body>
        <jsp:include page="Library.jsp"></jsp:include>
            <div class="sweet-local" tabindex="-1" id="Ventana1" style="opacity: 1.03; display:none;">
                <div class="cont_reg">
                    <div style="display: flex; justify-content: space-between">
                        <h2>Solicitudes Proyectos</h2>
                        <button class="btn btn-outline-secondary" onclick="mostrarConvencion(1)" style="height: 30px; padding: 3px; width: 30px;">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                    <div class="cont_form_user">
                        <p>El sistema permite llevar un control de las solicitudes generadas por los usuarios de diferentes áreas al área de proyectos. El sistema como ayuda virtual permite al usuario acceder a la información de manera segura, rápida y confiable para poder realizar en cada uno de los procesos de la solicitud una adecuada manipulación.</p>
                    </div>
                </div>
            </div>

            <div id="main">
                <div class="img_logo" id="cont_img">
                    <img id="data_img" class="cls_img" src="Interfaz/Contenido/Imagen/solicitud_proyectos.png" width="150">
                    <h2><span style="color:#f70f03">Solicitudes</span><span style="color: #313131"> Proyectos</span></h2>
                </div>
                <div>
                    <div class="cont_icon" onclick="mostrarConvencion(1)">
                        <i class="fas fa-question"></i>
                    </div>
                    <form action="Login?opc=1" method="post" autocomplete="off">
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <i class="fas fa-user"></i>
                                    </div>
                                </div>
                                <input type="text" class="form-control" name="Txt_user" id="Txt_user" placeholder="Usuario" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <i class="fas fa-key"></i>
                                    </div>
                                </div>
                                <input type="password" class="form-control" name="Txt_password" id="txtPassword" placeholder="Contraseña" autocomplete="off">
                                <div class="input-group-text" onclick="mostrarPass()" id="show_password" style="cursor: pointer;">
                                    <i id="icon" class="fas fa-eye"></i>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn" style="margin-top: 25px; box-shadow: 1px 2px 5px 0px #959595;">
                            <i class="fas fa-sign-in-alt"></i>
                        </button>
                    </form>
                    <div style="float: right; bottom: 0;"><b style="font-size:15px; color:#03291d; ">VA</b><b style="font-size: 15px; color:#f70f03"> 11.45.15</b></div>
                </div>
            </div>
        <Alerta:Alertas/>
        <script type="text/javascript">
            function mostrarPass() {
                var password = document.getElementById("txtPassword");
                var eye = document.getElementById("icon");
                if (password.type === "password") {
                    password.type = "text";
                    eye.className = "fas fa-eye-slash";
                } else {
                    password.type = "password";
                    eye.className = "fas fa-eye";
                }
            }
        </script>
        <script type="text/javascript">
            function mostrarConvencion(id) {
                var ventana = document.getElementById("Ventana" + id);
                ventana.style.display = (ventana.style.display === "none") ? "block" : "none";
            }
        </script>
        <script>
            setTimeout(function () {
                document.getElementById("cont_img").classList.remove("img_logo_tr");
                document.getElementById("cont_img").classList.add("img_logo");
                document.getElementById("data_img").classList.remove("cls_img");
                document.getElementById("main").style.opacity = 1;
            }, 40);
        </script>
    </body>
</html>
