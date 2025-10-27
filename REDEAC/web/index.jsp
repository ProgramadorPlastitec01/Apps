<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="icon" type="image/png" href="Interfaz/Contenido/Images/Logo.png"/>
        <link type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet">
        <script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
        <link type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css" rel="stylesheet">
        <link type="text/css" href="Interfaz/FontAwesome/css/all.css" rel="stylesheet">
        <link type="text/css" href="Interfaz/Contenido/Css/Login.css" rel="stylesheet">

        <!--Alertas-->
        <script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
        <link type="text/css" href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet">
        <script type="text/javascript">
            function sesion() {
                document.getElementById("prsDiv").style.opacity = "0"
                setTimeout(function () {
                    document.getElementById("prsDiv").style.display = "none"
                }, 500)
            }
        </script>
    </head>
    <body onload="setTimeout('sesion()', 1500);">
        <div class="container">
            <div class="box"></div>
            <div class="container-forms">
                <div class="container-info">
                    <div class="info-item">
                        <div class="table">
                            <div style="position: fixed;margin-left: 10px;"><p style="font-size: 14;"><b>Va 18.34.12</b></p></div>
                            <!--<div style="position: fixed;margin-left: 10px;"><p style="font-size: 14;"><b>VA 12.29.11</b></p></div>-->
                            <!--<div style="position: fixed;margin-left: 10px;"><p style="font-size: 14;"><b>VA 12.29.11</b></p></div>-->
                            <!--<div style="position: fixed;margin-left: 10px;"><p style="font-size: 14;">Va 09.21.10</p></div>-->
                            <!--<div style="position: fixed;margin-left: 10px;"><p style="font-size: 14;">Va 09.16.08</p></div>-->
                            <div class="table-cell">
                                <p>REDEAC</p>
                                <div class="btn">Iniciar</div>
                            </div>
                        </div>
                    </div>
                    <div class="info-item">
                        <div class="table">
                            <div style="position: fixed;margin-left: 170px;"><p style="font-size: 14;"><b>Va 18.34.12</b></p></div>
                            <!--<div style="position: fixed;margin-left: 170px;"><p style="font-size: 14;"><b>VA 12.29.11</b></p></div>-->
                            <!--<div style="position: fixed;margin-left: 170px;"><p style="font-size: 14;"><b>VA 12.29.11</b></p></div>-->
                            <!--<div style="position: fixed;margin-left: 170px;"><p style="font-size: 14;">Va 09.21.10</p></div>-->
                            <!--<div style="position: fixed;margin-left: 170px;"><p style="font-size: 14;">Va 09.16.08</p></div>-->
                            <div class="table-cell">
                                <p>Registrar Caso?</p>
                                <div class="btn">Registrar</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-form">
                    <div class="form-item log-in">
                        <div class="table">
                            <div class="table-cell">
                                <center><span style="font-size: 3em;color: graytext"><img src="Interfaz/Contenido/Images/user (1).png"></span></center>
                                <br><br>
                                <form action="Login?opc=1" method="post" id="formL" name="formL">
                                <center>
                                    <input type="text" name="txt_user" placeholder="Usuario" onchange="javascript:this.value = this.value.toUpperCase();"/>
                                    <input type="Password"  name="txt_pass" placeholder="Constraseña"/>
                                    <!--<div class="btn" onclick="javascript:document.formL.submit();">Iniciar</div>-->
                                    <br>
                                    <button class="button-82-pushable" role="button">
                                            <span class="button-82-shadow"></span>
                                            <span class="button-82-edge"></span>
                                            <span class="button-82-front text">
                                                Iniciar
                                            </span>
                                        </button>
                                    </center>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="form-item sign-up">
                        <div class="table">
                            <div class="table-cell">
                                <center>
                                    <span style="font-size: 3em;color: graytext"><img src="Interfaz/Contenido/Images/headset.png"></span><br><br>
                                    <form action="Login?opc=5" method="post" id="formC" name="formC">
                                        <input type='text' name='txt_documento' id='documento-id' value="" placeholder='Documento' style='margin: 0px;'  required><br>
                                        <input type='text' name='txt_codigo' id='codigo-id' value="" placeholder='Codigo' style='margin: 0px;'  required>
                                         <br>
                                        <button class="button-82-pushable" role="button">
                                            <span class="button-82-shadow"></span>
                                            <span class="button-82-edge"></span>
                                            <span class="button-82-front text">
                                                Ingresar
                                            </span>
                                        </button>
                                        </center>
                                        <!--                                        <br><input class="button-88"  type='submit' value="Registrar" /><br/><br/>-->
                                    </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container" style="position: absolute;margin-left: 28%;height: 400;top: 44%; opacity: 1;transition: 1.00s;" id="prsDiv">
            <div class="box"></div>
            <div style="position: absolute;top: 20%;left: 10%;">
                <img src="Interfaz/Contenido/Images/Logo.png" alt="Logo" width="250" /><br><br>
                <!--<center><b style="font-size: 14;color:#fff">Va 04.13.04</b></center>-->
                <!--<center><b style="font-size: 14;color:#fff">Va 09.16.08</b></center>-->
                <!--<center><b style="font-size: 14;color:#fff">Va 12.29.11</b></center>-->
                <center><b style="font-size: 14;color:#fff">Va 18.34.12</b></center>
            </div>
            <div style="position: absolute;top: 40%;left: 60%;">
                <img src="Interfaz/Contenido/Images/templatemo_logo.png" alt="Logo" width="200" />
            </div>
        </div>
        <script type="text/javascript" src="Interfaz/Contenido/Scripts/jquery-2.2.4.min.js"></script>
        <script type="text/javascript" src="Interfaz/Contenido/Scripts/Login.js"></script>
        <resultado:MuestraResultado/>
    </body>
</html>
