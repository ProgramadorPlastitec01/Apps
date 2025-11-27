<!DOCTYPE html>

<html lang="en">
    <head>
        <%@ page contentType="text/html; charset=UTF-8" %>
        <%@page pageEncoding="UTF-8"%>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Argo/Interfaz/fontawesome/css/all.css">
        <link href="Argo/Interfaz/css/bootstrap_min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="Argo/Interfaz/js/bootstrap_bundle_min.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="Argo/Interfaz/css/menu.css" type="text/css">
        <script async="" src="Argo/Argo_files/analytics.js"></script><script src="./Argo/Argo_files/jquery.js"></script>
        <link rel="icon" type="image/png" href="Argo/Argo_files/Logos/Logo_solito.fw.png"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>APPS PT</title>
        <!-- Estilos de navidad para las apps "SNOW" -->
        <link rel="stylesheet" href="Argo/Interfaz/css/styleSnow.css" type="text/css">

        <!-- Estilos para halloween -->
        <!--<link rel="stylesheet" href="Argo/Interfaz/css/styleHalloween.css" type="text/css">-->
        <!--<script type="text/javascript" src="Argo/Interfaz/Alertas/dist/sweetalert.min.js"></script>-->
        <!--<link type="text/css" href="Argo/Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet">-->
        <!--<link type="text/css" href="Argo/Interfaz/css/Artificial.css" rel="stylesheet">-->
        <style>
            body{
                margin:0;
                text-align:center;
                font-family: Verdana;
            }
            h1 {
                text-align:center;
            }
            .container {
                width:90%;
                margin:0 auto;
            }
            input[type="radio"] {
                display:none;
            }
            label {
                width:200px;
                float:left;
                text-align:center;
                background:#ffffff;
                box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
                color:#222222;
                padding:0.5%;
                margin:0.5%;
                margin-bottom:30px;
                cursor:pointer;
            }

            input[type="radio"][id="blue"]:checked + label {
                background: #04918d;
            }

            input[type="radio"][id="blue"]:checked ~ .red, input[type="radio"][id="blue"]:checked ~ .green, input[type="radio"][id="blue"]:checked ~ .orange {
                width:0;
                height:0;
                padding:0;
                margin:0;
                opacity:0;
            }

            input[type="radio"][id="red"]:checked + label {
                background:#720cb3;
            }
            input[type="radio"][id="red"]:checked ~ .blue, input[type="radio"][id="red"]:checked ~ .green, input[type="radio"][id="red"]:checked ~ .orange {
                width:0;
                height:0;
                padding:0;
                margin:0;
                opacity:0;
            }

            input[type="radio"][id="green"]:checked + label {
                background:#0da15c;
            }
            input[type="radio"][id="green"]:checked ~ .blue, input[type="radio"][id="green"]:checked ~ .red, input[type="radio"][id="green"]:checked ~ .orange {
                width:0;
                height:0;
                padding:0;
                margin:0;
                opacity:0;
            }
            input[type="radio"][id="orange"]:checked + label {
                background: #d67600;
            }
            input[type="radio"][id="orange"]:checked ~ .blue, input[type="radio"][id="orange"]:checked ~ .green, input[type="radio"][id="orange"]:checked ~ .red {
                width:0;
                height:0;
                padding:0;
                margin:0;
                opacity:0;
            }

            input[type="radio"][id="purple"]:checked + label {
                background: #06b1ed;
            }
            input[type="radio"][id="purple"]:checked ~ .blue, input[type="radio"][id="purple"]:checked ~ .green, input[type="radio"][id="purple"]:checked ~ .red, input[type="radio"][id="purple"]:checked ~ .orange {
                width:0;
                height:0;
                padding:0;
                margin:0;
                opacity:0;
            }

            .tile {
                width: 190px;
                height: 190px;
                float:left;
                transition: 0.2s;
                margin:1%;
                padding:0%;
            }

            .button-33 {
                color: white;
                cursor: pointer;
                display: inline-block;
                font-family: CerebriSans-Regular,-apple-system,system-ui,Roboto,sans-serif;
                padding: 7px 20px;
                text-align: center;
                text-decoration: none;
                transition: all 250ms;
                border: 0;
                font-size: 16px;
                user-select: none;
                -webkit-user-select: none;
                touch-action: manipulation;
            }
        </style>
    </head>

    <!--<body style="overflow-x: hidden;  background-size: auto; background-repeat: repeat; background-attachment: fixed;"  onload="alerData()">-->
    <body style="overflow-x: hidden; background: white;" onload="alerData()">

        <!--HALLOWEN-->
        <!--<body id="bodyWeen" class="bodyWeen_da" style="overflow-x: hidden; 
              background-repeat: no-repeat;
              background-size: cover;
              background-attachment: fixed;
              background-attachment: fixed;" onload="alerData()">-->
        <!--FIN HALLOWEN-->
        <header>
            <nav id="caja" class="navegacion">
                <ul class="menu" id="menu_d">
                    <li><a href="#"><b>Ambiente</b></a>
                        <ul class="submenu">
                            <li><a href="http://172.16.2.122:8084/Aplicativos_Plastitec_Pruebas/"><i class="fas fa-parking"></i> Pruebas </a></li>
                            <li><a href="http://172.16.2.122:8084/Aplicativos_Plastitec_Historica/"><i class="fas fa-heading"></i> Historico </a></li>
                            <li><a href="http://172.16.5.99:8084/AppSupport/"><i class="fas fa-headset"></i> Soporte </a></li>
                        </ul>
                    </li>
                    <li><a href="#"><b>Reportes</b></a>
                        <ul class="submenu">
                            <li><a href="#" onclick="javascript:location.href = 'Consulta_traslasdo_Lote_General.xlsm';" ><i class="fas fa-file-alt"></i> Traslado General</a></li>
                            <li><a href="#" onclick="javascript:location.href = 'Consulta_traslasdo_Lote_Requisicion.xlsm';" ><i class="fas fa-file-alt"></i> Traslado Requisiciones</a></li>
                            <li><a href="" onclick="javascript:location.href = 'Verificacion_Etiquetas.xlsm';"><i class="fas file-alt"></i> Etiquetas</a></li>
                        </ul>
                    </li>
                    <li class="li_1" style="margin-left:5%;margin-top: 1%;">
                        <img src="Argo/Argo_files/Logos/Logo_solito.fw.png" height="30">
                    </li>
                    <li><a href="#"><b>Verificaciones</b></a>
                        <ul class="submenu" style="width: 11%;">
                            <li><a href="http://172.16.2.117:8084/Validacion_ped/"><i class="fas fa-archive"></i> Pedidos</a></li>
                        </ul>
                    </li>
                    <li><a href="http://172.16.2.117:8084/Herramental/Consulta_catologo?opc=1&idF=0&txt_bus=" target="_blank"><b>Catalogos Defectos</b></a><span class="burbuja" style="top:8px;right: 120px;padding-bottom: 18px;border: 2px solid #c42419;"></span></li>
                </ul>
            </nav>
        </header>
        <!--                <div class="firework"></div>
                        <div class="firework"></div>
                        <div class="firework"></div>
                        <div class="firework"></div>        
                        <div class="firework"></div>
                        <div class="firework"></div>
                        
                        <div class="firework"></div>
                        <div class="firework"></div>
                        <div class="firework"></div>
                        <div class="firework"></div>        
                        <div class="firework"></div>
                        <div class="firework"></div>-->


        <a><button id="boton" class="btn_dropDown" onclick="divLogin()"><i id="ico" style="color: black;" class="fas fa-arrow-down"></i></button></a>

        <div class="ModalNews" id="modalnws" style="display: none;">
            <h2>Mensaje para todos!</h2>
            <h6>Nuevo modulo de capacitaciones</h6>
            <div>
                <video width="320" height="240" controls>
                    <source src="Argo/Interfaz/imges_24/Cap.webm" type="video/webm">
                </video>
            </div>
        </div>

        <!--<a><button id="boton" class="btn_dropDown" onclick="divLogin()"><img id="ico" src="Argo/Interfaz/images/baston.png" width="20"></button></a>-->
        <!--<a><button id="boton" class="btn_dropDown" onclick="divLogin()"><img id="ico" src="Argo/Interfaz/images/bat.png"></button></a>-->
        <a><button id="boton" class="btn_dropDown" onclick="divLogin()"><i id="ico" class="fas fa-arrow-up"></i></button></a>
        <div class="DivSmall">
            <img src="Argo/Interfaz/images/newest.png" width="24" alt=""/>
        </div>
        <div>
            <a class="phone">
                <button id="btn_dp" onclick="divLoginR()" class="phone2" style="background: #fff;
                        border: 2px solid #19284b;"><i style="font-size: 20px;" class="fas fa-headset"></i></button>
                <!--<button id="btn_dp" onclick="divLoginR()" class="phone2" style="background: #eb5d27;
                        border: 1px solid black"><img src="Argo/Interfaz/images/terror_1.png" width="34"></button>-->
                <!--                                <button id="btn_dp" onclick="divLoginR()" class="phone2" style="background: #fff;
                                                        border: 2px solid #bf1913; padding: 0%;"><i class="fas fa-gift"></i></button>-->
                <button id="btn_dp" onclick="divVideo()" class="phone3" style="background: #fff;
                        border: 2px solid #1c2b4d; padding: 0%;"><i class="fas fa-play"></i></button>

                <button id="btn_dp" onclick="ReunionBtn()" class="phone4" style="background: #fff;
                        border: 2px solid #1c2b4d; padding: 0%;"><i class="far fa-calendar-alt"></i></button>
            </a>
        </div>
        <div id="myModal" class="modal">
            <div class="modal-content">
                <div class="img-container">
                    <span class="close" onclick="closeModal()">&times;</span>
                    <a href="https://calendar.app.google/UC4mhnqE4eNiifRS8" target="_blank">
                        <img src="Argo/Argo_files/Logos/ImagenReunion.jpg" alt="Imagen de ejemplo">
                    </a>
                    <a href="https://calendar.app.google/UC4mhnqE4eNiifRS8" target="_blank" class="btn-float">
                        Reservar
                    </a>
                </div>
            </div>
        </div>
        <script>
            var clic = 1;
            function divLoginR() {

                if (clic == 1) {
                    document.getElementById("cont_case").style.visibility = "hidden";

                    clic = clic + 1;

                } else {
                    document.getElementById("cont_case").style.visibility = "visible";

                    clic = 1;
                }
            }
        </script>
        <script>
            function divVideo() {
                if (document.getElementById("modalnws").style.display === "block") {
                    document.getElementById("modalnws").style.display = "none";
                } else {
                    document.getElementById("modalnws").style.display = "block";
                }
            }
            function ReunionBtn() {
                document.getElementById("myModal").style.display = "block";
                document.body.style.overflow = "hidden";
            }

            function closeModal() {
                document.getElementById("myModal").style.display = "none";
                document.body.style.overflowY = "auto"; // ✅ Restaura scroll vertical

            }

// Cerrar si clickea fuera del modal
            window.onclick = function (event) {
                let modal = document.getElementById("myModal");
                if (event.target === modal) {
                    closeModal();
                }
            }
        </script>

        <div class="cont_case" id="cont_case">
            <i class="fas fa-caret-right" style="font-size: 39px;position: absolute;right: -14px;top: -5px;color: #101010; "></i>
            <div class="cont_numbs">
                <h5>Contacto con TI!</h5>
                <p style="margin: 0px;">- 3175023662</p>
                <p style="margin: 0px;">- Ext. 250 / 235 </p>
                <p style="margin: 0px; font-size: 14px;">- Soporte.ti@plastitec-sa.com</p>
                <hr>
            </div>
            <div class="cont_regCase">
                <h5>Soporte <b style="color:#582b77;">REDEAC</b></h5>
                <form action="http://172.16.2.117:8084/REDEAC/Login?opc=5" method="post">
                    <div class="input-group input-group-sm mb-3">
                        <input type="number" class="form-control" style="color: #582b77;background: transparent;border: none;border-bottom: 1px solid #582b77;" placeholder="Ingrese Documento" name="txt_documento" id="documento-id" oninput="if(value.length>11)value=value.slice(0,11)" required><br/>
                    </div>
                    <div class="input-group input-group-sm mb-3">
                        <input type="number" class="form-control" style="color: #582b77;background: transparent;border: none;border-bottom: 1px solid #582b77;" placeholder="Ingrese Codigo" name="txt_codigo" id="codigo-id" oninput="if(value.length>4)value=value.slice(0,4)" required><br>
                    </div>
                    <button type="submit" class="button-35" role="button">Subir</button>
                </form>
            </div>
        </div>
        <!--<div style="height: 160px; align-items: center;">-->
        <!--<img src="Argo/Interfaz/images/Wise_men.png" alt="alt" style="width: 10%;"/>-->
        <!--</div>-->
        <!-- <p style="position: absolube;margin-top: 69px;margin-left: 10px;font-weight: bold;background: #b7b7b7;padding: 12px;border-radius: 7px;">Favor pulsar las teclas <br> Control + Shift + R para recargar</p> -->
        <!--<section style="padding-bottom: 10px;border-bottom: 1px solid #ddd">-->


        <!--<section style="padding-bottom: 10px;">-->
        <div style="margin: 3%;">
            <!--<img style="background: white;border-radius: 17px" src="Argo/Interfaz/images/Navidad20242.fw.png" width="75%">-->
            <!--<img style="background: white;border-radius: 17px" src="Argo/Interfaz/images/plast_gorro.png">-->
            <!--<img style="background: white;border-radius: 17px" src="Argo/Interfaz/images_24/">-->
            <img src="Argo/Interfaz/images/plastNT.png" alt=""/>
            <!--<img src="Argo/Interfaz/images/plast_halloween_hw4.fw.png">-->
            <!--</div>-->
            <!--</section>-->
            <!--<div  onclick="switchbk()">-->
            <!--<button onclick="switchbk()">cambiar</button>-->
        </div>

        <div style="width: 105%;">
            <div class="container"> 
                <input type="radio" id="reset" name="color"/>
                <label for="reset" class="button-34"  style="margin-left: 15px;">Todas</label>

                <input type="radio" id="green" name="color"/>
                <label for="green" class="button-34">Producción</label>

                <input type="radio" id="red" name="color"/>			  
                <label for="red" class="button-34">Bitacoras</label>

                <input type="radio" id="blue" name="color" />
                <label for="blue" class="button-34">Otros</label>

                <input type="radio" id="orange" name="color" />
                <label for="orange" class="button-34">Externas</label>

                <input type="radio" id="purple" name="color" />
                <label for="purple" class="button-34">Correo</label>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Activos.png" alt="tec" width="200" style="">
                        </div>
                        <div class="back Activos" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Activos</h1>
                            <button class="button-33 activos" role="button" 
                                    onclick="javascript:window.open('http://172.16.2.111:8084/Activos/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile red">
                    <div class="card card_bc">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Bitacora_calidad.png" alt="tec" width="200" style="">
                        </div>
                        <div class="back BitacoraC" style="background: #ede2e2;"> 
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Bitacora Calidad</h1>
                            <button class="button-33 bc" role="button" 
                                    onclick="javascript:window.open('http://172.16.2.111:8084/Bitacora_calidad/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile red">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Bitacora_general.png" alt="tec" width="200" style="">
                        </div>
                        <div class="back BitacoraG" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Bitacora General</h1>
                            <button class="button-33 bg" role="button" 
                                    onclick="javascript:window.open('http://172.16.2.111:8084/Bitacora/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">Ingresar</button>
                        </div>
                    </div>	
                </div>

                <div class="tile red">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Bitacora_produccion.png" alt="tec" width="200" style="">
                        </div>
                        <div class="back BitacoraP" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Bitacora Produccion</h1>
                            <button class="button-33 bp" role="button" 
                                    onclick="javascript:window.open('http://172.16.2.117:8084/Bitacora_produccion/Ingreso.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <!--<img src="Argo/Interfaz/images/CopecNv.fw.png" alt="tec" width="200">-->
                            <img src="Argo/Interfaz/images/CGBC.png" alt="tec" width="180">
                            <div class="DivFront">
                                <img src="Argo/Interfaz/images/newest.png" width="50" alt=""/>
                            </div>
                            <!--<img src="Argo/Interfaz/images/Copec_hw.fw.png" alt="tec" width="200">-->
                        </div>
                        <div class="back ControlM" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">CBGC</h1>
                            <button  class="button-33 mcb" role="button" 
                                     onclick="javascript:window.open('http://172.16.5.99:8084/CBGC/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>	
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <!--<img src="Argo/Interfaz/images/CopecNv.fw.png" alt="tec" width="200">-->
                            <img src="Argo/Interfaz/images/Copec.png" alt="tec" width="200">
                            <!--<img src="Argo/Interfaz/images/Copec_hw.fw.png" alt="tec" width="200">-->
                        </div>
                        <div class="back copec" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Copec</h1>
                            <button  class="button-33 cpec" role="button" 
                                     onclick="javascript:window.open('http://172.16.1.138:8084/Copec/Login.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>	
                </div>

                <div class="tile green">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Control_formulas_2.png" alt="tec" width="200">
                        </div>
                        <div class="back controlF" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Control Formulas</h1>
                            <button  class="button-33 cF" role="button" 
                                     onclick="javascript:window.open('http://172.16.5.99:8084/ControlFormulas/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile green">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Control_grafado.png" alt="tec" width="200">
                        </div>
                        <div class="back controlG" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Control Grafado</h1>
                            <button  class="button-33 cG" role="button" 
                                     onclick="javascript:window.open('http://172.16.5.99:8084/ControlGrafado/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile green">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Control_pruebas.png" alt="tec" width="200">
                        </div>
                        <div class="back controlP" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Control Pruebas</h1>
                            <button  class="button-33 cP" role="button" 
                                     onclick="javascript:window.open('http://172.16.5.99:8084/Control_pruebas', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/CVP.png" alt="tec" width="200">
                        </div>
                        <div class="back cvp" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">CVP</h1>
                            <button  class="button-33 cVp" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/CVP/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Controles_microbiologicos.png" alt="tec" width="200">
                        </div>
                        <div class="back ControlM" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Controles Microbiologicos</h1>
                            <button  class="button-33 mcb" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/Controles_microbiologicos/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile orange">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/imges_24/Daruma_produccion_Final.png" alt="tec" width="180">
                        </div>
                        <div class="back Daruma" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Daruma</h1>
                            <button  class="button-33 Drm" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.99/app.php/staff/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>
                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Diseno_desarrollo.png" alt="tec" width="200">
                        </div>
                        <div class="back DyD" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">D&D</h1>
                            <button  class="button-33 dyd" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/Diseno_desarrollo/index.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile orange">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Factura1_NE.png" alt="tec" width="150">
                        </div>
                        <div class="back F1" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Factura NE</h1>
                            <button  class="button-33 fp" role="button" 
                                     onclick="javascript:window.open('https://app.factura1.com.co/EmisionNomina/login.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile orange">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Factura1.png" alt="tec" width="150">
                        </div>
                        <div class="back F1" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Factura 1</h1>
                            <button  class="button-33 fp" role="button" 
                                     onclick="javascript:window.open('https://app.factura1.com.co/emisionColapiV2/login.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>
                <div class="tile green">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Generacion_lotes_2_1.png" alt="tec" width="190">
                        </div>
                        <div class="back GLotes" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Generacion De Lotes</h1>
                            <button  class="button-33 gl" role="button" 
                                     onclick="javascript:window.open('http://172.16.5.99:8084/Generacion_Lotes/index.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Herramental_proceso.png" alt="tec" width="200">
                        </div>
                        <div class="back herramental" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Herramental <br/> Proceso </h1>
                            <button  class="button-33 hp" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/Herramental/index.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile orange">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Infotrack.png" alt="tec" width="200">
                        </div>
                        <div class="back Infotrack" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Infotrack</h1>
                            <button  class="button-33 inft" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.115:8081/Login.aspx', '', 'width=auto,height=auto,left=50,\n\
											top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile green">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Inspeccion_manga.png" alt="tec" width="200">
                        </div>
                        <div class="back Manga" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Inspeccion Manga</h1>
                            <button  class="button-33 im" role="button" 
                                     onclick="javascript:window.open('http://172.16.5.99:8084/Inspeccion_manga/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Locativos.png" alt="tec" width="200">
                        </div>
                        <div class="back Locativos" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Locativos</h1>
                            <button  class="button-33 lct" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/Locativos/index.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>
                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/LogoUp.fw.png" alt="tec" width="180" style="margin-top: 0%">
                            <!--<img src="Argo/Interfaz/images/IconW.fw.png" alt="tec" width="140" style="margin-top: 14%">-->
                        </div>
                        <div class="back Nexus" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Nexus</h1>
                            <button  class="button-33 nx" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/AppTI/index.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>
                <div class="tile orange">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Plastitec_tags_2.png" alt="tec" width="200">
                            <!--<img src="Argo/Interfaz/images/Logo_F_3.png" alt="tec" width="200">-->
                        </div>
                        <div class="back PV4" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Plastitec Tags</h1>
                            <button  class="button-33 plas" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.119/TagsApp4/auth/signin', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/PMP_MF.png" alt="tec" width="200">
                        </div>
                        <div class="back PMPMF" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;"> PMP MTF</h1>
                            <button  class="button-33 pmt" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/PMP_MF/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/PMP.png" alt="tec" width="200">
                        </div>
                        <div class="back PMPMI" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">PMP MI</h1>
                            <button  class="button-33 pmi" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/PMP/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/MetrologiaV2.png" alt="tec" width="170" style="margin-top: 11px;">
                        </div>
                        <div class="back PVM" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">PVM</h1>
                            <button  class="button-33 pvmm" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/PVM/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile red">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Redeac_2.png" alt="tec" >
                        </div>
                        <div class="back REDEAC" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">REDEAC</h1>
                            <button  class="button-33 rdc" role="button"><a style="color:white; text-decoration: none" target="_blank" href="http://172.16.2.117:8084/REDEAC">Ingresar</a></button>
                        </div>
                    </div>
                </div>

                <div class="tile green">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Registros_lab.png" alt="tec" width="200">
                        </div>
                        <div class="back LAB" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Registros Lab</h1>
                            <!--Anterior: http://172.16.2.117:8084/Registros_lab/index.jsp --> 
                            <button  class="button-33 rl" role="button" 
                                     onclick="javascript:window.open('http://172.16.1.138:8084/Registros_lab/index.jsp', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>

                    </div>
                </div>


                <div class="tile red">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/RegistroPesaje.png" alt="tec" width="150" style="margin-top: 13%">
                            <!--<img src="Argo/Interfaz/images/RegistroPesajeWeen.fw.png" alt="tec" width="150" style="margin-top: 13%">-->
                        </div>
                        <div class="back Locativos" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Registro Pesaje</h1>
                            <!--                            <button  class="button-33 lct" role="button" 
                                                                 onclick="javascript:window.open('http://172.16.2.117:8084/Aplicativos_Plastitec/Ventana_Matenimiento.jsp', '', 'width=auto,height=auto,left=50,\n\
                                                                                                            top=50,toolbar=yes');
                                                                         void 0">Ingresar</button>-->
                            <button  class="button-33 lct" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/Registro_pesaje/', '', 'width=auto,height=auto,left=50,\n\
                                                                                                            top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Reunion.png" alt="tec" width="200">
                        </div>
                        <div class="back REUNION" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Reunion PT</h1>
                            <button  class="button-33 rpt" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/Reunion/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/SIRH.png" alt="tec" width="200">
                        </div>
                        <div class="back SIRH" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">SIRH</h1>
                            <button  class="button-33 srh" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.111:8084/SIRH/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/LogoSP.png" alt="tec" width="150" style="margin-top: 9%;">
                        </div>
                        <div class="back Proyectos" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Solicitudes Proyectos</h1>
                            <button  class="button-33 sp" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/SolicitudesProyectos/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/ST_Desc_2.png" alt="tec" width="150" style="margin-top: 23%">
                        </div>
                        <div class="back SistemaT" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Sistema Tubo</h1>
                            <button  class="button-33 cVp" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/Sistema_Tubo/', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                            <!--                                                        <span>Proximamente...</span>
                                                                                    <img src="Argo/Interfaz/images/Loading_ST.gif" width="50px">-->
                        </div>
                    </div>
                </div>

                <div class="tile blue">
                    <div class="card">
                        <div class="front">
                            <!--<img src="Argo/Interfaz/images/WP_SagWeen.fw.png" alt="tec" width="170" style="margin-top: 8%">-->
                            <img src="Argo/Interfaz/images/WP_Sag.png" alt="tec" width="170" style="margin-top: 8%">
                        </div>
                        <div class="back LAB" style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">SAGRILAFT</h1>
                            <!--                            <button  class="button-33 rl" role="button" 
                                                                 onclick="javascript:window.open('https://sagrilaft.plastitec-sa.com/SAGRILAFT/', '', 'width=auto,height=auto,left=50,\n\top=50,toolbar=yes');
                                                                         void 0">Ingresar</button>-->
                            <button  class="button-33 rl" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.98:8084/SAGRILAFT/', '', 'width=auto,height=auto,left=50,\n\top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                            <!--<button  class="button-33 rl" role="button" 
                                     onclick="javascript:window.open('http://172.16.2.117:8084/Aplicativos_Plastitec/Ventana_Mantenimiento.jsp', '', 'width=auto,height=auto,left=50,\n\
                                                                                top=50,toolbar=yes');
                                             void 0">Ingresar</button>-->
                            <!--                                                        <span>Proximamente...</span>
                                                                                    <img src="Argo/Interfaz/images/Loading_ST.gif" width="50px">-->
                        </div>
                    </div>
                </div>

                <div class="tile purple">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/Zho2.png" alt="tec" width="170" style="margin-top: 4%">
                            <!--<img src="Argo/Interfaz/images/Zho_ween.png" alt="tec" width="170" style="margin-top: 24%">-->
                        </div>
                        <div class="back " style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 3%;">Zoho Mail</h1>
                            <button style="margin-bottom: 10px;" class="button-33 rl" role="button" 
                                    onclick="javascript:window.open('https://accounts.zoho.com/signin?service_language=es&servicename=VirtualOffice&signupurl=https://www.zoho.com/es-xl/mail/zohomail-pricing.html&serviceurl=https://mail.zoho.com', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">Ingresar</button>

                            <button style="font-size: 14px;
                                    padding: 4px 14px 4px 14px;
                                    border-radius: 6px; margin-top: 8px;"  class="button-33 zho" role="button" 
                                    onclick="javascript:window.open('Argo/Interfaz/video/ZohoMail.mp4', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">¿Como usar?</button>
                        </div>
                    </div>
                    <!--                    <video width="320" height="240" controls>
                                            <source src="Argo/Interfaz/video/ZohoMail.mp4" type="video/mp4">
                                            Your browser does not support the <code>video</code> tag.
                                        </video>-->
                </div>

                <div class="tile purple">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/ZhoCliq.png" alt="tec" width="170" style="margin-top: 24%">
                        </div>
                        <div class="back " style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Zoho Cliq</h1>
                            <button  class="button-33 rl" role="button" 
                                     onclick="javascript:window.open('https://cliq.zoho.com/mpchat.do', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                             void 0">Ingresar</button>
                        </div>
                    </div>
                </div>


                <div class="tile purple" style="margin-bottom: 60px;">
                    <div class="card">
                        <div class="front">
                            <img src="Argo/Interfaz/images/365.png" alt="tec" width="180" style="margin-top: 18%">
                        </div>
                        <div class="back " style="background: #ede2e2;">
                            <h1 style="color: black; padding: 5%; padding-top: 10%;">Email 365</h1>
                            <button  class="button-33 rl" role="button" 
                                     onclick="javascript:window.open('https://outlook.office.com/mail/', '', 'width=auto,height=auto,left=50,\n\
                                                                                top=50,toolbar=yes');
                                             void 0">Ingresar</button>

                            <button style="font-size: 14px;
                                    padding: 4px 14px 4px 14px;
                                    border-radius: 6px; margin-top: 8px;"  class="button-33 zho" role="button" 
                                    onclick="javascript:window.open('Argo/Interfaz/video/Intro365.mp4', '', 'width=auto,height=auto,left=50,\n\
										top=50,toolbar=yes');
                                            void 0">¿Como usar?</button>

                            <!--<b>PROXIMAMENTE....</b>-->
                        </div>
                    </div>
                </div>


            </div>
        </div>


        <!--        <div onclick="Animate()">
                    <img src="Argo/Interfaz/images/Calabaza_br.fw.png" alt="" style="position: absolute;bottom: -86%;right: 77px;" onclick="Animate()"/>
                </div>-->
        <!--<img id="imgTest" class="imgTest" src="Argo/Interfaz/images/plastEsterEggs.png" style="position: absolute;margin: auto;text-align: center;left: 37%;bottom: -37%;">-->

        <!--<footer id="footer" style="display: flex; justify-content: space-between; padding: 5px; align-items: right;">-->
        <footer id="footer" style="padding: 1px;">
            <div class="container" style="text-align: left;">
                <div style="display: flex;justify-content: end;align-items: center;">
                    <div>
                        &copy; 2025 <a href="" style="color: white;">PLASTITEC</a>
                    </div>
                    <!--                                        <div>
                                                                <img src="Argo/Interfaz/images/Navidad.png" alt="" width="380px"/>
                                                            </div>-->
                </div>
            </div>
            <!--                       <div style="">
                                        <img src="Argo/Interfaz/images/corona.png" style="margin: 0;" width="8%">
                                    </div>-->
            <!--<div style="margin-right: 30px; width: 33%;text-align: end;">-->
            <!--<h2>Feliz Navidad</h2>-->
            <!--<img src="Argo/Interfaz/images/Navidad_2.png">-->
            <!--<img src="Argo/Interfaz/images/Feliz_amor_amistad.png">--> 
            <!--</div>-->
        </footer>
        <script>
            var clic = 1;
            function divLogin() {
                if (clic == 1) {
                    document.getElementById("caja").style.height = "0px";
                    document.getElementById("menu_d").style.display = "none";
                    document.getElementById("ico").classname = "fas fa-angle-double-up";
                    clic = clic + 1;
                } else {
                    document.getElementById("caja").style.height = "60px";
                    document.getElementById("menu_d").style.display = "flex";
                    document.getElementById("ico").classname = "fas fa-angle-double-up";
                    clic = 1;
                }
            }
        </script>
        <script>

        </script>
        <!--        <script>
                    function switchBk() {
                        const clasBod = document.getElementById("bodyWeen").classname;
                        if (clasBod == "bodyWeen_da") {
                            document.getElementById("bodyWeen").classname = "bodyWeen_oc";
                        } else if (clasBod == "bodyWeen_oc") {
                            document.getElementById("bodyWeen").classname = "bodyWeen_da";
                        }
        //                alert("Buenos dias");
                    }
                    setInterval(switchBk, 1000);
                </script>-->
        <script>
            function Animate() {
                if (document.getElementById("imgTest").style.display == "block") {
                    document.getElementById("imgTest").style.display = "none";
                }
                if (document.getElementById("imgTest").style.display == "none") {
                    document.getElementById("imgTest").style.display = "block";
                }
            }
        </script>
        <!--        <script>
                    function alerData() {
                        swal({
                            title: "Bienvenido!",
                            text: "Se han agregado cambios a la pagina de aplicativos, favor recargar. <br> Pulsar las teclas Ctrl + Shift + R <br><img src='Argo/Interfaz/images/Teclado.png' width='250px;'>",
                            type: "info",
                            html: true,
                        });
                    }
                </script>-->
        <!--JULIO-->
        <!--<script src="Argo/Interfaz/js/snowjs.js" ></script>-->
        <!--SEPTIEMBRE-->
        <!--<script src="Argo/Interfaz/js/heart.js" ></script>-->
        <!--OCTUBRE-->
        <!--<script src="Argo/Interfaz/js/halloween.js" ></script>-->
        <!--DICIEMBRE1-->
        <!--<script src="Argo/Interfaz/js/Navidad.js" ></script>-->
    </body>

</html>