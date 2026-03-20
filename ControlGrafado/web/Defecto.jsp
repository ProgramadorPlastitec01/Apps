<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_defecto.tld" prefix="defecto" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Defecto</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function registroD() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function Editar(Defecto) {
                    location.href = "Defecto?opc=1&idD=" + Defecto + "&txt_bus=" + 0 + "";
                }
                function Inactivar(Defecto) {
                    location.href = "Defecto?opc=4&idD=" + Defecto + "&txt_bus=" + 0 + "&est=0";
                }
                function Activar(Defecto) {
                    location.href = "Defecto?opc=4&idD=" + Defecto + "&txt_bus=" + 0 + "&est=1";
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <defecto:MuestraDefectos />
        </div>
        <resultados:MuestraResultados />
    </body>
</html>
