package Servlets;

import Controladores_BD.MenuJpaController;
import Controladores_BD.PersonalJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import GLPI.GLPIClient;

import Metodos.ConnectionSignature;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Personal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int mnu = 0;
            boolean proceso = true;
            boolean proceso2 = true;
            PersonalJpaController jpacpsn = new PersonalJpaController();
            MenuJpaController jpacmnu = new MenuJpaController();
            ConnectionSignature ConnSigna = new ConnectionSignature();
            GLPIClient Glpi_Opc = new GLPIClient();
            String usuario_registro = "";
            try {
                usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            } catch (Exception e) {
                usuario_registro = "Sin Session";
            }
            String consulta = "";
            String documento = "";
            String codigo = "";
            String especialidad = "";
            String nombres = "";
            String apellidos = "";
            String genero = "";
            String filtro = "";
            int tipo_firma = 0;
            String filtro_abc = "";
            String fecha_nacimiento = "";
            int id_area = 0;
            int anio = 0;
            int modulo = 0;
            int anio_inicio = 0;
            int anio_fin = 0;
            int estado_reg = 0;
            int reintegro = 0;
            int id_cargo = 0;
            String fecha_ingreso = "";
            String salario = "";
            String tipo_contrato = "";
            String fecha_contrato = "";
            String estado = "";
            String telefono_fijo = "";
            String telefono_movil = "";
            String telefono_movil2 = "";
            String numero_hijos = "";
            String brigadista = "";
            String correo = "";
            String contacto_urgencias = "";
            String grupo_sanguineo = "";
            String nivel_educativo = "";
            String restriccion_fisica = "";
            String restriccion_medica = "";
            String firma = "";
            String sindicalizado = "";
            String localidad = "";
            List lst_empleado = null;
            switch (opc) {
                //Presentar formulario registro personal
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    try {
                        documento = request.getParameter("Txt_documento");
                    } catch (Exception e) {
                        documento = "";
                    }
                    request.setAttribute("Personal", "Registrar");
                    request.setAttribute("Documento", documento);
                    request.getRequestDispatcher("Personal.jsp").forward(request, response);
                    break;
                //Registrar empleado
                case 2:
                    documento = request.getParameter("Txt_documento");
                    nombres = request.getParameter("Txt_nombres");
                    apellidos = request.getParameter("Txt_apellidos");
                    genero = request.getParameter("Rdb_genero");
                    fecha_nacimiento = request.getParameter("Txt_nacimiento");
                    codigo = request.getParameter("Txt_codigo");
                    id_cargo = Integer.parseInt(request.getParameter("Cbx_cargo"));
                    especialidad = request.getParameter("Txt_especialidad");
                    fecha_ingreso = request.getParameter("Txt_fecha_ingreso");
                    salario = request.getParameter("Txt_salario");
                    tipo_contrato = request.getParameter("Rdb_contrato");
                    fecha_contrato = request.getParameter("Txt_fecha_contrato");
                    estado = request.getParameter("Rdb_estado");
                    correo = request.getParameter("Txt_correo");
                    telefono_fijo = request.getParameter("Txt_fijo");
                    telefono_movil = request.getParameter("Txt_movil");
                    telefono_movil2 = request.getParameter("Txt_movil2");
                    numero_hijos = request.getParameter("Txt_hijos");
                    brigadista = request.getParameter("Rdb_brigadista");
                    grupo_sanguineo = request.getParameter("Rdb_grupo_sanguineo");
                    nivel_educativo = request.getParameter("Rdb_nivel_educativo");
                    localidad = request.getParameter("Txt_localidad");
                    contacto_urgencias = request.getParameter("Txt_contacto") + "-" + request.getParameter("Txt_num_contacto");
                    restriccion_fisica = request.getParameter("Txt_descripcion").split("<hr />")[0];
                    restriccion_medica = request.getParameter("Txt_descripcion").split("<hr />")[1];
                    proceso = jpacpsn.Registrar_empleado(documento, nombres, apellidos, genero, fecha_nacimiento, codigo, especialidad);
                    if (proceso) {
                        proceso = jpacpsn.Registrar_datos_empleado(documento, id_cargo, fecha_ingreso, salario, tipo_contrato, estado, correo, telefono_fijo + "-" + telefono_movil + "-" + telefono_movil2, numero_hijos, brigadista, usuario_registro, grupo_sanguineo, contacto_urgencias, restriccion_fisica, restriccion_medica, nivel_educativo, fecha_contrato, localidad);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_empleado");
                            request.setAttribute("var1", nombres + " " + apellidos);
                            String res = Glpi_Opc.crearUsuario(codigo, nombres, apellidos, "2026");

                            //<editor-fold defaultstate="collapsed" desc="REGISTER USER GLPI">
                            int idGLPI = 0;
                            try {
                                Pattern pattern = Pattern.compile("\"id\":(\\d+)");
                                Matcher matcher = pattern.matcher(res);
                                if (matcher.find()) {
                                    idGLPI = Integer.parseInt(matcher.group(1));
                                }
                                jpacpsn.Registrar_id_glpi(Integer.parseInt(documento), idGLPI);
                            } catch (Exception e) {
                            }
                            System.out.print(res);
                            //</editor-fold>
                            
                            request.getRequestDispatcher("Personal?opc=4&mnu=22&abc=" + apellidos.charAt(0) + "&Txt_documento=").forward(request, response);

                        } else {
                            request.setAttribute("Alerta", "Error_empleado");
                            request.setAttribute("var1", nombres + " " + apellidos);
                            request.getRequestDispatcher("Personal?opc=1&mnu=21&Txt_documento=" + documento).forward(request, response);
                        }
                    } else {
                        request.getRequestDispatcher("Personal?opc=1&mnu=21&Txt_documento=" + documento).forward(request, response);
                    }
                    break;
                //Modificar usuarios
                case 3:
                    documento = request.getParameter("Txt_documento");
                    nombres = request.getParameter("Txt_nombres");
                    apellidos = request.getParameter("Txt_apellidos");
                    genero = request.getParameter("Rdb_genero");
                    fecha_nacimiento = request.getParameter("Txt_nacimiento");
                    codigo = request.getParameter("Txt_codigo");
                    id_cargo = Integer.parseInt(request.getParameter("Cbx_cargo"));
                    especialidad = request.getParameter("Txt_especialidad");
                    fecha_ingreso = request.getParameter("Txt_fecha_ingreso");
                    salario = request.getParameter("Txt_salario");
                    tipo_contrato = request.getParameter("Rdb_contrato");
                    fecha_contrato = request.getParameter("Txt_fecha_contrato");
                    estado = request.getParameter("Rdb_estado");
                    correo = request.getParameter("Txt_correo");
                    telefono_fijo = request.getParameter("Txt_fijo");
                    telefono_movil = request.getParameter("Txt_movil");
                    telefono_movil2 = request.getParameter("Txt_movil2");
                    numero_hijos = request.getParameter("Txt_hijos");
                    brigadista = request.getParameter("Rdb_brigadista");
                    grupo_sanguineo = request.getParameter("Rdb_grupo_sanguineo");
                    nivel_educativo = request.getParameter("Rdb_nivel_educativo");
                    localidad = request.getParameter("Txt_localidad");
                    contacto_urgencias = request.getParameter("Txt_contacto") + "-" + request.getParameter("Txt_num_contacto");
                    restriccion_fisica = request.getParameter("Txt_descripcion").split("<hr />")[0];
                    restriccion_medica = request.getParameter("Txt_descripcion").split("<hr />")[1];
                    proceso2 = jpacpsn.Modificar_empleado(documento, nombres, apellidos, genero, fecha_nacimiento, codigo, especialidad);
                    proceso = jpacpsn.Inactivar_datos_old_empleado(documento);
                    if (proceso) {
                        jpacpsn.Modificar_especialidad(documento, especialidad, codigo);
                        proceso = jpacpsn.Registrar_datos_empleado(documento, id_cargo, fecha_ingreso, salario, tipo_contrato, estado, correo, telefono_fijo + "-" + telefono_movil + "-" + telefono_movil2, numero_hijos, brigadista, usuario_registro, grupo_sanguineo, contacto_urgencias, restriccion_fisica, restriccion_medica, nivel_educativo, fecha_contrato, localidad);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_empleado");
                            request.setAttribute("var1", nombres + " " + apellidos);

                            //<editor-fold defaultstate="collapsed" desc="EDITAR GLPI">
                            int idGLPI = 0;
                            try {
                                List personaldata = jpacpsn.ConsultarIdGLPI(documento);
                                if (personaldata != null) {
                                    Object[] ObjPersonal = (Object[]) personaldata.get(0);
                                    idGLPI = Integer.parseInt(ObjPersonal[1].toString());
                                    String res = Glpi_Opc.editarUsuario(idGLPI, nombres, apellidos);
                                    res = res.replaceAll("\\D+", "");
                                    System.out.print(res);
                                }
                            } catch (Exception e) {
                            }
                            //</editor-fold>

                        } else {
                            request.setAttribute("Alerta", "Error_modificar_empleado");
                            request.setAttribute("var1", nombres + " " + apellidos);
                        }
                    }
                    request.getRequestDispatcher("Personal?opc=4&mnu=22&abc=" + apellidos.charAt(0) + "&Txt_documento=").forward(request, response);
                    break;
                //Consultar usuarios
                case 4:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        filtro_abc = request.getParameter("abc");
                    } catch (Exception e) {
                        filtro_abc = "A";
                    }
                    try {
                        estado_reg = Integer.parseInt(request.getParameter("etd"));
                    } catch (Exception e) {
                        estado_reg = 1;
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Personal", "Consulta");
                    request.setAttribute("Filtro_abc", filtro_abc);
                    request.setAttribute("Estado_reg", estado_reg);
                    request.getRequestDispatcher("Personal.jsp").forward(request, response);
                    break;
                //añadir a  sesion personal
                case 5:
                    documento = request.getParameter("Txt_documento");
                    filtro_abc = request.getParameter("abc");
                    try {
                        consulta = request.getSession().getAttribute("Consulta").toString();
                        String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                        if (arg_consulta.length < 5 && !consulta.contains("[" + documento + "]")) {
                            consulta = consulta + "[" + documento + "]";
                            sesion.setAttribute("Consulta", consulta);
                        } else if (arg_consulta.length == 5) {
                            if (!consulta.contains("[" + documento + "]")) {
                                consulta = "[" + arg_consulta[1] + "][" + arg_consulta[2] + "][" + arg_consulta[3] + "][" + arg_consulta[4] + "][" + documento + "]";
                            }
                            sesion.setAttribute("Consulta", consulta);
                        }
                    } catch (Exception e) {
                        consulta = "[" + documento + "]";
                        sesion.setAttribute("Consulta", consulta);
                    }
                    request.getRequestDispatcher("Personal?opc=4&mnu=22&abc=" + filtro_abc).forward(request, response);
                    break;
                //retirar de sesion personal
                case 6:
                    documento = request.getParameter("Txt_documento");
                    try {
                        consulta = request.getSession().getAttribute("Consulta").toString();
                        consulta = consulta.replace("[" + documento + "]", "");
                        sesion.setAttribute("Consulta", consulta);
                    } catch (Exception e) {
                        sesion.setAttribute("Consulta", consulta);
                    }
                    request.getRequestDispatcher("Personal?opc=4&mnu=22").forward(request, response);
                    break;
                //modulo modificar empleado
                case 7:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        reintegro = Integer.parseInt(request.getParameter("rit"));
                    } catch (Exception e) {
                        reintegro = 0;
                    }
                    try {
                        String TypeMov = request.getParameter("typeMov");
                        if (TypeMov != null) {
                            request.setAttribute("Personal", TypeMov);
                        } else {
                            request.setAttribute("Personal", "Modificar");
                        }
                    } catch (Exception e) {
                        request.setAttribute("Personal", "Modificar");
                    }

                    request.setAttribute("Permisos", mnu);
                    documento = request.getParameter("dcm");

                    request.setAttribute("Documento", documento);
                    request.setAttribute("Reintegro", reintegro);
                    request.getRequestDispatcher("Personal.jsp").forward(request, response);
                    break;
                //modulo detalle empleado
                case 8:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    try {
                        anio_inicio = Integer.parseInt(request.getParameter("Txt_anio_ini"));
                    } catch (Exception e) {
                        anio_inicio = 0;
                    }
                    try {
                        anio_fin = Integer.parseInt(request.getParameter("Txt_anio_fin"));
                    } catch (Exception e) {
                        anio_fin = 0;
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("Cbx_modulo"));
                    } catch (Exception e) {
                        modulo = 0;
                    }
                    documento = request.getParameter("dcm");
                    request.setAttribute("Personal", "Detalle_empleado");
                    request.setAttribute("Documento", documento);
                    request.setAttribute("Anio_inicio", anio_inicio);
                    request.setAttribute("Anio_fin", anio_fin);
                    request.setAttribute("Modulo", modulo);
                    request.getRequestDispatcher("Personal.jsp").forward(request, response);
                    break;
                //Firma electronica empleado
                case 9:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    documento = request.getParameter("dcm");
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Personal", "Firma_electronica");
                    request.setAttribute("Documento", documento);
                    request.getRequestDispatcher("Personal.jsp").forward(request, response);
                    break;
                case 10:
                    documento = request.getParameter("dcm");
                    codigo = request.getParameter("cdg");
                    filtro_abc = request.getParameter("abc");
                    tipo_firma = Integer.parseInt(request.getParameter("Rdb_tipo_firma"));
                    firma = request.getParameter("Txt_firma");
                    int event = 0,
                     idCap = 0,
                     idCapdt = 0;
                    event = Integer.parseInt(request.getParameter("event"));
                    try {
                        idCap = Integer.parseInt(request.getParameter("icp"));
                        idCapdt = Integer.parseInt(request.getParameter("idCapDetalle"));
                    } catch (Exception e) {
                        event = 0;
                    }
                    if (tipo_firma == 0) {
//                        ConnSigna.ActualizarFirmas(Long.parseLong(documento), Long.parseLong(codigo));
//                        jpacmnu.Cambiar_estados_firma(Long.parseLong(documento), Long.parseLong(codigo));
//                        jpacmnu.Registrar_firma(Long.parseLong(documento), Integer.parseInt(codigo), firma);
                        ConnSigna.RegistrarFirmas(Long.parseLong(documento), Long.parseLong(codigo), firma);
                        request.setAttribute("Alerta", "Registro_firmas");
                        request.setAttribute("var1", documento);
                    } else {
//                        jpacmnu.Actualizar_firma(Long.parseLong(documento), Integer.parseInt(codigo), firma);
                        ConnSigna.ActualizarFirmas(Long.parseLong(documento), Integer.parseInt(codigo), firma);
                        request.setAttribute("Alerta", "Actualizacion_firmas");
                        request.setAttribute("var1", documento);
                    }
                    if (event == 1) {
                        request.getRequestDispatcher("Seguimiento?opc=35&icp=" + idCap + "&idCapDetalle=" + idCapdt + "&txtDocument=" + documento + "&txtCode=" + codigo + "").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&idCapd=" + idCapdt + "&fml=3&icp=" + idCap + "&DocUSer=" + documento + "&CodUser=" + codigo + "").forward(request, response);
                    }
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="Guardar Sindicalizado">
                    documento = request.getParameter("dcm");
                    sindicalizado = request.getParameter("sdcl");
                    jpacpsn.ModificarSindicalizado(documento, sindicalizado);
                    //</editor-fold>
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
