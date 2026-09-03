
<%@page import="Controller.CodeJpaController"%>
<%@page import="com.google.gson.Gson"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/visual"  prefix="Visual" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<%@page import="Controller.CustomerJpaController"%>
<%@page import="java.util.List"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <!--        <script type="text/javascript">
                    history.pushState(null, null, 'Visual.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Visual.jsp');
                    });
                </script>-->
    </head>
    <body class="sidebar-mini">
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="main-content" style="min-height: 694px;">
                <div style="
                     display:flex;
                     align-items:stretch;
                     width:100%;
                     margin-top:10px;
                     border-radius:8px;
                     overflow:hidden;
                     box-shadow:0 2px 6px rgba(0,0,0,0.08);
                     ">


                    <!-- REGISTROS ASOCIADOS -->
                    <div id="alertaRegistros"
                         style="
                         display:flex;
                         flex:1;
                         padding:12px 16px;
                         background:#e8f5ff;
                         border-left:4px solid #2196f3;
                         color:#1f4f70;
                         ">

                        <div style="
                             display:flex;
                             align-items:center;
                             gap:10px;
                             width:100%;
                             ">

                            <span style="font-size:20px;">✔️</span>

                            <div>
                                <div style="
                                     font-weight:600;
                                     font-size:14px;
                                     ">
                                    Registros asociados
                                </div>

                                <div style="
                                     font-size:12px;
                                     color:#52738a;
                                     ">
                                    <b id="AmoutRegDataText"
                                       style="
                                       font-size:16px;
                                       color:#1769aa;
                                       ">0</b>
                                    registros encontrados
                                </div>
                            </div>

                        </div>
                    </div>

                    <!-- SIN INFORMACIÓN -->
                    <div id="alerta"
                         style="
                         display:none;
                         flex:1;
                         padding:12px 16px;
                         background:#fff4df;
                         border-left:4px solid #f0ad4e;
                         color:#5f4b32;
                         ">

                        <div style="
                             display:flex;
                             align-items:center;
                             gap:10px;
                             height:100%;
                             ">

                            <span style="font-size:20px;">⚠️</span>

                            <div>
                                <div style="
                                     font-weight:600;
                                     font-size:14px;
                                     ">
                                    Sin información
                                </div>

                                <div style="
                                     font-size:12px;
                                     color:#806b50;
                                     ">
                                    No se encontró información para los datos ingresados.
                                </div>
                            </div>

                            <button class="btn btn-sm btn-dark"
                                    onclick="mostrarDetalles()"
                                    style="
                                    margin-left:auto;
                                    white-space:nowrap;
                                    ">
                                Ver detalles
                            </button>

                        </div>
                    </div>












                    <!-- DIFERENCIAS -->
                    <div id="alertaError"
                         style="
                         display:flex;
                         flex:1;
                         padding:12px 16px;
                         background:#fff0f0;
                         border-left:4px solid #dc3545;
                         color:#6b2525;
                         ">

                        <div style="
                             display:flex;
                             align-items:center;
                             gap:10px;
                             width:100%;
                             ">

                            <i class="fas fa-times-circle" style="font-size:22px; color:#dc3545;"></i>

                            <div>
                                <div style="
                                     font-weight:600;
                                     font-size:14px;
                                     ">
                                    Diferencias encontradas
                                </div>

                                <div style="
                                     font-size:12px;
                                     color:#8a5050;
                                     ">
                                    <b id="ErrorCountText"
                                       style="
                                       font-size:16px;
                                       color:#c62828;
                                       ">0</b>
                                    diferencias
                                </div>
                            </div>

                            <button class="btn btn-sm btn-dark"
                                    onclick="mostrarDetallesErrores()"
                                    style="
                                    margin-left:auto;
                                    white-space:nowrap;
                                    ">
                                Ver detalles
                            </button>

                        </div>
                    </div>

                </div>

                <!-- ====================================================== -->
                <!-- MODAL DE DIFERENCIAS (COMPROBAR ERRORES) -->
                <!-- ====================================================== -->

                <div id="modalErrores"
                     style="
                     display:none;
                     position:fixed;
                     top:0;
                     left:0;
                     width:100%;
                     height:100%;
                     background:rgba(0,0,0,0.55);
                     z-index:9999;
                     justify-content:center;
                     align-items:center;
                     ">

                    <div style="
                         background:#fff;
                         padding:0;
                         border-radius:10px;
                         width:92%;
                         max-width:1150px;
                         max-height:88vh;
                         overflow:hidden;
                         box-shadow:0 8px 30px rgba(0,0,0,0.25);
                         ">

                        <!-- ENCABEZADO -->
                        <div style="
                             padding:16px 20px;
                             border-bottom:1px solid #ddd;
                             display:flex;
                             align-items:center;
                             justify-content:space-between;
                             background:#fafafa;
                             ">

                            <div>
                                <h4 style="margin:0; font-size:18px; font-weight:600; color:#00bcd4;">
                                    Comprobar Errores
                                </h4>

                                <div style="margin-top:3px; font-size:12px; color:#777;">
                                    Seleccione o edite el <b>Dato Real</b> para cada campo divergente para unificar los registros.
                                </div>
                            </div>

                            <button type="button" onclick="cerrarModalErrores()" style="font-size:24px; border:none; background:none; cursor:pointer; color:#777;">&times;</button>
                        </div>


                        <!-- CONTENIDO -->
                        <div style="max-height:68vh; overflow-y:auto; padding:15px 20px;">

                            <!-- Filtro de verificación -->
                            <div style="margin-bottom:15px;">
                                <label style="font-size:12px; font-weight:700; color:#00bcd4; margin-bottom:4px; display:block;">Filtro de verificación :</label>
                                <input type="text" id="filtroDiferencias" onkeyup="filtrarTablaDiferencias()" class="form-control" placeholder="Buscar por campo o valor..." style="border-radius:20px; border:1.5px solid #00bcd4; padding:6px 16px; font-size:13px;">
                            </div>

                            <table id="tablaErrores" width="100%" style="border-collapse:collapse; table-layout:fixed; font-size:13px;">
                                <thead>
                                    <tr>
                                        <th style="width:22%; padding:10px; text-align:left; background:#f5f5f5; border:1px solid #ddd; color:#555; font-weight:600;">Campo / Componente</th>
                                        <th style="width:43%; padding:10px; text-align:left; background:#f7fcff; border:1px solid #ddd; color:#1769aa; font-weight:600;">Valores Encontrados (Frecuencia)</th>
                                        <th style="width:35%; padding:10px; text-align:left; background:#fff8f8; border:1px solid #ddd; color:#c62828; font-weight:600;">Dato Real a Aplicar (C y P)</th>
                                    </tr>
                                </thead>

                                <tbody id="tablaErroresBody">

                                </tbody>
                            </table>
                        </div>


                        <!-- PIE -->
                        <div style="padding:12px 20px; border-top:1px solid #ddd; background:#fafafa; text-align:right; display:flex; justify-content:flex-end; gap:10px;">
                            <button type="button" class="btn btn-outline-info" onclick="consultarLogHistorial()" style="padding:6px 16px; border-radius:5px;">
                                <i class="fas fa-history"></i> Historial de Cambios
                            </button>
                            <button id="btnUnificarDiferencias" class="btn btn-primary" onclick="unificarTodosCampos()" style="padding:6px 20px; border-radius:5px; display:none;">
                                <i class="fas fa-sync-alt"></i> Aplicar todos los cambios
                            </button>
                            <button class="btn btn-dark" onclick="cerrarModalErrores()" style="padding:6px 18px; border-radius:5px;">Cerrar</button>
                        </div>
                    </div>
                </div>


                <!-- Modal de eventos -->
                <div id="modalEventos" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
                     background:rgba(0,0,0,0.5); z-index:9999; justify-content:center; align-items:center;">
                    <div style="background:white; padding:20px; border-radius:10px; width:80%; max-width:800px;">
                        <h4>ðŸ“‹ Eventos sin información</h4>
                        <table id="tablaEventos" border="1" width="100%" style="border-collapse:collapse;">
                            <thead>
                                <tr>
                                    <th>Tipo</th>
                                    <th>Orden</th>
                                    <th>Producto</th>
                                    <th>Lote</th>
                                    <th>Mensaje</th>
                                    <th>Fecha</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                        <div style="text-align:right; margin-top:10px;">
                            <button class="btn btn-dark" onclick="cerrarModal()">Cerrar</button>
                        </div>
                    </div>
                </div>
            <Visual:Visual/>
            <Alert:Alert/>

        </div>
        <!-- ====================================================== -->
        <!-- JAVASCRIPT -->
        <!-- ====================================================== -->

        <script>

            document.addEventListener("DOMContentLoaded", function () {

                console.log("Inicializando panel de alertas...");

                const inputRegistros = document.getElementById("AmoutRegData");
                const textoRegistros = document.getElementById("AmoutRegDataText");

                if (inputRegistros && textoRegistros) {
                    const cantidadRegistros = inputRegistros.value;
                    textoRegistros.textContent = cantidadRegistros || "0";
                }

                const errorCountInput = document.getElementById("ErrorCount");
                const alertaError = document.getElementById("alertaError");
                const errorCountText = document.getElementById("ErrorCountText");

                if (alertaError) {
                    const errorCount = errorCountInput ? (parseInt(errorCountInput.value, 10) || 0) : 0;
                    if (errorCountText) {
                        errorCountText.textContent = errorCount;
                    }
                    alertaError.style.display = "flex";
                }

            });

            function cargarTablaDiferencias(datos) {
                const tbody = document.getElementById("tablaErroresBody");
                const btnUnificar = document.getElementById("btnUnificarDiferencias");

                if (!tbody) return;
                tbody.innerHTML = "";

                if (!datos || datos.length === 0) {
                    if (btnUnificar) btnUnificar.style.display = "none";
                    tbody.innerHTML = '<tr><td colspan="3" style="padding:20px;text-align:center;color:#777;"><i class="fas fa-check-circle" style="color:#28a745;"></i> No se encontraron diferencias en los registros.</td></tr>';
                    return;
                }

                if (btnUnificar) btnUnificar.style.display = "inline-block";

                // Agrupar diferencias por el nombre de campo
                const agrupados = {};
                datos.forEach((diff) => {
                    if (!agrupados[diff.campo]) {
                        agrupados[diff.campo] = [];
                    }
                    agrupados[diff.campo].push(diff);
                });

                Object.keys(agrupados).forEach((campoName) => {
                    const listaDiffs = agrupados[campoName];
                    const primeraDiff = listaDiffs[0];
                    const campoKey = campoName.replace(/[^a-zA-Z0-9]/g, '_');
                    const parCorrecto = extraerCP(primeraDiff.valorCorrecto);

                    const fila = document.createElement("tr");
                    fila.className = "fila-diferencia";

                    /* 1. CAMPO */
                    const tdCampo = document.createElement("td");
                    tdCampo.style.padding = "12px";
                    tdCampo.style.border = "1px solid #ddd";
                    tdCampo.style.verticalAlign = "middle";

                    tdCampo.innerHTML = '<div style="display:flex; align-items:center; gap:8px;">'
                        + '<i style="cursor:pointer; font-size:15px;" title="Editar dato real" onclick="focusCampo(\'' + campoKey + '\')"></i>'
                        + '<strong style="color:#00bcd4; font-size:13px;">' + campoName + '</strong>'
                        + '</div>';

                    /* 2. VALORES ENCONTRADOS */
                    const tdValores = document.createElement("td");
                    tdValores.style.padding = "10px";
                    tdValores.style.border = "1px solid #ddd";
                    tdValores.style.verticalAlign = "top";
                    tdValores.style.background = "#fbfbfb";

                    let htmlValores = '<div style="margin-bottom:8px; padding:6px 10px; background:#eaf7ff; border-radius:4px; border:1px solid #bce4ff;">'
                        + '<div style="font-weight:600; color:#1769aa; font-size:12px;">'
                        + '<b>C:</b> ' + parCorrecto.c + ' &nbsp;|&nbsp; <b>P:</b> ' + parCorrecto.p
                        + '</div>'
                        + '<div style="display:flex; justify-content:space-between; align-items:center; margin-top:4px;">'
                        + '<small style="color:#666;">' + primeraDiff.cantidadCorrecto + ' registros (predominante)</small>'
                        + '<button type="button" class="btn btn-xs btn-outline-primary" style="padding:2px 8px; font-size:11px;" onclick="setDatoReal(\'' + campoKey + '\', \'' + escapeJs(parCorrecto.c) + '\', \'' + escapeJs(parCorrecto.p) + '\')">Usar este</button>'
                        + '</div>'
                        + '</div>';

                    listaDiffs.forEach((diff) => {
                        const parDiff = extraerCP(diff.valorDiferente);
                        htmlValores += '<div style="margin-bottom:6px; padding:6px 10px; background:#fff0f0; border-radius:4px; border:1px solid #ffcdd2;">'
                            + '<div style="font-weight:600; color:#c62828; font-size:12px;">'
                            + '<b>C:</b> ' + parDiff.c + ' &nbsp;|&nbsp; <b>P:</b> ' + parDiff.p
                            + '</div>'
                            + '<div style="display:flex; justify-content:space-between; align-items:center; margin-top:4px;">'
                            + '<small style="color:#666;">' + diff.cantidadDiferente + ' registros (diferencia)</small>'
                            + '<button type="button" class="btn btn-xs btn-outline-danger" style="padding:2px 8px; font-size:11px;" onclick="setDatoReal(\'' + campoKey + '\', \'' + escapeJs(parDiff.c) + '\', \'' + escapeJs(parDiff.p) + '\')">Usar este</button>'
                            + '</div>'
                            + '</div>';
                    });

                    tdValores.innerHTML = htmlValores;

                    /* 3. DATO REAL A APLICAR (CON BOTá“N DE ACCIá“N POR CAMPO) */
                    const tdDatoReal = document.createElement("td");
                    tdDatoReal.style.padding = "10px";
                    tdDatoReal.style.border = "1px solid #ddd";
                    tdDatoReal.style.verticalAlign = "middle";
                    tdDatoReal.style.background = "#fff";

                    tdDatoReal.innerHTML = '<div data-campo="' + campoName + '" data-key="' + campoKey + '">'
                        + '<label style="font-size:11px; font-weight:700; color:#555; margin-bottom:2px; display:block;">Dato Real C (Lote C):</label>'
                        + '<input type="text" id="input_c_' + campoKey + '" value="' + escapeJs(parCorrecto.c) + '" class="form-control form-control-sm mb-2" style="font-weight:600; color:#1769aa;">'
                        + '<label style="font-size:11px; font-weight:700; color:#555; margin-bottom:2px; display:block;">Dato Real P (Lote P):</label>'
                        + '<input type="text" id="input_p_' + campoKey + '" value="' + escapeJs(parCorrecto.p) + '" class="form-control form-control-sm mb-2" style="font-weight:600; color:#1769aa;">'
                        + '<button type="button" class="btn btn-sm btn-success btn-block" style="padding:5px 10px; font-size:11px; font-weight:600; border-radius:4px;" onclick="unificarUnCampo(\'' + escapeJs(campoName) + '\', \'' + campoKey + '\')">'
                        + '<i class="fas fa-check-circle"></i> Aplicar cambio a este campo'
                        + '</button>'
                        + '</div>';

                    fila.appendChild(tdCampo);
                    fila.appendChild(tdValores);
                    fila.appendChild(tdDatoReal);
                    tbody.appendChild(fila);
                });
            }

            function extraerCP(valorStr) {
                if (!valorStr) return { c: "N/A", p: "N/A" };
                const sep = " | P: ";
                const pos = valorStr.indexOf(sep);
                if (valorStr.startsWith("C: ") && pos >= 0) {
                    return {
                        c: valorStr.substring(3, pos),
                        p: valorStr.substring(pos + sep.length)
                    };
                }
                return { c: valorStr, p: "N/A" };
            }

            function escapeJs(str) {
                return (str || '').replace(/'/g, "\\'").replace(/"/g, '&quot;');
            }

            function setDatoReal(campoKey, valC, valP) {
                const inputC = document.getElementById("input_c_" + campoKey);
                const inputP = document.getElementById("input_p_" + campoKey);
                if (inputC) inputC.value = valC;
                if (inputP) inputP.value = valP;
            }

            function focusCampo(campoKey) {
                const inputC = document.getElementById("input_c_" + campoKey);
                if (inputC) {
                    inputC.focus();
                    inputC.select();
                }
            }

            function filtrarTablaDiferencias() {
                const filtro = (document.getElementById("filtroDiferencias").value || "").toLowerCase();
                const filas = document.querySelectorAll("#tablaErroresBody tr");
                filas.forEach(fila => {
                    const texto = fila.textContent.toLowerCase();
                    fila.style.display = texto.includes(filtro) ? "" : "none";
                });
            }

            function mostrarDetallesErrores() {
                const modal = document.getElementById("modalErrores");
                const tbody = document.getElementById("tablaErroresBody");

                const idProductoInput = document.getElementById("IdProductoVerificar");
                const loteInput = document.getElementById("LoteVerificar");
                const idLineaInput = document.getElementById("IdLineaVerificar");
                const cicloInput = document.getElementById("CicloVerificar");

                if (!modal || !tbody || !idProductoInput || !loteInput || !idLineaInput || !cicloInput) {
                    console.error("No se encontraron los pará¡metros o elementos del modal de diferencias.");
                    return;
                }

                const idProducto = idProductoInput.value;
                const lote = loteInput.value;
                const idLinea = idLineaInput.value;
                const ciclo = cicloInput.value;

                if (!idProducto || !lote || !idLinea) {
                    tbody.innerHTML = "<tr><td colspan='3' style='padding:20px;text-align:center;color:#c62828;'>No hay pará¡metros vá¡lidos para consultar las diferencias.</td></tr>";
                    modal.style.display = "flex";
                    return;
                }

                tbody.innerHTML = "<tr><td colspan='3' style='padding:20px;text-align:center;color:#777;'><i class='fas fa-spinner fa-spin'></i> Consultando diferencias...</td></tr>";
                modal.style.display = "flex";

                fetch(
                    "ConsultarDiferenciasRegistro"
                    + "?idProducto=" + encodeURIComponent(idProducto)
                    + "&lote=" + encodeURIComponent(lote)
                    + "&idLinea=" + encodeURIComponent(idLinea)
                    + "&ciclo=" + encodeURIComponent(ciclo)
                )
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error HTTP: " + response.status);
                    }
                    return response.json();
                })
                .then(datos => {
                    if (!Array.isArray(datos)) {
                        throw new Error(datos && datos.mensaje ? datos.mensaje : "Respuesta JSON no vá¡lida.");
                    }
                    cargarTablaDiferencias(datos);
                })
                .catch(error => {
                    console.error("Error consultando diferencias:", error);
                    tbody.innerHTML = "<tr><td colspan='3' style='padding:20px;text-align:center;color:#c62828;'><i class='fas fa-exclamation-circle'></i> No fue posible consultar las diferencias.</td></tr>";
                });
            }

            function consultarLogHistorial() {
                const idProductoInput = document.getElementById("IdProductoVerificar");
                const loteInput = document.getElementById("LoteVerificar");
                const idLineaInput = document.getElementById("IdLineaVerificar");
                const cicloInput = document.getElementById("CicloVerificar");
                const tbody = document.getElementById("tablaErroresBody");

                if (!idProductoInput || !loteInput || !idLineaInput || !cicloInput || !tbody) return;

                const idProducto = idProductoInput.value;
                const lote = loteInput.value;
                const idLinea = idLineaInput.value;
                const ciclo = cicloInput.value;

                tbody.innerHTML = '<tr><td colspan="3" style="padding:20px;text-align:center;color:#777;"><i class="fas fa-spinner fa-spin"></i> Cargando historial de cambios...</td></tr>';

                fetch("ConsultarLogDiferencias?idProducto=" + encodeURIComponent(idProducto) + "&lote=" + encodeURIComponent(lote) + "&idLinea=" + encodeURIComponent(idLinea) + "&ciclo=" + encodeURIComponent(ciclo))
                .then(res => res.json())
                .then(logs => {
                    if (!Array.isArray(logs) || logs.length === 0) {
                        tbody.innerHTML = '<tr><td colspan="3" style="padding:20px;text-align:center;color:#777;"><i class="fas fa-info-circle"></i> No hay registros de cambios realizados para este lote. <br><br><button type="button" class="btn btn-sm btn-secondary" onclick="mostrarDetallesErrores()">Volver a edición</button></td></tr>';
                        return;
                    }

                    let html = '<tr><td colspan="3" style="padding:0;">'
                        + '<div style="padding:10px 15px; background:#f0f8ff; border-bottom:1px solid #cce5ff; display:flex; justify-content:space-between; align-items:center;">'
                        + '<strong style="color:#007bff; font-size:13px;"><i class="fas fa-history"></i> Historial de Auditorá­a de Cambios</strong>'
                        + '<button type="button" class="btn btn-sm btn-outline-secondary" onclick="mostrarDetallesErrores()"><i class="fas fa-arrow-left"></i> Volver a edición</button>'
                        + '</div>'
                        + '<table class="table table-sm table-striped" style="margin:0; font-size:12px;">'
                        + '<thead><tr style="background:#e9ecef;"><th>Fecha / Hora</th><th>Usuario Responsable</th><th>Campo</th><th>Antes (Estado previo)</th><th>Despuá©s (Dato Real)</th></tr></thead>'
                        + '<tbody>';

                    logs.forEach(log => {
                        html += '<tr>'
                            + '<td style="white-space:nowrap;"><b>' + (log[5] || '') + '</b></td>'
                            + '<td><i class="fas fa-user text-info"></i> ' + (log[4] || '') + '</td>'
                            + '<td><b style="color:#00bcd4;">' + (log[1] || '') + '</b></td>'
                            + '<td style="color:#c62828; font-size:11px;">' + (log[2] || '') + '</td>'
                            + '<td style="color:#28a745; font-weight:600; font-size:11px;">' + (log[3] || '') + '</td>'
                            + '</tr>';
                    });

                    html += '</tbody></table></td></tr>';
                    tbody.innerHTML = html;
                })
                .catch(err => {
                    tbody.innerHTML = '<tr><td colspan="3" style="padding:20px;text-align:center;color:#c62828;"><i class="fas fa-exclamation-triangle"></i> Error consultando el historial de auditorá­a. <br><br><button type="button" class="btn btn-sm btn-secondary" onclick="mostrarDetallesErrores()">Volver a edición</button></td></tr>';
                });
            }

            function cerrarModalErrores() {
                const modal = document.getElementById("modalErrores");
                if (modal) {
                    modal.style.display = "none";
                }
            }

            function unificarUnCampo(campoName, campoKey) {
                const inputC = document.getElementById("input_c_" + campoKey);
                const inputP = document.getElementById("input_p_" + campoKey);

                if (!inputC || !inputP) return;

                const listaAjustes = [{
                    campo: campoName,
                    c: inputC.value.trim(),
                    p: inputP.value.trim()
                }];

                ejecutarUnificacion(listaAjustes, "al campo " + campoName);
            }

            function unificarTodosCampos() {
                const contenedores = document.querySelectorAll("#tablaErroresBody [data-campo]");
                const listaAjustes = [];

                contenedores.forEach(c => {
                    const campo = c.getAttribute("data-campo");
                    const key = c.getAttribute("data-key");
                    const inputC = document.getElementById("input_c_" + key);
                    const inputP = document.getElementById("input_p_" + key);

                    if (campo && inputC && inputP) {
                        listaAjustes.push({
                            campo: campo,
                            c: inputC.value.trim(),
                            p: inputP.value.trim()
                        });
                    }
                });

                if (listaAjustes.length === 0) {
                    alert("No hay datos para actualizar.");
                    return;
                }

                ejecutarUnificacion(listaAjustes, "a todos los campos modificados");
            }

            function ejecutarUnificacion(listaAjustes, descripcionAccion) {
                const idProductoInput = document.getElementById("IdProductoVerificar");
                const loteInput = document.getElementById("LoteVerificar");
                const idLineaInput = document.getElementById("IdLineaVerificar");
                const cicloInput = document.getElementById("CicloVerificar");

                if (!idProductoInput || !loteInput || !idLineaInput || !cicloInput) {
                    alert("Faltan pará¡metros de verificación.");
                    return;
                }

                const idProducto = idProductoInput.value;
                const lote = loteInput.value;
                const idLinea = idLineaInput.value;
                const ciclo = cicloInput.value;

                if (!confirm("Está¡ seguro de aplicar el cambio " + descripcionAccion + "?\nEsta acción actualizará¡ los registros de este lote en la base de datos de Registros LAB.")) {
                    return;
                }

                const btnUnificar = document.getElementById("btnUnificarDiferencias");
                if (btnUnificar) {
                    btnUnificar.disabled = true;
                    btnUnificar.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Actualizando registros...';
                }

                const params = new URLSearchParams();
                params.append("confirmar", "SI");
                params.append("idProducto", idProducto);
                params.append("lote", lote);
                params.append("idLinea", idLinea);
                params.append("ciclo", ciclo);
                params.append("ajustesJson", JSON.stringify(listaAjustes));

                fetch("ActualizarDiferenciasRegistro", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
                    body: params.toString()
                })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { throw new Error(err.mensaje || "Error actualizando registros."); });
                    }
                    return response.json();
                })
                .then(data => {
                    if (data && data.mensaje) {
                        if (typeof iziToast !== "undefined") {
                            iziToast.success({ title: "á‰xito", message: data.mensaje, position: "topRight" });
                        } else {
                            alert(data.mensaje);
                        }
                    }
                    cerrarModalErrores();
                    setTimeout(() => { location.reload(); }, 1200);
                })
                .catch(error => {
                    console.error("Error al unificar diferencias:", error);
                    alert(error.message || "No fue posible actualizar los registros.");
                })
                .finally(() => {
                    if (btnUnificar) {
                        btnUnificar.disabled = false;
                        btnUnificar.innerHTML = '<i class="fas fa-sync-alt"></i> Aplicar todos los cambios';
                    }
                });
            }
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', () => {

                const isEmpty = t => !t || t.trim() === '' || t.trim() === '-' || t.trim() === '----';
                const phoneTd = document.getElementById('TelefonoValue');
                if (phoneTd) {
                    const phoneEditable = phoneTd.querySelector('.editable');
                    if (phoneEditable) {

                        const getText = () => phoneEditable.innerText.trim();
                        const updatePhoneState = () => {
                            if (isEmpty(getText())) {
                                phoneTd.classList.add('pending'); // gris en TD
                                phoneEditable.innerText = ''; // ✅ nunca mostrar ----
                            } else {
                                phoneTd.classList.remove('pending');
                            }
                        };
                        // Estado inicial
                        updatePhoneState();
                        // Mientras escribe
                        phoneEditable.addEventListener('input', updatePhoneState);
                        // Al salir
                        phoneEditable.addEventListener('blur', updatePhoneState);
                    }
                }

                document.querySelectorAll('.editable').forEach(el => {

                    // 🚫 excluir teléfono
                    if (el.closest('#TelefonoValue'))
                        return;
                    const getText = () => el.innerText.trim();
                    // Estado inicial
                    if (getText() === '' || getText() === '----') {
                        el.innerText = '----';
                        el.classList.add('pending');
                    }

                    // Focus
                    el.addEventListener('focus', () => {
                        if (getText() === '----')
                            el.innerText = '';
                    });
                    // Blur
                    el.addEventListener('blur', () => {
                        if (getText() === '') {
                            el.innerText = '----';
                            el.classList.add('pending');
                        } else {
                            el.classList.remove('pending');
                        }
                    });
                    // Mientras escribe
                    el.addEventListener('input', () => {
                        const t = getText();
                        if (t !== '' && t !== '----') {
                            el.classList.remove('pending');
                        }
                    });
                });
            });
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {

                // Quitar borde rojo al editar textos
                ['clientValue', 'AmountValue'].forEach(id => {
                    const el = document.getElementById(id);
                    if (el) {
                        el.addEventListener('input', function () {
                            this.style.border = '';
                        });
                    }
                });
                // ✅ Quitar borde rojo cuando se seleccione fecha
                const dateInput = document.querySelector('#DateDispatch input');
                if (dateInput) {
                    dateInput.addEventListener('change', function () {
                        this.style.border = '';
                    });
                }

            });
        </script>
        <script>
            function saveHtml() {

                var form = document.getElementById('FormGenerate');
                if (!form) {
                    iziToast.error({
                        title: 'Error',
                        message: 'Formulario no encontrado',
                        position: 'bottomRight'
                    });
                    return;
                }

                var htmlContainer = document.getElementById('HtmlContent');
                // Sincronizar inputs
                if (htmlContainer) {
                    htmlContainer.querySelectorAll('input').forEach(input => {
                        if (input.type === 'radio' || input.type === 'checkbox') {
                            input.checked
                                    ? input.setAttribute('checked', 'checked')
                                    : input.removeAttribute('checked');
                        } else {
                            input.setAttribute('value', input.value);
                        }
                    });
                }

                // HTML actualizado
                var contentHtml = htmlContainer ? htmlContainer.innerHTML : "";
                var encodedHtml = encodeURIComponent(contentHtml);
                // ===== CAPTURA =====
                var clientSpan = document.getElementById('clientValue');
                        var clientText = clientSpan?.textContent.trim() || '';
                var amountSpan = document.getElementById('AmountValue');
                        var amountRaw = amountSpan?.textContent.trim() || '';
                var amountClean = amountRaw.replace(/[^\d]/g, '');
                var amountNumber = Number(amountClean);
                var dateInput = document.querySelector('#DateDispatch input');
                        var dateValue = dateInput?.value || '';
                ;
                        var consText = document.getElementById('consValue')?.textContent.trim() || '';
                        var idRegisterText = document.getElementById('IdRegister')?.textContent.trim() || '';
                        var Code = document.getElementById('codeValue')?.textContent.trim() || '';
                // ===== VALIDACIONES =====
                if (clientText === '' || clientText === '-----') {
                    showWarning(clientSpan, 'Debe ingresar el Cliente.');
                    return;
                }

                if (!amountClean || isNaN(amountNumber) || amountNumber <= 0) {
                    showWarning(amountSpan, 'El monto debe ser mayor a cero.');
                    return;
                }

                if (dateValue === '') {
                    showWarning(dateInput, 'Debe ingresar la fecha de despacho.');
                    return;
                }

                // ===== HIDDEN INPUTS =====
                const addHidden = (name, value) => {
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = name;
                    input.value = value;
                    form.appendChild(input);
                };
                // Limpiar hidden inputs previos

                let batchValues = [];
                if (htmlContainer) {
                    htmlContainer.querySelectorAll('[id^="IdBatchM"]').forEach(td => {
                        const value = td.textContent.trim();
                        if (value !== '') {
                            batchValues.push(value);
                        }
                    });
                }

                // UNIFICAR (eliminar duplicados sin alertar)
                const uniqueBatches = [...new Set(batchValues)];
                addHidden('Html', encodedHtml);
                addHidden('clientValue', clientText);
                addHidden('AmountValue', amountNumber);
                addHidden('DateDispatch', dateValue);
                addHidden('MaterialBatches', uniqueBatches.join(','));
                addHidden('MaterialBatchCount', uniqueBatches.length);
                if (consText)
                    addHidden('ConsValue', consText);
                if (idRegisterText)
                    addHidden('IdRegisterValue', idRegisterText);
                if (Code)
                    addHidden('codeValue', Code);
                // ===== SUBMIT =====
                form.submit();
            }
        </script>
        <script>
            /* ===================== ALERTA ===================== */
            function showWarning(element, message) {
                iziToast.warning({
                    title: 'Atención!',
                    message: message,
                    position: 'bottomRight',
                    timeout: 4000
                });
                if (element) {
                    element.focus();
                    element.style.border = '2px solid red';
                }
            }
        </script>
        <script>
            function dibujarCoordenadas() {
                const canvas = document.getElementById('signature-canvas');
                const input = document.getElementById('coordenadas-hidden');
                if (!canvas || !input || !input.value)
                    return;

                const ctx = canvas.getContext('2d');
                if (!ctx)
                    return;
                // 1) Leer y decodificar entidades html
                let raw = input.value;
                raw = raw.replace(/&quot;/g, '"').replace(/&#39;/g, "'").trim();
                // Función auxiliar para intentar parsear y devolver resultado o null
                function tryParse(s) {
                    try {
                        return JSON.parse(s);
                    } catch (e) {
                        console.warn("Intento de parse falló:", e.message);
                        return null;
                    }
                }

                // 2) Intento directo
                let coordenadas = tryParse(raw);
                if (!coordenadas) {
                    // 3) Si empieza con '{' (objeto) y no con '[' (array), envolver en []
                    if (raw.startsWith('{') && !raw.startsWith('[')) {
                        let candidate = '[' + raw + ']';
                        coordenadas = tryParse(candidate);
                        if (coordenadas)
                            raw = candidate;
                    }
                }

                if (!coordenadas) {
                    // 4) Si hay '}{' pegados, reemplazar '}{' por '},{' y envolver en []
                    if (raw.indexOf('}{') !== -1) {
                        let fixed = raw.replace(/}\s*{/g, '},{');
                        if (!fixed.startsWith('['))
                            fixed = '[' + fixed + ']';
                        coordenadas = tryParse(fixed);
                        if (coordenadas)
                            raw = fixed;
                    }
                }

                if (!coordenadas) {
                    // 5) Último recurso
                    let cleaned = raw.replace(/[\u0000-\u001f\u007f-\u009f]/g, '');
                    cleaned = cleaned.replace(/,\s*]/g, ']');
                    if (!cleaned.startsWith('[') && cleaned.startsWith('{'))
                        cleaned = '[' + cleaned + ']';
                    cleaned = cleaned.replace(/}\s*,\s*}/g, '},{');
                    coordenadas = tryParse(cleaned);
                    if (coordenadas)
                        raw = cleaned;
                }

                if (!coordenadas) {
                    console.error("No pude parsear las coordenadas. Valor final probado:", raw.slice(0, 500));
                    return;
                }

                if (!Array.isArray(coordenadas)) {
                    coordenadas = [coordenadas];
                }

                ctx.clearRect(0, 0, canvas.width, canvas.height);
                // ⚙️ ---- ESCALADO AUTOMÁTICO Y CENTRADO ---- ⚙️
                const maxX = Math.max(...coordenadas.map(c => Math.max(c.lx, c.mx)));
                const maxY = Math.max(...coordenadas.map(c => Math.max(c.ly, c.my)));
                const minX = Math.min(...coordenadas.map(c => Math.min(c.lx, c.mx)));
                const minY = Math.min(...coordenadas.map(c => Math.min(c.ly, c.my)));
                const originalWidth = maxX - minX;
                const originalHeight = maxY - minY;
                const scaleX = canvas.width / originalWidth;
                const scaleY = canvas.height / originalHeight;
                const scale = Math.min(scaleX, scaleY); // mantiene proporción

                // Calcular offset para centrar
                const offsetX = (canvas.width - originalWidth * scale) / 2 - minX * scale;
                const offsetY = (canvas.height - originalHeight * scale) / 2 - minY * scale;
                console.log(`Escala aplicada: ${scale.toFixed(2)} | Offset: (${offsetX.toFixed(1)}, ${offsetY.toFixed(1)})`);
                // ---- DIBUJAR CON ESCALA Y CENTRADO ----
                ctx.strokeStyle = 'black';
                ctx.lineWidth = 1.5;
                coordenadas.forEach(coord => {
                    if (coord && typeof coord.lx === 'number' && typeof coord.ly === 'number'
                            && typeof coord.mx === 'number' && typeof coord.my === 'number') {

                        ctx.beginPath();
                        ctx.moveTo(coord.lx * scale + offsetX, coord.ly * scale + offsetY);
                        ctx.lineTo(coord.mx * scale + offsetX, coord.my * scale + offsetY);
                        ctx.stroke();
                    }
                });
                console.log("Dibujo completado. Puntos dibujados:", coordenadas.length);
            }

            document.addEventListener('DOMContentLoaded', dibujarCoordenadas);
        </script>
        <script>
            // Inicializa el arreglo global para almacenar eventos sin información
            window.NoDataEvents = [];
        </script>
        <div class="modal fade" id="htmlModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl">
                <div class="modal-content">
                    <div class="modal-header bg-secondary text-white">
                        <h5 class="modal-title">Vista previa del adjunto</h5>
                    </div>
                    <div class="modal-body" id="htmlModalBody" style="overflow:auto; max-height:68vh;">
                        <!-- Contenido dinámico -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary" onclick="downloadHtmlAsPDF()">Descargar PDF</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showHtmlAttachmentById(htmlContainerId) {
                const container = document.getElementById(htmlContainerId);
                if (!container)
                    return;
                const modalBody = document.getElementById('htmlModalBody');
                if (!modalBody)
                    return;
                modalBody.innerHTML = container.innerHTML;
                // 🔹 Ajuste dinámico del tamaño según el contenido
                const modalDialog = modalBody.closest('.modal-dialog');
                if (modalDialog) {
                    modalDialog.style.width = 'auto';
                    modalDialog.style.maxWidth = '68vw'; // ocupa hasta 95% del ancho de pantalla
                }
                modalBody.style.maxHeight = '58vh'; // ocupa hasta 85% de la altura

                if (window.jQuery && jQuery.fn && jQuery.fn.modal) {
                    jQuery('#htmlModal').modal('show');
                } else {
                    console.error('Bootstrap 4/jQuery no está disponible para abrir #htmlModal.');
                    return;
                }
                // 🔹 Ajusta automáticamente el alto si el contenido es pequeño
                setTimeout(() => {
                    const contentHeight = modalBody.scrollHeight;
                    const windowHeight = window.innerHeight * 0.85;
                    if (contentHeight < windowHeight) {
                        modalBody.style.maxHeight = contentHeight + 'px';
                    }
                }, 200);
            }

            function downloadHtmlAsPDF() {
                const modalBody = document.getElementById('htmlModalBody');
                if (!modalBody)
                    return;
                const content = modalBody.innerHTML;
                const w = window.open('', '_blank');
                w.document.write('<html><head><title>Adjunto</title></head><body>' + content + '</body></html>');
                w.document.close();
                setTimeout(() => w.print(), 400);
            }
        </script>
        <script>
            function confirmarDevolucion(url) {

                swal({
                    title: "¿Está seguro de devolver?",
                    text: "Por favor, justifique la razón de la devolución.",
                    content: {
                        element: "textarea",
                        attributes: {
                            placeholder: "Escriba aquí la justificación...",
                            id: "razonDevolucion"
                        }
                    },
                    icon: "warning",
                    buttons: {
                        cancel: {
                            text: "Cancelar",
                            visible: true,
                            className: "btn btn-secondary",
                            closeModal: true
                        },
                        confirm: {
                            text: "Devolver",
                            visible: true,
                            className: "btn btn-success",
                            closeModal: false
                        }
                    },
                    dangerMode: true
                }).then((value) => {

                    if (!value)
                        return;
                    const razon = document.getElementById("razonDevolucion").value.trim();
                    if (!razon) {
                        swal({
                            title: "Justificación requerida",
                            text: "Debe ingresar una justificación para realizar la devolución.",
                            icon: "error"
                        });
                        return;
                    }

                    // Crear contenido de carga
                    const loading = document.createElement("div");
                    loading.innerHTML = `
                <div class="loader"></div>
                <div class="loader-text">
                    <strong>Procesando devolución...</strong><br>
                    Registrando la devolución y enviando la notificación por correo.<br><br>
                    <small>Este proceso puede tardar algunos segundos.<br>Por favor, no cierre esta ventana.</small>
                </div>
            `;
                    // Mostrar alerta de espera
                    swal({
                        title: "Espere un momento",
                        content: loading,
                        buttons: false,
                        closeOnClickOutside: false,
                        closeOnEsc: false
                    });
                    // Dar tiempo para que el usuario vea la animación antes de redirigir
                    setTimeout(function () {
                        window.location.href = url + "&Justification=" + encodeURIComponent(razon);
                    }, 500);
                });
            }

        </script>
        <%
            CustomerJpaController customerJpa = new CustomerJpaController();
            List lstCustomer = customerJpa.ConsultCustomer();

            Gson gson = new Gson();
            String jsonCustomer = gson.toJson(lstCustomer);
        %>
        <script>

            window.lstCustomers = <%=jsonCustomer%>;
            window.lstCustomers = window.lstCustomers.map(c => ({
                    id: c[0],
                    name: c[1],
                    address: c[2],
                    city: c[3],
                    country: c[4]
                }));

        </script>
        <script>
            function UpdateCustomer() {

                let html = `
            <div class="mb-2">
                <input type="text"
                       id="buscarCliente"
                       class="form-control"
                       placeholder="🔍 Buscar cliente..."
                       onkeyup="filtrarClientes()">
            </div>

            <div style="max-height:420px; overflow-y:auto; border:1px solid #dee2e6; border-radius:6px;" >

            <table class="table table-bordered table-hover table-sm mb-0" id="tablaClientes" style="font-size: 13px;color: black;text-align: left;">

                <thead style="position:sticky; top:0; background:#dccbfe; color:black; z-index:10;">

                    <tr>
                        <th style="width:28%">Cliente</th>
                        <th style="width:30%">Dirección</th>
                        <th style="width:12%">Ciudad</th>
                        <th style="width:12%">País</th>
                        <th style="width:10% text-align: center;">Opc</th>
                    </tr>

                </thead>

                <tbody>
        `;

                window.lstCustomers.forEach(c => {

                    html += "<tr>";

                    html += "<td>" + c.name + "</td>";
                    html += "<td>" + c.address + "</td>";
                    html += "<td>" + c.city + "</td>";
                    html += "<td>" + c.country + "</td>";

                    html += "<td style='text-align:center'>";
                    html += "<button class='btn btn-success btn-sm' onclick='seleccionarCliente(" + c.id + ")'>";
                    html += "<i class='fas fa-check'></i>";
                    html += "</button>";
                    html += "</td>";

                    html += "</tr>";

                });

                html += `
                </tbody>

            </table>

            </div>
        `;

                swal({

                    title: "Seleccionar Cliente",

                    content: {
                        element: "div",
                        attributes: {
                            innerHTML: html
                        }
                    },

                    button: "Cerrar"

                });

                setTimeout(function () {

                    const modal = document.querySelector(".swal-modal");

                    modal.style.width = "878px";
                    modal.style.maxWidth = "878px";
                    modal.style.borderRadius = "10px";
                    modal.style.height = "height: 677px;";

                    document.querySelector(".swal-content").style.padding = "10px 20px";

                }, 50);

            }
        </script>
        <script>
            function filtrarClientes() {

                let filtro = document.getElementById("buscarCliente").value.toUpperCase();

                let filas = document.querySelectorAll("#tablaClientes tbody tr");

                filas.forEach(fila => {

                    let texto = fila.innerText.toUpperCase();

                    fila.style.display = texto.indexOf(filtro) > -1 ? "" : "none";

                });

            }
        </script>
        <script>
            function actualizarCampo(id, valor) {
                const elemento = document.getElementById(id);
                if (!elemento)
                    return;
                elemento.textContent = valor || "";
                elemento.classList.add("editable");
                elemento.setAttribute("contenteditable", "true");
                // Si manejas estados pendientes, puedes remover esa clase
                elemento.classList.remove("pending");
            }
            function seleccionarCliente(id) {

                try {

                    const cliente = window.lstCustomers.find(c => c.id == id);

                    if (!cliente)
                        return;

                    actualizarCampo("clientValue", cliente.name);
                    actualizarCampo("tAddress", cliente.address);
                    actualizarCampo("tCity", cliente.city);
                    actualizarCampo("tCountry", cliente.country);

                    swal.close();

                    iziToast.success({
                        title: "Correcto",
                        message: "Cliente actualizado correctamente.",
                        position: "topRight"
                    });

                } catch (e) {
                    console.error(e);
                }

            }
        </script>
        <%
            // ============================================================
            // CONSULTAR CÓDIGOS
            // ============================================================

            CodeJpaController codeJpa = new CodeJpaController();
            List lstCode = codeJpa.ConsultCode();

            Gson gsonCode = new Gson();
            String jsonCode = gsonCode.toJson(lstCode);
        %>
        <script>

            // ============================================================
            // CARGAR CÓDIGOS
            // ============================================================

            window.lstCodes = <%=jsonCode%>;

            window.lstCodes = window.lstCodes.map(c => ({
                    id: c[0],
                    code: c[1],
                    client: c[2]
                }));

        </script>
        <script>

            // ============================================================
            // ABRIR MODAL
            // ============================================================

            function UpdateCustomerCode() {

                let html = '<div class="mb-2">' +
                        '<input type="text" id="buscarCodigo" class="form-control" placeholder="🔍 Buscar cliente o código..." onkeyup="filtrarCodigos()">' +
                        '</div>' +
                        '<div style="max-height:420px; overflow-y:auto; border:1px solid #dee2e6; border-radius:6px;">' +
                        '<table class="table table-bordered table-hover table-sm mb-0" id="tablaCodigos" style="font-size:13px; color:black; text-align:left;">' +
                        '<thead style="position:sticky; top:0; background:#dccbfe; color:black; z-index:10;">' +
                        '<tr>' +
                        '<th style="width:45%">Cliente</th>' +
                        '<th style="width:35%">Código</th>' +
                        '<th style="width:20%; text-align:center;">Opc</th>' +
                        '</tr>' +
                        '</thead>' +
                        '<tbody>';


                // ========================================================
                // RECORRER DATOS
                // ========================================================

                window.lstCodes.forEach(c => {

                    html += "<tr>";

                    html += "<td>";
                    html += c.client || "";
                    html += "</td>";

                    html += "<td>";
                    html += c.code || "";
                    html += "</td>";

                    // ====================================================
                    // BOTÓN OPC
                    // ====================================================

                    html += "<td style='text-align:center'>";

                    html +=
                            "<button " +
                            "type='button' " +
                            "class='btn btn-success btn-sm btnCodigo' " +
                            "data-id='" + c.id + "' " +
                            "title='Seleccionar código'>" +
                            "<i class='fas fa-check'></i>" +
                            "</button>";

                    html += "</td>";

                    html += "</tr>";

                });


                    </div>

                `;


                // ========================================================
                // MOSTRAR SWAL
                // ========================================================

                swal({

                    title: "Seleccionar Código",

                    content: {

                        element: "div",

                        attributes: {

                            innerHTML: html

                        }

                    },

                    button: "Cerrar"

                });


                // ========================================================
                // CONFIGURAR MODAL
                // ========================================================

                setTimeout(function () {

                    const modal =
                            document.querySelector(".swal-modal");


                    if (modal) {

                        modal.style.width = "750px";
                        modal.style.maxWidth = "750px";
                        modal.style.borderRadius = "10px";

                    }


                    const content =
                            document.querySelector(".swal-content");


                    if (content) {

                        content.style.padding =
                                "10px 20px";

                    }


                    // ====================================================
                    // EVENTO DE LOS BOTONES
                    // ====================================================

                    const botones =
                            document.querySelectorAll(
                                    ".btnCodigo"
                                    );


                    botones.forEach(function (boton) {

                        boton.addEventListener(
                                "click",
                                function () {

                                    const id =
                                            this.getAttribute(
                                                    "data-id"
                                                    );


                                    console.log(
                                            "ID seleccionado:",
                                            id
                                            );


                                    seleccionarCodigo(id);

                                }
                        );

                    });

                }, 100);

            }

        </script>
        <script>

            // ============================================================
            // FILTRAR
            // ============================================================

            function filtrarCodigos() {

                const input =
                        document.getElementById(
                                "buscarCodigo"
                                );


                if (!input)
                    return;


                const filtro =
                        input.value.toUpperCase();


                const filas =
                        document.querySelectorAll(
                                "#tablaCodigos tbody tr"
                                );


                filas.forEach(function (fila) {

                    const texto =
                            fila.innerText.toUpperCase();


                    fila.style.display =
                            texto.indexOf(filtro) > -1
                            ? ""
                            : "none";

                });

            }

        </script>
        <script>

            // ============================================================
            // SELECCIONAR CLIENTE + CÓDIGO
            // ============================================================

            function seleccionarCodigo(id) {

                try {

                    console.log(
                            "ID recibido:",
                            id
                            );


                    // ====================================================
                    // BUSCAR REGISTRO
                    // ====================================================

                    const registro =
                            window.lstCodes.find(function (c) {

                                return String(c.id) === String(id);

                            });


                    if (!registro) {

                        console.error(
                                "No se encontró el registro:",
                                id
                                );

                        return;

                    }


                    console.log(
                            "Registro seleccionado:",
                            registro
                            );


                    // ====================================================
                    // ACTUALIZAR CLIENTE
                    // ====================================================

                    const elementoCliente =
                            document.getElementById(
                                    "clientValue"
                                    );


                    if (!elementoCliente) {

                        console.error(
                                "No se encontró el elemento #clientValue"
                                );

                        return;

                    }


                    elementoCliente.textContent =
                            registro.client || "";


                    elementoCliente.classList.add(
                            "editable"
                            );


                    elementoCliente.setAttribute(
                            "contenteditable",
                            "true"
                            );


                    elementoCliente.classList.remove(
                            "pending"
                            );


                    console.log(
                            "Cliente actualizado:",
                            registro.client
                            );


                    // ====================================================
                    // ACTUALIZAR CÓDIGO
                    // ====================================================

                    const elementoCodigo =
                            document.getElementById(
                                    "tCode"
                                    );


                    if (!elementoCodigo) {

                        console.error(
                                "No se encontró el elemento #tCode"
                                );

                        return;

                    }


                    elementoCodigo.textContent =
                            registro.code || "";


                    elementoCodigo.classList.add(
                            "editable"
                            );


                    elementoCodigo.setAttribute(
                            "contenteditable",
                            "true"
                            );


                    elementoCodigo.classList.remove(
                            "pending"
                            );


                    console.log(
                            "Código actualizado:",
                            registro.code
                            );


                    // ====================================================
                    // CERRAR MODAL
                    // ====================================================

                    swal.close();


                    // ====================================================
                    // MOSTRAR ALERTA
                    // ====================================================

                    iziToast.success({

                        title: "Correcto",

                        message:
                                "Cliente y código actualizados correctamente.",

                        position: "topRight"

                    });


                } catch (e) {

                    console.error(
                            "Error al seleccionar cliente y código:",
                            e
                            );

                }

            }

        </script> 
        <script src="Interface/Content/Assets/js/eventLogger.js"></script>
        <script src="Interface/Content/Assets/js/Print.js"></script>
        <script src="Interface/Content/Assets/js/html2canvas.min.js"></script>
        <script src="Interface/Content/Assets/js/jspdf.umd.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="Interface/Content/Assets/modules/sweetalert/sweetalert.min.js"></script>
    </body>
</html>
