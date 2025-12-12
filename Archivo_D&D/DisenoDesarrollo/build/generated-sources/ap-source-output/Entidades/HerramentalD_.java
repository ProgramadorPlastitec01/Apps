package Entidades;

import Entidades.HerramentalC;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(HerramentalD.class)
public class HerramentalD_ { 

    public static volatile SingularAttribute<HerramentalD, String> texto;
    public static volatile SingularAttribute<HerramentalD, String> tipo;
    public static volatile SingularAttribute<HerramentalD, String> tTitulo;
    public static volatile SingularAttribute<HerramentalD, String> adjunto;
    public static volatile SingularAttribute<HerramentalD, Date> fchRegistro;
    public static volatile SingularAttribute<HerramentalD, String> categoria;
    public static volatile SingularAttribute<HerramentalD, String> usuRegistro;
    public static volatile SingularAttribute<HerramentalD, HerramentalC> fkHerramentalC;
    public static volatile SingularAttribute<HerramentalD, Integer> idHerramentalD;

}