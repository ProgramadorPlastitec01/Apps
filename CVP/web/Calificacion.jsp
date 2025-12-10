<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Calificacion.tld" prefix="Calificacion"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/CVP.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Calificacion</title>
            <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
            <script type="text/javascript">
                function Informe() {
                    var htmleditor = document.getElementsByName("HTML_Editor").innerHTML;
                    document.getElementsByName("Txt_descripcion").value = htmleditor;
                    document.Form_informe.submit();
                }
            </script>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Calificacion.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Calificacion.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <script type = "text/javascript" >
                    function PostProtocolo(Protocolo) {
                        var Romper = Protocolo.split(' / ');
                        var code = Romper[0] + "";
                        var name = Romper[1] + "";
                        document.getElementById("Txt_calificacion").value = name;
                    }
                </script>
                <script type="text/javascript">
                    function DesactivarCalificacion(id_calificacion) {
                        swal({
                            title: "Inactivar Calificacion",
                            text: "Seguro que desea desactivar calificacion..!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "red",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Calificacion?opc=2&Id_calificacion=' + id_calificacion + '&Estado=0';
                                });
                    }
                    function ActivarCalificacion(id_calificacion) {
                        swal({
                            title: "Activar Calificacion",
                            text: "Seguro que desea activar calificacion...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Calificacion?opc=2&Id_calificacion=' + id_calificacion + '&Estado=1';
                                });
                    }
                    function InformeVigente(id_calificacion, Id_informe) {
                        swal({
                            title: "Informe Vigente",
                            text: "Seguro que desea dejar en estado vigente del informe...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Calificacion?opc=9&icl=' + id_calificacion + '&iif=' + Id_informe;
                                });
                    }
                </script>
                <script type="text/javascript">
                    function ResponsabilidadesInforme(tipo_responsable, id_informe, id_calificacion) {
                        var titulo = '';
                        var descripcion = '';
                        if (tipo_responsable == 1) {
                            titulo = 'Ejecutar Informe';
                            descripcion = 'Seguro que desea dar por ejecutado el informe.';
                        } else if (tipo_responsable == 2) {
                            titulo = 'Revisar Informe';
                            descripcion = 'Seguro que desea dar por revisado el informe.';
                        } else {
                            titulo = 'Aprobar Informe';
                            descripcion = 'Seguro que desea dar por aprobado el informe.';
                        }
                        swal({
                            title: "" + titulo,
                            text: "" + descripcion,
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Calificacion?opc=7&iif=' + id_informe + '&icl=' + id_calificacion + '&trp=' + tipo_responsable;
                                });
                    }
                </script>
                <!-- Slección Implementos -->
                <script type="text/javascript">
                    function SeleccionCalificaciones(icl)
                    {
                        if (icl.checked) {
                            document.getElementById('Txt_seleccion_dependencias').value += "" + icl.value;
                        } else {
                            document.getElementById("Txt_seleccion_dependencias").value = document.getElementById("Txt_seleccion_dependencias").value.replace(icl.value, "");
                        }
                    }
                </script>
                <!-- Seleccion de informes -->
                <script type="text/javascript">
                    function SeleccionCalificacionesInforme(icl)
                    {
                        if (icl.checked) {
                            document.getElementById('Txt_seleccion_dependencias').value += "" + icl.value;
                        } else {
                            document.getElementById("Txt_seleccion_dependencias").value = document.getElementById("Txt_seleccion_dependencias").value.replace(icl.value, "");
                        }
                        if (document.getElementById('Txt_seleccion_dependencias').value.length > 0 || document.getElementById('Txt_seleccion_calificaciones').value.length > 0) {
                            document.getElementById("Btn_guardar_informe").style.display = "block";
                        } else {
                            document.getElementById("Btn_guardar_informe").style.display = "none";
                        }
                    }
                </script>
                <!-- Seleccion de calificaciones-->
                <script type="text/javascript">
                    function ProgramarCalificaciones(icl)
                    {
                        if (icl.checked) {
                            document.getElementById('Txt_seleccion_calificaciones').value += "" + icl.value;
                        } else {
                            document.getElementById("Txt_seleccion_calificaciones").value = document.getElementById("Txt_seleccion_calificaciones").value.replace(icl.value, "");
                        }
                        if (document.getElementById('Txt_seleccion_calificaciones').value.length > 0 || document.getElementById('Txt_seleccion_dependencias').value.length > 0) {
                            document.getElementById("Btn_guardar_informe").style.display = "block";
                        } else {
                            document.getElementById("Btn_guardar_informe").style.display = "none";
                        }
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Calificacion:Calificacion />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>