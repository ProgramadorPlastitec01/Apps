<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Inicio.tld" prefix="Inicio"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Inicio</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Inicio.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Inicio:Inicio />
            <div style="width:350px;height:450px;float:left;border-radius: 25px 0px 0px 25px;padding: 30px">
                <b style="color:#34495e">CONTROL DE PROCESO</b><br /><b>PRODUCCIÓN FARMACEUTICA</b><br /><br />
                <p style="color:#34495e" align="justify"><b>RegistrosLAB </b>Este sistema de información es el encargado de facilitar el manejo del control dimensional de las bolsas, diligenciados en los registros
                    <b>R-PRF-010 Screen - Colas</b> / <b>011 Bocas</b> y <b> 013 Colpitt</b>.<br />
                    <br /> Permitiendo abrir los registros de despeje de linea <b>R-PRF-005 Screen- Colas</b> / <b>006 Bocas</b> y <b>007 Colpitt</b> cuando cambie la generación de lotes del producto en los turnos.
                    <br />Tambien ayuda con la generación de resumenes para analisis y archivo de información en el <b>R-GC-017</b>.
                    <br />El sistema como ayuda virtual permite al usuario acceder a la información de manera <b>segura, rapida </b>y<b> confiable</b> para poder realizar en cada uno de los procesos del registro una adecuada manipulación.</p>
            </div>
            <div style="width:800px;float:left;">
                <b style="color:#34495e">MAPA DE ICONOS</b><br /><br />
                <table class="table">
                    <tr>
                        <th>Icono</th>
                        <th>Descripción</th>
                        <th>Icono</th>
                        <th>Descripción</th>
                    </tr>
                    <tr>
                        <td align="center"><span class="fas fa-exclamation-circle fa-size_normal" ></a></td>
                        <td>No tiene permisos en el modulo o no hay datos en la consulta.</td>
                        <td align="center"><span class="fa fa-check fa-size_normal" ></a></td>
                        <td>Indica que un registro se encunetra activo, tambien sirve para liberar el registro de despeje.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="fa fa-lock fa-size_normal" ></a></td>
                        <td>Indica que el registro se encuentra cerrado.</td>
                        <td align="center"><span class="fa fa-lock-open fa-size_normal" ></a></td>
                        <td>Indica que el registro se encuentra abierto.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="far fa-eye fa-size_normal" ></a></td>
                        <td>Permite entrar a ver el detalle de un registro.</td>
                        <td align="center"><span class="far fa-plus-square fa-size_normal" ></a></td>
                        <td>Habilita formulario de registro segun el modulo..</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="fa fa-arrow-left fa-size_normal" ></a></td>
                        <td>Permite volver al modulo anterior o principal.</td>
                        <td align="center"><span class="fa fa-pen fa-size_normal" ></a></td>
                        <td>Otorga permisos de edición en el registro seleccionado.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="fa fa-key fa-size_normal" ></a></td>
                        <td>En el modulo de usuario permite restablecer la contraseña al año en curso.</td>
                        <td align="center"><span class="fa fa-times fa-size_normal" ></a></td>
                        <td>Indica la inactividad de un registro o tambien sirve para cerrar o cancelar formularios o ventanas.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="far fa-copy fa-size_normal" ></a></td>
                        <td>Permite en el mmodulo de turnos generar un turno consecutivo a partir de los datos del turno anterior.</td>
                        <td align="center"><span class="fa fa-running fa-size_normal" ></a></td>
                        <td>Salir de Registros LAB.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="fa fa-sync fa-size_normal" ></a></td>
                        <td>Sirve para actualizar la información de los datos de control a una siguiente versión o para recargar los datos de la pagina..</td>
                        <td align="center"><span class="far fa-file-alt fa-size_normal" ></a></td>
                        <td>Permite ver el listado de registros de despeje historicos de la OP y el producto.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="fas fa-print fa-size_normal" ></a></td>
                        <td>Envia el contenido del modulo a impresion o exportar como PDF.</td>
                        <td align="center"><span class="fas fa-signature fa-size_normal" ></a></td>
                        <td>Permite ingresar la firma del usuario en sesión en los registros de despeje o en la cabecera de los turnos.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="fas fa-align-left fa-size_normal" ></a></td>
                        <td>Habilita o deshabilita las observaciones en el registro de despeje.</td>
                        <td align="center"><span class="far fa-save fa-size_normal" ></a></td>
                        <td>Almacena el contenido de la plantilla de registro de despeje.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="far fa-caret-square-down fa-size_normal" ></a></td>
                        <td>Habilita menu de opciones dentro del registro de control de proceso.</td>
                        <td align="center"><span class="fa fa-eraser fa-size_normal" ></a></td>
                        <td>Limpia información de las estaciones horarias del registro.</td>
                    </tr>
                    <tr>
                        <td align="center"><span class="far fa-calendar-alt fa-size_normal" ></a></td>
                        <td>En el modulo de registros del día permite consultar por la fecha seleccionada.</td>
                        <td align="center"><span class="far fa-file-excel fa-size_normal" ></a></td>
                        <td>Permite exportar la información en formato Excel.</td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>