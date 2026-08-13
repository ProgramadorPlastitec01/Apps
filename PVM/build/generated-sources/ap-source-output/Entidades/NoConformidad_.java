package Entidades;

import Entidades.InstrumentoMedicion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-05-20T11:40:02")
@StaticMetamodel(NoConformidad.class)
public class NoConformidad_ { 

    public static volatile SingularAttribute<NoConformidad, Integer> consecutivo;
    public static volatile SingularAttribute<NoConformidad, Date> fecha;
    public static volatile SingularAttribute<NoConformidad, String> plantilla;
    public static volatile SingularAttribute<NoConformidad, Integer> correo;
    public static volatile SingularAttribute<NoConformidad, Date> fchRegistro;
    public static volatile SingularAttribute<NoConformidad, InstrumentoMedicion> instrumentoMedicion;
    public static volatile SingularAttribute<NoConformidad, String> usuRegistro;
    public static volatile SingularAttribute<NoConformidad, Integer> idNoConformidad;

}