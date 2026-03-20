package Servlet;

import Controlador.PreguntaJpaController;
import Controlador.PruebaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Prueba extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean resultado = true;
            PruebaJpaController jpa_prueba = new PruebaJpaController();
            PreguntaJpaController jpa_pregunta = new PreguntaJpaController();
            String nombre_usuario = sesion.getAttribute("l_capacitador").toString();
            int id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            int estado = 0, id_prueba = 0, id_pregunta = 0, tiempo_Lmt = 0, promedio = 0, tipoC = 0;
            String nombre = "", filtro = "", ids_preguntas = "";
            switch (opc) {
                case 1:
                    tipoC = Integer.parseInt(request.getParameter("TpC").toString());
                    filtro = request.getParameter("txt_bus").toString();
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    id_pregunta = Integer.parseInt(request.getParameter("idPr").toString());
                    if (tipoC == 1) {
                        if (filtro.equals("")) {
                            request.setAttribute("lst_pruebas", jpa_prueba.ConsultapruebasPorArea(id_area));
                        } else {
                            request.setAttribute("lst_pruebas", jpa_prueba.ConsultapruebasPorAreaFiltro(id_area, filtro));
                        }
                    } else if (tipoC == 2) {
                        if (id_prueba != 0) {
                            request.setAttribute("lst_prueba", jpa_prueba.ConsultapruebaId(id_prueba));
                        }
                        request.setAttribute("lst_pruebas", jpa_prueba.ConsultapruebasPorArea(id_area));
                        if (filtro.equals("")) {
                            request.setAttribute("lst_preguntas", jpa_pregunta.ConsultaPreguntas());
                        } else {
                            request.setAttribute("lst_preguntas", jpa_pregunta.ConsultaPreguntasFiltro(filtro));
                        }
                    }
                    request.setAttribute("id_prueba", id_prueba);
                    request.setAttribute("id_pregunta", id_pregunta);
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("tipoC", tipoC);
                    request.getRequestDispatcher("Prueba.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre").toString();
                    tiempo_Lmt = Integer.parseInt(request.getParameter("txt_tiempoLmt").toString());
                    promedio = Integer.parseInt(request.getParameter("txt_promedio").toString());
                    resultado = jpa_prueba.RegistroPrueba(nombre, id_area, tiempo_Lmt, promedio, nombre_usuario);
                    if (resultado) {
                        request.setAttribute("registro_prueba", resultado);
                    } else {
                        request.setAttribute("registro_prueba", resultado);
                    }
                    request.getRequestDispatcher("Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 1 + "&txt_bus=").forward(request, response);
                    break;
                case 3:
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    filtro = request.getParameter("txt_bus").toString();
                    nombre = request.getParameter("txt_nombre").toString();
                    tiempo_Lmt = Integer.parseInt(request.getParameter("txt_tiempoLmt").toString());
                    promedio = Integer.parseInt(request.getParameter("txt_promedio").toString());
                    resultado = jpa_prueba.ModificarPrueba(id_prueba, nombre, tiempo_Lmt, promedio);
                    if (resultado) {
                        request.setAttribute("modificar_prueba", resultado);
                    } else {
                        request.setAttribute("modificar_prueba", resultado);
                    }
                    request.getRequestDispatcher("Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 1 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 4:
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    filtro = request.getParameter("txt_bus").toString();
                    estado = Integer.parseInt(request.getParameter("est").toString());
                    resultado = jpa_prueba.EstadoPrueba(id_prueba, estado);
                    if (resultado) {
                        request.setAttribute("estado_prueba", resultado);
                    } else {
                        request.setAttribute("estado_prueba", resultado);
                    }
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 1 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 5:
                    id_prueba = Integer.parseInt(request.getParameter("idP").toString());
                    filtro = request.getParameter("txt_bus").toString();
                    ids_preguntas = request.getParameter("idPrs").toString();
                    resultado = jpa_prueba.RegistroPreguntaPrueba(id_prueba, ids_preguntas);
                    if (resultado) {
                        request.setAttribute("preguntas_prueba", resultado);
                    } else {
                        request.setAttribute("preguntas_prueba", resultado);
                    }
                    request.getRequestDispatcher("Prueba?opc=1&idP=" + id_prueba + "&idPr=" + 0 + "&TpC=" + 2 + "&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 6:
                    
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
