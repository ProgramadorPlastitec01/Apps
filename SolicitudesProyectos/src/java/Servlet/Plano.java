package Servlet;

import Controladores.PlanoJpaController;
import Controladores.VerificarEtdJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Plano extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            PlanoJpaController jpa_plano = new PlanoJpaController();
            VerificarEtdJpaController jpa_itemVer = new VerificarEtdJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            String tipo = "", num_plano = "", let_plano = "", plano = "", fecha = "";
            int id_plano = 0;
            switch (opc) {
                case 1:
                    try {
                        id_plano = Integer.parseInt(request.getParameter("idP"));
                    } catch (Exception e) {
                        id_plano = 0;
                    }
                    request.setAttribute("id_plano", id_plano);
                    request.getRequestDispatcher("Plano.jsp").forward(request, response);
                    break;
                case 2:
                    fecha = request.getParameter("txt_fecha");
                    tipo = request.getParameter("slc_tipo");
                    let_plano = request.getParameter("txt_letraP");
                    num_plano = request.getParameter("txt_numeroP");
                    plano = let_plano + "-" + num_plano;
                    resultado = jpa_plano.registroPlano(tipo, plano, fecha);
                    request.setAttribute("Registro_Plano", resultado);
                    request.getRequestDispatcher("Plano?opc=1").forward(request, response);
                    break;
                case 3:
                    id_plano = Integer.parseInt(request.getParameter("idP"));
                    tipo = request.getParameter("slc_tipo");
                    let_plano = request.getParameter("txt_letraP");
                    num_plano = request.getParameter("txt_numeroP");
                    plano = let_plano + "-" + num_plano;
                    resultado = jpa_plano.modificarPlano(id_plano, tipo, plano);
                    request.setAttribute("Modificar_Plano", resultado);
                    request.getRequestDispatcher("Plano?opc=1&idP=0").forward(request, response);
                    break;
                case 4:
                    id_plano = Integer.parseInt(request.getParameter("idP"));
                    int num_items = Integer.parseInt(request.getParameter("numI"));
                    plano = request.getParameter("nomP");
                    List lst_items = jpa_itemVer.consultaItemsVerificacion();
                    for (int i = num_items; i < lst_items.size(); i++) {
                        Object[] obj_items = (Object[]) lst_items.get(i);
                        String cumple = request.getParameter("slc_cumple_" + i);
                        String aplica = request.getParameter("slc_aplica_" + i);
                        resultado = jpa_plano.registroCalificacionPlano(id_plano, (Integer) obj_items[0], plano, obj_items[1].toString(), obj_items[2].toString(), cumple, aplica);
                    }
                    request.setAttribute("ActualizarListaPlano", resultado);
                    request.getRequestDispatcher("Plano?opc=1&idP=0").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Plano.jsp").forward(request, response);
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
