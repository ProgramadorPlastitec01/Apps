package Mail;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Controller.ConfigurationControllerJpa;
import Controller.UserControllerJpa;
import java.time.LocalDate;

public class SendMail {

    UserControllerJpa UserJpa = new UserControllerJpa();
    ConfigurationControllerJpa ConfigJpa = new ConfigurationControllerJpa();
    List lst_Users = null;
    List lst_Config = null;
    String Password = "", mail = "";

    public void SendingClientMail(String MailxClient, String UserClient) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL CLIENT">

        LocalDate currentDate = LocalDate.now();
        int CurrentYear = currentDate.getYear();
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");//465...25
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                try {
                    MailFinal = MailxClient.toString().split(";");
                    for (int i = 0; i < MailFinal.length; i++) {
                        if (i == MailFinal.length - 1) {
                            MailClient += MailFinal[i].toString();
                        } else {
                            MailClient += MailFinal[i].toString() + ",";
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailxClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                String[] destino = MailClient.toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Vinculacion Plastitec");// Asunto
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailContent");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "SAGRILAFT/";
//                    String link = "http://localhost:8089/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXUSERCLIENTXXX", UserClient);
                    texto_mail = texto_mail.replace("XXXPASSWORDCLIENTXXX", "" + CurrentYear + "");
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);
                    texto_mail = texto_mail.replace("- - Boton Access - -", "<a href='" + link + "'>" + link + "</a>");
                }

                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void SendingClientMailModify(String MailxClient, String UserClient) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL CLIENT MODIFY">

        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                try {
                    MailFinal = MailxClient.toString().split(";");
                    for (int i = 0; i < MailFinal.length; i++) {
                        if (i == MailFinal.length - 1) {
                            MailClient += MailFinal[i].toString();
                        } else {
                            MailClient += MailFinal[i].toString() + ",";
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailxClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                String[] destino = MailClient.toString().split(",");
                InternetAddress[] addresto = new InternetAddress[destino.length];
                for (int i = 0; i < destino.length; i++) {
                    addresto[i] = new InternetAddress(destino[i]);
                }
                message.setRecipients(Message.RecipientType.TO, addresto);// correo destinatario
                message.setSubject("Actualización - Plastitec");// Asunto
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailContentModify");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
//                    String link = "http://localhost:8089/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXUSERCLIENTXXX", UserClient);
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);
                }

                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void SendingClientMailReturn(String MailxClient, String UserClient) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL CLIENT RETURN">
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
//                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");//465...25
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                try {
                    MailFinal = MailxClient.toString().split(";");
                    for (int i = 0; i < MailFinal.length; i++) {
                        if (i == MailFinal.length - 1) {
                            MailClient += MailFinal[i].toString();
                        } else {
                            MailClient += MailFinal[i].toString() + ",";
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailxClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("Vinculacion Plasttiec - Devolucion");// Asunto
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailContentReturn");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
//                    String link = "http://" + hostport + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXUSERCLIENTXXX", UserClient);
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);
                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void SendingBossNotify(String Client) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL NOTIFY BOSS">
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");//465...25
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailBoss");
                if (lst_Config != null) {
                    Object[] ObjBoss = (Object[]) lst_Config.get(0);
                    MailClient = ObjBoss[2].toString();
                }
                try {
                    MailFinal = MailClient.toString().split(";");
                    if (MailFinal.length == 1) {
                    } else {
                        for (int i = 0; i < MailFinal.length; i++) {
                            if (i == MailFinal.length - 1) {
                                MailClient += MailFinal[i].toString();
                            } else {
                                MailClient += MailFinal[i].toString() + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("SAGRILAFT Plasttiec - Notificación");// Asunto}
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailBossContent");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
//                    String hostport = objParam[3].toString().replace("/", ":");
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
//                    String link = "http://" + hostport + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);
                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//</editor-fold>
    }

    public void SendingNotifyAdd(String Client, String UserClient) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL NOTIFY ADD">

        LocalDate currentDate = LocalDate.now();
        int CurrentYear = currentDate.getYear();
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");//465...25
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailCopy");
                if (lst_Config != null) {
                    Object[] ObjBoss = (Object[]) lst_Config.get(0);
                    MailClient = ObjBoss[2].toString();
                }
                try {
                    MailFinal = MailClient.toString().split(";");
                    if (MailFinal.length == 1) {
                    } else {
                        for (int i = 0; i < MailFinal.length; i++) {
                            if (i == MailFinal.length - 1) {
                                MailClient += MailFinal[i].toString();
                            } else {
                                MailClient += MailFinal[i].toString() + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("PT Notificación - Nuevo documento");// Asunto
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailNotifyAdd");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);

                    texto_mail = texto_mail.replace("XXXUSERCLIENTXXX", UserClient);
                    texto_mail = texto_mail.replace("XXXPASSWORDCLIENTXXX", "" + CurrentYear + "");

                    texto_mail = texto_mail.replace("XXXLINKXXX", link);

                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }
    
    public void SendingNotifyAdd_reNew(String Client, String UserClient) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL NOTIFY RE NEW DOCUMENT USER">
        LocalDate currentDate = LocalDate.now();
        int CurrentYear = currentDate.getYear();
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailCopy");
                if (lst_Config != null) {
                    Object[] ObjBoss = (Object[]) lst_Config.get(0);
                    MailClient = ObjBoss[2].toString();
                }
                try {
                    MailFinal = MailClient.toString().split(";");
                    if (MailFinal.length == 1) {
                    } else {
                        for (int i = 0; i < MailFinal.length; i++) {
                            if (i == MailFinal.length - 1) {
                                MailClient += MailFinal[i].toString();
                            } else {
                                MailClient += MailFinal[i].toString() + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("PT Notificación - Nuevo documento");// Asunto
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailNotifyAdd");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);

                    texto_mail = texto_mail.replace("XXXUSERCLIENTXXX", UserClient);
                    texto_mail = texto_mail.replace("XXXPASSWORDCLIENTXXX", "" + CurrentYear + "");

                    texto_mail = texto_mail.replace("XXXLINKXXX", link);

                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void SendingNotifyModify(String Client) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL NOTIFY MODIFY">
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");//465...25
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailCopy");
                if (lst_Config != null) {
                    Object[] ObjBoss = (Object[]) lst_Config.get(0);
                    MailClient = ObjBoss[2].toString();
                }
                try {
                    MailFinal = MailClient.toString().split(";");
                    if (MailFinal.length == 1) {
                    } else {
                        for (int i = 0; i < MailFinal.length; i++) {
                            if (i == MailFinal.length - 1) {
                                MailClient += MailFinal[i].toString();
                            } else {
                                MailClient += MailFinal[i].toString() + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("PT Notificación - Modificacion de documento");// Asunto}
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailNotifyModify");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);
                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void SendingNotifyReturn(String Client) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL NOTIFY RETURN">
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailCopy");
                if (lst_Config != null) {
                    Object[] ObjBoss = (Object[]) lst_Config.get(0);
                    MailClient = ObjBoss[2].toString();
                }
                try {
                    MailFinal = MailClient.toString().split(";");
                    if (MailFinal.length == 1) {
                    } else {
                        for (int i = 0; i < MailFinal.length; i++) {
                            if (i == MailFinal.length - 1) {
                                MailClient += MailFinal[i].toString();
                            } else {
                                MailClient += MailFinal[i].toString() + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("PT Notificación - Devolución de documento");// Asunto}
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailNotifyReturn");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);
                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>
    }

    public void ResendMailClient(String user, String Client, String MailCl) throws javax.mail.MessagingException {
        //<editor-fold defaultstate="collapsed" desc="MAIL TO REMEMBER CLIENT USER">
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailCopy");
                if (lst_Config != null) {
                    Object[] ObjCopy = (Object[]) lst_Config.get(0);
                    MailClient = ObjCopy[2].toString() + ", " + MailCl;
                }
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setRecipients(Message.RecipientType.TO, MailClient);
                    message.setSubject("PT Recordatorio - Credenciales");
                    message.setFrom(new InternetAddress(mail));
                    lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailResendClient");
                    int anio = LocalDate.now().getYear();
                    String texto_mail = "";
                    if (lst_Config != null) {
                        Object[] objParam = (Object[]) lst_Config.get(0);
                        String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
                        texto_mail = objParam[2].toString();
                        texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);
                        texto_mail = texto_mail.replace("XXXUSERXXX", user);
                        texto_mail = texto_mail.replace("XXXPASSXXX", "" + anio);
                        texto_mail = texto_mail.replace("XXXLINKXXX", link);
                    }
                    message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                    Transport transport = session.getTransport("smtp");
                    transport.connect(mail, Password);// Su Correo y Contraseña
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//</editor-fold>
    }
    
    public void NotifyPlastitecDocumentEnd(String Client) throws javax.mail.MessagingException{
        LocalDate currentDate = LocalDate.now();
        int CurrentYear = currentDate.getYear();
        try {
            Properties propiedades = new Properties();
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailConfig");
            if (lst_Config != null) {
                Object[] objParam = (Object[]) lst_Config.get(0);
                String[] datMail = objParam[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                propiedades.setProperty("mail.smtp.host", datMail[0]);
                propiedades.setProperty("mail.smtp.starttls.enable", datMail[1]);
                propiedades.setProperty("mail.smtp.port", datMail[2]);//465...25
                propiedades.setProperty("mail.smtp.auth", datMail[3]);
                propiedades.setProperty("mail.smtp.user", datMail[4]);
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = datMail[4].toString();
                Password = datMail[5].toString();
            } else {
                propiedades.setProperty("mail.smtp.host", "mail3.plastitec-sa.com");
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                propiedades.setProperty("mail.smtp.port", "587");//465...25
                propiedades.setProperty("mail.smtp.auth", "true");
                propiedades.setProperty("mail.smtp.user", "aplicativo@plastitec-sa.com");
                propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                propiedades.setProperty("mail.smtp.socketFactory.fallback", "true");
                mail = "aplicativo@plastitec-sa.com";
                Password = "Notificaciones2022+";
            }
            Session session = Session.getDefaultInstance(propiedades);
            try {
                String[] MailFinal = {};
                String MailClient = "";
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailCopy");
                if (lst_Config != null) {
                    Object[] ObjBoss = (Object[]) lst_Config.get(0);
                    MailClient = ObjBoss[2].toString();
                }
                try {
                    MailFinal = MailClient.toString().split(";");
                    if (MailFinal.length == 1) {
                    } else {
                        for (int i = 0; i < MailFinal.length; i++) {
                            if (i == MailFinal.length - 1) {
                                MailClient += MailFinal[i].toString();
                            } else {
                                MailClient += MailFinal[i].toString() + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    MailClient = MailClient.toString();
                }
                MimeMessage message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, MailClient);// correo destinatario
                message.setSubject("PT Notificación - Documento finalizado");// Asunto
                message.setFrom(new InternetAddress(mail));
                lst_Config = ConfigJpa.ConsultSettingsByCategorie("MailNotifyEndDoc");
                String texto_mail = "";
                if (lst_Config != null) {
                    Object[] objParam = (Object[]) lst_Config.get(0);
                    String link = "http://" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[0] + ":" + objParam[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///")[1] + "/SAGRILAFT/";
                    texto_mail = objParam[2].toString();
                    texto_mail = texto_mail.replace("XXXCLIENTEXXX", Client);
                    texto_mail = texto_mail.replace("XXXLINKXXX", link);

                }
                message.setContent(texto_mail, "text/html; charset=UTF-8");//Mensaje
                Transport transport = session.getTransport("smtp");
                transport.connect(mail, Password);// Su Correo y Contraseña
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
