<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>

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
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <style>
            .home {
                position: relative;
                height: 100vh;
                width: 100%;
                background-size: cover;
                background-position: center;
                transition: background-image 1s ease-in-out; /* efecto suave */
            }
            .ImgSizeSala:hover{
                    transform: scale(1.1) rotate(360deg) !important;
            }
        </style>
    </head>
    <body>
        <jsp:include page="Library.jsp"></jsp:include>
            <!-- Header -->
            <header class="header">
                <nav class="nav">
                    <!--<a href="index.jsp" class="nav_logo">AppTI</a>-->
                    <!--<img src="Interface/Imagen/Logo_app/Icon.fw.png" alt=""/>-->
                    <!--<img src="Interface/Imagen/Logo_app/LogoTitle.fw.png" alt=""/>-->
                    <img src="Interface/Imagen/Logo_app/LogoSideW.fw.png" alt="" style="width: 13%;"/>

                    <ul class="nav_items">
                        <li class="nav_item">
                            <a href="Knowledge.jsp" class="nav_link" target="_blank" rel="noopener noreferrer" class="nav_link">Documentación Técnica</a>
                            <a href="http://172.16.5.99:8084/AppSupport/" class="nav_link" target="_blank" rel="noopener noreferrer">Soporte</a>
                            <a href="#"  onclick="ViewWindows(2)" class="nav_link">Idea</a>
                        </li>
                    </ul>

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
                                <a  id="signup" style="color:#33bf98" class="forgot_pw" onmouseover="UnderlinePassOver()" onmouseout="UnderlinePassOut()"><span id="RestPas">Restablecer Contraseña?</span></a>
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
                            <h2>Nexus</h2>
                            <button class="btn btn-outline-secondary" onclick="mostrarConvencion(1)" style="height: 30px;padding: 3px;width: 30px;"><i class="fas fa-times"></i></button>
                        </div>
                        <div class="cont_form_user">
                            <p>Este sistema de información, es el encargado de facilitar el manejo y control de la documentación del areá con los registros <b>R-TI-001</b>,<b>R-TI-005</b>,<b>R-TI-031</b>,<b>R-TI-014</b> entre otros.</br> El sistema como ayuda virtual, permite al usuario acceder a la información de manera <b style="color:#00281b">segura, rapida</b> y <b style="color:#00281b">confiable</b> para poder realizar en cada uno de los procesos del registros una adecuada manipulación.</p>
                        </div>
                    </div>
                </div>
                <div  id="Window2" class="DivSuggestion">
                    <div class="cont_reg">
                        <div class="cont_form_user">
                            <form action="GeneralNotSession?opt=2" method="post" class="p-2  rounded " style="max-width: 600px; margin:auto;">
                                <h5 class="mb-3 text-center">💡 Tu opinión nos ayuda a mejorar</h5>

                                <!-- Nombre del usuario -->
                                <div class="mb-3">
                                    <label for="nombre" class="form-label">Nombre</label>
                                    <input type="text" class="form-control" id="nombre" name="Name" placeholder="Escribe tu nombre">
                                </div>

                                <!-- Tipo de aporte -->
                                <div class="mb-3">
                                    <label for="tipo" class="form-label">Tipo de aporte</label>
                                    <select class="form-control" id="tipo" name="Type" required>
                                        <option value="">Selecciona una opción</option>
                                        <option value="sugerencia">Sugerencia</option>
                                        <option value="idea">Idea</option>
                                        <option value="mejora">Mejora</option>
                                        <option value="otro">Otro</option>
                                    </select>
                                </div>

                                <!-- Texto de la sugerencia -->
                                <div class="mb-3">
                                    <label for="detalle" class="form-label">Escribe tu aporte</label>
                                    <textarea class="form-control" id="detalle"  style="margin-left: 12px;" name="Detail" rows="5" placeholder="Cuéntanos tu sugerencia, idea o mejora..." required></textarea>
                                </div>

                                <!-- Botón de envío -->
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary">Enviar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <img src="Interface/Imagen/Sala2.png" onclick="window.open('https://calendar.google.com/calendar/u/0/appointments/schedules/AcZssZ1A0nzF9oO2Pl5AXUv2s6vi1LCstbctuYoHD2QD1jMz1iqsO1EXPNbY1D79ECJuQs2xyj44M7dO', '_blank')" class='ImgSizeSala' alt="" width="58" style="cursor: pointer;position: absolute; bottom: 19px;left: 19px;"/>
                <div class="ButtomFloat" onclick="ViewWindows(1)" ><img  class='ImgSizeFloat' src='Interface/Imagen/BOTTI.png'></div>
            </section>
            <div  id="Window1" class="DivBot">
                <div class="container mt-5" >
                    <div class="">
                        <div class="card chat-box card-success" id="mychatbox2">
                            <div class="card-header">
                                <h4><i class="fas fa-circle text-success mr-2" title="Online" data-toggle="tooltip"></i>ChatBot</h4>
                            </div>

                            <!-- Área de mensajes -->
                            <div class="card-body chat-content" id="chatArea">
                                <!-- Aquí se va a cargar la bienvenida y el menú -->
                            </div>

                            <!-- Área de ingreso de mensaje -->
                            <div class="card-footer chat-form">
                                <form id="chat-form2" onsubmit="sendMessage(); return false;">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="userInput" placeholder="Escribe tu mensaje....." autocomplete="off" />
                                        <div class="input-group-append">
                                            <button type="submit" class="btn btn-green PriorityButtom" style="z-index:1000">
                                                <i class="far fa-paper-plane"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
                
        <Alerts:Alert/>
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
        <script type="text/javascript" language="javascript">
            function showMenu() {
                const chatArea = document.getElementById("chatArea");
                const menuHTML = `
                    <div class='chat-item chat-left'>
                        <div class='chat-details OutMargin'>
                            <img class='ImgSize' src='Interface/Imagen/BOTTI.png'>
                            <div class='chat-text'>
                                <div class='row' style="display:flex; flex-direction: column; gap: 0.5rem;">
                                    <button onclick="sendMessageInput('Solicito un soporte')" class='btn btn-green CustomBtn'>Solicito un soporte</button>
                                    <button onclick="sendMessageInput('Manual')" class='btn btn-green CustomBtn'>Manual</button>
                                    <button onclick="sendMessageInput('Contacto')" class='btn btn-green CustomBtn'>Contacto</button>
                                    <button onclick="sendMessageInput('Horario')" class='btn btn-green CustomBtn'>Horario T.I</button>
                                    <button onclick="sendMessageInput('Capacitacion')" class='btn btn-green CustomBtn'>Capacitacion</button>
                                    <button onclick="sendMessageInput('Solicito un usuario')" class='btn btn-green CustomBtn'>Solicito un usuario</button>
                                    <button onclick="sendMessageInput('Quien esta en turno')" class='btn btn-green CustomBtn'>¿Quien esta en turno?</button>
                                    <button onclick="sendMessageInput('que puedes hacer')" class='btn btn-green CustomBtn'>¿Que puedo hacer?</button>
                                    <button onclick="sendMessageInput('ayuda')" class='btn btn-green CustomBtn'>¡Ayuda!</button>
                                    <button onclick="sendMessageInput('tags')" class='btn btn-green CustomBtn'>Tags</button>
                                </div>
                            </div>
                        </div>
                    </div>`;
                chatArea.innerHTML += menuHTML;
                chatArea.scrollTop = chatArea.scrollHeight;
            }

            function sendMessage() {
                const inputElement = document.getElementById("userInput");
                const userInput = inputElement.value.trim();
                if (!userInput)
                    return;

                const chatArea = document.getElementById("chatArea");

                // Mostrar mensaje del usuario
                chatArea.innerHTML += "<div class='chat-item chat-right'>\
        <div class='chat-details OutMargin'>\
            <div><img class='ImgSize' src='Interface/Imagen/UserChat.png'></div>\
            <div class='chat-text'>" + userInput + "</div>\
        </div>\
       </div>";

                // Eliminar menús anteriores para evitar duplicados
                [...chatArea.querySelectorAll(".chat-item.chat-left .chat-text > div")].forEach(div => {
                    if (div.querySelector("button")) {
                        div.parentElement.parentElement.parentElement.remove();
                    }
                });

                fetch('LocalChatBotServlet', {
                    method: "POST",
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: "message=" + encodeURIComponent(userInput)
                })
                        .then(response => response.text())
                        .then(response => {
                            if (response === "__SHOW_MENU__") {
                                showMenu();
                            } else {
                                chatArea.innerHTML += "<div class='chat-item chat-left'>\
                <div class='chat-details OutMargin'>\
                    <img class='ImgSize' src='Interface/Imagen/BOTTI.png'>\
                    <div class='chat-text'>" + response + "</div>\
                </div>\
               </div>";
                            }
                            chatArea.scrollTop = chatArea.scrollHeight;
                        });

                inputElement.value = "";
            }


            function sendMessageInput(mse) {
                document.getElementById("userInput").value = mse;
                sendMessage();
            }

            window.onload = function () {
                const chatArea = document.getElementById("chatArea");
                chatArea.innerHTML = "<em>Bienvenido al ChatBot, soy BOTTI, tu Guia virtual. En las siguientes opciones encontrara el menu de opciones. Si desea volver la opciones escriba en el mensaje <b>Menu</b></em><br>";
                showMenu();
            }
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const totalImages = 12;
                const homeSection = document.querySelector(".home");

                if (!homeSection) {
                    console.error("No se encontró el elemento con clase 'home'");
                    return;
                }

                function getDailyImageIndex() {
                    const today = new Date();
                    const seed = today.getFullYear() + "" + (today.getMonth() + 1) + "" + today.getDate();
                    // Convierte la fecha en un número entero
                    let num = 0;
                    for (let i = 0; i < seed.length; i++) {
                        num += seed.charCodeAt(i);
                    }
                    // Saca un índice dentro del rango de imágenes
                    return (num % totalImages) + 1;
                }

                function setDailyBackground() {
                    const index = getDailyImageIndex();
                    const imageUrl = "Interface/Content/Assets/css/Img/B" + index + ".jpg";
                    console.log("Imagen diaria (" + index + "):", imageUrl);
                    homeSection.style.backgroundImage = 'url("' + imageUrl + '")';
                }

                // Precarga (opcional, si quieres que no parpadee al cargar)
                const images = [];
                for (let i = 1; i <= totalImages; i++) {
                    images[i] = new Image();
                    images[i].src = "Interface/Content/Assets/css/Img/B" + i + ".jpg";
                }

                // Asigna la imagen del día
                setDailyBackground();
            });
        </script>

        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/js/custom.js"></script>
        <script src="Interface/Content/Assets/js/page/components-chat-box.js"></script>
    </body>
</html>
