<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Usuario.tld" prefix="usuario" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <script type="text/javascript">
                 history.pushState(null, null, 'Usuario.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Usuario.jsp');
                });
                function DesactivarUsuario(idUsuario) {
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
                                location.href = 'Usuario?opc=4&idUsuario=' + idUsuario + '';
                            });
                }
                function ActivarUsuario(id_usuario) {
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
                                location.href = 'Usuario?opc=5&idUsuario=' + id_usuario + '';
                            });
                }
                
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:Menu/>
            <alertas:Alertas/>
            <usuario:Usuario/>
        </div>
    </body>
</html>
