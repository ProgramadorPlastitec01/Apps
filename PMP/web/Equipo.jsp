<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Equipo.tld" prefix="Equipo"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/PMP_MI.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Equipos</title>
            <script type = "text/javascript" >
                history.pushState(null, null, 'Equipo.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Equipo.jsp');
                });
            </script>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <script type="text/javascript">
                    function DesactivarEquipo(id_equipo) {
                        swal({
                            title: "Desactivar Equipo?",
                            text: "<form action='Equipo?opc=2&Id_equipo=" + id_equipo + "&Estado=0' id='formVerificacion' method='post'<form action='Equipo?opc=2&Id_equipo=" + id_equipo + "&Estado=0' id='formVerificacion' method='pst'>\n\
                            <textarea name='Txt_justificacion' placeholder='Añade una justificación' style='margin: 0px 0px 10px; width: 319px; height: 59px;'></textarea></form>\n\
                              <a href='Equipo?opc=1&ieq=0&ot=0&fto=' id='formVolver' method='post'><button type='submit' required  form='formVolver'>Volver</button></a>\n\
                                &nbsp;&nbsp;<button type='submit' required  form='formVerificacion'>Enviar</button>",
                                    type: "warning",
                            showConfirmButton: false,
                            html: true
                        },
                                function () {
                                    location.href = 'Equipo?opc=2&Id_equipo=' + id_equipo + '&Estado=0';
                                });
                    }
                    function ActivarEquipo(id_equipo) {
                        swal({
                            title: "Activar Equipo?",
                            text: "Seguro que desea activar el equipo...!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "green",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false,
                        },
                                function () {
                                    location.href = 'Equipo?opc=2&Id_equipo=' + id_equipo + '&Estado=1';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function Form_registro_equipo() {
                        document.getElementById('Form_registro_equipo').style.display = 'block';
                    }
                    function Form_registro_equipo_cerrar() {
                        document.getElementById('Form_registro_equipo').style.display = 'none';
                    }
                </script>
                <script type="text/javascript">
                    function PostBackParametro() {
                        var Tipo_parametro = document.getElementById("Cbx_tipo_parametro");
                        document.forms['FormParametro'].submit();
                    }
                    function PostBackCategoria() {
                        var Tipo_parametro = document.getElementById("Cbx_tipo_categoria");
                        document.forms['FormCategoria'].submit();
                    }
                    function AplicaPMP(aplica) {
                        if (aplica > 0) {
                            document.getElementById('Div_PMP').style.display = 'block';
                        } else {
                            document.getElementById('Div_PMP').style.display = 'none';
                        }
                    }
                </script>
        </head>
        <body id="subpage">
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Equipo:Equipo />
        </div>
        <Alertas:Alertas />
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>