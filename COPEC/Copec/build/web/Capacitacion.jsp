<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Capacitacion.tld" prefix="Capacitacion"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Capacitacion</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Capacitacion.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Capacitacion.jsp');
                });
            </script>
            <script type="text/javascript">
                function alerta(tipoE) {
                    var campo = document.getElementById("alerta");
                    if (tipoE === 0) {
                        campo.innerHTML = "<b class='verde'>RECUERDE ACTUALIZAR <br/>LA VERSIÓN DEL DOCUMENTO</b>";
                    } else {
                        campo.innerHTML = "<b class='verde'>RECUERDE FINALIZAR <br/>LAS PRUEBAS YA REALIZADAS</b>";
                    }
                }
            </script>
            <script type="text/javascript">
                function seleccionar_todo() {
                    var campo = "";
                    form = document.forms["form_marcar_todos"]
                    for (i = 0; i < form.elements.length; i++) {
                        if (form.elements[i].type == "checkbox")
                            form.elements[i].checked = 0;
                    }
                    document.getElementById("id_idsP").value = "";
                    for (i = 0; i < form.elements.length; i++) {
                        if (form.elements[i].type == "checkbox") {
                            form.elements[i].checked = 1;
                            if (i === 0) {
                                campo = "[" + form.elements[i].value + "]";
                            } else {
                                campo = campo + "[" + form.elements[i].value + "]"
                            }
                        }
                    }
                    document.getElementById("id_idsP").value = campo;
                }
                function deseleccionar_todo() {
                    form = document.forms["form_marcar_todos"]
                    for (i = 0; i < form.elements.length; i++) {
                        if (form.elements[i].type == "checkbox")
                            form.elements[i].checked = 0;
                    }
                    document.getElementById("id_idsP").value = "";
                }
                function seleccionar_todoE() {
                    form = document.forms["form_marcar_todos"]
                    for (i = 0; i < form.elements.length; i++) {
                        if (form.elements[i].type == "checkbox")
                            form.elements[i].checked = 1;
                    }
                }
                function deseleccionar_todoE() {
                    form = document.forms["form_marcar_todos"]
                    for (i = 0; i < form.elements.length; i++) {
                        if (form.elements[i].type == "checkbox")
                            form.elements[i].checked = 0;
                    }
                }
                function asignar(id, acc) {
                    var campo = document.getElementById("id_idsP").value;
                    if (campo === "") {
                        document.getElementById("id_idsP").value = "[" + id + "]";
                        document.getElementById("id_idsP").value = "[" + id + "]";
                    } else {
                        if (campo.includes("[" + id + "]")) {
                            campo = campo.replace("[" + id + "]", "")
                            document.getElementById("id_idsP").value = campo;
                        } else {
                            document.getElementById("id_idsP").value = campo + "[" + id + "]";
                        }
                    }
                    if (acc === 1) {
                        document.form_marcar_todos.submit();
                    }
                }
            </script>
        </head>
        <body>
            <div id="subpage">
                <div id="templatemo_wrapper">
                <Menu:Menu/>
                <Capacitacion:Capacitacion/>
            </div>
            <Alertas:Alertas/>
            <script type="text/javascript" src="Interfaz/Tabs/tabs.js"></script>
            <script type="text/javascript" src="Interfaz/Calendarios/Js_normal.js"></script>
            <script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
            
        </div>
    </body>
</html>
