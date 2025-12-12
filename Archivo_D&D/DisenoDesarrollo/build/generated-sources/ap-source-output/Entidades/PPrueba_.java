package Entidades;

import Entidades.Proyecto;
import Entidades.Prueba;
import Entidades.PruebaC;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(PPrueba.class)
public class PPrueba_ { 

    public static volatile SingularAttribute<PPrueba, PruebaC> fkPruebaC;
    public static volatile SingularAttribute<PPrueba, Prueba> fkPrueba;
    public static volatile SingularAttribute<PPrueba, Date> fchRegistro;
    public static volatile SingularAttribute<PPrueba, String> usuRegistro;
    public static volatile SingularAttribute<PPrueba, Integer> idPPrueba;
    public static volatile SingularAttribute<PPrueba, Proyecto> fkProyecto;

}