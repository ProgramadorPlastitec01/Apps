package Methods;

import Controladores.ProyectoJpaController;
import java.util.List;

public class Distribucion {

    String listado_distribucion = "";
    ProyectoJpaController jpacpyt = new ProyectoJpaController();
    List lst_proyecto = null;
    int contador = 0;
    boolean bool = false;
    boolean bool_participe = false;

    public boolean Listado_distribucion(int id_proyecto, String nuevos_participes) {
        lst_proyecto = jpacpyt.Traer_participe(id_proyecto);
        Object[] obj_proyecto = (Object[]) lst_proyecto.get(0);
        listado_distribucion = obj_proyecto[1].toString();
        String[] arg_participes = nuevos_participes.replace("][", "-").replace("[", "").replace("]", "").split("-");
        for (int i = 0; i < arg_participes.length; i++) {
            bool_participe = listado_distribucion.contains("[" + arg_participes[i] + "]");
            if (!bool_participe) {
                listado_distribucion = listado_distribucion + "[" + arg_participes[i] + "]";
            }
        }
        bool = jpacpyt.Lista_distribucion(id_proyecto, listado_distribucion.replace("[]", ""));
        return bool;
    }
}
