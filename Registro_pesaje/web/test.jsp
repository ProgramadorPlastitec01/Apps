<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Test.tld" prefix="Pruebas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <Pruebas:Test/>
            </div>
        </div>

        <script>
            const fileContent = document.getElementById('fileContent');
            const selectBascula = document.getElementById("selectBascula");
            let socket;

            selectBascula.addEventListener('change', function () {
                const basculaSeleccionada = selectBascula.value;
                if (socket) {
                    socket.close();
                }
                socket = new WebSocket("ws://172.16.1.176:8084/Registro_pesaje/filewatcher/" + basculaSeleccionada);

                socket.onopen = () => {
                    console.log('Conexión WebSocket abierta');
                };

                socket.onclose = (event) => {
                    if (event.wasClean) {
                        console.log(`La conexión WebSocket se cerró de forma limpia. Código de cierre: ${event.code}, Razón: ${event.reason}`);
                    } else {
                        console.error(`La conexión WebSocket se cerró de forma inesperada. Código de cierre: ${event.code}`);
                    }
                };

                socket.onerror = (error) => {
                    console.error('Error en la conexión WebSocket:', error);
                };

                socket.onmessage = (event) => {
                    console.log('Mensaje recibido del servidor WebSocket:', event.data);
                    fileContent.textContent = event.data;
                };
            });

            selectBascula.dispatchEvent(new Event('change'));
        </script>  
    </body>
</html>
