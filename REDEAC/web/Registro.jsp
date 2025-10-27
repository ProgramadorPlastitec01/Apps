<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_registro.tld" prefix="registros"%>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html style="overflow-y: hidden">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Registro</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
            <script type="text/javascript" src='Interfaz/Contenido/Scripts/Registro_005.js'></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
            <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>


        </head>
        <body>

        <menu:MuestraMenu/>
        <div id="content">
            <registros:Registros/>
        </div>
        <resultado:MuestraResultado/>
        <!--    <script>
                $('select').selectpicker({
                    width: '188px'
                });
            </script>-->
        <script type="text/javascript">
            function RegistroE(equipo) {
                if (equipo.checked) {
                    document.getElementById("equipos").value += "[" + equipo.value + "]";
                } else {
                    document.getElementById("equipos").value = document.getElementById("equipos").value.replace("[" + equipo.value + "]", "");
                }
            }
            function ejecutar(idEP, idP) {
                swal({
                    title: "Ejecutar actividad!",
                    text: "<form action='Registro?opc=5&idEP=" + idEP + "&idP=" + idP + "' id='formEj' method='post'><textarea name='txt_observaciones' style='margin: 0px 0px 10px; height: 88px; width: 399px;' onchange='javascript:this.value=this.value.toUpperCase();' required></textarea><button type='submit'>Enviar</button></form>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true,
                });
            }
            function verificar(idEP, idP) {
                swal({
                    title: "Verificar actividad!",
                    text: "<form action='Registro?opc=6&idEP=" + idEP + "&idP=" + idP + "' id='formEj' method='post'><textarea name='txt_observaciones' style='margin: 0px 0px 10px; height: 88px; width: 399px;' onchange='javascript:this.value=this.value.toUpperCase();' required></textarea><button type='submit'>Enviar</button></form>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true,
                });
            }
            function EliminarE(idS, idP) {
                swal({
                    title: "Eliminar!",
                    text: "¿Seguro que desea eliminar el Equipo?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#5356ad",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function () {
                            location.href = 'Registro?opc=4&idEP=' + idS + '&idP=' + idP + '';
                        });
            }

            function TipoR(tipo) {
                if (tipo === "1") {
                    document.getElementById("divH").style.display = "block";
                    document.getElementById("divS").style.display = "none";
                } else {
                    document.getElementById("divS").style.display = "block";
                    document.getElementById("divH").style.display = "none";
                }
            }
            function RegistrarC(id) {
                var cop = document.getElementById("mes" + id);
                var meses = document.getElementById("mes-id").value;
                if (cop.checked == 1) {
                    document.getElementById("mes-id").value = meses + "[" + id + "]";
                } else {
                    document.getElementById("mes-id").value = meses.replace("[" + id + "]", "");
                }
            }
            function InabilitarRegistro(id_digi) {
                swal({
                    title: "Inabilitar",
                    text: "¿Seguro que Desea inabilitar el registro?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#6D256F",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function () {
                            location.href = "Registro?opc=13&idD=" + id_digi + "&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=";
                        });
            }
        </script>
        <script type="text/javascript">
            $(function borrarContent() {
                $('#borrar').remove();
            });
        </script>
        <script>
            function FiltroAvanzado() {
                var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
                if (filtro !== "") {
                    document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                    document.getElementById('Buscar_valores').innerHTML += "<div style=\"display: flex;\"><input class=\"form-control\" value='" + filtro + "' style='text-decoration:none;cursor:pointer;color:black;background:#d8dae9;pointer-events: none;'><a type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar('" + filtro + "')\"><img src=\"Interfaz/Fotos/trash-can.png\" alt=\"Logo\" width=\"16\"></a></div><br />";
                }
                document.getElementById('Txt_filtro_avanzado').value = "";
            }
            function FiltroAvanzadoQuitar(e) {
                var valor = document.getElementById('Txt_valores_filtro').value;
                document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
                var vista = document.getElementById('Buscar_valores').innerHTML;
                var elim = "<div style=\"display: flex;\"><input class=\"form-control\" value=\"" + e + "\" style=\"text-decoration:none;cursor:pointer;color:black;background:#d8dae9;pointer-events: none;\"><a type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar('" + e + "')\"><img src=\"Interfaz/Fotos/trash-can.png\" alt=\"Logo\" width=\"16\"></a></div><br>";
                document.getElementById('Buscar_valores').innerHTML = "";
                document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
            }
            function ElimUser(id){
                
            }
        </script>
        <script>
            function PasarDatos(ide) {
                var id = "[" + ide + "]";
                var content = document.getElementById("id_contenido").value;
                if (content.includes(id)) {
                    document.getElementById("id_contenido").value = content.replace(id, "");
                } else {
                    document.getElementById("id_contenido").value += id;
                }
            }
        </script>
        <script>
            function HabilitarCampos() {
                var ex = document.getElementById("user_ex");
                var inter = document.getElementById("user_in");
                var btn_ca = document.getElementById("btn_cambio");
                if (ex.className === "user_ex") {
                    ex.className = "user_ex_in";
                    inter.style.display = "none";
                    btn_ca.className = "btn_cambio";
                } else {
                    ex.className = "user_ex";
                    inter.style.display = "block";
                    btn_ca.className = "btn_in";
                }
            }
        </script>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
        <script>
            CKEDITOR.replace("editor");
        </script>

        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/Menu.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
        <script src="Interfaz/Firma/assets/bezier.js"></script>
        <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
        <script src="Interfaz/Firma/assets/json2.min.js"></script>
        <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
        <script src="Interfaz/Paginas/filtro.js"></script>
    </body>
</html>
