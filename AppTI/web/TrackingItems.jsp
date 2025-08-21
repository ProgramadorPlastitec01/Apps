<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_trackingItem.tld" prefix="ItemTracking" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seguimiento a items</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <script src="Interface/Content/Assets/js/sweetalert2.js"></script>
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <style>
            button:focus {
                outline: none;
            }
            .btn:focus, .btn:active {
                box-shadow: none;
            }
            .btn:hover{
                background-color: #00000000 !important;
            }
        </style>
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
                    <ItemTracking:TrackingItem/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>

        <script>
            function SelectedSigna(id, dat) {
                const dt = "[" + dat + "]";
                let btn = document.getElementById("btnSelec" + id);
                let inputId = document.getElementById('idItmeToSig');
                var content = inputId.value;
                if (btn.classList.contains("btn-green")) {
                    btn.classList.replace("btn-green", "btn-yellow");
                } else {
                    btn.classList.replace("btn-yellow", "btn-green");
                }
                if (content.includes(dt)) {
                    inputId.value = content.replace(dt, "");
                } else {
                    inputId.value += dt;
                }
                if (inputId.value == "") {
                    document.getElementById("btnUserSign").style.display = "none";
                }else{
                    document.getElementById("btnUserSign").style.display = "block";
                }
            }
            setTimeout(() => {
                btn.classList.remove("btn-green", "btn-yellow");
                btn.offsetHeight; // Forzar reflow
                btn.classList.add(btn.classList.contains("btn-green") ? "btn-yellow" : "btn-green");
                btn.style.backgroundColor = "";
            }, 10);
            
            
        </script>

        <script>
            function validformSearch() {
                var numItem = document.getElementById("id_numItem").value;
                var ref = document.getElementById("id_ref").value;
                var dateMove = document.getElementById("id_dateMove").value;
                var numMov = document.getElementById("id_numMov").value;
                var keyword = document.getElementById("id_keyword").value;
                
                if (numItem == "" && ref == "" && dateMove == "" && numMov == "" && keyword == "") {
                    $("#toastr-2").ready(function () {
                        iziToast.warning({
                            title: 'Atención!',
                            message: 'Se debe ingresar al menos un dato para buscar!.',
                            position: 'topRight'
                        });
                    });
                } else {
                    Swal.fire({
                        title: 'Buscando Movimientos...',
                        html: '<i class="fas fa-spinner fa-spin" style="font-size: 50px; color: #00281b;"></i>',
                        icon: 'info',
                        showConfirmButton: false,
                        allowEscapeKey: false,
                        allowOutsideClick: false,
                    });
                    document.getElementById("FormMoveSearch").submit();
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
    </body>
</html>
