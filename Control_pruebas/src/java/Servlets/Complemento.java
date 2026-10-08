package Servlets;

import Controladores.LineaJpaController;
import Controladores.ProcesoJpaController;
import Controladores.PruebaJpaController;
import Controladores.VariableJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complemento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            LineaJpaController jpaclna = new LineaJpaController();
            PruebaJpaController jpacpeb = new PruebaJpaController();
            ProcesoJpaController jpacpcs = new ProcesoJpaController();
            VariableJpaController jpacvra = new VariableJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            List lst_lineas = null;
            List lst_prueba = null;
            String tipo = "", nombre = "", frecuencia = "", tipos_linea = "";
            String variables = "";
            int id_tipo_linea = 0;
            int id_linea = 0;
            int id_prueba = 0;
            int id_proceso = 0;
            int contador = 0;
            int cantidad = 0;
            int tipo_estado = 0;
            int codigo_serial = 0;
            switch (opc) {
                case 1:
                    tipo = "Registro_linea";
                    lst_lineas = jpaclna.Lineas();
                    request.setAttribute("Complemento", tipo);
                    if (lst_lineas == null) {
                        request.setAttribute("Lista_lineas", null);
                    } else {
                        request.setAttribute("Lista_lineas", lst_lineas);
                    }
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("Txt_nombre");
                    id_tipo_linea = Integer.parseInt(request.getParameter("Cbx_tipo_linea").toString());
                    codigo_serial = Integer.parseInt(request.getParameter("Txt_codigo").toString());
                    proceso = jpaclna.Registrar_linea(nombre, id_tipo_linea, codigo_serial, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_linea");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_linea");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    }
                    break;
                case 3:
                    id_linea = Integer.parseInt(request.getParameter("Id_linea").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpaclna.Activar_linea(id_linea);
                    } else {
                        jpaclna.Desactivar_linea(id_linea);
                    }
                    request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    break;
                case 4:
                    tipo = "Registro_pruebas";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 5:
                    nombre = request.getParameter("Txt_prueba");
                    frecuencia = request.getParameter("Cbx_frecuencia");
                    id_proceso = Integer.parseInt(request.getParameter("Cbx_proceso"));
                    cantidad = Integer.parseInt(request.getParameter("Cantidad_tipos_linea").toString());
                    String vector_tipos_linea[] = new String[cantidad];
                    for (int i = 0; i < cantidad; i++) {
                        vector_tipos_linea[i] = request.getParameter("Ckb_tipo_linea[" + i + "]");
                        if (vector_tipos_linea[i] != null) {
                            if (contador == 0) {
                                tipos_linea = vector_tipos_linea[i].toString();
                                contador++;
                            } else {
                                tipos_linea = tipos_linea + "," + vector_tipos_linea[i].toString();
                            }
                        }
                    }
                    lst_prueba = jpacpeb.Prueba_repetida(nombre, id_proceso);
                    if (lst_prueba != null) {
                        request.setAttribute("Alerta", "Error_prueba_existencia");
                        request.setAttribute("var1", nombre);
                    } else {
                        proceso = jpacpeb.Registrar_prueba(nombre, frecuencia, id_proceso, tipos_linea, sesion.getAttribute("Rol/Nombres").toString());
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_prueba");
                            request.setAttribute("var1", nombre);
                        } else {
                            request.setAttribute("Alerta", "Error_prueba");
                            request.setAttribute("var1", nombre);
                        }
                    }
                    request.getRequestDispatcher("Complemento?opc=4").forward(request, response);
                    break;
                case 6:
                    id_prueba = Integer.parseInt(request.getParameter("Id_prueba").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacpeb.Activar_prueba(id_prueba);
                    } else {
                        proceso = jpacpeb.Desactivar_prueba(id_prueba);
                    }
                    request.getRequestDispatcher("Complemento?opc=4").forward(request, response);
                    break;
                case 7:
                    tipo = "Registro_proceso";
                    request.setAttribute("Complemento", tipo);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 8:
                    nombre = request.getParameter("Txt_nombre").toString();
//                    cantidad = Integer.parseInt(request.getParameter("Cantidad_variables").toString());
//                    String vector_variables[] = new String[cantidad];
//                    for (int i = 0; i < cantidad; i++) {
//                        vector_variables[i] = request.getParameter("Rdb_variable[" + i + "]");
//                        if (vector_variables[i] != null) {
//                            if (contador == 0) {
//                                variables = vector_variables[i].toString();
//                                contador++;
//                            } else {
//                                variables = variables + "," + vector_variables[i].toString();
//                            }
//                        }
//                    }
//                    if (variables == null ? "" == null : variables.equals("")) {
//                        variables = "N/A";
//                    }
                    variables = request.getParameter("Rdb_variable");
                    proceso = jpacpcs.Registrar_proceso(nombre, variables, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_proceso");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_proceso");
                        request.setAttribute("var1", nombre);
                    }
                    request.getRequestDispatcher("Complemento?opc=7").forward(request, response);
                    break;
                case 9:
                    id_proceso = Integer.parseInt(request.getParameter("Id_proceso").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        proceso = jpacpcs.Activar_proceso(id_proceso);
                    } else {
                        proceso = jpacpcs.Desactivar_proceso(id_proceso);
                    }
                    request.getRequestDispatcher("Complemento?opc=7").forward(request, response);
                    break;
                case 10:
                    nombre = request.getParameter("Txt_new_variable");
                    if (nombre.length() > 0) {
                        proceso = jpacvra.Registrar_varivable(nombre);
                    } else {
                        proceso = false;
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_variable");
                        request.setAttribute("var1", nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_variable");
                        request.setAttribute("var1", nombre);
                    }
                    request.getRequestDispatcher("Complemento?opc=7").forward(request, response);
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
