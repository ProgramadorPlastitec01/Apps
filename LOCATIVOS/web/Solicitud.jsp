<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Solicitud.tld" prefix="solicitud"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
       <title>Locativos MT</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Solicitud.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Solicitud.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script type="text/javascript">
                function add_sub(el)
                {
                    if (el.checked) {
                        document.getElementById('Id_solicitudes_correo').value += "" + el.value;
                    } else {
                        document.getElementById("Id_solicitudes_correo").value = document.getElementById("Id_solicitudes_correo").value.replace(el.value, "");
                    }
                }
            </script>
            <script type="text/javascript">
                function registroS() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function EnviarCorreo() {
                    swal({
                        title: "Confirmar Solicitudes?",
                        text: "Desea enviar un correo de la(s) solicitud(es)...!",
                        type: "info",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        closeOnConfirm: false,
                        showLoaderOnConfirm: true,
                    },
                            function () {
                                document.vector_solicitudes_correo.submit();
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
            <!--Agrupar solicitudes-->
            <script type="text/javascript">
                function agrupar_solicitudes(el)
                {
                    if (el.checked) {
                        document.getElementById('Id_solicitudes_agrupar').value += "" + el.value;
                    } else {
                        document.getElementById("Id_solicitudes_agrupar").value = document.getElementById("Id_solicitudes_agrupar").value.replace(el.value, "");
                    }
                }
            </script>
            </script>
            <!--Agrupar solicitudes-->
            <script type="text/javascript">
                function Programar_Solicitudes(el)
                {
                    if (el.checked) {
                        document.getElementById('Id_Solicitudes_Programar').value += "" + el.value;
                    } else {
                        document.getElementById("Id_Solicitudes_Programar").value = document.getElementById("Id_Solicitudes_Programar").value.replace(el.value, "");
                    }
                }
            </script>
            <!-- validacion de campos de agrupacion-->
            <script languaje="javascript">
                function habilitaDeshabilita(Id_solicitud, id) {
                    var arg_solicitudes = Id_solicitud.split("-");
                    for (i = 0; i < arg_solicitudes.length; i++) {
                        if (arg_solicitudes[i] === id) {
                            document.getElementById("box" + arg_solicitudes[i]).style.backgroundColor = "none";
                            document.getElementById("box" + arg_solicitudes[i]).style.backgroundColor = "green";
                            document.getElementById("estado" + arg_solicitudes[i]).innerHTML = "Principal";
                            document.getElementById("estado" + arg_solicitudes[i]).style.color = "green";
                            document.getElementById("confirmar").setAttribute("style", "displa:block");
                            document.getElementById("Id_solicitud_principal").value = id;
                        } else {
                            document.getElementById("box" + arg_solicitudes[i]).style.backgroundColor = "none";
                            document.getElementById("box" + arg_solicitudes[i]).style.backgroundColor = "red";
                            document.getElementById("estado" + arg_solicitudes[i]).innerHTML = "Agrupada";
                            document.getElementById("estado" + arg_solicitudes[i]).style.color = "red";
                            document.getElementById("confirmar").setAttribute("style", "displa:none");
                        }
                    }
                }
            </script>
            <script type="text/javascript">
                function DeclinarSolicitud(id_solicitud) {
                    swal({
                        title: "Declinar Solicitud",
                        text: "Justificar eliminación de la orden de trabajo ya que el consecutivo se desaparece y el valor del horometro sera restablecido al anterior.<br /><br /><form action='Solicitud?opc=15&isl=" + id_solicitud + "' id='FormDeclinarSolicitud' method='post'><textarea name='Txt_justificacion' id='Txt_justificacion' style='width:350px;height:100px' placeholder='Justificar por que se declina la solicitud' required onfocus></textarea></form><button type='submit' form='FormDeclinarSolicitud'>Declinar</button>",
                        type: "error",
                        showConfirmButton: false,
                        html: true,
                    });
                }
            </script>

        </head>
        <body id="subpage" style="background:#FFF url(Interfaz/Contenido/images/pattern.png) repeat top left;">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <solicitud:Solicitud/>
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>
