<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Horometro.tld" prefix="Horometro"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/PMP_MI.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Horometros</title>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Horometro.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Horometro.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!--Marcar y desmarcar check-->
                <script type="text/javascript">
                    function seleccionar_todo() {
                        for (i = 0; i < document.f1.elements.length; i++) {
                            if (document.f1.elements[i].type == "checkbox") {
                                document.f1.elements[i].checked = 1
                            }
                        }
                    }
                    function deseleccionar_todo() {
                        for (i = 0; i < document.f1.elements.length; i++) {
                            if (document.f1.elements[i].type == "checkbox") {
                                document.f1.elements[i].checked = 0
                            }
                        }
                    }
                </script>
                <script type="text/javascript">
                    function PostBackAnio() {
                        var anio = document.getElementById("Cbx_anio");
                        document.forms['FormAnio'].submit();
                    }
                </script>
                <!--Posicionar-->
                <script type="text/javascript">
                    function Posicionar() {
                        document.getElementById(document.getElementById("Txt_pos").value).scrollIntoView(true);
                    }
                </script>
        </head>
        <body id="subpage" onload="Posicionar()">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Horometro:Horometro />
        </div>

        <script>
            function validHor(iter) {
                let nhr = parseFloat(document.getElementById("Txt_act_horometro" + iter).value);
                let ahr = parseFloat(document.getElementById("Txt_actual_horometro" + iter).value);
                let cal = nhr - ahr;
                if (nhr < ahr) {
                    swal({
                        title: "¡Atención!",
                        text: "El nuevo horomentro <b style='color:black;'>( " + nhr + " )</b> no puede ser menor al actual <b style='color:black;'>( " + ahr + " )</b> ",
                        type: "error",
                        showConfirmButton: true,
                        html: true,
                    });
                    document.getElementById("Txt_act_horometro" + iter).value = "";
                } else if (cal > 744) {
                    swal({
                        title: "¡Atención!",
                        text: "El nuevo horometro <b style='color:black;'>( " + nhr + " )</b> no puede ser mayor a 744 horas. <b style='color:black;'>Max ( " + (ahr + 744) + " )</b> ",
                        type: "error",
                        showConfirmButton: true,
                        html: true,
                    });
                    document.getElementById("Txt_act_horometro" + iter).value = "";
                } else {
                    document.getElementById("Form_horometro_" + iter).submit();
                }
            }
        </script>

        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>