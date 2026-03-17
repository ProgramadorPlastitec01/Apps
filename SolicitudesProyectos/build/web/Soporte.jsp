<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/soporte.tld" prefix="Soporte" %>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SP | Soporte</title>
        
        
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/codemirror/lib/codemirror.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/codemirror/theme/duotone-dark.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/jquery-selectric/selectric.css">
        <!--THIS FILE-->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <!-------->

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Soporte:Soporte/>
                </div>
            </div>
        </div>
        <script>
            function timer() {
                $("#swal-5").ready(function () {
                    swal({
                        title: 'Favor espere, se esta enviando un correo con el caso!',
                        text: '<i class="fas fa-spinner fa-spin" style="font-size: 50px;color: #00281b;"></i>',
                        icon: 'warning',
                        buttons: false,
                        showConfirmButton: false,
                        allowEscapeKey: false,
                        dangerMode: true,
                        html: true,
                    });
                });
            }
        </script>        

        <Alertas:Alertas/>

        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-sweetalert.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>

        <!--THIS FILE-->
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.js"></script>
        <script src="Interfaz/Contenido/assets/modules/codemirror/lib/codemirror.js"></script>
        <script src="Interfaz/Contenido/assets/modules/codemirror/mode/javascript/javascript.js"></script>
        <script src="Interfaz/Contenido/assets/modules/jquery-selectric/jquery.selectric.min.js"></script>
        <!-------->
    </body>
</html>
