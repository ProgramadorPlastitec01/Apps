<%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("id") != null || sesion.getAttribute("Nombre") != null || sesion.getAttribute("Rol") != null) {
                sesion.removeAttribute("id_usuario");
                sesion.removeAttribute("Nombre");
                sesion.removeAttribute("Documento");
                sesion.removeAttribute("Usuario");
                sesion.removeAttribute("id_rol");
                sesion.removeAttribute("Rol");
                sesion.invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
%>

