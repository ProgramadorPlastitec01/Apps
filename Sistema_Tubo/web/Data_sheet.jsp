<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Data_sheet.tld" prefix="DataSheet" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha Tecnica | ST</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <DataSheet:Data_sheet/>
                </div>
                <Alert:Alert/>
                <script type="text/javascript">
                    function SwitchValue() {
                        if (document.getElementById('State').checked == true) {
                            document.getElementById("State").value = 1;
                        } else {
                            document.getElementById("State").value = 0;
                        }
                    }
                </script>
                <script>
                    // This is an old version, for a more recent version look at
                    function maxLengthCheck(object)
                    {
                        if (object.value.length > object.maxLength)
                            object.value = object.value.slice(0, object.maxLength)
                    }
                    function mouseOver(id) {
                        document.getElementById("Text" + id).style.display = "block";
                    }
                    function mouseOut(id) {
                        document.getElementById("Text" + id).style.display = "none";
                    }
                </script>
                <script>
                    function showPages(id) {
                        var totalNumberOfPages = 5;
                        for (var i = 1; i <= totalNumberOfPages; i++) {
                            if (document.getElementById('page' + i)) {
                                document.getElementById('page' + i).style.display = 'none';
                            }
                        }
                        if (document.getElementById('page' + id)) {
                            document.getElementById('page' + id).style.display = 'block';
                        }
                    }
                </script>
            </div>
        </div>

        <script>
            function process(proc) {
                if (proc == "PP") {
                    document.getElementById("DivProces").style.display = "block";
                } else {
                    document.getElementById("DivProces").style.display = "none";
                }
            }
        </script>



        <!-- Tables -->
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Contenido/assets/js/paging.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/assets/js/Paging_div.js"></script>
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/Paging_div.js"></script>
        <script src="Interfaz/Contenido/assets/js/Filter.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
