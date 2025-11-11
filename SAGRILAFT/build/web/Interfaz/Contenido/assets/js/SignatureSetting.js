const signatureCanvas = document.getElementById('signature-canvas');
const textCanvas = document.getElementById('text-canvas');
const imageCanvas = document.getElementById('image-canvas');
const contextSignature = signatureCanvas.getContext('2d');
const contextText = textCanvas.getContext('2d');
const contextImage = imageCanvas.getContext('2d');
const nameInput = document.getElementById('name-input');
const fontStyleSelect = document.getElementById('font-style-select');
const hiddenInput = document.getElementById('coordenadas-hidden');


// Configurar el estilo predeterminado del texto
contextText.font = 'bold 30px Arial';

// Eventos de ratón para dibujar la firma
signatureCanvas.addEventListener('mousedown', startDrawing);
signatureCanvas.addEventListener('mouseup', stopDrawing);
signatureCanvas.addEventListener('mousemove', draw);

let isDrawing = false;
let coordinates = []; // Array para almacenar las coordenadas de la firma

function startDrawing(event) {
    isDrawing = true;
    draw(event);
}

function stopDrawing() {
    isDrawing = false;
    contextSignature.beginPath();
    guardarCoordenadas(); // Guardar las coordenadas al dejar de dibujar
}

function draw(event) {
    if (!isDrawing)
        return;
    const rect = signatureCanvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
//    const x = event.offsetX - signatureCanvas.offsetLeft;
//    const y = event.offsetY - signatureCanvas.offsetTop;
//    const x = event.offsetX;
//    const y = event.offsetY;

    contextSignature.lineWidth = 2;
    contextSignature.lineCap = 'round';
    contextSignature.strokeStyle = 'black';
    contextSignature.lineTo(x, y);
    contextSignature.stroke();
    contextSignature.beginPath();
    contextSignature.moveTo(x, y);

    // Guardar las coordenadas mientras se dibuja
    coordinates.push({x: x, y: y});
}

// Limpiar el canvas de la firma
function limpiarCanvas(canvasId) {
    const canvas = document.getElementById(canvasId);
    const context = canvas.getContext('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
    coordinates = []; // Limpiar las coordenadas
    if (canvasId === 'text-canvas') {
        contextText.fillText('', 10, 30); // Limpiar el texto
        nameInput.value = ''; // Limpiar el input del nombre
    } else {
        guardarCoordenadas(); // Limpiar también las coordenadas guardadas
    }
}

// Función para guardar las coordenadas de la firma
function guardarCoordenadas() {
    // Convertir las coordenadas en una cadena JSON
    const coordinatesJSON = JSON.stringify(coordinates);

    // Guardar las coordenadas en el input escondido
    hiddenInput.value = coordinatesJSON;
}

// Evento de entrada de texto para mostrar el nombre
nameInput.addEventListener('input', () => {
    const name = nameInput.value;
    contextText.clearRect(0, 0, textCanvas.width, textCanvas.height);
    contextText.font = `bold 60px ${fontStyleSelect.value}`; // Aumentar el tamaño y hacer negrita
    contextText.fillText(name, 10, 50);
});

// Evento de cambio de estilo de fuente
fontStyleSelect.addEventListener('change', () => {
    const name = nameInput.value;
    contextText.clearRect(0, 0, textCanvas.width, textCanvas.height);
    contextText.font = `bold 60px ${fontStyleSelect.options[fontStyleSelect.selectedIndex].text}`; // Aumentar el tamaño y hacer negrita
    contextText.fillText(name, 10, 50);
});

// Función para cargar la imagen de la firma
function cargarImagen(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const img = new Image();
            img.onload = function () {
                contextImage.clearRect(0, 0, imageCanvas.width, imageCanvas.height); // Limpiar el canvas antes de dibujar la imagen
                contextImage.drawImage(img, 0, 0, imageCanvas.width, imageCanvas.height);
            };
            img.src = e.target.result;
            const nombreArchivo = file.name;
//            document.getElementById("idSignUpload").value = nombreArchivo;
        };
        reader.readAsDataURL(file);
    }
}

nameInput.dispatchEvent(new Event('input'));