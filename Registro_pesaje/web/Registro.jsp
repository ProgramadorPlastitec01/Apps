<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/registros.tld" prefix="Registros" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="Resultado" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <!--        <script type = "text/javascript" >
                    history.pushState(null, null, 'Registro.jsp');
                    window.addEventListener('popstate', function (event) {
                        history.pushState(null, null, 'Registro.jsp');
                    });
                </script>-->
        <title>Registro | Registro Pesaje</title>

    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <Registros:Registro/>
                <Resultado:ResultadosAlertas/>
            </div>
        </div>

        <script>
            function lote() {
                var fecha = document.getElementById("datepicker2").value;

                if (fecha === "") {
                    swal({
                        title: "Atencion!",
                        text: "No se ha seleccionado una fecha.",
                        type: "warning"
                    });
                } else {
                    var anio = fecha.split("-");
                    var meses = ["X", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"];
                    var mes = parseInt(anio[1]);
                    var year = parseInt(anio[0]) - 1990;
                    var cadena = year + meses[mes] + anio[2];
                    cadena = cadena.toString();
                    document.getElementById("Txt_lote").value = cadena;
                }
            }

        </script>
        <script type="text/javascript">
            function printSection(el) {
                var getFullContent = document.body.innerHTML;
                var printsection = document.getElementById(el).innerHTML;
                document.body.innerHTML = printsection;
                window.print();
                document.body.innerHTML = getFullContent;
            }
        </script>
        <script type="text/javascript">
            function SubmitForm() {
                var html = document.getElementById("templateMajor").innerHTML;
                document.getElementById("templateSecondary").value = html;
                let form = document.getElementById("FormSignature");
                form.submit();
            }
        </script>
        <script type="text/javascript">
            function ValuePass() {
                var html = document.getElementById("templateMajor").innerHTML;
                document.getElementById("templateSecondary").value = html;
            }
        </script>
        <script type="text/javascript">
            function SubmitFormSave() {
                var html = document.getElementById("templateMajor").innerHTML;
                document.getElementById("templateThird").value = html;
                let form = document.getElementById("FormSave");
                form.submit();
            }
        </script>
        <script type="text/javascript">
            function SignatureClearance(id_order, id_record, id_clearance) {
                swal({
                    title: "Atencion!",
                    text: "Asegúrese de guardar antes de continuar, para liberar el despeje dar clic en Aceptar.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#c9e433",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false
                },
                        function () {
                            location.href = "Registro?opc=7&id_orden=" + id_order + "&id_registro=" + id_record + "&id_despeje=" + id_clearance + "&est=1";
                        }
                );
            }
        </script>
        <script type="text/javascript">
            function ConfirmationSave() {
                swal({
                    title: "Cuidado!",
                    text: "Asegúrese de guardar antes de continuar, para acceder a la opcion dar clic en Aceptar.",
                    type: "warning",
                    showCancelButton: true,
                    showConfirmButton: true,
                    confirmButtonColor: "#c9e433",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: true
                },
                        function () {
                            document.getElementById("Ventana5").style.display = 'block';
                        }
                );
            }
        </script>
        <script>
            function mostrarPass() {
                var password = document.getElementById("txtPassword");
                var eye = document.getElementById("icon");
                if (password.type == "password") {
                    password.type = "text";
                    eye.className = "fas fa-eye-slash";
                } else {
                    password.type = "password";
                    eye.className = "fas fa-eye";
                }
            }
        </script>
        <!--<img src="Interfaz/Contenido/Imagenes/reply.png">--> 
        <!--        <script type="text/javascript">
                    document.getElementById("lote1").addEventListener('keyup', autoCompleteNew);
        
                    function autoCompleteNew(e) {
                        var value = $(this).val();
                        $("#lote").val(value.replace('-', '').toLowerCase());
                    }
                </script>-->
        <link rel="stylesheet" type="text/css" href="Interfaz/Calendarios/pikaday.css">
        <script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
        <script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
        <script type="text/javascript" src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>
