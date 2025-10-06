
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

    </body>
</html>
