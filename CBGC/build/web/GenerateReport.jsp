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
    </body>

    <script>
        function FormGenerate(form) {
            const order = document.getElementById("orderInput").value.trim();
            const producto = document.getElementById("resultadoProductos").value.trim();
            const lote = document.getElementById("resultadoLotes").value.trim();

            let valido = true;

            // Validación de Orden
            if (order === "") {
                document.getElementById("orderInput").classList.add("is-invalid");
                valido = false;
            } else {
                document.getElementById("orderInput").classList.remove("is-invalid");
            }

            // Validación de Producto
            if (producto === "") {
                document.getElementById("resultadoProductos").classList.add("is-invalid");
                valido = false;
            } else {
                document.getElementById("resultadoProductos").classList.remove("is-invalid");
            }

            // Validación de Lote
            if (lote === "") {
                document.getElementById("resultadoLotes").classList.add("is-invalid");
                valido = false;
            } else {
                document.getElementById("resultadoLotes").classList.remove("is-invalid");
            }

            // Si todo está correcto, enviamos el formulario
            if (valido) {
                form.submit();
            }

            return false; // siempre detener el submit por defecto
        }
    </script>


    <script src="Interface/Content/Assets/js/filterop.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
    <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
    <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
</html>
