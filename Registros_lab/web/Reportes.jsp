<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<%@taglib uri="/WEB-INF/Tlds/Reporte.tld" prefix="Reportes"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Reportes</title>
            <!--            <script type = "text/javascript" >
                            history.pushState(null, null, 'Reportes.jsp');
                            window.addEventListener('popstate', function (event) {
                                history.pushState(null, null, 'Reportes.jsp');
                            });
                        </script>-->
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <script type="text/javascript" src="Interfaz/Paginas/Filtro_resaltado.js"></script>
                <!--HTML EDITOR-->
                <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
                <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
                <!--Otros-->
                <script type="text/javascript" language="javascript">
                    function mostrarConvencion(id) {
                        if (document.getElementById("Ventana" + id).style.display === "none") {
                            document.getElementById("Ventana" + id).style.display = "block";
                        } else if (document.getElementById("Ventana" + id).style.display === "block") {
                            document.getElementById("Ventana" + id).style.display = "none";
                            document.getElementById("subpage").style.overflow = "auto";
                        }
                    }
                </script>
                <script type="text/javascript">
                    function PostBackProducto() {
                        document.forms['FormReporteCalidad2'].submit();
                    }
                    function PostBackFicha() {
                        document.forms['FormReportEst'].submit();
                    }
                    function PostBackLinea() {
                        var Linea = document.getElementById("Cbx_linea");
                        document.forms['FormReporteCalidadOEE'].submit();
                    }
                    function PostBackAnio() {
                        var anio = document.getElementById("Cbx_anio");
                        document.forms['FormFiltroAnio'].submit();
                    }
                </script>
                <link href="Interfaz/Tabs/tabs.css" rel="stylesheet" type="text/css" />
                <script type="text/javascript" language="javascript">
                    function Agregar_seleccion() {
                        var texto_datoss = document.getElementById("Cbx_lote");
                        var textD = texto_datoss.options[texto_datoss.selectedIndex].text;
                        var textDS = textD.split("_");
                        var fecha_inicio = document.getElementById("start");
                        var hora_inicio = document.getElementById("Txt_hora_inicio");
                        var fecha_fin = document.getElementById("end");
                        var hora_fin = document.getElementById("Txt_hora_fin");
                        for (var j = 0; j < textDS.length; j++) {
                            var obj_lote = new Object(textDS);
                            if (j == 3) {
                                fecha_inicio.value = obj_lote[3];
                            } else if (j == 4) {
                                hora_inicio.value = obj_lote[4];
                            } else if (j == 7) {
                                fecha_fin.value = obj_lote[7];
                            } else if (j == 8) {
                                hora_fin.value = obj_lote[8];
                            }
                        }
                    }
                    function Form_comprobar_errores() {
                        document.getElementById('Form_comprobar').style.display = 'block';
                    }
                    function Form_comprobar_errores_cerrar() {
                        document.getElementById('Form_comprobar').style.display = 'none';
                    }
                    function Resaltar_error(error) {
                        document.getElementById('Txt_buscar').value = error;
                    }

                    function FormEditError(id) {
                        if (document.getElementById('Edit_comprobar').style.display === "block") {
                            document.getElementById('Edit_comprobar').style.display = 'none';
                            document.getElementById('Div' + id).style.display = 'none';
                        } else if (document.getElementById('Edit_comprobar').style.display === "none") {
                            document.getElementById('Edit_comprobar').style.display = 'block';
                            document.getElementById('Div' + id).style.display = 'block';
                        }
                    }
                    function EnviarEdit() {
                        swal({
                            title: "¡Confirmar Envio!",
                            text: "Se modificarán los datos pertenecientes a esta lote. ¿Estas seguro?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#15aabf",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false

                        },
                                function () {
                                    var form = document.getElementById("EnviarForm");
                                    form.submit();
                                });
                    }
                    function EnviarIds() {
                        var IdInicial = document.getElementById("IdList").value;
                        document.getElementById("IdModificar").value = IdInicial;
                    }
                </script>
                <script type="text/javascript">
                    function ValidarDetallado() {
                        var InputC = document.getElementById("Rdb_detalllado_oee");
                        var InputVal = document.getElementById("Txt_cod_producto2");
                        if (InputC.checked) {
                            InputVal.setAttribute("required", "required");
                        } else {
                            InputVal.removeAttribute("required");
                        }
                    }
                    function ValidarAgrupado() {
                        var InputA = document.getElementById("Rdb_agrupacion_oeeP");
                        var InputVal1 = document.getElementById("Txt_cod_producto2");
                        if (InputA.checked) {
                            InputVal1.removeAttribute("required");
                        } else {
                            InputVal1.setAttribute("required", "required");
                        }
                    }
                </script>
                <script>
                    chrome.runtime.onMessage.addListener(function (message, sender, sendResponse) {
                        setTimeout(function () {
                            // Algunas operaciones asíncronas
                        }, 1000);
                        return true;  // Indica que se enviará una respuesta de forma asíncrona
                    });
                    chrome.runtime.onMessage.addListener(function (message, sender, sendResponse) {
                        setTimeout(function () {
                            // Responder de manera asíncrona
                            sendResponse({success: true});
                        }, 1000);
                        return true;
                    });
                </script>
                <script>
                    function SeleccionFiltro(Cond) {
                        if (Cond === 1) {
                            document.getElementById("RangoFecha").style.display = "block";
                            document.getElementById("RangoAnio").style.display = "none";
                            document.getElementById("RangoF").classList.add("OpcionFecha");
                            document.getElementById("RangoA").classList.remove("OpcionFecha");

                        } else {
                            document.getElementById("RangoAnio").style.display = "block";
                            document.getElementById("RangoFecha").style.display = "none";
                            document.getElementById("RangoA").classList.add("OpcionFecha");
                            document.getElementById("RangoF").classList.remove("OpcionFecha");
                        }
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Reportes:Reporte />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
    </body>
</html>