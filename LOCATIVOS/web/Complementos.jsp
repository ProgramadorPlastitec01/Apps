<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Complementos.tld" prefix="complemento"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
        <title>Locativos MT</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Complementos.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Complementos.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <!-- Alertas controlados-->
            <script type="text/javascript">
                function DesactivarUsuario(id_usuario) {
                    swal({
                        title: "Desactivar Usuario?",
                        text: "Seguro que desea desactivar el usuario...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=5&Id_usuario=' + id_usuario + '';
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
                                location.href = 'Complementos?opc=6&Id_usuario=' + id_usuario + '';
                            });
                }
                function DesactivarProveedor(id_proveedor) {
                    swal({
                        title: "Desactivar Proveedor?",
                        text: "Seguro que desea desactivar el proveedor...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=11&Id_proveedor=' + id_proveedor + '';
                            });
                }
                function ActivarProveedor(id_proveedor) {
                    swal({
                        title: "Activar Proveedor?",
                        text: "Seguro que desea activar el proveedor...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=12&Id_proveedor=' + id_proveedor + '';
                            });
                }
                function DesactivarUbicacion(id_ubicacion) {
                    swal({
                        title: "Desactivar Ubicación?",
                        text: "Seguro que desea desactivar la ubicacion...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=17&Id_ubicacion=' + id_ubicacion + '';
                            });
                }
                function ActivarUbicacion(id_ubicacion) {
                    swal({
                        title: "Activar Ubicación?",
                        text: "Seguro que desea activar la ubicacion...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=18&Id_ubicacion=' + id_ubicacion + '';
                            });
                }
                function DesactivarClasificacion(id_clasificacion) {
                    swal({
                        title: "Desactivar Clasificación?",
                        text: "Seguro que desea desactivar la clasificacion...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=23&Id_clasificacion=' + id_clasificacion + '';
                            });
                }
                function ActivarClasificacion(id_clasificacion) {
                    swal({
                        title: "Activar Clasificación?",
                        text: "Seguro que desea activar la clasificacion...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Complementos?opc=24&Id_clasificacion=' + id_clasificacion + '';
                            });
                }
            </script>
             <!-- externos -->
        <script type="text/javascript">
            function externos_sub(el)
            {
                if (el.checked) {
                    document.getElementById('Id_solicitud_externos').value += "" + el.value;
                } else {
                    document.getElementById("Id_solicitud_externos").value = document.getElementById("Id_solicitud_externos").value.replace(el.value, "");
                }
            }
        </script>
        </head>
        <body id="subpage" style="background:#FFF url(Interfaz/Contenido/images/pattern.png) repeat top left;">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <complemento:Complementos/>
        </div>
        <Alertas:Alertas />
    </body>
</html>
