
<%@page import="Controller.CodeJpaController"%>
<%@page import="com.google.gson.Gson"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/visual"  prefix="Visual" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@page import="Controller.CustomerJpaController"%>
<%@page import="java.util.List"%>


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
                    <div class='float-right'><button class="btn btn-sm btn-dark " onclick="mostrarDetalles()">Ver detalles</button></div>
                </div>

                <div id="alertaRegistrosError"
                     class="alert alert-info"
                     style="display:none; margin-top:10px; background-color:#bae9ff; color:black;">
                    ✖️ Diferencias en la información: 
                    <div class='float-right'><button class="btn btn-sm btn-dark " onclick="mostrarDetalles()">Ver detalles</button></div>
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
                const phoneTd = document.getElementById('TelefonoValue');
                if (phoneTd) {
                    const phoneEditable = phoneTd.querySelector('.editable');
                    if (phoneEditable) {

                        const getText = () => phoneEditable.innerText.trim();
                        const updatePhoneState = () => {
                            if (isEmpty(getText())) {
                                phoneTd.classList.add('pending'); // gris en TD
                                phoneEditable.innerText = ''; // ✅ nunca mostrar ----
                            } else {
                                phoneTd.classList.remove('pending');
                            }
                        };
                        // Estado inicial
                        updatePhoneState();
                        // Mientras escribe
                        phoneEditable.addEventListener('input', updatePhoneState);
                        // Al salir
                        phoneEditable.addEventListener('blur', updatePhoneState);
                    }
                }

                document.querySelectorAll('.editable').forEach(el => {

                    // 🚫 excluir teléfono
                    if (el.closest('#TelefonoValue'))
                        return;
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
                    text: "Por favor, justifique la razón de la devolución.",
                    content: {
                        element: "textarea",
                        attributes: {
                            placeholder: "Escriba aquí la justificación...",
                            id: "razonDevolucion"
                        }
                    },
                    icon: "warning",
                    buttons: {
                        cancel: {
                            text: "Cancelar",
                            visible: true,
                            className: "btn btn-secondary",
                            closeModal: true
                        },
                        confirm: {
                            text: "Devolver",
                            visible: true,
                            className: "btn btn-success",
                            closeModal: false
                        }
                    },
                    dangerMode: true
                }).then((value) => {

                    if (!value)
                        return;
                    const razon = document.getElementById("razonDevolucion").value.trim();
                    if (!razon) {
                        swal({
                            title: "Justificación requerida",
                            text: "Debe ingresar una justificación para realizar la devolución.",
                            icon: "error"
                        });
                        return;
                    }

                    // Crear contenido de carga
                    const loading = document.createElement("div");
                    loading.innerHTML = `
                <div class="loader"></div>
                <div class="loader-text">
                    <strong>Procesando devolución...</strong><br>
                    Registrando la devolución y enviando la notificación por correo.<br><br>
                    <small>Este proceso puede tardar algunos segundos.<br>Por favor, no cierre esta ventana.</small>
                </div>
            `;
                    // Mostrar alerta de espera
                    swal({
                        title: "Espere un momento",
                        content: loading,
                        buttons: false,
                        closeOnClickOutside: false,
                        closeOnEsc: false
                    });
                    // Dar tiempo para que el usuario vea la animación antes de redirigir
                    setTimeout(function () {
                        window.location.href = url + "&Justification=" + encodeURIComponent(razon);
                    }, 500);
                });
            }

        </script>
        <%
            CustomerJpaController customerJpa = new CustomerJpaController();
            List lstCustomer = customerJpa.ConsultCustomer();

            Gson gson = new Gson();
            String jsonCustomer = gson.toJson(lstCustomer);
        %>
        <script>

            window.lstCustomers = <%=jsonCustomer%>;
            window.lstCustomers = window.lstCustomers.map(c => ({
                    id: c[0],
                    name: c[1],
                    address: c[2],
                    city: c[3],
                    country: c[4]
                }));

        </script>
        <script>
            function UpdateCustomer() {

                let html = `
            <div class="mb-2">
                <input type="text"
                       id="buscarCliente"
                       class="form-control"
                       placeholder="🔍 Buscar cliente..."
                       onkeyup="filtrarClientes()">
            </div>

            <div style="max-height:420px; overflow-y:auto; border:1px solid #dee2e6; border-radius:6px;" >

            <table class="table table-bordered table-hover table-sm mb-0" id="tablaClientes" style="font-size: 13px;color: black;text-align: left;">

                <thead style="position:sticky; top:0; background:#dccbfe; color:black; z-index:10;">

                    <tr>
                        <th style="width:28%">Cliente</th>
                        <th style="width:30%">Dirección</th>
                        <th style="width:12%">Ciudad</th>
                        <th style="width:12%">País</th>
                        <th style="width:10% text-align: center;">Opc</th>
                    </tr>

                </thead>

                <tbody>
        `;

                window.lstCustomers.forEach(c => {

                    html += "<tr>";

                    html += "<td>" + c.name + "</td>";
                    html += "<td>" + c.address + "</td>";
                    html += "<td>" + c.city + "</td>";
                    html += "<td>" + c.country + "</td>";

                    html += "<td style='text-align:center'>";
                    html += "<button class='btn btn-success btn-sm' onclick='seleccionarCliente(" + c.id + ")'>";
                    html += "<i class='fas fa-check'></i>";
                    html += "</button>";
                    html += "</td>";

                    html += "</tr>";

                });

                html += `
                </tbody>

            </table>

            </div>
        `;

                swal({

                    title: "Seleccionar Cliente",

                    content: {
                        element: "div",
                        attributes: {
                            innerHTML: html
                        }
                    },

                    button: "Cerrar"

                });

                setTimeout(function () {

                    const modal = document.querySelector(".swal-modal");

                    modal.style.width = "878px";
                    modal.style.maxWidth = "878px";
                    modal.style.borderRadius = "10px";
                    modal.style.height = "height: 677px;";

                    document.querySelector(".swal-content").style.padding = "10px 20px";

                }, 50);

            }
        </script>
        <script>
            function filtrarClientes() {

                let filtro = document.getElementById("buscarCliente").value.toUpperCase();

                let filas = document.querySelectorAll("#tablaClientes tbody tr");

                filas.forEach(fila => {

                    let texto = fila.innerText.toUpperCase();

                    fila.style.display = texto.indexOf(filtro) > -1 ? "" : "none";

                });

            }
        </script>
        <script>
            function actualizarCampo(id, valor) {
                const elemento = document.getElementById(id);
                if (!elemento)
                    return;
                elemento.textContent = valor || "";
                elemento.classList.add("editable");
                elemento.setAttribute("contenteditable", "true");
                // Si manejas estados pendientes, puedes remover esa clase
                elemento.classList.remove("pending");
            }
            function seleccionarCliente(id) {

                try {

                    const cliente = window.lstCustomers.find(c => c.id == id);

                    if (!cliente)
                        return;

                    actualizarCampo("clientValue", cliente.name);
                    actualizarCampo("tAddress", cliente.address);
                    actualizarCampo("tCity", cliente.city);
                    actualizarCampo("tCountry", cliente.country);

                    swal.close();

                    iziToast.success({
                        title: "Correcto",
                        message: "Cliente actualizado correctamente.",
                        position: "topRight"
                    });

                } catch (e) {
                    console.error(e);
                }

            }
        </script>
        <%
            // ============================================================
            // CONSULTAR CÓDIGOS
            // ============================================================

            CodeJpaController codeJpa = new CodeJpaController();
            List lstCode = codeJpa.ConsultCode();

            Gson gsonCode = new Gson();
            String jsonCode = gsonCode.toJson(lstCode);
        %>
        <script>

            // ============================================================
            // CARGAR CÓDIGOS
            // ============================================================

            window.lstCodes = <%=jsonCode%>;

            window.lstCodes = window.lstCodes.map(c => ({
                    id: c[0],
                    code: c[1],
                    client: c[2]
                }));

        </script>
        <script>

            // ============================================================
            // ABRIR MODAL
            // ============================================================

            function UpdateCustomerCode() {

                let html = `

                    <div class="mb-2">

                        <input
                            type="text"
                            id="buscarCodigo"
                            class="form-control"
                            placeholder="🔍 Buscar cliente o código..."
                            onkeyup="filtrarCodigos()"
                        >

                    </div>


                    <div
                        style="
                            max-height:420px;
                            overflow-y:auto;
                            border:1px solid #dee2e6;
                            border-radius:6px;
                        "
                    >

                        <table
                            class="table table-bordered table-hover table-sm mb-0"
                            id="tablaCodigos"
                            style="
                                font-size:13px;
                                color:black;
                                text-align:left;
                            "
                        >

                            <thead
                                style="
                                    position:sticky;
                                    top:0;
                                    background:#dccbfe;
                                    color:black;
                                    z-index:10;
                                "
                            >

                                <tr>

                                    <th style="width:45%">
                                        Cliente
                                    </th>

                                    <th style="width:35%">
                                        Código
                                    </th>

                                    <th
                                        style="
                                            width:20%;
                                            text-align:center;
                                        "
                                    >
                                        Opc
                                    </th>

                                </tr>

                            </thead>

                            <tbody>
                `;


                // ========================================================
                // RECORRER DATOS
                // ========================================================

                window.lstCodes.forEach(c => {

                    html += "<tr>";

                    html += "<td>";
                    html += c.client || "";
                    html += "</td>";

                    html += "<td>";
                    html += c.code || "";
                    html += "</td>";

                    // ====================================================
                    // BOTÓN OPC
                    // ====================================================

                    html += "<td style='text-align:center'>";

                    html +=
                            "<button " +
                            "type='button' " +
                            "class='btn btn-success btn-sm btnCodigo' " +
                            "data-id='" + c.id + "' " +
                            "title='Seleccionar código'>" +
                            "<i class='fas fa-check'></i>" +
                            "</button>";

                    html += "</td>";

                    html += "</tr>";

                });


                html += `

                            </tbody>

                        </table>

                    </div>

                `;


                // ========================================================
                // MOSTRAR SWAL
                // ========================================================

                swal({

                    title: "Seleccionar Código",

                    content: {

                        element: "div",

                        attributes: {

                            innerHTML: html

                        }

                    },

                    button: "Cerrar"

                });


                // ========================================================
                // CONFIGURAR MODAL
                // ========================================================

                setTimeout(function () {

                    const modal =
                            document.querySelector(".swal-modal");


                    if (modal) {

                        modal.style.width = "750px";
                        modal.style.maxWidth = "750px";
                        modal.style.borderRadius = "10px";

                    }


                    const content =
                            document.querySelector(".swal-content");


                    if (content) {

                        content.style.padding =
                                "10px 20px";

                    }


                    // ====================================================
                    // EVENTO DE LOS BOTONES
                    // ====================================================

                    const botones =
                            document.querySelectorAll(
                                    ".btnCodigo"
                                    );


                    botones.forEach(function (boton) {

                        boton.addEventListener(
                                "click",
                                function () {

                                    const id =
                                            this.getAttribute(
                                                    "data-id"
                                                    );


                                    console.log(
                                            "ID seleccionado:",
                                            id
                                            );


                                    seleccionarCodigo(id);

                                }
                        );

                    });

                }, 100);

            }

        </script>
        <script>

            // ============================================================
            // FILTRAR
            // ============================================================

            function filtrarCodigos() {

                const input =
                        document.getElementById(
                                "buscarCodigo"
                                );


                if (!input)
                    return;


                const filtro =
                        input.value.toUpperCase();


                const filas =
                        document.querySelectorAll(
                                "#tablaCodigos tbody tr"
                                );


                filas.forEach(function (fila) {

                    const texto =
                            fila.innerText.toUpperCase();


                    fila.style.display =
                            texto.indexOf(filtro) > -1
                            ? ""
                            : "none";

                });

            }

        </script>
        <script>

            // ============================================================
            // SELECCIONAR CLIENTE + CÓDIGO
            // ============================================================

            function seleccionarCodigo(id) {

                try {

                    console.log(
                            "ID recibido:",
                            id
                            );


                    // ====================================================
                    // BUSCAR REGISTRO
                    // ====================================================

                    const registro =
                            window.lstCodes.find(function (c) {

                                return String(c.id) === String(id);

                            });


                    if (!registro) {

                        console.error(
                                "No se encontró el registro:",
                                id
                                );

                        return;

                    }


                    console.log(
                            "Registro seleccionado:",
                            registro
                            );


                    // ====================================================
                    // ACTUALIZAR CLIENTE
                    // ====================================================

                    const elementoCliente =
                            document.getElementById(
                                    "clientValue"
                                    );


                    if (!elementoCliente) {

                        console.error(
                                "No se encontró el elemento #clientValue"
                                );

                        return;

                    }


                    elementoCliente.textContent =
                            registro.client || "";


                    elementoCliente.classList.add(
                            "editable"
                            );


                    elementoCliente.setAttribute(
                            "contenteditable",
                            "true"
                            );


                    elementoCliente.classList.remove(
                            "pending"
                            );


                    console.log(
                            "Cliente actualizado:",
                            registro.client
                            );


                    // ====================================================
                    // ACTUALIZAR CÓDIGO
                    // ====================================================

                    const elementoCodigo =
                            document.getElementById(
                                    "tCode"
                                    );


                    if (!elementoCodigo) {

                        console.error(
                                "No se encontró el elemento #tCode"
                                );

                        return;

                    }


                    elementoCodigo.textContent =
                            registro.code || "";


                    elementoCodigo.classList.add(
                            "editable"
                            );


                    elementoCodigo.setAttribute(
                            "contenteditable",
                            "true"
                            );


                    elementoCodigo.classList.remove(
                            "pending"
                            );


                    console.log(
                            "Código actualizado:",
                            registro.code
                            );


                    // ====================================================
                    // CERRAR MODAL
                    // ====================================================

                    swal.close();


                    // ====================================================
                    // MOSTRAR ALERTA
                    // ====================================================

                    iziToast.success({

                        title: "Correcto",

                        message:
                                "Cliente y código actualizados correctamente.",

                        position: "topRight"

                    });


                } catch (e) {

                    console.error(
                            "Error al seleccionar cliente y código:",
                            e
                            );

                }

            }

        </script> 
        <script src="Interface/Content/Assets/js/eventLogger.js"></script>
        <script src="Interface/Content/Assets/js/Print.js"></script>
        <script src="Interface/Content/Assets/js/html2canvas.min.js"></script>
        <script src="Interface/Content/Assets/js/jspdf.umd.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
    </body>
</html>
