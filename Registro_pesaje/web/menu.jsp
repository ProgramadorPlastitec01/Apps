<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/menu.tld" prefix="Menu" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Tell the browser to be responsive to screen width -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <meta name="keywords"
              content="wrappixel, admin dashboard, html css dashboard, web dashboard, bootstrap 5 admin, bootstrap 5, css3 dashboard, bootstrap 5 dashboard, Ample lite admin bootstrap 5 dashboard, frontend, responsive bootstrap 5 admin template, Ample admin lite dashboard bootstrap 5 dashboard template">
        <meta name="description"
              content="Ample Admin Lite is powerful and clean admin dashboard template, inpired from Bootstrap Framework">
        <meta name="robots" content="noindex,nofollow">
        <title>Menu | Registro Pesaje</title>
<!--        <link rel="canonical" href="https://www.wrappixel.com/templates/ample-admin-lite/" />-->
        <!-- Favicon icon -->
        <link rel="icon" type="image/png" sizes="16x16" href="Interfaz/Contenido/Pluguins/images/favicon.png">
        <!-- Custom CSS -->
        <link href="Interfaz/Contenido/Pluguins/bower_components/chartist/dist/chartist.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Interfaz/Contenido/Pluguins/bower_components/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css">
        <!--Calendario-->

        <!-- Custom CSS -->
        <link href="Interfaz/Contenido/Css/style.min.css" rel="stylesheet">
        <link href="Interfaz/Contenido/Css/css_principal.css" rel="stylesheet">
        <link rel="stylesheet" href="Interfaz/Contenido/fontawesome/css/all.css">
        <!-- JQuery alertas -->
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <!--Validacion-->
        <script type="text/javascript" src="Interfaz/Contenido/Validacion/LiveValidation.js"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Validacion/StyleSheetLiveValidation.css">
    </head>
    <body>
        <Menu:Menu />
        <script>
//            function activate_a() {
//                var asi = document.getElementById("cont_aside");
//                if (asi.style.left == -243) {
//                    asi.style.left == 0;
//                } else {
//                    asi.style.left == -243;
//                }
//            }
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
        <script>
            function mostrar_opc() {
                if (document.getElementById("cont_user").style.display === "none") {
                    document.getElementById("cont_user").style.display = "block";
                } else if (document.getElementById("cont_user").style.display === "block") {
                    document.getElementById("cont_user").style.display = "none";
                }
            }

        </script>
        <script>
            function mostrar_opc2() {
                if (document.getElementById("opc_section").style.display === "none") {
                    document.getElementById("opc_section").style.display = "block";
                } else if (document.getElementById("opc_section").style.display === "block") {
                    document.getElementById("opc_section").style.display = "none";
                }
            }
        </script>
        <script>
            function mostrar_opc3() {
                if (document.getElementById("opc_complement").style.display === "none") {
                    document.getElementById("opc_complement").style.display = "block";
                } else if (document.getElementById("opc_complement").style.display === "block") {
                    document.getElementById("opc_complement").style.display = "none";
                }
            }
        </script>
        <script>
            function Esconder_menu() {
                if (document.getElementById("cont_total").className === "cont_total") {
                    document.getElementById("cont_total").className = "cont_total2";
                    document.getElementById("cont_aside").className = "left-sidebar2";
                    document.getElementById("icon_menu").className = "fas fa-chevron-right";
                } else if (document.getElementById("cont_total").className === "cont_total2") {
                    document.getElementById("cont_total").className = "cont_total";
                    document.getElementById("cont_aside").className = "left-sidebar";
                    document.getElementById("icon_menu").className = "fas fa-chevron-left";
                }
            }

            function Imprimir() {
                var objeto = document.getElementById('Div_export');  //obtenemos el objeto a imprimir
                var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
                ventana.document.write('<link href="Interfaz/Contenido/Css/style.min.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write('<link href="Interfaz/Contenido/Css/css_principal.css" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write(objeto.innerHTML); //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write('<link href="Interfaz/Contenido/Css/style.min.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write('<link href="Interfaz/Contenido/Css/css_principal.css" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.close();  //cerramos el documento
                ventana.print(); //imprimimos la ventana
                ventana.close();  //cerramos la ventana
            }

            function Imprimir() {
                var objeto = document.getElementById('Imprimir');  //obtenemos el objeto a imprimir
                var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
                ventana.document.write(objeto.innerHTML);  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write('<link href="Interfaz/Contenido/Css/style.min.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write('<link href="Interfaz/Contenido/Css/css_principal.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.close();  //cerramos el documento
                ventana.print(); //imprimimos la ventana
                ventana.close();  //cerramos la ventana
                //location.href='Materia_prima?opc=1&itk=0';
            }
        </script>

        <script src="Interfaz/Contenido/Pluguins/bower_components/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap tether Core JavaScript -->
        <script src="Interfaz/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/app-style-switcher.js"></script>
        <script src="Interfaz/Contenido/Pluguins/bower_components/jquery-sparkline/jquery.sparkline.min.js"></script>
        <!--Wave Effects -->
        <script src="Interfaz/Contenido/Scripts/waves.js"></script>
        <!--Menu sidebar -->
        <script src="Interfaz/Contenido/Scripts/sidebarmenu.js"></script>
        <!--Custom JavaScript -->
        <script src="Interfaz/Contenido/Scripts/custom.js"></script>
        <!--This page JavaScript -->
        <!--chartis chart-->
        <script src="Interfaz/Contenido/Pluguins/bower_components/chartist/dist/chartist.min.js"></script>
        <script src="Interfaz/Contenido/Pluguins/bower_components/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/pages/dashboards/dashboard1.js"></script>
        <script src="Interfaz/Contenido/Scripts/js_principal.js" ></script>
        <script src="Interfaz/Contenido/Scripts/filtro.js" ></script>
        <script src="Interfaz/Contenido/Scripts/Paging.js" ></script>

        <script src="Interfaz/Contenido/Scripts/perfil.js" ></script>

    </body>
</html>
