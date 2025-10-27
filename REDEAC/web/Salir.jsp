<%@page import="Controladoras.UsuarioJpaController"%>
<%
    HttpSession sesion = request.getSession();
    UsuarioJpaController jpacusa = new UsuarioJpaController();
    boolean proceso = true;
    if (sesion.getAttribute("Rol") != null || sesion.getAttribute("Nombre_apellido") != null || sesion.getAttribute("Usuario") != null || sesion.getAttribute("Id_usuario") != null || sesion.getAttribute("Id_rol") != null) {
        //if (sesion.getAttribute("Rol").equals("Tecnico T.I")) {
        //    proceso = jpacusa.establecerTecnicoTurno(Integer.parseInt(sesion.getAttribute("Id_usuario").toString()), 0);
        //    proceso = jpacusa.establecerTecnicoTurno(2, 1);
        //}
        sesion.removeAttribute("Rol");
        sesion.removeAttribute("Nombre_apellido");
        sesion.removeAttribute("Usuario");
        sesion.removeAttribute("Id_usuario");
        sesion.removeAttribute("Id_rol");
        sesion.invalidate();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else if (sesion.getAttribute("documento") != null || sesion.getAttribute("codigo") != null) {
        sesion.removeAttribute("documento");
        sesion.removeAttribute("codigo");
        sesion.invalidate();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>
