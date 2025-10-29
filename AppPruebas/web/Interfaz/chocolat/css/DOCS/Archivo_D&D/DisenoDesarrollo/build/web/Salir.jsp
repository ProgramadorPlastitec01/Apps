<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("Id_usuario") != null || sesion.getAttribute("Nombres") != null || sesion.getAttribute("Cargo") != null || sesion.getAttribute("Rol/Nombres") != null || sesion.getAttribute("Mail") != null
            || sesion.getAttribute("Pass_mail") != null || sesion.getAttribute("Cargo") != null || sesion.getAttribute("Usuario_cargo") != null || sesion.getAttribute("Menu") != null) {
        sesion.removeAttribute("Id_usuario");
        sesion.removeAttribute("Nombres");
        sesion.removeAttribute("Cargo");
        sesion.removeAttribute("Rol/Nombres");
        sesion.removeAttribute("Mail");
        sesion.removeAttribute("Pass_mail");
        sesion.removeAttribute("Cargo");
        sesion.removeAttribute("Usuario_cargo");
        sesion.removeAttribute("Menu");
        sesion.invalidate();
//        request.setAttribute("Alerta", "Salida");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
%>