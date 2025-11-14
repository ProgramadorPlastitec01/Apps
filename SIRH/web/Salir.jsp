<%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("Id_usuario") != null || sesion.getAttribute("Nombre_apellido") != null || sesion.getAttribute("Color") != null || sesion.getAttribute("Rol") != null || sesion.getAttribute("Menu") != null) {
                sesion.removeAttribute("Nombre_apellido");
                sesion.removeAttribute("Id_usuario");
                sesion.removeAttribute("Color");
                sesion.removeAttribute("Rol");
                sesion.removeAttribute("Menu");
                sesion.removeAttribute("Fecha_sesion");
                sesion.removeAttribute("FechaPS_inicio");
                sesion.removeAttribute("FechaPS_fin");
                sesion.removeAttribute("Id_areaS");
                sesion.removeAttribute("Consulta_personalS");
                sesion.invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
%>