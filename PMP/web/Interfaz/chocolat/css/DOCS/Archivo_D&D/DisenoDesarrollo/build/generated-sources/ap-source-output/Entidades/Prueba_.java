package Entidades;

import Entidades.PPrueba;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Prueba.class)
public class Prueba_ { 

    public static volatile SingularAttribute<Prueba, String> tCategoria;
    public static volatile SingularAttribute<Prueba, Integer> estado;
    public static volatile SingularAttribute<Prueba, String> criterio;
    public static volatile CollectionAttribute<Prueba, PPrueba> pPruebaCollection;
    public static volatile SingularAttribute<Prueba, String> tPrueba;
    public static volatile SingularAttribute<Prueba, Integer> idPrueba;
    public static volatile SingularAttribute<Prueba, Date> fchRegistro;
    public static volatile SingularAttribute<Prueba, String> documento;
    public static volatile SingularAttribute<Prueba, String> usuRegistro;
    public static volatile SingularAttribute<Prueba, String> prueba;

}