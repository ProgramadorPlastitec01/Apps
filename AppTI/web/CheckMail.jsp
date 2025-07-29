<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/Tld_checkMail.tld" prefix="CheckMailx" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comprobador de correo</title>
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/Select-1.2.4/css/select.bootstrap4.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/datatables/datatables.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/modules/izitoast/css/iziToast.min.css">
        <link rel="stylesheet" href="Interface/Content/Assets/css/main.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <CheckMailx:CheckMail/>
                </div>
            </div>
        </div>

        <script>
            function ValidMail() {

                const host = document.getElementsByName("txtHost")[0].value.trim();
                const port = document.getElementsByName("txtPort")[0].value.trim();
                const mail = document.getElementsByName("txtMail")[0].value.trim();
                const pass = document.getElementsByName("txtfakePsw")[0].value.trim();
                const receptor = document.getElementsByName("txtReceptor")[0].value.trim();


                if (!host || !port || !mail || !pass || !receptor) {
                    iziToast.warning({
                        title: 'Atención!',
                        message: 'No ha diligenciado todos los campos.',
                        position: 'bottomRight'
                    });
                    return false;
                } else {
                    document.getElementById("contLoader").style.display = "block";
                    document.getElementById("formCheckMail").submit();
                    document.getElementById("ContFormData").classList.add("blockerContainer");
                    return true;
                }
            }
        </script>
        
        
        <script>
            function mostrarPass() {
                var password = document.getElementById("txtPassw");
                var eye = document.getElementById("icon");
                if (password.type == "password") {
                    password.type = "text";
                    eye.className = "fas fa-eye-slash iconEye";
                } else {
                    password.type = "password";
                    eye.className = "fas fa-eye iconEye";
                }
            }
        </script>
        
        <script>
            function mostrarPass2() {
                var password = document.getElementById("spanPw");
                var eye = document.getElementById("iconz");
                if (password.classList.contains("password-hidden")) {
                    password.classList.remove("password-hidden");
                    eye.className = "fas fa-eye-slash iconEye";
                } else {
                    password.classList.add("password-hidden");
                    eye.className = "fas fa-eye iconEye";
                }
            }
        </script>

        <script src="Interface/Content/Assets/modules/datatables/DataTables-1.10.16/js/dataTables.bootstrap4.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/Select-1.2.4/js/dataTables.select.min.js"></script>
        <script src="Interface/Content/Assets/modules/datatables/datatables.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-datatables.js"></script>
        <script src="Interface/Content/Assets/modules/izitoast/js/iziToast.min.js"></script>
        <script src="Interface/Content/Assets/js/page/modules-toastr.js"></script>
        <script src="Interface/Content/Assets/js/page/bootstrap-modal.js"></script>
    </body>
</html>
