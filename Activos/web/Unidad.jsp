<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Unidad.tld" prefix="unidad" %>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
         <jsp:include page="Contenedor_head.jsp"></jsp:include>
         <script type="text/javascript">
             history.pushState(null, null, 'Unidad.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Unidad.jsp');
                });
                  function ActivarUnidad(idUnidad) {
                    swal({
                        title: "Activar!",
                        text: "Seguro que desea cambiar de Estado?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = 'Unidad?opc=5&idUnidad=' + idUnidad + '';
                            });
                }
                function DesactivarUnidad(idUnidad) {
                    swal({
                        title: "Desactivar!",
                        text: "Seguro que desea cambiar de Estado?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = 'Unidad?opc=4&idUnidad=' + idUnidad + '';
                            });
                }
         </script>
    </head>
       <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:Menu/>
            <alertas:Alertas/>
            <unidad:Unidad/>
            
            </div>
    </body>
</html>