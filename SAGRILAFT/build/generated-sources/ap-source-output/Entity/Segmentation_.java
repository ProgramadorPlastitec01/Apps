package Entity;

import Entity.Ciiu;
import Entity.Controls;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-11-20T11:14:01")
@StaticMetamodel(Segmentation.class)
public class Segmentation_ { 

    public static volatile SingularAttribute<Segmentation, Integer> typeService;
    public static volatile CollectionAttribute<Segmentation, Controls> controlsCollection;
    public static volatile SingularAttribute<Segmentation, String> country;
    public static volatile SingularAttribute<Segmentation, String> city;
    public static volatile SingularAttribute<Segmentation, Integer> typeDocumentation;
    public static volatile SingularAttribute<Segmentation, String> businessName;
    public static volatile SingularAttribute<Segmentation, String> shoppingValueYear;
    public static volatile SingularAttribute<Segmentation, Integer> type;
    public static volatile SingularAttribute<Segmentation, Integer> associateSupplyChain;
    public static volatile SingularAttribute<Segmentation, Date> dateLinking;
    public static volatile SingularAttribute<Segmentation, Integer> qualCommercial;
    public static volatile SingularAttribute<Segmentation, Integer> pep;
    public static volatile SingularAttribute<Segmentation, Integer> plastitecCode;
    public static volatile SingularAttribute<Segmentation, Integer> id;
    public static volatile SingularAttribute<Segmentation, Integer> relationship;
    public static volatile SingularAttribute<Segmentation, String> contactName;
    public static volatile SingularAttribute<Segmentation, String> operationsFrecuency;
    public static volatile SingularAttribute<Segmentation, String> addressBeneficiary;
    public static volatile SingularAttribute<Segmentation, String> contactPosition;
    public static volatile SingularAttribute<Segmentation, Date> dateMonitoring;
    public static volatile SingularAttribute<Segmentation, Integer> idSegmentation;
    public static volatile SingularAttribute<Segmentation, Integer> finalBeneficiary;
    public static volatile SingularAttribute<Segmentation, Integer> kindPerson;
    public static volatile SingularAttribute<Segmentation, Integer> antiquity;
    public static volatile SingularAttribute<Segmentation, Date> dateRegister;
    public static volatile SingularAttribute<Segmentation, Integer> qualcustom;
    public static volatile SingularAttribute<Segmentation, Ciiu> ciiu1;
    public static volatile SingularAttribute<Segmentation, Ciiu> ciiu;
    public static volatile SingularAttribute<Segmentation, String> representativeName;
    public static volatile SingularAttribute<Segmentation, Integer> qualMarket;
    public static volatile SingularAttribute<Segmentation, Integer> qualShipment;

}