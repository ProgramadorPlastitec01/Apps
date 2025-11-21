<%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("Id_usuario") != null || sesion.getAttribute("Nombres") != null || sesion.getAttribute("Codigo") != null || sesion.getAttribute("Usuario") != null || sesion.getAttribute("Password") != null
                    || sesion.getAttribute("Estado") != null || sesion.getAttribute("Id_rol") != null || sesion.getAttribute("Nombre_rol") != null || sesion.getAttribute("Fecha_registro") != null
                    || sesion.getAttribute("Rol/Nombres") != null || sesion.getAttribute("Menu") != null) {
                sesion.removeAttribute("Id_usuario");
                sesion.removeAttribute("Nombres");
                sesion.removeAttribute("Rol/Nombres");
                sesion.removeAttribute("Codigo");
                sesion.removeAttribute("Usuario");
                sesion.removeAttribute("Password");
                sesion.removeAttribute("Estado");
                sesion.removeAttribute("Id_rol");
                sesion.removeAttribute("Nombre_rol");
                sesion.removeAttribute("Fecha_registro");
                sesion.removeAttribute("Menu");
                sesion.invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
%>