<!DOCTYPE html>
<!-- saved from url=(0055)http://themestrong.com/demo/argo/live_menu.html#contact -->
<html style="margin-top: 0 !important" lang="en" class="csstransforms csstransforms3d csstransitions"><!--<![endif]--><head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Automatic servlets</title>
        <link type="image/png" href="Interfaz/images/ICON_APPSPLASTITEC.ico" rel="icon" >
        <link rel="stylesheet" href="./Argo/Argo_files/style.css" type="text/css" media="all">
        <script languaje="Javascript">
            function startTime() {
                var fecha = new Date();
                var dia_mes = fecha.getDate();
                if (dia_mes < 10) {
                    dia_mes = '0' + dia_mes;
                }
                var mes = fecha.getMonth();
                mes += 1;
                if (mes < 10) {
                    mes = '0' + mes;
                }
                var anio = fecha.getFullYear();
                if (anio < 1900) {
                    anio = 1900 + fecha.getFullYear();
                }
                var h = fecha.getHours();
                var m = fecha.getMinutes();
                var s = fecha.getSeconds();
                m = checkTime(m);
                s = checkTime(s);
                var decimal = h + "." + m + "" + s;
                var t = setTimeout(startTime, 500);
                var semana = ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];
                var meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
                var dia_semana = "" + semana[fecha.getDay()];
                var mes_anio = "" + meses[fecha.getMonth()];
                //CALCULAR SEMANA DEL AÑO
                var validador = [2, 1, 7, 6, 5, 4, 3];
                var dia = eval(dia_mes);
                var mes = eval(mes);
                var ano = eval(anio);
                if (mes !== 0) {
                    mes--;
                }
                var dia_pri = new Date(ano, 0, 1);
                dia_pri = dia_pri.getDay();
                dia_pri = eval(validador[dia_pri]);
                var tiempo0 = new Date(ano, 0, dia_pri);
                dia = (dia + dia_pri);
                var tiempo1 = new Date(ano, mes, dia);
                var lapso = (tiempo1 - tiempo0);
                var semanas = Math.floor(lapso / 1000 / 60 / 60 / 24 / 7);
                if (dia_pri === 1) {
                    semanas++;
                }
                if (semanas === 0) {
                    semanas = 52;
                    ano--;
                }
                if (ano < 10) {
                    ano = '0' + ano;
                }
                // FIN
                document.getElementById('txt1').innerHTML = "Semana del año : " + semanas + "";
                document.getElementById('txt').innerHTML = dia_semana + " " + dia_mes + " de " + mes_anio + " del " + anio + " " + h + ":" + m + ":" + s;
//                if (decimal === "05.5001") {
//                    location.href = 'http://172.16.2.117:8084/Herramental/Bitacora?opc=1';
//                }
//                if (decimal === "15.0001") {
//                    location.href = 'http://172.16.2.117:8084/Herramental/Bitacora?opc=2&turno=1';
//                }
//                if (decimal === "12.3601") {
//                    location.href = 'http://172.16.2.117:8084/Herramental/Bitacora?opc=2&turno=2';
//                }
//                if (decimal === "7.0001") {
//                    location.href = 'http://172.16.2.117:8084/Herramental/Bitacora?opc=2&turno=3';
//                }
//                if (decimal === "15.0501") {
//                    location.href = 'http://172.16.1.138:8084/Registros_lab/Automatico?opc=1&tno=1';
//                }
//                if (decimal === "23.0501") {
//                    location.href = 'http://172.16.1.138:8084/Registros_lab/Automatico?opc=1&tno=2';
//                }
//                if (decimal === "7.0501") {
//                    location.href = 'http://172.16.1.138:8084/Registros_lab/Automatico?opc=1&tno=3';
//                }
//                if (decimal === "7.0001" && dia_semana === "Lunes") {
//                    location.href = 'http://172.16.2.111:8084/CVP/Reporte?opc=5';
//                }
//                if (decimal === "7.0050" && dia_semana === "Lunes") {
//                    location.href = 'http://172.16.2.111:8084/PMP_MF/Reportes?opc=4';
//                }
//                if (decimal === "7.1501" && dia_semana === "Lunes") {
//                    location.href = 'http://172.16.2.111:8084/CVP/Reporte?opc=6';
//                }
//                if (decimal === "4.0001" && dia_semana === "Lunes") {
//                    location.href = 'http://172.16.2.111:8084/Activos/EmailNotificacion?opc=4';
//                }
//                if (decimal === "7.3001") {
//                    location.href = 'http://172.16.2.111:8084/PMP/Informe?opc=6';
//                }
//                if (decimal === "7.1550" && dia_semana === "Lunes") {
//                    location.href = 'http://172.16.2.111:8084/PMP/Informe?opc=7';
//                }
            }
            function checkTime(i) {
                if (i < 10) {
                    i = "0" + i
                }
                return i;
            }
        </script>
        <script type="text/javascript">
        </script>
        <script type="text/javascript">
            function Enviar_caso(app) {
                window.onload = document.getElementById("Casos").style.display = "none";
                document.getElementById("Titulo_app").innerHTML = "Enviando notificaciones del Aplicativo " + app;
                window.onload = document.getElementById("Gif_send").style.display = "block";
            }
        </script>
        <style>
            html::-webkit-scrollbar {
                width: 10px;     /* Tamaño del scroll en vertical */
                height: 8px;    /* Tamaño del scroll en horizontal */
                /*display: none;   Ocultar scroll */
            }

            html::-webkit-scrollbar-thumb {
                background: black;
                border-radius: 4px;
                border: 1px solid white;
            }

            /* Cambiamos el fondo y agregamos una sombra cuando esté en hover */
            html::-webkit-scrollbar-thumb:hover {
                background: black;
                box-shadow: 0 0 2px 1px rgba(0, 0, 0, 0.2);
            }

            /* Cambiamos el fondo cuando esté en active */
            html::-webkit-scrollbar-thumb:active {
                background-color: black;
            }
        </style>
    </head>
    <body onload="startTime()">
        <div align='center' id='Gif_send' style='display:none'>
            <h2 id='Titulo_app' style="font-size: 30px;color: #1f6377;font-weight: bold;">Enviado notificaciones del</h2>
            <img src="Argo/Argo_files/Logos/Send_all.gif" alt=""/>
        </div>
        <div id='Casos' style='display:block'>
            <br />
            <div id="txt" style="font-size: 16px;color: #1f6377;font-weight: bold;float:right;padding-right: 100px;"></div><br />
            <div id="txt1" style="font-size: 16px;color: #1f6377;font-weight: bold;float:right;padding-right: 100px;"></div>
            <div style="padding-left: 10%;padding-right: 10%">
                <br />
                <h2>Automatic Servlet's</h2>
                <p>1 ) Aqui se disparan peticiones automaticas a los diferentes aplicativos.<br />
                    2 ) Tambien se designa boton para ejecutar manualmente las peticiones.</p>
                <br />
                <table style="width: 100%;border:2px solid grey">
                    <tr>
                        <td align="center"><b>APLICATIVO</b></td>
                        <td align="center"><b>QUE HACE</b></td>
                        <td align="center"><b>ENLACE</b></td>
                        <td align="center"><b>ENVIO MANUAL</b></td>
                        <td align="center"><b>PROGRAMACION</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/Activos.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Envia notificación del avance en la gestión de las requisiciones</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.111:8084/Activos/" style="color:#1f6377">Acceso al aplicativo Activos</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.111:8084/Activos/EmailNotificacion?opc=4' onclick="Enviar_caso('Activos')" style='color:#1f6377'>Manual</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los <br />Lunes 04:00am</td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/CVP.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Evalua todas las calificaciones cuales estan dentro o fuera de los 3 meses asignados de alerta, enviando notificaciones por correo a las áreas afectadas. </td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.111:8084/CVP/" style="color:#1f6377">Acceso al aplicativo CVP</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.111:8084/CVP/Reporte?opc=5' onclick="Enviar_caso('CVP')" style='color:#1f6377'>Manual</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Lunes, a las 07:00am</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/CVP.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Recuerda a las areas que actualizacen sus informes de calificacion para cumplir con la programación de una validación o calificación del proceso.</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.111:8084/CVP/" style="color:#1f6377">Acceso al aplicativo CVP</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.111:8084/CVP/Reporte?opc=6' onclick="Enviar_caso('CVP')" style='color:#1f6377'>Manual</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Lunes, a las 07:15am</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/Herramental_proceso.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Crea las bitacora del dia (Turno 1,2 y 3) de todas las maquinas de los procesos de Peletizado, extrusion pp y extrusion PVC</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.117:8084/Herramental/index.jsp" style="color:#1f6377">Acceso al aplicativo Herramental</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.117:8084/Herramental/Bitacora?opc=1' onclick="Enviar_caso('Herramental')" style='color:#1f6377'>Manual</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los dias 05:50am</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/Herramental_proceso.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Cambia el estado de los turnos de todas las maquinas</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.117:8084/Herramental/index.jsp" style="color:#1f6377">Acceso al aplicativo Herramental</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.117:8084/Herramental/Bitacora?opc=2&turno=1' onclick="Enviar_caso('Herramental')" style='color:#1f6377'>Manual Turno 1</a><br />
                            <a href='http://172.16.2.117:8084/Herramental/Bitacora?opc=2&turno=2' onclick="Enviar_caso('Herramental')" style='color:#1f6377'>Manual Turno 2</a><br />
                            <a href='http://172.16.2.117:8084/Herramental/Bitacora?opc=2&turno=3' onclick="Enviar_caso('Herramental')" style='color:#1f6377'>Manual Turno 3</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los dias <br />T1 02:15pm <br />T2 10:15pm <br />T3 06:15am</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/Registros_lab.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Cambia el estado de los turnos de todas las lineas</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.1.138:8084/Registros_lab/index.jsp" style="color:#1f6377">Acceso al aplicativo Registros LAB</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.1.138:8084/Registros_lab/Automatico?opc=1&tno=1' onclick="Enviar_caso('Herramental')" style='color:#1f6377'>Manual Turno 1</a><br />
                            <a href='http://172.16.1.138:8084/Registros_lab/Automatico?opc=1&tno=2' onclick="Enviar_caso('Registros Lab')" style='color:#1f6377'>Manual Turno 2</a><br />
                            <a href='http://172.16.1.138:8084/Registros_lab/Automatico?opc=1&tno=3' onclick="Enviar_caso('Registros Lab')" style='color:#1f6377'>Manual Turno 3</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los dias <br />T1 03:05pm <br />T2 11:05pm <br />T3 07:05am</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/PMP.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Envia recordatorio al área MTI para la actualización de horometros en el R-MTI-151.</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.111:8084/PMP" style="color:#1f6377">Acceso al aplicativo PMP</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.111:8084/PMP/Informe?opc=6' onclick="Enviar_caso('PMP')" style='color:#1f6377'>Manual</a><br/>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los dias <br /> 07:30am</b></td>
                    </tr>
                     <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/PMP.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Envia recordatorio al área MTI sobre ordenes de trabajo que sobrepasan 5 dias desde su programación.</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.111:8084/PMP" style="color:#1f6377">Acceso al aplicativo PMP</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.111:8084/PMP/Informe?opc=7' onclick="Enviar_caso('PMP')" style='color:#1f6377'>Manual</a><br/>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los dias <br /> 07:10am</b></td>
                    </tr>
                    <tr>
                        <td style="width: 7%;border:2px solid grey"><img src="./Argo/Argo_files/Logos/PMP_MF.png" alt="Portfolio1"></td>
                        <td style="width: 43%;border:2px solid grey" valign="top">Envia notificación semanal de Ordendes de trabajo programadas vs ejecutadas</td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.111:8084/PMP_MF/" style="color:#1f6377">Acceso al aplicativo Activos</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><a href='http://172.16.2.111:8084/PMP_MF/Reportes?opc=4' onclick="Enviar_caso('PMP_MF')" style='color:#1f6377'>Manual</a></td>
                        <td align="center" style="width: 25%;border:2px solid grey"><b>Todos los <br />Lunes 04:00am</td>
                    </tr>
                    <!--<tr>
    <td style="width: 7%;border:2px solid grey"><img src="" alt="Portfolio1"></td>
    <td style="width: 43%;border:2px solid grey" valign="top">	Limpia las sesiones inactivas de los aplicativos con mas flujo de usuarios y peticiones, asignandoles una frecuencia de recarga.</td>
    <td align="center" style="width: 25%;border:2px solid grey"><a href="http://172.16.2.117/" style="color:#1f6377">Acceso al administrador</a></td>
    <td align="center" style="width: 25%;border:2px solid grey">
                            
                            </td>
                            <td align="center" style="width: 25%;border:2px solid grey"><b>Cada 4 horas</b></td>
</tr>-->
                </table>
            </div>
        </div>
</html>