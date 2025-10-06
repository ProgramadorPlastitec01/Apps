package Servlets;

import Controladores.AlgoritmoJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.SerialJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Complemento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            LineaJpaController jpaclna = new LineaJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            SerialJpaController jpacsra = new SerialJpaController();
            AlgoritmoJpaController jpacagr = new AlgoritmoJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc"));
            List lst_lineas = null;
            List lst_algoritmos = null;
            List lst_fichas = null;
            List lst_ficha = null;
            List lst_seriales = null;
            boolean proceso = true;
            String tipo, nombre;
            String fecha_calibracion, fecha_proxima, tipo_serial;
            String codigo_ficha = "", pared_doble = "", pared_doble_max = "", pared_doble_min = "",
                    pared_doble_estria = "", pared_doble_estria_max = "", pared_doble_estria_min = "",
                    pared_sencilla = "", pared_sencilla_max = "", pared_sencilla_min = "",
                    pared_sencilla_estria = "", pared_sencilla_estria_max = "", pared_sencilla_estria_min = "",
                    ancho_manga = "", ancho_manga_max = "", ancho_manga_min = "",
                    ancho_ventana = "", ancho_ventana_max = "", ancho_ventana_min = "",
                    ancho_bobina = "", ancho_bobina_max = "", ancho_bobina_min = "",
                    dureza = "", dureza_max = "", dureza_min = "",
                    variacion_espesor = "", curvatura = "", diferencia_perimetro = "",
                    peso = "", peso_max = "", peso_min = "",
                    peso_amarre = "", peso_nucleo = "", peso_bolsa = "", centrado_ventana = "";
            int frecuencia_control = 0, cantidad_tomas = 0, cantidad_evaluar = 0;
            String codigo_producto = "", nombre_producto = "", observaciones = "";
            int id_linea = 0;
            int codigo = 0;
            int aplica_pd = 0;
            int material = 0;
            int estria_ventana = 0;
            int id_algoritmo = 0;
            int id_serial = 0;
            int id_ficha = 0;
            int tipo_estado = 0;
            int version = 0;
            int contador = 0;
            String condicion = "";
            String filtro = "";
            switch (opc) {
                case 1:
                    tipo = "Registro_linea";
                    lst_lineas = jpaclna.Lineas();
                    request.setAttribute("Complemento", tipo);
                    if (lst_lineas == null) {
                        request.setAttribute("Lista_lineas", null);
                    } else {
                        request.setAttribute("Lista_lineas", lst_lineas);
                    }
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 2:
                    nombre = request.getParameter("Txt_nombre");
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo"));
                    proceso = jpaclna.Registrar_linea(nombre, sesion.getAttribute("Rol/Nombres").toString(), codigo);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_linea");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_linea");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    }
                    break;
                case 3:
                    id_linea = Integer.parseInt(request.getParameter("Id_linea"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpaclna.Activar_linea(id_linea);
                    } else {
                        proceso = jpaclna.Desactivar_linea(id_linea);
                    }
                    request.getRequestDispatcher("Complemento?opc=1").forward(request, response);
                    break;
                case 4:
                    tipo = "Registro_ficha";
                    condicion = request.getParameter("cdc");
                    codigo_producto = request.getParameter("cpd");
                    filtro = request.getParameter("fto");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_fichas = jpacftn.Fichas_tecnicas();
                    } else {
                        lst_fichas = jpacftn.Fichas_tecnicas_filtro(filtro);
                        if (lst_fichas == null) {
                            lst_fichas = jpacftn.Fichas_tecnicas();
                        }
                    }
                    request.setAttribute("Complemento", tipo);
                    request.setAttribute("Filtro", filtro);
                    if (lst_fichas == null) {
                        if (condicion == null ? "0" == null : condicion.equals("0")) {
                            request.setAttribute("Codigo_producto", codigo_producto);
                            request.setAttribute("Lista_fichas", null);
                            request.setAttribute("Lista_ficha", null);
                            request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                        }
                    } else if (condicion == null ? "0" == null : condicion.equals("0")) {
                        request.setAttribute("Codigo_producto", codigo_producto);
                        request.setAttribute("Lista_fichas", lst_fichas);
                        request.setAttribute("Lista_ficha", null);
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    } else {
                        lst_ficha = jpacftn.Fichas_tecnicas_codigo(condicion);
                        request.setAttribute("Codigo_producto", codigo_producto);
                        request.setAttribute("Lista_fichas", lst_fichas);
                        request.setAttribute("Lista_ficha", lst_ficha);
                        request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    }
                    break;
                case 5:
                    nombre_producto = request.getParameter("Cbx_producto");
                    codigo_ficha = request.getParameter("Txt_codigo");
                    version = Integer.parseInt(request.getParameter("Txt_version"));
                    pared_doble = request.getParameter("Txt_prd_doble");
                    pared_doble_max = request.getParameter("Txt_prd_doble_max");
                    pared_doble_min = request.getParameter("Txt_prd_doble_min");
                    pared_sencilla = request.getParameter("Txt_prd_sencilla");
                    pared_sencilla_max = request.getParameter("Txt_prd_sencilla_max");
                    pared_sencilla_min = request.getParameter("Txt_prd_sencilla_min");
                    ancho_manga = request.getParameter("Txt_ancho_manga");
                    ancho_manga_max = request.getParameter("Txt_ancho_manga_max");
                    ancho_manga_min = request.getParameter("Txt_ancho_manga_min");
                    ancho_bobina = request.getParameter("Txt_ancho_bobina");
                    ancho_bobina_max = request.getParameter("Txt_ancho_bobina_max");
                    ancho_bobina_min = request.getParameter("Txt_ancho_bobina_min");
                    dureza = request.getParameter("Txt_dureza");
                    dureza_max = request.getParameter("Txt_dureza_max");
                    dureza_min = request.getParameter("Txt_dureza_min");
                    variacion_espesor = request.getParameter("Txt_variacion_espesor");
                    curvatura = request.getParameter("Txt_curvatura");
                    diferencia_perimetro = request.getParameter("Txt_diferencia_perimetro");
                    centrado_ventana = request.getParameter("Txt_centrado_ventana");
                    peso = request.getParameter("Txt_peso");
                    peso_max = request.getParameter("Txt_peso_max");
                    peso_min = request.getParameter("Txt_peso_min");
                    peso_amarre = request.getParameter("Txt_peso_amarre");
                    peso_nucleo = request.getParameter("Txt_peso_nucleo");
                    peso_bolsa = request.getParameter("Txt_peso_bolsas");
                    frecuencia_control = Integer.parseInt(request.getParameter("Txt_frecuencia_control"));
                    cantidad_tomas = Integer.parseInt(request.getParameter("Txt_cantidad_tomas"));
                    cantidad_evaluar = Integer.parseInt(request.getParameter("Txt_cantidad_evaluar"));
                    observaciones = request.getParameter("Txt_observaciones");
                    aplica_pd = Integer.parseInt(request.getParameter("Rdb_registro"));
                    material = Integer.parseInt(request.getParameter("Rdb_material"));
                    //NUEVAS VARIABLES
                    pared_doble_estria = request.getParameter("Txt_prd_doble_estriada");
                    pared_doble_estria_max = request.getParameter("Txt_prd_doble_estriada_max");
                    pared_doble_estria_min = request.getParameter("Txt_prd_doble_estriada_min");
                    pared_sencilla_estria = request.getParameter("Txt_prd_sencilla_estriada");
                    pared_sencilla_estria_max = request.getParameter("Txt_prd_sencilla_estriada_max");
                    pared_sencilla_estria_min = request.getParameter("Txt_prd_sencilla_estriada_min");
                    ancho_ventana = request.getParameter("Txt_ancho_ventana");
                    ancho_ventana_max = request.getParameter("Txt_ancho_ventana_max");
                    ancho_ventana_min = request.getParameter("Txt_ancho_ventana_min");
                    try {
                        estria_ventana = Integer.parseInt(request.getParameter("Rdb_estriada_ventana"));
                    } catch (Exception e) {
                        estria_ventana = 0;
                    }

                    //FIN NUEVAS VARIABLES
                    lst_fichas = jpacftn.Fichas_tecnicas_codigo(codigo_ficha);
                    if (lst_fichas != null) {
                        for (int i = 0; i < lst_fichas.size(); i++) {
                            Object[] obj_fichas = (Object[]) lst_fichas.get(i);
                            if ((Integer) obj_fichas[3] == version) {
                                contador++;
                            }
                        }
                    }
                    if (contador >= 1) {
                        request.setAttribute("Alerta", "Ficha_existente");
                        request.setAttribute("var1", codigo_ficha);
                        request.setAttribute("var2", version);
                        request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                    } else {
                        nombre_producto = nombre_producto.toUpperCase().replace("\u039C", "M");
                        proceso = jpacftn.Registrar_ficha(nombre_producto, codigo_ficha.toUpperCase(), version,
                                pared_doble, pared_doble_max, pared_doble_min,
                                pared_sencilla, pared_sencilla_max, pared_sencilla_min,
                                ancho_manga, ancho_manga_max, ancho_manga_min,
                                ancho_bobina, ancho_bobina_max, ancho_bobina_min,
                                dureza, dureza_max, dureza_min,
                                variacion_espesor, curvatura, diferencia_perimetro,
                                peso, peso_max, peso_min, peso_amarre, peso_nucleo, peso_bolsa,
                                frecuencia_control, cantidad_tomas, cantidad_evaluar,
                                observaciones, sesion.getAttribute("Rol/Nombres").toString(), aplica_pd, material,
                                pared_doble_estria, pared_doble_estria_max, pared_doble_estria_min, pared_sencilla_estria,
                                pared_sencilla_estria_max, pared_sencilla_estria_min, ancho_ventana, ancho_ventana_max, ancho_ventana_min, estria_ventana, centrado_ventana);
                        if (proceso) {
                            proceso = jpacftn.Desactivar_ficha_version_old(codigo_ficha, version);
                            request.setAttribute("Alerta", "Registro_ficha");
                            request.setAttribute("var1", codigo_ficha);
                            request.setAttribute("var2", version);
                        } else {
                            request.setAttribute("Alerta", "Error_ficha");
                            request.setAttribute("var1", codigo_ficha);
                            request.setAttribute("var2", version);
                        }
                        request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                    }
                    break;
                case 6:
                    id_ficha = Integer.parseInt(request.getParameter("Id_ficha"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacftn.Activar_ficha(id_ficha);
                    } else {
                        proceso = jpacftn.Desactivar_ficha(id_ficha);
                    }
                    request.getRequestDispatcher("Complemento?opc=4&cdc=0&cpd=0&fto=").forward(request, response);
                    break;
                case 7:
                    tipo = "Registro_serial";
                    id_serial = Integer.parseInt(request.getParameter("isr"));
                    lst_seriales = jpacsra.Traer_serial(id_serial);
                    request.setAttribute("Complemento", tipo);
                    if (id_serial == 0) {
                        request.setAttribute("Lista_serial", null);
                    } else {
                        request.setAttribute("Lista_serial", lst_seriales);
                    }
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 8:
                    nombre = request.getParameter("Txt_nombre");
                    fecha_calibracion = request.getParameter("Txt_fecha_verificacion");
                    fecha_proxima = request.getParameter("Txt_fecha_proxima");
                    tipo_serial = request.getParameter("Cbx_tipo_serial");
                    proceso = jpacsra.Registrar_serial(nombre, tipo_serial, fecha_calibracion, fecha_proxima, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=7&isr=0").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=7&isr=0").forward(request, response);
                    }
                    break;
                case 9:
                    id_serial = Integer.parseInt(request.getParameter("Id_serial"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacsra.Activar_serial(id_serial);
                    } else {
                        proceso = jpacsra.Desactivar_serial(id_serial);
                    }
                    request.getRequestDispatcher("Complemento?opc=7&isr=0").forward(request, response);
                    break;
                case 10:
                    nombre = request.getParameter("Txt_nombre");
                    id_serial = Integer.parseInt(request.getParameter("Id_serial"));
                    fecha_calibracion = request.getParameter("Txt_fecha_verificacion");
                    fecha_proxima = request.getParameter("Txt_fecha_proxima");
                    tipo_serial = request.getParameter("Cbx_tipo_serial");
                    proceso = jpacsra.Actualizar_serial(id_serial, fecha_calibracion, fecha_proxima, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=7&isr=0").forward(request, response);
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_serial");
                        request.setAttribute("var1", nombre);
                        request.getRequestDispatcher("Complemento?opc=7&isr=0").forward(request, response);
                    }
                    break;
                case 11:
                    tipo = "Registro_algoritmo";
                    lst_algoritmos = jpacagr.Agoritmos();
                    request.setAttribute("Complemento", tipo);
                    if (lst_algoritmos == null) {
                        request.setAttribute("Lista_algoritmos", null);
                    } else {
                        request.setAttribute("Lista_algoritmos", lst_algoritmos);
                    }
                    request.getRequestDispatcher("Complemento.jsp").forward(request, response);
                    break;
                case 12:
                    nombre = request.getParameter("Txt_algoritmo");
                    proceso = jpacagr.Registrar_algoritmo(nombre, sesion.getAttribute("Rol/Nombres").toString());
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_algoritmo");
                        request.setAttribute("var1", nombre);
                        jpacagr.Algotitmo_actual(nombre);
                    } else {
                        request.setAttribute("Alerta", "Error_algoritmo");
                        request.setAttribute("var1", nombre);
                    }
                    request.getRequestDispatcher("Complemento?opc=11").forward(request, response);
                    break;
                case 13:
                    id_algoritmo = Integer.parseInt(request.getParameter("Id_algoritmo"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        proceso = jpacagr.Activar_algoritmo(id_algoritmo);
                        jpacagr.Algotitmo_actual(id_algoritmo);
                    } else {
                        proceso = jpacagr.Desactivar_algoritmo(id_algoritmo);
                    }
                    request.getRequestDispatcher("Complemento?opc=11").forward(request, response);
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
