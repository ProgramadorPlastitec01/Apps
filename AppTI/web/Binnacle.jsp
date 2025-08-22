<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_binnacle.tld" prefix="Binacles" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bitacora</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/summernote/summernote-bs4.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Binacles:Binnacle/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>   
        <script>
            function MoveData(ide) {
                var id = "[" + ide + "]";
                var input = document.getElementById("txtIdBins");
                var content = input.value;
                if (content.includes(id)) {
                    input.value = content.replace(id, "");
                } else {
                    input.value += id;
                }
            }

            function toggleAll() {
                var content = document.getElementById("idRecolect").value;
                document.getElementById("txtIdBins").value = content;
            }
        </script>

        <script>
            function updateTimeFields() {
                var select = document.getElementById('shiftSelect');
                var selectedIndex = select.selectedIndex;
                if (selectedIndex !== -1) {
                    var option = select.options[selectedIndex];
                    var startTime = option.getAttribute('data-start');
                    var endTime = option.getAttribute('data-end');
                    document.getElementById('startTime').value = startTime;
                    document.getElementById('endTime').value = endTime;
                }
            }
        </script>
        <script>
            function SendIds() {
                var data = document.getElementById("txtIdBins").value;
                if (data === "") {
                    $("#toastr-4").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar al menos a un usuario!',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    document.getElementById("SendIdRecole").submit();
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
                            filebrowserBrowseUrl: 'http://172.16.1.164/elFinder/elfinder.html?rol=' + Rol + '&idusuario=' + IdUsPhp,
                            filebrowserImageBrowseUrl: 'http://172.16.1.164/elFinder/elfinder.html?type=Images&rol=' + Rol + '&idusuario=' + IdUsPhp,
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
        <!-- Este script escucha los mensajes enviados desde elFinder (por postMessage) -->
        <script>
            window.addEventListener('message', function (event) {
                // Recomendado: validar origen si solo aceptas desde elFinder
                // if (event.origin !== 'http://172.16.1.164') return;

                const data = event.data;
                if (data && data.funcNum && data.url) {
                    // Llama la función de CKEditor con la URL recibida
                    if (typeof CKEDITOR !== 'undefined') {
                        CKEDITOR.tools.callFunction(data.funcNum, data.url);
                    }
                }
            }, false);
        </script>
        
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>

        <script src="Interface/Content/Assets/modules/summernote/summernote-bs4.js"></script>
    </body>
</html>