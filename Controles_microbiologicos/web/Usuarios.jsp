<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Usuario.tld" prefix="Usuario"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Usuarios</title>
            <jsp:include page="Encabezado.jsp"></jsp:include>
                <script type="text/javascript">
                    history.pushState(null, null, 'Usuario.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Usuario.jsp');
                    });
                    function DesactivarUsuario(id_Usuario) {
                        swal({
                            title: "Desactivar!",
                            text: "Seguro que desea Desactivar el Usuario?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#A146BF",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Usuario?opc=5&Id_usuario=' + id_Usuario + '';
                                });
                    }
                    function ActivarUsuario(id_Usuario) {
                        swal({
                            title: "Activar!",
                            text: "Seguro que desea activar el Usuario?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#A146BF",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Usuario?opc=6&Id_usuario=' + id_Usuario + '';
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