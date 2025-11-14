<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/tlds/Personal.tld" prefix="Personal" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Personal.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Personal.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script src="Interfaz/MasterPage/loader.js" type="text/javascript"></script>
            <script type="text/javascript">
            $(window).load(function () {
                $(".loader").fadeOut("slow");
            });
            </script>
            <script type = "text/javascript" >
                function SeleccionarEspecialidadPersonal(datos) {
                    if (datos.checked) {
                        document.getElementById('Txt_especialidad').value += "" + datos.value;
                    } else {
                        document.getElementById("Txt_especialidad").value = document.getElementById("Txt_especialidad").value.replace(datos.value, "");
                    }
                }
                function CompletarCampo(campo) {
                    var dato = document.getElementById(campo).value;
                    if (dato.length === 0) {
                        document.getElementById(campo).value = "Ninguno";
                    }
                }
                function RangoAnios() {
                    var anio1 = document.getElementById("Txt_anio_ini").value;
                    var anio2 = document.getElementById("Txt_anio_fin").value;
                    var max = document.getElementById("Txt_anio_min").value;
                    var min = document.getElementById("Txt_anio_max").value;
                    if ((anio1 >= min && anio1 <= max) && (anio2 >= min && anio2 <= max)) {
                        document.getElementById("Btn_consultar_anios").style.visibility = 'visible';
                    } else {
                        if (anio1 <= anio2) {
                            document.getElementById("Btn_consultar_anios").style.visibility = 'visible';
                        } else {
                            document.getElementById("Btn_consultar_anios").style.visibility = 'hidden';
                            
                        }
                    }
                    
                }
                function CambiarValor(i) {
                    var Gato = document.getElementById("Cmb" + i).value;
                    if (Gato == "SI") {
                        document.getElementById("Cmb" + i).value = "NO";
                    } else {
                        document.getElementById("Cmb" + i).value = "SI";
                        
                    }
                    
                }
            </script>
            <script>
                function PassDoc() {
                    var doc = document.getElementById("Txt_documento").value;
                    document.getElementById("id_txtDoc").value = doc;
                    if (doc == "") {
                        document.getElementById("txtMess").style.display = "block";
                        document.getElementById("tButn").style.display = "none";
                    }else{
                        document.getElementById("txtMess").style.display = "none";
                        document.getElementById("tButn").style.display = "block";
                    }
                }
                document.addEventListener("DOMContentLoaded", function () {
                    PassDoc();
                });
            </script>
        </head>
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container">
            <div class="loader"></div>
            <div class="loader2" id="Control_carga"></div>
            <div id="page">
                <Alertas:Alertas />
                <Personal:Personal />
                <script src="Interfaz/Tabs/tabs.js" type="text/javascript"></script>
                <script src="Interfaz/Calendarios/Js_range.js"></script>
                <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
                <script src="Interfaz/Calendarios/Js_normal.js"></script>
                <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
                <script src="Interfaz/Firma/assets/bezier.js"></script>
                <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
                <script src="Interfaz/Firma/assets/json2.min.js"></script>
            </div>
        </div>
    </body>
</html>
