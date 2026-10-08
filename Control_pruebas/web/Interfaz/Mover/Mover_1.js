var xInic, yInic;
var estaPulsado = false;

function ratonPulsado(evt) {
    //Obtener la posición de inicio
    xInic = evt.clientX;
    yInic = evt.clientY;
    estaPulsado = true;
    //Para Internet Explorer: Contenido no seleccionable
    document.getElementById("Mover").unselectable = true;
}

function ratonMovido(evt) {
    if(estaPulsado) {
        //Calcular la diferencia de posición
        var xActual = evt.clientX;
        var yActual = evt.clientY;
        var xInc = xActual-xInic;
        var yInc = yActual-yInic;
        xInic = xActual;
        yInic = yActual;

        //Establecer la nueva posición
        var elemento = document.getElementById("Mover");
        var position = getPosicion(elemento);
        elemento.style.top = (position[0] + yInc) + "px";
        elemento.style.left = (position[1] + xInc) + "px";
    }
}

function ratonSoltado(evt) {
    estaPulsado = false;
}

/*
             * Función para obtener la posición en la que se encuentra el
             * elemento indicado como parámetro.
             * Retorna un array con las coordenadas x e y de la posición
             */
function getPosicion(elemento) {
    var posicion = new Array(2);
    if(document.defaultView && document.defaultView.getComputedStyle) {
        posicion[0] = parseInt(document.defaultView.getComputedStyle(elemento, null).getPropertyValue("top"))
        posicion[1] = parseInt(document.defaultView.getComputedStyle(elemento, null).getPropertyValue("left"));
    } else {
        //Para Internet Explorer
        posicion[0] = parseInt(elemento.currentStyle.top);
        posicion[1] = parseInt(elemento.currentStyle.left);
    }
    return posicion;
}
