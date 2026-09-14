<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="/WEB-INF/Tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/Tlds/Formula.tld" prefix="Formula"%>
<%@taglib uri="/WEB-INF/Tlds/Alertas.tld" prefix="Alertas"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link type="image/png" href="Interfaz/Contenido/images/Control_formulas_new.ico" rel="icon" >
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
            <!--Controlar url-->
            <script type = "text/javascript" >
                history.pushState(null, null, 'Formula.jsp');
                window.addEventListener('popstate', function (event) {
                    history.pushState(null, null, 'Formula.jsp');
                });
            </script>
            <title>Materia prima</title>
            <jsp:include page='Contenedor_head.jsp'></jsp:include>
                <!--MOSTRAR/OCULTAR FORMULARIO-->
                <script type="text/javascript">
                    function mtform(valor) {
                        var contenido = "<b>Ficha Técnica :</b><br> \n\
                            <input type='text' name='c_ficha_tec' id='c_ficha_tec'  placeholder='Ficha técnica' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/ >\n\
                            <b>Versión :</b><br>\n\
                            <input type='number' min='0' name='c_version' id='c_version'  placeholder='Versión' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>\n\
                            <b>Dureza :</b>\n\
                            <input type='text' name='c_dureza' id='c_dureza'  placeholder='Dureza' title='Dureza Aceptable'  \n\
                            onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>\n\
                            <b>Dureza Max:</b>\n\
                            <input type='text' name='c_dureza_max' id='c_dureza_max'  placeholder='Dureza Max' title='Dureza Max' \n\
                            onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>\n\
                            <b>Dureza Min:</b>\n\
                            <input type='text' name='c_dureza_min' id='c_dureza_min'  placeholder='Dureza Min' title='Dureza Min' \n\
                            onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>";
                        if (valor == 1) {
                            document.getElementById("form_registro").innerHTML = contenido;
                            document.getElementById("form_registro").style.display = "block";
                        } else {
                            document.getElementById("form_registro").innerHTML = " ";
                            document.getElementById("form_registro").style.display = "none";
                        }
                    }
                </script>
                <!--CAMBIAR DE CAMPO TECLA ENTER-->
                <script type="text/javascript">
                    function cambiarCampo(e, id) {
                        (e.keyCode) ? k = e.keyCode : k = e.which;
                        // Si la tecla pulsada es enter (codigo ascii 13)
                        if (k == 13)
                        {
                            // Si la variable id contiene "submit" enviamos el formulario
                            if (id == "submit")
                            {
                                document.forms[0].submit();
                            } else {
                                // nos posicionamos en el siguiente input
                                document.getElementById(id).focus();
                            }
                        }
                    }
                </script>
                <!----PROMEDIO DUREZAS---->
                <script type="text/javascript">
                    function cal(id) {
                        var max = parseFloat(document.getElementById("Txt_parametro_max").value);
                        var min = parseFloat(document.getElementById("Txt_parametro_min").value);
                        var audio = new Audio();
                        audio.src = "Interfaz/Contenido/alert.mp3";
                        try {
                            var a = parseFloat(document.getElementById("Txt_lectura" + id).value) || 0;
                            if (a >= min && a <= max) {
                                document.getElementById("Txt_lectura" + id).style.boxShadow = "none";
                                document.getElementById("Txt_lectura" + id).style.border = "none";
                                document.getElementById("Txt_lectura" + id).style.boxShadow = "0 0 5px #00CC00;";
                                document.getElementById("Txt_lectura" + id).style.border = "1px solid #00CC00";
                            } else {
                                audio.play();
                                document.getElementById("Txt_lectura" + id).style.boxShadow  = "none";
                                document.getElementById("Txt_lectura" + id).style.border= "none";
                                document.getElementById("Txt_lectura" + id).style.boxShadow  = "0 0 5px red";
                                document.getElementById("Txt_lectura" + id).style.border= "1px solid red";
                            }
                            var lect1 = parseFloat(document.getElementById("Txt_lectura1").value) || 0;
                            var lect2 = parseFloat(document.getElementById("Txt_lectura2").value) || 0;
                            var lect3 = parseFloat(document.getElementById("Txt_lectura3").value) || 0;
                            var lect4 = parseFloat(document.getElementById("Txt_lectura4").value) || 0;
                            var result = parseFloat((lect1 + lect2 + lect3 + lect4) / 4);
                            if (lect1 >= min && lect1 <= max && lect2 >= min && lect2 <= max && lect3 >= min && lect3 <= max && lect4 >= min && lect4 <= max) {
                                if (result >= min && result <= max) {
                                    document.getElementById("label_concepto").innerHTML = "Cumple";
                                    document.getElementById("label_concepto").style.color = "green";
                                    document.getElementById("Txt_concepto").value = 1;
                               
                                } else {
                                    document.getElementById("label_concepto").innerHTML = "No Cumple";
                                    document.getElementById("label_concepto").style.color = "red";
                                    document.getElementById("Txt_concepto").value = 0;
                                }
                            } else {
                                document.getElementById("label_concepto").innerHTML = "No Cumple";
                                document.getElementById("label_concepto").style.color = "red";
                                document.getElementById("Txt_concepto").value = 0;
                            }
                            document.getElementById("promedio").value = result;
                        } catch (e) {
                        }
                    }
                </script>
                
                <script type="text/javascript">
                    function DesactivarFormula(id_formula) {
                        swal({
                            title: "Finalizar!",
                            text: "Seguro que desea cambiar de Estado?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Formula?opc=3&Id_formula=' + id_formula + '&Estado=0';
                                });
                    }
                    function ActivarFormula(id_formula) {
                        swal({
                            title: "Finalizar!",
                            text: "Seguro que desea cambiar de Estado?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#006666",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Formula?opc=3&Id_formula=' + id_formula + '&Estado=1';
                                });
                    }
                    function DesactivarFormulaMP(id_formula_materia, id_formula) {
                        swal({
                            title: "Finalizar!",
                            text: "Seguro que desea cambiar de Estado?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#006666",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Formula?opc=14&Id_formula_materia=' + id_formula_materia + '&Id_formula=' + id_formula + '&Estado=0';
                                });
                    }
                    function ActivarFormulaMP(id_formula_materia, id_formula) {
                        swal({
                            title: "Finalizar!",
                            text: "Seguro que desea cambiar de Estado?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#006666",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Formula?opc=14&Id_formula_materia=' + id_formula_materia + '&Id_formula=' + id_formula + '&Estado=1';
                                });
                    }
                    function GuardarRegistro(id_registro, id_formula) {
                        swal({
                            title: "Finalizar!",
                            text: "Seguro que desea guardar el registro?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#006666",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Formula?opc=12&Id_registro=' + id_registro + '&Id_formula=' + id_formula;
                                });
                    }
                    function QuitarMPRegistros(id_registro_detalle, id_formula, id_registro) {
                        swal({
                            title: "Finalizar!",
                            text: "Seguro que desea quitar la MP del registro?",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#006666",
                            confirmButtonText: "Aceptar",
                            cancelButtonText: "Cancelar",
                            closeOnConfirm: false
                        },
                                function () {
                                    location.href = 'Formula?opc=17&Id_registro_detalle=' + id_registro_detalle + '&Id_formula=' + id_formula + '&Id_registro=' + id_registro + '';
                                });
                    }
                </script>
                <script type="text/javascript">
                    function PostBackFormula() {
                        var Tipo_parametro = document.getElementById("Cbx_tipo_parametro");
                        document.forms['FormFormula'].submit();
                    }
                </script>
                <!--Calendarios-->
                <link rel="stylesheet" type="text/css" href="Interfaz/Calendarios/pikaday.css">
                    <script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
                    <script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
                    <!--Arbol-->
                    <link rel="stylesheet" href="Interfaz/Arbol/jquery.treeview.css" />
                    <script src="Interfaz/Arbol/jquery.js" type="text/javascript"></script>
                    <script src="Interfaz/Arbol/jquery.cookie.js" type="text/javascript"></script>
                    <script src="Interfaz/Arbol/jquery.treeview.js" type="text/javascript"></script>
                    <script type="text/javascript">
                    $(document).ready(function () {
                        $("#browser").treeview({
                            toggle: function () {
                                console.log("%s was toggled.", $(this).find(">span").text());
                            }
                        });
                    });
                    </script>
                    </head>
                    <body id="subpage">
                        <div id="templatemo_wrapper">
                        <Menu:Menu />
                        <Formula:Formula />
                        <script src="Interfaz/Calendarios/Js_normal.js" type="text/javascript"></script>
                    </div>
                    <Alertas:Alertas />
                    <script src="Interfaz/Acordeon/Js_accordeon.js" type="text/javascript"></script>
                </body>
                </html>