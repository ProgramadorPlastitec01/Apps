<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/Tlds/Complementos.tld" prefix="Complementos" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
<!--        <script type = "text/javascript" >
            history.pushState(null, null, 'Complementos.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>-->
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script type="text/javascript">
                function DesactivarCorreo(id_correo) {
                    swal({
                        title: "Inactivar Correo",
                        text: "Seguro que desea desactivar el Correo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=3&Id_correo=' + id_correo + '&Estado=0';
                            });
                }
                function ActivarCorreo(id_correo) {
                    swal({
                        title: "Activar Correo",
                        text: "Seguro que desea activar el Correo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=3&Id_correo=' + id_correo + '&Estado=1';
                            });
                }
                function CambiarRol(id_correo) {
                    swal({
                        title: "Cambiar rol",
                        text: "Seguro que desea activar cambiar de rol, al realizar esto se cerrara la session!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=3&Id_correo=' + id_correo + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarTipoEquipo(id_tipo_equipo) {
                    swal({
                        title: "Inactivar Tipo Equipo",
                        text: "Seguro que desea desactivar el Tipo Equipo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=6&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=0';
                            });
                }
                function ActivarTipoEquipo(id_tipo_equipo) {
                    swal({
                        title: "Activar CorTipo Equiporeo",
                        text: "Seguro que desea activar el Tipo Equipo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=6&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=1';
                            });
                }
                function TipoRegistroMtto(a) {
                    var tipo_registros = parseFloat(a);
                    if (tipo_registros === 1) {
                        document.getElementById("Txt_codigo_lub").value = 'N/A';
                        document.getElementById("Txt_codigo_lub").disabled = true;
                        document.getElementById("Txt_codigo_insp").disabled = false;
                        document.getElementById("Txt_codigo_mm").disabled = false;
                        document.getElementById("Txt_codigo_mm").value = '0';
                        document.getElementById("Txt_codigo_insp").value = '0';
                    } else {
                        document.getElementById("Txt_codigo_mm").value = 'N/A';
                        document.getElementById("Txt_codigo_mm").disabled = true;
                        document.getElementById("Txt_codigo_insp").value = 'N/A';
                        document.getElementById("Txt_codigo_insp").disabled = true;
                        document.getElementById("Txt_codigo_lub").disabled = false;
                        document.getElementById("Txt_codigo_lub").value = '0';
                    }
                }
            </script>
            <script type="text/javascript">
                function DesactivarParametro(id_tipo_equipo, id_tipo_mtto, id_parametro) {
                    swal({
                        title: "Inactivar Parametro",
                        text: "Seguro que desea desactivar el parametro del Tipo Equipo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=9&Id_tipo_equipo=' + id_tipo_equipo + '&Id_tipo_mtto=' + id_tipo_mtto + '&Id_parametro=' + id_parametro + '&Estado=0';
                            });
                }
                function ActivarParametro(id_tipo_equipo, id_tipo_mtto, id_parametro) {
                    swal({
                        title: "Activar Parametro",
                        text: "Seguro que desea activar el parametro del Tipo Equipo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=9&Id_tipo_equipo=' + id_tipo_equipo + '&Id_tipo_mtto=' + id_tipo_mtto + '&Id_parametro=' + id_parametro + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarParametroZona(id_zona, id_parametro) {
                    swal({
                        title: "Inactivar Parametro",
                        text: "Seguro que desea desactivar el parametro de la Zona...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=18&Id_zona=' + id_zona + '&Id_parametro=' + id_parametro + '&Estado=0';
                            });
                }
                function ActivarParametroZona(id_zona, id_parametro) {
                    swal({
                        title: "Activar Parametro",
                        text: "Seguro que desea activar el parametro de la Zona...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=18&Id_zona=' + id_zona + '&Id_parametro=' + id_parametro + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarParametroGeneral(id_general, id_parametro) {
                    swal({
                        title: "Inactivar Parametro",
                        text: "Seguro que desea desactivar el parametro de la General...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=24&Id_general=' + id_general + '&Id_parametro=' + id_parametro + '&Estado=0';
                            });
                }
                function ActivarParametroGeneral(id_general, id_parametro) {
                    swal({
                        title: "Activar Parametro",
                        text: "Seguro que desea activar el parametro de la General...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=24&Id_general=' + id_general + '&Id_parametro=' + id_parametro + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarLinea(id_linea) {
                    swal({
                        title: "Inactivar Linea",
                        text: "Seguro que desea desactivar la linea...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=12&Id_linea=' + id_linea + '&Estado=0';
                            });
                }
                function ActivarLinea(id_linea) {
                    swal({
                        title: "Activar Linea",
                        text: "Seguro que desea activar la linea...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=12&Id_linea=' + id_linea + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarGeneral(id_general) {
                    swal({
                        title: "Inactivar Actividad General",
                        text: "Seguro que desea desactivar la Actividad General...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=21&Id_general=' + id_general + '&Estado=0';
                            });
                }
                function ActivarGeneral(id_general) {
                    swal({
                        title: "Activar Actividad General",
                        text: "Seguro que desea activar la Actividad General...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=21&Id_general=' + id_general + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarRegistro(id_registro) {
                    swal({
                        title: "Inactivar Registro",
                        text: "Seguro que desea desactivar el registro...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=29&Id_registro=' + id_registro + '&Estado=0';
                            });
                }
                function ActivarRegistro(id_registro) {
                    swal({
                        title: "Activar Registro",
                        text: "Seguro que desea activar el registro...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=29&Id_registro=' + id_registro + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function ConstruirEditor() {
                    document.getElementById("Txt_instruccion_seguridad").value = document.getElementById("Txt_editor_html").innerHTML;
                    document.getElementById("FormInstruccion").submit();
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
                        <Complementos:Complementos/>
                    </div>
                </div>
            </div>
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
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
    </body>
</html>
