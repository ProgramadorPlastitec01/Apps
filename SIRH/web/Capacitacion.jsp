<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/tlds/Capacitacion.tld" prefix="Capacitaciones" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>

            <script>
                function activeShield(val, cont) {
                    if (val == 1) {
                        document.getElementById(cont).style.display = "block";
                    } else if (val == 2) {
                        document.getElementById(cont).style.display = "none";
                    }
                }
            </script>
            <script>
                function ActiveRadioData(val, cont) {
//                    document.getElementById("btnAct").style.display = "block";
                    if (val == 1) {
                        document.getElementById(cont).style.display = "block";
                    } else if (val == 2) {
                        document.getElementById(cont).style.display = "none";
                    }
                    validateForm();
                }
                function validateForm() {
                    const column1 = document.querySelector('input[name="Txt_TypeAC"]:checked');
                    const column2 = document.querySelector('input[name="Txt_Dirg"]:checked');
                    const column3 = document.querySelector('input[name="Txt_alca"]:checked');
                    const column4 = document.querySelector('input[name="Txt_metod"]:checked');
                    const column5 = document.querySelector('input[name="Txt_eva"]:checked');
                    const updateButton = document.getElementById('updateButton');

                    if (column1 && column2 && column3 && column4 && column5) {
                        updateButton.style.display = "block";
                    } else {
                        updateButton.style.display = "none";
                    }
                }

                function validateBeforeSubmit() {
                    const column1 = document.querySelector('input[name="Txt_TypeAC"]:checked');
                    const column2 = document.querySelector('input[name="Txt_Dirg"]:checked');
                    const column3 = document.querySelector('input[name="Txt_alca"]:checked');
                    const column4 = document.querySelector('input[name="Txt_metod"]:checked');
                    const column5 = document.querySelector('input[name="Txt_eva"]:checked');

                    if (!column1 || !column2 || !column3 || !column4 || !column5) {
                        alert("Por favor, seleccione una opción en todas las columnas antes de actualizar.");
                        return false;
                    }
                    return true;
                }
            </script>
            <script>
                function toggleCheckboxes(checked) {
                    // Obtener todos los checkboxes dentro de la tabla
                    var checkboxes = document.querySelectorAll('.exam-checkbox');
                    checkboxes.forEach(function (checkbox) {
                        checkbox.checked = checked;
                    });
                    updateHiddenField();
                }

                function updateHiddenField() {
                    var checkboxes = document.querySelectorAll('.exam-checkbox');
                    var selectedIds = [];
                    checkboxes.forEach(function (checkbox) {
                        if (checkbox.checked) {
                            selectedIds.push('[' + checkbox.id + ']');
                        }
                    });
                    document.getElementById('selectedIds').value = selectedIds.join('');
                    var actionButton = document.getElementById('actionButton');
                    var actionButton2 = document.getElementById('actionButton2');
                    if (selectedIds.length > 0) {
                        actionButton.style.display = 'block';
                        actionButton2.style.display = 'block';
                    } else {
                        actionButton.style.display = 'none';
                        actionButton2.style.display = 'none';
                    }
                }
            </script>
            <script>
                function validForm(nra) {
                    document.getElementById("validac").value = nra;
                    document.getElementById("FormEvalu").submit();
                }
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
                function PassData(idDetalle, idCod, val, valShel) {
                    document.getElementById(valShel).value = val;
                    document.getElementById("Id_valdCod").value = idCod;
                    document.getElementById("Id_valId").value = idDetalle;
                }
            </script>
            <script>
                function CompareData(ValInit, ValParc) {
                    var init = document.getElementById(ValInit).value;
                    var parc = document.getElementById(ValParc).value;
                    if (parc !== init) {
                        document.getElementById("NonCoin").style.display = "block";
                        document.getElementById("ButtonConsul").disabled = true;
                    } else if (parc == init) {
                        document.getElementById("NonCoin").style.display = "none";
                        document.getElementById("ButtonConsul").disabled = false;

                    }
                }
            </script>
            <script>
                function AlertaFirmas() {
                    swal({
                        title: "Alerta",
                        text: "No se puede cerrar ya que falta personal por firmar. ",
                        type: "info",
                        confirmButtonColor: "cian",
                        confirmButtonText: "De acuerdo",
                    });
                }
                function AlertaEvaluacion() {
                    swal({
                        title: "Alerta",
                        text: "No se puede cerrar ya que falta personal por evaluar. ",
                        type: "info",
                        confirmButtonColor: "cian",
                        confirmButtonText: "De acuerdo",
                    });
                }
                function AlertaAmbasData() {
                    swal({
                        title: "Alerta",
                        text: "No se puede cerrar ya que falta personal por firmar y evaluar. ",
                        type: "info",
                        confirmButtonColor: "cian",
                        confirmButtonText: "De acuerdo",
                    });
                }
                function DesactivarCapacitacion(id_capacitacion) {
                    swal({
                        title: "Cerrar Capacitacion",
                        text: "<form action='Capacitacion?opc=25&Id_capacitacion=" + id_capacitacion + "&Estado=1' id='formVerificacion' method='post'><input type='text' name='NroFolio' placeholder='Numero de folio' style='display: block;'></form><button onclick='window.location.href=\"Capacitacion.jsp\"'>Cancelar</button><button type='submit' required form='formVerificacion'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        showCancelButton: false,
                        html: true,
                    });
                }
                function EliminarCapacitacion(id_capacitacion) {
                    swal({
                        title: "Eliminar Capacitación",
                        text: "Seguro que desea eliminar la Capacitación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Capacitacion?opc=25&Id_capacitacion=' + id_capacitacion + '&Estado=2';
                            });
                }
                function ActivarCapacitacion(id_capacitacion) {
                    swal({
                        title: "Abrir Capacitación",
                        text: "Seguro que desea abrir la capacitación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Capacitacion?opc=25&Id_capacitacion=' + id_capacitacion + '&Estado=0';
                            });
                }
            </script>

        </head>
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container" >
            <div id="page"  style="margin-top: 50px;overflow-y:auto;padding: 18px;">
                <Alertas:Alertas />
                <Capacitaciones:Capacitacion />
                <script src="Interfaz/Calendarios/Js_range.js"></script>
                <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
                <script src="Interfaz/Calendarios/Js_normal.js"></script>
                <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
                <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
                <script src="Interfaz/Firma/assets/bezier.js"></script>
                <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
                <script src="Interfaz/Firma/assets/json2.min.js"></script>
            </div>
        </div>
    </body>
</html>
