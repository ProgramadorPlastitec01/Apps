<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Rnoconforme.tld" prefix="RNoConforme" %>
<%@taglib uri="/WEB-INF/tlds/Alerta.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PVM | No conformidad</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <!--THIS FILE-->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <RNoConforme:Rno_conforme/>
                </div>
            </div>
        </div>
        <Alertas:LanzarAlertas/>
        <script>
            function SwitchValue() {
                if (document.getElementById("Nmb_estP").checked === true) {
                    document.getElementById("Nmb_est").value = 1;
                } else {
                    document.getElementById("Nmb_est").value = 0;
                }
            }
        </script>
        <script>
            function cleanForm(idform) {
                document.getElementById(idform).reset();
            }
        </script>
        <script>
            function SendMail(){
                document.getElementById("FormMail").submit();
            }
        </script>
        <script>
            function habilitar(value)
            {
                if (value == "")
                {
                    // habilitamos
                    document.getElementById("btn_enviar").style.display = 'none';
                } else {
                    // deshabilitamos
                    document.getElementById("btn_enviar").style.display = 'initial';
                }
            }
            function SeleccionDestinatarios(idst)
            {
                if (idst.checked) {
                    document.getElementById('txt_destinatarios').value += "" + idst.value;
                } else {
                    document.getElementById("txt_destinatarios").value = document.getElementById("txt_destinatarios").value.replace(idst.value, "");
                }
            }

            function SeleccionDestino(idst, otro)
            {
                var radioButTrat = document.getElementsByName("chk_destino");
                var anterior = document.getElementById('txt_destino').value;
                for (var i = 0; i < radioButTrat.length; i++) {
                    if (radioButTrat[i].value == anterior) {
                        radioButTrat[i].style.display = 'block';
                    }
                }
                if (idst.checked) {
                    document.getElementById('txt_destino').value = idst.value;
                    otro.style.display = 'none';
                    document.getElementById("txt_destinatarios").value = document.getElementById("txt_destinatarios").value.replace(idst.value.split("/")[0], "");
                    for (var i = 0; i < radioButTrat.length; i++) {
                        if (radioButTrat[i].value == idst.value.split("/")[0]) {
                            radioButTrat[i].checked = false;
                        }
                    }
                }
            }
            function idMail(id) {
                alert(id);
            }
            function platilla() {
                var htmleditor = document.getElementById("htmleditor-id").value;
                document.getElementById("plantilla-id").value = htmleditor;
                document.formP.submit();
            }
            function mueveReloj() {
                var mydate = new Date();
                var year = mydate.getYear();
                if (year < 1000)
                    year += 1900;
                var day = mydate.getDay();
                var month = mydate.getMonth() + 1;
                if (month < 10)
                    month = "0" + month;
                var daym = mydate.getDate();
                if (daym < 10)
                    daym = "0" + daym;
                momentoActual = new Date()
                hora = momentoActual.getHours()
                minuto = momentoActual.getMinutes()
                segundo = momentoActual.getSeconds()

                horaImprimible = year + " / " + month + " / " + daym;

                document.form_reloj.reloj.value = horaImprimible

            }
        </script>
        <script>
            function EnvioCorreoCarga(){
                swal({
                    title: "Enviando correo...",
                    text: "<i class='fas fa-spinner fa-spin fa-lg' style='font-size: 24px;'></i>",
                    type: "info",
                    showConfirmButton: false,
                    html: true
                })
            }
        </script>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables_second.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables_Third.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Instrumentos.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
    </body>
</html>
