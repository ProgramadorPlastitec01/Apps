<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Instrumento_medicion.tld" prefix="Instrumento" %>
<%@taglib uri="/WEB-INF/tlds/Alerta.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PVM | Instrumento Medición </title>
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
        <!-------->
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Instrumento:Tipo_instrumento/>
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
            function contrasenaM() {
                document.getElementById("passM-id").value = '1';
                document.form1.submit();
            }
        </script>
        <script>
            function swipeContent(ide) {
                var cambiar = 0;
                if (ide == 1) {
                    cambiar = 2;
                } else {
                    cambiar = 1;
                }
                if (document.getElementById("divEditor").style.display === "none") {
                    document.getElementById("divEditor").style.display = "block";
                    document.getElementById("btnchang" + ide).style.display = "none";
                    document.getElementById("btnchang" + cambiar).style.display = "block";
                } else if (document.getElementById("divEditor").style.display === "block") {
                    document.getElementById("divEditor").style.display = "none";
                    document.getElementById("btnchang" + ide).style.display = "none";
                    document.getElementById("btnchang" + cambiar).style.display = "block";
                }
            }
        </script>       

        <script>
            function Activarinstrumento(obj_instrumentos) {
                swal({
                    title: "Activar Instrumento?",
                    text: "Seguro que desea activar el instrumento",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Instrumento_medicion?opc=9&idI=' + obj_instrumentos + '&est=1';

                        });
            }

            function Inactivarinstrumento(obj_instrumentos) {
                swal({
                    title: "Inactivar Instrumento?",
                    text: "Seguro que desea inactivar el instrumento",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Instrumento_medicion?opc=9&idI=' + obj_instrumentos + '&est=0';

                        });
            }
        </script>
        <script>
            function ElimVerifi(Dat) {
                swal({
                    title: "¡Atención!",
                    text: "¿Seguro que desea eliminar este registro?<br><br><b>Ingrese una justificacion:</b>",
                    type: "input",
                    showCancelButton: true,
                    confirmButtonColor: 'orange',
                    cancelButtonColor: '#3D6FFF',
                    confirmButtonText: 'Aceptar',
                    cancelButtonText: 'Cancelar',
                    closeOnConfirm: false,
                    html: true,
                },
                        function (inputValue) {

                            if (inputValue === false)
                                return false;

                            if (inputValue === "") {
                                swal.showInputError("Se debe agregar una justificación!");
                                return false
                            }

                            var data = Dat;
                            data = data.replace("[", "");
                            data = data.replace("]", "");
                            data = data.split("///");
                            location.href = "Instrumento_medicion?opc=12&idV=" + data[0] + "&idI=" + data[1] + "&idTi=" + data[2] + "&idTp=" + data[3] + "&Just=" + inputValue + "";

                        });
            }
        </script>
        <script>
            function swticher() {
                if (document.getElementById("btnStatics").style.display === "none") {
                    document.getElementById("btnStatics").style.display = "block";
                    document.getElementById("btnGrafics").style.display = "none";
                } else if (document.getElementById("btnStatics").style.display === "block") {
                    document.getElementById("btnStatics").style.display = "none";
                    document.getElementById("btnGrafics").style.display = "block";
                }
            }
        </script>

        <script>
            document.getElementById('campo1').addEventListener('input', function () {
                var texto = this.value;
                document.getElementById('campo2').value = texto;
            });
        </script>

<!--        <script>
            $(document).ready(function () {
                $('#table-1').DataTable({
                    dom: '<"search"f>t'
                });

                // Agregar un ID al campo de búsqueda
                $('.dataTables_filter input').attr('id', 'miCampoDeBusqueda');
            });
        </script>-->

        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables_second.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables_Third.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script src="Interfaz/Contenido/assets/modules/chart.min.js"></script>
        <!--<script src="Interfaz/Contenido/assets/js/page/modules-chartjs.js"></script>-->

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>

        <!--THIS FILE-->
        <script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Instrumentos.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>

        <!--<script src="Interfaz/Contenido/assets/js/scripts.js"></script>-->
        <!--<script src="Interfaz/Contenido/assets/js/custom.js"></script>-->


        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>

    </body>
</html>
