
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/profile" prefix="Profile" %>
<%@taglib uri="/WEB-INF/tlds/alert" prefix="Alert" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/Logo_app/IconW.fw.png">
        <link rel="stylesheet" href="Interface/Content/Validation/StyleSheetLiveValidation.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="icon" type="image/png" href="Interface/Imagen/LogoSWhite.png">
        <title>Perfil</title>
        <script type="text/javascript">
            history.pushState(null, null, 'Perfil.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Perfil.jsp');
            });
        </script>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Profile:Profile/>
                </div>
            </div>
            <Alert:Alert/>
        </div>
        <script>
            function mostrarAlertaPass() {
                iziToast.info({
                    title: 'Requisitos de contraseña',
                    message:
                            "Debe tener entre 8 y 15 caracteres, incluir:<br>" +
                            "- Una letra mayúscula<br>" +
                            "- Una letra minúscula<br>" +
                            "- Un número<br>" +
                            "- Un carácter especial ($@!%*?&...)",
                    position: 'bottomRight',
                    timeout: 9000,
                    messageColor: '#000',
                    backgroundColor: '#FFFBEA',
                    titleColor: '#d58512',
                    icon: 'fa fa-info-circle'
                });
            }

            document.addEventListener("DOMContentLoaded", function () {
                var validatedObj = new LiveValidation('pass-input');
                validatedObj.add(function (value) {
                    if (value === '')
                        return true;
                    return Validate.Password(value, {
                        failureMessage: "La contraseña no cumple con los requisitos"
                    });
                }, {failureMessage: "La contraseña no cumple con los requisitos"});
            });
        </script>
        <script>
            function validarNombreArchivo(input) {
                const file = input.files[0];
                if (file) {
                    const nombreArchivo = file.name;
                    // 1. Validar longitud
                    if (nombreArchivo.length > 50) {
                        iziToast.warning({
                            title: 'Atención!',
                            message: 'El nombre del archivo no debe superar los 50 caracteres.',
                            position: 'bottomRight'
                        });
                        input.value = "";
                        return;
                    }
                    const caracteresInvalidos = /[ñÑ<>:"\/\\|?*\x00-\x1F]/g;
                    if (caracteresInvalidos.test(nombreArchivo)) {
                        iziToast.warning({
                            title: 'Atención!',
                            message: 'El nombre del archivo contiene caracteres no permitidos: ñ Ñ <> : \" / \\ | ? *',
                            position: 'bottomRight'
                        });
                        input.value = "";
                        return;
                    }
                }
            }
        </script>

        <script src="Interface/Content/Validation/LiveValidation.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
    </body>
</html>
