<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/act_logo.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>ACTIVOS</title>
            <script type = "text/javascript" >
                history.pushState(null, null, 'index.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'index.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body class="container-login">
        <alertas:Alertas />
        <center>
            <div style="width: 600px;margin-top: 5px;" align='center'>
                <div style="font-size: 1.2em;height: 50%;width:80%;background: linear-gradient(to left,#f5f5f6 50%,#f5f5f6 50%);color: #f5f5f6; border-radius: 20px"  align='center'>
                    <br></br><b style="font-size: 160%">ACTIVOS</b>
                    <br></br><center><img src="Interfaz/Contenido/images/logoAct.png" alt="Logo" width="100" title="Logo"/></center>
                   <br /><form action="Sesion?opc=1" method="post">
                        <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#f5f5f6;border-bottom:1px solid #880E4F ;border-right: none;border-left: none;border-top: none;" /><br />
                        <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#f5f5f6;border-bottom:1px solid #880E4F;border-right: none;border-left: none;border-top: none;"/>
                        <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #880E4F;color:#fff'/><br/><br/>
                        <b style='color: #880E4F'>Va 06.14.06</b>
                        <!--<b style='color: #880E4F'>Va 02.10.03</b>-->
                        <!--<b style='color: #880E4F'>Va 00.07.02</b>-->
                        <!--<b style='color: #880E4F'>Va 00.00.00</b>-->
                    </form>
                    <hr></hr><div style="width: 80%;margin-top: 20px;text-align: center; font-size: 13px" align="center">
                    <p style="color:grey" align="justify"><b>ACTIVOS:</b> Este sistema de información facilita el proceso de las Requisiciones de Materiales en sus diferentes etapas, ademas facilita el control de Inventario Maquinaria y activos de tipo fijo o de gasto de la compañia.
                        El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>segura, rapida</b> y <b>confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
                    </div></br>
                    <!--</div>-->
                </div>
            </div>
        </center>
    </body>
</html>