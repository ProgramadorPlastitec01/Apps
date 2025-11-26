document.addEventListener("DOMContentLoaded", function () {
    const input1 = document.getElementById("myInput");
    if (!input1) {
        console.error("❌ ERROR: No se encontró el input con ID 'myInput'");
    } else {
        input1.addEventListener("input", filterList1);
    }

    const input2 = document.getElementById("myInput2");
    if (!input2) {
        console.error("❌ ERROR: No se encontró el input con ID 'myInput2'");
    } else {
        input2.addEventListener("input", filterList2);
    }

    const input3 = document.getElementById("myInput3");
    if (!input3) {
        console.error("❌ ERROR: No se encontró el input con ID 'myInput3'");
    } else {
        input3.addEventListener("input", filterList3);
    }

    function filterList1(e) {
        filterItems(e, "container", "single-item");
    }

    function filterList2(e) {
        filterItems(e, "container2", "single-item2");
    }

    function filterList3(e) {
        filterItems(e, "container3", "single-item3");
    }

    function filterItems(e, containerId, itemClass) {
        try {
            const filter = e.target.value.toUpperCase();
            const container = document.getElementById(containerId);

            if (!container) {
                console.error(`❌ ERROR: No se encontró el contenedor con ID '${containerId}'`);
                return;
            }

            const items = container.getElementsByClassName(itemClass);

            if (items.length === 0) {
                console.warn(`⚠️ No hay elementos con la clase '${itemClass}'`);
                return;
            }

            let hasResults = false;

            for (let i = 0; i < items.length; i++) {
                const textContent = items[i].innerText.toUpperCase();

                if (textContent.includes(filter)) {
                    items[i].style.display = "";
                    hasResults = true;
                } else {
                    items[i].style.display = "none";
                }
            }

            if (!hasResults) {
                console.warn("❌ No se encontraron coincidencias.");
            }
        } catch (error) {
            console.error("❌ ERROR EN LA FUNCIÓN:", error);
        }
    }
});
