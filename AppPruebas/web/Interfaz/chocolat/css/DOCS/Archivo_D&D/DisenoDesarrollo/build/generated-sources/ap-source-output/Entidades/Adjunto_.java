package Entidades;

import Entidades.Proyecto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Adjunto.class)
public class Adjunto_ { 

    public static volatile SingularAttribute<Adjunto, Proyecto> idProyecto;
    public static volatile SingularAttribute<Adjunto, String> tipo;
    public static volatile SingularAttribute<Adjunto, String> fase;
    public static volatile SingularAttribute<Adjunto, String> verificacion;
    public static volatile SingularAttribute<Adjunto, Date> fechaVerificacion;
    public static volatile SingularAttribute<Adjunto, String> usuarioRegistro;
    public static volatile SingularAttribute<Adjunto, String> etapa;
    public static volatile SingularAttribute<Adjunto, Date> fechaRegistro;
    public static volatile SingularAttribute<Adjunto, String> adjunto;
    public static volatile SingularAttribute<Adjunto, Integer> idAdjunto;
    public static volatile SingularAttribute<Adjunto, String> observacion;
    public static volatile SingularAttribute<Adjunto, String> usuarioVerificacion;

}