/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.ActividadJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class R_GC_079 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            ActividadJpaController ActJpa = new ActividadJpaController();
            HttpSession sesion = request.getSession();
            Date Fecha_ = new Date();
            boolean resultado = false;
            List list_actividades = null;
            int Id_Usuario = Integer.parseInt(sesion.getAttribute("identificacion").toString());
            int option = Integer.parseInt(request.getParameter("opc"));
            int Ubicacion = 0;
            int Consecutivo = 0;
            int Id_Actividad = 0;
            String Usuario = sesion.getAttribute("nombre").toString();
            String filtro = "";
            String Fecha = "";
            String fecha_i = "";
            String fecha_f = "";
            List Actual = null;
            String Hora = "";
            String Asunto = "";
            String Tipo = "";
            String contenido = "";
            String estado = "";
//</editor-fold>
            switch (option) {
                //<editor-fold defaultstate="collapsed" desc="1. CONSULTAR">
                case 1:
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    try {
                        Actual = ActJpa.consultarConsecutivo();
                        Object[] obj_actual = (Object[]) Actual.get(0);
                        Consecutivo = Integer.parseInt(obj_actual[0].toString()) + 1;
                    } catch (Exception e) {
                        Consecutivo = 1;
                    }
                    if (fecha_i == null && fecha_f == null) {
                        fecha_i = "";
                        fecha_f = "";
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    filtro = request.getParameter("txt_bus");
                    request.setAttribute("Id_Actividad", Id_Actividad);
                    request.setAttribute("Consecutivo", Consecutivo);
                    request.setAttribute("txt_bus", filtro);
                    request.getRequestDispatcher("R_GC_079.jsp").forward(request, response);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. REGISTRAR">
                case 2:
                    filtro = request.getParameter("txt_bus");
                    Tipo = "calidad";
                    Consecutivo = Integer.parseInt(request.getParameter("consecutiv"));
                    Fecha = request.getParameter("txtfecha");
                    Hora = request.getParameter("txthora");
                    Asunto = request.getParameter("txtturno");
                    Ubicacion = Integer.parseInt(request.getParameter("Ubicacion"));
                    contenido = request.getParameter("txt_descripcion-id");
                    String[] parts = contenido.split("<hr />");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    String part3 = parts[2];
                    resultado = ActJpa.registrarActividad(Fecha, Hora, Consecutivo, Asunto, Ubicacion, part1, part2, part3, Tipo, Id_Usuario);
                    request.setAttribute("resultado_inserta_actividad", resultado);
                    request.getRequestDispatcher("R_GC_079?opc=1&Id_Actividad=0&txt_bus=" + filtro + "").forward(request, response);
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="3. MODIFICAR">
                case 3:
                    try {
                        Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    } catch (Exception e) {
                        Id_Actividad = 0;
                    }
                    filtro = request.getParameter("txt_bus");
                    Asunto = request.getParameter("txtturno");
                    Ubicacion = Integer.parseInt(request.getParameter("Ubicacion"));
                    contenido = request.getParameter("txt_descripcion-id");
                    String[] partes = contenido.split("<hr />");
                    String parte1 = partes[0];
                    String parte2 = partes[1];
                    String parte3 = partes[2];
                    resultado = ActJpa.modificarActividad(Id_Actividad, Asunto, Ubicacion, parte1, parte2, parte3);
                    request.setAttribute("resultado_modifica_actividad", resultado);
                    request.getRequestDispatcher("R_GC_079?opc=1&Id_Actividad=0&txt_bus=" + filtro + "").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="4. CAMBIAR ESTADO ACTIVIDAD">
                case 4:
                    Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    estado = request.getParameter("est");
                    resultado = ActJpa.estadoActividad(Id_Actividad, estado);
                    request.setAttribute("estado_actividad", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("R_GC_079?opc=1&Id_Actividad=0&txt_bus=").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="5. REVISAR ACTIVIDAD">
                case 5:
                    Id_Actividad = Integer.parseInt(request.getParameter("Id_Actividad"));
                    String Informacion = Id_Usuario + " " + Usuario + " " + Fecha_;
                    Tipo = "r";
                    resultado = ActJpa.revisarActividad(Id_Actividad, Informacion, Tipo);
                    request.setAttribute("Revisar", resultado);
                    request.getRequestDispatcher("R_GC_079?opc=1&Id_Actividad=0&txt_bus=").forward(request, response);
                    break;
//</editor-fold>
            }

        } catch (Exception e) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
