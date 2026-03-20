<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_maquina.tld" prefix="maquina" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Maquina</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function registroM() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function Editar(maquina) {
                    location.href = "Maquina?opc=1&idM=" + maquina + "&txt_bus=" + 0 + "";
                }
                function Inactivar(maquina) {
                    location.href = "Maquina?opc=4&idM=" + maquina + "&txt_bus=" + 0 + "&estado=0";
                }
                function Activar(maquina) {
                    location.href = "Maquina?opc=4&idM=" + maquina + "&txt_bus=" + 0 + "&estado=1";
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <maquina:MuestraMaquina />
        </div>
        <resultados:MuestraResultados />
    </body>
</html>
