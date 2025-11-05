<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
        <!-- General CSS Files -->
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/ionicons/css/ionicons.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">

        <!-- CSS Libraries -->
        <link rel="stylesheet" href="Interface/Content/Assets/modules/jqvmap/dist/jqvmap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/owlcarousel2/dist/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/owlcarousel2/dist/assets/owl.theme.default.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css">

        <link rel="stylesheet" href="Interface/Content/Assets/css/appDetail.css">

        <!-- Datepicker CSS -->
        <link rel="stylesheet" type="text/css" media="all" href="Interface/Content/Assets/rangeCalendar/daterangepicker.css" />

        <!-- Template CSS -->
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
        <!-- Start GA -->
        <!--        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-94034622-3"></script>-->
        <!--        <script type="text/javascript" src="Interface/Alertas/dist/sweetalert.min.js"></script>
                <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>-->

        <style>
            #lottie-loader {
                position: fixed;
                top: 0;
                left: 0;
                width: 100vw;
                height: 100vh;
                background: rgba(255, 255, 255, 0.9);
                display: none;
                justify-content: center;
                align-items: center;
                z-index: 9999;
            }

            #lottie-animation {
                width: 200px;
                height: 200px;
            }
        </style>

        <!-- Editores -->
        <link rel="stylesheet" href="Interface/Editor/samples/toolbarconfigurator/lib/codemirror/neo.css">
        <script src="Interface/Editor/ckeditor.js"></script>
        <script src="Interface/Editor/Configuracion.js"></script>

        <!-- End Editor -->

        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag() {
                dataLayer.push(arguments);
            }
            gtag('js', new Date());
            gtag('config', 'UA-94034622-3');
        </script>
    </head>
    <body>
        <Menu:Menu/>
        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                const body = document.body;
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
                body.classList.toggle('modal-open');
            }
        </script>

        <script type="text/javascript" language="javascript">
            function MostrarWindows(id) {
                if (document.getElementById("Windows" + id).style.display === "none") {
                    document.getElementById("Windows" + id).style.display = "block";
                } else if (document.getElementById("Windows" + id).style.display === "block") {
                    document.getElementById("Windows" + id).style.display = "none";
                }
            }
            function CloseDivStartEndDate() {
                document.getElementById("Ventana99").style.display = "none";
            }
        </script>

        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <script src="Interface/Content/Assets/modules/moment.min.js"></script>
        <script type="text/javascript" src="Interface/Content/Assets/rangeCalendar/daterangepicker.js"></script>

        <script type="text/javascript">
            $(function () {
                moment.locale('es');

                var start = moment().subtract(29, 'days');
                var end = moment();

                function cb(start, end) {
                    $('#reportrange span').html(start.format('D [de] MMMM [de] YYYY') + ' - ' + end.format('D [de] MMMM [de] YYYY'));
                    // Actualizar los campos ocultos del formulario
                    $('#startDate').val(start.format('YYYY-MM-DD'));
                    $('#endDate').val(end.format('YYYY-MM-DD'));
                }

                $('#reportrange').daterangepicker({
                    startDate: start,
                    endDate: end,
                    locale: {
                        format: 'YYYY/MM/DD',
                        separator: ' - ',
                        applyLabel: 'Aplicar',
                        cancelLabel: 'Cancelar',
                        fromLabel: 'Desde',
                        toLabel: 'Hasta',
                        customRangeLabel: 'Personalizado',
                        daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
                        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                        firstDay: 1
                    },
                    ranges: {
                        'Hoy': [moment(), moment()],
                        'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        'Últimos 7 Días': [moment().subtract(6, 'days'), moment()],
                        'Últimos 30 Días': [moment().subtract(29, 'days'), moment()],
                        'Este Mes': [moment().startOf('month'), moment().endOf('month')],
                        'Mes Pasado': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    }
                }, cb);

                cb(start, end);
            });
        </script>
        <script>
            $(document).ready(function () {
                $('.daterange-cus').daterangepicker({
                    locale: {
                        format: 'YYYY-MM-DD',
                        separator: ' - ',
                        applyLabel: 'Aplicar',
                        cancelLabel: 'Cancelar',
                        fromLabel: 'Desde',
                        toLabel: 'Hasta',
                        customRangeLabel: 'Personalizado',
                        daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
                        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                            'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                        firstDay: 1
                    },
                    opens: 'right', // Posición del calendario
                    autoUpdateInput: false // Evita que se escriba automáticamente la fecha seleccionada
                });

                // Evento para actualizar el input cuando se seleccione una fecha
                $('.daterange-cus').on('apply.daterangepicker', function (ev, picker) {
                    $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
                });

                // Evento para limpiar el input si se cancela la selección
                $('.daterange-cus').on('cancel.daterangepicker', function (ev, picker) {
                    $(this).val('');
                });
            });

        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const tagInput = document.getElementById("tagInput");
                const tagContainer = document.getElementById("tagContainer");

                tagInput.addEventListener("keypress", function (event) {
                    if (event.key === "Enter" && tagInput.value.trim() !== "") {
                        event.preventDefault();
                        const tagText = tagInput.value.trim();

                        // Crear el contenedor de la etiqueta visual
                        const tag = document.createElement("span");
                        tag.classList.add("tag");

                        // Crear el texto de la etiqueta
                        const tagContent = document.createTextNode(tagText);
                        tag.appendChild(tagContent);

                        // Crear el input hidden para enviar al servidor
                        const hiddenInput = document.createElement("input");
                        hiddenInput.type = "hidden";
                        hiddenInput.name = "Data";
                        hiddenInput.value = tagText;
                        tag.appendChild(hiddenInput);

                        // Botón para eliminar
                        const removeBtn = document.createElement("span");
                        removeBtn.classList.add("remove");
                        removeBtn.innerHTML = "&minus;";
                        removeBtn.addEventListener("click", function () {
                            tag.remove();
                        });

                        tag.appendChild(removeBtn);
                        tagContainer.insertBefore(tag, tagInput);
                        tagInput.value = ""; // Limpiar input
                    }
                });
            });

        </script>
        <script>
            function MassiveId(ide) {
                var id = "[" + ide + "]";
                var cont = document.getElementById("IdModule").value;
                if (cont.includes(id)) {
                    document.getElementById("IdModule").value = cont.replace(id, "");
                } else {
                    document.getElementById("IdModule").value += id;
                }
            }
        </script>
        <script>
            function validateCheckboxes() {
                const checkboxes = document.querySelectorAll("input[name='imagecheck']");
                const isChecked = Array.from(checkboxes).some(cb => cb.checked);

                if (!isChecked) {
                    $("#toastr-2").ready(function () {
                        iziToast.warning({
                            title: '¡Alerta!',
                            message: 'Debe seleccionar al menos un módulo.',
                            position: 'bottomRight'
                        });
                    });
                    return false; // evita que el formulario se envíe
                }

                return true; // permite enviar el formulario
            }
        </script>
        <script>
            function ViewAlertREDEAC() {
                $("#toastr-2").ready(function () {
                    iziToast.warning({
                        title: '¡Fallo!',
                        message: 'Se debe configurar la nueva ruta del app historica, informar al administrador.',
                        position: 'bottomRight'
                    });
                });
            }
        </script>

        <%--<Alerts:Alert/>--%>

        <!-- Template JS File -->
        <script src="Interface/Content/Assets/modules/popper.js"></script>
        <script src="Interface/Content/Assets/modules/tooltip.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interface/Content/Assets/modules/nicescroll/jquery.nicescroll.min.js"></script>
        <script src="Interface/Content/Assets/js/stisla.js"></script>

        <!--JS Libraries--> 
        <script src="Interface/Content/Assets/modules/jquery.sparkline.min.js"></script>
        <script src="Interface/Content/Assets/modules/chart.min.js"></script>
        <script src="Interface/Content/Assets/modules/owlcarousel2/dist/owl.carousel.min.js"></script>
        <script src="Interface/Content/Assets/modules/summernote/summernote-bs4.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>

        <script src="Interface/Content/Assets/js/lottie.min.js"></script>

        <script>
            const animation = lottie.loadAnimation({
                container: document.getElementById('lottie-animation'),
                renderer: 'svg',
                loop: true,
                autoplay: false,
                path: 'https://lottie.host/24b3f2ff-747e-4b3f-bc89-a065bd3bb00f/61L1Ea73FZ.json' // Puedes cambiar esta animación
            });

            function showLoader() {
                document.getElementById('lottie-loader').style.display = 'flex';
                animation.play();
            }

            function hideLoader() {
                animation.stop();
                document.getElementById('lottie-loader').style.display = 'none';
            }

            // Simulación de proceso de carga
            function cargarDatos() {
                showLoader();

            }
            function cargarDatosForm(form) {
                let valido = true;

                // revisar manualmente campos requeridos
                form.querySelectorAll("[required]").forEach(function (input) {
                    if (!input.value.trim()) {
                        input.classList.add("is-invalid");
                        valido = false;
                    } else {
                        input.classList.remove("is-invalid");
                    }
                });

                if (valido) {
                    showLoader();
                    return true;
                }
                return false;
            }

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
                                                fetch(window.location.origin + '/AppTI/UploadPasteImageServlet', {
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
                                                                // ✅ Inserta directamente la imagen con la URL en el cursor actual
                                                                editor.insertHtml('<img src="' + data.url + '" alt="Imagen pegada" />');

                                                                iziToast.success({
                                                                    title: 'Imagen subida',
                                                                    message: 'La imagen fue guardada y agregada correctamente.',
                                                                    position: 'bottomRight',
                                                                    timeout: 3000
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

        <!--         Page Specific JS File -->
        <script src="Interface/Content/Assets/js/page/index.js"></script>
        <!--Template JS File--> 
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/js/custom.js"></script>
    </body>
</html>
