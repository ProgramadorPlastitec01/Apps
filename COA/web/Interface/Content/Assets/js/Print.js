function renderSignatureToImage(canvasId = 'signature-canvas', inputId = 'coordenadas-hidden') {
    const canvas = document.getElementById(canvasId);
    const input = document.getElementById(inputId);
    if (!canvas || !input || !input.value.trim())
        return null;

    // Parsear coordenadas
    // --- Parsear coordenadas de forma segura ---
    let raw = input.value.replace(/&quot;/g, '"').replace(/&#39;/g, "'").trim();

// Intentar limpiar cadenas concatenadas o con basura
    raw = raw.replace(/}\s*{/g, '},{');         // une objetos sueltos
    if (!raw.startsWith('['))
        raw = '[' + raw;  // asegura corchetes
    if (!raw.endsWith(']'))
        raw = raw + ']';    // asegura cierre

    let coordenadas = [];
    try {
        coordenadas = JSON.parse(raw);
    } catch (err) {
        console.error("❌ Error al parsear coordenadas:", err);
        console.warn("🧾 Valor que causó error:", raw);
        return null; // salir para no romper impresión
    }
    if (!Array.isArray(coordenadas))
        coordenadas = [coordenadas];

    // Dibujar en canvas temporal
    const temp = document.createElement('canvas');
    temp.width = canvas.width;
    temp.height = canvas.height;
    const ctx = temp.getContext('2d');

    const maxX = Math.max(...coordenadas.map(c => Math.max(c.lx, c.mx)));
    const maxY = Math.max(...coordenadas.map(c => Math.max(c.ly, c.my)));
    const minX = Math.min(...coordenadas.map(c => Math.min(c.lx, c.mx)));
    const minY = Math.min(...coordenadas.map(c => Math.min(c.ly, c.my)));
    const originalWidth = maxX - minX;
    const originalHeight = maxY - minY;
    const scale = Math.min(temp.width / originalWidth, temp.height / originalHeight);
    const offsetX = (temp.width - originalWidth * scale) / 2 - minX * scale;
    const offsetY = (temp.height - originalHeight * scale) / 2 - minY * scale;

    ctx.strokeStyle = 'black';
    ctx.lineWidth = 1.5;
    coordenadas.forEach(c => {
        ctx.beginPath();
        ctx.moveTo(c.lx * scale + offsetX, c.ly * scale + offsetY);
        ctx.lineTo(c.mx * scale + offsetX, c.my * scale + offsetY);
        ctx.stroke();
    });

    // Crear imagen Base64
    const img = document.createElement('img');
    img.src = temp.toDataURL('image/png');
    img.alt = 'Firma digital';
    img.className = 'firma-img';
    img.style.maxWidth = '100%';
    img.style.border = '1px solid #ccc';
    img.style.borderRadius = '6px';
    img.style.marginTop = '5px';
    return img;
}

function PrintHtml() {
    const objeto = document.getElementById('HtmlContent');
    if (!objeto) {
        alert('No se encontró el contenido para imprimir.');
        return;
    }

    // Reemplazar el canvas por imagen
    const canvas = document.getElementById('signature-canvas');
    const imgFirma = renderSignatureToImage('signature-canvas', 'coordenadas-hidden');
    if (canvas && imgFirma)
        canvas.parentNode.replaceChild(imgFirma, canvas);

    const htmlContent = objeto.innerHTML;

    // Base para rutas
    const basePath = window.location.origin + window.location.pathname.replace(/\/[^\/]*$/, '/');

    const ventana = window.open('', '_blank');
    ventana.document.open();
    ventana.document.write(`
        <html>
        <head>
            <base href="${basePath}">
            <title>Vista para imprimir</title>

            <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" href="Interface/Content/Assets/css/style.min.css" type="text/css">
            <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
            <link rel="stylesheet" href="Interface/Content/Assets/css/style.css">
            <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">

            <style>
                /* 🔹 RESET GLOBAL (reduce márgenes y paddings al mínimo) */
                html, body {
                    margin: 0 !important;
                    padding: 0 !important;
                    width: 100%;
                    font-size: 13px;
                }

                body.container {
                    padding: 5px !important;  /* Puedes bajarlo a 0 si quieres CERO márgenes */
                }

                * {
                    box-sizing: border-box;
                }

                /* 🔹 Eliminar paddings de Bootstrap */
                .container, .container-fluid, .row, [class*="col-"] {
                    padding-left: 2px !important;
                    padding-right: 2px !important;
                }
                /* 🔹 Comprimir todos los td/th */
                table td, 
                table th {
                    padding: 1px 2px !important;
                    line-height: 1.05 !important;
                }

                /* 🔹 Quitar padding y márgenes de las celdas con textos largos o multilínea */
                table td p,
                table th p {
                    margin: 0 !important;
                    padding: 0 !important;
                }

                /* 🔹 Comprimir títulos multilínea */
                table td.fw-bold,
                table th.fw-bold {
                    padding-top: 0 !important;
                    padding-bottom: 0 !important;
                    line-height: 1.05 !important;
                }

                /* 🔹 Comprimir celdas que tienen texto bilingüe (ESP / ENG) */
                table td.text-start,
                table td.text-end,
                table td.text-center {
                    padding-top: 1px !important;
                    padding-bottom: 1px !important;
                }

                /* 🔹 Quitar padding del contenedor de secciones (1.1 / 2.1 etc) */
                td[colspan] {
                    padding-top: 1px !important;
                    padding-bottom: 1px !important;
                }

                /* 🔹 Evitar saltos de línea innecesarios */
                tr, td, th {
                    page-break-inside: avoid !important;
                }
            }

                /* 🔹 Imagen de firma sin borde */
                .firma-img {
                    border: none !important;
                    background: transparent !important;
                }

                /* 🔹 Ajustar colores */
                body .bg-secondary {
                    background-color: #6c757d !important;
                    color: #fff !important;
                }
                body .table-secondary,
                body .table-secondary > th,
                body .table-secondary > td,
                body .table-secondary thead th {
                    background-color: #d6d8db !important;
                    color: #000 !important;
                }

                /* 🔹 Ajustes estrictos para impresión */
                @media print {
                    @page {
                        margin: 5mm;  /* ⭐ Ajusta márgenes del papel */
                    }

                    body {
                        margin: 0 !important;
                        padding: 0 !important;
                    }

                    .bg-secondary,
                    .table-secondary,
                    .table-secondary > th,
                    .table-secondary > td,
                    .table-secondary thead th {
                        -webkit-print-color-adjust: exact !important;
                        print-color-adjust: exact !important;
                    }
                }
            </style>
        </head>
        <body class="container">
            ${htmlContent}
        </body>
        </html>
    `);
    ventana.document.close();

    ventana.onload = () => {
        ventana.print();
        ventana.close();
    };
}


