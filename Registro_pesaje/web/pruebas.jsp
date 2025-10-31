<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/pruebas.tld" prefix="Pruebas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv='cache-control' content='no-cache'>
        <meta http-equiv='expires' content='0'>
        <meta http-equiv='pragma' content='no-cache'>
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Pruebas | Registro Pesaje</title>
        <link rel="stylesheet" href="Interfaz/Contenido/Editor/samples/css/samples.css">
        <link rel="stylesheet" href="Interfaz/Contenido/Editor/samples/toolbarconfigurator/lib/codemirror/neo.css">
    </head>
    <body >
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <Pruebas:Pruebas />
            </div>
        </div>
        <script type="application/javascript" >
//            setInterval(function (){
//                document.getElementById("btn_flo").click();
//                console.log("Ejecutado");
//            }, 5000);
        </script>
        
        
        <!--<script src="Interfaz/Contenido/Editor/samples/js/sample.js"></script>-->
        <!--<script src="Interfaz/Contenido/Editor/ckeditor.js"></script>-->
        <!--<script src="Interfaz/Contenido/Editor/Configuracion.js"></script>-->
        <!--<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>-->
        <script src="Interfaz/Contenido/Scripts/LeerDatos.js"></script>   
    </body>
</html>
