package Entidades;

import Entidades.FichaTecnica;
import Entidades.Linea;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-09-04T15:58:53")
@StaticMetamodel(OrdenProduccion.class)
public class OrdenProduccion_ { 

    public static volatile SingularAttribute<OrdenProduccion, String> cliente;
    public static volatile SingularAttribute<OrdenProduccion, Linea> idLinea;
    public static volatile SingularAttribute<OrdenProduccion, Integer> estado;
    public static volatile SingularAttribute<OrdenProduccion, String> numero;
    public static volatile SingularAttribute<OrdenProduccion, String> usuarioRegistro;
    public static volatile SingularAttribute<OrdenProduccion, FichaTecnica> idFichaTecnica;
    public static volatile SingularAttribute<OrdenProduccion, Date> fechaRegistro;
    public static volatile SingularAttribute<OrdenProduccion, Integer> idOrden;
    public static volatile SingularAttribute<OrdenProduccion, String> observacion;

}