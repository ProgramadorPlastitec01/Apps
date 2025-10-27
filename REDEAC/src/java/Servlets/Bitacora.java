package Servlets;

import Controladoras.BitacoraJpaController;
import Controladoras.UsuarioJpaController;
import Mails.Email;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Bitacora extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            BitacoraJpaController jpa_bitacora = new BitacoraJpaController();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            Email mail = new Email();
            String modulo = "", fechaI = "", fechaF = "", horaI = "", horaF = "", asunto = "", turno = "";
            int id_usuarioB = 0, id_bitacora = 0, cantAct = 0, cantActRpd = 0, cantCas = 0, cantCasPnt = 0, cantPndSld = 0, cantPnd = 0, cantMvm = 0, cantAcD = 0;
            switch (opc) {
                case 1:
                    try {
                        id_usuarioB = Integer.parseInt(request.getParameter("idU"));
                    } catch (Exception e) {
                        id_usuarioB = 0;
                    }
                    try {
                        id_bitacora = Integer.parseInt(request.getParameter("idB"));
                    } catch (Exception e) {
                        id_bitacora = 0;
                    }
                    modulo = request.getParameter("mod");
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("id_usuarioB", id_usuarioB);
                    request.setAttribute("id_bitacora", id_bitacora);
                    request.getRequestDispatcher("Bitacora.jsp").forward(request, response);
                    break;
                case 2:
                    fechaI = request.getParameter("txt_fechaI");
                    fechaF = request.getParameter("txt_fechaF");
                    horaI = request.getParameter("txt_horaI");
                    horaF = request.getParameter("txt_horaF");
                    request.setAttribute("fecha_inicial", fechaI + " " + horaI);
                    request.setAttribute("fecha_final", fechaF + " " + horaF);
                    request.getRequestDispatcher("Bitacora?opc=1&mod=BG").forward(request, response);
                    break;
                case 3:
                    fechaI = request.getParameter("txt_fechaI");
                    fechaF = request.getParameter("txt_fechaF");
                    asunto = request.getParameter("txt_asunto");
                    turno = request.getParameter("slc_turno");
                    cantAct = Integer.parseInt(request.getParameter("txt_actividades"));
                    cantActRpd = Integer.parseInt(request.getParameter("txt_actividadesR"));
                    cantCas = Integer.parseInt(request.getParameter("txt_casos"));
                    cantPndSld = Integer.parseInt(request.getParameter("txt_pendientesS"));
                    cantMvm = Integer.parseInt(request.getParameter("txt_movimientos"));
                    cantCasPnt = Integer.parseInt(request.getParameter("txt_casosP"));
                    cantPnd = Integer.parseInt(request.getParameter("txt_pendientes"));
                    cantAcD = Integer.parseInt(request.getParameter("txt_AcDiarias"));
                    resultado = jpa_bitacora.registroBitacora(asunto, turno, fechaI, fechaF, cantAct, cantActRpd, cantCas, cantCasPnt, cantPndSld, cantPnd, cantAcD, cantMvm, id_usuario);
                    if (resultado) {
                        mail.EnviarBitacora(id_usuario, turno, fechaI, fechaF);
                        jpa_bitacora.modificarEstadoActividadBitacora(id_usuario, fechaI, fechaF);
                        jpa_bitacora.modificarEstadoActividadesReportadasBitacora(id_usuario, fechaI, fechaF);
                        jpa_bitacora.modificarEstadoCasosBitacora(id_usuario, fechaI, fechaF);
                        jpa_bitacora.modificarEstadoPendientesBitacora(id_usuario, fechaI, fechaF);
                    }
                    request.setAttribute("Registro_bitacora", resultado);
                    jpa_usuario.establecerTecnicoTurno(id_usuario, 0);
                    request.getRequestDispatcher("Bitacora?opc=1&mod=BC").forward(request, response);
                    break;
                case 4:
                    id_bitacora = Integer.parseInt(request.getParameter("idB"));
                    id_usuarioB = Integer.parseInt(request.getParameter("idU"));
                    resultado = jpa_bitacora.revizarBitacora(id_bitacora);
                    request.setAttribute("Revizar_bitacora", resultado);
                    request.getRequestDispatcher("Bitacora?opc=1&mod=BCU&idU=" + id_usuarioB + "").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
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
