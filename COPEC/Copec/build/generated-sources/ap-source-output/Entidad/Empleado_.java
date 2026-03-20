package Entidad;

import Entidad.Cargo;
import Entidad.Programacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile CollectionAttribute<Empleado, Programacion> programacionCollection;
    public static volatile SingularAttribute<Empleado, Integer> codigo;
    public static volatile SingularAttribute<Empleado, Integer> idEmpleado;
    public static volatile SingularAttribute<Empleado, String> apellido;
    public static volatile SingularAttribute<Empleado, Cargo> cargo;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile SingularAttribute<Empleado, String> dacumento;

}