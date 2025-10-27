<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_registro.tld" prefix="registro" %>
<%@taglib uri="/WEB-INF/tlds/tld_area.tld" prefix="area" %>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultados.tld" prefix="resultados" %>
<!DOCTYPE html>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registros</title>
        <link rel="stylesheet" href="Interfaz/Contenido/Css/tooltip.css" type="text/css">
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <style>
                #toggleR {
                    float: left;
                    max-width: 100%;
                    font-size: 14px;
                    position: absolute;
                    margin-left: 25px;
                }
                #toggleM {
                    float: left;
                    max-width: 100%;
                    font-size: 14px;
                    position: absolute;
                    margin-left: 25px;
                    top: 205px;
                    z-index: 2;
                }

                #toggleS {
                    float: right;
                    max-width: 100%;
                    font-size: 14px;
                    position: absolute;
                    margin:0 -350px;
                    z-index: 2;

                }
                #NavPosicion{
                    width: 70%;
                    margin-top: 1%;
                }
                .Contenerdor-radio{
                    display: flex;
                    margin-left: 48%;
                    margin-top: -3%;
                    margin-bottom: 1%;
                }
            </style>
        </head>

        <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <registro:Registro/>
        </div>

        <script type = "text/javascript" >
            history.pushState(null, null, 'registro.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'registro.jsp');
            });
            function confirmarAbrirRegistro(idRegistro, Temp1) {
                swal({
                    title: "Firmar!",
                    text: "¿Está seguro de abrir esta bitacora? Tenga en cuenta que si la bitacora ya tiene firma, esta firma sera eliminada",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = "Registro?op=14&est=1&idRegistro=" + idRegistro + "&Temp1=" + Temp1;
                });
            }
            function confirmarCerrarRegistro(idRegistro, Temp1) {
                swal({
                    title: "Firmar!",
                    text: "¿Está seguro de cerrar esta bitacora? Tenga en cuenta que una ves cerrada, no se podra abrir a excepción de un superior",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "green",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                }, function () {
                    location.href = "Registro?op=14&est=0&idRegistro=" + idRegistro + "&Temp1=" + Temp1;
                });
            }
            function MassiveId(ide) {
                var id = "[" + ide + "]";
                var cont = document.getElementById("idZ").value;
                if (cont.includes(id)) {
                    document.getElementById("idZ").value = cont.replace(id, "");
                } else {
                    document.getElementById("idZ").value += id;
                }
            }

            function MassiveIdModi(ideM) {
                var idM = "[" + ideM + "]";
                var contM = document.getElementById("idZmodi").value;
                if (contM.includes(idM)) {
                    document.getElementById("idZmodi").value = contM.replace(idM, "");
                } else {
                    document.getElementById("idZmodi").value += idM;
                }
            }

            function ZonaVacia() {
                var zonas = document.getElementById("idZmodi").value;
                if (zonas == "" || zonas == null) {
                    swal({
                        title: 'Información',
                        text: 'No se puede editar un registro sin zona, por favor escoja al menos una zona',
                        type: 'info',
                        timer: 2000,
                        showConfirmButton: false
                    });
                    return false;
                } else {
                    return true;
                }
            }

            function limiteregistros() {
                document.getElementById('form').submit();
            }

            function fechas() {
                var fecha1 = document.getElementById("start").value;
                document.getElementById("end").value = fecha1;
            }

            function cerrarmodicabecera() {
                var modalcabecera = document.getElementById("formmodi");
                modalcabecera.style.display = "none";
            }
        </script>




    <script src="Interfaz/Paginas/filtro.js" type="text/javascript"></script>
    <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
    <script src="Interfaz/Calendarios/Js_range.js" type="text/javascript"></script>
    <script src="Interfaz/Calendarios/Js_range_altenativo.js" type="text/javascript"></script>
    <resultados:MuestraResultados />
</body>
</html>