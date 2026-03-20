package Servlet;

import Controlador.PreguntaJpaController;
import Controlador.TipoProcesoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pregunta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean resultado = true;
            String nombre_usuario = sesion.getAttribute("l_capacitador").toString();
            PreguntaJpaController jpa_pregunta = new PreguntaJpaController();
            int id_prueba = 0, id_pregunta = 0, id_tipoP = 0, cont = 0;
            String filtro = "", pregunta = "", respuestaC = "", opciones = "";
            switch (opc) {
                case 1:
                    filtro = request.getParameter("txt_bus").toString();
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_pregunta = Integer.parseInt(request.getParameter("idPr").toString());
                    request.setAttribute("lst_preguntas", jpa_pregunta.ConsultaPreguntas());
                    request.setAttribute("id_prueba", id_prueba);
                    request.setAttribute("id_pregunta", id_pregunta);
                    request.setAttribute("filtro", filtro);
                    request.getRequestDispatcher("Pregunta.jsp").forward(request, response);
                    break;
                case 2:
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_tipoP = Integer.parseInt(request.getParameter("slc_proceso").toString());
                    cont = Integer.parseInt(request.getParameter("cont").toString());
                    pregunta = request.getParameter("txt_pregunta").toString();
                    respuestaC = request.getParameter("txt_ResCorrecta").toString();
                    for (int i = 0; i < cont; i++) {
                        if (i == 0) {
                            opciones = request.getParameter("txt_opcion" + i + "").toString();
                        } else {
                            opciones = opciones + "//" + request.getParameter("txt_opcion" + i + "").toString();
                        }
                    }
                    resultado = jpa_pregunta.RegistroPregunta(id_tipoP, pregunta, respuestaC, opciones, nombre_usuario);
                    if (resultado) {
                        request.setAttribute("registro_pregunta", resultado);
                    } else {
                        request.setAttribute("registro_pregunta", resultado);
                    }
                    request.getRequestDispatcher("Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 2 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus").toString();
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_pregunta = Integer.parseInt(request.getParameter("idPr").toString());
                    id_tipoP = Integer.parseInt(request.getParameter("slc_proceso").toString());
                    cont = Integer.parseInt(request.getParameter("cont").toString());
                    pregunta = request.getParameter("txt_pregunta").toString();
                    respuestaC = request.getParameter("txt_ResCorrecta").toString();
                    for (int i = 0; i < cont; i++) {
                        if (i == 0) {
                            opciones = request.getParameter("txt_opcion" + i + "").toString();
                        } else {
                            opciones = opciones + "//" + request.getParameter("txt_opcion" + i + "").toString();
                        }
                    }
                    resultado = jpa_pregunta.ModificarPregunta(id_pregunta, id_tipoP, pregunta, respuestaC, opciones);
                    if (resultado) {
                        request.setAttribute("modificar_pregunta", resultado);
                    } else {
                        request.setAttribute("modificar_pregunta", resultado);
                    }
                    request.getRequestDispatcher("Prueba?opc=1&idP=" + id_prueba + "&idPr=" + 0 + "&TpC=" + 2 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
