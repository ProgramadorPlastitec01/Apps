<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/item_verificacion.tld" prefix="ItemVerificacion" %>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alertas" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SP | Item Verificacion</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                <div class="main-content" style="min-height: 694px;">
                <ItemVerificacion:Item_verificacion/>
                </div>
            </div>
            <Alertas:Alertas/>
            <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
            <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

            <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
            <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        </div>
    </body>
</html>
