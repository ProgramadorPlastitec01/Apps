package Entidades;

import Entidades.OrdenProduccion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(Linea.class)
public class Linea_ { 

    public static volatile SingularAttribute<Linea, Integer> idLinea;
    public static volatile SingularAttribute<Linea, String> codigo;
    public static volatile SingularAttribute<Linea, Integer> estado;
    public static volatile SingularAttribute<Linea, String> usuarioRegistro;
    public static volatile CollectionAttribute<Linea, OrdenProduccion> ordenProduccionCollection;
    public static volatile SingularAttribute<Linea, Date> fechaRegistro;
    public static volatile SingularAttribute<Linea, String> nombre;

}