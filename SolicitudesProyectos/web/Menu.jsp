
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/menu.tld" prefix="menu" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitudes Proyectos | Menu</title>
        <!-- General CSS Files -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />

        <!-- CSS Libraries -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/jqvmap/dist/jqvmap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">

        <!-- Template CSS -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>

        <!-- Select2 -->
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag() {
                dataLayer.push(arguments);
            }
            gtag('js', new Date());

            gtag('config', 'UA-94034622-3');
        </script>
        <style>
            /* ================================================================
               Sidebar responsivo para portatiles y proyectores.
               Va aqui y no en style.css: style.css es la hoja compartida de
               toda la plantilla Stisla y estas reglas son propias del menu.
               ================================================================ */

            /* Los submenus abren EN FLUJO (static), empujando el resto hacia
               abajo, no flotando encima. El acordeon lo maneja scripts.js con
               slideToggle sobre .dropdown-menu; el data-toggle de Bootstrap
               (que los posicionaba absolutos) se quito del marcado. */
            .main-sidebar .sidebar-menu li ul.dropdown-menu {
                position: static !important;
                float: none !important;
                display: none;
                width: 100% !important;
                box-shadow: none;
                border: 0;
            }

            /* Columna flexible de alto completo: el area del menu scrollea sola
               y el logo queda anclado al pie. Sin esto, en pantallas de 768px
               de alto (o al 125% de escala) los ultimos items se cortaban. */
            .main-sidebar.sidebar-style-2 {
                height: 100vh !important;
                display: flex;
                flex-direction: column;
            }
            .main-sidebar.sidebar-style-2 .sidebar-scroll {
                flex: 1 1 auto;
                min-height: 0;   /* imprescindible: habilita el scroll del hijo flex */
                overflow-y: auto;
                overflow-x: hidden;
            }
            .main-sidebar.sidebar-style-2 .sidebar-footer {
                flex-shrink: 0;
            }
        </style>
    </head>
    <body>
        <menu:Menu/>
        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
            function mostrarModalAP(id) {
                if (document.getElementById("ModalA" + id).style.display === "none") {
                    document.getElementById("ModalA" + id).style.display = "block";
                } else if (document.getElementById("ModalA" + id).style.display === "block") {
                    document.getElementById("ModalA" + id).style.display = "none";
                }
            }
            function mostrarModalDV(id) {
                if (document.getElementById("ModalD" + id).style.display === "none") {
                    document.getElementById("ModalD" + id).style.display = "block";
                } else if (document.getElementById("ModalD" + id).style.display === "block") {
                    document.getElementById("ModalD" + id).style.display = "none";
                }
            }
        </script>
        <!-- Template JS File -->
        <script src="Interfaz/Contenido/assets/modules/jquery.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/popper.js"></script>
        <script src="Interfaz/Contenido/assets/modules/tooltip.js"></script>
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
