package Servlets;

import Controladores.EtapaJpaController;
import Controladores.FaseJpaController;
import Controladores.MemoriaCJpaController;
import Controladores.MemoriaDJpaController;
import Controladores.ProyectoJpaController;
import Controladores.HerramentalCJpaController;
import Controladores.FormulaCJpaController;
import Controladores.EntradaOtroJpaController;
import Controladores.PruebaCJpaController;
import Methods.Distribucion;
import Methods.Email;
import Methods.Directory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Proyecto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        //<editor-fold defaultstate="collapsed" desc="VARIABLES GLOBALES">
        ProyectoJpaController jpa_proyecto = new ProyectoJpaController();
        MemoriaCJpaController jpa_memoriac = new MemoriaCJpaController();
        MemoriaDJpaController jpa_memoriad = new MemoriaDJpaController();
        EtapaJpaController jpa_etapa = new EtapaJpaController();
        FaseJpaController jpa_fase = new FaseJpaController();
        HerramentalCJpaController jpa_herramental_C = new HerramentalCJpaController();
        FormulaCJpaController jpa_formula_C = new FormulaCJpaController();
        EntradaOtroJpaController jpa_entrada_otro = new EntradaOtroJpaController();
        PruebaCJpaController jpa_prueba_C = new PruebaCJpaController();
        Distribucion mtddtb = new Distribucion();
        Email mtdmail = new Email();
        Directory mtdDir = new Directory();
        int opc = Integer.parseInt(request.getParameter("opc").toString());
        int id_proyecto = 0, Templdd = 0, tipo_proyecto = 0, total_entradas = 0, otras_entradas = 0, estado = 0, tipo_consulta = 0, estadoM = 0, TempM = 0, id_memoria = 0, numeral = 0, id_memoria_D = 0, estadoM_D = 0, filtrado = 0, resp = 0, actividad = 0, Tipo_Entrada = 0, cant_capa = 0, id_entrada = 0, TempE = 0, id_prueba = 0, TempPRU = 0, id_pru = 0, comunicar = 0;
        String fecha = "", numero = "", proyecto = "", entrada = "", t_entrada = "", uso_previsto = "", usu_registro = "", mail = "", pass_mail = "", t_entrada_old = "", f_salida = "", participe = "", usuario = "", fecha_registro = "", observaciones = "", texto = "", formato_antiguo = "", quitar = "", tipo_memoria_log = "", tipo_adj = "", herramental = "", num_herramental = "", num_plano, tiempo = "", num_tipo_herramental = "", t_herramental = "", version = "", producto = "", codigo = "", Tipo_prod = "", Tipo_mat_prod = "", estructura_prod = "", capa = "", asunto_entrada = "", versionS = "", prog = "", tipo_prueba = "", tipo_envio, atender = "";
        boolean proceso = false;
        boolean envio = false;
        List lst_proyecto = null;
        List lst_etapas_proyecto = null;
        List lst_id_etapa = null;
        List lst_id_fase = null;
        List lst_herramentalC = null, lst_ultimo_pyt = null;
        //</editor-fold>

        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES DE SESION">
            HttpSession sesion = request.getSession();
            int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            usu_registro = sesion.getAttribute("Usuario").toString();
            mail = sesion.getAttribute("Mail").toString();
            pass_mail = sesion.getAttribute("Pass_mail").toString();
            String Usuario = sesion.getAttribute("Usuario").toString().toUpperCase();
            String user_act = sesion.getAttribute("Usuario_cargo").toString().toUpperCase();
            //</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO GENERAL">
                    request.setAttribute("Proyecto", "Proyectos");
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        tipo_consulta = Integer.parseInt(request.getParameter("Rdb_consulta").toString());
                        request.setAttribute("Consulta", tipo_consulta);
                    } catch (Exception e) {
                        request.setAttribute("Consulta", 0);
                    }
                    try {
                        Templdd = Integer.parseInt(request.getParameter("Templdd"));
                    } catch (Exception ex) {
                        Templdd = 0;
                    }
                    request.setAttribute("ipy", id_proyecto);
                    request.setAttribute("Templdd", Templdd);
                    request.getRequestDispatcher("Proyecto.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTAR PROYECTO">
                    fecha = request.getParameter("Txt_fecha");
                    numero = request.getParameter("txt_numero");
                    proyecto = request.getParameter("txt_proyecto");
                    int arr_r_entrada[] = new int[6];
                    /*Cantidad Entrada*/
                    for (int id = 0; id < arr_r_entrada.length; id++) {
                        if ((request.getParameter("arr_entrada[" + id + "]")) != null) {
                            entrada = request.getParameter("arr_entrada[" + id + "]").toString();
                            if (t_entrada == null ? "" == null : t_entrada.equals("")) {
                                t_entrada = entrada;
                            } else {
                                t_entrada = t_entrada + ";" + entrada;
                            }
                        }
                    }
                    uso_previsto = request.getParameter("txt_uso_previsto");
                    tipo_proyecto = Integer.parseInt(request.getParameter("Rdb_tipo_consulta").toString());
                    proceso = jpa_proyecto.Registrar_proyecto(usu_registro, fecha, numero, proyecto, uso_previsto, t_entrada, "[" + id_usuario + "]", tipo_proyecto);
                    if (proceso) {
                        mtdmail.mail_registro_proyecto(usu_registro, fecha, numero, proyecto, uso_previsto, mail, pass_mail);

                        lst_ultimo_pyt = jpa_proyecto.traer_ultimo_proyecto();
                        if (lst_ultimo_pyt != null) {
                            Object[] ulti = (Object[]) lst_ultimo_pyt.get(0);
                            // Crear carpeta con el nombre de ulti[0]
                            mtdDir.crearCarpeta(ulti[1].toString());
                            lst_etapas_proyecto = jpa_memoriac.Traer_etapa(Integer.parseInt(ulti[0].toString()));
                            if (lst_etapas_proyecto == null || lst_etapas_proyecto.size() == 0) {
                                lst_id_etapa = jpa_etapa.Consultar_etapa_a();
                                for (int i = 0; i < lst_id_etapa.size(); i++) {
                                    Object[] obj_lst_id_etapa = (Object[]) lst_id_etapa.get(i);
                                    lst_id_fase = jpa_fase.Consultar_fase_a((Integer) obj_lst_id_etapa[0]);
                                    for (int j = 0; j < lst_id_fase.size(); j++) {
                                        Object[] obj_lst_id_fase = (Object[]) lst_id_fase.get(j);
                                        jpa_memoriac.Registrar_memoria_c(usuario, Integer.parseInt(ulti[0].toString()), (Integer) obj_lst_id_etapa[0], (Integer) obj_lst_id_fase[0]);
                                    }
                                }
                            }
                        }
                        request.setAttribute("Alerta", "Registro_proyecto");
                    } else {
                        request.setAttribute("Alerta", "Error_proyecto");
                    }

                    request.getRequestDispatcher("Proyecto?opc=1&ipy=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PROYECTO">
                    id_proyecto = Integer.parseInt(request.getParameter("id_proyecto").toString());
                    fecha = request.getParameter("Txt_fecha").toString();
                    numero = request.getParameter("txt_numero").toString();
                    proyecto = request.getParameter("txt_proyecto").toString();
                    total_entradas = Integer.parseInt(request.getParameter("Total_entradas").toString());
                    otras_entradas = Integer.parseInt(request.getParameter("Otras_entradas").toString());
                    //int arr_m_entrada[] = new int[total_entradas];
                    for (int id = 0; id < total_entradas; id++) {
                        if ((request.getParameter("arr_entrada[" + id + "]")) != null) {
                            entrada = request.getParameter("arr_entrada[" + id + "]").toString();
                            if (t_entrada_old == null ? "" == null : t_entrada_old.equals("")) {
                                t_entrada_old = entrada;
                            } else {
                                t_entrada_old = t_entrada_old + ";" + entrada;
                            }
                        }
                    }
                    for (int i = 1; i <= otras_entradas; i++) {
                        try {
                            entrada = request.getParameter("Entrada_" + i + "").toString();
                            if (t_entrada == null ? "" == null : t_entrada.equals("")) {
                                t_entrada = entrada;
                            } else {
                                t_entrada = t_entrada + ";" + entrada;
                            }
                        } catch (Exception e) {
                        }
                    }
                    lst_proyecto = jpa_proyecto.Traer_t_entrada(id_proyecto);
                    Object[] obj_t_entrada = (Object[]) lst_proyecto.get(0);
                    //t_entrada_old = obj_t_entrada[1].toString();
                    uso_previsto = request.getParameter("txt_uso_previsto").toString();
                    t_entrada = t_entrada_old + ";" + t_entrada;
                    tipo_proyecto = Integer.parseInt(request.getParameter("Rdb_tipo_consulta").toString());
                    proceso = jpa_proyecto.Modificar_proyecto(usu_registro, fecha, numero, proyecto, uso_previsto, t_entrada.replace(";;", ";"), id_proyecto, tipo_proyecto);
                    //proceso = obj_proyecto.modificar_proyecto(usu_registro, fecha, numero, proyecto, uso_previsto, t_entrada, id_proyecto);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_proyecto");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_proyecto");
                    }
                    request.getRequestDispatcher("Proyecto?opc=1").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="LISTA DE DISTRIBUCIÓN">
                    id_proyecto = Integer.parseInt(request.getParameter("Id_proyecto").toString());
                    String[] personasSeleccionadas = request.getParameterValues("personas");
                    if (personasSeleccionadas == null) {
                        participe = "";
                    } else {
                        participe = String.join("", personasSeleccionadas);
                    }

                    jpa_proyecto.Lista_distribucion(id_proyecto, participe);
                    request.setAttribute("Alerta", "Lista_distribucion");
                    request.getRequestDispatcher("Proyecto?opc=1").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVAR O INACTIVAR PROYECTO">
                    id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    jpa_proyecto.Estado_proyecto(id_proyecto, estado);
                    request.getRequestDispatcher("Proyecto?opc=1").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADOS DE PROYECTOS Y FIRMAR">
                    id_proyecto = Integer.parseInt(request.getParameter("id_proyecto").toString());
                    f_salida = request.getParameter("f_salida").toString();
                    if (f_salida.equals("CANCELADO")) {
                        request.getRequestDispatcher("Proyecto?opc=1&ipy=0&Templdd=98").forward(request, response);
                    } else if (f_salida.equals("FINALIZADO")) {
                        proceso = jpa_proyecto.Estado_f_salida_revision(id_proyecto, f_salida, Usuario);
                        request.getRequestDispatcher("Proyecto?opc=1&ipy=0").forward(request, response);
                    } else {
                        proceso = jpa_proyecto.Estado_f_salida(id_proyecto, f_salida);
                        request.getRequestDispatcher("Proyecto?opc=1&ipy=0").forward(request, response);
                    }
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="IR A LAS MEMORIAS">
                    request.setAttribute("Proyecto", "Memoria");
                    id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM"));
                    } catch (Exception ex) {
                        estadoM = 0;
                    }
                    try {
                        TempM = Integer.parseInt(request.getParameter("TempM"));
                    } catch (Exception ex) {
                        TempM = 0;
                    }
                    try {
                        id_memoria = Integer.parseInt(request.getParameter("cba_num").toString());
                    } catch (Exception e) {
                        id_memoria = 0;
                    }
                    try {
                        filtrado = Integer.parseInt(request.getParameter("options").toString());
                    } catch (Exception e) {
                        filtrado = 0;
                    }
                    try {
                        resp = Integer.parseInt(request.getParameter("resp").toString());
                    } catch (Exception e) {
                        resp = 0;
                    }
                    try {
                        actividad = Integer.parseInt(request.getParameter("cant_act").toString());
                    } catch (Exception e) {
                        actividad = 0;
                    }
                    try {
                        tipo_adj = request.getParameter("ver_adj");
                    } catch (Exception e) {
                        tipo_adj = "";
                    }
                    try {
                        tipo_envio = request.getParameter("envio");
                    } catch (Exception e) {
                        tipo_envio = "";
                    }

                    if (TempM == 11) {
                        request.setAttribute("Alerta", "Correo_lista_distribucion");
                    } else if (TempM == 12) {
                        request.setAttribute("Alerta", "Error_Correo_lista_distribucion");
                    }

                    if (TempM == 101) {
                        request.setAttribute("Alerta", "actividad_proceso");
                    } else if (TempM == 102) {
                        request.setAttribute("Alerta", "actividad_revision");
                    } else if (TempM == 103) {
                        request.setAttribute("Alerta", "actividad_finalizacion");
                    } else if (TempM == 100) {
                        request.setAttribute("Alerta", "error_estado_actividad");
                    }

                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM);
                    request.setAttribute("TempM", TempM);
                    request.setAttribute("cba_num", id_memoria);
                    request.setAttribute("options", filtrado);
                    request.setAttribute("resp", resp);
                    request.setAttribute("cant_act", actividad);
                    request.setAttribute("ver_adj", tipo_adj);
                    request.getRequestDispatcher("Memorias.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR #">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        id_memoria_D = Integer.parseInt(request.getParameter("id_memoria_d").toString());
                    } catch (Exception e) {
                        id_memoria_D = 0;
                    }
                    try {
                        numeral = Integer.parseInt(request.getParameter("numeral").toString());
                    } catch (Exception e) {
                        numeral = 0;
                    }
                    try {
                        estadoM_D = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estadoM_D = 0;
                    }

                    id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                    id_memoria_D = Integer.parseInt(request.getParameter("id_memoria_d").toString());

                    numeral = Integer.parseInt(request.getParameter("numeral").toString());
                    estadoM_D = Integer.parseInt(request.getParameter("estado").toString());
                    jpa_memoriad.Log_memoria_d(id_memoria_D, "AUTOR");
                    proceso = jpa_memoriad.Modificar_numeral(numeral, id_memoria_D);
                    mtddtb.Listado_distribucion(id_proyecto, participe);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_numeral_detalle_proyecto");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_numeral_detalle_proyecto");
                    }

                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM_D);
                    request.getRequestDispatcher("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR MEMORIAS">
                    try {
                        estadoM_D = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estadoM_D = 0;
                    }
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("id_usuario").toString());
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    try {
                        fecha_registro = request.getParameter("fecha_reg");
                    } catch (Exception e) {
                        fecha_registro = "";
                    }
                    try {
                        numeral = Integer.parseInt(request.getParameter("numeral").toString());
                    } catch (Exception e) {
                        numeral = 0;
                    }
                    try {
                        observaciones = request.getParameter("observacion");
                    } catch (Exception e) {
                        observaciones = null;
                    }

                    String[] personasSeleccionadasM = request.getParameterValues("personas");
                    if (personasSeleccionadasM == null) {
                        participe = "";
                    } else {
                        participe = String.join("", personasSeleccionadasM);
                    }

                    mtddtb.Listado_distribucion(id_proyecto, participe);
                    if (participe.equals("")) {
                        proceso = jpa_memoriad.Registrar_memoria_d(id_usuario, numeral, observaciones, fecha_registro, participe, 3);
                    } else {
                        proceso = jpa_memoriad.Registrar_memoria_d(id_usuario, numeral, observaciones, fecha_registro, participe, 1);
                        mtdmail.mail_responsabilidad_autoridad(id_proyecto, numeral, mail, pass_mail);
                    }

                    if (proceso) {
                        if (!participe.equals("")) {
                            request.setAttribute("Alerta", "Registrar_detalle_proyecto_resp");
                        } else {
                            request.setAttribute("Alerta", "Registrar_detalle_proyecto");
                        }
                    } else if (!proceso) {
                        if (!participe.equals("")) {
                            request.setAttribute("Alerta", "Error_registrar_detalle_proyecto_resp");
                        } else {
                            request.setAttribute("Alerta", "Error_registrar_detalle_proyecto");
                        }
                    }

                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM_D);
                    request.getRequestDispatcher("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="EDITAR MEMORIAS">
                    try {
                        estadoM_D = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estadoM_D = 0;
                    }
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("id_usuario").toString());
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    try {
                        id_memoria = Integer.parseInt(request.getParameter("id_memoria").toString());
                    } catch (Exception e) {
                        id_memoria = 0;
                    }
                    try {
                        fecha_registro = request.getParameter("fecha_reg");
                    } catch (Exception e) {
                        fecha_registro = "";
                    }
                    try {
                        numeral = Integer.parseInt(request.getParameter("numeral").toString());
                    } catch (Exception e) {
                        numeral = 0;
                    }
                    try {
                        observaciones = request.getParameter("observacion");
                    } catch (Exception e) {
                        observaciones = null;
                    }

                    String[] personasSeleccionadasModi = request.getParameterValues("personas");
                    if (personasSeleccionadasModi == null) {
                        participe = "";
                    } else {
                        participe = String.join("", personasSeleccionadasModi);
                    }

                    jpa_memoriad.Log_memoria_d(id_memoria, "AUTOR");
                    proceso = jpa_memoriad.Modificar_memoria_d(id_memoria, observaciones, fecha_registro, id_usuario + "", participe, numeral);

                    if (participe.equals("")) {
                        jpa_memoriad.Cambiar_estado_actividad(id_memoria, 3);
                    } else {
                        mtdmail.mail_responsabilidad_autoridad(id_proyecto, numeral, mail, pass_mail);
                        jpa_memoriad.Cambiar_estado_actividad(id_memoria, 1);
                    }

                    mtddtb.Listado_distribucion(id_proyecto, participe);
                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_detalle_proyecto");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_detalle_proyecto");
                    }
                    request.getRequestDispatcher("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="RESPONDER ACTIVIDAD">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }

                    try {
                        estadoM_D = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estadoM_D = 0;
                    }

                    try {
                        id_memoria_D = Integer.parseInt(request.getParameter("id_memoria").toString());
                    } catch (Exception e) {
                        id_memoria_D = 0;
                    }

                    try {
                        id_usuario = Integer.parseInt(request.getParameter("id_usuario").toString());
                    } catch (Exception e) {
                        id_usuario = 0;
                    }

                    try {
                        usuario = request.getParameter("usuario");
                    } catch (Exception e) {
                        usuario = "";
                    }

                    try {
                        fecha_registro = request.getParameter("fecha_reg");
                    } catch (Exception e) {
                        fecha_registro = "";
                    }

                    try {
                        observaciones = request.getParameter("observacion");
                    } catch (Exception e) {
                        observaciones = "";
                    }

                    try {
                        comunicar = Integer.parseInt(request.getParameter("Cbx_enviar_autor").toString());
                    } catch (Exception e) {
                        comunicar = 0;
                    }

                    try {
                        formato_antiguo = request.getParameter("form_ant");
                    } catch (Exception e) {
                        formato_antiguo = "";
                    }

                    try {
                        tipo_memoria_log = request.getParameter("Tipo_log");
                    } catch (Exception e) {
                        tipo_memoria_log = "";
                    }

                    atender = usuario + "---" + fecha_registro + "---" + observaciones;

                    if (formato_antiguo == null || formato_antiguo.equals("") || formato_antiguo.equals("null")) {
                        texto = "<b>Responsable :</b>" + usuario + "<br /><b>Fecha :</b>" + fecha_registro + "<br /><b>Respuesta :</b>" + observaciones + "<hr />";
                    } else {
                        texto = formato_antiguo + "\n<b>Responsable :</b>" + usuario + "<br /><b>Fecha :</b>" + fecha_registro + "<br /><b>Respuesta :</b>" + observaciones + "<hr />";
                    }

                    if (tipo_memoria_log.equals("RESPONSABLE")) {
                        jpa_memoriad.Log_memoria_d(id_memoria_D, "RESPONSABLE");
                    }

                    proceso = jpa_memoriad.Responder_actividad(id_memoria_D, texto, id_usuario + "");

                    if (comunicar == 1) {
                        mtdmail.mail_solucion_responsabilidad_autoridad_parcial(id_proyecto, id_memoria_D, mail, pass_mail, atender, user_act);

                    }

                    if (proceso) {
                        request.setAttribute("Alerta", "Atender_actividad");
                    } else {
                        request.setAttribute("Alerta", "Error_atender_actividad");
                    }

                    request.getRequestDispatcher("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="EDITAR RESPUESTA DE ACTIVIDAD">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }

                    try {
                        estadoM_D = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estadoM_D = 0;
                    }

                    try {
                        id_memoria_D = Integer.parseInt(request.getParameter("id_memoria").toString());
                    } catch (Exception e) {
                        id_memoria_D = 0;
                    }

                    try {
                        usuario = request.getParameter("usuario");
                    } catch (Exception e) {
                        usuario = "";
                    }

                    try {
                        fecha_registro = request.getParameter("fecha_reg");
                    } catch (Exception e) {
                        fecha_registro = "";
                    }

                    try {
                        observaciones = request.getParameter("observacion");
                    } catch (Exception e) {
                        observaciones = "";
                    }

                    try {
                        comunicar = Integer.parseInt(request.getParameter("Cbx_enviar_autor").toString());
                    } catch (Exception e) {
                        comunicar = 0;
                    }

                    try {
                        formato_antiguo = request.getParameter("form_ant");
                    } catch (Exception e) {
                        formato_antiguo = "";
                    }

                    try {
                        tipo_memoria_log = request.getParameter("Tipo_log");
                    } catch (Exception e) {
                        tipo_memoria_log = "";
                    }

                    quitar = observaciones.replace("&aacute;", "á").replace("&eacute;", "é").replace("&iacute;", "í").replace("&oacute;", "ó").replace("&uacute;", "ú").replace("&Aacute;", "Á").replace("&Eacute;", "É").replace("&Iacute;", "Í").replace("&Oacute;", "Ó").replace("&Uacute;", "Ú").replace("&ntilde;", "ñ").replace("&Ntilde;", "Ñ");

                    texto = "<b>Responsable :</b>" + usuario + "<br /><b>Fecha :</b>" + fecha_registro + "<br /><b>Respuesta :</b>" + quitar + "<hr />";

                    atender = usuario + "---" + fecha_registro + "---" + observaciones;

                    if (tipo_memoria_log.equals("RESPONSABLE")) {
                        jpa_memoriad.Log_memoria_d(id_memoria_D, "RESPONSABLE");
                    }

                    proceso = jpa_memoriad.Cambiar_actividad_registrada(id_memoria_D, formato_antiguo + "<hr />", texto);

                    if (comunicar == 1) {
                        mtdmail.mail_solucion_responsabilidad_autoridad_parcial(id_proyecto, id_memoria_D, mail, pass_mail, atender, user_act);
                    }

                    if (proceso) {
                        request.setAttribute("Alerta", "Atender_actividad");
                    } else {
                        request.setAttribute("Alerta", "Error_atender_actividad");
                    }

                    request.getRequestDispatcher("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR DE ESTADO LA MEMORIA">
                    id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                    id_memoria_D = Integer.parseInt(request.getParameter("id_memoria").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    estadoM_D = Integer.parseInt(request.getParameter("estadoM").toString());
                    proceso = jpa_memoriad.Cambiar_estado_actividad(id_memoria_D, estado);
                    if (estado == 2) {
                        mtdmail.mail_solucion_responsabilidad_autoridad(id_proyecto, id_memoria_D, mail, pass_mail, user_act);
                    }

                    if (proceso) {
                        if (estado == 1) {
                            response.sendRedirect("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "&TempM=101");
                        } else if (estado == 2) {
                            response.sendRedirect("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "&TempM=102");
                        } else if (estado == 3) {
                            response.sendRedirect("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "&TempM=103");
                        }
                    } else {
                        response.sendRedirect("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM_D + "&TempM=100");
                    }
                    //</editor-fold>
                    break;
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="IR A LAS ENTRADAS">
                    request.setAttribute("Proyecto", "Entradas_proyectos");
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        Tipo_Entrada = Integer.parseInt(request.getParameter("T_Entrada").toString());
                    } catch (Exception e) {
                        Tipo_Entrada = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM"));
                    } catch (Exception ex) {
                        estadoM = 0;
                    }
                    try {
                        id_entrada = Integer.parseInt(request.getParameter("id_E").toString());
                    } catch (Exception e) {
                        id_entrada = 0;
                    }
                    try {
                        TempE = Integer.parseInt(request.getParameter("tempE").toString());
                    } catch (Exception e) {
                        TempE = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado").toString());
                    } catch (Exception e) {
                        estado = 0;
                    }
                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("T_Entrada", Tipo_Entrada);
                    request.setAttribute("estadoM", estadoM);
                    request.setAttribute("id_E", id_entrada);
                    request.setAttribute("tempE", TempE);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Entradas.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADAS">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        Tipo_Entrada = Integer.parseInt(request.getParameter("T_Entrada").toString());
                    } catch (Exception e) {
                        Tipo_Entrada = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM"));
                    } catch (Exception ex) {
                        estadoM = 0;
                    }
                    try {
                        fecha = request.getParameter("fecha").toString();
                    } catch (Exception e) {
                        fecha = "";
                    }
                    try {
                        herramental = request.getParameter("herramental").toString();
                    } catch (Exception e) {
                        herramental = "";
                    }
                    try {
                        num_herramental = request.getParameter("n_herramental").toString();
                    } catch (Exception e) {
                        num_herramental = "";
                    }
                    try {
                        num_plano = request.getParameter("n_plano").toString();
                    } catch (Exception e) {
                        num_plano = "";
                    }
                    try {
                        tiempo = request.getParameter("t_estimado").toString();
                    } catch (Exception e) {
                        tiempo = "";
                    }
                    try {
                        num_tipo_herramental = request.getParameter("n_t_herramental").toString();
                    } catch (Exception e) {
                        num_tipo_herramental = "";
                    }
                    try {
                        t_herramental = request.getParameter("cbx_t_herramental").toString();
                    } catch (Exception e) {
                        t_herramental = "";
                    }
                    try {
                        observaciones = request.getParameter("observacion").toString();
                    } catch (Exception e) {
                        observaciones = "";
                    }
                    try {
                        version = request.getParameter("version").toString();
                    } catch (Exception e) {
                        version = "";
                    }

                    try {
                        producto = request.getParameter("producto").toString();
                    } catch (Exception e) {
                        producto = "";
                    }
                    try {
                        codigo = request.getParameter("codigo").toString();
                    } catch (Exception e) {
                        codigo = "";
                    }
                    try {
                        Tipo_prod = request.getParameter("tipo").toString();
                    } catch (Exception e) {
                        Tipo_prod = "";
                    }
                    try {
                        Tipo_mat_prod = request.getParameter("tipo_M").toString();
                    } catch (Exception e) {
                        Tipo_mat_prod = "";
                    }
                    try {
                        estructura_prod = request.getParameter("estructura").toString();
                    } catch (Exception e) {
                        estructura_prod = "";
                    }
                    try {
                        capa = request.getParameter("capa").toString();
                    } catch (Exception e) {
                        capa = "";
                    }
                    try {
                        cant_capa = Integer.parseInt(request.getParameter("c_capa").toString());
                    } catch (Exception e) {
                        cant_capa = 0;
                    }

                    try {
                        asunto_entrada = request.getParameter("asunto_ent").toString();
                    } catch (Exception e) {
                        asunto_entrada = "";
                    }

                    if (Tipo_Entrada == 1) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA PROYECTOS">
                        request.setAttribute("Id_proyecto", id_proyecto);
                        request.setAttribute("T_Entrada", Tipo_Entrada);
                        request.setAttribute("estadoM", estadoM);
                        request.setAttribute("fecha", fecha);
                        request.setAttribute("herramental", herramental);
                        request.setAttribute("n_herramental", num_herramental);
                        request.setAttribute("n_plano", num_plano);
                        request.setAttribute("t_estimado", tiempo);
                        request.setAttribute("n_t_herramental", num_tipo_herramental);
                        request.setAttribute("cbx_t_herramental", t_herramental);
                        request.setAttribute("observacion", observaciones);
                        request.setAttribute("version", version);

                        proceso = jpa_herramental_C.insertar_herrmental_c(usu_registro, fecha, herramental, num_herramental, num_plano, tiempo, t_herramental, num_tipo_herramental, observaciones, id_proyecto, version);

                        if (proceso) {
                            request.setAttribute("Alerta", "Registrar_entradas_proyectos");
                        } else {
                            request.setAttribute("Alerta", "Error_registrar_entradas_proyectos");
                        }
                        //</editor-fold>
                    } else if (Tipo_Entrada == 2) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA PRODUCCION">
                        request.setAttribute("Id_proyecto", id_proyecto);
                        request.setAttribute("T_Entrada", Tipo_Entrada);
                        request.setAttribute("estadoM", estadoM);
                        request.setAttribute("fecha", fecha);
                        request.setAttribute("producto", producto);
                        request.setAttribute("codigo", codigo);
                        request.setAttribute("tipo", Tipo_prod);
                        request.setAttribute("tipo_M", Tipo_mat_prod);
                        request.setAttribute("estructura", estructura_prod);
                        request.setAttribute("capa", capa);
                        request.setAttribute("c_capa", cant_capa);
                        request.setAttribute("observacion", observaciones);

                        proceso = jpa_formula_C.insertar_formula_c(usu_registro, producto, codigo, fecha, Tipo_prod, Tipo_mat_prod, estructura_prod, capa, cant_capa, id_proyecto, observaciones);

                        if (proceso) {
                            request.setAttribute("Alerta", "Registrar_entrada_produccion");
                        } else {
                            request.setAttribute("Alerta", "Error_registrar_entrada_produccion");
                        }
                        //</editor-fold>
                    } else if (Tipo_Entrada == 3) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA OTROS">
                        request.setAttribute("Id_proyecto", id_proyecto);
                        request.setAttribute("T_Entrada", Tipo_Entrada);
                        request.setAttribute("estadoM", estadoM);
                        request.setAttribute("fecha", fecha);
                        request.setAttribute("asunto_ent", asunto_entrada);
                        request.setAttribute("observacion", observaciones);

                        proceso = jpa_entrada_otro.Registrar_otra_entrada(id_proyecto, fecha, asunto_entrada, observaciones, usu_registro);

                        if (proceso) {
                            request.setAttribute("Alerta", "Registrar_otras_entradas");
                        } else {
                            request.setAttribute("Alerta", "Error_otras_entradas");
                        }
                        //</editor-fold>
                    }
                    request.getRequestDispatcher("Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=" + Tipo_Entrada + "&estadoM=" + estadoM + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 16:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR ENTRADAS">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        Tipo_Entrada = Integer.parseInt(request.getParameter("T_Entrada").toString());
                    } catch (Exception e) {
                        Tipo_Entrada = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM"));
                    } catch (Exception ex) {
                        estadoM = 0;
                    }
                    try {
                        TempE = Integer.parseInt(request.getParameter("tempE").toString());
                    } catch (Exception e) {
                        TempE = 0;
                    }

                    try {
                        id_entrada = Integer.parseInt(request.getParameter("id_E").toString());
                    } catch (Exception e) {
                        id_entrada = 0;
                    }
                    try {
                        fecha = request.getParameter("fecha").toString();
                    } catch (Exception e) {
                        fecha = "";
                    }
                    try {
                        herramental = request.getParameter("herramental").toString();
                    } catch (Exception e) {
                        herramental = "";
                    }
                    try {
                        num_herramental = request.getParameter("n_herramental").toString();
                    } catch (Exception e) {
                        num_herramental = "";
                    }
                    try {
                        num_plano = request.getParameter("n_plano").toString();
                    } catch (Exception e) {
                        num_plano = "";
                    }
                    try {
                        tiempo = request.getParameter("t_estimado").toString();
                    } catch (Exception e) {
                        tiempo = "";
                    }
                    try {
                        num_tipo_herramental = request.getParameter("n_t_herramental").toString();
                    } catch (Exception e) {
                        num_tipo_herramental = "";
                    }
                    try {
                        t_herramental = request.getParameter("cbx_t_herramental").toString();
                    } catch (Exception e) {
                        t_herramental = "";
                    }
                    try {
                        observaciones = request.getParameter("observacion").toString();
                    } catch (Exception e) {
                        observaciones = "";
                    }
                    try {
                        version = request.getParameter("version").toString();
                    } catch (Exception e) {
                        version = "";
                    }

                    try {
                        producto = request.getParameter("producto").toString();
                    } catch (Exception e) {
                        producto = "";
                    }
                    try {
                        codigo = request.getParameter("codigo").toString();
                    } catch (Exception e) {
                        codigo = "";
                    }
                    try {
                        Tipo_prod = request.getParameter("tipo").toString();
                    } catch (Exception e) {
                        Tipo_prod = "";
                    }
                    try {
                        Tipo_mat_prod = request.getParameter("tipo_M").toString();
                    } catch (Exception e) {
                        Tipo_mat_prod = "";
                    }
                    try {
                        estructura_prod = request.getParameter("estructura").toString();
                    } catch (Exception e) {
                        estructura_prod = "";
                    }
                    try {
                        capa = request.getParameter("capa").toString();
                    } catch (Exception e) {
                        capa = "";
                    }
                    try {
                        cant_capa = Integer.parseInt(request.getParameter("c_capa").toString());
                    } catch (Exception e) {
                        cant_capa = 0;
                    }

                    try {
                        asunto_entrada = request.getParameter("asunto_ent").toString();
                    } catch (Exception e) {
                        asunto_entrada = "";
                    }

                    if (Tipo_Entrada == 1) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR ENTRADA PROYECTOS">
                        request.setAttribute("Id_proyecto", id_proyecto);
                        request.setAttribute("T_Entrada", Tipo_Entrada);
                        request.setAttribute("estadoM", estadoM);
                        request.setAttribute("fecha", fecha);
                        request.setAttribute("herramental", herramental);
                        request.setAttribute("n_herramental", num_herramental);
                        request.setAttribute("n_plano", num_plano);
                        request.setAttribute("t_estimado", tiempo);
                        request.setAttribute("n_t_herramental", num_tipo_herramental);
                        request.setAttribute("cbx_t_herramental", t_herramental);
                        request.setAttribute("observacion", observaciones);
                        request.setAttribute("version", version);
                        request.setAttribute("id_E", id_entrada);
                        versionS = version.toString();

                        proceso = jpa_herramental_C.modificar_herrmental_c(usu_registro, fecha, herramental, num_herramental, num_plano, tiempo, t_herramental, num_tipo_herramental, observaciones, versionS, id_entrada);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_entradas_proyectos");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_entradas_proyectos");
                        }
                        //</editor-fold>
                    } else if (Tipo_Entrada == 2) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR ENTRADA PRODUCCION">
                        request.setAttribute("Id_proyecto", id_proyecto);
                        request.setAttribute("T_Entrada", Tipo_Entrada);
                        request.setAttribute("estadoM", estadoM);
                        request.setAttribute("fecha", fecha);
                        request.setAttribute("producto", producto);
                        request.setAttribute("codigo", codigo);
                        request.setAttribute("tipo", Tipo_prod);
                        request.setAttribute("tipo_M", Tipo_mat_prod);
                        request.setAttribute("estructura", estructura_prod);
                        request.setAttribute("capa", capa);
                        request.setAttribute("c_capa", cant_capa);
                        request.setAttribute("observacion", observaciones);
                        request.setAttribute("id_E", id_entrada);

                        proceso = jpa_formula_C.Modificar_formula_c(id_entrada, usu_registro, producto, codigo, fecha, Tipo_prod, Tipo_mat_prod, estructura_prod, capa, cant_capa, id_proyecto, observaciones);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_entrada_produccion");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_entrada_produccion");
                        }
                        //</editor-fold>
                    } else if (Tipo_Entrada == 3) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR OTRAS ENTRADAS">
                        request.setAttribute("Id_proyecto", id_proyecto);
                        request.setAttribute("T_Entrada", Tipo_Entrada);
                        request.setAttribute("estadoM", estadoM);
                        request.setAttribute("fecha", fecha);
                        request.setAttribute("asunto_ent", asunto_entrada);
                        request.setAttribute("observacion", observaciones);
                        request.setAttribute("id_E", id_entrada);

                        proceso = jpa_entrada_otro.Modificar_otra_entrada(id_entrada, fecha, asunto_entrada, observaciones, usu_registro);

                        if (proceso) {
                            request.setAttribute("Alerta", "Modificar_otras_entradas");
                        } else {
                            request.setAttribute("Alerta", "Error_modificar_otras_entradas");
                        }
                        //</editor-fold>
                    }
                    request.getRequestDispatcher("Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=" + Tipo_Entrada + "&estadoM=" + estadoM + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO DE ENTRADAS DE PROYECTO">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                    id_entrada = Integer.parseInt(request.getParameter("id_E").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    estadoM = Integer.parseInt(request.getParameter("estadoM").toString());
                    Tipo_Entrada = Integer.parseInt(request.getParameter("T_Entrada").toString());
                    TempE = Integer.parseInt(request.getParameter("tempE").toString());

                    proceso = jpa_herramental_C.modificar_estado(id_entrada, estado);

                    if (proceso) {
                        if (estado == 0) {
                            request.setAttribute("Alerta", "Entrada_desactivada");
                        } else if (estado == 1) {
                            request.setAttribute("Alerta", "Entrada_activada");
                        }
                    } else {
                        if (estado == 0) {
                            request.setAttribute("Alerta", "Error_Entrada_desactivada");
                        } else if (estado == 1) {
                            request.setAttribute("Alerta", "Error_Entrada_activada");
                        }
                    }

                    request.getRequestDispatcher("Proyecto?opc=14&ipy=" + id_proyecto + "&T_Entrada=" + Tipo_Entrada + "&estadoM=" + estadoM + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="IR A LAS PRUEBAS">
                    request.setAttribute("Proyecto", "Pruebas_resultados");
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM"));
                    } catch (Exception ex) {
                        estadoM = 0;
                    }
                    try {
                        id_prueba = Integer.parseInt(request.getParameter("idPru").toString());
                    } catch (Exception e) {
                        id_prueba = 0;
                    }
                    try {
                        TempPRU = Integer.parseInt(request.getParameter("TempPRU").toString());
                    } catch (Exception e) {
                        TempPRU = 0;
                    }
                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM);
                    request.setAttribute("idPru", id_prueba);
                    request.setAttribute("TempPRU", TempPRU);
                    request.getRequestDispatcher("Pruebas.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR PRUEBA">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM").toString());
                    } catch (Exception e) {
                        estadoM = 0;
                    }
                    try {
                        prog = request.getParameter("programacion");
                    } catch (Exception e) {
                        prog = "";
                    }
                    try {
                        fecha = request.getParameter("fecha");
                    } catch (Exception e) {
                        fecha = "";
                    }
                    try {
                        numero = request.getParameter("consecutivo");
                    } catch (Exception e) {
                        numero = "";
                    }
                    try {
                        tipo_prueba = request.getParameter("tipo");
                    } catch (Exception e) {
                        tipo_prueba = "";
                    }
                    try {
                        observaciones = request.getParameter("observacion");
                    } catch (Exception e) {
                        observaciones = "";
                    }

                    request.setAttribute("programacion", prog);
                    request.setAttribute("fecha", fecha);
                    request.setAttribute("consecutivo", numero);
                    request.setAttribute("tipo", tipo_prueba);
                    request.setAttribute("observacion", observaciones);
                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM);

                    proceso = jpa_prueba_C.Registrar_prueba_c(usu_registro, prog, fecha, numero, observaciones, tipo_prueba, id_proyecto);

                    if (proceso) {
                        request.setAttribute("Alerta", "Registrar_programacion_prueba");
                    } else {
                        request.setAttribute("Alerta", "Error_registrar_programacion_prueba");
                    }

                    request.getRequestDispatcher("Proyecto?opc=18&ipy=" + id_proyecto + "&estadoM=" + estadoM + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 20:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR PRUEBAS">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM").toString());
                    } catch (Exception e) {
                        estadoM = 0;
                    }
                    try {
                        id_pru = Integer.parseInt(request.getParameter("id_prueba").toString());
                    } catch (Exception e) {
                        id_pru = 0;
                    }
                    try {
                        prog = request.getParameter("programacion");
                    } catch (Exception e) {
                        prog = "";
                    }
                    try {
                        fecha = request.getParameter("fecha");
                    } catch (Exception e) {
                        fecha = "";
                    }
                    try {
                        numero = request.getParameter("consecutivo");
                    } catch (Exception e) {
                        numero = "";
                    }
                    try {
                        tipo_prueba = request.getParameter("tipo");
                    } catch (Exception e) {
                        tipo_prueba = "";
                    }
                    try {
                        observaciones = request.getParameter("observacion");
                    } catch (Exception e) {
                        observaciones = "";
                    }

                    request.setAttribute("programacion", prog);
                    request.setAttribute("fecha", fecha);
                    request.setAttribute("consecutivo", numero);
                    request.setAttribute("tipo", tipo_prueba);
                    request.setAttribute("observacion", observaciones);
                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM);
                    request.setAttribute("id_prueba", id_pru);

                    proceso = jpa_prueba_C.Modificar_prueba_c(usu_registro, prog, fecha, numero, observaciones, tipo_prueba, id_pru);

                    if (proceso) {
                        request.setAttribute("Alerta", "Modificar_programacion_prueba");
                    } else {
                        request.setAttribute("Alerta", "Error_modificar_programacion_prueba");
                    }
                    request.getRequestDispatcher("Proyecto?opc=18&ipy=" + id_proyecto + "&estadoM=" + estadoM + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 21:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO PRUEBAS">
                    id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                    id_pru = Integer.parseInt(request.getParameter("idPru").toString());
                    estado = Integer.parseInt(request.getParameter("estado").toString());
                    estadoM = Integer.parseInt(request.getParameter("estadoM").toString());
                    proceso = jpa_prueba_C.Estado_prueba_c(id_pru, estado);

                    if (estado == 0) {
                        if (proceso) {
                            request.setAttribute("Alerta", "prueba_desactivada");
                        } else {
                            request.setAttribute("Alerta", "Error_prueba_desactivada");
                        }
                    } else if (estado == 1) {
                        if (proceso) {
                            request.setAttribute("Alerta", "Prueba_activada");
                        } else {
                            request.setAttribute("Alerta", "Error_Prueba_activada");
                        }
                    }

                    request.getRequestDispatcher("Proyecto?opc=18&ipy=" + id_proyecto + "&estadoM=" + estadoM + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 22:
                    //<editor-fold defaultstate="collapsed" desc="ENVIAR CORREO A LA LISTA DE DISTRIBUCION SOBRE LA ACTIVIDAD Y RESPUESTAS">
                    try {
                        id_proyecto = Integer.parseInt(request.getParameter("ipy").toString());
                        request.setAttribute("Id_proyecto", id_proyecto);
                    } catch (Exception e) {
                        id_proyecto = 0;
                    }
                    try {
                        estadoM = Integer.parseInt(request.getParameter("estadoM").toString());
                    } catch (Exception e) {
                        estadoM = 0;
                    }
                    try {
                        id_memoria = Integer.parseInt(request.getParameter("cba_num").toString());
                    } catch (Exception e) {
                        id_memoria = 0;
                    }
                    try {
                        tipo_envio = request.getParameter("envio");
                    } catch (Exception e) {
                        tipo_envio = "";
                    }

                    request.setAttribute("Id_proyecto", id_proyecto);
                    request.setAttribute("estadoM", estadoM);
                    request.setAttribute("cba_num", id_memoria);
                    request.setAttribute("envio", tipo_envio);

                    if (tipo_envio.equals("A")) {
                        mtdmail.mail_notificacion_proyecto(id_proyecto, id_memoria, tipo_envio, mail, pass_mail);
                        proceso = jpa_memoriad.Enviar_correo_actividad(id_memoria);

                        if (proceso) {
                            request.setAttribute("Alerta", "Correo_lista_distribucion");
                        } else {
                            request.setAttribute("Alerta", "Error_Correo_lista_distribucion");
                        }

                    } else {
                        mtdmail.mail_notificacion_proyecto(id_proyecto, id_memoria, t_entrada, mail, pass_mail);
                        proceso = jpa_memoriad.Enviar_correo_respuesta(id_memoria);

                        if (proceso) {
                            request.setAttribute("Alerta", "Correo_lista_distribucion");
                        } else {
                            request.setAttribute("Alerta", "Error_Correo_lista_distribucion");
                        }

                    }

                    if (proceso) {
                        response.sendRedirect("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=11");
                    } else {
                        response.sendRedirect("Proyecto?opc=7&ipy=" + id_proyecto + "&estadoM=" + estadoM + "&TempM=12");
                    }
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Proyecto?opc=1&ipy=0").forward(request, response);
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
