<%@page import="Controladores_BD.PersonalJpaController"%>
<%@page import="Metodos.ConnectionSignature"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/tlds/Alertas.tld" prefix="Alertas"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>SIRH | Iniciar Sesion</title>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Firmas.jsp');
            window.addEventListener('popstate', function (event) {
                history.pushState(null, null, 'Firmas.jsp');
            });
        </script>
        <link rel="icon" href="Interfaz/MasterPage/images/Logo_carne.png" >
        <link type="text/css" href="Interfaz/Firma/assets/jquery.signaturepad.css" rel="stylesheet">
        <jsp:include page='Contenedor_head.jsp'></jsp:include>
            <style>
                .divSt{
                    border: 1px;
                    width: 58%;
                    background: white;
                    border-radius: 5px;
                    position: absolute;
                    top: 17%;
                    left: 20%;
                    -webkit-box-shadow: 3px 3px 4px 0px rgba(158,158,158,1);
                    -moz-box-shadow: 3px 3px 4px 0px rgba(158,158,158,1);
                    box-shadow: 2px 2px 4px 0px rgba(110,110,110,1);
                }
                .DivCamp{
                    display: flex;
                    justify-content: space-evenly;
                    margin-top: 10px;
                    width: 100%;
                }
                .InputCl{
                    background-color:#f5f5f6 !important;
                    border-right: none !important;
                    border-left: none !important;
                    border-top: none !important;
                    width: 100% !important;
                    font-size: 20px !important;
                    border-radius: 5px !important;
                    height: 61% !important;
                }
                .ButtonCl{
                    margin-top: 6px;width: 194px; 
                }
                .LogoCl{
                    width: 59%;
                    margin: 23px;
                }
                .TituloCl{
                    font-size: 21px;
                    float: left;
                    margin-bottom: 7px;
                }
            </style>
        </head>
        <body>
            <div class="pielF">
            <Alertas:Alertas />
            <center>
                <div class="divSt">
                    <div><img Class="LogoCl" src="Interfaz/Imagen/Logo_Firma_SIRH.fw.png"></div>
                    <form action="Sesion?opc=6" method="post">
                        <div class="DivCamp"> 
                            <div>
                                <div class="TituloCl"><b>Documento:</b></div>
                                <div><input type="number" name="Txt_documento" id="Txt_documento" min="1" placeholder="Ingrese documento aqui" class="InputCl" required autocomplete="off"  /></div>
                            </div>
                            <div>
                                <div class="TituloCl"><b>Código:</b></div>
                                <div><input type="number" name="Txt_codigo" id="Txt_codigo" placeholder="Ingrese Código aqui" class="InputCl" required autocomplete="off"/></div>
                            </div>
                        </div>
                        <input  class="ButtonCl" type="submit" value="Consultar" />
                    </form>
                </div>

                <%
                    int documento = 0, codigo = 0;
                    try {
                        documento = Integer.parseInt(pageContext.getRequest().getAttribute("Documento").toString());
                    } catch (Exception e) {
                        documento = 0;
                    }
                    try {
                        codigo = Integer.parseInt(pageContext.getRequest().getAttribute("Codigo").toString());
                    } catch (Exception e) {
                        codigo = 0;
                    }
                    if (documento > 0) {
                        ConnectionSignature firmasJpa = new ConnectionSignature();
                        PersonalJpaController jpacpsn = new PersonalJpaController();
                        List lst_firma = null;
                        List lst_personal = null;
                        lst_firma = firmasJpa.TraerFirmas(documento, codigo);
                        String firma = "";
                        if (lst_firma != null && lst_firma.size() > 0) {
                            String[] obj_firma = lst_firma.toString().replace("[", "").replace("]", "").split("---");
                            try {
                                if (obj_firma[3] != null) {
                                    firma = ".regenerate([" + obj_firma[3] + "]);";
                                } else {
                                    firma = "0";
                                }
                            } catch (Exception e) {
                                firma = "0";
                            }
                        } else {
                            firma = "0";
                        }
                        lst_personal = jpacpsn.Consultar_empleado_documento(String.valueOf(documento));
                        if (lst_personal != null) {
                            out.print("<div>");
                            out.print("<div class='sweet-local' id='Ventana1' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;margin-left:10px'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 14%;left:22%;'>");
                            out.print("<div style='float: right;'><span onclick='mostrarConvencion(1)' class='fa fa-times fa-size_small'></span></div>");
                            if (firma.equals("0")) {
                                out.print("<h1><b>Registrar firma electrónica</b></h1>");
                            } else {
                                out.print("<h1><b>Firma electrónica registrada</b></h1>");
                            }
                            Object[] obj_personal = (Object[]) lst_personal.get(0);
                            out.print("<form action='Sesion?opc=7' method='post'>");
                            out.print("<input type='hidden' name='dcm' value='" + documento + "'>");
                            out.print("<input type='hidden' name='cdg' value='" + obj_personal[5] + "'>");
                            out.print("<table class='table' style='width:50%'>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:30%' valign='bottom'>");
                            out.print("<img id='Img_foto' src='Fotos/" + documento + ".jpg' alt='" + documento + "' style='width:253px;heigth:300px' />");
                            out.print("</td>");
                            out.print("<td>");
                            out.print("<div class='sigPad' id='smoothed' style='width:100%;'>");
                            out.print("<ul class='sigNav' style='display: block;'>");
                            if (firma.equals("0")) {
                                out.print("<li class='clearButton' style='display: list-item;'><a href='#clear'><span class='fa fa-eraser fa-size_super_small'></span></a></li>");
                            }
                            out.print("</ul>");
                            out.print("<div class='sig sigWrapper current' style='height: auto; display: block;'>");
                            out.print("<div class='codigo' style='display: block;" + ((!obj_personal[10].toString().equals("GC")) ? "color:#596275" : "color:#2b5797") + "'>" + obj_personal[5] + "</div>");
                            out.print("<canvas class='pad' width='440px' height='250px'></canvas>");
                            out.print("<input type='hidden' name='Txt_firma' class='output' value='' required>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<script>");
                            out.print("$(document).ready(function () {");
                            out.print("$('#smoothed').signaturePad({");
                            out.print("drawOnly: true,");
                            out.print("drawBezierCurves:true,");
                            if (firma.equals("0")) {
                            } else {
                                out.print("displayOnly:true,");
                            }
                            out.print("lineTop: 200,");
                            out.print("bgColour : 'transparent',");
                            out.print("penColour : '" + ((!obj_personal[10].toString().equals("GC")) ? "#596275" : "#2b5797") + "'");
                            out.print("}");
                            out.print(")" + (!(firma.equals("0")) ? firma : ""));
                            out.print("});");
                            out.print("</script>");

                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td valign='top'  " + (firma.equals("0") ? "" : "colspan='2' style='text-align:center;' ") + ">"
                                    + "<b>Documento : </b>" + obj_personal[0] + "<br />"
                                    + "<b>Nombre : </b>" + obj_personal[1] + "<br />"
                                    + "<b>Apellidos : </b>" + obj_personal[2] + "<br />"
                                    + "<b>Genero : </b>" + (obj_personal[3].equals("M") ? "MASCULINO" : "FEMENINO") + "<br />"
                                    + "</td>");
                            String[] prueba = {};
                            if (firma.equals("0")) {
                                out.print("<td valign='top'>"
                                        + "<input type='hidden' name='Rdb_tipo_firma' value='0'/>");
                                out.print("<input type='submit' value='Registrar Firma' />");
                                out.print("</td>");
                            }
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class=\"clear\"></div>");
                        } else {
                            out.print("<script language='javascript' type='text/javascript'>");
                            out.print("swal({");
                            out.print("  title: 'Empleado no existente',");
                            out.print("  text: 'El documento y código no existe dentro en la APP',");
                            out.print("  icon: 'info',");
                            out.print("  timer: 5000,"); // Tiempo en milisegundos (10 segundos)
                            out.print("  showConfirmButton: false,"); // Oculta el botón de confirmación
                            out.print("});");
                            out.print("</script>");
                        }
                    }

                %>
            </center>
        </div>
        <script type="text/javascript" language="javascript">
            function mostrarConvencion(id) {
                if (document.getElementById("Ventana" + id).style.display === "none") {
                    document.getElementById("Ventana" + id).style.display = "block";
                } else if (document.getElementById("Ventana" + id).style.display === "block") {
                    document.getElementById("Ventana" + id).style.display = "none";
                }
            }
            function CambiarColorDiv() {
                var ColorId1 = document.getElementById("option-1");
                var ColorId2 = document.getElementById("option-2");
                var divColor1 = document.getElementById("divColor1");
                var divColor2 = document.getElementById("divColor2");
                if (ColorId1.checked) {
                    divColor1.style.color = '#f5f5f6';
                    divColor2.style.color = '';
                    document.getElementById("DivId1").style.display = 'block';
                    document.getElementById("DivId2").style.display = 'none';
                } else if (ColorId2.checked) {
                    divColor2.style.color = '#f5f5f6';
                    divColor1.style.color = '';
                    document.getElementById("DivId2").style.display = 'block';
                    document.getElementById("DivId1").style.display = 'none';
                }
            }
        </script>
        <script src="Interfaz/Firma/assets/numeric-1.2.6.min.js"></script>
        <script src="Interfaz/Firma/assets/bezier.js"></script>
        <script src="Interfaz/Firma/jquery.signaturepad.js"></script>
        <script src="Interfaz/Firma/assets/json2.min.js"></script>
    </body>
</html>
