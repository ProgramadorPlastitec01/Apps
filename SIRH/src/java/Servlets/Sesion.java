package Servlets;

import Controladores_BD.MenuJpaController;
import Controladores_BD.UsuarioJpaController;
import Metodos.ConnectionSignature;
import Metodos.Control_encriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Sesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            //FECHA
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
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
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            MenuJpaController jpamenu = new MenuJpaController();
            ConnectionSignature ConnSigna = new ConnectionSignature();
            String user = "";
            String password = "";
            String password_encript = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String firma = "";
            int id_usuario = 0, documento = 0, codigo = 0, tipo_firma = 0, Temp = 1;
            List lst_usuario = null;
            String color = "";
            boolean registro = true;
            switch (opc) {
                //Caso de login
                case 1:
                    user = request.getParameter("Txt_user");
                    password = request.getParameter("Txt_password");
                    if (password.length() >= 8) {
                        password_encript = md5.md5(password);
                        lst_usuario = jpacusa.Iniciar_sesion(user, password_encript);
                        if (lst_usuario == null) {
                            lst_usuario = jpacusa.Iniciar_sesion(user, password);
                        }
                    } else {
                        lst_usuario = jpacusa.Iniciar_sesion(user, password);
                    }
                    if (lst_usuario == null) {
                        request.setAttribute("Alerta", "Usuario_no_existe");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        //Se obtienen los datos de la sesion
                        Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                        if ((Integer) obj_sesion[11] == 0) {
                            request.setAttribute("Alerta", "Usuario_desactivado");
                            request.setAttribute("var1", obj_sesion[1]);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else if (obj_sesion[14].toString().equals("SI")) {
                            request.setAttribute("Alerta", "Cambio_contraseña");
                            request.setAttribute("var1", obj_sesion[0]);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            id_usuario = Integer.parseInt(obj_sesion[0].toString());
                            //Se asigna el usuario a la sesion
                            sesion.setAttribute("Nombre_apellido", obj_sesion[1].toString() + " " + obj_sesion[2].toString());
                            sesion.setAttribute("Id_usuario", id_usuario);
                            sesion.setAttribute("Color", obj_sesion[10].toString());
                            sesion.setAttribute("Rol", obj_sesion[9].toString());
                            sesion.setAttribute("Menu", id_usuario);
                            sesion.setAttribute("Fecha_sesion", obj_sesion[15].toString());
                            sesion.setAttribute("FechaPS_inicio", anio + "-" + mes + "-01");
                            sesion.setAttribute("FechaPS_fin", anio + "-" + mes + "-" + dia);
                            sesion.setAttribute("name_user", obj_sesion[4].toString());
                            sesion.setAttribute("password", obj_sesion[5].toString());
                            sesion.setAttribute("Id_areaS", obj_sesion[16].toString());
                            sesion.setAttribute("Consulta_personalS", obj_sesion[17].toString());
                            jpacusa.Fecha_sesion(id_usuario);
                            request.getRequestDispatcher("Inicio?opc=2&mnu=5").forward(request, response);
                        }
                    }
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password");
                    password_encript = md5.md5(password); // se encripta contraseña nueva
                    jpacusa.Cambiar_password(id_usuario, password_encript); // metodo para modificar la contraseña
                    request.setAttribute("Alerta", "Password_actualizado");  // alerta de exito
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                case 4:
                    color = request.getParameter("color");
                    id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
                    registro = jpacusa.Cambiar_color(id_usuario, color);
                    if (registro) {
                        sesion.removeAttribute("Color");
                        sesion.setAttribute("Color", color);
                    }
                    request.getRequestDispatcher("Inicio?opc=2&mnu=5").forward(request, response);
                    break;
                //fecha de proceso
                case 5:
                    fecha_inicio = request.getParameter("fpi");
                    if (fecha_inicio.equals("") || fecha_inicio == null) {
                        fecha_inicio = sesion.getAttribute("FechaPS_inicio").toString();
                    }
                    fecha_fin = request.getParameter("fpf");
                    if (fecha_fin.equals("") || fecha_fin == null) {
                        fecha_fin = sesion.getAttribute("FechaPS_fin").toString();
                    }
                    sesion.setAttribute("FechaPS_inicio", fecha_inicio);
                    sesion.setAttribute("FechaPS_fin", fecha_fin);
                    request.setAttribute("Ajustes", "Fecha_proceso");
                    request.setAttribute("FechaP_inicio", fecha_inicio);
                    request.setAttribute("FechaP_fin", fecha_fin);
                    request.getRequestDispatcher("Inicio?opc=1&mnu=1").forward(request, response);
                    break;
                case 6:
                    try {
                        documento = Integer.parseInt(request.getParameter("Txt_documento"));
                        codigo = Integer.parseInt(request.getParameter("Txt_codigo"));
                        request.setAttribute("Documento", documento);
                        request.setAttribute("Codigo", codigo);
                        request.setAttribute("Temp", Temp);
                        request.getRequestDispatcher("Firmas.jsp").forward(request, response);
                    } catch (Exception e) {
                        request.getRequestDispatcher("Firmas.jsp").forward(request, response);
                    }
                    break;
                case 7:
                    documento = Integer.parseInt(request.getParameter("dcm"));
                    codigo = Integer.parseInt(request.getParameter("cdg"));
                    tipo_firma = Integer.parseInt(request.getParameter("Rdb_tipo_firma"));
                    firma = request.getParameter("Txt_firma");
                    if (tipo_firma == 0) {
//                        jpamenu.Cambiar_estados_firma(documento, codigo);
                        ConnSigna.RegistrarFirmas(documento, codigo, firma);
                        request.setAttribute("Alerta", "Registro_firmas");
                        request.setAttribute("var1", documento);
                    } else {
                        ConnSigna.ActualizarFirmas(documento, codigo, firma);
                        request.setAttribute("Alerta", "Actualizacion_firmas");
                        request.setAttribute("var1", documento);
                    }
                    request.setAttribute("Temp", Temp);
                    request.getRequestDispatcher("Firmas.jsp").forward(request, response);
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
