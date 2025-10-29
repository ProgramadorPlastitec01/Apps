<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_pending.tld" prefix="Pending" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pendiente</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/progress.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/RangeInput.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <link rel="stylesheet" href="Interface/Content/Assets/css/pending.css">
        <!-- CKEditor -->
        <script src="Interface/Editor/ckeditor.js"></script>
        <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&family=Orbitron:wght@400;700&family=Audiowide&display=swap" rel="stylesheet">


    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Pending:Pending/>
                </div>
            </div>
        </div>
        <script>
            function setProgress(id, percent) {
                const meter = document.querySelector(`#${id}`).previousElementSibling;
                const offset = 157 - (percent / 100) * 157; // Calcula el nuevo desplazamiento del trazo
                meter.style.setProperty('--progress', offset);
                document.getElementById(id).textContent = `${percent}%`;
            }

            // Ejemplo de cómo cambiar el progreso con animación para cada círculo
            setTimeout(() => {
                for (let i = 0; i < lst_pending.length; i++) {
                    setProgress(`progressText${i}`, 30); // Cambia el porcentaje a 30% después de 0.5s
                }
            }, 500);

            function ViewContentPending(count) {
                var DivTicket = document.getElementsByClassName("div-ticket");
                var IdPendingDiv = document.getElementById("IdPendingDiv" + count + "");
                for (var i = 0; i < DivTicket.length; i++) {
                    DivTicket[i].classList.remove("active");
                }
                var DivPendingContent = document.getElementById("DivPendingContent" + count + "");
                var DivContent = document.getElementsByClassName("div-content");
                for (var i = 0; i < DivContent.length; i++) {
                    DivContent[i].style.display = "none";
                }
                IdPendingDiv.classList.add("active");
                DivPendingContent.style.display = "block";
            }

            function ValidationType(type) {
                if (type === 1) {
                    document.getElementById("DivCat1").style.display = "block";
                    document.getElementById("DivCat2").style.display = "none";
                    document.getElementById("typePen").value = "1";
                    document.getElementById("Select1").setAttribute("required", "true");
                    document.getElementById("Select2").removeAttribute("required");
                } else {
                    document.getElementById("DivCat2").style.display = "block";
                    document.getElementById("DivCat1").style.display = "none";
                    document.getElementById("typePen").value = "2";
                    document.getElementById("Select2").setAttribute("required", "true");
                    document.getElementById("Select1").removeAttribute("required");
                }
            }
        </script>
        <script type="text/javascript">
            $(function () {
                var rangePercent = $('[type="range"]').val();
                $('[type="range"]').on('change input', function () {
                    rangePercent = $('[type="range"]').val();
                    $('em').html(rangePercent + '<span></span>');
                    $('[type="range"], strong>span').css('filter', 'hue-rotate(-' + rangePercent + 'deg)');
                    $('em').css({'transform': 'translateX(-50%) scale(' + (1 + (rangePercent / 500)) + ')', 'left': rangePercent + '%'});

                    // Enviar el valor al input con id="rangeOutput"
                    $('#rangeOutput').val(rangePercent);
                    $('#textPor').text(rangePercent + '%');
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                // Función para establecer la fecha y hora actuales en el campo datetime-local
                function setCurrentDateTime() {
                    var now = new Date();
                    var year = now.getFullYear();
                    var month = ('0' + (now.getMonth() + 1)).slice(-2); // Meses desde 0 a 11
                    var day = ('0' + now.getDate()).slice(-2);
                    var hours = ('0' + now.getHours()).slice(-2);
                    var minutes = ('0' + now.getMinutes()).slice(-2);

                    var datetimeLocal = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
                    $('#DateExecute').val(datetimeLocal);
                }

                // Llama a la función para establecer la fecha y hora actuales cuando la página se cargue
                setCurrentDateTime();
            });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {

                // ✅ Obtiene los valores desde los inputs ocultos en el HTML
                let Rol = document.getElementById("PhpRol") ? document.getElementById("PhpRol").value : "";
                let IdUsPhp = document.getElementById("IdPhpUser") ? document.getElementById("IdPhpUser").value : "";

                CKEDITOR.on('dialogDefinition', function (ev) {
                    const dialogDefinition = ev.data.definition;
                    if (dialogDefinition.getContents('info')) {
                        const browseButton = dialogDefinition.getContents('info').get('browse');
                        if (browseButton)
                            browseButton.label = 'Gestor de archivos';
                    }
                });

                const editorIDs = ['editorCK', 'editorCK1', 'editorCK2'];

                editorIDs.forEach(function (id) {
                    const element = document.getElementById(id);
                    if (element) {
                        CKEDITOR.replace(id, {
                            // ✅ Incluye el rol e idusuario en las URLs del gestor de archivos
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
                                    const editor = evt.editor;

                                    // ✅ Maneja imágenes pegadas (base64 → subir automáticamente)
                                    editor.on('paste', function (pasteEvt) {
                                        let content = pasteEvt.data.dataValue;

                                        // Detecta imágenes en base64
                                        const matches = content.match(/<img[^>]+src="data:image\/[^">]+"[^>]*>/gi);
                                        if (matches) {
                                            matches.forEach(function (imgTag) {
                                                const base64Data = imgTag.match(/src="(data:image\/[^">]+)"/i)[1];

                                                // 🔹 Obtiene variables del JSP
                                                let Rol = document.getElementById("PhpRol") ? document.getElementById("PhpRol").value : "";
                                                let IdUsPhp = document.getElementById("IdPhpUser") ? document.getElementById("IdPhpUser").value : "";

                                                // 🔹 Enviar la imagen + datos al servlet
                                                fetch(`${pageContext.request.contextPath}/UploadPasteImageServlet`, {
                                                    method: 'POST',
                                                    body: JSON.stringify({
                                                        imageData: base64Data,
                                                        rol: Rol,
                                                        idusuario: IdUsPhp
                                                    }),
                                                    headers: {'Content-Type': 'application/json'}
                                                })
                                                        .then(response => response.json())
                                                        .then(data => {
                                                            if (data && data.url) {
                                                                // Reemplaza el base64 por la URL del archivo
                                                                content = content.replace(base64Data, data.url);
                                                                pasteEvt.data.dataValue = content;
                                                                iziToast.success({
                                                                    title: 'Imagen subida',
                                                                    message: 'La imagen fue guardada automáticamente.',
                                                                    position: 'bottomRight',
                                                                    time: 3000
                                                                });
                                                            } else {
                                                                iziToast.error({
                                                                    title: 'Error',
                                                                    message: 'No se pudo subir la imagen.',
                                                                    position: 'bottomRight'
                                                                });
                                                            }
                                                        })
                                                        .catch(err => {
                                                            console.error(err);
                                                            iziToast.error({
                                                                title: 'Error',
                                                                message: 'Ocurrió un problema al subir la imagen.',
                                                                position: 'bottomRight'
                                                            });
                                                        });

                                                pasteEvt.cancel(); // Evita insertar el base64 original
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
            document.addEventListener('DOMContentLoaded', function () {
                function pad(n) {
                    return n < 10 ? '0' + n : n;
                }
                function ymd(d) {
                    return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate());
                }

                var now = new Date();
                var todayZero = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                var tomorrow = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1);

                function setupDateInput(input, baseDate) {
                    if (!input)
                        return;

                    var max = new Date(baseDate.getFullYear(), baseDate.getMonth() + 12, baseDate.getDate());

                    // establecer min y max
                    input.setAttribute('min', ymd(baseDate));
                    input.setAttribute('max', ymd(max));

                    function isInvalidDate(value) {
                        if (!value)
                            return true;
                        var parts = value.split('-');
                        if (parts.length !== 3)
                            return true;
                        var sel = new Date(parseInt(parts[0], 10), parseInt(parts[1], 10) - 1, parseInt(parts[2], 10));
                        return sel < baseDate; // permite hoy si baseDate es hoy
                    }

                    input.addEventListener('change', function () {
                        if (isInvalidDate(input.value)) {
                            input.classList.add('is-invalid');
                            input.value = '';
                        } else {
                            input.classList.remove('is-invalid');
                        }
                    });

                    var form = input.closest('form');
                    if (form) {
                        form.addEventListener('submit', function (e) {
                            if (isInvalidDate(input.value)) {
                                e.preventDefault();
                                input.classList.add('is-invalid');
                                input.focus();
                            }
                        });
                    }
                }

                // Caso 1: Txt_deadline -> permitir hoy
                setupDateInput(document.getElementById('Txt_deadline'), todayZero);

                // Caso 2: Txt_deadline2 -> desde DateRegister
                var dateRegisterStr = document.getElementById('DateRegister')?.value;
                if (dateRegisterStr) {
                    var parts = dateRegisterStr.split('-');
                    if (parts.length === 3) {
                        var dateRegister = new Date(parseInt(parts[0], 10), parseInt(parts[1], 10) - 1, parseInt(parts[2], 10));
                        setupDateInput(document.getElementById('Txt_deadline2'), dateRegister);
                    }
                }
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const now = new Date();
                const todayZero = new Date(now.getFullYear(), now.getMonth(), now.getDate()); // solo fecha

                document.querySelectorAll(".ticket-item").forEach(function (item) {
                    // limpiar clases previas
                    item.classList.remove("vencido", "soonHead");

                    const raw = item.getAttribute("data-fecha-limite");
                    if (!raw)
                        return;

                    // extraer solo la parte fecha: soporta "yyyy-mm-dd", "yyyy-mm-ddTHH:MM", "yyyy-mm-dd HH:MM:SS"
                    const datePart = raw.split("T")[0].split(" ")[0];
                    const parts = datePart.split("-");
                    if (parts.length !== 3) {
                        console.warn("Fecha con formato inesperado:", raw, "en", item.id);
                        return;
                    }

                    const y = parseInt(parts[0], 10);
                    const m = parseInt(parts[1], 10);
                    const d = parseInt(parts[2], 10);
                    if (isNaN(y) || isNaN(m) || isNaN(d)) {
                        console.warn("Fecha inválida:", raw, "en", item.id);
                        return;
                    }

                    const fechaLimiteLocal = new Date(y, m - 1, d); // medianoche local de la fecha límite

                    // Comparación POR DÍA (sin horas)
                    if (todayZero.getTime() > fechaLimiteLocal.getTime()) {
                        // hoy es posterior -> vencido
                        item.classList.add("vencido");
                    } else if (todayZero.getTime() === fechaLimiteLocal.getTime()) {
                        // mismo día -> pronto a vencer
                        item.classList.add("soonHead");
                    }
                    // else -> futuro, nada que hacer
                    // DEBUG opcional:
                    // console.log(item.id, "limite:", fechaLimiteLocal.toISOString().slice(0,10), "hoy:", todayZero.toISOString().slice(0,10));
                });
            });


        </script>
        <Alerts:Alert/>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
