<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formato</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/summernote/summernote-bs4.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/codemirror/lib/codemirror.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/codemirror/theme/duotone-dark.css">
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            td {
                border: 1px solid black;
            }
        </style>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                <div class="main-content" style="min-height: 694px;">
                    <div style="background: white" class="mt-5 mb-5"> 
                        <table style="width:100%">
                            <tr>
                                <td rowspan="3" class="text-center">
                                    <img src="Interface/Imagen/Logo.png" height="58px" alt="Logo">
                                    <div>
                                        <b>PLANTA PRODUCTOS
                                            <br/> MEDICO FARMACEUTICOS
                                        </b>
                                    </div>
                                </td>
                                <td colspan="3" class="text-center BoldText">INDICADORES DE GESTION</td>
                                <td colspan="3" class="text-center"><span class="BoldText">AREA</span><hr class="m-0">TECNOLOGIA DE INFORMACIÓN</td>
                            </tr>
                            <tr>
                                <td colspan="3" class="text-center BoldText ">TIEMPO DE PARADA  EQUIPOS</td>
                                <td colspan="3" class="text-center"><span class="BoldText">CONSECUTIVO</span><hr class="m-0">XXXMESXXX</td>
                            </tr>
                        </table>
                        <div style="border: 1px solid #e1dbdb;">
                            <div class="d-flex">
                                <div class="w-100" >
                                    <p class="m-2"><span class="BoldText">OBJETIVO:</span>
                                        <br>Disminuir el tiempo de parada de equipos por inconvenientes de Tecnología de Información (T.I)</p>
                                </div>
                                <div class="w-100">
                                    <p class="m-2"><span class="BoldText">COMO SE HACE:</span>
                                        <br>Se calcula el tiempo  teórico de producción  (TP)= SUM(Equipos 24 horas)+ SUM(Equipos 10 horas)+ SUM(Servidores)=24horas*6dias*4 semanas+ 10horas*5 dias*4 semanas+24horas*7dias*4semanas
                                        <br>Formula: (TPE/TP)*100</p>
                                </div>
                                <div class="w-100">
                                    <p class="m-2"><span class="BoldText">ALCANCE:</span>
                                        <br>Equipos de la organización</p>
                                </div>
                            </div>
                            <div class="d-flex">
                                <div class="w-100" >
                                    <p class="m-2"><span class="BoldText">META:</span>
                                        <br>0.25% PARADAS DE EQUIPOS. PARADA PRODUCCION: 0%</p>
                                </div>
                                <div class="w-100">
                                    <p class="m-2"><span class="BoldText">FRECUENCIA:</span>
                                        <br>Mensual</p>
                                </div>
                                <div class="w-100">
                                    <p class="m-2"><span class="BoldText">REPORTA A:</span>
                                        <br>Gerente General</p>
                                </div>
                            </div>
                             <!--Aqui debe ir la grafica--> 
                            <p class="BoldText m-2">DATOS:</p>
                            <div class="d-flex justify-content-around ">
                                <p></p>
                                <table style="width: 70%">
                                    <tr>
                                        <th class="p-2">Mes</th>
                                        <th class="p-2">Tiempo Trabajo (Minutos)</th>
                                        <th class="p-2">Parada Equipo (Minutos)</th>
                                        <th class="p-2">Parada Produccion</th>
                                        <th class="p-2">%PE</th>
                                        <th class="p-2">%PP</th>
                                    </tr>
                                    <tr>
                                        <td class="p-2">XXMESXX</td>
                                        <td class="p-2">XXTTMXX</td>
                                        <td class="p-2">XXPEMXX</td>
                                        <td class="p-2">XXPRXX</td>
                                        <td class="p-2">XXPEXX</td>
                                        <td class="p-2">XXPPXX</td>
                                    </tr>
                                </table>
                                <p></p>
                            </div>
                            <p class="BoldText m-2">ANALISIS</p>
                            <div class='ml-3 mb-3' style="border: 1px solid #e1dbdb; height: 150px; width: 97%">
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <Alerts:Alert/>
    <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
    <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
    <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
    <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
    <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>


    <script src="Interface/Content/Assets/modules/summernote/summernote-bs4.js"></script>
    <script src="Interface/Content/Assets/modules/codemirror/lib/codemirror.js"></script>
    <script src="Interface/Content/Assets/modules/codemirror/mode/javascript/javascript.js"></script>

    <!-- Script para configurar el gráfico -->
</body>
</html>

