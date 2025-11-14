<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/tlds/Plastitec.tld" prefix="Plastitec" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Plastitec.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Plastitec.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        <script type="text/javascript">
            function PostBackFiltro() {
                document.forms['FormFiltro'].submit();
            }
            </script>
            <script type="text/javascript">
                function DesactivarArea(id_area) {
                    swal({
                        title: "Inactivar Area",
                        text: "Seguro que desea desactivar el area...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Plastitec?opc=3&Id_area=' + id_area + '&Estado=0';
                            });
                }
                function ActivarArea(id_area) {
                    swal({
                        title: "Activar Area",
                        text: "Seguro que desea activar el area...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Plastitec?opc=3&Id_area=' + id_area + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarCargo(id_cargo) {
                    swal({
                        title: "Inactivar Cargo",
                        text: "Seguro que desea desactivar el cargo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Plastitec?opc=6&Id_cargo=' + id_cargo + '&Estado=0';
                            });
                }
                function ActivarCargo(id_cargo) {
                    swal({
                        title: "Activar Cargo",
                        text: "Seguro que desea activar el cargo...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Plastitec?opc=6&Id_cargo=' + id_cargo + '&Estado=1';
                            });
                }
            </script>
            <script type="text/javascript">
                function DesactivarCategoria(id_categoria) {
                    swal({
                        title: "Inactivar Categoria",
                        text: "Seguro que desea desactivar la categoria...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Plastitec?opc=9&Id_categoria=' + id_categoria + '&Estado=0';
                            });
                }
                function ActivarCategoria(id_categoria) {
                    swal({
                        title: "Activar Categoria",
                        text: "Seguro que desea activar la categoria...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Plastitec?opc=9&Id_categoria=' + id_categoria + '&Estado=1';
                            });
                }
            </script>
        </head>
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container">
            <div id="page">
                <Plastitec:Plastitec />
            </div>
        </div>
        <Alertas:Alertas />
    </body>
</html>
