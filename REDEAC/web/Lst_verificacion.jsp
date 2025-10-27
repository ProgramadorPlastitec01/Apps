<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_lst_verificacion.tld" prefix="lst_verificacion" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Listado Equipos</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
            <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
            <script type="text/javascript">
                function registrar() {
                    var nombre = document.getElementById("nombre-id").value;
                    if (nombre !== "") {
                        document.formLE.submit();
                    }
                }
                function modEquipo(equipo) {
                    var equiEst = equipo.split("/");
                    document.getElementById("nombreM-id").value = equiEst[0];
                    document.getElementById("nombreOld-id").value = equipo;
                    for (var i = 0; i < document.formME.estadoId.length; i++) {
                        if (document.formME.estadoId[i].value === equiEst[1]) {
                            document.formME.estadoId[i].checked = true;
                        }
                    }
                }
                function EliminarR(id_equipo, id_hojaA) {
                    swal({
                        title: "Eliminar",
                        text: "¿Seguro que Desea eliminar el registro?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Lst_verificacion?opc=8&idLV=0&idVR=" + id_equipo + "&idHV=0&idAD=" + id_hojaA + "&mod=HVV";
                            });
                }
            </script>
            <script>
                function Volver() {
                    location.href = "Lst_verificacion?opc=1&idLV=0&idLVR=0&mod=LV&txt_bus=";
                }
            </script>

        </head>
        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <lst_verificacion:MuestraLst_verificacion/>
        </div>
        <resultado:MuestraResultado/>
        <script>
            $('select').selectpicker({
                width: '188px'
            });
        </script>
         <script>
    CKEDITOR.replace("editor");
</script>
        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>
