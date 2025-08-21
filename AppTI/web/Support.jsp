<%@taglib uri="/WEB-INF/tlds/Tld_support.tld" prefix="Supports" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Support</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        
        <link rel="stylesheet" href="Interface/Content/Assets/css/components.css">
        
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Supports:Support/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>
        <script>
            function ViewContentPending(count) {
                var DivTicket = document.getElementsByClassName("div-ticket");
                var IdPendingDiv = document.getElementById("IdPendingDiv" + count + "");
                for (var i = 0; i < DivTicket.length; i++) {
                    DivTicket[i].classList.remove("active");
                }
                var DivPendingContent = document.getElementById("DivPendingContent" + count + "");
                var DivContent = document.getElementsByClassName("div-content");
                for (var i = 0; i < DivContent.length; i++) {
                    DivContent[i].style.display = "none";
                }
                IdPendingDiv.classList.add("active");
                DivPendingContent.style.display = "block";
            }
        </script>

        <script>
            function SwitchView(contOne, contTwo, btnOne, btnTwo) {
                document.getElementById(contOne).style.display = "block";
                document.getElementById(contTwo).style.display = "none";
                document.getElementById(btnOne).style.display = "block";
                document.getElementById(btnTwo).style.display = "none";
            }
        </script>

        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>
        <script src="Interface/Content/Assets/modules/chart.min.js"></script>
        <!--<script src="Interface/Content/Assets/js/page/modules-chartjs.js"></script>-->
    </body>
</html>
