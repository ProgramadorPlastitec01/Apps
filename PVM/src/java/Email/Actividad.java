package Email;

import Controladores.InstrumentoMedicionJpaController;
import java.util.List;

public class Actividad {

    InstrumentoMedicionJpaController jpa_intrumento = new InstrumentoMedicionJpaController();
    int idInstrumento = 0;
    int idVerificacion = 0;
    int idTipo = 0;
    int id_plantilla = 0;
    String Justificacion = "";
    String usuarioR = "";
    String nomAdjunto = "";
    String fecha = "";
    boolean resultado = false;

    public boolean RegistroVerificacion(List variables, String adjunto) {
        // <editor-fold defaultstate="collapsed"  desc="Registro verificacion">
//        Object[] obj_variables = (Object[]) variables.get(0);
        idInstrumento = Integer.parseInt(variables.get(0).toString());
        idTipo = Integer.parseInt(variables.get(1).toString());
        Justificacion = variables.get(2).toString();
        usuarioR = variables.get(3).toString();
        resultado = jpa_intrumento.registrarVerificacionInstrumento(idInstrumento, idTipo, Justificacion, adjunto, usuarioR);
        return resultado;
        // </editor-fold>
    }

    public boolean FinalizarVerificacion(List variables, String adjunto) {
        // <editor-fold defaultstate="collapsed"  desc="Registro verificacion">
//        Object[] obj_variables = (Object[]) variables.get(0);
        idVerificacion = Integer.parseInt(variables.get(2).toString());
        fecha = variables.get(3).toString();
        Justificacion = variables.get(4).toString();
        resultado = jpa_intrumento.finalizarVerificacion(idVerificacion, fecha, Justificacion, adjunto);
        return resultado;
        // </editor-fold>
    }
//    public boolean ModificarActividad(List variables, String adjunto) {
////        Object[] obj_variables = (Object[]) variables.get(0);
//        filtro = variables.get(0).toString();
//        idActividad = Integer.parseInt(variables.get(1).toString());
//        usuarioR = variables.get(2).toString();
//        hora = variables.get(3).toString();
//        fecha = variables.get(4).toString();
//        turno = variables.get(5).toString();
//        tnovedad = Integer.parseInt(variables.get(6).toString());
//        try {
//            campo1 = variables.get(7).toString();
//        } catch (Exception e) {
//            campo1 = "null";
//        }
//        try {
//            campo2 = variables.get(8).toString();
//        } catch (Exception e) {
//            campo2 = "null";
//        }
//        try {
//            campo3 = variables.get(9).toString();
//        } catch (Exception e) {
//            campo3 = "null";
//        }
//        try {
//            campo4 = variables.get(10).toString();
//        } catch (Exception e) {
//            campo4 = "null";
//        }
//        try {
//            campo5 = variables.get(11).toString();
//        } catch (Exception e) {
//            campo5 = "null";
//        }
//        try {
//            campo6 = variables.get(12).toString();
//        } catch (Exception e) {
//            campo6 = "null";
//        }
//        try {
//            campo7 = variables.get(13).toString();
//        } catch (Exception e) {
//            campo7 = "null";
//        }
//        try {
//            campo8 = variables.get(14).toString();
//        } catch (Exception e) {
//            campo8 = "null";
//        }
//        try {
//            campo9 = variables.get(15).toString();
//        } catch (Exception e) {
//            campo9 = "null";
//        }
//        resultado = jpa_actividad.ModificarActividad(idActividad, usuarioR, fecha, hora, turno, (adjunto.equals("N/A") ? "null" : adjunto), tnovedad, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
//        return resultado;
//    }
}
