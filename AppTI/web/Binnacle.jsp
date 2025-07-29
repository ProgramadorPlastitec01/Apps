<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_binnacle.tld" prefix="Binacles" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bitacora</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/summernote/summernote-bs4.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Binacles:Binnacle/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>   
        <script>
            function MoveData(ide) {
                var id = "[" + ide + "]";
                var input = document.getElementById("txtIdBins");
                var content = input.value;
                if (content.includes(id)) {
                    input.value = content.replace(id, "");
                } else {
                    input.value += id;
                }
            }

            function toggleAll() {
                var content = document.getElementById("idRecolect").value;
                document.getElementById("txtIdBins").value = content;
            }
        </script>

        <script>
            function updateTimeFields() {
                var select = document.getElementById('shiftSelect');
                var selectedIndex = select.selectedIndex;
                if (selectedIndex !== -1) {
                    var option = select.options[selectedIndex];
                    var startTime = option.getAttribute('data-start');
                    var endTime = option.getAttribute('data-end');
                    document.getElementById('startTime').value = startTime;
                    document.getElementById('endTime').value = endTime;
                }
            }
        </script>
        <script>
            function SendIds() {
                var data = document.getElementById("txtIdBins").value;
                if (data === "") {
                    $("#toastr-4").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar al menos a un usuario!',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    document.getElementById("SendIdRecole").submit();
                }
            }
        </script>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>

        <script src="Interface/Content/Assets/modules/summernote/summernote-bs4.js"></script>
    </body>
</html>