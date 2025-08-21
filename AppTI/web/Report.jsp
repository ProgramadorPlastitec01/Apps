
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<%@taglib uri="/WEB-INF/tlds/Tld_report.tld" prefix="Report" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Pendiente</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/progress.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/chocolat/dist/css/chocolat.css">
        <script src="Interface/Content/Assets/js/sweetalert2.js"></script>
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Report:Report/>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('.daterange-cus').daterangepicker({
                    singleDatePicker: false,
                    showDropdowns: false,
                    autoUpdateInput: false, // No actualizar el input automáticamente
                    locale: {
                        format: 'YYYY-MM-DD', // Formato de las fechas
                        separator: " - ",
                        applyLabel: "Aplicar",
                        cancelLabel: "Cancelar",
                        fromLabel: "Desde",
                        toLabel: "Hasta",
                        customRangeLabel: "Personalizado",
                        weekLabel: "S",
                        daysOfWeek: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
                        monthNames: ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"],
                        firstDay: 1  // La semana comienza el lunes
                    }
                })
                        // Evento cuando se aplica el rango de fechas
                        .on('apply.daterangepicker', function (ev, picker) {
                            $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
                        })
                        // Evento cuando se cancela el rango de fechas
                        .on('cancel.daterangepicker', function (ev, picker) {
                            $(this).val('');  // Limpiar el campo de fecha si se cancela
                        });
            });


            function ViewDetailPending(id) {
                const body = document.body;
                if (document.getElementById("View" + id).style.display === "none") {
                    document.getElementById("View" + id).style.display = "block";
                } else if (document.getElementById("View" + id).style.display === "block") {
                    document.getElementById("View" + id).style.display = "none";
                }
                body.classList.toggle('modal-open');
            }
            function ViewDetailPendingDesc(id) {
                const body = document.body;
                if (document.getElementById("ViewDesc" + id).style.display === "none") {
                    document.getElementById("ViewDesc" + id).style.display = "block";
                } else if (document.getElementById("ViewDesc" + id).style.display === "block") {
                    document.getElementById("ViewDesc" + id).style.display = "none";
                }
                body.classList.toggle('modal-open');
            }
        </script>
        <script type="text/javascript">
            function validarInput(input) {
                // Obtenemos el valor del input
                const valor = input.value;
                const progressFinal = document.querySelector("#ProgressFinal");

                // Comprobamos si el campo está vacío
                if (valor === "") {
                    // Si el campo está vacío, limpiamos y deshabilitamos el campo #ProgressFinal
                    input.value = "";
                    progressFinal.value = "";
                    progressFinal.disabled = true;
                } else if (!isNaN(valor)) {
                    // Comprobamos si el valor es un número entre 1 y 100
                    const numValor = Number(valor);
                    if (numValor < 0 || numValor > 99) {
                        // Si el valor está fuera del rango, lo ponemos a vacío y deshabilitamos #ProgressFinal
                        input.value = "";
                        progressFinal.value = "";
                        progressFinal.disabled = true;
                    } else {
                        // Si el valor es válido, sumamos 1 al valor y lo asignamos a #ProgressFinal
                        const sm = numValor + 1;
                        progressFinal.value = sm;
                        progressFinal.disabled = false;  // Habilitamos el campo si el valor es válido
                    }
                } else {
                    // Si el valor no es un número, lo ponemos a vacío y limpiamos #ProgressFinal
                    input.value = "";
                    progressFinal.value = "";
                    progressFinal.disabled = true;
                }
            }


            function validarInputTwo(input) {
                // Obtenemos el valor del primer campo
                const progressInitial = document.querySelector("#ProgressInitial");
                const valor1 = parseInt(progressInitial.value);

                // Obtenemos el segundo campo
                const progressFinal = document.querySelector("#ProgressFinal");

                // Verificamos si el valor del segundo campo está vacío
                if (input.value === "") {
                    iziToast.warning({
                        title: 'Campo vacío!',
                        message: 'Para habilitar ajuste nuevamente el campo incial.',
                        position: 'bottomRight',
                        time: 3000
                    });
//                    progressInitial.value = ""; // Limpiamos el campo inicial
                    progressFinal.value = "";   // Limpiamos el campo final
                    progressFinal.disabled = true; // Deshabilitamos el segundo campo
                    return;
                }

                // Verificamos si el valor del primer campo es un número válido
                if (isNaN(valor1) || valor1 < 0) {
                    iziToast.warning({
                        title: 'Campo inválido!',
                        message: 'Por favor ingrese un valor válido en el campo inicial.',
                        position: 'bottomRight',
                        time: 3000
                    });
                    input.value = ""; // Limpiamos el segundo campo
                    progressFinal.value = ""; // Limpiamos el valor de #ProgressFinal
                    progressFinal.disabled = true; // Deshabilitamos el segundo campo
                    return;
                }

                // Habilitamos el segundo campo si el primer campo tiene valor válido
                progressFinal.disabled = false;

                // Obtenemos el valor del segundo campo
                const valor2 = input.value;

                // Comprobamos si el valor del segundo campo es un número
                if (!isNaN(valor2)) {
                    // Convertimos a entero el valor del segundo campo
                    const numero2 = parseInt(valor2);

                    // Definimos los límites permitidos
                    const maximoPermitido = Math.min(100, valor1 + 100); // No permitimos más de 100
                    const minimoPermitido = valor1 + 1;

                    // Validamos si el número ingresado está dentro del rango permitido
                    if (numero2 < minimoPermitido) {
                        iziToast.warning({
                            title: 'Cantidad mínima permitida!',
                            message: 'El mínimo permitido es a partir de ' + minimoPermitido + '.',
                            position: 'bottomRight',
                            time: 3000
                        });
                        input.value = ""; // Limpiamos el valor si es menor al mínimo permitido
                    } else if (numero2 > maximoPermitido) {
                        iziToast.warning({
                            title: 'Cantidad máxima permitida!',
                            message: 'El máximo permitido es ' + maximoPermitido + '.',
                            position: 'bottomRight',
                            time: 3000
                        });
                        input.value = ""; // Limpiamos el valor si es mayor al máximo permitido
                    } else {
                        // Si el valor es correcto, lo dejamos como está
                        progressFinal.value = numero2;
                    }
                } else {
                    // Si el valor no es un número, lo ponemos a vacío
                    input.value = "";
                }
            }
        </script>
        <script>
            function validarFormulario() {
                // Obtener los valores de los campos
                var idPending = document.querySelector("input[name='IdPending']").value.trim();
                var txtAffair = document.querySelector("input[name='Txt_affair']").value.trim();
                var txtPriority = document.querySelector("select[name='Txt_priority']").value.trim();
                var txtCharge = document.querySelector("select[name='Txt_charge']").value.trim();
                var txtPerson = document.querySelector("select[name='Txt_person']").value.trim();
                var dateRegister = document.querySelector("input[name='DateRegister']").value.trim();
                var dateSolution = document.querySelector("input[name='DateSolution']").value.trim();
                var progressInitial = document.querySelector("input[name='Txt_progressInitial']").value.trim();
                var progressFinal = document.querySelector("input[name='Txt_progressFinal']").value.trim();
                var txtDescription = document.querySelector("input[name='Txt_description']").value.trim();
                var txtSolution = document.querySelector("input[name='Txt_solution']").value.trim();

                // Comprobar si todos los campos están vacíos
                if (idPending === "" && txtAffair === "" && txtPriority === "" && txtCharge === "" &&
                        txtPerson === "" && dateRegister === "" && dateSolution === "" &&
                        progressInitial === "" && progressFinal === "" && txtDescription === "" && txtSolution === "") {

                    // Mostrar alerta si no hay campos diligenciados
                    iziToast.warning({
                        title: 'Campos vacíos!',
                        message: 'Debe diligenciar al menos un campo para realizar la búsqueda.',
                        position: 'bottomRight',
                        timeout: 3000
                    });
                } else {
                    // Si al menos un campo está diligenciado, enviar el formulario
                    document.getElementById('FormReportPending').submit();
                }
            }
        </script>
        <script>
            function validateFormInfo() {
                // Obtener los valores de los campos
                var IdPC = document.querySelector("select[name='IdPC']").value.trim();
                var Antivirus = document.querySelector("select[name='Antivirus']").value.trim();
                var Internal_Mail = document.querySelector("input[name='Internal_Mail']").value.trim();
                var External_Mail = document.querySelector("input[name='External_Mail']").value.trim();
                var Description = document.querySelector("input[name='Description']").value.trim();
                var Bill = document.querySelector("input[name='Bill']").value.trim();
                var Bill_Date = document.querySelector("input[name='Bill_Date']").value.trim();
                var Warranty = document.querySelector("input[name='Warranty']").value.trim();
                var Warranty_Date = document.querySelector("input[name='Warranty_Date']").value.trim();
                var Internet = document.querySelector("select[name='Internet']").value.trim();
                var Login_Plastitec = document.querySelector("input[name='Login_Plastitec']").value.trim();
                var Licence = document.querySelector("input[name='Licence']").value.trim();
                var Licence_date = document.querySelector("input[name='Licence_date']").value.trim();
                var MAC = document.querySelector("input[name='MAC']").value.trim();
                var Supplier = document.querySelector("input[name='Supplier']").value.trim();
                var Network_Point = document.querySelector("input[name='Network_Point']").value.trim();
                var RED = document.querySelector("input[name='RED']").value.trim();
                var Skype = document.querySelector("input[name='Skype']").value.trim();
                var Type_State = document.querySelector("select[name='Type_State']").value.trim();
                var IP = document.querySelector("input[name='IP']").value.trim();
                var VLAN = document.querySelector("input[name='VLAN']").value.trim();
                var VPN = document.querySelector("input[name='VPN']").value.trim();
                var Office_Version = document.querySelector("input[name='Office_Version']").value.trim();
                var WIN_Version = document.querySelector("input[name='WIN_Version']").value.trim();
                var Responsible = document.querySelector("input[name='Responsible']").value.trim();
                var Area = document.querySelector("select[name='Area']").value.trim();
                var Type_PC = document.querySelector("select[name='Type_PC']").value.trim();

                // Comprobar si todos los campos están vacíos
                if (IdPC === "" && Antivirus === "" && Internal_Mail === "" && External_Mail === "" &&
                        Description === "" && Bill === "" && Bill_Date === "" &&
                        Warranty === "" && Warranty_Date === "" && Internet === "" && Login_Plastitec === "" &&
                        Licence === "" && Licence_date === "" && MAC === "" && Supplier === "" &&
                        Network_Point === "" && RED === "" && Skype === "" && Type_State === "" &&
                        IP === "" && VLAN === "" && VPN === "" && Office_Version === "" &&
                        WIN_Version === "" && Responsible === "" && Area === "" && Type_PC === "") {


                    // Mostrar alerta si no hay campos diligenciados
                    iziToast.warning({
                        title: 'Campos vacíos!',
                        message: 'Debe diligenciar al menos un campo para realizar la búsqueda.',
                        position: 'bottomRight',
                        timeout: 3000
                    });
                } else {
                    // Si al menos un campo está diligenciado, enviar el formulario
                    Swal.fire({
                        title: 'Buscando...',
                        html: '<i class="fas fa-spinner fa-spin" style="font-size: 50px; color: #00281b; overflow: hidden;"></i>',
                        icon: 'info',
                        showConfirmButton: false,
                        allowEscapeKey: false,
                        allowOutsideClick: false
                    });
                    document.getElementById('FormReportInformation').submit();
                }
            }
        </script>
        <script>
            function limpiarFormulario() {
                // Obtener el formulario por su ID
                var formulario = document.getElementById('FormReportPending');

                // Resetear el formulario para limpiar todos los campos
                formulario.reset();

                // Si hay campos que estaban deshabilitados, también se deben habilitar de nuevo si es necesario
                document.getElementById('ProgressFinal').disabled = true; // Reestablecer estado de campos específicos si es necesario

                iziToast.success({
                    title: 'Formulario limpio!',
                    message: 'Todos los campos han sido limpiados.',
                    position: 'bottomRight',
                    timeout: 3000
                });
            }
            $(document).ready(function () {
                // Iniciar Chocolat.js cuando el documento esté listo
                $('.chocolat-parent').Chocolat();
            });
        </script>
        <Alerts:Alert/>
        <script>
            function ClearInforData() {
                var formulario = document.getElementById('FormReportInformation');
                formulario.reset();
                iziToast.success({
                    title: 'Formulario limpio!',
                    message: 'Todos los campos han sido limpiados.',
                    position: 'bottomRight',
                    timeout: 3000
                });
            }
            $(document).ready(function () {
                $('.chocolat-parent').Chocolat();
            });
            $(document).ready(function () {
                if ($.fn.DataTable.isDataTable('#table-4')) {
                    $('#table-4').DataTable().destroy(); // Destruye la instancia anterior
                }

                $('#table-4').DataTable({
                    "scrollX": true, // Activa el desplazamiento horizontal
                    "autoWidth": false, // Evita que DataTables ajuste el ancho de las columnas
                    "destroy": true // Permite la reinicialización
                });
            });

        </script>
        <Alerts:Alert/>
        <script src="Interface/Content/Assets/modules/chocolat/dist/js/jquery.chocolat.min.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-sweetalert.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
    </body>
</html>
