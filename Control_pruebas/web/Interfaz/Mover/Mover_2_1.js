       var el = document.getElementById("Mover2");
            if (el.addEventListener){
                el.addEventListener("mousedown", ratonPulsado2, false);
                el.addEventListener("mouseup", ratonSoltado2, false);
                document.addEventListener("mousemove", ratonMovido2, false);
            } else { //Para IE
                el.attachEvent('onmousedown', ratonPulsado2);
                el.attachEvent('onmouseup', ratonSoltado2);
                document.attachEvent('onmousemove', ratonMovido2);
            }