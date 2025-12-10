<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Inicio.tld" prefix="Inicio"%>
<%@taglib uri="/WEB-INF/Tlds/Calendario.tld" prefix="Calendario"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/locativos.ico" rel="icon" />
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
           <title>Locativos MT</title>
            <!-- CONTROL ENVIO DE PETICIONES -->
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
                history.pushState(null, null, 'Inicio.jsp');
                window.addEventListener('popstate', function(event) {
                    history.pushState(null, null, 'Inicio.jsp');
                });
            </script>
            <!-- CSS Principal -->
            <link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
            <!-- CSS Menu -->
            <link rel="stylesheet" type="text/css" href="Interfaz/Contenido/Css/CSS_Menu.css" />
            <!-- JQuery desplega menu -->
            <script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
            <!-- JQuery desplega menu -->
            <script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
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

            <link rel="stylesheet" href="Interfaz/calendar/fullcalendar.css" />
            <script src="Interfaz/calendar/lib/jquery.min.js"></script>
            <script src="Interfaz/calendar/lib/moment.min.js"></script>
            <script src="Interfaz/calendar/fullcalendar.js"></script>
            <script src="Interfaz/calendar/lang/es.js"></script>
    </head>
    <body id="subpage" style="background:#FFF url(Interfaz/Contenido/images/pattern.png) repeat top left;">
        <div id="templatemo_wrapper">
            <Menu:Menu/>
            <Inicio:Inicio />
        </div>
    </body>
</html>
        