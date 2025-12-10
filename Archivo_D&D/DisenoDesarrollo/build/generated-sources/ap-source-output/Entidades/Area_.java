package Entidades;

import Entidades.Cargo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Area.class)
public class Area_ { 

    public static volatile SingularAttribute<Area, String> area;
    public static volatile SingularAttribute<Area, Integer> estado;
    public static volatile SingularAttribute<Area, Integer> idArea;
    public static volatile SingularAttribute<Area, Date> fchRegistro;
    public static volatile SingularAttribute<Area, String> signatura;
    public static volatile SingularAttribute<Area, String> usuRegistro;
    public static volatile CollectionAttribute<Area, Cargo> cargoCollection;

}