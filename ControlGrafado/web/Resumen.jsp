<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resumen.tld" prefix="Resumen" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Resumen</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function registroM() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript" language="javascript">
                function Agregar() {
                    var texto_datoss = document.getElementById("loteE-id");
                    var textD = texto_datoss.options[texto_datoss.selectedIndex].text;
                    document.getElementById("campos-id").value = textD;
                    document.forMC.submit();
                }
                function AgregarL(lote) {
                    var campoL = document.getElementById("lotesF").value;
                    var div = document.getElementById("lotesA");
                    if (campoL === "") {
                        document.getElementById("lotesF").value = "[" + lote + "]";
                        div.innerHTML = "<table class='table' id='tableL' style='width:100%;'></table>";
                        var tabla = document.getElementById("tableL");
                        tabla.innerHTML = "<tr><td align='center'>" + lote + "</td><td align='center'><a href='#' onclick='EliminarL(\"" + lote + "\")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='15' height='15' title='Eliminar'></a></td></tr>";
                    } else {
                        var n = campoL.includes("[" + lote + "]");
                        if (n === false) {
                            var tabla = document.getElementById("tableL");
                            document.getElementById("lotesF").value = campoL + "[" + lote + "]";
                            var elemento = tabla.innerHTML;
                            tabla.innerHTML = elemento + "<tr><td align='center'>" + lote + "</td><td align='center'><a href='#' onclick='EliminarL(\"" + lote + "\")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='15' height='15' title='Eliminar'></a></td></tr>";
                        }
                    }
                }
                function EliminarL(lote) {
                    var tabla = document.getElementById("tableL");
                    var campoL = document.getElementById("lotesF").value;
                    var contenido = tabla.innerHTML;
                    var n = campoL.includes("[" + lote + "]");
                    if (n === true) {
                        campoL = campoL.replace("[" + lote + "]", "");
                        contenido = contenido.replace("<tr><td align=\"center\">" + lote + "</td><td align=\"center\"><a href=\"#\" onclick=\"EliminarL(&quot;" + lote + "&quot;)\"><img src=\"Interfaz/Contenido/Iconos/Delete.png\" width=\"15\" height=\"15\" title=\"Eliminar\"></a></td></tr>", "");
                        document.getElementById("lotesF").value = campoL;
                        tabla.innerHTML = contenido;
                    }
                }
                function agregarO(idO) {
                    var valor = document.getElementById("idOrdenes").value;
                    var nombres = document.getElementById("ordenes").innerHTML;
                    var id = idO.split("//")[0];
                    var name = idO.split("//")[1];
                    if (valor === "") {
                        document.getElementById("idOrdenes").value = "[" + id + "]";
                        document.getElementById("ordenes").innerHTML = "<b class='negro'>" + name + "</b><br/>";
                        document.getElementById("ordenesDiv").value = "<b class='negro'>" + name + "</b><br/>";
                    } else {
                        var cond = valor.includes("[" + id + "]");
                        if (cond === false) {
                            document.getElementById("idOrdenes").value = valor + "[" + id + "]";
                            document.getElementById("ordenes").innerHTML = nombres + "<b class='negro'>" + name + "</b><br/>";
                            document.getElementById("ordenesDiv").value = nombres + "<b class='negro'>" + name + "</b><br/>";
                        }
                    }
                }
                function agregarL(idL) {
                    var valor = document.getElementById("idLotes").value;
                    var nombres = document.getElementById("lotes").innerHTML;
                    var lote = idL.split("//")[0];
                    if (valor === "") {
                        document.getElementById("idLotes").value = "[" + lote + "]";
                        document.getElementById("lotes").innerHTML = "<b class='negro'>" + lote + " "+ idL.split("//")[1] +"</b><br/>";
                        document.getElementById("lotesDiv").value = "<b class='negro'>" + lote + " "+ idL.split("//")[1] +"</b><br/>";
                    } else {
                        var cond = valor.includes("[" + lote + "]");
                        if (cond === false) {
                            document.getElementById("idLotes").value = valor + "[" + lote + "]";
                            document.getElementById("lotes").innerHTML = nombres + "<b class='negro'>" + lote + " "+ idL.split("//")[1] +"</b><br/>";
                            document.getElementById("lotesDiv").value = nombres + "<b class='negro'>" + lote + " "+ idL.split("//")[1] +"</b><br/>";
                        }
                    }
                }
                function registroDespejeResumen(id) {
                    location.href = "javascript:window.open('Turno?opc=15&idD=" + id + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0";
                }
                function agregar(idD) {
                    var slcFicha = idD.split('//')[0];
                    document.getElementById("idFicha").value = slcFicha;
                    document.formFT.submit();
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <Resumen:MuestraResumen/>
        </div>
        <resultados:MuestraResultados />
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>