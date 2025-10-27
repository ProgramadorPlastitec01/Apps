<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_calificar_caso.tld" prefix="calificacion" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calificar Caso</title>
        <link rel="icon" type="image/png" href="Interfaz/Contenido/Images/Logo.png"/>
        <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
        <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <link type="text/css" href="Interfaz/FontAwesome/css/all.css" rel="stylesheet">
        <link type="text/css" href="Interfaz/Contenido/Css/Moldal.css" rel="stylesheet">

    </head>
    <body>
        <div id="content">
            <calificacion:MuestraCalificarCaso/>
            <resultado:MuestraResultado/>
        </div>
        <script type="text/javascript">
            const openEls = document.querySelectorAll("[data-open]");
            const isVisible = "is-visible";

            for (const el of openEls) {
                el.addEventListener("click", function () {
                    const modalId = this.dataset.open;
                    document.getElementById(modalId).classList.add(isVisible);
                });
            }

            const closeEls = document.querySelectorAll("[data-close]");
            for (const el of closeEls) {
                el.addEventListener("click", function () {
                    this.parentElement.parentElement.parentElement.classList.remove(isVisible);
                });
            }
            document.addEventListener("click", (e) => {
                if (e.target === document.querySelector(".modal.is-visible")) {
                    document.querySelector(".modal.is-visible").classList.remove(isVisible);
                }
            });

            document.addEventListener("keyup", (e) => {
                if (e.key === "Escape" && document.querySelector(".modal.is-visible")) {
                    document.querySelector(".modal.is-visible").classList.remove(isVisible);
                }
            });
        </script>

        <script type="text/javascript">
            // Función para permitir solo números
            function Numeros(string) {
                var out = '';
                var filtro = '1234567890';
                for (var i = 0; i < string.length; i++) {
                    if (filtro.indexOf(string.charAt(i)) !== -1) {
                        out += string.charAt(i);
                    }
                }
                return out;
            }
        </script>

    </body>
</html>
