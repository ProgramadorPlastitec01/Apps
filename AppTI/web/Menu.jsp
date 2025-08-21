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
        </script>

        <!--         Page Specific JS File -->
        <script src="Interface/Content/Assets/js/page/index.js"></script>
        <!--Template JS File--> 
        <script src="Interface/Content/Assets/js/scripts.js"></script>
        <script src="Interface/Content/Assets/js/custom.js"></script>
    </body>
</html>
