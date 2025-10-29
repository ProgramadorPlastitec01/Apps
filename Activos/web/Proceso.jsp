<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<%@taglib uri="/WEB-INF/tlds/Proceso.tld" prefix="proceso" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
        <jsp:include page="Contenedor_head.jsp"></jsp:include>
            <script type="text/javascript">
                history.pushState(null, null, 'Proceso.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Proceso.jsp');
                });
                function Estado(idProceso, est) {
                    swal({
                        title: "Estado!",
                        text: "El estado pasara a verificacion por Mtto general ¿Esta Seguro que Desea Continuar?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Proceso?opc=9&idProceso=" + idProceso + "&estado=" + est + "";
                            });
                }
                function Devolver(idProceso) {
                    swal({
                        title: "Devolver",
                        text: "El proceso se devolvera ¿Estas seguro?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Proceso?opc=7&idProceso=" + idProceso + "";
                            }
                    );
                }
                function noFinalizado(idProceso, est) {
                    swal({
                        title: "Justificar no Finalizado!",
                        text: "<form action='Proceso?opc=4&idProceso=" + idProceso + "&estado=" + est + "' id='formVerificacion' method='post'><textarea name='Txt_justificacion' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form><a href='Proceso?opc=1&idProceso=0#' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button><button type='submit' required  form='formVerificacion'>Enviar</button>",
                        type: "warning",
                        showConfirmButton: false,
                        html: true
                    });
                }
                function recibeActivo(activo) {
                    if (activo.checked) {
                        document.getElementById("activoUsado").value += "[" + activo.value + "]";
                    } else {
                        document.getElementById("activoUsado").value = document.getElementById("activoUsado").value.replace("[" + activo.value + "]", "");
                    }
                }
                function Liberar(idProceso, etd) {
                    swal({
                        title: "Liberar!",
                        text: "¿Seguro que Desea Liberar?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Proceso?opc=8&idProceso=" + idProceso + "&estado=" + etd;
                            });
                }
                function Filtrartodo() {
                    Filtrar();
                }
                function Abrir_img_pro(imgs) {
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
            <proceso:Proceso/>
            <div id="popUp2" class="modal2" onclick="javascript:document.getElementById('popUp2').style.display = 'none'">
                <img class="modal2-content" id="imgReq" style="width: auto; height: auto;"/>
            </div>
            <script src="Interfaz/Calendarios/Js_range.js" type="text/javascript"></script>
            <script src="Interfaz/Calendarios/Js_range_altenativo.js" type="text/javascript"></script>
            <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
        </div>
    </body>
</html>
