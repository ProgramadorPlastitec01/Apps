document.addEventListener("DOMContentLoaded", function () {
    const tds = document.querySelectorAll("tbody tr td:first-child");

    tds.forEach(td => {
        const textoRaw = td.textContent;
        const texto = textoRaw ? textoRaw.trim() : "";
        if (/^\d+(\.\d+)*$/.test(texto)) {
            const tr = td.parentElement;
            const btn = document.createElement("button");
            btn.innerHTML = '<i class="fas fa-times"></i>';
            btn.className = "btn btn-sm btn-danger me-2 mr-2";
            btn.title = "Eliminar fila o grupo";
            btn.addEventListener("click", () => eliminarFilaOGrupo(tr));

            const div = document.createElement("div");
            div.classList.add("d-flex", "align-items-center");
            div.appendChild(btn);

            const span = document.createElement("span");
            span.textContent = texto;
            div.appendChild(span);

            td.textContent = "";
            td.appendChild(div);
        }
    });
});

/**
 * Elimina una fila o grupo completo según la jerarquía,
 * y luego reenumerar apropiadamente.
 */
function eliminarFilaOGrupo(tr) {
    const tabla = tr.closest("table");
    if (!tabla)
        return;
    const tbody = tabla.querySelector("tbody");
    if (!tbody)
        return;

    const tdSpan = tr.querySelector("td:first-child span");
    const numeroEliminado = tdSpan && tdSpan.textContent ? tdSpan.textContent.trim() : null;
    if (!numeroEliminado)
        return;

    const partes = numeroEliminado.split(".");
    const esCabeceraSegundoNivel = partes.length === 2;

    if (esCabeceraSegundoNivel) {
        // Usar SweetAlert v1 en lugar del confirm nativo
        swal({
            title: "¿Eliminar grupo " + numeroEliminado + "?",
            text: "Esta acción eliminará todas las subfilas relacionadas.",
            icon: "warning",
            buttons: ["Cancelar", "Eliminar"],
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                eliminarGrupo(tbody, numeroEliminado, partes[0]);
                swal("Grupo eliminado correctamente", {icon: "success", timer: 1500});
            }
        });
    } else {
        swal({
            title: "¿Eliminar fila " + numeroEliminado + "?",
            icon: "warning",
            buttons: ["Cancelar", "Eliminar"],
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                tr.remove();
                reenumerarSubnivel(tbody, numeroEliminado);
                swal("Fila eliminada correctamente", {icon: "success", timer: 1200});
            }
        });
    }
}

function eliminarGrupo(tbody, numeroEliminado, major) {
    const prefijo = numeroEliminado + ".";
    const filas = Array.from(tbody.querySelectorAll("tr"));
    filas.forEach(f => {
        const span = f.querySelector("td:first-child span");
        const texto = span && span.textContent ? span.textContent.trim() : "";
        if (texto === numeroEliminado || (texto && texto.startsWith(prefijo))) {
            f.remove();
        }
    });
    reenumerarCabecerasSegundoNivel(tbody, major);
}

/**
 * Reenumerar subfilas de un mismo prefijo (ej: 2.5.1,2.5.2 ...) cuando se elimina una subfila.
 */
function reenumerarSubnivel(tbody, numeroBase) {
    const partes = numeroBase.split(".");
    if (partes.length < 3)
        return;

    const prefijo = partes.slice(0, -1).join(".") + ".";
    let contador = 1;

    const filas = Array.from(tbody.querySelectorAll("tr"));
    filas.forEach(f => {
        const span = f.querySelector("td:first-child span");
        if (!span || !span.textContent)
            return;
        const texto = span.textContent.trim();

        if (texto.startsWith(prefijo)) {
            span.textContent = prefijo + contador;
            contador++;
        }
    });
}

/**
 * Reenumerar cabeceras de segundo nivel y actualizar sus subfilas.
 */
function reenumerarCabecerasSegundoNivel(tbody, major) {
    const filas = Array.from(tbody.querySelectorAll("tr"));
    const oldHeaders = [];
    filas.forEach(f => {
        const span = f.querySelector("td:first-child span");
        if (!span || !span.textContent)
            return;
        const texto = span.textContent.trim();
        const partes = texto.split(".");
        if (partes.length === 2 && partes[0] === major) {
            oldHeaders.push(texto);
        }
    });

    if (oldHeaders.length === 0)
        return;

    const mapping = {};
    let contador = 1;
    oldHeaders.forEach(old => {
        const nueva = major + "." + contador;
        mapping[old] = nueva;
        contador++;
    });

    const filas2 = Array.from(tbody.querySelectorAll("tr"));
    filas2.forEach(f => {
        const span = f.querySelector("td:first-child span");
        if (!span || !span.textContent)
            return;
        const texto = span.textContent.trim();

        if (mapping[texto]) {
            span.textContent = mapping[texto];
            return;
        }

        for (const oldHeader in mapping) {
            if (Object.prototype.hasOwnProperty.call(mapping, oldHeader)) {
                const oldPref = oldHeader + ".";
                if (texto.startsWith(oldPref)) {
                    const resto = texto.slice(oldPref.length);
                    span.textContent = mapping[oldHeader] + "." + resto;
                    break;
                }
            }
        }
    });
}