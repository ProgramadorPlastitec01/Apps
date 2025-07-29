<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld"  prefix="Alert"%>
<%@taglib uri="/WEB-INF/tlds/Tld_indicator.tld" prefix="Indicator" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Indicador</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <script src="Interface/Content/Assets/modules/jquery.min.js"></script>
        <style>
            /* Aplica el estilo del scrollbar al área de desplazamiento de la tabla */
            #table-1_wrapper .dataTables_scrollBody::-webkit-scrollbar {
                width: 10px;     /* Tamaño del scroll en vertical */
                height: 8px;     /* Tamaño del scroll en horizontal */
                /*display: none;*/  
            }

            #table-1_wrapper .dataTables_scrollBody::-webkit-scrollbar-thumb {
                background: #0b0025;
                border-radius: 4px;
                border: 1px solid white;
            }

            /* Cambiamos el fondo y agregamos una sombra cuando esté en hover */
            #table-1_wrapper .dataTables_scrollBody::-webkit-scrollbar-thumb:hover {
                background: #0b0025;
                box-shadow: 0 0 2px 1px rgba(0, 0, 0, 0.2);
            }

            /* Cambiamos el fondo cuando esté en active */
            #table-1_wrapper .dataTables_scrollBody::-webkit-scrollbar-thumb:active {
                background-color: #0b0025;
            }

        </style>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Indicator:Indicator/>
                </div>
            </div>
        </div>
        <Alert:Alert/>
        <script>
            $(document).ready(function () {
                $('#table-1').DataTable({
                    "paging": false, // Desactiva la paginación
                    "ordering": true, // Mantiene el orden
                    "searching": true, // Mantiene el filtro de búsqueda
//                    "scrollY": "400px", // Altura del área desplazable
                    "scrollCollapse": true, // Permite colapsar la vista al tamaño del contenido
                    "destroy": true           // Destruye cualquier instancia previa
                });
            });
        </script>
        <script>
            function ViewIndicatorSection(Cont) {
                const views = ["View1", "View2", "View3", "View4"];
                const buttons = [
                    {id: "Btn1", activeClass: "btn-info", inactiveClass: "btn-outline-info"},
                    {id: "Btn2", activeClass: "btn-warning", inactiveClass: "btn-outline-warning"},
                    {id: "Btn3", activeClass: "btn-danger", inactiveClass: "btn-outline-danger"},
                    {id: "Btn4", activeClass: "btn-secondary", inactiveClass: "btn-outline-secondary"}
                ];

                views.forEach((view, index) => {
                    document.getElementById(view).style.display = (index + 1 === Cont) ? "block" : "none";
                });

                buttons.forEach((button, index) => {
                    const btnElement = document.getElementById(button.id);
                    if (index + 1 === Cont) {
                        btnElement.classList.add(button.activeClass);
                        btnElement.classList.remove(button.inactiveClass);
                    } else {
                        btnElement.classList.add(button.inactiveClass);
                        btnElement.classList.remove(button.activeClass);
                    }
                });
            }
            function bigImg(x) {
                x.style.height = "64px";
                x.style.width = "64px";
            }

            function normalImg(x) {
                x.style.height = "32px";
                x.style.width = "32px";
            }
            function SendDataCount() {
                var Value1 = document.getElementById("Data1").value;
                var CalcValue = Value1 * 60;
                document.getElementById("GetData1").innerText = Value1;
                document.getElementById("GetData2").innerText = CalcValue;
                document.getElementById("TotalH").value = Value1;
                document.getElementById("TotalM").value = CalcValue;
                document.getElementById("MinuteJob").innerText = CalcValue;
                var PC = document.getElementById("PC_data").value;
                var PP = document.getElementById("PP_data").value;
                var PE = (PC / CalcValue * 100);
                var PDP = (PP / CalcValue * 100);
                document.getElementById("PE").innerText = PE.toFixed(4);
                document.getElementById("PDP").innerText = PDP.toFixed(3);
                var StopPrd = "";
                if (PP === "0.0") {
                    StopPrd = "No se presenta parada de producción por incovenientes del área de Tecnología de Información.";
                    document.getElementById("Analysis").innerText = StopPrd;
                } else {

                }
                var CantTicket = document.getElementById("CantTicket").value;
                if (CantTicket !== 0) {
                    document.getElementById("Analysis").innerText += CantTicket;
                }
                var TypeServ = document.getElementById("TypeServ").value;
                if (TypeServ !== "") {
                    document.getElementById("Analysis").innerText += TypeServ;
                }
                var ResultCl = document.getElementById("ResultCl").value;
                if (ResultCl !== "") {
                    document.getElementById("Analysis").innerText += ResultCl;
                }
            }
        </script>


        <script src="Interface/Content/Assets/modules/chart.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
