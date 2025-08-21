<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_shift.tld" prefix="Shifts" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shift</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Shifts:Shift/>
                </div>
            </div>
        </div>
        <script>
            function swapColors(val, clas, data) {
                var color = clas;
                var intp = val + 1;
                document.getElementById("media" + val).classList.toggle(color);
                if (document.getElementById("media" + val).classList.contains(color)) {
                    document.getElementById("hiddenTurnoId" + intp).value = data + "/";
                    document.getElementById("ClassData").value = color;
                } else {
                    if (document.getElementById("ClassData").value == color) {
                        document.getElementById("ClassData").value = "";
                    }
                    clearSelections(intp, color);
                }
                document.getElementById("tempTurno").value = intp;
            }

            function swapColors2(val, clas, data) {
                var color = clas;
                var intp = val + 1;
                document.getElementById("mediax" + val).classList.toggle(color);
                if (document.getElementById("mediax" + val).classList.contains(color)) {
                    document.getElementById("hiddenTurnoxId" + intp).value = data + "/";
                    document.getElementById("ClassDatax").value = color;
                } else {
                    if (document.getElementById("ClassDatax").value == color) {
                        document.getElementById("ClassDatax").value = "";
                    }
                    clearSelections2(intp, color);
                }
                document.getElementById("tempTurnox").value = intp;
            }

            function RotateColors(val, ids) {
                var dataClass = document.getElementById("ClassData").value;
                if (dataClass == "") {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar un turno.',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    var element = document.getElementById("mediaUs" + val);
                    if (element.classList.contains(dataClass)) {
                        element.classList.toggle(dataClass);
                        element.classList.remove('disabled');
                    } else {
                        element.classList.toggle(dataClass);
                        element.classList.add('disabled');
                    }
                }
                var id = "[" + ids + "]";
                var temp = document.getElementById("tempTurno").value;
                var cont = document.getElementById("hiddenTurnoId" + temp).value;
                if (cont.includes(id)) {
                    document.getElementById("hiddenTurnoId" + temp).value = cont.replace(id, "");
                } else {
                    document.getElementById("hiddenTurnoId" + temp).value += id;
                }
            }

            function RotateColors2(val, ids) {
                var dataClass = document.getElementById("ClassDatax").value;
                if (dataClass == "") {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar un turno.',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    var element = document.getElementById("mediaUsx" + val);
                    if (element.classList.contains(dataClass)) {
                        element.classList.toggle(dataClass);
                        element.classList.remove('disabled');
                    } else {
                        element.classList.toggle(dataClass);
                        element.classList.add('disabled');
                    }
                }
                var id = "[" + ids + "]";
                var temp = document.getElementById("tempTurnox").value;
                var cont = document.getElementById("hiddenTurnoxId" + temp).value;
                if (cont.includes(id)) {
                    document.getElementById("hiddenTurnoxId" + temp).value = cont.replace(id, "");
                } else {
                    document.getElementById("hiddenTurnoxId" + temp).value += id;
                }
            }

            function clearSelections(turnoIndex, colorClass) {
                document.getElementById("hiddenTurnoId" + turnoIndex).value = "";
                var elements = document.querySelectorAll('.media.' + colorClass);

                elements.forEach(function (element) {
                    element.classList.remove(colorClass);
                    element.classList.remove('disabled');
                });
            }

            function clearSelections2(turnoIndex, colorClass) {
                document.getElementById("hiddenTurnoxId" + turnoIndex).value = "";
                var elements = document.querySelectorAll('.mediax.' + colorClass);

                elements.forEach(function (element) {
                    element.classList.remove(colorClass);
                    element.classList.remove('disabled');
                });
            }
        </script>
        <script>
            function moveWeekAct(week) {
                var dataWeek = week;
                if (dataWeek.includes("[1]")) {
                    document.getElementById("weekSelect").value = week.replace("[1]", "");
                    document.getElementById("CbxValidPog").value = "[1]";
                } else if (dataWeek.includes("[2]")) {
                    document.getElementById("weekSelect").value = week.replace("[2]", "");
                    document.getElementById("CbxValidPog").value = "[2]";
                }
            }
            function moveWeekAct2(week) {
                var dataWeek = week;
                if (dataWeek.includes("[1]")) {
                    document.getElementById("weekSelectx").value = week.replace("[1]", "");
                    document.getElementById("ValWeekx").value = "[1]";
                } else if (dataWeek.includes("[2]")) {
                    document.getElementById("weekSelectx").value = week.replace("[2]", "");
                    document.getElementById("ValWeekx").value = "[2]";
                }
            }

            function weekClean() {
                document.getElementById("ValWeek").value = "";
            }

            function weekClean2() {
                document.getElementById("ValWeekx").value = "";
            }

            function ejectForm() {
                var dat = document.getElementById("weekSelect").value;
                var shift = [
                    document.getElementById("hiddenTurnoId1").value,
                    document.getElementById("hiddenTurnoId2").value,
                    document.getElementById("hiddenTurnoId3").value,
                    document.getElementById("hiddenTurnoId4").value,
                    document.getElementById("hiddenTurnoId5").value,
                    document.getElementById("hiddenTurnoId6").value,
                    document.getElementById("hiddenTurnoId7").value,
                    document.getElementById("hiddenTurnoId8").value,
                    document.getElementById("hiddenTurnoId8").value,
                    document.getElementById("hiddenTurnoId10").value
                ];

                var hasValue = shift.some(function (turno) {
                    return turno.trim() !== "";
                });
                var containsBracket = shift.some(function (turno) {
                    return turno.includes("[");
                });
                if (dat == "") {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar una semana.',
                            position: 'bottomRight'
                        });
                    });
                } else if (hasValue == false) {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar un turno.',
                            position: 'bottomRight'
                        });
                    });
                } else if (containsBracket == false) {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar al menos una persona.',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    cargarDatos();
                    document.getElementById("formShift").submit();
                }
            }
            function ejectForm2() {
                var dat = document.getElementById("weekSelectx").value;
                var shift = [
                    document.getElementById("hiddenTurnoxId1").value,
                    document.getElementById("hiddenTurnoxId2").value,
                    document.getElementById("hiddenTurnoxId3").value,
                    document.getElementById("hiddenTurnoxId4").value,
                    document.getElementById("hiddenTurnoxId5").value,
                    document.getElementById("hiddenTurnoxId6").value,
                    document.getElementById("hiddenTurnoxId7").value,
                    document.getElementById("hiddenTurnoxId8").value,
                    document.getElementById("hiddenTurnoxId9").value,
                    document.getElementById("hiddenTurnoxId10").value
                ];

                var hasValue = shift.some(function (turno) {
                    return turno.trim() !== "";
                });
                var containsBracketOrComma = shift.some(function (turno) {
                    return turno.includes("[");
                }) || shift.some(function (turno) {
                    return turno.includes(",");
                });
                if (dat == "") {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar una semana.',
                            position: 'bottomRight'
                        });
                    });
                } else if (hasValue == false) {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar un turno.',
                            position: 'bottomRight'
                        });
                    });
                } else if (containsBracketOrComma == false) {
                    $("#toastr-3").ready(function () {
                        iziToast.info({
                            title: 'Atención',
                            message: 'Debe seleccionar al menos una persona.',
                            position: 'bottomRight'
                        });
                    });
                } else {
                    cargarDatos();
                    document.getElementById("formShiftx").submit();
                }
            }

        </script>





        <Alerts:Alert/>                  
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/js/page/components-user.js"></script>
</html>
