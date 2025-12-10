<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Reunion.tld" prefix="Reunion"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Reunion.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Reunion</title>
        <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
        <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
        <script type="text/javascript">
            function Informe() {
                var htmleditor = document.getElementsByName("HTML_Editor").innerHTML;
                document.getElementsByName("Txt_descripcion").value = htmleditor;
                document.Form_informe.submit();
            }

        </script>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Reunion.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Reunion.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script type="text/javascript">
                function SeleccionParticipes(ius){
                    if (ius.checked) {
                        document.getElementById('Txt_seleccion_participes').value += "" + ius.value;
                    } else {
                        document.getElementById("Txt_seleccion_participes").value = document.getElementById("Txt_seleccion_participes").value.replace(ius.value, "");
                    }
                }
                function SeleccionAreas(iar){
                    if (iar.checked) {
                        document.getElementById('Txt_seleccion_areas').value += "" + iar.value;
                    } else {
                        document.getElementById("Txt_seleccion_areas").value = document.getElementById("Txt_seleccion_areas").value.replace(iar.value, "");
                    }
                }
                function SeleccionResponsables(irp){
                    if (irp.checked) {
                        document.getElementById('Txt_seleccion_responsables').value += "" + irp.value;
                    } else {
                        document.getElementById("Txt_seleccion_responsables").value = document.getElementById("Txt_seleccion_responsables").value.replace(irp.value, "");
                    }
                    if (document.getElementById('Txt_seleccion_responsables').value.length > 0) {
                        document.getElementById("Btn_guardar_pendiente").style.display = "block";
                    } else {
                        document.getElementById("Btn_guardar_pendiente").style.display = "none";
                    }
                }
                function FiltroAvanzado(e){
                    tecla = (document.all) ? e.keyCode : e.which;
                    if (tecla === 43) {
                        document.getElementById('Txt_valores_filtro').value += document.getElementById('Txt_filtro_avanzado').value.replace("+", "+");
                        document.getElementById('Buscar_valores').innerHTML += document.getElementById('Txt_filtro_avanzado').value.replace("+", "") + "<br />";
                        document.getElementById('Txt_filtro_avanzado').value = '';
                    }
                }
            </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Reunion:Reunion/>
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>