<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Ubicacion.tld" prefix="ubicacion" %>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <script type="text/javascript">
                history.pushState(null, null, 'Ubicación.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Ubicación.jsp');
                });
                function DesactivarUbicacion(idUbicacion) {
                    swal({
                        title: "Desactivar!",
                        text: "Seguro que desea cambiar de Estado?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = 'Ubicacion?opc=4&idUbicacion=' + idUbicacion + '';
                            });
                }
                function ActivarUbicacion(idUbicacion) {
                    swal({
                        title: "Activar!",
                        text: "Seguro que desea cambiar de Estado?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = 'Ubicacion?opc=5&idUbicacion=' + idUbicacion + '';
                            });
                }
                function Filtrartodo() {
                    Filtrar();
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:Menu/>
            <alertas:Alertas/>
            <ubicacion:Ubicacion/>
        </div>
    </body>
</html>
