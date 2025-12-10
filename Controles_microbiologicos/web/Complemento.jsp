<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Complemento.tld" prefix="Complemento"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Complementos</title>
            <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function validarPCT(dato) {
                    var PrmProcentaje = (50 * dato) / 100;
                    var SgdProcentaje = (70 * dato) / 100;
                    var cumple = document.getElementById("Txt_cumple");
                    var alerta = document.getElementById("Txt_alerta");
                    var accion = document.getElementById("Txt_accion")
                    cumple.value = PrmProcentaje;
                    alerta.value = SgdProcentaje;
                    accion.value = dato;
                    cumple.setAttribute("min", 0)
                    cumple.setAttribute("max", PrmProcentaje)
                    alerta.setAttribute("min", (PrmProcentaje + 1))
                    alerta.setAttribute("max", SgdProcentaje)
                    accion.setAttribute("min", (SgdProcentaje + 1))
                    accion.setAttribute("max", dato)
                    document.getElementById("Txt_incumplimiento").value = dato;
                }
            </script>
    </head>
    <body id="subpage">
        <div id="templatemo_wrapper">
            <Menu:Menu />
            <Complemento:Complemento />
        </div>
        <Alertas:Alertas />
    </body>
</html>