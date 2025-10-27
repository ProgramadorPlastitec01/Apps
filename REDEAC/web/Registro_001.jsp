<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_registro001.tld" prefix="registro_001" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<%--<%@ page contentType="text/html; charset=UTF-8" %>--%>
<%--<%@page pageEncoding="UTF-8"%>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
                <script type="text/javascript" src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>-->
        <script type="text/javascript" src="Interfaz/Paginas/paging_2.js"></script>
        <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
        <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
        <link type="text/css" href="Interfaz/Contenido/Css/modal_01.css" rel="stylesheet">
        <link type="text/css" href="Interfaz/FontAwesome/css/all.css" rel="stylesheet">
        <!--<script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>-->
        <script src="Interfaz/Contenido/Scripts/Ajax.js"></script>

        <title>Registro 001</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        </head>
        <body>
        <menu:MuestraMenu />
        <div id="content">
            <registro_001:Registro_001 />
        </div>
        <resultado:MuestraResultado />
        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
        </script>
        <script>
            function mostrarCampos(dato) {
                if (dato == 1) {
                    if (document.getElementById("modal1").style.display === "none") {
                        document.getElementById("modal1").style.display = "block";
                        document.getElementById("modal2").style.display = "none";
                    } else if (document.getElementById("modal1").style.display === "block") {
                        document.getElementById("modal1").style.display = "none";
                        document.getElementById("modal2").style.display = "block";
                    }
                } else if (dato == 2) {
                    if (document.getElementById("modal2").style.display === "none") {
                        document.getElementById("modal2").style.display = "block";
                        document.getElementById("modal1").style.display = "none";
                    } else if (document.getElementById("modal2").style.display === "block") {
                        document.getElementById("modal2").style.display = "none";
                        document.getElementById("modal1").style.display = "block";
                    }
                }
            }
        </script>
        <script>
            function bloquearCampo() {
                var user = document.getElementById("Txt_filtro_avanzado");
                user.disabled = "true";
            }
        </script>
        <script>
            $('select').selectpicker({
                width: '196px'
            }
            );
        </script>
        <script>
            function EnviarDatos(ide) {
                var id = ide;
                var content = document.getElementById("Txt_ids").value;
                var btn_firm = document.getElementById("btn_firma");
                var btn_edit = document.getElementById("btn_edit");
                var botones = document.getElementById("cont_botones");
                if (content.includes(id)) {
                    document.getElementById("Txt_ids").value = content.replace(id, "");
                    document.getElementById("Txt_ids2").value = content.replace(id, "");
                    btn_edit.className = "btn_act2";
                    btn_firm.className = "btn_act2";
                    botones.className = "botones";
                } else {
                    document.getElementById("Txt_ids").value = id;
                    document.getElementById("Txt_ids2").value = id;
                    btn_edit.className = "btn_act2";
                    btn_firm.className = "btn_act2";
                    botones.className = "botones";
                }
            }
        </script>
        <script>
            function EnviarForm() {
                document.getElementById("form_anio").submit();
            }
        </script>
        <script>
            CKEDITOR.replace("editor");
        </script>
        <script>
            function fechas(nro) {
                var nro = nro;
                var now = moment().format("YYYY-MM-DDTHH:mm");
                document.getElementById("fechas_" + nro + "").value = now;
            }
        </script>

        <script src="Interfaz/Contenido/Scripts/Filtro.js"></script>
        <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
        <script src="Interfaz/Firma/assets/bezier.js"></script>
        <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
        <script src="Interfaz/Firma/assets/json2.min.js"></script>
        <!--<script src="Interfaz/Contenido/Scripts/"></script>-->
    </body>
</html>
