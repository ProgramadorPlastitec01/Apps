<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Record.tld" prefix="Record" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alert"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro | ST</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
    </head>
    <body class="sidebar-mini">
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Record:Record/>
                </div>
                <Alert:Alert/>
                <script type="text/javascript">
                    function SwitchValue() {
                        if (document.getElementById('State').checked === true) {
                            document.getElementById("State").value = 1;
                        } else {
                            document.getElementById("State").value = 0;
                        }
                    }
                </script>
                <script type="text/javascript">
                    function th_rollo() {
                        document.getElementById("th_rollo").classList.remove("sorting_asc");
                        document.getElementById("th_rollo").classList.remove("sorting_desc");
                    }
                </script>
                <script type="text/javascript">
                    function MassiveId(ide) {
                        var id = "[" + ide + "]";
                        var cont = document.getElementById("id_serial").value;
                        if (cont.includes(id)) {
                            document.getElementById("id_serial").value = cont.replace(id, "");
                        } else {
                            document.getElementById("id_serial").value += id;
                        }
                    }
                </script>
                <script type="text/javascript">
                    function mostrarPass() {
                        var password = document.getElementById("txtPassword");
                        var eye = document.getElementById("icon");
                        if (password.type == "password") {
                            password.type = "text";
                            eye.className = "fas fa-eye-slash";
                        } else {
                            password.type = "password";
                            eye.className = "fas fa-eye";
                        }
                    }
                </script>
                <script type="text/javascript">
                    function SubmitForm() {
                        var html = document.getElementById("templateMajor").innerHTML;
                        document.getElementById("templateSecondary").value = html;
                        let form = document.getElementById("FormSignature");
                        form.submit();
                    }
                </script>
                <script type="text/javascript">
                    function ValuePass() {
                        var html = document.getElementById("templateMajor").innerHTML;
                        document.getElementById("templateSecondary").value = html;
                    }
                </script>
                <script type="text/javascript">
                    function ConfirmationSave() {
                        swal({
                            title: "Cuidado!",
                            text: "Asegúrese de guardar antes de continuar, para acceder a la opcion dar clic en Aceptar.",
                            type: "warning",
                            showCancelButton: true,
                            showConfirmButton: true,
                            confirmButtonColor: "#c9e433",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: true
                        },
                                function () {
                                    document.getElementById("Ventana5").style.display = 'block';
                                }
                        );
                    }
                </script>
                <script type="text/javascript">
                    function SignatureClearance(id_order, id_record, id_clearance) {
                        swal({
                            title: "Atencion!",
                            text: "Asegúrese de guardar antes de continuar, para liberar el despeje dar clic en Aceptar.",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#c9e433",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = "Record?opc=7&id_order=" + id_order + "&id_record=" + id_record + "&id_clearence=" + id_clearance + "&state=1";
                                }
                        );
                    }
                </script>
                <script type="text/javascript">
                    function SubmitFormSave() {
                        var html = document.getElementById("templateMajor").innerHTML;
                        document.getElementById("templateThird").value = html;
                        let form = document.getElementById("FormSave");
                        form.submit();
                    }
                </script>
                <script type="text/javascript">
                    function printSection(el) {
                        var getFullContent = document.body.innerHTML;
                        var printsection = document.getElementById(el).innerHTML;
                        document.body.innerHTML = printsection;
                        window.print();
                        document.body.innerHTML = getFullContent;
                    }
                </script>
                <script>
                    function ValidLineForm() {
                        var loteUser = document.getElementById("Lote").value;
                        var lotes = document.getElementById("lotes").value;
                        var Arg_line = lotes.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        var idLinea = document.getElementById("id_linea").value;
                        for (var i = 0; i < Arg_line.length; i++) {
                            var Arg_vali = Arg_line[i].toString().split("/");
                            if (Arg_vali[0] === loteUser && Arg_vali[1] === idLinea) {
                                document.getElementById("buttonValidation").disabled = true;
                                document.getElementById("div_dp").style.display = 'block';
                                document.getElementById("div_dp").style.opacity = '1';
                                i = Arg_line.length;
                            } else {
                                document.getElementById("buttonValidation").disabled = false;
                                document.getElementById("div_dp").style.display = 'none';
                            }
                        }
                    }
                    function DivHa(id) {
                        if (document.getElementById("DivH" + id).style.display === "none") {
                            document.getElementById("DivH" + id).style.display = "block";
                            document.getElementById("TempRoll").value = 1;
                        } else if (document.getElementById("DivH" + id).style.display === "block") {
                            document.getElementById("DivH" + id).style.display = "none";
                            document.getElementById("TempRoll").value = 0;
                        }
                    }
                    function loteIni() {
                        var fecha = document.getElementById("Txt_date").value;
                        if (fecha === "") {
                            swal({
                                title: "Atencion!",
                                text: "No se ha seleccionado una fecha.",
                                type: "warning"
                            });
                        } else {
                            var anio = fecha.split("-");
                            var meses = ["X", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"];
                            var mes = parseInt(anio[1]);
                            var year = parseInt(anio[0]) - 1990;
                            var cadena = year + meses[mes] + anio[2];
                            cadena = cadena.toString();
                            var prod = document.getElementById("Temp_prod").value;
                            document.getElementById("Lote").value = prod + cadena;
                            document.getElementById("Txt_lot_c").value = "-" + cadena;
                        }
                    }
                    function validarInput(input) {
                        // Obtenemos el valor del input
                        const valor = input.value;
                        // Comprobamos si el valor es un número
                        if (!isNaN(valor)) {
                            // Comprobamos si el valor es mayor o igual a 0
                            if (valor <= 0) {
                                // Si el valor es menor que 0, lo ponemos a vacío
                                input.value = "";
                            }
                        } else {
                            // Si el valor no es un número, lo ponemos a vacío
                            input.value = "";
                        }
                    }
                    function validarSegundoCampo(input, mxrll) {
                        // Obtenemos el valor del primer campo
                        const valor1 = parseInt(document.querySelector("#Roll_ini").value);
                        // Obtenemos el valor del segundo campo
                        const valor2 = input.value;
                        let sumData = 0;


                        // Comprobamos si el valor es un número
                        if (!isNaN(valor2)) {
                            // Si el valor es un número, lo convertimos a entero
                            const numero2 = parseInt(valor2);
                            const maximoPermitido = valor1 + 60;



                            const minimoPermitido = valor1 + 1;
                            // Comprobamos si el valor es mayor o igual al valor del primer campo



                            if (numero2 < valor1) {
                                // Si el valor es menor que el valor del primer campo, lo ponemos a vacío
                                iziToast.warning({
                                    title: 'Cantidad minima permitidad!',
                                    message: 'El minimo permitido es apartir de ' + minimoPermitido + '.',
                                    position: 'bottomRight',
                                    time: 3000
                                });
                                input.value = "";
                            } else if (numero2 > maximoPermitido) {
                                if (mxrll > 0) {
                                    sumData = valor1 - valor2;
                                    if (sumData > mxrll) {
                                        iziToast.warning({
                                            title: 'Cantidad maxima permitida!',
                                            message: 'El máximo permitido es ' + maximoPermitido + ', si ingresa mas rollos, supera la cantidad de rollos permitidos por orden.',
                                            position: 'bottomRight',
                                            time: 3000
                                        });
                                        input.value = "";
                                    }
                                }
                                iziToast.warning({
                                    title: 'Cantidad maxima permitida!',
                                    message: 'El máximo permitido es ' + maximoPermitido + ', que representa 60 rollos adicionales.',
                                    position: 'bottomRight',
                                    time: 3000
                                });
                                input.value = "";
                            } else if (numero2 === valor1) {
                                // Si el valor es igual al valor del primer campo, no hacemos nada
                                input.value = numero2 + 1;
                            } else {
                                // Si el valor es mayor o igual al valor del primer campo, lo dejamos como está
                            }
                        } else {
                            // Si el valor no es un número, lo ponemos a vacío
                            input.value = "";
                        }
                    }
                </script>
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        const checkboxes = document.querySelectorAll('.input-control');
                        checkboxes.forEach(checkbox => {
                            checkbox.addEventListener('change', () => {
                                const parentItem = checkbox.closest('.ValidDiv');
                                checkboxes.forEach(otherCheckbox => {
                                    if (otherCheckbox.closest('.ValidDiv') !== parentItem) {
                                        otherCheckbox.closest('.ValidDiv').classList.add('inactive');
                                    }
                                });
                                // Verificar si no hay checkboxes seleccionados
                                const noCheckboxesSelected = !Array.from(checkboxes).some(cb => cb.checked);
                                if (noCheckboxesSelected) {
                                    checkboxes.forEach(otherCheckbox => {
                                        otherCheckbox.closest('.ValidDiv').classList.remove('inactive');
                                    });
                                }
                            });
                        });
                    });
                </script>
                <script type="text/javascript">
                    function pasarDatos_uno(main, ide) {
                        var id = "[" + ide + "]";
                        var content = document.getElementById("inputDatos" + main).value;

                        if (content.includes(id)) {
                            document.getElementById("inputDatos" + main).value = content.replace(id, "");
                            document.getElementById("recolec" + main).value += id;
                        } else {
                            var cont = document.getElementById("recolec" + main).value;
                            if (cont.includes(id)) {
                                document.getElementById("recolec" + main).value = cont.replace(id, "");
                                document.getElementById("inputDatos" + main).value += id;
                            }
                        }

                    }
                </script>
                <script type="text/javascript">
                    function pasarDatos_dos(ide, list, idR) {
                        var id = "[" + ide + "]";
                        var content = document.getElementById("editable").value;
                        var contentReg = document.getElementById("idReg").value;
                        if (content.includes(id)) {
                            document.getElementById("editable").value = content.replace(id, "");
                            document.getElementById("idReg").value = contentReg.replace(idR, "");
                        } else {
                            document.getElementById("editable").value += id;
                            document.getElementById("idReg").value += idR;
                        }
                        var divElement = document.getElementById("DivId" + ide);
                        var inputCheckend = document.getElementsByName("CountId");

                        for (var i = 0; i <= list; i++) {
                            if (i != ide) {
                                if (document.getElementById("selc" + i).disabled === false) {
                                    document.getElementById("selc" + i).disabled = true;
                                    document.getElementById("cont" + i).disabled = false;
                                    document.getElementById("BloqSelc" + i).style.color = "#C1C1C1";
                                    document.getElementById("BloqSelc" + i).style.cursor = "not-allowed";
                                    document.getElementById("BloqCont" + i).style.color = "#00281b";
                                    document.getElementById("BloqCont" + i).style.cursor = "pointer";
                                    divElement.classList.add("inactive");
                                    for (var j = 0; j < inputCheckend.length; j++) {
                                        inputCheckend[j].checked = false;
                                    }
                                } else {
                                    document.getElementById("selc" + i).disabled = false;
                                    document.getElementById("cont" + i).disabled = true;
                                    document.getElementById("BloqSelc" + i).style.color = "#00281b";
                                    document.getElementById("BloqSelc" + i).style.cursor = "pointer";
                                    document.getElementById("BloqCont" + i).style.color = "#C1C1C1";
                                    document.getElementById("BloqCont" + i).style.cursor = "not-allowed";
                                    divElement.classList.remove("inactive");
                                    for (var j = 0; j < inputCheckend.length; j++) {
                                        inputCheckend[j].checked = false;
                                    }
                                    if (i > 0) {
                                        document.getElementById("inputDatos" + i).value += cont = document.getElementById("recolec" + i).value;
                                        document.getElementById("recolec" + i).value = "";
                                    }
                                }
                            }
                        }
                    }
                </script>
                <script type="text/javascript">
                    function pasarDatos_tres(ide, idReg2) {
                        var idReg = document.getElementById("idReg").value;
                        var salidax = document.getElementById("editable").value;
                        var salida = salidax.replace("[", "").replace("]", "");
                        var salidinput = document.getElementById("recolec" + salida).value;
                        if (salidinput.length > 0) {
                            var entrainput = document.getElementById("inputDatos" + ide).value;
                            var datasalida = document.getElementById("inputDatos" + salida).value;
                            var editaa = document.getElementById("editable").value;
                            if (entrainput.includes(salidinput)) {
                                document.getElementById("inputDatos" + ide).value = entrainput.replace(salidinput, "");
                            } else {
                                document.getElementById("inputDatos" + ide).value += salidinput;

                            }
                            var resul = document.getElementById("inputDatos" + ide).value;
                            var result = document.getElementById("resultados").value;
                            var datarep = "[xx" + idReg2 + "xx]" + resul + salidinput;
                            var finalData = "[" + idReg + "]///" + datasalida + "---" + "[xx" + idReg2 + "xx]///" + entrainput;
                            if (result.includes(datarep)) {
                                document.getElementById("resultados").value = result.replace(finalData, "");
                            } else {
                                document.getElementById("resultados").value += finalData + salidinput;
                            }
                            document.getElementById("Ventana12").style.display = "block";
                            document.getElementById("SelecId").value = salidinput;
                        } else {
                            mostrarAlerta();
                        }
                    }
                    function mostrarAlerta() {
                        iziToast.warning({
                            title: 'Sin rollos seleccionados!',
                            message: 'Debe seleccionar como mínimo un rollo.',
                            position: 'bottomRight'
                        });
                    }
                </script>
                <script type="text/javascript">
                    function QuitarConfirmacion() {
                        document.getElementById("Ventana12").style.display = "none";
                        document.getElementById("resultados").value = "";
                        var inputElements = document.getElementsByName("select");
                        for (var i = 0; i < inputElements.length; i++) {
                            inputElements[i].checked = false;
                        }
                    }
                </script>

            </div>
        </div>
        <!-- Tables -->
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-sweetalert.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>

        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
