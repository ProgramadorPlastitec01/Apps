package Entidad;

import Entidad.Pregunta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-14T14:54:33")
@StaticMetamodel(TipoProceso.class)
public class TipoProceso_ { 

    public static volatile SingularAttribute<TipoProceso, Integer> estado;
    public static volatile SingularAttribute<TipoProceso, String> proceso;
    public static volatile SingularAttribute<TipoProceso, Date> fchRegistro;
    public static volatile SingularAttribute<TipoProceso, Integer> idTipoProceso;
    public static volatile SingularAttribute<TipoProceso, String> usuRegistro;
    public static volatile CollectionAttribute<TipoProceso, Pregunta> preguntaCollection;

}