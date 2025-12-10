<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Complemento.tld" prefix="Complemento"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/PMP_MI.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Complementos</title>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Complemento.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Complemento.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <script type="text/javascript">
                    function DesactivarTipoEquipo(id_tipo_equipo) {
                        swal({
                            title: "Inactivar Tipo?",
                            text: "Seguro que desea desactivar el tipo de equipo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=2&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=0';
                                });
                    }
                    function ActivarTipoEquipo(id_tipo_equipo) {
                        swal({
                            title: "Activar Tipo?",
                            text: "Seguro que desea activar el tipo de equipo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=2&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=1';
                                });
                    }
                    function DesactivarActividad(id_actividad, id_tipo_equipo) {
                        swal({
                            title: "Inactivar Actividad?",
                            text: "Seguro que desea desactivar la actividad...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=5&Id_actividad=' + id_actividad + '&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=0';
                                });
                    }
                    function ActivarActividad(id_actividad, id_tipo_equipo) {
                        swal({
                            title: "Activar Actividad?",
                            text: "Seguro que desea activar la actividad...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=5&Id_actividad=' + id_actividad + '&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=1';
                                });
                    }
                    function DesactivarParametro(id_parametro, id_tipo_equipo) {
                        swal({
                            title: "Inactivar Parametro?",
                            text: "Seguro que desea desactivar el parámetro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=6&Id_parametro=' + id_parametro + '&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=0';
                                });
                    }
                    function ActivarParametro(id_parametro, id_tipo_equipo) {
                        swal({
                            title: "Activar Parametro?",
                            text: "Seguro que desea activar el parámetro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=6&Id_parametro=' + id_parametro + '&Id_tipo_equipo=' + id_tipo_equipo + '&Estado=1';
                                });
                    }
                    function DesactivarUnidad(id_unidad) {
                        swal({
                            title: "Desactivar Unidad?",
                            text: "Seguro que desea desactivar la unidad de medida...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=14&Id_unidad=' + id_unidad + '&Estado=0';
                                });
                    }
                    function ActivarUnidad(id_unidad) {
                        swal({
                            title: "Activar Unidad?",
                            text: "Seguro que desea activar la unidad de medida...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=14&Id_unidad=' + id_unidad + '&Estado=1';
                                });
                    }
                    function DesactivarInstrumento(id_instrumento) {
                        swal({
                            title: "Desactivar Instrumento?",
                            text: "Seguro que desea desactivar el instrumento...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=15&Id_instrumento=' + id_instrumento + '&Estado=0';
                                });
                    }
                    function ActivarInstrumento(id_instrumento) {
                        swal({
                            title: "Activar Instrumento?",
                            text: "Seguro que desea activar el instrumento...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=15&Id_instrumento=' + id_instrumento + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function PostBackParametro() {
                        var Tipo_parametro = document.getElementById("Cbx_tipo_parametro");
                        document.forms['FormParametro'].submit();
                    }
                </script>
                <script type="text/javascript">
                    function PostBackCategoria() {
                        var Tipo_parametro = document.getElementById("Cbx_tipo_categoria");
                        document.forms['FormCategoria'].submit();
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Complemento:Complemento />
        </div>
        <Alertas:Alertas />
    </body>
</html>