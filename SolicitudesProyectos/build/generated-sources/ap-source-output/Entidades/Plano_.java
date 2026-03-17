package Entidades;

import Entidades.Calificar;
import Entidades.Electrodo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Plano.class)
public class Plano_ { 

    public static volatile SingularAttribute<Plano, String> nombrePlano;
    public static volatile SingularAttribute<Plano, Date> fechaIngreso;
    public static volatile SingularAttribute<Plano, String> tipo;
    public static volatile CollectionAttribute<Plano, Calificar> calificarCollection;
    public static volatile CollectionAttribute<Plano, Electrodo> electrodoCollection;
    public static volatile SingularAttribute<Plano, Integer> idPlano;

}