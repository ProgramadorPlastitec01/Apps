<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<%@taglib uri="/WEB-INF/Tlds/Support.tld" prefix="Support"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Soporte | A-D&D</title>
        <link rel="shortcut icon" href="Interfaz/Contenido/Img/favicon.ico" type="image/x-icon">

        <!-- General CSS Files -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">

        <!-- CSS Libraries -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/jqvmap/dist/jqvmap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/owlcarousel2/dist/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/owlcarousel2/dist/assets/owl.theme.default.min.css">

        <!-- Template CSS -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
        <!-- Start GA -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-94034622-3"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/codemirror/lib/codemirror.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/codemirror/theme/duotone-dark.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/jquery-selectric/selectric.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/images/favicon.ico" type="image/x-icon" />
        <!--<link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>-->
        <!--THIS FILE-->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <!-------->

    </head>
    <body>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <div id="app">
                <div class="main-wrapper main-wrapper-1">
                <Menu:Menu/>
                <div class="main-content" style="min-height: 694px;">
                    <Support:Supporter/>
                </div>
            </div>
        </div>

        <script type = "text/javascript" >
            history.pushState(null, null, 'Soporte.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Soporte.jsp');
            });
        </script>

        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
        </script>

        <!--        <script>
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
                </script>-->

        <script>
            $(document).ready(function () {
                $("#enviar").click(function () {
                    var campo1 = $("#Importancia").val();
                    var campo2 = $("#Desc").val();

                    if (campo1 && campo2) {
                        timer();
                    } else {
                        swal("Error", "Por favor, completa todos los campos.", "error");
                    }
                });
            });

            function timer() {
                swal({
                    title: 'Favor espere, se está enviando un correo con el caso!',
                    text: '<i class="fas fa-spinner fa-spin" style="font-size: 50px;color: #00281b;"></i>',
                    icon: 'warning',
                    buttons: false,
                    showConfirmButton: false,
                    allowEscapeKey: false,
                    dangerMode: true,
                    html: true,
                });

            }
        </script>

        <Alertas:Alertas/>

        <!-- Template JS File -->
        <script src="Interfaz/Contenido/assets/modules/jquery.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/popper.js"></script>
        <script src="Interfaz/Contenido/assets/modules/tooltip.js"></script>
        <!--<link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">-->
        <script src="Interfaz/Contenido/assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/nicescroll/jquery.nicescroll.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/moment.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/stisla.js"></script>

        <!-- JS Libraies -->
        <script src="Interfaz/Contenido/assets/modules/jquery.sparkline.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/chart.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/owlcarousel2/dist/owl.carousel.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.js"></script>
        <!-- Page Specific JS File -->
        <script src="Interfaz/Contenido/assets/js/page/index.js"></script>
        <!-- Template JS File -->
        <script src="Interfaz/Contenido/assets/js/scripts.js"></script>
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>

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
