package Servlets;

import Controladoras.EquipoJpaController;
import Controladoras.HvEquipoJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
            HttpSession sesion = request.getSession();
            EquipoJpaController jpa_equipo = new EquipoJpaController();
            HvEquipoJpaController jpa_hojaV = new HvEquipoJpaController();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            String nombre = sesion.getAttribute("Nombre_apellido").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            List lst_equipo = null;
            List lst_movimientos = null;
            int id_equipo = 0, id_area = 0, protocolo = 0, hojaV = 0, id_h_equipo = 0, id_h_equipos, id_movimiento = 0;
            String filtro = "", modulo = "", equipo = "", cargo = "", responsable = "", fechaA = "", estado = "", tipoE = "", correo = "", descripcion = "", generar = "";
            String adjunto = "", tipo = "", aplicativo = "";
            switch (opc) {
                case 1:
                    try {
                        id_equipo = Integer.parseInt(request.getParameter("idE"));
                    } catch (Exception e) {
                        id_equipo = 0;
                    }
                    try {
                        hojaV = Integer.parseInt(request.getParameter("idHV"));
                    } catch (Exception e) {
                        hojaV = 0;
                    }
                    try {
                        id_movimiento = Integer.parseInt(request.getParameter("idM"));
                    } catch (Exception e) {
                        id_movimiento = 0;
                    }
                    try {
                        id_h_equipos = Integer.parseInt(request.getParameter("idHR"));
                    } catch (Exception e) {
                        id_h_equipos = 0;
                    }
                    generar = request.getParameter("txt_generar");
                    if (generar == null) {
                        generar = "";
                    }
                    modulo = request.getParameter("mod");
                    filtro = request.getParameter("txt_bus");
                    request.setAttribute("filtro", filtro);
                    request.setAttribute("id_movimiento", id_movimiento);
                    request.setAttribute("id_hv_equipo", id_h_equipos);
                    request.setAttribute("id_equipo", id_equipo);
                    request.setAttribute("Equipo", modulo);
                    request.setAttribute("hojaV", hojaV);
                    request.setAttribute("generar", generar);
                    request.getRequestDispatcher("Equipo.jsp").forward(request, response);
                    break;
                case 2:
                    filtro = request.getParameter("txt_bus");
                    equipo = request.getParameter("txt_equipo");
                    cargo = request.getParameter("txt_cargo");
                    responsable = request.getParameter("txt_responsable");
                    tipoE = request.getParameter("slc_tipoE");
                    tipo = request.getParameter("slc_tipo");
                    estado = request.getParameter("slc_estado");
                    fechaA = request.getParameter("txt_fechaA");
                    protocolo = Integer.parseInt(request.getParameter("rdo_prioridad"));
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    descripcion = request.getParameter("txt_descripcion");
                    correo = request.getParameter("txt_correo");
                    aplicativo = request.getParameter("txt_app");
                    resultado = jpa_equipo.registroEquipo(equipo, responsable, tipoE, tipo, id_area, cargo, estado, descripcion, fechaA, nombre, protocolo, correo, aplicativo);
                    request.setAttribute("Registro_equipo", resultado);
                    request.getRequestDispatcher("Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "").forward(request, response);
                    break;
                case 3:
                    filtro = request.getParameter("txt_bus");
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    equipo = request.getParameter("txt_equipo");
                    cargo = request.getParameter("txt_cargo");
                    responsable = request.getParameter("txt_responsable");
                    tipoE = request.getParameter("slc_tipoE");
                    tipo = request.getParameter("slc_tipoM");
                    estado = request.getParameter("slc_estado");
                    fechaA = request.getParameter("txt_fechaA");
                    protocolo = Integer.parseInt(request.getParameter("rdo_prioridad"));
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    descripcion = request.getParameter("txt_descripcion");
                    correo = request.getParameter("txt_correo");
                    aplicativo = request.getParameter("txt_app");
                    resultado = jpa_equipo.ModificarEquipo(id_equipo, equipo, responsable, tipoE, tipo, id_area, cargo, estado, descripcion, fechaA, protocolo, correo, nombre, aplicativo);
                    request.setAttribute("Modificar_equipo", resultado);
                    request.getRequestDispatcher("Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "&idE=0").forward(request, response);
                    break;
                case 4:
                    filtro = request.getParameter("txt_bus");
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    equipo = request.getParameter("txt_equipo");
                    cargo = request.getParameter("txt_cargo");
                    responsable = request.getParameter("txt_responsable");
                    tipoE = request.getParameter("slc_tipoE");
                    estado = request.getParameter("slc_estado");
                    fechaA = request.getParameter("txt_fechaA");
                    id_area = Integer.parseInt(request.getParameter("slc_area"));
                    descripcion = request.getParameter("txt_descripcion");
                    lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    resultado = jpa_equipo.registrarMovimientoEquipo(id_equipo, equipo, responsable, tipoE, id_area, cargo, estado, descripcion, nombre, fechaA, obj_equipo[2].toString(), obj_equipo[3].toString(), Integer.parseInt(obj_equipo[4].toString()), obj_equipo[6].toString(), obj_equipo[7].toString(), obj_equipo[8].toString(), obj_equipo[9].toString(), obj_equipo[10].toString(), obj_equipo[11].toString());
                    if (resultado) {
                        jpa_equipo.ModificarEquipo(id_equipo, equipo, responsable, tipoE, tipo, id_area, cargo, estado, descripcion, fechaA, Integer.parseInt(obj_equipo[12].toString()), obj_equipo[13].toString(), nombre, obj_equipo[15].toString() );
                    }
                    request.setAttribute("Registrar_movimiento", resultado);
                    request.getRequestDispatcher("Equipo?opc=1&mod=Mvt&txt_bus=" + filtro + "&idE=" + id_equipo + "").forward(request, response);
                    break;
                case 5:
                    filtro = request.getParameter("txt_bus");
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    generar = request.getParameter("txt_generar");
                    descripcion = request.getParameter("txt_descripcion");
                    String[] campos = generar.replace("][", "##").replace("]", "").replace("[", "").split("##");
                    String[] adjuntos = descripcion.split("<hr />");
                    for (int i = 0; i < campos.length; i++) {
                        String[] campo = campos[i].split("//");
                        resultado = jpa_hojaV.registroHojaDeVidaEquipo(id_equipo, campo[1], campo[3], Integer.parseInt(campo[2]), adjuntos[i], campo[0], nombre);
                    }
                    request.setAttribute("Registrar_HV", resultado);
                    request.getRequestDispatcher("Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + id_equipo + "").forward(request, response);
                    break;
                case 6:
                    id_h_equipo = Integer.parseInt(request.getParameter("idHR"));
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    filtro = request.getParameter("txt_bus");
                    resultado = jpa_hojaV.Eliminar_adjunto(id_h_equipo);
                    request.setAttribute("EliminarRegistroEquipo", resultado);
                    request.setAttribute("adjunto", adjunto);
                    request.getRequestDispatcher("Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + id_equipo + "").forward(request, response);
                    break;
                case 7:
                    filtro = request.getParameter("txt_bus");
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    id_h_equipos = Integer.parseInt(request.getParameter("idHVR"));
                    String fecha = request.getParameter("txt_Mfecha");
                    descripcion = request.getParameter("txt_descripcion");
                    String registro = request.getParameter("Mslc_registro");
                    String[] campos2 = registro.split("//");
                    resultado = jpa_hojaV.modificarHojaDeVidaEquipo(id_h_equipos, fecha, campos2[0], Integer.parseInt(campos2[1]), campos2[2], descripcion, nombre);
                    request.setAttribute("Modificar_HV", resultado);
                    request.getRequestDispatcher("Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + id_equipo + "").forward(request, response);
                    break;
                case 8:
                    filtro = request.getParameter("txt_bus");
                    id_movimiento = Integer.parseInt(request.getParameter("idM"));
                    id_equipo = Integer.parseInt(request.getParameter("idE"));
                    equipo = request.getParameter("txt_equipoM");
                    cargo = request.getParameter("txt_cargoM");
                    responsable = request.getParameter("txt_responsableM");
                    tipoE = request.getParameter("slc_tipoEM");
                    estado = request.getParameter("slc_estadoM");
                    fechaA = request.getParameter("txt_fechaAM");
                    id_area = Integer.parseInt(request.getParameter("slc_areaM"));
                    descripcion = request.getParameter("txt_descripcionM");
                    lst_movimientos = jpa_equipo.consultaEquipoId(id_equipo);
                    Object[] obj_equipoM = (Object[]) lst_movimientos.get(0);
                    resultado = jpa_equipo.ModificarMovimientoEquipo(id_movimiento, id_equipo, equipo, responsable, tipoE, id_area, cargo, estado, descripcion, fechaA);
//                    resultado = jpa_equipo.ModificarMovimientoEquipo(id_movimiento, id_equipo, equipo, responsable, tipoE, id_area, cargo, estado, descripcion, fechaA, obj_equipoM[2].toString(), obj_equipoM[3].toString(), Integer.parseInt(obj_equipoM[4].toString()), obj_equipoM[6].toString(), obj_equipoM[7].toString(), obj_equipoM[8].toString(), obj_equipoM[11].toString());
//                    resultado = jpa_equipo.ModificarMovimientoEquipo(id_movimiento, id_equipo, equipo, responsable, tipoE, id_area, cargo, estado, descripcion, fechaA, obj_equipoM[2].toString(), obj_equipoM[3].toString(), Integer.parseInt(obj_equipoM[4].toString()), obj_equipoM[6].toString(), obj_equipoM[7].toString(), obj_equipoM[8].toString(), obj_equipoM[9].toString(), obj_equipoM[11].toString());
//                    if (resultado) {
//                        jpa_equipo.ModificarEquipo(id_equipo, equipo, responsable, tipoE, id_area, cargo, estado, descripcion, fechaA, Integer.parseInt(obj_equipoM[12].toString()), obj_equipoM[13].toString(), nombre);
//                    }
                    request.setAttribute("Modificar_movimiento", resultado);
                    request.getRequestDispatcher("Equipo?opc=1&mod=Mvt&txt_bus=" + filtro + "&idE=" + id_equipo + "&idM=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
