<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/MaquinasTLD.tld" prefix="maquinas"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'maquinaria.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'maquinaria.jsp');
                });
            </script>
            <script type="text/javascript">
                function registroM() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu />
            <maquinas:VistaMaquinas></maquinas:VistaMaquinas>
            </div>
        <resultados:Resultados/>
    </body>
</html>