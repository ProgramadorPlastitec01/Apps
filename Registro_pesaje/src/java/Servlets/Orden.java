package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.OrdenJpaController;
import SQL.Conexion_Factory;
import javax.servlet.http.HttpSession;

public class Orden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        OrdenJpaController jpaodn = new OrdenJpaController();
        Conexion_Factory JpaFact = new Conexion_Factory();
        List lst_factory = null;
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String nombre_responsable = sesion.getAttribute("Nombre").toString() + " " + sesion.getAttribute("Apellido").toString();
            List lst_orden = null;
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean result = false;
            int id_orden = 0, cantidad = 0, estado = 0, peso_meta = 0, id_maquina = 0;
            String codigo = "", unidad = "", plan = "", centro_costo = "", fechaI = "", fechaF = "", lote = "", producto = "", estado_r = "", estado_d = "", valor_maquina = "", numero_orden = "";
            int unidadesxempaque = 0, pesoxgramos = 0;
            String ft_version = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDEN">
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        numero_orden = request.getParameter("numero_orden");
                    } catch (Exception e) {
                        numero_orden = "0";
                    }
                    request.setAttribute("numero_orden", numero_orden);
                    request.setAttribute("id_orden", id_orden);
                    request.getRequestDispatcher("Orden.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ORDEN">
                    numero_orden = request.getParameter("orden");
                    codigo = request.getParameter("codigo");
                    producto = request.getParameter("producto");
                    plan = request.getParameter("plan");
                    lote = request.getParameter("lote");
                    cantidad = Integer.parseInt(request.getParameter("cantidad"));
                    unidad = request.getParameter("unidad");
                    centro_costo = request.getParameter("centro");
                    ft_version = request.getParameter("ft_version");
//                    fechaI = request.getParameter("fechai");
//                    fechaF = request.getParameter("fechaf");
                    unidadesxempaque = Integer.parseInt(request.getParameter("unidadesxempaque"));
                    valor_maquina = request.getParameter("valor_maquina");
                    String arr_maquina[] = valor_maquina.split("-");
                    id_maquina = Integer.parseInt(arr_maquina[0]);
                    pesoxgramos = Integer.parseInt(arr_maquina[1]);
                    peso_meta = cantidad * pesoxgramos / 1000;
                    result = jpaodn.Registar_orden(numero_orden, id_maquina, codigo, producto, plan, lote, cantidad, unidad, centro_costo, ft_version, peso_meta, unidadesxempaque, nombre_responsable);
                    request.setAttribute("Registar_Orden", result);
                    request.getRequestDispatcher("Orden?opc=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO ORDEN">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    lst_orden = jpaodn.Consultar_estadosxOrden(id_orden);
                    if (lst_orden != null) {
                        Object[] obj_est = (Object[]) lst_orden.get(0);
                        if (obj_est[4] == null || obj_est[4].toString().equals("CERRADO")) {
                            if (obj_est[6] == null || obj_est[6].toString().equals("CERRADO")) {
                                if (estado == 1) {
                                    estado = 0;
                                    result = jpaodn.Cambiar_estado_estado(estado, id_orden);
                                } else {
                                    estado = 1;
                                    result = jpaodn.Cambiar_estado_estado(estado, id_orden);
                                }
                            } else {
                                request.setAttribute("registros_abiertos_Detalle", true);
                            }
                        } else {
                            request.setAttribute("registros_abiertos_Registros", true);
                        }
                    } else {
                        result = false;
                        request.setAttribute("ErrorCambiarEstado", true);
                    }
                    request.setAttribute("Cambiar_Estado_Orden", result);
                    request.getRequestDispatcher("Orden?opc=1&id_orden=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="EDITAR ORDEN">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    unidadesxempaque = Integer.parseInt(request.getParameter("unidadesxempaque"));
                    cantidad = Integer.parseInt(request.getParameter("cantidad"));
                    valor_maquina = request.getParameter("valor_maquina");
                    String arr_maquina_m[] = valor_maquina.split("-");
                    id_maquina = Integer.parseInt(arr_maquina_m[0]);
                    pesoxgramos = Integer.parseInt(arr_maquina_m[1]);
                    peso_meta = cantidad * pesoxgramos / 1000;
                    result = jpaodn.ModificarOrden(id_orden, id_maquina, unidadesxempaque, peso_meta);
                    request.setAttribute("Modificar_Orden", result);
                    request.getRequestDispatcher("Orden?opc=1&id_orden=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ORDEN SIN FACTORY">
                    numero_orden = request.getParameter("TxtOrden");
                    cantidad = Integer.parseInt(request.getParameter("txtCantidad"));
                    unidadesxempaque = Integer.parseInt(request.getParameter("unidadesxempaque"));
                    String dataClient = request.getParameter("Cbx_cliente");
                    String dataMaquina = request.getParameter("Txt_filtro_avanzado");
                    if (dataMaquina != null && !dataMaquina.equals("")) {
                        String[] datMaquina = dataMaquina.split("/");
                        codigo = datMaquina[1].toString();
//                        producto = datMaquina[2].toString();
                    } else {
                        codigo = "";
//                        producto = "";
                    }
                    lst_factory = JpaFact.ConsultaCodigosCod(codigo.trim());
                    if (lst_factory != null) {
                        String[] DataFact = lst_factory.toString().replace("[", "").replace("]", "").split(" / ");
                        ft_version = dataClient + "/" + DataFact[2].toString() + "/" + DataFact[3].toString();
                        if (producto.equals("")) {
                            producto =  DataFact[1];
                        }
                    }
                    String[] arr_maquin = dataMaquina.split("/");
                    id_maquina = Integer.parseInt(arr_maquin[2].toString().trim());
                    pesoxgramos = Integer.parseInt(arr_maquin[3].toString().trim());
//                    int undTots = Integer.parseInt(arr_maquin[7].toString().trim());
                    peso_meta = cantidad * pesoxgramos / unidadesxempaque;
                    
                    result = jpaodn.Registar_orden(numero_orden, id_maquina, codigo, producto, "N/A", "N/A", cantidad, "UN", "N/A", ft_version, peso_meta, unidadesxempaque, nombre_responsable);
                    request.setAttribute("Registar_Orden", result);
                    request.getRequestDispatcher("Orden?opc=1").forward(request, response);
//</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Error_app", true);
            request.getRequestDispatcher("Orden.jsp").forward(request, response);
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
