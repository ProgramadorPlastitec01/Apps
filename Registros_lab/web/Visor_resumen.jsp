
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/Tlds/Visor_resumen.tld"  prefix="Resumen"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/images/Registros_lab_new.ico" rel="icon" />
        <title>R-GC-017</title>
        <link type="text/css" href="Interfaz/Contenido/Css/CSS_Principal2018.css">
        <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/FontAwesome/css/all.css">

    </head>
    <body>
        <Resumen:Visor_resumen/>
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
    </body>
</html>
