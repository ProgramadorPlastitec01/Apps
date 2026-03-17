var idInicial = parseInt(document.getElementById("idInicialBCD").value, 10);
var idFinal = parseInt(document.getElementById("idFinalBCD").value, 10);
var contador = (idInicial === 0) ? 1 : (idInicial > 0 ? (idInicial + 1) : (idFinal + 1));

function CamposClissetB() {
    if (contador > idFinal) {
        iziToast.warning({
            title: 'Alerta',
            message: 'No se permite ingresar más.',
            position: 'topRight'
        });
        return;
    }

    var divC = document.getElementById("containerB");
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
                    <button type='button' class='btn btn-warning remove-btn' onclick='removeFieldB("FormControl${contador}")'>
                        <i class='fas fa-minus'></i>
                    </button>
                </div>
            </div>`;

    var div = document.createElement('div');
    div.innerHTML = content;
    divC.insertAdjacentHTML('afterbegin', div.innerHTML);
    contador++;
    document.getElementById("contadorFinalB").value = contador - 1;
    updateRemoveButtons();

    // Attach input event listener to the new numeric input field
    var newInput = document.getElementById(`txt_valor${contador - 1}`);
    newInput.addEventListener('input', validateNumericInput);
}

function removeFieldB(id) {
    var element = document.getElementById(id);
    if (element) {
        element.remove();
        contador--;
        document.getElementById("contadorFinalB").value = contador - 1;
        updateRemoveButtons();
    }
}

function updateRemoveButtons() {
    var removeButtons = document.querySelectorAll('.remove-btn');
    removeButtons.forEach(function(button, index) {
        if (index === 0) { // The most recently added element
            button.disabled = false;
        } else {
            button.disabled = true;
        }
    });
}

// Initial call to disable all remove buttons except the last one
updateRemoveButtons();

document.addEventListener('keydown', function (event) {
    if (event.key === '+') {
        CamposClissetB();  // Asegúrate de que la función se llama correctamente
    }
});

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
document.querySelectorAll('.numeric-input').forEach(function(input) {
    input.addEventListener('input', validateNumericInput);
});
