package Entidades;

import Entidades.InstrumentoMedicion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-05-20T11:40:02")
@StaticMetamodel(Tipo.class)
public class Tipo_ { 

    public static volatile SingularAttribute<Tipo, String> tipo;
    public static volatile SingularAttribute<Tipo, Integer> idTipo;
    public static volatile CollectionAttribute<Tipo, InstrumentoMedicion> instrumentoMedicionCollection;

}