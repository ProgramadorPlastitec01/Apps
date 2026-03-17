
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/visual"  prefix="Visual" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <!--        <script type="text/javascript">
                    history.pushState(null, null, 'Visual.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Visual.jsp');
                    });
                </script>-->
    </head>
    <body class="sidebar-mini">
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
                <!-- Alerta -->
                <div id="alerta" class="alert alert-warning" style="display:none; margin-top:10px; background-color: #f9e4c7; color: black">
                    ⚠️ Atención: No se encontró información para los datos ingresados. 
                    <button class="btn btn-sm btn-dark" onclick="mostrarDetalles()">Ver detalles</button>
                </div>

                <!-- Modal de eventos -->
                <div id="modalEventos" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
                     background:rgba(0,0,0,0.5); z-index:9999; justify-content:center; align-items:center;">
                    <div style="background:white; padding:20px; border-radius:10px; width:80%; max-width:800px;">
                        <h4>📋 Eventos sin información</h4>
                        <table id="tablaEventos" border="1" width="100%" style="border-collapse:collapse;">
                            <thead>
                                <tr>
                                    <th>Tipo</th>
                                    <th>Orden</th>
                                    <th>Producto</th>
                                    <th>Lote</th>
                                    <th>Mensaje</th>
                                    <th>Fecha</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                        <div style="text-align:right; margin-top:10px;">
                            <button class="btn btn-dark" onclick="cerrarModal()">Cerrar</button>
                        </div>
                    </div>
                </div>
            <Visual:Visual/>
            <Alert:Alert/>

        </div>
        <script>
            document.addEventListener('DOMContentLoaded', () => {

                const isEmpty = t => !t || t.trim() === '' || t.trim() === '-' || t.trim() === '----';

                // =========================
                // 📞 TELÉFONO → pintar TD
                // =========================
                document.querySelectorAll('td').forEach(td => {
                    const label = td.innerText.trim().toUpperCase();

                    if (label === 'PHONE:' || label === 'TELÉFONO:') {
                        const valueTd = td.nextElementSibling;
                        if (!valueTd)
                            return;

                        const text = valueTd.innerText.trim();

                        if (isEmpty(text))
                            valueTd.classList.add('pending');
                        else
                            valueTd.classList.remove('pending');
                    }
                });

                // ==================================
                // 🧾 CAMPOS EDITABLES
                // ==================================
                document.querySelectorAll('.editable').forEach(el => {

                    const getText = () => el.innerText.trim();

                    // Estado inicial
                    if (getText() === '' || getText() === '----') {
                        el.innerText = '----';
                        el.classList.add('pending');
                    }

                    // Focus
                    el.addEventListener('focus', () => {
                        if (getText() === '----')
                            el.innerText = '';
                    });

                    // Blur
                    el.addEventListener('blur', () => {
                        if (getText() === '') {
                            el.innerText = '----';
                            el.classList.add('pending');
                        } else {
                            el.classList.remove('pending');
                        }
                    });

                    // Mientras escribe
                    el.addEventListener('input', () => {
                        const t = getText();
                        if (t !== '' && t !== '----') {
                            el.classList.remove('pending');
                        }
                    });

                });

            });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {

                // Quitar borde rojo al editar textos
                ['clientValue', 'AmountValue'].forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.addEventListener('input', function () {
                            this.style.border = '';
                        });
                    }
                });

                // ✅ Quitar borde rojo cuando se seleccione fecha
                const dateInput = document.querySelector('#DateDispatch input');
                if (dateInput) {
                    dateInput.addEventListener('change', function () {
                        this.style.border = '';
                    });
                }

            });
        </script>
        <script>
            function saveHtml() {

                var form = document.getElementById('FormGenerate');
                if (!form) {
                    iziToast.error({
                        title: 'Error',
                        message: 'Formulario no encontrado',
                        position: 'bottomRight'
                    });
                    return;
                }

                var htmlContainer = document.getElementById('HtmlContent');

                // Sincronizar inputs
                if (htmlContainer) {
                    htmlContainer.querySelectorAll('input').forEach(input => {
                        if (input.type === 'radio' || input.type === 'checkbox') {
                            input.checked
                                    ? input.setAttribute('checked', 'checked')
                                    : input.removeAttribute('checked');
                        } else {
                            input.setAttribute('value', input.value);
                        }
                    });
                }

                // HTML actualizado
                var contentHtml = htmlContainer ? htmlContainer.innerHTML : "";
                var encodedHtml = encodeURIComponent(contentHtml);

                // ===== CAPTURA =====
                var clientSpan = document.getElementById('clientValue');
                        var clientText = clientSpan?.textContent.trim() || '';

                var amountSpan = document.getElementById('AmountValue');
                        var amountRaw = amountSpan?.textContent.trim() || '';
                var amountClean = amountRaw.replace(/[^\d]/g, '');
                var amountNumber = Number(amountClean);

                var dateInput = document.querySelector('#DateDispatch input');
                        var dateValue = dateInput?.value || '';
                ;

                var consText = document.getElementById('consValue')?.textContent.trim() || '';
                        var idRegisterText = document.getElementById('IdRegister')?.textContent.trim() || '';
                        var Code = document.getElementById('codeValue')?.textContent.trim() || '';

                // ===== VALIDACIONES =====
                if (clientText === '' || clientText === '-----') {
                    showWarning(clientSpan, 'Debe ingresar el Cliente.');
                    return;
                }

                if (!amountClean || isNaN(amountNumber) || amountNumber <= 0) {
                    showWarning(amountSpan, 'El monto debe ser mayor a cero.');
                    return;
                }

                if (dateValue === '') {
                    showWarning(dateInput, 'Debe ingresar la fecha de despacho.');
                    return;
                }

                // ===== HIDDEN INPUTS =====
                const addHidden = (name, value) => {
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = name;
                    input.value = value;
                    form.appendChild(input);
                };


                // Limpiar hidden inputs previos

                let batchValues = [];

                if (htmlContainer) {
                    htmlContainer.querySelectorAll('[id^="IdBatchM"]').forEach(td => {
                        const value = td.textContent.trim();
                        if (value !== '') {
                            batchValues.push(value);
                        }
                    });
                }

                // UNIFICAR (eliminar duplicados sin alertar)
                const uniqueBatches = [...new Set(batchValues)];

                addHidden('Html', encodedHtml);
                addHidden('clientValue', clientText);
                addHidden('AmountValue', amountNumber);
                addHidden('DateDispatch', dateValue);
                addHidden('MaterialBatches', uniqueBatches.join(','));
                addHidden('MaterialBatchCount', uniqueBatches.length);


                if (consText)
                    addHidden('ConsValue', consText);
                if (idRegisterText)
                    addHidden('IdRegisterValue', idRegisterText);
                if (Code)
                    addHidden('codeValue', Code);

                // ===== SUBMIT =====
                form.submit();
            }
        </script>
        <script>
            /* ===================== ALERTA ===================== */
            function showWarning(element, message) {
                iziToast.warning({
                    title: 'Atención!',
                    message: message,
                    position: 'bottomRight',
                    timeout: 4000
                });

                if (element) {
                    element.focus();
                    element.style.border = '2px solid red';
                }
            }
        </script>


        <script>
            function dibujarCoordenadas() {
                const canvas = document.getElementById('signature-canvas');
                const ctx = canvas.getContext('2d');
                const input = document.getElementById('coordenadas-hidden');

                if (!input || !input.value)
                    return;

                // 1) Leer y decodificar entidades html
                let raw = input.value;
                raw = raw.replace(/&quot;/g, '"').replace(/&#39;/g, "'").trim();

                // Función auxiliar para intentar parsear y devolver resultado o null
                function tryParse(s) {
                    try {
                        return JSON.parse(s);
                    } catch (e) {
                        console.warn("Intento de parse falló:", e.message);
                        return null;
                    }
                }

                // 2) Intento directo
                let coordenadas = tryParse(raw);
                if (!coordenadas) {
                    // 3) Si empieza con '{' (objeto) y no con '[' (array), envolver en []
                    if (raw.startsWith('{') && !raw.startsWith('[')) {
                        let candidate = '[' + raw + ']';
                        coordenadas = tryParse(candidate);
                        if (coordenadas)
                            raw = candidate;
                    }
                }

                if (!coordenadas) {
                    // 4) Si hay '}{' pegados, reemplazar '}{' por '},{' y envolver en []
                    if (raw.indexOf('}{') !== -1) {
                        let fixed = raw.replace(/}\s*{/g, '},{');
                        if (!fixed.startsWith('['))
                            fixed = '[' + fixed + ']';
                        coordenadas = tryParse(fixed);
                        if (coordenadas)
                            raw = fixed;
                    }
                }

                if (!coordenadas) {
                    // 5) Último recurso
                    let cleaned = raw.replace(/[\u0000-\u001f\u007f-\u009f]/g, '');
                    cleaned = cleaned.replace(/,\s*]/g, ']');
                    if (!cleaned.startsWith('[') && cleaned.startsWith('{'))
                        cleaned = '[' + cleaned + ']';
                    cleaned = cleaned.replace(/}\s*,\s*}/g, '},{');
                    coordenadas = tryParse(cleaned);
                    if (coordenadas)
                        raw = cleaned;
                }

                if (!coordenadas) {
                    console.error("No pude parsear las coordenadas. Valor final probado:", raw.slice(0, 500));
                    return;
                }

                if (!Array.isArray(coordenadas)) {
                    coordenadas = [coordenadas];
                }

                ctx.clearRect(0, 0, canvas.width, canvas.height);

                // ⚙️ ---- ESCALADO AUTOMÁTICO Y CENTRADO ---- ⚙️
                const maxX = Math.max(...coordenadas.map(c => Math.max(c.lx, c.mx)));
                const maxY = Math.max(...coordenadas.map(c => Math.max(c.ly, c.my)));
                const minX = Math.min(...coordenadas.map(c => Math.min(c.lx, c.mx)));
                const minY = Math.min(...coordenadas.map(c => Math.min(c.ly, c.my)));

                const originalWidth = maxX - minX;
                const originalHeight = maxY - minY;

                const scaleX = canvas.width / originalWidth;
                const scaleY = canvas.height / originalHeight;
                const scale = Math.min(scaleX, scaleY); // mantiene proporción

                // Calcular offset para centrar
                const offsetX = (canvas.width - originalWidth * scale) / 2 - minX * scale;
                const offsetY = (canvas.height - originalHeight * scale) / 2 - minY * scale;

                console.log(`Escala aplicada: ${scale.toFixed(2)} | Offset: (${offsetX.toFixed(1)}, ${offsetY.toFixed(1)})`);

                // ---- DIBUJAR CON ESCALA Y CENTRADO ----
                ctx.strokeStyle = 'black';
                ctx.lineWidth = 1.5;

                coordenadas.forEach(coord => {
                    if (coord && typeof coord.lx === 'number' && typeof coord.ly === 'number'
                            && typeof coord.mx === 'number' && typeof coord.my === 'number') {

                        ctx.beginPath();
                        ctx.moveTo(coord.lx * scale + offsetX, coord.ly * scale + offsetY);
                        ctx.lineTo(coord.mx * scale + offsetX, coord.my * scale + offsetY);
                        ctx.stroke();
                    }
                });

                console.log("Dibujo completado. Puntos dibujados:", coordenadas.length);
            }

            document.addEventListener('DOMContentLoaded', dibujarCoordenadas);
        </script>
        <script>
            // Inicializa el arreglo global para almacenar eventos sin información
            window.NoDataEvents = [];
        </script>
        <div class="modal fade" id="htmlModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl">
                <div class="modal-content">
                    <div class="modal-header bg-secondary text-white">
                        <h5 class="modal-title">Vista previa del adjunto</h5>
                    </div>
                    <div class="modal-body" id="htmlModalBody" style="overflow:auto; max-height:68vh;">
                        <!-- Contenido dinámico -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary" onclick="downloadHtmlAsPDF()">Descargar PDF</button>
                    </div>
                </div>
            </div>
        </div>>
    </div>
    <script>
        function showHtmlAttachmentById(htmlContainerId) {
            const container = document.getElementById(htmlContainerId);
            if (!container)
                return;

            const modalBody = document.getElementById('htmlModalBody');
            modalBody.innerHTML = container.innerHTML;

            // 🔹 Ajuste dinámico del tamaño según el contenido
            const modalDialog = modalBody.closest('.modal-dialog');
            modalDialog.style.width = 'auto';
            modalDialog.style.maxWidth = '68vw'; // ocupa hasta 95% del ancho de pantalla
            modalBody.style.maxHeight = '58vh'; // ocupa hasta 85% de la altura

            const modal = new bootstrap.Modal(document.getElementById('htmlModal'));
            modal.show();

            // 🔹 Ajusta automáticamente el alto si el contenido es pequeño
            setTimeout(() => {
                const contentHeight = modalBody.scrollHeight;
                const windowHeight = window.innerHeight * 0.85;
                if (contentHeight < windowHeight) {
                    modalBody.style.maxHeight = contentHeight + 'px';
                }
            }, 200);
        }

        function downloadHtmlAsPDF() {
            const content = document.getElementById('htmlModalBody').innerHTML;
            const w = window.open('', '_blank');
            w.document.write('<html><head><title>Adjunto</title></head><body>' + content + '</body></html>');
            w.document.close();
            setTimeout(() => w.print(), 400);
        }
        (function () {
            function attachCloseHandlers() {
                const modalEl = document.getElementById('htmlModal');
                if (!modalEl)
                    return;

                // Asegurar una instancia de bootstrap.Modal
                let modalInstance = null;
                try {
                    modalInstance = bootstrap.Modal.getOrCreateInstance(modalEl);
                } catch (e) {
                    console.warn('Bootstrap Modal API no disponible:', e);
                }

                // Botones con data-bs-dismiss="modal" dentro del modal
                const dismissButtons = modalEl.querySelectorAll('[data-bs-dismiss="modal"]');
                dismissButtons.forEach(btn => {
                    // quitar escuchadores antiguos si los hay
                    btn.removeEventListener('click', btn._closeHandler || function () {});
                    const handler = function (e) {
                        e.preventDefault();
                        if (modalInstance && typeof modalInstance.hide === 'function') {
                            modalInstance.hide();
                        } else {
                            // fallback: ocultar manualmente
                            modalEl.classList.remove('show');
                            modalEl.style.display = 'none';
                            document.body.classList.remove('modal-open');
                            const backdrop = document.querySelector('.modal-backdrop');
                            if (backdrop)
                                backdrop.remove();
                        }
                    };
                    btn.addEventListener('click', handler);
                    btn._closeHandler = handler;
                });

                // Si cierras el modal por fuera (backdrop o ESC), asegúrate de que la instancia existe
                // (opcional) manejar ESC manualmente si quieres:
                document.addEventListener('keydown', function (ev) {
                    if (ev.key === 'Escape') {
                        if (modalInstance && typeof modalInstance.hide === 'function')
                            modalInstance.hide();
                    }
                });
            }

            // Esperar a que DOM y bootstrap estén listos
            if (document.readyState === 'loading') {
                document.addEventListener('DOMContentLoaded', attachCloseHandlers);
            } else {
                attachCloseHandlers();
            }
        })();
    </script>
    <script>
        function confirmarDevolucion(url) {
            swal({
                title: "¿Está seguro de devolver?",
                text: "Por favor, justifique la razón de la devolucion",
                content: {
                    element: "textarea",
                    attributes: {
                        placeholder: "Escriba aquí la justificación...",
                        id: "razonDevolucion"
                    },
                },
                icon: "warning",
                buttons: {
                    cancel: {
                        text: "Cancelar",
                        visible: true,
                        className: "btn btn-secondary",
                        closeModal: true,
                    },
                    confirm: {
                        text: "Devolver",
                        visible: true,
                        className: "btn btn-green",
                        closeModal: false,
                    },
                },
                dangerMode: true,
            }).then((value) => {
                if (value) {
                    const razon = document.getElementById("razonDevolucion").value.trim();
                    if (!razon) {
                        swal("Debe justificar la devolución", {
                            icon: "error",
                        });
                        return;
                    }
                    // Redirige al servlet con la razón codificada
                    const razonEncoded = encodeURIComponent(razon);
                    window.location.href = url + "&Justification=" + razonEncoded;
                }
            });
        }
    </script>

    <script src="Interface/Content/Assets/js/eventLogger.js"></script>
    <script src="Interface/Content/Assets/js/Print.js"></script>
    <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
</body>
</html>
