<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Control Grafado</title>
        <!-- CSS Principal -->
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <style>
                .placeholder-white::placeholder { color:#f5f5f6; }
            </style>
        </head>
        <body class="container-login">
        <center>

            <div style="width: 600px;margin-top: 5px;" align='center'>
                <div style="font-size: 1.2em;height: 100%;width:80%;background: linear-gradient(to left,#f5f5f6 50%,#f5f5f6 50%);color: #f5f5f6; border-radius: 20px"  align='center'>
                    <br></br><b style="font-size: 140%; color: black;">CONTROL</b>&nbsp;<b style="font-size: 140%">GRAFADO</b>
                    <br></br><center><img src="Interfaz/Contenido/images/CGrafado.png" alt="Logo"  width="30%"/></center>
                    <br /><form action="Login?opc=1" method="post">
                        <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" style="background-color:#f5f5f6;border-bottom:1px solid #00838f ;border-right: none;border-left: none;border-top: none;" /><br />
                        <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#f5f5f6;border-bottom:1px solid #00838f;border-right: none;border-left: none;border-top: none;"/>
                        <br /><br /><input type="submit" class='Iniciar_sesion' value="Iniciar" style='background-color: #00838f;color:#fff'/><br/><br/>
                        <b style='color: #00838f;size: 13px'>Va 15.32.18</b>
                        <!--<b>Va 07.18.12</b>-->
                        <!--<b>Va 07.22.12</b>-->
                        <!--<b>Va 09.24.15</b>-->
                        <!--<b>Va 11.27.15</b>-->
                    </form>
                    <hr></hr><div style="width: 80%;margin-top: 20px;text-align: center; font-size: 12px" align="center">
                        <p style="color:grey" align="justify"><b>CONTROL GRAFADO</b> Este sistema de información es el encargado de facilitar el manejo de información de los <b>R-GC-014 SITIO ESTANDÁR</b> y <b>R-GC-116 SITIO INYECCIÓN USA</b> permitiendo identificar el concepto de la toma según la especificación de la Ficha Técnica.
                            Registro de Nuevos Turnos, Registro de Datos de Control, Lanzamiento de Orden de Producción, Manejo y Control del Estado de Calidad, Defectos de Inyección.
                            El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>rapida, segura</b> y <b>confiable</b> para poder realizar en cada uno de los Controles una adecuada manipulación.
                    </div></br>
                    <!--</div>-->
                </div>
            </div>
        </center>
    <resultados:MuestraResultados/>
</body>
</html>
