<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib  uri="/WEB-INF/tlds/Cafe.tld" prefix="Cafe" %>
<%@page import="Controladores_BD.ParametrosJpaController"%>
<%@page import="java.util.List"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>CAFE</title>
        <link href="Interfaz/Css/Css_cafe.css" rel="stylesheet" type="text/css" media="all" />
        <script type = "text/javascript" >
            history.pushState(null, null, 'CAFE.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'CAFE.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
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
                    var anio = fecha.getYear();
                    if (anio < 1900) {
                        anio = 1900 + fecha.getYear();
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
                    var mess = eval(mes);
                    var ano = eval(anio);
                    if (mess != 0) {
                        mess--;
                    }
                    var dia_pri = new Date(ano, 0, 1);
                    dia_pri = dia_pri.getDay();
                    dia_pri = eval(validador[dia_pri]);
                    var tiempo0 = new Date(ano, 0, dia_pri);
                    dia = (dia + dia_pri);
                    var tiempo1 = new Date(ano, mess, dia);
                    var lapso = (tiempo1 - tiempo0);
                    var semanas = Math.floor(lapso / 1000 / 60 / 60 / 24 / 7);
                    if (dia_pri == 1) {
                        semanas++;
                    }
                    if (semanas == 0) {
                        semanas = 52;
                        ano--;
                    }
                    if (ano < 10) {
                        ano = '0' + ano;
                    }
                    // FIN
                    document.getElementById('txt3').innerHTML = dia_semana;
                    document.getElementById('txt').innerHTML = "Fecha : " + anio + " / " + mes + " / " + dia_mes;
                    document.getElementById('txt2').innerHTML = "Hora : " + h + ":" + m + ":" + s + "";
                    document.getElementById('txt1').innerHTML = "Semana del año : " + (semanas + 1);
            <%
                String ipAddress = request.getRemoteAddr();
                ParametrosJpaController JpaParametros = new ParametrosJpaController();
                List lst_parametros = null;
                lst_parametros = JpaParametros.ConsultarParametrosxCategoria("Ips_permitidas");
                if (lst_parametros != null) {
                    Object[] obj_parametros = (Object[]) lst_parametros.get(0);

                    if (obj_parametros[2].toString().contains(ipAddress)) {
            %>
                    if (decimal === "11.0501") {
                        location.href = 'http://172.16.1.246:8080/SIRH_CES/CES?opc=3';
                    }
                    if (decimal === "19.0501") {
                        location.href = 'http://172.16.1.246:8080/SIRH_CES/CES?opc=3';
                    }
                    if (decimal === "04.0501") {
                        location.href = 'http://172.16.1.246:8080/SIRH_CES/CES?opc=3';
                    }
            <%
                            }
                        }
            %>
                }
                function checkTime(i) {
                    if (i < 10) {
                        i = "0" + i
                    }
                    return i;
                }
        </script>
        <script>
            function Ocultar_datos() {
                try {
                    var datos = document.getElementById('Tr_datos').innerHTML;
                    if (datos.includes('ESCANEAR') || datos.includes('NO PERMITIDO')) {
                    } else {
                        location.href = 'http://172.16.1.246:8080/SIRH_CES/CAFE?opc=1&dts=0';
                    }
                } catch (error) {
                    location.href = 'http://172.16.1.246:8080/SIRH_CES/CAFE?opc=3';
                }
            }
        </script>
    </head>
    <body onload="startTime();
            javascript:setTimeout(Ocultar_datos, 2000);">
    <center>
        <div id='Div_circle' style="width:60%">
            <table style="width:100%">
                <tr>
                    <td rowspan="2" style="height: 60%;width: 50%;padding:10px;">
                        <div class="circle" style="height: 600px;">
                            <br />
                            <!--<img src="Interfaz/Gif/coffee-88.gif.webp" width="120px" style="border-radius: 24px" onclick="javascript:location.href = 'CES?opc=1&dts=';" >-->
                            <%
                                String ipAddress2 = request.getRemoteAddr();
                                List lst_parametros2 = null;
                                lst_parametros2 = JpaParametros.ConsultarParametrosxCategoria("Ips_permitidas");
                                if (lst_parametros2 != null) {
                                    Object[] obj_parametros2 = (Object[]) lst_parametros2.get(0);

                                    if (obj_parametros2[2].toString().contains(ipAddress2)) {
                            %>
                            <span class="fas fa-mug-hot fa-size_medio" style='color:#fff' onclick="javascript:location.href = 'CES?opc=1&dts=';"></span>
                            <%
                            } else {
                            %>
                            <span class="fas fa-mug-hot fa-size_medio" style='color:#fff'></span>
                            <%
                                    }
                                }
                            %>

                            <h1><b style='text-shadow:none'></b>CAFÉ</h1>
                            <hr />
                            <br />
                            El sistema lleva registro de las marcaciones <br /> de entrada y salida del personal a trávés de <br /> la lectura de un código de barras asignado.
                            <br /><br />
                            <hr />
                            <br/>
                            <div id="txt3" style="font-size: 35px;color:#fff;font-weight: bold;text-shadow: 2px 2px #1b000a;"></div>
                            <div id="txt" style="font-size: 26px;color:#1b000a;font-weight: bold;"></div>
                            <div id="txt2" style="font-size: 35px;color:#fff;font-weight: bold;text-shadow: 2px 2px #1b000a;"></div>
                            <div id="txt1" style="font-size: 24px; color: #00fefe;font-weight: bold;text-shadow: 2px 2px #323232;"></div>
                        </div>
                    </td>
                    <td style="height:5%;width:50%;padding:10px;">
                        <div class='circle' style="height: 100px">
                            <form action='CAFE?opc=2' method='post'>
                                <h3>Escanear codigo de barras</h3>
                                <input type='text' name='Txt_codbar' id='Txt_codbar' maxlength='5' autofocus='on' autocomplete='off' required placeholder='Codigo de barras' />
                            </form>
                        </div>
                    </td>
                </tr>
                <Cafe:Cafe/>
            </table>
        </div>
    </center>
</body>
</html>