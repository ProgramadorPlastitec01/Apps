
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/visual"  prefix="Visual" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
            <Visual:Visual/>
        </div>
        <script>
            document.querySelectorAll('.editable').forEach(el => {
                el.addEventListener('blur', () => {
                    // Si el usuario borra todo el contenido, lo reestablece con ****
                    if (el.innerText.trim() === '') {
                        el.innerText = '****';
                    }
                });

                // Previene eliminar completamente el span
                el.addEventListener('keydown', (e) => {
                    if ((e.key === 'Delete' || e.key === 'Backspace') && el.innerText.trim() === '') {
                        e.preventDefault();
                    }
                });
            });
        </script>
        <script>
            function saveHtml() {
                var form = document.getElementById('FormGenerate');
                if (!form) {
                    alert("Formulario no encontrado");
                    return;
                }

                // Obtener el contenido específico del div donde está el HTML dinámico
                var htmlContainer = document.getElementById('HtmlContent');
                var contentHtml = htmlContainer ? htmlContainer.innerHTML : "";
                var encodedHtml = encodeURIComponent(contentHtml);

                // Capturar elementos con ID
                var consText = document.getElementById('consValue')?.textContent.trim() || '';
                        var clientText = document.getElementById('clientValue')?.textContent.trim() || '';
                        var idRegisterText = document.getElementById('IdRegister')?.textContent.trim() || '';
                        var Code = document.getElementById('codeValue')?.textContent.trim() || '';

                // Función helper para agregar inputs ocultos
                const addHidden = (name, value) => {
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = name;
                    input.value = value;
                    form.appendChild(input);
                };

                // Agregar datos al formulario
                addHidden('Html', encodedHtml);
                if (consText)
                    addHidden('ConsValue', consText);
                if (clientText)
                    addHidden('clientValue', clientText);
                if (idRegisterText)
                    addHidden('IdRegisterValue', idRegisterText);
                if (Code)
                    addHidden('codeValue', Code);

                form.submit();
            }
        </script>



    </body>
</html>
