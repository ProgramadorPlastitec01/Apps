<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("idUsuario") != null || sesion.getAttribute("Nombre") != null || sesion.getAttribute("Rol") != null) {
        sesion.removeAttribute("idUsuario");
        sesion.removeAttribute("Nombre");
        sesion.removeAttribute("Rol");
        sesion.invalidate();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>