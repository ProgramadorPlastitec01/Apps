package Servlets;

import Controladores_BD.AreaJpaController;
import Controladores_BD.CargoJpaController;
import Controladores_BD.CategoriaJpaController;
import Controladores_BD.MenuJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Plastitec extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            AreaJpaController jpacara = new AreaJpaController();
            CargoJpaController jpaccgo = new CargoJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            MenuJpaController jpacmnu = new MenuJpaController();
            //VARIABLES GLOBALES
            int opc = Integer.parseInt(request.getParameter("opc"));
            int mnu = 0;
            boolean proceso = true;
            int id_area = 0;
            int especialidad = 0;
            String area = "";
            String formato_sst = "";
            int tipo_estado = 0;
            String sigla = "";
            String jefe = "";
            String correo = "";
            int id_cargo = 0;
            int maternidad = 0;
            int grafica = 0;
            int anio = 0;
            int mes = 0;
            int numero_trabajadores = 0;
            int id_numero_trabajadores = 0;
            int id_categoria = 0;
            String cargo = "";
            String categoria = "";
            int id_tipo_categoria = 0;
            //VARIABLES PRECARGADAS
            String usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            switch (opc) {
                //CONTROL DE ÁREAS
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Plastitec", "Areas");
                    try {
                        grafica = Integer.parseInt(request.getParameter("gfc"));
                    } catch (Exception e) {
                        grafica = 0;
                    }
                    request.setAttribute("Grafica", grafica);
                    request.getRequestDispatcher("Plastitec.jsp").forward(request, response);
                    break;
                //REGISTRO DE AREAS
                case 2:
                    area = request.getParameter("Txt_area");
                    sigla = request.getParameter("Txt_sigla");
                    jefe = request.getParameter("Txt_jefe");
                    correo = request.getParameter("Txt_correo");
                    proceso = jpacara.Registrar_area(area, sigla, jefe, correo, usuario_registro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_area");
                        request.setAttribute("var1", area);
                    } else {
                        request.setAttribute("Alerta", "Error_area");
                        request.setAttribute("var1", area);
                    }
                    request.getRequestDispatcher("Plastitec?opc=1&mnu=8").forward(request, response);
                    break;
                //CAMBIAR ESTADO AREAS
                case 3:
                    id_area = Integer.parseInt(request.getParameter("Id_area"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacara.Activar_area(id_area);
                    } else {
                        proceso = jpacara.Desactivar_area(id_area);
                    }
                    request.getRequestDispatcher("Plastitec?opc=1&mnu=8").forward(request, response);
                    break;
                //CONTROL DE CARGOS
                case 4:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Plastitec", "Cargos");
                    request.getRequestDispatcher("Plastitec.jsp").forward(request, response);
                    break;
                //REGISTRO DE CARGOS
                case 5:
                    cargo = request.getParameter("Txt_cargo");
                    id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                    especialidad = Integer.parseInt(request.getParameter("Rdb_especialidad"));
                    formato_sst = request.getParameter("Cbx_formato_sst");
                    proceso = jpaccgo.Registrar_cargo(cargo, id_area, especialidad, formato_sst, usuario_registro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_cargo");
                        request.setAttribute("var1", area);
                    } else {
                        request.setAttribute("Alerta", "Error_cargo");
                        request.setAttribute("var1", area);
                    }
                    request.getRequestDispatcher("Plastitec?opc=4&mnu=9").forward(request, response);
                    break;
                //CAMBIAR ESTADO CARGO
                case 6:
                    id_cargo = Integer.parseInt(request.getParameter("Id_cargo"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpaccgo.Activar_cargo(id_cargo);
                    } else {
                        proceso = jpaccgo.Desactivar_cargo(id_cargo);
                    }
                    request.getRequestDispatcher("Plastitec?opc=4&mnu=9").forward(request, response);
                    break;
                //CONTROL DE CATEGORIAS
                case 7:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Plastitec", "Categoria");
                    request.getRequestDispatcher("Plastitec.jsp").forward(request, response);
                    break;
                //REGISTRO DE CATEGORIAS
                case 8:
                    categoria = request.getParameter("Txt_categoria");
                    id_tipo_categoria = Integer.parseInt(request.getParameter("Cbx_tipo_categoria"));
                    maternidad = Integer.parseInt(request.getParameter("Rdb_maternidad"));
                    proceso = jpacctg.Registrar_categoria(categoria, id_tipo_categoria, maternidad, usuario_registro);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_categoria");
                        request.setAttribute("var1", categoria);
                    } else {
                        request.setAttribute("Alerta", "Error_categoria");
                        request.setAttribute("var1", categoria);
                    }
                    request.getRequestDispatcher("Plastitec?opc=7&mnu=10").forward(request, response);
                    break;
                //CAMBIAR ESTADO CATEGORIAS
                case 9:
                    id_categoria = Integer.parseInt(request.getParameter("Id_categoria"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacctg.Activar_categoria(id_categoria);
                    } else {
                        proceso = jpacctg.Desactivar_categoria(id_categoria);
                    }
                    request.getRequestDispatcher("Plastitec?opc=7&mnu=10").forward(request, response);
                    break;
                //NUMERTO DE TRABAJADORES
                case 10:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    try {
                        id_numero_trabajadores = Integer.parseInt(request.getParameter("intb"));
                    } catch (Exception e) {
                        id_numero_trabajadores = 0;
                    }
                    request.setAttribute("Plastitec", "Numero_trabajadores");
                    request.setAttribute("Id_numero_trabajadores", id_numero_trabajadores);
                    request.getRequestDispatcher("Plastitec.jsp").forward(request, response);
                    break;
                //REGISTRO DE CARGOS
                case 11:
                    anio = Integer.parseInt(request.getParameter("Txt_anio"));
                    mes = Integer.parseInt(request.getParameter("Cbx_mes"));
                    numero_trabajadores = Integer.parseInt(request.getParameter("Txt_num_trabajadores"));
                    id_numero_trabajadores = Integer.parseInt(request.getParameter("intb"));
                    if (id_numero_trabajadores == 0) {
                        proceso = jpacmnu.Registrar_numero_trabajadores_anio_mes(anio, mes, numero_trabajadores, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_num_trabajadores");
                        } else {
                            request.setAttribute("Alerta", "Error_registro_num_trabajadores");
                        }
                    } else {
                        proceso = jpacmnu.Actualizar_numero_trabajadores_anio_mes(anio, mes, numero_trabajadores);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_num_trabajadores");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_num_trabajadores");
                        }
                    }
                    request.getRequestDispatcher("Plastitec?opc=10&mnu=36").forward(request, response);
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
