package Entidades;

import Entidades.CabeceraEtdHasVerificarEtd;
import Entidades.Electrodo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(CabeceraEtd.class)
public class CabeceraEtd_ { 

    public static volatile SingularAttribute<CabeceraEtd, Date> fecha;
    public static volatile SingularAttribute<CabeceraEtd, String> verificadoPor;
    public static volatile SingularAttribute<CabeceraEtd, Electrodo> idElectrodo;
    public static volatile SingularAttribute<CabeceraEtd, Integer> idCabeceraetd;
    public static volatile CollectionAttribute<CabeceraEtd, CabeceraEtdHasVerificarEtd> cabeceraEtdHasVerificarEtdCollection;
    public static volatile SingularAttribute<CabeceraEtd, String> solicitudes;

}