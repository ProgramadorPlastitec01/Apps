<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/menuTLD.tld" prefix="menu"%>
<%@taglib uri="/WEB-INF/tlds/CoordinadoresTLD.tld" prefix="Actividad"%>
<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type = "text/javascript" >
                history.pushState(null, null, 'R_GC_079.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Bitacora.jsp');
                });
            </script>
            <script type="text/javascript">
                function FinalizarC(Id, estado) {
                    swal({
                        title: "Finalizar!",
                        text: "Seguro que desea Finalizar, una vez finalizado no se podra modificar ni abrir?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "R_GC_079?opc=4&Id_Actividad=" + Id + "&est=" + estado + "";
                            });
                }
                function FinalizarA(Id, estado) {
                    swal({
                        title: "Finalizar!",
                        text: "Seguro que desea Finalizar?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "R_GC_079?opc=4&Id_Actividad=" + Id + "&est=" + estado + "";
                            });
                }
                function AbrirA(Id, estado) {
                    swal({
                        title: "Abrir!",
                        text: "Seguro que desea Abrir la Actividad?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "R_GC_079?opc=4&Id_Actividad=" + Id + "&est=" + estado + "";
                            });
                }
            </script>
            <script type="text/javascript">
                function registroR() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
            </script>
            <script type="text/javascript">
                function RevisarA(Ra) {
                    swal({
                        title: "Revisar!",
                        text: "Seguro que desea Revisar?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "R_GC_079?opc=5&Id_Actividad=" + Ra + "";
                            });
                }
            </script>
            <script type="text/javascript" >
                function completar(obj) {
                    if (obj <= 9) {
                        compl = "0" + obj;
                    } else {
                        compl = obj;
                    }
                    return compl;
                }
                function fecha() {
                    horasistema = new Date();
                    hora = horasistema.getHours();
                    minutos = horasistema.getMinutes();
                    segundos = horasistema.getSeconds();
                    anio = horasistema.getFullYear();
                    mes = horasistema.getMonth() + 1;
                    dia = horasistema.getDate();
                    horacompl = completar(hora);
                    minutcomple = completar(minutos);
                    segundocomple = completar(segundos);
                    aniocomple = completar(anio);
                    mescomplet = completar(mes);
                    diacomplet = completar(dia);
                    fechaCompleta = aniocomple + "/" + mescomplet + "/" + diacomplet;
                    horaCompleta = horacompl + ":" + minutcomple + ":" + segundocomple;
                    document.form1.txthora.value = horaCompleta;
                    document.form1.txtfecha.value = fechaCompleta;
                }
            </script>
        </head>
        <body onload="fecha()" id="subpage">
            <div id="templatemo_wrapper">
            <menu:menu></menu:menu>
            <Actividad:MostrarActividad></Actividad:MostrarActividad>
            </div>
        <resultados:Resultados></resultados:Resultados>
        <script src="Calendarios/Js_range.js" type="text/javascript"></script>
        <script src="Calendarios/Js_normal.js" type="text/javascript"></script>
        <script src="Interfaz/Acordeon/Js_accordeon.js"></script>
    </body>
</html>
