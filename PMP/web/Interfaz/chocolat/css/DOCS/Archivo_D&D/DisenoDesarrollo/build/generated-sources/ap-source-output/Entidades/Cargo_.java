package Entidades;

import Entidades.Area;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Cargo.class)
public class Cargo_ { 

    public static volatile SingularAttribute<Cargo, Integer> idCargo;
    public static volatile SingularAttribute<Cargo, Integer> estado;
    public static volatile SingularAttribute<Cargo, Area> fkArea;
    public static volatile SingularAttribute<Cargo, Date> fchRegistro;
    public static volatile SingularAttribute<Cargo, String> usuRegistro;
    public static volatile SingularAttribute<Cargo, String> cargo;

}