package Entidades;

import Entidades.Verificacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-05-20T11:40:02")
@StaticMetamodel(TipoVerificacion.class)
public class TipoVerificacion_ { 

    public static volatile SingularAttribute<TipoVerificacion, String> tipo;
    public static volatile SingularAttribute<TipoVerificacion, Integer> estado;
    public static volatile CollectionAttribute<TipoVerificacion, Verificacion> verificacionCollection;
    public static volatile SingularAttribute<TipoVerificacion, Integer> idTipoVerificacion;
    public static volatile SingularAttribute<TipoVerificacion, Date> fchRegistro;
    public static volatile SingularAttribute<TipoVerificacion, String> usuRegistro;

}