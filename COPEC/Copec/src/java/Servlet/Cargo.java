package Servlet;

import Controlador.CargoJpaController;
import Controlador.AreaJpaController;
import Controlador.CapacitacionJpaController;
import Controlador.DocumentoJpaController;
import Controlador.ManualJpaController;

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
            CargoJpaController obj_cargo = new CargoJpaController();
            AreaJpaController obj_area = new AreaJpaController();
            ManualJpaController obj_manual = new ManualJpaController();
            CapacitacionJpaController obj_capacitacion = new CapacitacionJpaController();
            DocumentoJpaController obj_documento = new DocumentoJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int l_id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            boolean registro = true;
            boolean registroCG = false;
            int estado = 0;
            int id_area = 0;
            int id_cargo = 0;
            int opcion = 0;
            int caso = 0;
            int a_version = 0;
            String cargo = "";
            String manual = "";
            String m_version = "";
            String registro_ = "";
            String r_version = "";
            String filtro = "";
            List lst_version = null;
            switch (opc) {
                case 1:
                    request.setAttribute("Cargo", "modulo");
                    request.setAttribute("CargoM", "registrar");
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro == null || filtro.isEmpty()) {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo());
                        request.setAttribute("filtro", filtro);
                    } else {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo_filtro(filtro));
                        request.setAttribute("filtro", filtro);
                    }
                    request.setAttribute("t_lst_area", obj_area.ara_c_area());
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);
                    break;
                case 2:
                    cargo = request.getParameter("txt_cargo").toString().toUpperCase();
                    id_area = Integer.parseInt(request.getParameter("cbx_area").toString().toUpperCase());
//                    manual = request.getParameter("txt_manual").toString().toUpperCase();
//                    m_version = request.getParameter("txt_m_version").toString().toUpperCase();
                    registro_ = request.getParameter("txt_registro").toString().toUpperCase();
                    r_version = request.getParameter("txt_r_version").toString().toUpperCase();
                    registro = obj_cargo.car_r_cargo(cargo, id_area, registro_, r_version);
//                    registro = obj_cargo.car_r_cargo(cargo, id_area, manual, m_version, registro_, r_version);
                    if (registro) {
                        registroCG = obj_cargo.RegistroCriteriosGenerales();
                        request.setAttribute("registro_cargo", registro);
                    } else {
                        request.setAttribute("registro_cargo", registro);
                    }
                    request.setAttribute("Cargo", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo());
                    request.setAttribute("t_lst_area", obj_area.ara_c_area());
                    request.setAttribute("CargoM", "registrar");
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);
                    break;
                case 3:
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    registro = obj_cargo.car_m_estado(id_cargo, estado);
                    caso = Integer.parseInt(request.getParameter("caso").toString());
                    if (registro) {
                        request.setAttribute("estado_cargo", registro);
                    } else {
                        request.setAttribute("estado_cargo", registro);
                    }
                    request.setAttribute("estado", estado);
                    request.setAttribute("Cargo", "modulo");
                    request.setAttribute("CargoM", "registrar");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo());
                    request.setAttribute("t_lst_area", obj_area.ara_c_area());
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);
                    break;
                case 4:
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    if (l_id_area == 12) {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo());
                    } else {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo_area(l_id_area));
                    }
                    request.setAttribute("consultar_cargo_especi", obj_cargo.car_c_cargo_espec(id_cargo));
                    request.setAttribute("t_lst_area", obj_area.ara_c_area());
                    request.setAttribute("CargoM", "modificar");
                    request.setAttribute("Cargo", "modulo");
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);
                    break;
                case 5:
                    opcion = Integer.parseInt(request.getParameter("op").toString());
                    id_cargo = Integer.parseInt(request.getParameter("txt_id").toString().toUpperCase());
                    r_version = request.getParameter("txt_r_version").toString().toUpperCase();
                    if (opcion == 1) {
                        cargo = request.getParameter("txt_cargo").toString().toUpperCase();
                        id_area = Integer.parseInt(request.getParameter("cbx_area").toString().toUpperCase());
                        registro_ = request.getParameter("txt_registro").toString().toUpperCase();
                        registro = obj_cargo.car_m_cargo(id_cargo, cargo, id_area, registro_, r_version);
                        if (registro) {
                            request.setAttribute("modificar_cargo", registro);
                        } else {
                            request.setAttribute("modificar_cargo", registro);
                        }
                    } else {
                        registro = obj_cargo.car_m_cargo_areas(id_cargo, r_version);
                        if (registro) {
                            request.setAttribute("modificar_cargo", registro);
                        } else {
                            request.setAttribute("modificar_cargo", registro);
                        }
                    }
                    request.setAttribute("Cargo", "modulo");
                    request.setAttribute("CargoM", "registrar");
                    request.setAttribute("t_lst_area", obj_area.ara_c_area());
                    if (l_id_area == 12) {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo());
                    } else {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo_area(l_id_area));
                    }
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);

                    break;
                case 6:
                    request.setAttribute("Cargo", "modulo");
                    request.setAttribute("CargoM", "registrar");
                    filtro = request.getParameter("txt_bus").toString();
                    if (filtro == null || filtro.isEmpty()) {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo_area(l_id_area));
                        request.setAttribute("filtro", filtro);
                    } else {
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_cargo_area_filtro(filtro, l_id_area));
                        request.setAttribute("filtro", filtro);
                    }
                    request.setAttribute("t_lst_area", obj_area.ara_c_area());
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);
                    break;
                case 7:
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    request.setAttribute("Cargo", "matriz");
                    request.setAttribute("cabecera_r", obj_cargo.car_c_cargo_espec(id_cargo));
                    request.setAttribute("detalle_r", obj_manual.crm_t_cargo(id_cargo));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.getRequestDispatcher("Cargo.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
