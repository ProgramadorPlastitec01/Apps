<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/Tlds/Equipos.tld" prefix="Equipos" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Equipos.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script type="text/javascript">
                function DesactivarEquipo(id_equipo) {
                    // La inactivacion exige asunto y descripcion; se registran como nota del equipo
                    document.getElementById('Id_equipo_inactivacion').value = id_equipo;
                    document.getElementById('Txt_asunto_inactivacion').value = '';
                    document.getElementById('Txt_descripcion_inactivacion').value = '';
                    $('#InactivarEquipoModal').modal('show');
                }
                function ConfirmarInactivacionEquipo() {
                    var asunto = document.getElementById('Txt_asunto_inactivacion').value.trim();
                    var descripcion = document.getElementById('Txt_descripcion_inactivacion').value.trim();
                    if (asunto === '' || descripcion === '') {
                        swal('Datos incompletos', 'Diligencie el asunto y la descripción del motivo de la inactivación.', 'warning');
                        return;
                    }
                    document.getElementById('FormInactivarEquipo').submit();
                }
                function ActivarEquipo(id_equipo) {
                    swal({
                        title: "Activar Equipo",
                        text: "Seguro que desea activar el Equipo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Equipo?opc=3&Id_equipo=' + id_equipo + '&Estado=1';
                            });
                }
                function ActualizarFechasOld(id_equipo, nombre_equipo, estado_mtto, registros, fechas) {
                    document.getElementById("Txt_nombre_equipo_old").value = nombre_equipo;
                    document.getElementById("Id_equipo_old").value = id_equipo;
                    var arg_registros = registros.split(",");
                    var arg_fechas = fechas.split(",");
                    if (estado_mtto === '1') {
                        document.getElementById("datepicker_mm").readOnly = false;
                        document.getElementById("datepicker_insp").readOnly = false;
                        document.getElementById("datepicker_lub").readOnly = true;
                    } else {
                        document.getElementById("datepicker_mm").readOnly = true;
                        document.getElementById("datepicker_insp").readOnly = true;
                        document.getElementById("datepicker_lub").readOnly = false;
                    }
                    if (arg_registros[0] === 'N/A') {
                        document.getElementById("datepicker_mm").value = "N/A";
                    } else {
                        document.getElementById("datepicker_mm").value = arg_fechas[0];
                    }
                    if (arg_registros[1] === 'N/A') {
                        document.getElementById("datepicker_insp").value = "N/A";
                    } else {
                        document.getElementById("datepicker_insp").value = arg_fechas[1];
                    }
                    if (arg_registros[2] === 'N/A') {
                        document.getElementById("datepicker_lub").value = "N/A";
                    } else {
                        document.getElementById("datepicker_lub").value = arg_fechas[2];
                    }
                }
                function RegistroFichaTecnica(id_equipo, tipo) {
                    swal({
                        title: "Registrar datos",
                        text: "Se registra nuevo item!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Equipo?opc=7&Id_equipo=' + id_equipo + '&Txt_tipo=' + tipo + '';
                            });
                }
                function ConcatUnidadMedida() {
                    var unidad_1 = document.getElementById("Txt_unidad_medida1").value;
                    var unidad_2 = document.getElementById("Txt_unidad_medida2").value;
                    var unidad_3 = document.getElementById("Txt_unidad_medida3").value;
                    var unidad_4 = document.getElementById("Txt_unidad_medida4").value;
                    document.getElementById("Txt_unidad_medida").value = unidad_1 + "//" + unidad_2 + "//" + unidad_3 + "//" + unidad_4;
                }
                function Inactivar(id_equipo, id_nota) {
                    swal({
                        title: "Eliminar!",
                        text: "¿Seguro que desea enviar a Eliminar la nota?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#106eff",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Equipo?opc=12&Id_equipo=" + id_equipo + "&id_nota=" + id_nota;
                            });
                }
            </script>
            <script>
                function CambiarListadoEquipos(temp) {
                    location.href = "Equipo?opc=1&temp=" + temp + "";
                }
            </script>
            <script>
                function ToggleEstadoEquipos(btn, temp) {
                    var estado = btn.getAttribute('data-estado');
                    var nuevoEstado = (estado === '1') ? '0' : '1';
                    if (estado === '1') {
                        btn.setAttribute('data-estado', '0');
                        btn.classList.remove('btn-success');
                        btn.classList.add('btn-secondary');
                        btn.innerHTML = '<i class="fas fa-toggle-off"></i>&nbsp;Inactivo';
                        if (typeof FiltrarEquiposEstado === 'function') {
                            FiltrarEquiposEstado(0);
                        }
                    } else {
                        btn.setAttribute('data-estado', '1');
                        btn.classList.remove('btn-secondary');
                        btn.classList.add('btn-success');
                        btn.innerHTML = '<i class="fas fa-toggle-on"></i>&nbsp;Activo';
                        if (typeof FiltrarEquiposEstado === 'function') {
                            FiltrarEquiposEstado(1);
                        }
                    }
                    location.href = "Equipo?opc=1&estado=" + nuevoEstado + "&temp=" + temp;
                }
            </script>
        </head>
        <body id="page-top">
            <div id="wrapper">
            <Menu:Menu />
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <Menu:Sesion />
                    <div class="container-fluid">
                        <Equipos:Equipos />
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal inactivacion de equipo: asunto y descripcion quedan como nota del equipo -->
        <div class="modal fade" id="InactivarEquipoModal" tabindex="-1" role="dialog" aria-labelledby="InactivarEquipoLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="InactivarEquipoLabel">Inactivar Equipo</h5>
                    </div>
                    <div class="modal-body">
                        <p>Indique el motivo de la inactivaci&oacute;n. Se registrar&aacute; como nota del equipo con la fecha, hora y responsable.</p>
                        <form action="Equipo?opc=3" method="post" id="FormInactivarEquipo">
                            <input type="hidden" name="Id_equipo" id="Id_equipo_inactivacion" value="0" />
                            <input type="hidden" name="Estado" value="0" />
                            <b>Asunto:</b>
                            <input class="form-control" type="text" name="Txt_asunto_inactivacion" id="Txt_asunto_inactivacion" maxlength="180" required />
                            <b>Descripci&oacute;n:</b>
                            <textarea class="form-control" name="Txt_descripcion_inactivacion" id="Txt_descripcion_inactivacion" rows="5" required></textarea>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancelar</button>
                        <button class="btn btn-danger" type="button" onclick="ConfirmarInactivacionEquipo();">Inactivar</button>
                    </div>
                </div>
            </div>
        </div>
        <Alertas:Alertas />
        <!-- Bootstrap core JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery/jquery.min.js"></script>
        <script src="Interfaz/StylePage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="Interfaz/StylePage/js/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
        <script src="Interfaz/StylePage/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="Interfaz/StylePage/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <!-- Page level custom scripts -->
        <script src="Interfaz/StylePage/js/demo/datatables-demo.js"></script>
        <!-- Calendarios -->
        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <!--HTML editor-->
        <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
        <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
    </body>
</html>
