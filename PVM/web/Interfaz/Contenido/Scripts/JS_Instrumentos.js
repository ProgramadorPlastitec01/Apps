function tipoV(tipo) {
    var adjunto = document.getElementById("adjunto");
    var justificacion = document.getElementById("justificacion");
    var adjuntona = document.getElementById("adjuntona");
    var input = document.getElementById("uploadFile");
    if (tipo === "1" || tipo === "3") {
        if (adjunto.style.display === "none") {
            adjunto.style.display = "block";
            input.style.display = "block";
        }
        if (tipo === "3") {
            justificacion.style.display = "block";
        } else {
            justificacion.style.display = "none";
        }
    } else {
        adjunto.style.display = "none";
        input.style.display = "none";
        justificacion.style.display = "none";
        adjuntona.innerHTML = "<input type='hidden' value='' name='archivo' />";
    }
}
function platilla() {
//    var htmleditor = document.getElementById("htmleditor-id").value;
//    document.getElementById("plantilla-id").value = htmleditor;
    var htmleditor = document.getElementById("htmleditor-id").value;
    document.getElementById("plantilla-id").value = htmleditor;
    document.formP.submit();
}
function marcar(check, id) {
    var element = document.getElementById(id);
    if (check.checked) {
        element.setAttribute("checked", "");
    } else {
        element.removeAttribute("checked");
    }
}
function marcarR(check, id) {
    var element = document.getElementsByName("");
    if (check.checked) {
        element.setAttribute("checked", "");
    } else {
        element.removeAttribute("checked");
    }
}
function agregarRMT005() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    TdFecha.setAttribute("colspan", "2")
    var TdEjecuto = row.insertCell(1);
    TdEjecuto.setAttribute("contenteditable", "true")
    TdEjecuto.innerHTML = "&nbsp;";
    var TdDescripcion = row.insertCell(2);
    TdDescripcion.setAttribute("contenteditable", "true")
    TdDescripcion.setAttribute("colspan", "3")
    TdDescripcion.setAttribute("valign", "top")
    TdDescripcion.innerHTML = "&nbsp;";
}
function agregarRMT008() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdEjecuto = row.insertCell(1);
    TdEjecuto.setAttribute("contenteditable", "true")
    TdEjecuto.innerHTML = "&nbsp;";
    var TdDescripcion = row.insertCell(2);
    TdDescripcion.setAttribute("contenteditable", "true")
    TdDescripcion.setAttribute("colspan", "2")
    TdDescripcion.setAttribute("valign", "top")
    TdDescripcion.innerHTML = "&nbsp;";
}
function agregarRMT011() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdEjecuto = row.insertCell(1);
    TdEjecuto.setAttribute("contenteditable", "true")
    TdEjecuto.innerHTML = "&nbsp;";
    var TdDescripcion = row.insertCell(2);
    TdDescripcion.setAttribute("contenteditable", "true")
    TdDescripcion.setAttribute("colspan", "4")
    TdDescripcion.setAttribute("valign", "top")
    TdDescripcion.innerHTML = "&nbsp;";
}
function agregarRMT021() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdEjecuto = row.insertCell(1);
    TdEjecuto.setAttribute("contenteditable", "true")
    TdEjecuto.innerHTML = "&nbsp;";
    var TdDescripcion = row.insertCell(2);
    TdDescripcion.setAttribute("contenteditable", "true")
    TdDescripcion.setAttribute("colspan", "2")
    TdDescripcion.setAttribute("valign", "top")
    TdDescripcion.innerHTML = "&nbsp;";
}
function agregarRMT046() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdEjecuto = row.insertCell(1);
    TdEjecuto.setAttribute("contenteditable", "true")
    TdEjecuto.innerHTML = "&nbsp;";
    var TdDescripcion = row.insertCell(2);
    TdDescripcion.setAttribute("contenteditable", "true")
    TdDescripcion.setAttribute("colspan", "3")
    TdDescripcion.setAttribute("valign", "top")
    TdDescripcion.innerHTML = "&nbsp;";
}
function Promedio(id) {
    var m1 = parseFloat(document.getElementById("m1" + id).innerHTML);
    var m2 = parseFloat(document.getElementById("m2" + id).innerHTML);
    var m3 = parseFloat(document.getElementById("m3" + id).innerHTML);
    var m4 = parseFloat(document.getElementById("m4" + id).innerHTML);
    var suma = (m1 + m2 + m3 + m4);
    var promedio = suma / 4;
    var campoProm = document.getElementById("prom" + id).innerHTML = promedio.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("prom" + id).dispatchEvent(event);
}
function Diferencia(id) {
    var campoProm = parseFloat(document.getElementById("prom" + id).innerHTML);
    var campoA = parseFloat(document.getElementById("A" + id).innerHTML);
    var resta = (campoProm - campoA);
    var campoDelta = document.getElementById("dif" + id).innerHTML = resta.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("dif" + id).dispatchEvent(event);
}

function errorprueba1(id) {
    var valor1 = parseFloat(document.getElementById("cargaPE" + id).innerHTML);
    var valor2 = parseFloat(document.getElementById("indicacionPE" + id).innerHTML);
    var resultado = Math.max(valor2 - valor1);
    document.getElementById("errorPE" + id).innerHTML = resultado.toFixed(3);

    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("errorPE" + id).dispatchEvent(event);
}

function limiterror(id) {
    var valor1 = parseFloat(document.getElementById("errorPE" + id).innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= valor2) {
        document.getElementById("LimiteE" + id).innerHTML = "Cumple";
    } else {
        document.getElementById("LimiteE" + id).innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("LimiteE" + id).dispatchEvent(event);
}
function limiterror2(id) {
    var valor1 = parseFloat(document.getElementById("errorPE" + id).innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= (2 * valor2)) {
        document.getElementById("LimiteE" + id).innerHTML = "Cumple";
    } else {
        document.getElementById("LimiteE" + id).innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("LimiteE" + id).dispatchEvent(event);
}
function limiterror3(id) {
    var valor1 = parseFloat(document.getElementById("errorPE" + id).innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= (3 * valor2)) {
        document.getElementById("LimiteE" + id).innerHTML = "Cumple";
    } else {
        document.getElementById("LimiteE" + id).innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("LimiteE" + id).dispatchEvent(event);
}
function errorprueba2(id, carga) {
    var valor1 = parseFloat(document.getElementById("carga" + carga).innerHTML);
    var valor2 = parseFloat(document.getElementById("indicacionPC" + id).innerHTML);
    var resultado = valor2 - valor1;
    document.getElementById("errorPC" + id).innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });

    // Envía el evento al elemento donde quieres simular la pulsación de la tecla
    document.getElementById("errorPC" + id).dispatchEvent(event);
}
function MaximoMinimo(id1, id2, id3, id4, id5, id) {
    var valor1 = parseFloat(document.getElementById("errorPC" + id1).innerHTML);
    var valor2 = parseFloat(document.getElementById("errorPC" + id2).innerHTML);
    var valor3 = parseFloat(document.getElementById("errorPC" + id3).innerHTML);
    var valor4 = parseFloat(document.getElementById("errorPC" + id4).innerHTML);
    var valor5 = parseFloat(document.getElementById("errorPC" + id5).innerHTML);
    var resultado = Math.max(valor1, valor2, valor3, valor4, valor5) - Math.min(valor1, valor2, valor3, valor4, valor5);
    document.getElementById("MaxMin" + id).innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("MaxMin" + id).dispatchEvent(event);
}
function errorE() {
    var valor1 = parseFloat(document.getElementById("MaxMin1").innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= valor2) {
        document.getElementById("E1").innerHTML = "Cumple";
    } else {
        document.getElementById("E1").innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("E1").dispatchEvent(event);
}
function errorE2() {
    var valor1 = parseFloat(document.getElementById("MaxMin2").innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= (2 * valor2)) {
        document.getElementById("E2").innerHTML = "Cumple";
    } else {
        document.getElementById("E2").innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("E2").dispatchEvent(event);
}
function errorE3() {
    var valor1 = parseFloat(document.getElementById("MaxMin3").innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= (3 * valor2)) {
        document.getElementById("E3").innerHTML = "Cumple";
    } else {
        document.getElementById("E3").innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("E3").dispatchEvent(event);
}
function desvEst(id1, id2, id3, id4, id5, id) {
    var valor1 = parseFloat(document.getElementById("errorPC" + id1).innerHTML);
    var valor2 = parseFloat(document.getElementById("errorPC" + id2).innerHTML);
    var valor3 = parseFloat(document.getElementById("errorPC" + id3).innerHTML);
    var valor4 = parseFloat(document.getElementById("errorPC" + id4).innerHTML);
    var valor5 = parseFloat(document.getElementById("errorPC" + id5).innerHTML);
    var media = (valor1 + valor2 + valor3 + valor4 + valor5) / 5;
    var SumValor1 = Math.pow(valor1 - media, 2);
    var SumValor2 = Math.pow(valor2 - media, 2);
    var SumValor3 = Math.pow(valor3 - media, 2);
    var SumValor4 = Math.pow(valor4 - media, 2);
    var SumValor5 = Math.pow(valor5 - media, 2);
    var varianza = SumValor1 + SumValor2 + SumValor3 + SumValor4 + SumValor5;
    var division = varianza / 4;
    var resultado = Math.sqrt(division);
    document.getElementById("desviacion" + id).innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });

    // Envía el evento al elemento donde quieres simular la pulsación de la tecla
    document.getElementById("desviacion" + id).dispatchEvent(event);
}
function errorprueba3(id) {
    var valor1 = parseFloat(document.getElementById("CargaMax").innerHTML);
    var valor2 = parseFloat(document.getElementById("indicacionPCE" + id).innerHTML);
    var resultado = valor2 - valor1;
    document.getElementById("errorPCE" + id).innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });

    // Envía el evento al elemento donde quieres simular la pulsación de la tecla
    document.getElementById("errorPCE" + id).dispatchEvent(event);
}
function errorPruebaE3(id) {
    var valor1 = parseFloat(document.getElementById("errorPCE" + id).innerHTML);
    var valor2 = parseFloat(document.getElementById("e").innerHTML);
    if (valor1 <= valor2) {
        document.getElementById("LimiterrorPCE" + id).innerHTML = "Cumple";
    } else {
        document.getElementById("LimiterrorPCE" + id).innerHTML = "No cumple";
    }
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("LimiterrorPCE" + id).dispatchEvent(event);
}
function desvEst3(id1, id2, id3, id4, id5, id6) {
    var valor1 = parseFloat(document.getElementById("errorPCE" + id1).innerHTML);
    var valor2 = parseFloat(document.getElementById("errorPCE" + id2).innerHTML);
    var valor3 = parseFloat(document.getElementById("errorPCE" + id3).innerHTML);
    var valor4 = parseFloat(document.getElementById("errorPCE" + id4).innerHTML);
    var valor5 = parseFloat(document.getElementById("errorPCE" + id5).innerHTML);
    var valor6 = parseFloat(document.getElementById("errorPCE" + id6).innerHTML);
    var media = (valor1 + valor2 + valor3 + valor4 + valor5 + valor6) / 6;
    var SumValor1 = Math.pow(valor1 - media, 2);
    var SumValor2 = Math.pow(valor2 - media, 2);
    var SumValor3 = Math.pow(valor3 - media, 2);
    var SumValor4 = Math.pow(valor4 - media, 2);
    var SumValor5 = Math.pow(valor5 - media, 2);
    var SumValor6 = Math.pow(valor6 - media, 2);
    var varianza = SumValor1 + SumValor2 + SumValor3 + SumValor4 + SumValor5 + SumValor6;
    var division = varianza / 5;
    var resultado = Math.sqrt(division);
    document.getElementById("desvEstPCE").innerHTML = resultado.toFixed(3);
    document.getElementById("UExcentricidad").innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("desvEstPCE").dispatchEvent(event);
    document.getElementById("UExcentricidad").dispatchEvent(event);
}

function Ua() {
    var desvEst1 = parseFloat(document.getElementById("desviacion1").innerHTML);
    var desvEst2 = parseFloat(document.getElementById("desviacion2").innerHTML);
    var desvEst3 = parseFloat(document.getElementById("desviacion3").innerHTML);
    var resultado = (Math.max(desvEst1, desvEst2, desvEst3) / 5) * Math.exp(1 / 2);
    document.getElementById("Ua").innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("Ua").dispatchEvent(event);
}
function Ub() {
    var valor1 = parseFloat(document.getElementById("Up").innerHTML);
    var valor2 = parseFloat(document.getElementById("Ur").innerHTML);
    var valor3 = parseFloat(document.getElementById("Ue").innerHTML);
    var resultado = Math.sqrt((Math.pow(valor1, 2) + Math.pow(valor2, 2) + Math.pow(valor3, 2)));
    document.getElementById("Ub").innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("Ub").dispatchEvent(event);
}
function e() {
    var e = document.getElementById("e").innerHTML;
    document.getElementById("UResolucion").innerHTML = e;
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("UResolucion").dispatchEvent(event);
}
function Up() {
    var valor1 = parseFloat(document.getElementById("Upatron").innerHTML);
    var resultado = valor1 / 2;
    document.getElementById("Up").innerHTML = resultado.toFixed(4);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("Up").dispatchEvent(event)
}
function Ur() {
    var valor1 = parseFloat(document.getElementById("UResolucion").innerHTML);
    var resultado = valor1 / 12 * Math.exp(1 / 2);
    document.getElementById("Ur").innerHTML = resultado.toFixed(4);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("Ur").dispatchEvent(event)
}
function Ue() {
    var valor1 = parseFloat(document.getElementById("UExcentricidad").innerHTML);
    var resultado = valor1 / 3 * Math.exp(1 / 2);
    document.getElementById("Ue").innerHTML = resultado.toFixed(4);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("Ue").dispatchEvent(event)
}
function Uc() {
    var valor1 = parseFloat(document.getElementById("Ua").innerHTML);
    var valor2 = parseFloat(document.getElementById("Ub").innerHTML);
    var resultado = Math.sqrt((Math.pow(valor1, 2) + Math.pow(valor2, 2)))
    document.getElementById("Uc").innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("Uc").dispatchEvent(event)
}
function U() {
    var valor1 = parseFloat(document.getElementById("Uc").innerHTML);
    var resultado = valor1 * 2;
    document.getElementById("U").innerHTML = resultado.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("U").dispatchEvent(event)
}
function confirmarfase1() {
    for (i = 1; i < 10; i++) {
        errorprueba1(i);
        errorprueba1(i + "" + i);
        limiterror(i);
    }
}
function confirmarfase2() {
    for (i = 1; i < 6; i++) {
        errorprueba2(i, 1);
        errorprueba2(i + "" + i, 2);
        errorprueba2(i + "" + i + "" + i, 3);
    }
    MaximoMinimo(1, 2, 3, 4, 5, 1);
    MaximoMinimo(11, 22, 33, 44, 55, 2);
    MaximoMinimo(111, 222, 333, 444, 555, 3);
    errorE();
    errorE2();
    errorE3();
    desvEst(1, 2, 3, 4, 5, 1);
    desvEst(11, 22, 33, 44, 55, 2);
    desvEst(111, 222, 333, 444, 555, 3);
}
function confirmarfase3() {
    for (i = 1; i < 6; i++) {
        if (i == 1) {
            errorprueba3(i + "" + i);
            errorPruebaE3(i + "" + i);
        }
        errorprueba3(i);
        errorPruebaE3(i);
    }
    desvEst3(1, 2, 3, 4, 5, 11);
}
function confirmarfase4() {
    Ub();
    Ua();
    Up();
    Ur();
    Ue();
    Uc();
    U();
}
function agregarRMT10() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdEjecuto = row.insertCell(1);
    TdEjecuto.setAttribute("contenteditable", "true")
    TdEjecuto.innerHTML = "&nbsp;";
    var TdDescripcion = row.insertCell(2);
    TdDescripcion.setAttribute("contenteditable", "true")
    TdDescripcion.setAttribute("valign", "top")
    TdDescripcion.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
    var TdFecha = row.insertCell(0);
    TdFecha.setAttribute("contenteditable", "true")
    TdFecha.innerHTML = "&nbsp;";
}
function agregarRMT057() {
    var table = document.getElementById("table1");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var Tdcampo1 = row.insertCell(0);
    Tdcampo1.setAttribute("contenteditable", "true")
    Tdcampo1.setAttribute("colspan", "2")
    Tdcampo1.innerHTML = "&nbsp;";
    var Tdcampo2 = row.insertCell(0);
    Tdcampo2.setAttribute("contenteditable", "true")
    Tdcampo2.innerHTML = "&nbsp;";
    var Tdcampo3 = row.insertCell(0);
    Tdcampo3.setAttribute("contenteditable", "true")
    Tdcampo3.innerHTML = "&nbsp;";
    var Tdcampo4 = row.insertCell(0);
    Tdcampo4.setAttribute("contenteditable", "true")
    Tdcampo4.innerHTML = "&nbsp;";
    var Tdcampo4 = row.insertCell(0);
    Tdcampo4.setAttribute("contenteditable", "true")
    Tdcampo4.innerHTML = "&nbsp;";
    var Tdcampo4 = row.insertCell(0);
    Tdcampo4.setAttribute("contenteditable", "true")
    Tdcampo4.innerHTML = "&nbsp;";
    var Tdcampo5 = row.insertCell(0);
    Tdcampo5.setAttribute("contenteditable", "true")
    Tdcampo5.innerHTML = "&nbsp;";
    var Tdcampo6 = row.insertCell(0);
    Tdcampo6.setAttribute("contenteditable", "true")
    Tdcampo6.innerHTML = "&nbsp;";
    var Tdcampo7 = row.insertCell(0);
    Tdcampo7.setAttribute("contenteditable", "true")
    Tdcampo7.innerHTML = "&nbsp;";
    var Tdcampo8 = row.insertCell(0);
    Tdcampo8.setAttribute("contenteditable", "true")
    Tdcampo8.innerHTML = "&nbsp;";
}
function PromedioR079(id) {
    var l1 = parseFloat(document.getElementById("l1" + id).innerHTML);
    var l2 = parseFloat(document.getElementById("l2" + id).innerHTML);
    var l3 = parseFloat(document.getElementById("l3" + id).innerHTML);
    var suma = (l1 + l2 + l3);
    var promedio = suma / 3;
    document.getElementById("prom" + id).innerHTML = promedio.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("prom" + id).dispatchEvent(event);
}
function DiferenciaR079(id) {
    var lect = parseFloat(document.getElementById("lp" + id).innerHTML);
    var prom = parseFloat(document.getElementById("prom" + id).innerHTML);
    var diferencia = prom - lect;
    document.getElementById("dif" + id).innerHTML = diferencia.toFixed(3);
    var event = new KeyboardEvent('keydown', {
        bubbles: true,
        cancelable: true,
        key: 'Enter',
        char: 'Enter',
        shiftKey: false,
        ctrlKey: false,
        metaKey: false
    });
    document.getElementById("dif" + id).dispatchEvent(event);
}
function Eliminar(id) {
    swal({
        title: "Seguro que desea eliminar la verificacion?",
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "orange",
        cancelButtonColor: "blue",
        confirmButtonText: "Aceptar",
        cancelButtonText: "Cancelar",
        closeOnConfirm: false,
        closeOnCancel: true,
        html: true
    },
            function (isConfirm) {
                if (isConfirm) {
//                    let form = document.getElementById("form" + id + "");
//                    form.submit();
//                    document.getElementById("form" + id + "").submit();
                    location.href = "Javascript:form" + id + ".submit();";
                } else {
                    location.href = "Javascript:formV.submit();";
                }
            });
}
function NoEspacios(e) {
    var campo = e.split(" ");
    campo = campo.join("");
    document.getElementById("numSerial-id").value = campo;
}
function registroI() {
    document.getElementById("btsubmit").disabled = true;
    document.getElementById("btsubmit").value = "";
    document.getElementById("puntos").style.display = "block";
}

function registroV() {
    document.getElementById("btsubmit").disabled = true;
    document.getElementById("btsubmit").value = "";
    document.getElementById("puntos").style.display = "block";
}
function Modvrf(id, mod) {
    swal({
        title: "Que accion desea realizar?",
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "c1c1c1",
        confirmButtonText: "Mod Estado",
        cancelButtonText: "Mod Fecha",
        closeOnConfirm: false,
        closeOnCancel: false,
        html: true
    },
            function (isConfirm) {
                if (isConfirm) {
                    if (mod == 1) {
                        swal({
                            title: "seguro desea cambiar el estado de la verificacion?",
                            text: "",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "68BB18",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                            closeOnCancel: true,
                            html: true
                        },
                                function (isConfirm) {
                                    if (isConfirm) {
                                        location.href = "Javascript:formModestVer" + id + ".submit();";
                                    }
                                });
                    } else {
                        swal("No se ha iniciado una verificación");
                    }
                } else {
                    swal({
                        title: "Seguro desea cambiar la fecha de la verificacion?",
                        text: "<b>Fecha: </b><br /><input type='date' id='datepicker' placeholder='Seleccionar Fecha' style='display:block'>",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "68BB18",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                        closeOnCancel: true,
                        html: true
                    },
                            function (isConfirm) {
                                if (isConfirm) {
                                    var fecha = document.getElementById("datepicker").value;
                                    var idV = document.getElementById("idV" + id).value;
                                    var idTp = document.getElementById("idTp" + id).value;
                                    var idI = document.getElementById("idI" + id).value;
                                    var lstTipoIF = document.getElementById("lstTipoIF" + id).value;
                                    var filtro = document.getElementById("txt_bus" + id).value;
                                    var dias = document.getElementById("txt_dias" + id).value;
                                    location.href = "Instrumento_medicion?opc=11&txt_fecha=" + fecha + "&idI=" + idI + "&idTp=" + idTp + "&idV=" + idV + "&txt_bus=" + filtro + "&lstTipoIF=" + lstTipoIF + "&txt_dias=" + dias + "";
                                }
                            });
                }
            });
}
function informe() {
    var datosC = document.getElementById("datosE");
    var dat = datosC.value;
    if (dat !== "") {
        datosC.value = "";
    }
    for (var i = 0; i < 4; i++) {
        var dif = document.getElementById("dif" + i).innerHTML;
        dat = datosC.value;
        datosC.value = dat + "[" + dif + "]";
    }
}

function allFunct(val) {
//    for (var i = 0; i < 4; i++) {
//        Promedio(i);
//        Diferencia(i);
//    }
    if (val === 1) {
        document.getElementById("divBut").style.display = "none"
    } else {
        document.getElementById("divBut").style.display = "block"
    }
}

