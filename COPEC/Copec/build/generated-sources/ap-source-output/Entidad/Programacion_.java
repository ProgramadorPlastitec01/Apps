package Entidad;

import Entidad.Capacitacion;
import Entidad.Empleado;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(Programacion.class)
public class Programacion_ { 

    public static volatile SingularAttribute<Programacion, String> estado;
    public static volatile SingularAttribute<Programacion, Capacitacion> capacitacion;
    public static volatile SingularAttribute<Programacion, Empleado> empleado;
    public static volatile SingularAttribute<Programacion, Integer> idProgramacion;
    public static volatile SingularAttribute<Programacion, Date> fechaProgramacion;

}