<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Activo.tld" prefix="activo" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <script type="text/javascript">
                history.pushState(null, null, 'Activo.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Activo.jsp');
                });

                function Registrar_adicion(id_adicion) {
                    swal({
                        title: "Registro!",
                        text: "La adicion se ha registrado correctamente",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = 'Activo?opc=7&idActivo=' + id_adicion + '';
                            });
                }
                function DesactivarActivo(idActivo) {
                    swal({
                        title: "Desactivar!",
                        text: "¿Seguro que desea cambiar de Estado?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = 'Activo?opc=4&idActivo=' + idActivo + '';
                            });
                }
                function ActivarActivo(idActivo, estado) {
                    swal({
                        title: "Justificar Activación!",
                        text: "<form action='Activo?opc=5&idActivo=" + idActivo + "&estado=" + estado + "' id='formVerificacion' method='post'><textarea name='Txt_justificacion' style='margin: 0px 0px 10px; width: 328px; height: 82px;'></textarea></form><a href='Activo?opc=1&consultaEstado=" + estado + "&query=#' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a><button type='submit' required form='formVerificacion'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        html: true,
                    });
                }
                function DarBajaActivo(idActivo) {
                    swal({
                        title: "Justificar Dada de Baja!",
                        text: "<form action='Activo?opc=6&idActivo=" + idActivo + "' id='formVerificacion' method='post'><textarea name='Txt_justificacion' style='margin: 0px 0px 10px; width: 328px; height: 82px;'></textarea></form><a href='Activo?opc=1&idActivo=0&query=' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a><button type='submit' required form='formVerificacion'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        html: true,
                    });
                }
                function SeleccionCampos(cmp) {
                    if (cmp.checked) {
                        document.getElementById('Txt_filtro_campos').value += "" + cmp.value;
                    } else {
                        document.getElementById("Txt_filtro_campos").value = document.getElementById("Txt_filtro_campos").value.replace(cmp.value, "");
                    }
                }
                function SeleccionFechas(tfc) {
                    document.getElementById('Txt_filtro_fecha').value = "" + tfc;
                }
                function getdestinos() {
                    var destinos;

                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            //document.getElementById("get").innerHTML = this.responseText;
                            destinos = this.responseText;
                        }
                    };
                    xhttp.open("GET", "getdestinos.php", true);
                    xhttp.send();
                }
            </script>
            <script type="text/javascript">
                function seleccion() {
                    var opcion = document.getElementById('Cbx_activo').options[document.getElementById('Cbx_activo').selectedIndex].text;
                    var dato = opcion.split("/");
                    document.getElementById("Txt_codigo").value = dato[1];
                    document.getElementById("Txt_nombre_equipo").value = dato[0];
                }

                function FiltroAvanzado(e) {
                    tecla = (document.all) ? e.keyCode : e.which;
                    if (tecla === 43) {
                        var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
                        if (filtro !== "") {
                            document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                            document.getElementById('Buscar_valores').innerHTML += "<a href='#' onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\" style='text-decoration:none'>" + filtro + "</a><br />";
                        }
                        document.getElementById('Txt_filtro_avanzado').value = "";
                    }
                }
                function FiltroAvanzadoQuitar(e) {
                    var valor = document.getElementById('Txt_valores_filtro').value;
                    document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
                    var vista = document.getElementById('Buscar_valores').innerHTML;
                    var elim = "<a href=\"#\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\" style=\"text-decoration:none\">" + e + "</a><br>";
                    document.getElementById('Buscar_valores').innerHTML = "";
                    document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
                }
                function Filtrartodo() {
                    Filtrar();
                }
                function Abrir_img_act(imgs) {
                    var img = document.getElementById(imgs);
                    var modal = document.getElementById('popUp2');
                    var modalImg = document.getElementById("imgReq");
                    modal.style.display = "block";
                    modalImg.src = img.src;
                }
            </script>

        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:Menu/>
            <alertas:Alertas/>
            <activo:Activo/>
            <div id="popUp2" class="modal2" onclick="javascript:document.getElementById('popUp2').style.display = 'none'">
                <img class="modal2-content" id="imgReq" style="width: auto; height: auto;"/>
            </div>
        </div>
        <script src="Interfaz/Calendarios/Js_range.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
    </body>
</html>
