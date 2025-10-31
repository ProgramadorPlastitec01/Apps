
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/visual"  prefix="Visual" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">

    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
            <Visual:Visual/>
            <Alert:Alert/>
        </div>
        <script>
            document.querySelectorAll('.editable').forEach(el => {
                el.addEventListener('blur', () => {
                    // Si el usuario borra todo el contenido, lo reestablece con ****
                    if (el.innerText.trim() === '') {
                        el.innerText = '****';
                    }
                });
                // Previene eliminar completamente el span
                el.addEventListener('keydown', (e) => {
                    if ((e.key === 'Delete' || e.key === 'Backspace') && el.innerText.trim() === '') {
                        e.preventDefault();
                    }
                });
            });
        </script>
        <script>
            function saveHtml() {
                var form = document.getElementById('FormGenerate');
                if (!form) {
                    alert("Formulario no encontrado");
                    return;
                }

                var htmlContainer = document.getElementById('HtmlContent');
                // 🔹 Sincronizar el estado actual de los inputs con sus atributos HTML
                if (htmlContainer) {
                    htmlContainer.querySelectorAll('input').forEach(input => {
                        if (input.type === 'radio' || input.type === 'checkbox') {
                            if (input.checked) {
                                input.setAttribute('checked', 'checked');
                            } else {
                                input.removeAttribute('checked');
                            }
                        } else {
                            input.setAttribute('value', input.value);
                        }
                    });
                }

                // Obtener el contenido HTML actualizado
                var contentHtml = htmlContainer ? htmlContainer.innerHTML : "";
                var encodedHtml = encodeURIComponent(contentHtml);
                // Capturar elementos con ID
                var consText = document.getElementById('consValue')?.textContent.trim() || '';
                        var clientText = document.getElementById('clientValue')?.textContent.trim() || '';
                        var idRegisterText = document.getElementById('IdRegister')?.textContent.trim() || '';
                        var Code = document.getElementById('codeValue')?.textContent.trim() || '';
                // Función helper para agregar inputs ocultos
                const addHidden = (name, value) => {
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = name;
                    input.value = value;
                    form.appendChild(input);
                };
                // Agregar datos al formulario
                addHidden('Html', encodedHtml);
                if (consText)
                    addHidden('ConsValue', consText);
                if (clientText)
                    addHidden('clientValue', clientText);
                if (idRegisterText)
                    addHidden('IdRegisterValue', idRegisterText);
                if (Code)
                    addHidden('codeValue', Code);
                form.submit();
            }

        </script>
        <script>
            function dibujarCoordenadas() {
                const canvas = document.getElementById('signature-canvas');
                const ctx = canvas.getContext('2d');
                const input = document.getElementById('coordenadas-hidden');

                if (!input || !input.value)
                    return;

                // 1) Leer y decodificar entidades html
                let raw = input.value;
                raw = raw.replace(/&quot;/g, '"').replace(/&#39;/g, "'").trim();

                // Función auxiliar para intentar parsear y devolver resultado o null
                function tryParse(s) {
                    try {
                        return JSON.parse(s);
                    } catch (e) {
                        console.warn("Intento de parse falló:", e.message);
                        return null;
                    }
                }

                // 2) Intento directo
                let coordenadas = tryParse(raw);
                if (!coordenadas) {
                    // 3) Si empieza con '{' (objeto) y no con '[' (array), envolver en []
                    if (raw.startsWith('{') && !raw.startsWith('[')) {
                        let candidate = '[' + raw + ']';
                        coordenadas = tryParse(candidate);
                        if (coordenadas)
                            raw = candidate;
                    }
                }

                if (!coordenadas) {
                    // 4) Si hay '}{' pegados, reemplazar '}{' por '},{' y envolver en []
                    if (raw.indexOf('}{') !== -1) {
                        let fixed = raw.replace(/}\s*{/g, '},{');
                        if (!fixed.startsWith('['))
                            fixed = '[' + fixed + ']';
                        coordenadas = tryParse(fixed);
                        if (coordenadas)
                            raw = fixed;
                    }
                }

                if (!coordenadas) {
                    // 5) Último recurso
                    let cleaned = raw.replace(/[\u0000-\u001f\u007f-\u009f]/g, '');
                    cleaned = cleaned.replace(/,\s*]/g, ']');
                    if (!cleaned.startsWith('[') && cleaned.startsWith('{'))
                        cleaned = '[' + cleaned + ']';
                    cleaned = cleaned.replace(/}\s*,\s*}/g, '},{');
                    coordenadas = tryParse(cleaned);
                    if (coordenadas)
                        raw = cleaned;
                }

                if (!coordenadas) {
                    console.error("No pude parsear las coordenadas. Valor final probado:", raw.slice(0, 500));
                    return;
                }

                if (!Array.isArray(coordenadas)) {
                    coordenadas = [coordenadas];
                }

                ctx.clearRect(0, 0, canvas.width, canvas.height);

                // ⚙️ ---- ESCALADO AUTOMÁTICO Y CENTRADO ---- ⚙️
                const maxX = Math.max(...coordenadas.map(c => Math.max(c.lx, c.mx)));
                const maxY = Math.max(...coordenadas.map(c => Math.max(c.ly, c.my)));
                const minX = Math.min(...coordenadas.map(c => Math.min(c.lx, c.mx)));
                const minY = Math.min(...coordenadas.map(c => Math.min(c.ly, c.my)));

                const originalWidth = maxX - minX;
                const originalHeight = maxY - minY;

                const scaleX = canvas.width / originalWidth;
                const scaleY = canvas.height / originalHeight;
                const scale = Math.min(scaleX, scaleY); // mantiene proporción

                // Calcular offset para centrar
                const offsetX = (canvas.width - originalWidth * scale) / 2 - minX * scale;
                const offsetY = (canvas.height - originalHeight * scale) / 2 - minY * scale;

                console.log(`Escala aplicada: ${scale.toFixed(2)} | Offset: (${offsetX.toFixed(1)}, ${offsetY.toFixed(1)})`);

                // ---- DIBUJAR CON ESCALA Y CENTRADO ----
                ctx.strokeStyle = 'black';
                ctx.lineWidth = 1.5;

                coordenadas.forEach(coord => {
                    if (coord && typeof coord.lx === 'number' && typeof coord.ly === 'number'
                            && typeof coord.mx === 'number' && typeof coord.my === 'number') {

                        ctx.beginPath();
                        ctx.moveTo(coord.lx * scale + offsetX, coord.ly * scale + offsetY);
                        ctx.lineTo(coord.mx * scale + offsetX, coord.my * scale + offsetY);
                        ctx.stroke();
                    }
                });

                console.log("Dibujo completado. Puntos dibujados:", coordenadas.length);
            }

            document.addEventListener('DOMContentLoaded', dibujarCoordenadas);
        </script>


        <script src="Interface/Content/Assets/js/Print.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
    </body>
</html>
