document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("searchInput");
    const sections = document.querySelectorAll(".SectionColor");
    const descriptions = document.querySelectorAll(".DescriptionText");
    const noResultsMessage = document.getElementById("noResultsMessage");

    // Función para normalizar texto (eliminar acentos)
    function normalize(text) {
        return text.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    }

    // Función para limpiar resaltados anteriores
    function clearHighlights() {
        descriptions.forEach(desc => {
            const spans = desc.querySelectorAll("span.highlight");
            spans.forEach(span => {
                span.replaceWith(span.innerText);
            });
        });
    }

    // Función para resaltar texto en nodos de texto, preservando elementos HTML
    function highlightText(query) {
        if (!query) {
            clearHighlights();
            return;
        }

        const normalizedQuery = normalize(query);
        const words = normalizedQuery.split(/\s+/).filter(word => word.length > 0);
        const regex = new RegExp(`\\b(${words.join("|")})\\b`, "gi");

        descriptions.forEach(desc => {
            const section = desc.closest(".SectionColor");
            if (section.style.display === "none") return;

            function processNode(node) {
                if (node.nodeType === Node.TEXT_NODE) {
                    const text = node.nodeValue;
                    if (regex.test(text)) {
                        const span = document.createElement("span");
                        span.innerHTML = text.replace(regex, match => `<span class="highlight">${match}</span>`);
                        node.replaceWith(...span.childNodes);
                    }
                } else if (node.nodeType === Node.ELEMENT_NODE && !node.classList.contains("highlight")) {
                    Array.from(node.childNodes).forEach(child => processNode(child));
                }
            }

            Array.from(desc.childNodes).forEach(node => processNode(node));
        });
    }

    // Listener para el campo de búsqueda
    searchInput.addEventListener("input", function () {
        const query = searchInput.value.trim();
        clearHighlights();
        highlightText(query);

        // Lógica de filtrado y manejo del mensaje de no resultados
        const normalizedQuery = normalize(query);
        let hasVisibleSections = false;

        sections.forEach(section => {
            const rawText = normalize(section.innerText);
            const isVisible = query === "" || rawText.includes(normalizedQuery);
            section.style.display = isVisible ? "" : "none";
            if (isVisible) hasVisibleSections = true;
        });

        // Mostrar u ocultar el mensaje de no resultados
        if (noResultsMessage) {
            noResultsMessage.style.display = hasVisibleSections ? "none" : "block";
        }
    });
});

// CSS para el resaltado
const style = document.createElement("style");
style.innerHTML = `
    .highlight {
        background-color: yellow;
        font-weight: bold;
    }
    #noResultsMessage {
        color: #666;
        font-style: italic;
    }
`;
document.head.appendChild(style);