<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/generate" prefix="Generate"%>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <title>Generación</title>
        <script type="text/javascript">
            history.pushState(null, null, 'GenerateReport.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'GenerateReport.jsp');
            });
        </script>
    </head>
    <body class="sidebar-mini">
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
            <Generate:GenerateReport/>
            <Alert:Alert/>
        </div>
        <div id="loading-screen">
            <div class="doc-loader">
                <div class="doc-icon"><i class="fas fa-file-alt" style="font-size: 30px"></i></div>
                <div id="loading-text" class="dots">Buscando información</div>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", () => {

                const orderInput = document.getElementById("orderInput");
                const productoSection = document.getElementById("producto-section");
                const loteSection = document.getElementById("lote-section");
                const registroSection = document.getElementById("registro-section");

                const productosSelect = document.getElementById("resultadoProductos");
                const lotesSelect = document.getElementById("resultadoLotes");

                const loadingScreen = document.getElementById("loading-screen");

                /* ================== LOADER ================== */
                function mostrarCarga(texto) {
                    document.getElementById("loading-text").innerText = texto;
                    loadingScreen.style.display = "flex";
                }

                function ocultarCarga() {
                    loadingScreen.style.display = "none";
                }

                /* ============ PASO 1: ORDEN ============ */
                orderInput.addEventListener("blur", () => {
                    const order = orderInput.value.trim();
                    if (!order)
                        return;

                    mostrarCarga("Buscando productos...");
                    productosSelect.innerHTML = "";
                    lotesSelect.innerHTML = "";
                    loteSection.classList.add("d-none");
                    registroSection.classList.add("d-none");

                    fetch("SearchProductsServlet?orden=" + order)
                            .then(r => r.json())
                            .then(data => {
                                ocultarCarga();

                                productoSection.classList.remove("d-none");
                                productosSelect.innerHTML = "<option value=''>-- Seleccione un producto --</option>";

                                if (data.length === 0) {
                                    productosSelect.innerHTML = "<option>No hay productos</option>";
                                    return;
                                }

                                data.forEach(item => {
                                    const opt = document.createElement("option");
                                    opt.value = item.codigo;
                                    opt.text = item.codigo + " - " + item.producto;
                                    opt.dataset.lotes = item.lote;
                                    productosSelect.appendChild(opt);
                                });
                            })
                            .catch(err => {
                                ocultarCarga();
                                console.error(err);
                            });
                });

                /* ============ PASO 2: PRODUCTO ============ */
                productosSelect.addEventListener("change", () => {
                    const selected = productosSelect.options[productosSelect.selectedIndex];
                    if (!selected || !selected.dataset.lotes)
                        return;

                    mostrarCarga("Cargando lotes...");
                    lotesSelect.innerHTML = "";

                    setTimeout(() => {
                        ocultarCarga();
                        loteSection.classList.remove("d-none");

                        const lotes = selected.dataset.lotes.split(",");
                        lotesSelect.innerHTML = "<option value=''>-- Seleccione un lote --</option>";

                        lotes.forEach(l => {
                            const opt = document.createElement("option");
                            opt.value = l.trim();
                            opt.text = l.trim();
                            lotesSelect.appendChild(opt);
                        });
                    }, 800);
                });

                /* ============ PASO 3: LOTE ============ */
                lotesSelect.addEventListener("change", () => {
                    if (!lotesSelect.value)
                        return;

                    mostrarCarga("Cargando registros...");
                    setTimeout(() => {
                        ocultarCarga();
                        registroSection.classList.remove("d-none");
                        btnGenerar.disabled = false;
                    }, 800);
                });
            });
        </script>

        <script>
            function mostrarCarga(texto) {
                document.getElementById("loading-text").innerText = texto;
                loadingScreen.style.display = "flex";
            }

            function ocultarCarga() {
                loadingScreen.style.display = "none";
            }
        </script>

        <script>
            // Validación igual a tu versión funcional
            function FormGenerate(form) {

                const orderInput = document.getElementById("orderInput");
                const productosSelect = document.getElementById("resultadoProductos");
                const lotesSelect = document.getElementById("resultadoLotes");
                const registroSelect = document.getElementById("registroSelect");
                const btnGenerar = document.getElementById("btnGenerar");

                let valido = true;

                // ===== ORDEN =====
                if (orderInput.value.trim() === "") {
                    orderInput.classList.add("is-invalid");
                    valido = false;
                } else {
                    orderInput.classList.remove("is-invalid");
                }

                // ===== PRODUCTO =====
                if (productosSelect.value === "") {
                    productosSelect.classList.add("is-invalid");
                    valido = false;
                } else {
                    productosSelect.classList.remove("is-invalid");
                }

                // ===== LOTE =====
                if (lotesSelect.value === "") {
                    lotesSelect.classList.add("is-invalid");
                    valido = false;
                } else {
                    lotesSelect.classList.remove("is-invalid");
                }

                // ===== REGISTRO =====
                if (registroSelect.value === "") {
                    registroSelect.classList.add("is-invalid");
                    valido = false;
                } else {
                    registroSelect.classList.remove("is-invalid");
                }

                // ===== RESULTADO =====
                if (!valido) {
                    return false; // 🚫 NO ENVÍA
                }

                // Evitar doble submit
                btnGenerar.disabled = true;

                return true; // ✅ ENVÍA NORMAL
            }

            document.addEventListener("DOMContentLoaded", function () {
                const checkAll = document.getElementById("checkbox-all");
                const checkboxes = document.querySelectorAll('input[data-checkboxes="mygroup"]:not([data-checkbox-role="dad"])');

                // Evento para el checkbox general
                checkAll.addEventListener("change", function () {
                    checkboxes.forEach(chk => {
                        chk.checked = checkAll.checked; // marca/desmarca visualmente
                        Masive(chk.value);              // ejecuta la función en cada uno
                    });
                });
            });

        </script>
        <script>
            function confirmarEliminacion(url) {
                swal({
                    title: "¿Está seguro de eliminar?",
                    text: "Por favor, justifique la razón de la eliminación:",
                    content: {
                        element: "textarea",
                        attributes: {
                            placeholder: "Escriba aquí la justificación...",
                            id: "razonEliminacion"
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
                            text: "Sí, eliminar",
                            visible: true,
                            className: "btn btn-danger",
                            closeModal: false,
                        },
                    },
                    dangerMode: true,
                }).then((value) => {
                    if (value) {
                        const razon = document.getElementById("razonEliminacion").value.trim();
                        if (!razon) {
                            swal("Debe justificar la eliminación", {
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
        <script>
            function confirmarFinalizar(url) {

                swal({
                    title: "¿Está seguro de finalizar?",
                    text: "Una vez finalizado no podrá realizar cambios o modificaciones al documento.",
                    icon: "warning",
                    buttons: {
                        cancel: {
                            text: "Cancelar",
                            visible: true,
                            className: "btn btn-secondary",
                            closeModal: true
                        },
                        confirm: {
                            text: "Aceptar",
                            visible: true,
                            className: "btn btn-success",
                            closeModal: false
                        }
                    },
                    dangerMode: true

                }).then((value) => {

                    // Si cancela
                    if (!value)
                        return;

                    // Contenido de la alerta de carga
                    const loading = document.createElement("div");
                    loading.innerHTML = `
            <div class="loader"></div>
            <div class="loader-text">
                <strong>Finalizando documento...</strong><br>
                Estamos registrando la información y enviando las notificaciones correspondientes.<br><br>
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

                    // Pequeña espera para que se vea la animación
                    setTimeout(function () {
                        window.location.href = url;
                    }, 500);

                });
            }
        </script>

        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/js/filterop.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
