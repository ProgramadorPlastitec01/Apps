package Servlets;

import Controladores_BD.CesJpaController;
import Controladores_BD.CesSeguimientoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Metodos.Connection_mysql_sirh;

public class CES extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();

        String id_client = request.getRemoteAddr();
        String host_client = request.getRemoteHost();

        try {
            //Sesion
            HttpSession sesion = request.getSession();
            Calendar cal = Calendar.getInstance();
            CesJpaController jpacces = new CesJpaController();
            Connection_mysql_sirh mtdmsr = new Connection_mysql_sirh();
            CesSeguimientoJpaController jpaccseg = new CesSeguimientoJpaController();
            //Variables globales
            int year = cal.get(Calendar.YEAR);
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
            boolean proceso = false;
            String resultado = "";
            int opc = Integer.parseInt(request.getParameter("opc"));
            String codbar = "";
            String datos = "";
            String etd = "";
            String eval_min = "";
            String eval_hour = "";
            List lst_datos_empleado = null;
            List lst_verificacion_existencia = null;
            List lst_evaluacion_tiempos = null;
            int id_ces_seg = 0;
            String user, password;
            List lst_usuario = null;
            switch (opc) {
                case 1:
                    request.setAttribute("SIRH_CES", "CES");
                    datos = request.getParameter("dts");
                    request.setAttribute("Datos", datos);
                    request.setAttribute("IpCliente", id_client);
                    request.setAttribute("HostCliente", host_client);
                    request.getRequestDispatcher("CES.jsp").forward(request, response);
                    break;
                case 2:
                    codbar = request.getParameter("Txt_codbar").trim();
                    try {
                        lst_datos_empleado = mtdmsr.Datos_empleado(codbar);
                        if (lst_datos_empleado != null) {
                            id_ces_seg = 0;
                            String obj_datos_personal[] = lst_datos_empleado.toString().replace("[", "").replace("]", "").split(" / ");
                            datos = obj_datos_personal[0].toString() + "/" + obj_datos_personal[1].toString() + "/" + obj_datos_personal[2].toString() + "/" + obj_datos_personal[3].toString() + "/" + obj_datos_personal[4].toString() + "/" + obj_datos_personal[5].toString() + "/" + obj_datos_personal[6].toString();
                            lst_verificacion_existencia = jpacces.Verificacion_existencia(obj_datos_personal[0].toString(), obj_datos_personal[6].toString(), year, mes);
                            if (lst_verificacion_existencia != null) {
                                Object[] obj_verificacion_existencia = (Object[]) lst_verificacion_existencia.get(0);
                                id_ces_seg = Integer.parseInt(obj_verificacion_existencia[8].toString());
                                etd = obj_verificacion_existencia[5].toString();
                                if (etd.contains(",")) {
                                    String[] arg_etd = etd.split(",");
                                    if (arg_etd[1].contains("Start")) {
                                        etd = arg_etd[1];
                                    } else {
                                        etd = arg_etd[0];
                                    }
                                }
                                //<editor-fold defaultstate="collapsed" desc="EVALUACION">
                                if (etd.contains("Start")) {
                                    lst_evaluacion_tiempos = jpacces.Evaluar_tiempo_marcacion_anio_mes(obj_datos_personal[0].toString(), obj_verificacion_existencia[5].toString(), obj_datos_personal[6].toString());
                                    Object[] obj_eval_tiempo = (Object[]) lst_evaluacion_tiempos.get(0);
                                    eval_min = obj_eval_tiempo[1].toString();//SI-NO
                                    eval_hour = obj_eval_tiempo[2].toString();//CIERRE-NO_CERRAR
                                } else {
                                    eval_min = "SI";
                                }
                                //</editor-fold>
                                if (eval_hour.equals("CIERRE")) {
                                    jpacces.Cierre_automatico_marcacion_anio_mes(obj_datos_personal[0].toString(), obj_verificacion_existencia[5].toString(), obj_datos_personal[6].toString());
                                    jpacces.Registrar_UbicacionMarcaicon_Sal("[NA]", Integer.parseInt(obj_datos_personal[0].toString()), Integer.parseInt(obj_datos_personal[6].toString()), year, mes);
                                    request.getRequestDispatcher("CES?opc=2&Txt_codbar=" + codbar).forward(request, response);
                                    break;
                                }
                                if (eval_min.equals("SI")) {
                                    resultado = jpacces.Registrar_marcacion_anio_mes(obj_datos_personal[0].toString(), obj_verificacion_existencia[5].toString(), year, mes, dia, obj_datos_personal[6].toString());
                                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE UBICACION DE MARCACION">
                                    int document = Integer.parseInt(obj_datos_personal[0].toString());
                                    int idCargo = Integer.parseInt(obj_datos_personal[6].toString());
                                    List lst_ent = jpacces.ConsultarEntradasSaldias(obj_datos_personal[0].toString(), obj_datos_personal[6].toString(), year, mes);
                                    Object[] obj_ent = (Object[]) lst_ent.get(0);
                                    String H_emt = "";
                                    String H_sal = "";
                                    if (resultado.equals("ENTRADA")) {
                                        if (obj_ent[1] == null) {
                                            H_emt = "";
                                        } else {
                                            H_emt = obj_ent[1].toString();
                                        }
                                        String ent = "[Start_" + dia + "/" + id_client + "]";
                                        H_emt = H_emt + ent;
                                        jpacces.Registrar_UbicacionMarcaicon_ENT(H_emt, document, idCargo, year, mes);
                                    } else if (resultado.equals("SALIDA")) {
                                        if (obj_ent[2] == null) {
                                            H_sal = "";
                                        } else {
                                            H_sal = obj_ent[2].toString();
                                        }
                                        String sal = "[End_" + dia + "/" + id_client + "]";
                                        H_sal = H_sal + sal;
                                        jpacces.Registrar_UbicacionMarcaicon_Sal(H_sal, document, idCargo, year, mes);
                                    }

//                                        //</editor-fold>
                                } else {
                                    resultado = "MARCACION_INICIADA";
                                }
                            } else {
                                proceso = jpacces.Registrar_marcacion_anio_mes(obj_datos_personal[0].toString(), obj_datos_personal[6].toString(), datos, "CES", "Generar", year, mes);
                                request.getRequestDispatcher("CES?opc=2&Txt_codbar=" + codbar).forward(request, response);
                                break;
//                                if (proceso == true) {
//                                    lst_verificacion_existencia = jpacces.Verificacion_existencia(obj_datos_personal[0].toString(), obj_datos_personal[6].toString(), year, mes);
//                                    if (lst_verificacion_existencia != null) {
//                                        Object[] obj_verificacion_existencia = (Object[]) lst_verificacion_existencia.get(0);
//                                        id_ces_seg = Integer.parseInt(obj_verificacion_existencia[8].toString());
//                                        etd = obj_verificacion_existencia[5].toString();
//                                        resultado = jpacces.Registrar_marcacion_anio_mes(obj_datos_personal[0].toString(), obj_verificacion_existencia[5].toString(), year, mes, dia, obj_datos_personal[6].toString());
//                                    }
//                                }
                            }
                            //<editor-fold defaultstate="collapsed" desc="CALCULOS HORAS">
                            if (resultado.equals("SALIDA")) {
                                if (id_ces_seg == 0) {
                                    try {
                                        jpaccseg.Registrar_seguimiento_anio_mes(obj_datos_personal[0].toString(), obj_datos_personal[6].toString(), datos, "CES", "Pendiente", year, mes);
                                    } catch (Exception e) {
                                    }
                                }
                                year = Integer.parseInt(etd.split("_")[1]);
                                mes = etd.split("_")[2];
                                dia = etd.split("_")[3];
                                jpaccseg.Calculos_marcacion(obj_datos_personal[0].toString(), obj_datos_personal[6].toString(), year, mes, dia);
                            }
                            //</editor-fold>
                        } else {
                            jpacces.Registrar_error_ces(codbar);
                            datos = "SIN DATOS";
                            resultado = "FALLIDO";
                        }
                    } catch (Exception e) {
                        jpacces.Registrar_error_ces(codbar);
                        datos = "SIN DATOS";
                        resultado = "FALLIDO";
                    }
                    datos = datos + "/" + resultado;
                    request.getRequestDispatcher("CES?opc=1&dts=" + datos).forward(request, response);
                    break;
                case 3:
                    sesion.removeAttribute("Id_usuario");
                    sesion.removeAttribute("User");
                    sesion.removeAttribute("Pass");
                    lst_usuario = jpacces.Login_temp("Admin", "Sirhces2021");
                    Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                    sesion.setAttribute("Id_usuario", obj_sesion[0]);
                    sesion.setAttribute("User", obj_sesion[1]);
                    sesion.setAttribute("Pass", obj_sesion[2]);
                    request.getRequestDispatcher("CES?opc=1&dts=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("CES?opc=1&dts=0").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CES.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CES.class.getName()).log(Level.SEVERE, null, ex);
        }
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
