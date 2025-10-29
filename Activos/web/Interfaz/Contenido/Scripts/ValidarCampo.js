function ValidarSolicitudRegistradas() {
    var ElementoRegistrado = document.getElementById("ElementoRegistrados").value;
    var ElementValidar = document.getElementById("elemento").value;
    
    // Verificar si no hay datos
    if (ElementoRegistrado.trim() === "" || ElementValidar.trim() === "") {
        return; // No hay datos, salir de la función
    }

    var Arg_elementR = ElementoRegistrado.split("][").join("///").replace("]", "").replace("[", "").split("///");
    
    // Verificar si no hay elementos en Arg_elementR
    if (Arg_elementR.length === 0) {
        return; // No hay datos, salir de la función
    }

    for (var i = 0; i < Arg_elementR.length; i++) {
        var Arg_vali = Arg_elementR[i].split("///");
        var regexPattern = new RegExp('.*' + escapeRegExp(ElementValidar) + '.*', 'i');
        
        if (regexPattern.test(Arg_vali[0])) {
            document.getElementById("Ventana5").style.display = 'block';
            document.getElementById("Txt_filtroVal").value = ElementValidar;
            FiltrarValidacion();
            break; // Si se encuentra un elemento que coincide, salir del bucle
        }
    }
}

function escapeRegExp(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); // $& significa toda la cadena coincidente
}
