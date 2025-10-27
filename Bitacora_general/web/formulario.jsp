<%@page contentType="text/html" pageEncoding="ISO-8859-1"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_formulario.tld" prefix="formulario" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general.ico" rel="icon" >
        <title>Formulario</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
        <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
        <script type="text/javascript">
            function mostrar(vv) {
                document.getElementById("selectDatos").innerHTML= "";
                document.getElementById("radio_div").innerHTML= "";
                document.getElementById("datos-idd").value = "";
                document.getElementById("datos-iddR").value = "";
                
                document.getElementById('dos_div').style.display=(vv.value=="Campo lista")?"none":"block";
                document.getElementById('datos-id').style.display=(vv.value=="Campo lista")?"block":"none";
                document.getElementById('lista').style.display=(vv.value=="Campo lista")?"block":"none";

                document.getElementById('dos_div').style.display=(vv.value=="Campo seleccion")?"none":"block";
                document.getElementById('radio_div').style.display=(vv.value=="Campo seleccion")?"block":"none";
                document.getElementById('datosR-id').style.display=(vv.value=="Campo seleccion")?"block":"none";
            }
        </script>
        <script type="text/javascript">
            function modificarC(vv) {
                document.getElementById("selectDatos").innerHTML= "";
                document.getElementById("radio_div").innerHTML= "";
                document.getElementById("datos-idd").value = "";
                document.getElementById("datos-iddR").value = "";

                document.getElementById('dos_div').style.display=(vv.value=="Campo lista")?"none":"block";
                document.getElementById('datos-id').style.display=(vv.value=="Campo lista")?"block":"none";
                document.getElementById('lista').style.display=(vv.value=="Campo lista")?"block":"none";

                document.getElementById('dos_div').style.display=(vv.value=="Campo seleccion")?"none":"block";
                document.getElementById('radio_div').style.display=(vv.value=="Campo seleccion")?"block":"none";
                document.getElementById('datosR-id').style.display=(vv.value=="Campo seleccion")?"block":"none";
            }
        </script>
        <script type="text/javascript" >
            function completar(obj){
                if(obj<=9){
                    compl = "0"+obj;
                }else{
                    compl = obj;
                }
                return compl;
            }
            function fecha(){
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
                document.getElementById("tmhora_id").value = horaCompleta;
                document.getElementById("fecha-id").value = fechaCompleta;
            }
        </script>
        <script type="text/javascript" language="javascript">
            function Agregar(){
                document.getElementById("selectDatos").innerHTML= "";
                var texto_datoss = document.getElementById("datos-idd");
                var textDS = texto_datoss.value.split("-");
                var lista=document.getElementById("selectDatos");
                for (var j = 0 ; j < textDS.length; j++){
                    lista.options.add(new Option(textDS[j]));
                }
            }
            function Eliminar(){
                var lista=document.getElementById("selectDatos");
                lista.options[lista.selectedIndex]=null;
            }
        </script>
        <script type="text/javascript" language="javascript">
            function AgregarR(){
                document.getElementById("radio_div").innerHTML= "";
                var texto_datossR = document.getElementById("datos-iddR");
                var textDSR = texto_datossR.value.split("-");
                for (var j = 0 ; j < textDSR.length; j++){
                    var x = document.createElement("INPUT");
                    var t = document.createElement("p");
                    x.setAttribute("type", "radio");
                    x.setAttribute("name", "textDSR");
                    x.setAttribute("value", textDSR[j]);
                    var text = document.createTextNode(textDSR[j]);
                    t.appendChild(text);
                    document.getElementById("radio_div").appendChild(x);
                    document.getElementById("radio_div").appendChild(text);
                }
            }
        </script>
        <script type = "text/javascript" >
            history.pushState(null, null, 'formulario.jsp');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, 'formulario.jsp');
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
    <body onload="fecha()" id="subpage">
        <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <formulario:MuestraFormulario />
        </div>
        <resultados:MuestraResultados />
        <script src="Calendarios/Js_range.js" type="text/javascript"></script>
        <script src="Calendarios/Js_normal.js" type="text/javascript"></script>
    </body>
</html>
