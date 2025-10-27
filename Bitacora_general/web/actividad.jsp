<%@page contentType="text/html" pageEncoding="ISO-8859-1"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_actividad.tld" prefix="actividad" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <title>Actividad</title>

        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>

            <script type="text/javascript">
                function RegistroA() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function ModificarA() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript" >
                function completar(obj) {
                    if (obj <= 9) {
                        compl = "0" + obj;
                    } else {
                        compl = obj;
                    }
                    return compl;
                }
                function fecha() {
                    horasistema = new Date();
                    hora = horasistema.getHours();
                    minutos = horasistema.getMinutes();
                    segundos = horasistema.getSeconds();
                    anio = horasistema.getFullYear();
                    mes = horasistema.getMonth() + 1;
                    dia = horasistema.getDate();
                    horacompl = completar(hora);
                    minutcomple = completar(minutos);
                    segundocomple = completar(segundos);
                    aniocomple = completar(anio);
                    mescomplet = completar(mes);
                    diacomplet = completar(dia);
                    fechaCompleta = aniocomple + "-" + mescomplet + "-" + diacomplet;
                    horaCompleta = horacompl + ":" + minutcomple + ":" + segundocomple;
                    document.form1.tmhora.value = horaCompleta;
                    document.form1.txtfecha.value = fechaCompleta;
                }
            </script>
            <!--Marcar y desmarcar check-->

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
            <!--validacion acciones navegador-->
            <script language="javascript">
                function checkKeyCode(evt)
                {
                    var evt = (evt) ? evt : ((event) ? event : null);
                    var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
                    if (event.keyCode == 116)
                    {
                        evt.keyCode = 0;
                        return false
                    }
                }
                document.onkeydown = checkKeyCode;
            </script>
            <script type = "text/javascript" >
                history.pushState(null, null, 'actividad.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'actividad.jsp');
                });
            </script>
            <script type="text/javascript">
                var statsend = false;
                function checkSubmit() {
                    if (!statsend) {
                        statsend = true;
                        return true;
                    } else {
                        alert(" Un momento por favor el formulario se esta enviando...");
                        return false;
                    }
                }
            </script>
            <script type="text/javascript">
                function CerrarActividad(actividad, CargoUsa) {
                    swal({
                        title: "Finalizar!",
                        text: "Seguro que desea Finalizar?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#666666",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Actividad?op=4&idA=" + actividad + "&idC=" + CargoUsa + "&cier=1&txt_bus=";
                            });
                }
            </script>
            <script>
                function yesnoCheck() {
                    if (document.getElementById('yesCheck').checked) {
                        document.getElementById('ifYes').style.visibility = 'visible';
                    } else
                        document.getElementById('ifYes').style.visibility = 'hidden';

                }
            </script>
            <style>
                #toggleF {
                    float: right;
                    width: 500px;
                    font-size: 14px;
                    background-color: #fff;
                }
                #toggleR {
                    float: left;
                    width: 485px;
                    font-size: 14px;
                    background-color: #fff;
                    position: absolute;
                    margin-left: 25px;
                }
            </style>
            <script type="text/javascript" src="Interfaz/Contenido/Editor/ckeditor.js"></script>
        </head>
        <body onload="fecha()" id="subpage">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <actividad:MuestraActividad />
        </div>
        <resultados:MuestraResultados />

        <script src="Calendarios/Js_range.js" type="text/javascript"></script>
        <script src="Calendarios/Js_normal.js" type="text/javascript"></script>

                <script>
                        CKEDITOR.replace('editor1', {
                            skin: 'kama',
                            extraPlugins : 'image2'
                        });
                </script>

    </body>
</html>

