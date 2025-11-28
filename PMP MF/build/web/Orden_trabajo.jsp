<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/Tlds/Orden_trabajo.tld" prefix="Orden" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Orden_trabajo.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Orden_trabajo.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script>
                function ActividadEjecuto(id_actividad, valor) {
                    var valor_num = parseFloat(valor);
                    var arg_id_act_pen = document.getElementById('Arg_id_actividades_pendientes').value;
                    var cont_ejecutadas = parseInt(document.getElementById('Cont_actividades_ejecutadas').value);
                    if (valor_num === 1) {
                        document.getElementById("Txt_ejecuto_" + id_actividad).innerHTML = "SI";
                        if (arg_id_act_pen.includes("[" + id_actividad + "]")) {
                            arg_id_act_pen = arg_id_act_pen.replace("[" + id_actividad + "]", "");
                        }
                        cont_ejecutadas++;
                    } else if (valor_num === 2) {
                        document.getElementById("Txt_ejecuto_" + id_actividad).innerHTML = "NO";
                        if (arg_id_act_pen.includes("[" + id_actividad + "]")) {
                        } else {
                            arg_id_act_pen += "[" + id_actividad + "]";
                        }
                        cont_ejecutadas = cont_ejecutadas - 1;
                    } else {
                        document.getElementById("Txt_ejecuto_" + id_actividad).innerHTML = "N/A";
                        if (arg_id_act_pen.includes("[" + id_actividad + "]")) {
                            arg_id_act_pen = arg_id_act_pen.replace("[" + id_actividad + "]", "");
                        }
                        cont_ejecutadas++;
                    }
                    document.getElementById('Cont_actividades_ejecutadas').value = cont_ejecutadas;
                    document.getElementById("Arg_id_actividades_pendientes").value = arg_id_act_pen;
                }
                function NoPmp(valor) {
                    var valor_num = parseFloat(valor);
                    if (valor_num === 1) {
                        document.getElementById("No_pmp").value = "PRF";
                    } else if (valor_num === 2) {
                        document.getElementById("No_pmp").value = "GC";
                    } else if (valor_num === 3) {
                        document.getElementById("No_pmp").value = "PER";
                    } else {
                        document.getElementById("No_pmp").value = "N/A";
                    }
                }
                function Verificacion(valor) {
                    var valor_num = parseFloat(valor);
                    if (valor_num === 1) {
                        document.getElementById("Txt_ver_equipo").value = "1";
                    } else if (valor_num === 2) {
                        document.getElementById("Txt_ver_equipo").value = "2";
                    }
                }
                function AddRepuesto() {
                    var rows = document.getElementById('Tr_respuestos_ot').getElementsByTagName('tr').length;
                    document.getElementById("Tr_respuestos_ot").innerHTML += "<tr><td align='center' style='width:5%'>" + (rows + 1) + "</td><td colspan='4' contenteditable='true'>N/A</td></tr>";
                }

                function ConstruirDatos(arg_id_actividades) {
                    var arrayIds = arg_id_actividades.split(",");
                    var datos = "";
                    for (var i = 0; i < arrayIds.length; i++) {
                        datos += "[" + arrayIds[i] + "_/_" + document.getElementById("Txt_ejecuto_" + arrayIds[i]).innerHTML + "_/_" + document.getElementById("Txt_observacion_" + arrayIds[i]).innerHTML + "]";
                    }
                    document.getElementById("Arg_actividades").value = datos;
                    document.getElementById("Txt_datos_pendientes").value = document.getElementById("Td_pendientes_ot").innerHTML;
                    document.getElementById("Txt_datos_descripcion").value = document.getElementById("Td_descripcion_ot").innerHTML;
                    document.getElementById("Txt_datos_repuestos").value = document.getElementById("Tr_respuestos_ot").innerHTML;
                    document.getElementById("Tiempo_trabajo").value = document.getElementById("Txt_tiempo_trabajo").value;
                    document.getElementById("FormGuardarOt").submit();
                }
                function ConstruirDatosAutoclave(arg_id_actividades) {
                    var arrayIds = arg_id_actividades.split(",");
                    var datos = "";
                    for (var i = 0; i < arrayIds.length; i++) {
                        datos += "[" + arrayIds[i] + "_/_" + document.getElementById("Txt_ejecuto_" + arrayIds[i]).innerHTML + "_/_" + document.getElementById("Txt_observacion_" + arrayIds[i]).innerHTML + "]";
                    }
                    document.getElementById("Arg_actividades").value = datos;
                    document.getElementById("Txt_datos_descripcion").value = document.getElementById("Td_descripcion_ot").innerHTML;
                    document.getElementById("Tiempo_trabajo").value = document.getElementById("Txt_tiempo_trabajo").value;
                    document.getElementById("FormGuardarOt").submit();
                }
                function ConstruirDatosAutoclaveMM(arg_id_actividades) {
                    var arrayIds = arg_id_actividades.split(",");
                    var datos = "";
                    for (var i = 0; i < arrayIds.length; i++) {
                        datos += "[" + arrayIds[i] + "_/_" + document.getElementById("Txt_ejecuto_" + arrayIds[i]).innerHTML + "_/_" + document.getElementById("Txt_observacion_" + arrayIds[i]).innerHTML + "]";
                    }
                    document.getElementById("Arg_actividades").value = datos;
                    document.getElementById("Tiempo_trabajo").value = document.getElementById("Txt_tiempo_trabajo").value;
                    document.getElementById("Verificacion").value = document.getElementById("Txt_ver_equipo").value;
                    document.getElementById("FormGuardarOt").submit();
                }
                function ConstruirDatos(arg_id_actividades) {
                    var arrayIds = arg_id_actividades.split(",");
                    var datos = "";
                    for (var i = 0; i < arrayIds.length; i++) {
                        datos += "[" + arrayIds[i] + "_/_" + document.getElementById("Txt_ejecuto_" + arrayIds[i]).innerHTML + "_/_" + document.getElementById("Txt_observacion_" + arrayIds[i]).innerHTML + "]";
                    }
                    document.getElementById("Arg_actividades").value = datos;
                    document.getElementById("Txt_datos_pendientes").value = document.getElementById("Td_pendientes_ot").innerHTML;
                    document.getElementById("Txt_datos_descripcion").value = document.getElementById("Td_descripcion_ot").innerHTML;
                    document.getElementById("Txt_datos_repuestos").value = document.getElementById("Tr_respuestos_ot").innerHTML;
                    document.getElementById("Tiempo_trabajo").value = document.getElementById("Txt_tiempo_trabajo").value;
                    document.getElementById("FormGuardarOt").submit();
                }
                function Devolver(idequipo, idorden, estado, tipo_mtto) {
                    swal({
                        title: "Justificar devolución de OT!",
                        text: "<form action='Orden?opc=15&Id_equipo=" + idequipo + "&Id_orden=" + idorden + "&Estado_ot=" + estado + "&Tipo_mt=" + tipo_mtto + "' id='formVerificacion' method='post'\n\
                                <form action='Orden?opc=15&Id_equipo=" + idequipo + "&Id_orden=" + idorden + "&Estado_ot=" + estado + "&Tipo_mt=" + tipo_mtto + "' id='formVerificacion' method='pst'>\n\
                            <textarea id='form-control' name='Txt_justificacion' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                              <a href='Orden?opc=5&Id_orden=" + idorden + "' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a>\n\
                                &nbsp;&nbsp;<button type='submit' required  form='formVerificacion'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        html: true
                    });
                }
                function DevolverOTGZ(idequipo, idorden, estado, id_tipo_mtto, tipo_mtto) {
                    swal({
                        title: "Justificar devolución de OT!",
                        text: "<form action='Orden?opc=15&Id_equipo=" + idequipo + "&Id_orden=" + idorden + "&Estado_ot=" + estado + "&idTP=" + id_tipo_mtto + "&Tipo_mt=" + tipo_mtto + "' id='formVerificacionZG' method='post'\n\
                                <form action='Orden?opc=15&Id_equipo=" + idequipo + "&Id_orden=" + idorden + "&Estado_ot=" + estado + "&idTP=" + id_tipo_mtto + "&Tipo_mt=" + tipo_mtto + "' id='formVerificacionZG' method='pst'>\n\
                            <textarea id='form-control' name='Txt_justificacion' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                              <a href='Orden?opc=13&Id_orden=" + idorden + "&Id_tipo_mtto=" + id_tipo_mtto + "' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a>\n\
                                &nbsp;&nbsp;<button type='submit' required  form='formVerificacionZG'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        html: true
                    });
                }
                function ActualizarFechasProg(Id,Orden,Equipo) {
                    document.getElementById("Txt_id").value = Id;
                    document.getElementById("Txt_orden").value = Orden;
                    document.getElementById("Equipo").value = Equipo;
                }
                function FirmasProg(Id_orden,Id_equipo) {
                    document.getElementById("Id_orden").value = Id_orden;
                    document.getElementById("Id_equipo").value = Id_equipo;
                }
            </script>
        </head>
        <body id="page-top">
            <div id="wrapper">
            <Menu:Menu />
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <Menu:Sesion />
                    <div class="container-fluid">
                        <Orden:Orden_trabajo />
                    </div>
                </div>
            </div>
        </div>
        <Alertas:Alertas />
        <!-- Bootstrap core JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery/jquery.min.js"></script>
        <script src="Interfaz/StylePage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="Interfaz/StylePage/js/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
        <script src="Interfaz/StylePage/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="Interfaz/StylePage/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <!-- Page level custom scripts -->
        <script src="Interfaz/StylePage/js/demo/datatables-demo.js"></script>
        <!-- Calendarios -->
         <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
    </body>
</html>
