package Entidades;

import Entidades.HerramentalD;
import Entidades.Proyecto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(HerramentalC.class)
public class HerramentalC_ { 

    public static volatile SingularAttribute<HerramentalC, String> tipo;
    public static volatile SingularAttribute<HerramentalC, Integer> estado;
    public static volatile SingularAttribute<HerramentalC, String> numeroTipo;
    public static volatile SingularAttribute<HerramentalC, String> usuRegistro;
    public static volatile SingularAttribute<HerramentalC, Date> fchSolicitud;
    public static volatile SingularAttribute<HerramentalC, String> herramental;
    public static volatile SingularAttribute<HerramentalC, Integer> idHerramentalC;
    public static volatile SingularAttribute<HerramentalC, String> numeroHerramental;
    public static volatile SingularAttribute<HerramentalC, Proyecto> fkProyecto;
    public static volatile SingularAttribute<HerramentalC, String> numeroPlano;
    public static volatile SingularAttribute<HerramentalC, String> versionR;
    public static volatile SingularAttribute<HerramentalC, String> tiempoEstimado;
    public static volatile SingularAttribute<HerramentalC, Date> fchRegistro;
    public static volatile SingularAttribute<HerramentalC, String> aprobo;
    public static volatile CollectionAttribute<HerramentalC, HerramentalD> herramentalDCollection;
    public static volatile SingularAttribute<HerramentalC, String> observacion;

}