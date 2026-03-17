<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_ficha_tecnica.tld" prefix="ficha" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Ficha Tecnica</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function registroF() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function ModificarFT(ficha) {
                    location.href = "Ficha_tecnica?opc=1&idF=" + ficha + "&txt_bus=0";
                }
                function Rechazar(ficha) {
                    location.href = "Ficha_tecnica?opc=4&idF=" + ficha + "&est=0&txt_bus=0";
                }
                function Aprobar(ficha) {
                    location.href = "Ficha_tecnica?opc=4&idF=" + ficha + "&est=1&txt_bus=0";
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <ficha:MuestraFicha />
        </div>
        <resultados:MuestraResultados />
    </body>
</html>
