<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Complemento.tld" prefix="Complemento"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/CVP.ico" rel="icon" >
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
                    function DesactivarArea(id_area) {
                        swal({
                            title: "Inactivar Area",
                            text: "Seguro que desea desactivar la area...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=2&Id_area=' + id_area + '&Estado=0';
                                });
                    }
                    function ActivarArea(id_area) {
                        swal({
                            title: "Activar Area",
                            text: "Seguro que desea activar la area...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=2&Id_area=' + id_area + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarTipoCalificacion(id_tipo) {
                        swal({
                            title: "Inactivar Tipo",
                            text: "Seguro que desea desactivar el tipo de calificacion...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=5&Id_tipo_calificacion=' + id_tipo + '&Estado=0';
                                });
                    }
                    function ActivarTipoCalificacion(id_tipo) {
                        swal({
                            title: "Activar Tipo",
                            text: "Seguro que desea activar el tipo de calificacion...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=5&Id_tipo_calificacion=' + id_tipo + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarTipoInforme(id_tipo) {
                        swal({
                            title: "Inactivar Tipo",
                            text: "Seguro que desea desactivar el tipo de informe...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=11&Id_tipo_informe=' + id_tipo + '&Estado=0';
                                });
                    }
                    function ActivarTipoInforme(id_tipo) {
                        swal({
                            title: "Activar Tipo",
                            text: "Seguro que desea activar el tipo de informe...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=11&Id_tipo_informe=' + id_tipo + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarGrupo(id_grupo) {
                        swal({
                            title: "Inactivar Grupo",
                            text: "Seguro que desea desactivar el grupo / subgrupo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=8&Id_grupo=' + id_grupo + '&Estado=0';
                                });
                    }
                    function ActivarGrupo(id_grupo) {
                        swal({
                            title: "Activar Grupo",
                            text: "Seguro que desea activar el grupo / subgrupo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=8&Id_grupo=' + id_grupo + '&Estado=1';
                                });
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Complemento:Complemento />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>