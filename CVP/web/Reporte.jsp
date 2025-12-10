<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Reporte.tld" prefix="Reporte"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/CVP.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Reportes</title>
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
                history.pushState(null, null, 'Reporte.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Reporte.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!--Post backs-->
                <script type="text/javascript">
                    function PostBackAnio() {
                        var anio = document.getElementById("Cbx_anio");
                        document.forms['FormAnio'].submit();
                    }
                </script>
        </head>
        <body id="subpage" >
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Reporte:Reporte/>
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>