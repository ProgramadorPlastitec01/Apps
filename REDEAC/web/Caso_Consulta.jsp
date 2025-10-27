<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu_consulta.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_caso_consulta.tld" prefix="caso" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>REDEAC</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <!--HTML editor-->
            <script language="javascript" type="text/javascript" src="tinyfck/tiny_mce.js"></script>
            <script language="javascript" type="text/javascript" src="tinyfck/HTMLEditor.js"></script>
            <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
            <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        </head>
        <body>
        <menu:MuestraMenuConsulta/>
        <div id="content">
            <caso:MuestraCasoConsulta/>
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
        <script type="text/javascript">
            function timer() {
                swal({
                    title: 'Favor espere, se esta enviando un correo con el caso!',
                    text: '<i class="fas fa-spinner fa-spin" style="font-size: 50px;color: #00281b;"></i>',
                    type: 'warning',
                    buttons: false,
                    showConfirmButton: false,
                    allowEscapeKey: false,
                    dangerMode: true,
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