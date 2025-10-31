<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/RegistroDetalle.tld" prefix="RegistroDetalle" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<%@page import="Tags.Tag_registroDetalle" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <title>Registro Detalle | Registro Pesaje</title>
        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'RegistroDetalle.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'RegistroDetalle.jsp');
                    });
                </script>-->
        <script src="Interfaz/Contenido/Scripts/jquery360.js"></script>
        <link rel="stylesheet" href="Interfaz/Contenido/select2/dist/css/select2.min.css">
        <script type=\"text/javascript\" src=\"Interfaz/Contenido/Graficas/js/highcharts_principal.js\"></script>
        <script src=\"Interfaz/Contenido/Graficas/js/highcharts.js\"></script>
        <script src=\"Interfaz/Contenido/Graficas/js/modules/exporting.js\"></script>

    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <RegistroDetalle:RegistroDetalle />
            </div>
        </div>

        <Resultado:ResultadosAlertas />
        <script>
            function FiltroAvanzado() {
                var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
                if (filtro !== "") {
                    document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                    document.getElementById('Buscar_valores').innerHTML += "<div><input class=\"form-control\" value='" + filtro + "' style='text-decoration:none;cursor:pointer;color:black;background:#d8dae9;'><button type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\"><img src=\"Interfaz/Contenido/Imagenes/trash-can.png\" alt=\"Logo\" width=\"16\"></button></div><br />";
                }
                document.getElementById('Txt_filtro_avanzado').value = "";
            }
            function FiltroAvanzadoQuitar(e) {
                var valor = document.getElementById('Txt_valores_filtro').value;
                document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
                var vista = document.getElementById('Buscar_valores').innerHTML;
                var elim = "<div><input class=\"form-control\" value=\"" + e + "\" style=\"text-decoration:none;cursor:pointer;color:black;background:#d8dae9;\"><button type=\"button\" class=\"btn btn-danger\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\"><img src=\"Interfaz/Contenido/Imagenes/trash-can.png\" alt=\"Logo\" width=\"16\"></button></div><br>";
                document.getElementById('Buscar_valores').innerHTML = "";
                document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
            }
            function MostrarCampo(valor) {
                if (document.getElementById(valor).style.display === "none") {
                    document.getElementById(valor).style.display = "block";
                } else if (document.getElementById(valor).style.display === "block") {
                    document.getElementById(valor).style.display = "none";
                }
            }
            function Masivo(ide) {
                var id = ide;
                var content = document.getElementById("Txt_ids").value;
                if (content.includes(id)) {
                    document.getElementById("Txt_ids").value = content.replace(ide, "");
                } else {
                    document.getElementById("Txt_ids").value += ide;
                }
            }
        </script>
        <script>
            function pasarDatos(id) {
                var ids = document.getElementById("cant_" + id + "").value;
                var decf = document.getElementById("id_def_" + id + "").value;
                document.getElementById("arm").value += "[" + ids + "/" + decf + "]";
                document.getElementById("cant_" + id + "").className = "borderdefc";
            }
        </script>
        <script>
            function pasarDatos_tiempo(id) {
                var ids = document.getElementById("time_" + id + "").value;
                var time = document.getElementById("id_time_" + id + "").value;
                document.getElementById("arm").value += "[" + ids + "/" + time + "]";
                document.getElementById("time_" + id + "").className = "borderdefc";
            }
        </script>
        <script>
            function pasarDetalles(ide) {
                var id = ide;
                var content = document.getElementById("txt_dll").value;
                if (content.includes(id)) {
                    document.getElementById("txt_dll").value = content.replace(ide, "");
                    document.getElementById("txt_dll3").value = content.replace(ide, "");
                    document.getElementById("btn_tiem").disabled = true;
                    document.getElementById("btn_obs").disabled = true;
                } else {
                    document.getElementById("txt_dll").value = ide;
                    document.getElementById("txt_dll3").value = ide;
                    document.getElementById("btn_tiem").disabled = false;
                    document.getElementById("btn_obs").disabled = false;
                }
            }
            function pasarDetallesGC(ide) {
                var id = ide;
                var content = document.getElementById("txt_dll3").value;
                if (content.includes(id)) {
                    document.getElementById("txt_dll3").value = content.replace(ide, "");
                    document.getElementById("btn_obs").disabled = true;
                } else {
                    document.getElementById("txt_dll3").value = ide;
                    document.getElementById("btn_obs").disabled = false;
                }
            }

        </script>
        <script>
            function pasarObs(hora, motivo, justificacion, pruebas, id) {
                var horas = hora;
                var motivos = motivo;
                var justifi = justificacion;
                var prueba = pruebas;
                var ide = id;
                var cont = document.getElementById("txt_comple").value;
                document.getElementById("txt_comple").value = cont.replace(prueba, "");
                document.getElementById("cbx_hora").value = horas;
                document.getElementById("txt_motivo").value = motivos;
                document.getElementById("txt_justifi").value = justifi;
                document.getElementById("btn_gro").value = ide;
                document.getElementById("obs_group").className = "obs_groupOut";
                document.getElementById("obs_group_" + ide + "").className = "obs_group2Out";
            }
        </script>
        <script>
            function activar_div(id) {
                var ide = id;
                document.getElementById("obs_group").className = "obs_group";
                document.getElementById("obs_group_" + ide + "").className = "obs_group2";
                document.getElementById("btn_gro").value = "";
            }
        </script>
        <script>
            function eject() {
                document.getElementById("form_hora").submit();
            }
        </script>
        <script>
            $(document).ready(function () {
                $("#valor1").keyup(function () {
                    var value = $(this).val();
                    $("#valor2").val(value);
                });
            });
        </script>

        <script>
            function passarBas(ide) {
                var id_dell = ide;
                document.getElementById("id_dell_bas").value = id_dell;
            }
        </script>
        <script>
            function openCont(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("cuanContContent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("cuaConLink");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
        </script>

        <script>
            function openTurnO(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("turnOcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("turnOlinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
        </script>

        <script>
            function openTurnW(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("turnWcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("turnWlinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
        </script>

        <script>
            function openTurnT(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("turnTcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("turnTlinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
        </script>
        <script>
            function openNew(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("Newcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("Newlinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
        </script>

        <script>
            function CloseForm(id) {
                document.getElementById("FormCierre" + id).submit();
            }
        </script>

        <script type="text/javascript">
            function EjecuteFirma(id_order, id_registro, id_controol, id_cuarent, idUserValid, event) {
                swal({
                    title: "Atencion!",
                    text: "¿Esta segur(a) de firmar esta cuarentena?.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#c9e433",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function () {
                            location.href = "Registro_detalle?opc=11&id_orden=" + id_order + "&id_registro=" + id_registro + "&id_contrl=" + id_controol + "&id_cuarent=" + id_cuarent + "&idUser=" + idUserValid + "&event=" + event + "";
                        }
                );
            }
        </script>
        <script>
            function mostrarPass() {
                var password = document.getElementById("txtPassword");
                var eye = document.getElementById("icon");
                if (password.type == "password") {
                    password.type = "text";
                    eye.className = "fas fa-eye-slash";
                } else {
                    password.type = "password";
                    eye.className = "fas fa-eye";
                }
            }
        </script>

        <script>
            document.getElementById('myInput').addEventListener('input', function (event) {
                const input = event.target;
                const value = input.value;
                const regex = /^[0-9cv-]*$/i;

                if (!regex.test(value)) {
                    input.value = value.slice(0, -1);
                }
            });
        </script>

        <script>
            const fileContent = document.getElementById('fileContent');
            const peso_force = document.getElementById('peso_force');
            const selectBascula = document.getElementById("selectBascula");
            let socket;

            selectBascula.addEventListener('change', function () {
                const basculaSeleccionada = selectBascula.value;
                if (socket) {
                    socket.close();
                }
                socket = new WebSocket("ws://localhost:8084/Registro_pesaje/filewatcher/" + basculaSeleccionada);

                socket.onopen = () => {
                    console.log('Conexión WebSocket abierta');
                };

                socket.onclose = (event) => {
                    if (event.wasClean) {
                        console.log(`La conexión WebSocket se cerró de forma limpia. Código de cierre: ${event.code}, Razón: ${event.reason}`);
                    } else {
                        console.error(`La conexión WebSocket se cerró de forma inesperada. Código de cierre: ${event.code}`);
                    }
                };

                socket.onerror = (error) => {
                    console.error('Error en la conexión WebSocket:', error);
                };

                socket.onmessage = (event) => {
                    console.log('Mensaje recibido del servidor WebSocket:', event.data);
                    const pesoValue = parseFloat(event.data);
                    if (!isNaN(pesoValue)) {
                        fileContent.value = event.data;
                        peso_force.value = event.data;
                        ReadData(pesoValue);
                    } else {
                        console.error('El valor recibido no es un número válido:', event.data);
                    }

                };
            });


            function ReadData(pesoValue) {
                var Peso_undEmpa = parseFloat(document.getElementById("Peso_undEmpa").value);
                var Peso_nominal = parseFloat(document.getElementById("Peso_nominal").value);
                var undEmpa = parseFloat(document.getElementById("undEmpa").value);
                var PesoMaq = parseFloat(document.getElementById("PesoMaq").value);
                var unds = parseFloat(document.getElementById("unds").value);
                var PesoMeta = document.getElementById("PesoMeta").value;

                var calc = pesoValue / Peso_undEmpa;
                calc = calc.toFixed(1);
                var und = (pesoValue * Peso_nominal / 1000);
                und = und.toFixed(1);
                document.getElementById("calculo").value = calc;
                document.getElementById("calculo2").value = und;
                if (und >= undEmpa) {
                    document.getElementById("bolsa_alert").style.display = 'block';
                } else {
                    document.getElementById("bolsa_alert").style.display = 'none';
                }
                PesoMaq = PesoMaq + pesoValue;
                if (PesoMaq >= PesoMeta) {
                    document.getElementById("form_hora1").submit();
                } else {
                }
            }

            selectBascula.dispatchEvent(new Event('change'));

        </script>

        <script>
            $(document).ready(function () {
                // Inicializar Select2
                $('#selectPersonal').select2({
                    placeholder: 'Selecciona una opción',
                    allowClear: true
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                // Inicializar Select2
                $('#selectPersonal2').select2({
                    placeholder: 'Selecciona una opción',
                    allowClear: true
                });
            });
        </script>
        <script>
            function validarInput(input) {
                // Obtenemos el valor del input
                const valor = input.value;
                // Comprobamos si el valor es un número
                if (!isNaN(valor)) {
                    // Comprobamos si el valor es mayor o igual a 0
                    if (valor <= 0) {
                        // Si el valor es menor que 0, lo ponemos a vacío
                        input.value = "0";
                    }
                } else {
                    // Si el valor no es un número, lo ponemos a vacío
                    input.value = "0";
                }
            }
        </script>
        <script>
            function HabilitarDivLimpieza(valid) {
                if (valid === 1) {
                    document.getElementById("LimHora").style.display = 'block';
                    document.getElementById("LimGeneral").style.display = 'none';
                } else {
                    document.getElementById("LimGeneral").style.display = 'block';
                    document.getElementById("LimHora").style.display = 'none';
                }
            }
        </script>


        <script src="Interfaz/Contenido/Scripts/highcharts.js"></script>
        <script src="Interfaz/Contenido/Scripts/highcharts-more.js"></script>
        <script src="Interfaz/Contenido/Scripts/solid-gauge.js"></script>
        <script src="Interfaz/Contenido/Scripts/LeerDatos.js"></script>
        <script src="Interfaz/Contenido/select2/dist/js/select2.full.min.js"></script>


    </body>
</html>
