<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("idUser") != null || sesion.getAttribute("Nombres") != null || sesion.getAttribute("Role/Name") != null || sesion.getAttribute("Document") != null
            || sesion.getAttribute("Username") != null || sesion.getAttribute("idRol") != null
            || sesion.getAttribute("RolName") != null || sesion.getAttribute("state") != null) {
        sesion.removeAttribute("FullName");
        sesion.removeAttribute("Nombres");
        sesion.removeAttribute("Role/Name");
        sesion.removeAttribute("Document");
        sesion.removeAttribute("idRol");
        sesion.removeAttribute("Username");
        sesion.removeAttribute("RolName");
        sesion.removeAttribute("name");
        sesion.removeAttribute("Lastname");
        sesion.removeAttribute("state");
        sesion.invalidate();
        request.setAttribute("getBack", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
        request.setAttribute("getBack", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>