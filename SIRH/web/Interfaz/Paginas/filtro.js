function Filtrar(tble) {
    var table = (tble === undefined || tble === null || tble === "") 
        ? document.getElementById('resultados') 
        : document.getElementById(tble);

    var filtro = document.getElementById('Txt_filtro').value.toLowerCase().trim();
    var cellsOfRow = "";
    var found = false;
    var compareWith = "";

    // Recorremos todas las filas con contenido de la tabla
    for (var i = 1; i < table.rows.length; i++) {
        cellsOfRow = table.rows[i].getElementsByTagName('td');
        found = false;

        if (filtro === "") {
            // Si el filtro está vacío, muestra todas las filas
            table.rows[i].style.display = '';
        } else {
            // Recorremos todas las celdas de la fila actual
            for (var j = 0; j < cellsOfRow.length && !found; j++) {
                compareWith = cellsOfRow[j].innerHTML.toLowerCase();
                // Buscamos el texto en el contenido de la celda
                if (compareWith.indexOf(filtro) > -1) {
                    found = true;
                }
            }
            // Mostrar/ocultar fila según si se encontró coincidencia
            table.rows[i].style.display = found ? '' : 'none';
        }
    }
}
