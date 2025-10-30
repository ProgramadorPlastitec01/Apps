document.addEventListener('DOMContentLoaded', function () {
    // Forzar dibujo de firmas iniciales
    if (typeof dibujarCoordenadas_00 === 'function') {
        dibujarCoordenadas_00();
        console.log('Firma 00 dibujada');
    } else {
        console.warn('Función dibujarCoordenadas_00 no definida');
    }
    if (typeof dibujarCoordenadas_01 === 'function') {
        dibujarCoordenadas_01();
        console.log('Firma 01 dibujada');
    } else {
        console.warn('Función dibujarCoordenadas_01 no definida');
    }

    async function convertImagesToBase64(container, baseUrl) {
        const imgs = container.querySelectorAll('img');
        for (let img of imgs) {
            let src = img.src;
            if (!src)
                continue;
            if (src.startsWith('data:') || src.startsWith('blob:')) {
                console.log(`Imagen ya en base64 o blob: ${src.substring(0, 40)}...`);
                continue;
            }
            try {
                if (!src.startsWith('http') && !src.startsWith('data:')) {
                    src = new URL(src, baseUrl).href;
                    img.src = src;
                }
                console.log(`Intentando cargar imagen: ${src}`);
                const response = await fetch(src, {
                    mode: 'cors',
                    credentials: 'include',
                    headers: {'Cache-Control': 'no-cache'}
                });
                if (!response.ok)
                    throw new Error(`Status ${response.status}`);
                const blob = await response.blob();
                const reader = new FileReader();
                await new Promise((resolve, reject) => {
                    reader.onloadend = () => {
                        img.src = reader.result;
                        console.log(`Imagen convertida a base64: ${src}`);
                        resolve();
                    };
                    reader.onerror = () => reject();
                    reader.readAsDataURL(blob);
                });
            } catch (err) {
                console.error(`No se pudo convertir la imagen a base64: ${src}`, err);
            }
        }
    }

    window.prepareMinuteToSend = async function () {
        const originalContainer = document.getElementById('dataDocument');
        if (!originalContainer) {
            console.error('No se encontró #dataDocument');
            iziToast.error({
                title: 'Error',
                message: 'No se encontró el documento a enviar.',
                position: 'topRight',
                timeout: 4000
            });
            return;
        }

        // Forzar dibujo de firmas nuevamente antes de capturar
        if (typeof dibujarCoordenadas_00 === 'function')
            dibujarCoordenadas_00();
        if (typeof dibujarCoordenadas_01 === 'function')
            dibujarCoordenadas_01();

        // SNAPSHOT de canvases originales (evita colección "live")
        const originalCanvases = Array.from(originalContainer.querySelectorAll('canvas'));
        console.log('Canvases originales encontrados:', originalCanvases.length);

        const canvasDataUrls = [];
        for (let i = 0; i < originalCanvases.length; i++) {
            const c = originalCanvases[i];
            let dataUrl = null;
            try {
                // Intento directo
                dataUrl = c.toDataURL('image/png');
                console.log(`toDataURL OK canvas[${i}] (long: ${dataUrl.length})`);
            } catch (err) {
                console.warn(`toDataURL falló para canvas[${i}]:`, err);
                // Fallback con html2canvas si está disponible
                if (typeof html2canvas === 'function') {
                    try {
                        console.log('Intentando fallback html2canvas para canvas[' + i + ']');
                        const rendered = await html2canvas(c, {useCORS: true, scale: 2});
                        dataUrl = rendered.toDataURL('image/png');
                        console.log('html2canvas fallback OK canvas[' + i + ']');
                    } catch (err2) {
                        console.error('html2canvas fallback falló para canvas[' + i + ']:', err2);
                    }
                } else {
                    console.warn('html2canvas no encontrada; no se puede fallback');
                }
            }
            canvasDataUrls.push(dataUrl); // puede ser null si falló
        }

        // Clonar el contenedor (snapshot)
        let element = originalContainer.cloneNode(true);


        // Alerta de espera
        let alerta = document.createElement('div');
        alerta.id = 'alertaEspera';
        alerta.style = `
    position:fixed;top:0;left:0;width:100%;height:100%;
    background-color:rgba(0,0,0,0.5);
    display:flex;justify-content:center;align-items:center;
    z-index:99999;backdrop-filter:blur(2px);
`;
        alerta.innerHTML = `
    <div style="background:white;padding:20px 30px;border-radius:10px;
                box-shadow:0 4px 15px rgba(0,0,0,0.3);font-size:16px;
                font-family:Segoe UI, Arial,sans-serif;">
        📤 Enviando acta, por favor espere...
    </div>
`;
        document.body.appendChild(alerta);

        // SNAPSHOT de canvases clonados (evita colección "live")
        const clonedCanvases = Array.from(element.querySelectorAll('canvas'));
        console.log('Canvases clonados encontrados:', clonedCanvases.length);

        const replaceCount = Math.min(canvasDataUrls.length, clonedCanvases.length);
        if (canvasDataUrls.length !== clonedCanvases.length) {
            console.warn(`Mismatch canvases: originales=${canvasDataUrls.length} clonados=${clonedCanvases.length}. Reemplazaré ${replaceCount}.`);
        }

        for (let i = 0; i < replaceCount; i++) {
            const dataUrl = canvasDataUrls[i];
            const clonedCanvas = clonedCanvases[i];
            if (dataUrl) {
                const img = document.createElement('img');
                img.src = dataUrl;
                // Mantener dimensiones visuales
                try {
                    img.width = clonedCanvas.width || clonedCanvas.getAttribute('width') || clonedCanvas.offsetWidth || 200;
                    img.height = clonedCanvas.height || clonedCanvas.getAttribute('height') || clonedCanvas.offsetHeight || 'auto';
                } catch (e) {
                    // ignore
                }
                img.style.maxWidth = '200px';
                img.style.height = 'auto';
                img.style.cssText = clonedCanvas.style && clonedCanvas.style.cssText ? clonedCanvas.style.cssText : img.style.cssText;
                clonedCanvas.parentNode.replaceChild(img, clonedCanvas);
                console.log(`Reemplazado canvas[${i}] por imagen.`);
            } else {
                console.warn(`No hay dataUrl para canvas[${i}] -> dejo canvas tal cual.`);
            }
        }

        // Eliminar scripts clonados
        let scripts = element.getElementsByTagName('script');
        while (scripts.length > 0) {
            scripts[0].parentNode.removeChild(scripts[0]);
        }

        // Convertir imgs externas a base64
        const baseUrl = window.location.origin;
        await convertImagesToBase64(element, baseUrl);

        // Estilos de impresión (igual que antes)
        const style = document.createElement('style');
        style.innerHTML = `
            @media print {
                body { margin: 0; padding: 0; font-family: Arial, sans-serif; }
                #dataDocument { width: 100%; max-width: 200mm; margin: 0 auto; box-sizing: border-box; }
                #dataDocument * { overflow: visible !important; page-break-inside: avoid; box-sizing: border-box; }
                table { width: 100%; border-collapse: collapse; table-layout: fixed; }
                td, th { border: 1px solid #ddd; padding: 5px; word-wrap: break-word; break-inside: avoid; }
                tr { break-inside: avoid; break-after: auto; }
                img { max-width: 100%; height: auto; display: block; page-break-inside: avoid; }
                p, div { page-break-inside: avoid; }
            }
            @media screen {
                #dataDocument { display: block; }
            }`;
        element.appendChild(style);

        // Generar y enviar PDF
        if (typeof html2pdf !== 'function' && typeof window.html2pdf === 'undefined') {
            console.error('html2pdf no está disponible en esta página.');
            document.body.removeChild(alerta);
            iziToast.error({
                title: 'Error',
                message: 'El generador PDF (html2pdf) no está disponible.',
                position: 'topRight',
                timeout: 4000
            });
            originalContainer.style.display = "none";
            return;
        }
// 🔧 DESACTIVAR scroll temporalmente para capturar todo
        const originalStyles = {
            maxHeight: originalContainer.style.maxHeight,
            overflowY: originalContainer.style.overflowY
        };

// Si la clase tiene max-height en CSS, también lo anulamos con inline-style
        element.style.maxHeight = 'none';
        element.style.overflowY = 'visible';

        console.log('Scroll desactivado temporalmente para captura completa');

        const totalWidth = Math.max(
                element.scrollWidth,
                document.documentElement.scrollWidth,
                document.body.scrollWidth
                );
        const totalHeight = Math.max(
                element.scrollHeight,
                document.documentElement.scrollHeight,
                document.body.scrollHeight
                );

        console.log('Tamaño total detectado:', totalWidth, totalHeight);

// Generar PDF con html2pdf ajustado
        html2pdf()
                .from(element)
                .set({
                    margin: [5, 5, 5, 5],
                    filename: 'Acta.pdf',
                    html2canvas: {
                        scale: 2,
                        useCORS: true,
                        allowTaint: true,
                        scrollY: 0,
                        logging: true,
                        windowWidth: totalWidth
                    },
                    jsPDF: {
                        unit: 'mm',
                        format: 'a4',
                        orientation: 'portrait',
                        compress: true
                    },
                    pagebreak: {mode: ['avoid-all', 'css', 'legacy']}
                })
                .outputPdf('blob')
                .then(function (pdfBlob) {
                    let form = document.getElementById('minuteForm');
                    if (!form) {
                        console.error('No se encontró #minuteForm');
                        document.body.removeChild(alerta);
                        return;
                    }

                    if (!form.checkValidity()) {
                        form.classList.add('was-validated');
                        document.body.removeChild(alerta);
                        iziToast.warning({
                            title: 'Formulario incompleto',
                            message: 'Por favor completa todos los campos obligatorios.',
                            position: 'bottomRight',
                            timeout: 3000
                        });
                        return;
                    }

                    let formData = new FormData(form);
                    let destinatarios = [];
                    form.querySelectorAll("input[name='destinatario[]']").forEach(input => {
                        if (input.value.trim() !== "")
                            destinatarios.push(input.value.trim());
                    });
                    formData.set("destinatario", destinatarios.join(";"));
                    formData.append('pdf', pdfBlob, 'Acta.pdf');

                    fetch('MinuteServlet', {method: 'POST', body: formData})
                            .then(r => r.text())
                            .then(res => {
                                iziToast.success({
                                    title: 'Acta enviada',
                                    message: res,
                                    position: 'topCenter',
                                    timeout: 3000
                                });
                                mostrarConvencion(5);
                            })
                            .catch(err => console.error('Error enviando PDF:', err))
                            .finally(() => {
                                if (document.getElementById('alertaEspera'))
                                    document.body.removeChild(alerta);
                            });
                })
                .catch(err => {
                    console.error('Error generando PDF:', err);
                    if (document.getElementById('alertaEspera'))
                        iziToast.error({
                            title: 'Error',
                            message: 'Hubo un problema generando el acta.',
                            position: 'topRight',
                            timeout: 4000
                        });
                    alert('Error generando el PDF. Revisa la consola.');
                });
        // 🔧 Restaurar estilos originales del contenedor
        originalContainer.style.maxHeight = originalStyles.maxHeight;
        originalContainer.style.overflowY = originalStyles.overflowY;

        console.log('Estilos originales restaurados');

    };

});
