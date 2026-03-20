package Servlets;

import Controladores.MaquinaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Maquina extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = Integer.parseInt(sesion.getAttribute("id_usuario").toString());
            MaquinaJpaController jpa_maquina = new MaquinaJpaController();
            String filtro = "", maquina = "", descripcion = "";
            boolean resultado = false;
            int id_maquina = 0, estado = 0;
            double frecuencia = 0;
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus");
                    id_maquina = Integer.parseInt(request.getParameter("idM"));
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_maquina", id_maquina);
                    request.getRequestDispatcher("Maquina.jsp").forward(request, response);
                    break;
                case 2:
                    maquina = request.getParameter("txt_maquina");
                    descripcion = request.getParameter("txt_descripcion");
                    frecuencia = Double.parseDouble(request.getParameter("txt_frecuencia"));
                    resultado = jpa_maquina.registroMaquina(maquina, descripcion, frecuencia);
                    request.setAttribute("registro_maquina", resultado);
                    request.getRequestDispatcher("Maquina?opc=1&idM=" + 0 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_maquina = Integer.parseInt(request.getParameter("idM"));
                    maquina = request.getParameter("txt_maquina");
                    descripcion = request.getParameter("txt_descripcion");
                    frecuencia = Double.parseDouble(request.getParameter("txt_frecuencia"));
                    resultado = jpa_maquina.modificarMaquina(id_maquina, maquina, descripcion, frecuencia);
                    request.setAttribute("modificar_maquina", resultado);
                    request.getRequestDispatcher("Maquina?opc=1&idM=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_maquina = Integer.parseInt(request.getParameter("idM").toUpperCase());
                    estado = Integer.parseInt(request.getParameter("estado").toUpperCase());
                    resultado = jpa_maquina.estadoMaquina(id_maquina, estado);
                    request.setAttribute("estado_maquina", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Maquina?opc=1&idM=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
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
