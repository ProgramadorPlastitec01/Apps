
import GLPI.GLPIClient;
import GLPI.GLPISession;

public class test {

    public static void main(String[] args) throws Exception {
        GLPIClient Glpi_Opc = new GLPIClient();

        //<editor-fold defaultstate="collapsed" desc="PRUEBA CONSULTA">
//        GLPISession session = new GLPISession();
//        String token = session.initSession();
//        System.out.println("Session token: " + token);
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="REGISTER USER">
//        String codigo = "20006"; 
//        String nombres = "Pruebas 06";
//        String apellidos = "06 prueba";
//        String res = Glpi_Opc.crearUsuario(codigo, nombres, apellidos, "2026");
//        System.out.print(res);
//        res = res.replaceAll("\\D+", "");
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="EDIT USER">
//        String codigo = "8479"; // ID UNICO DEL USUARIO
//        String nombres = "Pruebas 01 01";
//        String apellidos = "01 prueba 01";
//        String res = Glpi_Opc.editarUsuario(Integer.parseInt(codigo), nombres, apellidos);
//        
//        res = res.replaceAll("\\D+", "");
//        System.out.print(res);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="INACTIVE USER">
//        String codigo = "8479"; // ID UNICO DEL USUARIO
//        String nombres = "Pruebas 01 01";
//        String apellidos = "01 prueba 01";
//        String correo = "prueba@plasttiec.co";
//        String res = Glpi_Opc.inactivarUsuario(Integer.parseInt(codigo));
//        System.out.print(res);
//</editor-fold>
    }
}
