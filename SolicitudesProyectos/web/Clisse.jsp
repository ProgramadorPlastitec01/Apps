<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/clisse.tld" prefix="Clisse" %>
<%@taglib uri="/WEB-INF/tlds/alertas.tld" prefix="Alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SP | Clisse</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <link rel="shortcut icon" href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css" />
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/LogoSP.png" />
    </head>
    <body >
        <!--    <body class="sidebar-mini">-->
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Clisse:Clisse/>
                </div>
            </div>
            <script type="text/javascript">
                function Validar() {
                    var controlA = parseFloat(document.getElementById("txt_a").value);
                    var controlB = parseFloat(document.getElementById("txt_b").value);
                    var controlC = parseFloat(document.getElementById("txt_c").value);
                    var controlD = parseFloat(document.getElementById("txt_d").value);
                    // Verificar si todos los controles son cero
                    if (controlA === "" || isNaN(parseFloat(controlA)) ||
                            controlB === "" || isNaN(parseFloat(controlB)) ||
                            controlC === "" || isNaN(parseFloat(controlC)) ||
                            controlD === "" || isNaN(parseFloat(controlD))) {
                        iziToast.info({
                            title: 'Alerta',
                            message: 'Por favor, ingrese valores en los campos.',
                            position: 'bottomRight'
                        });
                        return;
                    } else {
                        var min = Math.min(controlA, controlB, controlC, controlD);
                        var max = Math.max(controlA, controlB, controlC, controlD);
                        var tolerancia = parseFloat(document.getElementById("toleracia").value);
                        var diferencia = max - min;
                        if (diferencia > tolerancia) {
                            document.getElementById("estadoV").value = 3;
                        } else {
                            document.getElementById("estadoV").value = 1;
                            document.getElementById("FormClisse").submit();
                        }
                    }
                }
                function ValidarCuarentena() {
                    var controlA = parseFloat(document.getElementById("txt_a_c").value);
                    var controlB = parseFloat(document.getElementById("txt_b_c").value);
                    var controlC = parseFloat(document.getElementById("txt_c_c").value);
                    var controlD = parseFloat(document.getElementById("txt_d_c").value);
                    // Verificar si todos los controles son cero
                    if (controlA === "" || isNaN(parseFloat(controlA)) ||
                            controlB === "" || isNaN(parseFloat(controlB)) ||
                            controlC === "" || isNaN(parseFloat(controlC)) ||
                            controlD === "" || isNaN(parseFloat(controlD))) {
                        iziToast.info({
                            title: 'Alerta',
                            message: 'Por favor, ingrese valores en los campos.',
                            position: 'bottomRight'
                        });
                        return;
                    } else {
                        var min = Math.min(controlA, controlB, controlC, controlD);
                        var max = Math.max(controlA, controlB, controlC, controlD);
                        var tolerancia = parseFloat(document.getElementById("toleracia").value);
                        var diferencia = max - min;
                        if (diferencia > tolerancia) {
                            document.getElementById("estadoVC").value = 2;
                            document.getElementById("FormClisseCuarentena").submit();
                        } else {
                            document.getElementById("estadoVC").value = 1;
                            document.getElementById("FormClisseCuarentena").submit();
                        }
                    }
                }
                function ValidarEstadoFirma() {
                    var estadoControl = parseInt(document.getElementById("etdC").value);
                    if (estadoControl === 1) {
                        document.getElementById("FormVerificacion").submit();
                    } else if (estadoControl == 4) {
                        iziToast.info({
                            title: 'Alerta',
                            message: 'No se permite verificar y cerrar registro porque no se encuentran datos.',
                            position: 'bottomRight'
                        });
                    } else {
                        iziToast.info({
                            title: 'Alerta',
                            message: 'No se permite verificar y cerrar registro porque existen I.D en cuarentena.',
                            position: 'bottomRight'
                        });
                    }
                }
                function Imprimir() {
                    var contenedor = document.getElementById("Imprimir").innerHTML;
                    var frame = document.createElement("iframe");
                    frame.name = "frame1";
                    frame.style.position = "absolute";
                    frame.style.top = "-1000000px";
                    document.body.appendChild(frame);
                    var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
                    frameDoc.document.open();
                    frameDoc.document.write('<html><head><title>Imprimir</title>');
                    frameDoc.document.write('<link href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />');
                    frameDoc.document.write('<link href="Interfaz/Contenido/assets/modules/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />');
                    frameDoc.document.write('<link href="Interfaz/Contenido/assets/css/style.css" rel="stylesheet" type="text/css" />');
                    frameDoc.document.write('<link href="Interfaz/Contenido/assets/css/style.min.css" rel="stylesheet" type="text/css" />');
                    frameDoc.document.write('<link href="Interfaz/Contenido/assets/css/main.css" rel="stylesheet" type="text/css" />');
                    frameDoc.document.write('</head><body>');
                    frameDoc.document.write(contenedor);
                    frameDoc.document.write('</body></html>');
                    frameDoc.document.close();
                    setTimeout(function () {
                        window.frames["frame1"].focus();
                        window.frames["frame1"].print();
                        document.body.removeChild(frame);
                    }, 300);
                    return false;
                }
                function EnviarLetra(letra) {
                    document.getElementById("txt_letra").value = letra;
                    document.getElementById("txt_letraB").value = letra;
                    document.getElementById("txt_letraC").value = letra;
                    document.getElementById("txt_letraD").value = letra;
                    document.getElementById("etiqueta").innerHTML = letra;
                    document.getElementById("etiquetaB").innerHTML = letra;
                    document.getElementById("etiquetaC").innerHTML = letra;
                    document.getElementById("etiquetaD").innerHTML = letra;
                }
            </script>
            <Alertas:Alertas/>
            <script src="Interfaz/Contenido/assets/js/CamposAddClisetJs.js"></script>
            <script src="Interfaz/Contenido/assets/js/CamposClissetBCD.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
            <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
            <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
            <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
            <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>

            <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
            <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        </div>
    </body>
</html>
