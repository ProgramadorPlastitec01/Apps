 const body = document.querySelector('body');

        const crearCorazon = () => {
            var corazon = document.createElement('div');
            corazon.className = "corazon";
            corazon.innerHTML = "⛄";
            var x = innerWidth * Math.random();
            if (x > innerWidth - 50) {
                x = innerWidth - 50;
            }
            var delay = Math.random() * 5;

            corazon.style.left = x + 'px';
            corazon.style.animationDelay = delay + 's';

            body.appendChild(corazon);

            setTimeout(() => {
                corazon.remove();
            }, 10000); // Cambia la duración si lo deseas (10 segundos)
        };

        // Generar algunos corazones iniciales
        for (let i = 0; i < 10; i++) {
            crearCorazon();
        }
 const crearCorazon2 = () => {
        var corazon2 = document.createElement('div');
        corazon2.className = "navidad";
        corazon2.innerHTML = "❄";
        var x = innerWidth * Math.random();
        if (x > innerWidth - 50) {
            x = innerWidth - 50;
        }
        var delay = Math.random() * 5;

        corazon2.style.left = x + 'px';
        corazon2.style.animationDelay = delay + 's';

        body.appendChild(corazon2);

        setTimeout(() => {
            corazon2.remove();
        }, 10000); // Cambia la duración si lo deseas (10 segundos)
    };

    // Generar algunos corazones iniciales
    for (let i = 0; i < 10; i++) {
        crearCorazon2();
    }

        setInterval(crearCorazon, 1000); // Cambia el intervalo si lo deseas
        setInterval(crearCorazon2, 1000); // Cambia el intervalo si lo deseas
        
        