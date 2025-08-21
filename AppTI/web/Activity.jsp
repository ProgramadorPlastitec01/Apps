<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_activity.tld" prefix="Activities" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actividades</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">

        <link rel="stylesheet" href="Interface/Content/Assets/modules/select2/dist/css/select2.min.css">

    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Activities:Activity/>
                </div>
            </div>
        </div>
        <Alerts:Alert/>

        <script>
            function activeDiv(first, second, activeBtn, inactiveBtn, val) {
                document.getElementById(first).style.display = 'block';
                document.getElementById(second).style.display = 'none';
                document.getElementById(activeBtn).classList.replace('btn-outline-dark', 'btn-yellow');
                document.getElementById(inactiveBtn).classList.replace('btn-yellow', 'btn-outline-dark');
                document.getElementById("typeSelec").value = val;
            }
        </script>


        <script>
            function selectButtonpc(value) {
                const btn1 = document.getElementById('btnpc1');
                const btn2 = document.getElementById('btnpc2');
                const input = document.getElementById('selectedPc'); // Input donde se guardará el valor
                btn1.classList.remove('active');
                btn2.classList.remove('active');
                if (value === 1) {
                    btn1.classList.add('active');
                } else if (value === 2) {
                    btn2.classList.add('active');
                }
                input.value = "[PC/" + value + "]";
            }
        </script>


        <script>
            function selectButtondv(value, id) {
                const input = document.getElementById('selectedDv');
                const btnx = document.getElementById('btnx' + id);
                if (btnx.classList.contains('active')) {
                    btnx.classList.remove('active');
                    input.value = input.value.replace("[DV/" + value + "]", "");
                } else {
                    btnx.classList.add('active');
                    input.value += "[DV/" + value + "]";
                }
            }
        </script>

        <script>
            document.querySelectorAll('.btn').forEach(button => {
                button.addEventListener('click', function () {
                    this.blur();
                });
            });
        </script>


        <script>
            function validForm() {
                const temporal = document.getElementById("typeSelec").value;
                let sltPc = "";
                let sltDv = "";
                if (temporal == 1) {
                    sltPc = document.getElementById("cbxPc").value;
                    sltDv = document.getElementById("cbxDv").value;
                } else if (temporal == 2) {
                    sltPc = document.getElementById("selectedPc").value;
                    sltDv = document.getElementById("selectedDv").value;
                }
                if (sltPc === "" && sltDv === "") {
                    $("#toastr-2").ready(function () {
                        iziToast.info({
                            title: '¡ATENCIÓN!',
                            message: 'Debe Seleccionar un tipo de computador o un tipo de dispositivo.',
                            position: 'topRight'
                        });
                    });
                } else {
                    document.getElementById("formActi").submit();
                }
            }
        </script>

        <script>
            function switchColorOrg(dp, ix) {
                const idBlue = document.getElementById('idVer').value;
                const checkbox = document.getElementById('checkOrange' + dp);
                if (idBlue != "") {
                    $("#toastr-2").ready(function () {
                        iziToast.info({
                            title: '¡ATENCIÓN!',
                            message: 'Hay una verificación seleccionada!',
                            position: 'topRight'
                        });
                    });
                    checkbox.checked = !checkbox.checked;
                } else {
                    const td = document.getElementById('orangeToggle' + dp);
                    if (td.classList.contains('toggleOrange')) {
                        td.classList.remove('toggleOrange');
                    } else {
                        td.classList.add('toggleOrange');
                    }
                    const data = "[" + ix + "]";
                    const idOrg = document.getElementById('idAct').value;
                    if (idOrg.includes(data)) {
                        document.getElementById("idAct").value = idOrg.replace(data, "");
                    } else {
                        document.getElementById("idAct").value += data;
                    }
                    if (document.getElementById('idAct').value == "") {
                        document.getElementById("bntEject").style.display = "none";
                    } else {
                        document.getElementById("bntEject").style.display = "block";
                    }
                }
            }

            function switchColorBle(dp, ix) {
                const idOrg = document.getElementById('idAct').value;
                const checkbox = document.getElementById('checkBlue' + dp);
                if (idOrg != "") {
                    $("#toastr-2").ready(function () {
                        iziToast.warning({
                            title: '¡ATENCIÓN!',
                            message: 'Hay una ejecución seleccionada! ',
                            position: 'topRight'
                        });
                    });
                    checkbox.checked = !checkbox.checked;
                } else {
                    const td = document.getElementById('blueToggle' + dp);
                    if (td.classList.contains('toggleBlue')) {
                        td.classList.remove('toggleBlue');
                    } else {
                        td.classList.add('toggleBlue');
                    }
                    const data = "[" + ix + "]";
                    const idBlue = document.getElementById('idVer').value;
                    if (idBlue.includes(data)) {
                        document.getElementById("idVer").value = idBlue.replace(data, "");
                    } else {
                        document.getElementById("idVer").value += data;
                    }
                    if (document.getElementById('idVer').value == "") {
                        document.getElementById("bntVerif").style.display = 'none';
                    } else {
                        document.getElementById("bntVerif").style.display = 'block';
                    }
                }
            }
        </script>


        <script>
            function FormEject() {
                document.getElementById("FoEje").submit();
            }

            function FormVerif() {
                document.getElementById("FoVer").submit();
            }
        </script>

        <script>
            document.getElementById('btnImprimir').addEventListener('click', () => {
                // Obtener el contenido de la sección a imprimir
                const contenido = document.getElementById('dataDocument').outerHTML;

                // Crear una nueva ventana para imprimir
                const ventanaImpresion = window.open('', '_blank', 'width=800, height=600');

                // Escribir el contenido HTML en la nueva ventana
                let cont = `<html>
                    <head>
                        <title>Imprimir</title>
                        <style>
                            body { 
                                font-family: Arial, sans-serif;
                                margin-top: 20mm;  /* Margen superior */
                                margin-bottom: 20mm; /* Margen inferior */
                            }
                            #content {
                                margin-top: 10mm;
                                margin-bottom: 10mm;
                            }
                            table {
                                width: 100%;
                                border-collapse: collapse; /* Evitar bordes duplicados */
                            }
                            table, th, td {
                                border: 1px solid black; /* Borde negro */
                            }
                            th, td {
                                padding: 8px; /* Espaciado interno */
                                text-align: left; /* Alineación del texto */
                            }
                        </style>
                    </head>
                    <body>
                        <div id="content">XXXDATAXXX</div>
                        <script>
                            window.onload = function() {
                                setTimeout(() => {
                                    window.print();
                                    window.close();
                                }, 500); // Esperar un poco para asegurar que todo cargue
                            };
                        <\/script>
                    </body>
                </html>`;

                // Reemplazar el marcador con el contenido real
                cont = cont.replace('XXXDATAXXX', contenido);

                // Escribir el contenido en la nueva ventana
                ventanaImpresion.document.open();
                ventanaImpresion.document.write(cont);
                ventanaImpresion.document.close();
            });
        </script>




        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>

        <script src="Interface/Content/Assets/modules/select2/dist/js/select2.full.min.js"></script>

        <script src="Interface/Content/Assets/js/page/forms-advanced-forms.js"></script>
    </body>
</html>
