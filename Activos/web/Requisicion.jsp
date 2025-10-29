<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<%@taglib uri="/WEB-INF/tlds/Requisicion.tld" prefix="Requisicion" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--<link rel="stylesheet" type="text/css" href="Img_modal/Img_modal.css">-->
    <title>ACTIVOS</title>
    <jsp:include page="Contenedor_head.jsp"></jsp:include>
        <style type="text/css" media="screen">@import "Interfaz/Tabs/tabs.css";</style>
        <!--<script type="text/javascript" src='Interfaz/Contenido/Scripts/JS_Requisicion.js'></script>-->
        <script type="text/javascript">
            history.pushState(null, null, 'Requisicion.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Requisicion.jsp');
            });
            function Seleccion() {
                document.getElementById("Txt_ids").value = "";
                var ids = "";
                form = document.forms["FormSolicitud"];
                for (i = 0; i < form.elements.length; i++) {
                    if (form.elements[i].type == "checkbox") {
                        if (form.elements[i].checked) {
                            var id = form.elements[i].value;
                            if (ids === "") {
                                ids = id;
                            } else {
                                ids += "-" + id
                            }
                        }
                    }
                }
                swal({
                    title: "¡Confirmar Envio!",
                    text: "¿Se enviara la requisicion(nes) seleccionadas ¿Estas seguro?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#6D256F",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false

                },
                        function () {
                            document.getElementById("Txt_ids").value = ids;
                            document.getElementById('FormSolicitud').submit();
                        });
            }
            function rick(id_req) {
                var id_r = "[" + id_req + "]";
                var content2 = document.getElementById("Txt_ids3").value;
                var content = document.getElementById("Txt_ids2").value;
                if (content.includes(id_r) || content2.includes(id_r)) {
                    document.getElementById("Txt_ids2").value = content.replace(id_r, "");
                    document.getElementById("Txt_ids3").value = content2.replace(id_r, "");
                } else {
                    document.getElementById("Txt_ids2").value += id_r;
                    document.getElementById("Txt_ids3").value += id_r;
                }
            }
            function RegCant(ide) {
                var id = "[" + ide + "]";
                var content1 = document.getElementById("Txt_ids4").value;
                if (content1.includes(id)) {
                    document.getElementById("Txt_ids4").value = content1.replace(id, "");
                } else {
                    document.getElementById("Txt_ids4").value += id;
                }
            }
            function cambiar(select) {
                if (select == "GASTO") {
                    document.getElementById("Txt_gasto").style.display = 'block';
                    document.getElementById("Txt_activo").style.display = 'none';
                    document.getElementById("Txt_activo").value = "N/A";
                } else {
                    document.getElementById("Txt_activo").style.display = 'block';
                    document.getElementById("Txt_gasto").style.display = 'none';
                    document.getElementById("Txt_gasto").value = "N/A";
                }
            }
            function ConstCot(dato) {
                document.getElementById("Txt_ids").value = dato;
                rickardo.submit();
            }
            function ConstCan() {
                var content2 = document.getElementById("Txt_ids").value;
                if (content2.length > 0) {
                    document.getElementById("Txt_ids").value = content2
                    FormConsCan.submit();
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar alguna requisicion para hacer el registro masivo de cantidades",
                        type: "warning",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function Masivo(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("Txt_ids").value;
                if (content.includes(id)) {
                    document.getElementById("Txt_ids").value = content.replace(id, "");
                } else {
                    document.getElementById("Txt_ids").value += id;
                }
            }
            function ConstCanOC() {
                var content2 = document.getElementById("Txt_ids4").value;
                console.log(content2);
                if (content2.length > 0) {
                    document.getElementById("FormConsCan").style.display = "block";
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar alguna requisicion para hacer el registro masivo",
                        type: "warning",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function RegCantDe(ide) {
                var id = "[" + ide + "]";
                var content3 = document.getElementById("Txt_ids6").value;
                if (content3.includes(id)) {
                    document.getElementById("Txt_ids6").value = content3.replace(id, "");
                } else {
                    document.getElementById("Txt_ids6").value += id;
                }
            }
            function SelectMasivoDe() {
                var content3 = document.getElementById("Txt_ids").value;
                console.log(content3);
                if (content3.length > 0) {
                    document.getElementById("FormDispo").style.display = "block";
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar alguna requisicion para hacer el registro masivo",
                        type: "warning",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function RegCantPC(ide) {
                var id = "[" + ide + "]";
                var content4 = document.getElementById("Txt_ids8").value;
                if (content4.includes(id)) {
                    document.getElementById("Txt_ids8").value = content4.replace(id, "");
                } else {
                    document.getElementById("Txt_ids8").value += id;
                }
            }
            function SelectMasivoPC() {
                var content4 = document.getElementById("Txt_ids").value;
                console.log(content4);
                console.log(content4.length);
                if (content4.length > 0) {
                    document.getElementById("FormSelectProCo").style.display = "block";
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar alguna requisicion para hacer el registro masivo",
                        type: "warning",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function Abrir_img_req(imgs) {
                var img = document.getElementById(imgs);
                var modal = document.getElementById('popUp2');
                var modalImg = document.getElementById("imgReq");
                modal.style.display = "block";
                modalImg.src = img.src;
            }
            function DetCotMasivo() {
                var content2 = document.getElementById("Txt_ids").value;
                if (content2.length > 0) {
                    document.getElementById('FormCotizacionM').style.display = 'block';
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar alguna requisicion para hacer el registro masivo",
                        type: "warning",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function DetOCMasivo() {
                var content2 = document.getElementById("Txt_ids").value;
                if (content2.length > 0) {
                    document.getElementById('FormOrdenCompraM').style.display = 'block';
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar alguna requisicion para hacer el registro masivo",
                        type: "warning",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function Traer_obs(id) {
                var content = document.getElementById("Txt_obs" + id).innerHTML;
                document.getElementById("Txt_observacion" + id).value = content;
            }
            function Cambio_est(etd, etd2) {
                document.getElementById("estado").value = etd;
                document.getElementById("estado2").value = etd2;
            }
            function DeclinarYDevolver(idRequisicion, est, mdo) {
                swal({
                    title: "Justificar declinacion de Solicitud!",
                    text: "<form action='Requisicion?opc=9&idRequisicion=" + idRequisicion + "&estado=" + est + "&modulo=" + mdo + "' id='formVerificacion' method='post'<form action='Requisicion?opc=9&idRequisicion=" + idRequisicion + "&estado=" + est + "&modulo=" + mdo + "' id='formVerificacion' method='pst'>\n\
                            <textarea name='Txt_justificacion' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                              <a href='Requisicion?opc=1&idRequisicion=0' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a>\n\
                                &nbsp;&nbsp;<button type='submit' required  form='formVerificacion'>Enviar</button>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }
            function DeclinarYDevolverGN(idRequisicion, est, mdo) {
                swal({
                    title: "Justificar declinacion de Solicitud!",
                    text: "<form action='Requisicion?opc=9&idRequisicion=" + idRequisicion + "&estado=" + est + "&modulo=" + mdo + "' id='formVerificacion' method='post'<form action='Requisicion?opc=9&idRequisicion=" + idRequisicion + "&estado=" + est + "&modulo=" + mdo + "' id='formVerificacion' method='pst'>\n\
                            <textarea name='Txt_justificacion' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                              <a href='Requisicion?opc=36&estado=" + mdo + "' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a>\n\
                                &nbsp;&nbsp;<button type='submit' required  form='formVerificacion'>Enviar</button>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }
            function ConfirmarRequisicionDecli(idRequisicion, mdo) {
                swal({
                    title: "Confirmar Requisición!",
                    text: "<form action='Requisicion?opc=9&idRequisicion=" + idRequisicion + "&modulo=" + mdo + "' id='formVerificacion' method='post'\n\
                            <form action='Requisicion?opc=9&idRequisicion=" + idRequisicion + "&modulo=" + mdo + "' id='formVerificacion' method='post'>\n\
                            <b>Estado:</b><br><select name='estado' id='estado' ><option value='1'>SOLICITUD</option><option value='2'>COTIZACION</option><option value='8'>PROCESO COMPRA</option><option value='3'>ORDEN COMPRA</option></select><br><br>   \n\
                              <b>Justificación:</b><br><textarea name='Txt_justificacion' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                                <a href='Requisicion?opc=17' id='formVolver' method='post''><button type='submit' required  form='formVolver'>Volver</button></a>&nbsp;&nbsp;\n\
                                    <button type='submit' required  form='formVerificacion'>Enviar</button>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }
            function DuplicarRequisicion(idRequisicion, est, mdo) {
                swal({
                    title: "Duplicar Solicitud!",
                    text: "<form action='Requisicion?opc=40&idRequisicion=" + idRequisicion + "' id='formVerificacion' method='post'\n\
                            <form action='Requisicion?opc=40&idRequisicion=" + idRequisicion + "&estado=" + est + "&modulo=" + mdo + "' id='formVerificacion' method='post'>\n\
                             <textarea name='Txt_observaciones' placeholder='ID: " + idRequisicion + "' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                              <a href='Requisicion?opc=1&estado=" + mdo + "' id='formVolver' method='post''><button type='submit' required  form='formVolver'>Volver</button></a>&nbsp;&nbsp;\n\
                                <button type='submit' required  form='formVerificacion'>Enviar</button>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }

            function Liberar(idRequisicion, etd) {
                swal({
                    title: "Liberar!",
                    text: "¿Seguro que Desea enviar a Descargas?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#6D256F",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function () {
                            location.href = "Requisicion?opc=16&idRequisicion=" + idRequisicion + "&estado=" + etd;
                        });
            }
            function Final(idRequisicion, etd, mdo) {
                swal({
                    title: "¡Entregar!<br>Nombre de quien recibe",
                    text: "<form action='Requisicion?opc=16&idRequisicion=" + idRequisicion + "&estado=" + etd + "&modulo=" + mdo + "' id='formVerificacion' method='post'<form action='Requisicion?opc=8&idRequisicion=" + idRequisicion + "&estado=" + etd + "&modulo=" + mdo + "' id='formVerificacion' method='pst'><textarea name='Txt_nombre' style='margin: 0px 0px 10px; width: 250px; height: 20px;' required></textarea></form><a href='Requisicion?opc=" + mdo + "&idRequisicion=0' id='formVolver' method='post''><button type='submit' required  form='formVerificacion'>Volver</button></a>&nbsp;&nbsp;<button type='submit' required  form='formVerificacion'>Entregar</button>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }
            function pasar(campo) {
                campo.focus();
            }
            

        </script>
        <script>
            function MatrizRegistro(valor) {
                var val = valor.trim();
                val = val.replace(/\	/gi, ",").replace(/\n/gi, "\n");
                document.getElementById('Txt_Matriz').value = "" + val;
                if (valor.length > 0) {
                    var new_n = valor.replace(/\)/gi, "\n");
                    var n = new_n.split('\n').map(parseFloat);
                    var max_n = Math.max.apply(null, n);
                    if (max_n != "") {
                        document.getElementById("Txt_Matriz").readOnly = true;
                    } else {
                        document.getElementById("Txt_Matriz").readOnly = false;
                    }
                }
            }
        </script>
        <script type="text/javascript">
            function timer() {
                swal({
                    title: 'Favor espere un momento, se esta enviando un correo con las solicitudes!',
                    text: '<i class="fas fa-spinner fa-spin" style="font-size: 50px;color: #00281b;"></i>',
                    type: 'warning',
                    buttons: false,
                    showConfirmButton: false,
                    allowEscapeKey: false,
                    dangerMode: true,
                    html: true
                });
            }
        </script>
        <script>
            function Imprimir() {
                var contenedor = document.getElementById("Imprimir").innerHTML;
                var frame = document.createElement("iframe");
                frame.name = "frame1";
                frame.style.position = "absolute";
                frame.style.top = "-1000000px";
                document.body.appendChild(frame);
                var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
                frameDoc.document.open();
                frameDoc.document.write('<link href="Interfaz/Contenido/Css/Css_General.css" rel="stylesheet" type="text/css" />');
                //frameDoc.document.write('<link href="Interfaz/MasterPage/default.css" rel="stylesheet" type="text/css" />');
                frameDoc.document.write('</head><body>');
                frameDoc.document.write(contenedor);
                frameDoc.document.write('</body></html>');
                frameDoc.document.close();
                setTimeout(function () {
                    window.frames["frame1"].focus();
                    window.frames["frame1"].print();
                    document.body.removeChild(frame);
                }, 50);
                return false;
            }
        </script>
        <script>
            function limpiar() {
                document.getElementById("Txt_Matriz").value = "";
                document.getElementById("Txt_Matriz").readOnly = false;
            }
        </script>
        <script>
            function priodidad_fecha() {
                var fecha1 = new Date(document.getElementById("end").value).getTime();
                var fecha2 = new Date();
                var diff = fecha1 - fecha2;
                var day_as_milliseconds = 86400000;
                var diff_in_days = diff / day_as_milliseconds;
                if (diff_in_days >= 2) {
                    document.getElementById("Rdb_prioridad0").checked = true;
                    document.getElementById("Rdb_prioridad1").checked = false;
                } else {
                    document.getElementById("Rdb_prioridad1").checked = true;
                    document.getElementById("Rdb_prioridad0").checked = false;
                }
            }
        </script>
        <script>
            function FiltroAvanzado(e) {
                tecla = (document.all) ? e.keyCode : e.which;
                if (tecla === 43) {
                    var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
                    if (filtro !== "") {
                        document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                        document.getElementById('Buscar_valores').innerHTML += "<a href='#' onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\" style='text-decoration:none'>" + filtro + "</a><br />";
                    }
                    document.getElementById('Txt_filtro_avanzado').value = "";
                }
            }
            function FiltroAvanzadoQuitar(e) {
                var valor = document.getElementById('Txt_valores_filtro').value;
                document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
                var vista = document.getElementById('Buscar_valores').innerHTML;
                var elim = "<a href=\"#\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\" style=\"text-decoration:none\">" + e + "</a><br>";
                document.getElementById('Buscar_valores').innerHTML = "";
                document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
            }
        </script>
        <script>
            function Observacion() {
                FormObservacion.submit();
            }
            function Masivo(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("Txt_ids").value;
                if (content.includes(id)) {
                    document.getElementById("Txt_ids").value = content.replace(id, "");
                } else {
                    document.getElementById("Txt_ids").value += id;
                }
            }
            function MasivoReporte(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("txt_arg_requisicion").value;
                if (content.includes(id)) {
                    document.getElementById("txt_arg_requisicion").value = content.replace(id, "");
                } else {
                    document.getElementById("txt_arg_requisicion").value += id;
                }
            }
        </script>
        <script>
            function seleccionarTodos() {
                var checkboxes = document.querySelectorAll('input[type="checkbox"]');
                checkboxes.forEach(function (checkbox) {
                    checkbox.checked = true;
                    Masivo(checkbox.value); // Llamar a la función Masivo para cada checkbox
                });
            }

            function deseleccionarTodos() {
                var checkboxes = document.querySelectorAll('input[type="checkbox"]');
                checkboxes.forEach(function (checkbox) {
                    checkbox.checked = false;
                    Masivo(checkbox.value); // Llamar a la función Masivo para cada checkbox
                });
            }
        </script>
        <script>
            function toggle(source) {
                checkboxes = document.getElementsByName('Cbx_Solicitud');
                for (var i = 0, n = checkboxes.length; i < n; i++) {
                    checkboxes[i].checked = source.checked;
                }
            }
        </script>
        <script type="text/javascript">
            function comprobar(obj)
            {
                if (obj.checked) {
                    document.getElementById('Valboton').style.display = "";
                } else {
                    document.getElementById('Valboton').style.display = "none";
                }
            }
            function DiasVencidos(obj)
            {
                if (obj.checked) {
                    document.getElementById('Valdiv').style.display = "";
                    document.getElementById('Valcampo').style.display = "";
                } else {
                    document.getElementById('Valdiv').style.display = "block";
                    document.getElementById('Valcampo').style.display = "none";
                }
            }
        </script>
        <script src="http://code.jquery.com/jquery-1.0.4.js"></script>
        <script>
            $(document).ready(function () {
                $("#Txt_referencias").keyup(function () {
                    var value = $(this).val();
                    $("#elemento").val(value);
                });
            });
        </script>
        <script>
            function ex_form() {
                document.getElementById("form_filterArea").submit();
            }
            function VaciarContenido() {
                document.getElementById("Txt_filtro").value = "";
                document.getElementById("InputCheck1").checked = false;
                document.getElementById("InputCheck2").checked = false;
            }
        </script>
    </head>
    <body id="subpage">
        <div id="templatemo_wrapper">
        <menu:Menu/>
        <alertas:Alertas/>
        <Requisicion:Requisicion/>
        <div id="popUp2" class="modal2" onclick="javascript:document.getElementById('popUp2').style.display = 'none'">
            <img class="modal2-content" id="imgReq" style="width: auto; height: auto;"/>
        </div>
        <script type="text/javascript" src="Interfaz/Tabs/tabs.js"></script>
    </div>
    <script src="Interfaz/Calendarios/Js_range.js" type="text/javascript"></script>
    <script src="Interfaz/Calendarios/Js_range_altenativo.js" type="text/javascript"></script>
    <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
    <script src="Interfaz/Contenido/Scripts/ValidarCampo.js" type="text/javascript"></script>
</body>