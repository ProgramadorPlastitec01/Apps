<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/generate" prefix="Generate"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
            <Generate:GenerateReport/>
        </div>
        <div id="loading-screen">
            <div class="doc-loader">
                <div class="doc-icon"><i class="fas fa-file-alt" style="font-size: 30px"></i></div>
                <div id="loading-text" class="dots">Buscando información</div>
            </div>
        </div>
    </body>


    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const orderInput = document.getElementById("orderInput");
            const productoSection = document.getElementById("producto-section");
            const loteSection = document.getElementById("lote-section");
            const registroSection = document.getElementById("registro-section");
            const loadingScreen = document.getElementById("loading-screen");
            const productosSelect = document.getElementById("resultadoProductos");
            const lotesSelect = document.getElementById("resultadoLotes");

            // Paso 1: buscar orden
            orderInput.addEventListener("change", function () {
                const orderValue = orderInput.value.trim();
                if (orderValue === "")
                    return;

                mostrarCarga("Buscando orden");

                setTimeout(() => {
                    ocultarCarga();
                    productoSection.classList.remove("d-none");
                    productoSection.classList.add("fade-in");
                    productosSelect.disabled = false;
                }, 1500);
            });

            // Paso 2: buscar producto
            productosSelect.addEventListener("change", function () {
                const productoValue = productosSelect.value.trim();
                if (productoValue === "")
                    return;

                mostrarCarga("Consultando productos");

                setTimeout(() => {
                    ocultarCarga();
                    loteSection.classList.remove("d-none");
                    loteSection.classList.add("fade-in");
                    lotesSelect.disabled = false;
                }, 1500);
            });

            // Paso 3: buscar lote
            lotesSelect.addEventListener("change", function () {
                const loteValue = lotesSelect.value.trim();
                if (loteValue === "")
                    return;

                mostrarCarga("Cargando registros");

                setTimeout(() => {
                    ocultarCarga();
                    registroSection.classList.remove("d-none");
                    registroSection.classList.add("fade-in");
                }, 1500);
            });

            function mostrarCarga(texto) {
                document.getElementById("loading-text").innerText = texto;
                loadingScreen.style.display = "flex";
            }

            function ocultarCarga() {
                loadingScreen.style.display = "none";
            }
        });

        // Validación igual a tu versión funcional
        function FormGenerate(form) {
            const order = document.getElementById("orderInput").value.trim();
            const producto = document.getElementById("resultadoProductos").value.trim();
            const lote = document.getElementById("resultadoLotes").value.trim();

            let valido = true;

            if (order === "") {
                document.getElementById("orderInput").classList.add("is-invalid");
                valido = false;
            } else {
                document.getElementById("orderInput").classList.remove("is-invalid");
            }

            if (producto === "") {
                document.getElementById("resultadoProductos").classList.add("is-invalid");
                valido = false;
            } else {
                document.getElementById("resultadoProductos").classList.remove("is-invalid");
            }

            if (lote === "") {
                document.getElementById("resultadoLotes").classList.add("is-invalid");
                valido = false;
            } else {
                document.getElementById("resultadoLotes").classList.remove("is-invalid");
            }

            if (valido) {
                form.submit();
            }

            return false;
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

        function Masive(ide) {
            var id = "[" + ide + "]";
            var input = document.getElementById("IdCerti");
            var content = input.value;

            if (content.includes(id)) {
                input.value = content.replace(id, "");
            } else {
                input.value += id;
            }
        }


        function ExecuteForm() {
            const form = document.getElementById("myForm");

            // Validar antes de enviar
            if (form.checkValidity()) {
                form.submit(); // Enviar formulario
            } else {
                form.reportValidity(); // Mostrar errores de campos requeridos
            }
        }
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


    <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
    <script src="Interface/Content/Assets/js/filterop.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
    <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
    <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
</html>
