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
import javax.servlet.http.HttpSession;

public class Seguimiento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //VARIABLES SESION
            HttpSession sesion = request.getSession();
            String usuario_registro = "";
            try {
                usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            } catch (Exception e) {
                usuario_registro = "Visitante";
            }
            //JPAS
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
            int dias = 0;
            String tipo = "";
            String parte = "";
            String agente = "";
            String titulo = "";
            String folio = "";
            String examenes = "";
            String entidad = "";
            String capacitador = "";
            String clasificacion = "";
            String observacion = "";
            String diagnostico = "";
            String motivo = "";
            String restriciones = "";
            String compromiso = "";
            String concepto = "";
            String centro_medico = "";
            String recomendacion = "";
            String asignacion = "";
            String salario_hora = "";
            String manual = "";
            String fecha_inicial = "";
            String fecha_final = "";
            String fecha_ajuste = "";
            String hora_inicial = "";
            String hora_final = "";
            String TipoAct = "", dirigdo = "", alcance = "", metodo = "", evalua = "";
            String name = "", cargo = "";
            long docu = 0;
            List lst_verificacion = null;
            long docx = 0, codx = 0;
            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="ACCIDENTES">
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Accidentes");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_accidente = Integer.parseInt(request.getParameter("iac"));
                        request.setAttribute("Id_accidente", id_accidente);
                    } catch (Exception e) {
                        request.setAttribute("Id_accidente", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        id_accidente = Integer.parseInt(request.getParameter("iac"));
                    } catch (Exception e) {
                        id_accidente = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Cbx_tipo");
                    parte = request.getParameter("Txt_parte_afectada");
                    agente = request.getParameter("Txt_agente");
                    dias = Integer.parseInt(request.getParameter("Txt_incapacidad"));
                    observacion = request.getParameter("Txt_descripcion");
                    salario_hora = request.getParameter("Txt_salario_hora");
                    if (id_accidente > 0) {
                        proceso = jpacacd.Modificar_accidente(id_accidente, documento + "", fecha, tipo, dias, parte, agente, observacion, salario_hora, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_accidente");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_accidente");
                        }
                    } else {
                        proceso = jpacacd.Registrar_accidente(documento + "", fecha, tipo, dias, parte, agente, observacion, salario_hora, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_accidente");
                        } else {
                            request.setAttribute("Alerta", "Error_accidente");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=1&mnu=14").forward(request, response);
                    break;
                case 3:
                    id_accidente = Integer.parseInt(request.getParameter("Id_accidente").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacacd.Activar_accidente(id_accidente);
                    } else if (tipo_estado == 0) {
                        jpacacd.Desactivar_accidente(id_accidente);
                    } else {
                        jpacacd.Eliminar_accidente(id_accidente);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=1&mnu=14").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ENFERMEDADES">
                case 4:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Enfermedades");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_enfermedad = Integer.parseInt(request.getParameter("ief"));
                        request.setAttribute("Id_enfermedad", id_enfermedad);
                    } catch (Exception e) {
                        request.setAttribute("Id_enfermedad", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 5:
                    try {
                        id_enfermedad = Integer.parseInt(request.getParameter("ief"));
                    } catch (Exception e) {
                        id_enfermedad = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Cbx_tipo");
                    dias = Integer.parseInt(request.getParameter("Txt_incapacidad"));
                    diagnostico = request.getParameter("Txt_diagnostico");
                    observacion = request.getParameter("Txt_descripcion");
                    salario_hora = request.getParameter("Txt_salario_hora");
                    if (id_enfermedad > 0) {
                        proceso = jpacefm.Modificar_enfermedad(id_enfermedad, documento + "", fecha, tipo, dias, diagnostico, observacion, salario_hora, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_enfermedad");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_enfermedad");
                        }
                    } else {
                        proceso = jpacefm.Registrar_enfermedad(documento + "", fecha, tipo, dias, diagnostico, observacion, salario_hora, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_enfermedad");
                        } else {
                            request.setAttribute("Alerta", "Error_enfermedad");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=4&mnu=15").forward(request, response);
                    break;
                case 6:
                    id_enfermedad = Integer.parseInt(request.getParameter("Id_enfermedad").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacefm.Activar_enfermedad(id_enfermedad);
                    } else if (tipo_estado == 0) {
                        jpacefm.Desactivar_enfermedad(id_enfermedad);
                    } else {
                        jpacefm.Eliminar_enfermedad(id_enfermedad);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=4&mnu=15").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INCAPACIDADES">
                case 7:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Incapacidades");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_incapacidad = Integer.parseInt(request.getParameter("iic"));
                        request.setAttribute("Id_incapacidad", id_incapacidad);
                    } catch (Exception e) {
                        request.setAttribute("Id_incapacidad", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 8:
                    try {
                        id_incapacidad = Integer.parseInt(request.getParameter("iic"));
                    } catch (Exception e) {
                        id_incapacidad = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    clasificacion = request.getParameter("Rdb_clasificacion");
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Cbx_tipo");
                    horas = Double.parseDouble(request.getParameter("Txt_hora"));
                    observacion = request.getParameter("Txt_descripcion");
                    salario_hora = request.getParameter("Txt_salario_hora");
                    if (id_incapacidad > 0) {
                        proceso = jpacicp.Modificar_incapacidad(id_incapacidad, documento + "", fecha, tipo, (horas * 8), observacion, salario_hora, usuario_registro, clasificacion);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_incapacidad");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_incapacidad");
                        }
                    } else {
                        proceso = jpacicp.Registrar_incapacidad(documento + "", fecha, tipo, (horas * 8), observacion, salario_hora, usuario_registro, clasificacion);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_incapacidad");
                        } else {
                            request.setAttribute("Alerta", "Error_incapacidad");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=7&mnu=16").forward(request, response);
                    break;
                case 9:
                    id_incapacidad = Integer.parseInt(request.getParameter("Id_incapacidad").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacicp.Activar_incapacidad(id_incapacidad);
                    } else if (tipo_estado == 0) {
                        jpacicp.Desactivar_incapacidad(id_incapacidad);
                    } else {
                        jpacicp.Eliminar_incapacidad(id_incapacidad);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=7&mnu=16").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="AUSENCIAS">
                case 10:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Ausencias");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_ausencia = Integer.parseInt(request.getParameter("ias"));
                        request.setAttribute("Id_ausencia", id_ausencia);
                    } catch (Exception e) {
                        request.setAttribute("Id_ausencia", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 11:
                    try {
                        id_ausencia = Integer.parseInt(request.getParameter("ias"));
                    } catch (Exception e) {
                        id_ausencia = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Cbx_tipo");
                    horas = Double.parseDouble(request.getParameter("Txt_hora"));
                    minutos = (Double.parseDouble(request.getParameter("Txt_minutos")) * 1) / 60;
                    observacion = request.getParameter("Txt_descripcion");
                    salario_hora = request.getParameter("Txt_salario_hora");
                    if (id_ausencia > 0) {
                        proceso = jpacasc.Modificar_ausencia(id_ausencia, documento + "", fecha, tipo, (horas + minutos), observacion, salario_hora, usuario_registro, 0);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_ausencia");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_ausencia");
                        }
                    } else {
                        proceso = jpacasc.Registrar_ausencia(documento + "", fecha, tipo, (horas + minutos), observacion, salario_hora, usuario_registro, 1);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_ausencia");
                        } else {
                            request.setAttribute("Alerta", "Error_ausencia");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=10&mnu=17").forward(request, response);
                    break;
                case 12:
                    id_ausencia = Integer.parseInt(request.getParameter("Id_ausencia").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacasc.Activar_ausencia(id_ausencia);
                    } else if (tipo_estado == 0) {
                        jpacasc.Desactivar_ausencia(id_ausencia);
                    } else {
                        jpacasc.Eliminar_ausencia(id_ausencia, 0);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=10&mnu=17").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DESCARGOS / DISCIPLINA">
                case 13:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Disciplina_descargos");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_disciplina = Integer.parseInt(request.getParameter("idc"));
                        request.setAttribute("Id_disciplina", id_disciplina);
                    } catch (Exception e) {
                        request.setAttribute("Id_disciplina", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 14:
                    try {
                        id_disciplina = Integer.parseInt(request.getParameter("idc"));
                    } catch (Exception e) {
                        id_disciplina = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Cbx_tipo");
                    motivo = request.getParameter("Txt_motivo");
                    dias = Integer.parseInt(request.getParameter("Txt_dias"));
                    observacion = request.getParameter("Txt_descripcion");
                    salario_hora = request.getParameter("Txt_salario_hora");
                    if (id_disciplina > 0) {
                        proceso = jpacdcp.Modificar_disciplina(id_disciplina, documento + "", fecha, tipo, motivo, observacion, salario_hora, usuario_registro, dias);
                        if (proceso) {
                            if (tipo.equals("Sancion")) {
                                jpacasc.Modificar_ausencia(id_disciplina, documento + "", fecha, tipo, (dias * 8), "<b>Motivo :</b><br />" + motivo + "<hr />" + observacion, salario_hora, usuario_registro, 2);
                            }
                            request.setAttribute("Alerta", "Modificar_disciplina");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_disciplina");
                        }
                    } else {
                        proceso = jpacdcp.Registrar_disciplina(documento + "", fecha, tipo, motivo, observacion, salario_hora, usuario_registro, dias);
                        if (proceso) {
                            if (tipo.equals("Sancion")) {
                                jpacasc.Registrar_ausencia(documento + "", fecha, tipo, (dias * 8), "<b>Motivo :</b><br />" + motivo + "<hr />" + observacion, salario_hora, usuario_registro, 2);
                            }
                            request.setAttribute("Alerta", "Registro_disciplina");
                        } else {
                            request.setAttribute("Alerta", "Error_disciplina");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=13&mnu=18").forward(request, response);
                    break;
                case 15:
                    id_disciplina = Integer.parseInt(request.getParameter("Id_disciplina"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        jpacdcp.Activar_disciplina(id_disciplina);
                    } else if (tipo_estado == 0) {
                        jpacdcp.Desactivar_disciplina(id_disciplina);
                    } else {
                        jpacdcp.Eliminar_disciplina(id_disciplina);
                        jpacasc.Eliminar_ausencia(id_disciplina, 2);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=13&mnu=18").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="RETIROS">
                case 16:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Retiros");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_retiro = Integer.parseInt(request.getParameter("irt"));
                        request.setAttribute("Id_retiro", id_retiro);
                    } catch (Exception e) {
                        request.setAttribute("Id_retiro", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 17:
                    try {
                        id_retiro = Integer.parseInt(request.getParameter("irt"));
                    } catch (Exception e) {
                        id_retiro = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Cbx_tipo");
                    observacion = request.getParameter("Txt_descripcion");
                    if (id_retiro > 0) {
                        proceso = jpacrtr.Modificar_retiro(id_retiro, documento + "", fecha, tipo, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_retiro");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_retiro");
                        }
                    } else {
                        proceso = jpacrtr.Registrar_retiro(documento + "", fecha, tipo, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_retiro");
                        } else {
                            request.setAttribute("Alerta", "Error_retiro");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=16&mnu=19&irt=0").forward(request, response);
                    break;
                case 18:
                    id_retiro = Integer.parseInt(request.getParameter("Id_retiro"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        jpacrtr.Activar_retiro(id_retiro);
                        jpacrtr.Inactivar_empleado_retiro(id_retiro);
                    } else if (tipo_estado == 0) {
                        jpacrtr.Desactivar_retiro(id_retiro);
                    } else {
                        jpacrtr.Eliminar_retiro(id_retiro);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=16&mnu=19&irt=0").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DOTACIÓN">
                case 19:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Dotaciones");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_dotacion = Integer.parseInt(request.getParameter("idt"));
                        request.setAttribute("Id_dotacion", id_dotacion);
                    } catch (Exception e) {
                        request.setAttribute("Id_dotacion", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 20:
                    try {
                        id_dotacion = Integer.parseInt(request.getParameter("idt"));
                    } catch (Exception e) {
                        id_dotacion = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    asignacion = request.getParameter("Txt_asignacion_dotacion");
                    observacion = request.getParameter("Txt_descripcion");
                    if (id_dotacion > 0) {
                        proceso = jpacdtc.Modificar_dotacion(id_dotacion, documento + "", fecha, asignacion, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_dotacion");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_dotacion");
                        }
                    } else {
                        proceso = jpacdtc.Registrar_dotacion(documento + "", fecha, asignacion, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_dotacion");
                        } else {
                            request.setAttribute("Alerta", "Error_dotacion");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=19&mnu=20").forward(request, response);
                    break;
                case 21:
                    id_dotacion = Integer.parseInt(request.getParameter("Id_dotacion").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacdtc.Activar_dotacion(id_dotacion);
                    } else if (tipo_estado == 0) {
                        jpacdtc.Desactivar_dotacion(id_dotacion);
                    } else {
                        jpacdtc.Eliminar_dotacion(id_dotacion);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=19&mnu=20").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAPACITACION">
                case 22:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Capacitaciones");
                    int temp = 0;
                    try {
                        temp = Integer.parseInt(request.getParameter("temp").toString());
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        docx = Long.parseLong(request.getParameter("DocUSer"));
                        codx = Long.parseLong(request.getParameter("CodUser"));
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
                    request.setAttribute("txtDocument", docx);
                    request.setAttribute("txtCode", codx);
                    request.setAttribute("Id_capDetall", id_capacitacion_detalle);
                    if (temp == 1) {
                        request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    }
                    break;
                case 23:
                    try {
                        id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                        fecha = request.getParameter("Txt_fecha");
                        titulo = request.getParameter("Txt_titulo");
                        entidad = request.getParameter("Txt_entidad");
                        capacitador = request.getParameter("Txt_capacitador");
//                        folio = request.getParameter("Txt_folio");
                        duracion = Double.parseDouble(request.getParameter("Txt_duracion"));
                        observacion = request.getParameter("Txt_descripcion");
                        proceso = jpaccpc.Modificar_capacitacion(id_capacitacion, entidad, fecha, titulo, duracion, capacitador, observacion, folio, usuario_registro);
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
                        proceso = jpaccpc.Registrar_capacitacion(entidad, fecha, titulo, duracion, capacitador, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_capacitacion");
                        } else {
                            request.setAttribute("Alerta", "Error_capacitacion");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23").forward(request, response);
                    break;
                case 24:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    manual = request.getParameter("Txt_manual");

                    if (manual.equals("N/A")) {
                        documento = Long.parseLong(request.getParameter("Txt_documento").toString());
                        if (jpaccpc.Verificar_registro(id_capacitacion, documento) == 0) {
                            proceso = jpaccpc.Registrar_capacitacion_detalle_alt(id_capacitacion, documento, usuario_registro);
                        } else {
                            proceso = false;
                        }
                    } else if (manual.equals("External")) {
                        docu = Long.parseLong(request.getParameter("NmbDoc").toString());
                        name = request.getParameter("TxtName").toString();
                        cargo = request.getParameter("TxtCargo").toString();
                        cargo = "" + cargo + " (Personal Externo)";
                        if (jpaccpc.Verificar_registro(id_capacitacion, documento) == 0) {
                            proceso = jpaccpc.RegsiterUserExternal(id_capacitacion, docu, name, cargo, usuario_registro);
                        } else {
                            proceso = false;
                        }
                    } else {
                        documento = Long.parseLong(manual.split(" / ")[1]);
                        if (jpaccpc.Verificar_registro(id_capacitacion, documento) == 0) {
                            proceso = jpaccpc.Registrar_capacitacion_detalle(id_capacitacion, Long.parseLong(manual.split(" / ")[1]), manual.split(" / ")[0], manual.split(" / ")[3] + " / " + manual.split(" / ")[2], "", usuario_registro);
                        } else {
                            proceso = false;
                        }
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Registro_capacitación_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_capacitación_detalle");
                    }
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion).forward(request, response);
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
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23").forward(request, response);
                    break;
                case 26:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    id_capacitacion_detalle = Integer.parseInt(request.getParameter("icd"));
                    jpaccpc.Eliminar_capacitacion_detalle(id_capacitacion_detalle);
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion).forward(request, response);
                    break;
                case 35:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    id_capacitacion_detalle = Integer.parseInt(request.getParameter("idCapDetalle"));
                    docx = Long.parseLong(request.getParameter("txtDocument"));
                    codx = Long.parseLong(request.getParameter("txtCode"));

                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&idCapd=" + id_capacitacion_detalle + "&DocUSer=" + docx + "&CodUser=" + codx + "").forward(request, response);
                    break;
                case 36:
                    id_capacitacion = Integer.parseInt(request.getParameter("icp"));
                    id_capacitacion_detalle = Integer.parseInt(request.getParameter("idCapDetalle"));
                    idSignature = Integer.parseInt(request.getParameter("idSignature"));
                    result = jpaccpc.ActualizarFirmaCapacitacion(id_capacitacion_detalle, idSignature);
                    if (result) {
                        request.setAttribute("Alerta", "CapacitacionFirmada");
                    }
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&DocUSer=" + 0 + "&CodUser=" + 0 + "").forward(request, response);
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
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&DocUSer=" + 0 + "&CodUser=" + 0 + "").forward(request, response);
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
                    request.getRequestDispatcher("Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&DocUSer=" + 0 + "&CodUser=" + 0 + "").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EXAMENES">
                case 27:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Examenes");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_examen = Integer.parseInt(request.getParameter("iex"));
                        request.setAttribute("Id_examen", id_examen);
                    } catch (Exception e) {
                        request.setAttribute("Id_examen", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 28:
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    tipo = request.getParameter("Rdb_tipo");
                    concepto = request.getParameter("Rdb_concepto");
                    centro_medico = request.getParameter("Txt_centro_medico");
                    recomendacion = request.getParameter("Txt_recomendacion");
                    compromiso = request.getParameter("Txt_compromiso");
                    restriciones = request.getParameter("Txt_restriciones");
                    observacion = request.getParameter("Txt_descripcion");
                    examenes = request.getParameter("Txt_examenes");
                    try {
                        id_examen = Integer.parseInt(request.getParameter("iex"));
                        proceso = jpacexm.Modificar_examen(id_examen, documento + "", fecha, tipo, concepto, centro_medico, recomendacion, observacion, compromiso, restriciones, usuario_registro, examenes);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_examen");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_examen");
                        }
                    } catch (Exception e) {
                        proceso = jpacexm.Registrar_examen(documento + "", fecha, tipo, concepto, centro_medico, recomendacion, observacion, compromiso, restriciones, usuario_registro, examenes);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_examen");
                        } else {
                            request.setAttribute("Alerta", "Error_examen");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=27&mnu=25").forward(request, response);
                    break;
                case 29:
                    id_examen = Integer.parseInt(request.getParameter("Id_examen").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacexm.Activar_examen(id_examen);
                    } else if (tipo_estado == 0) {
                        jpacexm.Desactivar_examen(id_examen);
                    } else {
                        jpacexm.Eliminar_examen(id_examen);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=27&mnu=25").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EPP">
                case 30:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Seguimiento", "Epp");
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        id_epp = Integer.parseInt(request.getParameter("iep"));
                        request.setAttribute("Id_epp", id_epp);
                    } catch (Exception e) {
                        request.setAttribute("Id_epp", 0);
                    }
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 31:
                    try {
                        id_epp = Integer.parseInt(request.getParameter("iep"));
                    } catch (Exception e) {
                        id_epp = 0;
                    }
                    documento = Long.parseLong(request.getParameter("Txt_documento"));
                    fecha = request.getParameter("Txt_fecha");
                    asignacion = request.getParameter("Txt_asignacion_dotacion");
                    observacion = request.getParameter("Txt_descripcion");
                    if (id_epp > 0) {
                        proceso = jpacepp.Modificar_epp(id_epp, documento + "", fecha, asignacion, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_epp");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_epp");
                        }
                    } else {
                        proceso = jpacepp.Registrar_epp(documento + "", fecha, asignacion, observacion, usuario_registro);
                        if (proceso) {
                            request.setAttribute("Alerta", "Registro_epp");
                        } else {
                            request.setAttribute("Alerta", "Error_epp");
                        }
                    }
                    request.getRequestDispatcher("Seguimiento?opc=30&mnu=33").forward(request, response);
                    break;
                case 32:
                    id_epp = Integer.parseInt(request.getParameter("Id_epp").toString());
                    tipo_estado = Integer.parseInt(request.getParameter("Estado").toString());
                    if (tipo_estado == 1) {
                        jpacepp.Activar_epp(id_epp);
                    } else if (tipo_estado == 0) {
                        jpacepp.Desactivar_epp(id_epp);
                    } else {
                        jpacepp.Eliminar_epp(id_epp);
                    }
                    request.getRequestDispatcher("Seguimiento?opc=30&mnu=33").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MARCACIONES">
                case 33:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    try {
                        formulario = Integer.parseInt(request.getParameter("fml"));
                        request.setAttribute("Formulario", formulario);
                    } catch (Exception e) {
                        request.setAttribute("Formulario", 0);
                    }
                    try {
                        documento = Integer.parseInt(request.getParameter("dcm"));
                        request.setAttribute("Documento", documento);
                    } catch (Exception e) {
                        request.setAttribute("Documento", 0);
                    }
                    try {
                        id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                        request.setAttribute("Id_area", id_area);
                    } catch (Exception e) {
                        request.setAttribute("Id_area", 0);
                    }
                    try {
                        id_cargo = Integer.parseInt(request.getParameter("Cbx_cargo"));
                        request.setAttribute("Id_cargo", id_cargo);
                    } catch (Exception e) {
                        request.setAttribute("Id_cargo", 0);
                    }
                    try {
                        tipo_consulta = Integer.parseInt(request.getParameter("Rdb_tipo_consulta"));
                        request.setAttribute("Tipo_consulta", tipo_consulta);
                    } catch (Exception e) {
                        request.setAttribute("Tipo_consulta", 0);
                    }
                    try {
                        dia_ini = Integer.parseInt(request.getParameter("Txt_dia_inicial"));
                        request.setAttribute("Dia_inicio", dia_ini);
                    } catch (Exception e) {
                        request.setAttribute("Dia_inicio", 0);
                    }
                    try {
                        dia_fin = Integer.parseInt(request.getParameter("Txt_dia_final"));
                        request.setAttribute("Dia_fin", dia_fin);
                    } catch (Exception e) {
                        request.setAttribute("Dia_fin", 0);
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("Modulo"));
                        request.setAttribute("Modulo", modulo);
                    } catch (Exception e) {
                        request.setAttribute("Modulo", 1);
                    }
                    fecha_ajuste = request.getParameter("faj");
                    request.setAttribute("Fecha_ajuste", fecha_ajuste);
                    request.setAttribute("Seguimiento", "Marcaciones");
                    request.getRequestDispatcher("Seguimiento.jsp").forward(request, response);
                    break;
                case 34:
                    //var filtro
                    tipo_consulta = Integer.parseInt(request.getParameter("tcs"));
                    id_area = Integer.parseInt(request.getParameter("iar"));
                    id_cargo = Integer.parseInt(request.getParameter("icgs"));
                    dia_ini = Integer.parseInt(request.getParameter("din"));
                    dia_fin = Integer.parseInt(request.getParameter("dfn"));
                    //var modificacion
                    documento = Long.parseLong(request.getParameter("dcm"));
                    icg = Integer.parseInt(request.getParameter("icg"));
                    anio = Integer.parseInt(request.getParameter("anio"));
                    mes = Integer.parseInt(request.getParameter("mes"));
                    dia = Integer.parseInt(request.getParameter("dia"));
                    fecha_inicial = request.getParameter("fin");
                    hora_inicial = request.getParameter("hin");
                    fecha_final = request.getParameter("ffn");
                    hora_final = request.getParameter("hfn");
                    observacion = request.getParameter("obs");
                    try {
                        modulo = Integer.parseInt(request.getParameter("Modulo"));
                        request.setAttribute("Modulo", modulo);
                    } catch (Exception e) {
                        request.setAttribute("Modulo", 1);
                    }
                    if (modulo == 1) {
                        jpacpsn.Cambiar_marcacion(documento + "", icg, anio, mes, dia, fecha_inicial + " " + hora_inicial, fecha_final + " " + hora_final);
                        jpacpsn.Calculos_marcacion(documento + "", icg + "", anio, mes + "", dia + "", "<b>" + usuario_registro + " : </b>" + observacion + " | ");
                    } else {
                        jpacpsn.Cambiar_marcacionCafe(documento + "", icg, anio, mes, dia, fecha_inicial + " " + hora_inicial, fecha_final + " " + hora_final);
                        lst_verificacion = jpacpsn.Verificacion_existencia_Cafe(String.valueOf(documento), String.valueOf(icg), anio, String.valueOf(mes));
                        if (lst_verificacion != null) {
                            Object[] obj_verificacion = (Object[]) lst_verificacion.get(0);
                            if (Integer.parseInt(obj_verificacion[8].toString()) == 0) {
                                jpacpsn.Registrar_seguimiento_anio_mes(String.valueOf(documento), String.valueOf(icg), obj_verificacion[3].toString(), "CAFE", "Pendiente", anio, String.valueOf(mes));
                            }
                        }
                        jpacpsn.Calculos_marcacionCafe(documento + "", icg + "", anio, mes + "", dia + "", "<b>" + usuario_registro + " : </b>" + observacion + " | ");
                    }
                    request.getRequestDispatcher("Seguimiento?opc=33&mnu=38&faj=&fml=0&Cbx_area=" + id_area + "&Cbx_cargo=" + id_cargo + "&Rdb_tipo_consulta=" + tipo_consulta + "&Txt_dia_inicial=" + dia_ini + "&Txt_dia_final=" + dia_fin + "&Modulo=" + modulo).forward(request, response);
                    break;
//</editor-fold>
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
