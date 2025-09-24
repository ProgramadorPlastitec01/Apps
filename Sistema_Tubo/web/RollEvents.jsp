<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Alert" prefix="Alertas" %>
<%@taglib uri="/WEB-INF/tlds/Roll_events.tld" prefix="RollEvents" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evento Rollos</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css" >
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <RollEvents:RollEnvents/>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function MassiveId(ide) {
                var id = "[" + ide + "]";
                var cont = document.getElementById("idRoll").value;
                if (cont.includes(id)) {
                    document.getElementById("idRoll").value = cont.replace(id, "");
                } else {
                    document.getElementById("idRoll").value += id;
                }
            }
            function MassiveRoll(roll) {
                var id = "[" + roll + "]";
                var cont = document.getElementById("NumRoll").value;
                if (cont.includes(id)) {
                    document.getElementById("NumRoll").value = cont.replace(id, "");
                } else {
                    document.getElementById("NumRoll").value += id;
                }
            }
            function ConsultRegister(temp) {
                if (temp === 1) {
                    document.getElementById("temp1").value = 1;
                    document.getElementById("formRegisterQ").submit();
                }
                if (temp === 2) {
                    document.getElementById("temp1").value = 2;
                    document.getElementById("formRegisterQ").submit();
                }
            }
            function ColorPanel(num) {
                var element1 = document.getElementById("all-tab");
                var element2 = document.getElementById("approved-tab");
                var element3 = document.getElementById("quarantine-tab");
                var element4 = document.getElementById("refused-tab");
                if (element1.ariaSelected === "true" && num === 1) {
                    element1.classList.add("ColorPanelAll");
                } else if (element2.ariaSelected === "true" && num === 2) {
                    element2.classList.add("ColorPanelApv");
                } else if (element3.ariaSelected === "true" && num === 3) {
                    element3.classList.add("ColorPanelQrn");
                } else if (element4.ariaSelected === "true" && num === 4) {
                    element4.classList.add("ColorPanelRfc");
                }
            }
            function HiddenDivAll(num) {
                var id = document.getElementById("panel-all-" + num);
                id.classList.remove("show");
            }
            function HiddenDivApproved(num) {
                var id = document.getElementById("panel-approved-" + num);
                id.classList.remove("show");
            }
            function HiddenDivQuarantine(num) {
                var id = document.getElementById("panel-quarantine-" + num);
                id.classList.remove("show");
            }
            function HiddenDivRefused(num) {
                var id = document.getElementById("panel-refused-" + num);
                id.classList.remove("show");
            }
            function SubmitFormT(val) {
                if (val === 1) {
                    document.getElementById("temp2").value = 1;
                    document.getElementById("FormConsult").submit();
                } else if (val === 2) {
                    document.getElementById("temp5").value = 1;
                    document.getElementById("FormConsult").submit();
                } else {
                    document.getElementById("temp3").value = 1;
                    document.getElementById("FormConsult").submit();
                }
            }
            function SubmitFormE(temp4) {
                if (temp4 === 0) {
                    document.getElementById("temp4").value = 0;
                    document.getElementById("FormSubmit").submit();
                } else if (temp4 === 1) {
                    document.getElementById("temp4").value = 1;
                    document.getElementById("FormSubmit").submit();
                } else {
                    document.getElementById("temp4").value = 2;
                    document.getElementById("FormSubmit").submit();
                }
            }
            function ValidationRoll(val) {
                var idRolls = document.getElementById("RollOrder").value;
                let NumberRoll = parseInt(document.getElementById("NumRoll" + val).value);
                var EstRoll = "[" + NumberRoll + "]";
                if (idRolls.includes(EstRoll)) {
                    document.getElementById("BtVal").disabled = true;
                    document.getElementById("AlertRoll").style.display = 'block';
                    document.getElementById("AlertRoll").style.opacity = '1';
                } else {
                    document.getElementById("BtVal").disabled = false;
                    document.getElementById("AlertRoll").style.display = 'none';
                    document.getElementById("AlertRoll").style.opacity = '0';
                }
            }

        </script>
        <Alertas:Alert/>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/Paging_div.js"></script>
        <script src="Interfaz/Contenido/assets/js/Filter.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/> 

    </body>
</html>
