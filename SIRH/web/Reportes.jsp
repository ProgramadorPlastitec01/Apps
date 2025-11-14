<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/tlds/Reportes.tld" prefix="Reportes" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type="text/javascript" >
            history.pushState(null, null, 'Reportes.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Reportes.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script src="Interfaz/MasterPage/loader.js" type="text/javascript"></script>
            <script type="text/javascript">
            $(window).load(function () {
                $(".loader").fadeOut("slow");
            });
            </script>
            <script type="text/javascript" src="Interfaz/Parallax/scripts/parallax.js"></script>
            <script type="text/javascript" src="Interfaz/Parallax/scripts/jquery.js"></script>
            <link rel="stylesheet" type="text/css" href="Interfaz/Parallax/styles/style.css" />
            <link rel="stylesheet" type="text/css" href="Interfaz/Parallax/styles/style.css" />
            <!-- JavaScriptbar code -->
            <script type="text/javascript" src="Interfaz/Barcode/sample/jquery-1.3.2.min.js"></script>
            <script type="text/javascript" src="Interfaz/Barcode/jquery-barcode.js"></script>
        </head>
        <body style='background-image:url("Interfaz/MasterPage/images/BG7.png");background-size: auto;'>
        <Menu:Menu />
        <div id="wrapper" class="container" >
            <div class="loader"></div>
            <div class="loader2" id="Control_carga"></div>
            <div id="page">
                <Alertas:Alertas />
                <Reportes:Reportes />
                <script type="text/javascript">
            function cambiar() {
                var pdrs = document.getElementById('file-input').files[0].name;
                document.getElementById('File_name').innerHTML = pdrs;
            }
            function leerArchivo(e) {
                var archivo = e.target.files[0];
                if (!archivo) {
                    return;
                }
                var lector = new FileReader();
                lector.onload = function (e) {
                    var contenido = e.target.result;
                    mostrarContenido(contenido);
                };
                lector.readAsText(archivo);
            }
            function mostrarContenido(contenido) {
                var elemento = document.getElementById('contenido-archivo');
                elemento.innerHTML = contenido;
                document.getElementById("Id_separar").style.pointerEvents = "auto";
                document.getElementById("Id_separar").style.color = "#596275";
                document.getElementById("Id_integridad").style.pointerEvents = "none";
                document.getElementById("Id_verificar").style.pointerEvents = "none";
            }
            document.getElementById('file-input').addEventListener('change', leerArchivo, false);
                </script>
                <script type="text/javascript">
                    function SepararData() {
                        var elemento = document.getElementById('contenido-archivo');
                        var data = document.getElementById('Txt_salarios');
                        var datos = elemento.innerHTML;
                        elemento.style.backgroundColor = '#ffffdb';
                        data.value = datos.replace(/[\n\r]/g, ' | ');
                        document.getElementById("Id_separar").style.pointerEvents = "none";
                        document.getElementById("Id_separar").style.color = "#aaa";
                        document.getElementById("Id_integridad").style.pointerEvents = "auto";
                        document.getElementById("Id_integridad").style.color = "#596275";
                        document.getElementById("Id_verificar").style.pointerEvents = "none";
                    }
                </script>
                <script type="text/javascript">
                    function IntegridadData() {
                        var contenedor_data = document.getElementById("Txt_salarios").value;
                        var elemento = document.getElementById("contenido-archivo");
                        var elemento2 = document.getElementById("contenido-proceso");
                        var datos_originales = elemento2.innerHTML;
                        var arg_contenedor_data = contenedor_data.split(" | ");
                        var cont_bien = 0;
                        var cont_mal = 0;
                        var datos_mal = "";
                        for (var i = 0; i < arg_contenedor_data.length; i++) {
                            var org_datos = arg_contenedor_data[i];
                            var arg_datos = arg_contenedor_data[i].split(";");
                            var cont_temp = 0;
                            for (var j = 0; j < arg_datos.length; j++) {
                                if (arg_datos[j].length === 0) {
                                    cont_temp++;
                                }
                            }
                            if (cont_temp > 0) {
                                datos_mal += org_datos + "<br />";
                                cont_mal++;
                            } else {
                                cont_bien++;
                            }
                        }
                        document.getElementById("Txt_salarios_error").value = datos_mal;
                        var error = document.getElementById("Txt_salarios_error").value;
                        var msg_mal = document.getElementById("Cont_mal").innerHTML;
                        var msg_bien = document.getElementById("Cont_bien").innerHTML;
                        if (error.length > 6) {
                            elemento2.innerHTML = error;
                            elemento2.style.backgroundColor = '#FDEDEC';
                            elemento2.style.color = '#EA4335';
                            document.getElementById("Cont_mal").innerHTML = msg_mal.replace("---", (cont_mal - 1));
                            document.getElementById("Cont_bien").innerHTML = msg_bien.replace("---", (cont_bien - 1));
                            document.getElementById("Id_verificar").style.pointerEvents = "none";
                            document.getElementById("Id_verificar").style.color = "#EA4335";
                        } else {
                            elemento2.innerHTML = elemento.innerHTML;
                            elemento2.style.backgroundColor = '#EAFAF1';
                            elemento2.style.color = '#3AA757';
                            document.getElementById("Cont_mal").innerHTML = msg_mal.replace("---", (cont_mal - 1));
                            document.getElementById("Cont_bien").innerHTML = msg_bien.replace("---", (cont_bien - 1));
                            document.getElementById("Id_verificar").style.pointerEvents = "auto";
                            document.getElementById("Id_verificar").style.color = "#596275";
                        }
                        document.getElementById("Id_separar").style.pointerEvents = "none";
                        document.getElementById("Id_separar").style.color = "#aaa";
                        document.getElementById("Id_integridad").style.pointerEvents = "none";
                        document.getElementById("Id_integridad").style.color = "#aaa";
                    }
                </script>
                <script type="text/javascript">
                    function VerificarData() {
                        var elemento = document.getElementById('FormVerificarData');
                        elemento.submit();
                    }
                </script>
                <script type="text/javascript">
                    function Ajustar_exportar(filename, vista) {
                        //document.getElementById("ExportDetail").innerHTML = "<form action='Reportes?opc=2&mnu=12&ept=1' method='post'><i>El origen de datos seleccionado <b class='negro'>(" + vista.toUpperCase() + ")</b></i><br /><b>Nombre de archivo : </b><input type='text' id='fnm' name='fnm' required value='" + filename.toUpperCase() + "' /></br><input type='text' name='fpt' id='fpt' value='" + vista + "' /><input type='submit' value='Descargar' /></form>";
                        //document.getElementById("ExportDetail").innerHTML = "<i>El origen de datos seleccionado <b class='negro'>(" + vista.toUpperCase() + ")</b></i><br /><b>Nombre de archivo : </b><input type='text' id='fnm' required value='" + filename.toUpperCase() + "' /></br><input type='hidden' id='fpt' value='" + vista + "' /><b>Fecha inicio : </b><input type='text' id='start' name='Txt_fecha_inicio' required /></br><b>Fecha final : </b><input type='text' id='end' name='Txt_fecha_fin' required /><input onclick='DownloadData();' type='submit' value='Descargar' />";
                        document.getElementById("ExportDetail").style.display = "none";
                        document.getElementById("ExportDetail2").style.display = "block";
                        document.getElementById("TituloExport").innerHTML = vista.toUpperCase();
                        document.getElementById("fnm").value = filename.toUpperCase();
                        document.getElementById("fpt").value = vista.toLowerCase();
                        document.getElementById("path").value = vista.toLowerCase();
                        var fin = document.getElementById("start").value;
                        var ffn = document.getElementById("end").value;
                        document.getElementById("fpt").value = vista.toLowerCase() + " WHERE (Fecha between '" + fin + "' and '" + ffn + "') or Fecha = 'Fecha'";
                    }
                    function Ajustar_fechas_export() {
                        var path = document.getElementById("path").value;
                        var fin = document.getElementById("start").value;
                        var ffn = document.getElementById("end").value;
                        document.getElementById("fpt").value = path.toLowerCase() + " WHERE (Fecha between '" + fin + "' and '" + ffn + "') or Fecha = 'Fecha'";
                    }
                    function DownloadData() {
                        var filename = document.getElementById("fnm").value;
                        var path = document.getElementById("fpt").value;
                        location.href = "Reportes?opc=2&mnu=12&ept=1&fnm=" + filename + "&fpt=" + path + "";
                    }
                </script>
                <script type="text/javascript">
                    function VigenciaCarne() {
                        var vigencia = document.getElementById('end').value;
                        var cantcarne = document.getElementById('CantCarne').value;
                        for (var i = 0; i < cantcarne; i++) {
                            document.getElementById('FechaVigencia' + i).innerHTML = vigencia;
                        }
                        
                    }
                </script>
                <script src="Interfaz/Calendarios/Js_range.js"></script>
                <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
                <script src="Interfaz/Calendarios/Js_normal.js"></script>
                <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
            </div>
        </div>
    </body>
</html>

