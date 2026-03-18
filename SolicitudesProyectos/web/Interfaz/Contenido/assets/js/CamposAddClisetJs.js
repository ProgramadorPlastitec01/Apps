var idInicial = parseInt(document.getElementById("idInicial").value, 10);
var idFinal = parseInt(document.getElementById("idFinal").value, 10);
var contador = idInicial === 0 ? 1 : (idFinal + 1);
var cont1 = 0;

function CampoAddClisset() {
    var divC = document.getElementById("container");
    var content = `<div class='FormControl row' id='FormControl${contador}'>
            <div class='col-3'>
                <b class='clssB'>I.D</b>
                <input type='text' class='form-control btnEstric text-center' readonly='false' name='id${contador}' id='id${contador}' value='${contador}' placeholder='Ejecutor' required='' autocomplete='off' data-toggle='tooltip' data-placement='top' title='I.D'>
                <div class='invalid-feedback invalid_data_rll'>
                    <i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un ID!
                </div>
            </div>
            <div class='col-6'>
                <b class='clssB'>VALOR</b>
                <input type='text' class='form-control numeric-input' name='txt_valor${contador}' id='txt_valor${contador}' value='' placeholder='0.0' required='' autocomplete='off' data-toggle='tooltip' data-placement='top' title='Valor'>
                <div class='invalid-feedback invalid_data_rll'>
                    <i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!
                </div>
            </div>
            <div class='col-2 mt-3'>
                <button type='button' class='btn btn-warning remove-btn' onclick='removeField("FormControl${contador}")'>
                    <i class='fas fa-minus'></i>
                </button>
            </div>
            </div>`;
    var div = document.createElement('div');
    div.innerHTML = content;
    divC.insertAdjacentHTML('afterbegin', div.innerHTML);
    contador++;
    document.getElementById("contadorFinal").value = contador - 1;
    updateRemoveButtons();  // Actualizar botones de eliminar después de añadir un nuevo campo


    // Attach input event listener to the new numeric input field
    var newInput = document.getElementById(`txt_valor${contador - 1}`);
    newInput.addEventListener('input', validateNumericInput);
}

if (idInicial === 0) {
    document.getElementById("contadorIncial").value = (idInicial + 1);
} else {
    document.getElementById("contadorIncial").value = (idFinal + 1);
}

function removeField(id) {
    var element = document.getElementById(id);
    if (element) {
        element.remove();
        contador--;
        document.getElementById("contadorFinal").value = contador - 1;
        updateRemoveButtons();  // Actualizar botones de eliminar después de eliminar un campo
    }
}

function updateRemoveButtons() {
    var removeButtons = document.querySelectorAll('.remove-btn');
    removeButtons.forEach(function (button, index) {
        button.disabled = (index !== removeButtons.length - 1);  // Solo habilitar el botón del último div
    });
}

// Inicializar estado de los botones de eliminar
updateRemoveButtons();

function validateNumericInput(event) {
    var input = event.target;
    var value = input.value;

    // Replace anything that isn't a number or decimal point
    var sanitizedValue = value.replace(/[^0-9.]/g, '');

    // If the sanitized value doesn't match the original value, update the input value
    if (sanitizedValue !== value) {
        input.value = sanitizedValue;
    }

    // Ensure only one decimal point is present
    var parts = sanitizedValue.split('.');
    if (parts.length > 2) {
        input.value = parts[0] + '.' + parts.slice(1).join('');
    }
}

// Attach input event listeners to existing numeric input fields
document.querySelectorAll('.numeric-input').forEach(function (input) {
    input.addEventListener('input', validateNumericInput);
});
