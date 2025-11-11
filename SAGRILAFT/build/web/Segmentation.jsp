<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_Segmentation.tld" prefix="Segmentation" %>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="tld_alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Segmentación | SGLT</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Segmentation.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Segmentation.jsp');
            });
        </script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/datepickerJQ.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png" />
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
    </head>
    <body >
        <!--<body class="sidebar-mini">-->
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Base.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Segmentation:Segmentation/>
                </div>
            </div>
            <tld_alert:AlertModule/>
        </div>
        <script type="text/javascript" language="javascript">
            function ViewConvention(id) {
                if (document.getElementById("View" + id).style.display === "none") {
                    document.getElementById("View" + id).style.display = "block";
                } else if (document.getElementById("View" + id).style.display === "block") {
                    document.getElementById("View" + id).style.display = "none";
                }
            }
            function ValidateAttachment(cdc) {
                if (cdc === 1) {
                    document.getElementById("Div1").style.display = "block";
                    document.getElementById("Div0").style.display = "none";
                    document.getElementById("BtnVal1").classList.remove("btn-outline-warning");
                    document.getElementById("BtnVal1").classList.add("btn-warning");
                    document.getElementById("BtnVal0").classList.remove("btn-warning");
                    document.getElementById("BtnVal0").classList.add("btn-outline-warning");
                } else {
                    document.getElementById("Div0").style.display = "block";
                    document.getElementById("Div1").style.display = "none";
                    document.getElementById("BtnVal0").classList.remove("btn-outline-warning");
                    document.getElementById("BtnVal0").classList.add("btn-warning");
                    document.getElementById("BtnVal1").classList.remove("btn-warning");
                    document.getElementById("BtnVal1").classList.add("btn-outline-warning");
                }
            }
        </script>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/Scripts/jquery-3.5.1.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/jquery-ui.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/datepicker-es.js"></script>
        <script>
            var jQuery = $.noConflict();
            jQuery(function () {
                jQuery("#InitialDate").datepicker({
                    onSelect: function (selectedDate) {
                        var fechaInicio = jQuery(this).datepicker('getDate');
                        fechaInicio.setDate(fechaInicio.getDate()); // Sumar un día
                        jQuery("#EndDate").datepicker('option', 'minDate', fechaInicio); // Bloquear fechas anteriores
                        jQuery("#EndDate").datepicker('setDate', fechaInicio); // Establecer fecha mínima y predeterminada
                    }
                });

                jQuery("#EndDate").datepicker({
                    minDate: 1, // Bloquear fechas anteriores a mañana
                });

                // Configurar el idioma del calendario a español
                jQuery.datepicker.setDefaults(jQuery.datepicker.regional['es']);
            });
        </script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
    </body>
</html>
