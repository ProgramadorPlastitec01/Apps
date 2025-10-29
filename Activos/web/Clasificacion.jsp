<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Clasificacion.tld" prefix="clasificacion" %>
<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="alertas" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVOS</title>
         <jsp:include page="Contenedor_head.jsp"></jsp:include>
         <script type="text/javascript">
             history.pushState(null, null, 'Clasificacion.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Clasificacion.jsp');
                });
             function ActivarClasificacion(idClasificacion) {
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
                                location.href = 'Clasificacion?opc=5&idClasificacion=' + idClasificacion + '';
                            });
                }
                function DesactivarClasificacion(idClasificacion) {
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
                                location.href = 'Clasificacion?opc=4&idClasificacion=' + idClasificacion + '';
                            });
                }
            
         </script>
    </head>
       <body id="subpage">
            <div id="templatemo_wrapper">
            <menu:Menu/>
            <alertas:Alertas/>
               <clasificacion:Clasificacion/>
            
            </div>
    </body>
</html>