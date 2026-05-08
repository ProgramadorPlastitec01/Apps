<%@page import="Controladores_BD.PersonalJpaController"%>
<%@page import="Metodos.ConnectionSignature"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html>
    <head>
        <link href="Interfaz/login_style.css" rel="stylesheet"  type="text/css" media="all" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>SIRH | Iniciar Sesion</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'index.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'index.jsp');
            });
        </script>
        <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body>

            <div id="popup" class="popUpData" style="display: none;">
                <div class="windData">
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                        <h3>Confirmación de datos</h3>
                        <span class="fa fa-times fa-size_super_small" onclick="OpenValidData()" style="cursor: pointer;"></span>
                    </div>

                    <span>Indique los datos correspondientes:</span>
                    <form action="Capacitacion?opc=1" method="POST">
                        <center>
                            <div>
                                <span>Numero de cedula:</span><br>
                                <input type="number" name="docCapt" placeholder="Numero de identificación" >
                            </div>
                            <div style="">
                                <span>Codigo:</span><br>
                                <input type="number" name="codCapt" placeholder="Codigo" >
                            </div>

                            <div>
                                <button type="submit">Consultar</button>
                        </center>
                    </form>
                </div>
            </div>

        </div>


        <div class="piel">
        <Alertas:Alertas />
        <center>
            <br /><br />
            <div style="width: 600px;margin-top: 100px;" align='center'>
                <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                    <b style="font-size:42px;color:f5f5f6">SIRH</b>
                    <h1 style="font-size: 3.2em;color: #f5f5f6;">
                        <span class="fab fa-hornbill fa-size_normal" style="color: #f5f5f6"></span>
                    </h1>
                </div>
                <div style="float: left;width: 300px;height: 300px;">
                    <div id="DivId1" style="display: block;">
                        <div>SISTEMA DE INFORMACIÓN <br/> RECURSOS HUMANOS</div><br/>
                        <form action="Sesion?opc=1" method="post">
                            <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                            <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                            <input style="margin-top: 6px;width: 194px;" type="submit" value="Iniciar" />
                            <!--<b>Vp 00.00.00</b>
                            <b>Va 01.05.02</b>-->
                            <!--<b>Va 03.12.03</b>-->
                            <!--<b>Va 04.15.04</b>-->
                            <div style="padding: 2px 0 0 0 ;"><b>Va 05.16.05</b></div>
                        </form>
                    </div>
                </div>
                <div style="float: left;width: 600px;height: 150px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;">
                    <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                        La aplicación <b>SIRH</b> ayuda administrar la información del área de recursos humanos en cuanto a
                        Datos personales, datos de la Formación, de Ausencias e Incapacidades, de
                        Accidentes de trabajo y Enfermedades profesionales, de eventos Disiplinarios, las
                        Evaluaciones de competencias, las Capacitaciones, Dotaciones, entre otros.
                        <div style="position: relative;
                             bottom: -72px;
                             left: 21%;
                             background: white;
                             width: 270px;
                             box-shadow: 2px 3px 5px 0px #b7b7b7;
                             border: 1px solid white;
                             border-radius: 12px;text-align: center;" onclick="OpenValidData()">
                            <button style="    background: transparent;
                                    border: 1px solid transparent;
                                    height: 32%;
                                    font-size: 18px;cursor: pointer;"><b>Registros de Capacitacion </b> &nbsp;<i class="fas fa-search"></i></button>
                        </div>
                        <!--                            <div style="position: relative;
                                                         bottom: -72px;
                                                         left: 21%;
                                                         background: white;
                                                         width: 270px;
                                                         box-shadow: 2px 3px 5px 0px #b7b7b7;
                                                         border: 1px solid white;
                                                         border-radius: 12px;text-align: center;">
                                                        <button style="    background: transparent;
                                                                border: 1px solid transparent;
                                                                height: 32%;
                                                                font-size: 18px;cursor: pointer;" onclick="window.location.href = 'Capacitacion.jsp'"><b>Registros de Capacitacion </b> &nbsp;<i class="fas fa-search"></i></button>
                                                    </div>-->
                    </div>
                </div>
        </center>
    </div>

    <script type="text/javascript" language="javascript">
        function mostrarConvencion(id) {
            if (document.getElementById("Ventana" + id).style.display === "none") {
                document.getElementById("Ventana" + id).style.display = "block";
            } else if (document.getElementById("Ventana" + id).style.display === "block") {
                document.getElementById("Ventana" + id).style.display = "none";
            }
        }
        function CambiarColorDiv() {
            var ColorId1 = document.getElementById("option-1");
            var ColorId2 = document.getElementById("option-2");
            var divColor1 = document.getElementById("divColor1");
            var divColor2 = document.getElementById("divColor2");
            if (ColorId1.checked) {
                divColor1.style.color = '#f5f5f6';
                divColor2.style.color = '';
                document.getElementById("DivId1").style.display = 'block';
                document.getElementById("DivId2").style.display = 'none';
            } else if (ColorId2.checked) {
                divColor2.style.color = '#f5f5f6';
                divColor1.style.color = '';
                document.getElementById("DivId2").style.display = 'block';
                document.getElementById("DivId1").style.display = 'none';
            }
        }
    </script>

    <script>
        function OpenValidData() {
            if (document.getElementById("popup").style.display == "none") {
                document.getElementById("popup").style.display = "block";
            } else if (document.getElementById("popup").style.display == "block") {
                document.getElementById("popup").style.display = "none";
            }
        }
    </script>




    <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
    <script src="Interfaz/Firma/assets/bezier.js"></script>
    <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
    <script src="Interfaz/Firma/assets/json2.min.js"></script>
</body>
</html>
