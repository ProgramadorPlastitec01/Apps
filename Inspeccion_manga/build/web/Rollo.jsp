<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Rollo.tld" prefix="Rollo"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Inspeccion_manga_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <title>Rollo</title>
<!--            <script type = "text/javascript" >
                history.pushState(null, null, 'Rollo.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Rollo.jsp');
                });
            </script>-->
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <script type="text/javascript">
                    function Peso(peso, restar) {
                        if (peso.value.indexOf('¨') !== -1) {
                            var pso = peso.value + "¨";
                            var arg_peso = pso.split("¨")[4];
                            arg_peso = arg_peso.replace(" ", "");
                            arg_peso = arg_peso.replace("kg", "");
                            arg_peso = arg_peso.replace("KG", "");
                        } else {
                            arg_peso = peso.value;
                        }
                        peso.value = arg_peso;
                        var original = parseFloat(arg_peso);
                        var original_rest = parseFloat(restar);
                        arg_peso = Math.round(original * 100) / 100;
                        restar = Math.round(original_rest * 100) / 100;
                        var result = (arg_peso - restar);
                        result = parseFloat(result);
                        result = Math.round(result * 100) / 100;
                        document.getElementById("Txt_peso_neto").value = result;
                    }
                </script>
                <script type="text/javascript">
                    function Peso_pp(peso, restar) {
                        var menor = document.getElementById("Txt_peso_min").value;
                        var mayor = document.getElementById("Txt_peso_max").value;
                        if (peso.value.indexOf('¨') !== -1) {
                            var pso = peso.value + "¨";
                            var arg_peso = pso.split("¨")[4];
                            arg_peso = arg_peso.replace(" ", "");
                            arg_peso = arg_peso.replace("kg", "");
                            arg_peso = arg_peso.replace("KG", "");
                        } else {
                            arg_peso = peso.value;
                        }
                        peso.value = arg_peso;
                        var original = parseFloat(arg_peso);
                        var original_rest = parseFloat(restar);
                        arg_peso = Math.round(original * 100) / 100;
                        restar = Math.round(original_rest * 100) / 100;
                        var result = (arg_peso - restar);
                        result = parseFloat(result);
                        result = Math.round(result * 100) / 100;
                        if (result >= menor && result <= mayor) {
                        } else {
                            document.getElementById("Txt_peso_neto").style.border = "none";
                            document.getElementById("Txt_peso_neto").style.border = "1px solid red";
                            document.getElementById("Txt_peso_neto").style.boxShadow = "0 0 5px red";
                        }
                        document.getElementById("Txt_peso_neto").value = result;
                    }
                </script>
                <script type="text/javascript">
                    function Validacion(idobjeticoevaluar, idmenor, idmayor, sig) {
                        var objetico = parseFloat(document.getElementById(idobjeticoevaluar).value);
                        var menor = parseFloat(document.getElementById(idmenor).value);
                        var mayor = parseFloat(document.getElementById(idmayor).value);
                        var siguiente = document.getElementById(sig);
                        var audio = new Audio();
                        audio.src = "Alerta.mp3";
                        if (objetico <= mayor && objetico >= menor) {
                            document.getElementById(idobjeticoevaluar).style.backgroundColor = "#fff";
                            document.getElementById(idobjeticoevaluar).style.color = "green";
                            document.getElementById(idobjeticoevaluar).value = objetico.toString().trim();
                            document.getElementById(idobjeticoevaluar).style.borderColor = "green";
                            document.getElementById(idobjeticoevaluar).style.boxShadow = "none";
                            document.getElementById(idobjeticoevaluar).style.boxShadow = "0 0 3px #00CC00";
                            siguiente.focus();
                        } else {
                            var redondeo = objetico.toFixed(2);
                            if (redondeo <= mayor && redondeo >= menor) {
                                document.getElementById(idobjeticoevaluar).style.backgroundColor = "#fff";
                                document.getElementById(idobjeticoevaluar).style.color = "red";
                                document.getElementById(idobjeticoevaluar).value = objetico.toString().trim();
                                document.getElementById(idobjeticoevaluar).style.borderColor = "ref";
                                document.getElementById(idobjeticoevaluar).style.boxShadow = "none";
                                document.getElementById(idobjeticoevaluar).style.boxShadow = "0 0 5px red";
                                siguiente.focus();
                            } else {
                                var estado = false;
                                estado = !estado;
                                audio.play();
                                document.getElementById(idobjeticoevaluar).style.backgroundColor = "#fff";
                                document.getElementById(idobjeticoevaluar).style.color = "red";
                                document.getElementById(idobjeticoevaluar).value = objetico.toString().trim();
                                document.getElementById(idobjeticoevaluar).style.borderColor = "red";
                                document.getElementById(idobjeticoevaluar).style.boxShadow = "none";
                                document.getElementById(idobjeticoevaluar).style.boxShadow = "0 0 5px red";
                                siguiente.focus();
                            }
                        }
                    }
                </script>
                <!--Rollo refilado-->
                <script type="text/javascript">
                    function Rollo_estria_ventana(dato, min, max, next) {
                        var valor = parseFloat(document.getElementById(dato).value);
                        var menor = parseFloat(document.getElementById(min).value);
                        var mayor = parseFloat(document.getElementById(max).value);
                        var nextinput = document.getElementById(next);
                        var audio = new Audio();
                        var cuarentena = 0;
                        var detalle_cuarentena = "";
                        var detalle_aprobado = "";
                        audio.src = "Alerta.mp3";
                        if (valor <= mayor && valor >= menor) {
                            document.getElementById(dato).style.backgroundColor = "#fff";
                            document.getElementById(dato).style.color = "green";
                            document.getElementById(dato).style.borderColor = "green";
                            document.getElementById(dato).style.boxShadow = "none";
                            document.getElementById(dato).style.boxShadow = "0 0 3px #00CC00";
                            document.getElementById(dato).value = valor.toString().trim();
                            var y = document.getElementById(dato).title;
                            detalle_aprobado = "* Por " + y + "<br />";
                        } else {
                            document.getElementById(dato).style.backgroundColor = "#fff";
                            document.getElementById(dato).style.color = "red";
                            document.getElementById(dato).style.borderColor = "red";
                            document.getElementById(dato).style.boxShadow = "none";
                            document.getElementById(dato).style.boxShadow = "0 0 5px red";
                            document.getElementById(dato).value = valor.toString().trim();
                            cuarentena++;
                            var x = document.getElementById(dato).title;
                            detalle_cuarentena = "* Por " + x + "<br />";
                        }
                        document.getElementById("Detalle_defecto").value = document.getElementById("Detalle_defecto").value.replace(detalle_aprobado, "").replace(detalle_cuarentena, "");
                        document.getElementById("Detalle_defecto").value += detalle_cuarentena;
                        if (document.getElementById("Detalle_defecto").value.length > 1) {
                            document.getElementById("Estado_calidad").value = "C";
                        } else {
                            document.getElementById("Estado_calidad").value = "A";
                        }
                        nextinput.focus();
                    }

                    function Rollo_mod_estria_ventana(dato, min, max, next) {
                        document.getElementById("Btn_modificar_rollo").style.display = 'none';
                        document.getElementById("Control_envio").value = "";
                        //document.getElementById("Form_registro_edicion_rollos").action = "#";
                        var valor = parseFloat(document.getElementById(dato).value);
                        var menor = parseFloat(document.getElementById(min).value);
                        var mayor = parseFloat(document.getElementById(max).value);
                        var nextinput = document.getElementById(next);
                        var audio = new Audio();
                        var cuarentena = 0;
                        var detalle_cuarentena = "";
                        var detalle_aprobado = "";
                        audio.src = "Alerta.mp3";
                        if (valor <= mayor && valor >= menor) {
                            document.getElementById(dato).style.backgroundColor = "#fff";
                            document.getElementById(dato).style.color = "green";
                            document.getElementById(dato).style.borderColor = "green";
                            document.getElementById(dato).style.boxShadow = "none";
                            document.getElementById(dato).style.boxShadow = "0 0 3px #00CC00";
                            document.getElementById(dato).value = valor.toString().trim();
                            var y = document.getElementById(dato).title;
                            detalle_aprobado = "* Por " + y + "<br />";
                        } else {
                            document.getElementById(dato).style.backgroundColor = "#fff";
                            document.getElementById(dato).style.color = "red";
                            document.getElementById(dato).style.borderColor = "red";
                            document.getElementById(dato).style.boxShadow = "none";
                            document.getElementById(dato).style.boxShadow = "0 0 5px red";
                            document.getElementById(dato).value = valor.toString().trim();
                            cuarentena++;
                            var x = document.getElementById(dato).title;
                            detalle_cuarentena = "* Por " + x + "<br />";
                        }
                        document.getElementById("Detalle_defecto").value = document.getElementById("Detalle_defecto").value.replace(detalle_aprobado, "").replace(detalle_cuarentena, "");
                        document.getElementById("Detalle_defecto").value += detalle_cuarentena;
                        if (document.getElementById("Detalle_defecto").value.length > 1) {
                            document.getElementById("Estado_calidad").value = "C";
                        } else {
                            document.getElementById("Estado_calidad").value = "A";
                        }
                        nextinput.focus();
                    }
                    function Pasar_valor(valor, campo) {
                        if (valor.includes(".") === true) {
                            var cant_decimal = (valor.split(".")[1]).length;
                            if (cant_decimal >= 2) {
                                campo.focus();
                                campo.select();
                            }
                        }
                    }
                    function Revalidar_parameros() {
                        var formulario;
                        //var action;
                        var CamposImput;
                        formulario = document.getElementById("Form_modificar_edicion_rollos");
                        CamposImput = formulario.getElementsByTagName("input");
                        for (var i = 0; i < CamposImput.length; i++) {
                            if (CamposImput[i].type == "text") {
                                CamposImput[i].focus();
                            }
                        }
                        document.getElementById("Btn_modificar_rollo").style.display = 'block';
                        document.getElementById("Control_envio").value = "0";
                    }
                </script>
                <!--Rollo refilado-->
                <script type="text/javascript">
                    function Rollo_refilado(radio) {
                        if (radio.value == 1) {
                            document.getElementById("Txt_numero_refilado").readOnly = true;
                            document.getElementById("Txt_numero_refilado").value = "0";
                        } else {
                            document.getElementById("Txt_numero_refilado").readOnly = false;
                            document.getElementById("Txt_numero_refilado").value = "";
                        }
                    }
                </script>
        </head>
        <body id="subpage" >
            <div id="templatemo_wrapper">
            <Menu:Menu />
            <Rollo:Rollo/>
        </div>
        <Alertas:Alertas />
    </body>
</html>


