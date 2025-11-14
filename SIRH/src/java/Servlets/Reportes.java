package Servlets;

import Controladores_BD.MenuJpaController;
import Metodos.Actualizacion_salarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Reportes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            //InetAddress localHost = InetAddress.getLocalHost();
            MenuJpaController jpacmnu = new MenuJpaController();
            Actualizacion_salarios mtdasl = new Actualizacion_salarios();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int mnu = 0;
            boolean proceso = false;
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            int id_opcion_menu = cal.get(Calendar.YEAR);
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            String[] select_area = new String[2];
            int id_area = 0;
            int exportar = 0;
            String nombre_area = "";
            String salarios = "";
            String error_salarios = "";
            int tipo_reporte = 0;
            int val_salarios = 0;
            String file_name = "";
            String file_path = "";
            String archivo_plano = "";
            String new_salarios = "";
            String old_salarios = "";
            String personal = "";
            String concepto = "";
            String fecha = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            int anio_report = 0;
            int mes_report = 0;
            List lst_personal = null;
            switch (opc) {
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Cumpleanos");
                    try {
                        mes_report = Integer.parseInt(request.getParameter("Cbx_mes"));
                    } catch (Exception e) {
                        mes_report = 0;
                    }
                    request.setAttribute("Mes", mes_report);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 2:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Export-data");
                    try {
                        fecha_inicio = request.getParameter("Txt_fecha_inicio");
                    } catch (Exception e) {
                        fecha_inicio = "";
                    }
                    try {
                        fecha_fin = request.getParameter("Txt_fecha_fin");
                    } catch (Exception e) {
                        fecha_fin = "";
                    }
                    try {
                        exportar = Integer.parseInt(request.getParameter("ept"));
                    } catch (Exception e) {
                        exportar = 0;
                    }
                    if (exportar > 0) {
                        file_name = request.getParameter("fnm");
                        file_path = request.getParameter("fpt");
                        if (fecha_inicio != null || fecha_fin != null) {
                            file_name = file_name + "_" + fecha_inicio + "_" + fecha_fin + ".xls";
                            Metodos.MysqlToXlsDate.main(file_path, file_name.toUpperCase(), fecha_inicio, fecha_fin + ".xls");
                        } else {
                            Metodos.MysqlToXls.main(file_path, file_name.toUpperCase() + "_" + usuario_registro + "_" + anio + mes + dia + ".xls");
                        }
                    }
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 3:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Carnets");
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 4:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        anio_report = 0;
                    }
                    try {
                        mes_report = Integer.parseInt(request.getParameter("Cbx_mes"));
                    } catch (Exception e) {
                        mes_report = 0;
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Ausencias");
                    request.setAttribute("Anio", anio_report);
                    request.setAttribute("Mes", mes_report);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 5:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        anio_report = 0;
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Rotacion");
                    request.setAttribute("Anio", anio_report);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 6:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        anio_report = anio;
                    }
                    try {
                        mes_report = Integer.parseInt(request.getParameter("Cbx_mes"));
                    } catch (Exception e) {
                        mes_report = Integer.parseInt(mes);
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Verificacion_registros");
                    request.setAttribute("Anio", anio_report);
                    request.setAttribute("Mes", mes_report);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 7:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        anio_report = anio;
                    }
                    try {
                        mes_report = Integer.parseInt(request.getParameter("Cbx_mes"));
                    } catch (Exception e) {
                        mes_report = Integer.parseInt(mes);
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Calificacion_competencias");
                    request.setAttribute("Anio", anio_report);
                    request.setAttribute("Mes", mes_report);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 8:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Actualizar_salarios");
                    try {
                        val_salarios = Integer.parseInt(request.getParameter("vsl"));
                    } catch (Exception e) {
                        val_salarios = 0;
                    }
                    request.setAttribute("Validacion", val_salarios);
                    if (val_salarios == 1) {
                        salarios = request.getParameter("Txt_salarios");
                        request.setAttribute("Salarios", salarios);
                    } else if (val_salarios == 2) {
                        request.setAttribute("Salarios", "");
                    } else {
                        request.setAttribute("Salarios", "");
                    }
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 9:
                    fecha = request.getParameter("Txt_fecha");
                    concepto = request.getParameter("Txt_concepto");
                    archivo_plano = request.getParameter("Txt_archivo_plano");
                    new_salarios = request.getParameter("Txt_new_salarios");
                    old_salarios = request.getParameter("Txt_old_salarios");
                    personal = request.getParameter("Txt_act_empleados");
                    if (personal.isEmpty() || personal == "" || personal.length() == 0) {
                        request.setAttribute("Alerta", "Actualizacion_salarios_vacio");
                    } else {
                        proceso = jpacmnu.Registrar_actualizacion_salarios(fecha, archivo_plano, concepto, personal, old_salarios, new_salarios, 1, usuario_registro);
                        if (proceso == true) {
                            mtdasl.Actualizar_salario_personal(personal, new_salarios);
                            request.setAttribute("Alerta", "Actualizacion_salarios");
                        } else {
                            request.setAttribute("Alerta", "Actualizacion_salarios_error");
                        }
                    }
                    request.getRequestDispatcher("Reportes?opc=8&mnu=35").forward(request, response);
                    break;
                case 10:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        anio_report = 0;
                    }
                    try {
                        mes_report = Integer.parseInt(request.getParameter("Cbx_mes"));
                    } catch (Exception e) {
                        mes_report = 0;
                    }
                    try {
                        tipo_reporte = Integer.parseInt(request.getParameter("Cbx_origen"));
                    } catch (Exception e) {
                        tipo_reporte = 0;
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Dotacion_x_mes");
                    request.setAttribute("Anio", anio_report);
                    request.setAttribute("Mes", mes_report);
                    request.setAttribute("Tipo_reporte", tipo_reporte);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
                case 11:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    try {
                        anio_report = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        anio_report = 0;
                    }
                    try {
                        mes_report = Integer.parseInt(request.getParameter("Cbx_mes"));
                    } catch (Exception e) {
                        mes_report = 0;
                    }
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Reportes", "Ausencias_sst");
                    request.setAttribute("Anio", anio_report);
                    request.setAttribute("Mes", mes_report);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Reportes.jsp").forward(request, response);
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
