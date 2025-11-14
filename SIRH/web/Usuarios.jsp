<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/tlds/Usuario.tld" prefix="Usuario" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Usuarios.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Usuarios.jsp');
            });
        </script>
        <script type = "text/javascript" >
            function SeleccionPermisos(ipm, ipm2) {
                if (ipm.checked) {
                    document.getElementById('Txt_seleccion_permisos').value += "" + ipm.value;
                    document.getElementById('Div_permisos_' + ipm2).style.display = "block";
                } else {
                    document.getElementById("Txt_seleccion_permisos").value = document.getElementById("Txt_seleccion_permisos").value.replace(ipm.value, "");
                    document.getElementById('Div_permisos_' + ipm2).style.display = "none";
                    var campo1 = document.getElementById("Txt_seleccion_permisos_detallados").value;
                    campo1 = campo1.replace("[" + ipm2 + "/I]", "");
                    campo1 = campo1.replace("[" + ipm2 + "/U]", "");
                    campo1 = campo1.replace("[" + ipm2 + "/D]", "");
                    campo1 = campo1.replace("[" + ipm2 + "/S]", "");
                    campo1 = campo1.replace("[" + ipm2 + "/V]", "");
                    campo1 = campo1.replace("[" + ipm2 + "/P]", "");
                    document.getElementById("Txt_seleccion_permisos_detallados").value = campo1;
                }
            }
        </script>
        <script type = "text/javascript" >
            function SeleccionPermisosDetallados(ipm) {
                if (ipm.checked) {
                    document.getElementById('Txt_seleccion_permisos_detallados').value += "" + ipm.value;
                } else {
                    document.getElementById("Txt_seleccion_permisos_detallados").value = document.getElementById("Txt_seleccion_permisos_detallados").value.replace(ipm.value, "");
                }
            }
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script type="text/javascript">
                function DesactivarUsuario(id_usuario) {
                    swal({
                        title: "Inactivar Usuario",
                        text: "Seguro que desea desactivar el usuario...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Usuario?opc=5&Id_usuario=' + id_usuario + '&Estado=0';
                            });
                }
                function ActivarUsuario(id_usuario) {
                    swal({
                        title: "Activar Usuario",
                        text: "Seguro que desea activar el usuario...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Usuario?opc=5&Id_usuario=' + id_usuario + '&Estado=1';
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
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container">
            <div id="page" style="height: 120%;">
                <Usuario:Usuario />
            </div>
        </div>
        <Alertas:Alertas />
    </body>
</html>
