<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_orden.tld" prefix="orden" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Orden</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function Alertdimensional() {
                    var parametro = document.getElementById("parametro-id").value;
                    var condicion = document.getElementById(parametro + "parametro-id").value;
                    var valor = document.getElementById("id-" + parametro).value;
                    var cond = "";
                    if (condicion === "<") {
                        cond = "Menores que";
                    } else {
                        cond = "Mayores que";
                    }
                    swal({
                        title: "Confirmar datos!",
                        text: "Usted va a modificar todos los datos dimensionales " + cond + " " + valor + " del parametro <b class='negro'>" + parametro + "</b>",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#009999",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                        closeOnCancel: false,
                        html: true
                    },
                            function (isConfirm) {
                                if (isConfirm) {
                                    swal({
                                        title: "Modificar datos " + parametro + "?",
                                        text: "Una vez modificados los datos no se podran restaurar.",
                                        type: "warning",
                                        showCancelButton: true,
                                        confirmButtonColor: "#009999",
                                        confirmButtonText: "Aceptar",
                                        cancelButtonText: "Cancelar",
                                        closeOnConfirm: false,
                                        closeOnCancel: false,
                                        html: true
                                    },
                                            function (isConfirm) {
                                                if (isConfirm) {
                                                    document.formModDimensional.submit();
                                                } else {
                                                    location.href = "Orden?opc=1&idO=0&txt_ficha=&txt_bus=";
                                                }
                                            });
                                } else {
                                    location.href = "Orden?opc=1&idO=0&txt_ficha=&txt_bus=";
                                }
                            });
                }
            </script>
            <script type="text/javascript">
                function registroO() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function MostrarPrmt(id) {
                    if (id === "y2") {
                        document.getElementById(id).style.display = "block";
                        document.getElementById("id-y2").required = true;
                    } else {
                        document.getElementById("y2").style.display = "none";
                        document.getElementById("id-y2").required = false;
                    }
                    if (id === "x1") {
                        document.getElementById(id).style.display = "block";
                        document.getElementById("id-x1").required = true;
                    } else {
                        document.getElementById("x1").style.display = "none";
                        document.getElementById("id-x1").required = false;
                    }
                    if (id === "y1") {
                        document.getElementById(id).style.display = "block";
                        document.getElementById("id-y1").required = true;
                    } else {
                        document.getElementById("y1").style.display = "none";
                        document.getElementById("id-y1").required = false;
                    }
                    if (id === "x3") {
                        document.getElementById(id).style.display = "block";
                        document.getElementById("id-x3").required = true;
                    } else {
                        document.getElementById("x3").style.display = "none";
                        document.getElementById("id-x3").required = false;
                    }
                }
                function validarDim(valor) {
                    var prmt = document.getElementById("parametro-id").value;
                    var media = document.getElementById("Val-" + prmt).value;
                    if (valor < media) {
                        document.getElementById(prmt + "parametro-id").value = '<';
                    } else {
                        document.getElementById(prmt + "parametro-id").value = '>';
                    }
                }
            </script>
            <script type="text/javascript">
                function VerSeguimiento(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=0&Sr=0&registro=6&txt_bus=0&PrF=0";
                }
                function envioVer(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=0&registro=0&Sr=0&txt_bus=";
                }
                function envioCuarentena(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=0&ver=0&registro=5&Sr=0&txt_bus=";
                }
                function habilitarEditar(orden) {
                    location.href = "Orden?opc=1&idO=" + orden + "&txt_ficha=&txt_bus=0";
                }
                function Volver() {
                    location.href = "Orden?opc=1&idO=0&txt_ficha=&txt_bus=";
                }
                function VolverD() {
                    location.href = "Orden?opc=1&idO=0&txt_ficha=&txt_bus=";
                }
            </script>
          
            <script type="text/javascript">
                function estado(est, idO, fil) {
                    swal({
                        title: "Seguro que desea cambiar el estado de la orden?",
                        text: "Cerrada",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#009999",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                        closeOnCancel: false,
                        html: true
                    },
                            function (isConfirm) {
                                if (isConfirm) {
                                    location.href = "Orden?opc=3&idO=" + idO + "&est=" + est + "&txt_bus=" + fil + "";
                                } else {
                                    location.href = "Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus=" + fil + "";
                                }
                            });
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <orden:MuestraOrden />
        </div>
        <resultados:MuestraResultados />
    </body>
</html>
