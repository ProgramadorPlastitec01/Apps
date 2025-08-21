
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<%@taglib uri="/WEB-INF/tlds/Tld_computer.tld" prefix="Computer" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PC</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css" >
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/computer.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <style>
            .nav-link .active {
                background: #5ecbeb;
            }
            .signature-pad {
                margin-bottom: 20px;
            }

            .canvas-container {
                margin-bottom: 20px;
            }
            canvas {
                border: 1px solid #000;
            }

            button {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Computer:Computer/>
                </div>
            </div>
        </div>
        <script>
            function showPages(id) {
                var totalNumberOfPages = 5;
                for (var i = 1; i <= totalNumberOfPages; i++) {
                    if (document.getElementById('page' + i)) {
                        document.getElementById('page' + i).style.display = 'none';
                    }
                }
                if (document.getElementById('page' + id)) {
                    document.getElementById('page' + id).style.display = 'block';
                }
            }
        </script>
        <script>
            function filterCards() {
                const input = document.getElementById('myInput');
                const filter = input.value.toLowerCase();
                const cardContainer = document.getElementById('cardContainer');
                const cards = cardContainer.getElementsByClassName('card-container');

                for (let i = 0; i < cards.length; i++) {
                    const card = cards[i];
                    const title = card.getElementsByTagName('h4')[0].innerText.toLowerCase();
                    const bodyText = card.innerText.toLowerCase();
                    if (title.includes(filter) || bodyText.includes(filter)) {
                        card.style.display = ''; // Muestra la tarjeta
                    } else {
                        card.style.display = 'none'; // Oculta la tarjeta
                    }
                }
            }
        </script>

        <script>
            function editar(event) {
                event.stopPropagation();
            }
        </script>


        <script>
            let contador = 0;

            function actualizarInputOculto() {
                const filas = document.querySelectorAll('#tabla-body tr:not(:first-child)');
                const valores = [];

                filas.forEach(fila => {
                    const columnas = fila.querySelectorAll('td');
                    const idName = columnas[0].textContent.trim();
                    const idType = columnas[1].textContent.trim();
                    const idVersion = columnas[2].textContent.trim();
                    valores.push('[' + idName + '/' + idType + '/' + idVersion + ']');
                });

                document.getElementById('infoOculta').value = valores.join(' ');
            }

            function agregarFila() {
                const idName = document.getElementById('idName').value.trim();
                const idType = document.getElementById('idType').value.trim();
                const idVersion = document.getElementById('idVersion').value.trim();

                if (!idName || !idType || !idVersion) {
                    alert("Por favor, completa todos los campos.");
                    return;
                }

                const tablaBody = document.getElementById('tabla-body');
                const nuevaFila = document.createElement('tr');

                let filaHtml = `
                    <td>xxNamexx</td>
                    <td>xxTypexx</td>
                    <td>xxVersionxx</td>
                    <td><button class="btn btn-danger" onclick="eliminarFila(this)">Eliminar</button></td>`;


                filaHtml = filaHtml
                        .replace('xxNamexx', idName)
                        .replace('xxTypexx', idType)
                        .replace('xxVersionxx', idVersion);

                nuevaFila.innerHTML = filaHtml;

                tablaBody.appendChild(nuevaFila);
                actualizarInputOculto();

                // Limpiar inputs
                document.getElementById('idName').value = '';
                document.getElementById('idType').value = '';
                document.getElementById('idVersion').value = '';
            }

            function eliminarFila(boton) {
                const fila = boton.closest('tr');
                fila.remove();
                actualizarContador(-1);
                actualizarInputOculto();
            }
        </script>

        <script>
            function sigMode(mode) {
                let hdmShield = document.getElementById('idSigMode');
                hdmShield.value = mode;
            }
        </script>

        <script>
            function guardarHTMLTabla() {
                const contenedor = document.getElementById("idtabla"); // ← asegúrate de que coincide el ID

                if (!contenedor) {
                    alert("No se encontró el contenedor con ID 'idtabla'");
                    return;
                }

                const inputs = contenedor.querySelectorAll("input, textarea, select");

                inputs.forEach(input => {
                    if (input.type === "checkbox" || input.type === "radio") {
                        if (input.checked) {
                            input.setAttribute("checked", "checked");
                        } else {
                            input.removeAttribute("checked");
                        }
                    } else {
                        input.setAttribute("value", input.value);
                    }
                });

                const contenido = contenedor.innerHTML;
                document.getElementById("htmlTabla").value = contenido;
                document.getElementById("Form04").submit();
            }
        </script>


        <Alerts:Alert/>        
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
