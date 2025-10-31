<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultados" %>
<html>
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/herramental.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Herramental proceso</title>
        <!-- CSS Principal -->
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <style>
                .placeholder-white::placeholder { color:#98958A; }
                #btnft:hover{
                    background-color: #f9c827;
                }
            </style>
        </head>
        <!--        <body style="font-size: 13px;background: linear-gradient(to left,#f5f5f6 50%, #CAA427 50% );">-->
        <body style="font-size: 13px; background: rgb(249,200,39);
              background: linear-gradient(157deg, rgba(249,200,39,1) 0%, rgba(84,70,24,1) 88%);">
            <div style="background-color: white; width: 480px; margin: auto; border-radius: 20px; display: block; margin-top: 20px;">
                <div>
                    <center style=' padding-bottom: 10px;'>
                        <img src="Interfaz/Contenido/images/logoT2.fw.png" style='margin-top: 10%; margin-bottom: 10px;'><br>
                        <br>
                        <img src="Interfaz/Contenido/images/herramental.png" alt="Logo" width="120" height="120" ><br><br>
                        <br>

                        <form action="Login?opc=1" method="post" >
                            <input type="hidden" name="slc_idM" id="idM"/>
                            <input type="hidden" name="est" id="idM" value="0"/>
                            <input type="text" autocomplete="off" name="Txt_user" id="Txt_user" placeholder="Usuario" class="placeholder-white" style="background-color:#fff;color: #f9c827;border-bottom:2px solid #f9c827;border-right: none;border-left: none;border-top: none;" onchange='javascript:this.value = this.value.toUpperCase();' /><br />
                            <input type="password" autocomplete="off" name="Txt_password" id="Txt_password" placeholder="Contraseña" class="placeholder-white" style="background-color:#fff;color: #f9c827;border-bottom:2px solid #f9c827;border-right: none;border-left: none;border-top: none;"/><br /><br>
                            <input type="submit" id="btnft" value="Iniciar" style="background-color: #000;color:#f9c827;"/><br/><br/>
                            <!--                            <b>Vp. 00.00.00</b>
                                                        <b>Vp. 01.03.01</b>
                                                        <b>Vp. 01.06.01</b>
                                                        <b>Va. 01.06.01</b>
                                                        <b>Va. 02.10.02</b>
                                                        <b>Va. 03.15.03</b>
                                                        <b>Va. 04.17.03</b>-->
                            <b style='color: #000;size: 13px;'>Va. 04.17.03</b><br>
                        </form>
                    </center>
                </div>
                <hr style="margin-bottom: -10px;">
                <div style="width: auto;text-align: justify;color:#454343; padding: 30px; border-radius: 0px 0px 20px 20px;font-size: 12px;" align="left">
                    <P><b>HERRAMENTAL PROCESO: </b>Este sistema de información es el encargado de facilitar la consulta y movimientos de los herramentales en los diferentes procesos de la organización, permitiendo alertar atravez de pendientes de <b>herramentales</b> o <b>maquinas</b> las diferentes actividades a realizar
                    El sistema como ayuda virtual permite al usuario acceder a la información de manera segura, rapida y confiable para poder realizar en cada uno de los procesos del registro una adecuada manipulación.
                    </P>
                </div>
            </div>
        <resultados:MuestraResultados/>
        <!--        <center>
                    <div style="width: 800px;margin-top: 100px;" align='center'>
                        <div style="width: 150px;height: 110px;background-color: #CAA427;color: #fff;top: 220px;position: fixed;">
                        </div>
                        <div style="width: 600px;">
                            <div style="float: left;width: 300px;height: 300px;color: #CAA427;">
                                <br /><br /><br /><br /><br /><br /><br />
                                <form action="Login?opc=1" method="post">
                                    <input type="hidden" name="slc_idM" id="idM"/>
                                    <input type="hidden" name="est" id="idM" value="0"/>
                                    <input type="text" name="Txt_user" id="Txt_user" placeholder="Usuario" class="placeholder-white" style="background-color:#CAA427;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;" onchange='javascript:this.value = this.value.toUpperCase();'/><br />
                                    <input type="password" name="Txt_password" id="Txt_password" placeholder="Contraseña" class="placeholder-white" style="background-color:#CAA427;color: #f5f5f6;border-bottom:3px solid #f5f5f6;border-right: none;border-left: none;border-top: none;"/><br />
                                    <input type="submit" value="Iniciar" style="background-color: #f5f5f6;color:#CAA427"/><br/><br/>
                                    <b>Vp. 00.00.00</b>
                                    <b>Vp. 01.03.01</b>
                                    <b>Vp. 01.06.01</b>
                                    <b>Va. 01.06.01</b>
                                    <b>Va. 02.10.02</b>
                                    <b>Va. 03.15.03</b>
                                    <b style='color: #f5f5f6;size: 13px'>Va. 04.17.03</b>
                                </form>
                            </div>
                            <div style="float: left;width: 300px;height: 300px;color: #B4045F;">
                                <br /><br />
                                <img src="Interfaz/Contenido/images/logoT2.fw.png" alt="Logo"/>
                                <br /><br />
                                <img src="Interfaz/Contenido/images/LogoTest7.fw.png" alt="Logo" width="220" height="220" />
                            </div>
                            <div style="float: left;width: 600px;height: 180px;background: linear-gradient(to left,#f5f5f6 50% ,#f5f5f6 50% );color: grey;font-weight: bold;">
                                <div style="width: 500px;margin-top: 10px;text-align: justify;color:grey" align="left">
                                    <b>Herramental proceso </b>Este sistema de información es el encargado de facilitar la consulta y movimientos de los herramentales en los diferentes procesos de la organización, permitiendo alertar atravez de pendientes de herramentales o maquinas las diferentes actividades a realizar
                                    <br />El sistema como ayuda virtual permite al usuario acceder a la información de manera segura, rapida y confiable para poder realizar en cada uno de los procesos del registro una adecuada manipulación.
                                </div>
                            </div>
                        </div>
                    </div>
                </center>-->
    </body>
</html>
