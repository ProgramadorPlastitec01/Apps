<%@page contentType="text/html" pageEncoding="ISO-8859-1"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_novedad.tld" prefix="novedad" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <title>Novedades de maquina</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        <script type="text/javascript">
                function RegistroN() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function ModificarN() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
        <script type="text/javascript">
            function completar(obj){
                if(obj<=9){
                    compl = "0"+obj;
                }else{
                    compl = obj;
                }
                return compl;
            }
            function fechaN(){
                horasistema = new Date();
                hora= horasistema.getHours();
                minutos = horasistema.getMinutes();
                segundos = horasistema.getSeconds();
                anio = horasistema.getFullYear();
                mes = horasistema.getMonth()+1;
                dia = horasistema.getDate();
                horacompl = completar(hora);
                minutcomple = completar(minutos);
                segundocomple = completar(segundos);
                aniocomple = completar(anio);
                mescomplet = completar(mes);
                diacomplet = completar(dia);
                fechaCompleta = aniocomple +"-"+mescomplet+"-"+diacomplet;
                horaCompleta = horacompl +":"+minutcomple +":"+segundocomple;
                document.form1.txtfecha.value = fechaCompleta;
            }
        </script>
        <script type="text/javascript">
            function PostBackUbicacion(){
                var Ubicacion = document.getElementById("idU");
                document.forms['formUbicacion'].submit();
            }
        </script>
        <script type = "text/javascript" >
            history.pushState(null, null, 'novedad.jsp');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, 'novedad.jsp');
            });
        </script>
        <script type="text/javascript">
            var statsend = false;
            function checkSubmit(){
                if(!statsend){
                    statsend = true;
                    return true;
                }else{
                    alert(" Un momento por favor el formulario se esta enviando...");
                    return false;
                }
            }
        </script>
    </head>
    <body id="subpage" onload="fechaN()">
        <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <novedad:MuestraNovedad/>
        </div>
        <resultados:MuestraResultados />
    </body>
</html>
