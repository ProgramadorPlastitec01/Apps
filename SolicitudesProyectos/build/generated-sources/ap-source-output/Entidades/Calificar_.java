package Entidades;

import Entidades.Plano;
import Entidades.VerificarEtd;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Calificar.class)
public class Calificar_ { 

    public static volatile SingularAttribute<Calificar, String> nombrePlano;
    public static volatile SingularAttribute<Calificar, String> descripcion;
    public static volatile SingularAttribute<Calificar, VerificarEtd> idVerificar;
    public static volatile SingularAttribute<Calificar, String> medidaEstandar;
    public static volatile SingularAttribute<Calificar, Plano> idPlano;
    public static volatile SingularAttribute<Calificar, Integer> idCalificado;
    public static volatile SingularAttribute<Calificar, String> aplica;
    public static volatile SingularAttribute<Calificar, String> cumple;

}