<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mantenimiento en Progreso</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
            text-align: center;
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
            font-size: 40px;
        }
        .contador {
            font-size: 2rem;
            font-weight: bold;
            color: #4a860d;
        }
        img {
            width: 350px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1>Sistema en Mantenimiento</h1>
    <h3>Estamos realizando mejoras, volveremos en:</h3>
    <div>
        <div class="contador" id="contador">10:00</div>
    </div>
    <img src="Argo/Interfaz/images/Imagen_SF.png" alt="Mantenimiento en progreso">
     <script>
        function iniciarContador(duracion) {
            let tiempoRestante = localStorage.getItem("tiempoMantenimiento");
            let tiempo = tiempoRestante ? parseInt(tiempoRestante) : duracion;
            let minutos, segundos;
            
            let intervalo = setInterval(() => {
                minutos = Math.floor(tiempo / 60);
                segundos = tiempo % 60;
                segundos = segundos < 10 ? "0" + segundos : segundos;
                document.getElementById("contador").textContent = minutos + ":" + segundos;
                localStorage.setItem("tiempoMantenimiento", tiempo);
                
                if (tiempo-- <= 0) {
                    clearInterval(intervalo);
                    document.getElementById("contador").textContent = "¡Listo!";
                    localStorage.removeItem("tiempoMantenimiento");
                }
            }, 1000);
        }
        iniciarContador(600);
    </script>
</body>
</html>
