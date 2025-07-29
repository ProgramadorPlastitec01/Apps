
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_role.tld" prefix="Role" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Role</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Role:Role/>
                </div>
            </div>
        </div>
        <Alert:Alert/>
        <script>
            function SwitchValue() {
                if (document.getElementById('State').checked === true) {
                    document.getElementById("State").value = 1;
                } else {
                    document.getElementById("State").value = 0;
                }
            }
        </script>
        <script type="text/javascript">
            function Masivo(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("Cbx_permission").value;
                if (content.includes(id)) {
                    document.getElementById("Cbx_permission").value = content.replace(id, "");
                } else {
                    document.getElementById("Cbx_permission").value += id;
                }
            }
        </script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
