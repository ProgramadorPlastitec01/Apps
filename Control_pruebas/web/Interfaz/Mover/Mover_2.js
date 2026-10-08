       var el = document.getElementById("Mover");
            if (el.addEventListener){
                el.addEventListener("mousedown", ratonPulsado, false);
                el.addEventListener("mouseup", ratonSoltado, false);
                document.addEventListener("mousemove", ratonMovido, false);
            } else { //Para IE
                el.attachEvent('onmousedown', ratonPulsado);
                el.attachEvent('onmouseup', ratonSoltado);
                document.attachEvent('onmousemove', ratonMovido);
            }