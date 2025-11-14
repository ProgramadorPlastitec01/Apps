package Servlets;

import Controladores_BD.AccidenteJpaController;
import Controladores_BD.AusenciaJpaController;
import Controladores_BD.CapacitacionJpaController;
import Controladores_BD.CategoriaJpaController;
import Controladores_BD.DisciplinaJpaController;
import Controladores_BD.DotacionJpaController;
import Controladores_BD.EnfermedadJpaController;
import Controladores_BD.EppJpaController;
import Controladores_BD.ExamenJpaController;
import Controladores_BD.IncapacidadJpaController;
import Controladores_BD.PersonalJpaController;
import Controladores_BD.RetiroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Capacitacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            PersonalJpaController jpacpsn = new PersonalJpaController();
            AccidenteJpaController jpacacd = new AccidenteJpaController();
            AusenciaJpaController jpacasc = new AusenciaJpaController();
            IncapacidadJpaController jpacicp = new IncapacidadJpaController();
            EnfermedadJpaController jpacefm = new EnfermedadJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            DisciplinaJpaController jpacdcp = new DisciplinaJpaController();
            DotacionJpaController jpacdtc = new DotacionJpaController();
            CapacitacionJpaController jpaccpc = new CapacitacionJpaController();
            RetiroJpaController jpacrtr = new RetiroJpaController();
            ExamenJpaController jpacexm = new ExamenJpaController();
            EppJpaController jpacepp = new EppJpaController();
            //VARIABLES OBLIGATORIAS
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int mnu = 0;
            int formulario = 0;
            int id_capacitacion = 0;
            int id_accidente = 0;
            int id_enfermedad = 0;
            int id_incapacidad = 0;
            int id_ausencia = 0;
            int id_disciplina = 0;
            int id_dotacion = 0;
            int id_examen = 0;
            int id_epp = 0;
            int id_retiro = 0;
            int id_capacitacion_detalle = 0;
            int id_area = 0;
            int id_cargo = 0;
            int tipo_estado = 0;
            int tipo_consulta = 0;
            int dia_ini = 0;
            int dia_fin = 0;
            int icg = 0;
            int anio = 0;
            int mes = 0;
            int dia = 0;
            int modulo = 0;
            int idFirma = 0, doc = 0, cod = 0, idSignature = 0;
            boolean proceso = true;
            boolean result = false;
            //VARIABLES GLOBALES
            long documento = 0;
            String fecha = "", idsCap = "";
            double horas = 0;
            double minutos = 0;
            double duracion = 0;
            String titulo = "";
            String folio = "";
            String entidad = "";
            String capacitador = "";
            String observacion = "";
            String manual = "";
            String TipoAct = "", dirigdo = "", alcance = "", metodo = "", evalua = "";
            List lst_verificacion = null;
            String name = "", cargo = "";
            long docu = 0;
            long docx = 0, codx = 0;
            String fecha_inicio = "", fecha_fin = "";

            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="CAPACITACION"> 
                case 22:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Capacitaciones");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        doc = Integer.parseInt(request.getParameter("DocUSer"));
                        cod = Integer.parseInt(request.getParameter("CodUser"));
                    } catch (Exception e) {
                        doc = 0;
                        cod = 0;
                    }
                    try {
                        id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                        try {
                            id_capacitacion_detalle = Integer.parseInt(request.getParameter("idCapd"));
                        } catch (Exception e) {
                            id_capacitacion_detalle = 0;
                        }
                        request.setAttribute("Id_capacitacion", id_capacitacion);
                    } catch (Exception e) {
                        request.setAttribute("Id_capacitacion", 0);
                    }
                    request.setAttribute("txtDocument", doc);
                    request.setAttribute("txtCode", cod);
                    request.setAttribute("Id_capDetall", id_capacitacion_detalle);
                    request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
                    break;
                case 23:
                    try {
                        id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                        fecha = request.getParameter("Txt_fecha");
                        titulo = request.getParameter("Txt_titulo");
                        entidad = request.getParameter("Txt_entidad");
                        capacitador = request.getParameter("Txt_capacitador");
                        folio = request.getParameter("Txt_folio");
                        duracion = Double.parseDouble(request.getParameter("Txt_duracion"));
                        observacion = request.getParameter("Txt_descripcion");
                        proceso = jpaccpc.Modificar_capacitacion(id_capacitacion, entidad, fecha, titulo, duracion, capacitador, observacion, folio, capacitador);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_capacitacion");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_capacitacion");
                        }
                    } catch (Exception e) {
                        fecha = request.getParameter("Txt_fecha");
                        titulo = request.getParameter("Txt_titulo");
                        entidad = request.getParameter("Txt_entidad");
                        capacitador = request.getParameter("Txt_capacitador");
//                        folio = request.getParameter("Txt_folio");
                        duracion = Double.parseDouble(request.getParameter("Txt_duracion"));
                        observacion = request.getParameter("Txt_descripcion");
                        proceso = jpaccpc.Registrar_capacitacion(entidad, fecha, titulo, duracion, capacitador, observacion, capacitador);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_capacitacion");
                        } else {
                            request.setAttribute("Alerta", "Error_capacitacion");
                        }
                    }
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23").forward(request, response);
                    break;
                case 24:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    manual = request.getParameter("Txt_manual");
                    capacitador = request.getParameter("txtResponsable");
                    if (manual.equals("N/A")) {
                        documento = Long.parseLong(request.getParameter("Txt_documento").toString());
                        if (jpaccpc.Verificar_registro(id_capacitacion, documento) == 0) {
                            proceso = jpaccpc.Registrar_capacitacion_detalle_alt(id_capacitacion, documento, capacitador);
                        } else {
                            proceso = false;
                        }
                    }else if (manual.equals("External")) {
                        docu = Long.parseLong(request.getParameter("NmbDoc").toString());
                        name = request.getParameter("TxtName").toString();
                        cargo = request.getParameter("TxtCargo").toString();
                        cargo = "" + cargo + " (Personal Externo)";
                        if (jpaccpc.Verificar_registro(id_capacitacion, documento) == 0) {
                            proceso = jpaccpc.RegsiterUserExternal(id_capacitacion, docu, name, cargo, capacitador);
                        } else {
                            proceso = false;
                        }
                    }  else {
                        documento = Long.parseLong(manual.split(" / ")[1]);
                        if (jpaccpc.Verificar_registro(id_capacitacion, documento) == 0) {
                            proceso = jpaccpc.Registrar_capacitacion_detalle(id_capacitacion, Long.parseLong(manual.split(" / ")[1]), manual.split(" / ")[0], manual.split(" / ")[3] + " / " + manual.split(" / ")[2], "", capacitador);
                        } else {
                            proceso = false;
                        }
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_capacitación_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_capacitación_detalle");
                    }
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion).forward(request, response);
                    break;
                case 25:
                    id_capacitacion = Integer.parseInt(request.getParameter("Id_capacitacion").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    String nroFolio = request.getParameter("NroFolio");
                    if (tipo_estado == 1) {
                        jpaccpc.Activar_capacitacion(id_capacitacion, nroFolio);
                        request.setAttribute("Alerta", "DesactivarCapacitacion");
                    } else if (tipo_estado == 0) {
                        jpaccpc.Desactivar_capacitacion(id_capacitacion);
                        request.setAttribute("Alerta", "ActivarCapacitacion");
                    } else {
                        jpaccpc.Eliminar_capacitacion(id_capacitacion);
                        request.setAttribute("Alerta", "EliminarCapacitacion");
                    }
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23").forward(request, response);
                    break;
                case 26:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    id_capacitacion_detalle = Integer.parseInt(request.getParameter("icd"));
                    jpaccpc.Eliminar_capacitacion_detalle(id_capacitacion_detalle);
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion).forward(request, response);
                    break;
                case 35:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    id_capacitacion_detalle = Integer.parseInt(request.getParameter("idCapDetalle"));
                    doc = Integer.parseInt(request.getParameter("txtDocument"));
                    cod = Integer.parseInt(request.getParameter("txtCode"));
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&idCapd=" + id_capacitacion_detalle + "&DocUSer=" + doc + "&CodUser=" + cod + "").forward(request, response);
                    break;
                case 36:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    id_capacitacion_detalle = Integer.parseInt(request.getParameter("idCapDetalle"));
                    idSignature = Integer.parseInt(request.getParameter("idSignature"));
                    result = jpaccpc.ActualizarFirmaCapacitacion(id_capacitacion_detalle, idSignature);
                    if (result) {
                        request.setAttribute("Alerta", "CapacitacionFirmada");
                    }
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&DocUSer=" + 0 + "&CodUser=" + 0 + "").forward(request, response);
                    break;
                case 37:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    idsCap = request.getParameter("selectedIds");
                    int valid = Integer.parseInt(request.getParameter("validac"));
                    idsCap = idsCap.replace("][", ",").replace("[", "").replace("]", "");
                    result = jpaccpc.ResultadoEvaluacion(valid, idsCap);
                    if (result) {
                        request.setAttribute("Alerta", "CapacitacionCalificacion");
                    }
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&DocUSer=" + 0 + "&CodUser=" + 0 + "").forward(request, response);
                    break;
                case 38:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    TipoAct = request.getParameter("Txt_TypeAC");
                    if (TipoAct.equals("Otro")) {
                        TipoAct = TipoAct + "/" + request.getParameter("Otro_one");
                    }
                    dirigdo = request.getParameter("Txt_Dirg");
                    if (dirigdo.equals("Otro")) {
                        dirigdo = dirigdo + "/" + request.getParameter("Otro_two");
                    }
                    alcance = request.getParameter("Txt_alca");
                    if (alcance.equals("Otro")) {
                        alcance = alcance + "/" + request.getParameter("Otro_three");
                    }
                    metodo = request.getParameter("Txt_metod");
                    if (metodo.equals("Otro")) {
                        metodo = metodo + "/" + request.getParameter("Otro_four");
                    }
                    evalua = request.getParameter("Txt_eva");
                    if (evalua.equals("Otro")) {
                        evalua = evalua + "/" + request.getParameter("Otro_five");
                    }
                    result = jpaccpc.ActualizarParametrosCapacitacion(id_capacitacion, TipoAct, dirigdo, alcance, metodo, evalua);
                    if (result) {
                        request.setAttribute("Alerta", "ParametrosActualizados");
                    }
                    request.getRequestDispatcher("Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&DocUSer=" + 0 + "&CodUser=" + 0 + "").forward(request, response);
                    break;
                    
//</editor-fold>
            }

        } catch (Exception e) {
            request.getRequestDispatcher("Capacitacion.jsp").forward(request, response);
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
