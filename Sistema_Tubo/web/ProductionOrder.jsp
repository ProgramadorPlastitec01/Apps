<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Production_order.tld" prefix="ProdOrder" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orden Producción | ST</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
        <!--THIS FILE-->

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >

        <!-------->

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <ProdOrder:Production_order/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>
        <script>
            function SwitchValue() {
                if (document.getElementById("Nmb_est").checked == true) {
                    document.getElementById("Nmb_est").value = 1;
                } else {
                    document.getElementById("Nmb_est").value = 0;
                }
            }
        </script>
        <script>
            function mouseOver(id) {
                document.getElementById("Text" + id).style.display = "block";
            }
            function mouseOut(id) {
                document.getElementById("Text" + id).style.display = "none";
            }
        </script>
        <script>
            function changeForm(num) {
                if (num === 1) {
                    document.getElementById("cont_form_int").style.display = "none";
                    document.getElementById("Txt_orden1").type = "text";
                    document.getElementById("Txt_orden").type = "hidden";
                    document.getElementById("Txt_orden").classList.remove("divdisab");
                    document.getElementById("teemp").value = "1";
                } else if (num === 2) {
                    document.getElementById("cont_form_int").style.display = "block";
                    document.getElementById("Txt_orden1").type = "hidden";
                    document.getElementById("Txt_orden").type = "text";
                    document.getElementById("Txt_orden").classList.add("divdisab");
                    document.getElementById("teemp").value = "2";
                }
            }
            function makeOrder() {
                var formu = document.getElementById("formul").value;
                var datep = document.getElementById("datepick").value;
                datep = datep.replace("-", "").replace("-", "").substring(2);
                var result = formu + "-" + datep;
                document.getElementById("Txt_orden").value = "";
                document.getElementById("Txt_orden").value = result;
                document.getElementById("test").value = result;
//                var Maq = document.getElementById("Cbx_line").value;
//                var formu = document.getElementById("formul").value;
//                var datep = document.getElementById("datepick").value;
//                datep = datep.replace("-", "").replace("-", "").substring(2);
//                var result = Maq + "-" + formu + "-" + datep;
//                document.getElementById("Txt_orden").value = "";
//                document.getElementById("Txt_orden").value = result;
//                document.getElementById("test").value = result;
//                alert(result);
            }

        </script>
<!--        <script>
            function activeFrec(){
                document.getElementById("div_frec").style.display = 'block';
            }
        </script>-->
        <!-- Tables -->
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>

        <!--THIS FILE-->
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <!-------->
    </body>
</html>
