package Entidades;

import Entidades.TipoInstrumento;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-02-17T10:56:24")
@StaticMetamodel(Area.class)
public class Area_ { 

    public static volatile SingularAttribute<Area, String> area;
    public static volatile SingularAttribute<Area, Integer> estado;
    public static volatile SingularAttribute<Area, String> responsable;
    public static volatile SingularAttribute<Area, Integer> idArea;
    public static volatile SingularAttribute<Area, String> siglatura;
    public static volatile SingularAttribute<Area, Date> fchRegistro;
    public static volatile CollectionAttribute<Area, TipoInstrumento> tipoInstrumentoCollection;
    public static volatile SingularAttribute<Area, String> usuRegistro;

}