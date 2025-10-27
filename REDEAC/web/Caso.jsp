<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_caso.tld" prefix="caso" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Actividad</title>
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <!--HTML editor-->
            <script language="javascript" type="text/javascript" src="tinyfck/tiny_mce.js"></script>
            <script language="javascript" type="text/javascript" src="tinyfck/HTMLEditor.js"></script>
            <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
            <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
        </head>

        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <caso:MuestraCaso/> 
        </div>
        <resultado:MuestraResultado/>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
        <script>
            CKEDITOR.replace("editor");
        </script>
        <script>
            function alertas(id) {
                var id_caso = id;
                swal({
                    title: "Advertencia!",
                    text: "¿Esta segur@ que desea eliminar el caso?<br> Esto sera permanente.<form action='Caso?opc=1&mod=CA&txt_bus=&idC=" + id_caso + "&action=1' method='POST'><a href='Caso?opc=1&mod=CA&txt_bus=' class='btnCancelar'>Cancelar</a><button type='submit' id='btnConfirm'>Eliminar</button></form>",
                    type: "warning",
                    showCancelButton: false,
                    showConfirmButton: false,
                    html: true
                });
            }
        </script>
        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
        <script src="Interfaz/Firma/assets/bezier.js"></script>
        <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
        <script src="Interfaz/Firma/assets/json2.min.js"></script>
    </body>
</html>
