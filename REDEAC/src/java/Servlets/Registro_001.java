package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controladoras.Registro_001JpaController;
import SQL.Connection_mysql_sirh;
import Controladoras.RegistroJpaController;
import java.util.List;

public class Registro_001 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            Registro_001JpaController Jpa_registro = new Registro_001JpaController();
            Connection_mysql_sirh jpa_personal = new Connection_mysql_sirh();
            RegistroJpaController jpa_registro = new RegistroJpaController();
            List lst_personal = null;

            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            int id_rol = Integer.parseInt(sesion.getAttribute("Id_rol").toString());
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean result = false;
            int anio = 0, mes = 0, txt_pc = 0, txt_otro = 0, tipo_pc = 0, id_regAct = 0, fto = 0, id_reg, documento = 0, codigo = 0, paradaE = 0, paradaP = 0, id_firma = 0, id_regActividad = 0, tipo_soport = 0;
            int puntuacion = 0;
            String fch_solicitud = "", txt_funcArea = "", fch_solucion = "", fch_ejecucion = "", txt_ejecutor = "", txt_actividad = "", txt_solucion = "", firma_usuario = "";
            String opinion = "";
//</editor-fold>
                switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE REGISTRO 001">
                    try {
                        fto = Integer.parseInt(request.getParameter("fto"));
                    } catch (Exception e) {
                        fto = 0;
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("anio"));
                    } catch (Exception e) {
                        anio = 0;
                    }
                    try {
                        mes = Integer.parseInt(request.getParameter("mes"));
                    } catch (Exception e) {
                        mes = 0;
                    }
                    try {
                        codigo = Integer.parseInt(request.getParameter("txt_codigo"));
                    } catch (Exception e) {
                        codigo = 0;
                    }
                    try {
                        id_regActividad = Integer.parseInt(request.getParameter("id_regActividad"));
                    } catch (Exception e) {
                        id_regActividad = 0;
                    }

                    request.setAttribute("anio", anio);
                    request.setAttribute("mes", mes);
                    request.setAttribute("funcionamiento", fto);
                    request.setAttribute("codigo_usuario", codigo);
                    request.setAttribute("id_regActividad", id_regActividad);
                    request.getRequestDispatcher("Registro_001.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA DE MES Y ANIO">
                    anio = Integer.parseInt(request.getParameter("anio"));
                    mes = Integer.parseInt(request.getParameter("mes"));
                    request.getRequestDispatcher("Registro_001?opc=1&anio=" + anio + "&mes=" + mes + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y EDITAR REGISTROS">
                    try {
                        id_regAct = Integer.parseInt(request.getParameter("id_regAct"));
                    } catch (Exception e) {
                        id_regAct = 0;
                    }
                    try {
                        id_reg = Integer.parseInt(request.getParameter("id_regActividad"));
                    } catch (Exception e) {
                        id_reg = 0;
                    }
                    try {
                        fto = Integer.parseInt(request.getParameter("fto"));
                    } catch (Exception e) {
                        fto = 0;
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("txt_anio"));
                        mes = Integer.parseInt(request.getParameter("txt_mes"));
                    } catch (Exception e) {
                        anio = 0;
                        mes = 0;
                    }

                    if (id_reg < 1) {
                        fch_solicitud = request.getParameter("fch_solicitud");
                        txt_funcArea = request.getParameter("txt_funcArea");
                        
                        fch_solucion = request.getParameter("fch_solucion");
                        fch_ejecucion = request.getParameter("fch_ejecucion");
//                    txt_ejecutor = request.getParameter("txt_ejecutor");
                        String[] txt_act_sol = request.getParameter("txt_act_sol").replace("*", "").replace("ACTIVIDAD:", "").replace("SOLUCION:", "").toString().split("<hr />");
                        txt_actividad = txt_act_sol[0].toString();
                        txt_solucion = txt_act_sol[1].toString();
                        tipo_pc = Integer.parseInt(request.getParameter("tipo"));
                        try {
                            tipo_soport = Integer.parseInt(request.getParameter("txt_tipoSop"));
                        } catch (Exception e) {
                            tipo_soport = 0;
                        }
                        if (tipo_pc == 1) {
                            txt_pc = Integer.parseInt(request.getParameter("txt_pc"));
                            txt_otro = 308;
                        } else if (tipo_pc == 2) {
                            txt_pc = 1;
                            txt_otro = Integer.parseInt(request.getParameter("txt_otro"));
                        }

                        if (id_regAct > 0) {
                            if (fto == 1) {
                                result = Jpa_registro.editar_actividadesDiarias(id_regAct, fch_solicitud, txt_funcArea, fch_ejecucion, txt_pc, txt_otro, txt_actividad, txt_solucion, id_usuario, fch_solucion, tipo_soport);
                                request.setAttribute("Editar_actividad01", result);
                                request.getRequestDispatcher("Registro_001?opc=1&anio=" + anio + "&mes=" + mes + "&fto=0").forward(request, response);
                                break;
                            } else if (fto == 2) {

                            }
                        } else {
                            result = Jpa_registro.Registro_actividadesDiarias(fch_solicitud, txt_funcArea, fch_ejecucion, txt_pc, txt_otro, txt_actividad, txt_solucion, id_usuario, fch_solucion, nombre, tipo_soport);
                            request.setAttribute("Registro_actividad01", result);
                        }
                    } else {
                        request.setAttribute("funcionamiento", fto);
                        request.setAttribute("id_regActividad", id_reg);
                    }
                    request.getRequestDispatcher("Registro_001?opc=1&anio=" + anio + "&mes=" + mes + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="GESTION FIRMAS REGISTROS">
                    try {
                        id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    } catch (Exception e) {
                        id_reg = 0;
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("txt_anio"));
                        mes = Integer.parseInt(request.getParameter("txt_mes"));
                    } catch (Exception e) {
                        anio = 0;
                        mes = 0;
                    }

                    firma_usuario = request.getParameter("txt_firma");
                    try {
                        documento = Integer.parseInt(request.getParameter("txt_documento"));
                    } catch (Exception e) {
                        documento = 0;
                    }
                    codigo = Integer.parseInt(request.getParameter("txt_codigo"));
                    lst_personal = jpa_personal.Empleado_sirh_comparacion(documento);
                    if (lst_personal.size() == 0) {
                        result = true;
                        request.setAttribute("NoExisteUsuario", result);
                        request.setAttribute("NoExisteUsuario_documento", documento);
                        request.setAttribute("firma_usuarios", firma_usuario);
                        fto = 2;
                    } else if (firma_usuario == null) {
                        paradaE = Integer.parseInt(request.getParameter("txt_paradaE"));
                        paradaP = Integer.parseInt(request.getParameter("txt_paradaP"));
                        id_firma = Integer.parseInt(request.getParameter("id_firma"));
                        puntuacion = Integer.parseInt(request.getParameter("star"));
                        opinion = request.getParameter("opinion");
                        result = Jpa_registro.Actualizar_firmasPAradas(id_reg, paradaE, paradaP, codigo, 1,puntuacion, opinion);
                        fto = 0;
                        request.setAttribute("Firma_registrdaActividad", result);
                    } else {
                        result = jpa_registro.Registrar_NuevaFirma_usuario(documento, codigo, firma_usuario);
                        request.setAttribute("RegistrarNuevaFirma", result);
                        request.setAttribute("documento_usuario", documento);
                        request.setAttribute("codigo_usuario", codigo);
                        request.getRequestDispatcher("Registro_001?opc=1&anio=" + anio + "&mes=" + mes + "&fto=2&id_regActividad=" + id_reg + "").forward(request, response);
                        break;
                    }
                    request.getRequestDispatcher("Registro_001?opc=1&anio=" + anio + "&mes=" + mes + "&fto=" + fto + "").forward(request, response);
                    //</editor-fold>
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
