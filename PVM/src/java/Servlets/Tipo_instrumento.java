package Servlets;

import Controladores.TipoInstrumentoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Tipo_instrumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            TipoInstrumentoJpaController jpa_TpInstrumento = new TipoInstrumentoJpaController();
            String nombre_Usuario = sesion.getAttribute("Nombre").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String filtro = "";
            String tipo = "";
            int id_area = 0, id_tipoI = 0, frecuenciaInt = 0, frecuenciaExt = 0, toleranciaInt = 0, toleranciaExt = 0, estado = 0, tipoF = 0, grafica = 0, plantilla = 0;
            boolean resultado = false;
            switch (opc) {
                case 1:
                    try {
                        id_tipoI = Integer.parseInt(request.getParameter("idTI"));
                    } catch (Exception e) {
                        id_tipoI = 0;
                    }
                    request.setAttribute("idTI", id_tipoI);
                    request.getRequestDispatcher("Tipo_instrumento.jsp").forward(request, response);
                    break;
                case 2:
                    tipo = request.getParameter("txt_tipo");
                    id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                    frecuenciaInt = Integer.parseInt(request.getParameter("nmb_freInt"));
                    toleranciaInt = Integer.parseInt(request.getParameter("nmb_tlInt"));
                    frecuenciaExt = Integer.parseInt(request.getParameter("nmb_freExt"));
                    toleranciaExt = Integer.parseInt(request.getParameter("nmb_tlExt"));
                    plantilla = Integer.parseInt(request.getParameter("Cbx_plantilla"));
                    tipoF = Integer.parseInt(request.getParameter("opt_tipo"));
                    grafica = Integer.parseInt(request.getParameter("opt_grafica"));
                    resultado = jpa_TpInstrumento.registroTipoIntrumentos(id_area, tipo, frecuenciaInt, toleranciaInt, frecuenciaExt, toleranciaExt, nombre_Usuario, tipoF, grafica, plantilla);
                    request.setAttribute("Registro_tipo_instrumento", resultado);
                    request.getRequestDispatcher("Tipo_instrumento?opc=1&idTI=" + 0 + "").forward(request, response);
                    break;
                case 3:
                    id_tipoI = Integer.parseInt(request.getParameter("idTI"));
                    tipo = request.getParameter("txt_tipo");
                    id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                    frecuenciaInt = Integer.parseInt(request.getParameter("nmb_freInt"));
                    toleranciaInt = Integer.parseInt(request.getParameter("nmb_tlInt"));
                    frecuenciaExt = Integer.parseInt(request.getParameter("nmb_freExt"));
                    toleranciaExt = Integer.parseInt(request.getParameter("nmb_tlExt"));
                    tipoF = Integer.parseInt(request.getParameter("opt_tipo"));
                    grafica = Integer.parseInt(request.getParameter("opt_grafica"));
                    plantilla = Integer.parseInt(request.getParameter("Cbx_plantilla"));
                    resultado = jpa_TpInstrumento.modificarTipoIntrumento(id_tipoI, id_area, tipo, frecuenciaInt, toleranciaInt, frecuenciaExt, toleranciaExt, tipoF, grafica, plantilla);
                    request.setAttribute("Modificar_tipo_instrumento", resultado);
                    request.getRequestDispatcher("Tipo_instrumento?opc=1&idTI=" + 0 + "").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_tipoI = Integer.parseInt(request.getParameter("idTI"));
                    estado = Integer.parseInt(request.getParameter("est"));
                    resultado = jpa_TpInstrumento.modificarTipoIntrumentoEstado(id_tipoI, estado);
                    request.setAttribute("Estado_tipo_instrumento", resultado);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Tipo_instrumento?opc=1&idTI=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
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
