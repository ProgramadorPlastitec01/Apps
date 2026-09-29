<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/signaturereport" prefix="Signature" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Icon.fw.png">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <title>Reporte</title>
        <style>
            /* Selección de filas para la firma masiva. */
            #table-2 tbody tr.fila-seleccionable {
                cursor: pointer;
            }
            #table-2 tbody tr.fila-seleccionable:hover > td {
                background-color: #fdf2d6;
            }
            #table-2 tbody tr.fila-seleccionada > td {
                background-color: #fdf2d6 !important;
                color: #14532d;
            }
            #table-2 tbody tr.fila-seleccionada > td:first-child {
                box-shadow: inset 4px 0 0 0 #fdc12c;
            }
        </style>
        <script type="text/javascript">
            history.pushState(null, null, 'Reporte.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Reporte.jsp');
            });
        </script>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
            <Signature:SignatureReport/>
            <Alert:Alert/>
        </div>
        <script>
            /* ============ SELECCIÓN MASIVA PARA FIRMAR ============
             * El usuario hace clic sobre las filas finalizadas (se resaltan en
             * verde pastel) y el botón superior las firma todas de una vez.
             */
            (function () {

                var Seleccionados = [];

                function refrescarSeleccion() {

                    var input = document.getElementById("IdCerti");
                    var contador = document.getElementById("ContadorFirmar");

                    if (input) {
                        input.value = Seleccionados.map(function (id) {
                            return "[" + id + "]";
                        }).join("");
                    }

                    if (contador) {
                        contador.textContent = Seleccionados.length
                                + (Seleccionados.length === 1 ? " seleccionado" : " seleccionados");
                        if (Seleccionados.length === 0) {
                            contador.classList.add("d-none");
                        } else {
                            contador.classList.remove("d-none");
                        }
                    }
                }

                $(document).on("click", "#table-2 tbody tr.fila-seleccionable", function (e) {

                    // Los controles de la fila (Ver, etc.) no seleccionan.
                    if ($(e.target).closest("button, a, input, label, .dropdown").length) {
                        return;
                    }

                    var id = String($(this).data("idcerti"));
                    var pos = Seleccionados.indexOf(id);

                    if (pos >= 0) {
                        Seleccionados.splice(pos, 1);
                        $(this).removeClass("fila-seleccionada");
                    } else {
                        Seleccionados.push(id);
                        $(this).addClass("fila-seleccionada");
                    }

                    refrescarSeleccion();
                });

                window.ExecuteForm = function () {

                    if (Seleccionados.length === 0) {
                        iziToast.warning({
                            title: 'Validación requerida',
                            message: 'Seleccione al menos un certificado finalizado haciendo clic sobre su fila.',
                            position: 'bottomRight',
                            timeout: 5000
                        });
                        return false;
                    }

                    var total = Seleccionados.length;

                    swal({
                        title: "¿Está seguro de firmar?",
                        text: "Se firmará" + (total === 1 ? "" : "n") + " " + total + " certificado"
                                + (total === 1 ? "" : "s") + ". Una vez firmados no podrá realizar cambios o modificaciones.",
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

                    }).then(function (value) {

                        if (!value)
                            return;

                        var loading = document.createElement("div");
                        loading.innerHTML = `
            <div class="loader"></div>
            <div class="loader-text">
                <strong>Firmando documentos...</strong><br>
                Estamos registrando la firma en los certificados seleccionados.<br><br>
                <small>Este proceso puede tardar algunos segundos.<br>Por favor, no cierre esta ventana.</small>
            </div>
        `;

                        swal({
                            title: "Espere un momento",
                            content: loading,
                            buttons: false,
                            closeOnClickOutside: false,
                            closeOnEsc: false
                        });

                        setTimeout(function () {
                            document.getElementById("myForm").submit();
                        }, 500);
                    });

                    return false;
                };
            })();
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
