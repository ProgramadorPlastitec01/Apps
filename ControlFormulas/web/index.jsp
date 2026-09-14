<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_formulas_new.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Control formulas</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <!--<style>
                /* all */
                ::-webkit-input-placeholder { color:#f5f5f6; }
                ::-moz-placeholder { color:#f5f5f6; } /* firefox 19+ */
                :-ms-input-placeholder { color:#f5f5f6; } /* ie */
                input:-moz-placeholder { color:#f5f5f6; }
            </style>-->
            <style>
                .placeholder-white::placeholder { color: #f5f5f6;}         
            </style>
            <!-- CSS Principal -->
            <link href="Interfaz/Contenido/Css/Css_Principal_New.css" rel="stylesheet" type="text/css" />
    </head>
    <body id="subpage" style="background: linear-gradient(to left,#f5f5f6 50%, #006666 50%);">
    <center>
        <div style="width: 600px;margin-top: 100px;" align='center'>
            <div style="font-size: 1.2em;height: 70px;width:600px;background: linear-gradient(to left,#f5f5f6 20%);color: #f5f5f6;" align='center'>
                <img src="Interfaz/Contenido/images/templatemo_logo.png" alt="Logo" />
                <div style='float:left;width:50%;margin-top: 10px'>Materias Primas en Formulación</div>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #03899C;">
                <br /><br /><br /><br />  <form action="Sesion?opc=1" method="post">
                    <input type="text"name="Txt_user" class="placeholder-white" id="Txt_user" placeholder="Usuario" onchange='javascript:this.value = this.value.toUpperCase();' style="background-color:#006666;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                    <input type="password" class="placeholder-white" name="Txt_password" id="Txt_password" placeholder="Contraseña" style="background-color:#006666; color:#f5f5f6; border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" /><br />
                    <br>
                    <input type="submit" value="Iniciar" style='background-color: #f5f5f6;color:#006666'/><br/><br/>
                </form>
            </div>
            <div style="float: left;width: 300px;height: 300px;color: #f5f5f6;">
                <br /><br />|
                <img src="Interfaz/Contenido/images/Control_formulas_new.png" alt="Logo" width="160" height="160" />
                <br>
                <br>
                <b>Va 03.14.06</b>
            </div>
            <div style="float: left;width: 600px;height: 150px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                <div style="width: 500px;margin-top: 20px;text-align: justify" align="left">
                    <p style="color:grey" align="justify"><b>Control Formulas </b>Este sistema de información es el encargado de facilitar la asignación de materias primas a las diferentes formulas y generar el manual de registro
                        <b>Control uso de lotes o sublotes de materias primas en formulas R-PI-004</b>. El sistema como ayuda virtual permite al usuario acceder a la información de manera<b> segura, rapida </b>y<b>
                            confiable</b> para poder realizar en cada uno de los procesos de las formulas una adecuada manipulación.</p>
                </div>
            </div>
        </div>
    </center>
<Alertas:Alertas/>
</body>
</html>
