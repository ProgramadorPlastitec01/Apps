package Entidades;

import Entidades.Movimientos;
import Entidades.Registro;
import Entidades.Seguimiento;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Solicitud.class)
public class Solicitud_ { 

    public static volatile SingularAttribute<Solicitud, String> descripcion;
    public static volatile SingularAttribute<Solicitud, Integer> estado;
    public static volatile SingularAttribute<Solicitud, String> tipo;
    public static volatile SingularAttribute<Solicitud, Integer> idplano;
    public static volatile CollectionAttribute<Solicitud, Movimientos> movimientosCollection;
    public static volatile CollectionAttribute<Solicitud, Registro> registroCollection;
    public static volatile SingularAttribute<Solicitud, String> pieza;
    public static volatile SingularAttribute<Solicitud, Integer> idSolicitud;
    public static volatile CollectionAttribute<Solicitud, Seguimiento> seguimientoCollection;
    public static volatile SingularAttribute<Solicitud, String> cantidadProgramada;
    public static volatile SingularAttribute<Solicitud, String> prioridad;
    public static volatile SingularAttribute<Solicitud, Date> fechaIngreso;
    public static volatile SingularAttribute<Solicitud, String> numeroSolicitud;
    public static volatile SingularAttribute<Solicitud, String> maquinaProgramada;
    public static volatile SingularAttribute<Solicitud, Integer> ficha;
    public static volatile SingularAttribute<Solicitud, String> cantidad;
    public static volatile SingularAttribute<Solicitud, Integer> idPendiente;
    public static volatile SingularAttribute<Solicitud, Integer> idUsuarios;
    public static volatile SingularAttribute<Solicitud, Date> fechaFinSolicitud;

}