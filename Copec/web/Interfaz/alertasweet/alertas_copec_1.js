////  Alertas Modulo Capacitacion
//
//function CerrarSesion() {
//    swal({
//        title: "Seguro que desea salir de COPEC?",
//        text: "",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//
//    },
//    function(){
//        swal("Saliendo", "", "success");
//        location.href='Salir.jsp';
//    });
//}
//
////$(document).ready(function ($) {
////    validar_regCapacitacion();
////});
//
//
////function validar_regCapacitacion() {
////    var validation1 = new LiveValidation('validate_capa',{
////        onlyOnSubmit: true
////    });
////    validation1.add( Validate.Presence );
////
////    var validation2 = new LiveValidation('validate_desc',{
////        onlyOnSubmit: true
////    });
////    validation2.add( Validate.Presence );
////
////    var validation3 = new LiveValidation('datepicker',{
////        onlyOnSubmit: true
////    });
////    validation3.add( Validate.Presence );
////
////    var automaticOnSubmit = validation1.form.onsubmit;
////
////    validation1.form.onsubmit = function(){
////        var valid = automaticOnSubmit();
////        if(valid)
////            swal({
////                title: "Entrenamiento \n Enviado",
////                text: "",
////                timer: 1000,
////                showConfirmButton: false
////            },
////            function(){
////                document.getElementById("form_capacitacion").action='Capacitacion?opc=2' ;
////                document.getElementById("form_capacitacion").submit();
////            });
////        else
////            swal("Error Al Enviar", "Por favor verificar los campos en rojo");
////        return false;
////    }
////
////}
//
//
//
//
//function borrar_entrenamiento(id_capacitacion) {
//    swal({
//
//        title: "Desea Borrar el Entrenamiento?",
//        text: "",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//
//    },
//    function(){
//        swal("Entrenamiento \n Borrado", "", "success");
//        location.href='Capacitacion?opc=5&id_capacitacion=' + id_capacitacion + '' ;
//    });
//}
//
//function cerrar_entrenamiento(id_capacitacion) {
//    swal({
//
//        title: "Desea Finalizar el Entrenamiento?",
//        text: "Nota: al finalizar el entrenamiento no podra registrar mas personal al mismo",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//
//    },
//    function(){
//        swal("Entrenamiento \n Borrado", "", "success");
//        location.href='Capacitacion?opc=3&id_capacitacion=' + id_capacitacion + '' ;
//    });
//}
//
////function enviar_editar() {
////    swal({
////        title: "Desea Editar el Entrenamiento?",
////        text: "Nota: Compruebe que todos los campos esten llenos correctamente",
////        type: "warning",
////        showCancelButton: true,
////        confirmButtonColor: "#048B86",
////        confirmButtonText: "Enviar",
////        closeOnConfirm: false
////
////    },
////    function(){
////        swal("Entrenamiento \n Editado", "", "success");
////        document.getElementById("editar_entrenamiento").action='Capacitacion?opc=14' ;
////        document.getElementById("editar_entrenamiento").submit();
////    });
////}
//
//
//// Alertas Modulo Personal
//
//
//
//
//function estado_activo(id_empleado, id_area) {
//
//    swal({
//
//        title: "Desea Enviar el Empleado a \n Estado Inactivo?",
//        text: "Nota: Puede volver a activar el empleado cuando desee",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//    },
//    function(){
//        swal("Enviando \n Empleado", "", "success");
//        location.href ='Empleado?opc=6&id_empleado=' + id_empleado + '&id_area='+id_area+'&estado=0' ;
//    });
//}
//
//function estado_inactivo(id_empleado, id_area) {
//
//    swal({
//
//        title: "Desea Enviar el Empleado a \n Estado Activo?",
//        text: "Nota: Puede volver a inactivar el empleado cuando desee",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//    },
//    function(){
//        swal("Enviando \n Empleado", "", "success");
//        location.href ='Empleado?opc=6&id_empleado=' + id_empleado + '&id_area='+id_area+'&estado=1' ;
//    });
//}
//
////function editar_personal() {
////    swal({
////        title: "Desea Editar el Empleado?",
////        text: "Nota: Compruebe que todos los campos esten llenos correctamente",
////        type: "warning",
////        showCancelButton: true,
////        confirmButtonColor: "#048B86",
////        confirmButtonText: "Enviar",
////        closeOnConfirm: false
////
////    },
////    function(){
////        swal("Entrenamiento \n Editado", "", "success");
////        document.getElementById("editar_empleado").action='Empleado?opc=5' ;
////        document.getElementById("editar_empleado").submit();
////    });
////}
//
//
//// Alertas Modulo Manual
//
//
////function confirm_regDocumento() {
////    swal({
////        title: "Desea Crear el Documento?",
////        text: "Nota: Al crear el documento no se podra eliminar",
////        type: "warning",
////        showCancelButton: true,
////        confirmButtonColor: "#048B86",
////        confirmButtonText: "Enviar",
////        closeOnConfirm: false
////    },
////    function(){
////        swal("Enviando \n Documento", "", "success");
////        document.getElementById("formTipo").action='Manual?opc=9' ;
////        document.getElementById("formTipo").submit();
////    });
////}
//
//function inactivo_documento(manual,siglatura,tipo) {
//    swal({
//        title: "Desea Inactivar el Documento?",
//        text: "Nota: Al Inactivar el documento, sera inactivado de todas las matrices de las areas de la empresa \n y seran notificadas por correo del cambio.",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//    },
//    function(){
//        swal("Enviando \n Documento", "", "success");
//        location.href='Manual?opc=12&estado=0&id_manual=' + manual + '&cbx_siglatura=' + siglatura + '&tipo_doc=' + tipo + '&op=0';
//    });
//}
//
//function activar_documento(manual,siglatura,tipo) {
//    swal({
//        title: "Activando Documento",
//        text: "",
//        timer: 1000,
//        showConfirmButton: false
//
//    },
//    function(){
//        location.href='Manual?opc=12&estado=1&id_manual=' + manual + '&cbx_siglatura=' + siglatura + '&tipo_doc=' + tipo + '&op=1';
//    });
//}
//
//function cambiar_nombre() {
//    swal({
//        title: "Nombre \n Enviado",
//        text: "",
//        timer: 1000,
//        showConfirmButton: false
//    })
//}
//
//function cambiar_version() {
//    swal({
//        title: "Desea Cambiar la Version del Documento?",
//        text: "Nota: Al Cambiar la version del documento, no dejara volverla a una version anterior",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//    },
//    function(){
//        swal("Enviando \n Version", "", "success");
//        document.getElementById("enviar_version").action='Manual?opc=6' ;
//        document.getElementById("enviar_version").submit();
//    });
//}
//
//// Alertas Modulo Capacitador
//
//
//function confirm_regCapacitador() {
//    swal({
//        title: "Capacitador \n Enviado",
//        text: "",
//        timer: 1000,
//        showConfirmButton: false
//    });
//}
//
//function estado_activo_capacitador(id_capacitador) {
//
//    swal({
//
//        title: "Desea Enviar el Capacitador a \n Estado Inactivo?",
//        text: "Nota: Puede volver a activar el Capacitador cuando desee, al inactivar no puede ingresar al sistema ese capacitador.",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//    },
//    function(){
//        swal("Enviando \n Capacitador", "", "success");
//        location.href ='Capacitador?opc=10&id_empleado=' + id_capacitador + '&estado=0' ;
//    });
//}
//
//function estado_inactivo_capacitador(id_capacitador) {
//
//    swal({
//
//        title: "Desea Activar el Capacitador?",
//        text: "Nota: Al volver a activar el capacitador puede ingresar con el usuario y contraseña registrados",
//        type: "warning",
//        showCancelButton: true,
//        confirmButtonColor: "#048B86",
//        confirmButtonText: "Enviar",
//        closeOnConfirm: false
//    },
//    function(){
//        swal("Enviando \n Capacitador", "", "success");
//        location.href ='Capacitador?opc=10&id_empleado=' + id_capacitador + '&estado=1' ;
//    });
//}
//// Alertas Modulo Documento
//
//
//
//
////$(document).ready(function () {
////    validar();
////});
//
////function validar_regds() {
////    var validation = new LiveValidation('validate_capa',{
////        onlyOnSubmit: true
////    });
////    validation.add( Validate.Presence );
////
////    var validation2 = new LiveValidation('validate_desc',{
////        onlyOnSubmit: true
////    });
////    validation2.add( Validate.Presence );
////
////    var validation3 = new LiveValidation('datepicker',{
////        onlyOnSubmit: true
////    });
////    validation3.add( Validate.Presence );
////
////    var validation4 = new LiveValidation('tm_hr_inicio',{
////        onlyOnSubmit: true
////    });
////    validation4.add( Validate.Presence );
////
////    var validation5 = new LiveValidation('cbx_cargo',{
////        onlyOnSubmit: true
////    });
////    validation5.add( Validate.Presence );
////
////    var automaticOnSubmit = validation.form.onsubmit;
////
////    validation.form.onsubmit = function(){
////        var valid = automaticOnSubmit();
////        if(valid)
////            swal({
////                title: "Entrenamiento \n Enviado",
////                text: "",
////                timer: 1000,
////                showConfirmButton: false
////            },
////            function(){
////                document.getElementById("form_id").action='Capacitacion?opc=2' ;
////                document.getElementById("form_id").submit();
////            });
////        else
////            swal("Error Al Enviar", "Por favor verificar los campos en rojo");
////        return false;
////    }
////
////}
//
//
////function validar_regEmpleado() {
////            swal({
////                title: "Personal \n Enviado",
////                text: "",
////                timer: 1000,
////                showConfirmButton: false
////            },
////            function(){
////                document.getElementById("registro_personal").action='Empleado?opc=2' ;
////                document.getElementById("registro_personal").submit();
////            });
////
////}
//
////function validar_regEmpleado() {
////
////    //Definimos los caracteres permitidos en una dirección de correo electrónico
////var regexp = /^[a-zA-Z._.-]+\@[a-zA-Z._.-]+\.[a-zA-Z]+$/;
////
//////Validamos un campo o área de texto, por ejemplo el campo nombre
////if (document.registro_personal.validate_nom.value.length==0){
////document.registro_personal.validate_nom.focus()
////onlyOnSubmit: false
////}else{
////  onlyOnSubmit: true
////}
//////document.form.submit();
////
////
////
//////
//////    var validation4 = new LiveValidation('validate_cod',{
//////        onlyOnSubmit: true
//////    });
//////    validation4.add( Validate.Presence );
//////    validation4.add(Validate.Numericality);
//////    validation4.add( Validate.Numericality, {
//////        onlyInteger: true
//////    } );
//////    var validation5 = new LiveValidation('validate_doc',{
//////        onlyOnSubmit: true
//////    });
//////    validation5.add( Validate.Presence );
//////    validation5.add(Validate.Numericality);
//////    validation5.add( Validate.Numericality, {
//////        onlyInteger: true
//////    } );
//////    validation5.add( Validate.Length, {
//////        minimum: 8,
//////        maximum: 11
//////    } );
//////    var validation6 = new LiveValidation('validate_nom',{
//////        onlyOnSubmit: true
//////    });
//////    validation6.add( Validate.Presence );
//////    validation6.add( Validate.Char );
//////    var validation7 = new LiveValidation('validate_ape',{
//////        onlyOnSubmit: true
//////    });
//////    validation7.add( Validate.Presence );
//////    validation7.add( Validate.Char );
//////    var validation8 = new LiveValidation('cbx_cargo',{
//////        onlyOnSubmit: true
//////    });
//////    validation8.add( Validate.Presence );
//////    validation8.add( Validate.Exclusion, {
//////        within: ['0']
//////    } );
////
//////
////    var automaticOnSubmit = document.registro_personal.onsubmit;
//////
////    document.registro_personal.onsubmit = function(){
////        var valid = automaticOnSubmit();
////        if(valid)
////            swal({
////                title: "Personal \n Enviado",
////                text: "",
////                timer: 1000,
////                showConfirmButton: false
////            },
////            function(){
////                document.getElementById("registro_personal").action='Empleado?opc=0' ;
////                document.getElementById("registro_personal").submit();
////            });
////        else
////            swal("Error Al Enviar", "Por favor verificar los campos en rojo");
////        return false;
////    }
////
////}
//
////Nombramos la función
////function valida_envia(){
//
////Definimos los caracteres permitidos en una dirección de correo electrónico
//var regexp = /^[0-9a-zA-Z._.-]+\@[0-9a-zA-Z._.-]+\.[0-9a-zA-Z]+$/;
//
////Validamos un campo o área de texto, por ejemplo el campo nombre
//if (document.form.nombre.value.length==0){
//alert("Tiene que escribir su nombre")
//document.form.nombre.focus()
//return 0;
//}
//
////Validamos un campo o área de texto, por ejemplo el campo apellidos
//if (document.form.apellidos.value.length==0){
//alert("Tiene que escribir sus apellidos")
//document.form.apellidos.focus()
//return 0;
//}
//
////Validamos un campo o área de texto, por ejemplo el campo dirección
//if (document.form.direccion.value.length==0){
//alert("Tiene que escribir su dirección")
//document.form.direccion.focus()
//return 0;
//}
//
////Validamos un campo o área de texto, por ejemplo el campo localidad
//if (document.form.localidad.value.length==0){
//alert("Tiene que escribir su localidad")
//document.form.localidad.focus()
//return 0;
//}
//
////Validamos un campo de lista/menú, por ejemplo el campo provincia
//if (document.form.provincia.selectedIndex==0){
//alert("Tiene que seleccionar su provincia")
//document.form.provincia.focus()
//return 0;
//}
//
////Validamos un campo de texto como numérico, por ejemplo el campo código postal de 5 dígitos
//valor = document.form.cp.value;
//if( !(/^\d{5}$/.test(valor)) ) {
//alert("Tiene que escribir su código postal (5 dígitos)");
//document.form.cp.focus();
//return 0;
//}
//
////Validamos un campo de texto como numérico, por ejemplo el campo teléfono de 9 dígitos
//valor = document.form.telefono.value;
//if( !(/^\d{9}$/.test(valor)) ) {
//alert("Tiene que escribir un teléfono de 9 dígitos");
//document.form.telefono.focus();
//return 0;
//}
//
////Validamos un campo de texto como email
//if ((regexp.test(document.form.email.value) == 0) || (document.form.email.value.length = 0)){
//alert("Introduzca una dirección de email válida");
//document.form.email.focus();
//return 0;
//} else {
//var c_email=true;
//}
//
////Validamos un campo de tipo checkbox, por ejemplo condiciones
//if (document.form.condiciones.checked==0){
//alert("Debe aceptar las condiciones")
//document.form.condiciones.focus()
//return 0;
//}
//
////Si todos los campos han validado correctamente, se envía el formulario
//document.form.submit();
//}