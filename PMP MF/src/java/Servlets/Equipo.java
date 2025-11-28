package Servlets;

import Controladores.EquipoJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.VerificacionFtJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Equipo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            //JPAS
            EquipoJpaController jpaceqp = new EquipoJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            VerificacionFtJpaController jpacvft = new VerificacionFtJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            boolean proceso = true;
            int estado = 0;
            String nombre_equipo = "", marca = "", tipo_modelo = "", serie = "", codigo_interno = "", localizacion = "", linea = "";
            String fecha_instalacion = "", proveedor = "", pais_origen = "", observacion = "", observacion_generales = "", unidad_medida = "";
            String caracteristicas_generales = "", unidad = "", valor = "", estado_tipo = "", informacion_adicional = "", codigo = "", nombre_instrumento = "";
            String fabricante = "", codigo_modelo = "", rango = "", ubicacion = "", verificacion = "";
            String longitud = "", ancho = "", alto = "", peso_total = "";
            String fecha_mm = "", fecha_insp = "", fecha_lub = "", numero_maquina = "", fecha_nota = "", asunto_nota = "", observacion_nota = "";
            int numero_contacto = 0, item = 0, id_ficha_tecnica = 0;
            int id_tipo_equipo = 0, ano_fabricacion = 0;
            int id_equipo = 0, tipo_ficha = 0, id_linea = 0, temp = 0, id_nota = 0, temp1 = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONTROL MODULO EQUIPO">
                    tipo = "Equipos_home";
                    request.setAttribute("Equipos", tipo);
                    try {
                        id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    } catch (Exception e) {
                        id_equipo = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("etd").toString());
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    request.setAttribute("Id_equipo", id_equipo);
                    request.setAttribute("Estado", estado);
                    request.setAttribute("temp", temp);
                    request.getRequestDispatcher("Equipos.jsp").forward(request, response);
                    break;
//</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR EQUIPO">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    nombre_equipo = request.getParameter("Txt_nombre_equipo");
                    id_tipo_equipo = Integer.parseInt(request.getParameter("Cbx_tipo_equipo").toString());
                    marca = request.getParameter("Txt_marca");
                    tipo_modelo = request.getParameter("Txt_tipo_modelo");
                    ano_fabricacion = Integer.parseInt(request.getParameter("Txt_ano_fabricacion").toString());
                    serie = request.getParameter("Txt_serie");
                    codigo_interno = request.getParameter("Txt_codigo_interno");
                    numero_maquina = request.getParameter("Txt_numero_maquina").toString();
                    id_linea = Integer.parseInt(request.getParameter("Cbx_linea"));
                    localizacion = request.getParameter("Txt_localizacion");
                    if (id_equipo > 0) {
                        proceso = jpaceqp.Modificar_equipo(id_equipo, nombre_equipo, id_tipo_equipo, marca, tipo_modelo, ano_fabricacion, serie, codigo_interno, numero_maquina, id_linea, localizacion, rol_usuario);
                    } else {
                        proceso = jpaceqp.Registrar_equipo(nombre_equipo, id_tipo_equipo, marca, tipo_modelo, ano_fabricacion, serie, codigo_interno, numero_maquina, id_linea, localizacion, rol_usuario);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_equipo == 0) ? "Registro" : "Modificar") + "_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_equipo == 0) ? "registro" : "modificar") + "_equipo");
                    }
                    request.setAttribute("var1", nombre_equipo);
                    request.getRequestDispatcher("Equipo?opc=1&Id_equipo=0&etd=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVAR E INACTIVAR EQUIPO">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (estado > 0) {
                        jpaceqp.Activar_equipo(id_equipo);
                    } else {
                        jpaceqp.Desactivar_equipo(id_equipo);
                    }
                    request.getRequestDispatcher("Equipo?opc=1&Id_equipo=0").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="MODULO FICHA TECNICA">
                    tipo = "Ficha_tecnica";
                    request.setAttribute("Equipos", tipo);
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    try {
                        id_ficha_tecnica = Integer.parseInt(request.getParameter("Id_ficha_tecnica").toString());
                    } catch (Exception e) {
                        id_ficha_tecnica = 0;
                    }
                    try {
                        tipo_ficha = Integer.parseInt(request.getParameter("Tipo_ficha").toString());
                    } catch (Exception e) {
                        tipo_ficha = 0;
                    }
                    request.setAttribute("Id_equipo", id_equipo);
                    request.setAttribute("Id_ficha_tecnica", id_ficha_tecnica);
                    request.setAttribute("Tipo_ficha", tipo_ficha);
                    request.getRequestDispatcher("Equipos.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR REGISTRO">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    fecha_instalacion = request.getParameter("Txt_fecha_instalacion");
                    proveedor = request.getParameter("Txt_proveedor");
                    numero_contacto = Integer.parseInt(request.getParameter("Txt_numero_contacto").toString());
                    pais_origen = request.getParameter("Txt_pais_origen");
                    longitud = request.getParameter("Txt_longitud");
                    ancho = request.getParameter("Txt_ancho");
                    alto = request.getParameter("Txt_alto");
                    peso_total = request.getParameter("Txt_peso_total");
                    unidad_medida = request.getParameter("Txt_unidad_medida");
                    observacion = request.getParameter("Txt_observacion");
                    observacion_generales = request.getParameter("Txt_observacion_generales");
                    proceso = jpaceqp.Modificar_equipo_ft(id_equipo, fecha_instalacion, proveedor, numero_contacto, pais_origen, longitud, ancho, alto, peso_total, unidad_medida, observacion, observacion_generales, rol_usuario);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_equipo");
                    }
                    request.getRequestDispatcher("Equipo?opc=4&Id_equipo=" + id_equipo + "").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR CARACTERISTICAS TECNICAS">
                    id_ficha_tecnica = Integer.parseInt(request.getParameter("Id_ficha_tecnica").toString());
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    tipo_ficha = Integer.parseInt(request.getParameter("Txt_tipo").toString());
                    String arrg_dato = "";
                    if (tipo_ficha == 1) {
                        item = Integer.parseInt(request.getParameter("Txt_item").toString());
                        caracteristicas_generales = request.getParameter("Txt_caracteristicas_generales");
                        unidad = request.getParameter("Txt_unidad");
                        valor = request.getParameter("Txt_valor");
                        estado_tipo = request.getParameter("Txt_estado");
                        informacion_adicional = request.getParameter("Txt_informacion_adicional");
                        arrg_dato = "[" + caracteristicas_generales + "][" + unidad + "][" + valor + "][" + estado_tipo + "][" + informacion_adicional + "]";
                    } else {
                        item = Integer.parseInt(request.getParameter("Txt_item").toString());
                        codigo = request.getParameter("Txt_codigo");
                        nombre_instrumento = request.getParameter("Txt_nombre_instrumento");
                        fabricante = request.getParameter("Txt_fabricante");
                        codigo_modelo = request.getParameter("Txt_codigo_modelo");
                        serie = request.getParameter("Txt_serie");
                        rango = request.getParameter("Txt_rango");
                        ubicacion = request.getParameter("Txt_ubicacion");
                        verificacion = request.getParameter("Txt_verificacion");
                        observacion = request.getParameter("Txt_observacion");
                        arrg_dato = "[" + codigo + "][" + nombre_instrumento + "][" + fabricante + "][" + codigo_modelo + "][" + serie + "][" + rango + "][" + ubicacion + "][" + verificacion + "][" + observacion + "]";
                    }
                    proceso = jpacftn.Actualizar_ficha_tecnica(id_ficha_tecnica, id_equipo, tipo_ficha, item, arrg_dato, rol_usuario);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_equipo");
                    }
                    request.getRequestDispatcher("Equipo?opc=4&Id_equipo=" + id_equipo + "&Id_ficha_tecnica=0&Tipo_ficha=0").forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTAR FICHA TECNICA">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    tipo_ficha = Integer.parseInt(request.getParameter("Txt_tipo").toString());
                    String datos = "N/A";
                    proceso = jpacftn.Registrar_ficha_tecnica(id_equipo, tipo_ficha, datos, rol_usuario);
                    request.getRequestDispatcher("Equipo?opc=4&Id_equipo=" + id_equipo + "").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="VERIFICAR FT">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    observacion = request.getParameter("Txt_observacion");
                    proceso = jpacvft.Registrar_verificacion_ft(id_equipo, observacion, rol_usuario);
                    if (proceso) {
                        request.setAttribute("Alerta", "Verificar_ft_equipo");
                    } else {
                        request.setAttribute("Alerta", "Error_verificar_ft_equipo");
                    }
                    request.getRequestDispatcher("Equipo?opc=4&Id_equipo=" + id_equipo + "").forward(request, response);
                    break;
//</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR FECHA PMP OLD">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo").toString());
                    fecha_mm = request.getParameter("Txt_fecha_mm");
                    fecha_insp = request.getParameter("Txt_fecha_insp");
                    fecha_lub = request.getParameter("Txt_fecha_lub");
                    proceso = jpaceqp.Actualizar_fechas_old_pmp(id_equipo, fecha_mm, fecha_insp, fecha_lub);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_equipo_fechas");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_equipo_fechas");
                    }
                    request.getRequestDispatcher("Equipo?opc=1&Id_equipo=0&etd=0").forward(request, response);
                    break;
//</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="MÓDULO NOTAS">
                    tipo = "Notas";
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    try {
                        id_nota = Integer.parseInt(request.getParameter("id_nota"));
                    } catch (NumberFormatException e) {
                        id_nota = 0;
                    }
                    try {
                        temp1 = Integer.parseInt(request.getParameter("temp1"));
                    } catch (NumberFormatException e) {
                        temp1 = 0;
                    }
                    request.setAttribute("Equipos", tipo);
                    request.setAttribute("Id_equipo", id_equipo);
                    request.setAttribute("Id_nota", id_nota);
                    request.setAttribute("temp1", temp1);
                    request.getRequestDispatcher("Equipos.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR - MODIFICAR NOTA">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    try {
                        id_nota = Integer.parseInt(request.getParameter("id_nota"));
                    } catch (NumberFormatException e) {
                        id_nota = 0;
                    }
                    fecha_nota = request.getParameter("Txt_Fecha");
                    asunto_nota = request.getParameter("Txt_asunto");
                    observacion_nota = request.getParameter("Txt_observacion");
                    if (id_nota != 0) {
                        proceso = jpaceqp.Modificar_Nota(id_nota, fecha_nota, asunto_nota, observacion_nota);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_nota");
                        } else {
                            request.setAttribute("Alerta", "Error_nota");
                        }
                    } else {
                        proceso = jpaceqp.Registrar_Nota(id_equipo, fecha_nota, asunto_nota, observacion_nota, rol_usuario);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registrar_nota");
                        } else {
                            request.setAttribute("Alerta", "Error_nota");
                        }
                    }
                    request.getRequestDispatcher("Equipo?opc=10&Id_equipo=" + id_equipo + "&id_nota=0").forward(request, response);
                    break;
                //</editor-fold>
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="ESTADO DE NOTA">
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    try {
                        id_nota = Integer.parseInt(request.getParameter("id_nota"));
                    } catch (NumberFormatException e) {
                        id_nota = 0;
                    }
                    proceso = jpaceqp.Estado_Nota(id_nota);
                    if (proceso) {
                        request.setAttribute("Alerta", "Estado_nota");
                    } else {
                        request.setAttribute("Alerta", "Error_Estado_nota");
                    }
                    request.getRequestDispatcher("Equipo?opc=10&Id_equipo=" + id_equipo + "&id_nota=0").forward(request, response);
                    break;
                //</editor-fold>
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
