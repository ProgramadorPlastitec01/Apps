<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd"><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Orden.tld" prefix="OrdenProd"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Orden de Producción</title>
<!--            <script type = "text/javascript" >
                history.pushState(null, null, 'Orden.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Orden.jsp');
                });
            </script>-->
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <script type="text/javascript">
                    function PostBackFicha() {
                        var ficha = document.getElementById("Cbx_ficha");
                        document.forms['FormFicha'].submit();
                    }
                </script>
                <!-- Abrir y cerra registro/producto/orden -->
                <script type="text/javascript">
                    function DesactivarRegistro(id_registro, orden, id_producto, opcion) {
                        swal({
                            title: "Cerrar Registro",
                            text: "Seguro que desea cerrar el registro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=9&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '&tcs=' + opcion + '';
                                });
                    }
                    function ActivarRegistro(id_registro, orden, id_producto, opcion) {
                        swal({
                            title: "Abrir Registro",
                            text: "Seguro que desea abrir el registro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=9&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '&tcs=' + opcion + '';
                                });
                    }
                    function VerificarRegistro(id_registro, orden, id_producto) {
                        swal({
                            title: "Verificar Registro",
                            text: "Seguro que la cabecera del registro esta verificado...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Orden?opc=13&irg=' + id_registro + '&odn=' + orden + '&ipd=' + id_producto + '';
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
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Orden?opc=11&iop=' + id_orden + '&tcs=1';
                                });
                    }
                </script>

                <script type="text/javascript">
                    function PostBackLinea(Tipo_linea, Tipo_evento) {
                        //var Tipo_linea = document.getElementById("Cbx_linea").value;
                        var Romper = Tipo_linea.split('/');
                        var Registro = Romper[1] + "";
                        var Id_linea = Romper[0] + "";
                        document.getElementById("Id_linea").value = Id_linea;
                        panel_fecha_turno = document.getElementById("Div_fecha_turno");
                        panel_lote_producto = document.getElementById("Div_lote_producto");
                        panel_lote_boca = document.getElementById("Div_lote_boca");
                        panel_lote_cola = document.getElementById("Div_lote_cola");
                        panel_manga = document.getElementById("Div_manga");
                        panel_manga_alt = document.getElementById("Div_manga_alt");
                        panel_ductos = document.getElementById("Div_ductos");
                        try {
                            panel_volumen = document.getElementById("div_volumen");
                            panel_longitudCuerpo = document.getElementById("LongitudCuerpo");
                            panel_longitudMinMax = document.getElementById("div_longitud_MinMax");
                            panel_longitudMinMax2 = document.getElementById("div_longitud_MinMax2");
                            panel_longitudMinMax3 = document.getElementById("div_longitud_MinMax3");
                        } catch (err) {
                            err
                        }

                        panel_ducto_central = document.getElementById("Div_ductos_central");
                        panel_ductos_eva = document.getElementById("Div_ductos_eva");
                        panel_ensambles = document.getElementById("Div_ensambles");
                        panel_ensambles2 = document.getElementById("Div_ensambles2");
                        panel_tinta = document.getElementById("Div_tinta");
                        titulo_tinta_1 = document.getElementById("TintaFoil_1");
                        titulo_tinta_2 = document.getElementById("TintaFoil_2");
                        titulo_tinta_3 = document.getElementById("TintaFoil_3");
                        panel_horno_luz = document.getElementById("Div_horno_luz");
                        panel_alternativos = document.getElementById("Div_alternativos");
                        panel_eva = document.getElementById("Div_eva");
                        panel_sublote = document.getElementById("Div_sublote");
                        panel_boton = document.getElementById("Div_boton");
                        if (Registro == "R-PRF-013") {
                            panel_fecha_turno.style.visibility = 'visible';
                            panel_fecha_turno.style.display = 'block';
                            panel_lote_producto.style.visibility = 'visible';
                            panel_lote_producto.style.display = 'block';
                            panel_lote_cola.style.visibility = 'hidden';
                            panel_lote_cola.style.display = 'none';
                            panel_lote_boca.style.visibility = 'hidden';
                            panel_lote_boca.style.display = 'none';
                            panel_manga.style.visibility = 'visible';
                            panel_manga.style.display = 'block';
                            panel_manga_alt.style.visibility = 'hidden';
                            panel_manga_alt.style.display = 'none';
                            panel_ductos.style.visibility = 'visible';
                            panel_ductos.style.display = 'block';
                            panel_ducto_central.style.visibility = 'hidden';
                            panel_ducto_central.style.display = 'none';
                            panel_ductos_eva.style.visibility = 'hidden';
                            panel_ductos_eva.style.display = 'none';
                            panel_ensambles.style.visibility = 'visible';
                            panel_ensambles.style.display = 'block';
                            panel_ensambles2.style.visibility = 'hidden';
                            panel_ensambles2.style.display = 'none';
                            panel_tinta.style.visibility = 'visible';
                            panel_tinta.style.display = 'block';
                            titulo_tinta_1.innerHTML = 'Tinta';
                            titulo_tinta_2.innerHTML = 'Lote Tinta';
                            titulo_tinta_3.innerHTML = 'Color Tinta';
                            panel_boton.style.visibility = 'visible';
                            panel_boton.style.display = 'block';
                            panel_alternativos.style.visibility = 'visible';
                            panel_alternativos.style.display = 'block';
                            panel_eva.style.visibility = 'hidden';
                            panel_eva.style.display = 'none';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
                            panel_horno_luz.style.visibility = 'hidden';
                            panel_horno_luz.style.display = 'none';
                            panel_volumen.style.visibility = 'hidden';
                            panel_volumen.style.display = 'none';
                            panel_longitudCuerpo.style.visibility = 'hidden';
                            panel_longitudCuerpo.style.display = 'none';
                            panel_longitudMinMax.style.visibility = 'hidden';
                            panel_longitudMinMax.style.display = 'none';
                            panel_longitudMinMax2.style.visibility = 'hidden';
                            panel_longitudMinMax2.style.display = 'none';
                            panel_longitudMinMax3.style.visibility = 'hidden';
                            panel_longitudMinMax3.style.display = 'none';
                            if (Tipo_evento == 1) {
                                document.getElementById("Txt_lote_cola").value = "N/A";
                                document.getElementById("Txt_manga_c_alt").value = "N/A";
                                document.getElementById("Txt_dto_ctl_c").value = "N/A";
                                document.getElementById("Txt_dto_ctl_p").value = "N/A";
                                document.getElementById("Txt_lote_boca").value = "N/A";
                                document.getElementById("Txt_ensamble_3").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_3").value = "N/A";
                                document.getElementById("Txt_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_tubo_refuerzo").value = "N/A";
                                document.getElementById("Txt_lote_ducto_alt").value = "N/A";
                                document.getElementById("Txt_ciclo_esterilizacion").value = "N/A";
                                document.getElementById("Txt_sublote_c").value = "N/A";
                                document.getElementById("Txt_sublote_c_alt").value = "N/A";
                                document.getElementById("Txt_sublote_p").value = "N/A";
                                document.getElementById("Txt_lote_tinta_m").value = "N/A";
                                document.getElementById("Txt_horno_uv").value = "N/A";
                                document.getElementById("Txt_luz_led").value = "N/A";
                                document.getElementById("Rbt_parametros_alternativos_SI").checked = true;
                                document.getElementById("Rbt_parametros_alternativos_NO").checked = false;
                            }
                        } else if (Registro == "R-PRF-011") {
                            panel_fecha_turno.style.visibility = 'visible';
                            panel_fecha_turno.style.display = 'block';
                            panel_lote_producto.style.visibility = 'visible';
                            panel_lote_producto.style.display = 'block';
                            panel_lote_cola.style.visibility = 'visible';
                            panel_lote_cola.style.display = 'block';
                            panel_lote_boca.style.visibility = 'hidden';
                            panel_lote_boca.style.display = 'none';
                            panel_manga.style.visibility = 'visible';
                            panel_manga.style.display = 'block';
                            panel_manga_alt.style.visibility = 'visible';
                            panel_manga_alt.style.display = 'block';
                            panel_ductos.style.visibility = 'visible';
                            panel_ductos.style.display = 'block';
                            panel_ducto_central.style.visibility = 'visible';
                            panel_ducto_central.style.display = 'block';
                            panel_ductos_eva.style.visibility = 'hidden';
                            panel_ductos_eva.style.display = 'none';
                            panel_ensambles.style.visibility = 'visible';
                            panel_ensambles.style.display = 'block';
                            panel_ensambles2.style.visibility = 'hidden';
                            panel_ensambles2.style.display = 'none';
                            panel_tinta.style.visibility = 'visible';
                            panel_tinta.style.display = 'block';
                            titulo_tinta_1.innerHTML = 'Tinta';
                            titulo_tinta_2.innerHTML = 'Lote Tinta';
                            titulo_tinta_3.innerHTML = 'Color Tinta';
                            panel_boton.style.visibility = 'visible';
                            panel_boton.style.display = 'block';
                            panel_alternativos.style.visibility = 'visible';
                            panel_alternativos.style.display = 'block';
                            panel_eva.style.visibility = 'hidden';
                            panel_eva.style.display = 'none';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
                            panel_horno_luz.style.visibility = 'hidden';
                            panel_horno_luz.style.display = 'none';
                            if (Tipo_evento == 1) {
                                document.getElementById("Txt_lote_cola").value = "N/A";
                                document.getElementById("Txt_manga_c_alt").value = "N/A";
                                document.getElementById("Txt_dto_ctl_c").value = "N/A";
                                document.getElementById("Txt_dto_ctl_p").value = "N/A";
                                document.getElementById("Txt_lote_boca").value = "N/A";
                                document.getElementById("Txt_ensamble_3").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_3").value = "N/A";
                                document.getElementById("Txt_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_tubo_refuerzo").value = "N/A";
                                document.getElementById("Txt_lote_ducto_alt").value = "N/A";
                                document.getElementById("Txt_ciclo_esterilizacion").value = "N/A";
                                document.getElementById("Txt_sublote_c").value = "N/A";
                                document.getElementById("Txt_sublote_c_alt").value = "N/A";
                                document.getElementById("Txt_sublote_p").value = "N/A";
                                document.getElementById("Txt_lote_tinta_m").value = "N/A";
                                document.getElementById("Txt_horno_uv").value = "N/A";
                                document.getElementById("Txt_luz_led").value = "N/A";
                                document.getElementById("Rbt_parametros_alternativos_SI").checked = false;
                                document.getElementById("Rbt_parametros_alternativos_NO").checked = true;
                            }
                        } else if (Registro == "R-PRF-056") {
                            panel_fecha_turno.style.visibility = 'visible';
                            panel_fecha_turno.style.display = 'block';
                            panel_lote_producto.style.visibility = 'visible';
                            panel_lote_producto.style.display = 'block';
                            panel_lote_cola.style.visibility = 'hidden';
                            panel_lote_cola.style.display = 'none';
                            panel_lote_boca.style.visibility = 'hidden';
                            panel_lote_boca.style.display = 'none';
                            panel_manga.style.visibility = 'visible';
                            panel_manga.style.display = 'block';
                            panel_manga_alt.style.visibility = 'hidden';
                            panel_manga_alt.style.display = 'none';
                            panel_ductos.style.visibility = 'visible';
                            panel_ductos.style.display = 'block';
                            panel_ducto_central.style.display = 'block';
                            panel_ducto_central.style.visibility = 'visible';
                            try {
                                panel_volumen.style.visibility = 'visible';
                                panel_volumen.style.display = 'block';
                            } catch (err) {
                            }
                            try {
                                panel_longitudCuerpo.style.visibility = 'visible';
                                panel_longitudCuerpo.style.display = 'block';
                            } catch (err) {
                            }
                            try {
                                panel_longitudMinMax.style.visibility = 'visible';
                                panel_longitudMinMax.style.display = 'block';
                            } catch (err) {
                            }
                            try {
                                panel_longitudMinMax2.style.visibility = 'visible';
                                panel_longitudMinMax2.style.display = 'block';
                            } catch (err) {
                            }
                            try {
                                panel_longitudMinMax3.style.visibility = 'visible';
                                panel_longitudMinMax3.style.display = 'block';
                            } catch (err) {
                            }
                            panel_ductos_eva.style.visibility = 'hidden';
                            panel_ductos_eva.style.display = 'none';
                            panel_ensambles.style.visibility = 'visible';
                            panel_ensambles.style.display = 'block';
                            panel_ensambles2.style.visibility = 'hidden';
                            panel_ensambles2.style.display = 'none';
                            panel_tinta.style.visibility = 'visible';
                            panel_tinta.style.display = 'block';
                            titulo_tinta_1.innerHTML = 'Tinta';
                            titulo_tinta_2.innerHTML = 'Lote Tinta';
                            titulo_tinta_3.innerHTML = 'Color Tinta';
                            panel_boton.style.visibility = 'visible';
                            panel_boton.style.display = 'block';
                            panel_alternativos.style.visibility = 'visible';
                            panel_alternativos.style.display = 'block';
                            panel_eva.style.visibility = 'hidden';
                            panel_eva.style.display = 'none';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
                            panel_horno_luz.style.visibility = 'hidden';
                            panel_horno_luz.style.display = 'none';
                            if (Tipo_evento == 1) {
                                document.getElementById("Txt_lote_cola").value = "N/A";
                                document.getElementById("Txt_manga_c_alt").value = "N/A";
                                document.getElementById("Txt_dto_ctl_c").value = "N/A";
                                document.getElementById("Txt_dto_ctl_p").value = "N/A";
                                document.getElementById("Txt_lote_boca").value = "N/A";
                                document.getElementById("Txt_ensamble_3").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_3").value = "N/A";
                                document.getElementById("Txt_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_tubo_refuerzo").value = "N/A";
                                document.getElementById("Txt_lote_ducto_alt").value = "N/A";
                                document.getElementById("Txt_ciclo_esterilizacion").value = "N/A";
                                document.getElementById("Txt_sublote_c").value = "N/A";
                                document.getElementById("Txt_sublote_c_alt").value = "N/A";
                                document.getElementById("Txt_sublote_p").value = "N/A";
                                document.getElementById("Txt_lote_tinta_m").value = "N/A";
                                document.getElementById("Txt_horno_uv").value = "N/A";
                                document.getElementById("Txt_luz_led").value = "N/A";
                                document.getElementById("Rbt_parametros_alternativos_SI").checked = true;
                                document.getElementById("Rbt_parametros_alternativos_NO").checked = false;
                            }
                        } else if (Registro == "R-PRF-010") {
                            panel_fecha_turno.style.visibility = 'visible';
                            panel_fecha_turno.style.display = 'block';
                            panel_lote_producto.style.visibility = 'visible';
                            panel_lote_producto.style.display = 'block';
                            panel_lote_cola.style.visibility = 'hidden';
                            panel_lote_cola.style.display = 'none';
                            panel_lote_boca.style.visibility = 'hidden';
                            panel_lote_boca.style.display = 'none';
                            panel_manga.style.visibility = 'visible';
                            panel_manga.style.display = 'block';
                            panel_manga_alt.style.visibility = 'hidden';
                            panel_manga_alt.style.display = 'none';
                            panel_ductos.style.visibility = 'hidden';
                            panel_ductos.style.display = 'none';
                            panel_ducto_central.style.visibility = 'hidden';
                            panel_ducto_central.style.display = 'none';
                            panel_ductos_eva.style.visibility = 'hidden';
                            panel_ductos_eva.style.display = 'none';
                            panel_ensambles.style.visibility = 'hidden';
                            panel_ensambles.style.display = 'none';
                            panel_ensambles2.style.visibility = 'hidden';
                            panel_ensambles2.style.display = 'none';
                            panel_tinta.style.visibility = 'visible';
                            panel_tinta.style.display = 'block';
                            titulo_tinta_1.innerHTML = 'Tinta/Foil';
                            titulo_tinta_2.innerHTML = 'Lote Tinta/Foil';
                            titulo_tinta_3.innerHTML = 'Color Tinta/Foil';
                            panel_boton.style.visibility = 'visible';
                            panel_boton.style.display = 'block';
                            panel_alternativos.style.visibility = 'hidden';
                            panel_alternativos.style.display = 'none';
                            panel_eva.style.visibility = 'hidden';
                            panel_eva.style.display = 'none';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
                            panel_horno_luz.style.visibility = 'visible';
                            panel_horno_luz.style.display = 'block';
                            document.getElementById("Txt_lote_cola").value = "N/A";
                            document.getElementById("Txt_manga_c_alt").value = "N/A";
                            document.getElementById("Txt_ensamble").value = "N/A";
                            document.getElementById("Txt_lote_ensamble").value = "N/A";
                            document.getElementById("Txt_ensamble_2").value = "N/A";
                            document.getElementById("Txt_lote_ensamble_2").value = "N/A";
                            document.getElementById("Txt_dto_ctl_c").value = "N/A";
                            document.getElementById("Txt_dto_ctl_p").value = "N/A";
                            document.getElementById("Txt_dto_drc_c").value = "N/A";
                            document.getElementById("Txt_dto_drc_p").value = "N/A";
                            document.getElementById("Txt_dto_iqe_c").value = "N/A";
                            document.getElementById("Txt_dto_iqe_p").value = "N/A";
                            document.getElementById("Txt_lote_boca").value = "N/A";
                            document.getElementById("Txt_ensamble_3").value = "N/A";
                            document.getElementById("Txt_lote_ensamble_3").value = "N/A";
                            document.getElementById("Txt_ensamble_4").value = "N/A";
                            document.getElementById("Txt_lote_ensamble_4").value = "N/A";
                            document.getElementById("Txt_lote_tubo_refuerzo").value = "N/A";
//                            document.getElementById("Txt_lote_ducto_alt").value = "N/A";
                            document.getElementById("Txt_ciclo_esterilizacion").value = "N/A";
                            document.getElementById("Txt_sublote_c").value = "N/A";
                            document.getElementById("Txt_sublote_c_alt").value = "N/A";
                            document.getElementById("Txt_sublote_p").value = "N/A";
                            panel_volumen.style.visibility = 'hidden';
                            panel_volumen.style.display = 'none';
                            panel_longitudCuerpo.style.visibility = 'hidden';
                            panel_longitudCuerpo.style.display = 'none';
//                            panel_longitudMinMax.style.visibility = 'hidden';
//                            panel_longitudMinMax.style.display = 'none';
//                            panel_longitudMinMax2.style.visibility = 'hidden';
//                            panel_longitudMinMax2.style.display = 'none';
                        } else if (Registro == "R-PRF-012") {
                            panel_fecha_turno.style.visibility = 'visible';
                            panel_fecha_turno.style.display = 'block';
                            panel_lote_producto.style.visibility = 'visible';
                            panel_lote_producto.style.display = 'block';
                            panel_lote_cola.style.visibility = 'hidden';
                            panel_lote_cola.style.display = 'none';
                            panel_lote_boca.style.visibility = 'hidden';
                            panel_lote_boca.style.display = 'none';
                            panel_manga.style.visibility = 'visible';
                            panel_manga.style.display = 'block';
                            panel_manga_alt.style.visibility = 'visible';
                            panel_manga_alt.style.display = 'block';
                            panel_ductos.style.visibility = 'hidden';
                            panel_ductos.style.display = 'none';
                            panel_ducto_central.style.visibility = 'hidden';
                            panel_ducto_central.style.display = 'none';
                            panel_ductos_eva.style.visibility = 'hidden';
                            panel_ductos_eva.style.display = 'none';
                            panel_ensambles.style.visibility = 'hidden';
                            panel_ensambles.style.display = 'none';
                            panel_ensambles2.style.visibility = 'hidden';
                            panel_ensambles2.style.display = 'none';
                            panel_tinta.style.visibility = 'hidden';
                            panel_tinta.style.display = 'none';
                            titulo_tinta_1.innerHTML = 'Tinta';
                            titulo_tinta_2.innerHTML = 'Lote Tinta';
                            titulo_tinta_3.innerHTML = 'Color Tinta';
                            panel_boton.style.visibility = 'visible';
                            panel_boton.style.display = 'block';
                            panel_alternativos.style.visibility = 'hidden';
                            panel_alternativos.style.display = 'none';
                            panel_eva.style.visibility = 'hidden';
                            panel_eva.style.display = 'none';
                            panel_sublote.style.visibility = 'visible';
                            panel_sublote.style.display = 'block';
                            panel_horno_luz.style.visibility = 'hidden';
                            panel_horno_luz.style.display = 'none';
                            document.getElementById("Txt_lote_cola").value = "N/A";
                            document.getElementById("Txt_manga_c_alt").value = "N/A";
                            document.getElementById("Txt_ensamble").value = "N/A";
                            document.getElementById("Txt_lote_ensamble").value = "N/A";
                            document.getElementById("Txt_ensamble_2").value = "N/A";
                            document.getElementById("Txt_lote_ensamble_2").value = "N/A";
                            document.getElementById("Txt_dto_ctl_c").value = "N/A";
                            document.getElementById("Txt_dto_ctl_p").value = "N/A";
                            document.getElementById("Txt_dto_drc_c").value = "N/A";
                            document.getElementById("Txt_dto_drc_p").value = "N/A";
                            document.getElementById("Txt_dto_iqe_c").value = "N/A";
                            document.getElementById("Txt_dto_iqe_p").value = "N/A";
                            document.getElementById("Txt_lote_boca").value = "N/A";
                            document.getElementById("Txt_lote_tinta").value = "N/A";
                            document.getElementById("Txt_color_tinta").value = "N/A";
                            document.getElementById("Txt_lote_tinta_m").value = "N/A";
                            document.getElementById("Txt_horno_uv").value = "N/A";
                            document.getElementById("Txt_luz_led").value = "N/A";
                            document.getElementById("Txt_ensamble_3").value = "N/A";
                            document.getElementById("Txt_lote_ensamble_3").value = "N/A";
                            document.getElementById("Txt_ensamble_4").value = "N/A";
                            document.getElementById("Txt_lote_ensamble_4").value = "N/A";
                            document.getElementById("Txt_lote_tubo_refuerzo").value = "N/A";
                            document.getElementById("Txt_lote_ducto_alt").value = "N/A";
                            document.getElementById("Txt_ciclo_esterilizacion").value = "N/A";
                            panel_volumen.style.visibility = 'hidden';
                            panel_volumen.style.display = 'none';
                            panel_longitudCuerpo.style.visibility = 'hidden';
                            panel_longitudCuerpo.style.display = 'none';
                            panel_longitudMinMax.style.visibility = 'hidden';
                            panel_longitudMinMax.style.display = 'none';
                            panel_longitudMinMax2.style.visibility = 'hidden';
                            panel_longitudMinMax2.style.display = 'none';
                            panel_longitudMinMax3.style.visibility = 'hidden';
                            panel_longitudMinMax3.style.display = 'none';
                        } else if (Registro == "R-PRF-019") {
                            panel_fecha_turno.style.visibility = 'visible';
                            panel_fecha_turno.style.display = 'block';
                            panel_lote_producto.style.visibility = 'visible';
                            panel_lote_producto.style.display = 'block';
                            panel_lote_cola.style.visibility = 'visible';
                            panel_lote_cola.style.display = 'block';
                            panel_lote_boca.style.visibility = 'visible';
                            panel_lote_boca.style.display = 'block';
                            panel_manga.style.visibility = 'visible';
                            panel_manga.style.display = 'block';
                            panel_manga_alt.style.visibility = 'visible';
                            panel_manga_alt.style.display = 'block';
                            panel_ductos.style.visibility = 'hidden';
                            panel_ductos.style.display = 'none';
                            panel_ducto_central.style.visibility = 'hidden';
                            panel_ducto_central.style.display = 'none';
                            panel_ductos_eva.style.visibility = 'visible';
                            panel_ductos_eva.style.display = 'block';
                            panel_ensambles.style.visibility = 'visible';
                            panel_ensambles.style.display = 'block';
                            panel_ensambles2.style.visibility = 'visible';
                            panel_ensambles2.style.display = 'block';
                            panel_tinta.style.visibility = 'visible';
                            panel_tinta.style.display = 'block';
                            titulo_tinta_1.innerHTML = 'Foil';
                            titulo_tinta_2.innerHTML = 'Lote Foil';
                            titulo_tinta_3.innerHTML = 'Color Foil';
                            panel_boton.style.visibility = 'visible';
                            panel_boton.style.display = 'block';
                            panel_alternativos.style.visibility = 'visible';
                            panel_alternativos.style.display = 'block';
                            panel_eva.style.visibility = 'visible';
                            panel_eva.style.display = 'block';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
//                            panel_horno_luz.style.visibility = 'hidden';
//                            panel_horno_luz.style.display = 'none';
                            panel_volumen.style.visibility = 'hidden';
                            panel_volumen.style.display = 'none';
                            panel_longitudCuerpo.style.visibility = 'hidden';
                            panel_longitudCuerpo.style.display = 'none';
//                            panel_longitudMinMax.style.visibility = 'hidden';
//                            panel_longitudMinMax.style.display = 'none';
//                            panel_longitudMinMax2.style.visibility = 'hidden';
//                            panel_longitudMinMax2.style.display = 'none';
                            if (Tipo_evento == 1) {
                                document.getElementById("Txt_lote_cola").value = "N/A";
                                document.getElementById("Txt_lote_boca").value = "N/A";
                                document.getElementById("Txt_manga_c_alt").value = "N/A";
                                document.getElementById("Txt_dto_ctl_c").value = "N/A";
                                document.getElementById("Txt_dto_ctl_p").value = "N/A";
                                document.getElementById("Txt_lote_boca").value = "N/A";
                                document.getElementById("Txt_ensamble_3").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_3").value = "N/A";
                                document.getElementById("Txt_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_ensamble_4").value = "N/A";
                                document.getElementById("Txt_lote_tubo_refuerzo").value = "N/A";
                                document.getElementById("Txt_lote_ducto_alt").value = "N/A";
                                document.getElementById("Txt_ciclo_esterilizacion").value = "N/A";
                                document.getElementById("Rbt_parametros_alternativos_SI").checked = false;
                                document.getElementById("Rbt_parametros_alternativos_NO").checked = true;
                                document.getElementById("Txt_sublote_c").value = "N/A";
                                document.getElementById("Txt_sublote_c_alt").value = "N/A";
                                document.getElementById("Txt_sublote_p").value = "N/A";
                                document.getElementById("Txt_lote_tinta_m").value = "N/A";
                                document.getElementById("Txt_horno_uv").value = "N/A";
                                document.getElementById("Txt_luz_led").value = "N/A";
                            }
                        } else if (Registro == "0") {
                            panel_fecha_turno.style.visibility = 'hidden';
                            panel_fecha_turno.style.display = 'none';
                            panel_lote_producto.style.visibility = 'hidden';
                            panel_lote_producto.style.display = 'none';
                            panel_lote_cola.style.visibility = 'hidden';
                            panel_lote_cola.style.display = 'none';
                            panel_lote_boca.style.visibility = 'hidden';
                            panel_lote_boca.style.display = 'none';
                            panel_manga.style.visibility = 'hidden';
                            panel_manga.style.display = 'none';
                            panel_manga_alt.style.visibility = 'hidden';
                            panel_manga_alt.style.display = 'none';
                            panel_ductos.style.visibility = 'hidden';
                            panel_ductos.style.display = 'none';
                            panel_ducto_central.style.visibility = 'hidden';
                            panel_ducto_central.style.display = 'none';
                            panel_ensambles.style.visibility = 'hidden';
                            panel_ensambles.style.display = 'none';
                            panel_ensambles2.style.visibility = 'hidden';
                            panel_ensambles2.style.display = 'none';
                            panel_tinta.style.visibility = 'hidden';
                            panel_tinta.style.display = 'none';
                            panel_boton.style.visibility = 'hidden';
                            panel_boton.style.display = 'none';
                            panel_alternativos.style.visibility = 'hidden';
                            panel_alternativos.style.display = 'none';
                            panel_eva.style.visibility = 'hidden';
                            panel_eva.style.display = 'none';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
                            panel_sublote.style.visibility = 'hidden';
                            panel_sublote.style.display = 'none';
                            panel_horno_luz.style.visibility = 'hidden';
                            panel_horno_luz.style.display = 'none';
                            panel_volumen.style.visibility = 'hidden';
                            panel_volumen.style.display = 'none';
                            panel_longitudCuerpo.style.visibility = 'hidden';
                            panel_longitudCuerpo.style.display = 'none';
                            panel_longitudMinMax.style.visibility = 'hidden';
                            panel_longitudMinMax.style.display = 'none';
                            panel_longitudMinMax2.style.visibility = 'hidden';
                            panel_longitudMinMax2.style.display = 'none';
                            panel_longitudMinMax3.style.visibility = 'hidden';
                            panel_longitudMinMax3.style.display = 'none';
                        }
                    }
                    function Ductos_eva() {
                        var lote_c = document.getElementById('Txt_ductos_eva_c').value;
                        var lote_p = document.getElementById('Txt_ductos_eva_p').value;
                        document.getElementById("Txt_dto_ctl_c").value = lote_c;
                        document.getElementById("Txt_dto_ctl_p").value = lote_p;
                        document.getElementById("Txt_dto_drc_c").value = lote_c;
                        document.getElementById("Txt_dto_drc_p").value = lote_p;
                        document.getElementById("Txt_dto_iqe_c").value = lote_c;
                        document.getElementById("Txt_dto_iqe_p").value = lote_p;
                    }
                    function Form_registro_cabecera() {
                        document.getElementById('Form_registro').style.display = 'block';
                    }
                    function Form_registro_cabecera_cerrar() {
                        document.getElementById('Form_registro').style.display = 'none';
                    }
                </script>
                <script>
                    function FormEnviar() {
                        document.getElementById('FormSubmit').submit();
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
                            closeOnConfirm: false
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
                            closeOnConfirm: false
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
                            closeOnConfirm: false
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
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Orden?opc=15&odn=' + orden + '&ipd=' + id_producto + '&irg=' + id_registro + '&Tipo=' + tipo;
                                });
                    }

                </script>
                <script>
                    function mostrarConvencion(id) {
                        if (document.getElementById("Ventana" + id).style.display === "none") {
                            document.getElementById("Ventana" + id).style.display = "block";
                        } else if (document.getElementById("Ventana" + id).style.display === "block") {
                            document.getElementById("Ventana" + id).style.display = "none";
                        }
                    }
                </script>
                <script>
                    function Form_registro_prod_cabecera() {
                        document.getElementById('Form_registro_prod').style.display = 'block';
                    }
                    function Form_registro_prod_cabecera_cerrar() {
                        document.getElementById('Form_registro_prod').style.display = 'none';
                    }
                    function Asignar_li() {
                        var ft_ingresada = document.getElementById("Txt_ft_complementarias").value;
                        var adicion = ft_ingresada;
                        var materiales = "";
                        var vacio = 5;
                        if (adicion.length > 0) {
                            if (document.getElementById("Txt_complementarias_ft").value.length === 0) {
                                vacio = 0;
                            }
                            if (document.getElementById("Txt_complementarias_ft").value.includes("[" + ft_ingresada + "]") !== true) {
                                document.getElementById("Txt_complementarias_ft").value += "[" + ft_ingresada + "]";
                                materiales = materiales += ft_ingresada.split(" ___ ")[1];
                                if (Encontrar_li(adicion)) {
                                    var li = document.createElement('li');
                                    li.id = adicion;
                                    li.style.fontSize = "14px";
                                    adicion = ft_ingresada.split(" / ")[0] + " / " + ft_ingresada.split(" / ")[1] + " / " + ft_ingresada.split(" / ")[2].split(" ___ ")[0];
                                    if (vacio > 0) {
                                        li.innerHTML = "<span onclick='Eliminar_li(this)' class='far fa-minus-square fa-size_small' title='Quitar Elemento' ></span> " + adicion;
                                    } else {
                                        li.innerHTML = "<b> " + adicion + "</b>";
                                        document.getElementById("Tipo_ft_orden").innerHTML = "FT Complementarias";
                                        document.getElementById('Btn_registrar_prod').style.display = 'block';
                                    }
                                    document.getElementById("lst_asignacion").appendChild(li);
                                }
                                document.getElementById("Div_materiales").innerHTML += materiales;
                            }
                        }
                        document.getElementById("Txt_ft_complementarias").value = "";
                        return false;
                    }
                    function Encontrar_li(contenido) {
                        var el = document.getElementById("lst_asignacion").getElementsByTagName("li");
                        for (var i = 0; i < el.length; i++)
                        {
                            if (el[i].innerHTML == contenido)
                                return false;
                        }
                        return true;
                    }
                    function Eliminar_li(elemento) {
                        var id = elemento.parentNode.getAttribute("id");
                        var materia = id.split(" ___ ")[1] + "";
                        id = "[" + id + "]";
                        document.getElementById("Txt_complementarias_ft").value = document.getElementById("Txt_complementarias_ft").value.replace(id, "");
                        //if (document.getElementById("Txt_asignacion_dotacion").value == "") {}
                        document.getElementById("Div_materiales").innerHTML = document.getElementById("Div_materiales").innerHTML.replace(materia, "");
                        node = document.getElementById(id.replace("]", "").replace("[", ""));
                        node.parentNode.removeChild(node);
                    }
                    function Refrescar_asignar() {
                        document.getElementById("Txt_ft_complementarias").value = "";
                    }
                    function ValidarDespeje(val) {
                        if (val === 1) {
                            document.getElementById("ValidRegDesp").style.display = "block";
                        }
                    }
                    function DuplicarDespeje(id_registro, orden, id_producto, tipo) {
                        var text_alert;
                        var color_alert;
                        var linea = document.getElementById("AlertaLinea").value;
                        var id_t_linea = document.getElementById("Id_T_Linea").value;
                        if (tipo == 1) {
                            text_alert = "¿Seguro que desea duplicar el registro de despeje?. Se tomara información del ultimo registro de la linea " + linea;
                            color_alert = 'green';
                        } else {
                            text_alert = 'Seguro de iniciar el turno sin registro de despeje ?';
                            color_alert = 'red';
                        }
                        swal({
                            title: "Duplicar Despeje",
                            text: text_alert,
                            type: "info",
                            showCancelButton: true,
                            confirmButtonColor: color_alert,
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Orden?opc=22&odn=' + orden + '&ipd=' + id_producto + '&irg=' + id_registro + '&idTLinea=' + id_t_linea + '&Tipo=' + tipo;
                                });
                    }
                    function CerrarValDespeje() {
                        document.getElementById("ValidRegDesp").style.display = "none";
                        document.getElementById("AplicaDespN").checked = true;
                        document.getElementById("AplicaDespS").checked = false;
                    }
                    function DespejeEscogido() {
                        document.getElementById("ValidRegDesp").style.display = "none";
                        document.getElementById("ValDespeje").style.display = "block";
                        var id = document.getElementById("id_reg").value;
                        document.getElementById("id_despejeDP").value = id;
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <OrdenProd:OrdenProd />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>