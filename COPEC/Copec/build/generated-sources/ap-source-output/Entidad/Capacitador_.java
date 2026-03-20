package Entidad;

import Entidad.Capacitacion;
import Entidad.Cargo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(Capacitador.class)
public class Capacitador_ { 

    public static volatile SingularAttribute<Capacitador, Integer> codigo;
    public static volatile SingularAttribute<Capacitador, Integer> idCapacitador;
    public static volatile CollectionAttribute<Capacitador, Capacitacion> capacitacionCollection;
    public static volatile SingularAttribute<Capacitador, String> apellido;
    public static volatile SingularAttribute<Capacitador, String> documento;
    public static volatile SingularAttribute<Capacitador, String> usuario;
    public static volatile SingularAttribute<Capacitador, String> contrasena;
    public static volatile SingularAttribute<Capacitador, Cargo> cargo;
    public static volatile SingularAttribute<Capacitador, String> nombre;

}