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
                const idCerti = document.getElementById("IdCerti");

                // Validar que haya al menos un id seleccionado
                if (!idCerti || idCerti.value.trim() === "" || idCerti.value.trim() === "[]") {
                    iziToast.warning({
                        title: 'Validación requerida',
                        message: 'Debes seleccionar al menos un certificado para firmar.',
                        position: 'bottomRight',
                        timeout: 5000
                    });
                    return false; // Detiene el envío
                }

                // Validar el formulario HTML5 normalmente
                if (form.checkValidity()) {
                    form.submit();
                } else {
                    form.reportValidity();
                }
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
