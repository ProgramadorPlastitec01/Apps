
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<%@taglib uri="/WEB-INF/tlds/Tld_computer.tld" prefix="Computer" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PC</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css" >
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/computer.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
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
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Computer:Computer/>
                </div>
            </div>
        </div>
        <script>
            function showPages(id) {
                var totalNumberOfPages = 5;
                for (var i = 1; i <= totalNumberOfPages; i++) {
                    if (document.getElementById('page' + i)) {
                        document.getElementById('page' + i).style.display = 'none';
                    }
                }
                if (document.getElementById('page' + id)) {
                    document.getElementById('page' + id).style.display = 'block';
                }
            }
        </script>
        <script>
            function filterCards() {
                const input = document.getElementById('myInput');
                const filter = input.value.toLowerCase();
                const cardContainer = document.getElementById('cardContainer');
                const cards = cardContainer.getElementsByClassName('card-container');

                for (let i = 0; i < cards.length; i++) {
                    const card = cards[i];
                    const title = card.getElementsByTagName('h4')[0].innerText.toLowerCase();
                    const bodyText = card.innerText.toLowerCase();
                    if (title.includes(filter) || bodyText.includes(filter)) {
                        card.style.display = ''; // Muestra la tarjeta
                    } else {
                        card.style.display = 'none'; // Oculta la tarjeta
                    }
                }
            }
        </script>

        <script>
            function editar(event) {
                event.stopPropagation();
            }
        </script>


        <script>
            let contador = 0;

            function actualizarInputOculto() {
                const filas = document.querySelectorAll('#tabla-body tr:not(:first-child)');
                const valores = [];

                filas.forEach(fila => {
                    const columnas = fila.querySelectorAll('td');
                    const idName = columnas[0].textContent.trim();
                    const idType = columnas[1].textContent.trim();
                    const idVersion = columnas[2].textContent.trim();
                    valores.push('[' + idName + '/' + idType + '/' + idVersion + ']');
                });

                document.getElementById('infoOculta').value = valores.join(' ');
            }

            function agregarFila() {
                const idName = document.getElementById('idName').value.trim();
                const idType = document.getElementById('idType').value.trim();
                const idVersion = document.getElementById('idVersion').value.trim();

                if (!idName || !idType || !idVersion) {
                    alert("Por favor, completa todos los campos.");
                    return;
                }

                const tablaBody = document.getElementById('tabla-body');
                const nuevaFila = document.createElement('tr');

                let filaHtml = `
                    <td>xxNamexx</td>
                    <td>xxTypexx</td>
                    <td>xxVersionxx</td>
                    <td><button class="btn btn-danger" onclick="eliminarFila(this)">Eliminar</button></td>`;


                filaHtml = filaHtml
                        .replace('xxNamexx', idName)
                        .replace('xxTypexx', idType)
                        .replace('xxVersionxx', idVersion);

                nuevaFila.innerHTML = filaHtml;

                tablaBody.appendChild(nuevaFila);
                actualizarInputOculto();

                // Limpiar inputs
                document.getElementById('idName').value = '';
                document.getElementById('idType').value = '';
                document.getElementById('idVersion').value = '';
            }

            function eliminarFila(boton) {
                const fila = boton.closest('tr');
                fila.remove();
                actualizarContador(-1);
                actualizarInputOculto();
            }
        </script>

        <script>
            function sigMode(mode) {
                let hdmShield = document.getElementById('idSigMode');
                hdmShield.value = mode;
            }
        </script>

        <script>
            function guardarHTMLTabla() {
                const contenedor = document.getElementById("idtabla"); // ← asegúrate de que coincide el ID

                if (!contenedor) {
                    alert("No se encontró el contenedor con ID 'idtabla'");
                    return;
                }

                const inputs = contenedor.querySelectorAll("input, textarea, select");

                inputs.forEach(input => {
                    if (input.type === "checkbox" || input.type === "radio") {
                        if (input.checked) {
                            input.setAttribute("checked", "checked");
                        } else {
                            input.removeAttribute("checked");
                        }
                    } else {
                        input.setAttribute("value", input.value);
                    }
                });

                const contenido = contenedor.innerHTML;
                document.getElementById("htmlTabla").value = contenido;
                document.getElementById("Form04").submit();
            }
        </script>

        <script>
            function appsProtocol(data) {
                let validApp = document.getElementById("AppProt");
                if (validApp.value.includes(data)) {
                    validApp.value = validApp.value.replace(data, '');
                } else {
                    validApp.value += data;
                }
            }
        </script>


        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Se crean variables que permitirán tener control de permisos y carpetas por usuarios dentro del PHP del gestor.
                let Rol = document.getElementById("PhpRol").value;
                let IdUsPhp = document.getElementById("IdPhpUser").value;
                console.log("Rol obtenido:", Rol);

                // Sobrescribir la etiqueta del botón "Ver servidor" por "Gestor de archivos"
                CKEDITOR.on('dialogDefinition', function (ev) {
                    var dialogName = ev.data.name;
                    var dialogDefinition = ev.data.definition;

                    if (dialogDefinition.getContents('info')) {
                        var browseButton = dialogDefinition.getContents('info').get('browse');
                        if (browseButton) {
                            browseButton.label = 'Gestor de archivos';
                        }
                    }
                });

                // Inicializa por ID los editores dentro de un mismo contexto.
                const editorIDs = ['editorCK', 'editorCK1', 'editorCK2'];
                editorIDs.forEach(function (id) {
                    let element = document.getElementById(id);
                    if (element) {
                        CKEDITOR.replace(id, {
                            filebrowserBrowseUrl: 'http://172.16.2.117/elFinder/elfinder.html?rol=' + Rol + '&idusuario=' + IdUsPhp,
                            filebrowserImageBrowseUrl: 'http://172.16.2.117/elFinder/elfinder.html?type=Images&rol=' + Rol + '&idusuario=' + IdUsPhp,
                            removeDialogTabs: 'link:upload;image:upload',
                            language: 'es',
                            height: 150,
                            toolbarGroups: [
                                {name: 'document', groups: ['mode', 'document', 'doctools']},
                                {name: 'clipboard', groups: ['clipboard', 'undo']},
                                {name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing']},
                                {name: 'forms', groups: ['forms']},
                                {name: 'basicstyles', groups: ['basicstyles', 'cleanup']},
                                {name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph']},
                                {name: 'links', groups: ['links']},
                                {name: 'colors', groups: ['colors']},
                                {name: 'insert', groups: ['insert']},
                                {name: 'tools', groups: ['tools']},
                                {name: 'others', groups: ['others']},
                                {name: 'about', groups: ['about']},
                                '/',
                                {name: 'styles', groups: ['styles']}
                            ],
                            removeButtons: 'Save,NewPage,Preview,Source,Templates,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Subscript,Superscript,Blockquote,CreateDiv,BidiLtr,BidiRtl,Anchor,HorizontalRule,SpecialChar,PageBreak,Iframe,ShowBlocks,Language,Styles,About,Font,ExportPdf,Print,Replace',
                            on: {
                                instanceReady: function (evt) {
                                    var editor = evt.editor;

                                    editor.on('paste', function (pasteEvt) {
                                        let content = pasteEvt.data.dataValue;

                                        if (content && content.includes('src="data:image/')) {
                                            pasteEvt.data.dataValue = content.replace(/<img[^>]+src="data:image\/[^">]+"[^>]*>/gi, '');
                                            iziToast.warning({
                                                title: 'No se permite copiar y pegar archivos o imagenes!',
                                                message: 'Por favor suba la archivo o imagen al gestor de archivos.',
                                                position: 'bottomRight',
                                                time: 5000
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            });
        </script>

        <script>
            window.addEventListener('message', function (event) {
                // Recomendado: validar origen si solo aceptas desde elFinder
                // if (event.origin !== 'http://172.16.2.117') return;

                const data = event.data;
                if (data && data.funcNum && data.url) {
                    // Llama la función de CKEditor con la URL recibida
                    if (typeof CKEDITOR !== 'undefined') {
                        CKEDITOR.tools.callFunction(data.funcNum, data.url);
                    }
                }
            }, false);
        </script>


        <script>
            function validData003() {
                const form = document.getElementById("formR03");
                var infoField = document.getElementById("infoField").value;
                var infoHide = document.getElementById("infoOculta").value;

                const showWarning = (msg) => {
                    iziToast.warning({
                        title: 'Atención!',
                        message: msg,
                        position: 'topRight'
                    });
                };

                if (!infoField) {
                    showWarning('No se ha seleccionado items.');
                } else if (!infoHide) {
                    showWarning('No se ha ingresado software instalado.');
                } else {
                    if (form.checkValidity()) {
                        form.submit();  // solo se envía si pasa validaciones
                    } else {
                        form.reportValidity(); // muestra mensajes nativos de HTML5
                    }
                }
            }
        </script>

        <Alerts:Alert/>        
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
