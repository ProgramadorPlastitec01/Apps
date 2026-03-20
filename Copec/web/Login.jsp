<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    
    <head>
        <link rel="icon" type="image/png" href="Interfaz/Template/images/Ico.ico"/>
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <jsp:include page="Encabezado.jsp"></jsp:include>
        <style>
            /* all */
            ::-webkit-input-placeholder { color:#f5f5f6; }
            ::-moz-placeholder { color:#f5f5f6; } /* firefox 19+ */
            :-ms-input-placeholder { color:#f5f5f6; } /* ie */
            input:-moz-placeholder { color:#f5f5f6; }
        </style>
    </head>
</head>
<body style="font-size: 13px;background: linear-gradient(to left,#f5f5f6 50%, #048B86 50% );">
<center>
    <div style="width: 800px;margin-top: 100px;" align='center'>
        <div style="width: 150px;height: 110px;background-color: #048B86;color: #fff;top: 220px;position: fixed;">
            <br /><a href="javascript:window.open('Calificacion?opc=1','','width=1024,height=720,left=50,top=50,toolbar=no,resizable=no,location=no');void 0"><img src="Interfaz/Template/images/Checklist_B.png" alt="Logo" width="50" height="70" /></a><br />
        </div>
        <div style="width: 600px;">
            <div style="float: left;width: 300px;height: 300px;color: #048B86;">
                <br />
                <div style="float:left;margin-top: 10px;color: #FFF">CONTROL DE PERSONAL ENTRENADO Y CALIFICADO</div>
                <br /><br /><br /><br /><br /><br /><form action="Login?opc=1" method="post">
                    <input type="text" name="txt_usuario" id="txt_usuario" placeholder="Usuario" onchange='javascript:this.value = this.value.toUpperCase();' style="background-color:#048B86;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                    <input type="password" name="txt_contrasena" id="txt_contrasena" placeholder="Contraseña" style="background-color:#048B86;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/>
                    <input type="submit" value="Iniciar" style="background-color: #f5f5f6;color:#048B86" /><br/><br/>
                    <!--<b>Va 08.29.15</b>-->
                    <!--<b>Va 10.35.17</b>-->
                    <b style='color: #f5f5f6;size: 13px'>Va 14.36.20</b>
                </form>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #B4045F;">
                <br /><br />
                <img src="Interfaz/Template/images/Titulo.png" alt="Logo" width="80.9" height="19.5"/>
                <br /><br /><br /><br />
                <img src="Interfaz/Template/images/Copec.png" alt="Logo" width="219.5" height="150.5" />
            </div>
            <div style="float: left;width: 600px;height: 150px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold; padding-bottom: 30px; padding-top: 10px;">
                <div style="width: 500px;margin-top: 10px;text-align: justify;color:grey" align="left">
                    <p style="color:gray" align="justify">
                    <b>COPEC</b> Este sistema de información es el encargado de facilitar el manejo de información de las matrices de entrenamiento permitiendo identificar en cada entrenamiento el personal y los documentos en las versiones a divulgar.
                    Programación del Entrenamiento, Reportes de Entrenamiento, asignación de Documentos al área, actualización u movimientos en las versiones, Consulta de Matrices de Entrenamiento.
                    El sistema como ayuda virtual permite al usuario acceder a la información de manera rapida, segura y confiable para poder realizar en cada uno de los entrenamientos una adecuada manipulación.
                    </p>
                </div>
            </div>
        </div>
    </div>
</center>
<Alertas:Alertas/>
</body>
</html>
