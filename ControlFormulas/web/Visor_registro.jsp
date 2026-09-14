<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Formula.tld" prefix="Formula"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_formulas_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
                     <!--Controlar url-->
              <script type = "text/javascript" >
                history.pushState(null, null, 'Visor_registro.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Visor_registro.jsp');
                });
            </script>
            <title>...</title>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!-- CSS Principal -->
                <!--<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />-->
                <style type="text/css">
                    /*Cuerpo*/
                    /*COLOR #0B4C5F -- #21bdd0*/
                    body {
                        color: #292929;
                        /*font-family: Tahoma, Geneva, sans-serif;*/
                        font-family:"Segoe UI";
                        font-size: 14px;
                        background-color: #FFF;
                    }
                    b{
                        color:#006666;
                    }

                    /*Vinculos*/

                    /*Selección*/
                    .tmo_list { margin: 10px 0 10px 0; padding: 0; list-style: none }
                    .tmo_list li { color:#636363; margin: 0 0 5px 0; padding: 0 0 0 20px; background: url(../images/templatemo_list.png) no-repeat scroll 0 7px  }
                    .tmo_list li a { color: #636363; font-weight: normal }
                    .tmo_list li a:hover { color: #000 }


                    /*Titulos*/
                    h1, h2, h3, h4, h5, h6 { color: #006666; font-weight: normal; }

                    /*Controles HTML*/

                    input[type="text"]:focus,input[type="password"]:focus,textarea:focus,input[type="number"]:focus,select{
                        outline: none !important;
                        border:1px solid #006666;
                        box-shadow: 0 0 5px #006666;
                    }
                    input[type="submit"]:focus{
                        outline: none !important;
                        border:none;
                    }

                    input[type="text"],input[type="password"] {
                        width: 188px;
                        background: #fff;
                        border: 1px solid #006666;
                        padding: 5px;
                        margin-bottom: 10px;
                        font-size: 14px;
                        color:#292929;
                    }

                    input[type="time"]{
                        width: 188px;
                        background: #fff;
                        border: 1px solid #006666;
                        padding: 5px;
                        margin-bottom: 10px;
                        font-size: 14px;
                        color:#292929;
                        /* text-transform: uppercase;*/
                    }

                    input[type="number"]{
                        width: 188px;
                        background: #fff;
                        border: 1px solid #006666;
                        padding: 5px;
                        margin-bottom: 10px;
                        font-size: 14px;
                        color:#292929;
                        -moz-appearance: none;
                        -webkit-appearance: none;
                        appearance: none;
                        /*text-transform: uppercase;*/
                    }
                    textarea {
                        width: 188px;
                        background: #fff;
                        border: 1px solid #006666;
                        padding: 5px;
                        margin-bottom: 10px;
                        font-size: 14px;
                        color:#292929;
                        /*text-transform: uppercase;*/
                    }
                    input[type="submit"] {
                        padding: 7px 11px 8px 11px;
                        border: none;
                        font-size: 13px;
                        font-weight: bold;
                        color: #fff;
                        width: 200px;
                        background-color:#292929;
                    }
                    input[type="submit"]:hover {
                        padding: 7px 11px 8px 11px;
                        border-color: #006666;
                        font-size: 13px;
                        font-weight: bold;
                        width: 200px;
                        color: #fff;
                        background-color:#006666;
                    }

                    select{
                        border: 1px solid #006666;
                        padding: 5px 0px 5px 0px;
                        color: #006666;
                        font-size: 13px;
                        width: 200px;
                        font-weight: bold;
                        -moz-appearance: none;
                        -webkit-appearance: none;
                        appearance: none;
                        text-transform:uppercase;
                    }
                    option{
                        background-color: #fff;
                        font-weight: bold;
                        font-size: 13px;
                        padding: 5px 11px 5px 11px;
                        color:#006666;
                    }

                    fieldset{
                        background-color: #fff;
                        border:2px solid #006666;
                        height: auto;
                    }
                    .resalta_field{
                        outline: none !important;
                        border:1px solid #DCDCDC;
                        box-shadow: 0 0 60px #292929;
                    }

                    legend{
                        padding: 7px 15px 8px 15px;
                        border: none;
                        font-size: 13px;
                        font-weight: bold;
                        color: #FFF;
                        background-color:#006666;
                    }

                    table{
                    }

                    th{
                        padding: 7px 15px 8px 15px;
                        border: none;
                        font-size: 14px;
                        font-weight: bold;
                        color: #FFF;
                        background-color:#006666;
                    }

                    .table {

                    }
                    .table td{
                        padding: 7px 15px 8px 15px;
                        border-color: #006666;
                        font-size: 14px;
                        color: #292929;
                        background-color:#fff;
                        border-right: 2px solid #eee;
                        border-bottom: 2px solid #eee;
                        text-transform: uppercase;
                    }
                    .color{
                        color: #292929;
                        font-weight: bold;
                        font-size: 11px;
                    }
                    hr{
                        border: 1px solid #DCDCDC;
                    }
                    .rojo td{
                        border-color: #006666;
                        font-size: 14px;
                        color: #CC0000;
                        font-weight: bold;
                        background-color:#fff;
                        border-right: 2px solid #eee;
                        border-bottom: 2px solid #eee;
                        text-transform: uppercase;
                    }
                    .administrador td{
                        border-color: #006666;
                        font-size: 14px;
                        color: #006666;
                        font-weight: bold;
                        background-color:#fff;
                        border-right: 2px solid #eee;
                        border-bottom: 2px solid #eee;
                        text-transform: uppercase;
                    }
                    .gris td{
                        border-color: #006666;
                        font-size: 11px;
                        color: #006666;
                        font-weight: bold;
                        background-color:#eee;
                        border-right: 2px solid #eee;
                        border-bottom: 2px solid #eee;
                        text-transform: uppercase;
                    }
                    .rojo{
                        color: #CC0000;
                        font-weight: bold;
                        text-transform: uppercase;
                    }
                    .naranja{
                        font-size: 11px;
                        color: #F6921E;
                        font-weight: bold;
                        text-transform: uppercase;
                    }
                    .girar{
                        -webkit-transform: rotate(-90deg);
                        -moz-transform: rotate(-90deg);
                        -o-transform: rotate(-90deg);
                        -ms-transform: rotate(-90deg);
                        transform: rotate(-90deg);
                    }
                    .negro {
                        color:#292929;
                    }
                    .negro2 {
                        color:#292929;
                        font-size: 15px;
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
                    .calidad tr{
                        color: #045FB4;
                        font-weight: bold;
                    }
                    /*Contenedores*/
                    #templatemo_wrapper {
                        width: 1250px;
                        padding: 0 10px;
                        margin: 0 auto;
                    }
                    #templatemo_header {
                        width: 1250px;
                        font-size: 8px;
                        height: 100px;
                    }
                    #templatemo_menu {
                        width: 1250px;
                        height: 50px;
                        background-color: #292929;
                    }
                    #sidebar {
                        float: left;
                        width: 199px;
                        padding: 20px 30px 0 10px;
                        border-right: 1px solid #006666;
                        font-size: 14px;
                    }
                    .sidebar_title {
                        cursor:default;
                        font-size: 8px;
                    }
                    .sidebar_title:hover {
                        text-decoration:none;
                    }
                    #sidebar h3 {
                        padding: 0 0 15px 0;
                        margin: 0 0 15px 0;
                        background: url(../images/sidebar_header_bg.png)  left bottom no-repeat
                    }
                    .sidebar_menu {
                        list-style: none;
                        margin: 0 0 30px;
                        padding: 0;
                    }
                    .sidebar_menu li {
                        margin: 0;
                        padding: 2px 0 3px 15px;
                        background: url(../images/templatemo_list_01.png) no-repeat scroll 0 8px;
                        border-bottom: 1px dotted #ccc
                    }
                    .sidebar_menu li a {
                        color: #292929;
                        /*    font-weight: bold;*/
                    }

                    #content {
                        float: right;
                        width: 970px;
                        /*    padding: 20px 10px 20px;*/
                    }
                    #content_sin {
                        float: right;
                        width: 1240px;
                        /*    padding: 20px 10px 20px;*/
                    }

                    #site_title { float: left; margin-top: 20px; }
                    #site_title h1 { margin: 0; padding: 0 }
                    #site_title h1 a { display: block; width: 400px; padding: 35px 0 0 100px; color: #006666; text-align: left; background: url(../images/templatemo_logo.png) no-repeat top left }

                    #header_right { float: right; display: inline-block;	 padding-top: 20px; }

                    .master_opciones{
                        width: 600px;
                        height: 400px;
                        background-color: #006666;
                        color: #FFF;
                    }
                </style>
                <!--Imprimir-->
                <script language="javascript">
                    function Imprimir() {
                        var objeto = document.getElementById('Imprimir');  //obtenemos el objeto a imprimir
                        var ventana = window.open('', '_blank');  //abrimos una ventana vacía nueva
                        ventana.document.write(objeto.innerHTML);  //imprimimos el HTML del objeto en la nueva ventana
                        ventana.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />');  //imprimimos el HTML del objeto en la nueva ventana
                        ventana.document.close();  //cerramos el documento
                        ventana.print();  //imprimimos la ventana
                        ventana.close();  //cerramos la ventana
                    }
                </script>
                <!-- CSS acordion -->
                <link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css"/>
                <!--Arbol-->
                <link rel="stylesheet" href="Interfaz/Arbol/jquery.treeview.css" />
                <script src="Interfaz/Arbol/jquery.js" type="text/javascript"></script>
                <script src="Interfaz/Arbol/jquery.cookie.js" type="text/javascript"></script>
                <script src="Interfaz/Arbol/jquery.treeview.js" type="text/javascript"></script>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $("#browser").treeview({
                            toggle: function () {
                                console.log("%s was toggled.", $(this).find(">span").text());
                            }
                        });
                    });
                </script>
        </head>
        <body id="subpage" >
        <Formula:Formula />
        <Formula:Formula_app />
        <Alertas:Alertas />
        <script src="Interfaz/Acordeon/Js_accordeon.js" type="text/javascript"></script>
    </body>
</html>