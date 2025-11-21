<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Registros LAB</title>
        <!-- CONTROL ENVIO DE PETICIONES-->
        <script language="javascript">
            function checkKeyCode(evt)
            {
                var evt = (evt) ? evt : ((event) ? event : null);
                var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
                if(event.keyCode==116)
                {
                    evt.keyCode=0;
                    return false
                }
            }
            document.onkeydown=checkKeyCode;
        </script>
        <script type="text/javascript">
            var statsend = false;
            function checkSubmit(){
                if(!statsend){
                    statsend = true;
                    return true;
                }else{
                    alert(" Un momento por favor el formulario se esta enviando...");
                    return false;
                }
            }
        </script>
        <script type = "text/javascript" >
            history.pushState(null, null, 'index.jsp');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, 'index.jsp');
            });
        </script>
        <!-- CSS Principal -->
        <link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />
    </head>
    <body id="subpage">
        <!--<div style='background-color:#c10937;color:#FFF;' align='center'><MARQUEE>............VERSION DE PRUEBA  CAMILO YO VERE ...........</MARQUEE></div>-->
        <Alertas:Alertas />
        <center>
            <div style="width: 600px">
                <div style="float: left;width: 800px;margin-top: 100px;">
                    <div style="float: left;width: 300px;height: 300px;">
                        <br /><br /><br />
                        <img src="Interfaz/Contenido/images/Registros_lab.png" alt="Logo" width="259.5" height="160.5" />
                        <br />
                        <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
                        <!-- <h2>Registros LAB<br />Vp. 00.00.00</h2>-->
                    </div>
                    <div style="float: left;width: 300px;height: 300px;">
                        <br /><br /><br />
                        <fieldset>
                            <legend>Iniciar Sesión</legend>
                            <form action="Sesion?opc=1" method="post">
                                <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" onchange='javascript:this.value=this.value.toUpperCase();'/><br />
                                <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" onchange='javascript:this.value=this.value.toUpperCase();'/><br />
                                <input type="submit" value="Iniciar" /><br/><br/>
                                <b>Va 09.47.05</b>
                            </form>
                        </fieldset>
                    </div>
                    <div style="float: left;width: 600px;height: 210px;background-color: #34495e;color: #fff">
                        <div style="width: 500px;margin-top: 20px" align="center">
                            <p style="color:#ffffff" align="justify"><b>RegistrosLAB </b>Este sistema de información es el encargado de facilitar el manejo del control dimensional de las bolsas, diligenciados en los registros
                                <b>R-PRF-010 Screen - Colas</b> / <b>011 Bocas</b> y <b> 013 Colpitt</b>.<br /> Permitiendo abrir los registros de despeje de linea <b>R-PRF-005 Screen- Colas</b> / <b>006 Bocas</b> y <b>007 Colpitt</b> cuando cambie la generación de lotes del producto en los turnos.
                                <br />Tambien ayuda con la generación de resumenes para analisis y archivo de información en el <b>R-GC-017</b>.
                                <br />El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
                        </div>
                    </div>
                </div>
            </div>
        </center>
    </body>
</html>
