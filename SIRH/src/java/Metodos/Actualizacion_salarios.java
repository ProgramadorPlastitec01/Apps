package Metodos;

import Controladores_BD.MenuJpaController;
import Controladores_BD.PersonalJpaController;

public class Actualizacion_salarios {

    MenuJpaController jpacmnu = new MenuJpaController();
    PersonalJpaController jpacpsn = new PersonalJpaController();

    public String Actualizar_salario_personal(String empleados, String new_salarios) throws Exception {
        boolean proceso = false;
        String[] arg_empleados = null;
        String[] arg_salarios = null;
        arg_empleados = empleados.split(",");
        arg_salarios = new_salarios.split(",");
        for (int i = 0; i < arg_empleados.length; i++) {
            jpacpsn.Inactivar_datos_old_empleado(arg_empleados[i]);
            jpacpsn.Actualizar_salarios(arg_empleados[i], Integer.parseInt(arg_salarios[i]));
        }
        return "Realizado";
    }

    public String Revertir_actualizacion_salario_personal(String empleados, String old_salarios) throws Exception {
        boolean proceso = false;
        String[] arg_empleados = null;
        String[] arg_salarios = null;
        arg_empleados = empleados.split(",");
        arg_salarios = old_salarios.split(",");
        for (int i = 0; i < arg_empleados.length; i++) {
            jpacpsn.Inactivar_datos_old_empleado(arg_empleados[i]);
            jpacpsn.Actualizar_salarios(arg_empleados[i], Integer.parseInt(arg_salarios[i]));
        }
        return "Realizado";
    }

}
