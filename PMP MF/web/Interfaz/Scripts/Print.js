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

    // Reemplazar el canvas con la imagen de la firma
    const canvas = document.getElementById('signature-canvas');
    const imgFirma = renderSignatureToImage('signature-canvas', 'coordenadas-hidden');
    if (canvas && imgFirma)
        canvas.parentNode.replaceChild(imgFirma, canvas);

    const htmlContent = objeto.innerHTML;

    // 📍 Agregar <base> para rutas relativas
    const basePath = window.location.origin + window.location.pathname.replace(/\/[^\/]*$/, '/');

    const ventana = window.open('', '_blank');
    ventana.document.open();
    ventana.document.write(`
        <html>
        <head>
            <base href="${basePath}">
            <title>Vista para imprimir</title>
            <link rel="stylesheet" href="Interfaz/StylePage/css/My_css.css">
            <link rel="stylesheet" href="Interfaz/StylePage/css/sb-admin-2.css" type="text/css" />
            <link rel="stylesheet" href="Interfaz/StylePage/css/main.css">
            <link rel="stylesheet" href="Interfaz/StylePage/css/sb-admin-2.min.css">
            <link rel="stylesheet" href="Interfaz/StylePage/FontAwesome/css/all.min.css">
            <style>
                /* 🔹 Quita borde al canvas convertido */
                .firma-img {
                    border: none !important;
                    background: transparent !important;
                }

                /* 🔹 Forzar estilos secundarios */
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

                /* 🔹 Forzar colores en impresión */
                @media print {
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

