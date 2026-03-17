package Entidades;

import Entidades.CabeceraEtd;
import Entidades.Plano;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(Electrodo.class)
public class Electrodo_ { 

    public static volatile SingularAttribute<Electrodo, String> estado;
    public static volatile SingularAttribute<Electrodo, Integer> idElectrodo;
    public static volatile SingularAttribute<Electrodo, Plano> idPlano;
    public static volatile CollectionAttribute<Electrodo, CabeceraEtd> cabeceraEtdCollection;
    public static volatile SingularAttribute<Electrodo, String> numeroElectrodo;
    public static volatile SingularAttribute<Electrodo, String> linea;

}