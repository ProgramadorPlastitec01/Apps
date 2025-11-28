<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib uri="/WEB-INF/Tlds/Devolucion.tld" prefix="Devolucion" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Devolucion.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Inicio.jsp');
            });
        </script>
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
        </head>
        <body id="page-top">
            <div id="wrapper">
            <Menu:Menu />
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <Menu:Sesion />
                    <div class="container-fluid">
                        <Devolucion:Devolucion/>
                    </div>
                </div>
            </div>
        </div>
        <Alertas:Alertas />
        <!-- Bootstrap core JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery/jquery.min.js"></script>
        <script src="Interfaz/StylePage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="Interfaz/StylePage/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="Interfaz/StylePage/js/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
        <script src="Interfaz/StylePage/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="Interfaz/StylePage/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <!-- Page level custom scripts -->
        <script src="Interfaz/StylePage/js/demo/datatables-demo.js"></script>
    </body>
</html>
