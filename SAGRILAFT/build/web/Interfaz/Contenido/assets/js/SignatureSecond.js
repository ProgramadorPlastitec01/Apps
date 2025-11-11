const signatureCanvasSeg = document.getElementById('signature-canvastw');
const textCanvastw = document.getElementById('text-canvastw');
const imageCanvastw = document.getElementById('image-canvastw');
const contextSignatureSeg = signatureCanvasSeg.getContext('2d');
const contextTexttw = textCanvastw.getContext('2d');
const contextImagetw = imageCanvastw.getContext('2d');
const nameInputSeg = document.getElementById('name-inputtw');
const fontStyleSelectSeg = document.getElementById('font-style-selecttw');
const hiddenInputSeg = document.getElementById('coordenadas-hiddentw');

contextTexttw.font = 'bold 30px Arial';

signatureCanvasSeg.addEventListener('mousedown', startDrawing);
signatureCanvasSeg.addEventListener('mouseup', stopDrawing);
signatureCanvasSeg.addEventListener('mousemove', draw);

let isDrawing = false;
let coordinates = []; 

function startDrawing(event) {
    isDrawing = true;
    draw(event);
}

function stopDrawing() {
    isDrawing = false;
    contextSignatureSeg.beginPath();
    guardarCoordenadas(); 
}

function draw(event) {
    if (!isDrawing)
        return;
    const rect = signatureCanvasSeg.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    contextSignatureSeg.lineWidth = 2;
    contextSignatureSeg.lineCap = 'round';
    contextSignatureSeg.strokeStyle = 'black';
    contextSignatureSeg.lineTo(x, y);
    contextSignatureSeg.stroke();
    contextSignatureSeg.beginPath();
    contextSignatureSeg.moveTo(x, y);

    coordinates.push({x: x, y: y});
}

function limpiarCanvas(canvasId) {
    const canvas = document.getElementById(canvasId);
    const context = canvas.getContext('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
    coordinates = []; // Limpiar las coordenadas
    if (canvasId === 'text-canvas') {
        contextTexttw.fillText('', 10, 30); // Limpiar el texto
        nameInputSeg.value = ''; // Limpiar el input del nombre
    } else {
        guardarCoordenadas(); // Limpiar también las coordenadas guardadas
    }
}

// Función para guardar las coordenadas de la firma
function guardarCoordenadas() {
    // Convertir las coordenadas en una cadena JSON
    const coordinatesJSON = JSON.stringify(coordinates);

    // Guardar las coordenadas en el input escondido
    hiddenInputSeg.value = coordinatesJSON;
}

// Evento de entrada de texto para mostrar el nombre
nameInputSeg.addEventListener('input', () => {
    const name = nameInputSeg.value;
    contextTexttw.clearRect(0, 0, textCanvastw.width, textCanvastw.height);
    contextTexttw.font = `bold 60px ${fontStyleSelectSeg.value}`; // Aumentar el tamaño y hacer negrita
    contextTexttw.fillText(name, 10, 50);
});

// Evento de cambio de estilo de fuente
fontStyleSelectSeg.addEventListener('change', () => {
    const name = nameInputSeg.value;
    contextTexttw.clearRect(0, 0, textCanvastw.width, textCanvastw.height);
    contextTexttw.font = `bold 60px ${fontStyleSelectSeg.options[fontStyleSelectSeg.selectedIndex].text}`; // Aumentar el tamaño y hacer negrita
    contextTexttw.fillText(name, 10, 50);
});

// Función para cargar la imagen de la firma
function cargarImagen(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const img = new Image();
            img.onload = function () {
                contextImagetw.clearRect(0, 0, imageCanvastw.width, imageCanvastw.height); // Limpiar el canvas antes de dibujar la imagen
                contextImagetw.drawImage(img, 0, 0, imageCanvastw.width, imageCanvastw.height);
            };
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

 nameInputSeg.dispatchEvent(new Event('input'));
