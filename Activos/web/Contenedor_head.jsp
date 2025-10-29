<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="image/png" href="Interfaz/Contenido/images/act_logo.ico" rel="icon" >
<link  rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Principal.css" />
<link  rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_tooltip.css" />
<link href="Interfaz/Contenido/Css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<script src="Interfaz/Contenido/Scripts/jquery-3.0.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/FontAwesome/css/all.css">
<!--Validaciones-->
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<link rel="stylesheet" type="text/css" href="Interfaz/Validacion/StyleSheetLiveValidation.css">
<!-- JavaScript Tooltip -->
<link href="Interfaz/Tooltip/css/tooltipster.bundle.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-borderless.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-light.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-noir.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-punk.min.css" rel="stylesheet" type="text/css"/>
<link href="Interfaz/Tooltip/css/plugins/themes/tooltipster-sideTip-shadow.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="Interfaz/Tooltip/js/tooltipster.bundle.min.js"></script>
<!--  ALERTAS  --->
<script src="Interfaz/Alertas/dist/sweetalert.min.js" type="text/javascript"></script>
<link href="Interfaz/Alertas/dist/sweetalert2018.css" rel="stylesheet" type="text/css"/>
<!--Paginacion-->
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<!--Filtrar-->
<script type="text/javascript" src="Interfaz/Paginas/filtro.js"></script>
<!-- CSS Menu NW -->
<!-- CSS Menu -->
<link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Menu.css" />
<link rel="stylesheet" type="text/css" href="Interfaz/Tabs/tabs.css" />
<link rel="stylesheet" type="text/css" href="Interfaz/Checkbox/magic-input.css" />
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
<!-- Menu flotante -->
<script src="Interfaz/Contenido/Scripts/jquery-1.10.2.js"></script>
<!--CALENDARIOS-->
<script src="Interfaz/Calendarios/moment.js" type="text/javascript"></script>
<link href="Interfaz/Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<script src="Interfaz/Calendarios/pikaday.js" type="text/javascript"></script>
<!--EDITOR-->
<script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
<script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
<!-- JavaScript desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Requisicion.js"></script>
<script type="text/javascript">
    ddsmoothmenu.init({
        mainmenuid: "templatemo_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    });
</script>
<script type="text/javascript">
    function mostrar(id, idProceso, activoUsado) {
        if (document.getElementById("emergente" + id).style.display === "none") {
            document.getElementById("emergente" + id).style.display = "block";
            document.getElementById("idProcesoM").value = idProceso;
            document.getElementById("activoUsado").value = activoUsado;
        } else if (document.getElementById("emergente" + id).style.display === "block") {
            document.getElementById("emergente" + id).style.display = "none";
        }
        for (i = 0; i < document.form1.elements.length; i++) {
            if (document.form1.elements[i].type === "checkbox") {
                var contenido = document.form1.elements[i].value;
                if (activoUsado.includes(contenido)) {
                    document.form1.elements[i].checked = 1;
                }
            }
        }
    }
    function mostrarVentana(id) {
        if (document.getElementById("Ventana" + id).style.display === "none") {
            document.getElementById("Ventana" + id).style.display = "block";
        } else if (document.getElementById("Ventana" + id).style.display === "block") {
            document.getElementById("Ventana" + id).style.display = "none";
        }
    }
    function mostrarFecha(id) {
        if (document.getElementById("Fechas" + id).style.display === "none") {
            document.getElementById("Fechas" + id).style.display = "block";
        } else if (document.getElementById("Fechas" + id).style.display === "block") {
            document.getElementById("Fechas" + id).style.display = "none";
        }
    }
    function mostrarConvecion(id) {
        if (document.getElementById("Convecion" + id).style.display === "none") {
            document.getElementById("Convecion" + id).style.display = "block";
        } else if (document.getElementById("Convecion" + id).style.display === "block") {
            document.getElementById("Convecion" + id).style.display = "none";
        }
    }
    function mostrarFldDetalle(id, detalle) {
        if (document.getElementById("detalle").style.display === "none") {
            document.getElementById("detalle").style.display = "block";
            document.getElementById("idCotizacion").value = id;
            document.getElementById("detalle").value = detalle;
        } else if (document.getElementById("detalle").style.display === "block") {
            document.getElementById("detalle").style.display = "none";
        }
    }
    function mostrarHistorial(id) {
        if (document.getElementById("historial").style.display === "none") {
            document.getElementById("historial").style.display = "block";
            document.getElementById("idRequisicion").value = id;
        } else if (document.getElementById("historial").style.display === "block") {
            document.getElementById("historial").style.display = "none";
        }
    }
</script>
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
    function ReestablecerPass(idUsuario) {
        swal({
            title: "Restablecer!",
            text: "Seguro que desea restablecer contraseña?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#6D256F",
            confirmButtonText: "Aceptar",
            cancelButtonText: "Cancelar",
            closeOnConfirm: false
        },
                function () {
                    location.href = 'Usuario?opc=6&idUsuario=' + idUsuario + '';
                });
    }
</script>
<script>
    var acc = document.getElementsByClassName("accordion");
    var i;
    for (i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var Despliege = this.nextElementSibling;
            if (Despliege.style.maxHeight) {
                Despliege.style.maxHeight = null;
            } else {
                Despliege.style.maxHeight = Despliege.scrollHeight + "px";
            }
        });
    }
</script>
<script>
        var tableToExcel = (function() {    
            var uri = 'data:application/vnd.ms-excel;base64,'
                    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
                    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
                    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
                            return function(table, name) {
                            if (!table.nodeType) table = document.getElementById(table)
                            var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
                            window.location.href = uri + base64(format(template, ctx))
                                        }
                           })()
</script>
<!-- Menu flotante -->
<style>
    #toggle {
        float: left;
        width: 240px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;

    }
    #toggle2 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;   

    }
    #toggle3 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
    #toggle4 {
        position: absolute;
        float: right;
        width: 300px;
        font-size: 14px;
        right: 215px;
        background-color: #fff;
    }
    #toggle5 {
        float: left;
        width: 242px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
</style>
