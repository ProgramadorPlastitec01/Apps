<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Proveedor.tld" prefix="proveedor" %>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <script type="text/javascript">
                history.pushState(null, null, 'Proveedor.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Proveedor.jsp');
                });
                function ActivarProveedor(idProveedor) {
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
                                location.href = 'Proveedor?opc=5&idProveedor=' + idProveedor + '';
                            });
                }
                function DesactivarProveedor(idProveedor) {
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
                                location.href = 'Proveedor?opc=4&idProveedor=' + idProveedor + '';
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
            <proveedor:Proveedor/>
        </div>
    </body>
</html>
