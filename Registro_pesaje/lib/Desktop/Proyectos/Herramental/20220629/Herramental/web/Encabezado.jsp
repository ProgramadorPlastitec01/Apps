<link type="image/png" href="Interfaz/Contenido/images/herramental.ico" rel="icon" >
<link href="Interfaz/Contenido/Css/CSS_Menu.css" rel="stylesheet" type="text/css" />
<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />
<link href="Interfaz/Contenido/Css/CSS_tooltip.css" rel="stylesheet" type="text/css" />

<!--out.print("<script src=\"Interfaz/EditorHtml/htmljquery-3.5.1.min.js\" type=\"text/javascript\"></script>");
out.print("<script src=\"Interfaz/EditorHtml/htmlpopper.min.js\" type=\"text/javascript\"></script>");
out.print("<link href=\"Interfaz/EditorHtml/htmlbootstrap2.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
out.print("<script src=\"Interfaz/EditorHtml/htmlbootstrap.min.js\" type=\"text/javascript\"></script>");
out.print("<link href=\"Interfaz/EditorHtml/htmlsummernote-bs4.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
out.print("<script src=\"Interfaz/EditorHtml/htmlsummernote-bs4.min.js\" type=\"text/javascript\"></script>");-->

<link href="Interfaz/Validacion/StyleSheetLiveValidation.css" rel="stylesheet" type="text/css" />
<script src="https://kit.fontawesome.com/0bb7091f99.js" crossorigin="anonymous"></script>
<!-- Acordeon -->
<link rel="stylesheet" href="Interfaz/Acordeon/Css_accordeon.css">
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu_Min.js"></script>
<!-- JQuery alertas -->
<script type="text/javascript" src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<link href="Interfaz/Alertas/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
<!-- JQuery desplega menu -->
<script type="text/javascript" src="Interfaz/Contenido/Scripts/JS_Menu.js"></script>
<script type="text/javascript" src="Interfaz/Paginas/paging.js"></script>
<script type="text/javascript" src="Interfaz/Validacion/LiveValidation.js"></script>
<!-- Menu flotante -->
<script src="Interfaz/Contenido/Scripts/jquery-1.10.2.js"></script>
<!-- JavaScript calendarios -->
<script type="text/javascript" src="Interfaz/Calendarios/moment.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/pikaday.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/Js_normal.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/Js_range.js"></script>
<script type="text/javascript" src="Interfaz/Calendarios/Js_range_altenativo.js"></script>
<link href="Interfaz/Calendarios/pikaday.css" rel="stylesheet" type="text/css"/>
<!-- JavaScript html editor -->
<style type="text/css" media="screen">@import "Interfaz/Tabs/tabs.css";</style>
<!-- JavaScript desplega menu -->
<script type="text/javascript">
    ddsmoothmenu.init({
        mainmenuid: "templatemo_menu", //menu DIV id
        orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
        classname: 'ddsmoothmenu', //class added to menu's outer DIV
        //customtheme: ["#1c5a80", "#18374a"],
        contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
    })
</script>
<!--<script  type="text/javascript">
    window.history.go(1);
</script> -->
<script type="text/javascript" language="javascript">
    function Imprimir() {
        var contenedor = document.getElementById("ganttPrint").innerHTML;
        var frame = document.createElement("iframe");
        frame.name = "frame1";
        frame.style.position = "absolute";
        frame.style.top = "-1000000px";
        document.body.appendChild(frame);
        var frameDoc = frame.contentWindow ? frame.contentWindow : frame.contentDocument.document ? frame.contentDocument.document : frame.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<html><head><title>Imprimir</title>');
        frameDoc.document.write('<link href="Interfaz/Contenido/Css/CSS_Principal.css" rel="stylesheet" type="text/css" />');
        frameDoc.document.write('<link href="Interfaz/Gantt/css/style.css" type="text/css" rel="stylesheet">');
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

<script type="text/javascript">
    var statsend = false;
    function checkSubmit() {
        if (!statsend) {
            statsend = true;
            return true;
        } else {
            alert(" Un momento por favor el formulario se esta enviando...");
            return false;
        }
    }

    function firmas(vari) {
        vari = document.getElementById("firma_insu").value = vari;
    }
</script>
<script type="text/javascript">
    function checkKeyCode(evt)
    {
        var evt = (evt) ? evt : ((event) ? event : null);
        var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        if (event.keyCode == 116)
        {
            evt.keyCode = 0;
            return false
        }
    }
    document.onkeydown = checkKeyCode;
</script>
<script>
    function time() {
        setTimeout(function () {
            swal("Atencion!", "Se cerrara la session por inactividad", "warning");
        }, 1770000);
    }
    function agregarUM(check, id) {
        id = "[" + id + "]";
        if (check.checked) {
            var campo = document.getElementById("usuario-id").value;
            campo = campo + id;
            document.getElementById("usuario-id").value = campo;
        } else {
            var campo = document.getElementById("usuario-id").value;
            campo = campo.replace(id, "");
            document.getElementById("usuario-id").value = campo;
        }
        document.getElementById("usuario-id").value = campo;
    }
    function agregarUP(check, id) {
        id = "[" + id + "]";
        if (check.checked) {
            var campo = document.getElementById("usuario-idP").value;
            campo = campo + id;
            document.getElementById("usuario-idP").value = campo;
        } else {
            var campo = document.getElementById("usuario-idP").value;
            campo = campo.replace(id, "");
            document.getElementById("usuario-idP").value = campo;
        }
        document.getElementById("usuario-idP").value = campo;
    }
    function idP(id, est) {
        document.getElementById("idP").value = id;
        document.getElementById("estP").value = est;
    }
    function idMV(id) {
        document.getElementById("idMV").value = id;
    }

</script>
<script type="text/javascript">
    function contrasena() {
        document.getElementById("pass-id").value = '1';
        document.formRC.submit();
    }
    function contrasenaM() {
        document.getElementById("passM-id").value = '1';
        document.form1.submit();
    }
</script>

<script>
    function MasivoCargos(ide) {
        var id = "[" + ide + "]";
        var content = document.getElementById("txt_cargs").value;
        if (content.includes(id)) {
            document.getElementById("txt_cargs").value = content.replace(id, "");
        } else {
            document.getElementById("txt_cargs").value += id;
        }
    }
</script>

<script>
    function Masivo(ide) {
        var id = "[" + ide + "]";
        var content = document.getElementById("Txt_ids").value;
        if (content.includes(id)) {
            document.getElementById("Txt_ids").value = content.replace(id, "");
        } else {
            document.getElementById("Txt_ids").value += id;
        }
    }
    function MostrarR() {
        if (document.getElementById('f_registro').style.display = "block") {
        } else {
            document.getElementById('f_registro').style.display = "none";
        }
    }
</script>

<script>
    function EsconderRR() {
        if (document.getElementById('f_registro').style.display = "block") {
            document.getElementById('f_registro').style.display = "none";
        } else {
        }
    }
    function EsconderR() {
        if (document.getElementById('form_est').style.display = "block") {
            document.getElementById('form_est').style.display = "none";
        } else {
        }
    }
    function Esconder_Maquina() {
        if (document.getElementById('cont_maquina').style.display = "block") {
            document.getElementById('cont_maquina').style.display = "none";
        } else {
        }
    }
    function Esconder_estado() {
        if (document.getElementById('form_estad').style.display = "block") {
            document.getElementById('form_estad').style.display = "none";
        } else {
        }
    }

    function Esconder_reg_ft() {
        if (document.getElementById('toggle7').style.display = "block") {
            document.getElementById('toggle7').style.display = "none";
        } else {
        }
    }

    function myFunction() {
        document.getElementById("myForm").submit();
    }

    function HacerSubmit() {
        document.getElementById("formEstado").submit();
    }

</script>
<script>
    //<editor-fold defaultstate="collapsed" desc="Filtro de datos en tiempo real">
    function Filtrar()
    {
        var table = document.getElementById('resultados');
        var filtro = document.getElementById('Txt_filtro').value.toLowerCase();
        filtro = filtro.trim(filtro);
        var cellsOfRow = "";
        var found = false;
        var compareWith = "";
        // Recorremos todas las filas con contenido de la tabla
        for (var i = 1; i < table.rows.length; i++)
        {
            cellsOfRow = table.rows[i].getElementsByTagName('td');
            found = false;
            // Recorremos todas las celdas
            if (filtro == "") {
                found = false;
            } else {
                for (var j = 0; j < cellsOfRow.length && !found; j++)
                {
                    compareWith = cellsOfRow[j].innerHTML.toLowerCase();
                    // Buscamos el texto en el contenido de la celda
                    if (filtro.length == 0 || (compareWith.indexOf(filtro) > -1))
                    {
                        found = true;
                    }
                }
            }
            if (found)
            {
                table.rows[i].style.display = '';
            } else {
                if (filtro.length == 0) {
                    // i starts from 1 to skip table header row
                    if (i > 10)
                        table.rows[i].style.display = 'none';
                    else
                        table.rows[i].style.display = '';
                    // table.rows[i].style.display = '';
                } else {
                    // si no ha encontrado ninguna coincidencia, esconde la fila de la tabla
                    table.rows[i].style.display = 'none';

                }
            }
        }
    }
    //</editor-fold>

</script>
<script>
    function Filtrar2()
    {
        var table = document.getElementById('cons_pdnt');
        var filtro = document.getElementById('Txt_filtrop').value.toLowerCase();
        filtro = filtro.trim(filtro);
        var cellsOfRow = "";
        var found = false;
        var compareWith = "";
        // Recorremos todas las filas con contenido de la tabla
        for (var i = 1; i < table.rows.length; i++)
        {
            cellsOfRow = table.rows[i].getElementsByTagName('td');
            found = false;
            // Recorremos todas las celdas
            if (filtro == "") {
                found = false;
            } else {
                for (var j = 0; j < cellsOfRow.length && !found; j++)
                {
                    compareWith = cellsOfRow[j].innerHTML.toLowerCase();
                    // Buscamos el texto en el contenido de la celda
                    if (filtro.length == 0 || (compareWith.indexOf(filtro) > -1))
                    {
                        found = true;
                    }
                }
            }
            if (found)
            {
                table.rows[i].style.display = '';
            } else {
                if (filtro.length == 0) {
                    // i starts from 1 to skip table header row
                    if (i > 10)
                        table.rows[i].style.display = 'none';
                    else
                        table.rows[i].style.display = '';
                    // table.rows[i].style.display = '';
                } else {
                    // si no ha encontrado ninguna coincidencia, esconde la fila de la tabla
                    table.rows[i].style.display = 'none';

                }
            }
        }
    }
</script>
<script>
    function Filtrar3()
    {
        var table = document.getElementById('table_history');
        var filtro = document.getElementById('Txt_filtroh').value.toLowerCase();
        filtro = filtro.trim(filtro);
        var cellsOfRow = "";
        var found = false;
        var compareWith = "";
        // Recorremos todas las filas con contenido de la tabla
        for (var i = 1; i < table.rows.length; i++)
        {
            cellsOfRow = table.rows[i].getElementsByTagName('td');
            found = false;
            // Recorremos todas las celdas
            if (filtro == "") {
                found = false;
            } else {
                for (var j = 0; j < cellsOfRow.length && !found; j++)
                {
                    compareWith = cellsOfRow[j].innerHTML.toLowerCase();
                    // Buscamos el texto en el contenido de la celda
                    if (filtro.length == 0 || (compareWith.indexOf(filtro) > -1))
                    {
                        found = true;
                    }
                }
            }
            if (found)
            {
                table.rows[i].style.display = '';
            } else {
                if (filtro.length == 0) {
                    // i starts from 1 to skip table header row
                    if (i > 10)
                        table.rows[i].style.display = 'none';
                    else
                        table.rows[i].style.display = '';
                    // table.rows[i].style.display = '';
                } else {
                    // si no ha encontrado ninguna coincidencia, esconde la fila de la tabla
                    table.rows[i].style.display = 'none';

                }
            }
        }
    }
</script>


<style type="text/css">
    html, body
    {
        font-family: verdana,sans-serif;
        margin: 0;
        padding: 5px;
    }
</style>
<!-- Menu flotante -->
<style>
    #toggle {
        float: left;
        width: 240px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;

    }
    #toggle2 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;   

    }
    #toggle3 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
    #toggle4 {
        position: absolute;
        float: right;
        width: 300px;
        font-size: 14px;
        right: 215px;
        background-color: #fff;
    }
    #toggle5 {
        float: left;
        width: 530px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
    #toggle6 {
        float: left;
        width: 540px;
        font-size: 14px;
        background-color: #fff;
        position: absolute;
        margin-left: 25px;    
        margin-top: 15;
    }
    #toggle7 {
        float: left;
        background-color: #fff;
        position: absolute;  
        margin-top: 15;
    }
    #toggle8 {
        float: right;
        background-color: #fff;
        position: absolute;  
        margin-top: 15;
    }
    #toggle9 {
        float: right;
        background-color: #fff;
        position: absolute;  
        margin-top: 15;
    }
    #toggle10 {
        float: right;
        background-color: #fff;
        position: absolute;  
        margin-top: 15;
    }
</style>

