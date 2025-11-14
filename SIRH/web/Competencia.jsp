<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@taglib uri="/WEB-INF/tlds/Competencia.tld" prefix="Competencia"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Competencia.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Competencia.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script>
                function Verificar_datos() {
                    var grupos = document.getElementById("Txt_grupos").value;
                    var arg_grupos = grupos.split("-");
                    for (var i = 0; i < arg_grupos.length; i++) {
                        var contenido = document.getElementById("Txt_td_" + arg_grupos[i]).innerHTML;
                        document.getElementById("Txt_" + arg_grupos[i]).value = contenido;
                    }
                }
                function Detalle_formato_competencia(grupo) {
                    var contenido = document.getElementById("Txt_td_" + grupo).innerHTML;
                    document.getElementById("Txt_" + grupo).value = contenido;
                    Verificar_datos();
                }
                function Detalle_formato_competencia_add(grupo) {
                    var contenido = "<hr><b>DEFINICIÓN :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + grupo + "')\"></div><br /><b>CONDUCTA :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + grupo + "')\"></div>"
                    document.getElementById("Txt_td_" + grupo).innerHTML += contenido;
                    Verificar_datos();
                }
                function Detalle_formato_competencia_delete(grupo) {
                    var contenido = document.getElementById("Txt_td_" + grupo).innerHTML;
                    var arg_contenido = contenido.split("<hr>");
                    var removed = arg_contenido.splice(arg_contenido.length - 1, 1);
                    contenido = contenido.replace('<hr>' + removed, '');
                    document.getElementById("Txt_td_" + grupo).innerHTML = contenido;
                    Verificar_datos();
                }
            </script>
            <script>
                function Calcular_total_grupos() {
                    var total = 0;
                    for (var i = 1; i <= 7; i++) {
                        total = (parseInt(total) + parseInt(document.getElementById("Txt_valor_" + i).value));
                    }
                    if (total <= 99) {
                        document.getElementById("Total_grupo").innerHTML = "" + total;
                        document.getElementById("Total_grupo").style.color = "orange";
                        document.getElementById("Btn_registrar_competencia").style.display = "none";
                    } else if (total === 100) {
                        document.getElementById("Total_grupo").innerHTML = "" + total;
                        document.getElementById("Total_grupo").style.color = "green";
                        document.getElementById("Btn_registrar_competencia").style.display = "block";
                        Verificar_datos();
                    } else {
                        document.getElementById("Total_grupo").innerHTML = "" + total;
                        document.getElementById("Total_grupo").style.color = "red";
                        document.getElementById("Btn_registrar_competencia").style.display = "none";
                    }
                }
            </script>
            <script>
                function Arreglo_calificacion(arg) {
                    var detalle_grupo_cal = arg.split("-");
                    var arreglo_final = "";
                    for (var i = 0; i < detalle_grupo_cal.length; i++) {
                        arreglo_final = arreglo_final + "[" + document.getElementById("Id" + detalle_grupo_cal[i]).value + "°" + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value + "°" + document.getElementById("Txt_observacion" + detalle_grupo_cal[i]).innerHTML + "]";
                    }
                    document.getElementById("Txt_arg_calificacion").value = arreglo_final;
                    document.getElementById("Txt_evaluadores").value = document.getElementById("Txt_td_evaluadores").innerHTML;
                    document.getElementById("Txt_recomendacion").value = document.getElementById("Txt_td_recomendacion").innerHTML;
                    /// FormCalificacionComp.submit()
                }
                function Control_recomendaciones() {
                    var recomendaciones = document.getElementById("Txt_td_recomendacion").innerHTML;
                    if (recomendaciones.length > 3) {
                        document.getElementById("Guardar1").style.display = 'block';
                        document.getElementById("Guardar2").style.display = 'block';
                    } else {
                        document.getElementById("Guardar1").style.display = 'none';
                        document.getElementById("Guardar2").style.display = 'none';
                    }
                }

                function Calcular_calificacion(valor, porcentaje, acumulado, grupo, detalle_grupo) {
                    var porcentaje_cal = porcentaje;
                    var acumulado_cal = acumulado;
                    var grupo_cal = grupo;
                    var detalle_grupo_cal = detalle_grupo.split("-");
                    var total_grupo1 = 0;
                    var total_grupo2 = 0;
                    var total_grupo3 = 0;
                    var total_grupo4 = 0;
                    var total_grupo5 = 0;
                    var total_grupo6 = 0;
                    var total_grupos = 0;
                    if (grupo_cal === 1) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo1 = parseFloat(total_grupo1) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo1.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    } else if (grupo_cal === 2) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo2 = parseFloat(total_grupo2) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo2.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    } else if (grupo_cal === 3) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo3 = parseFloat(total_grupo3) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo3.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    } else if (grupo_cal === 4) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo4 = parseFloat(total_grupo4) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo4.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    } else if (grupo_cal === 5) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo5 = parseFloat(total_grupo5) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo5.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    } else if (grupo_cal === 6) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo6 = parseFloat(total_grupo6) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo6.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    }
                    var cant_grupos = document.getElementById("Cant_grupos_mc").value;
                    for (var i = 1; i <= cant_grupos; i++) {
                        var temp = document.getElementById("Result_" + i).innerHTML.replace("Total ", "").replace("%", "");
                        total_grupos = parseFloat(total_grupos) + parseFloat(temp);
                    }
                    document.getElementById("Calificacion_final").innerHTML = total_grupos.toFixed(1) + "%";
                    document.getElementById("Calificacion_final_A5").innerHTML = ((total_grupos.toFixed(1) * 5) / 100).toFixed(1) + "%";
                    document.getElementById("Calificacion_final").style.fontSize = 22;
                    document.getElementById("Calificacion_final").style.color = "#2C3A47";
                    document.getElementById("Txt_calificacion").value = total_grupos.toFixed(1);
                    document.getElementById("Txt_calificacion_grupos").value = "[GENERICAS/" + document.getElementById("Result_1").innerHTML + "]" + "[TECNICAS/" + document.getElementById("Result_2").innerHTML + "]" + "[HUMANAS/" + document.getElementById("Result_3").innerHTML + "]" + "[EDUCACION/" + document.getElementById("Result_4").innerHTML + "]" + "[FORMACION/" + document.getElementById("Result_5").innerHTML + "]" + "[EXPERIENCIA/" + document.getElementById("Result_6").innerHTML + "]";
                    if (cant_grupos === '7') {
                        document.getElementById("Txt_calificacion_grupos").value += "[SGSST/" + document.getElementById("Result_7").innerHTML + "]";
                    }
                    if (total_grupos <= 74) {
                        var recomendaciones = document.getElementById("Txt_td_recomendacion").innerHTML;
                        if (recomendaciones.length > 3) {
                            document.getElementById("Guardar1").style.display = 'block';
                            document.getElementById("Guardar2").style.display = 'block';
                        } else {
                            document.getElementById("Guardar1").style.display = 'none';
                            document.getElementById("Guardar2").style.display = 'none';
                        }
                        if (total_grupos <= 60) {
                            document.getElementById("Txt_td_recomendacion").style.backgroundColor = '#ffdef2';
                        } else {
                            document.getElementById("Txt_td_recomendacion").style.backgroundColor = '#ffffe3';
                        }
                    } else {
                        document.getElementById("Guardar1").style.display = 'block';
                        document.getElementById("Guardar2").style.display = 'block';
                        document.getElementById("Txt_td_recomendacion").style.backgroundColor = '#fff';
                    }
                }
            </script>
            <script type="text/javascript">
                function Arreglo_calificacion_sst(arg) {
                    var detalle_grupo_cal = arg.split("-");
                    var arreglo_final = "";
                    for (var i = 0; i < detalle_grupo_cal.length; i++) {
                        arreglo_final = arreglo_final + "[" + document.getElementById("Id" + detalle_grupo_cal[i]).value + "°" + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value + "°" + document.getElementById("Txt_observacion" + detalle_grupo_cal[i]).innerHTML + "]";
                    }
                    document.getElementById("Txt_arg_calificacion_sst").value = arreglo_final;
                }
                function Calcular_calificacion_sst(valor, porcentaje, acumulado, grupo, detalle_grupo) {
                    var porcentaje_cal = porcentaje;
                    var acumulado_cal = acumulado;
                    var grupo_cal = grupo;
                    var detalle_grupo_cal = detalle_grupo.split("-");
                    var total_grupo1 = 0;
                    var total_grupo2 = 0;
                    var total_grupos = 0;
                    if (grupo_cal === 8) {
                        //total_grupo1 = parseFloat(document.getElementById("Result_" + grupo_cal).innerHTML);
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo1 = parseFloat(total_grupo1) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo1.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    } else if (grupo_cal === 9) {
                        for (var i = 0; i < detalle_grupo_cal.length; i++) {
                            total_grupo2 = parseFloat(total_grupo2) + ((parseFloat(document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value) * porcentaje_cal) / acumulado_cal);
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).innerHTML = "Valor : " + document.getElementById("Rdb_definicion" + detalle_grupo_cal[i]).value;
                            document.getElementById("Dato_" + detalle_grupo_cal[i]).style.color = "#2C3A47";
                        }
                        document.getElementById("Result_" + grupo_cal).innerHTML = "Total " + total_grupo2.toFixed(1) + "%";
                        document.getElementById("Result_" + grupo_cal).style.color = "orange";
                    }
                    for (var i = 8; i <= 9; i++) {
                        var temp = document.getElementById("Result_" + i).innerHTML.replace("Total ", "").replace("%", "");
                        total_grupos = parseFloat(total_grupos) + parseFloat(temp);
                    }
                    document.getElementById("Calificacion_final").innerHTML = total_grupos.toFixed(1) + "%";
                    document.getElementById("Calificacion_final_A5").innerHTML = ((total_grupos.toFixed(1) * 5) / 100).toFixed(1) + "%";
                    document.getElementById("Calificacion_final").style.fontSize = 22;
                    document.getElementById("Calificacion_final").style.color = "#2C3A47";
                    document.getElementById("Txt_calificacion_sst").value = total_grupos.toFixed(1);
                    document.getElementById("Txt_calificacion_sst_grupos").value = "[COMPROMISO FRENTE AL SISTEMA DE GESTION/" + document.getElementById("Result_8").innerHTML + "]" + "[SEGURIDAD Y ENTORNO/" + document.getElementById("Result_9").innerHTML + "]";
                }
            </script>
            <script type="text/javascript">
                function ActivarMcCargo(id_mc_cargo) {
                    swal({
                        title: "Activar Formato de competencias",
                        text: "Seguro que desea abrir el formato de competencias...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Competencia?opc=9&imccgo=' + id_mc_cargo + '&Estado=1';
                            });
                }
                function DesactivarMcCargo(id_mc_calificacion) {
                    swal({
                        title: "Inactivar Formato de competencias",
                        text: "Seguro que desea cerrar el formato de competencias...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Competencia?opc=9&imccgo=' + id_mc_calificacion + '&Estado=0';
                            });
                }
            </script>
            <script type="text/javascript">
                function ActivarCalificacion(id_mc_calificacion) {
                    swal({
                        title: "Abrir Calificación",
                        text: "Seguro que desea abrir la calificación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Competencia?opc=8&imcclf=' + id_mc_calificacion + '&Estado=1';
                            });
                }
                function DesactivarCalificacion(id_mc_calificacion) {
                    swal({
                        title: "Cerrar Calificación",
                        text: "Seguro que desea cerrar la calificación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Competencia?opc=8&imcclf=' + id_mc_calificacion + '&Estado=0';
                            });
                }
                function EliminarCalificacion(id_mc_calificacion) {
                    swal({
                        title: "Eliminar Calificacion",
                        text: "Seguro que desea eliminar la calificación...!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "red",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Competencia?opc=8&imcclf=' + id_mc_calificacion + '&Estado=2';
                            });
                }
            </script>
            <script type = "text/javascript" >
                function SeleccionarPersonalCalificar(datos) {
                    if (datos.checked) {
                        document.getElementById('Txt_seleccion_personal_calificacion').value += "" + datos.value;
                    } else {
                        document.getElementById("Txt_seleccion_personal_calificacion").value = document.getElementById("Txt_seleccion_personal_calificacion").value.replace(datos.value, "");
                    }
                    var valor = document.getElementById('Txt_seleccion_personal_calificacion').value;
                    if (valor.length > 0) {
                        document.getElementById('Btn_save').style.display = "block";
                    } else {
                        document.getElementById('Btn_save').style.display = "none";
                    }
                }
            </script>
        </head>
        <body onload="Verificar_datos()" style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container">
            <div id="page" style="height: 140%;">
                <Alertas:Alertas />
                <Competencia:Competencia />
                <script src="Interfaz/Calendarios/Js_range.js"></script>
                <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
                <script src="Interfaz/Calendarios/Js_normal.js"></script>
            </div>
        </div>
    </body>
</html>
