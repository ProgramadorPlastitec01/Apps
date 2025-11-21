<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Usuario.tld" prefix="Usuario"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Usuarios</title>
            <!-- CONTROL ENVIO DE PETICIONES -->
            <script type = "text/javascript" >
                history.pushState(null, null, 'Usuarios.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Usuarios.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!-- Alertas controlados-->
                <script type="text/javascript">
                    function DesactivarUsuario(id_usuario) {
                        swal({
                            title: "Inactivar Usuario?",
                            text: "Seguro que desea desactivar el usuario...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Usuario?opc=5&Id_usuario=' + id_usuario + '';
                                });
                    }
                    function ActivarUsuario(id_usuario) {
                        swal({
                            title: "Activar Usuario?",
                            text: "Seguro que desea activar el usuario...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Usuario?opc=6&Id_usuario=' + id_usuario + '';
                                });
                    }
                    function RestablecerPassword(id_usuario) {
                        swal({
                            title: "Restablecer Password",
                            text: "Seguro que desea restablecer el password asignado a el usuario...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Usuario?opc=7&Id_usuario=' + id_usuario + '';
                                });
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Usuario:Usuario />
        </div>
        <Alertas:Alertas />
    </body>
</html>