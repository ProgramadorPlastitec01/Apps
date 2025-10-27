<%@page contentType="text/html" pageEncoding="ISO-8859-1"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_novedad.tld" prefix="novedadMaquina" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <title>Novedades de maquina</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        <script type="text/javascript">
            function PostBackUbicacion(){
                var Ubicacion = document.getElementById("idA");
                document.forms['formUbicacion'].submit();
            }
        </script>
        <script type="text/javascript">
            $(function() {
                var dates = $( "#dtp_inicio, #dtp_fin" ).datepicker({
                    defaultDate: "0",
                    changeMonth: true,
                    numberOfMonths: 1,
                    onSelect: function( selectedDate ) {
                        var option = this.id == "dtp_inicio" ? "minDate" : "maxDate",
                        instance = $( this ).data( "datepicker" );
                        date = $.datepicker.parseDate(
                        instance.settings.dateFormat ||
                            $.datepicker._defaults.dateFormat,
                        selectedDate, instance.settings );
                        dates.not( this ).datepicker( "option", option, date );
                    }
                });
            });
        </script>
        <script type = "text/javascript" >
            history.pushState(null, null, 'novedad_Maquina.jsp');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, 'novedad_Maquina.jsp');
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
    <body id="subpage">
        <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <novedadMaquina:MuestraNovedadMaquina />
        </div>
        <resultados:MuestraResultados />
        <script src="Calendarios/Js_range.js" type="text/javascript"></script>
        <script src="Calendarios/Js_normal.js" type="text/javascript"></script>
    </body>
</html>

