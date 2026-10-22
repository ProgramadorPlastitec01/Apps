<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/tlds/reporte.tld" prefix="Reporte" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>SP | Informe de Gestion</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/reporte.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/reporte-print.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
    </head>
    <body >
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                <link rel="stylesheet" href="Interfaz/Contenido/assets/css/reporte-print.css">
                <div class="main-content" style="min-height: 694px;">
                    <Reporte:Reporte/>
                </div>

                <script>
                    /**
                     * Muestra el selector de mes solo cuando se compara contra
                     * "Otro mes". No se oculta con CSS puro porque el input
                     * tiene que seguir enviandose cuando SI esta elegido.
                     */
                    /**
                     * Alterna los campos del filtro segun el modo elegido.
                     * Los dos bloques se emiten siempre desde el Tag; aqui solo
                     * se oculta el que no corresponde, para que el formulario
                     * siga siendo valido sin JS.
                     */
                    function mostrarModo() {
                        var sel = document.getElementById('slcModo');
                        if (!sel) {
                            return;
                        }
                        var esRango = (sel.value === '2');
                        document.querySelectorAll('.bloqueModoMes').forEach(function (e) {
                            e.style.display = esRango ? 'none' : '';
                        });
                        document.querySelectorAll('.bloqueModoRango').forEach(function (e) {
                            e.style.display = esRango ? '' : 'none';
                        });
                        actualizarComparativo(esRango);
                        mostrarMesLibre();
                    }

                    // Reconstruye las opciones de "Comparar contra" segun el modo,
                    // preservando la seleccion actual si sigue siendo valida.
                    function actualizarComparativo(esRango) {
                        var sel = document.getElementById('slcComparativo');
                        if (!sel) {
                            return;
                        }
                        var actual = sel.value;
                        var ops = esRango
                            ? [['4', 'Sin comparativo (solo este periodo)'], ['1', 'Periodo inmediatamente anterior (mismo numero de dias)'], ['2', 'Mismo periodo del anio anterior']]
                            : [['4', 'Sin comparativo (solo este mes)'], ['1', 'Mes inmediatamente anterior'], ['2', 'Mismo mes del anio anterior'], ['3', 'Otro mes especifico…']];
                        sel.innerHTML = '';
                        ops.forEach(function (o) {
                            var opt = document.createElement('option');
                            opt.value = o[0];
                            opt.textContent = o[1];
                            sel.appendChild(opt);
                        });
                        var existe = ops.some(function (o) {
                            return o[0] === actual;
                        });
                        sel.value = existe ? actual : '1';
                    }

                    function mostrarMesLibre() {
                        var sel = document.getElementById('slcComparativo');
                        var mes = document.getElementById('txtPeriodoComp');
                        var modo = document.getElementById('slcModo');
                        if (!sel || !mes) {
                            return;
                        }
                        // El mes libre solo aplica al modo Mes con la opcion 3.
                        var esRango = (modo && modo.value === '2');
                        mes.style.display = (!esRango && sel.value === '3') ? '' : 'none';
                    }

                    // ---- Graficos --------------------------------------------------
                    // Una sola funcion para los tres: el Tag emite tres arreglos con
                    // la misma forma {etiqueta, valor, color} y aqui solo cambia el
                    // tipo de Chart.
                    var GRAFICOS = [];

                    function crearGrafico(idCanvas, tipo, datos) {
                        var lienzo = document.getElementById(idCanvas);
                        if (!lienzo || !datos || !datos.length) {
                            return null;
                        }
                        var etiquetas = [], valores = [], colores = [];
                        for (var i = 0; i < datos.length; i++) {
                            etiquetas.push(datos[i].etiqueta);
                            valores.push(datos[i].valor);
                            colores.push(datos[i].color);
                        }
                        var esDona = (tipo === 'doughnut');
                        var opciones = {
                            responsive: true,
                            maintainAspectRatio: false,
                            // duration 0: si la animacion corre al llamar
                            // toBase64Image() se captura el grafico a medio dibujar.
                            animation: {duration: 0},
                            legend: {display: false}
                        };
                        if (esDona) {
                            opciones.cutoutPercentage = 62;
                            // A una dona NO se le pasa 'scales', ni siquiera vacio:
                            // Chart.js 2.7.1 intenta concatenar options.scales.xAxes,
                            // que no existe en un grafico sin ejes, y lanza
                            // "Cannot read properties of undefined (reading 'concat')".
                            // Esa excepcion era la que dejaba la dona y el grafico
                            // siguiente sin dibujar.
                        } else if (tipo === 'horizontalBar') {
                            // En barras horizontales el eje de valores es el X.
                            opciones.scales = {xAxes: [{ticks: {beginAtZero: true}}]};
                        } else {
                            opciones.scales = {yAxes: [{ticks: {beginAtZero: true}}]};
                        }
                        var grafico = new Chart(lienzo.getContext('2d'), {
                            type: tipo,
                            data: {
                                labels: etiquetas,
                                datasets: [{data: valores, backgroundColor: colores, borderWidth: 1}]
                            },
                            options: opciones
                        });
                        GRAFICOS.push(grafico);
                        return grafico;
                    }

                    // Sincroniza el rango de fechas del lado del cliente: Hasta no
                    // puede ser anterior a Desde, y Desde no puede pasar de Hasta ni
                    // de hoy. El backend igual reacota; esto es para el datepicker.
                    function sincronizarRango() {
                        var d = document.getElementsByName('txt_desde')[0];
                        var h = document.getElementsByName('txt_hasta')[0];
                        if (!d || !h) {
                            return;
                        }
                        var hoy = new Date().toISOString().slice(0, 10);
                        d.addEventListener('change', function () {
                            if (d.value) {
                                h.min = d.value;
                            }
                        });
                        h.addEventListener('change', function () {
                            d.max = (h.value && h.value < hoy) ? h.value : hoy;
                        });
                        if (d.value) {
                            h.min = d.value;
                        }
                        d.max = (h.value && h.value < hoy) ? h.value : hoy;
                    }

                    document.addEventListener("DOMContentLoaded", function () {
                        mostrarModo();
                        sincronizarRango();
                        // Cada grafico en su propio try: los tres se crean en el
                        // mismo listener y una excepcion en el segundo dejaba al
                        // tercero sin dibujar. Aislarlos no tapa el error, lo
                        // deja en consola con el nombre del grafico que fallo.
                        [['chartMes', 'bar', DATOS_MES],
                         ['chartArea', 'doughnut', DATOS_AREA],
                         ['chartPlanos', 'horizontalBar', DATOS_TOP]].forEach(function (g) {
                            try {
                                crearGrafico(g[0], g[1], g[2]);
                            } catch (e) {
                                console.error('No se pudo dibujar ' + g[0] + ':', e);
                            }
                        });
                    });

                    // ---- Impresion a PDF -------------------------------------------
                    // Se usa la impresion NATIVA del navegador, no html2canvas.
                    // Rasterizar el DOM con este layout (grid + tablas con overflow)
                    // generaba un lienzo enorme casi vacio que jsPDF paginaba en 147
                    // hojas, casi todas en blanco. El motor de impresion del navegador
                    // entiende el flujo del documento y respeta reporte-print.css.

                    /**
                     * Cambia cada <canvas> por su imagen y devuelve como deshacerlo.
                     * Un <canvas> se imprime en blanco en varios navegadores: se
                     * copia el elemento pero no los pixeles. Se sustituye ANTES de
                     * imprimir y se restaura DESPUES.
                     */
                    function graficosAImagen() {
                        var cambios = [];
                        GRAFICOS.forEach(function (ch) {
                            var img = new Image();
                            img.src = ch.toBase64Image();
                            img.className = 'graficoImpreso';
                            var lienzo = ch.canvas;
                            if (lienzo.parentNode) {
                                var monitors = lienzo.parentNode.querySelectorAll('.chartjs-size-monitor');
                                monitors.forEach(function (m) {
                                    m.style.display = 'none';
                                });
                            }
                            lienzo.parentNode.replaceChild(img, lienzo);
                            cambios.push({img: img, canvas: lienzo});
                        });
                        return cambios;
                    }

                    function restaurarGraficos(cambios) {
                        cambios.forEach(function (c) {
                            if (c.img.parentNode) {
                                var contenedor = c.img.parentNode;
                                contenedor.replaceChild(c.canvas, c.img);
                                var monitors = contenedor.querySelectorAll('.chartjs-size-monitor');
                                monitors.forEach(function (m) {
                                    m.style.display = '';
                                });
                            }
                        });
                    }

                    /**
                     * Nombre sugerido del PDF. El navegador lo toma del title
                     * del documento al guardar como PDF, asi que se cambia
                     * mientras dura la impresion y se restaura despues.
                     */
                    function nombreInforme() {
                        var d = document.getElementsByName('txt_desde')[0];
                        var h = document.getElementsByName('txt_hasta')[0];
                        var modo = document.getElementById('slcModo');
                        if (modo && modo.value === '2' && d && h && d.value && h.value) {
                            return 'Informe_Gestion_Taller_' + d.value + '_' + h.value;
                        }
                        var p = document.getElementsByName('txt_periodo')[0];
                        return 'Informe_Gestion_Taller_' + (p ? p.value.replace('-', '') : '');
                    }

                    function DescargarPDF() {
                        var tituloOriginal = document.title;
                        document.title = nombreInforme();
                        var cambios = graficosAImagen();
                        document.body.classList.add('imprimiendo');

                        var restaurado = false;
                        var restaurar = function () {
                            if (restaurado) {
                                return;
                            }
                            restaurado = true;
                            document.body.classList.remove('imprimiendo');
                            restaurarGraficos(cambios);
                            document.title = tituloOriginal;
                            window.removeEventListener('afterprint', restaurar);
                        };
                        window.addEventListener('afterprint', restaurar);

                        // Respaldo al reenfocar la ventana por si afterprint no se emite
                        var alVolver = function () {
                            window.removeEventListener('focus', alVolver);
                            setTimeout(restaurar, 500);
                        };
                        window.addEventListener('focus', alVolver);

                        setTimeout(function () {
                            window.print();
                        }, 250);
                        return false;
                    }
                </script>
            </div>
            <Alertas:Alertas/>
            <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
            <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
            <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        </div>
    </body>
</html>
