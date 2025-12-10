<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Programacion.tld" prefix="Programacion"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html>   
    <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" />
    <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
   <title>Locativos MT</title>
    <script type = "text/javascript" >
        history.pushState(null, null, 'Programacion.jsp');
        window.addEventListener('popstate', function (event) {
            history.pushState(null, null, 'Programacion.jsp');
        });
    </script>
    <jsp:include page='Contenedor_head.jsp'></jsp:include>
    <!-- crear input-->
    <script type="text/javascript">
            function crear(obj) {
                var num = document.getElementById('cantidad_actividades').value;
                num++;
                if (document.getElementById('cantidad_actividades').value == 0) {
                    document.getElementById('cantidad_actividades').value = 1;
                    num = 1
                }
                document.getElementById('cantidad_actividades').value = num;
                fi = document.getElementById('fiel');
                contenedor = document.createElement('div');
                contenedor.id = 'div' + num;
                fi.appendChild(contenedor);
                var hr = document.createElement('hr');
                contenedor.appendChild(hr);
                ele = document.createElement('textarea');
                ele.type = 'text';
                ele.style = 'width:300px;text-transform:uppercase;';
                ele.name = 'Txt_actividad' + num;
                ele.id = 'Txt_actividad' + num;
                contenedor.appendChild(ele);
                ele = document.createElement('img');
                ele.src = 'Interfaz/Contenido/Iconos/Min.png';
                ele.style = 'width:26px;padding-bottom: 22px;padding-left:22px;height:25px;';
                ele.name = 'div' + num;
                ele.onclick = function () {
                    borrar(this.name)
                }
                contenedor.appendChild(ele);
                var text = document.createElement("b");
                text.innerHTML = "</br>Requiere alistamiento de area ?";
                contenedor.appendChild(text);
                var si = document.createElement("b");
                si.innerHTML = "SI";
                si.style = 'color:#000';
                ele = document.createElement('input');
                ele.type = 'radio';
                ele.checked = 'checked';
                ele.name = 'Rdb_area_lista' + num;
                ele.id = 'Rdb_area_lista' + num;
                ele.value = 'SI REQUERIDA';
                contenedor.appendChild(ele);
                contenedor.appendChild(si);
                var no = document.createElement("b");
                no.innerHTML = "NO";
                no.style = 'color:#000';
                ele = document.createElement('input');
                ele.type = 'radio';
                ele.name = 'Rdb_area_lista' + num;
                ele.id = 'Rdb_area_lista' + num;
                ele.value = 'NO REQUERIDA';
                contenedor.appendChild(ele);
                contenedor.appendChild(no);
            }
            function borrar(obj) {
                fi = document.getElementById('fiel');
                fi.removeChild(document.getElementById(obj));
                var cant = document.getElementById('cantidad_actividades').value;
                cant = cant - 1;
                document.getElementById('cantidad_actividades').value = cant;
                document.getElementById('cont_objet').value = document.getElementById('cont_objet').value - 1;
            }
        </script>
    <script type="text/javascript">
                function registroP() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
    <script type="text/javascript">
                function registroPS() {
                    document.getElementById("btsubmit1").disabled = true;
                    document.getElementById("btsubmit1").value = "";
                    document.getElementById("puntos1").style.display = "block";
                }
            </script>
    <!--checkbox -->
    <script type="text/javascript">
            function add_sub(el)
            {
                if (el.checked) {
                    document.getElementById('Id_solicitudes').value += "" + el.value;
                } else {
                    document.getElementById("Id_solicitudes").value = document.getElementById("Id_solicitudes").value.replace(el.value, "");
                }
            }
        </script>
    <!--Confirmar eliminacion -->
    <script>
            function Confirmar_eliminacion(Id_actividad, id_programacion, id_programacion_detalle, id_solicitud) {
                swal({
                    title: "Elminar Actividad?",
                    text: "Seguro que desea Eliminar esta actividad..!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            location.href = 'Programacion?opc=10&Id_actividad=' + Id_actividad + '&Id_programacion=' + id_programacion + '&Id_programacion_detalle=' + id_programacion_detalle + '&Id_Solicitud=' + id_solicitud;
                        });
            }
        </script>
        <!--Confirmar cierre de programacion -->
        <script>
            function Confirmar_cierre_de_programacion() {
                swal({
                    title: "Cerrar Programacion",
                    text: "Seguro que desea cerrar esta programacion...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            document.form_actualizar_solicitud.submit();
                        });
            }
        </script>
        <!--Enviar a formulario de externos-->
        <script>
            function form_externos(id_proveedor) {
                location.href = 'javascript:Form_externos.submit(' + id_proveedor + ');';
            }
        </script>
        <!--Enviar correo de la programacion-->
        <script type="text/javascript">
            function CierreProgramacion(id_programacion) {
                swal({
                    title: "Cerrar Programación?",
                    text: "Desea enviar un correo de la programación...!\n\
                           Recuerde enviar el correo",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            location.href = 'Programacion?opc=13&Id_programacion=' + id_programacion;
                        });
            }
        </script>
        <script type="text/javascript">
            function EnviarCorreoProgramacion(id_programacion) {
                swal({
                    title: "Enviar Programación?",
                    text: "Desea enviar un correo de la programación...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            location.href = 'Programacion?opc=26&Id_programacion=' + id_programacion;
                        });
            }
        </script>
        <!--Terminar ejecución.-->
        <script type="text/javascript">
            function Terminar_ejecucion(id_programacion) {
                swal({
                    title: "Terminar Ejecucion?",
                    text: "Desea terminar la ejecucion de la programación enviandola a revision....!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            location.href = 'Programacion?opc=14&Id_programacion=' + id_programacion + '';
                        });
            }
            function VolverEjecucion(id_programacion) {
                swal({
                    title: "Volver Ejecucion",
                    text: "Seguro de enviar la programación a estado de ejecucion...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            location.href = 'Programacion?opc=15&Id_programacion=' + id_programacion + '';
                        });
            }
        </script>
        <!--SIN ACTIVIDADES.-->
        <script type="text/javascript">
            function SinActividades() {
                swal({
                    title: "Sin completar",
                    text: "Hay solicitudes sin actividades....!",
                    type: "error",
                    timer: 3000
                });
            }
            function SolicitudesDuplicadas(solicitudes) {
                swal({
                    title: "Duplicado",
                    text: "Se han filtrado a la programación solicitudes duplicadas " + solicitudes + "....!",
                    type: "error",
                    timer: 3000
                });
            }
        </script>
        <!--Salto linea -->
        <script type='text/javascript'>
            function Salto_linea() {
                var textarea = document.getElementById("Txt_trabajadores_externos_temp").value;
                var textarea_line = textarea.replace(new RegExp("\\n", "g"), "---");
                document.getElementById("Txt_trabajadores_externos_registro").value = textarea_line;
            }
        </script>
        <!--Ver listado de externos -->
        <script>
            function ver_lista_ejecutores() {
                document.getElementById("ver_listado").style.display = "block";
            }
        </script>
        <!-- cambiar el N/A en empresas externas-->
        <script>
            function cambiar_el_NA(externo, id_programacion_detalle) {
                if (externo == 1) {
                    document.getElementById("mostrar_textarea" + id_programacion_detalle).style.display = "none";
                    document.getElementById("textarea_vacio" + id_programacion_detalle).innerHTML = "<input type='hidden' name='Txt_trabajadores_externos" + id_programacion_detalle + " id='Txt_trabajadores_externos" + id_programacion_detalle + " value='N/A' />"
                } else {
                    document.getElementById("mostrar_textarea" + id_programacion_detalle).style.display = "block";
                }
            }
        </script>
        <!-- Seleccionar todo -->
        <script type="text/javascript">
            function Seleccionar_todo() {
                for (i = 0; i < document.Form_asignar.elements.length; i++) {
                    if (document.Form_asignar.elements[i].type == "checkbox") {
                        document.Form_asignar.elements[i].checked = 1
                    }
                }
            }
            function Deseleccionar_todo() {
                for (i = 0; i < document.Form_asignar.elements.length; i++) {
                    if (document.Form_asignar.elements[i].type == "checkbox") {
                        document.Form_asignar.elements[i].checked = 0
                    }
                }
            }
        </script>
        <!-- Desplegar lista externos -->
        <script type="text/javascript">
            function showContent() {
                element = document.getElementById("content");
                check = document.getElementById("check_externo");
                if (check.checked) {
                    element.style.display = 'block';
                } else {
                    element.style.display = 'none';
                }
            }
        </script>
        <!-- confirmar eliminacion de solicitud en la programacion -->
        <script>
            function Quitar_solicitud_de_programacion(Id_solicitud, Id_programacion_detalle, Id_programacion) {
                swal({
                    title: "Quitar Programación?",
                    text: "Seguro que desea quitar la solicitud de esta programación...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            location.href = 'Programacion?opc=16&Id_solicitud=' + Id_solicitud + '&Id_programacion_detalle=' + Id_programacion_detalle + '&Id_programacion=' + Id_programacion;
                        });
            }
        </script>
        <!-- externos -->
        <script type="text/javascript">
            function externos_sub(el)
            {
                if (el.checked) {
                    document.getElementById('Id_solicitud_externos').value += "" + el.value;
                } else {
                    document.getElementById("Id_solicitud_externos").value = document.getElementById("Id_solicitud_externos").value.replace(el.value, "");
                }
            }
        </script>
        <!-- actividades adicionales -->
        <script type="text/javascript">
            function actividades_adicionales(obj) {
                var num = document.getElementById('cantidad_actividades').value;
                num++;
                if (document.getElementById('cantidad_actividades').value == 0) {
                    document.getElementById('cantidad_actividades').value = 1;
                    num = 1;
                }
                document.getElementById('cantidad_actividades').value = num;
                fi = document.getElementById('fiel');
                contenedor = document.createElement('div');
                contenedor.id = 'div' + num;
                fi.appendChild(contenedor);
                var hr = document.createElement('hr');
                contenedor.appendChild(hr);
                var text = document.createElement("b");
                text.innerHTML = "Actividad adicional";
                contenedor.appendChild(text);
                var text = document.createElement("b");
                text.innerHTML = "</br>";
                contenedor.appendChild(text);
                ele = document.createElement('input');
                ele.type = 'text';
                ele.name = 'Txt_ubicacion' + num;
                ele.style = 'width:300px;text-transform:uppercase;';
                ele.id = 'Txt_ubicacion' + num;
                ele.required = true;
                ele.onchange = 'javascript:this.value=this.value.toUpperCase()';
                ele.placeholder = 'Ubicación';
                contenedor.appendChild(ele);
                var text = document.createElement("b");
                text.innerHTML = "</br>";
                contenedor.appendChild(text);
                ele = document.createElement('textarea');
                ele.type = 'text';
                ele.style = 'width:300px;text-transform:uppercase;';
                ele.name = 'Txt_actividad' + num;
                ele.id = 'Txt_actividad' + num;
                ele.required = true;
                ele.onchange = 'javascript:this.value=this.value.toUpperCase()';
                ele.placeholder = 'Actividad adicional';
                contenedor.appendChild(ele);
                ele = document.createElement('img');
                ele.src = 'Interfaz/Contenido/Iconos/Min.png';
                ele.style = 'padding-bottom:22px;padding-left:22px;width:22px;height:22px;';
                ele.name = 'div' + num;
                ele.onclick = function () {
                    borrar(this.name)
                }
                contenedor.appendChild(ele);
            }
            function borrar(obj) {
                fi = document.getElementById('fiel');
                fi.removeChild(document.getElementById(obj));
                var cant = document.getElementById('cantidad_actividades').value;
                cant = cant - 1;
                document.getElementById('cantidad_actividades').value = cant;
                document.getElementById('cont_objet').value = document.getElementById('cont_objet').value - 1;
            }
        </script>
        <!--Confirmar eliminacion de actividad adicional-->
        <script type="text/javascript">
            function Confirmar_eliminacion_de_actividad(id_actividad_adicional, id_programacion) {
                swal({
                    title: "Eliminar Actividad?",
                    text: "Seguro que desea Eliminar esta actividad...!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "red",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            //if (isConfirm) {
                            location.href = 'Programacion?opc=22&Id_actividad_adicional=' + id_actividad_adicional + '&Id_programacion=' + id_programacion;
                            //} else {
                            //    location.href = 'Programacion?opc=7&Id_programacion=' + id_programacion;
                            // }
                        });
            }
        </script>
        <!--Guardar-->
        <script type="text/javascript">
            function Guardar_ejecucion_prog() {
                swal({
                    title: "Guardar",
                    text: "Seguro de enviar la ejecucion de las actividades...!",
                    type: "info",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true,
                },
                        function () {
                            document.form_ejecucion.submit();
                        });
            }
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#browser").treeview({
                    toggle: function () {
                        console.log("%s was toggled.", $(this).find(">span").text());
                    }
                });
                $("#add").click(function () {
                    var branches = $("<li><span class='folder'>New Sublist</span><ul>" +
                            "<li><span class='file'>Item1</span></li>" +
                            "<li><span class='file'>Item2</span></li></ul></li>").appendTo("#browser");
                    $("#browser").treeview({
                        add: branches
                    });
                });
            });
        </script>
    </head>
    <body id="subpage" style="background:#FFF url(Interfaz/Contenido/images/pattern.png) repeat top left;">
        <div id="templatemo_wrapper">
        <Menu:Menu />
        <Programacion:Programacion/>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </div>
</body>
</html>
