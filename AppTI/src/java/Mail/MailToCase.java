package Mail;

import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class MailToCase {

    // Configuración de Zoho y MySQL
    private static final String HOST = "imap.zoho.com";
    private static final String USER = "aplicativo@plastitec.co";
    private static final String PASSWORD = "wxWmH1szhuJn"; // App password Zoho
    private static final String DB_URL = "jdbc:mysql://172.16.1.161:3306/appti";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    // Procesar correos no leídos
    public static void procesarCorreos() {
        try {
            // Configuración IMAP
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props);

            Store store = session.getStore("imaps");
            store.connect(HOST, 993, USER, PASSWORD);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            // Conexión MySQL
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Buscar SOLO correos no leídos
            Message[] mensajesNoLeidos = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            if (mensajesNoLeidos.length > 0) {
                System.out.println("📩 Encontrados " + mensajesNoLeidos.length + " correos nuevos.");

                for (Message msg : mensajesNoLeidos) {
                    String remitente = ((InternetAddress) msg.getFrom()[0]).getAddress();

                    // Ignorar correos automáticos (bounces) y propios
                    if (remitente.toLowerCase().contains("mailer-daemon")
                            || remitente.toLowerCase().contains("postmaster")
                            || remitente.toLowerCase().contains("noreply@zohoaccounts.com")
                            || remitente.toLowerCase().contains("aplicativo@plastitec.co") 
                            || remitente.toLowerCase().contains("MicrosoftExchange329e71ec88ae4615bbc36ab6ce41109e@plastitec-sa.com")) {
                        msg.setFlag(Flags.Flag.SEEN, true); // marcar como leído
                        System.out.println("⚠️ Correo ignorado: " + remitente);
                        continue;
                    }

                    String asunto = msg.getSubject();
                    String contenido = getTextFromMessage(msg);

                    PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO support (solution, affair, request) VALUES (?, ?, ?)"
                    );
                    ps.setString(1, remitente);
                    ps.setString(2, asunto);
                    ps.setString(3, contenido);
                    ps.executeUpdate();

                    msg.setFlag(Flags.Flag.SEEN, true); // marcar como leído
                    System.out.println("✅ Ticket creado para correo de: " + remitente);
                }

            } else {
                System.out.println("📭 No hay correos nuevos.");
            }

            conn.close();
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Extraer texto (plain o html)
    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return mimeMultipart.getBodyPart(0).getContent().toString();
        }
        return "";
    }
}
