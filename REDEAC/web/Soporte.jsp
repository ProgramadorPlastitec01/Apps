<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_caso.tld" prefix="caso" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Actividad</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <!--HTML editor-->
            <script language="javascript" type="text/javascript" src="tinyfck/tiny_mce.js"></script>
            <script language="javascript" type="text/javascript" src="tinyfck/HTMLEditor.js"></script>
            <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
            <script type="text/javascript">
                function mueveReloj() {
                    var mydate = new Date();
                    var year = mydate.getYear();
                    if (year < 1000)
                        year += 1900;
                    var day = mydate.getDay();
                    var month = mydate.getMonth() + 1;
                    if (month < 10)
                        month = "0" + month;
                    var daym = mydate.getDate();
                    if (daym < 10)
                        daym = "0" + daym;
                    momentoActual = new Date()
                    hora = momentoActual.getHours()
                    minuto = momentoActual.getMinutes()
                    segundo = momentoActual.getSeconds()
                    if (hora > 12) {
                        hora = hora - 12
                        horaImprimible = year + "-" + month + "-" + daym + "  " + hora + ":" + minuto + ":" + segundo + " PM"
                    } else {
                        horaImprimible = year + "-" + month + "-" + daym + "  " + hora + ":" + minuto + ":" + segundo + " AM"
                    }
                    document.getElementById("fecha_hora").innerHTML = horaImprimible;
                    setTimeout("mueveReloj()", 1000)
                }
            </script>
        </head>
        <body onload="mueveReloj();">
            <div id="content_sin">
            <caso:MuestraCaso/>
        </div>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
        <script src="Interfaz/Firma/assets/bezier.js"></script>
        <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
        <script src="Interfaz/Firma/assets/json2.min.js"></script>
    </body>
</html>
