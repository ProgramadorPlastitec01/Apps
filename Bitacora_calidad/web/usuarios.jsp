<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/UsuariosTLD.tld" prefix="usuarios"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'usuarios.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'usuarios.jsp');
                });
            </script>
            <script type="text/javascript">
                function registroU() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu />
            <usuarios:MuestraUsuarios />
        </div>
        <resultados:Resultados />
    </body>
</html>