package Entidades;

import Entidades.Solicitud;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Seguimiento.class)
public class Seguimiento_ { 

    public static volatile SingularAttribute<Seguimiento, String> descripcion;
    public static volatile SingularAttribute<Seguimiento, Date> fecha;
    public static volatile SingularAttribute<Seguimiento, Integer> idSeguimientos;
    public static volatile SingularAttribute<Seguimiento, String> ejecutor;
    public static volatile SingularAttribute<Seguimiento, Solicitud> idSolicitud;

}