<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Materia_prima.tld" prefix="Materia_prima"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_formulas_new.ico" rel="icon" >
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <!--Controlar url-->
        <script type = "text/javascript" >
          history.pushState(null, null, 'Materia_prima.jsp');
          window.addEventListener('popstate', function (event) {
              history.pushState(null, null, 'Materia_prima.jsp');
          });
        </script>
        <title>Materia prima</title>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        <!-- CSS Principal -->
        <link href="Interfaz/Contenido/Css/Css_Principal_New.css" rel="stylesheet" type="text/css" />
        <!--Validaciones-->
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
            <script type="text/javascript">
                function DesactivarMateria(id_materia) {
                    swal({
                        title: "Finalizar!",
                        text: "Seguro que desea desactivar la materia prima?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#006666",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                    function () {
                        location.href='Materia_prima?opc=3&Id_materia=' + id_materia + '&Estado=0';
                    });
                }
                function ActivarMateria(id_materia) {
                    swal({
                        title: "Finalizar!",
                        text: "Seguro que desea activar la materia prima?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#006666",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                    function () {
                        location.href='Materia_prima?opc=3&Id_materia=' + id_materia + '&Estado=1';
                    });
                }
            </script>
    </head>
    <body id="subpage">
        <div id="templatemo_wrapper">
            <Menu:Menu />
            <Materia_prima:Materia_prima />
        </div>
        <Alertas:Alertas />
    </body>
</html>