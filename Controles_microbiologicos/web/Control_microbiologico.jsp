<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Control_microbiologico.tld" prefix="Control_microbiologico"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_microbiologico.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Complementos</title>
            <jsp:include page="Encabezado.jsp"></jsp:include>
                <script>
                    history.pushState(null, null, 'Control_microbiologico.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Control_microbiologico.jsp');
                    });
                    function Cerrar_Analisis(cabecera_idCabecera) {
                        swal({
                            title: "Desactivar!",
                            text: "Seguro que desea desactivar el Analisis?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#A146BF",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Control_microbiologico?opc=8&Cabecera_idCabecera=' + cabecera_idCabecera + '';
                                });
                    }
                    function Abrir_Analisis(cabecera_idCabecera) {
                        swal({
                            title: "Activar!",
                            text: "Seguro que desea activar el Analisis?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#A146BF",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Control_microbiologico?opc=9&Cabecera_idCabecera=' + cabecera_idCabecera + '';
                                });
                    }
                </script>
                <script>
                    function register() {
                        var x = document.getElementById('toggle');
                        if (x.style.display === 'none') {
                            x.style.display = 'block';
                        } else {
                            x.style.display = 'none';
                        }
                    }
                     
                </script>
            </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Control_microbiologico:Control_microbiologico />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
    </body>
</html>