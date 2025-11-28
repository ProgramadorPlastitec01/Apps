<%@page contentType="text/html" pageEncoding="ISO-8859-1" %>
<%@taglib  uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu" %>
<%@taglib  uri="/WEB-INF/Tlds/Reportes.tld" prefix="Reportes" %>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title></title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Reportes.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Reportes.jsp');
            });
            function Filtrartodo() {
                Filtrar();
                Filtrar2();
                Filtrar3();
                Filtrar4();
                Filtrar5();
                Filtrar6();
                Filtrar7();
                Filtrar8();
                Filtrar9();
                Filtrar10();
                Filtrar11();
            }
            function VolverReporteIndicador() {
                location.href = 'Reportes?opc=2&vlr=&fin=&ffn=';
            }
            function VolverReporteProximaOT() {
                location.href = 'Reportes?opc=6&fcha=';
            }
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
                        <Reportes:Reportes />
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
        <!-- Calendarios -->
        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>
