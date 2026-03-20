package Servlets;

import Controladores.OrdenJpaController;
import Controladores.ResumenJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Resumen extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String UsuarioR = sesion.getAttribute("Nombre").toString();
            boolean resultado = false;
            OrdenJpaController jpa_orden = new OrdenJpaController();
            ResumenJpaController jpa_resumen = new ResumenJpaController();
            List lst_resumen = null;
            String orden = "", orden_despacho = "", cliente = "", lote = "", fecha_despacho = "", num_certificado = "", fecha1 = "",
                    fecha2 = "", hora1 = "", hora2 = "", num_grafadora = "", camposR = "", observaciones = "", sentencia = "", condicion = "", condicion2 = "", ordenesDiv = "", lotesDiv = "", anio = "";
            int id_orden = 0, cantidad_resumen = 0, id_resumen = 0, cantidad_frecuencia = 0, id_fichaT = 0;
            switch (opc) {
                case 1:
                    orden = request.getParameter("txt_orden");
                    if (!orden.isEmpty()) {
                        camposR = request.getParameter("Campos");
                    }
                    request.setAttribute("camposR", camposR);
                    request.setAttribute("Reportes", "Generar");
                    request.setAttribute("orden", orden);
                    request.getRequestDispatcher("Resumen.jsp").forward(request, response);
                    break;
                case 2:
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    orden = request.getParameter("txt_orden");
                    cliente = request.getParameter("Cbx_cliente");
                    orden_despacho = request.getParameter("txt_ODespacho");
                    num_grafadora = request.getParameter("txt_NumGrafadora");
                    lote = request.getParameter("slt_lote");
                    fecha1 = request.getParameter("txt_fchI");
                    hora1 = request.getParameter("hrI");
                    fecha2 = request.getParameter("txt_fchF");
                    hora2 = request.getParameter("hrF");
                    request.setAttribute("fecha1", fecha1);
                    request.setAttribute("hora1", hora1);
                    request.setAttribute("fecha2", fecha2);
                    request.setAttribute("hora2", hora2);
                    fecha1 = fecha1 + " " + hora1 + ":00";
                    fecha2 = fecha2 + " " + hora2 + ":59";
                    fecha_despacho = request.getParameter("txt_fechaD");
                    num_certificado = request.getParameter("txt_numeroC");
                    request.setAttribute("Cabecera_Resumen", jpa_resumen.consultaCabeceraResumen(orden, lote));
                    request.setAttribute("Cantidad_Resumen", jpa_resumen.consultaCantidadResumen(id_orden, lote, fecha1, fecha2));
                    request.setAttribute("cliente", cliente);
                    request.setAttribute("orden_despacho", orden_despacho);
                    request.setAttribute("num_grafadora", num_grafadora);
                    request.setAttribute("fecha_despacho", fecha_despacho);
                    request.setAttribute("num_certificado", num_certificado);
                    request.getRequestDispatcher("Resumen?opc=1&txt_orden=" + orden + "&Campos=").forward(request, response);
                    break;
                case 3:
                    num_certificado = request.getParameter("num_certificado");
                    orden = request.getParameter("orden");
                    lote = request.getParameter("lote_ensamble");
                    fecha1 = request.getParameter("fecha1");
                    hora1 = request.getParameter("hora1");
                    fecha2 = request.getParameter("fecha2");
                    hora2 = request.getParameter("hora2");
                    fecha_despacho = request.getParameter("fecha_despacho");
                    num_grafadora = request.getParameter("num_grafadora");
                    cliente = request.getParameter("cliente");
                    orden_despacho = request.getParameter("ordenD");
                    observaciones = request.getParameter("text_obs");
                    cantidad_resumen = Integer.parseInt(request.getParameter("cantR"));
                    if (fecha_despacho.equals("")) {
                        fecha_despacho = "N/A";
                    }
                    if (num_certificado.equals("")) {
                        num_certificado = "N/A";
                    }
                    if (observaciones.equals("")) {
                        observaciones = "N/A";
                    }
                    resultado = jpa_resumen.registroResumen(num_certificado, orden, lote, cantidad_resumen, fecha1, hora1, fecha2, hora2, fecha_despacho, num_grafadora, UsuarioR, orden_despacho, cliente, observaciones);
                    lst_resumen = jpa_resumen.consultaResumenIdPorOrden(orden, lote);
                    Object[] obj_resumen = (Object[]) lst_resumen.get(0);
                    fecha1 = fecha1 + " " + hora1;
                    fecha2 = fecha2 + " " + hora2;
                    jpa_resumen.resumirRegistro(orden, lote, fecha1, fecha2, Integer.parseInt(obj_resumen[0].toString()));
                    request.setAttribute("Cabecera_Resumen", jpa_resumen.consultaCabeceraResumen(orden, lote));
                    request.setAttribute("Registro_resumen", resultado);
//                    orden = request.getParameter("txt_orden");
//                    if (!orden.isEmpty()) {
//                        camposR = request.getParameter("Campos");
//                    }
//                    request.setAttribute("camposR", camposR);
//                    request.setAttribute("Reportes", "Generar");
//                    request.setAttribute("orden", orden);
//                    request.getRequestDispatcher("Resumen.jsp").forward(request, response);
//                    request.getRequestDispatcher("Resumen?opc=1&idR=0&txt_orden=").forward(request, response);
                    request.getRequestDispatcher("Resumen?opc=4&idR=0").forward(request, response);
                    break;
                case 4:
                    id_resumen = Integer.parseInt(request.getParameter("idR"));
                    anio = request.getParameter("slc_anio");
                    request.setAttribute("Reportes", "Resumidos");
                    request.setAttribute("anio", ((anio == null) ? "" : anio));
                    request.setAttribute("id_resumen", id_resumen);
                    request.getRequestDispatcher("Resumen.jsp").forward(request, response);
                    break;
                case 5:
                    id_resumen = Integer.parseInt(request.getParameter("idR"));
                    num_certificado = request.getParameter("txt_numeroC");
                    fecha_despacho = request.getParameter("txt_fechaD");
                    orden_despacho = request.getParameter("ordenD");
                    cliente = request.getParameter("slc_cliente");
                    observaciones = request.getParameter("text_obs");
                    anio = request.getParameter("slc_anio");
                    resultado = jpa_resumen.completarResumen(id_resumen, num_certificado, fecha_despacho, orden_despacho, cliente, observaciones);
                    request.setAttribute("Completar_Resumen", resultado);
                    request.getRequestDispatcher("Resumen?opc=4&idR=0&slc_anio=" + anio + "").forward(request, response);
                    break;
                case 6:
                    orden = request.getParameter("txt_orden");
                    lote = request.getParameter("slt_lote");
                    request.setAttribute("Lote", lote);
                    request.setAttribute("orden", orden);
                    request.setAttribute("Reportes", "Formulacion");
                    request.getRequestDispatcher("Resumen.jsp").forward(request, response);
                    break;
                case 7:
                    orden = request.getParameter("txt_orden");
                    id_orden = Integer.parseInt(request.getParameter("idO"));
                    if (!orden.equals("") && id_orden != 0) {
                        lote = request.getParameter("txt_lotes");
                        cantidad_frecuencia = Integer.parseInt(request.getParameter("txt_cant"));
                        lote = lote.replace("][", "//");
                        lote = lote.replace("[", "");
                        lote = lote.replace("]", "");
                        String[] lotes = lote.split("//");
                        sentencia = "";
                        condicion = "";
                        if (lotes.length == 1) {
                            sentencia = "(select COUNT(dd.y2) from control_dms_c cc INNER JOIN orden oo ON cc.id_orden = oo.id_orden"
                                    + " INNER JOIN control_dms_d dd ON dd.id_dimensional_c = cc.id_dimensional_c"
                                    + " WHERE (cc.lote_ensamble = '" + lotes[0] + "' and oo.id_orden = " + id_orden + " and cc.estado = 'cerrado') and dd.y2 = d.y2 order by cc.id_dimensional_c limit " + cantidad_frecuencia + ") as '" + lotes[0] + "'";
                            condicion = "c.lote_ensamble = '" + lotes[0] + "'";
                        } else {
                            for (int i = 0; i < lotes.length; i++) {
                                if (i == 0) {
                                    sentencia = "(select COUNT(dd.y2) from control_dms_c cc INNER JOIN orden oo ON cc.id_orden = oo.id_orden"
                                            + " INNER JOIN control_dms_d dd ON dd.id_dimensional_c = cc.id_dimensional_c"
                                            + " WHERE (cc.lote_ensamble = '" + lotes[i] + "' and oo.id_orden = " + id_orden + " and cc.estado = 'cerrado') and dd.y2 = d.y2 order by cc.id_dimensional_c limit " + cantidad_frecuencia + ") as '" + lotes[i] + "',";
                                } else if (i == (lotes.length - 1)) {
                                    sentencia = sentencia + " (select COUNT(dd.y2) from control_dms_c cc INNER JOIN orden oo ON cc.id_orden = oo.id_orden"
                                            + " INNER JOIN control_dms_d dd ON dd.id_dimensional_c = cc.id_dimensional_c"
                                            + " WHERE (cc.lote_ensamble = '" + lotes[i] + "' and oo.id_orden = " + id_orden + " and cc.estado = 'cerrado') and dd.y2 = d.y2 order by cc.id_dimensional_c limit " + cantidad_frecuencia + ") as '" + lotes[i] + "'";
                                } else {
                                    sentencia = sentencia + " (select COUNT(dd.y2) from control_dms_c cc INNER JOIN orden oo ON cc.id_orden = oo.id_orden"
                                            + " INNER JOIN control_dms_d dd ON dd.id_dimensional_c = cc.id_dimensional_c"
                                            + " WHERE (cc.lote_ensamble = '" + lotes[i] + "' and oo.id_orden = " + id_orden + " and cc.estado = 'cerrado') and dd.y2 = d.y2 order by cc.id_dimensional_c limit " + cantidad_frecuencia + ") as '" + lotes[i] + "',";
                                }
                            }
                            for (int i = 0; i < lotes.length; i++) {
                                if (i == (lotes.length - 1)) {
                                    condicion = condicion + " c.lote_ensamble = '" + lotes[i] + "'";
                                } else {
                                    condicion = "c.lote_ensamble = '" + lotes[i] + "' or";
                                }
                            }
                        }
                        String query = "SELECT d.y2,"
                                + " " + sentencia + ""
                                + " FROM control_dms_c c INNER JOIN orden o ON c.id_orden = o.id_orden"
                                + " INNER JOIN control_dms_d d ON d.id_dimensional_c = c.id_dimensional_c"
                                + " WHERE ("
                                + "" + condicion + ""
                                + ") and o.id_orden = " + id_orden + " and c.estado = 'cerrado'"
                                + " GROUP BY d.y2"
                                + " ORDER BY d.y2 asc";
                        request.setAttribute("Lista_frecuencia", jpa_resumen.consultarFrecuencia(query));
                        request.setAttribute("Lotes", lotes);
                    }
                    request.setAttribute("Reportes", "Frecuencias");
                    request.setAttribute("orden", orden);
                    request.getRequestDispatcher("Resumen.jsp").forward(request, response);
                    break;
                case 8:
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    request.setAttribute("id_fichaT", id_fichaT);
                    request.setAttribute("Reportes", "Premuestras");
                    request.getRequestDispatcher("Resumen.jsp").forward(request, response);
                    break;
                case 9:
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    orden = request.getParameter("idOrds");
                    ordenesDiv = request.getParameter("ordenesDiv");
                    request.setAttribute("ordenes", orden);
                    orden = orden.replace("][", "//");
                    orden = orden.replace("]", "").replace("[", "");
                    String[] arrayOrden = orden.split("//");
                    sentencia = "SELECT cc.id_dimensional_c, cc.lote_ensamble , o.orden, o.id_orden, COUNT(cc.id_dimensional_c) FROM control_dms_c cc INNER JOIN orden o ON cc.id_orden = o.id_orden WHERE ";
                    for (int i = 0; i < arrayOrden.length; i++) {
                        if (i == 0) {
                            condicion = condicion + "cc.id_orden =" + arrayOrden[i];
                        } else {
                            condicion = condicion + " or cc.id_orden =" + arrayOrden[i];
                        }
                    }
                    sentencia = sentencia + condicion + " GROUP by cc.id_orden,cc.lote_ensamble  order by cc.lote_ensamble, o.orden";
                    request.setAttribute("Lista_lotes", jpa_orden.consultaLotesIdOrdenes(sentencia));
                    request.setAttribute("ordenesDiv", ordenesDiv);
                    request.getRequestDispatcher("Resumen?opc=8&idF=" + id_fichaT + "").forward(request, response);
                    break;
                case 10:
                    id_fichaT = Integer.parseInt(request.getParameter("idF"));
                    orden = request.getParameter("idOrds");
                    lote = request.getParameter("idL");
                    ordenesDiv = request.getParameter("ordenesDiv");
                    lotesDiv = request.getParameter("lotesDiv");
                    num_certificado = request.getParameter("txt_numeroC");
                    fecha_despacho = request.getParameter("txt_fechaD");
                    orden_despacho = request.getParameter("txt_ODespacho");
                    cliente = request.getParameter("slc_cliente");
                    String odns = orden;
                    request.setAttribute("lotes", lote);
                    orden = orden.replace("][", "//");
                    orden = orden.replace("]", "").replace("[", "");
                    lote = lote.replace("][", "//");
                    lote = lote.replace("]", "").replace("[", "");
                    String[] arrayorden = orden.split("//");
                    String[] arraylote = lote.split("//");
                    for (int i = 0; i < arrayorden.length; i++) {
                        if (i == 0) {
                            condicion = condicion + "cc.id_orden = " + arrayorden[i];
                        } else {
                            condicion = condicion + " or cc.id_orden = " + arrayorden[i];
                        }
                    }
                    for (int i = 0; i < arraylote.length; i++) {
                        if (i == 0) {
                            condicion2 = condicion2 + "cc.lote_ensamble = '" + arraylote[i] + "'";
                        } else {
                            condicion2 = condicion2 + " or cc.lote_ensamble = '" + arraylote[i] + "'";
                        }
                    }
                    request.setAttribute("condOrden", condicion);
                    request.setAttribute("condLote", condicion2);
                    request.setAttribute("lotesDiv", lotesDiv);
                    request.setAttribute("fch_despacho", fecha_despacho);
                    request.setAttribute("ordenDes", orden_despacho);
                    request.setAttribute("num_certificado", num_certificado);
                    request.setAttribute("cliente_pre", cliente);
                    request.getRequestDispatcher("Resumen?opc=9&idF=" + id_fichaT + "&ordenesDiv=" + ordenesDiv + "&idOrds=" + odns + "").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("Menu.jsp").forward(request, response);
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
