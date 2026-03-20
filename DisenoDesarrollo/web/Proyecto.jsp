<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Proyecto.tld" prefix="Proyecto" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proyectos | A-D&D</title>
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
        <link href="Interfaz/Contenido/assets/css/proyectos.css"rel="stylesheet" type="text/css"/>


        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <link href="Interfaz/Contenido/assets/Validacion/StyleSheetLiveValidation.css" rel="stylesheet" type="text/css"/>


        <!--        <link href="Interfaz/Contenido/assets/css/proyectos.css" rel="stylesheet" type="text/css"/>-->
        <!--                        <script type = "text/javascript" >
                                    history.pushState(null, null, 'Proyecto.jsp');
                                    window.addEventListener('popstate', function (event) {
                                        history.pushState(null, null, 'Proyecto.jsp');
                                    });
                                </script>-->
    </head>
    <body>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <div id="app">
                <div class="main-wrapper main-wrapper-1">
                <Menu:Menu/>
                <div class="main-content" style="min-height: 694px;">
                    <Proyecto:Proyectos/>
                </div>
            </div>
        </div>

        <Alertas:Alertas/>

        <script type = "text/javascript" >
            history.pushState(null, null, 'Proyecto.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Proyectos.jsp');
            });
        </script>

        <script src="Interfaz/Contenido/assets/modules/jquery.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/nicescroll/jquery.nicescroll.min.js"></script>

        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
        </script>

        <script>
            $(document).ready(function () {
                var limit = 3; // Límite predeterminado
                var currentPage = 1;
                var pageRange = 4; // Número máximo de botones de página a mostrar en el rango central

                function showRecords() {
                    var value = $('#searchInput').val().toLowerCase();
                    var start = (currentPage - 1) * limit;
                    var end = start + limit;
                    var shownCount = 0;

                    $('#commentTable tbody').children().each(function () {
                        var record = $(this);
                        if (record.hasClass('thead')) {
                            record = record.nextUntil('.thead').addBack();
                            var recordText = record.text().toLowerCase();

                            if (recordText.includes(value)) {
                                if (shownCount >= start && shownCount < end) {
                                    record.show();
                                } else {
                                    record.hide();
                                }
                                shownCount++;
                            } else {
                                record.hide();
                            }
                        }
                    });

                    updatePagination();
                }

                function updatePagination() {
                    var value = $('#searchInput').val().toLowerCase();
                    var totalRecords = 0;

                    $('#commentTable tbody').children().each(function () {
                        var record = $(this);
                        if (record.hasClass('thead')) {
                            record = record.nextUntil('.thead').addBack();
                            var recordText = record.text().toLowerCase();

                            if (recordText.includes(value)) {
                                totalRecords++;
                            }
                        }
                    });

                    var totalPages = Math.ceil(totalRecords / limit);
                    var paginationHtml = '';

                    if (totalPages > 1) {

                        paginationHtml += '<button class="page-btn prev-btn btn ' + (currentPage === 1 ? 'btn-secondary" style="cursor:auto;"' : 'btn-dark"') + '>Atrás</button> ';

                        // Determinar el rango de páginas a mostrar
                        var startPage = Math.max(1, currentPage - Math.floor(pageRange / 2));
                        var endPage = Math.min(totalPages, currentPage + Math.floor(pageRange / 2));

                        // Mostrar la primera página y elipses si es necesario
                        if (startPage > 2) {
                            paginationHtml += '<span class="page-item btn btn-dark" data-page="1">1</span> ';
                            if (startPage > 3)
                                paginationHtml += '<span class="page-item btn btn-dark disabled" style="pointer-events: none;cursor: no-drop;opacity: 0.3;" >...</span> ';
                        }


                        for (var i = startPage; i <= endPage; i++) {
                            paginationHtml += '<span class="page-item btn btn-dark ' + (i === currentPage ? 'active font-weight-bold' : 'disabled font-italic') + '" data-page="' + i + '">' + i + '</span> ';
                        }

                        // Mostrar elipses y la última página si es necesario
                        if (endPage < totalPages - 1) {
                            if (endPage < totalPages - 2)
                                paginationHtml += '<span class="page-item btn btn-dark disabled" style="pointer-events: none;cursor: no-drop;opacity: 0.3;" >...</span> ';
                            paginationHtml += '<span class="page-item btn btn-dark" data-page="' + totalPages + '">' + totalPages + '</span> ';
                        }


                        paginationHtml += '<button class="page-btn next-btn btn ' + (currentPage === totalPages ? 'btn-secondary" style="cursor:auto;"' : 'btn-dark"') + '>Siguiente</button>';
                    }

                    $('.paginacion').html(paginationHtml);


                    $('.page-item').on('click', function () {
                        currentPage = parseInt($(this).data('page'));
                        showRecords();
                    });

                    $('.prev-btn').on('click', function () {
                        if (currentPage > 1) {
                            currentPage--;
                            showRecords();
                        }
                    });

                    $('.next-btn').on('click', function () {
                        if (currentPage < totalPages) {
                            currentPage++;
                            showRecords();
                        }
                    });
                }

                $('#searchInput').on('keyup', function () {
                    currentPage = 1;
                    showRecords();
                });

                $('#watchlimit').on('change', function () {
                    limit = parseInt($(this).val()) || 1;
                    currentPage = 1;
                    showRecords();
                });

                showRecords();
            });
        </script>


        <script type="text/javascript">
            function ProyectoProceso(id_proyecto) {
                var tipo_proyecto = document.getElementById("t_proyecto").value;
                swal({
                    title: "Proyecto Terminado",
                    text: "Seguro de cambiar el estado del proyecto a TERMINADO...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=6&id_proyecto=' + id_proyecto + '&Rdb_consulta=' + tipo_proyecto + '&f_salida=TERMINADO';
                        });
            }

            function ProyectoTerminado(id_proyecto) {
                var tipo_proyecto = document.getElementById("t_proyecto").value;
                swal({
                    title: "Definir estado de proyecto!",
                    text: "<a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=CANCELADO' id='formVolver'><button style='background:gray' type='submit' required  form='formVolver'>Cancelar</button></a>&nbsp;&nbsp;&nbsp;&nbsp;\n\
                                   <a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=PROCESO' id='formVolver'><button style='background:orange' type='submit' required  form='formVolver'>Proceso</button></a>&nbsp;\n\
                                   <a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=REVISION' id='formVolver'><button style='background:#084fe6' type='submit' required  form='formVolver'>Revisi&oacute;n</button></a>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }

            function ProyectoRevision(id_proyecto) {
                var tipo_proyecto = document.getElementById("t_proyecto").value;
                swal({
                    title: "Definir estado de proyecto!",
                    text: "<a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=CANCELADO' ><button style='background:gray' type='submit' required  form='formVolver'>Cancelar</button></a>&nbsp;&nbsp;&nbsp;\n\
                                   <a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=PROCESO'><button style='background:orange'  type='submit' required  form='formVolver'>Proceso</button></a>&nbsp;\n\
                                   <a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=FINALIZADO'><button style='background:#191d21' type='submit' required  form='formVolver'>Finalizar Revisi&oacute;n y firmar</button></a>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }

            function ProyectoFinalizado(id_proyecto) {
                var tipo_proyecto = document.getElementById("t_proyecto").value;
                swal({
                    title: "Definir estado de proyecto!",
                    text: "<a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=CANCELADO' ><button style='background:gray' type='submit' >Cancelar</button></a>&nbsp;&nbsp;&nbsp;\n\
                                   <a href='Proyecto?opc=6&id_proyecto=" + id_proyecto + "&Rdb_consulta=" + tipo_proyecto + "&f_salida=PROCESO'><button style='background:orange' type='submit' >Proceso</button></a>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }
            function ProyectoEstado1(id_proyecto) {
                swal({
                    title: "Actividad en proceso",
                    text: "Seguro de devolver el estado de la actividad a proceso...!",
                    type: "error",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            document.forms["Form_estado_" + id_proyecto].submit();
                        });
            }
            function ProyectoEstado2(id_proyecto) {
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
                            document.forms["Form_atender_" + id_proyecto].submit();
                        });
            }
            function ProyectoEstado3(id_proyecto) {
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
                            document.forms["Form_estado_" + id_proyecto].submit();
                        });
            }
            function ActivarProyecto(id_proyecto) {
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
                            location.href = 'Proyecto?opc=5&ipy=' + id_proyecto + '&estado=1&Templdd=99';
                        });
            }
            function InactivarProyecto(id_proyecto) {
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
                            location.href = 'Proyecto?opc=5&ipy=' + id_proyecto + '&estado=0&Templdd=99';
                        });
            }
            function ActivarProyectoPrueba(id_proyecto, id_prueba) {
                swal({
                    title: "Bloquear asignacion de pruebas",
                    text: "Al bloquear la opción esta ya no le permitira asignar pruebas al proyecto...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "orange",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=14&ipy=' + id_proyecto + '&ipe=' + id_prueba + '&estado=0';
                        });
            }
            function InactivarProyectoPrueba(id_proyecto, id_prueba) {
                swal({
                    title: "Desbloquear asignacion de pruebas",
                    text: "Al desbloquear esta opción le permite asignar pruebas al proyecto...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "orange",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=14&ipy=' + id_proyecto + '&ipe=' + id_prueba + '&estado=1';
                        });
            }
            function EliminarPruebaProyecto(id_proyecto, id_prueba_p, id_prueba_c, prueba) {
                swal({
                    title: "Eliminar prueba asignada",
                    text: "Seguro de eliminar prueba asignada...!",
                    type: "error",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Proyecto?opc=19&ipy=' + id_proyecto + '&ipp=' + id_prueba_p + '&ipc=' + id_prueba_c + '&t_prueba=' + prueba;
                        });
            }
            function ActivarEntradaProyecto(id_herramental, id_proyecto) {
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
                            location.href = 'Proyecto?opc=30&ipy=' + id_proyecto + '&ihc=' + id_herramental + '&estado=1';
                        });
            }
            function InactivarEntradaProyecto(id_herramental, id_proyecto) {
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
                            location.href = 'Proyecto?opc=30&ipy=' + id_proyecto + '&ihc=' + id_herramental + '&estado=0';
                        });
            }
            function QuitarSeccionAtender(id_herramental, id_proyecto) {
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
                            location.href = 'Proyecto?opc=30&ipy=' + id_proyecto + '&ihc=' + id_herramental + '&estado=0';
                        });
            }
            function ModificarSeccionAtender(id_herramental, id_proyecto) {
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
                            location.href = 'Proyecto?opc=30&ipy=' + id_proyecto + '&ihc=' + id_herramental + '&estado=0';
                        });
            }
        </script>
        <script type="text/javascript">
            function Enviar_caso() {
                window.onload = document.getElementById("Formulario").style.display = "none";
                window.onload = document.getElementById("Carga").style.display = "block";
            }
        </script>
        <script type="text/javascript">
            function Enviar_caso2() {
                window.onload = document.getElementById("Formulario").style.display = "none";
                window.onload = document.getElementById("Carga2").style.display = "block";
            }
        </script>
        <script type="text/javascript">
            function Form_prueba(datos, titulo) {
                document.getElementById("Control_pet").style.display = "block";
                document.getElementById("Txt_fase").value = datos;
                document.getElementById("Label_titulo").innerHTML = titulo;
            }
            function Form_pruebaCerrar() {
                document.getElementById("Control_pet").style.display = "none";
            }
        </script>
        <!--Otros-->
        <script type="text/javascript">
            function Memoria_registro(valor) {
                var id_memoria_c = valor.split('/')[0];
                var fase = valor.split('/')[1];
                document.getElementById("id_memoria_c").value = id_memoria_c;
                document.getElementById("Txt_posicion").value = fase;
            }
        </script>
        <script type="text/javascript">
            function Memoria_modificar(valor) {
                var id_memoria_c = valor.split('/')[0];
                var fase = valor.split('/')[1];
                document.getElementById("id_memoria_c").value = id_memoria_c;
                document.getElementById("Txt_posicion").value = fase;
            }
        </script>
        <script type="text/javascript">
            function calc(vari, id_proyecto, id_prueba_c, t_prueba)
            {
                var seleccion = document.getElementById("ckb_prueba" + vari).checked;
                if (seleccion == true) {
                    var id_prueba = document.getElementById("ckb_prueba" + vari).value;
                    location.href = 'Proyecto?opc=18&ipe=' + id_prueba + '&ipy=' + id_proyecto + '&ipc=' + id_prueba_c + '&t_prueba=' + t_prueba + '';
                }
            }
        </script>
        <!--<script type="text/javascript">
                function showContent() {
                    element_b = document.getElementById("seleccion_c_b");
                    element_n = document.getElementById("seleccion_c_n");
                    check = document.getElementById("check");
                    if (check.checked) {
                        element_b.style.display = 'block';
                        element_n.style.display = 'none';
                    } else {
                        element_b.style.display = 'none';
                        element_n.style.display = 'block';
                    }
                }
            </script>-->
        <!--Posicionar-->
        <script type="text/javascript">
            function Posicionar() {
                document.getElementById(document.getElementById("Txt_pos").value).scrollIntoView(true);
            }
        </script> 

        <script type="text/javascript">
            function Enviar_caso2() {
                const consecutivo = document.getElementById("consecutivo").value.trim();
                const fecha = document.getElementById("fecha_P").value.trim();
                const tipo_pr = document.getElementById("Rdb_tipo_consulta").value.trim();
                const proyecto = document.getElementById("proyecto-id").value.trim();
                const uso = document.getElementById("uso_previsto-id").value.trim();
                if (consecutivo && fecha && tipo_pr && proyecto && uso) {
                    window.onload = document.getElementById("Formulario").style.display = "none";
                    window.onload = document.getElementById("Carga2").style.display = "block";
                } else {
                    console.log("Faltan campos por llenar")
                }
            }
        </script>

        <script type="text/javascript">
            $(document).ready(function () {
                localStorage.removeItem('activeTab');
            });
        </script>

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
        <script type="text/javascript" src="Interfaz/Contenido/assets/Validacion/LiveValidation.js"></script>

    </body>
</html>
