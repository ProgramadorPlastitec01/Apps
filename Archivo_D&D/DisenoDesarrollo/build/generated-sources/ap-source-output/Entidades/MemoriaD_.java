package Entidades;

import Entidades.MemoriaC;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(MemoriaD.class)
public class MemoriaD_ { 

    public static volatile SingularAttribute<MemoriaD, String> usuRespuesta;
    public static volatile SingularAttribute<MemoriaD, Integer> estado;
    public static volatile SingularAttribute<MemoriaD, String> memoria;
    public static volatile SingularAttribute<MemoriaD, MemoriaC> fkMemoriaC;
    public static volatile SingularAttribute<MemoriaD, Integer> correoActividad;
    public static volatile SingularAttribute<MemoriaD, Date> fchRespuesta;
    public static volatile SingularAttribute<MemoriaD, String> usuRegistro;
    public static volatile SingularAttribute<MemoriaD, String> autoridad;
    public static volatile SingularAttribute<MemoriaD, Integer> idMemoriaD;
    public static volatile SingularAttribute<MemoriaD, Integer> correoRespuesta;
    public static volatile SingularAttribute<MemoriaD, Date> fchRegistro;
    public static volatile SingularAttribute<MemoriaD, String> respuesta;
    public static volatile SingularAttribute<MemoriaD, Integer> correoAutor;

}