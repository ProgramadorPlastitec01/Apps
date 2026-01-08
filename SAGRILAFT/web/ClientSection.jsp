<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="Alerts" %>
<%@taglib uri="/WEB-INF/tlds/tld_ClientSection.tld" prefix="Client" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cient | SGLT</title>
        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'ClientSection.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'ClientSection.jsp');
                    });
                </script>-->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/Fonts/FontStyle.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/WP_Sag2.png" />

        <style>
            .nav-link .active {
                background: #5ecbeb;
            }
            .signature-pad {
                margin-bottom: 20px;
            }

            .canvas-container {
                margin-bottom: 20px;
            }

            canvas {
                border: 1px solid #000;
            }

            button {
                margin-top: 10px;
            }
        </style>

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="BaseClient.jsp"></jsp:include>
                    <div class="" style="min-height: 700px; padding: 30px;">
                    <Client:ClientSection/>
                </div>
            </div>
        </div>
        <Alerts:AlertModule/>
        <script>
            function ToActiveShield(idValid, idShl, valid) {
                const isChecked = document.getElementById(idValid).checked;
                if (isChecked) {
                    document.getElementById(idShl).setAttribute("type", "text");
                } else {
                    document.getElementById(idShl).setAttribute("type", "hidden");
                }
                if (valid === "Si") {
                    document.getElementById("ContResolution").style.display = "block";
                } else if (valid === "No") {
                    document.getElementById("ContResolution").style.display = "none";

                }
            }
        </script>

        <script>
            function validEmpyData(field, message, lang) {
                var data = document.getElementById(field).value;
                if (data == "") {
                    iziToast.warning({
                        title: 'Atencion!',
                        message: message,
                        position: 'bottomRight'
                    });
                    event.preventDefault();
                } else {
                    ValidActionNew('TxtValidAction', 1);
                    ExcuteForm(lang);
                }
            }
        </script>

        <script>
            function ValidAction(idShl, Val) {
                document.getElementById(idShl).value = Val;
            }
        </script>
        <script>
            function ValidActionNew(idShl, Val) {
                var Valid = document.getElementById("IdTypeSigna").value;
                document.getElementById(idShl + Valid).value = Val;
            }
        </script>

        <script>
            function DataReplace(val) {
                document.getElementById("IdTypeSig").value = val;
                document.getElementById("IdTypeSigx").value = val;
            }
        </script>

        <script>
            function DataReplaceV2(val) {
                document.getElementById("IdTypeSigna").value = val;
            }
        </script>

        <script>
            function ExcuteForm(lg) {
                var typeSig = document.getElementById("IdTypeSigna").value;
                var Doc = document.getElementById("Doc_security");
                if (lg == 'es') {
                    if (Doc.checked) {
//                        if (typeSig == 1) {
                        if (typeSig == 3) {
//                            var signat = document.getElementById("coordenadas-hidden").value;
                            var signat = document.getElementById("file-input").value;
                            if (signat === "") {
                                iziToast.warning({
                                    title: 'Atencion!',
                                    message: 'Debe firmar el documento de acuerdo de seguridad.',
                                    position: 'bottomRight'
                                });
                            } else {
                                document.getElementById("SignForm" + typeSig).submit();
                            }
                        } else {
                            document.getElementById("SignForm" + typeSig).submit();

                        }
                    } else {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'Debe marcar la casilla de confirmación de lectura del acuerdo.',
                            position: 'bottomRight'
                        });
                    }
                } else {
                    if (Doc.checked) {
                        document.getElementById("SignForm" + typeSig).submit();
                    } else {
                        iziToast.warning({
                            title: 'Attention!',
                            message: 'You must check the agreement reading confirmation box.',
                            position: 'bottomRight'
                        });
                    }
                }
            }
        </script>


        <script>
            function ExcuteFormV2(lg) {
                var typeSig = document.getElementById("IdTypeSigna").value;
                if (lg == 'es') {
//                        if (typeSig == 1) {
                    if (typeSig == 3) {
//                            var signat = document.getElementById("coordenadas-hidden").value;
                        var signat = document.getElementById("file-input").value;
                        if (signat === "") {
                            iziToast.warning({
                                title: 'Atencion!',
                                message: 'Debe firmar el documento de acuerdo de seguridad.',
                                position: 'bottomRight'
                            });
                        } else {
                            document.getElementById("SignForm" + typeSig).submit();
                        }
                    } else {
                        document.getElementById("SignForm" + typeSig).submit();

                    }
                } else {
                    if (Doc.checked) {
                        document.getElementById("SignForm" + typeSig).submit();
                    } else {
                        iziToast.warning({
                            title: 'Attention!',
                            message: 'You must check the agreement reading confirmation box.',
                            position: 'bottomRight'
                        });
                    }
                }
            }
        </script>

        <script>
            function validImg(lg, nm) {
                var signat = document.getElementById("file-input").value;
                if (signat === "") {
                    if (lg == 'es') {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'Debe firmar el documento de acuerdo de seguridad.',
                            position: 'bottomRight'
                        });
                    } else {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'You must sign the security agreement document.',
                            position: 'bottomRight'
                        });
                    }
                } else {
                    ValidAction('TxtValidActionImg', nm);
                }
            }
        </script>

        <script>
            function validImg2(lg, nm) {
                var signat = document.getElementById("file-input").value;
                if (signat === "") {
                    if (lg == 'es') {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'Debe firmar el documento de acuerdo de seguridad.',
                            position: 'bottomRight'
                        });
                    } else {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'You must sign the security agreement document.',
                            position: 'bottomRight'
                        });
                    }
                } else {
                    ValidAction('TxtValidActionImg', nm);
                    document.getElementById("FromSignaturImgg").submit();
                }
            }
        </script>

        <script>
            function MoveData(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("TxtCertifications").value;
                if (content.includes(id)) {
                    document.getElementById("TxtCertifications").value = content.replace(id, "");
                } else {
                    document.getElementById("TxtCertifications").value += id;
                }
            }
        </script>

        <script>
            function MoveDataSelected(ide, shield) {
                var id = "[" + ide + "]";
                var content = document.getElementById(shield).value;
                if (content.includes(id)) {
                    document.getElementById(shield).value = content.replace(id, "");
                } else {
                    document.getElementById(shield).value += id;
                }
            }
        </script>

        <script>
            function ActiveCont(valid, conte) {
                if (valid == "Si") {
                    document.getElementById(conte).style.display = "block";
                } else if (valid == "No") {
                    document.getElementById(conte).style.display = "none";
                }
            }
        </script>

        <script>
            function sigChange() {
                document.getElementById("sigChange").style.display = "block";
                if (document.getElementById("buttonSvve").style.display === "block") {
                    document.getElementById("buttonSvve").style.display = "none";
                    document.getElementById("buttonNsvve").style.display = "block";
                } else {
                    document.getElementById("buttonSvve").style.display = "block";
                    document.getElementById("buttonNsvve").style.display = "none";
                }
            }
        </script>

        <script>
            function sigChangev2() {
                document.getElementById("sigChange").style.display = "block";
                if (document.getElementById("buttonSvve").style.display === "block") {
                    document.getElementById("buttonSvve").style.display = "none";
                    document.getElementById("buttonNsvve").style.display = "block";
                } else {
                    document.getElementById("buttonSvve").style.display = "block";
                    document.getElementById("buttonNsvve").style.display = "none";
                }
            }
        </script>

        <script>
            let contador = 1;  // Contador para identificar cada grupo de campos de persona
            function agregarPersona(count) {
                if (count == 0) {
                } else {
                    contador = document.getElementById("CounterPersonAcc").value;
                }
                contador++; // Incrementa el contador
                const formulario = document.getElementById('formulario');
                const nuevaPersona = document.createElement('div');
                nuevaPersona.id = 'persona' + contador;
                nuevaPersona.className = 'd-flex person mt-2'; // Agregando la clase 'd-flex'

                let contenidoHTML = `
                        <div class="col-lg-4">
                          <div class="">
                            <input type="text" class="form-control" name="TxtNameXXXXAcc" placeholder="Nombre completo" data-toggle="tooltip" data-placement="top" title="Denominación Social o Nombre completo" required>
                          </div>
                        </div>
                        <div class="col-lg-4">
                          <div class="d-flex">
                            <div class="col-lg-5" style="margin-left: -15px;" data-toggle="tooltip" data-placement="top" title="">
                              <select class="form-control" name="CbxTypeDocXXXXAcc" required>
                                <option value="">Tipo</option>
                                <option value="PP">PP</option>
                                <option value="CC">CC</option>
                                <option value="CE">CE</option>
                                <option value="TAX ID">TAX ID</option>
                                <option value="NIT">NIT</option>
                                <option value="Otro">Otro</option>
                              </select>
                            </div>
                            <input type="number" class="form-control col-lg-9" name="NmbNroDocXXXXAcc" placeholder="Número de documento" data-toggle="tooltip" data-placement="top" title="" required>
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="d-flex mt-2 justify-content-center">
                            <input type="radio" name="is_pepXXXXAcc" value="Si"> &nbsp; Si &nbsp;&nbsp;
                            <input type="radio" name="is_pepXXXXAcc" value="No" checked> &nbsp; No &nbsp;&nbsp;
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="number" class="form-control" name="TxtPartXXXXAcc" placeholder="% Participación" data-toggle="tooltip" data-placement="top" title="Porcentaje de participación" required>
                          </div>
                        </div>
                      `;
                contenidoHTML = contenidoHTML.replaceAll('XXXX', contador);
                nuevaPersona.innerHTML = contenidoHTML;

                // Agregar botón de eliminar si no es la primera persona
                if (contador > 1) {
                    const botonEliminarDiv = document.createElement('div');
                    botonEliminarDiv.className = 'boton-eliminar';
                    const botonEliminar = document.createElement('button');
                    // Crea un elemento i para el icono de Font Awesome
                    const iconoEliminar = document.createElement('i');
                    iconoEliminar.className = 'fas fa-trash';
                    botonEliminar.appendChild(iconoEliminar); // Adjunta el icono al botón
                    botonEliminarDiv.appendChild(botonEliminar);
                    nuevaPersona.appendChild(botonEliminarDiv);

                    // Agrega la clase 'btn btn-danger' al botón
                    botonEliminar.className = 'btn btn-danger';

                    // Agrega el evento para eliminar la persona al hacer clic en el botón
                    botonEliminar.onclick = function () {
                        formulario.removeChild(nuevaPersona);
                    };
                    document.getElementById("CounterPersonAcc").value = contador;
                }

                formulario.appendChild(nuevaPersona);
            }

        </script>

        <script>
            contador = 1; // Contador para identificar cada grupo de campos de persona

            function AgregarRep(count) {
                if (count == 0) {
                } else {
                    contador = document.getElementById("CounterPerson").value;
                }
                contador++; // Incrementa el contador
                const formulario = document.getElementById('formulario');
                const nuevaPersona = document.createElement('div');
                nuevaPersona.id = 'persona' + contador;
                nuevaPersona.className = 'd-flex mt-2'; // Agregando la clase 'd-flex'

                let contenidoHTML = `
                        <div class="col-lg-3">
                          <div class="">
                            <input type="text" class="form-control" name="TxtNameXXXX" required>
                          </div>
                        </div>
                        <div class="col-lg-4">
                          <div class="d-flex">
                            <div class="col-lg-4" style="margin-left: -15px;">
                              <select class="form-control" name="CbxTypeDocXXXX" required>
                                <option value="">Tipo</option>
                                <option value="PP">PP</option>
                                <option value="CC">CC</option>
                                <option value="CE">CE</option>
                                <option value="TAX ID">TAX ID</option>
                                <option value="NIT">NIT</option>
                                <option value="Otro">Otro</option>
                              </select>
                            </div>
                            <input type="number" class="form-control col-lg-8" name="NmbNroDocXXXX" required>
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="">
                            <input type="text" class="form-control" name="TxtMailXXXX"  required>
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="">
                            <input type="text" class="form-control" name="TxtPhoneXXXX" placeholder="" required>
                          </div>
                        </div>
                        <div style="width: 90px;">
                          <div class="d-flex mt-2 justify-content-center" style="align-items: baseline;">
                            <input type="radio" name="is_pepXXXX" value="Si"> &nbsp; Si &nbsp;&nbsp;
                            <input type="radio" name="is_pepXXXX" value="No" checked> &nbsp; No &nbsp;&nbsp;
                          </div>
                        </div>
                      `;
                contenidoHTML = contenidoHTML.replaceAll('XXXX', contador);
                nuevaPersona.innerHTML = contenidoHTML;

                // Agregar botón de eliminar si no es la primera persona
                if (contador > 1) {
                    const botonEliminarDiv = document.createElement('div');
                    botonEliminarDiv.className = 'boton-eliminar';
                    const botonEliminar = document.createElement('a');
                    // Crea un elemento i para el icono de Font Awesome
                    const iconoEliminar = document.createElement('i');
                    iconoEliminar.className = 'fas fa-trash';
                    botonEliminar.appendChild(iconoEliminar); // Adjunta el icono al botón
                    botonEliminarDiv.appendChild(botonEliminar);
                    nuevaPersona.appendChild(botonEliminarDiv);

                    // Agrega la clase 'btn btn-danger' al botón
                    botonEliminar.className = 'btn btn-danger btn-sm';

                    // Agrega el evento para eliminar la persona al hacer clic en el botón
                    botonEliminar.onclick = function () {
                        formulario.removeChild(nuevaPersona);
                    };
                    document.getElementById("CounterPerson").value = contador;
                }

                formulario.appendChild(nuevaPersona);
            }

        </script>


        <script>
            contador = 1; // Contador para identificar cada grupo de campos de persona

            function agregarPersonaPlus(count) {
                if (count == 0) {
                } else {
                    contador = document.getElementById("CounterPersonBenf").value;
                }
                contador++; // Incrementa el contador
                const formulario = document.getElementById('formularioC');
                const nuevaPersona = document.createElement('div');
                nuevaPersona.id = 'persona' + contador;
                nuevaPersona.className = 'd-flex person mt-4';
                nuevaPersona.style.alignItems = 'center';

                let contenidoHTML = `
                        <div class="col-lg-4">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtNameXXXX" placeholder="Nombre completo" data-toggle="tooltip" data-placement="top" title="Denominación Social o Nombre completo" required>
                          </div>
                        </div>
                        <div class="col-lg-4">
                          <div class="d-flex">
                            <div class="col-lg-4" style="margin-left: -15px;" data-toggle="tooltip" data-placement="top" title="">
                              <select class="form-control" name="CbxTypeDocXXXX" required>
                                <option value="">Tipo</option>
                                <option value="PP">PP</option>
                                <option value="CC">CC</option>
                                <option value="CE">CE</option>
                                <option value="NIT">NIT</option>
                                <option value="Otro">Otro</option>
                              </select>
                            </div>
                            <input type="number" class="form-control col-lg-9" name="NmbNroDocXXXX" placeholder="Número de documento" data-toggle="tooltip" data-placement="top" title="" required>
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="d-flex mt-2 justify-content-center">
                            <input type="radio" name="is_pepXXXX" value="Si"> &nbsp; Si &nbsp;&nbsp;
                            <input type="radio" name="is_pepXXXX" value="No" checked> &nbsp; No &nbsp;&nbsp;
                          </div>
                        </div>
                      `;
                contenidoHTML = contenidoHTML.replaceAll('XXXX', contador);
                nuevaPersona.innerHTML = contenidoHTML;

                // Agregar botón de eliminar si no es la primera persona
                if (contador > 1) {
                    const botonEliminarDiv = document.createElement('div');
                    botonEliminarDiv.className = 'boton-eliminar';
                    const botonEliminar = document.createElement('button');
                    // Crea un elemento i para el icono de Font Awesome
                    const iconoEliminar = document.createElement('i');
                    iconoEliminar.className = 'fas fa-trash';
                    botonEliminar.appendChild(iconoEliminar); // Adjunta el icono al botón
                    botonEliminarDiv.appendChild(botonEliminar);
                    nuevaPersona.appendChild(botonEliminarDiv);

                    // Agrega la clase 'btn btn-danger' al botón
                    botonEliminar.className = 'btn btn-danger';

                    // Agrega el evento para eliminar la persona al hacer clic en el botón
                    botonEliminar.onclick = function () {
                        formulario.removeChild(nuevaPersona);
                    };
                    document.getElementById("CounterPersonBenf").value = contador;
                }

                formulario.appendChild(nuevaPersona);
            }

        </script>
        
        <script>
            contador = 1; // Contador para identificar cada grupo de campos de persona
            function agregarPersonaComer(count) {
                if (count == 0) {
                } else {
                    contador = document.getElementById("CounterPerson").value;
                }
                contador++; // Incrementa el contador
                const formulario = document.getElementById('formulario');
                const nuevaPersona = document.createElement('div');
                nuevaPersona.id = 'persona' + contador;
                nuevaPersona.className = 'd-flex person mt-4';
                nuevaPersona.style.alignItems = 'center';

                let contenidoHTML = `
                        <div class="col-lg-3">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtComercialRefXXXX" placeholder='Ref. comercial' required>
                          </div>
                        </div>
        
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtNitXXXX" placeholder='Tax id / nit' required>
                          </div>
                        </div>
        
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtConctacXXXX" placeholder='Contact' required>
                          </div>
                        </div>
                        
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtEmailXXXX" placeholder='Mail' required>
                          </div>
                        </div>
        
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtPhoneXXXX" placeholder='Num. phone' required>
                          </div>
                        </div>
                      `;
                contenidoHTML = contenidoHTML.replaceAll('XXXX', contador);
                nuevaPersona.innerHTML = contenidoHTML;

                // Agregar botón de eliminar si no es la primera persona
                if (contador > 1) {
                    const botonEliminarDiv = document.createElement('div');
                    botonEliminarDiv.className = 'boton-eliminar';
                    const botonEliminar = document.createElement('button');
                    // Crea un elemento i para el icono de Font Awesome
                    const iconoEliminar = document.createElement('i');
                    iconoEliminar.className = 'fas fa-trash';
                    botonEliminar.appendChild(iconoEliminar); // Adjunta el icono al botón
                    botonEliminarDiv.appendChild(botonEliminar);
                    nuevaPersona.appendChild(botonEliminarDiv);

                    // Agrega la clase 'btn btn-danger' al botón
                    botonEliminar.className = 'btn btn-danger';

                    // Agrega el evento para eliminar la persona al hacer clic en el botón
                    botonEliminar.onclick = function () {
                        formulario.removeChild(nuevaPersona);
                    };
                    document.getElementById("CounterPerson").value = contador;
                }

                formulario.appendChild(nuevaPersona);
            }

        </script>
        
        <script>
            contador = 1; // Contador para identificar cada grupo de campos de persona
            function agregarPersonaBank(count) {
                if (count == 0) {
                } else {
                    contador = document.getElementById("CounterPersonBank").value;
                }
                contador++; // Incrementa el contador
                const formulario = document.getElementById('formularioBank');
                const nuevaPersona = document.createElement('div');
                nuevaPersona.id = 'persona' + contador;
                nuevaPersona.className = 'd-flex person mt-4';
                nuevaPersona.style.alignItems = 'center';

                let contenidoHTML = `
                        <div class="col-lg-3">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtBankRefXXXX" placeholder='Ref. comercial' required>
                          </div>
                        </div>
                        <div class="col-lg-4">
                          <div class="d-flex">
                            <div class="col-lg-4" style="margin-left: -15px;" >
                              <select class="form-control" name="CbxTypeDocXXXX" required>
                                <option value="">Type</option>
                                <option value="AHORROS">AHORROS/SAVINGS</option>
                                <option value="CORRIENTE">CORRIENTE/CURRENT</option>
                                <option value="EMPRESARIAL">EMPRESARIAL/BUSINESS</option>
                              </select>
                            </div>
                            <input type="number" class="form-control col-lg-9" name="NmbNroDocXXXX" placeholder='Num. Doc' required>
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtContBankXXXX" placeholder='Contact' required>
                          </div>
                        </div>
                        <div class="col-lg-2">
                          <div class="mt-2">
                            <input type="text" class="form-control" name="TxtPhoneBankXXXX" placeholder='Num. phone' required>
                          </div>
                        </div>
                      `;
                contenidoHTML = contenidoHTML.replaceAll('XXXX', contador);
                nuevaPersona.innerHTML = contenidoHTML;

                // Agregar botón de eliminar si no es la primera persona
                if (contador > 1) {
                    const botonEliminarDiv = document.createElement('div');
                    botonEliminarDiv.className = 'boton-eliminar';
                    const botonEliminar = document.createElement('button');
                    // Crea un elemento i para el icono de Font Awesome
                    const iconoEliminar = document.createElement('i');
                    iconoEliminar.className = 'fas fa-trash';
                    botonEliminar.appendChild(iconoEliminar); // Adjunta el icono al botón
                    botonEliminarDiv.appendChild(botonEliminar);
                    nuevaPersona.appendChild(botonEliminarDiv);

                    // Agrega la clase 'btn btn-danger' al botón
                    botonEliminar.className = 'btn btn-danger';

                    // Agrega el evento para eliminar la persona al hacer clic en el botón
                    botonEliminar.onclick = function () {
                        formulario.removeChild(nuevaPersona);
                    };
                    document.getElementById("CounterPersonBank").value = contador;
                }

                formulario.appendChild(nuevaPersona);
            }

        </script>
        
        <script>
            function DeleteItem(id) {
                const div = document.getElementById('DataForm' + id);
                div.innerHTML = '';
            }
        </script>

        <script>
            function DeleteItemBenf(id) {
                const div = document.getElementById('Data' + id + 'Form' + id);
                div.innerHTML = '';
            }
        </script>

        <script>
            function ReadDoc(lg) {
                var Doc = document.getElementById("Doc_security");
                if (lg == 'es') {
                    if (Doc.checked) {

                    } else {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'Debe marcar la casilla de confirmación de lectura del acuerdo.',
                            position: 'bottomRight'
                        });
                    }
                } else {
                    if (Doc.checked) {

                    } else {
                        iziToast.warning({
                            title: 'Attention!',
                            message: 'You must check the agreement reading confirmation box.',
                            position: 'bottomRight'
                        });
                    }
                }

            }
        </script>

        <script>
            function ReadDocComplet(lg) {
                var validate = document.getElementById("idReadDoc").value;
                var Whitout = [];
                if (lg == 'es') {
                    if (!validate.includes("[1]")) {
                        Whitout.push("Datos personales");
                    }
                    if (!validate.includes("[2]")) {
                        Whitout.push("Origen de recursos");
                    }
                    if (!validate.includes("[3]")) {
                        Whitout.push("Programa de transparencia");
                    }
                    if (Whitout.length === 0) {
                    } else {
                        iziToast.warning({
                            title: 'Atencion!',
                            message: 'Falta marcar la casilla de lectura de ' + Whitout.join(', '),
                            position: 'bottomRight'
                        });
//                    alert("Falta seleccionar las opciones: " + Whitout.join(", "));
                    }
                } else {
                    if (!validate.includes("[1]")) {
                        Whitout.push("Personal information");
                    }
                    if (!validate.includes("[2]")) {
                        Whitout.push("Resource origin");
                    }
                    if (!validate.includes("[3]")) {
                        Whitout.push("Transparency program");
                    }
                    if (Whitout.length === 0) {
                    } else {
                        iziToast.warning({
                            title: 'Attention!',
                            message: 'The readding box needs to be checked for ' + Whitout.join(', '),
                            position: 'bottomRight'
                        });
//                    alert("Falta seleccionar las opciones: " + Whitout.join(", "));
                    }
                }


            }
        </script>
        <script>
            function ContainerAnimate(idCont) {
                var DataCont = document.getElementById(idCont);
                if (DataCont.style.display === "block") {
                    DataCont.style.display = "none";
                } else if (DataCont.style.display === "none") {
                    DataCont.style.display = "block";
                }
            }
        </script>
        <script>
            function ExecuteForm(id) {
                document.getElementById(id).submit();
            }
        </script>

        <script>
            function FormImage() {
                var name = document.getElementById("TxtName").value;
                var NroDoc = document.getElementById("NmbDocument").value;
                var validation = document.getElementById("TxtValidAction").value;
                var img = document.getElementById("file-input").value;

                document.getElementById("idNameg").value = name;
                document.getElementById("idDocg").value = NroDoc;
                document.getElementById("TxtValidActiong").value = validation;
                document.getElementById("idImg").value = img;


            }
        </script>

        <script>
            document.querySelectorAll('.moneyVal').forEach(function (input) {
                input.addEventListener('input', function (e) {
                    let value = e.target.value;

                    value = value.replace(/[^0-9.]/g, '');

                    let parts = value.split('.');
                    let integerPart = parts[0];
                    let decimalPart = parts.length > 1 ? '.' + parts[1] : '';

                    integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

                    e.target.value = integerPart + decimalPart;
                });
            });
        </script>

        <script>
            function validarNombreArchivo(input, lng) {
                const file = input.files[0];
                if (file) {
                    const nombreArchivo = file.name;
                    // 1. Validar longitud
                    if (lng == "en  ") {
                        if (nombreArchivo.length > 50) {
                            iziToast.warning({
                                title: 'Attention!',
                                message: 'The file name must not exceed 50 characters.',
                                position: 'bottomRight'
                            });
                            input.value = "";
                            return;
                        }

                        // 2. Validar caracteres especiales y ñ/Ñ
                        const caracteresInvalidos = /[ñÑ<>:"\/\\|?*\x00-\x1F]/g;
                        if (caracteresInvalidos.test(nombreArchivo)) {
                            iziToast.warning({
                                title: 'Attention!',
                                message: 'The file name contains illegal characters: ñ Ñ <> : \" / \\ | ? *',
                                position: 'bottomRight'
                            });
                            input.value = "";
                            return;
                        }
                    } else {
                        if (nombreArchivo.length > 50) {
                            iziToast.warning({
                                title: 'Atención!',
                                message: 'El nombre del archivo no debe superar los 50 caracteres.',
                                position: 'bottomRight'
                            });
                            input.value = "";
                            return;
                        }
                        const caracteresInvalidos = /[ñÑ<>:"\/\\|?*\x00-\x1F]/g;
                        if (caracteresInvalidos.test(nombreArchivo)) {
                            iziToast.warning({
                                title: 'Atención!',
                                message: 'El nombre del archivo contiene caracteres no permitidos: ñ Ñ <> : \" / \\ | ? *',
                                position: 'bottomRight'
                            });
                            input.value = "";
                            return;
                        }
                    }

                }
            }
        </script>


        <footer style="position: fixed;
                bottom: 0;
                width: 100%;
                text-align: center;
                box-shadow: 5px 6px 13px 4px #a4a4a4;">
            <p style="margin-bottom: 0;">&copy; Plastitec S.A.S</p>
        </footer>

        <script src="Interfaz/Contenido/assets/js/SignatureSetting.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <link href="Interfaz/Contenido/assets/js/DinamicShareholding.js" rel="stylesheet" type="text/css"/>
    </body>
</html>
