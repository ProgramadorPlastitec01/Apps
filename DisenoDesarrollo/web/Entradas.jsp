<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/Tlds/Entradas.tld" prefix="Entradas" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Entradas memorias | A-D&D</title>
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
                    <Entradas:Entradas/>
                </div>
            </div>
        </div>

        <Alertas:Alertas/>

        <script type = "text/javascript" >
            history.pushState(null, null, 'Entradas_Memoria.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Entradas_Memoria.jsp');
            });
        </script>

        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }

            function ActivarEntradaProyecto(id_herramental, id_proyecto, estadoM, Tipo_entrada) {
                swal({
                    title: "Activar edicion del proyecto",
                    text: "Esta opción permite que los participes realicen actividades en la memoria del proyecto...!",
                    type: "success",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=17&ipy=' + id_proyecto + '&T_Entrada=' + Tipo_entrada + '&estadoM=' + estadoM + '&tempE=2&id_E=' + id_herramental + '&estado=1';
                        });
            }

            function InactivarEntradaProyecto(id_herramental, id_proyecto, estadoM, Tipo_entrada) {
                swal({
                    title: "Bloquear edicion del proyecto",
                    text: "Esta opción no permite que los participes realicen actividades en la memoria del proyecto...!",
                    type: "error",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=17&ipy=' + id_proyecto + '&T_Entrada=' + Tipo_entrada + '&estadoM=' + estadoM + '&tempE=2&id_E=' + id_herramental + '&estado=0';
                        });
            }

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

                            // Actualizar el valor del input con el contenido del editor
                            document.getElementById('textInputM').value = cleanedContent;
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
</html>
