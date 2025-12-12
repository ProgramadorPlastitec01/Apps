package Entidades;

import Entidades.PPrueba;
import Entidades.Proyecto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-10T10:10:26")
@StaticMetamodel(PruebaC.class)
public class PruebaC_ { 

    public static volatile SingularAttribute<PruebaC, Integer> estado;
    public static volatile CollectionAttribute<PruebaC, PPrueba> pPruebaCollection;
    public static volatile SingularAttribute<PruebaC, String> verifica;
    public static volatile SingularAttribute<PruebaC, String> usuRegistro;
    public static volatile SingularAttribute<PruebaC, Date> fchInicial;
    public static volatile SingularAttribute<PruebaC, Proyecto> fkProyecto;
    public static volatile SingularAttribute<PruebaC, String> consecutivo;
    public static volatile SingularAttribute<PruebaC, Integer> idPruebaC;
    public static volatile SingularAttribute<PruebaC, String> tProgramacion;
    public static volatile SingularAttribute<PruebaC, Date> fchRegistro;
    public static volatile SingularAttribute<PruebaC, String> programacion;
    public static volatile SingularAttribute<PruebaC, String> observacion;
    public static volatile SingularAttribute<PruebaC, Date> fchVerifica;

}