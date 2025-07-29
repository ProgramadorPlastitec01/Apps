<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<%@taglib uri="/WEB-INF/tlds/Tld_appDetail.tld" prefix="AppDetails" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">


        <link rel="stylesheet" href="Interface/Content/FlmngrFroala/css/froala_editor.pkgd.min.css">
        <link rel="stylesheet" href="Interface/Content/FlmngrFroala/css/file.min.css">
        <link rel="stylesheet" href="Interface/Content/FlmngrFroala/css/image.min.css">


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
            canvas {
                border: 1px solid #000;
                background-image: url("Interface/Imagen/canvaBK.jpg");
                background-size: cover;
            }
        </style>

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <AppDetails:appDetail/>
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

        <script type="text/javascript">
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

        <!--        <script>
                    function dibujarFirmaPorUsuario(signa) {
                        const canvas = document.getElementById("signature-canvas-" + signa);
                        if (!canvas)
                            return;
        
                        const ctx = canvas.getContext('2d');
                        const hiddenInput = document.getElementById("coordenadas-hidden-" + signa);
                        if (!hiddenInput)
                            return;
        
                        const coordenadas = JSON.parse(hiddenInput.value);
        
                        // Limpiar el canvas
                        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
                        // Dibujar las coordenadas
                        coordenadas.forEach(coord => {
                            ctx.beginPath();
                            ctx.moveTo(coord.lx, coord.ly);
                            ctx.lineTo(coord.mx, coord.my);
                            ctx.strokeStyle = 'black';
                            ctx.lineWidth = 2;
                            ctx.stroke();
                        });
                    }
        
                    function dibujarTodasLasFirmas() {
                        const canvases = document.querySelectorAll("canvas[id^='signature-canvas-']");
                        canvases.forEach(canvas => {
                            const signa = canvas.id.split('-').pop(); // Extrae el ID del usuario
                            dibujarFirmaPorUsuario(signa);
                        });
                    }
        
                    // Ejecutar cuando se termine de cargar la página
                    window.onload = dibujarTodasLasFirmas;
                </script>-->


        <script type="text/javascript">
            $(function () {
                moment.locale('es');
                var start = moment().subtract(29, 'days');
                var end = moment();
                function cb(start, end) {
                    $('#reportrange span').html(start.format('D [de] MMMM [de] YYYY') + ' - ' + end.format('D [de] MMMM [de] YYYY'));
                    // Actualizar los campos ocultos del formulario
                    $('#startDate').val(start.format('YYYY-MM-DD'));
                    $('#endDate').val(end.format('YYYY-MM-DD'));
                }

                $('#reportrange').daterangepicker({
                    startDate: start,
                    endDate: end,
                    locale: {
                        format: 'YYYY/MM/DD',
                        separator: ' - ',
                        applyLabel: 'Aplicar',
                        cancelLabel: 'Cancelar',
                        fromLabel: 'Desde',
                        toLabel: 'Hasta',
                        customRangeLabel: 'Personalizado',
                        daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
                        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                        firstDay: 1
                    },
                    ranges: {
                        'Hoy': [moment(), moment()],
                        'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        'Últimos 7 Días': [moment().subtract(6, 'days'), moment()],
                        'Últimos 30 Días': [moment().subtract(29, 'days'), moment()],
                        'Este Mes': [moment().startOf('month'), moment().endOf('month')],
                        'Mes Pasado': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    }
                }, cb);
                cb(start, end);
            });
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

            var requerimientoCount = document.getElementById("InCouter").value;
            function addRequi() {
                requerimientoCount++;
                // Crear un nuevo elemento div para la nueva estructura
                const nuewcont = document.createElement('div');
                const contenedor = document.getElementById('ContRequi');
                let contenidoHTML = `
                            <div class='d-flex' style='width: 98%;'>
                                <div class='col-lg-5' style='padding: 0 !important;'>
                                    <span>Requerimiento</span>
                                    <input type='text' class='form-control' style='margin-right: 0; margin-left: 0;' name='txtRequi_XXXX' data-toggle='tooltip' data-placement='top' title='' value=''>
                                </div>

                                <div class=''>
                                    <span>Incremento</span>
                                    <div class='d-flex'>
                                        <input type='text' class='form-control ml-2' name='txtMoveX_XXXX' style='width: 43px;margin-right: 0px;' placeholder='X' value='' oninput='updateVersionFinal()'>
                                        <input type='text' class='form-control' name='txtMoveY_XXXX' style='width: 43px;margin-right: 0px;margin-left: 0px;' placeholder='Y' value='' oninput='updateVersionFinal()'>
                                        <input type='text' class='form-control mr-2' name='txtMoveJ_XXXX' style='width: 43px;margin-left: 0px;' placeholder='J' value='' oninput='updateVersionFinal()'>
                                    </div>
                                </div>

                                <div class='mr-2'>
                                    <span>Area</span>
                                    <input type='text' class='form-control' style='margin-left: 0; padding: 3px' name='txtArea_XXXX' value=''>
                                </div>

                                <div class='col-lg-2 mr-2' style='padding: 0;'>
                                    <span>Fecha solicitud</span>
                                    <input type='date' class='form-control' style='margin-left: 0px;' name='txtDateSol_XXXX' value=''>
                                </div>

                                <div class='col-lg-2' style='padding: 0;'>
                                    <span>Fecha ejecucion</span>
                                    <input type='date' class='form-control' style='margin-left: 0px;' name='txtDateEjec_XXXX' value=''>
                                </div>
                                
                                <div>
                                <button class='btn btn-danger btn-sm' onclick='removeRequi(this)' style='margin-top: 40px;margin-left: 6px;'><i class='fas fa-trash'></i></button>
                                </div>
        
                            </div>`;
                // Seleccionar el contenedor donde se agregarán los nuevos requerimientos


                contenidoHTML = contenidoHTML.replaceAll('XXXX', requerimientoCount);
                nuewcont.innerHTML = contenidoHTML;
                contenedor.appendChild(nuewcont);
                document.getElementById("InCouter").value = requerimientoCount;
            }

            function removeRequi(element) {
                // Eliminar el div padre más cercano al botón eliminar (es decir, la fila del requerimiento)
                element.closest('.d-flex').remove();
                requerimientoCount--;
                document.getElementById("InCouter").value = requerimientoCount;
                updateVersionFinal();
            }

            function removeRequ(element) {
                // Eliminar el div padre más cercano al botón eliminar (es decir, la fila del requerimiento)
                element.closest('.d-flex').remove();
                requerimientoCount--;
                document.getElementById("InCouter").value = requerimientoCount;
                updateVersionFinal();
            }


            function updateVersionFinal() {
                const inputsX = document.querySelectorAll('input[name^="txtMoveX"]');
                const inputsY = document.querySelectorAll('input[name^="txtMoveY"]');
                const inputsJ = document.querySelectorAll('input[name^="txtMoveJ"]');
                var result = "";
                let sumX = 0;
                let sumY = 0;
                let sumJ = 0;
                inputsX.forEach(input => {
                    const value = parseFloat(input.value) || 0;
                    sumX += value;
                });
                inputsY.forEach(input => {
                    const value = parseFloat(input.value) || 0;
                    sumY += value;
                });
                inputsJ.forEach(input => {
                    const value = parseFloat(input.value) || 0;
                    sumJ += value;
                });
                result = result.concat(sumX, ".", sumY, ".", sumJ);
                // Concatenar las sumas en el formato requerido
                document.getElementById("VersFinal").value = result;
                document.getElementById("VerFin").value = "Va. " + result;
            }


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


        <script>
            function validForm(dte, aff, per, edi) {
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
                    var cont = document.getElementById(edi).value;
                    if (personal == "[]" || personal == "" || cont == "") {
                        if (personal == "[]" || personal == "") {
                            $("#toastr-2").ready(function () {
                                iziToast.warning({
                                    title: 'Atención!',
                                    message: 'No se ha asignado personal.',
                                    position: 'topRight'
                                });
                            });
                        } else if (cont == "") {
                            $("#toastr-2").ready(function () {
                                iziToast.warning({
                                    title: 'Atención!',
                                    message: 'No se ha ingresado contenido al acta.',
                                    position: 'topRight'
                                });
                            });
                        }
                    } else {
                        document.getElementById("formData").submit();
                    }
                }
            }
        </script>
        
        <!-- Cargar jQuery -->
        <!--<script src="Interface/Content/Assets/modules/jquery.min.js"></script>-->
         <!--Cargar Moment.js--> 
        <script src="Interface/Content/Assets/modules/moment.min.js"></script>
         <!--Cargar Daterangepicker.js--> 
        <script type="text/javascript" src="Interface/Content/Assets/rangeCalendar/daterangepicker.js"></script>

        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
