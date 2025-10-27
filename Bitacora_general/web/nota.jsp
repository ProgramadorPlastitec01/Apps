<%@page contentType="text/html" pageEncoding="ISO-8859-1"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/tlds/tld_nota.tld" prefix="nota" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <title>Nota</title>
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
        <script type = "text/javascript" >
            history.pushState(null, null, 'nota.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'nota.jsp');
            });
        </script>
        <script type="text/javascript">
            var statsend = false;
            function checkSubmit() {
                if (!statsend) {
                    statsend = true;
                    return true;
                } else {
                    alert(" Un momento por favor el formulario se esta enviando...");
                    return false;
                }
            }
        </script>
        <script type="text/javascript">
            function EnviarNota(SeIdarea, nota) {
                swal({
                    title: "Enviar!",
                    text: "Seguro que desea Enviar?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#666666",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function () {
                            location.href = "Nota?op=6&idA=" + SeIdarea + "&idN=" + nota + "&Env=" + 1 + "&txt_bus=";
                        });
            }
        </script>
    </head>
    <body id="subpage">
        <div id="templatemo_wrapper">
        <menu:MuestraMenu />
        <nota:MuestraNota />
    </div>
    <resultados:MuestraResultados />
    </body>
</html>

