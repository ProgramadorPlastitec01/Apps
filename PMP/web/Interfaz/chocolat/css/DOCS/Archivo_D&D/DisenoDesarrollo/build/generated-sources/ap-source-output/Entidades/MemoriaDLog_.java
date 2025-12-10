package Entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(MemoriaDLog.class)
public class MemoriaDLog_ { 

    public static volatile SingularAttribute<MemoriaDLog, Integer> idMemoriaDLog;
    public static volatile SingularAttribute<MemoriaDLog, String> usuRespuesta;
    public static volatile SingularAttribute<MemoriaDLog, Integer> estado;
    public static volatile SingularAttribute<MemoriaDLog, String> memoria;
    public static volatile SingularAttribute<MemoriaDLog, Integer> fkMemoriaD;
    public static volatile SingularAttribute<MemoriaDLog, Date> fechaRegistro;
    public static volatile SingularAttribute<MemoriaDLog, Integer> fkMemoriaC;
    public static volatile SingularAttribute<MemoriaDLog, Integer> correoActividad;
    public static volatile SingularAttribute<MemoriaDLog, Date> fchRespuesta;
    public static volatile SingularAttribute<MemoriaDLog, String> usuRegistro;
    public static volatile SingularAttribute<MemoriaDLog, String> tipoLog;
    public static volatile SingularAttribute<MemoriaDLog, String> autoridad;
    public static volatile SingularAttribute<MemoriaDLog, Integer> correoRespuesta;
    public static volatile SingularAttribute<MemoriaDLog, Date> fchRegistro;
    public static volatile SingularAttribute<MemoriaDLog, String> respuesta;
    public static volatile SingularAttribute<MemoriaDLog, Integer> correoAutor;

}