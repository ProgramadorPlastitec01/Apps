<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PVM | Menu</title>
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
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag() {
            dataLayer.push(arguments);
            }
            gtag('js', new Date());
            gtag('config', 'UA-94034622-3');
        </script>
    </head>
    <body>
        <Menu:Menu/>
        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
            if (document.getElementById("Ventana" + id).style.display === "none") {
            document.getElementById("Ventana" + id).style.display = "block";
            } else if (document.getElementById("Ventana" + id).style.display === "block") {
            document.getElementById("Ventana" + id).style.display = "none";
            }
            }
        </script>
        <script type="text/javascript">
                            var tableToExcel = (function() {
                            var uri = 'data:application/vnd.ms-excel;base64,'
                                    //, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
                                    ,
                                    template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
            base64 = function(s) {
                return window.btoa(unescape(encodeURIComponent(s)))
            },
            format = function(s, c) {
                return s.replace(/{(\w+)}/g, function(m, p) {
                    return c[p];
                })
            }
            return function(table, name) {
                if (!table.nodeType) table = document.getElementById(table)
                var ctx = {
                    worksheet: name || 'Worksheet',
                    table: table.innerHTML
                }
                window.location.href = uri + base64(format(template, ctx))
            }
        })()
        </script>
        <script type="text/javascript" language="javascript">
                                    function Imprimir() {
                                    var contenedor = document.getElementById("Imprimir").innerHTML;
                                    var frame = document.createElement("iframe");
                                    frame.name = "frame1";
                                    frame.style.position = "absolute";
                                    frame.style.top = "-1000000px";
                                    document.body.appendChild(frame);
                                    var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
                                    frameDoc.document.open();
                                    frameDoc.document.write('<html><head><title>Imprimir</title>');
                                    frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />');
                                    frameDoc.document.write('</head><body>');
                                    frameDoc.document.write(contenedor);
                                    frameDoc.document.write('</body></html>');
                                    frameDoc.document.close();
                                    setTimeout(function () {
                                    window.frames["frame1"].focus();
                                    window.frames["frame1"].print();
                                    document.body.removeChild(frame);
                                    }, 300);
                                    return false;
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
        <!--<link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">-->

        <!-- JS Libraies -->
        <script src="Interfaz/Contenido/assets/modules/jquery.sparkline.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/chart.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/owlcarousel2/dist/owl.carousel.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.js"></script>
        <script src="Interfaz/Contenido/assets/modules/chocolat/dist/js/jquery.chocolat.min.js"></script>
        <!-- Page Specific JS File -->
        <script src="Interfaz/Contenido/assets/js/page/index.js"></script>
        <!-- Template JS File -->
        <script src="Interfaz/Contenido/assets/js/scripts.js"></script>
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>
    </body>
</html>
