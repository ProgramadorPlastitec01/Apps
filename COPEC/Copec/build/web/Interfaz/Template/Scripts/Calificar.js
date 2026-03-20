function Calificar(id_pregunta) {
    var id_prueba = document.getElementById("idP").value;
    var id_usuario = document.getElementById("idU").value;
    var id_programacion = document.getElementById("idProg").value;
    var query = document.getElementById("query-id").value;
    var seleccion = "";
    var opciones = document.getElementsByName("opc" + id_pregunta);
    for (var i = 0; i < opciones.length; i++) {
        query = query.replace("(" + id_usuario + "," + id_prueba + "," + id_pregunta + "," + id_programacion + ",'" + opciones[i].value + "',(select if(p.respuesta ='" + opciones[i].value + "'  ,1,0) from pregunta p where p.id_pregunta =" + id_pregunta + "),fechI)", "(" + id_usuario + "," + id_prueba + "," + id_pregunta + "," + id_programacion + ",selccionU,(select if(p.respuesta =selccionU  ,1,0) from pregunta p where p.id_pregunta =" + id_pregunta + "),fechI)");
        if (opciones[i].checked) {
            seleccion = opciones[i].value;
        }
    }
    var sentencia = "(" + id_usuario + "," + id_prueba + "," + id_pregunta + "," + id_programacion + ",'" + seleccion + "',(select if(p.respuesta ='" + seleccion + "'  ,1,0) from pregunta p where p.id_pregunta =" + id_pregunta + "),fechI)";
    query = query.replace("(" + id_usuario + "," + id_prueba + "," + id_pregunta + "," + id_programacion + ",selccionU,(select if(p.respuesta =selccionU  ,1,0) from pregunta p where p.id_pregunta =" + id_pregunta + "),fechI)", sentencia);
    document.getElementById("query-id").value = query;
}

function boton() {
    form = document.forms["formP"];
    var total = document.getElementById("Topreg-id").value;
    var cont = 0;
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type === "radio") {
            if(form.elements[i].checked){
                cont = (cont + 1);
            }
        }
    }
    if (cont >= total) {
        document.getElementById("botonF").style.display = "block";
    }
    
}

function tiempo() {
   var Hora = document.getElementById("Hours").innerHTML;
   var minutos = document.getElementById("Minutes").innerHTML;
   var segundos = document.getElementById("Seconds").innerHTML;
    if (Hora === "0" && minutos === "0" && segundos === "0") {
        document.getElementById("formP-id").submit();
    }
   
}