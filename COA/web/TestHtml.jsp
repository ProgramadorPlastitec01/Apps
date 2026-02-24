
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
                <link rel="stylesheet" href="Interface/Content/Assets/modules/bootstrap/css/bootstrap.min.css">

    </head>
    <body>
        <!-- COPIA NO CONTROLADA -->
        <table class="table table-bordered mb-3" style="width:100%">
            <tr>
                <td colspan="10" style="height: 0px;" class="bg-secondary text-white text-center fw-bold py-1">
                    COPIA NO CONTROLADA
                </td>
            </tr>
        </table>

        <!-- ENCABEZADO -->
        <div class="d-flex align-items-start w-100 mb-3">
            <div class="d-flex w-100">
                <div class="me-3" style="width:19%">
                    <img src="Interface/Imagen/Logo.png" height="58px" alt="Logo" class="img-fluid">
                    <div class="me-3 m-2">
                        <h5 class="mb-0">PLASTITEC</h5>
                        <h7 class="mb-0">PLASTITEC TECNICOS S.A.S</h7></br>
                        <h7 class="mb-0">NIT 860.024.986-0</h7>
                    </div>
                </div>
                <div class="me-3 mr-2 small">
                    <p class="mb-0">CARRERA 56 No. 5C-72</p>
                    <p class="mb-0">PBX + 57 1 261 47 06</p>
                    <p class="mb-0">FAX + 57 1 262 42 08 BOGOTA COLOMBIA</p>
                    <p class="mb-0">www.plastitec-sa.com</p>
                </div>
                <div class="text-center flex-grow-1">
                    <div class="fw-bold border border-dark p-1">CERTIFICADO DE CALIDAD DUCTO / <span id="codeValue">R-GC-061 </span>V5</div>
                    <div class="fw-bold border border-dark p-1">TUBING QUALITY CERTIFICATE / <span id="consValue1">R-GC-061 </span> V5</div>
                    <div class="d-flex justify-content-between mt-2 w-100">
                        <!-- Fecha fabricación -->
                        <div class="border border-dark rounded p-1" style="width:30%">
                            <p class="mb-0 border-bottom border-dark">FECHA DE FABRICACION /</p>
                            <p class="mb-0 border-bottom border-dark">MANUFACTURE DATE</p>
                            <div class="d-flex text-center">
                                <div class="flex-fill border-top border-end border-dark">XYearMANX</div>
                                <div class="flex-fill border-top border-end border-dark">XMonthMANX</div>
                                <div class="flex-fill border-top border-dark">XDayMANX</div>
                            </div>
                        </div>
                        <!-- Código -->
                        <div class="border border-dark d-flex rounded align-items-center justify-content-center fw-bold" style="width:30%">
                            <h3 id="consValue" class="mb-0 editable" contenteditable="true">CC----</h3>
                        </div>
                        <!-- Fecha vencimiento -->
                        <div class="border border-dark rounded p-1" style="width:30%">
                            <p class="mb-0 border-bottom border-dark">FECHA DE VENCIMIENTO /</p>
                            <p class="mb-0 border-bottom border-dark">EXPIRATION DATE</p>
                            <div class="d-flex text-center">
                                <div class="flex-fill border-top border-end border-dark">XYearEXPX</div>
                                <div class="flex-fill border-top border-end border-dark">XMonthEXPX</div>
                                <div class="flex-fill border-top border-dark">XDayEXPX</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- DATOS CLIENTE -->
        <table class="table table-bordered table-sm align-middle text-center mb-3" style="width:100%">
            <tr>
                <td class="fw-bold">CLIENTE:</td>
                <td rowspan="2" id="clientValue" colspan="3">XClientX</td>
                <td class="fw-bold">CODIGO:</td>
                <td rowspan="2">XCodeX</td>
                <td class="fw-bold">ORDEN DE PRODUCCION</td>
                <td rowspan="2">XOrderX</td>
            </tr>
            <tr>
                <td class="fw-bold">CUSTOMER:</td>
                <td class="fw-bold">CODE</td>
                <td class="fw-bold">PRODUCTION ORDER:</td>
            </tr>
            <tr>
                <td class="fw-bold">DIRECCION:</td>
                <td rowspan="2">XAddressX</td>
                <td class="fw-bold">TELEFONO:</td>
                <td rowspan="2">XPhoneX</td>
                <td class="fw-bold">CIUDAD:</td>
                <td rowspan="2" colspan="2">XCityX</td>
                <td rowspan="2">XCountryX</td>
            </tr>
            <tr>
                <td class="fw-bold">DELIVERY:</td>
                <td class="fw-bold">PHONE:</td>
                <td class="fw-bold">CITY:</td>
            </tr>
            <tr>
                <td class="fw-bold">ORDEN CLIENTE:</td>
                <td rowspan="2">XClient_OrderX</td>
                <td class="fw-bold">FACTURA:</td>
                <td rowspan="2">XBillX</td>
                <td class="fw-bold">LISTA/REMISION:</td>
                <td rowspan="2">XReissue_listX</td>
                <td class="fw-bold">MUESTRA:</td>
                <td rowspan="2">XSampleX</td>
            </tr>
            <tr>
                <td class="fw-bold">CUSTOM ORDER:</td>
                <td class="fw-bold">INVOICE:</td>
                <td class="fw-bold">LIST/REMISSION:</td>
                <td class="fw-bold">SAMPLE:</td>
            </tr>
        </table>

        <!-- PRODUCTO -->
        <table class="table table-bordered table-sm mb-3" style="width:100%">
            <tr>
                <td class="fw-bold">PRODUCTO / PRODUCT</td>
                <td>XProductX</td>
                <td rowspan="4" class="text-center align-middle">
                    <div class="fw-bold">RESULTADO:</div>
                    <div><input name="result" type="radio" checked> Aprobado / Passed</div>
                    <div><input name="result" type="radio"> Rechazado / Failed</div>
                </td>
            </tr>
            <tr>
                <td class="fw-bold" >ANCHO / WIDTH</td>
                <td>XWidthX</td>
            </tr>
            <tr>
                <td class="fw-bold">LOTE # / LOT #</td>
                <td>XBatchX</td>
            </tr>
            <tr>
                <td class="fw-bold" >CANTIDAD / QUANTITY</td>
                <td id="AmountValue" contenteditable="true" >XQuatityGenX</td>
            </tr>
        </table>
        <div style="width:100%">
            <table class="table table-bordered table-sm mt-3 text-center align-middle">
                <!-- Encabezado sección materiales --> 
                <thead>
                    <tr class="table-secondary">
                        <th colspan="2">1. MATERIALES</th>
                        <th colspan="2">REFERENCIA</th>
                        <th colspan="2">LOTE #</th>
                        <th>CONSECUTIVO</th>
                    </tr>
                    <tr class="table-secondary">
                        <th colspan="2">MATERIALS</th>
                        <th colspan="2">REFERENCE</th>
                        <th colspan="2">LOT NUMBER</th>
                        <th>CONSECUTIVE</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="text-end">1.1</td>
                        <td class="text-start">COMPUESTO / COMPOUND</td>
                        <td colspan="2">REF1</td>
                        <td colspan="2" id="IdBatchM1">LBT1</td>
                        <td>COS1</td>
                    </tr>
                    <!-- CONTROL DIMENSIONAL --> 
                    <tr class="table-secondary fw-bold">
                        <td colspan="2">2. CONTROL DIMENSIONAL</td>
                        <td>UNIDADES</td>
                        <td>PARAMETRO</td>
                        <td colspan="2">RESULTADO</td>
                        <td>PROMEDIO</td>
                    </tr>
                    <tr class="table-secondary fw-bold">
                        <td colspan="2" rowspan="2">DIMENSIONAL CONTROL</td>
                        <td rowspan="2">UNITS</td>
                        <td rowspan="2">PARAMETER</td>
                        <td colspan="2">TEST RESULT</td>
                        <td rowspan="2">MEAN</td>
                    </tr>
                    <tr class="table-secondary fw-bold">
                        <td>MIN</td>
                        <td>MAX</td>
                    </tr>
                    <!-- FUNDA --> 
                    <tr>
                        <td>2.1</td>
                        <td class="fw-bold">DIMENSIONAL CONTROL </td>
                        <td colspan="5"></td>
                    </tr>
                    <tr>
                        <td class="text-end">2.1.1</td>
                        <td class='text-start'>Diámetro exterior / External Diameter</td>
                        <td>mm</td>
                        <td>PRM1</td>
                        <td>MIN1</td>
                        <td>MAX1</td>
                        <td>MEAN1</td>
                    </tr>
                    <tr>
                        <td class="text-end">2.1.2</td>
                        <td class="text-start">Diámetro interior / Internal Diameter</td>
                        <td>mm</td>
                        <td>PRM2</td>
                        <td>MIN2</td>
                        <td>MAX2</td>
                        <td>MEAN2</td>
                    </tr>
                    <tr>
                        <td class="text-end">2.1.3</td>
                        <td class="text-start">Longitud / Length</td>
                        <td>m</td>
                        <td>PRM3</td>
                        <td>MIN3</td>
                        <td>MAX3</td>
                        <td>MEAN3</td>
                    </tr>
                    <tr>
                        <td class="text-end">2.1.4</td>
                        <td class="text-start">Dureza / Hardness</td>
                        <td>Shore A</td>
                        <td>PRM4</td>
                        <td>MIN4</td>
                        <td>MAX4</td>
                        <td>MEAN4</td>
                    </tr>
                    <tr>
                        <td class="text-end">2.1.5</td>
                        <td class="text-start">Espesor de Pared / Wall Thickness</td>
                        <td>mm</td>
                        <td>PRM5</td>
                        <td>MIN5</td>
                        <td>MAX5</td>
                        <td>MEAN5</td>
                    </tr>
                    <tr>
                        <td class="text-end">2.1.6</td>
                        <td class="text-start">Rugosidad, Ra Cutt Off 0.8mm x 5 / Roughness</td>
                        <td>mm</td>
                        <td>PRM6</td>
                        <td>MIN6</td>
                        <td>MAX6</td>
                        <td>MEAN6</td>
                    </tr>
                    <!-- 3.OTRAS PRUEBAS --> 
                    <tr class="table-secondary fw-bold">
                        <td colspan="2">3. OTRAS PRUEBAS</td>
                        <td colspan="4">NORMA INTERNA</td>
                        <td>APROBADO</td>
                    </tr>
                    <tr class="table-secondary fw-bold">
                        <td colspan="2">OTHER TESTS</td>
                        <td colspan="4">INTERNAL STANDARD</td>
                        <td>APROVED</td>
                    </tr>
                    <tr>
                        <td>3.1</td>
                        <td class="text-start">Inspección Tubo / Tube Test</td>
                        <td colspan="4">I-GC-009</td>
                        <td><input type="checkbox" checked> Ok.</td>
                    </tr>
                    <tr>
                        <td>3.2</td>
                        <td class="text-start">Rugosidad / Roughness</td>
                        <td colspan="4">I-GC-021</td>
                        <td><input type="checkbox" checked> Ok.</td>
                    </tr>
                    <!-- 4. OBSERVACIONES / NOTES --> 
                    <tr class="table-secondary fw-bold">
                        <td colspan="2">4. OBSERVACIONES / NOTES</td>
                        <td colspan="5">5. DOCUMENTOS ANEXOS / ATTACHED DOCUMENTS</td>
                    </tr>
                    <tr>
                        <td rowspan="3" style="width:50%" class="editable" contenteditable="true" colspan="2">FICHA TECNICA XFichaTecnicaX."Mantener en almacén seco, evitar el agua, la humedad alta y la luz solar; abrir la primera bolsa en la esclusa y la segunda envoltura en la sala limpia".  / "Keep in dry warehouse. avoid water. high humidity and sun light; Open the first wrap bag in the first lock. and second wrap in the clean room".</td>
                        <td colspan="5" style="height: 50px;"  contenteditable="true">----</td>
                    </tr>
                    <tr>
                        <td colspan="2">Wilmer A. Hernández</td>
                        <td colspan="3"><div id="SignatureImage"></div></td>
                    </tr>
                    <tr>
                        <td colspan="2" rowspan="2">Nombre / Name</td>
                        <td colspan="3" rowspan="2">Firma autorizada / Authorized signature</td>
                    </tr>
                    <tr>
                        <td colspan="2">FECHA DEL DESPACHO: <div id="DateDispatch" style="border: 2px solid red; padding:4px;"><input type="date" id="dispatchDate" name="dispatchDate" class="editable completed" style="border:none; outline:none; background:transparent;"></div></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>

</html>
