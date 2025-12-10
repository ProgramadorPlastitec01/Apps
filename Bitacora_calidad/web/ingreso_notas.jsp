<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/NotasTLD.tld" prefix="notas"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type="text/javascript">
                function registroN() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript" >
                function completar(obj) {
                    if (obj <= 9) {
                        compl = "0" + obj;
                    } else {
                        compl = obj;
                    }
                    return compl;
                }
                function fecha() {
                    horasistema = new Date();
                    anio = horasistema.getFullYear();
                    mes = horasistema.getMonth() + 1;
                    dia = horasistema.getDate();
                    aniocomple = completar(anio);
                    mescomplet = completar(mes);
                    diacomplet = completar(dia);
                    fechaCompleta = aniocomple + "/" + mescomplet + "/" + diacomplet;
                    document.form1.txtfecha.value = fechaCompleta;
                }
            </script>
        </head>
        <body onload="fecha()" id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu/>
            <notas:MuestraNotas></notas:MuestraNotas>
            </div>
        <resultados:Resultados/>
    </body>
</html>
