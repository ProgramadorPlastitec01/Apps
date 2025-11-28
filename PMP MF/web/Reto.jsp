<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/Tlds/Reto.tld" prefix="Reto" %>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type = "text/javascript" >
            history.pushState(null, null, 'Reto.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Reto.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <title>R-MTF-052</title>
        </head>
        <body id="page-top"  >
            <div id="wrapper">
            <Menu:Menu />
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <Menu:Sesion />
                    <div class="container-fluid">
                        <Reto:Reto/>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function EnviarLoteId(IdReto) {
                document.getElementById("IdRetoP").value = IdReto;
                document.getElementById("FormMttoS").submit();
            }
            function EnviarFirmaProduccion(Id) {
                document.getElementById("IdRetoFirma").value = Id;
            }
            function IdModElMaquina(Id) {
                document.getElementById("IdMaquina").value = Id;
                document.getElementById("IdDetalle").value = Id;
            }
            function IdRetoEliminar(Id){
                document.getElementById("IdRetoElimnar").value = Id;
            }
            function CambioDivRegistro() {
                var rg1 = document.getElementById("RG1");
                var rg2 = document.getElementById("RG2");

                if (rg1.style.display === "none" || rg1.style.display === "") {
                    rg1.style.display = "block";
                    rg2.style.display = "none";
                } else {
                    rg1.style.display = "none";
                    rg2.style.display = "block";
                }
            }

            function ValidarInputMaquina() {
                const InputMaquina = document.getElementById("IdMaquina").value;
                if (InputMaquina.length > 0) {
                    FormEnvMod.submit();
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar un registro, para modificar",
                        type: "info",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function ActivarDiv() {
                const div1 = document.getElementById("DivCab1");
                const div2 = document.getElementById("DivCab2");
                const boton = document.getElementById("btnModificar");

                if (div1.style.display === "block") {
                    div1.style.display = "none";
                    div2.style.display = "block";
                    boton.innerText = "Modificar Cabecera";
                } else {
                    div1.style.display = "block";
                    div2.style.display = "none";
                    boton.innerText = "Modificar Reto";
                }
            }

            function InactivarDetalle() {
                const InputDetalle = document.getElementById("IdDetalle").value;
                if (InputDetalle.length > 0) {
                    swal({
                        title: "¡Confirmar!",
                        text: "¿Esta seguro que desea eliminar el item?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#106eff",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormEliminar').submit();
                            });
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar un registro, para eliminar",
                        type: "info",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
            function EliminarReto() {
                const InputDetalle = document.getElementById("IdRetoElimnar").value;
                if (InputDetalle.length > 0) {
                    swal({
                        title: "¡Confirmar!",
                        text: "¿Esta seguro que desea eliminar el item?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#106eff",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                document.getElementById('FormEliminarReto').submit();
                            });
                } else {
                    swal({
                        title: "Alerta!",
                        text: "Debe seleccionar un registro, para eliminar",
                        type: "info",
                        confirmButtonText: "Aceptar",
                        html: true
                    });
                }
            }
        </script>

        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
            function ContinuarReg(num) {
                document.getElementById("Validacion").value = num;
            }

            function ArregloRetosMasivo(Cant) {
                // Validar Turno
                const turno = document.getElementById("TurnoVal");
                if (turno.value === "0") {
                    swal({
                        title: "Alerta",
                        text: "Por favor selecciona un Turno.",
                        type: "warning",
                        timer: 2000,
                        showConfirmButton: false
                    });
                    setTimeout(() => turno.focus(), 2100);
                    return false;
                }

                // Validar Maquina
                const maquina = document.getElementById("Cbx_maquina2");
                if (maquina.value === "0") {
                    swal({
                        title: "Alerta",
                        text: "Por favor selecciona una Máquina.",
                        type: "warning",
                        timer: 2000,
                        showConfirmButton: false
                    });
                    setTimeout(() => maquina.focus(), 2100);
                    return false;
                }

                // Validar Lote
                const lote = document.getElementById("lote2");
                if (lote.value.trim() === "") {
                    swal({
                        title: "Alerta",
                        text: "Por favor ingresa el Lote.",
                        type: "warning",
                        timer: 2000,
                        showConfirmButton: false
                    });
                    setTimeout(() => lote.focus(), 2100);
                    return false;
                }

                // Validar retos
                for (let i = 1; i <= Cant; i++) {
                    const Reto = document.getElementById("LoteArg" + i).value.trim();
                    let ObservacionInput = document.getElementById("Observacion" + i);
                    let Observacion = ObservacionInput.value.trim();
                    if (Observacion === "") {
                        Observacion = "NA";
                    }

                    const ResultadoFinal = document.getElementById("ArgReto" + i);
                    const estado1 = document.getElementById("Estado1_" + i);
                    const estado2 = document.getElementById("Estado2_" + i);

                    let Estado = 0;

                    if (estado1.checked) {
                        Estado = 1;
                    } else if (estado2.checked) {
                        Estado = 2;
                    } else {
                        swal({
                            title: "Alerta!",
                            text: "Por favor selecciona SI o NO en el reto #" + i,
                            type: "info",
                            timer: 2000,
                            showConfirmButton: false
                        });
                        setTimeout(() => {
                            estado1.closest(".selectgroup2").scrollIntoView({behavior: "smooth"});
                            estado1.focus(); // o estado2, cualquiera sirve para fijar foco en el grupo
                        }, 2100);
                        return false;
                    }

                    const Resultado = "[" + Reto + "///" + Estado + "///" + Observacion + "]";
                    ResultadoFinal.value = Resultado;
                }

                return true;
            }

            function sugerirObservacion(indice) {
                const observacion = document.getElementById("Observacion" + indice);

                swal({
                    title: "Observación sugerida",
                    text: "Si seleccionaste NO, sería ideal que agregues una observación.",
                    type: "info",
                    timer: 2000,
                    showConfirmButton: false
                });

                // Esperamos a que el swal desaparezca y luego damos foco
                setTimeout(function () {
                    observacion.focus();
                }, 2300); // Un poco después del swal (500 ms recomendado si usas swal v1)
            }
        </script>
        <script>
            var envioEnProceso = false;

            function PantallaCarga() {
                if (envioEnProceso)
                    return false;
                envioEnProceso = true;

                swal({
                    title: "Un momento!",
                    text: "Se están enviando los datos.",
                    type: "info",
                    showConfirmButton: false,
                    allowOutsideClick: false
                });
            }

            document.addEventListener("DOMContentLoaded", function () {
                var formularios = ["FormReto", "FormRetoDetalle", "FormUnoXUno", "FormMasivo", "FormModificar", "FormMCabecera"];

                formularios.forEach(function (id) {
                    var form = document.getElementById(id);
                    if (form) {
                        form.addEventListener("submit", function () {
                            PantallaCarga();
                        });
                    }
                });
            });
        </script>
        <script>
            function QuitarFirmaPR(IdReto, Modulo, FechaReto, Tipo) {
                swal({
                    title: "¿Está seguro?",
                    text: "Esta acción eliminará el registro seleccionado.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#d33",
                    cancelButtonColor: "#3085d6",
                    confirmButtonText: "Sí, eliminar",
                    cancelButtonText: "Cancelar",
                    allowOutsideClick: false
                }, function (isConfirm) {
                    if (isConfirm) {
                        // Si confirma, creamos y enviamos el formulario
                        var form = document.createElement("form");
                        form.method = "post";
                        form.action = "Reto?opc=11";

                        var params = {
                            "IdReto": IdReto,
                            "Modulo": Modulo,
                            "FechaReto": FechaReto,
                            "Tipo": Tipo
                        };

                        for (var key in params) {
                            if (params.hasOwnProperty(key)) {
                                var input = document.createElement("input");
                                input.type = "hidden";
                                input.name = key;
                                input.value = params[key];
                                form.appendChild(input);
                            }
                        }

                        document.body.appendChild(form);
                        form.submit();
                        document.body.removeChild(form);
                    }
                    // Si cancela, no hacemos nada
                });
            }
        </script>


        <Alertas:Alertas />
        <!-- Bootstrap core JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery/jquery.min.js"></script>
        <script src="Interfaz/StylePage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="Interfaz/StylePage/js/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
        <script src="Interfaz/StylePage/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="Interfaz/StylePage/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <!-- Page level custom scripts -->
        <script src="Interfaz/StylePage/js/demo/datatables-demo.js"></script>
    </body>
</html>
