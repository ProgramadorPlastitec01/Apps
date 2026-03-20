<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="tld_alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="Interfaz/Contenido/assets/css/login.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">

        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png" />

        <!--<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>-->
        <script src="Interfaz/Contenido/assets/js/page/Jquery360.js"></script>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <title>Login | SGLT</title>
    </head>
    <body>
        <div style="display: flex; height: 100vh;">
            <div class="ContenInfo">
                <div class="subInfo">
                    <h1 class="text-center mb-4">PLASTITEC</h1>
                    <div class="">
                        <div class="col-lg-12">
                            <h3  class="text-center mb-2">OEA</h3>
                            <p>Este sistema de información permite a Plastitec garantizar unos niveles mínimos de seguridad y facilitar el flujo del comercio internacional, forjando 
                                alianzas sólidas entre nuestros clientes y proveedores que permitan garantizar la seguridad de toda la 
                                cadena de suministro y construir relaciones de confianza, teniendo como último estadio el reconocimiento mutuo,
                                resultado de las alianzas entre las aduanas.
                            </p>
                        </div>
                        <div class="col-lg-12">
                            <h3  class="text-center mb-2">SAGRILAFT</h3>
                            <p>Este sistema de información permite a Plastitec cumplir con todas las leyes y regulación en contra del lavado de activos (LA), el financiamiento del terrorismo (FT) fabricación y proliferación de armas de destrucción masiva (FPADM), buscando mitigarlo a través de:<br>
                                • Identificación, análisis, evaluación y tratamiento de los riesgos de LA/FT/FPADM.<br>
                                • Implementación de procedimientos de debida diligencia para el conocimiento de las contrapartes o asociados de negocio (Clientes, proveedores, contratistas, empleados y socios).
                            </p>
                        </div>
                    </div>
                    <div class="text-center mt-5 d-flex justify-content-around">
                        <div class="col-lg-4">
                            <img src="Interfaz/Contenido/Imagen/WP_Sag2.png" alt="" width="200"/>
                        </div>
                        <!--                        <div class="col-lg-4">
                                                    <img src="Interfaz/Contenido/Imagen/WP_OEA.fw.png" alt="" width="250"/>
                                                </div>-->
                    </div>
                </div>
            </div>

            <div class="contenData">
                <div class="subData">
                    <div style="margin-bottom: 52px;">
                        <p class="mb-4">Iniciar Sesion</p>
                    </div>
                    <form action="Login?opt=1" method="post">
                        <div class="form-group">
                            <h6>Usuario / User</h6>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <i class="fas fa-user"></i>
                                    </div>
                                </div>
                                <input type="text" class="form-control" name="Txt_user" id="Txt_user" placeholder="Usuario/User" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-group">
                            <h6>Contraseña / Password</h6>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <i class="fas fa-key"></i>
                                    </div>
                                </div>
                                <input type="password" class="form-control" name="Txt_password" id="txtPassword" placeholder="Contraseña/Password" autocomplete="off">
                                <div class="input-group-text" onclick="mostrarPass()" id="show_password" style="cursor: pointer;"><i id="icon" class="fas fa-eye"></i></div>
                            </div>
                        </div>
                        <div class="g-recaptcha" data-sitekey="6Lchq40sAAAAAPKp0iaEUyJnMmfG1i-46iEsEP_9" data-callback="habilitarBoton"></div>
                        <div class="ContenBtn">
                            <button type="submit" class="btn btnLogin"  id="btnEnviar" disabled><i class="fas fa-angle-right"></i></button>
                            <!--<button type="submit" class="btn btnLogin"  id="btnEnviar" ><i class="fas fa-angle-right"></i></button>-->
                        </div>
                    </form>
                </div>
                <div class="FootData">
                    <!--<p style="color: #5ecbeb;">VP 00.00.00</p>-->
                    <!--<p style="color: #5ecbeb;">VA 04.09.05</p>-->
                    <p style="color: #5ecbeb;">VA 08.18.10</p>
                    <p>PLASTITEC 2026 &#169 copyright</p>
                </div>
            </div>
        </div>
        <tld_alert:AlertModule/>
        <script>
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
        <script>
            function habilitarBoton() {
                document.getElementById("btnEnviar").disabled = false;
            }
        </script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/scripts.js"></script>
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>
    </body>
</html>
