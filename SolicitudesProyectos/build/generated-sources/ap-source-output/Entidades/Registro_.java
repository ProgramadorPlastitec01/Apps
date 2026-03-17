package Entidades;

import Entidades.Maquina;
import Entidades.Solicitud;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Registro.class)
public class Registro_ { 

    public static volatile SingularAttribute<Registro, String> descripcion;
    public static volatile SingularAttribute<Registro, String> herramienta;
    public static volatile SingularAttribute<Registro, Date> fechaRegistro;
    public static volatile SingularAttribute<Registro, Integer> idPlano;
    public static volatile SingularAttribute<Registro, String> pieza;
    public static volatile SingularAttribute<Registro, Solicitud> idSolicitud;
    public static volatile SingularAttribute<Registro, Date> fechaFin;
    public static volatile SingularAttribute<Registro, String> usuarioRegistro;
    public static volatile SingularAttribute<Registro, Date> fechaInicio;
    public static volatile SingularAttribute<Registro, String> tiempo;
    public static volatile SingularAttribute<Registro, Maquina> idMaquina;
    public static volatile SingularAttribute<Registro, Integer> cantidad;
    public static volatile SingularAttribute<Registro, Integer> idRegistro;

}