<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_turno.tld" prefix="Turnos" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Turno</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script type="text/javascript">
                function registroT() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
                function ControlC() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
                function registroTomas() {
                    document.getElementById("btsubmit").disabled = true;
                    document.getElementById("btsubmit").value = "";
                    document.getElementById("puntos").style.display = "block";
                }
                function seleccionarS(serial, check) {
                    var campo = document.getElementById("seriales-id").value;
                    if (campo === "") {
                        document.getElementById("seriales-id").value = serial;
                    } else {
                        if (check.checked) {
                            document.getElementById("seriales-id").value = campo + serial;
                        } else {
                            campo = campo.replace(serial, "");
                            document.getElementById("seriales-id").value = campo;
                        }
                    }
                }
            </script>
            <script type="text/javascript">
                function Ver(orden, turno) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + turno + "&registro=" + 2 + "&txt_bus=0";
                }
                function VerSeguimiento(orden, turno) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + turno + "&registro=" + 2 + "&txt_bus=0";
                }
                function Seriales(orden, turno) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + turno + "&Sr=" + 1 + "&txt_bus=0&registro=" + 0 + "";
                }
                function EditarTurno(orden, turno) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + turno + "&Sr=" + 0 + "&txt_bus=0&registro=" + 0 + "";
                }
                function Tomas(orden, turno) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + turno + "&registro=" + 1 + "&txt_bus=0&filtro=0";
                }
                function Nota(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=0&PrF=" + 1 + "";
                }
                function Seguimiento(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 6 + "&txt_bus=0&PrF=" + 0 + "";
                }
                function registroDespeje(turno) {
                    location.href = "javascript:window.open('Turno?opc=15&idD=" + turno + "','','width=1024,height=650,left=50,top=50,toolbar=yes');void 0";
                }
                function Turno_Abierto(orden, turno) {
                    location.href = "Turno?opc=9&idO=" + orden + "&idT=" + turno + "&txt_bus=" + 0 + "&est=cerrado";
                }
                function Turno_Cerrado(orden, turno) {
                    location.href = "Turno?opc=9&idO=" + orden + "&idT=" + turno + "&txt_bus=" + 0 + "&est=abierto";
                }
                function Aprobar(orden, turno) {
                    location.href = "Turno?opc=8&idO=" + orden + "&idT=" + turno + "&est=" + 1 + "&txt_bus=" + 0 + "";
                }
                function Cerrar(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&txt_bus=" + 0 + "&registro=" + 0 + "";
                }
                function CerrarPFuncional(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=0&Sr=0&registro=0&txt_bus=&PrF=1";
                }
                function CerrarPFuncional1(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&txt_bus=&registro=" + 0 + "";
                }
                function CerrarPFuncionalSeguimiento(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&txt_bus=" + 0 + "&registro=" + 6 + "";
                }
                function ModificarTurno(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=0";
                }
                function CerrarModificarT(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus=0";
                }
                function CerrarCuarentena(orden) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=0&ver=0&registro=5&Sr=0&txt_bus=";
                }
                function ConsultarControlDCuarentena(orden, turno) {
                    location.href = "Turno?opc=1&idO=" + orden + "&idT=" + turno + "&ver=1&registro=5&txt_bus=";
                }
            </script>
            <script type="text/javascript">
                function PruebaFuncionalCo() {
                    FormPFCo.submit();
                }
                function MasivoTurno(ide) {
                    var id = "[" + ide + "]";
                    var cont = document.getElementById("txt_reg_turno").value;
                    if (cont.includes(id)) {
                        document.getElementById("txt_reg_turno").value = cont.replace(id, "");
                    } else {
                        document.getElementById("txt_reg_turno").value += id;
                    }
                }
                function Cuarentena() {
                    FormCuarentena.submit();
                }
                function Masivo(ide) {
                    var id = "[" + ide + "]";
                    var content = document.getElementById("txt_arg_cuarentena").value;
                    if (content.includes(id)) {
                        document.getElementById("txt_arg_cuarentena").value = content.replace(id, "");
                    } else {
                        document.getElementById("txt_arg_cuarentena").value += id;
                    }
                }
            </script>
            <script type="text/javascript">
                function SinRegistro() {
                    swal({
                        title: "Error",
                        text: "No se han registrado ninguna toma",
                        type: "error",
                    })
                }
            </script>
            <script type="text/javascript" language="javascript">
                function NoEspacios(e, campo) {
                    var valor = e.split(" ");
                    valor = valor.join("");
                    campo.value = valor;
                }

            </script>
            <script>
                $(document).ready(function () {
                    $('.tooltip').tooltipster({
                        contentCloning: false,
                        theme: 'tooltipster-Shadow',
                        side: 'bottom'
                                //side:'top',
                                //side:'right',
                                //side:'left',
                    });
                });
            </script>
            <script>
                function Habilitar() {
                    var camp = document.getElementsByName("txt_x3");
                    var button = document.getElementsByName("Cbx_validar");
                    if (camp != null && camp != '') {
                        button.disabled = true;
                    } else {
                        button.disabled = false;
                    }
                }
            </script>
            <script type='text/javascript'>
                function validacion(cp, mn, my, pr, cv) {
                    var objv = document.getElementById(cp);
                    var arg_datos = document.getElementById('Arg_datos' + cv);
                    var int = parseFloat(arg_datos.value);
                    var vari_c = objv.value;
                    vari_c = vari_c.trim();
                    var vari = parseFloat(vari_c);
                    var max = parseFloat(my);
                    var min = parseFloat(mn);
                    if (vari <= max && vari >= min) {
                        objv.value = vari;
                        objv.style.backgroundColor = '#97FF97';
                        arg_datos.value = parseFloat(int + 1);
                    } else {
                        var redondeo = vari.toFixed(2);
                        if (redondeo <= max && redondeo >= min) {
                            objv.value = redondeo;
                            objv.style.backgroundColor = '#97FF97';
                            arg_datos.value = parseFloat(int + 1);
                        } else {
                            objv.value = vari;
                            objv.style.backgroundColor = '#FF6363';
                            arg_datos.value = parseFloat(int + 1);
                        }
                    }
                    var verificar = document.getElementById('DVerificar' + cv);
                    if (arg_datos.value > 4) {
                        verificar.style.display = 'block';
                    } else {
                        verificar.style.display = 'none';
                    }
                    if (pr === 'Sin') {
                    } else {
                        var prox = document.getElementById(pr);
                        prox.focus();
                    }
                }
            </script>
        </head>
        <body id="subpage" onload="time()">
            <div id="templatemo_wrapper">
            <menu:MuestraMenu />
            <Turnos:Turno />
            </div>
        <resultados:MuestraResultados />
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script type="text/javascript" src="Interfaz/Tabs/tabs.js"></script>
    </body>
</html>
