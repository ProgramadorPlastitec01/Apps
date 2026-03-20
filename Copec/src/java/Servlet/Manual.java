package Servlet;

import Email.Email;
import Controlador.ManualJpaController;
import Controlador.AreaJpaController;
import Controlador.CapacitacionJpaController;
import Controlador.CargoJpaController;
import Controlador.CargoManualJpaController;
import Controlador.DocumentoJpaController;
import Email.Connection_mysql_daruma;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Manual extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int l_id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            String l_capacitador = sesion.getAttribute("l_capacitador").toString();
            ManualJpaController obj_manual = new ManualJpaController();
            AreaJpaController obj_area = new AreaJpaController();
            CargoJpaController obj_cargo = new CargoJpaController();
            DocumentoJpaController obj_documento = new DocumentoJpaController();
            CapacitacionJpaController obj_capacitacion = new CapacitacionJpaController();
            CargoManualJpaController obj_solicitud = new CargoManualJpaController();
            Email correo = new Email();
            boolean registro = true;
            List consulta = null;
            int id_area = 0;
            int id_cargo = 0;
            int id_manual = 0;
            int opcion = 0;
            int tipo_doc = 0;
            String arr_cargo = "";
            String siglatura = "";
            String tipo = "";
            String numerico = "";
            String codigo = "";
            String estado = "";
            String documento = "";
            String version = "";
            String aversion = "";
            int ant_version = 0;
            String justificacion = "";
            String nom_car = "";
            String numero = "";
            int id_cargo_manual = 0;
            List lst_cargo_manual = null;
            List lst_version = null;
            List lst_documentoD = null;
            switch (opc) {
                case 1:
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", null);
                    request.setAttribute("consultar_l_cargo", null);
                    request.setAttribute("consultar_l_cargo_a", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", 0);
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 2:
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString().toUpperCase());
                    if (tipo_doc == 1) {
                        request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                        request.setAttribute("consultar_l_cargo", null);
                        request.setAttribute("consultar_l_cargo_a", null);
                    } else {
                        request.setAttribute("consultar_l_area", null);
                        request.setAttribute("consultar_l_cargo", obj_cargo.car_c_id_area(l_id_area));
                        request.setAttribute("consultar_l_cargo_a", obj_area.ara_c_area());
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", tipo_doc);
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 3:
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString().toUpperCase());
                    if (tipo_doc == 1) {
                        siglatura = request.getParameter("cbx_siglatura").toString().toUpperCase();
                        request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                        request.setAttribute("consultar_l_cargo", null);
                        request.setAttribute("consultar_l_cargo_a", null);
                        request.setAttribute("id_area", l_id_area);
                    } else {
                        arr_cargo = request.getParameter("cbx_cargo").toString();
                        String Datos[] = arr_cargo.split(":");
                        id_cargo = Integer.parseInt(Datos[0].toString());
                        siglatura = Datos[1].toString();
                        request.setAttribute("consultar_l_area", null);
                        request.setAttribute("consultar_l_cargo", obj_cargo.car_c_id_area(l_id_area));
                        request.setAttribute("consultar_l_cargo_a", obj_area.ara_c_area());
                        request.setAttribute("id_area", 0);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", tipo_doc);
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 4:
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString().toUpperCase());
                    siglatura = request.getParameter("siglatura").toString();
                    request.setAttribute("Manual", "movit");
                    request.setAttribute("tipo_doc", tipo_doc);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("consultar_movit", obj_manual.lmn_c_l_manual(id_manual));
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 5:
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    codigo = request.getParameter("codigo").toString();
                    documento = request.getParameter("documento").toString();
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString());
                    version = request.getParameter("m_version").toString();
                    aversion = request.getParameter("txt_a_version").toString();
                    siglatura = request.getParameter("siglatura").toString();
                    request.setAttribute("Manual", "version");
                    request.setAttribute("id_manual", id_manual);
                    request.setAttribute("codigo", codigo);
                    request.setAttribute("documento", documento);
                    request.setAttribute("tipo_doc", tipo_doc);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("version", version);
                    request.setAttribute("aversion", aversion);
                    request.setAttribute("l_capacitador", l_capacitador);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 6:
                    version = request.getParameter("version").toString();
                    justificacion = request.getParameter("txt_justificacion").toString();
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString());
                    siglatura = request.getParameter("siglatura").toString();
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    if (!justificacion.isEmpty()) {
                        registro = obj_manual.lmn_r_l_manual(version, justificacion, l_capacitador, id_manual);
                        obj_manual.mnl_m_version(version, id_manual);
                        if (registro) {
                            if (tipo_doc == 1) {
                                try {
                                    ant_version = Integer.parseInt(request.getParameter("ant_version").toString());
                                    if (ant_version < Integer.parseInt(version)) {
                                        lst_version = obj_manual.mnl_c_version_matrices("(" + id_manual + ")");
                                        if (lst_version != null) {
                                            for (int i = 0; i < lst_version.size(); i++) {
                                                Object[] obj_version = (Object[]) lst_version.get(i);
                                                obj_capacitacion.cpc_m_cerrar(Integer.parseInt(obj_version[1].toString()));
                                                //nuevo cargar advertencia de cerrada la capacitacion por cambio de documento
                                                List lst_cam_contenido = obj_capacitacion.cpc_t_id_capacitacion(Integer.parseInt(obj_version[1].toString()));
                                                Object[] obj_capaci_cam = (Object[]) lst_cam_contenido.get(0);
                                                String cont_nuevo = obj_capaci_cam[2].toString() + " (/////)";
                                                obj_capacitacion.cpc_m_contenido(cont_nuevo, Integer.parseInt(obj_version[1].toString()));
                                            }
                                        }
                                    }
                                    List lst_areas = obj_solicitud.crm_c_areas_email(id_manual);
                                    if (lst_areas == null) {
                                        request.setAttribute("existencia", 13);
                                    } else {
                                        correo.mail_cambio_version_documento(id_manual, l_capacitador,Integer.parseInt(version));
                                        request.setAttribute("existencia", 14);
                                    }
                                } catch (MessagingException ex) {
                                    Logger.getLogger(Manual.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                request.setAttribute("existencia", 15);
                            }
                        } else {
                            request.setAttribute("existencia", 15);
                        }
                    } else {
                        request.setAttribute("existencia", 16);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                    request.setAttribute("consultar_l_cargo", null);
                    request.setAttribute("consultar_l_cargo_a", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", tipo_doc);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 7:
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    siglatura = request.getParameter("siglatura").toString();
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString());
                    registro = obj_manual.v_arm_r_area_manual(l_id_area, id_manual);
                    if (registro) {
                        if (tipo_doc == 1) {
                            request.setAttribute("Manual", "modulo");
                            request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                            request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                            request.setAttribute("consultar_l_cargo", null);
                            request.setAttribute("consultar_l_cargo_a", null);
                            request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                            request.setAttribute("id_area", l_id_area);
                            request.setAttribute("id_cargo", id_cargo);
                            request.setAttribute("siglatura", siglatura);
                            request.setAttribute("tipo_doc", tipo_doc);
                            request.setAttribute("existencia", 0);
                            request.getRequestDispatcher("Manual.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Login.jsp").forward(request, response);
                        }
                    } else {
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                    }
                    break;
                case 8:
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    siglatura = request.getParameter("siglatura").toString();
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString());
                    id_cargo = Integer.parseInt(request.getParameter("id_cargo").toString());
                    id_cargo_manual = Integer.parseInt(request.getParameter("id_cargo_manual").toString());
                    if (id_cargo_manual > 0) {
                        estado = request.getParameter("estado").toString();
                        lst_cargo_manual = obj_manual.crm_l_cargo_manual(id_cargo_manual);
                        Object[] obj_cargo_manual_c = (Object[]) lst_cargo_manual.get(0);
                        if (Integer.parseInt(obj_cargo_manual_c[1].toString()) == 0) {
                            obj_manual.v_crm_e_cargo_manual(id_cargo_manual, "d", estado);
                        } else {
                            obj_manual.v_crm_e_cargo_manual(id_cargo_manual, "e", estado);
                        }
                    } else {
                        obj_manual.v_crm_r_cargo_manual(id_cargo, id_manual);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", null);
                    request.setAttribute("consultar_l_cargo", obj_cargo.car_c_id_area(l_id_area));
                    request.setAttribute("consultar_l_cargo_a", obj_area.ara_c_area());
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", 0);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", tipo_doc);
                    request.setAttribute("existencia", 0);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 9:
                    version = request.getParameter("txt_ver").toString().toUpperCase();
                    tipo = request.getParameter("cbx_doc").toString().toUpperCase();
                    siglatura = request.getParameter("rfc").toString().toUpperCase();
                    numerico = request.getParameter("txt_numerico").toString().toUpperCase();
                    documento = request.getParameter("txt_manual").toString().toUpperCase();
                    if (tipo != null && siglatura != null && numerico != null && documento != null && version != null) {
                        codigo = (tipo + "-" + siglatura + "-" + numerico).toString().toUpperCase();
                        if (codigo.length() >= 6) {
                            consulta = obj_manual.mnl_c_manual_busqueda(codigo);
                            if (consulta == null) {
                                registro = obj_manual.mnl_r_manual("" + tipo + "-" + "" + siglatura + "-" + numerico + "", documento, version, tipo);
                                if (registro) {
                                    request.setAttribute("existencia", 1);
                                } else {
                                    request.setAttribute("existencia", 2);
                                }
                            } else {
                                request.setAttribute("existencia", 3);
                            }
                        } else {
                            request.setAttribute("existencia", 2);
                        }
                    } else {
                        request.setAttribute("existencia", 2);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", null);
                    request.setAttribute("consultar_l_cargo", null);
                    request.setAttribute("consultar_l_cargo_a", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", 0);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 10:
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    siglatura = request.getParameter("siglatura").toString();
                    documento = request.getParameter("txt_documento").toString().toUpperCase();
                    if (!documento.isEmpty()) {
                        registro = obj_manual.mnl_m_documento(documento, id_manual);
                        if (registro) {
                            request.setAttribute("existencia", 7);
                        } else {
                            request.setAttribute("existencia", 8);
                        }
                    } else {
                        request.setAttribute("existencia", 9);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                    request.setAttribute("consultar_l_cargo", null);
                    request.setAttribute("consultar_l_cargo_a", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", 1);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 11:
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    siglatura = request.getParameter("siglatura").toString();
                    numero = request.getParameter("numerico_d").toString();
                    if (!numero.isEmpty()) {
                        registro = obj_manual.mnl_m_codigo("" + request.getParameter("tipo_d").toString() + "-" + request.getParameter("area_d").toString() + "-" + request.getParameter("numerico_d").toString() + "", id_manual);
                        if (registro) {
                            request.setAttribute("existencia", 10);
                        } else {
                            request.setAttribute("existencia", 11);
                        }
                    } else {
                        request.setAttribute("existencia", 12);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                    request.setAttribute("consultar_l_cargo", null);
                    request.setAttribute("consultar_l_cargo_a", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", 1);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
                    break;
                case 12:
                    version = request.getParameter("txt_version").toString();
                    tipo_doc = Integer.parseInt(request.getParameter("tipo_doc").toString().toUpperCase());
                    siglatura = request.getParameter("cbx_siglatura").toString().toUpperCase();
                    id_manual = Integer.parseInt(request.getParameter("id_manual").toString());
                    opcion = Integer.parseInt(request.getParameter("op").toString());
                    estado = request.getParameter("estado").toString();
                    if (opcion == 1) {
                        registro = obj_manual.mnl_u_manual_estado(id_manual, estado);
                        request.setAttribute("existencia", 6);
                    } else {
                        registro = obj_manual.mnl_u_manual_estado(id_manual, estado);
                        try {
                            List lst_areas = obj_solicitud.crm_c_areas_email(id_manual);
                            if (lst_areas == null) {
                                request.setAttribute("existencia", 4);
                            } else {
                                correo.mail_inactivar_documento(id_manual, l_capacitador,Integer.parseInt(version));
                                request.setAttribute("existencia", 5);
                            }
                        } catch (MessagingException ex) {
                            Logger.getLogger(Manual.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        registro = obj_manual.mnl_u_manual_estado_cargoManual(id_manual, estado);
                    }
                    request.setAttribute("Manual", "modulo");
                    request.setAttribute("consultar_f_area", obj_area.ara_t_id_area(l_id_area));
                    request.setAttribute("consultar_l_area", obj_area.ara_c_area());
                    request.setAttribute("consultar_l_cargo", null);
                    request.setAttribute("consultar_l_cargo_a", null);
                    request.setAttribute("consultar_documento", obj_documento.dcm_c_documento());
                    request.setAttribute("id_area", l_id_area);
                    request.setAttribute("id_cargo", id_cargo);
                    request.setAttribute("siglatura", siglatura);
                    request.setAttribute("tipo_doc", 0);
                    request.getRequestDispatcher("Manual.jsp").forward(request, response);
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
