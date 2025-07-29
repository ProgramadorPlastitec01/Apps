<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chat Local</title>
        <!-- CSS de Stisla -->
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
    </head>
    <body>

        <div class="container mt-5">
            <!--<h2 class="mb-4">ChatBot</h2>-->
            <div class="col-12 col-sm-8 col-lg-6">
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

        <!-- Scripts -->
        <script>
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
                chatArea.innerHTML = "<em>Bienvenido al Chat, soy BOTTI, tu Guia Bot. Encontraras la siguientes opciones, seleccione uno, si deseas habilitar el menú nuevamente, escriba <b>Menu</b></em><br>";
                showMenu();
            }
        </script>

        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/js/custom.js"></script>
        <script src="Interface/Content/Assets/js/page/components-chat-box.js"></script>

    </body>
</html>
