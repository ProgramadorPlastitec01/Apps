<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Alerta.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menuusss" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/login.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
        <title>PVM | Login</title>
    </head>
    <body>
        <%--<jsp:include page="Menu.jsp"></jsp:include>--%>
        <div class="sweet-local" tabindex="-1" id="Ventana1" style="opacity: 1.03; display:none;">
            <div class="cont_reg">
                <div style="display: flex; justify-content: space-between">
                    <div class="mb-3" style="text-align: center; width: 100%;"><h2>PVM </h2><h3> Programa de verificación Metrologica</h3></div>
                    <button class="btn btn-outline-secondary" onclick="mostrarConvencion(1)" style="height: 30px;padding: 3px;width: 30px;"><i class="fas fa-times"></i></button>
                </div>
                <div class="cont_form_user">
                    <p>Este sistema de información es el encargado de administrar los instrumentos y sus registros 
                        <b style="color:#00281b">(Listado maestro, Ficha Tecnica y Hoja de vida, Verificación e Inspección), alertando el cumplimiento del programa de verificación.</b>
                        </br>
                        El sistema como ayuda virtual, permite al usuario acceder a la información de manera 
                        <b style="color:#00281b">segura, rapida</b> y <b style="color:#00281b">confiable</b> 
                        para poder realizar en cada uno de los procesos del registros una adecuada manipulación.</p>
                </div>
            </div>
        </div>

        <div id="main">
            <div class="img_logo" id="cont_img">
                <img id="data_img" class="cls_img" src="Interfaz/Contenido/images/NewPvm3.fw.png" width="200">
            </div>
            <div>
                <div class="cont_icon" onclick="mostrarConvencion(1)">
                    <i class="fas fa-question"></i>
                </div>
                <h2 style="color: #313131">PVM</h2>
                <h5 class="mb-4">Programa de verificación metrologica</h5>

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
                            <input type="password" class="form-control" name="Txt_password" id="txtPass" placeholder="Contraseña" autocomplete="off">
                            <div class="input-group-text" onclick="mostrarPass()" id="show_password" style="cursor: pointer;"><i id="icon" class="fas fa-eye"></i></div>
                        </div>
                    </div>
                    <button class="btn" style="margin-top: 25px; box-shadow: 1px 2px 5px 0px #959595;"><i class="fas fa-sign-in-alt"></i></button>
                    <!--<button class="btn" style="margin-top: 25px;"><i class="fas fa-sign-in-alt"></i></button>-->

                </form>
                <div style="float: right; bottom: 0;">
                    <b style="font-size:15px; color:#03291d; ">VA</b>
                    <!--<b style="font-size: 15px; color:#03291d"> vp 00.00.00</b>-->
                    <!--<b style="font-size: 15px; color:#03291d"> vp  01.02.01</b>-->
                    <!--<b style="font-size: 15px; color:#03291d"> va 01.05.02</b>-->
                    <!--<b style="font-size: 15px; color:#03291d"> 05.08.03</b>-->
                    <b style="font-size: 15px; color:#03291d"> 06.12.05</b>
                </div>
            </div>
        </div>
        <Alertas:LanzarAlertas/>
        <script type="text/javascript">
            function mostrarPass() {
                var password = document.getElementById("txtPass");
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
        <script>
            setTimeout(function () {
                document.getElementById("cont_img").classList.remove("img_logo_tr");
                document.getElementById("cont_img").classList.add("img_logo");
                document.getElementById("data_img").classList.remove("cls_img");
                document.getElementById("main").style.opacity = 1;
            }, 40);
        </script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
    </body>
</html>
