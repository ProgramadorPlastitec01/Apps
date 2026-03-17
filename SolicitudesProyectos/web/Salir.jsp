        <%
                    HttpSession sesion = request.getSession();
                    if (sesion.getAttribute("id") != null || sesion.getAttribute("Nombre") != null || sesion.getAttribute("Rol") != null || sesion.getAttribute("Area") != null || sesion.getAttribute("IdMqn") != null || sesion.getAttribute("est") != null) {
                        sesion.removeAttribute("id");
                        sesion.removeAttribute("Nombre");
                        sesion.removeAttribute("Rol");
                        sesion.removeAttribute("Area");
                        sesion.removeAttribute("IdMqn");
                        sesion.removeAttribute("est");
                        sesion.invalidate();
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
        %>