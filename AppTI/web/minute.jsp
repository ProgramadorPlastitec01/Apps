<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_minute.tld" prefix="Minutes" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actas - TI</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">

        <link rel="stylesheet" href="Interface/Content/FlmngrFroala/css/froala_editor.pkgd.min.css">
        <link rel="stylesheet" href="Interface/Content/FlmngrFroala/css/file.min.css">
        <link rel="stylesheet" href="Interface/Content/FlmngrFroala/css/image.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">


        <style>
            .fr-image-resizer .fr-handler.fr-hnw{
                width: 10% !important;
                height: 10% !important;
            }
            .fr-image-resizer .fr-handler.fr-hne {
                width: 10% !important;
                height: 10% !important;
            }
            .fr-image-resizer .fr-handler.fr-hsw {
                width: 10% !important;
                height: 10% !important;
            }
            .fr-image-resizer .fr-handler.fr-hse {
                width: 10% !important;
                height: 10% !important;
            }
        </style>

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Minutes:Minute/>
                </div>
            </div>
        </div>

        <Alerts:Alert/>

        <script src="Interface/Content/FlmngrFroala/js/froala_editor.pkgd.min.js"></script>
        <script src="Interface/Content/FlmngrFroala/js/image.min.js"></script>
        <script src="Interface/Content/FlmngrFroala/js/file.min.js"></script>
        <script src="Interface/Content/FlmngrFroala/js/es.js"></script>
        <script src="Interface/Content/FlmngrFroala/js/froala-file-manager.js"></script>
        <script src="Interface/Content/FlmngrFroala/js/froala-image-editor.js"></script>

        <script>
            document.getElementById('table-filter').addEventListener('keyup', function () {
                var filter = this.value.toLowerCase();
                var listItems = document.getElementById('available-persons').getElementsByTagName('li');
                for (var i = 0; i < listItems.length; i++) {
                    var text = listItems[i].textContent || listItems[i].innerText;
                    if (text.toLowerCase().indexOf(filter) > -1) {
                        listItems[i].style.display = ''; // Mostrar
                    } else {
                        listItems[i].style.display = 'none'; // Ocultar
                    }
                }
            });
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Obtener las listas y el campo oculto
                var availableList = document.getElementById('available-persons');
                var assignedList = document.getElementById('assigned-persons');
                var assignedPersonIdsInput = document.getElementById('assigned-person-ids');
                // Función para ordenar los elementos de la lista
                function sortList(list) {
                    var items = Array.from(list.children);
                    items.sort(function (a, b) {
                        return a.textContent.localeCompare(b.textContent);
                    });
                    items.forEach(function (item) {
                        list.appendChild(item);
                    });
                }

                // Función para actualizar el campo oculto con la información de las personas asignadas
                function updateAssignedPersonIds() {
                    var assignedInfo = [];
                    Array.from(assignedList.children).forEach(function (item) {
                        assignedInfo.push(item.getAttribute('data-person-info')); // Obtener el valor de 'data-person-info'
                    });
                    assignedPersonIdsInput.value = "[" + assignedInfo.join("][") + "]"; // Formato con corchetes
                }

                // Añadir el evento de clic para mover a 'Seleccionado'
                availableList.addEventListener('click', function (event) {
                    var item = event.target;
                    if (item.tagName === 'LI') {
                        assignedList.appendChild(item); // Mover a la lista de asignados
                        updateAssignedPersonIds(); // Actualizar la información seleccionada
                    }
                });
                // Añadir el evento de clic para devolver a 'Personal'
                assignedList.addEventListener('click', function (event) {
                    var item = event.target;
                    if (item.tagName === 'LI') {
                        availableList.appendChild(item); // Mover de vuelta a la lista de disponibles
                        sortList(availableList); // Ordenar la lista de disponibles
                        updateAssignedPersonIds(); // Actualizar la información seleccionada
                    }
                });
            });
        </script>
        <script>
            const canvas = document.getElementById('signature-canvas');
            const context = canvas.getContext('2d');
            const hiddenInput = document.getElementById('coordenadas-hidden');
            const isTouchDevice = 'ontouchstart' in window || navigator.maxTouchPoints > 0;

            if (isTouchDevice) {
                canvas.addEventListener('touchstart', startDrawing);
                canvas.addEventListener('touchend', stopDrawing);
                canvas.addEventListener('touchmove', draw);
            } else {
                canvas.addEventListener('mousedown', startDrawing);
                canvas.addEventListener('mouseup', stopDrawing);
                canvas.addEventListener('mousemove', draw);
            }

            let isDrawing = false;
            let lastPoint = null; // Última coordenada conocida
            let coordinates = []; // Almacena trazos en formato requerido

            function startDrawing(event) {
                isDrawing = true;
                lastPoint = getCoordinates(event); // Guardar el punto inicial
            }

            function stopDrawing() {
                isDrawing = false;
                lastPoint = null; // Reiniciar última coordenada
                guardarCoordenadas();
            }

            function draw(event) {
                if (!isDrawing)
                    return;

                const currentPoint = getCoordinates(event);
                if (lastPoint) {
                    coordinates.push({
                        lx: lastPoint.x,
                        ly: lastPoint.y,
                        mx: currentPoint.x,
                        my: currentPoint.y,
                    });

                    context.beginPath();
                    context.moveTo(lastPoint.x, lastPoint.y);
                    context.lineTo(currentPoint.x, currentPoint.y);
                    context.stroke();
                    context.closePath();
                }

                lastPoint = currentPoint; // Actualizar la última coordenada
            }

            function getCoordinates(event) {
                const rect = canvas.getBoundingClientRect();
                if (event.type.includes('touch')) {
                    return {
                        x: event.touches[0].clientX - rect.left,
                        y: event.touches[0].clientY - rect.top,
                    };
                } else {
                    return {
                        x: event.clientX - rect.left,
                        y: event.clientY - rect.top,
                    };
                }
            }

            function clearCanvas() {
                context.clearRect(0, 0, canvas.width, canvas.height);
                coordinates = []; // Reiniciar coordenadas
                hiddenInput.value = ''; // Limpiar el input oculto
            }

            function guardarCoordenadas() {
                // Convertir las coordenadas en JSON
                const coordinatesJSON = JSON.stringify(coordinates);
                hiddenInput.value = coordinatesJSON;
            }
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Obtener las listas y el campo oculto
                var availableList = document.getElementById('available-persons');
                var assignedList = document.getElementById('assigned-persons');
                var assignedPersonIdsInput = document.getElementById('assigned-person-ids');
                // Función para ordenar los elementos de la lista
                function sortList(list) {
                    var items = Array.from(list.children);
                    items.sort(function (a, b) {
                        return a.textContent.localeCompare(b.textContent);
                    });
                    items.forEach(function (item) {
                        list.appendChild(item);
                    });
                }

                // Función para actualizar el campo oculto con la información de las personas asignadas
                function updateAssignedPersonIds() {
                    var assignedInfo = [];
                    Array.from(assignedList.children).forEach(function (item) {
                        assignedInfo.push(item.getAttribute('data-person-info')); // Obtener el valor de 'data-person-info'
                    });
                    assignedPersonIdsInput.value = "[" + assignedInfo.join("][") + "]"; // Formato con corchetes
                }

                // Añadir el evento de clic para mover a 'Seleccionado'
                availableList.addEventListener('click', function (event) {
                    var item = event.target;
                    if (item.tagName === 'LI') {
                        assignedList.appendChild(item); // Mover a la lista de asignados
                        updateAssignedPersonIds(); // Actualizar la información seleccionada
                    }
                });
                // Añadir el evento de clic para devolver a 'Personal'
                assignedList.addEventListener('click', function (event) {
                    var item = event.target;
                    if (item.tagName === 'LI') {
                        availableList.appendChild(item); // Mover de vuelta a la lista de disponibles
                        sortList(availableList); // Ordenar la lista de disponibles
                        updateAssignedPersonIds(); // Actualizar la información seleccionada
                    }
                });
            });
        </script>

        <script>
            function ValidData(docz, codx) {
                document.getElementById("idDocx").value = docz;
                document.getElementById("idCodx").value = codx;
            }
            function FindDoc() {
                var dox = document.getElementById("FinDocx").value;
                var VDoc = document.getElementById("idDocx").value;
                if (dox == VDoc) {
                    document.getElementById("DocSpan").style.display = "none";
                } else {
                    document.getElementById("DocSpan").style.display = "block";
                }
            }
            function FindCod() {
                var cox = document.getElementById("FinCodx").value;
                var VCod = document.getElementById("idCodx").value;
                if (cox == VCod) {
                    document.getElementById("CodSpan").style.display = "none";
                } else {
                    document.getElementById("CodSpan").style.display = "block";
                }
            }
        </script>

        <script type="text/javascript">
            new FroalaEditor('#editorNext', {
                Flmngr: {
                    apiKey: 'z5tCL8YdVd99dhc5MQCNlQlo',
                    urlFileManager: 'http://172.16.1.164/flmngr/flmngr.php',
                    urlFiles: 'http://172.16.1.164/flmngr/files/'
                }
            });
        </script>

        <script>
            function validForm(dte, aff, per) {
                var datefor = document.getElementById(dte).value;
                var affair = document.getElementById(aff).value;

                if (affair == "" || datefor == "") {
                    $("#toastr-2").ready(function () {
                        iziToast.warning({
                            title: 'Atención!',
                            message: 'No se ha ingresado datos basicos.',
                            position: 'topRight'
                        });
                    });
                } else {
                    var personal = document.getElementById(per).value;
                    if (personal == "[]" || personal == "") {
                        $("#toastr-2").ready(function () {
                            iziToast.warning({
                                title: 'Atención!',
                                message: 'No se ha asignado personal.',
                                position: 'topRight'
                            });
                        });
                    } else {
                        document.getElementById("formData").submit();
                    }
                }
            }
        </script>


        <script>
            function uploadFiles() {
                // Obtener la última fecha de subida del storage
                let lastUploadTime = localStorage.getItem('lastUploadTime') || new Date().toISOString();
                // Guardar el valor actual de lastUploadTime como valor anterior
                localStorage.setItem('previousUploadTime', lastUploadTime);
                // Guardar la fecha y hora actual como nueva última subida
                lastUploadTime = new Date().toISOString();
                localStorage.setItem('lastUploadTime', lastUploadTime);
                // Obtener archivos en el intervalo de tiempo
                let filesToUpload = []; // Aquí puedes agregar lógica para llenar este array con los archivos que desees subir

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "http://172.16.1.153/PMP_MI/flmngr/envio.php", true);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        alert("Archivos subidos correctamente.");
                    }
                };
                // Enviar los datos en formato JSON
                xhr.send(JSON.stringify({
                    files: filesToUpload,
                    lastUploadTime: lastUploadTime,
                    previousUploadTime: localStorage.getItem('previousUploadTime')
                }));
            }

            // Aquí puedes llamar a uploadFiles() cuando lo necesites, por ejemplo, en un botón
            // document.getElementById('uploadButton').addEventListener('click', uploadFiles);
        </script>

        <script>
            function dataPass() {
                document.getElementById("NewData").value = document.getElementById("editorNext").value;
                document.FromNew.submit();
            }
        </script>


        <script>
            document.getElementById('btnImprimir').addEventListener('click', () => {
                // Obtener el contenido de la sección a imprimir
                const contenido = document.getElementById('dataDocument').outerHTML;

                // Crear una nueva ventana o iframe para imprimir
                const ventanaImpresion = window.open('', '_blank', 'width=800, height=600');

                // Escribir el contenido HTML en la ventana
                let cont = `<html>
                            <head>
                                <title>Imprimir</title>
                                <style>
                                    body { 
                                        font-family: Arial, sans-serif;
                                        margin-top: 20mm;  /* Margen superior */
                                        margin-bottom: 20mm; /* Margen inferior */
                                    }
                                    #content {
                                        margin-top: 10mm;
                                        margin-bottom: 10mm;
                                    }
                                    table {
                                        width: 100%;
                                        border-collapse: collapse; /* Evitar bordes duplicados */
                                    }
                                    table, th, td {
                                        border: 1px solid black; /* Borde negro */
                                    }
                                    th, td {
                                        padding: 8px; /* Espaciado interno */
                                        text-align: left; /* Alineación del texto */
                                    }
                                </style>
                            </head>
                            <body>
                                <div id="content">XXXDATAXXX</div>
                            </body>
                        </html>`;

                cont = cont.replace('XXXDATAXXX', contenido);

                ventanaImpresion.document.write(cont);

                // Esperar a que la nueva ventana cargue el contenido y luego enviar a imprimir
                ventanaImpresion.document.close(); // Cerrar el documento
                ventanaImpresion.print(); // Imprimir
                ventanaImpresion.close(); // Cerrar la ventana después de imprimir
            });


        </script>





        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
