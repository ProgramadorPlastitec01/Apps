<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("idUsuario") != null || sesion.getAttribute("Nombres") != null || sesion.getAttribute("Rol/Nombres") != null || sesion.getAttribute("Documento") != null
            || sesion.getAttribute("Codigo") != null || sesion.getAttribute("Usuario") != null || sesion.getAttribute("idRol") != null
            || sesion.getAttribute("NombreRol") != null || sesion.getAttribute("Estado") != null) {
        sesion.removeAttribute("idUsuario");
        sesion.removeAttribute("Nombres");
        sesion.removeAttribute("Rol/Nombres");
        sesion.removeAttribute("Documento");
        sesion.removeAttribute("Codigo");
        sesion.removeAttribute("Usuario");
        sesion.removeAttribute("idRol");
        sesion.removeAttribute("NombreRol");
        sesion.removeAttribute("Estado");
        sesion.invalidate();
        request.setAttribute("getBack", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
        request.setAttribute("getBack", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>