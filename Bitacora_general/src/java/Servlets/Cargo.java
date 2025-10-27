package Servlets;

import Controladoras.AreaJpaController;
import Controladoras.CargoJpaController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Cargo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            AreaJpaController jpa_area = new AreaJpaController();
            CargoJpaController jpa_cargo = new CargoJpaController();
            boolean resultado = false;
            int idArea = 0;
            int idCargo = 0;
            int email = 0;
            int estado = 0;
            String filtro = "";
            String responsableR = "";
            String cargo = "";
            String nomRegistro = "";
            String codRegistro = "";
            String version = "";
            int actividadP = 0;
            int NotasP = 0;
            if (opc <= 10) {
                switch (opc) {
                    case 1:
                        filtro = request.getParameter("txt_bus");
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idArea = Integer.parseInt(request.getParameter("txt_area").toString());
                        request.setAttribute("sigla", jpa_area.ConsultaAreaPorId(idArea));
                        request.getRequestDispatcher("Cargo?op=3&idC=" + idCargo + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                    case 2:
                        idArea = Integer.parseInt(request.getParameter("txt_area").toString());
                        responsableR = request.getParameter("txt_registro");
                        cargo = request.getParameter("txt_cargo");
                        nomRegistro = request.getParameter("txt_nomRegistro");
                        codRegistro = request.getParameter("txt_codigo");
                        version = request.getParameter("txt_version");
                        email = Integer.parseInt(request.getParameter("rd_correo").toString());
                        resultado = jpa_cargo.RegistroCargo(idArea, responsableR, cargo, nomRegistro, codRegistro, version, email);
                        List lst_area = jpa_area.ConsultaAreaPorId(idArea);
                        Object[] obj_area = (Object[]) lst_area.get(0);
                        try {
                            File directorio = new File("\\\\172.16.2.117\\d\\Sistemas de informacion\\Bitacora_general\\Archivos_adjuntos\\"+obj_area[3]+"");
                            directorio.mkdirs();
                        } catch (Exception e) {
                        }
                        if (resultado) {
                            request.setAttribute("Resultado_Cargo", resultado);
                        } else {
                            request.setAttribute("Resultado_Cargo", resultado);
                        }
                        request.getRequestDispatcher("Cargo?op=3&idC=" + 0 + "&txt_bus=").forward(request, response);
                        break;
                    case 3:
                        filtro = request.getParameter("txt_bus");
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        if (filtro == null || filtro.isEmpty()) {
                            if (idCargo == 0) {
                                request.setAttribute("consultaCargo", jpa_cargo.ConsultaCargos());
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("ModificarCargo", jpa_cargo.ConsultaCargosPorId(idCargo));
                                request.setAttribute("consultaCargo", jpa_cargo.ConsultaCargos());
                                request.setAttribute("filtro", filtro);
                            }
                        } else {
                            if (idCargo == 0) {
                                request.setAttribute("consultaCargo", jpa_cargo.ConsultaFiltroCargos(filtro));
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("ModificarCargo", jpa_cargo.ConsultaCargosPorId(idCargo));
                                request.setAttribute("consultaCargo", jpa_cargo.ConsultaFiltroCargos(filtro));
                                request.setAttribute("filtro", filtro);
                            }
                        }
                        request.getRequestDispatcher("cargo.jsp").forward(request, response);
                        break;
                    case 4:
                        filtro = request.getParameter("txt_bus");
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        idArea = Integer.parseInt(request.getParameter("txt_area").toString());
                        responsableR = request.getParameter("txt_registroM");
                        cargo = request.getParameter("txt_cargoM");
                        nomRegistro = request.getParameter("txt_nomRegistroM");
                        codRegistro = request.getParameter("txt_codigoM");
                        version = request.getParameter("txt_versionM");
                        email = Integer.parseInt(request.getParameter("rd_correoM").toString());
                        resultado = jpa_cargo.ModificarCargo(idCargo, idArea, responsableR, cargo, nomRegistro, codRegistro, version, email);
                        if (resultado) {
                            request.setAttribute("Resultado_CargoM", resultado);
                        } else {
                            request.setAttribute("Resultado_CargoM", resultado);
                        }
                        request.getRequestDispatcher("Cargo?op=3&idC=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                    case 5:
                        filtro = request.getParameter("txt_bus");
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        actividadP = Integer.parseInt(request.getParameter("rdo_atd").toString());
                        NotasP = Integer.parseInt(request.getParameter("rdo_nta").toString());
                        resultado = jpa_cargo.PermisosCargo(idCargo, actividadP, NotasP);
                        if (resultado) {
                            request.setAttribute("Resultado_CargoPer", resultado);
                        } else {
                            request.setAttribute("Resultado_CargoPer", resultado);
                        }
                        request.getRequestDispatcher("Cargo?op=3&idC=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                    case 6:
                        filtro = request.getParameter("txt_bus");
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        estado = Integer.parseInt(request.getParameter("est").toString());
                        resultado = jpa_cargo.ModificarEstadoCargo(idCargo, estado);
                        if (resultado) {
                            request.setAttribute("Resultado_CargoE", resultado);
                            request.setAttribute("estado", estado);
                        } else {
                            request.setAttribute("Resultado_CargoE", resultado);
                            request.setAttribute("estado", estado);
                        }
                        request.getRequestDispatcher("Cargo?op=3&idC=" + 0 + "&txt_bus=" + filtro + "").forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            out.close();
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
