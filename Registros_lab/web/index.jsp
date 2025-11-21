<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Registros LAB</title>
            <script type = "text/javascript" >
                history.pushState(null, null, 'index.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'index.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <style>
                    .DivVideo{
                        border: 1px solid black;
                        background: white;
                        font-size: 22px;
                        margin-left: 95%;
                        margin-bottom: 45%;
                        padding: 7px;
                        border-radius: 5px;
                        color: #15aabf;
                        cursor: pointer;
                        width: 3%;
                        text-align: center;
                        box-shadow: 2px 2px 1px #112a4d;
                        position: absolute;
                        z-index: 1;
                        right: 0;
                        top: 26px
                    }
                    .DivContent{
                        background: white;
                        display: block;
                        width: 23%;
                        position: absolute;
                        margin-left: 70%;
                        margin-bottom: 31%;
                        border-radius: 7px;
                        padding: 5px;
                        box-shadow: 3px 3px 3px 0px #102c4e;
                    }
                    .TextCenter{
                        text-align: center;
                        padding: 0px 7px 0px 7px;
                    }
                    .ColorN{
                        color:#15aabf;
                    }
                    .ColorG{
                        color: #34495e;
                    }
                    .buttonVideo{
                        width: 28% !important;
                    }
                </style>
        </head>
        <body class="container-login" style="overflow: hidden">
        <Alertas:Alertas />
<!--        <div class="DivCabVideo" >
            <div onclick="HabilitarModal()">
                <div class="DivVideo"  >
                    <i class="fa fa-play" ></i>
                </div>
            </div> 
        </div>-->
<!--        <div class="DivContent" id="ModalVideo">
            <h2 class="TextCenter ColorN">¡Nuevos ajustes!</h2>
            <p class="TextCenter ColorG">Se implementaron nuevos ajuste que permite duplicar los despeje de la misma línea, si quiere saber como</p>
            <p class="TextCenter ColorG">Haz clic en el siguiente botón</p>
            <div class="TextCenter"><input type="submit" class="buttonVideo" value="Video" onclick="AbrirVideo()" />
            </div>
        </div>-->
        <div align="center" style="width:32%;background-color: #fff;border-radius: 20px;padding: 20px;">
            <h1 style="font-size: 4.2em;color: #f5f5f6;">
                <span class="fa-stack"><i class="fa fa-folder fa-size_normal fa-stack-1x" style="color:#15aabf;"></i>
                    <i class="fa fa-flask fa-size_super_small fa-stack-2x" style="color:f5f5f6;"></i></span>
            </h1>
            <b style="font-size:30px;color:15aabf">Registros</b><b style="font-size:30px;color:#34495e;">LAB</b><br />
            <p style="color: grey;font-size: 16px;">Sistema de información para el control de proceso de las Bolsas farmaceuticas.</p>
            <b style="font-size: 16px;color:grey;">Iniciar Sesión</b><br /><br />
            <form action="Sesion?opc=1" method="post">
                <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="font-size: 16px;width: 90%;background-color:#f5f5f6;border-bottom:1px solid #15aabf;border-right: none;border-left: none;border-top: none;" autocomplete="off"/><br />
                <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="font-size: 16px;width: 90%;background-color:#f5f5f6;border-bottom:1px solid #15aabf;border-right: none;border-left: none;border-top: none;" autocomplete="off"/>
                <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" /><br/><br/>
                <!--<b>Va 18.65.15</b>-->
                <b>Va 23.70.18</b>
            </form>
            <p style="color: grey;font-size: 14px">&copy; PLASTITEC</p>
        </div>
    </body>
    <script>
        function AbrirVideo() {
            window.open('Video.jsp', '_blank', 'width=800,height=480');
        }
        function HabilitarModal() {
            var modal = document.getElementById("ModalVideo");
            if (modal.style.display === "none") {
                modal.style.display = "block";
            } else {
                modal.style.display = "none";
            }

        }
    </script>
</html>

