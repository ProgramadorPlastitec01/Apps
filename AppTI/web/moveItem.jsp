<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_moveItem.tld" prefix="MoveItems" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
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
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
        <link rel="stylesheet" href="assets/modules/bootstrap/css/bootstrap.min.css">
        <script src="Interface/Content/Assets/js/sweetalert2.js"></script>
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <style>
            .swal2-html-container {
                overflow: hidden !important;
            }
        </style>
    </head>

    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <MoveItems:MoveItem/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>

        <script>
            function SearchItems() {
                var refren = document.getElementById("idRef").value;
                if (refren != "") {
                    Swal.fire({
                        title: 'Buscando Movimientos...',
                        html: '<i class="fas fa-spinner fa-spin" style="font-size: 50px; color: #00281b;"></i>',
                        icon: 'info',
                        showConfirmButton: false,
                        allowEscapeKey: false,
                        allowOutsideClick: false,
                    });
                    document.getElementById("FormMove").submit();
                } else {
                    $("#toastr-2").ready(function () {
                        iziToast.warning({
                            title: 'Atención!',
                            message: 'Sebe diligenciar la referencia!.',
                            position: 'topRight'
                        });
                    });
                }
            }
        </script>

        <script>
            document.getElementById("startDate").addEventListener("change", function () {
                let startDate = new Date(this.value);
                if (!isNaN(startDate.getTime())) { // Verifica que la fecha es válida

                    let endDateInput = document.getElementById("endDate");
                    endDateInput.min = this.value;
                    let maxDate = new Date(startDate);
                    maxDate.setDate(maxDate.getDate() + 7);
                    let maxDateFormatted = maxDate.toISOString().split("T")[0];
                    endDateInput.max = maxDateFormatted;
                }
            });
        </script>

        <script>
            const tableRows = document.querySelectorAll('.tableRef tbody tr');
            const inputId = document.getElementById('FieldMove');

            tableRows.forEach(row => {
                row.addEventListener('click', () => {
                    row.classList.toggle("selectedx");
                    updateSelectedIds();
                });
            });

            function updateSelectedIds() {
                const selectedRows = document.querySelectorAll('.tableRef tbody tr.selectedx');
                const selectedIds = Array.from(selectedRows).map(row => row.getAttribute('data-id'));
                inputId.value = selectedIds.join(', ');
            }

        </script>

        <script>
            function formToMove() {
                const input = FieldMove.value;
                if (input === "") {
                    $("#toastr-2").ready(function () {
                        iziToast.warning({
                            title: 'Atención!',
                            message: 'Se debe seleccionar al menos un item!.',
                            position: 'topRight'
                        });
                    });
                } else {
                    document.getElementById("formDataMove").submit();
                }
            }
        </script>


        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>

        <script src="Interface/Content/Assets/js/page/modules-sweetalert.js"></script>
    </body>
</html>
