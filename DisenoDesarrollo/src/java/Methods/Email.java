package Methods;

import Controladores.MemoriaDJpaController;
import Controladores.UsuarioJpaController;
import Controladores.ProyectoJpaController;
import Controladores.ParametrosJpaController;
import java.util.List;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;

public class Email extends HttpServlet {

    MemoriaDJpaController jpacmmd = new MemoriaDJpaController();
    ParametrosJpaController jpa_parametros = new ParametrosJpaController();
    ProyectoJpaController jpacpyt = new ProyectoJpaController();
    UsuarioJpaController jpacusa = new UsuarioJpaController();
    List lst_ult_memoria = null;
    List lst_usuario_e = null;
    List lst_usuario_r = null;
    List lst_proyecto = null;
    List lst_parametros = null;
    List lst_parametros_mensaje = null;
    List lst_usuario = null;

    //<editor-fold defaultstate="collapsed" desc="REGISTRO PROYECTO">
    public void mail_registro_proyecto(String usu_registro, String fecha, String numero, String proyecto, String uso_previsto, String user, String pass) throws javax.mail.MessagingException {
        UsuarioJpaController obj_usuario = new UsuarioJpaController();
        lst_parametros = jpa_parametros.Info_correo();
        Object[] obj_lst_correo_P = (Object[]) lst_parametros.get(0);
        String ant_Correo = obj_lst_correo_P[2].toString().replace("][", "///").replace("[", "").replace("]", "");
        String[] Correo = ant_Correo.split("///");
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", Correo[2]);
        propiedades.setProperty("mail.smtp.starttls.enable", Correo[4]);
        propiedades.setProperty("mail.smtp.port", Correo[3]);
        propiedades.setProperty("mail.smtp.auth", Correo[5]);
        propiedades.setProperty("mail.smtp.user", Correo[0]);
        Session session = Session.getDefaultInstance(propiedades);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Correo[0]));

            lst_usuario = obj_usuario.Consultar_usuario_linea();
            lst_parametros_mensaje = jpa_parametros.Mensaje_correo();
            Object[] obj_mensaje = (Object[]) lst_parametros_mensaje.get(0);

            int num = 0;

            for (int i = 0; i < lst_usuario.size(); i++) {
                Object[] obj_lst_usuario = (Object[]) lst_usuario.get(i);

                if (obj_lst_usuario[8] == null) {
                    num = i + 1;
                } else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_lst_usuario[8].toString()));
                }
            }

            message.setSubject("Registro Proyecto NO:" + numero + "");
            message.setText("\n"
                    + "<div style='background-color: whitesmoke;padding: 1%;'>"
                    + "  <div style=\"font-family: Helvetica, sans-serif;\">"
                    + "    <div style='margin-left: 18%;'>"
                    + "      <h4 style=\"width: 10%;padding: 10px;background-color: #79c0ff;text-align: center;\">Asunto</h4>"
                    + "      <p>Usted es partícipe del nuevo proyecto.</p>"
                    + "    </div>"
                    + "    <center>"
                    + "      <div"
                    + "        style=\"width: 50%; height: 50%; background-color: #e9e9e9; display: flex; justify-content: center; align-items: center; padding: 20px; box-sizing: border-box;margin-top: 62px;\">"
                    + "        <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "          <thead style=\"border: 1px solid black;\">"
                    + "            <tr>"
                    + "              <th style=\"background-color: #4eb8ff; color: white; border: 1px solid black; padding: 8px;\">DE:<br>AUTOR</th>"
                    + "              <th style=\"border: 1px solid black; padding: 8px;\">" + usu_registro + "<br><a href=\"mailto:" + user + "\" style=\"text-decoration: none;font-weight: normal;color: black;\">" + user + "</a></th>"
                    + "              <th style=\"background-color: #4eb8ff; color: white; border: 1px solid black; padding: 8px;\">PARA:</th>"
                    + "              <th style=\"border: 1px solid black; padding: 8px;\">Participes</th>"
                    + "            </tr>"
                    + "          </thead>"
                    + "          <tbody style=\"border: 1px solid black;\">"
                    + "            <tr style=\"border: 1px solid black;\">"
                    + "              <td style=\"text-align: center; background-color: #4eb8ff61;border: 1px solid black;\"><b>FECHA</b></td>"
                    + "              <td colspan=\"2\" style=\"text-align: center; background-color: #4eb8ff61;border: 1px solid black;\"><b>NOMBRE DEL PROYECTO</b></td>"
                    + "              <td style=\"text-align: center; background-color: #4eb8ff61;border: 1px solid black;\"><b>PROYECTO N°</b></td>"
                    + "            </tr>"
                    + "            <tr style=\"border: 1px solid black;\">"
                    + "              <td style=\"text-align:center;border: 1px solid black;\">" + fecha + "</td>"
                    + "              <td colspan=\"2\" style=\"text-align:center;border: 1px solid black;\">" + proyecto + "</td>"
                    + "              <td style=\"text-align:center;border: 1px solid black;\">" + numero + "</td>"
                    + "            </tr>"
                    + "            <tr style=\"border: 1px solid black;\">"
                    + "              <td colspan=\"4\" style=\"text-align: center; background-color: #4eb8ff61;border: 1px solid black;\"><b>USO PREVISTO</b></td>"
                    + "            </tr>"
                    + "            <tr style=\"border: 1px solid black;\">"
                    + "              <td colspan=\"4\" style=\"text-align: center;border: 1px solid black;\">" + uso_previsto.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/").replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/") + "</td>"
                    + "            </tr>"
                    + "          </tbody>"
                    + "        </table>"
                    + "      </div>"
                    + "      <br>"
                    + "      <p style='width: 62%;'>" + obj_mensaje[2] + "</p>"
                    + "    </center>"
                    + "  </div>"
                    + "</div>"
                    + "\n", "UTF-8", "HTML");

            Transport transport = session.getTransport("smtp");
            transport.connect(Correo[0], Correo[1]);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ASIGNADO A UNA ACTIVIDAD">
    public void mail_responsabilidad_autoridad(int id_proyecto, int id_memoria_c, String user, String pass) throws javax.mail.MessagingException {
        lst_parametros = jpa_parametros.Info_correo();
        Object[] obj_lst_correo_P = (Object[]) lst_parametros.get(0);
        String ant_Correo = obj_lst_correo_P[2].toString().replace("][", "///").replace("[", "").replace("]", "");
        String[] Correo = ant_Correo.split("///");
        lst_ult_memoria = jpacmmd.Traer_ultima_memoria(id_memoria_c);
        Object[] obj_lst_ult_memoria = (Object[]) lst_ult_memoria.get(0);
        lst_proyecto = jpacpyt.Traer_proyecto(id_proyecto);
        Object[] obj_lst_proyecto = (Object[]) lst_proyecto.get(0);
        UsuarioJpaController Jpa_user = new UsuarioJpaController();
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", Correo[2]);
        propiedades.setProperty("mail.smtp.starttls.enable", Correo[4]);
        propiedades.setProperty("mail.smtp.port", Correo[3]);
        propiedades.setProperty("mail.smtp.auth", Correo[5]);
        propiedades.setProperty("mail.smtp.user", Correo[0]);

        Session session = Session.getDefaultInstance(propiedades);

        String arr_correo[] = obj_lst_ult_memoria[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
        lst_usuario_e = jpacusa.Traer_usuario(Integer.parseInt(obj_lst_ult_memoria[2].toString()));
        Object[] obj_usuario_e = (Object[]) lst_usuario_e.get(0);

        lst_parametros_mensaje = jpa_parametros.Mensaje_correo();
        Object[] obj_mensaje = (Object[]) lst_parametros_mensaje.get(0);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Correo[0]));

            for (int q = 0; q < arr_correo.length; q++) {
                Object[] obj_lst_usuario = (Object[]) jpacusa.Traer_usuario(Integer.parseInt(arr_correo[q].toString())).get(0);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_lst_usuario[8].toString()));
            }
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.toLowerCase()));
            message.setSubject("Responsabilidad Asignada Proyecto " + obj_lst_proyecto[5] + "");
            String actividad = obj_lst_ult_memoria[4].toString().replace("[////]", "<br />");
            actividad = actividad.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/");
            actividad = actividad.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/");
            String mail_text = "\n"
                    + "  <div style=\"width: 100%;margin: 0 auto;\">"
                    + "    <div style=\"font-family: Helvetica, sans-serif; margin: 2% 5% 0% 5%;\">"
                    + "      <div style=\"background-color: whitesmoke; padding: 1%;\">"
                    + "        <div>"
                    + "          <h4 style=\"color: #328f94;\">Buen día</h4>"
                    + "          <p>El usuario " + obj_usuario_e[12] + " / " + obj_usuario_e[3] + " " + obj_usuario_e[4] + " le ha asignado en el proyecto " + obj_lst_proyecto[5] + " una actividad.</p>"
                    + "        </div>\n"
                    + "        <hr>\n"
                    + "        <div style=\"display: flex; justify-content: space-between;\">"
                    + ""
                    + "          <div style=\"flex: 1; margin-right: 1%;\">"
                    + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "              <thead>\n"
                    + "                <tr style=\"border: 1px solid black;\">"
                    + "                  <th colspan=\"3\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DEL PROYECTO</th>"
                    + "                </tr>"
                    + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                  <th style=\"border: 1px solid black;\">CONSECUTIVO</th>"
                    + "                  <th style=\"border: 1px solid black;\">PROYECTO</th>"
                    + "                  <th style=\"border: 1px solid black;\">ESTADO</th>"
                    + "                </tr>"
                    + "              </thead>"
                    + "              <tbody>"
                    + "                <tr style=\"border: 1px solid black;text-align:center;\">"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_lst_proyecto[5] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_lst_proyecto[6] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_lst_proyecto[4] + "</td>"
                    + "                </tr>"
                    + "              </tbody>"
                    + "            </table>"
                    + "          </div>"
                    + ""
                    + ""
                    + "          <div style=\"flex: 1; margin-left: 1%;\">"
                    + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "              <thead>\n"
                    + "                <tr style=\"border: 1px solid black;\">"
                    + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA ACTIVIDAD</th>"
                    + "                </tr>"
                    + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                  <th style=\"border: 1px solid black;\">ETAPA</th>"
                    + "                  <th style=\"border: 1px solid black;\">FASE</th>"
                    + "                  <th style=\"border: 1px solid black;\">FECHA</th>"
                    + "                  <th style=\"border: 1px solid black;\">ACTIVIDAD</th>"
                    + "                </tr>"
                    + "              </thead>"
                    + "              <tbody>"
                    + "                <tr style=\"border: 1px solid black;text-align: center;\"\">"
                    + "                  <td style=\"border: 1px solid black;\">" + ((obj_lst_ult_memoria[11].toString().equals("7.3.1.1")) ? obj_lst_ult_memoria[12] : obj_lst_ult_memoria[11] + " " + obj_lst_ult_memoria[12]) + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_lst_ult_memoria[13] + " " + obj_lst_ult_memoria[14] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_lst_ult_memoria[1] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + actividad + "</td>"
                    + "                </tr>"
                    + "              </tbody>"
                    + "            </table>"
                    + "          </div>"
                    + "        </div>"
                    + "        <h4>Cordialmente</h4>"
                    + "        <p>Programa de Diseño y Desarrollo PLASTITEC</p>"
                    + "        <br>"
                    + "        <p>" + obj_mensaje[2] + "</p>"
                    + "      </div>"
                    + "    </div>"
                    + "  </div>"
                    + "\n";
            message.setText(mail_text, "UTF-8", "HTML");
            Transport transport = session.getTransport("smtp");
            transport.connect(Correo[0], Correo[1]);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ENVIAR ACTIVIDAD Y RESPUESTAS">
    public void mail_notificacion_proyecto(int id_proyecto, int id_memoria_d, String t_entrada, String user, String pass) throws javax.mail.MessagingException {
        lst_parametros = jpa_parametros.Info_correo();
        Object[] obj_lst_correo_P = (Object[]) lst_parametros.get(0);
        String ant_Correo = obj_lst_correo_P[2].toString().replace("][", "///").replace("[", "").replace("]", "");
        String[] Correo = ant_Correo.split("///");
        lst_ult_memoria = jpacmmd.Traer_memoria_a(id_memoria_d);
        Object[] obj_ult_memoria = (Object[]) lst_ult_memoria.get(0);
        lst_proyecto = jpacpyt.Traer_proyecto(id_proyecto);
        Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
        lst_parametros_mensaje = jpa_parametros.Mensaje_correo();
        Object[] obj_mensaje = (Object[]) lst_parametros_mensaje.get(0);
        UsuarioJpaController jpacusa = new UsuarioJpaController();
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", Correo[2]);
        propiedades.setProperty("mail.smtp.starttls.enable", Correo[4]);
        propiedades.setProperty("mail.smtp.port", Correo[3]);
        propiedades.setProperty("mail.smtp.auth", Correo[5]);
        propiedades.setProperty("mail.smtp.user", Correo[0]);
        Session session = Session.getDefaultInstance(propiedades);
        String arr_correo[] = obj_proyecto[8].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
        lst_usuario_e = jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[2].toString()));
        Object[] obj_usuario_e = (Object[]) lst_usuario_e.get(0);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Correo[0]));

            for (int q = 0; q < arr_correo.length; q++) {
                Object[] obj_lst_usuario = (Object[]) jpacusa.Traer_usuario(Integer.parseInt(arr_correo[q].toString())).get(0);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_lst_usuario[8].toString()));

            }

            String actividad = "";
            String respuesta = "";

            if (obj_ult_memoria[4] != null) {
                actividad = obj_ult_memoria[4].toString().replace("[////]", "<br />");
                actividad = actividad.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/");
                actividad = actividad.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/");
            }
            if (t_entrada == null ? "A" != null : !t_entrada.equals("A")) {
                respuesta = obj_ult_memoria[6].toString().replace("[////]", "<br />");
                respuesta = respuesta.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/");
                respuesta = respuesta.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/");
            }

            String mail_text = "";
            if (t_entrada == null ? "A" != null : !t_entrada.equals("A")) {
                lst_usuario_r = jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[7].toString()));
                Object[] obj_usuario_r = (Object[]) lst_usuario_r.get(0);
                message.setSubject("Notificación Respuesta Proyecto " + obj_proyecto[5] + "");
                mail_text = "<h3 style='color: #328f94;'>Buen día a todos</h3>";
                mail_text = mail_text + "<p>El participe del proyecto " + obj_usuario_r[12] + " / " + obj_usuario_r[3] + " " + obj_usuario_r[4] + " le ha notificado la respuesta de actividad para el avance del proyecto.</p>";
            } else {
                message.setSubject("Notificación Proyecto " + obj_proyecto[5] + "");
                mail_text = "<h3 style='color: #328f94;'>Buen día a todos</h3>";
                mail_text = mail_text + "<p>El participe del proyecto " + obj_usuario_e[12] + " / " + obj_usuario_e[3] + " " + obj_usuario_e[4] + " le ha notificado nueva actividad en el avance del proyecto.</p>";
            }

            if (t_entrada == null ? "A" != null : !t_entrada.equals("A")) {
                lst_usuario_r = jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[7].toString()));
                Object[] obj_usuario_r = (Object[]) lst_usuario_r.get(0);
                mail_text = mail_text + "\n"
                        + "  <hr>\n"
                        + "  <div style=\"width: 100%;margin: 0 auto;\">\n"
                        + "    <div style=\"font-family: Helvetica, sans-serif; margin: 2% 5% 0% 5%;\">\n"
                        + "      <div style=\"background-color: whitesmoke; padding: 1%;\">\n"
                        + "        <div style=\" margin-right: 0%;\">\n"
                        + "          <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                        + "            <thead>\n"
                        + "              <tr style=\"border: 1px solid black;\">\n"
                        + "                <th colspan=\"3\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DEL PROYECTO</th>\n"
                        + "              </tr>\n"
                        + "              <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">\n"
                        + "                <th style=\"border: 1px solid black;\">CONSECUTIVO</th>\n"
                        + "                <th style=\"border: 1px solid black;\">PROYECTO</th>\n"
                        + "                <th style=\"border: 1px solid black;\">ESTADO</th>\n"
                        + "              </tr>\n"
                        + "            </thead>\n"
                        + "            <tbody>\n"
                        + "              <tr style=\"border: 1px solid black;text-align:center;\">\n"
                        + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[5] + "</td>\n"
                        + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[6] + "</td>\n"
                        + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[4] + "</td>\n"
                        + "              </tr>\n"
                        + "            </tbody>\n"
                        + "          </table>\n"
                        + "        </div>\n"
                        + "        <br>\n"
                        + "        <div style=\"display: flex; justify-content: space-between;\">\n"
                        + "          <div style=\"flex: 1; margin-left: 0%;\">\n"
                        + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                        + "              <thead>\n"
                        + "                <tr style=\"border: 1px solid black;\">\n"
                        + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA ACTIVIDAD</th>\n"
                        + "                </tr>\n"
                        + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">\n"
                        + "                  <th style=\"border: 1px solid black;\">ETAPA</th>\n"
                        + "                  <th style=\"border: 1px solid black;\">FASE</th>\n"
                        + "                  <th style=\"border: 1px solid black;\">FECHA</th>\n"
                        + "                  <th style=\"border: 1px solid black;\">ACTIVIDAD</th>\n"
                        + "                </tr>\n"
                        + "              </thead>\n"
                        + "              <tbody>\n"
                        + "                <tr style=\"border: 1px solid black;text-align: center;\"\">\n"
                        + "                  <td style=\" border: 1px solid black;\">" + ((obj_ult_memoria[11].toString().equals("7.3.1.1")) ? obj_ult_memoria[12] : obj_ult_memoria[11] + " " + obj_ult_memoria[12]) + "</td>\n"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[13] + "</td>\n"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[1] + "</td>\n"
                        + "                  <td style=\"border: 1px solid black;\">" + actividad + "</td>\n"
                        + "                </tr>\n"
                        + "              </tbody>\n"
                        + "            </table>\n"
                        + "          </div>\n"
                        + "\n"
                        + "          <div style=\"flex: 1; margin-left: 1%;\">\n"
                        + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                        + "              <thead>\n"
                        + "                <tr style=\"border: 1px solid black;\">\n"
                        + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA RESPUESTA</th>\n"
                        + "                </tr>\n"
                        + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">\n"
                        + "                  <th style=\"border: 1px solid black;\">RESPONSABLE</th>\n"
                        + "                  <th style=\"border: 1px solid black;\">FECHA</th>\n"
                        + "                  <th style=\"border: 1px solid black;\">RESPUESTA</th>\n"
                        + "                </tr>\n"
                        + "              </thead>\n"
                        + "              <tbody>\n"
                        + "                <tr style=\"border: 1px solid black;text-align: center;\"\">\n"
                        + "                  <td style=\" border: 1px solid black;\">" + obj_usuario_r[12] + " / " + obj_usuario_r[3] + " " + obj_usuario_r[4] + "</td>\n"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[8] + "</td>\n"
                        + "                  <td style=\"border: 1px solid black;\">" + respuesta + "</td>\n"
                        + "                </tr>\n"
                        + "              </tbody>\n"
                        + "            </table>\n"
                        + "          </div>\n"
                        + "        </div>\n"
                        + "        <h4>Cordialmente</h4>\n"
                        + "        <p>Programa de Diseño y Desarrollo PLASTITEC</p>\n"
                        + "        <br>\n"
                        + "        <p>" + obj_mensaje[2] + "</p>"
                        + "      </div>"
                        + "    </div>"
                        + "  </div>"
                        + "\n";
            } else {
                mail_text = mail_text + "\n"
                        + "<hr>"
                        + "  <div style=\"width: 100%;margin: 0 auto;\">"
                        + "    <div style=\"font-family: Helvetica, sans-serif; margin: 2% 5% 0% 5%;\">"
                        + "      <div style=\"background-color: whitesmoke; padding: 1%;\">"
                        + "        <div style=\"display: flex; justify-content: space-between;\">"
                        + "\n"
                        + "          <div style=\"flex: 1; margin-right: 1%;\">"
                        + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                        + "              <thead>\n"
                        + "                <tr style=\"border: 1px solid black;\">"
                        + "                  <th colspan=\"3\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DEL PROYECTO</th>"
                        + "                </tr>\n"
                        + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                        + "                  <th style=\"border: 1px solid black;\">CONSECUTIVO</th>"
                        + "                  <th style=\"border: 1px solid black;\">PROYECTO</th>"
                        + "                  <th style=\"border: 1px solid black;\">ESTADO</th>"
                        + "                </tr>\n"
                        + "              </thead>\n"
                        + "              <tbody>\n"
                        + "                <tr style=\"border: 1px solid black;text-align:center;\">"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_proyecto[5] + "</td>"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_proyecto[6] + "</td>"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_proyecto[4] + "</td>"
                        + "                </tr>"
                        + "              </tbody>"
                        + "            </table>"
                        + "          </div>"
                        + ""
                        + ""
                        + "          <div style=\"flex: 1; margin-left: 1%;\">"
                        + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                        + "              <thead>\n"
                        + "                <tr style=\"border: 1px solid black;\">"
                        + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA ACTIVIDAD</th>"
                        + "                </tr>\n"
                        + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                        + "                  <th style=\"border: 1px solid black;\">ETAPA</th>"
                        + "                  <th style=\"border: 1px solid black;\">FASE</th>"
                        + "                  <th style=\"border: 1px solid black;\">FECHA</th>"
                        + "                  <th style=\"border: 1px solid black;\">ACTIVIDAD</th>"
                        + "                </tr>\n"
                        + "              </thead>\n"
                        + "              <tbody>\n"
                        + "                <tr style=\"border: 1px solid black;text-align: center;\">"
                        + "                  <td style=\"border: 1px solid black;\">" + ((obj_ult_memoria[11].toString().equals("7.3.1.1")) ? obj_ult_memoria[12] : obj_ult_memoria[11] + " " + obj_ult_memoria[12]) + "</td>"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[13] + " " + obj_ult_memoria[14] + "</td>"
                        + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[1] + "</td>"
                        + "                  <td style=\"border: 1px solid black;\">" + actividad + "</td>"
                        + "                </tr>"
                        + "              </tbody>"
                        + "            </table>"
                        + "          </div>"
                        + "        </div>"
                        + "        <h4>Cordialmente</h4>"
                        + "        <p>Programa de Diseño y Desarrollo PLASTITEC</p>"
                        + "        <br>"
                        + "<p>" + obj_mensaje[2] + "<p>"
                        + "         </div>"
                        + "     </div>"
                        + " </div>"
                        + "\n";
            }
            message.setText(mail_text, "UTF-8", "HTML");
            Transport transport = session.getTransport("smtp");
            transport.connect(Correo[0], Correo[1]);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e) {
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RESPUESTA ESTADO">
    public void mail_solucion_responsabilidad_autoridad(int id_proyecto, int id_memoria_d, String user, String pass, String Usuario) throws javax.mail.MessagingException {
        lst_parametros = jpa_parametros.Info_correo();
        Object[] obj_lst_correo_P = (Object[]) lst_parametros.get(0);
        String ant_Correo = obj_lst_correo_P[2].toString().replace("][", "///").replace("[", "").replace("]", "");
        String[] Correo = ant_Correo.split("///");
        lst_parametros_mensaje = jpa_parametros.Mensaje_correo();
        Object[] obj_mensaje = (Object[]) lst_parametros_mensaje.get(0);
        lst_ult_memoria = jpacmmd.Traer_memoria_a(id_memoria_d);
        Object[] obj_ult_memoria = (Object[]) lst_ult_memoria.get(0);
        lst_proyecto = jpacpyt.Traer_proyecto(id_proyecto);
        Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
        UsuarioJpaController jpacusa = new UsuarioJpaController();
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", Correo[2]);
        propiedades.setProperty("mail.smtp.starttls.enable", Correo[4]);
        propiedades.setProperty("mail.smtp.port", Correo[3]);
        propiedades.setProperty("mail.smtp.auth", Correo[5]);
        propiedades.setProperty("mail.smtp.user", Correo[0]);
        Session session = Session.getDefaultInstance(propiedades);
        lst_usuario_e = jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[2].toString()));
        Object[] obj_usuario_e = (Object[]) lst_usuario_e.get(0);
        Object[] obj_lst_usuario = (Object[]) jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[2].toString())).get(0);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Correo[0]));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_usuario_e[8].toString()));
            String mail_text = "";
            lst_usuario_r = jpacusa.Traer_usuario(Integer.parseInt(obj_usuario_e[0].toString()));
            message.setSubject("Avance final de Actividad, proyecto " + obj_proyecto[5] + "");
            String actividad = obj_ult_memoria[4].toString().replace("[////]", "<br />");
            actividad = actividad.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/");
            actividad = actividad.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/");
            String respuesta = obj_ult_memoria[6].toString().replace("[////]", "<br />");
            respuesta = respuesta.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/");
            respuesta = respuesta.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/");
            mail_text = "\n"
                    + "  <div style=\"width: 100%;margin: 0 auto;\">"
                    + "    <div style=\"font-family: Helvetica, sans-serif; margin: 2% 5% 0% 5%;\">"
                    + "      <div style=\"background-color: whitesmoke; padding: 1%;\">"
                    + "        <div>"
                    + "          <h4 style=\"color: #328f94;\">Buen día " + obj_lst_usuario[3] + " " + obj_lst_usuario[4] + "</h4>"
                    + "          <p>El participe del proyecto " + Usuario + " ha solucionado la actividad asignada para el avance del proyecto.</p>"
                    + "        </div>"
                    + "        <hr>"
                    + "        <div style=\" margin-right: 0%;\">"
                    + "          <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "            <thead>"
                    + "              <tr style=\"border: 1px solid black;\">"
                    + "                <th colspan=\"3\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DEL PROYECTO</th>"
                    + "              </tr>"
                    + "              <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                <th style=\"border: 1px solid black;\">CONSECUTIVO</th>"
                    + "                <th style=\"border: 1px solid black;\">PROYECTO</th>"
                    + "                <th style=\"border: 1px solid black;\">ESTADO</th>"
                    + "              </tr>"
                    + "            </thead>"
                    + "            <tbody>"
                    + "              <tr style=\"border: 1px solid black;text-align:center;\">"
                    + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[5] + "</td>"
                    + "                <td style=\"border: 1px solid black;text-transform: uppercase;\">" + obj_proyecto[6] + "</td>"
                    + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[4] + "</td>"
                    + "              </tr>"
                    + "            </tbody>"
                    + "          </table>"
                    + "        </div>"
                    + "        <br>"
                    + "        <div style=\"display: flex; justify-content: space-between;\">"
                    + "          <div style=\"flex: 1; margin-left: 0%;\">"
                    + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "              <thead>"
                    + "                <tr style=\"border: 1px solid black;\">"
                    + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA ACTIVIDAD</th>"
                    + "                </tr>"
                    + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                  <th style=\"border: 1px solid black;\">ETAPA</th>"
                    + "                  <th style=\"border: 1px solid black;\">FASE</th>"
                    + "                  <th style=\"border: 1px solid black;\">FECHA</th>"
                    + "                  <th style=\"border: 1px solid black;\">ACTIVIDAD</th>"
                    + "                </tr>\n"
                    + "              </thead>\n"
                    + "              <tbody>\n"
                    + "                <tr style=\"border: 1px solid black;text-align: center;\">"
                    + "                  <td style=\" border: 1px solid black;\">" + ((obj_ult_memoria[11].toString().equals("7.3.1.1")) ? obj_ult_memoria[12] : obj_ult_memoria[11] + " " + obj_ult_memoria[12]) + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[13] + " " + obj_ult_memoria[14] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[1] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + actividad + "</td>"
                    + "                </tr>"
                    + "              </tbody>"
                    + "            </table>"
                    + "          </div>";
            lst_usuario_r = jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[7].toString()));
            Object[] obj_usuario_s = (Object[]) lst_usuario_r.get(0);
            mail_text = mail_text + ""
                    + "          <div style=\"flex: 1; margin-left: 1%;\">\n"
                    + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "              <thead>"
                    + "                <tr style=\"border: 1px solid black;\">"
                    + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA RESPUESTA</th>"
                    + "                </tr>\n"
                    + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                  <th style=\"border: 1px solid black;\">RESPONSABLE</th>"
                    + "                  <th style=\"border: 1px solid black;\">FECHA</th>"
                    + "                  <th style=\"border: 1px solid black;\">RESPUESTA</th>"
                    + "                </tr>"
                    + "              </thead>"
                    + "              <tbody>"
                    + "                <tr style=\"border: 1px solid black;text-align: center;\">"
                    + "                  <td style=\" border: 1px solid black;\">" + obj_usuario_s[12] + " / " + obj_usuario_s[3] + " " + obj_usuario_s[4] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[8] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + respuesta + "</td>"
                    + "                </tr>"
                    + "              </tbody>"
                    + "            </table>"
                    + "          </div>"
                    + "        </div>"
                    + "        <h4>Cordialmente</h4>"
                    + "        <p>Programa de Diseño y Desarrollo PLASTITEC</p>"
                    + "        <br>"
                    + "        <p>" + obj_mensaje[2] + "</p>"
                    + "      </div>"
                    + "    </div>"
                    + "  </div>"
                    + "\n";
            message.setText(mail_text, "UTF-8", "HTML");
            Transport transport = session.getTransport("smtp");
            transport.connect(Correo[0], Correo[1]);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
        }
    }
    //</editor-fold>

    public void mail_solucion_responsabilidad_autoridad_parcial(int id_proyecto, int id_memoria_d, String user, String pass, String resp, String Usuario) throws javax.mail.MessagingException {
        lst_parametros = jpa_parametros.Info_correo();
        Object[] obj_lst_correo_P = (Object[]) lst_parametros.get(0);
        String ant_Correo = obj_lst_correo_P[2].toString().replace("][", "///").replace("[", "").replace("]", "");
        String[] Correo = ant_Correo.split("///");
        lst_parametros_mensaje = jpa_parametros.Mensaje_correo();
        Object[] obj_mensaje = (Object[]) lst_parametros_mensaje.get(0);
        lst_ult_memoria = jpacmmd.Traer_memoria_a(id_memoria_d);
        Object[] obj_ult_memoria = (Object[]) lst_ult_memoria.get(0);
        lst_proyecto = jpacpyt.Traer_proyecto(id_proyecto);
        Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
        UsuarioJpaController jpacusa = new UsuarioJpaController();
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", Correo[2]);
        propiedades.setProperty("mail.smtp.starttls.enable", Correo[4]);
        propiedades.setProperty("mail.smtp.port", Correo[3]);
        propiedades.setProperty("mail.smtp.auth", Correo[5]);
        propiedades.setProperty("mail.smtp.user", Correo[0]);
        Session session = Session.getDefaultInstance(propiedades);
        lst_usuario_e = jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[2].toString()));
        Object[] obj_lst_usuario = (Object[]) jpacusa.Traer_usuario(Integer.parseInt(obj_ult_memoria[2].toString())).get(0);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Correo[0]));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(obj_lst_usuario[8].toString()));
            String mail_text = "";
            message.setSubject("Avance de Actividad, Proyecto " + obj_proyecto[5] + "");
            String actividad = obj_ult_memoria[4].toString().replace("[////]", "<br />");
            actividad = actividad.replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/");
            actividad = actividad.replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/");

            mail_text = "\n"
                    + "  <div style=\"width: 100%;margin: 0 auto;\">"
                    + "    <div style=\"font-family: Helvetica, sans-serif; margin: 2% 5% 0% 5%;\">"
                    + "      <div style=\"background-color: whitesmoke; padding: 1%;\">"
                    + "        <div>"
                    + "          <h4 style=\"color: #328f94;\">Buen día  " + obj_lst_usuario[3] + " " + obj_lst_usuario[4] + "</h4>"
                    + "          <p>El participe del proyecto " + Usuario + " le ha notificado nueva actividad en el avance del proyecto.</p>"
                    + "        </div>"
                    + "        <hr>"
                    + "        <div style=\" margin-right: 0%;\">"
                    + "          <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "            <thead>"
                    + "              <tr style=\"border: 1px solid black;\">"
                    + "                <th colspan=\"3\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DEL PROYECTO</th>"
                    + "              </tr>"
                    + "              <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                <th style=\"border: 1px solid black;\">CONSECUTIVO</th>"
                    + "                <th style=\"border: 1px solid black;\">PROYECTO</th>"
                    + "                <th style=\"border: 1px solid black;\">ESTADO</th>"
                    + "              </tr>"
                    + "            </thead>"
                    + "            <tbody>"
                    + "              <tr style=\"border: 1px solid black;text-align:center;\">"
                    + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[5] + "</td>"
                    + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[6] + "</td>"
                    + "                <td style=\"border: 1px solid black;\">" + obj_proyecto[4] + "</td>"
                    + "              </tr>"
                    + "            </tbody>"
                    + "          </table>"
                    + "        </div>"
                    + "        <br>"
                    + "        <div style=\"display: flex; justify-content: space-between;\">"
                    + "          <div style=\"flex: 1; margin-left: 0%;\">\n"
                    + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "              <thead>"
                    + "                <tr style=\"border: 1px solid black;\">"
                    + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA ACTIVIDAD</th>"
                    + "                </tr>\n"
                    + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                  <th style=\"border: 1px solid black;\">ETAPA</th>"
                    + "                  <th style=\"border: 1px solid black;\">FASE</th>"
                    + "                  <th style=\"border: 1px solid black;\">FECHA</th>"
                    + "                  <th style=\"border: 1px solid black;\">ACTIVIDAD</th>"
                    + "                </tr>"
                    + "              </thead>"
                    + "              <tbody>"
                    + "                <tr style=\"border: 1px solid black;text-align: center;\">"
                    + "                  <td style=\" border: 1px solid black;\">" + ((obj_ult_memoria[11].toString().equals("7.3.1.1")) ? obj_ult_memoria[12] : obj_ult_memoria[11] + " " + obj_ult_memoria[12]) + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[13] + " " + obj_ult_memoria[14] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + obj_ult_memoria[1] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + actividad + "</td>"
                    + "                </tr>"
                    + "              </tbody>"
                    + "            </table>"
                    + "          </div>";
            String[] respuesta = resp.toString().split("---");
            mail_text = mail_text + ""
                    + "          <div style=\"flex: 1; margin-left: 1%;\">"
                    + "            <table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">"
                    + "              <thead>"
                    + "                <tr style=\"border: 1px solid black;\">"
                    + "                  <th colspan=\"4\" style=\"border: 1px solid black; background-color: #a3d0f8;\">DATOS DE LA RESPUESTA</th>"
                    + "                </tr>"
                    + "                <tr style=\"border: 1px solid black; background-color: #bfdffbab;\">"
                    + "                  <th style=\"border: 1px solid black;\">RESPONSABLE</th>"
                    + "                  <th style=\"border: 1px solid black;\">FECHA</th>"
                    + "                  <th style=\"border: 1px solid black;\">RESPUESTA</th>"
                    + "                </tr>"
                    + "              </thead>"
                    + "              <tbody>"
                    + "                <tr style=\"border: 1px solid black;text-align: center;\">"
                    + "                  <td style=\" border: 1px solid black;\">" + respuesta[0] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + respuesta[1] + "</td>"
                    + "                  <td style=\"border: 1px solid black;\">" + respuesta[2].replace("<a href=\"UserFiles/", "<a href=\"http://172.16.2.111:8084/DYD/UserFiles/").replace("<img src=\"UserFiles/", "<img src=\"http://172.16.2.111:8084/DYD/UserFiles/") + "</td>"
                    + "                </tr>"
                    + "              </tbody>"
                    + "            </table>"
                    + "          </div>"
                    + "        </div>"
                    + "        <h4>Cordialmente</h4>"
                    + "        <p>Programa de Diseño y Desarrollo PLASTITEC</p>"
                    + "        <br>"
                    + "        <p>" + obj_mensaje[2] + "</p>"
                    + "      </div>"
                    + "    </div>"
                    + "  </div>"
                    + "\n";
            message.setText(mail_text, "UTF-8", "HTML");
            Transport transport = session.getTransport("smtp");
            transport.connect(Correo[0], Correo[1]);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
        }
    }

    Server_redeac SuportJpa = new Server_redeac();

    public void SolicitudSoporte(String fecha, String area, String reportante, String descripcion, String prioridad, String correo, String Modulo, int id_caso) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="SOLICITUD-SOPORTE">
        String module = "SOLICITUD SOPORTE";
        List lst_correo = null;
        try {
            lst_correo = SuportJpa.consulMail(module);
        } catch (Exception e) {
            lst_correo = null;
        }
        if (lst_correo == null) {
        } else {
            Object[] obj_correos = {};
            for (int i = 0; i < lst_correo.size(); i++) {
                String[] arr_mail = lst_correo.toString().replace("[", "").replace("]", "").split("////");
                for (int j = 0; j < arr_mail.length; j++) {
                    obj_correos = arr_mail[i].toString().replace(" ", "").split("---");
                    j = arr_mail.length;
                }
            }
//            Object[] obj_correos = (Object[]) lst_correo.get(0);
            Properties propiedades = new Properties();
            propiedades.setProperty("mail.smtp.host", "" + obj_correos[6].toString() + "");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "" + obj_correos[7].toString() + "");//465...587
            propiedades.setProperty("mail.smtp.auth", "true");
            propiedades.setProperty("mail.smtp.socketFactory.port", "587");
            propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
            propiedades.setProperty("mail.smtp.user", "" + obj_correos[2].toString() + "");
            Session session = Session.getDefaultInstance(propiedades);
            // session.setDebug(true);
            try {
                MimeMessage message = new MimeMessage(session);
                String[] destino = obj_correos[4].toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setFrom(new InternetAddress("" + obj_correos[2].toString() + ""));
                message.setSubject("Solicitud Soporte - " + area + " - " + reportante + " - ID " + id_caso + "");// Asunto
                message.setText("<fieldset style='width: 1029px;background-color: #fff;border:1px solid #5356ad;height: auto;'>"
                        + "<table style='background-color: #5356ad; color:#fff; border:1px solid #5356ad; font-size: 14px;'><th>APLICATIVO REDEAC</th></table>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>Buen día</p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>El funcionario(a) <b style='color:#5356ad; font-size: 12px;'>" + reportante + "</b> de <b style='color:#5356ad; font-size: 12px;'>" + area + "</b> Solicita un soporte tecnico con prioridad<b style='color:#5356ad; font-size: 12px;'> " + prioridad + "</b></p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>"
                        + descripcion
                        + "</p>"
                        + "<p style='font-family:Segoe UI;font-size: 14px;color: #292929;'>Dar pronta Solución.</p>"
                        + "<b style='color:#5356ad;'>Atentamente Dpto. Tecnología de información </b>"
                        + "<div style='background-color:ghostwhite; width: 1029px;' >"
                        + "<p style='font-family:Segoe UI;font-size: 11px;color: #1f3b73;'>Este correo pudo ser enviado fuera del horario laboral de quién lo recibe. Le invitamos a responderlo durante su jornada de trabajo.</p>"
                        + "<p style='font-family:Segoe UI;font-size: 10px;color: #BDBDBD;'>Este mensaje y sus archivos adjuntos van dirigidos exclusivamente a su destinatario pudiendo contener información confidencial sometida a secreto profesional. No está permitida su reproducción o distribución sin la autorización expresa de PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. Si usted no es el destinatario final por favor elimínelo e infórmenos por este mismo medio. De acuerdo con la Ley Estatutaria 1581 de 2012 de Protección de Datos y normas concordantes, le informamos que PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. cuenta con política para el tratamiento de los datos personales almacenados en sus bases de datos, la cual puede ser consultada en el siguiente link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . Puede usted ejercitar los derechos de acceso, corrección, supresión, revocación o reclamo por infracción sobre sus datos, mediante escrito dirigido a PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. a la dirección de correo electrónico proteccion.datos@plastitec-sa.com, indicando en el asunto el derecho que desea ejercitar, o mediante correo ordinario remitido a la CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ."
                        + "<br>This message and its attached files are exclusively addressed to its recipient and may contain confidential information subject to professional secrecy. Its reproduction or distribution is not allowed without the express authorization of PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. If you are not the final recipient, please delete it and inform us by this same means. In accordance with Statutory Law 1581 of 2012 on Data Protection and concordant regulations, we inform you that PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. has a policy for the treatment of personal data stored in its databases, which can be consulted at the following link: https://www.plastitec-sa.com/img/PL-01%20Manual%20interno%20de%20politicas%20y%20procedimientos.pdf . You can exercise the rights of access, correction, deletion, revocation or claim for infringement of your data, by writing to PLASTICOS TECNICOS S.A.S. - PLASTITEC S.A.S. to the email address proteccion.datos@plastitec-sa.com, indicating in the subject the right you wish to exercise, or by ordinary mail sent to CARRERA 56 # 5C- 72, BOGOTÁ D.C., BOGOTÁ. </p></div></fieldset>"
                        + "", "ISO-8859-1", "HTML");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect("" + obj_correos[2].toString() + "", "" + obj_correos[3].toString() + "");// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (MessagingException e) {
            }
        }
        //</editor-fold>
    }

}
