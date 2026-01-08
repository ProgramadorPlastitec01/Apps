
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Interface/Content/Assets/css/404.css">

    </head>
    <body>

        <div class="glass-card">

            <div class="icon-box">📄</div>

            <div class="error-title">Oops..</div>

            <div class="error-desc">
                El certificado, batch record o enlace que intentas abrir
                no está disponible o ya no existe en COA.
                <br><br>
                No pasa nada, te ayudamos a continuar.
            </div>

            <div class="d-flex justify-content-center gap-3">
                <button onclick="history.back()" class="btn btn-soft">Regresar</button>
            </div>

            <div class="error-footer">
                <img style="width: 18%" src="Interface/Imagen/LogoSWhite.png">
            </div>

        </div>

    </body>
</html>
