<%@page contentType="text/html" pageEncoding="ISO-8859-1"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_maquinas.tld" prefix="maquinas" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <title>Maquinas</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        <script type = "text/javascript" >
            history.pushState(null, null, 'maquinas.jsp');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, 'maquinas.jsp');
            });
        </script>
        <script type="text/javascript">
            var statsend = false;
            function checkSubmit(){
                if(!statsend){
                    statsend = true;
                    return true;
                }else{
                    alert(" Un momento por favor el formulario se esta enviando...");
                    return false;
                }
            }
        </script>
    </head>
    <body id="subpage">
        <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <maquinas:MuestraMaquinas />
        </div>
    <resultados:MuestraResultados />
</body>
</html>
