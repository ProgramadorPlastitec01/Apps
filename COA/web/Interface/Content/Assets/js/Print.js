/* =====================================================
 ✍️ FIRMA → CANVAS A IMAGEN (ROBUSTO)
 ===================================================== */
function renderSignatureToImage(canvasId = 'signature-canvas', inputId = 'coordenadas-hidden') {
    const canvas = document.getElementById(canvasId);
    const input = document.getElementById(inputId);

    if (!canvas || !input || !input.value.trim())
        return null;

    // 🔹 Limpiar string corrupto
    let raw = input.value
            .replace(/&quot;/g, '"')
            .replace(/&#39;/g, "'")
            .replace(/}\s*{/g, '},{')
            .trim();

    if (!raw.startsWith('['))
        raw = '[' + raw;
    if (!raw.endsWith(']'))
        raw = raw + ']';

    let coordenadas;
    try {
        coordenadas = JSON.parse(raw);
    } catch (err) {
        console.error("❌ Error parseando firma:", err);
        return null;
    }

    if (!Array.isArray(coordenadas))
        coordenadas = [coordenadas];
    if (!coordenadas.length)
        return null;

    // 🔹 Canvas temporal
    const temp = document.createElement('canvas');
    temp.width = canvas.width;
    temp.height = canvas.height;
    const ctx = temp.getContext('2d');

    // 🔹 Calcular límites reales
    const xs = coordenadas.flatMap(c => [c.lx, c.mx]);
    const ys = coordenadas.flatMap(c => [c.ly, c.my]);

    const minX = Math.min(...xs);
    const maxX = Math.max(...xs);
    const minY = Math.min(...ys);
    const maxY = Math.max(...ys);

    const originalWidth = maxX - minX || 1;
    const originalHeight = maxY - minY || 1;

    const scale = Math.min(
            temp.width / originalWidth,
            temp.height / originalHeight
            ) * 0.9;

    const offsetX = (temp.width - originalWidth * scale) / 2 - minX * scale;
    const offsetY = (temp.height - originalHeight * scale) / 2 - minY * scale;

    // 🔹 Dibujar firma
    ctx.strokeStyle = '#000';
    ctx.lineWidth = 1.6;
    ctx.lineCap = 'round';

    coordenadas.forEach(c => {
        ctx.beginPath();
        ctx.moveTo(c.lx * scale + offsetX, c.ly * scale + offsetY);
        ctx.lineTo(c.mx * scale + offsetX, c.my * scale + offsetY);
        ctx.stroke();
    });

    // 🔹 Convertir a imagen
    const img = document.createElement('img');
    img.src = temp.toDataURL('image/png');
    img.className = 'firma-img';
    img.style.maxWidth = '100%';
    img.style.display = 'block';
    return img;
}


/* =====================================================
 🖨️ IMPRESIÓN LIMPIA (OPCIONAL)
 ===================================================== */
function PrintHtml() {
    const contenido = document.getElementById('HtmlContent');
    if (!contenido)
        return alert('No se encontró el contenido.');

    // 🔹 Reemplazar firma
    const canvas = document.getElementById('signature-canvas');
    const imgFirma = renderSignatureToImage();
    if (canvas && imgFirma)
        canvas.replaceWith(imgFirma);

    const basePath = window.location.origin + window.location.pathname.replace(/\/[^\/]*$/, '/');
    const ventana = window.open('', '_blank');

    ventana.document.write(`
        <html>
        <head>
            <base href="${basePath}">
            <title>Imprimir</title>

            <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">

            <style>
                @page { size: Letter portrait; margin: 12mm; }

                html, body {
                    margin: 0;
                    padding: 0;
                    background: #fff;
                    color: #000;
                    font-size: 12px;
                }

                * {
                    box-shadow: none !important;
                    text-shadow: none !important;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                td, th {
                    padding: 2px 4px !important;
                    line-height: 1.15 !important;
                }

                tr, td, th {
                    page-break-inside: avoid !important;
                }

                img {
                    max-width: 100%;
                    height: auto;
                }

                #TelefonoValue.pending {
                    background-color: #e9ecef !important;
                    border: none !important;
                }

                * {
                    -webkit-print-color-adjust: exact !important;
                    print-color-adjust: exact !important;
                }
            </style>
        </head>
        <body>
            ${contenido.innerHTML}
        </body>
        </html>
    `);

    ventana.document.close();
    ventana.onload = () => {
        ventana.print();
        ventana.close();
    };
}


/* =====================================================
 📐 ANCHO DE CAPTURA PARA LA HOJA CARTA
 ===================================================== */
// Ancho mínimo (px) al que se puede angostar el certificado: por debajo las
// columnas de las tablas empiezan a partir palabras.
const PDF_ANCHO_MINIMO = 760;

// Deja `el` con el ancho más angosto (letra más grande en el PDF) con el que
// todavía cabe en una sola hoja: alto/ancho <= proporcionMax. Si ni siquiera
// con el ancho actual cabe, no lo toca (se sigue reduciendo como antes).
function ajustarAnchoParaCarta(el, proporcionMax) {
    const anchoOriginal = el.style.width;
    const anchoActual = Math.floor(el.getBoundingClientRect().width);
    const cabe = (w) => {
        el.style.width = w + 'px';
        return el.scrollWidth <= w + 1 && el.offsetHeight / w <= proporcionMax;
    };

    if (anchoActual <= PDF_ANCHO_MINIMO || !cabe(anchoActual)) {
        el.style.width = anchoOriginal;
        return;
    }
    if (cabe(PDF_ANCHO_MINIMO)) {
        return;
    }
    let lo = PDF_ANCHO_MINIMO; // no cabe
    let hi = anchoActual;      // cabe
    while (hi - lo > 8) {
        const mid = Math.floor((lo + hi) / 2);
        if (cabe(mid)) {
            hi = mid;
        } else {
            lo = mid;
        }
    }
    el.style.width = hi + 'px';
}


/* =====================================================
 📄 PDF CARTA PERFECTO (RECOMENDADO)
 ===================================================== */
async function descargarPDFCarta() {
    if (!window.jspdf || !window.html2canvas) {
        alert("Error: Librerías PDF no cargaron.");
        return;
    }

    const {jsPDF} = window.jspdf;
    const contenido = document.getElementById('Imprimir');

    if (!contenido)
        return alert('No se encontró el contenido.');

    // 🔹 Reemplazar firma canvas → imagen
    const canvasFirma = document.getElementById('signature-canvas');
    const imgFirma = renderSignatureToImage();
    if (canvasFirma && imgFirma)
        canvasFirma.replaceWith(imgFirma);

    // 📄 Carta real (pt)
    const pageWidth = 612;
    const pageHeight = 792;
    const margin = 5;
    const usableWidth = pageWidth - margin * 2;
    const usableHeight = pageHeight - margin * 2;

    // 🔹 Modo limpio PDF
    contenido.classList.add('pdf-mode');

    // 🔹 Letra más grande sin salirse de la hoja: la captura se reduce al ancho
    // de la carta, así que entre más ancho esté el certificado en pantalla más
    // pequeña sale la letra. Se angosta temporalmente al ancho mínimo con el que
    // el contenido todavía cabe en UNA hoja (alto/ancho <= proporción útil de
    // la carta) y se restaura al terminar.
    const anchoOriginal = contenido.style.width;
    ajustarAnchoParaCarta(contenido, usableHeight / usableWidth);

    let canvas;
    try {
        canvas = await html2canvas(contenido, {
            scale: 3,
            useCORS: true,
            backgroundColor: '#FFFFFF',
            removeContainer: true,
            imageTimeout: 0,
            logging: false,
            onclone: (doc) => {
                const root = doc.getElementById('Imprimir');
                if (root)
                    root.classList.add('pdf-mode');

                // 🔥 FIX VISUAL ROWSPAN
                doc.querySelectorAll('td[rowspan], th[rowspan]').forEach(cell => {
                    cell.style.position = 'relative';
                    cell.style.zIndex = '1';
                    cell.style.backgroundClip = 'padding-box';
                });

                // 🔥 Forzar que la tabla se recalcule completa
                doc.querySelectorAll('table').forEach(table => {
                    table.style.borderCollapse = 'collapse';
                    table.style.borderSpacing = '0';
                    table.style.tableLayout = 'fixed';

                    // Truco: reflow real
                    table.style.display = 'none';
                    table.offsetHeight;
                    table.style.display = 'table';
                });
            }
        });
    } finally {
        contenido.classList.remove('pdf-mode');
        contenido.style.width = anchoOriginal;
    }

    const imgData = canvas.toDataURL('image/jpeg', 1.0);

    const pdf = new jsPDF({
        orientation: 'portrait',
        unit: 'pt',
        format: 'letter'
    });

    const ratio = Math.min(
            usableWidth / canvas.width,
            usableHeight / canvas.height
            );

    const newWidth = canvas.width * ratio;
    const newHeight = canvas.height * ratio;

    const x = (pageWidth - newWidth) / 2;
    const y = margin;


    pdf.addImage(imgData, 'JPEG', x, y, newWidth, newHeight);

    const fecha = new Date().toISOString().slice(0, 10);
    pdf.save(`Certificado_Calidad_${fecha}.pdf`);
}
