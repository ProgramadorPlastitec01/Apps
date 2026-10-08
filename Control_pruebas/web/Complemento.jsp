<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Complemento.tld" prefix="Complemento"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_pruebas.ico" rel="icon" >
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
                    function Nueva_variable() {
                        var new_variable = document.getElementById("Txt_new_variable").value;
                        location.href = 'Complemento?opc=10&Txt_new_variable=' + new_variable;
                    }
                    function DesactivarLinea(id_linea) {
                        swal({
                            title: "Inactivar Linea",
                            text: "Seguro que desea desactivar la línea...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=3&Id_linea=' + id_linea + '&Estado=0';
                                });
                    }
                    function ActivarLinea(id_linea) {
                        swal({
                            title: "Activar Linea",
                            text: "Seguro que desea activar la línea...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=3&Id_linea=' + id_linea + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarPrueba(id_prueba) {
                        swal({
                            title: "Inactivar Prueba",
                            text: "Seguro que desea desactivar la prueba...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=6&Id_prueba=' + id_prueba + '&Estado=0';
                                });
                    }
                    function ActivarPrueba(id_prueba) {
                        swal({
                            title: "Activar Prueba",
                            text: "Seguro que desea activar la prueba...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=6&Id_prueba=' + id_prueba + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarProceso(id_proceso) {
                        swal({
                            title: "Inactivar Proceso",
                            text: "Seguro que desea desactivar el proceso...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=9&Id_proceso=' + id_proceso + '&Estado=0';
                                });
                    }
                    function ActivarProceso(id_proceso) {
                        swal({
                            title: "Activar Proceso",
                            text: "Seguro que desea activar el proceso...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=9&Id_proceso=' + id_proceso + '&Estado=1';
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
    </body>
</html>