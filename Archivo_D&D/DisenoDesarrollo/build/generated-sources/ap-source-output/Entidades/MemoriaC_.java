package Entidades;

import Entidades.Etapa;
import Entidades.Fase;
import Entidades.MemoriaD;
import Entidades.Proyecto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(MemoriaC.class)
public class MemoriaC_ { 

    public static volatile SingularAttribute<MemoriaC, Fase> fkFase;
    public static volatile SingularAttribute<MemoriaC, Integer> idMemoriaC;
    public static volatile SingularAttribute<MemoriaC, Date> fchRegistro;
    public static volatile SingularAttribute<MemoriaC, String> usuRegistro;
    public static volatile SingularAttribute<MemoriaC, Etapa> fkEtapa;
    public static volatile CollectionAttribute<MemoriaC, MemoriaD> memoriaDCollection;
    public static volatile SingularAttribute<MemoriaC, Proyecto> fkProyecto;

}