package Entidades;

import Entidades.Menu;
import Entidades.Modulo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Opcion.class)
public class Opcion_ { 

    public static volatile SingularAttribute<Opcion, Integer> idOpcion;
    public static volatile SingularAttribute<Opcion, String> opcion;
    public static volatile SingularAttribute<Opcion, Date> fchRegistro;
    public static volatile SingularAttribute<Opcion, Modulo> fkModulo;
    public static volatile SingularAttribute<Opcion, String> usuRegistro;
    public static volatile SingularAttribute<Opcion, String> nombre;
    public static volatile CollectionAttribute<Opcion, Menu> menuCollection;

}