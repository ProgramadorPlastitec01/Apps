<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_profile.tld" prefix="Profile" %>
<%@taglib uri="/WEB-INF/tlds/Tld_alert.tld" prefix="Alerts" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/profile.css">
        <link rel="stylesheet" href="Interface/Content/Validation/StyleSheetLiveValidation.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Profile:Profile/>
                </div>
            </div>
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

        <Alerts:Alert/>
        <script src="Interface/Content/Validation/LiveValidation.js"></script>
        <script src="Interface/Content/Assets/js/Profile.js"></script>
        <script src="Interface/Content/Assets/modules/chart.min.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
