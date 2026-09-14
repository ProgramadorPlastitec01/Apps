package Servlets;

import Controladores.MateriaPrimaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Materia_prima extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            MateriaPrimaJpaController jpacmpm = new MateriaPrimaJpaController();
            //VARIABLES GLOBALES
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String tipo = "";
            String filtro = "";
            String nombre = "";
            String equivalente = "";
            int tipo_estado = 0;
            int posicion = 0;
            int id_materia = 0;
            List lst_materia_prima = null;
            switch (opc) {
                case 1:
                    tipo = "Registro";
                    filtro = request.getParameter("fto");
                    id_materia = Integer.parseInt(request.getParameter("imp"));
                    if (id_materia == 0) {
                        request.setAttribute("Materia_prima", tipo);
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            request.setAttribute("Filtro", "");
                        } else {
                            request.setAttribute("Filtro", filtro);
                        }
                        request.setAttribute("Id_materia_prima", id_materia);
                        request.getRequestDispatcher("Materia_prima.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Materia_prima", tipo);
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            request.setAttribute("Filtro", "");
                        } else {
                            request.setAttribute("Filtro", filtro);
                        }
                        request.setAttribute("Id_materia_prima", id_materia);
                        request.getRequestDispatcher("Materia_prima.jsp").forward(request, response);
                    }
                    break;
                case 2:
                    nombre = request.getParameter("Txt_nombre");
                    proceso = jpacmpm.Registrar_materia_prima(nombre, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_materia");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_materia");
                        request.setAttribute("var1", nombre);
                    }
                    request.getRequestDispatcher("Materia_prima?opc=1&fto=&imp=0").forward(request, response);
                    break;
                case 3:
                    id_materia = Integer.parseInt(request.getParameter("Id_materia").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacmpm.Activar_materia_prima(id_materia);
                    } else {
                        proceso = jpacmpm.Desactivar_materia_prima(id_materia);
                    }
                    request.getRequestDispatcher("Materia_prima?opc=1&fto=&imp=0").forward(request, response);
                    break;
                case 4:
                    id_materia = Integer.parseInt(request.getParameter("Id_materia").toString());
                    nombre = request.getParameter("Cbx_mp_equivalente");
                    lst_materia_prima = jpacmpm.Traer_materia_prima_id(id_materia);
                    Object[] obj_materia_prima = (Object[]) lst_materia_prima.get(0);
                    if (obj_materia_prima[2].toString() == null ? "" == null : obj_materia_prima[2].toString().equals("")) {
                        equivalente = nombre;
                    } else {
                        equivalente = obj_materia_prima[2].toString() + "-" + nombre;
                    }
                    proceso = jpacmpm.Registrar_materia_prima_equivalente(id_materia, equivalente);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_materia_equivalente");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_materia_equivalente");
                        request.setAttribute("var1", nombre);
                    }
                    request.getRequestDispatcher("Materia_prima?opc=1&fto=&imp=" + id_materia).forward(request, response);
                    break;
                case 5:
                    id_materia = Integer.parseInt(request.getParameter("Id_materia").toString());
                    nombre = request.getParameter("Txt_mp_equivalente");
                    lst_materia_prima = jpacmpm.Traer_materia_prima_id(id_materia);
                    Object[] obj_materia_prima_eliminar = (Object[]) lst_materia_prima.get(0);
                    String vector_equivalentes[] = obj_materia_prima_eliminar[2].toString().split("-");
                    for (int i = 0; i < vector_equivalentes.length; i++) {
                        if (vector_equivalentes[i].toString().equals(nombre)) {
                            equivalente = equivalente + "";
                        } else {
                            if (i == 0) {
                                equivalente = vector_equivalentes[i];
                            } else {
                                equivalente = equivalente + "-" + vector_equivalentes[i];
                            }
                        }
                    }
                    proceso = jpacmpm.Registrar_materia_prima_equivalente(id_materia, equivalente);
                    if (proceso) {
                        request.setAttribute("Alerta", "Quitar_materia_equivalente");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_materia_equivalente");
                        request.setAttribute("var1", nombre);
                    }
                    jpacmpm.Limpiar_mp_equivalentes();
                    request.getRequestDispatcher("Materia_prima?opc=1&fto=&imp=" + id_materia).forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
