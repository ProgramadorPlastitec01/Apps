package Entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2026-01-13T10:33:19")
@StaticMetamodel(Parametro.class)
public class Parametro_ { 

    public static volatile SingularAttribute<Parametro, Integer> idParametro;
    public static volatile SingularAttribute<Parametro, String> tipoMtto;
    public static volatile SingularAttribute<Parametro, String> frecuencia;
    public static volatile SingularAttribute<Parametro, String> usuarioRegistro;
    public static volatile SingularAttribute<Parametro, Date> fechaRegistro;
    public static volatile SingularAttribute<Parametro, String> ejecucion;
    public static volatile SingularAttribute<Parametro, String> grupo;
    public static volatile SingularAttribute<Parametro, String> tipoEquipo;
    public static volatile SingularAttribute<Parametro, String> actividad;

}