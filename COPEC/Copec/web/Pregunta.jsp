<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Pregunta.tld" prefix="Pregunta"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Pregunta</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Pregunta.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Pregunta.jsp');
                });
            </script>
            <script type="text/javascript">
                function AñadirP() {
                    var cont = parseInt(document.getElementById("cont").value);
                    if (cont <= 3) {
                        var div = document.getElementById("opciones");
                        var campo = document.createElement("textarea");
                        var eliminar = document.createElement("a");
                        campo.setAttribute("name", "txt_opcion" + cont + "");
                        campo.setAttribute("id", "opcion" + cont + "-id");
                        campo.setAttribute("style", "width:90%;");
                        campo.setAttribute("placeholder", "Opcion " + (cont + 1) + "");
                        campo.setAttribute("onchange", "javascript:this.value=this.value.toUpperCase();");
                        campo.setAttribute("required", "");
                        eliminar.setAttribute("id", "eliminar" + cont + "");
                        eliminar.setAttribute("style", "margin-top: 10px;float: right;");
                        eliminar.setAttribute("onclick", "EliminarP(" + cont + ")");
                        eliminar.innerHTML = "<img src='Interfaz/Template/images/Cancel-min.png' width='22' height='22'>";
                        document.getElementById("cont").value = (cont + 1);
                        div.appendChild(campo);
                        div.appendChild(eliminar);
                        if (cont >= 1) {
                            document.getElementById("eliminar" + (cont - 1) + "").removeAttribute("onclick");
                        }
                    }
                }
                function EliminarP(id) {
                    var cont = parseInt(document.getElementById("cont").value);
                    var div = document.getElementById("opciones");
                    div.removeChild(document.getElementById("opcion" + id + "-id"));
                    div.removeChild(document.getElementById("eliminar" + id + ""));
                    document.getElementById("cont").value = (cont - 1);
                    if (cont > 1) {
                        document.getElementById("eliminar" + (cont - 2) + "").setAttribute("onclick", "EliminarP(" + (cont - 2) + ")");
                    }
                }
            </script>
        </head>
        <body>
            <div id="subpage">
                <div id="templatemo_wrapper">
                <Menu:Menu/>
                <Pregunta:Pregunta />
            </div>
            <Alertas:Alertas/>
        </div>
    </body>
</html>
