<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_menu.tld" prefix="menu" %>
<%@taglib uri="/WEB-INF/tlds/tld_detalle_equipo.tld" prefix="detalle" %>
<%@taglib uri="/WEB-INF/tlds/tld_resultado.tld" prefix="resultado" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Detalle Equipo</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
            <!--HTML editor-->
            <script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
            <script type="text/javascript" language="javascript">
                function FiltroAvanzado(e) {
                    tecla = (document.all) ? e.keyCode : e.which;
                    if (tecla === 43) {
                        var filtro = document.getElementById('Txt_filtro_avanzado').value.replace("+", "");
                        if (filtro !== "") {
                            document.getElementById('Txt_valores_filtro').value += "[" + filtro + "]";
                            document.getElementById('Buscar_valores').innerHTML += "<a href='#' onclick=\"FiltroAvanzadoQuitar(\'" + filtro + "\')\" style='text-decoration:none'>" + filtro + "</a><br />";
                        }
                        document.getElementById('Txt_filtro_avanzado').value = "";
                    }
                }
                function FiltroAvanzadoQuitar(e) {
                    var valor = document.getElementById('Txt_valores_filtro').value;
                    document.getElementById('Txt_valores_filtro').value = valor.replace("[" + e + "]", "");
                    var vista = document.getElementById('Buscar_valores').innerHTML;
                    var elim = "<a href=\"#\" onclick=\"FiltroAvanzadoQuitar(\'" + e + "\')\" style=\"text-decoration:none\">" + e + "</a><br>";
                    document.getElementById('Buscar_valores').innerHTML = "";
                    document.getElementById('Buscar_valores').innerHTML = vista.replace("" + elim + "", "");
                }
                function SeleccionCampos(cmp) {
                    if (cmp.checked) {
                        document.getElementById('Txt_filtro_campos').value += "" + cmp.value;
                    } else {
                        document.getElementById("Txt_filtro_campos").value = document.getElementById("Txt_filtro_campos").value.replace(cmp.value, "");
                    }
                }
                function MostrarCampo(valor) {
                    document.getElementById("modal").style.display = "block";
                    if (valor == 1) {
                        if (document.getElementById("Txt_nombre_equipo").style.display === "none") {
                            document.getElementById("Txt_nombre_equipo").style.display = "block";
                        } else if (document.getElementById("Txt_nombre_equipo").style.display === "block") {
                            document.getElementById("Txt_nombre_equipo").style.display = "none";
                        }
                    } else if (valor == 2) {
                        if (document.getElementById("Txt_tipo_equipo").style.display === "none") {
                            document.getElementById("Txt_tipo_equipo").style.display = "block";
                        } else if (document.getElementById("Txt_tipo_equipo").style.display === "block") {
                            document.getElementById("Txt_tipo_equipo").style.display = "none";
                        }
                    } else if (valor == 3) {
                        if (document.getElementById("Txt_login_plastitec").style.display === "none") {
                            document.getElementById("Txt_login_plastitec").style.display = "block";
                        } else if (document.getElementById("Txt_login_plastitec").style.display === "block") {
                            document.getElementById("Txt_login_plastitec").style.display = "none";
                        }
                    } else if (valor == 4) {
                        if (document.getElementById("Txt_ip").style.display === "none") {
                            document.getElementById("Txt_ip").style.display = "block";
                        } else if (document.getElementById("Txt_ip").style.display === "block") {
                            document.getElementById("Txt_ip").style.display = "none";
                        }
                    } else if (valor == 5) {
                        if (document.getElementById("Txt_mac").style.display === "none") {
                            document.getElementById("Txt_mac").style.display = "block";
                        } else if (document.getElementById("Txt_mac").style.display === "block") {
                            document.getElementById("Txt_mac").style.display = "none";
                        }
                    } else if (valor == 6) {
                        if (document.getElementById("Txt_garantia").style.display === "none") {
                            document.getElementById("Txt_garantia").style.display = "block";
                        } else if (document.getElementById("Txt_garantia").style.display === "block") {
                            document.getElementById("Txt_garantia").style.display = "none";
                        }
                    } else if (valor == 7) {
                        if (document.getElementById("Txt_antivirus").style.display === "none") {
                            document.getElementById("Txt_antivirus").style.display = "block";
                        } else if (document.getElementById("Txt_antivirus").style.display === "block") {
                            document.getElementById("Txt_antivirus").style.display = "none";
                        }
                    } else if (valor == 8) {
                        if (document.getElementById("Txt_internet").style.display === "none") {
                            document.getElementById("Txt_internet").style.display = "block";
                        } else if (document.getElementById("Txt_internet").style.display === "block") {
                            document.getElementById("Txt_internet").style.display = "none";
                        }
                    } else if (valor == 9) {
                        if (document.getElementById("Txt_win_instalado").style.display === "none") {
                            document.getElementById("Txt_win_instalado").style.display = "block";
                        } else if (document.getElementById("Txt_win_instalado").style.display === "block") {
                            document.getElementById("Txt_win_instalado").style.display = "none";
                        }
                    } else if (valor == 10) {
                        if (document.getElementById("Txt_office_instalado").style.display === "none") {
                            document.getElementById("Txt_office_instalado").style.display = "block";
                        } else if (document.getElementById("Txt_office_instalado").style.display === "block") {
                            document.getElementById("Txt_office_instalado").style.display = "none";
                        }
                    } else if (valor == 11) {
                        if (document.getElementById("Txt_vlan").style.display === "none") {
                            document.getElementById("Txt_vlan").style.display = "block";
                        } else if (document.getElementById("Txt_vlan").style.display === "block") {
                            document.getElementById("Txt_vlan").style.display = "none";
                        }
                    } else if (valor == 12) {
                        if (document.getElementById("Txt_vpn").style.display === "none") {
                            document.getElementById("Txt_vpn").style.display = "block";
                        } else if (document.getElementById("Txt_vpn").style.display === "block") {
                            document.getElementById("Txt_vpn").style.display = "none";
                        }
                    } else if (valor == 13) {
                        if (document.getElementById("Txt_skye").style.display === "none") {
                            document.getElementById("Txt_skye").style.display = "block";
                        } else if (document.getElementById("Txt_skye").style.display === "block") {
                            document.getElementById("Txt_skye").style.display = "none";
                        }
                    } else if (valor == 14) {
                        if (document.getElementById("Txt_gmail").style.display === "none") {
                            document.getElementById("Txt_gmail").style.display = "block";
                        } else if (document.getElementById("Txt_gmail").style.display === "block") {
                            document.getElementById("Txt_gmail").style.display = "none";
                        }
                    } else if (valor == 15) {
                        if (document.getElementById("Txt_correo_interno").style.display === "none") {
                            document.getElementById("Txt_correo_interno").style.display = "block";
                        } else if (document.getElementById("Txt_correo_interno").style.display === "block") {
                            document.getElementById("Txt_correo_interno").style.display = "none";
                        }
                    } else if (valor == 16) {
                        if (document.getElementById("Txt_correo_externo").style.display === "none") {
                            document.getElementById("Txt_correo_externo").style.display = "block";
                        } else if (document.getElementById("Txt_correo_externo").style.display === "block") {
                            document.getElementById("Txt_correo_externo").style.display = "none";
                        }
                    } else if (valor == 17) {
                        if (document.getElementById("Txt_factura").style.display === "none") {
                            document.getElementById("Txt_factura").style.display = "block";
                        } else if (document.getElementById("Txt_factura").style.display === "block") {
                            document.getElementById("Txt_factura").style.display = "none";
                        }
                    } else if (valor == 18) {
                        if (document.getElementById("Txt_fecha_factura").style.display === "none") {
                            document.getElementById("Txt_fecha_factura").style.display = "block";
                        } else if (document.getElementById("Txt_fecha_factura").style.display === "block") {
                            document.getElementById("Txt_fecha_factura").style.display = "none";
                        }
                    } else if (valor == 19) {
                        if (document.getElementById("Txt_lincecia").style.display === "none") {
                            document.getElementById("Txt_lincecia").style.display = "block";
                        } else if (document.getElementById("Txt_lincecia").style.display === "block") {
                            document.getElementById("Txt_lincecia").style.display = "none";
                        }
                    } else if (valor == 20) {
                        if (document.getElementById("Txt_fecha_garantia").style.display === "none") {
                            document.getElementById("Txt_fecha_garantia").style.display = "block";
                        } else if (document.getElementById("Txt_fecha_garantia").style.display === "block") {
                            document.getElementById("Txt_fecha_garantia").style.display = "none";
                        }
                    } else if (valor == 21) {
                        if (document.getElementById("Txt_proveedor").style.display === "none") {
                            document.getElementById("Txt_proveedor").style.display = "block";
                        } else if (document.getElementById("Txt_proveedor").style.display === "block") {
                            document.getElementById("Txt_proveedor").style.display = "none";
                        }
                    } else if (valor == 22) {
                        if (document.getElementById("Txt_activos_soporte").style.display === "none") {
                            document.getElementById("Txt_activos_soporte").style.display = "block";
                        } else if (document.getElementById("Txt_activos_soporte").style.display === "block") {
                            document.getElementById("Txt_activos_soporte").style.display = "none";
                        }
                    } else if (valor == 23) {
                        if (document.getElementById("Txt_tipo_sofware").style.display === "none") {
                            document.getElementById("Txt_tipo_sofware").style.display = "block";
                        } else if (document.getElementById("Txt_tipo_sofware").style.display === "block") {
                            document.getElementById("Txt_tipo_sofware").style.display = "none";
                        }
                    } else if (valor == 24) {
                        if (document.getElementById("Txt_red").style.display === "none") {
                            document.getElementById("Txt_red").style.display = "block";
                        } else if (document.getElementById("Txt_red").style.display === "block") {
                            document.getElementById("Txt_red").style.display = "none";
                        }
                    }
                }
            </script>
        </head>
        <body>
        <menu:MuestraMenu/>
        <div id="content">
            <detalle:MuestraDetalleE/>
            <script>
                $('select').selectpicker({
                    width: '188px',
                    padding: '0px 4px 0px 10px;'
                });
            </script>
        </div>
        <resultado:MuestraResultado/>
        <script src="Interfaz/Contenido/Scripts/jquery-1.11.3.min.js"></script>
        <script src="Interfaz/Calendarios/Js_normal.js"></script>
        <script src="Interfaz/Calendarios/Js_range.js"></script>
    </body>
</html>
