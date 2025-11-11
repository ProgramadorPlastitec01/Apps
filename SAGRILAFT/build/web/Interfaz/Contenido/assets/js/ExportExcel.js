document.getElementById('exportar-btn').addEventListener('click', function () {
    var tabla = document.getElementById('mi-tabla');

    tabla.style.border = '1px solid black';
    var celdas = tabla.querySelectorAll('td, th');
    celdas.forEach(function (celda) {
        celda.style.border = '1px solid black';
    });

    var wb = XLSX.utils.table_to_book(tabla);

    var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'binary'});

    var blob = new Blob([s2ab(wbout)], {type: 'application/octet-stream'});
    var a = document.createElement('a');
    a.href = window.URL.createObjectURL(blob);
    a.download = 'tabla.xlsx';
    document.body.appendChild(a);
    a.click();

    document.body.removeChild(a);
    tabla.style.border = '';
    celdas.forEach(function (celda) {
        celda.style.border = '';
    });
});

function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i = 0; i != s.length; ++i)
        view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
}
