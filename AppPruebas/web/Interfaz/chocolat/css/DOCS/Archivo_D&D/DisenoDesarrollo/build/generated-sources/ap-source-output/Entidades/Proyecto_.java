package Entidades;

import Entidades.Adjunto;
import Entidades.FormulaC;
import Entidades.HerramentalC;
import Entidades.MemoriaC;
import Entidades.PPrueba;
import Entidades.PruebaC;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-10-09T11:47:51")
@StaticMetamodel(Proyecto.class)
public class Proyecto_ { 

    public static volatile SingularAttribute<Proyecto, String> participe;
    public static volatile SingularAttribute<Proyecto, String> tEntrada;
    public static volatile SingularAttribute<Proyecto, Integer> idProyecto;
    public static volatile SingularAttribute<Proyecto, Integer> estado;
    public static volatile CollectionAttribute<Proyecto, MemoriaC> memoriaCCollection;
    public static volatile SingularAttribute<Proyecto, Date> fchEntrada;
    public static volatile SingularAttribute<Proyecto, String> numero;
    public static volatile CollectionAttribute<Proyecto, PPrueba> pPruebaCollection;
    public static volatile CollectionAttribute<Proyecto, FormulaC> formulaCCollection;
    public static volatile SingularAttribute<Proyecto, String> proyecto;
    public static volatile SingularAttribute<Proyecto, String> usuRegistro;
    public static volatile CollectionAttribute<Proyecto, HerramentalC> herramentalCCollection;
    public static volatile CollectionAttribute<Proyecto, Adjunto> adjuntoCollection;
    public static volatile SingularAttribute<Proyecto, String> usoPrevisto;
    public static volatile SingularAttribute<Proyecto, String> adjunto;
    public static volatile SingularAttribute<Proyecto, Date> fchRegistro;
    public static volatile CollectionAttribute<Proyecto, PruebaC> pruebaCCollection;
    public static volatile SingularAttribute<Proyecto, Short> tipoProyecto;
    public static volatile SingularAttribute<Proyecto, String> fchSalida;

}