<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--
    Fragmento de librerias compartidas por index.jsp e Inicio.jsp.

    NO es un documento HTML. Se incluye con <jsp:include> DENTRO del <body> de
    la pagina anfitriona, asi que emitir aqui <!DOCTYPE>, <html>, <head> o
    <body> generaba un documento anidado y dejaba el <link> de estilos colgado
    dentro del cuerpo.

    DEPENDENCIA: la pagina anfitriona debe haber cargado jquery.min.js ANTES de
    incluir este fragmento. modules-toastr.js invoca $ en el nivel superior del
    archivo; si $ no existe todavia el script aborta con "ReferenceError: $ is
    not defined" y ningun manejador posterior del archivo llega a registrarse.
      - index.jsp  carga jQuery en su propio <head>.
      - Inicio.jsp lo recibe de Menu.jsp, por eso incluye este fragmento
        despues del menu y no antes.

    sweetalert es obligatorio aqui: Tags/Tag_alertas.java emite llamadas
    directas a swal(...) para usuario inactivo y datos incorrectos. Sin la
    libreria el navegador lanza "swal is not defined" y el usuario se queda sin
    ninguna respuesta visual al fallar el ingreso.
--%>
<link rel="stylesheet" href="Interfaz/Contenido/assets/modules/izitoast/css/iziToast.min.css">
<link rel="stylesheet" href="Interfaz/Alertas/dist/sweetalert.css">
<script src="Interfaz/Alertas/dist/sweetalert.min.js"></script>
<script src="Interfaz/Contenido/assets/modules/izitoast/js/iziToast.min.js"></script>
<script src="Interfaz/Contenido/assets/js/page/modules-toastr.js"></script>
