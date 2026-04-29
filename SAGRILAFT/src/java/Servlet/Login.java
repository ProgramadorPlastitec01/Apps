package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.UserControllerJpa;
import java.util.List;
import javax.servlet.http.HttpSession;
import Encript.EncriptControl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.json.JSONObject;

public class Login extends HttpServlet {

    private static final String SECRET_KEY = "6Lchq40sAAAAAAgAGuVmdrbv2bVQrRWMlpucWWFs";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            response.getWriter().println("Por favor valida el captcha");
            return;
        }
        boolean captchaValido = validarCaptcha(gRecaptchaResponse);
        if (!captchaValido) {
            response.getWriter().println("Captcha inválido ❌");
            return;
        }
        //<editor-fold defaultstate="collapsed" desc="AFTER CAPTCHA">
        try {
            HttpSession sesion = request.getSession();
            EncriptControl md5 = new EncriptControl();
            UserControllerJpa UserJpa = new UserControllerJpa();
            List lst_usuario = null;
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idUsuario = 0;
            boolean accion = true;
            int id_usuario = 0, temp = 0;
            int ste = 0;
            int attempts = 0;
            String Expired = "";

            String user, password, passwordEncrypt = "";

            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="INICIO DE SESION">
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }

                    if (temp == 1) {
                        // Cambio de contraseña temporal
                        id_usuario = Integer.parseInt(request.getParameter("Txt_user"));
                        request.setAttribute("idUsuario", id_usuario);
                        request.setAttribute("Cambio_contraseña", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);

                    } else {
                        user = request.getParameter("Txt_user");
                        password = request.getParameter("Txt_password");

                        // 1️⃣ Consultar estado del usuario (SP) ANTES de login
                        List Attempts = UserJpa.ConsultAttempt(user);
                        idUsuario = 0;
                        ste = 0;
                        attempts = 0;
                        Expired = "";

                        if (Attempts != null && !Attempts.isEmpty()) {
                            Object[] att = (Object[]) Attempts.get(0);
                            idUsuario = Integer.parseInt(att[0].toString());
                            ste = Integer.parseInt(att[1].toString());
                            attempts = Integer.parseInt(att[2].toString());
                            try {
                                Expired = att[3].toString();
                            } catch (Exception e) {
                                Expired = "";
                            }

                            // 2️⃣ Validar bloqueo ANTES de login
                            boolean bloqueado = false;
                            if (Expired != null && !Expired.isEmpty()) {
                                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date fechaBloqueo = formato.parse(Expired);
                                Date ahora = new Date();
                                if (fechaBloqueo.after(ahora)) {
                                    bloqueado = true;
                                }
                            }

                            if (bloqueado) {
                                request.setAttribute("Usuario_bloqueado", true);
                                request.setAttribute("Tiempo_bloqueo", Expired);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                                return; // corta flujo antes de login
                            }
                        }

                        // 3️⃣ Intentar login SOLO si no está bloqueado
                        lst_usuario = null;
                        if (password.length() >= 8) {
                            passwordEncrypt = md5.md5(password);
                            lst_usuario = UserJpa.LoginUser(user, passwordEncrypt);
                            if (lst_usuario == null) {
                                lst_usuario = UserJpa.LoginUser(user, password);
                            }
                        } else {
                            lst_usuario = UserJpa.LoginUser(user, password);
                        }

                        // 4️⃣ Login fallido → incrementar intentos
                        if (lst_usuario == null) {
                            if (Attempts != null && !Attempts.isEmpty()) {
                                attempts += 1;

                                if (attempts >= 3) {
                                    // Bloquear 15 minutos
                                    Calendar cal = Calendar.getInstance();
                                    cal.add(Calendar.MINUTE, 15);
                                    Date nuevaExpiracion = cal.getTime();
                                    String dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nuevaExpiracion);
                                    UserJpa.BlockAttempts(idUsuario, dateformat);

                                    request.setAttribute("Usuario_bloqueado", true);
                                    request.setAttribute("Tiempo_bloqueo", dateformat);
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                    return;
                                } else {
                                    // Solo actualizar intentos
                                    UserJpa.Attempts(idUsuario, attempts);
                                }

                                // Mensaje genérico de login fallido
                                request.setAttribute("Usuario_no_existe", false);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                                return;

                            } else {
                                // Usuario no existe en la BD
                                request.setAttribute("Usuario_no_existe", false);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                                return;
                            }
                        }

                        // 5️⃣ Login exitoso → crear sesión y resetear intentos
                        Object[] obj_sesion = (Object[]) lst_usuario.get(0);

                        if ((Integer) obj_sesion[11] == 0) {
                            // Usuario desactivado
                            request.setAttribute("Usuario_no_existe", false);
                            request.setAttribute("var1", obj_sesion[1]);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                            return;

                        } else if (obj_sesion[6].toString().equals("Si")) {
                            // Cambio de contraseña requerido
                            request.setAttribute("idUsuario", obj_sesion[0]);
                            request.setAttribute("Cambio_contraseña", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                            return;

                        } else {
                            // Usuario activo, iniciar sesión
                            sesion.setAttribute("idUsuario", obj_sesion[0]);
                            sesion.setAttribute("Nombres", obj_sesion[1]);
                            sesion.setAttribute("Rol/Nombres", obj_sesion[8] + "/" + obj_sesion[1]);
                            sesion.setAttribute("Documento", obj_sesion[4]);
                            sesion.setAttribute("Usuario", obj_sesion[5]);
                            sesion.setAttribute("idRol", obj_sesion[8]);
                            sesion.setAttribute("NombreRol", obj_sesion[9]);
                            sesion.setAttribute("Nombre", obj_sesion[2]);
                            try {
                                sesion.setAttribute("Apellido", obj_sesion[3]);
                            } catch (Exception e) {
                            }
                            sesion.setAttribute("Permisos", obj_sesion[12]);
                            sesion.setAttribute("Estado", obj_sesion[11]);
                            try {
                                sesion.setAttribute("idDoc", obj_sesion[13]);
                            } catch (Exception e) {
                            }

                            // Resetear intentos tras login exitoso
                            if (idUsuario > 0) {
                                UserJpa.ResetAttempts(idUsuario);
                            }

                            request.setAttribute("welcome", true);

                            if ((Integer) obj_sesion[8] == 6 && obj_sesion[12] != null) {
                                request.getRequestDispatcher("ClientSection?opt=1&IdDoc=" + obj_sesion[13]).forward(request, response);
                            } else {
                                request.getRequestDispatcher("Start.jsp").forward(request, response);
                            }
                        }
                    }

                    //</editor-fold>
                    break;
                case 2:
                    idUsuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password");
                    passwordEncrypt = md5.md5(password);
                    accion = UserJpa.UserPassword(idUsuario, passwordEncrypt);
                    request.setAttribute("password_update", accion);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        //</editor-fold>

        response.getWriter().println("Captcha válido ✅");
    }

    private boolean validarCaptcha(String captchaResponse) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String params = "secret=" + SECRET_KEY + "&response=" + captchaResponse;

            OutputStream os = con.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder responseStr = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseStr.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(responseStr.toString());

            return json.getBoolean("success");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
