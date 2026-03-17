package Servlets;

import Controladores.InstrumentoMedicionJpaController;
import Controladores.NoConformidadJpaController;
import Email.Control_correo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Noconforme extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        Noconforme jpa_noconforme = new Noconforme();
        NoConformidadJpaController jpa_noconformidad = new NoConformidadJpaController();
        Control_correo correo = new Control_correo();
        try {
            HttpSession sesion = request.getSession();
            InstrumentoMedicionJpaController jpa_intrumento = new InstrumentoMedicionJpaController();
            String buscador = "";
            List Instrument_serial = null;
            List plantilla_noconforme = null;
            List plantilla_correo = null;
            boolean rgt_noconforme = false;
            int opc = Integer.parseInt(request.getParameter("opc"));
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            int id_instrumento = 0;
            String fecha = "";
            int consecutivo = 0;
            String plantilla = "";
            int idrgt = 0;
            int idrgtnoconforme = 0;
            int idins = 0;
            int idI = 0;
            switch (opc) {
                case 1:
                    request.getRequestDispatcher("Rnoconforme.jsp").forward(request, response);
                    break;
                case 2:
                    buscador = request.getParameter("txtserial");
                    Instrument_serial = jpa_noconformidad.consultaSerial(buscador);
                    if (Instrument_serial.isEmpty() || Instrument_serial == null) {
                        request.setAttribute("Consulta_serialvacio", Instrument_serial);
                    } else {
                        request.setAttribute("Consulta_serial", Instrument_serial);
                    }
                    request.getRequestDispatcher("Rnoconforme.jsp").forward(request, response);
                    break;
                case 3:
                    id_instrumento = Integer.parseInt(request.getParameter("lst_serial"));
                    consecutivo = Integer.parseInt(request.getParameter("txtconsecutivo"));
                    fecha = request.getParameter("txtfecha");
                    rgt_noconforme = jpa_noconformidad.registroNoConformidad(id_instrumento, consecutivo, fecha, nombre_Usuario);
                    request.setAttribute("Registro_noconformidad", rgt_noconforme);
                    request.getRequestDispatcher("Rnoconforme.jsp").forward(request, response);
                    break;
                case 4:
                    idrgt = Integer.parseInt(request.getParameter("id"));
                    List plantillanoconforme = jpa_noconformidad.consultaPlantilla();
                    plantilla_noconforme = jpa_noconformidad.registroNoConforme(idrgt);
                    if (plantilla_noconforme.isEmpty() || plantilla_noconforme == null) {
                    } else {
                        request.setAttribute("Visor_plantilla", plantilla_noconforme);
                        request.setAttribute("plantilla", plantillanoconforme);
                    }
                    request.getRequestDispatcher("Rnoconforme.jsp").forward(request, response);
                    break;
                case 5:
                    plantilla = request.getParameter("txt_plantilla");
                    idrgt = Integer.parseInt(request.getParameter("idrgt"));
                    rgt_noconforme = jpa_noconformidad.modificarRegistroNoConformidad(idrgt, plantilla);
                    request.setAttribute("MRegistro_noconformidad", rgt_noconforme);
                    request.getRequestDispatcher("Noconforme?opc=4&id=" + idrgt + "").forward(request, response);
                    break;
                case 6:
                    idrgtnoconforme = Integer.parseInt(request.getParameter("id"));
                    idins = Integer.parseInt(request.getParameter("idins"));
                    rgt_noconforme = jpa_noconformidad.modificarEstadoRegistroNoConformidad(idrgtnoconforme, 1);
                    request.setAttribute("MEstRegistro_noconformidad", rgt_noconforme);
                    request.getRequestDispatcher("Noconforme?opc=4&idI=" + idins + "").forward(request, response);
                    break;
                case 7:
                    String destino = request.getParameter("txt_destino");
                    String destinatarios = request.getParameter("txt_destinatarios");
                    idrgtnoconforme = Integer.parseInt(request.getParameter("idrgt"));
                    idI = Integer.parseInt(request.getParameter("idi"));
                    correo.ReporteNoconformidad(idrgtnoconforme, destinatarios);
                    correo.RevisaNoconformidad(idrgtnoconforme, destino);
                    correo.RevisaNoconformidadDrto(idrgtnoconforme);
                    rgt_noconforme = jpa_noconformidad.modificarEstadoRegistroNoConformidad(idrgtnoconforme, 2);
                    request.setAttribute("MEnvio_noconformidad", rgt_noconforme);
                    request.getRequestDispatcher("Noconforme?opc=4&id=" + idrgtnoconforme + "").forward(request, response);
                    break;
                case 11:
                    idrgtnoconforme = Integer.parseInt(request.getParameter("id"));
                    rgt_noconforme = jpa_noconformidad.modificarEstadoRegistroNoConformidad(idrgtnoconforme, 3);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
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
