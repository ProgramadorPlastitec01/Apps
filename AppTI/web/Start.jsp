<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_start.tld" prefix="Start" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />

        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fullcalendar/fullcalendar.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/start.css">

        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
    </head>
    <style>
        h2{
            text-transform: capitalize;
        }
        a{
            text-transform: capitalize;
        }
        #table-1_wrapper .dataTables_scrollBody {
            overflow-y: auto !important;
        }
    </style>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Start:StartMod/>
                </div>
                <script type="text/javascript">
                    function ChangeDiv(number) {
                        if (number === 1) {
                            document.getElementById("div_start_access").style.display = "none";
                            document.getElementById("div_start_calendar").style.display = "block";
                        } else {
                            document.getElementById("div_start_calendar").style.display = "none";
                            document.getElementById("div_start_access").style.display = "block";
                        }
                    }
                </script>
                <script>
                    $(document).ready(function () {
                        const commonOptions = {
                            searching: false,
                            lengthChange: false,
                            info: false,
                            scrollCollapse: true,
                            paging: true,
                            language: {
                                url: '//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json'
                            }
                        };
                        const tableConfigs = {
                            '#table-1': {scrollY: '200px'},
                            '#table-2': {scrollY: 'auto'},
                            '#table-3': {scrollY: '200px'}
                        };
                        for (const [selector, options] of Object.entries(tableConfigs)) {
                            $(selector).DataTable({...commonOptions, ...options});
                        }
                    });
                </script>

                <script>
                    function toggleCustomizer() {
                        document.getElementById('modPanel').classList.toggle('active');
                        document.body.classList.add('no-scroll');
                    }
                </script>
                <script>
                    function toggleSection(sectionId) {
                        const section = document.getElementById(sectionId);

                        // Alternar visibilidad solo de la sección seleccionada
                        if (section.style.display === "block") {
                            section.style.display = "none"; // Ocultar si ya estaba visible
                        } else {
                            section.style.display = "block"; // Mostrar si estaba oculta
                        }

                        return true;
                    }

                </script>

                <script>
                    function selectModule(element, divId) {
                        const section = document.getElementById(divId);
                        if (!section) {
                            iziToast.warning({
                                title: 'Sin información!',
                                message: 'Este módulo no tiene información disponible actualmente.',
                                position: 'bottomRight',
                                time: 5000
                            });
                            return;
                        }

                        const input = document.getElementById('modSelectedInput');
                        let selected = input.value ? input.value.split(',') : [];

                        if (element.classList.contains('active')) {
                            element.classList.remove('active');
                            selected = selected.filter(id => id !== divId);
                            section.classList.add("d-none"); // ⬅️ Oculta el div
                        } else {
                            element.classList.add('active');
                            if (!selected.includes(divId)) {
                                selected.push(divId);
                            }
                            section.classList.remove("d-none"); // ⬅️ Muestra el div
                        }

                        input.value = selected.join(',');
                    }
                </script>
                <script>
                    function SendForm() {
                        const form = document.getElementById("MyForm");
                        form.submit();
                    }
                </script>


            </div>
        </div>
        <Alerts:Alert/>


        <script src="Interface/Content/Assets/modules/chart.min.js"></script>

        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>

        <script src="Interface/Content/Assets/modules/fullcalendar/fullcalendar.min.js"></script>
        <script src="Interface/Content/Assets/modules/fullcalendar/locale-all.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>

        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-calendar.js"></script>
        <script src="Interface/Content/Assets/js/fullcalendar/index.global.js"></script>

    </body>
</html>
