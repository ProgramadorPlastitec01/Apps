package Servlets;

import Controladores.AccesorioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Accesorio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        
        try {
            HttpSession sesion = request.getSession();
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            AccesorioJpaController jpa_accesorio = new AccesorioJpaController();
            String filtro = "";
            boolean resultado = false;
            int opc = Integer.parseInt(request.getParameter("opc"));
            String nombre = "", descripcion = "";
            int idAccesorio = 0, cantidad = 0, estado = 0;
            switch (opc) {
                case 1:
//                    filtro = request.getParameter("txt_bus");
                    idAccesorio = Integer.parseInt(request.getParameter("idAc"));
//                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_accesorio", idAccesorio);
//                    request.setAttribute("ipUSer", ip);
//                    request.setAttribute("hostUSer", host);
                    request.getRequestDispatcher("Accesorio.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre");
                    descripcion = request.getParameter("txt_descripcion");
                    cantidad = Integer.parseInt(request.getParameter("txt_cantidad"));
                    resultado = jpa_accesorio.registrarAccesorio(nombre, descripcion, cantidad, nombre_Usuario);
                    request.setAttribute("Registro_accesorio", resultado);
                    request.getRequestDispatcher("Accesorio?opc=1&idAc=" + 0 + "").forward(request, response);
                    break;
                case 3:
//                    filtro = request.getParameter("txt_bus");
                    idAccesorio = Integer.parseInt(request.getParameter("idAc"));
                    nombre = request.getParameter("txt_nombre");
                    descripcion = request.getParameter("txt_descripcion");
                    cantidad = Integer.parseInt(request.getParameter("txt_cantidad"));
                    resultado = jpa_accesorio.modificarAccesorio(idAccesorio, nombre, descripcion, cantidad);
                    request.setAttribute("Modificar_accesorio", resultado);
                    request.getRequestDispatcher("Accesorio?opc=1&idAc=" + 0 + "").forward(request, response);
                    break;
                case 4:
//                    filtro = request.getParameter("txt_bus");
                    idAccesorio = Integer.parseInt(request.getParameter("idAc"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_accesorio.modificarAccesorioEstado(idAccesorio, estado);
                    request.setAttribute("Estado_accesorio", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Accesorio?opc=1&idAc=" + 0 + "").forward(request, response);
                    break;

            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
