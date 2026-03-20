<%@taglib uri="/WEB-INF/Tlds/Memorias.tld" prefix="Memorias" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Memorias | A-D&D</title>
        <link rel="shortcut icon" href="Interfaz/Contenido/Img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">

        <!-- CSS Libraries -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/jqvmap/dist/jqvmap.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/owlcarousel2/dist/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/owlcarousel2/dist/assets/owl.theme.default.min.css">

        <!-- Template CSS -->
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
        <!-- Start GA -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-94034622-3"></script>

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link href="Interfaz/Contenido/assets/css/proyectos.css" rel="stylesheet" type="text/css"/>


        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.css">

        <link href="Interfaz/Contenido/froala/CSS/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
        <link href="Interfaz/Contenido/froala/CSS/file.min.css" rel="stylesheet" type="text/css" />
        <link href="Interfaz/Contenido/froala/CSS/image.min.css" rel="stylesheet" type="text/css" />


    </head>
    <body>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <div id="app">
                <div class="main-wrapper main-wrapper-1">
                <Menu:Menu/>
                <div class="main-content" style="min-height: 694px;">
                    <Memorias:Memorias/>
                </div>
            </div>
        </div>

        <Alertas:Alertas/>

        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'Memorias.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Memorias.jsp');
                    });
                </script>-->

        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }

            function ProyectoEstado1(id_proyecto, estadoM, id_memoria, estado) {
                swal({
                    title: "Actividad en proceso",
                    text: "Seguro de devolver el estado de la actividad a proceso...!",
                    type: "info",
                    showCancelButton: true,
                    confirmButtonColor: "#c9dae1",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=13&ipy=' + id_proyecto + '&estadoM=' + estadoM + '&id_memoria=' + id_memoria + '&estado=' + estado + '';
                        });
            }

            function ProyectoEstado2(id_proyecto, estadoM, id_memoria, estado) {
                swal({
                    title: "Actividad en revisión",
                    text: "Seguro de enviar a revisión la actividad en proceso...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "orange",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=13&ipy=' + id_proyecto + '&estadoM=' + estadoM + '&id_memoria=' + id_memoria + '&estado=' + estado + '';
                        });
            }

            function ProyectoEstado3(id_proyecto, estadoM, id_memoria, estado) {
                swal({
                    title: "Finalizar actividad",
                    text: "Seguro de dar por finalizada la actividad...!",
                    type: "success",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=13&ipy=' + id_proyecto + '&estadoM=' + estadoM + '&id_memoria=' + id_memoria + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript">
            function Enviar_caso2() {
                window.onload = document.getElementById("Formulario").style.display = "none";
                window.onload = document.getElementById("Carga2").style.display = "block";
            }
        </script>

<!--        <script type="text/javascript">
            function Enviar_caso3() {
                var checkbox = document.getElementById("Cbx_enviar_autor");

                // Check if the checkbox is checked and fields are not empty
                if (checkbox && checkbox.checked) {
                    console.log("Checkbox está marcado.");
                    document.getElementById("Formulario2").style.display = "none";
                    document.getElementById("Carga3").style.display = "block";
                } else {
                    console.log("Checkbox no está marcado o hay campos vacíos.");
                }
            }
        </script>-->

        <script>
            function Enviar_caso3() {
                const checkbox = document.getElementById('Cbx_enviar_autor');
                const fechaReg = document.getElementById('fecha_reg_res');
                const observacion = document.getElementById('textInputR');
                const boton = document.getElementById('Formulario2');
                const carga3 = document.getElementById('Carga3');

                // Verifica si el checkbox está marcado
                if (checkbox.checked) {
                    // Valida que los campos requeridos estén llenos
                    if (fechaReg.value.trim() !== '' && observacion.value.trim() !== '') {
                        boton.style.display = 'none'; // Oculta el botón
                        carga3.style.display = 'block'; // Muestra el Carga3
                    } else {
                        console.log('Por favor, llena todos los campos requeridos.'); // Mensaje de alerta
                        checkbox.checked = false; // Desmarca el checkbox
                    }
                } else {
                    // Si el checkbox no está marcado, asegurarse de que el div de carga esté oculto y el botón visible
                    boton.style.display = 'block';
                    carga3.style.display = 'none';
                }
            }
        </script>

        <script>
            $(document).ready(function () {
                // Verifica si hay un tab guardado en el localStorage
                var activeTabId = localStorage.getItem('activeTab');

                if (activeTabId) {
                    // Si hay un tab guardado, activa ese tab
                    $('#myTab a[href="' + activeTabId + '"]').tab('show');
                } else {
                    // Si no hay tab guardado, activa el primer tab
                    $('#myTab li:first-child a').tab('show');
                }

                // Al hacer clic en un tab, guarda el ID del tab en localStorage
                $('#myTab a').on('click', function () {
                    var targetId = $(this).attr('href');
                    localStorage.setItem('activeTab', targetId);
                });

            });
        </script>


        <script src="Interfaz/Contenido/assets/modules/jquery.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/nicescroll/jquery.nicescroll.min.js"></script>
        <!-- Template JS File -->
        <script src="Interfaz/Contenido/assets/modules/popper.js"></script>
        <script src="Interfaz/Contenido/assets/modules/tooltip.js"></script>
        <!--<link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">-->
        <script src="Interfaz/Contenido/assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/moment.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/stisla.js"></script>

        <!-- JS Libraies -->
        <script src="Interfaz/Contenido/assets/modules/jquery.sparkline.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/chart.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/owlcarousel2/dist/owl.carousel.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/summernote/summernote-bs4.js"></script>
        <!-- Page Specific JS File -->
        <script src="Interfaz/Contenido/assets/js/page/index.js"></script>
        <!-- Template JS File -->
        <script src="Interfaz/Contenido/assets/js/scripts.js"></script>
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>

        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>



        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>

        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/froala_editor.pkgd.min.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/file.min.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/image.min.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/languages-es.js"></script>

        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/froala-file-manager.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/froala-image-editor.js"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                new FroalaEditor('#editor', {
                    language: 'es',
                    events: {
                        'contentChanged': function () {
                            var editableDiv = document.querySelector('#editor [contenteditable="true"]');
                            var textInput = document.getElementById('textInput');

                            // Elimina espacios en blanco y caracteres invisibles directamente
                            var cleanedHTML = editableDiv.innerHTML
                                    .replace(/^\s+|\s+$/g, '') // Elimina espacios al principio y al final
                                    .replace(/\u200B/g, '') // Elimina espacios de ancho cero
                                    .replace(/&ZeroWidthSpace;/g, ''); // Elimina &ZeroWidthSpace;

                            // Asigna el contenido limpio al campo de entrada
                            textInput.value = cleanedHTML;
                        },
                        'image.beforeUpload': function (files) {
                            const editor = this;
                            const reader = new FileReader();

                            reader.onload = function (e) {
                                const img = new Image();
                                img.src = e.target.result;
                                editor.image.insert(img.src, null, null, editor.image.get());
                            };

                            reader.readAsDataURL(files[0]);
                            return false; // Previene la subida por defecto
                        },
                        'file.beforeUpload': function (files) {
                            const editor = this;
                            const reader = new FileReader();

                            reader.onload = function (e) {
                                const link = e.target.result;
                                editor.file.insert(link, null, editor.file.get());
                            };

                            reader.readAsDataURL(files[0]);
                            return false; // Previene la subida por defecto
                        }
                    },
                    Flmngr: {
                        apiKey: "toRgIgI6",
                        urlFileManager: 'http://localhost/Archivo_DYD/flmngr/flmngr.php',
                        urlFiles: 'http://localhost/Archivo_DYD/flmngr/files/'
                    }
                });
            });
        </script>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Inicializar el editor Froala
                var editor = new FroalaEditor('#editorM', {
                    language: 'es',
                    Flmngr: {
                        apiKey: 'toRgIgI6',
                        urlFileManager: 'http://localhost/Archivo_DYD/flmngr/flmngr.php',
                        urlFiles: 'http://localhost/Archivo_DYD/flmngr/files'
                    },
                    events: {
                        'contentChanged': function () {
                            // Capturar el contenido actual del editor
                            var editorContent = editor.html.get();

                            // Limpiar el contenido de caracteres invisibles
                            var cleanedContent = editorContent
                                    .replace(/^\s+|\s+$/g, '') // Elimina espacios al principio y al final
                                    .replace(/\u200B/g, '') // Elimina espacios de ancho cero
                                    .replace(/&ZeroWidthSpace;/g, ''); // Elimina &ZeroWidthSpace;

                            // Actualizar el valor del input con el contenido limpio del editor
                            document.getElementById('textInputM').value = cleanedContent;
                        }
                    }
                });
            });
        </script>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                new FroalaEditor('#editorR', {
                    language: 'es',
                    events: {
                        'contentChanged': function () {
                            var editableDiv = document.querySelector('#editorR [contenteditable="true"]');
                            var textInput = document.getElementById('textInputR');

                            // Elimina espacios en blanco y caracteres invisibles directamente
                            var cleanedHTML = editableDiv.innerHTML
                                    .replace(/^\s+|\s+$/g, '') // Elimina espacios al principio y al final
                                    .replace(/\u200B/g, '') // Elimina espacios de ancho cero
                                    .replace(/&ZeroWidthSpace;/g, ''); // Elimina &ZeroWidthSpace;

                            // Asigna el contenido limpio al campo de entrada
                            textInput.value = cleanedHTML;
                        },
                        'image.beforeUpload': function (files) {
                            const editor = this;
                            const reader = new FileReader();

                            reader.onload = function (e) {
                                const img = new Image();
                                img.src = e.target.result;
                                editor.image.insert(img.src, null, null, editor.image.get());
                            };

                            reader.readAsDataURL(files[0]);
                            return false; // Previene la subida por defecto
                        },
                        'file.beforeUpload': function (files) {
                            const editor = this;
                            const reader = new FileReader();

                            reader.onload = function (e) {
                                const link = e.target.result;
                                editor.file.insert(link, null, editor.file.get());
                            };

                            reader.readAsDataURL(files[0]);
                            return false; // Previene la subida por defecto
                        }
                    },
                    Flmngr: {
                        apiKey: "toRgIgI6",
                        urlFileManager: 'http://localhost/Archivo_DYD/flmngr/flmngr.php',
                        urlFiles: 'http://localhost/Archivo_DYD/flmngr/files'
                    }
                });
            });
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Inicializar el editor Froala
                var editor = new FroalaEditor('#editorRM', {
                    language: 'es',
                    Flmngr: {
                        apiKey: 'toRgIgI6',
                        urlFileManager: 'http://localhost/Archivo_DYD/flmngr/flmngr.php',
                        urlFiles: 'http://localhost/Archivo_DYD/flmngr/files'
                    },
                    events: {
                        'contentChanged': function () {
                            // Capturar el contenido actual del editor
                            var editorContent = editor.html.get();

                            // Limpiar el contenido de caracteres invisibles
                            var cleanedContent = editorContent
                                    .replace(/^\s+|\s+$/g, '') // Elimina espacios al principio y al final
                                    .replace(/\u200B/g, '') // Elimina espacios de ancho cero
                                    .replace(/&ZeroWidthSpace;/g, ''); // Elimina &ZeroWidthSpace;

                            // Actualizar el valor del input con el contenido del editor
                            document.getElementById('textInputRM').value = cleanedContent;
                        }
                    }
                });
            });
        </script>

        <script>
            function uploadFiles() {
                // Obtener la última fecha de subida del storage
                let lastUploadTime = localStorage.getItem('lastUploadTime') || new Date().toISOString();

                // Guardar el valor actual de lastUploadTime como valor anterior
                localStorage.setItem('previousUploadTime', lastUploadTime);

                // Guardar la fecha y hora actual como nueva última subida
                lastUploadTime = new Date().toISOString();
                localStorage.setItem('lastUploadTime', lastUploadTime);

                // Obtener archivos en el intervalo de tiempo
                let filesToUpload = []; // Aquí puedes agregar lógica para llenar este array con los archivos que desees subir

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "http://localhost/Archivo_DYD/flmngr/envio.php", true);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        alert("Archivos subidos correctamente.");
                    }
                };

                // Enviar los datos en formato JSON
                xhr.send(JSON.stringify({
                    files: filesToUpload,
                    lastUploadTime: lastUploadTime,
                    previousUploadTime: localStorage.getItem('previousUploadTime')
                }));
            }

            // Aquí puedes llamar a uploadFiles() cuando lo necesites, por ejemplo, en un botón
            // document.getElementById('uploadButton').addEventListener('click', uploadFiles);
        </script>

    </body>
</body>
</html>
