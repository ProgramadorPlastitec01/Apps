package Email;

import Controladoras.ActividadJpaController;
import Controladoras.FormularioJpaController;
import java.util.List;

public class Actividad {

    ActividadJpaController jpa_actividad = new ActividadJpaController();
    FormularioJpaController jpa_formulario = new FormularioJpaController();
    int idUsuario = 0;
    int idCargo = 0;
    int idActividad = 0;
    int consecutivo = 0;
    int cierre = 0;
    int contCampos = 0;
    String NomCampos = "";
    String filtro = "";
    String usuarioR = "";
    String fecha = "";
    String hora = "";
    String turno = "";
    String nomAdjunto = "";
    int tnovedad = 0;
    String campo1 = "", campo2 = "", campo3 = "", campo4 = "", campo5 = "", campo6 = "", campo7 = "", campo8 = "", campo9 = "";
    List Consultaform = null;
    boolean resultado = false;

    public boolean RegistroActividad(List variables, String adjunto) {
        // <editor-fold defaultstate="collapsed"  desc="Registro actividad">
//        Object[] obj_variables = (Object[]) variables.get(0);
        idUsuario = Integer.parseInt(variables.get(0).toString());
        idCargo = Integer.parseInt(variables.get(1).toString());
        usuarioR = variables.get(2).toString();
        consecutivo = Integer.parseInt(variables.get(3).toString());
        contCampos = Integer.parseInt(variables.get(4).toString());
        NomCampos = variables.get(5).toString();
//        hora = variables.get(6).toString();
//        fecha = variables.get(7).toString();
        turno = variables.get(8).toString();
        tnovedad = Integer.parseInt(variables.get(9).toString());
        try {
            campo1 = variables.get(10).toString();
        } catch (Exception e) {
            campo1 = "null";
        }
        try {
            campo2 = variables.get(11).toString();
        } catch (Exception e) {
            campo2 = "null";
        }
        try {
            campo3 = variables.get(12).toString();
        } catch (Exception e) {
            campo3 = "null";
        }
        try {
            campo4 = variables.get(13).toString();
        } catch (Exception e) {
            campo4 = "null";
        }
        try {
            campo5 = variables.get(14).toString();
        } catch (Exception e) {
            campo5 = "null";
        }
        try {
            campo6 = variables.get(15).toString();
        } catch (Exception e) {
            campo6 = "null";
        }
        try {
            campo7 = variables.get(16).toString();
        } catch (Exception e) {
            campo7 = "null";
        }
        try {
            campo8 = variables.get(17).toString();
        } catch (Exception e) {
            campo8 = "null";
        }
        try {
            campo9 = variables.get(18).toString();
        } catch (Exception e) {
            campo9 = "null";
        }
        if (idCargo == 13) {
            resultado = jpa_actividad.RegistroActividad(idUsuario, consecutivo, usuarioR,  turno, (adjunto.equals("N/A") ? "null" : adjunto), tnovedad, contCampos, NomCampos, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
//            resultado = jpa_actividad.RegistroActividad(idUsuario, consecutivo, usuarioR, fecha, hora, turno, (adjunto.equals("N/A") ? "null" : adjunto), tnovedad, contCampos, NomCampos, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
        } else {
            resultado = jpa_actividad.RegistroActividad(idUsuario, consecutivo, usuarioR, turno, (adjunto.equals("N/A") ? "null" : adjunto), tnovedad, ((adjunto == null ? "" == null : adjunto.equals("null")) ? contCampos : (contCampos - 1)), NomCampos, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
//            resultado = jpa_actividad.RegistroActividad(idUsuario, consecutivo, usuarioR, fecha, hora, turno, (adjunto.equals("N/A") ? "null" : adjunto), tnovedad, ((adjunto == null ? "" == null : adjunto.equals("null")) ? contCampos : (contCampos - 1)), NomCampos, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
        }
        return resultado;
        // </editor-fold>
    }

    public boolean ModificarActividad(List variables, String adjunto) {
//        Object[] obj_variables = (Object[]) variables.get(0);
        filtro = variables.get(0).toString();
        idActividad = Integer.parseInt(variables.get(1).toString());
        usuarioR = variables.get(2).toString();
        hora = variables.get(3).toString();
        fecha = variables.get(4).toString();
        turno = variables.get(5).toString();
        tnovedad = Integer.parseInt(variables.get(6).toString());
        try {
            campo1 = variables.get(7).toString();
        } catch (Exception e) {
            campo1 = "null";
        }
        try {
            campo2 = variables.get(8).toString();
        } catch (Exception e) {
            campo2 = "null";
        }
        try {
            campo3 = variables.get(9).toString();
        } catch (Exception e) {
            campo3 = "null";
        }
        try {
            campo4 = variables.get(10).toString();
        } catch (Exception e) {
            campo4 = "null";
        }
        try {
            campo5 = variables.get(11).toString();
        } catch (Exception e) {
            campo5 = "null";
        }
        try {
            campo6 = variables.get(12).toString();
        } catch (Exception e) {
            campo6 = "null";
        }
        try {
            campo7 = variables.get(13).toString();
        } catch (Exception e) {
            campo7 = "null";
        }
        try {
            campo8 = variables.get(14).toString();
        } catch (Exception e) {
            campo8 = "null";
        }
        try {
            campo9 = variables.get(15).toString();
        } catch (Exception e) {
            campo9 = "null";
        }
        resultado = jpa_actividad.ModificarActividad(idActividad, usuarioR, fecha, hora, turno, (adjunto.equals("N/A") ? "null" : adjunto), tnovedad, campo1, campo2, campo3, campo4, campo5, campo6, campo7, campo8, campo9);
        return resultado;
    }
}
