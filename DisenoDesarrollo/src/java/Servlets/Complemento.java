/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controladores.AreaJpaController;
import Controladores.CargoJpaController;
import Controladores.CategoriaJpaController;
import Controladores.EtapaJpaController;
import Controladores.FaseJpaController;
import Controladores.PruebaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.objects.NativeString;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Complemento extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        //<editor-fold defaultstate="collapsed" desc="CONTROLADORES">
        EtapaJpaController jpa_etapa = new EtapaJpaController();
        FaseJpaController jpa_fase = new FaseJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        CategoriaJpaController jpa_catego = new CategoriaJpaController();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
        int opc = Integer.parseInt(request.getParameter("opc").toString());
        String consulta = "", usu_registro = "", mail = "", pass_mail = "", numero_etapa = "", nombre_etapa = "", letra = "", fase = "", norma = "", guia = "", texto = "", area = "", siglatura = "", cargo = "", na_prueba = "", t_prueba = "", catego_p = "", codigo_daru = "", aceptacion = "", tipo_catego = "", catego = "", t_cam_catego = "", formato_catego = "", permisos = "";
        int id_g = 0, Temp_G = 0, id_etapa = 0, estado = 0, ef = 0, id_fase = 0, id_area = 0, areas = 0, id_cargo = 0, id_prueba = 0, adj_catego = 0, id_catego = 0, id_permisos = 0;
        boolean proceso = false;

        List lst_etapa = null;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="VARIABLES DE SESION">
        HttpSession sesion = request.getSession();
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        usu_registro = sesion.getAttribute("Usuario").toString();
        mail = sesion.getAttribute("Mail").toString();
        pass_mail = sesion.getAttribute("Pass_mail").toString();
        int id_position = Integer.parseInt(sesion.getAttribute("id_position").toString());
        String Usuario = sesion.getAttribute("Usuario").toString().toUpperCase();
        String user_act = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
        //</editor-fold>

        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO GENERAL">
                    try {
                        consulta = request.getParameter("complemento");
                    } catch (Exception e) {
                        consulta = "";
                    }
                    try {
                        id_g = Integer.parseInt(request.getParameter("Id"));
                    } catch (Exception e) {
                        id_g = 0;
                    }
                    try {
                        Temp_G = Integer.parseInt(request.getParameter("Temp"));
                    } catch (Exception e) {
                        Temp_G = 0;
                    }
                    try {
                        id_permisos = Integer.parseInt(request.getParameter("id_perm"));
                    } catch (Exception e) {
                        id_permisos = 0;
                    }

                    request.setAttribute("complemento", consulta);
                    request.setAttribute("Id", id_g);
                    request.setAttribute("Temp", Temp_G);
                    request.setAttribute("id_perm", id_permisos);
                    request.setAttribute("id_position", id_position);
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ETAPA">
                    try {
                        numero_etapa = request.getParameter("Numero");
                        nombre_etapa = request.getParameter("etapa");
                        norma = request.getParameter("norma");
                        guia = request.getParameter("guia");
                        consulta = request.getParameter("complemento");

                        if (numero_etapa == null || nombre_etapa == null || norma == null) {
                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        } else {

                            texto = guia.toString().replace("*N/A", "N/A").replace("* N/A", "N/A");

                            proceso = jpa_etapa.Registrar_etapa(usu_registro, numero_etapa, nombre_etapa, norma, texto);

                            if (proceso) {
                                request.setAttribute("Alerta", "Registro_etapa");
                            } else {
                                request.setAttribute("Alerta", "Error_etapa");
                            }

                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        }
                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR ETAPA">
                    try {
                        numero_etapa = request.getParameter("Numero");
                        nombre_etapa = request.getParameter("etapa");
                        norma = request.getParameter("norma");
                        guia = request.getParameter("guia");
                        id_etapa = Integer.parseInt(request.getParameter("Id_E").toString());
                        consulta = request.getParameter("complemento");

                        if (numero_etapa == null || nombre_etapa == null) {
                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        } else {

                            texto = guia.toString().replace("*N/A", "N/A").replace("* N/A", "N/A");

                            proceso = jpa_etapa.Modificar_etapa(usu_registro, numero_etapa, nombre_etapa, norma, texto, id_etapa);

                            if (proceso) {
                                request.setAttribute("Alerta", "Modificar_etapa");
                            } else {
                                request.setAttribute("Alerta", "Error_etapa_modificar");
                            }

                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        }
                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO ETAPA">
                    id_etapa = Integer.parseInt(request.getParameter("Id_E").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    consulta = request.getParameter("complemento");
                    proceso = jpa_etapa.Estado_etapa(id_etapa, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "Cambio_estado_etapa");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FASE">
                    try {
                        letra = request.getParameter("Letra");
                        fase = request.getParameter("fase");
                        ef = Integer.parseInt(request.getParameter("etapa_f").toString());
                        consulta = request.getParameter("complemento");

                        lst_etapa = jpa_etapa.Normaporetapa(ef);
                        if (lst_etapa != null || lst_etapa.size() > 0 || !lst_etapa.isEmpty()) {
                            Object[] obj_lst_etapa = (Object[]) lst_etapa.get(0);
                            norma = obj_lst_etapa[3].toString();
                        }

                        proceso = jpa_fase.Registrar_fase(usu_registro, letra, fase, ef, norma);

                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_fase");
                        } else {
                            request.setAttribute("Alerta", "Error_fase");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="MDDIFICAR FASE">
                    try {
                        letra = request.getParameter("Letra");
                        fase = request.getParameter("fase");
                        id_fase = Integer.parseInt(request.getParameter("id_fas").toString());
                        consulta = request.getParameter("complemento");

                        proceso = jpa_fase.Modificar_fase(usu_registro, letra, fase, id_fase);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_fase");
                        } else {
                            request.setAttribute("Alerta", "Error_fase_modificar");
                        }
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO FASE">
                    id_fase = Integer.parseInt(request.getParameter("Id_F").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    consulta = request.getParameter("complemento");
                    proceso = jpa_fase.Estado_fase(id_fase, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "Cambio_estado_fase");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR AREA">
                    try {
                        area = request.getParameter("area");
                        siglatura = request.getParameter("siglatura");
                        consulta = request.getParameter("complemento");

                        if (area == null || siglatura == null) {
                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        } else {
                            proceso = jpa_area.Registrar_area(usu_registro, area, siglatura);

                            if (proceso) {
                                request.setAttribute("Alerta", "Registro_area");
                            } else {
                                request.setAttribute("Alerta", "Error_area");
                            }

                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        }

                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR AREA">
                    try {
                        area = request.getParameter("area");
                        siglatura = request.getParameter("siglatura");
                        id_area = Integer.parseInt(request.getParameter("id_a").toString());
                        consulta = request.getParameter("complemento");

                        proceso = jpa_area.Modificar_area(usu_registro, area, siglatura, id_area);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_area");
                        } else {
                            request.setAttribute("Alerta", "Error_area_modificar");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO AREA">
                    id_area = Integer.parseInt(request.getParameter("Id_A").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    consulta = request.getParameter("complemento");
                    proceso = jpa_area.Estado_area(id_area, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "Cambio_estado_area");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CARGO">
                    try {
                        cargo = request.getParameter("cargoP");
                        areas = Integer.parseInt(request.getParameter("areaC").toString());
                        consulta = request.getParameter("complemento");

                        proceso = jpa_cargo.Registrar_cargo(usu_registro, cargo, areas);

                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_cargo");
                        } else {
                            request.setAttribute("Alerta", "Error_cargo");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR CARGO">
                    try {
                        cargo = request.getParameter("cargoP");
                        areas = Integer.parseInt(request.getParameter("areaC").toString());
                        id_cargo = Integer.parseInt(request.getParameter("Id_C").toString());
                        consulta = request.getParameter("complemento");

                        proceso = jpa_cargo.Modificar_cargo(usu_registro, cargo, areas, id_cargo);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_cargo");
                        } else {
                            request.setAttribute("Alerta", "Error_cargo_modificar");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        //Error
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO CARGO">
                    id_cargo = Integer.parseInt(request.getParameter("Id_C").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    consulta = request.getParameter("complemento");
                    proceso = jpa_cargo.Estado_cargo(id_cargo, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "Cambio_estado_cargo");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR PRUEBA">
                    try {
                        na_prueba = request.getParameter("prueba");
                        t_prueba = request.getParameter("tipo_prueba");
                        catego_p = request.getParameter("cetego");
                        codigo_daru = request.getParameter("Codigo");
                        aceptacion = request.getParameter("aceptacion");
                        consulta = request.getParameter("complemento");

                        if (na_prueba == null || t_prueba == null || catego_p == null || codigo_daru == null || aceptacion == null) {
                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        } else {
                            proceso = jpa_prueba.Registrar_prueba(usu_registro, na_prueba, t_prueba, catego_p, aceptacion, codigo_daru);

                            if (proceso) {
                                request.setAttribute("Alerta", "Registro_prueba");
                            } else {
                                request.setAttribute("Alerta", "Error_prueba");
                            }

                            request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                        }

                    } catch (Exception e) {
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PRUEBA">
                    try {
                        na_prueba = request.getParameter("prueba");
                        t_prueba = request.getParameter("tipo_prueba");
                        catego_p = request.getParameter("cetego");
                        codigo_daru = request.getParameter("Codigo");
                        aceptacion = request.getParameter("aceptacion");
                        id_prueba = Integer.parseInt(request.getParameter("Id_P"));
                        consulta = request.getParameter("complemento");

                        proceso = jpa_prueba.Modificar_prueba(usu_registro, na_prueba, t_prueba, catego_p, aceptacion, codigo_daru,id_prueba);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_prueba");
                        } else {
                            request.setAttribute("Alerta", "Error_prueba_modificar");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 16:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO PRUEBA">
                    id_prueba = Integer.parseInt(request.getParameter("Id_P").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    consulta = request.getParameter("complemento");
                    proceso = jpa_prueba.Modificar_estado(id_prueba, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "Cambio_estado_prueba");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CATEGORIA">
                    try {
                        tipo_catego = request.getParameter("tipo_catego");
                        catego = request.getParameter("catego");
                        t_cam_catego = request.getParameter("tipo_camp_catego");
                        formato_catego = request.getParameter("formato_catego");
                        adj_catego = Integer.parseInt(request.getParameter("icon-input").toString());
                        consulta = request.getParameter("complemento");

                        proceso = jpa_catego.Registrar_categoria(usu_registro, tipo_catego, catego, adj_catego, t_cam_catego, formato_catego);

                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_categoria");
                        } else {
                            request.setAttribute("Alerta", "Error_categoria");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR CATEGORIA">
                    try {
                        tipo_catego = request.getParameter("tipo_catego");
                        catego = request.getParameter("catego");
                        t_cam_catego = request.getParameter("tipo_camp_catego");
                        formato_catego = request.getParameter("formato_catego");
                        adj_catego = Integer.parseInt(request.getParameter("icon-input").toString());
                        id_catego = Integer.parseInt(request.getParameter("Id_Catego".toString()));
                        consulta = request.getParameter("complemento");

                        proceso = jpa_catego.Modificar_categoria(usu_registro, tipo_catego, catego, adj_catego, t_cam_catego, formato_catego, id_catego);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_categoria");
                        } else {
                            request.setAttribute("Alerta", "Error_categoria_modificar");
                        }

                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);

                    } catch (Exception e) {
                        request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO CATEGORIA">
                    id_catego = Integer.parseInt(request.getParameter("Id_Catego").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    consulta = request.getParameter("complemento");
                    proceso = jpa_catego.Modificar_estado(id_catego, estado);

                    if (proceso) {
                        request.setAttribute("Alerta", "Cambio_estado_catego");
                    } else {
                        request.setAttribute("Alerta", "Error_cambio_estado_etapa");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="ASIGNAR PERMISOS A LOS CARGOS">

                    try {
                        id_permisos = Integer.parseInt(request.getParameter("id_rol").toString());
                    } catch (Exception e) {
                        id_permisos = 0;
                    }

                    permisos = request.getParameter("Cbx_permission");
                    consulta = request.getParameter("complemento");

                    proceso = jpa_cargo.UpdatePermission(id_permisos, permisos);

                    if (proceso) {
                        request.setAttribute("Alerta", "Permisos");
                    } else {
                        request.setAttribute("Alerta", "Error_permisos");
                    }

                    request.getRequestDispatcher("Complemento?opc=1&complemento=" + consulta + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Complemento.jsp").forward(request, response);
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
