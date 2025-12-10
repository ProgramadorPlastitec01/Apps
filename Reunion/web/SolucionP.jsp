<%-- 
    Document   : SolucionP
    Created on : 26/10/2017, 04:51:58 PM
    Author     : Aprendiz.Sena1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/Tlds/Solucion.tld" prefix="Solucion"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<!DOCTYPE html>
<html>
    <head>
        <!--Validaciones-->
        <link href="Interfaz/Validacion/StyleSheetLiveValidation.css" media="screen" rel="stylesheet" type="text/css" />
        <script src='Interfaz/Validacion/LiveValidation.js'></script>
        <!--Enlaces para modal popup-->
        <script src="Interfaz/ModalPopup/jqmodal.js"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/ModalPopup/cssmodal.css">
        <!-- HTML Editor-->
        <!--HTML editor-->
        <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
        <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
        <!--FIN Enlaces para modal popup-->
        <link href="Interfaz/Contenido/CSS_Principal.css" rel="stylesheet" type="text/css" />
        <link type="image/png" href="Interfaz/Contenido/images/Reunion.ico" rel="icon" />
        <link href="Interfaz/Contenido/CSS_Principal.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Solucion Pendiente</title>
        <script language="javascript" type = "text/javascript" src = "tinyfck/tiny_mce.js"></script>
        <script language="javascript" type = "text/javascript" src = "tinyfck/HTMLEditor.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <script type="text/javascript">
                function Informe() {
                    var htmleditor = document.getElementsByName("HTML_Editor").innerHTML;
                    document.getElementsByName("Txt_descripcion").value = htmleditor;
                    document.Form_informe.submit();
                }

                function Finalizar(id_pnd) {

                    swal({
                        title: "Pendiente Solucionado?",
                        text: "Una vez finalizado no podra modificar de nuevo",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "green",
                        confirmButtonText: "Aceptar",
                        cancelButtonText: "Cancelar",
                        closeOnConfirm: false,
                    },
                            function () {
                                location.href = 'Solucionp?opc=3&idpnd=' + id_pnd + '&est=1';
                            });

                }
            </script>
        </head>
        <body>
            <div id="templatemo_wrapper">
            <Solucion:Solucion/>
        </div>
        <Alertas:Alertas/>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
        <script src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
    </body>
</html>
