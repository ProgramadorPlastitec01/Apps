<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menus"%>
<%@taglib uri="/WEB-INF/tlds/Informes.tld" prefix="Informes"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Inicio</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menus:Menu />
            <Informes:Informes />
            <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
            <script src="Interfaz/Calendarios/Js_range.js" type="text/javascript"></script>
            <script type="text/javascript" src="Interfaz/Tabs/tabs.js"></script>
        </div>
    </body>
</html>