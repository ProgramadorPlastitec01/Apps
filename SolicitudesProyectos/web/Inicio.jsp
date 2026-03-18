<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/inicio.tld" prefix="Inicio" %>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SP | Solicitud</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
    </head>
    <body>
        <div id="app">
            <jsp:include page="Library.jsp"></jsp:include>
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Inicio:Inicio/>
                </div>
            </div>
        </div>
        <Alertas:Alertas/>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
    </body>
</html>
