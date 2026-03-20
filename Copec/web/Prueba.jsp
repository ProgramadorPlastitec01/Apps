<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Prueba.tld" prefix="Prueba"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Prueba</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Prueba.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Prueba.jsp');
                });
            </script>
            <script type = "text/javascript" >
                function Consulta(id_prueba, tipo, filtro) {
                    if (filtro !== "" || filtro !== null){
                        location.href = 'Prueba?opc=1&idP=' + id_prueba + '&idPr=' + 0 + '&TpC=' + tipo + '&txt_bus=' + filtro + '';
                    }else{
                        location.href = 'Prueba?opc=1&idP=' + id_prueba + '&idPr=' + 0 + '&TpC=' + tipo + '&txt_bus=';
                    }
                }
                function RegistroPrg(id_pregunta) {
                    var ids = document.getElementById("idPrs").value;
                    if (ids === "") {
                        document.getElementById("idPrs").value = "[" + id_pregunta + "]";
                    } else {
                        if (ids.includes("[" + id_pregunta + "]")) {
                            ids = ids.replace("[" + id_pregunta + "]", "")
                            document.getElementById("idPrs").value = ids;
                        } else {
                            document.getElementById("idPrs").value = ids + "[" + id_pregunta + "]";
                        }
                    }
                    document.formPrg.submit();
                }
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
                        campo.setAttribute("style", "width:85%;max-width;");
                        campo.setAttribute("placeholder", "Opcion " + (cont + 1) + "");
                        campo.setAttribute("onchange", "javascript:this.value=this.value.toUpperCase();");
                        campo.setAttribute("required", "");
                        eliminar.setAttribute("id", "eliminar" + cont + "");
                        eliminar.setAttribute("style", "margin-top: 15px;float: right;");
                        eliminar.setAttribute("onclick", "EliminarP(" + cont + ")");
                        eliminar.innerHTML = "<img src='Interfaz/Template/images/Cancel-min.png' width='15' height='15'>";
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
                <Prueba:Prueba/>
            </div>
            <Alertas:Alertas/>
        </div>
    </body>
</html>

