package Servlet;

import Controlador.EmpleadoJpaController;
import Controlador.CargoJpaController;
import Controlador.AreaJpaController;
import Controlador.ManualJpaController;
import Controlador.DocumentoJpaController;

import java.util.Calendar;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Empleado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int l_id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            EmpleadoJpaController obj_empleado = new EmpleadoJpaController();
            CargoJpaController obj_cargo = new CargoJpaController();
            AreaJpaController obj_area = new AreaJpaController();
            ManualJpaController obj_manual = new ManualJpaController();
            DocumentoJpaController obj_documento = new DocumentoJpaController();
            Calendar DataFech = Calendar.getInstance();
            int yearAct = DataFech.getWeekYear();
            boolean registro = true;
            int id_cargo = 0;
            int id_area = 0;
            int id_empleado = 0;
            int estado = 0;
            int hdd_activo = 1;
            
            int anioIni = 0, anioFin = 0;
            
            String nombre = "";
            String apellido = "";
            String identificacion = "";
            String codigo = "";
            String buscar = "";
            List consultaE = null;
            switch (opc) {
                case 1:
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("Empleadom", "registro");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 1));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    apellido = request.getParameter("txt_apellido").toString().toUpperCase();
                    identificacion = request.getParameter("txt_documento").toString().toUpperCase();
                    codigo = request.getParameter("txt_codigo").toString().toUpperCase();
                    id_cargo = Integer.parseInt(request.getParameter("cbx_cargo").toString());
                    id_area = Integer.parseInt(request.getParameter("id_area").toString());
//                    if(!nombre.isEmpty() || !apellido.isEmpty() || identificacion.)
                    consultaE = obj_empleado.epd_c_empleado_documento(identificacion);
                    if (consultaE == null) {
                        registro = obj_empleado.epd_r_empleado(nombre, apellido, identificacion, codigo, id_cargo, hdd_activo);
                        if (registro) {
                            request.setAttribute("registro_empleado", registro);
                        } else {
                            request.setAttribute("registro_empleado", registro);
                        }
                    } else {
                        request.setAttribute("registro_empleado", registro);
                    }
                    request.setAttribute("Empleadom", "registro");
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 1));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 3:
                    id_area = Integer.parseInt(request.getParameter("id_area").toString());
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                    try {
                        anioIni = Integer.parseInt(request.getParameter("anioIni").toString());
                        anioFin = Integer.parseInt(request.getParameter("anioFin").toString());
                    } catch (Exception e) {
                        anioIni = yearAct - 1;
                        anioFin = yearAct;
                    }
                    if (obj_empleado.crm_c_r_cabecera(id_cargo, id_empleado) != null) {
                        request.setAttribute("Empleado", "registroM");
                        request.setAttribute("cabecera_r", obj_empleado.crm_c_r_cabecera(id_cargo, id_empleado));
                        request.setAttribute("Cargo_matriz", id_cargo);
                        request.setAttribute("detalle_r", obj_manual.crm_t_cargo(id_cargo));
                        request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                        request.setAttribute("anioIni", anioIni);
                        request.setAttribute("anioFin", anioFin);
                        request.setAttribute("id_area", id_area);
                        request.setAttribute("id_empleado", id_empleado);
                        request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    } else {
                        request.setAttribute("Empleado", "modulo");
                        request.setAttribute("Empleadom", "registro");
                        request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                        request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 1));
                        request.setAttribute("id_area", l_id_area);
                        request.setAttribute("estado", 1);
                        request.setAttribute("anioIni", anioIni);
                        request.setAttribute("anioFin", anioFin);
                        request.setAttribute("id_area", id_area);
                        request.setAttribute("id_empleado", id_empleado);
                        request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    }
                    break;
                case 4:
                    id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("Empleadom", "modificar");
                    request.setAttribute("traer_empleado", obj_empleado.epd_t_empleado(id_empleado));
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 1));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 5:
                    id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                    id_area = Integer.parseInt(request.getParameter("id_area").toString());
                    nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                    apellido = request.getParameter("txt_apellido").toString().toUpperCase();
                    identificacion = request.getParameter("txt_documento").toString().toUpperCase();
                    codigo = request.getParameter("txt_codigo").toString().toUpperCase();
                    id_cargo = Integer.parseInt(request.getParameter("cbx_cargo").toString());
                    registro = obj_empleado.epd_m_empleado(id_empleado, nombre, apellido, identificacion, codigo, id_cargo);
                    if (registro) {
                        request.setAttribute("modificar_empleado", registro);
                    } else {
                        request.setAttribute("modificar_empleado", registro);
                    }
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("Empleadom", "registrar");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 1));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 6:
                    id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                    id_area = Integer.parseInt(request.getParameter("id_area").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    registro = obj_empleado.epd_m_estado(id_empleado, estado);
                    if (registro) {
                        request.setAttribute("estado_personal", registro);
                    } else {
                        request.setAttribute("estado_personal", registro);
                    }
                    request.setAttribute("estadop", estado);
                    request.setAttribute("Empleadom", "registro");
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 1));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 7:
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("Empleadom", "registrar");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_c_id_area(l_id_area, 0));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 0);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 8:
                    buscar = request.getParameter("txt_buscar").toString();
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("Empleadom", "registrar");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_f_empleado(buscar, l_id_area));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 9:
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 1);
                    int opcion = Integer.parseInt(request.getParameter("esta").toString());
                    if (opcion == 1) {
                        request.setAttribute("consultar_empleado", obj_empleado.epd_c_empleado_traslado());
                        request.setAttribute("Empleado", "Translado");
                    } else if (opcion == 2) {
                        request.setAttribute("consultar_empleado", obj_empleado.epd_c_empleado_traslado());
                        int area_cargo = Integer.parseInt(request.getParameter("idU").toString());
                        id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                        request.setAttribute("id_area_cargo", area_cargo);
                        request.setAttribute("Empleado", "Translado_Aprobado_Cargo");
                        request.setAttribute("traer_empleado", obj_empleado.epd_t_empleado(id_empleado));
                    } else if (opcion == 3) {
                        id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                        nombre = request.getParameter("txt_nombre").toString().toUpperCase();
                        apellido = request.getParameter("txt_apellido").toString().toUpperCase();
                        identificacion = request.getParameter("txt_documento").toString().toUpperCase();
                        codigo = request.getParameter("txt_codigo").toString().toUpperCase();
                        id_cargo = Integer.parseInt(request.getParameter("txt_cargo").toString());
                        registro = obj_empleado.epd_t_cargo(id_empleado, nombre, apellido, identificacion, codigo, id_cargo);
                        if (registro) {
                            request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                            request.setAttribute("consultar_empleado", obj_empleado.epd_c_empleado_traslado());
                            request.setAttribute("id_area", l_id_area);
                            request.setAttribute("estado", 1);
                            request.setAttribute("Empleado", "Translado");
                        } else {
                            request.getRequestDispatcher("Login.jsp").forward(request, response);
                        }
                    } else if (opcion == 4) {
                        buscar = request.getParameter("txt_buscar").toString();
                        request.setAttribute("consultar_empleado", obj_empleado.epd_b_empleado_traslado(buscar));
                        request.setAttribute("Empleado", "Translado");
                    } else {
                        id_empleado = Integer.parseInt(request.getParameter("id_empleado").toString());
                        request.setAttribute("consultar_empleado", obj_empleado.epd_c_empleado_traslado());
                        request.setAttribute("Empleado", "Translado_Aprobado");
                        request.setAttribute("traer_empleado", obj_empleado.epd_t_empleado(id_empleado));
                    }
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
                case 10:
                    buscar = request.getParameter("txt_buscar").toString();
                    request.setAttribute("Empleado", "modulo");
                    request.setAttribute("Empleadom", "registrar");
                    request.setAttribute("consultar_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_empleado", obj_empleado.epd_f_empleado_inactivo(buscar, l_id_area));
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("estado", 0);
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Empleado.jsp").forward(request, response);
                    break;
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
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
