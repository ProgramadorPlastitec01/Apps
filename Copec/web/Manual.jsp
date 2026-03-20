<%@taglib uri="/WEB-INF/tlds/Menu.tld" prefix="Menu"%>
<%@taglib uri="/WEB-INF/tlds/Manual.tld" prefix="Manual"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Documento</title>
        <jsp:include page="Encabezado.jsp"></jsp:include>
        <script type = "text/javascript" >
            history.pushState(null, null, 'Manual.jsp');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, 'Manual.jsp');
            });
        </script>
        <script type="text/javascript">
            function calc(vari)
            {
                var seleccion_a= document.getElementById("tipo_doc_a").checked;
                var seleccion_c = document.getElementById("tipo_doc_c").checked;
                if (seleccion_a == true) {
                    location.href='Manual?opc=2&tipo_doc=' + vari + '';
                }
                if (seleccion_c == true) {
                    location.href='Manual?opc=2&tipo_doc=' + vari + '';
                }
            }
        </script>
        <script type="text/javascript">
            function calc_a(vari, id_manual, tipo_doc, siglatura)
            {
                var seleccion = document.getElementById("ckb_doc" + vari).checked;
                if (seleccion == true) {
                    location.href='Manual?opc=7&id_manual=' + id_manual + '&tipo_doc=' + tipo_doc + '&siglatura=' + siglatura + '';
                }
            }
        </script>
        <script type="text/javascript">
            function calc_c(vari, id_manual, tipo_doc, id_cargo, siglatura,id_cargo_manual,estado)
            {
                if (id_cargo_manual > 0) {
                    location.href='Manual?opc=8&id_manual=' + id_manual + '&tipo_doc=' + tipo_doc + '&id_cargo=' + id_cargo + '&siglatura=' + siglatura + '&id_cargo_manual='+id_cargo_manual+'&estado='+estado;
                }else{
                    var seleccion = document.getElementById("ckb_doc" + vari).checked;
                    if (seleccion == true) {
                        location.href='Manual?opc=8&id_manual=' + id_manual + '&tipo_doc=' + tipo_doc + '&id_cargo=' + id_cargo + '&siglatura=' + siglatura + '&id_cargo_manual='+id_cargo_manual+'&estado='+estado;
                    }
                }
            }
        </script>

        <script type="text/javascript">
            function checarcombo(){
                if(formTipo.cbx_doc.value=="GC"){
                    formTipo.rfc.disabled=false;
                    formTipo.rfc.value=("001");
                } else {
                    formTipo.rfc.disabled=false;
                    var input = document.getElementById ("rfc");
                    input.value = input.defaultValue;
                }
            }
        </script>
    </head>
    <body>
        <div id="subpage">
            <div id="templatemo_wrapper">
                <Menu:Menu/>
                <Manual:Manual/>
            </div>
        </div>
            <script src="Interfaz/paginas/filtro.js"></script>
    </body>
</html>
