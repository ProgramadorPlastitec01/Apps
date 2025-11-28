package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controladores.RetoJpaController;
import Metodos.Connection_mysql_sirh;
import Metodos.Connection_factory;

public class Reto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String Nombre_rol = sesion.getAttribute("Nombre_rol").toString();
            String Nombres = sesion.getAttribute("Nombres").toString();
            RetoJpaController RetoJpa = new RetoJpaController();
            Connection_mysql_sirh mtdcms = new Connection_mysql_sirh();
            Connection_factory ConnFact = new Connection_factory();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String Modulo = "", Fecha = "", Turno = "", FechaReto = "", Lote = "", Producto = "", Reto = "", Observacion = "", Hora = "", ArgReto = "";
            int IdReto = 0, Maquina = 0, Estado = 0, Tipo = 0, IdRetoDetalle = 0, Documento = 0, Codigo = 0, Val = 0, Validacion = 0, Cantidad = 0;
            List lst_reto = null, lst_empleado = null, lst_producto = null, lst_validacion = null;
            boolean resultado = false;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO RETO">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    try {
                        IdRetoDetalle = Integer.parseInt(request.getParameter("IdRetoDetalle"));
                    } catch (NumberFormatException e) {
                        IdRetoDetalle = 0;
                    }
                    try {
                        Validacion = Integer.parseInt(request.getParameter("Validacion"));
                    } catch (NumberFormatException e) {
                        Validacion = 0;
                    }
                    request.setAttribute("Modulo", Modulo);
                    request.setAttribute("FechaReto", FechaReto);
                    request.setAttribute("Tipo", Tipo);
                    request.setAttribute("IdRetoDetalle", IdRetoDetalle);
                    request.setAttribute("Validacion", Validacion);
                    request.getRequestDispatcher("Reto.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR RETO DIA">
                    Fecha = request.getParameter("Txt_fecha");
                    lst_validacion = RetoJpa.ValidarRetoDia(Fecha);
                    if (lst_validacion != null) {
                        request.setAttribute("Alerta", "Validacion_Reto");
                    } else {
                        resultado = RetoJpa.Registrar_reto_dia(Fecha, rol_usuario);
                        if (resultado) {
                            request.setAttribute("Alerta", "Registrar_reto");
                        } else {
                            request.setAttribute("Alerta", "Fallo_reto");
                        }
                    }
                    request.getRequestDispatcher("Reto?opc=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR RETO DETALLE">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdReto"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    Maquina = Integer.parseInt(request.getParameter("Cbx_maquina"));
                    Lote = request.getParameter("Txt_lote");
                    Reto = request.getParameter("Cbx_reto");
                    Estado = Integer.parseInt(request.getParameter("Estado"));
                    Observacion = request.getParameter("Txt_observacion");
                    if (Lote == null && Lote.equals("") && Lote.length() < 4) {
                        Producto = "N/A";
                    } else {
                        String[] CodigoPdt = Lote.split("-");
                        lst_producto = ConnFact.Productos(CodigoPdt[0]);
                        if (lst_producto.size() > 0) {
                            String DataProducto = lst_producto.get(0).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                            Producto = DataProducto.split("/")[1];
                        } else {
                            Producto = "N/A";
                        }
                    }
                    resultado = RetoJpa.Registrar_reto_detalle(IdReto, Maquina, Lote, Reto, Producto, Estado, Observacion, rol_usuario);
                    if (resultado) {
                        lst_reto = RetoJpa.ConsultaRetoId(IdReto);
                        if (lst_reto != null) {
                            Object[] Obj_reto = (Object[]) lst_reto.get(0);
                            if (Obj_reto[6] != null) {
                                String Calidad = Obj_reto[6].toString();
                                if (!Nombres.contains(Calidad)) {
                                    Calidad = Calidad + "," + Nombres;
                                    RetoJpa.ActualizarVerificacionCalidad(IdReto, Calidad);
                                }
                            } else {
                                RetoJpa.ActualizarVerificacionCalidad(IdReto, Nombres);
                            }
                        }
                        request.setAttribute("Alerta", "Registrar_reto_detalle");
                    } else {
                        request.setAttribute("Alerta", "Fallo_reto");
                    }
                    Validacion = Integer.parseInt(request.getParameter("Validacion"));
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&Validacion=" + Validacion + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="FIRMA PRODUCTO MTF">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdReto"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    lst_reto = RetoJpa.ConsultaRetoId(IdReto);
                    if (lst_reto != null) {
                        Object[] Obj_reto = (Object[]) lst_reto.get(0);
                        if (Obj_reto[5] != null) {
                            String Ejecuto = Obj_reto[5].toString();
                            if (!Ejecuto.contains(Nombres)) {
                                Ejecuto = Ejecuto + "," + Nombres;
                                resultado = RetoJpa.Registrar_producto_responsables_Ids(IdReto, Ejecuto);
                            } else {
                                resultado = RetoJpa.Registrar_producto_responsables_Ids(IdReto, Nombres);
                            }
                        } else {
                            resultado = RetoJpa.Registrar_producto_responsables_Ids(IdReto, Nombres);
                        }
                    }
                    if (resultado) {
                        request.setAttribute("Alerta", "Registrar_producto");
                    } else {
                        request.setAttribute("Alerta", "Fallo_reto");
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&IdRetoDetalle=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR RETO DETALLE">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    try {
                        IdRetoDetalle = Integer.parseInt(request.getParameter("IdRetoDetalle"));
                    } catch (NumberFormatException e) {
                        IdRetoDetalle = 0;
                    }
                    Maquina = Integer.parseInt(request.getParameter("Cbx_maquina"));
                    Lote = request.getParameter("Txt_lote");
                    Reto = request.getParameter("Cbx_reto");
                    Estado = Integer.parseInt(request.getParameter("Estado"));
                    Observacion = request.getParameter("Txt_observacion");
                    if (Lote == null && Lote.equals("") && Lote.length() < 4) {
                        Producto = "N/A";
                    } else {
                        String[] CodigoPdt = Lote.split("-");
                        lst_producto = ConnFact.Productos(CodigoPdt[0]);
                        if (lst_producto.size() > 0) {
                            String DataProducto = lst_producto.get(0).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                            Producto = DataProducto.split("/")[1];
                        } else {
                            Producto = "N/A";
                        }
                    }
                    resultado = RetoJpa.ModificarDetalleId(IdRetoDetalle, Maquina, Lote, Reto, Producto, Estado, Observacion);
                    if (resultado) {
                        request.setAttribute("Alerta", "Modificar_reto_detalle");
                    } else {
                        request.setAttribute("Alerta", "Fallo_reto");
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&IdRetoDetalle=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="FIRMA PRODUCCION">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdReto"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    Documento = Integer.parseInt(request.getParameter("Documento"));
                    Codigo = Integer.parseInt(request.getParameter("Codigo"));
                    if (Documento > 0 && Codigo > 0) {
                        lst_empleado = mtdcms.Empleado_sirh(Documento + "");
                        String empleado = lst_empleado.get(0).toString();
                        resultado = RetoJpa.ActualizacionFirmarProduccion(IdReto, empleado);
                        if (resultado) {
                            request.setAttribute("Alerta", "Firma_Produccion");
                            request.setAttribute("var", empleado);
                        } else {
                            request.setAttribute("Alerta", "Error_firma_OT");
                        }
                    } else {
                        request.setAttribute("Alerta", "Empleado_inexistente");
                        request.setAttribute("var1", resultado);
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&IdRetoDetalle=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="INACTIVAR (ELIMINAR) ITEM">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    try {
                        IdRetoDetalle = Integer.parseInt(request.getParameter("IdRetoDetalle"));
                    } catch (NumberFormatException e) {
                        IdRetoDetalle = 0;
                    }
                    resultado = RetoJpa.InactivarItemDetalle(IdRetoDetalle);
                    if (resultado) {
                        request.setAttribute("Alerta", "Inactivar_item");
                    } else {
                        request.setAttribute("Alerta", "Fallo_reto");
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&IdRetoDetalle=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y ACTUALIZAR FECHA, TURNO, TIPO, HORA">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdReto"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    Turno = request.getParameter("Cbx_turno");
                    Hora = request.getParameter("Txt_hora");
                    Val = Integer.parseInt(request.getParameter("Val"));
                    if (Val == 1) {
                        resultado = RetoJpa.ActualizarPrimerReto(IdReto, Turno, Tipo, Hora);
                        if (resultado) {
                            request.setAttribute("Alerta", "Registrar_cabecera");
                        } else {
                            request.setAttribute("Alerta", "Fallo_reto_detalle");
                        }
                    } else {
                        resultado = RetoJpa.RegistrarSegundoReto(FechaReto, Turno, Tipo, Hora);
                        if (resultado) {
                            request.setAttribute("Alerta", "Registrar_cabecera");
                        } else {
                            request.setAttribute("Alerta", "Fallo_reto_detalle");
                        }
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&IdRetoDetalle=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="MODULO VISOR">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "Visor";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    request.setAttribute("Modulo", Modulo);
                    request.setAttribute("FechaReto", FechaReto);
                    request.getRequestDispatcher("Reto.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO MASIVO">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdReto"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    Maquina = Integer.parseInt(request.getParameter("Cbx_maquina"));
                    Lote = request.getParameter("Txt_lote");
                    Cantidad = Integer.parseInt(request.getParameter("Cantidad"));
                    if (Lote == null && Lote.equals("") && Lote.length() < 4) {
                        Producto = "N/A";
                    } else {
                        String[] CodigoPdt = Lote.split("-");
                        lst_producto = ConnFact.Productos(CodigoPdt[0]);
                        if (lst_producto.size() > 0) {
                            String DataProducto = lst_producto.get(0).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                            Producto = DataProducto.split("/")[1];
                        } else {
                            Producto = "N/A";
                        }
                    }
                    for (int i = 1; i <= Cantidad; i++) {
                        ArgReto = request.getParameter("ArgReto" + i);
                        String[] ArrR = ArgReto.replace("[", "").replace("]", "").split("///");
                        String Rto = ArrR[0];
                        int Est = Integer.parseInt(ArrR[1]);
                        String Obs = ArrR[2];
                        if (Obs.equals("NA")) {
                            Obs = "";
                        }
                        resultado = RetoJpa.Registrar_reto_detalle(IdReto, Maquina, Lote, Rto, Producto, Est, Obs, rol_usuario);
                    }

                    if (resultado) {
                        lst_reto = RetoJpa.ConsultaRetoId(IdReto);
                        if (lst_reto != null) {
                            Object[] Obj_reto = (Object[]) lst_reto.get(0);
                            if (Obj_reto[6] != null) {
                                String Calidad = Obj_reto[6].toString();
                                if (!Nombres.contains(Calidad)) {
                                    Calidad = Calidad + "," + Nombres;
                                    RetoJpa.ActualizarVerificacionCalidad(IdReto, Calidad);
                                }
                            } else {
                                RetoJpa.ActualizarVerificacionCalidad(IdReto, Nombres);
                            }
                        }
                        request.setAttribute("Alerta", "Registrar_reto_detalle");
                    } else {
                        request.setAttribute("Alerta", "Fallo_reto");
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "&Validacion=" + Validacion + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR FIRMA PRODUCCIÓN">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdReto"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    resultado = RetoJpa.EliminarFirmarProduccion(IdReto);
                    if (resultado) {
                        request.setAttribute("Alerta", "EliminarFirmaPR");
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="ELIMINAR RETO VALIDADO">
                    try {
                        Modulo = request.getParameter("Modulo");
                    } catch (Exception e) {
                        Modulo = "RetoCabecera";
                    }
                    try {
                        FechaReto = request.getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        IdReto = Integer.parseInt(request.getParameter("IdRetoElimnar"));
                    } catch (NumberFormatException e) {
                        IdReto = 0;
                    }
                    try {
                        Tipo = Integer.parseInt(request.getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 0;
                    }
                    resultado = RetoJpa.EliminarReto(IdReto);
                    if (resultado) {
                        request.setAttribute("Alerta", "EliminarCabeceraReto");
                    } else {
                        request.setAttribute("Alerta", "FalloEliminarReto");
                    }
                    request.getRequestDispatcher("Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Reto.jsp").forward(request, response);
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
