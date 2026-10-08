<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Registro.tld" prefix="Registro"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_pruebas.ico" rel="icon" >
            <link rel="stylesheet" href="Interfaz/Contenido/Css/modal.css"/>
            <link rel="stylesheet" href="Interfaz/Tabs/tabs.css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Registro</title>
             <script type = "text/javascript" >
            history.pushState(null, null, 'Registro.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Registro.jsp');
            });
        </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!--Alertas de control-->
                <script type="text/javascript">
                    function Seleccion_lineas(id_linea, nom_linea) {
                        var arg_id_linea = id_linea;
                        var arg_nom_linea = nom_linea;
                        var campo_lineas = document.getElementById("Txt_lineas").value;
                        var div_lineas = document.getElementById("I_seleccion_lineas").innerHTML;
                        if (campo_lineas.includes(arg_id_linea)) {
                            document.getElementById("Txt_lineas").value = campo_lineas.replace(arg_id_linea, '');
                            document.getElementById("I_seleccion_lineas").innerHTML = div_lineas.replace(arg_nom_linea + ',', '');
                        } else {
                            document.getElementById("Txt_lineas").value += arg_id_linea;
                            document.getElementById("I_seleccion_lineas").innerHTML += arg_nom_linea + ',';
                        }
                    }
                    function Producto_ingresado() {
                        document.getElementById("I_Cod_producto").innerHTML = document.getElementById("Txt_codigo_producto").value;
                    }
                    function Seleccion_pruebas(id_prueba, nom_prueba) {
                        var arg_id_prueba = id_prueba;
                        var arg_nom_prueba = nom_prueba;
                        var campo_pruebas = document.getElementById("Txt_pruebas").value;
                        var div_pruebas = document.getElementById("I_seleccion_pruebas").innerHTML;
                        if (campo_pruebas.includes(arg_id_prueba)) {
                            document.getElementById("Txt_pruebas").value = campo_pruebas.replace(arg_id_prueba, '');
                            document.getElementById("I_seleccion_pruebas").innerHTML = div_pruebas.replace(arg_nom_prueba + ',', '');
                        } else {
                            document.getElementById("Txt_pruebas").value += arg_id_prueba;
                            document.getElementById("I_seleccion_pruebas").innerHTML += arg_nom_prueba + ',';
                        }
                    }
                    function Verificacion_campos() {
                        var campo_lineas = document.getElementById("Txt_lineas").value;
                        var campo_pruebas = document.getElementById("Txt_pruebas").value;
                        var campo_codigo = document.getElementById("Txt_codigo_producto").value;
                        if (campo_lineas.length > 0 && campo_pruebas.length > 0 && campo_codigo.length > 0) {
                            document.getElementById("Btn_contruir").style.display = 'block';
                        } else {
                            document.getElementById("Btn_contruir").style.display = 'none';
                        }
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarRegistro(id_registro) {
                        swal({
                            title: "Cerrar Turno",
                            text: "Seguro que desea cerrar el turno...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Registro?opc=3&Id_registro=' + id_registro + '&Estado=0';
                                });
                    }
                    function ActivarRegistro(id_registro) {
                        swal({
                            title: "Abrir Turno",
                            text: "Seguro que desea abrir el turno...",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Registro?opc=3&Id_registro=' + id_registro + '&Estado=1';
                                });
                    }
                    function EliminarPrueba(id, id2, id3) {
                        swal({
                            title: "Eliminar Prueba",
                            text: "Seguro de eliminar la prueba del registro...!",
                            type: "error",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Registro?opc=11&irg=' + id + '&ipe=' + id2 + '&ipc=' + id3;
                                });
                    }
                    function VerRegistrosProceso(id, id2) {
                        location.href = 'Registro?opc=5&irg=' + id + '&Cbx_proceso=' + id2 + '&Form=0&Seguimiento=0&Txt_codigo_producto=0&Arg_pruebas=0&Arg_lineas=0';
                    }
                    function EliminarPrueba_seguimiento(id, id2, id3, id4) {
                        swal({
                            title: "Eliminar Prueba",
                            text: "Seguro de eliminar la prueba del registro...!",
                            type: "error",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Registro?opc=13&irg=' + id + '&ipe=' + id2 + '&ipc=' + id3 + '&ird=' + id4;
                                });
                    }

                    function ConfirmarPrueba(id) {
                        swal({
                            title: "Confirmar Prueba",
                            text: "Seguro de registrar los cambios realizados en la prueba...!",
                            type: "info",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            closeOnConfirm: false,
                            showLoaderOnConfirm: true,
                        },
                                function () {
                                    document.getElementById('Form_' + id).submit();
                                });
                    }
                    function ConfirmarResultado(id) {
                        swal({
                            title: "Confirmar Decisión",
                            text: "Seguro de registrar lla decisión de los resultados de la prueba...!",
                            type: "info",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            closeOnConfirm: false,
                            showLoaderOnConfirm: true,
                        },
                                function () {
                                    document.getElementById('Form_decision_' + id).submit();
                                });
                    }
                </script>
                <!--Otros-->
                <script type="text/javascript">
                    function PostBackProceso() {
                        var Producto = document.getElementById("Cbx_proceso");
                        document.forms['FormEnviarProceso'].submit();
                    }
                    function PostBackProceso2() {
                        var Producto = document.getElementById("Cbx_proceso");
                        document.forms['FormEnviarProceso2'].submit();
                    }
                    function PostBackProducto() {
                        var Linea = document.getElementById("Cbx_linea");
                        document.forms['FormReporteCalidadOEE'].submit();
                    }
                </script>
                <!--Peor de los casos factory-->
                <script type="text/javascript">
                    function Ocultar_productos() {
                        var producto = document.getElementById("Cbx_producto").value;
                        if (producto == "MANUAL") {
                            document.getElementById('Codigo_producto').style.display = 'none';
                            document.getElementById('Productos_seleccion').style.display = 'none';
                            document.getElementById('Productos_manual').style.display = 'block';
                            var field = document.getElementById("Cbx_producto");
                            field.id = "Cbx_producto_old";  // using element properties
                            field.name = "Cbx_producto_old";  // using element properties
                            field.setAttribute("Cbx_producto_old", "Cbx_producto_old");  // using .setAttribute() method
                        }
                    }
                    function Mostrar_productos() {
                        var producto = document.getElementById("Chk_producto").value;
                        if (producto == "SELECCION") {
                            document.getElementById('Codigo_producto').style.display = 'block';
                            document.getElementById('Productos_seleccion').style.display = 'block';
                            document.getElementById('Productos_manual').style.display = 'none';
                            var field = document.getElementById("Cbx_producto_old");
                            field.id = "Cbx_producto";  // using element properties
                            field.name = "Cbx_producto";  // using element properties
                            field.setAttribute("Cbx_producto", "Cbx_producto");  // using .setAttribute() method
                            document.getElementById("Chk_producto").checked = 0;
                            document.getElementById("Cbx_producto").value = "";
                        }
                    }
                    function Concatenar_producto() {
                        var cod_producto = document.getElementById("Txt_codigo_producto_opc").value;
                        var producto = document.getElementById("Txt_nombre_producto").value;
                        document.getElementById("Cbx_producto").value = cod_producto + " / " + producto;
                        //document.getElementById("Cbx_producto").value = document.getElementById("Cbx_producto_concatenar").value;
                    }
                </script>

                <script type="text/javascript">
                    function Control_envio() {
                        document.getElementById("Btn_reg_prueba").disabled = true;
                        document.getElementById("Btn_reg_prueba").value = "";
                        document.getElementById("Puntos").style.display = "block";
                    }
                    function Control_envio_seguimiento() {
                        document.getElementById("Btn_seg_prueba").disabled = true;
                        document.getElementById("Btn_seg_prueba").value = "";
                        document.getElementById("Puntos_seg").style.display = "block";
                    }
                </script>
                <script type="text/javascript">
                    function Programar_pruebas(el) {
                        if (el.checked) {
                            document.getElementById('Cbx_prueba').value += "" + el.value;
                        } else {
                            document.getElementById("Cbx_prueba").value = document.getElementById("Txt_pruebas").value.replace(el.value, "");
                        }
                    }
                </script>
        </head>
        <body id="subpage" >
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Registro:Registro />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
        <script src="Interfaz/Tabs/tabs.js"></script>
    </body>
</html>