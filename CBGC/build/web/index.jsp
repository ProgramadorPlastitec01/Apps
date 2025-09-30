<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Login</title>
        <link rel="stylesheet" href="Interface/Content/Assets/css/login2.css" />
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/Suggestion.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/resetpass.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
    </head>
    <body>
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

                        <div class="login_signup">VP 00.00.00 </div>
                    </form>
                </div>



                <!-- Signup From -->
                <div class="form signup_form">
                    <form action="Session?opt=5" method="post" autocomplete="off">
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
                            Este sistema de información está diseñado para <b>facilitar el manejo, control y emisión de certificados de calidad</b>, apoyándose en la información proveniente de los aplicativos <b>REGISTROS LAB</b>, <b>CONTROL GRAFADO</b>, <b>SISTEMA DE TUBO</b> e <b>INSPECCIÓN MANGA</b>.  
                            <br>
                            Además de gestionar la documentación asociada a registros como <b>R-GC-046</b>, <b>R-GC-074</b>, <b>R-GC-194</b>, <b>R-GC-136</b> entre otros, el sistema genera un <b>Batch Record</b>, que consiste en la recopilación integral de todos los archivos correspondientes a un lote.  
                            Como herramienta virtual, permite al usuario acceder a la información de forma <b style="color:#00281b">segura</b>, <b style="color:#00281b">rápida</b> y <b style="color:#00281b">confiable</b>, garantizando una adecuada gestión y trazabilidad en cada uno de los procesos.
                        </p>

                    </div>
                </div>
            </div>
            <div class="ButtomFloat" onclick="ViewWindows(1)" ><img  class='ImgSizeFloat' src='Interface/Imagen/BOTTI.png'></div>
        </section>
        <script src="Interface/Content/Assets/js/ScriptLogin.js"></script>
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
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/js/custom.js"></script>
    </body>
</html>
