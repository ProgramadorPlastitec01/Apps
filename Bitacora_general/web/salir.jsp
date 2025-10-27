        <%
                    HttpSession sesion = request.getSession();
                    if (sesion.getAttribute("identificacion") != null || sesion.getAttribute("nombre") != null || sesion.getAttribute("rol") != null) {
                        sesion.removeAttribute("identificacion");
                        sesion.removeAttribute("nombre");
                        sesion.removeAttribute("rol");
                        sesion.invalidate();
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
        %>
