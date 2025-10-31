<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/maquina.tld" prefix="maquina" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8"/>
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <link rel="stylesheet" href="Interfaz/Contenido/select2/dist/css/select2.min.css">

        <title>Maquina | Registro Pesaje</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'maquina.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'maquina.jsp');
            });
        </script>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <maquina:Maquina />
            </div>
        </div>
        <Resultado:ResultadosAlertas />
        <script>
            $(document).ready(function () {
                // Inicializar Select2
                $('#selectMaquina').select2({
                    placeholder: 'Selecciona una opción',
                    allowClear: true
                });
            });
        </script>
        <script src="Interfaz/Contenido/Scripts/jquery360.js"></script>
        <script src="Interfaz/Contenido/select2/dist/js/select2.full.min.js"></script>
    </body>
</html>
