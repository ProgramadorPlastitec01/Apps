package Servlets;

import Controladoras.ActividadJpaController;
import Controladoras.FormularioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Formulario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            FormularioJpaController jpa_formulario = new FormularioJpaController();
            ActividadJpaController jpa_actividad = new ActividadJpaController();
            List pos = null;
            List formularios = null;
            List formulario = null;
            int idCargo = 0;
            int idFormulario = 0;
            int posicionF = 0, posicionI = 0;
            int estado = 0;
            int count = 0;
            String campo = "";
            String valor = "";
            String valorR = "";
            String tipo = "";
            String responsable = "";
            boolean resultado = false;
            if (opc <= 5) {
                switch (opc) {
                    case 1:
                        idFormulario = Integer.parseInt(request.getParameter("idF").toString());
                        if (idFormulario == 0) {
                            idCargo = Integer.parseInt(request.getParameter("idC").toString());
                            request.setAttribute("id_Cargo", idCargo);
                            pos = jpa_formulario.ConsultaPosicionCargo(idCargo);
                            request.setAttribute("Posicion", pos);
                            formularios = jpa_formulario.ConsultaFormularioPorCargo(idCargo);
                            request.setAttribute("ConsultaForm", formularios);
                        } else {
                            request.setAttribute("consultaformularioM", jpa_formulario.ConsultaCampoFormularioPorId(idFormulario));
                            idCargo = Integer.parseInt(request.getParameter("idC").toString());
                            request.setAttribute("id_Cargo", idCargo);
                            pos = jpa_formulario.ConsultaPosicionCargo(idCargo);
                            request.setAttribute("Posicion", pos);
                            formularios = jpa_formulario.ConsultaFormularioPorCargo(idCargo);
                            request.setAttribute("ConsultaForm", formularios);
                        }
                        request.getRequestDispatcher("formulario.jsp").forward(request, response);
                        break;
                    case 2:
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        responsable = request.getParameter("txt_registro").toString();
                        campo = request.getParameter("txt_nombreC").toString();
                        tipo = request.getParameter("txt_tipo").toString();
                        valor = request.getParameter("txt_datos").toString();
                        valorR = request.getParameter("txt_datosR").toString();
                        if (tipo.equals("N/A")) {
                            resultado = false;
                            request.setAttribute("Resultado_FormularioSelect", resultado);
                        } else {
                            if (valor.equals("")) {
                                valor = "N/A";
                            }
                            posicionF = Integer.parseInt(request.getParameter("posC").toString());
                            if (posicionF == 0) {
                                posicionF = 1;
                            } else {
                                posicionF = posicionF + 1;
                            }
                            if (posicionF <= 9) {
                                if (valorR.equals("")) {
                                    jpa_formulario.RegistroFormulario(idCargo, responsable, campo, tipo, valor, posicionF);
                                } else {
                                    jpa_formulario.RegistroFormulario(idCargo, responsable, campo, tipo, valorR, posicionF);
                                }
                            } else {
                                resultado = true;
                                request.setAttribute("Resultado_FormularioSelect", resultado);
                            }
                        }
                        boolean editText = false;
                        formulario = jpa_formulario.ConsultaFormularioPorCargo(idCargo);
                        for (int i = 0; i < formulario.size(); i++) {
                            Object[] obj_formulario = (Object[]) formulario.get(i);
                            if (obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) {
                                editText = true;
                            }
                            if (i == (formulario.size() - 1)) {
                                idFormulario = Integer.parseInt(obj_formulario[0].toString());
                            }
                        }
                        if (editText) {
                            if (tipo.equals("Campo editorTexto") || tipo.equals("Campo archivo")) {
                                request.getRequestDispatcher("Formulario?op=1&idC=" + idCargo + "&idF=" + 0 + "").forward(request, response);
                            } else {
                                request.getRequestDispatcher("Formulario?op=4&idC=" + idCargo + "&idF=" + idFormulario + "&pos=" + posicionF + "&ubicacion=" + (posicionF - 1) + "").forward(request, response);
                            }
                        } else {
                            request.getRequestDispatcher("Formulario?op=1&idC=" + idCargo + "&idF=" + 0 + "").forward(request, response);
                        }
                        break;
                    case 3:
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idFormulario = Integer.parseInt(request.getParameter("idF").toString());
                        estado = Integer.parseInt(request.getParameter("est").toString());
                        resultado = jpa_formulario.ModificarEstadoFormulario(idFormulario, estado);
                        if (resultado) {
                            request.setAttribute("Resultado_FormularioE", resultado);
                            request.setAttribute("estado", estado);
                        } else {
                            request.setAttribute("Resultado_FormularioE", resultado);
                        }
                        request.getRequestDispatcher("Formulario?op=1&idC=" + idCargo + "&idF=" + 0 + "").forward(request, response);
                        break;
                    case 4:
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idFormulario = Integer.parseInt(request.getParameter("idF").toString());
                        posicionI = Integer.parseInt(request.getParameter("pos").toString());
                        posicionF = Integer.parseInt(request.getParameter("ubicacion").toString());
                        jpa_formulario.ModificarPosicionFormulario(idFormulario, posicionF);
                        formulario = jpa_formulario.ConsultaFormularioPorCargo(idCargo);
                        if (posicionF < posicionI) {
                            count = posicionF;
                            for (int j = (posicionF - 1); j < formulario.size(); j++) {
                                Object[] obj_formularioMin = (Object[]) formulario.get(j);
                                if (idFormulario != (Integer) obj_formularioMin[0]) {
                                    count++;
                                    jpa_formulario.ModificarPosicionFormulario((Integer) obj_formularioMin[0], count);
                                }
                            }
                        } else {
                            count = 0;
                            for (int k = 0; k < posicionF; k++) {
                                Object[] obj_formularioMax = (Object[]) formulario.get(k);
                                if (idFormulario != (Integer) obj_formularioMax[0]) {
                                    count++;
                                    jpa_formulario.ModificarPosicionFormulario((Integer) obj_formularioMax[0], count);
                                }
                            }
                        }
                        request.getRequestDispatcher("Formulario?op=1&idC=" + idCargo + "&idF=" + 0 + "").forward(request, response);
                        break;
                    case 5:
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idFormulario = Integer.parseInt(request.getParameter("idF").toString());
                        campo = request.getParameter("txt_nombreC").toString();
                        tipo = request.getParameter("txt_tipoM").toString();
                        valor = request.getParameter("txt_datos").toString();
                        valorR = request.getParameter("txt_datosR").toString();
                        if (valor.equals("")) {
                            if (valorR.equals("")) {
                                valor = "N/A";
                            } else {
                                valor = valorR;
                            }
                        }
                        resultado = jpa_formulario.ModificarFormulario(idFormulario, campo, tipo, valor);
                        if (resultado) {
                            request.setAttribute("Resultado_FormularioM", resultado);
                        } else {
                            request.setAttribute("Resultado_FormularioM", resultado);
                        }
                        request.getRequestDispatcher("Formulario?op=1&idC=" + idCargo + "&idF=" + 0 + "").forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
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
