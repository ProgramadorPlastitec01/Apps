<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Registro.tld" prefix="Registro"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Registro</title>
<!--            <script type = "text/javascript" >
                history.pushState(null, null, 'Registro.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Registro.jsp');
                });
            </script>-->
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!--Replace (..)/(.)-->
                <script type="text/javascript">
                    function Replace(that) {
                        if (that.value.indexOf("..") >= 0) {
                            that.value = that.value.replace(/\../g, ".");
                        }
                    }
                </script>
                <!--Eliminar PN y paradas de maquinaC-->

<!--                <script type="text/javascript">
                    function ValidarLongPlumat(ValorFch, max, min) {
                        var ValorIn = document.getElementById("Vlr_parametro_").value;
                        if (ValorIn === 0) {
                               document.getElementById("Vlr_parametro_").style = "box-shadow: 0 0 8px green";
                        } else if (ValorFch >= min && ValorFch <= max) {
                               document.getElementById("Vlr_parametro_").style = "box-shadow: 0 0 8px green";
                        } else {
                               document.getElementById("Vlr_parametro_").style = "box-shadow: 0 0 8px red";
                        }
                    }
                </script>-->

                <script type="text/javascript">
                    
                    function EliminarPNC(id_pnc, id_registro) {
                        swal({
                            title: "Quitar PNC",
                            text: "Seguro que desea quitar la descripción de PNC del registro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=28&Id_registro=' + id_registro + '&Id_registro_pnc=' + id_pnc + '';
                                });
                    }
                    function EliminarParadaMaquina(id_parada, id_registro) {
                        swal({
                            title: "Quitar Parada",
                            text: "Seguro que desea quitar la parada de maquina del registro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=29&Id_registro=' + id_registro + '&Id_registro_parada=' + id_parada + '';
                                });
                    }
//                    function EliminarParadaMaquina(id_parada, id_registro) {
//                        swal({
//                            title: "Quitar Parada",
//                            text: "Seguro que desea quitar la parada de maquina del registro...!",
//                            type: "warning",
//                            showCancelButton: true,
//                            confirmButtonColor: "red",
//                            confirmButtonText: "Aceptar",
//                            cancelButtonText: "Cancelar",
//                            closeOnConfirm: false
//                        },
//                                function () {
//                                    location.href = 'Registro?opc=29&Id_registro=' + id_registro + '&Id_registro_parada=' + id_parada + '';
//                                });
//                    }
                    function EliminarObservacion(id_observacion, id_registro) {
                        swal({
                            title: "Quitar Observacion",
                            text: "Seguro que desea quitar la observación del registro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=31&Id_registro=' + id_registro + '&Id_registro_observacion=' + id_observacion + '';
                                });
                    }
                    function EliminarEntradaMaterial(id_entrada_material, id_registro) {
                        swal({
                            title: "Quitar Material",
                            text: "Seguro que desea quitar la entrada de material del registro...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=32&Id_registro=' + id_registro + '&Id_registro_entrada_material=' + id_entrada_material + '';
                                });
                    }
                    function BloquearEstacion(toma, id_registro) {
                        alerta_text = '';
                        if (toma == 5 || toma == 10) {
                            alerta_text = "Seguro que desea bloquear la estación horaria de la coordinadora de producción del módulo...";
                        } else {
                            if (toma >= 6) {
                                alerta_text = "Seguro que desea bloquear la estación horaria " + (toma - 1) + " del módulo...";
                            } else {
                                alerta_text = "Seguro que desea bloquear la estación horaria " + toma + " del módulo...";
                            }
                        }
                        swal({
                            title: "Bloquear Estacion",
                            text: "" + alerta_text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=37&Id_registro=' + id_registro + '&Cbx_frecuencia=' + toma + '&fce=0';
                                });
                    }
                    function DesbloquearEstacion(toma, id_registro) {
                        alerta_text = '';
                        if (toma == 5 || toma == 10) {
                            alerta_text = "Seguro que desea bloquear la estación horaria de la coordinadora de producción del módulo...";
                        } else {
                            if (toma >= 6) {
                                alerta_text = "Seguro que desea desbloquear la estación horaria " + (toma - 1) + " del módulo...";
                            } else {
                                alerta_text = "Seguro que desea debloquear la estación horaria " + toma + " del módulo...";
                            }
                        }
                        swal({
                            title: "Desbloquear Estacion",
                            text: "" + alerta_text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=38&Id_registro=' + id_registro + '&Cbx_frecuencia=' + toma + '&fce=0';
                                });

                    }
                    function BloquearEstacionMedia(toma, id_registro) {
                        alerta_text = '';
                        if (toma == 8 || toma == 17) {
                            alerta_text = "Seguro que desea bloquear la estación horaria de la coordinadora de producción del módulo...";
                        } else {
                            if (toma >= 9 && toma <= 16) {
                                alerta_text = "Seguro que desea bloquear la estación horaria " + (toma - 1) + " del módulo...";
                            } else {
                                alerta_text = "Seguro que desea bloquear la estación horaria " + ((toma == 18) ? (toma - 2) : toma) + " del módulo...";
                            }
                        }
                        swal({
                            title: "Bloquear Estacion",
                            text: "" + alerta_text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=37&Id_registro=' + id_registro + '&Cbx_frecuencia=' + toma + '&fce=1';
                                });
                    }
                    function DesbloquearEstacionMedia(toma, id_registro) {
                        alerta_text = '';
                        if (toma == 8 || toma == 17) {
                            alerta_text = "Seguro que desea bloquear la estación horaria de la coordinadora de producción del módulo...";
                        } else {
                            if (toma >= 9 && toma <= 16) {
                                alerta_text = "Seguro que desea desbloquear la estación horaria " + (toma - 1) + " del módulo...";
                            } else {
                                alerta_text = "Seguro que desea debloquear la estación horaria " + ((toma == 18) ? (toma - 2) : toma) + " del módulo...";
                            }
                        }
                        swal({
                            title: "Desbloquear Estacion",
                            text: "" + alerta_text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=38&Id_registro=' + id_registro + '&Cbx_frecuencia=' + toma + '&fce=1';
                                });

                    }
                    function RegistroDespeje(id_registro) {
                        swal({
                            title: "Habilitar R. Despeje",
                            text: "Seguro que desea abrir registro de despeje de linea...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=41&Id_registro=' + id_registro;
                                });
                    }
                    function LimpiarModulos(id_registro, modulo) {
                        alerta_text = '';
                        if (modulo == 1) {
                            alerta_text = "Seguro que desea limpiar modulo de parametros de frecuencia por hora...";
                        } else if (modulo == 2) {
                            alerta_text = "Seguro que desea limpiar modulo de verificación de lote y codigo...";
                        } else if (modulo == 3) {
                            alerta_text = "Seguro que desea limpiar modulo de pruebas de calidad...";
                        } else if (modulo == 4) {
                            alerta_text = "Seguro que desea limpiar modulo de parametros de frecuencia cada media hora...";
                        } else if (modulo == 5) {
                            alerta_text = "Seguro que desea limpiar modulo de registros despeje de linea...";
                        } else if (modulo == 6) {
                            alerta_text = "Seguro que desea eliminar el registro...";
                        }
                        swal({
                            title: "Limpiar Modulos",
                            text: "" + alerta_text,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "orange",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Registro?opc=50&Id_registro=' + id_registro + '&Modulo=' + modulo;
                                });
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
                    function pasarDatos(ide) {
                        var id = ide;
                        var content = document.getElementById("Txt_ids").value;
                        if (content.includes(id)) {
                            document.getElementById("Txt_ids").value = content.replace('[' + ide + ']', "");
                        } else {
                            document.getElementById("Txt_ids").value += '[' + ide + ']';
                        }
                    }
                </script>
                <!-- Gif animado control envio -->
                <script type="text/javascript">
                    function Enviar_evento() {
                        window.onload = document.getElementById("sidebar").style.display = "none";
                        window.onload = document.getElementById("content").style.display = "none";
                        window.onload = document.getElementById("Carga").style.display = "block";
                    }
                </script>
                <script>
                    function ejecutarFormEnt() {
                        document.getElementById("FormEntrMate").submit();
                    }
                </script>
                <script type="text/javascript">
                    function Form_registro_cabecera() {
                        document.getElementById('Form_registro').style.display = 'block';
                    }
                    function Form_registro_cabecera_cerrar() {
                        document.getElementById('Form_registro').style.display = 'none';
                    }
                    function Form_limpiar_cabecera() {
                        document.getElementById('Form_limpiar').style.display = 'block';
                    }
                    function Form_limpiar_cabecera_cerrar() {
                        document.getElementById('Form_limpiar').style.display = 'none';
                    }
                    function Form_calidad_cabecera() {
                        document.getElementById('Form_calidad').style.display = 'block';
                    }
                    function Form_calidad_cabecera_cerrar() {
                        document.getElementById('Form_calidad').style.display = 'none';
                    }
                </script>
                <script>
                    function ejectForm(nmb) {
                        document.getElementById("cbx_hora_hm").value = nmb;
                        document.getElementById("form_hora").submit();
                    }
                </script>
                <script>
                    function sendData(data) {
                        document.getElementById("txt_tempo").value = data;
                    }
                </script>
                <script>
                    function ejecutarFormEntVal() {
                        document.getElementById("formValidEnt").submit();
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Registro:Registro_menu />
            <Registro:Registro />

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
    </body>
</html>