
<%@taglib uri="/WEB-INF/Tlds/Complemento.tld" prefix="Complementos" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Complemento | A-D&D</title>
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
                    <Complementos:Complementos/>
                </div>
            </div>
        </div>

        <Alertas:Alertas/>

        <script type="text/javascript">
            function reinicializarTooltips() {
                if (typeof $ !== 'undefined' && typeof $.fn.tooltip !== 'undefined') {
                    $('[data-toggle="tooltip"]').tooltip();
                } else {
                    console.error("jQuery o Bootstrap no están cargados");
                }
            }

            $(document).ready(function () {
                var table = $('#table-1').DataTable();
                reinicializarTooltips();
                table.on('draw', function () {
                    reinicializarTooltips();
                });
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
        </script>

        <script type="text/javascript" language="javascript">
            function current_year() {
                var year_already = new Date().getFullYear();

                document.getElementById('contra_year').value = year_already;
            }

            document.addEventListener('DOMContentLoaded', function () {
                current_year();
            });
        </script>        

        <script type="text/javascript" language="javascript">
            function InactivarUsuario(consulta, id, estado) {
                swal({
                    title: "Inactivar usuario",
                    text: "Seguro que desea desactivar el usuario...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Usuario?opc=4&complemento=' + consulta + '&Id_usu=' + id + '&estado=' + estado + '';
                        });
            }
        </script>  

        <script type="text/javascript" language="javascript">
            function ActivarUsuario(consulta, id, estado) {
                swal({
                    title: "Activar usuario",
                    text: "Seguro que desea activar el usuario...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Usuario?opc=4&complemento=' + consulta + '&Id_usu=' + id + '&estado=' + estado + '';
                        });
            }
        </script>   
        
        <script type="text/javascript" language="javascript">
            function Restablecer_password(consulta, id) {
                swal({
                    title: "Restablecer Contraseña",
                    text: "Seguro que desea restablecer la contraseña del usuario...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#f8be86",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Usuario?opc=5&complemento=' + consulta + '&Id_usu=' + id + '';
                        });
            }
        </script>   

        <script type="text/javascript" language="javascript">
            function InactivarEtapa(consulta, id, estado) {
                swal({
                    title: "Inactivar etapa",
                    text: "Seguro que desea desactivar la etapa...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=4&complemento=' + consulta + '&Id_E=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function ActivarEtapa(consulta, id, estado) {
                swal({
                    title: "Activar etapa",
                    text: "Seguro que desea activar la etapa...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=4&complemento=' + consulta + '&Id_E=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script>
            const textarea = document.getElementById('Guia_norma');

            function addAsteriskToLines(value) {
                return value.split('\n').map(line => '*' + line).join('\n');
            }

            textarea.value = '*';

            textarea.addEventListener('keydown', function (event) {
                if (event.key === 'Enter') {
                    event.preventDefault();

                    const start = textarea.selectionStart;
                    const end = textarea.selectionEnd;

                    const value = textarea.value;
                    const before = value.substring(0, start);
                    const after = value.substring(end);

                    const newValue = before + '\n*' + after;

                    textarea.value = newValue;

                    textarea.selectionStart = textarea.selectionEnd = start + 3;
                }
            });
        </script>

        <script type="text/javascript" language="javascript">
            function InactivarFase(consulta, id, estado) {
                swal({
                    title: "Inactivar fase",
                    text: "Seguro que desea desactivar la fase...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=7&complemento=' + consulta + '&Id_F=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function InactivarArea(consulta, id, estado) {
                swal({
                    title: "Inactivar Área",
                    text: "Seguro que desea desactivar el área...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=10&complemento=' + consulta + '&Id_A=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function ActivarArea(consulta, id, estado) {
                swal({
                    title: "Activar Área",
                    text: "Seguro que desea activar el área...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=10&complemento=' + consulta + '&Id_A=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function InactivarCargo(consulta, id, estado) {
                swal({
                    title: "Inactivar Cargo",
                    text: "Seguro que desea desactivar el cargo...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=13&complemento=' + consulta + '&Id_C=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function ActivarCargo(consulta, id, estado) {
                swal({
                    title: "Activar Cargo",
                    text: "Seguro que desea activar el cargo...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=13&complemento=' + consulta + '&Id_C=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function InactivarPrueba(consulta, id, estado) {
                swal({
                    title: "Inactivar Prueba",
                    text: "Seguro que desea desactivar la prueba...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=16&complemento=' + consulta + '&Id_P=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function ActivarPrueba(consulta, id, estado) {
                swal({
                    title: "Activar Prueba",
                    text: "Seguro que desea activar la prueba...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=16&complemento=' + consulta + '&Id_P=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function InactivarCatego(consulta, id, estado) {
                swal({
                    title: "Inactivar Categoria",
                    text: "Seguro que desea desactivar la categoria...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=19&complemento=' + consulta + '&Id_Catego=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript" language="javascript">
            function ActivarCatego(consulta, id, estado) {
                swal({
                    title: "Activar Categoria",
                    text: "Seguro que desea activar la categoria...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Complemento?opc=19&complemento=' + consulta + '&Id_Catego=' + id + '&estado=' + estado + '';
                        });
            }
        </script>

        <script type="text/javascript">
            function Masivo(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("Cbx_permission").value;
                if (content.includes(id)) {
                    document.getElementById("Cbx_permission").value = content.replace(id, "");
                } else {
                    document.getElementById("Cbx_permission").value += id;
                }
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

        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>

        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/froala_editor.pkgd.min.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/file.min.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/image.min.js"></script>

        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/froala-file-manager.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/froala/JS/froala-image-editor.js"></script>

    </body>
</html>
