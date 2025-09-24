<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Rollo.tld" prefix="Roll" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rollo | ST</title>
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
        <style>
            .was-validated select.select2:invalid + .select2.select2-container.select2-container--default span.select2-selection, select.select2.is-invalid + .select2.select2-container.select2-container--default span.select2-selection {
                border-color: #fa5c7c;
                padding-right: 2.25rem;
                background-repeat: no-repeat;
                background-position: right calc(0.375em + 0.1875rem) center;
                background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
            }
            .was-validated select.select2:invalid + .select2.select2-container.select2-container--default .select2-selection__arrow, select.select2.is-invalid + .select2.select2-container.select2-container--default .select2-selection__arrow {
                right: 25px!important;
            }
            .was-validated select.select2:valid + .select2.select2-container.select2-container--default span.select2-selection, select.select2.is-valid + .select2.select2-container.select2-container--default span.select2-selection {
                border-color: #28a745;
                padding-right: 2.25rem;
                background-repeat: no-repeat;
                background-position: right calc(0.375em + 0.1875rem) center;
                background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
            }
            .was-validated select.select2:valid + .select2.select2-container.select2-container--default .select2-selection__arrow, select.select2.is-valid + .select2.select2-container.select2-container--default .select2-selection__arrow {
                right: 25px!important;
            }
        </style>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Roll:Rollo/>
                </div>
            </div>
        </div>
        <Alertas:Alert/>
        <!--        <script>
                    $('.select2').select2();
                    $(".needs-validation").on('submit', function (event) {
                    $(this).addClass('was-validated');
                            if ($(this)[0].checkValidity() === false) {
                    event.preventDefault();
                            event.stopPropagation();
                            return false;
                    } else {
                    alert('form submitted');
                            event.preventDefault();
                            event.stopPropagation();
                            return true;
                    }
                    ;
                </script>-->
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
            function ActiveControl(idRoll) {
                document.getElementById("btnEstric").classList.remove("btnEstric");
                document.getElementById("idRoll").value = idRoll;
                document.getElementById("idRoll2").value = idRoll;
                document.getElementById("idRoll3").value = idRoll;
                document.getElementById("idRoll4").value = idRoll;
                document.getElementById("idRoll5").value = idRoll;
                document.getElementById("idRoll6").value = idRoll;

            }
            function ActiveControl2(idRoll){
                document.getElementById("btnEstric").classList.remove("btnEstric");
                document.getElementById("idRoll2").value = idRoll;
                document.getElementById("idRoll3").value = idRoll;
                document.getElementById("idRoll4").value = idRoll;
                document.getElementById("idRoll5").value = idRoll;
                document.getElementById("idRoll6").value = idRoll;
            }
        </script>
        <script>
            function fechas(nro) {
                var now = moment().format("HH:mm");
                document.getElementById("fechas_" + nro + "").value = now;
                document.getElementById("fechas_2").value = now;
            }
        </script>
        <script>
            function Filtrar2() {
                var table = document.getElementById('table_roll');
                var filtro = document.getElementById('Txt_filtrop').value.toLowerCase();
                filtro = filtro.trim(filtro);
                var cellsOfRow = "";
                var found = false;
                var compareWith = "";
                // Recorremos todas las filas con contenido de la tabla
                for (var i = 2; i < table.rows.length; i++)
                {
                    cellsOfRow = table.rows[i].getElementsByTagName('td');
                    found = false;
                    // Recorremos todas las celdas
                    if (filtro == "") {
                        found = false;
                    } else {
                        for (var j = 0; j < cellsOfRow.length && !found; j++)
                        {
                            compareWith = cellsOfRow[j].innerHTML.toLowerCase();
                            // Buscamos el texto en el contenido de la celda
                            if (filtro.length == 0 || (compareWith.indexOf(filtro) > -1))
                            {
                                found = true;
                            }
                        }
                    }
                    if (found)
                    {
                        table.rows[i].style.display = '';
                    } else {
                        if (filtro.length == 0) {
                            // i starts from 1 to skip table header row
                            if (i > 10)
                                table.rows[i].style.display = 'none';
                            else
                                table.rows[i].style.display = '';
                            // table.rows[i].style.display = '';
                        } else {
                            // si no ha encontrado ninguna coincidencia, esconde la fila de la tabla
                            table.rows[i].style.display = 'none';
                        }
                    }
                }
            }

        </script>
        <script>
            function validarFormulario() {
                var radios = document.getElementsByName('Nmb_inspv');
                var seleccionado = false;
                for (var i = 0; i < radios.length; i++) {
                    if (radios[i].checked) {
                        seleccionado = true;
                        break;
                    }
                }
                if (!seleccionado) {
                    // Si ninguna opción está seleccionada, agrega la clase 'no-seleccionado' a las opciones
                    for (var i = 0; i < radios.length; i++) {
                        radios[i].parentNode.classList.add('no-seleccionado');
                    }
                    return false; // Devuelve false para evitar el envío del formulario
                } else {
                    // Si hay una opción seleccionada, elimina la clase 'no-seleccionado' de todas las opciones
                    for (var i = 0; i < radios.length; i++) {
                        radios[i].parentNode.classList.remove('no-seleccionado');
                    }
                    return true; // Permite el envío del formulario
                }
            }

            function ValidarInputLabel() {
                var rd = document.getElementsByName('Nmb_inspv');
                for (var i = 0; i < rd.length; i++) {
                    rd[i].parentNode.classList.remove('no-seleccionado');
                }
            }


        </script>
        <script>
            function CargarHora() {
                var hoy = new Date();
                var ahora = hoy.toLocaleTimeString('en-US');
                document.getElementById("fechas_1").value = ahora;
                alert(ahora);
            }
        </script>
        <script>
            function RolloReplace(NroRll) {
                document.getElementById("idRollNew").value = NroRll;
                document.getElementById("formReplace").submit();
            }
        </script>
        <script>
            function tran_rll(nro) {
                document.getElementById("idTranRlll").value = nro;
            }
        </script>
        <script type="text/javascript">
            function avanzarCampo(event, siguienteCampoId) {
                if (event.keyCode === 13) {
                    event.preventDefault(); // Evitar el comportamiento predeterminado de "Enter"
                    document.getElementById(siguienteCampoId).focus();
                }
            }
            function enviarFormulario(event) {
                if (event.keyCode === 13) {
                    event.preventDefault(); // Evitar el comportamiento predeterminado de "Enter"
                    document.getElementById('FormKeyCode').submit(); // Enviar el formulario
                }
            }
        </script>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/> 
        <script src="Interfaz/Contenido/assets/js/page/modules-sweetalert.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>

        <!--THIS FILE-->
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <!-------->
    </body>
</html>
