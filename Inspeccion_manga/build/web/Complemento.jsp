<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Complemento.tld" prefix="Complemento"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Inspeccion_manga_new.ico" rel="icon" >
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
                    function DesactivarLinea(id_linea) {
                        swal({
                            title: "Inactivar Linea",
                            text: "Seguro que desea desactivar la línea...!",
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
                            text: "Seguro que desea activar la línea...!",
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
                    function DesactivarFicha(id_ficha) {
                        swal({
                            title: "Inactivar Datos de control?",
                            text: "Seguro que desea desactivar los datos de control...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=6&Id_ficha=' + id_ficha + '&Estado=0';
                                });
                    }
                    function ActivarFicha(id_ficha) {
                        swal({
                            title: "Activar Datos de control",
                            text: "Seguro que desea activar los datos de control...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=6&Id_ficha=' + id_ficha + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarSerial(id_serial) {
                        swal({
                            title: "Inactivar Serial",
                            text: "Seguro que desea desactivar el serial...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=9&Id_serial=' + id_serial + '&Estado=0';
                                });
                    }
                    function ActivarSerial(id_serial) {
                        swal({
                            title: "Activar Serial",
                            text: "Seguro que desea activar el serial...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=9&Id_serial=' + id_serial + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarAlgoritmo(id_algoritmo) {
                        swal({
                            title: "Inactivar Algoritmos",
                            text: "Seguro que desea desactivar el algoritmo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=13&Id_algoritmo=' + id_algoritmo + '&Estado=0';
                                });
                    }
                    function ActivarAlgoritmo(id_algoritmo) {
                        swal({
                            title: "Activar Algoritmo",
                            text: "Seguro que desea activar el algoritmo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Complemento?opc=13&Id_algoritmo=' + id_algoritmo + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function Ocultar_productos() {
                        var producto = document.getElementById("Cbx_producto").value;
                        if (producto == "MANUAL") {
                            document.getElementById('Codigo_producto').style.display = 'none';
                            document.getElementById('Productos_seleccion').style.display = 'none';
                            document.getElementById('Productos_manual').style.display = 'block';
                            var field = document.getElementById("Cbx_producto");
                            field.id = "Cbx_producto_old"; // using element properties
                            field.name = "Cbx_producto_old"; // using element properties
                            field.setAttribute("Cbx_producto_old", "Cbx_producto_old"); // using .setAttribute() method
                        }
                    }
                    function Mostrar_productos() {
                        var producto = document.getElementById("Chk_producto").value;
                        if (producto == "SELECCION") {
                            document.getElementById('Codigo_producto').style.display = 'block';
                            document.getElementById('Productos_seleccion').style.display = 'block';
                            document.getElementById('Productos_manual').style.display = 'none';
                            var field = document.getElementById("Cbx_producto_old");
                            field.id = "Cbx_producto"; // using element properties
                            field.name = "Cbx_producto"; // using element properties
                            field.setAttribute("Cbx_producto", "Cbx_producto"); // using .setAttribute() method
                            document.getElementById("Chk_producto").checked = 0;
                            document.getElementById("Cbx_producto").value = "";
                        }
                    }
                    function Concatenar_producto() {
                        var cod_producto = document.getElementById("Txt_codigo_producto").value;
                        var producto = document.getElementById("Txt_nombre_producto").value;
                        document.getElementById("Cbx_producto").value = cod_producto + " / " + producto;
                        //document.getElementById("Cbx_producto").value = document.getElementById("Cbx_producto_concatenar").value;
                    }
                </script>
                <script type="text/javascript">
                    function Registro_pp(radio) {
                        if (radio.value == 1) {
                            document.getElementById("Rdb_material_1").disabled = false;
                            document.getElementById("Rdb_material_0").disabled = false;
                            document.getElementById("Rdb_material_0").checked = true;
                              document.getElementById("Rdb_estriada_ventana_0").disabled = false;
                            document.getElementById("Rdb_estriada_ventana_1").disabled = false;
                            document.getElementById("Rdb_estriada_ventana_2").disabled = false;
                            document.getElementById("Rdb_estriada_ventana_0").checked = true ;
                        } else {
                            document.getElementById("Rdb_material_1").disabled = true;
                            document.getElementById("Rdb_material_0").disabled = false;
                            document.getElementById("Rdb_material_0").checked = true;
                            document.getElementById("Rdb_estriada_ventana_0").disabled = true;
                            document.getElementById("Rdb_estriada_ventana_1").disabled = true;
                            document.getElementById("Rdb_estriada_ventana_2").disabled = true;
                            document.getElementById("Rdb_estriada_ventana_0").checked = true;
                        }
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Complemento:Complemento />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>