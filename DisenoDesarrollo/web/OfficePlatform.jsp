<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Gestor de Archivos | A-D&D</title>
            <link rel="shortcut icon" href="Interfaz/Contenido/Img/favicon.ico" type="image/x-icon">
            <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/fontawesome/css/all.min.css">
            <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
            <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
            <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        </head>

        <body>
            <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <div id="app">
                <div class="main-wrapper main-wrapper-1">
                    <Menu:Menu />
                    <div class="main-content" style="min-height: 694px;">
                        <section class="section">
                            <div class="section-header">
                                <h1>Gestor de Archivos y Editor de Texto</h1>
                            </div>
                            <div class="section-body">
                                <div class="card">
                                    <div class="card-body" style="padding: 20px; min-height: 600px;">
                                        <div id="office-platform"></div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>

            <script src="Interfaz/Contenido/assets/modules/jquery.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/popper.js"></script>
            <script src="Interfaz/Contenido/assets/modules/tooltip.js"></script>
            <script src="Interfaz/Contenido/assets/modules/bootstrap/js/bootstrap.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/nicescroll/jquery.nicescroll.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/stisla.js"></script>
            <script src="Interfaz/Contenido/assets/js/scripts.js"></script>
        </body>

        </html>