package Metodos;

/**
 * Reglas de trabajo colaborativo sobre un turno (registro).
 *
 * registro.responsable guarda uno o varios "Rol/Nombre" separados por coma.
 * Los turnos de CALIDAD pueden ser trabajados por cualquier usuario con rol
 * Calidad-Pruebas; al registrar en el turno se agrega como responsable.
 */
public class Control_turno {

    public static final String ROL_COLABORADOR = "Calidad-Pruebas";
    public static final String PROCESO_COLABORATIVO = "CALIDAD";
    public static final String SEPARADOR = ", ";

    /**
     * Indica si user_rol ("Rol/Nombre") esta en la lista de responsables.
     * Compara cada responsable completo, no por subcadena.
     */
    public static boolean esResponsable(Object responsables, String user_rol) {
        if (responsables == null || user_rol == null) {
            return false;
        }
        String usuario = user_rol.trim();
        for (String responsable : responsables.toString().split(",")) {
            if (responsable.trim().equalsIgnoreCase(usuario)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Texto para mostrar: solo los nombres, sin el rol
     * ("Calidad-Pruebas/ANA, Calidad-Pruebas/LUIS" -> "ANA, LUIS").
     */
    public static String nombresResponsables(Object responsables) {
        if (responsables == null || responsables.toString().trim().isEmpty()) {
            return "---";
        }
        StringBuilder nombres = new StringBuilder();
        for (String responsable : responsables.toString().split(",")) {
            String valor = responsable.trim();
            if (valor.isEmpty()) {
                continue;
            }
            int barra = valor.indexOf('/');
            if (nombres.length() > 0) {
                nombres.append(SEPARADOR);
            }
            nombres.append(barra >= 0 ? valor.substring(barra + 1).trim() : valor);
        }
        return nombres.length() > 0 ? nombres.toString() : "---";
    }

    /**
     * Indica si el rol puede colaborar en turnos del proceso indicado sin ser
     * quien lo creo.
     */
    public static boolean esColaborador(String rol, Object proceso_turno) {
        return ROL_COLABORADOR.equals(rol)
                && proceso_turno != null
                && PROCESO_COLABORATIVO.equalsIgnoreCase(proceso_turno.toString().trim());
    }

    /**
     * Indica si el usuario puede registrar, editar y consultar las pruebas
     * del turno.
     */
    public static boolean puedeTrabajarTurno(Object responsables, Object proceso_turno, String user_rol, String rol) {
        return esResponsable(responsables, user_rol) || esColaborador(rol, proceso_turno);
    }

    private static boolean esProcesoCalidad(Object proceso_turno) {
        return proceso_turno != null && PROCESO_COLABORATIVO.equalsIgnoreCase(proceso_turno.toString().trim());
    }

    private static boolean mismoProceso(String rol_proceso, Object proceso_turno) {
        return rol_proceso != null && proceso_turno != null && rol_proceso.equalsIgnoreCase(proceso_turno.toString().trim());
    }

    /**
     * Abrir un turno cerrado: Administrador o Coordinadora-Calidad si son
     * responsables; Coordinadora-Calidad ademas en cualquier turno de CALIDAD.
     */
    public static boolean puedeReabrirTurno(String rol, Object proceso_turno, boolean es_responsable) {
        if (es_responsable && ("Administrador".equals(rol) || "Coordinadora-Calidad".equals(rol))) {
            return true;
        }
        return "Coordinadora-Calidad".equals(rol) && esProcesoCalidad(proceso_turno);
    }

    /**
     * Acceso al menu Complementos (Lineas, Pruebas, Proceso): solo
     * Administrador y Coordinadora-Calidad.
     */
    public static boolean puedeGestionarComplementos(String rol) {
        return "Administrador".equals(rol) || "Coordinadora-Calidad".equals(rol);
    }

    /**
     * Firmar como quien ENTREGA un turno abierto: responsables del mismo
     * proceso, o cualquier Calidad-Pruebas en turnos de CALIDAD.
     */
    public static boolean puedeFirmarEntrega(String rol, String rol_proceso, Object proceso_turno, boolean es_responsable) {
        return (es_responsable && mismoProceso(rol_proceso, proceso_turno)) || esColaborador(rol, proceso_turno);
    }

    /**
     * Firmar como quien RECIBE un turno abierto: usuarios del mismo proceso
     * que no son responsables, o cualquier Calidad-Pruebas en turnos de CALIDAD.
     */
    public static boolean puedeFirmarRecibe(String rol, String rol_proceso, Object proceso_turno, boolean es_responsable) {
        return (!es_responsable && mismoProceso(rol_proceso, proceso_turno)) || esColaborador(rol, proceso_turno);
    }
}
