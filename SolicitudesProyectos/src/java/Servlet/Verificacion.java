package Servlet;

import Controladores.CabeceraEtdJpaController;
import Controladores.ElectrodoJpaController;
import Controladores.VerificarEtdJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

public class Verificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            CabeceraEtdJpaController jpa_verificacion = new CabeceraEtdJpaController();
            ElectrodoJpaController jpa_electrodo = new ElectrodoJpaController();
            VerificarEtdJpaController jpa_itemsV = new VerificarEtdJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String usuario = sesion.getAttribute("Nombre").toString();
            Calendar calendario = Calendar.getInstance();
            int anioC = calendario.get(Calendar.YEAR);
            boolean resultado = false;
            int id_verificacion = 0, id_plano = 0, id_electrodo = 0, id_pieza = 0, anio = 0, val = 0, IdEstandar = 0;
            String numeroS = "", pieza = "", fecha = "", cumple = "", aplica = "", observacion = "";
            List lst_verificacion = null;
            List lst_electrodo = null;
            List lst_itemsV = null;
            List lst_relacionV = null;
            switch (opc) {
                case 1:
                    try {
                        id_pieza = Integer.parseInt(request.getParameter("id_pieza"));
                    } catch (NumberFormatException e) {
                        id_pieza = 0;
                    }
                    try {
                        id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    } catch (NumberFormatException e) {
                        id_verificacion = 0;
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("anio"));
                        if (anio == 0) {
                            val = 0;
                            anio = anioC;
                        } else {
                            val = 1;
                        }
                    } catch (NumberFormatException e) {
                        anio = anioC;
                        val = 1;
                    }
                    request.setAttribute("id_pieza", id_pieza);
                    request.setAttribute("id_verificacion", id_verificacion);
                    request.setAttribute("anio", anio);
                    request.setAttribute("val", val);
                                        request.setAttribute("IdEstandar", IdEstandar);
                    request.getRequestDispatcher("Verificacion.jsp").forward(request, response);
                    break;
                case 2:
                    id_plano = Integer.parseInt(request.getParameter("idP"));
                    numeroS = request.getParameter("numS");
                    pieza = request.getParameter("pieza");
                    lst_verificacion = jpa_verificacion.consultarVerificacionPieza(numeroS, pieza);
                    if (lst_verificacion != null) {
                        Object[] obj_verificacion = (Object[]) lst_verificacion.get(0);
                        request.getRequestDispatcher("Verificacion?opc=1&idV=" + obj_verificacion[0] + "").forward(request, response);
                    } else {
                        lst_electrodo = jpa_electrodo.consultaElectrodosIdPlano(pieza, id_plano);
                        if (lst_electrodo == null) {
                            jpa_electrodo.registroElectrodo(id_plano, pieza, "N/A", "N/A");
                            request.getRequestDispatcher("Verificacion?opc=1").forward(request, response);
                        } else {
                            Object[] obj_electrodo = (Object[]) lst_electrodo.get(0);
                            id_electrodo = (Integer) obj_electrodo[0];
                            request.setAttribute("id_plano", id_plano);
                            request.setAttribute("numero_solicitud", numeroS);
                            request.setAttribute("id_pieza", id_electrodo);
                            request.getRequestDispatcher("Verificacion?opc=1&id_plano=" + id_plano + "&numero_solicitud=" + numeroS + "&id_pieza=" + id_electrodo + "").forward(request, response);
                        }
                    }
                    break;
                case 3:
                    lst_itemsV = jpa_itemsV.consultaItemsVerificacion();
                    numeroS = request.getParameter("txt_numS");
                    pieza = request.getParameter("pieza").trim();
                    fecha = request.getParameter("txt_fecha");
                    id_pieza = Integer.parseInt(request.getParameter("idPz"));
                    resultado = jpa_verificacion.registroVerificacionPieza(fecha, numeroS, usuario, id_pieza);
                    lst_verificacion = jpa_verificacion.consultarVerificacionPieza(numeroS, pieza);
                    if (lst_verificacion != null) {
                        Object[] obj_verificacion = (Object[]) lst_verificacion.get(0);
                        for (int i = 0; i < lst_itemsV.size(); i++) {
                            Object[] obj_items = (Object[]) lst_itemsV.get(i);
                            jpa_verificacion.registroRelacion(Integer.parseInt(obj_verificacion[0].toString()), Integer.parseInt(obj_items[0].toString()));
                        }
                        lst_relacionV = jpa_verificacion.consultaRelacionVerificacionId(Integer.parseInt(obj_verificacion[0].toString()));
                        int cont = 0;
                        for (int i = 0; i < lst_itemsV.size(); i++) {
                            Object[] obj_relacion = (Object[]) lst_relacionV.get(cont);
                            Object[] obj_items = (Object[]) lst_itemsV.get(i);
                            cumple = request.getParameter("txtcumple_" + i);
                            aplica = request.getParameter("txtaplica_" + i);
                            observacion = request.getParameter("txtobservaciones_" + i);
                            if (observacion.equals("")) {
                                observacion = "N/A";
                            }
                            jpa_verificacion.registroCalificacion(Integer.parseInt(obj_relacion[0].toString()), cumple, aplica, observacion);
                            cont++;
                        }
                        request.setAttribute("RegistroVerificacion", resultado);
                        request.getRequestDispatcher("Verificacion?opc=1&idV=" + obj_verificacion[0] + "").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Verificacion?opc=1").forward(request, response);
                    }
                    break;
                case 4:
                    try {
                        id_pieza = Integer.parseInt(request.getParameter("id_pieza"));
                    } catch (NumberFormatException e) {
                        id_pieza = 0;
                    }
                    try {
                        id_plano = Integer.parseInt(request.getParameter("idP"));
                    } catch (NumberFormatException e) {
                        id_plano = 0;
                    }
                    try {
                        id_verificacion = Integer.parseInt(request.getParameter("idV"));
                    } catch (NumberFormatException e) {
                        id_verificacion = 0;
                    }
                    try {
                        IdEstandar = Integer.parseInt(request.getParameter("IdEstandar"));
                    } catch (NumberFormatException e) {
                        IdEstandar = 0;
                    }
                    try {
                        numeroS = request.getParameter("numS");
                    } catch (Exception e) {
                        numeroS = "";
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("anio"));
                        if (anio == 0) {
                            val = 0;
                            anio = anioC;
                        } else {
                            val = 1;
                        }
                    } catch (NumberFormatException e) {
                        anio = anioC;
                        val = 1;
                    }
                    request.setAttribute("id_pieza", id_pieza);
                    request.setAttribute("id_plano", id_plano);
                    request.setAttribute("numero_solicitud", numeroS);
                    request.setAttribute("IdEstandar", IdEstandar);
                    request.setAttribute("id_verificacion", id_verificacion);
                    request.setAttribute("anio", anio);
                    request.setAttribute("val", val);
                    request.getRequestDispatcher("Verificacion.jsp").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Verificacion.jsp").forward(request, response);
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
