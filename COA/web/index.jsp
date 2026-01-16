<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <!-- CSS -->
        <link rel="stylesheet" href="Interface/Content/Assets/css/login2.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/resetpass.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
    </head>

    <body>
        <jsp:include page="Library.jsp"></jsp:include>
            <div class="login-layout">

                <!-- IZQUIERDA -->
                <div class="login-left">
                    <div class="login-box">


                        <!-- ================= LOGIN ================= -->
                        <div id="loginForm">
                            <h1 class="login-title">Inicio Sesion.</h1>
                            <p class="login-subtitle">
                                Ingresa con los datos.
                            </p>

                            <form action="Session?opt=1" method="post" autocomplete="off">

                                <div class="form-group">
                                    <div class="input-icon">
                                        <i class="fas fa-user"></i>
                                        <input type="text" class="form-control"
                                               name="Txt_user"
                                               placeholder="Usuario"
                                               required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="input-icon">
                                        <i class="fas fa-lock"></i>
                                        <input type="password" class="form-control"
                                               name="Txt_password"
                                               id="txtPassword"
                                               placeholder="Contraseña"
                                               required>
                                        <span class="toggle-pass" onclick="mostrarPass()">
                                            <i id="icon" class="fas fa-eye"></i>
                                        </span>
                                    </div>
                                </div>

                                <button type="submit" class="btn-login">
                                    Ingresar
                                </button>

                                <div class="login-links">
                                    <a href="#" onclick="mostrarReset()">¿Olvidaste tu contraseña?</a>
                                </div>
                            </form>
                        </div>

                        <!-- ================= RESET PASSWORD ================= -->
                        <div id="resetForm" style="display:none;">
                            <h1 class="login-title">Restablecer</h1>
                            <p class="login-subtitle">
                                Ingresa los datos para recuperar tu acceso.
                            </p>

                            <form action="Session?opt=3" method="post" autocomplete="off">

                                <div class="form-group">
                                    <div class="input-icon">
                                        <i class="fas fa-id-card"></i>
                                        <input type="number" class="form-control"
                                               name="Txt_document"
                                               placeholder="Documento"
                                               required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="input-icon">
                                        <i class="fas fa-user"></i>
                                        <input type="text" class="form-control"
                                               name="Txt_user"
                                               placeholder="Usuario"
                                               required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="input-icon">
                                        <i class="fas fa-envelope"></i>
                                        <input type="email" class="form-control"
                                               name="Txt_mail"
                                               placeholder="Correo electrónico"
                                               required>
                                    </div>
                                </div>

                                <button type="submit" class="btn-login">
                                    Enviar
                                </button>

                                <div class="login-links">
                                    <a href="#" onclick="mostrarLogin()">Volver a login</a>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>

                <!-- DERECHA -->
                <div class="login-right">

                    <div class="login-logo">
                        <img src="Interface/Imagen/Logo1.fw.png" alt="COA - Control Operativo y Administrativo">
                    </div>

                    <div class="login-text">
                        
                        <p class="login-description">
                            COA es el sistema central que integra y consolida la información generada por los distintos aplicativos
                            operativos y de calidad de la organización. Unifica registros digitales y físicos para construir el
                            <strong>Batch Record completo de cada lote</strong>, garantizando trazabilidad total, control del proceso
                            y la generación confiable del <strong>Certificado de Calidad</strong>.
                        </p>

                        <p class="login-slogan">
                            “Toda la información del lote, en un solo lugar.”
                        </p>
                    </div>

                </div>
            </div>
        <Alert:Alert/>

        <!-- JS -->
        <script>
            function mostrarReset() {
                document.getElementById("loginForm").style.display = "none";
                document.getElementById("resetForm").style.display = "block";
            }

            function mostrarLogin() {
                document.getElementById("resetForm").style.display = "none";
                document.getElementById("loginForm").style.display = "block";
            }

            function mostrarPass() {
                const p = document.getElementById("txtPassword");
                const i = document.getElementById("icon");
                if (p.type === "password") {
                    p.type = "text";
                    i.className = "fas fa-eye-slash";
                } else {
                    p.type = "password";
                    i.className = "fas fa-eye";
                }
            }
        </script>

        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>

    </body>
</html>
