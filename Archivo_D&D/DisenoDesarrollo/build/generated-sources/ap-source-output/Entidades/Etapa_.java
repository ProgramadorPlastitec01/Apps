package Entidades;

import Entidades.Fase;
import Entidades.MemoriaC;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(Etapa.class)
public class Etapa_ { 

    public static volatile SingularAttribute<Etapa, Integer> estado;
    public static volatile CollectionAttribute<Etapa, MemoriaC> memoriaCCollection;
    public static volatile SingularAttribute<Etapa, String> numero;
    public static volatile SingularAttribute<Etapa, String> etapa;
    public static volatile SingularAttribute<Etapa, Date> fchRegistro;
    public static volatile SingularAttribute<Etapa, String> norma;
    public static volatile CollectionAttribute<Etapa, Fase> faseCollection;
    public static volatile SingularAttribute<Etapa, String> usuRegistro;
    public static volatile SingularAttribute<Etapa, String> guiaNorma;
    public static volatile SingularAttribute<Etapa, Integer> idEtapa;

}