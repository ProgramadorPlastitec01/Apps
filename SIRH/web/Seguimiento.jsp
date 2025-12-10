<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@taglib uri="/WEB-INF/tlds/Seguimiento.tld" prefix="Seguimiento"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'Seguimiento.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Seguimiento.jsp');
                    });
                </script>-->
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
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
                function validForm(nra) {
                    document.getElementById("validac").value = nra;
                    document.getElementById("FormEvalu").submit();
                }
            </script>
            <script type="text/javascript">
                function Seguimiento_observaciones() {
                    var htmleditor = document.getElementsByName("HTML_Editor").innerHTML;
                    document.getElementsByName("Txt_descripcion").value = htmleditor;
                    document.Form_seguimiento.submit();
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
                function CompareCode(ValInit, ValParc) {
                    var init = document.getElementById(ValInit).value;
                    var parc = document.getElementById(ValParc).value;
                    if (parc !== init) {
                        document.getElementById("NonCoinCod").style.display = "block";
                        document.getElementById("ButtonConsul").disabled = true;
                    } else if (parc == init) {
                        document.getElementById("NonCoinCod").style.display = "none";
                        document.getElementById("ButtonConsul").disabled = false;

                    }
                }
            </script>
            <script type = "text/javascript" >
                function SeleccionExamenes(iex) {
                    if (iex.checked) {
                        document.getElementById('Txt_examenes').value += "" + iex.value;
                    } else {
                        document.getElementById("Txt_examenes").value = document.getElementById("Txt_examenes").value.replace(iex.value, "");
                    }
                }
            </script>
            <script>
                function CleanForm(Myform) {
                    document.getElementById(Myform).reset();
                    document.getElementById("NonCoin").style.display = "none";
                }
            </script>
            <script>
                function Habilitar_incapacidad(Incapacidad) {
                    if (Incapacidad == 1) {
                        document.getElementById("Txt_incapacidad").value = "0";
                        document.getElementById("Txt_incapacidad").readonly = true;
                    } else if (Incapacidad == 0) {
                        document.getElementById("Txt_incapacidad").value = "0";
                        document.getElementById("Txt_incapacidad").readonly = false;
                    }
                }
            </script> 
            <script>
                function Costo_empleado_dia_horas() {
                    var dias = document.getElementById("Txt_hora").value;
                    var valor_hora = document.getElementById("Txt_salario_hora").value;
                    var resul = parseFloat(valor_hora) * parseFloat(dias * 8);
                    document.getElementById("Label_costo_empleado").innerHTML = resul.toFixed(2);
                }
            </script> 
            <script>
                function Costo_empleado_horas() {
                    var horas = document.getElementById("Txt_hora").value;
                    var valor_hora = document.getElementById("Txt_salario_hora").value;
                    var resul = parseFloat(valor_hora) * parseFloat(horas);
                    document.getElementById("Label_costo_empleado").innerHTML = resul.toFixed(2);
                }
            </script> 
            <script>
                function Costo_empleado_horas_minutos() {
                    var horas = document.getElementById("Txt_hora").value;
                    var minutos = document.getElementById("Txt_minutos").value;
                    var valor_hora = document.getElementById("Txt_salario_hora").value;
                    var resul_hora = parseFloat(valor_hora) * parseFloat(horas);
                    var valor_minuto = valor_hora / 60;
                    var resul_minutos = parseFloat(valor_minuto) * parseFloat(minutos);
                    document.getElementById("Label_costo_empleado").innerHTML = (resul_hora + resul_minutos).toFixed(2);
                }
            </script> 
            <script>
                function Empleado_seleccionado(Empleado) {
                    var Empleado_result = document.getElementById(Empleado.split(' / ')[1]).dataset.value;
                    var dcm = Empleado_result.split(' / ')[1];
                    var epa = Empleado_result.split(' / ')[0];
                    var ara = Empleado_result.split(' / ')[2];
                    var cgo = Empleado_result.split(' / ')[3];
                    var slr = Empleado_result.split(' / ')[4];
                    document.getElementById("Txt_manual").value = "";
                    document.getElementById("Label_nombre").innerHTML = epa;
                    document.getElementById("Label_documento").innerHTML = dcm;
                    document.getElementById("Img_foto").src = "Fotos/" + dcm + ".jpg";
                    document.getElementById("Label_area").innerHTML = ara;
                    document.getElementById("Label_cargo").innerHTML = cgo;
                    document.getElementById("Txt_documento").value = dcm;
                    var result = ((slr / 30) / 8);
                    result = parseFloat(result);
                    result = Math.round(result * 100) / 100;
                    document.getElementById("Txt_salario_hora").value = result;
                    document.getElementById("Label_salario").innerHTML = result;
                }
            </script> 
            <script>
                function Asignar_li()
                {
                    var dotacion = document.getElementById("Txt_dotacion").value;
                    var cantidad = document.getElementById("Txt_cantidad").value;
                    if (cantidad > 0) {
                        document.getElementById("Txt_asignacion_dotacion").value += "[" + dotacion + " / " + cantidad + "]";
                        var adicion = dotacion + " / " + cantidad;
                        if (adicion.length > 0)
                        {
                            if (Encontrar_li(adicion))
                            {
                                var li = document.createElement('li');
                                li.id = adicion;
                                li.style.fontSize = "14px";
                                li.innerHTML = "<span onclick='Eliminar_li(this)' class='fa fa-trash-alt fa-size_super_small'></span> " + adicion;
                                document.getElementById("lst_asiganacion").appendChild(li);
                            }
                        }
                        document.getElementById("Btn_asignar_dotacion").style.display = "block";
                        document.getElementById("Txt_dotacion").value = "";
                        document.getElementById("Txt_cantidad").value = 0;
                        return false;
                    }
                }
                function Encontrar_li(contenido)
                {
                    var el = document.getElementById("lst_asiganacion").getElementsByTagName("li");
                    for (var i = 0; i < el.length; i++)
                    {
                        if (el[i].innerHTML == contenido)
                            return false;
                    }
                    return true;
                }
                function Eliminar_li(elemento)
                {
                    var id = elemento.parentNode.getAttribute("id");
                    id = "[" + id + "]";
                    document.getElementById("Txt_asignacion_dotacion").value = document.getElementById("Txt_asignacion_dotacion").value.replace(id, "");
                    if (document.getElementById("Txt_asignacion_dotacion").value == "") {
                        document.getElementById("Btn_asignar_dotacion").style.display = "none";
                    }
                    node = document.getElementById(id.replace("]", "").replace("[", ""));
                    node.parentNode.removeChild(node);
                }
                function Refrescar_asignar()
                {
                    document.getElementById("Txt_dotacion").value = "";
                    document.getElementById("Txt_cantidad").value = 0;
                }
            </script>
            <script type="text/javascript">
                function DesactivarAccidente(id_accidente) {
                    swal({
                        title: "Cerrar Accidente",
                        text: "Seguro que desea cerrar el accidente...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=3&Id_accidente=' + id_accidente + '&Estado=1';
                            });
                }
                function EliminarAccidente(id_accidente) {
                    swal({
                        title: "Eliminar Accidente",
                        text: "Seguro que desea eliminar el accidente...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=3&Id_accidente=' + id_accidente + '&Estado=2';
                            });
                }
                function ActivarAccidente(id_accidente) {
                    swal({
                        title: "Abrir Accidente",
                        text: "Seguro que desea abrir el accidente...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=3&Id_accidente=' + id_accidente + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarEnfermedad(id_enfermedad) {
                    swal({
                        title: "Cerrar Enfermedad",
                        text: "Seguro que desea cerrar la enfermedad...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=6&Id_enfermedad=' + id_enfermedad + '&Estado=1';
                            });
                }
                function EliminarEnfermedad(id_enfermedad) {
                    swal({
                        title: "Eliminar Enfermedad",
                        text: "Seguro que desea eliminar la enfermedad...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=6&Id_enfermedad=' + id_enfermedad + '&Estado=2';
                            });
                }
                function ActivarEnfermedad(id_enfermedad) {
                    swal({
                        title: "Abrir Enfermedad",
                        text: "Seguro que desea abrir la enfermedad...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=6&Id_enfermedad=' + id_enfermedad + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarIncapacidad(id_incapacidad) {
                    swal({
                        title: "Cerrar Incapacidad",
                        text: "Seguro que desea cerrar la incapacidad...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=9&Id_incapacidad=' + id_incapacidad + '&Estado=1';
                            });
                }
                function EliminarIncapacidad(id_incapacidad) {
                    swal({
                        title: "Eliminar Incapacidad",
                        text: "Seguro que desea eliminar la incapacidad...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=9&Id_incapacidad=' + id_incapacidad + '&Estado=2';
                            });
                }
                function ActivarIncapacidad(id_incapacidad) {
                    swal({
                        title: "Abrir Incapacidad",
                        text: "Seguro que desea abrir la incapacidad...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=9&Id_incapacidad=' + id_incapacidad + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarAusencia(id_ausencia) {
                    swal({
                        title: "Cerrar Ausencia",
                        text: "Seguro que desea cerrar la Ausencia...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=12&Id_ausencia=' + id_ausencia + '&Estado=1';
                            });
                }
                function EliminarAusencia(id_ausencia) {
                    swal({
                        title: "Eliminar Ausencia",
                        text: "Seguro que desea eliminar la Ausencia...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=12&Id_ausencia=' + id_ausencia + '&Estado=2';
                            });
                }
                function ActivarAusencia(id_ausencia) {
                    swal({
                        title: "Abrir Ausencia",
                        text: "Seguro que desea abrir la Ausencia...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=12&Id_ausencia=' + id_ausencia + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
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
                function AlertaDiciplina() {
                    swal({
                        title: "Alerta",
                        text: "El registro de la ausencia es replica del modulo de disciplina / descargos, para realizar cambios ir a ese modulo.",
                        type: "info",
                        confirmButtonColor: "cian",
                        confirmButtonText: "De acuerdo",
                    });
                }
                function DesactivarDisciplina(id_disciplina) {
                    swal({
                        title: "Cerrar Disciplina",
                        text: "Seguro que desea cerrar la disciplina...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=15&Id_disciplina=' + id_disciplina + '&Estado=1';
                            });
                }
                function EliminarDisciplina(id_disciplina) {
                    swal({
                        title: "Eliminar Disciplina",
                        text: "Seguro que desea eliminar la disciplina...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=15&Id_disciplina=' + id_disciplina + '&Estado=2';
                            });
                }
                function ActivarDisciplina(id_disciplina) {
                    swal({
                        title: "Abrir Disciplina",
                        text: "Seguro que desea abrir la disciplina...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=15&Id_disciplina=' + id_disciplina + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarRetiro(id_retiro) {
                    swal({
                        title: "Cerrar Retiro",
                        text: "Seguro que desea cerrar el retiro?\nAl realizar esta operación ya no se permite modificar y se procedera con inactivar del personal.",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=18&Id_retiro=' + id_retiro + '&Estado=1';
                            });
                }
                function EliminarRetiro(id_retiro) {
                    swal({
                        title: "Eliminar Retiro",
                        text: "Seguro que desea eliminar el retiro...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=18&Id_retiro=' + id_retiro + '&Estado=2';
                            });
                }
                function ActivarRetiro(id_retiro) {
                    swal({
                        title: "Abrir Retiro",
                        text: "Seguro que desea abrir el retiro...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=18&Id_retiro=' + id_retiro + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarDotacion(id_dotacion) {
                    swal({
                        title: "Cerrar Dotación",
                        text: "Seguro que desea cerrar la dotación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=21&Id_dotacion=' + id_dotacion + '&Estado=1';
                            });
                }
                function EliminarDotacion(id_dotacion) {
                    swal({
                        title: "Eliminar Dotación",
                        text: "Seguro que desea eliminar la dotación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=21&Id_dotacion=' + id_dotacion + '&Estado=2';
                            });
                }
                function ActivarDotacion(id_dotacion) {
                    swal({
                        title: "Abrir Dotación",
                        text: "Seguro que desea abrir la dotación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=21&Id_dotacion=' + id_dotacion + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarEpp(id_epp) {
                    swal({
                        title: "Cerrar EPP",
                        text: "Seguro que desea cerrar la asignación de EPP...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=32&Id_epp=' + id_epp + '&Estado=1';
                            });
                }
                function EliminarEpp(id_epp) {
                    swal({
                        title: "Eliminar EPP",
                        text: "Seguro que desea eliminar la asignación de EPP...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=32&Id_epp=' + id_epp + '&Estado=2';
                            });
                }
                function ActivarEpp(id_epp) {
                    swal({
                        title: "Abrir EPP",
                        text: "Seguro que desea abrir la asignación de EPP...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=32&Id_epp=' + id_epp + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarCapacitacion(id_capacitacion) {
                    swal({
                        title: "Cerrar Capacitacion",
                        text: "<form action='Seguimiento?opc=25&Id_capacitacion=" + id_capacitacion + "&Estado=1' id='formVerificacion' method='post'><input type='text' name='NroFolio' placeholder='Numero de folio' style='display: block;'></form><button type='submit' required form='formVerificacion'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        showCancelButton: true,
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
                                location.href = 'Seguimiento?opc=25&Id_capacitacion=' + id_capacitacion + '&Estado=2';
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
                                location.href = 'Seguimiento?opc=25&Id_capacitacion=' + id_capacitacion + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarExamen(id_examen) {
                    swal({
                        title: "Cerrar Examen",
                        text: "Seguro que desea cerrar el examen...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=29&Id_examen=' + id_examen + '&Estado=1';
                            });
                }
                function EliminarExamen(id_examen) {
                    swal({
                        title: "Eliminar Examen",
                        text: "Seguro que desea eliminar el examen...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=29&Id_examen=' + id_examen + '&Estado=2';
                            });
                }
                function ActivarExamen(id_examen) {
                    swal({
                        title: "Abrir Examen",
                        text: "Seguro que desea abrir el examen...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Seguimiento?opc=29&Id_examen=' + id_examen + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function ValDiasMarcacion() {
                    var dia_ini = parseInt(document.getElementById("Txt_dia_inicial").value);
                    var dia_fin = parseInt(document.getElementById("Txt_dia_final").value);
                    if (dia_ini > dia_fin) {
                        document.getElementById("Txt_dia_inicial").value = dia_fin;
                    } else if (dia_fin < dia_ini) {
                        document.getElementById("Txt_dia_final").value = dia_ini;
                    }
                }
                function ModMarcacion(dcm, icg, anio, mes, dia, fin, ffn) {
                    document.getElementById("Modificar_marcacion").style.display = "block";
                    document.getElementById("dcm").value = dcm;
                    document.getElementById("Title_dcm").innerHTML = dcm;
                    document.getElementById("icg").value = icg;
                    document.getElementById("anio").value = anio;
                    document.getElementById("Title_anio").innerHTML = anio;
                    document.getElementById("mes").value = mes;
                    document.getElementById("Title_mes").innerHTML = mes;
                    document.getElementById("dia").value = dia;
                    document.getElementById("Title_dia").innerHTML = dia;
                    if (fin === "NO") {
                        document.getElementById("start").value = "";
                        document.getElementById("hin").value = "";
                    } else {
                        document.getElementById("start").value = fin.split(" ")[0];
                        var arg_hora_ini = fin.split(" ")[1];
                        document.getElementById("hin").value = arg_hora_ini.split(":")[0] + ":" + arg_hora_ini.split(":")[1];
                    }
                    if (ffn === "NO") {
                        document.getElementById("end").value = "";
                        document.getElementById("hfn").value = "";
                    } else {
                        document.getElementById("end").value = ffn.split(" ")[0];
                        var arg_hora_fin = ffn.split(" ")[1];
                        document.getElementById("hfn").value = arg_hora_fin.split(":")[0] + ":" + arg_hora_fin.split(":")[1];
                    }
                }
                function CerrarModMarcacion() {
                    document.getElementById("Modificar_marcacion").style.display = "none";
                }
            </script>

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

        </head>
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container">
            <div id="page">
                <Alertas:Alertas />
                <Seguimiento:Seguimiento />
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
        </div>
    </body>
</html>
