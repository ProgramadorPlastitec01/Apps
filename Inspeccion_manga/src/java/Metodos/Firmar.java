package Metodos;

import Controladores.RegistroJpaController;

public class Firmar {

    RegistroJpaController jpacrgt = new RegistroJpaController();
    boolean proceso = true;
    String[] array_responsables;
    int contador = 0;

    public boolean Firmar_registro(int id_registro, String responsables_pi, String responsables_gc, String rol, String usuario) throws Exception {
        try {
            if (responsables_pi == null ? "" == null : responsables_pi.equals("") || responsables_pi.equals("PENDIENTE")) {
                responsables_pi =  usuario;
                proceso = jpacrgt.Firmar_turno(id_registro, responsables_pi, rol);
            } else {
                if (rol.equals("Coordinador_extrusion") || rol.equals("Operario_extrusion")) {
                    array_responsables = responsables_pi.split(",");
                    for (int i = 0; i < array_responsables.length; i++) {
                        if ((array_responsables[i].toString().split("/")[0] + "/" + array_responsables[i].toString().split("/")[1]).equals(usuario.toString())) {
                            contador = 0;
                            break;
                        } else {
                            contador++;
                        }
                    }
                    if (contador > 0) {
                        responsables_pi = responsables_pi + "," + rol + "/" + usuario;
                    }
                    proceso = jpacrgt.Firmar_turno(id_registro, responsables_pi, rol);
                }
            }
            if (responsables_gc == null ? "" == null : responsables_gc.equals("") || responsables_gc.equals("PENDIENTE")) {
                responsables_gc =  usuario;
                proceso = jpacrgt.Firmar_turno(id_registro, responsables_gc, rol);
            } else {
                if (rol.equals("Inspectora_calidad") || rol.equals("Coordinadora_calidad")) {
                    array_responsables = responsables_gc.split(",");
                    for (int i = 0; i < array_responsables.length; i++) {
                        if ((array_responsables[i].toString().split("/")[0] + "/" + array_responsables[i].toString().split("/")[1]).equals(usuario.toString())) {
                            contador = 0;
                            break;
                        } else {
                            contador++;
                        }
                    }
                    if (contador > 0) {
                        responsables_gc = responsables_gc + "," + usuario;
                    }
                    proceso = jpacrgt.Firmar_turno(id_registro, responsables_gc, rol);
                }
            }
            return proceso;
        } catch (Exception ex) {
            return false;
        }
    }
}
