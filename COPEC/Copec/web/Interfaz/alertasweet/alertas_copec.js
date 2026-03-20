
function CerrarSesion() {
    swal({
        title: "Seguro que desea salir de COPEC?",
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#B72E27",
        confirmButtonText: "Salir",
        closeOnConfirm: false

    },
            function () {
                swal("Saliendo", "", "success");
                location.href = 'Salir.jsp';
            });
}

//  Alertas Modulo Capacitacion

function borrar_entrenamiento(id_capacitacion) {
    swal({
        title: "Desea borrar la programacion?",
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false

    },
            function () {
                location.href = 'Capacitacion?opc=5&id_capacitacion=' + id_capacitacion + '';
            });
}

function cerrar_entrenamiento(id_capacitacion) {
    swal({
        title: "Desea finalizar la programacion?",
        text: "Nota: al finalizar el entrenamiento no podra registrar mas personal al mismo",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false

    },
            function () {
                location.href = 'Capacitacion?opc=3&id_capacitacion=' + id_capacitacion + '';
            });
}


// Alertas Modulo Personal

function estado_activo(id_empleado, id_area) {

    swal({
        title: "Desea Enviar el Empleado a \n Estado Inactivo?",
        text: "Nota: Puede volver a activar el empleado cuando desee",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Empleado?opc=6&id_empleado=' + id_empleado + '&id_area=' + id_area + '&estado=' + 0 + '';
            });
}

function estado_inactivo(id_empleado, id_area) {

    swal({
        title: "Desea Enviar el Empleado a \n Estado Activo?",
        text: "Nota: Puede volver a inactivar el empleado cuando desee",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Empleado?opc=6&id_empleado=' + id_empleado + '&id_area=' + id_area + '&estado=' + 1 + '';
            });
}


// Alertas Modulo Manual


function reg_manual() {

    swal("Registrando \n Documento", "", "success");

}

function inactivo_documento(manual, siglatura, tipo, version) {
    swal({
        title: "Desea Inactivar el Documento?",
        text: "Nota: Al Inactivar el documento, sera inactivado de todas las matrices de las areas de la empresa \n y seran notificadas por correo del cambio.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                swal("Enviando \n Documento", "", "success");
                location.href = 'Manual?opc=12&estado=0&id_manual=' + manual + '&cbx_siglatura=' + siglatura + '&tipo_doc=' + tipo + '&txt_version=' + version + '&op=0';
            });
}

function activar_documento(manual, siglatura, tipo, version) {
    swal({
        title: "Activando Documento",
        text: "",
        timer: 1000,
        showConfirmButton: false

    },
            function () {
                location.href = 'Manual?opc=12&estado=1&id_manual=' + manual + '&cbx_siglatura=' + siglatura + '&tipo_doc=' + tipo + '&txt_version=' + version + '&op=1';
            });
}

function cambiar_nombre() {
    swal({
        title: "Nombre \n Enviado",
        text: "",
        timer: 1000,
        showConfirmButton: false
    })
}

function cambiar_version() {
    swal({
        title: "Desea Cambiar la Version del Documento?",
        text: "Nota: Al Cambiar la version del documento, no dejara volverla a una version anterior",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                swal("Enviando \n Version", "", "success");
                document.getElementById("enviar_version").action = 'Manual?opc=6';
                document.getElementById("enviar_version").submit();
            });
}


// Alertas Modulo Capacitador

function estado_activo_capacitador(id_capacitador) {

    swal({
        title: "Desea Inactivar el Capacitador ?",
        text: "Nota: Puede volver a activar el Capacitador cuando desee, al inactivar no puede ingresar al sistema ese capacitador.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Capacitador?opc=10&id_capacitador=' + id_capacitador + '&estado=0';
            });
}

function estado_inactivo_capacitador(id_capacitador) {

    swal({
        title: "Desea Activar el Capacitador?",
        text: "Nota: Al volver a activar el capacitador puede ingresar con el usuario y contraseña registrados",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Capacitador?opc=10&id_capacitador=' + id_capacitador + '&estado=1';
            });
}


// Alertas Modulo Area

function estado_activo_area(id_area) {

    swal({
        title: "Desea Inactivar el Area ?",
        text: "Nota: Puede volver a activar el Area cuando desee.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Area?opc=3&id_area=' + id_area + '&estado=0&caso=1';
            });
}

function estado_inactivo_area(id_area) {

    swal({
        title: "Desea Activar el Area ?",
        text: "Nota: Puede volver a inactivar el Area cuando desee.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Area?opc=3&id_area=' + id_area + '&estado=1&caso=2';
            });
}


//Alertas Modulo Cargos


function estado_activo_cargo(id_cargo) {

    swal({
        title: "Desea Inactivar el Cargo ?",
        text: "Nota: Puede volver a activar el Cargo cuando desee.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Cargo?opc=3&id_cargo=' + id_cargo + '&estado=0&caso=1';
            });
}

function estado_inactivo_cargo(id_cargo) {

    swal({
        title: "Desea Activar el Cargo ?",
        text: "Nota: Puede volver a inactivar el Cargo cuando desee.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                location.href = 'Cargo?opc=3&id_cargo=' + id_cargo + '&estado=1&caso=2';
            });
}

function mod_cargo_user() {
    swal({
        title: "Desea Modificar el Registro del Cargo?",
        text: "Nota: \n Al cambiar la version del registro matriz acuerdese de actualizar el sistema DARUMA y reentrenar el personal en los cambios.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false

    },
            function () {
                document.getElementById("modifica_cargo").action = 'Cargo?opc=5&op=2';
                document.getElementById("modifica_cargo").submit();
            });
}


//Alertas Modulo Tipo Documento


function reg_tdocumento() {

    swal("Registrando \n Tipo de Documento", "", "success");

}

function mod_tdocumento() {

    swal("Modificando \n Tipo de Documento", "", "success");

}

function estado_activo_tdocumento(id_documento) {

    swal({
        title: "Desea Inactivar el Tipo de Documento ?",
        text: "Nota: Puede volver a activar el Tipo de Documento cuando desee.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                swal("Enviando \n Documento", "", "success");
                location.href = 'Documento?opc=3&id_documento=' + id_documento + '&estado=0&caso=1';
            });
}

function estado_inactivo_tdocumento(id_documento) {

    swal({
        title: "Desea Activar el Tipo de Documento ?",
        text: "Nota: Puede volver a inactivar el Tipo de Documento cuando desee.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#048B86",
        confirmButtonText: "Enviar",
        closeOnConfirm: false
    },
            function () {
                swal("Enviando \n Documento", "", "success");
                location.href = 'Documento?opc=3&id_documento=' + id_documento + '&estado=1&caso=2';
            });
}