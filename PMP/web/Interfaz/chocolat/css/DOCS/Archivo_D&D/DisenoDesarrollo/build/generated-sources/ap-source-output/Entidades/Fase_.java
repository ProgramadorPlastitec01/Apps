package Entidades;

import Entidades.Etapa;
import Entidades.MemoriaC;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Fase.class)
public class Fase_ { 

    public static volatile SingularAttribute<Fase, String> fase;
    public static volatile SingularAttribute<Fase, Integer> estado;
    public static volatile CollectionAttribute<Fase, MemoriaC> memoriaCCollection;
    public static volatile SingularAttribute<Fase, Integer> idFase;
    public static volatile SingularAttribute<Fase, Date> fchRegistro;
    public static volatile SingularAttribute<Fase, String> norma;
    public static volatile SingularAttribute<Fase, String> usuRegistro;
    public static volatile SingularAttribute<Fase, Etapa> fkEtapa;
    public static volatile SingularAttribute<Fase, String> letra;

}