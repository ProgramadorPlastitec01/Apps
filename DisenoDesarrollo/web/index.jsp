<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/login.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/Validacion/StyleSheetLiveValidation.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <title>Login | A-D&D</title>
        <link rel="shortcut icon" href="Interfaz/Contenido/Img/favicon.ico" type="image/x-icon">
    </head>
    <body>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <div class="sweet-local" tabindex="-1" id="Ventana1" style="opacity: 1.03; display:none;">
                <div class="cont_reg">
                    <div style="display: flex; justify-content: space-between">
                        <h2 class="text-capitalize">Archivo Dise&ntilde;o &amp; desarrollo</h2>
                        <button class="btn btn-outline-secondary" onclick="mostrarConvencion(1)" style="height: 30px;padding: 3px;width: 30px;"><i class="fas fa-times"></i></button>
                    </div>
                    <div class="cont_form_user">
                        <p class="text-justify">
                            <b>7.3</b> A-D&D Archivo Diseño & Desarrollo Este sistema de información es el encargado de facilitar el <b>manejo de información de los proyectos</b> permitiendo identificar en cada 
                            una de sus etapas el <b>seguimiento y ejecución</b> de sus númerales. <b>Planificación</b>, Registros de Elementos de Entrada, Programación de Pruebas, <b>Resultados y Control de Cambio
                            para el Proyecto</b>. El sistema como ayuda virtual permite al usuario acceder a la información de manera rapida, segura y confiable para poder realizar en cada uno de
                            los procesos de los proyectos una adecuada <b>manipulación</b>.
                        </p>
                    </div>
                </div>
            </div>

            <div id="main">
                <div class="img_logo" id="cont_img">
                    <img id="data_img" class="cls_img" src="Interfaz/Contenido/Img/Iso 1.jpg" width="150" alt="A-D&D">
                </div>
                <div>
                    <div class="cont_icon" onclick="mostrarConvencion(1)">
                        <i class="fas fa-question"></i>
                    </div>
                    <h2 class="text-center text-uppercase">Archivo <div>Dise&ntilde;o &amp; Desarrollo</div></h2>
                    <br>
                    <form action="Sesion?opc=1" method="post" autocomplete="off">
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
                                <div class="input-group-text" onclick="mostrarPass()" id="show_password" style="cursor: pointer;"><i id="icon" class="fas fa-eye"></i></div>
                            </div>
                        </div>
                        <button class="btn" style="margin-top: 25px; box-shadow: 1px 2px 5px 0px #959595;"><i class="fas fa-sign-in-alt"></i></button>
                        <!--<button class="btn" style="margin-top: 25px;"><i class="fas fa-sign-in-alt"></i></button>-->
                    </form>
                    <div>
                        <div style="float: right; bottom: 0;">
                            <b style="font-size:15px; color:#03291d; ">Va</b><b style="font-size: 15px; color:#03291d"> 12.76.38</b>
                        </div>
                        <p class="text-left text-uppercase">&copy; Plastitec</p>
                    </div>
                </div>
            </div>
        <Alertas:Alertas/>
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
        <script>
            setTimeout(function () {
                document.getElementById("cont_img").classList.remove("img_logo_tr");
                document.getElementById("cont_img").classList.add("img_logo");
                document.getElementById("data_img").classList.remove("cls_img");
                document.getElementById("main").style.opacity = 1;
            }, 40);
        </script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interfaz/Contenido/assets/Validacion/LiveValidation.js" type="text/javascript" language="javascript"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
    </body>
</html>