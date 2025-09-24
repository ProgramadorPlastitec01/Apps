<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Quality_certificate.tld" prefix="Certificate" %>
<%@taglib uri="/WEB-INF/tlds/Alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>R-GC-209 | ST</title>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interfaz/Contenido/assets/css/main.css" media="all"/>
        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
        <link rel="shortcut icon" href="Interfaz/Contenido/Imagen/Icon1.png" />

        <link rel="stylesheet" href="Interfaz/Contenido/assets/modules/select2/dist/css/select2.min.css"  >
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Certificate:QualityCertificate/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>
        <script>
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
        </script>
        <script>
            function translateId(id) {
                document.getElementById("id_summary").value = id;
            }
        </script>
        <script>
            function Redirec(anio) {
                location.href = "Quality_certificate?opc=1&ac_year=" + anio + "";
            }
        </script>
        <script>
            function printSection(el) {
                var getFullContent = document.body.innerHTML;
                var printsection = document.getElementById(el).innerHTML;
                document.body.innerHTML = printsection;
                window.print();
                document.body.innerHTML = getFullContent;
            }
        </script>

        <script language="Javascript">
            function imprSelec(nombre) {
                var ficha = document.getElementById(nombre);
                var ventimp = window.open(' ', 'popimpr');
                ventimp.document.write(ficha.innerHTML);
                ventimp.document.close();
                ventimp.print( );
                ventimp.close();
            }
        </script>
        <script>
            function exportTableToExcel(tableID, tableID2, tableID3, filename = '') {
                var downloadLink;
                var dataType = 'application/vnd.ms-excel';
                var tableSelect = document.getElementById(tableID);
                var tableSelect2 = document.getElementById(tableID2);
                var tableSelect3 = document.getElementById(tableID3);

                var htmlParcial_1 = tableSelect.outerHTML.replace(/ /g, '%20');
                var htmlParcial_2 = tableSelect2.outerHTML.replace(/ /g, '%20');
                var htmlParcial_3 = tableSelect3.outerHTML.replace(/ /g, '%20');
                var tableHTML = htmlParcial_1 + htmlParcial_2 + htmlParcial_3;

                // Specify file name
                filename = filename ? filename + '.xls' : 'excel_data.xls';

                // Create download link element
                downloadLink = document.createElement("a");

                document.body.appendChild(downloadLink);

                if (navigator.msSaveOrOpenBlob) {
                    var blob = new Blob(['ufeff', tableHTML], {
                        type: dataType
                    });
                    navigator.msSaveOrOpenBlob(blob, filename);
                } else {
                    // Create a link to the file
                    downloadLink.href = 'data:' + dataType + ', ' + tableHTML;

                    // Setting the file name
                    downloadLink.download = filename;

                    //triggering the function
                    downloadLink.click();
            }
            }
        </script>
        <script>
            function validateRlls(min, max) {
                var inmin = document.getElementById("minRoll").value;

                if (inmin < min || inmin > max) {
                    document.getElementById("btnRlls").disabled = true;
                } else {
                    document.getElementById("btnRlls").disabled = false;
                }
            }
        </script>
        <script>
            function validateRll2(min, max) {
                var inmax = document.getElementById("maxRoll").value;

                if (inmax > max || inmax < min) {
                    document.getElementById("btnRlls").disabled = true;
                } else {
                    document.getElementById("btnRlls").disabled = false;
                }
            }
        </script>
        <script src="Interfaz/Contenido/assets/modules/datatables/datatables.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interfaz/Contenido/assets/modules/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-datatables.js"></script>
        <script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>

        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/> 

        <!--THIS FILE-->
        <script src="Interfaz/Contenido/assets/js/page/forms-advanced-forms.js"></script>
        <script src="Interfaz/Contenido/assets/modules/select2/dist/js/select2.full.min.js"></script>
        <!-------->


    </body>
</html>
