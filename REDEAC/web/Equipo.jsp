<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_equipo.tld" prefix="equipo" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Equipo</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <!--HTML editor-->
            <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
            <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
            <script language="javascript" type = "text/javascript" src = "Interfaz/Graficas/Chart.js"></script>
            <link href="Interfaz/Contenido/Css/bootstrap-select.css" rel="stylesheet">
            <script src="Interfaz/Contenido/Scripts/bootstrap-select.js"></script>
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
            <script type="text/javascript">
                function agregar() {
                    var fecha = document.getElementById("datepicker").value;
                    var registro = document.getElementById("registro-id").value;
                    if (fecha !== '' && registro !== '') {
                        var generar = document.getElementById("generar").value;
                        document.getElementById("generar").value = generar + "[" + fecha + "//" + registro + "]";
                        document.getElementById("tableG").insertRow(-1).innerHTML = "<tr><td>" + fecha + "</td><td>" + registro.split("//")[2] + "</td><td align='center'><i class='fas fa-minus fa-lg' onclick='quitar(this,\"" + fecha + "//" + registro + "\")'></i></td></tr>";
                    }
                }
                function quitar(id, registro) {
                    var generar = document.getElementById("generar").value;
                    generar = generar.replace("[" + registro + "]", "");
                    document.getElementById("generar").value = generar;
                    var cell = id.parentNode;
                    var row = cell.parentNode;
                    document.getElementById("tableG").deleteRow(row.rowIndex);
                }
                function EliminarRegistro(id_equipo, id_hojaV) {
                    swal({
                        title: "Eliminar",
                        text: "¿Seguro que Desea Eliminar el registro?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#6D256F",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false
                    },
                            function () {
                                location.href = "Equipo?opc=6&mod=HVE&txt_bus=&idE=" + id_equipo + "&idHR=" + id_hojaV + "";
                            });
                }
                function MostrarInput(value) {
                    if (value == 1) {
                        document.getElementById("Txt_aplicativo").style.display = "block";
                    } else {
                        document.getElementById("Txt_aplicativo").style.display = "none";
                    }
                }
                function Masivo(ide) {
                    var id = "[" + ide + "]";
                    var content = document.getElementById("Txt_ids").value;
                    if (content.includes(id)) {
                        document.getElementById("Txt_ids").value = content.replace(id, "");
                    } else {
                        document.getElementById("Txt_ids").value += id;
                    }
                }
            </script>
        </head>
        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <equipo:MuestraEquipo/>
        </div>
        <resultado:MuestraResultado/>
        <script>
            $('select').selectpicker({
                width: '196px'
            }
            );
        </script>
        <script>
//    CKEDITOR.replace("editor");
</script>
        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>
