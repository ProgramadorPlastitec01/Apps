package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.ClisseJpaController;
import java.util.List;

public class Clisse extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession sesion = request.getSession();
            String nombreSession = sesion.getAttribute("Nombre").toString();
            ClisseJpaController jpa_clisse = new ClisseJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String fecha = "", codigo = "", producto = "", observacion = "", ejecutor = "", c_a = "", c_b = "", c_c = "", c_d = "", obs = "", obserF = "", valor = "",
                    verificador = "", letra = "";
            int id_clisse = 0, temp = 0, id = 0, estadoV = 0, id_detalle = 0, estado = 0, contadorInicial = 0, contadorFinal = 0, temp2 = 0;
            boolean resultado = false;
            List lst_clisseId = null, lst_iddetallexID = null, lst_diff;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MÓDULO CLISSE">
                    try {
                        id_clisse = Integer.parseInt(request.getParameter("id_clisse"));
                    } catch (NumberFormatException e) {
                        id_clisse = 0;
                    }
                    try {
                        id_detalle = Integer.parseInt(request.getParameter("id_detalle"));
                    } catch (NumberFormatException e) {
                        id_detalle = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    try {
                        temp2 = Integer.parseInt(request.getParameter("temp2"));
                    } catch (NumberFormatException e) {
                        temp2 = 0;
                    }
                    try {
                        letra = request.getParameter("letra");
                    } catch (Exception e) {
                        letra = "";
                    }
                    if (temp == 0) {
                        request.setAttribute("Clisse", "ModuloConsulta");
                    } else {
                        request.setAttribute("Clisse", "ModuloDetalle");
                    }
                    request.setAttribute("id_clisse", id_clisse);
                    request.setAttribute("id_detalle", id_detalle);
                    request.setAttribute("temp2", temp2);
                    request.setAttribute("letra", letra);
                    request.getRequestDispatcher("Clisse.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO | MODIFICAR CLISSE">
                    try {
                        id_clisse = Integer.parseInt(request.getParameter("id_clisse"));
                    } catch (NumberFormatException e) {
                        id_clisse = 0;
                    }
                    fecha = request.getParameter("txt_fecha");
                    codigo = request.getParameter("txt_codigo");
                    producto = request.getParameter("txt_producto");
                    observacion = request.getParameter("txt_observacion");
                    if (id_clisse > 0) {
                        lst_clisseId = jpa_clisse.Consulta_Clisse_Id(id_clisse);
                        if (lst_clisseId != null) {
                            Object[] obj_clisseId = (Object[]) lst_clisseId.get(0);
                            if (observacion.equals("")) {
                                if (obj_clisseId[11] == null) {
                                    observacion = "";
                                } else {
                                    observacion = obj_clisseId[11].toString();
                                }
                            }
                            if (obs.equals("")) {
                                obserF = observacion;
                            } else {
                                obserF = observacion + "<div>" + obs + "</div>";
                            }
                        }
                        resultado = jpa_clisse.Modificar_Clisse(id_clisse, fecha, codigo, producto, obserF);
                        request.setAttribute("Modificar_clisse", resultado);
                    } else {
                        resultado = jpa_clisse.Registro_Clisse(fecha, codigo, producto, nombreSession, observacion);
                        request.setAttribute("Registro_clisse", resultado);
                    }

                    request.getRequestDispatcher("Clisse?opc=1&id_clisse=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROL">
                    try {
                        id_clisse = Integer.parseInt(request.getParameter("id_clisse"));
                    } catch (NumberFormatException e) {
                        id_clisse = 0;
                    }
                    ejecutor = request.getParameter("txt_ejecutor");
                    letra = request.getParameter("txt_letra");
                    contadorInicial = Integer.parseInt(request.getParameter("contadorInicial"));
                    try {
                        contadorFinal = Integer.parseInt(request.getParameter("contadorFinal"));
                        estadoV = Integer.parseInt(request.getParameter("estadoV"));
                        lst_clisseId = jpa_clisse.Consulta_Clisse_Id(id_clisse);
                        if (lst_clisseId != null) {
                            Object[] obj_clisseId = (Object[]) lst_clisseId.get(0);
                            obs = request.getParameter("txt_observacion");
                            if (obj_clisseId[11] == null) {
                                observacion = "";
                            } else {
                                observacion = obj_clisseId[11].toString();
                            }
                            if (obs.equals("")) {
                                obserF = observacion;
                            } else {
                                obserF = observacion + "<div>" + obs + "</div>";
                            }
                        }
                        for (int i = contadorInicial; i <= contadorFinal; i++) {
                            id = Integer.parseInt(request.getParameter("id" + i));
                            valor = request.getParameter("txt_valor" + i);
                            if (letra.contains("a")) {
                                resultado = jpa_clisse.Registrar_Control(id_clisse, id, 1, valor, nombreSession);
                            } else {
                                lst_iddetallexID = jpa_clisse.Consultar_detalleIdxFila(id_clisse, id);
                                if (lst_iddetallexID != null) {
                                    Object[] obj_xID = (Object[]) lst_iddetallexID.get(0);
                                    int id_detallexId = Integer.parseInt(obj_xID[0].toString());
                                    resultado = jpa_clisse.ActualizarValorXLetra(id_detallexId, letra, valor);
                                }
                            }
                        }
                        if (resultado) {
                            jpa_clisse.Actualizar_Clisse_Control(id_clisse, ejecutor, obserF, estadoV);
                        }
                        lst_diff = jpa_clisse.Consultar_Diff_valores(id_clisse);
                        if (lst_diff != null) {
                            for (int i = 0; i < lst_diff.size(); i++) {
                                Object[] obj_diff = (Object[]) lst_diff.get(i);
                                if (obj_diff[5] != null) {
                                    if (obj_diff[5].equals("F")) {
                                        jpa_clisse.ActualizarEstadoNoCumple(id_clisse);
                                    }
                                }
                            }
                        }
                        request.setAttribute("Registro_clisse", resultado);
                    } catch (Exception e) {
                        request.setAttribute("SinDatosControl", true);
                    }
                    request.getRequestDispatcher("Clisse?opc=1&id_clisse=" + id_clisse + "&temp=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CONTROL CUARENTENA">
                    try {
                        id_clisse = Integer.parseInt(request.getParameter("id_clisse"));
                    } catch (NumberFormatException e) {
                        id_clisse = 0;
                    }
                    try {
                        id_detalle = Integer.parseInt(request.getParameter("id_detalle"));
                    } catch (NumberFormatException e) {
                        id_detalle = 0;
                    }
                    c_a = request.getParameter("txt_a");
                    c_b = request.getParameter("txt_b");
                    c_c = request.getParameter("txt_c");
                    c_d = request.getParameter("txt_d");
                    estadoV = Integer.parseInt(request.getParameter("estadoV"));
                    lst_clisseId = jpa_clisse.Consulta_Clisse_Id(id_clisse);
                    if (lst_clisseId != null) {
                        Object[] obj_clisseId = (Object[]) lst_clisseId.get(0);
                        obs = request.getParameter("txt_observacion");
                        if (obs.equals("")) {
                            observacion = obj_clisseId[11].toString();
                        } else {
                            obserF = observacion += "<p>" + obs + "</p>";
                        }
                    }
                    resultado = jpa_clisse.Registrar_Control_cuaretena(id_detalle, c_a, c_b, c_c, c_d, estadoV);
                    if (resultado) {
                        if (estadoV == 2) {
                            jpa_clisse.Actualizar_Estado(id_clisse, estadoV, 0, obserF);
                        } else {
                            jpa_clisse.Actualizar_Estado(id_clisse, estadoV, 1, obserF);
                        }
                    }
                    request.setAttribute("Registro_cuarentena", resultado);
                    request.getRequestDispatcher("Clisse?opc=1&id_clisse=" + id_clisse + "&temp=1&id_detalle=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="VERIFICAR REGISTRO">
                    try {
                        id_clisse = Integer.parseInt(request.getParameter("id_clisse"));
                    } catch (NumberFormatException e) {
                        id_clisse = 0;
                    }
                    verificador = request.getParameter("txt_verificador").trim();
                    estado = Integer.parseInt(request.getParameter("estado"));
                    resultado = jpa_clisse.Verificar_registro(id_clisse, verificador, estado);
                    request.setAttribute("Verificar_registro", resultado);
                    request.getRequestDispatcher("Clisse?opc=1&id_clisse=" + id_clisse + "&temp=1&id_detalle=0").forward(request, response);
                    //</editor-fold>
                    break;

            }
        } catch (IOException | NumberFormatException | ServletException e) {
            request.getRequestDispatcher("Clisse.jsp").forward(request, response);
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
