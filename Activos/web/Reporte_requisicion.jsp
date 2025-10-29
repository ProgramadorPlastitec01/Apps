<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Reporte_requisicion.tld" prefix="Reporte" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <script type="text/javascript">
                history.pushState(null, null, 'Reporte_requisicion.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Reporte_requisicion.jsp');
                });
                function Filtrartodo() {
                    Filtrar();
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <alertas:Alertas/>
            <div style="float: right;"><a href="javascript:window.open('http://172.16.2.111:8084/Activos/','','width=1024,height=720,left=50,top=50,toolbar=yes');void 0"><img style="width: 100px" src="Interfaz/Contenido/images/logoAct.png" alt="Portfolio1"></a></div><br /><br /><br />
            <Reporte:Reporte_requisicion/>
        </div>
    </body>
</html>

