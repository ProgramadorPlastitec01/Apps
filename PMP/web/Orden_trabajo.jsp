<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/Tlds/Orden_trabajo.tld" prefix="Orden_trabajo" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orden Trabajo</title>
        <link rel="shortcut icon" href="Interfaz/Contenido/images/PMP_MI.ico" type="image/x-icon">

        <link rel="stylesheet" href="Interfaz/Contenido/Css/CSS_contol.css">
        <link rel="stylesheet" href="Interfaz/chocolat/css/chocolat.css">
        <link rel="stylesheet" href="Interfaz/Contenido/Css/components.css">

        <link rel="stylesheet" href="Interfaz/froala/CSS/froala_editor.pkgd.min.css">
        <link rel="stylesheet" href="Interfaz/froala/CSS/file.min.css">
        <link rel="stylesheet" href="Interfaz/froala/CSS/image.min.css">

        <style>
            .fr-image-resizer .fr-handler.fr-hnw{
                width: 10% !important;
                height: 10% !important;
            }
            .fr-image-resizer .fr-handler.fr-hne {
                width: 10% !important;
                height: 10% !important;
            }
            .fr-image-resizer .fr-handler.fr-hsw {
                width: 10% !important;
                height: 10% !important;
            }
            .fr-image-resizer .fr-handler.fr-hse {
                width: 10% !important;
                height: 10% !important;
            }
        </style>

    </head>
    <body  id="subpage" onload="Posicionar()">
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <div id="templatemo_wrapper">
            <Menu:Menu/>
            <Orden_trabajo:Orden_trabajo/>
        </div>
        <Alertas:Alertas/>

        <script src="Interfaz/Contenido/Scripts/jquery.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/bootstrap.min.js"></script>
        <script src="Interfaz/chocolat/js/jquery.chocolat.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/scripts.js"></script>

        <script src="Interfaz/froala/JS/froala_editor.pkgd.min.js"></script>
        <script src="Interfaz/froala/JS/image.min.js"></script>
        <script src="Interfaz/froala/JS/file.min.js"></script>
        <script src="Interfaz/froala/JS/es.js"></script>
        <script src="Interfaz/froala/JS/froala-file-manager.js"></script>
        <script src="Interfaz/froala/JS/froala-image-editor.js"></script>


        <script type="text/javascript">
        function QuitarParametro(id_parametro, id_orden) {
            swal({
                title: "Quitar Parametro?",
                text: "Seguro que desea quitar el parametro de la OT...!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "red",
                confirmButtonText: "Aceptar",
                cancelButtonText: "Cancelar",
                closeOnConfirm: false,
            },
                    function () {
                        location.href = 'Orden_trabajo?opc=8&iot=' + id_orden + '&ipo=' + id_parametro;
                    });
        }
        function QuitarActividad(id_actividad, id_orden) {
            swal({
                title: "Quitar Actividad?",
                text: "Seguro que desea quitar la actividad de la OT...!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "red",
                confirmButtonText: "Aceptar",
                cancelButtonText: "Cancelar",
                closeOnConfirm: false,
            },
                    function () {
                        location.href = 'Orden_trabajo?opc=7&iot=' + id_orden + '&iao=' + id_actividad;
                    });
        }
        </script>

        <script type="text/javascript">
            function CerrarProgramacion(id_orden, id_equipo) {
                swal({
                    title: "Cerrar Programacion?",
                    text: "Seguro que desea cerrar la programación y enviar a ejecucion de la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=9&iot=' + id_orden + '&ieq=' + id_equipo;
                        });
            }
        </script>

        <script type="text/javascript">
            function CerrarEjecucion(id_orden, id_equipo) {
                swal({
                    title: "Cerrar Ejecucion?",
                    text: "Seguro que desea cerrar la ejecución de la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=12&iot=' + id_orden + '&ieq=' + id_equipo;
                        });
            }
        </script>

        <script type="text/javascript">
            function CerrarRevision(id_orden, id_equipo) {
                swal({
                    title: "Cerrar Revision?",
                    text: "Seguro que desea cerrar la revisión de la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=13&iot=' + id_orden + '&ieq=' + id_equipo;
                        });
            }
        </script>

        <script type="text/javascript">
            function VolverProgramacion(id_orden) {
                swal({
                    title: "Volver a Programacion?",
                    text: "Seguro que desea enviar la OT a programación...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=25&iot=' + id_orden;
                        });
            }
        </script>

        <script type="text/javascript">
            function VolverEjecucion(id_orden) {
                swal({
                    title: "Volver Ejecucion?",
                    text: "<form action='Orden_trabajo?opc=20&iot=" + id_orden + "' id='FormVolverEjecucion' method='post'><textarea name='Txt_justificacion' id='Txt_justificacion' style='width:350px;height:100px' placeholder='Justificar Devolución de la OT' required onfocus></textarea></form><button type='submit' form='FormVolverEjecucion'>Enviar</button>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true,
                });
            }
        </script>

        <script type="text/javascript">
            function EliminarOT(id_orden, numero, id_equipo, horometro) {
                swal({
                    title: "Eliminar OT " + numero,
                    text: "Justificar eliminación de la orden de trabajo ya que el consecutivo se desaparece y el valor del horometro sera restablecido al anterior.<br /><br /><form action='Orden_trabajo?opc=24&iot=" + id_orden + "&ieq=" + id_equipo + "&nmo=" + numero + "&hrm=" + horometro + "' id='FormEliminarOT' method='post'><textarea name='Txt_justificacion' id='Txt_justificacion' style='width:350px;height:100px' placeholder='Justificar Eliminación de la OT' required onfocus></textarea></form><button type='submit' form='FormEliminarOT'>Eliminar</button>",
                    type: "error",
                    showConfirmButton: false,
                    html: true,
                });
            }
        </script>

        <script type="text/javascript">
            function CerrarOT(id_orden, id_equipo) {
                swal({
                    title: "Cerrar OT?",
                    text: "Seguro que desea cerrar la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=14&iot=' + id_orden + '&ieq=' + id_equipo;
                        });
            }
        </script>

        <script type="text/javascript">
            function CambiarResponsables(id_orden, id_equipo) {
                swal({
                    title: "Cambiar OT?",
                    text: "Seguro de ir modificar los responsables de la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=1&ieq=' + id_equipo + '&ot=' + id_orden + '&fto=';
                        });
            }
        </script>

        <script type="text/javascript">
            function CrearNovedades(id_orden) {
                swal({
                    title: "Novedades OT?",
                    text: "Seguro de habilitar una novedad para la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=17&iot=' + id_orden + '';
                        });
            }
        </script>

        <script type="text/javascript">
            function CrearRepuestos(id_orden) {
                swal({
                    title: "Repuestos OT?",
                    text: "Seguro de habilitar un repuesto para la OT...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                },
                        function () {
                            location.href = 'Orden_trabajo?opc=15&iot=' + id_orden + '';
                        });
            }
        </script>

        <script type="text/javascript">
            function seleccionar_todo() {
                for (i = 0; i < document.f1.elements.length; i++) {
                    if (document.f1.elements[i].type == "checkbox") {
                        document.f1.elements[i].checked = 1
                    }
                }
            }
            function deseleccionar_todo() {
                for (i = 0; i < document.f1.elements.length; i++) {
                    if (document.f1.elements[i].type == "checkbox") {
                        document.f1.elements[i].checked = 0
                    }
                }
            }
        </script>

        <script type="text/javascript">
            function Form_registro_actividades() {
                document.getElementById('Form_registro_actividades').style.display = 'block';
            }
            function Form_registro_actividades_cerrar() {
                document.getElementById('Form_registro_actividades').style.display = 'none';
            }
            function Form_registro_parametros() {
                document.getElementById('Form_registro_parametros').style.display = 'block';
            }
            function Form_registro_parametros_cerrar() {
                document.getElementById('Form_registro_parametros').style.display = 'none';
            }
        </script>

        <script type="text/javascript">
            function Posicionar() {
                document.getElementById(document.getElementById("Txt_pos").value).scrollIntoView(true);
            }
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
                xhr.open("POST", "http://172.16.1.166/PMP_MI/flmngr/envio.php", true);
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
