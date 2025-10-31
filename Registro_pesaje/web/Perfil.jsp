<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Perfil.tld" prefix="perfil" %>
<%@taglib uri="/WEB-INF/tlds/Resultado.tld" prefix="result" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="image/png" href="Interfaz/Contenido/Imagenes/Logo.png" rel="icon" >
        <script type = "text/javascript" >
            history.pushState(null, null, 'Perfil.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Perfil.jsp');
            });
        </script>
        <title>Perfil | Registro Pesaje</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"></jsp:include>
            <div class="cont_total2" id="cont_total">
                <div style="width: 100%; margin-top: 10px;">
                <perfil:Perfil />
            </div>
        </div>
        <script>
            function MasivoPhoto(ide) {
                var content = document.getElementById("txt_img").value;
                document.getElementById("txt_img").value = ide;

            }

        </script>
        <script>
// Add active class to the current button (highlight it)
            var header = document.getElementById("myDIV");
            var btns = header.getElementsByClassName("btn_bt");
            for (var i = 0; i < btns.length; i++) {
                btns[i].addEventListener("click", function () {
                    var current = document.getElementsByClassName("active2");
                    current[0].className = current[0].className.replace(" active2", "");
                    this.className += " active2";
                });
            }
        </script>
        <script>
            function Confirmacion(id_usuario) {
                swal({
                    title: "¿Desea cambiar contraseña?",
                    text: "<p>Si realiza cambio de contraseña, tendra que inciar sesion nuevamente.</p><a href='Perfil?opc=1&id_usuario="+ id_usuario +"' id='formVolver' method='post'><button class='btn btn-danger btn-sm' type='submit' required  form='formVolver'>Cancelar</button></a>\n\
                                &nbsp;&nbsp;<a href='Login?opc=1&temp=1&Txt_user="+ id_usuario +"'><button class='btn btn-primary btn-sm' type='submit' required  form='formVerificacion'>Confirmar</button></a>",
                    type: "warning",
                    showConfirmButton: false,
                    html: true
                });
            }
        </script>
            <result:ResultadosAlertas />
    </body>
</html>
