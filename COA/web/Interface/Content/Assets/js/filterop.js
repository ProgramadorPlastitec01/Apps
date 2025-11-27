/* global fetch */

function SearchForProductsOrder() {
    const orderInput = document.getElementById("orderInput");
    const order = orderInput.value.trim();

    let productosSelect = document.getElementById("resultadoProductos");
    let lotesSelect = document.getElementById("resultadoLotes");

    // 🔹 Si borran el campo de orden, limpiar todo
    if (order === "") {
        productosSelect.innerHTML = "<option value=''>-- Seleccione un producto --</option>";
        lotesSelect.innerHTML = "<option value=''>-- Seleccione un lote --</option>";
        return;
    }

    fetch("SearchProductsServlet?orden=" + order)
            .then(response => response.json())
            .then(data => {
                productosSelect.innerHTML = "";
                lotesSelect.innerHTML = "<option value=''>-- Seleccione un lote --</option>"; // limpiar lotes siempre

                if (data.length === 0) {
                    productosSelect.innerHTML = "<option value=''>No se encontraron productos</option>";
                    return;
                }

                let defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.text = "-- Seleccione un producto --";
                productosSelect.appendChild(defaultOption);

                // Guardar lotes en data-atributos
                data.forEach(item => {
                    let option = document.createElement("option");
                    option.value = item.codigo;
                    option.text = item.codigo + " - " + item.producto;
                    option.dataset.lotes = item.lote;
                    productosSelect.appendChild(option);
                });

                // 🔥 Escuchar cuando seleccionas un producto
                productosSelect.onchange = function () {
                    let selected = productosSelect.options[productosSelect.selectedIndex];
                    lotesSelect.innerHTML = "";

                    if (selected && selected.dataset.lotes && selected.dataset.lotes.trim() !== "") {
                        let lotes = selected.dataset.lotes.split(",").map(l => l.trim());

                        let defaultOption = document.createElement("option");
                        defaultOption.value = "";
                        defaultOption.text = "-- Seleccione un lote --";
                        lotesSelect.appendChild(defaultOption);

                        lotes.forEach(lote => {
                            let option = document.createElement("option");
                            option.value = lote;
                            option.text = lote;
                            lotesSelect.appendChild(option);
                        });
                    } else {
                        // Si no hay lotes, mostrar solo la opción default
                        let defaultOption = document.createElement("option");
                        defaultOption.value = "";
                        defaultOption.text = "-- No hay lotes disponibles --";
                        lotesSelect.appendChild(defaultOption);
                    }
                };
            })
            .catch(err => console.error("Error en la consulta:", err));
}
