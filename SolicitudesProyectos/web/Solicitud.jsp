<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/tlds/solicitudes.tld" prefix="Solicitud" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SP | Solicitud</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
    </head>
    <body >
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Solicitud:Solicitud/>
                </div>
                <script type="text/javascript" language="javascript">
                    function Agregar() {
                        var texto_datoss = document.getElementById("piezas-id");
                        var textD = texto_datoss.options[texto_datoss.selectedIndex].text;
                        var textDS = textD.split("/");
                        var pieza = document.getElementById("piezax-id");
                        for (var j = 0; j < textDS.length;
                                j++
                                ) {
                            var obj_lote = new Object(textDS);
                            if (j == 0) {
                                var textp = document.getElementById("piezax-id").value;
                                if (textp == "") {
                                    pieza.value = obj_lote[0];
                                } else {
                                    pieza.value = textp + "-" + obj_lote[0];
                                }
                            }
                        }
                    }
                </script>
                <script type="text/javascript" language="javascript">
                    document.addEventListener("DOMContentLoaded", function () {
                        // Llama a la función obtenerSolicitud una vez que el DOM esté completamente cargado
                        obtenerSolicitud();
                    });

                    function obtenerSolicitud() {
                        try {
                            var horasistema = new Date();
                            var hora = horasistema.getHours();
                            var minutos = horasistema.getMinutes();
                            var año = horasistema.getFullYear() - 2000;
                            var mes = horasistema.getMonth() + 1;
                            var dia = horasistema.getDate();

                            if (mes <= 9) {
                                mes = "0" + mes;
                            }
                            if (dia <= 9) {
                                dia = "0" + dia;
                            }
                            if (hora <= 9) {
                                hora = "0" + hora;
                            }
                            if (minutos <= 9) {
                                minutos = "0" + minutos;
                            }

                            var solicitud = año + "" + mes + "" + dia + "" + hora + "" + minutos + "";
                            // Asigna el valor de la solicitud a los elementos deseados
                            // Validacion defensiva: cada id solo existe en el modo de
                            // vista que lo renderiza; no todos estaran presentes a la vez.
                            var el1 = document.getElementById("solicitud-id");
                            var el2 = document.getElementById("solicitud-id2");
                            var el3 = document.getElementById("solicitud-id3");
                            var el4 = document.getElementById("solicitud-id4");
                            if (el1) { el1.value = solicitud; }
                            if (el2) { el2.value = solicitud; }
                            if (el3) { el3.value = solicitud; }
                            if (el4) { el4.value = solicitud; }
                        } catch (error) {
                            console.error("Error al obtener la solicitud:", error.message);
                        }
                    }
                </script>
                <script>
                    // ---- Top de planos del indicador -------------------------------
                    // El Tag emite DATOS_PLANOS y URL_PLANO; si no estamos en la
                    // vista de contadores no existen y no hay nada que dibujar.

                    // Rampa del rojo corporativo de mayor a menor. Las agrupaciones
                    // ('Varios' con id 0 y 'Sin plano asignado' con id -1) van
                    // siempre en el gris #ADB5BD: no son planos y no navegan.
                    var RAMPA_PLANOS = ['#B72E27', '#C2453E', '#CD5B55', '#D8726C', '#E08983',
                        '#E79F9A', '#EDB2AE', '#F1C3C0', '#F5D2D0', '#F8DFDD'];
                    var GRIS_AGRUPACION = '#ADB5BD';

                    function colorPlano(dato, indice) {
                        return (dato.idPlano > 0) ? RAMPA_PLANOS[indice % RAMPA_PLANOS.length] : GRIS_AGRUPACION;
                    }

                    // Un solo destino para el clic en la porcion, en la leyenda y en
                    // la fila de la tabla. Conserva fechas, estado y tipo porque
                    // URL_PLANO ya viene armada con el filtro vigente.
                    function irAPlano(id) {
                        if (!id || id <= 0) {
                            return;   // 'Varios' y 'Sin plano asignado' son agregaciones
                        }
                        location.href = URL_PLANO + id;
                    }

                    function initChartPlanos() {
                        var lienzo = document.getElementById('chartPlanos');
                        if (!lienzo || typeof DATOS_PLANOS === 'undefined' || !DATOS_PLANOS.length) {
                            return;
                        }
                        var etiquetas = [], valores = [], colores = [];
                        for (var i = 0; i < DATOS_PLANOS.length; i++) {
                            etiquetas.push(DATOS_PLANOS[i].nombre);
                            valores.push(DATOS_PLANOS[i].horas);
                            colores.push(colorPlano(DATOS_PLANOS[i], i));
                        }
                        var grafico = new Chart(lienzo.getContext('2d'), {
                            type: 'doughnut',
                            data: {
                                labels: etiquetas,
                                datasets: [{data: valores, backgroundColor: colores, borderWidth: 1}]
                            },
                            options: {
                                cutoutPercentage: 62,
                                // La leyenda es HTML propia del Tag: lleva No. de
                                // parte y descripcion, que Chart.js no sabe pintar.
                                legend: {display: false},
                                // duration 0 para que toBase64Image() del PDF no
                                // capture el grafico a medio dibujar.
                                animation: {duration: 0},
                                tooltips: {
                                    callbacks: {
                                        label: function (item, data) {
                                            var d = DATOS_PLANOS[item.index];
                                            return d.nombre + ': ' + d.horas + ' h (' + d.porcentaje + '%)';
                                        }
                                    }
                                }
                            }
                        });
                        lienzo.onclick = function (e) {
                            var el = grafico.getElementAtEvent(e)[0];
                            if (!el) {
                                return;
                            }
                            irAPlano(DATOS_PLANOS[el._index].idPlano);
                        };
                    }

                    document.addEventListener("DOMContentLoaded", initChartPlanos);
                </script>
                <script>
                    function execute_form() {
                        document.getElementById("form_btns").submit();
                    }
                    function Imprimir() {
                        var contenedor = document.getElementById("Imprimir").innerHTML;
                        var frame = document.createElement("iframe");
                        frame.name = "frame1";
                        frame.style.position = "absolute";
                        frame.style.top = "-1000000px";
                        document.body.appendChild(frame);
                        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
                        frameDoc.document.open();
                        frameDoc.document.write('<html><head><title>Imprimir</title>');
                        frameDoc.document.write('<link href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />');
                        frameDoc.document.write('<link href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />');
                        frameDoc.document.write('<link href="Interfaz/Contenido/assets/css/style.css" rel="stylesheet" type="text/css" />');
                        frameDoc.document.write('<link href="Interfaz/Contenido/assets/css/style.min.css" rel="stylesheet" type="text/css" />');
                        frameDoc.document.write('<link href="Interfaz/Contenido/assets/css/main.css" rel="stylesheet" type="text/css" />');
                        frameDoc.document.write('</head><body>');
                        frameDoc.document.write(contenedor);
                        frameDoc.document.write('</body></html>');
                        frameDoc.document.close();
                        setTimeout(function () {
                            window.frames["frame1"].focus();
                            window.frames["frame1"].print();
                            document.body.removeChild(frame);
                        }, 300);
                        return false;
                    }
                </script>
            </div>
            <Alertas:Alertas/>
            <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
            <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
            <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>

            <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
            <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        </div>
    </body>
</html>
