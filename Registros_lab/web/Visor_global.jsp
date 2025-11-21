<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Visor_global.tld" prefix="Visore_global"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Visor_global</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Visor_global.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Visor_global.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <!--Eliminar y liberar despejes-->
            <script language="javascript">
                function EliminarDespeje() {
                    swal({
                        title: "Eliminar Despeje",
                        text: "Seguro de eliminar el registro de despeje para el turno...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormDeleteDespeje').submit();
                            });
                }
                function LiberarDespeje() {
                    swal({
                        title: "Liberar Despeje",
                        text: "Seguro de liberar el registro de despeje para el turno...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormFreeDespeje').submit();
                            });
                }
                function DespejeObservaciones() {
                    swal({
                        title: "Observaciones Despeje",
                        text: "Seguro de habilitar en el registro de despeje las observaciones para el turno...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormObservacionesDespeje').submit();
                            });
                }
                function FirmarDespeje() {
                    swal({
                        title: "Firmar Despeje",
                        text: "Seguro de firmar el registro de despeje...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormFirmarDespeje').submit();
                            });
                }
                function HabilitarDespejeLiberado(consecutivo) {
                    swal({
                        title: "Habilitar Despeje",
                        text: "Seguro de habilitar el registro de despeje para editarlo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormDevolverDespeje_' + consecutivo).submit();
                            });
                }
            </script>
        </head>
        <body id="subpage">
        <Visore_global:Visor_global />
        <script>
            $('.jqte-test').jqte();
            // settings of status
            var jqteStatus = true;
            $(".status").click(function ()
            {
                jqteStatus = jqteStatus ? false : true;
                $('.jqte-test').jqte({"status": jqteStatus})
            });
        </script>
        <script src="Interfaz/Acordeon/Js_accordeon.js" type="text/javascript"></script>
        <Alertas:Alertas />
    </body>
</html>