<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_information.tld" prefix="Information" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informacion</title>
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                        <!--<button onclick="exportarExcel()">Exportar a Excel</button>-->
                    <Information:Information/>
                </div>
            </div>
        </div>
                    <Alert:Alert/>
        <script>
            function exportarExcel() {
                fetch('http://localhost:8089/AppTI/DataExport?opt=1') // Llama al Servlet
                        .then(response => response.json())
                        .then(data => {
                            if (data.error) {
                                alert("Error al obtener datos");
                                return;
                            }

                            // Definir el orden correcto de las columnas
                            const columnOrder = [
                                "Nombre Equipo", "Estado", "Responsable", "Area", "Login Plastitec",
                                "IP", "MAC", "Red", "VLAN", "Win Instalado", "Office Instalado", "Antivirus",
                                "Internet", "VPN", "Skype", "Gmail", "Correo Interno", "Correo Externo",
                                "Factura", "Fecha Factura", "Licencia", "Fecha Licencia", "Proveedor",
                                "Garantia", "Fecha Garantia", "Proceso", "Tipo", "Punto de red", "Caracteristicas"
                            ];

                            // Asegurar que los objetos en `data` siguen este orden
                            const orderedData = data.map(item => {
                                let orderedItem = {};
                                columnOrder.forEach(col => {
                                    orderedItem[col] = item[col] !== undefined ? item[col] : ""; // Si no existe, poner vacío
                                });
                                return orderedItem;
                            });

                            // Crear la hoja de cálculo con las columnas ordenadas
                            let ws = XLSX.utils.json_to_sheet(orderedData, {header: columnOrder});

                            // Crear el libro de Excel
                            let wb = XLSX.utils.book_new();
                            XLSX.utils.book_append_sheet(wb, ws, "Datos");

                            // Descargar el archivo Excel
                            XLSX.writeFile(wb, "Test.xlsx");
                        })
                        .catch(error => console.error("Error:", error));
            }
        </script>
        <script src="Interface/Content/Assets/modules/chart.min.js"></script>
        <script src="Interface/Content/Assets/js/Paging_div.js"></script>
        <script src="Interface/Content/Assets/js/Filter.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/xlsxFull.js"></script>

    </body>
</html>
