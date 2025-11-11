<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_BaseClient.tld" prefix="BaseClint" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    </head>
    <body>
            <BaseClint:BaseClient/>
            <script type="text/javascript" language="javascript">
                function mostrarConvencion(id) {
                    if (document.getElementById("Ventana" + id).style.display === "none") {
                        document.getElementById("Ventana" + id).style.display = "block";
                    } else if (document.getElementById("Ventana" + id).style.display === "block") {
                        document.getElementById("Ventana" + id).style.display = "none";
                    }
                }
            </script>
            <script type="text/javascript" language="javascript">
                function MostrarWindows(id) {
                    if (document.getElementById("Windows" + id).style.display === "none") {
                        document.getElementById("Windows" + id).style.display = "block";
                    } else if (document.getElementById("Windows" + id).style.display === "block") {
                        document.getElementById("Windows" + id).style.display = "none";
                    }
                }
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
    </body>
</html>
