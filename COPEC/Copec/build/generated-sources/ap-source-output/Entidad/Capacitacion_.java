package Entidad;

import Entidad.Capacitador;
import Entidad.Programacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(Capacitacion.class)
public class Capacitacion_ { 

    public static volatile CollectionAttribute<Capacitacion, Programacion> programacionCollection;
    public static volatile SingularAttribute<Capacitacion, String> contenido;
    public static volatile SingularAttribute<Capacitacion, Integer> idCapacitacion;
    public static volatile SingularAttribute<Capacitacion, Date> fechaInicio;
    public static volatile SingularAttribute<Capacitacion, Date> fechaFinal;
    public static volatile SingularAttribute<Capacitacion, Capacitador> capacitador;
    public static volatile SingularAttribute<Capacitacion, Date> fechaInsercion;
    public static volatile SingularAttribute<Capacitacion, String> nombre;

}