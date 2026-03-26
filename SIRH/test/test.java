
import GLPI.GLPIClient;
import GLPI.GLPISession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controladores_BD.PersonalJpaController;

public class test {
//
    public static void main(String[] args) throws Exception {
        PersonalJpaController PersonalJpa = new PersonalJpaController();
        GLPIClient Glpi_Opc = new GLPIClient();
//
//        //<editor-fold defaultstate="collapsed" desc="PRUEBA CONSULTA">
////        GLPISession session = new GLPISession();
////        String token = session.initSession();
////        System.out.println("Session token: " + token);
////</editor-fold>
//        //<editor-fold defaultstate="collapsed" desc="REGISTER USER">
//        String codigo = "20014"; 
//        String nombres = "Pruebas 06";
//        String apellidos = "06 prueba";
//        String res = Glpi_Opc.crearUsuario(codigo, nombres, apellidos, "2026");
//        System.out.print(res);
//        res = res.replaceAll("\\D+", "");
//        //</editor-fold>
//        //<editor-fold defaultstate="collapsed" desc="EDIT USER">
////        String codigo = "8479"; // ID UNICO DEL USUARIO
////        String nombres = "Pruebas 01 01";
////        String apellidos = "01 prueba 01";
////        String res = Glpi_Opc.editarUsuario(Integer.parseInt(codigo), nombres, apellidos);
////        
////        res = res.replaceAll("\\D+", "");
////        System.out.print(res);
//        //</editor-fold>
//        //<editor-fold defaultstate="collapsed" desc="INACTIVE USER">
////        String codigo = "8479"; // ID UNICO DEL USUARIO
////        String nombres = "Pruebas 01 01";
////        String apellidos = "01 prueba 01";
////        String correo = "prueba@plasttiec.co";
////        String res = Glpi_Opc.inactivarUsuario(Integer.parseInt(codigo));
////        System.out.print(res);
////</editor-fold>
//        //<editor-fold defaultstate="collapsed" desc="REGISTRO MASIVO DE USUARIOS">
        Gson gson = new Gson();

        FileReader reader = new FileReader("sirh.json");
        Type listType = new TypeToken<List<Usuario>>() {
        }.getType();
        List<Usuario> usuarios = gson.fromJson(reader, listType);
        int total = usuarios.size();
        int contador = 0;
        for (Usuario u : usuarios) {
            contador++;

            try {

                String codigo = u.getLogin();
                String nombres = u.getNombre();
                String apellidos = u.getApellido();
                // contraseña inicial
                String password = "2026";

                System.out.println(
                        "Registrando "
                        + contador + "/" + total
                        + " -> " + codigo
                );

                System.out.println("Registrando: " + codigo);

                String res = Glpi_Opc.crearUsuario(
                        codigo,
                        nombres,
                        apellidos,
                        password
                );
                // Limpiar respuesta
                Pattern pattern = Pattern.compile("\"id\":(\\d+)");
                Matcher matcher = pattern.matcher(res);
                int idUsuario = 0;

                if (matcher.find()) {
                    idUsuario = Integer.parseInt(matcher.group(1));
                }

                PersonalJpa.Registrar_id_glpi_codigo(Integer.parseInt(codigo), idUsuario);
                res = res.replaceAll("\\D+", "");
                System.out.println("Resultado: " + res);
                Thread.sleep(200);

            } catch (Exception e) {

                System.out.println(
                        "ERROR -> "
                        + u.getLogin()
                );

            }

        }
        System.out.println("Proceso finalizado.");
//        //</editor-fold>
//
    }
//
    public class Usuario {

        private String login;
        private String nombre;
        private String apellido;

        // Getters
        public String getLogin() {
            return login;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        // Setters
        public void setLogin(String login) {
            this.login = login;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }
    }
}
