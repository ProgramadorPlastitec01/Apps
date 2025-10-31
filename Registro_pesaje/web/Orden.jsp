<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/ordenProduccion.tld" prefix="ordenProduccion" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
<!--        <script type = "text/javascript" >
            history.pushState(null, null, 'Orden.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Orden.jsp');
            });
        </script>-->
        <script>
            function ChangeMode(cont1, cont2) {
                document.getElementById("contOrder" + cont1).style.display = "block";
                document.getElementById("contOrder" + cont2).style.display = "none";
            }
        </script>
        <script>
            function FiltroAvanzado() {
                var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
                if (filtro !== "") {
                    document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                    document.getElementById('Buscar_valores').innerHTML += "<div><input class=\"form-control\" value='" + filtro + "' style='text-decoration:none;cursor:pointer;color:black;background:#d8dae9;'><button type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\"><img src=\"Interfaz/Contenido/Imagenes/trash-can.png\" alt=\"Logo\" width=\"16\"></button></div><br />";
                }
                document.getElementById('Txt_filtro_avanzado').value = "";
            }
            function FiltroAvanzadoQuitar(e) {
                var valor = document.getElementById('Txt_valores_filtro').value;
                document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
                var vista = document.getElementById('Buscar_valores').innerHTML;
                var elim = "<div><input class=\"form-control\" value=\"" + e + "\" style=\"text-decoration:none;cursor:pointer;color:black;background:#d8dae9;\"><button type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\"><img src=\"Interfaz/Contenido/Imagenes/trash-can.png\" alt=\"Logo\" width=\"16\"></button></div><br>";
                document.getElementById('Buscar_valores').innerHTML = "";
                document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
            }
        </script>
        <script>
            function FiltroAvanzado() {
                var filtro = document.getElementById('Txt_filtro_avanzado2').value.replace("+", "");
                if (filtro !== "") {
                    document.getElementById('Txt_valores_filtro2').value += "[" + filtro + "]";
                    document.getElementById('Buscar_valores2').innerHTML += "<div><input class=\"form-control\" value='" + filtro + "' style='text-decoration:none;cursor:pointer;color:black;background:#d8dae9;'><button type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\"><img src=\"Interfaz/Contenido/Imagenes/trash-can.png\" alt=\"Logo\" width=\"16\"></button></div><br />";
                }
                document.getElementById('Txt_filtro_avanzado2').value = "";
            }
            function FiltroAvanzadoQuitar(e) {
                var valor = document.getElementById('Txt_valores_filtro2').value;
                document.getElementById('Txt_valores_filtro2').value = valor.replace("[" + e + "]", "");
                var vista = document.getElementById('Buscar_valores2').innerHTML;
                var elim = "<div><input class=\"form-control\" value=\"" + e + "\" style=\"text-decoration:none;cursor:pointer;color:black;background:#d8dae9;\"><button type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\"><img src=\"Interfaz/Contenido/Imagenes/trash-can.png\" alt=\"Logo\" width=\"16\"></button></div><br>";
                document.getElementById('Buscar_valores2').innerHTML = "";
                document.getElementById('Buscar_valores2').innerHTML = vista.replace("" + elim + "", "");
            }
        </script>
        <title>Orden | Registro Pesaje</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <ordenProduccion:Orden_pruduccion />
                <Resultado:ResultadosAlertas/>
            </div>
        </div>
    </body>
</html>
