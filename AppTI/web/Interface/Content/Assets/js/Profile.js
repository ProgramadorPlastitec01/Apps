document.addEventListener("DOMContentLoaded", () => {
  const total = 26;
  const visible = 13; // número total de imágenes visibles (impar)
  const track = document.getElementById("carouselTrack");

  // Obtener imagen inicial desde el backend (input oculto)
  const initialImageInput = document.getElementById("initialImage");
  let current = 0;

  if (initialImageInput) {
    const initialImage = initialImageInput.value; // Ej: "E13.png"
    const match = initialImage.match(/E(\d+)\.png/);
    if (match) {
      const index = parseInt(match[1], 10);
      if (!isNaN(index) && index >= 1 && index <= total) {
        current = index - 1; // Ajustar al índice base 0
      }
    }
  }

  function getIndex(offset) {
    return (current + offset + total) % total;
  }

  function updateCarousel() {
    track.innerHTML = "";

    const half = Math.floor(visible / 2);
    let selectedImageName = "";

    for (let i = -half; i <= half; i++) {
      const imgIndex = getIndex(i) + 1;
      const img = document.createElement("img");
      img.src = `Interface/Imagen/Profile/E${imgIndex}.png`;
      img.alt = `Imagen ${imgIndex}`;

      if (i === 0) {
        img.classList.add("center");
        selectedImageName = `E${imgIndex}.png`;
      }

      track.appendChild(img);
    }

    // Actualiza el input con el nombre de la imagen central
    const input = document.getElementById("selectedImage");
    if (input) {
      input.value = selectedImageName;
    }
  }

  window.prevSlide = () => {
    current = (current - 1 + total) % total;
    updateCarousel();
  };

  window.nextSlide = () => {
    current = (current + 1) % total;
    updateCarousel();
  };

  updateCarousel();
});
