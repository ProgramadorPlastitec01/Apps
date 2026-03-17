package GLPI;

import GLPI.GLPIClient;

public class GLPIUserService {

    private GLPIClient client;

    public GLPIUserService(GLPIClient client) {
        this.client = client;
    }

    public String procesarUsuario(String login, String nombre, String apellido, String password, boolean activo) throws Exception {
        String resultadoBusqueda = client.buscarUsuario(login);
        if (resultadoBusqueda.contains("\"totalcount\":0")) {
            if (activo) {
                // No existe y debe estar activo → crear
                return client.crearUsuario(login, nombre, apellido, password);
            } else {
                return "El usuario no existe en GLPI y no se crea porque viene inactivo";
            }
        } else {
            int idUsuario = extraerIdUsuario(resultadoBusqueda);
            if (idUsuario == -1) {
                return "No se pudo obtener el ID del usuario";
            }
            if (!activo) {
                // Usuario existe pero debe quedar inactivo
                return client.inactivarUsuario(idUsuario);
            }
            // Usuario existe y sigue activo → actualizar datos
            return client.editarUsuario(idUsuario, nombre, apellido);
        }
    }

    private int extraerIdUsuario(String json) {
        int indice = json.indexOf("\"id\":");
        if (indice == -1) {
            return -1;
        }
        String sub = json.substring(indice + 5);
        String numero = sub.split(",")[0].replaceAll("[^0-9]", "");
        return Integer.parseInt(numero);
    }
}
