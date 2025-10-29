<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_support.tld" prefix="Sppot"%>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/chocolat/dist/css/chocolat.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
    </head>
    <body>
        <div class="main-wrapper main-wrapper-1">
            <jsp:include page="content.jsp"></jsp:include>
                <div class="main-content" style="min-height: 200px;">
                <Sppot:Support/>
            </div>
        </div>
        <Alerts:AlertData/>
        <script>
            // Selección de filas
            const tableRows = document.querySelectorAll('#data-table tbody tr');
            const inputID = document.getElementById('selected-id'); // Input donde se mostrará el ID

            tableRows.forEach(row => {
                row.addEventListener('click', () => {
                    // Eliminar clase 'selected' de todas las filas
                    tableRows.forEach(r => r.classList.remove('selected'));
                    // Añadir clase 'selected' a la fila actual
                    row.classList.add('selected');
                    // Obtener el ID único de la fila desde el atributo 'data-id'
                    const selectedId = row.getAttribute('data-id');
                    // Mostrar el ID en el input
                    inputID.value = selectedId;
                });
            });
        </script>

        <script>
            function LoadData() {
                try {
                    document.getElementById("consultEJe").value = "";
                } catch (e) {
                }
                document.getElementById("formReload").submit();
            }

            function sendData() {
                var data = document.getElementById("selected-id").value;
                if (data === "") {
                    $("#toastr-2").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'No se ha seleccionado ningun item de la lista.',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    $("#swal-6").ready(function () {
                        swal({
                            title: "¿Está seguro?",
                            text: "Favor confirmar antes de ejecutar el caso.",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#3085d6",
                            cancelButtonColor: "#d33",
                            confirmButtonText: "Sí, confirmar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        }, function (isConfirm) {
                            if (isConfirm) {
                                document.getElementById("formReload").submit();
                            }
                        });
                    });
                }
            }
        </script>

        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/stisla.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="Interfaz/Contenido/assets/modules/chocolat/dist/js/jquery.chocolat.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-sweetalert.js"></script>
        <!--<script src="Interfaz/Contenido/assets/js/scripts.js"></script>-->
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>
    </body>
</html>
