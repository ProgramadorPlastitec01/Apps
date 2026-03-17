package Entidades;

import Entidades.InstrumentoMedicion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-02-17T10:56:24")
@StaticMetamodel(Traslado.class)
public class Traslado_ { 

    public static volatile SingularAttribute<Traslado, String> tipo;
    public static volatile SingularAttribute<Traslado, String> accesorio;
    public static volatile SingularAttribute<Traslado, String> usuarioEnt;
    public static volatile SingularAttribute<Traslado, String> plantilla;
    public static volatile SingularAttribute<Traslado, InstrumentoMedicion> instrumentoMedicion;
    public static volatile SingularAttribute<Traslado, String> usuarioSal;
    public static volatile SingularAttribute<Traslado, Integer> idTraslado;

}