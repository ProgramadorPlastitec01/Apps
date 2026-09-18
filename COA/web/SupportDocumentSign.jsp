<%@page import="Controller.CertificateFileJpaController"%>
<%@page import="Controller.CertificateFileRow"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    String Permission = "";
    try {
        Permission = sesion.getAttribute("Permisos").toString();
    } catch (Exception e) {
    }
    String Firma = "";
    try {
        Firma = sesion.getAttribute("Firma").toString();
    } catch (Exception e) {
    }

    String cliente = request.getParameter("cliente");
    String anio = request.getParameter("anio");
    String orden = request.getParameter("orden");
    String lote = request.getParameter("lote");
    String id = request.getParameter("id");

    CertificateFileRow fila = null;
    try {
        if (id != null) {
            fila = new CertificateFileJpaController().consultFileById(Long.parseLong(id));
        }
    } catch (Exception e) {
    }
    String archivo = fila != null ? fila.getName() : null;

    boolean autorizado = Permission.contains("[39]") && Firma != null && !Firma.trim().isEmpty() && fila != null;
    String rutaArchivo = "FileDownloadProxyServlet?id=" + id + "&modo=inline";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Firmar documento de soporte</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <style>
            body { background:#eef1f5; }
            #viewerToolbar {
                position: sticky; top: 0; z-index: 30;
                background:#0b0025; color:#fff; padding:10px 16px;
                display:flex; align-items:center; justify-content:space-between;
                flex-wrap: wrap; gap:8px;
            }
            #pagesContainer {
                position: relative;
                overflow-y: auto;
                max-height: calc(100vh - 64px);
                padding: 20px 0;
            }
            .page-wrapper {
                position: relative;
                margin: 0 auto 20px auto;
                width: fit-content;
                box-shadow: 0 2px 10px rgba(0,0,0,0.25);
                background:#fff;
            }
            .page-wrapper canvas { display:block; }
            .page-number-badge {
                position:absolute; top:-10px; left:-10px;
                background:#0b0025; color:#fff; border-radius:50%;
                width:26px; height:26px; font-size:12px;
                display:flex; align-items:center; justify-content:center;
            }
            .signature-overlay {
                position:absolute;
                cursor: move;
                border: 2px dashed #6777ef;
                touch-action: none;
                z-index: 10;
            }
            .signature-overlay img { width:100%; height:100%; display:block; pointer-events:none; }
            .resize-handle {
                position:absolute; right:-8px; bottom:-8px;
                width:16px; height:16px; background:#6777ef; border-radius:50%;
                cursor: nwse-resize;
            }
            .remove-handle {
                position:absolute; top:-10px; right:-10px;
                width:20px; height:20px; background:#dc3545; color:#fff; border-radius:50%;
                display:flex; align-items:center; justify-content:center;
                font-size:12px; cursor:pointer; line-height:1;
            }
        </style>
    </head>
    <body>
        <% if (!autorizado) { %>
        <div class="alert alert-danger m-4">
            No tiene permiso para firmar este documento, o no tiene una firma registrada en su perfil.
        </div>
        <% } else { %>

        <div id="viewerToolbar">
            <div>
                <strong>Firmar documento de soporte:</strong> <%= archivo%>
            </div>
            <div>
                <button class="btn btn-sm btn-light mr-2" onclick="agregarFirma()">
                    <i class="fas fa-plus"></i> Agregar firma
                </button>
                <button class="btn btn-sm btn-success ml-2" onclick="confirmarFirma()">
                    <i class="fas fa-signature"></i> Confirmar y guardar documento firmado
                </button>
                <button class="btn btn-sm btn-secondary ml-2" onclick="window.close()">Cancelar</button>
            </div>
        </div>

        <div id="pagesContainer"></div>

        <form id="signForm" method="post" action="SupportDocumentSignServlet" style="display:none;">
            <input type="hidden" name="cliente" value="<%= cliente%>">
            <input type="hidden" name="anio" value="<%= anio%>">
            <input type="hidden" name="orden" value="<%= orden%>">
            <input type="hidden" name="lote" value="<%= lote%>">
            <input type="hidden" name="id" value="<%= id%>">
            <input type="hidden" name="firmas" id="Txt_firmas">
        </form>

        <script src="Interface/Content/Assets/js/sweetalert2.js"></script>
        <script src="Interface/Content/Assets/modules/pdfjs/pdf.min.js"></script>
        <script>
            pdfjsLib.GlobalWorkerOptions.workerSrc = "Interface/Content/Assets/modules/pdfjs/pdf.worker.min.js";

            var rutaArchivo = "<%= rutaArchivo%>";
            var rutaFirma = "Interface/Uploads/Signature/<%= Firma%>";
            var pagesContainer = document.getElementById("pagesContainer");
            var pageWrappers = []; // {el, numero}
            var firmasColocadas = []; // elementos .signature-overlay colocados sobre las páginas
            var contadorCascada = 0;

            // Renderiza TODAS las páginas del documento, una debajo de otra, en un
            // contenedor con scroll: el usuario ve el documento completo y puede
            // colocar tantas firmas como necesite en cualquier página, en vez de
            // firmar de a una página/una firma por vez.
            pdfjsLib.getDocument(rutaArchivo).promise.then(function (doc) {
                var renders = [];
                for (var i = 1; i <= doc.numPages; i++) {
                    renders.push(renderizarPagina(doc, i));
                }
                Promise.all(renders).then(function () {
                    agregarFirma(); // primera firma lista para ubicar, sobre la página 1
                });
            });

            function renderizarPagina(doc, numeroPagina) {
                return doc.getPage(numeroPagina).then(function (page) {
                    var viewport = page.getViewport({scale: 1.4});

                    var wrapper = document.createElement("div");
                    wrapper.className = "page-wrapper";
                    wrapper.style.width = viewport.width + "px";
                    wrapper.style.height = viewport.height + "px";

                    var badge = document.createElement("div");
                    badge.className = "page-number-badge";
                    badge.innerText = numeroPagina;
                    wrapper.appendChild(badge);

                    var canvas = document.createElement("canvas");
                    canvas.width = viewport.width;
                    canvas.height = viewport.height;
                    wrapper.appendChild(canvas);

                    pagesContainer.appendChild(wrapper);
                    pageWrappers.push({el: wrapper, numero: numeroPagina});

                    var renderContext = {canvasContext: canvas.getContext("2d"), viewport: viewport};
                    return page.render(renderContext).promise;
                });
            }

            function agregarFirma() {
                if (pageWrappers.length === 0) {
                    return;
                }
                var overlay = document.createElement("div");
                overlay.className = "signature-overlay";
                overlay.style.width = "160px";
                overlay.style.height = "70px";

                var img = document.createElement("img");
                img.src = rutaFirma;
                overlay.appendChild(img);

                var resizeHandle = document.createElement("div");
                resizeHandle.className = "resize-handle";
                overlay.appendChild(resizeHandle);

                var removeHandle = document.createElement("div");
                removeHandle.className = "remove-handle";
                removeHandle.innerHTML = "&times;";
                removeHandle.title = "Quitar esta firma";
                overlay.appendChild(removeHandle);

                // Ubica la nueva firma sobre la primera página, en cascada para que
                // varias firmas agregadas seguidas no queden exactamente superpuestas.
                var primeraPagina = pageWrappers[0].el;
                var offset = 20 + (contadorCascada % 6) * 25;
                contadorCascada++;
                overlay.style.left = (primeraPagina.offsetLeft + offset) + "px";
                overlay.style.top = (primeraPagina.offsetTop + offset) + "px";

                pagesContainer.appendChild(overlay);
                firmasColocadas.push(overlay);

                habilitarArrastreYRedimension(overlay);

                removeHandle.addEventListener("click", function (e) {
                    e.stopPropagation();
                    pagesContainer.removeChild(overlay);
                    firmasColocadas = firmasColocadas.filter(function (f) {
                        return f !== overlay;
                    });
                });
            }

            // Convierte una posición de mouse (viewport) a coordenadas del
            // contenido de #pagesContainer, incluyendo lo que esté desplazado por
            // el scroll. Usar siempre este mismo sistema de coordenadas (tanto al
            // iniciar el arrastre como en cada movimiento) es lo que evita el
            // "salto": antes se mezclaba e.clientX/Y (relativo a la ventana) con
            // overlay.offsetLeft/Top (relativo al contenido con scroll) sin restar
            // la posición del contenedor, así que en cuanto había scroll (por
            // ejemplo al bajar a otra página) el primer movimiento saltaba una
            // distancia igual a ese scroll.
            function posicionEnContenido(e) {
                var rect = pagesContainer.getBoundingClientRect();
                return {
                    x: e.clientX - rect.left + pagesContainer.scrollLeft,
                    y: e.clientY - rect.top + pagesContainer.scrollTop
                };
            }

            function habilitarArrastreYRedimension(overlay) {
                var arrastrando = false, offsetX = 0, offsetY = 0;
                overlay.addEventListener("mousedown", function (e) {
                    if (e.target.classList.contains("resize-handle") || e.target.classList.contains("remove-handle")) {
                        return;
                    }
                    arrastrando = true;
                    var pos = posicionEnContenido(e);
                    offsetX = pos.x - overlay.offsetLeft;
                    offsetY = pos.y - overlay.offsetTop;
                    e.preventDefault();
                });
                document.addEventListener("mousemove", function (e) {
                    if (!arrastrando) {
                        return;
                    }
                    var pos = posicionEnContenido(e);
                    var nuevoX = pos.x - offsetX;
                    var nuevoY = pos.y - offsetY;
                    nuevoX = Math.max(0, Math.min(nuevoX, pagesContainer.scrollWidth - overlay.offsetWidth));
                    nuevoY = Math.max(0, Math.min(nuevoY, pagesContainer.scrollHeight - overlay.offsetHeight));
                    overlay.style.left = nuevoX + "px";
                    overlay.style.top = nuevoY + "px";
                });
                document.addEventListener("mouseup", function () {
                    arrastrando = false;
                });

                var redimensionando = false, startX = 0, startWidth = 0;
                overlay.querySelector(".resize-handle").addEventListener("mousedown", function (e) {
                    redimensionando = true;
                    startX = e.clientX;
                    startWidth = overlay.offsetWidth;
                    e.preventDefault();
                    e.stopPropagation();
                });
                document.addEventListener("mousemove", function (e) {
                    if (!redimensionando) {
                        return;
                    }
                    var nuevoAncho = Math.max(60, startWidth + (e.clientX - startX));
                    var proporcion = overlay.offsetHeight / overlay.offsetWidth;
                    overlay.style.width = nuevoAncho + "px";
                    overlay.style.height = (nuevoAncho * proporcion) + "px";
                });
                document.addEventListener("mouseup", function () {
                    redimensionando = false;
                });
            }

            // Determina sobre qué página quedó cada firma colocada, según su
            // posición vertical, y calcula su posición como fracción del ancho y
            // alto de ESA página (independiente del zoom usado en el visor).
            function calcularFirmas() {
                var resultado = [];
                firmasColocadas.forEach(function (overlay) {
                    var centroY = overlay.offsetTop + overlay.offsetHeight / 2;
                    var pagina = pageWrappers[0];
                    for (var i = 0; i < pageWrappers.length; i++) {
                        var pw = pageWrappers[i];
                        if (centroY >= pw.el.offsetTop && centroY <= pw.el.offsetTop + pw.el.offsetHeight) {
                            pagina = pw;
                            break;
                        }
                        pagina = pw; // si quedó debajo de la última página, se asigna a esa
                    }

                    var xFrac = (overlay.offsetLeft - pagina.el.offsetLeft) / pagina.el.offsetWidth;
                    var yFrac = (overlay.offsetTop - pagina.el.offsetTop) / pagina.el.offsetHeight;
                    var widthFrac = overlay.offsetWidth / pagina.el.offsetWidth;

                    resultado.push(pagina.numero + ":" + xFrac.toFixed(6) + ":" + yFrac.toFixed(6) + ":" + widthFrac.toFixed(6));
                });
                return resultado.join(";");
            }

            function confirmarFirma() {
                if (firmasColocadas.length === 0) {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Falta ubicar la firma',
                        text: 'Agregue al menos una firma y ubíquela sobre el documento antes de confirmar.',
                        confirmButtonText: 'Entendido'
                    });
                    return;
                }

                document.getElementById("Txt_firmas").value = calcularFirmas();

                Swal.fire({
                    icon: 'question',
                    title: '¿Confirmar firma?',
                    text: 'Se firmará el documento en las ' + firmasColocadas.length + ' posición(es) seleccionada(s). '
                            + 'Esta acción generará el documento firmado y reemplazará el pendiente.',
                    showCancelButton: true,
                    confirmButtonText: 'Sí, confirmar',
                    cancelButtonText: 'Cancelar',
                    confirmButtonColor: '#6777ef',
                    reverseButtons: true
                }).then(function (result) {
                    if (result.isConfirmed) {
                        document.getElementById("signForm").submit();
                    }
                });
            }
        </script>
        <% } %>
    </body>
</html>
