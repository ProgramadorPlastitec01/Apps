<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/ubicacionTLD.tld" prefix="ubicacion"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ubicacion</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'ubicacion.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'ubicacion.jsp');
                });
            </script>
            <script type="text/javascript">
                function registroUb() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu/>
            <ubicacion:ubicacion></ubicacion:ubicacion>
            </div>
        <resultados:Resultados/>
    </body>
</html>
