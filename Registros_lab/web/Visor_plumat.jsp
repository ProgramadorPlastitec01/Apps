<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<%@taglib uri="/WEB-INF/Tlds/Visor_plumat.tld" prefix="Plummat" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>...</title>
        <!-- CONTROL ENVIO DE PETICIONES -->
        <script language="javascript">
            function checkKeyCode(evt)
            {
                var evt = (evt) ? evt : ((event) ? event : null);
                var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
                if (event.keyCode == 116)
                {
                    evt.keyCode = 0;
                    return false
                }
            }
            document.onkeydown = checkKeyCode;
        </script>
        <script type="text/javascript">
            var statsend = false;
            function checkSubmit() {
                if (!statsend) {
                    statsend = true;
                    return true;
                } else {
                    alert(" Un momento por favor el formulario se esta enviando...");
                    return false;
                }
            }
        </script>
        <!-- CSS Principal -->
        <!--<link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />-->
        <style type="text/css">
            /*Cuerpo*/
            /*COLOR #0B4C5F -- #21bdd0*/
            body {
                color: #34495e;
                font-family:"Segoe UI";
                font-size: 14px;
                background-color: #FFF;
            }
            b{
                color:#15aabf;
            }
            th{
                padding: 5px 10px 5px 10px;
                border: none;
                font-size: 9px;
                font-weight: bold;
                color: #FFF;
                background-color:#15aabf;
            }

            .table {
                width:  100%;
            }
            .table td{
                padding: 1px 3px 3px 1px;
                border-color: #15aabf;
                font-size: 8px;
                color: #34495e;
                background-color:#fff;
                border-right: 1px solid #eee;
                border-bottom: 1px solid #eee;
                text-transform: uppercase;
            }

            .color{
                color: #34495e;
                font-weight: bold;
                font-size: 11px;
            }
            hr{
                border: 1px solid #DCDCDC;
            }
            .rojo td{
                border-color: #15aabf;
                font-size: 8px;
                color: #CC0000;
                font-weight: bold;
                background-color:#fff;
                border-right: 1px solid #eee;
                border-bottom: 1px solid #eee;
                text-transform: uppercase;
            }
            .administrador td{
                border-color: #15aabf;
                font-size: 8px;
                color: #15aabf;
                font-weight: bold;
                background-color:#fff;
                border-right: 1px solid #eee;
                border-bottom: 1px solid #eee;
                text-transform: uppercase;
            }
            .documental td{
                border-color: #15aabf;
                font-size: 11px;
                color: #045FB4;
                font-weight: bold;
                background-color:#fff;
                border-right: 1px solid #eee;
                border-bottom: 1px solid #eee;
                text-transform: uppercase;
            }
            .calidad td{
                border-color: #15aabf;
                font-size: 8px;
                color: #045FB4;
                font-weight: bold;
                background-color:#fff;
                border-right: 1px solid #eee;
                border-bottom: 1px solid #eee;
                text-transform: uppercase;
            }
            .coordinadora td{
                border-color: #045FB4;
                font-size: 8px;
                color: #045FB4;
                font-weight: bold;
                background-color: #dcdcdc;
                border-right: 1px solid #eee;
                border-bottom: 1px solid #eee;
                text-transform: uppercase;
            }

            .rojo{
                font-size: 8px;
                color: #CC0000;
                font-weight: bold;
                text-transform: uppercase;
            }
            .documental{
                color: #9D0042;
                font-weight: bold;
            }
            .documental td{
                color: #9D0042;
                font-weight: bold;
            }
            .calidad{
                color: #045FB4;
                font-weight: bold;
            }
            .calidad td{
                color: #045FB4;
                font-weight: bold;
            }
            .coordinadora {
                color: #34495e;
                background-color: #dcdcdc;
                font-weight: bold;
            }
            .coordinadora td{
                color: #34495e;
                background-color: #dcdcdc;
                font-weight: bold;
            }
            .naranja{
                font-size: 8px;
                color: #F6921E;
                font-weight: bold;
                text-transform: uppercase;
            }
            .negro {
                color:#34495e;
            }
            .negro2 {
                color:#34495e;
                font-size: 15px;
                font-weight: bold;
            }
            .table tr:hover td{
                background-color:#eee;
            }
            #content_sin {
                float: right;
                width: 1240px;
                /*    padding: 20px 10px 20px;*/
            }
        </style>
        <!--Imprimir-->
        <script language="javascript">
            function Imprimir() {
                var objeto = document.getElementById('Imprimir');  //obtenemos el objeto a imprimir
                var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
                ventana.document.write(objeto.innerHTML);  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal2018.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                ventana.document.close();  //cerramos el documento
                ventana.print();  //imprimimos la ventana
                ventana.close();  //cerramos la ventana
            }
        </script>
    </head>
    <body id="subpage" >
        <Plummat:Visor_plumat/>
        <Alertas:Alertas />
    </body>
</html>