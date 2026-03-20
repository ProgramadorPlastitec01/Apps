<%@page contentType="text/html" pageEncoding="ISO-8859-1"  session="true" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>COPEC</title>
        <link rel="icon" type="image/png" href="Interfaz/Template/images/copec.ico" />
    </head>
    <body>
        <%
                    HttpSession sesion = request.getSession();
                    sesion.removeAttribute("l_id_capacitador");
                    sesion.removeAttribute("l_capacitador");
                    sesion.invalidate();
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
        %>
    </body>
</html>
