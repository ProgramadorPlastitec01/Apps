<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_customer.tld" prefix="Custom" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tickets</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">


        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">

        <!-- CSS Libraries -->
        <link rel="stylesheet" href="Interface/Content/Assets/modules/jqvmap/dist/jqvmap.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/owlcarousel2/dist/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/owlcarousel2/dist/assets/owl.theme.default.min.css">

        <!--new editor-->
        <link href="https://cdn.jsdelivr.net/npm/froala-editor@latest/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/npm/froala-editor@latest/css/plugins/file.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/npm/froala-editor@latest/css/plugins/image.min.css" rel="stylesheet" type="text/css" />



        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/froala-editor@latest/js/froala_editor.pkgd.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/froala-editor@latest/js/plugins/file.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/froala-editor@latest/js/plugins/image.min.js"></script>

        <script type="text/javascript" src="https://cdn.jsdelivr.net/gh/edsdk/froala-file-manager@latest/js/froala-file-manager.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/gh/edsdk/froala-image-editor@latest/js/froala-image-editor.js"></script>

        <!-- Datepicker CSS -->
        <link rel="stylesheet" type="text/css" media="all" href="Interface/Content/Assets/rangeCalendar/daterangepicker.css" />

        <!-- Template CSS -->
        <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
        <!-- Start GA -->
        <!--<script async src="https://www.googletagmanager.com/gtag/js?id=UA-94034622-3"></script>-->
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag() {
                dataLayer.push(arguments);
            }
            gtag('js', new Date());
            gtag('config', 'UA-94034622-3');
        </script>

        <style>
            .show-placeholder > div > a,
            .fr-wrapper > div > a { display: none !important; }
        </style>

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <%--<jsp:include page="Menu.jsp"></jsp:include>--%>
                <div class="main-content" style="min-height: 694px; padding-left: 30px;
                     padding-right: 30px;
                     padding-top: 30px;
                     width: 100%;
                     position: relative;">
                    <Custom:Customer/>
                </div>
            </div>
        </div>


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

        <script>
            function switchSelect(rm) {
                if (rm == 1) {
                    document.getElementById("selectApp").style.display = "none";
                    document.getElementById("selectPc").style.display = "none";
                } else if (rm == 2) {
                    document.getElementById("selectApp").style.display = "block";
                    document.getElementById("selectPc").style.display = "none";
                } else if (rm == 3) {
                    document.getElementById("selectApp").style.display = "none";
                    document.getElementById("selectPc").style.display = "block";
                }
            }
        </script>

        <script>
            function switchEdit(rm) {
                if (rm == 1) {
                    document.getElementById("editApp").style.display = "none";
                    document.getElementById("editPc").style.display = "none";
                } else if (rm == 2) {
                    document.getElementById("editApp").style.display = "block";
                    document.getElementById("editPc").style.display = "none";
                } else if (rm == 3) {
                    document.getElementById("editApp").style.display = "none";
                    document.getElementById("editPc").style.display = "block";
                }
            }
        </script>

        <!-- Cargar jQuery -->
        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <!-- Cargar Moment.js -->
        <script src="Interface/Content/Assets/modules/moment.min.js"></script>
        <!-- Cargar Daterangepicker.js -->
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
            document.querySelectorAll('.rating input').forEach(input => {
                input.addEventListener('change', function () {
                    const ratingValue = this.value;
                    const emoji = document.getElementById('emoji');
                    let emojiIcon = '';

                    switch (ratingValue) {
                        case '1':
                            emojiIcon = '😠';
                            break;
                        case '2':
                            emojiIcon = '😟';
                            break;
                        case '3':
                            emojiIcon = '😐';
                            break;
                        case '4':
                            emojiIcon = '🙂';
                            break;
                        case '5':
                            emojiIcon = '😁';
                            break;
                    }
                    emoji.textContent = emojiIcon;
                });
            });

        </script>
        <script type="text/javascript">
            new FroalaEditor('#editorNext', {
                Flmngr: {
                    apiKey: 'z5tCL8YdVd99dhc5MQCNlQlo',
                    urlFileManager: 'http://172.16.1.164/flmngr/flmngr.php',
                    urlFiles: 'http://172.16.1.164/flmngr/files/'
                }
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
                xhr.open("POST", "http://172.16.1.164/flmngr/envio.php", true);
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

        <script>
            function passData(id) {
                document.getElementById("Reop").value = id;
            }
        </script>


        <script>
            function dataPass() {
                document.getElementById("NewData").value = document.getElementById("editorNext").value;
                document.FromNew.submit();
            }
        </script>

        <script>
//            window.onload = function () {
                var div = document.getElementById("TKAff");
                div.scrollTop = div.scrollHeight;
//            };
        </script>

        <Alerts:Alert/>
        <!-- Template JS File -->
        <script src="Interface/Content/Assets/modules/popper.js"></script>
        <script src="Interface/Content/Assets/modules/tooltip.js"></script>
        <script src="Interface/Content/Assets/modules/bootstrap/js/bootstrap.min.js"></script>
        <script src="Interface/Content/Assets/modules/nicescroll/jquery.nicescroll.min.js"></script>
        <script src="Interface/Content/Assets/js/stisla.js"></script>

        <!-- JS Libraries -->
        <script src="Interface/Content/Assets/modules/jquery.sparkline.min.js"></script>
        <script src="Interface/Content/Assets/modules/chart.min.js"></script>
        <script src="Interface/Content/Assets/modules/owlcarousel2/dist/owl.carousel.min.js"></script>
        <script src="Interface/Content/Assets/modules/summernote/summernote-bs4.js"></script>

        <!-- Page Specific JS File -->
        <script src="Interface/Content/Assets/js/page/index.js"></script>
        <!-- Template JS File -->
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/js/custom.js"></script>


        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
