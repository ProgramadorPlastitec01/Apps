<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_Role.tld" prefix="Role" %>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="tld_alert" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rol | SGLT</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png" />
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Base.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Role:Role/>
                </div>
            </div>
        </div>
        <script>
            function SwitchValue() {
                if (document.getElementById('State').checked == true) {
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
        <tld_alert:AlertModule/>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
