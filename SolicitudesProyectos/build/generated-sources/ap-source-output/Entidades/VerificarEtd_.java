package Entidades;

import Entidades.CabeceraEtdHasVerificarEtd;
import Entidades.Calificar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-27T12:21:14")
@StaticMetamodel(VerificarEtd.class)
public class VerificarEtd_ { 

    public static volatile SingularAttribute<VerificarEtd, String> descripcion;
    public static volatile CollectionAttribute<VerificarEtd, Calificar> calificarCollection;
    public static volatile SingularAttribute<VerificarEtd, Integer> idVerificaretd;
    public static volatile CollectionAttribute<VerificarEtd, CabeceraEtdHasVerificarEtd> cabeceraEtdHasVerificarEtdCollection;
    public static volatile SingularAttribute<VerificarEtd, String> medidaStandard;

}