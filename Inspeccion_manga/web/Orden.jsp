<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Orden.tld" prefix="Orden"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Inspeccion_manga_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Orden de Producción</title>
            <!--            <script type = "text/javascript" >
                            history.pushState(null, null, 'Orden.jsp');
                            window.addEventListener('popstate', function (event) {
                                history.pushState(null, null, 'Orden.jsp');
                            });
                        </script>-->
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!-- Abrir y cerra registro/producto/orden -->
                <script type="text/javascript">
                    function DesactivarRegistro_PI(id_registro, orden, id_producto, opcion) {
                        swal({
                            title: "Cerrar Registro PI",
                            text: "Seguro que desea cerrar el registro por PRODUCCIÓN INSUMOS...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=9&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '&tcs=' + opcion + '&rol=PI';
                                });
                    }
                    function ActivarRegistro_PI(id_registro, orden, id_producto, opcion) {
                        swal({
                            title: "Abrir Registro PI",
                            text: "Seguro que desea abrir el registro por PRODUCCIÓN INSUMOS...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=9&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '&tcs=' + opcion + '&rol=PI';
                                });
                    }
                    function DesactivarRegistro_GC(id_registro, orden, id_producto, opcion) {
                        swal({
                            title: "Cerrar Registro GC",
                            text: "Seguro que desea cerrar el registro por GESTIÓN CALIDAD...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=9&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '&tcs=' + opcion + '&rol=GC';
                                });
                    }
                    function ActivarRegistro_GC(id_registro, orden, id_producto, opcion) {
                        swal({
                            title: "Abrir Registro GC",
                            text: "Seguro que desea abrir el registro por GESTIÓN CALIDAD...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=9&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '&tcs=' + opcion + '&rol=GC';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarProducto(orden, id_producto, opcion) {
                        swal({
                            title: "Cerrar Producto",
                            text: "Seguro que desea cerrar el producto...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=10&odn=' + orden + '&tcs=' + opcion + '&ipd=' + id_producto + '';
                                });
                    }
                    function ActivarProducto(orden, id_producto, opcion) {
                        swal({
                            title: "Abrir Producto",
                            text: "Seguro que desea abrir el producto...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=10&odn=' + orden + '&tcs=' + opcion + '&ipd=' + id_producto + '';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarOrden(id_orden) {
                        swal({
                            title: "Cerrar Orden",
                            text: "Seguro que desea cerrar la orden de producción...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=11&iop=' + id_orden + '&tcs=2';
                                });
                    }
                    function ActivarOrden(id_orden) {
                        swal({
                            title: "Abrir Orden",
                            text: "Seguro que desea abrir la orden de producción...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=11&iop=' + id_orden + '&tcs=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function Ocultar_cliente() {
                        var cliente = document.getElementById("Cbx_cliente").value;
                        if (cliente == "MANUAL") {
                            document.getElementById('Cliente_seleccion').style.display = 'none';
                            document.getElementById('Cliente_seleccion').style.visibility = 'hidden';
                            document.getElementById('Cliente_manual').style.display = 'block';
                            document.getElementById('Cliente_manual').style.visibility = 'visible';
                        }
                    }
                    function Mostrar_cliente() {
                        var cliente = document.getElementById("Chk_cliente").value;
                        if (cliente == "SELECCION") {
                            document.getElementById('Cliente_seleccion').style.display = 'block';
                            document.getElementById('Cliente_seleccion').style.visibility = 'visible';
                            document.getElementById('Cliente_manual').style.display = 'none';
                            document.getElementById('Cliente_manual').style.visibility = 'hidden';
                            document.getElementById("Chk_cliente").checked = 0;
                            document.getElementById("Cbx_cliente").value = "";
                        }
                    }
                </script>
                <script type="text/javascript">
                    function DespejeRPI002(id_registro) {
                        swal({
                            title: "Habilitar R-PI-002",
                            text: "Seguro de habilitar registro de despeje R-PI-002 para el turno...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=14&Id_registro=' + id_registro + '&Tipo_despeje=R_PI_002';
                                });
                    }
                    function DespejeRPI027(id_registro) {
                        swal({
                            title: "Habilitar R-PI-027",
                            text: "Seguro de habilitar registro de despeje R-PI-027 para el turno...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=14&Id_registro=' + id_registro + '&Tipo_despeje=R_PI_027';
                                });
                    }
                    function DespejeRPI031(id_registro, opcion) {
                        swal({
                            title: "Habilitar R-PI-031",
                            text: "Seguro de habilitar registro de despeje R-PI-031 para el turno...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=14&Id_registro=' + id_registro + '&Tipo_despeje=R_PI_031';
                                });
                    }
                </script>
                <!--Eliminar y liberar despejes-->
                <script language="javascript">
                    function EliminarDespeje() {
                        swal({
                            title: "Eliminar Despeje",
                            text: "Seguro de eliminar el registro de despeje para el turno...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    document.getElementById('FormDeleteDespeje').submit();
                                });
                    }
                    function LiberarDespeje() {
                        swal({
                            title: "Liberar Despeje",
                            text: "Seguro de liberar el registro de despeje para el turno...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    document.getElementById('FormFreeDespeje').submit();
                                });
                    }
                    function FirmarDespeje() {
                        swal({
                            title: "Firmar Despeje",
                            text: "Seguro de firmar el registro de despeje...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    document.getElementById('FormFirmarDespeje').submit();
                                });
                    }
                    function PermisoDespeje(id_registro, orden, id_producto, tipo) {
                        var text_alert;
                        var color_alert;
                        if (tipo == 1) {
                            text_alert = 'Seguro de crear registro de despeje para el turno ?';
                            color_alert = 'green';
                        } else {
                            text_alert = 'Seguro de iniciar el turno sin registro de despeje ?';
                            color_alert = 'red';
                        }
                        swal({
                            title: "Habilitar Despeje",
                            text: text_alert,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: color_alert,
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=18&Orden=' + orden + '&Id_producto=' + id_producto + '&Id_registro=' + id_registro + '&Tipo=' + tipo;
                                });
                    }
                </script>
                <script language="javascript">
                    function Validacion_campos() {
                        var obj = document.getElementById('padre');
                        var numero = obj.getElementsByTagName('u').length;
                        var numero_x = obj.getElementsByTagName('i').length;
                        for (i = 0; i < numero; i++) {
                            var campo = document.getElementById((i + 1)).innerHTML;
                            var count = campo.length;
                            if (count < 3) {
                                document.getElementById((i + 1)).setAttribute("style", "background-color:rgba(255, 0, 0, 0.43)");
                                document.getElementById((i + 1) + "_" + (i + 1)).setAttribute("style", "color:red");
                            } else {
                                document.getElementById((i + 1)).setAttribute("style", "background-color:#fff");
                                document.getElementById((i + 1) + "_" + (i + 1)).setAttribute("style", "color:black");
                            }
                        }
                        for (i = 0; i < numero_x; i++) {
                            var campo_x = document.getElementById("X" + (i + 1)).innerHTML;
                            var count_x = campo_x.length;
                            if (count_x < 3) {
                                document.getElementById("X" + (i + 1)).setAttribute("style", "background-color:rgba(255, 0, 0, 0.43)");
                            } else {
                                document.getElementById("X" + (i + 1)).setAttribute("style", "background-color:#fff");
                            }
                        }
                    }
                </script>
                <!-- Slección Implementos -->
                <script type="text/javascript">
                    function SeleccionImplementos(el)
                    {
                        if (el.checked) {
                            document.getElementById('Txt_seleccion_seriales').value += "" + el.value;
                        } else {
                            document.getElementById("Txt_seleccion_seriales").value = document.getElementById("Txt_seleccion_seriales").value.replace(el.value, "");
                        }
                    }
                </script>
                <!-- Numero de orden dinamico -->
                <script type="text/javascript">
                    function NumeroOrdenProduccion() {
                        var cod_linea = document.getElementById('Cbx_codigo_linea').value;
                        var formula = document.getElementById('Txt_formula').value;
                        var fecha = document.getElementById('datepicker').value;
                        fecha = fecha.replace("-", "").replace("-", "").substring(2);
                        document.getElementById('Txt_orden').value = cod_linea + "-" + formula + "-" + fecha;
                    }
                    function Tipo_op(valor) {
                        if (valor > 0) {
                            document.getElementById('Div_tipo_op').style.display = "block";
                            document.getElementById('OP_alt').style.display = "none";
                            document.getElementById('OP_real').style.display = "block";
                            document.getElementById('Txt_orden_alt').value = "12345";
                        } else {
                            document.getElementById('Div_tipo_op').style.display = "none";
                            document.getElementById('OP_alt').style.display = "block";
                            document.getElementById('OP_real').style.display = "none";
                            document.getElementById('Txt_orden_alt').value = "";
                        }
                    }
                </script>

                <script>
                    function validar(minValue) {
                        const campo = document.getElementById("miCampo");
                        const mensaje = document.getElementById("mensaje");
                        const valor = parseFloat(campo.value);

                        // Validamos solo si el input no está vacío
                        if (!isNaN(valor)) {
                            if (valor < minValue) {
                                mensaje.textContent = 'El valor no puede ser menor que ' + minValue;
//                                campo.value = ''
                                campo.style.borderColor = "red";
                            } else {
                                mensaje.textContent = "";
                                campo.style.borderColor = "initial";
                            }
                        } else {
                            mensaje.textContent = "";
                            campo.style.borderColor = "initial";
                        }
                    }
                </script>
                <script>
                    function valFormPasar(idForm) {
                        swal({
                            title: "Atención",
                            text: "¿Esta seguro/a de pasar los rollos?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "orange",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    document.getElementById("FormPasar" + idForm).submit();
                                });
                    }
                </script>

        </head>
        <body id="subpage" onload="Validacion_campos()">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Orden:Orden />
            <script>
                $('.jqte-test').jqte();
                // settings of status
                var jqteStatus = true;
                $(".status").click(function ()
                {
                    jqteStatus = jqteStatus ? false : true;
                    $('.jqte-test').jqte({"status": jqteStatus})
                });
            </script>
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>