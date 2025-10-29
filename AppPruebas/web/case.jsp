<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_case.tld" prefix="CaseApp" %>
<%@taglib uri="/WEB-INF/tlds/tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/chocolat/dist/css/chocolat.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/style.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/components.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/codemirror.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/monokai.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="content.jsp"></jsp:include>
                <div class="main-content" style="min-height: 600px;">
                    <CaseApp:Case/>
                </div>
            </div>
        </div>
        <Alerts:AlertData/>
        <script src="Interfaz/Contenido/assets/js/codemirror.js"></script>
        <script src="Interfaz/Contenido/assets/js/codeSql.js"></script>
        <script>
            // Inicializa el editor CodeMirror
            const editor = CodeMirror.fromTextArea(document.getElementById("editor"), {
                mode: 'text/x-sql', // Resaltado de sintaxis para SQL
                theme: 'monokai', // Tema oscuro
                lineNumbers: true, // Mostrar números de línea
                lineWrapping: true, // Ajuste automático de líneas
                indentWithTabs: true,
                smartIndent: true,
                matchBrackets: true, // Resaltar paréntesis y corchetes
                autofocus: true
            });
            const editor2 = CodeMirror.fromTextArea(document.getElementById("editor2"), {
                mode: 'text/x-sql', // Resaltado de sintaxis para SQL
                theme: 'monokai', // Tema oscuro
                lineNumbers: true, // Mostrar números de línea
                lineWrapping: true, // Ajuste automático de líneas
                indentWithTabs: true,
                smartIndent: true,
                matchBrackets: true, // Resaltar paréntesis y corchetes
                autofocus: true
            });
            const edito3 = CodeMirror.fromTextArea(document.getElementById("editor3"), {
                mode: 'text/x-sql', // Resaltado de sintaxis para SQL
                theme: 'monokai', // Tema oscuro
                lineNumbers: true, // Mostrar números de línea
                lineWrapping: true, // Ajuste automático de líneas
                indentWithTabs: true,
                smartIndent: true,
                matchBrackets: true, // Resaltar paréntesis y corchetes
                autofocus: true
            });
            const editor4 = CodeMirror.fromTextArea(document.getElementById("editor4"), {
                mode: 'text/x-sql', // Resaltado de sintaxis para SQL
                theme: 'monokai', // Tema oscuro
                lineNumbers: true, // Mostrar números de línea
                lineWrapping: true, // Ajuste automático de líneas
                indentWithTabs: true,
                smartIndent: true,
                matchBrackets: true, // Resaltar paréntesis y corchetes
                autofocus: true
            });
        </script>

        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/js/stisla.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <script src="Interfaz/Contenido/assets/modules/chocolat/dist/js/jquery.chocolat.min.js"></script>
        <!--<script src="Interfaz/Contenido/assets/js/scripts.js"></script>-->
        <script src="Interfaz/Contenido/assets/js/custom.js"></script>
    </body>
</html>
