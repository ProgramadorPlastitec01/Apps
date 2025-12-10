<%@taglib uri="/WEB-INF/tlds/ResultadosTLD.tld" prefix="resultados"%>
<html>
    <head>
        <link rel="icon" type="image/png" href="Interfaz/Contenido/images/Bitacora.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calidad</title>
        <jsp:include page='master_head.jsp'></jsp:include>
            <script type="text/javascript">
                function FinalizarSession() {
                    confirmar = confirm("Confirmar cierre de sessión")
                    if (confirmar) {
                        location.href = 'salir.jsp';
                    } else {
                        location.href = 'menu.jsp';
                    }
                }
            </script>
            <script type="text/javascript">
                function contrasena() {
                    document.getElementById("pass-id").value = '2020';  // id del campo contraseña 
                    document.form1.submit();  /// dar name al formulario 
                }
            </script>
            <style>
                .placeholder-white::placeholder { color: #f5f5f6;}         
            </style>
        </head>
        <body id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%, #880e4f 50%);">
        <center>
            <div style="width: 600px;margin-top: 100px;" align='center'>
                <div style="float: left;width: 300px;height: 300px;color: #03899C;">
                    <br /><br /><br /><br /><br /><br />       
                    <form method="post" action="Ingreso?opc=1">  
                        <input type="text" name="user" class="placeholder-white" id="Txt_user" placeholder="Usuario" onchange='javascript:this.value = this.value.toUpperCase();' style="background-color:#880e4f;color: #f5f5f6;border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                        <input type="password" class="placeholder-white" name="password" id="Txt_password" placeholder="Contraseña" style="background-color:#880e4f; color:#f5f5f6; border-bottom:1px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                        <br>
                        <input type="submit" value="Iniciar" style='background-color: #f5f5f6;color:#880e4f'/><br/><br/>
                        <!--<b style="color:#ffffff">Va 02.10.16</b>-->
                        <b style="color:#ffffff">Va 03.13.18</b>
                    </form>
                </div>
                <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                    <br /><br />|
                    <img src="Interfaz/Contenido/images/BitacoraLogo.png" alt="Logo" />
                    <br /><br />|<br /><br />
                    <img src="Interfaz/Contenido/images/Bitacora.png" alt="Logo" width="140" />
                    <br>
                    <br>
                </div>
                <div style="float: left;width: 600px;height: 150px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                    <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                        <p style="color:grey" align="justify"><b>Bitácora</b> es una herramienta que permite el registro de actividades ingresada por el trabajador en su respectivo turno, facilitando el seguimiento, manipulación de la información y seguridad de la misma con el fin de mantener un control y constancia de las actividades ejecutadas por el operario.
                            Bitácora permite registro de actividades en un orden cronológico de acuerdo al avance del proyecto.</p>
                    </div>
                </div>
            </div>
        </center>
    <resultados:Resultados />
</body>
</html>