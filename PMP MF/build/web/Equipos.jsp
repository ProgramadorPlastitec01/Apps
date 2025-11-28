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
                    swal({
                        title: "Inactivar Equipo",
                        text: "Seguro que desea desactivar el Equipo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Equipo?opc=3&Id_equipo=' + id_equipo + '&Estado=0';
                            });
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
