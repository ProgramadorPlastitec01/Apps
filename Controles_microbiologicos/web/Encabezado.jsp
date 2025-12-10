<link type="image/png" href="Interfaz/Contenido/images/Control_microbiologico.png" rel="icon" >
<!-- CSS Principal -->
<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
<!--Validaciones-->
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
<!-- CSS Menu -->
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Menu.css" />
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
<!-- JavaScript paginacion -->
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<!-- JavaScript desplega menu -->
<script type="text/javascript">
    ddsmoothmenu.init({
        mainmenuid: "templatemo_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    })
</script>
<!--tabs-->
<style type="text/css" media="screen">@import "Interfaz/Tabs/tabs.css";</style>
<link rel="stylesheet" href="Interfaz/Acordeon/Acordeon_principal.css">
<link rel="stylesheet" href="Interfaz/Acordeon/index_style.css">
<!--Imprimir-->
<script type="text/javascript" language="javascript">
    function Imprimir() {
        var contenedor = document.getElementById("Imprimir").innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<html><head><title>Imprimir</title>');
        frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Menu.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Inicio.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contenedor);
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame);
        }, 300);
        return false;
    }
    function restablecePass(id_Usuario) {
        swal({
            title: "Restablecer!",
            text: "Seguro que desea restablecer la Contraseña al año en curso?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#A146BF",
            confirmButtonText: "Aceptar",
            cancelButtonText: "Cancelar",
            closeOnConfirm: false
        },
                function () {
                    location.href = 'Usuario?opc=7&idUsuario=' + id_Usuario + '';
                });
    }
</script>
<!--Alertas-->
<link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
<script src="Interfaz/Alertas/dist/sweetalert.min.js" type="text/javascript"></script>
<!--Graficas-->
<script type="text/javascript" src="Interfaz/Graficas/js/JS_4GRAFICS.js"></script>
<style type="text/css">
    ${demo.css}
</style>
<!--Calendarios-->
<script src="Interfaz/Calendarios/moment.js" type="text/javascript"></script>
<link href="Interfaz/Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<script src="Interfaz/Calendarios/pikaday.js" type="text/javascript"></script>
<!--Filtrar-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<style>
    #toggle {
        float: left;

        font-size: 14px;

        position: fixed;



    }
</style>