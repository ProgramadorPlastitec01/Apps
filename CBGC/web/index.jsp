<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Login</title>
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/login2.css" />
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/Suggestion.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/resetpass.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Icon.fw.png">
    </head>
    <body>
        <jsp:include page="Library.jsp"></jsp:include>
            <header class="header">
                <nav class="nav">
                    <img src="Interface/Imagen/Logo2.fw.png" alt="" style="width: 16%;"/>
                    <button class="button" id="form-open">Login</button>
                </nav>
            </header>
            <!-- Home -->
            <section id="Section1" class="home show">
                <div class="form_container">
                    <i class="uil uil-times form_close"></i>
                    <!-- Login From -->
                    <div class="form login_form">
                        <form action="Session?opt=1"  method="post" autocomplete="off" >
                            <div style="display:flex;">
                                <div style="width: 88%">
                                    <h2 onclick="mostrarConvencion(1)">Login</h2>
                                </div>
                                <div>
                                    <button type="button" class="button2" onclick="HiddenLogin()">X</button>
                                </div>
                            </div>

                            <div class="input_box">
                                <i class="fas fa-user ml-2" style="font-size: 16px;"></i>
                                <input type="text" placeholder="Ingrese el usuario"  name="Txt_user" id="Txt_user" autocomplete="off" required>
                            </div>
                            <div class="input_box">
                                <i class="fas fa-lock ml-2" style="font-size: 16px;"></i>
                                <input type="password"  name="Txt_password" id="txtPassword" placeholder="Ingresa tu contraseña" required />
                                <div onclick="mostrarPass()" id="show_password" style="cursor: pointer;margin-left:88%">
                                    <i id="icon" class="fas fa-eye iconEye"></i>
                                </div>
                            </div>

                            <div class="option_field">
                                <a  id="signup" style="color:#5aaadc" class="forgot_pw" onmouseover="UnderlinePassOver()" onmouseout="UnderlinePassOut()"><span id="RestPas">Restablecer Contraseña?</span></a>
                            </div>

                            <button class="button">Ingresar</button>

                            <div class="login_signup text-warning">Entorno de pruebas</div>
                        </form>
                    </div>
                    <!-- Signup From -->
                    <div class="form signup_form">
                        <form action="Session?opt=2" method="post" autocomplete="off">
                            <h2>Restablecer Contraseña</h2>

                            <div class="input_box">
                                <i class="fas fa-portrait ml-2" style="font-size: 16px;"></i>
                                <input type="number" placeholder="Ingrese documento" name="Txt_document" id="Txt_document" autocomplete="off" required />
                            </div>
                            <div class="input_box">
                                <i class="fas fa-user-secret ml-2" style="font-size: 16px;"></i>
                                <input type="text" placeholder="Ingrese usuario" onchange="javascript:this.value = this.value.toUpperCase();" name="Txt_user" id="Txt_user1" autocomplete="off" required />
                            </div>
                            <div class="input_box">
                                <i class="fas fa-envelope ml-2" style="font-size: 16px;"></i>
                                <input type="email" placeholder="Ingrese el correo" name="Txt_mail" id="Txt_mail" autocomplete="off" required />
                            </div>

                            <button class="button">Enviar</button>

                            <div class="login_signup">Volver a <a href="#" id="login">Login</a></div>
                        </form>
                    </div>
                </div>
                <div class="sweet-local" tabindex="-1" id="Ventana1" style="opacity: 1.03; display:none;">
                    <div class="cont_reg">
                        <div style="display: flex; justify-content: space-between">
                            <h2>CBGC</h2>
                            <button class="button" onclick="mostrarConvencion(1)" style="height: 30px;padding: 3px;width: 30px;"><i class="fas fa-times"></i></button>
                        </div>
                        <div><h6>Certifcados y Batch Record Gestión Calidad</h6></div>
                        <div class="cont_form_user">
                            <p>
                                CBGC es una herramienta creada para apoyar al área de Calidad en la generación y control de certificados de producto y reportes de producción (Batch Record). Su principal objetivo es facilitar la consulta y recopilación de información que proviene de diferentes sistemas, permitiendo generar documentos completos y confiables sin depender de múltiples aplicaciones o procesos manuales.
                                El aplicativo reúne en un solo lugar los datos necesarios para los certificados de calidad y los reportes por lote, ofreciendo espacios para ingresar información adicional cuando sea necesario. De esta forma, CBGC ayuda a que los procesos de calidad sean más ágiles, organizados y precisos, mejorando la trazabilidad y el seguimiento de la producción.
                            </p>

                        </div>
                    </div>
                </div>
            </section>
            <script type="text/javascript">
                function ViewWindows(id) {
                    const div = document.getElementById("Window" + id);
                    div.classList.toggle("active");
                }
            </script>
            <script type="text/javascript">
                function HiddenLogin() {
                    document.getElementById("Section1").classList.remove("show");
                }
                function mostrarPass() {
                    var password = document.getElementById("txtPassword");
                    var eye = document.getElementById("icon");
                    if (password.type == "password") {
                        password.type = "text";
                        eye.className = "fas fa-eye-slash iconEye";
                    } else {
                        password.type = "password";
                        eye.className = "fas fa-eye iconEye";
                    }
                }
                function UnderlinePassOver() {
                    document.getElementById("RestPas").classList.add("UnderLineReset");
                }
                function UnderlinePassOut() {
                    document.getElementById("RestPas").classList.remove("UnderLineReset");
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
            <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
            <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
            <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
            <script src="Interface/Content/Assets/js/scripts.js"></script>
            <script src="Interface/Content/Assets/js/custom.js"></script>
            <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
            <script src="Interface/Content/Assets/js/ScriptLogin.js"></script>
        <Alert:Alert/>
        <script>
                $(function () {
                    iziToast.success({
                        title: 'Prueba',
                        message: 'El toast funciona correctamente',
                        position: 'topRight'
                    });
                });
        </script>
    </body>
</html>
