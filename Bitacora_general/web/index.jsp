<%@taglib uri="/WEB-INF/tlds/tld_resultados" prefix="resultados" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Bitacora_general_fw.ico" rel="icon" />
        <title>Bitacora General</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <style>
                .placeholder-white::placeholder { color:#f5f5f6; }
            </style>

        </head>
        <body class="container-login">
        <center>
            <div style="width: 600px;margin-top: 5px;" align='center'>
                <div style="font-size: 1.2em; width:75%;background: linear-gradient(to left,#f5f5f6 50%,#f5f5f6 50%);color: #f5f5f6; border-radius: 20px"  align='center'>
                    <br><br><b style="font-size: 160%">BITCORA GENERAL</b>
                    <br><br><center><img src="Interfaz/Contenido/images/Bitacora_general_fw.png" alt="Logo" width="100" title="Logo"/></center>
                    <br>
                    <form action="Login?opc=1" method="post">
                        <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#f5f5f6;border-bottom:1px solid #601c03 ;border-right: none;border-left: none;border-top: none;" /><br>
                        <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#f5f5f6;border-bottom:1px solid #601c03;border-right: none;border-left: none;border-top: none;"/>
                        <br><br>
                        <input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #601c03;color:#fff'/>
                        <br><br>
                        <b style='color: #black;size: 13px'>Va. 07.13.10</b>
                        <!--<b style='color: #black;size: 13px'>Va. 04.08.07</b>-->
                        <!--<b style='color: #black;size: 13px'>Va. 01.02.03</b>-->
                        <!--<b style='color: #601c03'>Va 02.10.03</b>-->
                        <!--<b style='color: #601c03'>Va 00.07.02</b>-->
                        <!--<b style='color: #601c03'>Va 00.00.00</b>-->
                    </form>
                    <hr>
                    <div style="width: 80%;margin-top: 20px;text-align: center; font-size: 13px" align="center">
                        <p style="color:grey" align="justify"><b>BITACORA:</b> Es una herramienta que permite el registro de actividades ingresada por el trabajador en su respectivo turno, facilitando el seguimiento, manipulación de la información y seguridad de la misma con el fin de mantener un control y constancia de las actividades ejecutadas por el operario.</p>
                    </div><br>
                    <!--</div>-->
                </div>
            </div>
            <!--            <div style="width: 600px;">
                            <div style="float: left;width: 300px;height: 300px;color: #666666;">
                                <br></br><b style="font-size: 160%">Bitacora General</b> 
                                <form action="Login?opc=1" method="post">
                                    <br></br><center><img src="Interfaz/Contenido/images/Bitacora_general_fw.png" alt="Logo" width="100" title="Logo"/></center>
                                    <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" class="placeholder-white" onchange='javascript:this.value = this.value.toUpperCase();' style="background-color:#666666;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                                    <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" class="placeholder-white" style="background-color:#666666;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                                    <input type="submit" value="Iniciar" style="background-color: #f5f5f6;color:#666666" /><br/><br/>
                                    <b>Va. 00.01.02</b>
                                    <b style='color: #f5f5f6;size: 13px'>Va. 01.02.03</b>
                                </form>
                            </div>
                            <div style="float: left;width: 300px;height: 300px;color: #B4045F;">
                                <br /><br />
                                <img src="Interfaz/Contenido/images/BitacoraLogo.png" alt="Logo"/>
                                <br /><br /><br /><br />
                                <img src="Interfaz/Contenido/images/Bitacora_general.png" alt="Logo" width="140px" />
                            </div>
                            <div style="float: left;width: 600px;height: 100px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                                <div style="width: 500px;margin-top: 10px;text-align: justify;color:grey" align="left">
                                    <b>Bitácora </b>es una herramienta que permite el registro de actividades ingresada por el trabajador en su respectivo turno, facilitando el seguimiento, manipulación de la información y seguridad de la misma con el fin de mantener un control y constancia de las actividades ejecutadas por el operario.
                                </div>
                            </div>
                        </div>-->
        </center>
    <resultados:MuestraResultados />
</body>
</html>


