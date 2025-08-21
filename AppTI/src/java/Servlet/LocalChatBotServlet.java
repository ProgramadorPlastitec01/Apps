package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.text.Normalizer;
import java.util.concurrent.ConcurrentHashMap;
import Controller.ShiftControllerJpa;
import Controller.UserControllerJpa;
import java.util.List;

@WebServlet("/LocalChatBotServlet")
public class LocalChatBotServlet extends HttpServlet {

    private final Map<String, String> respuestas = new HashMap<>();
    private final Map<String, String> estadoPorUsuario = new ConcurrentHashMap<>();
    private final Map<String, String> datosUsuario = new ConcurrentHashMap<>();
    private final ShiftControllerJpa ShiftJpa = new ShiftControllerJpa();
    private final UserControllerJpa UserJpa = new UserControllerJpa();

    public static String normalizarTexto(String texto) {
        String textoSinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoSinAcentos = textoSinAcentos.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        textoSinAcentos = textoSinAcentos.replaceAll("[^a-zA-Z0-9\\s]", ""); // elimina signos
        return textoSinAcentos.toLowerCase().trim();
    }

    String FinalShift = "";

    private String obtenerUsuariosEnTurno() {

        String UnionShift = "";
        List<Object[]> lst_shift = ShiftJpa.ConsultShiftStart();
        if (lst_shift != null && !lst_shift.isEmpty()) {
            Object[] ObjShift = lst_shift.get(0);
            if (ObjShift[2].toString().contains("[")) {
                String[] ArgShift = ObjShift[2].toString()
                        .replace("][", "///")
                        .replace("[", "")
                        .replace("]", "")
                        .split("///");

                for (String arg : ArgShift) {
                    String[] partes = arg.split("/");
                    if (partes.length < 2) {
                        continue;
                    }

                    String ArgT = partes[0];
                    String ArgU = partes[1];
                    String TextUserFormatter = "";

                    List<Object[]> lst_user = UserJpa.ConsultUsersMultiple(ArgU);
                    if (lst_user != null) {
                        for (int j = 0; j < lst_user.size(); j++) {
                            Object[] ObjUser = lst_user.get(j);
                            if (j == 0) {
                                TextUserFormatter = ObjUser[12].toString();
                            } else {
                                TextUserFormatter += ", " + ObjUser[12];
                            }
                        }
                    }
                    UnionShift += "<div><b>" + ArgT + ":</b> " + TextUserFormatter + "</div><div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>";
                    FinalShift = UnionShift;
                }
            }
        }
        return UnionShift.trim().isEmpty() ? "No hay turnos registrados actualmente.  <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>" : UnionShift.trim();
    }

    @Override
    public void init() throws ServletException {
        respuestas.put(normalizarTexto("hola"), "¡Hola! ¿En qué puedo ayudarte?");
        respuestas.put(normalizarTexto("horario"), "Nuestros horarios son: <div><b>Tecnicos:</b> Labor en los tres turno de lunes a sábado. </div><div><b>Programadores:</b> 7 am a 5 pm de lunes a viernes. </div><div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("gracias"), "¡Con gusto! <i class=\"far fa-smile-wink\"></i> <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("adios"), "Hasta luego. <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("buenos días"), "¡Buenos días! Espero que tengas un excelente día. <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("buenas tardes"), "¡Buenas tardes! ¿Cómo te puedo ayudar? <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("buenas noches"), "¡Buenas noches! Que descanses. <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("¿cómo estás?"), "Estoy muy bien, ¡gracias por preguntar! <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("¿quién eres?"), "Soy tu asistente local. Estoy aquí para ayudarte. <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("adiós"), "Hasta luego. ¡Que tengas un buen día! <i class=\"far fa-smile-wink\"></i> <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("¿qué puedes hacer?"), "Puedo responder preguntas frecuentes y ayudarte con tareas simples. <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("quien esta en turno"), FinalShift + "<div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("¿qué día es hoy?"),
                LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", new Locale("es", "ES"))));
        respuestas.put(normalizarTexto("¿qué hora es?"),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        respuestas.put(normalizarTexto("ayuda"),
                "Puedo ayudarte con lo siguiente:<br>- Información básica<br>- Preguntas comunes<br>Ejemplo: '¿quién eres?' o '¿qué puedes hacer?' o 'Solicitud de un soporte'. <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("contacto"),
                "Contacto T.I: <ul class='ContactBot'>"
                + "<li><b>Celular:</b> 3175023662 - Corporativo Técnicos</li>"
                + "<li><b>Extensión Programadores:</b> 250</li>"
                + "<li><b>Extensión Técnicos:</b> 235</li>"
                + "<li><b>Correos:</b> g.ti@plastitec-sa.com, soporte.ti@plastitec-sa.com, p.ti@plastitec-sa.com.</li>"
                + "<li>Para la solicitud de un ticket escribe tu mensaje indicando <b>Solicito un soporte</b></li>"
                + "</ul>"
                + "<div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("extension"), respuestas.get(normalizarTexto("contacto")));
        respuestas.put(normalizarTexto("extensiones"), respuestas.get(normalizarTexto("contacto")));
        respuestas.put(normalizarTexto("manuales"),
                "Nuestros manuales o instructivos los puedes encontrar en el aplicativo DARUMA o puedes acceder a este <a target='_blank' href='http://172.16.2.99/app.php/staff/portal/tab/6'>LINK</a><div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("capacitacion"),
                "Contamos con diferentes videos de capacitación, puedes ingresar por medio de este <a target='_blank' href='http://172.16.2.99/app.php/staff/portal/tab/9'>LINK</a> <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
        respuestas.put(normalizarTexto("tags"),
                "Plastitec Tags es el sistema de información encargado de la identificación del producto, si quiere conocer mas de este aplicativo aqui te dejo unos instructivos que te ayudarán. "
                + "<ul>"
                + "<li>I-TI-037 Producto terminado: <a target='_blank' href='http://172.16.2.99/app.php/staff/document/viewPublic?index=4524'>LINK</a></li>"
                + "<li>I-TI-038 Producto en proceso: <a target='_blank' href='http://172.16.2.99/app.php/staff/document/viewPublic?index=4523'>LINK</a></li>"
                + "<li>I-TI-039 Gestión de mallas: <a target='_blank' href='http://172.16.2.99/app.php/staff/document/viewPublic?index=4525'>LINK</a></li>"
                + " </ul> <div class='text-center m-2'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Volver al Menú</button></div>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String mensajeOriginal = request.getParameter("message");
        if (mensajeOriginal == null || mensajeOriginal.trim().isEmpty()) {
            response.getWriter().write("Por favor ingresa un mensaje válido.");
            return;
        }

        mensajeOriginal = mensajeOriginal.trim();
        String mensaje = normalizarTexto(mensajeOriginal);
        String respuesta = "Lo siento, no pude entender tu mensaje.<br/>\n"
                + "Para ver las opciones disponibles, haz clic en el siguiente botón:<div class='text-center'><button onclick='showMenu()' class='btn btn-green CustomBtn'>Menú</button></div>";

        String userId = request.getRemoteAddr();
        String estado = estadoPorUsuario.getOrDefault(userId, "inicio");

        // Soporte para comando reiniciar
        if (mensaje.equals("menu")) {
            // Enviamos una señal especial para el cliente
            response.getWriter().write("__SHOW_MENU__");
            return;
        }

        if (mensaje.equals("reiniciar")) {
            estadoPorUsuario.remove(userId);
            datosUsuario.remove(userId + "_documento");
            datosUsuario.remove(userId + "_codigo");
            response.getWriter().write("Conversación reiniciada. ¿En qué te puedo ayudar?");
            return;
        }

        switch (estado) {
            case "inicio":
                if (mensaje.equals("manual")) {
                    respuesta = respuestas.get(normalizarTexto("manuales"));
                } else if (mensaje.equals("contacto")) {
                    respuesta = respuestas.get(normalizarTexto("contacto"));
                } else if (mensaje.contains("solicito un soporte")) {
                    respuesta = "Claro, por favor ingresa tu número de documento.";
                    estadoPorUsuario.put(userId, "esperando_documento");
                } else if (mensaje.contains("solicito un usuario")) {
                    respuesta = "Claro, para proceder con la creación del usuario, vamos a generar un ticket, por favor ingresa tu número de documento.";
                    estadoPorUsuario.put(userId, "esperando_documento");
                } else if (mensaje.contains(normalizarTexto("quien esta en turno"))) {
                    respuesta = "TI: " + obtenerUsuariosEnTurno();
                } else {
                    for (Map.Entry<String, String> entry : respuestas.entrySet()) {
                        if (mensaje.contains(entry.getKey())) {
                            respuesta = entry.getValue();
                            break;
                        }
                    }
                }
                break;

            case "esperando_documento":
                datosUsuario.put(userId + "_documento", mensajeOriginal);
                respuesta = "Gracias. Ahora ingresa el código asignado por la empresa.";
                estadoPorUsuario.put(userId, "esperando_codigo");
                break;

            case "esperando_codigo":
                datosUsuario.put(userId + "_codigo", mensajeOriginal);

                String doc = datosUsuario.get(userId + "_documento");
                String cod = datosUsuario.get(userId + "_codigo");

                String link = "http://localhost:8089/AppTI/Customer?opt=1&idDoct=" + doc;

                respuesta = "Gracias. Puedes acceder a la página  soporte con el siguiente enlace:<br><a class='b_' href='" + link + "' target='_blank'>LINK</a>";

                estadoPorUsuario.remove(userId);
                datosUsuario.remove(userId + "_documento");
                datosUsuario.remove(userId + "_codigo");
                break;
        }

        response.getWriter().write(respuesta);
    }
}
