<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_device.tld" prefix="Devices" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dispositivos</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/device.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css" >
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Devices:Device/>
                </div>
            </div>
        </div>
        <script>
            function showDetail(cont) {
                if (document.getElementById("dvDetFront" + cont).style.display === "block") {
                    document.getElementById("dvDetFront" + cont).style.display = "none";
                    document.getElementById("dvDetBack" + cont).style.display = "block";
                    document.getElementById("arrow" + cont).classList.remove("fa-chevron-down");
                    document.getElementById("arrow" + cont).classList.add("fa-chevron-up");
                } else {
                    document.getElementById("dvDetFront" + cont).style.display = "block";
                    document.getElementById("dvDetBack" + cont).style.display = "none";
                    document.getElementById("arrow" + cont).classList.remove("fa-chevron-up");
                    document.getElementById("arrow" + cont).classList.add("fa-chevron-down");
                }
            }
        </script>

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
            /* --- Actualiza el input oculto con SOLO las filas que no contienen inputs --- */
            function actualizarInputOculto() {
                // seleccionar solo las filas que tienen el botón eliminar
                var filas = [];
                var todas = document.querySelectorAll('#tabla-body tr');
                for (var i = 0; i < todas.length; i++) {
                    if (todas[i].querySelector('.btn-danger')) {
                        filas.push(todas[i]);
                    }
                }

                var valores = [];

                for (var j = 0; j < filas.length; j++) {
                    var columnas = filas[j].getElementsByTagName('td');
                    var idName = columnas[0].textContent.trim();
                    var idType = columnas[1].textContent.trim();
                    var idVersion = columnas[2].textContent.trim();

                    // sin template literals -> concatenación tradicional:
                    var valor = '[' + idName + '/' + idType + '/' + idVersion + ']';

                    valores.push(valor);
                }

                document.getElementById('infoOculta').value = valores.join(' ');
            }


            /* --- Agregar fila (igual que antes pero llamando a la función robusta) --- */
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
                <td><button type="button" class="btn btn-danger btn-eliminar">Eliminar</button></td>
            `;

                filaHtml = filaHtml
                        .replace('xxNamexx', idName)
                        .replace('xxTypexx', idType)
                        .replace('xxVersionxx', idVersion);

                nuevaFila.innerHTML = filaHtml;
                tablaBody.appendChild(nuevaFila);

                actualizarInputOculto();

                // Limpiar inputs de captura
                document.getElementById('idName').value = '';
                document.getElementById('idType').value = '';
                document.getElementById('idVersion').value = '';
            }

            /* --- Manejo de eliminación con delegación (más fiable que onclick inline) --- */
            document.addEventListener('click', function (e) {
                // si pulsaron un botón eliminar dentro del tbody
                if (e.target && e.target.matches('#tabla-body .btn-eliminar, .btn-eliminar')) {
                    const fila = e.target.closest('tr');
                    if (fila)
                        fila.remove();
                    // actualizar oculto inmediatamente
                    actualizarInputOculto();
                }
            });

            function eliminarFila(boton) {
                const fila = boton.closest('tr');
                fila.remove();
                actualizarInputOculto(); // <- YA NO NECESITAS actualizarContador
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
                
                const requeridos = contenedor.querySelectorAll("[required]");
                for (let campo of requeridos) {
                    if (!campo.checkValidity()) {
                        campo.reportValidity();
                        campo.focus();
                        return;
                    }
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

        <script>
            function validData003() {
                const form = document.getElementById("formR03");
                var infoField = document.getElementById("infoField").value;
                var infoHide = document.getElementById("infoOculta").value;

                const showWarning = (msg) => {
                    iziToast.warning({
                        title: 'Atención!',
                        message: msg,
                        position: 'topRight',
                    });
                };

                if (!infoField) {
                    showWarning('No se ha seleccionado items.');
                    if (form.checkValidity()) {
                        form.submit();  // solo se envía si pasa validaciones
                    } else {
                        form.reportValidity(); // muestra mensajes nativos de HTML5
                    }
                } else if (!infoHide) {
                    showWarning('No se ha ingresado software instalado.');
                    if (form.checkValidity()) {
                        form.submit();  // solo se envía si pasa validaciones
                    } else {
                        form.reportValidity(); // muestra mensajes nativos de HTML5
                    }
                } else {
                }
            }

        </script>
        
        
        <script>
            function AddItem() {
                let tbody = document.getElementById("tbodyEquipos");
                let filaBoton = document.getElementById("filaBoton");
                let nuevaFila = document.createElement("tr");
                nuevaFila.innerHTML = `
                                    <td><input type="text" class="form-control" required></td>
                                    <td><input type="text" class="form-control" required></td>
                                    <td><input type="text" class="form-control" required></td>
                                    <td><input type="text" class="form-control" required></td>
                                    <td><input type="text" class="form-control" required></td>
                                    <td><button class="btn btn-danger" onclick="eliminarFila(this)"><i class="fas fa-trash"></i></button></td>
                                `;
                tbody.insertBefore(nuevaFila, filaBoton);
            }

            function eliminarFila(boton) {
                let fila = boton.closest("tr");
                fila.remove();
            }

        </script>


        <script>

            function AddSoftware() {

                let tbody = document.getElementById("tbodySoftware");
                let filaBoton = document.getElementById("filaBotonSoftware");

                let nuevaFila = document.createElement("tr");

                nuevaFila.innerHTML = `
                            <td><input type="text" class="form-control" required></td>
                            <td><input type="text" class="form-control" required></td>
                            <td><input type="text" class="form-control" required></td>
                            <td><button class="btn btn-danger" onclick="eliminarFila(this)"><i class="fas fa-trash"></i></button></td>
                        `;
                tbody.insertBefore(nuevaFila, filaBoton);
            }


            function eliminarFila(boton) {
                let fila = boton.closest("tr");
                fila.remove();

            }

        </script>

        <Alerts:Alert/>


        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/js/Filter.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>
    </body>
</html>
