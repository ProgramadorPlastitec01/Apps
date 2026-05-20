<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_formRegistro.tld" prefix="formRegistro" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<!DOCTYPE html>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Registro</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <link rel="stylesheet" href="Interfaz/Contenido/Css/tooltip.css" type="text/css">
        </head>

        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu/>
            <formRegistro:FormRegistro/>
        </div>
        <script>
            function abrirModal(num) {
                var count = num;
                document.getElementById("contador").value = count;
                var modal = document.getElementById("modal");
                modal.style.display = "block";
            }
            function cerrarModal() {
                var modal = document.getElementById("modal");
                modal.style.display = "none";
            }
            function abrirModalEditar(idDesc) {
                document.getElementById("idDescNuevo").value = "";
                document.getElementById("idDescNuevo").value += idDesc;
                var modal = document.getElementById("modalEditar");
                modal.style.display = "block";
            }
            function cerrarModalEditar() {
                var modal = document.getElementById("modalEditar");
                modal.style.display = "none";
            }
            function ventana(num) {
                if (document.getElementById("formulario" + num).style.display === "none") {
                    document.getElementById("formulario" + num).style.display = "block";
                } else if (document.getElementById("formulario" + num).style.display === "block") {
                    document.getElementById("formulario" + num).style.display = "none";
                }
            }
            function lineaSellado(num1) {
                var count1 = num1;
                document.getElementById("countLiSe").value = count1;
                var modal1 = document.getElementById("modalLineaSellado");
                modal1.style.display = "block";
            }
            function lineaSelladoCerrar() {
                var modal = document.getElementById("modalLineaSellado");
                modal.style.display = "none";
            }
            function lineaSelladoEditar(idDesc2) {
                document.getElementById("idDescNuevoLiSellado").value = "";
                document.getElementById("idDescNuevoLiSellado").value += idDesc2;
                var modal = document.getElementById("modalEditar");
                modal.style.display = "block";
            }

            function equipoBocasGuardar(num) {
                var count = num;
                document.getElementById("countBocas").value = count;
                var modal = document.getElementById("modalEquBocas");
                modal.style.display = "block";
            }

            function equipoBocasCerrar() {
                var modal = document.getElementById("modalEquBocas");
                modal.style.display = "none";
            }

            function equipoBocasEditar(idDesc) {
                document.getElementById("idDescBocas").value = "";
                document.getElementById("idDescBocas").value += idDesc;
                var modal = document.getElementById("modalEditar");
                modal.style.display = "block";
            }

            function equipoPpGuardar(num) {
                var count = num;
                document.getElementById("countPp").value = count;
                var modal = document.getElementById("modalEquPp");
                modal.style.display = "block";
            }

            function equipoPpCerrar() {
                var modal = document.getElementById("modalEquPp");
                modal.style.display = "none";
            }

            function equipoColpittGuardar(num) {
                var count = num;
                document.getElementById("countColpitt").value = count;
                var modal = document.getElementById("modalEquColpitt");
                modal.style.display = "block";
            }

            function equipoColpittCerrar() {
                var modal = document.getElementById("modalEquColpitt");
                modal.style.display = "none";
            }

            function guardarFirma(id) {

                document.getElementById("id").value = id;



                var modal = document.getElementById("modalFirma");
                modal.style.display = "block";


            }

            function guardarFirma2(formatoAnti, id2, camp1, camp2, tipoFirma, camp3) {
                document.getElementById("formatoAnti").value = formatoAnti;
                document.getElementById("id2").value = id2;
                document.getElementById("camp1").value = camp1;
                document.getElementById("camp2").value = camp2;
                document.getElementById("tipoFirma").value = tipoFirma;
                document.getElementById("camp3").value = camp3;
                var modal = document.getElementById("modalFirma2");
                modal.style.display = "block";
            }

            function firmaCerrar() {
                var modal = document.getElementById("modalFirma");
                modal.style.display = "none";
            }

            function firmaCerrar2() {
                var modal = document.getElementById("modalFirma2");
                modal.style.display = "none";
            }

            function ConfirmarFirma() {
                swal({
                    title: "Confirmar",
                    text: "¿Está seguro de que desea firmar este control? Recuerde que una vez firmado, no se podrá modificar.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                }, function () {
                    // Ejecuta el formulario de la firma de calidad
                    document.getElementById("firma").submit();
                });
            }

            function ConfirmarFirma2() {
                swal({
                    title: "Confirmar",
                    text: "¿Está seguro de que desea firmar este control? Recuerde que una vez firmado, no se podrá modificar.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                }, function () {
                    // Ejecuta el formulario de la firma de calidad
                    document.getElementById("firma3").submit();
                });
            }

            // Alerta para eliminar la descripcion de la falla
            function eliminarDescripcion(idRegistro, idLn, desc, idDesc) {
                const descCodificada = encodeURIComponent(desc); // Se codifica la descripcion para permitir eliminar si se envian caracteres especiales.
                swal({
                    title: "Eliminar!",
                    text: "¿Está seguro de desea eliminar este item?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = 'Registro?op=5&idRegistro=' + idRegistro + '&idLinea=' + idLn + '&idDesc=' + idDesc + '&desc=' + descCodificada;
                });
            }
            // Alerta para eliminar la descripcion inspeccion rutinaria
            function eliminarLineaSellado(idRegistro, campo1, campo2, desc) {
                const descCodificada = encodeURIComponent(desc); // Se codifica la descripcion para permitir eliminar si se envian caracteres especiales.
                swal({
                    title: "Eliminar!",
                    text: "¿Está seguro de desea eliminar este item?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = "Registro?op=8&idRegistro=" + idRegistro + "&idEquiLinSell=" + campo1 + "&idDescLinSell=" + campo2 + "&desLiSe=" + descCodificada;
                });
            }

            function eliminarItem(idRegistro, desc, opcion) {
                swal({
                    title: "Eliminar!",
                    text: "¿Está seguro de desea eliminar este item?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = "Registro?op=10&idRegistro=" + idRegistro + "&desc=" + desc + "&opcion=" + opcion;
                });
            }

            function confirmarFirmaJefe(idVerifica, idRegistro) {
                var generalObservacion = document.getElementById("input-text").value;

                swal({
                    title: "Firmar!",
                    text: "¿Está seguro de que desea firmar este registro? Tenga en cuenta que una vez que lo firme, no podrá eliminar la firma.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function (isConfirm) {
                    if (isConfirm) {
                        location.href = "Registro?op=13&idRegistro=" + idRegistro + "&idVerifica=" + idVerifica + "&observacion=" + encodeURIComponent(generalObservacion);
                    }
                });
            }

            function eliminarFirmaItem(idRegistro, formatoAnt, firma, tipoFirma) {
                const descCodificada1 = encodeURIComponent(formatoAnt); // Se codifica la descripcion para permitir eliminar si se envian caracteres especiales.
                const descCodificada2 = encodeURIComponent(firma); // Se codifica la descripcion para permitir eliminar si se envian caracteres especiales.

                swal({
                    title: "Firmar!",
                    text: "¿Está seguro de que desea eliminar la firma? Tenga en cuenta que una vez eliminada solo podra firmar el personal de calidad.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    if (tipoFirma === 1) {
                        location.href = "Registro?op=18&idRegistro=" + idRegistro + "&formatoAntiguo=" + descCodificada1 + "&firma=" + descCodificada2 + "&tipoFirma=1";
                    } else if (tipoFirma === 2) {
                        location.href = "Registro?op=18&idRegistro=" + idRegistro + "&formatoAntiguo=" + descCodificada1 + "&firma=" + descCodificada2 + "&tipoFirma=2";
                    } else if (tipoFirma === 3) {
                        location.href = "Registro?op=18&idRegistro=" + idRegistro + "&formatoAntiguo=" + descCodificada1 + "&firma=" + descCodificada2 + "&tipoFirma=3";
                    } else if (tipoFirma === 4) {
                        location.href = "Registro?op=18&idRegistro=" + idRegistro + "&formatoAntiguo=" + descCodificada1 + "&firma=" + descCodificada2 + "&tipoFirma=4";
                    }
                });
            }

            function eliminarFirmaItemDesafios(idRegistro, formatoAnt, firma) {
                swal({
                    title: "Firmar!",
                    text: "¿Está seguro de que desea eliminar la firma? Tenga en cuenta que una vez eliminada solo podra firmar el personal de calidad.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = "Registro?op=18&idRegistro=" + idRegistro + "&formatoAntiguo=" + formatoAnt + "&firma=" + firma + "&tipoFirma=2";
                });
            }

            function pasarTexto(div, input) {
                // Obtiene el texto del div
                const texto = div.textContent;

                // Establece el valor del input
                input.value = texto;
            }

            function printSection(el) {
                var getFullContent = document.body.innerHTML;
                var printsection = document.getElementById(el).innerHTML;
                document.body.innerHTML = printsection;
                window.print();
                document.body.innerHTML = getFullContent;
            }

            function confirmarCerrarRegistro(idRegistro, Temp1) {
                swal({
                    title: "Firmar!",
                    text: "¿Está seguro de cerrar esta bitacora? Tenga en cuenta que una ves cerrada, no se podra abrir a excepción de un superior",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = "Registro?op=14&est=0&idRegistro=" + idRegistro + "&Temp1=" + Temp1;
                });
            }

        </script>

       <script type = "text/javascript" >
            history.pushState(null, null, 'registro.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'registro.jsp');
            });
        </script>

        <script>
            function controlTexto() {
                var prohibido = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var descdatos = document.getElementById('descripcion');
                var descValue = descdatos.value;
                var botonAgregar = document.getElementById('botonAgregar');

                for (var j = 0; j < prohibido.length; j++) {
                    if (descValue.includes(prohibido[j])) {
                        swal({
                            title: 'Informacion',
                            text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                            type: 'info',
                            timer: 1500,
                            showConfirmButton: false
                        });
                        descValue = descValue.replaceAll(prohibido[j], '');
                        descdatos.value = descValue;
                        return false;
                    }
                }
                return true;
            }
        </script>

        <script>
            function validarTexto() {
                var simbolos = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var textareaValue = document.getElementById("desc");
                var description = textareaValue.value;
                var botonEditar = document.getElementById("botonEditar");
                for (var i = 0; i < simbolos.length; i++) {
                    if (description.includes(simbolos[i])) {
                        swal({
                            title: 'Informacion',
                            text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                            type: 'info',
                            timer: 1500,
                            showConfirmButton: false
                        });
                        description = description.replaceAll(simbolos[i], '');
                        textareaValue.value = description;
                        return false;
                    }

                }

                return true;
            }

        </script>

        <script type="text/javascript">
            function controltextosellado() {
                var nopaso = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var campos = ['campo2', 'campo3', 'campo4', 'campo5', 'campo6', 'campo7', 'campo8', 'campo9'];
                var botonenviar = document.getElementById('subir');

                for (var c = 0; c < campos.length; c++) {
                    var textvalue = document.getElementById(campos[c]);
                    var infor = textvalue.value;

                    for (var o = 0; o < nopaso.length; o++) {
                        if (infor.includes(nopaso[o])) {
                            swal({
                                title: 'Informacion',
                                text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                                type: 'info',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            infor = infor.replaceAll(nopaso[o], '');
                            textvalue.value = infor;
                            return false;
                        }
                    }
                }
                return true;
            }
            function controltextosellado250414() {
                var nopaso = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var campos = ['campo2', 'campo3', 'campo4', 'campo5', 'campo6', 'campo7', 'campo8', 'campo9', 'campo10'];
                var botonenviar = document.getElementById('subir');

                for (var c = 0; c < campos.length; c++) {
                    var textvalue = document.getElementById(campos[c]);
                    var infor = textvalue.value;

                    for (var o = 0; o < nopaso.length; o++) {
                        if (infor.includes(nopaso[o])) {
                            swal({
                                title: 'Informacion',
                                text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                                type: 'info',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            infor = infor.replaceAll(nopaso[o], '');
                            textvalue.value = infor;
                            return false;
                        }
                    }
                }
                return true;
            }
            
            function controltextosellado260521() {
                var nopaso = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var campos = ['campo2', 'campo3', 'campo4', 'campo5', 'campo6', 'campo7', 'campo8', 'campo9', 'campo10', 'campo11'];
                var botonenviar = document.getElementById('subir');

                for (var c = 0; c < campos.length; c++) {
                    var textvalue = document.getElementById(campos[c]);
                    var infor = textvalue.value;

                    for (var o = 0; o < nopaso.length; o++) {
                        if (infor.includes(nopaso[o])) {
                            swal({
                                title: 'Informacion',
                                text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                                type: 'info',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            infor = infor.replaceAll(nopaso[o], '');
                            textvalue.value = infor;
                            return false;
                        }
                    }
                }
                return true;
            }

            function verificacionTextoSellado() {
                var noti = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var campose = ['campoM2', 'campoM3', 'campoM4', 'campoM5', 'campoM6', 'campoM7', 'campoM8', 'campoM9'];
                for (var e = 0; e < campose.length; e++) {
                    var editarvalue = document.getElementById(campose[e]);
                    var comunicado = editarvalue.value;
                    for (var m = 0; m < noti.length; m++) {
                        if (comunicado.includes(noti[m])) {
                            swal({
                                title: 'Informacion',
                                text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                                type: 'info',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            comunicado = comunicado.replaceAll(noti[m], '');
                            editarvalue.value = comunicado;
                            return false;
                        } else {
                        }
                    }
                }
                return true;
            }
            function verificacionTextoSellado250414() {
                var noti = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var campose = ['campoM2', 'campoM3', 'campoM4', 'campoM5', 'campoM6', 'campoM7', 'campoM8', 'campoM9','campoM10'];
                for (var e = 0; e < campose.length; e++) {
                    var editarvalue = document.getElementById(campose[e]);
                    var comunicado = editarvalue.value;
                    for (var m = 0; m < noti.length; m++) {
                        if (comunicado.includes(noti[m])) {
                            swal({
                                title: 'Informacion',
                                text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                                type: 'info',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            comunicado = comunicado.replaceAll(noti[m], '');
                            editarvalue.value = comunicado;
                            return false;
                        } else {
                        }
                    }
                }
                return true;
            }
            function verificacionTextoSellado260520() {
                var noti = ['---', '+', '*', '#', '|', '///', '°', '¬', '$', '&', '=', '^', '_', '~', '[', ']'];
                var campose = ['campoM2', 'campoM3', 'campoM4', 'campoM5', 'campoM6', 'campoM7', 'campoM8', 'campoM9','campoM10','campoM11'];
                for (var e = 0; e < campose.length; e++) {
                    var editarvalue = document.getElementById(campose[e]);
                    var comunicado = editarvalue.value;
                    for (var m = 0; m < noti.length; m++) {
                        if (comunicado.includes(noti[m])) {
                            swal({
                                title: 'Informacion',
                                text: 'No se puede usar caracteres especiales en la descripción. Como por ejemplo: ---, +, *, #, |, ///, °, ¬, $, &, =, ^, _, ~, [, ]',
                                type: 'info',
                                timer: 1500,
                                showConfirmButton: false
                            });
                            comunicado = comunicado.replaceAll(noti[m], '');
                            editarvalue.value = comunicado;
                            return false;
                        } else {
                        }
                    }
                }
                return true;
            }

            function updateTextInput() {
                var divContent = document.getElementById('div-editable').innerText;
                document.getElementById('input-text').value = divContent;
            }

            function Guardarinfo() {
                var id = document.getElementById("id").value;



                sessionStorage.setItem("id", id);



                var Temp2 = 1;
                document.getElementById("Temp2").value = Temp2;
                document.getElementById("firma2").submit();
            }

            window.onload = function () {
                var id = sessionStorage.getItem("id");

                document.getElementById("id").value = id;

                sessionStorage.removeItem("id");

            };
        </script>



        <resultados:MuestraResultados />
    </body>
</html>
