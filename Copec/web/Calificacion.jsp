<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Calificacion.tld" prefix="Calificacion"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Calificacion</title>
        <link rel="icon" type="image/png" href="Interfaz/Template/images/Ico.ico" />
        <link rel="stylesheet" href="Interfaz/Template/Css/CSS_Principal.css" media="screen">
        <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
        <link rel="stylesheet" href="Interfaz/LiveValidation/StyleSheetLiveValidation.css" media="screen">
        <script type="text/javascript" src="Interfaz/LiveValidation/LiveValidation.js"></script>
        <script type="text/javascript" src="Interfaz/alertasweet/sweetalert.min.js"></script>
        <link rel="stylesheet" type="text/css" href="Interfaz/alertasweet/sweetalert.css">
        <script type="text/javascript" src="Interfaz/alertasweet/alertas_copec.js"></script>
        <!-- reloj-->
        <script type="text/javascript" src="Interfaz/TimeCircle/inc/jquery.min.js"></script>
        <script type="text/javascript" src="Interfaz/TimeCircle/inc/TimeCircles.js"></script>
        <link rel="stylesheet" href="Interfaz/TimeCircle/inc/TimeCircles.css">
        <script type="text/javascript" src="Interfaz/Template/Scripts/Calificar.js"></script>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Calificacion.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Calificacion.jsp');
            });
        </script>
        <script type="text/javascript">
            function Image(url) {
                //var myWindow = window.open("","child","width=1000,height=1000,status=no,directories=no,menubar=no,toolbar=no,scrollbars=no,location=no,resizable=no,titlebar=no");
                var myWindow = window.open("", "child", "width=1024,height=720,left=50,top=50,toolbar=no,resizable=yes,location=no");
                myWindow.document.write("<div style='width:100%;height:100%;'><img src='" + url + "' width='100%'></div>");
                return false;
            }
        </script>
        <script type="text/javascript" >
            function SPrueba(tiempo, usuario, idU, idP, documento, codigo, id_programacion) {
                swal({
                    title: "Seguro que desea iniciar la prueba?",
                    text: "" + usuario + " <br />Recuerde que la prueba solo tiene 1 intento <br /> y tendra un tiempo limite de " + tiempo + " minutos",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#048B86",
                    confirmButtonText: "Aceptar",
                    cancelButtonText: "Cancelar",
                    closeOnConfirm: false,
                    closeOnCancel: false,
                    html: true
                },
                        function (isConfirm) {
                            if (isConfirm) {
                                location.href = "Calificacion?opc=3&idP=" + idP + "&idU=" + idU + "&idProg=" + id_programacion + "";
                            } else {
                                location.href = "Calificacion?opc=2&txt_documento=" + documento + "&txt_codigo=" + codigo + "&mod=0";
                            }
                        });
            }
        </script>
        <script type="text/javascript" language="javascript">
            function Imprimir() {
                var contenedor = document.getElementById("Imprimir").innerHTML;
                var frame = document.createElement("iframe");
                frame.name = "frame1";
                frame.style.position = "absolute";
                frame.style.top = "-1000000px";
                document.body.appendChild(frame);
                var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
                frameDoc.document.open();
                frameDoc.document.write('<html><head><title>Imprimir</title>');
                frameDoc.document.write('<link href="Interfaz/Acordeon/Css_accordeon.css" rel="stylesheet" type="text/css" />');
                frameDoc.document.write('<link href="Interfaz/Template/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />');
                frameDoc.document.write('</head><body>');
                frameDoc.document.write(contenedor);
                frameDoc.document.write('</body></html>');
                frameDoc.document.close();
                setTimeout(function () {
                    window.frames["frame1"].focus();
                    window.frames["frame1"].print();
                    document.body.removeChild(frame);
                }, 300);
                return false;
            }
        </script>
    </head>
    <body onload="ResultadoAlert();">
        <div id="subpage">
            <div id="templatemo_wrapper">
                <Calificacion:Calificacion />
                <script>
                    $("#CountDownTimer").TimeCircles({
                        count_past_zero: false,
                        time: {
                            Days: {show: false}
                        }
                    });
                </script>
            </div>
        </div>
    </body>
</html>
