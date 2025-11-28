<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "Interfaz/Contenido/Scripts/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html >
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <title>Login</title>
                <jsp:include page='Contenedor_head.jsp'></jsp:include>
                    </head>
                    <body class="bg-gradient-primary">
                    <Alertas:Alertas />
                    <div class="container">
                        <!-- Outer Row -->
                        <div class="row justify-content-center">
                            <div class="col-xl-10 col-lg-12 col-md-9">
                                <div class="card o-hidden border-0 shadow-lg my-5">
                                    <div class="card-body p-0">
                                        <div class="col-lg-6">
                                            <div class="p-5">
                                                <div class="text-center">
                                                    <h5>PMP MTF</h5>
                                                    <img src="Interfaz/Images/PMP_MF.png" alt="Logo" class="logo_img"  /><br/>
                                                </div>
                                                <form class="user" id="FormLogin" action="Sesion?opc=1" method="post">
                                                    <div class="form-group">
                                                        <input type="text" class="form-control_index form-control-user" id="exampleInputUser" name="Txt_user" placeholder="Usuario">
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="password" class="form-control_index form-control-user" id="exampleInputPassword" name="Txt_password" placeholder="Password">
                                                    </div>
                                                    <a onclick="javascript:document.getElementById('FormLogin').submit();" class="btn btn-primary btn-user btn-block"><b style="color:white">LOGIN</b></a>
                                                    <br/><center><b>VA 13.06.08</b></center>
                                                    <!--<br/><center><b>VA 09.05.04</b></center>-->
                                                    <!--<br/><center><b>VA 07.04.01</b></center>-->
                                                    <hr />
                                                    <center><b>PMP MTF</b></center>
                                                    <p>Este sistema de información apoya la gestión del programa preventivo de mantenimiento del área de Mtto Farmaceutico.</p>
                                                </form>
                                            </div>
                                        </div>
                                        <!--</div>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Bootstrap core JavaScript-->
                    <script src="Interfaz/StylePage/vendor/jquery/jquery.min.js"></script>
                    <script src="Interfaz/StylePage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
                    <!-- Core plugin JavaScript-->
                    <script src="Interfaz/StylePage/vendor/jquery-easing/jquery.easing.min.js"></script>
                    <!-- Custom scripts for all pages-->
                    <script src="Interfaz/StylePage/js/sb-admin-2.min.js"></script>
                </body>
                </html>
