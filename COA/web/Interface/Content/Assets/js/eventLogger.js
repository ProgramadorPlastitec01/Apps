const eventosSinInfo = [];

function registrarEvento(tipo, orden, producto, lote, mensaje) {
    const fecha = new Date().toLocaleString();
    eventosSinInfo.push({tipo, orden, producto, lote, mensaje, fecha});

    const alerta = document.getElementById('alerta');
    if (alerta)
        alerta.style.display = 'block';
}

function mostrarDetalles() {
    const tbody = document.querySelector('#tablaEventos tbody');
    tbody.innerHTML = '';

    eventosSinInfo.forEach(ev => {
        const fila = `
                <tr>
                    <td>${ev.tipo}</td>
                    <td>${ev.orden}</td>
                    <td>${ev.producto}</td>
                    <td>${ev.lote}</td>
                    <td>${ev.mensaje}</td>
                    <td>${ev.fecha}</td>
                </tr>
            `;
        tbody.insertAdjacentHTML('beforeend', fila);
    });

    document.getElementById('modalEventos').style.display = 'flex';
}

function cerrarModal() {
    document.getElementById('modalEventos').style.display = 'none';
}